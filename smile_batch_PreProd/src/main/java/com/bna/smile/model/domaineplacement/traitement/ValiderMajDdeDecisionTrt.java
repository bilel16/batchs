package com.bna.smile.model.domaineplacement.traitement;


import com.bna.commun.model.CertifChqMandPers;
import com.bna.commun.model.CertificationCheques;

import com.bna.commun.model.Commission;
import com.bna.commun.model.ContratCpt;
import com.bna.commun.model.ContratPlacement;
import com.bna.commun.model.DemandeDecision;
import com.bna.commun.model.DetailOperMoyPaiement;
import com.bna.commun.model.MandatPersonne;
import com.bna.commun.model.OperationMoyPay;
import com.bna.commun.model.PersClient;
import com.bna.commun.model.Personne;
import com.bna.commun.model.SeqAgence;
import com.bna.commun.model.Structure;

import com.bna.commun.model.StructureDomaine;
import com.bna.commun.traitements.Traitement;
import com.bna.commun.util.ContextHandler;
import com.bna.smile.model.constant.Constants;



import com.bna.smile.model.domainecommun.service.CRUDservice;
import com.bna.smile.model.domainecommun.traitement.GetCommissionTrt;
import com.bna.smile.model.domainecommun.traitement.GetPersonneTrt;
import com.bna.smile.model.domainecontratcompte.moyensPaiement.model.ParamDemandeChequeCertifie;


import com.bna.smile.model.domaineplacement.model.ParamLiquidation;

import com.oxia.fwk.context.Context;

import com.oxia.fwk.core.ICriteria;
import com.oxia.fwk.core.IExpression;
import com.oxia.fwk.core.ISearchEngine;
import com.oxia.fwk.core.IValueObject;
import com.oxia.fwk.core.ValueObject;

import com.oxia.fwk.searchengine.SearchEngine;

import java.text.SimpleDateFormat;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

/**
 * validation d'une demande de décision.
 * @author El arbi hassine && Jerbi Lamia
 * @param DemandeDecision
 * @return DemandeDecision
 * @since 31/10/2007
 * 
 */
public class ValiderMajDdeDecisionTrt extends Traitement{
    public ValiderMajDdeDecisionTrt() {
    }
        
    public IValueObject perform (IValueObject vo ) {
     
       DemandeDecision demandeDecision  = (DemandeDecision )vo;               
             
       try{ 
            this.setCroFlag(false);                     
            Context context = ContextHandler.getContext();
            CRUDservice crudService = (CRUDservice)context.getBean("crudservice"); 
            ISearchEngine searchEngine = (SearchEngine)context.getBean("searchEngine");
            ICriteria criterePlc = searchEngine.createCriteria();
            IExpression expression = searchEngine.createExpression();
            crudService.update(demandeDecision);
            
            
            if(demandeDecision.getCodNdemDemd().equalsIgnoreCase("R") && demandeDecision.getCodEtatDemd().equalsIgnoreCase("R") ){
                // le cas de renouvellement
                // mettre à jour le champs COD_EREN_CPLA à 0 pour dire que le placement n'est pas renouvlé...
                 ContratPlacement contratPlacement = new ContratPlacement();
                 criterePlc.add(expression.eq("numSeqCpla", 
                                                 demandeDecision.getContratPlacement().getNumSeqCpla()));
                 
                 List listePlacement = searchEngine.find(ContratPlacement.class, criterePlc);

                 if (listePlacement != null && listePlacement.size() > 0) {
                     contratPlacement =(ContratPlacement)listePlacement.get(0);
                     contratPlacement.setCodErenCpla(Long.valueOf("0"));
                     crudService.update(contratPlacement);
                 }
                
            }
            
            }
         catch (Exception e) {
                com.oxia.fwk.core.Error erreur=new com.oxia.fwk.core.Error();
                erreur.setCode("Technique");
                erreur.setDescription("ValiderMajDdeDecisionTrt  "+e.getMessage());;
                demandeDecision.addError(erreur);
                logger.error("Exception : ",e);   
                throw new RuntimeException(e);
        } 
        return (demandeDecision);
    }
    
    public void genCroText(ValueObject vo) {
    
    } 
    public String getNumeroTache(ValueObject vo) {
        return (Constants.CODE_RESSOURCE_GENERALE);
    }
    
    public IValueObject getNumeroDomaine(IValueObject vo){
        StructureDomaine structureDomaine = new StructureDomaine();
        DemandeDecision demandeDecision  = (DemandeDecision )vo;            
        structureDomaine.setCodDomDomm(Constants.COD_DOM_PLACEMENT);
        structureDomaine.setCodStrcStrc(demandeDecision.getContratCpt().getContratCptId().getCodStrcStrc());
        return structureDomaine;
    }
}
