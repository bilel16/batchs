package com.bna.smile.model.domainecaisse.commande;

import com.bna.commun.util.ContextHandler;
import com.bna.smile.model.domainecaisse.service.CaisseService;
import com.oxia.fwk.context.Context;
import com.oxia.fwk.core.IValueObject;
/**
 * Cette classe permet de retourner la situation d'une caisse centrale, 
 * caisse centrale devise, caisse centrale dinars et detail caisse devise
 * pour une journée d'une structure.
 * @author Mdimagh Med Lassaad
 * @since 18/03/2008
 * @entree : VO CaisseCentrale avec CaisseCentraleId rempli
 */
public class GetSituationCaisseCentraleCmd {
    public GetSituationCaisseCentraleCmd() {
    }
    
    /**
     * 
     * @param  SituationCaisseCentraleVo
     * @return SituationCaisseCentraleVo
     */
    public

    IValueObject execute(IValueObject vo) {
       
        Context context = ContextHandler.getContext();

       CaisseService caisseService = 
            (CaisseService)context.getBean("caisseService");

        return (caisseService.GetSituationCaisseCentrale(vo));
    } 
}
