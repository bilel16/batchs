package com.bna.smile.model.domainecaisse.traitement;

import com.bna.commun.model.DetailSessionCaisse;
import com.bna.commun.service.CURService;
import com.bna.commun.traitements.Traitement;
import com.bna.commun.util.ContextHandler;
import com.oxia.fwk.context.Context;
import com.oxia.fwk.core.IValueObject;
import com.oxia.fwk.core.ValueObject;

public class InsertDetailSessionCaisseTrt extends Traitement {
    Context context = ContextHandler.getContext();
    public InsertDetailSessionCaisseTrt() {
    }
    
    public  IValueObject perform(IValueObject vo) throws Exception {
    
     DetailSessionCaisse detailSessionCaisse = (DetailSessionCaisse)vo;
              
     try {
            CURService crudService = (CURService)context.getBean("CURService");
            crudService.create(detailSessionCaisse);
            return (detailSessionCaisse);
            
         } catch (Exception e) {
         System.out.println(e.toString());
             com.oxia.fwk.core.Error erreur = new com.oxia.fwk.core.Error();
             StringBuffer text = new StringBuffer("Erreur dans InsertDetailSessionCaisseTrt : ");
             text.append(e.toString());
             erreur.setCode("200");
             erreur.setDescription(text.toString());
             erreur.setKey("caisse");
             detailSessionCaisse.addError(erreur);
             return (detailSessionCaisse);
         }

         }

         public void genCroText(ValueObject vo) {

         }

}
