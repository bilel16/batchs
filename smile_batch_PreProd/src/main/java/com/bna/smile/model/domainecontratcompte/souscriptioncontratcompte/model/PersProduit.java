package com.bna.smile.model.domainecontratcompte.souscriptioncontratcompte.model;

import com.oxia.fwk.core.ValueObject;
/** Fichier: PersProduit.java version 1.0.0 du 19/06/2007
 * Copyright(c) 2007 BNA (www.bna.com.tn)
 * Classe: PersProduit
 * package: com.bna.smile.model.domainecontratcompte.souscriptioncontratcompte.model
 * Auteur : Boussen Youssef & Kriaa Hatem
 */
public class PersProduit extends ValueObject {

    private Long    numSeqPers;
    private Long    codPrdPrd;
    private Long    codTpceTpce;
    private String  numPcePers;
    private Long    codStrcStrc;

    
    public PersProduit() {
    }

    public void setNumSeqPers(Long numSeqPers) {
        this.numSeqPers = numSeqPers;
    }

    public Long getNumSeqPers() {
        return numSeqPers;
    }

    public void setCodPrdPrd(Long codPrdPrd) {
        this.codPrdPrd = codPrdPrd;
    }

    public Long getCodPrdPrd() {
        return codPrdPrd;
    }

    public void setCodTpceTpce(Long codTpceTpce) {
        this.codTpceTpce = codTpceTpce;
    }

    public Long getCodTpceTpce() {
        return codTpceTpce;
    }

    public void setNumPcePers(String numPcePers) {
        this.numPcePers = numPcePers;
    }

    public String getNumPcePers() {
        return numPcePers;
    }

    public void setCodStrcStrc(Long codStrcStrc) {
        this.codStrcStrc = codStrcStrc;
    }

    public Long getCodStrcStrc() {
        return codStrcStrc;
    }
}

