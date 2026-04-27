package com.bna.smile.model.statistique.dao;

import java.util.Date;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;

/**
 * @author Mdimagh Med Lassaad
 * @since 07/05/2008
 * 
 */
public class TableauDeBordDAO {
    public TableauDeBordDAO() {
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


    /**
     * Nombre de souscription effectués
     * @param codeStructure
     * @param etatContrat
     * @param dateOuverture
     * @return nombre de souscription
     * @author Mdimagh med Lassaad
     */
    public Long getNombreTotalSouscription(Long codeStructure, 
                                           String etatContrat, 
                                           Date dateOuverture) {
        jt = new JdbcTemplate(dataSource);
        String requete = 
            new String("select count(*)  from contrat_cpt where cod_strc_strc = " + 
                       codeStructure + " and cod_etat_ccpt = '" + etatContrat + 
                       "'");
        Long nombre = (Long)jt.queryForObject(requete, Long.class);
        return nombre;
    }

    /**
     * Nombnre de souscription (contrat) par produit
     * @param codeStructure
     * @param etatContrat
     * @param dateOuverture
     * @return
     */
    public List getlistNombreSouscriptionParTypeContrat(Long codeStructure, 
                                                        String etatContrat, 
                                                        Date dateOuverture) {
        jt = new JdbcTemplate(dataSource);
        String requete = 
            new String("select cod_prd_prd, count(*) nombre  from contrat_cpt where cod_strc_strc = " + 
                       codeStructure + " and cod_etat_ccpt = '" + etatContrat + 
                       "'" + " group by cod_prd_prd");
        List list = (List)jt.queryForList(requete);
        return list;
    }


    /**
     * Nombre de souscription par type de personne (PP / PM /CO);
     * @param codeStructure
     * @param etatContrat
     * @param dateOuverture
     * @return
     */
    public List getlistNombreSouscriptionParTypeClient(Long codeStructure, 
                                                       String etatContrat, 
                                                       Date dateOuverture) {
        jt = new JdbcTemplate(dataSource);
        String requete = 
            new String("select tp.cod_tper_tper ,  count(*) nombre from contrat_cpt cpt, client cl, personne p, categorie_personne cp, type_pers tp   " + 
                       "where " + 
                       "cpt.num_seq_pers    = cl.num_seq_pers   and " + 
                       "cl.num_seq_pers     = p.num_seq_pers    and " + 
                       "p.cod_catp_catp     = cp.cod_catp_catp  and " + 
                       "cp.cod_tper_tper    = tp.cod_tper_tper  and " + 
                       "cpt.cod_strc_strc =" + codeStructure + 
                       "  and cpt.cod_etat_ccpt ='" + etatContrat + "' " + 
                       " group by  tp.cod_tper_tper ");


        List list = (List)jt.queryForList(requete);
        return list;
    }
}
