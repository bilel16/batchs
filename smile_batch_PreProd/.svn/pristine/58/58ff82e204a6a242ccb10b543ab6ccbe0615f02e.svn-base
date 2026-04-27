package com.bna.smile.model.domainecontratcompte.souscriptioncontratcompte.traitement;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.bna.commun.model.CoTitulaire;
import com.bna.commun.model.CoTitulaireId;
import com.bna.commun.model.Personne;
import com.bna.commun.traitements.Traitement;
import com.bna.commun.util.ContextHandler;
import com.bna.smile.model.constant.Constants;
import com.bna.smile.model.domainecommun.model.Listes;
import com.bna.smile.model.domainecommun.service.CRUDservice;
import com.bna.smile.model.domainecontratcompte.souscriptioncontratcompte.model.ListeCotit;
import com.oxia.fwk.context.Context;
import com.oxia.fwk.core.IValueObject;
import com.oxia.fwk.core.ValueObject;

public class InsertCotitulaireTrt extends Traitement{
    

    public InsertCotitulaireTrt() {
    }


    /** méthode d'insertion  d'un cotitulaire,elle prend en argument
     * la liste des personne, le client et les informations sur le Cotitulaire
     * et retourne un valueObject : liste des Cotitulaires insérés
     * @param   ValueObject : ListeCotit
     * @return  ValueObject : Listes
     */
    public IValueObject perform(IValueObject vo) {
        Context context = ContextHandler.getContext();
        ListeCotit listeCotit = (ListeCotit)vo;
        Listes listescoti = new Listes();
        List l = new ArrayList();
        this.setCroFlag(false);
    try{
        for (Iterator it = listeCotit.getListePersonne().iterator(); 
             it.hasNext(); ) {
            Personne personne = (Personne)it.next();
            CoTitulaire cotitulaire = new CoTitulaire();
            CoTitulaireId cotitulairePK = new CoTitulaireId();
            cotitulairePK.setNumSeqCli(listeCotit.getClient().getNumSeqPers());
            cotitulairePK.setNumSeqPers(personne.getNumSeqPers());
            cotitulaire.setCoTitulaireId(cotitulairePK);
            cotitulaire.setCodTcotCoti(listeCotit.getCodTcotCoti());
            cotitulaire.setCodSigCoti(listeCotit.getCodSigCoti());

            CRUDservice crudService = 
                (CRUDservice)context.getBean("crudservice");
            crudService.create(cotitulaire);
            l.add(cotitulaire);
        }
        listescoti.setList(l);
       
        } catch (Exception e) {
                         com.oxia.fwk.core.Error erreur = new com.oxia.fwk.core.Error();
                         StringBuffer text = 
                             new StringBuffer("Erreur dans InsertCotitulaireTrt : ");
                         text.append(e.toString());
                         erreur.setCode("100");
                         erreur.setDescription(text.toString());
                         erreur.setKey("InsertCotitulaire");
                         listescoti.addError(erreur);
                         logger.error("Exception : ",e);   
                         throw new RuntimeException(e);
                         
          }  
        return (listescoti);
    }
    
    public void genCroText(ValueObject vo) {    
    
    }
    
    public String getNumeroTache(IValueObject vo) {
      return (Constants.CODE_RESSOURCE_GENERALE);        
    }
}
