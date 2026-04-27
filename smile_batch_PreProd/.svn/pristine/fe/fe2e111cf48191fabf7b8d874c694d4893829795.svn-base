package com.bna.smile.model.domaineguichet.traitement;

import com.bna.commun.model.ContratCpt;
import com.bna.commun.model.ContratCptId;
import com.bna.commun.model.DetailCaisDevAg;
import com.bna.commun.model.DetailCaisDevAgId;
import com.bna.commun.model.DetailOperMoyPaiement;
import com.bna.commun.model.MontantMiseDiposition;
import com.bna.commun.model.MouvementsCaisses;
import com.bna.commun.model.OperationEpargnes;
import com.bna.commun.model.OperationMoyPay;
import com.bna.commun.model.Tache;
import com.bna.commun.service.CURService;
import com.bna.commun.traitements.Traitement;
import com.bna.commun.traitements.UpdateSoldTrt;
import com.bna.commun.util.DateHandler;
import com.bna.commun.util.ContextHandler;
import com.bna.commun.vo.ContratCptSold;
import com.bna.smile.model.constant.Constants;

import com.bna.smile.model.domainecaisse.model.SituationDetailCaisseStructureVo;
import com.bna.smile.model.domainecaisse.traitement.GetDetailCaisseStructureTrt;
import com.bna.smile.model.domainecommun.service.CRUDservice;

import com.bna.smile.model.domainecommun.traitement.GetContratCptByIdTrt;
import com.oxia.fwk.context.Context;
import com.oxia.fwk.core.ICriteria;
import com.oxia.fwk.core.IExpression;
import com.oxia.fwk.core.ISearchEngine;
import com.oxia.fwk.core.IValueObject;
import com.oxia.fwk.core.ValueObject;

import com.oxia.fwk.searchengine.SearchEngine;

import java.text.SimpleDateFormat;

import java.util.Date;
import java.util.Iterator;
import java.util.List;

public class ValidationVersementMemeAgenceTrt extends Traitement{
    public Context context = ContextHandler.getContext();
    public ValidationVersementMemeAgenceTrt() {
    }
    
