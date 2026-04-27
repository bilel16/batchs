package com.bna.smile.model.domainecontratcompte.procuration.traitement;

import com.bna.commun.model.Mandat;
import com.bna.commun.traitements.Traitement;
import com.bna.smile.model.constant.Constants;
import com.oxia.fwk.context.Context;
import com.oxia.fwk.core.ICriteria;
import com.oxia.fwk.core.IExpression;
import com.oxia.fwk.core.ISearchEngine;
import com.oxia.fwk.core.IValueObject;
import com.oxia.fwk.core.ValueObject;

public class GetMandatTrt extends Traitement{
    
    public GetMandatTrt() {
    }

    public IValueObject perform(IValueObject vo) {
        //Context context = ContextHandler.getContext();
        ISearchEngine searchEngine=(ISearchEngine)Context.getInstance().getSpringContext().getBean("searchEngine");
        //ISearchEngine searchEngine = (SearchEngine)context.getBean("searchEngine");
        ICriteria criteria = searchEngine.createCriteria();
        IExpression expression = searchEngine.createExpression();
        Mandat mandat = (Mandat)vo;
        Mandat mandatRetour=new Mandat();
        this.setCroFlag(false);
        try{
        criteria.add(expression.eq("numMandMand", mandat.getNumMandMand()));

        /* Charger le mandat existante */
        mandatRetour = 
            (Mandat)searchEngine.get(Mandat.class, mandat.getNumMandMand());

        return (mandatRetour);
        }catch (Exception e) {
                com.oxia.fwk.core.Error erreur = new com.oxia.fwk.core.Error();
                StringBuffer text = 
                    new StringBuffer("Erreur GetMandatTrt ");
                text.append(e.toString());
                erreur.setCode("400");
                erreur.setDescription(text.toString());
                erreur.setKey("GetMandatTrt");
                mandatRetour.addError(erreur);
                logger.error(" *** Erreur lors de GetMandatTrt concernant l'agence "+mandat.getCodStrcMand()+" : ", e);
                return (mandatRetour);
            }
    }
    public void genCroText(ValueObject vo) {
          
         
        } 
    public String getNumeroTache(ValueObject vo) {
        return (Constants.CODE_RESSOURCE_GENERALE);    
        
        
    }
}
