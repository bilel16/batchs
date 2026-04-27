package com.bna.smile.model.domainecommun.traitement;

import com.bna.commun.model.PieceAnnexe;
import com.bna.commun.traitements.Traitement;

import com.bna.commun.util.ContextHandler;

import com.oxia.fwk.context.Context;
import com.oxia.fwk.core.ISearchEngine;
import com.oxia.fwk.core.IValueObject;
import com.oxia.fwk.core.ValueObject;
import com.oxia.fwk.searchengine.SearchEngine;

/**
 * Classe qui cherche une pièce annexe par sont identifiant
 * Mdimagh Med Lassaad
 * @since 25/12/2007
 */
public class GetPieceAnnexeByIdTrt extends Traitement {
   
    public GetPieceAnnexeByIdTrt() {
    
    }
    public IValueObject perform (IValueObject vo ) throws Exception{  
    PieceAnnexe pieceAnnexe = (PieceAnnexe) vo;
    try{
      
        ISearchEngine searchEngine=(ISearchEngine)Context.getInstance().getSpringContext().getBean("searchEngine");
        pieceAnnexe =(PieceAnnexe) searchEngine.get(PieceAnnexe.class,pieceAnnexe.getPieceAnnexeId() );
        
       
    }catch(Exception e){
        com.oxia.fwk.core.Error erreur = new com.oxia.fwk.core.Error();
        StringBuffer text = 
            new StringBuffer("Erreur dans GetPieceAnnexeByIdTrt : ");
        text.append(e.toString());
        erreur.setCode("100");
        erreur.setDescription(text.toString());
        erreur.setKey("GetPieceAnnexeByIdTrt");
        pieceAnnexe.addError(erreur);
        logger.error("Exception : ",e);   
       
    }
        return (pieceAnnexe);
    
    }
   
   
    public void genCroText(ValueObject vo) {
    
    }  
}
