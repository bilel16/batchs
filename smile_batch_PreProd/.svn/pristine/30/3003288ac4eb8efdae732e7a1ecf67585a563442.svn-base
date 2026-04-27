package com.bna.smile.model.moyenPayement.dao;

import java.math.BigInteger;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import com.bna.commun.util.DateHandler;
import com.bna.smile.model.moyenPayement.model.Prelevement;

public class PrelevementDAO {
    protected String sqlQuery;
    protected JdbcTemplate jt;
    protected DataSource dataSource;

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public void setSqlQuery(String sqlQuery) {
        this.sqlQuery = sqlQuery;
    }
    public String calculerRIB(String RIB) {
                String cle = "";
                String resultat = "";
                if (RIB.length() == 18) {
                        String RI = RIB;
                        BigInteger rr = new BigInteger(RI.concat("00"));
                        int rest = rr.mod(new BigInteger("97")).intValue();
                        int nb = 97 - rest;
                        String nbr = "" + nb;
                        if (nbr.length() == 1)
                                resultat = "0" + nbr;
                        else
                                resultat = nbr;
               }
                return resultat;
        }
    public List getPrelevementByStructure(String codeStructure, 
                                       Date dateChargement) {

        List<Prelevement> prelevements = new ArrayList<Prelevement>();
        jt = new JdbcTemplate(dataSource);
        String req1 = 
            "select COD_BCT_STRC from structure where COD_STRC_STRC=" + 
            codeStructure;
        Long codeBct = jt.queryForLong(req1);

        /*String req = 
            "select COD_AGE_DES,RIB_TIR,RIB_BEN,LIB_PRL,MNT_PRL from ad_detail_prelevement" + 
            " where cod_sen=2 and cod_enr=21 and COD_AGE_DES=" + codeBct + 
            " and DAT_OPE='" + DateHandler.dateToStr(dateChargement) + "'";*/
            
         String req = 
        " select COD_AGE_DES,DAT_OPE,SUBSTR(RIB_TIR,5,13 ) as numcpt,MNT_PRL,RIB_BEN,LIB_PRL," +
        " (select nom_inti_ccpt from contrat_cpt where cod_strc_strc=to_number(SUBSTR(RIB_TIR,5,3 )) " +
        " and cod_prd_prd=to_number(SUBSTR(RIB_TIR,8,4)) and num_ccpt_ccpt=to_number(SUBSTR(RIB_TIR,12,6 ))) as nom " +
        " from ad_detail_prelevement  where cod_sen=2 and cod_enr=21 and COD_AGE_DES=" + codeBct + 
        " and DAT_OPE='" + DateHandler.dateToStr(dateChargement) + "'";
    
        prelevements = jt.query(req, new RowMapper() {

                        public Object mapRow(ResultSet rs, 
                                             int rowNum) throws SQLException {
                            Prelevement prelevement = new Prelevement();
                            NumberFormat format = 
                                new DecimalFormat("#,##0.000");
                            prelevement.setRibBenificiaire(rs.getString("RIB_BEN"));
                            prelevement.setNumCompte(rs.getString("numcpt"));
                            prelevement.setMntPrelevement(format.format(Double.parseDouble(rs.getString("MNT_PRL"))));
                           prelevement.setNprsTireur(rs.getString("nom"));
                            prelevement.setCodeBct(rs.getString("COD_AGE_DES"));
                            prelevement.setMotifOperation(rs.getString("LIB_PRL"));
                            return prelevement;
                        }


                    });

        return prelevements;

    }
}
