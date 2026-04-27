package com.bna.smile.model.domaineplacement.traitement;

import com.bna.commun.model.BonDeCaisse;
import com.bna.commun.model.ContratCpt;
import com.bna.commun.model.ContratCptId;
import com.bna.commun.model.ContratPlacement;
import com.bna.commun.model.DetailsBc;
import com.bna.commun.model.DetailsOperationPlacement;
import com.bna.commun.model.InteretServi;
import com.bna.commun.model.MandPersOperPlac;
import com.bna.commun.model.OperationMoyPay;
import com.bna.commun.model.StructureDomaine;
import com.bna.commun.model.Tache;
import com.bna.commun.model.TacheId;
import com.bna.commun.traitements.Traitement;

import com.bna.commun.traitements.UpdateSoldTrt;
import com.bna.commun.util.ContextHandler;
import com.bna.commun.util.StrHandler;
import com.bna.commun.vo.ContratCptSold;
import com.bna.smile.model.constant.Constants;
import com.bna.smile.model.domainecommun.service.CRUDservice;
import com.bna.smile.model.domaineplacement.model.ParamAbonnementement;
import com.bna.smile.model.domaineplacement.model.ParamContratPlacement;

import com.bna.smile.model.domaineplacement.model.ParamInsertInteret;

import com.oxia.fwk.context.Context;
import com.oxia.fwk.core.ISearchEngine;
import com.oxia.fwk.core.IValueObject;
import com.oxia.fwk.core.ValueObject;

import java.util.Iterator;
import java.util.Set;

import org.springframework.orm.hibernate3.HibernateTemplate;

