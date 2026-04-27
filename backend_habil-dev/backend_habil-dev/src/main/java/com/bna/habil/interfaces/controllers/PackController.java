package com.bna.habil.interfaces.controllers;

import com.bna.habil.application.dto.PackDto;
import com.bna.habil.application.services.PackService;
import com.bna.habil.infrastructure.security.model.ResponseHabil;
import com.bna.habil.infrastructure.utils.Constants;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/pack")
@Slf4j
@Validated
public class PackController {

    private final PackService packService;

    public PackController(PackService packService) {
        this.packService = packService;
    }

    // ==================== CRUD ENDPOINTS ====================

    @PostMapping
    public ResponseEntity<ResponseHabil> createPack(@Valid @RequestBody PackDto packDto) {
        log.info("Creating pack: {}", packDto.getCodPackPack());

        PackDto created = packService.create(packDto);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new ResponseHabil(0, Constants.CREATED, created));
    }

    @GetMapping
    public ResponseEntity<ResponseHabil> getAllPacks() {
        log.info("Getting all packs");

        List<PackDto> packs = packService.findAll();

        return ResponseEntity.ok(new ResponseHabil(0, Constants.SUCCES, packs));
    }

    @GetMapping("/{codPackPack}")
    public ResponseEntity<ResponseHabil> getPackById(@PathVariable String codPackPack) {
        log.info("Getting pack by code: {}", codPackPack);

        PackDto pack = packService.findById(codPackPack);

        return ResponseEntity.ok(new ResponseHabil(0, Constants.SUCCES, pack));
    }

    @PutMapping("/{codPackPack}")
    public ResponseEntity<ResponseHabil> updatePack(
            @PathVariable String codPackPack,
            @Valid @RequestBody PackDto packDto) {

        log.info("Updating pack: {}", codPackPack);

        PackDto updated = packService.update(codPackPack, packDto);

        return ResponseEntity.ok(new ResponseHabil(0, Constants.UPDATED, updated));
    }

    @DeleteMapping("/{codPackPack}")
    public ResponseEntity<ResponseHabil> deletePack(@PathVariable String codPackPack) {
        log.info("Deleting pack: {}", codPackPack);

        packService.delete(codPackPack);

        return ResponseEntity.ok(new ResponseHabil(0, Constants.SUCCES, "Supprimé avec succès"));
    }

    // ==================== QUERY ENDPOINTS ====================

    @GetMapping("/active")
    public ResponseEntity<ResponseHabil> getActivePacks() {
        log.info("Getting all active packs");

        List<PackDto> packs = packService.getActivePacks();

        return ResponseEntity.ok(new ResponseHabil(0, Constants.SUCCES, packs));
    }

    @GetMapping("/by-niveau/{codNivhPfl}")
    public ResponseEntity<ResponseHabil> getPacksByNiveau(@PathVariable String codNivhPfl) {
        log.info("Getting packs by hierarchical level: {}", codNivhPfl);

        List<PackDto> packs = packService.getPacksByNiveauHierarchique(codNivhPfl);

        return ResponseEntity.ok(new ResponseHabil(0, Constants.SUCCES, packs));
    }

    @GetMapping("/by-categorie/{codCatpPfl}")
    public ResponseEntity<ResponseHabil> getPacksByCategorie(@PathVariable String codCatpPfl) {
        log.info("Getting packs by category: {}", codCatpPfl);

        List<PackDto> packs = packService.getPacksByCategorie(codCatpPfl);

        return ResponseEntity.ok(new ResponseHabil(0, Constants.SUCCES, packs));
    }

    @GetMapping("/manager")
    public ResponseEntity<ResponseHabil> getManagerPacks() throws Exception {
        log.info("Getting manager packs for application");

        List<PackDto> packs = packService.getManagerPacks();

        return ResponseEntity.ok(new ResponseHabil(0, Constants.SUCCES, packs));
    }

    @GetMapping("/available-for-user/{targetUserMat}")
    public ResponseEntity<ResponseHabil> getAvailablePacksForUser(@PathVariable String targetUserMat) throws Exception {

        log.info("Getting available packs for user {}", targetUserMat);

        List<PackDto> packs = packService.getAvailablePacksForUser(targetUserMat);

        return ResponseEntity.ok(new ResponseHabil(0, Constants.SUCCES, packs));
    }

    @GetMapping("/not-assigned/{targetUserMat}")
    public ResponseEntity<ResponseHabil> getNotAssignedPacksForUser(
            @PathVariable String targetUserMat) throws Exception {

        log.info("Getting not-assigned packs for user {}", targetUserMat);

        List<PackDto> packs = packService.getAvailablePacksForUserNotAssigned(targetUserMat);

        return ResponseEntity.ok(new ResponseHabil(0, Constants.SUCCES, packs));
    }
}