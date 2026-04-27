package com.bna.smile.model.SMS.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.sql.DataSource;

import org.apache.log4j.Logger;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import com.bna.commun.model.ContratCpt;
import com.bna.commun.model.ContratCptId;
import com.bna.smile.model.SMS.model.PowerCardSMS;
import com.bna.smile.model.domainecommun.model.EnvoiSMSVo;

public class EnvoiSmsDAO {

	protected String sqlQuery;
	protected JdbcTemplate jt;
	protected DataSource dataSource;
	private SimpleDateFormat formaterDate = new SimpleDateFormat("dd/MM/yyyy");
	private static final Logger logger = Logger.getLogger(EnvoiSmsDAO.class);

	// ////////////////
	public List getListAgencesPilotes() {
		jt = new JdbcTemplate(dataSource);

		String requete = "select cod_strc_strc from agence_pilote order by cod_strc_strc ";

		// System.out.println(requete);
		logger.info(requete);
		List listAgencesPilotes = jt.queryForList(requete);
		logger.info("la requete a ramené " + String.valueOf(listAgencesPilotes.size()));

		return listAgencesPilotes;

	}

	public String getCodeStructureBCT(Long codeStructureBNA) {

		jt = new JdbcTemplate(dataSource);
		String codeStructureBCT = (String) jt.queryForObject(
				"SELECT  LPAD(COD_BCT_STRC,3,'0') FROM STRUCTURE WHERE COD_STRC_STRC=" + codeStructureBNA + "",
				String.class);
		return codeStructureBCT;
	}

	public String getLastChifreSolde(Long codeChiffre, boolean sensSolde) {

		jt = new JdbcTemplate(dataSource);
		String caractere = "";
		if (sensSolde == true) {
			caractere = (String) jt.queryForObject(
					"select credit from CORRESPONDANCE_AFB where chiffre =" + codeChiffre + "", String.class);
		} else {
			caractere = (String) jt.queryForObject(
					"select debit from CORRESPONDANCE_AFB where chiffre =" + codeChiffre + "", String.class);
		}

		return caractere;
	}

	public List<EnvoiSMSVo> getListeOperationsMoyenPayByCriteres(Long codStrcCcpt, Long codPrdCcpt, Long numCcptCcpt,
			final Date dateJour) {
		jt = new JdbcTemplate(dataSource);
		SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(dateJour);
		calendar.add(Calendar.DATE, -30);
		Date dateDebut = calendar.getTime();

		String requete =

				"select  count(num_oper_omp)as nbre,sum(oper.mont_din_omp) as montant,oper.COD_OPER_OPER,op.LIB_COUR_OPER,oper.cod_sens_omp "
						+ "	from operation_moy_pay oper , operation op " + "	where  oper.cod_etat_omp='V'  "
						+ "	and oper.COD_OPER_OPER not in (select COD_OPER_OPER from operation_ignore_sms )"
						+ "	and trunc(DAT_time_OMP) = '" + format.format(dateJour) + "'	and oper.cod_prd_prd="
						+ codPrdCcpt + " and dat_oper_omp>='" + format.format(dateDebut) + "' and oper.COD_STRC_STRC="
						+ codStrcCcpt + "" + "	and oper.NUM_CCPT_CCPT=" + numCcptCcpt
						+ "	 and op.cod_oper_oper=oper.cod_oper_oper"
						+ "	 group by 	 oper.COD_OPER_OPER,op.LIB_COUR_OPER,oper.cod_sens_omp "
						+ "				 order by oper.COD_OPER_OPER asc";

		List<EnvoiSMSVo> list = jt.query(requete, new RowMapper() {

			public Object mapRow(ResultSet rs, int rowNum) throws SQLException {

				EnvoiSMSVo envoiSMSVo = new EnvoiSMSVo();
				envoiSMSVo.setNbreOperation(rs.getLong("NBRE"));

				envoiSMSVo.setCodeOperation(rs.getLong("COD_OPER_OPER"));

				if (rs.getLong("COD_OPER_OPER") == 2018L) {
					envoiSMSVo.setLibOperation("Virement");
				} else {
					envoiSMSVo.setLibOperation(rs.getString("LIB_COUR_OPER"));
				}

				envoiSMSVo.setMontantOperation(rs.getLong("MONTANT"));
				envoiSMSVo.setSens(rs.getString("COD_SENS_OMP"));
				envoiSMSVo.setDateComptable(dateJour);

				return envoiSMSVo;
			}
		});
		return list;
	}

