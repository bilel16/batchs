package com.bna.habil.interfaces.controllers;

import com.bna.habil.application.dto.AddApplicationDto;
import com.bna.habil.infrastructure.security.model.ResponseHabil;
import com.bna.habil.infrastructure.utils.Constants;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.bna.habil.application.dto.ApplicationDto;
import com.bna.habil.application.services.ApplicationService;


@RestController
@RequestMapping("/applications")
@RequiredArgsConstructor
@Slf4j
public class ApplicationController {

    private final ApplicationService applicationService;

    @PostMapping
    public ResponseEntity<ResponseHabil> createApplication(@Valid @RequestBody ApplicationDto dto) {
        log.info("Creating application: {}", dto.getCodApp());
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ResponseHabil(0, Constants.CREATED, applicationService.create(dto)));
    }

    @GetMapping
    public ResponseEntity<ResponseHabil> getAllApplications() {
        return ResponseEntity.ok(new ResponseHabil(0, Constants.SUCCES, applicationService.findAll()));
    }

    @GetMapping("/{codApp}")
    public ResponseEntity<ResponseHabil> getApplicationById(@PathVariable String codApp) {
        return ResponseEntity.ok(new ResponseHabil(0, Constants.SUCCES, applicationService.findById(codApp)));
    }

    @PutMapping("/{codApp}")
    public ResponseEntity<ResponseHabil> updateApplication(@PathVariable String codApp,
                                                           @Valid @RequestBody ApplicationDto dto) {
        return ResponseEntity.ok(new ResponseHabil(0, Constants.UPDATED, applicationService.update(codApp, dto)));
    }

    @DeleteMapping("/{codApp}")
    public ResponseEntity<ResponseHabil> deleteApplication(@PathVariable String codApp) {
        applicationService.delete(codApp);
        return ResponseEntity.ok(new ResponseHabil(0, Constants.SUCCES, "Supprimé avec succès"));
    }

    @PostMapping("/add")
    public ResponseEntity<ResponseHabil> createApplicationWithProfiles(@Valid @RequestBody AddApplicationDto dto) {
        log.info("Creating application with profiles: {}", dto.getCodApp());
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ResponseHabil(0, Constants.CREATED, applicationService.createWithProfiles(dto)));
    }

    @PutMapping("/update/{codApp}")
    public ResponseEntity<ResponseHabil> updateApplicationWithProfiles(@PathVariable String codApp,
                                                                       @Valid @RequestBody AddApplicationDto dto) {
        log.info("Updating application with profiles: {}", codApp);
        return ResponseEntity.ok(new ResponseHabil(0, Constants.UPDATED, applicationService.updateWithProfiles(codApp, dto)));
    }

    @GetMapping("/details/{codApp}")
    public ResponseEntity<ResponseHabil> getApplicationDetails(@PathVariable String codApp) {
        return ResponseEntity.ok(new ResponseHabil(0, Constants.SUCCES, applicationService.getApplicationDetails(codApp)));
    }

    @GetMapping("/authorized")
    public ResponseEntity<ResponseHabil> getAllAuthorizedApplications() {
        return ResponseEntity.ok(new ResponseHabil(0, Constants.SUCCES, applicationService.getAllAuthorizedApplications()));
    }
}