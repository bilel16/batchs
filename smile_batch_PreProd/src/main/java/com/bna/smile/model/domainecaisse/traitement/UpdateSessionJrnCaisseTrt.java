package com.bna.smile.model.domainecaisse.traitement;

import com.bna.commun.model.SessionJrnCaisse;
import com.bna.commun.traitements.Traitement;
import com.bna.commun.util.ContextHandler;
import com.bna.smile.model.domainecommun.service.CRUDservice;
import com.oxia.fwk.context.Context;
import com.oxia.fwk.core.IValueObject;
import com.oxia.fwk.core.ValueObject;

public class UpdateSessionJrnCaisseTrt extends Traitement{
    public UpdateSessionJrnCaisseTrt() {
    }
    
    /**
     * MAJ d'une SessionJrnCaisse .
     * @param SessionJrnCaisse
     * @return SessionJrnCaisse
     * 
     */

    public IValueObject perform (IValueObject vo ) {     
     
    Context context = ContextHandler.getContext();
    SessionJrnCaisse sessionJrnCaisse = (SessionJrnCaisse)vo;             
             
    try{ 

        CRUDservice crudService = (CRUDservice)context.getBean("crudservice"); 
            
        if(sessionJrnCaisse!=null){
            crudService.update(sessionJrnCaisse);  
        }
            
    }
    catch (Exception e) {
                com.oxia.fwk.core.Error erreur=new com.oxia.fwk.core.Error();
                erreur.setCode("Technique");
                erreur.setDescription("UpdateSessionJrnCaisseTrt  "+e.getMessage());
                sessionJrnCaisse.addError(erreur);

                throw new RuntimeException(e);
        } 
        return (sessionJrnCaisse);
    }
    
    public void genCroText(ValueObject vo) {
            
    }   


}
