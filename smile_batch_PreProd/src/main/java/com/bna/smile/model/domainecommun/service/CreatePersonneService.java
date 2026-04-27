package com.bna.smile.model.domainecommun.service;

import com.oxia.fwk.beans.service.BasicService;
import com.oxia.fwk.core.IValueObject;

public class CreatePersonneService extends BasicService {

    public IValueObject execute(IValueObject IValueObject) {
        
       return super.execute(IValueObject, BasicService.BASIC_CREATE_ACTION);
    }
}

