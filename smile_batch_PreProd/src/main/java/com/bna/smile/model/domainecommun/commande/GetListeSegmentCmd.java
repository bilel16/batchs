/** Fichier: GetSegmentCmd.java version 1.0.0 du 14/04/2006
 * Copyright(c) 2006 BNA (www.bna.com.tn)
 * Classe: GetClassSegmentCmd
 * package: com.bna.smile.model.domainecommun.commande
 * Auteur : Ramzi
 */
package com.bna.smile.model.domainecommun.commande;


import com.bna.commun.util.ContextHandler;
import com.bna.smile.model.domainecommun.model.Listes;
import com.bna.smile.model.domainecommun.service.NomenclatureService;
import com.bna.smile.model.domainecommun.service.PersonneService;

import com.oxia.fwk.context.Context;
import com.oxia.fwk.core.ICommande;
import com.oxia.fwk.core.IValueObject;
import com.oxia.fwk.core.ValueObject;

/** Fichier: GetSegmentCmd.java version 1.0.0 du 19/01/2006
 * Copyright(c) 2006 BNA (www.bna.com.tn)
 * Classe: GetSousClassSegmentCmd
 * package: com.bna.smile.model.domainecommun.commande
 * Auteur : Ramzi
 */
public class GetListeSegmentCmd implements ICommande {
    public GetListeSegmentCmd() {
    }

    /**
     * methode execute
     * @param  vo Objet : Segment
     * @return vo Objet : Listes Segment
     */
    public IValueObject execute(IValueObject vo) {
        Context context = ContextHandler.getContext();
         NomenclatureService nomenclatureService = 
             (NomenclatureService)context.getBean("nomenclatureService");
        Listes listeSegment = (Listes)nomenclatureService.getListeSegment(vo);
        return (listeSegment);
    }

}
