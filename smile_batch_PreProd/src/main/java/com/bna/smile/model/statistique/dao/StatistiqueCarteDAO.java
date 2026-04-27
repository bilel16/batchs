package com.bna.smile.model.statistique.dao;

import java.util.Date;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;

import com.bna.commun.util.DateHandler;
import com.bna.smile.model.constant.Constants;

public class StatistiqueCarteDAO {
    public StatistiqueCarteDAO() {
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
     * Nombre de  carte banquaire demandées et validées
     * @param codeStructure
     * @param etatContrat
     * @param dateOuverture
     * @return nombre de souscription
     * @author Mdimagh med Lassaad
     */
    public List getNombreCarteDemande(Long codeStructure, String etatContrat, 
                                      Date dateOuverture) {
        jt = new JdbcTemplate(dataSource);
        String requete = 
            new String("select d.COD_TCAR_TCAR, t.LIB_TCAR_TCAR, count(*) nombre  from demande_carte d , type_carte t " + 
                       "where cod_strc_strc = " + codeStructure + 
                       " and COD_ETAT_DCAR = '"+etatContrat+"' " + " and to_char(DAT_DEM_DCAR,'DD/MM/YYYY') = '" + 
                       DateHandler.dateToStr(dateOuverture) + 
                       "' and d.COD_TCAR_TCAR = t.COD_TCAR_TCAR " + 
                       " group by d.COD_TCAR_TCAR, t.LIB_TCAR_TCAR");
        List list = (List)jt.queryForList(requete);
        return list;
    }
    /**
     * Nombre de carte par type non encore validés (en attente de validation agence etat 1/2/4/31/33/10)
     * @param codeStructure
     * @param etatContrat
     * @param dateOuverture
     * @return
     */
    public List getListeNombreCarteDemandeNonValide(Long codeStructure,Date dateOuverture) {
        jt = new JdbcTemplate(dataSource);
        String requete = 
            new String("select d.COD_TCAR_TCAR, t.LIB_TCAR_TCAR, count(*) nombre  from demande_carte d , type_carte t " + 
                       "where cod_strc_strc = " + codeStructure + 
                       " and COD_ETAT_DCAR  IN ("+ Constants.COD_ETAT_DCAR_Attente +"," +
                       Constants.COD_ETAT_DCAR_AttenteGarantie + "," +
                       Constants.COD_ETAT_DCAR_PrevaliderDR + ")" +
                       Constants.COD_ETAT_DCAR_PrevaliderScm + ")" +
                       Constants.COD_ETAT_DCAR_PrevaliderScc + ")" +
                       Constants.COD_ETAT_DCAR_DemandeRempl + ")" +
                       " and to_char(DAT_DEM_DCAR,'DD/MM/YYYY') = '" + 
                       DateHandler.dateToStr(dateOuverture) + 
                       "' and d.COD_TCAR_TCAR = t.COD_TCAR_TCAR " + 
                       " group by d.COD_TCAR_TCAR, t.LIB_TCAR_TCAR");
        List list = (List)jt.queryForList(requete);
        return list;
    }
    
    
    /**
     * Nombre de  carte bancaires reçus
     * @param codeStructure
     * @param dateOuverture
     * @return nombre de cartes par type
     * @author Mdimagh med Lassaad
     */
    public List getListeNombreCarteRecus(Long codeStructure, Date dateOuverture) {
        jt = new JdbcTemplate(dataSource);
        String requete = 
            new String("select d.COD_TCAR_TCAR, t.LIB_TCAR_TCAR, count(*) nombre  from demande_carte d , type_carte t " + 
                       "where cod_strc_strc = " + codeStructure + 
                       "  and to_char(DAT_RECP_DCAR,'DD/MM/YYYY') = '" + 
                       DateHandler.dateToStr(dateOuverture) + 
                       "' and d.COD_TCAR_TCAR = t.COD_TCAR_TCAR " + 
                       " group by d.COD_TCAR_TCAR, t.LIB_TCAR_TCAR");
        List list = (List)jt.queryForList(requete);
        return list;
    }
    
    /**
     * Nombre de  carte bancaires délivrées
     * @param codeStructure
     * @param dateOuverture
     * @return nombre de cartes par type
     * @author Mdimagh med Lassaad
     */
    public List getListeNombreCarteDelivrees(Long codeStructure, Date dateOuverture) {
        jt = new JdbcTemplate(dataSource);
        String requete = 
            new String("select d.COD_TCAR_TCAR, t.LIB_TCAR_TCAR, count(*) nombre  from demande_carte d , type_carte t " + 
                       "where cod_strc_strc = " + codeStructure + 
                       " and COD_ETAT_DCAR = '"+Constants.COD_ETAT_DCAR_CarteRemis+"' " + " and to_char(DAT_REMI_DCAR,'DD/MM/YYYY') = '" + 
                       DateHandler.dateToStr(dateOuverture) + 
                       "' and d.COD_TCAR_TCAR = t.COD_TCAR_TCAR " + 
                       " group by d.COD_TCAR_TCAR, t.LIB_TCAR_TCAR");
        List list = (List)jt.queryForList(requete);
        return list;
    }
    
    
    /**
     * Nombre de  cartes bancaires annulées (on a annulé leurs renouvellement)
     * @param codeStructure
     * @param dateOuverture
     * @return nombre de cartes par type
     * @author Mdimagh med Lassaad
     */
    public List getListeNombreCarteAnnulees(Long codeStructure, Date dateOuverture) {
        jt = new JdbcTemplate(dataSource);
        String requete = 
            new String("select d.COD_BIN_TCAR, t.LIB_TCAR_TCAR, count(*) nombre  from carte_bancaire d , type_carte t " + 
                       "where cod_strc_strc = " + codeStructure + 
                       " and COD_ETAT_CARB = '"+Constants.COD_ETAT_CARB_CarteRemise+"' " + " and to_char(DAT_OPER_CARB,'DD/MM/YYYY') = '" + 
                       DateHandler.dateToStr(dateOuverture) + 
                       "' and BOOL_ANNL_CARB = 1 "+
                       " and d.COD_BIN_TCAR = t.COD_TCAR_TCAR " + 
                       " group by d.COD_BIN_TCAR, t.LIB_TCAR_TCAR");
        List list = (List)jt.queryForList(requete);
        return list;
    }


    /**
     * Nombre des cartes bancaires rejetées (par le chef d'agence ou elle est mal confectionnée)
     * @param codeStructure
     * @param dateOuverture
     * @return nombre de cartes par type
     * @author Mdimagh med Lassaad
     */
    public List getListeNombreCarteRejetees(Long codeStructure, Date dateOuverture) {
        jt = new JdbcTemplate(dataSource);
        String requete = 
            new String("select d.COD_BIN_TCAR, t.LIB_TCAR_TCAR, count(*) nombre  from carte_bancaire d , type_carte t " + 
                       "where cod_strc_strc = " + codeStructure + 
                       " and COD_ETAT_CARB in ("+Constants.COD_ETAT_DCAR_RejetDemande+" , "+ Constants.COD_ETAT_DCAR_RejetDelivreCarte + " ) " + " and to_char(DAT_OPER_CARB,'DD/MM/YYYY') = '" + 
                       DateHandler.dateToStr(dateOuverture) + 
                       "' and d.COD_BIN_TCAR = t.COD_TCAR_TCAR " + 
                       " group by d.COD_BIN_TCAR, t.LIB_TCAR_TCAR");
        List list = (List)jt.queryForList(requete);
        return list;
    }
}
