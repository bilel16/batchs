package com.bna.smile.model.domainecommun.traitement;

import com.bna.commun.model.StructureDomaine;
import com.bna.commun.traitements.Traitement;

import com.bna.commun.util.StrHandler;
import com.bna.smile.model.constant.Constants;
import com.bna.smile.model.domainecommun.model.ParamExonerationCltTva;

import com.oxia.fwk.core.IValueObject;
import com.oxia.fwk.core.ValueObject;

public class UpdateExonerationCltTvaTrt extends Traitement{

    public UpdateExonerationCltTvaTrt() {
    }

    protected void genCroText(ValueObject valueObject) {
    }

    protected IValueObject perform(IValueObject iValueObject) {
        ParamExonerationCltTva paramExonerationCltTva = (ParamExonerationCltTva)iValueObject;
        UpdateExonerationTvaTrt updateExonerationTvaTrt = new UpdateExonerationTvaTrt();
        InsertTraceExoTvaTrt insertTraceExoTvaTrt = new InsertTraceExoTvaTrt();
        try{
            updateExonerationTvaTrt.exec(paramExonerationCltTva.getExonerationCltTva());
            insertTraceExoTvaTrt.exec(paramExonerationCltTva.getTraceExoTva());
        } catch (Exception e) {
            com.oxia.fwk.core.Error erreur = new com.oxia.fwk.core.Error();
            StringBuffer text = 
                new StringBuffer("Erreur dans UpdateExonerationCltTvaTrt : ");
            text.append(e.toString());
            erreur.setCode("100");
            erreur.setDescription(text.toString());
            erreur.setKey("updateExonerationTVA");
            paramExonerationCltTva.addError(erreur);
            logger.error("Exception : ",e);   
            throw new RuntimeException(e);  
            
        }  
        return (paramExonerationCltTva);
    }
    
    public String getNumeroTache(IValueObject vo) {
        ParamExonerationCltTva paramExonerationCltTva = (ParamExonerationCltTva)vo;
        return(paramExonerationCltTva.getCodeOperation()+
        StrHandler.lpad(paramExonerationCltTva.getCodeTache(),'0',2));
    }
    
    public IValueObject getNumeroDomaine(IValueObject vo){
        StructureDomaine structureDomaine = new StructureDomaine();
        ParamExonerationCltTva paramExonerationCltTva =  (ParamExonerationCltTva) vo;
        structureDomaine.setCodDomDomm(Constants.COD_DOM_CLIENT);
        structureDomaine.setCodStrcStrc(paramExonerationCltTva.getExonerationCltTva().getStructure().getCodStrcStrc());
        return structureDomaine;
    }

}