    public IValueObject perform (IValueObject vo ) throws Exception{

        OperationMoyPay operationMoyPay = (OperationMoyPay)vo;
    try{
       Long montantAjout            = Long.valueOf(0);
       Long montantAjoutEpargne     = Long.valueOf(0);
       Long montantBrutDinars       = operationMoyPay.getMontDinOmp();
       Long montantNetDinars        = Long.valueOf("0");
       Long montantCommission       = Long.valueOf("0");
       Long montantTva              = Long.valueOf("0");
       
       ///--------------------- Calcul du montant net (Brut - commission -TVA )
        montantTva = operationMoyPay.getMontTvaOmp();
       
        for(Iterator it = operationMoyPay.getDetailOperMoyPaiements().iterator(); it.hasNext(); ){
            DetailOperMoyPaiement detailOperMoy  = (DetailOperMoyPaiement)  it.next();
            montantCommission  = detailOperMoy.getMontValDomp();
        }
        
        montantNetDinars= montantBrutDinars -montantTva - montantCommission   ;  
        
        ///--------------------------------------------------------------
        ///----------- MAJ etat operation moyen payement valide 'V' -----
        ///--------------------------------------------------------------
        operationMoyPay.setCodEtatOmp(Constants.COD_VALIDATION);
        operationMoyPay.getTache().getTacheId().setCodTachTach(Constants.TACHE_VALIDATION);
       
        
        CRUDservice crudService = (CRUDservice)context.getBean("crudservice");
        crudService.update(operationMoyPay);
        ///-------------------------------------------------------------
        
        ///--------------------------------------------------------------
        ///----------- MAJ etat solde du compte  ------------------------
        ///--------------------------------------------------------------
         
        UpdateSoldTrt updateSoldTrt = new UpdateSoldTrt();
        ContratCptSold   contratCptSold =  new    ContratCptSold ();
        
            ///--------------------------------------------------------------
            ///----------- Traitemlent du compte 101 ------------------------
            ///--------------------------------------------------------------
            if (operationMoyPay.getContratCpt().getContratCptId().getCodPrdPrd().equals(Constants.COD_COMPTE_CHEQUE)) {
                GetContratCptByIdTrt getContratCpt = new GetContratCptByIdTrt();
                ContratCptId contratCptId = new ContratCptId();
                contratCptId.setCodPrdPrd(Constants.COD_COMPTE_VERT);
                contratCptId.setCodStrcStrc(operationMoyPay.getContratCpt().getContratCptId().getCodStrcStrc());
                contratCptId.setNumCcptCcpt(operationMoyPay.getContratCpt().getContratCptId().getNumCcptCcpt());
                
                ContratCpt contratVert = new ContratCpt();
                contratVert.setContratCptId(contratCptId);
                getContratCpt.setSecurityFlag(false);
                contratVert= (ContratCpt) getContratCpt.exec(contratVert);
                //----------------------------------------------------------------//
                //--------- Recherche du contrat pour savoir le solde reel -------//
                //----------------------------------------------------------------//
                ContratCpt contratCptReel       = new ContratCpt();
                ContratCptId contratCptIdReel   = new ContratCptId();
                contratCptIdReel = operationMoyPay.getContratCpt().getContratCptId();
                contratCptReel.setContratCptId(contratCptIdReel);
                contratCptReel =  (ContratCpt) getContratCpt.exec(contratCptReel);
                if (contratCptReel.getMontSoldCcpt() != null){
                    contratCptReel.setMontSoldCcpt(Long.valueOf(0));
                }
                Long montantCumul = montantNetDinars + contratCptReel.getMontSoldCcpt().longValue();
                
                //----------------------- S'il y a un contrat valide 165 lié au contrat 101
                if (contratVert != null && contratVert.getCodEtatCcpt().equals(Constants.COD_ETAT_CPT_VALID)){
                
                  if (montantCumul.longValue() >  operationMoyPay.getContratCpt().getMontSminCcpt().longValue() ){
                      montantAjout              = operationMoyPay.getContratCpt().getMontSminCcpt().longValue() - operationMoyPay.getContratCpt().getMontSoldCcpt().longValue() ; 
                      montantAjoutEpargne  = montantCumul.longValue() -  operationMoyPay.getContratCpt().getMontSminCcpt().longValue(); 
                  } else {
                      montantAjout              = montantCumul.longValue() ;
                  }
                //----------------------- aucun contrat lié au contrat 101
                }else {
                      montantAjout              =  montantNetDinars;
                }
                
                //------------- Mis a jours plan epargne ------------------------------//
                if (montantAjoutEpargne.longValue()>0){
                contratCptSold.setContratCpt(contratVert);
                contratCptSold.setSens("C");
                contratCptSold.setSolde(montantAjoutEpargne);
                contratCptSold.setSoldeDevise(operationMoyPay.getMontDevOmp());
                updateSoldTrt.setSecurityFlag(false);
                updateSoldTrt.exec(contratCptSold);
                
                }
                
                //------------- Mis a jours solde du contrat ----------------------//
                if (montantAjout.longValue()>0){
                 contratCptSold.setContratCpt(operationMoyPay.getContratCpt());
                 contratCptSold.setSens("C");
                 contratCptSold.setSolde(montantAjout);
                 //contratCptSold.setSoldeDevise(operationMoyPay.getMontDevOmp());
                 updateSoldTrt.setSecurityFlag(false);
                 updateSoldTrt.exec(contratCptSold);
                 
                }
            
                ///--------------------------------------------------------------
                ///----------- Traitemlent autre que le compte 101 --------------
                ///--------------------------------------------------------------
                
            }  else {
            
                contratCptSold.setContratCpt(operationMoyPay.getContratCpt());
                contratCptSold.setSens("C");
                contratCptSold.setSolde(montantNetDinars);
                contratCptSold.setSoldeDevise(operationMoyPay.getMontDevOmp());
                updateSoldTrt.setSecurityFlag(false);
                updateSoldTrt.exec(contratCptSold);
                
                
                //-------------------- si les contrats sont des plans d'épargne
                if ( operationMoyPay.getContratCpt().getContratCptId().getCodPrdPrd().equals(Constants.COD_PRD_PRD_PEL) ||
                    operationMoyPay.getContratCpt().getContratCptId().getCodPrdPrd().equals(Constants.COD_PRD_PRD_PEM) ||
                    operationMoyPay.getContratCpt().getContratCptId().getCodPrdPrd().equals(Constants.COD_PRD_PRD_PEE) )  {
                    
                   
                    OperationEpargnes operationEpargnes = new OperationEpargnes();
                    operationEpargnes.setContratCpt(operationMoyPay.getContratCpt());
                    operationEpargnes.setDatOpeOpe(DateHandler.strToDate(DateHandler.dateJour()));
                    operationEpargnes.setMntOpeOpe(operationMoyPay.getContratCpt().getMontSoldCcpt());
                    operationEpargnes.setTache(operationMoyPay.getTache());
                    operationEpargnes.setNumOperOmp(operationMoyPay.getNumOperOmp());
                    AjoutOperationEpargneTrt ajoutOPerationEpargneTrt = new  AjoutOperationEpargneTrt();
                    ajoutOPerationEpargneTrt.setSecurityFlag(false);
                    operationEpargnes = (OperationEpargnes) ajoutOPerationEpargneTrt.exec(operationEpargnes);
                    
                }
            
            }
            
        ///--------------------------------------------------------------    
         ///----------- MAJ CAISSE   ------------------------------------
         ///--------------------------------------------------------------
         
          ISearchEngine searchEngine = (SearchEngine)context.getBean("searchEngine"); 
          ICriteria criteria         = searchEngine.createCriteria();
          IExpression expression     = searchEngine.createExpression();
          
          //criteria.add(expression.eq("tache.tacheId.codOperOper",operationMoyPay.getTache().getTacheId().getCodOperOper())); /// tache
          //criteria.add(expression.eq("tache.tacheId.codTachTach",operationMoyPay.getTache().getTacheId().getCodTachTach())); /// tache
           
          criteria.add(expression.eq("numMvtrMc",operationMoyPay.getNumOperOmp() )); /// numero opération
          
          List listMVTCaisse = searchEngine.find(MouvementsCaisses.class,criteria);
           
          if (listMVTCaisse != null && listMVTCaisse.size() > 0 ){
              MouvementsCaisses mvtCaisse = (MouvementsCaisses) listMVTCaisse.get(0);
              mvtCaisse.setCodStatMc(Long.valueOf(1));
              crudService.update(mvtCaisse);
              
              
              GetDetailCaisseStructureTrt getDetailCaisseStructure = new  GetDetailCaisseStructureTrt();
              SituationDetailCaisseStructureVo situation           = new  SituationDetailCaisseStructureVo();
              
              situation.setCodeStructure(mvtCaisse.getCaisseStructure().getCaisseStructureId().getCodStrcStrc());
              situation.setNumeroCaisse(mvtCaisse.getCaisseStructure().getCaisseStructureId().getNumCaisAc());
              situation.setDateJournee(DateHandler.strToDate(DateHandler.dateToStr(mvtCaisse.getCaisseStructure().getCaisseStructureId().getDatJrnJrn())));
              
              getDetailCaisseStructure.setSecurityFlag(false);
              situation= (SituationDetailCaisseStructureVo) getDetailCaisseStructure.exec(situation);
              
              if (operationMoyPay.getDevise().getCodDevDev().equals(Constants.COD_DEV_DINAR)){
                Long nouveauSoldeCaisse = situation.getCaisseDinars().getMontActuCda() +  operationMoyPay.getMontDinOmp();
                situation.getCaisseDinars().setMontActuCda(nouveauSoldeCaisse);
                crudService.update(situation.getCaisseDinars());
              
              } else {
                  //--------------------------------------------------------------------//
                  //---- Si l'opération est en devise chercher la devise conçernée ----//
                  boolean testDeviseExist = false ;
                  for (Iterator it = situation.getCaisseDevises().getDetailCaisDevAgs().iterator(); it.hasNext();){
                     DetailCaisDevAg detailcaisseDevise = (DetailCaisDevAg)it.next();
                      if (detailcaisseDevise.getDevise().getCodDevDev().equals(operationMoyPay.getDevise().getCodDevDev())){
                          Long nouveauSoldeCaisse = detailcaisseDevise.getMontActuDcda() +  operationMoyPay.getMontDevOmp() ;
                          detailcaisseDevise.setMontActuDcda(nouveauSoldeCaisse);
                          crudService.update(detailcaisseDevise);
                          testDeviseExist = true; 
                      }
                  }
                  if (testDeviseExist == false){
                      //-----------------------------------//
                      //------ Création du détail --------//
                      DetailCaisDevAg   detailCaisDevAg = new DetailCaisDevAg();
                      DetailCaisDevAgId detailCaisDevAgId = new DetailCaisDevAgId();
                      
                      detailCaisDevAgId.setCodDevDev(operationMoyPay.getDevise().getCodDevDev());
                      detailCaisDevAgId.setNumDcsDcs(situation.getDetailCaisseStructure().getNumDcsDcs());
                      detailCaisDevAg.setDetailCaisDevAgId(detailCaisDevAgId);
                      detailCaisDevAg.setMontInitDcda(Long.valueOf(0));
                      detailCaisDevAg.setMontCvinDcda(Long.valueOf(0));
                      detailCaisDevAg.setMontActuDcda(Long.valueOf(operationMoyPay.getMontDevOmp()));
                      detailCaisDevAg.setMontCvacDcda(Long.valueOf(operationMoyPay.getMontDinOmp()));
                      detailCaisDevAg.setMontFinDcda(Long.valueOf(0));
                      detailCaisDevAg.setMontCvfnDcda(Long.valueOf(0));
                      crudService.create(detailCaisDevAg);
                      //---------------------------------------//
                      //--- Mettre à jour la caisse Devise ????---//
                  }
              }

          }
          
        ///--------------------------------------------------------------
        ///----------- Execution CRO ----------------------------------(---
        ///--------------------------------------------------------------
        this.setCroFlag(false);
        

        return (operationMoyPay);
        }
           catch (Exception e) {
              com.oxia.fwk.core.Error erreur = new com.oxia.fwk.core.Error();
              StringBuffer text = 
                  new StringBuffer("Erreur dans ValidationVersementMemeAgenceTrt : ");
              text.append(e.toString());
              erreur.setCode("300");
              erreur.setDescription(text.toString());
              erreur.setKey("ValidationVersementMemeAgenceTrt");
              operationMoyPay.addError(erreur);
              return (operationMoyPay);
          }
    }
    
    
    public void genCroText(ValueObject vo) {
            OperationMoyPay operationMoyPay = (OperationMoyPay)vo;            
              /* ---------------------- Garniture de la partie FIXE du CRO ----------------------------------- */
              
              this.setNumRefCro(Long.valueOf(operationMoyPay.getNumOperOmp()));
              this.setLibRefCro("smile.operation_moy_pay");
              this.setDatValCro(new Date());
              this.setCodeStructInitiatrice(operationMoyPay.getStructureInitiatrice().getCodStrcStrc().toString());
             // this.setTypeCro("F");
              this.setCodEtatCro(0);
            //  this.setCodHistCro(1);
              this.setCodeProduit(operationMoyPay.getContratCpt().getContratCptId().getCodPrdPrd().toString());
              //this.setOperationId(operationMoyPay.getOperation().getCodOperOper().toString());
              this.setDateOperation(operationMoyPay.getDatOperOmp());
              SimpleDateFormat formater=new SimpleDateFormat("dd/MM/yyyy");
              formater=new SimpleDateFormat("HH:mm:ss");
              String heureString = formater.format(new Date());
              this.setHeureOperation(heureString);                    
              //this.setMatriculeUser(operationMoyPay.getPersonnelInitiateur().getNumMatrUser());
              this.setTypeOperationCro("O");
              
                
                 /* ------------------Garniture de la partie VARIABLE du CRO----------------------------------  */
                
                StringBuffer cro=new StringBuffer("");
                
                // contratClient
                cro.append("COD_STRC_STRC=");
                cro.append(operationMoyPay.getContratCpt().getContratCptId().getCodStrcStrc()+";");
                cro.append("COD_PRD_PRD=");
                cro.append(operationMoyPay.getContratCpt().getContratCptId().getCodPrdPrd()+";");
                cro.append("NUM_CCPT_CCPT=");
                cro.append(operationMoyPay.getContratCpt().getContratCptId().getNumCcptCcpt()+";");
                
                
                
                cro.append("MONT_DIN_OMP=");
                cro.append(operationMoyPay.getMontDinOmp() +";");
                
                if(operationMoyPay.getDevise().getCodDevDev() !=null ){
                cro.append("COD_DEV_DEV=");
                cro.append(operationMoyPay.getDevise().getCodDevDev() +";");
                }
                if(operationMoyPay.getMontDevOmp()!=null && (!operationMoyPay.getMontDevOmp().equals(0))){
                cro.append("MONT_DEV_OMP=");
                cro.append(operationMoyPay.getMontDevOmp() +";");
                }
                if(operationMoyPay.getMontCdinOmp() !=null && (!operationMoyPay.getMontCdinOmp().equals(0))){
                cro.append("MONT_CDIN_OMP=");
                cro.append(operationMoyPay.getMontCdinOmp() +";");
                }
                if(operationMoyPay.getMontCourOmp() !=null && (!operationMoyPay.getMontCourOmp().equals(0))){
                cro.append("MONT_COUR_OMP=");
                cro.append(operationMoyPay.getMontCourOmp() +";");
                }
               if(operationMoyPay.getMontTvaOmp()  !=null && (!operationMoyPay.getMontTvaOmp().equals(0))){
                cro.append("MONT_TVA_OMP=");
                cro.append(operationMoyPay.getMontTvaOmp());
               }
                
             
                this.setCroText(cro.toString());
         
        }   
        
    public String  getNumeroTache (IValueObject vo) {   
        OperationMoyPay operationMoyPay = (OperationMoyPay)vo;
        if (operationMoyPay.getTache().getOperation() != null && operationMoyPay.getTache().getOperation().getCodOperOper().equals(Constants.COD_OPER_VERSEMENT)){
            return ("10102");
        } else if (operationMoyPay.getTache().getOperation() != null && operationMoyPay.getTache().getOperation().getCodOperOper().equals(Constants.COD_OPER_VERSEMENT_DEPLACE)){
            return ("10302");
        }
        
     return "10102";
    }
    
}
