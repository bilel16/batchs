package com.bna.smile.web.placement.servlets;

import com.bna.smile.model.domaineplacement.commande.GetParamBonCaisseCmd;
import com.bna.smile.model.domaineplacement.model.ParamBonCaisse;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.ajaxtags.helpers.AjaxXmlBuilder;
import org.ajaxtags.servlets.BaseAjaxServlet;

import org.apache.log4j.Logger;


public class GetNumBonCaisseServlet extends BaseAjaxServlet{

    private static final Logger logger = Logger.getLogger(GetNumBonCaisseServlet.class);
    
    public GetNumBonCaisseServlet() {
    }

    public String getXmlContent(HttpServletRequest httpServletRequest, 
                                HttpServletResponse httpServletResponse) {
        
        String numBonCaisse =  httpServletRequest.getParameter("numBonCaisse");
        String codStrcStrc =  httpServletRequest.getParameter("codeStructure");
        String existBC = "false";
        String existDBC = "true";
        String numSequentielBC="null";
        
        ParamBonCaisse paramBonCaisse = new ParamBonCaisse();
        
        paramBonCaisse.setNumBonCaisse(Long.valueOf(numBonCaisse));
        paramBonCaisse.setCodeStructure(Long.valueOf(codStrcStrc));
        
        GetParamBonCaisseCmd getParamBonCaisseCmd = new GetParamBonCaisseCmd();
        
        paramBonCaisse = (ParamBonCaisse)getParamBonCaisseCmd.execute(paramBonCaisse);
       
        if(paramBonCaisse != null){
            if(paramBonCaisse.isExistBonCaisse()){
                  existBC = "true";
                  numSequentielBC = paramBonCaisse.getNumSeqBc().toString();
              }else {
                   existBC = "false";
               }
            if(paramBonCaisse.isExistDetailsBC()){
                  existDBC = "true";
                   }else {
                       existDBC = "false";
                   }
           }else{
                logger.debug("paramBonCaisse est vide"); 
             
            }
            
        return new AjaxXmlBuilder()
            .addItem("existBCAjax",existBC)
            .addItem("existDBCAjax",existDBC)
            .addItem("numSeqBCAjax",numSequentielBC)
            .toString();
    }
}