	public String getNumeroTelephone(Long codStrcCcpt, Long codPrdCcpt, Long numCcptCcpt) {
		jt = new JdbcTemplate(dataSource);
		String numeroTelephone = "";
		try {
			String requete = "select pers.NUM_TEL_PERS from CONTRAT_CPT  cpt ,personne pers "
					+ " where cpt.COD_STRC_STRC=" + codStrcCcpt + " and cpt.COD_PRD_PRD=" + codPrdCcpt
					+ " and cpt.NUM_CCPT_CCPT=" + numCcptCcpt + " and cpt.NUM_SEQ_PERS=pers.NUM_SEQ_PERS ";

			numeroTelephone = (String) jt.queryForObject(requete, String.class);
		} catch (Exception e) {
			numeroTelephone = "";
		}
		return numeroTelephone;
	}

	public List<ContratCpt> getListeComptes(Date dateJour) {
		jt = new JdbcTemplate(dataSource);
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(dateJour);
		calendar.add(Calendar.DATE, -30);
		Date dateDebut = calendar.getTime();

		SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
		String requete = "select  count(*), COD_STRC_STRC,COD_PRD_PRD,NUM_CCPT_CCPT  from operation_moy_pay  "
				+ " where   cod_etat_omp='V'  and  COD_OPER_OPER not in (select COD_OPER_OPER from operation_ignore_sms )and "
				+ " trunc(DAT_time_OMP) = '" + format.format(dateJour) + "' and cod_prd_prd=121  "
				+ " and dat_oper_omp>='" + format.format(dateDebut) + "' "
				+ " group by COD_STRC_STRC,COD_PRD_PRD,NUM_CCPT_CCPT "
				+ " order by COD_STRC_STRC,COD_PRD_PRD,NUM_CCPT_CCPT ";

		List<ContratCpt> list = jt.query(requete, new RowMapper() {

			public Object mapRow(ResultSet rs, int rowNum) throws SQLException {

				ContratCpt contratCpt = new ContratCpt();
				ContratCptId contratCptId = new ContratCptId();
				contratCptId.setCodStrcStrc(rs.getLong("COD_STRC_STRC"));
				contratCptId.setCodPrdPrd(rs.getLong("COD_PRD_PRD"));
				contratCptId.setNumCcptCcpt(rs.getLong("NUM_CCPT_CCPT"));
				contratCpt.setContratCptId(contratCptId);
				return contratCpt;
			}
		});
		return list;
	}

	public List<PowerCardSMS> getListeSMSPowerCard() {
		jt = new JdbcTemplate(dataSource);

		String requete = "select * from POWERCARD.PCRD_SALARY_SMS where ETAT_ENV_SMS=0 order by NUM_TEL_SAL ";

		List<PowerCardSMS> list = jt.query(requete, new RowMapper() {

			public Object mapRow(ResultSet rs, int rowNum) throws SQLException {

				PowerCardSMS powerCardSMS = new PowerCardSMS();

				powerCardSMS.setNumTelSal(rs.getString("NUM_TEL_SAL"));
				powerCardSMS.setTextSmsSal(rs.getString("TEXT_SMS_SAL"));
				powerCardSMS.setEtatEnvSms(rs.getLong("ETAT_ENV_SMS"));
				powerCardSMS.setNumSeqSms(rs.getLong("NUM_SEQ_SMS"));
				
				return powerCardSMS;
			}
		});
		return list;
	}

	public void updateSMSPowerCard(PowerCardSMS powerCardSMS) {
		jt = new JdbcTemplate(dataSource);

		String requete =
				"update POWERCARD.PCRD_SALARY_SMS set ETAT_ENV_SMS=1 ,DAT_ENV_SMS=sysdate where ETAT_ENV_SMS=0 and NUM_TEL_SAL ='"
						+ powerCardSMS.getNumTelSal() + "'";
		jt.execute(requete);
		jt.execute("commit ");
	}
	// ********************Getter and Setter *******//

	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	public DataSource getDataSource() {
		return dataSource;
	}

	public void setSqlQuery(String sqlQuery) {
		this.sqlQuery = sqlQuery;
	}

	public void setFormaterDate(SimpleDateFormat formaterDate) {
		this.formaterDate = formaterDate;
	}

	public SimpleDateFormat getFormaterDate() {
		return formaterDate;
	}

}