package com.bna.smile.model.domainecaisse.commande;

import com.bna.commun.util.ContextHandler;
import com.bna.smile.model.domainecaisse.service.CaisseService;
import com.oxia.fwk.context.Context;
import com.oxia.fwk.core.IValueObject;

/**
 * commande pour la création d'un mouvement caisse
 * @author Mdimagh Med Lassaad
 * @since 28/03/02008
 */
public class InsertMouvementsCaissesCmd {
    public InsertMouvementsCaissesCmd() {
    }
    /**
    * @param   vo :MouvementsCaissesVo
    * @return  vo :MouvementsCaissesVo
    */
    public   IValueObject execute(IValueObject vo) {
      
       Context context = ContextHandler.getContext();

      CaisseService caisseService = 
           (CaisseService)context.getBean("caisseService");
       
       return (caisseService.insertMouvementsCaisses(vo));
    }
    
}
