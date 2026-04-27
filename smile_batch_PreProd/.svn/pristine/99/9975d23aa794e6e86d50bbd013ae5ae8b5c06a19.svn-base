package com.bna.smile.model.domainecommun.commande;

import com.bna.commun.model.CodePostal;
import com.bna.commun.model.Segment;
import com.bna.commun.util.ContextHandler;
import com.bna.smile.model.domainecommun.service.NomenclatureService;

import com.oxia.fwk.context.Context;
import com.oxia.fwk.core.ICommande;
import com.oxia.fwk.core.IValueObject;
import com.oxia.fwk.core.ValueObject;

/**
 * Commande permet de recuperer l'objet Segment
 * @author Mdimagh Med
 * @since 30/05/07
 */
public class GetSegmentCmd  implements ICommande {
    public GetSegmentCmd() {
    }
    
    /**
     * executer la recherce de l'objet Segment
     * @param vo :Segment
     * @return vO : Segment
     */
    public

    IValueObject execute(IValueObject vo) {
        Segment segment = (Segment)vo;
        Context context = ContextHandler.getContext();

        NomenclatureService nomenclatureService = 
            (NomenclatureService)context.getBean("nomenclatureService");
        segment = (Segment)nomenclatureService.getSegment(segment);
        return (segment);
    }
}
