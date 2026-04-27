package com.bna.smile.model.domainecaisse.traitement;

import com.bna.commun.model.StructureDomaine;
import com.bna.commun.traitements.Traitement;
import com.bna.smile.model.constant.Constants;
import com.oxia.fwk.core.IValueObject;
import com.oxia.fwk.core.ValueObject;

public class OuvertureCaisseTrt extends Traitement{
    public OuvertureCaisseTrt() {
    }
    
    public IValueObject perform (IValueObject vo ){
      //  ParamContratPlacement paramContratPlacement = (ParamContratPlacement)vo;             
       
        
    try{
        this.setCroFlag(false);
        
    }catch (Exception e) {
                com.oxia.fwk.core.Error erreur=new com.oxia.fwk.core.Error();
                erreur.setCode("Technique");
                erreur.setDescription("OuvertureCaisseTrt  "+e.getMessage());;
              //  paramContratPlacement.getContratPlacement().addError(erreur);
                logger.error("Exception : ",e);   
                throw new   RuntimeException(e);
        } 
        return (vo);
    }
    public void genCroText(ValueObject vo) {
       
    }
    
    public IValueObject getNumeroDomaine(IValueObject vo){
       StructureDomaine structureDomaine = new StructureDomaine();
      /*   ParamContratPlacement paramContratPlacement = (ParamContratPlacement)vo;      
        structureDomaine.setCodDomDomm(Constants.COD_DOM_GUICHET);
        structureDomaine.setCodStrcStrc();*/
        return structureDomaine;
    }

    public String getNumeroTache(IValueObject vo) {
        return(Constants.RESS_OUV_CAISSE);
    }

}
