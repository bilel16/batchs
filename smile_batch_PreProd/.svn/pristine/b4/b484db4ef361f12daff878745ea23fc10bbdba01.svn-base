package com.bna.smile.web.placement.servlets;


import com.bna.commun.model.CodePostal;

import com.bna.commun.model.Personne;
import com.bna.commun.util.CalanderHandler;
import com.bna.commun.util.DateHandler;
import com.bna.commun.util.StrHandler;
import com.bna.smile.model.domainecommun.commande.GetCodePostalCmd;

import com.bna.smile.model.domainecommun.commande.GetPersonneCptCmd;
import com.bna.smile.model.domainecommun.model.PersonneCpt;
import com.bna.smile.model.domainecommun.model.PersonneStrc;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.ajaxtags.helpers.AjaxXmlBuilder;
import org.ajaxtags.servlets.BaseAjaxServlet;

public class GetBonneDateValeurServlet extends BaseAjaxServlet {
    public String getXmlContent(HttpServletRequest request, HttpServletResponse response) {
      
     String nbJour =  request.getParameter("nbrJours");
     String dateValeur  =  request.getParameter("dateValeur");
     String typeDValeur  =  request.getParameter("typeDValeur");
     Date dateRetour = 
     null;
     
     
    if( Long.valueOf(nbJour).intValue() > 0 &&  dateValeur != null)   {
       
       if(typeDValeur.equals("O")){
         dateRetour =   CalanderHandler.getDateOuvrableBeforeNDays(DateHandler.strToDate(dateValeur),-Long.valueOf(nbJour).intValue());
       }else { 
         dateRetour =  DateHandler.addJour(DateHandler.strToDate(dateValeur), Long.valueOf(nbJour).intValue()*(-1));
       }
       
    }else dateRetour = DateHandler.strToDate(dateValeur);
          
    
       return new AjaxXmlBuilder()
          .addItem("dateValAjax",DateHandler.dateToStr(dateRetour))            
          .toString();
     }
}
