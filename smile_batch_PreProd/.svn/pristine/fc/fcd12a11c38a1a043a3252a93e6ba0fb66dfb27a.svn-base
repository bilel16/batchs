package com.bna.smile.model.domaineplacement.traitement;

import com.bna.commun.model.ContratCpt;
import com.bna.commun.model.ContratCptId;
import com.bna.commun.model.ContratPlacement;
import com.bna.commun.model.DetailsOperationPlacement;
import com.bna.commun.model.InteretServi;
import com.bna.commun.model.MandPersOperPlac;
import com.bna.commun.model.MouvementInterne;
import com.bna.commun.model.OperationMoyPay;
import com.bna.commun.model.Personne;
import com.bna.commun.model.Personnel;
import com.bna.commun.model.Structure;
import com.bna.commun.model.StructureDomaine;
import com.bna.commun.model.Tache;
import com.bna.commun.model.TacheId;
import com.bna.commun.traitements.Traitement;
import com.bna.commun.traitements.UpdateSoldTrt;
import com.bna.commun.util.ContextHandler;
import com.bna.commun.util.DateHandler;
import com.bna.commun.util.StrHandler;
import com.bna.commun.vo.ContratCptSold;
import com.bna.smile.model.constant.Constants;
import com.bna.smile.model.domainecommun.service.CRUDservice;
import com.bna.smile.model.domainecontratcompte.procuration.traitement.UpdateMandatOperationTrt;
import com.bna.smile.model.domaineplacement.model.ParamAbonnementement;
import com.bna.smile.model.domaineplacement.model.ParamContratPlacement;

import com.bna.smile.model.domaineplacement.model.ParamInsertInteret;

import com.oxia.fwk.context.Context;
import com.oxia.fwk.core.ISearchEngine;
import com.oxia.fwk.core.IValueObject;
import com.oxia.fwk.core.ValueObject;
import com.oxia.fwk.searchengine.SearchEngine;

import java.text.SimpleDateFormat;

import java.util.Date;
import java.util.Iterator;
import java.util.Set;

import org.acegisecurity.context.SecurityContextHolder;
import org.acegisecurity.userdetails.UserDetails;

import org.springframework.orm.hibernate3.HibernateTemplate;

/**
 * validation d'une souscription de contrat de placement.
 * @author Jerbi Lamia
 * @param ParamContratPlacement
 * @return ContratPlacement
 * 
 */
 
public class ValiderSouscriptionPlacementTrt extends Traitement{
  
    public Context context = ContextHandler.getContext();
    
    
    public ValiderSouscriptionPlacementTrt() {
    }
        
