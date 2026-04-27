package com.bna.smile.model.domainecontratcompte.moyensPaiement.dao;

import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;

import com.bna.commun.model.Chequier;
import com.bna.smile.model.domainecontratcompte.moyensPaiement.model.ParamDemandeCheque;

public class DemandeChequeDAO {

    public DemandeChequeDAO() {
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
     * Methode qui permet de retourner le numero du mandat d'une demande de chèque donne
     * * @return Long
     */
    public Long getNumeroMandatParDemande(String numdemDchq) {
        jt = new JdbcTemplate(dataSource);
        String requete = 
            "select distinct num_mand_mand from demande_cheque_mandat_personne where num_dem_dchq= " + 
            numdemDchq;
        Long numMandat = (Long)jt.queryForObject(requete, Long.class);
        return numMandat;
    }

    public List getList(ParamDemandeCheque paramDemandeCheque) {
        jt = new JdbcTemplate(dataSource);

        String requete = 
            "select distinct demande_cheque_mandat_personne.num_dem_dchq from demande_cheque_mandat_personne,demande_cheque " + 
            "where  num_mand_mand = " + paramDemandeCheque.getNumMandMand() + 
            " and demande_cheque_mandat_personne.num_dem_dchq = demande_cheque.num_dem_dchq" + 
            " and demande_cheque.cod_conf_conf = " + "'" + 
            paramDemandeCheque.getTypeConfection() + "'" + 
            " and cod_etat_dchq in (1,2,4,5,8)";

        
        List rows = jt.queryForList(requete);
        return rows;

    }


    /**
     * Methode qui permet de retourner la liste des demandes de chèques qui ont des chéquier prêts pour la destruction
     * demandeCheque dont l'état est partiellement satisfaite ou partiellement délivrée
     * ou les demandes dont les chequiers ont l'etat rejeté ou restitué
     * ou les demande qui ont des chéquiers recus et non encore délivré depuis 3 mois
     * 
     * * @return list
     */
    public List getListDemandeChequesPrDestruction(Long codeStructure) {
        jt = new JdbcTemplate(dataSource);

        String requete = 
            "select num_dem_dchq from demande_cheque " + "where  ( cod_strc_strc = " + 
            codeStructure + " and cod_etat_dchq in(5,6,7,3) and" + 
            " num_dem_dchq in (select distinct num_dem_dchq from chequier where cod_etat_chqi in (3,5) and cod_strc_strc = " + codeStructure + "))" + 
            " or (num_dem_dchq in (select distinct num_dem_dchq from chequier where (to_date(sysdate)-to_date(dat_recp_chqi))> 90  and cod_strc_strc = " + codeStructure + " and cod_etat_chqi = 1))";

        
        List rows = jt.queryForList(requete);
        return rows;

    }

    /**
     * Methode qui permet de verifier s'il existe déja un detail_operation_chequier
     * 
     * * @return Long
     */
    public

    Long getDetailOperationChequier(Chequier chequier, Long codOperOper) {
        jt = new JdbcTemplate(dataSource);
        String requete = 
            "select count(*) from Detail_operation_chequier where NUM_CHQI_CHQI =" + 
            chequier.getChequierId().getNumChqiChqi() + 
            " and NUM_DEM_DCHQ = " + chequier.getChequierId().getNumDemDchq() + 
            " and COD_ETAT_DDC = " + chequier.getCodEtatChqi() + 
            " and COD_OPER_OPER =" + codOperOper;
        Long nbre = (Long)jt.queryForObject(requete, Long.class);
        return nbre;
    }

    /**
     * Methode qui permet de verifier s'il la demande chèque comporte des chéquiers remis.
     * 
     * * @return Long
     */
    public

    Long getNbreChequierRemiParDemande(String numDemande) {
        jt = new JdbcTemplate(dataSource);
        String requete = 
            "select count(*) from chequier where NUM_DEM_DCHQ = " + 
            numDemande + " and cod_etat_chqi = 2";
        Long nbre = (Long)jt.queryForObject(requete, Long.class);
        return nbre;
    }

    /**
     * Methode qui permet d'extraire la liste des chéquiers à renouvler suite anomalie.
     * 
     * * @return List
     */
     public List getListChequiersARenouvler(Long codeStructure, String datDeb, String datFin) {
         jt = new JdbcTemplate(dataSource);

         String requete = 
             " select C.num_dem_dchq,C.num_chqi_chqi from chequier C, demande_cheque D "+ 
             " where  D.cod_strc_strc = " +  codeStructure + 
             " and D.NUM_DEM_DCHQ = C.NUM_DEM_DCHQ "+ 
             " and cod_etat_chqi = 5 and dat_recp_chqi is not null "+
             " and cod_etat_dchq in (3,5) and cod_oper_oper in(14,23) " +
             " and dat_recp_chqi between '"+datDeb+"' and '"+datFin +"'  " ;

         
         List rows = jt.queryForList(requete);
         return rows;

     }

    /**
     * Methode qui permet d'extraire la liste des chéquiers à non encore remis aux client.
     * 
     * * @return List
     */
     public List getListChequiersNonRemis(Long codeStructure, String datDeb, String datFin) {
         jt = new JdbcTemplate(dataSource);

         String requete = 
             " select C.num_dem_dchq,C.num_chqi_chqi from chequier C, demande_cheque D "+ 
             " where  D.cod_strc_strc = " +  codeStructure + 
             " and D.NUM_DEM_DCHQ = C.NUM_DEM_DCHQ "+ 
             " and cod_etat_chqi = 1 and dat_recp_chqi is not null "+
             " and cod_etat_dchq in (4,5) " +
             " and dat_recp_chqi between '"+datDeb+"' and '"+datFin +"'  " ;

         
         List rows = jt.queryForList(requete);
         return rows;

     }

    /**
     * Methode qui permet d'extraire la liste des chéquiers récupérés auprés de la clientèle
     * 
     * * @return List
     */
     public List getListChequiersRestitues(Long codeStructure, String datDeb, String datFin) {
         jt = new JdbcTemplate(dataSource);

         String requete = 
             " select C.num_dem_dchq,C.num_chqi_chqi from chequier C, demande_cheque D "+ 
             " where  D.cod_strc_strc = " +  codeStructure + 
             " and D.NUM_DEM_DCHQ = C.NUM_DEM_DCHQ "+ 
             " and cod_etat_chqi = 3 and dat_rest_chqi is not null "+
             " and cod_etat_dchq in (6,7) " +
             " and dat_rest_chqi between '"+datDeb+"' and '"+datFin +"'  " ;

         
         List rows = jt.queryForList(requete);
         return rows;

     }
   
       /**
     * Methode qui permet d'extraire la liste des chéquiers à detruire
     * 
     * * @return List
     */
     public List getListChequiersADetruire(Long codeStructure, String datDeb, String datFin) {
         jt = new JdbcTemplate(dataSource);

         String requete =   
         
        " select C.num_dem_dchq,C.num_chqi_chqi from chequier C, demande_cheque D "+ 
        " where ( D.cod_strc_strc = " +  codeStructure + 
        " and D.NUM_DEM_DCHQ = C.NUM_DEM_DCHQ "+ 
        " and D.num_dem_dchq in (select distinct num_dem_dchq from chequier where cod_etat_chqi in (3,5) ))"+
        " or (D.num_dem_dchq in (select distinct num_dem_dchq from chequier where (to_date(sysdate)-to_date(dat_recp_chqi))> 90  and cod_strc_strc = " + codeStructure + " and cod_etat_chqi = 1))";
 
     
    List rows = jt.queryForList(requete);
    return rows;

    }
    
    /**
    * Methode qui permet d'extraire la liste des chéquiers auto-confectionés
    *
    * * @return List
    */
    public List getListChequiersAutoConfectiones(Long codeStructure, String datDeb, String datFin) {
      jt = new JdbcTemplate(dataSource);

      String requete =   
      
     " select C.num_dem_dchq,C.num_chqi_chqi from chequier C, demande_cheque D "+ 
     " where  D.cod_strc_strc = " +  codeStructure + 
     " and D.NUM_DEM_DCHQ = C.NUM_DEM_DCHQ "+ 
     " and cod_etat_dchq in (4,5)"+
     " and  cod_etat_chqi = 1 and dat_recp_chqi is not null "+
     " and C.COD_CONF_CONF <> 'S' " +
     " and dat_recp_chqi between '"+datDeb+"' and '"+datFin +"'  " +
     " and D.COD_TLC_DCHQ =1 " ;
    
    
    List rows = jt.queryForList(requete);
    return rows;

    }
    
    
    /**
     * Methode qui permet de verifier s'il ya une demande chèque  certifié en cours.
     * 
     * * @return Long
     */
    public

    Long verifierDdeChqcertifieEnCours(Long codPrdCpti, Long codStrcCpti, Long NumCptiCpti) {
        jt = new JdbcTemplate(dataSource);
        String requete = 
            "select count(*) from demande_cheque where COD_STRC_CPTI	 = " + 
            codStrcCpti + " and COD_PRD_CPTI = " + codPrdCpti +  " and NUM_CPTI_CPTI = " + NumCptiCpti + 
            " and cod_etat_dchq = 2 ";
        
        Long nbre = (Long)jt.queryForObject(requete, Long.class);
        return nbre;
    }


    public List getListChequiersDetruis(Long codeStructure, String datDeb, String datFin) {
        jt = new JdbcTemplate(dataSource);

        String requete =   
        
       " select C.num_dem_dchq,C.num_chqi_chqi from chequier C, demande_cheque D "+ 
       " where ( D.cod_strc_strc = " +  codeStructure + 
       " and D.NUM_DEM_DCHQ = C.NUM_DEM_DCHQ "+ 
       " and D.num_dem_dchq in (select distinct num_dem_dchq from chequier where cod_etat_chqi = 4 ))"+
       " and dat_dest_chqi between '"+datDeb+"' and '"+datFin +"'  " ;
       
    
    
    List rows = jt.queryForList(requete);
    return rows;

    }
    
}
