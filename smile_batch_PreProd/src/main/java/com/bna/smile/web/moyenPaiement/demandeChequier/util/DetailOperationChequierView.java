package com.bna.smile.web.moyenPaiement.demandeChequier.util;

import com.bna.commun.model.DemandeCheque;
import com.bna.commun.model.DetailOperationChequier;
import com.bna.commun.model.MandatOperation;

/**
 * Classe qui represente l'objet DetailOperationChequier, elle est utilisée 
 * pour l'affichage dans les pages JSP
 * @author EL ARBI HASSINE
 */
public class DetailOperationChequierView extends com.oxia.fwk.core.ValueObject implements java.io.Serializable {
    private DetailOperationChequier detailOperationChequier;
    private String dateDetail;
    private String etatDetail;

    public DetailOperationChequierView() {
    }

    public void setDetailOperationChequier(DetailOperationChequier detailOperationChequier) {
        this.detailOperationChequier = detailOperationChequier;
    }

    public DetailOperationChequier getDetailOperationChequier() {
        return detailOperationChequier;
    }

    public void setDateDetail(String dateDetail) {
        this.dateDetail = dateDetail;
    }

    public String getDateDetail() {
        return dateDetail;
    }

    public void setEtatDetail(String etatDetail) {
        this.etatDetail = etatDetail;
    }

    public String getEtatDetail() {
        return etatDetail;
    }
}
