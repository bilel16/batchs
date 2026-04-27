package com.bna.smile.model.domainecaisse.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

public class CaisseDAO {

	public CaisseDAO() {
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

	public List<String[]> getMntSortDetailSessionCaisseGab() {
		List<String[]> list = new ArrayList<String[]>();
		jt = new JdbcTemplate(dataSource);
		// SimpleDateFormat sdf1 = new SimpleDateFormat("dd/MM/yyyy");
		// String dateSt = sdf1.format(new Date());
		String req =
				"SELECT det.MONT_TOTS_DSC,sess.NUM_CAIS_CAIS,to_char(to_date(sess.DAT_JRN_JRN, 'DD/MM/YY'), 'DD/MM/YY')DAT_JRN_JRN,to_char(to_date(sess.DAT_JRN_JRN, 'DD/MM/YY'), 'ddmmyyyy')DAT_JRN_JRN_S,det.COD_DEV_DEV,sess.COD_TYP_SJC,sess.COD_STRC_JRN,   "
						+ " sess.COD_STAT_SJC,sess.NUM_MATR_USER,cais.COD_TYP_CAIS,  "
						+ "to_char(to_date(sess.DAT_JRN_JRN, 'DD/MM/YY'), 'yyyymmdd')DAT_JRN_JRN_YYYYMMDD  "
						+ "FROM SMILE.DETAIL_SESSION_CAISSE det "
						+ " INNER JOIN SESSION_JRN_CAISSE sess ON det.NUM_SEQ_SJC = sess.NUM_SEQ_SJC "
						+ " INNER JOIN JOURNEE_CAISSE jrnCais ON sess.COD_STRC_JRN = jrnCais.COD_STRC_JRN AND sess.DAT_JRN_JRN = jrnCais.DAT_JRN_JRN AND sess.NUM_CAIS_CAIS = jrnCais.NUM_CAIS_CAIS "
						+ " INNER JOIN CAISSE_STRC cais ON jrnCais.COD_STRC_JRN = cais.COD_STRC_STRC AND jrnCais.NUM_CAIS_CAIS = cais.NUM_CAIS_CAIS "
						+ " where sess.COD_STAT_SJC='F' and sess.cod_typ_sjc='P' and cais.COD_TYP_CAIS='GAB' AND det.COD_DEV_DEV=788 "
						+ " and to_date(to_char(sess.DAT_JRN_JRN,'DD/MM/YY'),'DD/MM/YY') = to_date((SELECT max(journee_structure.dat_jrn_jrn) "
						+ " FROM journee_structure WHERE  cod_strc_strc=900 and COD_STAT_JRN=1)  ,'DD/MM/YY') ";

		list = jt.query(req, new RowMapper() {

			public String[] mapRow(ResultSet rs, int rowNum) throws SQLException {
				String[] rslt = { "", "", "", "", "","" };
				rslt[0] = String.valueOf(rs.getLong("MONT_TOTS_DSC"));
				rslt[1] = String.valueOf(rs.getLong("NUM_CAIS_CAIS"));
				rslt[2] = String.valueOf(rs.getLong("COD_STRC_JRN"));
				rslt[3] = rs.getString("DAT_JRN_JRN_S");
				rslt[4] = rs.getString("DAT_JRN_JRN");
				rslt[5] = rs.getString("DAT_JRN_JRN_YYYYMMDD");
				return rslt;

			}

		});
		return list;
	}

	public List<String[]> getMntSortComptableCaisseGab() {
		List<String[]> list = new ArrayList<String[]>();
		jt = new JdbcTemplate(dataSource);
		// SimpleDateFormat sdf1 = new SimpleDateFormat("dd/MM/yyyy");
		// String dateSt = sdf1.format(new Date());
		String req =
				" select COD_STRC_STRC,"
						+ "  REGEXP_REPLACE(REGEXP_SUBSTR(DONNE_OPER_CRO,'MNT_GLOB_RET=[^;]+;?'), 'MNT_GLOB_RET=([^;]+);?', '\1') as MNT_GLOB_RET, "
						+ "  REGEXP_REPLACE(REGEXP_SUBSTR(DONNE_OPER_CRO,'NUM_CAIS_CAIS=[^;]+;?'), 'NUM_CAIS_CAIS=([^;]+);?', '\1') as NUM_CAIS_CAIS"
						+ "  ,to_char(DAT_OPER_CRO, 'dd/MM/yyyy') as DAT_OPER_CRO,DAT_VAL_CRO,COD_TYP_OPER,lpad( COD_REF_INTER,9,'0') as COD_REF_INTER,to_char(DAT_OPER_CRO, 'ddMMyyyy')DAT_OPER_CRO_S ,"
						+ "  to_char(DAT_VAL_CRO, 'ddMMyyyy')DAT_VAL_CRO_S  ,decode(COD_TYP_OPER,'O','+','A','-') as SENS "
						+ "  from cro where  "
						+ "  dat_oper_cro= (SELECT max(journee_structure.dat_jrn_jrn) FROM journee_structure "
						+ "  WHERE cod_strc_strc=900 and COD_STAT_JRN=1)"
						+ "  and cod_oper_oper=1054    and cod_prd_prd=2248    order by cod_strc_strc";

		list = jt.query(req, new RowMapper() {

			public String[] mapRow(ResultSet rs, int rowNum) throws SQLException {
				String[] rslt = { "", "", "", "", "", "", "", "" };
				rslt[0] = String.valueOf("MNT_GLOB_RET");
				rslt[1] = String.valueOf("NUM_CAIS_CAIS");
				rslt[2] = String.valueOf("COD_STRC_STRC");
				rslt[3] = rs.getString("DAT_OPER_CRO_S");
				rslt[4] = rs.getString("DAT_OPER_CRO");
				rslt[5] = rs.getString("DAT_VAL_CRO_S");
				rslt[6] = rs.getString("SENS");
				rslt[7] = rs.getString("COD_REF_INTER");
				return rslt;

			}

		});
		return list;
	}

	public List<String[]> getMntSortDetailSessionCaisseGabByDate(Date dateJournee) {
		List<String[]> list = new ArrayList<String[]>();
		jt = new JdbcTemplate(dataSource);
		SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
		// String dateSt = sdf1.format(new Date());
		String req =
				"SELECT det.MONT_TOTS_DSC,sess.NUM_CAIS_CAIS,to_char(to_date(sess.DAT_JRN_JRN, 'DD/MM/YY'), 'DD/MM/YY')DAT_JRN_JRN,"
						+ "  to_char(to_date(sess.DAT_JRN_JRN, 'DD/MM/YY'), 'ddmmyyyy')DAT_JRN_JRN_S,det.COD_DEV_DEV,sess.COD_TYP_SJC,sess.COD_STRC_JRN, "
						+ "  sess.COD_STAT_SJC,sess.NUM_MATR_USER,cais.COD_TYP_CAIS FROM SMILE.DETAIL_SESSION_CAISSE det "
						+ "   INNER JOIN SESSION_JRN_CAISSE sess ON det.NUM_SEQ_SJC = sess.NUM_SEQ_SJC "
						+ "   INNER JOIN JOURNEE_CAISSE jrnCais ON sess.COD_STRC_JRN = jrnCais.COD_STRC_JRN AND sess.DAT_JRN_JRN = jrnCais.DAT_JRN_JRN "
						+ "   AND sess.NUM_CAIS_CAIS = jrnCais.NUM_CAIS_CAIS "
						+ "   INNER JOIN CAISSE_STRC cais ON jrnCais.COD_STRC_JRN = cais.COD_STRC_STRC "
						+ "   AND jrnCais.NUM_CAIS_CAIS = cais.NUM_CAIS_CAIS "
						+ "   where sess.COD_STAT_SJC='F' and sess.cod_typ_sjc='P' "
						+ "   and cais.COD_TYP_CAIS='GAB' AND det.COD_DEV_DEV=788 " + "   and sess.DAT_JRN_JRN ='"
						+ format.format(dateJournee) + "'";

		list = jt.query(req, new RowMapper() {

			public String[] mapRow(ResultSet rs, int rowNum) throws SQLException {
				String[] rslt = { "", "", "", "", "" };
				rslt[0] = String.valueOf(rs.getLong("MONT_TOTS_DSC"));
				rslt[1] = String.valueOf(rs.getLong("NUM_CAIS_CAIS"));
				rslt[2] = String.valueOf(rs.getLong("COD_STRC_JRN"));
				rslt[3] = rs.getString("DAT_JRN_JRN_S");
				rslt[4] = rs.getString("DAT_JRN_JRN");

				return rslt;

			}

		});
		return list;
	}

	public Long getSequenceMouvementCaisse() {
		jt = new JdbcTemplate(dataSource);
		Long numeroSequence = (Long) jt.queryForObject("select count(*)+1  from mouvements_caisses ", Long.class);
		return numeroSequence;
	}

	public Long getNbrDecimalDevise(Long codDev) {
		jt = new JdbcTemplate(dataSource);
		Long nbrUnite =
				(Long) jt.queryForObject(
						"select NBR_DEC_DEV  from DEVISE\n" + "        where COD_DEV_DEV=" + codDev.intValue(),
						Long.class);
		return nbrUnite;
	}

	public String formaterMnt(double mnt, Long codDevDev) {

		jt = new JdbcTemplate(dataSource);
		Long nbrUnite =
				(Long) jt.queryForObject(
						"select NBR_DEC_DEV  from DEVISE\n" + "        where COD_DEV_DEV=" + codDevDev, Long.class);
		DecimalFormat df = new DecimalFormat("# #0.0000");
		Double mntD = Double.valueOf(mnt) / Math.pow(10, nbrUnite);
		if (nbrUnite.intValue() == 3) {
			df = new DecimalFormat("# #0.000");
		} else {
			if (nbrUnite.intValue() == 2) {
				df = new DecimalFormat("# #0.00");
			} else {
				if (nbrUnite.intValue() == 1) {
					df = new DecimalFormat("# #0.0");
				} else {
					if (nbrUnite.intValue() == 0) {
						df = new DecimalFormat("# #0");
					}
				}
			}
		}
		DecimalFormatSymbols dcmlFS = new DecimalFormatSymbols();
		dcmlFS.setDecimalSeparator('.');

		df.setDecimalFormatSymbols(dcmlFS);

		return df.format(mntD);

	}

	public Double getCoursAchatBna(String codedev, Date dateJrs) {
		jt = new JdbcTemplate(dataSource);
		SimpleDateFormat sdf1 = new SimpleDateFormat("dd/MM/yyyy");
		String dc = sdf1.format(dateJrs);
		String query =
				"select MONT_CABA_CCHN from COURS_CHANGE WHERE COD_ETAT_CCHN like 'V' and COD_DEV_DEV = " + codedev
						+ " and DAT_JOUR_CCHN=(SELECT max(DAT_JOUR_CCHN) from COURS_CHANGE where COD_DEV_DEV= "
						+ codedev + ")";

		Double cours;
		try {
			cours = (Double) jt.queryForObject(query, Double.class);
			if (cours == null) {
				cours = new Double(1);
			}
		} catch (Exception e) {
			cours = new Double(1);
		}

		return cours;
	}

	public Long getNbreUnitDev(Long codedev) {
		jt = new JdbcTemplate(dataSource);
		String query = "select MONT_CVBC_CCHN from devise WHEREand COD_DEV_DEV = " + codedev + ")";
		Long dev = new Long(0);

		try {
			dev = (Long) jt.queryForObject(query, Long.class);
			if (dev == null) {
				return 0L;
			} else
				return dev;
		} catch (Exception e) {
			dev = new Long(0);
		}
		return dev;

	}

}
