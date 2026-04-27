package com.bna.smile.model.domainecontratcompte.souscriptioncontratcompte.traitement;

import java.util.List;

import com.bna.commun.model.ContratCpt;
import com.bna.commun.model.NatureBlocage;
import com.bna.commun.traitements.Traitement;
import com.bna.smile.model.constant.Constants;
import com.bna.smile.model.domainecommun.model.Listes;
import com.oxia.fwk.core.ICriteria;
import com.oxia.fwk.core.IExpression;
import com.oxia.fwk.core.IValueObject;
import com.oxia.fwk.core.ValueObject;

public class ChargerNatureblocageTrt extends Traitement{
    public ChargerNatureblocageTrt() {
    }
    /**
     * methode execute
     * @param  vo Objet : ContratCpt
     * @return vo Objet : listeMotifEtat
     */
   
    
     public IValueObject perform(IValueObject vo) {
         Listes listes =new Listes();
         try {
             this.setCroFlag(false); 
             //ISearchEngine searchEngine=(ISearchEngine)Context.getInstance().getSpringContext().getBean("searchEngine");
            
             ICriteria criteria =  getSearchEngine().createCriteria();
             IExpression expression =  getSearchEngine().createExpression();
             ContratCpt contratCpt = (ContratCpt)vo;
             
             
             
             List l =  getSearchEngine().findAll(NatureBlocage.class);
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
             erreur.setKey("ChargerNatureBlocage");
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
