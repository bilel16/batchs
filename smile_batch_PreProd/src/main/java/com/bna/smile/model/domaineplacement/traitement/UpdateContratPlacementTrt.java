package com.bna.smile.model.domaineplacement.traitement;

import com.bna.commun.model.ContratPlacement;
import com.bna.commun.model.DemandeDecision;
import com.bna.commun.model.StructureDomaine;
import com.bna.commun.traitements.Traitement;

import com.bna.commun.util.ContextHandler;
import com.bna.smile.model.constant.Constants;
import com.bna.smile.model.domainecommun.service.CRUDservice;

import com.oxia.fwk.context.Context;
import com.oxia.fwk.core.IValueObject;
import com.oxia.fwk.core.ValueObject;

/**
 * mise à jour d'un contrat de placement.
 * @author Jerbi Lamia
 * @param ContratPlacement
 * @return ContratPlacement
 * 
 */
public class UpdateContratPlacementTrt extends Traitement{

    public UpdateContratPlacementTrt() {
    }
    
    public IValueObject perform (IValueObject vo ) {
     
       ContratPlacement contratPlacement  = (ContratPlacement)vo;               
             
       try{ 
            this.setCroFlag(false);   
              Context context = ContextHandler.getContext();
              CRUDservice crudService = (CRUDservice)context.getBean("crudservice"); 
              crudService.update(contratPlacement);                
          }
         catch (Exception e) {
                com.oxia.fwk.core.Error erreur=new com.oxia.fwk.core.Error();
                erreur.setCode("Technique");
                erreur.setDescription("UpdateContratPlacementTrt  "+e.getMessage());;
                contratPlacement.addError(erreur);
                logger.error("Exception : ",e);   
                throw new RuntimeException(e);
        } 
        return (contratPlacement);
    }
    public void genCroText(ValueObject vo) {
    
    }  
    public String getNumeroTache(ValueObject vo) {
        return (Constants.CODE_RESSOURCE_GENERALE);
    }
    public IValueObject getNumeroDomaine(IValueObject vo){
        StructureDomaine structureDomaine = new StructureDomaine();
        ContratPlacement contratPlacement  = (ContratPlacement)vo;                 
        structureDomaine.setCodDomDomm(Constants.COD_DOM_PLACEMENT);
        structureDomaine.setCodStrcStrc(contratPlacement.getContratCpt().getContratCptId().getCodStrcStrc());
        return structureDomaine;
    }
}
