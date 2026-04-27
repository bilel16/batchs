package com.bna.habil.infrastructure.security.model;

import com.bna.habil.domain.entities.extra.MenuData;

public class MenuResponse {

    private int returnCode;
    private String message;
    private MenuData menuData;


    public int getReturnCode() {
        return returnCode;
    }

    public void setReturnCode(int returnCode) {
        this.returnCode = returnCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public MenuData getMenuData() {
        return menuData;
    }

    public void setMenuData(MenuData menuData) {
        this.menuData = menuData;
    }

    public MenuResponse(int returnCode, String message, MenuData menuData) {
        super();
        this.returnCode = returnCode;
        this.message = message;
        this.menuData = menuData;
    }

    public MenuResponse() {
        super();

    }

}

