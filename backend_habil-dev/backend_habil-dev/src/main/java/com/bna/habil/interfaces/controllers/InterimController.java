package com.bna.habil.interfaces.controllers;
import com.bna.habil.application.dto.InterimDetailsDto;
import com.bna.habil.application.dto.InterimDto;
import com.bna.habil.application.dto.statistics.InterimStatsDto;
import com.bna.habil.application.mappers.InterimMapper;
import com.bna.habil.application.services.impl.interim.InterimServiceImpl;
import com.bna.habil.domain.beans.interim.EtatInterim;
import com.bna.habil.domain.entities.interim.Interim;
import com.bna.habil.domain.entities.interim.InterimProfilBackup;
import com.bna.habil.domain.entities.interim.InterimProfilGranted;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;


@RestController
@RequestMapping("/api/interims")
@RequiredArgsConstructor
@Slf4j
public class InterimController {

    private final InterimServiceImpl interimService;
    private final InterimMapper interimMapper;

    // ═══════════════════════════════════════════════════════════════
    //                        CRUD
    // ═══════════════════════════════════════════════════════════════

    /**
     * POST /api/interims
     * Create a new interim
     */
    @PostMapping
    public ResponseEntity<InterimDto> createInterim(
            @Valid @RequestBody InterimDto dto) {

        log.info("POST /api/interims — create interim for cible {}",
                dto.getMatriculeCible());

        Interim interim = interimMapper.toEntity(dto);
        Interim created = interimService.createInterim(interim);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(interimMapper.toDto(created));
    }

    /**
     * PUT /api/interims/{id}
     * Update an interim (only EN_ATTENTE)
     */
    @PutMapping("/{id}")
    public ResponseEntity<InterimDto> updateInterim(
            @PathVariable Long id,
            @Valid @RequestBody InterimDto dto) {

        log.info("PUT /api/interims/{} — update interim", id);

        Interim updated = interimService.updateInterim(id, dto);

        return ResponseEntity.ok(interimMapper.toDto(updated));
    }

    /**
     * GET /api/interims/{id}
     * Get a specific interim by ID
     */
    @GetMapping("/{id}")
    public ResponseEntity<InterimDto> getInterimById(@PathVariable Long id) {

        log.info("GET /api/interims/{}", id);

        Interim interim = interimService.getInterimById(id);

        return ResponseEntity.ok(interimMapper.toDto(interim));
    }

    @GetMapping
    public ResponseEntity<List<InterimDetailsDto>> getAllInterims() {
        return ResponseEntity.ok(interimService.getAllInterims());
    }

    @GetMapping("/search")
    public ResponseEntity<List<InterimDetailsDto>> searchInterims(
            @RequestParam(required = false) Integer matriculeSource,
            @RequestParam(required = false) Integer matriculeCible,
            @RequestParam(required = false) EtatInterim etat,
            @RequestParam(required = false) Integer codStrc,
            @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date dateDebut,
            @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date dateFin) {

        return ResponseEntity.ok(interimService.searchInterims(matriculeSource, matriculeCible, etat, codStrc, dateDebut, dateFin));
    }

    // ═══════════════════════════════════════════════════════════════
    //                      ACTIONS
    // ═══════════════════════════════════════════════════════════════

    /**
     * PATCH /api/interims/{id}/cancel
     * Cancel an interim (EN_ATTENTE or ACTIF only)
     */
    @PatchMapping("/{id}/cancel")
    public ResponseEntity<InterimDto> cancelInterim(@PathVariable Long id) {

        log.info("PATCH /api/interims/{}/cancel", id);

        Interim cancelled = interimService.cancelInterim(id);

        return ResponseEntity.ok(interimMapper.toDto(cancelled));
    }

    // ═══════════════════════════════════════════════════════════════
    //                      SEARCH & FILTER
    // ═══════════════════════════════════════════════════════════════


    /**
     * GET /api/interims/by-source/{matricule}
     * All interims where this user is being replaced
     */
    @GetMapping("/by-source/{matricule}")
    public ResponseEntity<List<InterimDto>> getBySource(
            @PathVariable Integer matricule) {

        log.info("GET /api/interims/by-source/{}", matricule);

        List<InterimDto> dtos = interimService.getInterimsBySource(matricule)
                .stream()
                .map(interimMapper::toDto)
                .toList();

        return ResponseEntity.ok(dtos);
    }

