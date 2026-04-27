package com.bna.smile.model.domaineplacement.commande;

import com.bna.commun.util.ContextHandler;
import com.bna.commun.vo.PrimitiveVO;
import com.bna.smile.model.domaineplacement.model.ParamDates;
import com.bna.smile.model.domaineplacement.service.PlacementService;
import com.oxia.fwk.context.Context;
import com.oxia.fwk.core.ICommande;
import com.oxia.fwk.core.IValueObject;


/**
 * La moyenne des TMM entre 2 dates
 * selon une periode (M : mesuel,J : journalier )
 * @param vo : ParamDates
 * @return   : PrimitiveVO
 * @autor    : Youssef BOUSSEN 
 * @since    : 21/05/2009
 */

public class GetAvgTMMbetweenDatesCmd implements ICommande{
    public GetAvgTMMbetweenDatesCmd() {
    }
    public IValueObject execute(IValueObject vo) {
        Context context = ContextHandler.getContext();
        ParamDates paramDates = (ParamDates)vo;
        PlacementService placementService = (PlacementService)context.getBean("placementService");
        PrimitiveVO primitiveVO = (PrimitiveVO)placementService.GetAvgTMMbetweenDates(paramDates);
        return primitiveVO;
    }

}