    public IValueObject perform (IValueObject vo ){
     
        InsertOpMoyPaySouscriptionPlacTrt insertOpMoyPaySouscriptionPlacTrt = new InsertOpMoyPaySouscriptionPlacTrt(); 
        InsertOperationInteretServiTrt insertOpMoyPayInteretSouscPlacTrt = new InsertOperationInteretServiTrt();
        DetailsOperationPlacement detailsOpPlacement = new DetailsOperationPlacement();
        ParamContratPlacement paramContratPlacement = (ParamContratPlacement)vo;             
        ISearchEngine searchEngine =  (SearchEngine)context.getBean("searchEngine");

       try{ 
         this.setCroFlag(false); 
         Context context = ContextHandler.getContext();
         HibernateTemplate  hibernateTemplate = (HibernateTemplate) context.getBean("hibernateTemplate");
         ///----------- MAJ contrat placement  ----------------------
         ///--------------------------------------------------------------
          UpdateContratPlacementTrt updateContratPlacementTrt =new UpdateContratPlacementTrt();
     //     hibernateTemplate.flush();
          hibernateTemplate.evict(paramContratPlacement.getDetailsOperationPlacement().getContratPlacement());
        
         // ContratPlacement contratPlacementAjour = (ContratPlacement)updateContratPlacementTrt.exec(paramContratPlacement.getContratPlacement());
        
         Long numSeq = paramContratPlacement.getContratPlacement().getNumSeqCpla();
          
         ContratPlacement contratPlacementAjour = (ContratPlacement)searchEngine.loadForUpdate(ContratPlacement.class,numSeq);
        
         hibernateTemplate.evict(contratPlacementAjour);

          if(!contratPlacementAjour.hasError()){ 
            if(!contratPlacementAjour.getCodEtatCpla().equals(Constants.ETAT_CONTRAT_PLAC_VALIDE)){
            // si le contrat est mis à jour
                ///*** MAJ du montant actualisé dans le contrat compte  
                 ContratCptId contratCptId = paramContratPlacement.getContratPlacement().getContratCpt().getContratCptId();
              //   ISearchEngine searchEngine = (ISearchEngine)context.getBean("searchEngine");
                   // Charger le ContratCpt existant 
                 ContratCpt contratCpt =(ContratCpt) searchEngine.get(ContratCpt.class,contratCptId);
                 ContratCptSold contratCptSold = new ContratCptSold();
                 contratCptSold.setContratCpt(contratCpt);
                 
                 if(contratPlacementAjour.getCodPintCpla().equals("PRE")){ // type paiement des interets PRE
                  contratCptSold.setSolde(paramContratPlacement.getContratPlacement().getMontCapCpla()-paramContratPlacement.getInteretServi().getMontIsrvIsrv());
                     }else {
                         contratCptSold.setSolde(paramContratPlacement.getContratPlacement().getMontCapCpla());
                     }
                 contratCptSold.setSens(Constants.COD_SENS_DB);
                 UpdateSoldTrt updateSoldTrt = new UpdateSoldTrt();
                 contratCpt = (ContratCpt)updateSoldTrt.exec(contratCptSold);
                 
                 detailsOpPlacement = paramContratPlacement.getDetailsOperationPlacement();
             //    hibernateTemplate.evict(detailsOpPlacement);
              
                if(!detailsOpPlacement.equals(null)){
                    detailsOpPlacement.setContratPlacement(paramContratPlacement.getContratPlacement());
                    InsertDetailsOpPlacementTrt insertDetailsOpPlacementTrt = new InsertDetailsOpPlacementTrt();
                    if(detailsOpPlacement.getMandPersOperPlacs()!=null && detailsOpPlacement.getMandPersOperPlacs().size()>0 ){
                         if (detailsOpPlacement.getMandatOperation()!=null){ ///*** madat speciale (maj enveloppe utilisée)
                             detailsOpPlacement.getMandatOperation().setMontUtilMaop(detailsOpPlacement.getMandatOperation().getMontUtilMaop() + paramContratPlacement.getContratPlacement().getMontCapCpla());
                             UpdateMandatOperationTrt updateMandatOperationTrt =new UpdateMandatOperationTrt();
                             updateMandatOperationTrt.exec(detailsOpPlacement.getMandatOperation()) ;
                         }
                      }
                    detailsOpPlacement = (DetailsOperationPlacement)insertDetailsOpPlacementTrt.exec(detailsOpPlacement);
                  } 
                //----------------------------------mandat personne
               ///*** Insertion de mand_pers_oper_plac pour les mandataires qui ont souscrient au contrat placement
                  Set mandPers =  paramContratPlacement.getDetailsOperationPlacement().getMandPersOperPlacs();
                  if(mandPers!=null && mandPers.size()>0 ){
                      InsertMandPersOperPlacTrt insertMandPersOperPlacTrt = new InsertMandPersOperPlacTrt();
                      for (Iterator it = mandPers.iterator();it.hasNext(); ) { 
                          MandPersOperPlac mandPersOperPlac = (MandPersOperPlac)it.next();
                          mandPersOperPlac.getMandPersOperPlacId().setNumSeqDopl(paramContratPlacement.getDetailsOperationPlacement().getNumSeqDopl());
                   //       HibernateTemplate  hibernateTemplate = (HibernateTemplate) context.getBean("hibernateTemplate");
                      //    hibernateTemplate.flush();
                          hibernateTemplate.evict(mandPersOperPlac);
                        
                          insertMandPersOperPlacTrt.exec(mandPersOperPlac);
                      }
                  } 
                 // insertion opération moy pay
                   OperationMoyPay operationMoyPay = (OperationMoyPay)insertOpMoyPaySouscriptionPlacTrt.exec(paramContratPlacement.getDetailsOperationPlacement());
              
              // génération abonnement
               ParamAbonnementement paramAbonnementement = new ParamAbonnementement();
               paramAbonnementement.setDatDebAbpl(contratPlacementAjour.getDatValCpla());
               paramAbonnementement.setDatFinAbpl(contratPlacementAjour.getDatEcheCpla());
               paramAbonnementement.setTypeOperation("S");///*** S:souscription, A:avance
               if(contratPlacementAjour.getContratPlacementByNumSqcrCpla() != null){
                   paramAbonnementement.setOpRenouvellemnt(true);
               }
               paramAbonnementement.setNumSeqCpla(contratPlacementAjour.getNumSeqCpla());
               paramAbonnementement.setTypeInteret(contratPlacementAjour.getCodFavCpla()); ///*** I:indexé
               paramAbonnementement.setMontTotAbpl(contratPlacementAjour.getMontCapCpla()); ///*** montant placement
               paramAbonnementement.setNumTauiCpla(contratPlacementAjour.getNumTauiCpla());
               paramAbonnementement.setMontItotAbpl(paramContratPlacement.getInteretServi().getMontBrutIsrv());
               paramAbonnementement.setContratPlacement(contratPlacementAjour);
               paramAbonnementement.setDateCompAgence(paramContratPlacement.getDetailsOperationPlacement().getDatCompDopl());
               GenererAbonnementTrt genererAbonnementTrt = new GenererAbonnementTrt(); // type de faveur différen d'indexé au TMM
               genererAbonnementTrt.exec(paramAbonnementement);
               paramContratPlacement.setMontAbonnCorrectAnnee(Long.valueOf(paramAbonnementement.getMontInteretCorrecAnneesPrec().longValue()));
               paramContratPlacement.setMontAbonnCorrectMois(Long.valueOf(paramAbonnementement.getMontIntCorrectAbonnMois().longValue()));
                 // insertion interets servis
                  InsertInteretServiTrt insertInteretServiTrt =new InsertInteretServiTrt();
                  if(contratPlacementAjour.getCodPintCpla().equals("PRE")){ // type paiement des interets PRE
                  InteretServi intServ =new InteretServi();
                  intServ = (InteretServi)insertInteretServiTrt.exec(paramContratPlacement.getInteretServi());
                  intServ.setContratPlacement(contratPlacementAjour);
                  
                 ///----------- création opération Moyen de payement (interet) ----------------------     
               //   HibernateTemplate  hibernateTemplate = (HibernateTemplate) context.getBean("hibernateTemplate");
                //  hibernateTemplate.flush();
                  hibernateTemplate.evict(operationMoyPay);
 
                  OperationMoyPay operationMoyPay1 = new OperationMoyPay();
                  operationMoyPay1.setNumOperOmp(operationMoyPay.getNumOperOmp());
                  operationMoyPay.setOperationMoyPayM(operationMoyPay1);
                  paramContratPlacement.getDetailsOperationPlacement().setOperationMoyPay(operationMoyPay1);
                  Tache tache = new Tache();
                  TacheId tacheId = new TacheId();
                  tacheId.setCodTachTach(Constants.COD_TACH_INTERET_SOUSC_PLAC);
                   if(paramContratPlacement.getContratPlacement().getCodSbdvCpla() != null ){
                  if(paramContratPlacement.getContratPlacement().getCodSbdvCpla().equals("0")){
                      tacheId.setCodOperOper(Constants.OPER_INT_PRE_SOUSC_PLAC);
                  }else {
                      tacheId.setCodOperOper(Constants.OPER_INT_PRE_SOUSC_PLAC_SBDV);
                  }
                  }else {
                      tacheId.setCodOperOper(Constants.OPER_INT_PRE_SOUSC_PLAC);
                  }
                  tache.setTacheId(tacheId);
                  operationMoyPay.setTache(tache);
                  operationMoyPay.setMontDinOmp(paramContratPlacement.getInteretServi().getMontIsrvIsrv());
                  operationMoyPay.setBoolForcOmp(Long.valueOf(0)); // on laisse le forçage ?? op CR
                  operationMoyPay.setCodSensOmp(Constants.COD_SENS_CR); 
                  operationMoyPay.setDatValOmp(paramContratPlacement.getInteretServi().getDatValIsrv());///*** date valeur interet a partir des CB
                  operationMoyPay.setMontSoldCcpt(contratCpt.getMontSoldCcpt()-paramContratPlacement.getContratPlacement().getMontCapCpla());
                  operationMoyPay.setMontApreOmp(operationMoyPay.getMontApreOmp()+ paramContratPlacement.getInteretServi().getMontIsrvIsrv());
               //   operationMoyPay.setCodRefbOmp(contratPlacementAjour.getNumSeqCpla().toString().substring(7,15));
                  
                     ParamInsertInteret paramInsertInteret = new ParamInsertInteret();
                     paramInsertInteret.setOperationMoyPay(operationMoyPay);
                     paramInsertInteret.setInteretServi(intServ);
                   paramInsertInteret.setMontAbonnCorrectionAnnee(Long.valueOf(paramAbonnementement.getMontInteretCorrecAnneesPrec().longValue()));
                   paramInsertInteret.setMontAbonnCorrectionMois(Long.valueOf(paramAbonnementement.getMontIntCorrectAbonnMois().longValue()));
                  operationMoyPay = (OperationMoyPay)insertOpMoyPayInteretSouscPlacTrt.exec(paramInsertInteret);
              
                   //-----------------------------------------//Fin type paiement des interets PRE
               }else {
                   if(paramContratPlacement.getContratPlacement().getCodSbdvCpla() != null){
                    if(paramContratPlacement.getContratPlacement().getCodSbdvCpla().equals("1")
                    || paramContratPlacement.getContratPlacement().getCodSbdvCpla().equals("2")){
                       if(paramContratPlacement.getMontAbonnCorrectAnnee() != 0 || paramContratPlacement.getMontAbonnCorrectMois() !=0 ){
                       
                       // insertion du mouvement interne en cas de Sousc SBDV int post compté (344)(pqque on n'insere pas dans la table operMoypay )
                        MouvementInterne mouvementInterne = new MouvementInterne();                 
                        mouvementInterne.setCodRefmMvti(paramContratPlacement.getContratPlacement().getNumSeqCpla().toString());
                        mouvementInterne.setDatOperMvti(paramContratPlacement.getInteretServi().getDatIsrvIsrv());
                        mouvementInterne.setDatSystMvti(new Date());
                        mouvementInterne.setDatValMvti(paramContratPlacement.getInteretServi().getDatValIsrv());
                        mouvementInterne.setLibMotfMvti("Operation abonnements intetrets a servir suite op. avec valeur antérieur (644)");
                        Tache tache = new Tache();
                        TacheId tacheId = new TacheId();
                        tacheId.setCodTachTach(Long.valueOf("1"));                     
                        tacheId.setCodOperOper(Constants.OPER_INT_POST_SOUSC_PLAC_SBDV);
                        
                         tache.setTacheId(tacheId);
                         Object obj = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
                         com.oxia.security.abc.model.Personnel user = null;
                         if (obj instanceof UserDetails) {
                              user = (com.oxia.security.abc.model.Personnel)obj;
                         }
                        
                        mouvementInterne.setTache(tache);            
                        mouvementInterne.setMontMvtiMvti(Long.valueOf(paramAbonnementement.getMontInteretCorrecAnneesPrec().longValue()+paramAbonnementement.getMontIntCorrectAbonnMois().longValue()));
                        Structure strc = new Structure();
                        strc.setCodStrcStrc(detailsOpPlacement.getStructure().getCodStrcStrc());
                       
                        Personnel pers = new Personnel();
                        pers.setNumMatrUser(user.getNumMatrUser());
                        mouvementInterne.setPersonnel(pers);
                        mouvementInterne.setStructure(strc);
                        InsertMouvementInterneTrt insertMouvementInterneTrt = new InsertMouvementInterneTrt();
                        mouvementInterne = (MouvementInterne)insertMouvementInterneTrt.exec(mouvementInterne);
                       paramContratPlacement.setMouvementInterne(mouvementInterne);
                           this.setCroFlag(true); 
                       }else {
                           this.setCroFlag(false); 
                       }
                   }
                  }
                   
               }
             } 
          }
          
           hibernateTemplate.update(paramContratPlacement.getContratPlacement());
           hibernateTemplate.flush();
           
     }catch (Exception e) {
                com.oxia.fwk.core.Error erreur=new com.oxia.fwk.core.Error();
                erreur.setCode("Technique");
                erreur.setDescription("ValiderSouscriptionPlacementTrt  "+e.getMessage());;
                paramContratPlacement.getContratPlacement().addError(erreur);
                logger.error("Exception : ",e);   
                throw new   RuntimeException(e);
        } 
        return (paramContratPlacement.getContratPlacement());
    }
    
