package com.bna.smile.model.domainecontratcompte.modificationdonneesclient.commande;

import com.bna.commun.util.ContextHandler;
import com.bna.smile.model.domainecontratcompte.modificationdonneesclient.service.ModificationDonneesService;
import com.oxia.fwk.context.Context;
import com.oxia.fwk.core.IValueObject;

/**
 * Commande qui permet de modifier la relation
 * d'un client avec des personnes pour une qualité donnée
 * @author Mdimagh Lassaad
 * @since 28/06/07
 */
public class ModifierQualitePersonneCmd {
    public ModifierQualitePersonneCmd() {
    }
    /**
     * methode execute
     * @param  vo Objet : ParamListPersonneQualiteClientVo
     * @return vo Objet : ParamListPersonneQualiteClientVo 
     */
    public IValueObject execute(IValueObject vo) {
        
        Context context = ContextHandler.getContext();
        ModificationDonneesService modificationDonneesService = 
            (ModificationDonneesService)context.getBean("modificationDonneesService");

        return ( modificationDonneesService.modifierQualitePersonne(vo));
    }
}
