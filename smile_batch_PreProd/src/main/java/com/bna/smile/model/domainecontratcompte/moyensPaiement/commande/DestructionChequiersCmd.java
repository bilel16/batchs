package com.bna.smile.model.domainecontratcompte.moyensPaiement.commande;


import com.bna.commun.util.ContextHandler;
import com.bna.smile.model.domainecontratcompte.moyensPaiement.service.DemandeChequesService;
import com.oxia.fwk.context.Context;
import com.oxia.fwk.core.ICommande;
import com.oxia.fwk.core.IValueObject;
import com.oxia.fwk.core.ValueObject;

/** Fichier: DestructionChequiersCmd.java
  * @version 1.0.0 du 26/06/2007
  * Copyright(c) 2007 BNA (www.bna.com.tn)
  * Classe: DestructionChequiersCmd
  * package: com.bna.smile.model.domainecontratcompte.moyensPaiement.commande;
  * @author : El Arbi Hassine
  * Commande de destruction des chequiers
  */
public class DestructionChequiersCmd implements ICommande {
    Context context = ContextHandler.getContext();

    public DestructionChequiersCmd() {
    }

    public IValueObject execute(IValueObject vo) {
       
        DemandeChequesService demandeChequesService = 
        (DemandeChequesService)context.getBean("demandeChequesService");
        ValueObject voo = (ValueObject)demandeChequesService.destructionChequiers(vo);
        return (voo);
    }
}
