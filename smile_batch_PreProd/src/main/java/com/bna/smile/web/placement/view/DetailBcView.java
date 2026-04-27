package com.bna.smile.web.placement.view;

import com.bna.commun.model.ContratPlacement;
import com.bna.commun.model.DetailsOperationPlacement;

import com.bna.commun.model.Personne;
import com.bna.commun.model.Produit;
import com.bna.commun.model.TypePiece;
//import com.bna.commun.model.TypeSouscripteur;
import com.bna.commun.util.DateHandler;import com.bna.commun.util.StrHandler;
import com.bna.smile.model.constant.Constants;



public class DetailBcView extends com.oxia.fwk.core.ValueObject implements java.io.Serializable {

    private String intituleCpt;
    private String numSeqCpla;
    private String datCreCpla;
    private String libPrdPrd;
    private String numBcCpla;
    private String dateRecupBc;
    private ContratPlacement contratPlacement = new ContratPlacement(); 
    private String numCcptCpla;
   
   
   
    public DetailBcView() {

       intituleCpt="";
       numSeqCpla="";
       datCreCpla="";
       libPrdPrd="";
       numBcCpla="";
        dateRecupBc="";
       contratPlacement = new ContratPlacement();
    }


    public void setIntituleCpt(String intituleCpt) {
        this.intituleCpt = intituleCpt;
    }

    public String getIntituleCpt() {
        return intituleCpt;
    }

    public void setNumSeqCpla(String numSeqCpla) {
        this.numSeqCpla = numSeqCpla;
    }

    public String getNumSeqCpla() {
        return numSeqCpla;
    }

    public void setDatCreCpla(String datCreCpla) {
        this.datCreCpla = datCreCpla;
    }

    public String getDatCreCpla() {
        return datCreCpla;
    }

    public void setLibPrdPrd(String libPrdPrd) {
        this.libPrdPrd = libPrdPrd;
    }

    public String getLibPrdPrd() {
        return libPrdPrd;
    }

    public void setNumBcCpla(String numBcCpla) {
        this.numBcCpla = numBcCpla;
    }

    public String getNumBcCpla() {
        return numBcCpla;
    }

    public void setDateRecupBc(String dateRecupBc) {
        this.dateRecupBc = dateRecupBc;
    }

    public String getDateRecupBc() {
        return dateRecupBc;
    }

    public void setContratPlacement(ContratPlacement contratPlacement) {
        this.contratPlacement = contratPlacement;
    }

    public ContratPlacement getContratPlacement() {
        return contratPlacement;
    }

    public void setNumCcptCpla(String numCcptCpla) {
        this.numCcptCpla = numCcptCpla;
    }

    public String getNumCcptCpla() {
        return numCcptCpla;
    }
}
