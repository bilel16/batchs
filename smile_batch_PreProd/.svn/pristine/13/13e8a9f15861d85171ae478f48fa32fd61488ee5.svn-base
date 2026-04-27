package com.bna.smile.web.procuration.actions;


import com.bna.commun.model.ContratCpt;
import com.bna.commun.model.ContratCptId;
import com.bna.commun.model.Mandat;
import com.bna.commun.model.MandatPersonne;
import com.bna.commun.model.PersClient;
import com.bna.commun.model.Personne;
import com.bna.commun.model.Personnel;
import com.bna.commun.model.Structure;
import com.bna.commun.model.StructureDomaine;
import com.bna.commun.model.Tache;
import com.bna.commun.model.TacheId;
import com.bna.commun.model.TraceMandat;
import com.bna.commun.util.DateHandler;
import com.bna.commun.util.SmileUtil;
import com.bna.commun.util.StrHandler;
import com.bna.smile.model.constant.Constants;
import com.bna.smile.model.domainecommun.commande.GetContratMandatCmd;
import com.bna.smile.model.domainecommun.commande.GetPersonneClientQualiteCmd;
import com.bna.smile.model.domainecommun.commande.GetPersonneCptCmd;
import com.bna.smile.model.domainecommun.model.ContratCptMandat;
import com.bna.smile.model.domainecommun.model.Listes;
import com.bna.smile.model.domainecommun.model.MandatPersonneMandat;
import com.bna.smile.model.domainecommun.model.ParamListPersonneQualiteClientVo;
import com.bna.smile.model.domainecommun.model.PersonneCpt;
import com.bna.smile.model.domainecommun.model.PersonneStrc;
import com.bna.smile.model.domainecommun.traitement.GetPersonneByNumSeqPersTrt;
import com.bna.smile.model.domainecontratcompte.procuration.commande.AnnulMandatCmd;
import com.bna.smile.model.domainecontratcompte.procuration.commande.GetContratByDossJurCmd;
import com.bna.smile.model.domainecontratcompte.procuration.commande.GetDetailMandatCmd;
import com.bna.smile.model.domainecontratcompte.procuration.commande.GetMandatAvaliderCmd;
import com.bna.smile.model.domainecontratcompte.procuration.commande.GetMandatCmd;
import com.bna.smile.model.domainecontratcompte.procuration.commande.GetMandatParDemandeCmd;
import com.bna.smile.model.domainecontratcompte.procuration.commande.GetMandatReserveCmd;
import com.bna.smile.model.domainecontratcompte.procuration.commande.GetTraceMandatCmd;
import com.bna.smile.model.domainecontratcompte.procuration.commande.GetTraceMandatCptCmd;
import com.bna.smile.model.domainecontratcompte.procuration.commande.UpdateMandatTraceCmd;
import com.bna.smile.model.domainecontratcompte.procuration.model.DetailMandat;
import com.bna.smile.model.domainecontratcompte.procuration.model.DossierMandat;
import com.bna.smile.model.domainecontratcompte.procuration.model.MandatRecherche;
import com.bna.smile.model.domainecontratcompte.procuration.model.ParamMandOper;
import com.bna.smile.model.domainecontratcompte.procuration.model.StrConcern;
import com.bna.smile.model.reporting.model.CommonReportVO;
import com.bna.smile.web.commun.model.ParamAgence;
import com.bna.smile.web.commun.util.PersonneClientView;
import com.bna.smile.web.commun.util.SessionUtil;
import com.bna.smile.web.procuration.forms.ConsultationMandatForm;
import com.bna.smile.web.procuration.model.TraceMandatView;
import com.bna.smile.web.procuration.util.ContratCptView;
import com.oxia.fwk.core.ValueObject;
import java.io.IOException;
import java.text.SimpleDateFormat;
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


public class ConsultationMandatAction extends DispatchAction {

    /**
     * Action de la page  consultationMandat.jsp 
     * pour initier la page selon l'appel du menu
     * Nom du package : com.bna.smile.web.procuration.actions
     * @author Kriaa hatem & Boussen youssef
     * @version le 19/01/2007
     */
    private static final

    Logger logger = Logger.getLogger(ConsultationMandatAction.class);
    ActionMessages actionMessages = new ActionMessages();
    Long codeOperation;
    Long codTach;

    public

    ActionForward initierPage(ActionMapping mapping, ActionForm form, 
                              HttpServletRequest request, 
                              HttpServletResponse response) throws IOException, 
                                                                   ServletException {


        ParamAgence paramAgence = new ParamAgence();

        paramAgence = 
                (ParamAgence)request.getSession().getAttribute("paramAgBNA"); /// structure de l'agent qui fait la saisie


        SessionUtil sessionUtil = new SessionUtil();
        //Suppression des anciens Bean de type Form de la session, SAUF "consultationMandatForm"
        sessionUtil.removeSession(request, "consultationMandatForm");
        try {
            ConsultationMandatForm consultationMandatForm = 
                (ConsultationMandatForm)form;
            if (!(consultationMandatForm.getCodeTraitement().equalsIgnoreCase("C")) && 
                !(consultationMandatForm.getCodeTraitement().equalsIgnoreCase("X")) && 
                !(consultationMandatForm.getCodeTraitement().equalsIgnoreCase("H")) && 
                !(consultationMandatForm.getCodeTraitement().equalsIgnoreCase("E"))) {
                ///*** verification de l'habilitation sur cet operation (sauf pour la consultation)
                StructureDomaine structureDomaine = 
                    new StructureDomaine(paramAgence.getCodStrcStrc(), 
                                         Constants.COD_DOM_CONTRATCOMPTE);
                boolean bool = SmileUtil.testDomaineOuvert(structureDomaine);
            }
            String codeOperation = 
                consultationMandatForm.getInitialisationView().getCodeOperation();

            consultationMandatForm.clearForm();


            //  paramAgence =(ParamAgence)request.getSession().getAttribute("paramAgBNA"); /// structure de l'agent qui fait la saisie
            consultationMandatForm.getInitialisationView().setCodeOperation(codeOperation);
            consultationMandatForm.getInitialisationView().setDateOp(new Date());
            consultationMandatForm.getInitialisationView().setNumMatrUser(paramAgence.getNumMatrUser().toString());
            consultationMandatForm.getInitialisationView().setCodeAgence(paramAgence.getCodStrcStrc().toString());

            consultationMandatForm.setCodmenu(consultationMandatForm.getCodeTraitement());

            consultationMandatForm.setMyTypeStructure(paramAgence.getCodTstrcTstrc());

            if (paramAgence.getCodTstrcTstrc() == 1) { // agence
                consultationMandatForm.setCodStrcStrc(paramAgence.getCodStrcStrc().toString());
                consultationMandatForm.setChoixagence("A");
            } else {
                consultationMandatForm.setChoixagence("N");
                consultationMandatForm.setCodeTraitDetail("A");
            }

            /**********************cas de consultation***************************/

            if ((consultationMandatForm.getCodeTraitement().equalsIgnoreCase("C"))) {

                return mapping.findForward("debutconsult");

                /**********************cas de consultation mandat par opération***************************/
            } else if ((consultationMandatForm.getCodeTraitement().equalsIgnoreCase("X"))) {
                consultationMandatForm.setDateDebutOper(paramAgence.getDateComptable());
                consultationMandatForm.setDateFinOper(paramAgence.getDateComptable());

                return mapping.findForward("consultmandatOper");
                /**********************cas de consultation historique mandat***************************/
            } else if ((consultationMandatForm.getCodeTraitement().equalsIgnoreCase("H"))) {
                consultationMandatForm.setDateDebutOper(paramAgence.getDateComptable());
                consultationMandatForm.setDateFinOper(paramAgence.getDateComptable());

                return mapping.findForward("consultHistMand");
                /**********************cas de l'edition***************************/
            } else if ((consultationMandatForm.getCodeTraitement().equalsIgnoreCase("E"))) {
                consultationMandatForm.setCodStrConcer(paramAgence.getCodStrcStrc());
                consultationMandatForm.setChoixEdit("0");
                consultationMandatForm.setDateDebutconsult("");
                consultationMandatForm.setDateFinconsult("");
                return mapping.findForward("editMandat");

                /**********************cas de validation de création***************************/
            } else if (consultationMandatForm.getCodeTraitement().equalsIgnoreCase("V")) {
                consultationMandatForm.setCodEtatRecherche(Constants.COD_ETAT_MAND_ATT);
                if (paramAgence.getCodTstrcTstrc() == 1) {
                    return mapping.findForward("validMemeAG");
                }


                /**********************cas de Prévalidation de création***************************/
            } else if (consultationMandatForm.getCodeTraitement().equalsIgnoreCase("P")) {
                consultationMandatForm.setCodEtatRecherche(Constants.COD_ETAT_MAND_ATT_PRE);
                if (paramAgence.getCodTstrcTstrc() == 1) {
                    return mapping.findForward("prevMemeAG");
                }


                /**********************cas de Modification***************************/
            } else if (consultationMandatForm.getCodeTraitement().equalsIgnoreCase("SM")) {
                consultationMandatForm.setCodEtatRecherche(Constants.COD_ETAT_MAND_VALID);
                consultationMandatForm.setCodEtatAttente(Constants.COD_SYCL_MOD);

            } else if (consultationMandatForm.getCodeTraitement().equalsIgnoreCase("PM")) {
                consultationMandatForm.setCodEtatAttente(Constants.COD_ETAT_MAND_ATT_PRE_MOD);

            } else if (consultationMandatForm.getCodeTraitement().equalsIgnoreCase("VM")) {
                consultationMandatForm.setCodEtatAttente(Constants.COD_ETAT_MAND_ATT_VAL_MOD);

                /**********************cas de Annulation***************************/
            } else if (consultationMandatForm.getCodeTraitement().equalsIgnoreCase("SA")) {
                consultationMandatForm.setCodEtatRecherche(Constants.COD_ETAT_MAND_VALID);
                consultationMandatForm.setChoixtab("A");


            } else if (consultationMandatForm.getCodeTraitement().equalsIgnoreCase("PA")) {
                consultationMandatForm.setCodEtatAttente(Constants.COD_ETAT_MAND_ATT_PRE_ANN);
                consultationMandatForm.setChoixtab("A");

            } else if (consultationMandatForm.getCodeTraitement().equalsIgnoreCase("VA")) {
                consultationMandatForm.setCodEtatAttente(Constants.COD_ETAT_MAND_ATT_VAL_ANN);
                consultationMandatForm.setChoixtab("A");

                /**********************cas de Renouvellement***************************/
            } else if (consultationMandatForm.getCodeTraitement().equalsIgnoreCase("SR")) {
                consultationMandatForm.setCodEtatRecherche(Constants.COD_ETAT_MAND_VALID);

            } else if (consultationMandatForm.getCodeTraitement().equalsIgnoreCase("PR")) {
                consultationMandatForm.setCodEtatAttente(Constants.COD_ETAT_MAND_ATT_PRE_REN);

            } else if (consultationMandatForm.getCodeTraitement().equalsIgnoreCase("VR")) {
                consultationMandatForm.setCodEtatAttente(Constants.COD_ETAT_MAND_ATT_VAL_REN);

            } else if (consultationMandatForm.getCodeTraitement().equalsIgnoreCase("CB")) {
                consultationMandatForm.setDateDebutOper(paramAgence.getDateComptable());
                consultationMandatForm.setDateFinOper(paramAgence.getDateComptable());
                return mapping.findForward("rechParBanque");

            } else if (consultationMandatForm.getCodeTraitement().equalsIgnoreCase("AR")) {
                consultationMandatForm.setCodEtatRecherche(Constants.COD_ETAT_MAND_VAL_RES);
                return mapping.findForward("annulReserve");
            }
            return mapping.findForward("parmRecherche");


        } catch (Exception e) {
            com.oxia.fwk.core.Error erreur = new com.oxia.fwk.core.Error();
            StringBuffer text = 
                new StringBuffer("la transaction est Interrompu, une erreur dans ConsultationMandatAction / Dispatch Action :initierPage ");
            text.append(e.toString());
            erreur.setCode("200");
            erreur.setDescription(text.toString());
            logger.error("Exception : ", e);
            ActionMessage actionMessage = 
                new ActionMessage("exception.generique", 
                                  erreur.getDescription());
            actionMessages.add("Erreur ", actionMessage);
            this.saveMessages(request, actionMessages);
            return mapping.findForward("error");
        }


    }

    public ActionForward detailMandat(ActionMapping mapping, ActionForm form, 
                                      HttpServletRequest request, 
                                      HttpServletResponse response) throws IOException, 
                                                                           ServletException {
        /**
         * Action de la page  detailMandat.jsp
         * pour ramener les mandat opérations et mandat personnes d'un mandat choisit
         * Nom du package : com.bna.smile.web.procuration.actions
         * @author Kriaa hatem & Boussen youssef
         * @version le 27/05/2007
         */

        try {


            ConsultationMandatForm consultationMandatForm = 
                (ConsultationMandatForm)form;

            DetailMandat detailMandat = new DetailMandat();
            Long vMandatChoisi = 
                new Long((String)request.getParameter("numand"));
            consultationMandatForm.setMandatchoisi(vMandatChoisi.toString());
            /**********************Cas de modification*********************/
            if ((consultationMandatForm.getCodeTraitement().length() > 1) && 
                (consultationMandatForm.getCodeTraitement().substring(1, 
                                                                      2).equalsIgnoreCase("M"))) {
                return mapping.findForward("successPrevalid");
            }
            /**********************Cas de Prévaliation*********************/
            if (consultationMandatForm.getCodeTraitement().equalsIgnoreCase("P")) {
                return mapping.findForward("successPrevalid");
            }
            /**********************Cas de valiation*********************/
            if (consultationMandatForm.getCodeTraitement().equalsIgnoreCase("V")) {
                return mapping.findForward("successValid");
            } else {
                /**********************commande de recherche du detail du mandat*/
                for (Iterator it = 
                     consultationMandatForm.getListeMandatAvalider().iterator(); 
                     it.hasNext(); ) {

                    MandatPersonneMandat mandatPersonneMandat = 
                        (MandatPersonneMandat)it.next();
                    Mandat mandat1 = new Mandat();
                    mandat1 = mandatPersonneMandat.getMandat();
                    if (mandat1.getNumMandMand().longValue() == 
                        vMandatChoisi.longValue()) {
                        consultationMandatForm.setDatCreMand(DateHandler.dateToStr(mandat1.getDatCreMand()));
                        consultationMandatForm.setDatDebMand(DateHandler.dateToStr(mandat1.getDatDebMand()));
                        consultationMandatForm.setDatFinMand(DateHandler.dateToStr(mandat1.getDatFinMand()));
                        consultationMandatForm.setAncDatFinMand(DateHandler.dateToStr(mandat1.getDatFiniMand()));
                        consultationMandatForm.setCodTypMand(mandat1.getCodTypMand());
                        consultationMandatForm.setNumDemMand(mandat1.getNumDemMand());
                        consultationMandatForm.setCodSignMand(mandat1.getCodSignMand());
                        consultationMandatForm.setNbrMinMand(mandat1.getNbrMinMand());
                        
                        if(mandat1.getContratCpt().getClient().getPersonne().getNumDosJur()!=null){
                            consultationMandatForm.setNumRdjMand(mandat1.getContratCpt().getClient().getPersonne().getNumDosJur());
                        }
                       // if (mandat1.getNumRdjMand() != null) {
                     //       consultationMandatForm.setNumRdjMand(mandat1.getNumRdjMand());
                     //   }
                        if (mandat1.getLibMrejMand() != null && 
                            !mandat1.getLibMrejMand().equalsIgnoreCase("")) {
                            consultationMandatForm.setTypeValidation("R");
                        }
                        if (mandat1.getLibMrsvMand() != null && 
                            !mandat1.getLibMrsvMand().equalsIgnoreCase("")) {
                            consultationMandatForm.setTypeValidation("VR");
                        }
                        consultationMandatForm.setMotifRejet(mandat1.getLibMrejMand());
                        consultationMandatForm.setMotifReserve(mandat1.getLibMrsvMand());
                        consultationMandatForm.setObservation(mandat1.getLibObsMand());
                        GetDetailMandatCmd getDetailMandatCmd = 
                            new GetDetailMandatCmd();
                        detailMandat = 
                                (DetailMandat)getDetailMandatCmd.execute(mandat1);

                    }

                    consultationMandatForm.setDetailMandatOperation(detailMandat.getListeMandatOperations());
                    consultationMandatForm.setDetailMandatPersonne(detailMandat.getListeMandatPersonnes());
                }
                if (consultationMandatForm.getCodeTraitement().equalsIgnoreCase("CB")) {
                    return mapping.findForward("detailMandBq");
                } else {
                    return mapping.findForward("success1");
                }

            }
        } catch (Exception e) {
            com.oxia.fwk.core.Error erreur = new com.oxia.fwk.core.Error();
            StringBuffer text = 
                new StringBuffer("la transaction est Interrompu, une erreur dans ConsultationMandatAction / Dispatch Action :détailMandat ");
            text.append(e.toString());
            erreur.setCode("200");
            erreur.setDescription(text.toString());
            logger.error("Exception : ", e);
            ActionMessage actionMessage = 
                new ActionMessage("exception.generique", 
                                  erreur.getDescription());
            actionMessages.add("Erreur ", actionMessage);
            this.saveMessages(request, actionMessages);
            return mapping.findForward("error");
        }

    }

    public ActionForward detailReserve(ActionMapping mapping, ActionForm form, 
                                       HttpServletRequest request, 
                                       HttpServletResponse response) throws IOException, 
                                                                            ServletException {
        /**
         * Action de la page  detailReserve.jsp
         * pour ramener les mandat opérations et mandat personnes d'un mandat avec reserve
         * Nom du package : com.bna.smile.web.procuration.actions
         * @author Kriaa hatem
         * @version le 30/12/2010
         */

        try {


            ConsultationMandatForm consultationMandatForm = 
                (ConsultationMandatForm)form;

            DetailMandat detailMandat = new DetailMandat();
            Long vMandatChoisi = 
                new Long((String)request.getParameter("numand"));
            consultationMandatForm.setMandatchoisi(vMandatChoisi.toString());

            for (Iterator it = 
                 consultationMandatForm.getListeMandatAvalider().iterator(); 
                 it.hasNext(); ) {

                MandatPersonneMandat mandatPersonneMandat = 
                    (MandatPersonneMandat)it.next();
                Mandat mandat1 = new Mandat();
                mandat1 = mandatPersonneMandat.getMandat();
                if (mandat1.getNumMandMand().longValue() == 
                    vMandatChoisi.longValue()) {
                    consultationMandatForm.setDatCreMand(DateHandler.dateToStr(mandat1.getDatCreMand()));
                    consultationMandatForm.setDatDebMand(DateHandler.dateToStr(mandat1.getDatDebMand()));
                    consultationMandatForm.setDatFinMand(DateHandler.dateToStr(mandat1.getDatFinMand()));
                    consultationMandatForm.setAncDatFinMand(DateHandler.dateToStr(mandat1.getDatFiniMand()));
                    consultationMandatForm.setCodTypMand(mandat1.getCodTypMand());
                    consultationMandatForm.setNumDemMand(mandat1.getNumDemMand());
                    consultationMandatForm.setMotifReserve(mandat1.getLibMrsvMand());
                    consultationMandatForm.setObservation(mandat1.getLibObsMand());

                    GetDetailMandatCmd getDetailMandatCmd = 
                        new GetDetailMandatCmd();
                    detailMandat = 
                            (DetailMandat)getDetailMandatCmd.execute(mandat1);

                }

                consultationMandatForm.setDetailMandatOperation(detailMandat.getListeMandatOperations());
                consultationMandatForm.setDetailMandatPersonne(detailMandat.getListeMandatPersonnes());
            }

            return mapping.findForward("detailReserve");


        } catch (Exception e) {
            com.oxia.fwk.core.Error erreur = new com.oxia.fwk.core.Error();
            StringBuffer text = 
                new StringBuffer("la transaction est Interrompu, une erreur dans ConsultationMandatAction / Dispatch Action :détailMandat ");
            text.append(e.toString());
            erreur.setCode("200");
            erreur.setDescription(text.toString());
            logger.error("Exception : ", e);
            ActionMessage actionMessage = 
                new ActionMessage("exception.generique", 
                                  erreur.getDescription());
            actionMessages.add("Erreur ", actionMessage);
            this.saveMessages(request, actionMessages);
            return mapping.findForward("error");
        }

    }

