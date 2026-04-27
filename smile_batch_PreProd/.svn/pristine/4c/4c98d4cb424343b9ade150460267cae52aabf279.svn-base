package com.bna.smile.model.clotureDomaine.dao;

import java.util.Date;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;

import com.bna.commun.util.DateHandler;

public class StatDomPlacementDAO {
    public StatDomPlacementDAO() {
    }

    protected String sqlQuery;
    protected JdbcTemplate jt;
    protected DataSource dataSource;

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public void setSqlQuery(String sqlQuery) {
        this.sqlQuery = sqlQuery;
    }


    public List getNbrDemPlacParEtat(Long codeStructure, Long produit, 
                                     String etat, Date dateOuverture) {
        jt = new JdbcTemplate(dataSource);
        String requete = 
            new String("select count(*),SUM(MONT_PLA_DEMD)  from DEMANDE_DECISION where COD_NDEM_DEMD='S'" + 
                       " AND cod_strc_strc = " + codeStructure + 
                       " and COD_ETAT_DEMD = '" + etat + 
                       "'  and COD_PRD_PLC	 = " + produit + 
                       " AND to_char(DAT_CRE_DEMD,'DD/MM/YYYY') = '" + 
                       DateHandler.dateToStr(dateOuverture) + "'");

        List listDem = (List)jt.queryForList(requete);
        return listDem;
    }

    public List getNbrSouscEnAttente(Long codeStructure, Long produit, 
                                     String etat, Date dateOuverture) {
        jt = new JdbcTemplate(dataSource);
        String requete = 
            new String("select count(*),SUM(MONT_PLA_DEMD)  from DEMANDE_DECISION where COD_NDEM_DEMD='S'" + 
                       " AND cod_strc_strc = " + codeStructure + 
                       " and COD_ETAT_DEMD = '" + etat + 
                       "'  and COD_PRD_PLC  = " + produit + 
                       " AND to_char(DAT_VLD_DEMD,'DD/MM/YYYY') = '" + 
                       DateHandler.dateToStr(dateOuverture) + "'");

        List listDem = (List)jt.queryForList(requete);
        return listDem;
    }

    public List getNbrDemPlacParEtatCondGen(Long codeStructure, Long produit, 
                                            String etat, Date dateOuverture) {
        jt = new JdbcTemplate(dataSource);
        String requete = 
            new String("select count(*),SUM(MONT_PLA_DEMD)  from DEMANDE_DECISION where COD_NDEM_DEMD='S'" + 
                       " and cod_strc_strc = " + codeStructure + 
                       " and COD_ETAT_DEMD = '" + etat + 
                       "'  and COD_PRD_PLC      = " + produit + 
                       " AND COD_FAV_DEMD='G'" + 
                       " AND to_char(DAT_VLD_DEMD,'DD/MM/YYYY') = '" + 
                       DateHandler.dateToStr(dateOuverture) + "'");

        List listDem = (List)jt.queryForList(requete);
        return listDem;
    }

    public List getNbrDemPlacParEtatCondPref(Long codeStructure, Long produit, 
                                             String etat, Date dateOuverture) {
        jt = new JdbcTemplate(dataSource);
        String requete = 
            new String("select count(*),SUM(MONT_PLA_DEMD)  from DEMANDE_DECISION where COD_NDEM_DEMD='S'" + 
                       " and cod_strc_strc = " + codeStructure + 
                       " and COD_ETAT_DEMD = '" + etat + 
                       "'  and COD_PRD_PLC      = " + produit + 
                       " AND COD_FAV_DEMD!='G'" + 
                       " AND to_char(DAT_VLD_DEMD,'DD/MM/YYYY') = '" + 
                       DateHandler.dateToStr(dateOuverture) + "'");

        List listDem = (List)jt.queryForList(requete);
        return listDem;
    }

    public List getNbrDemPlacPrefRejParStrc(Long codeStructure, Long produit, 
                                            String etat, Date dateOuverture, 
                                            Long strcvalid) {
        jt = new JdbcTemplate(dataSource);
        String requete = 
            new String("select count(*),SUM(MONT_PLA_DEMD)  from DEMANDE_DECISION where COD_NDEM_DEMD='S'" + 
                       " and cod_strc_strc = " + codeStructure + 
                       " and COD_ETAT_DEMD = '" + etat + 
                       "'  and COD_PRD_PLC      = " + produit + 
                       " and COD_STRV_STRC=" + strcvalid + 
                       " AND COD_FAV_DEMD!='G'" + 
                       " AND to_char(DAT_VLD_DEMD,'DD/MM/YYYY') = '" + 
                       DateHandler.dateToStr(dateOuverture) + "'");

        List listDem = (List)jt.queryForList(requete);
        return listDem;
    }

