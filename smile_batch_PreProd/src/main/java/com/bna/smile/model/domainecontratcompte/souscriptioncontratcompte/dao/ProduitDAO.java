package com.bna.smile.model.domainecontratcompte.souscriptioncontratcompte.dao;

import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;

import com.bna.smile.model.domainecontratcompte.souscriptioncontratcompte.model.ParamPers;
import com.bna.smile.model.domainecontratcompte.souscriptioncontratcompte.model.PersProduit;

public class ProduitDAO {


    protected String sqlQuery;
    protected JdbcTemplate jt;
    protected DataSource dataSource;


    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public void setSqlQuery(String sqlQuery) {
        this.sqlQuery = sqlQuery;
    }


    public List getList(ParamPers paramPers) {
        jt = new JdbcTemplate(dataSource);
        String codNatPcon;
        String codResPcon = "I";
        if (paramPers.getCodPaysPays() == null) {
            codNatPcon = "I";
        }
        if (paramPers.getCodPaysPays().equalsIgnoreCase("TUN")) {
            codNatPcon = "T";
        } else
            codNatPcon = "E";


        if (paramPers.getBoolResPers() == 0) {
            codResPcon = "N";
        } else if (paramPers.getBoolResPers() == 1) {
            codResPcon = "R";
        }

        Integer v_age = paramPers.getAge();
        String req = 
            "SELECT  liste_document.COD_PRD_PRD,liste_document.cod_pop_pcon,produit.lib_prd_prd,liste_document.LIB_DOC_LDOC,produit.cod_tprd_prd,produit.COD_SFAM_SFP,produit.COD_GFAM_GP   FROM liste_document,produit where    COD_POP_PCON in \n" + 
            "		(\n" + "		select COD_POP_PCON from pop_concern where \n" + 
            "		(COD_NAT_PCON = '" + codNatPcon + 
            "' or COD_NAT_PCON = 'I') and \n" + 
            "               (COD_RES_PCON = '" + codResPcon + 
            "' or COD_RES_PCON = 'I') and\n" + 
            "               (COD_CATP_CATP = '" + paramPers.getCodCatpCatp() + 
            "') and\n" + "		(COD_FJ_FJ     = " + paramPers.getCodFjFj() + 
            ")\n" + "		and " + v_age + 
            " between  NVL(NBR_AGMI_PCON,0) and NVL(NBR_AGMA_PCON,999))\n" + 
            "                and liste_document.COD_PRD_PRD = produit.COD_PRD_PRD and produit.COD_PRD_PRD not in ('103','165','195') \n " + 
            "Minus\n" + 
            "(select distinct liste_document.cod_prd_prd,liste_document.cod_pop_pcon,produit.LIB_PRD_PRD,liste_document.LIB_DOC_LDOC, produit.cod_tprd_prd, produit.COD_SFAM_SFP,produit.COD_GFAM_GP \n" + 
            " from liste_document,contrat_cpt,produit where\n" + 
            "		contrat_cpt.cod_prd_prd = liste_document.cod_prd_prd \n" + 
            "                and liste_document.COD_PRD_PRD = produit.COD_PRD_PRD \n" + 
            "		and liste_document.cod_prd_prd in (select produit.cod_prd_prd from produit,contrat_cpt \n" + 
            "		                                    where COD_UNIQ_PRD = 'U'\n" + 
            "		                                    and produit.cod_prd_prd = contrat_cpt.cod_prd_prd \n" + 
            "		                                    and contrat_cpt.num_seq_pers   = " + 
            paramPers.getNumSeqPers() + "\n" + 
            "		                                    and contrat_cpt.cod_etat_ccpt  not in ('N','R')))\n" + 
            "order by COD_PRD_PRD";

        System.out.println(req);
      
        List rows = jt.queryForList(req);
        
        return rows;

    }

