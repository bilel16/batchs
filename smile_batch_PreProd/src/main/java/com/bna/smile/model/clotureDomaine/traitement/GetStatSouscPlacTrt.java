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
import com.bna.smile.model.clotureDomaine.model.StatSouscriptionVo;
import com.oxia.fwk.context.Context;
import com.oxia.fwk.core.ICriteria;
import com.oxia.fwk.core.IExpression;
import com.oxia.fwk.core.ISearchEngine;
import com.oxia.fwk.core.IValueObject;
import com.oxia.fwk.core.ValueObject;

public class GetStatSouscPlacTrt extends Traitement {
    public GetStatSouscPlacTrt() {
    }

    public IValueObject perform(IValueObject vo) throws SQLException, 
                                                        Exception {

        Context context = ContextHandler.getContext();
        StatPlacement statPlacement = (StatPlacement)vo;
        PlacementVo placementVo = new PlacementVo();
        List listtemp = new ArrayList();
        try {
            ISearchEngine searchEngine = 
                (ISearchEngine)Context.getInstance().getSpringContext().getBean("searchEngine");
            ICriteria criteria = searchEngine.createCriteria();
            IExpression expression = searchEngine.createExpression();
            StatDomPlacementDAO statDomPlacementDAO = 
                (StatDomPlacementDAO)context.getBean("statDomPlacementDAO");
            List listeDemande = new ArrayList();
            String remplie;
            Long nbrGlob = new Long(0);
            Double mntGlob = new Double(0);
            List l = searchEngine.findAll(ProduitPlacement.class);

            if (l != null && l.size() > 0) {
                for (Iterator it = l.iterator(); it.hasNext(); ) {
                    ListOrderedMap listPrdAtt = null;
                    remplie = "false";
                    StatSouscriptionVo statSouscriptionVo = 
                        new StatSouscriptionVo();
                    ProduitPlacement produitPlacement = 
                        (ProduitPlacement)it.next();
                    statSouscriptionVo.setCod_prd_plc(produitPlacement.getCodPrdPlc());
                    statSouscriptionVo.setLib_prd_plc(produitPlacement.getLibPrdPlc());

                    /*soucsriptions en attente de validation*/
                    listeDemande = 
                            statDomPlacementDAO.getNbrSouscEnAttente(statPlacement.getStructure(), 
                                                                     produitPlacement.getCodPrdPlc(), 
                                                                     "V", 
                                                                     statPlacement.getDateJournee());
                    listPrdAtt = (ListOrderedMap)listeDemande.get(0);
                    statSouscriptionVo.setNbr_Sousc_att(Long.valueOf(listPrdAtt.getValue(0).toString()));
                    if (statSouscriptionVo.getNbr_Sousc_att().longValue() != 
                        0) {
                        remplie = "true";
                    }
                    if (listPrdAtt.getValue(1) != null) {
                        statSouscriptionVo.setMnt_Sousc_att(StrHandler.formatmnt(Math.abs(Double.valueOf(listPrdAtt.getValue(1).toString()))));
                    } else {
                        statSouscriptionVo.setMnt_Sousc_att("0");
                    }


                    /*soucsriptions validées*/
                    listeDemande = 
                            statDomPlacementDAO.getNbrSouscParEtat(statPlacement.getStructure(), 
                                                                   produitPlacement.getCodPrdPlc(), 
                                                                   Long.valueOf("298"), 
                                                                   statPlacement.getDateJournee());
                    listPrdAtt = (ListOrderedMap)listeDemande.get(0);
                    statSouscriptionVo.setNbr_Sousc_Val(Long.valueOf(listPrdAtt.getValue(0).toString()));
                    nbrGlob = 
                            nbrGlob + Long.valueOf(listPrdAtt.getValue(0).toString());
                    if (statSouscriptionVo.getNbr_Sousc_Val().longValue() != 
                        0) {
                        remplie = "true";
                    }
                    if (listPrdAtt.getValue(1) != null) {
                        mntGlob = 
                                mntGlob + Double.valueOf(listPrdAtt.getValue(1).toString());
                        statSouscriptionVo.setMnt_Sousc_Val(StrHandler.formatmnt(Math.abs(Double.valueOf(listPrdAtt.getValue(1).toString()))));
                    } else {
                        statSouscriptionVo.setMnt_Sousc_Val("0");
                    }


                    /*soucsriptions rejetées*/
                    listeDemande = 
                            statDomPlacementDAO.getListContratPlacParEtat(statPlacement.getStructure(), 
                                                                          produitPlacement.getCodPrdPlc(), 
                                                                          statPlacement.getDateJournee(), 
                                                                          "R");
                    listPrdAtt = (ListOrderedMap)listeDemande.get(0);
                    statSouscriptionVo.setNbr_Sousc_Rej(Long.valueOf(listPrdAtt.getValue(0).toString()));
                    if (statSouscriptionVo.getNbr_Sousc_Rej().longValue() != 
                        0) {
                        remplie = "true";
                    }
                    if (listPrdAtt.getValue(1) != null) {
                        statSouscriptionVo.setMnt_Sousc_Rej(StrHandler.formatmnt(Math.abs(Double.valueOf(listPrdAtt.getValue(1).toString()))));
                    } else {
                        statSouscriptionVo.setMnt_Sousc_Rej("0");
                    }
                    if (remplie.equalsIgnoreCase("true")) {
                        listtemp.add(statSouscriptionVo);
                    }


                }
                placementVo.setList(listtemp);
                placementVo.setNbrGlobSousc(nbrGlob);
                placementVo.setMntGlobalSousc(mntGlob);

            }

        } catch (Exception e) {
            com.oxia.fwk.core.Error erreur = new com.oxia.fwk.core.Error();
            erreur.setCode("Technique");
            erreur.setDescription("GetStatSouscPlacTrt " + e.getMessage());
            placementVo.addError(erreur);
            throw new Exception(e);
        }
        return placementVo;
    }

    public void genCroText(ValueObject vo) {

    }
}
