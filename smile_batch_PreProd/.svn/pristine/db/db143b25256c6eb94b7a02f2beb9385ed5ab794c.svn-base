package com.bna.smile.model.moyenPayement.service;

import com.bna.smile.model.moyenPayement.traitement.GetAccuseByStructureDateTrt;
import com.bna.smile.model.moyenPayement.traitement.GetPrelevementByStructureDateTrt;
import com.bna.smile.model.moyenPayement.traitement.GetVirementByRibDateTrt;
import com.bna.smile.model.moyenPayement.traitement.GetVirementByStructureDateTrt;
import com.bna.smile.model.moyenPayement.traitement.InsertRefConsultationTrt;
import com.oxia.fwk.beans.service.BasicService;
import com.oxia.fwk.core.IValueObject;


public class MoyensPayementService extends BasicService{
    private GetAccuseByStructureDateTrt getAccuseByStructureDateTrt;
    private InsertRefConsultationTrt insertRefConsultationTrt;  
    private GetVirementByStructureDateTrt getVirementByStructureDateTrt;
    private GetVirementByRibDateTrt getVirementByRibDateTrt;
    private GetPrelevementByStructureDateTrt getPrelevementByStructureDateTrt;


    public IValueObject getPrelevementByStructureDateService(IValueObject vo) {
    return (getPrelevementByStructureDateTrt.perform(vo));
    }
  
  
    public IValueObject getAccuseByStructureDateService(IValueObject vo) {
    return (getAccuseByStructureDateTrt.perform(vo));
    }
    public IValueObject insertRefConsultationService(IValueObject vo) {
    return (insertRefConsultationTrt.perform(vo));

    }
    
    
    
    public IValueObject getVirementByStructureDateService(IValueObject vo) {
    return (getVirementByStructureDateTrt.perform(vo));
    }
    public IValueObject getVirementByRibDateService(IValueObject vo) {
    return (getVirementByRibDateTrt.perform(vo));
    }
      
    public void setGetAccuseByStructureDateTrt(GetAccuseByStructureDateTrt getAccuseByStructureDateTrt) {
        this.getAccuseByStructureDateTrt = getAccuseByStructureDateTrt;
    }
    public GetAccuseByStructureDateTrt getGetAccuseByStructureDateTrt() {
        return getAccuseByStructureDateTrt;
    }

    public void setInsertRefConsultationTrt(InsertRefConsultationTrt insertRefConsultationTrt) {
        this.insertRefConsultationTrt = insertRefConsultationTrt;
    }

    public InsertRefConsultationTrt getInsertRefConsultationTrt() {
        return insertRefConsultationTrt;
    }

    public void setGetVirementByStructureDateTrt(GetVirementByStructureDateTrt getVirementByStructureDateTrt) {
        this.getVirementByStructureDateTrt = getVirementByStructureDateTrt;
    }

    public GetVirementByStructureDateTrt getGetVirementByStructureDateTrt() {
        return getVirementByStructureDateTrt;
    }

    public void setGetVirementByRibDateTrt(GetVirementByRibDateTrt getVirementByRibDateTrt) {
        this.getVirementByRibDateTrt = getVirementByRibDateTrt;
    }

    public GetVirementByRibDateTrt getGetVirementByRibDateTrt() {
        return getVirementByRibDateTrt;
    }

    public void setGetPrelevementByStructureDateTrt(GetPrelevementByStructureDateTrt getPrelevementByStructureDateTrt) {
        this.getPrelevementByStructureDateTrt = getPrelevementByStructureDateTrt;
    }

    public GetPrelevementByStructureDateTrt getGetPrelevementByStructureDateTrt() {
        return getPrelevementByStructureDateTrt;
    }
}
