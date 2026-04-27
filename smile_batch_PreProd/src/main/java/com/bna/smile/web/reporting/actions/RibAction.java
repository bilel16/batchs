package com.bna.smile.web.reporting.actions;

import com.bna.commun.model.ContratCpt;
import com.bna.commun.model.ContratCptId;
import com.bna.commun.util.ContextHandler;
import com.bna.commun.vo.PrimitiveVO;
import com.bna.smile.web.reporting.forms.ReportRIBForm;
import com.bna.smile.model.domainecommun.commande.*;

import java.sql.Connection;

import java.io.IOException;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.jasperreports.engine.JasperRunManager;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import org.apache.commons.dbcp.BasicDataSource;

import com.oxia.fwk.context.Context;

import com.bna.smile.web.commun.model.ParamAgence;

import javax.servlet.ServletException;

import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.actions.DispatchAction;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import java.sql.Connection;

import java.util.HashMap;
import java.util.Map;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperRunManager;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DownloadAction;
import org.apache.commons.dbcp.BasicDataSource;

import com.oxia.fwk.context.Context;

import com.bna.commun.util.ContextHandler;

import com.bna.commun.util.ContextHandler;

import com.bna.smile.model.constant.Constants;
import com.bna.smile.model.reporting.commande.PrinterCmd;
import com.bna.smile.model.reporting.model.CommonReportVO;
import com.bna.smile.web.souscription.actions.SouscriptionContratCompteAction;
import com.bna.smile.web.souscription.actions.reporting.PrintReportAction;



import org.apache.log4j.Logger;
import org.apache.struts.action.ActionMessage;

public class RibAction extends DispatchAction {

    private static final Logger logger = Logger.getLogger(RibAction.class);
    
    public ActionForward initierPage(ActionMapping mapping, ActionForm form, 
                                     HttpServletRequest request, 
                                     HttpServletResponse response) throws IOException, 
                                                                          ServletException {
        ReportRIBForm RIBForm = (ReportRIBForm)form;
        ActionMessages actionMessages = new ActionMessages();
        try {
            
         RIBForm.clearRibForm();
         return mapping.findForward("success");
         
        } catch (Exception e) {
            com.oxia.fwk.core.Error erreur = new com.oxia.fwk.core.Error();
            StringBuffer text = 
                new StringBuffer("L'initialisation de la consultation du RIB / IBAN du contrat compte a été interrompue, veuillez transmettre ce message à l'équipe informatique: ");
            text.append(". Exception :"); text.append(e.toString());
            erreur.setCode("200");
            erreur.setDescription(text.toString());
            logger.error(text.toString(),e);   
            ActionMessage actionMessage = 
                new ActionMessage("exception.generique", 
                                  erreur.getDescription());
            actionMessages.add("Erreur ", actionMessage);
            this.saveMessages(request, actionMessages);
            return mapping.findForward("error");
        }

    }

