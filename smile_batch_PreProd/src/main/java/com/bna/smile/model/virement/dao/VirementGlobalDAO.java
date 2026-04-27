package com.bna.smile.model.virement.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.sql.DataSource;

import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import com.bna.smile.model.virement.model.ParametreBNA;

public class VirementGlobalDAO {

	protected String sqlQuery;
	protected JdbcTemplate jt;
	protected DataSource dataSource;
	private SimpleDateFormat formaterDate = new SimpleDateFormat("dd/MM/yyyy");
	private static final Logger logger = Logger.getLogger(VirementGlobalDAO.class);

	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	public DataSource getDataSource() {
		return dataSource;
	}

	public void setSqlQuery(String sqlQuery) {
		this.sqlQuery = sqlQuery;
	}

	public Long getSequenceGlobalVirement() {

		jt = new JdbcTemplate(dataSource);
		Long numeroSequence = (Long) jt.queryForObject("select SEQ_GLOBAL_VIREMENT.NEXTVAL from dual ", Long.class);
		return numeroSequence;
	}

	public Long getSequenceTraceOperVirement() {

		jt = new JdbcTemplate(dataSource);
		Long numeroSequence = (Long) jt.queryForObject("select SEQ_TRACE_OPER_VIREMENT.NEXTVAL from dual ", Long.class);
		return numeroSequence;
	}

	public Long getSequenceTraceVirement() {

		jt = new JdbcTemplate(dataSource);
		Long numeroSequence = (Long) jt.queryForObject("select SEQ_TRACE_VIREMENT.NEXTVAL from dual ", Long.class);
		return numeroSequence;
	}

	public Long getSequenceDetailsOperationVirement() {

		jt = new JdbcTemplate(dataSource);
		Long numeroSequence =
				(Long) jt.queryForObject("select SEQ_DETAILS_OPERATION_VIR.NEXTVAL from dual ", Long.class);
		return numeroSequence;
	}

	public Long getSequenceFluxComptableVirement() {

		jt = new JdbcTemplate(dataSource);
		Long numeroSequence =
				(Long) jt.queryForObject("select SEQ_FLUX_COMPTABLE_VIREMENT.NEXTVAL from dual ", Long.class);
		return numeroSequence;
	}

	public Long getSequenceEnvoiCTX() {

		jt = new JdbcTemplate(dataSource);
		Long numeroSequence = (Long) jt.queryForObject("select SEQ_ENVOI_CTX.NEXTVAL from dual ", Long.class);
		return numeroSequence;
	}

	public Long getNbreTraceOperVirement(String numSeqGvir) {

		jt = new JdbcTemplate(dataSource);

		String reqSQL = " select count(TRACE_OPER_VIREMENT.NUM_SEQ_TVIR) \n"
				+ "   from GLOBAL_VIREMENT, DETAIL_VIREMENT, TRACE_OPER_VIREMENT \n"
				+ "   where GLOBAL_VIREMENT.NUM_SEQ_GVIR = DETAIL_VIREMENT.NUM_SEQ_GVIR \n"
				+ "   AND DETAIL_VIREMENT.NUM_SEQ_GVIR = TRACE_OPER_VIREMENT.NUM_SEQ_DGVIR \n"
				+ "   AND DETAIL_VIREMENT.NUM_SEQ_DETV = TRACE_OPER_VIREMENT.NUM_SEQ_DETV \n"
				+ "   AND TRACE_OPER_VIREMENT.NUM_SEQ_DGVIR = '" + numSeqGvir + "'";
		System.out.println("----------------  Debut  getNbreTraceOperVirement ---------------------------");
		System.out.println(reqSQL);
		System.out.println("----------------  Fin  getNbreTraceOperVirement -----------------------------");

		Long nbre = (Long) jt.queryForObject(reqSQL, Long.class);
		return nbre;
	}

