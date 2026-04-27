package com.bna.smile.web.souscription.actions;

import com.bna.commun.util.DateHandler;
import com.bna.smile.model.domainecommun.model.Listes;
import com.bna.smile.model.domainecontratcompte.moyensPaiement.commande.GetListOppositionCmd;
import com.bna.smile.model.domainecontratcompte.moyensPaiement.model.ParamRechercheOpposition;
import com.bna.smile.model.domainecontratcompte.souscriptioncontratcompte.commande.GetListeContratsRejetesCmd;
import com.bna.smile.model.reporting.model.CommonReportVO;
import com.bna.smile.web.commun.model.ParamAgence;

import com.bna.smile.web.commun.util.SessionUtil;
import com.bna.smile.web.souscription.forms.ConsultationCompteRejetesForm;
import com.bna.smile.web.souscription.forms.ConsultationContratCompteForm;

import java.io.IOException;

import java.util.HashMap;
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

public class ConsultationCompteRejetesAction extends DispatchAction {

    ParamAgence paramAgence = new ParamAgence();
    private static final Logger logger = Logger.getLogger(ConsultationCompteRejetesAction.class);
    
    public ActionForward initierPage(ActionMapping mapping, ActionForm form, 
                                     HttpServletRequest request, 
                                     HttpServletResponse response) throws IOException, 
                                                                          ServletException {
                                                                          
        ActionMessages actionMessages =   new ActionMessages();
    
     SessionUtil sessionUtil =new SessionUtil();
     //Suppression des anciens Bean de type Form de la session, SAUF "consultationCompteRejetesForm"
     sessionUtil.removeSession(request,"consultationCompteRejetesForm"); 
   
     ConsultationCompteRejetesForm consultationCompteRejetesForm = 
         (ConsultationCompteRejetesForm)form;
    
    try{
        paramAgence = 
                (ParamAgence)request.getSession().getAttribute("paramAgBNA"); /// structure de l'agent qui fait la saisie

        consultationCompteRejetesForm.clearForm();
        consultationCompteRejetesForm.getParamConsult().setCodStrcStrc(paramAgence.getCodStrcStrc().toString());
        consultationCompteRejetesForm.getParamConsult().setChoix("0");
            
    return mapping.findForward("success");
    } catch (Exception e) {
        com.oxia.fwk.core.Error erreur = new com.oxia.fwk.core.Error();
        StringBuffer text = 
            new StringBuffer("la transaction est Interrompu, une erreur dans ConsultationCompteRejetesAction / Dispatch Action :initierPage ");
        text.append(e.toString());
        erreur.setCode("200");
        erreur.setDescription(text.toString());
       
        ActionMessage actionMessage = new ActionMessage("exception.generique",erreur.getDescription());
        actionMessages.add("Erreur ", actionMessage);
        this.saveMessages(request, actionMessages);
        logger.error("Erreur au niveau de l'agence <<" +consultationCompteRejetesForm.getParamConsult().getCodStrcStrc()+ ">>. Exception : ",e);  
        return mapping.findForward("error");
    }
 }
 
