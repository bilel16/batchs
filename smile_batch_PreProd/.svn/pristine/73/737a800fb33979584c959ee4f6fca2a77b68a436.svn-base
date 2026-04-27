package com.bna.smile.model.domaineplacement.traitement;

import com.bna.commun.model.DemandeDecision;
import com.bna.commun.traitements.Traitement;
import com.bna.smile.model.domainecommun.model.Listes;
import com.bna.smile.model.domaineplacement.model.ParamDemandeDecision;

import com.oxia.fwk.core.ICriteria;
import com.oxia.fwk.core.IExpression;
import com.oxia.fwk.core.IValueObject;
import com.oxia.fwk.core.ValueObject;

import java.util.List;

import org.hibernate.criterion.Order;


/**
 * Classe de traitement :permet de fournir la liste des demandes de décision de placement.
 
 * @author El arbi hassine && Jerbi Lamia
 * @since 02/11/2007
 * 
 */
public class GetListDemandesDecisionTrt extends Traitement{

    public GetListDemandesDecisionTrt() {
    }
    
    public ValueObject perform(IValueObject vo ){
        ParamDemandeDecision paramDemandeDecision = (ParamDemandeDecision)vo;
        Listes listeDemandeDecision = new Listes();
        
        
        try{
        DemandeDecision dem;
        //dem.setcon
        ICriteria   criteria       = getSearchEngine().createCriteria();
        IExpression expression     = getSearchEngine().createExpression();
        this.setCroFlag(false);
                     
                if(paramDemandeDecision.getContratPersonne().getPersonneId() != null){
                    if(paramDemandeDecision.getContratPersonne().getPersonneId().getNumPcePers()!= null && paramDemandeDecision.getContratPersonne().getPersonneId().getCodTpceTpce()!= null ){
                        criteria.add(expression.eq("numNpceDemd", 
                                                   paramDemandeDecision.getContratPersonne().getPersonneId().getNumPcePers()));                                                   
                        criteria.add(expression.eq("typePiece.codTpceTpce", 
                                                   paramDemandeDecision.getContratPersonne().getPersonneId().getCodTpceTpce()));
                    }   
                }
                
                if(paramDemandeDecision.getNumRefdDemd()!=null) {
                    criteria.add(expression.eq("numRefdDemd", 
                                               paramDemandeDecision.getNumRefdDemd()));
                }
                
                
                 if (paramDemandeDecision.getCodEtatDemd()!= null ) { 
                     criteria.add(expression.in("codEtatDemd", 
                                              paramDemandeDecision.getCodEtatDemd()));
               }      
                
                if (paramDemandeDecision.getNatureDemande()!= null ) { 
                      criteria.add(expression.eq("codNdemDemd", 
                                               paramDemandeDecision.getNatureDemande()));
                }
                if (paramDemandeDecision.getTypeRenouvel()!=null) {
                    criteria.add(expression.eq("codTyprDemd", 
                                               paramDemandeDecision.getTypeRenouvel()));
                }   
                if(paramDemandeDecision.getProduitPlacement()!=null) {
                    criteria.add(expression.eq("produitPlacement.codPrdPlc", 
                                               paramDemandeDecision.getProduitPlacement()));
                }
                
               
                if(paramDemandeDecision.getStructureDemande()!=null) {
                    criteria.add(expression.eq("contratCpt.contratCptId.codStrcStrc", 
                                               paramDemandeDecision.getStructureDemande()));
                }
                
                if(paramDemandeDecision.getCodPrdPrd()!=null) {
                    criteria.add(expression.eq("contratCpt.contratCptId.codPrdPrd", 
                                               Long.valueOf(paramDemandeDecision.getCodPrdPrd())));
                }
                
                if(paramDemandeDecision.getNumCcptCcpt()!=null) {
                    criteria.add(expression.eq("contratCpt.contratCptId.numCcptCcpt", 
                                               Long.valueOf(paramDemandeDecision.getNumCcptCcpt())));
                }
                
                if(paramDemandeDecision.getCodStrcStrc() != null)
                  criteria.add(expression.in("contratCpt.contratCptId.codStrcStrc", 
                                           paramDemandeDecision.getCodStrcStrc()));
             
                
                if (paramDemandeDecision.getDateDebut()!=null ) {
                    criteria.add(expression.ge("datCreDemd", 
                                               paramDemandeDecision.getDateDebut()));
                }
                if (paramDemandeDecision.getDateFin()!=null) {
                    criteria.add(expression.le("datCreDemd", 
                                               paramDemandeDecision.getDateFin()));
                }            
              
                if (paramDemandeDecision.getDateComptable()!=null) {
                    criteria.add(expression.le("datValDemd", 
                                               paramDemandeDecision.getDateComptable()));
                } 
                criteria.addOrder(Order.desc("numRefdDemd"));
                
                  List l = getSearchEngine().find(DemandeDecision.class, criteria);
                  if(l!=null && l.size()>0)
                      listeDemandeDecision.setList(l);
         
            }catch(Exception e) {
                            com.oxia.fwk.core.Error erreur = new com.oxia.fwk.core.Error();
                            StringBuffer text = new StringBuffer("Erreur dans GetListDemandesDecisionTrt : ");
                            text.append(e.toString());
                            erreur.setCode("100");
                            erreur.setDescription(text.toString());
                            erreur.setKey("GetListDemandesDecisionTrt");
                            listeDemandeDecision.addError(erreur);
                            logger.error("Exception : ",e);   
                            throw new RuntimeException(e);
                           
            }
       return(listeDemandeDecision);
   }  
   
    public void genCroText(ValueObject vo) {
    
    }  
}
