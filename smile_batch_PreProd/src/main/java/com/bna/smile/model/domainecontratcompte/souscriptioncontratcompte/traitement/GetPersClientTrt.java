package com.bna.smile.model.domainecontratcompte.souscriptioncontratcompte.traitement;

import java.util.List;

import com.bna.commun.model.PersClient;
import com.bna.commun.traitements.Traitement;
import com.bna.smile.model.constant.Constants;
import com.bna.smile.model.domainecommun.model.PersonneCpt;
import com.oxia.fwk.context.Context;
import com.oxia.fwk.core.ICriteria;
import com.oxia.fwk.core.IExpression;
import com.oxia.fwk.core.ISearchEngine;
import com.oxia.fwk.core.IValueObject;
import com.oxia.fwk.core.ValueObject;

public class GetPersClientTrt extends Traitement{
    public GetPersClientTrt() {
    }

    /**
     * methode permettant la recherche du tuteur ou de l'actionaire  à partir du type pièce et numero pièce 
     * et de la qualite du client
     * @param vo :Objet : PersonneCpt
     * @return   :Objet : Personne
     */
    public

    IValueObject perform(IValueObject vo) {

        PersonneCpt personneCpt = (PersonneCpt)vo;
        
        ISearchEngine searchEngine=(ISearchEngine)Context.getInstance().getSpringContext().getBean("searchEngine");

        ICriteria criteria = searchEngine.createCriteria();
        IExpression expression = searchEngine.createExpression();
        PersClient personneClient = new PersClient();
        this.setCroFlag(false);
     try{
        

        criteria.add(expression.eq("persClientId.numSeqCli", 
                                   personneCpt.getClient().getNumSeqPers()));
        criteria.add(expression.eq("persClientId.codQualQual", 
                                   personneCpt.getCodQualQual()));

        List l = searchEngine.find(PersClient.class, criteria);

        /*si la personne existe*/
        if (l != null && l.size() > 0) {
            personneClient = (PersClient)l.get(0);
        }
        return (personneClient.getPersonne());
      } catch (Exception e) {
               com.oxia.fwk.core.Error erreur = new com.oxia.fwk.core.Error();
               StringBuffer text = 
                   new StringBuffer("Erreur dans GetPersClientTrt : ");
               text.append(e.toString());
               erreur.setCode("100");
               erreur.setDescription(text.toString());
               erreur.setKey("GetPersClient");
               personneClient.getPersonne().addError(erreur);
               logger.error("Exception : ",e);  
               return (personneClient.getPersonne());
      } 
    }
    
    public void genCroText(ValueObject vo) {    
    
    }
    
    public String getNumeroTache(IValueObject vo) {
      return (Constants.CODE_RESSOURCE_GENERALE);        
    }
}
