package com.bna.smile.web.admin.forms;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

public class InitSmileForm extends ActionForm{

    private String rebrique;
    
    

    public void setRebrique(String rebrique) {
        this.rebrique = rebrique;
    }

    public String getRebrique() {
        return rebrique;
    }
    
    public void reset(ActionMapping mapping, HttpServletRequest request) {
        super.reset( mapping, request);
    }

    /**Validate all properties to their default values.
     * @param mapping The ActionMapping used to select this instance.
     * @param request The HTTP Request we are processing.
     * @return ActionErrors A list of all errors found.
     */
    public ActionErrors validate(ActionMapping mapping, 
                                 HttpServletRequest request) {
        return super.validate( mapping, request);
    }
}
