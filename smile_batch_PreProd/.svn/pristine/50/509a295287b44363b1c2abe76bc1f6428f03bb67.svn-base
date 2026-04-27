package com.bna.smile.web.placement.servlets;


import com.bna.commun.model.CodePostal;

import com.bna.commun.model.PersClient;
import com.bna.commun.model.Personne;
import com.bna.commun.model.ProduitPlacement;
import com.bna.commun.util.DateHandler;
import com.bna.smile.model.domainecommun.commande.GetCodePostalCmd;

import com.bna.smile.model.domainecommun.commande.GetPersonneCptCmd;
import com.bna.smile.model.domainecommun.model.PersonneCpt;
import com.bna.smile.model.domainecommun.model.PersonneStrc;

import com.bna.smile.web.commun.model.ParamAgence;

import com.oxia.fwk.context.Context;
import com.oxia.fwk.core.ICriteria;
import com.oxia.fwk.core.IExpression;
import com.oxia.fwk.core.ISearchEngine;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import java.util.Date;

import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import javax.sql.DataSource;

import org.ajaxtags.helpers.AjaxXmlBuilder;
import org.ajaxtags.servlets.BaseAjaxServlet;

import org.apache.commons.collections.map.ListOrderedMap;

import org.springframework.jdbc.core.JdbcTemplate;

public class GetRegleGestionPrdPlc extends BaseAjaxServlet {
   
       
    private String valdurMinPlc;
    private String valdurMaxPlc;
    private String valmntMinPlc;
    private String valmntMaxPlc;
    private String valmntnominPlc;
    private String vallibAbrPlc;
    private String vallibStrvPlc;
    
  
    public String getXmlContent(HttpServletRequest request, HttpServletResponse response) {
      
       valdurMinPlc="null";
       valdurMaxPlc="null";
       valmntMinPlc="null";
       valmntMaxPlc="null";
       valmntnominPlc="null";
       vallibAbrPlc="null";
       vallibStrvPlc="null";
       
     String produit =  request.getParameter("produit");
    //fonction qui permette de retourner les règles de gestion d'un produit de placement
     ISearchEngine searchEngine=(ISearchEngine)Context.getInstance().getSpringContext().getBean("searchEngine");

     ICriteria criteria = searchEngine.createCriteria();
     IExpression expression = searchEngine.createExpression();
     ProduitPlacement produitPlacement = new ProduitPlacement();
    
 
     criteria.add(expression.eq("codPrdPlc", 
                                Long.valueOf(produit)));
    

     List l = searchEngine.find(ProduitPlacement.class, criteria);

   
     if (l != null && l.size() > 0) {
         produitPlacement = (ProduitPlacement)l.get(0);
     }
          
           if(produitPlacement.getDurMinPlc() != null)
              valdurMinPlc = produitPlacement.getDurMinPlc().toString();
           if(produitPlacement.getDurMaxPlc() != null)
              valdurMaxPlc= produitPlacement.getDurMaxPlc().toString();
           if(produitPlacement.getMntMinPlc() != null)
              valmntMinPlc =   produitPlacement.getMntMinPlc().toString();
           if(produitPlacement.getMntMaxPlc() != null)
              valmntMaxPlc =  produitPlacement.getMntMaxPlc().toString();
           if(produitPlacement.getValNomiPlc() != null)
              valmntnominPlc =  produitPlacement.getValNomiPlc().toString();
           if(produitPlacement.getLibAbrPlc() != null)
              vallibAbrPlc =  produitPlacement.getLibPrdPlc();
           if(produitPlacement.getLibStrvPlc() != null)
              vallibStrvPlc =  produitPlacement.getLibStrvPlc();
      
      
      System.out.println(valmntnominPlc);
           return new AjaxXmlBuilder()
                .addItem("mntMinPlc", valmntMinPlc)
                .addItem("mntMaxPlc", valmntMaxPlc)
                .addItem("durMinPlc", valdurMinPlc)
                .addItem("durMaxPlc", valdurMaxPlc)
                .addItem("mntnominPlc", valmntnominPlc)
                .addItem("libAbrPlc", vallibAbrPlc)
                .addItem("libStrvPlc", vallibStrvPlc)
                .toString();          
        
      
        
    }
    
}

