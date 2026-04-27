/** Fichier: GetSousClassSegmentCmd.java version 1.0.0 du 19/01/2006
 * Copyright(c) 2006 BNA (www.bna.com.tn)
 * Classe: GetClassSegmentCmd
 * package: com.bna.smile.model.souscriptionContratCompte.commande
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

/** Fichier: GetSousClassSegmentCmd.java version 1.0.0 du 14/06/2007
 * Copyright(c) 2006 BNA (www.bna.com.tn)
 * Classe: GetSousClassSegmentCmd
 * package: com.bna.smile.model.souscriptionContratCompte.commande
 * Auteur : Ramzi
 */
public class GetSousClassSegmentCmd  implements ICommande {
    public GetSousClassSegmentCmd() {
    }

    /** Cette commande retourne la liste des sous classe segment dont le libellé contient un mot
     * methode execute
     * @param  vo Objet : SousClassSegment
     * @return vo Objet : Listes SousClassSegment
     */
    public IValueObject execute(IValueObject vo) {
        Context context = ContextHandler.getContext();

        NomenclatureService nomenclatureService = 
            (NomenclatureService)context.getBean("nomenclatureService");
        Listes listeSousClassSegment = 
            (Listes)nomenclatureService.getListeSousClassSegment(vo);
        return (listeSousClassSegment);
    }

}
