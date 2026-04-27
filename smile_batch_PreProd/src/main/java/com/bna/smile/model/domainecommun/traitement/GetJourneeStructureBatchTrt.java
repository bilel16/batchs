package com.bna.smile.model.domainecommun.traitement;

import java.util.List;

import com.bna.commun.model.JourneeStructureBatch;
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


public class GetJourneeStructureBatchTrt extends Traitement{
   public GetJourneeStructureBatchTrt() {
    }
    public IValueObject perform(IValueObject vo) {
        Context context = ContextHandler.getContext();
        ISearchEngine searchEngine = (SearchEngine)context.getBean("searchEngine");

        ICriteria   criteria       = searchEngine.createCriteria();
        IExpression expression     = searchEngine.createExpression();
        JourneeStructureBatch journeeStructureBatch = (JourneeStructureBatch)vo;
      
        this.setCroFlag(false);
        try{
        
         //  test sur l'agence et le batch
       
        if(journeeStructureBatch.getJourneeStructureBatchId().getCodStrcStrc() != null){
              criteria.add(expression.eq("journeeStructureBatchId.codStrcStrc",journeeStructureBatch.getJourneeStructureBatchId().getCodStrcStrc()));
          }
        if(journeeStructureBatch.getJourneeStructureBatchId().getDatJrnJrn() != null){
                  criteria.add(expression.eq("journeeStructureBatchId.datJrnJrn",journeeStructureBatch.getJourneeStructureBatchId().getDatJrnJrn()));
              }
        if(journeeStructureBatch.getJourneeStructureBatchId().getCodBatBmet() != null){
                      criteria.add(expression.eq("journeeStructureBatchId.codBatBmet",journeeStructureBatch.getJourneeStructureBatchId().getCodBatBmet()));
                  }
                  
                  
        List list = searchEngine.find(JourneeStructureBatch.class,criteria);
       
            if(list != null && list.size() > 0){
                journeeStructureBatch = (JourneeStructureBatch)list.get(0);
             }else {
                journeeStructureBatch = null;
            }
        return (journeeStructureBatch);
        }catch (Exception e) {
                com.oxia.fwk.core.Error erreur = new com.oxia.fwk.core.Error();
                StringBuffer text = 
                    new StringBuffer("Erreur GetJourneeStructureBatchTrt ");
                text.append(e.toString());
                erreur.setCode("400");
                erreur.setDescription(text.toString());
                erreur.setKey("GetParamBonCaisseTrt");
                journeeStructureBatch.addError(erreur);
                logger.error(" *** Erreur lors de GetJourneeStructureBatchTrt : ", e);
                return (journeeStructureBatch);
            }
    }
    
    
     public void genCroText(ValueObject valueObject) {
     
     }
     public String getNumeroTache(ValueObject vo) {
         return (Constants.CODE_RESSOURCE_GENERALE);
     }
}
