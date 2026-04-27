package com.bna.smile.model.domainecaisse.model;

import java.util.Date;

import com.bna.commun.model.CaisseDevises;
import com.bna.commun.model.CaisseDinars;
import com.bna.commun.model.DetailCaisDevAg;
import com.bna.commun.model.DetailCaisseStructure;
import com.bna.commun.model.DetailSessionCaisse;
import com.bna.commun.model.JourneeCaisse;
import com.oxia.fwk.core.ValueObject;

/**
 * value object pour les données d'un détail Caisse structure
 * @author Mdimagh Med Lassaad
 * @since 21/12/2007
 * 
 */
public class SituationDetailCaisseStructureVo extends ValueObject{
    //--------- Les éléments de recherche ----------//
    private Long codeStructure;
    private Long numeroCaisse;
    private Date dateJournee;
    
    //--------- Les éléments retournés -------------//
    private DetailCaisseStructure detailCaisseStructure;
    private boolean             existDetailCaisseStructure;
    private CaisseDinars        caisseDinars;
    private boolean             existCaisseDinars;
    private CaisseDevises       caisseDevises;
    private boolean             existCaisseDevises;
    private DetailCaisDevAg     detailCaisDevAg;
    private JourneeCaisse journeeCaisseIn;
    private JourneeCaisse JourneeCaisseOut;
    private DetailSessionCaisse detailSessionCaisse;

    public SituationDetailCaisseStructureVo() {
    }

    public void setDetailCaisseStructure(DetailCaisseStructure detailCaisseStructure) {
        this.detailCaisseStructure = detailCaisseStructure;
    }

    public DetailCaisseStructure getDetailCaisseStructure() {
        return detailCaisseStructure;
    }

    public void setExistDetailCaisseStructure(boolean existDetailCaisseStructure) {
        this.existDetailCaisseStructure = existDetailCaisseStructure;
    }

    public boolean isExistDetailCaisseStructure() {
        return existDetailCaisseStructure;
    }

    public void setCaisseDinars(CaisseDinars caisseDinars) {
        this.caisseDinars = caisseDinars;
    }

    public CaisseDinars getCaisseDinars() {
        return caisseDinars;
    }

    public void setExistCaisseDinars(boolean existCaisseDinars) {
        this.existCaisseDinars = existCaisseDinars;
    }

    public boolean isExistCaisseDinars() {
        return existCaisseDinars;
    }

    public void setCaisseDevises(CaisseDevises caisseDevises) {
        this.caisseDevises = caisseDevises;
    }

    public CaisseDevises getCaisseDevises() {
        return caisseDevises;
    }

    public void setExistCaisseDevises(boolean existCaisseDevises) {
        this.existCaisseDevises = existCaisseDevises;
    }

    public boolean isExistCaisseDevises() {
        return existCaisseDevises;
    }

    public void setDetailCaisDevAg(DetailCaisDevAg detailCaisDevAg) {
        this.detailCaisDevAg = detailCaisDevAg;
    }

    public DetailCaisDevAg getDetailCaisDevAg() {
        return detailCaisDevAg;
    }

    public void setCodeStructure(Long codeStructure) {
        this.codeStructure = codeStructure;
    }

    public Long getCodeStructure() {
        return codeStructure;
    }

    public void setNumeroCaisse(Long numeroCaisse) {
        this.numeroCaisse = numeroCaisse;
    }

    public Long getNumeroCaisse() {
        return numeroCaisse;
    }

    public void setDateJournee(Date dateJournee) {
        this.dateJournee = dateJournee;
    }

    public Date getDateJournee() {
        return dateJournee;
    }

    public void setJourneeCaisseOut(JourneeCaisse journeeCaisseOut) {
        this.JourneeCaisseOut = journeeCaisseOut;
    }

    public JourneeCaisse getJourneeCaisseOut() {
        return JourneeCaisseOut;
    }

    public void setJourneeCaisseIn(JourneeCaisse journeeCaisseIn) {
        this.journeeCaisseIn = journeeCaisseIn;
    }

    public JourneeCaisse getJourneeCaisseIn() {
        return journeeCaisseIn;
    }

    public void setDetailSessionCaisse(DetailSessionCaisse detailSessionCaisse) {
        this.detailSessionCaisse = detailSessionCaisse;
    }

    public DetailSessionCaisse getDetailSessionCaisse() {
        return detailSessionCaisse;
    }
}
