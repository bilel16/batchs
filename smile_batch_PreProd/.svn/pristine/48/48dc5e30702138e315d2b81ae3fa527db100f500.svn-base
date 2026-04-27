package com.bna.smile.model.domainecommun.commande;

import com.bna.commun.model.Activite;
import com.bna.commun.model.PieceAnnexe;
import com.bna.commun.util.ContextHandler;
import com.bna.smile.model.domainecommun.service.NomenclatureService;

import com.oxia.fwk.context.Context;
import com.oxia.fwk.core.ICommande;
import com.oxia.fwk.core.IValueObject;
import com.oxia.fwk.core.ValueObject;

/**
 * Classe permettant la recherche d'une pièce annexe par son identifiant
 * @author Mdimagh Med Lassaad
 * @since 25/12/2007
 */
public class GetPieceAnnexeByIdCmd implements ICommande  {
    public GetPieceAnnexeByIdCmd() {
    }
    
    /**
     * executer la recherce de l'objet PieceAnnexe
     * @param vo  :PieceAnnexe
     * @return vo :PieceAnnexe
     */
    public IValueObject execute(IValueObject vo) {
        PieceAnnexe pieceAnnexe = (PieceAnnexe)vo;
        Context context = ContextHandler.getContext();

        NomenclatureService nomenclatureService = 
            (NomenclatureService)context.getBean("nomenclatureService");
        pieceAnnexe = (PieceAnnexe) nomenclatureService.getPieceAnnexeById(pieceAnnexe);
        return (pieceAnnexe);
    }  
}
