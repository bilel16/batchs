package com.bna.smile.model.domainecompensation.gestionrejet.commande;

import com.bna.commun.util.ContextHandler;
import com.bna.smile.model.domainecommun.model.Listes;
import com.bna.smile.model.domainecompensation.gestionrejet.model.ContratRejet;
import com.bna.smile.model.domainecompensation.gestionrejet.model.Rejet;
import com.bna.smile.model.domainecompensation.gestionrejet.service.GestionRejetService;
import com.bna.smile.model.domainecontratcompte.procuration.model.MandatRecherche;
import com.bna.smile.model.domainecontratcompte.procuration.service.ProcurationService;

import com.oxia.fwk.context.Context;
import com.oxia.fwk.core.ValueObject;

public class ChercherRejetCmd {
    public ChercherRejetCmd() {
    }
    /**
         * Methode execute
         * @param vo Objet : Rejet
         * @return   Objet : ContratRejet
         */
    public ValueObject execute(ValueObject vo) {
        Rejet rejet = (Rejet)vo;
        Context context = ContextHandler.getContext();
                 
        GestionRejetService gestionRejetService = (GestionRejetService)context.getBean("gestionRejetService");
        ContratRejet contratRejet = (ContratRejet)gestionRejetService.chercherRejet(rejet);
        return(contratRejet);
    }
}
