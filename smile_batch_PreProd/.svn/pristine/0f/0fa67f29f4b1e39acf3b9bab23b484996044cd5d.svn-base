package com.bna.smile.web.moyenPaiement.demandeCarteBnacaire.actions;

import com.bna.commun.model.CarteBancaire;
import com.bna.commun.model.CoTitulaire;
import com.bna.commun.model.ContratCpt;
import com.bna.commun.model.ContratCptId;
import com.bna.commun.model.DemandeCarte;
import com.bna.commun.model.DemandeCarteMandatPersonne;
import com.bna.commun.model.DemandeCarteMandatPersonneId;
import com.bna.commun.model.DemandeCheque;
import com.bna.commun.model.DemandeChequeMandatPersonne;
import com.bna.commun.model.DemandeChequeMandatPersonneId;
import com.bna.commun.model.Mandat;
import com.bna.commun.model.MandatOperation;
import com.bna.commun.model.MandatPersonne;
import com.bna.commun.model.MotifRejet;
import com.bna.commun.model.Operation;
import com.bna.commun.model.Personne;
import com.bna.commun.model.Personnel;
import com.bna.commun.model.StructureDomaine;
import com.bna.commun.model.Tache;
import com.bna.commun.model.TacheId;
import com.bna.commun.model.TypeCarte;
import com.bna.commun.model.TypeConfection;
import com.bna.commun.util.DateHandler;import com.bna.commun.util.StrHandler;
import com.bna.commun.util.DateHandler;import com.bna.commun.util.StrHandler;
import com.bna.commun.util.ContextHandler;
import com.bna.commun.util.SmileUtil;
import com.bna.commun.vo.PrimitiveVO;
import com.bna.smile.model.constant.Constants;
import com.bna.smile.model.domainecommun.commande.GetDetailContratCmd;
import com.bna.smile.model.domainecommun.commande.GetListMembreCotitulaireCmd;
import com.bna.smile.model.domainecommun.commande.GetOperationCmd;
import com.bna.smile.model.domainecommun.commande.GetPersonneCptCmd;
import com.bna.smile.model.domainecommun.model.ContratPersonne;
import com.bna.smile.model.domainecommun.model.Listes;
import com.bna.smile.model.domainecommun.model.PersonneCpt;
import com.bna.smile.model.domainecommun.model.PersonneStrc;
import com.bna.smile.model.domainecommun.service.ContratCompteService;
import com.bna.smile.model.domainecontratcompte.moyensPaiement.commande.GetCartesEligibleContratCmd;
import com.bna.smile.model.domainecontratcompte.moyensPaiement.commande.InsertDemandeChequeCmd;
import com.bna.smile.model.domainecontratcompte.moyensPaiement.commande.InsertDemandeChequeMandatPersonneCmd;
import com.bna.smile.model.domainecontratcompte.moyensPaiement.commande.PecDemandeCarteCmd;
import com.bna.smile.model.domainecontratcompte.moyensPaiement.commande.VerifDemandeCarteEnCoursCmd;
import com.bna.smile.model.domainecontratcompte.moyensPaiement.commande.VerifPossedeTypeCarteCmd;
import com.bna.smile.model.domainecontratcompte.moyensPaiement.model.DemandeCarteSignataire;
import com.bna.smile.model.domainecontratcompte.moyensPaiement.model.ParamDemandeCheque;
import com.bna.smile.model.domainecontratcompte.moyensPaiement.model.PersonneTypeCarteCpt;
import com.bna.smile.model.domainecontratcompte.moyensPaiement.model.TypeCarteCpt;
import com.bna.smile.model.domainecontratcompte.moyensPaiement.traitement.GetCartesEligibleContratTrt;
import com.bna.smile.model.domainecontratcompte.procuration.commande.GetDetailMandatCmd;
import com.bna.smile.model.domainecontratcompte.procuration.commande.GetListMandatOperationPersonneContratOperationCmd;
import com.bna.smile.model.domainecontratcompte.procuration.dao.MandatDAO;
import com.bna.smile.model.domainecontratcompte.procuration.model.DetailMandat;
import com.bna.smile.model.domainecontratcompte.procuration.model.ListMandatOperationVo;
import com.bna.smile.model.domainecontratcompte.procuration.model.ParamMandatOperationVo;
import com.bna.smile.model.domaineguichet.commande.VerifierInterditChequierCmd;
import com.bna.smile.web.commun.model.ParamAgence;
import com.bna.smile.web.commun.util.SessionUtil;
import com.bna.smile.web.moyenPaiement.demandeCarteBnacaire.forms.PecDemandeCarteBancaireForm;


import com.bna.smile.web.moyenPaiement.demandeChequier.forms.CreationDemandeChequeForm;
import com.bna.smile.web.procuration.util.MandatView;

import com.bna.smile.web.souscription.actions.SouscriptionContratCompteAction;

import com.oxia.fwk.context.Context;

import com.oxia.fwk.core.ValueObject;

import java.io.IOException;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import java.util.ArrayList;
import java.util.Date;

import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.actions.DispatchAction;

public class PecDemandeCarteBancaireAction extends DispatchAction {
    /**This is the main action called from the Struts framework.
     * @param mapping The ActionMapping used to select this instance.
     * @param form The optional ActionForm bean for this request.
     * @param request The HTTP Request we are processing.
     * @param response The HTTP Response we are processing.
     */
     private static final Logger logger = Logger.getLogger(PecDemandeCarteBancaireAction.class);
     public  ActionForward initierPage(ActionMapping mapping, ActionForm form, 
                               HttpServletRequest request, 
                               HttpServletResponse response) throws IOException, 
                                                                    ServletException {

         
     try{
         SessionUtil sessionUtil =new SessionUtil();
         //Suppression des anciens Bean de type Form de la session, SAUF "pecDemandeCarteBancaireForm"
         sessionUtil.removeSession(request,"pecDemandeCarteBancaireForm"); 
          
         PecDemandeCarteBancaireForm pecDemandeCarteBancaireForm = 
             (PecDemandeCarteBancaireForm)form;
         ParamAgence paramAgence = (ParamAgence)request.getSession().getAttribute("paramAgBNA"); /// structure de l'agent 
             
         //verification de l'habilitation sur cet operation
         StructureDomaine structureDomaine = new StructureDomaine(paramAgence.getCodStrcStrc(),Constants.COD_DOM_CONTRATCOMPTE);
         boolean bool = SmileUtil.testDomaineOuvert(structureDomaine);
             
         //effacer la paje
         pecDemandeCarteBancaireForm.clearForm();
         
         //recuperation des varible de session dans la page 
         pecDemandeCarteBancaireForm.setNumMatrUser(paramAgence.getNumMatrUser().toString());
         pecDemandeCarteBancaireForm.setCodStrcStrc(paramAgence.getCodStrcStrc().toString());
        
         //affectation du code operatoion de la page
          pecDemandeCarteBancaireForm.setCodeOperation(Constants.COD_OPER_OPER_PECDemandeCarte.toString());
         //afficher le titre de la page
         pecDemandeCarteBancaireForm.setLibelleOperation(Constants.TITRE_PECDemandeCarte);
   
         
         
         } catch (Exception e) {
                 ActionMessages actionMessages = new ActionMessages();
                 ActionMessage actionMessage = 
                     new ActionMessage("exception.generique", 
                                       e.getMessage() );
                 actionMessages.add("Erreur ", actionMessage);   
                 this.saveMessages(request, actionMessages);
                 logger.error("Exception : ",e);
                 return mapping.findForward("error");  
         }
         
   
   
         return mapping.findForward("success");
     }
     
