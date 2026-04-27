package com.bna.smile.model.domainecommun.traitement;

import com.bna.commun.model.Personne;

import com.bna.commun.model.PieceAnnexe;
import com.bna.commun.traitements.Traitement;
import com.bna.commun.util.ContextHandler;
import com.bna.smile.model.constant.Constants;
import com.bna.smile.model.domainecommun.model.Listes;
import com.bna.smile.model.domainecommun.service.CRUDservice;

import com.bna.smile.model.domainecommun.service.PersonneService;
import com.bna.smile.model.domainecontratcompte.souscriptioncontratcompte.dao.PersonneDAO;

import com.oxia.fwk.context.Context;
import com.oxia.fwk.core.IValueObject;
import com.oxia.fwk.core.ValueObject;

import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class InsertPersonneTrt extends Traitement{
   

    public InsertPersonneTrt() {
    }
    
   // private InsertPieceAnnexeTrt insertPieceAnnexeTrt;
    /** méthode d'insertion  d'une nouvelle personne en prend en argument la classe Personne et retourne un valueObject Personne
     * @param   ValueObject : AjoutPersonneClientContratVo avec la personne sans numero de sequence
     * @return  ValueObject : AjoutPersonneClientContratVo ,Personne  avec numero de sequence
     */
    public IValueObject perform(IValueObject vo) {
        Personne personne = (Personne)vo;
        Context context = ContextHandler.getContext();
        InsertPieceAnnexeTrt insertPieceAnnexeTrt = new InsertPieceAnnexeTrt() ;
       try{
       if(this.checkClotureJournee()){
        this.setCroFlag(false);      
        //PersonneService personneService = (PersonneService)context.getBean("personneService");
         
        //affecter le numero de sequence de la personne 
        CRUDservice crudservice = (CRUDservice)context.getBean("crudservice");
        PersonneDAO personneDAO = (PersonneDAO)context.getBean("personneDAO");
        // inserer la personne 
        personne.setNumSeqPers(personneDAO.getSequencePersonne());
        //personne.setNumSeqPers(personneDAO.getSequencePersonne());
        if (personne.getNumPcePers() == null || personne.getNumPcePers().equals("") || personne.getTypePiece().getCodTpceTpce().equals(Constants.COD_NUM_ORDRE) ) {            
            personne.setNumPcePers(String.valueOf(personne.getNumSeqPers()));            
        }
       
        crudservice.create(personne);        
        
        Set listePieceAnn = new HashSet();
        listePieceAnn = personne.getPieceAnnexes();

        if (listePieceAnn != null && listePieceAnn.size() > 0) {
            Iterator it = listePieceAnn.iterator();
            PieceAnnexe pieceAnnexe = (PieceAnnexe)it.next();
            pieceAnnexe.getPieceAnnexeId().setNumSeqPers(personne.getNumSeqPers());
            PieceAnnexe pieceAnnRet = (PieceAnnexe)insertPieceAnnexeTrt.exec(pieceAnnexe);
        }

      
      }else{
                      com.oxia.fwk.core.Error erreur = new com.oxia.fwk.core.Error();
                      StringBuffer text = new StringBuffer("La journée est déja clôturée...");            
                      erreur.setCode("100");
                      erreur.setDescription(text.toString());
                      erreur.setKey("InsertPersonneTrt");
                      personne.addError(erreur);    
                      
        }     
        } catch (Exception e) {
            com.oxia.fwk.core.Error erreur = new com.oxia.fwk.core.Error();
            StringBuffer text = 
                new StringBuffer("Erreur dans InsertPersonneTrt : ");
            text.append(e.toString());
            erreur.setCode("100");
            erreur.setDescription(text.toString());
            erreur.setKey("InsertPersonne");
            personne.addError(erreur);
            logger.error("Exception : ",e);   
            throw new RuntimeException(e);  
            
        }  
        return (personne);
    }

    public void genCroText(ValueObject vo) {    
    
    }
    
    public String getNumeroTache(IValueObject vo) {
      return (Constants.CODE_RESSOURCE_GENERALE);    
    }

    
}
