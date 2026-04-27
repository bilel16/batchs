package com.bna.habil.domain.entities.interim;

import com.bna.habil.domain.beans.interim.EtatInterim;
import com.bna.habil.domain.beans.interim.ValidInterim;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
@Entity
@Table(name = "INTERIM_HABIL", schema = "habil")
@ValidInterim
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Interim implements java.io.Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "interim_seq")
    @SequenceGenerator(name = "interim_seq", sequenceName = "habil.SEQ_INTERIM_HABIL", allocationSize = 1)
    @Column(name = "ID")
    private Long id;

    // ─── WHO ───────────────────────────────────────────────────────

    @NotNull(message = "Le matricule source est obligatoire")
    @Min(value = 1000, message = "Le matricule source doit être un nombre de 4 chiffres")
    @Max(value = 9999, message = "Le matricule source doit être un nombre de 4 chiffres")
    @Column(name = "NUM_MATR_USER", nullable = false)
    private Integer matriculeSource;

    @NotNull(message = "Le matricule cible est obligatoire")
    @Min(value = 1000, message = "Le matricule cible doit être un nombre de 4 chiffres")
    @Max(value = 9999, message = "Le matricule cible doit être un nombre de 4 chiffres")
    @Column(name = "MATR_USER", nullable = false)
    private Integer matriculeCible;

    // ─── WHERE ─────────────────────────────────────────────────────

    @NotNull(message = "Le code STRUCTURE d'origine est obligatoire")
    @Positive(message = "Le code STRUCTURE d'origine doit être un nombre positif")
    @Column(name = "COD_STRC_ORIGINE", nullable = false)
    private Integer codStrcOrigine;

    @NotNull(message = "Le code STRUCTURE de destination est obligatoire")
    @Positive(message = "Le code STRUCTURE de destination doit être un nombre positif")
    @Column(name = "COD_STRC_STRC", nullable = false)
    private Integer codStrcDestination;

    // ─── WHEN ──────────────────────────────────────────────────────

    @NotNull(message = "La date de début d'intérim est obligatoire")
    @Temporal(TemporalType.DATE)
    @Column(name = "DAT_DEB_INT", nullable = false)
    private Date dateDebutInterim;

    @NotNull(message = "La date de fin d'intérim est obligatoire")
    @Temporal(TemporalType.DATE)
    @Column(name = "DAT_FIN_INT", nullable = false)
    private Date dateFinInterim;



    // ─── AUDIT ─────────────────────────────────────────────────────

    @NotNull(message = "La date d'opération est obligatoire")
    @PastOrPresent(message = "La date d'opération ne peut pas être dans le futur")
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "DATE_OPERATION", nullable = false)
    private Date dateOperation;

    // ─── STATUS ────────────────────────────────────────────────────

    @NotNull(message = "L'état est obligatoire")
    @Enumerated(EnumType.STRING)
    @Column(name = "ETAT", nullable = false, length = 15)
    private EtatInterim etat;
}