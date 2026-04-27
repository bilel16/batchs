package com.bna.habil.infrastructure.persistence.repositories.extra;

import com.bna.habil.domain.entities.PackProfil;
import com.bna.habil.domain.entities.entitiesId.PackProfilId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PackProfilRepository extends JpaRepository<PackProfil, PackProfilId> {
    List<PackProfil> findByCodPackPack(String codPackPack);

    List<PackProfil> findByCodPflPfl(String codPflPfl);

    List<PackProfil> findByCodPackPackAndBoolEtat(String codPackPack, int i);

    List<PackProfil> findByCodPackPackAndCodTstrcTstrc(String codPackPack, String codTstrcTstrc);

    List<PackProfil> findByCodPackPackAndCodTstrcTstrcAndBoolEtat(String codPackPack, String codTstrcTstrc, Integer boolEtat);

    int countByCodPackPack(String codPackPack);

    boolean existsByPackCodPackPackAndCodPflPfl(String packCode, String codPflPfl);
}
