package com.bna.smile.model.domainecontratcompte.moyensPaiement.traitement;

import java.util.List;

import com.bna.commun.model.Chequier;
import com.bna.commun.traitements.Traitement;
import com.bna.smile.model.constant.Constants;
import com.bna.smile.model.domainecontratcompte.moyensPaiement.model.Paramchequiers;
import com.oxia.fwk.context.Context;
import com.oxia.fwk.core.ICriteria;
import com.oxia.fwk.core.IExpression;
import com.oxia.fwk.core.ISearchEngine;
import com.oxia.fwk.core.IValueObject;
import com.oxia.fwk.core.ValueObject;

/**
 * Classe de traitement permettant de retourner l'objet Chequier
 * @author El arbi hassine
 * @param ParamChequier
 * @return Chequier
 * @since 19/05/2008
 * 
 */
public class GetDetailChequierTrt extends Traitement{
    public GetDetailChequierTrt() {
    }

    public IValueObject perform(IValueObject vo) {
        Paramchequiers paramchequiers = (Paramchequiers)vo;
        Chequier chequier = new Chequier();

       try{
           
            ISearchEngine searchEngine=(ISearchEngine)Context.getInstance().getSpringContext().getBean("searchEngine");
            ICriteria criteria = searchEngine.createCriteria();
            IExpression expression = searchEngine.createExpression();


            if (paramchequiers.getNumDemande() != null) {
                criteria.add(expression.eq("demandeCheque.numDemDchq", 
                                           paramchequiers.getNumDemande()));
            }
            
            if (paramchequiers.getNumChequier() != null) {
                criteria.add(expression.eq("chequierId.numChqiChqi", 
                                           paramchequiers.getNumChequier()));
            }

            List l = searchEngine.find(Chequier.class, criteria);
            if (l != null && l.size() > 0) {
                chequier = (Chequier)l.get(0);                
            }
        } catch (Exception e) {
            com.oxia.fwk.core.Error erreur = new com.oxia.fwk.core.Error();
            erreur.setCode("Technique");
            erreur.setDescription("GetDetailChequierTrt " + e.toString());            
            chequier.addError(erreur);
            logger.error("Exception : ",e); 
            throw new RuntimeException(e);
        }
        
        return (chequier);
    }
    
    public void genCroText(ValueObject vo) {    
    
    }
    
    public String getNumeroTache(IValueObject vo) {
      return (Constants.CODE_RESSOURCE_GENERALE);        
    }  
}
