/*
  * Classe form de login
  */
package com.bna.smile.web.admin.forms;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;


public class LoginForm extends ActionForm {

    private String login = "";
    private String password = "";
    private String agence = "";


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


    public void setAgence(String agence) {
        this.agence = agence;
    }

    public String getAgence() {
        return agence;
    }
}
