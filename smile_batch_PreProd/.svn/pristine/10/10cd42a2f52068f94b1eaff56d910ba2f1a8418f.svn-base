package com.bna.smile.model.domainecommun.traitement;


import com.bna.commun.model.PieceAnnexe;
import com.bna.commun.traitements.Traitement;
import com.bna.commun.util.ContextHandler;
import com.bna.smile.model.constant.Constants;
import com.bna.smile.model.domainecommun.model.PersonneStrc;

import com.oxia.fwk.context.Context;
import com.oxia.fwk.core.ICriteria;
import com.oxia.fwk.core.IExpression;
import com.oxia.fwk.core.ISearchEngine;
import com.oxia.fwk.core.IValueObject;
import com.oxia.fwk.core.ValueObject;
import com.oxia.fwk.searchengine.SearchEngine;

import java.util.List;


public class GetPieceAnnexeTrt extends Traitement{
    public GetPieceAnnexeTrt() {
    }


    /**
     * methode permettant la recherche d'une pièce annexe
     * @param vo :Objet : PersonneStrc : code et et type de la pièce annexe
     * @return   :Objet : pieceAnnexe
     */
    public

    IValueObject perform(IValueObject vo) {

        this.setCroFlag(false);
        PersonneStrc personneStrc = (PersonneStrc)vo;
        Context context = ContextHandler.getContext();
        ISearchEngine searchEngine = 
            (SearchEngine)context.getBean("searchEngine");
        ICriteria criteriaPers = searchEngine.createCriteria();
        ICriteria criteriaPieceAnn = searchEngine.createCriteria();
        IExpression expression = searchEngine.createExpression();
        PieceAnnexe pieceAnnexe = new PieceAnnexe();
        try {
            criteriaPieceAnn.add(expression.eq("pieceAnnexeId.codTpceTpce", 
                                               personneStrc.getCodTpceTpce()));
            criteriaPieceAnn.add(expression.eq("pieceAnnexeId.numPcePian", 
                                               personneStrc.getNumPcePers()));


            List listPieceAnnexe = searchEngine.find(PieceAnnexe.class, criteriaPieceAnn);
            /*si la piece annexe existe*/
            if (listPieceAnnexe != null && listPieceAnnexe.size() > 0) {
                pieceAnnexe = (PieceAnnexe)listPieceAnnexe.get(0);
            }

          

            } catch (Exception e) {
                com.oxia.fwk.core.Error erreur = new com.oxia.fwk.core.Error();
                StringBuffer text = 
                    new StringBuffer("Erreur dans GetPieceAnnexeTrt : ");
                text.append(e.toString());
                erreur.setCode("100");
                erreur.setDescription(text.toString());
                erreur.setKey("GetPieceAnnexe");
                pieceAnnexe.addError(erreur);
                logger.error("Exception : ",e);   
                throw new RuntimeException(e);  
              
            }     
        return (pieceAnnexe);
    }

    public void genCroText(ValueObject vo) {    
    
    }
    
    public String getNumeroTache(IValueObject vo) {
      return (Constants.CODE_RESSOURCE_GENERALE);     
    }

}


