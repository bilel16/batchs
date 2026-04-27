package com.bna.smile.model.domainecontratcompte.procuration.traitement;

import java.util.Date;

import com.bna.commun.model.DetailMandatPersonne;
import com.bna.commun.model.DetailMandatPersonneId;
import com.bna.commun.model.MandatPersonne;
import com.bna.commun.traitements.Traitement;
import com.bna.commun.util.ContextHandler;
import com.bna.smile.model.constant.Constants;
import com.bna.smile.model.domainecommun.service.CRUDservice;
import com.bna.smile.model.domainecontratcompte.procuration.dao.MandatDAO;
import com.oxia.fwk.context.Context;
import com.oxia.fwk.core.IValueObject;
import com.oxia.fwk.core.ValueObject;

public class InsertDetailMandatPersonneTrt extends Traitement{
   // public Context context = ContextHandler.getContext();
   // private static final Logger logger = Logger.getLogger(InsertDetailMandatPersonneTrt.class);

    public InsertDetailMandatPersonneTrt() {
    }

    /**
     * methode permettant l'insertion d'un nouveau DetailMandatPersonne
     * et de fermer l'ancien (date fin = date systeme) s'il existe
     * @param vo : MandatPersonne
     * @return DetailMandatPersonne
     */
    public IValueObject perform(IValueObject vo) {
    
        Context context = ContextHandler.getContext();
        MandatPersonne mandatPersonne = (MandatPersonne)vo;
        DetailMandatPersonne detailMandatPersonne = new DetailMandatPersonne();
        try{
        /* si le mandatPersonne existe deja */
        if (mandatPersonne.getDetailMandatPersonnes() != null && 
            mandatPersonne.getDetailMandatPersonnes().size() > 0) {
            /* Update le detailMandatPersonne precedent */
            UpdateDetailMandatPersonneTrt updateDetailMandatPersonneTrt = 
                new UpdateDetailMandatPersonneTrt();
            DetailMandatPersonne dmp = 
                (DetailMandatPersonne)updateDetailMandatPersonneTrt.exec(mandatPersonne);
        }
        /* insertion du DetailMandatPersonne dans la BD */

        
        MandatDAO mandatDAO = (MandatDAO)context.getBean("mandatDAO");

        DetailMandatPersonneId detailMandatPersonneId = 
            new DetailMandatPersonneId();
        detailMandatPersonneId.setNumMandMand(mandatPersonne.getMandatPersonneId().getNumMandMand());
        detailMandatPersonneId.setNumSeqPers(mandatPersonne.getMandatPersonneId().getNumSeqPers());
        detailMandatPersonneId.setNumDmpDmp(mandatDAO.getSequenceDetailMandatPersonne());
        detailMandatPersonne.setDetailMandatPersonneId(detailMandatPersonneId);
        detailMandatPersonne.setDatDebDmp(new Date());

        CRUDservice crudService = (CRUDservice)context.getBean("crudservice");
        crudService.create(detailMandatPersonne);

        return (detailMandatPersonne);
    
    }catch (Exception e) {
            com.oxia.fwk.core.Error erreur = new com.oxia.fwk.core.Error();
            StringBuffer text = 
                new StringBuffer("Erreur InsertDetailMandatPersonneTrt  ");
            text.append(e.toString());
            erreur.setCode("500");
            erreur.setDescription(text.toString());
            erreur.setKey("InsertDetailMandatPersonneTrt");
            detailMandatPersonne.addError(erreur);
            logger.error(" *** Erreur lors de InsertDetailMandatPersonneTrt concernant l'agence "+mandatPersonne.getMandat().getCodStrcMand()+" : ", e);
            return (detailMandatPersonne);
        } 
}


    public void genCroText(ValueObject vo) {    
    
    }
    
    public String getNumeroTache(IValueObject vo) {
      return (Constants.CODE_RESSOURCE_GENERALE);        
    }
}