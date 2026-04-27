
package com.bna.smile.web.placement.actions;

import com.bna.commun.model.AbonnementPlacement;
import com.bna.commun.model.AvancRembLiquid;
import com.bna.commun.model.ContratCptId;
import com.bna.commun.model.ContratPlacement;
import com.bna.commun.model.DemandeDecision;
import com.bna.commun.model.DetailsBc;
import com.bna.commun.model.InteretServi;
import com.bna.commun.model.Personne;
import com.bna.commun.util.ContextHandler;
import com.bna.commun.util.DateHandler;
import com.bna.commun.util.StrHandler;
import com.bna.smile.model.constant.Constants;
import com.bna.smile.model.domainecommun.commande.GetListCategoriesPersonneCmd;
import com.bna.smile.model.domainecommun.commande.GetPersonneCmd;
import com.bna.smile.model.domainecommun.model.ContratPersonne;
import com.bna.smile.model.domainecommun.model.Listes;
import com.bna.smile.model.domainecommun.model.PersonneStrc;
import com.bna.smile.model.domaineplacement.commande.GetAvancRembLiquidByIdCmd;
import com.bna.smile.model.domaineplacement.commande.GetContratPlacementCmd;
import com.bna.smile.model.domaineplacement.commande.GetInteretServiByIdCmd;
import com.bna.smile.model.domaineplacement.commande.GetListAbonnementsInteretsCmd;
import com.bna.smile.model.domaineplacement.commande.GetListAvancRembLiquidByEtatCmd;
import com.bna.smile.model.domaineplacement.commande.GetListContratsPlacementCmd;
import com.bna.smile.model.domaineplacement.commande.GetListDemandesDecisionCmd;
import com.bna.smile.model.domaineplacement.commande.GetListInteretServiCmd;
import com.bna.smile.model.domaineplacement.commande.GetSituationMensInteretByCategCltCmd;
import com.bna.smile.model.domaineplacement.commande.GetSituationMensuelleByCategorieClientCmd;
import com.bna.smile.model.domaineplacement.commande.GetSituationMensuelleByClientCmd;
import com.bna.smile.model.domaineplacement.commande.GetSituationMensuelleByStructureCmd;
import com.bna.smile.model.domaineplacement.commande.GetSituationMensuelleIntByCltCmd;
import com.bna.smile.model.domaineplacement.commande.GetSituationMensuelleIntByStrcCmd;
import com.bna.smile.model.domaineplacement.commande.RecapMouvementPlacementCmd;
import com.bna.smile.model.domaineplacement.dao.PlacementDAO;
import com.bna.smile.model.domaineplacement.model.ParamAbonnementement;
import com.bna.smile.model.domaineplacement.model.ParamAvanRembLiq;
import com.bna.smile.model.domaineplacement.model.ParamDemandeDecision;
import com.bna.smile.model.domaineplacement.traitement.GetDetailBcTrt;
import com.bna.smile.model.reporting.model.CommonReportVO;
import com.bna.smile.web.commun.model.ParamAgence;
import com.bna.smile.web.placement.forms.AvancRembLiquidValidPlacementForm;
import com.bna.smile.web.placement.forms.ConsultationPlacementForm;
import com.bna.smile.web.placement.view.AbonnementPlacementView;
import com.bna.smile.web.placement.view.AvancRembLiquidView;
import com.bna.smile.web.placement.view.ContratPlacementView;
import com.bna.smile.web.placement.view.DemandeDecisionView;
import com.bna.smile.web.placement.view.InteretServiView;
import com.oxia.fwk.context.Context;
import java.io.File;
import java.io.IOException;

import java.sql.SQLException;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.collections.map.ListOrderedMap;
import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.actions.DispatchAction;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;


public class ConsultationPlacementAction extends DispatchAction {

    /**
     * <B> Action de la page consultationContratPlacement.jsp  </B>
     * @param mapping The ActionMapping used to select this instance.
     * @param form The optional ActionForm bean for this request.
     * @param request The HTTP Request we are processing.
     * @param response The HTTP Response we are processing.
     * Nom du package : com.bna.smile.web.placement.actions
     *
     * @version le 19/01/2007
     * @modify le 06/07/07
     */
    private static final

    Logger logger = Logger.getLogger(ConsultationPlacementAction.class);


    /**
     * Action qui renvoi vers la page JSP: /pageInitialePlacement.jsp
     */
    public

