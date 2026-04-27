package com.bna.habil.infrastructure.persistence.repositories;


import java.util.List;
import java.util.Optional;
import java.util.Set;

import com.bna.habil.application.dto.PersonnelDetailsDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.bna.habil.domain.entities.Personne;


@Repository
public interface PersonneRepository extends JpaRepository<Personne, Long> {


	/*@Query(value="select pers.* from smile.personne pers, personnel p"
			+ " where p.num_cin_user=pers.NUM_PCE_PERS and pers.COD_TPCE_TPCE=02 and p.num_matr_user= :num_matr_user " , nativeQuery=true)
	*/

    @Query(value = "SELECT pers.* FROM smile.personne pers " +
            "JOIN personnel p ON p.num_cin_user = pers.NUM_PCE_PERS " +
            "WHERE pers.COD_TPCE_TPCE = 2 AND p.num_matr_user = :num_matr_user", nativeQuery = true)
    Optional<Personne> findTheUser(@Param("num_matr_user") String numMatrUser);

    @Query("SELECT new com.bna.habil.application.dto.PersonnelDetailsDto(" +
            "personnel.mat, " +
            "CONCAT(personne.nom_prn_pers, ' ', personne.nom_nom_pers), " +
            "personne.adr_mail_pers, " +
            "personnel.cod_stat_user, " +
            "personnel.cod_strc_strc, " +
            "s.codeTypeStructure) " +
            "FROM Personnel personnel " +
            "JOIN Personne personne ON personnel.cin = personne.num_pce_pers AND personne.cod_tpce_tpce = 2 " +
            "LEFT JOIN Structure s ON s.id = personnel.cod_strc_strc " +
            "WHERE personnel.cin = :cin")
    Optional<PersonnelDetailsDto> findPersonnelDetailsByCin(@Param("cin") String cin);

    //  NEW: Get ALL personnel (for admins)
    @Query("SELECT new com.bna.habil.application.dto.PersonnelDetailsDto(" +
            "personnel.mat, " +
            "CONCAT(personne.nom_prn_pers, ' ', personne.nom_nom_pers), " +
            "personne.adr_mail_pers, " +
            "personnel.cod_stat_user, " +
            "personnel.cod_strc_strc, " +
            "s.codeTypeStructure) " +
            "FROM Personnel personnel " +
            "JOIN Personne personne ON personnel.cin = personne.num_pce_pers " +
            "LEFT JOIN Structure s ON s.id = personnel.cod_strc_strc")
    List<PersonnelDetailsDto> findAllPersonnelDetails();

