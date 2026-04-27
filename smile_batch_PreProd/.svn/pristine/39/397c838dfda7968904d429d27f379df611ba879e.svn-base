package com.bna.smile.model.domainecommun.traitement;

import com.bna.commun.model.Pays;
import com.bna.commun.model.TypePiece;
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
 * classe pour la recherche d'un TypePiece
 * @author Mdimagh Med
 * @since 07/06/07
 */
public class GetTypePieceTrt  extends Traitement {
 
    public GetTypePieceTrt() {
    }
    /**
     * methde d'execution de la recherche
     * @param vo : TypePiece
     * @return vo :TypePiece
     */
    public IValueObject perform (IValueObject vo) {


        TypePiece typePiece = (TypePiece)vo;
        ISearchEngine searchEngine=(ISearchEngine)Context.getInstance().getSpringContext().getBean("searchEngine");

        ICriteria criteria = searchEngine.createCriteria();
        IExpression expression = searchEngine.createExpression();

        try {
            criteria.add(expression.eq("codTpceTpce", typePiece.getCodTpceTpce()));

            List ListTypePiece = searchEngine.find(TypePiece.class, criteria);
            /*si le typePiece existe*/
            if (ListTypePiece != null && ListTypePiece.size() > 0) {
                typePiece = (TypePiece)ListTypePiece.get(0);
            }
           

        } catch (Exception e) {
            com.oxia.fwk.core.Error erreur = new com.oxia.fwk.core.Error();
            StringBuffer text = 
                new StringBuffer("Erreur dans GetTypePieceTrt : ");
            text.append(e.toString());
            erreur.setCode("200");
            erreur.setDescription(text.toString());
            erreur.setKey("GetTypePieceTrt");             
            typePiece.addError(erreur);
            logger.error("Exception : ",e);   
            throw new RuntimeException(e);    
            
        }
        return (typePiece);
    }
    
    public void genCroText (ValueObject vo) {
    
    }
    
    public String getNumeroTache (ValueObject vo) {
     return  Constants.CODE_RESSOURCE_GENERALE;
    }
    
}
