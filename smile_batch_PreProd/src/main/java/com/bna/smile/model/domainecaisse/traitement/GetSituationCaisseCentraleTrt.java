package com.bna.smile.model.domainecaisse.traitement;

import java.util.List;

import com.bna.commun.model.CaisseCentrale;
import com.bna.commun.model.CaisseDeviseCentrale;
import com.bna.commun.model.CaisseDeviseCentraleId;
import com.bna.commun.model.CaisseDinarsCentrale;
import com.bna.commun.model.CaisseDinarsCentraleId;
import com.bna.commun.model.DetailCaisDevCen;
import com.bna.commun.traitements.Traitement;
import com.bna.commun.util.ContextHandler;
import com.bna.smile.model.domainecaisse.model.SituationCaisseCentraleVo;
import com.oxia.fwk.context.Context;
import com.oxia.fwk.core.ICriteria;
import com.oxia.fwk.core.IExpression;
import com.oxia.fwk.core.ISearchEngine;
import com.oxia.fwk.core.IValueObject;
import com.oxia.fwk.core.ValueObject;
import com.oxia.fwk.searchengine.SearchEngine;

/**
 * classe pour la création d'une caisse 
 * @author Mdimagh Med Lassaad
 * @since 21/12/2007
 */
public class GetSituationCaisseCentraleTrt extends Traitement {
    Context context = ContextHandler.getContext();
    CaisseCentrale caisseCentrale;
    public GetSituationCaisseCentraleTrt() {
    }
    
    public  IValueObject perform(IValueObject vo) throws Exception {
        SituationCaisseCentraleVo  situationCaisseCentraleVo = (SituationCaisseCentraleVo)vo;
     try {
         
         Context context = ContextHandler.getContext();
         ISearchEngine searchEngine = 
             (SearchEngine)context.getBean("searchEngine");
         ICriteria criteria = searchEngine.createCriteria();
         IExpression expression = searchEngine.createExpression();   
         
         criteria.add(expression.eq("caisseCentraleId.codStrcStrc", situationCaisseCentraleVo.getCodeStructure()));
         criteria.add(expression.eq("caisseCentraleId.datJrnJrn",   situationCaisseCentraleVo.getDateJournee() ));
         
         List listCaisseCentrale = searchEngine.find(CaisseCentrale.class, criteria);
         
         if (listCaisseCentrale != null && listCaisseCentrale.size() > 0) {
             caisseCentrale = (CaisseCentrale)listCaisseCentrale.get(0);
         }
         
                  
         
         //------- Si la caisse centrale est initialisée
         if (caisseCentrale != null){
             situationCaisseCentraleVo.setExistCaisseCentrale(true);
             situationCaisseCentraleVo.setCaisseCentrale(caisseCentrale);
             //---------------------------------------------------
             //-------Recherche s'il y a caisse en dinars --------
             
              CaisseDinarsCentraleId caisseDinarsCentraleId = new CaisseDinarsCentraleId();
              caisseDinarsCentraleId.setCodStrcStrc(situationCaisseCentraleVo.getCaisseCentrale().getCaisseCentraleId().getCodStrcStrc());
              caisseDinarsCentraleId.setDatJrnJrn(situationCaisseCentraleVo.getCaisseCentrale().getCaisseCentraleId().getDatJrnJrn());             
              CaisseDinarsCentrale caisseDinarsCentrale =(CaisseDinarsCentrale)  searchEngine.get(CaisseDinarsCentrale.class,caisseDinarsCentraleId );    
              
              if(caisseDinarsCentrale != null){
                  situationCaisseCentraleVo.setExistCaisseDinars(true);
                  situationCaisseCentraleVo.setCaisseDinarsCentrale(caisseDinarsCentrale);
               }
            
             //---------------------------------------------------
             //-------Recherche s'il y a caisse en devise --------
              CaisseDeviseCentraleId caisseDeviseCentraleId = new CaisseDeviseCentraleId();
              caisseDeviseCentraleId.setCodStrcStrc(situationCaisseCentraleVo.getCaisseCentrale().getCaisseCentraleId().getCodStrcStrc());
              caisseDeviseCentraleId.setDatJrnJrn(situationCaisseCentraleVo.getCaisseCentrale().getCaisseCentraleId().getDatJrnJrn());             
              CaisseDeviseCentrale caisseDeviseCentrale =(CaisseDeviseCentrale)  searchEngine.get(CaisseDeviseCentrale.class,caisseDeviseCentraleId );    
              
              if(caisseDeviseCentrale != null){
                situationCaisseCentraleVo.setExistCaisseDevise(true);
                situationCaisseCentraleVo.setCaisseDeviseCentrale(caisseDeviseCentrale);
              
                ICriteria criteriaCaisDevCen = searchEngine.createCriteria();
                IExpression expressionCaisDevCen = searchEngine.createExpression();
                criteriaCaisDevCen.add(expressionCaisDevCen.eq("detailCaisDevCenId.datJrnJrn", situationCaisseCentraleVo.getCaisseCentrale().getCaisseCentraleId().getDatJrnJrn()));
                criteriaCaisDevCen.add(expressionCaisDevCen.eq("detailCaisDevCenId.codStrcStrc",situationCaisseCentraleVo.getCaisseCentrale().getCaisseCentraleId().getCodStrcStrc()));
              
                List listeDetailDeviseCentrale =  searchEngine.find(DetailCaisDevCen.class, criteriaCaisDevCen);   
                if (listeDetailDeviseCentrale != null && listeDetailDeviseCentrale.size() >0 ){
                  situationCaisseCentraleVo.setListDetailCaisDevCen(listeDetailDeviseCentrale);
                }
              }
         }
        
         return situationCaisseCentraleVo;
    
     } catch (Exception e) {
         com.oxia.fwk.core.Error erreur = new com.oxia.fwk.core.Error();
         StringBuffer text = 
             new StringBuffer("Erreur dans GetSituationCaisseCentraleTrt : ");
         text.append(e.toString());
         erreur.setCode("200");
         erreur.setDescription(text.toString());
         erreur.setKey("caisse");
         situationCaisseCentraleVo.addError(erreur);
         return (situationCaisseCentraleVo);
     }

    }

    public void genCroText(ValueObject vo) {

    }
}
