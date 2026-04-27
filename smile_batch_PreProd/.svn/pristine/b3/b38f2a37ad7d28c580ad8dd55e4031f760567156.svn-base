package com.bna.smile.model.domainecaisse.traitement;

import java.util.Iterator;

import com.bna.commun.model.MouvementSessionCaisse;
import com.bna.commun.traitements.Traitement;
import com.bna.smile.model.constant.Constants;
import com.bna.smile.model.domainecommun.model.Listes;
import com.oxia.fwk.core.IValueObject;
import com.oxia.fwk.core.ValueObject;

public class UpdateListMouvementCaisseTrt extends Traitement{
    public UpdateListMouvementCaisseTrt() {
    }
    /**
     * MAJ d'un MouvementSessionCaisse .
     * @param MouvementSessionCaisse
     * @return MouvementSessionCaisse
     * 
     */

    public IValueObject perform (IValueObject vo ) {     
     
        Listes listeMouvementSessionCaisse = (Listes)vo;
        
        try{ 
             this.setCroFlag(false);  
            
            if(listeMouvementSessionCaisse !=null 
                && listeMouvementSessionCaisse.getList() != null 
                && listeMouvementSessionCaisse.getList().size()>0){ 
                
                   UpdateMouvementCaisseTrt updateMouvementCaisseTrt = new UpdateMouvementCaisseTrt();
                   for (Iterator it = listeMouvementSessionCaisse.getList().iterator(); it.hasNext(); ) {
                       MouvementSessionCaisse mouvementSessionCaisse = (MouvementSessionCaisse)it.next();
                       updateMouvementCaisseTrt.perform(mouvementSessionCaisse);
                   }
                updateMouvementCaisseTrt = null;
            }         
               
           }catch (Exception e) {
                 com.oxia.fwk.core.Error erreur=new com.oxia.fwk.core.Error();
                 erreur.setCode("Technique");
                 erreur.setDescription("UpdateMouvementCaisseTrt  "+e.getMessage());;
                 listeMouvementSessionCaisse.addError(erreur);
                 logger.error("Exception : ",e);   
                 throw new RuntimeException(e);
         } 
         return (listeMouvementSessionCaisse);
     
    }
    
    public void genCroText(ValueObject vo) {
            
    } 
    public String getNumeroTache(ValueObject vo) {
        return (Constants.CODE_RESSOURCE_GENERALE);
    }
    
    /* public IValueObject getNumeroDomaine(IValueObject vo){
       return null;
    }*/
}
