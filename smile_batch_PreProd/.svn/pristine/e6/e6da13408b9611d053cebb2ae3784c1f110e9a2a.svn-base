/** Fichier: GetProfessionCmd.java version 1.0.0 du 19/01/2006
 * Copyright(c) 2006 BNA (www.bna.com.tn)
 * Classe: GetProfessionCmd
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

/** Fichier: GetProfessionCmd.java version 1.0.0 du 19/01/2006
 * Copyright(c) 2006 BNA (www.bna.com.tn)
 * Classe: GetProfessionCmd
 * package: com.bna.smile.model.souscriptionContratCompte.commande
 * Auteur : Ramzi
 */
public class GetProfessionCmd implements ICommande {
    public GetProfessionCmd() {
    }

    /**
     * methode execute
     * @param  vo Objet : Profession
     * @return vo Objet : Listes Profession
     */
    public IValueObject execute(IValueObject vo) {
        Context context = ContextHandler.getContext();
        PersonneService personneService = 
            (PersonneService)context.getBean("personneService");
        Listes listeProfession = 
            (Listes)personneService.getListeProfession(vo);
        return (listeProfession);
    }

}
