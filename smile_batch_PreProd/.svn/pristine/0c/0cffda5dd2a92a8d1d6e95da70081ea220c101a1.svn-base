package com.bna.smile.model.statistique.service;

import com.bna.smile.model.statistique.traitement.GetTableauDeBordTrt;
import com.oxia.fwk.beans.service.BasicService;
import com.oxia.fwk.core.IValueObject;
/**
 * Les services de tableau de bord
 * 
 * @author Mdimagh Med Lassaad
 * @since 07/05/2008
 */
public class TableauDeBordService extends BasicService{


    private GetTableauDeBordTrt getTableauDeBordTrt;
    public TableauDeBordService() {
    }
    
    /**
     * Methode permettant de rechercher un contrat de placement.
     * @param  TableauDeBordVo
     * @return TableauDeBordVo
     */
    public IValueObject getTableauDeBord(IValueObject vo) {
        //GetTableauDeBordTrt getTableauDeBordTrt = new GetTableauDeBordTrt();
        getTableauDeBordTrt.setSecurityFlag(false);
        return (getTableauDeBordTrt.exec(vo));
    }

    public void setGetTableauDeBordTrt(GetTableauDeBordTrt getTableauDeBordTrt) {
        this.getTableauDeBordTrt = getTableauDeBordTrt;
    }

    public GetTableauDeBordTrt getGetTableauDeBordTrt() {
        return getTableauDeBordTrt;
    }
}
