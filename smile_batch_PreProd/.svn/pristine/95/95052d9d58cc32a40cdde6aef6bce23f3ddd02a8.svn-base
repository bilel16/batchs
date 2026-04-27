package com.bna.smile.model.domaineplacement.traitement;

import com.bna.commun.model.BonDeCaisse;
import com.bna.commun.model.ContratCpt;
import com.bna.commun.model.ContratCptId;
import com.bna.commun.model.ContratPlacement;
import com.bna.commun.model.DemandeDecision;
import com.bna.commun.model.DetailsBc;
import com.bna.commun.model.DetailsOperationPlacement;
import com.bna.commun.model.Devise;
import com.bna.commun.model.InteretServi;
import com.bna.commun.model.Operation;
import com.bna.commun.model.OperationMoyPay;
import com.bna.commun.model.Personnel;
import com.bna.commun.model.Produit;
import com.bna.commun.model.Structure;
import com.bna.commun.model.Tache;
import com.bna.commun.model.TacheId;
import com.bna.commun.model.TypeMoyenPaiement;
import com.bna.commun.model.TypePiece;
import com.bna.commun.traitements.Traitement;

import com.bna.commun.traitements.UpdateSoldTrt;
import com.bna.commun.util.ContextHandler;
import com.bna.commun.vo.ContratCptSold;
import com.bna.smile.model.constant.Constants;
import com.bna.smile.model.domainecommun.service.CRUDservice;
import com.bna.smile.model.domaineplacement.model.ParamAbonnementement;
import com.bna.smile.model.domaineplacement.model.ParamBonCaisse;
import com.bna.smile.model.domaineplacement.model.ParamContratPlacement;

import com.bna.smile.model.domaineplacement.model.ParamInsertInteret;

import com.oxia.fwk.context.Context;
import com.oxia.fwk.core.ISearchEngine;
import com.oxia.fwk.core.IValueObject;
import com.oxia.fwk.core.ValueObject;

import java.util.Date;

import org.springframework.orm.hibernate3.HibernateTemplate;

public class ValiderRenouvellementPlacementTrt extends Traitement{
    
    private InsertContratPlacementTrt insertContratPlacementTrt = new InsertContratPlacementTrt();
    private ContratPlacement  contratPlacement = new   ContratPlacement();
    private  ValiderMajDdeDecisionTrt validerMajDdeDecisionTrt = new ValiderMajDdeDecisionTrt();
    private InsertDetailsOpPlacementTrt insertDetailsOpPlacementTrt= new InsertDetailsOpPlacementTrt();
    private InsertOpMoyPaySouscriptionPlacTrt insertOpMoyPaySouscriptionPlacTrt = new InsertOpMoyPaySouscriptionPlacTrt(); 
    public ValiderRenouvellementPlacementTrt() {
    }

    protected void genCroText(ValueObject valueObject) {
    }

