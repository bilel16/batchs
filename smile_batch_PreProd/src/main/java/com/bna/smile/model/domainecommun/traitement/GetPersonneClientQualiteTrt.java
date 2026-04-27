package com.bna.smile.model.domainecommun.traitement;

import com.bna.commun.model.ContratCpt;
import com.bna.commun.model.PersClient;
import com.bna.commun.traitements.Traitement;
import com.bna.commun.util.ContextHandler;
import com.bna.smile.model.constant.Constants;
import com.bna.smile.model.domainecommun.model.ContratCptMandat;
import com.bna.smile.model.domainecommun.model.ParamListPersonneQualiteClientVo;
import com.bna.smile.model.domainecontratcompte.procuration.model.MandatRecherche;

import com.oxia.fwk.context.Context;
import com.oxia.fwk.core.ICriteria;
import com.oxia.fwk.core.IExpression;
import com.oxia.fwk.core.ISearchEngine;
import com.oxia.fwk.core.IValueObject;
import com.oxia.fwk.core.ValueObject;
import com.oxia.fwk.searchengine.SearchEngine;

import java.util.ArrayList;
import java.util.List;

/**
 *  Classe permet de recuperer l'objet client et l'objet qualite 
 *  et verifie les relations qui peuvent exister avec d'autres personnes
 *  @author Mdimagh lassaad
 *  @since 27/06/2007
 */
public class GetPersonneClientQualiteTrt extends Traitement {

    public GetPersonneClientQualiteTrt() {
    }
    /**
     * methode permettant l'execution de la recherche des personnes en relation avec
     * le client en une qualité donnée
     * @author : Mdimagh Med Lassaad
     * @param  : ParamListQualiteClientVo
     * @return : ParamListQualiteClientVo
     */
    public IValueObject perform (IValueObject vo){
       ParamListPersonneQualiteClientVo paramListQualiteClientVo = (ParamListPersonneQualiteClientVo)vo;
        try{
        Context context = ContextHandler.getContext();
        ISearchEngine searchEngine = (SearchEngine)context.getInstance().getSpringContext().getBean("searchEngine");  
        ICriteria criteria         = searchEngine.createCriteria();
        IExpression expression     = searchEngine.createExpression();
    
        /* Rechercher du contrat */
        criteria.add(expression.eq("persClientId.numSeqCli", paramListQualiteClientVo.getNumSeqPers()));
        criteria.add(expression.eq("persClientId.codQualQual", 
                                   paramListQualiteClientVo.getCodQualQual() ));
        

        List listPersonne = searchEngine.find(PersClient.class, criteria);

        if (listPersonne != null && listPersonne.size() > 0) {
            paramListQualiteClientVo.setListePersonneClient(listPersonne);
        }
        return (paramListQualiteClientVo);
    } catch (Exception e) {
        com.oxia.fwk.core.Error erreur = new com.oxia.fwk.core.Error();
                    StringBuffer text = 
                    new StringBuffer("Erreur dans GetPersonneClientQualiteTrt : ");
                    text.append(e.toString());
                    erreur.setCode("200");
                    erreur.setDescription(text.toString());
                    paramListQualiteClientVo.addError(erreur);
                    logger.error("Exception : ",e);   
                    return (paramListQualiteClientVo);
    }
 }
 
    public void genCroText(ValueObject vo) {
    
    }    
    
    public String getNumeroTache  (ValueObject vo) {
     return Constants.CODE_RESSOURCE_GENERALE;
    }
}
