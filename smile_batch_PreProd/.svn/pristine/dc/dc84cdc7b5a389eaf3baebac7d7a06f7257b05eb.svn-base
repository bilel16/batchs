
package com.bna.smile.model.domainecontratcompte.souscriptioncontratcompte.commande;


import com.bna.commun.util.ContextHandler;
import com.bna.smile.model.domainecommun.model.Listes;
import com.bna.smile.model.domainecontratcompte.souscriptioncontratcompte.model.ListeCotit;
import com.bna.smile.model.domainecontratcompte.souscriptioncontratcompte.service.SouscriptionContratCompteService;
import com.oxia.fwk.context.Context;
import com.oxia.fwk.core.ICommande;
import com.oxia.fwk.core.IValueObject;

/** Fichier: InsertCotitulaireCmd.java
 * @version 1.0.0 du 26/01/2006
 * Copyright(c) 2006 BNA (www.bna.com.tn)
 * Classe: InsertCotitulaireCmd
 * package com.bna.smile.model.domainecontratcompte.souscriptioncontratcompte.commande
 * @author :Boussen Youssef & Kriaa Hatem
 */
public class InsertCotitulaireCmd implements ICommande {
    public InsertCotitulaireCmd() {
    }

    /**
     * Methode execute
     * @param vo Objet : ListeCotit
     * @return   Objet : Listes
     */
    public IValueObject execute(IValueObject vo) {
        ListeCotit listeCotit = (ListeCotit)vo;
        Context context = ContextHandler.getContext();

        SouscriptionContratCompteService souscriptionContratCompteService = 
            (SouscriptionContratCompteService)context.getBean("souscriptionContratCompteService");
        Listes listCot = 
            (Listes)souscriptionContratCompteService.insertCotitulaire(listeCotit);
        return (listCot);

    }
}
