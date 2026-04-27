package com.bna.smile.model.domainecommun.service;

import com.bna.commun.model.ExonerationCltTva;
import com.bna.commun.util.ContextHandler;
import com.bna.smile.model.domainecommun.traitement.CreatExonerationCltTvaTrt;
import com.bna.smile.model.domainecommun.traitement.GetListExonerationTvaTrt;
import com.bna.smile.model.domainecommun.traitement.InsertExonerationTvaTrt;
import com.bna.smile.model.domainecommun.traitement.InsertTraceExoTvaTrt;
import com.bna.smile.model.domainecommun.traitement.UpdateExonerationCltTvaTrt;
import com.bna.smile.model.domainecommun.traitement.UpdateExonerationTvaTrt;

import com.oxia.fwk.beans.service.BasicService;
import com.oxia.fwk.context.Context;
import com.oxia.fwk.core.IValueObject;


public class ExonerationTVAService extends BasicService{
    public Context context = ContextHandler.getContext();
    private GetListExonerationTvaTrt getListExonerationTvaTrt;
    
    public ExonerationTVAService() {
    }
   public IValueObject creatExonerationCltTva(IValueObject vo) {
        CreatExonerationCltTvaTrt creatExonerationCltTvaTrt= new CreatExonerationCltTvaTrt();
           return (creatExonerationCltTvaTrt.exec(vo));
    }

    public IValueObject insertTraceExonerationTva(IValueObject vo) {
        InsertTraceExoTvaTrt insertTraceExoTvaTrt= new InsertTraceExoTvaTrt();
           return (insertTraceExoTvaTrt.exec(vo));
    }
    public IValueObject getListExonerationTva(IValueObject vo) {
    if (getListExonerationTvaTrt != null){
        return (getListExonerationTvaTrt.exec(vo));
    }else {
       return null;
    }
 }
 public IValueObject updateExonerationTva(IValueObject vo){
     UpdateExonerationCltTvaTrt updateExonerationCltTvaTrt = new UpdateExonerationCltTvaTrt();
     if (updateExonerationCltTvaTrt != null){
         return (updateExonerationCltTvaTrt.exec(vo));
     }else {
        return null;
     }  
 }

    public void setGetListExonerationTvaTrt(GetListExonerationTvaTrt getListExonerationTvaTrt) {
        this.getListExonerationTvaTrt = getListExonerationTvaTrt;
    }

    public GetListExonerationTvaTrt getGetListExonerationTvaTrt() {
        return getListExonerationTvaTrt;
    }

}
