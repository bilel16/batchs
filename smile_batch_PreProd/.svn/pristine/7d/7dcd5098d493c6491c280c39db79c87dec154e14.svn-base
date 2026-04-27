package com.bna.smile.model.domainecommun.traitement;

import com.bna.commun.model.CoTitulaire;
import com.bna.commun.model.CoTitulaireId;
import com.bna.commun.model.ContratCpt;
import com.bna.commun.model.Mandat;
import com.bna.commun.model.MandatPersonne;
import com.bna.commun.model.Personne;


import com.bna.commun.traitements.Traitement;
import com.bna.commun.util.ContextHandler;
import com.bna.smile.model.constant.Constants;
import com.bna.smile.model.domainecommun.model.ContratCptMandat;
import com.bna.smile.model.domainecommun.model.ContratPersonne;
import com.bna.smile.model.domainecommun.model.Listes;
import com.bna.smile.model.domainecommun.model.MandatPersonneMandat;
import com.bna.smile.model.domainecommun.service.ClientService;
import com.bna.smile.model.domainecommun.service.ContratCompteService;
import com.bna.smile.model.domainecommun.service.PersonneService;
import com.bna.smile.model.domainecontratcompte.procuration.model.MandatRecherche;

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

public class GetMembreCotitulaireTrt extends Traitement{
   
    public GetMembreCotitulaireTrt() {
    }
    /** Méthode qui permet de tester si une personne est membre cootitulaire sur
     * un contrat donné, et retourne cet objet dans le cas ou il existe.
     * @author Ramzi
     * @since  07/05/2007
     * @param VO:PersonneStrc contenant type piece, num piece & IdContratCpt:codStrcStrc,codPrdPrd,numCcptCcpt
     * @return VO:Entité cotitulaire
     */
    public IValueObject perform(IValueObject vo){
        Context context = ContextHandler.getContext();
        CoTitulaire cotit = new CoTitulaire();
        this.setCroFlag(false);
        
      try{  
        ISearchEngine searchEngine = (SearchEngine)context.getBean("searchEngine"); 
        ICriteria criteria         = searchEngine.createCriteria();
        IExpression expression     = searchEngine.createExpression();

        ContratPersonne contratPersonne=(ContratPersonne)vo;
        ContratCompteService contratCompteService = (ContratCompteService)context.getBean("contratCompteService");
        ContratCpt contratCotit=(ContratCpt)contratCompteService.GetDetailContrat(contratPersonne.getContratCptId());
        Long numSeqCli=contratCotit.getClient().getNumSeqPers();
        PersonneService personneService = (PersonneService)context.getBean("personneService");
        Personne personne=(Personne)personneService.getPersonne(contratPersonne.getPersonneId());
        Long numSeqPers=personne.getNumSeqPers();
        /* Rechercher  */
        criteria.add(expression.eq("coTitulaireId.numSeqCli",numSeqCli));  
        criteria.add(expression.eq("coTitulaireId.numSeqPers",numSeqPers));   
        CoTitulaireId cotitId=new CoTitulaireId();
        cotitId.setNumSeqCli(numSeqCli);
        cotitId.setNumSeqPers(numSeqPers);
        cotit=(CoTitulaire)searchEngine.get(CoTitulaire.class,cotitId);
        } catch (Exception e) {
            com.oxia.fwk.core.Error erreur = new com.oxia.fwk.core.Error();
            StringBuffer text = 
                new StringBuffer("Erreur dans GetListContratMandataireTrt : ");
            text.append(e.toString());
            erreur.setCode("100");
            erreur.setDescription(text.toString());
            erreur.setKey("GetListContratMandataireTrt");
            cotit.addError(erreur);
            logger.error("Exception : ",e);   
            throw new RuntimeException(e);  
          
        } 
        
        return cotit;   

    }

   public void genCroText(ValueObject vo) {    
    
    }
    
    public String getNumeroTache(IValueObject vo) {
     return (Constants.CODE_RESSOURCE_GENERALE);      
    }
   
    
}