    ActionForward initierPage(ActionMapping mapping, ActionForm form, 
                              HttpServletRequest request, 
                              HttpServletResponse response) throws IOException, 
                                                                   ServletException {

        ActionMessages actionMessages = new ActionMessages();
        ConsultationPlacementForm consultationPlacementForm = 
            (ConsultationPlacementForm)form;
        Context context = ContextHandler.getContext();
        try {
            ParamAgence paramAgence = 
                (ParamAgence)request.getSession().getAttribute("paramAgBNA");
            //souscriptionContratPlacementForm.clearFormDemandeDecision();
            consultationPlacementForm.setLibelleOperation("CONSULTATION DEMANDE DE PLACEMENT");
            consultationPlacementForm.setChoix("2");
            consultationPlacementForm.clearFormRechercheDemandeDecision();
            consultationPlacementForm.getInitialisationView().setDateOp(DateHandler.strToDate(paramAgence.getDateJours()));
            consultationPlacementForm.getInitialisationView().setDateActuelle(paramAgence.getDateComptable());
            consultationPlacementForm.getInitialisationView().setCodeAgence(paramAgence.getCodStrcStrc().toString());
            consultationPlacementForm.getInitialisationView().setCodeOperation(Constants.COD_OPER_SOUSC_PLACEMENT.toString());
            consultationPlacementForm.setDateDebRecherch(paramAgence.getDateJours());
            consultationPlacementForm.setDateFinRecherch(paramAgence.getDateJours());

            if (!paramAgence.getCodStrcStrc().equals(Constants.COD_DIR_TRESORERIE)) {
                // code structure diff de la dir trésorerie
                if (paramAgence.getCodTstrcTstrc().equals(Long.valueOf("1"))) {
                    // agence
                    consultationPlacementForm.setCodTypeStructure("A");
                    consultationPlacementForm.setStructureRech(paramAgence.getCodStrcStrc().toString());
                } else if (paramAgence.getCodTstrcTstrc().equals(Long.valueOf("4")) || 
                           paramAgence.getCodTstrcTstrc().equals(Long.valueOf("2"))) {
                    // D.régionale
                    consultationPlacementForm.setCodTypeStructure("R");
                    // recherche de la liste des agences concernées
                    PlacementDAO plcDao = 
                        (PlacementDAO)context.getBean("placementDAO");
                    consultationPlacementForm.setListAgConcernees(plcDao.getListStructureConcernes(paramAgence.getCodStrcStrc()));
                }
            } else {
                // trésorerie
                consultationPlacementForm.setCodTypeStructure("C");
            }

            return mapping.findForward("initConsultationDemandePlacement");
        } catch (Exception e) {
            com.oxia.fwk.core.Error erreur = new com.oxia.fwk.core.Error();
            StringBuffer text = 
                new StringBuffer("L'initialisation du domaine Placement a été interrompue, Veuillez transmettre ce message à l'équipe informatique: ");
            text.append("Exception au niveau de l'agence:");
            text.append(consultationPlacementForm.getInitialisationView().getCodeAgence());
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
    public ActionForward initHistoriqueContratPLA(ActionMapping mapping, 
                                                     ActionForm form, 
                                                     HttpServletRequest request, 
                                                     HttpServletResponse response) throws IOException, 
                                                                                          ServletException {
        ActionMessages actionMessages = new ActionMessages();
        ConsultationPlacementForm consultationPlacementForm = 
            (ConsultationPlacementForm)form;
        Context context = ContextHandler.getContext();
        try {
            ParamAgence paramAgence = 
                (ParamAgence)request.getSession().getAttribute("paramAgBNA");
            consultationPlacementForm.setLibelleOperation("CONSULTATION HISTORIQUE DE CONTRAT PLACEMENT");
            consultationPlacementForm.setChoix("3");
            consultationPlacementForm.clearFormRechercheContratPlacement();
            consultationPlacementForm.getInitialisationView().setDateOp(DateHandler.strToDate(paramAgence.getDateJours()));
            consultationPlacementForm.getInitialisationView().setDateActuelle(paramAgence.getDateComptable());
            consultationPlacementForm.getInitialisationView().setCodeAgence(paramAgence.getCodStrcStrc().toString());
            // consultationPlacementForm.getInitialisationView().setCodeOperation(Constants.COD_OPER_SOUSC_PLACEMENT.toString()); 
            consultationPlacementForm.setDateDebRecherch(paramAgence.getDateJours());
            consultationPlacementForm.setDateFinRecherch(paramAgence.getDateJours());
            if (!paramAgence.getCodStrcStrc().equals(Constants.COD_DIR_TRESORERIE)) {
                // code structure diff de la dir trésorerie
                if (paramAgence.getCodTstrcTstrc().equals(Long.valueOf("1"))) {
                    // agence
                    consultationPlacementForm.setCodTypeStructure("A");
                    consultationPlacementForm.setStructureRech(paramAgence.getCodStrcStrc().toString());
                } else if (paramAgence.getCodTstrcTstrc().equals(Long.valueOf("4")) || 
                           paramAgence.getCodTstrcTstrc().equals(Long.valueOf("2"))) {
                    // D.régionale
                    consultationPlacementForm.setCodTypeStructure("R");
                    // recherche de la liste des agences concernées
                    PlacementDAO plcDao = 
                        (PlacementDAO)context.getBean("placementDAO");
                    consultationPlacementForm.setListAgConcernees(plcDao.getListStructureConcernes(paramAgence.getCodStrcStrc()));
                }
            } else {
                consultationPlacementForm.setCodTypeStructure("C");
            }
            
            return mapping.findForward("initHistoriqueContratPlacement");
        } catch (Exception e) {
            com.oxia.fwk.core.Error erreur = new com.oxia.fwk.core.Error();
            StringBuffer text = 
                new StringBuffer("L'initialisation de la consultation des Contrats Placement a été interrompue, Veuillez transmettre ce message à l'équipe informatique: ");
            text.append("Exception au niveau de l'agence:");
            text.append(consultationPlacementForm.getInitialisationView().getCodeAgence());
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
     * Action qui permet d'afficher une liste des demandes selon le choix 
     */
    public

    ActionForward rechercherDemandesSelonChoix(ActionMapping mapping, 
                                               ActionForm form, 
                                               HttpServletRequest request, 
                                               HttpServletResponse response) throws IOException, 
                                                                                    ServletException {
        Context context = ContextHandler.getContext();
        ConsultationPlacementForm consultationPlacementForm = 
            (ConsultationPlacementForm)form;
        ActionMessages actionMessages = new ActionMessages();
        String[] codEtat = new String[3];
        Long[] listeStructure = new Long[50];
        try {
            consultationPlacementForm.setListeDemandesDecision(null);
            ParamDemandeDecision paramDemandeDecision = new ParamDemandeDecision();
            GetListDemandesDecisionCmd getListDemandesDecisionCmd = new GetListDemandesDecisionCmd();
            Listes listDemandeDecision = new Listes();
            ContratPersonne contratPersonne = new ContratPersonne();
            PersonneStrc personneStrc = new PersonneStrc();

            if (!consultationPlacementForm.getEtatDemande().equals("0")) {
                codEtat[0] = consultationPlacementForm.getEtatDemande();
                paramDemandeDecision.setCodEtatDemd(codEtat);
            } else {
                paramDemandeDecision.setCodEtatDemd(null);
            }
            if (!consultationPlacementForm.getStructureRech().equals("")) {
                // le cas de saisi d'une agence
                listeStructure[0] = Long.valueOf(consultationPlacementForm.getStructureRech());
                paramDemandeDecision.setCodStrcStrc(listeStructure);
            } else {
                if (consultationPlacementForm.getCodTypeStructure().equals("R")) {
                    //D regionale
                    ListOrderedMap ListStrc = null;
                    int i = 0;
                    for (Iterator it = 
                         consultationPlacementForm.getListAgConcernees().iterator(); 
                         it.hasNext(); ) {
                        ListStrc = (ListOrderedMap)it.next();
                        if ((ListStrc.getValue(0)).toString() != null) {
                            listeStructure[i] = 
                                    Long.valueOf(ListStrc.getValue(0).toString());
                        }
                        i++;
                    }
                    paramDemandeDecision.setCodStrcStrc(listeStructure);
                } else
                    paramDemandeDecision.setCodStrcStrc(null);
            }
            contratPersonne.setPersonneId(personneStrc);
            paramDemandeDecision.setContratPersonne(contratPersonne);
            paramDemandeDecision.setDateDebut(DateHandler.strToDate(consultationPlacementForm.getDateDebRecherch()));
            paramDemandeDecision.setDateFin(DateHandler.addJour(DateHandler.strToDate(consultationPlacementForm.getDateFinRecherch()),1));
            if (consultationPlacementForm.getChoix().equals("0")){
                // traiter le cas de la recherche par type et numéro de pièce
                personneStrc.setCodTpceTpce(new Long(consultationPlacementForm.getTypePieceId()));
                personneStrc.setNumPcePers(consultationPlacementForm.getNumPieceId());
                contratPersonne.setPersonneId(personneStrc);
                paramDemandeDecision.setContratPersonne(contratPersonne);
            } else if (consultationPlacementForm.getChoix().equals("1")) {
                // traiter le cas de la recherche par numéro compte
                paramDemandeDecision.setCodPrdPrd(consultationPlacementForm.getCodeProduit());
                paramDemandeDecision.setNumCcptCcpt(consultationPlacementForm.getNumeroCompte());
                paramDemandeDecision.setStructureDemande(Long.valueOf(consultationPlacementForm.getCodeAgence()));                
                paramDemandeDecision.setCodStrcStrc(null);
                
            } else if (consultationPlacementForm.getChoix().equals("2")) {
                //traiter le cas de la recherche par produit de placement                 
                paramDemandeDecision.setProduitPlacement(Long.valueOf(consultationPlacementForm.getProduitRech()));
            } /*else if (consultationPlacementForm.getChoix().equals("3")) {
                // traiter le cas de la recherche de toutes les demandes par date             
                paramDemandeDecision.setDateDebut(DateHandler.strToDate(consultationPlacementForm.getDateDebRecherch()));
                paramDemandeDecision.setDateFin(DateHandler.strToDate(consultationPlacementForm.getDateFinRecherch()));
            }*/
            listDemandeDecision = (Listes)getListDemandesDecisionCmd.execute(paramDemandeDecision);

            if (listDemandeDecision.getList() != null && 
                listDemandeDecision.getList().size() > 0) {
                List listDdeDecisionView = 
                    traiterListedemandesDecision(listDemandeDecision.getList(), 
                                                 consultationPlacementForm);
                consultationPlacementForm.setListeDemandesDecision(listDdeDecisionView);
                consultationPlacementForm.setAlertDemandeDecision("False");
            } else {
                consultationPlacementForm.setAlertDemandeDecision("True");
            }
            return mapping.findForward("initConsultationDemandePlacement");
        } catch (Exception e) {
            com.oxia.fwk.core.Error erreur = new com.oxia.fwk.core.Error();
            StringBuffer text = 
                new StringBuffer("La recherche de la demande choisie a été interrompue, veuillez transmettre ce message à l'équipe informatique: ");
            text.append("Exception au niveau de l'agence:");
            text.append(consultationPlacementForm.getInitialisationView().getCodeAgence());
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

        ConsultationPlacementForm consultationPlacementForm = 
            (ConsultationPlacementForm)form;
        List listDemandeDecisionView = new ArrayList();
        Context context = ContextHandler.getContext();
        try {
            if (listDemandes != null && listDemandes.size() > 0) {

                for (Iterator it = listDemandes.iterator(); it.hasNext(); ) {
                    DemandeDecision demandeDecision = 
                        (DemandeDecision)it.next();

                    DemandeDecisionView demandeDecisionView = new DemandeDecisionView();
                    demandeDecisionView.setNumCcptDemd(StrHandler.lpad(demandeDecision.getContratCpt().getContratCptId().getCodStrcStrc().toString(),'0',3) + 
                                                       StrHandler.lpad(demandeDecision.getContratCpt().getContratCptId().getCodPrdPrd().toString(),'0',4) + 
                                                       StrHandler.lpad(demandeDecision.getContratCpt().getContratCptId().getNumCcptCcpt().toString(),'0',6));
                    demandeDecisionView.setDemandeDecision(demandeDecision);
                    demandeDecisionView.setCodeTypeFaveur(demandeDecision.getCodFavDemd());
                    demandeDecisionView.setDatCreDemd(DateHandler.dateToStr(demandeDecision.getDatCreDemd()));
                    demandeDecisionView.setCodEtatDemd(demandeDecision.getCodEtatDemd());
                    demandeDecisionView.setCodNdemDemd(demandeDecision.getCodNdemDemd());
                    demandeDecisionView.setMontPlaDemd(StrHandler.formatmnt(Math.abs(demandeDecision.getMontPlaDemd().doubleValue())));
                    if (demandeDecision.getNumToffDemd() != null) {
                        if (demandeDecision.getCodFavDemd().equals("G") || 
                            demandeDecision.getCodFavDemd().equals("F") ||
                            demandeDecision.getCodFavDemd().equals("P"))
                            demandeDecisionView.setTauxAccorde(demandeDecision.getNumToffDemd().toString());
                        else if (demandeDecision.getCodFavDemd().equals("I"))
                            demandeDecisionView.setTauxAccorde("TMM" +demandeDecision.getCodMargDemd() +" "+demandeDecision.getNumToffDemd().toString());
                    }
                    demandeDecisionView.setCodTypPaiement(demandeDecision.getCodPintDemd());
                    demandeDecisionView.setLibCcptCcpt(demandeDecision.getContratCpt().getNomIntiCcpt());
                    listDemandeDecisionView.add(demandeDecisionView);
                    consultationPlacementForm.setAlertDemandeDecision("False");
                } // Fin For 
            } else {
                consultationPlacementForm.setAlertDemandeDecision("True");
            }
        } catch (Exception e) {
            logger.error("Exception dans souscriptionContratPlacementAction / Methode : traiterListedemandesDecision:  ", 
                         e);
            throw new RuntimeException(e);
        }
        return listDemandeDecisionView;
    }

    // Consultation contrat placement

    public ActionForward initConsultContratPlacement(ActionMapping mapping, 
                                                     ActionForm form, 
                                                     HttpServletRequest request, 
                                                     HttpServletResponse response) throws IOException, 
                                                                                          ServletException {

        ActionMessages actionMessages = new ActionMessages();
        ConsultationPlacementForm consultationPlacementForm = 
            (ConsultationPlacementForm)form;
        Context context = ContextHandler.getContext();
        try {
            ParamAgence paramAgence = 
                (ParamAgence)request.getSession().getAttribute("paramAgBNA");
            consultationPlacementForm.setLibelleOperation("CONSULTATION CONTRAT PLACEMENT");
            consultationPlacementForm.setChoix("4");
            consultationPlacementForm.clearFormRechercheContratPlacement();
            consultationPlacementForm.getInitialisationView().setDateOp(DateHandler.strToDate(paramAgence.getDateJours()));
            consultationPlacementForm.getInitialisationView().setDateActuelle(paramAgence.getDateComptable());
            consultationPlacementForm.getInitialisationView().setCodeAgence(paramAgence.getCodStrcStrc().toString());
            consultationPlacementForm.setDateDebRecherch(paramAgence.getDateComptable());
            consultationPlacementForm.setDateFinRecherch(paramAgence.getDateComptable());
            if (!paramAgence.getCodStrcStrc().equals(Constants.COD_DIR_TRESORERIE)) {
                // code structure diff de la dir trésorerie
                if (paramAgence.getCodTstrcTstrc().equals(Long.valueOf("1"))) {
                    // agence
                    consultationPlacementForm.setCodTypeStructure("A");
                    consultationPlacementForm.setCodeAgence(paramAgence.getCodStrcStrc().toString());
                    consultationPlacementForm.setStructureRech(paramAgence.getCodStrcStrc().toString());
                } else if (paramAgence.getCodTstrcTstrc().equals(Long.valueOf("4")) || 
                           paramAgence.getCodTstrcTstrc().equals(Long.valueOf("2"))) {
                    // D.régionale
                    consultationPlacementForm.setCodTypeStructure("R");
                    // recherche de la liste des agences concernées
                    PlacementDAO plcDao = 
                        (PlacementDAO)context.getBean("placementDAO");
                    consultationPlacementForm.setListAgConcernees(plcDao.getListStructureConcernes(paramAgence.getCodStrcStrc()));
                }
            } else {
                consultationPlacementForm.setCodTypeStructure("C");
            }

            return mapping.findForward("initConsultCPlac");
            //initSitMensCPlac
        } catch (Exception e) {
            com.oxia.fwk.core.Error erreur = new com.oxia.fwk.core.Error();
            StringBuffer text = 
                new StringBuffer("L'initialisation de la consultation des Contrats Placement a été interrompue, Veuillez transmettre ce message à l'équipe informatique: ");
            text.append("Exception au niveau de l'agence:");
            text.append(consultationPlacementForm.getInitialisationView().getCodeAgence());
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
     * Action qui permet l'impression des listes des demandes decision selon selon le choix 
     */
    public

    ActionForward imprimerListDemandePlacement(ActionMapping mapping, 
                                               ActionForm form, 
                                               HttpServletRequest request, 
                                               HttpServletResponse response) throws IOException, 
                                                                                    ServletException {
                                                                                    
                                                                                    
        Context context = ContextHandler.getContext();
        ConsultationPlacementForm consultationPlacementForm = 
            (ConsultationPlacementForm)form;
        ActionMessages actionMessages = new ActionMessages();
        String[] codEtat = new String[3];
        Long[] listeStructure = new Long[50];
        CommonReportVO valueObject=new CommonReportVO();
        Map parameters = new HashMap();
        String pLibEtat = "P_LIB_ETAT";
        String pMatrUser = "P_NUM_MATR_USER";
        String pdateDebut="P_DATE_DEBUT";
        String pdateFin="P_DATE_FIN";
        String pEtat="P_ETAT";
        String vLibEtat = "";
        String vMatrUser = "";
        String vdateDebut="";
        String vdateFin="";
        String vEtat="";
        try {
            ParamAgence paramAgence = (ParamAgence)request.getSession().getAttribute("paramAgBNA");
             vMatrUser = paramAgence.getNumMatrUser();
             vdateDebut=consultationPlacementForm.getDateDebRecherch();
             vdateFin=consultationPlacementForm.getDateFinRecherch();
             vEtat=consultationPlacementForm.getEtatDemande();
             parameters.put(pMatrUser, vMatrUser);
             parameters.put(pdateDebut, vdateDebut);
             parameters.put(pdateFin, vdateFin);
            if (!consultationPlacementForm.getEtatDemande().equals("0")) 
                parameters.put(pEtat, vEtat);
            if (consultationPlacementForm.getChoix().equals("0")) 
            {
                String pTypePiece="P_CODE_TPCE_TPCE";
                String pNumPiece="P_NUM_PCE_PERS";
                String vTypePiece =consultationPlacementForm.getTypePieceId();
                String vNumPiece=consultationPlacementForm.getNumPieceId();
                parameters.put(pTypePiece, vTypePiece);
                parameters.put(pNumPiece, vNumPiece);
                ParamDemandeDecision paramDemandeDecision = new ParamDemandeDecision();
                
                if (consultationPlacementForm.getEtatDemande().equals("0"))
                {
                    vLibEtat="Liste des demandes par identifiant client";
                    valueObject.setNomReport("DPPTP");
                }
                else 
                {
                    
                     vLibEtat="Liste des demandes par Etat et par identifiant client";
                     valueObject.setNomReport("DPPETP");
                }
            } 
            else if (consultationPlacementForm.getChoix().equals("1")) 
            {     //liste des demandes par numéro de compte 
                 
                String pCodStrcStrc="P_COD_STRC_STRC";
                String pCodPrdPrd="P_COD_PRD_PRD";
                String pNumCcptCcpt="P_NUM_CCPT_CCPT";
                String vCodStrcStrc=consultationPlacementForm.getCodeAgence();
                String vCodPrdPrd=consultationPlacementForm.getCodeProduit();
                String vNumCcptCcpt=consultationPlacementForm.getNumeroCompte();
                parameters.put(pCodStrcStrc, vCodStrcStrc);
                parameters.put(pCodPrdPrd, vCodPrdPrd); 
                parameters.put(pNumCcptCcpt, vNumCcptCcpt); 
                if (consultationPlacementForm.getEtatDemande().equals("0")) {
                    vLibEtat="Liste des demandes par numéro de de compte";
                    valueObject.setNomReport("DPPCCPT");
                }else{
                    vLibEtat="Liste des demandes par Etat et par numéro de de compte";
                    valueObject.setNomReport("DPPECCPT");
                    }               
            } 
            else if (consultationPlacementForm.getChoix().equals("2")) {
                String pCodPrdPlc="P_PRD_PLC";                                            //traiter le cas de la recherche par produit de placement                 
                String pStructure="P_STRUCTURE";
                String vStructure=consultationPlacementForm.getCodeAgence();
                String vCodPrdPlc= consultationPlacementForm.getProduitRech();
                parameters.put(pCodPrdPlc, vCodPrdPlc);
                parameters.put(pStructure, vStructure);
                if (consultationPlacementForm.getEtatDemande().equals("0")) {
                    vLibEtat="Liste des demandes par produit placement";
                    valueObject.setNomReport("DPPPRD");
                }else{
                    vLibEtat="Liste des demandes par Etat et par produit placement";
                    valueObject.setNomReport("DPPEPRD");
                    }               
            } 
            parameters.put(pLibEtat, vLibEtat);
            valueObject.setNomDossier("Placement");
            valueObject.setParams(parameters);
            request.getSession().setAttribute("CommonPrintVo",valueObject);
            request.setAttribute("print","1");
            return mapping.findForward("initConsultationDemandePlacement");
        } catch (Exception e) {
            com.oxia.fwk.core.Error erreur = new com.oxia.fwk.core.Error();
            StringBuffer text = 
                new StringBuffer("La recherche de la demande choisie a été interrompue, veuillez transmettre ce message à l'équipe informatique: ");
            text.append("Exception au niveau de l'agence:");
            text.append(consultationPlacementForm.getInitialisationView().getCodeAgence());
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
    public

    ActionForward imprimerHistoriquePlacement(ActionMapping mapping, 
                                               ActionForm form, 
                                               HttpServletRequest request, 
                                               HttpServletResponse response) throws IOException, 
                                                                                    ServletException {
                                                                                    
                                                                                    
        Context context = ContextHandler.getContext();
        ConsultationPlacementForm consultationPlacementForm = 
            (ConsultationPlacementForm)form;
        ActionMessages actionMessages = new ActionMessages();
        String[] codEtat = new String[3];
        Long[] listeStructure = new Long[50];
        CommonReportVO valueObject=new CommonReportVO();
        Map parameters = new HashMap();
        String pLibEtat = "P_LIB_ETAT";
        String pMatrUser = "P_NUM_MATR_USER";
        String pdateDebut="P_DATE_DEBUT";
        String pdateFin="P_DATE_FIN";
        String pTypePiece="P_COD_TPCE_TPCE";
        String pNumPiece="P_NUM_PCE_PRES";
        String vLibEtat = "";
        String vMatrUser = "";
        String vdateDebut="";
        String vdateFin="";
        String vTypePiece="";
        String vNumPiece="";
        try {
            ParamAgence paramAgence = (ParamAgence)request.getSession().getAttribute("paramAgBNA");
             vMatrUser = paramAgence.getNumMatrUser();
             vdateDebut=consultationPlacementForm.getDateDebRecherch();
             vdateFin=consultationPlacementForm.getDateFinRecherch();
             vTypePiece =consultationPlacementForm.getTypePieceId();
             vNumPiece=consultationPlacementForm.getNumPieceId();
             parameters.put(pTypePiece, vTypePiece);
             parameters.put(pNumPiece, vNumPiece);
             parameters.put(pMatrUser, vMatrUser);
             parameters.put(pdateDebut, vdateDebut);
             parameters.put(pdateFin, vdateFin);
            if (consultationPlacementForm.getEtatDemande().equals("LQ")) {
                vLibEtat="Etat des liquidation par relation client";
                valueObject.setNomReport("Liquidation");
            }
            if (consultationPlacementForm.getEtatDemande().equals("AC")) {
                vLibEtat="Etat des avances en cours par relation client";
                valueObject.setNomReport("AvancesEncour");
            }
            if (consultationPlacementForm.getEtatDemande().equals("AR")) {
                vLibEtat="Etat des avances remboursées par relation client";
                valueObject.setNomReport("AvancesRemb");
            }
                
            parameters.put(pLibEtat, vLibEtat);
            valueObject.setNomDossier("Placement");
            valueObject.setParams(parameters);
            request.getSession().setAttribute("CommonPrintVo",valueObject);
            request.setAttribute("print","1");
            return mapping.findForward("initHistoriqueContratPlacement");
        } catch (Exception e) {
            com.oxia.fwk.core.Error erreur = new com.oxia.fwk.core.Error();
            StringBuffer text = 
                new StringBuffer("La recherche de la demande choisie a été interrompue, veuillez transmettre ce message à l'équipe informatique: ");
            text.append("Exception au niveau de l'agence:");
            text.append(consultationPlacementForm.getInitialisationView().getCodeAgence());
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
     * Action qui permet d'afficher une liste des contrats placement selon le choix 
     */
    public

    ActionForward rechercherContratPlacSelonChoix(ActionMapping mapping, 
                                                  ActionForm form, 
                                                  HttpServletRequest request, 
                                                  HttpServletResponse response) throws IOException, 
                                                                                       ServletException {

        ConsultationPlacementForm consultationPlacementForm = 
            (ConsultationPlacementForm)form;
        ActionMessages actionMessages = new ActionMessages();
        Long[] listeStructure = new Long[50];
        try {
            consultationPlacementForm.setListeContratPlacement(null);
            ParamDemandeDecision paramDemandeDecision = 
                new ParamDemandeDecision();
            GetListContratsPlacementCmd getListContratsPlacementCmd = 
                new GetListContratsPlacementCmd();
            Listes listContratPlacement = new Listes();
           
            PersonneStrc personneStrc = new PersonneStrc();

          //  paramDemandeDecision.setDateComptable(DateHandler.addJour(DateHandler.strToDate(consultationPlacementForm.getInitialisationView().getDateActuelle()),1));
            
          
            if (!consultationPlacementForm.getStructureRech().equals("")) {
                // le cas de saisi d'une agence
                listeStructure[0] = 
                        Long.valueOf(consultationPlacementForm.getStructureRech());
                paramDemandeDecision.setCodStrcStrc(listeStructure);
            } else {
                if (consultationPlacementForm.getCodTypeStructure().equals("R")) {
                    //D regionale
                    ListOrderedMap ListStrc = null;
                    int i = 0;
                    for (Iterator it = 
                         consultationPlacementForm.getListAgConcernees().iterator(); 
                         it.hasNext(); ) {
                        ListStrc = (ListOrderedMap)it.next();
                        if ((ListStrc.getValue(0)).toString() != null) {
                            listeStructure[i] = 
                                    Long.valueOf(ListStrc.getValue(0).toString());
                        }
                        i++;
                    }
                    paramDemandeDecision.setCodStrcStrc(listeStructure);
                } else
                    paramDemandeDecision.setCodStrcStrc(null);

            }
           
           
           if (!consultationPlacementForm.getEtatDemande().equals("0")) {
                paramDemandeDecision.setCodEtatContrat(consultationPlacementForm.getEtatDemande());
            } else {
                paramDemandeDecision.setCodEtatContrat(null);
            }
            
            if (consultationPlacementForm.getChoix().equals("0")) {
                // traiter le cas de la recherche par type et numéro de pièce
                personneStrc.setCodTpceTpce(new Long(consultationPlacementForm.getTypePieceId()));
                personneStrc.setNumPcePers(consultationPlacementForm.getNumPieceId());
                GetPersonneCmd getPersonneCmd = new GetPersonneCmd();
                Personne pers = (Personne)getPersonneCmd.execute(personneStrc);
                paramDemandeDecision.setNumSeqPers(pers.getNumSeqPers());
            } else if (consultationPlacementForm.getChoix().equals("1")) {
                // traiter le cas de la recherche par numéro contrat compte
                 ContratPersonne contratPersonne = new ContratPersonne();
                 ContratCptId contratCptId = new ContratCptId();
                 contratCptId.setCodStrcStrc(Long.valueOf(consultationPlacementForm.getCodeAgence()));
                 contratCptId.setCodPrdPrd(Long.valueOf(consultationPlacementForm.getCodeProduit()));
                 contratCptId.setNumCcptCcpt(Long.valueOf(consultationPlacementForm.getNumeroCompte()));
                 contratPersonne.setContratCptId(contratCptId);
                 paramDemandeDecision.setContratPersonne(contratPersonne);
                 contratPersonne = null;
                 contratCptId = null;
            }else if (consultationPlacementForm.getChoix().equals("2")) {
                // traiter le cas de la recherche par numéro placement
                paramDemandeDecision.setNumSeqCpla(Long.valueOf(consultationPlacementForm.getNumCplaRech()));
            } else if (consultationPlacementForm.getChoix().equals("3")) {
                //traiter le cas de la recherche par produit de placement et par période             
                 paramDemandeDecision.setProduitPlacement(Long.valueOf(consultationPlacementForm.getProduitRech()));
               if(!consultationPlacementForm.getDateDebRecherch().equals("")){
                   paramDemandeDecision.setDateDebut(DateHandler.strToDate(consultationPlacementForm.getDateDebRecherch()));
               }
               if(!consultationPlacementForm.getDateFinRecherch().equals("")){
                 paramDemandeDecision.setDateFin(DateHandler.addJour(DateHandler.strToDate(consultationPlacementForm.getDateFinRecherch()),1));
               }
               
           }else if(consultationPlacementForm.getChoix().equals("4")){
               if(!consultationPlacementForm.getDateDebRecherch().equals("")){
                   paramDemandeDecision.setDateDebut(DateHandler.strToDate(consultationPlacementForm.getDateDebRecherch()));
               }
               if(!consultationPlacementForm.getDateFinRecherch().equals("")){
                 paramDemandeDecision.setDateFin(DateHandler.addJour(DateHandler.strToDate(consultationPlacementForm.getDateFinRecherch()),1));
               }
            }
            listContratPlacement = 
                    (Listes)getListContratsPlacementCmd.execute(paramDemandeDecision);

            if (listContratPlacement.getList() != null && listContratPlacement.getList().size() > 0) {
                List listCplacView = new ArrayList();
                if (consultationPlacementForm.getEtatDemande().equals(Constants.ETAT_CONTRAT_PLAC_ECHULIQ)) {
                    List listPlacBCechuLiqu = new ArrayList();
                        for (Iterator it = listContratPlacement.getList().iterator(); it.hasNext(); ) {
                            ContratPlacement contratPlacement = (ContratPlacement)it.next();
                            DetailsBc detailsBc = new DetailsBc();
                            detailsBc.setContratPlacement(contratPlacement);
                            GetDetailBcTrt getDetailBcTrt = new GetDetailBcTrt();
                            DetailsBc detailsBcTrouve = (DetailsBc)getDetailBcTrt.exec(detailsBc);
                            if(detailsBcTrouve.getNumBcDbc() != null && detailsBcTrouve.getDateRecaBc() == null){
                                listPlacBCechuLiqu.add(contratPlacement);
                                }
                        }
                    listCplacView = 
                       traiterListeContratPlacement(listPlacBCechuLiqu, consultationPlacementForm);
                    
                }else {
                 listCplacView = 
                    traiterListeContratPlacement(listContratPlacement.getList(), consultationPlacementForm);
                }
                consultationPlacementForm.setListeContratPlacement(listCplacView);
            }
            return mapping.findForward("initConsultCPlac");
        } catch (Exception e) {
            com.oxia.fwk.core.Error erreur = new com.oxia.fwk.core.Error();
            StringBuffer text = 
                new StringBuffer("La recherche de la liste des contrats placement a été interrompue, veuillez transmettre ce message à l'équipe informatique: ");
            text.append("Exception au niveau de l'agence:");
            text.append(consultationPlacementForm.getInitialisationView().getCodeAgence());
            text.append(". Exception :");
            text.append(e.toString());
            erreur.setCode("298");
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
     * Fonction qui retourne la liste des contrats placement à afficher
     * appelé par l'Action rechercherContratPlacSelonChoix
     */
    public List traiterListeContratPlacement(List listContrats, ActionForm form) {
        ConsultationPlacementForm consultationPlacementForm =   (ConsultationPlacementForm)form;
        List listContratsPlacementView = new ArrayList();
        Double sommeMontant =Double.valueOf("0");
        Double sommeMontantActualise =Double.valueOf("0");
        try {
            if (listContrats != null && listContrats.size() > 0) {

                for (Iterator it = listContrats.iterator(); it.hasNext(); ) {
                    ContratPlacement contratPlacement = 
                        (ContratPlacement)it.next();
                    ContratPlacementView contratPlacementView = 
                        new ContratPlacementView();
                    contratPlacementView.setContratPlacement(contratPlacement);
                    contratPlacementView.setNumSeqCpla(contratPlacement.getNumSeqCpla().toString());
                    contratPlacementView.setDatCreCpla(DateHandler.dateToStr(contratPlacement.getDatCreCpla()));
                    contratPlacementView.setDuree(contratPlacement.getNumNbrjCpla().toString());
                    contratPlacementView.setDatEcheCpla(DateHandler.dateToStr(contratPlacement.getDatEcheCpla()));
                    contratPlacementView.setMontCapCpla(StrHandler.formatmnt(Math.abs(contratPlacement.getMontCapCpla().doubleValue())));
                    contratPlacementView.setCodPrdPrd(contratPlacement.getProduitPlacement().getCodPrdPlc().toString());
                    contratPlacementView.setLibPrdPrd(contratPlacement.getProduitPlacement().getLibAbrPlc());
                    contratPlacementView.setMontActuCpla(StrHandler.formatmnt(Math.abs(contratPlacement.getMontActuCpla().doubleValue())));
                    contratPlacementView.setNumTauiCpla(contratPlacement.getNumTauiCpla().toString());
                    if (contratPlacement.getCodPintCpla().equals("PRE")) {
                        contratPlacementView.setCodPintCpla("PRE");
                        contratPlacementView.setLibPintCpla("A L'AVANCE");
                    } else {
                        contratPlacementView.setCodPintCpla("POST");
                        contratPlacementView.setLibPintCpla("A TERME ECHU");
                    }
                    contratPlacementView.setDatEcheCpla(DateHandler.dateToStr(contratPlacement.getDatEcheCpla()));
                    contratPlacementView.setTauIRC(contratPlacement.getNumTircCpla().toString());
                    if (contratPlacement.getProduitPlacement().getCodPrdPlc().equals(Constants.COD_PRD_BC_PLAC) || 
                        contratPlacement.getProduitPlacement().getCodPrdPlc().equals(Constants.COD_PRD_BCDC_PLAC)) {
                        if (contratPlacement.getNumBcCpla() != null) {
                            contratPlacementView.setNumBcCpla(contratPlacement.getNumBcCpla().toString());
                        } else {
                            logger.error("Le numéro de BC est vide");
                        }
                    }
                    contratPlacementView.setTypeFaveurCpla(contratPlacement.getCodFavCpla());
                    if (contratPlacement.getContratCpt() != null) {
                        contratPlacementView.setNumCcptCpla(StrHandler.lpad(contratPlacement.getContratCpt().getContratCptId().getCodStrcStrc().toString(),'0',3) + 
                                                            " " + StrHandler.lpad(contratPlacement.getContratCpt().getContratCptId().getCodPrdPrd().toString(),'0',4) + 
                                                            " " + StrHandler.lpad(contratPlacement.getContratCpt().getContratCptId().getNumCcptCcpt().toString(),'0',6));
                    
                        contratPlacementView.setIntituleCpt(contratPlacement.getContratCpt().getNomIntiCcpt());
                    }

                    Personne pers = contratPlacement.getPersonne();
                    if (pers.getTypePiece().getCodTpceTpce().equals(Constants.COD_RCS)) { // cas RCS affichage de libSiglPers 
                        contratPlacementView.setNomClient(pers.getLibSiglPers());
                        contratPlacementView.setPrenomClient(pers.getNomRsPers());
                    } else {
                        contratPlacementView.setNomClient(pers.getNomNomPers());
                        contratPlacementView.setPrenomClient(pers.getNomPrnPers());
                    }
                    sommeMontant = sommeMontant + contratPlacement.getMontCapCpla().doubleValue();
                    sommeMontantActualise= sommeMontantActualise + contratPlacement.getMontActuCpla().doubleValue();
                    listContratsPlacementView.add(contratPlacementView);
                } // Fin For 
              consultationPlacementForm.setSommeCapital(StrHandler.formatmnt(sommeMontant.doubleValue()));
              consultationPlacementForm.setSommeCapitalActualise(StrHandler.formatmnt(sommeMontantActualise.doubleValue()));
            }
        } catch (Exception e) {
            logger.error("Exception dans consultationPlacementAction/ Methode : traiterListeContratPlacement:  ", 
                         e);
            throw new RuntimeException(e);
        }
        return listContratsPlacementView;
    }

    /**
     * Action qui permet d'afficher une liste des contrats placement selon le choix 
     */
    public

    ActionForward imprimerListContratsPlacement(ActionMapping mapping, 
                                                ActionForm form, 
                                                HttpServletRequest request, 
                                                HttpServletResponse response) throws IOException, 
                                                                                     ServletException {

        ConsultationPlacementForm consultationPlacementForm = 
            (ConsultationPlacementForm)form;
        ActionMessages actionMessages = new ActionMessages();
        ParamAgence paramAgence = 
            (ParamAgence)request.getSession().getAttribute("paramAgBNA");
        try {
            CommonReportVO valueObject = new CommonReportVO();
            Map parameters = new HashMap();
            String pLibEtat = "P_LIB_ETAT";
            String vLibEtat = "";
            StringBuffer txtLibEtat = new StringBuffer("Liste des contrats placement");
            StringBuffer txtNomFichJasper = new StringBuffer("");
            vLibEtat = "";
            if (!consultationPlacementForm.getStructureRech().equals("")) {
                parameters.put("P_STRUCTURE",consultationPlacementForm.getStructureRech());
             }else {
                    parameters.put("P_STRUCTURE",paramAgence.getCodStrcStrc().toString());
            }
             
             // Ajout du parametre matricule utilisateur
            parameters.put("P_NUM_MATR_USER",paramAgence.getNumMatrUser());
                    
            if (consultationPlacementForm.getChoix().equals("0")) {
                // traiter le cas de la recherche par type et numéro de pièce / par periode   
                parameters.put("P_COD_TPCE_TPCE",consultationPlacementForm.getTypePieceId());
                parameters.put("P_NUM_PCE_PERS",consultationPlacementForm.getNumPieceId());
             /*   parameters.put("P_DATE_DEB",consultationPlacementForm.getDateDebRecherch());
                parameters.put("P_DATE_FIN",consultationPlacementForm.getDateFinRecherch());
                */
                txtLibEtat.append(" par pièce d'identification");
                if(!consultationPlacementForm.getEtatDemande().equals("0")){
                
                if(consultationPlacementForm.getEtatDemande().equals(Constants.ETAT_CPT_PLACEMENT_LIQUIDE)){
                    txtNomFichJasper.append("listCplac_pieceLiq");
                }else if(consultationPlacementForm.getEtatDemande().equals(Constants.ETAT_CONTRAT_PLAC_ECHULIQ)){
                        txtNomFichJasper.append("listCplac_pieceEchuLiq");
                        }else if(consultationPlacementForm.getEtatDemande().equals(Constants.ETAT_CONTRAT_PLAC_RENOUVELE)){
                            txtNomFichJasper.append("listCplac_pieceRenouv");
                            }else if(consultationPlacementForm.getEtatDemande().equals("AL")){
                                txtNomFichJasper.append("listCplac_pieceAttLiq");
                                }else if(consultationPlacementForm.getEtatDemande().equals(Constants.ETAT_CONTRAT_PLAC_VALIDE)){
                                    txtNomFichJasper.append("listCplac_pieceValid");
                                    }else if(consultationPlacementForm.getEtatDemande().equals("LP")){
                                        txtNomFichJasper.append("listCplac_pieceLiqPart");
                                        }else { 
                                            txtNomFichJasper.append("listCplac_pieceEtat");
                                               } 
                }else{
                    txtNomFichJasper.append("listCplac_piece");
                }
            } else if (consultationPlacementForm.getChoix().equals("3")) {
                //traiter le cas de la recherche par produit de placement   / par periode                 
                 parameters.put("PRD_PLAC",consultationPlacementForm.getProduitRech());
                 parameters.put("P_DATE_DEB",consultationPlacementForm.getDateDebRecherch());
                 parameters.put("P_DATE_FIN",consultationPlacementForm.getDateFinRecherch());
                
                txtLibEtat.append(" du jour/ou par période et par produit");
                // Echus en attente de restitution concerne tjs le BC ou BCDC!
                 if(consultationPlacementForm.getEtatDemande().equals(Constants.ETAT_CPT_PLACEMENT_LIQUIDE)){
                     txtNomFichJasper.append("listCplac_produitLiq");
                 }else  if(consultationPlacementForm.getEtatDemande().equals(Constants.ETAT_CONTRAT_PLAC_ECHULIQ)){
                         txtNomFichJasper.append("listCplac_produitEchuLiq");
                         }else if(consultationPlacementForm.getEtatDemande().equals(Constants.ETAT_CONTRAT_PLAC_RENOUVELE)){
                            txtNomFichJasper.append("listCplac_produitRenouv");
                            }else  if(consultationPlacementForm.getEtatDemande().equals("AL")){
                                 txtNomFichJasper.append("listCplac_produitAttLiq");
                                 }else if(consultationPlacementForm.getEtatDemande().equals("LP")){
                                 txtNomFichJasper.append("listCplac_produitLiqPart");
                                  }else if(consultationPlacementForm.getEtatDemande().equals(Constants.ETAT_CONTRAT_PLAC_VALIDE)){
                                        txtNomFichJasper.append("listCplac_produitValid");
                                    }else if(consultationPlacementForm.getEtatDemande().equals(Constants.ETAT_CPT_PLC_ATT_RESILIATION)||consultationPlacementForm.getEtatDemande().equals(Constants.ETAT_CPT_PLC_RESILIE)){
                                           txtNomFichJasper.append("listCplac_produitRes");
                                            }else if(consultationPlacementForm.getEtatDemande().equals(Constants.ETAT_CPLA_REJETE)){
                                                txtNomFichJasper.append("listCplac_produitRejet");
                                                }else {
                                                    txtNomFichJasper.append("listCplac_produit");
                                                }
                   
           
           /////
            } else if (consultationPlacementForm.getChoix().equals("2")) {
                //traiter le cas de la recherche par contrat de placement          
                parameters.put("P_NUM_CPLA",consultationPlacementForm.getNumCplaRech());
                txtNomFichJasper.append("listCplac_Cpla");
            } else if (consultationPlacementForm.getChoix().equals("4")) {
                // traiter le cas de la recherche par période           
                 parameters.put("P_DATE_DEB",consultationPlacementForm.getDateDebRecherch());
                 parameters.put("P_DATE_FIN",consultationPlacementForm.getDateFinRecherch());
            
                 if(consultationPlacementForm.getEtatDemande().equals(Constants.ETAT_CPT_PLACEMENT_LIQUIDE)){
                     txtNomFichJasper.append("listCplac_periodeLiq");
                 }else  if(consultationPlacementForm.getEtatDemande().equals(Constants.ETAT_CONTRAT_PLAC_ECHULIQ)){
                         parameters.put("P_DATE_COMPT",consultationPlacementForm.getInitialisationView().getDateActuelle());
                         txtNomFichJasper.append("listCplac_periodeEchu");
                         }else if(consultationPlacementForm.getEtatDemande().equals(Constants.ETAT_CONTRAT_PLAC_RENOUVELE)){
                            txtNomFichJasper.append("listCplac_periodeRenouv");
                            }else if(consultationPlacementForm.getEtatDemande().equals("AL")){
                                 txtNomFichJasper.append("listCplac_periodeAttLiq");
                                 }else if(consultationPlacementForm.getEtatDemande().equals("LP")){
                                 txtNomFichJasper.append("listCplac_periodeLiqPart");
                                 }else if(consultationPlacementForm.getEtatDemande().equals(Constants.ETAT_CONTRAT_PLAC_VALIDE)){
                                         if(consultationPlacementForm.getCodTypeStructure().equals("C")){
                                             txtNomFichJasper.append("listCplac_periodeValid900");
                                             }else {
                                                  txtNomFichJasper.append("listCplac_periodeValid");
                                             }
                                     }else if(consultationPlacementForm.getEtatDemande().equals(Constants.ETAT_CPT_PLC_ATT_RESILIATION)){
                                           txtNomFichJasper.append("listCplac_periodeAttRes");
                                             }else if(consultationPlacementForm.getEtatDemande().equals(Constants.ETAT_CPT_PLC_RESILIE)){
                                                 txtNomFichJasper.append("listCplac_periodeRes");
                                                 } else if(consultationPlacementForm.getEtatDemande().equals(Constants.ETAT_CPLA_REJETE)){
                                                 txtNomFichJasper.append("listCplac_periodeRejet");
                                                 }  else {
                                                     txtNomFichJasper.append("listCplac_periode");
                                                    }
                    
                 txtLibEtat.append(" par période");
            } else if(consultationPlacementForm.getChoix().equals("1")) {
              // traiter le cas de la recherche par contrat compte et par période           
               //    parameters.put("P_DATE_DEB",consultationPlacementForm.getDateDebRecherch());
            //     parameters.put("P_DATE_FIN",consultationPlacementForm.getInitialisationView().getDateComptable());
                 parameters.put("P_COD_STRC",consultationPlacementForm.getCodeAgence());
                 parameters.put("P_COD_PRD",consultationPlacementForm.getCodeProduit());
                 parameters.put("P_NUM_CCPT",consultationPlacementForm.getNumeroCompte());
                 parameters.put("P_STRUCTURE",consultationPlacementForm.getCodeAgence());
                 
                if(!consultationPlacementForm.getEtatDemande().equals("0")){
                 if(consultationPlacementForm.getEtatDemande().equals(Constants.ETAT_CPT_PLACEMENT_LIQUIDE)){
                     txtNomFichJasper.append("listCplac_ccptLiq");
                 }else  if(consultationPlacementForm.getEtatDemande().equals(Constants.ETAT_CONTRAT_PLAC_ECHULIQ)){
                      //   parameters.put("P_DATE_COMPT",consultationPlacementForm.getInitialisationView().getDateActuelle());
                         txtNomFichJasper.append("listCplac_ccptEchuLiq");
                         }else if(consultationPlacementForm.getEtatDemande().equals(Constants.ETAT_CONTRAT_PLAC_RENOUVELE)){
                            txtNomFichJasper.append("listCplac_ccptRenouv");
                            }else if(consultationPlacementForm.getEtatDemande().equals("AL")){
                                 txtNomFichJasper.append("listCplac_ccptAttLiq");
                                 }else if(consultationPlacementForm.getEtatDemande().equals("LP")){
                                 txtNomFichJasper.append("listCplac_ccptLiqPart");
                                 } else if(consultationPlacementForm.getEtatDemande().equals(Constants.ETAT_CONTRAT_PLAC_VALIDE)){
                                     txtNomFichJasper.append("listCplac_ccptValid");
                                     
                                     }else {
                                          txtNomFichJasper.append("listCplac_ccptEtat");
                                                    }
                }else { // aucun etat choisit
                 txtNomFichJasper.append("listCplac_ccpt");
                }
                 txtLibEtat.append(" par période");
            }
            if(!consultationPlacementForm.getEtatDemande().equals("0")){
               
                parameters.put("P_VALIDE",consultationPlacementForm.getEtatDemande());
               
                if(consultationPlacementForm.getEtatDemande().equals(Constants.ETAT_CONTRAT_PLAC_VALIDE)){
                    txtLibEtat.append(" (Valides)");
                   }else if(consultationPlacementForm.getEtatDemande().equals(Constants.ETAT_CONTRAT_PLAC_ATTENTE)){
                        txtLibEtat.append(" (En attentes)");
                            txtNomFichJasper.append("Etat");
                        }else if(consultationPlacementForm.getEtatDemande().equals(Constants.ETAT_CPT_PLACEMENT_LIQUIDE)){
                            txtLibEtat.append(" (Liquidés)");
                            }else if(consultationPlacementForm.getEtatDemande().equals(Constants.ETAT_CONTRAT_PLAC_ECHULIQ)){
                                 txtLibEtat.append(" (Echus en attente de restitution du BC)");
                                 }else if(consultationPlacementForm.getEtatDemande().equals(Constants.ETAT_CONTRAT_PLAC_RENOUVELE)){
                                        txtLibEtat.append(" (Renouvelés)");
                                      }else if(consultationPlacementForm.getEtatDemande().equals("AL")){
                                            txtLibEtat.append(" (En attente de liquidation)");
                                        }else if(consultationPlacementForm.getEtatDemande().equals("LP")){
                                            txtLibEtat.append(" (Liquidés partiellement)");
                                        }else if(consultationPlacementForm.getEtatDemande().equals(Constants.ETAT_CPT_PLC_ATT_RESILIATION)){
                                            txtLibEtat.append(" (Attente de résiliation)");
                                        }else if(consultationPlacementForm.getEtatDemande().equals(Constants.ETAT_CPT_PLC_RESILIE)){
                                            txtLibEtat.append(" (Résiliés)");
                                        }else if(consultationPlacementForm.getEtatDemande().equals(Constants.ETAT_CPLA_REJETE)){
                                            txtLibEtat.append(" (Rejetés)");
                                        }
                
            }
           
            // Titre du fichier à imprimer 
            vLibEtat = txtLibEtat.toString();
            parameters.put(pLibEtat,vLibEtat);
            
            valueObject.setParams(parameters);
            
            parameters = null;
            // indiquer le nom du fichier jasper                   
            valueObject.setNomReport(txtNomFichJasper.toString());  
            valueObject.setNomDossier("Placement");
            request.getSession().setAttribute("CommonPrintVo",valueObject);
            request.setAttribute("print","1");
            
            return mapping.findForward("initConsultCPlac");
        } catch (Exception e) {
            com.oxia.fwk.core.Error erreur = new com.oxia.fwk.core.Error();
            StringBuffer text = 
                new StringBuffer("L'impression de la liste des contrats placement a été interrompue, veuillez transmettre ce message à l'équipe informatique: ");
            text.append("Exception au niveau de l'agence:");
            text.append(consultationPlacementForm.getInitialisationView().getCodeAgence());
            text.append(". Exception :");
            text.append(e.toString());
            erreur.setCode("298");
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

   
   
   
    



    public ActionForward initConsultARL(ActionMapping mapping, 
                                                     ActionForm form, 
                                                     HttpServletRequest request, 
                                                     HttpServletResponse response) throws IOException, 
                                                                                          ServletException {

        ActionMessages actionMessages = new ActionMessages();
        ConsultationPlacementForm consultationPlacementForm = 
            (ConsultationPlacementForm)form;
        Context context = ContextHandler.getContext();
        try {
            ParamAgence paramAgence = (ParamAgence)request.getSession().getAttribute("paramAgBNA");
            consultationPlacementForm.setListeARLPlacement(null);
            consultationPlacementForm.setLibelleOperation("Consultation Avances/Remboursement");
            consultationPlacementForm.setChoix("2");
            consultationPlacementForm.clearFormRechercheContratPlacement();
            consultationPlacementForm.getInitialisationView().setDateOp(DateHandler.strToDate(paramAgence.getDateJours()));
            consultationPlacementForm.getInitialisationView().setDateActuelle(paramAgence.getDateComptable());
            consultationPlacementForm.getInitialisationView().setCodeAgence(paramAgence.getCodStrcStrc().toString());
            // consultationPlacementForm.getInitialisationView().setCodeOperation(Constants.COD_OPER_SOUSC_PLACEMENT.toString()); 
            consultationPlacementForm.setDateDebRecherch(paramAgence.getDateJours());
            consultationPlacementForm.setDateFinRecherch(paramAgence.getDateJours());

            if (!paramAgence.getCodStrcStrc().equals(Constants.COD_DIR_TRESORERIE)) {
                // code structure diff de la dir trésorerie
                if (paramAgence.getCodTstrcTstrc().equals(Long.valueOf("1"))) {
                    // agence
                    consultationPlacementForm.setCodTypeStructure("A");
                    consultationPlacementForm.setStructureRech(paramAgence.getCodStrcStrc().toString());
                } else if (paramAgence.getCodTstrcTstrc().equals(Long.valueOf("4")) || 
                           paramAgence.getCodTstrcTstrc().equals(Long.valueOf("2"))) {
                    // D.régionale
                    consultationPlacementForm.setCodTypeStructure("R");
                    // recherche de la liste des agences concernées
                    PlacementDAO plcDao = (PlacementDAO)context.getBean("placementDAO");
                    consultationPlacementForm.setListAgConcernees(plcDao.getListStructureConcernes(paramAgence.getCodStrcStrc()));
                }
            } else {
                consultationPlacementForm.setCodTypeStructure("C");
            }

            return mapping.findForward("initConsultARLPlac");
        } catch (Exception e) {
            com.oxia.fwk.core.Error erreur = new com.oxia.fwk.core.Error();
            StringBuffer text = 
                new StringBuffer("L'initialisation de la consultation des Avances/Remb a été interrompue, Veuillez transmettre ce message à l'équipe informatique: ");
            text.append("Exception au niveau de l'agence:");
            text.append(consultationPlacementForm.getInitialisationView().getCodeAgence());
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
     * Action qui permet d'afficher une liste des Avances / Rembours selon le choix 
     */
    public ActionForward rechercherARLSelonChoix(ActionMapping mapping, 
                                                  ActionForm form, 
                                                  HttpServletRequest request, 
                                                  HttpServletResponse response) throws IOException, 
                                                                                       ServletException {

        ConsultationPlacementForm consultationPlacementForm =(ConsultationPlacementForm)form;
        ActionMessages actionMessages = new ActionMessages();
        Long[] listeStructure = new Long[50];
        try {
            consultationPlacementForm.setListeARLPlacement(null);
            ParamAvanRembLiq paramAvanRembLiq = new ParamAvanRembLiq();
            GetListAvancRembLiquidByEtatCmd getListAvancRembLiquidByEtatCmd = new GetListAvancRembLiquidByEtatCmd();
            Listes listARLPlacement = new Listes();
           
            PersonneStrc personneStrc = new PersonneStrc();

            paramAvanRembLiq.setDateComptable(DateHandler.strToDate(consultationPlacementForm.getInitialisationView().getDateActuelle()));
            
            if (!consultationPlacementForm.getEtatDemande().equals("0")) {///* selon l'etat (V: valide, R: remboursement, ..)
                paramAvanRembLiq.setCodEtatArl(consultationPlacementForm.getEtatDemande());
            } else {
                paramAvanRembLiq.setCodEtatArl(null);
            }
            paramAvanRembLiq.setCodToprtArl(Constants.CODE_AVANCE); ///* seules les avances pas les Remboursement
            
            if (!consultationPlacementForm.getStructureRech().equals("")) {
                ///* le cas de saisi d'une agence
                listeStructure[0] = Long.valueOf(consultationPlacementForm.getStructureRech());
                paramAvanRembLiq.setListStrcStrc(listeStructure);
            } else {
                if (consultationPlacementForm.getCodTypeStructure().equals("R")) {
                   ///* D regionale
                    ListOrderedMap ListStrc = null;
                    int i = 0;
                    for (Iterator it = consultationPlacementForm.getListAgConcernees().iterator();it.hasNext(); ) {
                        ListStrc = (ListOrderedMap)it.next();
                        if ((ListStrc.getValue(0)).toString() != null) {
                            listeStructure[i] = Long.valueOf(ListStrc.getValue(0).toString());
                        }
                        i++;
                    }
                    paramAvanRembLiq.setListStrcStrc(listeStructure);
                } else
                    paramAvanRembLiq.setListStrcStrc(null);

            }

            if (consultationPlacementForm.getChoix().equals("0")) { ///* traiter le cas de la recherche par type et numéro de pièce
                personneStrc.setCodTpceTpce(new Long(consultationPlacementForm.getTypePieceId()));
                personneStrc.setNumPcePers(consultationPlacementForm.getNumPieceId());
                GetPersonneCmd getPersonneCmd = new GetPersonneCmd();
                Personne pers = (Personne)getPersonneCmd.execute(personneStrc);
                ///paramAvanRembLiq.setNumSeqPers(pers.getNumSeqPers());
                consultationPlacementForm.setNumSeqPersRech(pers.getNumSeqPers());
                consultationPlacementForm.setNumCplaRech(null);
            } else { consultationPlacementForm.setNumSeqPersRech(null);
                     consultationPlacementForm.setNumPieceId(null); 
                    if (consultationPlacementForm.getChoix().equals("1")) { ///* traiter le cas de la recherche par numéro de Contrat placement
                    paramAvanRembLiq.setNumSeqCpla(Long.valueOf(consultationPlacementForm.getNumCplaRech()));
                    }else consultationPlacementForm.setNumCplaRech(null);
            } 
            
//            if (consultationPlacementForm.getChoix().equals("2")) { ///* traiter le cas de la recherche de toutes les demandes par date             
                paramAvanRembLiq.setDateDebut(DateHandler.strToDate(consultationPlacementForm.getDateDebRecherch()));
                paramAvanRembLiq.setDateFin(DateHandler.strToDate(consultationPlacementForm.getDateFinRecherch()));
//            }

            listARLPlacement = (Listes)getListAvancRembLiquidByEtatCmd.execute(paramAvanRembLiq);

            if (listARLPlacement.getList() != null && 
                listARLPlacement.getList().size() > 0) {
                List listARLView = traiterListeARLPlacement(listARLPlacement.getList(),consultationPlacementForm);
                consultationPlacementForm.setListeARLPlacement(listARLView);
            }

            return mapping.findForward("initConsultARLPlac");
        } catch (Exception e) {
            com.oxia.fwk.core.Error erreur = new com.oxia.fwk.core.Error();
            StringBuffer text = 
                new StringBuffer("La recherche de la liste des Avances a été interrompue, veuillez transmettre ce message à l'équipe informatique: ");
            text.append("Exception au niveau de l'agence:");
            text.append(consultationPlacementForm.getInitialisationView().getCodeAgence());
            text.append(". Exception :");
            text.append(e.toString());
            erreur.setCode("301");
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
     * Fonction qui retourne la liste des ARL placement à afficher
     * appelé par l'Action rechercherARLPlacSelonChoix
     */
    public List traiterListeARLPlacement(List listARL, ActionForm form) {

        List listARLPlacementView = new ArrayList();
        ConsultationPlacementForm consultationPlacementForm  = (ConsultationPlacementForm)form;
        try {
            if (listARL != null && listARL.size() > 0) {

                for (Iterator it = listARL.iterator(); it.hasNext(); ) {
                    AvancRembLiquid avancRembLiquid =  (AvancRembLiquid)it.next();
                    //AvancRembLiquidView  avancRembLiquidView  =  new AvancRembLiquidView ();
                     String ag = avancRembLiquid.getContratPlacement().getContratCpt().getContratCptId().getCodStrcStrc().toString();
                    if(ag.equalsIgnoreCase(consultationPlacementForm.getStructureRech()) ||(consultationPlacementForm.getListAgConcernees()!=null && consultationPlacementForm.getListAgConcernees().contains(ag) )|| consultationPlacementForm.getStructureRech().equalsIgnoreCase("")){///*** verifier si l'agence appartient a la liste des agences
                        if (consultationPlacementForm.getNumSeqPersRech()==null || consultationPlacementForm.getNumSeqPersRech().intValue()==avancRembLiquid.getContratPlacement().getPersonne().getNumSeqPers().intValue()){///* en cas de recherche par identifiant client
                            AvancRembLiquidView avancRembLiquidView =  new AvancRembLiquidView();
                            avancRembLiquidView.setNumSeqArl(avancRembLiquid.getNumSeqArl().toString());
                            avancRembLiquidView.setDatArlArl(DateHandler.dateToStr(avancRembLiquid.getDatArlArl()));
                            avancRembLiquidView.setMontArlArl(StrHandler.formatmnt(Math.abs(avancRembLiquid.getMontArlArl().doubleValue())));
                            avancRembLiquidView.setNumTauiArl(avancRembLiquid.getNumTauiArl().toString());
                            avancRembLiquidView.setMontInetArl(StrHandler.formatmnt(Math.abs(avancRembLiquid.getMontInetArl().doubleValue())));
                            //String duree = Long.valueOf(Math.round(Double.valueOf(DateHandler.getDaysBetween(avancRembLiquid.getDatArlArl(),avancRembLiquid.getDatPrevArl())))).toString();
                            //avancRembLiquidView.setDuree(duree);
                            avancRembLiquidView.setDatPrevArl(DateHandler.dateToStr(avancRembLiquid.getDatPrevArl()));
                            avancRembLiquidView.setContratPlacement(avancRembLiquid.getContratPlacement());
                            avancRembLiquidView.setMontActuCpla(StrHandler.formatmnt(Math.abs(avancRembLiquid.getContratPlacement().getMontActuCpla())));
                            avancRembLiquidView.setMontCapCpla(StrHandler.formatmnt(Math.abs(avancRembLiquid.getContratPlacement().getMontCapCpla())));
                            if (avancRembLiquid.getDatReelArl()!=null){
                                avancRembLiquidView.setDatReelArl(DateHandler.dateToStr(avancRembLiquid.getDatReelArl()));
                            }
                            if (avancRembLiquid.getContratPlacement().getPersonne().getNomRsPers()!=null){
                                avancRembLiquidView.setLibRelation(avancRembLiquid.getContratPlacement().getPersonne().getNomRsPers());
                            }else
                                avancRembLiquidView.setLibRelation(avancRembLiquid.getContratPlacement().getPersonne().getNomPrnPers()+" "+avancRembLiquid.getContratPlacement().getPersonne().getNomNomPers());                                
                           // avancRembLiquidValidPlacementForm.getContratView().setCodDevDev(avancRembLiquid.getContratPlacement().getContratCpt().getDevise().getCodDevDev().toString());
                         /*   avancRembLiquidValidPlacementForm.getContratView().setCodStrcStrc(avancRembLiquid.getContratPlacement().getContratCpt().getContratCptId().getCodStrcStrc().toString());
                            avancRembLiquidValidPlacementForm.getContratView().setCodPrdPrd(avancRembLiquid.getContratPlacement().getContratCpt().getContratCptId().getCodPrdPrd().toString());
                            avancRembLiquidValidPlacementForm.getContratView().setNumCcptCcpt(avancRembLiquid.getContratPlacement().getContratCpt().getContratCptId().getNumCcptCcpt().toString());
                            avancRembLiquidValidPlacementForm.getPersonneDemandeur().setCodTpceTpceDemandeur(avancRembLiquid.getContratPlacement().getPersonne().getTypePiece().getCodTpceTpce().toString());
                            avancRembLiquidValidPlacementForm.getPersonneDemandeur().setNumPcePersDemandeur(avancRembLiquid.getContratPlacement().getPersonne().getNumPcePers());
                        */
                            listARLPlacementView.add(avancRembLiquidView);
                        }
                    }
                } // Fin For 
            }
        } catch (Exception e) {
            logger.error("Exception dans consultationPlacementAction/ Methode : traiterListeARLPlacement:  ", e);
            throw new RuntimeException(e);
        }
        return listARLPlacementView;
    }





    
    
    public ActionForward afficherDetailPlacement(ActionMapping mapping, 
                                                     ActionForm form, 
                                                     HttpServletRequest request, 
                                                     HttpServletResponse response) throws IOException, 
                                                                                          ServletException {

        ActionMessages actionMessages = new ActionMessages();
        ConsultationPlacementForm consultationPlacementForm = 
            (ConsultationPlacementForm)form;        
        Context context = ContextHandler.getContext();
        try {
            ParamAgence paramAgence = 
                (ParamAgence)request.getSession().getAttribute("paramAgBNA");
            String numSeqPlacement = 
                new String(request.getParameter("numPlacement"));
            ContratPlacement cpla = new ContratPlacement();
            cpla.setNumSeqCpla(Long.valueOf(numSeqPlacement));
            GetContratPlacementCmd getContratPlacementCmd = new GetContratPlacementCmd();
            cpla = (ContratPlacement)getContratPlacementCmd.execute(cpla);
            consultationPlacementForm.setNumCplaRech(numSeqPlacement);
            consultationPlacementForm.setListeAvance(null);
            consultationPlacementForm.setListeInteretServi(null);
            consultationPlacementForm.setListeLiquidationAnticipe(null);
            consultationPlacementForm.setNumliquidationChoisi("");
            consultationPlacementForm.setNumeroLiqChoisi("");
            consultationPlacementForm.setNumeroIntChoisi("");
            consultationPlacementForm.setDatCreationRech(DateHandler.dateToStr(cpla.getDatCreCpla()));
            consultationPlacementForm.setCapitalRech(StrHandler.formatmnt(Math.abs(cpla.getMontCapCpla())));
            consultationPlacementForm.setCapitalRestRech(StrHandler.formatmnt(Math.abs(cpla.getMontActuCpla())));
            consultationPlacementForm.setDureeRech(String.valueOf(cpla.getNumNbrjCpla()));
            consultationPlacementForm.setTauxRech(cpla.getNumTauiCpla().toString());
            consultationPlacementForm.setPaiementRech(cpla.getCodPintCpla());
            consultationPlacementForm.setDatValRech(DateHandler.dateToStr(cpla.getDatValCpla()));
            consultationPlacementForm.setDatEcheRech(DateHandler.dateToStr(cpla.getDatEcheCpla()));
            consultationPlacementForm.setCodeAgence(cpla.getContratCpt().getContratCptId().getCodStrcStrc().toString());
            consultationPlacementForm.setCodeProduit(cpla.getContratCpt().getContratCptId().getCodPrdPrd().toString());
            consultationPlacementForm.setNumeroCompte(cpla.getContratCpt().getContratCptId().getNumCcptCcpt().toString());
            consultationPlacementForm.setLibelleOperation("détails des opérations éffectués sur le contrat de placement numéro :" + numSeqPlacement);
            consultationPlacementForm.setEcheance(DateHandler.dateToStr(cpla.getDatEcheCpla()));            
            consultationPlacementForm.setPrdPlc(cpla.getProduitPlacement().getCodPrdPlc().toString());
            consultationPlacementForm.setTauxIrc(cpla.getNumTircCpla().toString());
            consultationPlacementForm.setTypeFaveurPlac(cpla.getCodFavCpla());
            afficherListeLiquidationAValider(consultationPlacementForm,paramAgence) ;
            afficherListeAvance(consultationPlacementForm,paramAgence) ;
            afficherListeInteretServi(consultationPlacementForm);
            if(cpla.getContratPlacementByNumSqcrCpla() != null){
                consultationPlacementForm.setEtatPlacement(Constants.ETAT_CONTRAT_PLAC_RENOUVELE);
                consultationPlacementForm.setCodeOperation(Constants.OPER_RENOUVEL_PLAC_AVAN.toString());
            }else{
               if(cpla.getCodEtatCpla().equals(Constants.ETAT_CONTRAT_PLAC_VALIDE)){
                    consultationPlacementForm.setEtatPlacement(Constants.ETAT_CONTRAT_PLAC_VALIDE);
                    if(cpla.getCodSbdvCpla() == null || cpla.getCodSbdvCpla().equals("0") ){
                        consultationPlacementForm.setCodeOperation(Constants.COD_OPER_SOUSC_PLAC.toString());
                    }else{
                        consultationPlacementForm.setCodeOperation(Constants.COD_OPER_SOUSC_PLAC_SBDV.toString());
                    }
                  }else {
                        consultationPlacementForm.setEtatPlacement(" ");
                         }
                  }
            return mapping.findForward("consultationDetailPlacement");
        } catch (Exception e) {
            com.oxia.fwk.core.Error erreur = new com.oxia.fwk.core.Error();
            StringBuffer text = 
                new StringBuffer("L'initialisation de la consultation des Contrats Placement a été interrompue, Veuillez transmettre ce message à l'équipe informatique: ");
            text.append("Exception au niveau de l'agence:");
            text.append(consultationPlacementForm.getInitialisationView().getCodeAgence());            
            text.append(e.toString()); 
            logger.error(text.toString(), e);
            ActionMessage actionMessage = 
                new ActionMessage("exception.generique", 
                                  erreur.getDescription());
            actionMessages.add("Erreur ", actionMessage);
            this.saveMessages(request, actionMessages);
            return mapping.findForward("error");
        }

    }

    public void afficherListeLiquidationAValider(ActionForm form,ParamAgence paramAgence) {

            ConsultationPlacementForm consultationPlacementForm = (ConsultationPlacementForm)form;
            ActionMessages actionMessages = new ActionMessages();

            try {

                    GetListAvancRembLiquidByEtatCmd  getListAvancRembLiquidByEtatCmd= new GetListAvancRembLiquidByEtatCmd();
                    Listes listes = new Listes();
                    ParamAvanRembLiq paramAvanRembLiq = new ParamAvanRembLiq();
                    paramAvanRembLiq.setCodEtatArl(Constants.ETAT_ARL_VALIDEE);
                    paramAvanRembLiq.setCodStrcStrc(paramAgence.getCodStrcStrc());  
                    paramAvanRembLiq.setNumSeqCpla(Long.valueOf(consultationPlacementForm.getNumCplaRech())); 
                    //paramAvanRembLiq.setCodToprtArl(Constants.CODE_LIQUIDATION_ANTICIPE);    
                    paramAvanRembLiq.setTypeLiquidation("T");
                    
                    listes = (Listes)getListAvancRembLiquidByEtatCmd.execute(paramAvanRembLiq);
                    
                     if (listes.getList() != null && listes.getList().size() > 0) {
                            
                       List listeLiqView = new ArrayList();
                        Set listesLiqSet =new HashSet();
                        listesLiqSet.addAll(listes.getList());                                                          
                        listeLiqView = traiterListeLiquidation(listesLiqSet,consultationPlacementForm);                                
                                
                        consultationPlacementForm.setListeLiquidationAnticipe(listeLiqView);
                        
                      } 
                
            } catch (Exception e) {
                throw new RuntimeException(e);  
            }
        }

    public List traiterListeLiquidation(Set listLiquidation, ActionForm form) {

        
        List listAvanceView = new ArrayList();

       try { 
            if (listLiquidation != null && listLiquidation.size() > 0) {

                for (Iterator it = listLiquidation.iterator(); it.hasNext(); ) {
                    AvancRembLiquid avancRembLiquid = (AvancRembLiquid)it.next();                    
                        AvancRembLiquidView avancRembLiquidView =  new AvancRembLiquidView();
                        avancRembLiquidView.setNumSeqArl(avancRembLiquid.getNumSeqArl().toString());
                        avancRembLiquidView.setDatArlArl(DateHandler.dateToStr(avancRembLiquid.getDatArlArl()));
                        avancRembLiquidView.setMontArlArl(StrHandler.formatmnt(Math.abs(avancRembLiquid.getMontArlArl().doubleValue())));
                        avancRembLiquidView.setNumTauiArl(avancRembLiquid.getNumTauiArl().toString());
                        avancRembLiquidView.setMontInetArl(StrHandler.formatmnt(Math.abs(avancRembLiquid.getMontInetArl().doubleValue())));
                        avancRembLiquidView.setContratPlacement(avancRembLiquid.getContratPlacement());
                        avancRembLiquidView.setMontActuCpla(StrHandler.formatmnt(Math.abs(avancRembLiquid.getContratPlacement().getMontActuCpla())));
                        avancRembLiquidView.setMontCapCpla(StrHandler.formatmnt(Math.abs(avancRembLiquid.getContratPlacement().getMontCapCpla())));
                        if(avancRembLiquid.getCodToprArl().equals(Constants.CODE_LIQUIDATION_ECHEANCE))
                            avancRembLiquidView.setCodToprArl("L.Echéance");
                        else if(avancRembLiquid.getCodToprArl().equals(Constants.CODE_LIQUIDATION_ANTICIPE)) 
                            avancRembLiquidView.setCodToprArl("L.Anticipée");
                        else if(avancRembLiquid.getCodToprArl().equals(Constants.CODE_RESILIATION_PLAC)) 
                            avancRembLiquidView.setCodToprArl("Résiliation");
                        
                    if(avancRembLiquid.getCodTyplArl() != null){
                        if(avancRembLiquid.getCodTyplArl().equals("T") || avancRembLiquid.getCodTyplArl().equals("R"))
                            avancRembLiquidView.setTypeLiquidation("Totale");
                        else avancRembLiquidView.setTypeLiquidation("Partielle");
                    }    
                        listAvanceView.add(avancRembLiquidView);                   
                } 
            }
            
            return listAvanceView;
        }
     catch (Exception e) {
        throw new RuntimeException(e);   
       
    }

    }
    
    
    public void afficherListeAvance(ActionForm form, ParamAgence paramAgence) {

            ConsultationPlacementForm consultationPlacementForm = (ConsultationPlacementForm)form;
            ActionMessages actionMessages = new ActionMessages();

        try {

                GetListAvancRembLiquidByEtatCmd  getListAvancRembLiquidByEtatCmd= new GetListAvancRembLiquidByEtatCmd();
                Listes listesAvances = new Listes();
                ParamAvanRembLiq paramAvanRembLiq = new ParamAvanRembLiq();
                paramAvanRembLiq.setCodEtatArl(Constants.ETAT_ARL_VALIDEE);
                paramAvanRembLiq.setCodStrcStrc(Long.valueOf(paramAgence.getCodStrcStrc()));
                paramAvanRembLiq.setCodToprtArl(Constants.CODE_AVANCE);         
                paramAvanRembLiq.setNumSeqCpla(Long.valueOf(consultationPlacementForm.getNumCplaRech())); 
                listesAvances = (Listes)getListAvancRembLiquidByEtatCmd.execute(paramAvanRembLiq);
                if (listesAvances.getList() != null && listesAvances.getList().size() > 0) {
                        
                            List listeAvanceView = new ArrayList();
                            Set listesAvancesSet = new HashSet();
                            listesAvancesSet.addAll(listesAvances.getList());
                            listeAvanceView = traiterListeAvance(listesAvancesSet,consultationPlacementForm);
                            
                            consultationPlacementForm.setListeAvance(listeAvanceView);
                }               
            
        } catch (Exception e) {
            com.oxia.fwk.core.Error erreur = new com.oxia.fwk.core.Error();
            StringBuffer text = 
                new StringBuffer("");
            text.append(e.getMessage());
            erreur.setCode("200");
            erreur.setDescription(text.toString());

            ActionMessage actionMessage = new ActionMessage("exception.generique", erreur.getDescription());
            actionMessages.add("Erreur ", actionMessage);
        }
    }    

    public List traiterListeAvance(Set listAvance, ActionForm form) {

            List listAvanceView = new ArrayList();

            if (listAvance != null && listAvance.size() > 0) {

                for (Iterator it = listAvance.iterator(); it.hasNext(); ) {
                    AvancRembLiquid avancRembLiquid = (AvancRembLiquid)it.next();
                    
                        AvancRembLiquidView avancRembLiquidView =  new AvancRembLiquidView();
                        avancRembLiquidView.setNumSeqArl(avancRembLiquid.getNumSeqArl().toString());
                        avancRembLiquidView.setDatArlArl(DateHandler.dateToStr(avancRembLiquid.getDatArlArl()));
                        avancRembLiquidView.setMontArlArl(StrHandler.formatmnt(Math.abs(avancRembLiquid.getMontArlArl().doubleValue())));
                        avancRembLiquidView.setNumTauiArl(avancRembLiquid.getNumTauiArl().toString());
                        avancRembLiquidView.setMontInetArl(StrHandler.formatmnt(Math.abs(avancRembLiquid.getMontInetArl().doubleValue())));
                        String duree = Long.valueOf(Math.round(Double.valueOf(DateHandler.getDaysBetween(avancRembLiquid.getDatArlArl(),avancRembLiquid.getDatPrevArl())))).toString();
                        avancRembLiquidView.setDuree(duree);
                        avancRembLiquidView.setDatPrevArl(DateHandler.dateToStr(avancRembLiquid.getDatPrevArl()));
                        avancRembLiquidView.setContratPlacement(avancRembLiquid.getContratPlacement());
                        avancRembLiquidView.setMontActuCpla(StrHandler.formatmnt(Math.abs(avancRembLiquid.getContratPlacement().getMontActuCpla())));
                        avancRembLiquidView.setMontCapCpla(StrHandler.formatmnt(Math.abs(avancRembLiquid.getContratPlacement().getMontCapCpla())));
                        if(avancRembLiquid.getDatReelArl()!=null){
                            // si la date reele de l'avance est garnie alors l'avance est remboursée sion elle ne l'est pas..
                             avancRembLiquidView.setBoolRembAvance("Remboursée");                            
                        }else avancRembLiquidView.setBoolRembAvance("Non remboursée");  
                        
                        if(avancRembLiquid.getCodTypiArl().equals("P")){
                            //intrete Perçus
                             avancRembLiquidView.setTypeInteretAv("Int. Perçus");
                        }else avancRembLiquidView.setTypeInteretAv("Int. Servis");
                        avancRembLiquidView.setDatReelArl(DateHandler.dateToStr(avancRembLiquid.getDatReelArl()));
                        avancRembLiquidView.setMontInetArl(StrHandler.formatmnt(Math.abs(avancRembLiquid.getMontInetArl())));
                        listAvanceView.add(avancRembLiquidView);
                    
                } // Fin For 
            }
            
            return listAvanceView;

        }


    public void afficherListeInteretServi(ActionForm form) {

            ConsultationPlacementForm consultationPlacementForm = (ConsultationPlacementForm)form;
            ActionMessages actionMessages = new ActionMessages();

        try {
               // rechercher la liste des interets servis...
               List listIntView = new ArrayList();
               Date dateIntertMax = null;
               GetListInteretServiCmd getListInteretServiCmd = new GetListInteretServiCmd();
               ContratPlacement contratlPacement = new ContratPlacement();
               contratlPacement.setNumSeqCpla(Long.valueOf(consultationPlacementForm.getNumCplaRech()));
               Listes listInt = (Listes)getListInteretServiCmd.execute(contratlPacement);
               Double montInteret = Double.valueOf(0);
               Double montIRC = Double.valueOf(0);
               Double montInteretBrut = Double.valueOf(0);
               if(listInt.getList().size()>0){
                   for (Iterator it = listInt.getList().iterator(); it.hasNext(); ) {
                           InteretServi interetServi = (InteretServi)it.next();                    
                           InteretServiView interetServiView =  new InteretServiView();
                           interetServiView.setNumIsrvIsrv(interetServi.getNumIsrvIsrv().toString());
                           interetServiView.setDatIsrvIsrv(DateHandler.dateToStr(interetServi.getDatIsrvIsrv()));
                           interetServiView.setMontBrutIrc(StrHandler.formatmnt(Math.abs(interetServi.getMontBrutIsrv().doubleValue())));
                           interetServiView.setMontIrcIsrv(StrHandler.formatmnt(Math.abs(interetServi.getMontIrcIsrv().doubleValue())));
                           interetServiView.setMontIsrvIsrv(StrHandler.formatmnt(Math.abs(interetServi.getMontIsrvIsrv().doubleValue())));                               
                          
                           listIntView.add(interetServiView); 
                           
                       montInteret =Math.abs(interetServi.getMontIsrvIsrv().doubleValue()) + montInteret; 
                       montIRC =Math.abs(interetServi.getMontIrcIsrv().doubleValue()) + montIRC; 
                       montInteretBrut =Math.abs(interetServi.getMontBrutIsrv().doubleValue()) + montInteretBrut; 
                   } 
                   consultationPlacementForm.setSommeInterets(StrHandler.formatmnt(Math.abs(montInteret)));
                   consultationPlacementForm.setSommeIRC(StrHandler.formatmnt(Math.abs(montIRC)));
                   consultationPlacementForm.setSommeInteretsBrut(StrHandler.formatmnt(Math.abs(montInteretBrut)));
                   consultationPlacementForm.setListeInteretServi(listIntView);
                   
               }
            
            
        } catch (Exception e) {
            com.oxia.fwk.core.Error erreur = new com.oxia.fwk.core.Error();
            StringBuffer text = 
                new StringBuffer("");
            text.append(e.getMessage());
            erreur.setCode("200");
            erreur.setDescription(text.toString());

            ActionMessage actionMessage = new ActionMessage("exception.generique", erreur.getDescription());
            actionMessages.add("Erreur ", actionMessage);
        }
    }    


    /**
     * Action qui permet l'impression des listes des liquidations
     */
    public

    ActionForward imprimerListLiquidation(ActionMapping mapping, 
                                               ActionForm form, 
                                               HttpServletRequest request, 
                                               HttpServletResponse response) throws IOException, 
                                                                                    ServletException {
                                                                                    
                                                                                    
        Context context = ContextHandler.getContext();
        ConsultationPlacementForm consultationPlacementForm = 
            (ConsultationPlacementForm)form;
        ActionMessages actionMessages = new ActionMessages();
        
        CommonReportVO valueObject=new CommonReportVO();
        Map parameters = new HashMap();
        String pLibEtat = "P_LIB_ETAT";
        String pMatrUser = "P_NUM_MATR_USER";         
        String pNumCptPlacement="P_NUM_CPT_PLA";
        String vNumCptPlacement = "";
        String vMatrUser = "";  
        String vLibEtat="";
        
        try {
            ParamAgence paramAgence = (ParamAgence)request.getSession().getAttribute("paramAgBNA");
             vMatrUser = paramAgence.getNumMatrUser();
            
             
             vNumCptPlacement = consultationPlacementForm.getNumCplaRech();
             vLibEtat="Liste des liquidations du contrat de placement N° " +vNumCptPlacement;
             valueObject.setNomReport("LiquidationNumCptPla");
           
            
            parameters.put(pLibEtat, vLibEtat);
            parameters.put(pNumCptPlacement, vNumCptPlacement);
            parameters.put(pMatrUser, vMatrUser);
            
            valueObject.setNomDossier("Placement");
            valueObject.setParams(parameters);
            request.getSession().setAttribute("CommonPrintVo",valueObject);
            request.setAttribute("print","1");
            return mapping.findForward("consultationDetailPlacement");
        } catch (Exception e) {
            com.oxia.fwk.core.Error erreur = new com.oxia.fwk.core.Error();
            StringBuffer text = 
                new StringBuffer(" veuillez transmettre ce message à l'équipe informatique: ");
            text.append("Exception au niveau de l'agence:");
            text.append(consultationPlacementForm.getInitialisationView().getCodeAgence());
            text.append(". Exception :");
            text.append(e.toString());           
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
     * Action qui permet l'impression des listes des avances
     */
    public ActionForward imprimerListAvance(ActionMapping mapping, 
                                               ActionForm form, 
                                               HttpServletRequest request, 
                                               HttpServletResponse response) throws IOException, 
                                                                                    ServletException {
                                                                                    
                                                                                    
        ConsultationPlacementForm consultationPlacementForm = 
            (ConsultationPlacementForm)form;
        ActionMessages actionMessages = new ActionMessages();
        
        CommonReportVO valueObject=new CommonReportVO();
        Map parameters = new HashMap();
        String pLibEtat = "P_LIB_ETAT";
        String pMatrUser = "P_NUM_MATR_USER";         
        String pNumCptPlacement="P_NUM_CPT_PLA";
        String vNumCptPlacement = "";
        String vMatrUser = "";  
        String vLibEtat="";
        
        try {
            ParamAgence paramAgence = (ParamAgence)request.getSession().getAttribute("paramAgBNA");
             vMatrUser = paramAgence.getNumMatrUser();
            
             
             vNumCptPlacement = consultationPlacementForm.getNumCplaRech();
             vLibEtat="Liste des Avances du contrat de placement N° " +vNumCptPlacement;
             valueObject.setNomReport("AvancesCptPla");
           
            
            parameters.put(pLibEtat, vLibEtat);
            parameters.put(pNumCptPlacement, vNumCptPlacement);
            parameters.put(pMatrUser, vMatrUser);
            
            valueObject.setNomDossier("Placement");
            valueObject.setParams(parameters);
            request.getSession().setAttribute("CommonPrintVo",valueObject);
            request.setAttribute("print","1");
            return mapping.findForward("consultationDetailPlacement");
        } catch (Exception e) {
            com.oxia.fwk.core.Error erreur = new com.oxia.fwk.core.Error();
            StringBuffer text = 
                new StringBuffer(" veuillez transmettre ce message à l'équipe informatique: ");
            text.append("Exception au niveau de l'agence:");
            text.append(consultationPlacementForm.getInitialisationView().getCodeAgence());
            text.append(". Exception :");
            text.append(e.toString());           
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
    
    public void imprimerRetenueAlaSource(ConsultationPlacementForm consultationPlacementForm , HttpServletRequest request) {
    
              try {    
                 CommonReportVO valueObject = new CommonReportVO();
                 ParamAgence paramAgence = new ParamAgence();
                 paramAgence = (ParamAgence)request.getSession().getAttribute("paramAgBNA");
                 Map parameters = new HashMap();
                 StringBuffer txtNomFichJasper =     new StringBuffer(File.separatorChar);
                 txtNomFichJasper.append("Placement");
                 txtNomFichJasper.append(File.separatorChar);
                 txtNomFichJasper.append("certificatRetenuSource");
                 
                 parameters.put("STRC_CPT",consultationPlacementForm.getCodeAgence());
                 parameters.put("PRD_CPT",consultationPlacementForm.getCodeProduit());
                 parameters.put("CCPT_CPT",consultationPlacementForm.getNumeroCompte());
                 
                 GetInteretServiByIdCmd getInteretServiByIdCmd = new GetInteretServiByIdCmd();
                 InteretServi interetServi=new InteretServi();
                 interetServi.setNumIsrvIsrv(Long.valueOf(consultationPlacementForm.getNumeroIntChoisi()));
                 interetServi = (InteretServi)getInteretServiByIdCmd.execute(interetServi);
                 
                 parameters.put("MONT_BRUT",interetServi.getMontBrutIsrv().toString());
                 parameters.put("MONT_IRC",interetServi.getMontIrcIsrv().toString());
                 parameters.put("MONT_NET",interetServi.getMontIsrvIsrv().toString());
                 parameters.put("DAT_SOUSCRIPTION",consultationPlacementForm.getDatValRech());
                 parameters.put("DUPLICATA",new String("DUPLICATA"));
                 StringBuffer periode = new StringBuffer("du ");
                 periode.append(consultationPlacementForm.getDatValRech());
                 periode.append(" au "); periode.append(consultationPlacementForm.getDatEcheRech()); 
                 parameters.put("PERIODE",periode.toString());
                 valueObject.setParams(parameters);
                 parameters = null;
                 /// indiquer le nom du fichier jasper                   
                 valueObject.setNomReport(txtNomFichJasper.toString());  
                 request.getSession().setAttribute("CommonPrintVo",valueObject);
                 request.setAttribute("print","1");
             }catch (Exception e) {
                logger.error("Exception Methode : imprimerRetenueAlaSource:  ",e);  
                throw new RuntimeException(e);
             }                                                                                
                                                                                               
         } 
         
    public ActionForward imprimerRecapAbonnementAvance(ActionMapping mapping, 
                                               ActionForm form, 
                                               HttpServletRequest request, 
                                               HttpServletResponse response) throws IOException, 
                                                                                    ServletException {
                                                                                    
                                                                                    
        ConsultationPlacementForm consultationPlacementForm = 
            (ConsultationPlacementForm)form;
        ActionMessages actionMessages = new ActionMessages();

              try {    
                 CommonReportVO valueObject = new CommonReportVO();
                 ParamAgence paramAgence = new ParamAgence();
                 paramAgence = (ParamAgence)request.getSession().getAttribute("paramAgBNA");
                 Map parameters = new HashMap();
                                
                 parameters.put("P_LIB_ETAT","Récap. Abonnement Avance");
                 parameters.put("P_NUM_MATR_USER",paramAgence.getNumMatrUser());
                 parameters.put("P_STRUCTURE",paramAgence.getCodStrcStrc().toString());
                 
                 valueObject.setParams(parameters);
                 parameters = null;
                 /// indiquer le nom du fichier jasper    
                 valueObject.setNomDossier("Placement");
                 valueObject.setNomReport("recapAbonAvance");  
                 request.getSession().setAttribute("CommonPrintVo",valueObject);
                 request.setAttribute("print","1");
                 return mapping.findForward("recapIntAbonnPlac");                                                                 
             } catch (Exception e) {
                 com.oxia.fwk.core.Error erreur = new com.oxia.fwk.core.Error();
                 StringBuffer text = 
                     new StringBuffer(" veuillez transmettre ce message à l'équipe informatique: ");
                 text.append("Exception au niveau de l'agence:");
                 text.append(consultationPlacementForm.getInitialisationView().getCodeAgence());
                 text.append(". Exception :");
                 text.append(e.toString());           
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
    public ActionForward imprimerRecapInteretPrecompte(ActionMapping mapping, 
                                               ActionForm form, 
                                               HttpServletRequest request, 
                                               HttpServletResponse response) throws IOException, 
                                                                                    ServletException {
                                                                                    
                                                                                    
        ConsultationPlacementForm consultationPlacementForm = 
            (ConsultationPlacementForm)form;
        ActionMessages actionMessages = new ActionMessages();

              try {    
                 CommonReportVO valueObject = new CommonReportVO();
                 ParamAgence paramAgence = new ParamAgence();
                 paramAgence = (ParamAgence)request.getSession().getAttribute("paramAgBNA");
                 Map parameters = new HashMap();
                                
                 parameters.put("P_LIB_ETAT","Récap. Intérêts. Payable à l'Avance");
                 parameters.put("P_NUM_MATR_USER",paramAgence.getNumMatrUser());
                 parameters.put("P_STRUCTURE",paramAgence.getCodStrcStrc().toString());
                 parameters.put("COD_PINT","PRE");
                 
                 valueObject.setParams(parameters);
                 parameters = null;
                 /// indiquer le nom du fichier jasper    
                 valueObject.setNomDossier("Placement");
                 valueObject.setNomReport("listPlacementAbon");  
                 request.getSession().setAttribute("CommonPrintVo",valueObject);
                 request.setAttribute("print","1");
                 return mapping.findForward("recapIntAbonnPlac");                                                                 
             } catch (Exception e) {
                 com.oxia.fwk.core.Error erreur = new com.oxia.fwk.core.Error();
                 StringBuffer text = 
                     new StringBuffer(" veuillez transmettre ce message à l'équipe informatique: ");
                 text.append("Exception au niveau de l'agence:");
                 text.append(consultationPlacementForm.getInitialisationView().getCodeAgence());
                 text.append(". Exception :");
                 text.append(e.toString());           
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
   public ActionForward imprimerRecapInteretPostcompte(ActionMapping mapping, 
                                               ActionForm form, 
                                               HttpServletRequest request, 
                                               HttpServletResponse response) throws IOException, 
                                                                                    ServletException {
                                                                                    
                                                                                    
        ConsultationPlacementForm consultationPlacementForm = 
            (ConsultationPlacementForm)form;
        ActionMessages actionMessages = new ActionMessages();
        
              try {    
                 CommonReportVO valueObject = new CommonReportVO();
                 ParamAgence paramAgence = new ParamAgence();
                 paramAgence = (ParamAgence)request.getSession().getAttribute("paramAgBNA");
                 Map parameters = new HashMap();
                                
                 parameters.put("P_LIB_ETAT","Récap. Intérêts. Payable à Terme Echu");
                 parameters.put("P_NUM_MATR_USER",paramAgence.getNumMatrUser());
                 parameters.put("P_STRUCTURE",paramAgence.getCodStrcStrc().toString());
                 parameters.put("COD_PINT","POST");
                 
                 valueObject.setParams(parameters);
                 parameters = null;
                 /// indiquer le nom du fichier jasper    
                 valueObject.setNomDossier("Placement");
                 valueObject.setNomReport("listPlacementAbonPOST");  
                 request.getSession().setAttribute("CommonPrintVo",valueObject);
                 request.setAttribute("print","1");
                 return mapping.findForward("recapIntAbonnPlac");
                 
                 } catch (Exception e) {
                     com.oxia.fwk.core.Error erreur = new com.oxia.fwk.core.Error();
                     StringBuffer text = 
                         new StringBuffer(" veuillez transmettre ce message à l'équipe informatique: ");
                     text.append("Exception au niveau de l'agence:");
                     text.append(consultationPlacementForm.getInitialisationView().getCodeAgence());
                     text.append(". Exception :");
                     text.append(e.toString());           
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
     * Action qui permet l'impression des listes de la retenue à la source
     */
    public

    ActionForward imprimerRetenueSource(ActionMapping mapping, 
                                               ActionForm form, 
                                               HttpServletRequest request, 
                                               HttpServletResponse response) throws IOException, 
                                                                                    ServletException {
                                                                                    
                                                                                    
       
        ConsultationPlacementForm consultationPlacementForm = 
            (ConsultationPlacementForm)form;
        ActionMessages actionMessages = new ActionMessages();
       try {
           imprimerRetenueAlaSource(consultationPlacementForm, request) ;
       
            return mapping.findForward("consultationDetailPlacement");
        } catch (Exception e) {
            com.oxia.fwk.core.Error erreur = new com.oxia.fwk.core.Error();
            StringBuffer text = 
                new StringBuffer(" veuillez transmettre ce message à l'équipe informatique: ");
            text.append("Exception au niveau de l'agence:");
            text.append(consultationPlacementForm.getInitialisationView().getCodeAgence());
            text.append(". Exception :");
            text.append(e.toString());           
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
     * Action qui permet l'impression des listes des interets servis 
     */
    public

    ActionForward imprimerListInteret(ActionMapping mapping, 
                                               ActionForm form, 
                                               HttpServletRequest request, 
                                               HttpServletResponse response) throws IOException, 
                                                                                    ServletException {
                                                                                    
                                                                                    
        Context context = ContextHandler.getContext();
        ConsultationPlacementForm consultationPlacementForm = 
            (ConsultationPlacementForm)form;
        ActionMessages actionMessages = new ActionMessages();
        
        CommonReportVO valueObject=new CommonReportVO();
        Map parameters = new HashMap();
        String pLibEtat = "P_LIB_ETAT";
        String pMatrUser = "P_NUM_MATR_USER";         
        String pNumCptPlacement="P_NUM_CPT_PLA";
        String vNumCptPlacement = "";
        String vMatrUser = "";  
        String vLibEtat="";
        
        try {
            ParamAgence paramAgence = (ParamAgence)request.getSession().getAttribute("paramAgBNA");
             vMatrUser = paramAgence.getNumMatrUser();
            
             
             vNumCptPlacement = consultationPlacementForm.getNumCplaRech();
             vLibEtat="Liste des intérêts servis du contrat de placement N° " +vNumCptPlacement;
             valueObject.setNomReport("InteretserviNumCptPLA");
           
            
            parameters.put(pLibEtat, vLibEtat);
            parameters.put(pNumCptPlacement, vNumCptPlacement);
            parameters.put(pMatrUser, vMatrUser);
            
            valueObject.setNomDossier("Placement");
            valueObject.setParams(parameters);
            request.getSession().setAttribute("CommonPrintVo",valueObject);
            request.setAttribute("print","1");
            return mapping.findForward("consultationDetailPlacement");
        } catch (Exception e) {
            com.oxia.fwk.core.Error erreur = new com.oxia.fwk.core.Error();
            StringBuffer text = 
                new StringBuffer(" veuillez transmettre ce message à l'équipe informatique: ");
            text.append("Exception au niveau de l'agence:");
            text.append(consultationPlacementForm.getInitialisationView().getCodeAgence());
            text.append(". Exception :");
            text.append(e.toString());           
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
     * Action qui permet l'impression de l'avis d'opération pour les contrats renouvelés
     */
    public

    ActionForward imprimerAvisOperation(ActionMapping mapping, 
                                               ActionForm form, 
                                               HttpServletRequest request, 
                                               HttpServletResponse response) throws IOException, 
                                                                                    ServletException {
                                                                                    
                                                                                    
        Context context = ContextHandler.getContext();
        ConsultationPlacementForm consultationPlacementForm = 
            (ConsultationPlacementForm)form;
        ActionMessages actionMessages = new ActionMessages();
        
        CommonReportVO valueObject = new CommonReportVO();
        ParamAgence paramAgence = new ParamAgence();
        paramAgence = (ParamAgence)request.getSession().getAttribute("paramAgBNA");
        Map parameters = new HashMap();
        StringBuffer txtNomFichJasper =     new StringBuffer(File.separatorChar);
        txtNomFichJasper.append("Placement");
        txtNomFichJasper.append(File.separatorChar);
     try{
      
       // PRECOMPTE
        if(consultationPlacementForm.getPaiementRech().equals("PRE")){
              txtNomFichJasper.append("AvisOpSouscInt");
          }else { //POSTCOMPTE
               if(consultationPlacementForm.getTypeFaveurPlac().equals("I")){
                   // taux indexé
                    txtNomFichJasper.append("AvisOpSouscTauVar");
               }else {
                   txtNomFichJasper.append("AvisOpSousc");
                   parameters.put("MONT_BRUT_ISRV",consultationPlacementForm.getIntBrut()); 
                   parameters.put("MONT_INET_ISRV",consultationPlacementForm.getIntNet());
                   parameters.put("MONT_IRC_ISRV",consultationPlacementForm.getMntIrc());
                   }
            }
        if(consultationPlacementForm.getCodeOperation().equals(Constants.COD_OPER_SOUSC_PLAC.toString()) 
            || consultationPlacementForm.getCodeOperation().equals(Constants.COD_OPER_SOUSC_PLAC_SBDV.toString())
        ){
               parameters.put("P_LIB_ETAT", "AVIS D'OPERATION : Souscription à un Contrat Placement");
               parameters.put("DUPLICATA", "DUPLICATA");
           }else if(consultationPlacementForm.getCodeOperation().equals(Constants.OPER_RENOUVEL_PLAC_AVAN.toString())){
               parameters.put("P_LIB_ETAT", "AVIS D'OPERATION : Renouvellement Contrat Placement");
               parameters.put("DUPLICATA", " ");
           }
        parameters.put("P_NUM_MATR_USER", paramAgence.getNumMatrUser());
        parameters.put("P_COD_OPER", Long.valueOf(consultationPlacementForm.getCodeOperation()));
        parameters.put("P_NUM_PLAC",Long.valueOf(consultationPlacementForm.getNumCplaRech()));
       
        
        valueObject.setParams(parameters);
        parameters = null;
        /// indiquer le nom du fichier jasper                   
        valueObject.setNomReport(txtNomFichJasper.toString());  
        request.getSession().setAttribute("CommonPrintVo",valueObject);
        request.setAttribute("print","1");
            return mapping.findForward("consultationDetailPlacement");
        } catch (Exception e) {
            com.oxia.fwk.core.Error erreur = new com.oxia.fwk.core.Error();
            StringBuffer text = 
                new StringBuffer(" veuillez transmettre ce message à l'équipe informatique: ");
            text.append("Exception au niveau de l'agence:");
            text.append(consultationPlacementForm.getInitialisationView().getCodeAgence());
            text.append(". Exception :");
            text.append(e.toString());           
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
     * Action qui permet l'impression des listes des avances/Remboursement
     */
    public ActionForward imprimerListARL(ActionMapping mapping, 
                                               ActionForm form, 
                                               HttpServletRequest request, 
                                               HttpServletResponse response) throws IOException, 
                                                                                    ServletException {
                                                                                    
                                                                                    
        ConsultationPlacementForm consultationPlacementForm = (ConsultationPlacementForm)form;
        ActionMessages actionMessages = new ActionMessages();
        
        CommonReportVO valueObject=new CommonReportVO();
        Map parameters = new HashMap();
        String pLibEtat = "P_LIB_ETAT";
        String pMatrUser = "P_NUM_MATR_USER";         
        String vMatrUser = "";  
        String vLibEtat="";
        String pDateDeb="P_DATE_DEBUT";
        String pDateFin="P_DATE_FIN"; 
        String pCodStrcStrc="P_COD_STRC_STRC";
        String pCodTstrcTstrc="P_COD_TSTRC_TSTRC";
        String vDateDeb="";
        String vDateFin="";    
        String vCodStrcStrc="";
        String vCodTstrcTstrc="";
        
        
        try {
            ParamAgence paramAgence = (ParamAgence)request.getSession().getAttribute("paramAgBNA");
             vMatrUser = paramAgence.getNumMatrUser();
            
            vDateDeb = consultationPlacementForm.getDateDebRecherch();
            vDateFin = consultationPlacementForm.getDateFinRecherch();
            if (!consultationPlacementForm.getCodTypeStructure().equalsIgnoreCase("A") && !consultationPlacementForm.getStructureRech().equalsIgnoreCase("")){
                vCodStrcStrc = consultationPlacementForm.getStructureRech().toString();
            }else vCodStrcStrc = paramAgence.getCodStrcStrc().toString();
            vCodTstrcTstrc = consultationPlacementForm.getCodTypeStructure();
            
            if (consultationPlacementForm.getChoix().equals("0")) { ///* le cas de la recherche par type et numéro de pièce
            
                String pNumPiece="P_NUM_PCE_PRES";
                String vNumPiece=consultationPlacementForm.getNumPieceId();
                String pTypePiece="P_COD_TPCE_TPCE";
                String vTypePiece=consultationPlacementForm.getTypePieceId();
                
                if (!consultationPlacementForm.getEtatDemande().equals("0")) {   
                       if (consultationPlacementForm.getEtatDemande().equalsIgnoreCase("A")){//En Attente
                           vLibEtat="Liste des Avances En attente entre le " +vDateDeb+" et le "+vDateFin+" par relation client";
                           valueObject.setNomReport("AvanAttTpce");
                        }else if (consultationPlacementForm.getEtatDemande().equalsIgnoreCase("VNR")){//Validées et non remboursées
                           vLibEtat="Liste des Avances Valides entre le " +vDateDeb+" et le "+vDateFin+" par relation client";
                           valueObject.setNomReport("AvanValideTpce");
                        }else if (consultationPlacementForm.getEtatDemande().equalsIgnoreCase("R")){//remboursée
                             vLibEtat="Liste des Avances Remboursées entre le " +vDateDeb+" et le "+vDateFin+" par relation client";
                             valueObject.setNomReport("AvancesRembTpce");
                        }else if (consultationPlacementForm.getEtatDemande().equalsIgnoreCase("ER")){ //echue non remboursée
                             vLibEtat="Liste des Avances échues non remboursées entre le " +vDateDeb+" et le "+vDateFin+" par relation client";
                             valueObject.setNomReport("AvanEchNonRemTpce");
                        }
                }else{
                            vLibEtat="Liste des Avances entre le " +vDateDeb+" et le "+vDateFin+" par relation client";
                            valueObject.setNomReport("AvanceTpce");
                        }
                parameters.put(pNumPiece,vNumPiece);
                parameters.put(pTypePiece,vTypePiece);
              }else if (consultationPlacementForm.getChoix().equals("1")) { ///* le cas de la recherche par type et numéro de contrat
            
                String pNumContratPla="P_NUM_CPT_PLA";
                String vNumContratPla=consultationPlacementForm.getNumCplaRech();
                if (!consultationPlacementForm.getEtatDemande().equals("0")) {   
                       if (consultationPlacementForm.getEtatDemande().equalsIgnoreCase("A")){//En Attente
                           vLibEtat="Liste des Avances En attente Pentre le " +vDateDeb+" et le "+vDateFin+" par numéro de contrat placement";
                           valueObject.setNomReport("AvanAttCptPla");
                        }else if (consultationPlacementForm.getEtatDemande().equalsIgnoreCase("VNR")){//Validées
                           vLibEtat="Liste des Avances Valides entre le " +vDateDeb+" et le "+vDateFin+" par numéro de contrat placement";
                           valueObject.setNomReport("AvanValideCptPla");
                        }else if (consultationPlacementForm.getEtatDemande().equalsIgnoreCase("R")){//remboursée
                             vLibEtat="Liste des Avances Remboursées entre le " +vDateDeb+" et le "+vDateFin+" par numéro de contrat placement";
                             valueObject.setNomReport("AvancesRembCptPla");
                        }else if (consultationPlacementForm.getEtatDemande().equalsIgnoreCase("ER")){ //echue non remboursée
                             vLibEtat="Liste des Avances échues non remboursées entre le " +vDateDeb+" et le "+vDateFin+" par numéro de contrat placement";
                             valueObject.setNomReport("AvanEchNonRemCptPla");
                        }
                }else{
                            vLibEtat="Liste des Avances entre le " +vDateDeb+" et le "+vDateFin+" numéro de contrat placement";
                            valueObject.setNomReport("AvanceCptPla");
                        }
                parameters.put(pNumContratPla,vNumContratPla);
                
            }else{
                if (!consultationPlacementForm.getEtatDemande().equals("0")) {   
                       if (consultationPlacementForm.getEtatDemande().equalsIgnoreCase("A")){//En Attente
                           vLibEtat="Liste des Avances En attente Pentre le " +vDateDeb+" et le "+vDateFin;
                           valueObject.setNomReport("AvanAttDate");
                        }else if (consultationPlacementForm.getEtatDemande().equalsIgnoreCase("VNR")){//Validées
                           vLibEtat="Liste des Avances Valides entre le " +vDateDeb+" et le "+vDateFin;
                           valueObject.setNomReport("AvanValideDate");
                        }else if (consultationPlacementForm.getEtatDemande().equalsIgnoreCase("R")){//remboursée
                             vLibEtat="Liste des Avances Remboursées entre le " +vDateDeb+" et le "+vDateFin;
                             valueObject.setNomReport("AvancesRembDate");
                        }else if (consultationPlacementForm.getEtatDemande().equalsIgnoreCase("ER")){ //echue non remboursée
                             vLibEtat="Liste des Avances échues non remboursées entre le " +vDateDeb+" et le "+vDateFin;
                             valueObject.setNomReport("AvanEchNonRemDate");
                        }
                }else{
                            vLibEtat="Liste des Avances entre le " +vDateDeb+" et le "+vDateFin;
                            valueObject.setNomReport("AvanceDate");
                        }
            }
            parameters.put(pLibEtat, vLibEtat);
            parameters.put(pMatrUser, vMatrUser);
            parameters.put(pDateDeb, vDateDeb);
            parameters.put(pDateFin, vDateFin);
            parameters.put(pCodStrcStrc, vCodStrcStrc);
            parameters.put(pCodTstrcTstrc, vCodTstrcTstrc);
            
            valueObject.setNomDossier("Placement");
            valueObject.setParams(parameters);
            request.getSession().setAttribute("CommonPrintVo",valueObject);
            request.setAttribute("print","1");
            return mapping.findForward("initConsultARLPlac");
        } catch (Exception e) {
            com.oxia.fwk.core.Error erreur = new com.oxia.fwk.core.Error();
            StringBuffer text = 
                new StringBuffer(" veuillez transmettre ce message à l'équipe informatique: ");
            text.append("Exception au niveau de l'agence:");
            text.append(consultationPlacementForm.getInitialisationView().getCodeAgence());
            text.append(". Exception :");
            text.append(e.toString());           
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
     * Action qui permet l'afficahe de situationmensuelle des placements  
     */
    public   ActionForward consultationMesuellePlacement(ActionMapping mapping, 
                                               ActionForm form, 
                                               HttpServletRequest request, 
                                               HttpServletResponse response) throws IOException, 
                                                                                    ServletException {
        ConsultationPlacementForm consultationPlacementForm = (ConsultationPlacementForm)form;                                                                      
                                                                                    
          GetListCategoriesPersonneCmd getListCategoriesPersonne=new GetListCategoriesPersonneCmd();
          Listes listeCtaegoriesPersonne=new Listes();
          listeCtaegoriesPersonne=(Listes)getListCategoriesPersonne.execute(listeCtaegoriesPersonne);
          
       
        
      consultationPlacementForm.setListCategoriesPersonne(listeCtaegoriesPersonne.getList());
          consultationPlacementForm.setLibelleOperation("Situation Mensuelle Des Souscriptions Placements");
          consultationPlacementForm.setChoix("0");
          consultationPlacementForm.setSituationMensuelle(new ArrayList());
          
            return mapping.findForward("consultMensuelle");
                                         
    }
    
    
    /**
     * Action qui permet l'afficahe de situationmensuelle des placements  
     */
    public   ActionForward rechercheConsultationMesuellePlacement(ActionMapping mapping, 
                                               ActionForm form, 
                                               HttpServletRequest request, 
                                               HttpServletResponse response) throws IOException, 
                                                                                    ServletException {
                                                                                    
                                                                                    
        ConsultationPlacementForm consultationPlacementForm = (ConsultationPlacementForm)form;
        ParamAgence paramAgence = (ParamAgence)request.getSession().getAttribute("paramAgBNA");
       
        String dateComptable=paramAgence.getDateComptable();
        Long codeStructure=paramAgence.getCodStrcStrc();
        ParamDemandeDecision paramDemandeDecision = new ParamDemandeDecision();
        ActionMessages actionMessages = new ActionMessages();
        Long[] listeStructure = new Long[50];
        PersonneStrc personneStrc=new PersonneStrc();
        ContratPersonne contratPersonne=new ContratPersonne();
        List situationMensuelle=new ArrayList();
        List situationMensuelleInt=new ArrayList();
        
        listeStructure[0]=codeStructure;
        paramDemandeDecision.setDateComptable(DateHandler.strToDate(dateComptable));
        paramDemandeDecision.setCodEtatContrat("V");
        paramDemandeDecision.setCodStrcStrc(listeStructure);
        try {
            
            
            if (consultationPlacementForm.getChoix().equals("0")) {
                // traiter le cas de la recherche par type et numéro de pièce
                personneStrc.setCodTpceTpce(new Long(consultationPlacementForm.getTypePieceId()));
                personneStrc.setNumPcePers(consultationPlacementForm.getNumPieceId());
                contratPersonne.setPersonneId(personneStrc);
                paramDemandeDecision.setContratPersonne(contratPersonne);
                GetSituationMensuelleByClientCmd getSituationMensuelleByClientCmd=new GetSituationMensuelleByClientCmd();
                situationMensuelle=((Listes)getSituationMensuelleByClientCmd.execute(paramDemandeDecision)).getList();
                GetSituationMensuelleIntByCltCmd getSituationMensuelleIntByCltCmd =new GetSituationMensuelleIntByCltCmd();
                situationMensuelleInt=((Listes)getSituationMensuelleIntByCltCmd.execute(paramDemandeDecision)).getList();
                
            }else if (consultationPlacementForm.getChoix().equals("1")) {
                // traiter le cas de la recherche par categorie personne
                String categoriePersonne=consultationPlacementForm.getCategoriePersonne();
                paramDemandeDecision.setName(categoriePersonne);
                GetSituationMensuelleByCategorieClientCmd getSituationMensuelleByCategorieClient=new GetSituationMensuelleByCategorieClientCmd();
                situationMensuelle=((Listes)getSituationMensuelleByCategorieClient.execute(paramDemandeDecision)).getList();
                GetSituationMensInteretByCategCltCmd getSituationMensInteretByCategCltCmd=new GetSituationMensInteretByCategCltCmd();
                situationMensuelleInt=((Listes)getSituationMensInteretByCategCltCmd.execute(paramDemandeDecision)).getList();
            }else
            {
            
            paramDemandeDecision.setCodStrcStrc(listeStructure);
            //paramDemandeDecision.setCreationDate(DateHandler.strToDate(consultationPlacementForm.getInitialisationView().getDateActuelle()));
            GetSituationMensuelleByStructureCmd getSituationMensuelleByStructureCmd=new GetSituationMensuelleByStructureCmd();
            GetSituationMensuelleIntByStrcCmd getSituationMensuelleIntByStrcCmd =new GetSituationMensuelleIntByStrcCmd();
            situationMensuelle=((Listes)getSituationMensuelleByStructureCmd.execute(paramDemandeDecision)).getList();
            situationMensuelleInt =((Listes)getSituationMensuelleIntByStrcCmd.execute(paramDemandeDecision)).getList();
            }
            consultationPlacementForm.setSituationMensuelle(situationMensuelle);
            consultationPlacementForm.setSituationMensuelleInt(situationMensuelleInt);
            return mapping.findForward("consultMensuelle");
        }catch (Exception e){
            com.oxia.fwk.core.Error erreur = new com.oxia.fwk.core.Error();
            StringBuffer text = 
                new StringBuffer("La recherche de la liste des contrats placement a été interrompue, veuillez transmettre ce message à l'équipe informatique: ");
            text.append("Exception au niveau de l'agence:");
            text.append(consultationPlacementForm.getInitialisationView().getCodeAgence());
            text.append(". Exception :");
            text.append(e.toString());
            erreur.setCode("298");
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
      * Action qui permet l'impression de la situation mensuelle des placements par structure
      **/
     public ActionForward imprimerSituationMensuelle(ActionMapping mapping, 
                                                ActionForm form, 
                                                HttpServletRequest request, 
                                                HttpServletResponse response) throws IOException, 
                                                                                     ServletException {
                                                                                     
                                                                                     
         ConsultationPlacementForm consultationPlacementForm = (ConsultationPlacementForm)form;
         ActionMessages actionMessages = new ActionMessages();
         
         CommonReportVO valueObject=new CommonReportVO();
         Map parameters = new HashMap();
         String pLibEtat = "P_LIB_ETAT";
         String pMatrUser = "P_NUM_MATR_USER";         
         String vMatrUser = "";  
         String vLibEtat="";
       //  String pDateComptable="P_DATE_COMPTABLE";
         String vDateComptable="";
      //   String pCodStrcStrc="P_COD_STRUCTURE";
         String vCodStrcStrc="";
         String pTpceTpce="P_COD_TPCE_TPCE";
         String pNumPcePers="P_NUM_PCE_PERS";
         String vTpceTpce="";
         String vNumPcePers="";
    
         try {
             ParamAgence paramAgence = (ParamAgence)request.getSession().getAttribute("paramAgBNA");
             vMatrUser = paramAgence.getNumMatrUser();
             vDateComptable = paramAgence.getDateComptable();
             vCodStrcStrc=paramAgence.getCodStrcStrc().toString();
            
            if (consultationPlacementForm.getChoix().equals("0")) {
                 String codeTpceTpce=consultationPlacementForm.getTypePieceId();
                 String numPcePers=consultationPlacementForm.getNumPieceId();
                 vTpceTpce=codeTpceTpce;
                 vNumPcePers=numPcePers;
                 vLibEtat="Situation des placements (en cours) par client";
                 parameters.put(pTpceTpce, vTpceTpce);      
                 parameters.put(pNumPcePers, vNumPcePers);      
                 parameters.put("COD_STRUCTURE", vCodStrcStrc);        
                 valueObject.setNomReport("situationMensTPce");
                 valueObject.setNomDossier("Placement");
             }else if (consultationPlacementForm.getChoix().equals("1")){
                 vLibEtat="Situation mensuelle des placements (en cours) par catégorie personne";
                 parameters.put("DATE_COMPTABLE", vDateComptable);
                 parameters.put("COD_STRUCTURE", vCodStrcStrc); 
                 if(consultationPlacementForm.getCategoriePersonne().equals("null")){
                       valueObject.setNomReport("situationMensCatp");
                   }else {
                       parameters.put("CATEGORIE",consultationPlacementForm.getCategoriePersonne()); 
                       valueObject.setNomReport("situationMensCatPers");
                   }
                 
                 valueObject.setNomDossier("Placement");
             }else{ 
                 vLibEtat="Situation mensuelle des placements (en cours)";
           //      if(paramAgence.getCodTstrcTstrc().equals(Long.valueOf("1"))){
                 if(vCodStrcStrc.equals("900")){
                     valueObject.setNomReport("EtatG_EncoursPlac");
                  }else {
                     parameters.put("DATE_COMPTABLE", vDateComptable);
                     parameters.put("COD_STRUCTURE", vCodStrcStrc);
                     valueObject.setNomReport("situationMensuelle");
                 }
                          
             }
             valueObject.setNomDossier("Placement"); 
             parameters.put(pLibEtat, vLibEtat);
             parameters.put(pMatrUser, vMatrUser);
             //valueObject.setNomDossier("");
             valueObject.setParams(parameters);
             request.getSession().setAttribute("CommonPrintVo",valueObject);
             request.setAttribute("print","1");
             return mapping.findForward("consultMensuelle");
             } catch (Exception e) {
             com.oxia.fwk.core.Error erreur = new com.oxia.fwk.core.Error();
             StringBuffer text = 
                 new StringBuffer(" veuillez transmettre ce message à l'équipe informatique: ");
             text.append("Exception au niveau de l'agence:");
             text.append(consultationPlacementForm.getInitialisationView().getCodeAgence());
             text.append(". Exception :");
             text.append(e.toString());           
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


    public   ActionForward initRecapMouvement(ActionMapping mapping, 
                                               ActionForm form, 
                                               HttpServletRequest request, 
                                               HttpServletResponse response) throws IOException, 
                                                                                    ServletException {
                                                                                    
        ConsultationPlacementForm consultationPlacementForm = (ConsultationPlacementForm)form;
        consultationPlacementForm.setRecapMouvement(new ArrayList());
        consultationPlacementForm.setLibelleOperation("RECAPITULATION MOUVEMENTS PLACEMENT");
        ParamAgence paramAgence = (ParamAgence)request.getSession().getAttribute("paramAgBNA");
        consultationPlacementForm.setDateDebRecherch(paramAgence.getDateComptable());
        return mapping.findForward("recapMouvement");
       
    } 

   public   ActionForward initRecapIneterets(ActionMapping mapping, 
                                                   ActionForm form, 
                                                   HttpServletRequest request, 
                                                   HttpServletResponse response) throws IOException, ServletException {
                                                                                        
                                                                                        
            ConsultationPlacementForm consultationPlacementForm = (ConsultationPlacementForm)form;
            consultationPlacementForm.setLibelleOperation("RECAPITULATION INTERETS PLACEMENT");
            
                return mapping.findForward("recapIntAbonnPlac");
           
        } 
    
    public   ActionForward initConsultationAbonnement(ActionMapping mapping, 
                                               ActionForm form, 
                                               HttpServletRequest request, 
                                               HttpServletResponse response) throws IOException, 
                                                                                    ServletException {
                                                                                    
        ConsultationPlacementForm consultationPlacementForm = (ConsultationPlacementForm)form;                                                                            
        consultationPlacementForm.setLibelleOperation("CONSULTATION DES ABONNEMENTS PAR CONTRAT PLACEMENT");
        consultationPlacementForm.setListeContratPlacement(null);
        consultationPlacementForm.setListeAbonnements(null);
        consultationPlacementForm.setErrorMessage("");
        consultationPlacementForm.setNumCplaRech("");
        consultationPlacementForm.setNumAvanceRecherch("");
            return mapping.findForward("consultationAbonnement");
       
    }    
    
    /**
     * Action qui permet l'affichage du recap des mouvements  
     */
    public   ActionForward recapMouvement(ActionMapping mapping, 
                                               ActionForm form, 
                                               HttpServletRequest request, 
                                               HttpServletResponse response) throws IOException, 
                                                                                    ServletException {
                                                                                    
                                                                                    
        ConsultationPlacementForm consultationPlacementForm = (ConsultationPlacementForm)form;
        ParamAgence paramAgence = (ParamAgence)request.getSession().getAttribute("paramAgBNA");
       
        String dateComptable=consultationPlacementForm.getDateDebRecherch();
        Long codeStructure=paramAgence.getCodStrcStrc();
        ParamDemandeDecision paramDemandeDecision = new ParamDemandeDecision();
        ActionMessages actionMessages = new ActionMessages();
        Long[] listeStructure = new Long[50];
        List recapMouvement=new ArrayList();
        listeStructure[0]=codeStructure;
        paramDemandeDecision.setDateComptable(DateHandler.strToDate(dateComptable));
        //paramDemandeDecision.setCodEtatContrat("V");
        paramDemandeDecision.setCodStrcStrc(listeStructure);
        try {
            
               // personneStrc.setCodTpceTpce(new Long(consultationPlacementForm.getTypePieceId()));
               // personneStrc.setNumPcePers(consultationPlacementForm.getNumPieceId());
              //  contratPersonne.setPersonneId(personneStrc);
               // paramDemandeDecision.setContratPersonne(contratPersonne);
                RecapMouvementPlacementCmd recapMouvementPlacementCmd=new RecapMouvementPlacementCmd();
                recapMouvement=((Listes)recapMouvementPlacementCmd.execute(paramDemandeDecision)).getList();
            consultationPlacementForm.setRecapMouvement(recapMouvement);
            return mapping.findForward("recapMouvement");
        }catch (Exception e){
            com.oxia.fwk.core.Error erreur = new com.oxia.fwk.core.Error();
            StringBuffer text = 
                new StringBuffer("La recherche de la liste des contrats placement a été interrompue, veuillez transmettre ce message à l'équipe informatique: ");
            text.append("Exception au niveau de l'agence:");
            text.append(consultationPlacementForm.getInitialisationView().getCodeAgence());
            text.append(". Exception :");
            text.append(e.toString());
            erreur.setCode("298");
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
     * Action qui permet l'impression de la situation mensuelle des placements par structure
     **/
    public ActionForward imprimerRecapMouvement(ActionMapping mapping, 
                                               ActionForm form, 
                                               HttpServletRequest request, 
                                               HttpServletResponse response) throws IOException, 
                                                                                    ServletException {
                                                                                    
                                                                                    
        ConsultationPlacementForm consultationPlacementForm = (ConsultationPlacementForm)form;
        ActionMessages actionMessages = new ActionMessages();
        
        CommonReportVO valueObject=new CommonReportVO();
        Map parameters = new HashMap();
        String pLibEtat = "P_LIB_ETAT";
        String pMatrUser = "P_NUM_MATR_USER";         
        String vMatrUser = "";  
        String vLibEtat="";
        String pDateComptable="P_DAT_COMPTABLE";
        String vDateComptable="";
        String pCodStrcStrc="P_STRUCTURE";
        String vCodStrcStrc="";

        try {
            ParamAgence paramAgence = (ParamAgence)request.getSession().getAttribute("paramAgBNA");
            vMatrUser = paramAgence.getNumMatrUser();
            vDateComptable = consultationPlacementForm.getDateDebRecherch();
            vCodStrcStrc=paramAgence.getCodStrcStrc().toString();
           
            vLibEtat="Recap des mouvements du "+vDateComptable;
            parameters.put(pDateComptable, vDateComptable);
            parameters.put(pCodStrcStrc, vCodStrcStrc);
            valueObject.setNomReport("RecapMvtOpr");
            valueObject.setNomDossier("Placement");
                  
            parameters.put(pLibEtat, vLibEtat);
            parameters.put(pMatrUser, vMatrUser);
             
            //valueObject.setNomDossier("");
            valueObject.setParams(parameters);
            request.getSession().setAttribute("CommonPrintVo",valueObject);
            request.setAttribute("print","1");
            return mapping.findForward("recapMouvement");
            } 
            catch (Exception e) {
            com.oxia.fwk.core.Error erreur = new com.oxia.fwk.core.Error();
            StringBuffer text = 
                new StringBuffer(" veuillez transmettre ce message à l'équipe informatique: ");
            text.append("Exception au niveau de l'agence:");
            text.append(consultationPlacementForm.getInitialisationView().getCodeAgence());
            text.append(". Exception :");
            text.append(e.toString());           
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
     * Action qui permet d'afficher une les abonnements d'un contrat placement 
     */
    public

    ActionForward rechercherAbonnement(ActionMapping mapping, 
                                                  ActionForm form, 
                                                  HttpServletRequest request, 
                                                  HttpServletResponse response) throws IOException, 
                                                                                       ServletException {

        ConsultationPlacementForm consultationPlacementForm = 
            (ConsultationPlacementForm)form;
        ActionMessages actionMessages = new ActionMessages();
        Long numSeqCpla=null;
        Long[] listeStructure = new Long[50];
        try {
            consultationPlacementForm.setListeContratPlacement(null);
            consultationPlacementForm.setErrorMessage("");
            ParamDemandeDecision paramDemandeDecision =new ParamDemandeDecision();
            if (consultationPlacementForm.getChoix().equals("1")) {
                GetAvancRembLiquidByIdCmd getAvancRembLiquidByIdCmd=new GetAvancRembLiquidByIdCmd();
                AvancRembLiquid avancRembLiquid=new AvancRembLiquid();
                avancRembLiquid.setNumSeqArl(new Long(consultationPlacementForm.getNumAvanceRecherch()));
                avancRembLiquid=(AvancRembLiquid)getAvancRembLiquidByIdCmd.execute(avancRembLiquid);
                numSeqCpla=avancRembLiquid.getContratPlacement().getNumSeqCpla();
            }
            GetListContratsPlacementCmd getListContratsPlacementCmd = new GetListContratsPlacementCmd();
            Listes listContratPlacement = new Listes();
            ContratPlacementView contratPlacementView=new ContratPlacementView();
            PersonneStrc personneStrc = new PersonneStrc();
            paramDemandeDecision.setCodEtatContrat(null);
            if (consultationPlacementForm.getChoix().equals("0")) 
                paramDemandeDecision.setNumSeqCpla(Long.valueOf(consultationPlacementForm.getNumCplaRech()));
            else if (consultationPlacementForm.getChoix().equals("1")) 
                paramDemandeDecision.setNumSeqCpla(numSeqCpla);
                
            List listeAbonnements=new ArrayList();
            listContratPlacement = (Listes)getListContratsPlacementCmd.execute(paramDemandeDecision);
            List ll=listContratPlacement.getList();
            if (ll!=null && ll.size() == 1 ) {
            List listCptPlac = new ArrayList();
            ContratPlacement contratPlacement=new ContratPlacement();
            //List listCptPla = new ArrayList();
                        for (Iterator it = listContratPlacement.getList().iterator(); it.hasNext(); ) {
                            contratPlacement = (ContratPlacement)it.next();
                            listCptPlac.add(contratPlacement);
                        }    
                     contratPlacementView.setLibPrdPrd(contratPlacement.getProduitPlacement().getLibPrdPlc());
                     contratPlacementView.setMontCapCpla(StrHandler.formatmnt(contratPlacement.getMontCapCpla()));
                     contratPlacementView.setNumSeqCpla(contratPlacement.getNumSeqCpla().toString());
                     contratPlacementView.setDatEcheCpla(DateHandler.dateToStr(contratPlacement.getDatEcheCpla()));
                     contratPlacementView.setDatCreCpla(DateHandler.dateToStr(contratPlacement.getDatValCpla()));
                     contratPlacementView.setDuree(contratPlacement.getNumNbrjCpla().toString());
                     contratPlacementView.setNumTauiCpla(contratPlacement.getNumTauiCpla().toString());
                     contratPlacementView.setCodPintCpla(contratPlacement.getCodPintCpla());
                     ContratCptId cptId=contratPlacement.getContratCpt().getContratCptId();
                     String numCcptCcpt= cptId.getCodStrcStrc().toString()+cptId.getCodPrdPrd().toString()+cptId.getNumCcptCcpt().toString();
                     contratPlacementView.setNumCcptCpla(numCcptCcpt);
                     String nomClient=contratPlacement.getContratCpt().getNomIntiCcpt();
                     contratPlacementView.setNomClient(nomClient);
                     consultationPlacementForm.setCodeAgence(StrHandler.lpad(cptId.getCodStrcStrc().toString(),'0',3));
                     consultationPlacementForm.setCodeProduit(StrHandler.lpad(cptId.getCodPrdPrd().toString(),'0',4));
                     consultationPlacementForm.setNumeroCompte(StrHandler.lpad(cptId.getNumCcptCcpt().toString(),'0',6));
                     consultationPlacementForm.setContratPlacementView(contratPlacementView);
                     consultationPlacementForm.setListeContratPlacement(listCptPlac);
                     String cod=contratPlacement.getCodEtatCpla();
                    if (!contratPlacement.getCodEtatCpla().equals("V")) {
                        consultationPlacementForm.setErrorMessage("ContratNonValide");
                        consultationPlacementForm.setListeAbonnements(listeAbonnements);
                        return mapping.findForward("consultationAbonnement");
                    }
                     /* Tableau des abonnement */
                     ParamAbonnementement paramAbonnementement=new ParamAbonnementement();
                     if (consultationPlacementForm.getChoix().equals("1")) {
                         paramAbonnementement.setNumSeqArl(new Long(consultationPlacementForm.getNumAvanceRecherch()));
                     }else if (consultationPlacementForm.getChoix().equals("0")) {
                        paramAbonnementement.setNumSeqCpla(new Long(consultationPlacementForm.getNumCplaRech()));
                     }
                     GetListAbonnementsInteretsCmd getListAbonnementsInteretsCmd=new GetListAbonnementsInteretsCmd();
                     Double someCapital=new Double(0);
                     Long someNbrJour=new Long(0);
                     Double mntAbT=new Double(0);
                     Double mntAbNT=new Double(0);
                     
                     List listeAb=((Listes)getListAbonnementsInteretsCmd.execute(paramAbonnementement)).getList();
                     for (Iterator itr = listeAb.iterator(); itr.hasNext(); ){
                          AbonnementPlacement abpl=(AbonnementPlacement)itr.next();
                          someCapital=someCapital+abpl.getMontAbplAbpl();
                          someNbrJour=someNbrJour+abpl.getNumNbrjAbpl();
                          if (abpl.getCodEtatAbpl().equals("T"))
                              mntAbT =mntAbT+abpl.getMontAbplAbpl();
                         else 
                            mntAbNT=mntAbNT+abpl.getMontAbplAbpl();
                          
                          AbonnementPlacementView abplv= new AbonnementPlacementView();
                          abplv.copyAbonnement(abpl);
                          listeAbonnements.add(abplv);
                     }
                     consultationPlacementForm.setListeAbonnements(listeAbonnements);
                     consultationPlacementForm.setSommeCapital(StrHandler.formatmnt(someCapital));
                     consultationPlacementForm.setSomeNbreAbl(someNbrJour.toString());
                     consultationPlacementForm.setSomeAbT(StrHandler.formatmnt(mntAbT));
                     consultationPlacementForm.setSomeAbNT(StrHandler.formatmnt(mntAbNT));
            }else {
                if (ll==null)
                consultationPlacementForm.setErrorMessage("InvalideCpt");
                consultationPlacementForm.setListeAbonnements(listeAbonnements);
            }
            return mapping.findForward("consultationAbonnement");
        } catch (Exception e) {
            com.oxia.fwk.core.Error erreur = new com.oxia.fwk.core.Error();
            StringBuffer text = 
                new StringBuffer("La recherche des abonnements du contrat placement a été interrompue, veuillez transmettre ce message à l'équipe informatique: ");
            text.append("Exception au niveau de l'agence:");
            text.append(consultationPlacementForm.getInitialisationView().getCodeAgence());
            text.append(". Exception :");
            text.append(e.toString());
            erreur.setCode("298");
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
     * Action qui permet l'impression des abonnements d'un contrat placement
     **/
    public ActionForward imprimerAbonnementContratPlc(ActionMapping mapping, 
                                               ActionForm form, 
                                               HttpServletRequest request, 
                                               HttpServletResponse response) throws IOException, 
                                                                                    ServletException {
        ConsultationPlacementForm consultationPlacementForm = (ConsultationPlacementForm)form;
        ActionMessages actionMessages = new ActionMessages();
        
        CommonReportVO valueObject=new CommonReportVO();
        Map parameters = new HashMap();
        String pLibEtat = "P_LIB_ETAT";
        String pMatrUser = "P_NUM_MATR_USER";         
        String vMatrUser = "";  
        String vLibEtat="";
        String pNumSeqCpla="P_NUM_SEQ_CPLA";
        String pNumSeqArl="P_NUM_SEQ_ARL";
        String vNumSeqCpla="";
        String vNumSeqArl="";
        GetListContratsPlacementCmd getListContratsPlacementCmd = new GetListContratsPlacementCmd();
        Listes listContratPlacement = new Listes();
        ParamDemandeDecision paramDemandeDecision =new ParamDemandeDecision();
        try {
            ParamAgence paramAgence = (ParamAgence)request.getSession().getAttribute("paramAgBNA");
            vMatrUser = paramAgence.getNumMatrUser();
            if (consultationPlacementForm.getChoix().equals("0")) {
             vNumSeqCpla=consultationPlacementForm.getNumCplaRech();
             paramDemandeDecision.setNumSeqCpla(new Long(vNumSeqCpla));
             listContratPlacement = (Listes)getListContratsPlacementCmd.execute(paramDemandeDecision);
             ContratPlacement contratPlacement=new ContratPlacement();
             if (listContratPlacement.getList().size()==1) {
                 contratPlacement=(ContratPlacement)listContratPlacement.getList().get(0);
                 if (contratPlacement.getCodEtatCpla().equals("L"))
                    valueObject.setNomReport("AbonnementTJ");
                  else
                    valueObject.setNomReport("Abonnement"); 
             }
             else {
                 consultationPlacementForm.setErrorMessage("InvalideCpt");
                 return mapping.findForward("consultationAbonnement");
             }
             vLibEtat="Liste des abonnements du contrat N°"+vNumSeqCpla;
             parameters.put(pNumSeqCpla, vNumSeqCpla);
         //    valueObject.setNomReport("Abonnement");
            }
            else if (consultationPlacementForm.getChoix().equals("1")) {
                vNumSeqArl=consultationPlacementForm.getNumAvanceRecherch();
                vNumSeqCpla=consultationPlacementForm.getContratPlacementView().getNumSeqCpla();
                vLibEtat="Liste des abonnements du contrat N°"+vNumSeqCpla+" - "+" Avanace N°"+vNumSeqArl;
                parameters.put(pNumSeqArl, vNumSeqArl);
                valueObject.setNomReport("AbonnementARL");    
            }
            valueObject.setNomDossier("Placement");
            parameters.put(pLibEtat, vLibEtat);
            parameters.put(pMatrUser, vMatrUser);
            valueObject.setParams(parameters);
            request.getSession().setAttribute("CommonPrintVo",valueObject);
            request.setAttribute("print","1");
            return mapping.findForward("consultationAbonnement");
            } catch (Exception e) {
            com.oxia.fwk.core.Error erreur = new com.oxia.fwk.core.Error();
            StringBuffer text = 
                new StringBuffer(" veuillez transmettre ce message à l'équipe informatique: ");
            text.append("Exception au niveau de l'agence:");
            text.append(consultationPlacementForm.getInitialisationView().getCodeAgence());
            text.append(". Exception :");
            text.append(e.toString());           
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
    
    
            
         
   public ActionForward imprimerAvisLiquidation(ActionMapping mapping, 
                                               ActionForm form, 
                                               HttpServletRequest request, 
                                               HttpServletResponse response) throws IOException, 
                                                                                    ServletException {
                                                                                    
                                                                                    
        Context context = ContextHandler.getContext();
        ConsultationPlacementForm consultationPlacementForm = 
            (ConsultationPlacementForm)form;
        ActionMessages actionMessages = new ActionMessages();
        
        CommonReportVO valueObject=new CommonReportVO();
        Map parameters = new HashMap();
        String pLibEtat = "P_LIB_ETAT";
        String pMatrUser = "P_NUM_MATR_USER";         
        String pNumLiq="P_NUM_SEQ_LIQ";
        String vMatrUser = "";  
        String vLibEtat="";
        Long vNumLiq=Long.valueOf("0");
        try {
            ParamAgence paramAgence = (ParamAgence)request.getSession().getAttribute("paramAgBNA");
            vMatrUser = paramAgence.getNumMatrUser();
            
            GetAvancRembLiquidByIdCmd getAvancRembLiquidByIdCmd = new GetAvancRembLiquidByIdCmd();
            AvancRembLiquid avancRembLiquid = new AvancRembLiquid();
            avancRembLiquid.setNumSeqArl(Long.valueOf(consultationPlacementForm.getNumeroLiqChoisi()));
            avancRembLiquid = (AvancRembLiquid)getAvancRembLiquidByIdCmd.execute(avancRembLiquid);
            vNumLiq =  avancRembLiquid.getNumSeqArl();
            
            if(avancRembLiquid.getCodToprArl().equalsIgnoreCase("LIQA")){
              if(avancRembLiquid.getCodTyplArl().equalsIgnoreCase("T"))
                 vLibEtat  ="AVIS D'OPERATION : LIQUIDATION ANTICIPEE TOTALE D'UN CONTRAT DE PLACEMENT";
               else vLibEtat =  "AVIS D'OPERATION : LIQUIDATION PARTIELLE D'UN CONTRAT DE PLACEMENT";
            }else if(avancRembLiquid.getCodToprArl().equalsIgnoreCase("LIQE")){
                 vLibEtat = "AVIS D'OPERATION : LIQUIDATION A ECHEANCE D'UN CONTRAT DE PLACEMENT";
            }else if(avancRembLiquid.getCodToprArl().equalsIgnoreCase("RESL")){
                vLibEtat = "AVIS D'OPERATION : RESILIATION D'UN CONTRAT DE PLACEMENT";
            } 
                          
            valueObject.setNomReport("AvisLiquidationAnticipeDuplicata");
            
            
            parameters.put(pLibEtat, vLibEtat);
            parameters.put(pNumLiq, vNumLiq);
            parameters.put(pMatrUser, vMatrUser);
            
            valueObject.setNomDossier("Placement");
            valueObject.setParams(parameters);
            request.getSession().setAttribute("CommonPrintVo",valueObject);
            request.setAttribute("print","1");
            return mapping.findForward("consultationDetailPlacement");
        } catch (Exception e) {
            com.oxia.fwk.core.Error erreur = new com.oxia.fwk.core.Error();
            StringBuffer text = 
                new StringBuffer(" veuillez transmettre ce message à l'équipe informatique: ");
            text.append("Exception au niveau de l'agence:");
            text.append(consultationPlacementForm.getInitialisationView().getCodeAgence());
            text.append(". Exception :");
            text.append(e.toString());           
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
    
    
    
    
    

   public  ActionForward initStatistique(ActionMapping mapping, ActionForm form, 
                              HttpServletRequest request, 
                              HttpServletResponse response) throws IOException, 
                                                                   ServletException {

        ActionMessages actionMessages = new ActionMessages();
        ConsultationPlacementForm consultationPlacementForm = 
            (ConsultationPlacementForm)form;
        Context context = ContextHandler.getContext();
        try {
            ParamAgence paramAgence = 
                (ParamAgence)request.getSession().getAttribute("paramAgBNA");
            //souscriptionContratPlacementForm.clearFormDemandeDecision();
            consultationPlacementForm.setLibelleOperation("STATISTIQUES PLACEMENT");
            consultationPlacementForm.setChoix("0");
            consultationPlacementForm.getInitialisationView().setDateOp(DateHandler.strToDate(paramAgence.getDateJours()));
            consultationPlacementForm.getInitialisationView().setDateActuelle(paramAgence.getDateComptable());
            consultationPlacementForm.getInitialisationView().setCodeAgence(paramAgence.getCodStrcStrc().toString());
            consultationPlacementForm.setDateDebRecherch(null);
            consultationPlacementForm.setDateFinRecherch(paramAgence.getDateJours());
            
            return mapping.findForward("statPlacement");
        } catch (Exception e) {
            com.oxia.fwk.core.Error erreur = new com.oxia.fwk.core.Error();
            StringBuffer text = 
                new StringBuffer("L'initialisation du domaine Placement a été interrompue, Veuillez transmettre ce message à l'équipe informatique: ");
            text.append("Exception au niveau de l'agence:");
            text.append(consultationPlacementForm.getInitialisationView().getCodeAgence());
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
    
    
    private static java.awt.Image createCamembertPlacementChartImage(String choix,ActionForm form) throws SQLException {

                    
                     ConsultationPlacementForm consultationPlacementForm = 
                         (ConsultationPlacementForm)form;
                    Context context = ContextHandler.getContext();
                    java.util.Hashtable infosPlacement = new Hashtable();
                    String LibChart = ""; 

                    /**
                     * Alimentation de la variable infosPersonnes
                     */

                    // On récupére l'objet Connection
                    
                   PlacementDAO plcDao= (PlacementDAO)context.getBean("placementDAO");
                   List listeStat = new ArrayList();
                   if(choix.equals("0"))
                     listeStat = plcDao.getSumCapitalByProduitPlc(consultationPlacementForm.getEtatDemande(),consultationPlacementForm.getDateDebRecherch(),consultationPlacementForm.getDateFinRecherch());
                   else if(choix.equals("1"))
                       listeStat = plcDao.getNbrPlcParProduitPlc(consultationPlacementForm.getEtatDemande(),consultationPlacementForm.getDateDebRecherch(),consultationPlacementForm.getDateFinRecherch());
                    else if(choix.equals("2"))
                        listeStat = plcDao.getNbrPlcParAgence(consultationPlacementForm.getEtatDemande(),consultationPlacementForm.getDateDebRecherch(),consultationPlacementForm.getDateFinRecherch());
                        
                   
                   
                   ListOrderedMap ListReqPlacement = null;
                  
                  for (Iterator it = listeStat.iterator(); it.hasNext(); ) {
                    ListReqPlacement = (ListOrderedMap)it.next(); 
                      infosPlacement.put(ListReqPlacement.getValue(0).toString(), Double.valueOf(ListReqPlacement.getValue(1).toString()));
                  }                   
                    // create a dataset...

                    DefaultPieDataset data = new DefaultPieDataset();
                    // fill dataset with infosPersonnes datas
                    for (java.util.Enumeration e = infosPlacement.keys(); e
                                    .hasMoreElements();) {
                            String capCpla = (String) e.nextElement();
                            data.setValue(capCpla, (Double) infosPlacement.get(capCpla));
                    }

                    // create a chart with the dataset
                    if(choix.equals("0"))
                         LibChart = "Total Capital par produit de placement";
                    else if(choix.equals("1"))
                        LibChart = "Nombre de placements par produit";
                    else if(choix.equals("2"))
                        LibChart = "Nombre de placements par Agence";
                        
                    JFreeChart chart = ChartFactory.createPieChart(LibChart, data,
                                    true, true, true);
                     
                    // create and return the image

                    return chart.createBufferedImage(500, 220);

            }
            
            
    public

    ActionForward afficherStatistiques(ActionMapping mapping, 
                                                ActionForm form, 
                                                HttpServletRequest request, 
                                                HttpServletResponse response) throws IOException, 
                                                                                     ServletException {

        ConsultationPlacementForm consultationPlacementForm = 
            (ConsultationPlacementForm)form;
        ActionMessages actionMessages = new ActionMessages();
        ParamAgence paramAgence = 
            (ParamAgence)request.getSession().getAttribute("paramAgBNA");
        try {
            CommonReportVO valueObject = new CommonReportVO();
            Map parameters = new HashMap();
            String pLibEtat = "P_LIB_ETAT";
            String vLibEtat = "";
            
            StringBuffer txtNomFichJasper = new StringBuffer("");
            vLibEtat = "";           
           parameters.put("P_STRUCTURE",paramAgence.getCodStrcStrc().toString());
          
             
             // Ajout du parametre matricule utilisateur
           parameters.put("P_NUM_MATR_USER",paramAgence.getNumMatrUser());
           
           txtNomFichJasper.append("listCplac_ccptValidTestChart");   
           StringBuffer txtLibEtat = new StringBuffer(" ");
           vLibEtat = txtLibEtat.toString();
            
            if (consultationPlacementForm.getChoix().equals("0") || consultationPlacementForm.getChoix().equals("1") || consultationPlacementForm.getChoix().equals("2")) {                               
                
                parameters.put("PlacementChart", createCamembertPlacementChartImage(consultationPlacementForm.getChoix(),consultationPlacementForm));               
            
            }else if(consultationPlacementForm.getChoix().equals("3") || consultationPlacementForm.getChoix().equals("4") || consultationPlacementForm.getChoix().equals("5")) {

                parameters.put("PlacementChart", createHistogrammePlacementChartImage(consultationPlacementForm.getChoix(),consultationPlacementForm));               

                
                
            }
            
            parameters.put(pLibEtat,vLibEtat);
                
            valueObject.setParams(parameters);
                
            parameters = null;
                // indiquer le nom du fichier jasper                   
            valueObject.setNomReport(txtNomFichJasper.toString());  
            valueObject.setNomDossier("Placement");
            request.getSession().setAttribute("CommonPrintVo",valueObject);
            request.setAttribute("print","1");
                
            
            return mapping.findForward("statPlacement");
            } catch (Exception e) {
            com.oxia.fwk.core.Error erreur = new com.oxia.fwk.core.Error();
            StringBuffer text = 
                new StringBuffer("la recherche des statistiques a généré des erreurs..., veuillez transmettre ce message à l'équipe informatique: ");
            text.append("Exception au niveau de l'agence:");
            text.append(consultationPlacementForm.getInitialisationView().getCodeAgence());
            text.append(". Exception :");
            text.append(e.toString());            
            erreur.setDescription(text.toString());
            logger.error(text.toString(), e);
            ActionMessage actionMessage = new ActionMessage("exception.generique", erreur.getDescription());
            actionMessages.add("Erreur ", actionMessage);
            this.saveMessages(request, actionMessages);
            return mapping.findForward("error");
            }
      }
    
    private static java.awt.Image createHistogrammePlacementChartImage(String choix,ActionForm form) throws SQLException {

                    
                     ConsultationPlacementForm consultationPlacementForm = 
                         (ConsultationPlacementForm)form;
                    Context context = ContextHandler.getContext();
                    java.util.Hashtable infosPlacement = new Hashtable();
                    String LibChart = ""; 

                    /**
                     * Alimentation de la variable infosPersonnes
                     */

                    // On récupére l'objet Connection
                    
                   PlacementDAO plcDao= (PlacementDAO)context.getBean("placementDAO");
                   List listeStat = new ArrayList();
                   if(choix.equals("3"))
                     listeStat = plcDao.getNbrPlcParProduitParAgence(consultationPlacementForm.getEtatDemande(),consultationPlacementForm.getDateDebRecherch(),consultationPlacementForm.getDateFinRecherch());
                   else if(choix.equals("4"))
                       listeStat = plcDao.getNbrPlcParProduitPlc(consultationPlacementForm.getEtatDemande(),consultationPlacementForm.getDateDebRecherch(),consultationPlacementForm.getDateFinRecherch());
                    else if(choix.equals("5"))
                        listeStat = plcDao.getNbrPlcParAgence(consultationPlacementForm.getEtatDemande(),consultationPlacementForm.getDateDebRecherch(),consultationPlacementForm.getDateFinRecherch());
                        
                   
                   
                   ListOrderedMap ListReqPlacement = null;
                   DefaultCategoryDataset dataset = new DefaultCategoryDataset(); 

                  for (Iterator it = listeStat.iterator(); it.hasNext(); ) {
                    ListReqPlacement = (ListOrderedMap)it.next(); 
                      //infosPlacement.put(ListReqPlacement.getValue(0).toString(), Double.valueOf(ListReqPlacement.getValue(1).toString()));
                       dataset.addValue((Integer.valueOf(ListReqPlacement.getValue(0).toString())).intValue(),ListReqPlacement.getValue(1).toString(),ListReqPlacement.getValue(2).toString());
                       System.out.println((Integer.valueOf(ListReqPlacement.getValue(0).toString())).intValue() + " Prd " + ListReqPlacement.getValue(1).toString()  + " Structure  " + ListReqPlacement.getValue(2).toString()  );
                  }                   
                    
                    // create a chart with the dataset
                     String libAxeVerticale = "";
                     String libAxeHorizontale = "";
                     
                    if(choix.equals("3")){
                         LibChart = "Nombre de placements Par Produit / Par agence";
                         libAxeVerticale = "Nbre de placement";
                         libAxeHorizontale = "Structure";
                    }else if(choix.equals("1"))
                        LibChart = "Nombre de placements par produit";
                    else if(choix.equals("2"))
                        LibChart = "Nombre de placements par Agence";
                        
                   JFreeChart barChart = ChartFactory.createBarChart(LibChart, "Structure", 
                   libAxeVerticale , dataset, PlotOrientation.VERTICAL, true, true, false); 

                     
                    // create and return the image

                    return barChart.createBufferedImage(1000, 450);

            }
            
}
