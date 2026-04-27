package com.bna.smile.model.prelevement.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.sql.DataSource;

import org.apache.log4j.Logger;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import com.bna.commun.model.AdDetailDomiciliation;
import com.bna.commun.model.Structure;
import com.bna.commun.util.ContextHandler;
import com.bna.commun.util.DateHandler;
import com.bna.smile.model.domainecommun.model.Listes;
import com.bna.smile.model.moyenPayement.model.Virement;
import com.bna.smile.model.prelevement.model.ADDetailDomiciliationVo;
import com.bna.smile.model.prelevement.model.ADDetailPrelevementVo;
import com.oxia.fwk.context.Context;
import com.oxia.fwk.core.ICriteria;
import com.oxia.fwk.core.IExpression;
import com.oxia.fwk.core.ISearchEngine;
import com.oxia.fwk.searchengine.SearchEngine;

public class PrelevementDAO {

	protected String sqlQuery;
	protected JdbcTemplate jt;
	protected DataSource dataSource;
	private SimpleDateFormat formaterDate = new SimpleDateFormat("dd/MM/yyyy");
	private static final Logger logger = Logger.getLogger(PrelevementDAO.class);

	// ************** Methodes *********************//
	public List getListJourneesAgences() {
		jt = new JdbcTemplate(dataSource);

		String requete =
				"select J.COD_STRC_STRC, to_char(J.DAT_JRN_JRN,'DD/MM/YYYY') as DAT_JRN_JRN, J.COD_DOM_DOMM  from JOURNEE_STRUCTURE_DOMAINE J , STRUCTURE STR"
						+ " where  J.COD_DOM_DOMM = 7  and J.COD_STAT_JSD !=2 and DAT_JRN_JRN in  "
						+ " (select max(I.DAT_JRN_JRN) from JOURNEE_STRUCTURE_DOMAINE I "
						+ " where I.COD_STRC_STRC = J.COD_STRC_STRC and I.COD_DOM_DOMM = 7) "
						+ " and STR.COD_STRC_STRC = J.COD_STRC_STRC and STR.COD_TSTR_TSTR = 1 "
						+ " order by j.cod_strc_strc";// +
													  // "and
													  // J.COD_STRC_STRC=120";

		// System.out.println(requete);
		logger.info(requete);
		List listAgences = jt.queryForList(requete);
		logger.info("la requete a ramené " + String.valueOf(listAgences.size()));

		return listAgences;

	}

	public Long getNombresDesLotsPrelevements(Long codeValeur, Long codStrcBCT) {
		jt = new JdbcTemplate(dataSource);
		long nombre = 0;
		String requete = "SELECT COUNT(*) FROM DETAILS_PRELEVEMENTS WHERE COD_SEN_PRL=2 "
				+ " AND COD_DEV_PRL=788 AND COD_ETAT_PRL ='A' AND COD_VAL_PRL='" + codeValeur + "' AND COD_ENR_PRL=21"
				+ " AND COD_AGE_PRL= " + codStrcBCT + "";

		nombre = jt.queryForLong(requete);
		return nombre;
	}

	public Long getNombresDesLotsDomiciliations(Long codeValeur, Long codStrcBCT) {
		jt = new JdbcTemplate(dataSource);
		long nombre = 0;
		String requete = "SELECT COUNT(*) FROM DETAIL_DOMICILIATION_TEMP WHERE COD_SEN_DOM=2 "
				+ " AND COD_ETAT_DOM ='A' AND COD_DEV_DEV=788  AND COD_VAL_DOM='" + codeValeur + "'	AND COD_AGE_DES= "
				+ codStrcBCT + "";

		nombre = jt.queryForLong(requete);
		return nombre;
	}

	public Long getSequenceContratDomiciliation() {

		jt = new JdbcTemplate(dataSource);
		Long numeroSequence =
				(Long) jt.queryForObject("select SEQ_CONTRAT_DOMICILIATION.NEXTVAL from dual ", Long.class);
		return numeroSequence;
	}

	public Long getSequenceTraceContratDomiciliation() {

		jt = new JdbcTemplate(dataSource);
		Long numeroSequence =
				(Long) jt.queryForObject("select SEQ_TRACE_CONT_DOMICILIATION.NEXTVAL from dual ", Long.class);
		return numeroSequence;
	}

	public Long getCodeStructureBNA(String codeStructureBCT) {

		jt = new JdbcTemplate(dataSource);
		Long codeStructureBNA = (Long) jt.queryForObject(
				"SELECT COD_STRC_STRC FROM STRUCTURE WHERE COD_BCT_STRC=" + Long.valueOf(codeStructureBCT) + "",
				Long.class);
		return codeStructureBNA;
	}

	public String getCodeStructureBCT(Long codeStructureBNA) {

		jt = new JdbcTemplate(dataSource);
		String codeStructureBCT = (String) jt.queryForObject(
				"SELECT  LPAD(COD_BCT_STRC,3,'0') FROM STRUCTURE WHERE COD_STRC_STRC=" + codeStructureBNA + "",
				String.class);
		return codeStructureBCT;
	}