    public IValueObject perform(IValueObject iValueObject) {
        ParamContratPlacement paramRenouvellement = (ParamContratPlacement)iValueObject;
        DemandeDecision demandeRenouvel = paramRenouvellement.getDemandeDecision();
        OperationMoyPay operationMoyPay = new OperationMoyPay();
     try{  
  if(paramRenouvellement.getTypeOperation().equals("REJET")){
      demandeRenouvel.setCodEtatDemd(Constants.ETAT_DEM_DECIS_REJETEE); // etat demande = rejet     
     // demandeRenouvel.setLibRmqDemd("Provision non disponible, pas de forçage pour les comptes 103, 121 et les comptes en dinars convertibles");
      demandeRenouvel.setDatRejDemd(paramRenouvellement.getDateComptRenouvel());
      validerMajDdeDecisionTrt.setVerifDomaine(false);
      validerMajDdeDecisionTrt.exec(demandeRenouvel);  
  }else {
      if(paramRenouvellement.isOperationForce()){
              demandeRenouvel.setCodEtatDemd(Constants.ETAT_DEM_RENOUV_FORCE); // etat demande = en attente de forçage (mode transactionnel)                            
              validerMajDdeDecisionTrt.setVerifDomaine(false);
              validerMajDdeDecisionTrt.exec(demandeRenouvel);     
          }else {
               
               if( paramRenouvellement.getContratPlacement() != null){
                insertContratPlacementTrt.setVerifDomaine(false);
                contratPlacement =(ContratPlacement)insertContratPlacementTrt.exec(paramRenouvellement.getContratPlacement());         
               }
               // MAJ demande
               if(!contratPlacement.getNumSeqCpla().equals(null)){
                // demande traitée                  
                demandeRenouvel.setCodEtatDemd(Constants.ETAT_DEM_DECIS_TRAITE); // etat demande = traitée                            
                validerMajDdeDecisionTrt.setVerifDomaine(false);
                paramRenouvellement.setDemandeDecision((DemandeDecision)validerMajDdeDecisionTrt.exec(demandeRenouvel));     
               
                // créer DetailsOperationPlacement
                paramRenouvellement.setDetailsOperationPlacement(affecterDonneesDetailsOperationPlacement(demandeRenouvel, paramRenouvellement));
               
                if(!paramRenouvellement.getDetailsOperationPlacement().equals(null)){
                      paramRenouvellement.getDetailsOperationPlacement().setContratPlacement(contratPlacement);
                        // créer OperationMoyenPaiement op renouvellement
                       insertDetailsOpPlacementTrt.setVerifDomaine(false);
                       insertDetailsOpPlacementTrt.exec(paramRenouvellement.getDetailsOperationPlacement());
                        
                        // insertion opération moy pay
                         insertOpMoyPaySouscriptionPlacTrt.setVerifDomaine(false);
                         paramRenouvellement.getDetailsOperationPlacement().setOperationMoyPay(affecterDonneesOperationMoyenPaiement(paramRenouvellement));
                         operationMoyPay = (OperationMoyPay)insertOpMoyPaySouscriptionPlacTrt.exec(paramRenouvellement.getDetailsOperationPlacement());
                         insererInteretServis(paramRenouvellement, contratPlacement, operationMoyPay);     
                    } 
                    // gestion procuration , au niveau de la demande!!
                  
                    miseAjourSoldeCompte(paramRenouvellement, contratPlacement);
                    
                   genererAbonnement(contratPlacement, paramRenouvellement.getInteretServi().getMontBrutIsrv());
                     
                } // si le contrat placement a été inseré (not null) -- contratPlacement.getNumSeqCpla().equals(null)
             } 
  }
       }catch (Exception e) {
           logger.error("Exception ValiderRenouvellementPlacementTrt : ",e);   
           throw new RuntimeException(e);
       }
        return paramRenouvellement; 
    }
    private void miseAjourSoldeCompte (ParamContratPlacement paramRenouvellement, ContratPlacement contratPlacement){
           Context context = ContextHandler.getContext();
     try{
              ///*** MAJ du montant actualisé dans le contrat compte  
                       ContratCptId contratCptId = contratPlacement.getContratCpt().getContratCptId();
                       ISearchEngine searchEngine = (ISearchEngine)context.getBean("searchEngine");
                         // Charger le ContratCpt existant 
                       ContratCpt contratCpt =(ContratCpt) searchEngine.get(ContratCpt.class,contratCptId);
                       ContratCptSold contratCptSold = new ContratCptSold();
                       contratCptSold.setContratCpt(contratCpt);
                       
                       if(contratPlacement.getCodPintCpla().equals("PRE")){ // type paiement des interets PRE
                        contratCptSold.setSolde(Double.valueOf(Math.rint(contratPlacement.getMontCapCpla()-paramRenouvellement.getInteretServi().getMontIsrvIsrv())).longValue());
                         }else {
                               contratCptSold.setSolde(contratPlacement.getMontCapCpla());
                           }
                       contratCptSold.setSens(Constants.COD_SENS_DB);
                       UpdateSoldTrt updateSoldTrt = new UpdateSoldTrt();
                       updateSoldTrt.setVerifDomaine(false);
                       contratCpt = (ContratCpt)updateSoldTrt.exec(contratCptSold);
         } catch (Exception e) {
                   logger.error("Exception Methode : miseAjourSoldeCompte:  ",e);  
                   throw new RuntimeException(e);               
            }       
       }
    /**
     * Fonction qui retourne un objet DetailsOperationPlacement
     */

