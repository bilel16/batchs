package com.bna.smile.model.domaineguichet.traitement;

import com.bna.commun.model.MontantMiseDiposition;
import com.bna.commun.model.OperationMoyPay;
import com.bna.commun.traitements.Traitement;

import com.bna.commun.util.ContextHandler;
import com.bna.commun.util.DateHandler;
import com.bna.commun.vo.PrimitiveVO;

import com.bna.smile.model.constant.Constants;
import com.bna.smile.model.domaineguichet.model.ListMiseAdispositionVo;

import com.oxia.fwk.context.Context;
import com.oxia.fwk.core.ICriteria;
import com.oxia.fwk.core.IExpression;
import com.oxia.fwk.core.ISearchEngine;
import com.oxia.fwk.core.IValueObject;
import com.oxia.fwk.core.ValueObject;
import com.oxia.fwk.searchengine.SearchEngine;

import java.util.ArrayList;
import java.util.List;

/**
 * Classe d'extraction de la list des Mises à disposition avec des critères :
 * Etat / Type /Date ...
 * @23/11/2007 
 * @author Mdimagh Med Lassaad
 */
public class GetListMontantMADTrt extends Traitement {
    public GetListMontantMADTrt() {
    }
    public Context context = ContextHandler.getContext();

    public IValueObject perform (IValueObject vo){
        ListMiseAdispositionVo listMiseAdispositionVo = (ListMiseAdispositionVo) vo;
        try{
         
            ISearchEngine searchEngine = (SearchEngine)context.getBean("searchEngine"); 
            ICriteria criteria         = searchEngine.createCriteria();
            IExpression expression     = searchEngine.createExpression();
            
            criteria.add(expression.eq("datMmadMmad",listMiseAdispositionVo.getDateMAD())); /// Date 
            criteria.add(expression.eq("codEtatMmad",listMiseAdispositionVo.getEtatMAD()));    /// Eta 
            criteria.add(expression.eq("montantMiseDipositionId.codTypeMmad",listMiseAdispositionVo.getTypeMAD()));  /// Type
            
            if (listMiseAdispositionVo.getStructureInitiatrice() != null && (!listMiseAdispositionVo.getStructureInitiatrice().equals("")) ){
                criteria.add(expression.eq("structureByCodEmetStrc.codStrcStrc",listMiseAdispositionVo.getStructureInitiatrice()));  /// structure
            }
            
            List l = searchEngine.find(MontantMiseDiposition.class,criteria);
            listMiseAdispositionVo.setListMiseAdisposition(l);
            
            return(listMiseAdispositionVo);
            
            
        }catch(Exception e)  {
            com.oxia.fwk.core.Error erreur = new com.oxia.fwk.core.Error();
            StringBuffer text = 
                new StringBuffer("Erreur dans GetListMontantMADTrt / perform: ");
            text.append(e.toString());
            erreur.setCode("500");
            erreur.setDescription(text.toString());
            erreur.setKey("GetOperationMoyPayTrt");
            listMiseAdispositionVo.addError(erreur);
            return (listMiseAdispositionVo);
        }
        
            
    }
    
    public void genCroText(ValueObject vo){
        
    }
    
    public String getNumeroTache(IValueObject vo){
       return Constants.CODE_RESSOURCE_GENERALE;   
    }
}
