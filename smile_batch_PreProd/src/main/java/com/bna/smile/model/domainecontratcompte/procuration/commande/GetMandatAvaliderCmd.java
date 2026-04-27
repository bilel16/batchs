
package com.bna.smile.model.domainecontratcompte.procuration.commande;

import com.bna.commun.util.ContextHandler;
import com.bna.smile.model.domainecommun.model.Listes;
import com.bna.smile.model.domainecontratcompte.procuration.model.MandatRecherche;
import com.bna.smile.model.domainecontratcompte.procuration.service.ProcurationService;
import com.oxia.fwk.context.Context;
import com.oxia.fwk.core.ICommande;
import com.oxia.fwk.core.IValueObject;


/** Fichier: GetContratMandatCmd.java 
 * @version 1.0.0 du 20/02/2006
 * Copyright(c) 2006 BNA (www.bna.com.tn)
 * Classe: GetContratMandatCmd
 * package com.bna.smile.model.domainecontratcompte.souscriptioncontratcompte.commande
 * @author : Boussen Youssef & Kriaa Hatem
 */
public class GetMandatAvaliderCmd implements ICommande{

    public GetMandatAvaliderCmd() {
    }

    /**
     * Methode execute
     * @param vo Objet : ContratCptId
     * @return   Objet : ContratCptMandat
     */
    public IValueObject execute(IValueObject vo) {
        MandatRecherche mandatRecherche = (MandatRecherche)vo;
        Context context = ContextHandler.getContext();
                 
        ProcurationService procurationService = (ProcurationService)context.getBean("procurationService");
        Listes liste = (Listes)procurationService.getMandatAvalider(mandatRecherche);
        return(liste);
    }

}
