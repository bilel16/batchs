package com.bna.smile.web.util.lov.actions;


import com.bna.commun.model.Activite;
import com.bna.commun.model.ActiviteId;
import com.bna.commun.model.SclasActivite;
import com.bna.commun.model.SclasActiviteId;
import com.bna.commun.model.SegmentId;
import com.bna.smile.model.domainecommun.commande.GetActiviteCmd;
import com.bna.smile.model.domainecommun.model.Listes;


import com.bna.smile.web.util.lov.forms.ActiviteForm;

import com.bna.smile.web.util.lov.forms.ActiviteNewForm;

import java.io.IOException;

import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class ActiviteNewAction extends Action {
    /**This is the main action called from the Struts framework.
     * @param mapping The ActionMapping used to select this instance.
     * @param form The optional ActionForm bean for this request.
     * @param request The HTTP Request we are processing.
     * @param response The HTTP Response we are processing.
     */
    public ActionForward execute(ActionMapping mapping, ActionForm form, 
                                 HttpServletRequest request, 
                                 HttpServletResponse response) throws IOException, 
                                                                      ServletException {

        try {
            ActiviteNewForm activiteNewForm = (ActiviteNewForm)form;
           
            activiteNewForm.clearForm();
            if (request.getParameter("pramActivite") != null) {
                activiteNewForm.setFinder((String)request.getParameter("pramActivite"));
                activiteNewForm.setCibleActivite((String)request.getParameter("paramCibleAct"));
                activiteNewForm.setCibleCodActivite((String)request.getParameter("paramCibleCodAct"));
                activiteNewForm.setCibleCodSActivite((String)request.getParameter("paramCibleCodSAct"));
                activiteNewForm.setCibleCodCActivite((String)request.getParameter("paramCibleCodCAct"));

            }
       // System.out.println("request param est"+request.getParameter("pramActivite"));
            GetActiviteCmd getActiviteCmd = new GetActiviteCmd();
            Activite activite = new Activite();
            activite.setLibActAct(activiteNewForm.getFinder());

            Listes listActivite = (Listes)getActiviteCmd.execute(activite);
            activiteNewForm.setListeActivite(listActivite.getList());
            
            return mapping.findForward("success");
        } catch (Exception e) {
            System.out.println(e.toString());
            e.printStackTrace();
            return mapping.findForward("error");
        }

    }
}
