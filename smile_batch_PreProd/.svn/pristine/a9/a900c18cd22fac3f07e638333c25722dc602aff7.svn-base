package com.bna.smile.model.reporting.service;

import com.bna.smile.model.reporting.model.CommonReportVO;
import com.bna.smile.model.reporting.traitement.CommonPrinterJobTrt;
import com.bna.smile.model.reporting.traitement.CommonPrinterTrt;
import com.oxia.fwk.context.Context;
import com.oxia.fwk.core.IValueObject;

public class PrinterService {

    public IValueObject execute(IValueObject vo) throws Exception{
        try {
        CommonPrinterTrt imprimerRetraitTrt = new CommonPrinterTrt();
        CommonPrinterJobTrt imprimerRetraitJobTrt = new CommonPrinterJobTrt();
        CommonReportVO report = (CommonReportVO)vo;
        
        String TI=report.getTypeImpression();
        if (TI.equals("P")){
             imprimerRetraitJobTrt  =(CommonPrinterJobTrt)Context.getInstance().getBean("printReportJobTrt");
            report  = (CommonReportVO) imprimerRetraitJobTrt.exec(report);
        }else{
             imprimerRetraitTrt =(CommonPrinterTrt)Context.getInstance().getBean("printReportTrt");
            
            report  = (CommonReportVO) imprimerRetraitTrt.exec(report);  
        }  
        return report;
        }   catch (Exception e) {
         
            throw new Exception(e);
        }
    }
}
