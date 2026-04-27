package com.bna.smile.model.traitementCompensationRecu.traitement;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.hibernate.criterion.Disjunction;
import org.hibernate.criterion.Restrictions;

import com.bna.commun.model.Cheque30;
import com.bna.commun.model.Cheque31;
import com.bna.commun.model.Cheque32;
import com.bna.commun.model.Cheque33;
import com.bna.commun.traitements.Traitement;
import com.bna.commun.util.ContextHandler;
import com.bna.commun.util.StrHandler;
import com.bna.smile.model.constant.Constants;
import com.bna.smile.model.traitementCompensationRecu.model.ChequeRecu;
import com.bna.smile.model.traitementCompensationRecu.model.ListChequesRecusVo;
import com.oxia.fwk.context.Context;
import com.oxia.fwk.core.ICriteria;
import com.oxia.fwk.core.ICriterion;
import com.oxia.fwk.core.IExpression;
import com.oxia.fwk.core.ISearchEngine;
import com.oxia.fwk.core.IValueObject;
import com.oxia.fwk.core.ValueObject;
import com.oxia.fwk.searchengine.SearchEngine;


public class GetListChequesRecusTrt extends Traitement {
    public GetListChequesRecusTrt() {
    }
    public Context context = ContextHandler.getContext();

    public IValueObject perform (IValueObject vo){
    	ListChequesRecusVo listChequesRecusVo=(ListChequesRecusVo) vo;
    	
         try{
         
            ISearchEngine searchEngine = (SearchEngine)context.getBean("searchEngine"); 
            IExpression expression     = searchEngine.createExpression();
            
           
           if((listChequesRecusVo.getTypeCheque()!=null && listChequesRecusVo.getTypeCheque().equals(Constants.COD_CHEQUE_PREMIERE_PRESENTATION) ) || listChequesRecusVo.getTypeCheque()==null){
        	   ICriteria criteria = searchEngine.createCriteria();
        	   if(listChequesRecusVo.getDateComptable()!=null){
            	   criteria.add(expression.eq("cheque30Id.datJou",listChequesRecusVo.getDateComptable())); /// Date   
               }
               if(listChequesRecusVo.getEtat()!=null){
            	   criteria.add(expression.eq("codEtatChq",listChequesRecusVo.getEtat())); /// Etat
               }else{
					criteria.add(expression.or(expression.eq("codEtatChq","T"), expression.isNull("codEtatChq")));

               }
               if(listChequesRecusVo.getStructure()!=null && !listChequesRecusVo.getStructure().equals("") ){
            	   criteria.add(expression.eq("cheque30Id.codUg",listChequesRecusVo.getStructure())); /// Structure   
               }
               
        	   List listCheques = searchEngine.find(Cheque30.class,criteria);  
        	   List<Cheque30> listCheques30 =new ArrayList<Cheque30>();
        	   for (Iterator it = listCheques.iterator(); it.hasNext();) {
   				Cheque30 cheque30 = (Cheque30) it.next();
   				listCheques30.add(cheque30);
   			   }
        	   listChequesRecusVo.setListCheques30(listCheques30); 
           }
           if((listChequesRecusVo.getTypeCheque()!=null && listChequesRecusVo.getTypeCheque().equals(Constants.COD_CHEQUE_REPRESENTATION_PAIEMENT_PARTIEL) ) || listChequesRecusVo.getTypeCheque()==null){
        	   ICriteria criteria1 = searchEngine.createCriteria();
        	   if(listChequesRecusVo.getDateComptable()!=null){
            	   criteria1.add(expression.eq("cheque31Id.datJou",listChequesRecusVo.getDateComptable())); /// Date   
               }
               if(listChequesRecusVo.getEtat()!=null){
            	   criteria1.add(expression.eq("codEtatChq",listChequesRecusVo.getEtat())); /// Etat
               }else{
            	   criteria1.add(expression.isNull("codEtatChq"));
               }
               if(listChequesRecusVo.getStructure()!=null && !listChequesRecusVo.getStructure().equals("") ){
            	   criteria1.add(expression.eq("cheque31Id.codUg",StrHandler.lpad(listChequesRecusVo.getStructure().toString(), '0', 3))); /// Structure      
               }
        	   List listCheques = searchEngine.find(Cheque31.class,criteria1);
        	   List<Cheque31> listCheques31 =new ArrayList<Cheque31>();
        	   for (Iterator it = listCheques.iterator(); it.hasNext();) {
   				Cheque31 cheque31 = (Cheque31) it.next();
   				listCheques31.add(cheque31);
   			   }
        	   listChequesRecusVo.setListCheques31(listCheques31);  
           }
           if((listChequesRecusVo.getTypeCheque()!=null && listChequesRecusVo.getTypeCheque().equals(Constants.COD_CHEQUE_REPRESENTATION_SUITE_ARP) ) || listChequesRecusVo.getTypeCheque()==null){
        	   ICriteria criteria2 = searchEngine.createCriteria();
        	   if(listChequesRecusVo.getDateComptable()!=null){
            	   criteria2.add(expression.eq("cheque32Id.datJou",listChequesRecusVo.getDateComptable())); /// Date   
               }
               if(listChequesRecusVo.getEtat()!=null){
            	   criteria2.add(expression.eq("codEtatChq",listChequesRecusVo.getEtat())); /// Etat
               }else{
            	   criteria2.add(expression.isNull("codEtatChq"));
               }
               if(listChequesRecusVo.getStructure()!=null && !listChequesRecusVo.getStructure().equals("") ){
            	   criteria2.add(expression.eq("cheque32Id.codUg",StrHandler.lpad(listChequesRecusVo.getStructure().toString(), '0', 3))); /// Structure   
               }
        	   List listCheques = searchEngine.find(Cheque32.class,criteria2); 
        	   List<Cheque32> listCheques32 =new ArrayList<Cheque32>();
        	   for (Iterator it = listCheques.iterator(); it.hasNext();) {
   				Cheque32 cheque32 = (Cheque32) it.next();
   				listCheques32.add(cheque32);
   			   }
        	   listChequesRecusVo.setListCheques32(listCheques32); 
           }
           if((listChequesRecusVo.getTypeCheque()!=null && listChequesRecusVo.getTypeCheque().equals(Constants.COD_CHEQUE_REPRESENTATION_SUITE_PAPILLON) ) || listChequesRecusVo.getTypeCheque()==null){
        	   ICriteria criteria3  = searchEngine.createCriteria();
        	   if(listChequesRecusVo.getDateComptable()!=null){
            	   criteria3.add(expression.eq("cheque33Id.datJou",listChequesRecusVo.getDateComptable())); /// Date   
               }
               if(listChequesRecusVo.getEtat()!=null){
            	   criteria3.add(expression.eq("codEtatChq",listChequesRecusVo.getEtat())); /// Etat
               }else{
					criteria3.add(expression.or(expression.eq("codEtatChq","T"), expression.isNull("codEtatChq")));

               }
               if(listChequesRecusVo.getStructure()!=null && !listChequesRecusVo.getStructure().equals("") ){
            	   criteria3.add(expression.eq("cheque33Id.codUg",StrHandler.lpad(listChequesRecusVo.getStructure(), '0', 3))); /// Structure   
               }
        	   List listCheques = searchEngine.find(Cheque33.class,criteria3);  
        	   List<Cheque33> listCheques33 =new ArrayList<Cheque33>();
        	   for (Iterator it = listCheques.iterator(); it.hasNext();) {
   				Cheque33 cheque33 = (Cheque33) it.next();
   				listCheques33.add(cheque33);
   			   }
        	   listChequesRecusVo.setListCheques33(listCheques33); 
           }
         if(listChequesRecusVo.isAllCheques()==true){
        List<ChequeRecu> listChequesRecus = new ArrayList<ChequeRecu>();
   		if(listChequesRecusVo.getListCheques30()!=null) {
   		for (int i=0;i<listChequesRecusVo.getListCheques30().size();i++) {
   			    Cheque30 cheque30=new Cheque30();
   			    cheque30=listChequesRecusVo.getListCheques30().get(i);
   				ChequeRecu chequeRecu=new ChequeRecu();
   				chequeRecu.setCodStrcStrc(cheque30.getCheque30Id().getCodUg());
   				chequeRecu.setDatJou(cheque30.getCheque30Id().getDatJou());
   				chequeRecu.setCodValVal(cheque30.getCodVal());
   				chequeRecu.setCodRej1(cheque30.getCodRej1());
   				chequeRecu.setCodRej2(cheque30.getCodRej2());
   				chequeRecu.setCodRej3(cheque30.getCodRej3());
   				chequeRecu.setCodRej4(cheque30.getCodRej4());
   				//chequeRecu.setImgRec(cheque30.getImgRec());
   				//chequeRecu.setImgVer(cheque30.getImgVer());
   				chequeRecu.setMntChq(cheque30.getMntChq());
   				chequeRecu.setRibBen(cheque30.getRibBen());
   				chequeRecu.setRibTir(cheque30.getRibTir());
   				chequeRecu.setNomBen(cheque30.getNomBen());
   				chequeRecu.setNumChq(cheque30.getNumChq());
   				
   				listChequesRecus.add(chequeRecu);
   			   }
   		}
   		if(listChequesRecusVo.getListCheques31()!=null) {
   			for (int i=0;i<listChequesRecusVo.getListCheques31().size();i++) {
   				    Cheque31 cheque31=new Cheque31();
   				    cheque31=listChequesRecusVo.getListCheques31().get(i);
   					ChequeRecu chequeRecu=new ChequeRecu();
   					chequeRecu.setCodStrcStrc(cheque31.getCheque31Id().getCodUg());
   					chequeRecu.setDatJou(cheque31.getCheque31Id().getDatJou());
   					chequeRecu.setCodValVal(cheque31.getCodVal());
   					chequeRecu.setCodRej1(cheque31.getCodRej1());
   					chequeRecu.setCodRej2(cheque31.getCodRej2());
   					chequeRecu.setCodRej3(cheque31.getCodRej3());
   					chequeRecu.setCodRej4(cheque31.getCodRej4());
   					chequeRecu.setMntChq(cheque31.getMntChq());
   					chequeRecu.setRibBen(cheque31.getRibBen());
   					chequeRecu.setRibTir(cheque31.getRibTir());
   					chequeRecu.setNumChq(cheque31.getNumChq());
   					listChequesRecus.add(chequeRecu);
   				   }
   			}
   		if(listChequesRecusVo.getListCheques32()!=null) {
   			for (int i=0;i<listChequesRecusVo.getListCheques32().size();i++) {
   				    Cheque32 cheque32=new Cheque32();
   				    cheque32=listChequesRecusVo.getListCheques32().get(i);
   					ChequeRecu chequeRecu=new ChequeRecu();
   					chequeRecu.setCodStrcStrc(cheque32.getCheque32Id().getCodUg());
   					chequeRecu.setDatJou(cheque32.getCheque32Id().getDatJou());
   					chequeRecu.setCodValVal(cheque32.getCodVal());
   					chequeRecu.setCodRej1(cheque32.getCodRej1());
   					chequeRecu.setCodRej2(cheque32.getCodRej2());
   					chequeRecu.setCodRej3(cheque32.getCodRej3());
   					chequeRecu.setCodRej4(cheque32.getCodRej4());
   					chequeRecu.setMntChq(cheque32.getMntChq());
   					chequeRecu.setRibBen(cheque32.getRibBen());
   					chequeRecu.setRibTir(cheque32.getRibTir());
   					chequeRecu.setNumChq(cheque32.getNumChq());
   					listChequesRecus.add(chequeRecu);
   				   }
   			}
   		if(listChequesRecusVo.getListCheques33()!=null) {
   			for (int i=0;i<listChequesRecusVo.getListCheques33().size();i++) {
   				    Cheque33 cheque33=new Cheque33();
   				    cheque33=listChequesRecusVo.getListCheques33().get(i);
   					ChequeRecu chequeRecu=new ChequeRecu();
   					chequeRecu.setCodStrcStrc(cheque33.getCheque33Id().getCodUg());
   					chequeRecu.setDatJou(cheque33.getCheque33Id().getDatJou());
   					chequeRecu.setCodValVal(cheque33.getCodVal());
   					chequeRecu.setCodRej1(cheque33.getCodRej1());
   					chequeRecu.setCodRej2(cheque33.getCodRej2());
   					chequeRecu.setCodRej3(cheque33.getCodRej3());
   					chequeRecu.setCodRej4(cheque33.getCodRej4());
   					//chequeRecu.setImgRec(cheque33.getImgRec());
   					//chequeRecu.setImgVer(cheque33.getImgVer());
   					chequeRecu.setMntChq(cheque33.getMntChq());
   					chequeRecu.setRibBen(cheque33.getRibBen());
   					chequeRecu.setRibTir(cheque33.getRibTir());
   					chequeRecu.setNomBen(cheque33.getNomBen());
   					chequeRecu.setNumChq(cheque33.getNumChq());
   					listChequesRecus.add(chequeRecu);
   				   }
   			}
   		
   	    	listChequesRecusVo.setListChequesRecus(listChequesRecus);
         }
 
            return(listChequesRecusVo);
            
            
        }catch(Exception e)  {
            com.oxia.fwk.core.Error erreur = new com.oxia.fwk.core.Error();
            StringBuffer text = new StringBuffer("Erreur dans GetListChequesRecusTrt / perform: ");
            text.append(e.toString());
            erreur.setCode("500");
            erreur.setDescription(text.toString());
            erreur.setKey("GetListChequesRecusTrt");
            listChequesRecusVo.addError(erreur);
            return (listChequesRecusVo);
        }  
    }
    
    public void genCroText(ValueObject vo){
        
    }
  
}
