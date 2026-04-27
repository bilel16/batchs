package com.bna.smile.model.domainecommun.traitement;

import com.bna.commun.model.Client;
import com.bna.commun.model.ContratCpt;
import com.bna.commun.model.Personne;
import com.bna.commun.traitements.Traitement;
import com.bna.commun.util.ContextHandler;
import com.bna.commun.util.DateHandler;
import com.bna.smile.model.constant.Constants;
import com.bna.smile.model.domainecommun.model.Listes;
import com.bna.smile.model.domainecommun.model.PersonneCpt;
import com.bna.smile.model.domainecommun.model.PersonneRechercheContratVo;
import com.bna.smile.model.domainecommun.model.PersonneStrc;

import com.oxia.fwk.context.Context;
import com.oxia.fwk.core.ICriteria;
import com.oxia.fwk.core.IExpression;
import com.oxia.fwk.core.ISearchEngine;
import com.oxia.fwk.core.IValueObject;
import com.oxia.fwk.core.ValueObject;
import com.oxia.fwk.searchengine.SearchEngine;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

public class GetListContratTrt extends Traitement{
    public GetListContratTrt() {
    }

    /**
     * Fonction qui prend les données d'une personne pour retourner toutes ses
     * contrats (valides & non valide)
     * @param personneStrc : type piece, N° piece
     * @return Listes ContratCpt : ses contrats
     */
    public IValueObject perform(IValueObject vo) {
        
        this.setCroFlag(false); 
        PersonneRechercheContratVo personneRechercheContratVo = (PersonneRechercheContratVo)vo;
        PersonneStrc personneStrc = personneRechercheContratVo.getPersonneStrc();
        Context context = ContextHandler.getContext();
        Listes listes = new Listes();
        try{
        ISearchEngine searchEngine = 
            (SearchEngine)context.getBean("searchEngine");
        ICriteria criteriaCpt = searchEngine.createCriteria();
        IExpression expression = searchEngine.createExpression();
        GetPersonneTrt getPersonneTrt = new GetPersonneTrt();

        
        List listCpt = new ArrayList();
        Client client = new Client();
        Personne personne = new Personne();
           
        if(personneRechercheContratVo.getCodeAgence()!=null){           
           criteriaCpt.add(expression.eq("contratCptId.codStrcStrc", 
                                          new Long(personneRechercheContratVo.getCodeAgence())));
        } 
        
        if (personneRechercheContratVo.getEtatContrat() != null ) {
                criteriaCpt.add(expression.eq("codEtatCcpt", 
                                              personneRechercheContratVo.getEtatContrat()));                          
        }
        
        if(personneStrc!=null){
             personne = (Personne)getPersonneTrt.exec(personneStrc);
    
            if (personne.getNumSeqPers() != null) {
                criteriaCpt.add(expression.eq("client.numSeqPers", 
                                              personne.getNumSeqPers()));
                /*si le code structure est null retourner les contrats dans toutes les agences*/
                if (personneStrc.getCodStrcStrc() != null) {
                    criteriaCpt.add(expression.eq("contratCptId.codStrcStrc", 
                                                  personneStrc.getCodStrcStrc()));
                }              
           }
        
            if (personneRechercheContratVo.getTypePersonne() != null ) {
            
                  criteriaCpt.add(expression.isNull("datNotfCcpt"));
            }
        
           
        }        
            if (personneRechercheContratVo.getDateDebut() != null) {
               
                criteriaCpt.add(expression.ge("datOuvCcpt", 
                                              personneRechercheContratVo.getDateDebut()));
            }
            
            if (personneRechercheContratVo.getDateFin() != null) {
                Date d = DateHandler.addJour(personneRechercheContratVo.getDateFin(),1);
                criteriaCpt.add(expression.lt("datOuvCcpt", 
                                              d));
            }            
           //recherche liste des contrats selon critères
            if(personneStrc!=null && personne.getNumSeqPers() == null )
              listCpt = null;
            else listCpt = searchEngine.find(ContratCpt.class, criteriaCpt);  
           
            listes.setList(listCpt);
           
            
            } catch (Exception e) {
                com.oxia.fwk.core.Error erreur = new com.oxia.fwk.core.Error();
                StringBuffer text = 
                    new StringBuffer("Erreur dans GetListContratTrt : ");
                text.append(e.toString());
                erreur.setCode("100");
                erreur.setDescription(text.toString());
                erreur.setKey("GetListContratTrt");
                listes.addError(erreur);
                logger.error("Exception : ",e);   
                throw new RuntimeException(e);  
              
            } 
            return (listes);
            
        }


    public void genCroText(ValueObject vo) {    
    
    }
    
    public String getNumeroTache(IValueObject vo) {
      return (Constants.CODE_RESSOURCE_GENERALE);    
    }        

}

