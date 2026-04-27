package com.bna.smile.web.change.actions;


import com.bna.commun.model.CoursChange;
import com.bna.commun.model.CoursChangeId;
import com.bna.commun.model.PariteOfficielle;
import com.bna.commun.model.PariteOfficielleId;
import com.bna.commun.model.Personnel;
import com.bna.commun.model.Tache;
import com.bna.commun.model.TacheId;
import com.bna.commun.model.TraceCoursChange;
import com.bna.commun.model.TracePariteOfficielle;
import com.bna.commun.util.ContextHandler;
import com.bna.commun.util.DateHandler;
import com.bna.smile.model.constant.Constants;
import com.bna.smile.model.domainechange.commande.GetCoursChangeCmd;
import com.bna.smile.model.domainechange.commande.GetCoursPariteOffCmd;
import com.bna.smile.model.domainechange.commande.InsertCoursChangeCmd;
import com.bna.smile.model.domainechange.commande.InsertPariteOffCmd;
import com.bna.smile.model.domainechange.commande.InsertTraceCoursChangeCmd;
import com.bna.smile.model.domainechange.commande.InsertTracePariteOffCmd;
import com.bna.smile.model.domainechange.commande.UpdateCoursChangeCmd;
import com.bna.smile.model.domainechange.commande.UpdatePariteOffCmd;
import com.bna.smile.model.domainechange.dao.ChangeDAO;
import com.bna.smile.model.reporting.model.CommonReportVO;
import com.bna.smile.web.change.forms.GestionCoursChangeForm;
import com.bna.smile.web.change.view.CoursChangeView;
import com.bna.smile.web.change.view.PariteOfficielleView;
import com.bna.smile.web.commun.model.ParamAgence;
import com.oxia.fwk.context.Context;
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
import org.apache.commons.collections.map.ListOrderedMap;
import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.actions.DispatchAction;

public class GestionCoursChangeAction extends DispatchAction {

    private static final

    Logger logger = Logger.getLogger(GestionCoursChangeAction.class);

