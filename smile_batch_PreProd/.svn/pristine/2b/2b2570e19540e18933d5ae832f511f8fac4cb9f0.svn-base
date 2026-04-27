package com.bna.smile.model.statistique.dao;

import java.util.Date;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;

import com.bna.commun.util.DateHandler;

public class StatistiqueSouscriptionDAO {
    public StatistiqueSouscriptionDAO() {
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
                       "'" + " and to_char(dat_ouv_ccpt,'DD/MM/YYYY') = '" + 
                       DateHandler.dateToStr(dateOuverture) + "'");
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
            new String("select c.cod_prd_prd , p.lib_prd_prd, count(*) nombre  from contrat_cpt c, produit p where cod_strc_strc = " + 
                       codeStructure + " and cod_etat_ccpt = '" + etatContrat + 
                       "'" + " and to_char(dat_ouv_ccpt,'DD/MM/YYYY') = '" + 
                       DateHandler.dateToStr(dateOuverture) + "'" + 
                       " and c.cod_prd_prd = p.cod_prd_prd  group by c.cod_prd_prd , p.lib_prd_prd ");
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
            new String("select tp.cod_tper_tper , tp.lib_tper_tper, count(*) nombre from contrat_cpt cpt, client cl, personne p, categorie_personne cp, type_pers tp   " + 
                       "where " + 
                       "cpt.num_seq_pers    = cl.num_seq_pers   and " + 
                       "cl.num_seq_pers     = p.num_seq_pers    and " + 
                       "p.cod_catp_catp     = cp.cod_catp_catp  and " + 
                       "cp.cod_tper_tper    = tp.cod_tper_tper  and " + 
                       "cpt.cod_strc_strc =" + codeStructure + 
                       "  and cpt.cod_etat_ccpt ='" + etatContrat + "' " + 
                       " and to_char(dat_ouv_ccpt,'DD/MM/YYYY') = '" + 
                       DateHandler.dateToStr(dateOuverture) + "'" + 
                       " group by  tp.cod_tper_tper , tp.lib_tper_tper ");


        List list = (List)jt.queryForList(requete);
        return list;
    }

    /**
     * Nombre de signatures scannées par type de contrat;
     * @param codeStructure
     * @param dateSignature
     * @return
     */
    public List getlistNombreSignatureParTypeContrat(Long codeStructure, 
                                                      
                                                       Date dateSignature) {
        jt = new JdbcTemplate(dataSource);
        String requete = 
            new String("select  s.cod_prd_prd , p.lib_prd_prd , count(*) nombre from signature s, produit p  " + 
                       "where " + 
                       "to_char(s.DAt_OPER_SIGN,'DD/MM/YYYY') ='" + DateHandler.dateToStr(dateSignature) + "'" +
                       " and s.cod_strc_strc =" + codeStructure + 
                       " and s.cod_prd_prd = p.cod_prd_prd " + 
                       " group by  s.cod_prd_prd , p.lib_prd_prd ");


        List list = (List)jt.queryForList(requete);
        return list;
    }
    public List getlistSouscriptionProduit(Long codeStructure, 
                                                        String etatContrat, 
                                                        Date dateOuverture,Long codproduit) {
        jt = new JdbcTemplate(dataSource);
        String requete = 
            new String("select cod_prd_prd , num_ccpt_ccpt,NOM_INTI_CCPT  from contrat_cpt  where cod_strc_strc = " + 
                       codeStructure + " and cod_etat_ccpt = '" + etatContrat + 
                       "'" + " and to_char(dat_ouv_ccpt,'DD/MM/YYYY') = '" + 
                       DateHandler.dateToStr(dateOuverture) + "'" +
                       "and cod_prd_prd="+codproduit +" order by cod_prd_prd");
        List list = (List)jt.queryForList(requete);
        return list;
    }


    public List getlistNbreSouscRejeteParTypeContrat(Long codeStructure,
                                                        Date daterej) {
        jt = new JdbcTemplate(dataSource);
        String requete = 
            new String("select c.cod_prd_cptr , p.lib_prd_prd, count(*) nombre  from contrat_rejete c, produit p where cod_strc_cptr = " + 
                       codeStructure + 
                        " and to_char(dat_rej_cptr,'DD/MM/YYYY') = '" + 
                       DateHandler.dateToStr(daterej) + "'" + 
                       " and c.cod_prd_cptr = p.cod_prd_prd  group by c.cod_prd_cptr , p.lib_prd_prd ");
        List list = (List)jt.queryForList(requete);
        return list;
    }
    
    public List getlistSouscRejProduit(Long codeStructure, Date daterej,Long codproduit) {
        jt = new JdbcTemplate(dataSource);
        String requete = 
            new String("select cod_prd_cptr , num_ccpt_cptr,LIB_INTI_CPTR  from contrat_rejete  where cod_strc_cptr = " + 
                       codeStructure + 
                       " and to_char(dat_rej_cptr,'DD/MM/YYYY') = '" + 
                       DateHandler.dateToStr(daterej) + "'" +
                       "and cod_prd_cptr="+codproduit +" order by cod_prd_cptr");
        List list = (List)jt.queryForList(requete);
        return list;
    }
}
