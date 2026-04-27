package com.bna.smile.model.domaineplacement.traitement;

import com.bna.commun.model.DetailsOperationPlacement;
import com.bna.commun.traitements.Traitement;

import com.oxia.fwk.context.Context;
import com.oxia.fwk.core.ICriteria;
import com.oxia.fwk.core.IExpression;
import com.oxia.fwk.core.ISearchEngine;
import com.oxia.fwk.core.IValueObject;
import com.oxia.fwk.core.ValueObject;

import java.util.List;


public class GetDetailsOperationPlacTrt extends Traitement{
    public GetDetailsOperationPlacTrt() {
    }
    
    public IValueObject perform (IValueObject vo ){
        DetailsOperationPlacement detailsOperationPlacement = (DetailsOperationPlacement)vo;
        DetailsOperationPlacement detailsOperationPlacementTrouve = new DetailsOperationPlacement();
        
        try{
            this.setCroFlag(false);
            ISearchEngine searchEngine=(ISearchEngine)Context.getInstance().getSpringContext().getBean("searchEngine");       
            ICriteria   criteriaCcpla  = searchEngine.createCriteria();
            IExpression expression  = searchEngine.createExpression();
                    
            if (detailsOperationPlacement.getContratPlacement() != null) {
                criteriaCcpla.add(expression.eq("contratPlacement.numSeqCpla", 
                                                detailsOperationPlacement.getContratPlacement().getNumSeqCpla()));
            }
            
            List listeDetailsOp = searchEngine.find(DetailsOperationPlacement.class, criteriaCcpla);
            
            if (listeDetailsOp != null && listeDetailsOp.size() > 0) {
                detailsOperationPlacementTrouve = (DetailsOperationPlacement)listeDetailsOp.get(0);
            }

            return (detailsOperationPlacementTrouve); 
            
        } catch (Exception e) {
                com.oxia.fwk.core.Error erreur=new com.oxia.fwk.core.Error();
                erreur.setCode("Technique");
                erreur.setDescription("GetDetailsOperationPlacTrt "+e.getMessage());;
                detailsOperationPlacementTrouve.addError(erreur);
                logger.error("Exception : ",e);   
                throw new RuntimeException(e);
        }   
        
    }
    
    public void genCroText(ValueObject vo) {
    
    }  
}
