package com.bna.smile.web.moyenPaiement.demandeChequier.actions;

import com.bna.commun.model.Chequier;
import com.bna.commun.model.ChequierId;
import com.bna.commun.model.CoTitulaire;
import com.bna.commun.model.CompteInterne;
import com.bna.commun.model.CompteInterneId;
import com.bna.commun.model.ContratCpt;
import com.bna.commun.model.ContratCptId;
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
import com.bna.commun.model.TypeConfection;
import com.bna.commun.util.DateHandler;
import com.bna.commun.util.StrHandler;
import com.bna.commun.util.ContextHandler;
import com.bna.commun.util.SmileUtil;
import com.bna.commun.vo.PrimitiveVO;
import com.bna.smile.model.constant.Constants;
import com.bna.smile.model.domainecommun.commande.GetContratMandatCmd;
import com.bna.smile.model.domainecommun.commande.GetDetailContratCmd;
import com.bna.smile.model.domainecommun.commande.GetDetailCptInterneCmd;
import com.bna.smile.model.domainecommun.commande.GetListMembreCotitulaireCmd;
import com.bna.smile.model.domainecommun.commande.GetOperationCmd;
import com.bna.smile.model.domainecommun.commande.GetPersonneByNumSeqPersCmd;
import com.bna.smile.model.domainecommun.commande.GetPersonneCmd;
import com.bna.smile.model.domainecommun.commande.GetPersonneCptCmd;
import com.bna.smile.model.domainecommun.commande.GetPersonnelCmd;
import com.bna.smile.model.domainecommun.model.ContratCptMandat;
import com.bna.smile.model.domainecommun.model.ContratPersonne;
import com.bna.smile.model.domainecommun.model.Listes;
import com.bna.smile.model.domainecommun.model.PersonneCpt;
import com.bna.smile.model.domainecommun.model.PersonneStrc;
import com.bna.smile.model.domainecontratcompte.moyensPaiement.commande.GetDetailDemandeChequeCmd;
import com.bna.smile.model.domainecontratcompte.moyensPaiement.commande.GetListDemandeChequePersonneCmd;
import com.bna.smile.model.domainecontratcompte.moyensPaiement.commande.GetListDemandesChequesCmd;
import com.bna.smile.model.domainecontratcompte.moyensPaiement.commande.GetListDemandesChequesMandatPersonneCmd;
import com.bna.smile.model.domainecontratcompte.moyensPaiement.commande.GetListDemandeursChequesMandatPersonneCmd;
import com.bna.smile.model.domainecontratcompte.moyensPaiement.commande.InsertDemandeChequeCmd;
import com.bna.smile.model.domainecontratcompte.moyensPaiement.commande.InsertDemandeChequeMandatPersonneCmd;
import com.bna.smile.model.domainecontratcompte.moyensPaiement.commande.ReceptionChequiersCmd;
import com.bna.smile.model.domainecontratcompte.moyensPaiement.dao.DemandeChequeDAO;
import com.bna.smile.model.domainecontratcompte.moyensPaiement.model.DetailDemandeCheque;
import com.bna.smile.model.domainecontratcompte.moyensPaiement.model.ListesDemandesCheques;
import com.bna.smile.model.domainecontratcompte.moyensPaiement.model.ParamDemandeCheque;
import com.bna.smile.model.domainecontratcompte.procuration.commande.GetDetailMandatCmd;
import com.bna.smile.model.domainecontratcompte.procuration.commande.GetListMandatOperationPersonneContratOperationCmd;
import com.bna.smile.model.domainecontratcompte.procuration.commande.GetMandatAvaliderCmd;
import com.bna.smile.model.domainecontratcompte.procuration.dao.MandatDAO;
import com.bna.smile.model.domainecontratcompte.procuration.model.DetailMandat;
import com.bna.smile.model.domainecontratcompte.procuration.model.ListMandatOperationVo;
import com.bna.smile.model.domainecontratcompte.procuration.model.MandatRecherche;
import com.bna.smile.model.domainecontratcompte.procuration.model.ParamMandatOperationVo;
import com.bna.smile.model.domaineguichet.commande.VerifierInterditChequierCmd;
import com.bna.smile.web.commun.model.ParamAgence;

import com.bna.smile.web.commun.util.SessionUtil;
import com.bna.smile.web.moyenPaiement.demandeChequier.forms.CreationDemandeChequeForm;
import com.bna.smile.web.procuration.util.MandatOperationView;
import com.bna.smile.web.procuration.util.MandatView;

import com.bna.smile.web.souscription.actions.SouscriptionContratCompteAction;

import com.oxia.fwk.context.Context;

import java.io.IOException;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import javax.servlet.http.HttpSession;

import org.apache.commons.collections.map.ListOrderedMap;
import org.apache.log4j.Logger;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.actions.DispatchAction;

public class CreationDemandeChequeAction extends DispatchAction {
    /**This is the main action called from the Struts framework.
     * @param mapping The ActionMapping used to select this instance.
     * @param form The optional ActionForm bean for this request.
     * @param request The HTTP Request we are processing.
     * @param response The HTTP Response we are processing.
     */
     
     private static final Logger logger = Logger.getLogger(CreationDemandeChequeAction.class);
    public

