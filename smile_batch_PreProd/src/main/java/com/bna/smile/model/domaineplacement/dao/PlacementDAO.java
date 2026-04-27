package com.bna.smile.model.domaineplacement.dao;


import java.util.Date;
import java.util.List;

import javax.sql.DataSource;

import org.apache.log4j.Logger;
import org.springframework.jdbc.core.JdbcTemplate;

import com.bna.commun.util.DateHandler;

public class PlacementDAO {
    private static final Logger logger = 
        Logger.getLogger(PlacementDAO.class);

    public PlacementDAO() {
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

   //fonction qui permette de retourner les règles de gestion d'un produit de placement
    public List getListRegleGestion(Long codPrdPlc) {
        jt = new JdbcTemplate(dataSource);

        String requete = 
            "select DUR_MIN_PLC, DUR_MAX_PLC, MNT_MIN_PLC, MNT_MAX_PLC, VAL_NOMI_PLC, LIB_ABR_PLC,LIB_STRV_PLC from PRODUIT_PLACEMENT " + 
            "where  COD_PRD_PLC = " + codPrdPlc ;
        
        List rows = jt.queryForList(requete);
        return rows;

    }
    
    
    /**
     * Methode qui permet de rettourner le nombre de demande en attente de traitement pour chaque cas.
     * 
     * * @return Long
     */
    public Long getNbreDemandeAlerte(String etatDemande, String codStrcstrc) {
        jt = new JdbcTemplate(dataSource);
        String requete = 
            "select count(*) from demande_decision where COD_ETAT_DEMD = " + 
             "'" + etatDemande + "'";
         
        if(!codStrcstrc.equals("")){
            requete = requete + " and cod_strc_strc = " + codStrcstrc;
        }
        Long nbre = (Long)jt.queryForObject(requete, Long.class);
        return nbre;
    }
    

    //fonction qui retourne les placements à liquider
     public List getListContratPlacementALiquider(Date dateLiquid,Long codeStructure) {
         jt = new JdbcTemplate(dataSource);

         String requete = "select * from CONTRAT_PLACEMENT " + "where to_char(DAT_ECHF_CPLA,'DD/MM/YYYY') = '" + 
                           DateHandler.dateToStr(dateLiquid) + "'" +
                           " and cod_etat_cpla = 'V'" +"cod_strc_strc = " + codeStructure ;
         
         List rows = jt.queryForList(requete);
         return rows;

     }
	 public boolean verifierLigneDetailBc(Long numBc, Long numPlc) {
        jt = new JdbcTemplate(dataSource);
        Long nbr = 
            (Long)jt.queryForObject("select count(*) from details_bc where num_bc_dbc = " + numBc + " and num_seq_cpla = " + numPlc ,
                                    Long.class);
        if(nbr > 0)
         return true;
        else return false;
    } 
    //fonction qui retourne la somme des intrerts servi pour un placement
    public Long getTotalInteretServi(Long numSeqCpla) {
        jt = new JdbcTemplate(dataSource);
        String requete = 
            "SELECT SUM(MONT_ISRV_ISRV) FROM INTERET_SERVI WHERE NUM_SEQ_CPLA = " + 
             numSeqCpla;
        Long interet = (Long)jt.queryForObject(requete, Long.class);
        return interet;
    }
    //fonction qui retourne les avances non remboursés d'un placement
     public List getListAvancesNonRemb(Long numSeqCpla) {
         jt = new JdbcTemplate(dataSource);

         String requete = "SELECT * FROM AVANC_REMB_LIQUID " + 
                          "WHERE COD_TOPR_ARL='AVAN' " + 
                          "AND DAT_REEL_ARL=null "+
                          "AND COD_ETAT_ARL='V'"+
                          "AND NUM_SEQ_CPLA =" +numSeqCpla;
         
         List listAvancesNonRemb = jt.queryForList(requete);
         return listAvancesNonRemb;

     }

    
    //fonction qui permette de retourner les produits de placement relatifs au contrat compte choisi
     public List getListProduitPlacement(Long codPrdPrd) {
         jt = new JdbcTemplate(dataSource);

         String requete = 
             "select M.COD_PRD_PLC, P.LIB_PRD_PRD  from MATRICE_PRODUIT_PLACEMENT M, PRODUIT P , PRODUIT_PLACEMENT PL" + 
             " where  M.COD_PRD_PRD = " + codPrdPrd + " and M.COD_PRD_PLC = P.COD_PRD_PRD and M.COD_PRD_PLC = PL.COD_PRD_PLC and PL.COD_ETAT_PLC = 'V'"  ;
         
         List rows = jt.queryForList(requete);
         return rows;

     }
     
    //fonction qui permette de retourner les structures relatives à une direction régionale
     public List getListStructureConcernes(Long codDirRegionale) {
         jt = new JdbcTemplate(dataSource);

         String requete = 
             "select COD_STRC_STRC  from STRUCTURE "  + 
             " where  COD_STRM_STRC = " + codDirRegionale + " and COD_STRC_STRC <>  " + codDirRegionale  ;
         
         List rows = jt.queryForList(requete);
         System.out.println(requete);
         return rows;

     }
     
    public Long getSequenceDemandeDecision() {
        jt = new JdbcTemplate(dataSource);
        Long numeroSequence = 
            (Long)jt.queryForObject("select SEQ_DEMANDE_DECISION.NEXTVAL from dual ", 
                                    Long.class);
        return numeroSequence;
    }


    public List getListAgencesPlacement() {
        jt = new JdbcTemplate(dataSource);

        String requete = 
            "select J.COD_STRC_STRC, to_char(J.DAT_JRN_JRN,'DD/MM/YYYY'), J.COD_DOM_DOMM  from JOURNEE_STRUCTURE_DOMAINE J" + 
            " where  J.COD_DOM_DOMM = 4  and (J.COD_STAT_JSD = 0 or J.COD_STAT_JSD = 3)and DAT_JRN_JRN in  "+
            " (select max(I.DAT_JRN_JRN) from JOURNEE_STRUCTURE_DOMAINE I "+
            " where I.COD_STRC_STRC = J.COD_STRC_STRC and I.COD_DOM_DOMM = 4)"+
            " and DAT_JRN_JRN in (select K.DAT_JRN_JRN from JOURNEE_STRUCTURE K "+
            " where J.COD_STRC_STRC = K.COD_STRC_STRC and K.COD_SOLD_JRN = 1)";
        
        //System.out.println(requete);
        logger.info(requete);
        List listAgencesPlacementLiq = jt.queryForList(requete);
        logger.info("la requete a ramené "+String.valueOf(listAgencesPlacementLiq.size()));
        

        return listAgencesPlacementLiq;

    }
    
    public List getListAgencesPlacementAb() {
        jt = new JdbcTemplate(dataSource);

        String requete = 
            "select J.COD_STRC_STRC, to_char(J.DAT_JRN_JRN,'DD/MM/YYYY'), J.COD_DOM_DOMM  from JOURNEE_STRUCTURE_DOMAINE J" + 
            " where  J.COD_DOM_DOMM = 4  and (J.COD_STAT_JSD = 0 or J.COD_STAT_JSD = 3)and DAT_JRN_JRN in  "+
            " (select max(I.DAT_JRN_JRN) from JOURNEE_STRUCTURE_DOMAINE I "+
            " where I.COD_STRC_STRC = J.COD_STRC_STRC and I.COD_DOM_DOMM = 4) order by J.COD_STRC_STRC ";
        
        //System.out.println(requete);
        logger.info(requete);
        List listAgencesPlacementLiq = jt.queryForList(requete);
        logger.info("la requete a ramené "+String.valueOf(listAgencesPlacementLiq.size()));
        

        return listAgencesPlacementLiq;

    }
    
    public Long getSequenceInteretSerrvi() {
        jt = new JdbcTemplate(dataSource);
        Long numeroSequence = 
            (Long)jt.queryForObject("select SEQ_INTERET_SERVI.NEXTVAL from dual ", 
                                    Long.class);
        return numeroSequence;
    }
    

    public boolean verifierRecuperationBc(Long numBc, Long numPlc) {
        jt = new JdbcTemplate(dataSource);
        Long nbr = 
            (Long)jt.queryForObject("select count(*) from details_bc where num_bc_dbc = " + numBc + " and num_seq_cpla = " + numPlc + " and date_reca_bc is not null" ,
                                    Long.class);
        if(nbr > 0)
         return true;
        else return false;
    } 
    
    /* Fonction qui retourne la situation mensuelle selon le code structure*/
    public List getSituationMensuelle(Long codeStructure,String dateComptable){
       jt = new JdbcTemplate(dataSource);


            String requete =
            "SELECT "+
            "CONTRAT_PLACEMENT.cod_strc_ccpt,"+
            "sum(CONTRAT_PLACEMENT.MONT_CAP_CPLA),"+
            "PRODUIT_PLACEMENT.LIB_PRD_PLC "+
            "FROM "+
            "CONTRAT_PLACEMENT,PRODUIT_PLACEMENT "+
            "WHERE "+ 
            "CONTRAT_PLACEMENT.COD_STRC_CCPT in (select COD_STRC_STRC from structure where COD_STRC_STRC="+codeStructure.toString()+" and COD_TSTR_TSTR=1 "+
            "UNION select COD_STRC_STRC from structure where COD_STRM_STRC="+codeStructure.toString()+" and COD_TSTR_TSTR=1) "+
            "and CONTRAT_PLACEMENT.COD_ETAT_CPLA = 'V'"+
            "and CONTRAT_PLACEMENT.COD_PRD_PLC = PRODUIT_PLACEMENT.COD_PRD_PLC "+
            "GROUP BY PRODUIT_PLACEMENT.LIB_PRD_PLC,CONTRAT_PLACEMENT.cod_strc_ccpt ";
            //System.out.println("****"+requete+"******");
        logger.info(requete);
        List situationMensuelle = jt.queryForList(requete);
        logger.info("la requete situation mensuelle a ramené "+String.valueOf(situationMensuelle.size()));
        return situationMensuelle;
    }
    /* Fonction qui retourne la situation mensuelle selon le code structure*/
    public List getSituationMensuelleInteret(Long codeStructure,String dateComptable){
       jt = new JdbcTemplate(dataSource);


            String requete =
            "SELECT "+
            "sum(INTERET_SERVI.MONT_IRC_ISRV),"+
            "sum(INTERET_SERVI.MONT_BRUT_ISRV),"+
            "sum(INTERET_SERVI.MONT_ISRV_ISRV),"+
            "PRODUIT_PLACEMENT.LIB_PRD_PLC," +
            "CONTRAT_PLACEMENT.cod_strc_ccpt "+
            "FROM "+
            "CONTRAT_PLACEMENT,INTERET_SERVI,PRODUIT_PLACEMENT "+
            "WHERE "+ 
            "CONTRAT_PLACEMENT.COD_STRC_CCPT in (select COD_STRC_STRC from structure where COD_STRC_STRC="+codeStructure.toString()+" and COD_TSTR_TSTR=1 "+
            "UNION select COD_STRC_STRC from structure where COD_STRM_STRC="+codeStructure.toString()+" and COD_TSTR_TSTR=1) "+
            "and INTERET_SERVI.NUM_SEQ_CPLA (+)= CONTRAT_PLACEMENT.NUM_SEQ_CPLA "+
            "and CONTRAT_PLACEMENT.COD_ETAT_CPLA = 'V'"+
            "and CONTRAT_PLACEMENT.COD_PRD_PLC = PRODUIT_PLACEMENT.COD_PRD_PLC "+
            "GROUP BY PRODUIT_PLACEMENT.LIB_PRD_PLC,CONTRAT_PLACEMENT.cod_strc_ccpt ";
            //System.out.println("****"+requete+"******");
        logger.info(requete);
        List situationMensuelle = jt.queryForList(requete);
        logger.info("la requete situation mensuelle a ramené "+String.valueOf(situationMensuelle.size()));
        return situationMensuelle;
    }
    
    //fonction qui retourne la somme des abonnements entre deux dates pour un contrat placement
    public Long getSommeAbonDate(String numSeqCpla,String dateDebut,String dateFin) {
        jt = new JdbcTemplate(dataSource);
        Long interet = Long.valueOf(0);
        String requete = 
            "SELECT SUM(nvl(MONT_ABPL_ABPL,0)) FROM abonnement_placement WHERE NUM_SEQ_CPLA = " + 
             numSeqCpla + " and DAT_DEB_ABPL >= '"+dateDebut+"' and DAT_DEB_ABPL < '"+dateFin+"'";
        if(jt.getFetchSize()>0){
            interet = (Long)jt.queryForObject(requete, Long.class);
        }
        return interet;
    }
    
    //fonction qui retourne la somme des abonnements pour l'année qui precède la date de l'interet servi
    public Long getSommeAbonAnnee(String numSeqCpla,String dateFin) {
           
        jt = new JdbcTemplate(dataSource);
        Long interet = Long.valueOf(0);
        String requete = 
            "SELECT nvl(MONT_SINT_ABPL,0)-decode(last_day(DAT_FIN_ABPL),DAT_FIN_ABPL,0,nvl(MONT_ABPL_ABPL,0)) FROM abonnement_placement WHERE NUM_SEQ_CPLA = " + 
             numSeqCpla + " and DAT_FIN_ABPL = '"+dateFin+"' and COD_PART_ABPL = 'P'";
        logger.info("requte pour la somme de ex tourne deja versé : "+requete);
        
        try{
            interet = (Long)jt.queryForObject(requete, Long.class);
            }catch(Exception ex){   
                logger.info("Aucun abonnement versé avant la date de l'interet servi");
            }
        return interet; 
    }


    public Long getSommeAbonAnneeExtourne(String numSeqCpla,String dateFin, String dateEcheance) {
           
        jt = new JdbcTemplate(dataSource);
        Long interet = Long.valueOf(0);
        String requete = 
            "SELECT SUM(nvl(MONT_ABPL_ABPL,0)) FROM abonnement_placement WHERE NUM_SEQ_CPLA = " + 
             numSeqCpla + " and DAT_FIN_ABPL > '"+dateFin+"' and DAT_FIN_ABPL <= '" + dateEcheance + "'"  +
             " and COD_ETAT_ABPL = 'T' and COD_TOPR_ABPL = 'S'" ;
        logger.info("reqeute pour la somme de extourne dans le cas de la liquidation  : "+requete);
        
        try{
            interet = (Long)jt.queryForObject(requete, Long.class);
            if (interet == null)
                interet = Long.valueOf(0);
                
        }catch(ClassCastException ex){   
            logger.info("Aucun abonnement versé après la date de l'interet servi");
        }
        return interet; 
    }
    
    
    
    public Long getSommeAbonAnneeExtourne(String numSeqCpla) {
           
        jt = new JdbcTemplate(dataSource);
        Long interet = Long.valueOf(0);
        String requete = 
            "SELECT SUM(nvl(MONT_ABPL_ABPL,0)) FROM abonnement_placement WHERE NUM_SEQ_CPLA = " + 
             numSeqCpla + " and COD_ETAT_ABPL = 'T' and COD_TOPR_ABPL = 'S'"  ;
        logger.info("reqeute pour la somme de extourne dans le cas de la liquidation  : "+requete);
        
        try{
            interet = (Long)jt.queryForObject(requete, Long.class);
            if (interet == null)
                interet = Long.valueOf(0);
        }catch(ClassCastException ex){   
            logger.info("Aucun abonnement versé après la date de l'interet servi");
        }
        return interet; 
    }
    
    
    
    public Long getSommeAbonAnneeExtournePreCompte(String numSeqCpla) {
           
        jt = new JdbcTemplate(dataSource);
        Long interet = Long.valueOf(0);
        String requete = 
            "SELECT SUM(nvl(MONT_ABPL_ABPL,0)) FROM abonnement_placement WHERE NUM_SEQ_CPLA = " + 
             numSeqCpla + " and COD_ETAT_ABPL = 'A' and COD_TOPR_ABPL = 'S'"  ;
        logger.info("reqeute pour la somme de extourne dans le cas de la liquidation à échéance d'un placement PRECOMPTE : "+requete);
        
        try{
            interet = (Long)jt.queryForObject(requete, Long.class);
            if (interet == null)
                interet = Long.valueOf(0);            
        }catch(ClassCastException ex){   
            logger.info("Pas d'abonnement pour ce placement précompté...");
        }
        return interet; 
    }



    
    public List getSituationMensuelleClient(Long codTpceTpce,String numPcePers){
       jt = new JdbcTemplate(dataSource);


            String requete =
            "SELECT "+
            "CONTRAT_PLACEMENT.cod_strc_ccpt,"+
            "sum(CONTRAT_PLACEMENT.MONT_CAP_CPLA),"+
            "PRODUIT_PLACEMENT.LIB_PRD_PLC "+
            "FROM "+
            "CONTRAT_PLACEMENT,PRODUIT_PLACEMENT,PERSONNE "+
            "WHERE "+ 
            "PERSONNE.COD_TPCE_TPCE="+codTpceTpce+
            " and PERSONNE.NUM_PCE_PERS=to_char('"+numPcePers+"')"+
            " and CONTRAT_PLACEMENT.NUM_SEQ_PERS =PERSONNE.NUM_SEQ_PERS "+
            "and CONTRAT_PLACEMENT.COD_ETAT_CPLA = 'V'"+
            "and CONTRAT_PLACEMENT.COD_PRD_PLC = PRODUIT_PLACEMENT.COD_PRD_PLC "+
            "GROUP BY PRODUIT_PLACEMENT.LIB_PRD_PLC,CONTRAT_PLACEMENT.cod_strc_ccpt ";
            //System.out.println("****"+requete+"******");
        logger.info(requete);
        List situationMensuelle = jt.queryForList(requete);
        logger.info("la requete situation mensuelle a ramené "+String.valueOf(situationMensuelle.size()));
        return situationMensuelle;
    }
    
    public List getSituationMensuelleClientInt(Long codTpceTpce,String numPcePers){
       jt = new JdbcTemplate(dataSource);


            String requete =
            "SELECT "+
            "CONTRAT_PLACEMENT.cod_strc_ccpt,"+
            "sum(INTERET_SERVI.MONT_IRC_ISRV),"+
            "sum(INTERET_SERVI.MONT_BRUT_ISRV),"+
            "sum(INTERET_SERVI.MONT_ISRV_ISRV),"+
            "PRODUIT_PLACEMENT.LIB_PRD_PLC "+
            "FROM "+
            "CONTRAT_PLACEMENT,INTERET_SERVI,PRODUIT_PLACEMENT,PERSONNE "+
            "WHERE "+ 
            "PERSONNE.COD_TPCE_TPCE="+codTpceTpce+
            " and PERSONNE.NUM_PCE_PERS=to_char('"+numPcePers+"')"+
            " and CONTRAT_PLACEMENT.NUM_SEQ_PERS =PERSONNE.NUM_SEQ_PERS "+
            "and INTERET_SERVI.NUM_SEQ_CPLA (+)= CONTRAT_PLACEMENT.NUM_SEQ_CPLA "+
            "and CONTRAT_PLACEMENT.COD_ETAT_CPLA = 'V'"+
            "and CONTRAT_PLACEMENT.COD_PRD_PLC = PRODUIT_PLACEMENT.COD_PRD_PLC "+
            "GROUP BY PRODUIT_PLACEMENT.LIB_PRD_PLC,CONTRAT_PLACEMENT.cod_strc_ccpt ";
            //System.out.println("****"+requete+"******");
        logger.info(requete);
        List situationMensuelle = jt.queryForList(requete);
        logger.info("la requete situation mensuelle a ramené "+String.valueOf(situationMensuelle.size()));
        return situationMensuelle;
    }
    
    public List getSituationMensuelleCategorieClient(String codeCategorie,String dateComptable,Long codeStructure){
       jt = new JdbcTemplate(dataSource); 


            String requete =
            "SELECT "+
            "CONTRAT_PLACEMENT.cod_strc_ccpt,"+
            "sum(CONTRAT_PLACEMENT.MONT_CAP_CPLA),"+
            "PRODUIT_PLACEMENT.LIB_PRD_PLC, "+
            "CATEGORIE_PERSONNE.LIB_CATP_CATP "+
            "FROM "+
            "CONTRAT_PLACEMENT,PRODUIT_PLACEMENT,PERSONNE,CATEGORIE_PERSONNE "+
            "WHERE "+ 
            "CATEGORIE_PERSONNE.COD_CATP_CATP ="+codeCategorie+
            "and CONTRAT_PLACEMENT.NUM_SEQ_PERS =PERSONNE.NUM_SEQ_PERS "+
            "and CONTRAT_PLACEMENT.COD_ETAT_CPLA = 'V'"+
            "and PERSONNE.COD_CATP_CATP=CATEGORIE_PERSONNE.COD_CATP_CATP "+
            "and CONTRAT_PLACEMENT.COD_PRD_PLC = PRODUIT_PLACEMENT.COD_PRD_PLC "+
            "and CONTRAT_PLACEMENT.COD_STRC_CCPT in (select COD_STRC_STRC from structure where COD_STRC_STRC="+codeStructure.toString()+" and COD_TSTR_TSTR=1 "+
            "UNION select COD_STRC_STRC from structure where COD_STRM_STRC="+codeStructure.toString()+" and COD_TSTR_TSTR=1) "+
            "GROUP BY PRODUIT_PLACEMENT.LIB_PRD_PLC,CATEGORIE_PERSONNE.LIB_CATP_CATP,CONTRAT_PLACEMENT.cod_strc_ccpt ";
            //System.out.println("****"+requete+"******");
        logger.info(requete);
        List situationMensuelle = jt.queryForList(requete);
        logger.info("la requete situation mensuelle a ramené "+String.valueOf(situationMensuelle.size()));
        return situationMensuelle;
    }
    
    public List getSituationMensuelleIntCategClt(String codeCategorie,String dateComptable,Long codeStructure){
       jt = new JdbcTemplate(dataSource); 


            String requete =
            "SELECT "+
            "CONTRAT_PLACEMENT.cod_strc_ccpt,"+
            "sum(INTERET_SERVI.MONT_IRC_ISRV),"+
            "sum(INTERET_SERVI.MONT_BRUT_ISRV),"+
            "sum(INTERET_SERVI.MONT_ISRV_ISRV),"+
            "PRODUIT_PLACEMENT.LIB_PRD_PLC, "+
            "CATEGORIE_PERSONNE.LIB_CATP_CATP "+
            "FROM "+
            "CONTRAT_PLACEMENT,INTERET_SERVI,PRODUIT_PLACEMENT,PERSONNE,CATEGORIE_PERSONNE "+
            "WHERE "+ 
            "CATEGORIE_PERSONNE.COD_CATP_CATP ="+codeCategorie+
            "and CONTRAT_PLACEMENT.NUM_SEQ_PERS =PERSONNE.NUM_SEQ_PERS "+
            "and CONTRAT_PLACEMENT.COD_ETAT_CPLA = 'V'"+
            "and INTERET_SERVI.NUM_SEQ_CPLA (+)= CONTRAT_PLACEMENT.NUM_SEQ_CPLA "+
            "and PERSONNE.COD_CATP_CATP=CATEGORIE_PERSONNE.COD_CATP_CATP "+
            "and CONTRAT_PLACEMENT.COD_PRD_PLC = PRODUIT_PLACEMENT.COD_PRD_PLC "+
            "and CONTRAT_PLACEMENT.COD_STRC_CCPT in (select COD_STRC_STRC from structure where COD_STRC_STRC="+codeStructure.toString()+" and COD_TSTR_TSTR=1 "+
            "UNION select COD_STRC_STRC from structure where COD_STRM_STRC="+codeStructure.toString()+" and COD_TSTR_TSTR=1) "+
            "GROUP BY PRODUIT_PLACEMENT.LIB_PRD_PLC,CATEGORIE_PERSONNE.LIB_CATP_CATP,CONTRAT_PLACEMENT.cod_strc_ccpt ";
            //System.out.println("****"+requete+"******");
        logger.info(requete);
        List situationMensuelle = jt.queryForList(requete);
        logger.info("la requete situation mensuelle a ramené "+String.valueOf(situationMensuelle.size()));
        return situationMensuelle;
    }
    
    
   
    public List getRecapMouvementPlacement(String dateMouvement,Long codeStructure){
       jt = new JdbcTemplate(dataSource);


            String requete =
        "SELECT "+
        "OPERATION.LIB_OPER_OPER,"+
        "OPERATION_MOY_PAY.COD_STRC_STRC,"+
        "OPERATION_MOY_PAY.NUM_MATR_USER,"+
        "OPERATION_MOY_PAY.MONT_DIN_OMP,"+
        "to_char(OPERATION_MOY_PAY.COD_STRC_STRC)||' '||lpad(to_char(OPERATION_MOY_PAY.COD_PRD_PRD),4,'0')||' '||lpad(to_char(OPERATION_MOY_PAY.NUM_CCPT_CCPT),6,'0'),"+
        "to_char(OPERATION_MOY_PAY.DAT_SYST_OMP, 'hh24:mi:ss'),"+
        "OPERATION_MOY_PAY.COD_OPER_OPER,"+
        "decode(OPERATION_MOY_PAY.COD_PRD_OMP,1001,OPERATION_MOY_PAY.NUM_MOYP_OMP) "+
        "FROM "+
        "SMILE.OPERATION_MOY_PAY,REFCLI.OPERATION "+ 
        "WHERE "+
        "OPERATION_MOY_PAY.DAT_OPER_OMP = to_date('"+dateMouvement+"','dd/mm/yyyy') "+
        "and OPERATION_MOY_PAY.COD_OPER_OPER in (298,300,301,302,303,309,311,317,318,321,331,613,616,617) "+
        "and OPERATION_MOY_PAY.COD_STRC_STRC in (select COD_STRC_STRC from structure where COD_STRC_STRC="+codeStructure.toString()+" and COD_TSTR_TSTR=1 UNION select COD_STRC_STRC from structure where COD_STRM_STRC="+codeStructure.toString()+" and COD_TSTR_TSTR=1) "+
        "and OPERATION_MOY_PAY.NUM_MATR_USER <> '9999' "+
        "and OPERATION_MOY_PAY.COD_OPER_OPER = OPERATION.COD_OPER_OPER "+
        "order by to_char(OPERATION_MOY_PAY.DAT_SYST_OMP, 'hh24:mi:ss')";
        logger.info(requete);
        List situationMensuelle = jt.queryForList(requete);
        logger.info("le récap mouvement placement a ramené "+String.valueOf(situationMensuelle.size()));
        return situationMensuelle;
    }
    
    /**
     * Methode qui permet de verouiller le batch lors de son execution.
     * * @return Long
     */
    public Long isBatchExec() {
        jt = new JdbcTemplate(dataSource);
        String requete = 
            "select count(*) from JOURNEE_STRUCTURE j where j.COD_STRC_STRC = 900 and j.COD_SOLD_JRN != 0 " ; 

        Long nbre = (Long)jt.queryForObject(requete, Long.class);
        return nbre;
    }
    public Long verifNumBC(Long numBC) {
        jt = new JdbcTemplate(dataSource);
        String requete = 
            "select count(*) FROM bon_de_caisse where "+numBC+" BETWEEN num_debc_bc and num_finc_bc " ; 

        Long nbre = (Long)jt.queryForObject(requete, Long.class);
        return nbre;
    }

    public Long verifIntervBC(Long numDebBC,Long numFinBC) {
        jt = new JdbcTemplate(dataSource);
        String requete = 
            "select count(*) FROM bon_de_caisse where (num_debc_bc BETWEEN "+
            +numDebBC+" and "+numFinBC+" ) or (num_finc_bc BETWEEN +"
            +numDebBC+" and "+numFinBC+" )";

        Long nbre = (Long)jt.queryForObject(requete, Long.class);
        return nbre;
    }
public Long getSeqBc() {
            jt = new JdbcTemplate(dataSource);
            String requete = 
                "select SEQ_BC.nextval from dual";
            Long nbre = (Long)jt.queryForObject(requete, Long.class);
            return nbre;
        }
        
        
    public List getSumCapitalByProduitPlc(String etat, String datedeb, String dateFin) {
        jt = new JdbcTemplate(dataSource);

        String requete = 
        //"SELECT c.cod_prd_plc,SUM(mont_cap_cpla) FROM contrat_placement c where cod_etat_cpla = 'V' and cod_prd_plc <> 1001 GROUP BY c.Cod_prd_plc ";
        "SELECT LIB_ABR_PLC,SUM(mont_cap_cpla) " + 
        "FROM contrat_placement c, produit_placement where " + 
        " c.cod_prd_plc = produit_placement.COD_PRD_PLC" ;
        if(etat.equals("V"))
            requete+= " and  cod_etat_cpla = 'V' " ;
        
        if(!datedeb.equals("") &&   !dateFin.equals("")){
            requete+= " and dat_val_cpla >= to_date('"+datedeb+"','dd/mm/yyyy') "+ " and dat_val_cpla <= to_date('"+dateFin+"','dd/mm/yyyy') ";            
        } 
        
        requete+= " and c.cod_prd_plc <> 1001  GROUP BY LIB_ABR_PLC ";       
       
        //System.out.println(requete);
        logger.info(requete);
        List listSumCapitalByProduitPlc = jt.queryForList(requete);
        logger.info("la requete a ramené "+String.valueOf(listSumCapitalByProduitPlc.size()));
        

        return listSumCapitalByProduitPlc;

    }
    
   
    public List getNbrPlcParProduitPlc(String etat, String datedeb, String dateFin) {
        jt = new JdbcTemplate(dataSource);

        String requete = 
        //"SELECT c.cod_prd_plc,SUM(mont_cap_cpla) FROM contrat_placement c where cod_etat_cpla = 'V' and cod_prd_plc <> 1001 GROUP BY c.Cod_prd_plc ";
        "SELECT LIB_ABR_PLC,count(*) " + 
        "FROM contrat_placement c, produit_placement where " + 
        " c.cod_prd_plc = produit_placement.COD_PRD_PLC" ;
        if(etat.equals("V"))
            requete+= " and  cod_etat_cpla = 'V' " ;
        
        if(!datedeb.equals("") &&   !dateFin.equals("")){
            requete+= " and dat_val_cpla >= to_date('"+datedeb+"','dd/mm/yyyy') "+ " and dat_val_cpla <= to_date('"+dateFin+"','dd/mm/yyyy') ";            
        } 
        
        requete+= " GROUP BY LIB_ABR_PLC ";      
        
        //System.out.println(requete);
        logger.info(requete);
        List listNbrPlcByProduitPlc = jt.queryForList(requete);
        logger.info("la requete a ramené "+String.valueOf(listNbrPlcByProduitPlc.size()));
        

        return listNbrPlcByProduitPlc;

    }
    
    public List getNbrPlcParAgence(String etat, String datedeb, String dateFin) {
        jt = new JdbcTemplate(dataSource);

        String requete = 
        //"SELECT c.cod_prd_plc,SUM(mont_cap_cpla) FROM contrat_placement c where cod_etat_cpla = 'V' and cod_prd_plc <> 1001 GROUP BY c.Cod_prd_plc ";
        "SELECT cod_strc_ccpt,count(*) " + 
        "FROM contrat_placement  " + 
        " " ;
        if(etat.equals("V"))
            requete+= " where  cod_etat_cpla = 'V' " ;
        
        if(etat.equals("V") && !datedeb.equals("") &&   !dateFin.equals("")){
            requete+= " and dat_val_cpla >= to_date('"+datedeb+"','dd/mm/yyyy') "+ " and dat_val_cpla <= to_date('"+dateFin+"','dd/mm/yyyy') ";            
        } 
        
        if(!etat.equals("V") && !datedeb.equals("") &&   !dateFin.equals("")){
            requete+= " where dat_val_cpla >= to_date('"+datedeb+"','dd/mm/yyyy') "+ " and dat_val_cpla <= to_date('"+dateFin+"','dd/mm/yyyy') ";   
        }
        
        requete+= " GROUP BY COD_STRC_CCPT ";      
        
        //System.out.println(requete);
        logger.info(requete);
        List listNbrPlcByProduitPlc = jt.queryForList(requete);
        logger.info("la requete a ramené "+String.valueOf(listNbrPlcByProduitPlc.size()));
        

        return listNbrPlcByProduitPlc;
    }
    
    
    
        public List getNbrPlcParProduitParAgence(String etat, String datedeb, String dateFin) {
            jt = new JdbcTemplate(dataSource);

            String requete = 
            //"SELECT c.cod_prd_plc,SUM(mont_cap_cpla) FROM contrat_placement c where cod_etat_cpla = 'V' and cod_prd_plc <> 1001 GROUP BY c.Cod_prd_plc ";
            "SELECT count(*),LIB_ABR_PLC,COD_STRC_CCPT " + 
            "FROM contrat_placement c, produit_placement p where " + 
            " c.cod_prd_plc = p.COD_PRD_PLC" ;
            if(etat.equals("V"))
                requete+= " and  cod_etat_cpla = 'V' " ;
            
            if(!datedeb.equals("") &&   !dateFin.equals("")){
                requete+= " and dat_val_cpla >= to_date('"+datedeb+"','dd/mm/yyyy') "+ " and dat_val_cpla <= to_date('"+dateFin+"','dd/mm/yyyy') ";            
            } 
            
            requete+= " GROUP BY p.LIB_ABR_PLC,c.COD_STRC_CCPT ";      
            
            //System.out.println(requete);
            logger.info(requete);
            List listNbrPlcByProduitPlc = jt.queryForList(requete);
            logger.info("la requete a ramené "+String.valueOf(listNbrPlcByProduitPlc.size()));
            

            return listNbrPlcByProduitPlc;
        }
    
    
        @SuppressWarnings("unchecked")
    	public String getTMMMoisDateVersementInteret(String dateVersementInteret) {
    		try {

    			Double tmm = null;
    			jt = new JdbcTemplate(dataSource);

    			String req =
    					" select val_tref_tref from CONDB.hist_taux_reference where COD_TREF_HTRE=1 and \r\n" + 
    					"dat_fin_htre=(select min(dat_fin_htre) from CONDB.hist_taux_reference where COD_TREF_HTRE=1 and  to_char(trunc(dat_fin_htre),'yyyy') =to_char(to_date('"+dateVersementInteret+"','dd/MM/yyyy'),'yyyy'))";

    			tmm = (Double) jt.queryForObject(req, Double.class);
    			if (tmm != null)
    				return tmm.toString();
    			else
    				return "";
    		} catch (Exception e) {
    			return "";
    		}
    	}

    

}
