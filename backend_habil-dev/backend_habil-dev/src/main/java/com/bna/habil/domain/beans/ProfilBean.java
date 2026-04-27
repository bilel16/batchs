package com.bna.habil.domain.beans;


import java.io.Serial;
import java.io.Serializable;


public class ProfilBean implements Serializable {
    @Serial
    private static final long serialVersionUID = -4588473658643004567L;


    private String codPflPfl;


    private String libpflpfl;


    private String libhdebpfl;


    private String libhfinpfl;


    private String codNivhPfl;


    private String boolEtatPfl;


    private String boolJouvPfl;


    private String codAppApp;


    private String codCatpPfl;


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


    public static long getSerialversionuid() {
        return serialVersionUID;
    }


    public ProfilBean(String codPflPfl, String libpflpfl, String libhdebpfl, String libhfinpfl, String codNivhPfl,
                      String boolEtatPfl, String boolJouvPfl, String codAppApp, String codCatpPfl) {
        super();
        this.codPflPfl = codPflPfl;
        this.libpflpfl = libpflpfl;
        this.libhdebpfl = libhdebpfl;
        this.libhfinpfl = libhfinpfl;
        this.codNivhPfl = codNivhPfl;
        this.boolEtatPfl = boolEtatPfl;
        this.boolJouvPfl = boolJouvPfl;
        this.codAppApp = codAppApp;
        this.codCatpPfl = codCatpPfl;
    }


    public ProfilBean() {
        super();
    }


}