    /**
     * GET /api/interims/by-cible/{matricule}
     * All interims where this user is the replacement
     */
    @GetMapping("/by-cible/{matricule}")
    public ResponseEntity<List<InterimDto>> getByCible(
            @PathVariable Integer matricule) {

        log.info("GET /api/interims/by-cible/{}", matricule);

        List<InterimDto> dtos = interimService.getInterimsByCible(matricule)
                .stream()
                .map(interimMapper::toDto)
                .toList();

        return ResponseEntity.ok(dtos);
    }

    /**
     * GET /api/interims/by-user/{matricule}
     * All interims involving this user (as source OR cible)
     */
    @GetMapping("/by-user/{matricule}")
    public ResponseEntity<List<InterimDto>> getByUser(
            @PathVariable Integer matricule) {

        log.info("GET /api/interims/by-user/{}", matricule);

        List<InterimDto> dtos = interimService.getInterimsByUser(matricule)
                .stream()
                .map(interimMapper::toDto)
                .toList();

        return ResponseEntity.ok(dtos);
    }

    /**
     * GET /api/interims/by-state/{etat}
     * All interims with a specific state
     */
    @GetMapping("/by-state/{etat}")
    public ResponseEntity<List<InterimDto>> getByState(
            @PathVariable EtatInterim etat) {

        log.info("GET /api/interims/by-state/{}", etat);

        List<InterimDto> dtos = interimService.getInterimsByState(etat)
                .stream()
                .map(interimMapper::toDto)
                .toList();

        return ResponseEntity.ok(dtos);
    }

    /**
     * GET /api/interims/by-structure/{codStrc}
     * All interims for a specific destination structure
     */
    @GetMapping("/by-structure/{codStrc}")
    public ResponseEntity<List<InterimDto>> getByStructure(
            @PathVariable Integer codStrc) {

        log.info("GET /api/interims/by-structure/{}", codStrc);

        List<InterimDto> dtos = interimService.getInterimsByStructure(codStrc)
                .stream()
                .map(interimMapper::toDto)
                .toList();

        return ResponseEntity.ok(dtos);
    }

    // ═══════════════════════════════════════════════════════════════
    //                   ACTIVE INTERIM CHECKS
    // ═══════════════════════════════════════════════════════════════

    /**
     * GET /api/interims/active/cible/{matricule}
     * Get active interim for a specific replacing user
     */
    @GetMapping("/active/cible/{matricule}")
    public ResponseEntity<InterimDto> getActiveForCible(
            @PathVariable Integer matricule) {

        log.info("GET /api/interims/active/cible/{}", matricule);

        return interimService.getActiveInterimForCible(matricule)
                .map(interimMapper::toDto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.noContent().build());
    }

    /**
     * GET /api/interims/active/source/{matricule}
     * Get active interim for a specific replaced user
     */
    @GetMapping("/active/source/{matricule}")
    public ResponseEntity<InterimDto> getActiveForSource(
            @PathVariable Integer matricule) {

        log.info("GET /api/interims/active/source/{}", matricule);

        return interimService.getActiveInterimForSource(matricule)
                .map(interimMapper::toDto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.noContent().build());
    }

    // ═══════════════════════════════════════════════════════════════
    //                     STATISTICS
    // ═══════════════════════════════════════════════════════════════

    /**
     * GET /api/interims/stats
     * Get interim statistics by state
     */
    @GetMapping("/stats")
    public ResponseEntity<InterimStatsDto> getStatistics() {

        log.info("GET /api/interims/stats");

        return ResponseEntity.ok(interimService.getStatistics());
    }

    // ═══════════════════════════════════════════════════════════════
    //                   INTERIM DETAILS
    // ═══════════════════════════════════════════════════════════════

    /**
     * GET /api/interims/{id}/granted-profiles
     * See which profiles were granted during this interim
     */
    @GetMapping("/{id}/granted-profiles")
    public ResponseEntity<List<InterimProfilGranted>> getGrantedProfiles(
            @PathVariable Long id) {

        log.info("GET /api/interims/{}/granted-profiles", id);

        return ResponseEntity.ok(interimService.getGrantedProfiles(id));
    }

    /**
     * GET /api/interims/{id}/backed-up-profiles
     * See which profiles were backed up for this interim
     */
    @GetMapping("/{id}/backed-up-profiles")
    public ResponseEntity<List<InterimProfilBackup>> getBackedUpProfiles(
            @PathVariable Long id) {

        log.info("GET /api/interims/{}/backed-up-profiles", id);

        return ResponseEntity.ok(interimService.getBackedUpProfiles(id));
    }
}