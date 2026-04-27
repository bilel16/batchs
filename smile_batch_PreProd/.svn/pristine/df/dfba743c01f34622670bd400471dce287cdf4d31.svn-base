package com.bna.smile.model.domainecontratcompte.procuration.traitement;

import java.util.ArrayList;
import java.util.List;

import com.bna.commun.model.TraceMandat;
import com.bna.commun.traitements.Traitement;
import com.bna.commun.util.ContextHandler;
import com.bna.smile.model.constant.Constants;
import com.bna.smile.model.domainecommun.model.Listes;
import com.bna.smile.model.domainecontratcompte.procuration.model.ParamMandOper;
import com.oxia.fwk.context.Context;
import com.oxia.fwk.core.ICriteria;
import com.oxia.fwk.core.IExpression;
import com.oxia.fwk.core.ISearchEngine;
import com.oxia.fwk.core.IValueObject;
import com.oxia.fwk.core.ValueObject;

public class GetTraceMandatTrt extends Traitement{

    public GetTraceMandatTrt() {
    }
    public IValueObject perform(IValueObject vo) {

        Context context = ContextHandler.getContext();
        //ISearchEngine searchEngine = (SearchEngine)context.getBean("searchEngine");
        ISearchEngine searchEngine=(ISearchEngine)Context.getInstance().getSpringContext().getBean("searchEngine");

        ICriteria criteria = searchEngine.createCriteria();
        IExpression expression = searchEngine.createExpression();
        ParamMandOper paramMandOper = (ParamMandOper)vo;
        Listes list=new Listes();
        List listeTrace=new ArrayList();
        this.setCroFlag(false);
        try{
                   
            if (paramMandOper.getDateDebutOper()!=null){
                criteria.add(expression.ge("datOperTrm",paramMandOper.getDateDebutOper()));
            }
            if(paramMandOper.getDateFinOper()!=null){
                criteria.add(expression.le("datOperTrm",paramMandOper.getDateFinOper()));
            }
            if (paramMandOper.getCodOper()!=null){
                criteria.add(expression.eq("tache.tacheId.codOperOper",paramMandOper.getCodOper()));
            }
            if (paramMandOper.getCodtach()!=null){
            criteria.add(expression.eq("tache.tacheId.codTachTach",paramMandOper.getCodtach()));
            }

            List l = searchEngine.find(TraceMandat.class, criteria); 
            /*if (l != null && l.size() > 0) {
                for (Iterator it =l.iterator(); it.hasNext();){
                    TraceMandat traceMandat =(TraceMandat)it.next();
                   listeTrace.add(traceMandat);
                }
            }*/
            list.setList(l);
        return (list);
        }catch (Exception e) {
                com.oxia.fwk.core.Error erreur = new com.oxia.fwk.core.Error();
                StringBuffer text = 
                    new StringBuffer("Erreur GetTraceMandatTrt ");
                text.append(e.toString());
                erreur.setCode("400");
                erreur.setDescription(text.toString());
                erreur.setKey("GetTraceMandatTrt");
                list.addError(erreur);
                logger.error("Exception lors de GetTraceMandatTrt concernat l'agence "+paramMandOper.getContratCptId().getCodStrcStrc()+" : ",e);   
             
                return (list);
            }
    }
    public void genCroText(ValueObject vo) {
          
         
        } 
    public String getNumeroTache(ValueObject vo) {
        return (Constants.CODE_RESSOURCE_GENERALE);    
        
        
    }
}
