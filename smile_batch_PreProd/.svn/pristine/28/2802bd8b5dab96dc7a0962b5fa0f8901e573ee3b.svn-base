package com.bna.smile.model.domaineguichet.traitement;

import com.bna.commun.model.MontantMiseDiposition;
import com.bna.commun.traitements.Traitement;
import com.bna.commun.util.ContextHandler;

import com.bna.smile.model.constant.Constants;
import com.bna.smile.model.domainecommun.model.Listes;
import com.bna.smile.model.domainecommun.model.PersonneStrc;

import com.bna.smile.model.domainecommun.service.CRUDservice;

import com.oxia.fwk.context.Context;
import com.oxia.fwk.core.ICriteria;
import com.oxia.fwk.core.IExpression;
import com.oxia.fwk.core.ISearchEngine;
import com.oxia.fwk.core.IValueObject;
import com.oxia.fwk.core.ValueObject;
import com.oxia.fwk.searchengine.SearchEngine;

import java.util.ArrayList;
import java.util.List;

public class GetMontantMADByIdPersTrt extends Traitement{
    public Context context = ContextHandler.getContext();

    public GetMontantMADByIdPersTrt() {
    }
    /**
     * Methode permet trouver les montants mis a disposition en cours 
     * destinés à une personne donnée
     * @param vo : PersonneStrc
     * @return   : Liste of montant mise à disposition
     * @autor    : Youssef BOUSSEN 
     */

     public ValueObject perform(IValueObject vo) { 
    
        this.setCroFlag(false);      

        Listes listes = new Listes();

        try{

            PersonneStrc personneStrc = (PersonneStrc)vo;
            List listMAD = new ArrayList();
            ISearchEngine searchEngine = (SearchEngine)context.getBean("searchEngine"); 
            ICriteria criteria         = searchEngine.createCriteria();
            IExpression expression     = searchEngine.createExpression();

        /* Rechercher de la liste des MAD en cours*/
            criteria.add(expression.eq("codTpceMmad",personneStrc.getCodTpceTpce()));
            criteria.add(expression.eq("numPceMmad",personneStrc.getNumPcePers()));
            criteria.add(expression.isNull("datRetMmad"));
            criteria.add(expression.eq("montantMiseDipositionId.codTypeMmad",personneStrc.getCode()));// RMD, RMG ou RCA

            listMAD = searchEngine.find(MontantMiseDiposition.class, criteria);
            if (listMAD != null && listMAD.size() > 0) {/* mises a disposition */
                listes.setList(listMAD);
            }
        return (listes);
        }
           catch (Exception e) {
              com.oxia.fwk.core.Error erreur = new com.oxia.fwk.core.Error();
              StringBuffer text = 
                  new StringBuffer("Erreur dans GetMontantMADByIdPers : ");
              text.append(e.toString());
              erreur.setCode("400");
              erreur.setDescription(text.toString());
              erreur.setKey("GetMontantMADByIdPers");
              listes.addError(erreur);
              return (listes);
          }

    
    }
    public void genCroText(ValueObject vo) {
    }
    
    public String getNumeroTache(IValueObject vo){
       return Constants.CODE_RESSOURCE_GENERALE;   
    }
}
