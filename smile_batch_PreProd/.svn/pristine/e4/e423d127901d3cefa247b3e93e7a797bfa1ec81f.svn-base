package com.bna.smile.model.reporting.traitement;


import net.sf.jasperreports.engine.JasperRunManager;

import org.apache.commons.dbcp.BasicDataSource;

import com.bna.commun.traitements.Traitement;
import com.bna.smile.model.reporting.model.CommonReportVO;
import com.oxia.fwk.FwkSystemException;
import com.oxia.fwk.context.Context;
import com.oxia.fwk.core.IValueObject;
import com.oxia.fwk.core.ValueObject;


public class CommonPrinterTrt extends Traitement {
	/**
	 * @param valueObject
	 * @return
	 * @throws FwkSystemException
	 */
	private String jasperReportFile;
	
	
	public IValueObject perform (IValueObject valueObject)
			throws FwkSystemException {
		try {
			CommonReportVO vo = (CommonReportVO) valueObject;
			BasicDataSource dataSource = (BasicDataSource) Context
					.getInstance().getBean("dataSource");
	
			/*byte[] bytes = JasperRunManager.runReportToPdf(vo.getRootFolder()+jasperReportFile,
					vo.getParams(), dataSource.getConnection());*/
                        byte[] bytes = JasperRunManager.runReportToPdf(vo.getRootFolder()+vo.getNomReport()+".jasper",
		                    vo.getParams(), dataSource.getConnection());
 			//repmlissage de contenu binaire suite a une impression
			vo.setContent(bytes);
			return vo;
		} catch (Exception e) {
			valueObject.setErrorMessage(e.getMessage());
			return valueObject;
		}
	}


	public String getJasperReportFile() {
		return jasperReportFile;
	}


	public void setJasperReportFile(String jasperReportFile) {
		this.jasperReportFile = jasperReportFile;
	}


    protected void genCroText(ValueObject valueObject) {
    }
}
