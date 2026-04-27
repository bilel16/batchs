/** Fichier: GetSousClassActiviteCmd.java version 1.0.0 du 19/01/2006
 * Copyright(c) 2006 BNA (www.bna.com.tn)
 * Classe: GetClassActiviteCmd
 * package: com.bna.smile.model.souscriptionContratCompte.commande
 * Auteur : Ramzi
 */
package com.bna.smile.model.domainecommun.commande;


import com.bna.commun.util.ContextHandler;
import com.bna.smile.model.domainecommun.model.Listes;
import com.bna.smile.model.domainecommun.service.PersonneService;

import com.oxia.fwk.context.Context;
import com.oxia.fwk.core.ICommande;
import com.oxia.fwk.core.IValueObject;
import com.oxia.fwk.core.ValueObject;

/** Fichier: GetSousClassActiviteCmd.java version 1.0.0 du 19/01/2006
 * Copyright(c) 2006 BNA (www.bna.com.tn)
 * Classe: GetSousClassActiviteCmd
 * package: com.bna.smile.model.souscriptionContratCompte.commande
 * Auteur : Ramzi
 */
public class GetSousClassActiviteCmd implements ICommande {
    public GetSousClassActiviteCmd() {
    }

    /** Cette commande retourne la liste des sous classe d'activité dont le libellé contient un mot
     * methode execute
     * @param  vo Objet : SousClassActivite
     * @return vo Objet : Listes SousClassActivite
     */
    public IValueObject execute(IValueObject vo) {
         Context context = ContextHandler.getContext();        
         PersonneService personneService = (PersonneService)context.getBean("personneService");

        Listes listeSousClassActivite = 
            (Listes)personneService.getListeSousClassActivite(vo);
        return (listeSousClassActivite);
    }

}
