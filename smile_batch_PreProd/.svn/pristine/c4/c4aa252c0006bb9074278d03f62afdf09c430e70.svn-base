package com.bna.smile.model.compteGod.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import com.bna.commun.model.BlocageGod;
import com.bna.commun.model.ContratCpt;
import com.bna.commun.model.ContratCptId;
import com.bna.commun.model.DeblocageGod;
import com.bna.smile.model.compteGod.model.BlocageGodVo;
import com.oxia.fwk.core.IValueObject;

public class GodDAO {

	protected String sqlQuery;
	protected JdbcTemplate jt;
	protected DataSource dataSource;

	public GodDAO() {

	}

	@SuppressWarnings("unchecked")
	public IValueObject getListeBlocEnAtt(IValueObject vo) {
		BlocageGodVo blocageGodVo = (BlocageGodVo) vo;

		List<BlocageGod> listeBloc = new ArrayList<BlocageGod>();

		jt = new JdbcTemplate(dataSource);
		String rq = " ";

		if (blocageGodVo.getCodOper() == 999L) {
			rq =
					"select bloc.NUM_BLOC_BLOC,bloc.NUM_OPER_OMP,bloc.COD_STRC_STRC,bloc.COD_PRD_PRD,bloc.NUM_CCPT_CCPT,bloc.NUM_MATR_USER,"
							+ " bloc.MTF_BLOC,bloc.OBJ_BLOC,bloc.DATE_EXEC_BLOC,bloc.MNT_BLOC_BLOC,bloc.ETAT_BLOC_BLOC,bloc.MNT_BLOC_DEV, "
							+ " bloc.TYPE_CCPT_CCPT,bloc.MONT_PAOF_PAOF,bloc.MNT_UTL_DIN_BLOC,bloc.MNT_UTL_DEV_BLOC ,cpt.nom_inti_ccpt "
							+ " from blocage_god bloc,contrat_cpt cpt "
							+ " where  bloc.cod_strc_strc = cpt.cod_strc_strc and bloc.cod_prd_prd = cpt.cod_prd_prd "
							+ " and bloc.num_ccpt_ccpt = cpt.num_ccpt_ccpt and ETAT_BLOC_BLOC = 0 and bloc.TYPE_CCPT_CCPT = 2 and bloc.cod_strc_strc = "
							+ blocageGodVo.getParamAgence().getCodStrcStrc();
			jt.execute(rq);
			listeBloc = jt.query(rq, new BlocageGodRowMapper());
			blocageGodVo.setListeBlocageDevEnAtt(listeBloc);
		} else {
			rq =
					"select bloc.NUM_BLOC_BLOC,bloc.NUM_OPER_OMP,bloc.COD_STRC_STRC,bloc.COD_PRD_PRD,bloc.NUM_CCPT_CCPT,bloc.NUM_MATR_USER,"
							+ " bloc.MTF_BLOC,bloc.OBJ_BLOC,bloc.DATE_EXEC_BLOC,bloc.MNT_BLOC_BLOC,bloc.ETAT_BLOC_BLOC,bloc.MNT_BLOC_DEV, "
							+ " bloc.TYPE_CCPT_CCPT,bloc.MONT_PAOF_PAOF,bloc.MNT_UTL_DIN_BLOC,bloc.MNT_UTL_DEV_BLOC ,cpt.nom_inti_ccpt "
							+ " from blocage_god bloc,contrat_cpt cpt "
							+ " where  bloc.cod_strc_strc = cpt.cod_strc_strc and bloc.cod_prd_prd = cpt.cod_prd_prd "
							+ " and bloc.num_ccpt_ccpt = cpt.num_ccpt_ccpt and ETAT_BLOC_BLOC = 0 and bloc.TYPE_CCPT_CCPT = 1 and bloc.cod_strc_strc = "
							+ blocageGodVo.getParamAgence().getCodStrcStrc();
			jt.execute(rq);
			listeBloc = jt.query(rq, new BlocageGodRowMapper());
			blocageGodVo.setListeBlocageEnAtt(listeBloc);
		}

		return blocageGodVo;

	}

