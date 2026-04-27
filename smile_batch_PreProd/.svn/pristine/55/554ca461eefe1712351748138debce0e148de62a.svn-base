package com.bna.smile.web.moyenPaiement.gestionAccuse.actions;


import com.bna.commun.model.Consultation;
import com.bna.commun.model.Personnel;
import com.bna.commun.util.ContextHandler;


import com.bna.commun.util.DateHandler;
import com.bna.smile.model.moyenPayement.commande.GetAccuseByStructureDateCmd;
import com.bna.smile.model.moyenPayement.commande.InsertRefConsultationCmd;
import com.bna.smile.model.moyenPayement.model.Accuse;
import com.bna.smile.model.moyenPayement.model.ParamAccuse;
import com.bna.smile.model.reporting.model.CommonReportVO;
import com.bna.smile.web.commun.model.ParamAgence;


import com.bna.smile.web.moyenPaiement.consultationVirement.actions.ConsultationVirementAction;
import com.bna.smile.web.moyenPaiement.gestionAccuse.forms.ConsultationAccusesForm;

import com.oxia.fwk.context.Context;

import com.oxia.fwk.core.IValueObject;

import com.oxia.fwk.core.User;

import java.io.IOException;

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

public class ConsultationAccusesAction extends DispatchAction {
    private static final Logger logger = Logger.getLogger(ConsultationAccusesAction.class);
    public ActionForward initierPage(ActionMapping mapping, ActionForm form, 
                                     HttpServletRequest request, 
                                     HttpServletResponse response) throws IOException, 
                                                                          ServletException {
        try{
            
            ParamAgence paramAgence = 
                    (ParamAgence)request.getSession().getAttribute("paramAgBNA"); /// structure de l'agent 
            Context context = ContextHandler.getContext();
            ConsultationAccusesForm consultationAccusesForm=(ConsultationAccusesForm)form;
            consultationAccusesForm.setLibelleOperation("Recap du flux des valeurs télécompensées reçues de l'agence");
            consultationAccusesForm.setDateJournee(paramAgence.getDateComptable());
            consultationAccusesForm.initialiser();
            return mapping.findForward("success");
            
            } catch (Exception e) {
                ActionMessages actionMessages = new ActionMessages();
                ActionMessage actionMessage = 
                    new ActionMessage("exception.generique", 
                                      e.getMessage());
                actionMessages.add("Erreur ", actionMessage);   
                this.saveMessages(request, actionMessages);
                logger.error("Exception : ",e);
                return mapping.findForward("error");
            }
    }
    public ActionForward consulterAccuses(ActionMapping mapping, ActionForm form, 
                                     HttpServletRequest request, 
                                     HttpServletResponse response) throws IOException, 
                                                                          ServletException {
        try{
            
            ParamAgence paramAgence = (ParamAgence)request.getSession().getAttribute("paramAgBNA"); /// structure de l'agent 
            ConsultationAccusesForm consultationAccusesForm=(ConsultationAccusesForm)form;
            consultationAccusesForm.setMessage("");
            ParamAccuse paramAccuse= new ParamAccuse();
            
            if (consultationAccusesForm.getDateJournee()==null||consultationAccusesForm.getDateJournee().equals("")) {
                consultationAccusesForm.initialiser();
                consultationAccusesForm.setMessage("dateNonRenseigne");
                return mapping.findForward("success");
            }
            Date dateJour=DateHandler.strToDate(consultationAccusesForm.getDateJournee());
                Date dateComptable = 
                    DateHandler.strToDate(paramAgence.getDateComptable());
                if (dateJour.before(dateComptable) || 
                    (dateJour.equals(dateComptable))) {
            paramAccuse.setCodeStructure(paramAgence.getCodStrcStrc());
            paramAccuse.setDateJourneeComptable(dateJour);
            GetAccuseByStructureDateCmd getAccuseByStructureDateCmd=new GetAccuseByStructureDateCmd();
            paramAccuse=(ParamAccuse)getAccuseByStructureDateCmd.execute((IValueObject)paramAccuse);
            List<Accuse> listeAccuse=paramAccuse.getListeAccusee();
            consultationAccusesForm.setListeAccuses(listeAccuse);
            }else{
            consultationAccusesForm.initialiser();
            consultationAccusesForm.setMessage("dateInferieure");
            }
            return mapping.findForward("success");
            }catch (Exception e) {
                ActionMessages actionMessages = new ActionMessages();
                ActionMessage actionMessage = 
                    new ActionMessage("exception.generique", 
                                      e.getMessage());
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
        try{
            
            ParamAgence paramAgence = (ParamAgence)request.getSession().getAttribute("paramAgBNA"); /// structure de l'agent 
            ConsultationAccusesForm consultationAccusesForm=(ConsultationAccusesForm)form;
            Date dateJour=DateHandler.strToDate(consultationAccusesForm.getDateJournee());
            Date dateComptable = DateHandler.strToDate(paramAgence.getDateComptable()); 
            if (dateJour.before(dateComptable) || (dateJour.equals(dateComptable))) {
            
            CommonReportVO valueObject=new CommonReportVO();
            Map parameters = new HashMap();
            String pLibEtat = "P_LIB_ETAT";
            String pMatrUser = "P_NUM_MATR_USER";
            String pcodeStructure="P_COD_STRC_STRC";
            String pdateChargement="P_DATE_JOURNEE";
            String pRefConsultation="P_REF_CONSULTATION";
            String pcodeBCT ="P_COD_BCT_STRC";
            String vRefConsultation="";
            String vMatrUser=paramAgence.getNumMatrUser();
            String vdateCharegement=consultationAccusesForm.getDateJournee();
            String vLibEtat="Recap du flux des valeurs télécompensées reçues de l'agence";
            String vcodeStructure=paramAgence.getCodStrcStrc().toString();
            String vcodeBCT = consultationAccusesForm.getListeAccuses().get(0).getCodeBct().toString();
            String nomRapport="AccuseADT";
            parameters.put(pMatrUser,vMatrUser);
            parameters.put(pLibEtat,vLibEtat);
            parameters.put(pdateChargement,vdateCharegement);
            parameters.put(pcodeStructure,vcodeStructure);
            parameters.put(pcodeBCT, vcodeBCT);
            valueObject.setNomDossier("MoyensPayement");
            valueObject.setNomReport(nomRapport);
            /****************************************************/
                ParamAccuse paramAccuse= new ParamAccuse();
                paramAccuse.setNumMatrUser(vMatrUser);
                paramAccuse.setNomRapport(nomRapport);
                paramAccuse.setDateJourneeComptable(DateHandler.strToDate(consultationAccusesForm.getDateJournee()) );
                InsertRefConsultationCmd insertRefConsultationCmd=new InsertRefConsultationCmd();
                paramAccuse=(ParamAccuse)insertRefConsultationCmd.execute((IValueObject) paramAccuse);
            /****************************************************/
            vRefConsultation=paramAccuse.getRefConsultation().toString();
            parameters.put(pRefConsultation,vRefConsultation);
            valueObject.setParams(parameters);
            request.getSession().setAttribute("CommonPrintVo",valueObject);
            request.setAttribute("print","1");
            }
            else{
             consultationAccusesForm.initialiser();
             consultationAccusesForm.setMessage("dateInferieure");
            }
            return mapping.findForward("success");
            }catch (Exception e) {
                ActionMessages actionMessages = new ActionMessages();
                ActionMessage actionMessage = 
                    new ActionMessage("exception.generique", 
                                      e.getMessage());
                actionMessages.add("Erreur ", actionMessage);   
                this.saveMessages(request, actionMessages);
                logger.error("Exception : ",e);
                return mapping.findForward("error");
            }
    }
}
