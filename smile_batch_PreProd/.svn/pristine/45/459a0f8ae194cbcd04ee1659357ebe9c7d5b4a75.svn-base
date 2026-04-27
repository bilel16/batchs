package com.bna.smile.model.domainecontratcompte.procuration.traitement;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.bna.commun.model.Mandat;
import com.bna.commun.model.MandatPersonne;
import com.bna.commun.traitements.Traitement;
import com.bna.smile.model.constant.Constants;
import com.bna.smile.model.domainecommun.model.Listes;
import com.bna.smile.model.domainecommun.model.MandatPersonneMandat;
import com.bna.smile.model.domainecontratcompte.procuration.model.MandatRecherche;
import com.oxia.fwk.context.Context;
import com.oxia.fwk.core.ICriteria;
import com.oxia.fwk.core.IExpression;
import com.oxia.fwk.core.ISearchEngine;
import com.oxia.fwk.core.IValueObject;
import com.oxia.fwk.core.ValueObject;

public class GetMandatAvaliderTrt extends Traitement{
   // public Context context = ContextHandler.getContext();
   // private static final Logger logger = Logger.getLogger(GetMandatAvaliderTrt.class);

    public GetMandatAvaliderTrt() {
    }
    
    /**
     * methode permettant l'affichage des informations sur un contrat donné
     * ainsi que le liste des mandats valides sur ce contrat
     * @param vo : IdContratCpt
     * @return ContratCptMandat
     */
    public IValueObject perform (IValueObject vo){
    
        //ISearchEngine searchEngine = (SearchEngine)context.getBean("searchEngine"); 
        ISearchEngine searchEngine=(ISearchEngine)Context.getInstance().getSpringContext().getBean("searchEngine");
        ICriteria criteria         = searchEngine.createCriteria();
        IExpression expression     = searchEngine.createExpression();

        MandatRecherche mandatRecherche=(MandatRecherche)vo;
        Listes list=new Listes();
       
        List listeMandatAvalider =new ArrayList();
        this.setCroFlag(false);
        try{
        /* Rechercher des mandats  de l'agence  */
        if (mandatRecherche.getCodMenu().equalsIgnoreCase("SM")||mandatRecherche.getCodMenu().equalsIgnoreCase("SA")||(mandatRecherche.getCodMenu().equalsIgnoreCase("SR"))){
            criteria.add(expression.or(expression.isNull("codEdemMand"),expression.eq("codEdemMand","VR")));  
            criteria.add(expression.eq("codEtatMand",mandatRecherche.getCodEtat()));
        }else{    
            if (mandatRecherche.getCodEtatAttente()!=null){
                criteria.add(expression.eq("codEdemMand",mandatRecherche.getCodEtatAttente()));
            }
            if(mandatRecherche.getCodEtat()!=null){
                criteria.add(expression.eq("codEtatMand",mandatRecherche.getCodEtat()));
            }else if (mandatRecherche.getCodEtat() != null){
                criteria.add(expression.isNull("codEdemMand"));
            }
        } 
        
        if (mandatRecherche.getCodStrcConcer()!=null){
          criteria.add(expression.eq("codStrcMand",mandatRecherche.getCodStrcConcer()));
          }
        if (mandatRecherche.getContratCptId()!=null){
          criteria.add(expression.eq("contratCpt.contratCptId.codStrcStrc",mandatRecherche.getContratCptId().getCodStrcStrc()));
        }
        
        if (mandatRecherche.getCodMenu().equalsIgnoreCase("DEC")){
        // cas de notification Deces...
         if (mandatRecherche.getContratCptId()!=null){
           criteria.add(expression.eq("contratCpt.contratCptId.codPrdPrd",mandatRecherche.getContratCptId().getCodPrdPrd()));
           criteria.add(expression.eq("contratCpt.contratCptId.numCcptCcpt",mandatRecherche.getContratCptId().getNumCcptCcpt()));
         }            
            
        }
        
         //Critère de recherche sur les dates debut et fin consultation
        if (mandatRecherche.getDateDeb()!=null){
            criteria.add(expression.ge("datCreMand",mandatRecherche.getDateDeb()));
        }
        if(mandatRecherche.getDateFin()!=null){
            criteria.add(expression.lt("datCreMand",mandatRecherche.getDateFin()));
        }
    
        List l = searchEngine.find(Mandat.class, criteria);
        if (l != null && l.size() > 0) {
            for (Iterator it =l.iterator(); it.hasNext();){
                Mandat mandat =(Mandat)it.next();
                String nom="";  
                for (Iterator it1 =mandat.getMandatPersonnes().iterator(); it1.hasNext();){
                    MandatPersonne mandatPersonne=(MandatPersonne)it1.next();
                    if (mandatPersonne.getCodEtatMp().equalsIgnoreCase("V")){
                        nom=nom+(String)mandatPersonne.getPersonne().getNomNomPers()+" "+
                        mandatPersonne.getPersonne().getNomPrnPers()+", ";
                    }
                }
                
                MandatPersonneMandat mandatPersonneMandat=new MandatPersonneMandat();
                testetEtatMandat(mandat,mandatPersonneMandat);
                mandatPersonneMandat.setMandat(mandat);
                mandatPersonneMandat.setMandataires(nom);
                listeMandatAvalider.add(mandatPersonneMandat);
     }
            
        list.setList(listeMandatAvalider);
        return list;
        }else{ /* Erreur: aucun mandat à valider */ 
            return null;
        
        }
        
       

    }catch (Exception e) {
            com.oxia.fwk.core.Error erreur = new com.oxia.fwk.core.Error();
            StringBuffer text = 
                new StringBuffer("Erreur GetMandatAvaliderTrt ");
            text.append(e.toString());
            erreur.setCode("500");
            erreur.setDescription(text.toString());
            erreur.setKey("GetMandatAvaliderTrt");
            list.addError(erreur);
            logger.error("Exception dans GetMandatAvaliderTrt "+mandatRecherche.getContratCptId().getCodStrcStrc()+": ",e);   
            return (list);
        } 
    
    
}
public void testetEtatMandat(Mandat mandat,MandatPersonneMandat mandatPersonneMandat){

if (mandat.getCodEtatMand().equalsIgnoreCase(Constants.COD_ETAT_MAND_ATT_PRE)){
    mandatPersonneMandat.setEtatMand("Saisie");
    
}else if (mandat.getCodEtatMand().equalsIgnoreCase(Constants.COD_ETAT_MAND_ATT)){
    mandatPersonneMandat.setEtatMand("Prévalidé");
    
}else if (mandat.getCodEtatMand().equalsIgnoreCase(Constants.COD_ETAT_MAND_VALID)){
    if (mandat.getCodEdemMand()==null){
        mandatPersonneMandat.setEtatMand("Valide");
    }else if (mandat.getCodEdemMand().equals("VR")){
        mandatPersonneMandat.setEtatMand("Validé avec réserve");
    }else if (mandat.getCodEdemMand().equals("SR")){
        mandatPersonneMandat.setEtatMand("Renouvellement saisie");
    }else if (mandat.getCodEdemMand().equals("AR")){
        mandatPersonneMandat.setEtatMand("Renouvellement prévalidé");
    }else if (mandat.getCodEdemMand().equals("SA")){
        mandatPersonneMandat.setEtatMand("Annulation saisie");
    }else if (mandat.getCodEdemMand().equals("AA")){
        mandatPersonneMandat.setEtatMand("Annulation prévalidé");
    }            
}else if (mandat.getCodEtatMand().equalsIgnoreCase(Constants.COD_SYCL_MOD)){ 
    if (mandat.getCodEdemMand().equals("SM")){
            mandatPersonneMandat.setEtatMand("Modification saisie");
    }else if (mandat.getCodEdemMand().equals("PM")){
            mandatPersonneMandat.setEtatMand("Modification prévalidé");
    }
}else if (mandat.getCodEtatMand().equalsIgnoreCase(Constants.COD_ETAT_MAND_ANN)){ 
    mandatPersonneMandat.setEtatMand("Annulé");
}else if (mandat.getCodEtatMand().equalsIgnoreCase(Constants.COD_ETAT_MAND_HIST)){ 
    if (mandat.getCodEdemMand()==null){
        mandatPersonneMandat.setEtatMand("Historisé");
    }else if (mandat.getCodEdemMand().equals(Constants.COD_ETAT_MAND_REJ_MOD)){
        mandatPersonneMandat.setEtatMand("Rejeté suite à modification");
    }
}

}
    public void genCroText(ValueObject vo) {
          
         
        }  
    public String getNumeroTache(ValueObject vo) {
        return (Constants.CODE_RESSOURCE_GENERALE);    
        
        
    }

}
