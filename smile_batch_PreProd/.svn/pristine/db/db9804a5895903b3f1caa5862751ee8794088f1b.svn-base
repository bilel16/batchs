package com.bna.smile.web.souscription.actions.reporting;


import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.util.HashMap;
import java.util.Map;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperRunManager;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DownloadAction;
import org.apache.commons.dbcp.BasicDataSource;
import com.oxia.fwk.context.Context;
import com.bna.commun.util.ContextHandler;



public class PrintReportAction extends DownloadAction {
	public StreamInfo getStreamInfo(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {


		///* getting Data Source
		 Context context= ContextHandler.getContext(); 
		 BasicDataSource datasource =(BasicDataSource)context.getBean("dataSource");
                 Connection connection=datasource.getConnection();

		///* getting parameters from the request scope
		String file_name = request.getRealPath("")+"\\reporting\\"+mapping.getParameter()+".jasper";
		///* remplissage du map des parmètres
		///* generation du rapport
                Map parameters = new HashMap();

                String pcodStrcStrcExec="codStrcStrcExecut";
                Long vcodStrcStrcExec=(Long)request.getSession().getAttribute("codStrcStrcExec");
                parameters.put(pcodStrcStrcExec, vcodStrcStrcExec);
                String pcodStrcStrc="codStrcStrc";
                Long vcodStrcStrc=(Long)request.getSession().getAttribute("codStrcStrc");
                parameters.put(pcodStrcStrc, vcodStrcStrc);
                String pcodPrdPrd="codPrdPrd";
                Long vcodPrdPrd=(Long)request.getSession().getAttribute("codPrdPrd");
                parameters.put(pcodPrdPrd, vcodPrdPrd);
                String pnumCcptCcpt="numCcptCcpt";
                Long vnumCcptCcpt=(Long)request.getSession().getAttribute("numCcptCcpt");
                parameters.put(pnumCcptCcpt, vnumCcptCcpt);
                String preliquat="reliquat";
//                Double freliq=((Long)request.getSession().getAttribute("reliquat")).doubleValue()/1000;
//                Long vreliquat=freliq.longValue();
                String vreliquat=(String)request.getSession().getAttribute("reliquat");
                parameters.put(preliquat, vreliquat);
           /*     String pcatCcptCcpt="catCcptCcpt";
                String vcatCcptCcpt=(String)request.getSession().getAttribute("CatRgm");
                parameters.put(pcatCcptCcpt, vcatCcptCcpt);
           */    
                String pNCatRgm="NCatRgm";
                String vNCatRgm=(String)request.getSession().getAttribute("NCatRgm");
                parameters.put(pNCatRgm, vNCatRgm);

		byte[] bytes = JasperRunManager.runReportToPdf(file_name,parameters, connection);
		return new ByteArrayStreamInfo("application/pdf", bytes);
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
