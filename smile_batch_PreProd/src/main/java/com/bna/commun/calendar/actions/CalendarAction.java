package com.bna.commun.calendar.actions;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

public class CalendarAction extends DispatchAction {
    /**This is the main action called from the Struts framework.
     * @param mapping The ActionMapping used to select this instance.
     * @param form The optional ActionForm bean for this request.
     * @param request The HTTP Request we are processing.
     * @param response The HTTP Response we are processing.
     */
    public ActionForward ajouterJourFerie(ActionMapping mapping, ActionForm form, 
                                 HttpServletRequest request, 
                                 HttpServletResponse response) throws IOException, 
                                                                      ServletException {
                                                                      

        //CalanderHandler.addJourFerieTest();                                                             

        return mapping.findForward( "success");
    }
    
    public ActionForward enleverJourFerie(ActionMapping mapping, ActionForm form, 
                                 HttpServletRequest request, 
                                 HttpServletResponse response) throws IOException, 
                                                                      ServletException {
                                                                      


        //CalanderHandler.delJourFerieTest();                                                              

        return mapping.findForward( "success");
    }

    public ActionForward prochainJourOuvrable(ActionMapping mapping, ActionForm form, 
                                 HttpServletRequest request, 
                                 HttpServletResponse response) throws IOException, 
                                                                      ServletException {
                                                                      
       // CalanderHandler.GetNextWorkingDayTest();                                                              
        return mapping.findForward( "success");
    }

    public ActionForward consultJoursFeries(ActionMapping mapping, ActionForm form, 
                                 HttpServletRequest request, 
                                 HttpServletResponse response) throws IOException, 
                                                                      ServletException {
                                                                      

        //CalanderHandler.ConsultJourFerieTest();                                                              

        return mapping.findForward( "success");
    }

}
