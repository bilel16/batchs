package com.bna.smile.web.moyenPaiement.consultationPrelevement.actions;

import com.bna.commun.util.ContextHandler;
import com.bna.commun.util.DateHandler;
import com.bna.smile.model.moyenPayement.commande.GetPrelevementByStructureDateCmd;
import com.bna.smile.model.moyenPayement.model.ParamPrelevement;
import com.bna.smile.model.moyenPayement.model.Prelevement;
import com.bna.smile.model.reporting.model.CommonReportVO;
import com.bna.smile.web.commun.model.ParamAgence;
import com.bna.smile.web.moyenPaiement.consultationPrelevement.forms.ConsultationPrelevementForm;

import com.oxia.fwk.context.Context;
import com.oxia.fwk.core.IValueObject;

import java.io.IOException;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
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


public class ConsultationPrelevementAction extends DispatchAction{
    private static final Logger logger = Logger.getLogger(ConsultationPrelevementAction.class);

    public ActionForward initierPage(ActionMapping mapping, ActionForm form, 
                                     HttpServletRequest request, 
                                     HttpServletResponse response) throws IOException, 
                                                                          ServletException {
        try {

            ParamAgence paramAgence = 
                (ParamAgence)request.getSession().getAttribute("paramAgBNA"); /// structure de l'agent 
            Context context = ContextHandler.getContext();
            ConsultationPrelevementForm consultationPrelevementForm = 
                (ConsultationPrelevementForm)form;
            consultationPrelevementForm.setLibelleOperation("Consultation des prélèvements Agence");
            consultationPrelevementForm.setDateJournee(paramAgence.getDateComptable());
            consultationPrelevementForm.initialiser();
            return mapping.findForward("success");
        } catch (Exception e) {
            ActionMessages actionMessages = new ActionMessages();
            ActionMessage actionMessage = 
                new ActionMessage("exception.generique", e.getMessage());
            actionMessages.add("Erreur ", actionMessage);
            this.saveMessages(request, actionMessages);
            logger.error("Exception : ",e);
            return mapping.findForward("error");
        }
    }

    public ActionForward consulterPrelevements(ActionMapping mapping, 
                                            ActionForm form, 
                                            HttpServletRequest request, 
                                            HttpServletResponse response) throws IOException, 
                                                                                 ServletException {
        try {

            ParamAgence paramAgence = 
                (ParamAgence)request.getSession().getAttribute("paramAgBNA"); /// structure de l'agent 
            ConsultationPrelevementForm consultationPrelevementForm = 
                (ConsultationPrelevementForm)form;
            consultationPrelevementForm.setMessage("");
            ParamPrelevement paramPrelevement = new ParamPrelevement();

            if (consultationPrelevementForm.getDateJournee() == null || 
                consultationPrelevementForm.getDateJournee().equals("")) {
                consultationPrelevementForm.initialiser();
                consultationPrelevementForm.setMessage("dateNonRenseigne");
                return mapping.findForward("success");
            }
            Date dateJour = 
                DateHandler.strToDate(consultationPrelevementForm.getDateJournee());
            Date dateComptable = 
                DateHandler.strToDate(paramAgence.getDateComptable());
            if (dateJour.before(dateComptable) || 
                (dateJour.equals(dateComptable))) {
                paramPrelevement.setCodeStructure(paramAgence.getCodStrcStrc().toString());
                paramPrelevement.setDateJourneeComptable(DateHandler.strToDate(consultationPrelevementForm.getDateJournee()));
                GetPrelevementByStructureDateCmd getPrelevementByStructureDateCmd = new GetPrelevementByStructureDateCmd();
                paramPrelevement = 
                        (ParamPrelevement)getPrelevementByStructureDateCmd.execute((IValueObject)paramPrelevement);
                List<Prelevement> listePrelevements = new ArrayList<Prelevement>();
                listePrelevements = paramPrelevement.getListePrelevements();
                consultationPrelevementForm.setListePrelevements(listePrelevements);
            } else {
                consultationPrelevementForm.initialiser();
                consultationPrelevementForm.setMessage("dateInferieure");
            }
            return mapping.findForward("success");
        } catch (Exception e) {
            ActionMessages actionMessages = new ActionMessages();
            ActionMessage actionMessage = 
                new ActionMessage("exception.generique", e.getMessage());
            actionMessages.add("Erreur ", actionMessage);
            this.saveMessages(request, actionMessages);
            logger.error("Exception : ",e);
            return mapping.findForward("error");
        }
    }

    public ActionForward print(ActionMapping mapping, ActionForm form, 
                               HttpServletRequest request, 
                               HttpServletResponse response) throws IOException, 
                                                                    ServletException {
        try {

            ParamAgence paramAgence = 
                (ParamAgence)request.getSession().getAttribute("paramAgBNA"); /// structure de l'agent 

            ConsultationPrelevementForm ConsultationPrelevementForm = 
                (ConsultationPrelevementForm)form;
            Date dateJour = 
                DateHandler.strToDate(ConsultationPrelevementForm.getDateJournee());
            Date dateComptable = 
                DateHandler.strToDate(paramAgence.getDateComptable());
            if (dateJour.before(dateComptable) || 
                (dateJour.equals(dateComptable))) {

                CommonReportVO valueObject = new CommonReportVO();
                Map parameters = new HashMap();
                String pLibEtat = "P_LIB_ETAT";
                String pMatrUser = "P_NUM_MATR_USER";
                String pcodeStructure = "P_COD_STRC_STRC";
                String pdateOperation = "P_DATE_JOURNEE";
                String pcodeBCT ="P_COD_BCT_STRC";
                String vMatrUser = paramAgence.getNumMatrUser();
                String vdateOperation =  ConsultationPrelevementForm.getDateJournee();
                String vLibEtat = "Liste des prélèvements télécompensés reçus";
                String vcodeStructure = paramAgence.getCodStrcStrc().toString();
                String vcodeBCT = ConsultationPrelevementForm.getListePrelevements().get(0).getCodeBct().toString();
                String nomRapport = "PrelevementParAgence";
                parameters.put(pMatrUser, vMatrUser);
                parameters.put(pLibEtat, vLibEtat);
                parameters.put(pdateOperation, vdateOperation);
                parameters.put(pcodeStructure, vcodeStructure);
                parameters.put(pcodeBCT, vcodeBCT);
                valueObject.setNomDossier("MoyensPayement");
                valueObject.setNomReport(nomRapport);
                valueObject.setParams(parameters);
                request.getSession().setAttribute("CommonPrintVo", 
                                                  valueObject);
                request.setAttribute("print", "1");

            } else {
                ConsultationPrelevementForm.initialiser();
                ConsultationPrelevementForm.setMessage("dateInferieure");
            }
            return mapping.findForward("success");
        } catch (Exception e) {
            ActionMessages actionMessages = new ActionMessages();
            ActionMessage actionMessage = 
                new ActionMessage("exception.generique", e.getMessage());
            actionMessages.add("Erreur ", actionMessage);
            this.saveMessages(request, actionMessages);
            logger.error("Exception : ",e);
            return mapping.findForward("error");
        }
    }
}