    private DetailsOperationPlacement affecterDonneesDetailsOperationPlacement(DemandeDecision demandeRenouvel, ParamContratPlacement paramRenouvellement) {
       
        DetailsOperationPlacement detailsOperationPlacement = new DetailsOperationPlacement();
    try{
       String numPieceSouscrip = demandeRenouvel.getNumNpceDemd();
       Long codPieceSouscrip = Long.valueOf(demandeRenouvel.getTypePiece().getCodTpceTpce());
       
       if (numPieceSouscrip != null && !numPieceSouscrip.equals("")) {
            TypePiece typePieceSouscripteur = new TypePiece();
            typePieceSouscripteur.setCodTpceTpce(codPieceSouscrip);
            detailsOperationPlacement.setTypePieceByCodTpssTpce(typePieceSouscripteur);
            detailsOperationPlacement.setNumNpssDopl(numPieceSouscrip);
            typePieceSouscripteur = null;
            numPieceSouscrip= null ;
            codPieceSouscrip =null ;
        }

        Tache tache = new Tache();
        TacheId tacheId = new TacheId();        
        tacheId.setCodOperOper(Constants.OPER_RENOUVEL_PLAC_AVAN);
        tacheId.setCodTachTach(Long.valueOf("2")); // validation création contrat
        tache.setTacheId(tacheId);                
        detailsOperationPlacement.setTache(tache);
        tache = null; tacheId =null;
        Personnel personnel = new Personnel();
        personnel.setNumMatrUser("9999");
        detailsOperationPlacement.setPersonnel(personnel);
        personnel = null;
        Structure structure = new Structure();
        structure.setCodStrcStrc(demandeRenouvel.getContratCpt().getContratCptId().getCodStrcStrc());
        detailsOperationPlacement.setStructure(structure);
        structure = null;
        detailsOperationPlacement.setDatOperDopl(new Date());
        detailsOperationPlacement.setDatCompDopl(paramRenouvellement.getDateComptRenouvel());
        if (paramRenouvellement.getDateValeur() != null) {
            detailsOperationPlacement.setDatValDopl(paramRenouvellement.getDateValeur());
        } else {
            detailsOperationPlacement.setDatValDopl(detailsOperationPlacement.getDatOperDopl()); // la date valeur est la date comptable
           }
        detailsOperationPlacement.setMontDopDopl(paramRenouvellement.getContratPlacement().getMontCapCpla());
        
    } catch (Exception e) {
                logger.error("Exception Methode : affecterDonnéesDetailsOperationPlacement:  ",e);  
                throw new RuntimeException(e);               
         } 
        return detailsOperationPlacement;
    }    

