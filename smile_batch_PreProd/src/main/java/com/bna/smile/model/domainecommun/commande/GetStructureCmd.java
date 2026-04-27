package com.bna.smile.model.domainecommun.commande;

import com.bna.commun.util.ContextHandler;
import com.bna.smile.model.domainecommun.service.NomenclatureService;

import com.oxia.fwk.context.Context;
import com.oxia.fwk.core.ICommande;
import com.oxia.fwk.core.IValueObject;

public class GetStructureCmd implements ICommande {
    public GetStructureCmd() {
    }
    
    public IValueObject execute(IValueObject vo) {
        
        Context context = ContextHandler.getContext();

        NomenclatureService nomenclatureService = 
            (NomenclatureService)context.getBean("nomenclatureService");
       
        return (nomenclatureService.getStructure(vo));
    }
    
}
