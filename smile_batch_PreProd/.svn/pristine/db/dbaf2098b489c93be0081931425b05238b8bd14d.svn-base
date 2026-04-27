package com.bna.smile.model.statistique.dao;

import java.util.Date;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;

import com.bna.commun.util.DateHandler;

public class StatistiqueCarnetDeChequeDAO {
    public StatistiqueCarnetDeChequeDAO() {
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
     * Nombre de carnet de chèque demandés
     * @param codeStructure
     * @param etatContrat
     * @param dateOuverture
     * @return nombre de souscription
     * @author Mdimagh med Lassaad
     */
    public List getNombreCarnetDecheque(Long codeStructure, String etatContrat, 
                                        Date dateOuverture) {
        jt = new JdbcTemplate(dataSource);
        String requete = 
            new String("select d.cod_conf_conf , t.lib_conf_conf, count(*) nombre  from demande_cheque d, type_confection t where d.cod_strc_strc = " + 
                       codeStructure + " " + 
                       "and d.COD_ETAT_DCHQ =" + etatContrat +" and to_char(d.DAT_DEM_DCHQ,'DD/MM/YYYY') = '" + 
                       DateHandler.dateToStr(dateOuverture) + 
                       "' and  d.cod_conf_conf = t.cod_conf_conf group by d.cod_conf_conf , t.lib_conf_conf");
        List list = (List)jt.queryForList(requete);
        return list;
    }
    
    
    /**
     * Nombre de demande de carnet de chèque 
     * @param codeStructure
     * @param etatContrat
     * @param dateDemande
     * @return nombre de souscription
     * @author Mdimagh med Lassaad
     */
    public List getNombreDemandeCarnetDeCheque(Long codeStructure, String etatContrat, 
                                        Date dateDemande) {
        jt = new JdbcTemplate(dataSource);
        String requete = 
            new String("select d.cod_conf_conf , t.lib_conf_conf, count(*) nombre  from demande_cheque d, type_confection t where d.cod_strc_strc = " + 
                       codeStructure + " " + 
                       "and d.COD_ETAT_DCHQ =" + etatContrat +" and to_char(d.DAT_DEM_DCHQ,'DD/MM/YYYY') = '" + 
                       DateHandler.dateToStr(dateDemande) + 
                       "' and  d.cod_conf_conf = t.cod_conf_conf group by d.cod_conf_conf , t.lib_conf_conf");
        List list = (List)jt.queryForList(requete);
        return list;
    }
    
}
