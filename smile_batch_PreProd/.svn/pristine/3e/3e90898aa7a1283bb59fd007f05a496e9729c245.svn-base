package com.bna.smile.web.moyenPaiement.consultationVirement.actions;

import com.bna.commun.util.ContextHandler;
import com.bna.commun.util.DateHandler;
import com.bna.smile.model.moyenPayement.commande.GetVirementByStructureDateCmd;
import com.bna.smile.model.moyenPayement.model.ParamVirement;
import com.bna.smile.model.moyenPayement.model.Virement;
import com.bna.smile.model.reporting.model.CommonReportVO;
import com.bna.smile.web.commun.model.ParamAgence;
import com.bna.smile.web.moyenPaiement.consultationVirement.forms.ConsultationVirementForm;

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


public class ConsultationVirementAction extends DispatchAction {
    private static final Logger logger = Logger.getLogger(ConsultationVirementAction.class);

    public ActionForward initierPage(ActionMapping mapping, ActionForm form, 
                                     HttpServletRequest request, 
                                     HttpServletResponse response) throws IOException, 
                                                                          ServletException {
        try {

            ParamAgence paramAgence = 
                (ParamAgence)request.getSession().getAttribute("paramAgBNA"); /// structure de l'agent 
            Context context = ContextHandler.getContext();
            ConsultationVirementForm consultationVirementForm = 
                (ConsultationVirementForm)form;
            consultationVirementForm.setLibelleOperation("Consultation des virements Agence");
            consultationVirementForm.setDateJournee(paramAgence.getDateComptable());
            consultationVirementForm.initialiser();
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

    public ActionForward consulterVirements(ActionMapping mapping, 
                                            ActionForm form, 
                                            HttpServletRequest request, 
                                            HttpServletResponse response) throws IOException, 
                                                                                 ServletException {
        try {

            ParamAgence paramAgence = 
                (ParamAgence)request.getSession().getAttribute("paramAgBNA"); /// structure de l'agent 
            ConsultationVirementForm consultationVirementForm = 
                (ConsultationVirementForm)form;
            consultationVirementForm.setMessage("");
            ParamVirement paramVirement = new ParamVirement();

            if (consultationVirementForm.getDateJournee() == null || 
                consultationVirementForm.getDateJournee().equals("")) {
                consultationVirementForm.initialiser();
                consultationVirementForm.setMessage("dateNonRenseigne");
                return mapping.findForward("success");
            }
            Date dateJour = 
                DateHandler.strToDate(consultationVirementForm.getDateJournee());
            Date dateComptable = 
                DateHandler.strToDate(paramAgence.getDateComptable());
            if (dateJour.before(dateComptable) || 
                (dateJour.equals(dateComptable))) {
                paramVirement.setCodeStructure(paramAgence.getCodStrcStrc().toString());
                paramVirement.setDateJourneeComptable(DateHandler.strToDate(consultationVirementForm.getDateJournee()));
                GetVirementByStructureDateCmd getVirementByStructureDateCmd = new GetVirementByStructureDateCmd();
                paramVirement = 
                        (ParamVirement)getVirementByStructureDateCmd.execute((IValueObject)paramVirement);
                List<Virement> listeVirements = new ArrayList<Virement>();
                listeVirements = paramVirement.getListeVirements();
                consultationVirementForm.setListeVirements(listeVirements);
            } else {
                consultationVirementForm.initialiser();
                consultationVirementForm.setMessage("dateInferieure");
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

            ConsultationVirementForm consultationVirementForm = 
                (ConsultationVirementForm)form;
            Date dateJour = 
                DateHandler.strToDate(consultationVirementForm.getDateJournee());
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
                String vdateOperation = 
                    consultationVirementForm.getDateJournee();
                String vLibEtat = "Liste des virements télécompensés reçus";
              // String vcodeStructure = 
                   //consultationVirementForm.getListeVirements().get(0).toString();
                String vcodeStructure = paramAgence.getCodStrcStrc().toString();
             
                String vcodeBCT = consultationVirementForm.getListeVirements().get(0).getCodeBct().toString();
                   System.out.println(vcodeBCT);
                String nomRapport = "VirementParAgence";
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
                consultationVirementForm.initialiser();
                consultationVirementForm.setMessage("dateInferieure");
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

