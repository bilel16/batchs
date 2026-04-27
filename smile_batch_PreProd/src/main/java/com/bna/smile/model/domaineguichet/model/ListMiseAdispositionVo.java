package com.bna.smile.model.domaineguichet.model;
/**
 * classe pour l'extraction des montant mise à dispositon
 * @author Mdimagh Lassaad 
 * @since 23/11/2007
 * modifié le  30/11/2007
 */
import com.oxia.fwk.core.ValueObject;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ListMiseAdispositionVo extends ValueObject {
    private String TypeMAD; // moneyGram, mise à disposition
    private Date   dateMAD;
    private String etatMAD; // A : attente , V : valide
    private Long structureInitiatrice; // code de la structure
    private List    listMiseAdisposition = new ArrayList();

    public ListMiseAdispositionVo() {
    }

    public void setTypeMAD(String typeMAD) {
        this.TypeMAD = typeMAD;
    }

    public String getTypeMAD() {
        return TypeMAD;
    }

    public void setDateMAD(Date dateMAD) {
        this.dateMAD = dateMAD;
    }

    public Date getDateMAD() {
        return dateMAD;
    }

    public void setEtatMAD(String etatMAD) {
        this.etatMAD = etatMAD;
    }

    public String getEtatMAD() {
        return etatMAD;
    }

    public void setListMiseAdisposition(List listMiseAdisposition) {
        this.listMiseAdisposition = listMiseAdisposition;
    }

    public List getListMiseAdisposition() {
        return listMiseAdisposition;
    }

    public void setStructureInitiatrice(Long structureInitiatrice) {
        this.structureInitiatrice = structureInitiatrice;
    }

    public Long getStructureInitiatrice() {
        return structureInitiatrice;
    }
}
