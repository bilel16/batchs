package com.bna.smile.model.domainecontratcompte.procuration.model;

import com.bna.commun.model.CoTitulaire;
import com.oxia.fwk.core.ValueObject;

/**
 * value Object qui contient : typePouvoir:le type du pouvoir pour une personne sur un contrat donné (T:Titulaaire,M:Mandataire,C:Cotitulaire) &
 * ListMandatOperationVo (liste des mandats valide) & Cotitulaire (entité cotitulaire si le cas)
 * @author Ramzi
 * @since 23/05/2007
 */
public class PouvoirVo extends ValueObject {
    private String typePouvoir;
    private ListMandatOperationVo listMandatOperation;
    private CoTitulaire coTitulaire;

    public PouvoirVo() {
    }

    public void setTypePouvoir(String typePouvoir) {
        this.typePouvoir = typePouvoir;
    }

    public String getTypePouvoir() {
        return typePouvoir;
    }


    public void setCoTitulaire(CoTitulaire coTitulaire) {
        this.coTitulaire = coTitulaire;
    }

    public CoTitulaire getCoTitulaire() {
        return coTitulaire;
    }

    public void setListMandatOperation(ListMandatOperationVo listMandatOperation) {
        this.listMandatOperation = listMandatOperation;
    }

    public ListMandatOperationVo getListMandatOperation() {
        return listMandatOperation;
    }
}
