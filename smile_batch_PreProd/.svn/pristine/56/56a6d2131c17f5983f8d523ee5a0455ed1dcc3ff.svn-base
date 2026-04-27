package com.bna.smile.web.commun.servlet;

import com.bna.commun.model.Devise;
import com.bna.smile.model.domainecommun.commande.GetDeviseCmd;
import com.bna.smile.model.domainecommun.model.ListTypeCatTpce;
import com.bna.smile.model.domainecontratcompte.souscriptioncontratcompte.commande.ChargerTypeCatPcePersonneCmd;
import com.bna.smile.model.domainecontratcompte.souscriptioncontratcompte.model.TypeCatPers;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.ajaxtags.helpers.AjaxXmlBuilder;
import org.ajaxtags.servlets.BaseAjaxServlet;

public class GetDeviseServlet extends BaseAjaxServlet {
    public GetDeviseServlet() {
    }
    public String getXmlContent(HttpServletRequest request, HttpServletResponse response)
        throws Exception {
        try{
        String codeDevise = request.getParameter("codDevDev");
        
        GetDeviseCmd getDeviseCmd = new GetDeviseCmd();
        Devise devise = new Devise();
        devise.setCodDevDev(Long.valueOf(codeDevise));
        devise = (Devise) getDeviseCmd.execute(devise);
            
            return new AjaxXmlBuilder().addItem("nbrDecDev", devise.getNbrDecDev().toString()).toString();
        }catch(Exception e){
            return new AjaxXmlBuilder().addItem("nbrDecDev", "").toString();
        }
        
        
        
        
        
    }
}
