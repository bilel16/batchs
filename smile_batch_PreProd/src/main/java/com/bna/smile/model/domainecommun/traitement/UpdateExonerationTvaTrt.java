package com.bna.smile.model.domainecommun.traitement;

import com.bna.commun.model.ExonerationCltTva;
import com.bna.commun.model.JourneeStructureDomaine;
import com.bna.commun.model.JourneeStructureDomaineId;
import com.bna.commun.model.StructureDomaine;
import com.bna.commun.traitements.Traitement;

import com.bna.commun.util.ContextHandler;
import com.bna.smile.model.constant.Constants;
import com.bna.smile.model.domainecommun.service.CRUDservice;

import com.bna.smile.model.domainecontratcompte.procuration.model.ParamInsertMandat;

import com.oxia.fwk.context.Context;
import com.oxia.fwk.core.IValueObject;
import com.oxia.fwk.core.ValueObject;

public class UpdateExonerationTvaTrt extends Traitement{
    public UpdateExonerationTvaTrt() {
    }

    protected void genCroText(ValueObject valueObject) {
    }

    protected IValueObject perform(IValueObject iValueObject) throws Exception{
    
      ExonerationCltTva exonerationCltTva =  (ExonerationCltTva) iValueObject ;
      try{ 
                this.setCroFlag(false);
                Context context = ContextHandler.getContext();
                CRUDservice crudService = (CRUDservice)context.getBean("crudservice"); 
                crudService.update(exonerationCltTva);  

        }   catch (Exception e) {
            com.oxia.fwk.core.Error erreur=new com.oxia.fwk.core.Error();
            erreur.setCode("Technique");
            erreur.setDescription("UpdateExonerationTvaTrt  "+e.getMessage());;
            exonerationCltTva.addError(erreur);
            logger.error("Exception : ",e);   
            throw new Exception(e);
        }
        return exonerationCltTva;
    }
    public IValueObject getNumeroDomaine(IValueObject vo){
        StructureDomaine structureDomaine = new StructureDomaine();
        ExonerationCltTva exonerationCltTva =  (ExonerationCltTva) vo;
        structureDomaine.setCodDomDomm(Constants.COD_DOM_CLIENT);
        structureDomaine.setCodStrcStrc(exonerationCltTva.getStructure().getCodStrcStrc());
        return structureDomaine;
    }
    
    public String getNumeroTache(IValueObject vo) {
      return (Constants.CODE_RESSOURCE_GENERALE);    
    }
    
}
