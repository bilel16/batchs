package com.bna.smile.web.operationguichet.servlets;

import com.bna.commun.model.CoursChange;
import com.bna.commun.model.CoursChangeId;
import com.bna.commun.model.Personne;
import com.bna.commun.util.DateHandler;import com.bna.commun.util.StrHandler;
import com.bna.commun.util.FormuleHandler;
import com.bna.commun.util.DateHandler;import com.bna.commun.util.StrHandler;
import com.bna.smile.model.domainechange.commande.GetCoursChangeCmd;
import com.bna.smile.model.domainecommun.commande.GetCoursDevCmd;
import com.bna.smile.model.domainecommun.commande.GetPersonneCmd;
import com.bna.smile.model.domainecommun.model.PersonneCpt;
import com.bna.smile.model.domainecommun.model.PersonneStrc;
import com.bna.smile.web.commun.servlet.GetPersonneServlet;

import com.oxia.fwk.logging.Log;

import java.io.IOException;

import java.util.Date;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
/**
 * @author : Lassaad 
 * 
 */
public class CalculCoursVersementServlet extends HttpServlet{
    public CalculCoursVersementServlet() {
    }

        // Initialize the "accounts" hashmap.  For the sake of this exercise,
        // two accounts are created with names "greg" and "duke" during
        // initialization of the Servlet.
        //
       // Log log = new Log(CalculCoursVersementServlet.class);

        public void init(ServletConfig config) throws ServletException {


        }

        public void doGet(HttpServletRequest request, 
                          HttpServletResponse response) throws IOException, 
                                                               ServletException {

            String codeDeviseOperation = request.getParameter("codeDeviseOperation");
            String codeDeviseContrat   = request.getParameter("codeDeviseContrat");
            String montant   = request.getParameter("montant");
            //String date      = request.getParameter("date");
            
                    
             montant = StrHandler.strWithoutBlanck(montant);
             montant = StrHandler.replace(montant,".","");
            Long vMontant = Long.valueOf(montant);
          
            try {
                
               
                Long montantDinars = Long.valueOf(0);
                Double contreValeur  = Double.valueOf(0);
                Double vCoursParitel = Double.valueOf(0);
                double vCoursMoyen   =1.755;
                
                //--------------------------------------------------//
                //---------- Recherche du cours Change  ------------//
                //--------------------------------------------------//
                CoursChangeId coursChangeId=new CoursChangeId();
                GetCoursChangeCmd getCoursChangeCmd = new GetCoursChangeCmd();
                
                coursChangeId.setCodDevDev(Long.valueOf(codeDeviseContrat));
                Date date = DateHandler.strToDate(DateHandler.dateJour());
                coursChangeId.setDatJourCchn(date);
                
                CoursChange coursChange = (CoursChange)getCoursChangeCmd.execute(coursChangeId);
                //-----------------------------------------------------//
                
               // vCoursParitel   = coursChange.getMontPoffCchn();
                vCoursParitel   = Double.valueOf(0f);
                Long  unite     = coursChange.getDevise().getNbrUnitDev();
                Long  decimal   = coursChange.getDevise().getNbrDecDev();
                // versement avec la meme devise 
                if (codeDeviseOperation.equals(codeDeviseContrat)){ 
                     
                     montantDinars =FormuleHandler.conversionDeviseMontantDinars(vMontant,vCoursParitel,unite,decimal,Long.valueOf(3));
                
                }else {// versement devise differente
                    montantDinars  =FormuleHandler.conversionDeviseMontantDinars(vMontant,vCoursParitel,unite,decimal,Long.valueOf(3));
                     contreValeur  = (montantDinars / vCoursMoyen) /1000 *(Math.pow(10,2) );
                    // montantDinars =FormuleHandler.conversionDeviseMontantDinars(contreValeur.longValue(),vCoursParitel,unite);
                }
             
                
                           
                String retour = 
                         vCoursParitel + "*" + vCoursMoyen + 
                                  "#" + contreValeur.longValue() + "$" + montantDinars.longValue()+":"+unite ;
              
                response.setContentType("text/xml");
                response.setHeader("Cache-Control", "no-cache");
                response.getWriter().write("<cours>" + retour + "</cours>");   
                 
                
            } catch (Exception e) {
                e.printStackTrace();
            }

        }

        public void doPost(HttpServletRequest request, 
                           HttpServletResponse response) throws IOException, 
                                                                ServletException {

            this.doGet(request, response);

        }
   
}
