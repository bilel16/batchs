package com.bna.smile.model.domainecontratcompte.procuration.traitement;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.bna.commun.model.Mandat;
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

public class GetTraceMandCptTrt extends Traitement{

    public GetTraceMandCptTrt() {
    }
    public IValueObject perform(IValueObject vo) {

        Context context = ContextHandler.getContext();
        //ISearchEngine searchEngine = (SearchEngine)context.getBean("searchEngine");
        ISearchEngine searchEngine=(ISearchEngine)Context.getInstance().getSpringContext().getBean("searchEngine");
        ICriteria criteria = searchEngine.createCriteria();
        ICriteria criteriaMand = searchEngine.createCriteria();
        IExpression expression = searchEngine.createExpression();
        ParamMandOper paramMandOper = (ParamMandOper)vo;
        Listes list=new Listes();
        List listeTrace=new ArrayList();
        this.setCroFlag(false);
        try{
           /*recherche des mandat du contrat*/
           /*GetContratMandatTrt getContratMandatTrt=new GetContratMandatTrt();
           MandatRecherche mandatRecherche=new MandatRecherche();
           mandatRecherche.setContratCptId(paramMandOper.getContratCptId());
           ContratCptMandat contratCptMandat=(ContratCptMandat)getContratMandatTrt.exec(mandatRecherche);*/
            criteriaMand.add(expression.eq("contratCpt.contratCptId.codPrdPrd"  ,paramMandOper.getContratCptId().getCodPrdPrd()));        
            criteriaMand.add(expression.eq("contratCpt.contratCptId.codStrcStrc",paramMandOper.getContratCptId().getCodStrcStrc()));        
            criteriaMand.add(expression.eq("contratCpt.contratCptId.numCcptCcpt",paramMandOper.getContratCptId().getNumCcptCcpt()));  
            //criteriaMand.add(expression.eq("codEtatMand","V"));
            List lMand = searchEngine.find(Mandat.class, criteriaMand);
           if (lMand!=null && lMand.size()>0){
               for (Iterator it1 =lMand.iterator(); it1.hasNext();){
                       Mandat mandat=(Mandat)it1.next();
                       /*recherche des operations sur ce mandat*/
                       
                        criteria.add(expression.eq("mandat.numMandMand",mandat.getNumMandMand()));
                       
                        if (paramMandOper.getDateDebutOper()!=null){
                            criteria.add(expression.ge("datOperTrm",paramMandOper.getDateDebutOper()));
                        }
                        if(paramMandOper.getDateFinOper()!=null){
                            criteria.add(expression.le("datOperTrm",paramMandOper.getDateFinOper()));
                        }
               List l = searchEngine.find(TraceMandat.class, criteria); 
               if (l != null && l.size() > 0) {
                for (Iterator it =l.iterator(); it.hasNext();){
                TraceMandat traceMandat =(TraceMandat)it.next();
                listeTrace.add(traceMandat);
                }
               }
           
           }
           }
        
            
            list.setList(listeTrace);
        return (list);
        }catch (Exception e) {
                com.oxia.fwk.core.Error erreur = new com.oxia.fwk.core.Error();
                StringBuffer text = 
                    new StringBuffer("Erreur GetTraceMandatTrt ");
                text.append(e.toString());
                erreur.setCode("400");
                erreur.setDescription(text.toString());
                erreur.setKey("GetTraceMandatCptTrt");
                list.addError(erreur);
                logger.error("Exception lors de GetTraceMandatCptTrt "+paramMandOper.getContratCptId().getCodStrcStrc()+" : ",e);   
             
                return (list);
            }
    }
    public void genCroText(ValueObject vo) {
          
         
        } 
    public String getNumeroTache(ValueObject vo) {
        return (Constants.CODE_RESSOURCE_GENERALE);    
        
        
    }
}
