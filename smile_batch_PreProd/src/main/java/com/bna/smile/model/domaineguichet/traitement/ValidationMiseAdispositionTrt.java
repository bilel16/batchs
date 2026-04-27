package com.bna.smile.model.domaineguichet.traitement;

import com.bna.commun.model.MontantMiseDiposition;
import com.bna.commun.traitements.Traitement;

import com.bna.commun.util.ContextHandler;

import com.bna.smile.model.constant.Constants;

import com.bna.smile.model.domainecommun.service.CRUDservice;

import com.oxia.fwk.context.Context;

import com.oxia.fwk.core.IExpression;
import com.oxia.fwk.core.ISearchEngine;
import com.oxia.fwk.core.IValueObject;
import com.oxia.fwk.core.ValueObject;
import com.oxia.fwk.searchengine.SearchEngine;

/**
 * Classe pour la validation de la mise à disposition
 * elle met le flag de l'etat à 'V' et met à jours l epersonnel valideur
 * @author Mdimagh Med Lassaad
 * @since 26/11/2007
 */
public class ValidationMiseAdispositionTrt extends Traitement {
    public Context context = ContextHandler.getContext();
    com.oxia.fwk.core.Error erreur = new com.oxia.fwk.core.Error();
    public ValidationMiseAdispositionTrt() {
    }
    
    public IValueObject perform( IValueObject vo){
        
        MontantMiseDiposition montantMiseDiposition =(MontantMiseDiposition) vo;
        ISearchEngine searchEngine = (SearchEngine)context.getBean("searchEngine"); 
        IExpression expression     = searchEngine.createExpression();
        try{
        /* Rechercher de la liste des MAD en cours*/
      
        MontantMiseDiposition montantMiseDipositionBase  = (MontantMiseDiposition) searchEngine.load(MontantMiseDiposition.class,montantMiseDiposition.getMontantMiseDipositionId());
        
        if (montantMiseDipositionBase.getCodEtatMmad() != null && montantMiseDipositionBase.getCodEtatMmad().equalsIgnoreCase(Constants.COD_ETAT_MISE_DISPOSITION_VALIDE)){
            StringBuffer text =  new StringBuffer("Mise à disposition déja validée.");
           
            erreur.setCode("500");
            erreur.setDescription(text.toString());
            erreur.setKey("ValidationMiseAdispositionTrt");
            montantMiseDiposition.addError(erreur);
        
        }else {
        
            CRUDservice crudService = (CRUDservice)context.getBean("crudservice");
            montantMiseDipositionBase.setCodEtatMmad(Constants.COD_ETAT_MISE_DISPOSITION_VALIDE);
            montantMiseDipositionBase.setPersonnelVersement(montantMiseDiposition.getPersonnelVersement());
            crudService.update(montantMiseDipositionBase);
        }
        
        
        }catch(Exception e){
        
            StringBuffer text = 
                new StringBuffer("Erreur dans ValidationMiseAdispositionTrt : ");
            text.append(e.toString());
            erreur.setCode("500");
            erreur.setDescription(text.toString());
            erreur.setKey("GetMontantMADById");
            montantMiseDiposition.addError(erreur);
            
        }
        return vo;
    }
    
    public void genCroText(ValueObject vo){
        
    }
}
