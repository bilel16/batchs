package com.bna.smile.model.clotureDomaine.traitement;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.collections.map.ListOrderedMap;

import com.bna.commun.model.JourneeStructureDomaine;
import com.bna.commun.model.ProduitPlacement;
import com.bna.commun.model.Structure;
import com.bna.commun.traitements.Traitement;
import com.bna.commun.util.ContextHandler;
import com.bna.commun.util.StrHandler;
import com.bna.smile.model.clotureDomaine.dao.StatDomPlacementDAO;
import com.bna.smile.model.clotureDomaine.model.StatPlacement;
import com.bna.smile.model.clotureDomaine.model.StatPlacementObjectVO;
import com.bna.smile.model.constant.Constants;
import com.bna.smile.model.domainecommun.model.Listes;
import com.bna.smile.model.domainecommun.service.CRUDservice;
import com.bna.smile.model.domaineplacement.model.ParamInteretServi;
import com.bna.smile.model.domaineplacement.traitement.AbonnementPlacementAgenceTrt;
import com.oxia.fwk.context.Context;
import com.oxia.fwk.core.ICriteria;
import com.oxia.fwk.core.IExpression;
import com.oxia.fwk.core.ISearchEngine;
import com.oxia.fwk.core.IValueObject;
import com.oxia.fwk.core.ValueObject;

public class GetDonnePlacementTrt extends Traitement {
    public GetDonnePlacementTrt() {
    }

