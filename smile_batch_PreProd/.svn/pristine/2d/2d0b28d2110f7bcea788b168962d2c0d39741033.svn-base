package com.bna.smile.model.domaineplacement.traitement;

import com.bna.commun.model.AvancRembLiquid;
import com.bna.commun.traitements.Traitement;
import com.bna.smile.model.constant.Constants;
import com.bna.smile.model.domaineplacement.model.ParamLiquidation;

import com.oxia.fwk.core.IValueObject;
import com.oxia.fwk.core.ValueObject;


public class TraitementLiquidationTrt extends Traitement{
    public TraitementLiquidationTrt() {
    }
    
    public IValueObject perform(IValueObject vo) {
        
        ParamLiquidation paramLiquidation = (ParamLiquidation)vo;
        AvancRembLiquid  avancRembLiquid = new AvancRembLiquid();
       
        try{
            // remboursement des avances avant la liquidation anticipée : traitement fournis pas youssef
            LiquidationAvancesPlacementTrt liquidationAvancesPlacementTrt = new LiquidationAvancesPlacementTrt();
            avancRembLiquid = (AvancRembLiquid)liquidationAvancesPlacementTrt.exec(paramLiquidation);

            ValiderLiquidationPlacementTrt validerLiquidationPlacementTrt = new ValiderLiquidationPlacementTrt();
            AvancRembLiquid  avancRembLiquid1 = new AvancRembLiquid();
            avancRembLiquid1 = (AvancRembLiquid) validerLiquidationPlacementTrt.exec(paramLiquidation) ;
            

     return avancRembLiquid1;
        }catch (Exception e) {
            com.oxia.fwk.core.Error erreur = new com.oxia.fwk.core.Error();
            StringBuffer text = new StringBuffer("Erreur dans TraitementLiquidationTrt : ");
            text.append(e.toString());
            erreur.setCode("100");
            erreur.setDescription(text.toString());
            erreur.setKey("TraitementLiquidationTrt");
            logger.error("Exception : ",e);   
            avancRembLiquid.addError(erreur);
            throw new RuntimeException(e);
                
        }
    }

    
    
    public void genCroText(ValueObject vo) {
    
    } 
    public String getNumeroTache(ValueObject vo) {
        return (Constants.CODE_RESSOURCE_GENERALE);    
    }    

}
