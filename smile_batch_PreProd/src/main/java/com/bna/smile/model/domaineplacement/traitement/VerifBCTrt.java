package com.bna.smile.model.domaineplacement.traitement;


import com.bna.commun.model.BonDeCaisse;
import com.bna.commun.model.Structure;
import com.bna.commun.traitements.Traitement;

import com.bna.commun.util.ContextHandler;

import com.bna.commun.util.DateHandler;
import com.bna.smile.model.constant.Constants;
import com.bna.smile.model.domainecommun.traitement.GetStructureTrt;

import com.bna.smile.model.domainecommun.service.CRUDservice;
import com.bna.smile.model.domaineplacement.dao.PlacementDAO;
import com.bna.smile.model.domaineplacement.model.ParamBonCaisse;


import com.oxia.fwk.context.Context;
import com.oxia.fwk.core.ICriteria;
import com.oxia.fwk.core.IExpression;
import com.oxia.fwk.core.ISearchEngine;
import com.oxia.fwk.core.IValueObject;
import com.oxia.fwk.core.ValueObject;
import com.oxia.fwk.searchengine.SearchEngine;



public class VerifBCTrt extends Traitement{
    public VerifBCTrt() {
    }
    
    Context context = ContextHandler.getContext();
    ISearchEngine searchEngine = (SearchEngine)context.getBean("searchEngine");
    

    ICriteria criteria = searchEngine.createCriteria();
    ICriteria criteriaAvanc = searchEngine.createCriteria();
    IExpression expression = searchEngine.createExpression();
    

    public IValueObject perform(IValueObject vo) {
        ParamBonCaisse paramBC=(ParamBonCaisse)vo;
        PlacementDAO plcDao= (PlacementDAO)context.getBean("placementDAO");
        if (paramBC.getNumBonCaisse()!=null) {
         Long l = plcDao.verifNumBC(paramBC.getNumBonCaisse());
         paramBC.setExistBonCaisse(false);
         if(!l.equals(Long.valueOf("0")))
          paramBC.setExistBonCaisse(true);
         
        }
        if (paramBC.getNumBonCaisse()==null) 
        {
             Long ll = plcDao.verifIntervBC(paramBC.getNumSeqDebBC(),paramBC.getNumSeqDebBC());
            paramBC.setExistDetailsBC(false);
             if  (ll.intValue()!=0) 
                paramBC.setExistDetailsBC(true);
        }
        return (paramBC);
    }
   public IValueObject insertBC(IValueObject vo) {
   ParamBonCaisse paramBC=(ParamBonCaisse)vo;
   BonDeCaisse bonDeCaisse  = new BonDeCaisse();
   PlacementDAO plcDao= (PlacementDAO)context.getBean("placementDAO");
    try{ 
         
        // this.setCroFlag(false);
         Context context = ContextHandler.getContext();
         Long nbreBC=paramBC.getNumSeqFinBC()-paramBC.getNumSeqDebBC()+1;
         Long numSeqBC=plcDao.getSeqBc();
         bonDeCaisse.setNumNbrcBc(nbreBC);
         bonDeCaisse.setNumSeqBc(numSeqBC);
         bonDeCaisse.setDatRecpBc(DateHandler.utilDateToSqlDate(DateHandler.strToDate(DateHandler.dateJour())));
         bonDeCaisse.setNumDebcBc(paramBC.getNumSeqDebBC());
         bonDeCaisse.setNumFincBc(paramBC.getNumSeqFinBC());
         Structure structure=new Structure();
         structure.setCodStrcStrc(paramBC.getCodeStructure());
         GetStructureTrt getStructureTrt=new GetStructureTrt();
         structure=(Structure)getStructureTrt.perform((IValueObject)structure);
         bonDeCaisse.setStructure(structure);
         CRUDservice crudService = (CRUDservice)context.getBean("crudservice");           
         
         if(!bonDeCaisse.equals(null)){
            crudService.create(bonDeCaisse);
          } 
       }catch (Exception e) {
             com.oxia.fwk.core.Error erreur=new com.oxia.fwk.core.Error();
             erreur.setCode("Technique");
             erreur.setDescription("InsertBonDeCaisseTrt  "+e.getMessage());;
             bonDeCaisse.addError(erreur);
             logger.error("Exception : ",e); 
             paramBC=null;
             throw new RuntimeException(e);
     } 
     return (paramBC);
   
    }
    public void genCroText(ValueObject vo) {
    }
    public String getNumeroTache(ValueObject vo) {
    return (Constants.CODE_RESSOURCE_GENERALE);    
    }
}
