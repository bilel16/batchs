package com.bna.smile.model.domainechange.dao;


import java.util.List;

import javax.sql.DataSource;

import org.apache.log4j.Logger;
import org.springframework.jdbc.core.JdbcTemplate;

public class ChangeDAO {
    private static final Logger logger = 
        Logger.getLogger(ChangeDAO.class);

    public ChangeDAO() {
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

    

    public List getListCoursChangeVeilleValide(String dateComptable) {
        jt = new JdbcTemplate(dataSource);

        String requete = 
        
        
        "select c.COD_DEV_DEV,to_char(DAT_JOUR_CCHN,'DD/MM/YYYY'),LIB_DEV_DEV ,MONT_CABA_CCHN,MONT_CVBA_CCHN, MONT_CABC_CCHN,MONT_CVBC_CCHN from cours_change c, devise d where dat_jour_cchn = (select max(dat_jour_cchn) from cours_change h where h.cod_dev_dev = c.COD_DEV_DEV) " +
        "and c.cod_dev_dev = d.COD_DEV_DEV and c.COD_ETAT_CCHN = 'V' ORDER BY d.ORD_DEV_DEV";
                    
        System.out.println(requete);
        List rows = jt.queryForList(requete);
        return rows;
    }  
   
    
    public List getListCoursChangeEnAttente(String dateComptable) {
        jt = new JdbcTemplate(dataSource);

        String requete = 
        
        
        "select c.COD_DEV_DEV,to_char(DAT_JOUR_CCHN,'DD/MM/YYYY'),LIB_DEV_DEV ,MONT_CABA_CCHN,MONT_CVBA_CCHN, MONT_CABC_CCHN,MONT_CVBC_CCHN from cours_change c, devise d where dat_jour_cchn = (select max(dat_jour_cchn) from cours_change h where h.cod_dev_dev = c.COD_DEV_DEV) " +
        "and c.cod_dev_dev = d.COD_DEV_DEV and COD_ETAT_CCHN = 'A' ORDER BY d.ORD_DEV_DEV";
                    
        System.out.println(requete);
        List rows = jt.queryForList(requete);
        return rows;
    }    
    
    
    public List getListCoursPariteOfficielle(String etat) {
        jt = new JdbcTemplate(dataSource);

        String requete = 
        
        
        "select p.COD_DEV_DEV,annee,LIB_DEV_DEV ,MONT_COUR_PAOF from PARITE_OFFICIELLE p, devise d where annee = (select max(annee) from PARITE_OFFICIELLE c where c.cod_dev_dev = p.cod_dev_dev) " +
        "and p.cod_dev_dev = d.COD_DEV_DEV and COD_ETAT_PAOF = '" + etat +"'"+" ORDER BY d.ORD_DEV_DEV";
                    
        System.out.println(requete);
        List rows = jt.queryForList(requete);
        return rows;
    } 
    public List getListCoursPariteParAnnee(String annee) {
        jt = new JdbcTemplate(dataSource);

        String requete = 
        
        
        "select p.COD_DEV_DEV,annee,LIB_DEV_DEV ,MONT_COUR_PAOF from PARITE_OFFICIELLE p, devise d where annee ="+annee +
        " and p.cod_dev_dev = d.COD_DEV_DEV and COD_ETAT_PAOF = 'V' ORDER BY d.ORD_DEV_DEV ";
                    
        System.out.println(requete);
        List rows = jt.queryForList(requete);
        return rows;
    }  
    public List getListCoursChangeParDate(String dateComptable) {
        jt = new JdbcTemplate(dataSource);

        String requete = 
        
        
        "select c.COD_DEV_DEV,to_char(DAT_JOUR_CCHN,'DD/MM/YYYY'),LIB_DEV_DEV ,MONT_CABA_CCHN,MONT_CVBA_CCHN, MONT_CABC_CCHN,MONT_CVBC_CCHN from cours_change c, devise d where to_char(DAT_JOUR_CCHN,'DD/MM/YYYY') =" +"'"+dateComptable+"'" +
        " and c.cod_dev_dev = d.COD_DEV_DEV and c.COD_ETAT_CCHN = 'V' ORDER BY d.ORD_DEV_DEV";
                    
        System.out.println(requete);
        List rows = jt.queryForList(requete);
        return rows;
    }   
}
