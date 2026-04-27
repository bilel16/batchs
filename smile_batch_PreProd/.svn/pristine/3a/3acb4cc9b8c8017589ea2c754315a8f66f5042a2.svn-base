package com.bna.smile.model.domainecaisse.traitement;

import com.bna.commun.model.AffectationCaisseStructure;
import com.bna.commun.traitements.Traitement;
import com.bna.commun.util.ContextHandler;
import com.bna.smile.model.domainecommun.service.CRUDservice;
import com.oxia.fwk.context.Context;
import com.oxia.fwk.core.IValueObject;
import com.oxia.fwk.core.ValueObject;

/**
 * classe pour la création d'une caisse 
 * @author Mdimagh Med Lassaad
 * @since 17/12/2007
 */
public class CreationCaisseTrt extends Traitement {
    Context context = ContextHandler.getContext();

    public CreationCaisseTrt() {
    }
    
       public  IValueObject perform(IValueObject vo) throws Exception {
      
        try {
            AffectationCaisseStructure  affectationCaisseStructure = (AffectationCaisseStructure)vo;

            CRUDservice crudService = (CRUDservice)context.getBean("crudservice");
            crudService.create(affectationCaisseStructure);
            return affectationCaisseStructure;
      
        } catch (Exception e) {
        e.printStackTrace();
        return null;
        }

    }

    public void genCroText(ValueObject vo) {

    }
}
