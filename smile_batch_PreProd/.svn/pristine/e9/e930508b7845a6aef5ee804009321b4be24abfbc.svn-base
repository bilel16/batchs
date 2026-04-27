package com.bna.smile.model.domainecontratcompte.moyensPaiement.dao;

import java.util.Date;
import java.util.List;

import javax.sql.DataSource;

import org.apache.log4j.Logger;
import org.springframework.jdbc.core.JdbcTemplate;

import com.bna.smile.model.constant.Constants;

public class OppositionMoyPaiementDAO {
    
    public OppositionMoyPaiementDAO() {
    }
    
    private static final Logger logger = Logger.getLogger(OppositionMoyPaiementDAO.class);
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
     * Methode qui permet de retourner la dernier etat et date operation sur un moyen de paiement donner
     * * @return List
     */
    public List getDernierEtatMoyPaiement(String typeMoyenPaiement, String numMoyenPaiement, String codAgence, String codProduit, String numCcpt) {
        try {
            jt = new JdbcTemplate(dataSource);
            String requete = "select cod_etat_opmp,DAT_OPER_OPMP from " +
            "(select cod_etat_opmp,DAT_OPER_OPMP from OPPOSITION_MOYEN_PAIEMENT " +
            " where COD_MOYP_TMOY="+typeMoyenPaiement+" and NUM_MOYP_OPMP='"+numMoyenPaiement+"' and " +
            "COD_STRC_STRC="+codAgence+" and " +
            "COD_PRD_PRD="+codProduit+" and " +
            "NUM_CCPT_CCPT="+numCcpt+
            " order by dat_oper_opmp desc " +
            ")where rownum=1";
              
            logger.info(requete);
            List rows = jt.queryForList(requete);
            return rows;
        } catch (Exception e) {
            logger.error(e.getMessage());
            return null;

        }
    }
    
    public Date getDateChequeEnCirculation(String numCheque, String codAgence, String codProduit, String numCcpt) {
        try {
            jt = new JdbcTemplate(dataSource);
            String requete = "select DAT_REMI_CHQI from chequier" +
            " where COD_ETAT_CHQI="+Constants.COD_ETAT_CHQI_Delivre+" and "+
            numCheque+" between NUM_DEB_CHQI and (NUM_DEB_CHQI+NBR_CHQ_CHQI-1) and " +
            "COD_STRC_STRC="+codAgence+" and " +
            "COD_PRD_PRD="+codProduit+" and " +
            "NUM_CCPT_CCPT="+numCcpt;
            logger.info(requete);
            Date dateCir = (Date)jt.queryForObject(requete, Date.class);
            
            return dateCir;
        } catch (Exception e) {
            logger.error(e.getMessage());
            return null;

        }
    }
    
  public String getNatureCheque(String numCheque) {
        try {
            jt = new JdbcTemplate(dataSource);
            String requete = "select COD_CONF_CONF from chequier" +
            " where COD_ETAT_CHQI="+Constants.COD_ETAT_CHQI_Delivre+" and "+
            numCheque+" between NUM_DEB_CHQI and (NUM_DEB_CHQI+NBR_CHQ_CHQI-1)";
            logger.info(requete);
            String naturCheq = (String)jt.queryForObject(requete, String.class);
            
            return naturCheq;
        } catch (Exception e) {
            logger.error(e.getMessage());
            return null;

        }
    }

    public Date getDateTelecompensation(String typeMoyenPaiement, String numMoyenPaiement) {
        try {
            //à partir d'un autre module
              return null;
        } catch (Exception e) {
            logger.error(e.getMessage());
            return null;

        }
    }
    
    public Date getDateRejetCNP(String typeMoyenPaiement, String numMoyenPaiement) {
        try {
            //à partir d'un autre module
              return null;
        } catch (Exception e) {
            logger.error(e.getMessage());
            return null;

        }
    }
    
    public Date getDateLivretEnCirculation(String typeLivret, String numLivret, String codAgence, String codProduit, String numCcpt) {
        try {
              //à partir d'un autre module
              return new Date();
        } catch (Exception e) {
            logger.error(e.getMessage());
            return null;

        }
    }
    
    public Date getDateCIBEnCirculation(String numLivret, String codAgence, String codProduit, String numCcpt) {
        try {
              //à partir d'un autre module
              return new Date();
        } catch (Exception e) {
            logger.error(e.getMessage());
            return null;

        }
    }
    public Date getDateBcEnCirculation(String numBc, String codAgence, String codProduit, String numCcpt) {
        try {
            jt = new JdbcTemplate(dataSource);
            String requete = "select DAT_CRE_CPLA from contrat_placement" +
            " where NUM_BC_CPLA="+numBc+" and "+
            "COD_STRC_CCPT="+codAgence+" and " +
            "COD_PRD_CCPT="+codProduit+" and " +
            "NUM_CCPT_CCPT="+numCcpt;
            logger.info(requete);
            Date dateCir = (Date)jt.queryForObject(requete, Date.class);
            
            return dateCir;
        } catch (Exception e) {
            logger.error(e.getMessage());
            return null;

        }
    }
    public Date getDateBcPresentation(String numBc) {
        try {
            jt = new JdbcTemplate(dataSource);
            String requete = "select DATE_RECA_BC from details_bc" +
            " where NUM_BC_DBC="+numBc;
            logger.info(requete);
            Date datePres = (Date)jt.queryForObject(requete, Date.class);
            
            return datePres;
        } catch (Exception e) {
            logger.error(e.getMessage());
            return null;

        }
    }
}