public class CreateContratPlacementTrt extends Traitement{
    public CreateContratPlacementTrt() {
    }
    public IValueObject perform (IValueObject vo ){
        ParamContratPlacement paramContratPlacement = (ParamContratPlacement)vo;             
        InsertDetailsOpPlacementTrt insertDetailsOpPlacementTrt= new InsertDetailsOpPlacementTrt();
        
    try{
        this.setCroFlag(false);
        
        ///----------- création contrat placement  ----------------------
        ///--------------------------------------------------------------
         ContratPlacement contratPlacement =new ContratPlacement();
         DetailsOperationPlacement detailsOperationPlacement = new DetailsOperationPlacement();
         MandPersOperPlac mandPOpPlac = new MandPersOperPlac();
         InsertContratPlacementTrt insertContratPlacementTrt = new InsertContratPlacementTrt();
         InsertOpMoyPaySouscriptionPlacTrt insertOpMoyPaySouscriptionPlacTrt = new InsertOpMoyPaySouscriptionPlacTrt(); 
         InsertOperationInteretServiTrt insertOpMoyPayInteretSouscPlacTrt = new InsertOperationInteretServiTrt();
        
         Context context = ContextHandler.getContext();
        
         if(!paramContratPlacement.getContratPlacement().equals(null)){
             paramContratPlacement.getContratPlacement().setCodEtatCpla(Constants.ETAT_CONTRAT_PLAC_VALIDE);
             paramContratPlacement.getContratPlacement().setDatVldCpla(paramContratPlacement.getContratPlacement().getDatCreCpla());
             contratPlacement =(ContratPlacement)insertContratPlacementTrt.exec(paramContratPlacement.getContratPlacement());         
         } 
        
         // remplir le numero BC dans la table detailsBC
          
          if(paramContratPlacement.getContratPlacement().getProduitPlacement().getCodPrdPlc().equals(Constants.COD_PRD_BC_PLAC)
            || paramContratPlacement.getContratPlacement().getProduitPlacement().getCodPrdPlc().equals(Constants.COD_PRD_BCDC_PLAC)
            ){
             CRUDservice crudService = (CRUDservice)context.getBean("crudservice");           
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
            
            // MAJ Solde
            ///*** MAJ du montant actualisé dans le contrat compte  
              ContratCptId contratCptId = paramContratPlacement.getContratPlacement().getContratCpt().getContratCptId();
              ISearchEngine searchEngine = (ISearchEngine)context.getBean("searchEngine");
                // Charger le ContratCpt existant 
              ContratCpt contratCpt =(ContratCpt) searchEngine.get(ContratCpt.class,contratCptId);
              ContratCptSold contratCptSold = new ContratCptSold();
              contratCptSold.setContratCpt(contratCpt);
              
              if(contratPlacement.getCodPintCpla().equals("PRE")){ // type paiement des interets PRE
               contratCptSold.setSolde(paramContratPlacement.getContratPlacement().getMontCapCpla()-paramContratPlacement.getInteretServi().getMontIsrvIsrv());
                  }else {
                      contratCptSold.setSolde(paramContratPlacement.getContratPlacement().getMontCapCpla());
                  }
              contratCptSold.setSens(Constants.COD_SENS_DB);
              UpdateSoldTrt updateSoldTrt = new UpdateSoldTrt();
              contratCpt = (ContratCpt)updateSoldTrt.exec(contratCptSold);
            
             // créer DetailsOperationPlacement
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
            // operation Moy Pay
             // insertion opération moy pay
               OperationMoyPay operationMoyPay = (OperationMoyPay)insertOpMoyPaySouscriptionPlacTrt.exec(paramContratPlacement.getDetailsOperationPlacement());
              
             // insertion interets servis
              InsertInteretServiTrt insertInteretServiTrt =new InsertInteretServiTrt();
              if(contratPlacement.getCodPintCpla().equals("PRE")){ // type paiement des interets PRE
              InteretServi intServ =new InteretServi();
              intServ = (InteretServi)insertInteretServiTrt.exec(paramContratPlacement.getInteretServi());
              intServ.setContratPlacement(contratPlacement);
             
                  ///----------- création opération Moyen de payement (interet) ----------------------     
                   HibernateTemplate  hibernateTemplate = (HibernateTemplate) context.getBean("hibernateTemplate");
                   hibernateTemplate.evict(operationMoyPay);
                  
                   OperationMoyPay operationMoyPay1 = new OperationMoyPay();
                   operationMoyPay1.setNumOperOmp(operationMoyPay.getNumOperOmp());
                   operationMoyPay.setOperationMoyPayM(operationMoyPay1);
                   paramContratPlacement.getDetailsOperationPlacement().setOperationMoyPay(operationMoyPay1);
                   Tache tache = new Tache();
                   TacheId tacheId = new TacheId();
                   tacheId.setCodTachTach(Constants.COD_TACH_INTERET_SOUSC_PLAC);
                   tacheId.setCodOperOper(Constants.OPER_INT_PRE_SOUSC_PLAC);
                   tache.setTacheId(tacheId);
                   operationMoyPay.setTache(tache);
                   operationMoyPay.setMontDinOmp(paramContratPlacement.getInteretServi().getMontIsrvIsrv());
                   operationMoyPay.setBoolForcOmp(Long.valueOf(1)); 
                   operationMoyPay.setCodSensOmp(Constants.COD_SENS_CR); 
                   operationMoyPay.setDatValOmp(paramContratPlacement.getInteretServi().getDatValIsrv());///*** date valeur interet a partir des CB
                   operationMoyPay.setMontSoldCcpt(contratCpt.getMontSoldCcpt()-paramContratPlacement.getContratPlacement().getMontCapCpla());
                   operationMoyPay.setMontApreOmp(operationMoyPay.getMontApreOmp()+ paramContratPlacement.getInteretServi().getMontIsrvIsrv());
                  
                    ParamInsertInteret paramInsertInteret = new ParamInsertInteret();
                    paramInsertInteret.setOperationMoyPay(operationMoyPay);
                    paramInsertInteret.setInteretServi(intServ);
                   
                   operationMoyPay = (OperationMoyPay)insertOpMoyPayInteretSouscPlacTrt.exec(paramInsertInteret);
                 }//--------------------------------------------------------------------------------------------------//Fin type paiement des interets PRE

            // Abonnement
             // génération abonnement
              ParamAbonnementement paramAbonnementement = new ParamAbonnementement();
            //  paramAbonnementement.setDatDebAbpl(contratPlacement.getDatValCpla());
              paramAbonnementement.setDatDebAbpl(paramContratPlacement.getDemandeDecision().getDatValDemd());
              paramAbonnementement.setDatFinAbpl(contratPlacement.getDatEcheCpla());
              paramAbonnementement.setTypeOperation("S");///*** S:souscription, A:avance
              if(contratPlacement.getContratPlacementByNumSqcrCpla() != null){
                  paramAbonnementement.setOpRenouvellemnt(true);
              }
              paramAbonnementement.setNumSeqCpla(contratPlacement.getNumSeqCpla());
              paramAbonnementement.setTypeInteret(contratPlacement.getCodFavCpla()); ///*** I:indexé
              paramAbonnementement.setMontTotAbpl(contratPlacement.getMontCapCpla()); ///*** montant placement
              paramAbonnementement.setNumTauiCpla(contratPlacement.getNumTauiCpla());
              paramAbonnementement.setMontItotAbpl(paramContratPlacement.getInteretServi().getMontBrutIsrv());
              paramAbonnementement.setContratPlacement(contratPlacement);
              GenererAbonnementTrt genererAbonnementTrt = new GenererAbonnementTrt(); // type de faveur différen d'indexé au TMM
              genererAbonnementTrt.exec(paramAbonnementement);
             
         }
    }catch (Exception e) {
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
        return(Constants.COD_OPER_SOUSC_PLAC.toString()+
        StrHandler.lpad(Constants.COD_TACHE_VALID_PLAC.toString(),'0',2));
    }

}
