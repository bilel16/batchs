package com.bna.habil.domain.entities;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;

import com.bna.habil.domain.entities.entitiesId.UtilisateurProfilId;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor

@ToString
@Entity
@Table(name = "utilisateur_profil")
public class UtilisateurProfil implements Serializable {
    @Serial
    private static final long serialVersionUID = 2160584291970206280L;


    @EmbeddedId
    private UtilisateurProfilId id;


    @MapsId("codPflPfl")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.RESTRICT)
    @JoinColumn(name = "COD_PFL_PFL", nullable = false)
    private Profil profil;

    @Column(name = "DAT_FADH_UTPR")
    @Temporal(TemporalType.DATE)
    private Date datFadhUtpr;

    @Column(name = "DAT_DADH_UTPR")
    @Temporal(TemporalType.DATE)
    private Date datdadhutpr;

    @Column(name = "BOOL_ETAT_UTPR")
    private Integer boolEtatUtpr;

    @Column(name = "BOOL_CUSTOM_PROFIL")
    private Integer boolCustomProfil = 0;


    public UtilisateurProfilId getId() {
        return id;
    }

    public Integer getBoolCustomProfil() {
        return boolCustomProfil;
    }

    public void setBoolCustomProfil(Integer boolCustomProfil) {
        this.boolCustomProfil = boolCustomProfil;
    }


    public void setId(UtilisateurProfilId id) {
        this.id = id;
    }

    public Profil getProfil() {
        return profil;
    }

    public void setProfil(Profil profil) {
        this.profil = profil;
    }

    public Date getDatFadhUtpr() {
        return datFadhUtpr;
    }

    public void setDatFadhUtpr(Date datFadhUtpr) {
        this.datFadhUtpr = datFadhUtpr;
    }

    public Date getDatdadhutpr() {
        return datdadhutpr;
    }

    public void setDatdadhutpr(Date datdadhutpr) {
        this.datdadhutpr = datdadhutpr;
    }

    public Integer getBoolEtatUtpr() {
        return boolEtatUtpr;
    }

    public void setBoolEtatUtpr(Integer boolEtatUtpr) {
        this.boolEtatUtpr = boolEtatUtpr;
    }

    public static long getSerialversionuid() {
        return serialVersionUID;
    }

}
