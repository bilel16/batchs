package com.bna.smile.model.domainecommun.dao;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;

public class CreditDAO {
    public CreditDAO() {
    }
    
    protected String sqlQuery;
    protected JdbcTemplate jt;
    protected DataSource dataSource;

    public void setSqlQuery(String sqlQuery) {
        this.sqlQuery = sqlQuery;
    }

    public String getSqlQuery() {
        return sqlQuery;
    }

    public void setJt(JdbcTemplate jt) {
        this.jt = jt;
    }

    public JdbcTemplate getJt() {
        return jt;
    }

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public DataSource getDataSource() {
        return dataSource;
    }
    
    public Long getNombreCredit( 
                                           String numCompte 
                                           ) {
        jt = new JdbcTemplate(dataSource);
        Long nombre=Long.valueOf(0);
        String requete = 
            new String("select count(*)  from tranchecci where num_cpt_tr='"+numCompte+"'" +
            "and COD_ETAT_ETT in (1,3,4)");
        nombre = (Long)jt.queryForObject(requete, Long.class);
        return nombre;
    }
}
