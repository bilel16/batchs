package com.bna.smile.model.reporting.service;

import com.bna.commun.util.ContextHandler;
import com.bna.smile.model.reporting.traitement.GetListOperMoyPayExtraitTrt;
import com.oxia.fwk.beans.service.BasicService;
import com.oxia.fwk.context.Context;


public class RetraitService extends BasicService{
    public Context context = ContextHandler.getContext();
    private GetListOperMoyPayExtraitTrt getListOperMoyPayExtraitTrt;
    
    public RetraitService() {
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public Context getContext() {
        return context;
    }

    public void setGetListOperMoyPayExtraitTrt(GetListOperMoyPayExtraitTrt getListOperMoyPayExtraitTrt) {
        this.getListOperMoyPayExtraitTrt = getListOperMoyPayExtraitTrt;
    }

    public GetListOperMoyPayExtraitTrt getGetListOperMoyPayExtraitTrt() {
        return getListOperMoyPayExtraitTrt;
    }
}
