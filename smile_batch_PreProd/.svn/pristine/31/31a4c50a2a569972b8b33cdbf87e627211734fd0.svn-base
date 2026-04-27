package com.bna.smile.model.domainecommun.traitement;

import com.bna.commun.model.Personne;
import com.bna.commun.traitements.Traitement;
import com.bna.commun.util.ContextHandler;
import com.bna.commun.util.StrHandler;
import com.bna.commun.vo.PrimitiveVO;
import com.bna.smile.model.constant.Constants;

import com.oxia.fwk.context.Context;
import com.oxia.fwk.core.ICriteria;
import com.oxia.fwk.core.IExpression;
import com.oxia.fwk.core.ISearchEngine;
import com.oxia.fwk.core.IValueObject;
import com.oxia.fwk.core.ValueObject;
import com.oxia.fwk.searchengine.SearchEngine;

import java.util.List;

public class VerifierExistancePersonneTrt extends Traitement{
    public VerifierExistancePersonneTrt() {
    }
    

    public IValueObject perform(IValueObject vo)  {
        
        Personne personne = (Personne)vo;
        Context context = ContextHandler.getContext();
        ISearchEngine searchEngine = 
            (SearchEngine)context.getBean("searchEngine");
        ICriteria criteria = searchEngine.createCriteria();
        IExpression expression = searchEngine.createExpression();

        if (personne.getNumSeqPers()!=null){
                personne = (Personne)searchEngine.get(Personne.class,personne.getNumSeqPers());
        }else{
                 criteria.add(expression.eq("typePiece.codTpceTpce", personne.getTypePiece().getCodTpceTpce()));
                 criteria.add(expression.eq("numPcePers", personne.getNumPcePers()));
                 List l = searchEngine.findAll(Personne.class);//, criteria);
                 personne = (Personne)l.get(0) ;
        }
        
        PrimitiveVO primitiveVO = new PrimitiveVO();

        if (personne.getNumPcePers() != null ) {
            primitiveVO.setVBool(true);
        } else {
            primitiveVO.setVBool(false);

        }
        return primitiveVO;
    }
    
    
    public void genCroText(ValueObject vo) {
        
    }
    
    public String getNumeroTache(IValueObject vo) {
        return (Constants.CODE_RESSOURCE_GENERALE);        

    }  

}
