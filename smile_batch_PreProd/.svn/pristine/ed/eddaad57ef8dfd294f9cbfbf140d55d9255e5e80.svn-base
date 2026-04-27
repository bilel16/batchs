package com.bna.smile.model.domainecontratcompte.moyensPaiement.traitement;


import com.bna.commun.model.DemandeChequeMandatPersonne;
import com.bna.commun.traitements.Traitement;
import com.bna.commun.util.ContextHandler;
import com.bna.smile.model.constant.Constants;
import com.bna.smile.model.domainecommun.service.CRUDservice;
import com.oxia.fwk.context.Context;
import com.oxia.fwk.core.IValueObject;
import com.oxia.fwk.core.ValueObject;

public class InsertDemandeChequeMandatPersonneTrt extends Traitement{
    

    public InsertDemandeChequeMandatPersonneTrt() {
    }


    /** méthode d'insertion  d'une demandeChequeMandatPersonne
     * et retourne le même objet inséré
     * @param   ValueObject : DemandeChequeMandatPersonne
     * @return  ValueObject : DemandeChequeMandatPersonne
     */
    public IValueObject perform(IValueObject vo) {
        Context context = ContextHandler.getContext();
        DemandeChequeMandatPersonne demandeChequeMandatPersonne = 
            (DemandeChequeMandatPersonne)vo;
        try {
            this.setCroFlag(false);
            CRUDservice crudservice = 
                (CRUDservice)context.getBean("crudservice");
            crudservice.create(demandeChequeMandatPersonne);

        } catch (Exception e) {
            com.oxia.fwk.core.Error erreur = new com.oxia.fwk.core.Error();
            StringBuffer text = 
                new StringBuffer("Erreur dans InsertDemandeChequeMandatPersonneTrt : ");
            text.append(e.toString());
            erreur.setCode("100");
            erreur.setDescription(text.toString());
            erreur.setKey("InsertDemandeChequeMandatPersonne");
            demandeChequeMandatPersonne.addError(erreur);
            logger.error("Erreur au niveau de l'agence <<" + demandeChequeMandatPersonne.getDemandeCheque().getContratCpt().getContratCptId().getCodStrcStrc() + ">>. Exception : ",e);                
            throw new RuntimeException(e);
        }
        return (demandeChequeMandatPersonne);
    }
    public void genCroText(ValueObject vo) {    
    
    }
    
    public String getNumeroTache(IValueObject vo) {
      return (Constants.CODE_RESSOURCE_GENERALE);        
    }  
}