    public List getListLibyen(ParamPers paramPers) {
        jt = new JdbcTemplate(dataSource);
        String codNatPcon = "L";
        String codResPcon = "N";
        
        Integer v_age = paramPers.getAge();
        String req = 
            "SELECT  liste_document.COD_PRD_PRD,liste_document.cod_pop_pcon,produit.lib_prd_prd,liste_document.LIB_DOC_LDOC,produit.cod_tprd_prd,produit.COD_SFAM_SFP,produit.COD_GFAM_GP   FROM liste_document,produit where    COD_POP_PCON in \n" + 
            "           (\n" + "                select COD_POP_PCON from pop_concern where \n" + 
            "           (COD_NAT_PCON = '" + codNatPcon + 
            "' or COD_NAT_PCON = 'I' or COD_NAT_PCON = 'E' ) and \n" + 
            "               (COD_RES_PCON = '" + codResPcon + 
            "' or COD_RES_PCON = 'I') and\n" + 
            "               (COD_CATP_CATP = '" + paramPers.getCodCatpCatp() + 
            "') and\n" + "              (COD_FJ_FJ     = " + paramPers.getCodFjFj() + 
            ")\n" + "           and " + v_age + 
            " between  NVL(NBR_AGMI_PCON,0) and NVL(NBR_AGMA_PCON,999))\n" + 
            "                and liste_document.COD_PRD_PRD = produit.COD_PRD_PRD and produit.COD_PRD_PRD not in ('103','165','195') \n " + 
            "Minus\n" + 
            "(select distinct liste_document.cod_prd_prd,liste_document.cod_pop_pcon,produit.LIB_PRD_PRD,liste_document.LIB_DOC_LDOC, produit.cod_tprd_prd, produit.COD_SFAM_SFP,produit.COD_GFAM_GP \n" + 
            " from liste_document,contrat_cpt,produit where\n" + 
            "           contrat_cpt.cod_prd_prd = liste_document.cod_prd_prd \n" + 
            "                and liste_document.COD_PRD_PRD = produit.COD_PRD_PRD \n" + 
            "           and liste_document.cod_prd_prd in (select produit.cod_prd_prd from produit,contrat_cpt \n" + 
            "                                               where COD_UNIQ_PRD = 'U'\n" + 
            "                                               and produit.cod_prd_prd = contrat_cpt.cod_prd_prd \n" + 
            "                                               and contrat_cpt.num_seq_pers   = " + 
            paramPers.getNumSeqPers() + "\n" + 
            "                                               and contrat_cpt.cod_etat_ccpt  not in ('N','R')))\n" + 
            "order by COD_PRD_PRD";

        System.out.println(req);
      
        List rows = jt.queryForList(req);
        
        return rows;

    }
    public List getListCptPersonnel(ParamPers paramPers) {
        jt = new JdbcTemplate(dataSource);
        String codNatPcon;
        String codResPcon = "I";
        if (paramPers.getCodPaysPays() == null) {
            codNatPcon = "I";
        }
        if (paramPers.getCodPaysPays().equalsIgnoreCase("TUN")) {
            codNatPcon = "T";
        } else
            codNatPcon = "E";


        if (paramPers.getBoolResPers() == 0) {
            codResPcon = "N";
        } else if (paramPers.getBoolResPers() == 1) {
            codResPcon = "R";
        }

        Integer v_age = paramPers.getAge();
        String req = 
            "SELECT  liste_document.COD_PRD_PRD,liste_document.cod_pop_pcon,produit.lib_prd_prd,liste_document.LIB_DOC_LDOC,produit.cod_tprd_prd,produit.COD_SFAM_SFP,produit.COD_GFAM_GP   FROM liste_document,produit where    COD_POP_PCON in \n" + 
            "           (\n" + 
            "                select COD_POP_PCON from pop_concern where \n" + 
            "           (COD_NAT_PCON = '" + codNatPcon + 
            "' or COD_NAT_PCON = 'I') and \n" + 
            "               (COD_RES_PCON = '" + codResPcon + 
            "' or COD_RES_PCON = 'I') and\n" + 
            "               (COD_CATP_CATP = '" + paramPers.getCodCatpCatp() + 
            "') and\n" + "              (COD_FJ_FJ     = " + 
            paramPers.getCodFjFj() + ")\n" + "           and " + v_age + 
            " between  NVL(NBR_AGMI_PCON,0) and NVL(NBR_AGMA_PCON,999))\n" + 
            "                and liste_document.COD_PRD_PRD = produit.COD_PRD_PRD and produit.COD_PRD_PRD = '103' \n " + 
            "Minus\n" + 
            "(select distinct liste_document.cod_prd_prd,liste_document.cod_pop_pcon,produit.LIB_PRD_PRD,liste_document.LIB_DOC_LDOC, produit.cod_tprd_prd, produit.COD_SFAM_SFP,produit.COD_GFAM_GP \n" + 
            " from liste_document,contrat_cpt,produit where\n" + 
            "           contrat_cpt.cod_prd_prd = liste_document.cod_prd_prd \n" + 
            "                and liste_document.COD_PRD_PRD = produit.COD_PRD_PRD and produit.COD_PRD_PRD = '103' \n" + 
            "           and liste_document.cod_prd_prd in (select produit.cod_prd_prd from produit,contrat_cpt \n" + 
            "                                               where COD_UNIQ_PRD = 'U'\n" + 
            "                                               and produit.cod_prd_prd = contrat_cpt.cod_prd_prd \n" + 
            "                                               and contrat_cpt.num_seq_pers   = " + 
            paramPers.getNumSeqPers() + "\n" + 
            "                                               and contrat_cpt.cod_etat_ccpt  not in ('N','R','S')))\n" + 
            "order by COD_PRD_PRD";

        List rows = jt.queryForList(req);
        // System.out.println(req);
        return rows;

    }

    public Long getNbrProdByPers(PersProduit persProduit) {
        jt = new JdbcTemplate(dataSource);

        String req = 
            "SELECT count(*) from contrat_cpt where contrat_cpt.cod_prd_prd = " + 
            persProduit.getCodPrdPrd().intValue() + 
            " and  contrat_cpt.cod_etat_ccpt = 'V' and contrat_cpt.num_seq_pers = " + 
            persProduit.getNumSeqPers().intValue();
        
        Long i = jt.queryForLong(req);

        return i;

    }

}
