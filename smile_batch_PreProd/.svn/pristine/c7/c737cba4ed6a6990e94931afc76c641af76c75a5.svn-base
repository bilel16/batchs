package com.bna.smile.model.domainecontratcompte.souscriptioncontratcompte.traitement;

import java.util.Date;

import com.bna.commun.model.DetailEtatContrat;
import com.bna.commun.model.DetailEtatContratId;
import com.bna.commun.traitements.Traitement;
import com.bna.commun.util.ContextHandler;
import com.bna.smile.model.constant.Constants;
import com.bna.smile.model.domainecommun.dao.SequenceDAO;
import com.bna.smile.model.domainecommun.service.CRUDservice;
import com.bna.smile.model.domainecontratcompte.souscriptioncontratcompte.model.ParamDetailEtatContrat;
import com.oxia.fwk.context.Context;
import com.oxia.fwk.core.IValueObject;
import com.oxia.fwk.core.ValueObject;

/**
 * Cette  methode permet d'inserer le detail etat contrat.
 * @param (contratCpt); motifEtat
 * @return detailEtatContrat : l'objet detailEtatContrat inseré
 * @author El arbi hassine
 */
public class InsertDetailEtatContratTrt extends Traitement{
    public InsertDetailEtatContratTrt() {
    }

    public IValueObject perform(IValueObject vo) {
        Context context = ContextHandler.getContext();
        CRUDservice crudService = 
            (CRUDservice)context.getBean("crudservice");

        DetailEtatContrat detailEtatContrat = new DetailEtatContrat();
        this.setCroFlag(false);
        ParamDetailEtatContrat paramDetailEtatContrat = 
            (ParamDetailEtatContrat)vo;
        try {
            
            SequenceDAO sequenceDAO = 
                (SequenceDAO)context.getBean("sequenceDAO");
            DetailEtatContratId detailEtatContratId = 
                new DetailEtatContratId();
            
            detailEtatContratId.setCodPrdPrd(paramDetailEtatContrat.getContratCpt().getContratCptId().getCodPrdPrd());
            detailEtatContratId.setCodStrcStrc(paramDetailEtatContrat.getContratCpt().getContratCptId().getCodStrcStrc());
            detailEtatContratId.setNumCcptCcpt(paramDetailEtatContrat.getContratCpt().getContratCptId().getNumCcptCcpt());
            detailEtatContratId.setNumDetcDetc(sequenceDAO.getSequenceDetailEtatContrat());

            detailEtatContrat.setDetailEtatContratId(detailEtatContratId);
            detailEtatContrat.setMotifEtat(paramDetailEtatContrat.getMotifEtat());

            detailEtatContrat.setContratCpt(paramDetailEtatContrat.getContratCpt());
            detailEtatContrat.setDatDebDetc(new Date());

            crudService.create(detailEtatContrat);
            return detailEtatContrat;
            } catch (Exception e) {
                             com.oxia.fwk.core.Error erreur = new com.oxia.fwk.core.Error();
                             StringBuffer text = 
                                 new StringBuffer("Erreur dans InsertDetailEtatContratTrt : ");
                             text.append(e.toString());
                             erreur.setCode("100");
                             erreur.setDescription(text.toString());
                             erreur.setKey("InsertDetailEtatContrat");
                             detailEtatContrat.addError(erreur);
                             logger.error("Erreur au niveau de l'agence <<" + paramDetailEtatContrat.getContratCpt().getContratCptId().getCodStrcStrc() + ">>. Exception : ",e);            
                             return (detailEtatContrat);
              }    

    }
    
    public void genCroText(ValueObject vo) {    
    
    }
    
    public String getNumeroTache(IValueObject vo) {
      return (Constants.CODE_RESSOURCE_GENERALE);        
    }  
}
