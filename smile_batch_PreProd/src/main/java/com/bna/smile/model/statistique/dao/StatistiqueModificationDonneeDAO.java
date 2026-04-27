package com.bna.smile.model.statistique.dao;

import java.util.Date;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;

import com.bna.commun.util.DateHandler;

public class StatistiqueModificationDonneeDAO {
    public StatistiqueModificationDonneeDAO() {
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
     * Nombre de modification par type demodification
     * @param codeStructure
     * @param dateModification
     * @return liste de nombre de modification
     * @author Mdimagh med Lassaad
     */
    public List getNombreModificationParTypeModification(Long codeStructure, 
                                                 Date dateModification) {
        jt = new JdbcTemplate(dataSource);
        

        String requete = 
            new String("select t.cod_cod_modf, t.lib_modf_modf, count(*) nombre " +
            "from modification_donnees m, type_modification t, personnel p, structure s" + 
            " where " + 
            " s.cod_strc_strc = " +  codeStructure + " and  to_char(dat_mod_modd,'DD/MM/YYYY') =  '" + DateHandler.dateToStr(dateModification) + "'"+
            "  and m.num_matr_user = p.NUM_MATR_USER" + 
            "  and p.cod_strc_strc = s.cod_strc_strc" + 
            "  and m.cod_cod_modf = t.cod_cod_modf" + 
            "  group by t.cod_cod_modf, t.lib_modf_modf" );
            
        List list = (List)jt.queryForList(requete);
        return list;
    }

}
