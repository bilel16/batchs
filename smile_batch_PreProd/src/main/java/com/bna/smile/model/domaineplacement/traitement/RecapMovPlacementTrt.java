package com.bna.smile.model.domaineplacement.traitement;

import com.bna.commun.traitements.Traitement;
import com.bna.commun.util.ContextHandler;
import com.bna.commun.util.DateHandler;
import com.bna.smile.model.constant.Constants;
import com.bna.smile.model.domainecommun.model.Listes;
import com.bna.smile.model.domaineplacement.dao.PlacementDAO;
import com.bna.smile.model.domaineplacement.model.ParamDemandeDecision;
import com.bna.smile.model.domaineplacement.model.RecapMouvementPlac;
import com.bna.smile.model.domaineplacement.model.RecapSituationMensuelle;

import com.oxia.fwk.context.Context;
import com.oxia.fwk.core.ISearchEngine;
import com.oxia.fwk.core.IValueObject;
import com.oxia.fwk.core.ValueObject;
import com.oxia.fwk.searchengine.SearchEngine;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.collections.map.ListOrderedMap;

public class RecapMovPlacementTrt extends Traitement{
    public RecapMovPlacementTrt() {
    }
    Context context = ContextHandler.getContext();
    ISearchEngine searchEngine = (SearchEngine)context.getBean("searchEngine");
    
    public IValueObject perform(IValueObject vo) {
    Long codeStructre=null;
    try{
    Listes ll=new Listes();
    ParamDemandeDecision paramDemandeDecision = (ParamDemandeDecision)vo;
    String dateComptable=DateHandler.dateToStr(paramDemandeDecision.getDateComptable());
    codeStructre=paramDemandeDecision.getCodStrcStrc()[0];
    PlacementDAO plcDao= (PlacementDAO)context.getBean("placementDAO");
    List movementPlacementDao = plcDao.getRecapMouvementPlacement(dateComptable,codeStructre);
    List listeMovementPlacement=new ArrayList();
    ListOrderedMap recapMap = null;
    
        if(movementPlacementDao!=null && movementPlacementDao.size()>0) {
            
            for (Iterator it1 = movementPlacementDao.iterator(); it1.hasNext(); ){
                RecapMouvementPlac recap=new RecapMouvementPlac();
                recapMap = (ListOrderedMap)it1.next();
                if (recapMap.getValue(0) != null) {
                    recap.setLibOperation(recapMap.getValue(0).toString());//libelle
                }
                if (recapMap.getValue(1) != null) {
                    recap.setCodStrcStrc(Long.valueOf(recapMap.getValue(1).toString()));//code structure
                }
                if (recapMap.getValue(2) != null) {
                    recap.setNumMatrUser((recapMap.getValue(2).toString()));//user
                }
                if (recapMap.getValue(3) != null) {
                    recap.setMontantMouvement(Long.valueOf(recapMap.getValue(3).toString()));//montant
                }
                if (recapMap.getValue(4) != null) {
                    recap.setNumeroCompte(recapMap.getValue(4).toString());//numero compte
                }
                if (recapMap.getValue(5) != null) {
                         recap.setHeureMouvement(recapMap.getValue(5).toString());//heure oper
                     }
                if (recapMap.getValue(6) != null) {
                    recap.setCodeOperation(Long.valueOf(recapMap.getValue(6).toString()));//code operation
                }
                if (recapMap.getValue(7) != null) {
                    recap.setNumBonCaisse(Long.valueOf(recapMap.getValue(7).toString()));//Numéro bon de caisse
                }
                listeMovementPlacement.add(recap);
            }
           
            ll.setList(listeMovementPlacement);
          }else {
            logger.debug("la liste des Mouvements est vide");
          }
    return ll;
    }catch (Exception e) {
        com.oxia.fwk.core.Error erreur = new com.oxia.fwk.core.Error();
        StringBuffer text = new StringBuffer("Erreur dans RecapMoevementTrt : ");
        text.append(e.toString());
        erreur.setCode("100");
        erreur.setDescription(text.toString());
        erreur.setKey("IntretServiTrt");
        logger.error("Exception : ",e);   
        throw new RuntimeException(e);
            
    }
    }


    public void genCroText(ValueObject vo) {
    
    }
    public String getNumeroTache(ValueObject vo) {
    return (Constants.CODE_RESSOURCE_GENERALE);    
    }
    
    
}
