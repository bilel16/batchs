package com.bna.smile.model.domainechange.service;

import com.bna.smile.model.domainechange.traitement.GetCoursChangeTrt;
import com.bna.smile.model.domainechange.traitement.GetCoursPariteOffTrt;
import com.bna.smile.model.domainechange.traitement.InsertCoursChangeTrt;
import com.bna.smile.model.domainechange.traitement.InsertPariteOffTrt;
import com.bna.smile.model.domainechange.traitement.InsertTraceCoursChangeTrt;
import com.bna.smile.model.domainechange.traitement.InsertTracePariteOffTrt;
import com.bna.smile.model.domainechange.traitement.UpdateCoursChangeTrt;
import com.bna.smile.model.domainechange.traitement.UpdatePariteOffTrt;
import com.oxia.fwk.core.IValueObject;

/**
 * Classe qui contient les services des opération de change
 * @author EL arbi hassine
 * @version 01/12/2010
 */
public class ChangeService {
    public ChangeService() {
    }
    
    /**
     * methode de recherche du cours change par CoursChangeId
     * @param vo :CoursChangeId
     * @return   :CoursChange
     */
    public IValueObject getCoursChange(IValueObject vo) {

        GetCoursChangeTrt getCoursChangeTrt = 
            new GetCoursChangeTrt();

        return (getCoursChangeTrt.exec(vo));
    }
    
    public IValueObject insertCoursChange(IValueObject vo) {

        InsertCoursChangeTrt insertCoursChangeTrt = 
            new InsertCoursChangeTrt();

        return (insertCoursChangeTrt.exec(vo));
    }
    
    public IValueObject updateCoursChange(IValueObject vo) {

        UpdateCoursChangeTrt updateCoursChangeTrt = 
            new UpdateCoursChangeTrt();

        return (updateCoursChangeTrt.exec(vo));
    }
    
    
    public IValueObject insertpariteOfficielle(IValueObject vo) {

         InsertPariteOffTrt insertPariteOffTrt = 
            new InsertPariteOffTrt();

        return (insertPariteOffTrt.exec(vo));
    }
    
    public IValueObject updatePariteOfficielle(IValueObject vo) {

        UpdatePariteOffTrt updatePariteOffTrt = 
            new UpdatePariteOffTrt();

        return (updatePariteOffTrt.exec(vo));
    }
    
    
    public IValueObject insertTraceCoursChange(IValueObject vo) {

        InsertTraceCoursChangeTrt insertTraceCoursChangeTrt = 
            new InsertTraceCoursChangeTrt();

        return (insertTraceCoursChangeTrt.exec(vo));
    }
    
    public IValueObject insertTracePariteOff(IValueObject vo) {

        InsertTracePariteOffTrt insertTracePariteOffTrt = 
            new InsertTracePariteOffTrt();

        return (insertTracePariteOffTrt.exec(vo));
    }
    
    
    public IValueObject getCoursPariteOff(IValueObject vo) {

        GetCoursPariteOffTrt getCoursPariteOffTrt = 
            new GetCoursPariteOffTrt();

        return (getCoursPariteOffTrt.exec(vo));
    }
    
}
