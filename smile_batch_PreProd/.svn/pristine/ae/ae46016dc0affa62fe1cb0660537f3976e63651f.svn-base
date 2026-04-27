package com.bna.smile.model.domainecontratcompte.moyensPaiement.commande;

import com.bna.commun.util.ContextHandler;
import com.bna.smile.model.domainecontratcompte.moyensPaiement.service.DemandeCartesService;
import com.oxia.fwk.context.Context;
import com.oxia.fwk.core.ValueObject;

  /**
   * Rejeter une demande carte donnée selon operation de rejet:rejet demande, rejet délivrance ; selon le
   * motif de rejet:demande en cours, demande carte de ^meme type, rejet DR/DQMRP...; et selon personnel qui a fait l'opération
   * @author Ramzi
   * @param DemandeCarte:: DemandeCarte aprés modification du : codOperOper: operation de rejet:rejet demande, rejet délivrance et
   * du codMotfMrej; motif de rejet:demande en cours, demande carte de ^meme type, rejet DR/DQMRP...
   * @return DemandeCarte
   * @since 19/06/2007
   * 
   */
public class RejetDemandeCarteCmd {
    public RejetDemandeCarteCmd() {
    }

    public ValueObject execute(ValueObject vo) {
        Context context = ContextHandler.getContext();
        DemandeCartesService demandeCartesService = 
            (DemandeCartesService)context.getBean("demandeCartesService");     
        return (ValueObject)demandeCartesService.rejetDemandeCarte(vo);
    }
}