    public ActionForward rechercheContrat(ActionMapping mapping, 
                                          ActionForm form, 
                                          HttpServletRequest request, 
                                          HttpServletResponse response) throws IOException, 
                                                                               ServletException {
        try {
            Context context = ContextHandler.getContext();
            PecDemandeCarteBancaireForm pecDemandeCarteBancaireForm = 
                (PecDemandeCarteBancaireForm)form;
            //------------------------------------------------
            //-------- sauvgarder qlq information (cle)
            String varCleCompte = 
                new String(pecDemandeCarteBancaireForm.getCleCompte());
            //-----------------------------------------------------------
            
             ContratCptId contratCptId = new ContratCptId();
             String varTypeDemandeur = new String();
             
             contratCptId.setCodStrcStrc(Long.valueOf(pecDemandeCarteBancaireForm.getCodStrcStrc()));
             contratCptId.setCodPrdPrd(Long.valueOf(pecDemandeCarteBancaireForm.getCodPrdPrd()));
             contratCptId.setNumCcptCcpt(Long.valueOf(pecDemandeCarteBancaireForm.getNumCcptCcpt()));

            //----------- Effacer l'ecran 
            pecDemandeCarteBancaireForm.clearForm();
            
            //recherche contrat
            GetDetailContratCmd getDetailContratCmd = new GetDetailContratCmd();
           
            //-------------------------------------------------------------------
            //------------- Recherche des donnée du Contrat et du Client 
            ContratCpt contratCpt = (ContratCpt)getDetailContratCmd.execute(contratCptId);
            //test si contrat existant
            if (contratCpt.getContratCptId() != null) {
                pecDemandeCarteBancaireForm.setTestExistanceContrat("ContratExistant");
                //test si contrat valide
                if (contratCpt.getCodEtatCcpt().equals(Constants.COD_ETAT_CPT_VALID)) {
                 // if (!verifierInterdictionChequier(contratCpt.getClient().getPersonne())) {
                  ////Alerte si le titulaire du compte est interdit de chequier
                  if (verifierInterdictionChequier(contratCpt.getClient().getPersonne())) {
                      pecDemandeCarteBancaireForm.setAlert("TitulaireInterditChequier");
                  } 
                    
                    if (contratCpt.getMontSoldCcpt() != null) {
                        pecDemandeCarteBancaireForm.setMontSoldCcpt(contratCpt.getMontSoldCcpt().toString());
                    }      
                    pecDemandeCarteBancaireForm.setDevise(contratCpt.getDevise().getLibDevDev());
                    pecDemandeCarteBancaireForm.setNomIntiCcpt(contratCpt.getNomIntiCcpt());
                    pecDemandeCarteBancaireForm.setCodTpceTpceClient(contratCpt.getClient().getPersonne().getTypePiece().getCodTpceTpce().toString());
                    pecDemandeCarteBancaireForm.setNumPcePersClient(contratCpt.getClient().getPersonne().getNumPcePers());                   
                    
                    // affectation du type et du categorie de la personne
                    pecDemandeCarteBancaireForm.setTypePersonne(contratCpt.getClient().getTypePers().getCodTperTper());
                    pecDemandeCarteBancaireForm.setCategoriePersonne(contratCpt.getClient().getPersonne().getCategoriePersonne().getCodCatpCatp());
                    
                    //-----------------------------------------------------------
                    //----------- Si la personne est une entite co-titulaire
                    if ((contratCpt.getClient().getTypePers().getCodTperTper().equals(Constants.ENTCOTITULAIRE))) {                        
                        PersonneStrc personneStrc = new PersonneStrc();
                        // chercher les personnes cotitulaires
                        GetListMembreCotitulaireCmd getListMembreCotitulaire = new GetListMembreCotitulaireCmd();               
                        personneStrc.setCodTpceTpce(contratCpt.getClient().getPersonne().getTypePiece().getCodTpceTpce());
                        personneStrc.setNumPcePers(contratCpt.getClient().getPersonne().getNumPcePers());               

                        Listes lisCotitulaire = (Listes)getListMembreCotitulaire.execute(personneStrc);
                        pecDemandeCarteBancaireForm.setListCotitulaires(lisCotitulaire.getList());
                        CoTitulaire cotitulaire = (CoTitulaire)lisCotitulaire.getList().get(0);
                        pecDemandeCarteBancaireForm.setNumSeqCliCotitulaire(cotitulaire.getClient().getNumSeqPers().toString());
                        pecDemandeCarteBancaireForm.setTypeCotitulaire(cotitulaire.getCodTcotCoti());
                        pecDemandeCarteBancaireForm.setTypeSignatureCotitulaire(cotitulaire.getCodSigCoti());

                        pecDemandeCarteBancaireForm.setNomNomPersClient(contratCpt.getClient().getPersonne().getNomNomPers());
                        //pecDemandeCarteBancaireForm.setNomPrnPersClient(pecDemandeCarteBancaireForm.getContratCpt().getClient().getPersonne().getNomPrnPers());

                        pecDemandeCarteBancaireForm.setMessageTexte(" Le contrat appartient à une entite co-titulaire.");                        
                        // alert d'aide si cotitulaire
                        pecDemandeCarteBancaireForm.setAlertDemandeur("alertCotitulaire");

                        //----------------------------------------------------------------
                        //--------- Cas d'une personne Morale
                    } else if (contratCpt.getClient().getTypePers().getCodTperTper().equals(Constants.PERSMORALE)) {
                        pecDemandeCarteBancaireForm.setNomNomPersClient(contratCpt.getClient().getPersonne().getNomRsPers());
                        pecDemandeCarteBancaireForm.setNomPrnPersClient(contratCpt.getClient().getPersonne().getLibSiglPers());                        
                    } else { // cas d'une personne physique
                        pecDemandeCarteBancaireForm.setNomNomPersClient(contratCpt.getClient().getPersonne().getNomNomPers());
                        pecDemandeCarteBancaireForm.setNomPrnPersClient(contratCpt.getClient().getPersonne().getNomPrnPers());                        
                    }

                    if (contratCpt.getAdresseCorresp() != null) {
                        pecDemandeCarteBancaireForm.setAdresseCorrespondanceClient(contratCpt.getAdresseCorresp().toString());
                    }
                    // sauvgarde du contrat en question dans la form bean
                    pecDemandeCarteBancaireForm.setContratCpt(contratCpt);

                    //Formation du MessageTexte et alert d'aide pour les Cas autres que cootitulaire
                    if(!contratCpt.getClient().getTypePers().getCodTperTper().equals(Constants.ENTCOTITULAIRE)){ 
                        MandatDAO mandatDAO = (MandatDAO)context.getBean("mandatDAO");
                        Long nombreMandatGen = mandatDAO.getNombreMandatGenerauxParContrat(contratCpt.getContratCptId().getCodStrcStrc(),
                        contratCpt.getContratCptId().getCodPrdPrd(),contratCpt.getContratCptId().getNumCcptCcpt());
                         
                        Long nombreMandatSpe = mandatDAO.getNombreMandatSpeciauxParContrat(contratCpt.getContratCptId().getCodStrcStrc(),
                        contratCpt.getContratCptId().getCodPrdPrd(),contratCpt.getContratCptId().getNumCcptCcpt(), Long.valueOf(pecDemandeCarteBancaireForm.getCodeOperation()));
                         
                        if (nombreMandatGen.intValue() > 0){
                         if (nombreMandatGen.intValue() == 1){
                            pecDemandeCarteBancaireForm.setMessageTexte(" un mandat général pour ce contrat"); 
                         }else {
                          pecDemandeCarteBancaireForm.setMessageTexte(nombreMandatGen + " mandats généraux pour ce contrat");
                          }                                           
                         }
                                           
                        if (nombreMandatSpe.intValue() > 0){
                         if (nombreMandatSpe.intValue() == 1){
                            pecDemandeCarteBancaireForm.setMessageTexte("un mandat spécial pour ce contrat");
                         }else {
                             pecDemandeCarteBancaireForm.setMessageTexte(nombreMandatSpe + " mandats spéciaux pour ce contrat"); 
                         }            
                        }
                        if (nombreMandatSpe.intValue() > 0 || nombreMandatGen.intValue() > 0 ){
                             pecDemandeCarteBancaireForm.setTestExistanceMandat("MandatsExistants");  
                        } else pecDemandeCarteBancaireForm.setTestExistanceMandat("MandatsInexistants"); 
                        
                        // gestion des alerts du demandeur 
                        if (pecDemandeCarteBancaireForm.getTestExistanceMandat().equals("MandatsInexistants")){
                            if(pecDemandeCarteBancaireForm.getTypePersonne().equals(Constants.PERSPHYSIQUE)){
                                if(pecDemandeCarteBancaireForm.getCategoriePersonne().equals(Constants.COD_CATEGORIE_MINEUR))
                                    pecDemandeCarteBancaireForm.setAlertDemandeur("mineurSansMandat");
                                else if(pecDemandeCarteBancaireForm.getCategoriePersonne().equals(Constants.COD_CATEGORIE_P_ETR_INC) || pecDemandeCarteBancaireForm.getCategoriePersonne().equals(Constants.COD_CATEGORIE_P_TUN_INC) )
                                       pecDemandeCarteBancaireForm.setAlertDemandeur("incapableSansMandat"); 
                            }else if(pecDemandeCarteBancaireForm.getTypePersonne().equals(Constants.PERSMORALE)){
                                  pecDemandeCarteBancaireForm.setAlertDemandeur("moraleSansMandat");                                     
                            }
                                
                        }else if(pecDemandeCarteBancaireForm.getTestExistanceMandat().equals("MandatsExistants")){
                                   if(pecDemandeCarteBancaireForm.getTypePersonne().equals(Constants.PERSPHYSIQUE)){
                                       if(pecDemandeCarteBancaireForm.getCategoriePersonne().equals(Constants.COD_CATEGORIE_MINEUR))
                                           pecDemandeCarteBancaireForm.setAlertDemandeur("alertMineur");
                                       else if(pecDemandeCarteBancaireForm.getCategoriePersonne().equals(Constants.COD_CATEGORIE_P_ETR_INC) || pecDemandeCarteBancaireForm.getCategoriePersonne().equals(Constants.COD_CATEGORIE_P_TUN_INC) )
                                              pecDemandeCarteBancaireForm.setAlertDemandeur("alertIncapable"); 
                                       else if(pecDemandeCarteBancaireForm.getCategoriePersonne().equals(Constants.COD_CATEGORIE_PHY_TUN_MAJ) )
                                              pecDemandeCarteBancaireForm.setAlertDemandeur("alertTitulaireMandat");      
                                   } else if(pecDemandeCarteBancaireForm.getTypePersonne().equals(Constants.PERSMORALE)){
                                             pecDemandeCarteBancaireForm.setAlertDemandeur("alertMorale");
                                    }                                
                        }    
                    }

              /*    } else{
                          pecDemandeCarteBancaireForm.setAlert("TitulaireInterditChequier"); // end if interdiction de chequier...
                  }*/
                } else {
                    pecDemandeCarteBancaireForm.setAlert("ContratNonvalide");
                    pecDemandeCarteBancaireForm.setEtatContrat(contratCpt.getCodEtatCcpt());
                }
                
            } else {
                //--------------------------------------------------------------------
                //------------- Contrat Inexistant
                pecDemandeCarteBancaireForm.setTestExistanceContrat("contratInexistant");

            }
            pecDemandeCarteBancaireForm.setCodPrdPrd(StrHandler.lpad(contratCptId.getCodPrdPrd().toString(),'0',4));
            pecDemandeCarteBancaireForm.setCodStrcStrc(StrHandler.lpad(contratCptId.getCodStrcStrc().toString(),'0',3));
            pecDemandeCarteBancaireForm.setNumCcptCcpt(StrHandler.lpad(contratCptId.getNumCcptCcpt().toString(),'0',6));
            pecDemandeCarteBancaireForm.setCleCompte(varCleCompte);

           
            //-------------- Fin desactivation champs demandeur  
            //-------------------------------------------------------------------- 
          
            return mapping.findForward("success");
            
        } catch (Exception e) {
            ActionMessages actionMessages = new ActionMessages();
            ActionMessage actionMessage = 
                new ActionMessage("exception.generique", 
                                  e.getMessage() );
            actionMessages.add("Erreur ", actionMessage);   
            this.saveMessages(request, actionMessages);
            logger.error("Exception : ",e);
            return mapping.findForward("error");

        }
    }


