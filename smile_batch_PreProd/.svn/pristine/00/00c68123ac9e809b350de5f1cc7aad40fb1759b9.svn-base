package com.bna.smile.model.domaineplacement.traitement;


import com.bna.commun.model.AvancRembLiquid;
import com.bna.commun.model.BonDeCaisse;
import com.bna.commun.model.ContratPlacement;
import com.bna.commun.model.DetailsBc;
import com.bna.commun.model.DetailsOperationPlacement;
import com.bna.commun.model.MandPersOperPlac;
import com.bna.commun.model.StructureDomaine;
import com.bna.commun.traitements.InsertionOperationMoyPayTrt;
import com.bna.commun.traitements.Traitement;
import com.bna.commun.util.ContextHandler;
import com.bna.commun.util.StrHandler;
import com.bna.smile.model.constant.Constants;
import com.bna.smile.model.domainecommun.service.CRUDservice;
import com.bna.smile.model.domaineplacement.model.ParamContratPlacement;

import com.oxia.fwk.context.Context;
import com.oxia.fwk.core.IValueObject;
import com.oxia.fwk.core.ValueObject;

import java.util.Iterator;

import java.util.Set;

import org.springframework.orm.hibernate3.HibernateTemplate;

/**
 * Prise en charge d'une souscription de contrat de placement.
 * @author Jerbi Lamia
 * @param ParamContratPlacement
 * @return ContratPlacement
 * 
 */
public class PecSouscriptionPlacementTrt extends Traitement{

    public PecSouscriptionPlacementTrt() {
    }
        
