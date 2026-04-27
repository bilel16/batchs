package com.bna.smile.web.commun.servlet;

import com.bna.commun.model.Personne;
import com.bna.commun.util.ContextHandler;
import com.bna.smile.model.domainecommun.commande.GetPersonneCmd;
import com.bna.smile.model.domainecommun.commande.GetPersonneCptCmd;
import com.bna.smile.model.domainecommun.model.PersonneCpt;
import com.bna.smile.model.domainecommun.model.PersonneStrc;

import com.oxia.fwk.context.Context;
import com.oxia.fwk.logging.Log;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.*;
import javax.servlet.http.*;


public class GetPersonneServlet extends HttpServlet {
    public Context context = ContextHandler.getContext();


    // Initialize the "accounts" hashmap.  For the sake of this exercise,
    // two accounts are created with names "greg" and "duke" during
    // initialization of the Servlet.
    //
    Log log = new Log(GetPersonneServlet.class);

    public void init(ServletConfig config) throws ServletException {


    }

    public void doGet(HttpServletRequest request, 
                      HttpServletResponse response) throws IOException, 
                                                           ServletException {

        String codTpceTpce = request.getParameter("typePiece");
        String numPcePers = request.getParameter("numeroPiece");
        //valuegrpProds = valuegrpProds.trim();
        String namenom = request.getParameter("name");
        String indexnom = request.getParameter("index");

      
        String nomprn = "";

        try {
                PersonneCpt personneCptRetour = new PersonneCpt();
                PersonneStrc personneStrc = new PersonneStrc();
                personneStrc.setCodTpceTpce(Long.parseLong(codTpceTpce));
                personneStrc.setNumPcePers(numPcePers.toUpperCase());

                GetPersonneCmd getPersonneCmd = new GetPersonneCmd();
                Personne personneRetour = 
                        (Personne)getPersonneCmd.execute(personneStrc);
                


                if (personneRetour != null) {
                    nomprn = 
                            (String)(personneRetour.getNomNomPers() + "*" + personneRetour.getNomPrnPers() + 
                                     "#" + personneRetour.getNumSeqPers() );
                } else
                    nomprn = "null*null#null$" ;

                response.setContentType("text/xml");
                response.setHeader("Cache-Control", "no-cache");
                response.getWriter().write("<nomprn>" + nomprn + "</nomprn>");
               

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