    ActionForward initierPage(ActionMapping mapping, ActionForm form, 
                              HttpServletRequest request, 
                              HttpServletResponse response) throws IOException, 
                                                                   ServletException {

        
       
       
        
        SessionUtil sessionUtil =new SessionUtil();
        //Suppression des anciens Bean de type Form de la session, SAUF "creationDemandeChequeForm"
        sessionUtil.removeSession(request,"creationDemandeChequeForm"); 
        
        CreationDemandeChequeForm creationDemandeChequeForm = 
            (CreationDemandeChequeForm)form;
        ActionMessages actionMessages = new ActionMessages();
        try {
            ParamAgence paramAgence = new ParamAgence();
            
            paramAgence = (ParamAgence)request.getSession().getAttribute("paramAgBNA");/// structure de l'agent qui fait la saisie
            creationDemandeChequeForm.setNumMatrUser(paramAgence.getNumMatrUser().toString());
            //----------------------------------------------------------------------------//
            // initialistaion du type chéquier
            creationDemandeChequeForm.setChoixTypeChq("0");
            creationDemandeChequeForm.clearForm();
            creationDemandeChequeForm.setCodStrcStrc(paramAgence.getCodStrcStrc().toString());
            
            //----------------------------------------------------------------------------------//
            DateFormat myformat = new SimpleDateFormat("dd/MM/yyyy");
            String d = myformat.format(new Date());
            creationDemandeChequeForm.setDateActuelle(d);
            creationDemandeChequeForm.setDateDemDchq(d);
            creationDemandeChequeForm.setDateComptableDchq(paramAgence.getDateComptable());
            
            //verification de l'habilitation sur cet operation
            StructureDomaine structureDomaine = new StructureDomaine(paramAgence.getCodStrcStrc(),Constants.COD_DOM_CONTRATCOMPTE);
            boolean bool = SmileUtil.testDomaineOuvert(structureDomaine);
            
            
            // recuperer le code Page à partir du menu 
            if (!creationDemandeChequeForm.getCodePage().equals("")) {
                if (creationDemandeChequeForm.getCodePage().equals(Constants.CREATION_DEM_CHQ))
                    creationDemandeChequeForm.setLibelleOperation("P.E.Charge Demande de Chèques/ Autoconfection Lettre de chèque");
                creationDemandeChequeForm.setLibPage("Saisie Demande de Chèques");
            }


            return mapping.findForward("success");
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

    public ActionForward rechercheContrat(ActionMapping mapping, 
                                          ActionForm form, 
                                          HttpServletRequest request, 
                                          HttpServletResponse response) throws IOException, 
                                                                               ServletException {
        ActionMessages actionMessages = new ActionMessages();
        Context context = ContextHandler.getContext();
        CreationDemandeChequeForm creationDemandeChequeForm = 
            (CreationDemandeChequeForm)form;
        try {

            GetDetailContratCmd getDetailContratCmd = 
                new GetDetailContratCmd();
            ContratCptId contratCptId = new ContratCptId();
            String varTypeDemandeur = new String();

            contratCptId.setCodStrcStrc(new Long(creationDemandeChequeForm.getCodStrcStrc()));
            contratCptId.setCodPrdPrd(new Long(creationDemandeChequeForm.getCodPrdPrd()));
            contratCptId.setNumCcptCcpt(new Long(creationDemandeChequeForm.getNumCcptCcpt()));

            //------------------------------------------------
            //-------- sauvgarder qlq information (le code operation, cle)
            Long varCodeOperation = 
                new Long(creationDemandeChequeForm.getCodeOperation());
            String varCleCompte = 
                new String(creationDemandeChequeForm.getCleCompte());
            //----------- Effacer l'ecran 
            creationDemandeChequeForm.clearForm();
            //------------- Recherche des donnée du Contrat et du Client 
            ContratCpt contratCpt = 
                (ContratCpt)getDetailContratCmd.execute(contratCptId);
            //test si contrat existant
            if (!contratCpt.hasError()) {
                if (contratCpt.getContratCptId() != null) {
                    creationDemandeChequeForm.setTestExistanceContrat("ContratExistant");
                    if (!verifierInterdictionChequier(contratCpt.getClient().getPersonne())) {

                        //test si contrat valide
                        if (contratCpt.getCodEtatCcpt().equals(Constants.COD_ETAT_CPT_VALID)) {
                            if (contratCpt.getMontSoldCcpt() != null) {
                                creationDemandeChequeForm.setMontSoldCcpt(contratCpt.getMontSoldCcpt().toString());
                            }

                            creationDemandeChequeForm.setDevise(contratCpt.getDevise().getLibDevDev());
                            creationDemandeChequeForm.setNomIntiCcpt(contratCpt.getNomIntiCcpt());
                            creationDemandeChequeForm.setCodTpceTpceClient(contratCpt.getClient().getPersonne().getTypePiece().getCodTpceTpce().toString());
                            creationDemandeChequeForm.setNumPcePersClient(contratCpt.getClient().getPersonne().getNumPcePers());

                            // affectation du type et du categorie de la personne
                            creationDemandeChequeForm.setTypePersonne(contratCpt.getClient().getTypePers().getCodTperTper());
                            creationDemandeChequeForm.setCategoriePersonne(contratCpt.getClient().getPersonne().getCategoriePersonne().getCodCatpCatp());

                            //-----------------------------------------------------------
                            //----------- Si la personne est une entite co-titulaire
                            if ((contratCpt.getClient().getTypePers().getCodTperTper().equals(Constants.ENTCOTITULAIRE))) {
                                PersonneStrc personneStrc = new PersonneStrc();
                                // chercher les personnes cotitulaires
                                GetListMembreCotitulaireCmd getListMembreCotitulaire = 
                                    new GetListMembreCotitulaireCmd();
                                personneStrc.setCodTpceTpce(contratCpt.getClient().getPersonne().getTypePiece().getCodTpceTpce());
                                personneStrc.setNumPcePers(contratCpt.getClient().getPersonne().getNumPcePers());

                                Listes lisCotitulaire = 
                                    (Listes)getListMembreCotitulaire.execute(personneStrc);
                                if (!lisCotitulaire.hasError()) {
                                    creationDemandeChequeForm.setListCotitulaires(lisCotitulaire.getList());
                                    CoTitulaire cotitulaire = 
                                        (CoTitulaire)lisCotitulaire.getList().get(0);
                                    creationDemandeChequeForm.setNumSeqCliCotitulaire(cotitulaire.getClient().getNumSeqPers().toString());
                                    creationDemandeChequeForm.setTypeCotitulaire(cotitulaire.getCodTcotCoti());
                                    creationDemandeChequeForm.setTypeSignatureCotitulaire(cotitulaire.getCodSigCoti());

                                    creationDemandeChequeForm.setNomNomPersClient(contratCpt.getClient().getPersonne().getNomNomPers());
                                    //creationDemandeChequeForm.setNomPrnPersClient(creationDemandeChequeForm.getContratCpt().getClient().getPersonne().getNomPrnPers());
                                    
                                    creationDemandeChequeForm.setMessageTexte(" Le contrat appartient à une entite co-titulaire.");
                                } else {
                                    List listErreur = contratCpt.getErrors();
                                    //ActionMessages actionMessages = new ActionMessages();
                                    for (Iterator it = listErreur.iterator(); 
                                         it.hasNext(); ) {
                                        com.oxia.fwk.core.Error erreur = 
                                            (com.oxia.fwk.core.Error)it.next();
                                        ActionMessage actionMessage = 
                                            new ActionMessage("exception.generique", 
                                                              erreur.getDescription());
                                        actionMessages.add("Erreur ", 
                                                           actionMessage);
                                    }
                                    this.saveMessages(request, actionMessages);
                                    return mapping.findForward("error");
                                }
                                //----------------------------------------------------------------
                                //--------- Cas d'une personne Morale
                            } else if (contratCpt.getClient().getTypePers().getCodTperTper().equals(Constants.PERSMORALE)) {
                                creationDemandeChequeForm.setNomNomPersClient(contratCpt.getClient().getPersonne().getNomRsPers());
                                creationDemandeChequeForm.setNomPrnPersClient(contratCpt.getClient().getPersonne().getLibSiglPers());
                            } else { // cas d'une personne physique
                                creationDemandeChequeForm.setNomNomPersClient(contratCpt.getClient().getPersonne().getNomNomPers());
                                creationDemandeChequeForm.setNomPrnPersClient(contratCpt.getClient().getPersonne().getNomPrnPers());
                            }

                            if (contratCpt.getAdresseCorresp() != null) {
                                creationDemandeChequeForm.setAdresseCorrespondanceClient(contratCpt.getAdresseCorresp().toString());
                            }

                            creationDemandeChequeForm.setContratCpt(contratCpt);
                            
                            if (creationDemandeChequeForm.getTypePersonne().equals(Constants.ENTCOTITULAIRE)){
                               creationDemandeChequeForm.setAlertDemandeur("alertCotitulaire");
                            }else{
                                creationDemandeChequeForm.setAlertDemandeur("alertTitulaireMandat");
                            }
                          /*  MandatDAO mandatDAO = 
                                (MandatDAO)context.getBean("mandatDAO");
                            Long nombreMandatGen = 
                                mandatDAO.getNombreMandatGenerauxParContrat(contratCpt.getContratCptId().getCodStrcStrc(), 
                                                                            contratCpt.getContratCptId().getCodPrdPrd(), 
                                                                            contratCpt.getContratCptId().getNumCcptCcpt());

                            Long nombreMandatSpe = 
                                mandatDAO.getNombreMandatSpeciauxParContrat(contratCpt.getContratCptId().getCodStrcStrc(), 
                                                                            contratCpt.getContratCptId().getCodPrdPrd(), 
                                                                            contratCpt.getContratCptId().getNumCcptCcpt(), 
                                                                            varCodeOperation);

                            if (nombreMandatGen.intValue() > 0) {
                                if (nombreMandatGen.intValue() == 1) {
                                    creationDemandeChequeForm.setMessageTexte(" un mandat général pour ce contrat");
                                } else {
                                    creationDemandeChequeForm.setMessageTexte(nombreMandatGen + 
                                                                              " mandats généraux pour ce contrat");
                                }
                            }

                            if (nombreMandatSpe.intValue() > 0) {
                                if (nombreMandatSpe.intValue() == 1) {
                                    creationDemandeChequeForm.setMessageTexte("un mandat spécial pour ce contrat");
                                } else {
                                    creationDemandeChequeForm.setMessageTexte(nombreMandatSpe + 
                                                                              " mandats spéciaux pour ce contrat");
                                }
                            }
                            if (nombreMandatSpe.intValue() > 0 || 
                                nombreMandatGen.intValue() > 0) {
                                creationDemandeChequeForm.setTestExistanceMandat("MandatsExistants");
                            } else
                                creationDemandeChequeForm.setTestExistanceMandat("MandatsInexistants");

                            // gestion des alerts du demandeur 
                            if (creationDemandeChequeForm.getTestExistanceMandat().equals("MandatsInexistants")) {
                                if (creationDemandeChequeForm.getTypePersonne().equals(Constants.PERSPHYSIQUE)) {
                                    if (creationDemandeChequeForm.getCategoriePersonne().equals(Constants.COD_CATEGORIE_MINEUR))
                                        creationDemandeChequeForm.setAlertDemandeur("mineurSansMandat");
                                    else if (creationDemandeChequeForm.getCategoriePersonne().equals(Constants.COD_CATEGORIE_P_ETR_INC) || 
                                             creationDemandeChequeForm.getCategoriePersonne().equals(Constants.COD_CATEGORIE_P_TUN_INC))
                                        creationDemandeChequeForm.setAlertDemandeur("incapableSansMandat");
                                } else if (creationDemandeChequeForm.getTypePersonne().equals(Constants.PERSMORALE)) {
                                    creationDemandeChequeForm.setAlertDemandeur("moraleSansMandat");
                                } else if (creationDemandeChequeForm.getTypePersonne().equals(Constants.ENTCOTITULAIRE))
                                    creationDemandeChequeForm.setAlertDemandeur("alertCotitulaire");

                            } else if (creationDemandeChequeForm.getTestExistanceMandat().equals("MandatsExistants")) {
                                if (creationDemandeChequeForm.getTypePersonne().equals(Constants.PERSPHYSIQUE)) {
                                    if (creationDemandeChequeForm.getCategoriePersonne().equals(Constants.COD_CATEGORIE_MINEUR))
                                        creationDemandeChequeForm.setAlertDemandeur("alertMineur");
                                    else if (creationDemandeChequeForm.getCategoriePersonne().equals(Constants.COD_CATEGORIE_P_ETR_INC) || 
                                             creationDemandeChequeForm.getCategoriePersonne().equals(Constants.COD_CATEGORIE_P_TUN_INC))
                                        creationDemandeChequeForm.setAlertDemandeur("alertIncapable");
                                    else if (creationDemandeChequeForm.getCategoriePersonne().equals(Constants.COD_CATEGORIE_PHY_TUN_MAJ))
                                        creationDemandeChequeForm.setAlertDemandeur("alertTitulaireMandat");
                                } else if (creationDemandeChequeForm.getTypePersonne().equals(Constants.PERSMORALE)) {
                                    creationDemandeChequeForm.setAlertDemandeur("alertMorale");
                                }
                            }  */
                           
                        } else {
                            creationDemandeChequeForm.setAlert("ContratNonvalide");
                            creationDemandeChequeForm.setEtatContrat(contratCpt.getCodEtatCcpt());
                        }
                    } else
                        creationDemandeChequeForm.setAlert("TitulaireInterditChequier"); // end if interdiction de chequier...
                } else {
                    //------------- Contrat Inexistant
                    creationDemandeChequeForm.setTestExistanceContrat("contratInexistant");

                }
            } else {
                List listErreur = contratCpt.getErrors();
                //ActionMessages actionMessages = new ActionMessages();
                for (Iterator it = listErreur.iterator(); it.hasNext(); ) {
                    com.oxia.fwk.core.Error erreur = 
                        (com.oxia.fwk.core.Error)it.next();
                    ActionMessage actionMessage = 
                        new ActionMessage("exception.generique", 
                                          erreur.getDescription());
                    actionMessages.add("Erreur ", actionMessage);
                }
                this.saveMessages(request, actionMessages);
                return mapping.findForward("error");
            }
            creationDemandeChequeForm.setCodPrdPrd(contratCptId.getCodPrdPrd().toString());
            creationDemandeChequeForm.setCodStrcStrc(contratCptId.getCodStrcStrc().toString());
            creationDemandeChequeForm.setNumCcptCcpt(contratCptId.getNumCcptCcpt().toString());
            creationDemandeChequeForm.setCleCompte(varCleCompte);
            //creationDemandeChequeForm.setCodeOperation(varCodeOperation.toString());

            //------------------------------------------------------------------------
            //------------- Desactiver les champs  demandeur
           /* if (creationDemandeChequeForm.getTestExistanceMandat().equals("O")) {
                creationDemandeChequeForm.setActiverDemandeur("O");
            }*/
            //-------------- Fin desactivation champs demandeur  
            //------------------------------------------------------------------------ 

            return mapping.findForward("success");

        } catch (Exception e) {
            com.oxia.fwk.core.Error erreur = new com.oxia.fwk.core.Error();
            StringBuffer text = 
                new StringBuffer("la transaction est Interrompu, une erreur dans CreationDemandeChequeAction / Dispatch Action :rechercheContrat ");
            text.append(e.toString());
            erreur.setCode("200");
            erreur.setDescription(text.toString());
            logger.error("Exception : ",e);
            ActionMessage actionMessage = 
                new ActionMessage("exception.generique", 
                                  erreur.getDescription());
            actionMessages.add("Erreur ", actionMessage);
            this.saveMessages(request, actionMessages);
            return mapping.findForward("error");
        }
    }

    public ActionForward verifierDemandeur(ActionMapping mapping, 
                                           ActionForm form, 
                                           HttpServletRequest request, 
                                           HttpServletResponse response) throws IOException, 
                                                                                ServletException {
        CreationDemandeChequeForm creationDemandeChequeForm = 
            (CreationDemandeChequeForm)form;
        ActionMessages actionMessages = new ActionMessages();
        Context context = ContextHandler.getContext();
        
        try {
            creationDemandeChequeForm.setAlert("");
            creationDemandeChequeForm.setTestExistanceDemandeur("");
            creationDemandeChequeForm.setAlertDemandeur("");
            creationDemandeChequeForm.setTypeDemandeur("");
            // Vider les champs concernant le mandats
            creationDemandeChequeForm.setListdesMandatsPersonneChoisi(null);
            creationDemandeChequeForm.setListMandats(null);
            creationDemandeChequeForm.setListMandatsConcernesPersonne(null);
            creationDemandeChequeForm.setListMandatsPersonneConcernesDemandeur(null);

            Long typePieceTitulaireContrat = 
                creationDemandeChequeForm.getContratCpt().getClient().getPersonne().getTypePiece().getCodTpceTpce();
            String numeroPieceTitulaireContrat = 
                creationDemandeChequeForm.getContratCpt().getClient().getPersonne().getNumPcePers();

            DemandeCheque demandeCheque = new DemandeCheque();
            DemandeCheque demandeChequeNew = new DemandeCheque();
            ParamDemandeCheque paramDemandeCheque = new ParamDemandeCheque();
            ContratPersonne contratPersonne = new ContratPersonne();
            contratPersonne.setContratCptId(creationDemandeChequeForm.getContratCpt().getContratCptId());
            paramDemandeCheque.setTypeConfection(creationDemandeChequeForm.getCodConfConf());

            //------- recherche de la personne
            PersonneStrc personneStrc = new PersonneStrc();
            personneStrc.setCodTpceTpce(new Long(creationDemandeChequeForm.getCodTpceTpceDemandeur()));
            personneStrc.setNumPcePers(creationDemandeChequeForm.getNumPcePersDemandeur());
            GetPersonneCmd getPersonneCmd = new GetPersonneCmd();
            GetPersonneCptCmd getPersonneCptCmd = new GetPersonneCptCmd();
            
            
             PersonneCpt personneCpt = (PersonneCpt)getPersonneCptCmd.execute(personneStrc);
            
            if (!personneCpt.hasError()) {
                creationDemandeChequeForm.setDemandeur(personneCpt.getPersonne());
                //------- Fin de la recherche de la personne
                if (personneCpt.getPersonne() != null) {
                    creationDemandeChequeForm.setIdDemandeur(personneCpt.getPersonne().getNumSeqPers().toString());
                    //---------- cas de titulaire
                    if(personneCpt.getPersonne().getDatDecePers() == null  && personneCpt.getPersonne().getNumDecePers() == null){ 
                    
                    if (personneCpt.getPersonne().getTypePiece().getCodTpceTpce().equals(typePieceTitulaireContrat) && 
                        personneCpt.getPersonne().getNumPcePers().equals(numeroPieceTitulaireContrat)) {
                        if (creationDemandeChequeForm.getCategoriePersonne().equals(Constants.COD_CATEGORIE_P_ETR_INC) || 
                            creationDemandeChequeForm.getCategoriePersonne().equals(Constants.COD_CATEGORIE_P_TUN_INC)) {
                            // traitement du cas personne majeure incapable
                            creationDemandeChequeForm.setAlertDemandeur("demandeurIncapable");
                            creationDemandeChequeForm.setNomNomPersDemandeur(null);
                            creationDemandeChequeForm.setNomPrnPersDemandeur(null);
                        } else {
                            creationDemandeChequeForm.setTypeDemandeur("T");
                            creationDemandeChequeForm.setMessageTexte("Titulaire du compte");
                            creationDemandeChequeForm.setOpenTabSheetCotitulaire("false");
                            creationDemandeChequeForm.setOpenTabSheetMandat("false");
                            creationDemandeChequeForm.setOpenTabSheetOperation("false");
                            creationDemandeChequeForm.setNomNomPersDemandeur(personneCpt.getPersonne().getNomNomPers());
                            creationDemandeChequeForm.setNomPrnPersDemandeur(personneCpt.getPersonne().getNomPrnPers());
                            //---------------------------------------------------------------------------------------------------------------
                            // verifier la demande en cours 
                            //---------------------------------------------------------------------------------------------------------------                
                            contratPersonne.setPersonneId(personneStrc);
                            paramDemandeCheque.setContratPersonne(contratPersonne);
                            if (!verifierInterdictionChequier(creationDemandeChequeForm.getDemandeur())) {
                                // si le demandeur n'est pas interdit de chequier
                                demandeCheque = 
                                        verifierDemandeEnCours(paramDemandeCheque, 
                                                               creationDemandeChequeForm);
                                if (demandeCheque.getNumDemDchq() == null) {
                                    // si pas de  demande en cours

                                } else {
                                    // si demande en cours
                                    creationDemandeChequeForm.setMotifRejet(Constants.MOTIF_DEM_EN_COURS.toString()); 
                                    determinerAlertEncours(creationDemandeChequeForm, 
                                                           demandeCheque);                                    
                                    creationDemandeChequeForm.setAlertDemandeur("demandeEnCours");
                                   
                                }
                            } else {
                                creationDemandeChequeForm.setEtatDemDchq(Constants.DEM_CHQ_REJETEE.toString());
                                creationDemandeChequeForm.setMotifRejet(Constants.MOTIF_DEM_INTERDIT_CHQ.toString());
                                demandeChequeNew =  gererDemandeChequier(creationDemandeChequeForm,creationDemandeChequeForm.getContratCpt());
                                

                                creationDemandeChequeForm.setMessageAlerte("Le demandeur du chequier  " + 
                                                                           creationDemandeChequeForm.getNomNomPersDemandeur() + 
                                                                           "  " + 
                                                                           creationDemandeChequeForm.getNomPrnPersDemandeur() + 
                                                                           " est interdit de chequier. ");
                                creationDemandeChequeForm.setAlertDemandeur("demandeurInterditChequier");
                            }
                        }
                    } else {
                        // cas d'un mandataire 
                         //afficher les mandats qui concerne la personne qui ne représente ni le titulaire, ni le cotitulaire du compte
                        MandatDAO mandatDAO = (MandatDAO)context.getBean("mandatDAO");
                        Long nombreMandatGen = 
                        mandatDAO.getNombreMandatGenerauxParContrat(creationDemandeChequeForm.getContratCpt().getContratCptId().getCodStrcStrc(), 
                                                                                                    creationDemandeChequeForm.getContratCpt().getContratCptId().getCodPrdPrd(), 
                                                                                                    creationDemandeChequeForm.getContratCpt().getContratCptId().getNumCcptCcpt());

                       Long nombreMandatSpe = 
                       mandatDAO.getNombreMandatSpeciauxParContrat(creationDemandeChequeForm.getContratCpt().getContratCptId().getCodStrcStrc(), 
                                                                                                    creationDemandeChequeForm.getContratCpt().getContratCptId().getCodPrdPrd(), 
                                                                                                    creationDemandeChequeForm.getContratCpt().getContratCptId().getNumCcptCcpt(), 
                                                                                                    Long.valueOf(creationDemandeChequeForm.getCodeOperation()));
                        
                        if (nombreMandatSpe.intValue() > 0 || 
                            nombreMandatGen.intValue() > 0) {
                            creationDemandeChequeForm.setTestExistanceMandat("MandatsExistants");
                        } else{
                            creationDemandeChequeForm.setTestExistanceMandat("MandatsInexistants");
                        }  
                        
                       
                       
                        if (creationDemandeChequeForm.getTestExistanceMandat().equals("MandatsExistants")) {

                            creationDemandeChequeForm.setNomNomPersDemandeur(personneCpt.getPersonne().getNomNomPers());
                            creationDemandeChequeForm.setNomPrnPersDemandeur(personneCpt.getPersonne().getNomPrnPers());
                            creationDemandeChequeForm.setTypeDemandeur("M");
                            creationDemandeChequeForm.setOpenTabSheetMandat("true");
                            creationDemandeChequeForm.setOpenTabSheetCotitulaire("false");
                            creationDemandeChequeForm.setOpenTabSheetOperation("false");

                            GetListMandatOperationPersonneContratOperationCmd getListM = 
                                new GetListMandatOperationPersonneContratOperationCmd();
                            //--------------  Les Objets du parametre ---------------------------------

                            ParamMandatOperationVo paramMandatOperationVo = 
                                new ParamMandatOperationVo();
                            ContratCpt contratCptRecherche = new ContratCpt();
                            Operation operation = new Operation();

                            contratCptRecherche.setContratCptId(creationDemandeChequeForm.getContratCpt().getContratCptId());
                            operation.setCodOperOper(new Long(creationDemandeChequeForm.getCodeOperation()));

                            personneStrc.setCodTpceTpce(personneCpt.getPersonne().getTypePiece().getCodTpceTpce()); // new Long(creationDemandeChequeForm.getCodTpceTpceDemandeur()));
                            personneStrc.setNumPcePers(personneCpt.getPersonne().getNumPcePers());

                            paramMandatOperationVo.setContraCptId(contratCptRecherche.getContratCptId());
                            paramMandatOperationVo.setPersonneStrc(personneStrc);
                            paramMandatOperationVo.setOperation(operation);

                            ListMandatOperationVo listMandatOperation = 
                                (ListMandatOperationVo)getListM.execute(paramMandatOperationVo);

                            //---------------------------------------------- 
                            //------- Remplir la liste des mandats View par les mandat generaux
                            if (!listMandatOperation.hasError()) {
                                List listeDesMandatsView = new ArrayList();
                                if (listMandatOperation.getListMandatsGeneraux().size() > 
                                    0) {
                                    for (Iterator it = 
                                         listMandatOperation.getListMandatsGeneraux().iterator(); 
                                         it.hasNext(); ) {
                                        Mandat mandat = (Mandat)it.next();
                                        MandatView mandatView = 
                                            new MandatView();
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
                                if (listMandatOperation.getListMandatsSpeciauxOperations().size() > 
                                    0) {
                                    for (Iterator it = 
                                         listMandatOperation.getListMandatsSpeciauxOperations().iterator(); 
                                         it.hasNext(); ) {
                                        Mandat mandat = (Mandat)it.next();
                                        MandatView mandatView = 
                                            new MandatView();
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

                                creationDemandeChequeForm.setListMandatsConcernesPersonne(listeDesMandatsView);
                                if (creationDemandeChequeForm.getListMandatsConcernesPersonne().size() == 
                                    0) {
                                    creationDemandeChequeForm.setAlertDemandeur("PasDeMandat");
                                    creationDemandeChequeForm.setMessageTexte("Pouvoir invalide");
                                    creationDemandeChequeForm.setOpenTabSheetMandat("false");

                                }
                            } else {
                                List listErreur = 
                                    listMandatOperation.getErrors();
                                for (Iterator it = listErreur.iterator(); 
                                     it.hasNext(); ) {
                                    com.oxia.fwk.core.Error erreur = 
                                        (com.oxia.fwk.core.Error)it.next();
                                    ActionMessage actionMessage = 
                                        new ActionMessage("exception.generique", 
                                                          erreur.getDescription());
                                    actionMessages.add("Erreur ", 
                                                       actionMessage);
                                }
                                this.saveMessages(request, actionMessages);
                                return mapping.findForward("error");
                            }
                        } //Fin test sur mandataire
                        else {
                            if (creationDemandeChequeForm.getContratCpt().getClient().getTypePers().getCodTperTper().equals(Constants.ENTCOTITULAIRE)) {
                                // traitement du cas cotitulaire
                                boolean membreTrouve = false;
                                if (creationDemandeChequeForm.getListCotitulaires().size() > 
                                    0) {
                                    for (Iterator it = 
                                         creationDemandeChequeForm.getListCotitulaires().iterator(); 
                                         it.hasNext(); ) {
                                        CoTitulaire coTitulaire = 
                                            (CoTitulaire)it.next();
                                        if (personneCpt.getPersonne().getNumSeqPers().equals(coTitulaire.getCoTitulaireId().getNumSeqPers())) {
                                            // membre cotitulaire existant dans l'entité
                                            creationDemandeChequeForm.setNomNomPersDemandeur(personneCpt.getPersonne().getNomNomPers());
                                            creationDemandeChequeForm.setNomPrnPersDemandeur(personneCpt.getPersonne().getNomPrnPers());
                                            creationDemandeChequeForm.setOpenTabSheetCotitulaire("true");
                                            creationDemandeChequeForm.setOpenTabSheetMandat("false");
                                            creationDemandeChequeForm.setOpenTabSheetOperation("false");
                                            creationDemandeChequeForm.setCotitulaire(coTitulaire);
                                            creationDemandeChequeForm.setTypeDemandeur("C");
                                            membreTrouve = true;
                                        }
                                    }

                                    // Verification de l'interdiction de chequier et du demande en cours dans le cas cotitulaire
                                    if (membreTrouve) {
                                        if (creationDemandeChequeForm.getListCotitulaires().size() > 
                                            0) {
                                            for (Iterator it = 
                                                 creationDemandeChequeForm.getListCotitulaires().iterator(); 
                                                 it.hasNext(); ) {

                                                CoTitulaire coTitulaire = 
                                                    (CoTitulaire)it.next();
                                                personneStrc.setCodTpceTpce(coTitulaire.getPersonne().getTypePiece().getCodTpceTpce());
                                                personneStrc.setNumPcePers(coTitulaire.getPersonne().getNumPcePers());
                                                contratPersonne.setPersonneId(personneStrc);
                                                paramDemandeCheque.setContratPersonne(contratPersonne);

                                                if (!verifierInterdictionChequier(coTitulaire.getPersonne())) {
                                                    demandeCheque = 
                                                            verifierDemandeEnCours(paramDemandeCheque, 
                                                                                   creationDemandeChequeForm);
                                                    if (demandeCheque.getNumDemDchq() != 
                                                        null) {
                                                        // si demande en cours
                                                        //rejeter la demande pour motif demande encours
                                                        determinerAlertEncours(creationDemandeChequeForm, 
                                                                               demandeCheque);                                                        
                                                        creationDemandeChequeForm.setMotifRejet(Constants.MOTIF_DEM_EN_COURS.toString());
                                                        //demandeChequeNew =  gererDemandeChequier(creationDemandeChequeForm,creationDemandeChequeForm.getContratCpt());
                                                        creationDemandeChequeForm.setAlertDemandeur("demandeEnCours");
                                                        break;
                                                    }
                                                } else {
                                                    // --rejeter la demande interdiction de chequier
                                                    creationDemandeChequeForm.setEtatDemDchq(Constants.DEM_CHQ_REJETEE.toString());
                                                    creationDemandeChequeForm.setMotifRejet(Constants.MOTIF_DEM_INTERDIT_CHQ.toString());
                                                    demandeChequeNew =  gererDemandeChequier(creationDemandeChequeForm,creationDemandeChequeForm.getContratCpt());

                                                    creationDemandeChequeForm.setMessageAlerte("Le membre cotitulaire  " + 
                                                                                               coTitulaire.getPersonne().getNomNomPers() + 
                                                                                               "  " + 
                                                                                               coTitulaire.getPersonne().getNomPrnPers() + 
                                                                                               " est interdit de chequier. ");
                                                    creationDemandeChequeForm.setAlertDemandeur("demandeurInterditChequier");
                                                    break;
                                                }
                                            } // end for
                                        }
                                    }

                                    if (!membreTrouve) {
                                        creationDemandeChequeForm.setOpenTabSheetCotitulaire("false");
                                        creationDemandeChequeForm.setAlertDemandeur("membreInexistant");
                                    }
                                }
                            } //fin test cotitulaire 
                            else{
                                 creationDemandeChequeForm.setAlertDemandeur("DemandeurInvalide");
                                 creationDemandeChequeForm.setMessageTexte(null);
                                }
                        }
                    }
                  } // fin test deces...
                  else {
                      creationDemandeChequeForm.setAlertDemandeur("demandeurDecede");
                      creationDemandeChequeForm.setNomNomPersDemandeur(null);
                      creationDemandeChequeForm.setNomPrnPersDemandeur(null);
                      
                  }
                } else { //-- Demandeur inexistant dans la base
                    creationDemandeChequeForm.setMessageTexte(null);
                    creationDemandeChequeForm.setTestExistanceDemandeur("N");
                    creationDemandeChequeForm.setOpenTabSheetCotitulaire("false");
                    creationDemandeChequeForm.setOpenTabSheetMandat("false");
                    creationDemandeChequeForm.setOpenTabSheetOperation("false");
                }
              } else {
                List listErreur = personneCpt.getErrors();
                //ActionMessages actionMessages = new ActionMessages();
                for (Iterator it = listErreur.iterator(); it.hasNext(); ) {
                    com.oxia.fwk.core.Error erreur = 
                        (com.oxia.fwk.core.Error)it.next();
                    ActionMessage actionMessage = 
                        new ActionMessage("exception.generique", 
                                          erreur.getDescription());
                    actionMessages.add("Erreur ", actionMessage);
                }
                this.saveMessages(request, actionMessages);
                return mapping.findForward("error");
            }
           
            
            if(demandeChequeNew.hasError()){             
              
                List listErreur = demandeChequeNew.getErrors();
                for (Iterator it1 = listErreur.iterator(); it1.hasNext(); ) {
                    com.oxia.fwk.core.Error erreur =(com.oxia.fwk.core.Error)it1.next();
                    ActionMessage actionMessage = new ActionMessage("exception.generique", erreur.getDescription());
                    actionMessages.add("Erreur ", actionMessage);
                 }
                    this.saveMessages(request, actionMessages);
                    return mapping.findForward("error");
            } 
            return mapping.findForward("success");
        } catch (Exception e) {
            com.oxia.fwk.core.Error erreur = new com.oxia.fwk.core.Error();
            StringBuffer text = 
                new StringBuffer("la transaction est Interrompu, une erreur dans CreationDemandeChequeAction / Dispatch Action :verifierDemandeur ");
            text.append(e.toString());
            erreur.setCode("200");
            erreur.setDescription(text.toString());
            logger.error("Exception : ",e);
            ActionMessage actionMessage = 
                new ActionMessage("exception.generique", 
                                  erreur.getDescription());
            actionMessages.add("Erreur ", actionMessage);
            this.saveMessages(request, actionMessages);
            return mapping.findForward("error");
        }
    }

    public ActionForward annuler(ActionMapping mapping, ActionForm form, 
                                 HttpServletRequest request, 
                                 HttpServletResponse response) throws IOException, 
                                                                      ServletException {
        CreationDemandeChequeForm creationDemandeChequeForm = 
            (CreationDemandeChequeForm)form;
        creationDemandeChequeForm.clearForm();
        return mapping.findForward("success");
    }


    public ActionForward mandatOperation(ActionMapping mapping, 
                                         ActionForm form, 
                                         HttpServletRequest request, 
                                         HttpServletResponse response) throws IOException, 
                                                                              ServletException {
        CreationDemandeChequeForm creationDemandeChequeForm = 
            (CreationDemandeChequeForm)form;
        ActionMessages actionMessages = new ActionMessages();
        try {
            Mandat mandat = new Mandat();
            /* rechercher le mandat */
            for (Iterator it = 
                 creationDemandeChequeForm.getListMandatsConcernesPersonne().iterator(); 
                 it.hasNext(); ) {
                MandatView mandatView = (MandatView)it.next();
                if (mandatView.getMandat().getNumMandMand().equals(new Long(creationDemandeChequeForm.getNumeroMandatChoisi()))) {
                    mandat = mandatView.getMandat();
                }

            }
            creationDemandeChequeForm.setRefMand(mandat.getNumDemMand().toString());
            creationDemandeChequeForm.setSignatureMandatChoisi(mandat.getCodSignMand());
            if(mandat.getNbrMinMand()!= null)
             creationDemandeChequeForm.setNbrMinMandatChoisi(mandat.getNbrMinMand().toString());
            else creationDemandeChequeForm.setNbrMinMandatChoisi("0");
            
            creationDemandeChequeForm.setTypeMandatChoisi(mandat.getCodTypMand());
            GetDetailMandatCmd getDetailMandatCmd = new GetDetailMandatCmd();
            DetailMandat detailMandat = 
                (DetailMandat)getDetailMandatCmd.execute(mandat);

            //---------------------------------------------------------------------//
            //----------- Recherche de l'Operation demande de chèque        ------------------------/
            //---------------------------------------------------------------------//
            if (!detailMandat.hasError()) {
                if (mandat.getCodTypMand().equals("S")) {
                    if (detailMandat.getListeMandatOperations() != null && 
                        detailMandat.getListeMandatOperations().size() > 0) {

                        for (Iterator it = 
                             detailMandat.getListeMandatOperations().iterator(); 
                             it.hasNext(); ) {
                            MandatOperation mandatOperation = 
                                (MandatOperation)it.next();
                            if (mandatOperation.getOperation().getCodOperOper().equals(new Long(creationDemandeChequeForm.getCodeOperation()))) {
                                creationDemandeChequeForm.setNumOperation(mandatOperation.getMandatOperationId().getNumMaopMaop().toString());
                                creationDemandeChequeForm.setSignatureMandatChoisi(mandatOperation.getCodSignMaop());
                                creationDemandeChequeForm.setNbrMinMandatChoisi(mandatOperation.getNbrMinMaop().toString());
                            }
                        }
                    } // Fin For  
                }

                if (mandat.getCodTypMand().equals("G")) {
                    // mandat général 
                    creationDemandeChequeForm.setMessageTexte(" dossier Mandat choisi : " + 
                                                              mandat.getNumDemMand() + 
                                                              ", type : " + 
                                                              mandat.getCodTypMand() + 
                                                              " , Signature : " + 
                                                              mandat.getCodSignMand());
                } else {
                    // mandat spécial ou juridique
                    creationDemandeChequeForm.setMessageTexte(" dossier Mandat choisi : " + 
                                                              mandat.getNumDemMand() + 
                                                              ", type : " + 
                                                              mandat.getCodTypMand() + 
                                                              " , Signature : " + 
                                                              creationDemandeChequeForm.getSignatureMandatChoisi() + 
                                                              " (selon opération appropriée)");
                }
                //---------------------------------------------------------------------//
                //----------- Recherche des personnes         ------------------------//
                //---------------------------------------------------------------------//
                if (detailMandat.getListeMandatPersonnes() != null && 
                    detailMandat.getListeMandatPersonnes().size() > 0) {
                    creationDemandeChequeForm.setOpenTabSheetOperation("true");
                    creationDemandeChequeForm.setOpenTabSheetCotitulaire("false");
                    creationDemandeChequeForm.setListdesMandatsPersonneChoisi(new ArrayList());
                    for (Iterator it = 
                         detailMandat.getListeMandatPersonnes().iterator(); 
                         it.hasNext(); ) {
                        MandatPersonne mandatPers = (MandatPersonne)it.next();
                        if ((creationDemandeChequeForm.getDemandeur().getTypePiece().getCodTpceTpce().equals(mandatPers.getPersonne().getTypePiece().getCodTpceTpce())) && 
                            (creationDemandeChequeForm.getDemandeur().getNumPcePers().equals(mandatPers.getPersonne().getNumPcePers()))) {
                            creationDemandeChequeForm.getListdesMandatsPersonneChoisi().add(creationDemandeChequeForm.getDemandeur().getNumSeqPers());
                        } else {
                            creationDemandeChequeForm.getListdesMandatsPersonneChoisi().add("");
                        }
                    }
                    creationDemandeChequeForm.setListMandatsPersonneConcernesDemandeur(detailMandat.getListeMandatPersonnes());
                    creationDemandeChequeForm.setNbreSignataires(new Integer(detailMandat.getListeMandatPersonnes().size()).toString());
                }
            } else {
                List listErreur = detailMandat.getErrors();
                for (Iterator it = listErreur.iterator(); it.hasNext(); ) {
                    com.oxia.fwk.core.Error erreur = 
                        (com.oxia.fwk.core.Error)it.next();
                    ActionMessage actionMessage = 
                        new ActionMessage("exception.generique", 
                                          erreur.getDescription());
                    actionMessages.add("Erreur ", actionMessage);
                }
                this.saveMessages(request, actionMessages);
                return mapping.findForward("error");
            }
            return mapping.findForward("success");
        } catch (Exception e) {
            com.oxia.fwk.core.Error erreur = new com.oxia.fwk.core.Error();
            StringBuffer text = 
                new StringBuffer("la transaction est Interrompu, une erreur dans CreationDemandeChequeAction / Dispatch Action :mandatOperation ");
            text.append(e.toString());
            erreur.setCode("200");
            erreur.setDescription(text.toString());
            logger.error("Exception : ",e); 
            ActionMessage actionMessage = 
                new ActionMessage("exception.generique", 
                                  erreur.getDescription());
            actionMessages.add("Erreur ", actionMessage);
            this.saveMessages(request, actionMessages);
            return mapping.findForward("error");
        }
    }

    public DemandeCheque verifierDemandeEnCours(ParamDemandeCheque paramDemandeCheque, 
                                                ActionForm form) {

        CreationDemandeChequeForm creationDemandeChequeForm = 
            (CreationDemandeChequeForm)form;
        DemandeCheque demandeCheque = new DemandeCheque();
        try{
        
        
        ListesDemandesCheques listesDemandesCheques = 
            new ListesDemandesCheques();

        if (creationDemandeChequeForm.getTypeDemandeur().equals("T") || 
            creationDemandeChequeForm.getTypeDemandeur().equals("C")) {
            // cas du titulaire ou  Cotitulaire
            GetListDemandeChequePersonneCmd getListDemandeChequePersonneCmd = 
                new GetListDemandeChequePersonneCmd();
            listesDemandesCheques = 
                    (ListesDemandesCheques)getListDemandeChequePersonneCmd.execute(paramDemandeCheque);
        } else {
            // cas du mandataire 
            GetListDemandesChequesMandatPersonneCmd getListDemandesChequesManadtPersonneCmd = 
                new GetListDemandesChequesMandatPersonneCmd();
            listesDemandesCheques = 
                    (ListesDemandesCheques)getListDemandesChequesManadtPersonneCmd.execute(paramDemandeCheque);
        }

        if (listesDemandesCheques.getListeAttente() != null && 
            listesDemandesCheques.getListeAttente().size() > 0 && 
            demandeCheque.getNumDemDchq() == null)
            demandeCheque = 
                    (DemandeCheque)listesDemandesCheques.getListeAttente().get(0);
        else if (listesDemandesCheques.getListeValidee() != null && 
                 listesDemandesCheques.getListeValidee().size() > 0 && 
                 demandeCheque.getNumDemDchq() == null)
            demandeCheque = 
                    (DemandeCheque)listesDemandesCheques.getListeValidee().get(0);
        else if (listesDemandesCheques.getListeTotSatisfaite() != null && 
                 listesDemandesCheques.getListeTotSatisfaite().size() > 0 && 
                 demandeCheque.getNumDemDchq() == null)
            demandeCheque = 
                    (DemandeCheque)listesDemandesCheques.getListeTotSatisfaite().get(0);
        else if (listesDemandesCheques.getListePartSatisfaite() != null && 
                 listesDemandesCheques.getListePartSatisfaite().size() > 0 && 
                 demandeCheque.getNumDemDchq() == null)
            demandeCheque = 
                    (DemandeCheque)listesDemandesCheques.getListePartSatisfaite().get(0);
        else if (listesDemandesCheques.getListeEnvoyeeDR_DCCI() != null && 
                 listesDemandesCheques.getListeEnvoyeeDR_DCCI().size() > 0 && 
                 demandeCheque.getNumDemDchq() == null)
            demandeCheque = 
                    (DemandeCheque)listesDemandesCheques.getListeEnvoyeeDR_DCCI().get(0);
        
        } catch (Exception e) {
               logger.error("Erreur au niveau de l'agence <<" +creationDemandeChequeForm.getCodStrcStrc() + ">>. Exception dans souscriptionContratCompteAction / Methode : creerClient:  ",e);  
               throw new RuntimeException(e);               
        }   
        
        return demandeCheque;


    }

    public void determinerAlertEncours(ActionForm form, 
                                       DemandeCheque demandeCheque) {

        CreationDemandeChequeForm creationDemandeChequeForm = 
            (CreationDemandeChequeForm)form;

        if (demandeCheque.getNumDemDchq() != null) {
            creationDemandeChequeForm.setAlertDemandeur("demandeEnCours");
            // il existe une demande en cour
            
            
            if (demandeCheque.getCodEtatDchq().equals(Constants.DEM_CHQ_PART_SATISFAITE) || 
                demandeCheque.getCodEtatDchq().equals(Constants.DEM_CHQ_TOT_SATISFAITE)) {
                // si la demande est reçu ( chequiers reçus : tot ou partiellement  satisfaite )
                creationDemandeChequeForm.setMessageAlerte("Une demande de chèquiers N° " + 
                                                           demandeCheque.getNumDemDchq() + 
                                                           " crée le  " + 
                                                           DateHandler.dateToStr(demandeCheque.getDatDemDchq()) + 
                                                           " au nom du  " + 
                                                           creationDemandeChequeForm.getNomNomPersDemandeur() + 
                                                           "  " + 
                                                           creationDemandeChequeForm.getNomPrnPersDemandeur() + 
                                                           " est déja satisfaite. Voulez vous forçer la demande ?");
            } else if (demandeCheque.getCodEtatDchq().equals(Constants.DEM_CHQ_ENVOYEE_DR) || 
                       demandeCheque.getCodEtatDchq().equals(Constants.DEM_CHQ_ATTENTE) || 
                       demandeCheque.getCodEtatDchq().equals(Constants.DEM_CHQ_VALIDEE)) {
                // si la demande est en cours de confection ( chequiers reçus : tot ou partiellement  satisfaite )
                creationDemandeChequeForm.setMessageAlerte("Une demande de chèquiers N° " + 
                                                           demandeCheque.getNumDemDchq() + 
                                                           " crée le  " + 
                                                           DateHandler.dateToStr(demandeCheque.getDatDemDchq()) + 
                                                           " au nom du  " + 
                                                           creationDemandeChequeForm.getNomNomPersDemandeur() + 
                                                           "  " + 
                                                           creationDemandeChequeForm.getNomPrnPersDemandeur() + 
                                                           "  est déja en cours de confection. Voulez vous forçer la demande ? ");
              }
            
        }
    }

    public DemandeCheque gererDemandeChequier(ActionForm form, ContratCpt contratCpt ){
        
        DateFormat myformat = new SimpleDateFormat("dd/MM/yyyy");
        CreationDemandeChequeForm creationDemandeChequeForm = 
            (CreationDemandeChequeForm)form;
        DemandeCheque demandeCheque = new DemandeCheque();
       try{
        Tache tache = new Tache();
        TacheId tacheId = new TacheId();        
        tacheId.setCodOperOper(new Long(creationDemandeChequeForm.getCodeOperation()));
        tacheId.setCodTachTach(new Long(creationDemandeChequeForm.getCodeTache()));
        tache.setTacheId(tacheId);
        TypeConfection typeConfection = new TypeConfection();
        typeConfection.setCodConfConf(creationDemandeChequeForm.getCodConfConf());
        Personnel personnel = new Personnel();
        personnel.setNumMatrUser(creationDemandeChequeForm.getNumMatrUser());


        //affectation de la demande
        demandeCheque.setContratCpt(contratCpt);
        demandeCheque.setTache(tache);
        demandeCheque.setTypeConfection(typeConfection);
        demandeCheque.setPersonnel(personnel);
        demandeCheque.setDatDemDchq(DateHandler.strToDate(creationDemandeChequeForm.getDateDemDchq()));   
        demandeCheque.setDatCompDchq(DateHandler.strToDate(creationDemandeChequeForm.getDateComptableDchq())); 
        demandeCheque.setNbrChqDchq(new Long(creationDemandeChequeForm.getNbreChequesDchq()));
        demandeCheque.setNbrChqiDchq(new Long(creationDemandeChequeForm.getNbreChequiers()));
        demandeCheque.setCodEtatDchq(new Long(creationDemandeChequeForm.getEtatDemDchq()));
        demandeCheque.setNumPceDchq(creationDemandeChequeForm.getDemandeur().getNumPcePers());
        demandeCheque.setCodTpceDchq(creationDemandeChequeForm.getDemandeur().getTypePiece().getCodTpceTpce());

        if (demandeCheque.getCodEtatDchq().equals(Constants.DEM_CHQ_ENVOYEE_DR)) {
            demandeCheque.setDatEnvdDchq(DateHandler.strToDate(DateHandler.dateJour()));
            if (creationDemandeChequeForm.getCodConfConf().equals(Constants.CODE_CHEQUE_PERSONALISE) || 
                creationDemandeChequeForm.getCodConfConf().equals(Constants.CODE_LETTRE_CHEQUE)) {
                demandeCheque.setCodFraiDchq(new Long(creationDemandeChequeForm.getCodFraisDchq()));
                demandeCheque.setCodTfacDchq(new Long(creationDemandeChequeForm.getTypeFactDchq()));                
            }
            
            if(creationDemandeChequeForm.getCodConfConf().equals(Constants.CODE_LETTRE_CHEQUE)) {
                demandeCheque.setCodTlcDchq(Long.valueOf(creationDemandeChequeForm.getTypeLCheque()));
            }
            
        }

        if (!creationDemandeChequeForm.getMotifRejet().equals(new String("0"))) {
            MotifRejet motifRejet = new MotifRejet();
            motifRejet.setCodMotfMrej(new Long(creationDemandeChequeForm.getMotifRejet()));
            demandeCheque.setMotifRejet(motifRejet);
            demandeCheque.setCodEtatDchq(Constants.DEM_CHQ_REJETEE);
        }
        demandeCheque.setCodDemDchq(creationDemandeChequeForm.getTypeDemandeur());
        
        if (!creationDemandeChequeForm.getStrcDecision().equals(new String("0"))) {
            demandeCheque.setCodDecsDchq(creationDemandeChequeForm.getStrcDecision());        
        }
        
        demandeCheque.setBoolForcDchq(Long.valueOf(creationDemandeChequeForm.getForcage()));
        
        if (creationDemandeChequeForm.getTypeDemandeur().equals("C")) {
            // cas cotitulaire
            if (creationDemandeChequeForm.getCotitulaire() != null) {
                demandeCheque.setCoTitulaire(creationDemandeChequeForm.getCotitulaire());
            }
        }

        InsertDemandeChequeCmd insertDemandeChequeCmd = 
            new InsertDemandeChequeCmd();
        demandeCheque = 
                (DemandeCheque)insertDemandeChequeCmd.execute(demandeCheque);
                
        if (creationDemandeChequeForm.getTypeDemandeur().equals("M")) {
            // cas du mandataires il faut inserer dans la table DemandeChequeMandatPersonne
            if (demandeCheque.getNumDemDchq() != null) {
                InsertDemandeChequeMandatPersonneCmd insertDemandeChequeMandatPersonneCmd = 
                    new InsertDemandeChequeMandatPersonneCmd();

                
                if (creationDemandeChequeForm.getSignatureMandatChoisi().equals("S")) {
                    // signature séparée
                    /// insertion juste du demandeur de cheque
                    DemandeChequeMandatPersonne demandeChequeMandatPersonneRetour = 
                        (DemandeChequeMandatPersonne)insertDemandeChequeMandatPersonneCmd.execute(affecterDonneesDemandeChequeMandatpersonne(creationDemandeChequeForm, 
                                                                                                                                             creationDemandeChequeForm.getDemandeur().getNumSeqPers(), 
                                                                                                                                             demandeCheque));
                } else {
                    // signature conjointe(insertion de tous les signataires)           
                    for (Iterator it = 
                         creationDemandeChequeForm.getListMandataireCoches().iterator(); 
                         it.hasNext(); ) {
                        String numSeq = (String)it.next();
                        DemandeChequeMandatPersonne demandeChequeMandatPersonneRetour = 
                            (DemandeChequeMandatPersonne)insertDemandeChequeMandatPersonneCmd.execute(affecterDonneesDemandeChequeMandatpersonne(creationDemandeChequeForm,   new Long(numSeq), demandeCheque));
                       
                    }
                    
                    
                }
            }
        }
        
        } catch (Exception e) {
               logger.error("Erreur au niveau de l'agence <<" +creationDemandeChequeForm.getCodStrcStrc() + ">>. Exception dans souscriptionContratCompteAction / Methode : gererDemandeChequier:  ",e);  
               throw new RuntimeException(e);               
        }   
        return (demandeCheque);
    }

    public DemandeChequeMandatPersonne affecterDonneesDemandeChequeMandatpersonne(ActionForm form, 
                                                                                  Long numSeqPers, 
                                                                                  DemandeCheque demandeCheque) {
   
        CreationDemandeChequeForm creationDemandeChequeForm = 
            (CreationDemandeChequeForm)form;
        DemandeChequeMandatPersonne demandeChequeMandatPersonne = 
            new DemandeChequeMandatPersonne();
        DemandeChequeMandatPersonneId demandeChequeMandatPersonneId = 
            new DemandeChequeMandatPersonneId();
      try{      
        demandeChequeMandatPersonneId.setNumMandMand(new Long(creationDemandeChequeForm.getNumeroMandatChoisi()));
        demandeChequeMandatPersonneId.setNumDemDchq(demandeCheque.getNumDemDchq());
        demandeChequeMandatPersonne.setDemandeCheque(demandeCheque);
        demandeChequeMandatPersonneId.setNumSeqPers(numSeqPers);
        demandeChequeMandatPersonne.setDemandeChequeMandatPersonneId(demandeChequeMandatPersonneId);
        } catch (Exception e) {
               logger.error("Erreur au niveau de l'agence <<" +creationDemandeChequeForm.getCodStrcStrc() + ">>. Exception dans souscriptionContratCompteAction / Methode : affecterDonneesDemandeChequeMandatpersonne:  ",e);  
               throw new RuntimeException(e);               
        }    
        
        return demandeChequeMandatPersonne;
    }

    public boolean verifierInterdictionChequier(Personne demandeur) {
        VerifierInterditChequierCmd  verifierInterditChequierCmd = new VerifierInterditChequierCmd();
        PersonneStrc personneStrc = new PersonneStrc();
        personneStrc.setCodTpceTpce(demandeur.getTypePiece().getCodTpceTpce());
        personneStrc.setNumPcePers(demandeur.getNumPcePers());
        
        PrimitiveVO pr =  (PrimitiveVO)verifierInterditChequierCmd.execute(personneStrc);
        return pr.isVBool();
    }

    public DemandeCheque verifierDemandeEnCoursMandatairesSigConjointe(ParamDemandeCheque paramDemandeCheque, 
                                                                       List listMandatairescoches, 
                                                                       ActionForm form) {
    
        CreationDemandeChequeForm creationDemandeChequeForm = 
            (CreationDemandeChequeForm)form;
        Context context = ContextHandler.getContext();
        boolean trouve = false;
        DetailDemandeCheque detailDemandeCheque = new DetailDemandeCheque();
      try{  
        DemandeCheque demandeCheque = new DemandeCheque();
        GetDetailDemandeChequeCmd getDetailDemandeChequeCmd = 
            new GetDetailDemandeChequeCmd();
       
        ListesDemandesCheques listesDemandesCheques = 
            new ListesDemandesCheques();
        GetListDemandeursChequesMandatPersonneCmd getListDemandeursChequesMandatPersonneCmd = 
            new GetListDemandeursChequesMandatPersonneCmd();
       
        List demandeEncoursMP = new ArrayList();
        DemandeChequeDAO demandeChequeDAO = 
            (DemandeChequeDAO)context.getBean("demandeChequeDAO");
        demandeEncoursMP = demandeChequeDAO.getList(paramDemandeCheque);
        ListOrderedMap ListNumDem = null;
        for (Iterator it = demandeEncoursMP.iterator(); it.hasNext(); ) {
            ListNumDem = (ListOrderedMap)it.next();
            if ((ListNumDem.getValue(0)).toString() != null) {
                paramDemandeCheque.setNumDemande(ListNumDem.getValue(0).toString());
                detailDemandeCheque = 
                        (DetailDemandeCheque)getDetailDemandeChequeCmd.execute(paramDemandeCheque);
                listesDemandesCheques = 
                        (ListesDemandesCheques)getListDemandeursChequesMandatPersonneCmd.execute(paramDemandeCheque);
                if (listesDemandesCheques.getListeDemandeursChqMandatPersonne() != 
                    null && 
                    listesDemandesCheques.getListeDemandeursChqMandatPersonne().size() > 
                    0) {

                    // comparer les deux listes : liste des demandeurs et la liste des mandataires cochés :
                    if (listesDemandesCheques.getListeDemandeursChqMandatPersonne().size() != 
                        creationDemandeChequeForm.getListMandataireCoches().size()) {
                        trouve = false;
                    } else {
                        // comparer les contenues des deux liste un par un
                        trouve = true;
                        for (Iterator it1 = 
                             listesDemandesCheques.getListeDemandeursChqMandatPersonne().iterator(); 
                             it1.hasNext() && trouve == true; ) {
                            Personne pers = (Personne)it1.next();
                            for (Iterator it2 = 
                                 creationDemandeChequeForm.getListMandataireCoches().iterator(); 
                                 it2.hasNext(); ) {
                                String numSeqcoche = (String)it2.next();
                                if (pers.getNumSeqPers().equals(new Long(numSeqcoche))) {
                                    trouve = true;
                                    break;
                                } else
                                    trouve = false;
                            } //fin 3eme For
                        } //fin 2eme For
                    }
                }
            }
            if (trouve)
                break;

        } // fin premiere For              
      } catch (Exception e) {
                logger.error("Erreur au niveau de l'agence <<" +creationDemandeChequeForm.getCodStrcStrc() + ">>. Exception dans souscriptionContratCompteAction / Methode : verifierDemandeEnCoursMandatairesSigConjointe:  ",e);  
                throw new RuntimeException(e);               
         }    
         
        if (trouve == true) {
            // demande en cours 
            return detailDemandeCheque.getDemandeCheque();
        } else
            return null;

    }

    public ActionForward validerDemande(ActionMapping mapping, ActionForm form, 
                                        HttpServletRequest request, 
                                        HttpServletResponse response) throws IOException, 
                                                                             ServletException {

        CreationDemandeChequeForm creationDemandeChequeForm = 
            (CreationDemandeChequeForm)form;
        ActionMessages actionMessages = new ActionMessages();
        String forward = "";
        try {
            GetPersonneByNumSeqPersCmd getPersonneByNumSeqPersCmd = 
                new GetPersonneByNumSeqPersCmd();
            DemandeCheque demandeCheque = new DemandeCheque();
            DemandeCheque demandeChequeNew = new DemandeCheque();
            PersonneStrc personneStrc = new PersonneStrc();
            ParamDemandeCheque paramDemandeCheque = new ParamDemandeCheque();
            ContratPersonne contratPersonne = new ContratPersonne();
            contratPersonne.setContratCptId(creationDemandeChequeForm.getContratCpt().getContratCptId());
            paramDemandeCheque.setTypeConfection(creationDemandeChequeForm.getCodConfConf());

            boolean interdit = false;
            if (creationDemandeChequeForm.getTypeDemandeur().equals("T") || 
                creationDemandeChequeForm.getTypeDemandeur().equals("C")) {  
                creationDemandeChequeForm.setAlertDemandeur("");
                demandeChequeNew =  gererDemandeChequier(creationDemandeChequeForm,creationDemandeChequeForm.getContratCpt());
                
                 forward = "confirmationGenerale";
                
            } else {
                // cas du mandataire
                paramDemandeCheque.setNumMandMand(new Long(creationDemandeChequeForm.getNumeroMandatChoisi()));
                if (creationDemandeChequeForm.getSignatureMandatChoisi().equals("S")) {
                    // cas d'une mandat dont la signature est séparée   
                    // verification simple de l'interdiction et de la demande encours du demandeur...
                    personneStrc.setCodTpceTpce(new Long(creationDemandeChequeForm.getCodTpceTpceDemandeur()));
                    personneStrc.setNumPcePers(creationDemandeChequeForm.getNumPcePersDemandeur());
                    contratPersonne.setPersonneId(personneStrc);
                    paramDemandeCheque.setContratPersonne(contratPersonne);
                    if (!verifierInterdictionChequier(creationDemandeChequeForm.getDemandeur())) {
                        demandeCheque = 
                                verifierDemandeEnCours(paramDemandeCheque, 
                                                       creationDemandeChequeForm);
                        if (demandeCheque.getNumDemDchq() != null) {
                            // si demande en cours
                            //rejeter la demande pour motif demande encours
                            determinerAlertEncours(creationDemandeChequeForm, demandeCheque);
                            creationDemandeChequeForm.setEtatDemDchq(Constants.DEM_CHQ_REJETEE.toString());
                            creationDemandeChequeForm.setMotifRejet(Constants.MOTIF_DEM_EN_COURS.toString());
                            creationDemandeChequeForm.setAlertDemandeur("demandeEnCours");
                            //demandeChequeNew =  gererDemandeChequier(creationDemandeChequeForm,creationDemandeChequeForm.getContratCpt());
                            
                        }
                    } else {
                        // --rejeter la demande interdiction de chequier
                        creationDemandeChequeForm.setEtatDemDchq(Constants.DEM_CHQ_REJETEE.toString());
                        creationDemandeChequeForm.setMotifRejet(Constants.MOTIF_DEM_INTERDIT_CHQ.toString());                        
                        creationDemandeChequeForm.setMessageAlerte("Le demandeur du chequier  " + 
                                                                   creationDemandeChequeForm.getNomNomPersDemandeur() + 
                                                                   "  " + 
                                                                   creationDemandeChequeForm.getNomPrnPersDemandeur() + 
                                                                   " est interdit de chequier. ");
                        creationDemandeChequeForm.setAlertDemandeur("demandeurInterditChequier");
                        demandeChequeNew =  gererDemandeChequier(creationDemandeChequeForm,creationDemandeChequeForm.getContratCpt());
                    }
                } // fin du cas signature séparée
                else {
                    // cas d'un mandat dont la signature est conjointe

                    List listMandataireCoches = new ArrayList();
                    for (Iterator it = 
                         creationDemandeChequeForm.getListdesMandatsPersonneChoisi().iterator(); 
                         it.hasNext(); ) {
                        String numSeq = (String)it.next();
                        if (!numSeq.equals("")) {
                            listMandataireCoches.add(numSeq);
                            creationDemandeChequeForm.setListMandataireCoches(listMandataireCoches);
                        }
                    }

                    // test sur l'interdiction de cheuqier pour chaque membre mandataire : 
                    for (Iterator it = 
                         creationDemandeChequeForm.getListMandataireCoches().iterator(); 
                         it.hasNext() && interdit == false; ) {
                        String numSeq = (String)it.next();
                        Personne pers1 = new Personne();
                        pers1.setNumSeqPers(new Long(numSeq));
                        Personne pers = 
                            (Personne)getPersonneByNumSeqPersCmd.execute(pers1);
                        if (!pers.hasError()) {
                            if (verifierInterdictionChequier(pers)) {
                                // si un membre est interdit de chequier
                                creationDemandeChequeForm.setEtatDemDchq(Constants.DEM_CHQ_REJETEE.toString());
                                creationDemandeChequeForm.setMotifRejet(Constants.MOTIF_DEM_INTERDIT_CHQ.toString());                               

                                creationDemandeChequeForm.setMessageAlerte("Le mandataire  " + 
                                                                           pers.getNomNomPers() + 
                                                                           "  " + 
                                                                           pers.getNomPrnPers() + 
                                                                           " est interdit de chequier. ");
                                creationDemandeChequeForm.setAlertDemandeur("demandeurInterditChequier");
                                demandeChequeNew =  gererDemandeChequier(creationDemandeChequeForm,creationDemandeChequeForm.getContratCpt());
                                interdit = true;
                            }
                        } else {
                            List listErreur = pers.getErrors();
                            for (Iterator it1 = listErreur.iterator(); 
                                 it1.hasNext(); ) {
                                com.oxia.fwk.core.Error erreur = 
                                    (com.oxia.fwk.core.Error)it1.next();
                                ActionMessage actionMessage = 
                                    new ActionMessage("exception.generique", 
                                                      erreur.getDescription());
                                actionMessages.add("Erreur ", actionMessage);
                            }
                            this.saveMessages(request, actionMessages);
                            return mapping.findForward("error");
                        }
                    }

                    if (!interdit) {
                        // si aucun membre interdit de chequier alors verifier la demande en cours
                        paramDemandeCheque.setContratPersonne(contratPersonne);
                        paramDemandeCheque.setEtatDemande(new Long(20)); //   
                        demandeCheque = verifierDemandeEnCoursMandatairesSigConjointe(paramDemandeCheque, 
                                                                              creationDemandeChequeForm.getListMandataireCoches(), 
                                                                              creationDemandeChequeForm);
                        if (demandeCheque != null) {
                            // si demande en cours
                            //rejeter la demande pour motif demande encours
                            determinerAlertEncours(creationDemandeChequeForm, 
                                                   demandeCheque);
                            creationDemandeChequeForm.setEtatDemDchq(Constants.DEM_CHQ_REJETEE.toString());
                            creationDemandeChequeForm.setMotifRejet(Constants.MOTIF_DEM_EN_COURS.toString());
                            creationDemandeChequeForm.setAlertDemandeur("demandeEnCours");
                            //demandeChequeNew =  gererDemandeChequier(creationDemandeChequeForm,creationDemandeChequeForm.getContratCpt());
                            
                        }
                    }

                }
                if (!creationDemandeChequeForm.getAlertDemandeur().equals("demandeEnCours") && 
                    !creationDemandeChequeForm.getAlertDemandeur().equals("demandeurInterditChequier")) {                    
                    creationDemandeChequeForm.setAlertDemandeur("");
                    demandeChequeNew =  gererDemandeChequier(creationDemandeChequeForm,creationDemandeChequeForm.getContratCpt());
                    
                    forward = "confirmationGenerale";
                }else forward = "success";

            } // Fin else du cas mandataire
            
             if(demandeChequeNew.hasError()){             
               
                 List listErreur = demandeChequeNew.getErrors();
                 for (Iterator it1 = listErreur.iterator(); it1.hasNext(); ) {
                     com.oxia.fwk.core.Error erreur =(com.oxia.fwk.core.Error)it1.next();
                     ActionMessage actionMessage = new ActionMessage("exception.generique", erreur.getDescription());
                     actionMessages.add("Erreur ", actionMessage);
                  }
                     this.saveMessages(request, actionMessages);
                     return mapping.findForward("error");
               }else{
                   StringBuffer msg = 
                       new StringBuffer("L'opération de demande de chéquier N ° " + demandeChequeNew.getNumDemDchq() + " au nom du " + creationDemandeChequeForm.getDemandeur().getNomNomPers() + " "  + creationDemandeChequeForm.getDemandeur().getNomPrnPers() + 
                                         " a été crée avec succès et en attende de validation par le chef d'agence..." );   
                    ActionMessage actionMessage1 = new ActionMessage("exception.generique",msg.toString());
                    actionMessages.add("Msg_validation ", actionMessage1);
                    this.saveMessages(request, actionMessages);                    
                   
               }
                        
          
             return mapping.findForward(forward);
        
        } catch (Exception e) {
            com.oxia.fwk.core.Error erreur = new com.oxia.fwk.core.Error();
            StringBuffer text = 
                new StringBuffer("la transaction est Interrompu, une erreur dans CreationDemandeChequeAction / Dispatch Action :validerDemande ");
            text.append(e.toString());
            logger.error("Exception : ",e);
            erreur.setCode("200");
            erreur.setDescription(text.toString());
            ActionMessage actionMessage = 
                new ActionMessage("exception.generique", 
                                  erreur.getDescription());
            actionMessages.add("Erreur ", actionMessage);
            this.saveMessages(request, actionMessages);
            return mapping.findForward("error");
        }

    }
    
    
    public ActionForward rejeterDemandeEnCours(ActionMapping mapping, ActionForm form, 
                                        HttpServletRequest request, 
                                        HttpServletResponse response) throws IOException, 
                                                                             ServletException {

         CreationDemandeChequeForm creationDemandeChequeForm = (CreationDemandeChequeForm)form;            
         DemandeCheque demandeChequeNew = new DemandeCheque();
         creationDemandeChequeForm.setEtatDemDchq(Constants.DEM_CHQ_REJETEE.toString());                 
         demandeChequeNew =  gererDemandeChequier(creationDemandeChequeForm,creationDemandeChequeForm.getContratCpt());      
         return mapping.findForward("success");     
     } 
     
    public ActionForward ForcerDemandeCheque(ActionMapping mapping, ActionForm form, 
                                        HttpServletRequest request, 
                                        HttpServletResponse response) throws IOException, 
                                                                             ServletException {

         ActionMessages actionMessages = new ActionMessages();
         CreationDemandeChequeForm creationDemandeChequeForm = (CreationDemandeChequeForm)form;            
         DemandeCheque demandeChequeNew = new DemandeCheque();    
         creationDemandeChequeForm.setEtatDemDchq(Constants.DEM_CHQ_ATTENTE.toString());
         demandeChequeNew =  gererDemandeChequier(creationDemandeChequeForm,creationDemandeChequeForm.getContratCpt());      
         StringBuffer msg = 
             new StringBuffer("L'opération de demande de chéquier N ° " + demandeChequeNew.getNumDemDchq() + " au nom du   " + creationDemandeChequeForm.getDemandeur().getNomNomPers() + " "  + creationDemandeChequeForm.getDemandeur().getNomPrnPers() + 
                               " a été crée avec succès et en attende de validation par le chef d'agence..."    );   
          ActionMessage actionMessage = new ActionMessage("exception.generique",msg.toString());
          actionMessages.add("Msg_validation ", actionMessage);
          this.saveMessages(request, actionMessages);           
          return mapping.findForward("confirmationGenerale");     
     } 
     
    public ActionForward initierCmdChqCertif(ActionMapping mapping, ActionForm form, 
                                        HttpServletRequest request, 
                                        HttpServletResponse response) throws IOException, 
                                                                             ServletException {

 
         
         SessionUtil sessionUtil =new SessionUtil();
         //Suppression des anciens Bean de type Form de la session, SAUF "creationDemandeChequeForm"
         sessionUtil.removeSession(request,"creationDemandeChequeForm"); 
         
         CreationDemandeChequeForm creationDemandeChequeForm = 
             (CreationDemandeChequeForm)form;
         ActionMessages actionMessages = new ActionMessages();
         try {
             ParamAgence paramAgence = 
                 (ParamAgence)request.getSession().getAttribute("paramAgBNA"); /// structure de l'agent         

             creationDemandeChequeForm.setNumMatrUser(paramAgence.getNumMatrUser().toString());
             //----------------------------------------------------------------------------//
             // initialistaion du type chéquier
             
             creationDemandeChequeForm.clearFormChqCertif();
             creationDemandeChequeForm.setCodStrcStrc(paramAgence.getCodStrcStrc().toString());
             creationDemandeChequeForm.setCodPrdPrd('0' + Constants.COD_PRD_PRD_CPT_INTERNE.toString());
             creationDemandeChequeForm.setNumCcptCcpt(String.valueOf("000000"));
             creationDemandeChequeForm.setDateComptableDchq(paramAgence.getDateComptable());
             
             DateFormat myformat = new SimpleDateFormat("dd/MM/yyyy");
             String d = myformat.format(new Date());
             creationDemandeChequeForm.setDateActuelle(d);
             
             if (!creationDemandeChequeForm.getCodePage().equals("")) {
                 if (creationDemandeChequeForm.getCodePage().equals("21")){
                     creationDemandeChequeForm.setLibelleOperation("P.E.Charge Demande chèquier BNA Agence");
                     
                     creationDemandeChequeForm.setDateDemDchq(d);
                 }else if(creationDemandeChequeForm.getCodePage().equals("22")){
                     creationDemandeChequeForm.setLibelleOperation("réception chèquier BNA Agence");
                      
                 }
             }

             //----------------------------------------------------------------------------------//
             

             
         } catch (Exception e) {
             com.oxia.fwk.core.Error erreur = new com.oxia.fwk.core.Error();
             StringBuffer text = 
                 new StringBuffer("la transaction est Interrompu, une erreur dans CreationDemandeChequeAction / Dispatch Action :initierCmdChqCertif ");
             text.append(e.toString());
             erreur.setCode("200");
             erreur.setDescription(text.toString());
             logger.error("Exception : ",e);
             ActionMessage actionMessage = 
                 new ActionMessage("exception.generique", 
                                   erreur.getDescription());
             actionMessages.add("Erreur ", actionMessage);
             this.saveMessages(request, actionMessages);
             return mapping.findForward("error");
         }
         
        return mapping.findForward("cmdChqCertif");

    }
    
    
    public ActionForward rechercherCompteInterne(ActionMapping mapping, ActionForm form, 
                                        HttpServletRequest request, 
                                        HttpServletResponse response) throws IOException, 
                                                                             ServletException {

                
     
            ActionMessages actionMessages = new ActionMessages();
            Context context = ContextHandler.getContext();
            CreationDemandeChequeForm creationDemandeChequeForm = 
                (CreationDemandeChequeForm)form;
            creationDemandeChequeForm.setTestExistanceContrat("");   
            creationDemandeChequeForm.setAlertChqcertif("");
           
            try {

                GetDetailCptInterneCmd getDetailCptInterneCmd = 
                    new GetDetailCptInterneCmd();
                
                CompteInterneId compteInterneId = new CompteInterneId();
                

                compteInterneId.setCodStrcStrc(Long.valueOf(creationDemandeChequeForm.getCodStrcStrc()));
                compteInterneId.setCodPrdPrd(Long.valueOf(creationDemandeChequeForm.getCodPrdPrd()));
                compteInterneId.setNumCptiCpti(Long.valueOf(creationDemandeChequeForm.getNumCcptCcpt()));
               
               
                //------------- Recherche des donnée du compte interne 
                CompteInterne compteInterne = 
                    (CompteInterne)getDetailCptInterneCmd.execute(compteInterneId);
                //test si contrat existant
                if (!compteInterne.hasError()) {
                    if (compteInterne.getCompteInterneId() != null) {                       
                            //test si contrat valide
                            if (compteInterne.getCodEtatCpti().equals(Constants.COD_ETAT_CPT_VALID)){ 
                                creationDemandeChequeForm.setTestExistanceContrat("ContratExistant");
                                creationDemandeChequeForm.setCompteInterne(compteInterne);
                                GetPersonnelCmd getPersonnelCmd = new GetPersonnelCmd();
                                Personnel personnel = new Personnel();
                                personnel.setNumMatrUser(creationDemandeChequeForm.getNumMatrUser());
                                personnel = (Personnel)getPersonnelCmd.execute(personnel);
                                if (!personnel.hasError()) {
                                    if(personnel!= null){
                                      creationDemandeChequeForm.setNumPcePers(personnel.getNumCinUser());     
                                    }
                                }
                                if (creationDemandeChequeForm.getCodePage().equals("22")){
                                   // cas de la réception chequier certifie
                                    GetListDemandesChequesCmd getListDemandesChequesCmd = 
                                        new GetListDemandesChequesCmd();
                                    ListesDemandesCheques listesDemandesCheques = 
                                        new ListesDemandesCheques();
                                    ParamDemandeCheque paramDemandeCheque = new ParamDemandeCheque();
                                    paramDemandeCheque.setCompteInterne(compteInterne);
                                    listesDemandesCheques = 
                                            (ListesDemandesCheques)getListDemandesChequesCmd.execute(paramDemandeCheque);
                                   
                                    if(listesDemandesCheques.getListeValidee().size()>0){
                                        DemandeCheque  demandeCheque = (DemandeCheque)listesDemandesCheques.getListeValidee().get(0);
                                        if(demandeCheque != null){
                                            creationDemandeChequeForm.setDemandeCheque(demandeCheque);
                                            creationDemandeChequeForm.setAlertChqcertif("DemandeExistante");
                                            creationDemandeChequeForm.setNumDemDchq(demandeCheque.getNumDemDchq());
                                            creationDemandeChequeForm.setDateDemDchq(DateHandler.dateToStr(demandeCheque.getDatDemDchq()));
                                            creationDemandeChequeForm.setNumPcePers(demandeCheque.getNumPceDchq());
                                            rechercherDemandeurCompteInterne( mapping, form,request, response);
                                        }                                        
                                    }else{
                                       creationDemandeChequeForm.setAlertChqcertif("PasDeDemande");
                                    }
                                    
                                }
                            }
                               
                    } else {
                        //------------- Contrat Inexistant
                        creationDemandeChequeForm.setTestExistanceContrat("contratInexistant");

                    }
                } else {
                    List listErreur = compteInterne.getErrors();
                    //ActionMessages actionMessages = new ActionMessages();
                    for (Iterator it = listErreur.iterator(); it.hasNext(); ) {
                        com.oxia.fwk.core.Error erreur = 
                            (com.oxia.fwk.core.Error)it.next();
                        ActionMessage actionMessage = 
                            new ActionMessage("exception.generique", 
                                              erreur.getDescription());
                        actionMessages.add("Erreur ", actionMessage);
                    }
                    this.saveMessages(request, actionMessages);
                    return mapping.findForward("error");
                }
                
                
                return mapping.findForward("cmdChqCertif");

            } catch (Exception e) {
                com.oxia.fwk.core.Error erreur = new com.oxia.fwk.core.Error();
                StringBuffer text = 
                    new StringBuffer("la transaction est Interrompu, une erreur dans CreationDemandeChequeAction / Dispatch Action :rechercherCompteInterne ");
                text.append(e.toString());
                erreur.setCode("200");
                erreur.setDescription(text.toString());
                logger.error("Exception : ",e);
                ActionMessage actionMessage = 
                    new ActionMessage("exception.generique", 
                                      erreur.getDescription());
                actionMessages.add("Erreur ", actionMessage);
                this.saveMessages(request, actionMessages);
                return mapping.findForward("error");
            }
        }
    
    
    public ActionForward rechercherDemandeurCompteInterne(ActionMapping mapping, ActionForm form, 
                                        HttpServletRequest request, 
                                        HttpServletResponse response) throws IOException, 
                                                                             ServletException {

                
     
            ActionMessages actionMessages = new ActionMessages();
            Context context = ContextHandler.getContext();
            CreationDemandeChequeForm creationDemandeChequeForm = 
                (CreationDemandeChequeForm)form;
           Personne personne = new Personne();
           GetPersonneCmd getPersonneCmd = new GetPersonneCmd();
           creationDemandeChequeForm.setMessageTexte("");
         try {
            PersonneStrc personneStrc = new PersonneStrc();
            personneStrc.setCodTpceTpce(Long.valueOf(creationDemandeChequeForm.getCodTpceTpce()));
            personneStrc.setNumPcePers(creationDemandeChequeForm.getNumPcePers());   
            personne = (Personne)getPersonneCmd.execute(personneStrc);
    
            if (!personne.hasError()) {
                if (personne.getNumSeqPers() != null) {    
                    creationDemandeChequeForm.setMessageTexte("demandeurValide");
                    creationDemandeChequeForm.setNomNomPers(personne.getNomNomPers());
                    creationDemandeChequeForm.setNomPrnPers(personne.getNomPrnPers());
                    
                }else  creationDemandeChequeForm.setMessageTexte("demandeurInValide");  
            }             

            } catch (Exception e) {
                com.oxia.fwk.core.Error erreur = new com.oxia.fwk.core.Error();
                StringBuffer text = 
                    new StringBuffer("la transaction est Interrompu, une erreur dans CreationDemandeChequeAction / Dispatch Action :rechercherDemandeurCompteInterne ");
                text.append(e.toString());                
                erreur.setDescription(text.toString());
                logger.error("Exception : ",e);
                ActionMessage actionMessage = 
                    new ActionMessage("exception.generique", 
                                      erreur.getDescription());
                actionMessages.add("Erreur ", actionMessage);
                this.saveMessages(request, actionMessages);
                return mapping.findForward("error");
            }
            
      return mapping.findForward("cmdChqCertif");
  }
      
      
    public ActionForward validerDdeChqCompteInterne(ActionMapping mapping, ActionForm form, 
                                        HttpServletRequest request, 
                                        HttpServletResponse response) throws IOException, 
                                                                             ServletException {

                
     
            ActionMessages actionMessages = new ActionMessages();
            Context context = ContextHandler.getContext();
            CreationDemandeChequeForm creationDemandeChequeForm = 
                (CreationDemandeChequeForm)form;
           DemandeChequeDAO demandeChequeDAO = 
            (DemandeChequeDAO)context.getBean("demandeChequeDAO");
           DemandeCheque demandeCheque = new DemandeCheque();
          
         try {
            Long trouve = demandeChequeDAO.verifierDdeChqcertifieEnCours(Long.valueOf(creationDemandeChequeForm.getCodPrdPrd()),Long.valueOf(creationDemandeChequeForm.getCodStrcStrc()),Long.valueOf(creationDemandeChequeForm.getNumCcptCcpt()));
            if(trouve > 0){
                creationDemandeChequeForm.setAlertDemandeur("demandeEnCours");               
                
            }else{
                Tache tache = new Tache();
                TacheId tacheId = new TacheId();        
                tacheId.setCodOperOper(Constants.OPER_VALIDATION_DEM_CHQ);
                tacheId.setCodTachTach(Constants.TACHE_VALIDATION_DEM_CHQ_STND);
                tache.setTacheId(tacheId);
                TypeConfection typeConfection = new TypeConfection();
                typeConfection.setCodConfConf("S");
                Personnel personnel = new Personnel();
                personnel.setNumMatrUser(creationDemandeChequeForm.getNumMatrUser());


                //affectation de la demande
                demandeCheque.setCompteInterne(creationDemandeChequeForm.getCompteInterne());
                demandeCheque.setTache(tache);
                demandeCheque.setTypeConfection(typeConfection);
                demandeCheque.setPersonnel(personnel);
                demandeCheque.setDatDemDchq(DateHandler.strToDate(creationDemandeChequeForm.getDateDemDchq()));
                demandeCheque.setNbrChqDchq(Long.valueOf(25));
                demandeCheque.setNbrChqiDchq(Long.valueOf(1));
                demandeCheque.setCodEtatDchq(Constants.DEM_CHQ_VALIDEE);
                demandeCheque.setNumPceDchq(creationDemandeChequeForm.getNumPcePers());
                demandeCheque.setCodTpceDchq(Long.valueOf(creationDemandeChequeForm.getCodTpceTpce()));                
                demandeCheque.setCodDemDchq("T");
                demandeCheque.setDatEnvDchq(DateHandler.strToDate(creationDemandeChequeForm.getDateDemDchq()));
                demandeCheque.setDatCompDchq(DateHandler.strToDate(creationDemandeChequeForm.getDateComptableDchq())); 
                InsertDemandeChequeCmd insertDemandeChequeCmd = new InsertDemandeChequeCmd();
                demandeCheque = (DemandeCheque)insertDemandeChequeCmd.execute(demandeCheque);
                
                if(demandeCheque.hasError()){             
                  
                    List listErreur = demandeCheque.getErrors();
                    for (Iterator it1 = listErreur.iterator(); it1.hasNext(); ) {
                        com.oxia.fwk.core.Error erreur =(com.oxia.fwk.core.Error)it1.next();
                        ActionMessage actionMessage = new ActionMessage("exception.generique", erreur.getDescription());
                        actionMessages.add("Erreur ", actionMessage);
                     }
                        this.saveMessages(request, actionMessages);
                        return mapping.findForward("error");
                  }else{
                      StringBuffer msg = 
                       new StringBuffer("L'opération de demande d'un chéquier BNA Agence (25) N ° " + demandeCheque.getNumDemDchq() + " au profil de l'agence  " + creationDemandeChequeForm.getCodStrcStrc() +  
                                            " est effectuée avec succes..." );   
                       ActionMessage actionMessage1 = new ActionMessage("exception.generique",msg.toString());
                       actionMessages.add("Msg_validation ", actionMessage1);
                       this.saveMessages(request, actionMessages);                    
                      
                  }
                
                
            }
            

            } catch (Exception e) {
                com.oxia.fwk.core.Error erreur = new com.oxia.fwk.core.Error();
                StringBuffer text = 
                    new StringBuffer("la transaction est Interrompu, une erreur dans CreationDemandeChequeAction / Dispatch Action :validerDdeChqCompteInterne ");
                text.append(e.toString());                
                erreur.setDescription(text.toString());
                logger.error("Exception : ",e);
                ActionMessage actionMessage = 
                    new ActionMessage("exception.generique", 
                                      erreur.getDescription());
                actionMessages.add("Erreur ", actionMessage);
                this.saveMessages(request, actionMessages);
                return mapping.findForward("error");
            }
            
      return mapping.findForward("cmdChqCertif");
    }   
    
    public ActionForward receptionChqCompteInterne(ActionMapping mapping, ActionForm form, 
                                        HttpServletRequest request, 
                                        HttpServletResponse response) throws IOException, 
                                                                             ServletException {

       ActionMessages actionMessages = new ActionMessages();
       Context context = ContextHandler.getContext();
       CreationDemandeChequeForm creationDemandeChequeForm = 
           (CreationDemandeChequeForm)form;
        Chequier chequier = new Chequier();
        ChequierId chequierId = new ChequierId();
        ReceptionChequiersCmd receptionChequiersCmd = new ReceptionChequiersCmd();
       
       try {
          
           DemandeCheque demChq =  new DemandeCheque();
           demChq = creationDemandeChequeForm.getDemandeCheque();
           chequierId.setNumChqiChqi(Long.valueOf(1));
           chequierId.setNumDemDchq(creationDemandeChequeForm.getNumDemDchq());
           chequier.setChequierId(chequierId);
           chequier.setDemandeCheque(demChq);
           chequier.setCompteInterne(creationDemandeChequeForm.getCompteInterne());
           chequier.setTypeConfection(demChq.getTypeConfection());
           chequier.setCodEtatChqi(Long.valueOf(4));
           chequier.setNumDebChqi(Long.valueOf(creationDemandeChequeForm.getNumDebChqCertif()));
           chequier.setNbrChqChqi(Long.valueOf(25));
           chequier.setDatRecpChqi(DateHandler.strToDate(creationDemandeChequeForm.getDateActuelle()));
           chequier.setCodEtatChqi(Constants.ETAT_CHQ_RECU);
           
           demChq.getChequiers().add(chequier);
           demChq.setCodEtatDchq(Constants.DEM_CHQ_TOT_SATISFAITE);
           DemandeCheque demandeCheque = (DemandeCheque)receptionChequiersCmd.execute(demChq);
           
           if(demandeCheque.hasError()){             
             
               List listErreur = demandeCheque.getErrors();
               for (Iterator it1 = listErreur.iterator(); it1.hasNext(); ) {
                   com.oxia.fwk.core.Error erreur =(com.oxia.fwk.core.Error)it1.next();
                   ActionMessage actionMessage = new ActionMessage("exception.generique", erreur.getDescription());
                   actionMessages.add("Erreur ", actionMessage);
                }
                   this.saveMessages(request, actionMessages);
                   return mapping.findForward("error");
             }else{
                 StringBuffer msg = 
                  new StringBuffer("L'opération de récéption du carnet de chèques certifiés au profil de l'agence  " + creationDemandeChequeForm.getCodStrcStrc() +  
                                       " est effectuée avec succes..." );   
                  ActionMessage actionMessage1 = new ActionMessage("exception.generique",msg.toString());
                  actionMessages.add("Msg_validation ", actionMessage1);
                  this.saveMessages(request, actionMessages);                    
                 
             }

       } catch (Exception e) {
           com.oxia.fwk.core.Error erreur = new com.oxia.fwk.core.Error();
           StringBuffer text = 
               new StringBuffer("la transaction est Interrompu, une erreur dans CreationDemandeChequeAction / Dispatch Action :receptionChqCompteInterne ");
           text.append(e.toString());                
           erreur.setDescription(text.toString());
           logger.error("Exception : ",e);
           ActionMessage actionMessage = 
               new ActionMessage("exception.generique", 
                                 erreur.getDescription());
           actionMessages.add("Erreur ", actionMessage);
           this.saveMessages(request, actionMessages);
           return mapping.findForward("error");
       }
       
       return mapping.findForward("cmdChqCertif");

   }
      
}