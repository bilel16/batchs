package com.bna.smile.web.operationguichet.view;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import java.util.Date;

public class InitialisationView extends com.oxia.fwk.core.ValueObject implements java.io.Serializable {

    private String reqCode;
    private String codeOperation;
    private String libelleOperation;
    private String numMatrUser;
    private String codeAgence;
    private String dateActuelle;
    private String alert;

    public InitialisationView() {
        reqCode = "";
        codeOperation = "";
        libelleOperation = "";
        numMatrUser = "";
        codeAgence = "";
        dateActuelle = "";
        alert = "";
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
        if (codeOperation != null && !codeOperation.equals("")) {
            if (codeOperation.equals("1500")) {
                libelleOperation = "Mise en opposition sur Chèque";
            } else if (codeOperation.equals("1501")) {
                libelleOperation = "Mise en opposition sur Carte";
            } else if (codeOperation.equals("1502")) {
                libelleOperation = "Mise en opposition sur Livret";
            } else if (codeOperation.equals("1503")) {
                libelleOperation = 
                        "Mise en opposition sur Carte d'Ident. Bancaire";
            } else if (codeOperation.equals("1504")) {
                libelleOperation = "Levée d'opposition sur Chèque";
            } else if (codeOperation.equals("1505")) {
                libelleOperation = "Levée d'opposition sur Livret";
            } else if (codeOperation.equals("1506")) {
                libelleOperation = 
                        "Levée d'opposition sur Carte d'Ident. Bancaire";
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

    public void setCodeAgence(String codeAgence) {
        this.codeAgence = codeAgence;
    }

    public String getCodeAgence() {
        return codeAgence;
    }

    public void setDateActuelle(String dateActuelle) {
        this.dateActuelle = dateActuelle;
    }

    public String getDateActuelle() {
        DateFormat myformat = new SimpleDateFormat("dd/MM/yyyy");
        String d = myformat.format(new Date());
        dateActuelle = d;
        return dateActuelle;
    }

    public void setAlert(String alert) {
        this.alert = alert;
    }

    public String getAlert() {
        return alert;
    }
}
