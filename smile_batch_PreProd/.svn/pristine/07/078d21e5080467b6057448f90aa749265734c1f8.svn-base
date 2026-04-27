

package com.bna.smile.model.domainecommun.commande;


import com.bna.commun.model.PieceAnnexe;
import com.bna.commun.util.ContextHandler;

import com.bna.smile.model.domainecommun.service.PersonneService;

import com.bna.smile.model.domainecommun.model.PersonneCpt;

import com.oxia.fwk.context.Context;
import com.oxia.fwk.core.ICommande;
import com.oxia.fwk.core.IValueObject;
import com.oxia.fwk.core.ValueObject;

/** Fichier: InsertPersonneCmd.java
 * @version 1.0.0 du 05/03/2007
 * Copyright(c) 2007 BNA (www.bna.com.tn)
 * Classe: InsertPieceAnnexeCmd
 * package com.bna.smile.model.domainecommun.commande
 * @author : El arbi hassine
 */
public class InsertPieceAnnexeCmd implements ICommande {
    public InsertPieceAnnexeCmd() {
    }

    /**
     *  methode execute
     * @param   Objet :Piece Annexe;
     * @return  Objet :Piece Annexe;
     */
    public IValueObject execute(IValueObject vo) {
        PieceAnnexe pieceAnnexe = (PieceAnnexe)vo;
        Context context = ContextHandler.getContext();

        PersonneService personneService = 
            (PersonneService)context.getBean("personneService");
        PieceAnnexe pieceAnnexeRetour = 
            (PieceAnnexe)personneService.insertPieceAnnexe(pieceAnnexe);
        return (pieceAnnexeRetour);
    }
}
