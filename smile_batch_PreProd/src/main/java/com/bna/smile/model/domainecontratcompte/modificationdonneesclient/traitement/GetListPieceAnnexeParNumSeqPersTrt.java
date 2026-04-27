package com.bna.smile.model.domainecontratcompte.modificationdonneesclient.traitement;

import java.util.List;

import org.apache.log4j.Logger;

import com.bna.commun.model.PieceAnnexe;
import com.bna.commun.traitements.Traitement;
import com.bna.commun.util.ContextHandler;
import com.bna.smile.model.domainecontratcompte.modificationdonneesclient.model.ParamListePieceAnnexeParNumSeqPersVo;
import com.oxia.fwk.context.Context;
import com.oxia.fwk.core.ICriteria;
import com.oxia.fwk.core.IExpression;
import com.oxia.fwk.core.ISearchEngine;
import com.oxia.fwk.core.IValueObject;
import com.oxia.fwk.core.ValueObject;
import com.oxia.fwk.searchengine.SearchEngine;

/**
 * Cette classe permet d'extraire la liste des pi_èce annexe d'une personne par sont clé primaire
 * @author Mdimagh Med Lassaad
 * @since 02/06/2008
 */
public class GetListPieceAnnexeParNumSeqPersTrt extends Traitement {
    public GetListPieceAnnexeParNumSeqPersTrt() {
    }
    static Logger logger = Logger.getLogger(GetListPieceAnnexeParNumSeqPersTrt.class);

    public IValueObject perform (IValueObject vo) {
        logger.info("Entrée GetListPieceAnnexeParNumSeqPersTrt");
        ParamListePieceAnnexeParNumSeqPersVo paramListeVo = 
            (ParamListePieceAnnexeParNumSeqPersVo)vo;
         try{
            Context context = ContextHandler.getContext();
            ISearchEngine searchEngine = 
                (SearchEngine)context.getBean("searchEngine");
    
            ICriteria criteria = searchEngine.createCriteria();
            IExpression expression = searchEngine.createExpression();
    
            criteria.add(expression.eq("pieceAnnexeId.numSeqPers", 
                                       paramListeVo.getNumSeqPers()));
            List listeDesPiecesAnnexes = 
                searchEngine.find(PieceAnnexe.class, criteria);
            
            paramListeVo.setListeDesPiecesAnnexes(listeDesPiecesAnnexes);
            logger.info("Sortie GetListPieceAnnexeParNumSeqPersTrt");
            
            return (paramListeVo);
         
        } catch (Exception e) {
            logger.debug("Exception dans : GetListPieceAnnexeParNumSeqPersTrt " +e.toString());
            com.oxia.fwk.core.Error erreur = new com.oxia.fwk.core.Error();
            StringBuffer text = 
                new StringBuffer("Erreur dans GetListeDesMineursDevenusMajeursTrt : ");
            text.append(e.toString());
            erreur.setCode("200");
            erreur.setDescription(text.toString());
            erreur.setKey("GetListeDesMineursDevenusMajeursTrt");
    
            paramListeVo.addError(erreur);
            return (paramListeVo);
        }
    }

    public void genCroText (ValueObject vo){
    }
}
