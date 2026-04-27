
package com.bna.smile.model.domainecontratcompte.souscriptioncontratcompte.commande;


import com.bna.commun.model.Signature;
import com.bna.commun.util.ContextHandler;
import com.bna.smile.model.domainecommun.model.ContratPersonne;
import com.bna.smile.model.domainecontratcompte.souscriptioncontratcompte.service.SouscriptionContratCompteService;
import com.oxia.fwk.context.Context;
import com.oxia.fwk.core.ICommande;
import com.oxia.fwk.core.IValueObject;

 /**
  * méthode d'extraction d'un objet signature en prend en argument 
  * le contrat + personne
  * @param vo ContratPersonne 
  * @return vo Signature
  * @author :Ramzi
  */
public class GetSignaturesCmd implements ICommande {
    public GetSignaturesCmd() {
    }


    public IValueObject execute(IValueObject vo) {
        ContratPersonne contratPersonne = (ContratPersonne)vo;
        Context context = ContextHandler.getContext();

        SouscriptionContratCompteService souscriptionContratCompteService = 
            (SouscriptionContratCompteService)context.getBean("souscriptionContratCompteService");
        Signature signature = 
            (Signature)souscriptionContratCompteService.getSignatures(contratPersonne);
        return (signature);

    }
}
