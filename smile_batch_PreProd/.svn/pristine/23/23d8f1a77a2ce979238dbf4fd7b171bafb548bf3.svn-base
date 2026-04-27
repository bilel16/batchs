package com.bna.smile.model.domainecontratcompte.souscriptioncontratcompte.traitement;

import java.util.Date;

import com.bna.commun.model.ContratCpt;
import com.bna.commun.model.ContratCptId;
import com.bna.commun.model.StructureDomaine;
import com.bna.commun.model.Tache;
import com.bna.commun.model.TacheId;
import com.bna.commun.model.TraceContrat;
import com.bna.commun.traitements.Traitement;
import com.bna.commun.util.ContextHandler;
import com.bna.smile.model.constant.Constants;
import com.bna.smile.model.domainecommun.service.CRUDservice;
import com.bna.smile.model.domainecontratcompte.souscriptioncontratcompte.model.ParamCompteLie;
import com.bna.smile.model.domainecontratcompte.souscriptioncontratcompte.service.SouscriptionContratCompteService;
import com.oxia.fwk.context.Context;
import com.oxia.fwk.core.IValueObject;
import com.oxia.fwk.core.ValueObject;

/** Fichier: InsertCompteLieTrt.java
 * cette classe permet de prendre en charge la creation d'un compte lié
 * @version 1.0.0 du 30/04/02007
 * @Copyright(c) 2007 BNA (www.bna.com.tn)
 * @Classe: InsertCompteLieTrt
 * @package
 * @author :El arbi hassine 
 */
public class InsertCompteLieTrt extends Traitement {
    

    public InsertCompteLieTrt() {
    }

    /**
     * Methode execute
     * @param vo Objet : ParamCompteLie
     * @return   Objet : ContratCpt
     */
    public IValueObject perform(IValueObject vo) {

        ParamCompteLie paramCompteLie = (ParamCompteLie)vo;
        Context context = ContextHandler.getContext();
        ContratCpt compteDav = new ContratCpt();
        compteDav = paramCompteLie.getContratCpt();
        ContratCpt compteLie = new ContratCpt();
        ContratCptId contratCptIdCompteLie = new ContratCptId();
    try{
      if(this.checkClotureJournee()){
        this.setCroFlag(false);  
        contratCptIdCompteLie.setCodPrdPrd(paramCompteLie.getCodeProduit());
        contratCptIdCompteLie.setCodStrcStrc(compteDav.getContratCptId().getCodStrcStrc());
        contratCptIdCompteLie.setNumCcptCcpt(compteDav.getContratCptId().getNumCcptCcpt());
        compteLie.setContratCptId(contratCptIdCompteLie);
        compteLie.setClient(compteDav.getClient());
        compteLie.setDevise(compteDav.getDevise());
        compteLie.setAdresseCorresp(compteDav.getAdresseCorresp());
        compteLie.setNumBctClt(compteDav.getNumBctClt());
        
        compteLie.setCodPerCpt(compteDav.getCodPerCpt());
        compteLie.setCodFoncCpt(compteDav.getCodFoncCpt());
        compteLie.setBoolRelvCpt(compteDav.getBoolRelvCpt());
         
        SouscriptionContratCompteService souscriptionContratCompteService = 
            (SouscriptionContratCompteService)context.getBean("souscriptionContratCompteService");
        CRUDservice crudservice = (CRUDservice)context.getBean("crudservice");
        //if (souscriptionContratCompteService.verifProduitLie(compteLie, 
                                                          //   compteDav)) {
            compteLie.setDatOuvCcpt(new Date());
            compteLie.setMontSminCcpt(paramCompteLie.getMontantSoldeMinimum());
            compteLie.setCodEtatCcpt(Constants.COD_ETAT_CPT_ATT);
            compteLie.setContratCpt(compteDav);
            crudservice.create(compteLie);
            
            //mise à jour du solde minimum dans le contrat compte 101 / 185
            compteDav.setMontSminCcpt(paramCompteLie.getMontantSoldeMinimum());
            crudservice.update(compteDav); 
            
            /* ##################  Insertion dans la table Trace_contrat  #######*/    
             TraceContrat traceContrat = new TraceContrat();
             Tache tache = new Tache();
             TacheId tacheId = new TacheId();
             tacheId.setCodOperOper(Constants.OPER_DEMANDE_SOUSC_COMPTE);
             tacheId.setCodTachTach(Constants.TACHE_DEMANDE_SOUSC_COMPTE);
             tache.setTacheId(tacheId);
             traceContrat.setCodEtatTrc(Constants.COD_ETAT_CPT_ATT);                
             traceContrat.setPersonnel(paramCompteLie.getPersonnel());
             traceContrat.setContratCpt(paramCompteLie.getContratCpt());                
             traceContrat.setTache(tache);
             InsertTraceContratTrt insertTraceContratTrt = 
                 new InsertTraceContratTrt();
             TraceContrat traceContratRetour = 
                 (TraceContrat)insertTraceContratTrt.exec(traceContrat);
            
            
            
             /*##################    ################## ################## Fin de l'insertion trace_contrat ################## ##################*/
              
            
       /* } else {
            compteLie = null;
        }*/
         // Fin controle cloture journee 
          }else{
                      com.oxia.fwk.core.Error erreur = new com.oxia.fwk.core.Error();
                      StringBuffer text = new StringBuffer("La journée est déja clôturée...");            
                      erreur.setCode("100");
                      erreur.setDescription(text.toString());
                      erreur.setKey("InsertDemandeCheque");
                      compteLie.addError(erreur);        
                  }
        } catch (Exception e) {
                         com.oxia.fwk.core.Error erreur = new com.oxia.fwk.core.Error();
                         StringBuffer text = 
                             new StringBuffer("Erreur dans InsertCompteLieTrt : ");
                         text.append(e.toString());
                         erreur.setCode("100");
                         erreur.setDescription(text.toString());
                         erreur.setKey("InsertCompteLie");
                         compteLie.addError(erreur);
                        logger.error("Erreur au niveau de l'agence <<" +compteDav.getContratCptId().getCodStrcStrc() + ">>. Exception : ",e);    
                         throw new RuntimeException(e);
          }
          
        return (compteLie);
    }

    public void genCroText(ValueObject vo) {    
    
    }
    
    public String getNumeroTache(IValueObject vo) {
      return ("101");    
    }
    
    public IValueObject getNumeroDomaine(IValueObject vo){
        StructureDomaine structureDomaine = new StructureDomaine();
        ParamCompteLie paramCompteLie = (ParamCompteLie)vo;
        structureDomaine.setCodDomDomm(Constants.COD_DOM_CONTRATCOMPTE);
        structureDomaine.setCodStrcStrc(paramCompteLie.getContratCpt().getContratCptId().getCodStrcStrc());
        return structureDomaine;
    }
}

