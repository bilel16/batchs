package com.bna.smile.web.clotureDomaine.view;

public class OperMoyPayView extends com.oxia.fwk.core.ValueObject implements java.io.Serializable {
   
    private String produit;
    private String relation;
    private String contratDav;
    private String contratPlacemant;
    private String montantPlacement;
    private String echeance;
    private String interertServi;
    private String irc;
    
    public OperMoyPayView() {
    
        produit="";
        relation="";
        contratDav="";
        contratPlacemant="";
        montantPlacement="";
        echeance="";
        interertServi="";
        irc="";
    }
    public void setProduit(String produit) {
        this.produit = produit;
    }

    public String getProduit() {
        return produit;
    }

    public void setRelation(String relation) {
        this.relation = relation;
    }

    public String getRelation() {
        return relation;
    }

    public void setContratDav(String contratDav) {
        this.contratDav = contratDav;
    }

    public String getContratDav() {
        return contratDav;
    }

    public void setContratPlacemant(String contratPlacemant) {
        this.contratPlacemant = contratPlacemant;
    }

    public String getContratPlacemant() {
        return contratPlacemant;
    }

    public void setMontantPlacement(String montantPlacement) {
        this.montantPlacement = montantPlacement;
    }

    public String getMontantPlacement() {
        return montantPlacement;
    }

    public void setEcheance(String echeance) {
        this.echeance = echeance;
    }

    public String getEcheance() {
        return echeance;
    }

    public void setInterertServi(String interertServi) {
        this.interertServi = interertServi;
    }

    public String getInterertServi() {
        return interertServi;
    }

    public void setIrc(String irc) {
        this.irc = irc;
    }

    public String getIrc() {
        return irc;
    }
}
