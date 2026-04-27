package com.bna.habil.interfaces.controllers;

import java.util.List;

import com.bna.habil.domain.entities.entitiesId.UtilisateurProfilId;
import com.bna.habil.infrastructure.security.model.ResponseHabil;
import lombok.extern.slf4j.Slf4j;


import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.bna.habil.application.dto.AssignProfileRequest;
import com.bna.habil.application.dto.UtilisateurProfilDTO;

import com.bna.habil.application.services.UtilisateurProfilService;
import com.bna.habil.application.services.impl.ProfilMenuApplicationServiceImpl;
import com.bna.habil.domain.beans.UtilisateurProfilBean;

import com.bna.habil.infrastructure.utils.Constants;
import com.bna.habil.infrastructure.utils.ExceptionUtils;

import jakarta.validation.Valid;

/**
 * REST Controller for UtilisateurProfil management
 */
@RestController
@RequestMapping("/utilisateur-profils")
@Slf4j
@Validated
public class UtilisateurProfilController {

    private final UtilisateurProfilService utilisateurProfilService;

    private final ProfilMenuApplicationServiceImpl profilMenuApplicationServiceImpl;

    public UtilisateurProfilController(UtilisateurProfilService utilisateurProfilService, ProfilMenuApplicationServiceImpl profilMenuApplicationServiceImpl) {
        this.utilisateurProfilService = utilisateurProfilService;
        this.profilMenuApplicationServiceImpl = profilMenuApplicationServiceImpl;
    }
    // ==================== CRUD OPERATIONS ====================

    @PostMapping
    public ResponseEntity<ResponseHabil> create(@Valid @RequestBody UtilisateurProfilBean userProfil) {
        log.info("Creating UtilisateurProfil for user {} and profile {}",
                userProfil.getNumMatrUser(), userProfil.getCodPflPfl());

        UtilisateurProfilBean created = utilisateurProfilService.create(userProfil);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new ResponseHabil(0, Constants.CREATED, created));
    }

    @PutMapping("/{codPfl}/{numMatr}")
    public ResponseEntity<ResponseHabil> update(
            @PathVariable String codPfl,
            @PathVariable String numMatr,
            @Valid @RequestBody UtilisateurProfilBean userProfil) {

        log.info("Updating UtilisateurProfil: Profile={}, User={}", codPfl, numMatr);

        UtilisateurProfilId id = new UtilisateurProfilId(codPfl, numMatr);
        UtilisateurProfilBean updated = utilisateurProfilService.update(id, userProfil);

        return ResponseEntity.ok(new ResponseHabil(0, Constants.UPDATED, updated));
    }

    @DeleteMapping("/{codPfl}/{numMatr}")
    public ResponseEntity<ResponseHabil> delete(
            @PathVariable String codPfl,
            @PathVariable String numMatr) {

        log.info("Deleting UtilisateurProfil: Profile={}, User={}", codPfl, numMatr);

        UtilisateurProfilId id = new UtilisateurProfilId(codPfl, numMatr);
        utilisateurProfilService.delete(id);

        return ResponseEntity.ok(new ResponseHabil(0, Constants.SUCCES, "Supprimé avec succès"));
    }

    // ==================== QUERY OPERATIONS ====================

    @GetMapping(value = "/by-application/{codApp}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseHabil> getByApplication(@PathVariable String codApp) throws Exception {
        log.info("Getting UtilisateurProfils for application: {}", codApp);

        List<UtilisateurProfilBean> list = utilisateurProfilService.getListUserProfil(codApp);

        return ResponseEntity.ok(new ResponseHabil(0, Constants.SUCCES, list));
    }

    @GetMapping(value = "/detailed", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseHabil> getDetailedList(
            @RequestParam String cdp,
            @RequestParam(required = false) String matricule) throws Exception {

        log.info("Getting detailed UtilisateurProfils for app {} and user {}",
                cdp, matricule);

        List<UtilisateurProfilDTO> data = utilisateurProfilService.getListUserProfil(cdp, matricule);

        return ResponseEntity.ok(new ResponseHabil(0, Constants.SUCCES, data));
    }

    @GetMapping("/user/{matricule}/applications")
    public ResponseEntity<ResponseHabil> getApplicationsByMatricule(@PathVariable String matricule) throws Exception {
        log.info("Getting applications for user: {}", matricule);

        List<String> applications = utilisateurProfilService.getApplicationsByMatricule(matricule);

        return ResponseEntity.ok(new ResponseHabil(0, Constants.SUCCES, applications));
    }

    /**
     * Assign a profile to a user under structure-aware rules.
     */
    @PostMapping("/assign")
    public ResponseHabil assignProfile(@RequestBody AssignProfileRequest request) {
        try {

            utilisateurProfilService.assignProfile(
                    request.getManagerMatricule(),
                    request.getUserMatricule(),
                    request.getProfileCode(),
                    request.getAppCode()
            );
            return new ResponseHabil(
                    0,
                    Constants.CREATED,
                    "✅ Profil " + request.getProfileCode()
                            + " assigné avec succès à l'utilisateur "
                            + request.getUserMatricule()
            );
        } catch (Exception e) {
            log.error("ajoutUserProfil : {}", e.getMessage());
            return ExceptionUtils.handleException(e);
        }
    }

    // (optional) Endpoint to preview assignable profiles for a manager
    @GetMapping("/assignable-profiles")
    public ResponseHabil getAssignableProfiles(
            @RequestParam String managerMatricule,
            @RequestParam String appCode) {
        try {
            profilMenuApplicationServiceImpl.getAssignableProfiles(managerMatricule, appCode);
            return (new ResponseHabil(0, Constants.CREATED, profilMenuApplicationServiceImpl.getAssignableProfiles(managerMatricule, appCode)));

        } catch (Exception e) {
            log.error("Exception : {}", e.getMessage());
            return ExceptionUtils.handleException(e);
        }
    }

    @PostMapping(value = "/updateUtilisateurProfil")
    public ResponseHabil updateUtilisateurProfil(@Valid @RequestBody UtilisateurProfilBean utilisateurProfil) {
        try {
            log.info("updateUtilisateurProfil: input : {}", utilisateurProfil.toString());

            utilisateurProfilService.update(new UtilisateurProfilId(utilisateurProfil.getCodPflPfl(), utilisateurProfil.getNumMatrUser()), utilisateurProfil);
            log.info("updateUtilisateurProfil : output : updateUtilisateurProfil terminée");
            return (new ResponseHabil(0, Constants.UPDATED, null));
        } catch (Exception e) {
            log.error("updateUtilisateurProfil : {}", e.getMessage());
            return ExceptionUtils.handleException(e);
        }
    }

    @DeleteMapping(value = "/deleteUtilisateurProfil/{codPflPfl}/{numMatrUser}")
    public ResponseHabil deleteUtilisateurProfil(@PathVariable("codPflPfl") String codPflPfl,
                                                 @PathVariable("numMatrUser") String numMatrUser) {
        try {
            utilisateurProfilService.delete(new UtilisateurProfilId(codPflPfl, numMatrUser));
            log.info("deleteUtilisateurProfil : output : deleteUtilisateurProfil terminée");
            return (new ResponseHabil(0, Constants.UPDATED, null));
        } catch (Exception e) {
            log.error("deleteUtilisateurProfil : {}", e.getMessage());
            return ExceptionUtils.handleException(e);
        }
    }

}
