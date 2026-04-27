package com.bna.smile.model.reporting.commande;

import com.bna.smile.model.reporting.model.CommonReportVO;
import com.bna.smile.model.reporting.service.PrinterService;
import com.oxia.fwk.core.IValueObject;

public class PrinterCmd {

    public IValueObject execute(IValueObject vo) throws Exception{
        try
        {
            CommonReportVO 
            reportVo = (CommonReportVO)vo;
            PrinterService ps = new PrinterService();
            reportVo=(CommonReportVO) ps.execute(reportVo);
            return (reportVo);
        }
        catch (Exception e) {
         
            throw new Exception(e);
        }
    }
}
