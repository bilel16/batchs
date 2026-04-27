package com.bna.smile.web.moyenPaiement.demandeCarteBnacaire.servlets;


import com.bna.commun.model.TypeCarte;
import com.bna.commun.util.ContextHandler;
import com.bna.smile.web.souscription.forms.SouscriptionContratCompteForm;

import com.oxia.fwk.context.Context;
import com.oxia.fwk.core.ISearchEngine;
import com.oxia.fwk.searchengine.SearchEngine;

import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import org.ajaxtags.servlets.BaseAjaxServlet;

import org.apache.commons.collections.map.ListOrderedMap;
import org.apache.struts.action.ActionForm;

/**
 * Exemple de servlet Ajax afin d'extraire la discription de chaque type 
 * de carte bancaire
 * @author Ramzi
 * @version  $Date: 28/06/2007 $
 */
public class DescriptionCarteBancaireServlet extends BaseAjaxServlet {

    public String getXmlContent(HttpServletRequest request, 
                                HttpServletResponse response) throws Exception {

        StringBuffer html = new StringBuffer();
        try {
            Long typeCarte = Long.valueOf(request.getParameter("make"));
            // recherche type de carte
             Context context = ContextHandler.getContext();
             ISearchEngine searchEngine = 
                 (SearchEngine)context.getBean("searchEngine");
             TypeCarte typCarte = (TypeCarte)searchEngine.get(TypeCarte.class, typeCarte);
             String description = typCarte.getLibDefTcar();
             
            html.append("<strong>").append(typCarte.getLibTcarTcar()).append("</strong><ul>");
            html.append("<li>").append(description).append("</li>");
            html.append("</ul>");
            
            //Champ hidden pour le sauvcarte du codOperTcar aprés le choix de la carte    
            html.append("<input type=");
            html.append("hidden");
            html.append(" id=");
            html.append("codOperAjax");
            html.append(" value =\"" + typCarte.getCodOperTcar() + "\"");
            html.append(" />");
            
            //Champ hidden pour le sauvcarte du boolPlafTcar aprés le choix de la carte   
            html.append("<input type=");
            html.append("hidden");
            html.append(" id=");
            html.append("boolPlafAjax");
            html.append(" value =\"" + typCarte.getBoolPlafTcar().toString() + "\"");
            html.append(" />");
            
            //Champ hidden pour le sauvcarte du libTcarTcar   
            html.append("<input type=");
            html.append("hidden");
            html.append(" id=");
            html.append("libTcarAjax");
            html.append(" value =\"" + typCarte.getLibTcarTcar() + "\"");
            html.append(" />");
            

            
        } catch (Exception e) {
            System.out.println("erreur load  " + e.getMessage());
            e.printStackTrace();
        }
        return html.toString();

    }
}
