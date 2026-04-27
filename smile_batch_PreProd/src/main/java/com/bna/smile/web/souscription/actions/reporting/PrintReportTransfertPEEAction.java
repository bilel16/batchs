package com.bna.smile.web.souscription.actions.reporting;


import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.util.HashMap;
import java.util.Map;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.jasperreports.engine.JasperRunManager;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DownloadAction;
import org.apache.commons.dbcp.BasicDataSource;
import com.oxia.fwk.context.Context;
import com.bna.commun.util.ContextHandler;
import com.bna.smile.model.constant.Constants;


public class PrintReportTransfertPEEAction extends DownloadAction {
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
                String pcodStrcStrc="pCodStrcStrc";
                Long vcodStrcStrc=(Long)request.getSession().getAttribute("codStrcStrc");
                parameters.put(pcodStrcStrc, vcodStrcStrc);
                String pcodPrdPrd="pCodPrdPrd";
                Long vcodPrdPrd=(Long)request.getSession().getAttribute("codPrdPrd");
                parameters.put(pcodPrdPrd, vcodPrdPrd);
                String pnumCcptCcpt="pNumCcptCcpt";
                Long vnumCcptCcpt=(Long)request.getSession().getAttribute("numCcptCcpt");
                parameters.put(pnumCcptCcpt, vnumCcptCcpt);
                

                String pNRgm="pLibNRgmRgm";
                String vNRgm=(String)request.getSession().getAttribute("NRgm");
                parameters.put(pNRgm, vNRgm);
                String pNCat="pLibNCatCat";
                String vNCat=(String)request.getSession().getAttribute("NCat");
                parameters.put(pNCat, vNCat);
                String pARgm="pLibRgmRgm";
                String vARgm=(String)request.getSession().getAttribute("ARgm");
                parameters.put(pARgm, vARgm);
                String pACat="pLibCatCat";
                String vACat=(String)request.getSession().getAttribute("ACat");
                parameters.put(pACat, vACat);
                String pMont2CaptCapt="pMnt2Capt";
                String pMont3CaptCapt="pMnt3Capt";
                Double vMont2CaptCapt=((Long)request.getSession().getAttribute("MontCaptCapt")).doubleValue()*2;
                Double vMont3CaptCapt=((Long)request.getSession().getAttribute("MontCaptCapt")).doubleValue()*3;
                parameters.put(pMont2CaptCapt, vMont2CaptCapt.longValue());
                parameters.put(pMont3CaptCapt, vMont3CaptCapt.longValue());
                String pMontVersMens="pMontVersMens";
                Long vMontVersMens=(Long)request.getSession().getAttribute("MontVersCat");
                parameters.put(pMontVersMens, vMontVersMens);



                if (vcodPrdPrd.intValue()==Constants.COD_PRD_PRD_PEE.intValue()){
                    String pNomTut="pNomPrnTuteur";
                    String vNomTut=(String)request.getSession().getAttribute("NomPrnTuteur");
                    parameters.put(pNomTut, vNomTut);
                    String pNumTut="pNumCinTuteur";
                    String vNumTut=(String)request.getSession().getAttribute("NumCinTuteur");
                    parameters.put(pNumTut, vNumTut);
                    String ptypTut="pTypePceTuteur";
                    Long vtypTut=new Long((String)request.getSession().getAttribute("TypePceTuteur"));
                    parameters.put(ptypTut, vtypTut);
                    String pMontBrsCat="pMontBrsCat";
                    Long vMontBrsCat=(Long)request.getSession().getAttribute("MontBrsCat");
                    parameters.put(pMontBrsCat, vMontBrsCat);
                }
                
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
