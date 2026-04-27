package com.bna.smile.model.domainecommun.traitement;

import com.bna.commun.model.ContratCpt;
import com.bna.commun.model.ContratCptId;
import com.bna.commun.model.DetailEtatContrat;
import com.bna.commun.model.Tranchecci;
import com.bna.commun.traitements.Traitement;
import com.bna.commun.util.ContextHandler;
import com.bna.commun.util.StrHandler;
import com.bna.smile.model.constant.Constants;
import com.bna.smile.model.domainecommun.dao.CreditDAO;
import com.bna.smile.model.domainecommun.model.ContratCptMandat;

import com.bna.smile.model.domainecommun.model.Listes;

import com.bna.smile.model.statistique.dao.StatistiqueOppositionDAO;

import com.oxia.fwk.context.Context;
import com.oxia.fwk.core.ICriteria;
import com.oxia.fwk.core.IExpression;
import com.oxia.fwk.core.ISearchEngine;
import com.oxia.fwk.core.IValueObject;
import com.oxia.fwk.core.ValueObject;

import java.util.List;

public class GetListCredTrt extends Traitement{
    public GetListCredTrt() {
    }
    
    public IValueObject perform (IValueObject vo){
    
        ContratCptMandat contratCptMandat = new ContratCptMandat();
        Listes liste=new Listes();
        Context context = ContextHandler.getContext();
    try{
       Long nombreCredit=Long.valueOf(0);

        ContratCptId idContratCpt = (ContratCptId)vo;
        contratCptMandat.setVerifEtat(true);
        contratCptMandat.setMessageEtat("");
        contratCptMandat.setContratCpt(null);
        String numcpt=StrHandler.lpad(idContratCpt.getCodStrcStrc().toString(),'0',3)+
        StrHandler.lpad(idContratCpt.getCodPrdPrd().toString(),'0',4)+
        StrHandler.lpad(idContratCpt.getNumCcptCcpt().toString(),'0',6);
        /* Rechercher du contrat */
        CreditDAO creditDAO = 
            (CreditDAO)context.getBean("creditDAO");
        nombreCredit=creditDAO.getNombreCredit(numcpt);
       
        liste.setNombre(nombreCredit);
      
            
    }   catch (Exception e) {
               com.oxia.fwk.core.Error erreur = new com.oxia.fwk.core.Error();
               StringBuffer text = 
                   new StringBuffer("Erreur dans GetListCredTrt : ");
               text.append(e.toString());
               erreur.setCode("200");
               erreur.setDescription(text.toString());
               erreur.setKey("GetListCredTrt");
               liste.addError(erreur);
               logger.error("Exception : ",e); 
               throw new RuntimeException(e);  
             
    }
        return (liste);
    }
    
    public void genCroText(ValueObject vo) {    
    
    }
    
    public String getNumeroTache(IValueObject vo) {
        return (Constants.CODE_RESSOURCE_GENERALE);    
    } 
}
