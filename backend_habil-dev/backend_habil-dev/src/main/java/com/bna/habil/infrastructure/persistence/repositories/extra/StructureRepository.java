package com.bna.habil.infrastructure.persistence.repositories.extra;


import org.springframework.data.jpa.repository.JpaRepository;

import com.bna.habil.domain.entities.Structure;


public interface StructureRepository extends JpaRepository<Structure, Integer> {

}
