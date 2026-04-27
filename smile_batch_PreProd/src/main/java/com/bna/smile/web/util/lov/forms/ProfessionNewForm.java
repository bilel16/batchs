package com.bna.smile.web.util.lov.forms;

import antlr.collections.List;

import java.util.ArrayList;

import java.util.Collection;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

public class ProfessionNewForm extends ActionForm {
    /**Reset all properties to their default values.
     * @param mapping The ActionMapping used to select this instance.
     * @param request The HTTP Request we are processing.
     */
    private String finder = "";
    private String cibleProfession = "";
    private String cibleCodProfession = "";
    private String cibleCodGProfession = "";
    private Collection listeProfession = new ArrayList();
    


    public void reset(ActionMapping mapping, HttpServletRequest request) {
        super.reset(mapping, request);
    }
    public void  clearForm(){
        
        listeProfession = new ArrayList();    
    }

    /**Validate all properties to their default values.
     * @param mapping The ActionMapping used to select this instance.
     * @param request The HTTP Request we are processing.
     * @return ActionErrors A list of all errors found.
     */
    public ActionErrors validate(ActionMapping mapping, 
                                 HttpServletRequest request) {
        return super.validate(mapping, request);
    }


    public void setFinder(String finder) {
        this.finder = finder;
    }

    public String getFinder() {
        return finder;
    }


    public void setCibleProfession(String cibleProfession) {
        this.cibleProfession = cibleProfession;
    }

    public String getCibleProfession() {
        return cibleProfession;
    }

    public void setCibleCodProfession(String cibleCodProfession) {
        this.cibleCodProfession = cibleCodProfession;
    }

    public String getCibleCodProfession() {
        return cibleCodProfession;
    }

    public void setCibleCodGProfession(String cibleCodGProfession) {
        this.cibleCodGProfession = cibleCodGProfession;
    }

    public String getCibleCodGProfession() {
        return cibleCodGProfession;
    }

    public void setListeProfession(Collection listeProfession) {
        this.listeProfession = listeProfession;
    }

    public Collection getListeProfession() {
        return listeProfession;
    }
}
