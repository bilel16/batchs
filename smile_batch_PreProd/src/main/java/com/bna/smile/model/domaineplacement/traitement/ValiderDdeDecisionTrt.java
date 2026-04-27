package com.bna.smile.model.domaineplacement.traitement;



import com.bna.commun.model.DemandeDecision;

import com.bna.commun.model.SeqAgence;
import com.bna.commun.model.SeqAgenceId;
import com.bna.commun.model.StructureDomaine;
import com.bna.commun.traitements.Traitement;
import com.bna.smile.model.domainecommun.service.CRUDservice;



import com.bna.commun.util.ContextHandler;


import com.bna.commun.util.StrHandler;
import com.bna.smile.model.constant.Constants;
import com.bna.smile.model.domaineplacement.dao.PlacementDAO;

import com.bna.smile.model.domaineplacement.model.ParamLiquidation;

import com.oxia.fwk.context.Context;


import com.oxia.fwk.core.ISearchEngine;
import com.oxia.fwk.core.IValueObject;
import com.oxia.fwk.core.ValueObject;

import java.util.Date;

/**
 * validation d'une demande de décision.
 * @author El arbi hassine && Jerbi Lamia
 * @param DemandeDecision
 * @return DemandeDecision
 * @since 31/10/2007
 * 
 */
public class ValiderDdeDecisionTrt extends Traitement{
    public ValiderDdeDecisionTrt() {
    }
        
    public IValueObject perform (IValueObject vo ) {
     
       DemandeDecision demandeDecision  = (DemandeDecision )vo;               
             
       try{ 
            this.setCroFlag(false);  
            demandeDecision.setNumRefdDemd(getNumDemandeDecision(demandeDecision));
            getCrudservice().create(demandeDecision);           
              
            }
         catch (Exception e) {
                com.oxia.fwk.core.Error erreur=new com.oxia.fwk.core.Error();
                erreur.setCode("Technique");
                erreur.setDescription("ValiderDdeDecisionTrt  "+e.getMessage());;
                demandeDecision.addError(erreur);
                logger.error("Exception : ",e);   
                throw new RuntimeException(e);
        } 
        return (demandeDecision);
    }
    
   
    public Long getNumDemandeDecision(DemandeDecision demandeDecision ) {

        Context context = ContextHandler.getContext();
        PlacementDAO plcDao= (PlacementDAO)context.getBean("placementDAO");
        
        String strc = "";
            if(demandeDecision.getContratCpt() != null)
              strc =  StrHandler.lpad(demandeDecision.getContratCpt().getContratCptId().getCodStrcStrc().toString(),'0', 3);
        
        String d = "" + (new Date().getYear() + 1900);
        String m = StrHandler.lpad(plcDao.getSequenceDemandeDecision().toString(), 
                            '0', 8);

        String numDem = (strc + d + m);
        return (Long.valueOf(numDem));
    }
    
   
    public void genCroText(ValueObject vo) {
    
    }  
    
    
    public IValueObject getNumeroDomaine(IValueObject vo){
        StructureDomaine structureDomaine = new StructureDomaine();
        DemandeDecision demandeDecision  = (DemandeDecision )vo;                  
        structureDomaine.setCodDomDomm(Constants.COD_DOM_PLACEMENT);
        structureDomaine.setCodStrcStrc(demandeDecision.getContratCpt().getContratCptId().getCodStrcStrc());
        return structureDomaine;
    }
}
