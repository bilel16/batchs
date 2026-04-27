package com.bna.smile.web.compensation.actions;

import com.bna.smile.model.constant.Constants;
import com.bna.smile.web.commun.model.ParamAgence;
import com.bna.smile.web.compensation.forms.GestionIncidentpaiementForm;
import com.bna.smile.web.moyenPaiement.demandeChequier.forms.CreationDemandeChequeForm;

import java.io.IOException;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

public class GestionIncidentpaimentAction {
    
    
    ActionForward initierPage(ActionMapping mapping, ActionForm form, 
                              HttpServletRequest request, 
                              HttpServletResponse response) throws IOException, 
                                                                   ServletException {

        GestionIncidentpaiementForm gestionIncidentpaiementForm = 
            (GestionIncidentpaiementForm)form;
        ActionMessages actionMessages = new ActionMessages();
     try{
        ParamAgence paramAgence = (ParamAgence)request.getSession().getAttribute("paramAgBNA"); /// structure de l'agent         
        
           
        
        return mapping.findForward("success");
        } catch (Exception e) {
            com.oxia.fwk.core.Error erreur = new com.oxia.fwk.core.Error();
            StringBuffer text = 
                new StringBuffer("la transaction est Interrompu, une erreur dans CreationDemandeChequeAction / Dispatch Action :initierPage ");
            text.append(e.toString());
            erreur.setCode("200");
            erreur.setDescription(text.toString());
            ActionMessage actionMessage = new ActionMessage("exception.generique",erreur.getDescription());
            actionMessages.add("Erreur ", actionMessage);
           // this.saveMessages(request, actionMessages);
            return mapping.findForward("error");
        }
      

    }    
}
