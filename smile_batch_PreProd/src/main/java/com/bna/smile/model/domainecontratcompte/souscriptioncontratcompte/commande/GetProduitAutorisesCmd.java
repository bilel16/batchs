
package com.bna.smile.model.domainecontratcompte.souscriptioncontratcompte.commande;

import com.bna.commun.util.ContextHandler;
import com.bna.smile.model.domainecommun.model.Listes;
import com.bna.smile.model.domainecontratcompte.souscriptioncontratcompte.model.ParamPers;
import com.bna.smile.model.domainecontratcompte.souscriptioncontratcompte.service.SouscriptionContratCompteService;
import com.oxia.fwk.context.Context;
import com.oxia.fwk.core.ICommande;
import com.oxia.fwk.core.IValueObject;

/** Fichier: GetProduitCmd.java
 * @version 1.0.0 du 19/01/2006
 * Copyright(c) 2006 BNA (www.bna.com.tn)
 * Classe: GetProduitCmd
 * package com.bna.smile.model.domainecommun.commande
 * @author : Boussen Youssef & Kriaa Hatem
 */
public class GetProduitAutorisesCmd implements ICommande {

    /** methode execute
     * @param  Objet ParamPers     :Les paramétres de filtrage;
     * @return Objet Listes        :Liste des produits;
     **/
    public IValueObject execute(IValueObject vo) {
        ParamPers paramPers = (ParamPers)vo;
        Context context = ContextHandler.getContext();

        SouscriptionContratCompteService souscriptionContratCompteService = 
            (SouscriptionContratCompteService)context.getBean("souscriptionContratCompteService");
        Listes listes = 
            (Listes)souscriptionContratCompteService.getProduitAutorises(paramPers);
        return (listes);
    }

}
