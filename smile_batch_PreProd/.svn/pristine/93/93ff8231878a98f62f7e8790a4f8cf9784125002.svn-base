package com.bna.smile.model.domainecaisse.model;

import java.util.ArrayList;
import java.util.Collection;

import com.bna.commun.model.Personnel;
import com.bna.commun.model.SessionJrnCaisse;
import com.oxia.fwk.core.ValueObject;

/**
 * classe qui pêrmet d'envoyer la session principale et la
 * session de vacation
 * @author BOUSSEN Youssef
 * @since 12/04/2011
 * 
 */
public class SessionJrnCaissePrVac extends ValueObject{

    SessionJrnCaisse sessionJrnCaissePr;
    SessionJrnCaisse sessionJrnCaisseVac;
    Collection listDetailSessionCaisseVac = new ArrayList();
    Personnel personnel;
    
    public SessionJrnCaissePrVac() {
    }

    public void setSessionJrnCaissePr(SessionJrnCaisse sessionJrnCaissePr) {
        this.sessionJrnCaissePr = sessionJrnCaissePr;
    }

    public SessionJrnCaisse getSessionJrnCaissePr() {
        return sessionJrnCaissePr;
    }

    public void setSessionJrnCaisseVac(SessionJrnCaisse sessionJrnCaisseVac) {
        this.sessionJrnCaisseVac = sessionJrnCaisseVac;
    }

    public SessionJrnCaisse getSessionJrnCaisseVac() {
        return sessionJrnCaisseVac;
    }

    public void setListDetailSessionCaisseVac(Collection listDetailSessionCaisseVac) {
        this.listDetailSessionCaisseVac = listDetailSessionCaisseVac;
    }

    public Collection getListDetailSessionCaisseVac() {
        return listDetailSessionCaisseVac;
    }

    public void setPersonnel(Personnel personnel) {
        this.personnel = personnel;
    }

    public Personnel getPersonnel() {
        return personnel;
    }
}
