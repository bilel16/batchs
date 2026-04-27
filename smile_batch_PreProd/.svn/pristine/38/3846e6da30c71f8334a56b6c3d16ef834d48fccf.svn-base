package com.bna.smile.model.domainecontratcompte.moyensPaiement.commande;


import com.bna.commun.util.ContextHandler;
import com.bna.smile.model.domainecontratcompte.moyensPaiement.service.DemandeChequesService;
import com.oxia.fwk.context.Context;
import com.oxia.fwk.core.ICommande;
import com.oxia.fwk.core.IValueObject;
import com.oxia.fwk.core.ValueObject;

/** Fichier: ValidationDemandeChequeCmd.java
  * @version 1.0.0 du 13/06/2007
  * Copyright(c) 2007 BNA (www.bna.com.tn)
  * Classe: ValidationDemandeChequeCmd
  * package: com.bna.smile.model.domainecontratcompte.moyensPaiement.commande;
  * @author : El Arbi Hassine
  * Commande de Validation d'une demande de cheques
  */
public class ValidationDemandeChequeCmd implements ICommande {
    Context context = ContextHandler.getContext();

    public ValidationDemandeChequeCmd() {
    }

    public IValueObject execute(IValueObject vo) {
        
        DemandeChequesService demandeChequesService = 
        (DemandeChequesService)context.getBean("demandeChequesService");
       ValueObject voo = (ValueObject)demandeChequesService.validerDemandeCheque(vo);
        return (voo);
    }
}
