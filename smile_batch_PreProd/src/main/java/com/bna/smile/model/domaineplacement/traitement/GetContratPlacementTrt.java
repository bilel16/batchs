package com.bna.smile.model.domaineplacement.traitement;


import com.bna.commun.model.CertifChqMandPers;
import com.bna.commun.model.CertificationCheques;

import com.bna.commun.model.Commission;
import com.bna.commun.model.ContratCpt;
import com.bna.commun.model.ContratPlacement;
import com.bna.commun.model.DetailOperMoyPaiement;
import com.bna.commun.model.MandatPersonne;
import com.bna.commun.model.OperationMoyPay;
import com.bna.commun.model.PersClient;
import com.bna.commun.model.Personne;
import com.bna.commun.model.SeqAgence;
import com.bna.commun.model.Structure;

import com.bna.commun.traitements.Traitement;
import com.bna.commun.util.DateHandler;import com.bna.commun.util.StrHandler;
import com.bna.commun.util.DateHandler;import com.bna.commun.util.StrHandler;
import com.bna.commun.util.ContextHandler;
import com.bna.smile.model.constant.Constants;



import com.bna.commun.vo.PrimitiveVO;
import com.bna.smile.model.domainecommun.service.CRUDservice;
import com.bna.smile.model.domainecommun.traitement.GetCommissionTrt;
import com.bna.commun.traitements.GetNumSequenceAgenceTrt;


import com.bna.smile.model.domainecommun.traitement.GetPersonneTrt;
import com.bna.smile.model.domainecontratcompte.moyensPaiement.model.ParamDemandeChequeCertifie;


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
 * recherche d'un contrat de Placement.
 * @author El arbi hassine && Jerbi Lamia
 * @param ContratPlacement
 * @return ContratPlacement
 * @since 30/10/2007
 * 
 */
public class GetContratPlacementTrt extends Traitement{
    public GetContratPlacementTrt() {
    }
   

     
    public IValueObject perform (IValueObject vo ){
     
        Context context = ContextHandler.getContext();
        ISearchEngine searchEngine = (SearchEngine)context.getBean("searchEngine");
        ICriteria critereCptPlacement = searchEngine.createCriteria();
        IExpression expression = searchEngine.createExpression();

        ContratPlacement  contratPlacement  = (ContratPlacement)vo;
        ContratPlacement  contratPlacementTrouve  = new ContratPlacement();               
       
       
       try{
         this.setCroFlag(false);
            if (contratPlacement.getNumSeqCpla() != null) {

                critereCptPlacement.add(expression.eq("numSeqCpla", 
                                                contratPlacement.getNumSeqCpla()));
                
                contratPlacementTrouve = (ContratPlacement)searchEngine.get(ContratPlacement.class,contratPlacement.getNumSeqCpla());

            }
            return (contratPlacementTrouve); 
           
        } catch (Exception e) {
                com.oxia.fwk.core.Error erreur=new com.oxia.fwk.core.Error();
                erreur.setCode("Technique");
                erreur.setDescription("GetContratPlacementTrt "+e.getMessage());;
                contratPlacement.addError(erreur);
                logger.error("Exception : ",e);   
                throw new RuntimeException(e);
                
        }   
    }
    
    public void genCroText(ValueObject vo) {
    
    }  
}
