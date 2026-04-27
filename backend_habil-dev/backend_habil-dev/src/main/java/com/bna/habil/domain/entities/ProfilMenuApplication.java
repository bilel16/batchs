package com.bna.habil.domain.entities;

import java.io.Serial;
import java.io.Serializable;

import com.bna.habil.domain.entities.entitiesId.ProfilMenuApplicationId;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Entity
@IdClass(ProfilMenuApplicationId.class)
@Table(name = "profil_menu_application")
@AllArgsConstructor
@NoArgsConstructor
public class ProfilMenuApplication implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "COD_APP_APP")
    private String codAppApp;

    @Id
    @Column(name = "COD_MENU_MENU")
    private String codMenuMenu;

    @Id
    @Column(name = "COD_PFL_PFL")
    private String codPflPfl;

    @Id
    @Column(name = "COD_TSTRC_TSTRC")
    private String codTstrcTstrc;

    @Column(name = "BOOL_ETAT_PMA")
    private Integer boolEtatPma;

    public String getCodAppApp() {
        return codAppApp;
    }

    public void setCodAppApp(String codAppApp) {
        this.codAppApp = codAppApp;
    }

    public String getCodMenuMenu() {
        return codMenuMenu;
    }

    public void setCodMenuMenu(String codMenuMenu) {
        this.codMenuMenu = codMenuMenu;
    }

    public String getCodPflPfl() {
        return codPflPfl;
    }

    public void setCodPflPfl(String codPflPfl) {
        this.codPflPfl = codPflPfl;
    }

    public String getCodTstrcTstrc() {
        return codTstrcTstrc;
    }

    public void setCodTstrcTstrc(String codTstrcTstrc) {
        this.codTstrcTstrc = codTstrcTstrc;
    }

    public Integer getBoolEtatPma() {
        return boolEtatPma;
    }

    public void setBoolEtatPma(Integer boolEtatPma) {
        this.boolEtatPma = boolEtatPma;
    }

    public static long getSerialversionuid() {
        return serialVersionUID;
    }

}
