package com.bna.habil.infrastructure.persistence.repositories.extra;


import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.bna.habil.domain.entities.UtilisateurProfil;
import com.bna.habil.domain.entities.entitiesId.UtilisateurProfilId;


@Repository
public interface UtilisateurProfilRepository extends JpaRepository<UtilisateurProfil, UtilisateurProfilId> {

    @Query(value = "select * from utilisateur_profil where cod_pfl_pfl in (select cod_pfl_pfl from profil where cod_app_app = :codAppApp)",
            countQuery = "select count(*) from utilisateur_profil where cod_pfl_pfl in (select cod_pfl_pfl from profil where cod_app_app = :codAppApp)",
            nativeQuery = true)
    Page<UtilisateurProfil> getUtilisateurProfil(@Param("codAppApp") String codAppApp, Pageable pageable);

    @Query(value = """
            SELECT DISTINCT COD_APP_APP
            FROM UTILISATEUR_PROFIL up
            JOIN PROFIL p 
                ON up.COD_PFL_PFL = p.COD_PFL_PFL
            WHERE up.NUM_MATR_USER = :matricule
            AND UP.BOOL_ETAT_UTPR= 1
            """, nativeQuery = true)
    List<String> findApplicationsByMatricule(@Param("matricule") String matricule);

    @Query("SELECT up FROM UtilisateurProfil up WHERE up.profil.codAppApp = :codAppApp")
    List<UtilisateurProfil> findByProfilCodAppApp(@Param("codAppApp") String codAppApp);

    @Query("""
            SELECT up FROM UtilisateurProfil up
            JOIN FETCH up.profil p
            WHERE p.codAppApp = :codAppApp
            AND (:numMatrUser IS NULL OR up.id.numMatrUser = :numMatrUser)
            """)
    List<UtilisateurProfil> findByProfilCodAppAppAndOptionalMatricule(
            @Param("codAppApp") String codAppApp,
            @Param("numMatrUser") String numMatrUser
    );

    List<UtilisateurProfil> findAll();

    List<UtilisateurProfil> findById_CodPflPfl(String codPflPfl);

    UtilisateurProfil findById_CodPflPflAndId_NumMatrUser(String codPflPfl, String numMatrUser);

    List<UtilisateurProfil> findById_NumMatrUser(String numMatrUser);

    List<UtilisateurProfil> findByBoolEtatUtpr(Integer boolEtatUtpr);

    List<UtilisateurProfil> findByDatFadhUtprBetween(Date startDate, Date endDate);

    @Query("SELECT up FROM UtilisateurProfil up WHERE up.id.numMatrUser = :matricule AND up.boolEtatUtpr = 1")
    List<UtilisateurProfil> findActiveByUserMatricule(@Param("matricule") String matricule);

    @Query("SELECT COUNT(up) FROM UtilisateurProfil up WHERE up.id.numMatrUser = :matricule AND up.boolEtatUtpr = 1")
    Long countActiveByUserMatricule(@Param("matricule") String matricule);

    @Query("SELECT up FROM UtilisateurProfil up WHERE up.id.numMatrUser = :matricule")
    List<UtilisateurProfil> findByUserMatricule(@Param("matricule") String matricule);

    List<UtilisateurProfil> findByIdNumMatrUserAndBoolCustomProfil(String numMatrUser, int boolCustomProfil);

    @Query("SELECT up FROM UtilisateurProfil up " +
            "JOIN FETCH up.profil p " +
            "WHERE up.id.numMatrUser = :numMatrUser " +
            "AND p.codPflPfl LIKE %:profileSuffix " +
            "AND up.boolEtatUtpr = 1" +
            "AND CURRENT_DATE BETWEEN up.datdadhutpr AND up.datFadhUtpr ")
    List<UtilisateurProfil> findByIdNumMatrUserAndProfileSuffix(
            @Param("numMatrUser") String numMatrUser,
            @Param("profileSuffix") String profileSuffix);

    @Query("""
            select (count(up) > 0)
            from UtilisateurProfil up
            join up.profil p
            where up.id.numMatrUser = :mat
              and p.codAppApp = 'BNAHABIL'
              and p.codPflPfl = 'SUPER_ADMIN_HABIL'
              and (p.codNivhPfl = :codeTypeStructure OR p.codNivhPfl = '0')
              and up.boolEtatUtpr = 1
              AND CURRENT_DATE BETWEEN up.datdadhutpr AND up.datFadhUtpr
            """)
    boolean existsSuperAdminHabilAtLevel(@Param("mat") String mat,
                                         @Param("codeTypeStructure") String codeTypeStructure);

    List<UtilisateurProfil> findByIdNumMatrUser(String userMatricule);
}

