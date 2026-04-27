package com.bna.smile.web.moyenPaiement.certificationCheque.actions;

import com.bna.commun.conditiondebanque.commande.DemandeConditionCmd;
import com.bna.commun.conditiondebanque.vo.Condition;
import com.bna.commun.conditiondebanque.vo.ConditionBanque;
import com.bna.commun.conditiondebanque.vo.DemandeCondition;
import com.bna.commun.conditiondebanque.vo.DetailConditionBanque;
import com.bna.commun.conditiondebanque.vo.ListConditionVo;
import com.bna.commun.model.CertifChqMandPers;
import com.bna.commun.model.CertifChqMandPersId;
import com.bna.commun.model.CertificationCheques;
import com.bna.commun.model.CoTitulaire;
import com.bna.commun.model.ContratCpt;
import com.bna.commun.model.ContratCptId;
import com.bna.commun.model.DemandeCheque;
import com.bna.commun.model.DemandeChequeMandatPersonne;
import com.bna.commun.model.DemandeChequeMandatPersonneId;
import com.bna.commun.model.DetailOperMoyPaiement;
import com.bna.commun.model.Devise;
import com.bna.commun.model.MandPersOperMoy;
import com.bna.commun.model.MandPersOperMoyId;
import com.bna.commun.model.MandatOperation;
import com.bna.commun.model.MandatPersonne;
import com.bna.commun.model.MotifRejet;
import com.bna.commun.model.NomencElemtCondition;
import com.bna.commun.model.Operation;
import com.bna.commun.model.OperationMoyPay;
import com.bna.commun.model.OppositionMoyenPaiementId;
import com.bna.commun.model.Personnel;
import com.bna.commun.model.Structure;
import com.bna.commun.model.Tache;
import com.bna.commun.model.TacheId;
import com.bna.commun.model.TypeConfection;
import com.bna.commun.model.TypeMoyenPaiement;
import com.bna.commun.model.TypePiece;
import com.bna.commun.util.DateHandler;
import com.bna.commun.util.StrHandler;
import com.bna.commun.util.DateHandler;
import com.bna.commun.util.StrHandler;
import com.bna.commun.util.ContextHandler;
import com.bna.smile.model.constant.Constants;
import com.bna.smile.model.domainecommun.commande.GetContratEtatCmd;
import com.bna.smile.model.domainecommun.commande.GetDetailContratCmd;
import com.bna.smile.model.domainecommun.commande.GetPersonneCptCmd;
import com.bna.smile.model.domainecommun.model.ContratCptMandat;
import com.bna.smile.model.domainecommun.model.ContratPersonne;
import com.bna.smile.model.domainecommun.model.Listes;
import com.bna.smile.model.domainecommun.model.PersonneCpt;
import com.bna.smile.model.domainecommun.model.PersonneStrc;
import com.bna.commun.vo.PrimitiveVO;
import com.bna.smile.model.domainecontratcompte.moyensPaiement.commande.AnnulerCertificationChequeCmd;
import com.bna.smile.model.domainecontratcompte.moyensPaiement.commande.GetDetailDemandeCertificationChequeCmd;
import com.bna.smile.model.domainecontratcompte.moyensPaiement.commande.GetDetailDemandeChequeCmd;
import com.bna.smile.model.domainecontratcompte.moyensPaiement.commande.GetListDemandesChequesCertifCmd;
import com.bna.smile.model.domainecontratcompte.moyensPaiement.commande.GetListDemandesChequesCmd;
import com.bna.smile.model.domainecontratcompte.moyensPaiement.commande.InsertDemandeChequeCmd;
import com.bna.smile.model.domainecontratcompte.moyensPaiement.commande.InsertDemandeChequeMandatPersonneCmd;
import com.bna.smile.model.domainecontratcompte.moyensPaiement.commande.PecDemandeCertificationChequeCmd;
import com.bna.smile.model.domainecontratcompte.moyensPaiement.commande.PrevaliderCertificationChequeDeplaceeCmd;
import com.bna.smile.model.domainecontratcompte.moyensPaiement.commande.ReglerCertificationChequeCmd;
import com.bna.smile.model.domainecontratcompte.moyensPaiement.commande.RestituerCertificationChequeCmd;
import com.bna.smile.model.domainecontratcompte.moyensPaiement.commande.ValiderCertificationChequeForceeCmd;
import com.bna.smile.model.domainecontratcompte.moyensPaiement.dao.DemandeChequeDAO;
import com.bna.smile.model.domainecontratcompte.moyensPaiement.model.DetailDemandeCheque;
import com.bna.smile.model.domainecontratcompte.moyensPaiement.model.ListesDemandesCheques;
import com.bna.smile.model.domainecontratcompte.moyensPaiement.model.ParamDemandeCheque;
import com.bna.smile.model.domainecontratcompte.moyensPaiement.model.ParamDemandeChequeCertifie;

import com.bna.smile.model.domaineguichet.commande.VerifOppositionMoyPayCmd;
import com.bna.smile.web.commun.forms.PouvoirForm;
import com.bna.smile.web.commun.forms.RechercheMandatPouvoirForm;
import com.bna.smile.web.commun.model.ParamAgence;
import com.bna.smile.web.commun.model.PersonneDemandeur;
import com.bna.smile.web.commun.model.Pouvoir;
import com.bna.smile.web.commun.util.SessionUtil;
import com.bna.smile.web.commun.view.ContratView;
import com.bna.smile.web.moyenPaiement.certificationCheque.forms.CertificationChequeForm;
import com.bna.smile.web.moyenPaiement.certificationCheque.util.CertificationChequesView;
import com.bna.smile.web.moyenPaiement.demandeChequier.forms.CreationDemandeChequeForm;
import com.bna.smile.web.moyenPaiement.demandeChequier.forms.RechercheDemandesChequesForm;
import com.bna.smile.web.moyenPaiement.demandeChequier.util.DemandeChequeView;


import com.bna.smile.web.souscription.forms.GestionContratCptForm;

import com.oxia.fwk.context.Context;

import com.oxia.fwk.core.ValueObject;

import java.io.IOException;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.actions.DispatchAction;


public class CertificationChequesAction extends DispatchAction {
    /**This is the main action called from the Struts framework.
     * @param mapping The ActionMapping used to select this instance.
     * @param form The optional ActionForm bean for this request.
     * @param request The HTTP Request we are processing.
     * @param response The HTTP Response we are processing.
     */
    public


    ActionForward initPageMenu(ActionMapping mapping, ActionForm form, 
                               HttpServletRequest request, 
                               HttpServletResponse response) throws IOException, 
                                                                    ServletException {

        return mapping.findForward("initPageMenu");

    }

