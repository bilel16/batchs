package com.bna.smile.model.banqueAssurance.traitement;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.criterion.Order;

import com.bna.commun.model.AdhesionAssVie;
import com.bna.commun.traitements.Traitement;
import com.bna.commun.util.ContextHandler;
import com.bna.smile.model.constant.Constants;
import com.bna.smile.model.domainecommun.model.Listes;
import com.bna.smile.model.domainecontratcompte.moyensPaiement.model.ParamRechercheOpposition;
import com.oxia.fwk.context.Context;
import com.oxia.fwk.core.ICriteria;
import com.oxia.fwk.core.IExpression;
import com.oxia.fwk.core.ISearchEngine;
import com.oxia.fwk.core.IValueObject;
import com.oxia.fwk.core.ValueObject;
import com.oxia.fwk.searchengine.SearchEngine;

public class GetListAdhesionAssVieTrt extends Traitement{
    public GetListAdhesionAssVieTrt() {
    }
    /**
     * Fonction qui permet de determiner la liste des adhesion assurance vie pour un client / par période / selon l etat (valide, attente...)
     * @Author : lamia jerbi
     * @since 09/09/2010
     */
    public IValueObject perform (IValueObject vo ){  
        Listes listesAdhesionAssVie = new Listes();
        ParamRechercheOpposition paramRecherche = (ParamRechercheOpposition)vo; 
    try{
           List l = new ArrayList();
           Context context = ContextHandler.getContext();
           ISearchEngine searchEngine = (SearchEngine)context.getBean("searchEngine");
           ICriteria criteria = searchEngine.createCriteria();
           IExpression expression = searchEngine.createExpression();
    
           if (paramRecherche.getNumSeqPers() != null) { 
                     criteria.add(expression.eq("client.numSeqPers", 
                                                           paramRecherche.getNumSeqPers()));
               }
           if (paramRecherche.getCodStrcStrc() != null) { 
                     criteria.add(expression.eq("contratCpt.contratCptId.codStrcStrc", 
                                                           paramRecherche.getCodStrcStrc()));
           }
           if (paramRecherche.getCodPrdPrd() != null) { 
                     criteria.add(expression.eq("contratCpt.contratCptId.codPrdPrd", 
                                                           paramRecherche.getCodPrdPrd()));
           }
           if (paramRecherche.getNumCcptCcpt() != null) { 
                     criteria.add(expression.eq("contratCpt.contratCptId.numCcptCcpt", 
                                                           paramRecherche.getNumCcptCcpt()));
           }
        
           if (paramRecherche.getEtat() != null) { 
                     criteria.add(expression.eq("codEtatAdh", 
                                                           paramRecherche.getEtat()));
           }
           
           criteria.addOrder(Order.desc("numSeqAdh"));
           
         l = searchEngine.find(AdhesionAssVie.class, criteria);
        
         if(l != null ){
                    listesAdhesionAssVie.setList(l);
                    }
      
        return (listesAdhesionAssVie); 
       
       }catch(Exception e){
          com.oxia.fwk.core.Error erreur = new com.oxia.fwk.core.Error();
          StringBuffer text = 
              new StringBuffer("Erreur dans GetListAdhesionAssVieTrt : ");
          text.append(e.toString());
          erreur.setCode("200");
          erreur.setDescription(text.toString());
          erreur.setKey("GetListAdhesionAssVieTrt");
          logger.error("Exception : ",e);  
          throw new RuntimeException(e);
        }
     }
        
        public void genCroText(ValueObject vo){
            }
        public String  getNumeroTache (IValueObject vo) {
              return (Constants.CODE_RESSOURCE_GENERALE);     
          }
    }
