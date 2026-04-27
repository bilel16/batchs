package com.bna.smile.web.util.lov.actions;

import com.bna.commun.model.ClasSegment;
import com.bna.commun.model.SclasSegment;
import com.bna.smile.model.domainecommun.commande.GetSousClassSegmentCmd;
import com.bna.smile.model.domainecommun.model.Listes;
import com.bna.smile.web.util.lov.forms.SousClasSegmentForm;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class SousClasSegmentAction extends Action {
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
            SousClasSegmentForm segmentForm = (SousClasSegmentForm)form;
            String paramLib = request.getParameter("pramLibClas");
            String paramCod = request.getParameter("pramCodClas");
            ///si action à partir d'un choix externe
            if (paramCod == null) {
                paramCod = segmentForm.getCodClas();
                paramLib = segmentForm.getLibClas();
                if (paramCod == null) {
                    return mapping.findForward("error");
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
            ///si action à partir du critere de recherchexterne
            String param = "";
            if (segmentForm.getFinder() != null) {
                param = segmentForm.getFinder();
            }

            GetSousClassSegmentCmd getSousClasssegmentCmd = 
                new GetSousClassSegmentCmd();
            SclasSegment sclas = new SclasSegment();
            sclas.setLibSsegSseg(param);
            ClasSegment clas = new ClasSegment();
            sclas.setClasSegment(clas);
            clas.setCodCsegCseg(Long.valueOf(paramCod));

            Listes liste = 
                (Listes)getSousClasssegmentCmd.execute(sclas);
            segmentForm.setTitreLov("Segment: " + paramLib);
            segmentForm.setLibClas(paramLib);
            segmentForm.setCodClas(paramCod);
            segmentForm.setListeSegment(liste.getList());
            return mapping.findForward("success");
        } catch (Exception e) {
            System.out.println(e.toString());
            e.printStackTrace();
            return mapping.findForward("error");
        }
    }
}