    public  ActionForward mandatOperation(ActionMapping mapping, ActionForm form, 
                                  HttpServletRequest request, 
                                  HttpServletResponse response) throws IOException, 
                                                                       ServletException {
    try{
        PecDemandeCarteBancaireForm pecDemandeCarteBancaireForm = 
            (PecDemandeCarteBancaireForm)form;
        Mandat mandat = new Mandat();

        /* rechercher le mandat */
        for (Iterator it = 
             pecDemandeCarteBancaireForm.getListMandatsConcernesPersonne().iterator(); 
             it.hasNext(); ) {
            MandatView mandatView = (MandatView)it.next();
            if (mandatView.getMandat().getNumMandMand().equals(new Long(pecDemandeCarteBancaireForm.getNumeroMandatChoisi()))) {
                mandat = mandatView.getMandat();
            }

        }
        if(mandat.getNumDemMand() != null)
            pecDemandeCarteBancaireForm.setRefMand(mandat.getNumDemMand().toString());
        if(mandat.getCodSignMand() != null)
            pecDemandeCarteBancaireForm.setSignatureMandatChoisi(mandat.getCodSignMand());
        if(mandat.getNbrMinMand() != null)
            pecDemandeCarteBancaireForm.setNbrMinMandatChoisi(mandat.getNbrMinMand().toString());
        pecDemandeCarteBancaireForm.setTypeMandatChoisi(mandat.getCodTypMand());
        GetDetailMandatCmd getDetailMandatCmd = new GetDetailMandatCmd();
        DetailMandat detailMandat = (DetailMandat)getDetailMandatCmd.execute(mandat);

        //---------------------------------------------------------------------//
        //----------- Recherche de l'Operation demande de carte        ------------------------/
        //---------------------------------------------------------------------//
        if(mandat.getCodTypMand().equals("S")){
            if (detailMandat.getListeMandatOperations() != null && detailMandat.getListeMandatOperations().size()>0) {            
                
                for (Iterator it = detailMandat.getListeMandatOperations().iterator(); it.hasNext(); ) {               
                    MandatOperation mandatOperation = (MandatOperation)it.next();
                    if(mandatOperation.getOperation().getCodOperOper().equals(new Long(pecDemandeCarteBancaireForm.getCodeOperation()))){
                        pecDemandeCarteBancaireForm.setNumOperation(mandatOperation.getMandatOperationId().getNumMaopMaop().toString());
                        pecDemandeCarteBancaireForm.setSignatureMandatChoisi(mandatOperation.getCodSignMaop());
                        if(mandat.getNbrMinMand()!= null)
                          pecDemandeCarteBancaireForm.setNbrMinMandatChoisi(mandatOperation.getNbrMinMaop().toString());   
                        else pecDemandeCarteBancaireForm.setNbrMinMandatChoisi("0");
                    }                    
                 }
            } // Fin For  
        }
        
        if(mandat.getCodTypMand().equals("G")){
            // mandat général 
             pecDemandeCarteBancaireForm.setMessageTexte(" dossier Mandat choisi : " +  mandat.getNumDemMand()+", type : " + mandat.getCodTypMand() +" , Signature : " + mandat.getCodSignMand());  
        }else{
            // mandat spécial ou juridique
             pecDemandeCarteBancaireForm.setMessageTexte(" dossier Mandat choisi : " +  mandat.getNumDemMand()+", type : " + mandat.getCodTypMand() +" , Signature : " + pecDemandeCarteBancaireForm.getSignatureMandatChoisi() + " (selon opération appropriée)");  
        }
        //---------------------------------------------------------------------//
        //----------- Recherche des personnes         ------------------------//
        //---------------------------------------------------------------------//
        if (detailMandat.getListeMandatPersonnes() != null && detailMandat.getListeMandatPersonnes().size()>0) {
            pecDemandeCarteBancaireForm.setOpenTabSheetOperation("true");
            pecDemandeCarteBancaireForm.setOpenTabSheetCotitulaire("false");
            pecDemandeCarteBancaireForm.setListdesMandatsPersonneChoisi(new ArrayList());
            for (Iterator it = detailMandat.getListeMandatPersonnes().iterator();it.hasNext(); ) {
              MandatPersonne mandatPers = (MandatPersonne)it.next();
               if((pecDemandeCarteBancaireForm.getDemandeur().getTypePiece().getCodTpceTpce().equals(mandatPers.getPersonne().getTypePiece().getCodTpceTpce()))
                  && (pecDemandeCarteBancaireForm.getDemandeur().getNumPcePers().equals(mandatPers.getPersonne().getNumPcePers()))){
                 pecDemandeCarteBancaireForm.getListdesMandatsPersonneChoisi().add(pecDemandeCarteBancaireForm.getDemandeur().getNumSeqPers());
              }else {pecDemandeCarteBancaireForm.getListdesMandatsPersonneChoisi().add("");}
            }
            pecDemandeCarteBancaireForm.setListMandatsPersonneConcernesDemandeur(detailMandat.getListeMandatPersonnes());
            pecDemandeCarteBancaireForm.setNbreSignataires(new Integer(detailMandat.getListeMandatPersonnes().size()).toString());
        }

        return mapping.findForward("success");
        } catch (Exception e) {
                ActionMessages actionMessages = new ActionMessages();
                ActionMessage actionMessage = 
                    new ActionMessage("exception.generique", 
                                      e.getMessage() );
                actionMessages.add("Erreur ", actionMessage);   
                this.saveMessages(request, actionMessages);
                logger.error("Exception : ",e);
                return mapping.findForward("error");
    
        }
    }



