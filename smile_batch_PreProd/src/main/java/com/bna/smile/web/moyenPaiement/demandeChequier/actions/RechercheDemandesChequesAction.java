package com.bna.smile.web.moyenPaiement.demandeChequier.actions;

import com.bna.commun.model.Chequier;
import com.bna.commun.model.ChequierId;
import com.bna.commun.model.CoTitulaire;
import com.bna.commun.model.ContratCpt;
import com.bna.commun.model.ContratCptId;
import com.bna.commun.model.DemandeCheque;

import com.bna.commun.model.DemandeChequeMandatPersonne;
import com.bna.commun.model.DetailOperationChequier;
import com.bna.commun.model.Mandat;
import com.bna.commun.model.MandatPersonne;
import com.bna.commun.model.MandatPersonneId;
import com.bna.commun.model.MotifRejet;

import com.bna.commun.model.StructureDomaine;
import com.bna.commun.model.Tache;
import com.bna.commun.model.TacheId;
import com.bna.commun.util.DateHandler;
import com.bna.commun.util.StrHandler;
import com.bna.commun.util.DateHandler;
import com.bna.commun.util.StrHandler;
import com.bna.commun.util.ContextHandler;
import com.bna.commun.util.SmileUtil;
import com.bna.smile.model.constant.Constants;

import com.bna.smile.model.domainecommun.commande.GetDetailContratCmd;
import com.bna.smile.model.domainecommun.commande.GetListMembreCotitulaireCmd;

import com.bna.smile.model.domainecommun.model.ContratPersonne;
import com.bna.smile.model.domainecommun.model.Listes;
import com.bna.smile.model.domainecommun.model.PersonneCpt;
import com.bna.smile.model.domainecommun.model.PersonneStrc;
import com.bna.smile.model.domainecontratcompte.moyensPaiement.commande.DelivranceChequiersCmd;
import com.bna.smile.model.domainecontratcompte.moyensPaiement.commande.DestructionChequiersCmd;
import com.bna.smile.model.domainecontratcompte.moyensPaiement.commande.GetDetailChequierCmd;
import com.bna.smile.model.domainecontratcompte.moyensPaiement.commande.GetDetailDemandeChequeCmd;
import com.bna.smile.model.domainecontratcompte.moyensPaiement.commande.GetListDemandeChequePersonneCmd;
import com.bna.smile.model.domainecontratcompte.moyensPaiement.commande.GetListDemandesChequesCmd;
import com.bna.smile.model.domainecontratcompte.moyensPaiement.commande.GetListDemandesChequesMandatPersonneCmd;
import com.bna.smile.model.domainecontratcompte.moyensPaiement.commande.GetListDemandeursChequesMandatPersonneCmd;
import com.bna.smile.model.domainecontratcompte.moyensPaiement.commande.GetListDetailOperationChequierCmd;
import com.bna.smile.model.domainecontratcompte.moyensPaiement.commande.InsertDemandeChequeCmd;
import com.bna.smile.model.domainecontratcompte.moyensPaiement.commande.InsertDemandeChequeMandatPersonneCmd;
import com.bna.smile.model.domainecontratcompte.moyensPaiement.commande.ReceptionChequiersCmd;
import com.bna.smile.model.domainecontratcompte.moyensPaiement.commande.RestitutionChequiersCmd;
import com.bna.smile.model.domainecontratcompte.moyensPaiement.commande.ValidationDemandeChequeCmd;


import com.bna.smile.model.domainecontratcompte.moyensPaiement.dao.DemandeChequeDAO;
import com.bna.smile.model.domainecontratcompte.moyensPaiement.model.DetailDemandeCheque;
import com.bna.smile.model.domainecontratcompte.moyensPaiement.model.ListesDemandesCheques;
import com.bna.smile.model.domainecontratcompte.moyensPaiement.model.ParamDemandeCheque;

import com.bna.smile.model.domainecontratcompte.moyensPaiement.model.Paramchequiers;
import com.bna.smile.model.domainecontratcompte.procuration.commande.GetMandatCmd;
import com.bna.smile.model.domainecontratcompte.procuration.dao.MandatDAO;

import com.bna.smile.model.reporting.model.CommonReportVO;
import com.bna.smile.web.commun.model.ParamAgence;

import com.bna.smile.web.commun.util.SessionUtil;
import com.bna.smile.web.moyenPaiement.demandeChequier.forms.CreationDemandeChequeForm;
import com.bna.smile.web.moyenPaiement.demandeChequier.forms.RechercheDemandesChequesForm;
import com.bna.smile.web.moyenPaiement.demandeChequier.model.ChequierView;
import com.bna.smile.web.moyenPaiement.demandeChequier.util.DemandeChequeView;
import com.bna.smile.web.moyenPaiement.demandeChequier.util.DetailOperationChequierView;

import com.bna.smile.web.procuration.util.MandatView;

import com.bna.smile.web.souscription.actions.SouscriptionContratCompteAction;

import com.oxia.fwk.context.Context;

import fr.improve.struts.taglib.layout.datagrid.Datagrid;

import java.io.File;
import java.io.IOException;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.map.ListOrderedMap;
import org.apache.log4j.Logger;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.actions.DispatchAction;

public class RechercheDemandesChequesAction extends DispatchAction {
    /**This is the main action called from the Struts framework.
     * @param mapping The ActionMapping used to select this instance.
     * @param form The optional ActionForm bean for this request.
     * @param request The HTTP Request we are processing.
     * @param response The HTTP Response we are processing.
     */

