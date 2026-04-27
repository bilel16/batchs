package com.bna.habil.infrastructure.persistence.repositories.interim;

import com.bna.habil.domain.entities.interim.InterimProfilBackup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InterimProfilBackupRepository
        extends JpaRepository<InterimProfilBackup, Long> {

    List<InterimProfilBackup> findByInterimId(Long interimId);

    List<InterimProfilBackup> findByInterimIdAndNumMatrUser(
            Long interimId, String numMatrUser);

    boolean existsByInterimId(Long interimId);

    @Modifying
    @Query("DELETE FROM InterimProfilBackup b WHERE b.interimId = :interimId")
    void deleteByInterimId(@Param("interimId") Long interimId);

    long countByInterimId(Long interimId);
}