    public ActionForward detailConsultBq(ActionMapping mapping, 
                                         ActionForm form, 
                                         HttpServletRequest request, 
                                         HttpServletResponse response) throws IOException, 
                                                                              ServletException {
        /**
         * Action de la page  detailConsultBanq.jsp
         * pour ramener les mandat opérations et mandat personnes d'un mandat choisit
         * Nom du package : com.bna.smile.web.procuration.actions
         * @author Kriaa hatem
         * @version le 20/07/2010
         */

        try {


            ConsultationMandatForm consultationMandatForm = 
                (ConsultationMandatForm)form;

            DetailMandat detailMandat = new DetailMandat();
            Long vMandatChoisi = 
                new Long((String)request.getParameter("numand"));
            consultationMandatForm.setMandatchoisi(vMandatChoisi.toString());
            ContratCpt contrat = new ContratCpt();
            Personne personne = new Personne();
            Personne pers = new Personne();
            consultationMandatForm.setPersMorale("false");
            GetPersonneByNumSeqPersTrt getPersonne = 
                new GetPersonneByNumSeqPersTrt();

            /**********************commande de recherche du detail du mandat*/
            for (Iterator it = 
                 consultationMandatForm.getListeMandatAvalider().iterator(); 
                 it.hasNext(); ) {

                MandatPersonneMandat mandatPersonneMandat = 
                    (MandatPersonneMandat)it.next();
                Mandat mandat1 = new Mandat();
                mandat1 = mandatPersonneMandat.getMandat();
                if (mandat1.getNumMandMand().longValue() == 
                    vMandatChoisi.longValue()) {
                    contrat = mandat1.getContratCpt();
                    consultationMandatForm.setCodStrcStrc(contrat.getContratCptId().getCodStrcStrc().toString());
                    consultationMandatForm.setCodPrdPrd(contrat.getContratCptId().getCodPrdPrd().toString());
                    consultationMandatForm.setNumCcptCcpt(contrat.getContratCptId().getNumCcptCcpt().toString());
                    personne.setNumSeqPers(contrat.getClient().getNumSeqPers());
                    pers = (Personne)getPersonne.exec(personne);
                    consultationMandatForm.setTypPcePers(pers.getTypePiece().getCodTpceTpce().toString());
                    consultationMandatForm.setNumPcePers(pers.getNumPcePers().toString());
                    /*cas personne morale*/
                    if (contrat.getClient().getTypePers().getCodTperTper().equals(Constants.PERSMORALE)) {
                        consultationMandatForm.setPersMorale("true");
                        String rue = "";
                        String cite = "";
                        String imm = "";
                        String codp = "";
                        consultationMandatForm.setNomNomPers(pers.getNomRsPers());
                        consultationMandatForm.setNomPrnPers(pers.getLibSiglPers());
                        if (pers.getMontCapPers() != null) {
                            consultationMandatForm.setCapitalSoc(StrHandler.formatmnt(Math.abs(pers.getMontCapPers())));
                        } else {
                            consultationMandatForm.setCapitalSoc("0");
                        }
                        consultationMandatForm.setFormeJur(pers.getFormeJuridique().getLibFjFj());
                        if (contrat.getAdresseCorresp().getImmeuble() != 
                            null) {
                            imm = contrat.getAdresseCorresp().getImmeuble();
                        }
                        if (contrat.getAdresseCorresp().getRue() != null) {
                            rue = contrat.getAdresseCorresp().getRue();
                        }
                        if (contrat.getAdresseCorresp().getCite() != null) {
                            cite = contrat.getAdresseCorresp().getCite();
                        }
                        if (contrat.getAdresseCorresp().getCodCpCp() != null) {
                            codp = contrat.getAdresseCorresp().getCodCpCp();
                        }
                        consultationMandatForm.setSiegeSoc(imm + " " + rue + 
                                                           " " + cite + " " + 
                                                           codp);
                        ParamListPersonneQualiteClientVo paramVo = 
                            new ParamListPersonneQualiteClientVo();
                        paramVo.setNumSeqPers(pers.getNumSeqPers());
                        paramVo.setCodQualQual(Long.valueOf(17));
                        GetPersonneClientQualiteCmd getPersonneClientQualiteCmd = 
                            new GetPersonneClientQualiteCmd();
                        paramVo = 
                                (ParamListPersonneQualiteClientVo)getPersonneClientQualiteCmd.execute(paramVo);
                        if (!paramVo.hasError()) {
                            if (paramVo.getListePersonneClient() != null && 
                                (paramVo.getListePersonneClient().size() > 
                                 0)) {
                                List listeDesPersonnes = new ArrayList();


                                for (Iterator itAss = 
                                     paramVo.getListePersonneClient().iterator(); 
                                     itAss.hasNext(); ) {
                                    PersClient persClient = 
                                        (PersClient)itAss.next();
                                    PersonneClientView personneClientView = 
                                        new PersonneClientView();
                                    personneClientView.setNumSeqPers(persClient.getPersonne().getNumSeqPers());
                                    personneClientView.setCodTpceTpce(persClient.getPersonne().getTypePiece().getCodTpceTpce());
                                    personneClientView.setNumPcePers(persClient.getPersonne().getNumPcePers());
                                    personneClientView.setNomNomPers(persClient.getPersonne().getNomNomPers());
                                    personneClientView.setNomPrnPers(persClient.getPersonne().getNomPrnPers());
                                    if (persClient.getTauxPartPecl() != null) {
                                        personneClientView.setTauxPartPect(persClient.getTauxPartPecl());
                                    }

                                    listeDesPersonnes.add(personneClientView);
                                }

                                consultationMandatForm.setListAssocies(listeDesPersonnes);
                            } else if ((pers.getFormeJuridique().equals(Long.valueOf(30))) && 
                                       (!consultationMandatForm.getCodeTraitement().equalsIgnoreCase("I")) && 
                                       (!consultationMandatForm.getCodeTraitement().equalsIgnoreCase("SA")) && 
                                       (!consultationMandatForm.getCodeTraitement().equalsIgnoreCase("SR")) && 
                                       (!consultationMandatForm.getCodeTraitement().equalsIgnoreCase("SM"))) {

                                consultationMandatForm.setAlertAssocié("false");

                            }


                        }


                    } else { // cas d'une personne physique
                        consultationMandatForm.setNomNomPers(pers.getNomNomPers().toString());
                        consultationMandatForm.setNomPrnPers(pers.getNomPrnPers().toString());
                    }


                    consultationMandatForm.setDatCreMand(DateHandler.dateToStr(mandat1.getDatCreMand()));
                    consultationMandatForm.setDatDebMand(DateHandler.dateToStr(mandat1.getDatDebMand()));
                    consultationMandatForm.setDatFinMand(DateHandler.dateToStr(mandat1.getDatFinMand()));
                    consultationMandatForm.setAncDatFinMand(DateHandler.dateToStr(mandat1.getDatFiniMand()));
                    consultationMandatForm.setCodTypMand(mandat1.getCodTypMand());
                    consultationMandatForm.setNumDemMand(mandat1.getNumDemMand());
                    if (mandat1.getNumRdjMand() != null) {
                        consultationMandatForm.setNumRdjMand(mandat1.getNumRdjMand());
                    }
                    if (mandat1.getLibMrejMand() != null && 
                        !mandat1.getLibMrejMand().equalsIgnoreCase("")) {
                        consultationMandatForm.setTypeValidation("R");
                    }
                    if (mandat1.getLibMrsvMand() != null && 
                        !mandat1.getLibMrsvMand().equalsIgnoreCase("")) {
                        consultationMandatForm.setTypeValidation("VR");
                    }
                    consultationMandatForm.setMotifRejet(mandat1.getLibMrejMand());
                    consultationMandatForm.setMotifReserve(mandat1.getLibMrsvMand());
                    consultationMandatForm.setObservation(mandat1.getLibObsMand());
                    GetDetailMandatCmd getDetailMandatCmd = 
                        new GetDetailMandatCmd();
                    detailMandat = 
                            (DetailMandat)getDetailMandatCmd.execute(mandat1);

                }

                consultationMandatForm.setDetailMandatOperation(detailMandat.getListeMandatOperations());
                consultationMandatForm.setDetailMandatPersonne(detailMandat.getListeMandatPersonnes());
            }

            return mapping.findForward("detailMandBq");


        } catch (Exception e) {
            com.oxia.fwk.core.Error erreur = new com.oxia.fwk.core.Error();
            StringBuffer text = 
                new StringBuffer("la transaction est Interrompu, une erreur dans ConsultationMandatAction / Dispatch Action :détailMandat ");
            text.append(e.toString());
            erreur.setCode("200");
            erreur.setDescription(text.toString());
            logger.error("Exception : ", e);
            ActionMessage actionMessage = 
                new ActionMessage("exception.generique", 
                                  erreur.getDescription());
            actionMessages.add("Erreur ", actionMessage);
            this.saveMessages(request, actionMessages);
            return mapping.findForward("error");
        }

    }

    public ActionForward rechercheParMandat(ActionMapping mapping, 
                                            ActionForm form, 
                                            HttpServletRequest request, 
                                            HttpServletResponse response) throws IOException, 
                                                                                 ServletException {
        /**
         * Action de la page  consultParMandat.jsp
         * recherche un mandat donné par le num de dossier
         * Nom du package : com.bna.smile.web.procuration.actions
         * @author Kriaa hatem & Boussen youssef
         * @version le 27/05/2007
         */

        try {

            ConsultationMandatForm consultationMandatForm = 
                (ConsultationMandatForm)form;

            DetailMandat detailMandat = new DetailMandat();

            /**********************commande de recherche du detail du mandat*/
            Mandat mandat = new Mandat();
            mandat.setNumMandMand(new Long(consultationMandatForm.getMandatchoisi()));
            GetMandatCmd getMandatCmd = new GetMandatCmd();
            Mandat mandat1 = (Mandat)getMandatCmd.execute(mandat);
            GetDetailMandatCmd getDetailMandatCmd = new GetDetailMandatCmd();
            detailMandat = (DetailMandat)getDetailMandatCmd.execute(mandat1);
            consultationMandatForm.setDatCreMand(DateHandler.dateToStr(mandat1.getDatCreMand()));
            consultationMandatForm.setDatDebMand(DateHandler.dateToStr(mandat1.getDatDebMand()));
            consultationMandatForm.setDatFinMand(DateHandler.dateToStr(mandat1.getDatFinMand()));
            consultationMandatForm.setCodTypMand(mandat1.getCodTypMand());
            consultationMandatForm.setDetailMandatOperation(detailMandat.getListeMandatOperations());
            consultationMandatForm.setDetailMandatPersonne(detailMandat.getListeMandatPersonnes());


            return mapping.findForward("parmandat");

        } catch (Exception e) {
            com.oxia.fwk.core.Error erreur = new com.oxia.fwk.core.Error();
            StringBuffer text = 
                new StringBuffer("la transaction est Interrompu, une erreur dans ConsultationMandatAction / Dispatch Action :rechercheParMandat ");
            text.append(e.toString());
            erreur.setCode("200");
            erreur.setDescription(text.toString());
            logger.error("Exception : ", e);
            ActionMessage actionMessage = 
                new ActionMessage("exception.generique", 
                                  erreur.getDescription());
            actionMessages.add("Erreur ", actionMessage);
            this.saveMessages(request, actionMessages);
            return mapping.findForward("error");
        }

    }


    public ActionForward annulMandat(ActionMapping mapping, ActionForm form, 
                                     HttpServletRequest request, 
                                     HttpServletResponse response) throws IOException, 
                                                                          ServletException {
        /**
         * Action de la page  consultationMandat.jsp
         * Annule un ou plusieurs mandats donné
         * Nom du package : com.bna.smile.web.procuration.actions
         * @author Kriaa hatem & Boussen youssef
         * @version le 27/05/2007
         */

        ActionMessages actionMessages = new ActionMessages();
        try {

            ParamAgence paramAgence = new ParamAgence();
            paramAgence = 
                    (ParamAgence)request.getSession().getAttribute("paramAgBNA"); /// structure de l'agent qui fait la saisie
            ConsultationMandatForm consultationMandatForm = 
                (ConsultationMandatForm)form;
            // List index = consultationMandatForm.getIndexMandatChoisis();
            Mandat mandatRetour = new Mandat();
            String message = "";
            SimpleDateFormat formater = new SimpleDateFormat("dd/MM/yyyy");
            String heureString = 
                formater.format(consultationMandatForm.getInitialisationView().getDateOp());
            codeOperation = Constants.COD_OPER_ANNUL_MAND;
            for (Iterator it = 
                 consultationMandatForm.getListeMandatAvalider().iterator(); 
                 it.hasNext(); ) {
                MandatPersonneMandat mandatPersonneMandat = 
                    (MandatPersonneMandat)it.next();
                Mandat mandat1 = mandatPersonneMandat.getMandat();

                if (mandat1.getNumMandMand().longValue() == 
                    new Long(consultationMandatForm.getMandatchoisi()).longValue()) {
                    /*cas de saisie d'annulation*/
                    if (consultationMandatForm.getCodeTraitement().equalsIgnoreCase("SA")) {

                        mandat1.setDatEnvaMand(new Date());
                        mandat1.setCodEdemMand(Constants.COD_ETAT_MAND_ATT_PRE_ANN);
                        getStructureConcernee(consultationMandatForm, mandat1, 
                                              paramAgence);
                        codTach = Constants.COD_TACH_SAISIE_ANNUL_MAND;
                        TraceMandat traceMandat = new TraceMandat();
                        traceMandat.setMandat(mandat1);
                        Personnel personnel = new Personnel();
                        Structure structure = new Structure();
                        structure.setCodStrcStrc(paramAgence.getCodStrcStrc());
                        personnel.setStructure(structure);
                        personnel.setNumMatrUser(paramAgence.getNumMatrUser());
                        traceMandat.setPersonnel(personnel);
                        Tache tache = new Tache();
                        TacheId tacheId = new TacheId();
                        tacheId.setCodOperOper(codeOperation);
                        tacheId.setCodTachTach(codTach);
                        tache.setTacheId(tacheId);
                        traceMandat.setTache(tache);

                        UpdateMandatTraceCmd updateMandatTraceCmd = 
                            new UpdateMandatTraceCmd();
                        ValueObject vo = 
                            (ValueObject)updateMandatTraceCmd.execute(traceMandat);
                        if (vo == null || vo.hasError()) {
                            List listErreur = vo.getErrors();
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
                        } else {
                            mandatRetour = (Mandat)vo;
                            message = 
                                    "Vous avez saisie l'annulation du mandat N° " + 
                                    mandatRetour.getNumMandMand() + 
                                    " sur le contrat " + 
                                    mandatRetour.getContratCpt().getContratCptId().getCodStrcStrc() + 
                                    mandatRetour.getContratCpt().getContratCptId().getCodPrdPrd() + 
                                    mandatRetour.getContratCpt().getContratCptId().getNumCcptCcpt() + 
                                    " en date du " + heureString;
                        }
                    }
                    if (consultationMandatForm.getCodeTraitement().equalsIgnoreCase("PA")) {
                        message = 
                                "Vous avez prévalidé l'annulation du mandat N° ";

                        if (consultationMandatForm.getTypeValidation().equalsIgnoreCase("VR")) {
                            mandat1.setLibMrsvMand(consultationMandatForm.getMotifReserve());
                            message = 
                                    "Vous avez prévalidé avec réserve l'annulation du mandat N° ";

                        } else if (consultationMandatForm.getTypeValidation().equalsIgnoreCase("R")) { /// rejet annulation
                            mandat1.setLibMrejMand(consultationMandatForm.getMotifRejet());
                            message = 
                                    "Vous avez rejeté l'annulation du mandat N° ";

                        }
                        
                        mandat1.setNumRdjMand(consultationMandatForm.getNumRdjMand());
                        mandat1.setLibObsMand(consultationMandatForm.getObservation());
                        mandat1.setCodEdemMand(Constants.COD_ETAT_MAND_ATT_VAL_ANN);
                        //getStructureConcernee(consultationMandatForm,mandat1,paramAgence);
                        codTach = Constants.COD_TACH_PREV_ANNUL_MAND;
                        TraceMandat traceMandat = new TraceMandat();
                        traceMandat.setMandat(mandat1);
                        Personnel personnel = new Personnel();
                        Structure structure = new Structure();
                        structure.setCodStrcStrc(paramAgence.getCodStrcStrc());
                        personnel.setStructure(structure);
                        personnel.setNumMatrUser(paramAgence.getNumMatrUser());
                        traceMandat.setPersonnel(personnel);
                        Tache tache = new Tache();
                        TacheId tacheId = new TacheId();
                        tacheId.setCodOperOper(codeOperation);
                        tacheId.setCodTachTach(codTach);
                        tache.setTacheId(tacheId);
                        traceMandat.setTache(tache);
                        UpdateMandatTraceCmd updateMandatTraceCmd = 
                            new UpdateMandatTraceCmd();
                        ValueObject vo = 
                            (ValueObject)updateMandatTraceCmd.execute(traceMandat);
                        if (vo == null || vo.hasError()) {
                            List listErreur = vo.getErrors();
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
                        } else {
                            mandatRetour = (Mandat)vo;

                        }
                        message = 
                                message + mandatRetour.getNumMandMand() + " sur le contrat " + 
                                mandatRetour.getContratCpt().getContratCptId().getCodStrcStrc() + 
                                mandatRetour.getContratCpt().getContratCptId().getCodPrdPrd() + 
                                mandatRetour.getContratCpt().getContratCptId().getNumCcptCcpt() + 
                                " en date du " + heureString;
                    }
                    if (consultationMandatForm.getCodeTraitement().equalsIgnoreCase("VA")) { /*cas de validation d'annulation*/
                        message = 
                                "Vous avez validé l'annulation du mandat N° ";

                        if (consultationMandatForm.getTypeValidation().equalsIgnoreCase("VR")) { /// validation annulation avec  reserve
                            mandat1.setLibMrsvMand(consultationMandatForm.getMotifReserve());
                            message = 
                                    "Vous avez validé avec réserve l'annulation du mandat N° ";

                        }
                        mandat1.setNumRdjMand(consultationMandatForm.getNumRdjMand());
                        mandat1.setCodStrcMand(mandat1.getContratCpt().getContratCptId().getCodStrcStrc());
                        codTach = Constants.COD_TACH_VAL_ANNUL_MAND;
                        TraceMandat traceMandat = new TraceMandat();
                        traceMandat.setMandat(mandat1);
                        Personnel personnel = new Personnel();
                        Structure structure = new Structure();
                        structure.setCodStrcStrc(paramAgence.getCodStrcStrc());
                        personnel.setStructure(structure);
                        personnel.setNumMatrUser(paramAgence.getNumMatrUser());
                        traceMandat.setPersonnel(personnel);
                        Tache tache = new Tache();
                        TacheId tacheId = new TacheId();
                        tacheId.setCodOperOper(codeOperation);
                        tacheId.setCodTachTach(codTach);
                        tache.setTacheId(tacheId);
                        traceMandat.setTache(tache);
                        if (consultationMandatForm.getTypeValidation().equalsIgnoreCase("R")) { // rejet de annulation
                            message = 
                                    "Vous avez rejeté l'annulation du mandat N° ";
                            mandat1.setLibMrejMand(consultationMandatForm.getMotifRejet());
                            mandat1.setCodEdemMand(null);
                            mandat1.setCodEtatMand("V");
                            UpdateMandatTraceCmd updateMandatTraceCmd = 
                                new UpdateMandatTraceCmd();
                            ValueObject vo = 
                                (ValueObject)updateMandatTraceCmd.execute(traceMandat);
                            if (vo == null || vo.hasError()) {
                                List listErreur = vo.getErrors();
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
                            } else {
                                mandatRetour = (Mandat)vo;
                                message = 
                                        message + mandatRetour.getNumMandMand() + 
                                        " sur le contrat " + 
                                        mandatRetour.getContratCpt().getContratCptId().getCodStrcStrc() + 
                                        mandatRetour.getContratCpt().getContratCptId().getCodPrdPrd() + 
                                        mandatRetour.getContratCpt().getContratCptId().getNumCcptCcpt() + 
                                        " en date du " + heureString;
                            }
                        } else {
                            AnnulMandatCmd annulMandatCmd = 
                                new AnnulMandatCmd();
                            ValueObject vo = 
                                (ValueObject)annulMandatCmd.execute(traceMandat);
                            if (vo == null || vo.hasError()) {
                                List listErreur = vo.getErrors();
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
                            } else {
                                mandatRetour = (Mandat)vo;
                                message = 
                                        message + mandatRetour.getNumMandMand() + 
                                        " sur le contrat " + 
                                        mandatRetour.getContratCpt().getContratCptId().getCodStrcStrc() + 
                                        mandatRetour.getContratCpt().getContratCptId().getCodPrdPrd() + 
                                        mandatRetour.getContratCpt().getContratCptId().getNumCcptCcpt() + 
                                        " en date du " + heureString;
                            }
                        }
                    }
                }
            }


            if (mandatRetour != null) {
                consultationMandatForm.setLibelleConfirmation(message);
            }
            return mapping.findForward("confirmation");


        } catch (Exception e) {
            com.oxia.fwk.core.Error erreur = new com.oxia.fwk.core.Error();
            StringBuffer text = 
                new StringBuffer("la transaction est Interrompu, une erreur dans ConsultationMandatAction / Dispatch Action :annulMandat ");
            text.append(e.toString());
            erreur.setCode("200");
            erreur.setDescription(text.toString());
            logger.error("Exception : ", e);
            ActionMessage actionMessage = 
                new ActionMessage("exception.generique", 
                                  erreur.getDescription());
            actionMessages.add("Erreur ", actionMessage);
            this.saveMessages(request, actionMessages);
            return mapping.findForward("error");
        }

    }