    public List getNbrSouscParEtat(Long codeStructure, Long produit, 
                                   Long codeOper, Date dateOuverture) {
        jt = new JdbcTemplate(dataSource);
        String requete = 
            new String("select count(*),SUM(MONT_DIN_OMP)  from OPERATION_MOY_PAY where " + 
                       " to_char(DAT_OPER_OMP,'DD/MM/YYYY') = '" + 
                       DateHandler.dateToStr(dateOuverture) + "'" + 
                       " AND COD_PRD_OMP=" + produit + " AND COD_OPER_OPER=" + 
                       codeOper + " AND COD_STRC_STRC=" + codeStructure);

        List listDem = (List)jt.queryForList(requete);
        return listDem;
    }

    public List getNbrSouscRejParStrc(Long codeStructure, Long produit, 
                                      String etat, Date dateOuverture, 
                                      Long strcvalid) {
        jt = new JdbcTemplate(dataSource);
        String requete = 
            new String("select count(*),SUM(MONT_PLA_DEMD)  from DEMANDE_DECISION where COD_NDEM_DEMD='S'" + 
                       " and cod_strc_strc = " + codeStructure + 
                       " and COD_ETAT_DEMD = '" + etat + 
                       "'  and COD_PRD_PLC      = " + produit + 
                       " and COD_STRV_STRC=" + strcvalid + 
                       " AND to_char(DAT_REJ_DEMD	,'DD/MM/YYYY') = '" + 
                       DateHandler.dateToStr(dateOuverture) + "'" + 
                       " and DAT_VLD_DEMD!=NULL");

        List listDem = (List)jt.queryForList(requete);
        return listDem;
    }

    public List getNbrAvancPlacParEtat(Long codeStructure, Long produit, 
                                       String etat, Date dateOuverture) {
        jt = new JdbcTemplate(dataSource);
        String requete = 
            new String("select count(*),SUM(MONT_ARL_ARL)  from AVANC_REMB_LIQUID where COD_ETAT_ARL='" + 
                       etat + "'" + "and COD_TOPR_ARL='AVAN'" + 
                       " AND to_char(DAT_ARL_ARL,'DD/MM/YYYY') = '" + 
                       DateHandler.dateToStr(dateOuverture) + "'" + 
                       "and AVANC_REMB_LIQUID.NUM_SEQ_CPLA	in" + 
                       "(select NUM_SEQ_CPLA from CONTRAT_PLACEMENT where COD_STRC_CCPT = " + 
                       codeStructure + "and COD_PRD_PLC  = " + produit + ")");


        List listDem = (List)jt.queryForList(requete);
        return listDem;
    }

    public List getNbrAvancParEtat(Long codeStructure, Long produit, 
                                   Long codeOper, Date dateOuverture) {
        jt = new JdbcTemplate(dataSource);
        String requete = 
            new String("select count(*),SUM(MONT_DIN_OMP)  from OPERATION_MOY_PAY where " + 
                       " to_char(DAT_OPER_OMP,'DD/MM/YYYY') = '" + 
                       DateHandler.dateToStr(dateOuverture) + "'" + 
                       " AND COD_PRD_OMP=" + produit + " AND COD_OPER_OPER=" + 
                       codeOper + " AND COD_STRC_STRC=" + codeStructure);

        List listDem = (List)jt.queryForList(requete);
        return listDem;
    }

    public List getNbrAvancPlacRej(Long codeStructure, Long produit,

        Date dateOuverture) {
        jt = new JdbcTemplate(dataSource);
        String requete = 
            new String("select count(*),SUM(MONT_ARL_ARL)  from AVANC_REMB_LIQUID where COD_ETAT_ARL='R'" + 
                       " and COD_TOPR_ARL='AVAN'" + 
                       " AND to_char(DAT_ARL_ARL,'DD/MM/YYYY') = '" + 
                       DateHandler.dateToStr(dateOuverture) + "'" + 
                       " AND to_char(DAT_REEL_ARL,'DD/MM/YYYY') is NULL" + 
                       " and AVANC_REMB_LIQUID.NUM_SEQ_CPLA in" + 
                       "(select NUM_SEQ_CPLA from CONTRAT_PLACEMENT where COD_STRC_CCPT = " + 
                       codeStructure + "and COD_PRD_PLC  = " + produit + ")");


        List listDem = (List)jt.queryForList(requete);
        return listDem;
    }


