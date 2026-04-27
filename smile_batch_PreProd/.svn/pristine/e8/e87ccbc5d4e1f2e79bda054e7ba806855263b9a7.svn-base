package com.bna.smile.model.domainecommun.traitement;

import com.bna.commun.model.TypeModification;
import com.bna.commun.traitements.Traitement;
import com.bna.commun.util.ContextHandler;

import com.bna.smile.model.constant.Constants;

import com.oxia.fwk.context.Context;
import com.oxia.fwk.core.ICriteria;
import com.oxia.fwk.core.IExpression;
import com.oxia.fwk.core.ISearchEngine;
import com.oxia.fwk.core.IValueObject;
import com.oxia.fwk.core.ValueObject;
import com.oxia.fwk.searchengine.SearchEngine;

import java.util.List;

/**
 * classe pour la recherche d'un Type de modification
 * @author Mdimagh Med
 * @since 31/05/07
 */
public class GetTypeModificationTrt  extends Traitement {
    public GetTypeModificationTrt() {
    }

    /**
     * methde d'execution de la recherche
     * @param vo : CodePostal
     * @return vo :CodePostal
     */
    public IValueObject perform(IValueObject vo) {


        TypeModification typeModification = (TypeModification)vo;
        ISearchEngine searchEngine=(ISearchEngine)Context.getInstance().getSpringContext().getBean("searchEngine");
        ICriteria criteria = searchEngine.createCriteria();
        IExpression expression = searchEngine.createExpression();

        try {
            criteria.add(expression.eq("codCodModf", 
                                       typeModification.getCodCodModf()));

            List ListTypeModification = 
                searchEngine.find(TypeModification.class, criteria);
            /*si le pays existe*/
            if (ListTypeModification != null && 
                ListTypeModification.size() > 0) {
                typeModification = 
                        (TypeModification)ListTypeModification.get(0);
            }

           

            } catch (Exception e) {
                com.oxia.fwk.core.Error erreur = new com.oxia.fwk.core.Error();
                StringBuffer text = 
                    new StringBuffer("Erreur dans GetTypeModificationTrt : ");
                text.append(e.toString());
                erreur.setCode("200");
                erreur.setDescription(text.toString());
                erreur.setKey("GetTypeModificationTrt");
                typeModification.addError(erreur);
                logger.error("Exception : ",e);   
                throw new RuntimeException(e);  
            }
            
        return (typeModification);
    }
    
    public void genCroText (ValueObject vo){
    
    }
    
    public String getNumeroTache (ValueObject vo) {
     return  Constants.CODE_RESSOURCE_GENERALE;
    }
}
