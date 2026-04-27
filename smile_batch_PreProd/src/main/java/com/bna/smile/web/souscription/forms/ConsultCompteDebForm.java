package com.bna.smile.web.souscription.forms;

import java.util.Collection;

import org.apache.struts.action.ActionForm;

public class ConsultCompteDebForm extends ActionForm{
    public ConsultCompteDebForm() {
    }
    private String typePieceId;
    private String codTypePieceId;
    private String numPieceId;
    private String codStrcRech;
    private String nomId;
    private String prenomId;
    private String dateDebut;
    private String dateFin;
    private Collection listeContrats;
    private String alert;

    public void setTypePieceId(String typePieceId) {
        this.typePieceId = typePieceId;
    }

    public String getTypePieceId() {
        return typePieceId;
    }

    public void setCodTypePieceId(String codTypePieceId) {
        this.codTypePieceId = codTypePieceId;
    }

    public String getCodTypePieceId() {
        return codTypePieceId;
    }

    public void setNumPieceId(String numPieceId) {
        this.numPieceId = numPieceId;
    }

    public String getNumPieceId() {
        return numPieceId;
    }

    public void setNomId(String nomId) {
        this.nomId = nomId;
    }

    public String getNomId() {
        return nomId;
    }

    public void setPrenomId(String prenomId) {
        this.prenomId = prenomId;
    }

    public String getPrenomId() {
        return prenomId;
    }

    public void setDateDebut(String dateDebut) {
        this.dateDebut = dateDebut;
    }

    public String getDateDebut() {
        return dateDebut;
    }

    public void setDateFin(String dateFin) {
        this.dateFin = dateFin;
    }

    public String getDateFin() {
        return dateFin;
    }

    public void setListeContrats(Collection listeContrats) {
        this.listeContrats = listeContrats;
    }

    public Collection getListeContrats() {
        return listeContrats;
    }

    public void setAlert(String alert) {
        this.alert = alert;
    }

    public String getAlert() {
        return alert;
    }

    public void setCodStrcRech(String codStrcRech) {
        this.codStrcRech = codStrcRech;
    }

    public String getCodStrcRech() {
        return codStrcRech;
    }
}
