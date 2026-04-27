package com.bna.smile.model.domainecontratcompte.souscriptioncontratcompte.traitement;

import com.bna.commun.model.StructureDomaine;
import com.bna.commun.model.Tache;
import com.bna.commun.model.TacheId;
import com.bna.commun.model.TraceContrat;
import com.bna.commun.traitements.Traitement;
import com.bna.commun.util.ContextHandler;
import com.bna.smile.model.constant.Constants;
import com.bna.smile.model.domainecommun.service.CRUDservice;
import com.bna.smile.model.domainecontratcompte.souscriptioncontratcompte.model.ParamInsertContrat;
import com.oxia.fwk.context.Context;
import com.oxia.fwk.core.IValueObject;
import com.oxia.fwk.core.ValueObject;

/** Fichier: InsertComptePersonnelBnaTrt.java
 * cette classe permet de prendre en charge la creation d'un compte lié
 * @version 1.0.0 du 21/02/2008
 * @Copyright(c) 2008 BNA (www.bna.com.tn)
 * @Classe: InsertCompteLieTrt
 * @package
 * @author :El arbi hassine 
 */
public class InsertComptePersonnelBnaTrt extends Traitement{
   

    public InsertComptePersonnelBnaTrt()  {
    }

    /**
     * Methode execute
     * @param vo Objet : ParamInsertContrat
     * @return   Objet : ContratCpt
     */
    public IValueObject perform(IValueObject vo) {
        Context context = ContextHandler.getContext();
        ParamInsertContrat paramInsertContrat = (ParamInsertContrat)vo;
        
    try{
         if(this.checkClotureJournee()){
            this.setCroFlag(false);   
           CRUDservice crudservice = (CRUDservice)context.getBean("crudservice");
       
           
            crudservice.create(paramInsertContrat.getContratCpt()); 
            
            /* ##################  Insertion dans la table Trace_contrat  #######*/    
             TraceContrat traceContrat = new TraceContrat();
             Tache tache = new Tache();
             TacheId tacheId = new TacheId();
             tacheId.setCodOperOper(Constants.OPER_DEMANDE_SOUSC_COMPTE);
             tacheId.setCodTachTach(Constants.TACHE_DEMANDE_SOUSC_COMPTE);
             tache.setTacheId(tacheId);
             traceContrat.setCodEtatTrc(Constants.COD_ETAT_CPT_ATT);                
             traceContrat.setPersonnel(paramInsertContrat.getPersonnel());
             traceContrat.setContratCpt(paramInsertContrat.getContratCpt());                
             traceContrat.setTache(tache);
             InsertTraceContratTrt insertTraceContratTrt = 
                 new InsertTraceContratTrt();
             TraceContrat traceContratRetour = 
                 (TraceContrat)insertTraceContratTrt.exec(traceContrat);
            
            
            
             /*##################    ################## ################## Fin de l'insertion trace_contrat ################## ##################*/
              
          //Fin  contrôle cloture journee
           }else{
                       com.oxia.fwk.core.Error erreur = new com.oxia.fwk.core.Error();
                       StringBuffer text = new StringBuffer("La journée est déja clôturée...");            
                       erreur.setCode("100");
                       erreur.setDescription(text.toString());
                       erreur.setKey("InsertDemandeCheque");
                       paramInsertContrat.getContratCpt().addError(erreur);        
                   }
        } catch (Exception e) {
                         com.oxia.fwk.core.Error erreur = new com.oxia.fwk.core.Error();
                         StringBuffer text = 
                             new StringBuffer("Erreur dans InsertComptePersonnelBnaTrt : ");
                         text.append(e.toString());
                         erreur.setCode("100");
                         erreur.setDescription(text.toString());
                         erreur.setKey("InsertComptePersonnelBnaTrt");
                         paramInsertContrat.getContratCpt().addError(erreur);
                         logger.error("Erreur au niveau de l'agence <<" + paramInsertContrat.getContratCpt().getContratCptId().getCodStrcStrc() + ">>. Exception : ",e);      
                         throw new RuntimeException(e);
                         
          }   
        return (paramInsertContrat.getContratCpt());
    }

    public void genCroText(ValueObject vo) {    
    
    }
    
    public String getNumeroTache(IValueObject vo) {
      return ("101");    
    }
    
    public IValueObject getNumeroDomaine(IValueObject vo){
        StructureDomaine structureDomaine = new StructureDomaine();
        ParamInsertContrat paramInsertContrat = (ParamInsertContrat)vo;
        structureDomaine.setCodDomDomm(Constants.COD_DOM_CONTRATCOMPTE);
        structureDomaine.setCodStrcStrc(paramInsertContrat.getContratCpt().getContratCptId().getCodStrcStrc());
        return structureDomaine;
    }

}

