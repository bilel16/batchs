package com.bna.smile.model.domainecommun.dao;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;

public class SequenceDAO {
    public SequenceDAO() {
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

    public Long getSequenceDetailEtatContrat() {
        jt = new JdbcTemplate(dataSource);
        Long numeroSequence = 
            (Long)jt.queryForObject("select SEQ_ETAT_CONTRAT.NEXTVAL from dual ", 
                                    Long.class);
        return numeroSequence;
    }

    public Long getSequenceCategorieContrat() {
        jt = new JdbcTemplate(dataSource);
        Long numeroSequence = 
            (Long)jt.queryForObject("select SEQ_CATEGORIE_COMPTE.NEXTVAL from dual ", 
                                    Long.class);
        return numeroSequence;
    }
    
    /**
     * Sequence pour l amodification des données client
     * @return
     */
    public Long getSequenceModificationDonnees() {
        jt = new JdbcTemplate(dataSource);
        Long numeroSequence = 
            (Long)jt.queryForObject("select SEQ_MODIF_DONNEE.NEXTVAL from dual ", 
                                    Long.class);
        return numeroSequence;
    }   
    
    /**
     * Sequence pour la table Opérations Epargnes
     * @author Mdimagh Lassaad
     * @since 31/10/2007
     */
    public Long getSequenceOperationEpargne() {
        jt = new JdbcTemplate(dataSource);
        Long numeroSequence = 
            (Long)jt.queryForObject("select SEQ_OPERATION_EPARGNE.NEXTVAL from dual ", 
                                    Long.class);
        return numeroSequence;
    } 
    
    /**
     * Sequence pour la table Mise à disposition
     * @author Mdimagh Lassaad
     * @since 06/10/2007
     */
    public Long getSequenceMiseAdisposition() {
        jt = new JdbcTemplate(dataSource);
        Long numeroSequence = 
            (Long)jt.queryForObject("select SEQ_MISE_A_DISPOSITION.NEXTVAL from dual ", 
                                    Long.class);
        return numeroSequence;
    } 
}
