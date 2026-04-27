package com.bna.smile.web.procuration.servlet;


import com.bna.commun.model.Personne;
import com.bna.commun.util.ContextHandler;

import com.bna.smile.model.domainecommun.commande.GetPersonneCptCmd;
import com.bna.smile.model.domainecommun.model.PersonneCpt;
import com.bna.smile.model.domainecommun.model.PersonneStrc;

import com.oxia.fwk.context.Context;
import com.oxia.fwk.core.ICriteria;
import com.oxia.fwk.core.IExpression;
import com.oxia.fwk.core.ISearchEngine;
import com.oxia.fwk.core.ValueObject;
import com.oxia.fwk.logging.Log;

import com.oxia.fwk.searchengine.SearchEngine;

import java.io.IOException;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import java.util.List;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class GetNomPrnMandatPersonneServlet extends HttpServlet {
    public Context context = ContextHandler.getContext();


    // Initialize the "accounts" hashmap.  For the sake of this exercise,
    // two accounts are created with names "greg" and "duke" during
    // initialization of the Servlet.
    //
    Log log = new Log(GetNomPrnMandatPersonneServlet.class);

    public void init(ServletConfig config) throws ServletException {


    }

    public void doGet(HttpServletRequest request, 
                      HttpServletResponse response) throws IOException, 
                                                           ServletException {

        String codTpceTpce = request.getParameter("value1");
        String numPcePers = request.getParameter("value2");
        //valuegrpProds = valuegrpProds.trim();
        String namenom = request.getParameter("name");
        String indexnom = request.getParameter("index");

        log.debug("value :" + codTpceTpce);
        log.debug("name :" + namenom);
        log.debug("index de List :" + indexnom);


        String nomprn = "";

        try {
            if (namenom.equals("numPcePers")) {

                            log.debug("codTpceTpce: :" + codTpceTpce);

                            PersonneCpt personneCptRetour = new PersonneCpt();
                            PersonneStrc personneStrc =  new PersonneStrc();
                            personneStrc.setCodTpceTpce(Long.parseLong(codTpceTpce));
                            personneStrc.setNumPcePers(numPcePers.toUpperCase());
                            
                            GetPersonneCptCmd getPersonneCptCmd = new GetPersonneCptCmd();
                            personneCptRetour = (PersonneCpt)getPersonneCptCmd.execute(personneStrc);
                            Personne personneRetour = personneCptRetour.getPersonne();
                
            
                            if (personneRetour!=null) { 
                            nomprn =(String)(personneRetour.getNomNomPers() + "*" + personneRetour.getNomPrnPers() + 
                                             "#" + personneRetour.getNumSeqPers() + "$" +personneRetour.getCategoriePersonne().getCodCatpCatp()+ "%"+
                                             indexnom);
                            }else nomprn="null*null#null$null%"+indexnom;
                            
                response.setContentType("text/xml");
                response.setHeader("Cache-Control", "no-cache");
                response.getWriter().write("<nomprn>" + nomprn + "</nomprn>");
                log.debug("nom prenom :" + nomprn);
            }

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
