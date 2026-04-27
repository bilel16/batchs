package com.bna.smile.model.domainecontratcompte.modificationdonneesclient.dao;


import java.util.Date;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;

import com.bna.commun.util.DateHandler;
import com.bna.smile.model.constant.Constants;
/**
 * classe permettant d'effectuer des recherches sur les personnes et leurs relations
 * @author Mdimagh Med Lassaad
 */
public class ModificationDonneeClientDAO {
    public ModificationDonneeClientDAO() {
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
     * Recherche les mineurs devenus majeurs
     * @param codeStructure
     * @param etatContrat
     * @param dateOuverture
     * @return
     */
    public List getListeDesMineursDevenusMajeurs(Long codeStructure, Date dateDuJour) {
        jt = new JdbcTemplate(dataSource);
        String requete = 
            new String("select v.cod_strc_strc codStrcStrc, v.cod_prd_prd codPrdPrd, v.num_ccpt_ccpt numCcptCcpt,p.num_seq_pers numSeqPers, p.cod_tpce_tpce  codTpceTpce, t.lib_tpce_tpce libTpceTpce, p.num_pce_pers numPcePers, p.nom_nom_pers nomNonPers, p.nom_prn_pers nomPrnPers ,to_char(p.dat_nais_pers,'DD/MM/YYYY') datNaisPers " +
            "from personne p, vue_personne_contrat v , type_piece t where " + 
            "(MONTHS_BETWEEN( '"+  DateHandler.dateToStr(dateDuJour) +"', p.dat_nais_pers  ) / 12 ) >= " + Constants.PARAM_AGE_MINEUR + 
            " and p.cod_catp_catp in ( 4 , 3, 52 )" + 
            " and v.cod_strc_strc = "+ codeStructure +  
            " and p.num_seq_pers = v.num_seq_pers " +
            " and p.cod_tpce_tpce = t.cod_tpce_tpce and v.cod_etat_ccpt = 'V'" );
        
        List list = (List)jt.queryForList(requete);
        return list;
    }
    
        /**
        * Recherche des personnes par le nom
        * @param nom
        * @param prenom
        * @return liste
        */
       public List getListePersonneParNom(String nom,String prenom) {
           jt = new JdbcTemplate(dataSource);
           StringBuffer requete = 
               new StringBuffer("select p.num_seq_pers numSeqPers, p.nom_nom_pers nomNomPers, p.nom_prn_pers nomPrnPers, p.LIB_SIGL_PERS libSiglPers, p.NOM_RS_PERS NomRsPers," +
               " p.cod_Tpce_Tpce codTpceTpce, t.lib_tpce_tpce libTpceTpce, p.num_pce_pers numPcePers "+
               " from personne p, type_piece t where p.cod_tpce_tpce = t.cod_tpce_tpce  ");
               if (nom != null && !nom.equals("")){
                requete.append( " and upper( p.nom_nom_pers) like  upper('%" +nom+"%') ");
               }
           
               if (prenom != null && !prenom.equals("")){
                requete.append(" and upper( p.nom_prn_pers) like  upper('%" +prenom+"%') ");  
               }
           
           List list = (List)jt.queryForList(requete.toString());
           return list;
       }
       
    /**
    * Recherche des personnes par la raison sociale
    * @param raisonSociale
    * @param sigle
    * @return liste
    */
    public List getListePersonneParRaisonSociale(String raisonSociale,String sigle) {
       jt = new JdbcTemplate(dataSource);
       StringBuffer requete = 
           new StringBuffer("select p.num_seq_pers numSeqPers, p.nom_nom_pers nomNomPers, p.nom_prn_pers nomPrnPers , p.LIB_SIGL_PERS libSiglPers, p.NOM_RS_PERS NomRsPers," +
           "  t.cod_Tpce_Tpce codTpceTpce, t.lib_tpce_tpce libTpceTpce, p.num_pce_pers numPcePers "+
           " from personne p, type_piece t where  p.cod_tpce_tpce = t.cod_tpce_tpce ");
           if (raisonSociale != null && !raisonSociale.equals("")){
            requete.append(" and upper( p.NOM_RS_PERS) like  upper('%" +raisonSociale +"%') ");
           }
           if (sigle != null && !sigle.equals("")){
            requete.append(" and upper( p.LIB_SIGL_PERS) like  upper('%" +sigle+"%') ");
           }
  
       List list = (List)jt.queryForList(requete.toString());
       return list;
    }
    
}
