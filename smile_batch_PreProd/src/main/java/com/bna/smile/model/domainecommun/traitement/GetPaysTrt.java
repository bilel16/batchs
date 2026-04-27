package com.bna.smile.model.domainecommun.traitement;

import com.bna.commun.model.Pays;
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

/**
 * classe pour la recherche d'un pays
 * @author Mdimagh Med
 * @since 30/05/07
 */
public class GetPaysTrt  extends Traitement {
    public GetPaysTrt() {
    }

    /**
     * methde d'execution de la recherche
     * @param vo : Pays
     * @return vo :Pays
     */
    public IValueObject perform(IValueObject vo) {


        Pays pays = (Pays)vo;
        ISearchEngine searchEngine=(ISearchEngine)Context.getInstance().getSpringContext().getBean("searchEngine");

        ICriteria criteria = searchEngine.createCriteria();
        IExpression expression = searchEngine.createExpression();

        try {
            criteria.add(expression.eq("codPaysPays", pays.getCodPaysPays()));

            List listPays = searchEngine.find(Pays.class, criteria);
            /*si le pays existe*/
            if (listPays != null && listPays.size() > 0) {
                pays = (Pays)listPays.get(0);
            }

        } catch (Exception e) {
            com.oxia.fwk.core.Error erreur = new com.oxia.fwk.core.Error();
            StringBuffer text = 
                new StringBuffer("Erreur dans GetPaysTrt : ");
            text.append(e.toString());
            erreur.setCode("200");
            erreur.setDescription(text.toString());
            erreur.setKey("GetPaysTrt");
            pays.addError(erreur);
            logger.error("Exception : ",e);   
            throw new RuntimeException(e);  
           
        }
        return (pays);
    }
    
    public void genCroText(ValueObject vo) {
    
    }

    public String getNumeroTache (ValueObject vo) {
     return  Constants.CODE_RESSOURCE_GENERALE;
    }
}
