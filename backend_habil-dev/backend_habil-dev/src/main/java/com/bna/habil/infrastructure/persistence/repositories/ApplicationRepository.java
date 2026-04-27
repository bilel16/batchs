package com.bna.habil.infrastructure.persistence.repositories;


import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bna.habil.domain.entities.Application;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


public interface ApplicationRepository extends JpaRepository<Application, String> {

    List<Application> findAll();

    @Query("SELECT a FROM Application a WHERE a.cod_app_app IN :codAppApps")
    List<Application> findByCodAppAppIn(@Param("codAppApps") Set<String> codAppApps);
}
