package com.bna.smile.web.placement.servlets;


import com.bna.commun.model.CodePostal;

import com.bna.commun.model.Personne;
import com.bna.commun.util.StrHandler;
import com.bna.smile.model.domainecommun.commande.GetCodePostalCmd;

import com.bna.smile.model.domainecommun.commande.GetPersonneCptCmd;
import com.bna.smile.model.domainecommun.model.PersonneCpt;
import com.bna.smile.model.domainecommun.model.PersonneStrc;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.ajaxtags.helpers.AjaxXmlBuilder;
import org.ajaxtags.servlets.BaseAjaxServlet;

public class GetNomPrnServlet extends BaseAjaxServlet {
    public String getXmlContent(HttpServletRequest request, HttpServletResponse response) {
      
     String typePiece =  request.getParameter("typePiece");
     String numPiece  =  request.getParameter("numPiece");
     String nom = "";
     String prenom = ""; 
     
     PersonneCpt personneCptRetour = new PersonneCpt();
     PersonneStrc personneStrc =  new PersonneStrc();
     personneStrc.setCodTpceTpce(Long.parseLong(typePiece));
     personneStrc.setNumPcePers(numPiece.toUpperCase());
     GetPersonneCptCmd getPersonneCptCmd = new GetPersonneCptCmd();
     personneCptRetour = (PersonneCpt)getPersonneCptCmd.execute(personneStrc);
     Personne personneRetour = personneCptRetour.getPersonne();    
     
     
     
     if(personneRetour != null && personneRetour.getNumSeqPers() != null){
         if(!personneRetour.getCategoriePersonne().getCodCatpCatp().equals("29")){
             if(personneRetour.getNomNomPers() != null && !personneRetour.getNomNomPers().equals(""))
                nom = personneRetour.getNomNomPers();
             else if(personneRetour.getNomRsPers() != null && !personneRetour.getNomRsPers().equals("")) 
                 nom = personneRetour.getNomRsPers();
             if(personneRetour.getNomPrnPers() != null && !personneRetour.getNomPrnPers().equals(""))   
               prenom = personneRetour.getNomPrnPers();
             else if(personneRetour.getLibSiglPers() != null && !personneRetour.getLibSiglPers().equals("")) 
               prenom = personneRetour.getLibSiglPers();
         }else{
              // personne co-titulaire
               nom = StrHandler.replace(personneRetour.getNomNomPers(),"&&","Et");
               //nom = personneRetour.getNomNomPers();
               prenom = ".";
          }
     }else{
         nom = "";
         prenom = "";
     }
        
    
      return new AjaxXmlBuilder()
          .addItem("nomAjax",nom)
          .addItem("prenomAjax",prenom)          
          .toString();
     }
}
