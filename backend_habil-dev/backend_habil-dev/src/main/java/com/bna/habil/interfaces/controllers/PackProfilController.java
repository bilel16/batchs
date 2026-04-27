package com.bna.habil.interfaces.controllers;

import com.bna.habil.application.dto.PackProfilDto;
import com.bna.habil.application.dto.ProfileConflictDto;
import com.bna.habil.application.services.PackProfilService;
import com.bna.habil.application.services.crud.model.BatchOperationResult;
import com.bna.habil.domain.entities.entitiesId.PackProfilId;
import com.bna.habil.domain.exceptions.BatchOperationException;
import com.bna.habil.domain.exceptions.DuplicateResourceException;
import com.bna.habil.domain.exceptions.EntityNotFoundException;
import com.bna.habil.infrastructure.security.model.ResponseHabil;
import com.bna.habil.infrastructure.utils.Constants;
import com.bna.habil.interfaces.response.SyncResult;
import jakarta.validation.Valid;
import jakarta.validation.ValidationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/pack-profil")
@Slf4j
@Validated
public class PackProfilController {

    private final PackProfilService packProfilService;
    private static final String PACK_PROFILE_FORMAT = "Pack=%s,Profile=%s";

    public PackProfilController(PackProfilService packProfilService) {
        this.packProfilService = packProfilService;
    }

    // ==================== CRUD ENDPOINTS ====================

