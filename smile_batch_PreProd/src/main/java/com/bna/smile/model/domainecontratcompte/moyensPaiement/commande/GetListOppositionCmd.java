package com.bna.smile.model.domainecontratcompte.moyensPaiement.commande;
import com.bna.commun.util.ContextHandler;
import com.bna.smile.model.domainecommun.model.Listes;
import com.bna.smile.model.domainecontratcompte.moyensPaiement.model.ParamRechercheOpposition;
import com.bna.smile.model.domainecontratcompte.moyensPaiement.service.OppositionMoyPaiService;
import com.oxia.fwk.context.Context;
import com.oxia.fwk.core.ICommande;
import com.oxia.fwk.core.IValueObject;

/**
 * Commande permet de fournir la liste des oppositions sur moyen de paiement
 * @author lamia j.
 * @since 14/04/2007
 * */
public class GetListOppositionCmd implements ICommande{
    public GetListOppositionCmd() {
    }
    
    /**
         * methode execute 
         * @param value Object :  ParamRechercheOpposition
         * @return value Object : ParamRechercheOpposition
         */
        public IValueObject execute(IValueObject vo) {
            Context context = ContextHandler.getContext();
            ParamRechercheOpposition paramRechercheOpposition = (ParamRechercheOpposition)vo;
            Listes listOppositionMoyenPaiement= new Listes();
            OppositionMoyPaiService oppositionMoyPaiService = 
                (OppositionMoyPaiService)context.getBean("oppositionMoyPaiService");
            listOppositionMoyenPaiement = 
            (Listes)oppositionMoyPaiService.getListOppositions(paramRechercheOpposition);
            return (listOppositionMoyenPaiement);
        }
}
