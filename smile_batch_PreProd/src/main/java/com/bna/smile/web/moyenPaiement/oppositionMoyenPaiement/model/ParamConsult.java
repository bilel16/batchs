package com.bna.smile.web.moyenPaiement.oppositionMoyenPaiement.model;
import java.sql.*;
import oracle.jdbc.OracleDriver;

public class ParamConsult extends com.oxia.fwk.core.ValueObject implements java.io.Serializable {

    private String choix="";
    private String typeOperation="";
    private String typeMoyPaie="";
    private String natureCheque="";
    private String natureCarte="";
    
    private String typPcePers="";
    private String numPcePers="";
    
    private String numMoyp="";
    
    private String codStrcStrc="";
    private String codPrdPrd="";
    private String numCcptCcpt="";
    
    private String codStrcStrc2="";
    private String codPrdPrd2="";
    private String numCcptCcpt2="";
    
    private String dateDebutconsult;
    private String dateFinconsult;
    
    private String etat;
    
    public ParamConsult() {
    }
   
    public void clear(){
       choix="";
       typeOperation="";
       typeMoyPaie="";
       natureCheque="";
       natureCarte="";
         
       typPcePers="";
       numPcePers="";
        
       codStrcStrc="";
       codPrdPrd="";
       numCcptCcpt="";
        
       dateDebutconsult="";
       dateFinconsult="";  
       
        etat="";
    }
    public void setChoix(String choix) {
        this.choix = choix;
    }

    public String getChoix() {
        return choix;
    }

    public void setTypeMoyPaie(String typeMoyPaie) {
        this.typeMoyPaie = typeMoyPaie;
    }

    public String getTypeMoyPaie() {
        return typeMoyPaie;
    }

       public void setTypPcePers(String typPcePers) {
        this.typPcePers = typPcePers;
    }

    public String getTypPcePers() {
        return typPcePers;
    }

    public void setNumPcePers(String numPcePers) {
        this.numPcePers = numPcePers;
    }

    public String getNumPcePers() {
        return numPcePers;
    }

    public void setCodStrcStrc(String codStrcStrc) {
        this.codStrcStrc = codStrcStrc;
    }

    public String getCodStrcStrc() {
        return codStrcStrc;
    }

    public void setCodPrdPrd(String codPrdPrd) {
        this.codPrdPrd = codPrdPrd;
    }

    public String getCodPrdPrd() {
        return codPrdPrd;
    }

    public void setNumCcptCcpt(String numCcptCcpt) {
        this.numCcptCcpt = numCcptCcpt;
    }

    public String getNumCcptCcpt() {
        return numCcptCcpt;
    }

    public void setDateDebutconsult(String dateDebutconsult) {
        this.dateDebutconsult = dateDebutconsult;
    }

    public String getDateDebutconsult() {
        return dateDebutconsult;
    }

    public void setDateFinconsult(String dateFinconsult) {
        this.dateFinconsult = dateFinconsult;
    }

    public String getDateFinconsult() {
        return dateFinconsult;
    }

    public void setTypeOperation(String typeOperation) {
        this.typeOperation = typeOperation;
    }

    public String getTypeOperation() {
        return typeOperation;
    }

    public void setNatureCheque(String natureCheque) {
        this.natureCheque = natureCheque;
    }

    public String getNatureCheque() {
        return natureCheque;
    }

    public void setNatureCarte(String natureCarte) {
        this.natureCarte = natureCarte;
    }

    public String getNatureCarte() {
        return natureCarte;
    }

    public void setNumMoyp(String numMoyp) {
        this.numMoyp = numMoyp;
    }

    public String getNumMoyp() {
        return numMoyp;
    }

    public void setCodStrcStrc2(String codStrcStrc2) {
        this.codStrcStrc2 = codStrcStrc2;
    }

    public String getCodStrcStrc2() {
        return codStrcStrc2;
    }

    public void setCodPrdPrd2(String codPrdPrd2) {
        this.codPrdPrd2 = codPrdPrd2;
    }

    public String getCodPrdPrd2() {
        return codPrdPrd2;
    }

    public void setNumCcptCcpt2(String numCcptCcpt2) {
        this.numCcptCcpt2 = numCcptCcpt2;
    }

    public String getNumCcptCcpt2() {
        return numCcptCcpt2;
    }

    public void setEtat(String etat) {
        this.etat = etat;
    }

    public String getEtat() {
        return etat;
    }
}
