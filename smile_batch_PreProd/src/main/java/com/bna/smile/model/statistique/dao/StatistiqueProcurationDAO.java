package com.bna.smile.model.statistique.dao;

import java.util.Date;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;

import com.bna.commun.util.DateHandler;

public class StatistiqueProcurationDAO {
    public StatistiqueProcurationDAO() {
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


    public Long getNombreMandat(Long codeStructure, String etatMandat, 
                                Date dateCreation) {
        jt = new JdbcTemplate(dataSource);
        String requete = 
            new String("select  count(*) nombre  from mandat  where cod_strc_strc = " + 
                       codeStructure + " and cod_etat_mand = '" + etatMandat + 
                       "'" + " and to_char(dat_cre_mand,'DD/MM/YYYY') = '" + 
                       DateHandler.dateToStr(dateCreation)) + "'";
        Long nombre = (Long)jt.queryForLong(requete);
        return nombre;
    }

    /**
     * Nombre de mandat par type de contrat
     * @param codeStructure
     * @param etatContrat
     * @param dateOuverture
     * @return nombre de souscription
     * @author Mdimagh med Lassaad
     */
    public


    List getNombreMandatParTypeContrat(Long codeStructure, String etatMandat, 
                                       Date dateCreation) {
        jt = new JdbcTemplate(dataSource);
        String requete = 
            new String("select m.cod_prd_prd, p.lib_prd_prd, count(*) nombre  from mandat m , produit p where cod_strc_strc = " + 
                       codeStructure + " and cod_etat_mand = '" + etatMandat + 
                       "'" + " and to_char(dat_cre_mand,'DD/MM/YYYY') = '" + 
                       DateHandler.dateToStr(dateCreation) + 
                       "' and m.cod_prd_prd = p.cod_prd_prd group by m.cod_prd_prd, p.lib_prd_prd");
        List list = (List)jt.queryForList(requete);
        return list;
    }

    /**
     * Nombre de mandats renouvellés
     * @param codeStructure
     * @param etatMandat
     * @param dateRenouvellement
     * @return
     */
    public List getNombreMandatRenouvleTypeContrat(Long codeStructure, 
                                                   String etatMandat, 
                                                   Date dateRenouvellement) {
        jt = new JdbcTemplate(dataSource);
        String requete = 
            new String("select m.cod_prd_prd, p.lib_prd_prd, count(*) nombre  from mandat m , produit p where cod_strc_strc = " + 
                       codeStructure + " and cod_etat_mand = '" + etatMandat + 
                       "'" + " and to_char(DAT_VLDR_MAND,'DD/MM/YYYY') = '" + 
                       DateHandler.dateToStr(dateRenouvellement) + 
                       "' and m.cod_prd_prd = p.cod_prd_prd group by m.cod_prd_prd, p.lib_prd_prd");
        List list = (List)jt.queryForList(requete);
        return list;
    }

    /**
     * Nombre de mandats modifiés
     * @param codeStructure
     * @param etatMandat
     * @param dateModification
     * @return
     */
    public List getNombreMandatModifieTypeContrat(Long codeStructure, 
                                                  String etatMandat, 
                                                  Date dateModification) {
        jt = new JdbcTemplate(dataSource);
        String requete = 
            new String("select m.cod_prd_prd, p.lib_prd_prd, count(*) nombre  from mandat m , produit p where cod_strc_strc = " + 
                       codeStructure + " and cod_etat_mand = '" + etatMandat + 
                       "'" + " and to_char(DAT_VALM_MAND,'DD/MM/YYYY') = '" + 
                       DateHandler.dateToStr(dateModification) + 
                       "' and m.cod_prd_prd = p.cod_prd_prd group by m.cod_prd_prd, p.lib_prd_prd");
        List list = (List)jt.queryForList(requete);
        return list;
    }

    /**
     * Nombre de mandats Annulés
     * @param codeStructure
     * @param etatMandat
     * @param dateAnnulation
     * @return
     */
    public List getNombreMandatAnnulesTypeContrat(Long codeStructure, 
                                                  String etatMandat, 
                                                  Date dateAnnulation) {
        jt = new JdbcTemplate(dataSource);
        String requete = 
            new String("select m.cod_prd_prd, p.lib_prd_prd, count(*) nombre  from mandat m , produit p where cod_strc_strc = " + 
                       codeStructure + " and cod_etat_mand = '" + etatMandat + 
                       "'" + " and to_char(DAT_VLDA_MAND,'DD/MM/YYYY') = '" + 
                       DateHandler.dateToStr(dateAnnulation) + 
                       "' and m.cod_prd_prd = p.cod_prd_prd group by m.cod_prd_prd, p.lib_prd_prd");
        List list = (List)jt.queryForList(requete);
        return list;
    }
    public List getlistCreationProcuration(Long codeStructure, Long codeOperat,Long codetache, Date dateOperation,Long codproduit) {
        jt = new JdbcTemplate(dataSource);
        String requete = 
            new String("select TM.MANDAT_NUM_MAND_MAND,M.COD_TYP_MAND , M.COD_STRC_STRC,M.COD_PRD_PRD,M.NUM_CCPT_CCPT from TRACE_MANDAT TM,MANDAT M where " +
            "TM.COD_OPER_OPER="+codeOperat+" and TM.COD_TACH_TACH="+codetache+
            " and to_char(DAT_OPER_TRM,'DD/MM/YYYY') = '" + DateHandler.dateToStr(dateOperation) + "'" +
            " and M.COD_STRC_STRC="+codeStructure+
            " and M.COD_PRD_PRD="+codproduit+
            "and TM.MANDAT_NUM_MAND_MAND=M.NUM_MAND_MAND");
            
        List list = (List)jt.queryForList(requete);
        return list;
    }
}
