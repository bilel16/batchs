package com.bna.smile.model.moyenPayement.traitement;

import java.util.ArrayList;
import java.util.List;

import com.bna.commun.traitements.Traitement;
import com.bna.commun.util.ContextHandler;
import com.bna.smile.model.moyenPayement.dao.AccuseDAO;
import com.bna.smile.model.moyenPayement.model.Accuse;
import com.bna.smile.model.moyenPayement.model.ParamAccuse;
import com.oxia.fwk.context.Context;
import com.oxia.fwk.core.IValueObject;
import com.oxia.fwk.core.ValueObject;

public class GetAccuseByStructureDateTrt extends Traitement{
    public GetAccuseByStructureDateTrt() {
    }
    public IValueObject perform(IValueObject vo)  {
        ParamAccuse paramAccuseVo = (ParamAccuse)vo;
        Context context = ContextHandler.getContext();
        AccuseDAO accuseDAO=(AccuseDAO)context.getBean("accuseDAO");
        List<Accuse> listAccuses= new ArrayList<Accuse>();
        
        try {
         
            listAccuses=accuseDAO.getAccuseByStructure(paramAccuseVo.getCodeStructure(),paramAccuseVo.getDateJourneeComptable());
            paramAccuseVo.setListeAccusee(listAccuses);
            
        }catch (Exception e){
        com.oxia.fwk.core.Error erreur = new com.oxia.fwk.core.Error();
        erreur.setCode("Technique");
        erreur.setDescription("GetAccuseTrt " + e.getMessage());
        paramAccuseVo.addError(erreur);
        throw new   RuntimeException(e);
    }
    return (paramAccuseVo);
    }
    public void genCroText(ValueObject vo) {

    }

}
