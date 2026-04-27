package com.bna.habil.application.services;


import java.util.List;
import java.util.Optional;

import com.bna.habil.domain.entities.Personnel;


public interface PersonnelService {

    Optional<Personnel> findByNumMatr(String mat);

    List<Personnel> findPersonnelByMatricule(String matricule);
}
