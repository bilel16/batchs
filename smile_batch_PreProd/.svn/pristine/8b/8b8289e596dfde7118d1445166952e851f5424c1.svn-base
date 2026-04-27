package com.bna.smile.web.commun.view;


import com.bna.smile.model.constant.Constants;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import java.util.Date;

public class InitialisationView extends com.oxia.fwk.core.ValueObject implements java.io.Serializable {

    private String reqCode="";
    private String codeOperation="";
    private String libelleOperation="";
    private String numMatrUser="";
    private String codeAgence="";
    private String dateActuelle="";
    private String dateComptable="";
    private String alert="";
    private Date dateOp ;
    private Date dateValCr;
    private Date dateValDb;
    private Date dateValEpCr;
    private Date dateValEpDb;

    
    public InitialisationView() {
      
    }

    public void setReqCode(String reqCode) {
        this.reqCode = reqCode;
    }

    public String getReqCode() {
        return reqCode;
    }

    public void setCodeOperation(String codeOperation) {
        this.codeOperation = codeOperation;
    }

    public String getCodeOperation() {
        return codeOperation;
    }

    public void setLibelleOperation(String libelleOperation) {
        this.libelleOperation = libelleOperation;
    }

    public String getLibelleOperation() {
                                           
    
        if(codeOperation != null && !codeOperation.equals("")){
            if(codeOperation.equals(Constants.COD_OPER_OPER_OPPOSITION_CHQ_CLIENT.toString())){
                libelleOperation = "Mise en opposition sur Chèque par client";
            }else if(codeOperation.equals(Constants.COD_OPER_OPER_OPPOSITION_CHQ_BANQUE.toString())){
                libelleOperation = "Mise en opposition sur Chèque par banque";
            }else if(codeOperation.equals(Constants.COD_OPER_OPER_LEVEE_CHQ.toString())){
                libelleOperation = "Levée d'opposition sur Chèque";
            }else if(codeOperation.equals(Constants.COD_OPER_OPER_OPPOSITION_LIVRET_CLIENT.toString())){
                libelleOperation = "Mise en opposition sur Livret";
            }else if(codeOperation.equals(Constants.COD_OPER_OPER_LEVEE_LIVRET.toString())){
                libelleOperation = "Levée d'opposition sur Livret";
            }else if(codeOperation.equals(Constants.COD_OPER_OPER_OPPOSITION_CIB_CLIENT.toString())){
                libelleOperation = "Mise en opposition sur Carte d'Ident Bancaire";
            }else if(codeOperation.equals(Constants.COD_OPER_OPER_LEVEE_CIB.toString())){
                libelleOperation = "Levée opposition sur Carte d'Ident Bancaire";
            }else if(codeOperation.equals(Constants.COD_OPER_OPER_OPPOSITION_CARTE_CLIENT.toString())){
                libelleOperation = "Mise en opposition sur Carte Bancaire par client";
            }else if(codeOperation.equals(Constants.COD_OPER_OPER_OPPOSITION_CARTE_BANQUE.toString())){
                libelleOperation = "Mise en opposition sur Carte Bancaire par banque";
            }else if(codeOperation.equals(Constants.COD_OPER_OPER_OPPOSITION_BC_PLAC.toString())){
                libelleOperation = "Mise en opposition sur BC placement";
            }else if(codeOperation.equals(Constants.COD_OPER_OPER_LEV_OPP_BC_PLAC.toString())){
                libelleOperation = "Levée opposition sur BC placement";
            }             
        } 
        return libelleOperation;
    }


    public void setNumMatrUser(String numMatrUser) {
        this.numMatrUser = numMatrUser;
    }

    public String getNumMatrUser() {
        return numMatrUser;
    }

  

    public void setDateActuelle(String dateActuelle) {
        this.dateActuelle = dateActuelle;
    }

    public String getDateActuelle() {
        DateFormat myformat = new SimpleDateFormat("dd/MM/yyyy");
        String d = myformat.format(new Date());
        dateActuelle =d;
        return dateActuelle;
    }

    public void setAlert(String alert) {
        this.alert = alert;
    }

    public String getAlert() {
        return alert;
    }

    public void setCodeAgence(String codeAgence) {
        this.codeAgence = codeAgence;
    }

    public String getCodeAgence() {
        return codeAgence;
    }

    public void setDateValCr(Date dateValCr) {
        this.dateValCr = dateValCr;
    }

    public Date getDateValCr() {
        return dateValCr;
    }

    public void setDateValDb(Date dateValDb) {
        this.dateValDb = dateValDb;
    }

    public Date getDateValDb() {
        return dateValDb;
    }

    public void setDateValEpCr(Date dateValEpCr) {
        this.dateValEpCr = dateValEpCr;
    }

    public Date getDateValEpCr() {
        return dateValEpCr;
    }

    public void setDateValEpDb(Date dateValEpDb) {
        this.dateValEpDb = dateValEpDb;
    }

    public Date getDateValEpDb() {
        return dateValEpDb;
    }


  

    public void setDateComptable(String dateComptable) {
        this.dateComptable = dateComptable;
    }

    public String getDateComptable() {
        return dateComptable;
    }

    public void setDateOp(Date dateOp) {
        this.dateOp = dateOp;
    }

    public Date getDateOp() {
        return dateOp;
    }
}
