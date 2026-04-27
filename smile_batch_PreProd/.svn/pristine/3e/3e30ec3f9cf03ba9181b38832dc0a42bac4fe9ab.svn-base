package com.bna.smile.web.util.lov.actions;


import com.bna.commun.model.ClasActivite;
import com.bna.smile.model.domainecommun.commande.GetClassActiviteCmd;
import com.bna.smile.model.domainecommun.model.Listes;
import com.bna.smile.web.souscription.forms.SouscriptionContratCompteForm;

import com.bna.smile.web.util.lov.forms.ClasActiviteForm;

import java.io.IOException;

import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class ClasActiviteAction extends Action {
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
            ClasActiviteForm activiteForm = (ClasActiviteForm)form;
            ///si action à partir d'un choix externe

            String param = request.getParameter("pramActivite");
            if (param == null) {
                param = activiteForm.getFinder();
                if (param == null) {
                    param = "";
                }
            }
            if (request.getParameter("paramCibleAct") != null && 
                !request.getParameter("paramCibleAct").equals("")) {
                String paramCibleAct = request.getParameter("paramCibleAct");
                String paramCibleCodAct = 
                    request.getParameter("paramCibleCodAct");
                String paramCibleCodSAct = 
                    request.getParameter("paramCibleCodSAct");
                String paramCibleCodCAct = 
                    request.getParameter("paramCibleCodCAct");
                activiteForm.setCibleActivite(paramCibleAct);
                activiteForm.setCibleCodActivite(paramCibleCodAct);
                activiteForm.setCibleCodSActivite(paramCibleCodSAct);
                activiteForm.setCibleCodCActivite(paramCibleCodCAct);
            }
            GetClassActiviteCmd getClassActiviteCmd = 
                new GetClassActiviteCmd();
            ClasActivite clasAct = new ClasActivite();
            clasAct.setLibCactCact(param);
            Listes contratcpt = (Listes)getClassActiviteCmd.execute(clasAct);
            activiteForm.setFinder(param);
            activiteForm.setListeActivite(contratcpt.getList());

            return mapping.findForward("success");
        } catch (Exception e) {
            System.out.println(e.toString());
            e.printStackTrace();
            return mapping.findForward("error");
        }

    }
}
