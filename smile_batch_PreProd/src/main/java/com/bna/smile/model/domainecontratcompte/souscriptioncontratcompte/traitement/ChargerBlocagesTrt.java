package com.bna.smile.model.domainecontratcompte.souscriptioncontratcompte.traitement;

import java.util.List;

import com.bna.commun.model.Blocage;
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

public class ChargerBlocagesTrt extends Traitement{
    public ChargerBlocagesTrt() {
    }
    
    /**
     * methode execute
     * @param  vo Objet : BlocageCriteres
     * @return vo Objet : listeBlocages
     */
   
   
     public IValueObject perform(IValueObject vo) {
         Listes listes =new Listes();
         try {
             this.setCroFlag(false); 
             ISearchEngine searchEngine=(ISearchEngine)Context.getInstance().getSpringContext().getBean("searchEngine");
            
             ICriteria criteria = searchEngine.createCriteria();
             IExpression expression = searchEngine.createExpression();
             BlocageCriteres blocageCriteres = (BlocageCriteres)vo;
             
             if (blocageCriteres.getContratCptId()!=null){
                 criteria.add(expression.eq("contratCpt.contratCptId",blocageCriteres.getContratCptId()));
             }
               
             criteria.add(expression.isNull("datFinBloc"));
           
             
             List l = getSearchEngine().find(Blocage.class, criteria);
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
             erreur.setKey("ChargerBlocage");
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