    public List getLiquiAVECHEParProduitType(Long codeStructure, 
                                             String etatAvance, 
                                             Date dateOuverture, Long produit, 
                                             String typeAvance) {
        jt = new JdbcTemplate(dataSource);
        String requete = 
            new String("select count(*),SUM(MONT_ARL_ARL)  from AVANC_REMB_LIQUID where COD_TOPR_ARL='LIQA'" + 
                       " and COD_ETAT_ARL='" + etatAvance + "'" + 
                       " and COD_TYPL_ARL='" + typeAvance + "'" + 
                       " AND to_char(DAT_ARL_ARL,'DD/MM/YYYY') = '" + 
                       DateHandler.dateToStr(dateOuverture) + "'" + 
                       " and AVANC_REMB_LIQUID.NUM_SEQ_CPLA in" + 
                       "(select NUM_SEQ_CPLA from CONTRAT_PLACEMENT where COD_STRC_CCPT = " + 
                       codeStructure + " and COD_PRD_PLC  =" + produit + ")");


        List listDem = (List)jt.queryForList(requete);
        return listDem;
    }

    public List getLiquiAEcheanceTraiteleSoir(Long codeStructure, Long produit, 
                                              Date dateOuverture, 
                                              String etat) {
        jt = new JdbcTemplate(dataSource);
        String requete = 
            new String("select count(*),SUM(MONT_CAP_CPLA)  from CONTRAT_PLACEMENT where " + 
                       " cod_strc_CCPT = " + codeStructure + 
                       " and COD_ETAT_CPLA = '" + etat + 
                       "'  and COD_PRD_PLC  = " + produit + 
                       "AND DAT_LIQ_CPLA>DAT_ECHE_CPLA	 " + 
                       " AND to_char(DAT_LIQ_CPLA,'DD/MM/YYYY') = '" + 
                       DateHandler.dateToStr(dateOuverture) + "'");


        List listDem = (List)jt.queryForList(requete);
        return listDem;
    }

    public List getContplacArriveAEcheance(Long codeStructure, 
                                           Date dateOuverture, Long produit, 
                                           String etat) {
        jt = new JdbcTemplate(dataSource);
        String requete = 
            new String("select count(*),SUM(MONT_CAP_CPLA)  from CONTRAT_PLACEMENT where " + 
                       " cod_strc_CCPT = " + codeStructure + 
                       " and COD_ETAT_CPLA = '" + etat + 
                       "'  and COD_PRD_PLC  = " + produit + 
                       " AND to_char(DAT_ECHE_CPLA,'DD/MM/YYYY') = '" + 
                       DateHandler.dateToStr(dateOuverture) + "'");


        List listDem = (List)jt.queryForList(requete);
        return listDem;
    }


    public List getInteretServiParProduit(Long codeStructure, 
                                          Date dateOuverture, Long produit) {
        jt = new JdbcTemplate(dataSource);
        String requete = 
            new String("select count(*),SUM(MONT_BRUT_ISRV)  from INTERET_SERVI where " + 
                       " to_char(DAT_ISRV_ISRV,'DD/MM/YYYY') = '" + 
                       DateHandler.dateToStr(dateOuverture) + "'" + 
                       " and INTERET_SERVI.NUM_SEQ_CPLA in" + 
                       "(select NUM_SEQ_CPLA from CONTRAT_PLACEMENT where COD_STRC_CCPT = " + 
                       codeStructure + " and COD_PRD_PLC  =" + produit + ")");


        List listDem = (List)jt.queryForList(requete);
        return listDem;
    }

