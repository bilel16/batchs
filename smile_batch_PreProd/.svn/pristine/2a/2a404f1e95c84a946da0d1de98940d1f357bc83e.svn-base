package com.bna.smile.model.domaineplacement.model;

import java.util.Date;

import com.bna.commun.model.Structure;
import com.oxia.fwk.core.ValueObject;

/** Classe de données permettant de communiquer la date debut,la date fin et 
 * le type d'interval (J:journalière,M:mensuelle)
 *  @author Ramzi 
 *  @since 07-10-09
 *  @version 1.0
 *  */

public class ParamInteretServi extends ValueObject{
    public ParamInteretServi() {
    }
    private Date dateComptableAgence;
    private Structure structure;
    private boolean finBatchStructure = false;

    public void setDateComptableAgence(Date dateComptableAgence) {
        this.dateComptableAgence = dateComptableAgence;
    }

    public Date getDateComptableAgence() {
        return dateComptableAgence;
    }


    public void setStructure(Structure structure) {
        this.structure = structure;
    }

    public Structure getStructure() {
        return structure;
    }

    public void setFinBatchStructure(boolean finBatchStructure) {
        this.finBatchStructure = finBatchStructure;
    }

    public boolean isFinBatchStructure() {
        return finBatchStructure;
    }
}
