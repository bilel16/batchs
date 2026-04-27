
/*
 * Classe form de lov
 */
package com.bna.smile.web.util.lov.forms;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;


public class LovForm extends ActionForm {

    private String login = "";
    private String password = "";
    private String fieldtitre = "";
    private String fieldproprety = "";
    private String fieldtitre1 = "";
    private String fieldproprety1 = "";
    private String vo = "";
    private String output = "";
    private String titre = "";
    private String lib1 = "";
    private String lib2 = "";
    private String pk = "";
    List vlov = new ArrayList();
    List vlov1 = new ArrayList();
    private String fieldnames;
    private String finder;
    private String where = "";


    public void setLogin(String login) {
        this.login = login;
    }

    public String getLogin() {
        return login;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPassword() {
        return password;
    }

    public ActionErrors validate(ActionMapping mapping, 
                                 HttpServletRequest request) {
        ActionErrors errors = new ActionErrors();

        if (getLogin() == null || getLogin().length() < 1) {
            errors.add("loginError", new ActionMessage("login.name.mondatory"));
        } else {
            try {
                Integer.parseInt(getLogin());
            } catch (NumberFormatException e) {
                errors.add("loginError", 
                           new ActionMessage("login.matricule.number"));
            }

        }

        if (getPassword() == null || getPassword().length() < 1) {
            errors.add("loginError", new ActionMessage("login.pwd.mondatory"));
        }
        return errors;
    }

    public void reset(ActionMapping actionMapping, 
                      ServletRequest servletRequest) {
        super.reset(actionMapping, servletRequest);
        setLogin("");
        setPassword("");

    }

    public void reset() {

        vlov = new ArrayList();
    }

    public void setFieldtitre(String fieldtitre) {
        this.fieldtitre = fieldtitre;
    }

    public String getFieldtitre() {
        return fieldtitre;
    }

    public void setFieldproprety(String fieldproprety) {
        this.fieldproprety = fieldproprety;
    }

    public String getFieldproprety() {
        return fieldproprety;
    }


    public void setFieldtitre1(String fieldtitre1) {
        this.fieldtitre1 = fieldtitre1;
    }

    public String getFieldtitre1() {
        return fieldtitre1;
    }

    public void setFieldproprety1(String fieldproprety1) {
        this.fieldproprety1 = fieldproprety1;
    }

    public String getFieldproprety1() {
        return fieldproprety1;
    }

    public void setVlov(List vlov) {
        this.vlov = vlov;
    }

    public List getVlov() {
        return vlov;
    }

    public void setOutput(String output) {
        this.output = output;
    }

    public String getOutput() {
        return output;
    }

    public void setPk(String pk) {
        this.pk = pk;
    }

    public String getPk() {
        return pk;
    }

    public void setVlov1(List vlov1) {
        this.vlov1 = vlov1;
    }

    public List getVlov1() {
        return vlov1;
    }

    public void setFieldnames(String fieldnames) {
        this.fieldnames = fieldnames;
    }

    public String getFieldnames() {
        return fieldnames;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public String getTitre() {
        return titre;
    }

    public void setFinder(String finder) {
        this.finder = finder;
    }

    public String getFinder() {
        return finder;
    }

    public void setVo(String vo) {
        this.vo = vo;
    }

    public String getVo() {
        return vo;
    }

    public void setLib1(String lib1) {
        this.lib1 = lib1;
    }

    public String getLib1() {
        return lib1;
    }

    public void setLib2(String lib2) {
        this.lib2 = lib2;
    }

    public String getLib2() {
        return lib2;
    }

    public void setWhere(String where) {
        this.where = where;
    }

    public String getWhere() {
        return where;
    }
}
