package com.bna.smile.model.domaineplacement.traitement;

import com.bna.commun.model.AvancRembLiquid;
import com.bna.commun.model.InteretServi;
import com.bna.commun.traitements.Traitement;
import com.bna.commun.util.ContextHandler;
import com.bna.smile.model.constant.Constants;

import com.oxia.fwk.context.Context;
import com.oxia.fwk.core.ISearchEngine;
import com.oxia.fwk.core.IValueObject;
import com.oxia.fwk.core.ValueObject;
import com.oxia.fwk.searchengine.SearchEngine;

public class GetInteretServiByIdTrt extends Traitement{
    public GetInteretServiByIdTrt() {
    }
    public IValueObject perform(IValueObject vo) {
        Context context = ContextHandler.getContext();
        ISearchEngine searchEngine = (SearchEngine)context.getBean("searchEngine");

        InteretServi interetServi = (InteretServi)vo;
        InteretServi interetServiRetour=new InteretServi();
        this.setCroFlag(false);
        try{
        
         interetServiRetour =(InteretServi) searchEngine.get(InteretServi.class,interetServi.getNumIsrvIsrv() );
         
        return (interetServiRetour);
        }catch (Exception e) {
                com.oxia.fwk.core.Error erreur = new com.oxia.fwk.core.Error();
                StringBuffer text = 
                    new StringBuffer("Erreur GetInteretServiByIdTrt ");
                text.append(e.toString());
                erreur.setCode("400");
                erreur.setDescription(text.toString());
                erreur.setKey("GetAvancRembLiquidById");
                interetServiRetour.addError(erreur);
                logger.error(" *** Erreur lors de GetInteretServiByIdTrt" /*concernant l'agence "+avancRembLiquid.get().getCodStrcMand()*/+" : ", e);
                return (interetServiRetour);
            }
    }
    public void genCroText(ValueObject vo) {
          
         
        } 
    public String getNumeroTache(ValueObject vo) {
        return (Constants.CODE_RESSOURCE_GENERALE);    
        
        
    }
}
