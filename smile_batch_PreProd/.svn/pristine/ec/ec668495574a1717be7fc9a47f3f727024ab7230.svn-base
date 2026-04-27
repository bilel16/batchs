package com.bna.smile.model.domainecontratcompte.modificationdonneesclient.traitement;

import com.bna.commun.model.PersClient;
import com.bna.commun.traitements.Traitement;
import com.bna.commun.util.ContextHandler;
import com.bna.smile.model.constant.Constants;
import com.bna.smile.model.domainecommun.service.CRUDservice;
import com.oxia.fwk.context.Context;
import com.oxia.fwk.core.IValueObject;
import com.oxia.fwk.core.ValueObject;
/**
 * @author Mdimagh Lassaad
 * @since 05/03/2008
 */
public class DeletePersClientTrt extends Traitement {
    public DeletePersClientTrt() {
    }
    
    /**
         * methode permettant la suppression d'une ligne personne client
         * 
         * @param  PersClient
         * @return PersClient
         */
    public IValueObject perform(IValueObject vo) {
        Context context = ContextHandler.getContext();
        PersClient persClient = (PersClient)vo;
        
        try{
        CRUDservice crudService = 
                (CRUDservice)context.getBean("crudservice");
            crudService.remove(persClient);
            return (persClient);
            
        } catch(Exception e){
            com.oxia.fwk.core.Error erreur = new com.oxia.fwk.core.Error();
            StringBuffer text = 
                new StringBuffer("Erreur dans DeletePersClientTrt : ");
            text.append(e.toString());
            erreur.setCode("200");
            erreur.setDescription(text.toString());
            erreur.setKey("ModifierDonnesClient");

            persClient.addError(erreur);
            return (persClient);        
            
         }
      }   
        
    public void genCroText(ValueObject vo) {
    
    }
    
    public String getNumeroTache (ValueObject vo) {
     return Constants.CODE_RESSOURCE_GENERALE;
    }
    
    
}
