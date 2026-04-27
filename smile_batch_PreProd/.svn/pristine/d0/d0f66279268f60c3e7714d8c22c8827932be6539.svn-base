package com.bna.smile.model.domaineplacement.traitement;

import com.bna.commun.model.AvancRembLiquid;
import com.bna.commun.model.StructureDomaine;
import com.bna.commun.traitements.Traitement;
import com.bna.commun.util.ContextHandler;
import com.bna.smile.model.constant.Constants;
import com.bna.smile.model.domainecommun.service.CRUDservice;

import com.oxia.fwk.context.Context;
import com.oxia.fwk.core.IValueObject;
import com.oxia.fwk.core.ValueObject;

public class UpdateAvanceRembLiquTrt extends Traitement{
    public UpdateAvanceRembLiquTrt() {
    }
    

    /**
     * MAJ d'une avance sur capital .
     * @param AvancRembLiquid
     * @return AvancRembLiquid
     * 
     */

    public IValueObject perform (IValueObject vo ) {     
     
    Context context = ContextHandler.getContext();
    AvancRembLiquid avancRembLiquid = (AvancRembLiquid)vo;             
             
    try{ 
    ///------------------------  Mise à jour de la table avanc_remb_liq  -------------------------------
        CRUDservice crudService = (CRUDservice)context.getBean("crudservice"); 
            
        if(avancRembLiquid!=null){
            crudService.update(avancRembLiquid);  
        }
            
    }
    catch (Exception e) {
                com.oxia.fwk.core.Error erreur=new com.oxia.fwk.core.Error();
                erreur.setCode("Technique");
                erreur.setDescription("UpdateAvanceRembLiquTrt  "+e.getMessage());
                avancRembLiquid.addError(erreur);

                throw new RuntimeException(e);
        } 
        return (avancRembLiquid);
    }
    
    public void genCroText(ValueObject vo) {
            
    }   

    public IValueObject getNumeroDomaine(IValueObject vo){
        StructureDomaine structureDomaine = new StructureDomaine();
        AvancRembLiquid avancRembLiquid = (AvancRembLiquid)vo;             
        structureDomaine.setCodDomDomm(Constants.COD_DOM_PLACEMENT);
        structureDomaine.setCodStrcStrc(avancRembLiquid.getContratPlacement().getContratCpt().getStructure().getCodStrcStrc());
        return structureDomaine;
    }
    
}
