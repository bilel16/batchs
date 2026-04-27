
package com.bna.smile.web.placement.actions;

import com.bna.commun.model.ContratCpt;
import com.bna.commun.model.ContratCptId;
import com.bna.commun.model.ContratPlacement;
import com.bna.commun.model.DemandeDecision;
import com.bna.commun.model.Personne;
import com.bna.commun.model.ProduitPlacement;
import com.bna.commun.model.Structure;
import com.bna.commun.model.StructureDomaine;
import com.bna.commun.model.TypePiece;
import com.bna.commun.util.CalanderHandler;
import com.bna.commun.util.ContextHandler;
import com.bna.commun.util.DateHandler;
import com.bna.commun.util.SmileUtil;
import com.bna.commun.util.StrHandler;
import com.bna.smile.model.constant.Constants;
import com.bna.smile.model.domainecommun.commande.GetDetailContratCmd;
import com.bna.smile.model.domainecommun.commande.GetListContratCmd;
import com.bna.smile.model.domainecommun.commande.GetPersonneByNumSeqPersCmd;
import com.bna.smile.model.domainecommun.commande.GetPersonneCptCmd;
import com.bna.smile.model.domainecommun.model.ContratPersonne;
import com.bna.smile.model.domainecommun.model.Listes;
import com.bna.smile.model.domainecommun.model.PersonneCpt;
import com.bna.smile.model.domainecommun.model.PersonneRechercheContratVo;
import com.bna.smile.model.domainecommun.model.PersonneStrc;
import com.bna.smile.model.domaineplacement.commande.GetContratPlacementCmd;
import com.bna.smile.model.domaineplacement.commande.GetDemandeDecisionCmd;
import com.bna.smile.model.domaineplacement.commande.GetListContratsPlacementCmd;
import com.bna.smile.model.domaineplacement.commande.GetListDemandesDecisionCmd;
import com.bna.smile.model.domaineplacement.commande.UpdateContratPlacementCmd;
import com.bna.smile.model.domaineplacement.commande.ValiderDdeDecisionCmd;
import com.bna.smile.model.domaineplacement.commande.ValiderMajDdeDecisionCmd;
import com.bna.smile.model.domaineplacement.dao.PlacementDAO;
import com.bna.smile.model.domaineplacement.model.ParamDemandeDecision;
import com.bna.smile.model.reporting.model.CommonReportVO;
import com.bna.smile.web.commun.model.ParamAgence;
import com.bna.smile.web.commun.model.PersonneDemandeur;
import com.bna.smile.web.commun.model.Pouvoir;
import com.bna.smile.web.commun.view.ContratView;
import com.bna.smile.web.placement.forms.SouscriptionContratPlacementForm;
import com.bna.smile.web.placement.view.ContratPlacementView;
import com.bna.smile.web.placement.view.DemandeDecisionView;
import com.bna.smile.web.procuration.util.ContratCptView;

import com.oxia.fwk.context.Context;

import java.io.File;
import java.io.IOException;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.actions.DispatchAction;


public class SouscriptionContratPlacementAction extends DispatchAction {

    /**
     * <B> Action de la page  souscriptionContratCompte.jsp  </B>
     * @param mapping The ActionMapping used to select this instance.
     * @param form The optional ActionForm bean for this request.
     * @param request The HTTP Request we are processing.
     * @param response The HTTP Response we are processing.
     * Nom du package : com.bna.smile.web.souscription.actions
     *
     * @version le 19/01/2007
     * @modify le 06/07/07
     */
    private static final

    Logger logger = Logger.getLogger(SouscriptionContratPlacementAction.class);

    
    /**
     * Action qui renvoi vers la page JSP: /pageInitialePlacement.jsp
     */
    public

    ActionForward initierPage(ActionMapping mapping, ActionForm form, 
                              HttpServletRequest request, 
                              HttpServletResponse response) throws IOException, 
                                                                   ServletException {

        ActionMessages actionMessages = new ActionMessages();
        SouscriptionContratPlacementForm souscriptionContratPlacementForm = 
            (SouscriptionContratPlacementForm)form;
        try {       
           
            String forward = "";
            if (souscriptionContratPlacementForm.getTypeMenu().equals("demande"))
                forward = "initDemandePlacement";
            else if (souscriptionContratPlacementForm.getTypeMenu().equals("souscription"))
                forward = "initSouscriptionPlacement";
            else if (souscriptionContratPlacementForm.getTypeMenu().equals("avance"))
                forward = "initAvancePlacement";
            else if (souscriptionContratPlacementForm.getTypeMenu().equals("liquidation"))
                forward = "initLiquidationPlacement";
            else if (souscriptionContratPlacementForm.getTypeMenu().equals("renouvellement"))
                forward = "initRenouvelPlacement";
            else if (souscriptionContratPlacementForm.getTypeMenu().equals("consultation"))
                forward = "initConsultPlacement";
            else if (souscriptionContratPlacementForm.getTypeMenu().equals("autreOper"))
                forward = "initAutreOperPlacement";


            return mapping.findForward(forward);
        } catch (Exception e) {
            com.oxia.fwk.core.Error erreur = new com.oxia.fwk.core.Error();
            StringBuffer text = 
                new StringBuffer("L'initialisation du domaine Placement a été interrompue, Veuillez transmettre ce message à l'équipe informatique: ");
            text.append("Exception au niveau de l'agence:");
            text.append(souscriptionContratPlacementForm.getInitialisationView().getCodeAgence());
            text.append(". Exception :");
            text.append(e.toString());
            erreur.setCode("200");
            erreur.setDescription(text.toString());
            logger.error(text.toString(), e);
            ActionMessage actionMessage = 
                new ActionMessage("exception.generique", 
                                  erreur.getDescription());
            actionMessages.add("Erreur ", actionMessage);
            this.saveMessages(request, actionMessages);
            return mapping.findForward("error");
        }

    }

    /**
     * Action qui renvoi vers la page JSP: /demandeDecisionPlacement.jsp si typeForm= Creation ou MAJ
     *            renvoi vers la page JSP: /listeDdeDecisionPlacement.jsp si typeForm= Recherche
     */
    public

    ActionForward initierDemandeDecision(ActionMapping mapping, 
                                         ActionForm form, 
                                         HttpServletRequest request, 
                                         HttpServletResponse response) throws IOException, 
                                                                              ServletException {

        Context context = ContextHandler.getContext();
        ActionMessages actionMessages = new ActionMessages();
        SouscriptionContratPlacementForm souscriptionContratPlacementForm = 
            (SouscriptionContratPlacementForm)form;
        StringBuffer text = 
            new StringBuffer("L'initialisation de la demande de placement a été interrompue, veuillez transmettre ce message à l'équipe informatique: ");
        Long nbrAlert = Long.valueOf("0");
        try {
            String forward = "";
            ParamAgence paramAgence = 
                (ParamAgence)request.getSession().getAttribute("paramAgBNA");
            souscriptionContratPlacementForm.clearFormDemandeDecision();
            
            //verification de l'habilitation sur cette operation
            StructureDomaine structureDomaine = new StructureDomaine(paramAgence.getCodStrcStrc(),Constants.COD_DOM_PLACEMENT);
            boolean bool = SmileUtil.testDomaineOuvert(structureDomaine);
            
            if (paramAgence != null) {
                souscriptionContratPlacementForm.getInitialisationView().setDateActuelle(null);
                souscriptionContratPlacementForm.getInitialisationView().setDateOp(DateHandler.strToDate(paramAgence.getDateJours()));
                souscriptionContratPlacementForm.getInitialisationView().setDateActuelle(paramAgence.getDateComptable());
                souscriptionContratPlacementForm.getDemandeDecisionView().setDateValeur(paramAgence.getDateComptable());
                souscriptionContratPlacementForm.getInitialisationView().setCodeAgence(paramAgence.getCodStrcStrc().toString());
                souscriptionContratPlacementForm.getInitialisationView().setCodeOperation(Constants.OPER_INT_PRE_SOUSC_PLAC.toString());

            } else {
                text.append("Les paramètres Agence sont vides (paramAgence). Veuillez vous re-connecter à SMILE. ");
            }

            souscriptionContratPlacementForm.getDemandeDecisionView().setDatCreDemd(paramAgence.getDateJours());
            PlacementDAO plcDao = 
                (PlacementDAO)context.getBean("placementDAO");


            if (souscriptionContratPlacementForm.getTypeForm().equals("Creation")) {
                forward = "demandeDecisionPlacement";
                souscriptionContratPlacementForm.getContratView().setCodStrcStrc(StrHandler.lpad(paramAgence.getCodStrcStrc().toString(),'0',3));
                souscriptionContratPlacementForm.setChoixRecherche("0");

              //  souscriptionContratPlacementForm.getDemandeDecisionView().setDateLimite(DateHandler.dateToStr(CalanderHandler.GetNextWorkingDay(DateHandler.strToDate(souscriptionContratPlacementForm.getInitialisationView().getDateActuelle()))));
                souscriptionContratPlacementForm.getDemandeDecisionView().setDateLimite(DateHandler.dateToStr(CalanderHandler.GetNextWorkingDayAfterNdays(DateHandler.strToDate(souscriptionContratPlacementForm.getInitialisationView().getDateActuelle()),1)));

                souscriptionContratPlacementForm.setLibelleOperation("DEMANDE DE PLACEMENT");
                if(souscriptionContratPlacementForm.getCodSbdvDemd().equals("1")){
                    // demande sous bonne date valeur
                    souscriptionContratPlacementForm.setLibelleOperation("DEMANDE DE PLACEMENT SOUS BONNE DATE VALEUR");
                    forward = "demandeDecisionPlacementSBDV";
                }
                
                nbrAlert = 
                        plcDao.getNbreDemandeAlerte(Constants.ETAT_DEM_DECIS_SAISIE, 
                                                    paramAgence.getCodStrcStrc().toString());
                if (nbrAlert > 0) {
                    souscriptionContratPlacementForm.setNbrDemandeAttAlertes(nbrAlert.toString());
                }
                souscriptionContratPlacementForm.setNbrDemandeAlertes("0");

            } else if (souscriptionContratPlacementForm.getTypeForm().equals("Validation")) {
                souscriptionContratPlacementForm.clearFormRechercheDemandeDecision();
                forward = "listeDemandesDecision";
                souscriptionContratPlacementForm.setChoix("2");
                souscriptionContratPlacementForm.setDateFinRecherch(paramAgence.getDateJours());
                souscriptionContratPlacementForm.setStructureRech(paramAgence.getCodStrcStrc().toString());
                souscriptionContratPlacementForm.setLibelleOperation("VALIDATION SAISIE DEMANDE DE PLACEMENT");
                nbrAlert = 
                        plcDao.getNbreDemandeAlerte(Constants.ETAT_DEM_DECIS_SAISIE, 
                                                    souscriptionContratPlacementForm.getStructureRech());
                if (nbrAlert > 0) {
                    souscriptionContratPlacementForm.setNbrDemandeAlertes(nbrAlert.toString());
                    souscriptionContratPlacementForm.setEtatDemandeAlertes("Saisie(s)");
                }
                souscriptionContratPlacementForm.setNbrDemandeAttAlertes("0");
            } else if (souscriptionContratPlacementForm.getTypeForm().equals("etude")) {
                souscriptionContratPlacementForm.clearFormRechercheDemandeDecision();
                forward = "listeDemandesDecision";
                souscriptionContratPlacementForm.setChoix("2");
                souscriptionContratPlacementForm.setLibelleOperation("ETUDE DEMANDE DE PLACEMENT (DIR.TRESORERIE)");
                nbrAlert = 
                        plcDao.getNbreDemandeAlerte(Constants.ETAT_DEM_DECIS_SAISI_VALIDEE, 
                                                    souscriptionContratPlacementForm.getStructureRech());
                if (nbrAlert > 0) {
                    souscriptionContratPlacementForm.setNbrDemandeAlertes(nbrAlert.toString());
                    souscriptionContratPlacementForm.setEtatDemandeAlertes("Validée(s)");
                }
                nbrAlert = 
                        plcDao.getNbreDemandeAlerte(Constants.ETAT_DEM_DECIS_ETUDEE, 
                                                    souscriptionContratPlacementForm.getStructureRech());
                if (nbrAlert > 0) {
                    souscriptionContratPlacementForm.setNbrDemandeAttAlertes(nbrAlert.toString());
                }

            } else if (souscriptionContratPlacementForm.getTypeForm().equals("validationEtude")) {
                souscriptionContratPlacementForm.clearFormRechercheDemandeDecision();
                forward = "listeDemandesDecision";
                souscriptionContratPlacementForm.setChoix("2");
                souscriptionContratPlacementForm.setLibelleOperation("VALIDATION ETUDE DEMANDE DE PLACEMENT (DIR.TRESORERIE)");
                nbrAlert = 
                        plcDao.getNbreDemandeAlerte(Constants.ETAT_DEM_DECIS_ETUDEE, 
                                                    souscriptionContratPlacementForm.getStructureRech());
                if (nbrAlert > 0) {
                    souscriptionContratPlacementForm.setNbrDemandeAlertes(nbrAlert.toString());
                    souscriptionContratPlacementForm.setEtatDemandeAlertes("Etudiée(s)");
                }
                souscriptionContratPlacementForm.setNbrDemandeAttAlertes("0");
            } else if (souscriptionContratPlacementForm.getTypeForm().equals("RecepNotif")) {
                souscriptionContratPlacementForm.clearFormRechercheDemandeDecision();
                forward = "listeDemandesDecision";
                souscriptionContratPlacementForm.setChoix("2");
                souscriptionContratPlacementForm.setLibelleOperation("RECEPTION DEMANDE DE PLACEMENT NOTIFIEE (DIR.TRESORERIE)");
                nbrAlert = 
                        plcDao.getNbreDemandeAlerte(Constants.ETAT_DEM_DECIS_NOTIFIE, 
                                                    souscriptionContratPlacementForm.getStructureRech());
                if (nbrAlert > 0) {
                    souscriptionContratPlacementForm.setNbrDemandeAlertes(nbrAlert.toString());
                    souscriptionContratPlacementForm.setEtatDemandeAlertes("Accpeté(s)");
                }
                souscriptionContratPlacementForm.setNbrDemandeAttAlertes("0");

            } else if (souscriptionContratPlacementForm.getTypeForm().equals("notification")) {
                souscriptionContratPlacementForm.clearFormRechercheDemandeDecision();
                forward = "listeDemandesDecision";
                souscriptionContratPlacementForm.setChoix("2");
                souscriptionContratPlacementForm.setStructureRech(paramAgence.getCodStrcStrc().toString());
                souscriptionContratPlacementForm.setLibelleOperation("NOTIFICATION CLIENT ");
                nbrAlert = 
                        plcDao.getNbreDemandeAlerte(Constants.ETAT_DEM_DECIS_ETUDE_VALIDEE, 
                                                    souscriptionContratPlacementForm.getStructureRech());
                if (nbrAlert > 0) {
                    souscriptionContratPlacementForm.setNbrDemandeAlertes(nbrAlert.toString());
                    souscriptionContratPlacementForm.setEtatDemandeAlertes("Etudiée(s) par la trésorerie");
                }
                souscriptionContratPlacementForm.setNbrDemandeAttAlertes("0");
            }

            return mapping.findForward(forward);
        } catch (Exception e) {
            com.oxia.fwk.core.Error erreur = new com.oxia.fwk.core.Error();

            text.append("Exception au niveau de l'agence:");
            text.append(souscriptionContratPlacementForm.getInitialisationView().getCodeAgence());
            text.append(". Exception :");
            text.append(e.toString());
            erreur.setCode("200");
            erreur.setDescription(text.toString());
            logger.error(text.toString(), e);
            logger.error(text.toString(), e);
            ActionMessage actionMessage = 
                new ActionMessage("exception.generique", 
                                  erreur.getDescription());
            actionMessages.add("Erreur ", actionMessage);
            this.saveMessages(request, actionMessages);
            return mapping.findForward("error");
        }

    }


    /**
     * Action qui renvoi vers la page JSP: /pageInitialePlacement.jsp aprés avoir inserer la demande dans la base de données
     */
    public

