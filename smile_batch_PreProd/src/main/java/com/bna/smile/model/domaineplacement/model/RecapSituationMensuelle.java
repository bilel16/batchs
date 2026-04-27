package com.bna.smile.model.domaineplacement.model;

import com.oxia.fwk.core.ValueObject;

public class RecapSituationMensuelle extends ValueObject{
    public RecapSituationMensuelle() {
    }
    
    private Long codStrcStrc;
    private String mntCapital;
    private String mntIntertServi;
    private String mntIntertServiBrut;
    private String mntIrcServi;
    private String libProduit;
    private String libCategorie;

    public void setCodStrcStrc(Long codStrcStrc) {
        this.codStrcStrc = codStrcStrc;
    }

    public Long getCodStrcStrc() {
        return codStrcStrc;
    }

   
    public void setLibProduit(String libProduit) {
        this.libProduit = libProduit;
    }

    public String getLibProduit() {
        return libProduit;
    }

    public void setLibCategorie(String libCategorie) {
        this.libCategorie = libCategorie;
    }

    public String getLibCategorie() {
        return libCategorie;
    }

    public void setMntIrcServi(String mntIrcServi) {
        this.mntIrcServi = mntIrcServi;
    }

    public String getMntIrcServi() {
        return mntIrcServi;
    }

    public void setMntIntertServi(String mntIntertServi) {
        this.mntIntertServi = mntIntertServi;
    }

    public String getMntIntertServi() {
        return mntIntertServi;
    }

    public void setMntCapital(String mntCapital) {
        this.mntCapital = mntCapital;
    }

    public String getMntCapital() {
        return mntCapital;
    }

    public void setMntIntertServiBrut(String mntIntertServiBrut) {
        this.mntIntertServiBrut = mntIntertServiBrut;
    }

    public String getMntIntertServiBrut() {
        return mntIntertServiBrut;
    }
}
