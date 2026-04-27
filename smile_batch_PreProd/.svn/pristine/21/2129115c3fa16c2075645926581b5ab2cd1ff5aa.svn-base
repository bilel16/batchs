package com.bna.smile.model.domainecontratcompte.moyensPaiement.traitement;

import java.util.ArrayList;
import java.util.List;

import com.bna.commun.model.DemandeCarte;
import com.bna.commun.model.StructureDomaine;
import com.bna.commun.traitements.Traitement;
import com.bna.commun.util.StrHandler;
import com.bna.smile.model.constant.Constants;
import com.bna.smile.model.domainecontratcompte.moyensPaiement.model.DemandeCarteSignataire;
import com.oxia.fwk.core.IValueObject;
import com.oxia.fwk.core.ValueObject;

/**
 * demande de modification plafond carte donnée.
 * @author Ramzi
 * @param DemandeCarteSignataire
 * @return DemandeCarte
 * @since 08/04/2009
 * 
 */
public class DemandeModifPlafondTrt extends Traitement{
    public DemandeModifPlafondTrt() {
    }
    public IValueObject perform(IValueObject vo) throws Exception{
        DemandeCarte  demandeCarte  = (DemandeCarte)vo;        
        try {
            PecDemandeCarteTrt pecDemandeCarteTrt = new PecDemandeCarteTrt(); 
            DemandeCarteSignataire  demandeCarteSignataire  = new DemandeCarteSignataire();
            demandeCarteSignataire.setDemandeCarte(demandeCarte);
            List newList = new ArrayList();
            newList.addAll(demandeCarte.getDemandeCarteMandatPersonnes());
            demandeCarteSignataire.setSignataire(newList);
            
            //insertion de la demande de remplacement    
            demandeCarte=(DemandeCarte)pecDemandeCarteTrt.exec(demandeCarteSignataire);
            
        } catch (Exception e) {
                com.oxia.fwk.core.Error erreur=new com.oxia.fwk.core.Error();
                erreur.setCode("Technique");
                erreur.setDescription("DemandeModifPlafondTrt "+e.getMessage());;
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
        DemandeCarte  demandeCarte  = (DemandeCarte)vo;    
        StructureDomaine  structureDomaine  = new StructureDomaine();
        structureDomaine.setCodStrcStrc(demandeCarte.getContratCpt().getContratCptId().getCodStrcStrc());
        structureDomaine.setCodDomDomm(Constants.COD_DOM_CONTRATCOMPTE);
        return structureDomaine;
    
    }
  
}
