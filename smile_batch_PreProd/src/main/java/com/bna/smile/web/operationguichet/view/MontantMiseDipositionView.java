package com.bna.smile.web.operationguichet.view;

import java.util.Date;

public class MontantMiseDipositionView extends com.oxia.fwk.core.ValueObject implements java.io.Serializable {

    private String numMmadMmad;
    private String montMontMmad;
    private long codTpemMmad;
    private String numNpemMmad;
    private Date datMmadMmad;
    private String nomEmetMmad;
    private String nomPremMmad;
    private long codStrcEmet;
    private String libStrcStrc;
    private String retour; // valeur de retour 
    
    public MontantMiseDipositionView() {
    }


    public void setCodTpemMmad(long codTpemMmad) {
        this.codTpemMmad = codTpemMmad;
    }

    public long getCodTpemMmad() {
        return codTpemMmad;
    }

    public void setNumNpemMmad(String numNpemMmad) {
        this.numNpemMmad = numNpemMmad;
    }

    public String getNumNpemMmad() {
        return numNpemMmad;
    }

    public void setDatMmadMmad(Date datMmadMmad) {
        this.datMmadMmad = datMmadMmad;
    }

    public Date getDatMmadMmad() {
        return datMmadMmad;
    }

    public void setNomEmetMmad(String nomEmetMmad) {
        this.nomEmetMmad = nomEmetMmad;
    }

    public String getNomEmetMmad() {
        return nomEmetMmad;
    }

    public void setNomPremMmad(String nomPremMmad) {
        this.nomPremMmad = nomPremMmad;
    }

    public String getNomPremMmad() {
        return nomPremMmad;
    }

    public void setCodStrcEmet(long codStrcEmet) {
        this.codStrcEmet = codStrcEmet;
    }

    public long getCodStrcEmet() {
        return codStrcEmet;
    }

    public void setNumMmadMmad(String numMmadMmad) {
        this.numMmadMmad = numMmadMmad;
    }

    public String getNumMmadMmad() {
        return numMmadMmad;
    }

    public void setLibStrcStrc(String libStrcStrc) {
        this.libStrcStrc = libStrcStrc;
    }

    public String getLibStrcStrc() {
        return libStrcStrc;
    }

    public void setMontMontMmad(String montMontMmad) {
        this.montMontMmad = montMontMmad;
    }

    public String getMontMontMmad() {
        return montMontMmad;
    }

    public void setRetour(String retour) {
        this.retour = retour;
    }

    public String getRetour() {
        return retour;
    }
}