	@SuppressWarnings("unchecked")
	public IValueObject getListeBlocageVal(IValueObject vo) {
		BlocageGodVo blocageGodVo = (BlocageGodVo) vo;

		List<BlocageGod> listeBloc = new ArrayList<BlocageGod>();

		jt = new JdbcTemplate(dataSource);
		String rq = "";
		if (blocageGodVo.getCodOper() == 999L) {
			rq =
					"select bloc.NUM_BLOC_BLOC,bloc.NUM_OPER_OMP,bloc.COD_STRC_STRC,bloc.COD_PRD_PRD,bloc.NUM_CCPT_CCPT,bloc.NUM_MATR_USER,"
							+ " bloc.MTF_BLOC,bloc.OBJ_BLOC,bloc.DATE_EXEC_BLOC,bloc.MNT_BLOC_BLOC,bloc.ETAT_BLOC_BLOC,bloc.MNT_BLOC_DEV, "
							+ " bloc.TYPE_CCPT_CCPT,bloc.MONT_PAOF_PAOF,bloc.MNT_UTL_DIN_BLOC,bloc.MNT_UTL_DEV_BLOC ,cpt.nom_inti_ccpt "
							+ " from blocage_god bloc,contrat_cpt cpt "
							+ " where  bloc.cod_strc_strc = cpt.cod_strc_strc and bloc.cod_prd_prd = cpt.cod_prd_prd "
							+ " and bloc.num_ccpt_ccpt = cpt.num_ccpt_ccpt and  (ETAT_BLOC_BLOC = 1 or  ETAT_BLOC_BLOC = 6 )"
							+ "and bloc.TYPE_CCPT_CCPT = 2 and bloc.cod_strc_strc = "
							+ blocageGodVo.getParamAgence().getCodStrcStrc();
			if (blocageGodVo.getContratCptRech() != null && blocageGodVo.getContratCptRech().getContratCptId() != null) {

				rq +=
						" and bloc.NUM_CCPT_CCPT="
								+ blocageGodVo.getContratCptRech().getContratCptId().getNumCcptCcpt()
								+ " and bloc.COD_PRD_PRD ="
								+ blocageGodVo.getContratCptRech().getContratCptId().getCodPrdPrd()
								+ "  and bloc.COD_STRC_STRC= "
								+ blocageGodVo.getContratCptRech().getContratCptId().getCodStrcStrc();

			}
			jt.execute(rq);
			listeBloc = jt.query(rq, new BlocageGodRowMapper());
			blocageGodVo.setListBlocageDevVal(listeBloc);

		} else {
			rq =
					"select bloc.NUM_BLOC_BLOC,bloc.NUM_OPER_OMP,bloc.COD_STRC_STRC,bloc.COD_PRD_PRD,bloc.NUM_CCPT_CCPT,bloc.NUM_MATR_USER,"
							+ " bloc.MTF_BLOC,bloc.OBJ_BLOC,bloc.DATE_EXEC_BLOC,bloc.MNT_BLOC_BLOC,bloc.ETAT_BLOC_BLOC,bloc.MNT_BLOC_DEV, "
							+ " bloc.TYPE_CCPT_CCPT,bloc.MONT_PAOF_PAOF,bloc.MNT_UTL_DIN_BLOC,bloc.MNT_UTL_DEV_BLOC ,cpt.nom_inti_ccpt "
							+ " from blocage_god bloc,contrat_cpt cpt "
							+ " where  bloc.cod_strc_strc = cpt.cod_strc_strc and bloc.cod_prd_prd = cpt.cod_prd_prd "
							+ " and bloc.num_ccpt_ccpt = cpt.num_ccpt_ccpt and (ETAT_BLOC_BLOC = 1 or ETAT_BLOC_BLOC = 6 )"
							+ " and bloc.TYPE_CCPT_CCPT = 1 and bloc.cod_strc_strc = "
							+ blocageGodVo.getParamAgence().getCodStrcStrc();

			if (blocageGodVo.getContratCptRech() != null && blocageGodVo.getContratCptRech().getContratCptId() != null) {

				rq +=
						" and bloc.NUM_CCPT_CCPT="
								+ blocageGodVo.getContratCptRech().getContratCptId().getNumCcptCcpt()
								+ " and bloc.COD_PRD_PRD ="
								+ blocageGodVo.getContratCptRech().getContratCptId().getCodPrdPrd()
								+ "  and bloc.COD_STRC_STRC= "
								+ blocageGodVo.getContratCptRech().getContratCptId().getCodStrcStrc();

			}
			jt.execute(rq);
			listeBloc = jt.query(rq, new BlocageGodRowMapper());
			blocageGodVo.setListeBlocageVal(listeBloc);
		}

		return blocageGodVo;

	}

