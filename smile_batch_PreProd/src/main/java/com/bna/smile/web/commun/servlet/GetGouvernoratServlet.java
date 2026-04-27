package com.bna.smile.web.commun.servlet;


import com.bna.commun.model.CodePostal;

import com.bna.smile.model.domainecommun.commande.GetCodePostalCmd;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.ajaxtags.helpers.AjaxXmlBuilder;
import org.ajaxtags.servlets.BaseAjaxServlet;

public class GetGouvernoratServlet extends BaseAjaxServlet {
    public String getXmlContent(HttpServletRequest request, HttpServletResponse response) {
      
      String codCpCp =  request.getParameter("codePostal");
      
     CodePostal codPostal = new CodePostal(); 
     codPostal.setCodCpCp(Long.valueOf(codCpCp));
     GetCodePostalCmd getCodePostalCmd = new GetCodePostalCmd();
     codPostal = (CodePostal) getCodePostalCmd.execute(codPostal);
     
      return new AjaxXmlBuilder()
          .addItem("libelleCodePostal", codPostal.getLibCpCp())
          .addItem("codeGouvernorat",   codPostal.getGouvernorat().getCodGouvGouv().toString())
          .addItem("libelleGouvernorat", codPostal.getGouvernorat().getLibGouvGouv())
          .toString();
    }

}
