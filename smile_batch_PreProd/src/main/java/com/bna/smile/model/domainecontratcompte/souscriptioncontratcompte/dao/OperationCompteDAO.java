package com.bna.smile.model.domainecontratcompte.souscriptioncontratcompte.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import com.bna.commun.model.ContratCpt;
import com.bna.commun.model.ContratCptId;
import com.bna.commun.util.DateHandler;
import com.bna.commun.util.StrHandler;
import com.bna.smile.web.procuration.util.ContratCptView;

public class OperationCompteDAO {

	public OperationCompteDAO() {
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

	public Long getSequenceOperationCompte() {
		jt = new JdbcTemplate(dataSource);
		Long numeroSequence = (Long) jt.queryForObject("select SEQ_NUM_OPER_COMPTE.NEXTVAL from dual ", Long.class);
		return numeroSequence;
	}

	public List<ContratCptView> getListesDesComptesPacks(Long codeStructure, Date dateOuverture) {
		try {

			jt = new JdbcTemplate(dataSource);

			String req = " select cptPack.*,cpt.cod_etat_ccpt as etatCompte "
					+ " from Contrat_CPT_PACK cptPack, Contrat_Cpt cpt "
					+ " where cptPack.COD_STRC_STRC = cpt.COD_STRC_STRC " + " and cptPack.COD_PRD_PRD=cpt.COD_PRD_PRD "
					+ " and cptPack.NUM_CCPT_CCPT=cpt.NUM_CCPT_CCPT " + " and  cptPack.COD_STRC_STRC=" + codeStructure
					+ "  and cpt.cod_etat_ccpt='V'  and cpt.DAT_OUV_CCPT <'" + DateHandler.dateToStr(dateOuverture)
					+ "' and (lpad(cptPack.cod_strc_strc,3,'0')||lpad(cptPack.cod_prd_prd,4,'0')|| lpad(cptPack.NUM_CCPT_CCPT,6,'0')) = cptPack.COMPTE "
					+ " order by Cptpack.Cod_Strc_Strc,cptPack.COD_PRD_PRD,cptPack.NUM_CCPT_CCPT ";

			@SuppressWarnings("unchecked")
			List<ContratCptView> list = jt.query(req, new RowMapper() {

				public ContratCptView mapRow(ResultSet rs, int rowNum) throws SQLException {
					ContratCptView contratCptView = new ContratCptView();
					contratCptView.setCodeAgence(rs.getLong("COD_STRC_STRC") + "");
					ContratCpt contratCpt = new ContratCpt();
					ContratCptId contratCptId = new ContratCptId();
					contratCptId.setCodStrcStrc(rs.getLong("COD_STRC_STRC"));
					contratCptId.setCodPrdPrd(rs.getLong("COD_PRD_PRD"));
					contratCptId.setNumCcptCcpt(rs.getLong("NUM_CCPT_CCPT"));
					contratCpt.setContratCptId(contratCptId);
					contratCptView.setContratCpt(contratCpt);
					contratCptView.setCodeProduit(rs.getLong("COD_PRD_PRD") + "");
					contratCptView.setNumeroCompte(rs.getLong("NUM_CCPT_CCPT") + "");
					contratCptView.setEtatCompte(rs.getString("ETATCOMPTE"));
					contratCptView.setCodePrdPack(rs.getLong("COD_PRD_PACK"));
					contratCptView.setPeriodPrelevement(rs.getString("PER_FACT_PACK"));
					contratCptView.setCompte13(contratCptId.getCompteClient().replace(" ", ""));

					return contratCptView;
				}
			});
			return list;
		} catch (Exception e) {
			return null;
		}

	}

	public Long getNaturePack(Long codeProduit) {
		jt = new JdbcTemplate(dataSource);
		Long codeNaturePack = (Long) jt
				.queryForObject("select COD_NAT_PACK from Nature_Pack where Cod_Prd_Pack= " + codeProduit, Long.class);
		return codeNaturePack;
	}

	public boolean verifierExistancePerceptionByCritere(ContratCpt contratCpt, Long codeProduit, Long mois,
			Long annee) {
		try {
			jt = new JdbcTemplate(dataSource);
			String requete = " select count(*) from Trace_Frais_Pack " + " where Cod_Strc_Strc= "
					+ contratCpt.getContratCptId().getCodStrcStrc() + " and Cod_Prd_Prd= "
					+ contratCpt.getContratCptId().getCodPrdPrd() + " and Num_Ccpt_Ccpt= "
					+ contratCpt.getContratCptId().getNumCcptCcpt() + " and Cod_Prd_Pack= " + codeProduit
					+ " and Mois_Trc_Pack=" + mois + " and Annee_Trc_Pack=" + annee + " and Etat_Trc_Pack='T'";
			//System.out.println( "requete : "+requete);
			Long nombre = (Long) jt.queryForObject(requete, Long.class);
			if (nombre.longValue() == 0l) {
				return false;
			} else {
				return true;
			}
		} catch (Exception e) {
			return false;
		}

	}
}