	// ////////////////
	public List getListAgencesVirement() {
		jt = new JdbcTemplate(dataSource);

		String requete = "select J.COD_STRC_STRC, to_char(J.DAT_JRN_JRN,'DD/MM/YYYY') as DAT_JRN_JRN, J.COD_DOM_DOMM  "
				+ " from JOURNEE_STRUCTURE_DOMAINE J, STRUCTURE STR "
				+ " where  J.COD_DOM_DOMM = 7  and (J.COD_STAT_JSD = 0 or J.COD_STAT_JSD = 3)"
				+ " and DAT_JRN_JRN in (select max(I.DAT_JRN_JRN) from JOURNEE_STRUCTURE_DOMAINE I  "
				+ " where I.COD_STRC_STRC = J.COD_STRC_STRC and I.COD_DOM_DOMM = 7)  "
				+ " and STR.COD_STRC_STRC = J.COD_STRC_STRC and STR.COD_TSTR_TSTR = 1 order by j.cod_strc_strc";

		// System.out.println(requete);
		logger.info(requete);
		List listAgencesVirement = jt.queryForList(requete);
		logger.info("la requete a ramené " + String.valueOf(listAgencesVirement.size()));
		logger.info("listAgencesVirement " + listAgencesVirement);
		return listAgencesVirement;

	}

	// ////////////////
	public List getListAgencesBNA() {
		jt = new JdbcTemplate(dataSource);

		String requete = "select J.COD_STRC_STRC, to_char(J.DAT_JRN_JRN,'DD/MM/YYYY') as DAT_JRN_JRN, J.COD_DOM_DOMM  "
				+ " from JOURNEE_STRUCTURE_DOMAINE J, STRUCTURE STR " + " where  J.COD_DOM_DOMM = 1   "
				+ " and DAT_JRN_JRN in (select max(I.DAT_JRN_JRN) from JOURNEE_STRUCTURE_DOMAINE I  "
				+ " where I.COD_STRC_STRC = J.COD_STRC_STRC and I.COD_DOM_DOMM = 1)  "
				+ " and STR.COD_STRC_STRC = J.COD_STRC_STRC and STR.COD_TSTR_TSTR = 1   order by j.cod_strc_strc";

		// System.out.println(requete);
		logger.info(requete);
		List listAgencesVirement = jt.queryForList(requete);
		logger.info("la requete a ramené " + String.valueOf(listAgencesVirement.size()));
		logger.info("getListAgencesBNA " + listAgencesVirement);
		return listAgencesVirement;

	}

	// ////////////////
	public List getListAgencesComptesLiees() {
		jt = new JdbcTemplate(dataSource);

		String requete =
				"select J.COD_STRC_STRC, to_char(J.DAT_JRN_JRN,'DD/MM/YYYY'), J.COD_DOM_DOMM  from JOURNEE_STRUCTURE_DOMAINE J"
						+ " where  J.COD_DOM_DOMM = 7  and (J.COD_STAT_JSD = 0 or J.COD_STAT_JSD = 3)and DAT_JRN_JRN in  "
						+ " (select max(I.DAT_JRN_JRN) from JOURNEE_STRUCTURE_DOMAINE I "
						+ " where I.COD_STRC_STRC = J.COD_STRC_STRC and I.COD_DOM_DOMM = 7)"
						+ " and J.COD_STRC_STRC in (select distinct(C.COD_STRC_STRC) from contrat_cpt c where c.cod_prd_prd=165) order by j.cod_strc_strc";

		// System.out.println(requete);
		logger.info(requete);
		List listAgencesVirement = jt.queryForList(requete);
		logger.info("la requete a ramené " + String.valueOf(listAgencesVirement.size()));

		return listAgencesVirement;

	}

	public Date getMinDateFromDetailsVirements(String NumSeqGvir) throws DataAccessException {

		jt = new JdbcTemplate(dataSource);
		String reqSQL = "select min(dat_ech_detv) from detail_virement where num_seq_gvir='" + NumSeqGvir + "'";
		Date date = (Date) (jt.queryForObject(reqSQL, Date.class));
		return date;
	}

