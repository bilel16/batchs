package com.bna.smile.model.banqueAssurance.traitement;

import java.util.Iterator;

import com.bna.commun.model.Assureur;
import com.bna.commun.model.StructureDomaine;
import com.bna.commun.model.TarifAssVie;
import com.bna.commun.traitements.Traitement;
import com.bna.commun.util.ContextHandler;
import com.bna.smile.model.constant.Constants;
import com.bna.smile.model.domainecommun.service.CRUDservice;
import com.oxia.fwk.context.Context;
import com.oxia.fwk.core.IValueObject;
import com.oxia.fwk.core.ValueObject;

public class InsertAssureurTrt extends Traitement{
    public InsertAssureurTrt() {
    }
    public IValueObject perform(IValueObject vo) {
      
        Assureur assureur = (Assureur)vo;
         
       try{
            this.setCroFlag(false);
            Context context = ContextHandler.getContext();
            CRUDservice crudService = (CRUDservice)context.getBean("crudservice");           
            crudService.create(assureur);
        
           for (Iterator it = assureur.getTarifAssVie().iterator(); it.hasNext(); ) {
                TarifAssVie tarifAssVie = (TarifAssVie)it.next();
                tarifAssVie.setAssureur(assureur);
                if(tarifAssVie.getProduit().getCodPrdPrd() != null)
                  crudService.create(tarifAssVie);
            }

        } catch (Exception e) {
            com.oxia.fwk.core.Error erreur = new com.oxia.fwk.core.Error();
            StringBuffer text = 
                new StringBuffer("Erreur dans InsertAssureurTrt : ");
            text.append(e.toString());
            erreur.setCode("100");
            erreur.setDescription(text.toString());
            erreur.setKey("InsertAssureurTrt");
            assureur.addError(erreur);
            logger.error("Exception : ",e);   
            throw new RuntimeException(e);  
            
        }  
        return (assureur);
    }


    public void genCroText(ValueObject vo) {    
    
    }

    public String  getNumeroTache (IValueObject vo) {
          return (Constants.COD_OPER_GEST_ASSUR_VIE.toString()+Constants.COD_TACH_PEC);     
      }
    public IValueObject getNumeroDomaine(IValueObject vo){
        StructureDomaine structureDomaine = new StructureDomaine();
        Assureur assureur = (Assureur)vo;
        structureDomaine.setCodDomDomm(Constants.COD_DOM_CLIENT);
        structureDomaine.setCodStrcStrc(assureur.getContratCpt().getContratCptId().getCodStrcStrc());
        return structureDomaine;
    }

}
