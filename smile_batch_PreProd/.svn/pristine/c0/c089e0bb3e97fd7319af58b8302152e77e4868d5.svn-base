package com.bna.smile.model.domainecaisse.traitement;

import com.bna.commun.model.MouvementSessionCaisse;
import com.bna.commun.service.CURService;
import com.bna.commun.traitements.Traitement;
import com.bna.commun.util.ContextHandler;
import com.oxia.fwk.context.Context;
import com.oxia.fwk.core.IValueObject;
import com.oxia.fwk.core.ValueObject;

public class InsertMouvementSessionCaisseTrt extends Traitement {
    Context context = ContextHandler.getContext();
    
    public InsertMouvementSessionCaisseTrt() {
    }
    
    public  IValueObject perform(IValueObject vo) throws Exception {
    
    MouvementSessionCaisse mouvementSessionCaisse =(MouvementSessionCaisse) vo;
     try {
            
           
            CURService crudService = (CURService)context.getBean("CURService");
            crudService.create(mouvementSessionCaisse);
            return (mouvementSessionCaisse);
            
         } catch (Exception e) {
         System.out.println(e.toString());
             com.oxia.fwk.core.Error erreur = new com.oxia.fwk.core.Error();
             StringBuffer text = new StringBuffer("Erreur dans InsertMouvementSessionCaisseTrt : ");
             text.append(e.toString());
             erreur.setCode("200");
             erreur.setDescription(text.toString());
             erreur.setKey("caisse");
             mouvementSessionCaisse.addError(erreur);
             return (mouvementSessionCaisse);
         }

         }

         public void genCroText(ValueObject vo) {

         }

}
