package com.bna.smile.model.moyenPayement.traitement;

import com.bna.commun.model.Consultation;
import com.bna.commun.model.ConsultationRapport;
import com.bna.commun.model.Personnel;
import com.bna.commun.traitements.Traitement;
import com.bna.commun.util.ContextHandler;
import com.bna.commun.util.DateHandler;
import com.bna.smile.model.domainecommun.service.CRUDservice;
import com.bna.smile.model.moyenPayement.dao.AccuseDAO;
import com.bna.smile.model.moyenPayement.model.ParamAccuse;
import com.oxia.fwk.context.Context;
import com.oxia.fwk.core.IValueObject;
import com.oxia.fwk.core.ValueObject;


public class InsertRefConsultationTrt extends Traitement{
    public IValueObject perform(IValueObject vo)  {
        ParamAccuse paramAccuseVo = (ParamAccuse)vo;
        Context context = ContextHandler.getContext();
        AccuseDAO accuseDAO=(AccuseDAO)context.getBean("accuseDAO");
        Consultation consultation =new Consultation();
        CRUDservice crudService = (CRUDservice)context.getBean("crudservice");
        try {
            
            
            Long refConsultation = accuseDAO.getSequenceRefConsultation();
            Long idRapport=accuseDAO.getRapportIdByName(paramAccuseVo.getNomRapport());
            ConsultationRapport rapport=new ConsultationRapport();//accuseDAO.getRapportByName(paramAccuseVo.getNomRapport());
            Personnel user=new Personnel();
            consultation.setRefConsultation(refConsultation);
            consultation.setDateConsultation(DateHandler.timeJour());
            consultation.setDateJourneeComptable(paramAccuseVo.getDateJourneeComptable());
            rapport.setIdRapport(idRapport);
            user.setNumMatrUser(paramAccuseVo.getNumMatrUser());
            consultation.setPersonnel(user);
            consultation.setRapport(rapport);
            
            crudService.create(consultation);
            paramAccuseVo.setRefConsultation(refConsultation);
            
        }catch (Exception e){
        com.oxia.fwk.core.Error erreur = new com.oxia.fwk.core.Error();
        erreur.setCode("Technique");
        erreur.setDescription("InsertRefConsultationTrt " + e.getMessage());
        paramAccuseVo.addError(erreur);
        throw new   RuntimeException(e);
    }
    return (paramAccuseVo);
    }
    public void genCroText(ValueObject vo) {

    }


}
