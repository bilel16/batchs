package com.bna.smile.model.domainecontratcompte.procuration.traitement;

import java.util.Iterator;
import java.util.List;

import com.bna.commun.model.Mandat;
import com.bna.commun.traitements.Traitement;
import com.bna.smile.model.constant.Constants;
import com.bna.smile.model.domainecontratcompte.procuration.model.DossierMandat;
import com.oxia.fwk.context.Context;
import com.oxia.fwk.core.ICriteria;
import com.oxia.fwk.core.IExpression;
import com.oxia.fwk.core.ISearchEngine;
import com.oxia.fwk.core.IValueObject;
import com.oxia.fwk.core.ValueObject;

public class GetMandatParDemandeTrt extends Traitement{
    //public Context context = ContextHandler.getContext();
    //private static final Logger logger = Logger.getLogger(GetMandatParDemandeTrt.class);

    public GetMandatParDemandeTrt() {
    }
    /**
     * methode permettant l'affichage des informations sur un contrat donné
     * ainsi que le liste des mandats valides sur ce contrat
     * @param vo : DossierM
     * andat
     * @return Mandat
     */
    public IValueObject perform (IValueObject vo){
    
        //ISearchEngine searchEngine = (SearchEngine)context.getBean("searchEngine"); 
        ISearchEngine searchEngine=(ISearchEngine)Context.getInstance().getSpringContext().getBean("searchEngine");
        ICriteria criteria         = searchEngine.createCriteria();
        IExpression expression     = searchEngine.createExpression();

        DossierMandat dossierMandat=(DossierMandat)vo;
        Mandat mandat=new Mandat();
        Mandat mandat1=new Mandat();
        this.setCroFlag(false);
        try{
        if(dossierMandat.getNumDemMand() != null){
        criteria.add(expression.eq("numDemMand",dossierMandat.getNumDemMand()));}
        if(dossierMandat.getCodStrcConcer() != null){
        criteria.add(expression.eq("codStrcMand",dossierMandat.getCodStrcConcer()));}
        
        List l = searchEngine.find(Mandat.class, criteria);
        if (l != null && l.size() > 0) {
            for (Iterator it =l.iterator(); it.hasNext();){
            mandat = (Mandat)it.next();
            if ((mandat.getCodEtatMand()!="M")&&(mandat.getCodEtatMand()!="H")){
            mandat1=mandat;
            }
            }
            return mandat1;   
        }
        else{ /* Erreur: aucun mandat  */ 
                    return null;
                
                }
         
         
        }catch (Exception e) {
                com.oxia.fwk.core.Error erreur = new com.oxia.fwk.core.Error();
                StringBuffer text = 
                    new StringBuffer("Erreur GetMandatParDemandeTrt ");
                text.append(e.toString());
                erreur.setCode("600");
                erreur.setDescription(text.toString());
                erreur.setKey("GetMandatParDemandeTrt");
                mandat1.addError(erreur);
                logger.error("  Erreur lors de GetMandatParDemandeTrt concernant l'agence "+mandat1.getCodStrcMand()+" : ", e);
                return (mandat1);
            }
    }
    public void genCroText(ValueObject vo) {
          
         
        }  
    public String getNumeroTache(ValueObject vo) {
        return (Constants.CODE_RESSOURCE_GENERALE);    
        
        
    }
}

