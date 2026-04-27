package com.bna.smile.web.operationguichet.servlets;

import com.bna.commun.model.Categorie;
import com.bna.commun.model.CategorieId;
import com.bna.commun.model.ContratCpt;
import com.bna.commun.model.CoursChange;
import com.bna.commun.model.CoursChangeId;

import com.bna.commun.model.Devise;


import com.bna.commun.util.DateHandler;
import com.bna.commun.util.StrHandler;
import com.bna.commun.util.DateHandler;
import com.bna.commun.util.StrHandler;
import com.bna.commun.util.ContextHandler;

import com.bna.commun.util.FormuleHandler;
import com.bna.smile.model.domainecommun.commande.GetCoursDevCmd;
import com.bna.commun.vo.PrimitiveVO;
import com.bna.smile.model.domainecontratcompte.souscriptioncontratcompte.commande.CalculSoldTheorEpargneCmd;
import com.bna.smile.model.domainecontratcompte.souscriptioncontratcompte.model.ParamDetailCatCpt;

import com.oxia.fwk.context.Context;
import com.oxia.fwk.core.ICriteria;
import com.oxia.fwk.core.IExpression;
import com.oxia.fwk.core.ISearchEngine;
import com.oxia.fwk.searchengine.SearchEngine;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.ajaxtags.helpers.AjaxXmlBuilder;
import org.ajaxtags.servlets.BaseAjaxServlet;

public class GetCoursDevise extends BaseAjaxServlet {
    public Context context = ContextHandler.getContext();
    
  public String getXmlContent(HttpServletRequest request, HttpServletResponse response) {
      
    Long codDevDevRet = new Long(request.getParameter("codDevDevRet"));
    String montRetrCcpt =  request.getParameter("montRetrCcpt");

/* Le cas du cours journalier */
    if (1==1){
        CoursChangeId coursChangeId=new CoursChangeId();
        coursChangeId.setCodDevDev(codDevDevRet);
        coursChangeId.setDatJourCchn(new Date());
        
        GetCoursDevCmd getCoursDevCmd=new GetCoursDevCmd();
        CoursChange coursChange = (CoursChange)getCoursDevCmd.execute(coursChangeId);
        
        Long k = coursChange.getDevise().getNbrUnitDev();
       // String  coursDev  = StrHandler.formatmnt(coursChange.getMontPoffCchn().doubleValue()*1000);
        String  coursDev  = "";
        Long i =  new Long((new Double(new Double(StrHandler.strWithoutBlanck(montRetrCcpt)).doubleValue()*1000).longValue()));
       // Long j = coursChange.getMontCabaCchn();
        Long  decimal   = coursChange.getDevise().getNbrDecDev();

      //  Long  contreValeur = FormuleHandler.conversionDeviseMontantDinars(i,new Double(coursChange.getMontPoffCchn()),k,decimal,Long.valueOf(2));
        Long  contreValeur = FormuleHandler.conversionDeviseMontantDinars(i,new Double(0),k,decimal,Long.valueOf(2));
        //Long.valueOf(j*i/(k));
    

          
        return new AjaxXmlBuilder()
            .addItem("coursDev",coursDev)
            .addItem("contreValeur", StrHandler.formatmnt(contreValeur.doubleValue()))
            .toString();
    }else{/* le cas du cours annuel (parité fixe)*/
        Context context = ContextHandler.getContext();
        ISearchEngine searchEngine = (SearchEngine)context.getBean("searchEngine");
         
        Devise devise = (Devise)searchEngine.get(Devise.class, codDevDevRet);
    //    String  coursDev  = devise.getMontPoffDev().toString();
        String  coursDev  = "";
        Long i =  new Long((new Double(new Double(StrHandler.strWithoutBlanck(montRetrCcpt)).doubleValue()*1000).longValue()));
       // Long j = devise.getMontPoffDev();
        Long j = Long.valueOf(0);
        Long  contreValeur = Long.valueOf(j*i/(devise.getNbrUnitDev()));


        return new AjaxXmlBuilder()
            .addItem("coursDev",coursDev)
            .addItem("contreValeur", StrHandler.formatmnt(contreValeur.doubleValue()))
            .toString();

    }
  }

}