    public ActionForward verifierDemandeur(ActionMapping mapping, ActionForm form, 
                                        HttpServletRequest request, 
                                        HttpServletResponse response) throws IOException, 
                                                                             ServletException {
   
        PecDemandeCarteBancaireForm pecDemandeCarteBancaireForm = 
            (PecDemandeCarteBancaireForm)form;
    try{        
        pecDemandeCarteBancaireForm.setAlert("");
        pecDemandeCarteBancaireForm.setTestExistanceDemandeur("");
        pecDemandeCarteBancaireForm.setAlertDemandeur("");
        pecDemandeCarteBancaireForm.setTypeDemandeur("");
        pecDemandeCarteBancaireForm.setNomNomPersDemandeur("");
        pecDemandeCarteBancaireForm.setNomPrnPersDemandeur("");
        pecDemandeCarteBancaireForm.setCodTcarTcar("");
        pecDemandeCarteBancaireForm.setListeTypeCarte(null);
        pecDemandeCarteBancaireForm.setSalarie("");
        pecDemandeCarteBancaireForm.setDomicilie("");
        pecDemandeCarteBancaireForm.setMontSalDcar("");
        pecDemandeCarteBancaireForm.setMontDretDcar("");
        pecDemandeCarteBancaireForm.setMontDachDcar("");
        pecDemandeCarteBancaireForm.setCodOperTcar("");
        pecDemandeCarteBancaireForm.setBoolPlafTcar("");
        

        // Vider les champs concernant le mandats 
        pecDemandeCarteBancaireForm.setListdesMandatsPersonneChoisi(null);
        pecDemandeCarteBancaireForm.setListMandats(null);
        pecDemandeCarteBancaireForm.setListMandatsConcernesPersonne(null);        
        pecDemandeCarteBancaireForm.setListMandatsPersonneConcernesDemandeur(null);
        // Vider les champs concernant le cotitulaire
        pecDemandeCarteBancaireForm.setOpenTabSheetCotitulaire("false");
        pecDemandeCarteBancaireForm.setOpenTabSheetMandat("false");                          
        pecDemandeCarteBancaireForm.setOpenTabSheetOperation("false");
        pecDemandeCarteBancaireForm.setCotitulaire(null);
        
        Long typePieceTitulaireContrat = pecDemandeCarteBancaireForm.getContratCpt().getClient().getPersonne().getTypePiece().getCodTpceTpce();
        String numeroPieceTitulaireContrat = pecDemandeCarteBancaireForm.getContratCpt().getClient().getPersonne().getNumPcePers();       
        
       
        ContratPersonne contratPersonne = new ContratPersonne();
        contratPersonne.setContratCptId(pecDemandeCarteBancaireForm.getContratCpt().getContratCptId());
        
        
        //------- recherche de la personne
        PersonneStrc personneStrc = new PersonneStrc();
        personneStrc.setCodTpceTpce(Long.valueOf(pecDemandeCarteBancaireForm.getCodTpceTpceDemandeur()));
        personneStrc.setNumPcePers(pecDemandeCarteBancaireForm.getNumPcePersDemandeur());
        GetPersonneCptCmd getPersonneCptCmd = new GetPersonneCptCmd();
        PersonneCpt personneCpt = (PersonneCpt)getPersonneCptCmd.execute(personneStrc);
        pecDemandeCarteBancaireForm.setDemandeur(personneCpt.getPersonne());
        //------- Fin de la recherche de la personne
     if (personneCpt.getPersonne()!= null) {
          pecDemandeCarteBancaireForm.setIdDemandeur(personneCpt.getPersonne().getNumSeqPers().toString());
        //---------- cas de titulaire
        if (personneCpt.getPersonne().getTypePiece().getCodTpceTpce().equals(typePieceTitulaireContrat) && 
            personneCpt.getPersonne().getNumPcePers().equals(numeroPieceTitulaireContrat)) {
            if(pecDemandeCarteBancaireForm.getCategoriePersonne().equals(Constants.COD_CATEGORIE_P_ETR_INC) || pecDemandeCarteBancaireForm.getCategoriePersonne().equals(Constants.COD_CATEGORIE_P_TUN_INC) ){
                // traitement du cas personne majeure incapable
                pecDemandeCarteBancaireForm.setAlertDemandeur("demandeurIncapable");  
                pecDemandeCarteBancaireForm.setNomNomPersDemandeur(null);
                pecDemandeCarteBancaireForm.setNomPrnPersDemandeur(null);
            }else{
                pecDemandeCarteBancaireForm.setTypeDemandeur("T");           
                pecDemandeCarteBancaireForm.setNomNomPersDemandeur(personneCpt.getPersonne().getNomNomPers());
                pecDemandeCarteBancaireForm.setNomPrnPersDemandeur(personneCpt.getPersonne().getNomPrnPers());
            }
        //--------- traitement du cas cotitulaire
        }else if(pecDemandeCarteBancaireForm.getContratCpt().getClient().getTypePers().getCodTperTper().equals(Constants.ENTCOTITULAIRE)){    
               boolean membreTrouve = false;
               if (pecDemandeCarteBancaireForm.getListCotitulaires().size() > 0) {
                   for (Iterator it = pecDemandeCarteBancaireForm.getListCotitulaires().iterator();it.hasNext(); ) {
                       CoTitulaire coTitulaire = (CoTitulaire)it.next();               
                       if(personneCpt.getPersonne().getNumSeqPers().equals(coTitulaire.getCoTitulaireId().getNumSeqPers())){
                           // membre cotitulaire existant dans l'entité
                            pecDemandeCarteBancaireForm.setTypeDemandeur("C"); 
                            pecDemandeCarteBancaireForm.setNomNomPersDemandeur(personneCpt.getPersonne().getNomNomPers());
                            pecDemandeCarteBancaireForm.setNomPrnPersDemandeur(personneCpt.getPersonne().getNomPrnPers());
                            pecDemandeCarteBancaireForm.setOpenTabSheetCotitulaire("true");
                            pecDemandeCarteBancaireForm.setOpenTabSheetMandat("false");                          
                            pecDemandeCarteBancaireForm.setOpenTabSheetOperation("false");
                            pecDemandeCarteBancaireForm.setCotitulaire(coTitulaire);
                            membreTrouve = true;                           
                       }
                   }
                   if (!membreTrouve){
                       pecDemandeCarteBancaireForm.setOpenTabSheetCotitulaire("false");
                       pecDemandeCarteBancaireForm.setAlertDemandeur("membreInexistant");
                   } 
               }
          //---------- cas d'un mandataire
          }else if (pecDemandeCarteBancaireForm.getTestExistanceMandat().equals("MandatsExistants")) {
               //-------------------------------------------------------

                   GetListMandatOperationPersonneContratOperationCmd getListM = 
                       new GetListMandatOperationPersonneContratOperationCmd();
                   //--------------  Les Objets du parametre ---------------------------------

                   ParamMandatOperationVo paramMandatOperationVo = 
                       new ParamMandatOperationVo();
                   ContratCpt contratCptRecherche = new ContratCpt();
                   Operation operation = new Operation();

                   contratCptRecherche.setContratCptId(pecDemandeCarteBancaireForm.getContratCpt().getContratCptId());
                   operation.setCodOperOper(new Long(pecDemandeCarteBancaireForm.getCodeOperation()));

                   personneStrc.setCodTpceTpce(personneCpt.getPersonne().getTypePiece().getCodTpceTpce()); // new Long(pecDemandeCarteBancaireForm.getCodTpceTpceDemandeur()));
                   personneStrc.setNumPcePers(personneCpt.getPersonne().getNumPcePers());

                   paramMandatOperationVo.setContraCptId(contratCptRecherche.getContratCptId());
                   paramMandatOperationVo.setPersonneStrc(personneStrc);
                   paramMandatOperationVo.setOperation(operation);


                   ListMandatOperationVo listMandatOperation = 
                       (ListMandatOperationVo)getListM.execute(paramMandatOperationVo);

                   //---------------------------------------------- 
                   //------- Remplir la liste des mandats View par les mandat generaux
                   List listeDesMandatsView = new ArrayList();
                   if (listMandatOperation.getListMandatsGeneraux().size() > 0) {
                       for (Iterator it = 
                            listMandatOperation.getListMandatsGeneraux().iterator(); 
                            it.hasNext(); ) {
                           Mandat mandat = (Mandat)it.next();
                           MandatView mandatView = new MandatView();
                           mandatView.setDateDebut(DateHandler.dateToStr(mandat.getDatDebMand()));
                           mandatView.setDateFin(DateHandler.dateToStr(mandat.getDatFinMand()));
                           if (mandat.getCodTypMand().equals("S"))
                               mandatView.setType("Spécial");
                           else if (mandat.getCodTypMand().equals("G")) {
                               mandatView.setType("Général");
                           }
                           mandatView.setMandat(mandat);
                           listeDesMandatsView.add(mandatView);
                       } //--- Fin remplir les mandats view
                   } // Fin if   mandats generaux    

                   //---------------------------------------------- 
                   //------- Remplir la liste des mandats View par les mandat spéciaux
                   if (listMandatOperation.getListMandatsSpeciauxOperations().size() > 0) {
                       for (Iterator it = 
                            listMandatOperation.getListMandatsSpeciauxOperations().iterator(); 
                            it.hasNext(); ) {
                           Mandat mandat = (Mandat)it.next();
                           MandatView mandatView = new MandatView();
                           mandatView.setDateDebut(DateHandler.dateToStr(mandat.getDatDebMand()));
                           mandatView.setDateFin(DateHandler.dateToStr(mandat.getDatFinMand()));
                           if (mandat.getCodTypMand().equals("S")) {
                               mandatView.setType("Spécial");
                           } else if (mandat.getCodTypMand().equals("G")) {
                               mandatView.setType("Général");
                           } else if (mandat.getCodTypMand().equals("J")) {
                               mandatView.setType("M. de Justice");
                           }
                           mandatView.setMandat(mandat);
                           listeDesMandatsView.add(mandatView);
                       } //--- Fin remplir les mandats view
                   } // Fin if  

                   pecDemandeCarteBancaireForm.setListMandatsConcernesPersonne(listeDesMandatsView);
                   if (pecDemandeCarteBancaireForm.getListMandatsConcernesPersonne().size() == 0) {
                       pecDemandeCarteBancaireForm.setAlertDemandeur("PasDeMandat");
                       pecDemandeCarteBancaireForm.setOpenTabSheetMandat("false");     
                   }else{
                       pecDemandeCarteBancaireForm.setNomNomPersDemandeur(personneCpt.getPersonne().getNomNomPers());
                       pecDemandeCarteBancaireForm.setNomPrnPersDemandeur(personneCpt.getPersonne().getNomPrnPers());
                       pecDemandeCarteBancaireForm.setTypeDemandeur("M");
                       pecDemandeCarteBancaireForm.setOpenTabSheetMandat("true");
                       pecDemandeCarteBancaireForm.setOpenTabSheetCotitulaire("false");
                       pecDemandeCarteBancaireForm.setOpenTabSheetOperation("false");
                         
                   }
               //Si aucun Pouvoir
               }else{              
                    pecDemandeCarteBancaireForm.setAlertDemandeur("PasDeMandat"); 
                    pecDemandeCarteBancaireForm.setOpenTabSheetCotitulaire("false");
                    pecDemandeCarteBancaireForm.setOpenTabSheetMandat("false");                          
                    pecDemandeCarteBancaireForm.setOpenTabSheetOperation("false");
               }
        } else { //-- Demandeur inexistant dans la base
            pecDemandeCarteBancaireForm.setTestExistanceDemandeur("N");
            
        }
        //si OK le demandeur possede le pouvoir alors chager la liste des types des cartes selon type demandeur: PM,PP...
        if(!pecDemandeCarteBancaireForm.getTypeDemandeur().equals("") ){ 
               //si possede pouvoir verifier si interdit de chequier
                ////Alerte si le demandeur est interdit de chequier
               if(!pecDemandeCarteBancaireForm.getTypeDemandeur().equals("T")){
                    if (verifierInterdictionChequier(pecDemandeCarteBancaireForm.getDemandeur())) {
                        pecDemandeCarteBancaireForm.setAlertDemandeur("TitulaireInterditChequier");
                    }
               }
                GetCartesEligibleContratCmd getCartesEligibleContratCmd = new GetCartesEligibleContratCmd();
                Listes listes = (Listes) getCartesEligibleContratCmd.execute(pecDemandeCarteBancaireForm.getContratCpt());
                List l = listes.getList();
                l.add(0,new TypeCarte());
                pecDemandeCarteBancaireForm.setListeTypeCarte(l);
                pecDemandeCarteBancaireForm.setCodTcarTcar("");
            
        }
    } catch (Exception e) {
        ActionMessages actionMessages = new ActionMessages();
        ActionMessage actionMessage = 
            new ActionMessage("exception.generique", 
                              e.getMessage() );
        actionMessages.add("Erreur ", actionMessage);   
        this.saveMessages(request, actionMessages);
        logger.error("Exception : ",e);
        return mapping.findForward("error");

    }
        
        return mapping.findForward("success");
        

    }
    
