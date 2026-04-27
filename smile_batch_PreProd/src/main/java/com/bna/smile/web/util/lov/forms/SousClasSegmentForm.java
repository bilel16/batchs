package com.bna.smile.web.util.lov.forms;

import java.util.ArrayList;
import java.util.Collection;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

public class SousClasSegmentForm extends ActionForm {

    private String finder = "";
    private String cibleSegment = "";
    private String cibleCodSegment = "";
    private String cibleCodSSegment = "";
    private String cibleCodCSegment = "";
    private Collection listeSegment = new ArrayList();
    private String libClas = "";
    private String codClas = "";
    private String titreLov = "";
    
    
    /**Reset all properties to their default values.
     * @param mapping The ActionMapping used to select this instance.
     * @param request The HTTP Request we are processing.
     */
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

    public void setFinder(String finder) {
        this.finder = finder;
    }

    public String getFinder() {
        return finder;
    }

    public void setCibleSegment(String cibleSegment) {
        this.cibleSegment = cibleSegment;
    }

    public String getCibleSegment() {
        return cibleSegment;
    }

    public void setCibleCodSegment(String cibleCodSegment) {
        this.cibleCodSegment = cibleCodSegment;
    }

    public String getCibleCodSegment() {
        return cibleCodSegment;
    }

    public void setCibleCodSSegment(String cibleCodSSegment) {
        this.cibleCodSSegment = cibleCodSSegment;
    }

    public String getCibleCodSSegment() {
        return cibleCodSSegment;
    }

    public void setCibleCodCSegment(String cibleCodCSegment) {
        this.cibleCodCSegment = cibleCodCSegment;
    }

    public String getCibleCodCSegment() {
        return cibleCodCSegment;
    }

    public void setListeSegment(Collection listeSegment) {
        this.listeSegment = listeSegment;
    }

    public Collection getListeSegment() {
        return listeSegment;
    }

    public void setLibClas(String libClas) {
        this.libClas = libClas;
    }

    public String getLibClas() {
        return libClas;
    }

    public void setCodClas(String codClas) {
        this.codClas = codClas;
    }

    public String getCodClas() {
        return codClas;
    }

    public void setTitreLov(String titreLov) {
        this.titreLov = titreLov;
    }

    public String getTitreLov() {
        return titreLov;
    }
}
