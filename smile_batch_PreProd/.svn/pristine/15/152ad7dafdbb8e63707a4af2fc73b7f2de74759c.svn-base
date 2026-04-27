package com.bna.smile.web.gestioncaisse.forms;

import com.bna.commun.model.CaisseDevises;
import com.bna.commun.model.CaisseDinars;
import com.bna.smile.web.commun.view.InitialisationView;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.struts.action.ActionForm;

public class ConsultationCaisseAgenceForm extends ActionForm{
 
    private  InitialisationView initialisationView = new InitialisationView();
    
    private  List       listeCaisseStructure = new ArrayList();
    private  String     numeroCaisseRech;
    private  Date       dateCaisseRech;
    private  String     libStatCais;
    private  String     typeCaisse;
    
    //-----------------------------------------//
    //---- Caisse dinars
    private String  montInitCaisDinars;
    private String  montActuCaisDinars;
    private String  montFinCaisDinars;
    private String  montDifferenceDinars;
    
    //------------------------------------------//
    //--- Caisse devise 
    
    private String montInitCdev;
    private String montActuCdev;
    private String montFinCdev;
    private String montDifferenceDevise;
    
    private  CaisseDevises caisseDevises;
    private  CaisseDinars  caisseDinars;
    
    private List listDetailDevise = new ArrayList();
    
    public ConsultationCaisseAgenceForm() {
    }


    public void clearForm(){
        listeCaisseStructure = new ArrayList();
        numeroCaisseRech ="";
        dateCaisseRech =null;
        libStatCais ="";
        typeCaisse  ="";
      
        //-----------------------------------------//
        //---- Caisse dinars
        montInitCaisDinars="";
        montActuCaisDinars="";
        montFinCaisDinars="";
        
        //------------------------------------------//
        //--- Caisse devise 
        
        montInitCdev="";
        montActuCdev="";
        montFinCdev="";        
        caisseDevises = null;
        caisseDinars  = null;
        listDetailDevise = new ArrayList();
    }
    
    
    public void setInitialisationView(InitialisationView initialisationView) {
        this.initialisationView = initialisationView;
    }

    public InitialisationView getInitialisationView() {
        return initialisationView;
    }


    public void setNumeroCaisseRech(String numeroCaisseRech) {
        this.numeroCaisseRech = numeroCaisseRech;
    }

    public String getNumeroCaisseRech() {
        return numeroCaisseRech;
    }

    public void setDateCaisseRech(Date dateCaisseRech) {
        this.dateCaisseRech = dateCaisseRech;
    }

    public Date getDateCaisseRech() {
        return dateCaisseRech;
    }

    public void setListeCaisseStructure(List listeCaisseStructure) {
        this.listeCaisseStructure = listeCaisseStructure;
    }

    public List getListeCaisseStructure() {
        return listeCaisseStructure;
    }

    public void setCaisseDevises(CaisseDevises caisseDevises) {
        this.caisseDevises = caisseDevises;
    }

    public CaisseDevises getCaisseDevises() {
        return caisseDevises;
    }

    public void setCaisseDinars(CaisseDinars caisseDinars) {
        this.caisseDinars = caisseDinars;
    }

    public CaisseDinars getCaisseDinars() {
        return caisseDinars;
    }

    public void setLibStatCais(String libStatCais) {
        this.libStatCais = libStatCais;
    }

    public String getLibStatCais() {
        return libStatCais;
    }

    public void setTypeCaisse(String typeCaisse) {
        this.typeCaisse = typeCaisse;
    }

    public String getTypeCaisse() {
        return typeCaisse;
    }

    public void setMontInitCaisDinars(String montInitCaisDinars) {
        this.montInitCaisDinars = montInitCaisDinars;
    }

    public String getMontInitCaisDinars() {
        return montInitCaisDinars;
    }

    public void setMontActuCaisDinars(String montActuCaisDinars) {
        this.montActuCaisDinars = montActuCaisDinars;
    }

    public String getMontActuCaisDinars() {
        return montActuCaisDinars;
    }

    public void setMontFinCaisDinars(String montFinCaisDinars) {
        this.montFinCaisDinars = montFinCaisDinars;
    }

    public String getMontFinCaisDinars() {
        return montFinCaisDinars;
    }

    public void setMontInitCdev(String montInitCdev) {
        this.montInitCdev = montInitCdev;
    }

    public String getMontInitCdev() {
        return montInitCdev;
    }

    public void setMontActuCdev(String montActuCdev) {
        this.montActuCdev = montActuCdev;
    }

    public String getMontActuCdev() {
        return montActuCdev;
    }

    public void setMontFinCdev(String montFinCdev) {
        this.montFinCdev = montFinCdev;
    }

    public String getMontFinCdev() {
        return montFinCdev;
    }

    public void setListDetailDevise(List listDetailDevise) {
        this.listDetailDevise = listDetailDevise;
    }

    public List getListDetailDevise() {
        return listDetailDevise;
    }

    public void setMontDifferenceDinars(String montDifferenceDinars) {
        this.montDifferenceDinars = montDifferenceDinars;
    }

    public String getMontDifferenceDinars() {
        return montDifferenceDinars;
    }

    public void setMontDifferenceDevise(String montDifferenceDevise) {
        this.montDifferenceDevise = montDifferenceDevise;
    }

    public String getMontDifferenceDevise() {
        return montDifferenceDevise;
    }
}
