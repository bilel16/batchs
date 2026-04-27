package com.bna.habil.infrastructure.persistence.repositories;


import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.bna.habil.domain.entities.Personnel;

public interface PersonnelRepository extends JpaRepository<Personnel, String> {
    @Query("SELECT p FROM Personnel p WHERE LOWER(p.mat) LIKE LOWER(CONCAT(:matricule, '%'))")
    List<Personnel> findPersonnelByMat(@Param("matricule") String matricule);

    @Query("SELECT s.codeTypeStructure FROM Personnel p " +
            "JOIN Structure s ON s.id = p.cod_strc_strc " +
            "WHERE p.mat = :mat")
    Optional<Integer> findCodeTypeStructureByMat(@Param("mat") String mat);

    @Query(value = """
    select b.MATCLE, b.PRENOM, b.NOMUSE, e.NOIDEN as CIN, 
           a.IDJB00, d.LBJBLG, g.LBOULG, f.IDOU00  
    from zy3b@HR.BNA.TN a, 
         zy00@HR.BNA.TN b, 
         zc00@HR.BNA.TN c, 
         zc01@HR.BNA.TN d, 
         zy12@HR.BNA.TN e, 
         ze00@HR.BNA.TN f, 
         ze01@HR.BNA.TN g 
    where a.nudoss = b.nudoss 
      and c.nudoss = d.nudoss 
      and e.nudoss = b.NUDOSS 
      and f.nudoss = g.nudoss 
      and a.IDOU00 = f.IDOU00 
      and a.IDJB00 = c.IDJB00 
    and TO_CHAR(a.DTEN00, 'DD/MM/RR') = '31/12/99'
        
    """, nativeQuery = true)
    List<Object[]> findEmployeesByDate();
}