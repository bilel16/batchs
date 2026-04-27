package com.bna.smile.model.domainetraitementfichier;

import com.bna.commun.util.ContextHandler;
import com.bna.commun.util.StrHandler;
import com.bna.smile.model.moyenPayement.model.Accuse;

import com.oxia.fwk.context.Context;
import java.io.Serializable;

import java.sql.ResultSet;

import java.sql.SQLException;

import java.text.DecimalFormat;
import java.text.NumberFormat;

import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;
import javax.swing.JOptionPane;
import org.apache.log4j.Logger;
import org.springframework.jdbc.core.JdbcTemplate;
import java.util.Date;

import org.springframework.jdbc.core.RowMapper;

public class GetCodeBctAgence implements Serializable {
 
   /** * * * * * * * * * * * * * * * * * * * *
     * @author JAOUALI Yossri                *
     * @param code STRC                      *
     * @return code BCT                      *
     * @since 28/10/2010                     *
     * * * * * * * * * * * * * * * * * * * * */

    private static Logger logger = Logger.getLogger(GetCodeBctAgence.class);
    JdbcTemplate jt;
    Context context = ContextHandler.getContext();
    DataSource dataSource = (DataSource)context.getBean("dataSource");
    
    /**
     * Methode permettant de retourner le code BCT d'une agence à travers de sa code STRC
     * @param  codeStrc:String
     * @return String
     */
     
    public String getCodeBctAgence(String codeStrc) {
        String codBct = null;

        try {

            jt = new JdbcTemplate(dataSource);
            codBct = String.valueOf(jt.queryForLong("select COD_BCT_STRC from structure where COD_STRC_STRC=" + codeStrc));

            if (codBct != null) {
                codBct = StrHandler.lpad(codBct, '0', 3);

            }

        } catch (Exception e) {

            JOptionPane.showMessageDialog(null, "Problème du connexion à la base");
            logger.error("Problème du connexion à la base" + e);

        }

        return codBct;
    }
    
    
    /**
     * Methode permettant d'insérer un fichier à traiter dans la base
     * @return String
     */
     public void ajouterFichier(String nomFichier,String codeStructure, 
                                             Date datePECFichier, int codeTraitFichier) {
         try {

             jt = new JdbcTemplate(dataSource);

         jt.update("INSERT INTO SUIVI_FILE (NOM_ORIG_SFILE,COD_STRC_STRC ,DAT_OPER_SFILE,COD_TRAI_SFILE) VALUES(?,?,?,?)", 
                   new Object[] {nomFichier, codeStructure, datePECFichier, codeTraitFichier });
         } catch (Exception e) {

             JOptionPane.showMessageDialog(null, "Problème du connexion à la base");
             logger.error("Problème du connexion à la base" + e);

         }
     }
    
    /**
     * Methode permettant de mettre à jour la date de PEC d'un fichier dans la base
     * @return String
     */
     public void updateFichier(String nomFichier,Date datePECFichier,int codeTraitFichier) {

         jt = new JdbcTemplate(dataSource);
         jt.update("update SUIVI_FILE set DAT_OPER_SFILE=?,COD_TRAI_SFILE=? where NOM_ORIG_SFILE=? ", 
                   new Object[] {datePECFichier,Integer.valueOf(codeTraitFichier), nomFichier });
     } 
    /*
     * Methode permettant de flagger le code traitement d'un fichier dans la base
     * @return String
     
     public void updateFichier(String nomFichier,int codeTraitFichier) {

         jt.update("update SUIVI_FILE set COD_TRAI_SFILE=? where NOM_ORIG_SFILE=? ", 
                   new Object[] {Integer.valueOf(codeTraitFichier), nomFichier });
     } */
   // Integer.valueOf(forumId) 
   public List<Fichier> getFichier(String nomFichier) {
   
        jt = new JdbcTemplate(dataSource);
    String req = " select  NOM_ORIG_SFILE,COD_TRAI_SFILE FROM SUIVI_FILE where NOM_ORIG_SFILE='"+nomFichier+"'";
   
List<Fichier> list=new ArrayList<Fichier>();

       list = jt.query(req, new RowMapper() {

                       public Object mapRow(ResultSet resultSet, 
                                            int rowNum) throws SQLException {
                          
                           Fichier fichier =new Fichier();

                           fichier.setNomFichier(resultSet.getString("NOM_ORIG_SFILE"));
                           fichier.setCodeTraitFichier(resultSet.getInt("COD_TRAI_SFILE"));
                              return fichier;
                       }


                   });
       
                
              return list;  
  
    }

  
}