    public IValueObject perform(IValueObject vo) throws SQLException, 
                                                        Exception {

        Context context = ContextHandler.getContext();
        StatPlacement statPlacement = (StatPlacement)vo;
        Listes listeretour = new Listes();
        List listtemp = new ArrayList();
        try {
            ISearchEngine searchEngine = 
                (ISearchEngine)Context.getInstance().getSpringContext().getBean("searchEngine");
            ICriteria criteria = searchEngine.createCriteria();
            IExpression expression = searchEngine.createExpression();
            StatDomPlacementDAO statDomPlacementDAO = 
                (StatDomPlacementDAO)context.getBean("statDomPlacementDAO");
            String remplie;
            List listeDemande = new ArrayList();
            JourneeStructureDomaine journeeStructureDomaine = 
                this.getJourneeStructureDomaine(statPlacement.getJourneeStructureDomaineId());

            if (this.checkClotureJournee()) {

                if (((journeeStructureDomaine.getCodStatJsd().equals(Constants.ETAT_JSDOM_OUV)) || 
                    (journeeStructureDomaine.getCodStatJsd().equals(Constants.ETAT_JSDOM_COURCLO))) &&
                    (journeeStructureDomaine.getJourneeStructureDomaineId().get_codStrcStrc()!=Long.valueOf("900"))){
                    /*mise à jour de l'etat du domaine*/
                    
                    journeeStructureDomaine.setCodStatJsd(Constants.ETAT_JSDOM_COURCLO);
                    CRUDservice crudService = 
                        (CRUDservice)context.getBean("crudservice");
                    crudService.update(journeeStructureDomaine);
                    /*batch abonnement*/
                    Structure structure=new Structure();
                    structure.setCodStrcStrc(statPlacement.getStructure());
                    ParamInteretServi paramInteretServi =new ParamInteretServi();
                    paramInteretServi.setStructure(structure);
                    paramInteretServi.setDateComptableAgence(statPlacement.getDateJournee());
                    AbonnementPlacementAgenceTrt abonnementPlacementAgenceTrt=new AbonnementPlacementAgenceTrt();
                    abonnementPlacementAgenceTrt.exec(paramInteretServi);
                    

                    /*recherche des données demandes placement*/
                    List l = searchEngine.findAll(ProduitPlacement.class);

                    if (l != null && l.size() > 0) {
                        for (Iterator it = l.iterator(); it.hasNext(); ) {
                            remplie = "false";
                            ListOrderedMap listPrdAtt = null;
                            StatPlacementObjectVO statPlacementObjectVO = 
                                new StatPlacementObjectVO();

                            ProduitPlacement produitPlacement = 
                                (ProduitPlacement)it.next();
                            statPlacementObjectVO.setCod_prd_plc(produitPlacement.getCodPrdPlc());
                            statPlacementObjectVO.setLib_prd_plc(produitPlacement.getLibPrdPlc());
                            if (!statPlacement.getStructure().equals(Long.valueOf("900"))) {
                                /*listes demandes en attente*/
                                listeDemande = 
                                        statDomPlacementDAO.getNbrDemPlacParEtat(statPlacement.getStructure(), 
                                                                                 produitPlacement.getCodPrdPlc(), 
                                                                                 "S", 
                                                                                 statPlacement.getDateJournee());

                                listPrdAtt = 
                                        (ListOrderedMap)listeDemande.get(0);
                                statPlacementObjectVO.setNbr_dem_att(Long.valueOf(listPrdAtt.getValue(0).toString()));
                                if (statPlacementObjectVO.getNbr_dem_att().longValue() != 
                                    0) {
                                    remplie = "true";
                                }
                                if (listPrdAtt.getValue(1) != null) {
                                    statPlacementObjectVO.setMnt_dem_att(StrHandler.formatmnt(Math.abs(Double.valueOf(listPrdAtt.getValue(1).toString()))));
                                } else {
                                    statPlacementObjectVO.setMnt_dem_att("0");
                                }

                                /*listes demandes validées avec CG*/
                                listeDemande = 
                                        statDomPlacementDAO.getNbrDemPlacParEtatCondGen(statPlacement.getStructure(), 
                                                                                        produitPlacement.getCodPrdPlc(), 
                                                                                        "V", 
                                                                                        statPlacement.getDateJournee());

                                listPrdAtt = 
                                        (ListOrderedMap)listeDemande.get(0);
                                statPlacementObjectVO.setNbr_dem_VCG(Long.valueOf(listPrdAtt.getValue(0).toString()));
                                if (statPlacementObjectVO.getNbr_dem_VCG().longValue() != 
                                    0) {
                                    remplie = "true";
                                }
                                if (listPrdAtt.getValue(1) != null) {
                                    statPlacementObjectVO.setMnt_dem_VCG(StrHandler.formatmnt(Math.abs(Double.valueOf(listPrdAtt.getValue(1).toString()))));
                                } else {
                                    statPlacementObjectVO.setMnt_dem_VCG("0");
                                }


                                /*listes demandes validées avec Cond Préf*/
                                listeDemande = 
                                        statDomPlacementDAO.getNbrDemPlacParEtatCondPref(statPlacement.getStructure(), 
                                                                                         produitPlacement.getCodPrdPlc(), 
                                                                                         "SV", 
                                                                                         statPlacement.getDateJournee());

                                listPrdAtt = 
                                        (ListOrderedMap)listeDemande.get(0);
                                statPlacementObjectVO.setNbr_dem_VCP(Long.valueOf(listPrdAtt.getValue(0).toString()));
                                if (statPlacementObjectVO.getNbr_dem_VCP().longValue() != 
                                    0) {
                                    remplie = "true";
                                }
                                if (listPrdAtt.getValue(1) != null) {
                                    statPlacementObjectVO.setMnt_dem_VCP(StrHandler.formatmnt(Math.abs(Double.valueOf(listPrdAtt.getValue(1).toString()))));
                                } else {
                                    statPlacementObjectVO.setMnt_dem_VCP("0");
                                }


                                /*listes des demandes rejetée par le client */
                                listeDemande = 
                                        statDomPlacementDAO.getNbrDemPlacPrefRejParStrc(statPlacement.getStructure(), 
                                                                                        produitPlacement.getCodPrdPlc(), 
                                                                                        "R", 
                                                                                        statPlacement.getDateJournee(), 
                                                                                        statPlacement.getStructure());

                                listPrdAtt = 
                                        (ListOrderedMap)listeDemande.get(0);
                                statPlacementObjectVO.setNbr_rej_AG(Long.valueOf(listPrdAtt.getValue(0).toString()));
                                if (statPlacementObjectVO.getNbr_rej_AG().longValue() != 
                                    0) {
                                    remplie = "true";
                                }
                                if (listPrdAtt.getValue(1) != null) {
                                    statPlacementObjectVO.setMnt_rej_AG(StrHandler.formatmnt(Math.abs(Double.valueOf(listPrdAtt.getValue(1).toString()))));
                                } else {
                                    statPlacementObjectVO.setMnt_rej_AG("0");
                                }

                                /*listes des demandes acceptée par le client notifiée*/
                                listeDemande = 
                                        statDomPlacementDAO.getNbrDemPlacParEtatCondPref(statPlacement.getStructure(), 
                                                                                         produitPlacement.getCodPrdPlc(), 
                                                                                         "N", 
                                                                                         statPlacement.getDateJournee());

                                listPrdAtt = 
                                        (ListOrderedMap)listeDemande.get(0);
                                statPlacementObjectVO.setNbr_dem_Not(Long.valueOf(listPrdAtt.getValue(0).toString()));
                                if (statPlacementObjectVO.getNbr_dem_Not().longValue() != 
                                    0) {
                                    remplie = "true";
                                }
                                if (listPrdAtt.getValue(1) != null) {
                                    statPlacementObjectVO.setMnt_dem_Not(StrHandler.formatmnt(Math.abs(Double.valueOf(listPrdAtt.getValue(1).toString()))));
                                } else {
                                    statPlacementObjectVO.setMnt_dem_Not("0");
                                }


                                /*validée par dir tres*/
                                listeDemande = 
                                        statDomPlacementDAO.getNbrDemPlacPrefRejParStrc(statPlacement.getStructure(), 
                                                                                        produitPlacement.getCodPrdPlc(), 
                                                                                        "V", 
                                                                                        statPlacement.getDateJournee(), 
                                                                                        new Long(900));

                                listPrdAtt = 
                                        (ListOrderedMap)listeDemande.get(0);
                                statPlacementObjectVO.setNbr_Trait_TR(Long.valueOf(listPrdAtt.getValue(0).toString()));
                                if (statPlacementObjectVO.getNbr_Trait_TR().longValue() != 
                                    0) {
                                    remplie = "true";
                                }
                                if (remplie.equalsIgnoreCase("true")) {
                                    listtemp.add(statPlacementObjectVO);
                                }


                            } else {
                                /*listes demandes en attente d'eude par la TRES*/
                                listeDemande = 
                                        statDomPlacementDAO.getDemTresorAtt(produitPlacement.getCodPrdPlc(), 
                                                                            "SV", 
                                                                            "S");

                                listPrdAtt = 
                                        (ListOrderedMap)listeDemande.get(0);
                                statPlacementObjectVO.setNbr_dem_att(Long.valueOf(listPrdAtt.getValue(0).toString()));
                                if (statPlacementObjectVO.getNbr_dem_att().longValue() != 
                                    0) {
                                    remplie = "true";
                                }
                                if (listPrdAtt.getValue(1) != null) {
                                    statPlacementObjectVO.setMnt_dem_att(StrHandler.formatmnt(Math.abs(Double.valueOf(listPrdAtt.getValue(1).toString()))));
                                } else {
                                    statPlacementObjectVO.setMnt_dem_att("0");
                                }
                                /*listes renouvellement en attente d'eude par la TRES*/
                                listeDemande = 
                                        statDomPlacementDAO.getDemTresorAtt(produitPlacement.getCodPrdPlc(), 
                                                                            "SV", 
                                                                            "R");

                                listPrdAtt = 
                                        (ListOrderedMap)listeDemande.get(0);
                                statPlacementObjectVO.setNbr_ren_att(Long.valueOf(listPrdAtt.getValue(0).toString()));
                                if (statPlacementObjectVO.getNbr_ren_att().longValue() != 
                                    0) {
                                    remplie = "true";
                                }
                                if (listPrdAtt.getValue(1) != null) {
                                    statPlacementObjectVO.setMnt_ren_att(StrHandler.formatmnt(Math.abs(Double.valueOf(listPrdAtt.getValue(1).toString()))));
                                } else {
                                    statPlacementObjectVO.setMnt_ren_att("0");
                                }

                                /*listes demandes etudiées par tresorerie*/
                                listeDemande = 
                                        statDomPlacementDAO.getDemTresor(produitPlacement.getCodPrdPlc(), 
                                                                         "E", 
                                                                         statPlacement.getDateJournee(), 
                                                                         "S");

                                listPrdAtt = 
                                        (ListOrderedMap)listeDemande.get(0);
                                statPlacementObjectVO.setNbr_EnEtud_TR(Long.valueOf(listPrdAtt.getValue(0).toString()));
                                if (statPlacementObjectVO.getNbr_EnEtud_TR().longValue() != 
                                    0) {
                                    remplie = "true";
                                }
                                if (listPrdAtt.getValue(1) != null) {
                                    statPlacementObjectVO.setMnt_EnEtud_TR(StrHandler.formatmnt(Math.abs(Double.valueOf(listPrdAtt.getValue(1).toString()))));
                                } else {
                                    statPlacementObjectVO.setMnt_EnEtud_TR("0");
                                }
                                /*listes ren etudiées par tresorerie*/
                                listeDemande = 
                                        statDomPlacementDAO.getDemTresor(produitPlacement.getCodPrdPlc(), 
                                                                         "E", 
                                                                         statPlacement.getDateJournee(), 
                                                                         "R");

                                listPrdAtt = 
                                        (ListOrderedMap)listeDemande.get(0);
                                statPlacementObjectVO.setNbr_ren_etu(Long.valueOf(listPrdAtt.getValue(0).toString()));
                                if (statPlacementObjectVO.getNbr_ren_etu().longValue() != 
                                    0) {
                                    remplie = "true";
                                }
                                if (listPrdAtt.getValue(1) != null) {
                                    statPlacementObjectVO.setMnt_ren_etu(StrHandler.formatmnt(Math.abs(Double.valueOf(listPrdAtt.getValue(1).toString()))));
                                } else {
                                    statPlacementObjectVO.setMnt_ren_etu("0");
                                }

                                /*listes demandes etudiées par tresorerie et validées*/
                                listeDemande = 
                                        statDomPlacementDAO.getDemTresor(produitPlacement.getCodPrdPlc(), 
                                                                         "EV", 
                                                                         statPlacement.getDateJournee(), 
                                                                         "S");

                                listPrdAtt = 
                                        (ListOrderedMap)listeDemande.get(0);
                                statPlacementObjectVO.setNbr_EtudVal_TR(Long.valueOf(listPrdAtt.getValue(0).toString()));
                                if (statPlacementObjectVO.getNbr_EtudVal_TR().longValue() != 
                                    0) {
                                    remplie = "true";
                                }
                                if (listPrdAtt.getValue(1) != null) {
                                    statPlacementObjectVO.setMnt_EtudVal_TR(StrHandler.formatmnt(Math.abs(Double.valueOf(listPrdAtt.getValue(1).toString()))));
                                } else {
                                    statPlacementObjectVO.setMnt_EtudVal_TR("0");
                                }
                                /*listes ren etudiées par tresorerie et validées*/
                                listeDemande = 
                                        statDomPlacementDAO.getDemTresor(produitPlacement.getCodPrdPlc(), 
                                                                         "EV", 
                                                                         statPlacement.getDateJournee(), 
                                                                         "R");

                                listPrdAtt = 
                                        (ListOrderedMap)listeDemande.get(0);
                                statPlacementObjectVO.setNbr_ren_etuVal(Long.valueOf(listPrdAtt.getValue(0).toString()));
                                if (statPlacementObjectVO.getNbr_ren_etuVal().longValue() != 
                                    0) {
                                    remplie = "true";
                                }
                                if (listPrdAtt.getValue(1) != null) {
                                    statPlacementObjectVO.setMnt_ren_etuVal(StrHandler.formatmnt(Math.abs(Double.valueOf(listPrdAtt.getValue(1).toString()))));
                                } else {
                                    statPlacementObjectVO.setMnt_ren_etuVal("0");
                                }

                                /*listes demandes notifiées par les agences*/
                                listeDemande = 
                                        statDomPlacementDAO.getDemTresor(produitPlacement.getCodPrdPlc(), 
                                                                         "N", 
                                                                         statPlacement.getDateJournee(), 
                                                                         "S");

                                listPrdAtt = 
                                        (ListOrderedMap)listeDemande.get(0);
                                statPlacementObjectVO.setNbr_dem_Not(Long.valueOf(listPrdAtt.getValue(0).toString()));
                                if (statPlacementObjectVO.getNbr_dem_Not().longValue() != 
                                    0) {
                                    remplie = "true";
                                }
                                if (listPrdAtt.getValue(1) != null) {
                                    statPlacementObjectVO.setMnt_dem_Not(StrHandler.formatmnt(Math.abs(Double.valueOf(listPrdAtt.getValue(1).toString()))));
                                } else {
                                    statPlacementObjectVO.setMnt_dem_Not("0");
                                }
                                /*listes ren notifiées par les agences*/
                                listeDemande = 
                                        statDomPlacementDAO.getDemTresor(produitPlacement.getCodPrdPlc(), 
                                                                         "N", 
                                                                         statPlacement.getDateJournee(), 
                                                                         "R");

                                listPrdAtt = 
                                        (ListOrderedMap)listeDemande.get(0);
                                statPlacementObjectVO.setNbr_ren_Not(Long.valueOf(listPrdAtt.getValue(0).toString()));
                                if (statPlacementObjectVO.getNbr_ren_Not().longValue() != 
                                    0) {
                                    remplie = "true";
                                }
                                if (listPrdAtt.getValue(1) != null) {
                                    statPlacementObjectVO.setMnt_ren_Not(StrHandler.formatmnt(Math.abs(Double.valueOf(listPrdAtt.getValue(1).toString()))));
                                } else {
                                    statPlacementObjectVO.setMnt_ren_Not("0");
                                }

                                /*listes demandes validées par tresorerie*/
                                listeDemande = 
                                        statDomPlacementDAO.getDemValideTresor(produitPlacement.getCodPrdPlc(), 
                                                                               "V", 
                                                                               statPlacement.getDateJournee(), 
                                                                               "S");

                                listPrdAtt = 
                                        (ListOrderedMap)listeDemande.get(0);
                                statPlacementObjectVO.setNbr_dem_VCP(Long.valueOf(listPrdAtt.getValue(0).toString()));
                                if (statPlacementObjectVO.getNbr_dem_VCP().longValue() != 
                                    0) {
                                    remplie = "true";
                                }
                                if (listPrdAtt.getValue(1) != null) {
                                    statPlacementObjectVO.setMnt_dem_VCP(StrHandler.formatmnt(Math.abs(Double.valueOf(listPrdAtt.getValue(1).toString()))));
                                } else {
                                    statPlacementObjectVO.setMnt_dem_VCP("0");
                                }
                                /*listes ren validées par tresorerie*/
                                listeDemande = 
                                        statDomPlacementDAO.getDemValideTresor(produitPlacement.getCodPrdPlc(), 
                                                                               "V", 
                                                                               statPlacement.getDateJournee(), 
                                                                               "R");

                                listPrdAtt = 
                                        (ListOrderedMap)listeDemande.get(0);
                                statPlacementObjectVO.setNbr_ren_Val(Long.valueOf(listPrdAtt.getValue(0).toString()));
                                if (statPlacementObjectVO.getNbr_ren_Val().longValue() != 
                                    0) {
                                    remplie = "true";
                                }
                                if (listPrdAtt.getValue(1) != null) {
                                    statPlacementObjectVO.setMnt_ren_Val(StrHandler.formatmnt(Math.abs(Double.valueOf(listPrdAtt.getValue(1).toString()))));
                                } else {
                                    statPlacementObjectVO.setMnt_ren_Val("0");
                                }
                                if (remplie.equalsIgnoreCase("true")) {
                                    listtemp.add(statPlacementObjectVO);
                                }
                            }
                        }
                        listeretour.setList(listtemp);


                    }


                } else if (journeeStructureDomaine.getCodStatJsd().equals(Constants.ETAT_JSDOM_SCLO)) {
                    com.oxia.fwk.core.Error erreur = 
                        new com.oxia.fwk.core.Error();
                    StringBuffer text = 
                        new StringBuffer("la session n'est pas encore ouverte...");
                    erreur.setCode("100");
                    erreur.setDescription(text.toString());
                    erreur.setKey("GetDonnePlacementTrt");
                    listeretour.addError(erreur);

                } else {

                    com.oxia.fwk.core.Error erreur = 
                        new com.oxia.fwk.core.Error();
                    StringBuffer text = 
                        new StringBuffer("Le domaine est déja cloturée...");
                    erreur.setCode("100");
                    erreur.setDescription(text.toString());
                    erreur.setKey("GetDonnePlacementTrt");
                    listeretour.addError(erreur);


                }


            } else {
                com.oxia.fwk.core.Error erreur = new com.oxia.fwk.core.Error();
                StringBuffer text = 
                    new StringBuffer("La journée est déja clôturée...");
                erreur.setCode("100");
                erreur.setDescription(text.toString());
                erreur.setKey("GetDonnePlacementTrt");
                listeretour.addError(erreur);

            }

            return listeretour;

        } catch (Exception e) {
            com.oxia.fwk.core.Error erreur = new com.oxia.fwk.core.Error();
            erreur.setCode("Technique");
            erreur.setDescription("GetDonnePlacementTrt " + e.getMessage());
            statPlacement.addError(erreur);
            throw new Exception(e);
        }

    }

    public void genCroText(ValueObject vo) {

    }
}
