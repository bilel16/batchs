package com.bna.smile.model.domainecontratcompte.modificationdonneesclient.commande;


import com.bna.commun.util.ContextHandler;
import com.bna.smile.model.domainecontratcompte.modificationdonneesclient.model.ParamModificationDonneesVo;
import com.bna.smile.model.domainecontratcompte.modificationdonneesclient.service.ModificationDonneesService;
import com.oxia.fwk.context.Context;
import com.oxia.fwk.core.ValueObject;

public class ModifierDonneesClientCmd {
    public ModifierDonneesClientCmd() {
    }

    /**
     * methode execute
     * @param  vo Objet : ParamModificationDonneesVo
     * @return vo Objet : ParamModificationDonneesVo Activite
     */
    public ValueObject execute(ValueObject vo) {
        ParamModificationDonneesVo paramModificationDonneesVo = 
            (ParamModificationDonneesVo)vo;

        Context context = ContextHandler.getContext();
        ModificationDonneesService modificationDonneesService = 
            (ModificationDonneesService)context.getBean("modificationDonneesService");

        paramModificationDonneesVo = 
                (ParamModificationDonneesVo)modificationDonneesService.modifierDonneesClient(paramModificationDonneesVo);
        return (paramModificationDonneesVo);
    }

}

