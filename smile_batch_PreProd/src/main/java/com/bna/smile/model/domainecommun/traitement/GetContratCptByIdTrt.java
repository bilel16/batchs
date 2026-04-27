package com.bna.smile.model.domainecommun.traitement;

import com.bna.commun.model.ContratCpt;
import com.bna.commun.traitements.Traitement;
import com.bna.commun.util.ContextHandler;

import com.bna.smile.model.constant.Constants;

import com.oxia.fwk.context.Context;
import com.oxia.fwk.core.ISearchEngine;
import com.oxia.fwk.core.IValueObject;
import com.oxia.fwk.core.ValueObject;
import com.oxia.fwk.searchengine.SearchEngine;

/**
 * Classe de traitement permet de rechercher un contrat par sa clé
 * @author Mdimagh Lassaad 
 * @version 1.0.0 16/08/2007
 */
public class GetContratCptByIdTrt  extends Traitement {
    public GetContratCptByIdTrt() {
    
      
    }
    
    /**
     * methode permettant la recherche d'uun contrat
     * @param vo :Objet : ContratCpt
     * @return   :Objet : ContratCpt
     */
    public    IValueObject perform (IValueObject vo) {
        ContratCpt contratCpt = (ContratCpt) vo;
       
        ISearchEngine searchEngine = (SearchEngine)Context.getInstance().getSpringContext().getBean("searchEngine"); 
    try{    
        if (contratCpt.getContratCptId()!=null){
            
            contratCpt = (ContratCpt)  searchEngine.get(ContratCpt.class,contratCpt.getContratCptId());
        
        }        
       
    }catch(Exception e){
        com.oxia.fwk.core.Error erreur = new com.oxia.fwk.core.Error();
        StringBuffer text = 
            new StringBuffer("Erreur dans GetContratCptByIdTrt : ");
        text.append(e.toString());
        erreur.setCode("200");
        erreur.setDescription(text.toString());
        contratCpt.addError(erreur); 
        logger.error("Exception : ",e); 
        throw new RuntimeException(e);  

    }
        return(contratCpt);
    }
    
    public void genCroText(ValueObject vo) {
    
    }    
    
    public String getNumeroTache  (ValueObject vo) {
     return  Constants.CODE_RESSOURCE_GENERALE;
    }
    
}
