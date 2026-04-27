package com.bna.habil.interfaces.controllers;

import java.util.List;


import com.bna.habil.application.dto.EmployeeDTO;
import com.bna.habil.application.dto.PersonnelDetailsDto;
import com.bna.habil.application.dto.PersonnelDto;

import com.bna.habil.application.dto.PersonnelFilterDto;

import com.bna.habil.infrastructure.security.model.ResponseHabil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.bna.habil.application.services.UtilisateurProfilService;
import com.bna.habil.application.services.impl.PersonneServiceImpl;
import com.bna.habil.domain.entities.Personnel;
import com.bna.habil.infrastructure.utils.Constants;
import com.bna.habil.application.dto.statistics.PersonnelStatsDto;


/**
 * REST Controller for Personnel management
 */
@RestController
@RequestMapping("/personnel")
@Slf4j
public class PersonnelController {

    private final PersonneServiceImpl personneService;


    private final UtilisateurProfilService utilisateurProfilService;

    public PersonnelController(PersonneServiceImpl personneService, UtilisateurProfilService utilisateurProfilService) {
        this.personneService = personneService;
        this.utilisateurProfilService = utilisateurProfilService;
    }

    // Search personnel by matricule (partial search)
    @GetMapping("/search/by-matricule")
    public ResponseEntity<ResponseHabil> searchByMatricule(@RequestParam("query") String query) {
        log.info("Searching personnel by matricule: {}", query);

        List<Personnel> personnelList = personneService.findPersonnelByMatricule(query);

        return ResponseEntity.ok(new ResponseHabil(0, Constants.SUCCES, personnelList));
    }


    @GetMapping("/{matricule}/applications")
    public ResponseEntity<ResponseHabil> getApplicationsByMatricule(@PathVariable String matricule) throws Exception {
        log.info("Getting applications for personnel: {}", matricule);

        List<String> applications = utilisateurProfilService.getApplicationsByMatricule(matricule);

        return ResponseEntity.ok(new ResponseHabil(0, Constants.SUCCES, applications));
    }

    @PostMapping("/create")
    public ResponseEntity<ResponseHabil> createPersonnel(@RequestBody PersonnelDto dto) {
        log.info("Creating personnel with DTO: {}", dto);
        PersonnelDetailsDto created = personneService.createPersonnel(dto);
        return ResponseEntity.ok(new ResponseHabil(0, Constants.SUCCES, created));
    }

    @GetMapping("/all-details")
    public ResponseEntity<ResponseHabil> getAllPersonnelDetails() {
        List<PersonnelDetailsDto> list = personneService.getAllPersonelles();
        return ResponseEntity.ok(new ResponseHabil(0, Constants.SUCCES, list));
    }

    @GetMapping("/page")
    public ResponseEntity<ResponseHabil> getPersonnelPage(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size,
            @RequestParam(required = false) String search,
            @RequestParam(required = false) Boolean codStatUser,
            @RequestParam(required = false) List<Integer> codStrcStrc,  // supports multiple
            @RequestParam(required = false) Integer codTstrTstr,
            @RequestParam(defaultValue = "mat") String sortBy,
            @RequestParam(defaultValue = "ASC") String sortDirection) {

        Sort sort = Sort.by(Sort.Direction.fromString(sortDirection), sortBy);
        Pageable pageable = PageRequest.of(page, size, sort);

        PersonnelFilterDto filter = new PersonnelFilterDto();
        filter.setSearch(search);
        filter.setCodStatUser(codStatUser);
        filter.setCodStrcStrcList(codStrcStrc);
        filter.setCodTstrTstr(codTstrTstr);

        Page<PersonnelDetailsDto> result = personneService.getPersonnelPageable(pageable, filter);

        return ResponseEntity.ok(new ResponseHabil(0, Constants.SUCCES, result));
    }

    @PutMapping("/{mat}")
    public ResponseEntity<ResponseHabil> updatePersonnel(@PathVariable String mat,
                                                         @RequestBody PersonnelDetailsDto dto) {
        PersonnelDetailsDto updated = personneService.updatePersonelle(mat, dto);
        return ResponseEntity.ok(new ResponseHabil(0, Constants.SUCCES, updated));
    }

    @GetMapping("/stats")
    public ResponseEntity<ResponseHabil> getPersonnelStats() {
        PersonnelStatsDto stats = personneService.getPersonnelStatistics();
        return ResponseEntity.ok(new ResponseHabil(0, Constants.SUCCES, stats));
    }

    @GetMapping("/by-date")
    public ResponseEntity<List<EmployeeDTO>> getEmployeesByDate() {
        return ResponseEntity.ok(personneService.getEmployeesByDate());
    }
}