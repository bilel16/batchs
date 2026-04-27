package com.bna.smile.batch.test;

import java.util.Iterator;
import java.util.List;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

import com.bna.commun.model.AvancRembLiquid;
import com.bna.commun.util.ContextHandler;
import com.bna.smile.model.domaineplacement.model.ParamAbonnementement;
import com.bna.smile.model.domaineplacement.traitement.GenererAbonnementTrt;
import com.oxia.fwk.context.Context;
import com.oxia.fwk.context.ContextFactory;
import com.oxia.fwk.core.ICriteria;
import com.oxia.fwk.core.IExpression;
import com.oxia.fwk.core.ISearchEngine;
import com.oxia.fwk.searchengine.SearchEngine;

public class GenerAbonnAvance {
    public GenerAbonnAvance() {
    }

    public static void main(String[] args) {
        String[] path = 
        { "./config/spring.xml", "./config/applicationContext-DAO.xml", "./config/applicationContext-habilitation.xml", 
          "./config/applicationContext-resources.xml", "./config/applicationContext-service.xml", 
          "./config/applicationContext-serviceBatch.xml", 
          "./config/applicationContext-serviceHabil.xml", 
          "./config/applicationContext-traitements.xml", "./config/quartz-oxia-calendars.xml", 
          "./config/quartz-oxia-core.xml", "./config/quartz-oxia-jobs.xml", "./config/security.xml",
          "./config/quartz-oxia-listners.xml", "./config/quartz-oxia-triggers.xml" };

        ApplicationContext springContext =  
            new FileSystemXmlApplicationContext(path);
        Context context= (Context) ContextFactory.initContext("./config/applicationContext-1Spring.xml");
        context.setSpringContext(springContext);
        ContextHandler.setContext(context);
        
        ISearchEngine searchEngine = 
            (SearchEngine)context.getBean("searchEngine");
        ICriteria   criteria       = searchEngine.createCriteria();
        IExpression expression     = searchEngine.createExpression();

  //      criteria.add(expression.isNull("datReelArl"));
      criteria.add(expression.eq("codToprArl","AVAN"));
   criteria.add(expression.ge("numSeqArl",Long.valueOf(1000)));
   
        List l = searchEngine.find(AvancRembLiquid.class, criteria);
        if(l!=null && l.size()>0){
            
            for (Iterator it1 = l.iterator(); it1.hasNext(); ){
                AvancRembLiquid avancRembLiquid = (AvancRembLiquid)it1.next();
                // génération abonnement
                 ///*---------------------------- tester la generation de l'abonnement ------
                  ParamAbonnementement paramAbonnementement = new ParamAbonnementement();
                  paramAbonnementement.setMontItotAbpl(Long.valueOf(avancRembLiquid.getMontInetArl().longValue()));
                  paramAbonnementement.setDatDebAbpl(avancRembLiquid.getDatArlArl());
                  paramAbonnementement.setDatFinAbpl(avancRembLiquid.getContratPlacement().getDatEcheCpla());
                  if (avancRembLiquid.getDatPrevArl() != null){
                    paramAbonnementement.setDatPrevAbpl(avancRembLiquid.getDatPrevArl());
                  }else{
                    paramAbonnementement.setDatPrevAbpl(avancRembLiquid.getContratPlacement().getDatEcheCpla());
                  }
                  paramAbonnementement.setMontTotAbpl(Long.valueOf(avancRembLiquid.getMontArlArl().longValue()));
                  paramAbonnementement.setNumSeqArl(avancRembLiquid.getNumSeqArl());
                  paramAbonnementement.setTypeOperation("A");
                  paramAbonnementement.setNumTauiCpla(avancRembLiquid.getNumTauiArl());
                  if (avancRembLiquid.getContratPlacement()!=null)
                    paramAbonnementement.setContratPlacement(avancRembLiquid.getContratPlacement());
                  GenererAbonnementTrt genererAbonnementTrt = new GenererAbonnementTrt();
                  paramAbonnementement = (ParamAbonnementement)genererAbonnementTrt.exec(paramAbonnementement);
                 
                 ///*------------------------------------------------------------------------

                /**
                  update abonnement_placement set COD_ETAT_ABPL='T' 
                   where DAT_FIN_ABPL<sysdate
                     and NUM_SEQ_ARL is not null
               **/
            }
            
        }
        }
}
