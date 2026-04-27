package com.bna.smile.model.domainecommun.traitement;

import com.bna.commun.model.Structure;
import com.bna.commun.traitements.Traitement;

import com.bna.smile.model.constant.Constants;

import com.oxia.fwk.context.Context;
import com.oxia.fwk.core.ICriteria;
import com.oxia.fwk.core.IExpression;
import com.oxia.fwk.core.ISearchEngine;
import com.oxia.fwk.core.IValueObject;
import com.oxia.fwk.core.ValueObject;

import java.util.List;

public class GetStructureTrt extends Traitement{

    /**
     * methode permettant la recherche d'une structure
     * @param vo :Objet : Structure 
     * @return   :Objet : Structure
     */
    public

    IValueObject perform(IValueObject vo) {
        Structure structure = (Structure) vo;
        try{
            ISearchEngine searchEngine=(ISearchEngine)Context.getInstance().getSpringContext().getBean("searchEngine");
            ICriteria criteria = searchEngine.createCriteria();
            IExpression expression = searchEngine.createExpression();   
            
            criteria.add(expression.eq("codStrcStrc", structure.getCodStrcStrc()));
            
             List listStructure = searchEngine.find(Structure.class, criteria);

            if (listStructure != null) {
                if ( listStructure.size() > 0){
                    structure = (Structure)listStructure.get(0);                    
                }
            }
        
      

        } catch (Exception e) {
            com.oxia.fwk.core.Error erreur = new com.oxia.fwk.core.Error();
            StringBuffer text = 
                new StringBuffer("Erreur dans GetStructureTrt : ");
            text.append(e.toString());
            erreur.setCode("100");
            erreur.setDescription(text.toString());
            erreur.setKey("GetStructureTrt");
            structure.addError(erreur);
            logger.error("Exception : ",e);   
           
        }       
            return (structure);
        }

        public void genCroText(ValueObject vo) {
        
        }
        
        public String getNumeroTache(IValueObject vo) {
        return (Constants.CODE_RESSOURCE_GENERALE);
        }

}
