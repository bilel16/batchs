package com.bna.smile.model.domainecommun.commande;

import com.bna.commun.model.ContratCpt;
import com.bna.commun.vo.PrimitiveVO;
import com.bna.smile.model.domainecommun.model.Listes;
import com.bna.smile.model.domainecommun.service.GetRibService;
import com.bna.smile.model.domainecommun.service.PersonneService;

import com.oxia.fwk.core.IValueObject;
import com.oxia.fwk.core.ValueObject;

public class GetRibCmd {

    public IValueObject execute(IValueObject vo) throws Exception{
        try
        {
        ContratCpt contrat = (ContratCpt)vo;
        GetRibService getRibService = new GetRibService();
        PrimitiveVO  rib=(PrimitiveVO) getRibService.execute(contrat);
        return (rib);
        }
        catch (Exception e) {
         
            throw new Exception(e);
        }
    }
}
