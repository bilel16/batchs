package com.bna.smile.web.util.lov.actions;


import com.bna.commun.model.Activite;
import com.bna.commun.model.ActiviteId;
import com.bna.commun.model.SclasActivite;
import com.bna.commun.model.SclasActiviteId;
import com.bna.commun.model.SegmentId;
import com.bna.smile.model.domainecommun.commande.GetActiviteCmd;
import com.bna.smile.model.domainecommun.model.Listes;


import com.bna.smile.web.util.lov.forms.ActiviteForm;

import java.io.IOException;

import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class ActiviteAction extends Action {
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
            ActiviteForm activiteForm = (ActiviteForm)form;
            String paramLibClas = request.getParameter("pramLibClasAct");
            String paramCodClas = request.getParameter("pramCodClasAct");
            String paramLibSClas = request.getParameter("pramLibSClasAct");
            String paramCodSClas = request.getParameter("pramCodSClasAct");
            ///si action à partir d'un choix externe
            if (paramCodSClas == null) {
                paramCodClas = activiteForm.getCodClasAct();
                paramLibClas = activiteForm.getLibClasAct();
                paramCodSClas = activiteForm.getCodSClasAct();
                paramLibSClas = activiteForm.getLibSClasAct();
                if (paramCodClas == null) {
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


            GetActiviteCmd getActiviteCmd = new GetActiviteCmd();
            Activite activite = new Activite();
            activite.setLibActAct(param);
            
            ActiviteId actId = new ActiviteId();
            activite.setActiviteId(actId);
            actId.setCodCactCact(paramCodClas);
            actId.setCodSactSact(new Long(paramCodSClas));
            
            /*
            SclasActivite sclasAct = new SclasActivite();
            activite.setSclasActivite(sclasAct);
            SclasActiviteId sclasActId = new SclasActiviteId();
            sclasAct.setSclasActiviteId(sclasActId);
            sclasActId.setCodSactSact(new Long(paramCodSClas));
            */

            Listes listActivite = (Listes)getActiviteCmd.execute(activite);
            activiteForm.setTitreLov("Activité: " + paramLibClas + " / " + 
                                     paramLibSClas);
            activiteForm.setLibClasAct(paramLibClas);
            activiteForm.setCodClasAct(paramCodClas);
            activiteForm.setLibSClasAct(paramLibSClas);
            activiteForm.setCodSClasAct(paramCodSClas);
            activiteForm.setListeActivite(listActivite.getList());
            return mapping.findForward("success");
        } catch (Exception e) {
            System.out.println(e.toString());
            e.printStackTrace();
            return mapping.findForward("error");
        }

    }
}
