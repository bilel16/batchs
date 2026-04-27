package com.bna.smile.model.reporting.model;

import com.oxia.fwk.core.ValueObject;

public class ParamRetraitVo extends ValueObject {

    private String numMatrUser;
    private String libEtat;
    private String path;
    private String numOperMp;
    
    public ParamRetraitVo() {
    }

    public void setNumMatrUser(String numMatrUser) {
        this.numMatrUser = numMatrUser;
    }

    public String getNumMatrUser() {
        return numMatrUser;
    }

    public void setLibEtat(String libEtat) {
        this.libEtat = libEtat;
    }

    public String getLibEtat() {
        return libEtat;
    }


    public void setNumOperMp(String numOperMp) {
        this.numOperMp = numOperMp;
    }

    public String getNumOperMp() {
        return numOperMp;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getPath() {
        return path;
    }
}
