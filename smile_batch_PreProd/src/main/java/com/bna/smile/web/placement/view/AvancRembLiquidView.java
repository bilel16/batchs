package com.bna.smile.web.placement.view;

import com.bna.commun.model.ContratPlacement;
import com.bna.commun.model.DetailsOperationPlacement;

import com.bna.commun.model.Personne;
import com.bna.commun.model.Produit;
import com.bna.commun.model.TypePiece;
//import com.bna.commun.model.TypeSouscripteur;
import com.bna.commun.util.DateHandler;import com.bna.commun.util.StrHandler;
import com.bna.smile.model.constant.Constants;



public class AvancRembLiquidView extends com.oxia.fwk.core.ValueObject implements java.io.Serializable {

    private String numSeqArl;
    private String datArlArl;
    private String montArlArl;
    private String montInetArl;
    private String numTauiArl;
    private String codToprArl;
    private String datReelArl;
    private String datPrevArl;
    private String duree;
    private String dateValeur;
    private String montNetRecu;
    private String montIntRecu;
    private String montNetRembour;
    private String montCapCpla;    
    private String montActuCpla;
    private String montIntAPercevoir;
    private String libelleRembours;
    private String libRelation;
    private String typeLiquidation;
    private String montBrutLiq;
    private String montantReelIRC;
    private String montantDebourserBrut;
    private String boolRembAvance;
    private String typeInteretAv;
    private DetailsOperationPlacement detailsOperationPlacement = new DetailsOperationPlacement();
    private ContratPlacement contratPlacement = new ContratPlacement(); 
    
    public AvancRembLiquidView() {

            numSeqArl="";
            datArlArl="";
            montArlArl="";
            montInetArl="";
            numTauiArl="";
            codToprArl="";
            datReelArl="";
            dateValeur="";
            duree="";       
            montNetRecu="";
            montIntRecu="";
            montNetRembour="";
            montCapCpla="";  
            montActuCpla="";
            montIntAPercevoir="";
            libelleRembours="";
            libRelation="";
            typeLiquidation="";
            montBrutLiq="";
            montantReelIRC="";
            montantDebourserBrut="";
        detailsOperationPlacement = new DetailsOperationPlacement();
        contratPlacement = new ContratPlacement();
    }


    public void setNumSeqArl(String numSeqArl) {
        this.numSeqArl = numSeqArl;
    }

    public String getNumSeqArl() {
        return numSeqArl;
    }

    public void setDatArlArl(String datArlArl) {
        this.datArlArl = datArlArl;
    }

    public String getDatArlArl() {
        return datArlArl;
    }

    public void setMontArlArl(String montArlArl) {
        this.montArlArl = montArlArl;
    }

    public String getMontArlArl() {
        return montArlArl;
    }

    public void setMontInetArl(String montInetArl) {
        this.montInetArl = montInetArl;
    }

    public String getMontInetArl() {
        return montInetArl;
    }

    public void setNumTauiArl(String numTauiArl) {
        this.numTauiArl = numTauiArl;
    }

    public String getNumTauiArl() {
        return numTauiArl;
    }

    public void setCodToprArl(String codToprArl) {
        this.codToprArl = codToprArl;
    }

    public String getCodToprArl() {
        return codToprArl;
    }

    public void setDatReelArl(String datReelArl) {
        this.datReelArl = datReelArl;
    }

    public String getDatReelArl() {
        return datReelArl;
    }

    public void setDetailsOperationPlacement(DetailsOperationPlacement detailsOperationPlacement) {
        this.detailsOperationPlacement = detailsOperationPlacement;
    }

    public DetailsOperationPlacement getDetailsOperationPlacement() {
        return detailsOperationPlacement;
    }

    public void setContratPlacement(ContratPlacement contratPlacement) {
        this.contratPlacement = contratPlacement;
    }

    public ContratPlacement getContratPlacement() {
        return contratPlacement;
    }

    public void setDateValeur(String dateValeur) {
        this.dateValeur = dateValeur;
    }

    public String getDateValeur() {
        return dateValeur;
    }

    public void setDuree(String duree) {
        this.duree = duree;
    }

    public String getDuree() {
        return duree;
    }

    public void setMontNetRecu(String montNetRecu) {
        this.montNetRecu = montNetRecu;
    }

    public String getMontNetRecu() {
        return montNetRecu;
    }

    public void setMontIntRecu(String montIntRecu) {
        this.montIntRecu = montIntRecu;
    }

    public String getMontIntRecu() {
        return montIntRecu;
    }

    public void setMontNetRembour(String montNetRembour) {
        this.montNetRembour = montNetRembour;
    }

    public String getMontNetRembour() {
        return montNetRembour;
    }

    public void setDatPrevArl(String datPrevArl) {
        this.datPrevArl = datPrevArl;
    }

    public String getDatPrevArl() {
        return datPrevArl;
    }

    public void setMontActuCpla(String montActuCpla) {
        this.montActuCpla = montActuCpla;
    }

    public String getMontActuCpla() {
        return montActuCpla;
    }

    public void setMontCapCpla(String montCapCpla) {
        this.montCapCpla = montCapCpla;
    }

    public String getMontCapCpla() {
        return montCapCpla;
    }

    public void setMontIntAPercevoir(String montIntAPercevoir) {
        this.montIntAPercevoir = montIntAPercevoir;
    }

    public String getMontIntAPercevoir() {
        return montIntAPercevoir;
    }

    public void setLibelleRembours(String libelleRembours) {
        this.libelleRembours = libelleRembours;
    }

    public String getLibelleRembours() {
        return libelleRembours;
    }

    public void setLibRelation(String libRelation) {
        this.libRelation = libRelation;
    }

    public String getLibRelation() {
        return libRelation;
    }

    public void setTypeLiquidation(String typeLiquidation) {
        this.typeLiquidation = typeLiquidation;
    }

    public String getTypeLiquidation() {
        return typeLiquidation;
    }

    public void setMontBrutLiq(String montBrutLiq) {
        this.montBrutLiq = montBrutLiq;
    }

    public String getMontBrutLiq() {
        return montBrutLiq;
    }

    public void setMontantReelIRC(String montantReelIRC) {
        this.montantReelIRC = montantReelIRC;
    }

    public String getMontantReelIRC() {
        return montantReelIRC;
    }

    public void setMontantDebourserBrut(String montantDebourserBrut) {
        this.montantDebourserBrut = montantDebourserBrut;
    }

    public String getMontantDebourserBrut() {
        return montantDebourserBrut;
    }

    public void setBoolRembAvance(String boolRembAvance) {
        this.boolRembAvance = boolRembAvance;
    }

    public String getBoolRembAvance() {
        return boolRembAvance;
    }

    public void setTypeInteretAv(String typeInteretAv) {
        this.typeInteretAv = typeInteretAv;
    }

    public String getTypeInteretAv() {
        return typeInteretAv;
    }
}
