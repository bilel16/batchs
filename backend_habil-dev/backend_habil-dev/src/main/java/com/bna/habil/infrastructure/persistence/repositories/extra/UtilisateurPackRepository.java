package com.bna.habil.infrastructure.persistence.repositories.extra;

import com.bna.habil.domain.entities.UtilisateurPack;
import com.bna.habil.domain.entities.entitiesId.UtilisateurPackId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UtilisateurPackRepository extends JpaRepository<UtilisateurPack, UtilisateurPackId> {
    List<UtilisateurPack> findByNumMatrUser(String matricule);

    List<UtilisateurPack> findByCodPackPackAndBoolEtatAffect(String codPackPack, int i);
}
