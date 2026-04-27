package com.bna.smile.model.domainecontratcompte.moyensPaiement.traitement;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.acegisecurity.context.SecurityContextHolder;
import org.acegisecurity.userdetails.UserDetails;
import org.apache.commons.collections.map.ListOrderedMap;

import com.bna.commun.model.CoTitulaire;
import com.bna.commun.model.MandatPersonne;
import com.bna.commun.model.OperationMoyPay;
import com.bna.commun.model.OppMoypMandPers;
import com.bna.commun.model.OppMoypMandPersId;
import com.bna.commun.model.OppositionMoyenPaiement;
import com.bna.commun.model.OppositionMoyenPaiementId;
import com.bna.commun.model.Personne;
import com.bna.commun.model.Personnel;
import com.bna.commun.model.StructureDomaine;
import com.bna.commun.model.Tache;
import com.bna.commun.model.TacheId;
import com.bna.commun.model.TypePiece;
import com.bna.commun.traitements.Traitement;
import com.bna.commun.util.ContextHandler;
import com.bna.commun.util.DateHandler;
import com.bna.commun.util.StrHandler;
import com.bna.smile.model.constant.Constants;
import com.bna.smile.model.domainecommun.model.PersonneStrc;
import com.bna.smile.model.domainecommun.service.CRUDservice;
import com.bna.smile.model.domainecommun.traitement.GetPersonneTrt;
import com.bna.smile.model.domainecontratcompte.moyensPaiement.dao.OppositionMoyPaiementDAO;
import com.bna.smile.model.domainecontratcompte.moyensPaiement.model.ParamOpposition;
import com.oxia.fwk.context.Context;
import com.oxia.fwk.core.IValueObject;
import com.oxia.fwk.core.ValueObject;

/**
 * Opposition cheque.
 * @author Ramzi
 * @param ParamOpposition
 * @return ParamOpposition
 * @since 28/09/2007
 * 
 */
public class OppositionChequesTrt extends Traitement{
    //private String datVal ;
    //private String mntTva = "0";
    //private Set listDetailOperMoyPai; 
    private OperationMoyPay  operationMoyPay=null;
    
    public OppositionChequesTrt() {
    }
    //formation du nouveau numéro de carte

