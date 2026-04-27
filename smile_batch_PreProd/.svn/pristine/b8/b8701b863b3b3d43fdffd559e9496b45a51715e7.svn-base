package com.bna.smile.model.domaineplacement.traitement;

import com.bna.commun.model.HistTauxReference;
import com.bna.commun.traitements.Traitement;
import com.bna.commun.util.DateHandler;
import com.bna.commun.vo.PrimitiveVO;
import com.bna.smile.model.constant.Constants;

import com.bna.smile.model.domaineplacement.model.ParamDates;

import com.oxia.fwk.context.Context;
import com.oxia.fwk.core.ICriteria;
import com.oxia.fwk.core.IExpression;
import com.oxia.fwk.core.ISearchEngine;
import com.oxia.fwk.core.IValueObject;

import com.oxia.fwk.core.ValueObject;

import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.List;
import java.util.TimeZone;

public class GetAvgTMMbetweenDatesTrt extends Traitement{
    public GetAvgTMMbetweenDatesTrt() {
    }
    
    /**
     * methode qui retourne la moyenne des TMM entre 2 dates
     * selon une periode (M : mesuelle,J : journaliere )
     * @param vo : ParamDates
     * @return   : PrimitiveVO
     * @autor    : Youssef BOUSSEN 
     */
    
