package com.bna.smile.model.domaineplacement.traitement;

import com.bna.commun.model.DemandeDecision;
import com.bna.commun.traitements.Traitement;

import com.oxia.fwk.core.ICriteria;
import com.oxia.fwk.core.IExpression;
import com.oxia.fwk.core.IValueObject;
import com.oxia.fwk.core.ValueObject;

import java.util.List;


/**
 * recherche d'une demande de decision de Placement.
 * @param DemandeDecision
 * @return DemandeDecision
 * @since 05/11/2007
 * 
 */
public class GetDemandeDecisionTrt extends Traitement{
    public GetDemandeDecisionTrt() {
    }
   

     
    public IValueObject perform (IValueObject vo ){
     
       DemandeDecision  demandeDecision  = (DemandeDecision)vo;       
       DemandeDecision  demandeDecisionTrouve  = new DemandeDecision(); 
       ICriteria critereDdeDecision = getSearchEngine().createCriteria();
       IExpression expression = getSearchEngine().createExpression();
       
       try{
         this.setCroFlag(false);
            if (demandeDecision.getNumRefdDemd() != null) {
                critereDdeDecision.add(expression.eq("numRefdDemd", 
                                                demandeDecision.getNumRefdDemd()));
            }
            List listeDdeDecision = getSearchEngine().find(DemandeDecision.class, critereDdeDecision);

            if (listeDdeDecision != null && listeDdeDecision.size() > 0) {
                demandeDecisionTrouve =(DemandeDecision)listeDdeDecision.get(0);
            }

            return (demandeDecisionTrouve); 
           
        } catch (Exception e) {
                com.oxia.fwk.core.Error erreur=new com.oxia.fwk.core.Error();
                erreur.setCode("Technique");
                erreur.setDescription("GetDemandeDecisionTrt "+e.getMessage());;
                demandeDecisionTrouve.addError(erreur);
                logger.error("Exception : ",e);   
                throw new RuntimeException(e);
        }   
    }
    
    public void genCroText(ValueObject vo) {
    
    }  
}
