package com.bna.smile.model.domainecommun.traitement;

import com.bna.commun.model.PersClient;
import com.bna.commun.model.Personne;
import com.bna.commun.traitements.Traitement;
import com.bna.commun.util.ContextHandler;
import com.bna.smile.model.constant.Constants;
import com.bna.smile.model.domainecommun.model.PersonneStrc;
import com.bna.smile.model.domainecommun.model.Tuteur;

import com.oxia.fwk.context.Context;
import com.oxia.fwk.core.ICriteria;
import com.oxia.fwk.core.IExpression;
import com.oxia.fwk.core.ISearchEngine;
import com.oxia.fwk.core.IValueObject;
import com.oxia.fwk.core.ValueObject;
import com.oxia.fwk.searchengine.SearchEngine;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class GetTuteurTrt extends Traitement{
   

    public GetTuteurTrt() {
    }

    /** méthode pour la recherche d'un tuteur et la liste des mineures en charge
     * @param  ValueObject : IdentifiantPersonneVo : l'identifiant de lapersonne
     * @return ValueObject : TuteurVo :La personne tuteur et la liste de ses mineures
     */
    public

    IValueObject perform(IValueObject vo) {

        this.setCroFlag(false);
        PersonneStrc personneStrc = (PersonneStrc)vo;
        Tuteur tuteurVo = new Tuteur();
        Personne personneTuteur = new Personne();       
        ISearchEngine searchEngine=(ISearchEngine)Context.getInstance().getSpringContext().getBean("searchEngine");       
        IExpression expression = searchEngine.createExpression();
        GetPersonneTrt getPersonneTrt = new GetPersonneTrt();
        personneTuteur = (Personne)getPersonneTrt.exec(personneStrc);
   try{
        if (personneTuteur.getNumSeqPers() != null) {

            tuteurVo.setPersonneTuteur(personneTuteur);
            ICriteria critereMineur = searchEngine.createCriteria();
            /* cherchons maintenant les mineures en charge */
            critereMineur.add(expression.eq("persClientId.numSeqPers", 
                                            personneTuteur.getNumSeqPers()));
            critereMineur.add(expression.eq("persClientId.codQualQual", 
                                            new Long(4)));

            List listeDesPersonnesClient = 
                searchEngine.find(PersClient.class, critereMineur);

            if (listeDesPersonnesClient != null && 
                listeDesPersonnesClient.size() > 0) {

                Iterator iterateur = listeDesPersonnesClient.iterator();
                List listeDesPersonneMineures = new ArrayList();

                while (iterateur.hasNext()) {
                    PersClient personneClient = (PersClient)iterateur.next();
                    listeDesPersonneMineures.add(personneClient.getClient().getPersonne());
                }
                tuteurVo.setListeDesMineures(listeDesPersonneMineures);
                tuteurVo.setIsTuteur(true);

            }

        }
     
        
        } catch (Exception e) {
            com.oxia.fwk.core.Error erreur = new com.oxia.fwk.core.Error();
            StringBuffer text = 
                new StringBuffer("Erreur dans GetTuteurTrt : ");
            text.append(e.toString());
            erreur.setCode("100");
            erreur.setDescription(text.toString());
            erreur.setKey("GetTuteur");
            tuteurVo.addError(erreur);
            logger.error("Exception : ",e);   
            throw new RuntimeException(e);  
          
        }                
            return (tuteurVo);
        }

    public void genCroText(ValueObject vo) {    
    
    }
    
    public String getNumeroTache(IValueObject vo) {
      return (Constants.CODE_RESSOURCE_GENERALE);      
    }

}
