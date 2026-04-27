package com.bna.smile.model.domainecommun.service;

import com.bna.smile.model.domainecommun.traitement.GetRegleGestionContratTrt;

import com.oxia.fwk.core.IValueObject;
import com.oxia.fwk.core.ValueObject;

public class ProduitService {
    public ProduitService() {
    }
    
    /** méthode pour la recherche d'une regle de gestion d'un produit pour une operation 
     * avecun type determiné
     * @param  ValueObject : RegleGestionContratId : l'identifiant de la regle de gestion
     * @return ValueObject : RegleGestionContrat  :La regle de gestion
     */
    public IValueObject getRegleGestionContrat(IValueObject vo) {

        GetRegleGestionContratTrt getRegleGestionContratTrt = new GetRegleGestionContratTrt();
        return (getRegleGestionContratTrt.exec(vo));
    }

}
