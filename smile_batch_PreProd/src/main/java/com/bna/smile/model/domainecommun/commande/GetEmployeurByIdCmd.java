package com.bna.smile.model.domainecommun.commande;

import com.bna.commun.model.Activite;
import com.bna.commun.model.Employeur;
import com.bna.commun.util.ContextHandler;
import com.bna.smile.model.domainecommun.service.NomenclatureService;

import com.oxia.fwk.context.Context;
import com.oxia.fwk.core.ICommande;
import com.oxia.fwk.core.IValueObject;
import com.oxia.fwk.core.ValueObject;

/**
 * Classe permet la recherche d'un employeur par son identifiant
 * @author Mdimagh Med Lassaad
 */
public class GetEmployeurByIdCmd implements ICommande{
    public GetEmployeurByIdCmd() {
    }
    
    /**
     * executer la recherce de l'objet Employeur
     * @param vo  :Employeur
     * @return vO : Employeur
     */
    public

    IValueObject execute(IValueObject vo) {
        Employeur employeur = (Employeur)vo;
        Context context = ContextHandler.getContext();

        NomenclatureService nomenclatureService = 
            (NomenclatureService)context.getBean("nomenclatureService");
            employeur = (Employeur)nomenclatureService.getEmployeurById(employeur);
            return (employeur);
        }
}
