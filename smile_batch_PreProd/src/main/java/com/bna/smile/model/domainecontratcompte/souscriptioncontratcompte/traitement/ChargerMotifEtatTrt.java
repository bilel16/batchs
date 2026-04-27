package com.bna.smile.model.domainecontratcompte.souscriptioncontratcompte.traitement;


import java.util.List;

import com.bna.commun.model.EtatContrat;
import com.bna.commun.model.MotifEtat;
import com.bna.commun.traitements.Traitement;
import com.bna.smile.model.constant.Constants;
import com.bna.smile.model.domainecommun.model.Listes;
import com.oxia.fwk.context.Context;
import com.oxia.fwk.core.ICriteria;
import com.oxia.fwk.core.IExpression;
import com.oxia.fwk.core.ISearchEngine;
import com.oxia.fwk.core.IValueObject;
import com.oxia.fwk.core.ValueObject;

public class ChargerMotifEtatTrt extends Traitement{
    public ChargerMotifEtatTrt() {
    }
    /**
     * methode execute
     * @param  vo Objet : ContratEtat
     * @return vo Objet : listeMotifEtat
     */
   
    
     public IValueObject perform(IValueObject vo) {
         Listes listes =new Listes();
         try {
             this.setCroFlag(false); 
            ISearchEngine searchEngine=(ISearchEngine)Context.getInstance().getSpringContext().getBean("searchEngine");
            
             ICriteria criteria = searchEngine.createCriteria();
             IExpression expression = searchEngine.createExpression();
             EtatContrat etatContrat = (EtatContrat)vo;
            
             criteria.add(expression.eq("motifEtatId.codEtatEcon", 
                                         etatContrat.getCodEtatEcon() ));
             
             List l = searchEngine.find(MotifEtat.class, criteria);
             if (l != null && l.size() > 0) {
                 listes.setList(l);
             }
            
             return listes;
         } catch (Exception e) {
             com.oxia.fwk.core.Error erreur = new com.oxia.fwk.core.Error();
             StringBuffer text = 
                 new StringBuffer("Erreur lors du blocage du montant ");
             text.append(e.toString());
             erreur.setCode("200");
             erreur.setDescription(text.toString());
             erreur.setKey("ChargerMotifEtat");
             listes.addError(erreur);
             logger.error("Exception : ",e);   
            
             return (listes);
         }
     }
    public void genCroText(ValueObject vo) {
          
         
        }  
    public String getNumeroTache(ValueObject vo) {
        return (Constants.CODE_RESSOURCE_GENERALE);    
        
        
    }
}
