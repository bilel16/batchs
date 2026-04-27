package com.bna.habil.infrastructure.persistence.repositories.interim;

import com.bna.habil.domain.beans.interim.EtatInterim;
import com.bna.habil.domain.entities.interim.Interim;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
public interface InterimRepository extends JpaRepository<Interim, Long> {

    // Find interims that should be ACTIVATED today
    @Query("""
        SELECT i FROM Interim i 
        WHERE i.etat = 'EN_ATTENTE' 
        AND i.dateDebutInterim <= :today
    """)
    List<Interim> findInterimsToActivate(@Param("today") Date today);

    // Find interims that should be TERMINATED today
    @Query("""
        SELECT i FROM Interim i 
        WHERE i.etat = 'ACTIF' 
        AND i.dateFinInterim < :today
    """)
    List<Interim> findInterimsToTerminate(@Param("today") Date today);

    // Check for overlapping interims for same person
    @Query("""
        SELECT COUNT(i) > 0 FROM Interim i 
        WHERE i.matriculeCible = :matricule 
        AND i.etat IN ('EN_ATTENTE', 'ACTIF')
        AND i.dateDebutInterim <= :dateFin 
        AND i.dateFinInterim >= :dateDebut
        AND (:excludeId IS NULL OR i.id <> :excludeId)
    """)
    boolean existsOverlappingInterim(
            @Param("matricule") Integer matricule,
            @Param("dateDebut") Date dateDebut,
            @Param("dateFin") Date dateFin,
            @Param("excludeId") Long excludeId
    );

    // ═══════════════════════════════════════════════════════════════
    // NEW QUERIES
    // ═══════════════════════════════════════════════════════════════

    /**
     * Find all interims for a specific source user (the one being replaced)
     */
    List<Interim> findByMatriculeSourceOrderByDateDebutInterimDesc(Integer matriculeSource);

    /**
     * Find all interims for a specific cible user (the one replacing)
     */
    List<Interim> findByMatriculeCibleOrderByDateDebutInterimDesc(Integer matriculeCible);

    /**
     * Find all interims by state
     */
    List<Interim> findByEtatOrderByDateDebutInterimDesc(EtatInterim etat);

    /**
     * Find all interims for a specific structure
     */
    List<Interim> findByCodStrcDestinationOrderByDateDebutInterimDesc(Integer codStrcDestination);

    /**
     * Find all interims involving a user (as source OR cible)
     */
    @Query("""
        SELECT i FROM Interim i
        WHERE i.matriculeSource = :matricule
           OR i.matriculeCible = :matricule
        ORDER BY i.dateDebutInterim DESC
    """)
    List<Interim> findByUser(@Param("matricule") Integer matricule);

    /**
     * Find active interim for a specific cible user
     */
    @Query("""
        SELECT i FROM Interim i
        WHERE i.matriculeCible = :matricule
        AND i.etat = 'ACTIF'
    """)
    Optional<Interim> findActiveInterimForCible(@Param("matricule") Integer matricule);

    /**
     * Find active interim for a specific source user
     */
    @Query("""
        SELECT i FROM Interim i
        WHERE i.matriculeSource = :matricule
        AND i.etat = 'ACTIF'
    """)
    Optional<Interim> findActiveInterimForSource(@Param("matricule") Integer matricule);

    /**
     * Count interims by state
     */
    long countByEtat(EtatInterim etat);

    @Query("""
    SELECT COUNT(i) FROM Interim i
    WHERE i.etat = :etat
    AND (i.matriculeSource IN :matricules OR i.matriculeCible IN :matricules)
    """)
    long countByEtatAndManagedMatricules(@Param("etat") EtatInterim etat,
                                         @Param("matricules") Set<Integer> matricules);

    /**
     * Scheduler queries
     */
    @Query("""
        SELECT i FROM Interim i
        WHERE i.etat = 'EN_ATTENTE'
        AND i.dateDebutInterim <= CURRENT_DATE
    """)
    List<Interim> findInterimsToActivate();

    @Query("""
        SELECT i FROM Interim i
        WHERE i.etat = 'ACTIF'
        AND i.dateFinInterim < CURRENT_DATE
    """)
    List<Interim> findInterimsToTerminate();

    /**
     * Search interims with filters
     */
    @Query("""
        SELECT i FROM Interim i
        WHERE (:matriculeSource IS NULL OR i.matriculeSource = :matriculeSource)
        AND (:matriculeCible IS NULL OR i.matriculeCible = :matriculeCible)
        AND (:etat IS NULL OR i.etat = :etat)
        AND (:codStrc IS NULL OR i.codStrcDestination = :codStrc)
        AND (:dateDebut IS NULL OR i.dateDebutInterim >= :dateDebut)
        AND (:dateFin IS NULL OR i.dateFinInterim <= :dateFin)
        ORDER BY i.dateDebutInterim DESC
    """)
    List<Interim> searchInterims(
            @Param("matriculeSource") Integer matriculeSource,
            @Param("matriculeCible") Integer matriculeCible,
            @Param("etat") EtatInterim etat,
            @Param("codStrc") Integer codStrc,
            @Param("dateDebut") Date dateDebut,
            @Param("dateFin") Date dateFin);

    @Query("""
    SELECT i FROM Interim i
    WHERE i.matriculeSource IN :matricules OR i.matriculeCible IN :matricules
    ORDER BY i.dateDebutInterim DESC
""")
    List<Interim> findByManagedMatricules(@Param("matricules") Set<Integer> matricules);

    @Query("""
    SELECT i FROM Interim i
    WHERE (i.matriculeSource IN :matricules OR i.matriculeCible IN :matricules)
    AND (:matriculeSource IS NULL OR i.matriculeSource = :matriculeSource)
    AND (:matriculeCible IS NULL OR i.matriculeCible = :matriculeCible)
    AND (:etat IS NULL OR i.etat = :etat)
    AND (:codStrc IS NULL OR i.codStrcDestination = :codStrc)
    AND (:dateDebut IS NULL OR i.dateDebutInterim >= :dateDebut)
    AND (:dateFin IS NULL OR i.dateFinInterim <= :dateFin)
    ORDER BY i.dateDebutInterim DESC
""")
    List<Interim> searchInterimsByManagedMatricules(
            @Param("matricules") Set<Integer> matricules,
            @Param("matriculeSource") Integer matriculeSource,
            @Param("matriculeCible") Integer matriculeCible,
            @Param("etat") EtatInterim etat,
            @Param("codStrc") Integer codStrc,
            @Param("dateDebut") Date dateDebut,
            @Param("dateFin") Date dateFin);
}