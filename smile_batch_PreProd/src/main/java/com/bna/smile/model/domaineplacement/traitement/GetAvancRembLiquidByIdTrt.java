package com.bna.smile.model.domaineplacement.traitement;

import com.bna.commun.model.AvancRembLiquid;
import com.bna.commun.model.MandatPersonne;
import com.bna.commun.traitements.Traitement;
import com.bna.commun.util.ContextHandler;
import com.bna.smile.model.constant.Constants;

import com.bna.smile.model.domainecontratcompte.procuration.traitement.GetMandatPersonneByIdTrt;

import com.oxia.fwk.context.Context;
import com.oxia.fwk.core.ISearchEngine;
import com.oxia.fwk.core.IValueObject;
import com.oxia.fwk.core.ValueObject;
import com.oxia.fwk.searchengine.SearchEngine;

import org.apache.log4j.Logger;

public class GetAvancRembLiquidByIdTrt extends Traitement {

    private static final Logger logger = Logger.getLogger(GetMandatPersonneByIdTrt.class);

    public GetAvancRembLiquidByIdTrt() {
    }    
    
    public IValueObject perform(IValueObject vo) {
        Context context = ContextHandler.getContext();
        ISearchEngine searchEngine = (SearchEngine)context.getBean("searchEngine");

        AvancRembLiquid avancRembLiquid = (AvancRembLiquid)vo;
        AvancRembLiquid avancRembLiquidRetour=new AvancRembLiquid();
        this.setCroFlag(false);
        try{
        /* Charger l'avancRembLiquid existante */
        
         avancRembLiquidRetour =(AvancRembLiquid) searchEngine.get(AvancRembLiquid.class,avancRembLiquid.getNumSeqArl() );
        return (avancRembLiquidRetour);
        }catch (Exception e) {
                com.oxia.fwk.core.Error erreur = new com.oxia.fwk.core.Error();
                StringBuffer text = 
                    new StringBuffer("Erreur GetAvancRembLiquidById ");
                text.append(e.toString());
                erreur.setCode("400");
                erreur.setDescription(text.toString());
                erreur.setKey("GetAvancRembLiquidById");
                avancRembLiquidRetour.addError(erreur);
                logger.error(" *** Erreur lors de GetAvancRembLiquidById" /*concernant l'agence "+avancRembLiquid.get().getCodStrcMand()*/+" : ", e);
                return (avancRembLiquidRetour);
            }
    }
    public void genCroText(ValueObject vo) {
          
         
        } 
    public String getNumeroTache(ValueObject vo) {
        return (Constants.CODE_RESSOURCE_GENERALE);    
        
        
    }
      
    
    
    
    
    
    
    
    
    
}