    public ActionForward annulerSaisie(ActionMapping mapping, ActionForm form, 
                                       HttpServletRequest request, 
                                       HttpServletResponse response) throws IOException, 
                                                                            ServletException {
        /**
         * Action de la page  consultationMandat.jsp
         * Annule la saisie d'annulation d'un mandat en ramenant
         * les attributs du mandat et leurs valeurs initiales
         * Nom du package : com.bna.smile.web.procuration.actions
         * @author Kriaa hatem & Boussen youssef
         * @version le 27/05/2007
         */
        ActionMessages actionMessages = new ActionMessages();

        try {

            ParamAgence paramAgence = new ParamAgence();
            paramAgence = 
                    (ParamAgence)request.getSession().getAttribute("paramAgBNA"); /// structure de l'agent qui fait la saisie
            ConsultationMandatForm consultationMandatForm = 
                (ConsultationMandatForm)form;
            List index = consultationMandatForm.getIndexMandatChoisis();
            codeOperation = Constants.COD_OPER_ANNUL_MAND;
            for (int i = 0; i < index.size(); i++) {
                if (!index.get(i).equals("")) {
                    Long vMandatChoisi = (new Long((String)index.get(i)));
                    for (Iterator it = 
                         consultationMandatForm.getListeMandatAvalider().iterator(); 
                         it.hasNext(); ) {
                        MandatPersonneMandat mandatPersonneMandat = 
                            (MandatPersonneMandat)it.next();
                        Mandat mandat1 = mandatPersonneMandat.getMandat();
                        if (mandat1.getNumMandMand().longValue() == 
                            (vMandatChoisi.longValue())) {
                            //GetMandatCmd getMandatCmd=new GetMandatCmd();
                            //Mandat mandatAAnnuler=(Mandat)getMandatCmd.execute(mandat1);
                            mandat1.setDatEnvaMand(null);
                            mandat1.setCodEdemMand(null);
                            codTach = 
                                    Constants.COD_TACH__ANNUL_SAISIE_ANNUL_MAND;
                            TraceMandat traceMandat = new TraceMandat();
                            traceMandat.setMandat(mandat1);
                            Personnel personnel = new Personnel();
                            Structure structure = new Structure();
                            structure.setCodStrcStrc(paramAgence.getCodStrcStrc());
                            personnel.setStructure(structure);
                            personnel.setNumMatrUser(paramAgence.getNumMatrUser());
                            traceMandat.setPersonnel(personnel);
                            Tache tache = new Tache();
                            TacheId tacheId = new TacheId();
                            tacheId.setCodOperOper(codeOperation);
                            tacheId.setCodTachTach(codTach);
                            tache.setTacheId(tacheId);
                            traceMandat.setTache(tache);
                            UpdateMandatTraceCmd updateMandatTraceCmd = 
                                new UpdateMandatTraceCmd();
                            ValueObject vo = 
                                (ValueObject)updateMandatTraceCmd.execute(traceMandat);
                            if (vo == null || vo.hasError()) {
                                List listErreur = vo.getErrors();
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


                        }
                    }

                }


            }
            return mapping.findForward("indexSMILE");


        } catch (Exception e) {
            com.oxia.fwk.core.Error erreur = new com.oxia.fwk.core.Error();
            StringBuffer text = 
                new StringBuffer("la transaction est Interrompu, une erreur dans ConsultationMandatAction / Dispatch Action :AnnuleSaisie ");
            text.append(e.toString());
            erreur.setCode("200");
            erreur.setDescription(text.toString());
            logger.error("Exception : ", e);
            ActionMessage actionMessage = 
                new ActionMessage("exception.generique", 
                                  erreur.getDescription());
            actionMessages.add("Erreur ", actionMessage);
            this.saveMessages(request, actionMessages);
            return mapping.findForward("error");
        }

    }

    public ActionForward retourPrevalid(ActionMapping mapping, ActionForm form, 
                                        HttpServletRequest request, 
                                        HttpServletResponse response) throws IOException, 
                                                                             ServletException {
        /**
         * Action de la page  consultationMandat.jsp
         * Annule la saisie d'annulation d'un mandat en ramenant
         * les attributs du mandat et leurs valeurs initiales
         * Nom du package : com.bna.smile.web.procuration.actions
         * @author Kriaa hatem & Boussen youssef
         * @version le 27/05/2007
         */
        ActionMessages actionMessages = new ActionMessages();

        try {

            ParamAgence paramAgence = new ParamAgence();
            paramAgence = 
                    (ParamAgence)request.getSession().getAttribute("paramAgBNA"); /// structure de l'agent qui fait la saisie
            ConsultationMandatForm consultationMandatForm = 
                (ConsultationMandatForm)form;
            List index = consultationMandatForm.getIndexMandatChoisis();
            codeOperation = Constants.COD_OPER_ANNUL_MAND;

            for (Iterator it = 
                 consultationMandatForm.getListeMandatAvalider().iterator(); 
                 it.hasNext(); ) {
                MandatPersonneMandat mandatPersonneMandat = 
                    (MandatPersonneMandat)it.next();
                Mandat mandat1 = mandatPersonneMandat.getMandat();
                if (mandat1.getNumMandMand().longValue() == 
                    new Long(consultationMandatForm.getMandatchoisi()).longValue()) {
                    //cas creation
                    if (mandat1.getCodEtatMand().equalsIgnoreCase(Constants.COD_ETAT_MAND_ATT)) {
                        mandat1.setCodEtatMand(Constants.COD_ETAT_MAND_ATT_PRE);
                    } else if (mandat1.getCodEtatMand().equalsIgnoreCase(Constants.COD_ETAT_MAND_VALID)) {
                        //cas annulation
                        if (mandat1.getCodEdemMand().equalsIgnoreCase(Constants.COD_ETAT_MAND_ATT_VAL_ANN)) {
                            mandat1.setCodEdemMand(Constants.COD_ETAT_MAND_ATT_PRE_ANN);
                            //cas renouvellement    
                        } else if (mandat1.getCodEdemMand().equalsIgnoreCase(Constants.COD_ETAT_MAND_ATT_VAL_REN)) {
                            mandat1.setCodEdemMand(Constants.COD_ETAT_MAND_ATT_PRE_REN);
                        }

                        //cas modification
                    } else if ((mandat1.getCodEtatMand().equalsIgnoreCase(Constants.COD_SYCL_MOD)) && 
                               (mandat1.getCodEdemMand().equalsIgnoreCase(Constants.COD_ETAT_MAND_ATT_VAL_MOD))) {
                        mandat1.setCodEtatMand(Constants.COD_ETAT_MAND_ATT_PRE_MOD);
                    }

                    codTach = Constants.COD_TACH_PREV_ANNUL_MAND;
                    TraceMandat traceMandat = new TraceMandat();
                    traceMandat.setMandat(mandat1);
                    Personnel personnel = new Personnel();
                    Structure structure = new Structure();
                    structure.setCodStrcStrc(paramAgence.getCodStrcStrc());
                    personnel.setStructure(structure);
                    personnel.setNumMatrUser(paramAgence.getNumMatrUser());
                    traceMandat.setPersonnel(personnel);
                    Tache tache = new Tache();
                    TacheId tacheId = new TacheId();
                    tacheId.setCodOperOper(codeOperation);
                    tacheId.setCodTachTach(codTach);
                    tache.setTacheId(tacheId);
                    traceMandat.setTache(tache);
                    UpdateMandatTraceCmd updateMandatTraceCmd = 
                        new UpdateMandatTraceCmd();
                    ValueObject vo = 
                        (ValueObject)updateMandatTraceCmd.execute(traceMandat);
                    if (vo == null || vo.hasError()) {
                        List listErreur = vo.getErrors();
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


            return mapping.findForward("indexSMILE");


        } catch (Exception e) {
            com.oxia.fwk.core.Error erreur = new com.oxia.fwk.core.Error();
            StringBuffer text = 
                new StringBuffer("la transaction est Interrompu, une erreur dans ConsultationMandatAction / Dispatch Action :AnnuleSaisie ");
            text.append(e.toString());
            erreur.setCode("200");
            erreur.setDescription(text.toString());
            logger.error("Exception : ", e);
            ActionMessage actionMessage = 
                new ActionMessage("exception.generique", 
                                  erreur.getDescription());
            actionMessages.add("Erreur ", actionMessage);
            this.saveMessages(request, actionMessages);
            return mapping.findForward("error");
        }

    }


    public ActionForward rechercherMandataValider(ActionMapping mapping, 
                                                  ActionForm form, 
                                                  HttpServletRequest request, 
                                                  HttpServletResponse response) throws IOException, 
                                                                                       ServletException {
        /**
         * Action de la page  consultation.jsp
         * recherche les mandats par num de contrat ou par code agence
         * Nom du package : com.bna.smile.web.procuration.actions
         * @author Kriaa hatem & Boussen youssef
         * @version le 27/05/2007
         */

        try {

            ConsultationMandatForm consultationMandatForm = 
                (ConsultationMandatForm)form;
            consultationMandatForm.setListeMandatAvalider(null);
            consultationMandatForm.setTypPcePers(null);
            consultationMandatForm.setNumPcePers(null);
            consultationMandatForm.setNomNomPers(null);
            consultationMandatForm.setNomPrnPers(null);
            consultationMandatForm.setMontSoldCcpt(null);
            consultationMandatForm.setLibDevDev(null);
            consultationMandatForm.setTypeValidation("");
            consultationMandatForm.setObservation("");
            consultationMandatForm.setMotifRejet("");
            consultationMandatForm.setMotifReserve("");
            consultationMandatForm.setPersMorale("false");
            consultationMandatForm.setListAssocies(null);
            //ActionErrors actionErrors = new ActionErrors();
            ActionMessages messages = new ActionMessages();
            MandatRecherche mandatRecherche = new MandatRecherche();
            StrConcern strConcern = new StrConcern();
            ParamAgence paramAgence = new ParamAgence();
            paramAgence = 
                    (ParamAgence)request.getSession().getAttribute("paramAgBNA"); /// structure de l'agent qui fait la saisie
            ContratCptId contratCptId = new ContratCptId();
            ContratCpt contratCpt = new ContratCpt();
            Listes liste = new Listes();
            consultationMandatForm.setAlert("");
            //  consultationMandatForm.setCodEtatAttente(null);
            //changement des codes etat de recherche pour la consultation
            if ((consultationMandatForm.getCodeTraitement().equalsIgnoreCase("C") || 
                 consultationMandatForm.getCodeTraitement().equalsIgnoreCase("E"))) {

                if (consultationMandatForm.getCodEtatRecherche().equalsIgnoreCase(Constants.COD_SANS_ETAT)) {
                    consultationMandatForm.setCodEtatAttente(null);
                    consultationMandatForm.setCodEtatRecherche(null);

                } else if (consultationMandatForm.getCodEtatRecherche().equalsIgnoreCase(Constants.COD_ETAT_MAND_ATT_PRE_MOD)) {
                    consultationMandatForm.setCodEtatAttente(Constants.COD_ETAT_MAND_ATT_PRE_MOD);
                    consultationMandatForm.setCodEtatRecherche(Constants.COD_SYCL_MOD);

                } else if (consultationMandatForm.getCodEtatRecherche().equalsIgnoreCase(Constants.COD_ETAT_MAND_ATT_VAL_MOD)) {
                    consultationMandatForm.setCodEtatAttente(Constants.COD_ETAT_MAND_ATT_VAL_MOD);
                    consultationMandatForm.setCodEtatRecherche(Constants.COD_SYCL_MOD);

                } else if (consultationMandatForm.getCodEtatRecherche().equalsIgnoreCase(Constants.COD_ETAT_MAND_ATT_PRE_ANN)) {
                    consultationMandatForm.setCodEtatAttente(Constants.COD_ETAT_MAND_ATT_PRE_ANN);
                    consultationMandatForm.setCodEtatRecherche(Constants.COD_ETAT_MAND_VALID);

                } else if (consultationMandatForm.getCodEtatRecherche().equalsIgnoreCase(Constants.COD_ETAT_MAND_ATT_VAL_ANN)) {
                    consultationMandatForm.setCodEtatAttente(Constants.COD_ETAT_MAND_ATT_VAL_ANN);
                    consultationMandatForm.setCodEtatRecherche(Constants.COD_ETAT_MAND_VALID);

                } else if (consultationMandatForm.getCodEtatRecherche().equalsIgnoreCase(Constants.COD_ETAT_MAND_ATT_PRE_REN)) {
                    consultationMandatForm.setCodEtatAttente(Constants.COD_ETAT_MAND_ATT_PRE_REN);
                    consultationMandatForm.setCodEtatRecherche(Constants.COD_ETAT_MAND_VALID);

                } else if (consultationMandatForm.getCodEtatRecherche().equalsIgnoreCase(Constants.COD_ETAT_MAND_ATT_VAL_REN)) {
                    consultationMandatForm.setCodEtatAttente(Constants.COD_ETAT_MAND_ATT_VAL_REN);
                    consultationMandatForm.setCodEtatRecherche(Constants.COD_ETAT_MAND_VALID);

                } else if (consultationMandatForm.getCodEtatRecherche().equalsIgnoreCase(Constants.COD_ETAT_MAND_VAL_RES)) {
                    consultationMandatForm.setCodEtatAttente(Constants.COD_ETAT_MAND_VAL_RES);
                    consultationMandatForm.setCodEtatRecherche(Constants.COD_ETAT_MAND_VALID);

                } else if (consultationMandatForm.getCodEtatRecherche().equalsIgnoreCase(Constants.COD_ETAT_MAND_REJ_MOD)) {
                    consultationMandatForm.setCodEtatAttente(Constants.COD_ETAT_MAND_REJ_MOD);
                    consultationMandatForm.setCodEtatRecherche(Constants.COD_ETAT_MAND_HIST);
                }

            }
            /*************************************commande de rechercher des mandat Par contrat*/

            if ((consultationMandatForm.getCodPrdPrd() != null) && 
                (!consultationMandatForm.getCodPrdPrd().equalsIgnoreCase("")) && 
                (consultationMandatForm.getCodStrcStrc() != null) && 
                (!consultationMandatForm.getCodStrcStrc().equalsIgnoreCase("")) && 
                (consultationMandatForm.getNumCcptCcpt() != null) && 
                (!consultationMandatForm.getNumCcptCcpt().equalsIgnoreCase(""))) {
                contratCptId.setCodPrdPrd(new Long(consultationMandatForm.getCodPrdPrd()));
                contratCptId.setCodStrcStrc(new Long(consultationMandatForm.getCodStrcStrc()));
                contratCptId.setNumCcptCcpt(new Long(consultationMandatForm.getNumCcptCcpt()));
                mandatRecherche.setContratCptId(contratCptId);
                mandatRecherche.setCodEtat(consultationMandatForm.getCodEtatRecherche());
                mandatRecherche.setCodEtatAttente(consultationMandatForm.getCodEtatAttente());
                if ((consultationMandatForm.getCodeTraitement().equalsIgnoreCase("C"))) {
                    mandatRecherche.setCodStrcConcer(null);
                    //getStructureConcerneeRecherche(consultationMandatForm,mandatRecherche,paramAgence);
                } else {
                    mandatRecherche.setCodStrcConcer(paramAgence.getCodStrcStrc());
                }
                mandatRecherche.setCodMenu(consultationMandatForm.getCodmenu());
                mandatRecherche.setDateDeb(DateHandler.strToDate(consultationMandatForm.getDateDebutconsult()));
                mandatRecherche.setDateFin(DateHandler.strToDate(consultationMandatForm.getDateFinconsult()));
                GetContratMandatCmd getContratMandatCmd = 
                    new GetContratMandatCmd();
                ContratCptMandat contratCptMandat = new ContratCptMandat();
                contratCptMandat = 
                        (ContratCptMandat)getContratMandatCmd.execute(mandatRecherche);
                /*****************Remplissage des informations du compte */
                if (contratCptMandat.getContratCpt() != null) {
                    contratCpt = contratCptMandat.getContratCpt();
                    if ((contratCptMandat.getListeMandat() != null) && 
                        (contratCptMandat.getListeMandat().size() > 0)) {
                        consultationMandatForm.setListeMandat(contratCptMandat.getListeMandat());
                        consultationMandatForm.setListeMandataire(contratCptMandat.getListeMandataire());
                        consultationMandatForm.setListeMandatAvalider(contratCptMandat.getListeMandataire());
                        int listesize = 
                            consultationMandatForm.getListeMandataire().size();
                        for (int i = 0; i < listesize; i++)
                            consultationMandatForm.getIndexMandatChoisis().add("");
                    } else {
                        consultationMandatForm.setAlert("AucunMandat");
                    }
                    consultationMandatForm.setTypPcePers(contratCpt.getClient().getPersonne().getTypePiece().getCodTpceTpce().toString());
                    consultationMandatForm.setNumPcePers(contratCpt.getClient().getPersonne().getNumPcePers().toString());
                    /*cas personne morale*/
                    if (contratCpt.getClient().getTypePers().getCodTperTper().equals(Constants.PERSMORALE)) {
                        consultationMandatForm.setPersMorale("true");
                        String rue = "";
                        String cite = "";
                        String imm = "";
                        String codp = "";
                        consultationMandatForm.setNomNomPers(contratCpt.getClient().getPersonne().getNomRsPers());
                        consultationMandatForm.setNomPrnPers(contratCpt.getClient().getPersonne().getLibSiglPers());
                        if (contratCpt.getClient().getPersonne().getMontCapPers() != 
                            null) {
                            consultationMandatForm.setCapitalSoc(StrHandler.formatmnt(Math.abs(contratCpt.getClient().getPersonne().getMontCapPers())));
                        } else {
                            consultationMandatForm.setCapitalSoc("0");
                        }
                        consultationMandatForm.setFormeJur(contratCpt.getClient().getPersonne().getFormeJuridique().getLibFjFj());
                        if (contratCpt.getAdresseCorresp().getImmeuble() != 
                            null) {
                            imm = contratCpt.getAdresseCorresp().getImmeuble();
                        }
                        if (contratCpt.getAdresseCorresp().getRue() != null) {
                            rue = contratCpt.getAdresseCorresp().getRue();
                        }
                        if (contratCpt.getAdresseCorresp().getCite() != null) {
                            cite = contratCpt.getAdresseCorresp().getCite();
                        }
                        if (contratCpt.getAdresseCorresp().getCodCpCp() != 
                            null) {
                            codp = contratCpt.getAdresseCorresp().getCodCpCp();
                        }
                        consultationMandatForm.setSiegeSoc(imm + " " + rue + 
                                                           " " + cite + " " + 
                                                           codp);
                        ParamListPersonneQualiteClientVo paramVo = 
                            new ParamListPersonneQualiteClientVo();
                        paramVo.setNumSeqPers(contratCpt.getClient().getPersonne().getNumSeqPers());
                        paramVo.setCodQualQual(Long.valueOf(17));
                        GetPersonneClientQualiteCmd getPersonneClientQualiteCmd = 
                            new GetPersonneClientQualiteCmd();
                        paramVo = 
                                (ParamListPersonneQualiteClientVo)getPersonneClientQualiteCmd.execute(paramVo);
                        if (!paramVo.hasError()) {
                            if (paramVo.getListePersonneClient() != null && 
                                (paramVo.getListePersonneClient().size() > 
                                 0)) {
                                List listeDesPersonnes = new ArrayList();


                                for (Iterator it = 
                                     paramVo.getListePersonneClient().iterator(); 
                                     it.hasNext(); ) {
                                    PersClient persClient = 
                                        (PersClient)it.next();
                                    PersonneClientView personneClientView = 
                                        new PersonneClientView();
                                    personneClientView.setNumSeqPers(persClient.getPersonne().getNumSeqPers());
                                    personneClientView.setCodTpceTpce(persClient.getPersonne().getTypePiece().getCodTpceTpce());
                                    personneClientView.setNumPcePers(persClient.getPersonne().getNumPcePers());
                                    personneClientView.setNomNomPers(persClient.getPersonne().getNomNomPers());
                                    personneClientView.setNomPrnPers(persClient.getPersonne().getNomPrnPers());
                                    if (persClient.getTauxPartPecl() != null) {
                                        personneClientView.setTauxPartPect(persClient.getTauxPartPecl());
                                    }

                                    listeDesPersonnes.add(personneClientView);
                                }

                                consultationMandatForm.setListAssocies(listeDesPersonnes);
                            } else if ((contratCpt.getClient().getPersonne().getFormeJuridique().equals(Long.valueOf(30))) && 
                                       (!consultationMandatForm.getCodeTraitement().equalsIgnoreCase("I")) && 
                                       (!consultationMandatForm.getCodeTraitement().equalsIgnoreCase("SA")) && 
                                       (!consultationMandatForm.getCodeTraitement().equalsIgnoreCase("SR")) && 
                                       (!consultationMandatForm.getCodeTraitement().equalsIgnoreCase("SM"))) {

                                consultationMandatForm.setAlertAssocié("false");

                            }


                        }


                    } else { // cas d'une personne physique
                        consultationMandatForm.setNomNomPers(contratCpt.getClient().getPersonne().getNomNomPers().toString());
                        consultationMandatForm.setNomPrnPers(contratCpt.getClient().getPersonne().getNomPrnPers().toString());
                    }
                    consultationMandatForm.setDatOuvCcpt(contratCpt.getDatOuvCcpt().toString());
                    if (contratCpt.getDatCloCcpt() == null) {
                        consultationMandatForm.setDatCloCcpt("");
                    } else {
                        consultationMandatForm.setDatCloCcpt(contratCpt.getDatCloCcpt().toString());
                    }
                    consultationMandatForm.setCodEtatCcpt(contratCpt.getCodEtatCcpt().toString());
                    if (contratCpt.getMontSoldCcpt() != null) {
                        consultationMandatForm.setMontSoldCcpt(contratCpt.getMontSoldCcpt().toString());
                    } else
                        consultationMandatForm.setMontSoldCcpt("0");
                    consultationMandatForm.setLibDevDev(contratCpt.getDevise().getLibDevDev());

                } else {
                    consultationMandatForm.setAlert("contratNonValide");
                }
                return mapping.findForward("success");
            }
            /*************************************commande de rechercher des mandat par agence */
            if (((consultationMandatForm.getCodPrdPrd() == null) || 
                 (consultationMandatForm.getCodPrdPrd().equalsIgnoreCase(""))) && 
                (consultationMandatForm.getCodStrcStrc() != null) && 
                (!consultationMandatForm.getCodStrcStrc().equalsIgnoreCase("")) && 
                ((consultationMandatForm.getNumCcptCcpt() == null) || 
                 (consultationMandatForm.getNumCcptCcpt().equalsIgnoreCase("")))) {
                contratCptId.setCodPrdPrd(null);
                contratCptId.setCodStrcStrc(new Long(consultationMandatForm.getCodStrcStrc()));
                contratCptId.setNumCcptCcpt(null);
                mandatRecherche.setContratCptId(contratCptId);
                mandatRecherche.setCodEtat(consultationMandatForm.getCodEtatRecherche());
                mandatRecherche.setCodEtatAttente(consultationMandatForm.getCodEtatAttente());
                mandatRecherche.setCodMenu(consultationMandatForm.getCodmenu());
                if ((consultationMandatForm.getCodeTraitement().equalsIgnoreCase("C"))) {
                    getStructureConcerneeRecherche(consultationMandatForm, 
                                                   mandatRecherche, 
                                                   paramAgence);
                } else {
                    mandatRecherche.setCodStrcConcer(paramAgence.getCodStrcStrc());
                }
                mandatRecherche.setDateDeb(DateHandler.strToDate(consultationMandatForm.getDateDebutconsult()));
                mandatRecherche.setDateFin(DateHandler.strToDate(consultationMandatForm.getDateFinconsult()));
                GetMandatAvaliderCmd getMandatAvaliderCmd = 
                    new GetMandatAvaliderCmd();

                liste = (Listes)getMandatAvaliderCmd.execute(mandatRecherche);
                if ((liste != null) && (liste.getList().size() > 0)) {
                    consultationMandatForm.setListeMandatAvalider(liste.getList());
                    consultationMandatForm.setListeMandat(liste.getList());
                    consultationMandatForm.setListeMandataire(liste.getList());
                    if (consultationMandatForm.getListeMandataire() != null) {
                        int listesize1 = 
                            consultationMandatForm.getListeMandataire().size();
                        for (int i = 0; i < listesize1; i++)
                            consultationMandatForm.getIndexMandatChoisis().add("");
                    }
                    if (consultationMandatForm.getListeMandatAvalider() != 
                        null && 
                        consultationMandatForm.getListeMandatAvalider().size() > 
                        0) {
                        MandatPersonneMandat mandatPersonneMandat = 
                            (MandatPersonneMandat)consultationMandatForm.getListeMandatAvalider().get(0);
                        consultationMandatForm.setLibStrcStrc(mandatPersonneMandat.getMandat().getContratCpt().getStructure().getLibStrcStrc());
                    }
                } else {
                    consultationMandatForm.setAlert("AucunMandat");
                }
            }
            /*************************************Lecture des erreur dand le VO de retour*/


            return mapping.findForward("success");
        } catch (Exception e) {
            com.oxia.fwk.core.Error erreur = new com.oxia.fwk.core.Error();
            StringBuffer text = 
                new StringBuffer("la transaction est Interrompu, une erreur dans ConsultationMandatAction / Dispatch Action :rechercheMandatAValider ");
            text.append(e.toString());
            erreur.setCode("200");
            erreur.setDescription(text.toString());
            logger.error("Exception : ", e);
            ActionMessage actionMessage = 
                new ActionMessage("exception.generique", 
                                  erreur.getDescription());
            actionMessages.add("Erreur ", actionMessage);
            this.saveMessages(request, actionMessages);
            return mapping.findForward("error");
        }

    }

    public ActionForward getMandatAvecReserve(ActionMapping mapping, 
                                              ActionForm form, 
                                              HttpServletRequest request, 
                                              HttpServletResponse response) throws IOException, 
                                                                                   ServletException {
        /**
         * Action de la page  annulationReserve.jsp
         * recherche les mandats valide avec reserve
         * Nom du package : com.bna.smile.web.procuration.actions
         * @author Kriaa hatem
         * @version le 30/12/2010
         */

        try {

            ConsultationMandatForm consultationMandatForm = 
                (ConsultationMandatForm)form;
            consultationMandatForm.setListeMandatAvalider(null);
            consultationMandatForm.setTypPcePers(null);
            consultationMandatForm.setNumPcePers(null);
            consultationMandatForm.setNomNomPers(null);
            consultationMandatForm.setNomPrnPers(null);
            consultationMandatForm.setMontSoldCcpt(null);
            consultationMandatForm.setLibDevDev(null);
            consultationMandatForm.setTypeValidation("");
            consultationMandatForm.setObservation("");
            consultationMandatForm.setMotifRejet("");
            consultationMandatForm.setMotifReserve("");
            consultationMandatForm.setPersMorale("false");
            consultationMandatForm.setListAssocies(null);
            MandatRecherche mandatRecherche = new MandatRecherche();
            ParamAgence paramAgence = new ParamAgence();
            paramAgence = 
                    (ParamAgence)request.getSession().getAttribute("paramAgBNA"); /// structure de l'agent qui fait la saisie
            ContratCptId contratCptId = new ContratCptId();
            ContratCpt contratCpt = new ContratCpt();
            Listes liste = new Listes();
            consultationMandatForm.setAlert("");
            consultationMandatForm.setCodEtatAttente(Constants.COD_ETAT_MAND_VAL_RES);
            consultationMandatForm.setCodEtatRecherche(Constants.COD_ETAT_MAND_VALID);
            contratCptId.setCodPrdPrd(new Long(consultationMandatForm.getCodPrdPrd()));
            contratCptId.setCodStrcStrc(new Long(consultationMandatForm.getCodStrcStrc()));
            contratCptId.setNumCcptCcpt(new Long(consultationMandatForm.getNumCcptCcpt()));
            mandatRecherche.setContratCptId(contratCptId);

            GetMandatReserveCmd getMandatReserveCmd = 
                new GetMandatReserveCmd();
            ContratCptMandat contratCptMandat = new ContratCptMandat();
            contratCptMandat = 
                    (ContratCptMandat)getMandatReserveCmd.execute(mandatRecherche);
            /*****************Remplissage des informations du compte */
            if (contratCptMandat.getContratCpt() != null) {
                contratCpt = contratCptMandat.getContratCpt();
                if ((contratCptMandat.getListeMandat() != null) && 
                    (contratCptMandat.getListeMandat().size() > 0)) {
                    consultationMandatForm.setListeMandat(contratCptMandat.getListeMandat());
                    consultationMandatForm.setListeMandataire(contratCptMandat.getListeMandataire());
                    consultationMandatForm.setListeMandatAvalider(contratCptMandat.getListeMandataire());
                    int listesize = 
                        consultationMandatForm.getListeMandataire().size();
                    for (int i = 0; i < listesize; i++)
                        consultationMandatForm.getIndexMandatChoisis().add("");
                } else {
                    consultationMandatForm.setAlert("AucunMandat");
                }
                consultationMandatForm.setTypPcePers(contratCpt.getClient().getPersonne().getTypePiece().getCodTpceTpce().toString());
                consultationMandatForm.setNumPcePers(contratCpt.getClient().getPersonne().getNumPcePers().toString());
                /*cas personne morale*/
                if (contratCpt.getClient().getTypePers().getCodTperTper().equals(Constants.PERSMORALE)) {
                    consultationMandatForm.setPersMorale("true");
                    String rue = "";
                    String cite = "";
                    String imm = "";
                    String codp = "";
                    consultationMandatForm.setNomNomPers(contratCpt.getClient().getPersonne().getNomRsPers());
                    consultationMandatForm.setNomPrnPers(contratCpt.getClient().getPersonne().getLibSiglPers());
                    if (contratCpt.getClient().getPersonne().getMontCapPers() != 
                        null) {
                        consultationMandatForm.setCapitalSoc(StrHandler.formatmnt(Math.abs(contratCpt.getClient().getPersonne().getMontCapPers())));
                    } else {
                        consultationMandatForm.setCapitalSoc("0");
                    }
                    consultationMandatForm.setFormeJur(contratCpt.getClient().getPersonne().getFormeJuridique().getLibFjFj());
                    if (contratCpt.getAdresseCorresp().getImmeuble() != null) {
                        imm = contratCpt.getAdresseCorresp().getImmeuble();
                    }
                    if (contratCpt.getAdresseCorresp().getRue() != null) {
                        rue = contratCpt.getAdresseCorresp().getRue();
                    }
                    if (contratCpt.getAdresseCorresp().getCite() != null) {
                        cite = contratCpt.getAdresseCorresp().getCite();
                    }
                    if (contratCpt.getAdresseCorresp().getCodCpCp() != null) {
                        codp = contratCpt.getAdresseCorresp().getCodCpCp();
                    }
                    consultationMandatForm.setSiegeSoc(imm + " " + rue + " " + 
                                                       cite + " " + codp);
                    ParamListPersonneQualiteClientVo paramVo = 
                        new ParamListPersonneQualiteClientVo();
                    paramVo.setNumSeqPers(contratCpt.getClient().getPersonne().getNumSeqPers());
                    paramVo.setCodQualQual(Long.valueOf(17));
                    GetPersonneClientQualiteCmd getPersonneClientQualiteCmd = 
                        new GetPersonneClientQualiteCmd();
                    paramVo = 
                            (ParamListPersonneQualiteClientVo)getPersonneClientQualiteCmd.execute(paramVo);
                    if (!paramVo.hasError()) {
                        if (paramVo.getListePersonneClient() != null && 
                            (paramVo.getListePersonneClient().size() > 0)) {
                            List listeDesPersonnes = new ArrayList();


                            for (Iterator it = 
                                 paramVo.getListePersonneClient().iterator(); 
                                 it.hasNext(); ) {
                                PersClient persClient = (PersClient)it.next();
                                PersonneClientView personneClientView = 
                                    new PersonneClientView();
                                personneClientView.setNumSeqPers(persClient.getPersonne().getNumSeqPers());
                                personneClientView.setCodTpceTpce(persClient.getPersonne().getTypePiece().getCodTpceTpce());
                                personneClientView.setNumPcePers(persClient.getPersonne().getNumPcePers());
                                personneClientView.setNomNomPers(persClient.getPersonne().getNomNomPers());
                                personneClientView.setNomPrnPers(persClient.getPersonne().getNomPrnPers());
                                if (persClient.getTauxPartPecl() != null) {
                                    personneClientView.setTauxPartPect(persClient.getTauxPartPecl());
                                }

                                listeDesPersonnes.add(personneClientView);
                            }

                            consultationMandatForm.setListAssocies(listeDesPersonnes);
                        } else if ((contratCpt.getClient().getPersonne().getFormeJuridique().equals(Long.valueOf(30))) && 
                                   (!consultationMandatForm.getCodeTraitement().equalsIgnoreCase("I")) && 
                                   (!consultationMandatForm.getCodeTraitement().equalsIgnoreCase("SA")) && 
                                   (!consultationMandatForm.getCodeTraitement().equalsIgnoreCase("SR")) && 
                                   (!consultationMandatForm.getCodeTraitement().equalsIgnoreCase("SM"))) {

                            consultationMandatForm.setAlertAssocié("false");

                        }


                    }


                } else { // cas d'une personne physique
                    consultationMandatForm.setNomNomPers(contratCpt.getClient().getPersonne().getNomNomPers().toString());
                    consultationMandatForm.setNomPrnPers(contratCpt.getClient().getPersonne().getNomPrnPers().toString());
                }
                consultationMandatForm.setDatOuvCcpt(contratCpt.getDatOuvCcpt().toString());
                if (contratCpt.getDatCloCcpt() == null) {
                    consultationMandatForm.setDatCloCcpt("");
                } else {
                    consultationMandatForm.setDatCloCcpt(contratCpt.getDatCloCcpt().toString());
                }
                consultationMandatForm.setCodEtatCcpt(contratCpt.getCodEtatCcpt().toString());
                if (contratCpt.getMontSoldCcpt() != null) {
                    consultationMandatForm.setMontSoldCcpt(contratCpt.getMontSoldCcpt().toString());
                } else
                    consultationMandatForm.setMontSoldCcpt("0");
                consultationMandatForm.setLibDevDev(contratCpt.getDevise().getLibDevDev());

            } else {
                consultationMandatForm.setAlert("contratNonValide");
            }


            return mapping.findForward("annulReserve");
        } catch (Exception e) {
            com.oxia.fwk.core.Error erreur = new com.oxia.fwk.core.Error();
            StringBuffer text = 
                new StringBuffer("la transaction est Interrompu, une erreur dans ConsultationMandatAction / Dispatch Action :rechercheMandatAValider ");
            text.append(e.toString());
            erreur.setCode("200");
            erreur.setDescription(text.toString());
            logger.error("Exception : ", e);
            ActionMessage actionMessage = 
                new ActionMessage("exception.generique", 
                                  erreur.getDescription());
            actionMessages.add("Erreur ", actionMessage);
            this.saveMessages(request, actionMessages);
            return mapping.findForward("error");
        }

    }

    public ActionForward annulerReserve(ActionMapping mapping, ActionForm form, 
                                        HttpServletRequest request, 
                                        HttpServletResponse response) throws IOException, 
                                                                             ServletException {
        /**
         * Action de la page  consultationMandat.jsp
         * Annule un motif de reserve
         * Nom du package : com.bna.smile.web.procuration.actions
         * @author Kriaa hatem
         * @version le 03/01/2011
         */

        ActionMessages actionMessages = new ActionMessages();
        try {

            ParamAgence paramAgence = new ParamAgence();
            paramAgence = 
                    (ParamAgence)request.getSession().getAttribute("paramAgBNA");
            ConsultationMandatForm consultationMandatForm = 
                (ConsultationMandatForm)form;

            Mandat mandatRetour = new Mandat();
            String message = "";
            SimpleDateFormat formater = new SimpleDateFormat("dd/MM/yyyy");
            String heureString = 
                formater.format(consultationMandatForm.getInitialisationView().getDateOp());
            for (Iterator it = 
                 consultationMandatForm.getListeMandatAvalider().iterator(); 
                 it.hasNext(); ) {
                MandatPersonneMandat mandatPersonneMandat = 
                    (MandatPersonneMandat)it.next();
                Mandat mandat1 = mandatPersonneMandat.getMandat();

                if (mandat1.getNumMandMand().longValue() == 
                    new Long(consultationMandatForm.getMandatchoisi()).longValue()) {

                    if (consultationMandatForm.getMotifReserve().equalsIgnoreCase("")) { /* levee reserve mandat valide*/
                        if ((mandat1.getCodEtatMand().equalsIgnoreCase("V")) && 
                            (mandat1.getCodEdemMand()!=null)) {
                             mandat1.setCodEdemMand(null);
                        }
                        
                    }
                    if (!consultationMandatForm.getMotifReserve().equalsIgnoreCase("")) { /* levee reserve mandat valide*/
                        if ((mandat1.getCodEtatMand().equalsIgnoreCase("V")) && 
                            ((mandat1.getCodEdemMand()==null)||(mandat1.getCodEdemMand().equalsIgnoreCase("")))) {
                            mandat1.setCodEdemMand("VR");
                        }
                        
                    }

                    mandat1.setLibMrsvMand(consultationMandatForm.getMotifReserve());
                    mandat1.setLibObsMand(consultationMandatForm.getObservation());
                    TraceMandat traceMandat = new TraceMandat();
                    traceMandat.setMandat(mandat1);
                    Personnel personnel = new Personnel();
                    Structure structure = new Structure();
                    structure.setCodStrcStrc(paramAgence.getCodStrcStrc());
                    personnel.setStructure(structure);
                    personnel.setNumMatrUser(paramAgence.getNumMatrUser());
                    traceMandat.setPersonnel(personnel);
                    Tache tache = new Tache();
                    TacheId tacheId = new TacheId();
                    tacheId.setCodOperOper(Constants.COD_OPER_LEV_RES);
                    tacheId.setCodTachTach(Constants.COD_TACHE_LEV_RES);
                    tache.setTacheId(tacheId);
                    traceMandat.setTache(tache);

                    UpdateMandatTraceCmd updateMandatTraceCmd = 
                        new UpdateMandatTraceCmd();
                    ValueObject vo = 
                        (ValueObject)updateMandatTraceCmd.execute(traceMandat);
                    if (vo == null || vo.hasError()) {
                        List listErreur = vo.getErrors();
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
                    } else {
                        mandatRetour = (Mandat)vo;
                        message = 
                                "Vous avez modifié l'observation et/ou la réserve du mandat N° " + 
                                mandatRetour.getNumMandMand() + 
                                " sur le contrat " + 
                                mandatRetour.getContratCpt().getContratCptId().getCodStrcStrc() + 
                                mandatRetour.getContratCpt().getContratCptId().getCodPrdPrd() + 
                                mandatRetour.getContratCpt().getContratCptId().getNumCcptCcpt() + 
                                " en date du " + heureString;
                    }

                }
            }


            if (mandatRetour != null) {
                consultationMandatForm.setLibelleConfirmation(message);
            }
            return mapping.findForward("confirmation");


        } catch (Exception e) {
            com.oxia.fwk.core.Error erreur = new com.oxia.fwk.core.Error();
            StringBuffer text = 
                new StringBuffer("la transaction est Interrompu, une erreur dans ConsultationMandatAction / Dispatch Action :annulerReserve ");
            text.append(e.toString());
            erreur.setCode("200");
            erreur.setDescription(text.toString());
            logger.error("Exception : ", e);
            ActionMessage actionMessage = 
                new ActionMessage("exception.generique", 
                                  erreur.getDescription());
            actionMessages.add("Erreur ", actionMessage);
            this.saveMessages(request, actionMessages);
            return mapping.findForward("error");
        }

    }

    public ActionForward rechMandatBanque(ActionMapping mapping, 
                                          ActionForm form, 
                                          HttpServletRequest request, 
                                          HttpServletResponse response) throws IOException, 
                                                                               ServletException {
        /**
         * Action de la page  consultation.jsp
         * recherche les mandats par num de contrat ou par code agence
         * Nom du package : com.bna.smile.web.procuration.actions
         * @author Kriaa hatem & Boussen youssef
         * @version le 27/05/2007
         */

        try {

            ConsultationMandatForm consultationMandatForm = 
                (ConsultationMandatForm)form;


            ActionMessages messages = new ActionMessages();
            MandatRecherche mandatRecherche = new MandatRecherche();
            StrConcern strConcern = new StrConcern();
            ParamAgence paramAgence = new ParamAgence();
            paramAgence = 
                    (ParamAgence)request.getSession().getAttribute("paramAgBNA"); /// structure de l'agent qui fait la saisie

            Listes liste = new Listes();
            consultationMandatForm.setAlert("");

            if (consultationMandatForm.getCodEtatRecherche().equalsIgnoreCase(Constants.COD_SANS_ETAT)) {
                consultationMandatForm.setCodEtatAttente(null);
                consultationMandatForm.setCodEtatRecherche(null);

            } else if (consultationMandatForm.getCodEtatRecherche().equalsIgnoreCase(Constants.COD_ETAT_MAND_ATT_PRE_MOD)) {
                consultationMandatForm.setCodEtatAttente(Constants.COD_ETAT_MAND_ATT_PRE_MOD);
                consultationMandatForm.setCodEtatRecherche(Constants.COD_SYCL_MOD);

            } else if (consultationMandatForm.getCodEtatRecherche().equalsIgnoreCase(Constants.COD_ETAT_MAND_ATT_VAL_MOD)) {
                consultationMandatForm.setCodEtatAttente(Constants.COD_ETAT_MAND_ATT_VAL_MOD);
                consultationMandatForm.setCodEtatRecherche(Constants.COD_SYCL_MOD);

            } else if (consultationMandatForm.getCodEtatRecherche().equalsIgnoreCase(Constants.COD_ETAT_MAND_ATT_PRE_ANN)) {
                consultationMandatForm.setCodEtatAttente(Constants.COD_ETAT_MAND_ATT_PRE_ANN);
                consultationMandatForm.setCodEtatRecherche(Constants.COD_ETAT_MAND_VALID);

            } else if (consultationMandatForm.getCodEtatRecherche().equalsIgnoreCase(Constants.COD_ETAT_MAND_ATT_VAL_ANN)) {
                consultationMandatForm.setCodEtatAttente(Constants.COD_ETAT_MAND_ATT_VAL_ANN);
                consultationMandatForm.setCodEtatRecherche(Constants.COD_ETAT_MAND_VALID);

            } else if (consultationMandatForm.getCodEtatRecherche().equalsIgnoreCase(Constants.COD_ETAT_MAND_ATT_PRE_REN)) {
                consultationMandatForm.setCodEtatAttente(Constants.COD_ETAT_MAND_ATT_PRE_REN);
                consultationMandatForm.setCodEtatRecherche(Constants.COD_ETAT_MAND_VALID);

            } else if (consultationMandatForm.getCodEtatRecherche().equalsIgnoreCase(Constants.COD_ETAT_MAND_ATT_VAL_REN)) {
                consultationMandatForm.setCodEtatAttente(Constants.COD_ETAT_MAND_ATT_VAL_REN);
                consultationMandatForm.setCodEtatRecherche(Constants.COD_ETAT_MAND_VALID);

            } else if (consultationMandatForm.getCodEtatRecherche().equalsIgnoreCase(Constants.COD_ETAT_MAND_VAL_RES)) {
                consultationMandatForm.setCodEtatAttente(Constants.COD_ETAT_MAND_VAL_RES);
                consultationMandatForm.setCodEtatRecherche(Constants.COD_ETAT_MAND_VALID);

            } else if (consultationMandatForm.getCodEtatRecherche().equalsIgnoreCase(Constants.COD_ETAT_MAND_REJ_MOD)) {
                consultationMandatForm.setCodEtatAttente(Constants.COD_ETAT_MAND_REJ_MOD);
                consultationMandatForm.setCodEtatRecherche(Constants.COD_ETAT_MAND_HIST);
            }
            mandatRecherche.setCodEtat(consultationMandatForm.getCodEtatRecherche());
            mandatRecherche.setCodEtatAttente(consultationMandatForm.getCodEtatAttente());
            mandatRecherche.setCodMenu(consultationMandatForm.getCodmenu());
            mandatRecherche.setDateDeb(DateHandler.strToDate(consultationMandatForm.getDateDebutconsult()));
            mandatRecherche.setDateFin(DateHandler.strToDate(consultationMandatForm.getDateFinconsult()));
            mandatRecherche.setCodStrcConcer(paramAgence.getCodStrcStrc());
            GetMandatAvaliderCmd getMandatAvaliderCmd = 
                new GetMandatAvaliderCmd();

            liste = (Listes)getMandatAvaliderCmd.execute(mandatRecherche);
            if ((liste != null) && (liste.getList().size() > 0)) {
                consultationMandatForm.setListeMandatAvalider(liste.getList());
                consultationMandatForm.setListeMandat(liste.getList());
                consultationMandatForm.setListeMandataire(liste.getList());
            } else {
                consultationMandatForm.setAlert("AucunMandat");
            }

            return mapping.findForward("rechParBanque");
        } catch (Exception e) {
            com.oxia.fwk.core.Error erreur = new com.oxia.fwk.core.Error();
            StringBuffer text = 
                new StringBuffer("la transaction est Interrompu, une erreur dans ConsultationMandatAction / Dispatch Action :rechMandatBanque ");
            text.append(e.toString());
            erreur.setCode("200");
            erreur.setDescription(text.toString());
            logger.error("Exception : ", e);
            ActionMessage actionMessage = 
                new ActionMessage("exception.generique", 
                                  erreur.getDescription());
            actionMessages.add("Erreur ", actionMessage);
            this.saveMessages(request, actionMessages);
            return mapping.findForward("error");
        }

    }

    /**
     * Methode permettant d'extraire les mandats à imprimer
     * @author JERBI lamia
     */
    public

    ActionForward rechercherMandatAimprimer(ActionMapping mapping, 
                                            ActionForm form, 
                                            HttpServletRequest request,

        HttpServletResponse response) throws IOException, ServletException {
        try {

            ConsultationMandatForm consultationMandatForm = 
                (ConsultationMandatForm)form;

            consultationMandatForm.setListeMandatAvalider(null);
            consultationMandatForm.setTypPcePers(null);
            consultationMandatForm.setNumPcePers(null);
            consultationMandatForm.setNomNomPers(null);
            consultationMandatForm.setNomPrnPers(null);
            consultationMandatForm.setMontSoldCcpt(null);
            consultationMandatForm.setLibDevDev(null);
            consultationMandatForm.setTypeValidation("");
            consultationMandatForm.setObservation("");
            consultationMandatForm.setMotifRejet("");
            consultationMandatForm.setMotifReserve("");
            consultationMandatForm.setPersMorale("false");
            consultationMandatForm.setListAssocies(null);

            MandatRecherche mandatRecherche = new MandatRecherche();
            ParamAgence paramAgence = new ParamAgence();
            paramAgence = 
                    (ParamAgence)request.getSession().getAttribute("paramAgBNA"); /// structure de l'agent qui fait la saisie
            ContratCptId contratCptId = new ContratCptId();
            ContratCpt contratCpt = new ContratCpt();
            Listes liste = new Listes();
            consultationMandatForm.setAlert("");
            consultationMandatForm.setCodEtatAttente(null);
            //changement des codes etat de recherche pour la consultation
            if ((consultationMandatForm.getCodeTraitement().equalsIgnoreCase("E"))) {
                if (consultationMandatForm.getCodEtatRecherche().equalsIgnoreCase(Constants.COD_ETAT_MAND_ATT_PRE_MOD)) {
                    consultationMandatForm.setCodEtatAttente(Constants.COD_ETAT_MAND_ATT_PRE_MOD);
                    consultationMandatForm.setCodEtatRecherche(Constants.COD_SYCL_MOD);
                }
                if (consultationMandatForm.getCodEtatRecherche().equalsIgnoreCase(Constants.COD_ETAT_MAND_ATT_VAL_MOD)) {
                    consultationMandatForm.setCodEtatAttente(Constants.COD_ETAT_MAND_ATT_VAL_MOD);
                    consultationMandatForm.setCodEtatRecherche(Constants.COD_SYCL_MOD);

                }
                if (consultationMandatForm.getCodEtatRecherche().equalsIgnoreCase(Constants.COD_ETAT_MAND_ATT_PRE_ANN)) {
                    consultationMandatForm.setCodEtatAttente(Constants.COD_ETAT_MAND_ATT_PRE_ANN);
                    consultationMandatForm.setCodEtatRecherche(Constants.COD_ETAT_MAND_VALID);

                }
                if (consultationMandatForm.getCodEtatRecherche().equalsIgnoreCase(Constants.COD_ETAT_MAND_ATT_VAL_ANN)) {
                    consultationMandatForm.setCodEtatAttente(Constants.COD_ETAT_MAND_ATT_VAL_ANN);
                    consultationMandatForm.setCodEtatRecherche(Constants.COD_ETAT_MAND_VALID);
                }
                if (consultationMandatForm.getCodEtatRecherche().equalsIgnoreCase(Constants.COD_ETAT_MAND_ATT_PRE_REN)) {
                    consultationMandatForm.setCodEtatAttente(Constants.COD_ETAT_MAND_ATT_PRE_REN);
                    consultationMandatForm.setCodEtatRecherche(Constants.COD_ETAT_MAND_VALID);

                }
                if (consultationMandatForm.getCodEtatRecherche().equalsIgnoreCase(Constants.COD_ETAT_MAND_ATT_VAL_REN)) {
                    consultationMandatForm.setCodEtatAttente(Constants.COD_ETAT_MAND_ATT_VAL_REN);
                    consultationMandatForm.setCodEtatRecherche(Constants.COD_ETAT_MAND_VALID);
                }
                if (consultationMandatForm.getCodEtatRecherche().equalsIgnoreCase(Constants.COD_ETAT_MAND_VAL_RES)) {
                    consultationMandatForm.setCodEtatAttente(Constants.COD_ETAT_MAND_VAL_RES);
                    consultationMandatForm.setCodEtatRecherche(Constants.COD_ETAT_MAND_VALID);
                }
                if (consultationMandatForm.getCodEtatRecherche().equalsIgnoreCase(Constants.COD_ETAT_MAND_REJ_MOD)) {
                    consultationMandatForm.setCodEtatAttente(Constants.COD_ETAT_MAND_REJ_MOD);
                    consultationMandatForm.setCodEtatRecherche(Constants.COD_ETAT_MAND_HIST);
                }

            }

            if (consultationMandatForm.getChoixEdit().equals("0")) {
                /*************************************commande de rechercher des mandat Par contrat*/

                if ((consultationMandatForm.getCodPrdPrd() != null) && 
                    (!consultationMandatForm.getCodPrdPrd().equalsIgnoreCase("")) && 
                    (consultationMandatForm.getCodStrcStrc() != null) && 
                    (!consultationMandatForm.getCodStrcStrc().equalsIgnoreCase("")) && 
                    (consultationMandatForm.getNumCcptCcpt() != null) && 
                    (!consultationMandatForm.getNumCcptCcpt().equalsIgnoreCase(""))) {
                    contratCptId.setCodPrdPrd(new Long(consultationMandatForm.getCodPrdPrd()));
                    contratCptId.setCodStrcStrc(new Long(consultationMandatForm.getCodStrcStrc()));
                    contratCptId.setNumCcptCcpt(new Long(consultationMandatForm.getNumCcptCcpt()));
                    mandatRecherche.setContratCptId(contratCptId);
                    mandatRecherche.setCodEtat(consultationMandatForm.getCodEtatRecherche());

                    mandatRecherche.setCodEtatAttente(consultationMandatForm.getCodEtatAttente());

                    getStructureConcerneeRecherche(consultationMandatForm, 
                                                   mandatRecherche, 
                                                   paramAgence);
                    mandatRecherche.setCodMenu(consultationMandatForm.getCodmenu());

                    GetContratMandatCmd getContratMandatCmd = 
                        new GetContratMandatCmd();
                    ContratCptMandat contratCptMandat = new ContratCptMandat();
                    contratCptMandat = 
                            (ContratCptMandat)getContratMandatCmd.execute(mandatRecherche);
                    /*****************Remplissage des informations du compte */
                    if (contratCptMandat.getContratCpt() != null) {
                        contratCpt = contratCptMandat.getContratCpt();
                        if ((contratCptMandat.getListeMandat() != null) && 
                            (contratCptMandat.getListeMandat().size() > 0)) {
                            consultationMandatForm.setListeMandat(contratCptMandat.getListeMandat());
                            consultationMandatForm.setListeMandataire(contratCptMandat.getListeMandataire());
                            consultationMandatForm.setListeMandatAvalider(contratCptMandat.getListeMandataire());
                            int listesize = 
                                consultationMandatForm.getListeMandataire().size();
                            for (int i = 0; i < listesize; i++)
                                consultationMandatForm.getIndexMandatChoisis().add("");
                        } else {
                            consultationMandatForm.setAlert("AucunMandat");
                        }
                        consultationMandatForm.setTypPcePers(contratCpt.getClient().getPersonne().getTypePiece().toString());
                        consultationMandatForm.setNumPcePers(contratCpt.getClient().getPersonne().getNumPcePers().toString());
                        if (contratCpt.getClient().getTypePers().getCodTperTper().equals(Constants.PERSMORALE)) {
                            consultationMandatForm.setPersMorale("true");
                            String rue = "";
                            String cite = "";
                            String imm = "";
                            String codp = "";
                            consultationMandatForm.setNomNomPers(contratCpt.getClient().getPersonne().getNomRsPers());
                            consultationMandatForm.setNomPrnPers(contratCpt.getClient().getPersonne().getLibSiglPers());
                            if (contratCpt.getClient().getPersonne().getMontCapPers() != 
                                null) {
                                consultationMandatForm.setCapitalSoc(StrHandler.formatmnt(Math.abs(contratCpt.getClient().getPersonne().getMontCapPers())));
                            } else {
                                consultationMandatForm.setCapitalSoc("0");
                            }
                            consultationMandatForm.setFormeJur(contratCpt.getClient().getPersonne().getFormeJuridique().getLibFjFj());
                            if (contratCpt.getAdresseCorresp().getImmeuble() != 
                                null) {
                                imm = 
contratCpt.getAdresseCorresp().getImmeuble();
                            }
                            if (contratCpt.getAdresseCorresp().getRue() != 
                                null) {
                                rue = contratCpt.getAdresseCorresp().getRue();
                            }
                            if (contratCpt.getAdresseCorresp().getCite() != 
                                null) {
                                cite = 
contratCpt.getAdresseCorresp().getCite();
                            }
                            if (contratCpt.getAdresseCorresp().getCodCpCp() != 
                                null) {
                                codp = 
contratCpt.getAdresseCorresp().getCodCpCp();
                            }
                            consultationMandatForm.setSiegeSoc(imm + " " + 
                                                               rue + " " + 
                                                               cite + " " + 
                                                               codp);
                            ParamListPersonneQualiteClientVo paramVo = 
                                new ParamListPersonneQualiteClientVo();
                            paramVo.setNumSeqPers(contratCpt.getClient().getPersonne().getNumSeqPers());
                            paramVo.setCodQualQual(Long.valueOf(17));
                            GetPersonneClientQualiteCmd getPersonneClientQualiteCmd = 
                                new GetPersonneClientQualiteCmd();
                            paramVo = 
                                    (ParamListPersonneQualiteClientVo)getPersonneClientQualiteCmd.execute(paramVo);
                            if (!paramVo.hasError()) {
                                if (paramVo.getListePersonneClient() != null && 
                                    (paramVo.getListePersonneClient().size() > 
                                     0)) {
                                    List listeDesPersonnes = new ArrayList();


                                    for (Iterator it = 
                                         paramVo.getListePersonneClient().iterator(); 
                                         it.hasNext(); ) {
                                        PersClient persClient = 
                                            (PersClient)it.next();
                                        PersonneClientView personneClientView = 
                                            new PersonneClientView();
                                        personneClientView.setNumSeqPers(persClient.getPersonne().getNumSeqPers());
                                        personneClientView.setCodTpceTpce(persClient.getPersonne().getTypePiece().getCodTpceTpce());
                                        personneClientView.setNumPcePers(persClient.getPersonne().getNumPcePers());
                                        personneClientView.setNomNomPers(persClient.getPersonne().getNomNomPers());
                                        personneClientView.setNomPrnPers(persClient.getPersonne().getNomPrnPers());
                                        if (persClient.getTauxPartPecl() != 
                                            null) {
                                            personneClientView.setTauxPartPect(persClient.getTauxPartPecl());
                                        }

                                        listeDesPersonnes.add(personneClientView);
                                    }

                                    consultationMandatForm.setListAssocies(listeDesPersonnes);
                                }
                            }
                        } else { // cas d'une personne physique
                            consultationMandatForm.setNomNomPers(contratCpt.getClient().getPersonne().getNomNomPers().toString());
                            consultationMandatForm.setNomPrnPers(contratCpt.getClient().getPersonne().getNomPrnPers().toString());
                        }
                        consultationMandatForm.setDatOuvCcpt(contratCpt.getDatOuvCcpt().toString());
                        if (contratCpt.getDatCloCcpt() == null) {
                            consultationMandatForm.setDatCloCcpt("");
                        } else {
                            consultationMandatForm.setDatCloCcpt(contratCpt.getDatCloCcpt().toString());
                        }
                        consultationMandatForm.setCodEtatCcpt(contratCpt.getCodEtatCcpt().toString());
                        if (contratCpt.getMontSoldCcpt() != null) {
                            consultationMandatForm.setMontSoldCcpt(contratCpt.getMontSoldCcpt().toString());
                        } else
                            consultationMandatForm.setMontSoldCcpt("0");
                        consultationMandatForm.setLibDevDev(contratCpt.getDevise().getLibDevDev());

                    }
                    return mapping.findForward("success");
                }
            } else if (consultationMandatForm.getChoixEdit().equals("2")) {
                /*************************************commande de rechercher des mandat par periode */

                if (consultationMandatForm.getCodStrConcer().equals(Constants.COD_STRC_DAJ)) { // cas DAJ
                    mandatRecherche.setCodStrcConcer(new Long(consultationMandatForm.getCodStrcAgence()));
                } else { // cas agence ou DReg
                    if (consultationMandatForm.getCodStrcStrc() != null && 
                        !consultationMandatForm.getCodStrcStrc().equals("")) {
                        contratCptId.setCodStrcStrc(new Long(consultationMandatForm.getCodStrcStrc()));
                        mandatRecherche.setContratCptId(contratCptId);
                        getStructureConcerneeRecherche(consultationMandatForm, 
                                                       mandatRecherche, 
                                                       paramAgence);
                    }
                }

                mandatRecherche.setCodEtat(consultationMandatForm.getCodEtatRecherche());
                mandatRecherche.setCodEtatAttente(consultationMandatForm.getCodEtatAttente());
                mandatRecherche.setCodMenu(consultationMandatForm.getCodmenu());
                //mandatRecherche.setCodStrcConcer(new Long(paramAgence.getCodStrcStrc()));
                mandatRecherche.setDateDeb(DateHandler.strToDate(consultationMandatForm.getDateDebutconsult()));
                mandatRecherche.setDateFin(DateHandler.addJour(DateHandler.strToDate(consultationMandatForm.getDateFinconsult()), 
                                                               1));
                GetMandatAvaliderCmd getMandatAvaliderCmd = 
                    new GetMandatAvaliderCmd();

                liste = (Listes)getMandatAvaliderCmd.execute(mandatRecherche);
                if ((liste != null) && (liste.getList().size() > 0)) {
                    consultationMandatForm.setListeMandatAvalider(liste.getList());
                    consultationMandatForm.setListeMandat(liste.getList());
                    consultationMandatForm.setListeMandataire(liste.getList());
                    if (consultationMandatForm.getListeMandataire() != null) {
                        int listesize1 = 
                            consultationMandatForm.getListeMandataire().size();
                        for (int i = 0; i < listesize1; i++)
                            consultationMandatForm.getIndexMandatChoisis().add("");
                    }
                    if (consultationMandatForm.getListeMandatAvalider() != 
                        null && 
                        consultationMandatForm.getListeMandatAvalider().size() > 
                        0) {
                        MandatPersonneMandat mandatPersonneMandat = 
                            (MandatPersonneMandat)consultationMandatForm.getListeMandatAvalider().get(0);
                        consultationMandatForm.setLibStrcStrc(mandatPersonneMandat.getMandat().getContratCpt().getStructure().getLibStrcStrc());
                    }
                } else {
                    consultationMandatForm.setAlert("AucunMandat");
                }


            } else if (consultationMandatForm.getChoixEdit().equals("4")) {
                contratCptId.setCodPrdPrd(null);
                contratCptId.setCodStrcStrc(new Long(consultationMandatForm.getCodStrcAgence()));
                contratCptId.setNumCcptCcpt(null);
                mandatRecherche.setContratCptId(contratCptId);
                mandatRecherche.setCodEtat(consultationMandatForm.getCodEtatRecherche());
                mandatRecherche.setCodEtatAttente(consultationMandatForm.getCodEtatAttente());
                mandatRecherche.setCodMenu(consultationMandatForm.getCodmenu());
                getStructureConcerneeRecherche(consultationMandatForm, 
                                               mandatRecherche, paramAgence);
                GetMandatAvaliderCmd getMandatAvaliderCmd = 
                    new GetMandatAvaliderCmd();

                liste = (Listes)getMandatAvaliderCmd.execute(mandatRecherche);
                if ((liste != null) && (liste.getList().size() > 0)) {
                    consultationMandatForm.setListeMandatAvalider(liste.getList());
                    consultationMandatForm.setListeMandat(liste.getList());
                    consultationMandatForm.setListeMandataire(liste.getList());
                    if (consultationMandatForm.getListeMandataire() != null) {
                        int listesize1 = 
                            consultationMandatForm.getListeMandataire().size();
                        for (int i = 0; i < listesize1; i++)
                            consultationMandatForm.getIndexMandatChoisis().add("");
                    }
                    if (consultationMandatForm.getListeMandatAvalider() != 
                        null && 
                        consultationMandatForm.getListeMandatAvalider().size() > 
                        0) {
                        MandatPersonneMandat mandatPersonneMandat = 
                            (MandatPersonneMandat)consultationMandatForm.getListeMandatAvalider().get(0);
                        consultationMandatForm.setLibStrcStrc(mandatPersonneMandat.getMandat().getContratCpt().getStructure().getLibStrcStrc());
                    }
                } else {
                    consultationMandatForm.setAlert("AucunMandat");
                }

            }
            /*************************************Lecture des erreur dans le VO de retour*/
            return mapping.findForward("success");
        } catch (Exception e) {
            com.oxia.fwk.core.Error erreur = new com.oxia.fwk.core.Error();
            StringBuffer text = 
                new StringBuffer("la transaction est Interrompu, une erreur dans ConsultationMandatAction / Dispatch Action :rechercheMandatAimprimer ");
            text.append(e.toString());
            erreur.setCode("200");
            erreur.setDescription(text.toString());
            logger.error("Exception : ", e);
            ActionMessage actionMessage = 
                new ActionMessage("exception.generique", 
                                  erreur.getDescription());
            actionMessages.add("Erreur ", actionMessage);
            this.saveMessages(request, actionMessages);
            return mapping.findForward("error");
        }

    }


    public ActionForward clearPage(ActionMapping mapping, ActionForm form, 
                                   HttpServletRequest request, 
                                   HttpServletResponse response) throws IOException, 
                                                                        ServletException {

        ConsultationMandatForm consultationMandatForm = 
            (ConsultationMandatForm)form;

        consultationMandatForm.clearForm();
        return mapping.findForward("success");

    }

    public ActionForward renouvlerMandat(ActionMapping mapping, 
                                         ActionForm form, 
                                         HttpServletRequest request, 
                                         HttpServletResponse response) throws IOException, 
                                                                              ServletException {
        ActionMessages actionMessages = new ActionMessages();
        try {

            ParamAgence paramAgence = new ParamAgence();
            paramAgence = 
                    (ParamAgence)request.getSession().getAttribute("paramAgBNA"); /// structure de l'agent qui fait la saisie
            ConsultationMandatForm consultationMandatForm = 
                (ConsultationMandatForm)form;
            Mandat mandatRetour = new Mandat();
            String message = "";
            SimpleDateFormat formater = new SimpleDateFormat("dd/MM/yyyy");
            String heureString = 
                formater.format(consultationMandatForm.getInitialisationView().getDateOp());
            codeOperation = Constants.COD_OPER_RENOUV_MAND;

            for (Iterator it = 
                 consultationMandatForm.getListeMandatAvalider().iterator(); 
                 it.hasNext(); ) {
                MandatPersonneMandat mandatPersonneMandat = 
                    (MandatPersonneMandat)it.next();
                Mandat mandat1 = mandatPersonneMandat.getMandat();

                if (mandat1.getNumMandMand().longValue() == 
                    new Long(consultationMandatForm.getMandatchoisi()).longValue()) {
                    //GetMandatCmd getMandatCmd=new GetMandatCmd();
                    //Mandat mandatArenouvler=(Mandat)getMandatCmd.execute(mandat1);
                    if (consultationMandatForm.getCodeTraitement().equalsIgnoreCase("SR")) {
                        mandat1.setDatEnvrMand(new Date());
                        mandat1.setDatADebMand(mandat1.getDatDebMand());
                        mandat1.setDatDebMand(DateHandler.strToDate(consultationMandatForm.getDatDebMand()));
                        mandat1.setCodEdemMand(Constants.COD_ETAT_MAND_ATT_PRE_REN);
                        mandat1.setDatFiniMand(mandat1.getDatFinMand());
                        mandat1.setDatFinMand(DateHandler.strToDate(consultationMandatForm.getDatFinMand()));
                        getStructureConcernee(consultationMandatForm, mandat1, 
                                              paramAgence);
                        codTach = Constants.COD_TACH_SAISIE_RENOUV_MAND;
                        message = 
                                "Vous avez saisie le renouvellement du mandat N° ";

                    }
                    if (consultationMandatForm.getCodeTraitement().equalsIgnoreCase("PR")) {
                        mandat1.setNumRdjMand(consultationMandatForm.getNumRdjMand());
                        mandat1.setCodEdemMand(Constants.COD_ETAT_MAND_ATT_VAL_REN);
                        mandat1.setLibObsMand(consultationMandatForm.getObservation());
                        mandat1.setDatFinMand(DateHandler.strToDate(consultationMandatForm.getDatFinMand()));
                        mandat1.setDatDebMand(DateHandler.strToDate(consultationMandatForm.getDatDebMand()));
                        message = 
                                "Vous avez prévalidé le renouvellement du mandat N° ";
                        if (consultationMandatForm.getTypeValidation().equalsIgnoreCase("VR")) { /// prévalidation renouvellement avec reserve
                            mandat1.setLibMrsvMand(consultationMandatForm.getMotifReserve());
                            message = 
                                    "Vous avez prévalidé avec réserve le renouvellement du mandat N° ";
                        } else if (consultationMandatForm.getTypeValidation().equalsIgnoreCase("R")) { /// rejet de renouvellement
                            mandat1.setLibMrejMand(consultationMandatForm.getMotifRejet());
                            message = 
                                    "Vous avez rejeté le renouvellement du mandat N° ";

                        }
                        //getStructureConcernee(consultationMandatForm,mandat1,paramAgence);
                        codTach = Constants.COD_TACH_PREV_RENOUV_MAND;

                    }
                    if (consultationMandatForm.getCodeTraitement().equalsIgnoreCase("VR")) {
                        mandat1.setNumRdjMand(consultationMandatForm.getNumRdjMand());
                        mandat1.setCodEdemMand(null);
                        mandat1.setCodEtatMand("V");
                        mandat1.setDatVldrMand(new Date());
                        mandat1.setCodStrcMand(mandat1.getContratCpt().getContratCptId().getCodStrcStrc()); //-pour que l'agence pourra operer sur lemandat aprés le DAJ ou DR
                        mandat1.setLibObsMand(consultationMandatForm.getObservation());
                        mandat1.setDatFinMand(DateHandler.strToDate(consultationMandatForm.getDatFinMand()));
                        mandat1.setDatDebMand(DateHandler.strToDate(consultationMandatForm.getDatDebMand()));
                        message = 
                                "Vous avez validé le renouvellement du mandat N° ";
                        if (consultationMandatForm.getTypeValidation().equalsIgnoreCase("R")) { //rejet de validation
                            mandat1.setDatFinMand(mandat1.getDatFiniMand());
                            mandat1.setDatDebMand(mandat1.getDatADebMand());
                            mandat1.setDatADebMand(null);
                            mandat1.setLibMrejMand(consultationMandatForm.getMotifRejet());
                            message = 
                                    "Vous avez rejeté le renouvellement du mandat N°";
                        } else if (consultationMandatForm.getTypeValidation().equalsIgnoreCase("VR")) { //validation avec reserve
                            mandat1.setLibMrsvMand(consultationMandatForm.getMotifReserve());
                            mandat1.setCodEdemMand(Constants.COD_ETAT_MAND_VAL_RES);
                            message = 
                                    "Vous avez validé avec réserve le renouvellement du mandat N°";
                        }
                        codTach = Constants.COD_TACH_VAL_RENOUV_MAND;

                    }


                    //*préparation de la trace
                    TraceMandat traceMandat = new TraceMandat();
                    traceMandat.setMandat(mandat1);
                    Personnel personnel = new Personnel();
                    Structure structure = new Structure();
                    structure.setCodStrcStrc(paramAgence.getCodStrcStrc());
                    personnel.setStructure(structure);
                    personnel.setNumMatrUser(paramAgence.getNumMatrUser());
                    traceMandat.setPersonnel(personnel);
                    Tache tache = new Tache();
                    TacheId tacheId = new TacheId();
                    tacheId.setCodOperOper(codeOperation);
                    tacheId.setCodTachTach(codTach);
                    tache.setTacheId(tacheId);
                    traceMandat.setTache(tache);
                    UpdateMandatTraceCmd updateMandatTraceCmd = 
                        new UpdateMandatTraceCmd();
                    ValueObject vo = 
                        (ValueObject)updateMandatTraceCmd.execute(traceMandat);

                    if (vo == null || vo.hasError()) {
                        List listErreur = vo.getErrors();
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
                    } else {
                        mandatRetour = (Mandat)vo;
                        if (mandatRetour != null) {

                            message = 
                                    message + mandatRetour.getNumMandMand() + " sur le contrat " + 
                                    mandatRetour.getContratCpt().getContratCptId().getCodStrcStrc() + 
                                    mandatRetour.getContratCpt().getContratCptId().getCodPrdPrd() + 
                                    mandatRetour.getContratCpt().getContratCptId().getNumCcptCcpt() + 
                                    " en date du " + heureString;
                            if (consultationMandatForm.getCodeTraitement().equalsIgnoreCase("VR") || 
                                consultationMandatForm.getCodeTraitement().equalsIgnoreCase("PR")) {
                                if ((consultationMandatForm.getPersMorale().equalsIgnoreCase("true"))&&(mandatRetour.getNumRdjMand()!=null)){
                                    message = message+" dont le numéro juridique est "+mandatRetour.getNumRdjMand().toString();
                                   
                                
                                }
                                
                                imprimerMandat(mandatRetour.getNumMandMand(), 
                                               request, 
                                mandatRetour.getContratCpt().getContratCptId().getCodStrcStrc().toString(),
                                               consultationMandatForm.getCodeTraitement(), 
                                               consultationMandatForm.getTypeValidation());
                            }

                        }
                    }
                }
            }

            if (mandatRetour != null) {
                consultationMandatForm.setLibelleConfirmation(message);
            }

            return mapping.findForward("confirmation");


        } catch (Exception e) {
            com.oxia.fwk.core.Error erreur = new com.oxia.fwk.core.Error();
            StringBuffer text = 
                new StringBuffer("la transaction est Interrompu, une erreur dans ConsultationMandatAction / Dispatch Action :renouvlerMandat ");
            text.append(e.toString());
            erreur.setCode("200");
            erreur.setDescription(text.toString());
            logger.error("Exception : ", e);
            ActionMessage actionMessage = 
                new ActionMessage("exception.generique", 
                                  erreur.getDescription());
            actionMessages.add("Erreur ", actionMessage);
            this.saveMessages(request, actionMessages);
            return mapping.findForward("error");
        }

    }

    private void imprimerMandat(Long numMand, HttpServletRequest request, 
                                String struct, String codeTraitement, 
                                String typeValid) {
        try {
            CommonReportVO valueObject = new CommonReportVO();
            Map parameters = new HashMap();
            ParamAgence paramAgence = new ParamAgence();
            paramAgence = 
                    (ParamAgence)request.getSession().getAttribute("paramAgBNA");
            parameters.put("P_NUM_MATR_USER", 
                           paramAgence.getNumMatrUser().toString());
            // libellé opération, prévalidation création, renouvel...
            if (codeTraitement.equalsIgnoreCase("PR")) {
                if (typeValid.equalsIgnoreCase("V")) {
                    /// validation
                    parameters.put("P_LIB_ETAT", 
                                   "Prévalidation Renouvellement Mandat");
                }
                if (typeValid.equalsIgnoreCase("VR")) { /// validation avec reserve
                    parameters.put("P_LIB_ETAT", 
                                   "Prévalidation Renouvellement Mandat avec Réserve");
                }
                if (typeValid.equalsIgnoreCase("R")) { /// rejet
                    parameters.put("P_LIB_ETAT", 
                                   "Rejet Prévalidation Renouvellement Mandat");
                }
            } else if (codeTraitement.equalsIgnoreCase("VR")) {
                if (typeValid.equalsIgnoreCase("V")) {
                    /// validation
                    parameters.put("P_LIB_ETAT", 
                                   "Validation Renouvellement Mandat");
                }
                if (typeValid.equalsIgnoreCase("VR")) { /// validation avec reserve
                    parameters.put("P_LIB_ETAT", 
                                   "Validation Renouvellement Mandat avec Réserve");
                }
                if (typeValid.equalsIgnoreCase("R")) { /// rejet
                    parameters.put("P_LIB_ETAT", 
                                   "Rejet Renouvellement Mandat");
                }
            }
            parameters.put("P_COD_STRC_STRC", struct);
            if (codeTraitement.equalsIgnoreCase("VR") ) {
                parameters.put("STRC_VALID_MAND", struct);
            } else {
                parameters.put("STRC_VALID_MAND", 
                               paramAgence.getCodStrcStrc().toString());
            }

            /*   if(!typValid.equalsIgnoreCase("R")){
                parameters.put("P_NUM_DEM_MAND",numeroDossierMandat);
                valueObject.setNomReport("listeMandatNumMand");
                }else{*/
            parameters.put("P_NUM_MAND_MAND", numMand.toString());
            valueObject.setNomReport("mandatNumMand");

            valueObject.setParams(parameters);

            request.getSession().setAttribute("CommonPrintVo", valueObject);
            request.setAttribute("print", "1");
        } catch (Exception e) {
            logger.error(e.getMessage());
            throw new RuntimeException(e);
        }
    }

    public ActionForward annulRenouvMandat(ActionMapping mapping, 
                                           ActionForm form, 
                                           HttpServletRequest request, 
                                           HttpServletResponse response) throws IOException, 
                                                                                ServletException {
        ActionMessages actionMessages = new ActionMessages();
        try {

            ParamAgence paramAgence = new ParamAgence();
            paramAgence = 
                    (ParamAgence)request.getSession().getAttribute("paramAgBNA"); /// structure de l'agent qui fait la saisie
            ConsultationMandatForm consultationMandatForm = 
                (ConsultationMandatForm)form;
            Mandat mandatRetour = new Mandat();
            String message = "";
            SimpleDateFormat formater = new SimpleDateFormat("dd/MM/yyyy");
            String heureString = 
                formater.format(consultationMandatForm.getInitialisationView().getDateOp());
            codeOperation = Constants.COD_OPER_RENOUV_MAND;

            for (Iterator it = 
                 consultationMandatForm.getListeMandatAvalider().iterator(); 
                 it.hasNext(); ) {
                MandatPersonneMandat mandatPersonneMandat = 
                    (MandatPersonneMandat)it.next();
                Mandat mandat1 = mandatPersonneMandat.getMandat();

                if (mandat1.getNumMandMand().longValue() == 
                    new Long(consultationMandatForm.getMandatchoisi()).longValue()) {
                    //GetMandatCmd getMandatCmd=new GetMandatCmd();
                    //Mandat mandatArenouvler=(Mandat)getMandatCmd.execute(mandat1);
                    mandat1.setCodEdemMand(null);
                    mandat1.setDatEnvrMand(null);
                    mandat1.setDatFinMand(mandat1.getDatFiniMand());
                    mandat1.setDatFiniMand(null);
                    message = 
                            "Vous avez annulé le renouvellement du mandat N° ";
                    //*prparation de la trace
                    codTach = Constants.COD_TACH_ANL_RENOUV_MAND;
                    TraceMandat traceMandat = new TraceMandat();
                    traceMandat.setMandat(mandat1);
                    Personnel personnel = new Personnel();
                    Structure structure = new Structure();
                    structure.setCodStrcStrc(paramAgence.getCodStrcStrc());
                    personnel.setStructure(structure);
                    personnel.setNumMatrUser(paramAgence.getNumMatrUser());
                    traceMandat.setPersonnel(personnel);
                    Tache tache = new Tache();
                    TacheId tacheId = new TacheId();
                    tacheId.setCodOperOper(codeOperation);
                    tacheId.setCodTachTach(codTach);
                    tache.setTacheId(tacheId);
                    traceMandat.setTache(tache);
                    UpdateMandatTraceCmd updateMandatTraceCmd = 
                        new UpdateMandatTraceCmd();
                    ValueObject vo = 
                        (ValueObject)updateMandatTraceCmd.execute(traceMandat);
                    if (vo == null || vo.hasError()) {
                        List listErreur = vo.getErrors();
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
                    } else {
                        mandatRetour = (Mandat)vo;
                        message = 
                                message + mandatRetour.getNumMandMand() + " sur le contrat " + 
                                mandatRetour.getContratCpt().getContratCptId().getCodStrcStrc() + 
                                mandatRetour.getContratCpt().getContratCptId().getCodPrdPrd() + 
                                mandatRetour.getContratCpt().getContratCptId().getNumCcptCcpt() + 
                                " en date du " + heureString;
                    }
                }
            }

            if (mandatRetour != null) {
                consultationMandatForm.setLibelleConfirmation(message);
            }
            return mapping.findForward("confirmation");


        } catch (Exception e) {
            com.oxia.fwk.core.Error erreur = new com.oxia.fwk.core.Error();
            StringBuffer text = 
                new StringBuffer("la transaction est Interrompu, une erreur dans ConsultationMandatAction / Dispatch Action :AnnulRenouvMandat ");
            text.append(e.toString());
            erreur.setCode("200");
            erreur.setDescription(text.toString());
            logger.error("Exception : ", e);
            ActionMessage actionMessage = 
                new ActionMessage("exception.generique", 
                                  erreur.getDescription());
            actionMessages.add("Erreur ", actionMessage);
            this.saveMessages(request, actionMessages);
            return mapping.findForward("error");
        }

    }

    /**
     * Methode permettant d'imprimer les mandats
     * @author JERBI lamia
     */
    public ActionForward imprimerMandatSelonChoix(ActionMapping mapping, 
                                                  ActionForm form, 
                                                  HttpServletRequest request, 
                                                  HttpServletResponse response) throws IOException, 
                                                                                       ServletException {
        ConsultationMandatForm consultationMandatForm = 
            (ConsultationMandatForm)form;
        ActionMessages actionMessages = new ActionMessages();
        try {
            CommonReportVO valueObject = new CommonReportVO();
            ParamAgence paramAgence = new ParamAgence();
            paramAgence = 
                    (ParamAgence)request.getSession().getAttribute("paramAgBNA");
            Map parameters = new HashMap();

            /*---------------------------------------------------------------------*/
            String pCodProd = "P_COD_PRD_PRD";
            String pNumContratCpt = "P_NUM_CCPT_CCPT";
            String vCodProd = "";
            String vNumContratCpt = "";
            String pCodStrcStrc = "P_COD_STRC_STRC";
            String vCodStrcStrc = paramAgence.getCodStrcStrc().toString();

            /*------------------------------------------------------------------
                  String pCodTpceTpce = "P_COD_TPCE_TPCE";
                  String pNumTpceTpce = "P_NUM_PCE_PERS";
                 String vCodTpceTpce = "";
                 String vNumTpceTpce = "";
                /*------------------------------------------------------------------*/
            String pDateDeb = "P_DATE_DEB";
            String pDateFin = "P_DATE_FIN";
            String vDateFin = "";
            String vDateDeb = "";
            /*-----------------------------------------------------------------*/
            String pNumDemMand = "P_NUM_DEM_MAND";
            String vNumDemMand = "";

            String pEtat = "P_VALIDE";
            String pMatrUser = "P_NUM_MATR_USER";
            String vMatrUser = paramAgence.getNumMatrUser().toString();
            String pLibEtat = "P_LIB_ETAT";
            StringBuffer vLibEtat = new StringBuffer("");
            String vEtat = "";
            String vEtatEdem = "";
            if (!consultationMandatForm.getChoixEdit().equals("3")) {
                if (consultationMandatForm.getCodEtatRecherche().equals(Constants.COD_ETAT_MAND_ATT_PRE.toString())) {
                    vLibEtat.append("Liste des Mandats (En attente de prévalidation de création)");
                    vEtat = Constants.COD_ETAT_MAND_ATT_PRE.toString();
                    vEtatEdem = "";
                } else if (consultationMandatForm.getCodEtatRecherche().equals(Constants.COD_ETAT_MAND_ATT.toString())) {
                    vLibEtat.append("Liste des Mandats (En attente de validation) ");
                    vEtat = Constants.COD_ETAT_MAND_ATT.toString();
                    vEtatEdem = "";
                } else if (consultationMandatForm.getCodEtatRecherche().equals(Constants.COD_ETAT_MAND_VALID.toString())) {
                    vEtat = Constants.COD_ETAT_CPT_VALID.toString();
                    if (consultationMandatForm.getCodEtatAttente() == null) {
                        vLibEtat.append("Liste des Mandats (Validées)");
                        vEtatEdem = "";
                    } else if (consultationMandatForm.getCodEtatAttente().equals(Constants.COD_ETAT_MAND_ATT_PRE_ANN.toString())) {
                        vLibEtat.append("Liste des Mandats (En attente de prévalidation d'annulation)");
                        vEtatEdem = 
                                Constants.COD_ETAT_MAND_ATT_PRE_ANN.toString();
                    } else if (consultationMandatForm.getCodEtatAttente().equals(Constants.COD_ETAT_MAND_ATT_VAL_ANN.toString())) {
                        vLibEtat.append("Liste des Mandats (En attente de validation d'annulation) ");
                        vEtatEdem = 
                                Constants.COD_ETAT_MAND_ATT_VAL_ANN.toString();
                    } else if (consultationMandatForm.getCodEtatAttente().equals(Constants.COD_ETAT_MAND_ATT_PRE_REN.toString())) {
                        vLibEtat.append("Liste des Mandats (En attente de prévalidation de renouvellement) ");
                        vEtatEdem = 
                                Constants.COD_ETAT_MAND_ATT_PRE_REN.toString();
                    } else if (consultationMandatForm.getCodEtatAttente().equals(Constants.COD_ETAT_MAND_ATT_VAL_REN.toString())) {
                        vLibEtat.append("Liste des Mandats (En attente de validation de renouvellement)");
                        vEtatEdem = 
                                Constants.COD_ETAT_MAND_ATT_VAL_REN.toString();
                    } else if (consultationMandatForm.getCodEtatAttente().equals(Constants.COD_ETAT_MAND_VAL_RES.toString())) {
                        vLibEtat.append("Liste des Mandats (Validés avec réserves)");
                        vEtatEdem = Constants.COD_ETAT_MAND_VAL_RES.toString();
                    }
                } else if (consultationMandatForm.getCodEtatRecherche().equals(Constants.COD_SYCL_MOD.toString())) {
                    vEtat = Constants.COD_SYCL_MOD.toString();
                    if (consultationMandatForm.getCodEtatAttente().equals(Constants.COD_ETAT_MAND_ATT_PRE_MOD.toString())) {
                        vLibEtat.append("Liste des Mandats (En attente de prévalidation de modification)");
                        vEtatEdem = 
                                Constants.COD_ETAT_MAND_ATT_PRE_MOD.toString();
                    } else if (consultationMandatForm.getCodEtatAttente().equals(Constants.COD_ETAT_MAND_ATT_VAL_MOD.toString())) {
                        vLibEtat.append("Liste des Mandats (En attente de validation de modification)");
                        vEtatEdem = 
                                Constants.COD_ETAT_MAND_ATT_VAL_MOD.toString();
                    }

                } else if (consultationMandatForm.getCodEtatRecherche().equals(Constants.COD_ETAT_MAND_R.toString())) {
                    vLibEtat.append("Liste des Mandats (Rejetées)");
                    vEtat = Constants.COD_ETAT_MAND_R.toString();
                    vEtatEdem = "";
                } else if (consultationMandatForm.getCodEtatRecherche().equals(Constants.COD_ETAT_MAND_HIST.toString())) {
                    vEtat = Constants.COD_ETAT_MAND_HIST.toString();
                    if (consultationMandatForm.getCodEtatAttente().equals(Constants.COD_ETAT_MAND_REJ_MOD.toString())) {
                        vLibEtat.append("Liste des Mandats (Rejetées suite à modification) ");
                        vEtatEdem = Constants.COD_ETAT_MAND_REJ_MOD.toString();
                    }
                }
                parameters.put("ETAT_EDEM", vEtatEdem);
                parameters.put(pEtat, vEtat);

            }
            MandatRecherche mandatRecherche = new MandatRecherche();
            if (consultationMandatForm.getCodStrConcer().equals(Constants.COD_STRC_DAJ)) { // cas DAJ
                mandatRecherche.setCodStrcConcer(new Long(consultationMandatForm.getCodStrcAgence()));
            } else { // cas agence ou DReg
                getStructureConcerneeRecherche(consultationMandatForm, 
                                               mandatRecherche, paramAgence);
            }

            parameters.put("STRC_VALID_MAND", 
                           mandatRecherche.getCodStrcConcer().toString());
            parameters.put(pMatrUser, vMatrUser);

            if (consultationMandatForm.getChoixEdit().equals("2")) {

                if (!consultationMandatForm.getDateDebutconsult().equals("") || 
                    !consultationMandatForm.getDateFinconsult().equals("")) {
                    // avec ou san détails
                    vDateFin = consultationMandatForm.getDateFinconsult();
                    vDateDeb = consultationMandatForm.getDateDebutconsult();
                    parameters.put(pDateDeb, vDateDeb);
                    parameters.put(pDateFin, vDateFin);

                    if (consultationMandatForm.getCodEtatRecherche().equals(Constants.COD_ETAT_MAND_ATT.toString()) || 
                        (consultationMandatForm.getCodEtatRecherche().equals(Constants.COD_ETAT_MAND_VALID.toString()) && 
                         consultationMandatForm.getCodEtatAttente() == null) || 
                        consultationMandatForm.getCodEtatRecherche().equals(Constants.COD_ETAT_MAND_ATT_PRE.toString()) || 
                        consultationMandatForm.getCodEtatRecherche().equals(Constants.COD_ETAT_MAND_R.toString())) {
                        if (paramAgence.getCodStrcStrc().equals(Constants.COD_STRC_DAJ)) {
                            if (!consultationMandatForm.getCodStrcAgence().equalsIgnoreCase("") && 
                                !consultationMandatForm.getCodStrcAgence().equalsIgnoreCase(Constants.COD_STRC_DAJ.toString())) {
                                valueObject.setNomReport("listeContratMandat");
                                vCodStrcStrc = 
                                        consultationMandatForm.getCodStrcAgence();
                            } else {
                                valueObject.setNomReport("listeContratMandat_opDAJ");
                            }
                        } else {
                            valueObject.setNomReport("listeContratMandat");
                        }
                    } else {
                        if (paramAgence.getCodStrcStrc().equals(Constants.COD_STRC_DAJ)) {
                            valueObject.setNomReport("listeContratMandat_opDAJEdem");
                        } else {
                            valueObject.setNomReport("listeContratMandatEdem");
                        }
                    }

                    /*   if(consultationMandatForm.getDateDebutconsult().equals(consultationMandatForm.getDateFinconsult()) ){
                                       valueObject.setNomReport("listingMandatETAT_v2"); // test sur date validation et code tache = 3 donc par rapport aux mandats validées
                                   }*/
                }
            } else if (consultationMandatForm.getChoixEdit().equals("1") || 
                       consultationMandatForm.getChoixEdit().equals("0")) {
                vCodProd = consultationMandatForm.getCodPrdPrd();
                vNumContratCpt = consultationMandatForm.getNumCcptCcpt();
                parameters.put(pCodProd, vCodProd);
                parameters.put(pNumContratCpt, vNumContratCpt);
                if (paramAgence.getCodStrcStrc().equals(Constants.COD_STRC_DAJ)) {
                    vCodStrcStrc = 
                            consultationMandatForm.getCodStrcStrc().toString();
                }
                if (consultationMandatForm.getCodEtatRecherche().equals(Constants.COD_ETAT_MAND_ATT.toString()) || 
                    (consultationMandatForm.getCodEtatRecherche().equals(Constants.COD_ETAT_MAND_VALID.toString()) && 
                     consultationMandatForm.getCodEtatAttente() == null) || 
                    consultationMandatForm.getCodEtatRecherche().equals(Constants.COD_ETAT_MAND_ATT_PRE.toString()) || 
                    consultationMandatForm.getCodEtatRecherche().equals(Constants.COD_ETAT_MAND_R.toString())) {
                    valueObject.setNomReport("listeMandatContrat");
                } else {
                    valueObject.setNomReport("listeMandatContratEdem");
                }

            } else if (consultationMandatForm.getChoixEdit().equals("3")) { // par numéro demande
                vNumDemMand = 
                        consultationMandatForm.getNumDemMand().toString();
                vLibEtat = new StringBuffer();
                vLibEtat.append("Données Mandat");
                parameters.put(pNumDemMand, vNumDemMand);
                valueObject.setNomReport("listeMandatNumMand");
            }

            if (paramAgence.getCodStrcStrc().equals(Constants.COD_STRC_DAJ)) {
                if (consultationMandatForm.getChoixEdit().equals("4")) { // par agence
                    parameters.put("P_COD_STRC_STRC", 
                                   consultationMandatForm.getCodStrcAgence());
                    if (consultationMandatForm.getCodEtatRecherche().equals(Constants.COD_ETAT_MAND_ATT.toString()) || 
                        (consultationMandatForm.getCodEtatRecherche().equals(Constants.COD_ETAT_MAND_VALID.toString()) && 
                         consultationMandatForm.getCodEtatAttente() == null) || 
                        consultationMandatForm.getCodEtatRecherche().equals(Constants.COD_ETAT_MAND_ATT_PRE.toString()) || 
                        consultationMandatForm.getCodEtatRecherche().equals(Constants.COD_ETAT_MAND_R.toString())) {
                        valueObject.setNomReport("listeMandatsAgence");
                    } else {
                        valueObject.setNomReport("listeMandatsAgenceEdem");
                    }
                }
            }

            parameters.put(pLibEtat, vLibEtat.toString());
            parameters.put(pCodStrcStrc, vCodStrcStrc);
            valueObject.setParams(parameters);
            request.getSession().setAttribute("CommonPrintVo", valueObject);
            request.setAttribute("print", "1");
            if (consultationMandatForm.getChoixEdit().equals("3")) {
                return mapping.findForward("parmandat");
            } else {
                return mapping.findForward("success");
            }
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

    /**
     * Methode permettant d'imprimer les opérations sur mandats (trace mandat)
     * @author JERBI lamia
     */
    public ActionForward imprimerOperationSurMandat(ActionMapping mapping, 
                                                    ActionForm form, 
                                                    HttpServletRequest request, 
                                                    HttpServletResponse response) throws IOException, 
                                                                                         ServletException {
        ConsultationMandatForm consultationMandatForm = 
            (ConsultationMandatForm)form;
        ActionMessages actionMessages = new ActionMessages();
        try {
            CommonReportVO valueObject = new CommonReportVO();
            ParamAgence paramAgence = new ParamAgence();
            paramAgence = 
                    (ParamAgence)request.getSession().getAttribute("paramAgBNA");
            Map parameters = new HashMap();

            String pCodStrcStrc = "P_COD_STRC_STRC";
            String vCodStrcStrc = paramAgence.getCodStrcStrc().toString();

            String pDateDeb = "P_DATE_DEB";
            String pDateFin = "P_DATE_FIN";
            String vDateFin = "";
            String vDateDeb = "";

            String pMatrUser = "P_NUM_MATR_USER";
            String vMatrUser = paramAgence.getNumMatrUser().toString();

            String pLibEtat = "P_LIB_ETAT";
            String vLibEtat = "";

            String pOperation = "OPERATION";
            String pTache = "TACHE";
            String vOperation = "";
            String vTache = "";

            if (consultationMandatForm.getCodOper().equals("C")) {
                vOperation = Constants.COD_OPER_CREAT_MANDAT.toString();
            } else if (consultationMandatForm.getCodOper().equals("M")) {
                vOperation = Constants.COD_OPER_MODIF_MANDAT.toString();
            } else if (consultationMandatForm.getCodOper().equals("A")) {
                vOperation = Constants.COD_OPER_ANNUL_MAND.toString();
            } else if (consultationMandatForm.getCodOper().equals("R")) {
                vOperation = Constants.COD_OPER_RENOUV_MAND.toString();
            }

            parameters.put(pOperation, vOperation);
            /*code tache*/
            if (consultationMandatForm.getCodtach().equalsIgnoreCase("S")) {
                vTache = Constants.COD_TACHE_SAISIE_MANDAT.toString();
            } else if (consultationMandatForm.getCodtach().equalsIgnoreCase("P")) {
                vTache = Constants.COD_TACHE_PREVALID_MANDAT.toString();
            } else if (consultationMandatForm.getCodtach().equalsIgnoreCase("V")) {
                vTache = Constants.COD_TACHE_VALID_MANDAT.toString();
            }

            parameters.put(pTache, vTache);

            vLibEtat = "Liste des opérations sur mandats";

            parameters.put(pLibEtat, vLibEtat);
            parameters.put(pCodStrcStrc, vCodStrcStrc);
            parameters.put(pMatrUser, vMatrUser);

            if (!consultationMandatForm.getDateDebutOper().equals("") || 
                !consultationMandatForm.getDateFinOper().equals("")) {
                vDateFin = consultationMandatForm.getDateFinOper();
                vDateDeb = consultationMandatForm.getDateDebutOper();
                parameters.put(pDateDeb, vDateDeb);
                parameters.put(pDateFin, vDateFin);
            }

            if (!consultationMandatForm.getCodOper().equals("T")) {
                valueObject.setNomReport("traceMandat");
            } else {
                valueObject.setNomReport("traceMandat_OPER");
            }
            valueObject.setParams(parameters);

            request.getSession().setAttribute("CommonPrintVo", valueObject);
            request.setAttribute("print", "1");
            return mapping.findForward("operationMandat");
        } catch (Exception e) {
            com.oxia.fwk.core.Error erreur = new com.oxia.fwk.core.Error();
            StringBuffer text = 
                new StringBuffer("la transaction est Interrompu, une erreur dans ConsultationMandatAction / Dispatch Action :imprimerOperationSurMandat ");
            text.append(e.toString());
            logger.error("Exception : ", e);
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

    /**
     * Methode permettant d'imprimer historique mandats (trace mandat)
     * @author JERBI lamia
     */
    public ActionForward imprimerHistoriqueMandat(ActionMapping mapping, 
                                                  ActionForm form, 
                                                  HttpServletRequest request, 
                                                  HttpServletResponse response) throws IOException, 
                                                                                       ServletException {
        ConsultationMandatForm consultationMandatForm = 
            (ConsultationMandatForm)form;
        ActionMessages actionMessages = new ActionMessages();
        try {
            CommonReportVO valueObject = new CommonReportVO();
            ParamAgence paramAgence = new ParamAgence();
            paramAgence = 
                    (ParamAgence)request.getSession().getAttribute("paramAgBNA");
            Map parameters = new HashMap();

            String pCodStrcStrc = "P_COD_STRC_STRC";
            String vCodStrcStrc = paramAgence.getCodStrcStrc().toString();
            /*---------------------------------------------------------------------*/
            String pCodProd = "P_COD_PRD_PRD";
            String pNumContratCpt = "P_NUM_CCPT_CCPT";
            String vCodProd = "";
            String vNumContratCpt = "";

            String pDateDeb = "P_DATE_DEB";
            String pDateFin = "P_DATE_FIN";
            String vDateFin = "";
            String vDateDeb = "";

            String pMatrUser = "P_NUM_MATR_USER";
            String vMatrUser = paramAgence.getNumMatrUser().toString();

            String pLibEtat = "P_LIB_ETAT";
            String vLibEtat = "";

            vLibEtat = "Historique Mandats";

            parameters.put(pLibEtat, vLibEtat);
            parameters.put(pCodStrcStrc, vCodStrcStrc);
            parameters.put(pMatrUser, vMatrUser);

            vCodProd = consultationMandatForm.getCodPrdPrd();
            vNumContratCpt = consultationMandatForm.getNumCcptCcpt();
            parameters.put(pCodProd, vCodProd);
            parameters.put(pNumContratCpt, vNumContratCpt);

            if (!consultationMandatForm.getDateDebutOper().equals("") || 
                !consultationMandatForm.getDateFinOper().equals("")) {
                vDateFin = consultationMandatForm.getDateFinOper();
                vDateDeb = consultationMandatForm.getDateDebutOper();
                parameters.put(pDateDeb, vDateDeb);
                parameters.put(pDateFin, vDateFin);
            }


            valueObject.setNomReport("ListeMandatHistorique");

            valueObject.setParams(parameters);

            request.getSession().setAttribute("CommonPrintVo", valueObject);
            request.setAttribute("print", "1");
            return mapping.findForward("HistMandat");
        } catch (Exception e) {
            com.oxia.fwk.core.Error erreur = new com.oxia.fwk.core.Error();
            StringBuffer text = 
                new StringBuffer("la transaction est Interrompu, une erreur dans ConsultationMandatAction / Dispatch Action :imprimerHistoriqueMandat ");
            text.append(e.toString());
            logger.error("Exception : ", e);
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

    public ActionForward afficheMandatsContrat(ActionMapping mapping, 
                                               ActionForm form, 
                                               HttpServletRequest request, 
                                               HttpServletResponse response) throws IOException, 
                                                                                    ServletException {
        try {

            ParamAgence paramAgence = new ParamAgence();
            paramAgence = 
                    (ParamAgence)request.getSession().getAttribute("paramAgBNA"); /// structure de l'agent qui fait la saisie
            ConsultationMandatForm consultationMandatForm = 
                (ConsultationMandatForm)form;
            PersonneStrc personneStrc = new PersonneStrc();
            PersonneCpt personneCpt = new PersonneCpt();
            consultationMandatForm.getListContratCpt().clear();
            consultationMandatForm.setPersonneexist("true");
            consultationMandatForm.setNomNomPers("");
            consultationMandatForm.setNomPrnPers("");

            //-cas direction centrale
            if (paramAgence.getCodTstrcTstrc() != 5) {
                personneStrc.setCodStrcStrc(paramAgence.getCodStrcStrc());
            }
            personneStrc.setCodTpceTpce(new Long(consultationMandatForm.getTypPcePers()));
            personneStrc.setNumPcePers(consultationMandatForm.getNumPcePers());
            GetPersonneCptCmd getPersonneCptCmd = new GetPersonneCptCmd();
            personneCpt = (PersonneCpt)getPersonneCptCmd.execute(personneStrc);
            if (personneCpt.getPersonne() != 
                null) { /* si personne est existante */
                if ((personneCpt.getPersonne().getTypePiece().getCodTpceTpce() == 
                    9)||(personneCpt.getPersonne().getTypePiece().getCodTpceTpce() == 
                    11)) { /* cas RCS affichage de libSiglPers */
                    consultationMandatForm.setNomNomPers(personneCpt.getPersonne().getLibSiglPers());
                    consultationMandatForm.setNomPrnPers(personneCpt.getPersonne().getNomRsPers());
                } else {
                    consultationMandatForm.setNomNomPers(personneCpt.getPersonne().getNomNomPers());
                    consultationMandatForm.setNomPrnPers(personneCpt.getPersonne().getNomPrnPers());
                }

                List listeDesContratsView = new ArrayList();
                for (Iterator it = personneCpt.getListeContratCpt().iterator(); 
                     it.hasNext(); ) {
                    ContratCpt contratCpt = (ContratCpt)it.next();
                    if (contratCpt.getMandats() != null && 
                        contratCpt.getMandats().size() != 0) {
                        String cleContrat = 
                            contratCpt.getContratCptId().getCodStrcStrc() + 
                            "_" + contratCpt.getContratCptId().getCodPrdPrd() + 
                            "*" + 
                            contratCpt.getContratCptId().getNumCcptCcpt();
                        ContratCptView contratCptView = new ContratCptView();
                        contratCptView.setCleContrat(cleContrat);
                        contratCptView.setDateContrat(DateHandler.dateToStr(contratCpt.getDatOuvCcpt()));
                        //contratCptView.setCodeAgence(contratCpt.getStructure().getCodStrcStrc().toString().);
                        contratCptView.setNumeroCompte(StrHandler.lpad(contratCpt.getContratCptId().getNumCcptCcpt().toString(), 
                                                                       '0', 
                                                                       6));
                        contratCptView.setCodeAgence(StrHandler.lpad(contratCpt.getContratCptId().getCodStrcStrc().toString(), 
                                                                     '0', 3));
                        contratCptView.setCodeProduit(StrHandler.lpad(contratCpt.getContratCptId().getCodPrdPrd().toString(), 
                                                                      '0', 4));
                        contratCptView.setContratCpt(contratCpt);
                        listeDesContratsView.add(contratCptView);
                    }
                }

                consultationMandatForm.setListContratCpt(listeDesContratsView);
                int cptsize = 
                    consultationMandatForm.getListContratCpt().size();
                for (int i = 0; i < cptsize; i++)
                    consultationMandatForm.getIndexContratChoisis().add("");

                consultationMandatForm.setPersonneexist("true");
            } else {
                consultationMandatForm.setNumPcePers("");
                consultationMandatForm.setTypPcePers("2");
                consultationMandatForm.setPersonneexist("false");
            }
            return mapping.findForward("parpersonne");
        } catch (Exception e) {
            com.oxia.fwk.core.Error erreur = new com.oxia.fwk.core.Error();
            StringBuffer text = 
                new StringBuffer("la transaction est Interrompu, une erreur dans ConsultationMandatAction / Dispatch Action :afficheMandatsContrat ");
            text.append(e.toString());
            erreur.setCode("200");
            erreur.setDescription(text.toString());
            logger.error("Exception : ", e);
            ActionMessage actionMessage = 
                new ActionMessage("exception.generique", 
                                  erreur.getDescription());
            actionMessages.add("Erreur ", actionMessage);
            this.saveMessages(request, actionMessages);
            return mapping.findForward("error");
        }

    }

    public ActionForward rechercheParDosJur(ActionMapping mapping, 
                                            ActionForm form, 
                                            HttpServletRequest request, 
                                            HttpServletResponse response) throws IOException, 
                                                                                 ServletException {
        try {

            ParamAgence paramAgence = new ParamAgence();
            paramAgence = 
                    (ParamAgence)request.getSession().getAttribute("paramAgBNA"); /// structure de l'agent qui fait la saisie
            ConsultationMandatForm consultationMandatForm = 
                (ConsultationMandatForm)form;
            PersonneStrc personneStrc = new PersonneStrc();
            PersonneCpt personneCpt = new PersonneCpt();
            consultationMandatForm.getListContratCpt().clear();
            consultationMandatForm.setPersonneexist("true");
            consultationMandatForm.setNomNomPers("");
            consultationMandatForm.setNomPrnPers("");

            //-cas direction centrale
            if (paramAgence.getCodTstrcTstrc() != 5) {
                personneStrc.setCodStrcStrc(paramAgence.getCodStrcStrc());
            }

            personneStrc.setNumDosJur(Long.valueOf(consultationMandatForm.getNumDosJur()));
            personneStrc.setCodStrcJur(paramAgence.getCodStrcStrc());


            GetContratByDossJurCmd getContratByDossJurCmd = 
                new GetContratByDossJurCmd();
            personneCpt = 
                    (PersonneCpt)getContratByDossJurCmd.execute(personneStrc);
            if (personneCpt.getPersonne() != 
                null) { /* si personne est existante */
                consultationMandatForm.setTypPcePers(personneCpt.getPersonne().getTypePiece().getCodTpceTpce().toString());
                consultationMandatForm.setNumPcePers(personneCpt.getPersonne().getNumPcePers());
                if (personneCpt.getPersonne().getTypePiece().getCodTpceTpce() == 
                    9) { /* cas RCS affichage de libSiglPers */
                    consultationMandatForm.setNomNomPers(personneCpt.getPersonne().getLibSiglPers());
                    consultationMandatForm.setNomPrnPers(personneCpt.getPersonne().getNomRsPers());
                } else {
                    consultationMandatForm.setNomNomPers(personneCpt.getPersonne().getNomNomPers());
                    consultationMandatForm.setNomPrnPers(personneCpt.getPersonne().getNomPrnPers());
                }

                List listeDesContratsView = new ArrayList();
                for (Iterator it = personneCpt.getListeContratCpt().iterator(); 
                     it.hasNext(); ) {
                    ContratCpt contratCpt = (ContratCpt)it.next();
                    if (contratCpt.getMandats() != null && 
                        contratCpt.getMandats().size() != 0) {
                        String cleContrat = 
                            contratCpt.getContratCptId().getCodStrcStrc() + 
                            "_" + contratCpt.getContratCptId().getCodPrdPrd() + 
                            "*" + 
                            contratCpt.getContratCptId().getNumCcptCcpt();
                        ContratCptView contratCptView = new ContratCptView();
                        contratCptView.setCleContrat(cleContrat);
                        contratCptView.setDateContrat(DateHandler.dateToStr(contratCpt.getDatOuvCcpt()));
                        //contratCptView.setCodeAgence(contratCpt.getStructure().getCodStrcStrc().toString().);
                        contratCptView.setNumeroCompte(StrHandler.lpad(contratCpt.getContratCptId().getNumCcptCcpt().toString(), 
                                                                       '0', 
                                                                       6));
                        contratCptView.setCodeAgence(StrHandler.lpad(contratCpt.getContratCptId().getCodStrcStrc().toString(), 
                                                                     '0', 3));
                        contratCptView.setCodeProduit(StrHandler.lpad(contratCpt.getContratCptId().getCodPrdPrd().toString(), 
                                                                      '0', 4));
                        contratCptView.setContratCpt(contratCpt);
                        listeDesContratsView.add(contratCptView);
                    }
                }

                consultationMandatForm.setListContratCpt(listeDesContratsView);
                int cptsize = 
                    consultationMandatForm.getListContratCpt().size();
                for (int i = 0; i < cptsize; i++)
                    consultationMandatForm.getIndexContratChoisis().add("");

                consultationMandatForm.setPersonneexist("true");
            } else {
                consultationMandatForm.setNumPcePers("");
                consultationMandatForm.setTypPcePers("2");
                consultationMandatForm.setPersonneexist("false");
            }
            return mapping.findForward("parpersonne");
        } catch (Exception e) {
            com.oxia.fwk.core.Error erreur = new com.oxia.fwk.core.Error();
            StringBuffer text = 
                new StringBuffer("la transaction est Interrompu, une erreur dans ConsultationMandatAction / Dispatch Action :afficheMandatsContrat ");
            text.append(e.toString());
            erreur.setCode("200");
            erreur.setDescription(text.toString());
            logger.error("Exception : ", e);
            ActionMessage actionMessage = 
                new ActionMessage("exception.generique", 
                                  erreur.getDescription());
            actionMessages.add("Erreur ", actionMessage);
            this.saveMessages(request, actionMessages);
            return mapping.findForward("error");
        }

    }

    public ActionForward rechercheParDemande(ActionMapping mapping, 
                                             ActionForm form, 
                                             HttpServletRequest request, 
                                             HttpServletResponse response) throws IOException, 
                                                                                  ServletException {

        try {

            ConsultationMandatForm consultationMandatForm = 
                (ConsultationMandatForm)form;
            consultationMandatForm.setPersMorale("false");
            consultationMandatForm.setListAssocies(null);
            DetailMandat detailMandat = new DetailMandat();
            ActionMessages actionMessages = new ActionMessages();
            /**********************commande de recherche du detail du mandat*/
            Mandat mandat = new Mandat();
            ParamAgence paramAgence = new ParamAgence();
            paramAgence = 
                    (ParamAgence)request.getSession().getAttribute("paramAgBNA"); /// structure de l'agent qui fait la saisie
            if (paramAgence.hasError()) {
                List listErreur = paramAgence.getErrors();
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
            DossierMandat dossierMandat = new DossierMandat();

            dossierMandat.setNumDemMand(new Long(consultationMandatForm.getNumDemMand()));
            dossierMandat.setCodStrcConcer(new Long(paramAgence.getCodStrcStrc()));

            GetMandatParDemandeCmd getMandatParDemandeCmd = 
                new GetMandatParDemandeCmd();
            Mandat mandat1 = 
                (Mandat)getMandatParDemandeCmd.execute(dossierMandat);

            GetDetailMandatCmd getDetailMandatCmd = new GetDetailMandatCmd();
            if (mandat1 != null) {
                if (mandat1.hasError()) {
                    List listErreur = paramAgence.getErrors();
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
                } else {
                    detailMandat = 
                            (DetailMandat)getDetailMandatCmd.execute(mandat1);
                    consultationMandatForm.setDatCreMand(DateHandler.dateToStr(mandat1.getDatCreMand()));
                    consultationMandatForm.setDatDebMand(DateHandler.dateToStr(mandat1.getDatDebMand()));
                    consultationMandatForm.setDatFinMand(DateHandler.dateToStr(mandat1.getDatFinMand()));
                    consultationMandatForm.setCodTypMand(mandat1.getCodTypMand());
                    if (mandat1.getNumRdjMand() != null) {
                        consultationMandatForm.setNumRdjMand(mandat1.getNumRdjMand());
                    }
                    if (mandat1.getLibMrejMand() != null && 
                        !mandat1.getLibMrejMand().equalsIgnoreCase("")) {
                        consultationMandatForm.setTypeValidation("R");
                    }
                    if (mandat1.getLibMrsvMand() != null && 
                        !mandat1.getLibMrsvMand().equalsIgnoreCase("")) {
                        consultationMandatForm.setTypeValidation("VR");
                    }
                    consultationMandatForm.setMotifRejet(mandat1.getLibMrejMand());
                    consultationMandatForm.setMotifReserve(mandat1.getLibMrsvMand());
                    consultationMandatForm.setObservation(mandat1.getLibObsMand());
                    consultationMandatForm.setTypPcePers(mandat1.getContratCpt().getClient().getPersonne().getTypePiece().getCodTpceTpce().toString());
                    consultationMandatForm.setNumPcePers(mandat1.getContratCpt().getClient().getPersonne().getNumPcePers().toString());
                    /*cas personne morale*/
                    if (mandat1.getContratCpt().getClient().getTypePers().getCodTperTper().equals(Constants.PERSMORALE)) {
                        consultationMandatForm.setPersMorale("true");
                        String rue = "";
                        String cite = "";
                        String imm = "";
                        String codp = "";
                        consultationMandatForm.setNomNomPers(mandat1.getContratCpt().getClient().getPersonne().getNomRsPers());
                        consultationMandatForm.setNomPrnPers(mandat1.getContratCpt().getClient().getPersonne().getLibSiglPers());
                        if (mandat1.getContratCpt().getClient().getPersonne().getMontCapPers() != 
                            null) {
                            consultationMandatForm.setCapitalSoc(StrHandler.formatmnt(Math.abs(mandat1.getContratCpt().getClient().getPersonne().getMontCapPers())));
                        } else {
                            consultationMandatForm.setCapitalSoc("0");
                        }
                        consultationMandatForm.setFormeJur(mandat1.getContratCpt().getClient().getPersonne().getFormeJuridique().getLibFjFj());
                        if (mandat1.getContratCpt().getAdresseCorresp().getImmeuble() != 
                            null) {
                            imm = 
mandat1.getContratCpt().getAdresseCorresp().getImmeuble();
                        }
                        if (mandat1.getContratCpt().getAdresseCorresp().getRue() != 
                            null) {
                            rue = 
mandat1.getContratCpt().getAdresseCorresp().getRue();
                        }
                        if (mandat1.getContratCpt().getAdresseCorresp().getCite() != 
                            null) {
                            cite = 
mandat1.getContratCpt().getAdresseCorresp().getCite();
                        }
                        if (mandat1.getContratCpt().getAdresseCorresp().getCodCpCp() != 
                            null) {
                            codp = 
mandat1.getContratCpt().getAdresseCorresp().getCodCpCp();
                        }
                        consultationMandatForm.setSiegeSoc(imm + " " + rue + 
                                                           " " + cite + " " + 
                                                           codp);
                        ParamListPersonneQualiteClientVo paramVo = 
                            new ParamListPersonneQualiteClientVo();
                        paramVo.setNumSeqPers(mandat1.getContratCpt().getClient().getPersonne().getNumSeqPers());
                        paramVo.setCodQualQual(Long.valueOf(17));
                        GetPersonneClientQualiteCmd getPersonneClientQualiteCmd = 
                            new GetPersonneClientQualiteCmd();
                        paramVo = 
                                (ParamListPersonneQualiteClientVo)getPersonneClientQualiteCmd.execute(paramVo);
                        if (!paramVo.hasError()) {
                            if (paramVo.getListePersonneClient() != null && 
                                (paramVo.getListePersonneClient().size() > 
                                 0)) {
                                List listeDesPersonnes = new ArrayList();


                                for (Iterator it = 
                                     paramVo.getListePersonneClient().iterator(); 
                                     it.hasNext(); ) {
                                    PersClient persClient = 
                                        (PersClient)it.next();
                                    PersonneClientView personneClientView = 
                                        new PersonneClientView();
                                    personneClientView.setNumSeqPers(persClient.getPersonne().getNumSeqPers());
                                    personneClientView.setCodTpceTpce(persClient.getPersonne().getTypePiece().getCodTpceTpce());
                                    personneClientView.setNumPcePers(persClient.getPersonne().getNumPcePers());
                                    personneClientView.setNomNomPers(persClient.getPersonne().getNomNomPers());
                                    personneClientView.setNomPrnPers(persClient.getPersonne().getNomPrnPers());
                                    if (persClient.getTauxPartPecl() != null) {
                                        personneClientView.setTauxPartPect(persClient.getTauxPartPecl());
                                    }

                                    listeDesPersonnes.add(personneClientView);
                                }

                                consultationMandatForm.setListAssocies(listeDesPersonnes);
                            }
                        }


                    } else { // cas d'une personne physique
                        consultationMandatForm.setNomNomPers(mandat1.getContratCpt().getClient().getPersonne().getNomNomPers().toString());
                        consultationMandatForm.setNomPrnPers(mandat1.getContratCpt().getClient().getPersonne().getNomPrnPers().toString());
                    }
                    if (detailMandat != null) {
                        if (detailMandat.hasError()) {
                            List listErreur = paramAgence.getErrors();
                            for (Iterator it = listErreur.iterator(); 
                                 it.hasNext(); ) {
                                com.oxia.fwk.core.Error erreur = 
                                    (com.oxia.fwk.core.Error)it.next();
                                ActionMessage actionMessage = 
                                    new ActionMessage("exception.generique", 
                                                      erreur.getDescription());
                                actionMessages.add("Erreur ", actionMessage);
                            }
                            this.saveMessages(request, actionMessages);
                            return mapping.findForward("error");
                        } else {
                            consultationMandatForm.setDetailMandatOperation(detailMandat.getListeMandatOperations());
                            consultationMandatForm.setDetailMandatPersonne(detailMandat.getListeMandatPersonnes());
                        }
                    }
                }
            }

            return mapping.findForward("parmandat");

        } catch (Exception e) {
            com.oxia.fwk.core.Error erreur = new com.oxia.fwk.core.Error();
            StringBuffer text = 
                new StringBuffer("la transaction est Interrompu, une erreur dans ConsultationMandatAction / Dispatch Action :rechercheParDemande ");
            text.append(e.toString());
            erreur.setCode("200");
            erreur.setDescription(text.toString());
            logger.error("Exception : ", e);
            ActionMessage actionMessage = 
                new ActionMessage("exception.generique", 
                                  erreur.getDescription());
            actionMessages.add("Erreur ", actionMessage);
            this.saveMessages(request, actionMessages);
            return mapping.findForward("error");
        }

    }

    private void getStructureConcernee(ConsultationMandatForm consultationMandatForm, 
                                       Mandat mandat, 
                                       ParamAgence paramAgence) {

        /* affectation de la structure concernée */
        if (consultationMandatForm.getMyTypeStructure() == 1) {
            if (consultationMandatForm.getTypeStrcConc().equalsIgnoreCase("A")) { /// structure concerné : Agence
                mandat.setCodStrcMand(paramAgence.getCodStrcStrc());

            } else {
                if (consultationMandatForm.getTypeStrcConc().equalsIgnoreCase("R")) { /// structure concerné : Dir Reg
                    mandat.setCodStrcMand(paramAgence.getCodStrmStrc());

                } else { /// structure concerné : DAJur
                    mandat.setCodStrcMand(Constants.COD_STRC_DAJ);

                }
            }
        } else {
            if (consultationMandatForm.getMyTypeStructure() == 2) {
                if (consultationMandatForm.getTypeStrcConc() != null && 
                    consultationMandatForm.getTypeStrcConc().equalsIgnoreCase("R")) { /// structure concerné : Dir Rg
                    mandat.setCodStrcMand(paramAgence.getCodStrmStrc());

                } else { /// structure concerné : DAJur
                    mandat.setCodStrcMand(Constants.COD_STRC_DAJ);

                }
            } else {
                mandat.setCodStrcMand(Constants.COD_STRC_DAJ);

            }
        }
    }


    private void getStructureConcerneeRecherche(ConsultationMandatForm consultationMandatForm, 
                                                MandatRecherche mandatRecherche, 
                                                ParamAgence paramAgence) {

        /* affectation de la structure concernée */
        if (consultationMandatForm.getMyTypeStructure().intValue() == 1) {
            if (consultationMandatForm.getTypeStrcConc().equalsIgnoreCase("A")) { /// structure concerné : Agence
                mandatRecherche.setCodStrcConcer(paramAgence.getCodStrcStrc());
                mandatRecherche.setLibStrcConcer("l'agence");
            } else {
                if (consultationMandatForm.getTypeStrcConc().equalsIgnoreCase("R")) { /// structure concerné : Dir Reg
                    mandatRecherche.setCodStrcConcer(paramAgence.getCodStrmStrc());
                    mandatRecherche.setLibStrcConcer("la direction régionale");
                } else { /// structure concerné : DAJur
                    mandatRecherche.setCodStrcConcer(Constants.COD_STRC_DAJ);
                    mandatRecherche.setLibStrcConcer("la direction des affaires juridiques");
                }
            }
        } else {
            if (consultationMandatForm.getMyTypeStructure() == 2) {
                if (consultationMandatForm.getTypeStrcConc() != null && 
                    consultationMandatForm.getTypeStrcConc().equalsIgnoreCase("R")) { /// structure concerné : Dir Rég
                    mandatRecherche.setCodStrcConcer(paramAgence.getCodStrmStrc());
                    mandatRecherche.setLibStrcConcer("la direction régionale");
                } else { /// structure concerné : DAJur
                    mandatRecherche.setCodStrcConcer(Constants.COD_STRC_DAJ);
                    mandatRecherche.setLibStrcConcer("la direction des affaires juridiques");
                }
            } else {
                if (mandatRecherche.getCodEtatAttente() != null) {
                    mandatRecherche.setCodStrcConcer(Constants.COD_STRC_DAJ);
                    mandatRecherche.setLibStrcConcer("la direction des affaires juridiques");

                } else {
                    mandatRecherche.setCodStrcConcer(mandatRecherche.getContratCptId().getCodStrcStrc());
                    mandatRecherche.setLibStrcConcer("la direction des affaires juridiques");
                }
            }
        }
    }


    public ActionForward rechercherMandOper(ActionMapping mapping, 
                                            ActionForm form, 
                                            HttpServletRequest request, 
                                            HttpServletResponse response) throws IOException, 
                                                                                 ServletException {
        /**
     * Action de la page  consultmandOper.jsp
     * recherche les opérations sur les mandat
     * entre deux date et par type d'opération
     * Nom du package : com.bna.smile.web.procuration.actions
     * @author Kriaa hatem & Boussen youssef
     * @version le 27/05/2007
     */

        try {

            ConsultationMandatForm consultationMandatForm = 
                (ConsultationMandatForm)form;
            ParamMandOper paramMandOper = new ParamMandOper();
            GetTraceMandatCmd getTraceMandatCmd = new GetTraceMandatCmd();
            Listes liste = new Listes();
            ParamAgence paramAgence = new ParamAgence();
            paramAgence = 
                    (ParamAgence)request.getSession().getAttribute("paramAgBNA");
            /*remplissage des parametres de recherche*/
            remplirParamRecherche(consultationMandatForm, paramMandOper);
            /*recherche des traces mandats*/
            liste = (Listes)getTraceMandatCmd.execute(paramMandOper);
            if ((liste != null) && (liste.getList().size() > 0)) {
                for (Iterator it = liste.getList().iterator(); it.hasNext(); 
                ) {
                    TraceMandat traceMandat = (TraceMandat)it.next();
                    if (traceMandat.getMandat().getContratCpt().getContratCptId().getCodStrcStrc().longValue() == 
                        paramAgence.getCodStrcStrc()) {
                        TraceMandatView traceMandatView = 
                            new TraceMandatView();
                        traceMandatView.setContratCpt(traceMandat.getMandat().getContratCpt());
                        traceMandatView.setNumMandMand(traceMandat.getMandat().getNumMandMand().toString());
                        traceMandatView.setDateOper(DateHandler.dateToStr(traceMandat.getDatOperTrm()));
                        traceMandatView.setNumMatrUser(traceMandat.getPersonnel().getNumMatrUser());
                        traceMandatView.setCodTypMand(traceMandat.getMandat().getCodTypMand());

                        if (traceMandat.getTache().getTacheId().getCodOperOper().longValue() == 
                            Constants.COD_OPER_CREAT_MANDAT) {
                            consultationMandatForm.getListeCreation().add(traceMandatView);
                        }
                        if (traceMandat.getTache().getTacheId().getCodOperOper().longValue() == 
                            Constants.COD_OPER_MODIF_MANDAT) {
                            consultationMandatForm.getListeModification().add(traceMandatView);
                        }
                        if (traceMandat.getTache().getTacheId().getCodOperOper().longValue() == 
                            Constants.COD_OPER_ANNUL_MAND) {
                            consultationMandatForm.getListeAnnulation().add(traceMandatView);
                        }
                        if (traceMandat.getTache().getTacheId().getCodOperOper().longValue() == 
                            Constants.COD_OPER_RENOUV_MAND) {
                            consultationMandatForm.getListeRenouvellement().add(traceMandatView);
                        }

                    }
                }
            } else {
                consultationMandatForm.setAlert("AucuneTraceMandat");
                return mapping.findForward("consultmandatOper");
            }
            return mapping.findForward("operationMandat");
        } catch (Exception e) {
            com.oxia.fwk.core.Error erreur = new com.oxia.fwk.core.Error();
            StringBuffer text = 
                new StringBuffer("la transaction est Interrompu, une erreur dans ConsultationMandatAction / Dispatch Action :rechercheMandatOper ");
            text.append(e.toString());
            erreur.setCode("200");
            erreur.setDescription(text.toString());
            logger.error("Exception : ", e);
            ActionMessage actionMessage = 
                new ActionMessage("exception.generique", 
                                  erreur.getDescription());
            actionMessages.add("Erreur ", actionMessage);
            this.saveMessages(request, actionMessages);
            return mapping.findForward("error");
        }


    }

    public ActionForward recherchHistMand(ActionMapping mapping, 
                                          ActionForm form, 
                                          HttpServletRequest request, 
                                          HttpServletResponse response) throws IOException, 
                                                                               ServletException {
        /**
         * Action de la page  consulhistMand.jsp
         * recherche l'historique des mandats d'un contrat donné
         * Nom du package : com.bna.smile.web.procuration.actions
         * @author Kriaa hatem & Boussen youssef
         * @version le 27/05/2007
         */

        try {

            ConsultationMandatForm consultationMandatForm = 
                (ConsultationMandatForm)form;
            ParamMandOper paramMandOper = new ParamMandOper();
            Listes liste = new Listes();
            ContratCptId contratCptId = new ContratCptId();
            paramMandOper.setDateDebutOper(DateHandler.strToDate(consultationMandatForm.getDateDebutOper()));
            paramMandOper.setDateFinOper(DateHandler.strToDate(consultationMandatForm.getDateFinOper()));
            contratCptId.setCodPrdPrd(Long.valueOf(consultationMandatForm.getCodPrdPrd()));
            contratCptId.setCodStrcStrc(Long.valueOf(consultationMandatForm.getCodStrcStrc()));
            contratCptId.setNumCcptCcpt(Long.valueOf(consultationMandatForm.getNumCcptCcpt()));
            paramMandOper.setContratCptId(contratCptId);
            GetTraceMandatCptCmd getTraceMandatCptCmd = 
                new GetTraceMandatCptCmd();

            liste = (Listes)getTraceMandatCptCmd.execute(paramMandOper);
            if ((liste != null) && (liste.getList().size() > 0)) {

                for (Iterator it = liste.getList().iterator(); it.hasNext(); 
                ) {
                    TraceMandat traceMandat = (TraceMandat)it.next();
                    TraceMandatView traceMandatView = new TraceMandatView();
                    traceMandatView.setContratCpt(traceMandat.getMandat().getContratCpt());
                    traceMandatView.setNumMandMand(traceMandat.getMandat().getNumMandMand().toString());
                    traceMandatView.setDateOper(DateHandler.dateToStr(traceMandat.getDatOperTrm()));
                    traceMandatView.setNumMatrUser(traceMandat.getPersonnel().getNumMatrUser());
                    traceMandatView.setCodTypMand(traceMandat.getMandat().getCodTypMand());
                    if (traceMandat.getMandat().getDatDebMand() != null) {
                        traceMandatView.setDatDebMand(DateHandler.dateToStr(traceMandat.getMandat().getDatDebMand()));
                    }
                    if (traceMandat.getMandat().getDatFinMand() != null) {
                        traceMandatView.setDatFinMand(DateHandler.dateToStr(traceMandat.getMandat().getDatFinMand()));
                    }
                    traceMandatView.setCodOperOper((traceMandat.getTache().getTacheId().getCodOperOper()).toString());
                    getLibOper(traceMandat, traceMandatView);
                    /*mandatpersonne*/
                    String nom = "";
                    for (Iterator it1 = 
                         traceMandat.getMandat().getMandatPersonnes().iterator(); 
                         it1.hasNext(); ) {
                        MandatPersonne mandatPersonne = 
                            (MandatPersonne)it1.next();
                        if (mandatPersonne.getCodEtatMp().equalsIgnoreCase("V")) {
                            nom = 
nom + mandatPersonne.getPersonne().getNomNomPers() + " " + 
  mandatPersonne.getPersonne().getNomPrnPers() + "  ";
                        }
                    }
                    traceMandatView.setMandataires(nom);
                    consultationMandatForm.getListeCreation().add(traceMandatView);
                }

            } else {
                consultationMandatForm.setAlert("AucuneTraceMandat");
                return mapping.findForward("consultHistMand");
            }
            return mapping.findForward("HistMandat");
        } catch (Exception e) {
            com.oxia.fwk.core.Error erreur = new com.oxia.fwk.core.Error();
            StringBuffer text = 
                new StringBuffer("la transaction est Interrompu, une erreur dans ConsultationMandatAction / Dispatch Action :rechercheHistMand ");
            text.append(e.toString());
            erreur.setCode("200");
            erreur.setDescription(text.toString());
            logger.error("Exception : ", e);
            ActionMessage actionMessage = 
                new ActionMessage("exception.generique", 
                                  erreur.getDescription());
            actionMessages.add("Erreur ", actionMessage);
            this.saveMessages(request, actionMessages);
            return mapping.findForward("error");
        }


    }

    private void remplirParamRecherche(ConsultationMandatForm consultationMandatForm, 
                                       ParamMandOper paramMandOper) {

        paramMandOper.setDateDebutOper(DateHandler.strToDate(consultationMandatForm.getDateDebutOper()));
        paramMandOper.setDateFinOper(DateHandler.strToDate(consultationMandatForm.getDateFinOper()));

        /*code tache*/
        if (consultationMandatForm.getCodtach().equalsIgnoreCase("S")) {
            paramMandOper.setCodtach(Constants.COD_TACHE_SAISIE_MANDAT);
        }
        if (consultationMandatForm.getCodtach().equalsIgnoreCase("P")) {
            paramMandOper.setCodtach(Constants.COD_TACHE_PREVALID_MANDAT);
        }
        if (consultationMandatForm.getCodtach().equalsIgnoreCase("V")) {
            paramMandOper.setCodtach(Constants.COD_TACHE_VALID_MANDAT);
        }
        /*code operation*/
        if (consultationMandatForm.getCodOper().equalsIgnoreCase("T")) {
            paramMandOper.setCodOper(null);
        }
        if (consultationMandatForm.getCodOper().equalsIgnoreCase("C")) {
            paramMandOper.setCodOper(Constants.COD_OPER_CREAT_MANDAT);
        }
        if (consultationMandatForm.getCodOper().equalsIgnoreCase("M")) {
            paramMandOper.setCodOper(Constants.COD_OPER_MODIF_MANDAT);
        }
        if (consultationMandatForm.getCodOper().equalsIgnoreCase("A")) {
            paramMandOper.setCodOper(Constants.COD_OPER_ANNUL_MAND);
        }
        if (consultationMandatForm.getCodOper().equalsIgnoreCase("R")) {
            paramMandOper.setCodOper(Constants.COD_OPER_RENOUV_MAND);
        }

    }

    private void getLibOper(TraceMandat traceMandat, 
                            TraceMandatView traceMandatView) {
        /*cas creation*/
        if (traceMandat.getTache().getTacheId().getCodOperOper().intValue() == 
            Constants.COD_OPER_CREAT_MANDAT) {
            if (traceMandat.getTache().getTacheId().getCodTachTach().intValue() == 
                Constants.COD_TACHE_SAISIE_MANDAT) {
                traceMandatView.setLibOperOper("Saisie Création");
            }
            if (traceMandat.getTache().getTacheId().getCodTachTach().intValue() == 
                Constants.COD_TACHE_PREVALID_MANDAT) {
                traceMandatView.setLibOperOper("Prévalidation Création");
            }
            if (traceMandat.getTache().getTacheId().getCodTachTach().intValue() == 
                Constants.COD_TACHE_VALID_MANDAT) {
                traceMandatView.setLibOperOper("Validation Création");
            }
        }
        /*cas modification*/
        if (traceMandat.getTache().getTacheId().getCodOperOper().intValue() == 
            Constants.COD_OPER_MODIF_MANDAT) {
            if (traceMandat.getTache().getTacheId().getCodTachTach().intValue() == 
                Constants.COD_TACHE_SAISIE_MANDAT) {
                traceMandatView.setLibOperOper("Saisie Modification");
            }
            if (traceMandat.getTache().getTacheId().getCodTachTach().intValue() == 
                Constants.COD_TACHE_PREVALID_MANDAT) {
                traceMandatView.setLibOperOper("Prévalidation Modification");
            }
            if (traceMandat.getTache().getTacheId().getCodTachTach().intValue() == 
                Constants.COD_TACHE_VALID_MANDAT) {
                traceMandatView.setLibOperOper("Validation Modification");
            }
        }
        /*cas annulation*/
        if (traceMandat.getTache().getTacheId().getCodOperOper().intValue() == 
            Constants.COD_OPER_ANNUL_MAND) {
            if (traceMandat.getTache().getTacheId().getCodTachTach().intValue() == 
                Constants.COD_TACHE_SAISIE_MANDAT) {
                traceMandatView.setLibOperOper("Saisie Annulation");
            }
            if (traceMandat.getTache().getTacheId().getCodTachTach().intValue() == 
                Constants.COD_TACHE_PREVALID_MANDAT) {
                traceMandatView.setLibOperOper("Prévalidation Annulation");
            }
            if (traceMandat.getTache().getTacheId().getCodTachTach().intValue() == 
                Constants.COD_TACHE_VALID_MANDAT) {
                traceMandatView.setLibOperOper("Validation Annulation");
            }
        }
        /*cas renouvellement*/
        if (traceMandat.getTache().getTacheId().getCodOperOper().intValue() == 
            Constants.COD_OPER_RENOUV_MAND) {
            if (traceMandat.getTache().getTacheId().getCodTachTach().intValue() == 
                Constants.COD_TACHE_SAISIE_MANDAT) {
                traceMandatView.setLibOperOper("Saisie Renouvellement");
            }
            if (traceMandat.getTache().getTacheId().getCodTachTach().intValue() == 
                Constants.COD_TACHE_PREVALID_MANDAT) {
                traceMandatView.setLibOperOper("Prévalidation Renouvellement");
            }
            if (traceMandat.getTache().getTacheId().getCodTachTach().intValue() == 
                Constants.COD_TACHE_VALID_MANDAT) {
                traceMandatView.setLibOperOper("Validation Renouvellement");
            }
        }


    }
}
