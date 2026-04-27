
package com.bna.smile.model.domainecontratcompte.souscriptioncontratcompte.commande;


import com.bna.commun.util.ContextHandler;
import com.bna.smile.model.domainecontratcompte.souscriptioncontratcompte.model.SignaturePersCpt;
import com.bna.smile.model.domainecontratcompte.souscriptioncontratcompte.service.SouscriptionContratCompteService;
import com.oxia.fwk.context.Context;
import com.oxia.fwk.core.ICommande;
import com.oxia.fwk.core.IValueObject;

 /**
  * méthode d'insertion des signatures Français et Arabe d'un client en prend en argument 
  * le contrat + personne + bufferedImage Fr + bufferedImage Ar
  * @param vo SignaturePersCpt:ContratPersonne, BufferedImage, BufferedImage 
  * @return vo SignaturePersCpt
  * @author :Ramzi
  */
public class InsertSignaturesCmd implements ICommande {
    public InsertSignaturesCmd() {
    }


    public IValueObject execute(IValueObject vo) {
        SignaturePersCpt signaturePersCpt = (SignaturePersCpt)vo;
        Context context = ContextHandler.getContext();

        SouscriptionContratCompteService souscriptionContratCompteService = 
            (SouscriptionContratCompteService)context.getBean("souscriptionContratCompteService");
        SignaturePersCpt signPersCpt = 
            (SignaturePersCpt)souscriptionContratCompteService.insertSignatures(signaturePersCpt);
        return (signPersCpt);

    }
}
