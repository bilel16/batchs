package com.bna.smile.web.ouverturejournee.forms;

import com.bna.commun.model.Personne;

import com.bna.smile.web.commun.view.InitialisationView;

import java.util.ArrayList;
import java.util.List;

import org.apache.struts.action.ActionForm;

public class OuvertureJourneeForm extends ActionForm {

   private InitialisationView initialisationView = new InitialisationView();
    
   private String  dateJournee;
   
   //------------------------------------//
   //---------- Caisse centrale  --------//
   
    private String  codStrcStrc;
    private String  libStrcStrc;
    private String  codStatCca;
    private String  libStatCca; // lieblle du status de la caisse
    private String  datInitCca;
    private String  datCloCca;
   
   //------------------------------------//
   //---------- Caisse centrale dinars --//
    
    private String  montInitCdc;
    private String  montFinCdc;
    
    //------------------------------------//
    //---------- Caisse centrale devise --//
   
    private String  montInitCdvc;
    private String  montFinCdvc;

    //-------------------------------------------//
    //---------- detail caisse centrale devise --//
    
    private List listDetailCaisseDeviseCentral = new ArrayList(0);


    public void clearForm() {
       dateJournee = "";
       codStrcStrc = "";
       libStrcStrc = "";
       codStatCca  = "";
       datInitCca  = "";
       datCloCca   = "";
       montInitCdc = "";
       montFinCdc  = "";
       montInitCdvc = "";
       montFinCdvc  = ""; 
       listDetailCaisseDeviseCentral = new ArrayList(0);
    }
    



    public void setCodStrcStrc(String codStrcStrc) {
        this.codStrcStrc = codStrcStrc;
    }

    public String getCodStrcStrc() {
        return codStrcStrc;
    }

    public void setLibStrcStrc(String libStrcStrc) {
        this.libStrcStrc = libStrcStrc;
    }

    public String getLibStrcStrc() {
        return libStrcStrc;
    }

    public void setCodStatCca(String codStatCca) {
        this.codStatCca = codStatCca;
    }

    public String getCodStatCca() {
        return codStatCca;
    }



    public void setDatCloCca(String datCloCca) {
        this.datCloCca = datCloCca;
    }

    public String getDatCloCca() {
        return datCloCca;
    }

    public void setMontInitCdc(String montInitCdc) {
        this.montInitCdc = montInitCdc;
    }

    public String getMontInitCdc() {
        return montInitCdc;
    }

    public void setMontFinCdc(String montFinCdc) {
        this.montFinCdc = montFinCdc;
    }

    public String getMontFinCdc() {
        return montFinCdc;
    }

    public void setMontInitCdvc(String montInitCdvc) {
        this.montInitCdvc = montInitCdvc;
    }

    public String getMontInitCdvc() {
        return montInitCdvc;
    }

    public void setMontFinCdvc(String montFinCdvc) {
        this.montFinCdvc = montFinCdvc;
    }

    public String getMontFinCdvc() {
        return montFinCdvc;
    }

    public void setListDetailCaisseDeviseCentral(List listDetailCaisseDeviseCentral) {
        this.listDetailCaisseDeviseCentral = listDetailCaisseDeviseCentral;
    }

    public List getListDetailCaisseDeviseCentral() {
        return listDetailCaisseDeviseCentral;
    }

    public void setDateJournee(String dateJournee) {
        this.dateJournee = dateJournee;
    }

    public String getDateJournee() {
        return dateJournee;
    }

    public void setInitialisationView(InitialisationView initialisationView) {
        this.initialisationView = initialisationView;
    }

    public InitialisationView getInitialisationView() {
        return initialisationView;
    }

    public void setLibStatCca(String libStatCca) {
        this.libStatCca = libStatCca;
    }

    public String getLibStatCca() {
        return libStatCca;
    }

    public void setDatInitCca(String datInitCca) {
        this.datInitCca = datInitCca;
    }

    public String getDatInitCca() {
        return datInitCca;
    }
}
