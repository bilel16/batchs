package com.bna.habil.domain.beans;


import java.io.Serial;
import java.io.Serializable;


public class MenuApplicationBean implements Serializable {
    @Serial
    private static final long serialVersionUID = -4588473658643004567L;

    private String codAppApp;
    private String codMenuMenu;
    private String libMenuMenu;

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

    public String getLibMenuMenu() {
        return libMenuMenu;
    }

    public void setLibMenuMenu(String libMenuMenu) {
        this.libMenuMenu = libMenuMenu;
    }

    public static long getSerialversionuid() {
        return serialVersionUID;
    }

    public MenuApplicationBean(String codAppApp, String codMenuMenu, String libMenuMenu) {
        super();
        this.codAppApp = codAppApp;
        this.codMenuMenu = codMenuMenu;
        this.libMenuMenu = libMenuMenu;
    }

    public MenuApplicationBean() {
        super();
    }
}
