package com.bna.smile.web.procuration.model;


import java.util.Date;

public class MandatOperationGrid extends com.oxia.fwk.core.ValueObject implements java.io.Serializable {

    private Long numMaopMaop;
    private String codSignMaop;
    //    private Date    datDebMaop;
    private String montMlimMaop;
    private String codPerMaop;
    private String datDperMaop;
    private String montElimMaop;
    private String montUtilMaop;
    private Long nbrMinMaop;
    private String datFinMaop;
    private Long codOperOper;
    private String libOperOper;
    private Long numPereMaop;
    

    public MandatOperationGrid() {
    }

    public void setCodSignMaop(String codSignMaop) {
        this.codSignMaop = codSignMaop;
    }

    public String getCodSignMaop() {
        return codSignMaop;
    }

    public void setCodPerMaop(String codPerMaop) {
        this.codPerMaop = codPerMaop;
    }

    public String getCodPerMaop() {
        return codPerMaop;
    }

    public void setNbrMinMaop(Long nbrMinMaop) {
        this.nbrMinMaop = nbrMinMaop;
    }

    public Long getNbrMinMaop() {
        return nbrMinMaop;
    }

    public void setCodOperOper(Long codOperOper) {
        this.codOperOper = codOperOper;
    }

    public Long getCodOperOper() {
        return codOperOper;
    }

    public void setLibOperOper(String libOperOper) {
        this.libOperOper = libOperOper;
    }

    public String getLibOperOper() {
        return libOperOper;
    }

    public void setDatDperMaop(String datDperMaop) {
        this.datDperMaop = datDperMaop;
    }

    public String getDatDperMaop() {
        return datDperMaop;
    }

    public void setDatFinMaop(String datFinMaop) {
        this.datFinMaop = datFinMaop;
    }

    public String getDatFinMaop() {
        return datFinMaop;
    }

    public void setMontMlimMaop(String montMlimMaop) {
        this.montMlimMaop = montMlimMaop;
    }

    public String getMontMlimMaop() {
        return montMlimMaop;
    }

    public void setMontElimMaop(String montElimMaop) {
        this.montElimMaop = montElimMaop;
    }

    public String getMontElimMaop() {
        return montElimMaop;
    }

    public void setMontUtilMaop(String montUtilMaop) {
        this.montUtilMaop = montUtilMaop;
    }

    public String getMontUtilMaop() {
        return montUtilMaop;
    }

    public void setNumMaopMaop(Long numMaopMaop) {
        this.numMaopMaop = numMaopMaop;
    }

    public Long getNumMaopMaop() {
        return numMaopMaop;
    }

    public void setNumPereMaop(Long numPereMaop) {
        this.numPereMaop = numPereMaop;
    }

    public Long getNumPereMaop() {
        return numPereMaop;
    }
}
