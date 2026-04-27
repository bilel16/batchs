package com.bna.smile.model.domainecontratcompte.moyensPaiement.traitement;

import java.util.Iterator;
import java.util.List;

import com.bna.commun.model.DemandeCarte;
import com.bna.commun.model.StructureDomaine;
import com.bna.commun.traitements.Traitement;
import com.bna.commun.util.ContextHandler;
import com.bna.commun.util.StrHandler;
import com.bna.smile.model.constant.Constants;
import com.bna.smile.model.domainecommun.service.CRUDservice;
import com.oxia.fwk.context.Context;
import com.oxia.fwk.core.IValueObject;
import com.oxia.fwk.core.ValueObject;

/**
 * Mettre en attente une demande de carte donnée.
 * @author Ramzi
 * @param DemandeCarte 
 * @return DemandeCarte
 * @since 21/06/2007
 * 
 */
public class MiseAttenteDemandeCarteTrt  extends Traitement{
    public MiseAttenteDemandeCarteTrt() {
    }

    public IValueObject perform(IValueObject vo) throws Exception{
        DemandeCarte  demandeCarte  = (DemandeCarte )vo;
        try {
            Context context = ContextHandler.getContext();
            CRUDservice crudService = 
                (CRUDservice)context.getBean("crudservice");
            /*boolean dejaHistorise = false;
            if(demandeCarte.getCodEtatDcar().equals(Constants.COD_ETAT_DCAR_AttenteGarantie))
                dejaHistorise = true;
                
            demandeCarte.setCodEtatDcar(Constants.COD_ETAT_DCAR_AttenteGarantie);    */
            //modification de la demande            
            crudService.update(demandeCarte);   
            
            //sauvgarde de l'historique si cet opération est new (conception)
           
            InsertDetailOperDemCartTrt insertDetailOperDemCartTrt = new InsertDetailOperDemCartTrt();
            //DetailOperDemCart detailOperDemCart = (DetailOperDemCart) insertDetailOperDemCartTrt.execute(demandeCarte);
             ValueObject voRetour = (ValueObject)insertDetailOperDemCartTrt.exec(demandeCarte);
             if (voRetour == null || voRetour.hasError()) {
                    List listErreur = voRetour.getErrors();
                    for (Iterator it = listErreur.iterator(); it.hasNext(); ) {
                        com.oxia.fwk.core.Error erreur = 
                            (com.oxia.fwk.core.Error)it.next();
                        demandeCarte.addError(erreur);
                        throw new RuntimeException(); 
                    }
             }
        } catch (Exception e) {
                com.oxia.fwk.core.Error erreur=new com.oxia.fwk.core.Error();
                erreur.setCode("Technique");
                erreur.setDescription("MiseAttenteDemandeCarteTrt "+e.getMessage());;
                demandeCarte.addError(erreur); 
                logger.error("Exception : ",e);
                throw new RuntimeException(e);      
        }
        return demandeCarte;
    }
    public void genCroText(ValueObject vo) {
    
    }
    public String getNumeroTache(IValueObject vo){
       DemandeCarte  demandeCarte  = (DemandeCarte )vo;
       return demandeCarte.getTache().getTacheId().getCodOperOper().toString()+
            StrHandler.lpad(demandeCarte.getTache().getTacheId().getCodTachTach().toString(),'0',2);
    }
    public IValueObject getNumeroDomaine(IValueObject vo){
        DemandeCarte  demandeCarte  = (DemandeCarte )vo;
        StructureDomaine  structureDomaine  = new StructureDomaine();
        structureDomaine.setCodStrcStrc(demandeCarte.getContratCpt().getContratCptId().getCodStrcStrc());
        structureDomaine.setCodDomDomm(Constants.COD_DOM_CONTRATCOMPTE);
        return structureDomaine;
    
    }
  
}