     private static final Logger logger = Logger.getLogger(RechercheDemandesChequesAction.class);
    public    ActionForward initierPage(ActionMapping mapping, ActionForm form, 
                              HttpServletRequest request, 
                              HttpServletResponse response) throws IOException, 
                                                                   ServletException {

       
       
        
        Context context = ContextHandler.getContext();
        SessionUtil sessionUtil =new SessionUtil();
        //Suppression des anciens Bean de type Form de la session, SAUF "rechercheDemandesChequesForm"
        sessionUtil.removeSession(request,"rechercheDemandesChequesForm"); 
        
        RechercheDemandesChequesForm rechercheDemandesChequesForm = 
            (RechercheDemandesChequesForm)form;
        ActionMessages actionMessages = new ActionMessages();
        try {
            ParamAgence paramAgence = new ParamAgence();      
            paramAgence = (ParamAgence)request.getSession().getAttribute("paramAgBNA");/// structure de l'agent qui fait la saisie
            
            //verification de l'habilitation sur cet operation
            StructureDomaine structureDomaine = new StructureDomaine(paramAgence.getCodStrcStrc(),Constants.COD_DOM_CONTRATCOMPTE);
            boolean bool = SmileUtil.testDomaineOuvert(structureDomaine);
            
            ParamDemandeCheque paramDemandeCheque = new ParamDemandeCheque();
            DetailDemandeCheque detailDemandeCheque = 
                new DetailDemandeCheque();
            GetDetailDemandeChequeCmd getDetailDemandeChequeCmd = 
                new GetDetailDemandeChequeCmd();
            rechercheDemandesChequesForm.setNumMatrUser(paramAgence.getNumMatrUser().toString());
            rechercheDemandesChequesForm.setCodeAgance(paramAgence.getCodStrcStrc().toString());
            rechercheDemandesChequesForm.setCodStrcRech(paramAgence.getCodStrcStrc().toString());
            //----------------------------------------------------------------------------//
            rechercheDemandesChequesForm.clearTabDemande();
            rechercheDemandesChequesForm.clearTabRecherche();
            rechercheDemandesChequesForm.setChoix("3");
            DateFormat myformat = new SimpleDateFormat("dd/MM/yyyy");
            String d = myformat.format(new Date()); 
            rechercheDemandesChequesForm.setDateActuelle(d);
            if( rechercheDemandesChequesForm.getDateDebRecherch() == null||rechercheDemandesChequesForm.getDateDebRecherch().equals(""))
                rechercheDemandesChequesForm.setDateDebRecherch(d);
            if(rechercheDemandesChequesForm.getDateFinRecherch() == null || rechercheDemandesChequesForm.getDateFinRecherch().equals(""))
                rechercheDemandesChequesForm.setDateFinRecherch(d);
              
            ListesDemandesCheques listDem = new ListesDemandesCheques();
            if (!rechercheDemandesChequesForm.getCodePage().equals(Constants.DESTRUCTION_CARNETS_CHEQUES)) {
                if (!rechercheDemandesChequesForm.getCodePage().equals(Constants.CONSULTATION_DEMANDE_CHQ) && 
                    !rechercheDemandesChequesForm.getCodePage().equals(Constants.CONSULTATION_HISTORIQUE_CHQ)&& 
                    !rechercheDemandesChequesForm.getCodePage().equals(Constants.EDITION_DEMANDE_CHQ) &&
                    !rechercheDemandesChequesForm.getCodePage().equals(Constants.EDITION_CHQUIERS))
                    listDem = 
                            determinerListeDemandesCheques(rechercheDemandesChequesForm);
            } else {
                DemandeChequeDAO demandeChequeDAO = 
                    (DemandeChequeDAO)context.getBean("demandeChequeDAO");
                List listNumDdeChqDetruit = 
                    demandeChequeDAO.getListDemandeChequesPrDestruction(paramAgence.getCodStrcStrc());
                ListOrderedMap ListNumDem = null;
                for (Iterator it = listNumDdeChqDetruit.iterator(); 
                     it.hasNext(); ) {
                    ListNumDem = (ListOrderedMap)it.next();
                    if ((ListNumDem.getValue(0)).toString() != null) {
                        paramDemandeCheque.setNumDemande(ListNumDem.getValue(0).toString());
                        detailDemandeCheque = 
                                (DetailDemandeCheque)getDetailDemandeChequeCmd.execute(paramDemandeCheque);
                        if (!detailDemandeCheque.hasError()) {
                            if (detailDemandeCheque.getDemandeCheque() != 
                                null) {
                                listDem.getListeDemandeChqADetruire().add(detailDemandeCheque.getDemandeCheque());
                            }
                        } else {
                            List listErreur = detailDemandeCheque.getErrors();
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
                }
            }
            // cas de validation de demande
            if (rechercheDemandesChequesForm.getCodePage().equals(Constants.VALIDATION_DEM_CHQ)) {
                // cas validation determiner la liste des demandes de chèques en attente de validation ou envoyé vers DR/DCCI ( toutes les demandes )
                rechercheDemandesChequesForm.setLibelleOperation("Validation demandes de chèques");
                rechercheDemandesChequesForm.setLibPage("Validation demandes de cheques");
                rechercheDemandesChequesForm.setOpenTabSheetChequiers("false");
                if (listDem.getListeAttente() != null && 
                    listDem.getListeAttente().size() > 0) {
                    List listeAttenteView = new ArrayList();
                    listeAttenteView = 
                            traiterListedemandes(listDem.getListeAttente(), 
                                                 rechercheDemandesChequesForm);
                    rechercheDemandesChequesForm.setListeDemandesCheques(listeAttenteView);
                }
            } else {
                rechercheDemandesChequesForm.setOpenTabSheetChequiers("true");
                if (rechercheDemandesChequesForm.getCodePage().equals(Constants.RECEPTION_CARNETS_CHEQUES)) {
                    rechercheDemandesChequesForm.setLibelleOperation("Réception carnets de chèques / Réception plage Lettre de chèque");
                    rechercheDemandesChequesForm.setLibPage("Réception carnets de chèques");
                    if (listDem.getListeValidee() != null && 
                        listDem.getListeValidee().size() > 0) {
                        List listeValideeView = new ArrayList();
                        listeValideeView = 
                                traiterListedemandes(listDem.getListeValidee(), 
                                                     rechercheDemandesChequesForm);
                        rechercheDemandesChequesForm.setListeDemandesCheques(listeValideeView);
                    }
                } else if (rechercheDemandesChequesForm.getCodePage().equals(Constants.DELIVRANCE_CARNETS_CHEQUES)) {
                    rechercheDemandesChequesForm.setLibelleOperation("Délivrance carnets de chèques / Remise plage Lettre de chèque");
                    rechercheDemandesChequesForm.setLibPage("Délivrance carnets de chèques");
                    if (listDem.getListePartSatisfaite() != null && 
                        listDem.getListePartSatisfaite().size() > 0) {
                        Collection listePartSatisfaiteView = 
                            traiterListedemandes(listDem.getListePartSatisfaite(), 
                                                 rechercheDemandesChequesForm);
                        rechercheDemandesChequesForm.getListeDemandesCheques().addAll(listePartSatisfaiteView);
                    }
                    if (listDem.getListeTotSatisfaite() != null && 
                        listDem.getListeTotSatisfaite().size() > 0) {
                        Collection listeTotSatisfaiteView = 
                            traiterListedemandes(listDem.getListeTotSatisfaite(), 
                                                 rechercheDemandesChequesForm);
                        rechercheDemandesChequesForm.getListeDemandesCheques().addAll(listeTotSatisfaiteView);
                    }
                } else if (rechercheDemandesChequesForm.getCodePage().equals(Constants.DESTRUCTION_CARNETS_CHEQUES)) {
                    rechercheDemandesChequesForm.setLibelleOperation("Destruction carnets de chèques");
                    rechercheDemandesChequesForm.setLibPage("Destruction carnets de chèques");
                    if (listDem.getListeDemandeChqADetruire() != null && 
                        listDem.getListeDemandeChqADetruire().size() > 0) {
                        List listeChqDetruireView = new ArrayList();
                        listeChqDetruireView = 
                                traiterListedemandes(listDem.getListeDemandeChqADetruire(), 
                                                     rechercheDemandesChequesForm);
                        rechercheDemandesChequesForm.setListeDemandesCheques(listeChqDetruireView);
                    }
                } else if (rechercheDemandesChequesForm.getCodePage().equals(Constants.RESTITUTION_CARNETS_CHEQUES)) {
                    rechercheDemandesChequesForm.setLibelleOperation("Restitution carnets de chèques");
                    rechercheDemandesChequesForm.setLibPage("Restitution carnets de chèques");
                    if (listDem.getListePartDelivree() != null && 
                        listDem.getListePartDelivree().size() > 0) {
                        Collection listePartDelivreeView = 
                            traiterListedemandes(listDem.getListePartDelivree(), 
                                                 rechercheDemandesChequesForm);
                        rechercheDemandesChequesForm.getListeDemandesCheques().addAll(listePartDelivreeView);
                    }
                    if (listDem.getListeTotDelivree() != null && 
                        listDem.getListeTotDelivree().size() > 0) {
                        Collection listeTotdelivreeView = 
                            traiterListedemandes(listDem.getListeTotDelivree(), 
                                                 rechercheDemandesChequesForm);
                        rechercheDemandesChequesForm.getListeDemandesCheques().addAll(listeTotdelivreeView);
                    }
                } else if (rechercheDemandesChequesForm.getCodePage().equals(Constants.VALIDATION_DEM_CHQ_ENVOYEE_DR)) {
                    rechercheDemandesChequesForm.setLibelleOperation("Réception décision DGR/DCCI");
                    rechercheDemandesChequesForm.setLibPage("Réception décision DGR/DCCI");
                    rechercheDemandesChequesForm.setOpenTabSheetChequiers("false");
                    if (listDem.getListeEnvoyeeDR_DCCI() != null && 
                        listDem.getListeEnvoyeeDR_DCCI().size() > 0) {
                        List listeEnvoyeeDRView = new ArrayList();
                        listeEnvoyeeDRView = 
                                traiterListedemandes(listDem.getListeEnvoyeeDR_DCCI(), 
                                                     rechercheDemandesChequesForm);
                        rechercheDemandesChequesForm.setListeDemandesCheques(listeEnvoyeeDRView);
                    }
                } else if (rechercheDemandesChequesForm.getCodePage().equals(Constants.CONSULTATION_DEMANDE_CHQ)) {
                    rechercheDemandesChequesForm.setLibelleOperation("Consultation demande chèquiers");
                    rechercheDemandesChequesForm.setLibPage("Consultation demande chèquiers");
                        } else if (rechercheDemandesChequesForm.getCodePage().equals(Constants.CONSULTATION_HISTORIQUE_CHQ)) {
                                rechercheDemandesChequesForm.setLibelleOperation("Consultation Historique chèquiers");
                                rechercheDemandesChequesForm.setLibPage("Consultation Historique chèquiers");
                               }else if (rechercheDemandesChequesForm.getCodePage().equals(Constants.EDITION_DEMANDE_CHQ)) {
                                        rechercheDemandesChequesForm.setChoix("2");
                                        rechercheDemandesChequesForm.setLibelleOperation("Edition demandes chèquiers");
                                        rechercheDemandesChequesForm.setLibPage("Edition demandes chèquiers");
                                       }else if (rechercheDemandesChequesForm.getCodePage().equals(Constants.EDITION_CHQUIERS)) {
                                           rechercheDemandesChequesForm.setChoixListe("0");
                                           rechercheDemandesChequesForm.setLibelleOperation("Edition Liste chèquiers");
                                           rechercheDemandesChequesForm.setLibPage("Edition Liste chèquiers");
                                       }


            }
            if (rechercheDemandesChequesForm.getListeDemandesCheques() != 
                null && 
                rechercheDemandesChequesForm.getListeDemandesCheques().size() > 
                0) {
                rechercheDemandesChequesForm.setAlertRecherche("ListeRemplie");
            } else
                rechercheDemandesChequesForm.setAlertRecherche("ListeVide");

            /// Create a new datagrid  (of chequier);
            Datagrid datagridChequiers = Datagrid.getInstance();
            datagridChequiers.setData(new ArrayList());
            datagridChequiers.setDataClass(ChequierView.class);
            rechercheDemandesChequesForm.setListChequiersGrid(datagridChequiers);

            if (rechercheDemandesChequesForm.getCodePage().equals(Constants.EDITION_DEMANDE_CHQ)){
                 return mapping.findForward("edit");
            }else if (rechercheDemandesChequesForm.getCodePage().equals(Constants.EDITION_CHQUIERS)){
                return mapping.findForward("editChequiers");
            }else return mapping.findForward("success");
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

    public ListesDemandesCheques determinerListeDemandesCheques(ActionForm form) {

        RechercheDemandesChequesForm rechercheDemandesChequesForm = 
            (RechercheDemandesChequesForm)form;
        ParamDemandeCheque paramDemandeCheque = new ParamDemandeCheque();
        ContratPersonne contratPersonne = new ContratPersonne();
        ContratCptId contratCptId = new ContratCptId();
        PersonneStrc personneStrc = new PersonneStrc();

        if (rechercheDemandesChequesForm.getCodePage().equals(Constants.CONSULTATION_DEMANDE_CHQ) || 
            rechercheDemandesChequesForm.getCodePage().equals(Constants.CONSULTATION_HISTORIQUE_CHQ)|| 
            //*******************************************************************************************edition
            rechercheDemandesChequesForm.getCodePage().equals(Constants.EDITION_DEMANDE_CHQ)) {
            if (!rechercheDemandesChequesForm.getChoixEtatDemande().equals("0")) {
                paramDemandeCheque.setEtatDemande(new Long(rechercheDemandesChequesForm.getChoixEtatDemande()));
            }
        }

        if (rechercheDemandesChequesForm.getChoix().equals("3")) {
            // traiter le cas général dès l'ouverture de la page JSP ( obtenir toutes les données )
            contratCptId.setCodStrcStrc(new Long(rechercheDemandesChequesForm.getCodeAgance()));
            contratPersonne.setContratCptId(contratCptId);
            contratPersonne.setPersonneId(personneStrc);
            paramDemandeCheque.setContratPersonne(contratPersonne);
            paramDemandeCheque.setDateDebut(DateHandler.strToDate(rechercheDemandesChequesForm.getDateDebRecherch()));
            paramDemandeCheque.setDateFin(DateHandler.strToDate(rechercheDemandesChequesForm.getDateFinRecherch()));
          
        } else if (rechercheDemandesChequesForm.getChoix().equals("0")) {
            // traiter le cas de la recherche par type et numéro de pièce
            personneStrc.setCodTpceTpce(new Long(rechercheDemandesChequesForm.getTypePieceId()));
            personneStrc.setNumPcePers(rechercheDemandesChequesForm.getNumPieceId());
            contratCptId.setCodStrcStrc(new Long(rechercheDemandesChequesForm.getCodeAgance()));
            contratPersonne.setContratCptId(contratCptId);
            contratPersonne.setPersonneId(personneStrc);
            paramDemandeCheque.setContratPersonne(contratPersonne);
        } else if (rechercheDemandesChequesForm.getChoix().equals("1")) {
            // traiter le cas de la recherche par numéro de contrat
            contratCptId.setCodStrcStrc(new Long(rechercheDemandesChequesForm.getCodStrcRech()));
            contratCptId.setCodPrdPrd(new Long(rechercheDemandesChequesForm.getCodPrdRech()));
            contratCptId.setNumCcptCcpt(new Long(rechercheDemandesChequesForm.getNumCcptRech()));
            contratPersonne.setContratCptId(contratCptId);
            contratPersonne.setPersonneId(personneStrc);
            paramDemandeCheque.setContratPersonne(contratPersonne);
        } else if (rechercheDemandesChequesForm.getChoix().equals("2")) {
            // traiter le cas de la recherche par numéro de demande
            contratPersonne.setPersonneId(personneStrc);
            contratPersonne.setContratCptId(contratCptId);
            paramDemandeCheque.setContratPersonne(contratPersonne);
            contratCptId.setCodStrcStrc(new Long(rechercheDemandesChequesForm.getCodeAgance()));
              // le cas de la page d'edition
             if (rechercheDemandesChequesForm.getCodePage().equals(Constants.EDITION_DEMANDE_CHQ)) {
                // traiter le cas de la recherche par date
                paramDemandeCheque.setDateDebut(DateHandler.strToDate(rechercheDemandesChequesForm.getDateDebRecherch()));
                paramDemandeCheque.setDateFin(DateHandler.strToDate(rechercheDemandesChequesForm.getDateFinRecherch()));
                } else {
                    paramDemandeCheque.setNumDemande(rechercheDemandesChequesForm.getNumDemande());
                }
        }
        ListesDemandesCheques listesDemandesCheques = 
            new ListesDemandesCheques();
        GetListDemandesChequesCmd getListDemandesChequesCmd = 
            new GetListDemandesChequesCmd();
        listesDemandesCheques = 
                (ListesDemandesCheques)getListDemandesChequesCmd.execute(paramDemandeCheque);

        return listesDemandesCheques;

    }


    public List traiterListedemandes(List listDemandes, ActionForm form) {

        RechercheDemandesChequesForm rechercheDemandesChequesForm = 
            (RechercheDemandesChequesForm)form;
        List listDemandeChequeView = new ArrayList();
        Context context = ContextHandler.getContext();
        DemandeChequeDAO demandeChequeDAO = 
            (DemandeChequeDAO)context.getBean("demandeChequeDAO");

        boolean ajouter = true;
        if (listDemandes != null && listDemandes.size() > 0) {


            for (Iterator it = listDemandes.iterator(); it.hasNext(); ) {
                DemandeChequeView demandeChequeView = new DemandeChequeView();
                DemandeCheque demandeCheque = (DemandeCheque)it.next();
                if (rechercheDemandesChequesForm.getCodePage().equals(Constants.RESTITUTION_CARNETS_CHEQUES)) {
                    Long nbr = 
                        demandeChequeDAO.getNbreChequierRemiParDemande(demandeCheque.getNumDemDchq());
                    if (nbr.equals(new Long(0))) {
                        // en cas de restitution, il faut que la demande comporte au moins un chequier remi
                        ajouter = false;
                    } else
                        ajouter = true;
                }
                demandeChequeView.setDemandeCheque(demandeCheque);
                demandeChequeView.setDateDemande(DateHandler.dateToStr(demandeCheque.getDatDemDchq()));

                if (ajouter) {
                    if (demandeCheque.getCodEtatDchq() != null) {
                        if (demandeCheque.getCodEtatDchq().equals(Constants.DEM_CHQ_ATTENTE)) {
                            demandeChequeView.setEtatDemande("Attente");
                        } else if (demandeCheque.getCodEtatDchq().equals(Constants.DEM_CHQ_VALIDEE)) {
                            demandeChequeView.setEtatDemande("Validée");
                        } else if (demandeCheque.getCodEtatDchq().equals(Constants.DEM_CHQ_TOT_DELIVREE)) {
                            demandeChequeView.setEtatDemande("Tot Délivrée");
                        } else if (demandeCheque.getCodEtatDchq().equals(Constants.DEM_CHQ_REJETEE)) {
                            demandeChequeView.setEtatDemande("rejetée");
                        } else if (demandeCheque.getCodEtatDchq().equals(Constants.DEM_CHQ_TOT_SATISFAITE)) {
                            demandeChequeView.setEtatDemande("Tot satisfaite");
                        } else if (demandeCheque.getCodEtatDchq().equals(Constants.DEM_CHQ_PART_SATISFAITE)) {
                            demandeChequeView.setEtatDemande("Part satisfaite");
                        } else if (demandeCheque.getCodEtatDchq().equals(Constants.DEM_CHQ_PART_DELIVREE)) {
                            demandeChequeView.setEtatDemande("Part Délivrée");
                        } else if (demandeCheque.getCodEtatDchq().equals(Constants.DEM_CHQ_ENVOYEE_DR)) {
                            demandeChequeView.setEtatDemande("Envoyée DGR/DCCI");
                        }
                    }
                    if (demandeCheque.getCodTpceDchq().equals(Constants.COD_CIN))
                        demandeChequeView.setTypePieceDem("CIN");
                    else if (demandeCheque.getCodTpceDchq().equals(Constants.COD_RCS))
                        demandeChequeView.setTypePieceDem("RCS");
                    else if (demandeCheque.getCodTpceDchq().equals(Constants.COD_NUM_ORDRE))
                        demandeChequeView.setTypePieceDem("NUM");

                    demandeChequeView.setCodeAgence(StrHandler.lpad(demandeCheque.getContratCpt().getContratCptId().getCodStrcStrc().toString(), 
                                                                    '0', 3));
                    demandeChequeView.setCodeProduit(StrHandler.lpad(demandeCheque.getContratCpt().getContratCptId().getCodPrdPrd().toString(), 
                                                                     '0', 4));
                    demandeChequeView.setNumeroCompte(StrHandler.lpad(demandeCheque.getContratCpt().getContratCptId().getNumCcptCcpt().toString(), 
                                                                      '0', 6));
                    
                    
                    if (demandeCheque.getBoolWebDchq() != null) {
                       if(demandeCheque.getBoolWebDchq().equals(Long.valueOf("1")))
                          demandeChequeView.setBoolWebDchq("Web");
                       else if(demandeCheque.getBoolWebDchq().equals(Long.valueOf("2")))
                          demandeChequeView.setBoolWebDchq("SMS");
                    }else demandeChequeView.setBoolWebDchq("Smile");  

                    listDemandeChequeView.add(demandeChequeView);
                }
            } // Fin For  
        }
        return listDemandeChequeView;

    }
/**
     * permet d'imprimer la liste des demandes de chéquiers selon leur etat, renvoie (forward) vers /reporting/editionDemandesChequiers
     * @param form com.bna.smile.web.moyenPaiement.demandeChequier.rechercheDemandesChequesForm
     *
     * @throws IOException
     * @throws ServletException
     */
    public ActionForward imprimerDemandesSelonChoix(ActionMapping mapping, 
                                                      ActionForm form, 
                                                      HttpServletRequest request, 
                                                      HttpServletResponse response) throws IOException, 
                                                                                           ServletException {
         RechercheDemandesChequesForm rechercheDemandesChequesForm = 
             (RechercheDemandesChequesForm)form;
         ActionMessages actionMessages = new ActionMessages();
         try {    
             CommonReportVO valueObject = new CommonReportVO();
             ParamAgence paramAgence = new ParamAgence();
             paramAgence = (ParamAgence)request.getSession().getAttribute("paramAgBNA");
             Map parameters = new HashMap();
             String vMatrUser = paramAgence.getNumMatrUser().toString();
             
            /*------------------------------------------------------------------*/
             String pCodStrcStrc = "P_COD_STRC_STRC";
             String pCodProd = "P_COD_PRD_PRD";
             String pNumContratCpt = "P_NUM_CCPT_CCPT";
             
             String vCodStrcStrc = paramAgence.getCodStrcStrc().toString();
             String vCodProd = "";
             String vNumContratCpt = "";
            
             /*-----------------------------------------------------------------*/
              String pCodTpceTpce = "P_COD_TPCE_TPCE";
              String pNumTpceTpce = "P_NUM_PCE_PERS";
             String vCodTpceTpce = "";
             String vNumTpceTpce = "";
            /*------------------------------------------------------------------*/
             String pDateDeb = "P_DATE_DEB";
             String pDateFin = "P_DATE_FIN";
             String vDateFin="";
             String vDateDeb="";
             /*-----------------------------------------------------------------*/
             String pEtat = "P_VALIDE";
             String pMatrUser = "P_NUM_MATR_USER";
             String pLibEtat="P_LIB_ETAT";
             String vLibEtat="";
             String vEtat="";
             
             String tt=rechercheDemandesChequesForm.getChoixEtatDemande();
             if (rechercheDemandesChequesForm.getChoixEtatDemande().equals(Constants.DEM_CHQ_ATTENTE.toString())){
                   vLibEtat = "Liste des demandes de chéquiers (En attentes)";
                   vEtat=Constants.DEM_CHQ_ATTENTE.toString();
                   }else if (rechercheDemandesChequesForm.getChoixEtatDemande().equals(Constants.DEM_CHQ_VALIDEE.toString())){
                           vLibEtat = "Liste des demandes de chéquiers (Validées)";
                           vEtat=Constants.DEM_CHQ_VALIDEE.toString();
                           } else if (rechercheDemandesChequesForm.getChoixEtatDemande().equals(Constants.DEM_CHQ_REJETEE.toString())){
                                   vLibEtat = "Liste des demandes de chéquiers (Rejetées)";
                                   vEtat=Constants.DEM_CHQ_REJETEE.toString();
                                   }else if (rechercheDemandesChequesForm.getChoixEtatDemande().equals(Constants.DEM_CHQ_TOT_SATISFAITE.toString())){
                                    vLibEtat = "Liste des demandes de chéquiers (Totalement satisfaites)";
                                    vEtat=Constants.DEM_CHQ_TOT_SATISFAITE.toString();
                                   }else if (rechercheDemandesChequesForm.getChoixEtatDemande().equals(Constants.DEM_CHQ_ENVOYEE_DR.toString())){
                                       vLibEtat = "Liste des demandes de chéquiers (Envoyées DGR/DCCI)";
                                       vEtat=Constants.DEM_CHQ_ENVOYEE_DR.toString();
                                       }else if (rechercheDemandesChequesForm.getChoixEtatDemande().equals(Constants.DEM_CHQ_PART_DELIVREE.toString())){
                                              vLibEtat = "Liste des demandes de chéquiers (Partiellement délivrées)";
                                              vEtat=Constants.DEM_CHQ_PART_DELIVREE.toString(); 
                                               }else if (rechercheDemandesChequesForm.getChoixEtatDemande().equals(Constants.DEM_CHQ_PART_SATISFAITE.toString())){
                                                        vLibEtat = "Liste des demandes de chéquiers (Partiellement satisfaites)";
                                                        vEtat=Constants.DEM_CHQ_PART_SATISFAITE.toString();
                                                       }else if (rechercheDemandesChequesForm.getChoixEtatDemande().equals(Constants.DEM_CHQ_TOT_DELIVREE.toString())){
                                                                vLibEtat = "Liste des demandes de chéquiers (Totalement délivrées)";
                                                                vEtat=Constants.DEM_CHQ_TOT_DELIVREE.toString();
                                                               }else {
                                                                   vLibEtat = "Liste des demandes de chéquiers";
                                                                   vEtat="";
                                                               }
               
             if (rechercheDemandesChequesForm.getChoix().equals("2")) {
                   vDateFin=rechercheDemandesChequesForm.getDateFinRecherch();
                   vDateDeb=rechercheDemandesChequesForm.getDateDebRecherch();
                   parameters.put(pDateDeb,vDateDeb);
                   parameters.put(pDateFin,vDateFin);
                   
                     if (rechercheDemandesChequesForm.getChoixEtatDemande().equals(Constants.DEM_CHQ_REJETEE.toString())){
                         valueObject.setNomReport("DemChq_rejet");
                     }else {
                         valueObject.setNomReport("DemCrChq");
                         }
                 } else if (rechercheDemandesChequesForm.getChoix().equals("1")) {// recherche par num contrat
                 
                            vCodProd= rechercheDemandesChequesForm.getCodPrdRech();
                            vNumContratCpt = rechercheDemandesChequesForm.getNumCcptRech();
                            parameters.put(pCodProd,vCodProd);
                            parameters.put(pNumContratCpt,vNumContratCpt);
                            
                          if (!rechercheDemandesChequesForm.getChoixEtatDemande().equals("0")){
                               if (rechercheDemandesChequesForm.getChoixEtatDemande().equals(Constants.DEM_CHQ_REJETEE.toString())){
                                    valueObject.setNomReport("DemChqNumcpt_rejet");
                                     }else { valueObject.setNomReport("NumCptEtat");
                                                                          }
                               } else {
                                           valueObject.setNomReport("DemCrChqNumcpt");
                                           }
                          } else if (rechercheDemandesChequesForm.getChoix().equals("0")) { // recherche par Type pièce
                                        vCodTpceTpce = rechercheDemandesChequesForm.getTypePieceId();
                                        vNumTpceTpce = rechercheDemandesChequesForm.getNumPieceId();
                                        parameters.put(pCodTpceTpce,vCodTpceTpce);
                                        parameters.put(pNumTpceTpce,vNumTpceTpce);
                                        if (!rechercheDemandesChequesForm.getChoixEtatDemande().equals("0")){
                                               if (rechercheDemandesChequesForm.getChoixEtatDemande().equals(Constants.DEM_CHQ_REJETEE.toString())){
                                                     valueObject.setNomReport("DemChqTP_rejet");
                                                    }else {
                                                         valueObject.setNomReport("DemCrChqTPEtat"); }
                                           } else{
                                                           valueObject.setNomReport("DemCrChqTP");
                                                         }
                                    }
         
             parameters.put(pMatrUser, vMatrUser);
             parameters.put(pLibEtat, vLibEtat);
             parameters.put(pCodStrcStrc, vCodStrcStrc);
             parameters.put(pEtat, vEtat);
             
             valueObject.setParams(parameters);
             
             request.getSession().setAttribute("CommonPrintVo",valueObject);
             request.setAttribute("print","1");
             
         return mapping.findForward("edit");
         } catch (Exception e) {
         com.oxia.fwk.core.Error erreur = new com.oxia.fwk.core.Error();
         StringBuffer text = 
             new StringBuffer("la transaction est Interrompu, une erreur dans RechercheDemandesChequesAction / Dispatch Action :imprimerDemandesSelonChoix ");
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
    public ActionForward rechercherDemandesSelonChoix(ActionMapping mapping, 
                                                      ActionForm form, 
                                                      HttpServletRequest request, 
                                                      HttpServletResponse response) throws IOException, 
                                                                                           ServletException {

        RechercheDemandesChequesForm rechercheDemandesChequesForm = 
            (RechercheDemandesChequesForm)form;
        ActionMessages actionMessages = new ActionMessages();
       
        try {
            ListesDemandesCheques listDem = 
                determinerListeDemandesCheques(rechercheDemandesChequesForm);
            rechercheDemandesChequesForm.getListeDemandesCheques().clear();

            if (rechercheDemandesChequesForm.getCodePage().equals(Constants.VALIDATION_DEM_CHQ)) {
                if (listDem.getListeAttente() != null && 
                    listDem.getListeAttente().size() > 0) {
                    List listeAttenteView = new ArrayList();
                    listeAttenteView = 
                            traiterListedemandes(listDem.getListeAttente(), 
                                                 rechercheDemandesChequesForm);
                    rechercheDemandesChequesForm.setListeDemandesCheques(listeAttenteView);
                }
            } else if (rechercheDemandesChequesForm.getCodePage().equals(Constants.RECEPTION_CARNETS_CHEQUES)) {
                if (listDem.getListeValidee() != null && 
                    listDem.getListeValidee().size() > 0) {
                    List listeValideeView = new ArrayList();
                    listeValideeView = 
                            traiterListedemandes(listDem.getListeValidee(), 
                                                 rechercheDemandesChequesForm);
                    rechercheDemandesChequesForm.setListeDemandesCheques(listeValideeView);
                }
            } else if (rechercheDemandesChequesForm.getCodePage().equals(Constants.DELIVRANCE_CARNETS_CHEQUES)) {
                if (listDem.getListePartSatisfaite() != null && 
                    listDem.getListePartSatisfaite().size() > 0) {
                    Collection listePartSatisfaiteView = 
                        traiterListedemandes(listDem.getListePartSatisfaite(), 
                                             rechercheDemandesChequesForm);
                    rechercheDemandesChequesForm.getListeDemandesCheques().addAll(listePartSatisfaiteView);
                }
                if (listDem.getListeTotSatisfaite() != null && 
                    listDem.getListeTotSatisfaite().size() > 0) {
                    Collection listeTotSatisfaiteView = 
                        traiterListedemandes(listDem.getListeTotSatisfaite(), 
                                             rechercheDemandesChequesForm);
                    rechercheDemandesChequesForm.getListeDemandesCheques().addAll(listeTotSatisfaiteView);
                }
            } else if (rechercheDemandesChequesForm.getCodePage().equals(Constants.RESTITUTION_CARNETS_CHEQUES)) {
                if (listDem.getListePartDelivree() != null && 
                    listDem.getListePartDelivree().size() > 0) {
                    Collection listePartDelivreeView = 
                        traiterListedemandes(listDem.getListePartDelivree(), 
                                             rechercheDemandesChequesForm);
                    rechercheDemandesChequesForm.getListeDemandesCheques().addAll(listePartDelivreeView);
                }
                if (listDem.getListeTotDelivree() != null && 
                    listDem.getListeTotDelivree().size() > 0) {
                    Collection listeTotdelivreeView = 
                        traiterListedemandes(listDem.getListeTotDelivree(), 
                                             rechercheDemandesChequesForm);
                    rechercheDemandesChequesForm.getListeDemandesCheques().addAll(listeTotdelivreeView);
                }
            } else if (rechercheDemandesChequesForm.getCodePage().equals(Constants.VALIDATION_DEM_CHQ_ENVOYEE_DR)) {
                if (listDem.getListeEnvoyeeDR_DCCI() != null && 
                    listDem.getListeEnvoyeeDR_DCCI().size() > 0) {
                    List listeEnvoyeeDRView = new ArrayList();
                    listeEnvoyeeDRView = 
                            traiterListedemandes(listDem.getListeEnvoyeeDR_DCCI(), 
                                                 rechercheDemandesChequesForm);
                    rechercheDemandesChequesForm.setListeDemandesCheques(listeEnvoyeeDRView);
                }
            } else if (rechercheDemandesChequesForm.getCodePage().equals(Constants.CONSULTATION_DEMANDE_CHQ) || 
                       rechercheDemandesChequesForm.getCodePage().equals(Constants.CONSULTATION_HISTORIQUE_CHQ)|| 
                       rechercheDemandesChequesForm.getCodePage().equals(Constants.EDITION_DEMANDE_CHQ)) {
                List listeView = new ArrayList();
                if (rechercheDemandesChequesForm.getChoixEtatDemande().equals("0")) {
                    // retourner toutes les demandes de toutes les etats
                    if (listDem.getListeGenerale() != null && 
                        listDem.getListeGenerale().size() > 0) {
                        listeView = 
                                traiterListedemandes(listDem.getListeGenerale(), 
                                                     rechercheDemandesChequesForm);
                        rechercheDemandesChequesForm.setListeDemandesCheques(listeView);
                    }
                } else if (rechercheDemandesChequesForm.getChoixEtatDemande().equals(Constants.DEM_CHQ_ATTENTE.toString())) {
                    listeView = 
                            traiterListedemandes(listDem.getListeAttente(), rechercheDemandesChequesForm);
                    rechercheDemandesChequesForm.setListeDemandesCheques(listeView);
                } else if (rechercheDemandesChequesForm.getChoixEtatDemande().equals(Constants.DEM_CHQ_VALIDEE.toString())) {
                    listeView = 
                            traiterListedemandes(listDem.getListeValidee(), rechercheDemandesChequesForm);
                    rechercheDemandesChequesForm.setListeDemandesCheques(listeView);
                } else if (rechercheDemandesChequesForm.getChoixEtatDemande().equals(Constants.DEM_CHQ_PART_SATISFAITE.toString())) {
                    listeView = 
                            traiterListedemandes(listDem.getListePartSatisfaite(), 
                                                 rechercheDemandesChequesForm);
                    rechercheDemandesChequesForm.setListeDemandesCheques(listeView);
                } else if (rechercheDemandesChequesForm.getChoixEtatDemande().equals(Constants.DEM_CHQ_TOT_SATISFAITE.toString())) {
                    listeView = 
                            traiterListedemandes(listDem.getListeTotSatisfaite(), 
                                                 rechercheDemandesChequesForm);
                    rechercheDemandesChequesForm.setListeDemandesCheques(listeView);
                } else if (rechercheDemandesChequesForm.getChoixEtatDemande().equals(Constants.DEM_CHQ_PART_DELIVREE.toString())) {
                    listeView = 
                            traiterListedemandes(listDem.getListePartDelivree(), 
                                                 rechercheDemandesChequesForm);
                    rechercheDemandesChequesForm.setListeDemandesCheques(listeView);
                } else if (rechercheDemandesChequesForm.getChoixEtatDemande().equals(Constants.DEM_CHQ_TOT_DELIVREE.toString())) {
                    listeView = 
                            traiterListedemandes(listDem.getListeTotDelivree(), 
                                                 rechercheDemandesChequesForm);
                    rechercheDemandesChequesForm.setListeDemandesCheques(listeView);
                } else if (rechercheDemandesChequesForm.getChoixEtatDemande().equals(Constants.DEM_CHQ_ENVOYEE_DR.toString())) {
                    listeView = 
                            traiterListedemandes(listDem.getListeEnvoyeeDR_DCCI(), 
                                                 rechercheDemandesChequesForm);
                    rechercheDemandesChequesForm.setListeDemandesCheques(listeView);
                } else if (rechercheDemandesChequesForm.getChoixEtatDemande().equals(Constants.DEM_CHQ_REJETEE.toString())) {
                    listeView = 
                            traiterListedemandes(listDem.getListeRejetee(), rechercheDemandesChequesForm);
                    rechercheDemandesChequesForm.setListeDemandesCheques(listeView);
                }

            }


            if (rechercheDemandesChequesForm.getListeDemandesCheques() != 
                null && 
                rechercheDemandesChequesForm.getListeDemandesCheques().size() > 
                0) {
                rechercheDemandesChequesForm.setAlertRecherche("ListeRemplie");
            } else
                rechercheDemandesChequesForm.setAlertRecherche("ListeVide");

          //  return mapping.findForward("success");
           if (!rechercheDemandesChequesForm.getCodePage().equals(Constants.EDITION_DEMANDE_CHQ)){
                   return mapping.findForward("success");
           }else {
               return mapping.findForward("edit");
           }
        } catch (Exception e) {
            com.oxia.fwk.core.Error erreur = new com.oxia.fwk.core.Error();
            StringBuffer text = 
                new StringBuffer("la transaction est Interrompu, une erreur dans RechercheDemandesChequesAction / Dispatch Action :rechercherDemandesSelonChoix ");
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

    public ActionForward choisirDemande(ActionMapping mapping, ActionForm form, 
                                        HttpServletRequest request, 
                                        HttpServletResponse response) throws IOException, 
                                                                             ServletException {


        Context context = ContextHandler.getContext();
        RechercheDemandesChequesForm rechercheDemandesChequesForm = 
            (RechercheDemandesChequesForm)form;
        ActionMessages actionMessages = new ActionMessages();
        try {
            DemandeCheque demandeCheque = new DemandeCheque();
            DetailDemandeCheque detailDemandeCheque = 
                new DetailDemandeCheque();
            GetDetailDemandeChequeCmd getDetailDemandeChequeCmd = 
                new GetDetailDemandeChequeCmd();
            ParamDemandeCheque paramDemandeCheque = new ParamDemandeCheque();
            GetDetailContratCmd getDetailContratCmd = 
                new GetDetailContratCmd();
            ContratCptId contratCptId = new ContratCptId();
            boolean controle = true;
            boolean valide = true;
            boolean verification = true;

            if (rechercheDemandesChequesForm.getCodePage().equals(Constants.VALIDATION_DEM_CHQ) || 
                rechercheDemandesChequesForm.getCodePage().equals(Constants.DELIVRANCE_CARNETS_CHEQUES) || 
                rechercheDemandesChequesForm.getCodePage().equals(Constants.VALIDATION_DEM_CHQ_ENVOYEE_DR)) {
                verification = true;
            } else
                verification = false;

            if (rechercheDemandesChequesForm.getNumDemandeChoisie() != null) {
                paramDemandeCheque.setNumDemande(rechercheDemandesChequesForm.getNumDemandeChoisie());
                rechercheDemandesChequesForm.setMessageTexte("");
                rechercheDemandesChequesForm.setMessageRestitution("");

                detailDemandeCheque = 
                        (DetailDemandeCheque)getDetailDemandeChequeCmd.execute(paramDemandeCheque);
                if (!detailDemandeCheque.hasError()) {
                    if (detailDemandeCheque != null && 
                        detailDemandeCheque.getDemandeCheque().getNumDemDchq() != 
                        null) {
                        rechercheDemandesChequesForm.setDemandeCheque(detailDemandeCheque.getDemandeCheque());
                        // Partie réservée pour le contrôle du contrat et du pouvoir du demandeur lors des opérations autre que la création
                        if (!detailDemandeCheque.getDemandeCheque().getContratCpt().getCodEtatCcpt().equals(Constants.COD_ETAT_CPT_VALID) && 
                            verification) {
                            controle = false;
                            rechercheDemandesChequesForm.setMotifRejet(Constants.MOTIF_DEM_CPT_INVALIDE.toString());
                            rechercheDemandesChequesForm.setAlert("contratNonValide");
                        }
                        if (detailDemandeCheque.getDemandeCheque().getCodDemDchq().equals("M") && 
                            controle) {
                            // extraire le mandat de la demande...
                            // contrôler la validité du mandat, du pouvoir et des manadataires valides
                            DemandeChequeDAO demandeChequeDAO = 
                                (DemandeChequeDAO)context.getBean("demandeChequeDAO");
                            Mandat mandat = new Mandat();
                            GetMandatCmd getMandatCmd = new GetMandatCmd();
                            GetListDemandeursChequesMandatPersonneCmd getListDemandeursChequesMandatPersonneCmd = 
                                new GetListDemandeursChequesMandatPersonneCmd();
                            ListesDemandesCheques listesDemandesCheques = 
                                new ListesDemandesCheques();
                            mandat.setNumMandMand(demandeChequeDAO.getNumeroMandatParDemande(detailDemandeCheque.getDemandeCheque().getNumDemDchq()));
                            mandat = (Mandat)getMandatCmd.execute(mandat);
                            if (!mandat.hasError()) {
                                if (mandat != null) {
                                    // si mandat existante :
                                    // test sur la validité du mandat et sur la date de fin du mandat
                                    paramDemandeCheque.setNumMandMand(mandat.getNumMandMand());
                                    if ((!mandat.getCodEtatMand().equals(Constants.COD_ETAT_MAND_VALID) || 
                                         (mandat.getDatFinMand() != null && 
                                          mandat.getDatFinMand().before(DateHandler.strToDate(DateHandler.dateJour())))) && 
                                        verification) {
                                        controle = false;
                                        rechercheDemandesChequesForm.setAlert("MandatInvalide");
                                        rechercheDemandesChequesForm.setMotifRejet(Constants.MOTIF_DEM_MAND_INVALIDE.toString());
                                    } else {
                                        // remplissage de la liste des mandat ( une seule mandat selectionée)
                                        List listeDesMandatsView = 
                                            new ArrayList();
                                        MandatView mandatView = 
                                            new MandatView();
                                        mandatView.setDateDebut(DateHandler.dateToStr(mandat.getDatDebMand()));
                                        mandatView.setDateFin(DateHandler.dateToStr(mandat.getDatFinMand()));
                                        if (mandat.getCodTypMand().equals("S"))
                                            mandatView.setType("Spécial");
                                        else if (mandat.getCodTypMand().equals("G")) {
                                            mandatView.setType("Général");
                                        } else if (mandat.getCodTypMand().equals("J")) {
                                            mandatView.setType("M. de Justice");
                                        }
                                        mandatView.setMandat(mandat);
                                        listeDesMandatsView.add(mandatView);
                                        rechercheDemandesChequesForm.setListMandatsConcernesPersonne(listeDesMandatsView);
                                        rechercheDemandesChequesForm.setOpenTabSheetMandat("true");
                                        rechercheDemandesChequesForm.setOpenTabsheetCotitulaire("false");
                                        rechercheDemandesChequesForm.setMessageTexte(" La demande de chèques est effectuée sur le mandat du dossier N° " + 
                                                                                     mandat.getNumDemMand());
                                        // ---------------------------------------------------------------------------------------------
                                        // verification de la validité de l'opération demande cheque pour une mandat de type spécial
                                        MandatDAO mandatDAO = 
                                            (MandatDAO)context.getBean("mandatDAO");
                                        if (mandat.getCodTypMand().equals("S") && 
                                            verification) {
                                            if (mandatDAO.verifierMandatOpartion(mandat.getNumMandMand(), 
                                                                                 Constants.OPER_DEM_CHQ)) {
                                                controle = true;
                                            } else {
                                                controle = false;
                                                rechercheDemandesChequesForm.setAlert("OperationInvalide");
                                                rechercheDemandesChequesForm.setMotifRejet(Constants.MOTIF_DEM_MAND_INVALIDE.toString());
                                            }
                                        }
                                        if (controle) {
                                            // verification de la validité des mandataires
                                            listesDemandesCheques = 
                                                    (ListesDemandesCheques)getListDemandeursChequesMandatPersonneCmd.execute(paramDemandeCheque);
                                            if (!listesDemandesCheques.hasError()) {
                                                if (listesDemandesCheques.getListeDemChqMandatPersonne() != 
                                                    null && 
                                                    listesDemandesCheques.getListeDemChqMandatPersonne().size() > 
                                                    0 && verification) {
                                                    for (Iterator it = 
                                                         listesDemandesCheques.getListeDemChqMandatPersonne().iterator(); 
                                                         it.hasNext() && 
                                                         valide == true; ) {
                                                        DemandeChequeMandatPersonne demandeChequeMandatPersonne = 
                                                            (DemandeChequeMandatPersonne)it.next();
                                                        if (!demandeChequeMandatPersonne.getMandatPersonne().getCodEtatMp().equals(Constants.COD_ETAT_MAND_VALID)) {
                                                            valide = false;
                                                        }
                                                    }
                                                }
                                                if (!valide) {
                                                    controle = false;
                                                    rechercheDemandesChequesForm.setAlert("MandataireInvalide");
                                                    rechercheDemandesChequesForm.setMotifRejet(Constants.MOTIF_POUVOIR_INSUFFISANT.toString());
                                                } else {
                                                    // remplissage de la liste des mandataires presents lors de la demande
                                                    rechercheDemandesChequesForm.setListMandatsPersonneConcernesDemandeur(listesDemandesCheques.getListeDemandeursChqMandatPersonne());
                                                    rechercheDemandesChequesForm.setOpenTabSheetOperation("true");
                                                }
                                            } else {
                                                List listErreur = 
                                                    listesDemandesCheques.getErrors();
                                                for (Iterator it1 = 
                                                     listErreur.iterator(); 
                                                     it1.hasNext(); ) {
                                                    com.oxia.fwk.core.Error erreur = 
                                                        (com.oxia.fwk.core.Error)it1.next();
                                                    ActionMessage actionMessage = 
                                                        new ActionMessage("exception.generique", 
                                                                          erreur.getDescription());
                                                    actionMessages.add("Erreur ", 
                                                                       actionMessage);
                                                }
                                                this.saveMessages(request, 
                                                                  actionMessages);
                                                return mapping.findForward("error");
                                            }
                                        }

                                    }
                                }
                            } else {
                                List listErreur = mandat.getErrors();
                                for (Iterator it1 = listErreur.iterator(); 
                                     it1.hasNext(); ) {
                                    com.oxia.fwk.core.Error erreur = 
                                        (com.oxia.fwk.core.Error)it1.next();
                                    ActionMessage actionMessage = 
                                        new ActionMessage("exception.generique", 
                                                          erreur.getDescription());
                                    actionMessages.add("Erreur ", 
                                                       actionMessage);
                                }
                                this.saveMessages(request, actionMessages);
                                return mapping.findForward("error");
                            }

                        } // fin du cas mandataire
                        //--------------------------------------------------------------------------------------------------------------------
                        //-----------------------------------------------------Fin du contrôle ----------------------------------------------------------
                        // partie réservée pour l'affichage du contrat  de la demande cheque 
                        if (controle) {
                            rechercheDemandesChequesForm.setCodPrdPrd(StrHandler.lpad(detailDemandeCheque.getDemandeCheque().getContratCpt().getContratCptId().getCodPrdPrd().toString(), 
                                                                                      '0', 
                                                                                      4));
                            rechercheDemandesChequesForm.setCodStrcStrc(StrHandler.lpad(detailDemandeCheque.getDemandeCheque().getContratCpt().getContratCptId().getCodStrcStrc().toString(), 
                                                                                        '0', 
                                                                                        3));
                            rechercheDemandesChequesForm.setNumCcptCcpt(StrHandler.lpad(detailDemandeCheque.getDemandeCheque().getContratCpt().getContratCptId().getNumCcptCcpt().toString(), 
                                                                                        '0', 
                                                                                        6));
                            rechercheDemandesChequesForm.setDevise(detailDemandeCheque.getDemandeCheque().getContratCpt().getDevise().getLibDevDev());
                            rechercheDemandesChequesForm.setNomIntiCcpt(detailDemandeCheque.getDemandeCheque().getContratCpt().getNomIntiCcpt());
                            if (detailDemandeCheque.getDemandeCheque().getContratCpt().getMontSoldCcpt() != 
                                null)
                                rechercheDemandesChequesForm.setMontSoldCcpt(detailDemandeCheque.getDemandeCheque().getContratCpt().getMontSoldCcpt().toString());

                            ContratCpt contratCpt = 
                                (ContratCpt)getDetailContratCmd.execute(detailDemandeCheque.getDemandeCheque().getContratCpt().getContratCptId());
                            rechercheDemandesChequesForm.setCodTpceTpceClient(contratCpt.getClient().getPersonne().getTypePiece().getCodTpceTpce().toString());
                            rechercheDemandesChequesForm.setNumPcePersClient(contratCpt.getClient().getPersonne().getNumPcePers());

                            if ((contratCpt.getClient().getTypePers().getCodTperTper().equals(Constants.ENTCOTITULAIRE))) {
                                rechercheDemandesChequesForm.setNomNomPersClient(contratCpt.getClient().getPersonne().getNomNomPers());
                            } else if (contratCpt.getClient().getTypePers().getCodTperTper().equals(Constants.PERSMORALE)) {
                                rechercheDemandesChequesForm.setNomNomPersClient(contratCpt.getClient().getPersonne().getNomRsPers());
                                rechercheDemandesChequesForm.setNomPrnPersClient(contratCpt.getClient().getPersonne().getLibSiglPers());
                            } else {
                                rechercheDemandesChequesForm.setNomNomPersClient(contratCpt.getClient().getPersonne().getNomNomPers());
                                rechercheDemandesChequesForm.setNomPrnPersClient(contratCpt.getClient().getPersonne().getNomPrnPers());
                            }
                            if (contratCpt.getAdresseCorresp() != null) {
                                rechercheDemandesChequesForm.setAdresseCorrespondanceClient(contratCpt.getAdresseCorresp().toString());
                            }
                            //--------------------------------------------------------------------------------------------------------------------
                            // partie réservé pour l'affichage du demandeur 
                            rechercheDemandesChequesForm.setCodTpceTpceDemandeur(detailDemandeCheque.getDemandeCheque().getCodTpceDchq().toString());
                            rechercheDemandesChequesForm.setNumPcePersDemandeur(detailDemandeCheque.getDemandeCheque().getNumPceDchq());
                            rechercheDemandesChequesForm.setNomNomPersDemandeur(detailDemandeCheque.getDemandeur().getNomNomPers());
                            rechercheDemandesChequesForm.setNomPrnPersDemandeur(detailDemandeCheque.getDemandeur().getNomPrnPers());
                            //--------------------------------------------------------------------------------------------------------------------
                            // partie réservé pour l'affichage des informations de la demande
                            rechercheDemandesChequesForm.setNumDemDchq(detailDemandeCheque.getDemandeCheque().getNumDemDchq().toString());
                            rechercheDemandesChequesForm.setDateDemDchq(DateHandler.dateToStr(detailDemandeCheque.getDemandeCheque().getDatDemDchq()));
                            rechercheDemandesChequesForm.setEtatDemDchq(detailDemandeCheque.getDemandeCheque().getCodEtatDchq().toString());
                            rechercheDemandesChequesForm.setCodConfConf(detailDemandeCheque.getDemandeCheque().getTypeConfection().getCodConfConf());
                            rechercheDemandesChequesForm.setTypeConfection(detailDemandeCheque.getDemandeCheque().getTypeConfection().getLibConfConf());
                            rechercheDemandesChequesForm.setNbreChequesDchq(detailDemandeCheque.getDemandeCheque().getNbrChqDchq().toString());
                            rechercheDemandesChequesForm.setNbreChequiers(detailDemandeCheque.getDemandeCheque().getNbrChqiDchq().toString());
                            rechercheDemandesChequesForm.setDatEnvoiDsc(DateHandler.dateToStr(detailDemandeCheque.getDemandeCheque().getDatEnvDchq()));
                            rechercheDemandesChequesForm.setDateEnvoiDr(DateHandler.dateToStr(detailDemandeCheque.getDemandeCheque().getDatEnvdDchq()));
                            if (detailDemandeCheque.getDemandeCheque().getCodDecsDchq() != null && !detailDemandeCheque.getDemandeCheque().getCodDecsDchq().equals(new String("0"))) {
                                rechercheDemandesChequesForm.setStrcDecision(detailDemandeCheque.getDemandeCheque().getCodDecsDchq());        
                            }
                            
                            if (rechercheDemandesChequesForm.getCodConfConf().equals(Constants.CODE_CHEQUE_PERSONALISE) || 
                                rechercheDemandesChequesForm.getCodConfConf().equals(Constants.CODE_LETTRE_CHEQUE)) {
                                rechercheDemandesChequesForm.setCodFraisDchq(detailDemandeCheque.getDemandeCheque().getCodFraiDchq().toString());
                                rechercheDemandesChequesForm.setTypeFactDchq(detailDemandeCheque.getDemandeCheque().getCodTfacDchq().toString());
                                if(detailDemandeCheque.getDemandeCheque().getMntFactDchq()!= null && !detailDemandeCheque.getDemandeCheque().getMntFactDchq().equals(""))
                                  rechercheDemandesChequesForm.setMontantFraisDchq(detailDemandeCheque.getDemandeCheque().getMntFactDchq().toString());
                            }

                            if (detailDemandeCheque.getDemandeCheque().getCodEtatDchq().equals(Constants.DEM_CHQ_REJETEE)) {
                                if(detailDemandeCheque.getDemandeCheque().getMotifRejet() != null)
                                  rechercheDemandesChequesForm.setMotifRejet(detailDemandeCheque.getDemandeCheque().getMotifRejet().getCodMotfMrej().toString());
                            } else {
                                rechercheDemandesChequesForm.setMotifRejet("0");
                            }
                            //--------------------------------------------------------------------------------------------------------------------

                            if (detailDemandeCheque.getDemandeCheque().getCodDemDchq().equals("C")) {
                                // cas ou le demandeur est une entité cotitulaire
                                GetListMembreCotitulaireCmd getListMembreCotitulaire = 
                                    new GetListMembreCotitulaireCmd();
                                PersonneStrc personneStrc = new PersonneStrc();
                                // extraction du client de l'entite cotitulaire
                                personneStrc.setCodTpceTpce(contratCpt.getClient().getPersonne().getTypePiece().getCodTpceTpce());
                                personneStrc.setNumPcePers(contratCpt.getClient().getPersonne().getNumPcePers());
                                // affichage de la liste des membres cotit et des infors de l'entité
                                Listes lisCotitulaire = 
                                    (Listes)getListMembreCotitulaire.execute(personneStrc);
                                if (!lisCotitulaire.hasError()) {
                                    rechercheDemandesChequesForm.setListCotitulaires(lisCotitulaire.getList());
                                    rechercheDemandesChequesForm.setTypeCotitulaire(detailDemandeCheque.getDemandeCheque().getCoTitulaire().getCodTcotCoti());
                                    rechercheDemandesChequesForm.setTypeSignatureCotitulaire(detailDemandeCheque.getDemandeCheque().getCoTitulaire().getCodSigCoti());
                                    rechercheDemandesChequesForm.setOpenTabsheetCotitulaire("true");
                                    rechercheDemandesChequesForm.setOpenTabSheetMandat("false");
                                    rechercheDemandesChequesForm.setOpenTabSheetOperation("false");
                                    rechercheDemandesChequesForm.setTypeDemandeur("C");
                                } else {
                                    List listErreur = 
                                        lisCotitulaire.getErrors();
                                    for (Iterator it1 = listErreur.iterator(); 
                                         it1.hasNext(); ) {
                                        com.oxia.fwk.core.Error erreur = 
                                            (com.oxia.fwk.core.Error)it1.next();
                                        ActionMessage actionMessage = 
                                            new ActionMessage("exception.generique", 
                                                              erreur.getDescription());
                                        actionMessages.add("Erreur ", 
                                                           actionMessage);
                                    }
                                    this.saveMessages(request, actionMessages);
                                    return mapping.findForward("error");
                                }
                                rechercheDemandesChequesForm.setMessageTexte(" Le contrat appartient à une entite co-titulaire.");
                            } else if (detailDemandeCheque.getDemandeCheque().getCodDemDchq().equals("T")) {
                                rechercheDemandesChequesForm.setOpenTabsheetCotitulaire("false");
                                rechercheDemandesChequesForm.setOpenTabSheetMandat("false");
                                rechercheDemandesChequesForm.setOpenTabSheetOperation("false");
                            }

                            rechercheDemandesChequesForm.setNumDemandeChoisie(rechercheDemandesChequesForm.getNumDemDchq());
                            rechercheDemandesChequesForm.setOpenTabsheetDemande("true");
                            rechercheDemandesChequesForm.setAlert("");
                            //affichage du tab Chequier dans les cas nécéssaires

                            /// Create a new datagrid  (of chequier);
                            Datagrid datagridChequiers = 
                                Datagrid.getInstance();
                            datagridChequiers.setData(new ArrayList());
                            datagridChequiers.setDataClass(ChequierView.class);
                            List listChequiers = new ArrayList();
                            if (!rechercheDemandesChequesForm.getCodePage().equals(Constants.VALIDATION_DEM_CHQ)) {
                                // tous les cas autre que la validation : afficher la liste des chéquiers
                                if (detailDemandeCheque.getDemandeCheque().getNbrChqiDchq() != 
                                    null && 
                                    detailDemandeCheque.getDemandeCheque().getNbrChqiDchq() > 
                                    new Long(0)) {
                                    if (rechercheDemandesChequesForm.getCodePage().equals(Constants.RECEPTION_CARNETS_CHEQUES)) {
                                        for (Long i = new Long(1); 
                                             i <= detailDemandeCheque.getDemandeCheque().getNbrChqiDchq(); 
                                             i++) {
                                            ChequierView chequierView = 
                                                new ChequierView();
                                            chequierView.setNumChqiChqi(i);
                                            chequierView.setNumDebChqi("00000000");
                                            chequierView.setDatRecpChqi(DateHandler.dateJour());
                                            chequierView.setCodEtatChqi(Constants.ETAT_CHQ_RECU);
                                            listChequiers.add(chequierView);
                                        }
                                    }
                                    if (rechercheDemandesChequesForm.getCodePage().equals(Constants.DELIVRANCE_CARNETS_CHEQUES)) {
                                        for (Iterator it = 
                                             detailDemandeCheque.getDemandeCheque().getChequiers().iterator(); 
                                             it.hasNext(); ) {
                                            Chequier chequier = 
                                                (Chequier)it.next();
                                            ChequierView chequierView = 
                                                new ChequierView();

                                            chequierView.setNumChqiChqi(chequier.getChequierId().getNumChqiChqi());
                                            chequierView.setNumDebChqi(chequier.getNumDebChqi().toString());
                                            chequierView.setDatRecpChqi(DateHandler.dateToStr(chequier.getDatRecpChqi()));
                                            chequierView.setCodEtatChqi(chequier.getCodEtatChqi());
                                            if (chequier.getCodEtatChqi().equals(Constants.ETAT_CHQ_RECU)) {
                                                chequierView.setCodEtatChqi(Constants.ETAT_CHQ_REMI);
                                                chequierView.setDatRemiChqi(DateHandler.dateJour());
                                            }
                                            listChequiers.add(chequierView);
                                        }
                                    }

                                    if (rechercheDemandesChequesForm.getCodePage().equals(Constants.DESTRUCTION_CARNETS_CHEQUES) || 
                                        rechercheDemandesChequesForm.getCodePage().equals(Constants.RESTITUTION_CARNETS_CHEQUES) || 
                                        rechercheDemandesChequesForm.getCodePage().equals(Constants.CONSULTATION_DEMANDE_CHQ)) {
                                        for (Iterator it = 
                                             detailDemandeCheque.getDemandeCheque().getChequiers().iterator(); 
                                             it.hasNext(); ) {
                                            Chequier chequier = 
                                                (Chequier)it.next();
                                            ChequierView chequierView = 
                                                new ChequierView();
                                            chequierView.setNumChqiChqi(chequier.getChequierId().getNumChqiChqi());
                                            chequierView.setNumDebChqi(chequier.getNumDebChqi().toString());
                                            chequierView.setDatRecpChqi(DateHandler.dateToStr(chequier.getDatRecpChqi()));
                                            chequierView.setCodEtatChqi(chequier.getCodEtatChqi());
                                            chequierView.setDatRemiChqi(DateHandler.dateToStr(chequier.getDatRemiChqi()));
                                            chequierView.setDatDestChqi(DateHandler.dateToStr(chequier.getDatDestChqi()));
                                            if (chequierView.getCodEtatChqi().equals(Constants.ETAT_CHQ_RESTITUE)) {
                                                rechercheDemandesChequesForm.setRestitution("true");
                                                chequierView.setNbrUtilChqi(chequier.getNbrUtilChqi());
                                                chequierView.setDatRestChqi(DateHandler.dateToStr(chequier.getDatRestChqi()));
                                                chequierView.setNumRestChqi(chequier.getNumRestChqi());
                                            } else
                                                rechercheDemandesChequesForm.setRestitution("false");
                                            listChequiers.add(chequierView);
                                        }
                                        if (rechercheDemandesChequesForm.getCodePage().equals(Constants.RESTITUTION_CARNETS_CHEQUES)) {
                                            rechercheDemandesChequesForm.setMessageRestitution("Veuillez saisir le nbre de chèques utiliséS afin de générer la liste des chèques à restituer.");
                                            rechercheDemandesChequesForm.setRestitution("true");
                                        } else
                                            rechercheDemandesChequesForm.setRestitution("false");

                                    }
                                }

                            }
                            datagridChequiers.setData(listChequiers);
                            rechercheDemandesChequesForm.setListChequiersGrid(datagridChequiers);
                        } else {
                            // la demande n'est plus valide : controle négatif
                            rechercheDemandesChequesForm.setEtatDemDchq(Constants.DEM_CHQ_REJETEE.toString());
                            ActionForward action = 
                                validerTransaction(mapping, form, request, 
                                                   response);
                        }


                    }
                } else {
                    List listErreur = detailDemandeCheque.getErrors();
                    for (Iterator it1 = listErreur.iterator(); it1.hasNext(); 
                    ) {
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
            } else
                rechercheDemandesChequesForm.setAlert("demandeInexistante");

            return mapping.findForward("success");
        } catch (Exception e) {
            com.oxia.fwk.core.Error erreur = new com.oxia.fwk.core.Error();
            StringBuffer text = 
                new StringBuffer("la transaction est Interrompu, une erreur dans RechercheDemandesChequesAction / Dispatch Action :choisirDemande ");
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

    public ActionForward validerTransaction(ActionMapping mapping, 
                                            ActionForm form, 
                                            HttpServletRequest request, 
                                            HttpServletResponse response) throws IOException, 
                                                                                 ServletException {

        RechercheDemandesChequesForm rechercheDemandesChequesForm = 
            (RechercheDemandesChequesForm)form;
        ActionMessages actionMessages = new ActionMessages();
        Tache tache = new Tache();
        TacheId tacheId = new TacheId();
        
        try {
            DemandeCheque demandeCheque = 
                rechercheDemandesChequesForm.getDemandeCheque();
            //demandeCheque.setChequiers(null);
            boolean chequierRejete = false;
            Long nbreRejetes = new Long(0);
            if (rechercheDemandesChequesForm.getCodePage().equals(Constants.VALIDATION_DEM_CHQ) || 
                rechercheDemandesChequesForm.getCodePage().equals(Constants.VALIDATION_DEM_CHQ_ENVOYEE_DR)) {
                // si le cas de validation ou de reception decision de demande Cheque           
                if (rechercheDemandesChequesForm.getMotifRejet().equals(new String("0"))) {
                    // la demande n'est pas rejetée
                    demandeCheque.setNbrChqDchq(new Long(rechercheDemandesChequesForm.getNbreChequesDchq()));
                    demandeCheque.setNbrChqiDchq(new Long(rechercheDemandesChequesForm.getNbreChequiers()));
                    demandeCheque.setDatEnvDchq(DateHandler.strToDate(rechercheDemandesChequesForm.getDateActuelle()));
                    demandeCheque.setCodEtatDchq(Constants.DEM_CHQ_VALIDEE);
                    
                    if(rechercheDemandesChequesForm.getCodePage().equals(Constants.VALIDATION_DEM_CHQ)){
                        if(demandeCheque.getTypeConfection().getCodConfConf().equals(Constants.CODE_CHEQUE_STANDARD)){
                          tacheId.setCodOperOper(Constants.OPER_VALIDATION_DEM_CHQ);
                          tacheId.setCodTachTach(Constants.TACHE_VALIDATION_DEM_CHQ_STND);
                        }else{
                            tacheId.setCodOperOper(Constants.OPER_VALIDATION_DEM_CHQ_LC_PERS);
                            tacheId.setCodTachTach(Constants.TACHE_VALIDATION_DEM_CHQ_LC_PERS);
                        }
                    }else if(rechercheDemandesChequesForm.getCodePage().equals(Constants.VALIDATION_DEM_CHQ_ENVOYEE_DR)){
                        if(demandeCheque.getTypeConfection().getCodConfConf().equals(Constants.CODE_CHEQUE_STANDARD)){
                          tacheId.setCodOperOper(Constants.OPER_DCIS_CHQ);
                          tacheId.setCodTachTach(Constants.TACHE_DCIS_CHQ_STND);
                        }else{
                            tacheId.setCodOperOper(Constants.OPER_DCIS_CHQ_LC_PERS);
                            tacheId.setCodTachTach(Constants.TACHE_DCIS_CHQ_LC_PERS);
                        }
                    }
                    tache.setTacheId(tacheId);
                    demandeCheque.setTache(tache);
                } else {
                     
                    MotifRejet motifRejet = new MotifRejet();
                    motifRejet.setCodMotfMrej(new Long(rechercheDemandesChequesForm.getMotifRejet()));
                    demandeCheque.setMotifRejet(motifRejet);
                    demandeCheque.setCodEtatDchq(Constants.DEM_CHQ_REJETEE);

                }
                ValidationDemandeChequeCmd validationDemandeChequeCmd = 
                    new ValidationDemandeChequeCmd();
                DemandeCheque demandeChequeRetour = 
                    (DemandeCheque)validationDemandeChequeCmd.execute(demandeCheque);
                if (demandeChequeRetour.hasError()) {
                    List listErreur = demandeChequeRetour.getErrors();
                    for (Iterator it1 = listErreur.iterator(); it1.hasNext(); 
                    ) {
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
                // fin du cas validation 
            } else {
                //tous les autres cas 
                DemandeCheque demandeChequeRetour = new DemandeCheque();
                Collection dgAdd = 
                    rechercheDemandesChequesForm.getListChequiersGrid().getDataWithState("");
                Collection dgSel = 
                    rechercheDemandesChequesForm.getListChequiersGrid().getDataWithState("selected");

                Collection listchequiers = new ArrayList();
                listchequiers.addAll(dgAdd);
                listchequiers.addAll(dgSel);

                for (Iterator it = listchequiers.iterator(); it.hasNext(); ) {
                    Chequier chequierTemp = new Chequier();
                    ChequierView chequierView = (ChequierView)it.next();
                    if (chequierView.getNumChqiChqi() != null) {
                        ChequierId chequierId = new ChequierId();
                        chequierId.setNumChqiChqi(chequierView.getNumChqiChqi());
                        chequierId.setNumDemDchq(demandeCheque.getNumDemDchq());
                        chequierTemp.setChequierId(chequierId);
                        chequierTemp.setDemandeCheque(demandeCheque);
                        chequierTemp.setContratCpt(demandeCheque.getContratCpt());
                        chequierTemp.setTypeConfection(demandeCheque.getTypeConfection());
                        chequierTemp.setCodEtatChqi(chequierView.getCodEtatChqi());
                        chequierTemp.setNumDebChqi(new Long(chequierView.getNumDebChqi()));
                        chequierTemp.setNbrChqChqi(demandeCheque.getNbrChqDchq());
                        chequierTemp.setDatRecpChqi(DateHandler.strToDate(chequierView.getDatRecpChqi()));

                        if (chequierView.getDatRemiChqi() != null) {
                            chequierTemp.setDatRemiChqi(DateHandler.strToDate(chequierView.getDatRemiChqi()));
                        }

                        if (chequierView.getDatRestChqi() != null) {
                            chequierTemp.setDatRestChqi(DateHandler.strToDate(chequierView.getDatRestChqi()));
                        }
                        if (chequierView.getDatDestChqi() != null) {
                            chequierTemp.setDatDestChqi(DateHandler.strToDate(chequierView.getDatDestChqi()));
                        }
                        if (chequierView.getNbrUtilChqi() != null) {
                            chequierTemp.setNbrUtilChqi(chequierView.getNbrUtilChqi());
                        }
                        if (chequierView.getNumRestChqi() != null) {
                            chequierTemp.setNumRestChqi(chequierView.getNumRestChqi());
                        }   //|| (rechercheDemandesChequesForm.getCodePage().equals(Constants.RECEPTION_CARNETS_CHEQUES)  ))
                        if (rechercheDemandesChequesForm.getCodePage().equals(Constants.RECEPTION_CARNETS_CHEQUES)) {                            
                            // creer des nouveaux chequiers en cas ou la demande n'est pas rejetée
                              demandeCheque.getChequiers().add(chequierTemp);                             
                        } else {
                            // traiter les autres cas autre que la réception : mettre à jour les chequiers                         
                            for (Iterator it1 = 
                                 demandeCheque.getChequiers().iterator(); 
                                 it1.hasNext(); ) {
                                Chequier chequierT = (Chequier)it1.next();
                                if (chequierTemp.getChequierId().getNumChqiChqi().equals(chequierT.getChequierId().getNumChqiChqi())) {
                                    if (rechercheDemandesChequesForm.getCodePage().equals(Constants.DELIVRANCE_CARNETS_CHEQUES)) {
                                        chequierT.setCodEtatChqi(chequierTemp.getCodEtatChqi());
                                        if (chequierT.getCodEtatChqi().equals(Constants.ETAT_CHQ_REJETE))
                                            chequierT.setDatRemiChqi(null);
                                        else{
                                            chequierT.setDatRemiChqi(chequierTemp.getDatRemiChqi());
                                            chequierT.setNumDebChqi(chequierTemp.getNumDebChqi());
                                        }    
                                    }
                                    if (rechercheDemandesChequesForm.getCodePage().equals(Constants.DESTRUCTION_CARNETS_CHEQUES)) {
                                        chequierT.setCodEtatChqi(chequierTemp.getCodEtatChqi());
                                        if (!chequierT.getCodEtatChqi().equals(Constants.ETAT_CHQ_DETRUIT))
                                            chequierT.setDatDestChqi(null);
                                        else
                                            chequierT.setDatDestChqi(chequierTemp.getDatDestChqi());
                                    }
                                    if (rechercheDemandesChequesForm.getCodePage().equals(Constants.RESTITUTION_CARNETS_CHEQUES)) {
                                        chequierT.setCodEtatChqi(chequierTemp.getCodEtatChqi());
                                        if (chequierT.getCodEtatChqi().equals(Constants.ETAT_CHQ_RESTITUE)) {
                                            chequierT.setDatRestChqi(chequierTemp.getDatRestChqi());
                                            chequierT.setNbrUtilChqi(chequierTemp.getNbrUtilChqi());
                                            chequierT.setNumRestChqi(chequierTemp.getNumRestChqi());
                                        }
                                    }

                                    break;
                                }
                            }

                        }

                    }
                    // decider de l'etat de la demande selon les etas des chéquiers
                    if (rechercheDemandesChequesForm.getCodePage().equals(Constants.RECEPTION_CARNETS_CHEQUES) || 
                        rechercheDemandesChequesForm.getCodePage().equals(Constants.DELIVRANCE_CARNETS_CHEQUES)) {
                        if (chequierView.getCodEtatChqi().equals(Constants.ETAT_CHQ_REJETE)) {
                            chequierRejete = true;
                            nbreRejetes = nbreRejetes + 1;

                        }
                    }
                } // End For             

                if (rechercheDemandesChequesForm.getCodePage().equals(Constants.RECEPTION_CARNETS_CHEQUES)) {
                    // cas de reception carnet de cheques
                    if (chequierRejete && 
                        nbreRejetes.equals(demandeCheque.getNbrChqiDchq()))
                        // tous les cheques sont rejetés alors la demande sera rejetée
                        demandeCheque.setCodEtatDchq(Constants.DEM_CHQ_REJETEE);
                    else if (chequierRejete && 
                             nbreRejetes < demandeCheque.getNbrChqiDchq()) {
                        //la demande sera partiellement satisfaite
                        demandeCheque.setCodEtatDchq(Constants.DEM_CHQ_PART_SATISFAITE);
                    } else if (!chequierRejete) {
                        //la demande sera totalement satisfaite
                        demandeCheque.setCodEtatDchq(Constants.DEM_CHQ_TOT_SATISFAITE);
                    }

                    if (nbreRejetes > 0) {
                        // s'il existe au moins un chequier rejeté
                        //refaire une demande de chequiers automatique pour les chequiers rejetées
                    }
                    
                   if (rechercheDemandesChequesForm.getCodConfConf().equals(Constants.CODE_CHEQUE_PERSONALISE) || 
                       rechercheDemandesChequesForm.getCodConfConf().equals(Constants.CODE_LETTRE_CHEQUE)) {
                       if(!rechercheDemandesChequesForm.getMontantFraisDchq().equals("")){
                           demandeCheque.setMntFactDchq(new Long(new Double(new Double(StrHandler.strWithoutBlanck(rechercheDemandesChequesForm.getMontantFraisDchq())).doubleValue() * 
                                                                 1000).longValue()));
                       }
                   } 
                   
                    if(demandeCheque.getTypeConfection().getCodConfConf().equals(Constants.CODE_CHEQUE_STANDARD)){
                       tacheId.setCodOperOper(Constants.OPER_RECEPTION_CHQ);
                       tacheId.setCodTachTach(Constants.TACHE_RECEPTION_CHQ_STND);
                    }else{
                      if(demandeCheque.getTypeConfection().getCodConfConf().equals(Constants.CODE_CHEQUE_PERSONALISE) || (demandeCheque.getTypeConfection().getCodConfConf().equals(Constants.CODE_LETTRE_CHEQUE) &&  demandeCheque.getCodTlcDchq().equals(Long.valueOf("1")))){                         
                         tacheId.setCodOperOper(Constants.OPER_RECEPTION_CHQ_LC_PERS);
                         tacheId.setCodTachTach(Constants.TACHE_RECEPTION_CHQ_LC_PERS);
                      }else if((demandeCheque.getTypeConfection().getCodConfConf().equals(Constants.CODE_LETTRE_CHEQUE) &&  demandeCheque.getCodTlcDchq().equals(Long.valueOf("2")))){
                          // autoconfection lettre de cheque
                          tacheId.setCodOperOper(Constants.OPER_RECEPTION_PLAGE_LC);
                          tacheId.setCodTachTach(Constants.TACHE_RECEPTION_PLAGE_LC);                          
                      }
                    }
                    tache.setTacheId(tacheId);
                    demandeCheque.setTache(tache);
                     
                    if(!rechercheDemandesChequesForm.getMotifRejet().equals(new String("0"))){
                        demandeCheque.setChequiers(null);
                        MotifRejet motifRejet = new MotifRejet();
                        motifRejet.setCodMotfMrej(new Long(rechercheDemandesChequesForm.getMotifRejet()));
                        demandeCheque.setMotifRejet(motifRejet);
                        demandeCheque.setCodEtatDchq(Constants.DEM_CHQ_REJETEE);
                        
                    }
                    ReceptionChequiersCmd receptionChequiersCmd =new ReceptionChequiersCmd();
                    demandeChequeRetour = (DemandeCheque)receptionChequiersCmd.execute(demandeCheque);

                    // fin cas reception

                } else if (rechercheDemandesChequesForm.getCodePage().equals(Constants.DELIVRANCE_CARNETS_CHEQUES)) {
                    // cas de délivrance carnet de cheques 
                    if (chequierRejete && 
                        nbreRejetes.equals(demandeCheque.getNbrChqiDchq()))
                        // tous les cheques sont rejetés alors la demande sera rejetée
                        demandeCheque.setCodEtatDchq(Constants.DEM_CHQ_REJETEE);
                    else if (chequierRejete && 
                             nbreRejetes < demandeCheque.getNbrChqiDchq()) {
                        //la demande sera partiellement satisfaite
                        demandeCheque.setCodEtatDchq(Constants.DEM_CHQ_PART_DELIVREE);
                    } else if (!chequierRejete) {
                        //la demande sera totalement satisfaite
                        demandeCheque.setCodEtatDchq(Constants.DEM_CHQ_TOT_DELIVREE);
                    }

                    if (nbreRejetes > 0) {
                        // s'il existe au moins un chequier rejeté
                        //refaire une demande de chequiers automatique pour les chequiers rejetées
                    }
                         if(demandeCheque.getTypeConfection().getCodConfConf().equals(Constants.CODE_CHEQUE_STANDARD)){
                            tacheId.setCodOperOper(Constants.OPER_DELIV_CHQ);
                            tacheId.setCodTachTach(Constants.TACHE_DELIV_CHQ_STND);
                         }else{
                            if(demandeCheque.getTypeConfection().getCodConfConf().equals(Constants.CODE_CHEQUE_PERSONALISE) || (demandeCheque.getTypeConfection().getCodConfConf().equals(Constants.CODE_LETTRE_CHEQUE) &&  demandeCheque.getCodTlcDchq().equals(Long.valueOf("1")))){                         
                              tacheId.setCodOperOper(Constants.OPER_DELIV_CHQ_LC_PERS);
                              tacheId.setCodTachTach(Constants.TACHE_DELIV_CHQ_LC_PERS);
                            }else if((demandeCheque.getTypeConfection().getCodConfConf().equals(Constants.CODE_LETTRE_CHEQUE) &&  demandeCheque.getCodTlcDchq().equals(Long.valueOf("2")))){
                               tacheId.setCodOperOper(Constants.OPER_REMISE_PLAGE_LC);
                               tacheId.setCodTachTach(Constants.TACHE_REMISE_PLAGE_LC);
                           }
                         }
                         tache.setTacheId(tacheId);
                         demandeCheque.setTache(tache);                    
                    
                    DelivranceChequiersCmd delivranceChequiersCmd = 
                        new DelivranceChequiersCmd();
                    demandeChequeRetour = 
                            (DemandeCheque)delivranceChequiersCmd.execute(demandeCheque);

                    // fin cas délvirance
                } else if (rechercheDemandesChequesForm.getCodePage().equals(Constants.DESTRUCTION_CARNETS_CHEQUES)) {
                    // cas de destruction carnet de cheques 
                    DestructionChequiersCmd destructionChequiersCmd = new DestructionChequiersCmd();
                    
                    if(demandeCheque.getTypeConfection().getCodConfConf().equals(Constants.CODE_CHEQUE_STANDARD)){
                       tacheId.setCodOperOper(Constants.OPER_DEST_CHQ);
                       tacheId.setCodTachTach(Constants.TACHE_DEST_CHQ_STND);
                    }else{
                       tacheId.setCodOperOper(Constants.OPER_DEST_CHQ_LC_PERS);
                       tacheId.setCodTachTach(Constants.TACHE_DEST_CHQ_LC_PERS);
                    }
                    tache.setTacheId(tacheId);
                    demandeCheque.setTache(tache);
                    
                    demandeChequeRetour = (DemandeCheque)destructionChequiersCmd.execute(demandeCheque);
                   
                    // fin cas destruction
                } else if (rechercheDemandesChequesForm.getCodePage().equals(Constants.RESTITUTION_CARNETS_CHEQUES)) {
                    // cas de restitution carnet de cheques 
                     if(demandeCheque.getTypeConfection().getCodConfConf().equals(Constants.CODE_CHEQUE_STANDARD)){
                        tacheId.setCodOperOper(Constants.OPER_REST_CHQ);
                        tacheId.setCodTachTach(Constants.TACHE_REST_CHQ_STND);
                     }else{
                        tacheId.setCodOperOper(Constants.OPER_REST_CHQ_LC_PERS);
                        tacheId.setCodTachTach(Constants.TACHE_REST_CHQ_LC_PERS);
                     }
                     tache.setTacheId(tacheId);
                     demandeCheque.setTache(tache);
                    
                    RestitutionChequiersCmd restitutionChequiersCmd = new RestitutionChequiersCmd();
                    demandeChequeRetour = (DemandeCheque)restitutionChequiersCmd.execute(demandeCheque);
                   
                   
                    // fin cas restitution
                }

                if (demandeChequeRetour.hasError()) {
                    List listErreur = demandeChequeRetour.getErrors();
                    for (Iterator it1 = listErreur.iterator(); it1.hasNext(); 
                    ) {
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
            return mapping.findForward("success");
        } catch (Exception e) {
            com.oxia.fwk.core.Error erreur = new com.oxia.fwk.core.Error();
            StringBuffer text = 
                new StringBuffer("la transaction est Interrompu, une erreur dans RechercheDemandesChequesAction / Dispatch Action :validerTransaction ");
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


    public ActionForward determinerHistoriqueChequier(ActionMapping mapping, 
                                                      ActionForm form, 
                                                      HttpServletRequest request, 
                                                      HttpServletResponse response) throws IOException, 
                                                                                           ServletException {


        RechercheDemandesChequesForm rechercheDemandesChequesForm = 
            (RechercheDemandesChequesForm)form;
        ActionMessages actionMessages = new ActionMessages();
        try{
        GetListDetailOperationChequierCmd getListDetailOperationChequierCmd = 
            new GetListDetailOperationChequierCmd();
        ParamDemandeCheque paramDemandeCheque = new ParamDemandeCheque();
        List detailOperChqView = new ArrayList();
        rechercheDemandesChequesForm.getListeHistoriqueCheques().clear();
        if (rechercheDemandesChequesForm.getNumDemandeChoisie() != null) {
            paramDemandeCheque.setNumDemande(rechercheDemandesChequesForm.getNumDemandeChoisie());
            ListesDemandesCheques listDem = 
                (ListesDemandesCheques)getListDetailOperationChequierCmd.execute(paramDemandeCheque);
            if (listDem.getListeDetailOperationChequier() != null && 
                listDem.getListeDetailOperationChequier().size() > 0) {
                for (Iterator it = 
                     listDem.getListeDetailOperationChequier().iterator(); 
                     it.hasNext(); ) {
                    DetailOperationChequierView detailOperationChequierView = 
                        new DetailOperationChequierView();
                    DetailOperationChequier detailOperationChequier = 
                        (DetailOperationChequier)it.next();

                    detailOperationChequierView.setDetailOperationChequier(detailOperationChequier);
                    detailOperationChequierView.setDateDetail(DateHandler.dateToStr(detailOperationChequier.getDatOperDdc()));
                    if (detailOperationChequier.getCodEtatDdc() != null) {
                        if (detailOperationChequier.getCodEtatDdc().equals(Constants.ETAT_CHQ_RECU)) {
                            detailOperationChequierView.setEtatDetail("Reçu");
                        } else if (detailOperationChequier.getCodEtatDdc().equals(Constants.ETAT_CHQ_REMI)) {
                            detailOperationChequierView.setEtatDetail("Remis");
                        } else if (detailOperationChequier.getCodEtatDdc().equals(Constants.ETAT_CHQ_REJETE)) {
                            detailOperationChequierView.setEtatDetail("Rejeté");
                        } else if (detailOperationChequier.getCodEtatDdc().equals(Constants.ETAT_CHQ_RESTITUE)) {
                            detailOperationChequierView.setEtatDetail("Restitué");
                        } else if (detailOperationChequier.getCodEtatDdc().equals(Constants.ETAT_CHQ_DETRUIT)) {
                            detailOperationChequierView.setEtatDetail("Detruit");
                        }
                    }
                    detailOperChqView.add(detailOperationChequierView);
                }
            }
            rechercheDemandesChequesForm.setListeHistoriqueCheques(detailOperChqView);
        }
        
        } catch (Exception e) {
            com.oxia.fwk.core.Error erreur = new com.oxia.fwk.core.Error();
            StringBuffer text = 
                new StringBuffer("la transaction est Interrompu, une erreur dans RechercheDemandesChequesAction / Dispatch Action :determinerHistoriqueChequier ");
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
        
        return mapping.findForward("success");

    }

    public ActionForward determinerDemandeAnterieures(ActionMapping mapping, 
                                                      ActionForm form, 
                                                      HttpServletRequest request, 
                                                      HttpServletResponse response) throws IOException, 
                                                                                           ServletException {


        RechercheDemandesChequesForm rechercheDemandesChequesForm = 
            (RechercheDemandesChequesForm)form;
        ActionMessages actionMessages = new ActionMessages();
        try{
        ParamDemandeCheque paramDemandeCheque = new ParamDemandeCheque();
        ListesDemandesCheques listesDemandesCheques = 
            new ListesDemandesCheques();
        GetListDemandeChequePersonneCmd getListDemandeChequePersonneCmd = 
            new GetListDemandeChequePersonneCmd();
        ContratPersonne contratPersonne = new ContratPersonne();
        contratPersonne.setContratCptId(rechercheDemandesChequesForm.getDemandeCheque().getContratCpt().getContratCptId());
        PersonneStrc personneStrc = new PersonneStrc();
        personneStrc.setCodTpceTpce(new Long(rechercheDemandesChequesForm.getDemandeCheque().getCodTpceDchq()));
        personneStrc.setNumPcePers(rechercheDemandesChequesForm.getDemandeCheque().getNumPceDchq());
        contratPersonne.setPersonneId(personneStrc);
        paramDemandeCheque.setContratPersonne(contratPersonne);
        listesDemandesCheques = 
                (ListesDemandesCheques)getListDemandeChequePersonneCmd.execute(paramDemandeCheque);
        rechercheDemandesChequesForm.getListeDemandesChequesAnterieures().clear();
        if (listesDemandesCheques.getListeAttente() != null && 
            listesDemandesCheques.getListeAttente().size() > 0) {
            Collection listeAttenteView = 
                traiterListedemandes(listesDemandesCheques.getListeAttente(), 
                                     rechercheDemandesChequesForm);
            rechercheDemandesChequesForm.getListeDemandesChequesAnterieures().addAll(listeAttenteView);
        }
        if (listesDemandesCheques.getListeValidee() != null && 
            listesDemandesCheques.getListeValidee().size() > 0) {
            Collection listeValideeView = 
                traiterListedemandes(listesDemandesCheques.getListeValidee(), 
                                     rechercheDemandesChequesForm);
            rechercheDemandesChequesForm.getListeDemandesChequesAnterieures().addAll(listeValideeView);
        }
        if (listesDemandesCheques.getListeTotSatisfaite() != null && 
            listesDemandesCheques.getListeTotSatisfaite().size() > 0) {
            Collection listeTotSatisfaiteView = 
                traiterListedemandes(listesDemandesCheques.getListeTotSatisfaite(), 
                                     rechercheDemandesChequesForm);
            rechercheDemandesChequesForm.getListeDemandesChequesAnterieures().addAll(listeTotSatisfaiteView);
        }
        if (listesDemandesCheques.getListePartSatisfaite() != null && 
            listesDemandesCheques.getListePartSatisfaite().size() > 0) {
            Collection listePartSatisfaiteView = 
                traiterListedemandes(listesDemandesCheques.getListePartSatisfaite(), 
                                     rechercheDemandesChequesForm);
            rechercheDemandesChequesForm.getListeDemandesChequesAnterieures().addAll(listePartSatisfaiteView);
        }
        if (listesDemandesCheques.getListeRejetee() != null && 
            listesDemandesCheques.getListeRejetee().size() > 0) {
            Collection listeRejeteeView = 
                traiterListedemandes(listesDemandesCheques.getListeRejetee(), 
                                     rechercheDemandesChequesForm);
            rechercheDemandesChequesForm.getListeDemandesChequesAnterieures().addAll(listeRejeteeView);
        }
        if (listesDemandesCheques.getListeEnvoyeeDR_DCCI() != null && 
            listesDemandesCheques.getListeEnvoyeeDR_DCCI().size() > 0) {
            Collection listeEnvoyeeView = 
                traiterListedemandes(listesDemandesCheques.getListeEnvoyeeDR_DCCI(), 
                                     rechercheDemandesChequesForm);
            rechercheDemandesChequesForm.getListeDemandesChequesAnterieures().addAll(listeEnvoyeeView);
        }
        
        } catch (Exception e) {
            com.oxia.fwk.core.Error erreur = new com.oxia.fwk.core.Error();
            StringBuffer text = 
                new StringBuffer("la transaction est Interrompu, une erreur dans RechercheDemandesChequesAction / Dispatch Action :determinerDemandeAnterieures ");
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
        
        return mapping.findForward("demandesAnterieures");

    }


    
    public ActionForward determinerListeChequiers(ActionMapping mapping, 
                                                      ActionForm form, 
                                                      HttpServletRequest request, 
                                                      HttpServletResponse response) throws IOException, 
                                                                                           ServletException {
                                                                                           
        
        ParamAgence paramAgence = (ParamAgence)request.getSession().getAttribute("paramAgBNA"); /// structure de l'agent 
        Paramchequiers paramChequier = new Paramchequiers();
        ActionMessages actionMessages = new ActionMessages();     
    try{
        List listChequierView = new ArrayList();
        GetDetailChequierCmd getDetailChequierCmd = new GetDetailChequierCmd();
        Context context = ContextHandler.getContext();                                                                                   
        RechercheDemandesChequesForm rechercheDemandesChequesForm =(RechercheDemandesChequesForm)form; 
        rechercheDemandesChequesForm.getListChequiers().clear();
        DemandeChequeDAO demandeChequeDAO =(DemandeChequeDAO)context.getBean("demandeChequeDAO");
        List listGenerique = new ArrayList();
        if(rechercheDemandesChequesForm.getChoixListe().equals("0"))
        // liste des chequiers à renouvler
          listGenerique = demandeChequeDAO.getListChequiersARenouvler(paramAgence.getCodStrcStrc(),rechercheDemandesChequesForm.getDateDebRecherch(),rechercheDemandesChequesForm.getDateFinRecherch() );
        else if (rechercheDemandesChequesForm.getChoixListe().equals("1"))
          // liste des chéquiers non encore remis au clients
          listGenerique = demandeChequeDAO.getListChequiersNonRemis(paramAgence.getCodStrcStrc(),rechercheDemandesChequesForm.getDateDebRecherch(),rechercheDemandesChequesForm.getDateFinRecherch() );
        else if (rechercheDemandesChequesForm.getChoixListe().equals("2"))
          // liste des chéquiers restitués...
          listGenerique = demandeChequeDAO.getListChequiersRestitues(paramAgence.getCodStrcStrc(),rechercheDemandesChequesForm.getDateDebRecherch(),rechercheDemandesChequesForm.getDateFinRecherch() );
        else if (rechercheDemandesChequesForm.getChoixListe().equals("3"))
          // liste des chequiers à detruire
          listGenerique = demandeChequeDAO.getListChequiersADetruire(paramAgence.getCodStrcStrc(),rechercheDemandesChequesForm.getDateDebRecherch(),rechercheDemandesChequesForm.getDateFinRecherch() );
        else if (rechercheDemandesChequesForm.getChoixListe().equals("4"))
          //  liste des chéquiers autoconfectionés par le client
          listGenerique = demandeChequeDAO.getListChequiersAutoConfectiones(paramAgence.getCodStrcStrc(),rechercheDemandesChequesForm.getDateDebRecherch(),rechercheDemandesChequesForm.getDateFinRecherch() );
        else if (rechercheDemandesChequesForm.getChoixListe().equals("5")){
               listGenerique = demandeChequeDAO.getListChequiersDetruis(paramAgence.getCodStrcStrc(),rechercheDemandesChequesForm.getDateDebRecherch(),rechercheDemandesChequesForm.getDateFinRecherch() );
             }
        
        
        ListOrderedMap ListNumChq = null;
        for (Iterator it = listGenerique.iterator(); it.hasNext(); ) {
            ListNumChq = (ListOrderedMap)it.next();
             Chequier chequier = new Chequier();
             ChequierView chequierView = new ChequierView();
            if ((ListNumChq.getValue(0)).toString() != null && ListNumChq.getValue(1).toString() != null) {
                paramChequier.setNumDemande(ListNumChq.getValue(0).toString());
                paramChequier.setNumChequier(Long.valueOf(ListNumChq.getValue(1).toString()));                
                chequier = (Chequier)getDetailChequierCmd.execute(paramChequier);        
                if(chequier.getChequierId().getNumChqiChqi() != null){
                    chequierView.setDemandeCheque(chequier.getDemandeCheque());
                    chequierView.setNumChqiChqi(chequier.getChequierId().getNumChqiChqi());
                    chequierView.setNumDebChqi(chequier.getNumDebChqi().toString());
                    chequierView.setDatRecpChqi(DateHandler.dateToStr(chequier.getDatRecpChqi()));
                    chequierView.setDateDemande(DateHandler.dateToStr(chequier.getDemandeCheque().getDatDemDchq()));
                    chequierView.setTypeConf(chequier.getTypeConfection().getLibConfConf());
                    chequierView.setTypeConf(chequier.getTypeConfection().getLibConfConf());
                    chequierView.setNbrChqChqi(chequier.getNbrChqChqi());
                    chequierView.setNumCompte(StrHandler.lpad(chequier.getDemandeCheque().getContratCpt().getContratCptId().getCodStrcStrc().toString(),'0',3 )+
                                              StrHandler.lpad(chequier.getDemandeCheque().getContratCpt().getContratCptId().getCodPrdPrd().toString(),'0',4 ) +
                                              StrHandler.lpad(chequier.getDemandeCheque().getContratCpt().getContratCptId().getNumCcptCcpt().toString(),'0',6 ));
                    
                    listChequierView.add(chequierView);
                    
                }
                 
             }
             
         }
     rechercheDemandesChequesForm.setListChequiers(listChequierView);
     
     } catch (Exception e) {
         com.oxia.fwk.core.Error erreur = new com.oxia.fwk.core.Error();
         StringBuffer text = 
             new StringBuffer("la transaction est Interrompu, une erreur dans RechercheDemandesChequesAction / Dispatch Action :determinerListeChequiers ");
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
         
     return mapping.findForward("editChequiers");
 }
    /**
         * Methode permettant d'imprimer les listes des chéquiers (à renouveler, non remis, récupérés...)
         * @author JERBI lamia
         */
     public ActionForward imprimerListeChéquier(ActionMapping mapping, 
                                                           ActionForm form, 
                                                           HttpServletRequest request, 
                                                           HttpServletResponse response) throws IOException, 
                                                                                                ServletException {
              RechercheDemandesChequesForm rechercheDemandesChequesForm =(RechercheDemandesChequesForm)form; 
              ActionMessages actionMessages = new ActionMessages();
              try {    
                  CommonReportVO valueObject = new CommonReportVO();
                  ParamAgence paramAgence = new ParamAgence();
                  paramAgence = (ParamAgence)request.getSession().getAttribute("paramAgBNA");
                  Map parameters = new HashMap();
                  
                  String pCodStrcStrc = "P_COD_STRC_STRC";
                  String vCodStrcStrc = paramAgence.getCodStrcStrc().toString();

                  String pDateDeb = "P_DATE_DEB";
                  String pDateFin = "P_DATE_FIN";
                  String vDateFin="";
                  String vDateDeb="";

                  String pMatrUser = "P_NUM_MATR_USER";
                  String vMatrUser = paramAgence.getNumMatrUser().toString();
                  
                  String pLibEtat="P_LIB_ETAT";
                  String vLibEtat="";
                  if(rechercheDemandesChequesForm.getChoixListe().equals("0"))
                  // liste des chequiers à renouvler
                   {
                       vLibEtat = "Liste des chéquiers à renouveler suite anomalies";
                       valueObject.setNomReport("listeCHQ_renouv"); 
                   }
                  else if (rechercheDemandesChequesForm.getChoixListe().equals("1"))
                    // liste des chéquiers non encore remis au clients
                   {
                       vLibEtat = "Liste des chéquiers non encore remis au clients";
                       valueObject.setNomReport("listeCHQ_NnRemi"); 
                   }
                  else if (rechercheDemandesChequesForm.getChoixListe().equals("2"))
                    // liste des chéquiers restitués...
                    {
                        vLibEtat = "Liste des chéquiers récupérés auprès de la clientèle";
                        valueObject.setNomReport("listeCHQ_recupere"); 
                    }
                  else if (rechercheDemandesChequesForm.getChoixListe().equals("3"))
                    // liste des chequiers à detruire
                    {
                        vLibEtat = "Liste des chéquiers à détruire";
                        valueObject.setNomReport("listeCHQ_detruite"); 
                    }
                  else if (rechercheDemandesChequesForm.getChoixListe().equals("4"))
                    //  liste des chéquiers autoconfectionés par le client
                    {
                        vLibEtat = "Liste des chéquiers auto-confectionnés par le client";
                        valueObject.setNomReport("listeCHQ_autoConf"); 
                    }
                  else if (rechercheDemandesChequesForm.getChoixListe().equals("5"))
                    //  liste des chéquiers déjà détruites
                    {
                        vLibEtat = "Liste des chéquiers détruits";
                        valueObject.setNomReport("listeCHQ_detruites"); 
                    }
                  
                  parameters.put(pLibEtat, vLibEtat);
                  parameters.put(pCodStrcStrc, vCodStrcStrc);
                  parameters.put(pMatrUser, vMatrUser);
                  
                   if(!rechercheDemandesChequesForm.getDateDebRecherch().equals("") ||
                             !rechercheDemandesChequesForm.getDateFinRecherch().equals("")){
                                 vDateFin=rechercheDemandesChequesForm.getDateFinRecherch();
                                 vDateDeb=rechercheDemandesChequesForm.getDateDebRecherch();
                                 parameters.put(pDateDeb,vDateDeb);
                                 parameters.put(pDateFin,vDateFin);
                              }
                  
                  valueObject.setParams(parameters);
                  
                  request.getSession().setAttribute("CommonPrintVo",valueObject);
                  request.setAttribute("print","1");
                  return mapping.findForward("editChequiers");
              } catch (Exception e) {
              com.oxia.fwk.core.Error erreur = new com.oxia.fwk.core.Error();
              StringBuffer text = 
                  new StringBuffer("la transaction est Interrompu, une erreur dans ConsultationMandatAction / Dispatch Action :imprimerMandatSelonChoix ");
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

}
