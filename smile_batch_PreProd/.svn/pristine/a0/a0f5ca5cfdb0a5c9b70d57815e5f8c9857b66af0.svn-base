package com.bna.smile.web.util.lov.actions;

import com.bna.commun.model.ClasSegment;
import com.bna.smile.model.domainecommun.commande.GetClassSegmentCmd;
import com.bna.smile.model.domainecommun.model.Listes;
import com.bna.smile.web.util.lov.forms.ClasSegmentForm;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class ClasSegmentAction extends Action {
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
            ClasSegmentForm segmentForm = (ClasSegmentForm)form;
            ///si action à partir d'un choix externe

            String param = request.getParameter("pramSegment");
            if (param == null) {
                param = segmentForm.getFinder();
                if (param == null) {
                    param = "";
                }
            }
            if (request.getParameter("paramCible") != null && 
                !request.getParameter("paramCible").equals("")) {
                String paramCible = request.getParameter("paramCible");
                String paramCibleCod = 
                    request.getParameter("paramCibleCod");
                String paramCibleCodS = 
                    request.getParameter("paramCibleCodS");
                String paramCibleCodC = 
                    request.getParameter("paramCibleCodC");
                segmentForm.setCibleSegment(paramCible);
                segmentForm.setCibleCodSegment(paramCibleCod);
                segmentForm.setCibleCodSSegment(paramCibleCodS);
                segmentForm.setCibleCodCSegment(paramCibleCodC);
            }
            GetClassSegmentCmd getClassSegmentCmd = 
                new GetClassSegmentCmd();
            ClasSegment clas = new ClasSegment();
            clas.setLibCsegCseg(param);
            Listes liste = (Listes)getClassSegmentCmd.execute(clas);
            segmentForm.setFinder(param);
            segmentForm.setListeSegment(liste.getList());

            return mapping.findForward("success");
        } catch (Exception e) {
            System.out.println(e.toString());
            e.printStackTrace();
            return mapping.findForward("error");
        }
    }
}
