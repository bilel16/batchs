package com.bna.smile.model.domainecontratcompte.procuration.commande;

import com.bna.commun.util.ContextHandler;
import com.bna.smile.model.domainecommun.model.ContratPersonne;
import com.bna.smile.model.domainecontratcompte.procuration.model.PouvoirVo;
import com.bna.smile.model.domainecontratcompte.procuration.service.ProcurationService;
import com.oxia.fwk.context.Context;
import com.oxia.fwk.core.ICommande;
import com.oxia.fwk.core.IValueObject;

/**
 * Commande permet de fournir deux listes : une des mandats générals,
 * et une autres des mandats Operations spéciale.
 * @author Mdimagh Mohamed Lassaad
 * @since 07/05/2007    
 * @version Ver 1.
 */
public class GetPouvoirPersonneContratCmd implements ICommande{
    public GetPouvoirPersonneContratCmd() {
    }

    /**
     * methode execute 
     * @param value Object :  ParamMandatOperationVo
     * @return value Object : ListMandatOperationVo
     */
    public IValueObject execute(IValueObject vo) {
        Context context = ContextHandler.getContext();
        ContratPersonne contratPersonne = (ContratPersonne)vo;
        ProcurationService procurationService = 
            (ProcurationService)context.getBean("procurationService");
        PouvoirVo pouvoirVo = 
            (PouvoirVo)procurationService.getPouvoirPersonneContrat(contratPersonne);
        return (pouvoirVo);
    }
}
