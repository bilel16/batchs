package com.bna.habil.infrastructure.persistence.repositories.extra;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.bna.habil.domain.entities.ProfilMenuApplication;
import com.bna.habil.domain.entities.entitiesId.ProfilMenuApplicationId;
import com.bna.habil.domain.repositories.UserRoleProjection;


public interface ProfilMenuApplicationRepository extends JpaRepository<ProfilMenuApplication, ProfilMenuApplicationId> {
    @Query(value =
            "SELECT p.COD_PFL_PFL as codPflPfl, p.COD_MENU_MENU as codMenuMenu, " +
                    "m.LIB_MENU_MENU as libMenuMenu, p.BOOL_ETAT_PMA as boolEtatPma " +
                    "FROM profil_menu_application p " +
                    "JOIN utilisateur_profil up ON up.COD_PFL_PFL = p.COD_PFL_PFL " +
                    "LEFT JOIN menu_application m ON m.COD_APP_APP = p.COD_APP_APP AND m.COD_MENU_MENU = p.COD_MENU_MENU " +
                    "WHERE up.NUM_MATR_USER = :numMatrUser AND p.COD_APP_APP = :codAppApp",
            nativeQuery = true)
    List<UserRoleProjection> findUserRolesInApplication(@Param("numMatrUser") String numMatrUser,
                                                        @Param("codAppApp") String codAppApp);

    // Update all matching records (same app, menu, profil) across structures (codTstrcTstrc)
    @Modifying
    @Query("UPDATE ProfilMenuApplication p SET p.boolEtatPma = :state " +
            "WHERE p.codAppApp = :codAppApp AND p.codMenuMenu = :codMenuMenu AND p.codPflPfl = :codPflPfl")
    int updateBoolEtatForRole(@Param("codAppApp") String codAppApp,
                              @Param("codMenuMenu") String codMenuMenu,
                              @Param("codPflPfl") String codPflPfl,
                              @Param("state") Integer state);

    @Query(value = """
            SELECT DISTINCT pma.*
            FROM profil_menu_application pma
            WHERE pma.cod_app_app = :appCode
              AND pma.cod_tstrc_tstrc <= :structureType
              AND pma.bool_etat_pma = 1
            """, nativeQuery = true)
    List<ProfilMenuApplication> findActiveProfilesByAppAndStructureType(
            @Param("appCode") String appCode,
            @Param("structureType") String structureType);

    List<ProfilMenuApplication> findByCodAppApp(String codAppApp);


    ProfilMenuApplication findByCodAppAppAndCodMenuMenuAndCodPflPflAndCodTstrcTstrc(String codAppApp,
                                                                                    String codMenuMenu, String codPflPfl, String codTstrcTstrc);

    List<ProfilMenuApplication> findByCodPflPfl(String codpflpfl);

}