    public ActionForward initierPage(ActionMapping mapping, ActionForm form, 
                                     HttpServletRequest request, 
                                     HttpServletResponse response) throws IOException, 
                                                                          ServletException {

        ActionMessages actionMessages = new ActionMessages();
       
        Context context = ContextHandler.getContext();
        try {
            GestionCoursChangeForm gestionCoursChangeForm = 
                (GestionCoursChangeForm)form;
            ParamAgence paramAgence = 
                (ParamAgence)request.getSession().getAttribute("paramAgBNA");
            List listCoursChange = null;
            
            //StructureDomaine structureDomaine = new StructureDomaine(paramAgence.getCodStrcStrc(),Constants.COD_DOM_CHANGE);
            String forward = "";
            ChangeDAO changeDAO = (ChangeDAO)context.getBean("changeDao");
            gestionCoursChangeForm.setAlert("");

            if (!gestionCoursChangeForm.getTypeMenu().equals("gestPartOff") && 
                !gestionCoursChangeForm.getTypeMenu().equals("validationPOff") && 
                !gestionCoursChangeForm.getTypeMenu().equals("consultPaoff")) {


                if (gestionCoursChangeForm.getTypeMenu().equalsIgnoreCase("insertion") ){
                    listCoursChange = 
                            changeDAO.getListCoursChangeVeilleValide(paramAgence.getDateComptable());
                } else if (gestionCoursChangeForm.getTypeMenu().equalsIgnoreCase("validation")) {
                    listCoursChange = 
                            changeDAO.getListCoursChangeEnAttente(paramAgence.getDateComptable());
                }
               
                ListOrderedMap ListCours = null;
                List listCoursView = new ArrayList();
                if (!gestionCoursChangeForm.getTypeMenu().equalsIgnoreCase("consult")) {
                for (Iterator it = listCoursChange.iterator(); it.hasNext(); 
                ) {
                    ListCours = (ListOrderedMap)it.next();
                    if ((ListCours.getValue(0)).toString() != null) {
                        CoursChangeView coursChangeView = 
                            new CoursChangeView();
                        coursChangeView.setCodDevise(ListCours.getValue(0).toString());
                        coursChangeView.setLibDevise(ListCours.getValue(2).toString());
                        coursChangeView.setDatCoursChange(ListCours.getValue(1).toString());
                        coursChangeView.setCoursAvhatBna(ListCours.getValue(3).toString());
                        coursChangeView.setCoursVenteBna(ListCours.getValue(4).toString());
                        coursChangeView.setCoursAchatBct(ListCours.getValue(5).toString());
                        coursChangeView.setCoursVenteBct(ListCours.getValue(6).toString());
                        listCoursView.add(coursChangeView);
                    }
                }
               
                }
                gestionCoursChangeForm.setListeCoursDevisesView(listCoursView);
                if (gestionCoursChangeForm.getTypeMenu().equalsIgnoreCase("insertion")) {
                    gestionCoursChangeForm.setDateJourCours(paramAgence.getDateComptable());
                    gestionCoursChangeForm.setLibelleOperation("Insertion Cours Change");
                } else if (gestionCoursChangeForm.getTypeMenu().equalsIgnoreCase("validation")) {
                    gestionCoursChangeForm.setLibelleOperation("Validation Saisie Cours Change");
                } else if (gestionCoursChangeForm.getTypeMenu().equalsIgnoreCase("consult")) {
                    gestionCoursChangeForm.setDateJournee(paramAgence.getDateComptable());
                    gestionCoursChangeForm.setLibelleOperation("Consultation Cours Change");
                    
                }
                if (gestionCoursChangeForm.getTypeMenu().equalsIgnoreCase("consult")) {
                    forward = "consult";
                } else {
                    forward = "success";
                }
            } else {
                List listPariteOff = null;
                int annee = 
                    DateHandler.GetYearFromDate(DateHandler.strToDate(paramAgence.getDateComptable()));
                gestionCoursChangeForm.setDateJourCours(String.valueOf(annee));
               
                if (gestionCoursChangeForm.getTypeMenu().equals("gestPartOff") ) {
                    listPariteOff = 
                            changeDAO.getListCoursPariteOfficielle("V");
                } else if(gestionCoursChangeForm.getTypeMenu().equalsIgnoreCase("validationPOff")) {
                    listPariteOff = 
                            changeDAO.getListCoursPariteOfficielle("A");
                }
                ListOrderedMap ListCours = null;
                List listPartOffView = new ArrayList();
                if (!gestionCoursChangeForm.getTypeMenu().equalsIgnoreCase("consultPaoff")) {
                for (Iterator it = listPariteOff.iterator(); it.hasNext(); ) {
                    ListCours = (ListOrderedMap)it.next();
                    if ((ListCours.getValue(0)).toString() != null) {
                        PariteOfficielleView pariteOfficielleView = 
                            new PariteOfficielleView();
                        pariteOfficielleView.setCodedevise(ListCours.getValue(0).toString());
                        pariteOfficielleView.setLibDevise(ListCours.getValue(2).toString());
                        pariteOfficielleView.setAnnee(ListCours.getValue(1).toString());
                        pariteOfficielleView.setMontCoursPaof(ListCours.getValue(3).toString());
                        listPartOffView.add(pariteOfficielleView);
                    }
                }
                }
                gestionCoursChangeForm.setListeParitOffView(listPartOffView);
                
                if (gestionCoursChangeForm.getTypeMenu().equalsIgnoreCase("gestPartOff")) {
                    gestionCoursChangeForm.setLibelleOperation("Insertion Parité Officielle");
                } else if (gestionCoursChangeForm.getTypeMenu().equalsIgnoreCase("validationPOff")) {
                    gestionCoursChangeForm.setLibelleOperation("Validation Saisie Parité Officielle");
                } else if (gestionCoursChangeForm.getTypeMenu().equalsIgnoreCase("consultPaoff")) {
                    gestionCoursChangeForm.setAnneeParite(String.valueOf(annee));
                    gestionCoursChangeForm.setLibelleOperation("Consultation parite officielle");
                }
                if (gestionCoursChangeForm.getTypeMenu().equalsIgnoreCase("consultPaoff")) {
                    forward = "consultPart";
                } else {
                    forward = "successPaoff";
                }
                

            }

            gestionCoursChangeForm.clear();
            return mapping.findForward(forward);
        } catch (Exception e) {
            com.oxia.fwk.core.Error erreur = new com.oxia.fwk.core.Error();
            StringBuffer text = 
                new StringBuffer("L'initialisation du domaine Change a été interrompue, Veuillez transmettre ce message à l'équipe informatique: ");
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

    public ActionForward rechercherCours(ActionMapping mapping, 
                                         ActionForm form, 
                                         HttpServletRequest request, 
                                         HttpServletResponse response) throws IOException, 
                                                                              ServletException {
        ActionMessages actionMessages = new ActionMessages();
        Context context = ContextHandler.getContext();
        try {

            ParamAgence paramAgence = 
                (ParamAgence)request.getSession().getAttribute("paramAgBNA");
            GestionCoursChangeForm gestionCoursChangeForm = 
                (GestionCoursChangeForm)form;
            List listCoursChange = null;
            gestionCoursChangeForm.setAlert("");
            ChangeDAO changeDAO = (ChangeDAO)context.getBean("changeDao");
            listCoursChange = 
                    changeDAO.getListCoursChangeParDate(gestionCoursChangeForm.getDateJournee());
            ListOrderedMap ListCours = null;
            List listCoursView = new ArrayList();
            if (listCoursChange.size() == 0) {
                gestionCoursChangeForm.setAlert("aucun");
            } else {
                for (Iterator it = listCoursChange.iterator(); it.hasNext(); 
                ) {
                    ListCours = (ListOrderedMap)it.next();
                    if ((ListCours.getValue(0)).toString() != null) {
                        CoursChangeView coursChangeView = 
                            new CoursChangeView();
                        coursChangeView.setCodDevise(ListCours.getValue(0).toString());
                        coursChangeView.setLibDevise(ListCours.getValue(2).toString());
                        coursChangeView.setDatCoursChange(ListCours.getValue(1).toString());
                        coursChangeView.setCoursAvhatBna(ListCours.getValue(3).toString());
                        coursChangeView.setCoursVenteBna(ListCours.getValue(4).toString());
                        coursChangeView.setCoursAchatBct(ListCours.getValue(5).toString());
                        coursChangeView.setCoursVenteBct(ListCours.getValue(6).toString());
                        listCoursView.add(coursChangeView);
                    }
                }
            }
            gestionCoursChangeForm.setListeCoursDevisesView(listCoursView);
            return mapping.findForward("consult");

        } catch (Exception e) {
            com.oxia.fwk.core.Error erreur = new com.oxia.fwk.core.Error();
            StringBuffer text = 
                new StringBuffer("Erreur au niveau de rechercherCours, Veuillez transmettre ce message à l'équipe informatique: ");
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

    public ActionForward rechercherParite(ActionMapping mapping, 
                                         ActionForm form, 
                                         HttpServletRequest request, 
                                         HttpServletResponse response) throws IOException, 
                                                                              ServletException {
        ActionMessages actionMessages = new ActionMessages();
        Context context = ContextHandler.getContext();
        try {

            ParamAgence paramAgence = 
                (ParamAgence)request.getSession().getAttribute("paramAgBNA");
            GestionCoursChangeForm gestionCoursChangeForm = 
                (GestionCoursChangeForm)form;
            List listPariteOff = null;
            gestionCoursChangeForm.setAlert("");
            ChangeDAO changeDAO = (ChangeDAO)context.getBean("changeDao");
            listPariteOff = 
                    changeDAO.getListCoursPariteParAnnee(gestionCoursChangeForm.getAnneeParite());
            ListOrderedMap ListCours = null;
            List listPartOffView = new ArrayList();
            if(listPariteOff.size()==0){
                gestionCoursChangeForm.setAlert("aucun");
            }else{
            for (Iterator it = listPariteOff.iterator(); it.hasNext(); ) {
                ListCours = (ListOrderedMap)it.next();
                if ((ListCours.getValue(0)).toString() != null) {
                    PariteOfficielleView pariteOfficielleView = 
                        new PariteOfficielleView();
                    pariteOfficielleView.setCodedevise(ListCours.getValue(0).toString());
                    pariteOfficielleView.setLibDevise(ListCours.getValue(2).toString());
                    pariteOfficielleView.setAnnee(ListCours.getValue(1).toString());
                    pariteOfficielleView.setMontCoursPaof(ListCours.getValue(3).toString());
                    listPartOffView.add(pariteOfficielleView);
                }
            }
            }
            gestionCoursChangeForm.setListeParitOffView(listPartOffView);
            return mapping.findForward("consultPart");

        } catch (Exception e) {
            com.oxia.fwk.core.Error erreur = new com.oxia.fwk.core.Error();
            StringBuffer text = 
                new StringBuffer("Erreur au niveau de rechercherParite, Veuillez transmettre ce message à l'équipe informatique: ");
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

    public ActionForward gererCoursChange(ActionMapping mapping, 
                                          ActionForm form, 
                                          HttpServletRequest request, 
                                          HttpServletResponse response) throws IOException, 
                                                                               ServletException {

        ActionMessages actionMessages = new ActionMessages();
        Context context = ContextHandler.getContext();
        try {

            ParamAgence paramAgence = 
                (ParamAgence)request.getSession().getAttribute("paramAgBNA");

            GestionCoursChangeForm gestionCoursChangeForm = 
                (GestionCoursChangeForm)form;
            CoursChangeId coursChangeId = new CoursChangeId();
            CoursChange coursChange = new CoursChange();
            coursChangeId.setCodDevDev(Long.valueOf(gestionCoursChangeForm.getCodeDevise()));
            coursChangeId.setDatJourCchn(DateHandler.strToDate(gestionCoursChangeForm.getDateJourCours()));
            coursChange.setCoursChangeId(coursChangeId);

            coursChange.setMontCabaCchn(Double.valueOf(gestionCoursChangeForm.getMontCoursAchatBna()));
            coursChange.setMontCvbaCchn(Double.valueOf(gestionCoursChangeForm.getMontCoursventeBna()));
            coursChange.setMontCabcCchn(Double.valueOf(gestionCoursChangeForm.getMontCoursAchatBct()));
            coursChange.setMontCvbcCchn(Double.valueOf(gestionCoursChangeForm.getMontCoursventeBct()));

            Personnel pers = new Personnel();
            pers.setNumMatrUser(paramAgence.getNumMatrUser());

            Tache tache = new Tache();
            TacheId tacheid = new TacheId();

            if (gestionCoursChangeForm.getTypeOperation().equalsIgnoreCase("insert")) {
                tacheid.setCodOperOper(Constants.COD_OPER_INSERT_COURS_CHANGE);
                tacheid.setCodTachTach(Constants.COD_TACH_PEC_COURS_CHANGE);
                coursChange.setCodEtatCchn("A");
                InsertCoursChangeCmd insertCoursChangeCmd = 
                    new InsertCoursChangeCmd();
                coursChange = 
                        (CoursChange)insertCoursChangeCmd.execute(coursChange);

            } else if (gestionCoursChangeForm.getTypeOperation().equalsIgnoreCase("update") || 
                       gestionCoursChangeForm.getTypeOperation().equalsIgnoreCase("validation")) {
                tacheid.setCodOperOper(Constants.COD_OPER_INSERT_COURS_CHANGE);
                tacheid.setCodTachTach(Constants.COD_TACH_PEC_COURS_CHANGE);
               
                coursChange.setCodEtatCchn("A");
                UpdateCoursChangeCmd updateCoursChangeCmd = 
                    new UpdateCoursChangeCmd();
                coursChange = 
                        (CoursChange)updateCoursChangeCmd.execute(coursChange);
            }

            // insertion de la trace cours change que pour les cas d'insertion ou de mise à jour // la validation sera inséré en bloc plus tard pour tous les devises validés
            if (gestionCoursChangeForm.getTypeOperation().equalsIgnoreCase("insert") || 
                gestionCoursChangeForm.getTypeOperation().equalsIgnoreCase("update")) {
                TraceCoursChange traceCoursChange = new TraceCoursChange();
                tache.setTacheId(tacheid);
                traceCoursChange.setCoursChange(coursChange);
                traceCoursChange.setPersonnel(pers);
                traceCoursChange.setDatOperTrcc(new Date());
                traceCoursChange.setTache(tache);
                InsertTraceCoursChangeCmd insertTraceCoursChangeCmd = 
                    new InsertTraceCoursChangeCmd();
                traceCoursChange = 
                        (TraceCoursChange)insertTraceCoursChangeCmd.execute(traceCoursChange);
            }


            return mapping.findForward("success");
        } catch (Exception e) {
            com.oxia.fwk.core.Error erreur = new com.oxia.fwk.core.Error();
            StringBuffer text = 
                new StringBuffer("Erreur au niveau de gererCoursChange, Veuillez transmettre ce message à l'équipe informatique: ");
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


    public ActionForward gererPariteOfficielle(ActionMapping mapping, 
                                               ActionForm form, 
                                               HttpServletRequest request, 
                                               HttpServletResponse response) throws IOException, 
                                                                                    ServletException {

        ActionMessages actionMessages = new ActionMessages();
        Context context = ContextHandler.getContext();
        try {

            ParamAgence paramAgence = 
                (ParamAgence)request.getSession().getAttribute("paramAgBNA");

            GestionCoursChangeForm gestionCoursChangeForm = 
                (GestionCoursChangeForm)form;
            PariteOfficielleId pariteOfficielleId = new PariteOfficielleId();
            PariteOfficielle pariteOfficielle = new PariteOfficielle();
            pariteOfficielleId.setCodDevDev(Long.valueOf(gestionCoursChangeForm.getCodeDevise()));
            pariteOfficielleId.setAnnee(Long.valueOf(gestionCoursChangeForm.getDateJourCours()));
            pariteOfficielle.setPariteOfficielleId(pariteOfficielleId);

            pariteOfficielle.setMontCoursPaof(Double.valueOf(gestionCoursChangeForm.getMontPariteOff()));

            Personnel pers = new Personnel();
            pers.setNumMatrUser(paramAgence.getNumMatrUser());

            Tache tache = new Tache();
            TacheId tacheid = new TacheId();

            if (gestionCoursChangeForm.getTypeOperation().equalsIgnoreCase("insert")) {
                tacheid.setCodOperOper(Constants.COD_OPER_INSERT_COURS_CHANGE);
                tacheid.setCodTachTach(Constants.COD_TACH_PEC_COURS_CHANGE);

                pariteOfficielle.setCodEtatPaof("A");
                InsertPariteOffCmd insertPariteOffCmd = 
                    new InsertPariteOffCmd();
                pariteOfficielle = 
                        (PariteOfficielle)insertPariteOffCmd.execute(pariteOfficielle);

            } else if (gestionCoursChangeForm.getTypeOperation().equalsIgnoreCase("update") || 
                       gestionCoursChangeForm.getTypeOperation().equalsIgnoreCase("validationPoff")) {
                tacheid.setCodOperOper(Constants.COD_OPER_INSERT_COURS_CHANGE);
                tacheid.setCodTachTach(Constants.COD_TACH_PEC_COURS_CHANGE);
                pariteOfficielle.setCodEtatPaof("A");
                UpdatePariteOffCmd updatePariteOffCmd = 
                    new UpdatePariteOffCmd();
                pariteOfficielle = 
                        (PariteOfficielle)updatePariteOffCmd.execute(pariteOfficielle);
            }

            // insertion de la trace cours change que pour les cas d'insertion ou de mise à jour // la validation sera inséré en bloc plus tard pour tous les devises validés
            if (gestionCoursChangeForm.getTypeOperation().equalsIgnoreCase("insert") || 
                gestionCoursChangeForm.getTypeOperation().equalsIgnoreCase("update")) {
                TracePariteOfficielle tracePariteOfficielle = 
                    new TracePariteOfficielle();
                tache.setTacheId(tacheid);
                tracePariteOfficielle.setPariteOfficielle(pariteOfficielle);
                tracePariteOfficielle.setPersonnel(pers);
                tracePariteOfficielle.setDatOperPaof(new Date());
                tracePariteOfficielle.setTache(tache);
                InsertTracePariteOffCmd insertTracePariteOffCmd = 
                    new InsertTracePariteOffCmd();
                tracePariteOfficielle = 
                        (TracePariteOfficielle)insertTracePariteOffCmd.execute(tracePariteOfficielle);
            }

            return mapping.findForward("successPaoff");
        } catch (Exception e) {
            com.oxia.fwk.core.Error erreur = new com.oxia.fwk.core.Error();
            StringBuffer text = 
                new StringBuffer("Erreur au niveau de gererCoursChange, Veuillez transmettre ce message à l'équipe informatique: ");
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


    public ActionForward validerListeCChange(ActionMapping mapping, 
                                             ActionForm form, 
                                             HttpServletRequest request, 
                                             HttpServletResponse response) throws IOException, 
                                                                                  ServletException {

        ActionMessages actionMessages = new ActionMessages();
        Context context = ContextHandler.getContext();
        GestionCoursChangeForm gestionCoursChangeForm = 
            (GestionCoursChangeForm)form;
        try {
            ParamAgence paramAgence = 
                (ParamAgence)request.getSession().getAttribute("paramAgBNA");
            GetCoursChangeCmd getCoursChangeCmd = new GetCoursChangeCmd();
            UpdateCoursChangeCmd updateCoursChangeCmd = 
                new UpdateCoursChangeCmd();
            for (Iterator it = 
                 gestionCoursChangeForm.getListeCoursDevisesView().iterator(); 
                 it.hasNext(); ) {
                CoursChangeView coursChangeView = (CoursChangeView)it.next();
                CoursChangeId coursChangeId = new CoursChangeId();
                coursChangeId.setCodDevDev(Long.valueOf(coursChangeView.getCodDevise()));
                coursChangeId.setDatJourCchn(DateHandler.strToDate(coursChangeView.getDatCoursChange()));
                CoursChange coursChange = 
                    (CoursChange)getCoursChangeCmd.execute(coursChangeId);
                coursChange.setCodEtatCchn("V");

                coursChange = 
                        (CoursChange)updateCoursChangeCmd.execute(coursChange);
                // insertion de la trace
                TraceCoursChange traceCoursChange = new TraceCoursChange();
                Tache tache = new Tache();
                TacheId tacheid = new TacheId();

                Personnel pers = new Personnel();
                pers.setNumMatrUser(paramAgence.getNumMatrUser());
                tacheid.setCodOperOper(Constants.COD_OPER_INSERT_COURS_CHANGE);
                tacheid.setCodTachTach(Constants.COD_TACH_VLD_COURS_CHANGE);
                           
                tache.setTacheId(tacheid);

                traceCoursChange.setCoursChange(coursChange);
                traceCoursChange.setPersonnel(pers);
                traceCoursChange.setDatOperTrcc(new Date());
                traceCoursChange.setTache(tache);

                InsertTraceCoursChangeCmd insertTraceCoursChangeCmd = 
                    new InsertTraceCoursChangeCmd();
                traceCoursChange = 
                        (TraceCoursChange)insertTraceCoursChangeCmd.execute(traceCoursChange);

            }

            return mapping.findForward("success");

        } catch (Exception e) {
            com.oxia.fwk.core.Error erreur = new com.oxia.fwk.core.Error();
            StringBuffer text = 
                new StringBuffer("Erreur au niveau de validerListeCChange, Veuillez transmettre ce message à l'équipe informatique: ");
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


    public ActionForward validerListepariteOfficielle(ActionMapping mapping, 
                                                      ActionForm form, 
                                                      HttpServletRequest request, 
                                                      HttpServletResponse response) throws IOException, 
                                                                                           ServletException {

        ActionMessages actionMessages = new ActionMessages();
        Context context = ContextHandler.getContext();
        GestionCoursChangeForm gestionCoursChangeForm = 
            (GestionCoursChangeForm)form;
        try {
            ParamAgence paramAgence = 
                (ParamAgence)request.getSession().getAttribute("paramAgBNA");
            GetCoursPariteOffCmd getCoursPariteOffCmd = 
                new GetCoursPariteOffCmd();
            UpdatePariteOffCmd updatePariteOffCmd = new UpdatePariteOffCmd();
            for (Iterator it = 
                 gestionCoursChangeForm.getListeParitOffView().iterator(); 
                 it.hasNext(); ) {
                PariteOfficielleView pariteOfficielleView = 
                    (PariteOfficielleView)it.next();
                PariteOfficielleId pariteOfficielleId = 
                    new PariteOfficielleId();
                pariteOfficielleId.setCodDevDev(Long.valueOf(pariteOfficielleView.getCodedevise()));
                pariteOfficielleId.setAnnee(Long.valueOf(pariteOfficielleView.getAnnee()));
                PariteOfficielle pariteOfficielle = 
                    (PariteOfficielle)getCoursPariteOffCmd.execute(pariteOfficielleId);
                pariteOfficielle.setCodEtatPaof("V");

                pariteOfficielle = 
                        (PariteOfficielle)updatePariteOffCmd.execute(pariteOfficielle);
                // insertion de la trace
                TracePariteOfficielle tracePariteOfficielle = 
                    new TracePariteOfficielle();
                Tache tache = new Tache();
                TacheId tacheid = new TacheId();

                Personnel pers = new Personnel();
                pers.setNumMatrUser(paramAgence.getNumMatrUser());

                tacheid.setCodOperOper(Constants.COD_OPER_INSERT_COURS_CHANGE);
                tacheid.setCodTachTach(Constants.COD_TACH_VLD_COURS_CHANGE);
                tache.setTacheId(tacheid);

                tracePariteOfficielle.setPariteOfficielle(pariteOfficielle);
                tracePariteOfficielle.setPersonnel(pers);
                tracePariteOfficielle.setDatOperPaof(new Date());
                tracePariteOfficielle.setTache(tache);

                InsertTracePariteOffCmd insertTracePariteOffCmd = 
                    new InsertTracePariteOffCmd();
                tracePariteOfficielle = 
                        (TracePariteOfficielle)insertTracePariteOffCmd.execute(tracePariteOfficielle);

            }

            return mapping.findForward("successPaoff");

        } catch (Exception e) {
            com.oxia.fwk.core.Error erreur = new com.oxia.fwk.core.Error();
            StringBuffer text = 
                new StringBuffer("Erreur au niveau de validerListeCChange, Veuillez transmettre ce message à l'équipe informatique: ");
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
     public ActionForward imprimerCourchange(ActionMapping mapping, 
                                               ActionForm form, 
                                               HttpServletRequest request, 
                                               HttpServletResponse response) throws IOException, 
                                                                                    ServletException {
                                                                                    
                                                                                    
        Context context = ContextHandler.getContext();
        GestionCoursChangeForm gestionCoursChangeForm = 
            (GestionCoursChangeForm)form;
        ActionMessages actionMessages = new ActionMessages();
        
        CommonReportVO valueObject=new CommonReportVO();
        Map parameters = new HashMap();
        
        try {
            ParamAgence paramAgence = (ParamAgence)request.getSession().getAttribute("paramAgBNA");
            valueObject.setNomReport("consultCourChange");
            parameters.put("P_LIB_ETAT", "Cours B.B.E et T.C");
            parameters.put("P_NUM_MATR_USER",  paramAgence.getNumMatrUser());
            parameters.put("P_DAT_COUR",gestionCoursChangeForm.getDateJournee());
            valueObject.setNomDossier("Change");
            valueObject.setParams(parameters);
            request.getSession().setAttribute("CommonPrintVo",valueObject);
            request.setAttribute("print","1");
            return mapping.findForward("consult");
        } catch (Exception e) {
            com.oxia.fwk.core.Error erreur = new com.oxia.fwk.core.Error();
            StringBuffer text = 
                new StringBuffer("Erreur au niveau de imprimerCourchange, Veuillez transmettre ce message à l'équipe informatique: ");
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

}
