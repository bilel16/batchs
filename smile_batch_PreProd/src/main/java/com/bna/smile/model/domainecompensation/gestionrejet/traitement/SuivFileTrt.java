package com.bna.smile.model.domainecompensation.gestionrejet.traitement;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.sql.DataSource;

import org.apache.log4j.Logger;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import com.bna.commun.util.ContextHandler;
import com.bna.commun.util.StrHandler;
import com.oxia.fwk.context.Context;

public class SuivFileTrt implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6680129139287818146L;
	private static Logger logger = Logger.getLogger(SuivFileTrt.class);
	static JdbcTemplate jt;
	static Context context = ContextHandler.getContext();
	static DataSource dataSource = (DataSource) context.getBean("dataSource");

	/**
	 * Methode permettant de retourner le code BCT d'une agence à travers de sa code STRC
	 * 
	 * @param codeStrc
	 *            :String
	 * @return String
	 */

	public String getCodeBctAgence(String codeStrc) {
		String codBct = null;

		try {

			jt = new JdbcTemplate(dataSource);
			codBct = String.valueOf(
					jt.queryForLong("select distinct COD_BCT_STRC from structure where COD_STRC_STRC=" + codeStrc));

			if (codBct != null) {
				codBct = StrHandler.lpad(codBct, '0', 3);

			}

		} catch (Exception e) {

			// JOptionPane.showMessageDialog(null,
			// "Problème du connexion à la base");
			logger.error("Problème du connexion à la base" + e);

		}

		return codBct;
	}

	/**
	 * Methode permettant d'insérer un fichier à traiter dans la base
	 * 
	 * @return String
	 */
	public static void ajouterFichier(String nomFichier, String codeStructure, Date datePECFichier,
			int codeTraitFichier, Long codValVal) {
		try {

			jt = new JdbcTemplate(dataSource);

			int exist = jt.queryForInt(
					"select count(*) from SUIVI_FILE_TELECOMPENSATION  where NOM_ORIG_SFILE='" + nomFichier + "'");
			if (exist == 0) {
				jt.update(
						"INSERT INTO SUIVI_FILE_TELECOMPENSATION (NOM_ORIG_SFILE,COD_STRC_STRC ,DAT_OPER_SFILE,COD_TRAI_SFILE,COD_VAL_VAL) VALUES(?,?,?,?,?)",
						new Object[]{ nomFichier, codeStructure, datePECFichier, codeTraitFichier, codValVal });
			} else {
				jt.update("update SUIVI_FILE_TELECOMPENSATION set COD_TRAI_SFILE =" + codeTraitFichier
						+ " where NOM_ORIG_SFILE='" + nomFichier + "'");
			}
		} catch (Exception e) {

			// JOptionPane.showMessageDialog(null,
			// "Problème du connexion à la base");
			logger.error("Problème du connexion à la base" + e);

		}
	}

	public static void appurementSuiviFile(String codeStructure, String datePECFichier) {

		jt = new JdbcTemplate(dataSource);
		String sql = "delete from  SUIVI_FILE_TELECOMPENSATION where trunc(dat_oper_sfile)='" + datePECFichier
				+ "' and cod_strc_strc='" + codeStructure
				+ "' and cod_val_val in (41,42,40) and nom_orig_sfile like '%.RCP'";
		jt.execute(sql);

	}

	public static void appurementSuiviFileEffet(String datePECFichier, String strcBct) {

		jt = new JdbcTemplate(dataSource);
		String sql = "";
		if (strcBct == null) {
			sql = "delete from  SUIVI_FILE_TELECOMPENSATION where trunc(dat_oper_sfile)='" + datePECFichier
					+ "' and cod_val_val in (41,42,40) and nom_orig_sfile like '%.RCP' ";
			jt.execute(sql);
			jt.execute("commit");
			sql = "delete from  effet_recu_adt where trunc(dat_ope_eff)='" + datePECFichier
					+ "' and  cod_etat_eff is null";
			jt.execute(sql);
			jt.execute("commit");
			sql = "delete from  effet_recu_tmp where trunc(dat_ope_eff)='" + datePECFichier
					+ "' and  cod_etat_eff is null";
			jt.execute(sql);
			jt.execute("commit");
			sql = "delete from  complement_effet_recu where trunc(dat_ope_eff)='" + datePECFichier + "' ";
			jt.execute(sql);
			jt.execute("commit");

		} else {

			sql = "delete from  SUIVI_FILE_TELECOMPENSATION where trunc(dat_oper_sfile)='" + datePECFichier
					+ "' and cod_val_val in (41,42,40) and nom_orig_sfile like '%.RCP' and cod_strc_strc='" + strcBct
					+ "'";
			jt.execute(sql);
			jt.execute("commit");
			sql = "delete from  effet_recu_adt where trunc(dat_ope_eff)='" + datePECFichier
					+ "' and  cod_etat_eff is null and cod_age_des='" + strcBct + "'";
			jt.execute(sql);
			jt.execute("commit");
			sql = "delete from  effet_recu_tmp where trunc(dat_ope_eff)='" + datePECFichier
					+ "' and  cod_etat_eff is null and cod_age_des='" + strcBct + "'";
			jt.execute(sql);
			jt.execute("commit");
			sql = "delete from  complement_effet_recu where trunc(dat_ope_eff)='" + datePECFichier
					+ "'  and cod_age_des='" + strcBct + "'";
			jt.execute(sql);
			jt.execute("commit");
		}

	}

	/**
	 * Methode permettant d'insérer un fichier à traiter dans la base
	 * 
	 * @return String
	 */
	public static void ajouterFichierAvecMontant(String nomFichier, String codeStructure, Date datePECFichier,
			int codeTraitFichier, Long codValVal, long mntTotal, long nbreTotal, Long mntTotalInter,
			long nbreTotalInter, long nbreTotalIntra, Long mntTotalIntra) {
		try {

			jt = new JdbcTemplate(dataSource);

			int exist = jt.queryForInt(
					"select count(*) from SUIVI_FILE_TELECOMPENSATION  where NOM_ORIG_SFILE='" + nomFichier + "'");
			if (exist == 0) {
				jt.update(
						"INSERT INTO SUIVI_FILE_TELECOMPENSATION (NOM_ORIG_SFILE,COD_STRC_STRC ,DAT_OPER_SFILE,COD_TRAI_SFILE,COD_VAL_VAL,MNT_TOT_SFILE,NBR_TOT_SFILE,MNT_TOT_INTER,NBR_TOT_INTER,MNT_TOT_INTRA,NBR_TOT_INTRA)"
								+ "  VALUES(?,?,?,?,?,?,?,?,?,?,?)",
						new Object[]{ nomFichier, codeStructure, datePECFichier, codeTraitFichier, codValVal, mntTotal,
								nbreTotal, mntTotalInter, nbreTotalInter, mntTotalIntra, nbreTotalIntra });
			} else {
				// update nbdour 01122014 : en cas d'update , faut garnir les
				// montants !
				jt.update("update SUIVI_FILE_TELECOMPENSATION set " + "COD_TRAI_SFILE =" + codeTraitFichier + ","
						+ "MNT_TOT_SFILE=" + mntTotal + "," + "NBR_TOT_SFILE=" + nbreTotal + "," + "MNT_TOT_INTER ="
						+ mntTotalInter + "," + "NBR_TOT_INTER=" + nbreTotalInter + "," + "MNT_TOT_INTRA="
						+ mntTotalIntra + "," + "NBR_TOT_INTRA=" + nbreTotalIntra

						+ " where NOM_ORIG_SFILE='" + nomFichier + "'");
			}
		} catch (Exception e) {

			// JOptionPane.showMessageDialog(null,
			// "Problème du connexion à la base");
			logger.error("Problème du connexion à la base" + e);

		}
	}

	/**
	 * Methode permettant de mettre à jour la date de PEC d'un fichier dans la base
	 * 
	 * @return String
	 */
	public void updateFichier(String nomFichier, Date datePECFichier, int codeTraitFichier) {

		jt = new JdbcTemplate(dataSource);
		jt.update("update SUIVI_FILE_TELECOMPENSATION set DAT_OPER_SFILE=?,COD_TRAI_SFILE=? where NOM_ORIG_SFILE=? ",
				new Object[]{ datePECFichier, Integer.valueOf(codeTraitFichier), nomFichier });
	}

	/*
	 * Methode permettant de flagger le code traitement d'un fichier dans la base
	 * 
	 * @return String
	 * 
	 * public void updateFichier(String nomFichier,int codeTraitFichier) {
	 * 
	 * jt.update("update SUIVI_FILE set COD_TRAI_SFILE=? where NOM_ORIG_SFILE=? " , new Object[]
	 * {Integer.valueOf(codeTraitFichier), nomFichier }); }
	 */
	// Integer.valueOf(forumId)
	public List<Fichier> getFichier(String nomFichier) {

		jt = new JdbcTemplate(dataSource);
		String req = " select  NOM_ORIG_SFILE,COD_TRAI_SFILE FROM SUIVI_FILE_TELECOMPENSATION where NOM_ORIG_SFILE='"
				+ nomFichier + "'";

		List<Fichier> list = new ArrayList<Fichier>();

		list = jt.query(req, new RowMapper() {

			public Object mapRow(ResultSet resultSet, int rowNum) throws SQLException {

				Fichier fichier = new Fichier();

				fichier.setNomFichier(resultSet.getString("NOM_ORIG_SFILE"));
				fichier.setCodeTraitFichier(resultSet.getInt("COD_TRAI_SFILE"));
				return fichier;
			}

		});

		return list;

	}

	public static List<String> getAllBctAge() {

		List<String> agences = new ArrayList<String>();
		List<String> returnValue = new ArrayList<String>();

		jt = new JdbcTemplate(dataSource);
		agences = (List<String>) jt.queryForList("select COD_BCT_STRC from structure where cod_tstr_tstr=1",
				String.class);
		for (String bct : agences)
			returnValue.add(StrHandler.lpad(bct, '0', 3));
		return returnValue;
	}

	// public static void insert30(Cheque30Id id, Cheque30 c30) throws
	// DataAccessException, SQLException {
	// jt = new JdbcTemplate(dataSource);
	// int insert =
	// jt.update(
	// "Insert into cheque_30
	// (COD_UG,DAT_JOU,NUM_SEQ,NUM,SENS,COD_VAL,NAT_REM,COD_REM,COD_CEN_REG,DAT_OPE,NUM_LOT,COD_ENR,COD_DEV,MNT_CHQ,"
	// +
	// "NUM_CHQ,RIB_TIR,COD_INS_DES,COD_CEN_REG_DES,RIB_BEN,NOM_BEN,DAT_EMI,LIE_EMI,SIT_BEN,NAT_CPT,COD_STA,CMP_AUTO,IMG_VER,IMG_REC,COD_ETAT_CHQ)"
	// + " values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)",
	// new Object[]{ c30.getCodUgOpe(), c30.getDatOpe(), id.getNumSeq(),
	// id.getNum(), c30.getSens(),
	// c30.getCodVal(), c30.getNatRem(), c30.getCodRem(), c30.getCodCenReg(),
	// c30.getDatOpe(),
	// c30.getNumLot(), c30.getCodEnr(), c30.getCodDev(), c30.getMntChq(),
	// c30.getNumChq(),
	// c30.getRibTir(), c30.getCodInsDes(), c30.getCodCenRegDes(),
	// c30.getRibBen(),
	// c30.getNomBen(), c30.getDatEmi(), c30.getLieEmi(), c30.getSitBen(),
	// c30.getNatCpt(),
	// "N", "N", new SqlLobValue(c30.getImgVer().getBytes(1, (int)
	// c30.getImgVer().length())),
	// null, c30.getCodEtatChq()
	//
	// });
	//
	// if (insert == 0)
	// System.out.println(" probleme insertion ");
	// }

	// public static void insert31(Cheque31Id id, Cheque31 c30) {
	// jt = new JdbcTemplate(dataSource);
	// int returnValue =
	// jt.update(
	// "Insert into cheque_31
	// (COD_UG,DAT_JOU,NUM_SEQ,NUM,SENS,COD_VAL,NAT_REM,COD_REM,COD_CEN_REG,DAT_OPE,NUM_LOT,COD_ENR,COD_DEV,MNT_CHQ,"
	// +
	// "NUM_CHQ,RIB_TIR,COD_INS_DES,COD_CEN_REG_DES,RIB_BEN,DAT_EMI,LIE_EMI,COD_STA,CMP_AUTO,IMG_VER,IMG_REC,COD_ETAT_CHQ)"
	// + " values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)",
	// new Object[]{ id.getCodUg(), id.getDatJou(), id.getNumSeq(), id.getNum(),
	// c30.getSens(),
	// c30.getCodVal(), c30.getNatRem(), c30.getCodRem(), c30.getCodCenReg(),
	// c30.getDatOpe(),
	// c30.getNumLot(), c30.getCodEnr(), c30.getCodDev(), c30.getMntChq(),
	// c30.getNumChq(),
	// c30.getRibTir(), c30.getCodInsDes(), c30.getCodCenRegDes(),
	// c30.getRibBen(),
	// c30.getDatEmi(), c30.getLieEmi(), "N", "N", c30.getImgVer(),
	// c30.getImgRec(),
	// c30.getCodEtatChq()
	//
	// });
	// if (returnValue == 0)
	// logger.debug("errur insertion :(cod_ug) " + id.getCodUg());
	// }

	// public static void insert32(Cheque32Id id, Cheque32 c30) {
	// jt = new JdbcTemplate(dataSource);
	// jt.update(
	// "Insert into cheque_32
	// (COD_UG,DAT_JOU,NUM_SEQ,NUM,SENS,COD_VAL,NAT_REM,COD_REM,COD_CEN_REG,DAT_OPE,NUM_LOT,COD_ENR,COD_DEV,MNT_CHQ,NUM_CHQ,RIB_TIR,COD_INS_DES,COD_CEN_REG_DES,RIB_BEN,DAT_EMI,LIE_EMI,COD_STA,CMP_AUTO,NUM_CNP,MNT_REC,IMG_VER,IMG_REC,COD_ETAT_CHQ)"
	// + " values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)",
	//
	// new Object[]{ id.getCodUg(), id.getDatJou(), id.getNumSeq(), id.getNum(),
	// c30.getSens(),
	// c30.getCodVal(), c30.getNatRem(), c30.getCodRem(), c30.getCodCenReg(),
	// c30.getDatOpe(),
	// c30.getNumLot(), c30.getCodEnr(), c30.getCodDev(), c30.getMntChq(),
	// c30.getNumChq(),
	// c30.getRibTir(), c30.getCodInsDes(), c30.getCodCenRegDes(),
	// c30.getRibBen(), c30.getDatEmi(),
	// c30.getLieEmi(), "N", "N", c30.getNumCnp(), c30.getMntRec(),
	// c30.getImgVer(), c30.getImgRec(),
	// c30.getCodEtatChq()
	//
	// });
	// }

	// public static void insert33(Cheque33Id id, Cheque33 c30) {
	// jt = new JdbcTemplate(dataSource);
	// jt.update(
	// "Insert into cheque_33
	// (COD_UG,DAT_JOU,NUM_SEQ,NUM,SENS,COD_VAL,NAT_REM,COD_REM,COD_CEN_REG,DAT_OPE,NUM_LOT,COD_ENR,COD_DEV,MNT_CHQ,"
	// +
	// "NUM_CHQ,RIB_TIR,COD_INS_DES,COD_CEN_REG_DES,RIB_BEN,NOM_BEN,DAT_EMI,LIE_EMI,SIT_BEN,NAT_CPT,COD_STA,CMP_AUTO,IMG_VER,IMG_REC,COD_ETAT_CHQ)"
	// + " values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)",
	// new Object[]{ id.getCodUg(), id.getDatJou(), id.getNumSeq(), id.getNum(),
	// c30.getSens(),
	// c30.getCodVal(), c30.getNatRem(), c30.getCodRem(), c30.getCodCenReg(),
	// c30.getDatOpe(),
	// c30.getNumLot(), c30.getCodEnr(), c30.getCodDev(), c30.getMntChq(),
	// c30.getNumChq(),
	// c30.getRibTir(), c30.getCodInsDes(), c30.getCodCenRegDes(),
	// c30.getRibBen(), c30.getNomBen(),
	// c30.getDatEmi(), c30.getLieEmi(), c30.getSitBen(), c30.getNatCpt(), "N",
	// "N", c30.getImgVer(),
	// c30.getImgRec(), c30.getCodEtatChq()
	//
	// });
	// }

	public static boolean isTreated(String nomFichier) {
		jt = new JdbcTemplate(dataSource);
		int exist = jt.queryForInt("select count(*) from SUIVI_FILE_TELECOMPENSATION  where NOM_ORIG_SFILE='"
				+ nomFichier + "' and COD_TRAI_SFILE=1");
		if (exist == 0) {
			return false;
		}
		return true;

	}

	public static void validInsert(Date date, String bct) {

		final SimpleDateFormat formatDate1 = new SimpleDateFormat("dd/MM/yyyy");
		final SimpleDateFormat formatDate2 = new SimpleDateFormat("ddMMyyyy");
		jt = new JdbcTemplate(dataSource);

		Long notExist = jt.queryForLong("select  count(*) from batch_comp_chq where COD_STRC_STRC='" + bct
				+ "' and DATE_OPER_OPER='" + formatDate1.format(date) + "' ");

		if (notExist == 0)
			// jt.update("update batch_comp_chq set ETAT_STRC_INSERT='N' where COD_STRC_STRC='"+bct+"' and
			// DATE_OPER_OPER='"+formatDate1.format(date)+"' ");
			// else
			jt.update(
					"INSERT INTO batch_comp_chq (DATE_OPER_OPER,COD_STRC_STRC,ETAT_STRC_INSERT,ETAT_STRC_POS) VALUES(?,?,?,?)",
					new Object[]{ formatDate1.format(date), StrHandler.lpad("" + bct, '0', 3), "N", null });

	}

	public static void validerInsertion(String date) {

		jt = new JdbcTemplate(dataSource);
		try {
			jt.update(
					"insert into SUIVI_FILE_TELECOMPENSATION  select * from  SUIVI_FILE_CMP_tmp where  trunc(dat_oper_sfile)='"
							+ date + "' ");
		} catch (Exception e) {
			// Catch constraint integrity
		}

	}

	public static void validInsertAgence(Date date, String bct) {

		final SimpleDateFormat formatDate1 = new SimpleDateFormat("dd/MM/yyyy");
		final SimpleDateFormat formatDate2 = new SimpleDateFormat("ddMMyyyy");
		jt = new JdbcTemplate(dataSource);

		Long notExist = jt.queryForLong("select  count(*) from batch_comp_chq where COD_STRC_STRC='" + bct
				+ "' and DATE_OPER_OPER='" + formatDate1.format(date) + "' ");

		if (notExist == 0)
			// jt.update("update batch_comp_chq set ETAT_STRC_INSERT='N' where COD_STRC_STRC='"+bct+"' and
			// DATE_OPER_OPER='"+formatDate1.format(date)+"' ");
			// else
			jt.update(
					"INSERT INTO batch_comp_chq (DATE_OPER_OPER,COD_STRC_STRC,ETAT_STRC_INSERT,ETAT_STRC_POS) VALUES(?,?,?,?)",
					new Object[]{ formatDate1.format(date), StrHandler.lpad("" + bct, '0', 3), "F", null });

	}

	public static void forcer(Date date) {

		final SimpleDateFormat formatDate1 = new SimpleDateFormat("dd/MM/yyyy");
		final SimpleDateFormat formatDate2 = new SimpleDateFormat("ddMMyyyy");

		jt = new JdbcTemplate(dataSource);
		validerInsertion(formatDate1.format(date));
		List<Long> notExist = jt.queryForList(
				"select  SS.COD_BCT_STRC from structure SS, AGENCE_PILOTE ag where SS.COD_STRC_STRC=AG.COD_STRC_STRC and SS.COD_BCT_STRC not in ( select SF.COD_STRC_STRC "
						+ "from SUIVI_FILE_TELECOMPENSATION  sf where SF.NOM_ORIG_SFILE like '%-30-%RCP'  and TRUNC(SF.DAT_OPER_SFILE)='"
						+ formatDate1.format(date) + "') ",
				Long.class);

		for (Long bct : notExist) {
			ajouterFichierAvecMontant(
					"03-" + StrHandler.lpad("" + bct, '0', 3) + "-30-" + formatDate2.format(date) + "-788.RCP",
					StrHandler.lpad("" + bct, '0', 3), date, 1, 30l, 0l, 0l, 0l, 0l, 0l, 0l);
			jt.update(
					"INSERT INTO batch_comp_chq (DATE_OPER_OPER,COD_STRC_STRC,ETAT_STRC_INSERT,ETAT_STRC_POS) VALUES(?,?,?,?)",
					new Object[]{ formatDate1.format(date), StrHandler.lpad("" + bct, '0', 3), "F", null });

		}

	}

	public static void validPos(Date date, String bct) {

		final SimpleDateFormat formatDate1 = new SimpleDateFormat("dd/MM/yyyy");
		jt = new JdbcTemplate(dataSource);

		jt.update("update batch_comp_chq  set ETAT_STRC_POS='N' where COD_STRC_STRC='" + bct + "' and DATE_OPER_OPER='"
				+ formatDate1.format(date) + "' ");

	}
	
	public static void ajouterFichierProvAvecMontant(String nomFichier, String codeStructure, Date datePECFichier,
			int codeTraitFichier, Long codValVal, long mntTotal, long nbreTotal, Long mntTotalInter,
			long nbreTotalInter, long nbreTotalIntra, Long mntTotalIntra) {
		try {

			jt = new JdbcTemplate(dataSource);

			int exist =
					jt.queryForInt("select count(*) from SUIVI_FILE_CMP_tmp  where NOM_ORIG_SFILE='"
							+ nomFichier + "'");
			if (exist == 0) {
				jt.update(
						"INSERT INTO SUIVI_FILE_CMP_tmp (NOM_ORIG_SFILE,COD_STRC_STRC ,DAT_OPER_SFILE,COD_TRAI_SFILE,COD_VAL_VAL,MNT_TOT_SFILE,NBR_TOT_SFILE,MNT_TOT_INTER,NBR_TOT_INTER,MNT_TOT_INTRA,NBR_TOT_INTRA)"
								+ "  VALUES(?,?,?,?,?,?,?,?,?,?,?)", new Object[]{ nomFichier, codeStructure,
								datePECFichier, codeTraitFichier, codValVal, mntTotal, nbreTotal, mntTotalInter,
								nbreTotalInter, mntTotalIntra, nbreTotalIntra });
			} else {
				
				// update nbdour 01122014 : en cas d'update , faut garnir les montants !
				jt.update("update SUIVI_FILE_CMP_tmp set " + "COD_TRAI_SFILE =" + codeTraitFichier + ","
						+ "MNT_TOT_SFILE=" + mntTotal + "," + "NBR_TOT_SFILE=" + nbreTotal + "," + "MNT_TOT_INTER ="
						+ mntTotalInter + "," + "NBR_TOT_INTER=" + nbreTotalInter + "," + "MNT_TOT_INTRA="
						+ mntTotalIntra + "," + "NBR_TOT_INTRA=" + nbreTotalIntra

						+ " where NOM_ORIG_SFILE='" + nomFichier + "'");
			}
		} catch (Exception e) {

			// JOptionPane.showMessageDialog(null, "Problème du connexion à la base");
			logger.error("Problème du connexion à la base" + e);

		}
	}
	
	
}
