package com.bna.smile.model.domainecontratcompte.souscriptioncontratcompte.dao;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;

public class PersonneDAO {

    protected String sqlQuery;
    protected JdbcTemplate jt;
    protected DataSource dataSource;

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public void setSqlQuery(String sqlQuery) {
        this.sqlQuery = sqlQuery;
    }

    public Long getSequencePersonne() {
        jt = new JdbcTemplate(dataSource);
        Long numeroSequence = 
            (Long)jt.queryForObject("select SEQ_PERSONNE.NEXTVAL from dual ", 
                                    Long.class);
        return numeroSequence;
    }
    
    public boolean verifExistContratCompte(Long codPrdPrd, Long codStrcStrc, Long numCcptCcpt ) {
        jt = new JdbcTemplate(dataSource);
        Long nbrContrat = 
            (Long)jt.queryForObject("select count(*) from contrat_cpt where cod_prd_prd = "+ codPrdPrd + " and cod_strc_strc =  "+ codStrcStrc + " and num_ccpt_ccpt = " + numCcptCcpt,  
                                    Long.class);        
        
        if (nbrContrat > Long.valueOf(0))
          return true;
        else  return false; 
    }
    
    public boolean verifExistLivretEpargne(String numLivret) {
        jt = new JdbcTemplate(dataSource);
        Long nbrLiv = 
            (Long)jt.queryForObject("select count(*) from livret_epargne where num_livr_live = '"+ numLivret +"'",  
                                    Long.class);        
        
        if (nbrLiv > Long.valueOf(0))
          return true;
        else  return false; 
    }
}

