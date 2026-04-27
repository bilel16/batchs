package com.bna.smile.model.domainecontratcompte.souscriptioncontratcompte.commande;

import com.bna.commun.model.LivretEpargne;
import com.bna.commun.util.ContextHandler;
import com.bna.smile.model.domainecontratcompte.souscriptioncontratcompte.model.Livrets;
import com.bna.smile.model.domainecontratcompte.souscriptioncontratcompte.service.SouscriptionContratCompteService;
import com.oxia.fwk.context.Context;
import com.oxia.fwk.core.ICommande;
import com.oxia.fwk.core.IValueObject;

public class MAJLivretEpargneCmd implements ICommande {
    public MAJLivretEpargneCmd() {
    }
    /**
     * Methode execute
     * @param vo Objet : LivretEpargne
     * @return   Objet : LivretEpargne
     */
    public IValueObject execute(IValueObject vo) {
        Livrets livrets = (Livrets)vo;
        Context context = ContextHandler.getContext();

        SouscriptionContratCompteService souscriptionContratCompteService = (SouscriptionContratCompteService)context.getBean("souscriptionContratCompteService");
        LivretEpargne livretEpargneRetour = (LivretEpargne)souscriptionContratCompteService.MAJLivretEpargne(livrets);
        return (livretEpargneRetour);
    
    }
}
