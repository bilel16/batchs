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
import com.bna.smile.model.clotureDomaine.model.StatAvancesVo;
import com.bna.smile.model.clotureDomaine.model.StatPlacement;
import com.oxia.fwk.context.Context;
import com.oxia.fwk.core.ICriteria;
import com.oxia.fwk.core.IExpression;
import com.oxia.fwk.core.ISearchEngine;
import com.oxia.fwk.core.IValueObject;
import com.oxia.fwk.core.ValueObject;

public class GetStatAvancPlacTrt extends Traitement {
    public GetStatAvancPlacTrt() {
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
            Long nbrGlobA = new Long(0);
            Double mntGlobA = new Double(0);
            Long nbrGlobR = new Long(0);
            Double mntGlobR = new Double(0);
            List l = searchEngine.findAll(ProduitPlacement.class);

            if (l != null && l.size() > 0) {
                for (Iterator it = l.iterator(); it.hasNext(); ) {
                    ListOrderedMap listPrdAtt = null;
                    remplie = "false";
                    StatAvancesVo statAvancesVo = new StatAvancesVo();
                    ProduitPlacement produitPlacement = 
                        (ProduitPlacement)it.next();
                    statAvancesVo.setCod_prd_plc(produitPlacement.getCodPrdPlc());
                    statAvancesVo.setLib_prd_plc(produitPlacement.getLibPrdPlc());

                    /*Avances placement en attente de validation*/
                    listeDemande = 
                            statDomPlacementDAO.getNbrAvancPlacParEtat(statPlacement.getStructure(), 
                                                                       produitPlacement.getCodPrdPlc(), 
                                                                       "A", 
                                                                       statPlacement.getDateJournee());
                    listPrdAtt = (ListOrderedMap)listeDemande.get(0);
                    statAvancesVo.setNbr_Avanc_att(Long.valueOf(listPrdAtt.getValue(0).toString()));
                    if (statAvancesVo.getNbr_Avanc_att().longValue() != 0) {
                        remplie = "true";
                    }
                    if (listPrdAtt.getValue(1) != null) {
                        statAvancesVo.setMnt_Avanc_att(StrHandler.formatmnt(Math.abs(Double.valueOf(listPrdAtt.getValue(1).toString()))));
                    } else {
                        statAvancesVo.setMnt_Avanc_att("0");
                    }


                    /*Avances placement validées*/
                    listeDemande = 
                            statDomPlacementDAO.getNbrAvancParEtat(statPlacement.getStructure(), 
                                                                   produitPlacement.getCodPrdPlc(), 
                                                                   Long.valueOf("300"), 
                                                                   statPlacement.getDateJournee());
                    listPrdAtt = (ListOrderedMap)listeDemande.get(0);
                    statAvancesVo.setNbr_Avanc_Val(Long.valueOf(listPrdAtt.getValue(0).toString()));
                    nbrGlobA = 
                            nbrGlobA + Long.valueOf(listPrdAtt.getValue(0).toString());
                    if (statAvancesVo.getNbr_Avanc_Val().longValue() != 0) {
                        remplie = "true";
                    }
                    if (listPrdAtt.getValue(1) != null) {
                        statAvancesVo.setMnt_Avanc_Val(StrHandler.formatmnt(Math.abs(Double.valueOf(listPrdAtt.getValue(1).toString()))));
                        mntGlobA = 
                                mntGlobA + Double.valueOf(listPrdAtt.getValue(1).toString());
                    } else {
                        statAvancesVo.setMnt_Avanc_Val("0");
                    }


                    /*Avances placement rejetées*/
                    listeDemande = 
                            statDomPlacementDAO.getNbrAvancPlacRej(statPlacement.getStructure(), 
                                                                   produitPlacement.getCodPrdPlc(), 
                                                                   statPlacement.getDateJournee());
                    listPrdAtt = (ListOrderedMap)listeDemande.get(0);
                    statAvancesVo.setNbr_Avanc_Rej(Long.valueOf(listPrdAtt.getValue(0).toString()));
                    if (statAvancesVo.getNbr_Avanc_Rej().longValue() != 0) {
                        remplie = "true";
                    }
                    if (listPrdAtt.getValue(1) != null) {
                        statAvancesVo.setMnt_Avanc_Rej(StrHandler.formatmnt(Math.abs(Double.valueOf(listPrdAtt.getValue(1).toString()))));
                    } else {
                        statAvancesVo.setMnt_Avanc_Rej("0");
                    }


                    /*Avances placement rembousées*/
                    listeDemande = 
                            statDomPlacementDAO.getNbrAvancParEtat(statPlacement.getStructure(), 
                                                                   produitPlacement.getCodPrdPlc(), 
                                                                   Long.valueOf("301"), 
                                                                   statPlacement.getDateJournee());
                    listPrdAtt = (ListOrderedMap)listeDemande.get(0);
                    statAvancesVo.setNbr_Avanc_Remb(Long.valueOf(listPrdAtt.getValue(0).toString()));
                    nbrGlobR = 
                            nbrGlobR + Long.valueOf(listPrdAtt.getValue(0).toString());
                    if (statAvancesVo.getNbr_Avanc_Remb().longValue() != 0) {
                        remplie = "true";
                    }
                    if (listPrdAtt.getValue(1) != null) {
                        statAvancesVo.setMnt_Avanc_Remb(StrHandler.formatmnt(Math.abs(Double.valueOf(listPrdAtt.getValue(1).toString()))));
                        mntGlobR = 
                                mntGlobR + Double.valueOf(listPrdAtt.getValue(1).toString());

                    } else {
                        statAvancesVo.setMnt_Avanc_Remb("0");
                    }
                    if (remplie.equalsIgnoreCase("true")) {
                        listtemp.add(statAvancesVo);
                    }


                }
                placementVo.setList(listtemp);
                placementVo.setNbrGlobAvanc(nbrGlobA);
                placementVo.setNbrGlobRemb(nbrGlobR);
                placementVo.setMntGlobalAvanc(mntGlobA);
                placementVo.setMntGlobalRemb(mntGlobR);
            }

        } catch (Exception e) {
            com.oxia.fwk.core.Error erreur = new com.oxia.fwk.core.Error();
            erreur.setCode("Technique");
            erreur.setDescription("GetStatAvancPlacTrt " + e.getMessage());
            placementVo.addError(erreur);
            throw new Exception(e);
        }
        return placementVo;
    }

    public void genCroText(ValueObject vo) {

    }
}
