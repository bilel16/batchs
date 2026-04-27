package com.bna.smile.model.domainecontratcompte.modificationdonneesclient.commande;

import com.bna.commun.util.ContextHandler;
import com.bna.smile.model.domainecontratcompte.modificationdonneesclient.service.ModificationDonneesService;
import com.oxia.fwk.context.Context;
import com.oxia.fwk.core.IValueObject;

/**
 * Commande qui permet de modifier le type de compte de compte
 
 * @author Mdimagh Lassaad
 * @since 10/10/08
 */
public class ModificationTypeCompteCmd {
    public ModificationTypeCompteCmd() {
    }
    /**
     * methode execute
     * @param  vo Objet : ParamModificationTypeCompteVo
     * @return vo Objet : ParamModificationTypeCompteVo 
     */
    public IValueObject execute(IValueObject vo) {
        
        Context context = ContextHandler.getContext();
        ModificationDonneesService modificationDonneesService = 
            (ModificationDonneesService)context.getBean("modificationDonneesService");

        return ( modificationDonneesService.modificationTypeCompte(vo));
    }
}
