package com.bna.smile.model.moyenPayement.commande;

import com.bna.commun.util.ContextHandler;
import com.bna.smile.model.moyenPayement.model.ParamAccuse;
import com.bna.smile.model.moyenPayement.traitement.InsertRefConsultationTrt;
import com.oxia.fwk.context.Context;
import com.oxia.fwk.core.IValueObject;

public class InsertRefConsultationCmd {
    public IValueObject execute(IValueObject vo) {
        Context context = ContextHandler.getContext();
        //MoyensPayementService moyensPayementService = (MoyensPayementService)context.getBean("moyensPayementService");
        //ParamAccuse paramAccuse=(ParamAccuse) moyensPayementService.insertRefConsultationService(vo);
        InsertRefConsultationTrt insertRefConsultationTrt=new InsertRefConsultationTrt();
        ParamAccuse paramAccuse=(ParamAccuse) insertRefConsultationTrt.perform(vo);
        return (paramAccuse);
    }
}
