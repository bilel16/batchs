package com.bna.smile.model.clotureDomaine.traitement;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.collections.map.ListOrderedMap;

import com.bna.commun.model.ProduitPlacement;
import com.bna.commun.traitements.Traitement;
import com.bna.commun.util.ContextHandler;
import com.bna.commun.util.StrHandler;
import com.bna.smile.model.clotureDomaine.dao.StatDomPlacementDAO;
import com.bna.smile.model.clotureDomaine.model.PlacementVo;
import com.bna.smile.model.clotureDomaine.model.StatPlacement;
import com.bna.smile.model.clotureDomaine.model.StatRenPlacVo;
import com.oxia.fwk.context.Context;
import com.oxia.fwk.core.ICriteria;
import com.oxia.fwk.core.IExpression;
import com.oxia.fwk.core.ISearchEngine;
import com.oxia.fwk.core.IValueObject;
import com.oxia.fwk.core.ValueObject;

public class GetStatrenouvPlacTrt extends Traitement {
    public GetStatrenouvPlacTrt() {
    }

    public IValueObject perform(IValueObject vo) throws SQLException, 
                                                        Exception {

        Context context = ContextHandler.getContext();
        StatPlacement statPlacement = (StatPlacement)vo;
        PlacementVo placementVo = new PlacementVo();
        List listtemp = new ArrayList();
        String remplie;
        Long nbrGlobRen = new Long(0);
        Double mntGlobRen = new Double(0);
        Long nbrGlobBtach = new Long(0);
        Double mntGlobBatch = new Double(0);

        try {
            ISearchEngine searchEngine = 
                (ISearchEngine)Context.getInstance().getSpringContext().getBean("searchEngine");
            ICriteria criteria = searchEngine.createCriteria();
            IExpression expression = searchEngine.createExpression();
            StatDomPlacementDAO statDomPlacementDAO = 
                (StatDomPlacementDAO)context.getBean("statDomPlacementDAO");
            List listeDemande = new ArrayList();

            remplie = "false";

            List l = searchEngine.findAll(ProduitPlacement.class);

            if (l != null && l.size() > 0) {
                for (Iterator it = l.iterator(); it.hasNext(); ) {
                    ListOrderedMap listPrdAtt = null;
                    remplie = "false";
                    StatRenPlacVo statRenPlacVo = new StatRenPlacVo();
                    ProduitPlacement produitPlacement = 
                        (ProduitPlacement)it.next();
                    statRenPlacVo.setCod_prd_plc(produitPlacement.getCodPrdPlc());
                    statRenPlacVo.setLib_prd_plc(produitPlacement.getLibPrdPlc());

                    listeDemande = 
                            statDomPlacementDAO.getNbrDemRenPlacType(statPlacement.getStructure(), 
                                                                     produitPlacement.getCodPrdPlc(), 
                                                                     "V", 
                                                                     Long.valueOf(1), 
                                                                     statPlacement.getDateJournee());

                    listPrdAtt = (ListOrderedMap)listeDemande.get(0);
                    statRenPlacVo.setNbr_DemRen_AVEch(Long.valueOf(listPrdAtt.getValue(0).toString()));
                    if (statRenPlacVo.getNbr_DemRen_AVEch().longValue() != 0) {
                        remplie = "true";
                    }
                    if (listPrdAtt.getValue(1) != null) {
                        statRenPlacVo.setMnt_DemRen_AVEch(StrHandler.formatmnt(Math.abs(Double.valueOf(listPrdAtt.getValue(1).toString()))));
                    } else {
                        statRenPlacVo.setMnt_DemRen_AVEch("0");
                    }


                    listeDemande = 
                            statDomPlacementDAO.getNbrDemRenPlacType(statPlacement.getStructure(), 
                                                                     produitPlacement.getCodPrdPlc(), 
                                                                     "V", 
                                                                     Long.valueOf(2), 
                                                                     statPlacement.getDateJournee());

                    listPrdAtt = (ListOrderedMap)listeDemande.get(0);
                    statRenPlacVo.setNbr_DemRen_APEch(Long.valueOf(listPrdAtt.getValue(0).toString()));
                    if (statRenPlacVo.getNbr_DemRen_APEch().longValue() != 0) {
                        remplie = "true";
                    }
                    if (listPrdAtt.getValue(1) != null) {
                        statRenPlacVo.setMnt_DemRen_APEch(StrHandler.formatmnt(Math.abs(Double.valueOf(listPrdAtt.getValue(1).toString()))));
                    } else {
                        statRenPlacVo.setMnt_DemRen_APEch("0");
                    }

                    listeDemande = 
                            statDomPlacementDAO.getNbrDemRenPlacRej(statPlacement.getStructure(), 
                                                                    produitPlacement.getCodPrdPlc(), 
                                                                    statPlacement.getDateJournee());

                    listPrdAtt = (ListOrderedMap)listeDemande.get(0);
                    statRenPlacVo.setNbr_DemRen_Rej(Long.valueOf(listPrdAtt.getValue(0).toString()));
                    if (statRenPlacVo.getNbr_DemRen_Rej().longValue() != 0) {
                        remplie = "true";
                    }
                    if (listPrdAtt.getValue(1) != null) {
                        statRenPlacVo.setMnt_DemRen_Rej(StrHandler.formatmnt(Math.abs(Double.valueOf(listPrdAtt.getValue(1).toString()))));
                    } else {
                        statRenPlacVo.setMnt_DemRen_Rej("0");
                    }

                    listeDemande = 
                            statDomPlacementDAO.getNbrRenPlacParEtat(statPlacement.getStructure(), 
                                                                     produitPlacement.getCodPrdPlc(), 
                                                                     "V", 
                                                                     statPlacement.getDateJournee());

                    listPrdAtt = (ListOrderedMap)listeDemande.get(0);
                    statRenPlacVo.setNbr_Ren_Val(Long.valueOf(listPrdAtt.getValue(0).toString()));
                    if (statRenPlacVo.getNbr_Ren_Val().longValue() != 0) {
                        remplie = "true";
                    }
                    if (listPrdAtt.getValue(1) != null) {
                        statRenPlacVo.setMnt_Ren_Val(StrHandler.formatmnt(Math.abs(Double.valueOf(listPrdAtt.getValue(1).toString()))));

                    } else {
                        statRenPlacVo.setMnt_Ren_Val("0");
                    }


                    listeDemande = 
                            statDomPlacementDAO.getNbrRenPlacApresEch(statPlacement.getStructure(), 
                                                                      produitPlacement.getCodPrdPlc(), 
                                                                      Long.valueOf(318), 
                                                                      statPlacement.getDateJournee());

                    listPrdAtt = (ListOrderedMap)listeDemande.get(0);
                    statRenPlacVo.setNbr_Ren_apech(Long.valueOf(listPrdAtt.getValue(0).toString()));
                    nbrGlobRen = 
                            nbrGlobRen + Long.valueOf(listPrdAtt.getValue(0).toString());
                    if (statRenPlacVo.getNbr_Ren_apech().longValue() != 0) {
                        remplie = "true";
                    }
                    if (listPrdAtt.getValue(1) != null) {
                        statRenPlacVo.setMnt_Ren_apech(StrHandler.formatmnt(Math.abs(Double.valueOf(listPrdAtt.getValue(1).toString()))));
                        mntGlobRen = 
                                mntGlobRen + Double.valueOf(listPrdAtt.getValue(1).toString());
                    } else {
                        statRenPlacVo.setMnt_Ren_apech("0");
                    }

                    listeDemande = 
                            statDomPlacementDAO.getNbrRenPlacApresEch(statPlacement.getStructure(), 
                                                                      produitPlacement.getCodPrdPlc(), 
                                                                      Long.valueOf(317), 
                                                                      statPlacement.getDateJournee());

                    listPrdAtt = (ListOrderedMap)listeDemande.get(0);
                    statRenPlacVo.setNbr_Ren_batch(Long.valueOf(listPrdAtt.getValue(0).toString()));
                    nbrGlobBtach = 
                            nbrGlobBtach + Long.valueOf(listPrdAtt.getValue(0).toString());
                    if (statRenPlacVo.getNbr_Ren_batch().longValue() != 0) {
                        remplie = "true";
                    }
                    if (listPrdAtt.getValue(1) != null) {
                        statRenPlacVo.setMnt_Ren_batch(StrHandler.formatmnt(Math.abs(Double.valueOf(listPrdAtt.getValue(1).toString()))));
                        mntGlobBatch = 
                                mntGlobBatch + Double.valueOf(listPrdAtt.getValue(1).toString());
                    } else {
                        statRenPlacVo.setMnt_Ren_batch("0");
                    }


                    if (remplie.equalsIgnoreCase("true")) {
                        listtemp.add(statRenPlacVo);
                    }
                }
                placementVo.setList(listtemp);
                placementVo.setNbrGlobRenAPEch(nbrGlobRen);
                placementVo.setMntGlobalRenAPEch(mntGlobRen);
                placementVo.setNbrGlobRenBatch(nbrGlobBtach);
                placementVo.setMntGlobalRenBatch(mntGlobBatch);
            }

            return placementVo;


        } catch (Exception e) {
            com.oxia.fwk.core.Error erreur = new com.oxia.fwk.core.Error();
            erreur.setCode("Technique");
            erreur.setDescription("GetStatrenouvPlacTrt " + e.getMessage());
            statPlacement.addError(erreur);
            throw new Exception(e);
        }

    }

    public void genCroText(ValueObject vo) {

    }
}
