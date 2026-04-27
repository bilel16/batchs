package com.bna.habil.interfaces.controllers;

import com.bna.habil.application.dto.UserRoleDTO;
import com.bna.habil.application.services.ProfilMenuApplicationService;
import com.bna.habil.application.services.crud.model.BatchOperationResult;
import com.bna.habil.domain.beans.ProfilMenuApplicationBean;
import com.bna.habil.domain.entities.entitiesId.ProfilMenuApplicationId;
import com.bna.habil.domain.entities.extra.RoleUpdateRequest;
import com.bna.habil.domain.exceptions.BatchOperationException;
import com.bna.habil.domain.exceptions.DuplicateResourceException;
import com.bna.habil.domain.exceptions.ValidationException;
import com.bna.habil.infrastructure.security.model.ResponseHabil;
import com.bna.habil.infrastructure.utils.Constants;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * REST Controller for ProfilMenuApplication management
 */
@RestController
@RequestMapping("/profil-menu-applications")
@Slf4j
@Validated
public class ProfilMenuApplicationController {

    private final ProfilMenuApplicationService profilAppService;

    public ProfilMenuApplicationController(ProfilMenuApplicationService profilAppService) {
        this.profilAppService = profilAppService;
    }

    // ==================== USER ROLE ENDPOINTS ====================

    @GetMapping("/user/{matricule}/app/{appCode}/roles")
    public ResponseEntity<ResponseHabil> getUserRoles(
            @PathVariable String matricule,
            @PathVariable String appCode) throws Exception {

        log.info("Getting roles for user {} in app {}", matricule, appCode);

        List<UserRoleDTO> roles = profilAppService.getUserRolesForApplication(matricule, appCode);

        return ResponseEntity.ok(new ResponseHabil(0, Constants.SUCCES, roles));
    }

    @PostMapping("/user/{matricule}/app/{appCode}/roles")
    public ResponseEntity<ResponseHabil> saveUserRoles(
            @PathVariable String matricule,
            @PathVariable String appCode,
            @Valid @RequestBody RoleUpdateRequest request) throws Exception {

        log.info("Saving roles for user {} in app {}", matricule, appCode);

        if (request == null || request.getRoles() == null) {
            throw new IllegalArgumentException("Roles cannot be null");
        }

        profilAppService.saveUserRoles(matricule, appCode, request.getRoles());

        // Return updated list
        List<UserRoleDTO> roles = profilAppService.getUserRolesForApplication(matricule, appCode);

        return ResponseEntity.ok(new ResponseHabil(0, Constants.SUCCES, roles));
    }

    // ==================== CRUD ENDPOINTS ====================

