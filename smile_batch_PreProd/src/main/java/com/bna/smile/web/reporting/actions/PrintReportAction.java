package com.bna.smile.web.reporting.actions;

import com.bna.smile.model.reporting.model.CommonReportVO;

import com.oxia.fwk.context.Context;

import java.io.ByteArrayInputStream;
import java.io.InputStream;



import java.io.File;
import java.io.IOException;


import java.util.HashMap;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import net.sf.jasperreports.engine.JasperRunManager;

import org.apache.commons.dbcp.BasicDataSource;
import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;
import org.apache.struts.actions.DownloadAction;


public class PrintReportAction extends DownloadAction {
    private static final Logger logger = Logger.getLogger(PrintReportAction.class);
    
  public StreamInfo getStreamInfo(ActionMapping mapping, ActionForm form,
                                                        HttpServletRequest request, HttpServletResponse response)
                                                        throws Exception {
try{
        CommonReportVO valueObject = new CommonReportVO();
        BasicDataSource dataSource = (BasicDataSource) Context.getInstance().getBean("dataSource");
        Map parameters = new HashMap();
            
        valueObject=(CommonReportVO)request.getSession().getAttribute("CommonPrintVo");
        String rapport=valueObject.getNomReport();
        String rootFolder=getServlet().getServletContext().getRealPath("") +File.separatorChar+"reporting"+File.separatorChar;
        String file_name;
        parameters=valueObject.getParams();
        if(valueObject.getNomDossier() != null){
            parameters.put("P_PATH",rootFolder+valueObject.getNomDossier()+File.separatorChar);
            file_name = rootFolder+valueObject.getNomDossier()+File.separatorChar+rapport+".jasper";
            }else {
                parameters.put("P_PATH",rootFolder);
                file_name = rootFolder+rapport+".jasper";
            }
        byte[] bytes = JasperRunManager.runReportToPdf(file_name,parameters, dataSource.getConnection());
        //request.getSession().setAttribute("print2","2");
        return new ByteArrayStreamInfo("application/pdf", bytes);
}catch(Exception e){
            logger.error("Exception : ",e);
            return null;
        }
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
