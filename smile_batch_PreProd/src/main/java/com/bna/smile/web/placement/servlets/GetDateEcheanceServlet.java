package com.bna.smile.web.placement.servlets;


import com.bna.commun.model.CodePostal;

import com.bna.commun.model.Personne;
import com.bna.commun.util.DateHandler;
import com.bna.smile.model.domainecommun.commande.GetCodePostalCmd;

import com.bna.smile.model.domainecommun.commande.GetPersonneCptCmd;
import com.bna.smile.model.domainecommun.model.PersonneCpt;
import com.bna.smile.model.domainecommun.model.PersonneStrc;

import com.bna.smile.web.commun.model.ParamAgence;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.ajaxtags.helpers.AjaxXmlBuilder;
import org.ajaxtags.servlets.BaseAjaxServlet;

public class GetDateEcheanceServlet extends BaseAjaxServlet {
    public String getXmlContent(HttpServletRequest request, HttpServletResponse response) {
         
         ParamAgence paramAgence = (ParamAgence)request.getSession().getAttribute("paramAgBNA"); 
         Date dateActuelle = paramAgence.getDateOp();         
         String duree =  request.getParameter("duree");
         String dateV =  request.getParameter("date");
         String dateEcheance = "";
         
  int nbreJour;
     if( !duree.equals(null) && !duree.equals("0") && !duree.equals("")){
         nbreJour=Integer.parseInt(duree);         
         dateEcheance = DateHandler.dateToStr(DateHandler.addJour(DateHandler.strToDate(dateV),nbreJour));        
         
     }else{
         dateEcheance= "";
     }
     
      return new AjaxXmlBuilder()
          .addItem("dateEchAjax",dateEcheance)         
          .toString();
     }
}
