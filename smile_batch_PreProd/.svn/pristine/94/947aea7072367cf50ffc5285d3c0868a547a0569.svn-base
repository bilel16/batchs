package com.bna.smile.model.domainecontratcompte.moyensPaiement.traitement;

import java.util.ArrayList;
import java.util.List;

import com.bna.commun.model.DetailOperationChequier;
import com.bna.commun.traitements.Traitement;
import com.bna.commun.util.ContextHandler;
import com.bna.smile.model.constant.Constants;
import com.bna.smile.model.domainecontratcompte.moyensPaiement.model.ListesDemandesCheques;
import com.bna.smile.model.domainecontratcompte.moyensPaiement.model.ParamDemandeCheque;
import com.oxia.fwk.context.Context;
import com.oxia.fwk.core.ICriteria;
import com.oxia.fwk.core.IExpression;
import com.oxia.fwk.core.ISearchEngine;
import com.oxia.fwk.core.IValueObject;
import com.oxia.fwk.core.ValueObject;

/**
 * Classe de traitement :permet de donner la liste de toutes les détails opérations chéquiers par demande  
 * @author El arbi hassine
 * @since 03/07/2007
 * 
 */
public class GetListDetailOperationChequierTrt extends Traitement{
    public GetListDetailOperationChequierTrt() {
    }

    public IValueObject perform(IValueObject vo) {
        ParamDemandeCheque paramDemandeCheque = (ParamDemandeCheque)vo;
        ListesDemandesCheques listesDemandesCheques = 
            new ListesDemandesCheques();

        List listeHistorique = new ArrayList();

        try {
            Context context = ContextHandler.getContext();
            ISearchEngine searchEngine=(ISearchEngine)Context.getInstance().getSpringContext().getBean("searchEngine");
            ICriteria criteria = searchEngine.createCriteria();
            IExpression expression = searchEngine.createExpression();

            if (paramDemandeCheque.getNumDemande() != null) {
                criteria.add(expression.eq("detailOperationChequierId.numDemDchq", 
                                           paramDemandeCheque.getNumDemande()));
            }


            List l = 
                searchEngine.find(DetailOperationChequier.class, criteria);
            if (l != null && l.size() > 0) {
                listeHistorique = l;
                listesDemandesCheques.setListeDetailOperationChequier(listeHistorique);
            }

            return (listesDemandesCheques);

        } catch (Exception e) {
            com.oxia.fwk.core.Error erreur = new com.oxia.fwk.core.Error();
            StringBuffer text = 
                new StringBuffer("Erreur dans GetListDetailOperationChequierTrt : ");
            text.append(e.toString());
            erreur.setCode("100");
            erreur.setDescription(text.toString());
            erreur.setKey("GetListDetailOperationChequier");
            listesDemandesCheques.addError(erreur);
            logger.error("Exception : ",e); 
            return (listesDemandesCheques);
        }
    }
    
    public void genCroText(ValueObject vo) {    
    
    }
    
    public String getNumeroTache(IValueObject vo) {
      return (Constants.CODE_RESSOURCE_GENERALE);        
    }  
}
