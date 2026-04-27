package com.bna.smile.model.reporting.service;


import com.bna.smile.model.reporting.model.ParamRetraitVo;
import com.bna.smile.model.reporting.traitement.ImprimerRetraitTrt;
import com.oxia.fwk.core.IValueObject;

public class ImprimerRetraitService {
    public ImprimerRetraitService() {
    }
    public IValueObject execute(IValueObject vo) throws Exception{
        try {
            
        ParamRetraitVo paramRetraitVo = (ParamRetraitVo)vo;
        ImprimerRetraitTrt imprimerRetraitTrt = new ImprimerRetraitTrt();
        paramRetraitVo  = (ParamRetraitVo) imprimerRetraitTrt.perform(paramRetraitVo);
        return (paramRetraitVo);
        }
        catch (Exception e) {
         
            throw new Exception(e);
        }
    }
}
