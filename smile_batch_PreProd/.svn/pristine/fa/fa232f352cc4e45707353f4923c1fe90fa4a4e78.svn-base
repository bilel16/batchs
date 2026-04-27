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
import com.bna.smile.batch.test.DiscordanceFrame;

public class DiscordanceSoldeDAO implements Serializable {

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
	public DiscordanceSoldeDAO() {
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

	public List<Long> getAgPilolte() {
		jt = new JdbcTemplate(dataSource);

		List<Long> liste = new ArrayList<Long>();

		Long seq = 1L;
		SqlRowSet rs = jt.queryForRowSet("select cod_strc_strc from agence_pilote   ");
		while (rs.next())
			liste.add(rs.getLong("cod_strc_strc"));

		return liste;
	}

	/***********************/
	private synchronized void deleteTraceSoldeProcss(Long codStrc) {
		jt = new JdbcTemplate(dataSource);
		String query =
				"DELETE FROM SOLDE_JOUR "
						+ " where to_char(DAT_EXEC_CCPT,'DD/MM/YYYY')=to_char(sysdate,'DD/MM/YYYY') and cod_strc_strc=?";
		jt.update(query, new Object[]{ codStrc });
	}

	private synchronized void deleteDiscordanceProcss(Long codStrc) {
		jt = new JdbcTemplate(dataSource);
		String query =
				"delete from DISCORDANCE_SOLDE_CPT where trunc(DATE_CONF_SLD)=trunc(sysdate) and cod_strc_strc=?";
		jt.update(query, new Object[]{ codStrc });
	}

	public synchronized void initSoldeProcss(Long codStrc) {

		deleteTraceSoldeProcss(codStrc);
		jt = new JdbcTemplate(dataSource);
		String query =
				"INSERT INTO SOLDE_JOUR(DAT_EXEC_CCPT," + " TIME_EXEC_CCPT," + " COD_STRC_STRC," + " COD_PRD_PRD,"
						+ " NUM_CCPT_CCPT," + " NUM_SEQ_PERS," + " COD_ETAT_CCPT," + " MONT_SOLD_CCPT,"
						+ " MONT_BLOC_CCPT," + " MONT_AUT_CCPT," + " MONT_SDEV_CCPT,"
						+ " DAT_EAUT_CCPT,MONT_BDEV_CCPT,MONT_DAFF_CCPT)" + " SELECT sysdate,"
						+ " to_char(sysdate,'HH24:MM:SS')," + " COD_STRC_STRC," + " COD_PRD_PRD," + " NUM_CCPT_CCPT,"
						+ " NUM_SEQ_PERS," + " COD_ETAT_CCPT," + " MONT_SOLD_CCPT," + " MONT_BLOC_CCPT,"
						+ " MONT_AUT_CCPT," + " MONT_SDEV_CCPT,"
						+ " DAT_EAUT_CCPT,MONT_BDEV_CCPT,MONT_DAFF_CCPT FROM CONTRAT_CPT"
						+ " where COD_ETAT_CCPT not in ('R','A','N','T')"
						+ " AND COD_STRC_STRC=?  and cod_prd_prd!=443";
		jt.update(query, new Object[]{ codStrc });

		// /Solde GOD
		initSoldeGodProcss(codStrc);

	}

	public synchronized void initSoldeGodProcss(Long codStrc) {

		jt = new JdbcTemplate(dataSource);
		String query =
				"INSERT INTO SOLDE_JOUR(DAT_EXEC_CCPT," + " TIME_EXEC_CCPT," + " COD_STRC_STRC," + " COD_PRD_PRD,"
						+ " NUM_CCPT_CCPT," + " NUM_SEQ_PERS," + " COD_ETAT_CCPT," + " MONT_SOLD_CCPT,"
						+ " MONT_BLOC_CCPT," + " MONT_AUT_CCPT," + " MONT_SDEV_CCPT,"
						+ " DAT_EAUT_CCPT,MONT_BDEV_CCPT,MONT_DAFF_CCPT)" + " SELECT sysdate,"
						+ " to_char(sysdate,'HH24:MM:SS')," + " COD_STRC_GOD," + " COD_PRD_GOD," + " NUM_CCPT_GOD,"
						+ " nvl(NUM_SEQ_PERS,0)," + " 'V'," + " nvl(MONT_SOLD_DIN_GOD,0)," + " 0," + " 0," + " nvl(MONT_SOLD_DEV_GOD,0),"
						+ " null,0,0 FROM CONTRAT_GOD" + " where  COD_STRC_GOD=? ";
		jt.update(query, new Object[]{ codStrc });

	}

	private synchronized Date getLastDateContrat(Long strc, Long prd, Long numccpt) {
		jt = new JdbcTemplate(dataSource);
		String query =
				"select nvl(MAX(DAT_EXEC_CCPT),null) " + "from SOLDE_JOUR " + "where cod_strc_strc=? "
						+ "and cod_prd_prd=? " + "and num_ccpt_ccpt=?";

		Date date = (Date) jt.queryForObject(query, new Object[]{ strc, prd, numccpt }, Date.class);
		return date;

	}

	private synchronized Long getLastSoldeContrat(Long strc, Long prd, Long numccpt, Date lastDate) {
		jt = new JdbcTemplate(dataSource);
		String query =
				"select nvl( MONT_SOLD_CCPT,0)  from SOLDE_JOUR " + "where cod_strc_strc=?" + "and cod_prd_prd=? "
						+ "and num_ccpt_ccpt=? and DAT_EXEC_CCPT=? ";

		Long solde = (Long) jt.queryForObject(query, new Object[]{ strc, prd, numccpt, lastDate }, Long.class);
		return solde;

	}

	private synchronized Long getSoldeContrat(Long strc, Long prd, Long numccpt) {
		jt = new JdbcTemplate(dataSource);
		String query =
				"select  nvl( MONT_SOLD_CCPT,0) from CONTRAT_CPT " + "where cod_strc_strc=?" + "and cod_prd_prd=? "
						+ "and num_ccpt_ccpt=?  ";

		Long solde = (Long) jt.queryForObject(query, new Object[]{ strc, prd, numccpt }, Long.class);
		return solde;

	}

	private synchronized Long getSequenceDiscordance(Long structure) {
		jt = new JdbcTemplate(dataSource);
		String query = "select SEQ_DISC_SLD.NEXTVAL  from dual ";

		Long seq = (Long) jt.queryForObject(query, Long.class);
		return Long.valueOf(structure + "" + seq);

	}

	private synchronized Long getSomeOperation(Long strc, Long prd, Long numccpt, String sens, Date lastDate) {
		jt = new JdbcTemplate(dataSource);
		String query =
				"select nvl( sum(MONT_DIN_OMP),0) " + " from operation_moy_pay  " + " where cod_strc_strc=?" +

				" and cod_prd_prd=? " + " and num_ccpt_ccpt=?" + " and COD_SENS_OMP=?" + " and cod_etat_omp='V'"
						+ " and dat_time_omp >?";

		Long solde = (Long) jt.queryForObject(query, new Object[]{ strc, prd, numccpt, sens, lastDate }, Long.class);
		return solde;

	}

	private synchronized Long getSomeTva(Long strc, Long prd, Long numccpt, Date lastDate) {
		jt = new JdbcTemplate(dataSource);
		String query = "select nvl( sum(MONT_TVA_OMP),0)" + " from operation_moy_pay  " + " where cod_strc_strc=?" +

		" and cod_prd_prd=? " + " and num_ccpt_ccpt=?" + " and cod_etat_omp='V'" + " and dat_time_omp >?";

		Long solde = (Long) jt.queryForObject(query, new Object[]{ strc, prd, numccpt, lastDate }, Long.class);
		return solde;

	}

	private synchronized Long getSomeComm(Long strc, Long prd, Long numccpt, Date lastDate) {
		jt = new JdbcTemplate(dataSource);
		String query =
				"select  nvl(sum(MONT_VAL_DOMP),0)  " + " from DETAIL_OPER_MOY_PAIEMENT  "
						+ " where NUM_OPER_OMP in ( select NUM_OPER_OMP from operation_moy_pay where cod_strc_strc=?"
						+ " and cod_prd_prd=? " + " and num_ccpt_ccpt=?" + " and dat_time_omp>?"
						+ " and cod_etat_omp='V'" + " )";

		Long solde = (Long) jt.queryForObject(query, new Object[]{ strc, prd, numccpt, lastDate }, Long.class);
		return solde;

	}

	private synchronized void ajouterDiscordance(Long v_num_seq, Long cod_strc_strc, Long cod_prd_prd,
			Long num_ccpt_ccpt, Long v_SOLDE_CPT, Long v_SOLDE_DEPART, Long v_SOMME_CR, Long v_SOMME_DB,
			Long v_SOMME_TVA, Long v_SOMME_COMM) {
		jt = new JdbcTemplate(dataSource);

		String query =
				"INSERT INTO DISCORDANCE_SOLDE_CPT" + " (NUM_SEQ_DISC," + " COD_STRC_STRC," + " COD_PRD_PRD,"
						+ " NUM_CCPT_CCPT," + " SOLDE_CPT," + " SOLDE_DEPART," + " DATE_CONF_SLD," + " SOMME_CREDIT,"
						+ " SOMME_DEBIT," + " SOMME_TVA," + " SOMME_COMM" + " ,DATE_SYS_TIME)" + " VALUES(?" + ",?"
						+ " ,?" + " ,?" + " ,?" + " ,?" + " ,sysdate" + " ,?" + " ,?" + " ,?" + " ,?" + " ,sysdate)";
		jt.update(query, new Object[]{ v_num_seq, cod_strc_strc, cod_prd_prd, num_ccpt_ccpt, v_SOLDE_CPT,
				v_SOLDE_DEPART, v_SOMME_CR, v_SOMME_DB, v_SOMME_TVA, v_SOMME_COMM });
	}

	public synchronized void verifierSoldeProcess(Long codeStrc, DiscordanceFrame frame) {

		deleteDiscordanceProcss(codeStrc);
		jt = new JdbcTemplate(dataSource);
		SqlRowSet srs = null;
		String req = "select distinct cod_strc_strc,cod_prd_prd,num_ccpt_ccpt from SOLDE_JOUR where cod_strc_strc=?";
		srs = jt.queryForRowSet(req, new Object[]{ codeStrc });

		while (srs.next()) {
			Long v_SOMME_CR = 0L;
			Long v_SOMME_DB = 0L;
			Long v_SOLDE_CPT = 0L;
			Long v_SOLDE_DEPART = 0L;
			Long v_SOMME_TVA = 0L;
			Long v_SOMME_COMM = 0L;

			Long v_num_seq = 0L;
			Date v_LAST_DATE = null;
			Long prd = srs.getLong("cod_prd_prd");
			Long numCpt = srs.getLong("num_ccpt_ccpt");

			// System.out.println("Compte :"+codeStrc+"/"+prd+"/"+numCpt);
			frame.updateInfo(" Compte :" + codeStrc + "/" + prd + "/" + numCpt);
			v_LAST_DATE = getLastDateContrat(codeStrc, prd, numCpt);

			if (v_LAST_DATE != null) {
				v_SOLDE_DEPART = getLastSoldeContrat(codeStrc, prd, numCpt, v_LAST_DATE);
				v_SOMME_CR = getSomeOperation(codeStrc, prd, numCpt, "C", v_LAST_DATE);
				v_SOMME_DB = getSomeOperation(codeStrc, prd, numCpt, "D", v_LAST_DATE);
				v_SOMME_TVA = getSomeTva(codeStrc, prd, numCpt, v_LAST_DATE);
				v_SOMME_COMM = getSomeComm(codeStrc, prd, numCpt, v_LAST_DATE);
				v_SOLDE_CPT = getSoldeContrat(codeStrc, prd, numCpt);

				if (v_SOLDE_DEPART + v_SOMME_CR - v_SOMME_DB - v_SOMME_COMM - v_SOMME_TVA != v_SOLDE_CPT)

				{
					v_num_seq = getSequenceDiscordance(codeStrc);
					ajouterDiscordance(v_num_seq, codeStrc, prd, numCpt, v_SOLDE_CPT, v_SOLDE_DEPART, v_SOMME_CR,
							v_SOMME_DB, v_SOMME_TVA, v_SOMME_COMM);
					// System.out.println("Discordance Found!");
				}

			}

		}

	}

	/************************/
	public void initSolde() {
		jt = new JdbcTemplate(dataSource);
		jt.execute(new CallableStatementCreator() {

			public CallableStatement createCallableStatement(Connection con) throws SQLException {
				CallableStatement cs = con.prepareCall("{call CHARG_SOLDE_JOUR()}");

				return cs;
			}
		}, new CallableStatementCallback() {

			public Object doInCallableStatement(CallableStatement cs) throws SQLException {
				cs.execute();
				return null; // Whatever is returned here is returned from the jdbcTemplate.execute method
			}
		});
	}

	public void verifDiscSolde() {
		jt = new JdbcTemplate(dataSource);
		jt.execute(new CallableStatementCreator() {

			public CallableStatement createCallableStatement(Connection con) throws SQLException {
				CallableStatement cs = con.prepareCall("{call DISCORDANCE_SOLDE()}");

				return cs;
			}
		}, new CallableStatementCallback() {

			public Object doInCallableStatement(CallableStatement cs) throws SQLException {
				cs.execute();
				return null; // Whatever is returned here is returned from the jdbcTemplate.execute method
			}
		});
	}

}