     public IValueObject perform(IValueObject vo) throws Exception{

        ParamOpposition paramOpposition = (ParamOpposition)vo;

        try {
            Context context = ContextHandler.getContext();
            CRUDservice crudService = 
                (CRUDservice)context.getBean("crudservice");
            
            int numPrem = Long.valueOf(paramOpposition.getNumPremierChq()).intValue();
            int numDern;
            if(paramOpposition.getNumDernierChq().equals("")){
                numDern = numPrem;
            }else{
                numDern = Long.valueOf(paramOpposition.getNumDernierChq()).intValue();
            }
            OppositionMoyPaiementDAO oppositionMoyPaiementDAO = 
                (OppositionMoyPaiementDAO)context.getBean("oppositionMoyPaiementDAO");
            List listDernierEtatMoyenPaiement = null;
            ListOrderedMap dernierEtatMoyenPaiement = null;
            String codEtat = null;
            Date dateOperation = null;
            Date dateCirculation = null;
            Date dateTelecompensation = null;
            String numChqNEnCirculation = "";
            for(int i=numPrem;i>= numPrem && i<=numDern;i++){           
                //verifier si moyen paiement est en circulation : si pas de foecage
                if(!paramOpposition.getForcageEnCirculation().equals("1")){
                     dateCirculation=oppositionMoyPaiementDAO.getDateChequeEnCirculation(String.valueOf(i),paramOpposition.getContratCpt().getContratCptId().getCodStrcStrc().toString()
                     ,paramOpposition.getContratCpt().getContratCptId().getCodPrdPrd().toString(),paramOpposition.getContratCpt().getContratCptId().getNumCcptCcpt().toString());
                    if(dateCirculation == null){
                        if (i==numPrem)
                            numChqNEnCirculation=i+"";
                        else
                            numChqNEnCirculation = numChqNEnCirculation+","+i;
                    }
                }
                 
                //verifier si moyen paiement est déja en opposition
                 listDernierEtatMoyenPaiement=oppositionMoyPaiementDAO.getDernierEtatMoyPaiement(Constants.COD_MOYP_TMOY_Cheque.toString(),String.valueOf(i),paramOpposition.getContratCpt().getContratCptId().getCodStrcStrc().toString(),paramOpposition.getContratCpt().getContratCptId().getCodPrdPrd().toString(),paramOpposition.getContratCpt().getContratCptId().getNumCcptCcpt().toString());
                 if(listDernierEtatMoyenPaiement.size()>0){
                     dernierEtatMoyenPaiement = (ListOrderedMap) listDernierEtatMoyenPaiement.get(0);
                     codEtat = (String)(dernierEtatMoyenPaiement.getValue(0));
                     dateOperation = (Date)(dernierEtatMoyenPaiement.getValue(1));
                    if(codEtat.equals(Constants.COD_ETAT_OPMP_Opposition)){
                        com.oxia.fwk.core.Error erreurEnOpposition = 
                            new com.oxia.fwk.core.Error();
                        erreurEnOpposition.setCode("MoyPayEnOpposition");
                        erreurEnOpposition.setDescription("Le chèque numéro "+i+" est déja mis en opposition le "+DateHandler.dateToStr(dateOperation));
                        paramOpposition.addError(erreurEnOpposition);
                        return paramOpposition;                   
                    }
                 }
                    
                //verifier si moyen paiement est déja telecompensé
                dateTelecompensation = oppositionMoyPaiementDAO.getDateTelecompensation(Constants.COD_MOYP_TMOY_Cheque.toString(),String.valueOf(i));
                if(dateTelecompensation != null){
                    com.oxia.fwk.core.Error erreurChqTelecompense = 
                        new com.oxia.fwk.core.Error();
                    erreurChqTelecompense.setCode("MoyPayTelecompense");
                    erreurChqTelecompense.setDescription("Le chèque numéro "+i+" est déja telecompensé le "+DateHandler.dateToStr(dateTelecompensation));;
                    paramOpposition.addError(erreurChqTelecompense);
                    return paramOpposition;      
                }
            }
            //test si liste des cheques en circulation 
            if(!numChqNEnCirculation.equals("")){
                 com.oxia.fwk.core.Error erreurEnOpposition = 
                     new com.oxia.fwk.core.Error();
                 erreurEnOpposition.setCode("MoyPayEnCirculation");
                 erreurEnOpposition.setDescription("Le(s) chèque(s) numéro "+numChqNEnCirculation+" n'est(ne sont) pas en circulation" );
                 paramOpposition.addError(erreurEnOpposition);
                 return paramOpposition;
            }
            Tache tache = new Tache();
            TacheId tacheId = new TacheId();
            tacheId.setCodOperOper(Constants.COD_OPER_OPER_OPPOSITION_CHQ_CLIENT);
            tacheId.setCodTachTach(Constants.COD_TACH_TACH_OPPOSITION_CHQ_CLIENT);
            tache.setTacheId(tacheId); 
            Personnel personnel = new Personnel();
            personnel.setNumMatrUser(paramOpposition.getMatriculeUser());
            //remplir l'objet OppositionMoyenPaiement et insertion 
            for(int i=numPrem;i>= numPrem && i<=numDern;i++){   
                OppositionMoyenPaiement oppositionMoyenPaiement = new OppositionMoyenPaiement();
                
                oppositionMoyenPaiement.setTache(tache);
                oppositionMoyenPaiement.setPersonnel(personnel);
            
                OppositionMoyenPaiementId oppositionMoyenPaiementId = new OppositionMoyenPaiementId();
                oppositionMoyenPaiementId.setCodMoypTmoy(Constants.COD_MOYP_TMOY_Cheque);
                oppositionMoyenPaiementId.setNumMoypOpmp(String.valueOf(i));
                String  d= DateHandler.dateJour();
                oppositionMoyenPaiementId.setDatOperOpmp(DateHandler.timeJour());
                oppositionMoyenPaiement.setOppositionMoyenPaiementId(oppositionMoyenPaiementId);
                
                oppositionMoyenPaiement.setCodEtatOpmp(Constants.COD_ETAT_OPMP_Opposition);
                oppositionMoyenPaiement.setCodActrOpmp(paramOpposition.getTypeActeur());
                
                oppositionMoyenPaiement.setContratCpt(paramOpposition.getContratCpt());
                
                TypePiece typePiece = new TypePiece();
                typePiece.setCodTpceTpce(Long.valueOf(paramOpposition.getTypePieceActeur()));
                oppositionMoyenPaiement.setTypePiece(typePiece);
                
                oppositionMoyenPaiement.setNumPceOpmp(paramOpposition.getNumPieceActeur());
                
                if(paramOpposition.getNumActJudiciaire() != null){
                    oppositionMoyenPaiement.setNumActjOpmp(paramOpposition.getNumActJudiciaire());
                    oppositionMoyenPaiement.setDatActjOpmp(paramOpposition.getDatActJudiciaire());
                }
                
                oppositionMoyenPaiement.setCodMotfOpmp(paramOpposition.getMotifOpposition());
                if(paramOpposition.getNumJugement() != null){
                    oppositionMoyenPaiement.setNumJugfOpmp(paramOpposition.getNumJugement());
                    oppositionMoyenPaiement.setDatJugfOpmp( DateHandler.strToDate(paramOpposition.getDatJugement()));
                }
                
                if(paramOpposition.getTypeActeur().equals("C")){
                        // cas cotitulaire
                        if(paramOpposition.getListCotitulaire()!=null && paramOpposition.getListCotitulaire().size()>0 ){
                             CoTitulaire cotitulaire = (CoTitulaire)paramOpposition.getListCotitulaire().get(0);
                             oppositionMoyenPaiement.setCoTitulaire(cotitulaire);
                        }         
                }   
                
                //insertion dans la table opposition_moyen_paiement
                crudService.create(oppositionMoyenPaiement);
                                           
                //insertion la liste des mandataires si cas mandataire
                if(paramOpposition.getTypeActeur().equals("M")){
                    if(paramOpposition.getMandat().getCodSignMand().equals("S")){
                      // signature séparée
                      /// insertion juste du demandeur  
                       PersonneStrc personneStrc = new PersonneStrc();
                       personneStrc.setCodTpceTpce(Long.valueOf(paramOpposition.getTypePieceActeur()));
                       personneStrc.setNumPcePers(paramOpposition.getNumPieceActeur());
                       GetPersonneTrt getPersonneTrt = new GetPersonneTrt();
                       Personne personne = (Personne)getPersonneTrt.exec(personneStrc);
                       
                      createOppMoypMandPers(oppositionMoyenPaiementId,paramOpposition.getMandat().getNumMandMand(), personne.getNumSeqPers(),crudService);         
                                            
                    }else{
                         // signature conjointe(insertion de tous les signataires)           
                         for (Iterator it = paramOpposition.getListMandatPersonne().iterator();it.hasNext(); ) {          
                            MandatPersonne mandatPersonne = (MandatPersonne)it.next(); 
                            Long numSeqPers = mandatPersonne.getMandatPersonneId().getNumSeqPers();
                            createOppMoypMandPers(oppositionMoyenPaiementId,paramOpposition.getMandat().getNumMandMand(), numSeqPers, crudService);
                         }               
                    }
                }
                
                //syncronisation pascal
                this.sychronisationPascal(oppositionMoyenPaiement);
                    
            }
            //int a = 1/0;
         /*   
            //Extraire Condition de banque
            //detailOperCarte.setCarteBancaire(carteBancaire);
            operationMoyPay = chargerConditionBanque(paramOpposition);
            
            // mettre à jour le solde du contrat:
            ContratCptSold contratCptSold = new ContratCptSold();
            contratCptSold.setContratCpt(paramOpposition.getContratCpt()); 
            Long.valueOf(Double.valueOf(operationMoyPay.getMontTvaOmp()).longValue());
            Long totalMnt =  Long.valueOf(Double.valueOf(operationMoyPay.getMontTvaOmp()).longValue()) + calculerCommissions(operationMoyPay.getDetailOperMoyPaiements());
            contratCptSold.setSolde(totalMnt);
            contratCptSold.setSens(Constants.COD_SENS_DB);
            UpdateSoldTrt updateSoldTrt = new UpdateSoldTrt();
            ContratCpt ContratCptMaj = (ContratCpt)updateSoldTrt.execute(contratCptSold);      
            
            //Ecriture dans la table operation moyen de payement
            ///Chargement de operation moy pay
            operationMoyPay = chargementOperMoyPay(paramOpposition, operationMoyPay);
            
            
            InsertOperationMoyPayTrt insertOperationMoyPayTrt = new InsertOperationMoyPayTrt(); 
            operationMoyPay = (OperationMoyPay)insertOperationMoyPayTrt.execute(operationMoyPay); 
                           
            //Genaration Cro
            this.setCroFlag(true); 
            
            
            */
            
            
     } catch (Exception e) {
            com.oxia.fwk.core.Error erreur = new com.oxia.fwk.core.Error();
            erreur.setCode("Technique");
            erreur.setDescription("OppositionChequesTrt " + e.getMessage());
            paramOpposition.addError(erreur);
            logger.error("Exception : ",e);
            throw new RuntimeException(e);         
    }
        return paramOpposition;
    }

