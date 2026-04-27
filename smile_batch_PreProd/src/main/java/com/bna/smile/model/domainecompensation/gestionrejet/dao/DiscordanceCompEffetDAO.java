package com.bna.smile.model.domainecompensation.gestionrejet.dao;

import java.io.Serializable;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.CallableStatementCallback;
import org.springframework.jdbc.core.CallableStatementCreator;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import com.bna.commun.model.Structure;
import com.bna.commun.util.ContextHandler;
import com.bna.commun.util.DateHandler;

public class DiscordanceCompEffetDAO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 
	 */
	protected String sqlQuery;
	/**
	 * 
	 */
	protected JdbcTemplate jt;
	/**
	 * 
	 */
	protected DataSource dataSource;

	/**
	 * 
	 */
	public DiscordanceCompEffetDAO() {
	}

	/**
	 * @param dataSource
	 */
	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	/**
	 * @param sqlQuery
	 */
	public void setSqlQuery(String sqlQuery) {
		this.sqlQuery = sqlQuery;
	}

	// Séquence table HIST_TRANCHE_SERVI
	public Long getSequenceDisc() {
		jt = new JdbcTemplate(dataSource);

		Long numeroSequence = (Long) jt.queryForObject("select SEQ_DISC_TELECOMP.NEXTVAL from dual ", Long.class);

		// System.out.println(numeroSequence);
		return numeroSequence;
	}

	public void insertDiscrodance(String motif, Long codStrc, String cod_bct, Long codOper, Long mntCro, Long mntExtra,
			String dateOper, Long numIdCro) {
		jt = new JdbcTemplate(dataSource);

		jt.update("Insert into DISCORDANCE_TELECOMPENSATION (NUM_SEQ_DISC,COD_OPER_OPER,NUM_REF_CRO,COD_STRC_STRC,COD_BCT_STRC,TOT_MNT_EXTRA,TOT_MNT_CRO,MOTF_DISC_COMP,DAT_OPER_DISC) "
				+ "values("
				+ getSequenceDisc()
				+ ","
				+ codOper
				+ ","
				+ numIdCro
				+ ","
				+ codStrc
				+ ",'"
				+ cod_bct
				+ "'," + mntExtra + "," + mntCro + "," + "'" + motif + "'," + "'" + dateOper + "')");

		System.out.println("Fin Insertion Discordance !");
	}

	private String getDonnCroValue(String param, String donnCro) {
		System.out.println(param);
		System.out.println(donnCro);
		String data[] = donnCro.split(";");
		List<String> liste = Arrays.asList(data);

		for (int i = 0; i < liste.size(); i++) {
			String donne = liste.get(i);
			if (donne.startsWith(param)) {
				String[] donnValue = donne.split("=");
				return donnValue[1];

			}

		}

		return "0";
	}

	private String getStrcBct(Long codestrc) {
		CompensationDAO compensationDAO = (CompensationDAO) ContextHandler.getContext().getBean("compensationDAO");
		Structure strc = compensationDAO.findStructure(codestrc);

		return strc.getCodBctStrc();
	}

	public List<Long> getAgPilolte() {
		jt = new JdbcTemplate(dataSource);

		List<Long> liste = new ArrayList<Long>();

		Long seq = 1L;
		SqlRowSet rs = jt.queryForRowSet("select cod_strc_strc from agence_pilote  ");
		while (rs.next())
			liste.add(rs.getLong("cod_strc_strc"));

		return liste;
	}

	private Long nbrEffetRecu(Long valeur, Long enr, String strcBct, String dateOper, String codeEtat) {
		jt = new JdbcTemplate(dataSource);
		String req =
				"select count(*) from trace_effet_recu where COD_ENR_EFF=" + enr + " and COD_VAL_EFF=" + valeur
						+ " and COD_AGE_DES='" + strcBct + "' and DAT_OPE_EFF='" + dateOper + "'";

		if (codeEtat != null) {
			req += " and COD_ETAT_EFF in (" + codeEtat + ")";
		}
		Long value = jt.queryForLong(req);
		return value;
	}

	private Long sommeEffetRecu(Long valeur, Long enr, String strcBct, String dateOper, String codeEtat) {
		jt = new JdbcTemplate(dataSource);
		String req =
				"select sum(MNT_EFF_EFF) from trace_effet_recu where COD_ENR_EFF=" + enr + " and COD_VAL_EFF=" + valeur
						+ " and COD_AGE_DES='" + strcBct + "' and DAT_OPE_EFF='" + dateOper + "'";
		if (codeEtat != null) {
			req += " and COD_ETAT_EFF in (" + codeEtat + ")";
		}
		Long value = jt.queryForLong(req);
		return value;
	}

	private Long sommeChequePaye806(String strcBct, String dateOper) {
		jt = new JdbcTemplate(dataSource);
		String req =
				"select sum(MNT_CHQ_CHQ) from cheque where" + "  COD_AGDE_CHQ='" + strcBct + "' and DAT_REG_CHQ='"
						+ dateOper + "'" + " and DAT_OPE_CHQ='" + dateOper + "'" + " and COD_ETAT_CHQ='P'";

		Long value = jt.queryForLong(req);
		return value;
	}

	private Long sommeChequePaye846(String strcBct, String dateOper) {
		jt = new JdbcTemplate(dataSource);
		String req =
				"select sum(MNT_CHQ_CHQ) from cheque where" + "  COD_AGDE_CHQ='" + strcBct + "' and DAT_REG_CHQ='"
						+ dateOper + "'" + " and DAT_OPE_CHQ<'" + dateOper + "' and cod_rej_chq=81"
						+ " and COD_ETAT_CHQ='P'";

		Long value = jt.queryForLong(req);
		return value;
	}

	private Long sommePreavis(String strcBct, String dateOper) {
		jt = new JdbcTemplate(dataSource);
		String req =
				"select * from preavis pr,cheque chq where"
						+ "  chq.COD_AGDE_CHQ='"
						+ strcBct
						+ "' and pr.DAT_PRE_PRE='"
						+ dateOper
						+ "'"

						+ " and chq.COD_ETAT_CHQ='R'"
						+ " and chq.NUM_CHQ_CHQ=pr.NUM_CHQ_CHQ and chq.RIB_TIR_CHQ=pr.RIB_TIR_CHQ and chq.RIB_BEN_CHQ=pr.RIB_BEN_CHQ";

		Long value = jt.queryForLong(req);
		return value;
	}

	private Long sommeDetailEffet(Long valeur, Long enr, String strcBct, String dateOper, String codeEtat) {
		jt = new JdbcTemplate(dataSource);
		String req =
				"select sum(MNT_EFF_EFF) from DETAIL_EFFET where COD_ENRG_EFF=" + enr + " and COD_VAL_EFF=" + valeur
						+ " and COD_AG_EM='" + strcBct + "' and DAT_OPE_EFF='" + dateOper + "'";
		if (codeEtat != null) {
			req += " and COD_ETAT_EFF in (" + codeEtat + ")";
		}
		Long value = jt.queryForLong(req);
		return value;
	}

	private Long sommeDetailEffetMan(Long valeur, Long enr, String strcBct, String dateOper, String codeEtat) {
		jt = new JdbcTemplate(dataSource);
		String req =
				"select sum(MNT_EFF_EFF) from DETAIL_EFFET where COD_ENRG_EFF=" + enr + " and COD_VAL_EFF=" + valeur
						+ " and COD_AG_EM='" + strcBct + "' and DAT_OPE_EFF='" + dateOper + "' and COD_TRT_EFF='MAN'";
		if (codeEtat != null) {
			req += " and COD_ETAT_EFF in (" + codeEtat + ")";
		}
		Long value = jt.queryForLong(req);
		return value;
	}

	private Long sommeDetailEffetEscompte(Long strc, String dateOper) {
		jt = new JdbcTemplate(dataSource);
		String req =
				"select  nvl(sum(e.MONT_EFFET_EFFESC),0)  from saebcg.credit_Esc c,saebcg.effet_esc e,saebcg.detail_effet_esc d "
						+ " where c.NUM_CRD_CRDESC=e.NUM_CRD_CRDESC" + " and c.DAT_DEBLCAGE_CRDESC=?"

						+ " and c.COD_ETATCRD_ETCRDESC='6'" + " and c.COD_STRC_STRC=?"

						+ " and e.COD_ETAT_ETEFFESC='1'" + " and e.num_effet_effesc =  d.num_deteffet_deteffesc"
						+ " and e.num_seq_effesc =   d.num_seq_deteffetesc"
						+ " and e.dat_oper_effesc =  d.dat_oper_deteffesc";

		Long value = jt.queryForLong(req, new Object[]{ dateOper, strc });
		return value;
	}

	private Long sommeOmpDin(String codeOper, Long strc, String dateOper) {
		jt = new JdbcTemplate(dataSource);
		String req =
				"select nvl(sum(MONT_DIN_OMP),0) from OPERATION_MOY_PAY where COD_OPER_OPER in (" + codeOper
						+ ") and COD_STRC_STRC=" + strc + " and  DAT_OPER_OMP='" + dateOper + "'";

		Long value = jt.queryForLong(req);
		return value;
	}

	private Long sommeOmpTva(String codeOper, Long strc, String dateOper) {
		jt = new JdbcTemplate(dataSource);
		String req =
				"select nvl(sum(nvl(MONT_TVA_OMP,0)),0) from OPERATION_MOY_PAY where COD_OPER_OPER in (" + codeOper
						+ ") and COD_STRC_STRC=" + strc + " and  DAT_OPER_OMP='" + dateOper + "'";

		Long value = jt.queryForLong(req);
		return value;
	}

	private Long sommeOmpComm(String codeOper, Long strc, String dateOper) {
		jt = new JdbcTemplate(dataSource);
		String req =
				"select nvl(sum(MONT_VAL_DOMP),0) from DETAIL_OPER_MOY_PAIEMENT where DETAIL_OPER_MOY_PAIEMENT.NUM_OPER_OMP in  (select OPERATION_MOY_PAY.NUM_OPER_OMP from OPERATION_MOY_PAY where COD_OPER_OPER in ("
						+ codeOper + ") and COD_STRC_STRC=" + strc + " and  DAT_OPER_OMP='" + dateOper + "')";

		Long value = jt.queryForLong(req);
		return value;
	}

	public List<String> getDonnCro(Long codOper, Long cod_prd, Long codStrcImp, String dateOper) {
		jt = new JdbcTemplate(dataSource);
		SqlRowSet srs = null;
		String req =
				"select DONNE_OPER_CRO   from cro where COD_STRC_IMPT=" + codStrcImp + " and cod_oper_oper=" + codOper
						+ " and cod_prd_prd=" + cod_prd + " and DAT_OPER_CRO='" + dateOper + "'";
		srs = jt.queryForRowSet(req);

		List<String> data = new ArrayList<String>();

		while (srs.next()) {

			String donneCro = srs.getString("DONNE_OPER_CRO");
			data.add(donneCro);
		}
		return data;
	}

	public void verifCro825(String dateOper, Long strc) {
		jt = new JdbcTemplate(dataSource);

		String req = "";
		Long totCro = 0L;
		Long totExtra = 0L;
		// 1- Verification Cro et son extra
		SqlRowSet srs = null;
		req =
				"select DONNE_OPER_CRO ,COD_STRC_STRC,COD_STRC_IMPT,NUM_REF_CRO,NUM_ID_CRO,COD_REF_INTER,COD_REFC_OMP from cro where cod_oper_oper=825 and cod_prd_prd=1061 and COD_STRC_IMPT="
						+ strc + " and DAT_OPER_CRO='" + dateOper + "'";
		srs = jt.queryForRowSet(req);

		while (srs.next()) {

			String donneCro = srs.getString("DONNE_OPER_CRO");
			Long idCro = srs.getLong("NUM_ID_CRO");

			Long nbrCro = Long.valueOf(getDonnCroValue("NBR_LOT_RECU_EFF", donneCro));
			Long mntCro = Long.valueOf(getDonnCroValue("MNT_LOT_RECU_EFF", donneCro));
			totCro += mntCro;
			if (!nbrCro.equals(0L) && !mntCro.equals(0L)) {
				Long nbrExtra = nbrEffetRecu(41L, 21L, getStrcBct(strc), dateOper, "'P','R'");
				Long mntExtra = sommeEffetRecu(41L, 21L, getStrcBct(strc), dateOper, "'P','R'");

				if (!nbrCro.equals(nbrExtra) || !mntExtra.equals(mntCro)) {
					insertDiscrodance("Discordance cro 825", strc, getStrcBct(strc), 825L, mntExtra, mntCro, dateOper,
							idCro);
				}

			}
		}

		// 2- Verification Extra et son cro

		Long nbrExtra = nbrEffetRecu(41L, 21L, getStrcBct(strc), dateOper, "'P','R'");
		Long mntExtra = sommeEffetRecu(41L, 21L, getStrcBct(strc), dateOper, "'P','R'");
		totExtra += mntExtra;

		Long nbrCro = 0L;
		Long mntCro = 0L;

		List<String> data = getDonnCro(825L, 1061L, strc, dateOper);

		for (int j = 0; j < data.size(); j++) {

			nbrCro += Long.valueOf(getDonnCroValue("NBR_LOT_RECU_EFF", data.get(j)));
			mntCro += Long.valueOf(getDonnCroValue("MNT_LOT_RECU_EFF", data.get(j)));
		}
		if (!nbrCro.equals(nbrExtra) || !mntExtra.equals(mntCro)) {
			insertDiscrodance("Discordance extra 825", strc, getStrcBct(strc), 825L, mntExtra, mntCro, dateOper, null);
		}

	}

	public void verifCro832(String dateOper, Long strc) {
		jt = new JdbcTemplate(dataSource);

		String req = "";
		Long totCro = 0L;
		Long totExtra = 0L;
		// 1- Verification Cro et son extra
		SqlRowSet srs = null;
		req =
				"select DONNE_OPER_CRO ,COD_STRC_STRC,COD_STRC_IMPT,NUM_REF_CRO,NUM_ID_CRO,COD_REF_INTER,COD_REFC_OMP from cro where cod_oper_oper=832 and cod_prd_prd=1061 and COD_STRC_IMPT="
						+ strc + " and DAT_OPER_CRO='" + dateOper + "'";
		srs = jt.queryForRowSet(req);

		while (srs.next()) {

			String donneCro = srs.getString("DONNE_OPER_CRO");
			Long idCro = srs.getLong("NUM_ID_CRO");

			Long nbrCro = Long.valueOf(getDonnCroValue("NBR_LOT_RECU_EFF", donneCro));
			Long mntCro = Long.valueOf(getDonnCroValue("MNT_LOT_RECU_EFF", donneCro));
			totCro += mntCro;
			if (!nbrCro.equals(0L) && !mntCro.equals(0L)) {
				Long nbrExtra = nbrEffetRecu(40L, 21L, getStrcBct(strc), dateOper, "'P','R'");
				Long mntExtra = sommeEffetRecu(40L, 21L, getStrcBct(strc), dateOper, "'P','R'");

				if (!nbrCro.equals(nbrExtra) || !mntExtra.equals(mntCro)) {
					insertDiscrodance("Discordance cro 832", strc, getStrcBct(strc), 832L, mntExtra, mntCro, dateOper,
							idCro);
				}

			}
		}

		// 2- Verification Extra et son cro

		Long nbrExtra = nbrEffetRecu(40L, 21L, getStrcBct(strc), dateOper, "'P','R'");
		Long mntExtra = sommeEffetRecu(40L, 21L, getStrcBct(strc), dateOper, "'P','R'");
		totExtra += mntExtra;

		Long nbrCro = 0L;
		Long mntCro = 0L;

		List<String> data = getDonnCro(832L, 1061L, strc, dateOper);

		for (int j = 0; j < data.size(); j++) {

			nbrCro += Long.valueOf(getDonnCroValue("NBR_LOT_RECU_EFF", data.get(j)));
			mntCro += Long.valueOf(getDonnCroValue("MNT_LOT_RECU_EFF", data.get(j)));
		}
		if (!nbrCro.equals(nbrExtra) || !mntExtra.equals(mntCro)) {
			insertDiscrodance("Discordance extra 832", strc, getStrcBct(strc), 832L, mntExtra, mntCro, dateOper, null);
		}

	}

	public void verifCro838(String dateOper, Long strc) {
		jt = new JdbcTemplate(dataSource);

		String req = "";
		Long totCro = 0L;
		Long totExtra = 0L;
		// 1- Verification Cro et son extra
		SqlRowSet srs = null;
		req =
				"select DONNE_OPER_CRO ,COD_STRC_STRC,COD_STRC_IMPT,NUM_REF_CRO,NUM_ID_CRO,COD_REF_INTER,COD_REFC_OMP from cro where cod_oper_oper=838 and cod_prd_prd=1061 and COD_STRC_IMPT="
						+ strc + " and DAT_OPER_CRO='" + dateOper + "'";
		srs = jt.queryForRowSet(req);

		while (srs.next()) {

			String donneCro = srs.getString("DONNE_OPER_CRO");
			Long idCro = srs.getLong("NUM_ID_CRO");

			Long nbrCro = Long.valueOf(getDonnCroValue("NBR_GLB_OC", donneCro));
			Long mntCro = Long.valueOf(getDonnCroValue("MNT_GLB_OC", donneCro));
			totCro += mntCro;
			if (!nbrCro.equals(0L) && !mntCro.equals(0L)) {
				Long nbrExtra = nbrEffetRecu(42L, 21L, getStrcBct(strc), dateOper, "'P','R'");
				Long mntExtra = sommeEffetRecu(42L, 21L, getStrcBct(strc), dateOper, "'P','R'");

				if (!nbrCro.equals(nbrExtra) || !mntExtra.equals(mntCro)) {
					insertDiscrodance("Discordance cro 838", strc, getStrcBct(strc), 838L, mntExtra, mntCro, dateOper,
							idCro);
				}

			}
		}

		// 2- Verification Extra et son cro

		Long nbrExtra = nbrEffetRecu(42L, 21L, getStrcBct(strc), dateOper, "'P','R'");
		Long mntExtra = sommeEffetRecu(42L, 21L, getStrcBct(strc), dateOper, "'P','R'");
		totExtra += mntExtra;

		Long nbrCro = 0L;
		Long mntCro = 0L;

		List<String> data = getDonnCro(838L, 1061L, strc, dateOper);

		for (int j = 0; j < data.size(); j++) {

			nbrCro += Long.valueOf(getDonnCroValue("NBR_GLB_OC", data.get(j)));
			mntCro += Long.valueOf(getDonnCroValue("MNT_GLB_OC", data.get(j)));
		}
		if (!nbrCro.equals(nbrExtra) || !mntExtra.equals(mntCro)) {
			insertDiscrodance("Discordance extra 838", strc, getStrcBct(strc), 838L, mntExtra, mntCro, dateOper, null);
		}

	}

	public void verifCro841(String dateOper, Long strc) {
		jt = new JdbcTemplate(dataSource);

		String req = "";
		Long totCro = 0L;
		Long totExtra = 0L;
		// 1- Verification Cro et son extra
		SqlRowSet srs = null;
		req =
				"select DONNE_OPER_CRO ,COD_STRC_STRC,COD_STRC_IMPT,NUM_REF_CRO,NUM_ID_CRO,COD_REF_INTER,COD_REFC_OMP from cro where cod_oper_oper=841 and cod_prd_prd=1061 and COD_STRC_IMPT="
						+ strc + " and DAT_OPER_CRO='" + dateOper + "'";
		srs = jt.queryForRowSet(req);

		while (srs.next()) {

			String donneCro = srs.getString("DONNE_OPER_CRO");
			Long idCro = srs.getLong("NUM_ID_CRO");

			Long nbrCro = Long.valueOf(getDonnCroValue("NBR_LOT_RECU_EFF", donneCro));
			Long mntCro = Long.valueOf(getDonnCroValue("MNT_LOT_RECU_EFF", donneCro));
			totCro += mntCro;
			if (!nbrCro.equals(0L) && !mntCro.equals(0L)) {
				Long nbrExtra = nbrEffetRecu(43L, 21L, getStrcBct(strc), dateOper, "'P','R'");
				Long mntExtra = sommeEffetRecu(43L, 21L, getStrcBct(strc), dateOper, "'P','R'");

				if (!nbrCro.equals(nbrExtra) || !mntExtra.equals(mntCro)) {
					insertDiscrodance("Discordance cro 841", strc, getStrcBct(strc), 841L, mntExtra, mntCro, dateOper,
							idCro);
				}

			}
		}

		// 2- Verification Extra et son cro

		Long nbrExtra = nbrEffetRecu(43L, 21L, getStrcBct(strc), dateOper, "'P','R'");
		Long mntExtra = sommeEffetRecu(43L, 21L, getStrcBct(strc), dateOper, "'P','R'");
		totExtra += mntExtra;

		Long nbrCro = 0L;
		Long mntCro = 0L;

		List<String> data = getDonnCro(841L, 1061L, strc, dateOper);

		for (int j = 0; j < data.size(); j++) {

			nbrCro += Long.valueOf(getDonnCroValue("NBR_LOT_RECU_EFF", data.get(j)));
			mntCro += Long.valueOf(getDonnCroValue("MNT_LOT_RECU_EFF", data.get(j)));
		}
		if (!nbrCro.equals(nbrExtra) || !mntExtra.equals(mntCro)) {
			insertDiscrodance("Discordance extra 841", strc, getStrcBct(strc), 841L, mntExtra, mntCro, dateOper, null);
		}

	}

	public void verifCro724(String dateOper, Long strc) {
		jt = new JdbcTemplate(dataSource);

		String req = "";
		Long totCro = 0L;
		Long totExtra = 0L;
		// 1- Verification Cro et son extra
		SqlRowSet srs = null;
		req =
				"select DONNE_OPER_CRO ,COD_STRC_STRC,COD_STRC_IMPT,NUM_REF_CRO,NUM_ID_CRO,COD_REF_INTER,COD_REFC_OMP from cro where cod_oper_oper=724 and cod_prd_prd=1061 and COD_STRC_IMPT="
						+ strc + " and DAT_OPER_CRO='" + dateOper + "'";
		srs = jt.queryForRowSet(req);

		while (srs.next()) {

			String donneCro = srs.getString("DONNE_OPER_CRO");

			// Long nbrCro = Long.valueOf(getDonnCroValue("NBR_EFF_IMP_DENO", donneCro));
			Long mntCro = Long.valueOf(getDonnCroValue("MNT_EFF_IMP_DENO", donneCro));
			totCro += mntCro;

		}

		// Long nbrExtra = nbrEffetRecu(41L, 22L, getStrcBct(strc), dateOper, "'P','R'");
		totExtra = sommeEffetRecu(41L, 22L, getStrcBct(strc), dateOper, "'P','R'");

		if (!totExtra.equals(totCro)) {
			insertDiscrodance("Discordance cro 724", strc, getStrcBct(strc), 724L, totExtra, totCro, dateOper, null);
		}

		// 2- Verification Extra et son cro

		// Long nbrExtra = nbrEffetRecu(41L, 22L, getStrcBct(strc), dateOper, "'P','R'");
		Long mntExtra = sommeEffetRecu(41L, 22L, getStrcBct(strc), dateOper, "'P','R'");

		Long mntCro = 0L;

		List<String> data = getDonnCro(724L, 1061L, strc, dateOper);

		for (int j = 0; j < data.size(); j++) {

			// nbrCro += Long.valueOf(getDonnCroValue("NBR_LOT_RECU_EFF", data.get(j)));
			mntCro += Long.valueOf(getDonnCroValue("MNT_EFF_IMP_DENO", data.get(j)));
		}
		if (!mntExtra.equals(mntCro)) {
			insertDiscrodance("Discordance extra 724", strc, getStrcBct(strc), 724L, mntExtra, mntCro, dateOper, null);
		}

	}

	public void verifCro1018(String dateOper, Long strc) {
		jt = new JdbcTemplate(dataSource);

		String req = "";
		Long totCro = 0L;
		Long totExtra = 0L;
		// 1- Verification Cro et son extra
		SqlRowSet srs = null;
		req =
				"select DONNE_OPER_CRO ,COD_STRC_STRC,COD_STRC_IMPT,NUM_REF_CRO,NUM_ID_CRO,COD_REF_INTER,COD_REFC_OMP from cro where cod_oper_oper=1018 and cod_prd_prd=1061 and COD_STRC_IMPT="
						+ strc + " and DAT_OPER_CRO='" + dateOper + "'";
		srs = jt.queryForRowSet(req);

		while (srs.next()) {

			String donneCro = srs.getString("DONNE_OPER_CRO");

			// Long nbrCro = Long.valueOf(getDonnCroValue("NBR_EFF_IMP_DENO", donneCro));
			Long mntCro = Long.valueOf(getDonnCroValue("MNT_EFF_IMP_DENO", donneCro));
			totCro += mntCro;

		}

		// Long nbrExtra = nbrEffetRecu(41L, 22L, getStrcBct(strc), dateOper, "'P','R'");
		totExtra = sommeEffetRecu(40L, 22L, getStrcBct(strc), dateOper, "'P','R'");

		if (!totExtra.equals(totCro)) {
			insertDiscrodance("Discordance cro 1018", strc, getStrcBct(strc), 1018L, totExtra, totCro, dateOper, null);
		}

		// 2- Verification Extra et son cro

		// Long nbrExtra = nbrEffetRecu(41L, 22L, getStrcBct(strc), dateOper, "'P','R'");
		Long mntExtra = sommeEffetRecu(40L, 22L, getStrcBct(strc), dateOper, "'P','R'");

		Long mntCro = 0L;

		List<String> data = getDonnCro(1018L, 1061L, strc, dateOper);

		for (int j = 0; j < data.size(); j++) {

			// nbrCro += Long.valueOf(getDonnCroValue("NBR_LOT_RECU_EFF", data.get(j)));
			mntCro += Long.valueOf(getDonnCroValue("MNT_EFF_IMP_DENO", data.get(j)));
		}
		if (!mntExtra.equals(mntCro)) {
			insertDiscrodance("Discordance extra 1018", strc, getStrcBct(strc), 1018L, mntExtra, mntCro, dateOper, null);
		}

	}

	public void verifCro962(String dateOper, Long strc) {
		jt = new JdbcTemplate(dataSource);
		Long codeOper = 962L;
		Long codeValeur = 41L;
		Long codeEnrg = 25L;
		String req = "";
		Long totCro = 0L;
		Long totExtra = 0L;
		// 1- Verification Cro et son extra
		SqlRowSet srs = null;
		req =
				"select DONNE_OPER_CRO ,COD_STRC_STRC,COD_STRC_IMPT,NUM_REF_CRO,NUM_ID_CRO,COD_REF_INTER,COD_REFC_OMP from cro where cod_oper_oper="
						+ codeOper
						+ " and cod_prd_prd=1061 and COD_STRC_IMPT="
						+ strc
						+ " and DAT_OPER_CRO='"
						+ dateOper + "'";
		srs = jt.queryForRowSet(req);

		while (srs.next()) {

			String donneCro = srs.getString("DONNE_OPER_CRO");

			// Long nbrCro = Long.valueOf(getDonnCroValue("NBR_EFF_IMP_DENO", donneCro));
			Long mntCro = Long.valueOf(getDonnCroValue("MNT_EFF_EFF", donneCro));
			totCro += mntCro;

		}

		// Long nbrExtra = nbrEffetRecu(41L, 22L, getStrcBct(strc), dateOper, "'P','R'");
		totExtra = sommeEffetRecu(codeValeur, codeEnrg, getStrcBct(strc), dateOper, "'P','R'");

		if (!totExtra.equals(totCro)) {
			insertDiscrodance("Discordance cro " + codeOper, strc, getStrcBct(strc), codeOper, totExtra, totCro,
					dateOper, null);
		}

		// 2- Verification Extra et son cro

		// Long nbrExtra = nbrEffetRecu(41L, 22L, getStrcBct(strc), dateOper, "'P','R'");
		Long mntExtra = sommeEffetRecu(codeValeur, codeEnrg, getStrcBct(strc), dateOper, "'P','R'");

		Long mntCro = 0L;

		List<String> data = getDonnCro(codeOper, 1061L, strc, dateOper);

		for (int j = 0; j < data.size(); j++) {

			// nbrCro += Long.valueOf(getDonnCroValue("NBR_LOT_RECU_EFF", data.get(j)));
			mntCro += Long.valueOf(getDonnCroValue("MNT_EFF_EFF", data.get(j)));
		}
		if (!mntExtra.equals(mntCro)) {
			insertDiscrodance("Discordance extra " + codeOper, strc, getStrcBct(strc), codeOper, mntExtra, mntCro,
					dateOper, null);
		}

		// verification extra et OMP

		Long mntOmp = sommeOmpDin("" + codeOper, strc, dateOper);
		if (!mntExtra.equals(mntOmp)) {
			insertDiscrodance("Discordance extra OMP " + codeOper, strc, getStrcBct(strc), codeOper, mntExtra, mntOmp,
					dateOper, null);
		}

	}

	public void verifCro722(String dateOper, Long strc) {
		jt = new JdbcTemplate(dataSource);
		Long codeOper = 722L;
		Long codeValeur = 41L;
		Long codeEnrg = 21L;
		String req = "";
		Long totMntCro = 0L;
		Long totCommCro = 0L;
		Long totTvaCro = 0L;

		Long totExtra = 0L;
		// 1- Verification Cro et son extra
		SqlRowSet srs = null;
		req =
				"select DONNE_OPER_CRO ,COD_STRC_STRC,COD_STRC_IMPT,NUM_REF_CRO,NUM_ID_CRO,COD_REF_INTER,COD_REFC_OMP from cro where cod_oper_oper="
						+ codeOper
						+ " and cod_prd_prd=1061 and COD_STRC_IMPT="
						+ strc
						+ " and DAT_OPER_CRO='"
						+ dateOper + "'";
		srs = jt.queryForRowSet(req);

		while (srs.next()) {

			String donneCro = srs.getString("DONNE_OPER_CRO");

			// Long nbrCro = Long.valueOf(getDonnCroValue("NBR_EFF_IMP_DENO", donneCro));
			totMntCro += Long.valueOf(getDonnCroValue("MNT_GLB_EFF", donneCro));

			totCommCro += Long.valueOf(getDonnCroValue("1", donneCro));
			totTvaCro += Long.valueOf(getDonnCroValue("MNT_TVA_EFF", donneCro));

		}

		// Long nbrExtra = nbrEffetRecu(41L, 22L, getStrcBct(strc), dateOper, "'P','R'");
		totExtra = sommeDetailEffet(codeValeur, codeEnrg, getStrcBct(strc), dateOper, "'T','V','E'");

		if (!totExtra.equals(totMntCro)) {
			insertDiscrodance("Discordance mnt cro " + codeOper, strc, getStrcBct(strc), codeOper, totExtra, totMntCro,
					dateOper, null);
		}

		// 2- Verification Extra et son cro

		// Long nbrExtra = nbrEffetRecu(41L, 22L, getStrcBct(strc), dateOper, "'P','R'");

		// Long mntCro = 0L;
		//
		// List<String> data = getDonnCro(codeOper, 1061L, strc, dateOper);
		//
		// for (int j = 0; j < data.size(); j++) {
		//
		// // nbrCro += Long.valueOf(getDonnCroValue("NBR_LOT_RECU_EFF", data.get(j)));
		// mntCro += Long.valueOf(getDonnCroValue("MNT_GLB_EFF", data.get(j)));
		// }
		// if (!totExtra.equals(mntCro)) {
		// insertDiscrodance("Discordance extra " + codeOper, strc, getStrcBct(strc), codeOper, totExtra, mntCro,
		// dateOper, null);
		// }

		// verification extra et OMP

		// Long mntOmp = sommeOmpDin(codeOper, strc, dateOper);
		// if (!totMntCro.equals(mntOmp)) {
		// insertDiscrodance("Discordance CRO OMP DIN" + codeOper, strc, getStrcBct(strc), codeOper, totMntCro, mntOmp,
		// dateOper, null);
		// }
		Long mntTva = sommeOmpTva("" + codeOper, strc, dateOper);
		if (!totTvaCro.equals(mntTva)) {
			insertDiscrodance("Discordance cro OMP TVA" + codeOper, strc, getStrcBct(strc), codeOper, totTvaCro,
					mntTva, dateOper, null);
		}
		Long mntComm = sommeOmpComm("" + codeOper, strc, dateOper);

		if (!totCommCro.equals(mntComm)) {
			insertDiscrodance("Discordance cro OMP Commission" + codeOper, strc, getStrcBct(strc), codeOper,
					totCommCro, mntComm, dateOper, null);
		}

	}

	public void verifCro728(String dateOper, Long strc) {
		jt = new JdbcTemplate(dataSource);
		Long codeOper = 728L;
		Long codeValeur = 41L;
		Long codeEnrg = 21L;
		String req = "";
		Long totMntCro = 0L;

		Long totExtra = 0L;
		// 1- Verification Cro et son extra
		SqlRowSet srs = null;
		req =
				"select DONNE_OPER_CRO ,COD_STRC_STRC,COD_STRC_IMPT,NUM_REF_CRO,NUM_ID_CRO,COD_REF_INTER,COD_REFC_OMP from cro where cod_oper_oper="
						+ codeOper
						+ " and cod_prd_prd=1061 and COD_STRC_IMPT="
						+ strc
						+ " and DAT_OPER_CRO='"
						+ dateOper + "'";
		srs = jt.queryForRowSet(req);

		while (srs.next()) {

			String donneCro = srs.getString("DONNE_OPER_CRO");

			// Long nbrCro = Long.valueOf(getDonnCroValue("NBR_EFF_IMP_DENO", donneCro));
			totMntCro += Long.valueOf(getDonnCroValue("MNT_GLB_JOUR_EFF", donneCro));

		}

		// Long nbrExtra = nbrEffetRecu(41L, 22L, getStrcBct(strc), dateOper, "'P','R'");
		totExtra = sommeDetailEffet(codeValeur, codeEnrg, getStrcBct(strc), dateOper, "'T','V'");

		if (!totExtra.equals(totMntCro)) {
			insertDiscrodance("Discordance mnt cro " + codeOper, strc, getStrcBct(strc), codeOper, totExtra, totMntCro,
					dateOper, null);
		}

	}

	public void verifCro2081(String dateOper, Long strc) {
		jt = new JdbcTemplate(dataSource);
		Long codeOper = 2081L;
		Long codeValeur = 41L;
		Long codeEnrg = 21L;
		String req = "";
		Long totMntCro = 0L;

		Long totExtra = 0L;
		// 1- Verification Cro et son extra
		SqlRowSet srs = null;
		req =
				"select DONNE_OPER_CRO ,COD_STRC_STRC,COD_STRC_IMPT,NUM_REF_CRO,NUM_ID_CRO,COD_REF_INTER,COD_REFC_OMP from cro where cod_oper_oper="
						+ codeOper
						+ " and cod_prd_prd=1061 and COD_STRC_IMPT="
						+ strc
						+ " and DAT_OPER_CRO='"
						+ dateOper + "'";
		srs = jt.queryForRowSet(req);

		while (srs.next()) {

			String donneCro = srs.getString("DONNE_OPER_CRO");

			// Long nbrCro = Long.valueOf(getDonnCroValue("NBR_EFF_IMP_DENO", donneCro));
			totMntCro += Long.valueOf(getDonnCroValue("MNT_GLB_JOUR_EFF", donneCro));

		}

		// Long nbrExtra = nbrEffetRecu(41L, 22L, getStrcBct(strc), dateOper, "'P','R'");
		totExtra = sommeDetailEffetMan(codeValeur, codeEnrg, getStrcBct(strc), dateOper, "'T','V'");

		if (!totExtra.equals(totMntCro)) {
			insertDiscrodance("Discordance mnt cro " + codeOper, strc, getStrcBct(strc), codeOper, totExtra, totMntCro,
					dateOper, null);
		}

	}

	public void verifCro723(String dateOper, Long strc) {
		jt = new JdbcTemplate(dataSource);
		Long codeOper = 723L;
		Long codeValeur = 41L;
		Long codeEnrg = 21L;
		String req = "";
		Long totMntCro = 0L;

		Long totExtra = 0L;
		// 1- Verification Cro et son extra
		SqlRowSet srs = null;
		req =
				"select DONNE_OPER_CRO ,COD_STRC_STRC,COD_STRC_IMPT,NUM_REF_CRO,NUM_ID_CRO,COD_REF_INTER,COD_REFC_OMP from cro where cod_oper_oper="
						+ codeOper
						+ " and cod_prd_prd=2123 and COD_STRC_IMPT="
						+ strc
						+ " and DAT_OPER_CRO='"
						+ dateOper + "'";
		srs = jt.queryForRowSet(req);

		while (srs.next()) {

			String donneCro = srs.getString("DONNE_OPER_CRO");

			// Long nbrCro = Long.valueOf(getDonnCroValue("NBR_EFF_IMP_DENO", donneCro));
			totMntCro += Long.valueOf(getDonnCroValue("MNT_GLB_JOUR_EFFESC", donneCro));

		}

		// Long nbrExtra = nbrEffetRecu(41L, 22L, getStrcBct(strc), dateOper, "'P','R'");
		totExtra = sommeDetailEffetEscompte(strc, dateOper);

		if (!totExtra.equals(totMntCro)) {
			insertDiscrodance("Discordance mnt cro " + codeOper, strc, getStrcBct(strc), codeOper, totExtra, totMntCro,
					dateOper, null);
		}

	}

	public void verifCro826(String dateOper, Long strc) {
		jt = new JdbcTemplate(dataSource);
		Long codeOper = 826L;
		Long codeValeur = 41L;
		Long codeEnrg = 21L;
		String req = "";
		Long totMntCro = 0L;
		Long totCommCro = 0L;
		Long totTvaCro = 0L;

		Long totExtra = 0L;
		// 1- Verification Cro et son extra
		SqlRowSet srs = null;
		req =
				"select DONNE_OPER_CRO ,COD_STRC_STRC,COD_STRC_IMPT,NUM_REF_CRO,NUM_ID_CRO,COD_REF_INTER,COD_REFC_OMP from cro where cod_oper_oper="
						+ codeOper
						+ " and cod_prd_prd=1061 and COD_STRC_IMPT="
						+ strc
						+ " and DAT_OPER_CRO='"
						+ dateOper + "'";
		srs = jt.queryForRowSet(req);

		while (srs.next()) {

			String donneCro = srs.getString("DONNE_OPER_CRO");

			// Long nbrCro = Long.valueOf(getDonnCroValue("NBR_EFF_IMP_DENO", donneCro));
			totMntCro += Long.valueOf(getDonnCroValue("MNT_EFF_EFF", donneCro));
			totCommCro += Long.valueOf(getDonnCroValue("197", donneCro));
			totTvaCro += Long.valueOf(getDonnCroValue("MNT_TVA_EFF", donneCro));

		}

		// Long nbrExtra = nbrEffetRecu(41L, 22L, getStrcBct(strc), dateOper, "'P','R'");
		totExtra = sommeEffetRecu(codeValeur, codeEnrg, getStrcBct(strc), dateOper, "'P'");

		if (!totExtra.equals(totMntCro)) {
			insertDiscrodance("Discordance mnt cro " + codeOper, strc, getStrcBct(strc), codeOper, totExtra, totMntCro,
					dateOper, null);
		}

		// 2- Verification Extra et son cro

		// Long nbrExtra = nbrEffetRecu(41L, 22L, getStrcBct(strc), dateOper, "'P','R'");

		// Long mntCro = 0L;
		//
		// List<String> data = getDonnCro(codeOper, 1061L, strc, dateOper);
		//
		// for (int j = 0; j < data.size(); j++) {
		//
		// // nbrCro += Long.valueOf(getDonnCroValue("NBR_LOT_RECU_EFF", data.get(j)));
		// mntCro += Long.valueOf(getDonnCroValue("MNT_GLB_EFF", data.get(j)));
		// }
		// if (!totExtra.equals(mntCro)) {
		// insertDiscrodance("Discordance extra " + codeOper, strc, getStrcBct(strc), codeOper, totExtra, mntCro,
		// dateOper, null);
		// }

		// verification extra et OMP

		Long mntOmp = sommeOmpDin("" + codeOper, strc, dateOper);
		if (!totMntCro.equals(mntOmp)) {
			insertDiscrodance("Discordance CRO OMP DIN" + codeOper, strc, getStrcBct(strc), codeOper, totMntCro,
					mntOmp, dateOper, null);
		}
		Long mntTva = sommeOmpTva("" + codeOper, strc, dateOper);
		if (!totTvaCro.equals(mntTva)) {
			insertDiscrodance("Discordance cro OMP TVA" + codeOper, strc, getStrcBct(strc), codeOper, totTvaCro,
					mntTva, dateOper, null);
		}
		Long mntComm = sommeOmpComm("" + codeOper, strc, dateOper);

		if (!totCommCro.equals(mntComm)) {
			insertDiscrodance("Discordance cro OMP Commission" + codeOper, strc, getStrcBct(strc), codeOper,
					totCommCro, mntComm, dateOper, null);
		}

	}

	public void verifCro833(String dateOper, Long strc) {
		jt = new JdbcTemplate(dataSource);
		Long codeOper = 833L;
		Long codeValeur = 40L;
		Long codeEnrg = 21L;
		String req = "";
		Long totMntCro = 0L;
		Long totCommCro = 0L;
		Long totTvaCro = 0L;

		Long totExtra = 0L;
		// 1- Verification Cro et son extra
		SqlRowSet srs = null;
		req =
				"select DONNE_OPER_CRO ,COD_STRC_STRC,COD_STRC_IMPT,NUM_REF_CRO,NUM_ID_CRO,COD_REF_INTER,COD_REFC_OMP from cro where cod_oper_oper="
						+ codeOper
						+ " and cod_prd_prd=1061 and COD_STRC_IMPT="
						+ strc
						+ " and DAT_OPER_CRO='"
						+ dateOper + "'";
		srs = jt.queryForRowSet(req);

		while (srs.next()) {

			String donneCro = srs.getString("DONNE_OPER_CRO");

			// Long nbrCro = Long.valueOf(getDonnCroValue("NBR_EFF_IMP_DENO", donneCro));
			totMntCro += Long.valueOf(getDonnCroValue("MNT_EFF_EFF", donneCro));
			totCommCro += Long.valueOf(getDonnCroValue("197", donneCro));
			totTvaCro += Long.valueOf(getDonnCroValue("MNT_TVA_EFF", donneCro));

		}

		// Long nbrExtra = nbrEffetRecu(41L, 22L, getStrcBct(strc), dateOper, "'P','R'");
		totExtra = sommeEffetRecu(codeValeur, codeEnrg, getStrcBct(strc), dateOper, "'P'");

		if (!totExtra.equals(totMntCro)) {
			insertDiscrodance("Discordance mnt cro " + codeOper, strc, getStrcBct(strc), codeOper, totExtra, totMntCro,
					dateOper, null);
		}

		// 2- Verification Extra et son cro

		// Long nbrExtra = nbrEffetRecu(41L, 22L, getStrcBct(strc), dateOper, "'P','R'");

		// Long mntCro = 0L;
		//
		// List<String> data = getDonnCro(codeOper, 1061L, strc, dateOper);
		//
		// for (int j = 0; j < data.size(); j++) {
		//
		// // nbrCro += Long.valueOf(getDonnCroValue("NBR_LOT_RECU_EFF", data.get(j)));
		// mntCro += Long.valueOf(getDonnCroValue("MNT_GLB_EFF", data.get(j)));
		// }
		// if (!totExtra.equals(mntCro)) {
		// insertDiscrodance("Discordance extra " + codeOper, strc, getStrcBct(strc), codeOper, totExtra, mntCro,
		// dateOper, null);
		// }

		// verification extra et OMP

		Long mntOmp = sommeOmpDin("" + codeOper, strc, dateOper);
		if (!totMntCro.equals(mntOmp)) {
			insertDiscrodance("Discordance CRO OMP DIN" + codeOper, strc, getStrcBct(strc), codeOper, totMntCro,
					mntOmp, dateOper, null);
		}
		Long mntTva = sommeOmpTva("" + codeOper, strc, dateOper);
		if (!totTvaCro.equals(mntTva)) {
			insertDiscrodance("Discordance cro OMP TVA" + codeOper, strc, getStrcBct(strc), codeOper, totTvaCro,
					mntTva, dateOper, null);
		}
		Long mntComm = sommeOmpComm("" + codeOper, strc, dateOper);

		if (!totCommCro.equals(mntComm)) {
			insertDiscrodance("Discordance cro OMP Commission" + codeOper, strc, getStrcBct(strc), codeOper,
					totCommCro, mntComm, dateOper, null);
		}

	}

	public void verifCro827(String dateOper, Long strc) {
		jt = new JdbcTemplate(dataSource);
		Long codeOper = 827L;
		Long codeValeur = 41L;
		Long codeEnrg = 21L;
		String req = "";
		Long totMntCro = 0L;
		Long totCommCro = 0L;
		Long totTvaCro = 0L;

		Long totExtra = 0L;
		// 1- Verification Cro et son extra
		SqlRowSet srs = null;
		req =
				"select DONNE_OPER_CRO ,COD_STRC_STRC,COD_STRC_IMPT,NUM_REF_CRO,NUM_ID_CRO,COD_REF_INTER,COD_REFC_OMP from cro where cod_oper_oper="
						+ codeOper
						+ " and cod_prd_prd=1061 and COD_STRC_IMPT="
						+ strc
						+ " and DAT_OPER_CRO='"
						+ dateOper + "'";
		srs = jt.queryForRowSet(req);

		while (srs.next()) {

			String donneCro = srs.getString("DONNE_OPER_CRO");

			// Long nbrCro = Long.valueOf(getDonnCroValue("NBR_EFF_IMP_DENO", donneCro));
			totMntCro += Long.valueOf(getDonnCroValue("MNT_EFF_EFF", donneCro));
			totCommCro += Long.valueOf(getDonnCroValue("4", donneCro));
			totTvaCro += Long.valueOf(getDonnCroValue("MNT_TVA_EFF", donneCro));

		}

		// Long nbrExtra = nbrEffetRecu(41L, 22L, getStrcBct(strc), dateOper, "'P','R'");
		totExtra = sommeEffetRecu(codeValeur, codeEnrg, getStrcBct(strc), dateOper, "'R'");

		if (!totExtra.equals(totMntCro)) {
			insertDiscrodance("Discordance mnt cro " + codeOper, strc, getStrcBct(strc), codeOper, totExtra, totMntCro,
					dateOper, null);
		}

		// verification extra et OMP

		Long mntTva = sommeOmpTva("" + codeOper, strc, dateOper);
		if (!totTvaCro.equals(mntTva)) {
			insertDiscrodance("Discordance cro OMP TVA" + codeOper, strc, getStrcBct(strc), codeOper, totTvaCro,
					mntTva, dateOper, null);
		}
		Long mntComm = sommeOmpComm("" + codeOper, strc, dateOper);

		if (!totCommCro.equals(mntComm)) {
			insertDiscrodance("Discordance cro OMP Commission" + codeOper, strc, getStrcBct(strc), codeOper,
					totCommCro, mntComm, dateOper, null);
		}

	}

	public void verifCro834(String dateOper, Long strc) {
		jt = new JdbcTemplate(dataSource);
		Long codeOper = 834L;
		Long codeValeur = 40L;
		Long codeEnrg = 21L;
		String req = "";
		Long totMntCro = 0L;
		Long totCommCro = 0L;
		Long totTvaCro = 0L;

		Long totExtra = 0L;
		// 1- Verification Cro et son extra
		SqlRowSet srs = null;
		req =
				"select DONNE_OPER_CRO ,COD_STRC_STRC,COD_STRC_IMPT,NUM_REF_CRO,NUM_ID_CRO,COD_REF_INTER,COD_REFC_OMP from cro where cod_oper_oper="
						+ codeOper
						+ " and cod_prd_prd=1061 and COD_STRC_IMPT="
						+ strc
						+ " and DAT_OPER_CRO='"
						+ dateOper + "'";
		srs = jt.queryForRowSet(req);

		while (srs.next()) {

			String donneCro = srs.getString("DONNE_OPER_CRO");

			// Long nbrCro = Long.valueOf(getDonnCroValue("NBR_EFF_IMP_DENO", donneCro));
			totMntCro += Long.valueOf(getDonnCroValue("MNT_EFF_EFF", donneCro));
			totCommCro += Long.valueOf(getDonnCroValue("4", donneCro));
			totTvaCro += Long.valueOf(getDonnCroValue("MNT_TVA_EFF", donneCro));

		}

		// Long nbrExtra = nbrEffetRecu(41L, 22L, getStrcBct(strc), dateOper, "'P','R'");
		totExtra = sommeEffetRecu(codeValeur, codeEnrg, getStrcBct(strc), dateOper, "'R'");

		if (!totExtra.equals(totMntCro)) {
			insertDiscrodance("Discordance mnt cro " + codeOper, strc, getStrcBct(strc), codeOper, totExtra, totMntCro,
					dateOper, null);
		}

		// verification extra et OMP

		Long mntTva = sommeOmpTva("" + codeOper, strc, dateOper);
		if (!totTvaCro.equals(mntTva)) {
			insertDiscrodance("Discordance cro OMP TVA" + codeOper, strc, getStrcBct(strc), codeOper, totTvaCro,
					mntTva, dateOper, null);
		}
		Long mntComm = sommeOmpComm("" + codeOper, strc, dateOper);

		if (!totCommCro.equals(mntComm)) {
			insertDiscrodance("Discordance cro OMP Commission" + codeOper, strc, getStrcBct(strc), codeOper,
					totCommCro, mntComm, dateOper, null);
		}

	}

	public void verifCro828(String dateOper, Long strc) {
		jt = new JdbcTemplate(dataSource);
		Long codeOper = 828L;
		Long codeValeur = 41L;
		Long codeEnrg = 21L;
		String req = "";
		Long totMntCro = 0L;
		Long totCommCro = 0L;
		Long totTvaCro = 0L;

		Long totExtra = 0L;
		// 1- Verification Cro et son extra
		SqlRowSet srs = null;
		req =
				"select DONNE_OPER_CRO ,COD_STRC_STRC,COD_STRC_IMPT,NUM_REF_CRO,NUM_ID_CRO,COD_REF_INTER,COD_REFC_OMP from cro where cod_oper_oper="
						+ codeOper
						+ " and cod_prd_prd=1061 and COD_STRC_IMPT="
						+ strc
						+ " and DAT_OPER_CRO='"
						+ dateOper + "'";
		srs = jt.queryForRowSet(req);

		while (srs.next()) {

			String donneCro = srs.getString("DONNE_OPER_CRO");

			// Long nbrCro = Long.valueOf(getDonnCroValue("NBR_EFF_IMP_DENO", donneCro));
			totMntCro += Long.valueOf(getDonnCroValue("MNT_GLB_REJET_EFF", donneCro));

		}

		// Long nbrExtra = nbrEffetRecu(41L, 22L, getStrcBct(strc), dateOper, "'P','R'");
		totExtra = sommeEffetRecu(codeValeur, codeEnrg, getStrcBct(strc), dateOper, "'R'");

		if (!totExtra.equals(totMntCro)) {
			insertDiscrodance("Discordance mnt cro " + codeOper, strc, getStrcBct(strc), codeOper, totExtra, totMntCro,
					dateOper, null);
		}

	}

	public void verifCro835(String dateOper, Long strc) {
		jt = new JdbcTemplate(dataSource);
		Long codeOper = 835L;
		Long codeValeur = 40L;
		Long codeEnrg = 21L;
		String req = "";
		Long totMntCro = 0L;
		Long totCommCro = 0L;
		Long totTvaCro = 0L;

		Long totExtra = 0L;
		// 1- Verification Cro et son extra
		SqlRowSet srs = null;
		req =
				"select DONNE_OPER_CRO ,COD_STRC_STRC,COD_STRC_IMPT,NUM_REF_CRO,NUM_ID_CRO,COD_REF_INTER,COD_REFC_OMP from cro where cod_oper_oper="
						+ codeOper
						+ " and cod_prd_prd=1061 and COD_STRC_IMPT="
						+ strc
						+ " and DAT_OPER_CRO='"
						+ dateOper + "'";
		srs = jt.queryForRowSet(req);

		while (srs.next()) {

			String donneCro = srs.getString("DONNE_OPER_CRO");

			// Long nbrCro = Long.valueOf(getDonnCroValue("NBR_EFF_IMP_DENO", donneCro));
			totMntCro += Long.valueOf(getDonnCroValue("MNT_GLB_REJET_EFF", donneCro));

		}

		// Long nbrExtra = nbrEffetRecu(41L, 22L, getStrcBct(strc), dateOper, "'P','R'");
		totExtra = sommeEffetRecu(codeValeur, codeEnrg, getStrcBct(strc), dateOper, "'R'");

		if (!totExtra.equals(totMntCro)) {
			insertDiscrodance("Discordance mnt cro " + codeOper, strc, getStrcBct(strc), codeOper, totExtra, totMntCro,
					dateOper, null);
		}

	}

	public boolean verifEtatCheque(Long numCheque, String cpt, String date) {
		jt = new JdbcTemplate(dataSource);

		return false;
	}

	public void verifPaymentCheque(String dateOper, Long strc) {
		jt = new JdbcTemplate(dataSource);

		String req = "";

		// 1- Verification Cro et son extra
		SqlRowSet srs = null;
		req =
				"select DONNE_OPER_CRO ,COD_STRC_STRC,COD_STRC_IMPT,NUM_REF_CRO,NUM_ID_CRO,COD_REF_INTER,COD_REFC_OMP from cro where cod_oper_oper in(806) "

						+ " and cod_prd_prd=1055 and COD_STRC_IMPT=" + strc + " and DAT_OPER_CRO='" + dateOper + "'";
		srs = jt.queryForRowSet(req);

		while (srs.next()) {

			String donneCro = srs.getString("DONNE_OPER_CRO");
			Long numCheque = Long.valueOf(getDonnCroValue("numcheque", donneCro));

			Long mnt = Long.valueOf(getDonnCroValue("MNT_CHQ_CLT", donneCro));
			String Numcptcli = getDonnCroValue("Numcptcli", donneCro);
			boolean verif = verifEtatCheque(numCheque, Numcptcli, dateOper);
			if (!verif) {
				insertDiscrodance("Etat Cheque non mise à jours 806 ", strc, getStrcBct(strc), 806L, mnt, mnt,
						dateOper, null);
			}

		}

	}

	public void verifCro806(String dateOper, Long strc) {
		jt = new JdbcTemplate(dataSource);

		String req = "";
		Long totMntCro806 = 0L;
		Long totMntCro846 = 0L;

		Long totExtra = 0L;
		// 1- Verification Cro et son extra
		SqlRowSet srs = null;
		req =
				"select DONNE_OPER_CRO ,COD_STRC_STRC,COD_STRC_IMPT,NUM_REF_CRO,NUM_ID_CRO,COD_REF_INTER,COD_REFC_OMP from cro where cod_oper_oper in(806) "

						+ " and cod_prd_prd=1055 and COD_STRC_IMPT=" + strc + " and DAT_OPER_CRO='" + dateOper + "'";
		srs = jt.queryForRowSet(req);

		while (srs.next()) {

			String donneCro = srs.getString("DONNE_OPER_CRO");

			totMntCro806 += Long.valueOf(getDonnCroValue("MNT_CHQ_CLT", donneCro));

		}

		// Long nbrExtra = nbrEffetRecu(41L, 22L, getStrcBct(strc), dateOper, "'P','R'");
		// tot 806
		totExtra = sommeChequePaye806(getStrcBct(strc), dateOper);
		Long codeOper = 806L;

		if (!totExtra.equals(totMntCro806)) {
			insertDiscrodance("Discordance mnt cro " + codeOper, strc, getStrcBct(strc), codeOper, totExtra,
					totMntCro806, dateOper, null);
		}

		totExtra = 0L;
		req =
				"select DONNE_OPER_CRO ,COD_STRC_STRC,COD_STRC_IMPT,NUM_REF_CRO,NUM_ID_CRO,COD_REF_INTER,COD_REFC_OMP from cro where cod_oper_oper in(846) "

						+ " and cod_prd_prd=1055 and COD_STRC_IMPT=" + strc + " and DAT_OPER_CRO='" + dateOper + "'";
		srs = jt.queryForRowSet(req);

		while (srs.next()) {

			String donneCro = srs.getString("DONNE_OPER_CRO");

			totMntCro846 += Long.valueOf(getDonnCroValue("MNT_CHQ_CLT", donneCro));

		}

		// Long nbrExtra = nbrEffetRecu(41L, 22L, getStrcBct(strc), dateOper, "'P','R'");
		// tot 846
		totExtra = sommeChequePaye846(getStrcBct(strc), dateOper);
		codeOper = 846L;

		if (!totExtra.equals(totMntCro846)) {
			insertDiscrodance("Discordance mnt cro " + codeOper, strc, getStrcBct(strc), codeOper, totExtra,
					totMntCro846, dateOper, null);
		}

		// 2- Verification Extra et son cro
		srs = null;
		Long totMntCro = 0L;

		req =
				"select DONNE_OPER_CRO ,COD_STRC_STRC,COD_STRC_IMPT,NUM_REF_CRO,NUM_ID_CRO,COD_REF_INTER,COD_REFC_OMP from cro where cod_oper_oper in(846) "

						+ " and cod_prd_prd=1055 and COD_STRC_IMPT=" + strc + " and DAT_OPER_CRO='" + dateOper + "'";
		srs = jt.queryForRowSet(req);

		while (srs.next()) {

			String donneCro = srs.getString("DONNE_OPER_CRO");

			if (getDonnCroValue("ETAT_CPT", donneCro).equals("0")) {
				totMntCro += Long.valueOf(getDonnCroValue("MNT_CHQ_CLT", donneCro));
			}
		}

		Long mntOmp = sommeOmpDin("846", strc, dateOper);
		if (!totMntCro.equals(mntOmp)) {
			insertDiscrodance("Discordance CRO OMP DIN 846", strc, getStrcBct(strc), 846L, totMntCro846, mntOmp,
					dateOper, null);
		}
		srs = null;
		totMntCro = 0L;
		Long totMntInt = 0L;

		req =
				"select DONNE_OPER_CRO ,COD_STRC_STRC,COD_STRC_IMPT,NUM_REF_CRO,NUM_ID_CRO,COD_REF_INTER,COD_REFC_OMP from cro where cod_oper_oper in (806)"

						+ " and cod_prd_prd=1055 and COD_STRC_IMPT=" + strc + " and DAT_OPER_CRO='" + dateOper + "'";
		srs = jt.queryForRowSet(req);

		while (srs.next()) {

			String donneCro = srs.getString("DONNE_OPER_CRO");

			if (getDonnCroValue("ETAT_CPT", donneCro).equals("0")
					&& getDonnCroValue("TYPE_PAIEMENT", donneCro).equals("1")) {
				totMntInt += Long.valueOf(getDonnCroValue("MNT_INT_RET_CHQ", donneCro));

				totMntCro += Long.valueOf(getDonnCroValue("MNT_CHQ_CLT", donneCro));
			}

		}
		totMntCro += totMntInt;
		mntOmp = sommeOmpDin("806", strc, dateOper);
		if (!totMntCro.equals(mntOmp)) {
			insertDiscrodance("Discordance CRO OMP DIN 806", strc, getStrcBct(strc), 806L, totMntCro, mntOmp, dateOper,
					null);
		}

	}

	private Long sommeRejet813(String strcBct, String dateOper) {
		jt = new JdbcTemplate(dataSource);
		String ribtir = "03" + strcBct;
		String req =
				"select  nvl(sum(MNT_CHQ_TCH),0) from TRACE_CHEQUE where cod_val_val=84 and COD_S_TCH!='MAN' and RIB_TIR_CHQ like '"
						+ ribtir + "%' and DAT_OPE_CHQ='" + dateOper + "'";

		Long valuePap = jt.queryForLong(req);

		req =
				"select  nvl(sum(chq.MNT_CHQ_CHQ),0) from cnp cnp ,cheque chq where cnp.DAT_CNP_CNP='" + dateOper
						+ "' and cnp.RIB_TIR_CHQ like '" + ribtir + "%' and chq.COD_S_CHQ!='MAN'"
						+ " and cnp.NUM_CHQ_CHQ = chq.NUM_CHQ_CHQ and cnp.RIB_TIR_CHQ=chq.RIB_TIR_CHQ ";
		Long valueCnp = jt.queryForLong(req);
		return valueCnp + valuePap;
	}

	public void verifCro813(String dateOper, Long strc) {
		jt = new JdbcTemplate(dataSource);
		Long codeOper = 813L;
		Long codeValeur = 41L;
		Long codeEnrg = 21L;
		String req = "";
		Long totMntCro = 0L;
		Long totMntCroAnnul = 0L;

		Long totCommCro = 0L;
		Long totTvaCro = 0L;

		Long totExtra = 0L;
		// 1- Verification Cro et son extra
		SqlRowSet srs = null;
		req =
				"select DONNE_OPER_CRO ,COD_STRC_STRC,COD_STRC_IMPT,NUM_REF_CRO,NUM_ID_CRO,COD_REF_INTER,COD_REFC_OMP from cro where cod_oper_oper="
						+ codeOper
						+ " and cod_prd_prd=1055 and cod_typ_oper='O' and COD_STRC_IMPT="
						+ strc
						+ " and DAT_OPER_CRO='"
						+ dateOper + "'";
		srs = jt.queryForRowSet(req);

		while (srs.next()) {

			String donneCro = srs.getString("DONNE_OPER_CRO");

			// Long nbrCro = Long.valueOf(getDonnCroValue("NBR_EFF_IMP_DENO", donneCro));
			totMntCro += Long.valueOf(getDonnCroValue("MNT_GLO_RJG_CH_AG", donneCro));

		}
		// Comptabiliser les annulation

		req =
				"select DONNE_OPER_CRO ,COD_STRC_STRC,COD_STRC_IMPT,NUM_REF_CRO,NUM_ID_CRO,COD_REF_INTER,COD_REFC_OMP from cro where cod_oper_oper="
						+ codeOper
						+ " and cod_prd_prd=1055 and cod_typ_oper='A' and COD_STRC_IMPT="
						+ strc
						+ " and DAT_OPER_CRO='" + dateOper + "'";
		srs = jt.queryForRowSet(req);

		while (srs.next()) {

			String donneCro = srs.getString("DONNE_OPER_CRO");

			// Long nbrCro = Long.valueOf(getDonnCroValue("NBR_EFF_IMP_DENO", donneCro));
			totMntCroAnnul += Long.valueOf(getDonnCroValue("MNT_GLO_RJG_CH_AG", donneCro));

		}

		// Long nbrExtra = nbrEffetRecu(41L, 22L, getStrcBct(strc), dateOper, "'P','R'");
		totExtra = sommeRejet813(getStrcBct(strc), dateOper);
		totMntCro=totMntCro-totMntCroAnnul;
		if (!totExtra.equals(totMntCro)) {
			insertDiscrodance("Discordance mnt cro " + codeOper, strc, getStrcBct(strc), codeOper, totMntCro, totExtra,
					dateOper, null);
		}

	}
	
	public void verifCro96(String dateOper, Long strc) {
		jt = new JdbcTemplate(dataSource);

		String req = "";
		Long totMntCro96 = 0L;
		Long totMntOmp96 = 0L;
		Long totExtra = 0L;
		// 1- Verification Cro et son extra
		SqlRowSet srs = null;
		req =
				"select DONNE_OPER_CRO ,COD_STRC_STRC,COD_STRC_IMPT,NUM_REF_CRO,NUM_ID_CRO,COD_REF_INTER,COD_REFC_OMP from cro where cod_oper_oper in(96) "

						+ "  and COD_STRC_IMPT=" + strc + " and DAT_OPER_CRO='" + dateOper + "'";
		srs = jt.queryForRowSet(req);

		while (srs.next()) {

			String donneCro = srs.getString("DONNE_OPER_CRO");

			totMntCro96 += Long.valueOf(getDonnCroValue("MONT_DIN_OMP_RET", donneCro));

		}

		// verification extra et OMP

//		Long mntOmp = sommeOmpDin("" + codeOper, strc, dateOper);
//		if (!totMntCro.equals(mntOmp)) {
//			insertDiscrodance("Discordance CRO OMP DIN" + codeOper, strc, getStrcBct(strc), codeOper, totMntCro,
//					mntOmp, dateOper, null);
//		}
//		Long mntTva = sommeOmpTva("" + codeOper, strc, dateOper);
//		if (!totTvaCro.equals(mntTva)) {
//			insertDiscrodance("Discordance cro OMP TVA" + codeOper, strc, getStrcBct(strc), codeOper, totTvaCro,
//					mntTva, dateOper, null);
//		}
//		Long mntComm = sommeOmpComm("" + codeOper, strc, dateOper);
//
//		if (!totCommCro.equals(mntComm)) {
//			insertDiscrodance("Discordance cro OMP Commission" + codeOper, strc, getStrcBct(strc), codeOper,
//					totCommCro, mntComm, dateOper, null);
//		}
//		
//		
//		
//
//		if (!totExtra.equals(totMntCro806)) {
//			insertDiscrodance("Discordance mnt cro " + codeOper, strc, getStrcBct(strc), codeOper, totExtra,
//					totMntCro806, dateOper, null);
//		}
//
//		totExtra = 0L;
//		req =
//				"select DONNE_OPER_CRO ,COD_STRC_STRC,COD_STRC_IMPT,NUM_REF_CRO,NUM_ID_CRO,COD_REF_INTER,COD_REFC_OMP from cro where cod_oper_oper in(846) "
//
//						+ " and cod_prd_prd=1055 and COD_STRC_IMPT=" + strc + " and DAT_OPER_CRO='" + dateOper + "'";
//		srs = jt.queryForRowSet(req);
//
//		while (srs.next()) {
//
//			String donneCro = srs.getString("DONNE_OPER_CRO");
//
//			totMntCro846 += Long.valueOf(getDonnCroValue("MNT_CHQ_CLT", donneCro));
//
//		}

		

	}
	

}
