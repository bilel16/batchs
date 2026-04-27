package com.bna.smile.web.util.lov.actions;

import com.bna.commun.model.Segment;
import com.bna.commun.model.SclasSegment;
import com.bna.commun.model.SclasSegmentId;
import com.bna.commun.model.SegmentId;
import com.bna.smile.model.domainecommun.commande.GetListeSegmentCmd;
import com.bna.smile.model.domainecommun.commande.GetSegmentCmd;
import com.bna.smile.model.domainecommun.model.Listes;
import com.bna.smile.web.util.lov.forms.SegmentForm;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class SegmentAction extends Action {
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
            SegmentForm SegmentForm = (SegmentForm)form;
            String paramLibClas = request.getParameter("pramLibClas");
            String paramCodClas = request.getParameter("pramCodClas");
            String paramLibSClas = request.getParameter("pramLibSClas");
            String paramCodSClas = request.getParameter("pramCodSClas");
            ///si action à partir d'un choix externe
            if (paramCodSClas == null) {
                paramCodClas = SegmentForm.getCodClas();
                paramLibClas = SegmentForm.getLibClas();
                paramCodSClas = SegmentForm.getCodSClas();
                paramLibSClas = SegmentForm.getLibSClas();
                if (paramCodClas == null) {
                    return mapping.findForward("error");
                }
            }
            if (request.getParameter("paramCible") != null && 
                !request.getParameter("paramCible").equals("")) {
                String paramCible = request.getParameter("paramCible");
                String paramCibleCod = request.getParameter("paramCibleCod");
                String paramCibleCodS = request.getParameter("paramCibleCodS");
                String paramCibleCodC = request.getParameter("paramCibleCodC");
                SegmentForm.setCibleSegment(paramCible);
                SegmentForm.setCibleCodSegment(paramCibleCod);
                SegmentForm.setCibleCodSSegment(paramCibleCodS);
                SegmentForm.setCibleCodCSegment(paramCibleCodC);
            }
            ///si action à partir du critere de recherchexterne
            String param = "";
            if (SegmentForm.getFinder() != null) {
                param = SegmentForm.getFinder();
            }


            GetListeSegmentCmd getListeSegmentCmd = new GetListeSegmentCmd();
            Segment segment = new Segment();
            segment.setLibSegSeg(param);
            SegmentId segId = new SegmentId();
            segment.setSegmentId(segId);
            segId.setCodCsegCseg(new Long(paramCodClas));
            segId.setCodSsegSseg(new Long(paramCodSClas));
            /*
            SclasSegment sclas = new SclasSegment();
            segment.setSclasSegment(sclas);
            SclasSegmentId sclasId = new SclasSegmentId();
            sclas.setSclasSegmentId(sclasId);
            sclasId.setCodSsegSseg(new Long(paramCodSClas));*/

            Listes listSegment = (Listes)getListeSegmentCmd.execute(segment);
            SegmentForm.setTitreLov("Segment: " + paramLibClas + " / " + 
                                    paramLibSClas);
            SegmentForm.setLibClas(paramLibClas);
            SegmentForm.setCodClas(paramCodClas);
            SegmentForm.setLibSClas(paramLibSClas);
            SegmentForm.setCodSClas(paramCodSClas);
            SegmentForm.setListeSegment(listSegment.getList());
            return mapping.findForward("success");
        } catch (Exception e) {
            System.out.println(e.toString());
            e.printStackTrace();
            return mapping.findForward("error");
        }
    }
}
