package com.bna.smile.model.domaineguichet.traitement;

import com.bna.commun.model.MontantMiseDiposition;
import com.bna.commun.traitements.Traitement;

import com.bna.commun.util.ContextHandler;

import com.bna.commun.vo.PrimitiveVO;

import com.oxia.fwk.context.Context;
import com.oxia.fwk.core.ICriteria;
import com.oxia.fwk.core.IExpression;
import com.oxia.fwk.core.ISearchEngine;
import com.oxia.fwk.core.IValueObject;
import com.oxia.fwk.core.ValueObject;
import com.oxia.fwk.searchengine.SearchEngine;

import java.util.ArrayList;
import java.util.List;

public class GetMiseAdispositionByPrimaryKeyTrt extends Traitement {
    public Context context = ContextHandler.getContext();

    public GetMiseAdispositionByPrimaryKeyTrt() {
    }
    
    /**
     * Methode permet la recherche d'une mise à disposition par son primaryKey
     * @param vo : 
     * @return   : MontantMiseDiposition : montant mise à disposition
     * @author Mdimagh Med Lassaad 
     * @since : 26/11/2007
     */

     public IValueObject perform(IValueObject vo) { 
        MontantMiseDiposition montantMiseDiposition = (MontantMiseDiposition)vo;
        MontantMiseDiposition montantMiseDipositionRetour;
         
        try{
             ISearchEngine searchEngine = (SearchEngine)context.getBean("searchEngine"); 
             
             ICriteria criteria = searchEngine.createCriteria();
             IExpression expression = searchEngine.createExpression();
             
             montantMiseDipositionRetour = (MontantMiseDiposition) searchEngine.get(MontantMiseDiposition.class,montantMiseDiposition.getMontantMiseDipositionId());
             /*
             criteria.add(expression.eq("montantMiseDipositionId.codTypeMmad", 
                                             montantMiseDiposition.getMontantMiseDipositionId().getCodTypeMmad()));
                                             
             criteria.add(expression.eq("montantMiseDipositionId.numMmadMmad", 
                                             montantMiseDiposition.getMontantMiseDipositionId().getNumMmadMmad()));
                                             
             List   listMontant  =searchEngine.find(MontantMiseDiposition.class,criteria);
             
             if (listMontant.size()>0){
                 montantMiseDipositionRetour = listMontant.get(0);
             }*/
             return (montantMiseDipositionRetour);
             
         }catch(Exception e) {
             com.oxia.fwk.core.Error erreur = new com.oxia.fwk.core.Error();
             StringBuffer text = 
                 new StringBuffer("Erreur dans GetMiseAdispositionByPrimaryKeyTrt : ");
             text.append(e.toString());
             erreur.setCode("500");
             erreur.setDescription(text.toString());
             erreur.setKey("GetMiseAdispositionByPrimaryKeyTrt");
             montantMiseDiposition.addError(erreur);
             return (montantMiseDiposition);
         }
        
     }
     
     
     public void genCroText(ValueObject vo) {
         
     }
}