	public Long getSequenceMvtPrelevements() {

		jt = new JdbcTemplate(dataSource);
		Long numeroSequence = (Long) jt.queryForObject("select SEQ_MVT_PRELEVEMENTS.NEXTVAL from dual ", Long.class);
		return numeroSequence;
	}

	public List<Long> getListAgencePrelevements() throws ParseException {
		List<Long> listeAgences = new ArrayList<Long>();
		jt = new JdbcTemplate(dataSource);
		listeAgences = jt.queryForList("SELECT DISTINCT(COD_AGE_DES) FROM details_prelevements WHERE COD_ETAT_PRL='A'");
		return listeAgences;
	}

	public List getSommePrelevements(Date dateComptable, Long codeAgence) {

		jt = new JdbcTemplate(dataSource);
		List liste = jt.queryForList(
				"SELECT SUM(MNT_PRL_PRL), COUNT(*),num_lot_prl FROM details_prelevements WHERE COD_AGE_PRL="
						+ codeAgence + " and dat_ope_prl = to_date('" + formaterDate.format(dateComptable)
						+ "','DD/MM/YYYY')" + "group by num_lot_prl");
		return liste;
	}

	public List<Long> getListAgenceDomiciliations() throws ParseException {
		List<Long> listeAgences = new ArrayList<Long>();
		jt = new JdbcTemplate(dataSource);
		listeAgences =
				jt.queryForList("SELECT DISTINCT(COD_AGE_DES) FROM detail_domiciliation_temp where COD_ETAT_DOM ='A'");
		return listeAgences;
	}

	// Getting codStrcBct by codStrcStrc
	public Structure findStructure(Long codStructure) {
		Listes listes = new Listes();
		Structure structure = new Structure();
		try {
			Context context = ContextHandler.getContext();
			ISearchEngine searchEngine = (SearchEngine) context.getBean("searchEngine");
			ICriteria criteria = searchEngine.createCriteria();
			IExpression expression = searchEngine.createExpression();
			criteria.add(expression.eq("codStrcStrc", codStructure));
			List res = searchEngine.find(Structure.class, criteria);
			Structure strc = (Structure) searchEngine.get(Structure.class, codStructure);
			return strc;
		} catch (Exception e) {
			com.oxia.fwk.core.Error erreur = new com.oxia.fwk.core.Error();
			StringBuffer text = new StringBuffer("Erreur dans findStructure : ");
			text.append(e.toString());
			erreur.setCode("500");
			erreur.setDescription(text.toString());
			erreur.setKey("StructureDAO");
			listes.addError(erreur);
			return (null);
		}
	}

