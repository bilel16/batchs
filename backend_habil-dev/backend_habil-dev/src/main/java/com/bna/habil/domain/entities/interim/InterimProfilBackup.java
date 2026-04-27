package com.bna.habil.domain.entities.interim;


import jakarta.persistence.*;
import lombok.*;

import java.io.Serial;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;

@Entity
@Table(name = "INTERIM_PROFIL_BACKUP")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class InterimProfilBackup implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "seq_interim_profil_backup"
    )
    @SequenceGenerator(
            name = "seq_interim_profil_backup",
            sequenceName = "SEQ_INTERIM_PROFIL_BACKUP",
            allocationSize = 1
    )
    private Long id;

    @Column(name = "INTERIM_ID", nullable = false)
    private Long interimId;

    @Column(name = "NUM_MATR_USER", nullable = false, length = 20)
    private String numMatrUser;

    @Column(name = "COD_PFL_PFL", nullable = false, length = 50)
    private String codPflPfl;

    @Column(name = "BOOL_ETAT_UTPR", nullable = false)
    private Integer boolEtatUtpr;

    @Column(name = "DAT_DADH_UTPR")
    @Temporal(TemporalType.DATE)
    private Date datDadhUtpr;

    @Column(name = "DAT_FADH_UTPR")
    @Temporal(TemporalType.DATE)
    private Date datFadhUtpr;

    @Column(name = "BOOL_CUSTOM_PROFIL")
    private Integer boolCustomProfil;

    @Column(name = "DATE_BACKUP", nullable = false)
    private Timestamp dateBackup;

    @PrePersist
    protected void onCreate() {
        if (dateBackup == null) {
            dateBackup = new Timestamp(System.currentTimeMillis());
        }
    }
}