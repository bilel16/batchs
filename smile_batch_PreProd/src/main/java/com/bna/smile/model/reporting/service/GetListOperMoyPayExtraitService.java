package com.bna.smile.model.reporting.service;

import com.bna.smile.model.reporting.traitement.GetListOperMoyPayExtraitTrt;
import com.oxia.fwk.core.IValueObject;

public class GetListOperMoyPayExtraitService {
    public GetListOperMoyPayExtraitService() {
    }
    
    public IValueObject execute(IValueObject vo)throws Exception {
    
        IValueObject paramMoyPay=vo;
        GetListOperMoyPayExtraitTrt getListOperMoyPayExtraitTrt=new GetListOperMoyPayExtraitTrt();
        paramMoyPay=getListOperMoyPayExtraitTrt.perform(paramMoyPay);
        return(paramMoyPay);
   }
}