	public void setFormaterDate(SimpleDateFormat formaterDate) {
		this.formaterDate = formaterDate;
	}

	public SimpleDateFormat getFormaterDate() {
		return formaterDate;
	}

	public List<Long> getListeStructuresByVirement(String numSeqGvir) {
		jt = new JdbcTemplate(dataSource);

		try {

			String req =
					"select cod_strc_strc from structure where to_number(cod_bct_strc) in (select distinct(COD_AGBC_AGBC) from detail_virement"
							+ " where cod_bank_bank=3 and  num_seq_gvir= " + numSeqGvir + " ) order by  cod_strc_strc";

			List<Long> listeStrc = jt.queryForList(req, Long.class);
			return listeStrc;
		} catch (Exception e) {
			e.printStackTrace();
			return null;

		}

	}

	public String getCodeStructureBCT(Long codeStructureBNA) {

		jt = new JdbcTemplate(dataSource);
		String codeStructureBCT = (String) jt.queryForObject(
				"SELECT  LPAD(COD_BCT_STRC,3,'0') FROM STRUCTURE WHERE COD_STRC_STRC=" + codeStructureBNA + "",
				String.class);
		return codeStructureBCT;
	}

	public Long getSequenceTraceBatch() {

		jt = new JdbcTemplate(dataSource);

		Long numeroSequence = (Long) jt.queryForObject("select (max(NUM_SEQ_TRC)) from trace_batch ", Long.class);

		if (numeroSequence != null && numeroSequence.longValue() > 0) {

			numeroSequence += Long.valueOf(1);
		} else {
			numeroSequence = Long.valueOf(1);
		}

		return numeroSequence;
	}

	public List<String> getListeVirementsEcheances(Long codeStructure, Date dateComptable) {
		jt = new JdbcTemplate(dataSource);
		SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
		try {

			String req = "select distinct(glob.num_seq_gvir ) from global_virement glob,detail_virement det "
					+ " where glob.cod_strc_strc=" + codeStructure + " and det.num_seq_gvir = glob.num_seq_gvir "
					+ " and glob.dat_cre_gvir <'" + format.format(dateComptable) + "' " + " and det.dat_ech_detv <='"
					+ format.format(dateComptable) + "' "
					+ " and det.eta_detv_detv in (0,6,7) and glob.etat_gvir_gvir in (0,4,6,7) "
					+ " and glob.cod_prd_prd=1064 " + " union all "
					+ " select distinct( glob.num_seq_gvir ) from global_virement glob,detail_virement det , Virement_Differe vir "
					+ " where glob.cod_strc_strc=" + codeStructure
					+ " and det.num_seq_gvir = glob.num_seq_gvir  and vir.NUM_VIR = glob.num_seq_gvir "
					+ " and glob.dat_cre_gvir <'" + format.format(dateComptable) + "' " + " and det.dat_ech_detv <='"
					+ format.format(dateComptable) + "' "
					+ " and glob.cod_prd_prd !=1064 and det.eta_detv_detv in (0,6,7) and glob.etat_gvir_gvir in (0,6,7) "
					+ "order by num_seq_gvir ";

			List<String> listeRemises = jt.queryForList(req, String.class);
			return listeRemises;
		} catch (Exception e) {
			e.printStackTrace();
			return null;

		}

	}

	public List<String> getListeVirementsCash(Long codeStructure, Date dateComptable) {
		jt = new JdbcTemplate(dataSource);
		SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
		try {

			String req = "select distinct(glob.num_seq_gvir) from global_virement glob,detail_virement det 		"
					+ " where  det.num_seq_gvir = glob.num_seq_gvir " + " and glob.dat_cre_gvir <'"
					+ format.format(dateComptable) + "' " + " and det.dat_ech_detv <='" + format.format(dateComptable)
					+ "' " + "	and glob.cod_strc_strc= " + codeStructure + " and det.eta_detv_detv in (0,6,7) "
					+ " and glob.etat_gvir_gvir in (0,4,6,7) "
					+ "	and ((glob.cod_prd_prd in (1063)and glob.flag_exec_gvir=2) or (glob.cod_prd_prd in (1062)and glob.flag_exec_gvir=0))  "
					+ "           and glob.src_gvir_gvir='@'" + " order by glob.num_seq_gvir";

			List<String> listeRemises = jt.queryForList(req, String.class);
			return listeRemises;
		} catch (Exception e) {
			e.printStackTrace();
			return null;

		}

	}

