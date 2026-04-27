package com.bna.smile.model.domainecontratcompte.procuration.dao;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;

import com.bna.commun.util.DateHandler;

public class MandatDAO {
    public MandatDAO() {
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

    public Long getSequenceMandat() {
        jt = new JdbcTemplate(dataSource);
        Long numeroSequence = 
            (Long)jt.queryForObject("select SEQ_MANDAT.NEXTVAL from dual ", 
                                    Long.class);
        return numeroSequence;
    }


    public Long getSequenceDetailMandatPersonne() {
        jt = new JdbcTemplate(dataSource);
        Long numeroSequence = 
            (Long)jt.queryForObject("select SEQ_MANDAT_PERSONNE.NEXTVAL from dual ", 
                                    Long.class);
        return numeroSequence;
    }

    public Long getSequenceDetailRenouvellementMandat() {
        jt = new JdbcTemplate(dataSource);
        Long numeroSequence = 
            (Long)jt.queryForObject("select SEQ_DETAIL_REN_MANDAT.NEXTVAL from dual ", 
                                    Long.class);
        return numeroSequence;
    }

    /**
     * Methode qui génére le numero de sequence d'un mandat Opération
     * @return Integer
     */
    public Long getSequenceMandatOperation() {
        jt = new JdbcTemplate(dataSource);
        Long numeroSequence = 
            (Long)jt.queryForObject("select SEQ_MANDAT_OPERATION.NEXTVAL from dual ", 
                                    Long.class);
        return numeroSequence;
    }

     /**
      * Methode qui permet de calculer le nombre des mandats généraux valides pour un contrat
      * @return Integer
      */
     public Long getNombreMandatGenerauxParContrat(Long codeStructure, Long codeProduit, Long numeroCompte) {
         jt = new JdbcTemplate(dataSource);
         String requete =            "select count(*) from mandat where cod_strc_strc="+codeStructure+
                                     " and  cod_prd_prd="+ codeProduit +" and num_ccpt_ccpt = "+ numeroCompte+
                                     " and cod_etat_mand ='V' " +
                                     " and  to_date(to_char(dat_deb_mand,'DD/MM/RRRR')) <= to_date('" + DateHandler.dateJour().toString()+ "')" + 
                                     " and (to_date(to_char(dat_fin_mand,'DD/MM/RRRR')) >= to_date('" + DateHandler.dateJour().toString() + "')" +
                                     " or dat_fin_mand is null )"+
                                     " and cod_typ_mand ='G' ";
      
      System.out.println(requete);
         Long nombreMandat = 
             (Long)jt.queryForObject(requete , Long.class);
         return nombreMandat;
     }

    /**
     * Methode qui permet de calculer le nombre des mandats spéciaux valides pour un contrat pour uen operation
     * @return Integer
     */
    public Long getNombreMandatSpeciauxParContrat(Long codeStructure, Long codeProduit, Long numeroCompte, Long codeOeration) {
        jt = new JdbcTemplate(dataSource);
        String requete =            "select count(*) from mandat m, mandat_Operation mo where cod_strc_strc="+codeStructure+
                                    " and  cod_prd_prd="+ codeProduit +" and num_ccpt_ccpt = "+ numeroCompte+
                                    " and cod_etat_mand ='V' " +
                                    " and  to_date(to_char(m.dat_deb_mand,'DD/MM/RRRR')) <= to_date('" + DateHandler.dateJour().toString()+ "')" + 
                                    " and (to_date(to_char(m.dat_fin_mand,'DD/MM/RRRR')) >= to_date('" + DateHandler.dateJour().toString() + "')" +
                                    " or dat_fin_mand is null )"+
                                    " and m.cod_typ_mand ='S' "+
                                    " and m.num_mand_mand =mo.num_mand_mand ";
                                    
                                    
         if (codeOeration != null) { requete = requete +" and mo.cod_oper_oper = "+ codeOeration.toString() ; }
         
            requete = requete +     " and to_date(to_char(mo.dat_deb_maop,'DD/MM/RRRR')) <= to_date('" + DateHandler.dateJour().toString()+ "')" + 
                                    " and (to_date(to_char(mo.dat_fin_maop,'DD/MM/RRRR')) >= to_date('"  + DateHandler.dateJour().toString()+ "')"+
                                    " or mo.dat_fin_maop is null )";
     System.out.println(requete);
        Long nombreMandat = 
            (Long)jt.queryForObject(requete , Long.class);
        return nombreMandat;
    }

    /**
     * Methode qui permet de verifier la validité d'une operation dans une mandat valide
     * @return boolean
     */
    public boolean verifierMandatOpartion(Long numMandat, Long codeOperation) {
        jt = new JdbcTemplate(dataSource);
        String requete =            "select NUM_MAOP_MAOP from mandat_operation where cod_oper_oper ="+codeOperation+
                                    " and  num_mand_mand="+ numMandat +" and dat_fin_maop is null";                                   
     
     System.out.println(requete);
        Long numMaopMaop = 
            (Long)jt.queryForObject(requete , Long.class);
        if (numMaopMaop!=null)
          return true;
        else return false;          
    }


}
