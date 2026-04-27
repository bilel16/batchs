package com.bna.smile.model.statistique.dao;

import java.util.Date;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;

import com.bna.commun.util.DateHandler;

public class StatistiqueOppositionDAO {
    public StatistiqueOppositionDAO() {
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
     * Nombre d'opposition
     * @param codeStructure
     * @param etatContrat
     * @param dateOuverture
     * @return nombre d'opposition
     * @author Mdimagh med Lassaad
     */
    public List getNombreOpposition(Long codeStructure, String etatContrat, 
                                      Date dateOuverture) {
        jt = new JdbcTemplate(dataSource);
        String requete = 
            new String("select o.COD_MOYP_TMOY, t.LIB_MOYP_TMOY, count(*) nombre  from OPPOSITION_MOYEN_PAIEMENT o , TYPE_MOYEN_PAIEMENT t " + 
                       "where cod_strc_strc = " + codeStructure + 
                       " and COD_ETAT_OPMP = '"+etatContrat+"' " + " and to_char(DAT_OPER_OPMP,'DD/MM/YYYY') = '" + 
                       DateHandler.dateToStr(dateOuverture) + 
                       "' and o.COD_MOYP_TMOY = t.COD_MOYP_TMOY " + 
                       " group by o.COD_MOYP_TMOY, t.LIB_MOYP_TMOY");
        List list = (List)jt.queryForList(requete);
        return list;
    }
}