	public List<ParametreBNA> getParametreSGMT() {
		jt = new JdbcTemplate(dataSource);
		String requete =
				" select NOM_PARAMETRE,VAL_PARAMETRE,NUM_HDJ_JRN,NUM_HFJ_JRN from  PARAMETRE_BNA where NOM_PARAMETRE='SGMT' ";

		List<ParametreBNA> list = jt.query(requete, new RowMapper() {

			public Object mapRow(ResultSet rs, int rowNum) throws SQLException {

				ParametreBNA parametreBNA = new ParametreBNA();
				parametreBNA.setNomParametre(rs.getString("NOM_PARAMETRE"));
				parametreBNA.setValParametre(rs.getString("VAL_PARAMETRE"));
				parametreBNA.setNumHdjJrn(rs.getLong("NUM_HDJ_JRN"));
				parametreBNA.setNumHfjJrn(rs.getLong("NUM_HFJ_JRN"));
				return parametreBNA;
			}
		});
		return list;
	}

	public void initParametreSGMT(Long heureDebut, Long heureFin) {
		jt = new JdbcTemplate(dataSource);

		jt.execute(" update PARAMETRE_BNA  set NUM_HDJ_JRN = " + heureDebut + ",NUM_HFJ_JRN =" + heureFin
				+ "  where NOM_PARAMETRE='SGMT' ");
		jt.execute("commit ");

	}

	public Long getNbreVirementMassesBNABNA(String numSeqGvir, Long codeStructure) {
		Long nbre = 0L;
		String valeur = "";
		try {
			jt = new JdbcTemplate(dataSource);

			String reqSQL = "select count(*) from detail_virement  det,structure str "
					+ " where 	det.cod_bank_bank=3 and lpad(det.cod_agbc_agbc,3,'0')!=lpad(str.cod_bct_strc,3,'0') "
					+ "	and str.cod_strc_strc=" + codeStructure + " and det.num_seq_gvir='" + numSeqGvir + "'";

			valeur = (String) jt.queryForObject(reqSQL, String.class);

			if (valeur != null && valeur.length() > 0) {
				nbre = Long.valueOf(valeur);
			}

		} catch (Exception e) {
			nbre = 0L;
		}

		return nbre;
	}

	public Long getNbreMinVirementMasses() {
		Long nbre = 0L;
		String valeur = "";
		try {
			jt = new JdbcTemplate(dataSource);

			String reqSQL = "select val_parametre from PARAMETRE_BNA where nom_parametre='NBRE_MIN_VIR'";

			valeur = (String) jt.queryForObject(reqSQL, String.class);

			if (valeur != null && valeur.length() > 0) {
				nbre = Long.valueOf(valeur);
			}

		} catch (Exception e) {
			nbre = 0L;
		}

		return nbre;
	}

	public Long getNbreFluxComptVirement(String numSeqGvir, Long codStrcRecp, Date dateOperation, Long typeFlux) {
		Long nbre = 0L;
		try {
			jt = new JdbcTemplate(dataSource);

			String reqSQL = "select count(*) from flux_compt_virement where num_seq_gvir='" + numSeqGvir
					+ "'  and cod_strc_strc= " + codStrcRecp + " and dat_oper_flux='"
					+ formaterDate.format(dateOperation) + "' and type_flux_vir=" + typeFlux + "";

			nbre = (Long) jt.queryForObject(reqSQL, Long.class);

		} catch (Exception e) {
			nbre = 0L;
		}

		return nbre;
	}
}