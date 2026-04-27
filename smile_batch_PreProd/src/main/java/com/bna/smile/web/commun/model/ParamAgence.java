package com.bna.smile.web.commun.model;

import com.oxia.fwk.core.ValueObject;

import java.util.Date;

public class ParamAgence extends com.oxia.fwk.core.ValueObject implements java.io.Serializable {

private Long codStrcStrc=null;
private Long codStrmStrc;
private Long codTstrcTstrc;
private String numMatrUser;
private Long codBctStrc;

private String dateComptable = "";
private String dateJours         = "";

private Date dateOp;
private Date dateValCr   = new Date();
private Date dateValDb   = new Date();
private Date dateValEpCr = new Date();
private Date dateValEpDb = new Date();




    public ParamAgence() {
    }

    public void setCodStrcStrc(Long codStrcStrc) {
        this.codStrcStrc = codStrcStrc;
    }

    public Long getCodStrcStrc() {
        return codStrcStrc;
    }


    public void setCodStrmStrc(Long codStrmStrc) {
        this.codStrmStrc = codStrmStrc;
    }

    public Long getCodStrmStrc() {
        return codStrmStrc;
    }


    public void setCodTstrcTstrc(Long codTstrcTstrc) {
        this.codTstrcTstrc = codTstrcTstrc;
    }

    public Long getCodTstrcTstrc() {
        return codTstrcTstrc;
    }


    public void setDateOp(Date dateOp) {
        this.dateOp = dateOp;
    }

    public Date getDateOp() {
        return new Date();
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

    public void setDateJours(String dateJours) {
        this.dateJours = dateJours;
    }

    public String getDateJours() {
        return dateJours;
    }

    public void setNumMatrUser(String numMatrUser) {
        this.numMatrUser = numMatrUser;
    }

    public String getNumMatrUser() {
        return numMatrUser;
    }

	public void setCodBctStrc(Long codBctStrc) {
		this.codBctStrc = codBctStrc;
	}

	public Long getCodBctStrc() {
		return codBctStrc;
	}
}