    public ActionForward validerDemande(ActionMapping mapping, ActionForm form, 
                                     HttpServletRequest request, 
                                     HttpServletResponse response) throws IOException, 
                                                                         ServletException {
      
         ActionMessages actionMessages = new ActionMessages();    
         PecDemandeCarteBancaireForm pecDemandeCarteBancaireForm = 
             (PecDemandeCarteBancaireForm)form;
         try{
         //verifier si demande en cours 
         
          PersonneTypeCarteCpt personneTypeCarteCpt = new PersonneTypeCarteCpt();
          
          PersonneStrc personneStrc = new PersonneStrc();
          personneStrc.setCodTpceTpce(Long.valueOf(pecDemandeCarteBancaireForm.getCodTpceTpceDemandeur()));
          personneStrc.setNumPcePers(pecDemandeCarteBancaireForm.getNumPcePersDemandeur());
          personneTypeCarteCpt.setPersonneStrc(personneStrc);
          
          TypeCarteCpt typeCarteCpt = new TypeCarteCpt();
          typeCarteCpt.setTypeCarte(Long.valueOf(pecDemandeCarteBancaireForm.getCodTcarTcar()));
          ContratCpt contratCpt = pecDemandeCarteBancaireForm.getContratCpt();
          typeCarteCpt.setContratCpt(contratCpt); 
          personneTypeCarteCpt.setTypeCarteCpt(typeCarteCpt);
             
          VerifDemandeCarteEnCoursCmd verifDemandeCarteEnCoursCmd = new VerifDemandeCarteEnCoursCmd();
          DemandeCarte demandeCarte = (DemandeCarte) verifDemandeCarteEnCoursCmd.execute(personneTypeCarteCpt);
          
          if(demandeCarte.getNumDemDcar()!=null){
         /********rejet demande en cours********/
              pecDemandeCarteBancaireForm.setAlertDemandeur("demandeEnCours");
              pecDemandeCarteBancaireForm.setMessageAlerte("Une demande de carte de même type N° "+demandeCarte.getNumDemDcar()+" créée le "+DateHandler.dateToStr(demandeCarte.getDatDemDcar())+" est déja en cours de confection pour ce client, Cette demande est rejetée !");   
              pecDemandeCarteBancaireForm.setCodMotifRejet(Constants.COD_MOTF_REJET_DemandeEnCours.toString());
              pecDemandeCarteBancaireForm.setCodeOperation(Constants.COD_OPER_OPER_PECDemandeCarte.toString());
              if (setDemandeCarte(form, contratCpt,request))
                    return mapping.findForward("success");
              else
                    return mapping.findForward("error");
          }else{ 
          //verifier possede carte de même type 
                VerifPossedeTypeCarteCmd verifPossedeTypeCarteCmd = new VerifPossedeTypeCarteCmd();
                CarteBancaire carteBancaire = (CarteBancaire) verifPossedeTypeCarteCmd.execute(personneTypeCarteCpt);
                if(carteBancaire.getCarteBancaireId()!=null){
                /********rejet demande carte delivrée********/
                    pecDemandeCarteBancaireForm.setAlertDemandeur("possedeCarte");
                    pecDemandeCarteBancaireForm.setMessageAlerte("Une carte de même type est déja reçu par le client le "+DateHandler.dateToStr(demandeCarte.getDatRemiDcar()));   
                    pecDemandeCarteBancaireForm.setCodMotifRejet(Constants.COD_MOTF_REJET_CarteDelivree.toString());
                    pecDemandeCarteBancaireForm.setCodeOperation(Constants.COD_OPER_OPER_PECDemandeCarte.toString());
                    if (setDemandeCarte(form, contratCpt,request))
                          return mapping.findForward("success");
                    else
                          return mapping.findForward("error");
                }else{
                // si cas mandataire et type signature conjointe
                    List listMandataireCoches = new ArrayList();
                    // extraire la liste des mandataire cochés
                    if(pecDemandeCarteBancaireForm.getTypeDemandeur().equals("M") && pecDemandeCarteBancaireForm.getSignatureMandatChoisi().equals("C")){
                         for (Iterator it = pecDemandeCarteBancaireForm.getListdesMandatsPersonneChoisi().iterator();it.hasNext(); ) {              
                           String numSeq = (String)it.next();
                           if(!numSeq.equals("")){
                             listMandataireCoches.add(numSeq);
                             pecDemandeCarteBancaireForm.setListMandataireCoches(listMandataireCoches);                 
                            }
                         }
                    }
                    /********Prise en charge demande carte********/
                    if (setDemandeCarte(form, contratCpt,request)){
                        StringBuffer message1 = new StringBuffer(
                               "La demande de carte de type " +pecDemandeCarteBancaireForm.getLibTcarTcar()+
                               " au nom de "+pecDemandeCarteBancaireForm.getNomPrnPersDemandeur()+" "+pecDemandeCarteBancaireForm.getNomNomPersDemandeur()+
                               " sur le compte " +
                               StrHandler.lpad(pecDemandeCarteBancaireForm.getCodStrcStrc().toString(), '0', 3) + " "+
                               StrHandler.lpad(pecDemandeCarteBancaireForm.getCodPrdPrd().toString(), '0', 4) + " "+
                               StrHandler.lpad(pecDemandeCarteBancaireForm.getNumCcptCcpt().toString(), '0', 6)+
                               " a été crée avec succès et en attente de validation.");
                        ActionMessage actionMessage = new ActionMessage("exception.generique",message1.toString());
                        actionMessages.add("Msg_validation ", actionMessage);
                        this.saveMessages(request, actionMessages);
                        return mapping.findForward("confirmationGenerale");
                        //return mapping.findForward("indexSMILE");
                    }else{
                        return mapping.findForward("error");
                    }
                }  
          }
          
         } catch (Exception e) {
            
             ActionMessage actionMessage = 
                 new ActionMessage("exception.generique", 
                                   e.getMessage() );
             actionMessages.add("Erreur ", actionMessage);   
             this.saveMessages(request, actionMessages);
             logger.error("Exception : ",e);
             return mapping.findForward("error");
         }   
         
         
        
     }
    
