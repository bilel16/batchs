package com.bna.smile.model.domainecaisse.traitement;

import java.util.Iterator;
import java.util.List;

import com.bna.commun.model.CaisseDevises;
import com.bna.commun.model.CaisseDinars;
import com.bna.commun.model.DetailCaisseStructure;
import com.bna.commun.traitements.Traitement;
import com.bna.commun.util.ContextHandler;
import com.bna.smile.model.constant.Constants;
import com.bna.smile.model.domainecaisse.model.SituationDetailCaisseStructureVo;
import com.oxia.fwk.context.Context;
import com.oxia.fwk.core.ICriteria;
import com.oxia.fwk.core.IExpression;
import com.oxia.fwk.core.ISearchEngine;
import com.oxia.fwk.core.IValueObject;
import com.oxia.fwk.core.ValueObject;
import com.oxia.fwk.searchengine.SearchEngine;

/**
 * Classe qui permer d'extraire les informations de l'un des détails d'une caisse pour une structure à un jour 
 * Si le numéro de détail ne figure pas, par défaut le détail caisse cherché sera le dèrnier détail.
 * @author Mdimagh med Lassaad
 * @since 21/12/2007
 */
public class GetDetailCaisseStructureTrt extends Traitement {
    Context context = ContextHandler.getContext();

    public GetDetailCaisseStructureTrt() {
    }
    
    public  IValueObject perform(IValueObject vo) throws Exception {
    
     
         SituationDetailCaisseStructureVo  situationDetailCaisseStructureVo = (SituationDetailCaisseStructureVo)vo;
         try {
         
         ISearchEngine searchEngine =(SearchEngine)context.getBean("searchEngine");
         ICriteria criteria         = searchEngine.createCriteria();
         IExpression expression     = searchEngine.createExpression();
         
         
         criteria.add(expression.eq("caisseStructure.caisseStructureId.numCaisAc"   ,situationDetailCaisseStructureVo.getNumeroCaisse() )); /// numéro caisse
         criteria.add(expression.eq("caisseStructure.caisseStructureId.codStrcStrc" ,situationDetailCaisseStructureVo.getCodeStructure())); /// structure
         criteria.add(expression.eq("caisseStructure.caisseStructureId.datJrnJrn"   ,situationDetailCaisseStructureVo.getDateJournee())); /// date journée
         
         List  listDetailCaisseStructure  =  searchEngine.find(DetailCaisseStructure.class,criteria );    
        
         if (listDetailCaisseStructure != null && listDetailCaisseStructure.size() > 0){
             DetailCaisseStructure detail  = (DetailCaisseStructure)listDetailCaisseStructure.get(0);
             CaisseDinars  caisseDinars = new CaisseDinars()   ;
             CaisseDevises caisseDevise = new CaisseDevises()  ;
             
             for(Iterator it =detail.getCaisseDinarses().iterator(); it.hasNext(); ){
                 caisseDinars = (CaisseDinars) it.next();
             }
             
             for(Iterator it =detail.getCaisseDeviseses().iterator(); it.hasNext(); ){
                 caisseDevise = (CaisseDevises) it.next();
             }
             
             situationDetailCaisseStructureVo.setDetailCaisseStructure(detail);
             situationDetailCaisseStructureVo.setCaisseDinars(caisseDinars);
             situationDetailCaisseStructureVo.setCaisseDevises(caisseDevise);
             
         }
         return situationDetailCaisseStructureVo;
    
     } catch (Exception e) {
         com.oxia.fwk.core.Error erreur = new com.oxia.fwk.core.Error();
         StringBuffer text = 
             new StringBuffer("Erreur dans GetDetailCaisseStructureTrt : ");
         text.append(e.toString());
         erreur.setCode("300");
         erreur.setDescription(text.toString());
         erreur.setKey("GetDetailCaisseStructureTrt");
         situationDetailCaisseStructureVo.addError(erreur);
         return (situationDetailCaisseStructureVo);
     }

    }

    public void genCroText(ValueObject vo) {

    }
    
    public String getNumeroTache(IValueObject vo) {
      return (Constants.CODE_RESSOURCE_GENERALE);        
    }
    
}
