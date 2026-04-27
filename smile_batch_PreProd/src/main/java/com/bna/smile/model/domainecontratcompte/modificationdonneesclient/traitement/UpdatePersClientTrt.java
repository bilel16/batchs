package com.bna.smile.model.domainecontratcompte.modificationdonneesclient.traitement;


import com.bna.commun.model.PersClient;
import com.bna.commun.traitements.Traitement;
import com.bna.commun.util.ContextHandler;
import com.bna.smile.model.constant.Constants;
import com.bna.smile.model.domainecommun.service.CRUDservice;
import com.oxia.fwk.context.Context;
import com.oxia.fwk.core.ISearchEngine;
import com.oxia.fwk.core.IValueObject;
import com.oxia.fwk.core.ValueObject;
import com.oxia.fwk.searchengine.SearchEngine;

/**
 * @author Mdimagh Lassaad
 * @since 05/03/2008
 */
public class UpdatePersClientTrt  extends Traitement {
    public UpdatePersClientTrt() {
    }
    
    /**
         * methode permettant la MAJ d'une ligne personne client
         * 
         * @param  PersClient
         * @return PersClient
         */
    public IValueObject perform(IValueObject vo) {
        Context context = ContextHandler.getContext();
        PersClient persClientMaj = (PersClient)vo;
       
       try{
            ISearchEngine searchEngine = 
                (SearchEngine)context.getBean("searchEngine");
           
            PersClient persClientBase = (PersClient) searchEngine.get(PersClient.class,persClientMaj.getPersClientId());
            if (persClientMaj.getLibFoncPecl()!=null){
                persClientBase.setLibFoncPecl(persClientMaj.getLibFoncPecl());
            }
            if (persClientMaj.getTauxPartPecl() != null){
                persClientBase.setTauxPartPecl(persClientMaj.getTauxPartPecl());
            }
                CRUDservice crudService = 
                    (CRUDservice)context.getBean("crudservice");
                crudService.update(persClientBase);
                return (persClientBase);
         } catch(Exception e){
             com.oxia.fwk.core.Error erreur = new com.oxia.fwk.core.Error();
             StringBuffer text = 
                 new StringBuffer("Erreur dans UpdatePersClientTrt : ");
             text.append(e.toString());
             erreur.setCode("200");
             erreur.setDescription(text.toString());
             erreur.setKey("ModifierDonnesClient");

             persClientMaj.addError(erreur);
             return (persClientMaj);        
         }
      
            
        } 
        
    public void genCroText(ValueObject vo) {    
    
    }
    
    public String  perform(ValueObject vo) {
     return Constants.CODE_RESSOURCE_GENERALE;
    }
        
}