    public List getNbrDemRenPlacType(Long codeStructure, Long produit, 
                                     String etat, Long typeRen, 
                                     Date dateOuverture) {
        jt = new JdbcTemplate(dataSource);
        String requete = 
            new String("select count(*),SUM(MONT_PLA_DEMD)  from DEMANDE_DECISION where COD_NDEM_DEMD='R'" + 
                       " AND cod_strc_strc = " + codeStructure + 
                       " and COD_ETAT_DEMD = '" + etat + "'" + 
                       " and COD_TYPR_DEMD	 = " + typeRen + 
                       " and COD_PRD_PLC  = " + produit + 
                       " AND to_char(DAT_VLD_DEMD,'DD/MM/YYYY') = '" + 
                       DateHandler.dateToStr(dateOuverture) + "'");

        List listDem = (List)jt.queryForList(requete);
        return listDem;
    }

    public List getNbrDemRenPlacRej(Long codeStructure, Long produit,

        Date dateOuverture) {
        jt = new JdbcTemplate(dataSource);
        String requete = 
            new String("select count(*),SUM(MONT_PLA_DEMD)  from DEMANDE_DECISION where COD_NDEM_DEMD='R'" + 
                       " AND cod_strc_strc = " + codeStructure + 
                       " and COD_ETAT_DEMD = 'R'" + " and COD_PRD_PLC  = " + 
                       produit + 
                       " AND to_char(DAT_REJ_DEMD,'DD/MM/YYYY') = '" + 
                       DateHandler.dateToStr(dateOuverture) + "'");

        List listDem = (List)jt.queryForList(requete);
        return listDem;
    }

    public List getNbrRenPlacParEtat(Long codeStructure, Long produit, 
                                     String etat, Date dateOuverture) {
        jt = new JdbcTemplate(dataSource);
        String requete = 
            new String("select count(*),SUM(MONT_CAP_CPLA)  from CONTRAT_PLACEMENT where " + 
                       " cod_strc_CCPT = " + codeStructure + 
                       " and COD_ETAT_CPLA = '" + etat + "'" + 
                       " and COD_PRD_PLC  = " + produit + 
                       "and NUM_SQCR_CPLA is not null" + 
                       " AND to_char(DAT_VLD_CPLA,'DD/MM/YYYY') = '" + 
                       DateHandler.dateToStr(dateOuverture) + "'");

        List listDem = (List)jt.queryForList(requete);
        return listDem;
    }


    public List getNbrRenPlacApresEch(Long codeStructure, Long produit, 
                                      Long codeOperation, Date dateOuverture) {
        jt = new JdbcTemplate(dataSource);
        String requete = 
            new String("select count(*),SUM(MONT_DOP_DOPL)  from Details_operation_placement where " + 
                       " COD_STRC_STRC = " + codeStructure + 
                       " and COD_OPER_OPER	 = " + codeOperation + 
                       " and COD_TACH_TACH   =" + 2 + 
                       " AND to_char(DAT_COMP_DOPL	,'DD/MM/YYYY') = '" + 
                       DateHandler.dateToStr(dateOuverture) + "'" + 
                       " and Details_operation_placement.NUM_SEQ_CPLA in" + 
                       "(select NUM_SEQ_CPLA from CONTRAT_PLACEMENT where COD_STRC_CCPT = " + 
                       codeStructure + " and COD_PRD_PLC  =" + produit + ")");

        List listDem = (List)jt.queryForList(requete);
        return listDem;
    }

    public List getInteretServi(Long codeStructure, Long codeOper, 
                                Date dateOuverture, Long produit) {
        jt = new JdbcTemplate(dataSource);
        String requete = 
            new String("select count(*),SUM(MONT_DIN_OMP)  from OPERATION_MOY_PAY where " + 
                       " to_char(DAT_OPER_OMP,'DD/MM/YYYY') = '" + 
                       DateHandler.dateToStr(dateOuverture) + "'" + 
                       " AND COD_PRD_OMP=" + produit + " AND COD_OPER_OPER=" + 
                       codeOper + " AND COD_STRC_STRC=" + codeStructure);


        List listDem = (List)jt.queryForList(requete);
        return listDem;
    }

    public List getDemTresorAtt(Long produit, String etat, String Type) {
        jt = new JdbcTemplate(dataSource);
        String requete = 
            new String("select count(*),SUM(MONT_PLA_DEMD)  from DEMANDE_DECISION where COD_NDEM_DEMD='" + 
                       Type + "'" + " and COD_ETAT_DEMD = '" + etat + 
                       "'  and COD_PRD_PLC      = " + produit + 
                       " AND COD_FAV_DEMD!='G'");

        List listDem = (List)jt.queryForList(requete);
        return listDem;
    }

