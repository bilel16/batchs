package com.bna.smile.web.util.lov.actions;


import com.bna.smile.model.constant.Constants;
import com.bna.smile.model.domainecommun.commande.LovCmd;
import com.bna.smile.web.admin.actions.LoginAction;
import com.bna.smile.web.util.lov.forms.LovForm;

import com.oxia.fwk.logging.Log;


import java.io.IOException;

import java.lang.reflect.Field;

import java.util.ArrayList;
import java.util.List;


import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;


public class LovAction extends Action {
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

        ActionErrors errors = new ActionErrors();
        try {
            System.out.println("dans action lov1 ");
            LovForm loginForm = (LovForm)form;
            loginForm.reset();
            String nbrcol = "";
            String fieldnames = "";
            String fieldvalues = "";
            String output = "";
            String titre = "";
            String lib1 = "";
            String lib2 = "";
            String where = "";
            String vo = request.getParameter("vo");
            if (vo != null && !vo.equals("")) {
                nbrcol = request.getParameter("nbrcol");
                fieldnames = request.getParameter("fieldnames");
                fieldvalues = request.getParameter("fieldvalues");
                output = request.getParameter("output");
                titre = request.getParameter("titre");
                lib1 = request.getParameter("lib1");
                lib2 = request.getParameter("lib2");
                where = request.getParameter("where");
                System.out.println("dans action lov2 ");
                loginForm.setVo(vo);
                loginForm.setOutput(output);
                loginForm.setTitre(titre);
                loginForm.setFieldnames(fieldnames);
                loginForm.setWhere(where);
            } else {
                vo = loginForm.getVo();
                output = loginForm.getOutput();
                titre = loginForm.getTitre();
                fieldnames = loginForm.getFieldnames();
                where=loginForm.getWhere();
                fieldvalues = loginForm.getFinder();
            }
            List l;
            List listfield = getListField(fieldnames);

            try {
                Field f = Constants.getclass(vo).getDeclaredField((String)listfield.get(0));
                Field f1 = Constants.getclass(vo).getDeclaredField((String)listfield.get(1));
                System.out.println("f.getGenericType() " + f.getType());

            } catch (NoSuchFieldException e) {
                System.out.println("erreur NoSuchFieldException");
                throw new Exception("erreurfield");
            }
//            where=null;
            LovCmd lcmd = new LovCmd();
            if(where!=null && !where.equals("")&& !where.equals("undefined"))
            l = lcmd.execute(fieldvalues, listfield, Constants.getclass(vo),where);
            else
            l = lcmd.execute(fieldvalues, listfield, Constants.getclass(vo));
            System.out.println("dans action lov3 " + l.get(0).getClass());

            //afficher directement dans la liste de la formbean
            //a condition que dans les property de la collection se nomment comme celles de la classe
            loginForm.setFieldproprety((String)listfield.get(0));
            loginForm.setFieldproprety1((String)listfield.get(1));
            System.out.println("dans action lov4 ");

            loginForm.setVlov(l);
            List t = new ArrayList();
            t.add((String)listfield.get(0));
            t.add((String)listfield.get(1));
            loginForm.setVlov1(t);
            loginForm.setFinder("");
           
            return mapping.findForward("login");
        } catch (Exception e) {
            System.out.println("erreur dans action lov4 " + e.getMessage());
            if (e.getMessage().equals("erreurclasse"))
                return mapping.findForward("erreurclasse");
            //errors.add("errors", new ActionError("" + e.getMessage()));
            else if (e.getMessage().equals("erreurfield"))
                return mapping.findForward("erreurfield");
            else
                return mapping.findForward("login");
        }

    }


    private List getListField(String fieldnames) {
        int i = 0;
        List listfield = new ArrayList();
        while (fieldnames.length() > 0) {
            i++;
            if (fieldnames.indexOf("-") != -1) {
                listfield.add(fieldnames.substring(0, 
                                                   fieldnames.indexOf("-")));
                fieldnames = 
                        fieldnames.substring(fieldnames.indexOf("-") + 1, fieldnames.length());
            } else {
                listfield.add(fieldnames.substring(0, fieldnames.length()));
                fieldnames = "";
            }

        }
        return listfield;
    }


}