	@SuppressWarnings("unchecked")
	public IValueObject getListeDeblocGodEnAtt(IValueObject vo) {
		BlocageGodVo blocageGodVo = (BlocageGodVo) vo;

		List<DeblocageGod> listeDebloc = new ArrayList<DeblocageGod>();

		jt = new JdbcTemplate(dataSource);
		String rq = "";
		if (blocageGodVo.getCodOper() == 999L) {
			rq =
					"select debloc.num_deblc_deblc,debloc.num_bloc_bloc,debloc.cod_strc_strc,debloc.cod_prd_prd,"
							+ " debloc.num_ccpt_ccpt,cpt.nom_inti_ccpt,debloc.NUM_MATR_USER,debloc.typ_deblc_deblc,debloc.mnt_deblc_deblc, "
							+ " debloc.mnt_deblc_dev,debloc.date_exec_deblc,debloc.motif_deblc_deblc,debloc.etat_dbloc_dbloc "
							+ " from DEBLOCAGE_GOD debloc, BLOCAGE_GOD bloc,contrat_cpt cpt "
							+ " where  debloc.cod_strc_strc = cpt.cod_strc_strc and debloc.cod_prd_prd = cpt.cod_prd_prd "
							+ " and debloc.num_ccpt_ccpt = cpt.num_ccpt_ccpt and  debloc.etat_dbloc_dbloc = 0 and "
							+ "debloc.num_bloc_bloc = bloc.num_bloc_bloc and bloc.type_ccpt_ccpt = 2 and bloc.cod_strc_strc = "
							+ blocageGodVo.getParamAgence().getCodStrcStrc();
			jt.execute(rq);
			listeDebloc = jt.query(rq, new DeblocageGodRowMapper());
			blocageGodVo.setListDeblocageDevEnAtt(listeDebloc);
		} else {
			rq =
					"select debloc.num_deblc_deblc,debloc.num_bloc_bloc,debloc.cod_strc_strc,debloc.cod_prd_prd,"
							+ " debloc.num_ccpt_ccpt,cpt.nom_inti_ccpt,debloc.NUM_MATR_USER,debloc.typ_deblc_deblc,debloc.mnt_deblc_deblc, "
							+ " debloc.mnt_deblc_dev,debloc.date_exec_deblc,debloc.motif_deblc_deblc,debloc.etat_dbloc_dbloc "
							+ " from DEBLOCAGE_GOD debloc, BLOCAGE_GOD bloc,contrat_cpt cpt "
							+ " where  debloc.cod_strc_strc = cpt.cod_strc_strc and debloc.cod_prd_prd = cpt.cod_prd_prd "
							+ " and debloc.num_ccpt_ccpt = cpt.num_ccpt_ccpt and  debloc.etat_dbloc_dbloc = 0 and "
							+ "debloc.num_bloc_bloc = bloc.num_bloc_bloc and bloc.type_ccpt_ccpt = 1 and bloc.cod_strc_strc = "
							+ blocageGodVo.getParamAgence().getCodStrcStrc();
			jt.execute(rq);
			listeDebloc = jt.query(rq, new DeblocageGodRowMapper());
			blocageGodVo.setListeDeblocEnAtt(listeDebloc);
		}

		return blocageGodVo;

	}

