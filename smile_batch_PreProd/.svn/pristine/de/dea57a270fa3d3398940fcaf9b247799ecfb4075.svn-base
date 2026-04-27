package com.bna.smile.model.domainecaisse.traitement;

import java.util.List;

import org.hibernate.criterion.Order;

import com.bna.commun.model.MouvementSessionCaisse;
import com.bna.commun.traitements.Traitement;
import com.bna.commun.util.ContextHandler;
import com.bna.smile.model.domainecaisse.model.ParamMvtCaisse;
import com.bna.smile.model.domainecommun.model.Listes;
import com.oxia.fwk.context.Context;
import com.oxia.fwk.core.ICriteria;
import com.oxia.fwk.core.IExpression;
import com.oxia.fwk.core.ISearchEngine;
import com.oxia.fwk.core.IValueObject;
import com.oxia.fwk.core.ValueObject;
import com.oxia.fwk.searchengine.SearchEngine;

public class GetListMouvementCaisseTrt extends Traitement {
    Context context = ContextHandler.getContext();
    
    public GetListMouvementCaisseTrt() {
    }
    
    public  IValueObject perform(IValueObject vo) throws Exception {
    
        ParamMvtCaisse paramMvtCaisse = (ParamMvtCaisse)  vo;
        
        try {
            ISearchEngine searchEngine  = (SearchEngine)context.getBean("searchEngine");
            ICriteria criteria = searchEngine.createCriteria();
            IExpression expression = searchEngine.createExpression();   
            Listes listeMvtCaisse = new Listes();
            
            if (paramMvtCaisse.getNumeroCais()!= null){
            criteria.add(expression.eq("caisseStrc.caisseStrcId.numCaisCais",paramMvtCaisse.getNumeroCais()));
            }
            
            if (paramMvtCaisse.getCodeOperation()!= null){
                criteria.add(expression.eq("tache.tacheId.codOperOper",paramMvtCaisse.getCodeOperation()));
            }
            
            if (paramMvtCaisse.getEtat()!= null){
                criteria.add(expression.eq("codStatMvtc",paramMvtCaisse.getEtat()));
            }
            
            if (paramMvtCaisse.getCodeStructure()!= null){///*** envoi externe
            criteria.add(expression.eq("caisseStrc.caisseStrcId.codStrcStrc",paramMvtCaisse.getCodeStructure()));
            }

            criteria.addOrder(Order.desc("numMvtMvtc"));


            
            List l = searchEngine.find(MouvementSessionCaisse.class, criteria);
            
            if (l != null && l.size() > 0) {
                listeMvtCaisse.setList(l); }
            
            
            return listeMvtCaisse;
      
        } catch (Exception e) {
            com.oxia.fwk.core.Error erreur=new com.oxia.fwk.core.Error();
            erreur.setCode("Technique");
            erreur.setDescription("GetListMouvementCaisseTrt  "+e.getMessage());;
            paramMvtCaisse.addError(erreur);
            logger.error("Exception : ",e);   
            throw new   RuntimeException(e);
        }

    }

    public void genCroText(ValueObject vo) {

    }
}
