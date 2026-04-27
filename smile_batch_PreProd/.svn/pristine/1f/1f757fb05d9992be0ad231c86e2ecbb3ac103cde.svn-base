package com.bna.smile.web.util.lov.actions;


import com.bna.commun.model.ClasActivite;
import com.bna.commun.model.SclasActivite;
import com.bna.smile.model.domainecommun.commande.GetSousClassActiviteCmd;
import com.bna.smile.model.domainecommun.model.Listes;

import com.bna.smile.web.util.lov.forms.SousClasActiviteForm;

import java.io.IOException;

import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class SousClasActiviteAction extends Action {
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
            SousClasActiviteForm activiteForm = (SousClasActiviteForm)form;
            String paramLib = request.getParameter("pramLibClasAct");
            String paramCod = request.getParameter("pramCodClasAct");
            ///si action à partir d'un choix externe
            if (paramCod == null) {
                paramCod = activiteForm.getCodClasAct();
                paramLib = activiteForm.getLibClasAct();
                if (paramCod == null) {
                    return mapping.findForward("error");
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
            ///si action à partir du critere de recherchexterne
            String param = "";
            if (activiteForm.getFinder() != null) {
                param = activiteForm.getFinder();
            }

            GetSousClassActiviteCmd getSousClassActiviteCmd = 
                new GetSousClassActiviteCmd();
            SclasActivite sclasAct = new SclasActivite();
            sclasAct.setLibSactSact(param);
            ClasActivite clasAct = new ClasActivite();
            sclasAct.setClasActivite(clasAct);
            clasAct.setCodCactCact(paramCod);

            Listes contratcpt = 
                (Listes)getSousClassActiviteCmd.execute(sclasAct);
            activiteForm.setTitreLov("Activité: " + paramLib);
            activiteForm.setLibClasAct(paramLib);
            activiteForm.setCodClasAct(paramCod);
            activiteForm.setListeActivite(contratcpt.getList());
            return mapping.findForward("success");
        } catch (Exception e) {
            System.out.println(e.toString());
            e.printStackTrace();
            return mapping.findForward("error");
        }

    }
}
