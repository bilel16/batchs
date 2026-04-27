package com.bna.smile.web.util.lov.forms;

import antlr.collections.List;

import java.util.ArrayList;

import java.util.Collection;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

public class GroupProfForm extends ActionForm {
    /**Reset all properties to their default values.
     * @param mapping The ActionMapping used to select this instance.
     * @param request The HTTP Request we are processing.
     */
    private String finder = "";
    private String cibleProf = "";
    private String cibleCodProf = "";
    private String cibleCodGProf = "";
    private Collection listeProfession = new ArrayList();


    public void reset(ActionMapping mapping, HttpServletRequest request) {
        super.reset(mapping, request);
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


    public void setListeProfession(Collection listeProfession) {
        this.listeProfession = listeProfession;
    }

    public Collection getListeProfession() {
        return listeProfession;
    }

    public void setCibleProf(String cibleProf) {
        this.cibleProf = cibleProf;
    }

    public String getCibleProf() {
        return cibleProf;
    }

    public void setCibleCodProf(String cibleCodProf) {
        this.cibleCodProf = cibleCodProf;
    }

    public String getCibleCodProf() {
        return cibleCodProf;
    }

    public void setCibleCodGProf(String cibleCodGProf) {
        this.cibleCodGProf = cibleCodGProf;
    }

    public String getCibleCodGProf() {
        return cibleCodGProf;
    }
}
