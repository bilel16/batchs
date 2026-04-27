package com.bna.smile.model.domainecommun.traitement;

import com.bna.commun.model.NiveauInstruction;

import com.bna.commun.traitements.Traitement;
import com.bna.commun.util.ContextHandler;

import com.bna.smile.model.constant.Constants;

import com.oxia.fwk.context.Context;
import com.oxia.fwk.core.ICriteria;
import com.oxia.fwk.core.IExpression;
import com.oxia.fwk.core.ISearchEngine;
import com.oxia.fwk.core.IValueObject;
import com.oxia.fwk.core.ValueObject;
import com.oxia.fwk.searchengine.SearchEngine;

import java.util.List;

public class GetNiveauInstructionTrt extends Traitement {
    public GetNiveauInstructionTrt() {
    }
    
    /** méthode pour la recherche du Niveau Instrcution
     * @param  ValueObject : NiveauInstruction 
     * @return ValueObject : NiveauInstruction 
     */
     
  public   IValueObject perform (IValueObject vo)  {

         NiveauInstruction niveauInstruction = (NiveauInstruction)vo;
         ISearchEngine searchEngine=(ISearchEngine)Context.getInstance().getSpringContext().getBean("searchEngine");
         ICriteria criteria = searchEngine.createCriteria();
         IExpression expression = searchEngine.createExpression();

             try {
                 criteria.add(expression.eq("codRmatRmat", niveauInstruction.getCodNiviNivi()));

                 List listNiveauInstruction = searchEngine.find(NiveauInstruction.class, criteria);
                 /*si le RegimeMatrimonial existe*/
                 if (listNiveauInstruction != null && listNiveauInstruction.size() > 0) {
                     niveauInstruction = (NiveauInstruction)listNiveauInstruction.get(0);
                 }else{
                     com.oxia.fwk.core.Error erreur = new com.oxia.fwk.core.Error();
                     StringBuffer text = 
                         new StringBuffer("Le niveau d'instruction n'existe pas. ");
                     erreur.setCode("200");
                     erreur.setDescription(text.toString());
                     niveauInstruction.addError(erreur);
                 }
              

             } catch (Exception e) {
                 com.oxia.fwk.core.Error erreur = new com.oxia.fwk.core.Error();
                 StringBuffer text = 
                     new StringBuffer("Erreur dans GetNiveauInstructionTrt : ");
                 text.append(e.toString());
                 erreur.setCode("200");
                 erreur.setDescription(text.toString());
                 niveauInstruction.addError(erreur);
                 logger.error("Exception : ",e);   
                 throw new RuntimeException(e);    
             }
             
         return (niveauInstruction);
     }
     
    public void genCroText(ValueObject vo) {
    
    }    
    
    public String getNumeroTache  (ValueObject vo) {
     return Constants.CODE_RESSOURCE_GENERALE;
    }
    
}
