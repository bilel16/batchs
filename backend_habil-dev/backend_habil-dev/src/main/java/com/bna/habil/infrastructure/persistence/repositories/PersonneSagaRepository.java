package com.bna.habil.infrastructure.persistence.repositories;


import org.springframework.data.jpa.repository.JpaRepository;

import com.bna.habil.domain.entities.PersonneSaga;

public interface PersonneSagaRepository extends JpaRepository<PersonneSaga, Long> {
}