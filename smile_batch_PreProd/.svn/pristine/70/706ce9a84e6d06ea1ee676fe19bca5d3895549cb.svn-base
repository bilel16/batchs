package com.bna.smile.model.domainecaisse.commande;

import com.bna.commun.util.ContextHandler;
import com.bna.smile.model.domainecaisse.service.CaisseService;
import com.oxia.fwk.context.Context;
import com.oxia.fwk.core.ICommande;
import com.oxia.fwk.core.IValueObject;

/**
 * Classe qui permet de retourner la liste des caisse pour une structure dans une journée
 * @author JERBI Lamia
 * @since 27/03/2011
 */
public class GetListeSessionJrnCaisseCmd implements ICommande{
    public GetListeSessionJrnCaisseCmd() {
    }
    
    /**
     * 
     * @param   vo :ListeCaisseStructureVo
     * @return  vo :ListeCaisseStructureVo
     */
    public   IValueObject execute(IValueObject vo) {
       
        Context context = ContextHandler.getContext();

       CaisseService caisseService = (CaisseService)context.getBean("caisseService");
        
        return (caisseService.GetListeSessionJrnCaisse(vo));
    }    
}
