package com.bna.smile.model.domaineplacement.traitement;

import com.bna.commun.traitements.Traitement;
import com.bna.commun.util.ContextHandler;
import com.bna.commun.util.DateHandler;
import com.bna.commun.util.StrHandler;
import com.bna.smile.model.constant.Constants;
import com.bna.smile.model.domainecommun.model.Listes;
import com.bna.smile.model.domaineplacement.dao.PlacementDAO;
import com.bna.smile.model.domaineplacement.model.ParamDemandeDecision;
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


public class GetSituationMensuelleByStructureTrt extends Traitement{
    public GetSituationMensuelleByStructureTrt() {
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
    List situationMensuelleDao = plcDao.getSituationMensuelle(codeStructre,dateComptable);
    List situationMensuelle=new ArrayList();
    ListOrderedMap sitMens = null;
    
        if(situationMensuelleDao!=null && situationMensuelleDao.size()>0) {
            
            for (Iterator it1 = situationMensuelleDao.iterator(); it1.hasNext(); ){
                RecapSituationMensuelle recap=new RecapSituationMensuelle();
                sitMens = (ListOrderedMap)it1.next();
                if (sitMens.getValue(0) != null) {
                    recap.setCodStrcStrc(Long.valueOf(sitMens.getValue(0).toString()));
                }
                if (sitMens.getValue(1) != null) {

                  recap.setMntCapital(StrHandler.formatmnt(Long.valueOf(sitMens.getValue(1).toString()).doubleValue()));

                }
                if (sitMens.getValue(2) != null) {
                    recap.setLibProduit(sitMens.getValue(2).toString());
                }
                situationMensuelle.add(recap);
            }
           
            ll.setList(situationMensuelle);
          }else {
            logger.debug("Situation mensuelle vide");
          }
    return ll;
    }catch (Exception e) {
        com.oxia.fwk.core.Error erreur = new com.oxia.fwk.core.Error();
        StringBuffer text = new StringBuffer("Erreur dans IntretServiTrt : ");
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