    public ActionForward rechercherCcptRejetes(ActionMapping mapping, ActionForm form, 
                                     HttpServletRequest request, 
                                     HttpServletResponse response) throws IOException, 
                                                                          ServletException {
                                                                          
        ActionMessages actionMessages =   new ActionMessages();
     ConsultationCompteRejetesForm consultationCompteRejetesForm = 
         (ConsultationCompteRejetesForm)form;
     consultationCompteRejetesForm.setListCcptRejetes(null);
    try{
        ParamRechercheOpposition paramRecherche = new ParamRechercheOpposition(); 
        paramRecherche.setCodStrcStrc(Long.valueOf(consultationCompteRejetesForm.getParamConsult().getCodStrcStrc()));
        if(consultationCompteRejetesForm.getParamConsult().getChoix().equals("0")){
               paramRecherche.setCodPrdPrd(Long.valueOf(consultationCompteRejetesForm.getParamConsult().getCodPrdPrd()));
               paramRecherche.setNumCcptCcpt(Long.valueOf(consultationCompteRejetesForm.getParamConsult().getNumCcptCcpt()));
                   }else if(consultationCompteRejetesForm.getParamConsult().getChoix().equals("1")){
                                            paramRecherche.setDateDebutConsult(
                                                                     DateHandler.strToDate(consultationCompteRejetesForm.getParamConsult().getDateDebutconsult()));
                                            paramRecherche.setDateFinConsult(DateHandler.addJour(
                                                                     DateHandler.strToDate(consultationCompteRejetesForm.getParamConsult().getDateFinconsult()),1));
                                          }
                         
         GetListeContratsRejetesCmd getListeContratsRejetesCmd= new GetListeContratsRejetesCmd();
               Listes l = new Listes();
               l = (Listes)getListeContratsRejetesCmd.execute(paramRecherche);
            if((l != null)&& l.getList().size()!=0){
              consultationCompteRejetesForm.setListCcptRejetes(l.getList());
            }
        
    return mapping.findForward("success");
    } catch (Exception e) {
        com.oxia.fwk.core.Error erreur = new com.oxia.fwk.core.Error();
        StringBuffer text = 
            new StringBuffer("la transaction est Interrompu, une erreur dans ConsultationCompteRejetesAction / Dispatch Action :rechercherCcptRejetes ");
        text.append(e.toString());
        erreur.setCode("200");
        erreur.setDescription(text.toString());
       
        ActionMessage actionMessage = new ActionMessage("exception.generique",erreur.getDescription());
        actionMessages.add("Erreur ", actionMessage);
        this.saveMessages(request, actionMessages);
        logger.error("Erreur au niveau de l'agence <<" +consultationCompteRejetesForm.getParamConsult().getCodStrcStrc()+ ">>. Exception : ",e);  
        return mapping.findForward("error");
    }
 }
    public ActionForward imprimerCcptRejetes(ActionMapping mapping, ActionForm form, 
                                     HttpServletRequest request, 
                                     HttpServletResponse response) throws IOException, 
                                                                          ServletException {
                                                                          
        ActionMessages actionMessages =   new ActionMessages();
     ConsultationCompteRejetesForm consultationCompteRejetesForm = 
         (ConsultationCompteRejetesForm)form;
    try{
        CommonReportVO valueObject = new CommonReportVO();
        Map parameters = new HashMap();
        
        /*---------------------------------------------------------------------*/
         String pCodStrcStrc = "P_COD_STRC_STRC";
         String vCodStrcStrc = consultationCompteRejetesForm.getParamConsult().getCodStrcStrc();
         parameters.put(pCodStrcStrc, vCodStrcStrc);

        /*------------------------------------------------------------------*/
        String pDateDeb = "P_DATE_DEB";
        String pDateFin = "P_DATE_FIN";
        String vDateFin="";
        String vDateDeb="";
        /*-----------------------------------------------------------------*/
         String pMatrUser = "P_NUM_MATR_USER";
         String vMatrUser = paramAgence.getNumMatrUser().toString();
        parameters.put(pMatrUser, vMatrUser);
        
         String pLibEtat="P_LIB_ETAT";
         String vLibEtat="Listes des contrats compte rejetés";
         parameters.put(pLibEtat, vLibEtat);
         
         vDateDeb = consultationCompteRejetesForm.getParamConsult().getDateDebutconsult();
         vDateFin = consultationCompteRejetesForm.getParamConsult().getDateFinconsult();
         parameters.put(pDateDeb, vDateDeb);
         parameters.put(pDateFin, vDateFin);
         valueObject.setNomReport("contratRejete");   
        

        valueObject.setParams(parameters);
        
        request.getSession().setAttribute("CommonPrintVo",valueObject);
        //------------l'attribut "print" prend la valeur 1 pour indiquer que le nombre de fois que le fichier sera imprimé est 1 (test effectué au niveau de la JSP : if((print!=null) && (print.equals("1")))...)
        request.setAttribute("print","1");    
    return mapping.findForward("success");
    } catch (Exception e) {
        com.oxia.fwk.core.Error erreur = new com.oxia.fwk.core.Error();
        StringBuffer text = 
            new StringBuffer("la transaction est Interrompu, une erreur dans ConsultationCompteRejetesAction / Dispatch Action :imprimerCcptRejetes ");
        text.append(e.toString());
        erreur.setCode("200");
        erreur.setDescription(text.toString());
       
        ActionMessage actionMessage = new ActionMessage("exception.generique",erreur.getDescription());
        actionMessages.add("Erreur ", actionMessage);
        this.saveMessages(request, actionMessages);
        logger.error("Erreur au niveau de l'agence <<" +consultationCompteRejetesForm.getParamConsult().getCodStrcStrc()+ ">>. Exception : ",e);  
        return mapping.findForward("error");
    }
    }    
    
}