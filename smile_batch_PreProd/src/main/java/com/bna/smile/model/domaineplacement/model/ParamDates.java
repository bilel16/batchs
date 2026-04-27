package com.bna.smile.model.domaineplacement.model;

import java.util.Date;

import com.oxia.fwk.core.ValueObject;

/** Classe de données permettant de communiquer la date debut,la date fin et 
 * le type d'interval (J:journalière,M:mensuelle)
 *  @author Boussen Youssef
 *  @since 13-05-09
 *  @version 1.0
 *  */

public class ParamDates extends ValueObject{
    public ParamDates() {
    }
    private Date dateDebut;
    private Date dateFin;
    private String interval="M";

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

    public void setInterval(String interval) {
        this.interval = interval;
    }

    public String getInterval() {
        return interval;
    }
}