    private void createOppMoypMandPers(OppositionMoyenPaiementId oppositionMoyenPaiementId, Long numMandat, Long numSeqPers,
                                       CRUDservice crudService) {
       try{
        OppMoypMandPers oppMoypMandPers = new OppMoypMandPers();
        OppMoypMandPersId oppMoypMandPersId = new OppMoypMandPersId();
        oppMoypMandPersId.setNumMandMand(numMandat);               
        oppMoypMandPersId.setNumSeqPers(numSeqPers);
        oppMoypMandPersId.setNumMoypOpmp(oppositionMoyenPaiementId.getNumMoypOpmp());
        oppMoypMandPersId.setDatOperOpmp(oppositionMoyenPaiementId.getDatOperOpmp());
        oppMoypMandPersId.setCodMoypTmoy(oppositionMoyenPaiementId.getCodMoypTmoy());
        oppMoypMandPers.setOppMoypMandPersId(oppMoypMandPersId);
       
        
        crudService.create(oppMoypMandPers);
        } catch (Exception e) {
            logger.error("Exception: ",e);
            throw new RuntimeException(e);  
        }
    }
  /*  
    public OperationMoyPay affecterDonneesOperationMoyenPaiement (OppositionMoyenPaiement oppositionMoyenPaiement){
        
        
            OperationMoyPay operationMoyPay = new OperationMoyPay();          
            Personnel personnelInit = new Personnel();
            personnelInit.setNumMatrUser(oppositionMoyenPaiement.getPersonnel().getNumMatrUser());
            Operation operation = new Operation();    
            Structure structureInit = new Structure();    
            structureInit.setCodStrcStrc(oppositionMoyenPaiement.getContratCpt().getStructure().getCodStrcStrc());
             
            Structure structureRecep = new Structure();    
            structureRecep.setCodStrcStrc(oppositionMoyenPaiement.getContratCpt().getStructure().getCodStrcStrc());           
             
            operationMoyPay.setContratCpt(oppositionMoyenPaiement.getContratCpt());
              
            operationMoyPay.setStructureInitiatrice(structureInit);
            operationMoyPay.setStructureReceptrice(structureRecep);
            
            GetDetailContratCmd getDetailContratCmd = new GetDetailContratCmd();
            ContratCpt cpt = (ContratCpt)getDetailContratCmd.execute(oppositionMoyenPaiement.getContratCpt().getContratCptId());
            
            Devise devise = new Devise();
            devise.setCodDevDev(cpt.getDevise().getCodDevDev());
            operationMoyPay.setDevise(devise);
            
            Produit produitPlacOmp =new Produit();
            
            operationMoyPay.setCodRefcOmp(null);
            operationMoyPay.setProduit(oppositionMoyenPaiement.getContratCpt().getProduit());
            
            if (autresOperationsPlacementForm.getContratPlacement().getNumBcCpla()!=null && autresOperationsPlacementForm.getContratPlacement().getNumBcCpla().toString()!="")
            operationMoyPay.setNumMoypOmp(autresOperationsPlacementForm.getContratPlacement().getNumBcCpla().toString());

            operationMoyPay.setCodEtatOmp(Constants.COD_VALIDATION);
            operationMoyPay.setPersonnelInitiateur(personnelInit);/// personne initiatrice seulement au cas de retrait ponctuel
            operationMoyPay.setPersonnelValideur(personnelInit);/// personnel initiatrice = personnel validateur
            
            //operationMoyPay.setCodRefcOmp(avancRembLiquidValidPlacementForm.getInitialisationView().getCodeOperation());
            operation.setCodOperOper(Long.valueOf(Constants.COD_OPER_RECUP_BC_PLAC));
            
            Tache tache = new Tache();
            tache.setOperation(operation);
            TacheId tacheId = new TacheId();
            tacheId.setCodOperOper(operation.getCodOperOper());            
            tacheId.setCodTachTach(Constants.COD_TACH_RECUP_BC_PLAC);
           
            tache.setTacheId(tacheId);
            operationMoyPay.setTache(tache);
            
            TraitementConditionBanque traitementConditionBanque = getCB(autresOperationsPlacementForm,request);
            
            operationMoyPay.setDatOperOmp(DateHandler.strToDate(paramAgence.getDateComptable()));
            operationMoyPay.setDatSystOmp(new Date());           
            operationMoyPay.setDatValOmp(DateHandler.strToDate(traitementConditionBanque.getDatevaleur()));
            
           
            operationMoyPay.setTypePieceDemandeur(cpt.getClient().getPersonne().getTypePiece());
            operationMoyPay.setNumPcedOmp(cpt.getClient().getPersonne().getNumPcePers().toString());
            
            traitementConditionBanque.setNumPcePers(cpt.getClient().getPersonne().getNumPcePers().toString());            
            if (cpt !=null)
                operationMoyPay.setMontSoldCcpt(cpt.getMontSoldCcpt());
            
            operationMoyPay.setMontDinOmp(autresOperationsPlacementForm.getContratPlacement().getMontCapCpla());
            operationMoyPay.setCodDemOmp("T");
            operationMoyPay.setCodSensOmp(Constants.COD_SENS_CR);
            operationMoyPay.setMontApreOmp(autresOperationsPlacementForm.getContratPlacement().getContratCpt().getMontSoldCcpt() + autresOperationsPlacementForm.getContratPlacement().getMontCapCpla());
            operationMoyPay.setLibMotfOmp("Recuperation BC");
          
          
            return operationMoyPay;
        } 
    
    
    
    private OperationMoyPay chargerConditionBanque(ParamOpposition paramOpposition){
        
        
                          
    operationMoyPay = new OperationMoyPay(); 
   
    try {
        
        TraitementConditionBanque traitementConditionBanque = getCB(paramOpposition);
      //  String dateValeur = traitementConditionBanque.getDatevaleur();
        float commission = traitementConditionBanque.getValeurCommission();
        float tvaComm = traitementConditionBanque.getMntTva();
        Date dateValeurComm =DateHandler.strToDate(traitementConditionBanque.getDatevaleurComm());
        operationMoyPay.setDatValOmp(dateValeurComm);
        operationMoyPay.setMont(dateValeurComm);
         
        logger.info("Condition banque chargée");
      } catch (Exception e) {
     // System.out.println(e.getMessage());
              com.oxia.fwk.core.Error erreur=new com.oxia.fwk.core.Error();
              erreur.setCode("Technique");
              erreur.setDescription("chargerConditionBanque -- ReceptionCarteBancaireTrt "+e.getMessage());
              throw new RuntimeException(e);
              
      }
      return operationMoyPay;
       
    }

    private TraitementConditionBanque getCB(ParamOpposition paramOpposition) {
        TraitementConditionBanque traitementConditionBanque = new TraitementConditionBanque();

        try {
           
            traitementConditionBanque.setCodOperOper(paramOpposition.getCodeOperation());
            ContratCpt contratCpt=paramOpposition.getContratCpt();
            //traitementConditionBanque.setCodPrdPrd(null);
            //traitementConditionBanque.setIdContrat(contratPlacement.getNumSeqCpla().toString());
            //traitementConditionBanque.setMontant(contratPlacement.getMontCapCpla().toString());

            if (contratCpt!=null){
                traitementConditionBanque.setNumCcptCcpt(contratCpt.getContratCptId().getNumCcptCcpt().toString());
                traitementConditionBanque.setCodStrcStrc(contratCpt.getContratCptId().getCodStrcStrc().toString());
                traitementConditionBanque.setCodPrdCpt(contratCpt.getContratCptId().getCodPrdPrd().toString());
                traitementConditionBanque.setCodTpceTpce(contratCpt.getClient().getPersonne().getTypePiece().getCodTpceTpce().toString());
                traitementConditionBanque.setNumPcePers(contratCpt.getClient().getPersonne().getNumPcePers());
            }
            Long nbUnites = Long.valueOf(paramOpposition.getNumDernierChq())- Long.valueOf(paramOpposition.getNumPremierChq()) +1; 
            traitementConditionBanque.setNbUnites(nbUnites.toString());
            traitementConditionBanque.setDateReference(DateHandler.dateJour());
          
            //affectaion du type du compte en devise ou en dinar dans le Palier
            traitementConditionBanque.getPalierChar().clear();
            if(contratCpt.getProduit().getCodTprdPrd().equals("DEV")){
                traitementConditionBanque.getPalierChar().add("29");            
            }else{
                traitementConditionBanque.getPalierChar().add("28");   
            }
            
            traitementConditionBanque.getCB();
            
           
        } catch (Exception e) {
                throw new RuntimeException(e);
        }
        return traitementConditionBanque;   
    }
   
    public Long calculerCommissions(Set listDetailOperMoyPai){
    try{
        Long sommeCommissions = Long.valueOf(0);
        for (Iterator it = listDetailOperMoyPai.iterator();it.hasNext(); ) {   
           DetailOperMoyPaiement detailOperMoyPaiement = (DetailOperMoyPaiement)it.next();
           sommeCommissions = sommeCommissions + detailOperMoyPaiement.getMontValDomp(); 
        }
        return sommeCommissions;
        } catch (Exception e) {
            logger.error("Exception: ",e);
            throw new RuntimeException(e);  
        }
    }
    private OperationMoyPay chargementOperMoyPay(ParamOpposition paramOpposition, OperationMoyPay operationMoyPay){
        
        try{
        operationMoyPay.setContratCpt(paramOpposition.getContratCpt());
        
        Tache tache = new Tache();
        TacheId tacheId = new TacheId();
        tacheId.setCodOperOper(Constants.COD_OPER_OPER_OPPOSITION_CHQ_CLIENT);
        tacheId.setCodTachTach(Constants.COD_TACH_TACH_OPPOSITION_CHQ_CLIENT);
        tache.setTacheId(tacheId);
        operationMoyPay.setDatOperOmp(DateHandler.timeJour());
        operationMoyPay.setCodDemOmp(paramOpposition.getTypeActeur());
        TypePiece typePiece = new TypePiece();
        typePiece.setCodTpceTpce(Long.valueOf(paramOpposition.getTypePieceActeur()));
        operationMoyPay.setTypePieceDemandeur(typePiece);
        operationMoyPay.setNumPcedOmp(paramOpposition.getNumPieceActeur());
        operationMoyPay.setCodEtatOmp(Constants.COD_VALIDATION);
        operationMoyPay.setCodSensOmp(Constants.COD_SENS_DB);
        Personnel personnel = new Personnel();
        personnel.setNumMatrUser(paramOpposition.getMatriculeUser());
        operationMoyPay.setPersonnelInitiateur(personnel);
        operationMoyPay.setPersonnelValideur(personnel);
        //devise commision toujours en dinars
        Devise devise = new Devise();
        devise.setCodDevDev(Constants.COD_DEV_DINAR);
        operationMoyPay.setDevise(devise);
        // insertion du produit vendu
        //operationMoyPay.setProduit(detailOperCarte.getCarteBancaire().getTypeCarte().getProduit());
        // insertion du numero (ou referance BNA) du produit vv
        operationMoyPay.setCodRefbOmp(paramOpposition.getNumPremierChq()+"|"+paramOpposition.getNumDernierChq());
        operationMoyPay.setStructureInitiatrice(operationMoyPay.getContratCpt().getStructure());
        //date systeme
         operationMoyPay.setDatOperOmp(new Date());
        
        return operationMoyPay;
        } catch (Exception e) {
                logger.error("Exception: ",e);
                throw new RuntimeException(e);  
        }
        
    }
    */
    public void genCroText(ValueObject vo) {
            ParamOpposition paramOpposition = (ParamOpposition)vo;
            Object obj = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
                     com.oxia.security.abc.model.Personnel user = null;
                     if (obj instanceof UserDetails) {
                         user = (com.oxia.security.abc.model.Personnel)obj;
            }
        try{
              
              this.setNumRefCro(Long.valueOf(operationMoyPay.getNumOperOmp()));
              this.setLibRefCro("smile.operation_moy_pay");
              this.setDatValCro(operationMoyPay.getDatValOmp());
              this.setCodeStructInitiatrice(operationMoyPay.getStructureInitiatrice().getCodStrcStrc().toString());
              this.setCodStrcImpt(operationMoyPay.getStructureInitiatrice().getCodStrcStrc());
              this.setCodEtatCro(0);
              
              this.setCodeProduit(operationMoyPay.getContratCpt().getContratCptId().getCodPrdPrd().toString());
              this.setOperationId(operationMoyPay.getTache().getTacheId().getCodOperOper().toString());
              this.setDateOperation(operationMoyPay.getDatOperOmp());
              SimpleDateFormat formater=new SimpleDateFormat("dd/MM/yyyy");
              formater=new SimpleDateFormat("HH:mm:ss");
              String heureString = formater.format(new Date());
              this.setHeureOperation(heureString);     
              
              this.setDatExecCro(operationMoyPay.getDatSystOmp()); // date system
              this.setTypeOperationCro("O");
              this.setCodTachTach(operationMoyPay.getTache().getTacheId().getCodTachTach().longValue());
              this.setCodRefcOmp(operationMoyPay.getNumOperOmp());
              this.setNumCinUser(user.getNumMatrUser());
              this.setCodTypUser(user.getMatriculeTyp());
              
              
                
                 /* ------------------Garniture de la partie VARIABLE du CRO----------------------------------  */
                
                StringBuffer cro=new StringBuffer("");
                
                // contrat Client
                cro.append("COD_STRC_STRC=");
                cro.append(operationMoyPay.getContratCpt().getContratCptId().getCodStrcStrc()+";");
                cro.append("COD_PRD_PRD=");
                cro.append(operationMoyPay.getContratCpt().getContratCptId().getCodPrdPrd()+";");
                cro.append("NUM_CCPT_CCPT=");
                cro.append(operationMoyPay.getContratCpt().getContratCptId().getNumCcptCcpt()+";");
                
                //type piece acteur opposition
                cro.append("COD_DEM_TPCE=");
                cro.append(operationMoyPay.getTypePieceDemandeur().getCodTpceTpce()+";");
                
                //num piece acteur opposition
                cro.append("NUM_PCED_OMP=");
                cro.append(operationMoyPay.getNumPcedOmp()+";");
                
                //type demandeur/acteur opposition : Titulaitre, mandataire...
                cro.append("COD_DEM_OMP=");
                cro.append(operationMoyPay.getCodDemOmp()+";");  
                
                // num premier cheque
                cro.append("NUM_DEB_OPMP=");
                cro.append(paramOpposition.getNumPremierChq()+";");
                
                // num dernier cheque
                cro.append("NUM_FIN_OPMP=");
                cro.append(paramOpposition.getNumDernierChq()+";");
                
                // motif opposition
                cro.append("COD_MOTF_OPMP=");
                cro.append(paramOpposition.getMotifOpposition()+";");
                
                // acte juridique
                cro.append("NUM_ACTJ_OPMP=");
                cro.append(paramOpposition.getNumActJudiciaire()+";");
                
            
             
                this.setCroText(cro.toString());
            } catch (Exception e) {
                logger.error("Exception: ",e);
                throw new RuntimeException(e);  
            }
        } 
        
    
    public String getNumeroTache(IValueObject vo){
        return Constants.COD_OPER_OPER_OPPOSITION_CHQ_CLIENT.toString()+
            StrHandler.lpad(Constants.COD_TACH_TACH_OPPOSITION_CHQ_CLIENT.toString(),'0',2);
        
    }
    public void genererSynchronisationPascal(ValueObject vo) {
    
        OppositionMoyenPaiement oppositionMoyenPaiement = (OppositionMoyenPaiement)vo;   
        
        try{
        DateFormat myformat1 = new SimpleDateFormat("ddMMyy");
        DateFormat myformat2 = new SimpleDateFormat("ddMMyyyy");
             
        //partie fixe
        this.setCodeOperationSynch(oppositionMoyenPaiement.getTache().getTacheId().getCodOperOper());
        this.setCodeTacheSynch(oppositionMoyenPaiement.getTache().getTacheId().getCodTachTach());
        this.setDateOperationSynch(new Date());
        this.setCodeStructureSynch(oppositionMoyenPaiement.getContratCpt().getContratCptId().getCodStrcStrc());
        
        //partie variable
        String numCompte = StrHandler.lpad(oppositionMoyenPaiement.getContratCpt().getContratCptId().getCodPrdPrd().toString(),'0',4) +
                           StrHandler.lpad(oppositionMoyenPaiement.getContratCpt().getContratCptId().getNumCcptCcpt().toString(),'0',6); 
        
        String numCheque = " 0000000";
        if(oppositionMoyenPaiement.getOppositionMoyenPaiementId().getNumMoypOpmp() != null )            
            numCheque =  " "+StrHandler.lpad(oppositionMoyenPaiement.getOppositionMoyenPaiementId().getNumMoypOpmp(),'0',7);

        String dateOpposition = "      ";
        if(oppositionMoyenPaiement.getOppositionMoyenPaiementId().getDatOperOpmp() != null )            
            dateOpposition =  myformat1.format(oppositionMoyenPaiement.getOppositionMoyenPaiementId().getDatOperOpmp());

        String matricule = "0000";
        if(oppositionMoyenPaiement.getPersonnel().getNumMatrUser() != null )            
            matricule =  StrHandler.lpad(oppositionMoyenPaiement.getPersonnel().getNumMatrUser(),'0',4);
        
        
        
        String partieVariable =  numCompte + numCheque + dateOpposition + matricule;
        
        this.setTextSynch(partieVariable);
        } catch (Exception e) {
            logger.error("Exception: ",e);
            throw new RuntimeException(e);  
        }
        
    }
    public IValueObject getNumeroDomaine(IValueObject vo){
        ParamOpposition paramOpposition = (ParamOpposition)vo;
        StructureDomaine  structureDomaine  = new StructureDomaine();
        structureDomaine.setCodStrcStrc(paramOpposition.getContratCpt().getContratCptId().getCodStrcStrc());
        structureDomaine.setCodDomDomm(Constants.COD_DOM_CONTRATCOMPTE);
        return structureDomaine;
    
    }


}
