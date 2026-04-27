package com.bna.smile.model.domainecommun.traitement;


import com.bna.commun.model.StructureDomaine;
import com.bna.commun.traitements.Traitement;
import com.bna.commun.util.StrHandler;
import com.bna.smile.model.constant.Constants;
import com.bna.smile.model.domainecommun.model.ParamExonerationCltTva;
import com.oxia.fwk.core.IValueObject;
import com.oxia.fwk.core.ValueObject;

public class CreatExonerationCltTvaTrt extends Traitement{

   
    public CreatExonerationCltTvaTrt() {
    }
    public IValueObject perform(IValueObject vo) {
        InsertExonerationTvaTrt insertExonerationTvaTrt = new InsertExonerationTvaTrt();
        InsertTraceExoTvaTrt insertTraceExoTvaTrt = new InsertTraceExoTvaTrt();
        ParamExonerationCltTva paramExonerationCltTva = (ParamExonerationCltTva)vo;
        try{
            insertExonerationTvaTrt.exec(paramExonerationCltTva.getExonerationCltTva());
            insertTraceExoTvaTrt.exec(paramExonerationCltTva.getTraceExoTva());
        } catch (Exception e) {
            com.oxia.fwk.core.Error erreur = new com.oxia.fwk.core.Error();
            StringBuffer text = 
                new StringBuffer("Erreur dans CreatExonerationCltTvaTrt : ");
            text.append(e.toString());
            erreur.setCode("100");
            erreur.setDescription(text.toString());
            erreur.setKey("InsertExonerationTVA");
            paramExonerationCltTva.addError(erreur);
            logger.error("Exception : ",e);   
            throw new RuntimeException(e);  
            
        }  
        return (paramExonerationCltTva);
    }

    public void genCroText(ValueObject vo) {    
    
    }
    
    public String getNumeroTache(IValueObject vo) {
        return(Constants.COD_OPER_CRE_ETVA.toString()+
        StrHandler.lpad(Constants.COD_TACH_PEC_ETVA.toString(),'0',2));
    }
    
    public IValueObject getNumeroDomaine(IValueObject vo){
        StructureDomaine structureDomaine = new StructureDomaine();
        ParamExonerationCltTva paramExonerationCltTva =  (ParamExonerationCltTva) vo;
        structureDomaine.setCodDomDomm(Constants.COD_DOM_CLIENT);
        structureDomaine.setCodStrcStrc(paramExonerationCltTva.getExonerationCltTva().getStructure().getCodStrcStrc());
        return structureDomaine;
    }

}