    private OperationMoyPay affecterDonneesOperationMoyenPaiement (ParamContratPlacement paramRenouvellement){
        OperationMoyPay operationMoyPay = new OperationMoyPay();     
    try{
        ContratPlacement contratPlacement = paramRenouvellement.getContratPlacement();
        Personnel personnelInit = new Personnel();
        personnelInit.setNumMatrUser("9999");
        Operation operation = new Operation();    
        
        Structure structure = new Structure();    
        ContratCpt contratCpt = contratPlacement.getContratCpt();
        operationMoyPay.setContratCpt(contratCpt);
        structure.setCodStrcStrc(contratCpt.getContratCptId().getCodStrcStrc());
        operationMoyPay.setStructureInitiatrice(structure);
        operationMoyPay.setStructureReceptrice(structure);
        
        Devise devise = new Devise();
        devise.setCodDevDev(Constants.COD_DEV_DINAR);
        operationMoyPay.setDevise(devise);

        if (contratPlacement.getProduitPlacement().getCodPrdPlc().equals(Constants.COD_PRD_BC_PLAC) ||
            contratPlacement.getProduitPlacement().getCodPrdPlc().equals(Constants.COD_PRD_BCDC_PLAC) )
          {
          if(contratPlacement.getNumBcCpla() != null){
            operationMoyPay.setNumMoypOmp(contratPlacement.getNumBcCpla().toString());
          }else {
              logger.debug("Le numéro BC est vide");
          }
        }
         
        operationMoyPay.setCodEtatOmp(Constants.COD_VALIDATION);
        operationMoyPay.setPersonnelInitiateur(personnelInit);/// personne initiatrice seulement au cas de retrait ponctuel
        operationMoyPay.setPersonnelValideur(personnelInit);/// personnel initiatrice = personnel validateur
        
        operation.setCodOperOper(Constants.OPER_RENOUVEL_PLAC_AVAN);
        
        Tache tache = new Tache();
        tache.setOperation(operation);
        TacheId tacheId = new TacheId();
        tacheId.setCodOperOper(operation.getCodOperOper());
        tacheId.setCodTachTach(Long.valueOf("2"));
        
        tache.setTacheId(tacheId);
        operationMoyPay.setTache(tache);
        String str = contratPlacement.getNumSeqCpla().toString();
        operationMoyPay.setCodRefbOmp(str);
        operationMoyPay.setDatOperOmp(paramRenouvellement.getDateComptRenouvel());
        operationMoyPay.setDatSystOmp(new Date()); // avec le time ok
       // Affecter mandat opération pour mettre a jour l enveloppe
       
        if(paramRenouvellement.getDemandeDecision() != null && paramRenouvellement.getDemandeDecision().getNumRefdDemd() != null){
                 operationMoyPay.setTypePieceDemandeur(paramRenouvellement.getDemandeDecision().getTypePiece());
                 operationMoyPay.setNumPcedOmp(paramRenouvellement.getDemandeDecision().getNumNpceDemd());
                 operationMoyPay.setNomNomdOmp(paramRenouvellement.getDemandeDecision().getNomNomDemd());
                 operationMoyPay.setNomPrndOmp(paramRenouvellement.getDemandeDecision().getNomPrnDemd());
         }else {
             logger.error("Aucune demande --  Methode : affecterDonneesOperationMoyenPaiement");
         }

        operationMoyPay.setDatValOmp(paramRenouvellement.getDateValeur());
        operationMoyPay.setMontDinOmp(contratPlacement.getMontCapCpla());
       
        operationMoyPay.setCodSensOmp(Constants.COD_SENS_DB);
     // solde avant  -- montsoldccpt / champ, non pa foreign key
        operationMoyPay.setMontSoldCcpt(contratCpt.getMontSoldCcpt());
        
        operationMoyPay.setCodRefcOmp(str.substring(str.length()-8,str.length())); // refc pr le CRO -- num seq cpla
        str = null;
        operationMoyPay.setMontApreOmp(contratCpt.getMontSoldCcpt()-contratPlacement.getMontCapCpla());
     
        Produit prd = new Produit();
        prd.setCodPrdPrd(contratPlacement.getProduitPlacement().getCodPrdPlc());
        operationMoyPay.setProduit(prd); // COD_PRD_OMP rempli avec le code produit placement
        prd=null;
        TypeMoyenPaiement typMoyPay = new TypeMoyenPaiement();
        typMoyPay.setCodMoypTmoy(Constants.COD_TMOY_ESPECE);
        operationMoyPay.setTypeMoyenPaiement(typMoyPay);
        typMoyPay =null;

    // numseqcli  numseqpers  a verifier (co titulair)
    
        // pas de forçage de l'opération si provision non disponible
        if(paramRenouvellement.isOperationForce()){
            operationMoyPay.setBoolForcOmp(Long.valueOf(1)); 
        }else {
            operationMoyPay.setBoolForcOmp(Long.valueOf(0)); 
        }
        
         
        if(contratPlacement.getProduitPlacement().getCodPrdPlc().equals(Constants.COD_PRD_BC_PLAC)
           || contratPlacement.getProduitPlacement().getCodPrdPlc().equals(Constants.COD_PRD_BCDC_PLAC)
            ){ 
          // cas BC
          if(contratPlacement.getNumBcCpla() != null ){
           operationMoyPay.setNumMoypOmp(contratPlacement.getNumBcCpla().toString());
          }
            }
       // a corriger avec le pouvoir
        operationMoyPay.setCodDemOmp("T");
        } catch (Exception e) {
                    logger.error("Exception Methode : affecterDonneesOperationMoyenPaiement:  ",e);  
                    throw new RuntimeException(e);               
             }       
    return operationMoyPay;
    } 
    private void insererInteretServis (ParamContratPlacement paramRenouvellement, ContratPlacement contratPlacement, OperationMoyPay operationMoyPay){
              Context context = ContextHandler.getContext();
        try{
              // insertion interets servis 
              InsertInteretServiTrt insertInteretServiTrt =new InsertInteretServiTrt();
                     
                    InsertOperationInteretServiTrt insertOpMoyPayInteretSouscPlacTrt = new InsertOperationInteretServiTrt();
                    if(contratPlacement.getCodPintCpla().equals("PRE")){ // type paiement des interets PRE
                     InteretServi intServ =new InteretServi();
                     paramRenouvellement.getInteretServi().setContratPlacement(contratPlacement);
                     insertInteretServiTrt.setVerifDomaine(false);
                     intServ = (InteretServi)insertInteretServiTrt.exec(paramRenouvellement.getInteretServi());
                     
                        ///----------- création opération Moyen de payement (interet) ----------------------     
                   HibernateTemplate  hibernateTemplate = (HibernateTemplate) context.getBean("hibernateTemplate");
                   hibernateTemplate.flush();
                   hibernateTemplate.evict(operationMoyPay);
              
                        ParamInsertInteret paramInsertInteret = new ParamInsertInteret();
                        OperationMoyPay operationMoyPayMere = new OperationMoyPay();
                        operationMoyPayMere.setNumOperOmp(operationMoyPay.getNumOperOmp());
                        operationMoyPay.setOperationMoyPayM(operationMoyPayMere);
                    
                         // créer OperationMoyenPaiement op interet servis
                   operationMoyPay =    modifierDonneesOperationMoyenPaiement (paramRenouvellement);
              
                   paramInsertInteret.setOperationMoyPay(operationMoyPay);   
                   paramInsertInteret.setInteretServi(intServ);
                  insertOpMoyPayInteretSouscPlacTrt.setVerifDomaine(false);
                  operationMoyPay = (OperationMoyPay)insertOpMoyPayInteretSouscPlacTrt.exec(paramInsertInteret);
              }
          } catch (Exception e) {
                      logger.error("Exception Methode : insererInteretServis:  ",e);  
                      throw new RuntimeException(e);               
               }       
          }
    private void genererAbonnement (ContratPlacement contratPlacement, Long montantIntServi){
             
         try{
                 // génération abonnement
                 
                  ParamAbonnementement paramAbonnementement = new ParamAbonnementement();
                  paramAbonnementement.setDatDebAbpl(contratPlacement.getDatValCpla());
                  paramAbonnementement.setDatFinAbpl(contratPlacement.getDatEcheCpla());
                  paramAbonnementement.setTypeOperation("S");///*** S:souscription, A:avance
                  paramAbonnementement.setNumSeqCpla(contratPlacement.getNumSeqCpla());
                  paramAbonnementement.setTypeInteret(contratPlacement.getCodFavCpla()); ///*** I:indexé
                  paramAbonnementement.setMontTotAbpl(contratPlacement.getMontCapCpla()); ///*** montant placement
                  paramAbonnementement.setNumTauiCpla(contratPlacement.getNumTauiCpla());
                  paramAbonnementement.setMontItotAbpl(montantIntServi);
                  paramAbonnementement.setContratPlacement(contratPlacement);
                  paramAbonnementement.setOpRenouvellemnt(true);
                   GenererAbonnementTrt genererAbonnementTrt = new GenererAbonnementTrt(); // type de faveur différen d'indexé au TMM
                   genererAbonnementTrt.setVerifDomaine(false);
                   genererAbonnementTrt.exec(paramAbonnementement);
                 
             } catch (Exception e) {
                       logger.error("Exception Methode : genererAbonnement:  ",e);  
                       throw new RuntimeException(e);               
                }       
           }  
      
