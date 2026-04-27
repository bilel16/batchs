package com.bna.smile.web.util.lov.actions;


import com.bna.commun.model.Activite;
import com.bna.commun.model.ActiviteId;
import com.bna.commun.model.Profession;
import com.bna.commun.model.SclasActivite;
import com.bna.commun.model.SclasActiviteId;
import com.bna.commun.model.SegmentId;
import com.bna.smile.model.domainecommun.commande.GetActiviteCmd;
import com.bna.smile.model.domainecommun.commande.GetProfessionCmd;
import com.bna.smile.model.domainecommun.model.Listes;


import com.bna.smile.web.util.lov.forms.ActiviteForm;

import com.bna.smile.web.util.lov.forms.ActiviteNewForm;

import com.bna.smile.web.util.lov.forms.ProfessionNewForm;

import java.io.IOException;

import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class ProfessionNewAction extends Action {
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
            ProfessionNewForm professionNewForm = (ProfessionNewForm)form;
           
            professionNewForm.clearForm();
            if (request.getParameter("pramProfession") != null) {
                professionNewForm.setFinder((String)request.getParameter("pramProfession"));
                professionNewForm.setCibleProfession((String)request.getParameter("paramCibleProf"));
                professionNewForm.setCibleCodProfession((String)request.getParameter("paramCibleCodProf"));
                professionNewForm.setCibleCodGProfession((String)request.getParameter("paramCibleCodGProf"));

            }
            GetProfessionCmd getProfessionCmd = new GetProfessionCmd();
            Profession profession = new Profession();
            profession.setLibProfProf(professionNewForm.getFinder());

            Listes listProfession = (Listes)getProfessionCmd.execute(profession);
            professionNewForm.setListeProfession(listProfession.getList());
            
            return mapping.findForward("success");
        } catch (Exception e) {
            System.out.println(e.toString());
            e.printStackTrace();
            return mapping.findForward("error");
        }

    }
}
