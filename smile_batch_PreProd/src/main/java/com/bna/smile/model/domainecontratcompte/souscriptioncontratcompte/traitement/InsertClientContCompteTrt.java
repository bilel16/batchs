package com.bna.smile.model.domainecontratcompte.souscriptioncontratcompte.traitement;

import com.bna.commun.model.ContratCpt;
import com.bna.commun.model.ContratCptId;
import com.bna.commun.traitements.Traitement;
import com.bna.commun.util.ContextHandler;
import com.bna.smile.model.domainecommun.service.CRUDservice;
import com.bna.smile.model.domainecontratcompte.souscriptioncontratcompte.model.ParamCompte519;
import com.oxia.fwk.context.Context;
import com.oxia.fwk.core.IValueObject;
import com.oxia.fwk.core.ValueObject;

public class InsertClientContCompteTrt  extends Traitement {
    public InsertClientContCompteTrt() {
    }
    /**
     * Methode execute
     * @param vo Objet : ParamCompte519
     * @return   Objet : ContratCpt
     */
    public IValueObject perform(IValueObject vo) {

        ParamCompte519 paramCompte519 = (ParamCompte519)vo;
        Context context = ContextHandler.getContext();
        ContratCpt compteDav = new ContratCpt();
        compteDav = paramCompte519.getContratCpt();
        ContratCpt compte519 = new ContratCpt();
        ContratCptId contratCptIdCompte519 = new ContratCptId();
    try{
      if(this.checkClotureJournee()){
        this.setCroFlag(false);  
        
        contratCptIdCompte519.setCodPrdPrd(compteDav.getContratCptId().getCodPrdPrd());
        contratCptIdCompte519.setCodStrcStrc(compteDav.getContratCptId().getCodStrcStrc());
        contratCptIdCompte519.setNumCcptCcpt(compteDav.getContratCptId().getNumCcptCcpt());
        compte519.setContratCptId(contratCptIdCompte519);
        compte519.setClient(compteDav.getClient());
        compte519.setDevise(compteDav.getDevise());
        compte519.setDatOuvCcpt(compteDav.getDatOuvCcpt());
        compte519.setCodEtatCcpt(compteDav.getCodEtatCcpt());
        compte519.setAdresseCorresp(compteDav.getAdresseCorresp());
        compte519.setNomIntiCcpt(compteDav.getNomIntiCcpt());
        compte519.setMontSoldCcpt(compteDav.getMontSoldCcpt());     
        compte519.setMontAutCcpt(compteDav.getMontAutCcpt());
        compte519.setMontBlocCcpt(compteDav.getMontBlocCcpt());
        compte519.setMontSdevCcpt(compteDav.getMontSdevCcpt());
        compte519.setMontSminCcpt(compteDav.getMontSminCcpt());
        compte519.setMontBdevCcpt(compteDav.getMontBdevCcpt());
 
        CRUDservice crudservice = (CRUDservice)context.getBean("crudservice");
        crudservice.create(compte519);
 
         // Fin controle cloture journee 
          }else{
                      com.oxia.fwk.core.Error erreur = new com.oxia.fwk.core.Error();
                      StringBuffer text = new StringBuffer("La journée est déja clôturée...");            
                      erreur.setCode("100");
                      erreur.setDescription(text.toString());
                      erreur.setKey("InsertDemandeCheque");
                      compte519.addError(erreur);        
                  }
        } catch (Exception e) {
                         com.oxia.fwk.core.Error erreur = new com.oxia.fwk.core.Error();
                         StringBuffer text = 
                             new StringBuffer("Erreur dans InsertClientContCompteTrt : ");
                         text.append(e.toString());
                         erreur.setCode("100");
                         erreur.setDescription(text.toString());
                         erreur.setKey("InsertCompte519");
                         compte519.addError(erreur);
                        logger.error("Erreur au niveau de l'agence <<" +compteDav.getContratCptId().getCodStrcStrc() + ">>. Exception : ",e);    
                         throw new RuntimeException(e);
          }
          
        return (compte519);
    }

    public void genCroText(ValueObject vo) {    
    
    }
    
    public String getNumeroTache(IValueObject vo) {
      return ("101");    
    }
    
  /*  public IValueObject getNumeroDomaine(IValueObject vo){
        StructureDomaine structureDomaine = new StructureDomaine();
        ParamCompteLie paramCompteLie = (ParamCompteLie)vo;
        structureDomaine.setCodDomDomm(Constants.COD_DOM_CONTRATCOMPTE);
        structureDomaine.setCodStrcStrc(paramCompteLie.getContratCpt().getContratCptId().getCodStrcStrc());
        return structureDomaine;
    }*/
}

