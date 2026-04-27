package com.bna.habil.infrastructure.persistence.repositories.interim;

import com.bna.habil.domain.entities.interim.InterimProfilGranted;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InterimProfilGrantedRepository
        extends JpaRepository<InterimProfilGranted, Long> {

    List<InterimProfilGranted> findByInterimId(Long interimId);

    List<InterimProfilGranted> findByInterimIdAndNumMatrUser(
            Long interimId, String numMatrUser);

    boolean existsByInterimId(Long interimId);

    @Modifying
    @Query("DELETE FROM InterimProfilGranted g WHERE g.interimId = :interimId")
    void deleteByInterimId(@Param("interimId") Long interimId);

    long countByInterimId(Long interimId);
}