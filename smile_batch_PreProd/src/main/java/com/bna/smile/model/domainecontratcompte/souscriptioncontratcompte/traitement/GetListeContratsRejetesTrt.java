package com.bna.smile.model.domainecontratcompte.souscriptioncontratcompte.traitement;

import java.util.List;

import com.bna.commun.model.ContratRejete;
import com.bna.commun.traitements.Traitement;
import com.bna.smile.model.constant.Constants;
import com.bna.smile.model.domainecommun.model.Listes;
import com.bna.smile.model.domainecontratcompte.moyensPaiement.model.ParamRechercheOpposition;
import com.oxia.fwk.context.Context;
import com.oxia.fwk.core.ICriteria;
import com.oxia.fwk.core.IExpression;
import com.oxia.fwk.core.ISearchEngine;
import com.oxia.fwk.core.IValueObject;
import com.oxia.fwk.core.ValueObject;

public class GetListeContratsRejetesTrt extends Traitement{
    
    public GetListeContratsRejetesTrt() {
    }
    
    
    /**
     * Fonction qui permet de determiner la liste des contrats rejetés
     * @Author : lamia jerbi
     * @since 26/05/2008
     */
     public IValueObject perform(IValueObject vo) {
    
        Listes listeCcptRejetes =new Listes();
        
        try{
            
             ISearchEngine searchEngine=(ISearchEngine)Context.getInstance().getSpringContext().getBean("searchEngine");
             ICriteria criteria = searchEngine.createCriteria();
             IExpression expression = searchEngine.createExpression();
             ParamRechercheOpposition paramRecherche = (ParamRechercheOpposition)vo; 
             //----------------------------------------------recherche par période
                 if (paramRecherche.getDateDebutConsult() != null) {
                     criteria.add(expression.gt("datRejCptr", 
                                                paramRecherche.getDateDebutConsult()));
                 }
                 if (paramRecherche.getDateFinConsult() != null) {
                     criteria.add(expression.le("datRejCptr", 
                                                paramRecherche.getDateFinConsult()));
                 }
             //----------------------------------------------recherche par num contrat cpt
                 if (paramRecherche.getCodPrdPrd() != null) {
                     criteria.add(expression.eq("codPrdCptr", 
                                                paramRecherche.getCodPrdPrd()));
                 }
                 if (paramRecherche.getCodStrcStrc() != null) {
                     criteria.add(expression.eq("codStrcCptr", 
                                                paramRecherche.getCodStrcStrc()));
                 }
                 if (paramRecherche.getNumCcptCcpt() != null) {
                     criteria.add(expression.eq("numCcptCptr", 
                                                paramRecherche.getNumCcptCcpt()));
                 }
                 //----------------------------------------------------------
                 
                  List l = searchEngine.find(ContratRejete.class, criteria);
          
             listeCcptRejetes.setList(l);
                  
             return (listeCcptRejetes);  
         }catch(Exception e){
            com.oxia.fwk.core.Error erreur = new com.oxia.fwk.core.Error();
            StringBuffer text = 
                new StringBuffer("Erreur dans GetListeContratsRejetesTrt : ");
            text.append(e.toString());
            erreur.setCode("200");
            erreur.setDescription(text.toString());
            listeCcptRejetes.addError(erreur);
            return (listeCcptRejetes);  
        }
   
    }
    
    
    public void genCroText(ValueObject vo) {    
    
    }
    
    public String getNumeroTache  (ValueObject vo) {
     return  Constants.CODE_RESSOURCE_GENERALE;
    }
    
}
