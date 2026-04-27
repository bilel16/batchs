package com.bna.habil.domain.beans;


import java.io.Serial;
import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;


public class UtilisateurProfilBean implements Serializable {
    @Serial
    private static final long serialVersionUID = -4588473658643004567L;


    private String codPflPfl;

    private String numMatrUser;


    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy", locale = "fr-FR", timezone = "GMT+01:00")
    private Date datFadhUtpr;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy", locale = "fr-FR", timezone = "GMT+01:00")
    private Date datdadhutpr;

    private Integer boolEtatUtpr;

    public String getCodPflPfl() {
        return codPflPfl;
    }

    public void setCodPflPfl(String codPflPfl) {
        this.codPflPfl = codPflPfl;
    }

    public String getNumMatrUser() {
        return numMatrUser;
    }

    public void setNumMatrUser(String numMatrUser) {
        this.numMatrUser = numMatrUser;
    }

    public Date getDatFadhUtpr() {
        return datFadhUtpr;
    }

    public void setDatFadhUtpr(Date datFadhUtpr) {
        this.datFadhUtpr = datFadhUtpr;
    }

    public Date getDatdadhutpr() {
        return datdadhutpr;
    }

    public void setDatdadhutpr(Date datdadhutpr) {
        this.datdadhutpr = datdadhutpr;
    }

    public Integer getBoolEtatUtpr() {
        return boolEtatUtpr;
    }

    public void setBoolEtatUtpr(Integer boolEtatUtpr) {
        this.boolEtatUtpr = boolEtatUtpr;
    }

    public static long getSerialversionuid() {
        return serialVersionUID;
    }

    public UtilisateurProfilBean(String codPflPfl, String numMatrUser, Date datFadhUtpr, Date datdadhutpr,
                                 Integer boolEtatUtpr) {
        super();
        this.codPflPfl = codPflPfl;
        this.numMatrUser = numMatrUser;
        this.datFadhUtpr = datFadhUtpr;
        this.datdadhutpr = datdadhutpr;
        this.boolEtatUtpr = boolEtatUtpr;
    }

    public UtilisateurProfilBean() {
        super();
    }


}
