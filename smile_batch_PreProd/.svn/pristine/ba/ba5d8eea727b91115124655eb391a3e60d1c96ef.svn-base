package com.bna.smile.model.domainecontratcompte.moyensPaiement.commande;

import com.bna.commun.util.ContextHandler;
import com.bna.smile.model.domainecontratcompte.moyensPaiement.service.DemandeCartesService;
import com.oxia.fwk.context.Context;
import com.oxia.fwk.core.ValueObject;

 /**
  * Vérifier s’il existe une demande de carte de même type en cours d’exécution  pour un porteur donné sur un contrat donné.
  * @author Ramzi
  * @param PersonneTypeCarteCpt:: PersonneStrc : le porteur,  TypeCarteCpt : type de carte et contrat
  * @return DemandeCarte
  * @since 19/06/2007
  * 
  */
public class VerifDemandeCarteEnCoursCmd {
    public VerifDemandeCarteEnCoursCmd() {
    }

    public ValueObject execute(ValueObject vo) {
        Context context = ContextHandler.getContext();
        DemandeCartesService demandeCartesService = 
            (DemandeCartesService)context.getBean("demandeCartesService"); 
        return (ValueObject)demandeCartesService.verifDemandeCarteEnCours(vo);
    }
}
