package com.bna.smile.model.moyenPayement.traitement;

import java.util.ArrayList;
import java.util.List;

import com.bna.commun.traitements.Traitement;
import com.bna.commun.util.ContextHandler;
import com.bna.smile.model.moyenPayement.dao.VirementDAO;
import com.bna.smile.model.moyenPayement.model.ParamVirement;
import com.bna.smile.model.moyenPayement.model.Virement;
import com.oxia.fwk.context.Context;
import com.oxia.fwk.core.IValueObject;
import com.oxia.fwk.core.ValueObject;

public class GetVirementByRibDateTrt extends Traitement {
    public GetVirementByRibDateTrt() {
    }
    public IValueObject perform(IValueObject vo)  {
        ParamVirement paramVirementVo = (ParamVirement)vo;
        Context context = ContextHandler.getContext();
        VirementDAO virementDAO=(VirementDAO)context.getBean("virementDAO");
        List<Virement> virements = new ArrayList<Virement>();
    
        try {
            virements=virementDAO.getVirementByRIB (paramVirementVo.getCodeStructure(),paramVirementVo.getDateJourneeComptable(),paramVirementVo.getNumeroContratCompte(),paramVirementVo.getCodeProduit());
            paramVirementVo.setListeVirements(virements);
        }catch (Exception e){
        com.oxia.fwk.core.Error erreur = new com.oxia.fwk.core.Error();
        erreur.setCode("Technique");
        erreur.setDescription("GetVirementByRibDateTrt " + e.getMessage());
        paramVirementVo.addError(erreur);
        throw new   RuntimeException(e);
    }
    return (paramVirementVo);
    }
    public void genCroText(ValueObject vo) {

    }
}