	/**
	 * BlocageGod ROW MAPPER
	 * 
	 * @return
	 */
	class BlocageGodRowMapper implements RowMapper {

		public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
			BlocageGod blocageGod = new BlocageGod();
			ContratCpt contratCpt = new ContratCpt();
			ContratCptId contratCptId = new ContratCptId();

			blocageGod.setNumBlocBloc(rs.getLong("NUM_BLOC_BLOC"));
			contratCptId.setCodStrcStrc(rs.getLong("COD_STRC_STRC"));
			contratCptId.setCodPrdPrd(rs.getLong("COD_PRD_PRD"));
			contratCptId.setNumCcptCcpt(rs.getLong("NUM_CCPT_CCPT"));
			contratCpt.setContratCptId(contratCptId);
			contratCpt.setNomIntiCcpt(rs.getString("NOM_INTI_CCPT"));
			blocageGod.setContratCpt(contratCpt);
			blocageGod.setNumMatrUser(rs.getLong("NUM_MATR_USER"));
			blocageGod.setMtfBloc(rs.getString("MTF_BLOC"));
			blocageGod.setObjBloc(rs.getString("OBJ_BLOC"));
			blocageGod.setDateExecBloc(rs.getDate("DATE_EXEC_BLOC"));
			blocageGod.setMntBlocBloc(rs.getLong("MNT_BLOC_BLOC"));
			blocageGod.setTypeCcptCcpt(rs.getLong("TYPE_CCPT_CCPT"));
			blocageGod.setMntBlocDev(rs.getLong("MNT_BLOC_DEV"));
			blocageGod.setMontPaof(rs.getDouble("MONT_PAOF_PAOF"));
			blocageGod.setEtatBlocBloc(rs.getLong("ETAT_BLOC_BLOC"));
			return blocageGod;

		}

	}

	/**
	 * DeblocageGod ROW MAPPER
	 * 
	 * @return
	 */
	class DeblocageGodRowMapper implements RowMapper {

		public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
			DeblocageGod deblocageGod = new DeblocageGod();

			BlocageGod blocageGod = new BlocageGod();
			ContratCpt contratCpt = new ContratCpt();
			ContratCptId contratCptId = new ContratCptId();
			deblocageGod.setNumDeblcDeblc(rs.getLong("NUM_DEBLC_DEBLC"));
			blocageGod.setNumBlocBloc(rs.getLong("NUM_BLOC_BLOC"));
			deblocageGod.setBlocageGod(blocageGod);
			contratCptId.setCodStrcStrc(rs.getLong("COD_STRC_STRC"));
			contratCptId.setCodPrdPrd(rs.getLong("COD_PRD_PRD"));
			contratCptId.setNumCcptCcpt(rs.getLong("NUM_CCPT_CCPT"));
			contratCpt.setContratCptId(contratCptId);
			contratCpt.setNomIntiCcpt(rs.getString("NOM_INTI_CCPT"));
			deblocageGod.setContratCpt(contratCpt);
			deblocageGod.setNumMatrUser(rs.getString("NUM_MATR_USER"));
			deblocageGod.setTypDeblcDeblc(rs.getString("TYP_DEBLC_DEBLC"));
			deblocageGod.setMntDeblcDeblc(rs.getLong("MNT_DEBLC_DEBLC"));
			deblocageGod.setMotifDeblcDeblc(rs.getString("MOTIF_DEBLC_DEBLC"));
			deblocageGod.setDateExecDeblc(rs.getDate("DATE_EXEC_DEBLC"));
			deblocageGod.setEtatDblocDbloc(rs.getLong("ETAT_DBLOC_DBLOC"));
			deblocageGod.setMntDeblcDev(rs.getLong("MNT_DEBLC_DEV"));

			return deblocageGod;

		}

	}

	/**
	 * 
	 * @return BlocageGodVo
	 */

	@SuppressWarnings("unchecked")
	public IValueObject getListeBlocageGodClt(IValueObject vo) {
		BlocageGodVo blocageGodVo = (BlocageGodVo) vo;

		List<BlocageGod> listeBlocClt = new ArrayList<BlocageGod>();

		jt = new JdbcTemplate(dataSource);
		String rq =
				"select bloc.NUM_BLOC_BLOC,bloc.NUM_OPER_OMP,bloc.COD_STRC_STRC,bloc.COD_PRD_PRD,bloc.NUM_CCPT_CCPT,bloc.NUM_MATR_USER,"
						+ " bloc.MTF_BLOC,bloc.OBJ_BLOC,bloc.DATE_EXEC_BLOC,bloc.MNT_BLOC_BLOC,bloc.ETAT_BLOC_BLOC,bloc.MNT_BLOC_DEV, "
						+ " bloc.TYPE_CCPT_CCPT,bloc.MONT_PAOF_PAOF,bloc.MNT_UTL_DIN_BLOC,bloc.MNT_UTL_DEV_BLOC ,cpt.nom_inti_ccpt "
						+ " from blocage_god bloc,contrat_cpt cpt "
						+ " where  bloc.cod_strc_strc = cpt.cod_strc_strc and bloc.cod_prd_prd = cpt.cod_prd_prd "
						+ " and bloc.num_ccpt_ccpt = cpt.num_ccpt_ccpt  and bloc.cod_strc_strc = "
						+ blocageGodVo.getParamAgence().getCodStrcStrc()
						+ " and bloc.cod_prd_prd ="
						+ blocageGodVo.getContratCptRech().getContratCptId().getCodPrdPrd()
						+ " and bloc.num_ccpt_ccpt = "
						+ blocageGodVo.getContratCptRech().getContratCptId().getNumCcptCcpt();

		if (blocageGodVo.getEtatBlocage() != null) {
			rq += " and ETAT_BLOC_BLOC = 1";
		}
		jt.execute(rq);
		listeBlocClt = jt.query(rq, new BlocageGodRowMapper());
		blocageGodVo.setListeBlocageGodClt(listeBlocClt);
		return blocageGodVo;
	}

	public Long getSequenceBlocageGOD() {

		jt = new JdbcTemplate(dataSource);
		Long numeroSequence = (Long) jt.queryForObject("select SEQ_BLOCAGE_GOD.NEXTVAL from dual ", Long.class);
		return numeroSequence;
	}

	public Long getSequenceDeblocageGOD() {

		jt = new JdbcTemplate(dataSource);
		Long numeroSequence = (Long) jt.queryForObject("select SEQ_DEBLOCAGE_GOD.NEXTVAL from dual ", Long.class);
		return numeroSequence;
	}

	public Long getSequenceTraceOperGOD() {

		jt = new JdbcTemplate(dataSource);
		Long numeroSequence = (Long) jt.queryForObject("select SEQ_TRACE_OPER_GOD.NEXTVAL from dual ", Long.class);
		return numeroSequence;
	}

	// *************************************//
	public String getSqlQuery() {
		return sqlQuery;
	}

	public void setSqlQuery(String sqlQuery) {
		this.sqlQuery = sqlQuery;
	}

	public JdbcTemplate getJt() {
		return jt;
	}

	public void setJt(JdbcTemplate jt) {
		this.jt = jt;
	}

	public DataSource getDataSource() {
		return dataSource;
	}

	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}

}
