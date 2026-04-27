package com.bna.habil.infrastructure.persistence.repositories.extra;

import com.bna.habil.application.dto.HrPersonnelDto;
import com.bna.habil.domain.entities.extra.DummyEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HrPersonnelRepository extends JpaRepository<DummyEntity, Long> {

    /**
     * Paginated query for HR Personnel with search criteria
     */
    @Query(value = """
            SELECT
                TRIM(b.MATCLE)  AS matcle,
                TRIM(b.PRENOM)  AS prenom,
                TRIM(b.NOMUSE)  AS nomuse,
                TRIM(e.NOIDEN) AS cin,
                TRIM(a.IDJB00) AS idjb00,
                TRIM(d.LBJBLG) AS lbjblg,
                TRIM(g.LBOULG) AS lboulg,
                TRIM(f.IDOU00) AS idou00
            FROM zy3b@HR.BNA.TN a
            JOIN zy00@HR.BNA.TN b ON a.nudoss = b.nudoss
            JOIN zy12@HR.BNA.TN e ON e.nudoss = b.nudoss
            JOIN zc00@HR.BNA.TN c ON a.IDJB00 = c.IDJB00
            JOIN zc01@HR.BNA.TN d ON c.nudoss = d.nudoss
            JOIN ze00@HR.BNA.TN f ON a.IDOU00 = f.IDOU00
            JOIN ze01@HR.BNA.TN g ON f.nudoss = g.nudoss
            WHERE (:cin IS NULL OR TRIM(e.NOIDEN) = :cin)
              AND (:matcle IS NULL OR TRIM(b.MATCLE) = :matcle)
              AND (:search IS NULL OR :search = ''
                   OR LOWER(TRIM(b.MATCLE)) LIKE LOWER('%' || :search || '%')
                   OR LOWER(TRIM(b.PRENOM)) LIKE LOWER('%' || :search || '%')
                   OR LOWER(TRIM(b.NOMUSE)) LIKE LOWER('%' || :search || '%')
                   OR LOWER(TRIM(b.PRENOM) || ' ' || TRIM(b.NOMUSE)) LIKE LOWER('%' || :search || '%')
                   OR LOWER(TRIM(b.NOMUSE) || ' ' || TRIM(b.PRENOM)) LIKE LOWER('%' || :search || '%'))
              AND TO_CHAR(a.DTEN00, 'DD/MM/RR') = '31/12/99'
            ORDER BY b.MATCLE
            """,
            countQuery = """
            SELECT COUNT(*)
            FROM zy3b@HR.BNA.TN a
            JOIN zy00@HR.BNA.TN b ON a.nudoss = b.nudoss
            JOIN zy12@HR.BNA.TN e ON e.nudoss = b.nudoss
            JOIN zc00@HR.BNA.TN c ON a.IDJB00 = c.IDJB00
            JOIN zc01@HR.BNA.TN d ON c.nudoss = d.nudoss
            JOIN ze00@HR.BNA.TN f ON a.IDOU00 = f.IDOU00
            JOIN ze01@HR.BNA.TN g ON f.nudoss = g.nudoss
            WHERE (:cin IS NULL OR TRIM(e.NOIDEN) = :cin)
              AND (:matcle IS NULL OR TRIM(b.MATCLE) = :matcle)
              AND (:search IS NULL OR :search = ''
                   OR LOWER(TRIM(b.MATCLE)) LIKE LOWER('%' || :search || '%')
                   OR LOWER(TRIM(b.PRENOM)) LIKE LOWER('%' || :search || '%')
                   OR LOWER(TRIM(b.NOMUSE)) LIKE LOWER('%' || :search || '%')
                   OR LOWER(TRIM(b.PRENOM) || ' ' || TRIM(b.NOMUSE)) LIKE LOWER('%' || :search || '%')
                   OR LOWER(TRIM(b.NOMUSE) || ' ' || TRIM(b.PRENOM)) LIKE LOWER('%' || :search || '%'))
              AND TO_CHAR(a.DTEN00, 'DD/MM/RR') = '31/12/99'
            """,
            nativeQuery = true)
    Page<HrPersonnelDto> findHrPersonnelWithSearch(
            @Param("search") String search,
            @Param("cin") String cin,
            @Param("matcle") String matcle,
            Pageable pageable
    );

    /**
     * Alternative: Separate search parameters for each field
     */
    @Query(value = """
            SELECT
                TRIM(b.MATCLE)  AS matcle,
                TRIM(b.PRENOM)  AS prenom,
                TRIM(b.NOMUSE)  AS nomuse,
                TRIM(e.NOIDEN) AS cin,
                TRIM(a.IDJB00) AS idjb00,
                TRIM(d.LBJBLG) AS lbjblg,
                TRIM(g.LBOULG) AS lboulg,
                TRIM(f.IDOU00) AS idou00
            FROM zy3b@HR.BNA.TN a
            JOIN zy00@HR.BNA.TN b ON a.nudoss = b.nudoss
            JOIN zy12@HR.BNA.TN e ON e.nudoss = b.nudoss
            JOIN zc00@HR.BNA.TN c ON a.IDJB00 = c.IDJB00
            JOIN zc01@HR.BNA.TN d ON c.nudoss = d.nudoss
            JOIN ze00@HR.BNA.TN f ON a.IDOU00 = f.IDOU00
            JOIN ze01@HR.BNA.TN g ON f.nudoss = g.nudoss
            WHERE (:cin IS NULL OR TRIM(e.NOIDEN) = :cin)
              AND (:matcle IS NULL OR LOWER(TRIM(b.MATCLE)) LIKE LOWER('%' || :matcle || '%'))
              AND (:prenom IS NULL OR LOWER(TRIM(b.PRENOM)) LIKE LOWER('%' || :prenom || '%'))
              AND (:nomuse IS NULL OR LOWER(TRIM(b.NOMUSE)) LIKE LOWER('%' || :nomuse || '%'))
              AND TO_CHAR(a.DTEN00, 'DD/MM/RR') = '31/12/99'
            ORDER BY b.MATCLE
            """,
            countQuery = """
            SELECT COUNT(*)
            FROM zy3b@HR.BNA.TN a
            JOIN zy00@HR.BNA.TN b ON a.nudoss = b.nudoss
            JOIN zy12@HR.BNA.TN e ON e.nudoss = b.nudoss
            JOIN zc00@HR.BNA.TN c ON a.IDJB00 = c.IDJB00
            JOIN zc01@HR.BNA.TN d ON c.nudoss = d.nudoss
            JOIN ze00@HR.BNA.TN f ON a.IDOU00 = f.IDOU00
            JOIN ze01@HR.BNA.TN g ON f.nudoss = g.nudoss
            WHERE (:cin IS NULL OR TRIM(e.NOIDEN) = :cin)
              AND (:matcle IS NULL OR LOWER(TRIM(b.MATCLE)) LIKE LOWER('%' || :matcle || '%'))
              AND (:prenom IS NULL OR LOWER(TRIM(b.PRENOM)) LIKE LOWER('%' || :prenom || '%'))
              AND (:nomuse IS NULL OR LOWER(TRIM(b.NOMUSE)) LIKE LOWER('%' || :nomuse || '%'))
              AND TO_CHAR(a.DTEN00, 'DD/MM/RR') = '31/12/99'
            """,
            nativeQuery = true)
    Page<HrPersonnelDto> findHrPersonnelWithFilters(
            @Param("cin") String cin,
            @Param("matcle") String matcle,
            @Param("prenom") String prenom,
            @Param("nomuse") String nomuse,
            Pageable pageable
    );

    /**
     * Non-paginated method
     */
    @Query(value = """
            SELECT
                TRIM(b.MATCLE)  AS matcle,
                TRIM(b.PRENOM)  AS prenom,
                TRIM(b.NOMUSE)  AS nomuse,
                TRIM(e.NOIDEN) AS cin,
                TRIM(a.IDJB00) AS idjb00,
                TRIM(d.LBJBLG) AS lbjblg,
                TRIM(g.LBOULG) AS lboulg,
                TRIM(f.IDOU00) AS idou00
            FROM zy3b@HR.BNA.TN a
            JOIN zy00@HR.BNA.TN b ON a.nudoss = b.nudoss
            JOIN zy12@HR.BNA.TN e ON e.nudoss = b.nudoss
            JOIN zc00@HR.BNA.TN c ON a.IDJB00 = c.IDJB00
            JOIN zc01@HR.BNA.TN d ON c.nudoss = d.nudoss
            JOIN ze00@HR.BNA.TN f ON a.IDOU00 = f.IDOU00
            JOIN ze01@HR.BNA.TN g ON f.nudoss = g.nudoss
            WHERE (:cin IS NULL OR TRIM(e.NOIDEN) = :cin)
            AND TO_CHAR(a.DTEN00, 'DD/MM/RR') = '31/12/99'
            ORDER BY b.MATCLE
            """, nativeQuery = true)
    List<HrPersonnelDto> findHrPersonnel(@Param("cin") String cin);

    @Query(value = "SELECT TRIM(a.IDJB00) AS codePoste " +
            "FROM zy3b@HR.BNA.TN a " +
            "JOIN zy00@HR.BNA.TN b ON a.nudoss = b.nudoss " +
            "WHERE TRIM(b.MATCLE) = :matcle " +
            "AND TO_CHAR(a.DTEN00, 'DD/MM/RR') = '31/12/99' " +
            "FETCH FIRST 1 ROWS ONLY",
            nativeQuery = true)
    String findPosteByMatricule(@Param("matcle") String matcle);

    @Query(value = "SELECT COUNT(*) FROM zy3b@HR.BNA.TN a " +
            "JOIN zy00@HR.BNA.TN b ON a.nudoss = b.nudoss " +
            "WHERE TRIM(b.MATCLE) = :matcle " +
            "AND TRIM(a.IDJB00) = :codePoste " +
            "AND TO_CHAR(a.DTEN00, 'DD/MM/RR') = '31/12/99'",
            nativeQuery = true)
    int countByMatriculeAndPoste(@Param("matcle") String matcle, @Param("codePoste") String codePoste);

}
