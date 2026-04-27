package com.bna.smile.model.domainecaisse.traitement;

import java.util.List;

import com.bna.commun.model.CaisseStrc;
import com.bna.commun.traitements.Traitement;
import com.bna.commun.util.ContextHandler;
import com.bna.commun.util.DateHandler;
import com.bna.smile.model.domainecaisse.model.ListeCaisseStructureVo;
import com.oxia.fwk.context.Context;
import com.oxia.fwk.core.ICriteria;
import com.oxia.fwk.core.IExpression;
import com.oxia.fwk.core.ISearchEngine;
import com.oxia.fwk.core.IValueObject;
import com.oxia.fwk.core.ValueObject;
import com.oxia.fwk.searchengine.SearchEngine;

public class GetListJourneeCaisseTrt  extends Traitement {
    public GetListJourneeCaisseTrt() {
    }
    
    public  IValueObject perform(IValueObject vo) throws Exception {
     ListeCaisseStructureVo listeCaisseStructureVo = (ListeCaisseStructureVo)  vo;;
   
     try {
        Context context = ContextHandler.getContext();     
        ISearchEngine searchEngine  = (SearchEngine)context.getBean("searchEngine");
        ICriteria criteria = searchEngine.createCriteria();
        IExpression expression = searchEngine.createExpression();   
        
          
        if (listeCaisseStructureVo.getCodeStructure()!=null){
            criteria.add(expression.eq("journeeCaisseId.codStrcJrn", listeCaisseStructureVo.getCodeStructure()));
        }
            
        if (listeCaisseStructureVo.getDateJournee()!=null){
            criteria.add(expression.between("journeeCaisseId.datJrnJrn",  DateHandler.strToDate( DateHandler.dateToStr(listeCaisseStructureVo.getDateJournee())), DateHandler.addJour(listeCaisseStructureVo.getDateJournee(),1) ));
        }
      
       //-------------------------- Critére sur le satus  ----------------------------------//
       if (listeCaisseStructureVo.getCodeStatus()!= null && !listeCaisseStructureVo.getCodeStatus().equals("") ){
           criteria.add(expression.eq("codStatJrnc",   listeCaisseStructureVo.getCodeStatus()  ));
       }
       
        List listCaisses = searchEngine.find(CaisseStrc.class, criteria);
      
        if (listCaisses != null && listCaisses.size() > 0) {
            listeCaisseStructureVo.setListeCaisseStructure(listCaisses);
        }
        
        
        return listeCaisseStructureVo;
     
    } catch (Exception e) {
        com.oxia.fwk.core.Error erreur = new com.oxia.fwk.core.Error();
        StringBuffer text = new StringBuffer("Erreur dans GetListJourneeCaisseTrt : ");
        text.append(e.toString());
        erreur.setCode("200");
        erreur.setDescription(text.toString());
        erreur.setKey("caisse");
        listeCaisseStructureVo.addError(erreur);
        return (listeCaisseStructureVo);
    }

    }

    public void genCroText(ValueObject vo) {

    }
    
}
