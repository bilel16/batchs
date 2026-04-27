
package com.bna.smile.model.domainecontratcompte.souscriptioncontratcompte.traitement;


import java.util.List;

import com.bna.commun.model.ContratCptId;
import com.bna.commun.model.DetailCatCpt;
import com.bna.commun.model.Produit;
import com.bna.commun.traitements.Traitement;
import com.bna.smile.model.constant.Constants;
import com.oxia.fwk.context.Context;
import com.oxia.fwk.core.ICriteria;
import com.oxia.fwk.core.IExpression;
import com.oxia.fwk.core.ISearchEngine;
import com.oxia.fwk.core.IValueObject;
import com.oxia.fwk.core.ValueObject;

public class GetCategorieContratTrt extends Traitement{

   

    public GetCategorieContratTrt() {
    }

    /**
     * methode permettant de determiner la catégorie d'un contrat epargne donnée * 
     * @param vo : IdContratCpt
     * @return detailCatCpt
     * @author El arbi hassine
     */
    public IValueObject perform(IValueObject vo) {

        ISearchEngine searchEngine=(ISearchEngine)Context.getInstance().getSpringContext().getBean("searchEngine");
        ICriteria criteria = searchEngine.createCriteria();
        IExpression expression = searchEngine.createExpression();

        ContratCptId contratCptId = (ContratCptId)vo;
        
        DetailCatCpt detailCatCpt = new DetailCatCpt();
        this.setCroFlag(false);
        /* determinet la famille et la sous famille du produit */
    try{
        Produit produit = new Produit();        
        produit = (Produit)searchEngine.get(Produit.class, contratCptId.getCodPrdPrd());
        
        if (produit.getGroupeProduit().getGroupeProduitId().getCodGfamGp().equals(Constants.COD_GFAM_PRD)
            && produit.getGroupeProduit().getGroupeProduitId().getCodSfamSfp().equals(Constants.COD_SFAM_PRD)){
           
             /* Rechercher du DetailCatCpt */
             criteria.add(expression.eq("contratCpt.contratCptId.codPrdPrd", 
                                        contratCptId.getCodPrdPrd()));
             criteria.add(expression.eq("contratCpt.contratCptId.codStrcStrc", 
                                        contratCptId.getCodStrcStrc()));
             criteria.add(expression.eq("contratCpt.contratCptId.numCcptCcpt", 
                                        contratCptId.getNumCcptCcpt()));
             criteria.add(expression.isNull("datFinDcc"));

             List l = searchEngine.find(DetailCatCpt.class, criteria);

             if (l != null && l.size() > 0) {
                 detailCatCpt = (DetailCatCpt)l.get(0);
             }  
         }
        
       
     } catch (Exception e) {
            com.oxia.fwk.core.Error erreur = new com.oxia.fwk.core.Error();
            StringBuffer text = 
                new StringBuffer("Erreur dans GetCategorieContratTrt : ");
            text.append(e.toString());
            erreur.setCode("100");
            erreur.setDescription(text.toString());
            erreur.setKey("GetCategorieContrat");
            detailCatCpt.addError(erreur);
            logger.error("Exception : ",e); 
            throw new RuntimeException(e);  
           
      } 
        return (detailCatCpt);
    }

    public void genCroText(ValueObject vo) {    
    
    }
    
    public String getNumeroTache(IValueObject vo) {
      return (Constants.CODE_RESSOURCE_GENERALE);        
    }
}


