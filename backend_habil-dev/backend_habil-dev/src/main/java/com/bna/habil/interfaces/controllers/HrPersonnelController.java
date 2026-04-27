package com.bna.habil.interfaces.controllers;

import com.bna.habil.application.dto.HrPersonnelDto;
import com.bna.habil.application.dto.filters.HrPersonnelFilterDto;
import com.bna.habil.application.services.HrPersonnelService;

import com.bna.habil.infrastructure.security.model.ResponseHabil;
import com.bna.habil.infrastructure.utils.Constants;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/hr")
@RequiredArgsConstructor
public class HrPersonnelController {

    private final HrPersonnelService service;

    /**
     * Get HR Personnel with pagination and GENERAL SEARCH
     *
     * The 'search' parameter searches across: matcle, prenom, nomuse, prenom+nomuse
     *
     * Examples:
     * GET /api/hr/personnel/page?page=0&size=10
     * GET /api/hr/personnel/page?page=0&size=10&search=John
     * GET /api/hr/personnel/page?page=0&size=10&search=12345
     * GET /api/hr/personnel/page?page=0&size=10&search=John Doe
     * GET /api/hr/personnel/page?page=0&size=10&cin=AB123456
     * GET /api/hr/personnel/page?page=0&size=10&search=John&cin=AB123456
     */
    @GetMapping("/personnel/page")
    public ResponseEntity<ResponseHabil> getHrPersonnelPageable(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size,
            @RequestParam(required = false) String search,
            @RequestParam(required = false) String cin,
            @RequestParam(required = false) String matcle,
            @RequestParam(defaultValue = "matcle") String sortBy,
            @RequestParam(defaultValue = "ASC") String sortDirection) {

        // Create Sort object
        Sort sort = Sort.by(Sort.Direction.fromString(sortDirection), sortBy);

        // Create Pageable
        Pageable pageable = PageRequest.of(page, size, sort);

        // Create filter DTO
        HrPersonnelFilterDto filter = HrPersonnelFilterDto.builder()
                .search(search)
                .cin(cin)
                .matcle(matcle)
                .build();

        // Get paginated result
        Page<HrPersonnelDto> result = service.getHrPersonnelPageable(filter, pageable);

        return ResponseEntity.ok(new ResponseHabil(0, Constants.SUCCES, result));
    }

    /**
     * Get HR Personnel with pagination and INDIVIDUAL FIELD FILTERS
     *
     * Each field can be filtered separately
     *
     * Examples:
     * GET /api/hr/personnel/filter?page=0&size=10&prenom=John
     * GET /api/hr/personnel/filter?page=0&size=10&nomuse=Doe
     * GET /api/hr/personnel/filter?page=0&size=10&prenom=John&nomuse=Doe
     * GET /api/hr/personnel/filter?page=0&size=10&matcle=12345
     * GET /api/hr/personnel/filter?page=0&size=10&cin=AB123456&prenom=John
     */
    @GetMapping("/personnel/filter")
    public ResponseEntity<ResponseHabil> getHrPersonnelWithFilters(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size,
            @RequestParam(required = false) String cin,
            @RequestParam(required = false) String matcle,
            @RequestParam(required = false) String prenom,
            @RequestParam(required = false) String nomuse,
            @RequestParam(defaultValue = "matcle") String sortBy,
            @RequestParam(defaultValue = "ASC") String sortDirection) {

        Sort sort = Sort.by(Sort.Direction.fromString(sortDirection), sortBy);
        Pageable pageable = PageRequest.of(page, size, sort);

        HrPersonnelFilterDto filter = HrPersonnelFilterDto.builder()
                .cin(cin)
                .matcle(matcle)
                .prenom(prenom)
                .nomuse(nomuse)
                .build();

        Page<HrPersonnelDto> result = service.getHrPersonnelWithFilters(filter, pageable);

        return ResponseEntity.ok(new ResponseHabil(0, Constants.SUCCES, result));
    }

    /**
     * Get all HR Personnel without pagination (backward compatibility)
     */
    @GetMapping("/personnel")
    public ResponseEntity<ResponseHabil> getHrPersonnel(
            @RequestParam(required = false) String cin) {

        List<HrPersonnelDto> result = service.getHrPersonnel(cin);

        return ResponseEntity.ok(new ResponseHabil(0, Constants.SUCCES, result));
    }

    @GetMapping("/personnel/poste")
    public ResponseEntity<ResponseHabil> getPosteByMatricule(
            @RequestParam String matcle) {

        String poste = service.getPosteByMatricule(matcle);

        return ResponseEntity.ok(new ResponseHabil(0, Constants.SUCCES, poste));
    }

    @GetMapping("/personnel/verify-poste")
    public ResponseEntity<ResponseHabil> verifyPosteByMatricule(
            @RequestParam String matcle,
            @RequestParam String codePoste) {

        boolean result = service.verifyPosteByMatricule(matcle, codePoste);

        return ResponseEntity.ok(new ResponseHabil(0, Constants.SUCCES, result));
    }
}