package com.bna.smile.model.domaineplacement.traitement;

import com.bna.commun.util.CalanderHandler;

import java.util.Date;

import org.apache.log4j.Logger;

public class GetDateComptable {
    private static final Logger logger = Logger.getLogger(GetDateComptable.class);
    
    public GetDateComptable() {
    }
    
    public static Date execute  (Date d) throws Exception {

      try{
      if(CalanderHandler.isJourFerier(d)){
      return(CalanderHandler.GetNextWorkingDay(d));
      }else
      {
      return(d);
      }
      
    }catch(Exception e){
        logger.error(" Erreur dans GetDateComptable.execute : " , e);
        throw   e;
    }
}
}