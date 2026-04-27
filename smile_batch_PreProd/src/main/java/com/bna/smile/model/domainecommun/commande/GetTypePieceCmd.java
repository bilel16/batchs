package com.bna.smile.model.domainecommun.commande;

import com.bna.commun.model.TypePiece;
import com.bna.commun.util.ContextHandler;
import com.bna.smile.model.domainecommun.model.Listes;
import com.bna.smile.model.domainecommun.service.NomenclatureService;
import com.bna.smile.model.domainecommun.service.PersonneService;

import com.oxia.fwk.context.Context;
import com.oxia.fwk.core.ICommande;
import com.oxia.fwk.core.IValueObject;
import com.oxia.fwk.core.ValueObject;

/**
 * Commande permet de recuperer l'objet TypePiece
 * @author Mdimagh Med
 * @since 07/06/07
 */
public class GetTypePieceCmd implements ICommande {
    public GetTypePieceCmd() {
    }

    /**
     * methode execute
     * @param  vo Objet : TypePiece :avec le code du type de la pièce
     * @return vo Objet : TypePiece
     */
    public IValueObject execute(IValueObject vo) {
        Context context = ContextHandler.getContext();
        TypePiece typePiece = (TypePiece)vo;
        NomenclatureService nomenclatureService = 
            (NomenclatureService)context.getBean("nomenclatureService");
        typePiece = (TypePiece)nomenclatureService.getTypePiece(typePiece);
        return (typePiece);
    }

}
