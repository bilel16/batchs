package com.bna.habil.interfaces.controllers;

import com.bna.habil.application.dto.statistics.ApplicationStatsDto;
import com.bna.habil.application.dto.statistics.PersonnelStatsDto;
import com.bna.habil.application.dto.statistics.ProfileStatsDto;
import com.bna.habil.application.services.MenuApplicationService;
import com.bna.habil.application.services.PersonneService;
import com.bna.habil.application.services.ProfilService;
import com.bna.habil.application.services.StructureService;
import com.bna.habil.infrastructure.security.model.ResponseHabil;
import com.bna.habil.infrastructure.utils.Constants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/statistics")
@Slf4j
public class StatisticsController {

    private final PersonneService personneService;
    private final StructureService structureService;
    private final MenuApplicationService menuApplicationService;
    private final ProfilService profilService;


    public StatisticsController(PersonneService personneService, StructureService structureService, MenuApplicationService menuApplicationService, ProfilService profilService) {
        this.personneService = personneService;
        this.structureService = structureService;
        this.menuApplicationService = menuApplicationService;
        this.profilService = profilService;
    }

    @PostMapping("/structure/labels")
    public ResponseEntity<ResponseHabil> getStructureLabels(@RequestBody List<Integer> structureIds) {
        log.info("Fetching labels for structure IDs: {}", structureIds);
        Map<Integer, String> labels = structureService.getStructureLabels(structureIds);
        return ResponseEntity.ok(new ResponseHabil(0, Constants.SUCCES, labels));
    }

    @GetMapping("/personnel")
    public ResponseEntity<ResponseHabil> getPersonnelStats() {
        PersonnelStatsDto stats = personneService.getPersonnelStatistics();
        return ResponseEntity.ok(new ResponseHabil(0, Constants.SUCCES, stats));
    }

    @GetMapping("/applications")
    public ResponseEntity<ResponseHabil> getApplicationStats() {
        log.info("Fetching application and menu statistics");
        ApplicationStatsDto stats = menuApplicationService.getApplicationStatistics();
        return ResponseEntity.ok(new ResponseHabil(0, Constants.SUCCES, stats));
    }

    @GetMapping("/profiles")
    public ResponseEntity<ResponseHabil> getProfileStats() {
        log.info("Fetching profile statistics");
        ProfileStatsDto stats = profilService.getProfileStatistics();
        return ResponseEntity.ok(new ResponseHabil(0, Constants.SUCCES, stats));
    }
}
