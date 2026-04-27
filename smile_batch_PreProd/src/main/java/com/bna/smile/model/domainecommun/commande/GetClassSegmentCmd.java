/** Fichier: GetClassActiviteCmd.java version 1.0.0 du 19/01/2006
 * Copyright(c) 2006 BNA (www.bna.com.tn)
 * Classe: GetClassActiviteCmd
 * package: com.bna.smile.model.souscriptionContratCompte.commande
 * Auteur : Ramzi
 */
package com.bna.smile.model.domainecommun.commande;


import com.bna.commun.util.ContextHandler;
import com.bna.smile.model.domainecommun.model.Listes;
import com.bna.smile.model.domainecommun.service.NomenclatureService;
import com.bna.smile.model.domainecommun.service.PersonneService;

import com.oxia.fwk.context.Context;
import com.oxia.fwk.core.IValueObject;
import com.oxia.fwk.core.ValueObject;

/** Fichier: GetClassSegmentCmd.java version 1.0.0 
 * Copyright(c) 2006 BNA (www.bna.com.tn)
 * Classe: GetClassSegmentCmd
 * package: com.bna.smile.model.souscriptionContratCompte.commande
 * Auteur : Ramzi
 */
public class GetClassSegmentCmd {
    public GetClassSegmentCmd() {
    }

    /** Cette commande retourne la liste des classe segment dont le libellé contient un mot
     * methode execute
     * @param  vo Objet : ClasSegment
     * @return vo Objet : Listes ClasSegment
     */
    public IValueObject execute(IValueObject vo) {
        //ClasActivite listeClassActivite = (ClasActivite)vo;
         Context context = ContextHandler.getContext();
         NomenclatureService nomenclatureService = 
             (NomenclatureService)context.getBean("nomenclatureService");
        Listes listeClasSegment = (Listes)nomenclatureService.getListeClassSegment(vo);
        return (listeClasSegment);
    }

}
