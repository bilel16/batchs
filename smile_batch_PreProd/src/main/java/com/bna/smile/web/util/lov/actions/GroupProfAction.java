package com.bna.smile.web.util.lov.actions;


import com.bna.commun.model.GroupeProfession;
import com.bna.smile.model.domainecommun.commande.GetGroupProfessionCmd;
import com.bna.smile.model.domainecommun.model.Listes;

import com.bna.smile.web.souscription.forms.SouscriptionContratCompteForm;

import com.bna.smile.web.util.lov.forms.GroupProfForm;

import java.io.IOException;

import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class GroupProfAction extends Action {
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
            GroupProfForm professionForm = (GroupProfForm)form;
            ///si action à partir d'un choix externe
            String param = request.getParameter("pramProfession");
            if (param == null) {
                param = professionForm.getFinder();
                if (param == null) {
                    param = "";
                }
            }
            if (request.getParameter("paramCibleProf") != null && 
                !request.getParameter("paramCibleProf").equals("")) {
                String paramCibleProf = request.getParameter("paramCibleProf");
                String paramCibleCodProf = 
                    request.getParameter("paramCibleCodProf");
                String paramCibleCodGProf = 
                    request.getParameter("paramCibleCodGProf");
                professionForm.setCibleProf(paramCibleProf);
                professionForm.setCibleCodProf(paramCibleCodProf);
                professionForm.setCibleCodGProf(paramCibleCodGProf);
            }

            GetGroupProfessionCmd getGroupProfessionCmd = 
                new GetGroupProfessionCmd();
            GroupeProfession groupProf = new GroupeProfession();
            groupProf.setLibGproGpro(param);
            Listes prof = (Listes)getGroupProfessionCmd.execute(groupProf);
            professionForm.setFinder(param);
            professionForm.setListeProfession(prof.getList());
            return mapping.findForward("success");
        } catch (Exception e) {
            System.out.println(e.toString());
            e.printStackTrace();
            return mapping.findForward("error");
        }

    }
}
