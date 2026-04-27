package com.bna.smile.model.domainecaisse.commande;

import com.bna.commun.util.ContextHandler;
import com.bna.smile.model.domainecaisse.service.CaisseService;
import com.bna.smile.model.domainecommun.model.Listes;
import com.oxia.fwk.context.Context;
import com.oxia.fwk.core.ICommande;
import com.oxia.fwk.core.IValueObject;

public class UpdateListDetailsSessionCaissesMouvementCaisseCmd implements ICommande{
    public UpdateListDetailsSessionCaissesMouvementCaisseCmd() {
    }
    
    public IValueObject execute(IValueObject vo) {
        
        Context context = ContextHandler.getContext();
        CaisseService caisseService =  (CaisseService)context.getBean("caisseService");
        Listes listes = (Listes)vo;
        Listes listesMvtCaisse = (Listes)caisseService.updateListDetailsSessionCaissesMouvementCaisse(listes);
            return (listesMvtCaisse);
        }

}
