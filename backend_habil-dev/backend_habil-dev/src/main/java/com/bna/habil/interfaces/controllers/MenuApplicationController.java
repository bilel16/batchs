package com.bna.habil.interfaces.controllers;

import java.util.List;

import com.bna.habil.infrastructure.security.model.ResponseHabil;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.bna.habil.application.dto.MenuApplicationDto;
import com.bna.habil.application.services.MenuApplicationService;
import com.bna.habil.domain.entities.entitiesId.MenuApplicationId;
import com.bna.habil.infrastructure.utils.Constants;

import jakarta.validation.Valid;

/**
 * REST Controller for MenuApplication management
 */
@RestController
@RequestMapping("/menu-applications")
@Slf4j
@Validated
public class MenuApplicationController {

    private static final Logger logger = LoggerFactory.getLogger(MenuApplicationController.class);

    private final MenuApplicationService menuApplicationService;

    public MenuApplicationController(MenuApplicationService menuApplicationService) {
        this.menuApplicationService = menuApplicationService;
    }

    @GetMapping
    public ResponseEntity<ResponseHabil> getAllMenuApplications() {
        log.info("Getting all menu applications");

        List<MenuApplicationDto> menuApplications = menuApplicationService.findAll();

        return ResponseEntity.ok(new ResponseHabil(0, Constants.SUCCES, menuApplications));
    }

    @GetMapping("/by-application/{codeApp}")
    public ResponseEntity<ResponseHabil> getMenuApplicationsByApp(@PathVariable("codeApp") String codeApp) throws Exception {
        log.info("Getting menu applications for application: {}", codeApp);

        List<MenuApplicationDto> menuApplications =
                menuApplicationService.getMenuApplicationListBycodAppApp(codeApp);

        return ResponseEntity.ok(new ResponseHabil(0, Constants.SUCCES, menuApplications));
    }

    @PostMapping
    public ResponseEntity<ResponseHabil> createMenuApplication(
            @Valid @RequestBody MenuApplicationDto menuApplicationDto) {

        log.info("Creating menu application: App={}, Menu={}",
                menuApplicationDto.getCodAppApp(), menuApplicationDto.getCodMenuMenu());

        MenuApplicationDto created = menuApplicationService.create(menuApplicationDto);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new ResponseHabil(0, Constants.CREATED, created));
    }

    @PutMapping("/{codApp}/{codMenu}")
    public ResponseEntity<ResponseHabil> updateMenuApplication(
            @PathVariable String codApp,
            @PathVariable String codMenu,
            @Valid @RequestBody MenuApplicationDto menuApplicationDto) {

        log.info("Updating menu application: App={}, Menu={}", codApp, codMenu);

        MenuApplicationId id = new MenuApplicationId(codApp, codMenu);
        MenuApplicationDto updated = menuApplicationService.update(id, menuApplicationDto);

        return ResponseEntity.ok(new ResponseHabil(0, Constants.UPDATED, updated));
    }

    @DeleteMapping(value = "/deleteMenuApp")
    public ResponseEntity<ResponseHabil> deleteMenuApplication(@Valid @RequestBody MenuApplicationDto menuApplicationDto) {
        MenuApplicationId id = new MenuApplicationId(menuApplicationDto.getCodAppApp(), menuApplicationDto.getCodMenuMenu());
        menuApplicationService.delete(id);
        logger.info("deleteMenuApplication : output : deleteMenuApplication terminée");
        return ResponseEntity.ok(new ResponseHabil(0, Constants.SUCCES, "Supprimé avec succès"));
    }

}