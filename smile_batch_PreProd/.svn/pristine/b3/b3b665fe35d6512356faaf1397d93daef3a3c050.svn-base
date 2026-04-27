package com.bna.smile.model.domainecontratcompte.souscriptioncontratcompte.commande;

import com.bna.commun.model.LivretEpargne;
import com.bna.commun.util.ContextHandler;
import com.bna.smile.model.domainecontratcompte.souscriptioncontratcompte.service.SouscriptionContratCompteService;
import com.oxia.fwk.context.Context;
import com.oxia.fwk.core.ICommande;
import com.oxia.fwk.core.IValueObject;
/** Fichier: InsertLivretEpargneCmd.java
 * @version 1.0.0 du 30/08/2007
 * Copyright(c) 2006 BNA (www.bna.com.tn)
 * Classe: InsertLivretEpargneCmd
 * package com.bna.smile.model.domainecontratcompte.souscriptioncontratcompte.commande
 * @author :Boussen Youssef & Kriaa Hatem
 */
public class InsertLivretEpargneCmd implements ICommande {
    public InsertLivretEpargneCmd() {
    }
    /**
     * Methode execute
     * @param vo Objet : LivretEpargne
     * @return   Objet : LivretEpargne
     */
    public IValueObject execute(IValueObject vo) {
        LivretEpargne LivretEpargne = (LivretEpargne)vo;
        Context context = ContextHandler.getContext();

        SouscriptionContratCompteService souscriptionContratCompteService = 
            (SouscriptionContratCompteService)context.getBean("souscriptionContratCompteService");
        LivretEpargne LivretEpargneRetour = 
            (LivretEpargne)souscriptionContratCompteService.insertCotitulaire(LivretEpargne);
        return (LivretEpargneRetour);

    }
}
