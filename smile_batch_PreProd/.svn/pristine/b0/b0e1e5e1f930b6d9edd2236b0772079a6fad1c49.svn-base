package com.bna.smile.model.domainecontratcompte.modificationdonneesclient.commande;

import com.bna.commun.util.ContextHandler;
import com.bna.smile.model.domainecontratcompte.modificationdonneesclient.service.ModificationDonneesService;
import com.oxia.fwk.context.Context;
import com.oxia.fwk.core.IValueObject;

/**
 * Commande qui permet de modifier l'intitulé de compte
 
 * @author Mdimagh Lassaad
 * @since 06/10/07
 */
public class ModifierIntituleCompteCmd {
    public ModifierIntituleCompteCmd() {
    }
    /**
     * methode execute
     * @param  vo Objet : ParamModificationIntituleCompteVo
     * @return vo Objet : ParamModificationIntituleCompteVo 
     */
    public IValueObject execute(IValueObject vo) {
        
        Context context = ContextHandler.getContext();
        ModificationDonneesService modificationDonneesService = 
            (ModificationDonneesService)context.getBean("modificationDonneesService");

        return ( modificationDonneesService.modificationIntituleCompte(vo));
    }
}
