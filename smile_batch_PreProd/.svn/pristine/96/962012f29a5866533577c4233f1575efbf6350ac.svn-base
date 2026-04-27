package com.bna.smile.web.souscription.servlets;


import com.bna.smile.web.souscription.actions.SouscriptionContratCompteAction;
import com.bna.smile.web.souscription.forms.SouscriptionContratCompteForm;

import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import org.ajaxtags.servlets.BaseAjaxServlet;

import org.apache.commons.collections.map.ListOrderedMap;
import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;

/**
 * Exemple de servlet Ajax afin d'extraire les pièces nécéssaires
 * suite au choix d'un produit.
 *
 * @author Hassine & Ramzi
 * @version  $Date: 25/01/2007 $
 */
public class PiecesNecessairesServlet extends BaseAjaxServlet {
    private static final Logger logger = Logger.getLogger(PiecesNecessairesServlet.class);

    public String getXmlContent(HttpServletRequest request, 
                                HttpServletResponse response) throws Exception {

        StringBuffer html = new StringBuffer();
        try {
            String make = request.getParameter("make");
            SouscriptionContratCompteForm souscription = 
                (SouscriptionContratCompteForm)request.getSession().getAttribute("souscriptionContratCompteForm");
            List list = (List)souscription.getListeProduits();
            String libDocLdoc = "";
            String libProduit = "";
            String typeProduit = "";
            String sousFamille = "";
            String groupeFamille = "";

            ListOrderedMap ss = null;

            for (Iterator it1 = list.iterator(); it1.hasNext(); ) {
                ss = (ListOrderedMap)it1.next();
                if (make.equals((ss.getValue(0)).toString())) {
                    libDocLdoc = ss.getValue(3).toString();
                    libProduit = ss.getValue(2).toString();
                    typeProduit = ss.getValue(4).toString();
                    sousFamille = ss.getValue(5).toString();
                    groupeFamille = ss.getValue(6).toString();

                    break;
                }
            }

            //champs html pour affecter la valeur du code prduit choisi dans la formebean  

            html.append("<input type=");
            html.append("hidden");
            html.append(" id=");
            html.append("codePrdAjax");
            html.append(" value =");
            html.append(make.toUpperCase());
            html.append(" />");

            //champs html pour affecter la valeur du libelle prduit choisi dans la formebean    
            html.append("<input type=");
            html.append("hidden");
            html.append(" id=");
            html.append("libPrdAjax");
            html.append(" value =\"" + libProduit + "\"");
            html.append(" />");

            //champs html pour affecter la valeur du type prduit choisi dans la formebean    
            html.append("<input type=");
            html.append("hidden");
            html.append(" id=");
            html.append("typeProduit");
            html.append(" value =\"" + typeProduit + "\"");
            html.append(" />");

            //champs html pour affecter la valeur du sous famille produit dans la formebean     
            html.append("<input type=");
            html.append("hidden");
            html.append(" id=");
            html.append("sousFamille");
            html.append(" value =\"" + sousFamille + "\"");
            html.append(" />");

            //champs html pour affecter la valeur du groupe famille produit dans la formebean     
            html.append("<input type=");
            html.append("hidden");
            html.append(" id=");
            html.append("groupeFamille");
            html.append(" value =\"" + groupeFamille + "\"");
            html.append(" />");


            html.append(" <h4> Pièces nécéssaires pour le produit  ").append(make.toUpperCase()).append("</h4><p></p><ul>");
            String listDoc = libDocLdoc;
            int i;
            String doc;
            while ((i = listDoc.indexOf("-")) >= 0) {
                doc = listDoc.substring(0, i);
                if (doc != null && !doc.equals("")) {
                    html.append("<li>").append(doc).append("</li>");
                }
                listDoc = listDoc.substring(i + 1);
            }
            html.append("<li>").append(listDoc).append("</li>");
            html.append("</ul>");


            return html.toString();
        } catch (Exception e) {
            //System.out.println("erreur load  " + e.getMessage());
            //e.printStackTrace("erreur load  " + e.getMessage());
            logger.error("erreur load  " + e.getMessage());
            return html.toString();

        }

    }
}
