package com.bna.smile.web.admin.actions;


import com.bna.commun.commande.GetJourneeStructureCmd;
import com.bna.commun.commande.InitialisationCoursChangeCmd;
import com.bna.commun.model.JourneeStructure;
import com.bna.commun.model.Personnel;

import com.bna.commun.util.DateHandler;import com.bna.commun.util.StrHandler;

import com.bna.commun.vo.JourneeVo;
import com.bna.smile.web.admin.forms.LoginForm;

import com.bna.smile.web.commun.model.ParamAgence;

import com.oxia.fwk.context.Context;
import com.oxia.fwk.core.ICriteria;
import com.oxia.fwk.core.IExpression;
import com.oxia.fwk.core.ISearchEngine;
import com.oxia.fwk.logging.Log;
import com.oxia.fwk.searchengine.SearchEngine;

import java.io.IOException;

import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;


public class LoginAction extends Action {
    Log logger = new Log(LoginAction.class);

    /**
     *
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws IOException
     * @throws ServletException
     */
    public ActionForward execute(ActionMapping mapping, ActionForm form, 
                                 HttpServletRequest request, 
                                 HttpServletResponse response) throws IOException, 
                                                                      ServletException {
        try {

            LoginForm loginForm = (LoginForm)form;
            Context context = 
                (Context)request.getSession().getServletContext().getAttribute("CONTEXT");
            ISearchEngine searchEngine = 
                (SearchEngine)context.getBean("searchEngine");
            ICriteria criteria = searchEngine.createCriteria();
            IExpression expression = searchEngine.createExpression();
         
       
            //criteria.add(expression.eq("nomPwdUser", loginForm.getPassword()));

            criteria.add(expression.eq("numMatrUser", 
                                       loginForm.getLogin()));


            List l = searchEngine.find(Personnel.class, criteria);

            //--------------------------------------------------------///
            //------ Initialisation journee ---------------------------//
             InitialisationCoursChangeCmd ini = new InitialisationCoursChangeCmd();
               JourneeVo j = new JourneeVo();
               j.setDateJourneeOuverte(DateHandler.strToDate(DateHandler.dateJour()));
               j= (JourneeVo) ini.execute(j);
             
            //----------------------------------------------------/// 
            if (l != null && l.size() > 0) {
                Personnel user = (Personnel)l.get(0);
                loginForm.setLogin(user.getNumMatrUser().toString());
                //loginForm.setPassword(user.getNomPwdUser());
                loginForm.setAgence(user.getStructure().getLibStrcStrc());
                /* initialisation des parametres de l'agence */
                ParamAgence paramAgence=new ParamAgence();
                paramAgence.setCodStrcStrc(user.getStructure().getCodStrcStrc());
                paramAgence.setNumMatrUser(user.getNumMatrUser());
                
                if (user.getStructure().getStructure() != null){
                paramAgence.setCodStrmStrc(user.getStructure().getStructure().getCodStrcStrc());
                }else {
                    paramAgence.setCodStrmStrc(user.getStructure().getCodStrcStrc());
                }
                
                if (user.getStructure().getTypeStructure().getCodTstrTstr() != null ){
                paramAgence.setCodTstrcTstrc(user.getStructure().getTypeStructure().getCodTstrTstr());
                } else {
                 paramAgence.setCodTstrcTstrc (new Long (1));
                }
                
                //------- Recherche de la date comptable de la structure
                
                GetJourneeStructureCmd getJourneeStructure = new GetJourneeStructureCmd();
                JourneeStructure  journeeStructure                  =new JourneeStructure();
                journeeStructure.setStructure(user.getStructure());
                
                journeeStructure = (JourneeStructure)  getJourneeStructure.execute(journeeStructure);
                if (journeeStructure.getJourneeStructureId() != null && journeeStructure.getJourneeStructureId().getDatJrnJrn() != null ){
                    paramAgence.setDateComptable(DateHandler.dateToStr(journeeStructure.getJourneeStructureId().getDatJrnJrn()));
                    paramAgence.setDateJours(DateHandler.dateToStr(new Date()));
                }
                
                request.getSession().setAttribute("paramAgBNA",paramAgence);
                
                logger.debug("Le Personnel [" + user.getNumMatrUser() + 
                             "] is Connected");

                return mapping.findForward("indexSMILE");
            } else {
                return mapping.findForward("errorLogin");
            }
        } catch (Exception e) {
            System.out.println("erreur load  " + e.getMessage());
            e.printStackTrace();
            return mapping.findForward("error");
        }
    }

    
    
    

}
