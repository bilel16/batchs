package com.bna.smile.model.domaineplacement.model;

import java.util.Date;

import com.bna.commun.model.ContratPlacement;
import com.oxia.fwk.core.ValueObject;

public class ParamBonCaisse extends ValueObject {
    
    private Long numBonCaisse;
    private Long numSeqBc; // la ligne des carnets qui contiennent le numéro BC (table BonDeCaisse)
    private Long codeStructure;
    private boolean existBonCaisse = true;
    private boolean existDetailsBC = true;
    private ContratPlacement contratPlacement = new ContratPlacement();
    private Date    dateDebut;
    private Date    dateFin;
    private Long numSeqDebBC;
    private Long numSeqFinBC;
    public void initi(){
          numBonCaisse=null;
          numSeqBc=null;
          codeStructure=null;
          existBonCaisse =false;
          existDetailsBC =false;
          //contratPlacement = null;
         //dateDebut;
          //   dateFin;
          numSeqDebBC=null;
          numSeqFinBC=null;
    }
    
    
    
    public ParamBonCaisse() {
    }

    public void setExistBonCaisse(boolean existBonCaisse) {
        this.existBonCaisse = existBonCaisse;
    }

    public boolean isExistBonCaisse() {
        return existBonCaisse;
    }

    public void setExistDetailsBC(boolean existDetailsBC) {
        this.existDetailsBC = existDetailsBC;
    }

    public boolean isExistDetailsBC() {
        return existDetailsBC;
    }

    public void setCodeStructure(Long codeStructure) {
        this.codeStructure = codeStructure;
    }

    public Long getCodeStructure() {
        return codeStructure;
    }

    public void setNumBonCaisse(Long numBonCaisse) {
        this.numBonCaisse = numBonCaisse;
    }

    public Long getNumBonCaisse() {
        return numBonCaisse;
    }

    public void setNumSeqBc(Long numSeqBc) {
        this.numSeqBc = numSeqBc;
    }

    public Long getNumSeqBc() {
        return numSeqBc;
    }

    public void setContratPlacement(ContratPlacement contratPlacement) {
        this.contratPlacement = contratPlacement;
    }

    public ContratPlacement getContratPlacement() {
        return contratPlacement;
    }

    public void setDateDebut(Date dateDebut) {
        this.dateDebut = dateDebut;
    }

    public Date getDateDebut() {
        return dateDebut;
    }

    public void setDateFin(Date dateFin) {
        this.dateFin = dateFin;
    }

    public Date getDateFin() {
        return dateFin;
    }

    public void setNumSeqDebBC(Long numSeqDebBC) {
        this.numSeqDebBC = numSeqDebBC;
    }

    public Long getNumSeqDebBC() {
        return numSeqDebBC;
    }

    public void setNumSeqFinBC(Long numSeqFinBC) {
        this.numSeqFinBC = numSeqFinBC;
    }

    public Long getNumSeqFinBC() {
        return numSeqFinBC;
    }
}
