package com.bna.habil.domain.entities;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;

import lombok.NoArgsConstructor;

import lombok.ToString;

import java.io.Serial;
import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor

@ToString
@Entity
@Table(name = "profil")
public class Profil implements Serializable {
    @Serial
    private static final long serialVersionUID = 2405172041950251807L;

    @Id
    @Column(name = "COD_PFL_PFL")
    private String codPflPfl;

    @Column(name = "LIB_PFL_PFL")
    private String libpflpfl;

    @Column(name = "LIB_HDEB_PFL")
    private String libhdebpfl;

    public String getCodPflPfl() {
        return codPflPfl;
    }

    public void setCodPflPfl(String codPflPfl) {
        this.codPflPfl = codPflPfl;
    }

    public String getLibpflpfl() {
        return libpflpfl;
    }

    public void setLibpflpfl(String libpflpfl) {
        this.libpflpfl = libpflpfl;
    }

    public String getLibhdebpfl() {
        return libhdebpfl;
    }

    public void setLibhdebpfl(String libhdebpfl) {
        this.libhdebpfl = libhdebpfl;
    }

    public String getLibhfinpfl() {
        return libhfinpfl;
    }

    public void setLibhfinpfl(String libhfinpfl) {
        this.libhfinpfl = libhfinpfl;
    }

    public String getCodNivhPfl() {
        return codNivhPfl;
    }

    public void setCodNivhPfl(String codNivhPfl) {
        this.codNivhPfl = codNivhPfl;
    }

    public String getBoolEtatPfl() {
        return boolEtatPfl;
    }

    public void setBoolEtatPfl(String boolEtatPfl) {
        this.boolEtatPfl = boolEtatPfl;
    }

    public String getBoolJouvPfl() {
        return boolJouvPfl;
    }

    public void setBoolJouvPfl(String boolJouvPfl) {
        this.boolJouvPfl = boolJouvPfl;
    }

    public String getCodAppApp() {
        return codAppApp;
    }

    public void setCodAppApp(String codAppApp) {
        this.codAppApp = codAppApp;
    }

    public String getCodCatpPfl() {
        return codCatpPfl;
    }

    public void setCodCatpPfl(String codCatpPfl) {
        this.codCatpPfl = codCatpPfl;
    }

    @Column(name = "LIB_HFIN_PFL")
    private String libhfinpfl;

    @Column(name = "COD_NIVH_PFL")
    private String codNivhPfl;

    @Column(name = "BOOL_ETAT_PFL")
    private String boolEtatPfl;

    @Column(name = "BOOL_JOUV_PFL")
    private String boolJouvPfl;

    @Column(name = "COD_APP_APP")

    private String codAppApp;

    @Column(name = "COD_CATP_PFL")
    private String codCatpPfl;


}
