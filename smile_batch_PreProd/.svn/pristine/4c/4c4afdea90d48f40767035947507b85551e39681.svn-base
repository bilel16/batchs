package com.bna.smile.model.domainecommun.traitement;

import com.bna.commun.model.Employeur;
import com.bna.commun.model.PieceAnnexe;
import com.bna.commun.traitements.Traitement;

import com.bna.commun.util.ContextHandler;

import com.bna.smile.model.constant.Constants;

import com.oxia.fwk.context.Context;
import com.oxia.fwk.core.ISearchEngine;
import com.oxia.fwk.core.IValueObject;
import com.oxia.fwk.core.ValueObject;
import com.oxia.fwk.searchengine.SearchEngine;

/**
 * classe qui permet le recherche d'un employeur par sa clé primaire
 * @author Mdimagh Med Lassaad
 */
public class GetEmployeurByIdTrt extends Traitement {
        
    public GetEmployeurByIdTrt() {
    }
    
    public IValueObject perform (IValueObject vo ) throws Exception{  
    Employeur employeur = (Employeur) vo;
    
     try{
      
        ISearchEngine searchEngine=(ISearchEngine)Context.getInstance().getSpringContext().getBean("searchEngine");
        employeur =(Employeur) searchEngine.get(Employeur.class,employeur.getCodEmpEmp() );
        
           
     } catch (Exception e){
        com.oxia.fwk.core.Error erreur = new com.oxia.fwk.core.Error();
        StringBuffer text =  new StringBuffer("Erreur dans GetEmployeurByIdTrt : ");
        text.append(e.toString());
        erreur.setCode("metier");
        erreur.setDescription(text.toString());
        erreur.setKey("GetEmployeurByIdTrt");
        employeur.addError(erreur);
        logger.error("Exception : ",e);   
       
     }
        return (employeur);
    
    }
    
    public void genCroText(ValueObject vo) {
    
    }    
    
    public String getNumeroTache  (ValueObject vo) {
      return Constants.CODE_RESSOURCE_GENERALE;
    }
    
}
