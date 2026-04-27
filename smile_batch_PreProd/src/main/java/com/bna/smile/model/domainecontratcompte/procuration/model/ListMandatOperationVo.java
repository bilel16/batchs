package com.bna.smile.model.domainecontratcompte.procuration.model;

import java.util.ArrayList;
import java.util.List;

import com.oxia.fwk.core.ValueObject;
/**
 * value Object de retour pour la commande: GetMandatOperationCmd 
 * elle contient une liste des Mandats Généraux
 * et une autre liste des mandats Opérations pour les mandats spéciaux
 * @author Mdimagh Mohamed Lassaad
 * @since 07/05/2007
 */
public class ListMandatOperationVo  extends ValueObject {
 private List listMandatsGeneraux = new ArrayList();
 private List listMandatsSpeciauxOperations = new ArrayList();
    public ListMandatOperationVo() {
    }


    public void setListMandatsGeneraux(List listMandatsGeneraux) {
        this.listMandatsGeneraux = listMandatsGeneraux;
    }

    public List getListMandatsGeneraux() {
        return listMandatsGeneraux;
    }

    public void setListMandatsSpeciauxOperations(List listMandatsSpeciauxOperations) {
        this.listMandatsSpeciauxOperations = listMandatsSpeciauxOperations;
    }

    public List getListMandatsSpeciauxOperations() {
        return listMandatsSpeciauxOperations;
    }
}
