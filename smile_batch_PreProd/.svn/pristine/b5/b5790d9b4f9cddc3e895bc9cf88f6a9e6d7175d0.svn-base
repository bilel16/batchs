package com.bna.smile.model.statistique.commande;

import com.bna.commun.util.ContextHandler;
import com.bna.smile.model.statistique.service.TableauDeBordService;
import com.oxia.fwk.context.Context;
import com.oxia.fwk.core.ICommande;
import com.oxia.fwk.core.IValueObject;

/**
 * Calsse qui permet de faire les statistiques pour le tableau de bord
 * @author Mdimagh Med Lassaad
 * @since 07/05/2008
 */
public class GetTableauDeBordCmd  implements ICommande{
    public GetTableauDeBordCmd() {
    }
    
    public IValueObject execute(IValueObject vo) {
        Context context = ContextHandler.getContext();
        TableauDeBordService tableauDeBordService = (TableauDeBordService)context.getBean("tableauDeBordService");
        return (tableauDeBordService.getTableauDeBord(vo));
    }
}
