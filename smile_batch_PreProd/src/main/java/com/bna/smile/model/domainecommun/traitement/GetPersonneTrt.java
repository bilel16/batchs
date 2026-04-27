package com.bna.smile.model.domainecommun.traitement;

import com.bna.commun.model.Personne;
import com.bna.commun.model.PieceAnnexe;
import com.bna.commun.traitements.Traitement;
import com.bna.commun.util.ContextHandler;
import com.bna.smile.model.constant.Constants;
import com.bna.smile.model.domainecommun.model.PersonneStrc;

import com.oxia.fwk.context.Context;
import com.oxia.fwk.core.ICriteria;
import com.oxia.fwk.core.IExpression;
import com.oxia.fwk.core.ISearchEngine;
import com.oxia.fwk.core.IValueObject;
import com.oxia.fwk.core.ValueObject;
import com.oxia.fwk.searchengine.SearchEngine;

import java.util.ArrayList;
import java.util.List;

public class GetPersonneTrt extends Traitement{
    public GetPersonneTrt() {
    }


    /**
     * methode permettant la recherche d'une personne selon le type pièce et numero pièce
     * @param vo :Objet : PersonneStrc
     * @return   :Objet : Personne
     */
    public

    IValueObject perform(IValueObject vo) {

        PersonneStrc personneStrc = (PersonneStrc)vo;
        Context context = ContextHandler.getContext();
        ISearchEngine searchEngine = 
            (SearchEngine)context.getBean("searchEngine");


        ICriteria criteriaPers = searchEngine.createCriteria();
        ICriteria criteriaCpt = searchEngine.createCriteria();
        IExpression expression = searchEngine.createExpression();

        Personne personneRechercher = new Personne();
        PieceAnnexe pieceAnnexe = new PieceAnnexe();
        List listPersonne = new ArrayList();
        this.setCroFlag(false);

   try{
            if((personneStrc.getNumDosJur()!=null)&&(personneStrc.getCodStrcJur()!=null)){
            criteriaPers.add(expression.eq("numDosJur", 
                                           personneStrc.getNumDosJur()));
            criteriaPers.add(expression.eq("codStrcJur", 
                                           personneStrc.getCodStrcJur()));


            List listePersonne = 
                searchEngine.find(Personne.class, criteriaPers);
            if (listePersonne != null && listePersonne.size() > 0) {
                personneRechercher = (Personne)listePersonne.get(0);
            }
            }else if (personneStrc.getCodTpceTpce().equals(Constants.COD_CIN) || 
            personneStrc.getCodTpceTpce().equals(Constants.COD_RCS) || 
            personneStrc.getCodTpceTpce().equals(Constants.COD_NUM_ORDRE)||
             personneStrc.getCodTpceTpce().equals(Constants.COD_NUM_CPT)) {

            criteriaPers.add(expression.eq("typePiece.codTpceTpce", 
                                           personneStrc.getCodTpceTpce()));
            criteriaPers.add(expression.eq("numPcePers", 
                                           personneStrc.getNumPcePers()));

            List listePersonne = 
                searchEngine.find(Personne.class, criteriaPers);
            /*si la personne existe*/
            if (listePersonne != null && listePersonne.size() > 0) {
                personneRechercher = (Personne)listePersonne.get(0);
            }
           }else if (personneStrc.getCodTpceTpce().equals(Constants.COD_CSEJ) || 
                personneStrc.getCodTpceTpce().equals(Constants.COD_PASS)) {
                criteriaPers.add(expression.eq("pieceAnnexeId.codTpceTpce", 
                                               personneStrc.getCodTpceTpce()));
                criteriaPers.add(expression.eq("pieceAnnexeId.numPcePian", 
                                               personneStrc.getNumPcePers()));

                List listPieceAnnexe = 
                    searchEngine.find(PieceAnnexe.class, criteriaPers);
                /*si la piece annexe existe*/
                if (listPieceAnnexe != null && listPieceAnnexe.size() > 0) {
                    pieceAnnexe = (PieceAnnexe)listPieceAnnexe.get(0);
                    personneRechercher = pieceAnnexe.getPersonne();
                }

            }
        
     
        } catch (Exception e) {
            com.oxia.fwk.core.Error erreur = new com.oxia.fwk.core.Error();
            StringBuffer text = 
                new StringBuffer("Erreur dans GetPersonneTrt : ");
            text.append(e.toString());
            erreur.setCode("100");
            erreur.setDescription(text.toString());
            erreur.setKey("GetPersonne");
            personneRechercher.addError(erreur);
            logger.error("Exception : ",e);   
            throw new RuntimeException(e);  
           
        }        
        return (personneRechercher);
    }

    public void genCroText(ValueObject vo) {    
    
    }
    
    public String getNumeroTache(IValueObject vo) {
      return (Constants.CODE_RESSOURCE_GENERALE);        
    }

}
