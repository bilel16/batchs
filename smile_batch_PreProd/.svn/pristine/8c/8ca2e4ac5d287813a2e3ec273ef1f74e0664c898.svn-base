package com.bna.smile.model.domainecontratcompte.procuration.commande;

import com.bna.commun.util.ContextHandler;
import com.bna.smile.model.domainecommun.model.PersonneCpt;
import com.bna.smile.model.domainecontratcompte.procuration.service.ProcurationService;
import com.oxia.fwk.context.Context;
import com.oxia.fwk.core.ICommande;
import com.oxia.fwk.core.IValueObject;


public class GetContratByDossJurCmd implements ICommande{
    public GetContratByDossJurCmd() {
    }
    
    /**
         * Methode execute
         * @param vo Objet : PersonneStrc
         * @return   Objet : PersonneCpt
         */
        public IValueObject execute(IValueObject vo){
           Context context = ContextHandler.getContext();
            
           ProcurationService procurationService = (ProcurationService)context.getBean("procurationService");
            PersonneCpt personneCpt = (PersonneCpt)procurationService.getContratByDosJur(vo);
           return(personneCpt);
            
        }
}
