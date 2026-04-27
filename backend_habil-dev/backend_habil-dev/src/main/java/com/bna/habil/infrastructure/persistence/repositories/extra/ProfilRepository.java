package com.bna.habil.infrastructure.persistence.repositories.extra;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.bna.habil.domain.entities.Profil;


public interface ProfilRepository extends JpaRepository<Profil, String> {

    List<Profil> findByCodAppApp(String codAppApp);

    Profil findByCodPflPfl(String codPflPfl);

    // Option 1: If profiles are filtered by some existing field (like category)
    @Query(value = """
            SELECT DISTINCT p.* FROM profil p
            WHERE p.cod_app_app = :appCode
            AND p.bool_etat_pfl = 1
            AND (p.cod_catp_pfl IN :allowedCategories)
            ORDER BY p.lib_pfl_pfl
            """, nativeQuery = true)
    List<Profil> findProfilesForCategories(
            @Param("appCode") String appCode,
            @Param("allowedCategories") List<String> allowedCategories
    );

    // Option 2: Simple query that returns all profiles (filtering done in service)
    @Query(value = """
            SELECT p.* FROM profil p
            WHERE p.cod_app_app = :appCode
            AND p.bool_etat_pfl = 1
            ORDER BY p.lib_pfl_pfl
            """, nativeQuery = true)
    List<Profil> findActiveProfilesByApp(@Param("appCode") String appCode);

    @Query(value = """
            SELECT DISTINCT pma.*
            FROM profil pma
            WHERE pma.cod_app_app = :appCode
              AND pma.cod_nivh_pfl = :structureType or pma.cod_nivh_pfl = 0
              AND pma.bool_etat_pfl = 1
            """, nativeQuery = true)
    List<Profil> findActiveProfilesByAppAndStructureTypeForUser(
            @Param("appCode") String appCode,
            @Param("structureType") Integer structureType);

    @Query(value = """
            SELECT DISTINCT pma.*
            FROM profil pma
            WHERE pma.bool_etat_pfl = 1
            AND pma.cod_nivh_pfl = :structureType
            """, nativeQuery = true)
    List<Profil> findActiveProfilesByStructureType(
            @Param("structureType") Integer structureType);

    // OPTIMIZED: Single native query with all calculations
    @Query(value = """
            SELECT 
                a.COD_APP_APP,
                a.LIB_APP_APP,
                COUNT(p.COD_PFL_PFL) as total_profiles,
                SUM(CASE WHEN p.BOOL_ETAT_PFL = '1' THEN 1 ELSE 0 END) as active_profiles,
                SUM(CASE WHEN NVL(p.BOOL_ETAT_PFL, '0') != '1' THEN 1 ELSE 0 END) as inactive_profiles
            FROM habil.APPLICATION a
            LEFT JOIN habil.PROFIL p ON p.COD_APP_APP = a.COD_APP_APP
            GROUP BY a.COD_APP_APP, a.LIB_APP_APP
            ORDER BY COUNT(p.COD_PFL_PFL) DESC
            """, nativeQuery = true)
    List<Object[]> getProfileStatisticsByApplication();

}
