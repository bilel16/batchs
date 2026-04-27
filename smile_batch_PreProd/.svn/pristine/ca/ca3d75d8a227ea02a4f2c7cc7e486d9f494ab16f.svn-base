package com.bna.smile.model.domainecommun.traitement;

import com.bna.commun.model.ExonerationCltTva;
import com.bna.commun.traitements.Traitement;
import com.bna.commun.util.StrHandler;
import com.bna.smile.model.domainecommun.model.Listes;

import com.bna.smile.model.domainecontratcompte.moyensPaiement.model.ParamRechercheOpposition;
import com.bna.smile.model.constant.Constants;
import com.bna.smile.model.domainecommun.model.PersonneCpt;
import com.bna.smile.model.domainecommun.model.PersonneStrc;

import com.oxia.fwk.core.ICriteria;
import com.oxia.fwk.core.IExpression;
import com.oxia.fwk.core.IValueObject;
import com.oxia.fwk.core.ValueObject;

import java.util.Date;
import java.util.List;

public class GetListExonerationTvaTrt extends Traitement{
    public GetListExonerationTvaTrt() {
    }
    /**
     * Fonction qui permet de determiner la liste des exoneration tva pour un client / par période / selon l etat (valide, attente...)
     * @Author : lamia jerbi
     * @since 30/12/2008
     */
    public IValueObject perform (IValueObject vo ){  
        Listes listesExoneration = new Listes();
        ParamRechercheOpposition paramRecherche = (ParamRechercheOpposition)vo; 
    try{
      
            
    ICriteria criteria = getSearchEngine().createCriteria();
    IExpression expression = getSearchEngine().createExpression();
    
       if (paramRecherche.getEtat() != null) {
       if(paramRecherche.getEtat().equals("A")){
           criteria.add(expression.eq("codEtatEtva", 
                                      Constants.COD_ETAT_ETVA_ATTENTE));
                                      
                if (paramRecherche.getDateDebutConsult() != null) {
                    criteria.add(expression.ge("datCreEtva", 
                                               paramRecherche.getDateDebutConsult()));
                }
                if (paramRecherche.getDateFinConsult() != null) {
                    criteria.add(expression.le("datCreEtva", 
                                               paramRecherche.getDateFinConsult()));
                }                            
            }else if(paramRecherche.getEtat().equals("MA")){
                      criteria.add(expression.or(expression.eq("codEtatEtva", 
                                                      Constants.COD_ETAT_ETVA_MODIF), expression.eq("codEtatEtva", 
                                                      Constants.COD_ETAT_ETVA_VALIDE)));
                           if (paramRecherche.getDateDebutConsult() != null) {
                               criteria.add(expression.ge("datValcEtva", 
                                                          paramRecherche.getDateDebutConsult()));
                           }
                           if (paramRecherche.getDateFinConsult() != null) {
                               criteria.add(expression.le("datValcEtva", 
                                                          paramRecherche.getDateFinConsult()));
                           }
                           
                       }else {
                                 criteria.add(expression.eq("codEtatEtva", 
                                                             paramRecherche.getEtat()));
                           
                            // rechercher par période selon l'état de l'exoneration TVA
                            if(paramRecherche.getEtat().equals(Constants.COD_ETAT_ETVA_ATTENTE)){
                                
                                 //----------------------------------------------Exoneration TVA en attente
                                     if (paramRecherche.getDateDebutConsult() != null) {
                                         criteria.add(expression.ge("datCreEtva", 
                                                                    paramRecherche.getDateDebutConsult()));
                                     }
                                     if (paramRecherche.getDateFinConsult() != null) {
                                         criteria.add(expression.le("datCreEtva", 
                                                                    paramRecherche.getDateFinConsult()));
                                     }
                                     
                            }else if(paramRecherche.getEtat().equals(Constants.COD_ETAT_ETVA_VALIDE)){
                                       //----------------------------------------------Exoneration TVA valide
                                           if (paramRecherche.getDateDebutConsult() != null) {
                                               criteria.add(expression.ge("datValcEtva", 
                                                                          paramRecherche.getDateDebutConsult()));
                                           }
                                           if (paramRecherche.getDateFinConsult() != null) {
                                               criteria.add(expression.le("datValcEtva", 
                                                                          paramRecherche.getDateFinConsult()));
                                           }
                                   }else if(paramRecherche.getEtat().equals(Constants.COD_ETAT_ETVA_MODIF)){
                                       //----------------------------------------------Exoneration TVA modifiée
                                           if (paramRecherche.getDateDebutConsult() != null) {
                                               criteria.add(expression.ge("datModEtva", 
                                                                          paramRecherche.getDateDebutConsult()));
                                           }
                                           if (paramRecherche.getDateFinConsult() != null) {
                                               criteria.add(expression.le("datModEtva", 
                                                                          paramRecherche.getDateFinConsult()));
                                           }
                                         }else if(paramRecherche.getEtat().equals(Constants.COD_ETAT_ETVA_ANNULE)){
                                             //----------------------------------------------Exoneration TVA modifiée
                                                 if (paramRecherche.getDateDebutConsult() != null) {
                                                     criteria.add(expression.ge("datAnnEtva", 
                                                                                paramRecherche.getDateDebutConsult()));
                                                 }
                                                 if (paramRecherche.getDateFinConsult() != null) {
                                                     criteria.add(expression.le("datAnnEtva", 
                                                                                paramRecherche.getDateFinConsult()));
                                                 }
                                             
                                         }
                            
                             
                             }
       }
       
       if (paramRecherche.getCodStrcStrc() != null) {
           criteria.add(expression.eq("structure.codStrcStrc", 
                                      paramRecherche.getCodStrcStrc()));
       }
  
     //----------------------------------------------recherche par type/num piece
      
      if ((paramRecherche.getTypPceDemd() != null) && (paramRecherche.getNumPceDemd() != null)) {
                PersonneStrc personneStrc = new PersonneStrc();
                PersonneCpt personneCpt = new PersonneCpt();
                GetPersonneCptTrt getPersonneCptTrt = new GetPersonneCptTrt();
                personneStrc.setNumPcePers(paramRecherche.getNumPceDemd());
                personneStrc.setCodTpceTpce(paramRecherche.getTypPceDemd());
                personneCpt =  (PersonneCpt) getPersonneCptTrt.exec(personneStrc);
                
                if(personneCpt != null){
                    criteria.add(expression.eq("client.numSeqPers", 
                                                      personneCpt.getClient().getNumSeqPers()));
                 }else {
                     logger.debug("---------- getPersonneCptTrt a retourné personneCpt vide");
                 }
                 
            }else { 
                 logger.info(" ------------ le type piece et numero piece (paramètres de recherche) sont vides ");
                   }
       


    List l = getSearchEngine().find(ExonerationCltTva.class, criteria);
    
    if(l != null ){
    listesExoneration.setList(l);
    }
  
    return (listesExoneration); 
   
   }catch(Exception e){
      com.oxia.fwk.core.Error erreur = new com.oxia.fwk.core.Error();
      StringBuffer text = 
          new StringBuffer("Erreur dans GetListExonerationTvaTrt : ");
      text.append(e.toString());
      erreur.setCode("200");
      erreur.setDescription(text.toString());
      listesExoneration.addError(erreur);
      return (listesExoneration);  
   }
    }
    
    public void genCroText(ValueObject vo){
        }
    public String  getNumeroTache (IValueObject vo) {
          return (Constants.CODE_RESSOURCE_GENERALE);     
      }
}
