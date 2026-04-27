package com.bna.smile.web.commun.servlet;

import com.bna.commun.util.ContextHandler;

import com.bna.smile.web.procuration.actions.ConsultationMandatAction;

import com.oxia.fwk.context.Context;
import com.oxia.security.abc.model.Personnel;
import com.oxia.security.abc.service.UserManager;

import javax.naming.NamingException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.ajaxtags.helpers.AjaxXmlBuilder;
import org.ajaxtags.servlets.BaseAjaxServlet;

import org.apache.log4j.Logger;

import org.springframework.orm.ObjectRetrievalFailureException;

public class GetNomPrnPersonnel extends BaseAjaxServlet {
    
    private static final Logger logger = Logger.getLogger(GetNomPrnPersonnel.class);
    
    public GetNomPrnPersonnel() {
    }
    
    public String getXmlContent(HttpServletRequest request, HttpServletResponse response) throws ObjectRetrievalFailureException, 
                                                                     NamingException {
    
        String numMatrUser =  request.getParameter("matricule");
        String codStrcStrc =  request.getParameter("codeStructure");
        String nomPrenomPersonnel = "";
        
        try{
        
            if(numMatrUser != null && !numMatrUser.equals("")){
                Context context= ContextHandler.getContext(); 
                UserManager userManager = (UserManager)context.getBean("userManager");
                Personnel user = null;
                
                user = userManager.getUserByIDAndStructure(numMatrUser, codStrcStrc);
                
                if(user != null){
                    nomPrenomPersonnel = user.getUsername()+" "+user.getUserLastname(); 
                }
            }
        }catch(Exception e){
            logger.error(e.toString());
        }
        
        return new AjaxXmlBuilder()
            .addItem("nomPrnUserAjax",nomPrenomPersonnel).toString();
    }
}
