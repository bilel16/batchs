package com.bna.smile.web.reporting.actions;

import com.bna.commun.model.ContratCpt;
import com.bna.commun.model.ContratCptId;
import com.bna.commun.util.ContextHandler;
import com.bna.commun.vo.PrimitiveVO;
import com.bna.smile.web.reporting.forms.ReportRIBForm;
import com.bna.smile.model.domainecommun.commande.*;

import com.bna.smile.model.reporting.model.CommonReportVO;

import java.sql.Connection;

import java.io.IOException;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.jasperreports.engine.JasperRunManager;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import org.apache.commons.dbcp.BasicDataSource;

import com.oxia.fwk.context.Context;



import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.InputStream;
import org.apache.struts.actions.DownloadAction;



import java.util.Enumeration;

import javax.servlet.ServletOutputStream;




public class PrintRibAction extends DownloadAction{

  
            public StreamInfo getStreamInfo(ActionMapping mapping, ActionForm form,
                            HttpServletRequest request, HttpServletResponse response)
                            throws Exception {
                CommonReportVO valueObject = new CommonReportVO();
                BasicDataSource dataSource = (BasicDataSource) Context.getInstance().getBean("dataSource");
                Map parameters = new HashMap();
                valueObject=(CommonReportVO)request.getSession().getAttribute("ribVo");
                String rapport=valueObject.getNomReport();
                String rootFolder=getServlet().getServletContext().getRealPath("") +File.separatorChar+"reporting"+File.separatorChar;
                String file_name = rootFolder+rapport+".jasper";
                parameters=valueObject.getParams();
                parameters.put("P_PATH",rootFolder);
                
                byte[] bytes1 = JasperRunManager.runReportToPdf(file_name,parameters, dataSource.getConnection());
                return new ByteArrayStreamInfo("application/pdf", bytes1);
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





