package com.bna.smile.model.domainecontratcompte.moyensPaiement.traitement;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import org.hibernate.criterion.Order;

import com.bna.commun.model.CarteBancaire;
import com.bna.commun.model.Structure;
import com.bna.commun.traitements.Traitement;
import com.bna.commun.util.ContextHandler;
import com.bna.smile.model.constant.Constants;
import com.bna.smile.model.domainecommun.model.Listes;
import com.bna.smile.model.domainecontratcompte.moyensPaiement.model.ParamRechercheDemandeCarte;
import com.oxia.fwk.context.Context;
import com.oxia.fwk.core.ICriteria;
import com.oxia.fwk.core.ICriterion;
import com.oxia.fwk.core.IExpression;
import com.oxia.fwk.core.ISearchEngine;
import com.oxia.fwk.core.IValueObject;
import com.oxia.fwk.core.ValueObject;
import com.oxia.fwk.searchengine.SearchEngine;

/**
 * Classe de traitement :permet de donner la liste des cartes selon critères de recherches
 * @author Ramzi
 * @since 23/07/2007
 * 
 */
public class GetListCartesBancairesTrt extends Traitement{
    public GetListCartesBancairesTrt() {
    }
    
    public IValueObject perform(IValueObject vo) throws Exception{
        ParamRechercheDemandeCarte  paramRechercheDemandeCarte  = (ParamRechercheDemandeCarte )vo;
        Listes listesCartesBancaires = new Listes();
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
                criteria.add(expression.eq("codTpceCarb", 
                                           paramRechercheDemandeCarte.getPorteur().getCodTpceTpce()));
                criteria.add(expression.eq("numPceCarb", 
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
             
            //remplir critère num_cart  
             if(paramRechercheDemandeCarte.getNumRecherche()!=null) {
                 criteria.add(expression.eq("carteBancaireId.codBinTcar", 
                                             Long.valueOf(paramRechercheDemandeCarte.getNumRecherche().substring(0,6))));
                 criteria.add(expression.eq("carteBancaireId.numCarbCarb", 
                                            Long.valueOf(paramRechercheDemandeCarte.getNumRecherche().substring(6))));
             }
             
            //remplir critère etats_carte   
             if(paramRechercheDemandeCarte.getEtatsRecherche()!=null) {  
                  if(paramRechercheDemandeCarte.getDateDernOper()!=null){
                      ICriterion   iCreteRestitue   = expression.eq("codEtatCarb", 
                                               Constants.COD_ETAT_CARB_CarteRestituee);
                      ICriterion   iCreteCarteMalConfection   = expression.eq("codEtatCarb", 
                                               Constants.COD_ETAT_CARB_CarteMalConfect);
                      ICriterion   iCreteNonDelivre   = expression.and(expression.lt("datOperCarb", 
                                               paramRechercheDemandeCarte.getDateDernOper()),expression.eq("codEtatCarb", 
                                               Constants.COD_ETAT_CARB_CarteRecu));
                      criteria.add(expression.or(expression.or(iCreteRestitue,iCreteCarteMalConfection),iCreteNonDelivre));    
                  }else{
                      criteria.add(expression.in("codEtatCarb", 
                                               paramRechercheDemandeCarte.getEtatsRecherche())); 
                      
                      //si cas opposition, restitution, destruction ordonner selon date oppostion desc
                      if(paramRechercheDemandeCarte.getEtatsRecherche()[0].equals(Constants.COD_ETAT_CARB_EnOpposition)
                      || paramRechercheDemandeCarte.getEtatsRecherche()[0].equals(Constants.COD_ETAT_CARB_CarteRestituee)
                      || paramRechercheDemandeCarte.getEtatsRecherche()[0].equals(Constants.COD_ETAT_CARB_CarteDetruite) ){
                        criteria.addOrder(Order.desc("datOperCarb"));
                      }                          
                      
                  }
                 
             }  
             
            //remplir critère date creation >= date  exmple recherche par date création   
             if(paramRechercheDemandeCarte.getDateDebutSup()!=null) {
                  criteria.add(expression.ge("datCreCarb", 
                                             paramRechercheDemandeCarte.getDateDebutSup()));
             } 
             
            //remplir critère date creation <= date  exmple recherche par date création   
            if(paramRechercheDemandeCarte.getDateDebutInf()!=null) {
                criteria.add(expression.le("datCreCarb", 
                paramRechercheDemandeCarte.getDateDebutInf()));
            } 
             
            //remplir critère date fin <= date exmple: sydate + 2 >= date fin (cas Renouvellement Automatique) (
             if(paramRechercheDemandeCarte.getDateFinInf()!=null) {
                  criteria.add(expression.le("datFinCarb", 
                                             paramRechercheDemandeCarte.getDateFinInf()));
             } 
             
            //remplir critère sur date fin >= date fin exmemple sysdate <= datre fin 
             if(paramRechercheDemandeCarte.getDateFinSup()!=null) {
                  criteria.add(expression.ge("datFinCarb", 
                                             paramRechercheDemandeCarte.getDateFinSup()));
             } 
             
            //remplir critère boolAnnulerRenouv
             if(paramRechercheDemandeCarte.getBoolAnnulerRenouv()!=null ) {
                //critere carte non encore annulée
                if(paramRechercheDemandeCarte.getBoolAnnulerRenouv().equals(Long.valueOf("0"))){
                    criteria.add(expression.isNull("boolAnnlCarb"));
                }else{
                    criteria.add(expression.eq("boolAnnlCarb",Long.valueOf("1")));
                }
             }
             
            //remplir critère numDemande is not null
             criteria.add(expression.isNotNull("demandeCarte.numDemDcar"));
             
             criteria.addOrder(Order.asc("datCreCarb"));
             
             List list = searchEngine.find(CarteBancaire.class,criteria);
             listesCartesBancaires.setList(list);
        
        } catch (Exception e) {
                com.oxia.fwk.core.Error erreur=new com.oxia.fwk.core.Error();
                erreur.setCode("Technique");
                erreur.setDescription("GetListDemandesCartesTrt "+e.getMessage());;
                listesCartesBancaires.addError(erreur);
                logger.error("Exception : ",e);
                throw new RuntimeException(e);       
        }
        return listesCartesBancaires;
    
    }
    public void genCroText(ValueObject vo) {
    
    }
    public String getNumeroTache(IValueObject vo){
       return Constants.CODE_RESSOURCE_GENERALE;   
    }
}
