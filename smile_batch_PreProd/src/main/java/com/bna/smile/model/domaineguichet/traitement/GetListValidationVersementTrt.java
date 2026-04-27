package com.bna.smile.model.domaineguichet.traitement;

import com.bna.commun.model.MontantMiseDiposition;
import com.bna.commun.model.OperationMoyPay;
import com.bna.commun.model.TacheId;
import com.bna.commun.traitements.Traitement;

import com.bna.commun.util.DateHandler;import com.bna.commun.util.StrHandler;
import com.bna.commun.util.ContextHandler;
import com.bna.smile.model.constant.Constants;
import com.bna.smile.model.domainecommun.service.CRUDservice;
import com.bna.smile.model.domaineguichet.dao.GuichetDAO;

import com.bna.smile.model.domaineguichet.dao.ListVersementVo;

import com.oxia.fwk.context.Context;
import com.oxia.fwk.core.ICriteria;
import com.oxia.fwk.core.IExpression;
import com.oxia.fwk.core.ISearchEngine;
import com.oxia.fwk.core.IValueObject;
import com.oxia.fwk.core.ValueObject;
import com.oxia.fwk.searchengine.SearchEngine;

import java.util.Date;
import java.util.List;

public class GetListValidationVersementTrt {
    public Context context = ContextHandler.getContext();
    public GetListValidationVersementTrt() {
    }
    
    public ValueObject execute(ValueObject vo) {

     ListVersementVo listVersment = (ListVersementVo) vo;
    try{
        /* extraction des versement */
         ISearchEngine searchEngine = (SearchEngine)context.getBean("searchEngine"); 
         ICriteria criteria         = searchEngine.createCriteria();
         IExpression expression     = searchEngine.createExpression();
    
        TacheId tacheId = new TacheId();
         tacheId.setCodOperOper(Long.valueOf(listVersment.getCodeOperation()));
         tacheId.setCodTachTach(Long.valueOf(listVersment.getCodeTache()));
         
         criteria.add(expression.eq("tache.tacheId",tacheId));
         
         criteria.add(expression.between("datOperOmp",listVersment.getDateVersement(),DateHandler.addJour(listVersment.getDateVersement(),1)));
         criteria.add(expression.eq("codEtatOmp",listVersment.getEtatVersement()));
         
         if (!listVersment.getTypeVersement().equals("CR")){
          criteria.add(expression.eq("structureInitiatrice.codStrcStrc",Long.valueOf(listVersment.getStructurInitiatrice())));
         }else{
          criteria.add(expression.eq("structureReceptrice.codStrcStrc",Long.valueOf(listVersment.getStructurReceptrice() )));   
         }
         
        
         List listeDesVersment  = searchEngine.find(OperationMoyPay.class, criteria);
        
         if(listeDesVersment != null && listeDesVersment.size()>0 ){
             listVersment.setListeVersements(listeDesVersment);
         }
          
        
         
         return (listVersment);
         
     }    catch (Exception e) {
              com.oxia.fwk.core.Error erreur = new com.oxia.fwk.core.Error();
              StringBuffer text = 
                  new StringBuffer("Erreur dans GetListValidationVersementTrt : ");
              text.append(e.toString());
              erreur.setCode("400");
              erreur.setDescription(text.toString());
              erreur.setKey("GetListValidationVersementTrt");
              listVersment.addError(erreur);
              return (listVersment);
          }
    }
}
