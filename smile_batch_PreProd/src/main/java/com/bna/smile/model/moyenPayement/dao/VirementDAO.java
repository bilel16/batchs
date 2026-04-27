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
import com.bna.commun.util.StrHandler;
import com.bna.smile.model.moyenPayement.model.Virement;

public class VirementDAO {

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
    public List getVirementByStructure(String codeStructure, 
                                       Date dateChargement) {

        List<Virement> virements = new ArrayList<Virement>();
        jt = new JdbcTemplate(dataSource);
        String req1 = 
            "select COD_BCT_STRC from structure where COD_STRC_STRC=" + 
            codeStructure;
        Long codeBct = jt.queryForLong(req1);

        String req = 
            "select COD_AGE_DES,RIB_TIR,NOM_PRN,MNT_VIR,RIB_BEN from ad_detail_virement " + 
            "where cod_sen=2 and cod_enr=21 and COD_AGE_DES=" + codeBct + 
            " and DAT_OPE='" + DateHandler.dateToStr(dateChargement) + "'";


        virements = jt.query(req, new RowMapper() {

                        public Object mapRow(ResultSet rs, 
                                             int rowNum) throws SQLException {
                            Virement virement = new Virement();
                            NumberFormat format = 
                                new DecimalFormat("#,##0.000");
                            virement.setRibBenificiaire(rs.getString("RIB_BEN"));
                            virement.setRibTireur(rs.getString("RIB_TIR"));
                            virement.setMntVirement(format.format(Double.parseDouble(rs.getString("MNT_VIR"))));
                            virement.setNprsTireur(rs.getString("NOM_PRN"));
                            virement.setCodeBct(rs.getString("COD_AGE_DES"));
                            return virement;
                        }


                    });

        return virements;

    }

    public List getVirementByRIB(String codeStructure, Date dateChargement, 
                                 String numeroCcpt, String codeProduit) {


        List<Virement> virements = new ArrayList<Virement>();
        jt = new JdbcTemplate(dataSource);
        String req1 = 
            "select COD_BCT_STRC from structure " + "where COD_STRC_STRC=" + 
            codeStructure;
        String codeBct =StrHandler.lpad(String.valueOf(jt.queryForLong(req1)),'0', 3) ;
        
        String cleRib=calculerRIB("03"+codeBct + codeStructure + codeProduit + numeroCcpt);

       String rib="03"+codeBct + codeStructure + codeProduit + numeroCcpt+cleRib;
     
        String req2 = 
            "select RIB_BEN,RIB_TIR,NOM_PRN,MNT_VIR,MOT_OPE from ad_detail_virement " + 
            "where cod_sen=2 and cod_enr=21 and RIB_BEN='" + rib + 
            "' and DAT_OPE='" + DateHandler.dateToStr(dateChargement) + "'";


        virements = jt.query(req2, new RowMapper() {

                        public Object mapRow(ResultSet rs, 
                                             int rowNum) throws SQLException {
                            Virement virement = new Virement();
                            NumberFormat format = new DecimalFormat("#,##0.000");
                            virement.setMotifOperation(rs.getString("MOT_OPE"));
                            virement.setRibTireur(rs.getString("RIB_TIR"));
                            virement.setMntVirement(format.format(Double.parseDouble(rs.getString("MNT_VIR"))));
                            virement.setNprsTireur(rs.getString("NOM_PRN"));
                            virement.setRibBenificiaire(rs.getString("RIB_BEN")); 
                            return virement;
                        }


                    });

        return virements;

    }

}
