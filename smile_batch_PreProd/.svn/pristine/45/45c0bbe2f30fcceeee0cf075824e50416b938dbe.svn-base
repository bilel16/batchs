package com.bna.smile.model.domainecontratcompte.moyensPaiement.traitement;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.apache.commons.collections.map.ListOrderedMap;

import com.bna.commun.model.CoTitulaire;
import com.bna.commun.model.DetailOperMoyPaiement;
import com.bna.commun.model.Devise;
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
 * Opposition livret.
 * @author Ramzi
 * @param ParamOpposition
 * @return ParamOpposition
 * @since 28/09/2007
 * 
 */
public class OppositionLivretTrt  extends Traitement{
    private OperationMoyPay  operationMoyPay=null;
    public OppositionLivretTrt() {
    }
    //formation du nouveau numéro de carte

     public IValueObject perform(IValueObject vo) throws Exception{

        ParamOpposition paramOpposition = (ParamOpposition)vo;

        try {
                Context context = ContextHandler.getContext();
                CRUDservice crudService = 
                    (CRUDservice)context.getBean("crudservice");
                
                String typeLivret = paramOpposition.getTypeLivret();
                String numLivret = paramOpposition.getNumLivret(); 
                
                OppositionMoyPaiementDAO oppositionMoyPaiementDAO = 
                    (OppositionMoyPaiementDAO)context.getBean("oppositionMoyPaiementDAO");
                List listDernierEtatMoyenPaiement = null;
                ListOrderedMap dernierEtatMoyenPaiement = null;
                String codEtat = null;
                Date dateOperation = null;
                Date dateCirculation = null;
                  
                //verifier si moyen paiement est en circulation
                dateCirculation=oppositionMoyPaiementDAO.getDateLivretEnCirculation(typeLivret, numLivret,paramOpposition.getContratCpt().getContratCptId().getCodStrcStrc().toString(),paramOpposition.getContratCpt().getContratCptId().getCodPrdPrd().toString(),paramOpposition.getContratCpt().getContratCptId().getNumCcptCcpt().toString());
                if(dateCirculation == null){
                    com.oxia.fwk.core.Error erreurEnOpposition = 
                        new com.oxia.fwk.core.Error();
                    erreurEnOpposition.setCode("MoyPayEnCirculationLivret");
                    erreurEnOpposition.setDescription("Ce livret n'est pas en circulation pour ce compte" );
                    paramOpposition.addError(erreurEnOpposition);
                    return paramOpposition;      
                }
                 
                //verifier si moyen paiement est déja en opposition
                 listDernierEtatMoyenPaiement=oppositionMoyPaiementDAO.getDernierEtatMoyPaiement(Constants.COD_MOYP_TMOY_Livret.toString(),numLivret,paramOpposition.getContratCpt().getContratCptId().getCodStrcStrc().toString(),paramOpposition.getContratCpt().getContratCptId().getCodPrdPrd().toString(),paramOpposition.getContratCpt().getContratCptId().getNumCcptCcpt().toString());
                 if(listDernierEtatMoyenPaiement.size()>0){
                     dernierEtatMoyenPaiement = (ListOrderedMap) listDernierEtatMoyenPaiement.get(0);
                     codEtat = (String)(dernierEtatMoyenPaiement.getValue(0));
                     dateOperation = (Date)(dernierEtatMoyenPaiement.getValue(1));
                    if(codEtat.equals(Constants.COD_ETAT_OPMP_Opposition)){
                        com.oxia.fwk.core.Error erreurEnOpposition = 
                            new com.oxia.fwk.core.Error();
                        erreurEnOpposition.setCode("MoyPayEnOpposition");
                        erreurEnOpposition.setDescription("Ce livret est déja mis en opposition le "+DateHandler.dateToStr(dateOperation));
                        paramOpposition.addError(erreurEnOpposition);
                        return paramOpposition;                   
                    }
                 }

                Tache tache = new Tache();
                TacheId tacheId = new TacheId();
                tacheId.setCodOperOper(Constants.COD_OPER_OPER_OPPOSITION_LIVRET_CLIENT);
                tacheId.setCodTachTach(Constants.COD_TACH_TACH_OPPOSITION_LIVRET_CLIENT);
                tache.setTacheId(tacheId);
                Personnel personnel = new Personnel();
                personnel.setNumMatrUser(paramOpposition.getMatriculeUser());
                
                //remplir l'objet OppositionMoyenPaiement et insertion 
                OppositionMoyenPaiement oppositionMoyenPaiement = new OppositionMoyenPaiement();
                
                oppositionMoyenPaiement.setTache(tache);
                oppositionMoyenPaiement.setPersonnel(personnel);
            
                OppositionMoyenPaiementId oppositionMoyenPaiementId = new OppositionMoyenPaiementId();
                oppositionMoyenPaiementId.setCodMoypTmoy(Constants.COD_MOYP_TMOY_Livret);
                oppositionMoyenPaiementId.setNumMoypOpmp(numLivret);
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
            erreur.setDescription("OppositionLivretTrt " + e.getMessage());
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
    private OperationMoyPay chargerConditionBanque(ParamOpposition paramOpposition){
    
        /*String idContrat = StrHandler.lpad(certificationChequeForm.getContratCpt().getContratCptId().getCodStrcStrc().toString(),'0',3)+
                       StrHandler.lpad(certificationChequeForm.getContratCpt().getContratCptId().getCodPrdPrd().toString(),'0',4)+
                       StrHandler.lpad(certificationChequeForm.getContratCpt().getContratCptId().getNumCcptCcpt().toString(),'0',6);*/
        //  int codPrdPrd,int codOperOper,int numCcptCcpt,int codStrcStrc,int codPrdCpt,int codTpceTpce,String numPcePers,float montant, int nbUnites ,Date dateReference)
        operationMoyPay = new OperationMoyPay();
        
        try {
        
        int codPrdPrd = 0;
        int codOperOper = Long.valueOf(paramOpposition.getCodeOperation()).intValue();
        int numCcptCcpt = 0;
        int codStrcStrc = 0;
        int codPrdCpt = 0;
        int codTpceTpce = Long.valueOf(paramOpposition.getTypePieceActeur()).intValue();
        String numPcePers = paramOpposition.getNumPieceActeur();
        String idContrat = null;
        float montant = 0;
        int nbUnites = 0;
        Date dateReference = DateHandler.timeJour();
        operationMoyPay.extraireConditionBanque(codPrdPrd, codOperOper, numCcptCcpt, codStrcStrc, codPrdCpt,codTpceTpce, numPcePers, idContrat, montant, nbUnites, dateReference);
        } catch (Exception e) {
          com.oxia.fwk.core.Error erreur=new com.oxia.fwk.core.Error();
          erreur.setCode("Technique");
          erreur.setDescription("chargerConditionBanque -- ReceptionCarteBancaireTrt "+e.getMessage());
          logger.error("Exception : ",e);
          throw new RuntimeException(e);
          
        }
        return operationMoyPay;
        
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
        tacheId.setCodOperOper(Constants.COD_OPER_OPER_OPPOSITION_LIVRET_CLIENT);
        tacheId.setCodTachTach(Constants.COD_TACH_TACH_OPPOSITION_LIVRET_CLIENT);
        tache.setTacheId(tacheId);
        
        operationMoyPay.setTache(tache);
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
        // insertion du numero (ou referance BNA) du produit vv
          operationMoyPay.setCodRefbOmp(paramOpposition.getNumLivret());
        operationMoyPay.setStructureInitiatrice(operationMoyPay.getContratCpt().getStructure());
        //date systeme
        operationMoyPay.setDatOperOmp(new Date()); 
        
        return operationMoyPay;
        } catch (Exception e) {
            logger.error("Exception: ",e);
            throw new RuntimeException(e);  
        }
    
    }
    public void genCroText(ValueObject vo) {
    try{
        ParamOpposition paramOpposition = (ParamOpposition)vo;
      /* ---------------------- Garniture de la partie FIXE du CRO ----------------------------------- */
      this.setNumRefCro(Long.valueOf(operationMoyPay.getNumOperOmp()));
      this.setLibRefCro("smile.operation_moy_pay");
      this.setDatValCro(operationMoyPay.getDatValOmp());
      this.setCodeStructInitiatrice(operationMoyPay.getStructureInitiatrice().getCodStrcStrc().toString());
      this.setCodEtatCro(0);
      this.setCodeProduit(operationMoyPay.getContratCpt().getContratCptId().getCodPrdPrd().toString());
      this.setOperationId(operationMoyPay.getTache().getTacheId().getCodOperOper().toString());
      this.setDateOperation(operationMoyPay.getDatOperOmp());
      SimpleDateFormat formater=new SimpleDateFormat("dd/MM/yyyy");
      formater=new SimpleDateFormat("HH:mm:ss");
      String heureString = formater.format(new Date());
      this.setHeureOperation(heureString);                    
    //  this.setMatriculeUser(operationMoyPay.getPersonnelInitiateur().getNumMatrUser());
      this.setTypeOperationCro("O");
      
        
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
        
        // num livret 
        cro.append("NUM_MOYP_OPMP=");
        cro.append(paramOpposition.getNumCarte()+";");
        
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
        return Constants.COD_OPER_OPER_OPPOSITION_LIVRET_CLIENT.toString()+
            StrHandler.lpad(Constants.COD_TACH_TACH_OPPOSITION_LIVRET_CLIENT.toString(),'0',2);
        
    }
    public IValueObject getNumeroDomaine(IValueObject vo){
        ParamOpposition paramOpposition = (ParamOpposition)vo;
        StructureDomaine  structureDomaine  = new StructureDomaine();
        structureDomaine.setCodStrcStrc(paramOpposition.getContratCpt().getContratCptId().getCodStrcStrc());
        structureDomaine.setCodDomDomm(Constants.COD_DOM_CONTRATCOMPTE);
        return structureDomaine;
    
    }

}
