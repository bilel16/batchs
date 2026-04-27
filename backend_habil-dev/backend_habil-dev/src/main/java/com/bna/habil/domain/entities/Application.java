package com.bna.habil.domain.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "APPLICATION", schema = "habil")
public class Application implements java.io.Serializable {

    @Id
    private String cod_app_app;

    private String lib_app_app;

    private String lib_lab_app;

    public String getCod_app_app() {
        return cod_app_app;
    }

    public void setCod_app_app(String cod_app_app) {
        this.cod_app_app = cod_app_app;
    }

    public String getLib_app_app() {
        return lib_app_app;
    }

    public void setLib_app_app(String lib_app_app) {
        this.lib_app_app = lib_app_app;
    }

    public String getLib_lab_app() {
        return lib_lab_app;
    }

    public void setLib_lab_app(String lib_lab_app) {
        this.lib_lab_app = lib_lab_app;
    }


}
