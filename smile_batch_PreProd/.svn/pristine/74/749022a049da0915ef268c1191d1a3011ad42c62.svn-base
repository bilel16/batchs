package com.bna.smile.model.traitementCompensationRecu.traitement;

import java.util.List;
import com.bna.commun.model.EffetRecuTmp;
import com.bna.commun.traitements.Traitement;
import com.bna.commun.util.ContextHandler;
import com.bna.commun.util.StrHandler;
import com.bna.smile.model.traitementCompensationRecu.model.ListEffetsRecusVo;
import com.oxia.fwk.context.Context;
import com.oxia.fwk.core.ICriteria;
import com.oxia.fwk.core.IExpression;
import com.oxia.fwk.core.ISearchEngine;
import com.oxia.fwk.core.IValueObject;
import com.oxia.fwk.core.ValueObject;
import com.oxia.fwk.searchengine.SearchEngine;


public class GetListEffetsRecusTrt extends Traitement {
    public GetListEffetsRecusTrt() {
    }
    public Context context = ContextHandler.getContext();

    public IValueObject perform (IValueObject vo){
    	ListEffetsRecusVo listEffetsRecusVo=(ListEffetsRecusVo) vo;
    	
         try{
         
            ISearchEngine searchEngine = (SearchEngine)context.getBean("searchEngine"); 
            IExpression expression     = searchEngine.createExpression();
            
           
           if((listEffetsRecusVo.getCodEnr()!=null && listEffetsRecusVo.getCodEnr().equals(Long.valueOf(21)))){
        	   ICriteria criteria1 = searchEngine.createCriteria();
        	   criteria1.add(expression.eq("codEnr",Long.valueOf(21))); /// Date   

        	   if(listEffetsRecusVo.getDateComptable()!=null){
            	   criteria1.add(expression.eq("effetId.datOpe",listEffetsRecusVo.getDateComptable())); /// Date   
               }
               if(listEffetsRecusVo.getEtat()!=null){
            	   criteria1.add(expression.eq("codEtatEff",listEffetsRecusVo.getEtat())); /// Etat
               }else{
            	   criteria1.add(expression.isNull("codEtatEff"));
               }
               if(listEffetsRecusVo.getStructure()!=null){
            	   criteria1.add(expression.eq("codAgeDes",StrHandler.lpad(listEffetsRecusVo.getStructure().getCodBctStrc(), '0', 3))); /// Structure      
               }
               List<EffetRecuTmp> list21 = searchEngine.find(EffetRecuTmp.class,criteria1);
               listEffetsRecusVo.setListEffet21(list21);  
               
               
           }
           
           
           if((listEffetsRecusVo.getCodEnr()!=null && listEffetsRecusVo.getCodEnr().equals(Long.valueOf(22)))){
        	   ICriteria criteria1 = searchEngine.createCriteria();
        	   criteria1.add(expression.eq("codEnr",Long.valueOf(22))); /// Date   

        	   if(listEffetsRecusVo.getDateComptable()!=null){
            	   criteria1.add(expression.eq("effetId.datOpe",listEffetsRecusVo.getDateComptable())); /// Date   
               }
               if(listEffetsRecusVo.getEtat()!=null){
            	   criteria1.add(expression.eq("codEtatEff",listEffetsRecusVo.getEtat())); /// Etat
               }else{
            	   criteria1.add(expression.isNull("codEtatEff"));
               }
               if(listEffetsRecusVo.getStructure()!=null){
            	   criteria1.add(expression.eq("codAgeDes",StrHandler.lpad(listEffetsRecusVo.getStructure().getCodBctStrc(), '0', 3))); /// Structure      
               }
               List<EffetRecuTmp> list22 = searchEngine.find(EffetRecuTmp.class,criteria1);
               listEffetsRecusVo.setListEffet22(list22);  
           }
           
           
           
           if((listEffetsRecusVo.getCodEnr()!=null && listEffetsRecusVo.getCodEnr().equals(Long.valueOf(25)))){
        	   ICriteria criteria1 = searchEngine.createCriteria();
        	   criteria1.add(expression.eq("codEnr",Long.valueOf(25))); /// Date   

        	   if(listEffetsRecusVo.getDateComptable()!=null){
            	   criteria1.add(expression.eq("effetId.datOpe",listEffetsRecusVo.getDateComptable())); /// Date   
               }
               if(listEffetsRecusVo.getEtat()!=null){
            	   criteria1.add(expression.eq("codEtatEff",listEffetsRecusVo.getEtat())); /// Etat
               }else{
            	   criteria1.add(expression.isNull("codEtatEff"));
               }
               if(listEffetsRecusVo.getStructure()!=null){
            	   criteria1.add(expression.eq("codAgeDes",StrHandler.lpad(listEffetsRecusVo.getStructure().getCodBctStrc(), '0', 3))); /// Structure      
               }
               List<EffetRecuTmp> list25 = searchEngine.find(EffetRecuTmp.class,criteria1);
               listEffetsRecusVo.setListEffet25(list25);  
           }
  
            return(listEffetsRecusVo);
            
           
        }catch(Exception e)  {
            com.oxia.fwk.core.Error erreur = new com.oxia.fwk.core.Error();
            StringBuffer text = new StringBuffer("Erreur dans GetListChequesRecusTrt / perform: ");
            text.append(e.toString());
            erreur.setCode("500");
            erreur.setDescription(text.toString());
            erreur.setKey("GetListChequesRecusTrt");
            listEffetsRecusVo.addError(erreur);
            return (listEffetsRecusVo);
        }  
    }
    
    public void genCroText(ValueObject vo){
        
    }
  
}
