package com.bna.habil.domain.entities.entitiesId;

import java.io.Serializable;
import java.util.Objects;

public class MenuApplicationId implements Serializable {

    private String codAppApp;
    private String codMenuMenu;

    public MenuApplicationId() {
    }

    public MenuApplicationId(String codAppApp, String codMenuMenu) {
        this.codAppApp = codAppApp;
        this.codMenuMenu = codMenuMenu;
    }

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

    // equals & hashCode (required!)
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof MenuApplicationId that)) return false;
        return Objects.equals(codAppApp, that.codAppApp) &&
                Objects.equals(codMenuMenu, that.codMenuMenu);
    }

    @Override
    public int hashCode() {
        return Objects.hash(codAppApp, codMenuMenu);
    }
}
