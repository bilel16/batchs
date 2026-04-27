package com.bna.smile.model.domainecaisse.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.bna.commun.model.CaisseCentrale;
import com.bna.commun.model.CaisseDeviseCentrale;
import com.bna.commun.model.CaisseDinarsCentrale;
import com.oxia.fwk.core.ValueObject;

/**
 * Vo qui sauvgarde la situation de la caisse centrale d'une agence
 * à un jour donné
 */
public class SituationCaisseCentraleVo extends ValueObject{

    //----------- Données en entrée
    private Date dateJournee;
    private Long codeStructure;
    
    //----------- Données en sortie
    private CaisseCentrale          caisseCentrale;
    private boolean                 existCaisseCentrale;  
    private CaisseDinarsCentrale    caisseDinarsCentrale;
    private boolean                 existCaisseDinars;  
    private CaisseDeviseCentrale    caisseDeviseCentrale;
    private boolean                 existCaisseDevise;  
    private List                    listDetailCaisDevCen = new ArrayList(0);
    
    public SituationCaisseCentraleVo() {
    }

    public void setCaisseCentrale(CaisseCentrale caisseCentrale) {
        this.caisseCentrale = caisseCentrale;
    }

    public CaisseCentrale getCaisseCentrale() {
        return caisseCentrale;
    }

    public void setCaisseDinarsCentrale(CaisseDinarsCentrale caisseDinarsCentrale) {
        this.caisseDinarsCentrale = caisseDinarsCentrale;
    }

    public CaisseDinarsCentrale getCaisseDinarsCentrale() {
        return caisseDinarsCentrale;
    }

    public void setExistCaisseDinars(boolean existCaisseDinars) {
        this.existCaisseDinars = existCaisseDinars;
    }

    public boolean isExistCaisseDinars() {
        return existCaisseDinars;
    }

    public void setCaisseDeviseCentrale(CaisseDeviseCentrale caisseDeviseCentrale) {
        this.caisseDeviseCentrale = caisseDeviseCentrale;
    }

    public CaisseDeviseCentrale getCaisseDeviseCentrale() {
        return caisseDeviseCentrale;
    }

    public void setExistCaisseDevise(boolean existCaisseDevise) {
        this.existCaisseDevise = existCaisseDevise;
    }

    public boolean isExistCaisseDevise() {
        return existCaisseDevise;
    }

    public void setListDetailCaisDevCen(List listDetailCaisDevCen) {
        this.listDetailCaisDevCen = listDetailCaisDevCen;
    }

    public List getListDetailCaisDevCen() {
        return listDetailCaisDevCen;
    }

    public void setExistCaisseCentrale(boolean existCaisseCentrale) {
        this.existCaisseCentrale = existCaisseCentrale;
    }

    public boolean isExistCaisseCentrale() {
        return existCaisseCentrale;
    }

    public void setDateJournee(Date dateJournee) {
        this.dateJournee = dateJournee;
    }

    public Date getDateJournee() {
        return dateJournee;
    }

    public void setCodeStructure(Long codeStructure) {
        this.codeStructure = codeStructure;
    }

    public Long getCodeStructure() {
        return codeStructure;
    }
}
