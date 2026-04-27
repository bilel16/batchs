package com.bna.habil.infrastructure.persistence.repositories;


import java.util.List;
import java.util.Optional;
import java.util.Set;

import com.bna.habil.application.dto.PersonnelDetailsDto;
import com.bna.habil.application.dto.statistics.StructurePersonnelCountDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.bna.habil.domain.entities.Personnel;


@Repository
public interface PersonnelCustomRepository extends PersonnelRepository {
    @Query("SELECT p FROM Personnel p WHERE p.id = :id")
    Personnel findPersonnelById(@Param("id") Long id);

    // Query that explicitly filters out null structure IDs
    @Query(value = "SELECT * FROM PERSONNEL p WHERE p.cod_strc_strc IS NOT NULL", nativeQuery = true)
    List<Personnel> findAllWithStructure();

    // Query for active personnel with structure
    @Query(value = "SELECT * FROM PERSONNEL p WHERE p.cod_strc_strc IS NOT NULL " +
            "AND p.cod_stat_user = 1", nativeQuery = true)
    List<Personnel> findAllActiveWithStructure();

    // Safe query for single user
    @Query(value = "SELECT * FROM PERSONNEL p WHERE p.num_matr_user = :matricule " +
            "AND p.cod_strc_strc IS NOT NULL", nativeQuery = true)
    Optional<Personnel> findActiveByMatricule(@Param("matricule") String matricule);

    @Query("SELECT p FROM Personnel p WHERE p.cod_strc_strc IN :structureIds " +
            "AND p.cod_stat_user = true")
    List<Personnel> findActivePersonnelByStructureIds(@Param("structureIds") Set<Integer> structureIds);

    @Query("SELECT p.mat FROM Personnel p " +
            "WHERE p.cod_strc_strc IN :structureIds " +
            "AND p.cod_stat_user = true")
    Set<String> findActiveMatriculesByStructureIds(
            @Param("structureIds") Set<Integer> structureIds);

    @Query("SELECT p FROM Personnel p WHERE p.cod_strc_strc = :structureId " +
            "AND p.cod_stat_user = true")
    List<Personnel> findActivePersonnelByStructureId(@Param("structureId") Integer structureId);

    @Query("SELECT COUNT(p) FROM Personnel p WHERE p.cod_strc_strc IN :structureIds " +
            "AND p.cod_stat_user = true")
    Long countActivePersonnelInStructures(@Param("structureIds") Set<Integer> structureIds);

    @Query("SELECT p.cin FROM Personnel p " +
            "WHERE p.cod_strc_strc IN :structureIds " +
            "AND p.cod_stat_user = true")
    Set<String> findActiveCinsByStructureIds(
            @Param("structureIds") Set<Integer> structureIds);

    // --- List all personnel details (DTO) ---
    @Query("SELECT new com.bna.habil.application.dto.PersonnelDetailsDto(" +
            "p.mat, " +
            "CONCAT(per.nom_prn_pers, ' ', per.nom_nom_pers), " +
            "per.adr_mail_pers, " +
            "p.cod_stat_user, " +
            "p.cod_strc_strc, " +
            "s.codeTypeStructure, " +
            "s.libelleStructure) " +
            "FROM Personnel p " +
            "JOIN Personne per ON p.cin = per.num_pce_pers " +
            "LEFT JOIN Structure s ON s.id = p.cod_strc_strc")
    List<PersonnelDetailsDto> findAllPersonnelDetails();

    // --- Pageable version (note the countQuery) ---
    @Query(value = "SELECT new com.bna.habil.application.dto.PersonnelDetailsDto(" +
            "p.mat, " +
            "CONCAT(per.nom_prn_pers, ' ', per.nom_nom_pers), " +
            "per.adr_mail_pers, " +
            "p.cod_stat_user, " +
            "p.cod_strc_strc, " +
            "s.codeTypeStructure) " +
            "FROM Personnel p " +
            "JOIN Personne per ON p.cin = per.num_pce_pers " +
            "LEFT JOIN Structure s ON s.id = p.cod_strc_strc",
            countQuery = "SELECT COUNT(p) FROM Personnel p")
    Page<PersonnelDetailsDto> findAllPersonnelDetails(Pageable pageable);

    // Optional: find DTO for an individual personnel by matricule (mat)
    @Query("SELECT new com.bna.habil.application.dto.PersonnelDetailsDto(" +
            "p.mat, CONCAT(per.nom_prn_pers, ' ', per.nom_nom_pers), per.adr_mail_pers, p.cod_stat_user, p.cod_strc_strc, s.codeTypeStructure) " +
            "FROM Personnel p " +
            "JOIN Personne per ON p.cin = per.num_pce_pers " +
            "LEFT JOIN Structure s ON s.id = p.cod_strc_strc " +
            "WHERE p.mat = :mat")
    Optional<PersonnelDetailsDto> findPersonnelDetailsByMat(@Param("mat") String mat);

    @Query("SELECT s.codeTypeStructure FROM Personnel p " +
            "JOIN Structure s ON s.id = p.cod_strc_strc " +
            "WHERE p.mat = :mat")
    Optional<Integer> findCodeTypeStructureByMat(@Param("mat") String mat);

    @Query("SELECT new com.bna.habil.application.dto.statistics.StructurePersonnelCountDto(" +
            "p.cod_strc_strc, " +
            "COUNT(p), " +
            "SUM(CASE WHEN p.cod_stat_user = true THEN 1 ELSE 0 END), " +
            "SUM(CASE WHEN p.cod_stat_user = false OR p.cod_stat_user IS NULL THEN 1 ELSE 0 END)) " +
            "FROM Personnel p " +
            "WHERE p.cod_strc_strc IS NOT NULL " +
            "GROUP BY p.cod_strc_strc")
    List<StructurePersonnelCountDto> countPersonnelByStructure();

}