        private OperationMoyPay modifierDonneesOperationMoyenPaiement (ParamContratPlacement paramRenouvellement){
           
            OperationMoyPay operationMoyPay = paramRenouvellement.getDetailsOperationPlacement().getOperationMoyPay();     
          
        try{
                             Tache tache = new Tache();
                             TacheId tacheId = new TacheId();
                             tacheId.setCodTachTach(Constants.COD_TACH_INTERET_SOUSC_PLAC);
                             tacheId.setCodOperOper(Constants.OPER_INT_PRE_SOUSC_PLAC);
                             tache.setTacheId(tacheId);
                             operationMoyPay.setTache(tache);
                             operationMoyPay.setMontSoldCcpt(operationMoyPay.getMontApreOmp());
                             operationMoyPay.setMontDinOmp(paramRenouvellement.getInteretServi().getMontIsrvIsrv());
                             operationMoyPay.setBoolForcOmp(Long.valueOf(0)); // on laisse le forçage ?? op CR
                             operationMoyPay.setCodSensOmp(Constants.COD_SENS_CR); 
                             operationMoyPay.setDatValOmp(paramRenouvellement.getInteretServi().getDatValIsrv());///*** date valeur interet a partir des CB
                             operationMoyPay.setMontApreOmp(operationMoyPay.getMontApreOmp()+ paramRenouvellement.getInteretServi().getMontIsrvIsrv());
            return operationMoyPay;
        }catch (Exception e) {
                     logger.error("Exception Methode : modifierDonneesOperationMoyenPaiement:  ",e);  
                     throw new RuntimeException(e);               
              } 
        
        } 