    public ActionForward initierPage(ActionMapping mapping, ActionForm form, 
                                     HttpServletRequest request, 
                                     HttpServletResponse response) throws IOException, 
                                                                          ServletException {
        
        SessionUtil sessionUtil =new SessionUtil();
        //Suppression des anciens Bean de type Form de la session, SAUF "certificationChequeForm"
        sessionUtil.removeSession(request,"certificationChequeForm"); 
        
        Context context = ContextHandler.getContext();
        CertificationChequeForm certificationChequeForm = 
            (CertificationChequeForm)form;
        ActionMessages actionMessages = new ActionMessages();

        try {

            String reqCodeTemp = 
                certificationChequeForm.getInitialisationView().getReqCode();
            certificationChequeForm.setContratView(new ContratView());
            certificationChequeForm.clearForm();
            //---affectation du parametre de session code agence, maticule personnel et date du jour
            ParamAgence paramAgence = 
                (ParamAgence)request.getSession().getAttribute("paramAgBNA"); /// structure de l'agent

            certificationChequeForm.getInitialisationView().setNumMatrUser(paramAgence.getNumMatrUser().toString());
            certificationChequeForm.getInitialisationView().setCodeAgence(paramAgence.getCodStrcStrc().toString());
            certificationChequeForm.getInitialisationView().setDateOp(paramAgence.getDateOp());
           /* certificationChequeForm.getInitialisationView().setDateValCr(paramAgence.getDateValCr());
            certificationChequeForm.getInitialisationView().setDateValDb(paramAgence.getDateValDb());
            certificationChequeForm.getInitialisationView().setDateValEpCr(paramAgence.getDateValEpCr());
            certificationChequeForm.getInitialisationView().setDateValEpDb(paramAgence.getDateValEpDb());*/

            certificationChequeForm.setCodAgPayement(paramAgence.getCodStrcStrc().toString());
            certificationChequeForm.setLibelleOperation("Certification chèque (Même Agence / déplacée )");
            certificationChequeForm.setDateCertCchq(certificationChequeForm.getInitialisationView().getDateActuelle());

            certificationChequeForm.getInitialisationView().setReqCode(reqCodeTemp);
            //----------------------------------------------------------------------------------------//


            return mapping.findForward("success");
        } catch (Exception e) {
            com.oxia.fwk.core.Error erreur = new com.oxia.fwk.core.Error();
            StringBuffer text = 
                new StringBuffer("la transaction est Interrompu, une erreur dans CertificationChequesAction / Dispatch Action :initierPage ");
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


    public ActionForward initierPageRecherche(ActionMapping mapping, 
                                              ActionForm form, 
                                              HttpServletRequest request, 
                                              HttpServletResponse response) throws IOException, 
                                                                                   ServletException {
        Context context = ContextHandler.getContext();
        CertificationChequeForm certificationChequeForm = 
            (CertificationChequeForm)form;
        ActionMessages actionMessages = new ActionMessages();

        try {
            ParamAgence paramAgence = 
                (ParamAgence)request.getSession().getAttribute("paramAgBNA"); /// structure de l'agent 

            certificationChequeForm.getInitialisationView().setNumMatrUser(paramAgence.getNumMatrUser().toString());
            certificationChequeForm.getInitialisationView().setCodeAgence(paramAgence.getCodStrcStrc().toString());
            certificationChequeForm.getInitialisationView().setDateOp(paramAgence.getDateOp());
            /*certificationChequeForm.getInitialisationView().setDateValCr(paramAgence.getDateValCr());
            certificationChequeForm.getInitialisationView().setDateValDb(paramAgence.getDateValDb());
            certificationChequeForm.getInitialisationView().setDateValEpCr(paramAgence.getDateValEpCr());
            certificationChequeForm.getInitialisationView().setDateValEpDb(paramAgence.getDateValEpDb());*/
            certificationChequeForm.setLibelleOperation("Traitement des demandes de Certification chèques Déplacées");
            certificationChequeForm.clearFormListeCertDeplacee();
            return mapping.findForward("ListeCertifCheques");

        } catch (Exception e) {
            com.oxia.fwk.core.Error erreur = new com.oxia.fwk.core.Error();
            StringBuffer text = 
                new StringBuffer("la transaction est Interrompu, une erreur dans CertificationChequesAction / Dispatch Action :initierPageRecherche ");
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


    public ActionForward initierPageConsultation(ActionMapping mapping, 
                                                 ActionForm form, 
                                                 HttpServletRequest request, 
                                                 HttpServletResponse response) throws IOException, 
                                                                                      ServletException {
        Context context = ContextHandler.getContext();
        CertificationChequeForm certificationChequeForm = 
            (CertificationChequeForm)form;
        ActionMessages actionMessages = new ActionMessages();

        try {
            ParamAgence paramAgence = 
                (ParamAgence)request.getSession().getAttribute("paramAgBNA"); /// structure de l'agent 
            if (certificationChequeForm.getCodeOperation().equals(Constants.COD_OPER_ANNUL_CERT_CHQ.toString()))
                certificationChequeForm.setLibelleOperation("Annulation chèque certifié");
            else if (certificationChequeForm.getCodeOperation().equals(Constants.COD_OPER_REST_CERT_CHQ.toString()))
                certificationChequeForm.setLibelleOperation("Restitution chèque certifié");
            else if (certificationChequeForm.getCodeOperation().equals(Constants.COD_OPER_REGL_CHQ_CERT.toString()))
                certificationChequeForm.setLibelleOperation("Règlement chèque certifié");
            else if (certificationChequeForm.getCodeOperation().equals("Consultation"))
                certificationChequeForm.setLibelleOperation("Consultation demandes chèques certifiés");
            else if (certificationChequeForm.getCodeOperation().equals(Constants.COD_OPER_CERT_CHQ.toString()))
                certificationChequeForm.setLibelleOperation("Validation forçage effectué sur certification chèque");

            certificationChequeForm.clearFormListeCertDeplacee();
            certificationChequeForm.clearFromConsultation();
            certificationChequeForm.getInitialisationView().setNumMatrUser(paramAgence.getNumMatrUser().toString());
            certificationChequeForm.getInitialisationView().setCodeAgence(paramAgence.getCodStrcStrc().toString());
            certificationChequeForm.getInitialisationView().setDateOp(paramAgence.getDateOp());
           /* certificationChequeForm.getInitialisationView().setDateValCr(paramAgence.getDateValCr());
            certificationChequeForm.getInitialisationView().setDateValDb(paramAgence.getDateValDb());
            certificationChequeForm.getInitialisationView().setDateValEpCr(paramAgence.getDateValEpCr());
            certificationChequeForm.getInitialisationView().setDateValEpDb(paramAgence.getDateValEpDb());*/
            certificationChequeForm.setCodStrcRech(paramAgence.getCodStrcStrc().toString());

            certificationChequeForm.setChoix("0");
            return mapping.findForward("ConsultationCertifCheques");

        } catch (Exception e) {
            com.oxia.fwk.core.Error erreur = new com.oxia.fwk.core.Error();
            StringBuffer text = 
                new StringBuffer("la transaction est Interrompu, une erreur dans CertificationChequesAction / Dispatch Action :initierPageConsultation ");
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

    public ActionForward rechercherListeCertChqDeplacee(ActionMapping mapping, 
                                                        ActionForm form, 
                                                        HttpServletRequest request, 
                                                        HttpServletResponse response) throws IOException, 
                                                                                             ServletException {
        Context context = ContextHandler.getContext();
        CertificationChequeForm certificationChequeForm = 
            (CertificationChequeForm)form;
        ActionMessages actionMessages = new ActionMessages();

        try {

            ParamAgence paramAgence = 
                (ParamAgence)request.getSession().getAttribute("paramAgBNA"); /// structure de l'agent 
            ParamDemandeChequeCertifie paramDemandeChequeCertifie = 
                new ParamDemandeChequeCertifie();
            GetListDemandesChequesCertifCmd getListDemandesChequesCertifCmd = 
                new GetListDemandesChequesCertifCmd();
            Listes listChqCertifies = new Listes();
            ContratPersonne contratPersonne = new ContratPersonne();
            ContratCptId contratCptId = new ContratCptId();
            PersonneStrc personneStrc = new PersonneStrc();
            contratPersonne.setPersonneId(personneStrc);
            contratPersonne.setContratCptId(contratCptId);
            paramDemandeChequeCertifie.setContratPersonne(contratPersonne);
            if (certificationChequeForm.getCertDeplacee().equals("Emis")) {
                paramDemandeChequeCertifie.setCodStrcStrcDom(paramAgence.getCodStrcStrc());
                paramDemandeChequeCertifie.setCodEtatCchq(Constants.ETAT_CERT_ATTENTE);
            } else if (certificationChequeForm.getCertDeplacee().equals("Recue")) {
                paramDemandeChequeCertifie.setCodStrcStrcInit(paramAgence.getCodStrcStrc());
                paramDemandeChequeCertifie.setCodEtatCchq(Constants.ETAT_CERT_PREVALIDEE);
            }

            listChqCertifies = 
                    (Listes)getListDemandesChequesCertifCmd.execute(paramDemandeChequeCertifie);
            if (!listChqCertifies.hasError()) {
                if (listChqCertifies.getList() != null && 
                    listChqCertifies.getList().size() > 0) {
                    List listChqCertifiesView = 
                        traiterListedemandesChqACertifier(listChqCertifies.getList(), 
                                                          certificationChequeForm);
                    certificationChequeForm.setListeDemandesChequesCertifies(listChqCertifiesView);
                }
            } else {
                List listErreur = listChqCertifies.getErrors();
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
            //----------------------------------------------------------------------------//            


            return mapping.findForward("ListeCertifCheques");
        } catch (Exception e) {
            com.oxia.fwk.core.Error erreur = new com.oxia.fwk.core.Error();
            StringBuffer text = 
                new StringBuffer("la transaction est Interrompu, une erreur dans CertificationChequesAction / Dispatch Action :rechercherListeCertChqDeplacee ");
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

    public ActionForward rechercherDemandesSelonChoix(ActionMapping mapping, 
                                                      ActionForm form, 
                                                      HttpServletRequest request, 
                                                      HttpServletResponse response) throws IOException, 
                                                                                           ServletException {
        Context context = ContextHandler.getContext();
        CertificationChequeForm certificationChequeForm = 
            (CertificationChequeForm)form;
        ActionMessages actionMessages = new ActionMessages();

        try {

            ParamAgence paramAgence = 
                (ParamAgence)request.getSession().getAttribute("paramAgBNA"); /// structure de l'agent 
            ParamDemandeChequeCertifie paramDemandeChequeCertifie = 
                new ParamDemandeChequeCertifie();
            GetListDemandesChequesCertifCmd getListDemandesChequesCertifCmd = 
                new GetListDemandesChequesCertifCmd();
            Listes listChqCertifies = new Listes();
            ContratPersonne contratPersonne = new ContratPersonne();
            ContratCptId contratCptId = new ContratCptId();
            PersonneStrc personneStrc = new PersonneStrc();
            paramDemandeChequeCertifie.setCodStrcStrcDom(paramAgence.getCodStrcStrc());
            certificationChequeForm.setContratView(null);
            certificationChequeForm.setListeDemandesChequesCertifies(null);
            if (certificationChequeForm.getCodeOperation().equals(Constants.COD_OPER_CERT_CHQ.toString())) {
                paramDemandeChequeCertifie.setCodEtatCchq(Constants.ETAT_CERT_ATTENTE);
            }

            if (certificationChequeForm.getChoix().equals("0")) {
                // traiter le cas de la recherche par type et numéro de pièce
                personneStrc.setCodTpceTpce(new Long(certificationChequeForm.getTypePieceId()));
                personneStrc.setNumPcePers(certificationChequeForm.getNumPieceId());
                contratCptId.setCodStrcStrc(paramAgence.getCodStrcStrc());
                contratPersonne.setContratCptId(contratCptId);
                contratPersonne.setPersonneId(personneStrc);
                paramDemandeChequeCertifie.setContratPersonne(contratPersonne);
            } else if (certificationChequeForm.getChoix().equals("1")) {
                // traiter le cas de la recherche par numéro de contrat
                contratCptId.setCodStrcStrc(new Long(certificationChequeForm.getCodStrcRech()));
                contratCptId.setCodPrdPrd(new Long(certificationChequeForm.getCodPrdRech()));
                contratCptId.setNumCcptCcpt(new Long(certificationChequeForm.getNumCcptRech()));
                contratPersonne.setContratCptId(contratCptId);
                contratPersonne.setPersonneId(personneStrc);
                paramDemandeChequeCertifie.setContratPersonne(contratPersonne);
            } else if (certificationChequeForm.getChoix().equals("2")) {
                // traiter le cas de la recherche par numéro de chèque
                contratPersonne.setPersonneId(personneStrc);
                contratPersonne.setContratCptId(contratCptId);
                paramDemandeChequeCertifie.setContratPersonne(contratPersonne);
                paramDemandeChequeCertifie.setNumChqCchq(Long.valueOf(certificationChequeForm.getNumChequeRech()));
            } else if (certificationChequeForm.getChoix().equals("3")) {
                // traiter le cas de la recherche par date ou periode
                contratPersonne.setPersonneId(personneStrc);
                contratPersonne.setContratCptId(contratCptId);
                paramDemandeChequeCertifie.setContratPersonne(contratPersonne);
                paramDemandeChequeCertifie.setDateDebut(DateHandler.strToDate(certificationChequeForm.getDateDebut()));
                if (!certificationChequeForm.getDateFin().equals(""))
                    paramDemandeChequeCertifie.setDateFin(DateHandler.strToDate(certificationChequeForm.getDateFin()));
            }

            listChqCertifies = 
                    (Listes)getListDemandesChequesCertifCmd.execute(paramDemandeChequeCertifie);
            if (!listChqCertifies.hasError()) {
                if (listChqCertifies.getList() != null && 
                    listChqCertifies.getList().size() > 0) {
                    List listChqCertifiesView = 
                        traiterListedemandesChqACertifier(listChqCertifies.getList(), 
                                                          certificationChequeForm);
                    certificationChequeForm.setListeDemandesChequesCertifies(listChqCertifiesView);
                }
            } else {
                List listErreur = listChqCertifies.getErrors();
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

            return mapping.findForward("ConsultationCertifCheques");


        } catch (Exception e) {
            com.oxia.fwk.core.Error erreur = new com.oxia.fwk.core.Error();
            StringBuffer text = 
                new StringBuffer("la transaction est Interrompu, une erreur dans CertificationChequesAction / Dispatch Action :rechercherDemandesSelonChoix ");
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


    public List traiterListedemandesChqACertifier(List listDemandes, 
                                                  ActionForm form) {

        CertificationChequeForm certificationChequeForm = 
            (CertificationChequeForm)form;
        List listDemandeChequeCertifieView = new ArrayList();
        Context context = ContextHandler.getContext();


        if (listDemandes != null && listDemandes.size() > 0) {

            for (Iterator it = listDemandes.iterator(); it.hasNext(); ) {
                CertificationChequesView certificationChequesView = 
                    new CertificationChequesView();
                CertificationCheques certificationCheques = 
                    (CertificationCheques)it.next();

                certificationChequesView.setCertificationCheques(certificationCheques);
                certificationChequesView.setDateDemande(DateHandler.dateToStr(certificationCheques.getDatCertCchq()));

                if (certificationCheques.getCodEtatCchq() != null) {
                    if (certificationCheques.getCodEtatCchq().equals(Constants.ETAT_CERT_ATTENTE)) {
                        certificationChequesView.setEtatDemande("Attente");
                    } else if (certificationCheques.getCodEtatCchq().equals(Constants.ETAT_CERT_VALIDE)) {
                        certificationChequesView.setEtatDemande("Validée");
                    } else if (certificationCheques.getCodEtatCchq().equals(Constants.ETAT_CERT_ANNULEE)) {
                        certificationChequesView.setEtatDemande("Annulée");
                    } else if (certificationCheques.getCodEtatCchq().equals(Constants.ETAT_CERT_PREVALIDEE)) {
                        certificationChequesView.setEtatDemande("prévalidée");
                    } else if (certificationCheques.getCodEtatCchq().equals(Constants.ETAT_CERT_REGLEE)) {
                        certificationChequesView.setEtatDemande("reglée");
                    } else if (certificationCheques.getCodEtatCchq().equals(Constants.ETAT_CERT_REJETEE)) {
                        certificationChequesView.setEtatDemande("rejetée");
                    } else if (certificationCheques.getCodEtatCchq().equals(Constants.ETAT_CERT_RESTITUEE)) {
                        certificationChequesView.setEtatDemande("restituée");
                    }

                    certificationChequesView.setCodeAgence(StrHandler.lpad(certificationCheques.getContratCpt().getContratCptId().getCodStrcStrc().toString(), 
                                                                           '0', 
                                                                           3));
                    certificationChequesView.setCodeProduit(StrHandler.lpad(certificationCheques.getContratCpt().getContratCptId().getCodPrdPrd().toString(), 
                                                                            '0', 
                                                                            4));
                    certificationChequesView.setNumeroCompte(StrHandler.lpad(certificationCheques.getContratCpt().getContratCptId().getNumCcptCcpt().toString(), 
                                                                             '0', 
                                                                             6));
                    certificationChequesView.setMontantCertification(StrHandler.formatmnt(Math.abs(certificationCheques.getMontCertCchq().doubleValue())));

                    if (certificationCheques.getCodTpceTpce().equals(Constants.COD_CIN))
                        certificationChequesView.setTypePieceDem("CIN");
                    else if (certificationCheques.getCodTpceTpce().equals(Constants.COD_RCS))
                        certificationChequesView.setTypePieceDem("RCS");
                    else if (certificationCheques.getCodTpceTpce().equals(Constants.COD_NUM_ORDRE))
                        certificationChequesView.setTypePieceDem("NUM");

                    if (certificationCheques.getCodAgdCchq() != null)
                        certificationChequesView.setCodeAgenceDom(StrHandler.lpad(certificationCheques.getCodAgdCchq().toString(), 
                                                                                  '0', 
                                                                                  3));

                    if (certificationCheques.getCodAgiCchq() != null)
                        certificationChequesView.setCodeAgenceinit(StrHandler.lpad(certificationCheques.getCodAgiCchq().toString(), 
                                                                                   '0', 
                                                                                   3));


                    listDemandeChequeCertifieView.add(certificationChequesView);
                }
            } // Fin For  
        }
        return listDemandeChequeCertifieView;

    }

    public ActionForward afficherDetailDemande(ActionMapping mapping, 
                                               ActionForm form, 
                                               HttpServletRequest request, 
                                               HttpServletResponse response) throws IOException, 
                                                                                    ServletException {
        Context context = ContextHandler.getContext();
        CertificationChequeForm certificationChequeForm = 
            (CertificationChequeForm)form;
        ActionMessages actionMessages = new ActionMessages();
        try {

            /*******recherche de la demande de certification **********/
            ParamDemandeChequeCertifie paramDemandeChequeCertifie = 
                new ParamDemandeChequeCertifie();
            GetDetailDemandeCertificationChequeCmd getDetailDemandeCertificationChequeCmd = 
                new GetDetailDemandeCertificationChequeCmd();
            paramDemandeChequeCertifie.setNumCertCchq(certificationChequeForm.getNumDemandeChoisie());

            CertificationCheques certificationCheques = 
                new CertificationCheques();
            certificationCheques = 
                    (CertificationCheques)getDetailDemandeCertificationChequeCmd.execute(paramDemandeChequeCertifie);
            certificationChequeForm.setContratView(new ContratView());
            if (!certificationCheques.hasError()) {
                if (certificationCheques.getContratCpt() != null) {
                    certificationChequeForm.getContratView().setContratCpt(certificationCheques.getContratCpt());
                    certificationChequeForm.setNumDemandeAffichee(certificationCheques.getNumCertCchq());
                    certificationChequeForm.setCertificationCheques(certificationCheques);
                    certificationChequeForm.setEtatDemCertifChoisie(certificationCheques.getCodEtatCchq().toString());
                    certificationChequeForm.setDatedemCertChqChoisie(DateHandler.dateToStr(certificationCheques.getDatCertCchq()));
                    //certificationChequeForm.setDatAnnuCchq(DateHandler.dateToStr(certificationCheques.getDatAnnuCchq()));
                   // certificationChequeForm.setDatRestCchq(DateHandler.dateToStr(certificationCheques.getDatRestCchq()));
                    certificationChequeForm.setDatPayCchq(DateHandler.dateToStr(certificationCheques.getDatPayCchq()));
                }

            } else {
                List listErreur = certificationCheques.getErrors();
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
            if (certificationChequeForm.getLibelleOperation().equals("Traitement des demandes de Certification chèques Déplacées"))
                return mapping.findForward("ListeCertifCheques");
            else
                return mapping.findForward("ConsultationCertifCheques");

        } catch (Exception e) {
            com.oxia.fwk.core.Error erreur = new com.oxia.fwk.core.Error();
            StringBuffer text = 
                new StringBuffer("la transaction est Interrompu, une erreur dans CertificationChequesAction / Dispatch Action :afficherDetailDemande ");
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


    public ActionForward rechercherContrat(ActionMapping mapping, 
                                           ActionForm form, 
                                           HttpServletRequest request, 
                                           HttpServletResponse response) throws IOException, 
                                                                                ServletException {
        Context context = ContextHandler.getContext();
        CertificationChequeForm certificationChequeForm = 
            (CertificationChequeForm)form;
        ActionMessages actionMessages = new ActionMessages();
        try {

            /*******recherche du contrat**********/
            GetDetailContratCmd getDetailContratCmd = 
                new GetDetailContratCmd();
            ContratCpt contratCpt = new ContratCpt();
            ContratCptId contratCptId = new ContratCptId();

            ContratView contratView = certificationChequeForm.getContratView();

            String codStrcStrc = 
                certificationChequeForm.getContratView().getCodStrcStrc();
            String codPrdPrd = 
                certificationChequeForm.getContratView().getCodPrdPrd();
            String numCcptCcpt = 
                certificationChequeForm.getContratView().getNumCcptCcpt();

            certificationChequeForm.clearForm();

            //oppositionMoyenPaiementForm.getContratView().setContratCpt(null);
            contratView.setCodStrcStrc(codStrcStrc);
            contratView.setCodPrdPrd(codPrdPrd);
            contratView.setNumCcptCcpt(numCcptCcpt);


            contratCptId.setCodStrcStrc(new Long(certificationChequeForm.getContratView().getCodStrcStrc()));
            contratCptId.setCodPrdPrd(new Long(certificationChequeForm.getContratView().getCodPrdPrd()));
            contratCptId.setNumCcptCcpt(new Long(certificationChequeForm.getContratView().getNumCcptCcpt()));
            contratCpt = (ContratCpt)getDetailContratCmd.execute(contratCptId);

            if (!contratCpt.hasError()) {
                if (contratCpt.getContratCptId() != null) {
                    /* Chargement du contrat si son etat est valide */
                    if (contratCpt.getCodEtatCcpt().equals(Constants.COD_ETAT_CPT_VALID) || 
                        contratCpt.getCodEtatCcpt().equals(Constants.COD_ETAT_CPT_BLOQUE)) {
                        certificationChequeForm.setContratCpt(contratCpt);
                        certificationChequeForm.getContratView().setContratCpt(contratCpt);
                        certificationChequeForm.setSoldeFinal(certificationChequeForm.getContratView().getMontSoldFCcpt());

                        //----------- si l'opérartion est déplacé

                        if (certificationChequeForm.getInitialisationView().getCodeOperation().equals(Constants.COD_OPER_CERT_AUTRE_AG.toString())) {
                            StringBuffer text = new StringBuffer("");
                            text.append("Agence : " + 
                                        contratCpt.getStructure().getLibStrcStrc());

                            if (contratCpt.getStructure().getNumTelStrc() != 
                                null) {
                                text.append(" (Tél : " + 
                                            contratCpt.getStructure().getNumTelStrc() + 
                                            " )");
                            }
                            text.append(" .");
                            certificationChequeForm.getContratView().setMessageContratCpt(text.toString());
                        }
                    } else {
                        certificationChequeForm.setAlertContrat("ContratNonvalide");
                        certificationChequeForm.setEtatContrat(contratCpt.getCodEtatCcpt());
                    }
                } else
                    certificationChequeForm.setAlertContrat("contratInexistant");
            } else {
                List listErreur = contratCpt.getErrors();
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
                new StringBuffer("la transaction est Interrompu, une erreur dans CertificationChequesAction / Dispatch Action :rechercherContrat ");
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


    public void verifOppositionCheque(ActionForm form) {

        CertificationChequeForm certificationChequeForm = 
            (CertificationChequeForm)form;
        VerifOppositionMoyPayCmd verifOppositionMoyPayCmd = 
            new VerifOppositionMoyPayCmd();
        OppositionMoyenPaiementId oppositionMoyenPaiementId = 
            new OppositionMoyenPaiementId();
        oppositionMoyenPaiementId.setCodMoypTmoy(Constants.COD_CHEQUE);
        oppositionMoyenPaiementId.setNumMoypOpmp(certificationChequeForm.getNumCheque());

        PrimitiveVO primitiveVO = 
            (PrimitiveVO)verifOppositionMoyPayCmd.execute(oppositionMoyenPaiementId);
        certificationChequeForm.setVerifOpposition(primitiveVO.isVBool());
        if (primitiveVO.isVBool()) {
            certificationChequeForm.setMotifRejet(Constants.MOTIF_CHQ_OPP.toString());
        }
    }


    public ParamDemandeChequeCertifie gererDemandeCertificationCheques(ActionForm form, 
                                                                       ContratCpt contratCpt) {


        CertificationChequeForm certificationChequeForm = 
            (CertificationChequeForm)form;
        ParamDemandeChequeCertifie paramDemandeChequeCertifie = 
            new ParamDemandeChequeCertifie();
        MandatOperation mandatOperation = new MandatOperation();
        CertificationCheques certificationCheques = new CertificationCheques();
        List listeCerChqMandPers = new ArrayList();
        Tache tache = new Tache();
        TacheId tacheId = new TacheId();
        tacheId.setCodOperOper(new Long(certificationChequeForm.getCodeOperation()));
        tacheId.setCodTachTach(new Long(certificationChequeForm.getCodeTache()));
        tache.setTacheId(tacheId);
        Personnel personnel = new Personnel();
        personnel.setNumMatrUser(certificationChequeForm.getInitialisationView().getNumMatrUser());

        //affectation de la demande de certification
        certificationCheques.setContratCpt(contratCpt);
        certificationCheques.setTache(tache);
        certificationCheques.setPersonnel(personnel);
        certificationCheques.setDatCertCchq(DateHandler.strToDate(certificationChequeForm.getDateCertCchq()));
        certificationCheques.setDatOperCchq(new Date());
        certificationCheques.setCodDemCchq(certificationChequeForm.getTypeDemandeur());
        certificationCheques.setNumChqCchq(new Long(certificationChequeForm.getNumCheque()));
        certificationCheques.setNumPceCchq(certificationChequeForm.getPersonneDemandeur().getNumPcePersDemandeur());
        certificationCheques.setCodTpceTpce(Long.valueOf(certificationChequeForm.getPersonneDemandeur().getCodTpceTpceDemandeur()));
        certificationCheques.setMontCertCchq(new Long(new Double(new Double(StrHandler.strWithoutBlanck(certificationChequeForm.getMontantCertif())).doubleValue() * 
                                                                 1000).longValue()));
        certificationCheques.setCodLieuCchq("1");
        certificationCheques.setCodTypcCchq(certificationChequeForm.getTypeCheque());
        certificationCheques.setNomBenfCchq(certificationChequeForm.getPersonneDemandeur().getNomNomPersDemandeur() + 
                                            "  " + 
                                            certificationChequeForm.getPersonneDemandeur().getNomPrnPersDemandeur());
        certificationCheques.setCodAgdCchq(Long.valueOf(certificationChequeForm.getContratView().getCodStrcStrc()));
        certificationCheques.setCodAgiCchq(Long.valueOf(certificationChequeForm.getCodAgPayement()));
        certificationCheques.setCodDemCchq(certificationChequeForm.getPouvoir().getTypePouvoir());
        certificationCheques.setNomTireCchq(certificationChequeForm.getNomTireCchq());

        if (certificationChequeForm.getCodeOperation().equals(Constants.COD_OPER_CERT_AUTRE_AG.toString()) || 
            (certificationChequeForm.getForcage().equals("true") && 
             certificationChequeForm.getCodeOperation().equals(Constants.COD_OPER_CERT_CHQ.toString())))
            certificationCheques.setCodEtatCchq(Constants.ETAT_CERT_ATTENTE);
        else if ((certificationChequeForm.getCodeOperation().equals(Constants.COD_OPER_CERT_CHQ.toString()) && 
                  certificationChequeForm.getForcage().equals("false")) || 
                 certificationChequeForm.getCodeOperation().equals(Constants.COD_OPER_CERT_CHQ_BENEF.toString()))
            certificationCheques.setCodEtatCchq(Constants.ETAT_CERT_VALIDE);

        if (!certificationChequeForm.getMotifRejet().equals(new String("0"))) {
            MotifRejet motifRejet = new MotifRejet();
            motifRejet.setCodMotfMrej(new Long(certificationChequeForm.getMotifRejet()));
            certificationCheques.setMotifRejet(motifRejet);
            certificationCheques.setCodEtatCchq(Constants.ETAT_CERT_REJETEE);
        }


        if (certificationChequeForm.getTypeDemandeur().equals("C")) {
            // cas cotitulaire
            if (certificationChequeForm.getPouvoir().getListCotitulaire() != 
                null && 
                certificationChequeForm.getPouvoir().getListCotitulaire().size() > 
                0) {
                CoTitulaire cotitulaire = 
                    (CoTitulaire)certificationChequeForm.getPouvoir().getListCotitulaire().get(0);
               // certificationCheques.setCoTitulaire(cotitulaire);
            }
        }


        if (certificationChequeForm.getTypeDemandeur().equals("M")) {
            // extraire le mandat operation en cas d'une mandat spéciale
            if (certificationChequeForm.getPouvoir().getListMandatOperation() != 
                null && 
                certificationChequeForm.getPouvoir().getListMandatOperation().size() > 
                0) {
                mandatOperation = 
                        (MandatOperation)certificationChequeForm.getPouvoir().getListMandatOperation().get(0);
            }

            // cas du mandataires il faut inserer dans la table certifChequeMandatPersonne
            certificationChequeForm.setNumeroMandatChoisi(certificationChequeForm.getPouvoir().getMandat().getNumMandMand().toString());
            if (certificationChequeForm.getPouvoir().getMandat().getCodSignMand().equals("S") && 
                certificationChequeForm.getPouvoir().getMandat().getCodTypMand().equals("G") || 
                certificationChequeForm.getPouvoir().getMandat().getCodTypMand().equals("S") && 
                mandatOperation.getCodSignMaop().equals("S")) {
                // mandat générale et signature séparée  OU mandat spéciale et signature de l'opération séparée
                /// insertion juste du demandeur de cheque                           
                CertifChqMandPers certifChqMandPers = 
                    affecterDonneesCertifChequeMandatpersonne(certificationChequeForm, 
                                                              certificationChequeForm.getPouvoir().getDemandeur().getNumSeqPers(), 
                                                              certificationCheques);
                listeCerChqMandPers.add(certifChqMandPers);
            } else {
                if (certificationChequeForm.getPouvoir().getMandat().getCodSignMand().equals("C") && 
                    certificationChequeForm.getPouvoir().getMandat().getCodTypMand().equals("G") || 
                    certificationChequeForm.getPouvoir().getMandat().getCodTypMand().equals("S") && 
                    mandatOperation.getCodSignMaop().equals("C")) {
                    // mandat générale et signature conjointe  OU mandat spéciale et signature de l'opération conjointe
                    // signature conjointe(insertion de tous les signataires)           
                    for (Iterator it = 
                         certificationChequeForm.getPouvoir().getListMandatPersonne().iterator(); 
                         it.hasNext(); ) {
                        MandatPersonne mandatPersonne = 
                            (MandatPersonne)it.next();
                        CertifChqMandPers certifChqMandPers = 
                            affecterDonneesCertifChequeMandatpersonne(certificationChequeForm, 
                                                                      mandatPersonne.getPersonne().getNumSeqPers(), 
                                                                      certificationCheques);
                        listeCerChqMandPers.add(certifChqMandPers);
                    }
                }
            }
        }

        paramDemandeChequeCertifie.setCertificationCheques(certificationCheques);
        paramDemandeChequeCertifie.setListeCerChqMandPers(listeCerChqMandPers);

        if (certificationCheques.getCodEtatCchq().equals(Constants.ETAT_CERT_VALIDE)) {
            OperationMoyPay operationMoyPayNew = 
                affecterDonneesOperationMoyenPaiement(certificationChequeForm, 
                                                      paramDemandeChequeCertifie);
            paramDemandeChequeCertifie.setOperationMoyPay(operationMoyPayNew);
        }
        return paramDemandeChequeCertifie;
    }

    public CertifChqMandPers affecterDonneesCertifChequeMandatpersonne(ActionForm form, 
                                                                       Long numSeqPers, 
                                                                       CertificationCheques certificationCheques) {

        CertificationChequeForm certificationChequeForm = 
            (CertificationChequeForm)form;
        CertifChqMandPers certifChqMandPers = new CertifChqMandPers();
        CertifChqMandPersId certifChqMandPersId = new CertifChqMandPersId();
        certifChqMandPersId.setNumMandMand(new Long(certificationChequeForm.getNumeroMandatChoisi()));
        certifChqMandPersId.setNumSeqPers(numSeqPers);
        certifChqMandPers.setCertificationCheques(certificationCheques);
        certifChqMandPers.setCertifChqMandPersId(certifChqMandPersId);
        return certifChqMandPers;
    }


    public OperationMoyPay affecterDonneesOperationMoyenPaiement(ActionForm form, 
                                                                 ParamDemandeChequeCertifie paramDemandeChequeCertifie) {


        CertificationChequeForm certificationChequeForm = 
            (CertificationChequeForm)form;
        OperationMoyPay operationMoyPay = new OperationMoyPay();
        Personnel personnelInit = new Personnel();
        personnelInit.setNumMatrUser(certificationChequeForm.getInitialisationView().getNumMatrUser());
        Tache tache = new Tache();
        TacheId tacheId = new TacheId();        
        tacheId.setCodOperOper(new Long(certificationChequeForm.getCodeOperation()));
        tacheId.setCodTachTach(new Long(certificationChequeForm.getCodeTache()));
        tache.setTacheId(tacheId);
        Structure structureInit = new Structure();
        structureInit.setCodStrcStrc(new Long(paramDemandeChequeCertifie.getCertificationCheques().getCodAgiCchq()));
        Structure structureRecep = new Structure();
        structureRecep.setCodStrcStrc(new Long(paramDemandeChequeCertifie.getCertificationCheques().getContratCpt().getStructure().getCodStrcStrc()));
        TypePiece typePieceDem = new TypePiece();
        typePieceDem.setCodTpceTpce(new Long(paramDemandeChequeCertifie.getCertificationCheques().getCodTpceTpce()));
        TypeMoyenPaiement typeMoyenPaiement = new TypeMoyenPaiement();
        typeMoyenPaiement.setCodMoypTmoy(Constants.COD_CHEQUE);
        Devise devise = new Devise();
        devise.setCodDevDev(Long.valueOf(paramDemandeChequeCertifie.getCertificationCheques().getContratCpt().getDevise().getCodDevDev()));

        operationMoyPay.setPersonnelInitiateur(personnelInit);
        operationMoyPay.setTache(tache);
        operationMoyPay.setContratCpt(paramDemandeChequeCertifie.getCertificationCheques().getContratCpt());
        operationMoyPay.setTypePieceDemandeur(typePieceDem);
        operationMoyPay.setNumPcedOmp(paramDemandeChequeCertifie.getCertificationCheques().getNumPceCchq());
        operationMoyPay.setNomNomdOmp(paramDemandeChequeCertifie.getCertificationCheques().getNomBenfCchq());
        operationMoyPay.setNomPrndOmp(paramDemandeChequeCertifie.getCertificationCheques().getNomBenfCchq());
        operationMoyPay.setTypeMoyenPaiement(typeMoyenPaiement);
        operationMoyPay.setDevise(devise);
        operationMoyPay.setStructureInitiatrice(structureInit);
        operationMoyPay.setStructureReceptrice(structureRecep);
        operationMoyPay.setCodDemOmp(paramDemandeChequeCertifie.getCertificationCheques().getCodDemCchq()); // type demandeur (Titulaire,CoTitul,Mandataire)
        operationMoyPay.setMontDinOmp(paramDemandeChequeCertifie.getCertificationCheques().getMontCertCchq());
        operationMoyPay.setNumMoypOmp(paramDemandeChequeCertifie.getCertificationCheques().getNumChqCchq().toString());
        operationMoyPay.setCodEtatOmp(Constants.COD_VALIDATION);
        operationMoyPay.setLibMotfOmp(certificationChequeForm.getLibelleOperation());
        operationMoyPay.setBoolForcOmp(Long.valueOf(0));
        operationMoyPay.setCodRefbOmp(paramDemandeChequeCertifie.getCertificationCheques().getNumChqCchq().toString());
        operationMoyPay.setDatOperOmp(new Date()); //
        if (certificationChequeForm.getMntTvaCchq() != null && 
            !certificationChequeForm.getMntTvaCchq().equals("")) {
            String montant = 
                certificationChequeForm.getMntTvaCchq().replace(".", "");
            operationMoyPay.setMontTvaOmp(Long.valueOf(montant.replace(" ", 
                                                                       "")));

        }
        operationMoyPay.setDatValOmp(DateHandler.strToDate(certificationChequeForm.getDatValCchq()));
        if (tache.getTacheId().getCodOperOper().equals(Constants.COD_OPER_ANNUL_CERT_CHQ) || 
            tache.getTacheId().getCodOperOper().equals(Constants.COD_OPER_REST_CERT_CHQ)) {
            operationMoyPay.setCodSensOmp(Constants.COD_SENS_CR);
        } else {
            operationMoyPay.setCodSensOmp(Constants.COD_SENS_DB);
        }

        // Insertion dans la table Detail_Oper_moy_Paiement.
        operationMoyPay.setDetailOperMoyPaiements(certificationChequeForm.getListDetailOperMoyPai());

        // Insertion dans la table Mand_pers_oper_moy

        if (paramDemandeChequeCertifie.getCertificationCheques().getCodDemCchq().equals("C")) {
            // cas cotitulaire
            if (certificationChequeForm.getPouvoir().getListCotitulaire() != 
                null && 
                certificationChequeForm.getPouvoir().getListCotitulaire().size() > 
                0) {
                CoTitulaire cotitulaire = 
                    (CoTitulaire)certificationChequeForm.getPouvoir().getListCotitulaire().get(0);
                operationMoyPay.setCoTitulaire(cotitulaire);
            }
        }

        if (paramDemandeChequeCertifie.getCertificationCheques().getCodDemCchq().equals("M")) {
            Set listeMandPersOperMoy = new HashSet(0);
            for (Iterator it = 
                 certificationChequeForm.getPouvoir().getListMandatPersonne().iterator(); 
                 it.hasNext(); ) {
                MandatPersonne mandatPersonne = (MandatPersonne)it.next();

                MandPersOperMoy mandPersOperMoy = new MandPersOperMoy();
                MandPersOperMoyId mandPersOperMoyId = new MandPersOperMoyId();
                mandPersOperMoyId.setNumMandMand(Long.valueOf(certificationChequeForm.getNumeroMandatChoisi()));
                mandPersOperMoyId.setNumSeqPers(Long.valueOf(mandatPersonne.getPersonne().getNumSeqPers()));
                mandPersOperMoyId.setNumOperOmp(operationMoyPay.getNumOperOmp());
                mandPersOperMoy.setMandPersOperMoyId(mandPersOperMoyId);
                listeMandPersOperMoy.add(mandPersOperMoy);
            }

            operationMoyPay.setMandPersOperMoies(listeMandPersOperMoy);
            if (certificationChequeForm.getPouvoir().getListMandatOperation() != 
                null && 
                certificationChequeForm.getPouvoir().getListMandatOperation().size() > 
                0) {
                MandatOperation mandatOperation = 
                    (MandatOperation)certificationChequeForm.getPouvoir().getListMandatOperation().get(0);
                operationMoyPay.setMandatOperation(mandatOperation);
            }
        }

        return operationMoyPay;
    }


    public ActionForward validationCerification(ActionMapping mapping, 
                                                ActionForm form, 
                                                HttpServletRequest request, 
                                                HttpServletResponse response) throws IOException, 
                                                                                     ServletException {

        ActionMessages actionMessages = new ActionMessages();
        CertificationChequeForm certificationChequeForm = 
            (CertificationChequeForm)form;
        Context context = ContextHandler.getContext();

        try {
            PecDemandeCertificationChequeCmd pecDemandeCertificationChequeCmd = 
                new PecDemandeCertificationChequeCmd();
            CertificationCheques certificationCheques = 
                new CertificationCheques();
            String forward = "";
            verifOppositionCheque(certificationChequeForm);

            ParamDemandeChequeCertifie paramDemandeChequeCertifie = 
                gererDemandeCertificationCheques(certificationChequeForm, 
                                                 certificationChequeForm.getContratCpt());
            paramDemandeChequeCertifie.setForcage(certificationChequeForm.getForcage());
            ValueObject vo = (ValueObject)pecDemandeCertificationChequeCmd.execute(paramDemandeChequeCertifie);
            
            
            if (!vo.hasError()) {
                CertificationCheques certificationChequesInseree = (CertificationCheques)vo;
                if (!certificationChequesInseree.getCodEtatCchq().equals(Constants.ETAT_CERT_REJETEE)) {
                    String message = "";
                    if (certificationChequeForm.getCodeOperation().equals(Constants.COD_OPER_CERT_CHQ.toString()) || 
                        certificationChequeForm.getCodeOperation().equals(Constants.COD_OPER_CERT_CHQ_BENEF.toString())) {
                        if (certificationChequeForm.getForcage().equals("false")) {
                            message = 
                                    "L'opération de certification du chèque N ° " + 
                                    certificationChequeForm.getNumCheque() + 
                                    " sous le contrat N° " + 
                                    StrHandler.lpad(certificationChequesInseree.getContratCpt().getContratCptId().getCodStrcStrc().toString(), 
                                                    '0', 3) + " " + 
                                    StrHandler.lpad(certificationChequesInseree.getContratCpt().getContratCptId().getCodPrdPrd().toString(), 
                                                    '0', 4) + " " + 
                                    StrHandler.lpad(certificationChequesInseree.getContratCpt().getContratCptId().getNumCcptCcpt().toString(), 
                                                    '0', 6) + 
                                    " a été effectuée avec succès.";
                        } else {
                            message = 
                                    "L'opération de certification du chèque N ° " + 
                                    certificationChequeForm.getNumCheque() + 
                                    " sous le contrat N° " + 
                                    StrHandler.lpad(certificationChequesInseree.getContratCpt().getContratCptId().getCodStrcStrc().toString(), 
                                                    '0', 3) + " " + 
                                    StrHandler.lpad(certificationChequesInseree.getContratCpt().getContratCptId().getCodPrdPrd().toString(), 
                                                    '0', 4) + " " + 
                                    StrHandler.lpad(certificationChequesInseree.getContratCpt().getContratCptId().getNumCcptCcpt().toString(), 
                                                    '0', 6) + 
                                    " est effectuée avec forçage et en attente de validation par le chef d'agence.";
                        }
                    } else if (certificationChequeForm.getCodeOperation().equals(Constants.COD_OPER_CERT_AUTRE_AG.toString())) {
                        message = 
                                "L'opération de certification déplacée du chèque N ° " + 
                                certificationChequeForm.getNumCheque() + 
                                " sous le contrat N° " + 
                                StrHandler.lpad(certificationChequesInseree.getContratCpt().getContratCptId().getCodStrcStrc().toString(), 
                                                '0', 3) + " " + 
                                StrHandler.lpad(certificationChequesInseree.getContratCpt().getContratCptId().getCodPrdPrd().toString(), 
                                                '0', 4) + " " + 
                                StrHandler.lpad(certificationChequesInseree.getContratCpt().getContratCptId().getNumCcptCcpt().toString(), 
                                                '0', 6) + 
                                " a été effectuée avec succès et en attente de prévalidation par l'agence domiciliatrice N° " + 
                                certificationChequeForm.getContratView().getCodStrcStrc();
                    }
                    certificationChequeForm.setLibelleConfirmation(message);
                    forward = "confirmationCertifCheques";
                } else
                    forward = "success";


                return mapping.findForward(forward);
            } else {
                List listError = vo.getErrors();
                com.oxia.fwk.core.Error erreur = 
                    (com.oxia.fwk.core.Error)listError.get(0);
                if (erreur.getCode().equals("Technique") || erreur.getCode().equals("Habilitation")) {
                    ActionMessage actionMessage = 
                        new ActionMessage("exception.generique", 
                                          erreur.getDescription());
                    actionMessages.add("Erreur ", actionMessage);
                    this.saveMessages(request, actionMessages);
                    return mapping.findForward("error");
                } else {
                    certificationChequeForm.setAlertContrat("provision");
                    return mapping.findForward("success");
                }

            }
        } catch (Exception e) {
            com.oxia.fwk.core.Error erreur = new com.oxia.fwk.core.Error();
            StringBuffer text = 
                new StringBuffer("la transaction est Interrompu, une erreur dans CertificationChequesAction / Dispatch Action :validationCerification ");
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

    public ActionForward traitementCerificationDeplacee(ActionMapping mapping, 
                                                        ActionForm form, 
                                                        HttpServletRequest request, 
                                                        HttpServletResponse response) throws IOException, 
                                                                                             ServletException {

        ActionMessages actionMessages = new ActionMessages();
        CertificationChequeForm certificationChequeForm = 
            (CertificationChequeForm)form;
        Context context = ContextHandler.getContext();

        try {

            /* DemandeConditionCmd cmd= new DemandeConditionCmd();
                  DemandeCondition dem = new DemandeCondition(1220,0,0,null,"1110200712201187417",2f,25,new Date());
                  ListConditionVo v=(ListConditionVo)cmd.execute(dem);*/

            PrevaliderCertificationChequeDeplaceeCmd prevaliderCertificationChequeDeplaceeCmd = 
                new PrevaliderCertificationChequeDeplaceeCmd();
            CertificationCheques certificationCheques = new CertificationCheques();
            if (certificationChequeForm.getCertDeplacee().equals("Emis"))
                certificationChequeForm.getCertificationCheques().setCodEtatCchq(Constants.ETAT_CERT_PREVALIDEE);
            else if (certificationChequeForm.getCertDeplacee().equals("Recue"))
                certificationChequeForm.getCertificationCheques().setCodEtatCchq(Constants.ETAT_CERT_VALIDE);
            else if (certificationChequeForm.getCertDeplacee().equals("Rejet")) {
                certificationChequeForm.getCertificationCheques().setCodEtatCchq(Constants.ETAT_CERT_REJETEE);
                MotifRejet motifRejet = new MotifRejet();
                motifRejet.setCodMotfMrej(new Long(Constants.COD_MOTF_REJET_ClientDouteux));
                certificationChequeForm.getCertificationCheques().setMotifRejet(motifRejet);
            }
            ParamDemandeChequeCertifie paramDemandeChequeCertifie = 
                new ParamDemandeChequeCertifie();
            paramDemandeChequeCertifie.setCertificationCheques(certificationChequeForm.getCertificationCheques());
            paramDemandeChequeCertifie.setForcage(certificationChequeForm.getForcage());
            if (certificationChequeForm.getCertDeplacee().equals("Recue")) {
                chargerConditionBanqueAutreOpCertifChq(certificationChequeForm);
                OperationMoyPay operationMoyPay = 
                    affecterDonneesOperationMoyenPaiement(certificationChequeForm, 
                                                          paramDemandeChequeCertifie);
                paramDemandeChequeCertifie.setOperationMoyPay(operationMoyPay);
            }
            ValueObject vo =  (ValueObject)prevaliderCertificationChequeDeplaceeCmd.execute(paramDemandeChequeCertifie);
            
            if (!vo.hasError()) {
                CertificationCheques certificationChequesMaj = (CertificationCheques)vo;
                String message = "";
                if (certificationChequeForm.getCertDeplacee().equals("Emis")) {
                    message = 
                            "L'opération de prévalidation de la certification déplacée du chèque N ° " + 
                            certificationChequesMaj.getNumChqCchq() + 
                            " sous le contrat N° " + 
                            StrHandler.lpad(certificationChequesMaj.getContratCpt().getContratCptId().getCodStrcStrc().toString(), 
                                            '0', 3) + " " + 
                            StrHandler.lpad(certificationChequesMaj.getContratCpt().getContratCptId().getCodPrdPrd().toString(), 
                                            '0', 4) + " " + 
                            StrHandler.lpad(certificationChequesMaj.getContratCpt().getContratCptId().getNumCcptCcpt().toString(), 
                                            '0', 6) + 
                            " a été effectuée avec succès et en attente de validation par l'agence initiatrice N° " + 
                            certificationChequesMaj.getCodAgiCchq();
                } else if (certificationChequeForm.getCertDeplacee().equals("Recue")) {
                    message = 
                            "L'opération de validation de la certification déplacée du chèque N° " + 
                            certificationChequesMaj.getNumChqCchq() + 
                            " sous le contrat N° " + 
                            StrHandler.lpad(certificationChequesMaj.getContratCpt().getContratCptId().getCodStrcStrc().toString(), 
                                            '0', 3) + " " + 
                            StrHandler.lpad(certificationChequesMaj.getContratCpt().getContratCptId().getCodPrdPrd().toString(), 
                                            '0', 4) + " " + 
                            StrHandler.lpad(certificationChequesMaj.getContratCpt().getContratCptId().getNumCcptCcpt().toString(), 
                                            '0', 6) + 
                            " a été effectuée avec succès.";

                    // insertion dans la table Operation_moyens_payement :
                    //OperationMoyPay operationMoyPay = affecterDonneesOperationMoyenPaiement(certificationChequeForm,certificationChequesMaj);                                                          
                }

                certificationChequeForm.setLibelleConfirmation(message);

                if (certificationChequeForm.getCertDeplacee().equals("Emis") || 
                    certificationChequeForm.getCertDeplacee().equals("Recue"))
                    return mapping.findForward("confirmationCertifCheques");
                else
                    return mapping.findForward("ListeCertifCheques");

            } else {
                List listError = vo.getErrors();
                com.oxia.fwk.core.Error erreur = 
                    (com.oxia.fwk.core.Error)listError.get(0);
                if (erreur.getCode().equals("Technique") || erreur.getCode().equals("Habilitation")) {
                    ActionMessage actionMessage = 
                        new ActionMessage("exception.generique", 
                                          erreur.getDescription());
                    actionMessages.add("Erreur ", actionMessage);
                    this.saveMessages(request, actionMessages);
                    return mapping.findForward("error");
                } else {
                    certificationChequeForm.setAlertContrat("provision");
                    return mapping.findForward("ListeCertifCheques");
                }
            }
        } catch (Exception e) {
            com.oxia.fwk.core.Error erreur = new com.oxia.fwk.core.Error();
            StringBuffer text = 
                new StringBuffer("la transaction est Interrompu, une erreur dans CertificationChequesAction / Dispatch Action :traitementCerificationDeplacee ");
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

    public ActionForward validerOperationReglAnnulRest(ActionMapping mapping, 
                                                       ActionForm form, 
                                                       HttpServletRequest request, 
                                                       HttpServletResponse response) throws IOException, 
                                                                                            ServletException {

        ActionMessages actionMessages = new ActionMessages();
        CertificationChequeForm certificationChequeForm = 
            (CertificationChequeForm)form;
        Context context = ContextHandler.getContext();
        ValueObject vo = new ValueObject();
        try {
            ParamDemandeChequeCertifie paramDemandeChequeCertifie = 
                new ParamDemandeChequeCertifie();
            CertificationCheques certificationCheques = 
                new CertificationCheques();
            paramDemandeChequeCertifie.setCertificationCheques(certificationChequeForm.getCertificationCheques());
            if (certificationChequeForm.getCodeOperation().equals(Constants.COD_OPER_ANNUL_CERT_CHQ.toString())) {
                certificationChequeForm.setCodeTache(Constants.COD_TACHE_ANNUL_CERT_CHQ.toString());
                AnnulerCertificationChequeCmd annulerCertificationChequeCmd = new AnnulerCertificationChequeCmd();
                certificationChequeForm.getCertificationCheques().setCodEtatCchq(Constants.ETAT_CERT_ANNULEE);
                //certificationChequeForm.getCertificationCheques().setDatAnnuCchq(new Date());
                chargerConditionBanqueAutreOpCertifChq(certificationChequeForm);
                OperationMoyPay operationMoyPay = 
                    affecterDonneesOperationMoyenPaiement(certificationChequeForm, 
                                                          paramDemandeChequeCertifie);
                paramDemandeChequeCertifie.setOperationMoyPay(operationMoyPay);
                 vo = (ValueObject)annulerCertificationChequeCmd.execute(paramDemandeChequeCertifie);
            } else if (certificationChequeForm.getCodeOperation().equals(Constants.COD_OPER_REST_CERT_CHQ.toString())) {
                certificationChequeForm.setCodeTache(Constants.COD_TACHE_REST_CERT_CHQ.toString());
                RestituerCertificationChequeCmd restituerCertificationChequeCmd = 
                    new RestituerCertificationChequeCmd();
                certificationChequeForm.getCertificationCheques().setCodEtatCchq(Constants.ETAT_CERT_RESTITUEE);
                //certificationChequeForm.getCertificationCheques().setDatRestCchq(new Date());
                chargerConditionBanqueAutreOpCertifChq(certificationChequeForm);
                OperationMoyPay operationMoyPay = 
                    affecterDonneesOperationMoyenPaiement(certificationChequeForm, 
                                                          paramDemandeChequeCertifie);
                paramDemandeChequeCertifie.setOperationMoyPay(operationMoyPay);
                 vo = (ValueObject)restituerCertificationChequeCmd.execute(paramDemandeChequeCertifie);

            } else if (certificationChequeForm.getCodeOperation().equals(Constants.COD_OPER_REGL_CHQ_CERT.toString())) {
                certificationChequeForm.setCodeTache(Constants.COD_TACHE_REGL_CHQ_CERT.toString());
                ReglerCertificationChequeCmd reglerCertificationChequeCmd = 
                    new ReglerCertificationChequeCmd();
                certificationChequeForm.getCertificationCheques().setCodEtatCchq(Constants.ETAT_CERT_REGLEE);
                certificationChequeForm.getCertificationCheques().setDatPayCchq(new Date());
                //OperationMoyPay operationMoyPay =  affecterDonneesOperationMoyenPaiement(certificationChequeForm,paramDemandeChequeCertifie);
                //paramDemandeChequeCertifie.setOperationMoyPay(operationMoyPay);                           
                 vo = (ValueObject)reglerCertificationChequeCmd.execute(paramDemandeChequeCertifie);
            } else if (certificationChequeForm.getCodeOperation().equals(Constants.COD_OPER_CERT_CHQ.toString())) {
                ValiderCertificationChequeForceeCmd validerCertificationChequeForceeCmd = 
                    new ValiderCertificationChequeForceeCmd();
                certificationChequeForm.getCertificationCheques().setCodEtatCchq(Constants.ETAT_CERT_VALIDE);
                chargerConditionBanqueAutreOpCertifChq(certificationChequeForm);
                OperationMoyPay operationMoyPay = affecterDonneesOperationMoyenPaiement(certificationChequeForm, paramDemandeChequeCertifie);
                paramDemandeChequeCertifie.setOperationMoyPay(operationMoyPay);
                 vo = (ValueObject)validerCertificationChequeForceeCmd.execute(paramDemandeChequeCertifie);
            }

            if (vo.hasError()) {
                List listErreur = certificationCheques.getErrors();
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

            } else
                return mapping.findForward("ConsultationCertifCheques");


        } catch (Exception e) {
            com.oxia.fwk.core.Error erreur = new com.oxia.fwk.core.Error();
            StringBuffer text = 
                new StringBuffer("la transaction est Interrompu, une erreur dans CertificationChequesAction / Dispatch Action :validerOperationReglAnnulRest ");
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

    public ActionForward chargerPouvoir(ActionMapping mapping, ActionForm form, 
                                        HttpServletRequest request, 
                                        HttpServletResponse response) throws IOException, 
                                                                             ServletException {

        try {
            CertificationChequeForm certificationChequeForm = 
                (CertificationChequeForm)form;
            PersonneDemandeur personneDemandeur = 
                certificationChequeForm.getPersonneDemandeur();
            Pouvoir pouvoir = 
                (Pouvoir)request.getSession().getAttribute("pouvoir");
            certificationChequeForm.setPouvoir(pouvoir);
            certificationChequeForm.setTypeDemandeur(pouvoir.getTypePouvoir());
            personneDemandeur = pouvoir.chargerPouvoir(personneDemandeur);
            if (personneDemandeur.getTypePouvoir().equals("N") || 
                personneDemandeur.getTypePouvoir().equals("")) {
                certificationChequeForm.getPouvoir().setTypePouvoir("TR");
                certificationChequeForm.setAlertDemandeur("demandeurTierce");
            } else
                certificationChequeForm.setAlertDemandeur("");
            chargerConditionBanqueCertifChqMemeAgence(certificationChequeForm);

            /*OperationMoyPay  operationMoyPay = new OperationMoyPay();
            operationMoyPay = operationMoyPay.extraireConditionBanque(
            0, // code produit
            Long.valueOf(certificationChequeForm.getCodeOperation()).intValue(), // code operation
            certificationChequeForm.getContratCpt().getContratCptId().getNumCcptCcpt().intValue(), // num sous compte
            certificationChequeForm.getContratCpt().getContratCptId().getCodStrcStrc().intValue(), // code structure
            certificationChequeForm.getContratCpt().getContratCptId().getCodPrdPrd().intValue(),   // code produit
            Long.valueOf(certificationChequeForm.getPersonneDemandeur().getCodTpceTpceDemandeur()).intValue(), // code type piece
            certificationChequeForm.getPersonneDemandeur().getNumPcePersDemandeur(), // num pièce
            null,
            0,
            0,
            certificationChequeForm.getInitialisationView().getDateOp());

            certificationChequeForm.setMntTvaCchq((String.valueOf(operationMoyPay.getMontTvaOmp())));
            certificationChequeForm.setDatValCchq(DateHandler.dateToStr(operationMoyPay.getDatValOmp()));
            certificationChequeForm.setListDetailOperMoyPai(operationMoyPay.getDetailOperMoyPaiements());
            */
            return mapping.findForward("success");
        } catch (Exception e) {
            System.out.println("Erreur chargerPouvoir  " + e.getMessage());
            return mapping.findForward("error");
        }

    }

    public void chargerConditionBanqueCertifChqMemeAgence(ActionForm form) {


        CertificationChequeForm certificationChequeForm = 
            (CertificationChequeForm)form;
        Set listDetailOperMoyPai = new HashSet();
        /*String idContrat = StrHandler.lpad(certificationChequeForm.getContratCpt().getContratCptId().getCodStrcStrc().toString(),'0',3)+
                           StrHandler.lpad(certificationChequeForm.getContratCpt().getContratCptId().getCodPrdPrd().toString(),'0',4)+
                           StrHandler.lpad(certificationChequeForm.getContratCpt().getContratCptId().getNumCcptCcpt().toString(),'0',6);*/

        DemandeConditionCmd cmd = new DemandeConditionCmd();
        DemandeCondition demCond = // code operation
            // num sous compte
            // code structure
            // code produit      
            // code type piece
            // num pièce
            new DemandeCondition(Long.valueOf(certificationChequeForm.getCodeOperation()).intValue(), 
                                 certificationChequeForm.getContratCpt().getContratCptId().getNumCcptCcpt().intValue(), 
                                 certificationChequeForm.getContratCpt().getContratCptId().getCodStrcStrc().intValue(), 
                                 certificationChequeForm.getContratCpt().getContratCptId().getCodPrdPrd().intValue(), 
                                 Long.valueOf(certificationChequeForm.getPersonneDemandeur().getCodTpceTpceDemandeur()).intValue(), 
                                 certificationChequeForm.getPersonneDemandeur().getNumPcePersDemandeur(), 
                                 0, 0, 
                                 certificationChequeForm.getInitialisationView().getDateOp());


        ListConditionVo v = (ListConditionVo)cmd.execute(demCond);

        if (v == null || v.getListConditionBanque().size() == 0){
            System.out.println("\n \n auccune condition de banque a appliquer \n");
            certificationChequeForm.setCondBanque("indisponible");
        }else {
            for (Iterator itCond = v.getListConditionBanque().iterator(); 
                 itCond.hasNext(); ) {
                Condition condition = (Condition)itCond.next();
                List conditionsBanque = condition.getConditionBanque();

                for (Iterator it = conditionsBanque.iterator(); it.hasNext(); 
                ) {

                    ConditionBanque conditionBanque = 
                        (ConditionBanque)it.next();
                    certificationChequeForm.setMntTvaCchq((String.valueOf(conditionBanque.getTvaCalculePourCommisions())));

                    List detailsConditionBanque = 
                        conditionBanque.getDetailConditionBanque();
                    for (Iterator itde = detailsConditionBanque.iterator(); 
                         itde.hasNext(); ) {
                        DetailConditionBanque detailConditionBanque = 
                            (DetailConditionBanque)itde.next();
                        DetailOperMoyPaiement detailOperMoyPaiement = 
                            new DetailOperMoyPaiement();
                        NomencElemtCondition nomencElemtCondition = 
                            new NomencElemtCondition();
                        if (detailConditionBanque.getCodTecdTecd().equals("D")) {
                            // garnir la date valeur
                            System.out.println("Date Systeme " + 
                                               certificationChequeForm.getInitialisationView().getDateOp());
                            System.out.println("Date valeur " + 
                                               detailConditionBanque.getDateValeur());
                            certificationChequeForm.setDatValCchq(detailConditionBanque.getDateValeur());
                        } else {
                            nomencElemtCondition.setCodNecdNecd(detailConditionBanque.getCodNecdNecd());
                            detailOperMoyPaiement.setNomencElemtCondition(nomencElemtCondition);
                            detailOperMoyPaiement.setCodTypDomp(detailConditionBanque.getCodTecdTecd());
                            if (detailConditionBanque.getCodTecdTecd().equals("C")) {
                                certificationChequeForm.setMntFraisCchq(String.valueOf(detailConditionBanque.getValeurCommission()));
                                detailOperMoyPaiement.setMontValDomp(new Long(new Double(new Double(StrHandler.strWithoutBlanck(String.valueOf(detailConditionBanque.getValeurCommission()))).doubleValue()).longValue()));

                            }
                            if (detailConditionBanque.getCodTecdTecd().equals("T")) {
                                detailOperMoyPaiement.setMontValDomp(Long.valueOf(String.valueOf(detailConditionBanque.getValValVael())));
                            }
                            listDetailOperMoyPai.add(detailOperMoyPaiement);
                        }


                    }
                }
            }

            certificationChequeForm.setListDetailOperMoyPai(listDetailOperMoyPai);
        }

    }


    public void chargerConditionBanqueAutreOpCertifChq(ActionForm form) {


        CertificationChequeForm certificationChequeForm = 
            (CertificationChequeForm)form;
        Set listDetailOperMoyPai = new HashSet();

        DemandeConditionCmd cmd = new DemandeConditionCmd();
        DemandeCondition demCond = // code operation
            // num sous compte
            // code structure
            // code produit      
            // code type piece
            // num pièce
            new DemandeCondition(Long.valueOf(certificationChequeForm.getCodeOperation()).intValue(), 
                                 certificationChequeForm.getCertificationCheques().getContratCpt().getContratCptId().getNumCcptCcpt().intValue(), 
                                 certificationChequeForm.getCertificationCheques().getContratCpt().getContratCptId().getCodStrcStrc().intValue(), 
                                 certificationChequeForm.getCertificationCheques().getContratCpt().getContratCptId().getCodPrdPrd().intValue(), 
                                 Long.valueOf(certificationChequeForm.getCertificationCheques().getCodTpceTpce()).intValue(), 
                                 certificationChequeForm.getCertificationCheques().getNumPceCchq(), 
                                 0, 0, 
                                 certificationChequeForm.getInitialisationView().getDateOp());


        ListConditionVo v = (ListConditionVo)cmd.execute(demCond);

        if (v.getListConditionBanque().size() == 0){
            System.out.println("\n \n auccune condition de banque a appliquer \n");
            certificationChequeForm.setCondBanque("indisponible");
        }else {
            for (Iterator itCond = v.getListConditionBanque().iterator(); 
                 itCond.hasNext(); ) {
                Condition condition = (Condition)itCond.next();
                List conditionsBanque = condition.getConditionBanque();

                for (Iterator it = conditionsBanque.iterator(); it.hasNext(); 
                ) {

                    ConditionBanque conditionBanque = 
                        (ConditionBanque)it.next();
                    certificationChequeForm.setMntTvaCchq(Long.valueOf(Float.valueOf(conditionBanque.getTvaCalculePourCommisions()).longValue()).toString());

                    List detailsConditionBanque = 
                        conditionBanque.getDetailConditionBanque();
                    for (Iterator itde = detailsConditionBanque.iterator(); 
                         itde.hasNext(); ) {
                        DetailConditionBanque detailConditionBanque = 
                            (DetailConditionBanque)itde.next();
                        DetailOperMoyPaiement detailOperMoyPaiement = 
                            new DetailOperMoyPaiement();
                        NomencElemtCondition nomencElemtCondition = 
                            new NomencElemtCondition();
                        if (detailConditionBanque.getCodTecdTecd().equals("D"))
                            // garnir la date valeur
                            certificationChequeForm.setDatValCchq(detailConditionBanque.getDateValeur());
                        else {
                            nomencElemtCondition.setCodNecdNecd(detailConditionBanque.getCodNecdNecd());
                            detailOperMoyPaiement.setNomencElemtCondition(nomencElemtCondition);
                            detailOperMoyPaiement.setCodTypDomp(detailConditionBanque.getCodTecdTecd());
                            if (detailConditionBanque.getCodTecdTecd().equals("C")) {
                                certificationChequeForm.setMntFraisCchq(Long.valueOf(Float.valueOf(detailConditionBanque.getValeurCommission()).longValue()).toString());
                                detailOperMoyPaiement.setMontValDomp(new Long(new Double(new Double(StrHandler.strWithoutBlanck(String.valueOf(detailConditionBanque.getValeurCommission()))).doubleValue()).longValue()));

                            }
                            if (detailConditionBanque.getCodTecdTecd().equals("T")) {
                                detailOperMoyPaiement.setMontValDomp(Long.valueOf(String.valueOf(detailConditionBanque.getValValVael())));
                            }
                            listDetailOperMoyPai.add(detailOperMoyPaiement);
                        }


                    }
                }
            }

            certificationChequeForm.setListDetailOperMoyPai(listDetailOperMoyPai);
        }

    }


}


