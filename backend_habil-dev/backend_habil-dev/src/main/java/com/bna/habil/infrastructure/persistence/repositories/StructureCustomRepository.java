package com.bna.habil.infrastructure.persistence.repositories;


import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.bna.habil.domain.entities.Structure;
import com.bna.habil.infrastructure.persistence.repositories.extra.StructureRepository;


@Repository
public interface StructureCustomRepository extends StructureRepository {
    /**
     * Get distinct structure type codes from STRUCTURE table
     */
    @Query(value = """
            SELECT DISTINCT s.COD_TSTR_TSTR 
            FROM STRUCTURE s 
            WHERE s.COD_TSTR_TSTR IS NOT NULL
            ORDER BY s.COD_TSTR_TSTR
            """, nativeQuery = true)
    List<Integer> findDistinctTypeCodes();
    /**
     * Get structures by type code
     */
    @Query("SELECT s FROM Structure s WHERE s.codeTypeStructure = :typeCode ORDER BY s.libelleStructure")
    List<Structure> findByTypeCode(@Param("typeCode") Integer typeCode);
    /**
     * Get distinct structure types for type filter dropdown
     */
    @Query(value = """
            SELECT DISTINCT ts.COD_TSTR_TSTR, ts.LIB_TSTR_TSTR 
            FROM TYPE_STRUCTURE ts 
            ORDER BY ts.LIB_TSTR_TSTR
            """, nativeQuery = true)
    List<Object[]> findAllStructureTypes();    /**
     * Get all structures with their type info for the dropdown
     */
    @Query(value = """
            SELECT s.cod_strc_strc, 
                   s.lib_strc_strc, 
                   s.COD_TSTR_TSTR
            FROM STRUCTURE s 
            ORDER BY s.lib_strc_strc
            """, nativeQuery = true)
    List<Object[]> findAllStructuresWithTypes();
    @Query(value = """
            SELECT s.*
            FROM structure s
            START WITH s.cod_strc_strc = :structureCode
            CONNECT BY PRIOR s.cod_strc_strc = s.cod_strm_strc
            """, nativeQuery = true)
    List<Structure> findByParentStructureRecursive(@Param("structureCode") Integer structureCode);

    @Query(value = """
            SELECT s.* FROM structure s
            INNER JOIN personnel p ON p.cod_strc_strc = s.cod_strc_strc
            WHERE p.num_matr_user = :userMatricule
            """, nativeQuery = true)
    Structure findStructureByUserMatricule(@Param("userMatricule") String userMatricule);

    @Query("SELECT s FROM Structure s WHERE s.id IN :structureIds")
    List<Structure> findByIdIn(@Param("structureIds") List<Integer> structureIds);
}