    public List getDemTresor(Long produit, String etat, Date dateOuverture, 
                             String type) {
        jt = new JdbcTemplate(dataSource);
        String requete = 
            new String("select count(*),SUM(MONT_PLA_DEMD)  from DEMANDE_DECISION where COD_NDEM_DEMD='" + 
                       type + "'" + " and COD_ETAT_DEMD = '" + etat + 
                       "'  and COD_PRD_PLC      = " + produit + 
                       " AND COD_FAV_DEMD!='G'" + 
                       " AND to_char(DAT_VLD_DEMD,'DD/MM/YYYY') = '" + 
                       DateHandler.dateToStr(dateOuverture) + "'");

        List listDem = (List)jt.queryForList(requete);
        return listDem;
    }

    public List getDemValideTresor(Long produit, String etat, 
                                   Date dateOuverture, String type) {
        jt = new JdbcTemplate(dataSource);
        String requete = 
            new String("select count(*),SUM(MONT_PLA_DEMD)  from DEMANDE_DECISION where COD_NDEM_DEMD='" + 
                       type + "'" + " and COD_ETAT_DEMD = '" + etat + 
                       "'  and COD_PRD_PLC      = " + produit + 
                       " and COD_STRV_STRC=900" + " AND COD_FAV_DEMD!='G'" + 
                       " AND to_char(DAT_VLD_DEMD,'DD/MM/YYYY') = '" + 
                       DateHandler.dateToStr(dateOuverture) + "'");

        List listDem = (List)jt.queryForList(requete);
        return listDem;
    }

    public List getListContratPlacParEtat(Long codeStructure, Long produit, 
                                          Date dateOuverture, String etat) {
        jt = new JdbcTemplate(dataSource);
        String requete = 
            new String("select count(*),SUM(MONT_CAP_CPLA)  from CONTRAT_PLACEMENT where " + 
                       " cod_strc_CCPT = " + codeStructure + 
                       " and COD_ETAT_CPLA = '" + etat + 
                       "'  and COD_PRD_PLC  = " + produit + 
                       " AND to_char(DAT_VAL_CPLA,'DD/MM/YYYY') = '" + 
                       DateHandler.dateToStr(dateOuverture) + "'");


        List listDem = (List)jt.queryForList(requete);
        return listDem;
    }

    public List getStatOperMoyPay(Long codeStructure, Long codeOper, 
                                  Date dateOuverture) {
        jt = new JdbcTemplate(dataSource);
        String requete = 
            new String("select count(*),SUM(MONT_DIN_OMP)  from OPERATION_MOY_PAY where " + 
                       " to_char(DAT_OPER_OMP,'DD/MM/YYYY') = '" + 
                       DateHandler.dateToStr(dateOuverture) + "'" + 
                       " AND COD_OPER_OPER=" + codeOper + 
                       " AND COD_STRC_STRC=" + codeStructure);


        List listDem = (List)jt.queryForList(requete);
        return listDem;
    }
    public List getStatOperMoyPayParPrd(Long codeStructure, Long codeOper, 
                                  Date dateOuverture,Long produit) {
        jt = new JdbcTemplate(dataSource);
        String requete = 
            new String("select count(*),SUM(MONT_DIN_OMP)  from OPERATION_MOY_PAY where " + 
                       " to_char(DAT_OPER_OMP,'DD/MM/YYYY') = '" + 
                       DateHandler.dateToStr(dateOuverture) + "'" + 
                       " AND COD_OPER_OPER=" + codeOper + 
                       " AND COD_PRD_OMP=" + produit + 
                       " AND COD_STRC_STRC=" + codeStructure);


        List listDem = (List)jt.queryForList(requete);
        return listDem;
    }

    public List getStatMouvInterne(Long codeStructure, Long codeOper, 
                                   Date dateOuverture) {
        jt = new JdbcTemplate(dataSource);
        String requete = 
            new String("select count(*),SUM(MONT_MVTI_MVTI)  from MOUVEMENT_INTERNE where " + 
                       " to_char(DAT_OPER_MVTI,'DD/MM/YYYY') = '" + 
                       DateHandler.dateToStr(dateOuverture) + "'" + 
                       " AND COD_OPER_OPER=" + codeOper + 
                       " AND COD_STRC_STRC=" + codeStructure);


        List listDem = (List)jt.queryForList(requete);
        return listDem;
    }
    


}


