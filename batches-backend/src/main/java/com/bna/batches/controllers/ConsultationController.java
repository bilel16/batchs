package com.bna.batches.controllers;

import com.bna.batches.interfaces.response.ConsultationResultDto;
import com.bna.batches.interfaces.response.DashboardStatsDto;
import com.bna.batches.services.ConsultationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/consultation")
@RequiredArgsConstructor
public class ConsultationController {

    private final ConsultationService consultationService;

    /** Dashboard KPIs and statistics */
    @GetMapping("/stats/dashboard")
    public ResponseEntity<DashboardStatsDto> getDashboardStats() {
        return ResponseEntity.ok(consultationService.getDashboardStats());
    }

    /** Search compensation results */
    @GetMapping("/search")
    public ResponseEntity<List<ConsultationResultDto>> search(
            @RequestParam(required = false) String dateStart,
            @RequestParam(required = false) String dateEnd,
            @RequestParam(required = false) String instrument,
            @RequestParam(required = false) String sens,
            @RequestParam(required = false) String valeur,
            @RequestParam(required = false) String agence) {

        List<ConsultationResultDto> results = consultationService.search(
                dateStart, dateEnd, instrument, sens, valeur, agence);
        return ResponseEntity.ok(results);
    }
}
