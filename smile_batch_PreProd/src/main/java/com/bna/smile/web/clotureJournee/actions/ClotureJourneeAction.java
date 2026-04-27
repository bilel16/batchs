package com.bna.smile.web.clotureJournee.actions;

import com.bna.commun.commande.ClotureJourneeStructureCmd;
import com.bna.commun.commande.GetJourneeStructureCmd;
import com.bna.commun.model.JourneeStructure;
import com.bna.commun.model.JourneeStructureId;
import com.bna.commun.model.Structure;
import com.bna.commun.util.DateHandler;


import com.bna.commun.vo.JourneeVo;
import com.bna.smile.web.clotureJournee.forms.ClotureJourneeForm;

import com.bna.smile.web.commun.model.ParamAgence;
import com.bna.smile.web.ouverturejournee.forms.OuvertureJourneeForm;

import java.io.IOException;

import java.util.Iterator;

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

public class ClotureJourneeAction extends DispatchAction  {
    public ClotureJourneeAction() {
    }
    Logger logger = Logger.getLogger(ClotureJourneeAction.class);
    ActionMessages actionMessages = new ActionMessages();
    public ActionForward initierPage(ActionMapping mapping, ActionForm form, 
                                     HttpServletRequest request, 
                                     HttpServletResponse response) throws IOException, 
                                                                          ServletException {

     
        ClotureJourneeForm clotureJourneeForm = 
            (ClotureJourneeForm)form;
        
        try {
           
            //clotureJourneeForm.clearForm();
            
              
            ParamAgence paramAgence = 
                (ParamAgence)request.getSession().getAttribute("paramAgBNA");
                
            clotureJourneeForm.setCodeStructure(paramAgence.getCodStrcStrc());
            //clotureJourneeForm.setDateJournee(paramAgence.getDateComptable());
            
            logger.info("Agence "+clotureJourneeForm.getCodeStructure()+" Matricule "+ paramAgence.getNumMatrUser() +  " Entrée methode initierPage ");
            
            clotureJourneeForm.setCodeMatricule(paramAgence.getNumMatrUser()); 
            GetJourneeStructureCmd getJourneeStructureCmd = new GetJourneeStructureCmd();
            
            JourneeStructure journeeStructure = new JourneeStructure();
            JourneeStructureId journeeStructureId = new JourneeStructureId();
            
            journeeStructureId.setCodStrcStrc(paramAgence.getCodStrcStrc());
            journeeStructure.setJourneeStructureId(journeeStructureId);
           
            journeeStructure = (JourneeStructure) getJourneeStructureCmd.execute(journeeStructure);
            
            if (journeeStructure !=  null && journeeStructure.getJourneeStructureId()!= null && journeeStructure.getJourneeStructureId().getDatJrnJrn() !=null ){
                //---------------------------------------------------------//
                //--------- Si la journée est déja clôturée ---------------//
                if (journeeStructure.getCodStatJrn() != null && journeeStructure.getCodStatJrn().equals(Long.valueOf(1)) ){
                    clotureJourneeForm.setTestJournee("cloture");
                    
                } else {
                    clotureJourneeForm.setDateJournee(DateHandler.dateToStr(journeeStructure.getJourneeStructureId().getDatJrnJrn()));   
                }
            }
            
            return mapping.findForward("clotureJournee");
            
        } catch (Exception e) {
            com.oxia.fwk.core.Error erreur = new com.oxia.fwk.core.Error();
            StringBuffer text = 
                new StringBuffer("la transaction est Interrompu, une erreur dans ClotureJourneeAction / initierPage : ");
            text.append(e.toString());
            erreur.setCode("200");
            erreur.setDescription(text.toString());

            ActionMessage actionMessage = 
                new ActionMessage("exception.generique", 
                                  erreur.getDescription());
            actionMessages.add("Erreur ", actionMessage);
            this.saveMessages(request, actionMessages);
            logger.info("Agence "+clotureJourneeForm.getCodeStructure()+" Matricule "+ clotureJourneeForm.getCodeMatricule() +  " Erreur dans la methode initierPage "+e.toString());
            return mapping.findForward("error");
        }
     
       


    }
    
    
    public ActionForward cloturerJournee(ActionMapping mapping, ActionForm form, 
                                     HttpServletRequest request, 
                                     HttpServletResponse response) throws IOException, 
                                                                          ServletException {

        ClotureJourneeForm clotureJourneeForm = (ClotureJourneeForm) form;
        try{
        logger.info("Agence "+clotureJourneeForm.getCodeStructure()+" Matricule "+ clotureJourneeForm.getCodeMatricule() +  " Entreé dans la methode cloturerJournee ");
        
        ClotureJourneeStructureCmd clotureJourneeCmd = new ClotureJourneeStructureCmd();
        JourneeVo journeeVo =  new JourneeVo() ;
        journeeVo.setDateJourneeOuverte(DateHandler.strToDate(clotureJourneeForm.getDateJournee()));
        Structure str = new Structure();
        str.setCodStrcStrc(clotureJourneeForm.getCodeStructure());
        journeeVo.setStructure(str);
        journeeVo.setMatriculeInitiateur(clotureJourneeForm.getCodeMatricule());
        journeeVo = (JourneeVo) clotureJourneeCmd.execute(journeeVo);
        if (!journeeVo.hasError()){
        logger.info("Agence "+clotureJourneeForm.getCodeStructure()+" Matricule "+ clotureJourneeForm.getCodeMatricule() +  " cloturer de la journee du "+clotureJourneeForm.getDateJournee());
        }else {
            for (Iterator it = journeeVo.getErrors().iterator(); it.hasNext(); ) {
                com.oxia.fwk.core.Error erreur = (com.oxia.fwk.core.Error)it.next();
                ActionMessage actionMessage = new ActionMessage("exception.generique",erreur.getDescription());
                actionMessages.add("Erreur ", actionMessage);
                logger.info("Agence "+clotureJourneeForm.getCodeStructure()+" Matricule "+ clotureJourneeForm.getCodeMatricule() +  " Erreur dans la methode cloturerJournee " +erreur.getDescription());
            }    
            this.saveMessages(request, actionMessages);
            return mapping.findForward("error");  
            
        }
        return mapping.findForward("confirmationClotureJournee");
       } catch (Exception e) {
                com.oxia.fwk.core.Error erreur = new com.oxia.fwk.core.Error();
                StringBuffer text = 
                    new StringBuffer("la transaction est Interrompu, une erreur dans ClotureJourneeAction / cloturerJournee : ");
                text.append(e.toString());
                erreur.setCode("200");
                erreur.setDescription(text.toString());

                ActionMessage actionMessage = 
                    new ActionMessage("exception.generique", 
                                      erreur.getDescription());
                actionMessages.add("Erreur ", actionMessage);
                this.saveMessages(request, actionMessages);
                logger.info("Agence "+clotureJourneeForm.getCodeStructure()+" Matricule "+ clotureJourneeForm.getCodeMatricule() +  " Erreur dans la methode cloturerJournee "+e.toString());
                return mapping.findForward("error");

      }
    }
    
}
