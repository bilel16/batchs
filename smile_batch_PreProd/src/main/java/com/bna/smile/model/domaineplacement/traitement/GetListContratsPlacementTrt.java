package com.bna.smile.model.domaineplacement.traitement;
import com.bna.smile.model.constant.Constants;
import com.bna.commun.model.ContratPlacement;
import com.bna.commun.traitements.Traitement;
import com.bna.commun.util.ContextHandler;


import com.bna.smile.model.domainecommun.model.Listes;


import com.bna.smile.model.domaineplacement.model.ParamDemandeDecision;

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

import org.hibernate.criterion.Order;

/**
 * Classe de traitement :permet de fournir la liste des contrats de placement.
 
 * @author El arbi hassine && Jerbi Lamia
 * @since 03/12/2007
 * 
 */
public class GetListContratsPlacementTrt extends Traitement{

    public GetListContratsPlacementTrt() {
    }
    
    public ValueObject perform(IValueObject vo ){
        
        ParamDemandeDecision paramDemandeDecision = (ParamDemandeDecision)vo;
        Listes listeContratPlacement = new Listes();
        
        
        try{        
                Context context = ContextHandler.getContext();
                ISearchEngine searchEngine = (SearchEngine)context.getBean("searchEngine");
                ICriteria criteria = searchEngine.createCriteria();
                IExpression expression = searchEngine.createExpression();
                this.setCroFlag(false);
              

                    if (paramDemandeDecision.getContratPersonne().getContratCptId().getCodStrcStrc() !=null) { 
                             criteria.add(expression.eq("contratCpt.contratCptId.codStrcStrc", 
                                                paramDemandeDecision.getContratPersonne().getContratCptId().getCodStrcStrc()));
                    }
                    
                     if ( paramDemandeDecision.getContratPersonne().getContratCptId().getCodPrdPrd() !=null ) { 
                              criteria.add(expression.eq("contratCpt.contratCptId.codPrdPrd", 
                                                 paramDemandeDecision.getContratPersonne().getContratCptId().getCodPrdPrd()));
                     }
                     
                     if ( paramDemandeDecision.getContratPersonne().getContratCptId().getNumCcptCcpt() !=null) { 
                              criteria.add(expression.eq("contratCpt.contratCptId.numCcptCcpt", 
                                                 paramDemandeDecision.getContratPersonne().getContratCptId().getNumCcptCcpt()));
                     }
                    if (paramDemandeDecision.getCodEtatContrat()!= null ) { 
                         
                         if(paramDemandeDecision.getCodEtatContrat().equals(Constants.ETAT_CPT_PLACEMENT_LIQUIDE)){
                             criteria.add(expression.eq("codEtatCpla", 
                                                      paramDemandeDecision.getCodEtatContrat()));
                             // cas des contrats liquidés, tester sur la date de liquidation
                              if (paramDemandeDecision.getDateDebut()!=null ) {
                                  criteria.add(expression.ge("datLiqCpla", 
                                                             paramDemandeDecision.getDateDebut()));
                              }
                              if (paramDemandeDecision.getDateFin()!=null) {
                                  criteria.add(expression.lt("datLiqCpla", 
                                                             paramDemandeDecision.getDateFin()));
                              } 
                             
                         }else if(paramDemandeDecision.getCodEtatContrat().equals(Constants.ETAT_CONTRAT_PLAC_ECHULIQ)){
                                
                                 criteria.add(expression.eq("codEtatCpla", 
                                                      Constants.ETAT_CPT_PLACEMENT_LIQUIDE));
                               //  tester sur la date d' échéance pour les contrats echus en attentes de restitution, date restitution BC null à tester au niveau action
                                 criteria.add(expression.or(
                                                         expression.eq("produitPlacement.codPrdPlc",Constants.COD_PRD_BC_PLAC),
                                                         expression.eq("produitPlacement.codPrdPlc",Constants.COD_PRD_BCDC_PLAC)));
                                 
                                 if (paramDemandeDecision.getDateDebut()!=null ) {
                                     criteria.add(expression.ge("datEcheCpla", 
                                                                paramDemandeDecision.getDateDebut()));
                                 }
                                 if (paramDemandeDecision.getDateFin()!=null) {
                                     criteria.add(expression.lt("datEcheCpla", 
                                                                paramDemandeDecision.getDateFin()));
                                 }
                             }else if(paramDemandeDecision.getCodEtatContrat().equals(Constants.ETAT_CONTRAT_PLAC_RENOUVELE)) { // en attente de liquidation,
                                 // les nouveaux contrats de placements aprés renouvellement
                                   criteria.add(expression.eq("codEtatCpla", 
                                                   Constants.ETAT_CONTRAT_PLAC_VALIDE));
                                     if (paramDemandeDecision.getDateDebut()!=null ) {
                                         criteria.add(expression.ge("datCreCpla", 
                                                                    paramDemandeDecision.getDateDebut()));
                                     }
                                     if (paramDemandeDecision.getDateFin()!=null) {
                                         criteria.add(expression.lt("datCreCpla", 
                                                                    paramDemandeDecision.getDateFin()));
                                     } 
                                     criteria.add(expression.isNotNull("contratPlacementByNumSqcrCpla.numSeqCpla"));
                                
                                 }else if(paramDemandeDecision.getCodEtatContrat().equals(Constants.ETAT_CONTRAT_PLAC_VALIDE)) { // valide et non encore echu
                                
                                   criteria.add(expression.eq("codEtatCpla", 
                                                   Constants.ETAT_CONTRAT_PLAC_VALIDE));
                                     if (paramDemandeDecision.getDateDebut()!=null ) {
                                         criteria.add(expression.ge("datValCpla", 
                                                                    paramDemandeDecision.getDateDebut()));
                                     }
                                     if (paramDemandeDecision.getDateFin()!=null) {
                                         criteria.add(expression.lt("datValCpla", 
                                                                    paramDemandeDecision.getDateFin()));
                                     } 
                                     if (paramDemandeDecision.getDateFin()!=null ) {
                                        criteria.add(expression.ge("datEcheCpla", 
                                                                   paramDemandeDecision.getDateFin()));
                                     }
                                 }else if(paramDemandeDecision.getCodEtatContrat().equals("AL")) { // en attente de liquidation,
                                 
                                   criteria.add(expression.eq("codEtatCpla", 
                                                   Constants.ETAT_CONTRAT_PLAC_VALIDE));
                                                   
                                  if (paramDemandeDecision.getDateDebut()!=null ) {
                                     criteria.add(expression.ge("datEcheCpla", 
                                                                paramDemandeDecision.getDateDebut()));
                                 }
                                 if (paramDemandeDecision.getDateFin()!=null) {
                                     criteria.add(expression.lt("datEcheCpla", 
                                                                paramDemandeDecision.getDateFin()));
                                 }
                                 }else if(paramDemandeDecision.getCodEtatContrat().equals(Constants.ETAT_CPT_PLC_ATT_RESILIATION)) { // en attente de résiliation
                                 
                                   criteria.add(expression.eq("codEtatCpla", 
                                                   Constants.ETAT_CPT_PLC_ATT_RESILIATION));

                                 }else if(paramDemandeDecision.getCodEtatContrat().equals(Constants.ETAT_CPT_PLC_RESILIE)) { // résilé
                                 
                                   criteria.add(expression.eq("codEtatCpla", 
                                                   Constants.ETAT_CPT_PLC_RESILIE));
                                                   
                                  if (paramDemandeDecision.getDateDebut()!=null ) {
                                     criteria.add(expression.ge("datResiCpla", 
                                                                paramDemandeDecision.getDateDebut()));
                                 }
                                 if (paramDemandeDecision.getDateFin()!=null) {
                                     criteria.add(expression.lt("datResiCpla", 
                                                                paramDemandeDecision.getDateFin()));
                                 }
                                 }else if(paramDemandeDecision.getCodEtatContrat().equals(Constants.ETAT_CPLA_REJETE)) { // rejeté
                                 
                                   criteria.add(expression.eq("codEtatCpla", 
                                                   Constants.ETAT_CPLA_REJETE));
                                                   
                                  if (paramDemandeDecision.getDateDebut()!=null ) {
                                     criteria.add(expression.ge("datRejCpla", 
                                                                paramDemandeDecision.getDateDebut()));
                                 }
                                 if (paramDemandeDecision.getDateFin()!=null) {
                                     criteria.add(expression.lt("datRejCpla", 
                                                                paramDemandeDecision.getDateFin()));
                                 }
                                 }else if(paramDemandeDecision.getCodEtatContrat().equals("LP")) { // Liquidés Partiellement
                                 
                                   criteria.add(expression.eq("codEtatCpla", 
                                                   Constants.ETAT_CONTRAT_PLAC_VALIDE));
                                                   
                                  if (paramDemandeDecision.getDateDebut()!=null ) {
                                     criteria.add(expression.ge("datVldCpla", 
                                                                paramDemandeDecision.getDateDebut()));
                                  }
                                  if (paramDemandeDecision.getDateFin()!=null) {
                                     criteria.add(expression.lt("datVldCpla", 
                                                                paramDemandeDecision.getDateFin()));
                                  }
                                   criteria.add(expression.neProperty("montCapCpla","montActuCpla"));
                                   criteria.add(expression.eq("produitPlacement.codPrdPlc",Constants.COD_PRD_BNAPLC_PLAC));
                                 }else {
                                        criteria.add(expression.eq("codEtatCpla", 
                                                                 paramDemandeDecision.getCodEtatContrat()));
                                        //  tester sur la date de création
                                         if (paramDemandeDecision.getDateDebut()!=null ) {
                                             criteria.add(expression.ge("datCreCpla", 
                                                                        paramDemandeDecision.getDateDebut()));
                                         }
                                         if (paramDemandeDecision.getDateFin()!=null) {
                                             criteria.add(expression.lt("datCreCpla", 
                                                                        paramDemandeDecision.getDateFin()));
                                         }   
                                    }
                    }else {
                        // aucun état choisit,//  tester sur la date de création
                          if (paramDemandeDecision.getDateDebut()!=null ) {
                              criteria.add(expression.ge("datCreCpla", 
                                                         paramDemandeDecision.getDateDebut()));
                         }
                         if (paramDemandeDecision.getDateFin()!=null) {
                              criteria.add(expression.lt("datCreCpla", 
                                                         paramDemandeDecision.getDateFin()));
                         }  
                    }
                    if (paramDemandeDecision.getCodStrcStrc()!= null ) { 
                          criteria.add(expression.in("contratCpt.contratCptId.codStrcStrc", 
                                                   paramDemandeDecision.getCodStrcStrc()));
                    } 

                    
                   
                    if(paramDemandeDecision.getProduitPlacement()!=null) {
                        criteria.add(expression.eq("produitPlacement.codPrdPlc", 
                                                   paramDemandeDecision.getProduitPlacement()));
                    }
                    
                    if(paramDemandeDecision.getNumSeqPers() != null){
                            criteria.add(expression.eq("personne.numSeqPers", 
                                                       paramDemandeDecision.getNumSeqPers()));                                                   
                       }   
                    
                    if (paramDemandeDecision.getNumSeqCpla()!=null) {
                        criteria.add(expression.eq("numSeqCpla", 
                                                   paramDemandeDecision.getNumSeqCpla()));
                    } 
                    
                    if(paramDemandeDecision.getEtatRenouvellement()!= null){
                        // cas de renouvellement
                        criteria.add(expression.in("codEtatCpla", 
                                                    paramDemandeDecision.getCodEtatDemd()));
                        
                        criteria.add(expression.eq("codErenCpla", 
                                                   paramDemandeDecision.getEtatRenouvellement()));
                        
                    }
                    

                    if(paramDemandeDecision.getListeProduit()!= null){
                        criteria.add(expression.in("produitPlacement.codPrdPlc",
                                                   paramDemandeDecision.getListeProduit()));
                    }
                    if(paramDemandeDecision.getCodEtatDemd()!= null){ 
                        criteria.add(expression.in("codEtatCpla", 
                                                paramDemandeDecision.getCodEtatDemd()));
                        
                      } 

                if (paramDemandeDecision.getDateComptable()!=null) {
                    criteria.add(expression.le("datCreCpla", 
                                               paramDemandeDecision.getDateComptable()));
                }
                
                if (paramDemandeDecision.getNumBc()!=null && !paramDemandeDecision.getNumBc().equals("")){
                    criteria.add(expression.eq("numBcCpla", 
                                               Long.valueOf(paramDemandeDecision.getNumBc())));
                }
                
                criteria.addOrder(Order.desc("numSeqCpla"));


                      
                  List l = getSearchEngine().find(ContratPlacement.class, criteria);
                  if(l!=null && l.size()>0)
                      listeContratPlacement.setList(l);
         
            }catch(Exception e) {
                            com.oxia.fwk.core.Error erreur = new com.oxia.fwk.core.Error();
                            StringBuffer text = new StringBuffer("Erreur dans GetListContratsPlacementTrt : ");
                            text.append(e.toString());
                            erreur.setCode("100");
                            erreur.setDescription(text.toString());
                            erreur.setKey("GetListContratsPlacementTrt");
                            logger.error("Exception : ",e);   
                            listeContratPlacement.addError(erreur);
                            throw new RuntimeException(e);
                           
            }
       return(listeContratPlacement);
   }  
   
    public void genCroText(ValueObject vo) {
    
    }  
}
