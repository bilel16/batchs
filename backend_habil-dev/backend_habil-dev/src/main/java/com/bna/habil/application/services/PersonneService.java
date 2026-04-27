package com.bna.habil.application.services;


import java.util.List;
import java.util.Optional;

import com.bna.habil.application.dto.EmployeeDTO;
import com.bna.habil.application.dto.PersonnelDetailsDto;
import com.bna.habil.application.dto.statistics.PersonnelStatsDto;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;

import com.bna.habil.domain.entities.Personne;
import com.bna.habil.domain.entities.Personnel;


public interface PersonneService {

    Page<Personne> getAll(int page, int size);

    Optional<Personne> findByNumMatrUser(String matricule);

    List<Personnel> findPersonnelByMatricule(String matricule);

    @Transactional
    PersonnelDetailsDto updatePersonelle(String mat, PersonnelDetailsDto dto);

    PersonnelStatsDto getPersonnelStatistics();

    List<EmployeeDTO> getEmployeesByDate();
}

