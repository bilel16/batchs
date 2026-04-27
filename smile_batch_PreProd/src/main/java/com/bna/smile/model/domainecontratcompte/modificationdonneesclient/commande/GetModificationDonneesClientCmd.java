package com.bna.smile.model.domainecontratcompte.modificationdonneesclient.commande;

import com.bna.commun.util.ContextHandler;
import com.bna.smile.model.domainecontratcompte.modificationdonneesclient.model.ParamRechercheModificationDonneesVo;
import com.bna.smile.model.domainecontratcompte.modificationdonneesclient.service.ModificationDonneesService;
import com.oxia.fwk.context.Context;
import com.oxia.fwk.core.IValueObject;

public class GetModificationDonneesClientCmd {

    public GetModificationDonneesClientCmd() {
    }

    /**
     * methode execute
     * @param  vo Objet : ParamModificationDonneesVo
     * @return vo Objet : ParamModificationDonneesVo Activite
     */
    public IValueObject execute(IValueObject vo) {
        ParamRechercheModificationDonneesVo paramRechercheModificationDonneesVo = 
            (ParamRechercheModificationDonneesVo)vo;

        Context context = ContextHandler.getContext();
        ModificationDonneesService modificationDonneesService = 
            (ModificationDonneesService)context.getBean("modificationDonneesService");
        paramRechercheModificationDonneesVo = 
                (ParamRechercheModificationDonneesVo)modificationDonneesService.getModificationDonneesClient(paramRechercheModificationDonneesVo);

        return (paramRechercheModificationDonneesVo);
    }
}