    ActionForward ValiderMajDdeDecision(ActionMapping mapping, ActionForm form, 
                                        HttpServletRequest request, 
                                        HttpServletResponse response) throws IOException, 
                                                                             ServletException {
        ActionMessages actionMessages = new ActionMessages();
        SouscriptionContratPlacementForm souscriptionContratPlacementForm = 
            (SouscriptionContratPlacementForm)form;

        try {

            ValiderMajDdeDecisionCmd validerMajDdeDecisionCmd = 
                new ValiderMajDdeDecisionCmd();

            DemandeDecision demandeDecision = new DemandeDecision();

            demandeDecision = 
                    majDonneesDemandeDecision(souscriptionContratPlacementForm);
            demandeDecision = 
                    (DemandeDecision)validerMajDdeDecisionCmd.execute(demandeDecision);
            if (!demandeDecision.hasError()) {

                return mapping.findForward("initPlacement");
            } else {
                List listErreur = demandeDecision.getErrors();
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

        } catch (Exception e) {
            com.oxia.fwk.core.Error erreur = new com.oxia.fwk.core.Error();
            StringBuffer text = 
                new StringBuffer("La validation de la réponse de la D.T à la demande de placement a été interrompue, veuillez transmettre ce message à l'équipe informatique:");
            text.append("Exception au niveau de l'agence:");
            text.append(souscriptionContratPlacementForm.getInitialisationView().getCodeAgence());
            text.append(". Exception :");
            text.append(e.toString());
            erreur.setCode("200");
            erreur.setDescription(text.toString());
            logger.error(text.toString(), e);
            ActionMessage actionMessage = 
                new ActionMessage("exception.generique", 
                                  erreur.getDescription());
            actionMessages.add("Erreur ", actionMessage);
            this.saveMessages(request, actionMessages);
            return mapping.findForward("error");
        }
    }

    
    /**
     * Action qui renvoi vers la page JSP: /demandeDecisionPlacement.jsp aprés avoir vérifier l'existance du contrat Placement dans la base de données
     * appelé en cas de demande de Cession/Renouvellement d'un placement
     */
    public

    ActionForward rechercherCptPlacement(ActionMapping mapping, 
                                         ActionForm form, 
                                         HttpServletRequest request, 
                                         HttpServletResponse response) throws IOException, 
                                                                              ServletException {

        ActionMessages actionMessages = new ActionMessages();
        SouscriptionContratPlacementForm souscriptionContratPlacementForm = 
            (SouscriptionContratPlacementForm)form;
        try {

            ContratPlacement contratPlacement = new ContratPlacement();
            ContratPlacement contratPlacementNew = new ContratPlacement();
            contratPlacement.setNumSeqCpla(Long.valueOf(souscriptionContratPlacementForm.getDemandeDecisionView().getNumSeqCpla()));
            GetContratPlacementCmd getContratPlacementCmd = 
                new GetContratPlacementCmd();
            contratPlacementNew = 
                    (ContratPlacement)getContratPlacementCmd.execute(contratPlacement);

            if (contratPlacementNew != null && 
                contratPlacementNew.getNumSeqCpla() != null) {

                souscriptionContratPlacementForm.getDemandeDecisionView().setCodPrdPrd(contratPlacementNew.getProduitPlacement().getProduit().getCodPrdPrd().toString());
                souscriptionContratPlacementForm.getDemandeDecisionView().setMontPlaDemd(contratPlacementNew.getMontCapCpla().toString());
                souscriptionContratPlacementForm.getDemandeDecisionView().setDateEchAncienContrat(DateHandler.dateToStr(contratPlacementNew.getDatEcheCpla()));
                souscriptionContratPlacementForm.getDemandeDecisionView().setCodTypPaiement(contratPlacementNew.getCodPintCpla());
                souscriptionContratPlacementForm.setContratPlacement(contratPlacementNew);
                souscriptionContratPlacementForm.setAlertCptPlac("true");
            } else
                souscriptionContratPlacementForm.setAlertCptPlac("false");
            
            String forward ="";
            
            if(souscriptionContratPlacementForm.getCodSbdvDemd().equals("1")){
                // demande sous bonne date valeur
                forward = "demandeDecisionPlacementSBDV";
            }else forward = "demandeDecisionPlacement";
            
            return mapping.findForward(forward);
        } catch (Exception e) {
            com.oxia.fwk.core.Error erreur = new com.oxia.fwk.core.Error();
            StringBuffer text = 
                new StringBuffer("La rechercher du compte de placement a été interrompue, veuillez transmettre ce message à l'équipe informatique:");
            text.append("Exception au niveau de l'agence:");
            text.append(souscriptionContratPlacementForm.getInitialisationView().getCodeAgence());
            text.append(". Exception :");
            text.append(e.toString());
            erreur.setCode("200");
            erreur.setDescription(text.toString());
            logger.error(text.toString(), e);
            ActionMessage actionMessage = 
                new ActionMessage("exception.generique", 
                                  erreur.getDescription());
            actionMessages.add("Erreur ", actionMessage);
            this.saveMessages(request, actionMessages);
            return mapping.findForward("error");
        }

    }

    /**
     * Fonction qui retourne un objet demande decision aprés avoir rempli les données à mettre à jour
     * appelé par l'Action qui met à jour demande decision
     */
    public

    DemandeDecision majDonneesDemandeDecision(ActionForm form) {

        SouscriptionContratPlacementForm souscriptionContratPlacementForm = 
            (SouscriptionContratPlacementForm)form;

        DemandeDecision demandeDecision = new DemandeDecision();
        try {
            demandeDecision = 
                    souscriptionContratPlacementForm.getDemandeDecision();
            if ((!souscriptionContratPlacementForm.getDemandeDecisionView().getDemandeDecision().equals(null) && 
                 !souscriptionContratPlacementForm.getDemandeDecisionView().getNumRefdDemd().equals(""))) {
                if ((!souscriptionContratPlacementForm.getDemandeDecisionView().getNumSeqCpla().equals(null) && 
                     !souscriptionContratPlacementForm.getDemandeDecisionView().getNumSeqCpla().equals(""))) {
                    ContratPlacement contratPlacement = new ContratPlacement();
                    contratPlacement.setNumSeqCpla(Long.valueOf(souscriptionContratPlacementForm.getDemandeDecisionView().getNumSeqCpla()));
                    demandeDecision.setContratPlacement(contratPlacement);
                }
                demandeDecision.setCodEtatDemd(souscriptionContratPlacementForm.getDemandeDecisionView().getCodEtatDemd());
                if ((!souscriptionContratPlacementForm.getDemandeDecisionView().getNumRcbDemd().equals(null) && 
                     !souscriptionContratPlacementForm.getDemandeDecisionView().getNumRcbDemd().equals(""))) {
                    demandeDecision.setNumRcbDemd(Long.valueOf(souscriptionContratPlacementForm.getDemandeDecisionView().getNumRcbDemd()));
                }

                if ((!souscriptionContratPlacementForm.getDemandeDecisionView().getNumTaunDemd().equals("") && 
                     !souscriptionContratPlacementForm.getDemandeDecisionView().getMontPvenDemd().equals("") && 
                     !souscriptionContratPlacementForm.getDemandeDecisionView().getDatCoupDemd().equals(""))) {
                    demandeDecision.setNumTaunDemd(Long.valueOf(souscriptionContratPlacementForm.getDemandeDecisionView().getNumTaunDemd()));
                    demandeDecision.setMontPvenDemd(Double.valueOf(souscriptionContratPlacementForm.getDemandeDecisionView().getMontPvenDemd()));
                    demandeDecision.setDatCoupDemd(DateHandler.strToDate(souscriptionContratPlacementForm.getDemandeDecisionView().getDatCoupDemd()));
                }
                if (!souscriptionContratPlacementForm.getDemandeDecisionView().getCodRdlDemd().equals("")) {
                    demandeDecision.setCodRdlDemd(souscriptionContratPlacementForm.getDemandeDecisionView().getCodRdlDemd());
                }
                /*    if (!souscriptionContratPlacementForm.getDemandeDecisionView().getNumCcptDemd().equals("")) {
                demandeDecision.getContratCpt().getContratCptId().setNumCcptCcpt(Long.valueOf(souscriptionContratPlacementForm.getDemandeDecisionView().getNumCcptDemd()));
            }*/
            }
        } catch (Exception e) {
            logger.error("Exception dans souscriptionContratPlacementAction / Methode : majDonnéesDemandeDecision:  ", 
                         e);
            throw new RuntimeException(e);
        }
        return demandeDecision;
    }

    /**
     * Fonction qui retourne un objet demande decision pour l'insérer dans la base
     * appelé par l'Action qui insére demande decision dans la base
     */
    public

    DemandeDecision affecterDonneesDemandeDecision(ActionForm form) {

        SouscriptionContratPlacementForm souscriptionContratPlacementForm = 
            (SouscriptionContratPlacementForm)form;

        DemandeDecision demandeDecision = new DemandeDecision();
        TypePiece typePiece = new TypePiece();
        Structure struct = new Structure();
        try {
            typePiece.setCodTpceTpce(Long.valueOf(souscriptionContratPlacementForm.getPersonneDemandeur().getCodTpceTpceDemandeur()));
            demandeDecision.setTypePiece(typePiece);
            demandeDecision.setNumNpceDemd(souscriptionContratPlacementForm.getPersonneDemandeur().getNumPcePersDemandeur());

            struct.setCodStrcStrc(Long.valueOf(souscriptionContratPlacementForm.getInitialisationView().getCodeAgence()));
            demandeDecision.setCodEtatDemd(Constants.ETAT_DEM_DECIS_SAISIE);
            demandeDecision.setStructure(struct);


            if (!souscriptionContratPlacementForm.getDemandeDecisionView().getCodPrdPrd().equals("0")) {
                ProduitPlacement produitPlc = new ProduitPlacement();
                produitPlc.setCodPrdPlc(Long.valueOf(souscriptionContratPlacementForm.getDemandeDecisionView().getCodPrdPrd()));
                demandeDecision.setProduitPlacement(produitPlc);
            }

            if (!souscriptionContratPlacementForm.getContratView().getNumCcptCcpt().equals("") && 
                !souscriptionContratPlacementForm.getContratView().getCodPrdPrd().equals("") && 
                !souscriptionContratPlacementForm.getContratView().getCodStrcStrc().equals("")) {

                Long x = 
                    Long.valueOf(souscriptionContratPlacementForm.getContratView().getNumCcptCcpt());
                Long y = 
                    Long.valueOf(souscriptionContratPlacementForm.getContratView().getCodPrdPrd());
                Long z = 
                    Long.valueOf(souscriptionContratPlacementForm.getContratView().getCodStrcStrc());

                ContratCptId contratCptId = new ContratCptId();
                ContratCpt cpt = new ContratCpt();
                contratCptId.setNumCcptCcpt(x);
                contratCptId.setCodPrdPrd(y);
                contratCptId.setCodStrcStrc(z);

                cpt.setContratCptId(contratCptId);
                demandeDecision.setContratCpt(cpt);
            }


            demandeDecision.setMontPlaDemd(Long.valueOf(new Double(new Double(StrHandler.strWithoutBlanck(souscriptionContratPlacementForm.getDemandeDecisionView().getMontPlaDemd())).doubleValue() * 
                                                                   1000).longValue()));
            demandeDecision.setNumDureDemd(Long.valueOf(souscriptionContratPlacementForm.getDemandeDecisionView().getNumDureDemd()));
            demandeDecision.setCodNdemDemd(souscriptionContratPlacementForm.getDemandeDecisionView().getCodNdemDemd());
            demandeDecision.setBoolFaxDemd(Long.valueOf(souscriptionContratPlacementForm.getDemandeDecisionView().getPecFax()));
            demandeDecision.setNomNomDemd(souscriptionContratPlacementForm.getPersonneDemandeur().getNomNomPersDemandeur());
            demandeDecision.setNomPrnDemd(souscriptionContratPlacementForm.getPersonneDemandeur().getNomPrnPersDemandeur());
            demandeDecision.setCodTdemDemd(souscriptionContratPlacementForm.getDemandeDecisionView().getCodTdemDemd());
            //demandeDecision.setDatCreDemd(DateHandler.strToDate(souscriptionContratPlacementForm.getDemandeDecisionView().getDatCreDemd()));
            demandeDecision.setDatCreDemd(new Date());
            demandeDecision.setNumTaugDemd(Double.valueOf(souscriptionContratPlacementForm.getDemandeDecisionView().getTauxGeneral()));
            demandeDecision.setLibRmqDemd(souscriptionContratPlacementForm.getLibRmqDemd());
            demandeDecision.setNumTircDemd(Double.valueOf(souscriptionContratPlacementForm.getDemandeDecisionView().getTauxIrc()));
            demandeDecision.setCodPintDemd(souscriptionContratPlacementForm.getDemandeDecisionView().getCodTypPaiement());
            demandeDecision.setDatValDemd(DateHandler.strToDate(souscriptionContratPlacementForm.getDemandeDecisionView().getDateValeur()));
            demandeDecision.setCodFavDemd(souscriptionContratPlacementForm.getDemandeDecisionView().getCodeTypeFaveur());
            if (!souscriptionContratPlacementForm.getDemandeDecisionView().getCodeTypeFaveur().equals("")) {
                demandeDecision.setDatLimDemd(DateHandler.strToDate(souscriptionContratPlacementForm.getDemandeDecisionView().getDateLimite()));

                if (souscriptionContratPlacementForm.getDemandeDecisionView().getCodeTypeFaveur().equals("F")) {
                    // cas de demande de faveur Fixe
                    demandeDecision.setNumTaudDemd(Double.valueOf(souscriptionContratPlacementForm.getDemandeDecisionView().getTauxDemande()));
                } else if (souscriptionContratPlacementForm.getDemandeDecisionView().getCodeTypeFaveur().equals("I")) {
                    // cas de demande de faveur indéxé
                    demandeDecision.setNumMargDemd(Double.valueOf(souscriptionContratPlacementForm.getDemandeDecisionView().getTauxMarge()));
                    demandeDecision.setCodMargDemd(souscriptionContratPlacementForm.getDemandeDecisionView().getSignMarge());
                } else if (souscriptionContratPlacementForm.getDemandeDecisionView().getCodeTypeFaveur().equals("G") || 
                           souscriptionContratPlacementForm.getDemandeDecisionView().getCodeTypeFaveur().equals("P")) {
                    // CB général ou préférentielle
                    demandeDecision.setNumToffDemd(Double.valueOf(souscriptionContratPlacementForm.getDemandeDecisionView().getTauxGeneral()));                    
                    
                }
            } else {
                // condition de banque  
                souscriptionContratPlacementForm.getDemandeDecisionView().setCodeTypeFaveur("G");
            }

            if (souscriptionContratPlacementForm.getDemandeDecisionView().getCodNdemDemd().equals("R")) {
                // cas de renouvellement
                if (!souscriptionContratPlacementForm.getDemandeDecisionView().getNumSeqCpla().equals("")) {
                    ContratPlacement contratPlacement = new ContratPlacement();
                    contratPlacement.setNumSeqCpla(Long.valueOf(souscriptionContratPlacementForm.getDemandeDecisionView().getNumSeqCpla()));
                    demandeDecision.setContratPlacement(contratPlacement);
                }
                demandeDecision.setCodTyprDemd(Long.valueOf(souscriptionContratPlacementForm.getDemandeDecisionView().getTypeRenouvellement()));
                if (souscriptionContratPlacementForm.getDemandeDecisionView().getTypeRenouvellement().equals("1")) {
                    // renouvellement avant echance
                    demandeDecision.setDatValDemd(DateHandler.strToDate(souscriptionContratPlacementForm.getDateValNvSousc()));
                } else
                    demandeDecision.setDatValDemd(DateHandler.strToDate(souscriptionContratPlacementForm.getDemandeDecisionView().getDateValeur()));
            }
            
            if(souscriptionContratPlacementForm.getCodSbdvDemd().equals("1")){
                demandeDecision.setCodSbdvDemd("1");
            }


        } catch (Exception e) {
            logger.error("Exception dans souscriptionContratPlacementAction / Methode : affecterDonnéesDemandeDecision:  ", 
                         e);
            throw new RuntimeException(e);
        }
        return demandeDecision;

    }


    public ActionForward validationDemandePlacement(ActionMapping mapping, 
                                                    ActionForm form, 
                                                    HttpServletRequest request, 
                                                    HttpServletResponse response) throws IOException, 
                                                                                         ServletException {

        ActionMessages actionMessages = new ActionMessages();
        SouscriptionContratPlacementForm souscriptionContratPlacementForm = 
            (SouscriptionContratPlacementForm)form;
        try {
            ValiderDdeDecisionCmd validerDdeDecisionCmd = new ValiderDdeDecisionCmd();
            DemandeDecision demandeDecision = new DemandeDecision();
            demandeDecision = affecterDonneesDemandeDecision(souscriptionContratPlacementForm);
            demandeDecision = (DemandeDecision)validerDdeDecisionCmd.execute(demandeDecision);
            if(demandeDecision.getCodNdemDemd().equalsIgnoreCase("R")){
              // cas de renouvellement : mettre à jour le contrat de placement : codErenCpla = 1 (en cours de renouvellemnt)...
               UpdateContratPlacementCmd updateContratPlacementCmd = new UpdateContratPlacementCmd();
               souscriptionContratPlacementForm.getContratPlacement().setCodErenCpla(Long.valueOf("1"));
               ContratPlacement cpla  = (ContratPlacement)updateContratPlacementCmd.execute(souscriptionContratPlacementForm.getContratPlacement());
            }
            
            
            imprimerDemandeFaveur(request,form,demandeDecision);
            String message = "";
            message = 
                    " Demande de placement N° " + demandeDecision.getNumRefdDemd().toString() + 
                    " au nom de " + 
                    souscriptionContratPlacementForm.getPersonneDemandeur().getNomNomPersDemandeur() + 
                    " " + 
                    souscriptionContratPlacementForm.getPersonneDemandeur().getNomPrnPersDemandeur() + 
                    " est crée avec succès et en attente de validation par le chef d'agence";


            souscriptionContratPlacementForm.setLibelleConfirmation(message);


            return mapping.findForward("confirmationDdeDecision");

        } catch (Exception e) {
            com.oxia.fwk.core.Error erreur = new com.oxia.fwk.core.Error();
            StringBuffer text = 
                new StringBuffer("La validation de la demande de placement a été interrompue, veuillez transmettre ce message à l'équipe informatique:");
            text.append("Exception au niveau de l'agence:");
            text.append(souscriptionContratPlacementForm.getInitialisationView().getCodeAgence());
            text.append(". Exception :");
            text.append(e.toString());
            erreur.setCode("200");
            erreur.setDescription(text.toString());
            logger.error(text.toString(), e);
            ActionMessage actionMessage = 
                new ActionMessage("exception.generique", 
                                  erreur.getDescription());
            actionMessages.add("Erreur ", actionMessage);
            this.saveMessages(request, actionMessages);
            return mapping.findForward("error");
        }

    }


    public ActionForward rechercherListeCpt(ActionMapping mapping, 
                                            ActionForm form, 
                                            HttpServletRequest request, 
                                            HttpServletResponse response) throws IOException, 
                                                                                 ServletException {

        ActionMessages actionMessages = new ActionMessages();
        SouscriptionContratPlacementForm souscriptionContratPlacementForm = 
            (SouscriptionContratPlacementForm)form;
        Long nbrCpt = Long.valueOf(0);
        try {


          Long[] produitEligiblePlacementClassique = new Long[20];
          produitEligiblePlacementClassique[0] = Long.valueOf(101);
          produitEligiblePlacementClassique[1] = Long.valueOf(103);
          produitEligiblePlacementClassique[3] = Long.valueOf(109);
          produitEligiblePlacementClassique[4] = Long.valueOf(115);
          produitEligiblePlacementClassique[5] = Long.valueOf(129);
          produitEligiblePlacementClassique[6] = Long.valueOf(155);
          produitEligiblePlacementClassique[7] = Long.valueOf(167);            
          produitEligiblePlacementClassique[8] = Long.valueOf(121);    
          produitEligiblePlacementClassique[9] = Long.valueOf(134);
          produitEligiblePlacementClassique[10] = Long.valueOf(183);
          produitEligiblePlacementClassique[11] = Long.valueOf(193);
                        
          Long[] produitEligiblePlacementSBDV = new Long[20];
          produitEligiblePlacementSBDV[0] = Long.valueOf(101);
          produitEligiblePlacementSBDV[1] = Long.valueOf(103);
          produitEligiblePlacementSBDV[3] = Long.valueOf(109);
          produitEligiblePlacementSBDV[4] = Long.valueOf(115);
          produitEligiblePlacementSBDV[5] = Long.valueOf(129);
          produitEligiblePlacementSBDV[8] = Long.valueOf(121);    
          produitEligiblePlacementSBDV[9] = Long.valueOf(134);
          
                        
          Long[] produitEligible = new Long[20]; 
          
          if(souscriptionContratPlacementForm.getCodSbdvDemd().equals("0")){
            // souscription classique 
             produitEligible = produitEligiblePlacementClassique;
          }else if(souscriptionContratPlacementForm.getCodSbdvDemd().equals("1")){
             produitEligible = produitEligiblePlacementSBDV;
          }
            
            Listes listesCpts = new Listes();
            listesCpts.setList(new ArrayList());
            souscriptionContratPlacementForm.setListContratCpt(null);
            PersonneStrc personneStrc = new PersonneStrc();
            PersonneRechercheContratVo personneRechercheContratVo = 
                new PersonneRechercheContratVo();
            personneStrc.setCodStrcStrc(Long.valueOf(souscriptionContratPlacementForm.getInitialisationView().getCodeAgence()));
            // si la recherche est effectuée par type et num piece
            if (souscriptionContratPlacementForm.getChoixRecherche().equals("0")) {
                personneStrc.setCodTpceTpce(Long.valueOf(souscriptionContratPlacementForm.getDemandeDecisionView().getCodTpceTpce()));
                personneStrc.setNumPcePers(souscriptionContratPlacementForm.getDemandeDecisionView().getNumNpceDemd());

                GetPersonneCptCmd getPersonneCptCmd = new GetPersonneCptCmd();
                PersonneCpt personneCptRetour = 
                    (PersonneCpt)getPersonneCptCmd.execute(personneStrc);
                Personne personneRetour = personneCptRetour.getPersonne();

                if (personneRetour != null) {

                    if (personneRetour.getNomNomPers() != null && 
                        !personneRetour.getNomNomPers().equals(""))
                        souscriptionContratPlacementForm.getDemandeDecisionView().setNomNomDemd(personneRetour.getNomNomPers());
                    else if (personneRetour.getNomRsPers() != null && 
                             !personneRetour.getNomRsPers().equals(""))
                        souscriptionContratPlacementForm.getDemandeDecisionView().setNomNomDemd(personneRetour.getNomRsPers());
                    if (personneRetour.getNomPrnPers() != null && 
                        !personneRetour.getNomPrnPers().equals(""))
                        souscriptionContratPlacementForm.getDemandeDecisionView().setNomPrnDemd(personneRetour.getNomPrnPers());
                    else if (personneRetour.getLibSiglPers() != null && 
                             !personneRetour.getLibSiglPers().equals(""))
                        souscriptionContratPlacementForm.getDemandeDecisionView().setNomPrnDemd(personneRetour.getLibSiglPers());

                    personneRechercheContratVo.setPersonneStrc(personneStrc);
                    personneRechercheContratVo.setEtatContrat(Constants.COD_ETAT_CPT_VALID);
                    GetListContratCmd getListContratCmd = 
                        new GetListContratCmd();
                    listesCpts = 
                            (Listes)getListContratCmd.execute(personneRechercheContratVo);

                    if (!listesCpts.hasError()) {

                        if (listesCpts.getList().size() > 0) {
                            // affecter la liste des contrats à la liste de collection Tag                          
                            souscriptionContratPlacementForm.setAlertContrat("");
                            List listeDesContratsView = new ArrayList();

                            for (Iterator it = listesCpts.getList().iterator(); 
                                 it.hasNext(); ) {
                                ContratCpt contratCpt = (ContratCpt)it.next();
                                if (contratCpt.getClient().getTypePers().getCodTperTper().equals(Constants.PERSMORALE)) {
                                    souscriptionContratPlacementForm.getDemandeDecisionView().setMatriculeFiscal(contratCpt.getClient().getNumFiscClt());
                                    if (souscriptionContratPlacementForm.getDemandeDecisionView().getMatriculeFiscal() == 
                                        null || 
                                        souscriptionContratPlacementForm.getDemandeDecisionView().getMatriculeFiscal().equals(""))
                                        souscriptionContratPlacementForm.setAlertMatriculeFiscale("Remarque : Matricule fiscale non garnie pour ce client...");
                                }
                                for (int i = 0; i < produitEligible.length; 
                                     i++) {
                                    if (contratCpt.getContratCptId().getCodPrdPrd().equals(produitEligible[i])) {
                                        String cleContrat = 
                                            StrHandler.lpad(contratCpt.getContratCptId().getCodStrcStrc().toString(), 
                                                            '0', 3) + 
                                            StrHandler.lpad(contratCpt.getContratCptId().getCodPrdPrd().toString(), 
                                                            '0', 4) + 
                                            StrHandler.lpad(contratCpt.getContratCptId().getNumCcptCcpt().toString(), 
                                                            '0', 6);

                                        ContratCptView contratCptView = 
                                            new ContratCptView();
                                        contratCptView.setCleContrat(cleContrat);
                                        contratCptView.setDateContrat(DateHandler.dateToStr(contratCpt.getDatOuvCcpt()));
                                        contratCptView.setNumeroCompte(StrHandler.lpad(contratCpt.getContratCptId().getNumCcptCcpt().toString(), 
                                                                                       '0', 
                                                                                       6));
                                        contratCptView.setCodeAgence(StrHandler.lpad(contratCpt.getContratCptId().getCodStrcStrc().toString(), 
                                                                                     '0', 
                                                                                     3));
                                        contratCptView.setCodeProduit(StrHandler.lpad(contratCpt.getContratCptId().getCodPrdPrd().toString(), 
                                                                                      '0', 
                                                                                      4));
                                        contratCptView.setSolde(StrHandler.formatmnt(Math.abs(contratCpt.getMontSoldCcpt().doubleValue())));
                                        contratCptView.setContratCpt(contratCpt);
                                        nbrCpt = nbrCpt + 1;
                                        if (contratCpt.getMontSoldCcpt() > 0)
                                            contratCptView.setSens("CR");
                                        else
                                            contratCptView.setSens("DB");

                                        listeDesContratsView.add(contratCptView);
                                        break;
                                    }
                                }
                            }

                            souscriptionContratPlacementForm.setListContratCpt(listeDesContratsView);
                            souscriptionContratPlacementForm.setNbrContrat(nbrCpt);


                        } else
                            souscriptionContratPlacementForm.setAlertContrat("ListeCptVide");

                    }

                } else {
                    souscriptionContratPlacementForm.setAlertContrat("PersonneInexistante");
                    souscriptionContratPlacementForm.getDemandeDecisionView().setNomNomDemd("");
                    souscriptionContratPlacementForm.getDemandeDecisionView().setNomPrnDemd("");
                    souscriptionContratPlacementForm.setListContratCpt(null);

                }
            }
            
            String forward ="";
            if(souscriptionContratPlacementForm.getCodSbdvDemd().equals("1")){
                // demande sous bonne date valeur
                forward = "demandeDecisionPlacementSBDV";
            }else forward = "demandeDecisionPlacement";
            
            return mapping.findForward(forward);

        } catch (Exception e) {
            com.oxia.fwk.core.Error erreur = new com.oxia.fwk.core.Error();
            StringBuffer text = 
                new StringBuffer("La validation de la demande de placement a été interrompue, veuillez transmettre ce message à l'équipe informatique:");
            text.append("Exception au niveau de l'agence:");
            text.append(souscriptionContratPlacementForm.getInitialisationView().getCodeAgence());
            text.append(". Exception :");
            text.append(e.toString());
            erreur.setCode("200");
            erreur.setDescription(text.toString());
            logger.error(text.toString(), e);
            ActionMessage actionMessage = 
                new ActionMessage("exception.generique", 
                                  erreur.getDescription());
            actionMessages.add("Erreur ", actionMessage);
            this.saveMessages(request, actionMessages);
            return mapping.findForward("error");
        }

    }


    public ActionForward rechercherContratsPlacement(ActionMapping mapping, 
                                                     ActionForm form, 
                                                     HttpServletRequest request, 
                                                     HttpServletResponse response) throws IOException, 
                                                                                          ServletException {

        SouscriptionContratPlacementForm souscriptionContratPlacementForm = 
            (SouscriptionContratPlacementForm)form;
        ActionMessages actionMessages = new ActionMessages();

        try {
            String[] codEtat = new String[3];

            String vContratChoisi = StrHandler.lpad( (String)request.getParameter("compte"),'0',13);

            String nature = (String)request.getParameter("nature");

            String typeF = (String)request.getParameter("typeF");

            souscriptionContratPlacementForm.setListeContratsPlacement(null);
            ParamDemandeDecision paramDemandeDecision = 
                new ParamDemandeDecision();
            GetListContratsPlacementCmd getListContratsPlacementCmd = 
                new GetListContratsPlacementCmd();
            Listes listContratPlacement = new Listes();
            ContratPersonne contratPersonne = new ContratPersonne();
            ContratCptId contratCptId = new ContratCptId();
            souscriptionContratPlacementForm.getDemandeDecisionView().setCodNdemDemd(nature);
            souscriptionContratPlacementForm.setTypeForm(typeF);

            String vcodStrcStrc = "";
            String vcodPrdPrd = "";
            String vnumCcptCcpt = "";
            vcodStrcStrc = vContratChoisi.substring(0, 3);
            vcodPrdPrd = vContratChoisi.substring(3, 7);
            vnumCcptCcpt = 
                    vContratChoisi.substring(7, vContratChoisi.length());

            souscriptionContratPlacementForm.getContratView().setCodPrdPrd(vcodPrdPrd);
            souscriptionContratPlacementForm.getContratView().setCodStrcStrc(vcodStrcStrc);
            souscriptionContratPlacementForm.getContratView().setNumCcptCcpt(vnumCcptCcpt);

            // if (!souscriptionContratPlacementForm.getContratChoisi().equals("")) {

            contratCptId.setCodPrdPrd(Long.valueOf(vcodPrdPrd));
            contratCptId.setCodStrcStrc(Long.valueOf(vcodStrcStrc));
            contratCptId.setNumCcptCcpt(Long.valueOf(vnumCcptCcpt));
            contratPersonne.setContratCptId(contratCptId);
            paramDemandeDecision.setContratPersonne(contratPersonne);


            // paramDemandeDecision.setCodEtatDemd(codEtat);
            if (souscriptionContratPlacementForm.getDemandeDecisionView().getCodNdemDemd().equals("S")) {
                paramDemandeDecision.setCodEtatContrat(Constants.ETAT_CONTRAT_PLAC_VALIDE);
            } else if (souscriptionContratPlacementForm.getDemandeDecisionView().getCodNdemDemd().equals("R")){
                // cas de renouvellement
                codEtat[0] = Constants.ETAT_CONTRAT_PLAC_VALIDE;                
                paramDemandeDecision.setCodEtatDemd(codEtat);
                paramDemandeDecision.setEtatRenouvellement(Long.valueOf(0));
            }

            listContratPlacement = 
                    (Listes)getListContratsPlacementCmd.execute(paramDemandeDecision);
            //}

            if (listContratPlacement.getList() != null && 
                listContratPlacement.getList().size() > 0) {
                List listContratsPlacementView = 
                    traiterListeContratsPlacement(listContratPlacement.getList(), 
                                                  souscriptionContratPlacementForm);
                souscriptionContratPlacementForm.setListeContratsPlacement(listContratsPlacementView);
            } else {
                souscriptionContratPlacementForm.setAlertCptPlac("Aucun contrat de placement pour ce client...");
            }


            return mapping.findForward("contratAnterieures");

        } catch (Exception e) {
            com.oxia.fwk.core.Error erreur = new com.oxia.fwk.core.Error();
            StringBuffer text = 
                new StringBuffer("la transaction est Interrompu, une erreur dans souscriptionContratPlacementForm / Dispatch Action :rechercherContratsPlacement ");
            text.append(e.toString());
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


    public List traiterListeContratsPlacement(List listContrats, 
                                              ActionForm form) {

        SouscriptionContratPlacementForm souscriptionContratPlacementForm = 
            (SouscriptionContratPlacementForm)form;
        List listContratPlacementView = new ArrayList();
        Context context = ContextHandler.getContext();

        if (listContrats != null && listContrats.size() > 0) {

            for (Iterator it = listContrats.iterator(); it.hasNext(); ) {
                ContratPlacement contratPlacement = 
                    (ContratPlacement)it.next();

                ContratPlacementView contratPlacementView = 
                    new ContratPlacementView();
                contratPlacementView.setCleCpla(contratPlacement.getNumSeqCpla().toString());
                contratPlacementView.setContratPlacement(contratPlacement);
                contratPlacementView.setDatCreCpla(DateHandler.dateToStr(contratPlacement.getDatCreCpla()));
                contratPlacementView.setDateValeur(DateHandler.dateToStr(contratPlacement.getDatValCpla()));
                contratPlacementView.setDatCreCpla(DateHandler.dateToStr(contratPlacement.getDatCreCpla()));
                contratPlacementView.setDatEcheCpla(DateHandler.dateToStr(contratPlacement.getDatEcheCpla()));
                contratPlacementView.setMontCapCpla(StrHandler.formatmnt(Math.abs(contratPlacement.getMontCapCpla().doubleValue())));
                contratPlacementView.setMontTauInt(contratPlacement.getNumTauiCpla().toString());
                contratPlacementView.setLibPrdPrd(contratPlacement.getProduitPlacement().getLibPrdPlc());
                listContratPlacementView.add(contratPlacementView);
            } // Fin For 
        }
        return listContratPlacementView;
    }


    /**
     * Action qui permet d'afficher une liste des demandes selon le choix et selon le typeForm (Souscription(demande valide=>en attente de traitement)/MAJ (demande en attente de validation))
     */
    public

    ActionForward rechercherDemandesSelonChoix(ActionMapping mapping, 
                                               ActionForm form, 
                                               HttpServletRequest request, 
                                               HttpServletResponse response) throws IOException, 
                                                                                    ServletException {
        Context context = ContextHandler.getContext();
        SouscriptionContratPlacementForm souscriptionContratPlacementForm = 
            (SouscriptionContratPlacementForm)form;
        ActionMessages actionMessages = new ActionMessages();
        String[] codEtat = new String[3];
        Long[] listStructure = new Long[10];
        try {
            souscriptionContratPlacementForm.setListeDemandesDecision(null);
            ParamDemandeDecision paramDemandeDecision = 
                new ParamDemandeDecision();
            GetListDemandesDecisionCmd getListDemandesDecisionCmd = 
                new GetListDemandesDecisionCmd();
            Listes listDemandeDecision = new Listes();
            ContratPersonne contratPersonne = new ContratPersonne();
            PersonneStrc personneStrc = new PersonneStrc();
            souscriptionContratPlacementForm.setListeDemandesDecision(null);

            if (!souscriptionContratPlacementForm.getStructureRech().equals("")) {
                // le cas de saisi d'une agence
                listStructure[0] = 
                        Long.valueOf(souscriptionContratPlacementForm.getStructureRech());
                paramDemandeDecision.setCodStrcStrc(listStructure);
            } else
                paramDemandeDecision.setCodStrcStrc(null);


            if (souscriptionContratPlacementForm.getTypeForm().equals("Souscription")) {
                codEtat[0] = Constants.ETAT_DEM_DECIS_VALIDE;
                paramDemandeDecision.setNatureDemande("S");
                souscriptionContratPlacementForm.clearFormDemandeDecision();
                souscriptionContratPlacementForm.clearFormSouscriptionCptPlacement();
                souscriptionContratPlacementForm.getContratView().setCodStrcStrc(souscriptionContratPlacementForm.getInitialisationView().getCodeAgence());
            } else if (souscriptionContratPlacementForm.getTypeForm().equals("Validation")) {
                codEtat[0] = Constants.ETAT_DEM_DECIS_SAISIE;

            } else if (souscriptionContratPlacementForm.getTypeForm().equals("etude")) {
                codEtat[0] = Constants.ETAT_DEM_DECIS_SAISI_VALIDEE;

            } else if (souscriptionContratPlacementForm.getTypeForm().equals("validationEtude")) {
                codEtat[0] = Constants.ETAT_DEM_DECIS_ETUDEE;

            } else if (souscriptionContratPlacementForm.getTypeForm().equals("notification")) {
                codEtat[0] = Constants.ETAT_DEM_DECIS_ETUDE_VALIDEE;
            } else if (souscriptionContratPlacementForm.getTypeForm().equals("RecepNotif")) {
                codEtat[0] = Constants.ETAT_DEM_DECIS_NOTIFIE;
            }

            paramDemandeDecision.setCodEtatDemd(codEtat);
            contratPersonne.setPersonneId(personneStrc);
            paramDemandeDecision.setContratPersonne(contratPersonne);
            if (souscriptionContratPlacementForm.getChoix().equals("0")){
                // traiter le cas de la recherche par type et numéro de pièce
                personneStrc.setCodTpceTpce(new Long(souscriptionContratPlacementForm.getTypePieceId()));
                personneStrc.setNumPcePers(souscriptionContratPlacementForm.getNumPieceId());
                contratPersonne.setPersonneId(personneStrc);
                paramDemandeDecision.setContratPersonne(contratPersonne);
            } else if (souscriptionContratPlacementForm.getChoix().equals("1")) {
                // traiter le cas de la recherche par numéro de demande
                paramDemandeDecision.setNumRefdDemd(Long.valueOf(souscriptionContratPlacementForm.getNumDemdRech()));
            } else if (souscriptionContratPlacementForm.getChoix().equals("2")) {
                // traiter le cas de la recherche par structure
                listStructure[0] = 
                        Long.valueOf(souscriptionContratPlacementForm.getStructureRech());
                paramDemandeDecision.setCodStrcStrc(listStructure);

            } else if (souscriptionContratPlacementForm.getChoix().equals("3")) {
                //traiter le cas de la recherche par produit de placement                 
                paramDemandeDecision.setProduitPlacement(Long.valueOf(souscriptionContratPlacementForm.getProduitRech()));
            } else if (souscriptionContratPlacementForm.getChoix().equals("4")) {
                // traiter le cas de la recherche de toutes les demandes par date             
                paramDemandeDecision.setDateDebut(DateHandler.strToDate(souscriptionContratPlacementForm.getDateDebRecherch()));
                paramDemandeDecision.setDateFin(DateHandler.strToDate(souscriptionContratPlacementForm.getDateFin()));
            }

            listDemandeDecision = 
                    (Listes)getListDemandesDecisionCmd.execute(paramDemandeDecision);

            if (listDemandeDecision.getList() != null && 
                listDemandeDecision.getList().size() > 0) {
                List listDdeDecisionView = 
                    traiterListedemandesDecision(listDemandeDecision.getList(), 
                                                 souscriptionContratPlacementForm);
                souscriptionContratPlacementForm.setListeDemandesDecision(listDdeDecisionView);
                souscriptionContratPlacementForm.setAlertDemandeDecision("False");
            } else {
                souscriptionContratPlacementForm.setAlertDemandeDecision("True");
            }

            String forward = "";
            if (souscriptionContratPlacementForm.getTypeForm().equals("Souscription")) {
                forward = "initierSouscriptionPlacement";
            } else {
                forward = "listeDemandesDecision";
            }

            return mapping.findForward(forward);
        } catch (Exception e) {
            com.oxia.fwk.core.Error erreur = new com.oxia.fwk.core.Error();
            StringBuffer text = 
                new StringBuffer("La recherche de la demande choisie a été interrompue, veuillez transmettre ce message à l'équipe informatique: ");
            text.append("Exception au niveau de l'agence:");
            text.append(souscriptionContratPlacementForm.getInitialisationView().getCodeAgence());
            text.append(". Exception :");
            text.append(e.toString());
            erreur.setCode("200");
            erreur.setDescription(text.toString());
            logger.error(text.toString(), e);

            ActionMessage actionMessage = 
                new ActionMessage("exception.generique", 
                                  erreur.getDescription());
            actionMessages.add("Erreur ", actionMessage);
            this.saveMessages(request, actionMessages);
            return mapping.findForward("error");
        }
    }

    /**
     * Fonction qui retourne la liste des demandes à afficher
     * appelé par l'Action rechercherDemandesSelonChoix
     */
    public

    List traiterListedemandesDecision(List listDemandes, ActionForm form) {

        SouscriptionContratPlacementForm souscriptionContratPlacementForm = 
            (SouscriptionContratPlacementForm)form;
        List listDemandeDecisionView = new ArrayList();
        Context context = ContextHandler.getContext();
        try {
            if (listDemandes != null && listDemandes.size() > 0) {

                for (Iterator it = listDemandes.iterator(); it.hasNext(); ) {
                    DemandeDecision demandeDecision = 
                        (DemandeDecision)it.next();

                    DemandeDecisionView demandeDecisionView = 
                        new DemandeDecisionView();

                    demandeDecisionView.setDemandeDecision(demandeDecision);
                    demandeDecisionView.setDatCreDemd(DateHandler.dateToStr(demandeDecision.getDatCreDemd()));
                    demandeDecisionView.setCodEtatDemd(demandeDecision.getCodEtatDemd());
                    demandeDecisionView.setCodNdemDemd(demandeDecision.getCodNdemDemd());
                    demandeDecisionView.setMontPlaDemd(StrHandler.formatmnt(Math.abs(demandeDecision.getMontPlaDemd().doubleValue())));
                    demandeDecisionView.setDatValDemd(DateHandler.dateToStr(demandeDecision.getDatValDemd()));
                    if(demandeDecision.getCodSbdvDemd()!= null && demandeDecision.getCodSbdvDemd().equals("1")){
                        demandeDecisionView.setCodSbdvDemd("O");
                    }else demandeDecisionView.setCodSbdvDemd("N");
                    
                    listDemandeDecisionView.add(demandeDecisionView);
                    souscriptionContratPlacementForm.setAlertDemandeDecision("False");
                } // Fin For 
            } else {
                souscriptionContratPlacementForm.setAlertDemandeDecision("True");
            }
        } catch (Exception e) {
            logger.error("Exception dans souscriptionContratPlacementAction / Methode : traiterListedemandesDecision:  ", 
                         e);
            throw new RuntimeException(e);
        }
        return listDemandeDecisionView;
    }

    /**
     * Action qui affiche une demande selon son numéro
     * appelé par la fonction JS : rechercherDemandes() si choix =1 (par numéro de demande)
     */
    public

    ActionForward afficherDemandesDecisionSelonNumRef(ActionMapping mapping, 
                                                      ActionForm form, 
                                                      HttpServletRequest request, 
                                                      HttpServletResponse response) throws IOException, 
                                                                                           ServletException {

        SouscriptionContratPlacementForm souscriptionContratPlacementForm = 
            (SouscriptionContratPlacementForm)form;
        ActionMessages actionMessages = new ActionMessages();
        DemandeDecision demandeDecisionTrouve = new DemandeDecision();
        try {
            souscriptionContratPlacementForm.setTypeForm("MAJ");
            if (!souscriptionContratPlacementForm.getNumDemdRech().equals(null) && 
                !souscriptionContratPlacementForm.getNumDemdRech().equals("")) {
                GetDemandeDecisionCmd getDemandeDecisionCmd = 
                    new GetDemandeDecisionCmd();
                DemandeDecision demandeDecision = new DemandeDecision();
                demandeDecision.setNumRefdDemd(Long.valueOf(souscriptionContratPlacementForm.getNumDemdRech()));
                demandeDecisionTrouve = 
                        (DemandeDecision)getDemandeDecisionCmd.execute(demandeDecision);

                if (demandeDecisionTrouve != null && 
                    demandeDecisionTrouve.getNumRefdDemd() != null) {
                    souscriptionContratPlacementForm.setDemandeDecision(demandeDecisionTrouve);
                    souscriptionContratPlacementForm.setDemandeDecisionView(affecterDemandeDecisionView(demandeDecisionTrouve));
                    souscriptionContratPlacementForm.setAlertAfficheDde("True");
                } else {
                    souscriptionContratPlacementForm.setAlertAfficheDde("False");
                }


            }
            String forward ="";
            if(souscriptionContratPlacementForm.getCodSbdvDemd().equals("1")){
                // demande sous bonne date valeur
                forward = "demandeDecisionPlacementSBDV";
            }else forward = "demandeDecisionPlacement";
            
            return mapping.findForward(forward);           
            
            
        } catch (Exception e) {
            com.oxia.fwk.core.Error erreur = new com.oxia.fwk.core.Error();
            StringBuffer text = 
                new StringBuffer("L' affichage de la demande de décision chosie selon son numéro, a été interrompue, veuillez transmettre ce message à l'équipe informatique:");
            text.append("Exception au niveau de l'agence:");
            text.append(souscriptionContratPlacementForm.getInitialisationView().getCodeAgence());
            text.append(". Exception :");
            text.append(e.toString());
            erreur.setCode("200");
            erreur.setDescription(text.toString());
            logger.error(text.toString(), e);

            ActionMessage actionMessage = 
                new ActionMessage("exception.generique", 
                                  erreur.getDescription());
            actionMessages.add("Erreur ", actionMessage);
            this.saveMessages(request, actionMessages);
            return mapping.findForward("error");
        }

    }

    /**
     * Action appelé si l'utilisateur coche une demande à afficher
     * appelé par la fonction JS : afficherDemande()
     */
    public

    ActionForward afficherDemandesDecision(ActionMapping mapping, 
                                           ActionForm form, 
                                           HttpServletRequest request, 
                                           HttpServletResponse response) throws IOException, 
                                                                                ServletException {

        SouscriptionContratPlacementForm souscriptionContratPlacementForm = 
            (SouscriptionContratPlacementForm)form;
        ActionMessages actionMessages = new ActionMessages();
        DemandeDecision demandeDecisionTrouve = new DemandeDecision();
        ParamAgence paramAgence = (ParamAgence)request.getSession().getAttribute("paramAgBNA");
        String tauxOffert = null;
        String signeOffert = null;
        try { //test sur le type form
            
            if (souscriptionContratPlacementForm.getTypeForm().equals("validationEtude")) {
             // sauvegarder les valeurs de la decision de la trésorerie pke après impréssion, les valeurs seront initialisé à partir de la base de données...
              tauxOffert  = souscriptionContratPlacementForm.getDemandeDecisionView().getTauxAccorde();
              signeOffert = souscriptionContratPlacementForm.getDemandeDecisionView().getSignMargeOffert(); 
            }
            
            if (!souscriptionContratPlacementForm.getNumDemandeChoisie().equals(null) && 
                !souscriptionContratPlacementForm.getNumDemandeChoisie().equals("")) {
                GetDemandeDecisionCmd getDemandeDecisionCmd = 
                    new GetDemandeDecisionCmd();
                DemandeDecision demandeDecision = new DemandeDecision();
                demandeDecision.setNumRefdDemd(Long.valueOf(souscriptionContratPlacementForm.getNumDemandeChoisie()));
                demandeDecisionTrouve = 
                        (DemandeDecision)getDemandeDecisionCmd.execute(demandeDecision);

                if (demandeDecisionTrouve != null && 
                    demandeDecisionTrouve.getNumRefdDemd() != null) {
                    souscriptionContratPlacementForm.setDemandeDecision(demandeDecisionTrouve);
                    souscriptionContratPlacementForm.setLibAbrPlc(demandeDecisionTrouve.getProduitPlacement().getLibAbrPlc());
                    souscriptionContratPlacementForm.getContratView().setCodStrcStrc(StrHandler.lpad(demandeDecisionTrouve.getContratCpt().getContratCptId().getCodStrcStrc().toString(), 
                                                                                                     '0', 
                                                                                                     3));
                    souscriptionContratPlacementForm.getContratView().setCodPrdPrd(StrHandler.lpad(demandeDecisionTrouve.getContratCpt().getContratCptId().getCodPrdPrd().toString(), 
                                                                                                   '0', 
                                                                                                   4));
                    souscriptionContratPlacementForm.getContratView().setNumCcptCcpt(StrHandler.lpad(demandeDecisionTrouve.getContratCpt().getContratCptId().getNumCcptCcpt().toString(), 
                                                                                                     '0', 
                                                                                                     6));
                    souscriptionContratPlacementForm.getContratView().setMontSoldCcpt(StrHandler.formatmnt(Math.abs(demandeDecisionTrouve.getContratCpt().getMontSoldCcpt().doubleValue())));
                    
                    if (demandeDecisionTrouve.getContratCpt().getMontSoldCcpt() > Long.valueOf(0))
                        souscriptionContratPlacementForm.getContratView().setSensSolde("CR");
                    else
                        souscriptionContratPlacementForm.getContratView().setSensSolde("DB");
                    
                    souscriptionContratPlacementForm.setContratChoisi(souscriptionContratPlacementForm.getContratView().getCodStrcStrc() + 
                                                                      souscriptionContratPlacementForm.getContratView().getCodPrdPrd() + 
                                                                      souscriptionContratPlacementForm.getContratView().getNumCcptCcpt());
                    souscriptionContratPlacementForm.getPersonneDemandeur().setCodTpceTpceDemandeur(demandeDecisionTrouve.getTypePiece().getCodTpceTpce().toString());
                    souscriptionContratPlacementForm.getPersonneDemandeur().setNumPcePersDemandeur(demandeDecisionTrouve.getNumNpceDemd());
                    souscriptionContratPlacementForm.getPersonneDemandeur().setNomNomPersDemandeur(demandeDecisionTrouve.getNomNomDemd());
                    souscriptionContratPlacementForm.getPersonneDemandeur().setNomPrnPersDemandeur(demandeDecisionTrouve.getNomPrnDemd());
                    souscriptionContratPlacementForm.getDemandeDecisionView().setCodTpceTpce(demandeDecisionTrouve.getContratCpt().getClient().getPersonne().getTypePiece().getCodTpceTpce().toString());
                    souscriptionContratPlacementForm.getDemandeDecisionView().setNumNpceDemd(demandeDecisionTrouve.getContratCpt().getClient().getPersonne().getNumPcePers());

                    if (demandeDecisionTrouve.getLibRmqDemd() != null && 
                        !demandeDecisionTrouve.getLibRmqDemd().equals(""))
                        souscriptionContratPlacementForm.setLibRmqDemd(demandeDecisionTrouve.getLibRmqDemd());


                    if (demandeDecisionTrouve.getProduitPlacement() != null)
                        souscriptionContratPlacementForm.getDemandeDecisionView().setCodPrdPrd(demandeDecisionTrouve.getProduitPlacement().getCodPrdPlc().toString());

                    souscriptionContratPlacementForm.getDemandeDecisionView().setMontPlaDemd(StrHandler.formatmnt(Math.abs(demandeDecisionTrouve.getMontPlaDemd().doubleValue())));
                    souscriptionContratPlacementForm.getDemandeDecisionView().setNumDureDemd(demandeDecisionTrouve.getNumDureDemd().toString());
                    souscriptionContratPlacementForm.getDemandeDecisionView().setCodNdemDemd(demandeDecisionTrouve.getCodNdemDemd());
                    if (demandeDecision.getNumRcbDemd() != null) {
                        souscriptionContratPlacementForm.getDemandeDecisionView().setNumRcbDemd(demandeDecisionTrouve.getNumRcbDemd().toString());
                    }
                    souscriptionContratPlacementForm.getDemandeDecisionView().setCodTdemDemd(demandeDecisionTrouve.getCodTdemDemd());
                    souscriptionContratPlacementForm.getDemandeDecisionView().setDatCreDemd(DateHandler.dateToStr(demandeDecisionTrouve.getDatCreDemd()));
                    souscriptionContratPlacementForm.getDemandeDecisionView().setDateValeur(DateHandler.dateToStr(demandeDecisionTrouve.getDatValDemd()));


                    if (demandeDecisionTrouve.getCodNdemDemd().equals("R")) {
                        // renouvellement
                        if (demandeDecisionTrouve.getContratPlacement() != 
                            null) {
                            souscriptionContratPlacementForm.getDemandeDecisionView().setNumSeqCpla(demandeDecisionTrouve.getContratPlacement().getNumSeqCpla().toString());
                            souscriptionContratPlacementForm.getDemandeDecisionView().setDateEchAncienContrat(DateHandler.dateToStr(demandeDecisionTrouve.getContratPlacement().getDatEcheCpla()));
                        }
                        souscriptionContratPlacementForm.getDemandeDecisionView().setTypeRenouvellement(String.valueOf(demandeDecisionTrouve.getCodTyprDemd()));

                        if (demandeDecisionTrouve.getCodTyprDemd().equals(Long.valueOf(1)))
                            // affecter la date comptable à la date valeur de la demande de renouvellemnt avant echeance pour pouvoir extraire la CB
                            souscriptionContratPlacementForm.getDemandeDecisionView().setDateValeur(paramAgence.getDateComptable());
                        else
                            souscriptionContratPlacementForm.getDemandeDecisionView().setDateValeur(DateHandler.dateToStr(demandeDecisionTrouve.getDatValDemd()));

                    }
                    // if(demandeDecisionTrouve.getNumTaugDemd()!= null)
                    // souscriptionContratPlacementForm.getDemandeDecisionView().setTauxGeneral(demandeDecisionTrouve.getNumTaugDemd().toString());
                    if (demandeDecisionTrouve.getCodFavDemd() != null) {
                        souscriptionContratPlacementForm.getDemandeDecisionView().setDateLimite(DateHandler.dateToStr(demandeDecisionTrouve.getDatLimDemd()));
                        souscriptionContratPlacementForm.getDemandeDecisionView().setCodeTypeFaveur(demandeDecisionTrouve.getCodFavDemd());
                        // si demande de faveur (I/F)                        
                        if (demandeDecisionTrouve.getCodFavDemd().equals("F")) {
                            // faveur avec taux Fixe
                            souscriptionContratPlacementForm.getDemandeDecisionView().setTauxDemande(demandeDecisionTrouve.getNumTaudDemd().toString());
                            souscriptionContratPlacementForm.setAlertTauxFaveur("Taux Fixe Accordé :");

                        } else if (demandeDecisionTrouve.getCodFavDemd().equals("I")) {
                            // faveur avec taux Indexé
                            souscriptionContratPlacementForm.setAlertTauxFaveur("Taux Variable Accordé : TMM " ); 
                            if(demandeDecisionTrouve.getCodMaroDemd()!= null )
                              souscriptionContratPlacementForm.getDemandeDecisionView().setSignMargeOffert(demandeDecisionTrouve.getCodMaroDemd());
                                  
                            souscriptionContratPlacementForm.getDemandeDecisionView().setTauxMarge(demandeDecisionTrouve.getNumMargDemd().toString());
                            souscriptionContratPlacementForm.getDemandeDecisionView().setSignMarge(demandeDecisionTrouve.getCodMargDemd());
                        }
                    } else {
                        souscriptionContratPlacementForm.getDemandeDecisionView().setTauxDemande("");
                        souscriptionContratPlacementForm.getDemandeDecisionView().setDateLimite("");
                        souscriptionContratPlacementForm.getDemandeDecisionView().setTauxMarge("");
                    }
                    if (demandeDecisionTrouve.getNumTircDemd() != null)
                        souscriptionContratPlacementForm.getDemandeDecisionView().setTauxIrc(demandeDecisionTrouve.getNumTircDemd().toString());

                    if (demandeDecisionTrouve.getCodFavDemd() != null && (demandeDecisionTrouve.getCodFavDemd().equals("F") ||demandeDecisionTrouve.getCodFavDemd().equals("I")) && 
                        demandeDecisionTrouve.getNumToffDemd() != null)
                        souscriptionContratPlacementForm.getDemandeDecisionView().setTauxAccorde(demandeDecisionTrouve.getNumToffDemd().toString());
                        
                        
                    if (souscriptionContratPlacementForm.getTypeForm().equals("validationEtude")) {
                      if(souscriptionContratPlacementForm.getAlertDemandeDecision().equals("impression")){
                       // lorsqu'on imprime la demande, la page sera de nouveau loadé, la demande sera de nouveau réinitialisée, donc on pert la modification
                       // effectuée au niveau de la phase de la trésorerie.
                          souscriptionContratPlacementForm.getDemandeDecisionView().setSignMargeOffert(signeOffert);
                          souscriptionContratPlacementForm.getDemandeDecisionView().setTauxAccorde(tauxOffert);
                      }
                    }       

                    souscriptionContratPlacementForm.setAlertAfficheDde("True");
                } else {
                    souscriptionContratPlacementForm.setAlertAfficheDde("False");
                }

                souscriptionContratPlacementForm.getDemandeDecisionView().setCodEtatDemd(demandeDecisionTrouve.getCodEtatDemd());
                souscriptionContratPlacementForm.getDemandeDecisionView().setCodTypPaiement(demandeDecisionTrouve.getCodPintDemd());
                souscriptionContratPlacementForm.getDemandeDecisionView().setCodSbdvDemd(demandeDecisionTrouve.getCodSbdvDemd());

            }


            return mapping.findForward("listeDemandesDecision");
        } catch (Exception e) {
            com.oxia.fwk.core.Error erreur = new com.oxia.fwk.core.Error();
            StringBuffer text = 
                new StringBuffer("L'affichage de la demande de decision a été interrompue, veuillez transmettre ce message à l'équipe informatique:");
            text.append("Exception au niveau de l'agence:");
            text.append(souscriptionContratPlacementForm.getInitialisationView().getCodeAgence());
            text.append(". Exception :");
            text.append(e.toString());
            erreur.setCode("200");
            erreur.setDescription(text.toString());
            logger.error(text.toString(), e);

            ActionMessage actionMessage = 
                new ActionMessage("exception.generique", 
                                  erreur.getDescription());
            actionMessages.add("Erreur ", actionMessage);
            this.saveMessages(request, actionMessages);
            return mapping.findForward("error");
        }
    }

    /**
     * Fonction qui retourne un objet DemandeDecisionView pour permettre l'affichage de l'objet DemandeDecision
     */
    public

    DemandeDecisionView affecterDemandeDecisionView(DemandeDecision demandeDecision) {


        DemandeDecisionView demandeDecisionView = new DemandeDecisionView();
        try {
            demandeDecisionView.setNumRefdDemd(demandeDecision.getNumRefdDemd().toString());

            demandeDecisionView.setCodTpceTpce(demandeDecision.getTypePiece().getCodTpceTpce().toString());

            if (demandeDecision.getContratPlacement() != null)
                demandeDecisionView.setNumSeqCpla(demandeDecision.getContratPlacement().getNumSeqCpla().toString());
            else
                demandeDecisionView.setNumSeqCpla(null);


            if (demandeDecision.getProduitPlacement() != null)
                demandeDecisionView.setCodPrdPrd(demandeDecision.getProduitPlacement().getCodPrdPlc().toString());


            demandeDecisionView.setMontPlaDemd(demandeDecision.getMontPlaDemd().toString());
            demandeDecisionView.setNumDureDemd(demandeDecision.getNumDureDemd().toString());
            demandeDecisionView.setCodNdemDemd(demandeDecision.getCodNdemDemd());
            demandeDecisionView.setCodEtatDemd(demandeDecision.getCodEtatDemd());
            if (demandeDecision.getNumRcbDemd() != null) {
                demandeDecisionView.setNumRcbDemd(demandeDecision.getNumRcbDemd().toString());
            }

            demandeDecisionView.setNumCcptDemd(demandeDecision.getContratCpt().getContratCptId().getCodPrdPrd().toString() + 
                                               demandeDecision.getContratCpt().getContratCptId().getCodStrcStrc().toString() + 
                                               demandeDecision.getContratCpt().getContratCptId().getNumCcptCcpt().toString());


            demandeDecisionView.setNumNpceDemd(demandeDecision.getNumNpceDemd());
            demandeDecisionView.setNomNomDemd(demandeDecision.getNomNomDemd());
            demandeDecisionView.setNomPrnDemd(demandeDecision.getNomPrnDemd());
            demandeDecisionView.setCodTdemDemd(demandeDecision.getCodTdemDemd());
            demandeDecisionView.setDatCreDemd(DateHandler.dateToStr(demandeDecision.getDatCreDemd()));
            demandeDecisionView.setPecFax(demandeDecision.getBoolFaxDemd().toString());
           
        } catch (Exception e) {
            logger.error("Exception dans souscriptionContratPlacementAction / Methode : affecterDemandeDecisionView:  ", 
                         e);
            throw new RuntimeException(e);
        }
        return (demandeDecisionView);
    }

    /**
     * Action qui renvoi vers la page JSP : demandeDecisionPlacement.jsp si typeForm=Creation
     *            renvoi vers la page JSP : souscriptionContratPlacement.jsp si typeForm=Souscription
     * Elle permet de rechercher les données du Contrat COMPTE (contratView)
     */
    public

    ActionForward rechercherContrat(ActionMapping mapping, ActionForm form, 
                                    HttpServletRequest request, 
                                    HttpServletResponse response) throws IOException, 
                                                                         ServletException {
        Context context = ContextHandler.getContext();
        SouscriptionContratPlacementForm souscriptionContratPlacementForm = 
            (SouscriptionContratPlacementForm)form;
        ActionMessages actionMessages = new ActionMessages();
        try {

            /*******recherche du contrat**********/
            GetDetailContratCmd getDetailContratCmd = 
                new GetDetailContratCmd();
            ContratCpt contratCpt = new ContratCpt();
            ContratCptId contratCptId = new ContratCptId();
            String codStrcStrc = "";
            String codPrdPrd = "";
            String numCcptCcpt = "";
            ContratView contratView = 
                souscriptionContratPlacementForm.getContratView();
            souscriptionContratPlacementForm.setAlertDemandeur("");
            souscriptionContratPlacementForm.setListContratCpt(null);
            List listeDesContratsView = new ArrayList();

            contratCptId.setCodStrcStrc(new Long(contratView.getCodStrcStrc()));
            contratCptId.setCodPrdPrd(new Long(contratView.getCodPrdPrd()));
            contratCptId.setNumCcptCcpt(new Long(contratView.getNumCcptCcpt()));
            contratCpt = (ContratCpt)getDetailContratCmd.execute(contratCptId);


            if (contratCpt.getContratCptId() != null) {
                /* Chargement du contrat si son etat est valide */
                if (contratCpt.getCodEtatCcpt().equals(Constants.COD_ETAT_CPT_VALID)) {
                    souscriptionContratPlacementForm.setContratCpt(contratCpt);
                    souscriptionContratPlacementForm.getContratView().setContratCpt(contratCpt);
                    souscriptionContratPlacementForm.setSoldeFinal(souscriptionContratPlacementForm.getContratView().getMontSoldFCcpt());
                    //Long.parseLong(souscriptionContratPlacementForm.getContratPlacementView().getMontCapCpla())
                    souscriptionContratPlacementForm.setTypePersonne(contratCpt.getClient().getTypePers().getCodTperTper());
                    souscriptionContratPlacementForm.setAlertContrat("contratValide");
                    if (souscriptionContratPlacementForm.getTypeForm().equals("Creation")) {
                        souscriptionContratPlacementForm.getDemandeDecisionView().setNumNpceDemd(contratCpt.getClient().getPersonne().getNumPcePers());
                        souscriptionContratPlacementForm.getDemandeDecisionView().setCodTpceTpce(contratCpt.getClient().getPersonne().getTypePiece().getCodTpceTpce().toString());
                        souscriptionContratPlacementForm.getDemandeDecisionView().setNomNomDemd(contratCpt.getClient().getPersonne().getNomNomPers());
                        souscriptionContratPlacementForm.getDemandeDecisionView().setNomPrnDemd(contratCpt.getClient().getPersonne().getNomPrnPers());
                        if (contratCpt.getClient().getTypePers().getCodTperTper().equals(Constants.PERSMORALE)) {
                            souscriptionContratPlacementForm.getDemandeDecisionView().setMatriculeFiscal(contratCpt.getClient().getNumFiscClt());
                            if (souscriptionContratPlacementForm.getDemandeDecisionView().getMatriculeFiscal() == 
                                null || 
                                souscriptionContratPlacementForm.getDemandeDecisionView().getMatriculeFiscal().equals(""))
                                souscriptionContratPlacementForm.setAlertMatriculeFiscale("Remarque : Matricule fiscale non garnie pour ce client...");
                        }

                        String cleContrat = 
                            StrHandler.lpad(contratView.getCodStrcStrc().toString(), 
                                            '0', 3) + 
                            StrHandler.lpad(contratView.getCodPrdPrd().toString(), 
                                            '0', 4) + 
                            StrHandler.lpad(contratView.getNumCcptCcpt().toString(), 
                                            '0', 6);

                        ContratCptView contratCptView = new ContratCptView();
                        contratCptView.setCleContrat(cleContrat);
                        contratCptView.setDateContrat(DateHandler.dateToStr(contratCpt.getDatOuvCcpt()));
                        contratCptView.setNumeroCompte(StrHandler.lpad(contratCpt.getContratCptId().getNumCcptCcpt().toString(), 
                                                                       '0', 
                                                                       6));
                        contratCptView.setCodeAgence(StrHandler.lpad(contratCpt.getContratCptId().getCodStrcStrc().toString(), 
                                                                     '0', 3));
                        contratCptView.setCodeProduit(StrHandler.lpad(contratCpt.getContratCptId().getCodPrdPrd().toString(), 
                                                                      '0', 4));
                        contratCptView.setSolde(StrHandler.formatmnt(Math.abs(contratCpt.getMontSoldCcpt().doubleValue())));
                        contratCptView.setContratCpt(contratCpt);
                        if (contratCpt.getMontSoldCcpt() > 0)
                            contratCptView.setSens("CR");
                        else
                            contratCptView.setSens("DB");
                        listeDesContratsView.add(contratCptView);

                        souscriptionContratPlacementForm.setListContratCpt(listeDesContratsView);

                    }
                } else {
                    souscriptionContratPlacementForm.setAlertContrat("ContratNonvalide");
                    souscriptionContratPlacementForm.setEtatContrat(contratCpt.getCodEtatCcpt());
                }
            } else {
                souscriptionContratPlacementForm.setAlertContrat("contratInexistant");
            }
            
            String forward ="";
            if(souscriptionContratPlacementForm.getCodSbdvDemd().equals("1")){
                // demande sous bonne date valeur
                forward = "demandeDecisionPlacementSBDV";
            }else forward = "demandeDecisionPlacement";
            
            return mapping.findForward(forward);


        } catch (Exception e) {
            com.oxia.fwk.core.Error erreur = new com.oxia.fwk.core.Error();
            StringBuffer text = 
                new StringBuffer("La recherche du contrat compte a été interrompue, veuillez transmettre ce message à l'équipe informatique: ");
            text.append("Exception au niveau de l'agence:");
            text.append(souscriptionContratPlacementForm.getInitialisationView().getCodeAgence());
            text.append(". Exception :");
            text.append(e.toString());
            erreur.setCode("200");
            erreur.setDescription(text.toString());
            logger.error(text.toString(), e);
            ActionMessage actionMessage = 
                new ActionMessage("exception.generique", 
                                  erreur.getDescription());
            actionMessages.add("Erreur ", actionMessage);
            this.saveMessages(request, actionMessages);
            return mapping.findForward("error");
        }

    }

    /**
     * Action qui renvoi vers la page JSP : souscriptionContratPlacement.jsp
     * vérifier Pouvoir (Mandataire/Titulaire ...)
     */
    public

    ActionForward chargerPouvoir(ActionMapping mapping, ActionForm form, 
                                 HttpServletRequest request, 
                                 HttpServletResponse response) throws IOException, 
                                                                      ServletException {
        Context context = ContextHandler.getContext();
        SouscriptionContratPlacementForm souscriptionContratPlacementForm = 
            (SouscriptionContratPlacementForm)form;
        ActionMessages actionMessages = new ActionMessages();
        Long[] produitDinConvertible = new Long[9];
        produitDinConvertible[0] = Long.valueOf(155);
        produitDinConvertible[1] = Long.valueOf(167);
        produitDinConvertible[2] = Long.valueOf(171);
        produitDinConvertible[3] = Long.valueOf(183);
        produitDinConvertible[4] = Long.valueOf(193);
        produitDinConvertible[5] = Long.valueOf(195);
        produitDinConvertible[6] = Long.valueOf(116);
        produitDinConvertible[7] = Long.valueOf(131);

        PlacementDAO plcDao = (PlacementDAO)context.getBean("placementDAO");
        
        try {
            souscriptionContratPlacementForm.setBoolTauxIrc("true");
            GetPersonneByNumSeqPersCmd getPersonneByNumSeqPersCmd = 
                new GetPersonneByNumSeqPersCmd();
            PersonneDemandeur personneDemandeur = 
                souscriptionContratPlacementForm.getPersonneDemandeur();
            Pouvoir pouvoir = 
                (Pouvoir)request.getSession().getAttribute("pouvoir");
            if (!pouvoir.equals(null)) {
                souscriptionContratPlacementForm.setPouvoir(pouvoir);
                souscriptionContratPlacementForm.setTypeDemandeur(pouvoir.getTypePouvoir());
                personneDemandeur = pouvoir.chargerPouvoir(personneDemandeur);
                if (pouvoir.getCodPieceAnnexe() != null && 
                    pouvoir.getNumPieceAnnexe() != null) {
                    souscriptionContratPlacementForm.getPersonneDemandeur().setCodTpceTpceDemandeur(pouvoir.getCodPieceAnnexe());
                    souscriptionContratPlacementForm.getPersonneDemandeur().setNumPcePersDemandeur(pouvoir.getNumPieceAnnexe());
                }

                if (personneDemandeur.getTypePouvoir().equals("N") || 
                    personneDemandeur.getTypePouvoir().equals("D") ||
                    personneDemandeur.getTypePouvoir().equals("")){
                    souscriptionContratPlacementForm.getPouvoir().setTypePouvoir("TR");
                    souscriptionContratPlacementForm.setAlertDemandeur("pouvoirInvalide");
                }else if(personneDemandeur.getTypePouvoir().equals("I")){
                    //demandeur incapable
                     souscriptionContratPlacementForm.setAlertDemandeur("demandeurIncapable");
                } else
                    souscriptionContratPlacementForm.setAlertDemandeur("pouvoirValide");

                if (pouvoir.getDemandeur().getNumSeqPers() != null) {
                    Personne pers = new Personne();
                    pers.setNumSeqPers(pouvoir.getDemandeur().getNumSeqPers());
                    pers = (Personne)getPersonneByNumSeqPersCmd.execute(pers);

                  /*  if (pers.getCategoriePersonne().getCodCatpCatp().equals(Constants.COD_CATEGORIE_P_TUN_INC)) {
                        // vérifier si le demandeur n'est pas incapable
                        souscriptionContratPlacementForm.setAlertDemandeur("demandeurIncapable");
                    }*/

                    //mettre  le taux IRC à 0 pour les comptes en dinars convertibles
                    for (int i = 0; i < produitDinConvertible.length; i++) {
                        if (Long.valueOf(souscriptionContratPlacementForm.getContratView().getCodPrdPrd()).equals(produitDinConvertible[i])) {
                            souscriptionContratPlacementForm.setBoolTauxIrc("false");
                            break;
                        }
                    }


                }
                // charger la liste des produits placements... 
                if(!souscriptionContratPlacementForm.getCodSbdvDemd().equals("1")){
                    Listes listePrd = new Listes();
                    listePrd.setList(plcDao.getListProduitPlacement(Long.valueOf(souscriptionContratPlacementForm.getContratView().getCodPrdPrd())));
                    souscriptionContratPlacementForm.setListeProduitsPlacement(listePrd.getList());
                }

            } else {
                souscriptionContratPlacementForm.setAlertPouvoir("false");
            }
            String forward = "";
            if (souscriptionContratPlacementForm.getTypeForm().equals("Creation")) {
                
                if(souscriptionContratPlacementForm.getCodSbdvDemd().equals("1")){
                    // demande sous bonne date valeur
                    forward = "demandeDecisionPlacementSBDV";
                }else forward = "demandeDecisionPlacement"; 
            
            } else
                forward = "listeDemandesDecision";

            return mapping.findForward(forward);

        } catch (Exception e) {
            com.oxia.fwk.core.Error erreur = new com.oxia.fwk.core.Error();
            StringBuffer text = 
                new StringBuffer("veuillez transmettre ce message à l'équipe informatique: ");
            text.append("Exception au niveau de l'agence:");
            text.append(souscriptionContratPlacementForm.getInitialisationView().getCodeAgence());
            text.append(". Exception :");
            text.append(e.toString());
            erreur.setCode("200");
            erreur.setDescription(text.toString());
            logger.error(text.toString(), e);
            ActionMessage actionMessage = 
                new ActionMessage("exception.generique", 
                                  erreur.getDescription());
            actionMessages.add("Erreur ", actionMessage);
            this.saveMessages(request, actionMessages);
            return mapping.findForward("error");
        }
    }


    public ActionForward rejeterDemandePlacement(ActionMapping mapping, 
                                                 ActionForm form, 
                                                 HttpServletRequest request, 
                                                 HttpServletResponse response) throws IOException, 
                                                                                      ServletException {
        SouscriptionContratPlacementForm souscriptionContratPlacementForm = 
            (SouscriptionContratPlacementForm)form;
        ActionMessages actionMessages = new ActionMessages();
        Context context = ContextHandler.getContext();
        try {
            ParamAgence paramAgence = 
                (ParamAgence)request.getSession().getAttribute("paramAgBNA");
            DemandeDecision demandeDecision = new DemandeDecision();
            demandeDecision = souscriptionContratPlacementForm.getDemandeDecision();
            demandeDecision.setCodEtatDemd(Constants.ETAT_DEM_DECIS_REJETEE);
            demandeDecision.setLibRmqDemd(souscriptionContratPlacementForm.getLibRmqDemd());
            demandeDecision.setDatRejDemd(DateHandler.strToDate(paramAgence.getDateComptable()));
            ValiderMajDdeDecisionCmd validerMajDdeDecisionCmd = 
                new ValiderMajDdeDecisionCmd();
            demandeDecision = 
                    (DemandeDecision)validerMajDdeDecisionCmd.execute(demandeDecision);
            souscriptionContratPlacementForm.clearFormRechercheDemandeDecision();
            return mapping.findForward("listeDemandesDecision");
        } catch (Exception e) {
            com.oxia.fwk.core.Error erreur = new com.oxia.fwk.core.Error();
            StringBuffer text = 
                new StringBuffer("veuillez transmettre ce message à l'équipe informatique: ");
            text.append("Exception au niveau de l'agence:");
            text.append(souscriptionContratPlacementForm.getInitialisationView().getCodeAgence());
            text.append(". Exception :");
            text.append(e.toString());
            erreur.setCode("200");
            erreur.setDescription(text.toString());
            logger.error(text.toString(), e);
            ActionMessage actionMessage = 
                new ActionMessage("exception.generique", 
                                  erreur.getDescription());
            actionMessages.add("Erreur ", actionMessage);
            this.saveMessages(request, actionMessages);
            return mapping.findForward("error");
        }

    }


    public ActionForward traiterDemandePlacement(ActionMapping mapping, 
                                                 ActionForm form, 
                                                 HttpServletRequest request, 
                                                 HttpServletResponse response) throws IOException, 
                                                                                      ServletException {
        SouscriptionContratPlacementForm souscriptionContratPlacementForm = 
            (SouscriptionContratPlacementForm)form;
        ActionMessages actionMessages = new ActionMessages();
        Context context = ContextHandler.getContext();
        try {
            ParamAgence paramAgence = 
                (ParamAgence)request.getSession().getAttribute("paramAgBNA");
            DemandeDecision demandeDecision = new DemandeDecision();
            demandeDecision = 
                    souscriptionContratPlacementForm.getDemandeDecision();
            demandeDecision.setCodEtatDemd(souscriptionContratPlacementForm.getDemandeDecisionView().getCodEtatDemd());
            if (souscriptionContratPlacementForm.getTypeForm().equals("Validation")){
                // cas de validation par le chef d'agence
                if (demandeDecision.getCodFavDemd().equals("F") || 
                    demandeDecision.getCodFavDemd().equals("I")) {
                    // demande sera acheminée vers la direction de la trésorerie
                    demandeDecision.getStructure().setCodStrcStrc(Constants.COD_DIR_TRESORERIE);
                } else {
                    // demande sera traitée automatiquement niveau agence 
                    demandeDecision.getStructure().setCodStrcStrc(Long.valueOf(souscriptionContratPlacementForm.getInitialisationView().getCodeAgence()));
                    
                }
            }

            if (souscriptionContratPlacementForm.getTypeForm().equals("etude") || 
                souscriptionContratPlacementForm.getTypeForm().equals("validationEtude") || 
                souscriptionContratPlacementForm.getTypeForm().equals("RecepNotif")) {
                // cas d'etude et validation par la direction de trésorerie
                if (demandeDecision.getCodFavDemd().equals("F") || 
                    demandeDecision.getCodFavDemd().equals("I")) {
                    // taux de faveur Fixe / Indexé
                    demandeDecision.setNumToffDemd(Double.valueOf(souscriptionContratPlacementForm.getDemandeDecisionView().getTauxAccorde()));                  
                }
                if (souscriptionContratPlacementForm.getDemandeDecisionView().getCodeTypeFaveur().equals("I")) {
                  demandeDecision.setCodMaroDemd(souscriptionContratPlacementForm.getDemandeDecisionView().getSignMargeOffert());
                }
                
                if (souscriptionContratPlacementForm.getTypeForm().equals("validationEtude")) {
                    // demande sera traitée automatiquement niveau agence que ce soit pour notification OU souscription placement
                    demandeDecision.getStructure().setCodStrcStrc(Long.valueOf(souscriptionContratPlacementForm.getInitialisationView().getCodeAgence()));
                }

                if (souscriptionContratPlacementForm.getTypeForm().equals("etude")) {
                    if (!souscriptionContratPlacementForm.getDemandeDecisionView().getNumRcbDemd().equals("")) {
                        demandeDecision.setNumRcbDemd(Long.valueOf(souscriptionContratPlacementForm.getDemandeDecisionView().getNumRcbDemd()));
                    }
                }

                if (souscriptionContratPlacementForm.getTypeForm().equals("RecepNotif")) {
                    if (!souscriptionContratPlacementForm.getDemandeDecisionView().getNumRcbDemd().equals("")) {
                        demandeDecision.setNumRcbDemd(Long.valueOf(souscriptionContratPlacementForm.getDemandeDecisionView().getNumRcbDemd()));
                        if (demandeDecision.getCodFavDemd().equals("F")) {
                            // taux Fixe
                            demandeDecision.setNumToffDemd(Double.valueOf(souscriptionContratPlacementForm.getDemandeDecisionView().getTauxInteret()));
                        }
                        if (demandeDecision.getCodFavDemd().equals("I")){
                            //taux indéxé sur TMM
                            demandeDecision.setNumToffDemd(Double.valueOf(souscriptionContratPlacementForm.getMarge()));
                            demandeDecision.setCodMargDemd(souscriptionContratPlacementForm.getSigne());
                            demandeDecision.setCodMaroDemd(souscriptionContratPlacementForm.getDemandeDecisionView().getSignMargeOffert());
                        }
                        //demandeDecision.setNumToffDemd(Double.valueOf(souscriptionContratPlacementForm.getDemandeDecisionView().getTauxInteret()));
                        //demandeDecision.setDatValDemd(DateHandler.strToDate(souscriptionContratPlacementForm.getDemandeDecisionView().getDateValeur()));
                    }
                    demandeDecision.getStructure().setCodStrcStrc(Long.valueOf(souscriptionContratPlacementForm.getInitialisationView().getCodeAgence()));
                }

            }

            if (souscriptionContratPlacementForm.getTypeForm().equals("notification")) {
                
                if(souscriptionContratPlacementForm.getDemandeDecisionView().getCodEtatDemd().equals("V")){                
                   // etat = 'V' , la demande est valide est prête pour la souscription contrat placement.
                    demandeDecision.setNumToffDemd(demandeDecision.getNumTaugDemd());
                    demandeDecision.setCodFavDemd("G");
                    demandeDecision.setLibRmqDemd("la direction de la trésorerie n'as pas accpété de donner un taux de faveur ==> taux général");
                    demandeDecision.getStructure().setCodStrcStrc(Long.valueOf(souscriptionContratPlacementForm.getInitialisationView().getCodeAgence()));
                }else {
                    // etat = 'N'; cas de notification et envoi vers direction tresorerie pour la saisie de la CB
                   demandeDecision.getStructure().setCodStrcStrc(Constants.COD_DIR_TRESORERIE);
                }
            }
            if(!demandeDecision.getCodFavDemd().equals("G")){
              demandeDecision.setLibRmqDemd(souscriptionContratPlacementForm.getLibRmqDemd() + 
                                          "   " + 
                                          souscriptionContratPlacementForm.getLibAjoutRmq());
            }
            // a chaque mise à jour, on garnie DAT_VLD_DEMD par la date comptable                                          
            demandeDecision.setDatVldDemd(DateHandler.strToDate(paramAgence.getDateComptable()));
            ValiderMajDdeDecisionCmd validerMajDdeDecisionCmd = new ValiderMajDdeDecisionCmd();
            demandeDecision = (DemandeDecision)validerMajDdeDecisionCmd.execute(demandeDecision);
            souscriptionContratPlacementForm.clearFormRechercheDemandeDecisionSansParam();

            return mapping.findForward("listeDemandesDecision");
        } catch (Exception e) {
            com.oxia.fwk.core.Error erreur = new com.oxia.fwk.core.Error();
            StringBuffer text = 
                new StringBuffer("veuillez transmettre ce message à l'équipe informatique: ");
            text.append("Exception au niveau de l'agence:");
            text.append(souscriptionContratPlacementForm.getInitialisationView().getCodeAgence());
            text.append(". Exception :");
            text.append(e.toString());
            erreur.setCode("200");
            erreur.setDescription(text.toString());
            logger.error(text.toString(), e);
            ActionMessage actionMessage = 
                new ActionMessage("exception.generique", 
                                  erreur.getDescription());
            actionMessages.add("Erreur ", actionMessage);
            this.saveMessages(request, actionMessages);
            return mapping.findForward("error");
        }

    }


    public ActionForward initierSimulation(ActionMapping mapping, 
                                           ActionForm form, 
                                           HttpServletRequest request, 
                                           HttpServletResponse response) throws IOException, 
                                                                                ServletException {
        SouscriptionContratPlacementForm souscriptionContratPlacementForm = 
            (SouscriptionContratPlacementForm)form;
        ActionMessages actionMessages = new ActionMessages();
        Context context = ContextHandler.getContext();
        try {
            souscriptionContratPlacementForm.clearFormDemandeDecision();
            ParamAgence paramAgence = 
                (ParamAgence)request.getSession().getAttribute("paramAgBNA");
            souscriptionContratPlacementForm.getDemandeDecisionView().setDateValeur(paramAgence.getDateComptable());

            return mapping.findForward("simulation");

        } catch (Exception e) {
            com.oxia.fwk.core.Error erreur = new com.oxia.fwk.core.Error();
            StringBuffer text = 
                new StringBuffer("veuillez transmettre ce message à l'équipe informatique: ");
            text.append("Exception au niveau de l'agence:");
            text.append(souscriptionContratPlacementForm.getInitialisationView().getCodeAgence());
            text.append(". Exception :");
            text.append(e.toString());
            erreur.setCode("200");
            erreur.setDescription(text.toString());
            logger.error(text.toString(), e);
            ActionMessage actionMessage = 
                new ActionMessage("exception.generique", 
                                  erreur.getDescription());
            actionMessages.add("Erreur ", actionMessage);
            this.saveMessages(request, actionMessages);
            return mapping.findForward("error");
        }

    }

    public void imprimerDemandeFaveur(HttpServletRequest request,ActionForm form, DemandeDecision demandeDecision) {

        SouscriptionContratPlacementForm souscriptionContratPlacementForm = (SouscriptionContratPlacementForm)form;
        try {

            ParamAgence paramAgence = 
                (ParamAgence)request.getSession().getAttribute("paramAgBNA");
            
            CommonReportVO valueObject = new CommonReportVO();
            Map parameters = new HashMap();
            String libParametre;
            String valParametre = "";
            String pLibEtat = "P_LIB_ETAT";
            String pMatrUser = "P_NUM_MATR_USER";
            String vMatrUser = paramAgence.getNumMatrUser().toString();
            parameters.put(pMatrUser, vMatrUser);

            StringBuffer txtLibEtat = new StringBuffer("");
            if (souscriptionContratPlacementForm.getDemandeDecisionView().getTauxDemande() != null && !souscriptionContratPlacementForm.getDemandeDecisionView().getTauxDemande().equals("")){
                txtLibEtat = new StringBuffer("DEMANDE DE PLACEMENT AVEC UN TAUX DE FAVEUR ");                        
                
                          
            }else txtLibEtat = new StringBuffer("DEMANDE DE PLACEMENT ");
            
            if(souscriptionContratPlacementForm.getTypeForm().equalsIgnoreCase("validationEtude"))  {
                txtLibEtat = 
                        new StringBuffer("( TRESORERIE ) DEMANDE DE PLACEMENT AVEC UN TAUX DE FAVEUR  ");       
            }
            
            if(demandeDecision.getCodSbdvDemd() != null && demandeDecision.getCodSbdvDemd().equals("1")){
                txtLibEtat.append(" ( SOUS BONNE DATE VALEUR ) ");
            }
            
            StringBuffer txtNomFichJasper = 
                new StringBuffer(File.separatorChar);
            txtNomFichJasper.append("Placement");
            txtNomFichJasper.append(File.separatorChar);
            if (demandeDecision != null) {
                if (demandeDecision.getNumRefdDemd() != null) {
                    libParametre = "P_NUM_REFD_DEMD";
                    valParametre = demandeDecision.getNumRefdDemd().toString();
                    parameters.put(libParametre, valParametre);
                }

            }
            if (souscriptionContratPlacementForm.getDemandeDecisionView() != 
                null) {
                if (souscriptionContratPlacementForm.getDemandeDecisionView().getDateEch() != 
                    null) {
                    libParametre = "DAT_ECHEANCE";
                    valParametre = 
                            souscriptionContratPlacementForm.getDemandeDecisionView().getDateEch();
                    parameters.put(libParametre, valParametre);
                }

                if (souscriptionContratPlacementForm.getDemandeDecisionView().getDateValeur() != 
                    null) {
                    libParametre = "DAT_VALEUR";
                    valParametre = 
                            DateHandler.dateToStr(demandeDecision.getDatValDemd());
                    parameters.put(libParametre, valParametre);
                }

                if (souscriptionContratPlacementForm.getDemandeDecisionView().getNumDureDemd() != 
                    null) {
                    libParametre = "DUREE";
                    valParametre = 
                            souscriptionContratPlacementForm.getDemandeDecisionView().getNumDureDemd();
                    parameters.put(libParametre, valParametre);
                }

                if (souscriptionContratPlacementForm.getDemandeDecisionView().getMontPlaDemd() != 
                    null) {
                    libParametre = "MONT_DEMD";
                    valParametre = 
                            souscriptionContratPlacementForm.getDemandeDecisionView().getMontPlaDemd();
                    parameters.put(libParametre, valParametre);
                }

                if (souscriptionContratPlacementForm.getDemandeDecisionView().getCodNdemDemd() != 
                    null) {
                    libParametre = "NATUR_DEMD";
                    if (souscriptionContratPlacementForm.getDemandeDecisionView().getCodNdemDemd().equals("S")) {
                        valParametre = "Une souscription en ";
                    } else if (souscriptionContratPlacementForm.getDemandeDecisionView().getCodNdemDemd().equals("R")) {
                        valParametre = "Un renouvellement en ";
                    } else if (souscriptionContratPlacementForm.getDemandeDecisionView().getCodNdemDemd().equals("C")) {
                        valParametre = "Une cession en ";
                    }

                    parameters.put(libParametre, valParametre);
                }

                if (souscriptionContratPlacementForm.getLibRmqDemd() != null) {
                    libParametre = "REMARQ";
                    if (souscriptionContratPlacementForm.getLibRmqDemd().equals("veuillez saisir vos remarques dans ce champs...")) {
                        valParametre = "";
                    } else {
                        valParametre = 
                                souscriptionContratPlacementForm.getLibRmqDemd();
                    }

                    parameters.put(libParametre, valParametre);
                }

                if (souscriptionContratPlacementForm.getDemandeDecisionView().getCodTypPaiement() != 
                    null) {
                    libParametre = "TYP_INT";
                    if (souscriptionContratPlacementForm.getDemandeDecisionView().getCodTypPaiement().equals("PRE")) {
                        valParametre = "l'avance";
                    } else if (souscriptionContratPlacementForm.getDemandeDecisionView().getCodTypPaiement().equals("POST")) {
                        valParametre = "terme échu";
                    }

                    parameters.put(libParametre, valParametre);
                }

                if (souscriptionContratPlacementForm.getDemandeDecisionView().getTauxGeneral() != null) {
                    libParametre = "TAUX_G";                   
                        valParametre = 
                                souscriptionContratPlacementForm.getDemandeDecisionView().getTauxGeneral();
                        parameters.put(libParametre, valParametre);                        
                    
                }

                if (souscriptionContratPlacementForm.getLibAbrPlc() != null) {
                    libParametre = "LIB_PRD";
                    valParametre = souscriptionContratPlacementForm.getLibAbrPlc();
                    
                    parameters.put(libParametre, valParametre);
                }
            }

            if (souscriptionContratPlacementForm.getContratView() != null) {
                if (souscriptionContratPlacementForm.getContratView().getCodStrcStrc() != 
                    null) {
                    libParametre = "STRC_CPT";
                    valParametre = 
                            souscriptionContratPlacementForm.getContratView().getCodStrcStrc();
                    parameters.put(libParametre, valParametre);
                }
                if (souscriptionContratPlacementForm.getContratView().getCodPrdPrd() != 
                    null) {
                    libParametre = "PRD_CPT";
                    valParametre = 
                            souscriptionContratPlacementForm.getContratView().getCodPrdPrd();
                    parameters.put(libParametre, valParametre);
                }
                if (souscriptionContratPlacementForm.getContratView().getNumCcptCcpt() != 
                    null) {
                    libParametre = "CCPT_CPT";
                    valParametre = 
                            souscriptionContratPlacementForm.getContratView().getNumCcptCcpt();
                    parameters.put(libParametre, valParametre);
                }

            }

            if (souscriptionContratPlacementForm.getDemandeDecisionView().getCodeTypeFaveur().equals("F")) {
                // taux Fixe
                if (souscriptionContratPlacementForm.getDemandeDecisionView().getTauxDemande() != 
                    null) {
                    libParametre = "TAUX_I";
                    
                    if(souscriptionContratPlacementForm.getTypeForm().equalsIgnoreCase("Creation"))
                        valParametre = 
                                souscriptionContratPlacementForm.getDemandeDecisionView().getTauxDemande();
                    else if(souscriptionContratPlacementForm.getTypeForm().equalsIgnoreCase("validationEtude"))  
                        valParametre = souscriptionContratPlacementForm.getDemandeDecisionView().getTauxAccorde();
                    
                    parameters.put(libParametre, valParametre);

                    libParametre = "Type_Faveur";
                    valParametre = "Taux Fixe";
                    parameters.put(libParametre, valParametre);

                }
            } else if (souscriptionContratPlacementForm.getDemandeDecisionView().getCodeTypeFaveur().equals("I")) {
                // taux Indexé sur le TMM
                if (souscriptionContratPlacementForm.getDemandeDecisionView().getTauxDemande() != 
                    null) {
                    libParametre = "TAUX_I";
                    String tauxAcc = "";
                    if(souscriptionContratPlacementForm.getTypeForm().equalsIgnoreCase("Creation"))
                     tauxAcc= souscriptionContratPlacementForm.getDemandeDecisionView().getTauxMarge();
                    else if(souscriptionContratPlacementForm.getTypeForm().equalsIgnoreCase("validationEtude"))  
                        tauxAcc= souscriptionContratPlacementForm.getDemandeDecisionView().getTauxMarge();
                        
                    valParametre = 
                            "TMM " + souscriptionContratPlacementForm.getDemandeDecisionView().getSignMarge() + 
                            " " + tauxAcc;
                            
                    parameters.put(libParametre, valParametre);

                    libParametre = "Type_Faveur";
                    valParametre = "Taux Indexé";
                    parameters.put(libParametre, valParametre);
                }

            }

            String vLibEtat;
            vLibEtat = txtLibEtat.toString();
            parameters.put(pLibEtat, vLibEtat);

            valueObject.setParams(parameters);
            parameters = null;
            // indiquer le nom du fichier jasper  
            if (souscriptionContratPlacementForm.getDemandeDecisionView().getCodeTypeFaveur().equals("I") || 
                souscriptionContratPlacementForm.getDemandeDecisionView().getCodeTypeFaveur().equals("F")) {
                txtNomFichJasper.append("demandeFaveur");
            } else {
                txtNomFichJasper.append("demandeGeneral");
            }
            valueObject.setNomReport(txtNomFichJasper.toString());
            request.getSession().setAttribute("CommonPrintVo", valueObject);
            request.setAttribute("print", "1");
            
            /*String forward = "";
            
            if(souscriptionContratPlacementForm.getTypeForm().equalsIgnoreCase("Creation")){
                if(souscriptionContratPlacementForm.getCodSbdvDemd().equals("1")){
                    // demande sous bonne date valeur
                    forward = "demandeDecisionPlacementSBDV";
                }else forward = "demandeDecisionPlacement";                

            }else if(souscriptionContratPlacementForm.getTypeForm().equalsIgnoreCase("validationEtude")) 
                forward = "listeDemandesDecision";
            */
            
        }catch (Exception e) {
             com.oxia.fwk.core.Error erreur = new com.oxia.fwk.core.Error();
             StringBuffer text = new StringBuffer("");
             text.append(e.getMessage());
             erreur.setCode("200");
             erreur.setDescription(text.toString());
             ActionMessage actionMessage =  new ActionMessage("exception.generique", erreur.getDescription());
          }          

    }


    public ActionForward genererAlertePlacement(ActionMapping mapping, 
                                                ActionForm form, 
                                                HttpServletRequest request, 
                                                HttpServletResponse response) throws IOException, 
                                                                                     ServletException {
        SouscriptionContratPlacementForm souscriptionContratPlacementForm = 
            (SouscriptionContratPlacementForm)form;
        ActionMessages actionMessages = new ActionMessages();
        Context context = ContextHandler.getContext();
        try {
            souscriptionContratPlacementForm.clearFormDemandeDecision();
            return mapping.findForward("alertePlacement");

        } catch (Exception e) {
            com.oxia.fwk.core.Error erreur = new com.oxia.fwk.core.Error();
            StringBuffer text = 
                new StringBuffer("veuillez transmettre ce message à l'équipe informatique: ");
            text.append("Exception au niveau de l'agence:");
            text.append(souscriptionContratPlacementForm.getInitialisationView().getCodeAgence());
            text.append(". Exception :");
            text.append(e.toString());
            erreur.setCode("200");
            erreur.setDescription(text.toString());
            logger.error(text.toString(), e);
            ActionMessage actionMessage = 
                new ActionMessage("exception.generique", 
                                  erreur.getDescription());
            actionMessages.add("Erreur ", actionMessage);
            this.saveMessages(request, actionMessages);
            return mapping.findForward("error");
        }

    }


    public DemandeDecision affecterDonneesHistoriqueDemandeDecision(ActionForm form) {


        SouscriptionContratPlacementForm souscriptionContratPlacementForm = 
            (SouscriptionContratPlacementForm)form;

        DemandeDecision demandeDecision = new DemandeDecision();
        TypePiece typePiece = new TypePiece();
        Structure struct = new Structure();
        try {
            typePiece.setCodTpceTpce(Long.valueOf(souscriptionContratPlacementForm.getPersonneDemandeur().getCodTpceTpceDemandeur()));
            demandeDecision.setTypePiece(typePiece);
            demandeDecision.setNumNpceDemd(souscriptionContratPlacementForm.getPersonneDemandeur().getNumPcePersDemandeur());

            struct.setCodStrcStrc(Long.valueOf(souscriptionContratPlacementForm.getInitialisationView().getCodeAgence()));
            demandeDecision.setCodEtatDemd(Constants.ETAT_DEM_DECIS_SAISIE);
            demandeDecision.setStructure(struct);

            if ((!souscriptionContratPlacementForm.getDemandeDecisionView().getNumSeqCpla().equals(null) && 
                 !souscriptionContratPlacementForm.getDemandeDecisionView().getNumSeqCpla().equals(""))) {
                ContratPlacement contratPlacement = new ContratPlacement();
                contratPlacement.setNumSeqCpla(Long.valueOf(souscriptionContratPlacementForm.getDemandeDecisionView().getNumSeqCpla()));
                demandeDecision.setContratPlacement(contratPlacement);
            }

            if (!souscriptionContratPlacementForm.getDemandeDecisionView().getCodPrdPrd().equals("0")) {

                ProduitPlacement produitPlc = new ProduitPlacement();
                produitPlc.setCodPrdPlc(Long.valueOf(souscriptionContratPlacementForm.getDemandeDecisionView().getCodPrdPrd()));
                demandeDecision.setProduitPlacement(produitPlc);

            }

            if (!souscriptionContratPlacementForm.getContratView().getNumCcptCcpt().equals("") && 
                !souscriptionContratPlacementForm.getContratView().getCodPrdPrd().equals("") && 
                !souscriptionContratPlacementForm.getContratView().getCodStrcStrc().equals("")) {

                Long x = 
                    Long.valueOf(souscriptionContratPlacementForm.getContratView().getNumCcptCcpt());
                Long y = 
                    Long.valueOf(souscriptionContratPlacementForm.getContratView().getCodPrdPrd());
                Long z = 
                    Long.valueOf(souscriptionContratPlacementForm.getContratView().getCodStrcStrc());

                ContratCptId contratCptId = new ContratCptId();
                ContratCpt cpt = new ContratCpt();
                contratCptId.setNumCcptCcpt(x);
                contratCptId.setCodPrdPrd(y);
                contratCptId.setCodStrcStrc(z);

                cpt.setContratCptId(contratCptId);
                demandeDecision.setContratCpt(cpt);

            }

            demandeDecision.setMontPlaDemd(Long.valueOf(new Double(new Double(StrHandler.strWithoutBlanck(souscriptionContratPlacementForm.getDemandeDecisionView().getMontPlaDemd())).doubleValue() * 
                                                                   1000).longValue()));
            demandeDecision.setNumDureDemd(Long.valueOf(souscriptionContratPlacementForm.getDemandeDecisionView().getNumDureDemd()));
            demandeDecision.setCodNdemDemd(souscriptionContratPlacementForm.getDemandeDecisionView().getCodNdemDemd());
            demandeDecision.setBoolFaxDemd(Long.valueOf(souscriptionContratPlacementForm.getDemandeDecisionView().getPecFax()));
            demandeDecision.setNomNomDemd(souscriptionContratPlacementForm.getPersonneDemandeur().getNomNomPersDemandeur());
            demandeDecision.setNomPrnDemd(souscriptionContratPlacementForm.getPersonneDemandeur().getNomPrnPersDemandeur());
            demandeDecision.setCodTdemDemd(souscriptionContratPlacementForm.getDemandeDecisionView().getCodTdemDemd());
            demandeDecision.setDatCreDemd(DateHandler.strToDate(souscriptionContratPlacementForm.getDemandeDecisionView().getDatCreDemd()));
            demandeDecision.setNumTaugDemd(Double.valueOf(souscriptionContratPlacementForm.getDemandeDecisionView().getTauxGeneral()));
            demandeDecision.setLibRmqDemd(souscriptionContratPlacementForm.getLibRmqDemd());
            demandeDecision.setNumTircDemd(Double.valueOf(souscriptionContratPlacementForm.getDemandeDecisionView().getTauxIrc()));
            demandeDecision.setCodPintDemd(souscriptionContratPlacementForm.getDemandeDecisionView().getCodTypPaiement());
            if (!souscriptionContratPlacementForm.getDemandeDecisionView().getTauxDemande().equals("")) {
                demandeDecision.setNumTaudDemd(Double.valueOf(souscriptionContratPlacementForm.getDemandeDecisionView().getTauxDemande()));
                demandeDecision.setDatValDemd(DateHandler.strToDate(souscriptionContratPlacementForm.getDemandeDecisionView().getDateValeur()));
                demandeDecision.setDatLimDemd(DateHandler.strToDate(souscriptionContratPlacementForm.getDemandeDecisionView().getDateLimite()));
            }


        } catch (Exception e) {
            logger.error("Exception dans souscriptionContratPlacementAction / Methode : affecterDonnéesDemandeDecision:  ", 
                         e);
            throw new RuntimeException(e);
        }
        return demandeDecision;

    }


    public ActionForward imprimerLettreNotification(ActionMapping mapping, 
                                                    ActionForm form, 
                                                    HttpServletRequest request, 
                                                    HttpServletResponse response) throws IOException, 
                                                                                         ServletException {

        ActionMessages actionMessages = new ActionMessages();
        SouscriptionContratPlacementForm souscriptionContratPlacementForm = 
            (SouscriptionContratPlacementForm)form;
        try {
            ParamAgence paramAgence = 
                (ParamAgence)request.getSession().getAttribute("paramAgBNA");
            DemandeDecision demd = 
                souscriptionContratPlacementForm.getDemandeDecision();
            CommonReportVO valueObject = new CommonReportVO();
            Map parameters = new HashMap();
            String libParametre;
            String valParametre = "";
            String pLibEtat = "P_LIB_ETAT";
            String pMatrUser = "P_NUM_MATR_USER";
            String vMatrUser = paramAgence.getNumMatrUser().toString();
            parameters.put(pMatrUser, vMatrUser);

            StringBuffer txtLibEtat = 
                new StringBuffer("Lettre de Notification ");
            StringBuffer txtNomFichJasper = 
                new StringBuffer(File.separatorChar);
            txtNomFichJasper.append("Placement");
            txtNomFichJasper.append(File.separatorChar);
            if (demd != null) {
                if (demd.getNumRefdDemd() != null) {
                    libParametre = "P_NUM_REFD_DEMD";
                    valParametre = demd.getNumRefdDemd().toString();
                    parameters.put(libParametre, valParametre);
                }

            }
            if (souscriptionContratPlacementForm.getInitialisationView() != 
                null) {
                // Ajout du parametre matricule utilisateur
                libParametre = "P_NUM_MATR_USER";
                valParametre = 
                        souscriptionContratPlacementForm.getInitialisationView().getNumMatrUser();
                parameters.put(libParametre, valParametre);
            }

            String vLibEtat;
            vLibEtat = txtLibEtat.toString();
            parameters.put(pLibEtat, vLibEtat);

            valueObject.setParams(parameters);
            parameters = null;
            // indiquer le nom du fichier jasper  
            txtNomFichJasper.append("lettreNotification");
            valueObject.setNomReport(txtNomFichJasper.toString());
            request.getSession().setAttribute("CommonPrintVo", valueObject);
            request.setAttribute("print", "1");
            return mapping.findForward("listeDemandesDecision");
        } catch (Exception e) {
            com.oxia.fwk.core.Error erreur = new com.oxia.fwk.core.Error();
            StringBuffer text = 
                new StringBuffer("veuillez transmettre ce message à l'équipe informatique: ");
            text.append("Exception au niveau de l'agence:");
            text.append(souscriptionContratPlacementForm.getInitialisationView().getCodeAgence());
            text.append(". Exception :");
            text.append(e.toString());
            erreur.setCode("200");
            erreur.setDescription(text.toString());
            logger.error(text.toString(), e);
            ActionMessage actionMessage = 
                new ActionMessage("exception.generique", 
                                  erreur.getDescription());
            actionMessages.add("Erreur ", actionMessage);
            this.saveMessages(request, actionMessages);
            return mapping.findForward("error");
        }

    }


    public ActionForward imprimerDemandeFaveurTresorerie(ActionMapping mapping, 
                                                    ActionForm form, 
                                                    HttpServletRequest request, 
                                                    HttpServletResponse response) throws IOException, 
                                                                                         ServletException {

        SouscriptionContratPlacementForm souscriptionContratPlacementForm = (SouscriptionContratPlacementForm)form;
        try {

            ParamAgence paramAgence = 
                (ParamAgence)request.getSession().getAttribute("paramAgBNA");
            DemandeDecision demandeDecision = souscriptionContratPlacementForm.getDemandeDecision();
            CommonReportVO valueObject = new CommonReportVO();
            Map parameters = new HashMap();
            String libParametre;
            String libParametre1;
            String valParametre = "";
            String valParametre1 = "";
            String pLibEtat = "P_LIB_ETAT";
            String pMatrUser = "P_NUM_MATR_USER";
            String vMatrUser = paramAgence.getNumMatrUser().toString();
            parameters.put(pMatrUser, vMatrUser);

            StringBuffer txtLibEtat = new StringBuffer("");
            if (souscriptionContratPlacementForm.getDemandeDecisionView().getTauxDemande() != null && !souscriptionContratPlacementForm.getDemandeDecisionView().getTauxDemande().equals("")){
                txtLibEtat = new StringBuffer("DEMANDE DE PLACEMENT AVEC UN TAUX DE FAVEUR ");                        
                
                          
            }else txtLibEtat = new StringBuffer("DEMANDE DE PLACEMENT ");
            
            if(souscriptionContratPlacementForm.getTypeForm().equalsIgnoreCase("validationEtude") || souscriptionContratPlacementForm.getTypeForm().equalsIgnoreCase("etude") )  {
                txtLibEtat = 
                        new StringBuffer("( TRESORERIE ) DEMANDE DE PLACEMENT AVEC UN TAUX DE FAVEUR  ");       
            }
            
            if(demandeDecision.getCodSbdvDemd() != null && demandeDecision.getCodSbdvDemd().equals("1")){
                txtLibEtat.append(" ( SOUS BONNE DATE VALEUR ) ");
            }
            
            StringBuffer txtNomFichJasper = 
                new StringBuffer(File.separatorChar);
            txtNomFichJasper.append("Placement");
            txtNomFichJasper.append(File.separatorChar);
            if (demandeDecision != null) {
                if (demandeDecision.getNumRefdDemd() != null) {
                    libParametre = "P_NUM_REFD_DEMD";
                    valParametre = demandeDecision.getNumRefdDemd().toString();
                    parameters.put(libParametre, valParametre);
                }

            }
            if (souscriptionContratPlacementForm.getDemandeDecisionView() != 
                null) {
                if (souscriptionContratPlacementForm.getDemandeDecisionView().getDateEch() != 
                    null) {
                    libParametre = "DAT_ECHEANCE";
                    valParametre = 
                            souscriptionContratPlacementForm.getDemandeDecisionView().getDateEch();
                    parameters.put(libParametre, valParametre);
                }

                if (souscriptionContratPlacementForm.getDemandeDecisionView().getDateValeur() != 
                    null) {
                    libParametre = "DAT_VALEUR";
                    valParametre = 
                            DateHandler.dateToStr(demandeDecision.getDatValDemd());
                    parameters.put(libParametre, valParametre);
                }

                if (souscriptionContratPlacementForm.getDemandeDecisionView().getNumDureDemd() != 
                    null) {
                    libParametre = "DUREE";
                    valParametre = 
                            souscriptionContratPlacementForm.getDemandeDecisionView().getNumDureDemd();
                    parameters.put(libParametre, valParametre);
                }

                if (souscriptionContratPlacementForm.getDemandeDecisionView().getMontPlaDemd() != 
                    null) {
                    libParametre = "MONT_DEMD";
                    valParametre = 
                            souscriptionContratPlacementForm.getDemandeDecisionView().getMontPlaDemd();
                    parameters.put(libParametre, valParametre);
                }

                if (souscriptionContratPlacementForm.getDemandeDecisionView().getCodNdemDemd() != 
                    null) {
                    libParametre = "NATUR_DEMD";
                    if (souscriptionContratPlacementForm.getDemandeDecisionView().getCodNdemDemd().equals("S")) {
                        valParametre = "Une souscription en ";
                    } else if (souscriptionContratPlacementForm.getDemandeDecisionView().getCodNdemDemd().equals("R")) {
                        valParametre = "Un renouvellement en ";
                    } else if (souscriptionContratPlacementForm.getDemandeDecisionView().getCodNdemDemd().equals("C")) {
                        valParametre = "Une cession en ";
                    }

                    parameters.put(libParametre, valParametre);
                }

                if (souscriptionContratPlacementForm.getLibRmqDemd() != null) {
                    libParametre = "REMARQ";
                    if (souscriptionContratPlacementForm.getLibRmqDemd().equals("veuillez saisir vos remarques dans ce champs...")) {
                        valParametre = "";
                    } else {
                        valParametre = 
                                souscriptionContratPlacementForm.getLibRmqDemd();
                    }

                    parameters.put(libParametre, valParametre);
                }

                if (souscriptionContratPlacementForm.getDemandeDecisionView().getCodTypPaiement() != 
                    null) {
                    libParametre = "TYP_INT";
                    if (souscriptionContratPlacementForm.getDemandeDecisionView().getCodTypPaiement().equals("PRE")) {
                        valParametre = "l'avance";
                    } else if (souscriptionContratPlacementForm.getDemandeDecisionView().getCodTypPaiement().equals("POST")) {
                        valParametre = "terme échu";
                    }

                    parameters.put(libParametre, valParametre);
                }

                if (souscriptionContratPlacementForm.getDemandeDecisionView().getTauxGeneral() != null) {
                    libParametre = "TAUX_G";                   
                        valParametre = 
                                souscriptionContratPlacementForm.getDemandeDecisionView().getTauxGeneral();
                        parameters.put(libParametre, valParametre);                        
                    
                }

                if (souscriptionContratPlacementForm.getLibAbrPlc() != null) {
                    libParametre = "LIB_PRD";
                    valParametre = souscriptionContratPlacementForm.getLibAbrPlc();
                    
                    parameters.put(libParametre, valParametre);
                }
            }

            if (souscriptionContratPlacementForm.getContratView() != null) {
                if (souscriptionContratPlacementForm.getContratView().getCodStrcStrc() != 
                    null) {
                    libParametre = "STRC_CPT";
                    valParametre = 
                            souscriptionContratPlacementForm.getContratView().getCodStrcStrc();
                    parameters.put(libParametre, valParametre);
                }
                if (souscriptionContratPlacementForm.getContratView().getCodPrdPrd() != 
                    null) {
                    libParametre = "PRD_CPT";
                    valParametre = 
                            souscriptionContratPlacementForm.getContratView().getCodPrdPrd();
                    parameters.put(libParametre, valParametre);
                }
                if (souscriptionContratPlacementForm.getContratView().getNumCcptCcpt() != 
                    null) {
                    libParametre = "CCPT_CPT";
                    valParametre = 
                            souscriptionContratPlacementForm.getContratView().getNumCcptCcpt();
                    parameters.put(libParametre, valParametre);
                }

            }

            if (souscriptionContratPlacementForm.getDemandeDecisionView().getCodeTypeFaveur().equals("F")) {
                // taux Fixe
                if (souscriptionContratPlacementForm.getDemandeDecisionView().getTauxDemande() != 
                    null) {
                    libParametre = "TAUX_I";
                    libParametre1 = "TAUX_ACC";
                    
                    if(souscriptionContratPlacementForm.getTypeForm().equalsIgnoreCase("Creation"))
                        valParametre = 
                                souscriptionContratPlacementForm.getDemandeDecisionView().getTauxDemande();
                    
                    else if(souscriptionContratPlacementForm.getTypeForm().equalsIgnoreCase("etude") || souscriptionContratPlacementForm.getTypeForm().equalsIgnoreCase("validationEtude") ){  
                        valParametre = souscriptionContratPlacementForm.getDemandeDecisionView().getTauxDemande();
                        valParametre1 = souscriptionContratPlacementForm.getDemandeDecisionView().getTauxAccorde();
                    }
                    
                    parameters.put(libParametre, valParametre);                    
                    parameters.put(libParametre1, valParametre1);   

                    libParametre = "Type_Faveur";
                    valParametre = "Taux Fixe";
                    parameters.put(libParametre, valParametre);

                }
            } else if (souscriptionContratPlacementForm.getDemandeDecisionView().getCodeTypeFaveur().equals("I")) {
                // taux Indexé sur le TMM
                if (souscriptionContratPlacementForm.getDemandeDecisionView().getTauxDemande() != 
                    null) {
                    libParametre = "TAUX_I";
                    libParametre1 = "TAUX_ACC";
                    String tauxSolli = "";
                    String tauxAcc = "";
                    if(souscriptionContratPlacementForm.getTypeForm().equalsIgnoreCase("Creation"))
                     tauxAcc= souscriptionContratPlacementForm.getDemandeDecisionView().getTauxMarge();
                    else if(souscriptionContratPlacementForm.getTypeForm().equalsIgnoreCase("etude") || souscriptionContratPlacementForm.getTypeForm().equalsIgnoreCase("validationEtude"))  
                        tauxSolli= souscriptionContratPlacementForm.getDemandeDecisionView().getTauxMarge();
                        tauxAcc= souscriptionContratPlacementForm.getDemandeDecisionView().getTauxAccorde();
                        
                    valParametre = 
                            "TMM " + souscriptionContratPlacementForm.getDemandeDecisionView().getSignMarge() + 
                            " " + tauxSolli;
                    valParametre1 = 
                            "TMM " + souscriptionContratPlacementForm.getDemandeDecisionView().getSignMargeOffert()+ 
                            " " + tauxAcc;
                            
                    parameters.put(libParametre, valParametre);
                    parameters.put(libParametre1, valParametre1);

                    libParametre = "Type_Faveur";
                    valParametre = "Taux Indexé";
                    parameters.put(libParametre, valParametre);
                }

            }

            String vLibEtat;
            vLibEtat = txtLibEtat.toString();
            parameters.put(pLibEtat, vLibEtat);

            valueObject.setParams(parameters);
            parameters = null;
            // indiquer le nom du fichier jasper  
            if (souscriptionContratPlacementForm.getDemandeDecisionView().getCodeTypeFaveur().equals("I") || 
                souscriptionContratPlacementForm.getDemandeDecisionView().getCodeTypeFaveur().equals("F")) {
                if(souscriptionContratPlacementForm.getTypeForm().equalsIgnoreCase("etude") || souscriptionContratPlacementForm.getTypeForm().equalsIgnoreCase("validationEtude")  )  {
                   txtNomFichJasper.append("demandeFaveurTr");
                }else txtNomFichJasper.append("demandeFaveur");
            } else {
                txtNomFichJasper.append("demandeGeneral");
            }
            valueObject.setNomReport(txtNomFichJasper.toString());
            request.getSession().setAttribute("CommonPrintVo", valueObject);
            request.setAttribute("print", "1");
            
            return mapping.findForward("listeDemandesDecision");
            
        }catch (Exception e) {
             com.oxia.fwk.core.Error erreur = new com.oxia.fwk.core.Error();
             StringBuffer text = new StringBuffer("");
             text.append(e.getMessage());
             erreur.setCode("200");
             erreur.setDescription(text.toString());
             ActionMessage actionMessage =  new ActionMessage("exception.generique", erreur.getDescription());
              return mapping.findForward("error");
          }          

    }

}


