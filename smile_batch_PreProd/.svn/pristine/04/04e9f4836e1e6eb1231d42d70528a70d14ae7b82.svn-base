package com.bna.smile.model.domainecommun.service;
import com.bna.smile.model.domainecommun.traitement.*;

import com.bna.commun.model.ContratCpt;
import com.bna.commun.model.Operation;
import com.bna.commun.vo.PrimitiveVO;
import com.bna.smile.model.domainecommun.traitement.GetOperationTrt;

import com.bna.smile.model.domainecommun.traitement.GetRibTrt;

import com.oxia.fwk.core.IValueObject;
import com.oxia.fwk.core.ValueObject;

public class GetRibService {
    public GetRibService() {
    }
    
    public IValueObject execute(IValueObject vo) throws Exception{
        try {
            
        ContratCpt contrat = (ContratCpt)vo;
        GetRibTrt getRibTrt = new GetRibTrt();
        PrimitiveVO rib = (PrimitiveVO) getRibTrt.perform(contrat);
        return (rib);
        }
        catch (Exception e) {
         
            throw new Exception(e);
        }
    }

}
