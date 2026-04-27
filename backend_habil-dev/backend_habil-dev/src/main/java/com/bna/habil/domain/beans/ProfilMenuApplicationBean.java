package com.bna.habil.domain.beans;


import java.io.Serial;
import java.io.Serializable;


public class ProfilMenuApplicationBean implements Serializable {
    @Serial
    private static final long serialVersionUID = -4588473658643004567L;
    private String codAppApp;
    private String codMenuMenu;
    private String codPflPfl;
    private String codTstrcTstrc;
    private Integer boolEtatPma;
    private Integer id_profil_menu_app;


    public String getCodAppApp() {
        return codAppApp;
    }

    public Integer getId_profil_menu_app() {
        return id_profil_menu_app;
    }

    public void setId_profil_menu_app(Integer id_profil_menu_app) {
        this.id_profil_menu_app = id_profil_menu_app;
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

    public ProfilMenuApplicationBean(String codAppApp, String codMenuMenu, String codPflPfl, String codTstrcTstrc,
                                     Integer boolEtatPma, Integer id_profil_menu_app) {
        super();
        this.codAppApp = codAppApp;
        this.codMenuMenu = codMenuMenu;
        this.codPflPfl = codPflPfl;
        this.codTstrcTstrc = codTstrcTstrc;
        this.boolEtatPma = boolEtatPma;
        this.id_profil_menu_app = id_profil_menu_app;
    }

    public ProfilMenuApplicationBean() {
        super();
    }

}
