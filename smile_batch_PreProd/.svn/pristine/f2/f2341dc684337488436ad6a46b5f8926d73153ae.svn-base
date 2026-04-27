package com.bna.smile.model.pilotage.dao;


import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;

public class PilotageDAO {
    public PilotageDAO() {
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

    public List getStatContratCptDepot(Long codeStructure, Long typePiece, 
                                     String numPiece,String etat) {
        jt = new JdbcTemplate(dataSource);
        String requete = 
            new String("select count(*),SUM(MONT_SOLD_CCPT) ,SUM(MONT_AUT_CCPT) from CONTRAT_CPT,PERSONNE where CONTRAT_CPT.COD_ETAT_CCPT='"+etat +"'"+
                      
                       " AND CONTRAT_CPT.cod_prd_prd not in(121,105,111,177) "+
                       " AND CONTRAT_CPT.NUM_SEQ_PERS=PERSONNE.NUM_SEQ_PERS"  + 
                       " AND PERSONNE.COD_TPCE_TPCE="+typePiece+
                       " AND PERSONNE.NUM_PCE_PERS='"+numPiece+"'");

        List listDem = (List)jt.queryForList(requete);
        return listDem;
    }
    public List getStatContratCptEpargne( Long typePiece, 
                                     String numPiece) {
        jt = new JdbcTemplate(dataSource);
        String requete = 
            new String("select count(*),SUM(MONT_SOLD_CCPT)  from CONTRAT_CPT,PERSONNE where CONTRAT_CPT.COD_ETAT_CCPT='V'" + 
                      
                       " AND CONTRAT_CPT.cod_prd_prd = 121 "+
                       " AND CONTRAT_CPT.NUM_SEQ_PERS=PERSONNE.NUM_SEQ_PERS"  + 
                       " AND PERSONNE.COD_TPCE_TPCE="+typePiece+
                       " AND PERSONNE.NUM_PCE_PERS='"+numPiece+"'");

        List listDem = (List)jt.queryForList(requete);
        return listDem;
    }
    public List getStatContratCptEpargneLie(Long typePiece, 
                                     String numPiece) {
        jt = new JdbcTemplate(dataSource);
        String requete = 
            new String("select count(*),SUM(MONT_SOLD_CCPT)  from CONTRAT_CPT,PERSONNE where CONTRAT_CPT.COD_ETAT_CCPT='V'" + 
                      
                       " AND CONTRAT_CPT.cod_prd_prd  in(105,111,177) "+
                       " AND CONTRAT_CPT.NUM_SEQ_PERS=PERSONNE.NUM_SEQ_PERS"  + 
                       " AND PERSONNE.COD_TPCE_TPCE="+typePiece+
                       " AND PERSONNE.NUM_PCE_PERS='"+numPiece+"'");

        List listDem = (List)jt.queryForList(requete);
        return listDem;
    }
    public Long getNombreDemChq(Long codeStructure, Long typePiece, 
                                     String numPiece) {
        jt = new JdbcTemplate(dataSource);
        String requete = 
            new String("select  count(*) nombre  from DEMANDE_CHEQUE,CONTRAT_CPT,PERSONNE  where " +
            " DEMANDE_CHEQUE.COD_ETAT_DCHQ!=6"    +
            " and DEMANDE_CHEQUE.cod_strc_strc=CONTRAT_CPT.cod_strc_strc"+
            " and DEMANDE_CHEQUE.NUM_CCPT_CCPT=CONTRAT_CPT.NUM_CCPT_CCPT"+
            " and DEMANDE_CHEQUE.COD_PRD_PRD=CONTRAT_CPT.COD_PRD_PRD"+
            " and CONTRAT_CPT.NUM_SEQ_PERS=PERSONNE.NUM_SEQ_PERS"+
            " and PERSONNE.COD_TPCE_TPCE="+typePiece+
            " and PERSONNE.NUM_PCE_PERS='"+numPiece+"'");
        Long nombre = (Long)jt.queryForLong(requete);
        return nombre;
    }
    public Long getNombreDemCarte(Long codeStructure, Long typePiece, 
                                     String numPiece) {
        jt = new JdbcTemplate(dataSource);
        String requete = 
            new String("select  count(*) nombre  from DEMANDE_CARTE,PERSONNE,CONTRAT_CPT  where " +
            " DEMANDE_CARTE.COD_ETAT_DCAR!=7"    +
            " and DEMANDE_CARTE.cod_strc_strc=CONTRAT_CPT.cod_strc_strc"+
            " and DEMANDE_CARTE.NUM_CCPT_CCPT=CONTRAT_CPT.NUM_CCPT_CCPT"+
            " and DEMANDE_CARTE.COD_PRD_PRD=CONTRAT_CPT.COD_PRD_PRD"+
            " and CONTRAT_CPT.NUM_SEQ_PERS=PERSONNE.NUM_SEQ_PERS"+
            " and PERSONNE.COD_TPCE_TPCE="+typePiece+
            " and PERSONNE.NUM_PCE_PERS='"+numPiece+"'");
        Long nombre = (Long)jt.queryForLong(requete);
        return nombre;
    }
    public Long getNombreOPP(Long codeStructure, Long typePiece, 
                                     String numPiece) {
        jt = new JdbcTemplate(dataSource);
        String requete = 
            new String("select  count(*) nombre  from OPPOSITION_MOYEN_PAIEMENT,PERSONNE,CONTRAT_CPT  where " +
            " OPPOSITION_MOYEN_PAIEMENT.COD_ETAT_OPMP='O'"    +
            " and OPPOSITION_MOYEN_PAIEMENT.cod_strc_strc=CONTRAT_CPT.cod_strc_strc"+
            " and OPPOSITION_MOYEN_PAIEMENT.NUM_CCPT_CCPT=CONTRAT_CPT.NUM_CCPT_CCPT"+
            " and OPPOSITION_MOYEN_PAIEMENT.COD_PRD_PRD=CONTRAT_CPT.COD_PRD_PRD"+
            " and CONTRAT_CPT.NUM_SEQ_PERS=PERSONNE.NUM_SEQ_PERS"+
            " and PERSONNE.COD_TPCE_TPCE="+typePiece+
            " and PERSONNE.NUM_PCE_PERS='"+numPiece+"'");
        Long nombre = (Long)jt.queryForLong(requete);
        return nombre;
    }
    public Long getNombreInter( Long typePiece, 
                                     String numPiece) {
        jt = new JdbcTemplate(dataSource);
        String requete = 
            new String("select  count(*) nombre  from INTERDIT_CHQ  where " +
            " INTERDIT_CHQ.STATUS='O'"    +
            " and INTERDIT_CHQ.TYPE_PIECE="+typePiece+
            " and INTERDIT_CHQ.NUM_PIECE='"+numPiece+"'");
        Long nombre = (Long)jt.queryForLong(requete);
        return nombre;
    }
    public List getStatInterdict( Long typePiece, 
                                     String numPiece) {
        jt = new JdbcTemplate(dataSource);
        String requete = 
            new String("select  count(*),DATE_INTERDICTION   from INTERDIT_CHQ  where " +
            " INTERDIT_CHQ.STATUS='O'"    +
            " and INTERDIT_CHQ.TYPE_PIECE="+typePiece+
            " and INTERDIT_CHQ.NUM_PIECE='"+numPiece+"'");

        List listDem = (List)jt.queryForList(requete);
        return listDem;
    }
    public List getStatEngagement(Long codeStructure, Long typePiece, 
                                     String numPiece) {
        jt = new JdbcTemplate(dataSource);
        String requete = 
            new String("select count(*),SUM(MONT_AECH_CRED)  from CREDITCCI,PERSONNE where " +
                       "CREDITCCI.COD_ETAT_ETC=1" + 
                       " AND CREDITCCI.NUM_SEQ_PERS=PERSONNE.NUM_SEQ_PERS"  + 
                       " AND PERSONNE.COD_TPCE_TPCE="+typePiece+
                       " AND PERSONNE.NUM_PCE_PERS='"+numPiece+"'");

        List listDem = (List)jt.queryForList(requete);
        return listDem;
    }
    public List getStatPlacement(Long codeStructure, Long typePiece, 
                                     String numPiece) {
        jt = new JdbcTemplate(dataSource);
        String requete = 
            new String("select count(*),SUM(MONT_ACTU_CPLA)  from CONTRAT_PLACEMENT,PERSONNE where " +
                       " CONTRAT_PLACEMENT.COD_ETAT_CPLA='V'" + 
                       " AND CONTRAT_PLACEMENT.NUM_SEQ_PERS=PERSONNE.NUM_SEQ_PERS"  + 
                       " AND PERSONNE.COD_TPCE_TPCE="+typePiece+
                       " AND PERSONNE.NUM_PCE_PERS='"+numPiece+"'");

        List listDem = (List)jt.queryForList(requete);
        return listDem;
    }
  
}
