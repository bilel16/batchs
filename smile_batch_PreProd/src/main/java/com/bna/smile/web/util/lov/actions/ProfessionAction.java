package com.bna.smile.web.util.lov.actions;


import com.bna.commun.model.GroupeProfession;
import com.bna.commun.model.Profession;
import com.bna.smile.model.domainecommun.commande.GetProfessionCmd;
import com.bna.smile.model.domainecommun.model.Listes;

import com.bna.smile.web.souscription.forms.SouscriptionContratCompteForm;

import com.bna.smile.web.util.lov.forms.ProfessionForm;

import java.io.IOException;

import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class ProfessionAction extends Action {
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
            ProfessionForm professionForm = (ProfessionForm)form;
            String paramLibGroup = request.getParameter("pramLibGroupProf");
            String paramCodGroup = request.getParameter("pramCodGroupProf");
            ///si action à partir d'un choix externe
            if (paramCodGroup == null) {
                paramCodGroup = professionForm.getCodGroupProf();
                paramLibGroup = professionForm.getLibGroupProf();
                if (paramCodGroup == null) {
                    return mapping.findForward("error");
                }
            }
            ///si action à partir du critere de recherchexterne
            String param = "";
            if (professionForm.getFinder() != null) {
                param = professionForm.getFinder();
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


            GetProfessionCmd getActiviteCmd = new GetProfessionCmd();
            Profession profession = new Profession();
            profession.setLibProfProf(param);
            GroupeProfession groupProf = new GroupeProfession();
            groupProf.setCodGproGpro(new Long(paramCodGroup));
            profession.setGroupeProfession(groupProf);

            Listes listeProfession = 
                (Listes)getActiviteCmd.execute(profession);
            professionForm.setTitreLov("Profession: " + paramLibGroup);
            professionForm.setLibGroupProf(paramLibGroup);
            professionForm.setCodGroupProf(paramCodGroup);
            professionForm.setListeProfession(listeProfession.getList());
            return mapping.findForward("success");
        } catch (Exception e) {
            System.out.println(e.toString());
            e.printStackTrace();
            return mapping.findForward("error");
        }

    }
}
