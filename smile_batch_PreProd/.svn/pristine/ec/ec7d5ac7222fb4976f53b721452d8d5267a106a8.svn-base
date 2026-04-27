package com.bna.smile.model.moyenPayement.traitement;

import java.util.ArrayList;
import java.util.List;

import com.bna.commun.traitements.Traitement;
import com.bna.commun.util.ContextHandler;
import com.bna.smile.model.moyenPayement.dao.PrelevementDAO;
import com.bna.smile.model.moyenPayement.model.ParamPrelevement;
import com.bna.smile.model.moyenPayement.model.Prelevement;
import com.oxia.fwk.context.Context;
import com.oxia.fwk.core.IValueObject;
import com.oxia.fwk.core.ValueObject;

public class GetPrelevementByStructureDateTrt extends Traitement {
    public GetPrelevementByStructureDateTrt() {
    }
    public IValueObject perform(IValueObject vo)  {
        ParamPrelevement paramPrelevementVo = (ParamPrelevement)vo;
        Context context = ContextHandler.getContext();
        PrelevementDAO prelevementDAO=(PrelevementDAO)context.getBean("prelevementDAO");
        List<Prelevement> prelevements = new ArrayList<Prelevement>();
    
        try {
            prelevements=prelevementDAO.getPrelevementByStructure(paramPrelevementVo.getCodeStructure(),paramPrelevementVo.getDateJourneeComptable());
          
            paramPrelevementVo.setListePrelevements(prelevements);
        }catch (Exception e){
        com.oxia.fwk.core.Error erreur = new com.oxia.fwk.core.Error();
        erreur.setCode("Technique");
        erreur.setDescription("GetPrelevementByStructureDateTrt " + e.getMessage());
        paramPrelevementVo.addError(erreur);
        throw new   RuntimeException(e);
    }
    return (paramPrelevementVo);
    }
    public void genCroText(ValueObject vo) {

    }
}
