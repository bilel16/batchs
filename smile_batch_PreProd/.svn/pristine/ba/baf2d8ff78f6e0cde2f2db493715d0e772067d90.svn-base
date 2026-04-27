package com.bna.smile.model.domainecontratcompte.procuration.traitement;

import java.util.Date;
import java.util.List;

import com.bna.commun.model.DetailRenouvellementMandat;
import com.bna.commun.model.Mandat;
import com.bna.commun.traitements.Traitement;
import com.bna.commun.util.ContextHandler;
import com.bna.smile.model.constant.Constants;
import com.bna.smile.model.domainecommun.service.CRUDservice;
import com.oxia.fwk.context.Context;
import com.oxia.fwk.core.ICriteria;
import com.oxia.fwk.core.IExpression;
import com.oxia.fwk.core.ISearchEngine;
import com.oxia.fwk.core.IValueObject;
import com.oxia.fwk.core.ValueObject;
import com.oxia.fwk.searchengine.SearchEngine;

/**
 * Classe  pour la prise en charge totale de la MAJ 
 * d'un DetailRenouvellementMandat
 * @author BOUSSEN Youssef & KRIAA Hatem
 * @date 16/05/2007
 */

public class UpdateDetailRenouvellementMandatTrt extends Traitement{

   
    public UpdateDetailRenouvellementMandatTrt() {
    }
    
    
    /**
         * methode permettant la MAJ de la derniere DetailRenouvellementMandat
         * d'une Mandat donnée
         * @param vo : Mandat
         * @return DetailRenouvellementMandat
         */
    public IValueObject perform(IValueObject vo) {
        Context context = ContextHandler.getContext();
        DetailRenouvellementMandat drm =new DetailRenouvellementMandat();
    try{
        Mandat mandat = (Mandat)vo;
        ISearchEngine searchEngine = (SearchEngine)context.getBean("searchEngine");
        ICriteria criteria = searchEngine.createCriteria();
        IExpression expression = searchEngine.createExpression();

        criteria.add(expression.eq("mandat.numMandMand", mandat.getNumMandMand()));
        criteria.add(expression.isNull("datFinDrm"));

      
        if (mandat.getCodEdemMand()!=null && mandat.getCodEdemMand().equalsIgnoreCase(Constants.COD_ETAT_MAND_ATT_PRE_REN) ){/// cas saisie renouvellement
            criteria.add(expression.eq("codEtatDrm", "X"));/// aucune maj
        }
    
        if (mandat.getCodEdemMand()!=null && mandat.getCodEdemMand().equalsIgnoreCase(Constants.COD_ETAT_MAND_ATT_VAL_REN)  ){/// cas prévalidation renouvellement
            criteria.add(expression.eq("codEtatDrm", "S"));
        }
        
        List l = searchEngine.find(DetailRenouvellementMandat.class, criteria);
        if (l != null && l.size() > 0) {
            drm = (DetailRenouvellementMandat)l.get(0);
            drm.setDatFinDrm(new Date());
            CRUDservice crudService = (CRUDservice)context.getBean("crudservice");
            crudService.update(drm);
            return (drm);
        } else
            return null;
    }
        catch (Exception e) {
            com.oxia.fwk.core.Error erreur = new com.oxia.fwk.core.Error();
            StringBuffer text = new StringBuffer("Erreur dans UpdateDetailRenouvellementMandatTrt : ");
            text.append(e.toString());
            erreur.setCode("200");
            erreur.setDescription(text.toString());
            erreur.setKey("UpdateDetailRenouvellementMandat");
            drm.addError(erreur);
            logger.error(" *** Erreur lors de  UpdateDetailRenouvellementMandat concernant l'agence "+drm.getMandat().getCodStrcMand()+" : ", e);
            return (drm);
        }
    }

    public void genCroText(ValueObject vo) {    
    
    }
    
    public String getNumeroTache(IValueObject vo) {
      return (Constants.CODE_RESSOURCE_GENERALE);        
    }
}
