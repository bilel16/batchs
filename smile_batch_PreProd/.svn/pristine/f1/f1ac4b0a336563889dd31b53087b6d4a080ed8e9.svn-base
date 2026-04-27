package com.bna.smile.model.domainecontratcompte.moyensPaiement.traitement;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import org.hibernate.criterion.Order;

import com.bna.commun.model.DemandeCarte;
import com.bna.commun.model.Structure;
import com.bna.commun.traitements.Traitement;
import com.bna.commun.util.ContextHandler;
import com.bna.smile.model.constant.Constants;
import com.bna.smile.model.domainecommun.model.Listes;
import com.bna.smile.model.domainecontratcompte.moyensPaiement.model.ParamRechercheDemandeCarte;
import com.oxia.fwk.context.Context;
import com.oxia.fwk.core.ICriteria;
import com.oxia.fwk.core.IExpression;
import com.oxia.fwk.core.ISearchEngine;
import com.oxia.fwk.core.IValueObject;
import com.oxia.fwk.core.ValueObject;
import com.oxia.fwk.searchengine.SearchEngine;

/**
 * Classe de traitement :permet de donner la liste de toutes les demandes de cartes selon critères de recherches
 * @author Ramzi
 * @since 05/07/2007
 * 
 */
public class GetListDemandesCartesTrt extends Traitement{
    public GetListDemandesCartesTrt() {
    }
    
    public IValueObject perform(IValueObject vo) throws Exception{
        ParamRechercheDemandeCarte  paramRechercheDemandeCarte  = (ParamRechercheDemandeCarte )vo;
        Listes listesDemandeCarte = new Listes();
        try {
            Context context = ContextHandler.getContext();
            ISearchEngine searchEngine = 
                (SearchEngine)context.getBean("searchEngine");
            ICriteria   criteria       = searchEngine.createCriteria();
            IExpression expression     = searchEngine.createExpression();
            
            //recherche type 
            Structure structure = (Structure)searchEngine.get(Structure.class,paramRechercheDemandeCarte.getCodAgence());
            long typeStructure = structure.getTypeStructure().getCodTstrTstr().longValue();
            //critere de recherche si direction regionale
             
            if(typeStructure==1 || typeStructure==6){
                //remplir critère agence
                 if(paramRechercheDemandeCarte.getCodAgence()!=null) {
                     criteria.add(expression.eq("contratCpt.contratCptId.codStrcStrc", 
                                                paramRechercheDemandeCarte.getCodAgence()));
                 }
            //critere de recherche si direction regionale   
            }else if(typeStructure==2){
                //remplir critère agence
                 if(paramRechercheDemandeCarte.getCodAgence()!=null) {
                     ICriteria   criteriaAgence       = searchEngine.createCriteria();
                     criteriaAgence.add(expression.eq("structure.codStrcStrc", 
                                                paramRechercheDemandeCarte.getCodAgence()));
                     criteriaAgence.add(expression.in("typeStructure.codTstrTstr", Arrays.asList((new Long[]{Long.valueOf(1), Long.valueOf(6)}))));
                     List listAgence = searchEngine.find(Structure.class,criteriaAgence);
                     Iterator it = listAgence.iterator();
                     List listCodAgence= new ArrayList();
                     for(;it.hasNext();){
                         Structure str = (Structure)it.next();
                         listCodAgence.add(str.getCodStrcStrc());
                     }
                     
                     criteria.add(expression.in("contratCpt.contratCptId.codStrcStrc", listCodAgence));
                    
                 }
            //critere de recherche autre (dmone,dcci)
            }
            
            
            
            
            //remplir critère porteur
            if(paramRechercheDemandeCarte.getPorteur()!=null && paramRechercheDemandeCarte.getPorteur().getCodTpceTpce()!=null) {
                criteria.add(expression.eq("codTpceDcar", 
                                           paramRechercheDemandeCarte.getPorteur().getCodTpceTpce()));
                criteria.add(expression.eq("numPceDcar", 
                                           paramRechercheDemandeCarte.getPorteur().getNumPcePers()));
            }
         
            //remplir critère contrat            
            if(paramRechercheDemandeCarte.getContratCptId()!=null) {
                 criteria.add(expression.eq("contratCpt.contratCptId.codStrcStrc", 
                                            paramRechercheDemandeCarte.getContratCptId().getCodStrcStrc()));
                 criteria.add(expression.eq("contratCpt.contratCptId.codPrdPrd", 
                                            paramRechercheDemandeCarte.getContratCptId().getCodPrdPrd()));
                 criteria.add(expression.eq("contratCpt.contratCptId.numCcptCcpt", 
                                            paramRechercheDemandeCarte.getContratCptId().getNumCcptCcpt()));
            }
             
            //remplir critère num_demande   
             if(paramRechercheDemandeCarte.getNumRecherche()!=null) {
                  criteria.add(expression.eq("numDemDcar", 
                                             paramRechercheDemandeCarte.getNumRecherche()));
             }
             
            //remplir critère etats_demande   
             if(paramRechercheDemandeCarte.getEtatsRecherche()!=null) {     
                  criteria.add(expression.in("codEtatDcar", 
                                             paramRechercheDemandeCarte.getEtatsRecherche()));
             }  
             
             
            //remplir critère date demande >= date  exmple recherche par date demande   
             if(paramRechercheDemandeCarte.getDateDebutSup()!=null) {
                  criteria.add(expression.ge("datDemDcar", 
                                             paramRechercheDemandeCarte.getDateDebutSup()));
             } 
             
            //remplir critère date demande <= date  exmple recherche par date demande   
            if(paramRechercheDemandeCarte.getDateDebutInf()!=null) {
                criteria.add(expression.le("datDemDcar", 
                paramRechercheDemandeCarte.getDateDebutInf()));
            } 
            
            //remplir critère boolmodpDcar
             if(paramRechercheDemandeCarte.getBoolModifPlafond()!=null ) {
                //critere demande non de modification
                if(paramRechercheDemandeCarte.getBoolModifPlafond().equals(Long.valueOf("0"))){
                    criteria.add(expression.isNull("boolModpDcar"));
                }else{
                    criteria.add(expression.eq("boolModpDcar",Long.valueOf("1")));
                }
             }
            
            criteria.addOrder(Order.asc("numDemDcar"));
           
             List list = searchEngine.find(DemandeCarte.class,criteria);
             listesDemandeCarte.setList(list);
        
        } catch (Exception e) {
                com.oxia.fwk.core.Error erreur=new com.oxia.fwk.core.Error();
                erreur.setCode("Technique");
                erreur.setDescription("GetListDemandesCartesTrt "+e.getMessage());;
                listesDemandeCarte.addError(erreur);
                logger.error("Exception : ",e);
                throw new RuntimeException(e);       
        }
        return listesDemandeCarte;
    
    }
    public void genCroText(ValueObject vo) {
    
    }
    public String getNumeroTache(IValueObject vo){
       return Constants.CODE_RESSOURCE_GENERALE;   
    }
}