    public boolean setDemandeCarte(ActionForm form, ContratCpt contratCpt, HttpServletRequest request){
        
        DateFormat myformat = new SimpleDateFormat("dd/MM/yyyy");
        PecDemandeCarteBancaireForm pecDemandeCarteBancaireForm = (PecDemandeCarteBancaireForm)form;
        DemandeCarte demandeCarte = new DemandeCarte();
        TypeCarte typeCarte = new TypeCarte();
        typeCarte.setCodTcarTcar(Long.valueOf(pecDemandeCarteBancaireForm.getCodTcarTcar()));
        Personnel personnel = new Personnel();
        personnel.setNumMatrUser(pecDemandeCarteBancaireForm.getNumMatrUser());
        
        
        Tache tache = new Tache();
        TacheId tacheId = new TacheId();
        tacheId.setCodOperOper(Constants.COD_OPER_OPER_PECDemandeCarte);
        tacheId.setCodTachTach(Constants.COD_TACH_TACH_PECDemandeCarte);
        tache.setTacheId(tacheId);
        
        //affectation de la demande
        demandeCarte.setContratCpt(contratCpt);
        demandeCarte.setTache(tache);
        demandeCarte.setTypeCarte(typeCarte);
        demandeCarte.setPersonnel(personnel);
        demandeCarte.setDatDemDcar(DateHandler.strToDate(DateHandler.dateJour()));
        
        //affectation du cod_aut_dcar 
        if(!pecDemandeCarteBancaireForm.getCodAutDcar().equals("")){
            demandeCarte.setCodAutDcar(pecDemandeCarteBancaireForm.getCodAutDcar());
        }
    
        //affectation de type du client salarie oui/non
        if(pecDemandeCarteBancaireForm.getSalarie().equals("O")){
            demandeCarte.setBoolSalDcar(Long.valueOf("1"));
        }else{
            demandeCarte.setBoolSalDcar(Long.valueOf("0"));
        }
        
        //affectation de type du client domicilié oui/non
        if(pecDemandeCarteBancaireForm.getDomicilie().equals("O")){
            demandeCarte.setBoolDomsDcar(Long.valueOf("1"));
        }else{
            demandeCarte.setBoolDomsDcar(Long.valueOf("0"));
        }
        
        //affectation des montants demandés
        if(!pecDemandeCarteBancaireForm.getMontSalDcar().equals("")){
            demandeCarte.setMontSalDcar(Long.valueOf(pecDemandeCarteBancaireForm.getMontSalDcar()));
        }
        if(!pecDemandeCarteBancaireForm.getMontDretDcar().equals("")){
            demandeCarte.setMontDretDcar(Long.valueOf(pecDemandeCarteBancaireForm.getMontDretDcar()));
        }
        if(!pecDemandeCarteBancaireForm.getMontDachDcar().equals("")){
            demandeCarte.setMontDachDcar(Long.valueOf(pecDemandeCarteBancaireForm.getMontDachDcar()));
        }
        
        demandeCarte.setCodEtatDcar(Constants.COD_ETAT_DCAR_Attente);
        demandeCarte.setCodDemDcar(pecDemandeCarteBancaireForm.getTypeDemandeur());
        demandeCarte.setCodTpceDcar(pecDemandeCarteBancaireForm.getDemandeur().getTypePiece().getCodTpceTpce());
        demandeCarte.setNumPceDcar(pecDemandeCarteBancaireForm.getDemandeur().getNumPcePers());
        
        // si cas de rejet
        if(!pecDemandeCarteBancaireForm.getCodMotifRejet().equals(new String("")) ){
            MotifRejet motifRejet = new MotifRejet();
            motifRejet.setCodMotfMrej(new Long(pecDemandeCarteBancaireForm.getCodMotifRejet()));
            demandeCarte.setMotifRejet(motifRejet);
            demandeCarte.setCodEtatDcar(Constants.COD_ETAT_DCAR_RejetDemande);
        }
       
       // si cas cotitulaire
        if(pecDemandeCarteBancaireForm.getTypeDemandeur().equals("C")){
            // cas cotitulaire
             if(pecDemandeCarteBancaireForm.getCotitulaire()!=null){
               demandeCarte.setCoTitulaire(pecDemandeCarteBancaireForm.getCotitulaire());
             }         
        }
        
        
       // si cas mandataire former la liste des DemandeCarteMandatPersonnes
        DemandeCarteSignataire demandeCarteSignataire = new DemandeCarteSignataire();
        List listSignataires = new ArrayList(); 
        if(pecDemandeCarteBancaireForm.getTypeDemandeur().equals("M")){
           // cas du mandataires il faut inserer dans la table DemandeChequeMandatPersonne
          // if(demandeCarte.getNumDemDcar()!=null){
               if(pecDemandeCarteBancaireForm.getSignatureMandatChoisi().equals("S")){
                   // signature séparée
                   /// insertion juste du demandeur de cheque
                    DemandeCarteMandatPersonne demandeCarteMandatPersonneRetour = affecterDonneesDemandeCarteMandatpersonne(pecDemandeCarteBancaireForm,pecDemandeCarteBancaireForm.getDemandeur().getNumSeqPers(),demandeCarte);  
                    listSignataires.add(demandeCarteMandatPersonneRetour);
               }else{
               // signature conjointe(insertion de tous les signataires)           
                   for (Iterator it = pecDemandeCarteBancaireForm.getListMandataireCoches().iterator();it.hasNext(); ) {          
                       String numSeq = (String)it.next();                                     
                       DemandeCarteMandatPersonne demandeCarteMandatPersonne = affecterDonneesDemandeCarteMandatpersonne(pecDemandeCarteBancaireForm,new Long(numSeq),demandeCarte);  
                       listSignataires.add(demandeCarteMandatPersonne);
                   }               
               }
          // }
        }
           demandeCarteSignataire.setDemandeCarte(demandeCarte);
           demandeCarteSignataire.setSignataire(listSignataires);  
           
         PecDemandeCarteCmd pecDemandeCarteCmd = new PecDemandeCarteCmd();
         ValueObject demandeCarteNew = pecDemandeCarteCmd.execute(demandeCarteSignataire);
         ActionMessages actionMessages = new ActionMessages();
         if (demandeCarteNew == null || demandeCarteNew.hasError()) {
                List listErreur = demandeCarteNew.getErrors();
                for (Iterator it = listErreur.iterator(); it.hasNext(); ) {
                    com.oxia.fwk.core.Error erreur = 
                        (com.oxia.fwk.core.Error)it.next();
                    ActionMessage actionMessage = 
                        new ActionMessage("exception.generique", 
                                          erreur.getDescription());
                    actionMessages.add("Erreur ", actionMessage);
                }
                this.saveMessages(request, actionMessages);
                return false;
               // return mapping.findForward("error");
         }
         return true;
    }
    
