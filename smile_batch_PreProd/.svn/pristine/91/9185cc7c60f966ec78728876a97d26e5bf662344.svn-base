package com.bna.smile.model.clotureDomaine.traitement;


import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.map.ListOrderedMap;

import com.bna.commun.traitements.Traitement;
import com.bna.commun.util.ContextHandler;
import com.bna.smile.model.clotureDomaine.dao.StatDomPlacementDAO;
import com.bna.smile.model.constant.Constants;
import com.bna.smile.model.statistique.model.TableauDeBordVo;
import com.oxia.fwk.context.Context;
import com.oxia.fwk.core.IValueObject;
import com.oxia.fwk.core.ValueObject;

public class GetStatAssVieTrt extends Traitement{
    public GetStatAssVieTrt() {
    }
    public IValueObject perform(IValueObject vo) throws SQLException, 
                                                        Exception {

        Context context = ContextHandler.getContext();
        TableauDeBordVo tableauDeBordVo =  (TableauDeBordVo)vo;

        try {
            StatDomPlacementDAO statDomPlacementDAO = 
                (StatDomPlacementDAO)context.getBean("statDomPlacementDAO");
            List listeDemande = new ArrayList();
            ListOrderedMap listPrdAtt = null;
            
           
            /*672*/
            listeDemande = 
                    statDomPlacementDAO.getStatOperMoyPay(tableauDeBordVo.getCodeStructure(), 
                                                          Constants.COD_OPER_PRELEV_ASSUR_VIE, 
                                                          tableauDeBordVo.getJourneeStructureDomaineId().getDatJrnJrn());

            listPrdAtt = (ListOrderedMap)listeDemande.get(0);
            tableauDeBordVo.setNbrOper672(Long.valueOf(listPrdAtt.getValue(0).toString()));
            if (listPrdAtt.getValue(1) != null) {
                tableauDeBordVo.setMntOper672(Double.valueOf(listPrdAtt.getValue(1).toString()));
            } else {
                tableauDeBordVo.setMntOper672(Double.valueOf("0"));
            }


            /*703*/
            listeDemande = 
                    statDomPlacementDAO.getStatOperMoyPay(tableauDeBordVo.getCodeStructure(), 
                                                          Constants.COD_OPER_REGLEMENT_ASSUR_VIE, 
                                                          tableauDeBordVo.getJourneeStructureDomaineId().getDatJrnJrn());

            listPrdAtt = (ListOrderedMap)listeDemande.get(0);
            tableauDeBordVo.setNbrOper703(Long.valueOf(listPrdAtt.getValue(0).toString()));
            if (listPrdAtt.getValue(1) != null) {
                tableauDeBordVo.setMntOper703(Double.valueOf(listPrdAtt.getValue(1).toString()));
            } else {
                tableauDeBordVo.setMntOper703(Double.valueOf("0"));
            }
           
            return tableauDeBordVo;


        } catch (Exception e) {
            com.oxia.fwk.core.Error erreur = new com.oxia.fwk.core.Error();
            erreur.setCode("Technique");
            erreur.setDescription("GetStatInteretServiTrt " + e.getMessage());
            tableauDeBordVo.addError(erreur);
            throw new Exception(e);
        }

    }

    public void genCroText(ValueObject vo) {

    }
}
