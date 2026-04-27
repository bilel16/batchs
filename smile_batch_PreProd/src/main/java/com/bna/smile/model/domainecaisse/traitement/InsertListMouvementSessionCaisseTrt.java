package com.bna.smile.model.domainecaisse.traitement;

import java.util.Iterator;

import com.bna.commun.model.MouvementSessionCaisse;
import com.bna.commun.traitements.Traitement;
import com.bna.commun.util.ContextHandler;
import com.bna.smile.model.domainecommun.model.Listes;
import com.bna.smile.model.domainecommun.service.CRUDservice;
import com.oxia.fwk.context.Context;
import com.oxia.fwk.core.IValueObject;
import com.oxia.fwk.core.ValueObject;

public class InsertListMouvementSessionCaisseTrt  extends Traitement {
    Context context = ContextHandler.getContext();
    
    public InsertListMouvementSessionCaisseTrt() {
    }
    
    public  IValueObject perform(IValueObject vo) throws Exception {
    
     try {
         CRUDservice crudService = (CRUDservice)context.getBean("crudservice");

         Listes listes =(Listes)vo;

         ///*** Insertion des MouvementSessionCaisse 
         for (Iterator it = listes.getList().iterator(); it.hasNext(); ) {
             MouvementSessionCaisse mouvementSessionCaisse = (MouvementSessionCaisse)it.next();
             crudService.create(mouvementSessionCaisse);
         }
         
         return listes;
    
     } catch (Exception e) {
         com.oxia.fwk.core.Error erreur=new com.oxia.fwk.core.Error();
         erreur.setCode("Technique");
         erreur.setDescription("InsertListMouvementSessionCaisseTrt  "+e.getMessage());;
         logger.error("Exception : ",e);   
         throw new   RuntimeException(e);
     }

    }

    public void genCroText(ValueObject vo) {


    }

    
}
