package com.bna.smile.model.domainecontratcompte.souscriptioncontratcompte.commande;

import com.bna.commun.util.ContextHandler;
import com.bna.commun.vo.PrimitiveVO;
import com.bna.smile.model.domainecontratcompte.souscriptioncontratcompte.model.PersProduit;
import com.bna.smile.model.domainecontratcompte.souscriptioncontratcompte.service.SouscriptionContratCompteService;
import com.oxia.fwk.context.Context;
import com.oxia.fwk.core.ICommande;
import com.oxia.fwk.core.IValueObject;

public class GetNbrProduitByPersCmd implements ICommande {
    public GetNbrProduitByPersCmd() {
    }
    
    /**
     * methode execute
     * @param  vo Objet : PersProduit
     * @return vo Objet : PrimitiveVO
     */
    public IValueObject execute(IValueObject vo) {
        PersProduit persProduit = (PersProduit)vo;
        Context context = ContextHandler.getContext();

        SouscriptionContratCompteService souscriptionContratCompteService = (SouscriptionContratCompteService)context.getBean("souscriptionContratCompteService");
        PrimitiveVO primitiveVO = (PrimitiveVO)souscriptionContratCompteService.GetNbrProduitByPers(persProduit);
        return (primitiveVO);
    
    }
}
