package com.bna.smile.model.domaineplacement.traitement;

import com.bna.commun.model.JourneeStructureBatch;
import com.bna.commun.model.JourneeStructureBatchId;
import com.bna.commun.model.Structure;
import com.bna.commun.traitements.Traitement;
import com.bna.commun.util.ContextHandler;
import com.bna.commun.util.DateHandler;
import com.bna.commun.util.StrHandler;
import com.bna.smile.model.constant.Constants;
import com.bna.smile.model.domainecommun.model.ContratPersonne;
import com.bna.smile.model.domainecommun.model.Listes;
import com.bna.smile.model.domainecommun.model.PersonneStrc;
import com.bna.smile.model.domaineplacement.dao.PlacementDAO;
import com.bna.smile.model.domaineplacement.model.ParamDemandeDecision;
import com.bna.smile.model.domaineplacement.model.ParamInteretServi;
import com.bna.smile.model.domaineplacement.model.RecapSituationMensuelle;
import com.bna.smile.model.domaineplacement.service.BatchService;
import com.bna.smile.model.domaineplacement.service.PlacementService;

import com.oxia.fwk.context.Context;
import com.oxia.fwk.core.ICriteria;
import com.oxia.fwk.core.IExpression;
import com.oxia.fwk.core.ISearchEngine;
import com.oxia.fwk.core.IValueObject;
import com.oxia.fwk.core.ValueObject;
import com.oxia.fwk.searchengine.SearchEngine;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.collections.map.ListOrderedMap;

public class GetSituationMensuelleByClientTrt extends Traitement{
    public GetSituationMensuelleByClientTrt() {
    }
    Context context = ContextHandler.getContext();
    ISearchEngine searchEngine = (SearchEngine)context.getBean("searchEngine");
    
    public IValueObject perform(IValueObject vo) {
    Long codeTpceTpce=null;
    String numPcePers=null;
    ContratPersonne contratPersonne=new  ContratPersonne();
    PersonneStrc personneStrc=new PersonneStrc();
    
    try{
    Listes ll=new Listes();
    ParamDemandeDecision paramDemandeDecision = (ParamDemandeDecision)vo;
    contratPersonne=paramDemandeDecision.getContratPersonne();
    personneStrc=contratPersonne.getPersonneId();
    codeTpceTpce=personneStrc.getCodTpceTpce();
    numPcePers=personneStrc.getNumPcePers();
    PlacementDAO plcDao= (PlacementDAO)context.getBean("placementDAO");
    List situationMensuelleDao = plcDao.getSituationMensuelleClient(codeTpceTpce,numPcePers);
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
