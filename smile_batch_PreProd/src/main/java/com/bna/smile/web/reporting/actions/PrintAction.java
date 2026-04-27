package com.bna.smile.web.reporting.actions;



import com.bna.commun.model.ContratCptId;
import com.bna.smile.model.reporting.commande.ImprimerRetraitCmd;
import com.bna.smile.model.reporting.commande.PrinterCmd;
import com.bna.smile.model.reporting.model.CommonReportVO;
import com.bna.smile.model.reporting.model.ParamRetraitVo;
import com.bna.smile.web.commun.model.ParamAgence;
import com.bna.smile.web.operationguichet.form.GuichetRetraitForm;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.*;


import java.io.File;
import java.io.IOException;


import java.util.HashMap;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;






import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;
import org.apache.struts.actions.DownloadAction;


public class PrintAction extends DownloadAction {
                                        public StreamInfo getStreamInfo(ActionMapping mapping, ActionForm form,
                                                        HttpServletRequest request, HttpServletResponse response)
                                                        throws Exception {

        CommonReportVO valueObject = new CommonReportVO();
        try {
            
            valueObject=(CommonReportVO)request.getAttribute("CommonPrintVo");
            //valueObject.setParams(parameters);
            //valueObject.setNomReport("EtatModification");
            //valueObject.setTypeImpression("F");/*P : printer , F: file*/
            valueObject.setRootFolder(getServlet().getServletContext().getRealPath("") +File.separatorChar+"reporting"+File.separatorChar);
            PrinterCmd printer = new PrinterCmd();
            valueObject = (CommonReportVO) printer.execute(valueObject);
            
        } 
        catch (Exception e) {
            System.out.println(">>>>>>>>>>> erreur ");
            e.printStackTrace();
        }
        return new ByteArrayStreamInfo("application/pdf", valueObject.getContent());
    }

    protected class ByteArrayStreamInfo implements StreamInfo {

            protected String contentType;

            protected byte[] bytes;

            public ByteArrayStreamInfo(String contentType, byte[] bytes) {
                    this.contentType = contentType;
                    this.bytes = bytes;
            }

            public String getContentType() {
                    return contentType;
            }

            public InputStream getInputStream() throws IOException {
                    return new ByteArrayInputStream(bytes);
            }
    }
}