    public DemandeCarteMandatPersonne affecterDonneesDemandeCarteMandatpersonne(ActionForm form, Long numSeqPers,DemandeCarte demandeCarte){
        
        PecDemandeCarteBancaireForm pecDemandeCarteBancaireForm = (PecDemandeCarteBancaireForm)form;
        DemandeCarteMandatPersonne demandeCarteMandatPersonne = new DemandeCarteMandatPersonne();
        DemandeCarteMandatPersonneId demandeCarteMandatPersonneId = new DemandeCarteMandatPersonneId();
        demandeCarteMandatPersonneId.setNumMandMand(new Long(pecDemandeCarteBancaireForm.getNumeroMandatChoisi()));
        //demandeCarteMandatPersonneId.setNumDemDcar(demandeCarte.getNumDemDcar());       
        demandeCarteMandatPersonne.setDemandeCarte(demandeCarte);
        demandeCarteMandatPersonneId.setNumSeqPers(numSeqPers);
        demandeCarteMandatPersonne.setDemandeCarteMandatPersonneId(demandeCarteMandatPersonneId);
        return demandeCarteMandatPersonne;
    }
    public boolean verifierInterdictionChequier(Personne demandeur) {
        VerifierInterditChequierCmd  verifierInterditChequierCmd = new VerifierInterditChequierCmd();
        PersonneStrc personneStrc = new PersonneStrc();
        personneStrc.setCodTpceTpce(demandeur.getTypePiece().getCodTpceTpce());
        personneStrc.setNumPcePers(demandeur.getNumPcePers());
        
        PrimitiveVO pr =  (PrimitiveVO)verifierInterditChequierCmd.execute(personneStrc);
        return pr.isVBool();
    }

}
