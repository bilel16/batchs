package com.bna.smile.model.domainecompensation.gestionrejet.traitement;


import com.bna.commun.model.Cheque;
import com.bna.commun.traitements.Traitement;
import com.bna.commun.util.ContextHandler;
import com.bna.smile.model.constant.Constants;
import com.oxia.fwk.context.Context;
import com.oxia.fwk.core.ISearchEngine;
import com.oxia.fwk.core.IValueObject;
import com.oxia.fwk.core.ValueObject;
import com.oxia.fwk.searchengine.SearchEngine;

public class GetchequeByIdTrt extends Traitement{
	 public GetchequeByIdTrt() {
	    }
	

	 public  IValueObject perform(IValueObject vo) throws Exception {
		 Cheque  cheque = (Cheque)vo;
	        try {
	        	Context context = ContextHandler.getContext();
	            ISearchEngine searchEngine = 
	                (SearchEngine)context.getBean("searchEngine");
	            cheque = (Cheque)
                searchEngine.get(Cheque.class, cheque.getChequeId());
	            
	          
	      
	        } catch (Exception e) {
	            com.oxia.fwk.core.Error erreur = new com.oxia.fwk.core.Error();
	            StringBuffer text = 
	                new StringBuffer("Erreur dans GetchequeByIdTrt : ");
	            text.append(e.toString());
	            erreur.setCode("200");
	            erreur.setDescription(text.toString());
	            cheque.addError(erreur);
	            logger.error("Exception : ",e);   
	            throw new RuntimeException(e);    
	         
	        }
	        return cheque;
	    }
	 public void genCroText(ValueObject vo) {

	    }
	  public String getNumeroTache  (ValueObject vo) {
		     return Constants.CODE_RESSOURCE_GENERALE;
		    }


}

