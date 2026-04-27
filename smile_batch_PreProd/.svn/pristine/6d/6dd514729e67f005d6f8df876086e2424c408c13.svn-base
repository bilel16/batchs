package com.bna.smile.model.domaineCB.dao;


import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;

public class CBDAO {

    public CBDAO() {
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
    public

    Long getNbreDemandeAlerte(String etatDemande) {
        jt = new JdbcTemplate(dataSource);
        String requete = 
            "select count(*) from demande_decision where COD_ETAT_DEMD = " + 
             "'" + etatDemande + "'";
        Long nbre = (Long)jt.queryForObject(requete, Long.class);
        return nbre;
    }
    
    
    //fonction qui permette de retourner les produits de placement relatifs au contrat compte choisi
     public List getListOperation() {
         jt = new JdbcTemplate(dataSource);

         String requete = 
             "select COD_OPER_OPER, LIB_OPER_OPER  from OPERATION order by cod_oper_oper" ;
         
         List rows = jt.queryForList(requete);
         return rows;

     }
     
     public List getListProduit(){
         jt = new JdbcTemplate(dataSource);

         String requete = "select COD_PRD_PRD, LIB_PRD_PRD  from PRODUIT UNION "+
                          "select null,'             ' from dual ";
         
         List rows = jt.queryForList(requete);
         return rows;
     }
     
    public List getListGroupe(){
        jt = new JdbcTemplate(dataSource);

        String requete = 
            "select COD_GRP_GRP, NOM_RS_GRP  from groupe order by cod_grp_grp ";
        
        List rows = jt.queryForList(requete);
        return rows;
    }
    
    public List getListOrganisme(){
        jt = new JdbcTemplate(dataSource);

        String requete = 
            "select COD_GRP_GRP	, LIB_GRP_GRP  from GROUPE_CRITERE where ETA_VALD_GRP='V' order by cod_grp_grp ";
        
        List rows = jt.queryForList(requete);
        return rows;
    }
    
    /**
     * Methode qui permet de retourner un Vector contenant le type de 
     */
    
    public int getCBGeneraleOperPrd(long cod_oper, long cod_prd){
        int res;
        jt = new JdbcTemplate(dataSource);
        String requete;
        if (cod_prd==-1)
        {
            requete = "SELECT count(distinct cb.num_seq_cond) FROM condition_banque cb,affecter_condition aff,detail_condition det " + 
            "               WHERE aff.num_seq_cond = cb.num_seq_cond " + 
            "               AND det.num_seq_cond = cb.num_seq_cond " + 
            "               AND cb.cod_tcnd_tcnd = 1 " + 
            "               AND trunc(nvl(aff.date_fval_affc,   sysdate)) >= trunc(sysdate) " + 
            "               AND cb.flag_val_cond = 'V'" + 
            "               AND trunc(nvl(aff.DATE_DVAL_AFFC,sysdate))<=trunc(sysdate-1)  " + 
            "               AND aff.cod_oper_oper ="+cod_oper + 
            "               AND aff.cod_prd_prd is null";
        }
        else
        {
            requete = "SELECT count(distinct cb.num_seq_cond) FROM condition_banque cb,affecter_condition aff,detail_condition det " + 
            "               WHERE aff.num_seq_cond = cb.num_seq_cond " + 
            "               AND det.num_seq_cond = cb.num_seq_cond " + 
            "               AND cb.cod_tcnd_tcnd = 1 " + 
            "               AND trunc(nvl(aff.date_fval_affc,   sysdate)) >= trunc(sysdate) " + 
            "               AND cb.flag_val_cond = 'V'" + 
            "               AND trunc(nvl(aff.DATE_DVAL_AFFC,sysdate))<=trunc(sysdate-1)  " + 
            "               AND aff.cod_oper_oper ="+cod_oper + 
            "               AND aff.cod_prd_prd ="+cod_prd;
        }
        
     
        Long nbre = (Long)jt.queryForObject(requete, Long.class);
        
        if (nbre.intValue()!=0) {
            //Il existe unr condition generale avec le produit ----> lancement de l etat operation+produit
            res=1;
        }//if
        else{
            //On verifie le couple operation et le groupe produit auquel appartient le produit en paramètre
            requete ="SELECT count(distinct cb.num_seq_cond) FROM condition_banque cb,affecter_condition aff,detail_condition det " +
            "WHERE aff.num_seq_cond = cb.num_seq_cond  AND det.num_seq_cond = cb.num_seq_cond " + "AND cb.cod_tcnd_tcnd = 1  " +
            "AND nvl(aff.date_fval_affc,   sysdate) >= sysdate  AND cb.flag_val_cond = 'V' and  trunc(nvl(aff.DATE_DVAL_AFFC,sysdate))<=trunc(sysdate-1)  " +
            "AND aff.cod_oper_oper =" +cod_oper +
            "AND (aff.COD_GP_GP,aff.COD_SFAM_GP,aff.COD_FAM_GP) =(select COD_GFAM_GP,COD_SFAM_SFP,COD_FAM_FAM FROM PRODUIT WHERE COD_PRD_PRD="+ cod_prd+")";
            
            nbre = (Long)jt.queryForObject(requete, Long.class);
            
            if (nbre.intValue()!=0){
                //Il existe une condition generale avec le groupe ----> lancement de l etat operation+groupeproduit
                 res=2;
            }//if
            else{
                //On verifie le couple operation et la sous famille produit auquel appartient le produit en paramètre
                requete="SELECT count(distinct cb.num_seq_cond)FROM condition_banque cb,affecter_condition aff,detail_condition det " +
                " WHERE aff.num_seq_cond = cb.num_seq_cond AND det.num_seq_cond = cb.num_seq_cond AND cb.cod_tcnd_tcnd = 1 " +
                " AND nvl(aff.date_fval_affc,   sysdate) >= sysdate AND cb.flag_val_cond = 'V' AND trunc(nvl(aff.DATE_DVAL_AFFC,sysdate))<=trunc(sysdate-1) " +
                " AND aff.cod_oper_oper ="+cod_oper+
                " AND (aff.COD_FAM_SFAM,aff.COD_SFAM_SFAM) = (select COD_SFAM_SFP,COD_FAM_FAM FROM PRODUIT WHERE COD_PRD_PRD="+ cod_prd+")";
                
                nbre = (Long)jt.queryForObject(requete, Long.class);
                if (nbre.intValue()!=0){
                    //Il existe une condition generale avec la sous famille produit ----> lancement de l etat operation+sousfamilleproduit
                     res=3;
                }//if
                else{
                    //On verifie le couple operation et la famille produit auquel appartient le produit en paramètre
                    requete ="SELECT count(distinct cb.num_seq_cond) FROM condition_banque cb, affecter_condition aff, detail_condition det" +
                            " WHERE aff.num_seq_cond = cb.num_seq_cond " +
                            " AND det.num_seq_cond = cb.num_seq_cond " +
                            " AND cb.cod_tcnd_tcnd = 1 " +
                            " AND nvl(aff.date_fval_affc,   sysdate) >= sysdate " +
                            " AND cb.flag_val_cond = 'V' " +
                            " AND trunc(nvl(aff.DATE_DVAL_AFFC,sysdate))<=trunc(sysdate-1) " +
                            " AND aff.cod_oper_oper ="+cod_oper+
                            " AND aff.COD_FAM_FAM =(select COD_FAM_FAM FROM PRODUIT WHERE COD_PRD_PRD="+ cod_prd+")";
                    
                    nbre = (Long)jt.queryForObject(requete, Long.class);           
                    if (nbre.intValue()!=0){
                        //il existe une condition generale avec la famille produit ----> lancement de l etat operation+familleproduit
                         res=4;
                    }
                    else
                    res=0;
                }
                
            }
        }//else
        
        return res;
    }
}
         