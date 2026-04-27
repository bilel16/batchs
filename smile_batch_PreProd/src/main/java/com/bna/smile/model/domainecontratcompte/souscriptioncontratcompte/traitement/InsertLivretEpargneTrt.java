package com.bna.smile.model.domainecontratcompte.souscriptioncontratcompte.traitement;

import com.bna.commun.model.LivretEpargne;
import com.bna.commun.traitements.Traitement;
import com.bna.commun.util.ContextHandler;
import com.bna.smile.model.constant.Constants;
import com.bna.smile.model.domainecommun.service.CRUDservice;
import com.oxia.fwk.context.Context;
import com.oxia.fwk.core.IValueObject;
import com.oxia.fwk.core.ValueObject;

public class InsertLivretEpargneTrt extends Traitement{
    public InsertLivretEpargneTrt() {
    }
    /** méthode d'insertion  d'un livret d'épargne,elle prend en argument
     * le livret d'epargne
     * et retourne le livret inséré
     * @param   ValueObject : LivretEpargne
     * @return  ValueObject : LivretEpargne
     */
    public IValueObject perform(IValueObject vo) {
        LivretEpargne livretEpargne = (LivretEpargne)vo;
        try{        
        this.setCroFlag(false);    
        
            Context context = ContextHandler.getContext();
            CRUDservice crudService = 
                (CRUDservice)context.getBean("crudservice");
            crudService.create(livretEpargne);
        } catch (Exception e) {
            com.oxia.fwk.core.Error erreur = new com.oxia.fwk.core.Error();
            StringBuffer text = new StringBuffer("Erreur dans InsertLivretEpargneTrt : ");
            text.append(e.toString());
            erreur.setCode("100");
            erreur.setDescription(text.toString());
            erreur.setKey("ValiderContrat");
            livretEpargne.addError(erreur);
            logger.error("Erreur au niveau de l'agence <<" + livretEpargne.getContratCpt().getContratCptId().getCodStrcStrc() + ">>. Exception : ",e);              
            throw new RuntimeException(e);
        }
        return (livretEpargne);
       
    }
    public void genCroText(ValueObject vo) {
          
         
        }  
    public String getNumeroTache(ValueObject vo) {
         
        return (Constants.CODE_RESSOURCE_GENERALE);      
        
    }
}
