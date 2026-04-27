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
import com.bna.smile.model.clotureDomaine.model.StatInteretServiVo;
import com.bna.smile.model.clotureDomaine.model.StatPlacement;
import com.bna.smile.model.constant.Constants;
import com.oxia.fwk.context.Context;
import com.oxia.fwk.core.ICriteria;
import com.oxia.fwk.core.IExpression;
import com.oxia.fwk.core.ISearchEngine;
import com.oxia.fwk.core.IValueObject;
import com.oxia.fwk.core.ValueObject;

public class GetStatInteretServiTrt extends Traitement {
    public GetStatInteretServiTrt() {
    }

    public IValueObject perform(IValueObject vo) throws SQLException, 
                                                        Exception {

        Context context = ContextHandler.getContext();
        StatPlacement statPlacement = (StatPlacement)vo;
        PlacementVo placementVo = new PlacementVo();
        List listtemp = new ArrayList();
        String remplie;

        try {
            ISearchEngine searchEngine = 
                (ISearchEngine)Context.getInstance().getSpringContext().getBean("searchEngine");
            ICriteria criteria = searchEngine.createCriteria();
            IExpression expression = searchEngine.createExpression();
            StatDomPlacementDAO statDomPlacementDAO = 
                (StatDomPlacementDAO)context.getBean("statDomPlacementDAO");
            List listeDemande = new ArrayList();
            ListOrderedMap listeOperMoyPay = null;


            remplie = "false";
            Long nbrGlobIntPart = new Long(0);
            Double mntGlobIntPart = new Double(0);

            ListOrderedMap listPrdAtt = null;
            StatInteretServiVo statInteretServiVo = new StatInteretServiVo();
            List l = searchEngine.findAll(ProduitPlacement.class);

            if (l != null && l.size() > 0) {
                for (Iterator it = l.iterator(); it.hasNext(); ) {

                    remplie = "false";
                    ProduitPlacement produitPlacement = 
                        (ProduitPlacement)it.next();
                    statInteretServiVo.setCod_prd_plc(produitPlacement.getCodPrdPlc());
                    statInteretServiVo.setLib_prd_plc(produitPlacement.getLibPrdPlc());

                    /*interert partiels*/

                    listeDemande = 
                            statDomPlacementDAO.getInteretServi(statPlacement.getStructure(), 
                                                                Long.valueOf(617), 
                                                                statPlacement.getDateJournee(), 
                                                                produitPlacement.getCodPrdPlc());

                    listPrdAtt = (ListOrderedMap)listeDemande.get(0);
                    statInteretServiVo.setNbr_Int_Part(Long.valueOf(listPrdAtt.getValue(0).toString()));
                    nbrGlobIntPart = 
                            nbrGlobIntPart + Long.valueOf(listPrdAtt.getValue(0).toString());
                    if (statInteretServiVo.getNbr_Int_Part().longValue() != 
                        0) {
                        remplie = "true";
                    }
                    if (listPrdAtt.getValue(1) != null) {
                        statInteretServiVo.setMnt_Int_Part(StrHandler.formatmnt(Math.abs(Double.valueOf(listPrdAtt.getValue(1).toString()))));
                        mntGlobIntPart = 
                                mntGlobIntPart + Double.valueOf(listPrdAtt.getValue(1).toString());
                    } else {
                        statInteretServiVo.setMnt_Int_Part("0");
                    }
                    if (remplie.equalsIgnoreCase("true")) {
                        listtemp.add(statInteretServiVo);
                    }
                }
            }
            /*interet post*/
            listeDemande = 
                    statDomPlacementDAO.getStatOperMoyPay(statPlacement.getStructure(), 
                                                          Long.valueOf(613), 
                                                          statPlacement.getDateJournee());

            listPrdAtt = (ListOrderedMap)listeDemande.get(0);
            placementVo.setNbrGlobIntPost(Long.valueOf(listPrdAtt.getValue(0).toString()));
            if (listPrdAtt.getValue(1) != null) {
                placementVo.setMntGlobalIntPost(Double.valueOf(listPrdAtt.getValue(1).toString()));
            } else {
                placementVo.setMntGlobalIntPost(Double.valueOf("0"));
            }

            /*interet pre*/
            listeDemande = 
                    statDomPlacementDAO.getStatOperMoyPay(statPlacement.getStructure(), 
                                                          Long.valueOf(320), 
                                                          statPlacement.getDateJournee());
            listPrdAtt = (ListOrderedMap)listeDemande.get(0);
            placementVo.setNbrGlobIntPre(Long.valueOf(listPrdAtt.getValue(0).toString()));
            if (listPrdAtt.getValue(1) != null) {
                placementVo.setMntGlobalIntPre(Double.valueOf(listPrdAtt.getValue(1).toString()));
            } else {
                placementVo.setMntGlobalIntPre(Double.valueOf("0"));
            }


            /*ristourne interet*/
            listeDemande = 
                    statDomPlacementDAO.getStatOperMoyPay(statPlacement.getStructure(), 
                                                          Long.valueOf(331), 
                                                          statPlacement.getDateJournee());

            listPrdAtt = (ListOrderedMap)listeDemande.get(0);
            placementVo.setNbrGlobResInt(Long.valueOf(listPrdAtt.getValue(0).toString()));
            if (listPrdAtt.getValue(1) != null) {
                placementVo.setMntGlobalResInt(Double.valueOf(listPrdAtt.getValue(1).toString()));
            } else {
                placementVo.setMntGlobalResInt(Double.valueOf("0"));
            }


            /*restitution interet au client*/
            listeDemande = 
                    statDomPlacementDAO.getStatOperMoyPay(statPlacement.getStructure(), 
                                                          Long.valueOf(322), 
                                                          statPlacement.getDateJournee());

            listPrdAtt = (ListOrderedMap)listeDemande.get(0);
            placementVo.setNbrGlobrestit(Long.valueOf(listPrdAtt.getValue(0).toString()));

            if (listPrdAtt.getValue(1) != null) {
                placementVo.setMntGlobalrestit(Double.valueOf(listPrdAtt.getValue(1).toString()));
            } else {
                placementVo.setMntGlobalrestit(Double.valueOf("0"));
            }

            /*versement interet suite � liquidation*/
            listeDemande = 
                    statDomPlacementDAO.getStatOperMoyPay(statPlacement.getStructure(), 
                                                          Long.valueOf(321), 
                                                          statPlacement.getDateJournee());

            listPrdAtt = (ListOrderedMap)listeDemande.get(0);
            placementVo.setNbrGlobVerIntLiq(Long.valueOf(listPrdAtt.getValue(0).toString()));
            if (listPrdAtt.getValue(1) != null) {
                placementVo.setMntGlobalVerIntLiq(Double.valueOf(listPrdAtt.getValue(1).toString()));
            } else {
                placementVo.setMntGlobalVerIntLiq(Double.valueOf("0"));
            }

            /*recup�ration BC*/
            listeDemande = 
                    statDomPlacementDAO.getStatOperMoyPay(statPlacement.getStructure(), 
                                                          Long.valueOf(313), 
                                                          statPlacement.getDateJournee());

            listPrdAtt = (ListOrderedMap)listeDemande.get(0);
            placementVo.setNbrGloblRecBC(Long.valueOf(listPrdAtt.getValue(0).toString()));
            if (listPrdAtt.getValue(1) != null) {
                placementVo.setMntGlobalRecBC(Double.valueOf(listPrdAtt.getValue(1).toString()));
            } else {
                placementVo.setMntGlobalRecBC(Double.valueOf("0"));
            }

            /*Perception interet sur avances*/
            listeDemande = 
                    statDomPlacementDAO.getStatOperMoyPay(statPlacement.getStructure(), 
                                                          Long.valueOf(302), 
                                                          statPlacement.getDateJournee());
            listeOperMoyPay = (ListOrderedMap)listeDemande.get(0);
            placementVo.setNbrGlobPerIntAvanc(Long.valueOf(listeOperMoyPay.getValue(0).toString()));
            if (listeOperMoyPay.getValue(1) != null) {
                placementVo.setMntGlobalPerIntAvanc(Double.valueOf(listeOperMoyPay.getValue(1).toString()));
            } else {
                placementVo.setMntGlobalPerIntAvanc(Double.valueOf("0"));
            }

            /*ristourne interet sur avances*/
            listeDemande = 
                    statDomPlacementDAO.getStatOperMoyPay(statPlacement.getStructure(), 
                                                          Long.valueOf(303), 
                                                          statPlacement.getDateJournee());
            listeOperMoyPay = (ListOrderedMap)listeDemande.get(0);
            placementVo.setNbrGlobRistIntAvanc(Long.valueOf(listeOperMoyPay.getValue(0).toString()));
            if (listeOperMoyPay.getValue(1) != null) {
                placementVo.setMntGlobalRistIntAvanc(Double.valueOf(listeOperMoyPay.getValue(1).toString()));
            } else {
                placementVo.setMntGlobalRistIntAvanc(Double.valueOf("0"));
            }
            /*perception interet complementaire*/
            listeDemande = 
                    statDomPlacementDAO.getStatOperMoyPay(statPlacement.getStructure(), 
                                                          Long.valueOf(616), 
                                                          statPlacement.getDateJournee());
            listeOperMoyPay = (ListOrderedMap)listeDemande.get(0);
            placementVo.setNbrGlobPerIntCompRemb(Long.valueOf(listeOperMoyPay.getValue(0).toString()));
            if (listeOperMoyPay.getValue(1) != null) {
                placementVo.setMntGlobalPerIntCompRemb(Double.valueOf(listeOperMoyPay.getValue(1).toString()));
            } else {
                placementVo.setMntGlobalPerIntCompRemb(Double.valueOf("0"));
            }
            /*abonnement ext interet*/
            listeDemande = 
                    statDomPlacementDAO.getStatMouvInterne(statPlacement.getStructure(), 
                                                           Long.valueOf(619), 
                                                           statPlacement.getDateJournee());
            listeOperMoyPay = (ListOrderedMap)listeDemande.get(0);
            placementVo.setNbrGlobAbonExtInt(Long.valueOf(listeOperMoyPay.getValue(0).toString()));
            if (listeOperMoyPay.getValue(1) != null) {
                placementVo.setMntGlobalAbonExtInt(Double.valueOf(listeOperMoyPay.getValue(1).toString()));
            } else {
                placementVo.setMntGlobalAbonExtInt(Double.valueOf("0"));
            }
            /*COD_OPER_ABONNE_INTERET_PLAC_POSTCOMPTE ("618")*/
            listeDemande = 
                    statDomPlacementDAO.getStatMouvInterne(statPlacement.getStructure(), 
                                                           Long.valueOf(618), 
                                                           statPlacement.getDateJournee());
            listeOperMoyPay = (ListOrderedMap)listeDemande.get(0);
            placementVo.setNbrGlobAbonIntPlacPost(Long.valueOf(listeOperMoyPay.getValue(0).toString()));
            if (listeOperMoyPay.getValue(1) != null) {
                placementVo.setMntGlobalAbonIntPlacPost(Double.valueOf(listeOperMoyPay.getValue(1).toString()));
            } else {
                placementVo.setMntGlobalAbonIntPlacPost(Double.valueOf("0"));
            }
            /*COD_OPER_ABONNE_INTERET_PLAC_PRECOMPTE ("620")*/
            listeDemande = 
                    statDomPlacementDAO.getStatMouvInterne(statPlacement.getStructure(), 
                                                           Long.valueOf(620), 
                                                           statPlacement.getDateJournee());
            listeOperMoyPay = (ListOrderedMap)listeDemande.get(0);
            placementVo.setNbrGlobAbonIntPlacPre(Long.valueOf(listeOperMoyPay.getValue(0).toString()));
            if (listeOperMoyPay.getValue(1) != null) {
                placementVo.setMntGlobalAbonIntPlacPre(Double.valueOf(listeOperMoyPay.getValue(1).toString()));
            } else {
                placementVo.setMntGlobalAbonIntPlacPre(Double.valueOf("0"));
            }
            
            /*COD_OPER_ABON_INTERET_REMB_AVANCE_PLAC ("304")*/
            listeDemande = 
                    statDomPlacementDAO.getStatMouvInterne(statPlacement.getStructure(), 
                                                           Long.valueOf(304), 
                                                           statPlacement.getDateJournee());
            listeOperMoyPay = (ListOrderedMap)listeDemande.get(0);
            placementVo.setNbrGlobAbonIntRembAv(Long.valueOf(listeOperMoyPay.getValue(0).toString()));
            if (listeOperMoyPay.getValue(1) != null) {
                placementVo.setMntGlobalAbonIntRembAv(Double.valueOf(listeOperMoyPay.getValue(1).toString()));
            } else {
                placementVo.setMntGlobalAbonIntRembAv(Double.valueOf("0"));
            }
            
            /*COD_OPER_RESILIATION 629*/
            listeDemande = 
                    statDomPlacementDAO.getStatOperMoyPay(statPlacement.getStructure(), 
                                                         Constants.COD_OPER_RESILIATION, 
                                                          statPlacement.getDateJournee());
            listeOperMoyPay = (ListOrderedMap)listeDemande.get(0);
            placementVo.setNbrGlobResiliation(Long.valueOf(listeOperMoyPay.getValue(0).toString()));
            if (listeOperMoyPay.getValue(1) != null) {
                placementVo.setMntGlobalResiliation(Double.valueOf(listeOperMoyPay.getValue(1).toString()));
            } else {
                placementVo.setMntGlobalResiliation(Double.valueOf("0"));
            }
            
            /*Vers int resiliation 631*/
            listeDemande = 
                    statDomPlacementDAO.getStatOperMoyPay(statPlacement.getStructure(), 
                                                         Constants.COD_OPER_VERS_INTERET_SUITE_RESILIATION, 
                                                          statPlacement.getDateJournee());
            listeOperMoyPay = (ListOrderedMap)listeDemande.get(0);
            placementVo.setNbrGlobVersIntResi(Long.valueOf(listeOperMoyPay.getValue(0).toString()));
            if (listeOperMoyPay.getValue(1) != null) {
                placementVo.setMntGlobalVersIntResi(Double.valueOf(listeOperMoyPay.getValue(1).toString()));
            } else {
                placementVo.setMntGlobalVersIntResi(Double.valueOf("0"));
            }
            
            /*Rist int resiliation 630*/
            listeDemande = 
                    statDomPlacementDAO.getStatOperMoyPay(statPlacement.getStructure(), 
                                                         Constants.COD_OPER_RISTOURNE_INTERET_SUITE_RESILIATION, 
                                                          statPlacement.getDateJournee());
            listeOperMoyPay = (ListOrderedMap)listeDemande.get(0);
            placementVo.setNbrGlobRistIntResi(Long.valueOf(listeOperMoyPay.getValue(0).toString()));
            if (listeOperMoyPay.getValue(1) != null) {
                placementVo.setMntGlobalRistIntResi(Double.valueOf(listeOperMoyPay.getValue(1).toString()));
            } else {
                placementVo.setMntGlobalRistIntResi(Double.valueOf("0"));
            }
            
            /*Rist int resiliation 641*/
            listeDemande = 
                    statDomPlacementDAO.getStatOperMoyPay(statPlacement.getStructure(), 
                                                         Constants.COD_OPER_DEMANDE_LIQUID_ANTICIPE_SBDV, 
                                                          statPlacement.getDateJournee());
            listeOperMoyPay = (ListOrderedMap)listeDemande.get(0);
            placementVo.setNbrOper641(Long.valueOf(listeOperMoyPay.getValue(0).toString()));
            if (listeOperMoyPay.getValue(1) != null) {
                placementVo.setMntOper641(Double.valueOf(listeOperMoyPay.getValue(1).toString()));
            } else {
                placementVo.setMntOper641(Double.valueOf("0"));
            }
            
            /*Rist int resiliation 642*/
            listeDemande = 
                    statDomPlacementDAO.getStatOperMoyPay(statPlacement.getStructure(), 
                                                         Constants.COD_OPER_SOUSC_PLAC_SBDV, 
                                                          statPlacement.getDateJournee());
            listeOperMoyPay = (ListOrderedMap)listeDemande.get(0);
            placementVo.setNbrOper642(Long.valueOf(listeOperMoyPay.getValue(0).toString()));
            if (listeOperMoyPay.getValue(1) != null) {
                placementVo.setMntOper642(Double.valueOf(listeOperMoyPay.getValue(1).toString()));
            } else {
                placementVo.setMntOper642(Double.valueOf("0"));
            }
            
            /*Rist int resiliation 643*/
            listeDemande = 
                    statDomPlacementDAO.getStatOperMoyPay(statPlacement.getStructure(), 
                                                         Constants.OPER_INT_PRE_SOUSC_PLAC_SBDV, 
                                                          statPlacement.getDateJournee());
            listeOperMoyPay = (ListOrderedMap)listeDemande.get(0);
            placementVo.setNbrOper643(Long.valueOf(listeOperMoyPay.getValue(0).toString()));
            if (listeOperMoyPay.getValue(1) != null) {
                placementVo.setMntOper643(Double.valueOf(listeOperMoyPay.getValue(1).toString()));
            } else {
                placementVo.setMntOper643(Double.valueOf("0"));
            }
            
            /*Rist int resiliation 644*/
            listeDemande = 
                    statDomPlacementDAO.getStatMouvInterne(statPlacement.getStructure(), 
                                                         Constants.OPER_INT_POST_SOUSC_PLAC_SBDV, 
                                                          statPlacement.getDateJournee());
            listeOperMoyPay = (ListOrderedMap)listeDemande.get(0);
            placementVo.setNbrOper644(Long.valueOf(listeOperMoyPay.getValue(0).toString()));
            if (listeOperMoyPay.getValue(1) != null) {
                placementVo.setMntOper644(Double.valueOf(listeOperMoyPay.getValue(1).toString()));
            } else {
                placementVo.setMntOper644(Double.valueOf("0"));
            }
            
            /*Rist int resiliation 645*/
            listeDemande = 
                    statDomPlacementDAO.getStatOperMoyPay(statPlacement.getStructure(), 
                                                         Constants.COD_OPER_DEMANDE_LIQUID_ANTICIPE_PARTIELLE_SBDV, 
                                                          statPlacement.getDateJournee());
            listeOperMoyPay = (ListOrderedMap)listeDemande.get(0);
            placementVo.setNbrOper645(Long.valueOf(listeOperMoyPay.getValue(0).toString()));
            if (listeOperMoyPay.getValue(1) != null) {
                placementVo.setMntOper645(Double.valueOf(listeOperMoyPay.getValue(1).toString()));
            } else {
                placementVo.setMntOper645(Double.valueOf("0"));
            }
            
            /*COD_OPER_ABONNE_AVANC_ECHU_PLAC 615*/
            listeDemande = 
                    statDomPlacementDAO.getStatMouvInterne(statPlacement.getStructure(), 
                                                         Constants.COD_OPER_ABONNE_AVANC_ECHU_PLAC, 
                                                          statPlacement.getDateJournee());
            listeOperMoyPay = (ListOrderedMap)listeDemande.get(0);
            placementVo.setNbrOper615(Long.valueOf(listeOperMoyPay.getValue(0).toString()));
            if (listeOperMoyPay.getValue(1) != null) {
                placementVo.setMntOper615(Double.valueOf(listeOperMoyPay.getValue(1).toString()));
            } else {
                placementVo.setMntOper615(Double.valueOf("0"));
            }
            
            
            placementVo.setList(listtemp);
            placementVo.setNbrGlobIntPart(nbrGlobIntPart);
            placementVo.setMntGlobalIntPart(mntGlobIntPart);
            return placementVo;


        } catch (Exception e) {
            com.oxia.fwk.core.Error erreur = new com.oxia.fwk.core.Error();
            erreur.setCode("Technique");
            erreur.setDescription("GetStatInteretServiTrt " + e.getMessage());
            placementVo.addError(erreur);
            throw new Exception(e);
        }

    }

    public void genCroText(ValueObject vo) {

    }
}
