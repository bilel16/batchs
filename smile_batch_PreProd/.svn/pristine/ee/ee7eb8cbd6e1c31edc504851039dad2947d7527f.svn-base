/** Fichier: RechercheTuteurCmd.java version 1.0.0 du 19/01/2006
 * Copyright(c) 2006 BNA (www.bna.com.tn)
 * Classe: RechercheTuteurCmd
 * package com.bna.smile.model.domainecontratcompte.souscriptioncontratcompte.commande
 * Auteur : Mdimagh Med Lassaad
 */
package com.bna.smile.model.domainecontratcompte.souscriptioncontratcompte.commande;

import com.bna.commun.util.ContextHandler;
import com.bna.smile.model.domainecommun.model.PersonneStrc;
import com.bna.smile.model.domainecommun.model.Tuteur;
import com.bna.smile.model.domainecommun.service.ContratCompteService;
import com.oxia.fwk.context.Context;
import com.oxia.fwk.core.ICommande;
import com.oxia.fwk.core.IValueObject;

/** Commande Rechercher Tuteur : verifie si la personne est tuteur ,
 *  elle extrait la liste des mineures
 *  @author Mdimagh Lassaad
 *  @since 23-01-07
 *  @version 1.0
 */
public class GetTuteurCmd implements ICommande {
    public GetTuteurCmd() {
    }

    /** methode execute
     * @param  Objet PersonneStrc  :Identifiant de la personne;
     * @return Objet TuteurVoles   :Données du tuteur + liste des mineures en charge;
     **/
    public IValueObject execute(IValueObject o) {

        PersonneStrc personneStrc = (PersonneStrc)o;
        Context context = ContextHandler.getContext();

        ContratCompteService contratCompteService = 
            (ContratCompteService)context.getBean("contratCompteService");
        Tuteur tuteur = (Tuteur)contratCompteService.getTuteur(personneStrc);
        return (tuteur);

    }
}