    public ActionForward verifRibByContrat(ActionMapping mapping, 
                                           ActionForm form, 
                                           HttpServletRequest request, 
                                           HttpServletResponse response) throws IOException, 
                                                                                ServletException {
        //ValueObject vo;
        ContratCpt contrat = new ContratCpt();
        ContratCptId idContrat = new ContratCptId();
        ReportRIBForm RIBForm = (ReportRIBForm)form;
        ActionMessages actionMessages = new ActionMessages();
        try {
            GetRibCmd getRibCmd = new GetRibCmd();
            idContrat.setCodStrcStrc(new Long(RIBForm.getCodStrcStrc()));
            idContrat.setCodPrdPrd(new Long(RIBForm.getCodPrdPrd()));
            idContrat.setNumCcptCcpt(new Long(RIBForm.getNumCcptCcpt()));
            
            ParamAgence paramAgence = new ParamAgence();
            paramAgence = (ParamAgence)request.getSession().getAttribute("paramAgBNA");
            GetContratCptByIdCmd getContratCptByIdCmd = new GetContratCptByIdCmd();
            contrat.setContratCptId(idContrat);
            contrat = (ContratCpt)getContratCptByIdCmd.execute(contrat);
            if (contrat != null) {
            if(contrat.getNomIntiCcpt() != null){
                RIBForm.setNomPrnPers(contrat.getNomIntiCcpt());
            }else {
                RIBForm.setNomPrnPers(contrat.getClient().getPersonne().getNomRsPers());
            }
                RIBForm.setEtatCcpt(contrat.getCodEtatCcpt()); 
                if(contrat.getCodEtatCcpt().equals(Constants.COD_ETAT_CPT_VALID)){
                    RIBForm.setAlertContrat("ContratValide");
                    }else {
                        RIBForm.setAlertContrat("ContratNonvalide");
                    }
                PrimitiveVO rib = (PrimitiveVO)(getRibCmd.execute(contrat));
                RIBForm.setRib(rib.getVString());
                RIBForm.setIban("TN59"+RIBForm.getRib());
                RIBForm.setLibStrc(contrat.getStructure().getLibStrcStrc());
                RIBForm.setNumMatrUser(paramAgence.getNumMatrUser().toString());
            }
            //ActionMessages actionMessages = new ActionMessages();
           
            return mapping.findForward("success");
        } catch (Exception e) {
            com.oxia.fwk.core.Error erreur = new com.oxia.fwk.core.Error();
            StringBuffer text = 
                new StringBuffer("La vérification du RIB / IBAN du contrat compte a été interrompue, veuillez transmettre ce message à l'équipe informatique: ");
            text.append(". Exception :"); text.append(e.toString());
            erreur.setCode("200");
            erreur.setDescription(text.toString());
            logger.error(text.toString(),e);   
            ActionMessage actionMessage = 
                new ActionMessage("exception.generique", 
                                  erreur.getDescription());
            actionMessages.add("Erreur ", actionMessage);
            this.saveMessages(request, actionMessages);
            return mapping.findForward("error");
        }
    }


    public ActionForward print(ActionMapping mapping, ActionForm form, 
                                     HttpServletRequest request, 
                                     HttpServletResponse response) throws IOException, 
                                                                          ServletException {
        CommonReportVO valueObject=new CommonReportVO();
        ActionMessages actionMessages = new ActionMessages();
        try {
            ReportRIBForm RIBForm = (ReportRIBForm)form;
            if(RIBForm.getEtatCcpt().equals(Constants.COD_ETAT_CPT_VALID)){
            ParamAgence paramAgence = new ParamAgence();
            paramAgence = (ParamAgence)request.getSession().getAttribute("paramAgBNA");
            Map parameters = new HashMap();
            String pNomNomPers = "P_PRN_USER";
            String pNomPrnPers = "P_NOM_USER";
            String pLibStrc = "P_LIB_STRC";
            String pRib = "P_RIB";
            String pLibEtat = "P_LIB_ETAT";
            String pMatrUser = "P_NUM_MATR_USER";
            String vNomNomPers = RIBForm.getNomPrnPers();
            String vNomPrnPers = "";
            String vLibStrc = RIBForm.getLibStrc();
            String vRib = RIBForm.getRib();
            String vLibEtat="";
            String vMatrUser = paramAgence.getNumMatrUser().toString();
 
            parameters.put(pMatrUser, vMatrUser);
           
            parameters.put(pRib, vRib);
            parameters.put(pLibStrc, vLibStrc);
            parameters.put(pNomNomPers, vNomNomPers);
            parameters.put(pNomPrnPers, vNomPrnPers);
            vLibEtat = "IDENTITE BANCAIRE ";
            valueObject.setNomReport("RibIban");
            
            parameters.put(pLibEtat, vLibEtat);
            valueObject.setParams(parameters);
            request.getSession().setAttribute("CommonPrintVo",valueObject);
            request.setAttribute("print","1");
           }else{
                   RIBForm.setAlertContrat("ContratNonvalide");
            }
               return mapping.findForward("success");
        } catch (Exception e) {
            com.oxia.fwk.core.Error erreur = new com.oxia.fwk.core.Error();
            StringBuffer text = 
                new StringBuffer("L'impression du RIB / IBAN du contrat compte a été interrompue, veuillez transmettre ce message à l'équipe informatique: ");
            text.append(". Exception :"); text.append(e.toString());
            erreur.setCode("200");
            erreur.setDescription(text.toString());
            logger.error(text.toString(),e);   
            ActionMessage actionMessage = 
                new ActionMessage("exception.generique", 
                                  erreur.getDescription());
            actionMessages.add("Erreur ", actionMessage);
            this.saveMessages(request, actionMessages);
            return mapping.findForward("error");
        }
    }




}
