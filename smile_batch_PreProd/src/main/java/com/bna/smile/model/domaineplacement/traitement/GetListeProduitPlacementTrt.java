package com.bna.smile.model.domaineplacement.traitement;

import com.bna.commun.model.Blocage;
import com.bna.commun.model.ContratPlacement;
import com.bna.commun.traitements.Traitement;
import com.bna.smile.model.constant.Constants;
import com.bna.smile.model.domainecommun.model.Listes;
import com.bna.smile.model.domainecontratcompte.souscriptioncontratcompte.model.BlocageCriteres;

import com.oxia.fwk.context.Context;
import com.oxia.fwk.core.ICriteria;
import com.oxia.fwk.core.IExpression;
import com.oxia.fwk.core.ISearchEngine;
import com.oxia.fwk.core.IValueObject;
import com.oxia.fwk.core.ValueObject;

import java.util.List;

public class GetListeProduitPlacementTrt  extends Traitement{
    public GetListeProduitPlacementTrt() {
    }
    public IValueObject perform(IValueObject vo) {
        Listes listes =new Listes();
        try {
            this.setCroFlag(false); 
            ISearchEngine searchEngine=(ISearchEngine)Context.getInstance().getSpringContext().getBean("searchEngine");
           
           
            List l = getSearchEngine().findAll(ContratPlacement.class);
            if (l != null && l.size() > 0) {
                listes.setList(l);
            }
           
           
        } catch (Exception e) {
            com.oxia.fwk.core.Error erreur = new com.oxia.fwk.core.Error();
            StringBuffer text = 
                new StringBuffer("Erreur GetListeProduitPlacementTrt ");
            text.append(e.toString());
            erreur.setCode("200");
            erreur.setDescription(text.toString());
            erreur.setKey("GetListeProduitPlacementTrt");
            listes.addError(erreur);
            logger.error("Exception : ",e);   
            throw new RuntimeException(e);
           
        }
        return (listes);
    }
    public void genCroText(ValueObject vo) {
         
        
       }  
    public String getNumeroTache(ValueObject vo) {
       return (Constants.CODE_RESSOURCE_GENERALE);    
       
       
    }
}