	public List<ADDetailPrelevementVo> getListPrelevementACHAgence(Date dateComptable, Long codeAgence) {
		List<ADDetailPrelevementVo> listePrelevements = new ArrayList<ADDetailPrelevementVo>();
		jt = new JdbcTemplate(dataSource);
		try {
			String req =
					"SELECT addetail.* FROM IBANK.Ad_Detail_Prelevement@T24.BNA.TN addetail, IBANK.Ad_Prelevement@T24.BNA.TN ad  "
							+ " where addetail.DAT_OPE=ad.DAT_OPE and ad.REF_FIC=addetail.REF_FIC "
							+ " and ad.COD_VAL=addetail.COD_VAL and ad.NUM_LOT=addetail.NUM_LOT and ad.COD_STA=7 "
							+ " and ad.COD_SEN=2 and trunc(ad.DAT_OPE)='" + formaterDate.format(dateComptable)
							+ "' and addetail.COD_BAN='03' and substr(lpad(RIB_TIR,20,'0'),3,3)=lpad(" + codeAgence
							+ ",3,'0')   ";

			listePrelevements = jt.query(req, new RowMapper() {

				public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
					ADDetailPrelevementVo detailPrelevementVo = new ADDetailPrelevementVo();

					detailPrelevementVo.setNumLot(rs.getLong("NUM_LOT"));
					detailPrelevementVo.setDatOpe(rs.getDate("DAT_OPE"));
					detailPrelevementVo.setCodSen(rs.getLong("COD_SEN"));
					detailPrelevementVo.setCodAge(rs.getString("COD_AGE"));
					detailPrelevementVo.setCodBan(rs.getString("COD_BAN"));
					detailPrelevementVo.setCodVal(rs.getLong("COD_VAL"));
					detailPrelevementVo.setCodNatEta(rs.getLong("COD_NAT_ETA"));
					detailPrelevementVo.setCodDev(rs.getString("COD_DEV"));
					detailPrelevementVo.setCodEnr(rs.getLong("COD_ENR"));
					detailPrelevementVo.setMntPrl(rs.getLong("MNT_PRL"));
					detailPrelevementVo.setNumPrl(rs.getLong("NUM_PRL"));
					detailPrelevementVo.setRibTir(rs.getString("RIB_TIR"));
					detailPrelevementVo.setCodBanDes(rs.getString("COD_BAN_DES"));
					detailPrelevementVo.setCodAgeDes(rs.getString("COD_AGE_DES"));
					detailPrelevementVo.setRibBen(rs.getString("RIB_BEN"));
					detailPrelevementVo.setCodEmePrl(rs.getLong("COD_EME_PRL"));
					detailPrelevementVo.setNumRefDom(rs.getString("NUM_REF_DOM"));
					detailPrelevementVo.setLibPrl(rs.getString("LIB_PRL"));
					detailPrelevementVo.setDatCmpIni(rs.getDate("DAT_CMP_INI"));
					detailPrelevementVo.setMotRej(rs.getString("MOT_REJ"));
					detailPrelevementVo.setDatEch(rs.getDate("DAT_ECH"));
					detailPrelevementVo.setRefFic(rs.getString("REF_FIC"));
					detailPrelevementVo.setNumEvtEnv(rs.getLong("NUM_EVT_ENV"));
					detailPrelevementVo.setNumEvtRcp(rs.getLong("NUM_EVT_RCP"));
					detailPrelevementVo.setRjtReg(rs.getString("RJT_REG"));

					return detailPrelevementVo;
				}

			});
		} catch (Exception e) {
			e.printStackTrace();
		}
		return listePrelevements;
	}

	public List<ADDetailDomiciliationVo> getListDomiciliationsACHAgence(Date dateComptable, Long codeAgence) {
		List<ADDetailDomiciliationVo> listeDomiciliations = new ArrayList<ADDetailDomiciliationVo>();
		jt = new JdbcTemplate(dataSource);

		String req =
				"SELECT addetail.* FROM IBANK.AD_DETAIL_DOMICILIATION@T24.BNA.TN addetail, IBANK.AD_DOMICILIATION@T24.BNA.TN ad   "
						+ " where addetail.DAT_OPE=ad.DAT_OPE and ad.REF_FIC=addetail.REF_FIC "
						+ " and ad.COD_VAL=addetail.COD_VAL and ad.NUM_LOT=addetail.NUM_LOT and ad.COD_STA=7 and addetail.COD_SEN=2 "
						+ " and ad.COD_SEN=2 and ad.DAT_OPE= to_date('" + formaterDate.format(dateComptable)
						+ "','DD/MM/YYYY') and addetail.COD_BAN_DES='03'" + "and addetail.COD_AGE_DES=" + codeAgence;

		listeDomiciliations = jt.query(req, new RowMapper() {

			public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
				ADDetailDomiciliationVo detailDomiciliationVo = new ADDetailDomiciliationVo();

				detailDomiciliationVo.setNumLot(rs.getLong("NUM_LOT"));
				detailDomiciliationVo.setDatOpe(rs.getDate("DAT_OPE"));
				detailDomiciliationVo.setCodSen(rs.getLong("COD_SEN"));
				detailDomiciliationVo.setCodAge(rs.getString("COD_AGE"));
				detailDomiciliationVo.setCodBan(rs.getString("COD_BAN"));
				detailDomiciliationVo.setCodVal(rs.getLong("COD_VAL"));
				detailDomiciliationVo.setCodNatEta(rs.getLong("COD_NAT_ETA"));
				detailDomiciliationVo.setCodDev(rs.getString("COD_DEV"));
				detailDomiciliationVo.setCodEnr(rs.getLong("COD_ENR"));
				detailDomiciliationVo.setRibTir(rs.getLong("RIB_TIR"));
				detailDomiciliationVo.setCodBanDes(rs.getString("COD_BAN_DES"));
				detailDomiciliationVo.setCodAgeDes(rs.getString("COD_AGE_DES"));
				detailDomiciliationVo.setCodEmePrl(rs.getLong("COD_EME_PRL"));
				detailDomiciliationVo.setNumRefDom(rs.getString("NUM_REF_DOM"));
				detailDomiciliationVo.setRefFic(rs.getString("REF_FIC"));
				detailDomiciliationVo.setNumEvtEnv(rs.getLong("NUM_EVT_ENV"));
				detailDomiciliationVo.setNumEvtRcp(rs.getLong("NUM_EVT_RCP"));
				detailDomiciliationVo.setNumDom(rs.getLong("NUM_DOM"));
				detailDomiciliationVo.setCodPay(rs.getLong("COD_PAY"));
				detailDomiciliationVo.setCodMaj(rs.getString("COD_MAJ"));
				detailDomiciliationVo.setDatMaj(rs.getDate("DAT_MAJ"));

				return detailDomiciliationVo;
			}

		});
		return listeDomiciliations;
	}

	// **************Getter and Setter *********************//
	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	public void setSqlQuery(String sqlQuery) {
		this.sqlQuery = sqlQuery;
	}

	public DataSource getDataSource() {
		return dataSource;
	}

	public String getSqlQuery() {
		return sqlQuery;
	}

}