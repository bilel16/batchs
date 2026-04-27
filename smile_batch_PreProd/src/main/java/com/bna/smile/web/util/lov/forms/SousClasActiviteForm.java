package com.bna.smile.web.util.lov.forms;

import antlr.collections.List;

import java.util.ArrayList;

import java.util.Collection;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

public class SousClasActiviteForm extends ActionForm {
    /**Reset all properties to their default values.
     * @param mapping The ActionMapping used to select this instance.
     * @param request The HTTP Request we are processing.
     */
    private String finder = "";
    private String cibleActivite = "";
    private String cibleCodActivite = "";
    private String cibleCodSActivite = "";
    private String cibleCodCActivite = "";
    private Collection listeActivite = new ArrayList();
    private String libClasAct = "";
    private String codClasAct = "";
    private String titreLov = "";


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

    public void setListeActivite(Collection listeActivite) {
        this.listeActivite = listeActivite;
    }

    public Collection getListeActivite() {
        return listeActivite;
    }

    public void setLibClasAct(String libClasAct) {
        this.libClasAct = libClasAct;
    }

    public String getLibClasAct() {
        return libClasAct;
    }

    public void setCodClasAct(String codClasAct) {
        this.codClasAct = codClasAct;
    }

    public String getCodClasAct() {
        return codClasAct;
    }

    public void setTitreLov(String titreLov) {
        this.titreLov = titreLov;
    }

    public String getTitreLov() {
        return titreLov;
    }

    public void setCibleActivite(String cibleActivite) {
        this.cibleActivite = cibleActivite;
    }

    public String getCibleActivite() {
        return cibleActivite;
    }

    public void setCibleCodActivite(String cibleCodActivite) {
        this.cibleCodActivite = cibleCodActivite;
    }

    public String getCibleCodActivite() {
        return cibleCodActivite;
    }

    public void setCibleCodSActivite(String cibleCodSActivite) {
        this.cibleCodSActivite = cibleCodSActivite;
    }

    public String getCibleCodSActivite() {
        return cibleCodSActivite;
    }

    public void setCibleCodCActivite(String cibleCodCActivite) {
        this.cibleCodCActivite = cibleCodCActivite;
    }

    public String getCibleCodCActivite() {
        return cibleCodCActivite;
    }
}
