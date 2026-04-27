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
import com.bna.smile.model.clotureDomaine.model.LiquidationVo;
import com.bna.smile.model.clotureDomaine.model.StatLiquidPlacVo;
import com.bna.smile.model.clotureDomaine.model.StatPlacement;
import com.bna.smile.model.constant.Constants;
import com.bna.smile.model.domainecommun.model.Listes;
import com.oxia.fwk.context.Context;
import com.oxia.fwk.core.ICriteria;
import com.oxia.fwk.core.IExpression;
import com.oxia.fwk.core.ISearchEngine;
import com.oxia.fwk.core.IValueObject;
import com.oxia.fwk.core.ValueObject;

public class GetStatLiquidTrt extends Traitement {
    public GetStatLiquidTrt() {
    }

    public IValueObject perform(IValueObject vo) throws SQLException, 
                                                        Exception {

        Context context = ContextHandler.getContext();
        StatPlacement statPlacement = (StatPlacement)vo;
        Listes listeretour = new Listes();
        List listtemp = new ArrayList();
        List listtemp1 = new ArrayList();
        List listtemp2 = new ArrayList();
        List listtemp3 = new ArrayList();
        String remplie;
        String remplie1;
        String remplie2;
        Long nbrGlobLiqPart = new Long(0);
        Double mntGlobLiqPart = new Double(0);
        Long nbrGlobAVEch = new Long(0);
        Double mntGlobAVEch = new Double(0);
        Long nbrGlobBatch = new Long(0);
        Double mntGlobBatch = new Double(0);
        ListOrderedMap listeOperMoyPay = null;
        Long nbr309BC = new Long(0);
        Double mnt309BC = new Double(0);

        try {
            ISearchEngine searchEngine = 
                (ISearchEngine)Context.getInstance().getSpringContext().getBean("searchEngine");
            ICriteria criteria = searchEngine.createCriteria();
            IExpression expression = searchEngine.createExpression();
            StatDomPlacementDAO statDomPlacementDAO = 
                (StatDomPlacementDAO)context.getBean("statDomPlacementDAO");
            List listeDemande = new ArrayList();
            LiquidationVo liquidationVo = new LiquidationVo();
            remplie = "false";

            /*liquidation partielle*/

            StatLiquidPlacVo statLiquidPlacVo = new StatLiquidPlacVo();
            statLiquidPlacVo.setCod_prd_plc(new Long(1004));
            statLiquidPlacVo.setLib_prd_plc("BNA Placement");

            listeDemande = 
                    statDomPlacementDAO.getLiquiAVECHEParProduitType(statPlacement.getStructure(), 
                                                                     "A", 
                                                                     statPlacement.getDateJournee(), 
                                                                     new Long(1004), 
                                                                     "P");
            ListOrderedMap listLiquPart = (ListOrderedMap)listeDemande.get(0);
            statLiquidPlacVo.setNbr_Liquid_att(Long.valueOf(listLiquPart.getValue(0).toString()));
            if (statLiquidPlacVo.getNbr_Liquid_att().longValue() != 0) {
                remplie = "true";
            }
            if (listLiquPart.getValue(1) != null) {
                statLiquidPlacVo.setMnt_Liquid_att(StrHandler.formatmnt(Math.abs(Double.valueOf(listLiquPart.getValue(1).toString()))));
            } else {
                statLiquidPlacVo.setMnt_Liquid_att("0");
            }
            
            
            
            listeDemande = 
                    statDomPlacementDAO.getStatOperMoyPay(statPlacement.getStructure(), 
                                                          Long.valueOf(625), 
                                                          statPlacement.getDateJournee());

            listLiquPart = (ListOrderedMap)listeDemande.get(0);
            statLiquidPlacVo.setNbr_Liquid_Val(Long.valueOf(listLiquPart.getValue(0).toString()));
            nbrGlobLiqPart = 
                nbrGlobLiqPart + Long.valueOf(listLiquPart.getValue(0).toString());
            if (statLiquidPlacVo.getNbr_Liquid_Val().longValue() != 0) {
                remplie = "true";
            }
            if (listLiquPart.getValue(1) != null) {
                    statLiquidPlacVo.setMnt_Liquid_Val((StrHandler.formatmnt(Math.abs(Double.valueOf(listLiquPart.getValue(1).toString())))));
                    mntGlobLiqPart = 
                            mntGlobLiqPart + Double.valueOf(listLiquPart.getValue(1).toString());
                } else {
                    statLiquidPlacVo.setMnt_Liquid_Val(("0"));
                }
                if (remplie.equalsIgnoreCase("true")) {
                    listtemp.add(statLiquidPlacVo);
                }
                liquidationVo.setLiquidPart(listtemp);
            

           
            
            
            

            /*liquidation totale avant echance*/

            List l = searchEngine.findAll(ProduitPlacement.class);

            if (l != null && l.size() > 0) {
                for (Iterator it = l.iterator(); it.hasNext(); ) {
                    ListOrderedMap listPrdAtt = null;
                    remplie = "false";
                    remplie1 = "false";
                    remplie2 = "false";
                    StatLiquidPlacVo statLiquidPlacVo1 = 
                        new StatLiquidPlacVo();
                    StatLiquidPlacVo statLiquidPlacVo2 = 
                        new StatLiquidPlacVo();
                    StatLiquidPlacVo statLiquidPlacVo3 = 
                        new StatLiquidPlacVo();
                    ProduitPlacement produitPlacement = 
                        (ProduitPlacement)it.next();
                    statLiquidPlacVo1.setCod_prd_plc(produitPlacement.getCodPrdPlc());
                    statLiquidPlacVo1.setLib_prd_plc(produitPlacement.getLibPrdPlc());
                    statLiquidPlacVo2.setCod_prd_plc(produitPlacement.getCodPrdPlc());
                    statLiquidPlacVo2.setLib_prd_plc(produitPlacement.getLibPrdPlc());
                    statLiquidPlacVo3.setCod_prd_plc(produitPlacement.getCodPrdPlc());
                    statLiquidPlacVo3.setLib_prd_plc(produitPlacement.getLibPrdPlc());


                    listeDemande = 
                            statDomPlacementDAO.getLiquiAVECHEParProduitType(statPlacement.getStructure(), 
                                                                             "A", 
                                                                             statPlacement.getDateJournee(), 
                                                                             produitPlacement.getCodPrdPlc(), 
                                                                             "T");
                    listPrdAtt = (ListOrderedMap)listeDemande.get(0);
                    statLiquidPlacVo1.setNbr_Liquid_att(Long.valueOf(listPrdAtt.getValue(0).toString()));
                    if (statLiquidPlacVo1.getNbr_Liquid_att().longValue() != 
                        0) {
                        remplie = "true";
                    }
                    if (listPrdAtt.getValue(1) != null) {
                        statLiquidPlacVo1.setMnt_Liquid_att(StrHandler.formatmnt(Math.abs(Double.valueOf(listPrdAtt.getValue(1).toString()))));
                    } else {
                        statLiquidPlacVo1.setMnt_Liquid_att("0");
                    }
                    
                    
                    listeDemande = 
                            statDomPlacementDAO.getStatOperMoyPayParPrd(statPlacement.getStructure(), 
                                                                  Long.valueOf(309), 
                                                                  statPlacement.getDateJournee(),produitPlacement.getCodPrdPlc());
                    listPrdAtt = (ListOrderedMap)listeDemande.get(0);
                    statLiquidPlacVo1.setNbr_Liquid_Val(Long.valueOf(listPrdAtt.getValue(0).toString()));
                    nbrGlobAVEch = 
                            nbrGlobAVEch + Long.valueOf(listPrdAtt.getValue(0).toString());
                    if (statLiquidPlacVo1.getNbr_Liquid_Val().longValue() != 
                        0) {
                        remplie = "true";
                    }
                    if (listPrdAtt.getValue(1) != null) {
                        statLiquidPlacVo1.setMnt_Liquid_Val(StrHandler.formatmnt(Math.abs(Double.valueOf(listPrdAtt.getValue(1).toString()))));
                        mntGlobAVEch = 
                                mntGlobAVEch + Double.valueOf(listPrdAtt.getValue(1).toString());
                    } else {
                        statLiquidPlacVo1.setMnt_Liquid_Val(("0"));
                    }
                    if (remplie.equalsIgnoreCase("true")) {
                        listtemp1.add(statLiquidPlacVo1);
                    }

                    /*liquidation arrivé à echeance*/

                    listeDemande = 
                            statDomPlacementDAO.getContplacArriveAEcheance(statPlacement.getStructure(), 
                                                                           statPlacement.getDateJournee(), 
                                                                           produitPlacement.getCodPrdPlc(), 
                                                                           "V");
                    listPrdAtt = (ListOrderedMap)listeDemande.get(0);
                    statLiquidPlacVo2.setNbr_Liquid_Val(Long.valueOf(listPrdAtt.getValue(0).toString()));
                    if (statLiquidPlacVo2.getNbr_Liquid_Val().longValue() != 
                        0) {
                        remplie1 = "true";
                    }
                    if (listPrdAtt.getValue(1) != null) {
                        statLiquidPlacVo2.setMnt_Liquid_Val(StrHandler.formatmnt(Math.abs(Double.valueOf(listPrdAtt.getValue(1).toString()))));
                    } else {
                        statLiquidPlacVo2.setMnt_Liquid_Val("0");
                    }
                    if (remplie1.equalsIgnoreCase("true")) {
                        listtemp2.add(statLiquidPlacVo2);
                    }


                    /*liquidation traité le soir*/
                    listeDemande = 
                            statDomPlacementDAO.getLiquiAEcheanceTraiteleSoir(statPlacement.getStructure(), 
                                                                              produitPlacement.getCodPrdPlc(), 
                                                                              statPlacement.getDateJournee(), 
                                                                              "L");
                    listPrdAtt = (ListOrderedMap)listeDemande.get(0);
                    statLiquidPlacVo3.setNbr_Liq_Batch(Long.valueOf(listPrdAtt.getValue(0).toString()));
                    nbrGlobBatch = 
                            nbrGlobBatch + Long.valueOf(listPrdAtt.getValue(0).toString());
                    if (statLiquidPlacVo3.getNbr_Liq_Batch().longValue() != 
                        0) {
                        remplie2 = "true";
                    }
                    if (listPrdAtt.getValue(1) != null) {
                        statLiquidPlacVo3.setMnt_Liq_Batch(StrHandler.formatmnt(Math.abs(Double.valueOf(listPrdAtt.getValue(1).toString()))));
                        mntGlobBatch = 
                                mntGlobBatch + Double.valueOf(listPrdAtt.getValue(1).toString());
                    } else {
                        statLiquidPlacVo3.setMnt_Liq_Batch("0");
                    }
                    if (remplie2.equalsIgnoreCase("true")) {
                        listtemp3.add(statLiquidPlacVo3);
                    }
                }
                /*Rist int resiliation 309 du BC*/
                listeDemande = 
                        statDomPlacementDAO.getStatMouvInterne(statPlacement.getStructure(), 
                                                             Constants.COD_OPER_DEMANDE_LIQUID_ANTICIPE, 
                                                              statPlacement.getDateJournee());
                listeOperMoyPay = (ListOrderedMap)listeDemande.get(0);
                nbr309BC=(Long.valueOf(listeOperMoyPay.getValue(0).toString()));
                if (listeOperMoyPay.getValue(1) != null) {
                    mnt309BC=(Double.valueOf(listeOperMoyPay.getValue(1).toString()));
                } else {
                    mnt309BC=(Double.valueOf("0"));
                }
                nbrGlobAVEch=nbrGlobAVEch+nbr309BC;
                mntGlobAVEch=mntGlobAVEch+mnt309BC;
                
                liquidationVo.setLiquidPart(listtemp);
                liquidationVo.setLiquidAvantEch(listtemp1);
                liquidationVo.setLiquidArrivAEcheance(listtemp2);
                liquidationVo.setLiquidTraitBatch(listtemp3);
                liquidationVo.setNbrGlobliquidPart(nbrGlobLiqPart);
                liquidationVo.setMntGlobliquidPart(mntGlobLiqPart);
                liquidationVo.setNbrGlobliqAVEch(nbrGlobAVEch);
                liquidationVo.setMntGlobliqAVEch(mntGlobAVEch);
                liquidationVo.setNbrGlobliqBatch(nbrGlobBatch);
                liquidationVo.setMntGlobliqBatch(mntGlobBatch);
            }

            return liquidationVo;

        } catch (Exception e) {
            com.oxia.fwk.core.Error erreur = new com.oxia.fwk.core.Error();
            erreur.setCode("Technique");
            erreur.setDescription("GetStatLiquidTrt " + e.getMessage());
            statPlacement.addError(erreur);
            throw new Exception(e);
        }

    }

    public void genCroText(ValueObject vo) {

    }
}
