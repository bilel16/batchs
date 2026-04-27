package com.bna.smile.model.pilotage.traitement;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.map.ListOrderedMap;

import com.bna.commun.traitements.Traitement;
import com.bna.commun.util.ContextHandler;
import com.bna.commun.util.StrHandler;
import com.bna.smile.model.pilotage.dao.PilotageDAO;
import com.bna.smile.model.pilotage.model.ParamClientVo;
import com.oxia.fwk.context.Context;
import com.oxia.fwk.core.IValueObject;
import com.oxia.fwk.core.ValueObject;

public class GetDonnClientTrt extends Traitement{
    public GetDonnClientTrt() {
    }
    public IValueObject perform(IValueObject vo) throws SQLException, 
                                                        Exception {
        ParamClientVo paramClientVo = (ParamClientVo)vo;

        Context context = ContextHandler.getContext();
        try {
    
            PilotageDAO pilotageDAO = 
                (PilotageDAO)context.getBean("pilotageDAO");
            ListOrderedMap listreq = null;
            Long nombre = Long.valueOf(0);
            List list = new ArrayList();
            
            nombre=(Long)pilotageDAO.getNombreDemChq(paramClientVo.getCodStrcStrc(),paramClientVo.getCodTpcePers()
                        ,paramClientVo.getNumPcePers());
            paramClientVo.setNbrDemChq(nombre); 
            
            nombre=(Long)pilotageDAO.getNombreDemCarte(paramClientVo.getCodStrcStrc(),paramClientVo.getCodTpcePers()
                        ,paramClientVo.getNumPcePers());
            paramClientVo.setNbrDemCart(nombre);
            
            nombre=(Long)pilotageDAO.getNombreOPP(paramClientVo.getCodStrcStrc(),paramClientVo.getCodTpcePers()
                        ,paramClientVo.getNumPcePers());
            paramClientVo.setNbrDemOpp(nombre);
            
            nombre=(Long)pilotageDAO.getNombreInter(paramClientVo.getCodTpcePers()
                        ,paramClientVo.getNumPcePers());
            paramClientVo.setNbrInterdict(nombre);
            /*interdiction*/
             /*list=(List)pilotageDAO.getStatInterdict(paramClientVo.getCodTpcePers()
                         ,paramClientVo.getNumPcePers());
             listreq = (ListOrderedMap)list.get(0);
             paramClientVo.setNbrInterdict(Long.valueOf(listreq.getValue(0).toString()));
             if (listreq.getValue(1) != null) {
                 paramClientVo.setDatinterd((listreq.getValue(1).toString()));
             } else {
                 paramClientVo.setDatinterd("");
             }*/
            /*contrat depot*/
            list=(List)pilotageDAO.getStatContratCptDepot(paramClientVo.getCodStrcStrc(),paramClientVo.getCodTpcePers()
                        ,paramClientVo.getNumPcePers(),"V");
            listreq = (ListOrderedMap)list.get(0);
            paramClientVo.setNbrDepot(Long.valueOf(listreq.getValue(0).toString()));
            if (listreq.getValue(1) != null) {
                paramClientVo.setMntDepot(Double.valueOf(listreq.getValue(1).toString()));
            } else {
                paramClientVo.setMntDepot(Double.valueOf("0"));
            }
            if (listreq.getValue(2) != null) {
                paramClientVo.setMntFacilDepot(StrHandler.formatmnt(Math.abs(Double.valueOf(listreq.getValue(2).toString()))));
            } else {
                paramClientVo.setMntFacilDepot("0");
            }
            /*contrat CTX*/
            list=(List)pilotageDAO.getStatContratCptDepot(paramClientVo.getCodStrcStrc(),paramClientVo.getCodTpcePers()
                        ,paramClientVo.getNumPcePers(),"T");
            listreq = (ListOrderedMap)list.get(0);
            paramClientVo.setNbrCtx(Long.valueOf(listreq.getValue(0).toString()));
            if (listreq.getValue(1) != null) {
                paramClientVo.setMntCtx(Double.valueOf(listreq.getValue(1).toString()));
            } else {
                paramClientVo.setMntCtx(Double.valueOf("0"));
            }
            
            /*contrat epargne*/  
            list=(List)pilotageDAO.getStatContratCptEpargne(paramClientVo.getCodTpcePers()
                        ,paramClientVo.getNumPcePers());
            listreq = (ListOrderedMap)list.get(0);
            paramClientVo.setNbrEparg(Long.valueOf(listreq.getValue(0).toString()));
            if (listreq.getValue(1) != null) {
                paramClientVo.setMntEparg(StrHandler.formatmnt(Math.abs(Double.valueOf(listreq.getValue(1).toString()))));
            } else {
                paramClientVo.setMntEparg("0");
            } 
            /*contrat epargne lié*/  
            list=(List)pilotageDAO.getStatContratCptEpargneLie(paramClientVo.getCodTpcePers()
                        ,paramClientVo.getNumPcePers());
            listreq = (ListOrderedMap)list.get(0);
            paramClientVo.setNbrEpargLie(Long.valueOf(listreq.getValue(0).toString()));
            if (listreq.getValue(1) != null) {
                paramClientVo.setMntEpargLie(StrHandler.formatmnt(Math.abs(Double.valueOf(listreq.getValue(1).toString()))));
            } else {
                paramClientVo.setMntEpargLie("0");
            } 
            /*Placement*/  
            list=(List)pilotageDAO.getStatPlacement(paramClientVo.getCodStrcStrc(),paramClientVo.getCodTpcePers()
                        ,paramClientVo.getNumPcePers());
            listreq = (ListOrderedMap)list.get(0);
            paramClientVo.setNbrPlacement(Long.valueOf(listreq.getValue(0).toString()));
            if (listreq.getValue(1) != null) {
                paramClientVo.setMntPlacement(StrHandler.formatmnt(Math.abs(Double.valueOf(listreq.getValue(1).toString()))));
            } else {
                paramClientVo.setMntPlacement("0");
            } 
            
            /*Engagement*/  
            list=(List)pilotageDAO.getStatEngagement(paramClientVo.getCodStrcStrc(),paramClientVo.getCodTpcePers()
                        ,paramClientVo.getNumPcePers());
            listreq = (ListOrderedMap)list.get(0);
            paramClientVo.setNbrEng(Long.valueOf(listreq.getValue(0).toString()));
            if (listreq.getValue(1) != null) {
                paramClientVo.setMntEng(StrHandler.formatmnt(Math.abs(Double.valueOf(listreq.getValue(1).toString()))));
            } else {
                paramClientVo.setMntEng("0");
            } 
            
    
        } catch (Exception e) {
             com.oxia.fwk.core.Error erreur = new com.oxia.fwk.core.Error();
            erreur.setCode("Technique");
            erreur.setDescription("GetDonnClientTrt " + e.getMessage());
            paramClientVo.addError(erreur);
            throw new Exception(e);
    }
    return (paramClientVo);
 }
    public void genCroText(ValueObject vo) {

    }
}
