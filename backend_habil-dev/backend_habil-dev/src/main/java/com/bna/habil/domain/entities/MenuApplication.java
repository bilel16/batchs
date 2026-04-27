package com.bna.habil.domain.entities;


import java.io.Serial;
import java.io.Serializable;

import com.bna.habil.domain.entities.entitiesId.MenuApplicationId;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.Table;

@Entity
@IdClass(MenuApplicationId.class)
@Table(name = "MENU_APPLICATION")
public class MenuApplication implements Serializable {
    @Serial
    private static final long serialVersionUID = 7370672918102648182L;

    @Id
    @Column(name = "COD_APP_APP")
    private String codAppApp;

    @Id
    @Column(name = "COD_MENU_MENU")
    private String codMenuMenu;

    @Column(name = "LIB_MENU_MENU")
    private String libMenuMenu;

    // Getters & setters
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
}
