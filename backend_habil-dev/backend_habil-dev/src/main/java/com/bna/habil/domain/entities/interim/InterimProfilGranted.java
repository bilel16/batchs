package com.bna.habil.domain.entities.interim;


import jakarta.persistence.*;
import lombok.*;

import java.io.Serial;
import java.io.Serializable;
import java.sql.Timestamp;

@Entity
@Table(name = "INTERIM_PROFIL_GRANTED")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class InterimProfilGranted implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "seq_interim_profil_granted"
    )
    @SequenceGenerator(
            name = "seq_interim_profil_granted",
            sequenceName = "SEQ_INTERIM_PROFIL_GRANTED",
            allocationSize = 1
    )
    private Long id;

    @Column(name = "INTERIM_ID", nullable = false)
    private Long interimId;

    @Column(name = "NUM_MATR_USER", nullable = false, length = 20)
    private String numMatrUser;

    @Column(name = "COD_PFL_PFL", nullable = false, length = 50)
    private String codPflPfl;

    @Column(name = "PREVIOUSLY_EXISTED", nullable = false)
    private Integer previouslyExisted;

    @Column(name = "PREVIOUS_ETAT")
    private Integer previousEtat;

    @Column(name = "DATE_GRANTED", nullable = false)
    private Timestamp dateGranted;

    @PrePersist
    protected void onCreate() {
        if (dateGranted == null) {
            dateGranted = new Timestamp(System.currentTimeMillis());
        }
    }
}