        private void remplirNumBC (ContratPlacement contratPlacement, Long codStrcStrc){
                 
             try{
                   // vérifier que le numéro BC existe 
                    if(contratPlacement.getProduitPlacement().getCodPrdPlc().equals(Constants.COD_PRD_BC_PLAC)
                      || contratPlacement.getProduitPlacement().getCodPrdPlc().equals(Constants.COD_PRD_BCDC_PLAC)
                      ){
                        Context context = ContextHandler.getContext();
                        CRUDservice crudService = (CRUDservice)context.getBean("crudservice");           
                         ParamBonCaisse paramBonCaisse = new ParamBonCaisse();
                         GetParamBonCaisseTrt getParamBonCaisseTrt =new GetParamBonCaisseTrt();
                         paramBonCaisse.setNumBonCaisse(contratPlacement.getNumBcCpla());
                         paramBonCaisse.setCodeStructure(codStrcStrc);
                         
                         paramBonCaisse = (ParamBonCaisse)getParamBonCaisseTrt.exec(paramBonCaisse);
                         if(paramBonCaisse != null){
                             DetailsBc detailsBc =new DetailsBc();
                             detailsBc.setContratPlacement(contratPlacement);
                             BonDeCaisse bonCaiss =new BonDeCaisse();
                             bonCaiss.setNumSeqBc(paramBonCaisse.getNumSeqBc());
                             detailsBc.setBonDeCaisse(bonCaiss);
                             detailsBc.setNumBcDbc(contratPlacement.getNumBcCpla());
                             crudService.create(detailsBc);
                         }
                    } 
                 } catch (Exception e) {
                           logger.error("Exception Methode : remplirNumBC:  ",e);  
                           throw new RuntimeException(e);               
                    }       
               }

             
}