    public IValueObject perform (IValueObject vo){
    
        
        ISearchEngine searchEngine=(ISearchEngine)Context.getInstance().getSpringContext().getBean("searchEngine");
        ICriteria criteria         = searchEngine.createCriteria();        
        IExpression expression     = searchEngine.createExpression();
        PrimitiveVO primitiveVO = new PrimitiveVO();
        

      try{
        ParamDates paramDates = (ParamDates)vo;
        paramDates.setDateDebut(DateHandler.strToDate(DateHandler.dateToStr(paramDates.getDateDebut())));
        paramDates.setDateFin(DateHandler.strToDate(DateHandler.dateToStr(paramDates.getDateFin())));
        /* Rechercher des intervalles de TMM */
        
        if((paramDates.getDateDebut().getMonth() == paramDates.getDateFin().getMonth())
            &&(paramDates.getDateDebut().getYear() == paramDates.getDateFin().getYear())){ ///*** cas ou date debut et date fin appartiennent au même mois et même année
        
            criteria.add(expression.ge("histTauxReferenceId.datFinHtre",paramDates.getDateFin()));
            criteria.add(expression.le("histTauxReferenceId.datDebHtre",DateHandler.addJour(paramDates.getDateFin(),1)));
            criteria.add(expression.eq("histTauxReferenceId.codTrefHtre",Constants.Taux_Ref_TMM));
            
        }else{
            criteria.add(expression.or(expression.between("histTauxReferenceId.datDebHtre",paramDates.getDateDebut(),paramDates.getDateFin()),
                                       expression.between("histTauxReferenceId.datFinHtre",paramDates.getDateDebut(),paramDates.getDateFin())));
            criteria.add(expression.eq("histTauxReferenceId.codTrefHtre",Constants.Taux_Ref_TMM));                        
        }
         
        List l = searchEngine.find(HistTauxReference.class, criteria);

        if (l != null && l.size() > 0) {            
            Double sommeTmm=Double.valueOf("0");
            Double i =Double.valueOf("0"); 
            if (paramDates.getInterval().equalsIgnoreCase(Constants.INTERVAL_TMM_MENSUEL)){///*** Mensuel
                for (Iterator it =l.iterator(); it.hasNext();){
    
                    HistTauxReference histTauxReference = (HistTauxReference)it.next();
                    if (histTauxReference!=null && histTauxReference.getHistTauxReferenceId()!=null && histTauxReference.getHistTauxReferenceId().getValTrefTref()!=null){
                        sommeTmm=sommeTmm+histTauxReference.getHistTauxReferenceId().getValTrefTref();
                        i=i+1;
                    }
                }
                if (i!=0) {
                    primitiveVO.setVDouble(Double.valueOf(sommeTmm/i)); 
                }
            }else if(paramDates.getInterval().equalsIgnoreCase(Constants.INTERVAL_TMM_JOURNALIER)){///*** Journalier
            
                 for (Iterator it =l.iterator(); it.hasNext();){
                 
                     HistTauxReference histTauxReference = (HistTauxReference)it.next();
   
                     if (histTauxReference!=null && histTauxReference.getHistTauxReferenceId()!=null && histTauxReference.getHistTauxReferenceId().getValTrefTref()!=null){
                         Date DateDebutHist = DateHandler.strToDate(DateHandler.dateToStr(histTauxReference.getHistTauxReferenceId().getDatDebHtre()));
                         Date DateFinHist = DateHandler.strToDate(DateHandler.dateToStr(histTauxReference.getHistTauxReferenceId().getDatFinHtre()));
                        if ((DateDebutHist.after(paramDates.getDateDebut())) && (DateFinHist.before(paramDates.getDateFin()))){ 
                            Double daysBetw = Math.rint(DateHandler.getDaysBetween( DateDebutHist,DateFinHist)+1);
                            sommeTmm=sommeTmm+(histTauxReference.getHistTauxReferenceId().getValTrefTref() * daysBetw);
                            i=i+daysBetw;
                        }else{  if ((DateDebutHist.before(paramDates.getDateDebut())) && (DateFinHist.after(paramDates.getDateDebut()))){
                                    Double daysBetw = Math.rint(DateHandler.getDaysBetween(paramDates.getDateDebut(),DateFinHist)+1);
                                    sommeTmm=sommeTmm+(histTauxReference.getHistTauxReferenceId().getValTrefTref() * daysBetw);
                                    i=i+daysBetw;
                                }else{  if ((DateDebutHist.before(paramDates.getDateFin())) && (DateFinHist.after(paramDates.getDateFin()))){
                                            Double daysBetw = Math.rint(DateHandler.getDaysBetween(DateDebutHist,paramDates.getDateFin())+1);
                                            sommeTmm=sommeTmm+(histTauxReference.getHistTauxReferenceId().getValTrefTref() * daysBetw);
                                            i=i+daysBetw;
                                        }
                                        else{
                                            int compdebut =DateDebutHist.compareTo(paramDates.getDateDebut());
                                            int compFin =DateFinHist.compareTo(paramDates.getDateFin());
                                            if ((DateDebutHist.before(paramDates.getDateDebut())||compdebut==0) && (DateFinHist.after(paramDates.getDateFin()))|| compFin==0){
                                                Double daysBetw = Math.rint(DateHandler.getDaysBetween(DateDebutHist,paramDates.getDateFin())+1);
                                                sommeTmm=sommeTmm+(histTauxReference.getHistTauxReferenceId().getValTrefTref() * daysBetw);
                                                i=i+daysBetw;
                                            }
                                                                            
                                        }
                                }
                        }
                     }
                 }
                    if (i!=0) {
                        primitiveVO.setVDouble(Double.valueOf(sommeTmm/i)); 
                    }
            }

        } else { /* TMM inexistant */
             com.oxia.fwk.core.Error erreur = new com.oxia.fwk.core.Error();
             StringBuffer text = new StringBuffer("Erreur dans GetAvgTMMbetweenDatesTrt : ");
             text.append("TMM Inexistant");
             erreur.setDescription(text.toString());
             erreur.setKey("GetAvgTMMbetweenDatesTrt");
             primitiveVO.addError(erreur);
        }
        
        } catch (Exception e) {
            com.oxia.fwk.core.Error erreur = new com.oxia.fwk.core.Error();
            StringBuffer text = new StringBuffer("Erreur dans GetAvgTMMbetweenDatesTrt : ");
            text.append(e.getMessage());
            erreur.setCode("100");
            erreur.setDescription(text.toString());
            erreur.setKey("GetAvgTMMbetweenDatesTrt");
            primitiveVO.addError(erreur);
            logger.error("Exception : ",e);   
            throw new RuntimeException(e);  
            
        }
        return primitiveVO;

    }



    public void genCroText(ValueObject vo) {    
    
    }
    
    public String getNumeroTache(IValueObject vo) {
        return (Constants.CODE_RESSOURCE_GENERALE);    
    } 

}
