package com.bna.smile.model.domainecontratcompte.procuration.traitement;


import java.util.Date;

import com.bna.commun.model.MandatOperation;
import com.bna.commun.traitements.Traitement;
import com.bna.commun.util.DateHandler;
import com.bna.commun.vo.PrimitiveVO;
import com.bna.smile.model.constant.Constants;
import com.oxia.fwk.core.IValueObject;
import com.oxia.fwk.core.ValueObject;


public class DebutDernierePeriodeTrt extends Traitement{
   // public Context context = ContextHandler.getContext();
   // private static final Logger logger = Logger.getLogger(DebutDernierePeriodeTrt.class);
    public DebutDernierePeriodeTrt() {
    }


/**
     * Cette fonction determine la date du debut de 
     * la derniere periode par rapport à la date du jour
     * @param  MandatOperation
     * @return primitiveVO
     * @author BOUSSEN Youssef & KRIAA Hatem
     * @version le 07/08/2007
     */
    public IValueObject perform(IValueObject vo) {
    
        MandatOperation mandatOperation = (MandatOperation)vo;
        PrimitiveVO primitiveVO = new PrimitiveVO();
    try{
        String periode =  mandatOperation.getCodPerMaop();
        Date   dateDebPer = mandatOperation.getDatDperMaop(); 
        
        Date vDateDebutDernPer=new Date();
        
        Double datBetween = new Double(DateHandler.getDaysBetween(dateDebPer,new Date()));
        Double monthBetween = new Double(DateHandler.getMonthsBetween(dateDebPer,new Date())); 
        
        if (periode.equalsIgnoreCase("J")){
            vDateDebutDernPer=new Date();
        }
        if (periode.equalsIgnoreCase("H")){
            vDateDebutDernPer=DateHandler.addJour(dateDebPer,7*(datBetween.intValue()/7));
        }
        if (periode.equalsIgnoreCase("D")){
            vDateDebutDernPer=DateHandler.addJour(dateDebPer,10*(datBetween.intValue()/10));
        }
        if (periode.equalsIgnoreCase("Q")){
            vDateDebutDernPer=DateHandler.addJour(dateDebPer,15*(datBetween.intValue()/15));
        }
        if (periode.equalsIgnoreCase("M")){
            vDateDebutDernPer=DateHandler.addMonth(dateDebPer,(monthBetween.intValue()));
        }
        if (periode.equalsIgnoreCase("T")){
           vDateDebutDernPer=DateHandler.addMonth(dateDebPer,3*(monthBetween.intValue()/3));
        }
        if (periode.equalsIgnoreCase("S")){
            vDateDebutDernPer=DateHandler.addMonth(dateDebPer,6*(monthBetween.intValue()/6));
        }
        if (periode.equalsIgnoreCase("A")){
            vDateDebutDernPer=DateHandler.addMonth(dateDebPer,12*(monthBetween.intValue()/12));
        }

        primitiveVO.setVDate(vDateDebutDernPer);
            
        return (primitiveVO);
        }
           catch (Exception e) {
              com.oxia.fwk.core.Error erreur = new com.oxia.fwk.core.Error();
              StringBuffer text = 
                  new StringBuffer("Erreur dans DebutDernierePeriodeTrt : ");
              text.append(e.toString());
              erreur.setCode("200");
              erreur.setDescription(text.toString());
              erreur.setKey("DebutDernierePeriodeTrt");
              primitiveVO.addError(erreur);
              logger.error(" *** Erreur lors de DebutDernierePeriodeTrt", e);
              return (primitiveVO);
          }
    }
    
    
    public void genCroText(ValueObject vo) {
          
         
        }  
    public String getNumeroTache(ValueObject vo) {
        return (Constants.CODE_RESSOURCE_GENERALE);    
        
        
    }
}
