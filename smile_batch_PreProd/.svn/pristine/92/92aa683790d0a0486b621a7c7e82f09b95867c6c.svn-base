package com.bna.smile.model.banqueAssurance.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.sql.DataSource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import com.bna.commun.model.Assurances;
import com.bna.commun.model.ContratCpt;
import com.bna.commun.model.ContratCptId;
import com.bna.commun.model.GlobalVirement;
import com.bna.commun.util.DateHandler;

public class AssuranceSereniteDAO {

	private static final long serialVersionUID = 1L;
	private static final Log logger = LogFactory.getLog(AssuranceSereniteDAO.class);

	protected JdbcTemplate jt;
	protected DataSource dataSource;

	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	public Long getSequenceAdhesionCapi() {
		jt = new JdbcTemplate(dataSource);
		Long numeroSequence = (Long) jt.queryForObject("select SEQ_ADHESION_ASS_CAPI.NEXTVAL from dual ", Long.class);
		return numeroSequence;
	}

	public Float getValeurTmg(Long annee, Long duree) {
		jt = new JdbcTemplate(dataSource);
		Float tmg = (Float) jt.queryForObject("select TMG from TARIF_ASSURANCE_CAPI where annee=" + annee + ""
				+ " and duree=" + duree + " and rownum=1", Float.class);
		return tmg;
	}

	public Float getValeurFga(Long annee) {
		jt = new JdbcTemplate(dataSource);
		Float fga =
				(Float) jt.queryForObject("select FGA from TARIF_ASSURANCE_CAPI where annee=" + annee + " and rownum=1",
						Float.class);
		return fga;
	}

	public Float getValeurFraisAdminEpargne(Long annee) {
		jt = new JdbcTemplate(dataSource);
		Float fga = (Float) jt.queryForObject(
				"select FRAIS_ADMIN_EPARGNE from TARIF_ASSURANCE_CAPI where annee=" + annee + "  and rownum=1",
				Float.class);
		return fga;
	}

	public Float getValeurFraisAdminPrimeAssur(Long annee) {
		jt = new JdbcTemplate(dataSource);
		Float fga = (Float) jt.queryForObject(
				"select FRAIS_ADMIN_ASSUR from TARIF_ASSURANCE_CAPI where annee=" + annee + " and rownum=1",
				Float.class);
		return fga;
	}

	public Assurances getAdrMailAssurance(Long codAss) {
		jt = new JdbcTemplate(dataSource);
		String req = "select * from ASSURANCES t where t.cod_ass_ass =" + codAss;

		@SuppressWarnings("unchecked")
		List<Assurances> list = jt.query(req, new RowMapper() {

			public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
				Assurances detailAss = new Assurances();
				detailAss.setCodAssAss(rs.getLong("COD_ASS_ASS"));
				detailAss.setLibAssAss(rs.getString("LIB_ASS_ASS"));
				detailAss.setSiglAssAss(rs.getString("SIGL_ASS_ASS"));
				detailAss.setAdrMailAss(rs.getString("ADR_MAIL_ASS"));

				return detailAss;
			}
		});
		return list.get(0);
	}

	public String getLienParenteBenefeCapi(String codeLien, Long numSeqBenef) {
		jt = new JdbcTemplate(dataSource);
		String lien;
		try {
			String req = "select LIB_LIEN_CAPI from LIEN_PARENTE_CAPI lien,BENEFICIAIRE_CAPI benef where "
					+ "benef.QUALITE_BENEF=lien.COD_LIEN_CAPI and benef.QUALITE_BENEF='" + codeLien
					+ "' and benef.num_seq_benef=" + numSeqBenef;

			lien = (String) jt.queryForObject(req, String.class);
			System.out.println(lien);
			if (lien != null)
				return lien;
			else
				return "";
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}

	public List<GlobalVirement> getVirementsLibresCAPI(Date dateChargement) {

		List<GlobalVirement> liste = new ArrayList<GlobalVirement>();
		jt = new JdbcTemplate(dataSource);
		String req =
				"select  NUM_SEQ_GVIR	,DAT_CRE_GVIR,	MNT_GVIR_GVIR,	COD_STRC_CCPT,	COD_PRD_CCPT	,NUM_CCPT_CCPT,	REF_DOSS_CAPI "
						+ " from global_virement where COD_PRD_PRD in ('2426') and ETAT_GVIR_GVIR=1  ";
		if (dateChargement != null) {
			req += " and DAT_CRE_GVIR>='" + DateHandler.dateToStr(dateChargement) + "' ";
		}

		req += " order by DAT_CRE_GVIR,NUM_SEQ_GVIR ";
		liste = jt.query(req, new RowMapper() {

			public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
				GlobalVirement virement = new GlobalVirement();

				virement.setNumSeqGvir(rs.getString("NUM_SEQ_GVIR"));
				virement.setDatCreGvir(rs.getDate("DAT_CRE_GVIR"));
				virement.setMntGvirGvir(rs.getLong("MNT_GVIR_GVIR"));
				ContratCpt cpt = new ContratCpt();
				ContratCptId contratCptId = new ContratCptId();
				contratCptId.setCodStrcStrc(rs.getLong("COD_STRC_CCPT"));
				contratCptId.setCodPrdPrd(rs.getLong("COD_PRD_CCPT"));
				contratCptId.setNumCcptCcpt(rs.getLong("NUM_CCPT_CCPT"));
				cpt.setContratCptId(contratCptId);
				virement.setContratCpt(cpt);
				virement.setRefDossierCapi(rs.getString("REF_DOSS_CAPI"));
				return virement;
			}

		});

		return liste;

	}

}
