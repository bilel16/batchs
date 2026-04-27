package com.bna.smile.model.domainecommun.service;

import com.bna.smile.model.domainecommun.traitement.GetCoursDevTrt;
import com.bna.smile.model.domainecommun.traitement.GetDateDerniereOperationTrt;
import com.bna.smile.model.domainecommun.traitement.InsertOperationCompteTrt;

import com.oxia.fwk.beans.service.BasicService;
import com.oxia.fwk.core.IValueObject;
import com.oxia.fwk.core.ValueObject;


public class OperationService extends BasicService {
    public OperationService() {
    }
   
   private InsertOperationCompteTrt insertOperationCompteTrt ;
   private GetDateDerniereOperationTrt getDateDerniereOperationTrt ;
   private GetCoursDevTrt getCoursDevTrt;
   
   
    public ValueObject InsertOperationCompte(ValueObject vo) {       
        return (insertOperationCompteTrt.execute(vo));
    }
    
    
    
/**
 * rechercher la date de la derniere opération faite sur cette mandat_operation
 * @param   ValueObject : MandatOperation
 * @return  ValueObject : PrimitiveVO (Date)
 * @author  BOUSSEN Youssef & KRIAA Hatem
 * @version le 06/08/2007
 **/
    public IValueObject GetDateDerniereOperation(IValueObject vo) {
        return (getDateDerniereOperationTrt.exec(vo));
    }
    
    /**
     * rechercher le cours de devise d'une devise donnée à une date donnée
     * @param   ValueObject : CoursChangeId
     * @return  ValueObject : CoursChange
     * @author  BOUSSEN Youssef 
     * @version le 07/09/2007
     **/
        public ValueObject GetCoursDev(ValueObject vo) {
            return (getCoursDevTrt.execute(vo));
        }


    public void setInsertOperationCompteTrt(InsertOperationCompteTrt insertOperationCompteTrt) {
        this.insertOperationCompteTrt = insertOperationCompteTrt;
    }

    public InsertOperationCompteTrt getInsertOperationCompteTrt() {
        return insertOperationCompteTrt;
    }

    public void setGetDateDerniereOperationTrt(GetDateDerniereOperationTrt getDateDerniereOperationTrt) {
        this.getDateDerniereOperationTrt = getDateDerniereOperationTrt;
    }

    public GetDateDerniereOperationTrt getGetDateDerniereOperationTrt() {
        return getDateDerniereOperationTrt;
    }

    public void setGetCoursDevTrt(GetCoursDevTrt getCoursDevTrt) {
        this.getCoursDevTrt = getCoursDevTrt;
    }

    public GetCoursDevTrt getGetCoursDevTrt() {
        return getCoursDevTrt;
    }
}
