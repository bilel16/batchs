package com.bna.smile.model.domainecontratcompte.moyensPaiement.dao;

import java.util.Date;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;

import com.bna.smile.model.constant.Constants;

public class DemandeCarteDAO {

    public DemandeCarteDAO() {
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
    public Long getNumeroMandatParDemande(String numdem) {
        try {
            jt = new JdbcTemplate(dataSource);
            String requete = 
                "select distinct num_mand_mand from demande_carte_mandat_personne where num_dem_dcar= " + 
                numdem;
            Long numMandat = (Long)jt.queryForObject(requete, Long.class);
            return numMandat;
        } catch (Exception e) {            
            return null;

        }
    }

    /**
     * Methode qui permet de retourner la date derniere opération sur une demande de carte
     * * @return Date
     */
    public Date getDateDernièreOperationDemande(String numdem) {
        try {
            jt = new JdbcTemplate(dataSource);
            String requete = 
                "select max(dat_oper_dodc) from detail_oper_dem_cart where num_dem_dcar= " + 
                numdem;
            Date date = (Date)jt.queryForObject(requete, Date.class);
            return date;
        } catch (Exception e) {            
            return null;

        }
    }

    /**
     * Methode qui permet de retourner la date derniere opération sur une carte
     * * @return Date
     */
    public Date getDateDernièreOperationCarte(String numCart) {
        try {
            jt = new JdbcTemplate(dataSource);
            String codeBin = numCart.substring(0, 6);
            String numRestCart = numCart.substring(6);
            String requete = 
                "select max(dat_oper_doc) from detail_oper_carte where COD_BIN_TCAR=" + 
                codeBin + " and num_carb_carb= " + numRestCart;
            Date date = (Date)jt.queryForObject(requete, Date.class);
            if(date==null){
                requete = 
                    "select GREATEST(DAT_MODM_CARB,DAT_OPER_CARB) from carte_bancaire where COD_BIN_TCAR=" + 
                    codeBin + " and num_carb_carb= " + numRestCart;
                date = (Date)jt.queryForObject(requete, Date.class);
            }
           
            return date;
        } catch (Exception e) {            
            return null;

        }
    }

    /**
     * Methode qui permet de retourner la date demande de remplacement pour une carte donnée (retoune null si pas de demande de remplacement)
     * * @return Date
     */
    public Date getDateDemandeRemplacement(String numCart) {
        try {
            jt = new JdbcTemplate(dataSource);
            String requete = 
                "select dat_dem_dcar from demande_carte where num_car_dcar= " + 
                numCart + " and cod_etat_dcar in (" + 
                Constants.COD_ETAT_DCAR_DemandeRempl + "," + 
                Constants.COD_ETAT_DCAR_DemandeRemplValide + "," + 
                Constants.COD_ETAT_DCAR_CarteRemplacee + ")";
            Date date = (Date)jt.queryForObject(requete, Date.class);
            return date;
        } catch (Exception e) {            
            return null;

        }
    }
    /**
     * Methode qui permet de retourner la date demande modification pladfond pour une carte donnée (retoune null si pas de demande de modif plafond(en cours))
     * * @return Date
     */
    public Date getDateDemandeModifPlafond(String numCart) {
        try {
            jt = new JdbcTemplate(dataSource);
            String requete = 
                "select dat_dem_dcar from demande_carte where nvl(BOOL_MODP_DCAR,0)=1 and num_car_dcar= " + 
                numCart + " and cod_etat_dcar not in (" + 
                Constants.COD_ETAT_DCAR_Valider+","+Constants.COD_ETAT_DCAR_RejetDemande+","+Constants.COD_ETAT_DCAR_RejetDr
                +","+Constants.COD_ETAT_DCAR_RejetScm+","+Constants.COD_ETAT_DCAR_RejetScc+")";
            Date date = (Date)jt.queryForObject(requete, Date.class);
            return date;
        } catch (Exception e) {            
            return null;

        }
    }
    /**
     * Methode qui permet d'extaire une nouvelle carte confectionnée (etat crée) par numéro
     * * * @return Date creation
     */
    public Date getDateCreationNewCarte(String numCart) {
        try {
            jt = new JdbcTemplate(dataSource);
            String codeBin = numCart.substring(0, 6);
            String numRestCart = numCart.substring(6);
            String requete = 
                "select max(DAT_CRE_CARB) from carte_bancaire where COD_BIN_TCAR=" + 
                codeBin + " and num_carb_carb= " + numRestCart + 
                " and COD_ETAT_CARB=" + Constants.COD_ETAT_CARB_CarteCree;
            Date date = (Date)jt.queryForObject(requete, Date.class);
            return date;
        } catch (Exception e) {            
            return null;

        }
    }
    /* Methode qui permet d'extaire une carte par numéro
    * * * @return Date creation
    */

    public Date getDateCarte(String numCart) {
        try {
            jt = new JdbcTemplate(dataSource);
            String codeBin = numCart.substring(0, 6);
            String numRestCart = numCart.substring(6);
            String requete = 
                "select max(DAT_CRE_CARB) from carte_bancaire where COD_BIN_TCAR=" + 
                codeBin + " and num_carb_carb= " + numRestCart;
            Date date = (Date)jt.queryForObject(requete, Date.class);
            return date;
        } catch (Exception e) {            
            return null;

        }
    }

    /* Methode qui permet d'extaire le numéro de carte qui remplace une carte donnée
    * * * @return Date creation
    */

    public String getNewCarteRemplace(String numCart) {
        try {
            jt = new JdbcTemplate(dataSource);
            String codeBin = numCart.substring(0, 6);
            String numRestCart = numCart.substring(6);
            String requete = 
                "select concat(COD_BINR_CARB,NUM_CARR_CARB) from carte_bancaire where COD_BIN_TCAR=" + 
                codeBin + " and num_carb_carb= " + numRestCart;
            String newNumcarte = 
                (String)jt.queryForObject(requete, String.class);
            return newNumcarte;
        } catch (Exception e) {            
            return null;

        }
    }
    /* Methode qui permet d'extaire une carte par numéro
    * * * @return Date creation
    */

    public Date getDateCarteEnCirculation(String numCart, String codAgence, String codProduit, String numCcpt) {
        try {
            jt = new JdbcTemplate(dataSource);
            String codeBin = numCart.substring(0, 6);
            String numRestCart = numCart.substring(6);
            String requete = 
                "select max(DAT_CRE_CARB) from carte_bancaire where COD_BIN_TCAR=" + 
                codeBin + " and num_carb_carb= " + numRestCart+
                " and COD_ETAT_CARB=" + Constants.COD_ETAT_CARB_CarteRemise +" and " +
                "COD_STRC_STRC="+codAgence+" and " +
                "COD_PRD_PRD="+codProduit+" and " +
                "NUM_CCPT_CCPT="+numCcpt;
  
            Date date = (Date)jt.queryForObject(requete, Date.class);
            return date;
        } catch (Exception e) {            
            return null;

        }
    }
    
    /* Methode qui permet d'extaire la durée d'un type de carte
    * * * @return String durée carte
    */

    public String getDureeCarte(String typeCarte) {
        try {
            jt = new JdbcTemplate(dataSource);
            String requete = 
                "select NUM_DURE_TCAR from type_carte where COD_TCAR_TCAR=" + 
                typeCarte+ " and COD_ETAT_TCAR='V'";
            String duree = 
                (String)jt.queryForObject(requete, String.class);
            return duree;
        } catch (Exception e) {            
            return null;

        }
    }
    
    /* Methode qui permet d'extaire l'etat d'un type de carte ('V' valide , 'N' ne plus utilisé)
    * * * @return String etat carte
    */
    
    public String getEtatTypeCarte(String typeCarte) {
        try {
            jt = new JdbcTemplate(dataSource);
            String requete = 
                "select COD_ETAT_TCAR from type_carte where COD_TCAR_TCAR=" + 
                typeCarte;
            String etat = 
                (String)jt.queryForObject(requete, String.class);
            return etat;
        } catch (Exception e) {            
            return null;

        }
    }

    /* Methode qui permet d'extaire le type de compte sur un produit de compte
    * * * @return String durée carte
    */
      
    public String getTypeCptCarte(String codeProduit) {
        try {
            jt = new JdbcTemplate(dataSource);
            String requete = 
                "select to_number(decode(COD_GFAM_GP,'04','03',COD_GFAM_GP)) from produit where COD_PRD_PRD=" + 
                codeProduit;
            String typeCompte = 
                (String)jt.queryForObject(requete, String.class);
            return typeCompte;
        } catch (Exception e) {            
            return null;

        }
    }
    
    /* Methode qui permet d'extaire le lib ancien carte par numéro
    * * * @return Date creation
    */

    public String getNomAncCarte(String numCart) {
        try {
            jt = new JdbcTemplate(dataSource);
            String codeBin = numCart.substring(0, 6);
            String numRestCart = numCart.substring(6);
            String requete = 
                "select LIB_AINT_CART from carte_bancaire where COD_BIN_TCAR=" + 
                codeBin + " and num_carb_carb= " + numRestCart;
            String nom = (String)jt.queryForObject(requete, String.class);
            return nom;
        } catch (Exception e) {            
            return null;

        }
    }


}
