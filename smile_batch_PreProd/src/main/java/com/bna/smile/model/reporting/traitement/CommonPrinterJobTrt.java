package com.bna.smile.model.reporting.traitement;

import java.awt.print.PrinterJob;
import java.util.HashMap;
import java.util.Map;

import javax.print.PrintService;
import javax.print.PrintServiceLookup;
import javax.print.attribute.HashPrintRequestAttributeSet;
import javax.print.attribute.PrintRequestAttributeSet;
import javax.print.attribute.standard.Copies;

import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.export.JRPrintServiceExporter;
import net.sf.jasperreports.engine.export.JRPrintServiceExporterParameter;

import org.apache.commons.dbcp.BasicDataSource;

import com.bna.commun.traitements.Traitement;
import com.bna.smile.model.reporting.model.CommonReportVO;
import com.oxia.fwk.FwkSystemException;
import com.oxia.fwk.context.Context;
import com.oxia.fwk.core.IValueObject;
import com.oxia.fwk.core.ValueObject;


public class CommonPrinterJobTrt extends Traitement {

    private String jasperReportFile;
    public IValueObject perform (IValueObject valueObject)
                    throws FwkSystemException {
                    // getting Data Source
             try {
                 
            
               BasicDataSource dataSource = (BasicDataSource) Context.getInstance().getBean("dataSource");
               CommonReportVO vo = (CommonReportVO) valueObject;
               Map parameters = new HashMap();
               JasperPrint jp=   JasperFillManager.fillReport (vo.getRootFolder()+vo.getNomReport()+".jasper",vo.getParams(), dataSource.getConnection());  
               //Lookup for printers
                PrinterJob job = PrinterJob.getPrinterJob();
                /* Create an array of PrintServices */
                PrintService[] services = PrintServiceLookup.lookupPrintServices(null, null);
                int selectedService = 0;
                job.setPrintService(services[selectedService]);
                PrintRequestAttributeSet printRequestAttributeSet = new HashPrintRequestAttributeSet();
        
                printRequestAttributeSet.add(new Copies(1));
                JRPrintServiceExporter exporter;
                exporter = new JRPrintServiceExporter();
                exporter.setParameter(JRExporterParameter.JASPER_PRINT, jp);
                /* We set the selected service and pass it as a paramenter */
                exporter.setParameter(JRPrintServiceExporterParameter.PRINT_SERVICE, services[selectedService]);
                exporter.setParameter(JRPrintServiceExporterParameter.PRINT_SERVICE_ATTRIBUTE_SET, services[selectedService].getAttributes());
                exporter.setParameter(JRPrintServiceExporterParameter.PRINT_REQUEST_ATTRIBUTE_SET, printRequestAttributeSet);
                exporter.setParameter(JRPrintServiceExporterParameter.DISPLAY_PAGE_DIALOG, Boolean.FALSE);
                exporter.setParameter(JRPrintServiceExporterParameter.DISPLAY_PRINT_DIALOG, Boolean.FALSE);
                exporter.exportReport();
                    return vo;//new ByteArrayStreamInfo("application/pdf", bytes);
                    
            }
                 catch (Exception e) {
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