    public void genCroText(ValueObject vo) {
        
        ParamContratPlacement paramContratPlacement = (ParamContratPlacement)vo;   
        ContratPlacement contratPlacement = paramContratPlacement.getContratPlacement(); 
        if((contratPlacement.getCodSbdvCpla().equals("1")|| paramContratPlacement.getContratPlacement().getCodSbdvCpla().equals("2")) && contratPlacement.getCodPintCpla().equals("POST")){
        if(paramContratPlacement.getMontAbonnCorrectAnnee() != 0 || paramContratPlacement.getMontAbonnCorrectMois() !=0 ){
        MouvementInterne mouvementInterne = paramContratPlacement.getMouvementInterne();
          /* ---------------------- Garniture de la partie FIXE du CRO ----------------------------------- */

           Object obj = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
                    com.oxia.security.abc.model.Personnel user = null;
                    if (obj instanceof UserDetails) {
                        user = (com.oxia.security.abc.model.Personnel)obj;
                   }
         
          this.setNumRefCro(mouvementInterne.getNumMvtiMvti());
          this.setLibRefCro("smile.plac.correctAbonInt");  
         
          this.setDatValCro(mouvementInterne.getDatValMvti());
          this.setCodeStructInitiatrice(contratPlacement.getContratCpt().getStructure().getCodStrcStrc().toString());              
          this.setCodStrcImpt(contratPlacement.getContratCpt().getStructure().getCodStrcStrc());
          this.setCodEtatCro(0);              
          this.setCodeProduit(contratPlacement.getProduitPlacement().getCodPrdPlc().toString());
          this.setOperationId(mouvementInterne.getTache().getTacheId().getCodOperOper().toString());
          this.setDateOperation(mouvementInterne.getDatOperMvti()); // date comptable
          
          SimpleDateFormat formater=new SimpleDateFormat("dd/MM/yyyy");
          formater=new SimpleDateFormat("HH:mm:ss");
          String heureString = formater.format(new Date());
          this.setHeureOperation(heureString);
          this.setTypeOperationCro("O");
          this.setDatExecCro(mouvementInterne.getDatSystMvti()); // date system
          this.setCodTachTach(Constants.COD_TACHE_VALID_PLAC);
          this.setCodRefcOmp(contratPlacement.getNumSeqCpla().toString());
          
          this.setNumCinUser(user.getNumMatrUser());
          this.setCodTypUser(user.getMatriculeTyp());
          
             /* ------------------Garniture de la partie VARIABLE du CRO----------------------------------  */
        StringBuffer cro=new StringBuffer("");
        cro.append("ABONNEMENT_PLACEMENT.MONT_CORRECT_AN="); // (30) abonnement non constaté pour l'année précédente en cas d chauvauchement d année entre la date comptable et la date souscription
        cro.append(paramContratPlacement.getMontAbonnCorrectAnnee() +";");
        cro.append("ABONNEMENT_PLACEMENT.MONT_CORRECT_MOI="); // (17')abonnement non constaté pour le mois précédent en cas d chauvauchement d mois entre la date comptable et la date souscription
        cro.append(paramContratPlacement.getMontAbonnCorrectMois() +";");
        System.out.println(paramContratPlacement.getMontAbonnCorrectAnnee()+paramContratPlacement.getMontAbonnCorrectMois());
        cro.append("ABONNEMENT_PLACEMENT.MONT_CORRECT_TOT=");// (30+17') abonnement non constaté total 
        cro.append(paramContratPlacement.getMontAbonnCorrectAnnee()+paramContratPlacement.getMontAbonnCorrectMois() +";");
        cro.append("CONTRAT_PLACEMENT.NUM_SEQ_CPLA=");
        cro.append(contratPlacement.getNumSeqCpla().toString()+";");
        this.setCroText(cro.toString());
        }
        }
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