    @Query(value = """
        SELECT new com.bna.habil.application.dto.PersonnelDetailsDto(
            p.mat, 
            CONCAT(per.nom_prn_pers, ' ', per.nom_nom_pers), 
            per.adr_mail_pers, 
            p.cod_stat_user, 
            p.cod_strc_strc, 
            s.codeTypeStructure,
            s.libelleStructure) 
        FROM Personnel p 
        JOIN Personne per ON p.cin = per.num_pce_pers 
        LEFT JOIN Structure s ON s.id = p.cod_strc_strc 
        WHERE (:search IS NULL OR :search = '' 
               OR LOWER(TRIM(CONCAT(TRIM(per.nom_prn_pers), ' ', TRIM(per.nom_nom_pers)))) LIKE LOWER(CONCAT('%', TRIM(:search), '%')) 
               OR LOWER(TRIM(per.nom_prn_pers)) LIKE LOWER(CONCAT('%', TRIM(:search), '%')) 
               OR LOWER(TRIM(per.nom_nom_pers)) LIKE LOWER(CONCAT('%', TRIM(:search), '%')) 
               OR LOWER(p.mat) LIKE LOWER(CONCAT('%', :search, '%')) 
               OR LOWER(per.adr_mail_pers) LIKE LOWER(CONCAT('%', :search, '%'))) 
        AND (:codStatUser IS NULL OR p.cod_stat_user = :codStatUser) 
        AND (:codStrcStrcList IS NULL OR p.cod_strc_strc IN :codStrcStrcList) 
        AND (:codTstrTstr IS NULL OR s.codeTypeStructure = :codTstrTstr)
        """,
            countQuery = """
        SELECT COUNT(p) FROM Personnel p 
        JOIN Personne per ON p.cin = per.num_pce_pers 
        LEFT JOIN Structure s ON s.id = p.cod_strc_strc 
        WHERE (:search IS NULL OR :search = '' 
               OR LOWER(per.nom_prn_pers) LIKE LOWER(CONCAT('%', :search, '%')) 
               OR LOWER(per.nom_nom_pers) LIKE LOWER(CONCAT('%', :search, '%')) 
               OR LOWER(p.mat) LIKE LOWER(CONCAT('%', :search, '%')) 
               OR LOWER(per.adr_mail_pers) LIKE LOWER(CONCAT('%', :search, '%'))) 
        AND (:codStatUser IS NULL OR p.cod_stat_user = :codStatUser) 
        AND (:codStrcStrcList IS NULL OR p.cod_strc_strc IN :codStrcStrcList) 
        AND (:codTstrTstr IS NULL OR s.codeTypeStructure = :codTstrTstr)
        """)
    Page<PersonnelDetailsDto> findAllPersonnelDetailsWithFilters(
            @Param("search") String search,
            @Param("codStatUser") Boolean codStatUser,
            @Param("codStrcStrcList") List<Integer> codStrcStrcList,
            @Param("codTstrTstr") Integer codTstrTstr,
            Pageable pageable
    );
    //  NEW: Get personnel by multiple CINs (optimized - single query instead of N queries)
    @Query("SELECT new com.bna.habil.application.dto.PersonnelDetailsDto(" +
            "personnel.mat, " +
            "CONCAT(personne.nom_prn_pers, ' ', personne.nom_nom_pers), " +
            "personne.adr_mail_pers, " +
            "personnel.cod_stat_user, " +
            "personnel.cod_strc_strc, " +
            "s.codeTypeStructure) " +
            "FROM Personnel personnel " +
            "JOIN Personne personne ON personnel.cin = personne.num_pce_pers " +
            "LEFT JOIN Structure s ON s.id = personnel.cod_strc_strc " +
            "WHERE personnel.cin IN :cins")
    List<PersonnelDetailsDto> findPersonnelDetailsByCins(@Param("cins") Set<String> cins);

    @Query("SELECT p FROM Personne p WHERE p.num_pce_pers = :numPcePers AND p.cod_tpce_tpce = 2")
    Optional<Personne> findByNumPcePers(@Param("numPcePers") String numPcePers);
    @Query("SELECT new com.bna.habil.application.dto.PersonnelDetailsDto(" +
            "personnel.mat, " +
            "CONCAT(personne.nom_prn_pers, ' ', personne.nom_nom_pers), " +
            "personne.adr_mail_pers, " +
            "personnel.cod_stat_user, " +
            "personnel.cod_strc_strc, " +
            "s.codeTypeStructure) " +
            "FROM Personnel personnel " +
            "JOIN Personne personne ON personnel.cin = personne.num_pce_pers " +
            "LEFT JOIN Structure s ON s.id = personnel.cod_strc_strc " +
            "WHERE personnel.cod_strc_strc IN :structureIds")
    List<PersonnelDetailsDto> findPersonnelDetailsByStructureIds(
            @Param("structureIds") Set<Integer> structureIds);

    @Query("""
    SELECT per.mat, CONCAT(TRIM(p.nom_prn_pers), ' ', TRIM(p.nom_nom_pers))
    FROM Personnel per
    JOIN Personne p ON per.cin = p.num_pce_pers
    WHERE per.mat IN :matricules
""")
    List<Object[]> findFullNamesByMatricules(@Param("matricules") Set<Integer> matricules);

    @Query("""
    SELECT s.codeTypeStructure 
    FROM Personnel p 
    LEFT JOIN Structure s ON s.id = p.cod_strc_strc 
    WHERE p.mat = :mat
    """)
    String findCodeTypeStructureByMat(@Param("mat") String mat);
}

