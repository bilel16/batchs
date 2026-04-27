/** Fichier: GetPieceAnnexeCmd version 1.0.0 du 19/01/2006
 * Copyright(c) 2006 BNA (www.bna.com.tn)
 * Classe: GetPieceAnnexeCmd 
 * package: com.bna.smile.model.domainecommun.commande;
 * Auteur : hassine
 */
package com.bna.smile.model.domainecommun.commande;


import com.bna.commun.model.Personne;
import com.bna.commun.model.PieceAnnexe;

import com.bna.commun.util.ContextHandler;
import com.bna.smile.model.domainecommun.model.PersonneCpt;
import com.bna.smile.model.domainecommun.model.PersonneStrc;
import com.bna.smile.model.domainecommun.service.PersonneService;

import com.oxia.fwk.context.Context;
import com.oxia.fwk.core.ICommande;
import com.oxia.fwk.core.IValueObject;
import com.oxia.fwk.core.ValueObject;


public class GetPieceAnnexeCmd implements ICommande {
    public GetPieceAnnexeCmd() {
    }

    /** methode execute
     * @param  Objet PersonneStrc  :Identifiant par pièce annexe de la personne;
     * @return Objet piece annexe
     **/
    public IValueObject execute(IValueObject vo) {

        PersonneStrc personneStrc = (PersonneStrc)vo;
        Context context = ContextHandler.getContext();
        PersonneService personneService = 
            (PersonneService)context.getBean("personneService");

        PieceAnnexe pieceannexe = 
            (PieceAnnexe)personneService.getPieceAnnexe(personneStrc);
        return (pieceannexe);

    }
}
