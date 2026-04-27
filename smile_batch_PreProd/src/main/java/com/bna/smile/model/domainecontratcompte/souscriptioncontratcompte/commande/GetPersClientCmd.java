/** Fichier: GetPersClientCmd.java version 1.0.0 du 08/05/2007
 * Copyright(c) 2007 BNA (www.bna.com.tn)
 * Classe: GetPersClientCmd
 * package com.bna.smile.model.domainecontratcompte.souscriptioncontratcompte.commande
 * Auteur : El Arbi hassine
 */
package com.bna.smile.model.domainecontratcompte.souscriptioncontratcompte.commande;

import com.bna.commun.model.Personne;
import com.bna.commun.util.ContextHandler;
import com.bna.smile.model.domainecommun.model.PersonneCpt;
import com.bna.smile.model.domainecommun.service.ContratCompteService;
import com.oxia.fwk.context.Context;
import com.oxia.fwk.core.ICommande;
import com.oxia.fwk.core.IValueObject;

/** Commande GetPersClientCmd : 
 *  permet de determiner le tuteur ou l'actionaire à partir des données du client  
 *  @author El Arbi hassine
 *  @since 08/05/2007
 *  @version 1.0
 */
public class GetPersClientCmd implements ICommande{
    public GetPersClientCmd() {
    }

    /** methode execute
     * @param  Objet PersonneCpt  :Identifiant de du client;
     * @return Objet personne;
     **/
    public IValueObject execute(IValueObject o) {

        PersonneCpt personneCpt = (PersonneCpt)o;
        Context context = ContextHandler.getContext();

        ContratCompteService contratCompteService = 
            (ContratCompteService)context.getBean("contratCompteService");
        Personne personne = 
            (Personne)contratCompteService.GetPersClient(personneCpt);
        return (personne);

    }
}
