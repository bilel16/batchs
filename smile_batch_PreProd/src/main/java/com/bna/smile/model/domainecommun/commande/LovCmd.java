/** Fichier: GetContratMandatCmd.java version 1.0.0 du 19/01/2006
 * Copyright(c) 2006 BNA (www.bna.com.tn)
 * Classe: GetContratMandatCmd
 * package: com.bna.smile.model.souscriptionContratCompte.commande
 * Auteur : WEM
 */
package com.bna.smile.model.domainecommun.commande;


import com.bna.commun.util.ContextHandler;


import com.bna.smile.model.domainecommun.service.LovService;


import com.oxia.fwk.context.Context;
import com.oxia.fwk.core.ICriteria;
import com.oxia.fwk.core.IExpression;
import com.oxia.fwk.core.IMatchMode;
import com.oxia.fwk.core.ISearchEngine;

import java.lang.reflect.Field;

import java.util.List;


/** Fichier: lov.java version 1.0.0 du 19/01/2006
 * Copyright(c) 2006 BNA (www.bna.com.tn)
 * Classe: LOV
 * package: LOV
 * Auteur : WEM
 */
public class LovCmd {

    public LovCmd() {
    }

    public List execute(String fieldvalues, List listfield, Class vo) {

        Context context = ContextHandler.getContext();
        LovService lovServ = (LovService)context.getBean("lovService");
        // LovService lovServ=new LovService();        
        if (fieldvalues != null && !fieldvalues.equals(""))
            return lovServ.getListeFind(vo, fieldvalues, listfield);
        else
            return lovServ.getListeFindAll(vo);
    }
    public List execute(String fieldvalues, List listfield, Class vo, String where) {

        Context context = ContextHandler.getContext();
        LovService lovServ = (LovService)context.getBean("lovService");
        // LovService lovServ=new LovService();        
        if (fieldvalues != null && !fieldvalues.equals(""))
            return lovServ.getListeFind(vo, fieldvalues, listfield,where);
        else
            return lovServ.getListeFindAll(vo,where);
    }

}
