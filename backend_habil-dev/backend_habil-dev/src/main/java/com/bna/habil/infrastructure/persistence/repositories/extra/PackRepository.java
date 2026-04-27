package com.bna.habil.infrastructure.persistence.repositories.extra;

import com.bna.habil.domain.entities.Pack;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PackRepository extends JpaRepository<Pack, String> {
    List<Pack> findByBoolActifPack(int i);

    List<Pack> findByCodNivhPfl(String codNivhPfl);

    List<Pack> findByCodCatpPfl(String codCatpPfl);

    @Query(value = """
            SELECT DISTINCT p.*
            FROM habil.pack p
            WHERE (
                (:structureType = 6 AND p.cod_catp_pfl = 1)
             OR (:structureType = 7 AND p.cod_catp_pfl = 7)
             OR (:structureType <= 2 AND p.cod_catp_pfl = :structureType)
             OR (:structureType > 2 AND :structureType NOT IN (6,7)
                 AND p.cod_catp_pfl IN (1, 2, :structureType))
            )
            """, nativeQuery = true)
    List<Pack> findActivePacksByStructureType(@Param("structureType") Integer structureType);
}
