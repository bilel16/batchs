package com.bna.smile.model.reporting.commande;

import com.bna.smile.model.reporting.model.ParamRetraitVo;
import com.bna.smile.model.reporting.service.ImprimerRetraitService;
import com.oxia.fwk.core.IValueObject;

public class ImprimerRetraitCmd {
    public ImprimerRetraitCmd() {
    }
    public IValueObject execute(IValueObject vo) throws Exception{
        try
        {
            ParamRetraitVo paramRetraitVo = (ParamRetraitVo)vo;
            ImprimerRetraitService imprimerRetraitService = new ImprimerRetraitService();
            paramRetraitVo=(ParamRetraitVo) imprimerRetraitService.execute(paramRetraitVo);
            return (paramRetraitVo);
        }
        catch (Exception e) {
         
            throw new Exception(e);
        }
    }
}
