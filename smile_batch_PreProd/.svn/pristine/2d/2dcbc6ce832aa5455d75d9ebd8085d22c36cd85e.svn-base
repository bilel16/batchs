package com.bna.smile.model.domainecaisse.traitement;

import java.util.Iterator;

import com.bna.commun.model.DetailSessionCaisse;
import com.bna.commun.model.MouvementSessionCaisse;
import com.bna.commun.traitements.Traitement;
import com.bna.smile.model.domainecaisse.model.ParamMvtCaisse;
import com.bna.smile.model.domainecommun.model.Listes;
import com.oxia.fwk.core.IValueObject;
import com.oxia.fwk.core.ValueObject;

public class ValidAlimentationCaisseTrt extends Traitement {
    public ValidAlimentationCaisseTrt() {
    }
    public  IValueObject perform(IValueObject vo) throws Exception {
    
        ParamMvtCaisse paramMvtCaisse = (ParamMvtCaisse)  vo;
        UpdateDetailSessionCaisseTrt updateDetailSessionCaisseTrt =new UpdateDetailSessionCaisseTrt();
        UpdateListMouvementCaisseTrt updateListMouvementCaisseTrt = new UpdateListMouvementCaisseTrt();
        InsertListMouvementSessionCaisseTrt insertListMvtCaisseTrt = new InsertListMouvementSessionCaisseTrt();
        InsertDetailSessionCaisseTrt insertDetailSessionCaisseTrt = new InsertDetailSessionCaisseTrt();
        Listes listMvt = new Listes();
        
        try{
        
       // mettre à jour les mvts envoi à 1
       if(paramMvtCaisse.getListMouvementForUpdate() != null && paramMvtCaisse.getListMouvementForUpdate().size()>0){
        listMvt.setList(paramMvtCaisse.getListMouvementForUpdate());
        updateListMouvementCaisseTrt.perform(listMvt);
       }
        
        // générer CRO
         if(paramMvtCaisse.getListMouvementForUpdate() != null 
                && paramMvtCaisse.getListMouvementForUpdate().size()>0){ 
                
                GenererCroMouvementTrt genererCroMouvementTrt = new GenererCroMouvementTrt();
                    for (Iterator it = paramMvtCaisse.getListMouvementForUpdate().iterator(); it.hasNext(); ) {
                       MouvementSessionCaisse mouvementSessionCaisse = (MouvementSessionCaisse)it.next();
                       genererCroMouvementTrt.perform(mouvementSessionCaisse);
                   }
                genererCroMouvementTrt = null;
            }
        
       // insérer les mvts alimentation
        if(paramMvtCaisse.getListMouvementForInsert() != null && paramMvtCaisse.getListMouvementForInsert().size()>0){
        listMvt.setList(paramMvtCaisse.getListMouvementForInsert());
        insertListMvtCaisseTrt.perform(listMvt);
        }
        
        
       // caisse origine :: mettr a jour detail caisse
        if(paramMvtCaisse.getListDetailCaissOriginForUpdate() != null && paramMvtCaisse.getListDetailCaissOriginForUpdate().size()>0){
            for (Iterator it = paramMvtCaisse.getListDetailCaissOriginForUpdate().iterator(); it.hasNext(); ) {
                DetailSessionCaisse detailSessionCaisse = (DetailSessionCaisse)it.next();
                updateDetailSessionCaisseTrt.perform(detailSessionCaisse);
            }
         }
            
       //caisse receptrice :: MAJ ou insert
       // insert
        if(paramMvtCaisse.getListDetailCaissReceptForInsert() != null && paramMvtCaisse.getListDetailCaissReceptForInsert().size()>0){
            for (Iterator it = paramMvtCaisse.getListDetailCaissReceptForInsert().iterator(); it.hasNext(); ) {
                DetailSessionCaisse detailSessionCaisse = (DetailSessionCaisse)it.next();
                insertDetailSessionCaisseTrt.perform(detailSessionCaisse);
            }
         }
        // update
        if(paramMvtCaisse.getListDetailCaissReceptForUpdate() != null && paramMvtCaisse.getListDetailCaissReceptForUpdate().size()>0){
                for (Iterator it = paramMvtCaisse.getListDetailCaissReceptForUpdate().iterator(); it.hasNext(); ) {
                    DetailSessionCaisse detailSessionCaisse = (DetailSessionCaisse)it.next();
                    updateDetailSessionCaisseTrt.perform(detailSessionCaisse);
                  }
          }
            
       
       
        }catch (Exception e) {
                    com.oxia.fwk.core.Error erreur=new com.oxia.fwk.core.Error();
                    erreur.setCode("Technique");
                    erreur.setDescription("ValidAlimentationCaisseTrt  "+e.getMessage());;
                    paramMvtCaisse.addError(erreur);
                    logger.error("Exception : ",e);   
                    throw new   RuntimeException(e);
            } 
        return paramMvtCaisse;
      
    }
    
    public void genCroText(ValueObject vo) {

    }
}
