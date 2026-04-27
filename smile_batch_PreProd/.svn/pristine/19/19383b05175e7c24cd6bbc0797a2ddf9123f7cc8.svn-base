package com.bna.smile.web.commun.servlet;

import com.bna.commun.model.Structure;

import com.bna.smile.model.domainecommun.commande.GetStructureCmd;

import javax.naming.NamingException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.ajaxtags.helpers.AjaxXmlBuilder;
import org.ajaxtags.servlets.BaseAjaxServlet;

import org.apache.log4j.Logger;

import org.springframework.orm.ObjectRetrievalFailureException;

public class GetLibStrcStrc extends BaseAjaxServlet {
    
    private static final Logger logger = Logger.getLogger(GetLibStrcStrc.class);
    
    public GetLibStrcStrc() {
    }
    
    
    public String getXmlContent(HttpServletRequest request, HttpServletResponse response) throws ObjectRetrievalFailureException, 
                                                                     NamingException {
    
        String codStrcStrc =  request.getParameter("codeStructure");
        String libStrcStrc = "";
        
        try{
        
            if(codStrcStrc != null && !codStrcStrc.equals("")){
                Structure structure =new Structure();
                structure.setCodStrcStrc(Long.valueOf(codStrcStrc));
                GetStructureCmd getStructureCmd= new GetStructureCmd();
                structure = (Structure)getStructureCmd.execute(structure);
                
                if(structure != null){
                    if (structure.getLibStrcStrc()!=null && !structure.getLibStrcStrc().equalsIgnoreCase("null")){
                        libStrcStrc = structure.getLibStrcStrc();                         
                    }
                }
            }
        }catch(Exception e){
            logger.error(e.toString());
        }
        
        return new AjaxXmlBuilder()
            .addItem("libStructureAjax",libStrcStrc).toString();
    }


}