    @PostMapping
    public ResponseEntity<ResponseHabil> create(@Valid @RequestBody PackProfilDto packProfilDto) {
        log.info("Creating PackProfil: Pack={}, Profile={}",
                packProfilDto.getCodPackPack(), packProfilDto.getCodPflPfl());

        PackProfilDto created = packProfilService.create(packProfilDto);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new ResponseHabil(0, Constants.CREATED, created));
    }

    @PostMapping("/batch")
    public ResponseEntity<ResponseHabil> createBatch(
            @Valid @RequestBody List<PackProfilDto> packProfils,
            @RequestParam(defaultValue = "ALL_OR_NOTHING") BatchOperationResult.BatchMode mode) {

        log.info("Batch creating {} PackProfils in {} mode", packProfils.size(), mode);

        List<PackProfilDto> successful = new ArrayList<>();
        List<BatchOperationException.BatchError> failed = new ArrayList<>();

        // Check for duplicates in the input batch
        boolean hasDuplicates = checkForDuplicates(packProfils, failed);

        // If duplicates found and mode is ALL_OR_NOTHING, fail immediately
        if (hasDuplicates && mode == BatchOperationResult.BatchMode.ALL_OR_NOTHING) {
            return handleDuplicateFailure(successful, failed, mode);
        }

        // Attempt to create items
        processBatchCreation(packProfils, successful, failed);

        // Construct and return response
        BatchOperationResult<PackProfilDto> result = new BatchOperationResult<>(successful, failed, mode);
        log.info("Batch operation summary: {} successful, {} failed", successful.size(), failed.size());

        return buildResponse(result, successful, failed, mode);
    }

    // Extract duplicate checking logic
    private boolean checkForDuplicates(List<PackProfilDto> packProfils,
                                       List<BatchOperationException.BatchError> failed) {
        Set<String> seenIds = new HashSet<>();
        boolean hasDuplicates = false;

        for (int i = 0; i < packProfils.size(); i++) {
            PackProfilDto pp = packProfils.get(i);
            String compositeKey = String.format("%s-%s", pp.getCodPackPack(), pp.getCodPflPfl());

            if (!seenIds.add(compositeKey)) {
                hasDuplicates = true;
                String identifier = formatPackProfileId(pp);
                failed.add(new BatchOperationException.BatchError(i, identifier, "Duplicate entry in batch"));
                log.warn("Duplicate found in batch at index {}: {}", i, identifier);
            }
        }

        return hasDuplicates;
    }

    // Extract duplicate failure handling
    private ResponseEntity<ResponseHabil> handleDuplicateFailure(
            List<PackProfilDto> successful,
            List<BatchOperationException.BatchError> failed,
            BatchOperationResult.BatchMode mode) {

        log.error("Duplicates found in batch, failing in ALL_OR_NOTHING mode");
        BatchOperationResult<PackProfilDto> result = new BatchOperationResult<>(successful, failed, mode);

        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(new ResponseHabil(1, "DUPLICATE_ENTRIES_IN_BATCH", result));
    }

    // Extract batch creation processing
    private void processBatchCreation(List<PackProfilDto> packProfils,
                                      List<PackProfilDto> successful,
                                      List<BatchOperationException.BatchError> failed) {
        try {
            successful.addAll(packProfilService.createBatch(packProfils));
            log.info("Batch create successful: {} items created", successful.size());

        } catch (BatchOperationException e) {
            log.error("Batch operation failed with errors: {}", e.getMessage());
            failed.addAll(e.getErrors());

        } catch (DuplicateResourceException e) {
            log.error("Duplicate resource exception: {}", e.getMessage());
            addErrorsForAllItems(packProfils, failed, "Already exists in database");

        } catch (ValidationException e) {
            log.error("Validation error: {}", e.getMessage());
            addErrorsForAllItems(packProfils, failed, e.getMessage());

        } catch (Exception e) {
            log.error("Unexpected error during batch operation", e);
            addErrorsForAllItems(packProfils, failed, "Unexpected error: " + e.getMessage());
        }
    }

    // Extract error addition logic
    private void addErrorsForAllItems(List<PackProfilDto> packProfils,
                                      List<BatchOperationException.BatchError> failed,
                                      String errorMessage) {
        for (int i = 0; i < packProfils.size(); i++) {
            String identifier = formatPackProfileId(packProfils.get(i));
            failed.add(new BatchOperationException.BatchError(i, identifier, errorMessage));
        }
    }

    // Extract response building logic
    private ResponseEntity<ResponseHabil> buildResponse(BatchOperationResult<PackProfilDto> result,
                                                        List<PackProfilDto> successful,
                                                        List<BatchOperationException.BatchError> failed,
                                                        BatchOperationResult.BatchMode mode) {
        if (!result.hasFailures()) {
            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .body(new ResponseHabil(
                            0,
                            Constants.CREATED + ": " + successful.size() + " items",
                            result.successful()
                    ));
        }

        if (mode == BatchOperationResult.BatchMode.BEST_EFFORT && !successful.isEmpty()) {
            return ResponseEntity
                    .status(HttpStatus.MULTI_STATUS)
                    .body(new ResponseHabil(
                            207,
                            String.format("%d succeeded, %d failed", successful.size(), failed.size()),
                            result
                    ));
        }

        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(new ResponseHabil(
                        1,
                        String.format("Batch operation failed: %d errors", failed.size()),
                        result
                ));
    }

    // Helper method to format Pack-Profile identifier
    private String formatPackProfileId(PackProfilDto dto) {
        return String.format(PACK_PROFILE_FORMAT, dto.getCodPackPack(), dto.getCodPflPfl());
    }

    @GetMapping
    public ResponseEntity<ResponseHabil> getAll() {
        log.info("Getting all PackProfils");

        List<PackProfilDto> list = packProfilService.findAll();

        return ResponseEntity.ok(new ResponseHabil(0, Constants.SUCCES, list));
    }

    @GetMapping("/by-pack/{codPackPack}")
    public ResponseEntity<ResponseHabil> getByPack(@PathVariable String codPackPack) throws Exception {
        log.info("Getting PackProfils for pack: {}", codPackPack);

        List<PackProfilDto> list = packProfilService.getPackProfilListByPack(codPackPack);

        return ResponseEntity.ok(new ResponseHabil(0, Constants.SUCCES, list));
    }

    @GetMapping("/by-profile/{codPflPfl}")
    public ResponseEntity<ResponseHabil> getByProfile(@PathVariable String codPflPfl) throws Exception {
        log.info("Getting PackProfils for profile: {}", codPflPfl);

        List<PackProfilDto> list = packProfilService.getPackProfilListByProfile(codPflPfl);

        return ResponseEntity.ok(new ResponseHabil(0, Constants.SUCCES, list));
    }

    @GetMapping("/active/{codPackPack}")
    public ResponseEntity<ResponseHabil> getActiveByPack(@PathVariable String codPackPack) throws Exception {
        log.info("Getting active PackProfils for pack: {}", codPackPack);

        List<PackProfilDto> list = packProfilService.getActivePackProfilsByPack(codPackPack);

        return ResponseEntity.ok(new ResponseHabil(0, Constants.SUCCES, list));
    }

    @GetMapping("/by-structure/{codPackPack}/{codTstrcTstrc}")
    public ResponseEntity<ResponseHabil> getByStructureType(
            @PathVariable String codPackPack,
            @PathVariable String codTstrcTstrc) throws Exception {

        log.info("Getting PackProfils for pack: {} and structure type: {}",
                codPackPack, codTstrcTstrc);

        List<PackProfilDto> list = packProfilService
                .getPackProfilsByStructureType(codPackPack, codTstrcTstrc);

        return ResponseEntity.ok(new ResponseHabil(0, Constants.SUCCES, list));
    }

    @PutMapping("/{codPackPack}/{codPflPfl}")
    public ResponseEntity<ResponseHabil> update(
            @PathVariable String codPackPack,
            @PathVariable String codPflPfl,
            @Valid @RequestBody PackProfilDto packProfilDto) {

        log.info("Updating PackProfil: Pack={}, Profile={}", codPackPack, codPflPfl);

        PackProfilId id = new PackProfilId(codPackPack, codPflPfl);
        PackProfilDto updated = packProfilService.update(id, packProfilDto);

        return ResponseEntity.ok(new ResponseHabil(0, Constants.UPDATED, updated));
    }

    @PatchMapping("/{codPackPack}/{codPflPfl}/status")
    public ResponseEntity<ResponseHabil> updateStatus(
            @PathVariable String codPackPack,
            @PathVariable String codPflPfl,
            @RequestParam Integer boolEtat) {

        log.info("Updating PackProfil status: Pack={}, Profile={}, Status={}",
                codPackPack, codPflPfl, boolEtat);

        packProfilService.updatePackProfilStatus(codPackPack, codPflPfl, boolEtat);

        return ResponseEntity.ok(new ResponseHabil(0, Constants.UPDATED, "Status updated successfully"));
    }

    @PutMapping("/{codPackPack}/{codPflPfl}/deactivate")
    public ResponseEntity<ResponseHabil> deactivate(
            @PathVariable String codPackPack,
            @PathVariable String codPflPfl) {

        log.info("Deactivating PackProfil: Pack={}, Profile={}", codPackPack, codPflPfl);

        PackProfilId id = new PackProfilId(codPackPack, codPflPfl);
        PackProfilDto deactivated = packProfilService.deactivatePackProfil(id);

        return ResponseEntity.ok(new ResponseHabil(0, Constants.SUCCES, deactivated));
    }

    @PutMapping("/{codPackPack}/{codPflPfl}/activate")
    public ResponseEntity<ResponseHabil> activate(
            @PathVariable String codPackPack,
            @PathVariable String codPflPfl) {

        log.info("Activating PackProfil: Pack={}, Profile={}", codPackPack, codPflPfl);

        PackProfilId id = new PackProfilId(codPackPack, codPflPfl);
        PackProfilDto activated = packProfilService.activatePackProfil(id);

        return ResponseEntity.ok(new ResponseHabil(0, Constants.SUCCES, activated));
    }

    @DeleteMapping("/{codPackPack}/{codPflPfl}")
    public ResponseEntity<ResponseHabil> delete(
            @PathVariable String codPackPack,
            @PathVariable String codPflPfl) {

        log.info("Permanently deleting PackProfil: Pack={}, Profile={}", codPackPack, codPflPfl);

        PackProfilId id = new PackProfilId(codPackPack, codPflPfl);
        packProfilService.delete(id);

        return ResponseEntity.ok(new ResponseHabil(0, Constants.SUCCES, "Supprimé définitivement"));
    }

    @DeleteMapping("/batch/{codPackPack}")
    public ResponseEntity<ResponseHabil> deleteMultipleFromPack(
            @PathVariable String codPackPack,
            @RequestBody List<String> profilCodes) {

        log.info("Deleting {} profiles from pack: {}", profilCodes.size(), codPackPack);

        packProfilService.deleteProfilsFromPack(codPackPack, profilCodes);

        return ResponseEntity.ok(new ResponseHabil(0, Constants.SUCCES,
                String.format("%d profiles deleted from pack", profilCodes.size())));
    }


    @PutMapping("/{codPackPack}/sync")
    public ResponseEntity<ResponseHabil> syncPackProfiles(@PathVariable String codPackPack) {

        log.info("Auto-syncing pack profiles and users for pack: {}", codPackPack);

        try {
            SyncResult result = packProfilService.autoSyncPackProfiles(codPackPack);

            return ResponseEntity.ok(new ResponseHabil(
                    0,
                    String.format("Pack profiles synced: %d users updated, %d profiles added, %d profiles removed",
                            result.getUsersUpdated(),
                            result.getProfilesAdded(),
                            result.getProfilesRemoved()),
                    result
            ));

        } catch (EntityNotFoundException e) {
            log.error("Pack not found: {}", codPackPack);
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(new ResponseHabil(1, "Pack not found", null));

        } catch (Exception e) {
            log.error("Error syncing pack profiles", e);
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseHabil(1, "Error syncing profiles: " + e.getMessage(), null));
        }
    }

    @GetMapping("/count/{codPackPack}")
    public ResponseEntity<ResponseHabil> countProfilesInPack(@PathVariable String codPackPack) {
        log.info("Counting profiles in pack: {}", codPackPack);

        int count = packProfilService.countProfilesInPack(codPackPack);

        return ResponseEntity.ok(new ResponseHabil(0, Constants.SUCCES,
                Map.of("codPackPack", codPackPack, "count", count)));
    }

    @GetMapping("/exists/{codPackPack}/{codPflPfl}")
    public ResponseEntity<ResponseHabil> checkExists(
            @PathVariable String codPackPack,
            @PathVariable String codPflPfl) {

        log.info("Checking if profile {} exists in pack {}", codPflPfl, codPackPack);

        boolean exists = packProfilService.isProfileInPack(codPackPack, codPflPfl);

        return ResponseEntity.ok(new ResponseHabil(0, Constants.SUCCES,
                Map.of("exists", exists)));
    }
//
//    @GetMapping("/{codPackPack}/conflicts")
//    public ResponseEntity<ResponseHabil> getConflicts(
//            @PathVariable String codPackPack) {
//        List<ProfileConflictDto> conflicts = packProfilService.detectProfileConflicts(codPackPack);
//        return ResponseEntity.ok(new ResponseHabil(0, Constants.SUCCES,conflicts ));
//    }
}