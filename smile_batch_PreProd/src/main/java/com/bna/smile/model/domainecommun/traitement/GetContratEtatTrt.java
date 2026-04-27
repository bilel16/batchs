package com.bna.smile.model.domainecommun.traitement;

import java.util.List;

import com.bna.commun.model.ContratCpt;
import com.bna.commun.model.ContratCptId;
import com.bna.commun.model.DetailEtatContrat;
import com.bna.commun.traitements.Traitement;
import com.bna.smile.model.constant.Constants;
import com.bna.smile.model.domainecommun.model.ContratCptMandat;
import com.oxia.fwk.context.Context;
import com.oxia.fwk.core.ICriteria;
import com.oxia.fwk.core.IExpression;
import com.oxia.fwk.core.ISearchEngine;
import com.oxia.fwk.core.IValueObject;
import com.oxia.fwk.core.ValueObject;

public class GetContratEtatTrt extends Traitement{
   
    public GetContratEtatTrt() {
    }
    
    /**
     * methode permettant l'affichage des informations sur un contrat donné
     * ainsi que le message sur son etat
     * @param vo : ContratCptId
     * @return ContratCptMandat
     */
    public IValueObject perform (IValueObject vo){
    
        ContratCptMandat contratCptMandat = new ContratCptMandat();

    try{
        ISearchEngine searchEngine=(ISearchEngine)Context.getInstance().getSpringContext().getBean("searchEngine");
        ICriteria criteria         = searchEngine.createCriteria();
        IExpression expression     = searchEngine.createExpression();

        ContratCptId idContratCpt = (ContratCptId)vo;
        ContratCpt contratCpt = new ContratCpt();

        contratCptMandat.setVerifEtat(true);
        contratCptMandat.setMessageEtat("");
        contratCptMandat.setContratCpt(null);
        
        /* Rechercher du contrat */
        criteria.add(expression.eq("contratCptId.codPrdPrd", idContratCpt.getCodPrdPrd()));
        criteria.add(expression.eq("contratCptId.codStrcStrc", idContratCpt.getCodStrcStrc()));
        criteria.add(expression.eq("contratCptId.numCcptCcpt", idContratCpt.getNumCcptCcpt()));

        List<ContratCpt> l = searchEngine.find(ContratCpt.class, criteria);

        if (l != null && l.size() > 0) {

            contratCpt=(ContratCpt)l.get(0);
            String etat=contratCpt.getCodEtatCcpt();
            if (etat.equalsIgnoreCase("V")){
                contratCptMandat.setVerifEtat(true);
                contratCptMandat.setMessageEtat("");
            }else{
                ICriteria criteria2 = searchEngine.createCriteria();
                IExpression expression2 = searchEngine.createExpression();
 
                criteria2.add(expression2.eq("contratCpt.contratCptId.codPrdPrd", idContratCpt.getCodPrdPrd()));
                criteria2.add(expression2.eq("contratCpt.contratCptId.codStrcStrc", idContratCpt.getCodStrcStrc()));
                criteria2.add(expression2.eq("contratCpt.contratCptId.numCcptCcpt", idContratCpt.getNumCcptCcpt()));
                criteria2.add(expression2.isNull("datFinDetc"));
                
                List<DetailEtatContrat> l2 = searchEngine.find(DetailEtatContrat.class, criteria2);

                if (l2 != null && l2.size() > 0) {
                    contratCptMandat.setVerifEtat(false);
                    DetailEtatContrat detailEtatContrat=(DetailEtatContrat)l2.get(0);
                    contratCptMandat.setMessageEtat(detailEtatContrat.getMotifEtat().getLibMotfMeta());
                }
             // *******Ajoute by hichem si le contrat_cpt ne possede pas des details etat contrat******//
				else {
					if (etat.equalsIgnoreCase("N")) {
						contratCptMandat.setVerifEtat(false);
						contratCptMandat.setMessageEtat("Compte Annulé");
					} else if (etat.equalsIgnoreCase("A")) {
						contratCptMandat.setVerifEtat(false);
						contratCptMandat.setMessageEtat("Compte en Attente");
					} else if (etat.equalsIgnoreCase("R")) {
						contratCptMandat.setVerifEtat(false);
						contratCptMandat.setMessageEtat("Compte Resilié");
					} else if (etat.equalsIgnoreCase("S")) {
						contratCptMandat.setVerifEtat(false);
						contratCptMandat.setMessageEtat("Compte Semi-Actif");
					} else if (etat.equalsIgnoreCase("B")) {
						contratCptMandat.setVerifEtat(false);
						contratCptMandat.setMessageEtat("Compte Bloqué");
					} else if (etat.equalsIgnoreCase("T")) {
						contratCptMandat.setVerifEtat(false);
						contratCptMandat.setMessageEtat("Compte Transféré au contentieux");
					}

				}
            }

            contratCptMandat.setContratCpt(contratCpt);
        }
      
            
    }   catch (Exception e) {
               com.oxia.fwk.core.Error erreur = new com.oxia.fwk.core.Error();
               StringBuffer text = 
                   new StringBuffer("Erreur dans GetContratEtatTrt : ");
               text.append(e.toString());
               erreur.setCode("200");
               erreur.setDescription(text.toString());
               erreur.setKey("GetContratEtatTrt");
               contratCptMandat.addError(erreur);
               logger.error("Exception : ",e); 
               throw new RuntimeException(e);  
             
    }
        return (contratCptMandat);
    }
    
    public void genCroText(ValueObject vo) {    
    
    }
    
    public String getNumeroTache(IValueObject vo) {
        return (Constants.CODE_RESSOURCE_GENERALE);    
    } 
    
}
