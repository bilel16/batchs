package com.bna.smile.web.souscription.servlets;

import com.bna.commun.model.Categorie;
import com.bna.commun.model.CategorieId;
import com.bna.commun.model.ContratCpt;
import com.bna.commun.util.DateHandler;import com.bna.commun.util.StrHandler;

import com.bna.commun.util.ContextHandler;
import com.bna.commun.util.StrHandler;
import com.bna.commun.vo.PrimitiveVO;
import com.bna.smile.model.constant.Constants;
import com.bna.smile.model.domainecontratcompte.souscriptioncontratcompte.commande.CalculSoldTheorEpargneCmd;
import com.bna.smile.model.domainecontratcompte.souscriptioncontratcompte.model.ParamDetailCatCpt;
import com.bna.smile.model.domainecontratcompte.souscriptioncontratcompte.traitement.CalculSoldTheorEpargneTrt;

import com.oxia.fwk.context.Context;
import com.oxia.fwk.core.ICriteria;
import com.oxia.fwk.core.IExpression;
import com.oxia.fwk.core.ISearchEngine;
import com.oxia.fwk.searchengine.SearchEngine;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.ajaxtags.helpers.AjaxXmlBuilder;
import org.ajaxtags.servlets.BaseAjaxServlet;


public class CalculReliquat extends BaseAjaxServlet {
    public Context context = ContextHandler.getContext();
    
  public String getXmlContent(HttpServletRequest request, HttpServletResponse response) {
    
    Long codeRegimeEpargne = new Long(request.getParameter("codeRegimeEpargne"));
    String codeCategorieEpargne =  request.getParameter("codeCategorieEpargne");
    Long montSoldActuel =  new Long(request.getParameter("montSoldActuel"));
    Long codPrdPrd = new Long(request.getParameter("codPrdPrd"));
    String datOuvCcpt =  request.getParameter("datOuvCcpt");
    
    Long montAssrCat =new Long("0");
    if (codPrdPrd.equals(Constants.COD_PRD_PRD_PEE)){
        montAssrCat =  new Long((request.getParameter("montAssrCat")));
    }
    ContratCpt contratCpt=new ContratCpt();
    contratCpt.setDatOuvCcpt(DateHandler.strToDate(datOuvCcpt));
    
    CalculSoldTheorEpargneCmd calculSoldTheorEpargneCmd=new CalculSoldTheorEpargneCmd();
    Categorie categorie =new Categorie();
    
    CategorieId categorieId=new CategorieId();
    categorieId.setCodCatCat(codeCategorieEpargne); 
    categorieId.setCodRgmRgm(codeRegimeEpargne);
    categorieId.setCodPrdPrd(codPrdPrd);
    categorie.setCategorieId(categorieId);
    ParamDetailCatCpt paramDetailCatCpt=new ParamDetailCatCpt();
    paramDetailCatCpt.setCategorie(categorie);
    paramDetailCatCpt.setContratCpt(contratCpt);  
      
    PrimitiveVO primitiveVO=(PrimitiveVO) calculSoldTheorEpargneCmd.execute(paramDetailCatCpt);
    Double montSoldTheor=primitiveVO.getVDouble();
    Double montSoldT=(Math.floor(primitiveVO.getVDouble())+1)/1000;
    Double reliquat =Math.max((Math.floor(montSoldTheor-montSoldActuel.doubleValue())+1)/1000,new Double(0));
      
      /* Recherche catégorie */
      ISearchEngine searchEngine = (SearchEngine)context.getBean("searchEngine");
      ICriteria criteriaCat = searchEngine.createCriteria();
      IExpression expression = searchEngine.createExpression();
      criteriaCat.add(expression.eq("categorieId.codCatCat", paramDetailCatCpt.getCategorie().getCategorieId().getCodCatCat()));
      criteriaCat.add(expression.eq("categorieId.codRgmRgm", paramDetailCatCpt.getCategorie().getCategorieId().getCodRgmRgm()));
      criteriaCat.add(expression.eq("categorieId.codPrdPrd", paramDetailCatCpt.getCategorie().getCategorieId().getCodPrdPrd()));
      
      List listeCatEpargne = new ArrayList();
      listeCatEpargne = searchEngine.find(Categorie.class, criteriaCat);
      Categorie cat=new Categorie();
      if (listeCatEpargne.size()>=0) {
          cat = (Categorie)listeCatEpargne.get(0);
      }
      Double reliquatAss =new Double("0");
      Double montBrsCat =new Double("0");
      if (cat.getCategorieId().getCodPrdPrd().equals(Constants.COD_PRD_PRD_PEE) ){ // montant de l'assurance seulement pour le PEE
         reliquatAss = cat.getMontAssrCat().doubleValue()- montAssrCat.doubleValue();
         montBrsCat  = cat.getMontBrsCat().doubleValue();
      }
      
    return new AjaxXmlBuilder()
        .addItem("montSoldTheor",StrHandler.formatmnt(montSoldT*1000))
        .addItem("reliquat", StrHandler.formatmnt(reliquat*1000))
        .addItem("nouvMontVersCat", StrHandler.formatmnt(cat.getMontVersCat().doubleValue()))
        .addItem("nouvMontCaptCat", StrHandler.formatmnt(cat.getMontCaptCat().doubleValue()))
        .addItem("reliquatAssur", StrHandler.formatmnt(reliquatAss))
        .addItem("montBrsCat", StrHandler.formatmnt(montBrsCat))
        .toString();
  }

}
