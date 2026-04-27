package com.bna.smile.web.procuration.util;

import com.bna.commun.model.Mandat;

import java.sql.Date;

public class MandatView extends com.oxia.fwk.core.ValueObject implements java.io.Serializable {
    Mandat mandat;
    String dateDebut;
    String dateFin;

    String type;
    

    public MandatView() {
    }

    public void setMandat(Mandat mandat) {
        this.mandat = mandat;
    }

    public Mandat getMandat() {
        return mandat;
    }


    public void setDateDebut(String dateDebut) {
        this.dateDebut = dateDebut;
    }

    public String getDateDebut() {
        return dateDebut;
    }

    public void setDateFin(String dateFin) {
        this.dateFin = dateFin;
    }

    public String getDateFin() {
        return dateFin;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }
}
