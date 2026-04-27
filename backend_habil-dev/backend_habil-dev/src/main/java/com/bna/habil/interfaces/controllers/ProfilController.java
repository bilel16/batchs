package com.bna.habil.interfaces.controllers;

import java.util.List;

import com.bna.habil.infrastructure.security.model.ResponseHabil;
import jakarta.validation.Valid;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.bna.habil.application.dto.ProfilDto;
import com.bna.habil.application.services.ProfilService;
import com.bna.habil.domain.entities.Profil;
import com.bna.habil.infrastructure.utils.Constants;


@RestController
@RequestMapping("/profils")
@Slf4j
@Validated
public class ProfilController {

    private final ProfilService profilService;

    public ProfilController(ProfilService profilService) {
        this.profilService = profilService;
    }

    // ==================== CRUD ENDPOINTS ====================

    @PostMapping
    public ResponseEntity<ResponseHabil> createProfil(@Valid @RequestBody ProfilDto profilDto) {
        log.info("Creating profil: {}", profilDto.getCodPflPfl());

        ProfilDto created = profilService.create(profilDto);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new ResponseHabil(0, Constants.CREATED, created));
    }

    @GetMapping
    public ResponseEntity<ResponseHabil> getAllProfils() {
        log.info("Getting all profils");

        List<ProfilDto> profils = profilService.findAll();

        return ResponseEntity.ok(new ResponseHabil(0, Constants.SUCCES, profils));
    }

    @GetMapping("/{codPflPfl}")
    public ResponseEntity<ResponseHabil> getProfilById(@PathVariable String codPflPfl) {
        log.info("Getting profil by code: {}", codPflPfl);

        ProfilDto profil = profilService.findById(codPflPfl);

        return ResponseEntity.ok(new ResponseHabil(0, Constants.SUCCES, profil));
    }

    @PutMapping("/{codPflPfl}")
    public ResponseEntity<ResponseHabil> updateProfil(
            @PathVariable String codPflPfl,
            @Valid @RequestBody ProfilDto profilDto) {

        log.info("Updating profil: {}", codPflPfl);

        ProfilDto updated = profilService.update(codPflPfl, profilDto);

        return ResponseEntity.ok(new ResponseHabil(0, Constants.UPDATED, updated));
    }

    @DeleteMapping("/{codPflPfl}")
    public ResponseEntity<ResponseHabil> deleteProfil(@PathVariable String codPflPfl) {
        log.info("Deleting profil: {}", codPflPfl);

        profilService.delete(codPflPfl);

        return ResponseEntity.ok(new ResponseHabil(0, Constants.SUCCES, "Supprimé avec succès"));
    }
    // ==================== QUERY ENDPOINTS ====================

    @GetMapping("/by-application/{codAppApp}")
    public ResponseEntity<ResponseHabil> getProfilsByApplication(@PathVariable String codAppApp) throws Exception {
        log.info("Getting profils for application: {}", codAppApp);

        List<Profil> profils = profilService.getProfilByCodApp(codAppApp);

        return ResponseEntity.ok(new ResponseHabil(0, Constants.SUCCES, profils));
    }

    @GetMapping("/manager/{codAppApp}")
    public ResponseEntity<ResponseHabil> getManagerProfils(@PathVariable String codAppApp) throws Exception {
        log.info("Getting manager profils for application: {}", codAppApp);

        List<ProfilDto> profils = profilService.getManagerProfiles(codAppApp);

        return ResponseEntity.ok(new ResponseHabil(0, Constants.SUCCES, profils));
    }

    @GetMapping("/available-for-user/{codAppApp}/{targetUserMat}")
    public ResponseEntity<ResponseHabil> getAvailableProfilsForUser(
            @PathVariable String codAppApp,
            @PathVariable String targetUserMat) throws Exception {

        log.info("Getting available profils for user {} in app {}", targetUserMat, codAppApp);

        List<ProfilDto> profils = profilService.getAvailableProfilesForUser(codAppApp, targetUserMat);

        return ResponseEntity.ok(new ResponseHabil(0, Constants.SUCCES, profils));
    }

    @GetMapping("/not-assigned/{codAppApp}/{targetUserMat}")
    public ResponseEntity<ResponseHabil> getNotAssignedProfilsForUser(
            @PathVariable String codAppApp,
            @PathVariable String targetUserMat) throws Exception {

        log.info("Getting not-assigned profils for user {} in app {}", targetUserMat, codAppApp);

        List<ProfilDto> profils = profilService.getAvailableProfilesForUserNotAssgined(codAppApp, targetUserMat);

        return ResponseEntity.ok(new ResponseHabil(0, Constants.SUCCES, profils));
    }

    @GetMapping("/getProfilsByStructure/{structureID}")
    public ResponseEntity<ResponseHabil> getProfilesByStructureId(@PathVariable Integer structureID) {

        log.info("Getting profils for Structure {}", structureID);

        List<ProfilDto> profils = profilService.getProfilesByStructureId(structureID);
        return ResponseEntity.ok(new ResponseHabil(0, Constants.SUCCES, profils));
    }

}