package com.bna.smile.web.commun.actions;

import com.bna.commun.util.DateHandler;
import com.bna.smile.model.constant.Constants;
import com.bna.smile.model.domainecontratcompte.modificationdonneesclient.commande.GetListeMineursDevenusMajeurCmd;
import com.bna.smile.model.domainecontratcompte.modificationdonneesclient.model.ParamListeMineursDevenusMajeursVo;
import com.bna.smile.model.domainecontratcompte.modificationdonneesclient.traitement.GetListeDesMineursDevenusMajeursTrt;
import com.bna.smile.model.reporting.model.CommonReportVO;
import com.bna.smile.web.commun.forms.ListeMineursDevenusMajeursForm;
import com.bna.smile.web.commun.model.ParamAgence;

import com.bna.smile.web.moyenPaiement.oppositionMoyenPaiement.forms.ConsultationOppMoyPaieForm;

import java.io.IOException;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.actions.DispatchAction;

public class ListeMineursDevenusMajeursAction extends DispatchAction {
    public ListeMineursDevenusMajeursAction() {
    }
    
    public ActionForward initierPage(ActionMapping mapping, ActionForm form, 
                                     HttpServletRequest request, 
                                     HttpServletResponse response) throws IOException, 
                                                                          ServletException {
                                                                          
        ListeMineursDevenusMajeursForm listeMineurForm = (ListeMineursDevenusMajeursForm) form;
        ParamAgence paramAgence = 
              (ParamAgence)request.getSession().getAttribute("paramAgBNA"); /// structure de l'agent qui fait la saisie
        
        ActionMessages actionMessages = new ActionMessages();
                
       try{
        
        listeMineurForm.setDateJour(DateHandler.strToDate(DateHandler.dateJour()));
        listeMineurForm.setCodeStructure(paramAgence.getCodStrcStrc());
        listeMineurForm.getInitialisationView().setNumMatrUser(paramAgence.getNumMatrUser().toString());
        GetListeMineursDevenusMajeurCmd getListeMineursCmd = new GetListeMineursDevenusMajeurCmd();
        
        ParamListeMineursDevenusMajeursVo paramListeMineurVo = new ParamListeMineursDevenusMajeursVo();
        paramListeMineurVo.setCodeStructure(listeMineurForm.getCodeStructure());
        paramListeMineurVo.setDateJour(listeMineurForm.getDateJour());
        
        paramListeMineurVo = (ParamListeMineursDevenusMajeursVo)getListeMineursCmd.execute(paramListeMineurVo);
        
        if (paramListeMineurVo.hasError()){
            List listErreur = paramListeMineurVo.getErrors();
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
            
        }else {
            listeMineurForm.setListeDesMineursDevenusMajeur(paramListeMineurVo.getListeDesMineursDevenusMajeurs());
            return mapping.findForward("initierPage");
        }
        
      } catch (Exception e) {
              com.oxia.fwk.core.Error erreur = new com.oxia.fwk.core.Error();
              StringBuffer text = 
                  new StringBuffer("la transaction est Interrompu, une erreur dans ListeMineursDevenusMajeursAction : ");
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
     * Action de la page  listeMineurDevenuMajeur.jsp 
     * imprimer la liste des mineurs devenus majeurs
     * Nom du package : com.bna.smile.web.commun.actions
     * @author lamia JERBI
     * @version le 29/05/2008
     */     
    public ActionForward imprimerListeMineursDevenusMajeurs(ActionMapping mapping, 
                                                          ActionForm form, 
                                                          HttpServletRequest request, 
                                                          HttpServletResponse response) throws IOException, 
                                                                                               ServletException {
             ListeMineursDevenusMajeursForm listeMineurForm = (ListeMineursDevenusMajeursForm) form;
             ActionMessages actionMessages = new ActionMessages();
   /*        Logger log = Logger.getLogger(ListeMineursDevenusMajeursAction.class);
             PropertyConfigurator.configure(".//log4j.properties");
             log.info("ActionForward : imprimerListeMineursDevenusMajeurs ---Begin---"); */
             try {    
                 CommonReportVO valueObject = new CommonReportVO();
                 
                 Map parameters = new HashMap();
                 
                 /*---------------------------------------------------------------------*/
                 String pCodStrcStrc = "P_COD_STRC_STRC";
                 String vCodStrcStrc = listeMineurForm.getCodeStructure().toString();
              
                /*------------------------------------------------------------------*/
                 String pDatJour = "DAT_JOUR";
                 String vDatJour="";
               
                 String pMatrUser = "P_NUM_MATR_USER";
                 String vMatrUser = listeMineurForm.getInitialisationView().getNumMatrUser();
                 String pLibEtat="P_LIB_ETAT";
                 String vLibEtat="";
                
                 vLibEtat = "Liste des mineurs devenus majeurs";
           
                 parameters.put(pLibEtat, vLibEtat);
                 parameters.put(pCodStrcStrc, vCodStrcStrc);
                 parameters.put(pMatrUser, vMatrUser);
                 
                if(!listeMineurForm.getDateJour().equals("")){
                                vDatJour= DateHandler.dateToStr(listeMineurForm.getDateJour());
                                parameters.put(pDatJour,vDatJour);
                                valueObject.setNomReport("listeMin_devenu_Maj");   
                   }else {
                   //    log.error("la date du jour est vide ---listeMineurForm.getDateJour()---");
                   }
                      
                 valueObject.setParams(parameters);
                 request.getSession().setAttribute("CommonPrintVo",valueObject);
                 //--- l'attribut "print" indique que le nombre de fois que l'impression du fichier sera effectuée est égale  à 1, le test sera fait au niveau JSP -- if((print!=null) && (print.equals("1")))--
                 request.setAttribute("print","1");
               
             return mapping.findForward("initierPage");
             } catch (Exception e) {
             com.oxia.fwk.core.Error erreur = new com.oxia.fwk.core.Error();
             StringBuffer text = 
                 new StringBuffer("la transaction est Interrompu, une erreur dans ListeMineursDevenusMajeursAction / Dispatch Action :imprimerListeMineursDevenusMajeurs ");
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