    @PostMapping
    public ResponseEntity<ResponseHabil> create(
            @Valid @RequestBody ProfilMenuApplicationBean profilMenuApplication) {

        log.info("Creating ProfilMenuApplication: {}", profilMenuApplication);

        ProfilMenuApplicationBean created = profilAppService.create(profilMenuApplication);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new ResponseHabil(0, Constants.CREATED, created));
    }

    @PostMapping("/batch")
    public ResponseEntity<ResponseHabil> createBatch(
            @Valid @RequestBody List<ProfilMenuApplicationBean> profilMenuApplications,
            @RequestParam(defaultValue = "ALL_OR_NOTHING") BatchOperationResult.BatchMode mode) {

        log.info("Batch creating {} ProfilMenuApplications in {} mode",
                profilMenuApplications.size(), mode);

        List<ProfilMenuApplicationBean> successful = new ArrayList<>();
        List<BatchOperationException.BatchError> failed = new ArrayList<>();

        boolean hasDuplicates = detectBatchDuplicates(profilMenuApplications, failed);

        if (hasDuplicates && mode == BatchOperationResult.BatchMode.ALL_OR_NOTHING) {
            return buildDuplicateConflictResponse(successful, failed, mode);
        }

        executeBatch(profilMenuApplications, successful, failed);

        BatchOperationResult<ProfilMenuApplicationBean> result =
                new BatchOperationResult<>(successful, failed, mode);

        log.info("Batch operation summary: {} successful, {} failed",
                successful.size(), failed.size());

        return buildBatchResponse(result);
    }


    @GetMapping
    public ResponseEntity<ResponseHabil> getAll() {
        log.info("Getting all ProfilMenuApplications");

        List<ProfilMenuApplicationBean> list = profilAppService.findAll();

        return ResponseEntity.ok(new ResponseHabil(0, Constants.SUCCES, list));
    }

    @GetMapping("/by-application/{codAppApp}")
    public ResponseEntity<ResponseHabil> getByApplication(@PathVariable String codAppApp) throws Exception {
        log.info("Getting ProfilMenuApplications for application: {}", codAppApp);

        List<ProfilMenuApplicationBean> list = profilAppService.getProfApplicationListBycodAppApp(codAppApp);

        return ResponseEntity.ok(new ResponseHabil(0, Constants.SUCCES, list));
    }

    @PutMapping("/{codApp}/{codMenu}/{codPfl}/{codTstrc}")
    public ResponseEntity<ResponseHabil> update(
            @PathVariable String codApp,
            @PathVariable String codMenu,
            @PathVariable String codPfl,
            @PathVariable String codTstrc,
            @Valid @RequestBody ProfilMenuApplicationBean profilMenuApplication) {

        log.info("Updating ProfilMenuApplication: App={}, Menu={}, Profil={}, Tstrc={}",
                codApp, codMenu, codPfl, codTstrc);

        ProfilMenuApplicationId id = new ProfilMenuApplicationId(codApp, codMenu, codPfl, codTstrc);
        ProfilMenuApplicationBean updated = profilAppService.update(id, profilMenuApplication);

        return ResponseEntity.ok(new ResponseHabil(0, Constants.UPDATED, updated));
    }

    @DeleteMapping("/{codApp}/{codMenu}/{codPfl}/{codTstrc}")
    public ResponseEntity<ResponseHabil> delete(
            @PathVariable String codApp,
            @PathVariable String codMenu,
            @PathVariable String codPfl,
            @PathVariable String codTstrc) {

        log.info("Deleting ProfilMenuApplication: App={}, Menu={}, Profil={}, Tstrc={}",
                codApp, codMenu, codPfl, codTstrc);

        ProfilMenuApplicationId id = new ProfilMenuApplicationId(codApp, codMenu, codPfl, codTstrc);
        profilAppService.delete(id);

        return ResponseEntity.ok(new ResponseHabil(0, Constants.SUCCES, "Supprimé avec succès"));
    }

    private boolean detectBatchDuplicates(
            List<ProfilMenuApplicationBean> items,
            List<BatchOperationException.BatchError> failed) {

        Set<String> seenIds = new HashSet<>();
        boolean hasDuplicates = false;

        for (int i = 0; i < items.size(); i++) {
            ProfilMenuApplicationBean pma = items.get(i);
            String key = buildCompositeKey(pma);

            if (!seenIds.add(key)) {
                hasDuplicates = true;

                failed.add(new BatchOperationException.BatchError(
                        i,
                        buildIdentifier(pma),
                        "Duplicate entry in batch"
                ));

                log.warn("Duplicate found in batch at index {}: {}", i, buildIdentifier(pma));
            }
        }

        return hasDuplicates;
    }

    private void executeBatch(
            List<ProfilMenuApplicationBean> items,
            List<ProfilMenuApplicationBean> successful,
            List<BatchOperationException.BatchError> failed) {

        try {
            successful.addAll(profilAppService.createBatch(items));
            log.info("Batch create successful: {} items created", successful.size());

        } catch (BatchOperationException e) {
            log.error("Batch operation failed with errors: {}", e.getMessage());
            failed.addAll(e.getErrors());

        } catch (DuplicateResourceException e) {
            log.error("Duplicate resource exception: {}", e.getMessage());
            addFailureForAll(items, failed, "Already exists in database");

        } catch (ValidationException e) {
            log.error("Validation error: {}", e.getMessage());
            addFailureForAll(items, failed, e.getMessage());

        } catch (Exception e) {
            log.error("Unexpected error during batch operation", e);
            addFailureForAll(items, failed, "Unexpected error: " + e.getMessage());
        }
    }

    private void addFailureForAll(
            List<ProfilMenuApplicationBean> items,
            List<BatchOperationException.BatchError> failed,
            String message) {

        for (int i = 0; i < items.size(); i++) {
            failed.add(new BatchOperationException.BatchError(
                    i,
                    buildIdentifier(items.get(i)),
                    message
            ));
        }
    }


    private ResponseEntity<ResponseHabil> buildBatchResponse(
            BatchOperationResult<ProfilMenuApplicationBean> result) {

        if (result.hasFailures()) {
            if (result.mode() == BatchOperationResult.BatchMode.BEST_EFFORT
                    && !result.successful().isEmpty()) {

                return ResponseEntity.status(HttpStatus.MULTI_STATUS)
                        .body(new ResponseHabil(
                                207,
                                String.format("%d succeeded, %d failed",
                                        result.successful().size(),
                                        result.failed().size()),
                                result
                        ));
            }

            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(new ResponseHabil(
                            1,
                            String.format("Batch operation failed: %d errors",
                                    result.failed().size()),
                            result
                    ));
        }

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ResponseHabil(
                        0,
                        Constants.CREATED + ": " + result.successful().size() + " items",
                        result.successful()
                ));
    }

    private String buildCompositeKey(ProfilMenuApplicationBean pma) {
        return String.format("%s-%s-%s-%s",
                pma.getCodAppApp(),
                pma.getCodMenuMenu(),
                pma.getCodPflPfl(),
                pma.getCodTstrcTstrc());
    }

    private String buildIdentifier(ProfilMenuApplicationBean pma) {
        return String.format("App=%s,Menu=%s,Profil=%s,Tstrc=%s",
                pma.getCodAppApp(),
                pma.getCodMenuMenu(),
                pma.getCodPflPfl(),
                pma.getCodTstrcTstrc());
    }

    private ResponseEntity<ResponseHabil> buildDuplicateConflictResponse(
            List<ProfilMenuApplicationBean> successful,
            List<BatchOperationException.BatchError> failed,
            BatchOperationResult.BatchMode mode) {

        BatchOperationResult<ProfilMenuApplicationBean> result =
                new BatchOperationResult<>(successful, failed, mode);

        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(new ResponseHabil(1, "DUPLICATE_ENTRIES_IN_BATCH", result));
    }

}