    public IValueObject perform (IValueObject vo ){
     
    
     ParamContratPlacement paramContratPlacement = (ParamContratPlacement)vo;             
     InsertDetailsOpPlacementTrt insertDetailsOpPlacementTrt= new InsertDetailsOpPlacementTrt();
    
        
       try{ 
            this.setCroFlag(false);
      if(paramContratPlacement.getTypeOperation().equals("RPEC")){
                // Mise à jour demande de décision
                 ValiderMajDdeDecisionTrt validerMajDdeDecisionTrt = new ValiderMajDdeDecisionTrt();
                 validerMajDdeDecisionTrt.exec(paramContratPlacement.getDemandeDecision());
               
        }else if(paramContratPlacement.getTypeOperation().equals("RVALID")){
        
               UpdateContratPlacementTrt updateContratPlacementTrt =new UpdateContratPlacementTrt();
               if(!paramContratPlacement.getContratPlacement().equals(null)){
                   updateContratPlacementTrt.exec(paramContratPlacement.getContratPlacement());
               }
           }else if(paramContratPlacement.getTypeOperation().equals("PEC")){
           
           ///----------- création contrat placement  ----------------------
           ///--------------------------------------------------------------
            ContratPlacement contratPlacement =new ContratPlacement();
            DetailsOperationPlacement detailsOperationPlacement = new DetailsOperationPlacement();
            MandPersOperPlac mandPOpPlac = new MandPersOperPlac();
    InsertContratPlacementTrt insertContratPlacementTrt = new InsertContratPlacementTrt();
            if(!paramContratPlacement.getContratPlacement().equals(null)){
              contratPlacement =(ContratPlacement)insertContratPlacementTrt.exec(paramContratPlacement.getContratPlacement());         
            } 
            // remplir le numero BC dans la table detailsBC
             Context context = ContextHandler.getContext();
             CRUDservice crudService = (CRUDservice)context.getBean("crudservice");           
             
             if(paramContratPlacement.getContratPlacement().getProduitPlacement().getCodPrdPlc().equals(Constants.COD_PRD_BC_PLAC)
               || paramContratPlacement.getContratPlacement().getProduitPlacement().getCodPrdPlc().equals(Constants.COD_PRD_BCDC_PLAC)
               ){
                DetailsBc detailsBc =new DetailsBc();
                detailsBc.setContratPlacement(paramContratPlacement.getContratPlacement());
                BonDeCaisse bonCaiss =new BonDeCaisse();
                bonCaiss.setNumSeqBc(paramContratPlacement.getNumSeqBc());
                detailsBc.setBonDeCaisse(bonCaiss);
                detailsBc.setNumBcDbc(paramContratPlacement.getContratPlacement().getNumBcCpla());
                crudService.create(detailsBc);
              } 
            
            ///-- MAJ demande
            ///------------------------------------------------------------------------
            if(!contratPlacement.getNumSeqCpla().equals(null)){
            // si le contrat est inséré
              // Mise à jour demande de décision
               ValiderMajDdeDecisionTrt validerMajDdeDecisionTrt = new ValiderMajDdeDecisionTrt();
               validerMajDdeDecisionTrt.exec(paramContratPlacement.getDemandeDecision());
                      
                if(!paramContratPlacement.getDetailsOperationPlacement().equals(null)){
                    paramContratPlacement.getDetailsOperationPlacement().setContratPlacement(paramContratPlacement.getContratPlacement());
                   detailsOperationPlacement = (DetailsOperationPlacement)insertDetailsOpPlacementTrt.exec(paramContratPlacement.getDetailsOperationPlacement());
                } 
                //----------------------------------mandat personne
               ///*** Insertion de mand_pers_oper_plac pour les mandataires qui ont souscrient au contrat placement
                  Set mandPers =  paramContratPlacement.getDetailsOperationPlacement().getMandPersOperPlacs();
                  if(mandPers!=null && mandPers.size()>0 ){
                      InsertMandPersOperPlacTrt insertMandPersOperPlacTrt = new InsertMandPersOperPlacTrt();
                      for (Iterator it = mandPers.iterator();it.hasNext(); ) { 
                          MandPersOperPlac mandPersOperPlac = (MandPersOperPlac)it.next();
                          mandPersOperPlac.getMandPersOperPlacId().setNumSeqDopl(paramContratPlacement.getDetailsOperationPlacement().getNumSeqDopl());
                          HibernateTemplate  hibernateTemplate = (HibernateTemplate) context.getBean("hibernateTemplate");
                          hibernateTemplate.evict(mandPersOperPlac);
                        
                         mandPOpPlac = (MandPersOperPlac)insertMandPersOperPlacTrt.exec(mandPersOperPlac);
                      }
                  }  
                
            } 
       }// fin test sur le type de l'opération : rejet ou PEC
}

          
         catch (Exception e) {
                com.oxia.fwk.core.Error erreur=new com.oxia.fwk.core.Error();
                erreur.setCode("Technique");
                erreur.setDescription("PecSouscriptionPlacementTrt  "+e.getMessage());;
                paramContratPlacement.getContratPlacement().addError(erreur);
                logger.error("Exception : ",e);   
                throw new   RuntimeException(e);
        } 
        return (paramContratPlacement.getContratPlacement());
    }
    
    public void genCroText(ValueObject vo) {
       
    }
    public IValueObject getNumeroDomaine(IValueObject vo){
        StructureDomaine structureDomaine = new StructureDomaine();
        ParamContratPlacement paramContratPlacement = (ParamContratPlacement)vo;      
        structureDomaine.setCodDomDomm(Constants.COD_DOM_PLACEMENT);
        structureDomaine.setCodStrcStrc(paramContratPlacement.getDetailsOperationPlacement().getStructure().getCodStrcStrc());
        return structureDomaine;
    }

    public String getNumeroTache(IValueObject vo) {
        ParamContratPlacement paramContratPlacement = (ParamContratPlacement)vo;             
      String operationTache;
        if(paramContratPlacement.getTypeOperation().equals("RPEC")
        || paramContratPlacement.getTypeOperation().equals("RVALID")
        ){
            operationTache = Constants.CODE_RESSOURCE_GENERALE;
        }else{
            operationTache =Constants.COD_OPER_SOUSC_PLAC.toString()+
        StrHandler.lpad(Constants.COD_TACH_INTERET_SOUSC_PLAC.toString(),'0',2);
        }
        return(operationTache);
    }

}
