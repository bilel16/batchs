package com.bna.smile.model.domainecontratcompte.moyensPaiement.commande;

import com.bna.commun.util.ContextHandler;
import com.bna.commun.vo.PrimitiveVO;
import com.bna.smile.model.domainecontratcompte.moyensPaiement.service.DemandeCartesService;
import com.oxia.fwk.context.Context;
import com.oxia.fwk.core.ValueObject;

   /**
    * extraire carte bancaire selon numéro carte et retourne 
    * les erreur applicatifs suivantes: contrat non valide, Fin pouvoir sur cet opération
    * @author Ramzi
    * @param PrimitiveVO: String numéro de la demande carte 
    * @return CarteBancaire
    * @since 21/06/2007
    * 
    */
public class GetCarteBancaireCmd {
    public GetCarteBancaireCmd() {
    }

    public ValueObject execute(ValueObject vo) {
        Context context = ContextHandler.getContext();
        PrimitiveVO primitiveVO = (PrimitiveVO)vo;
        DemandeCartesService demandeCartesService = 
            (DemandeCartesService)context.getBean("demandeCartesService");         
        return (ValueObject)demandeCartesService.getCarteBancaire(primitiveVO);
    }
}
