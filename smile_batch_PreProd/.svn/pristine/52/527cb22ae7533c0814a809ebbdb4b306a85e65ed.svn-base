package com.bna.smile.model.banqueAssurance.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import com.bna.commun.model.AdhesionAssVie;
import com.bna.commun.model.Assurances;
import com.bna.commun.model.ContratCpt;
import com.bna.commun.model.ContratCptId;
import com.bna.commun.model.DetailAdhesion;
import com.bna.commun.model.DetailAssuranceVoyage;
import com.bna.commun.model.Structure;
import com.bna.smile.model.banqueAssurance.vo.ContratAssuranceVo;
import com.oxia.fwk.context.Context;
import com.oxia.fwk.searchengine.SearchEngine;

public class AssuranceVoyageDAO {

	Context context;
	SearchEngine searchEngine;

	public AssuranceVoyageDAO() {

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

	public Long getSequenceContratAssuranceVoyage() {
		jt = new JdbcTemplate(dataSource);
		Long numeroSequence =
				(Long) jt.queryForObject("select SEQ_NUM_CONTRAT_ASS_VOYAGE.NEXTVAL from dual ", Long.class);
		return numeroSequence;
	}

	public Date getDateComptable() {
		jt = new JdbcTemplate(dataSource);
		Date dateComptable =
				(Date) jt.queryForObject("select max(DAT_JRN_JRN) from journee_structure_domaine ", Date.class);
		return dateComptable;
	}

	public Long getSequenceTraceAssurVoyage() {
		jt = new JdbcTemplate(dataSource);
		Long numeroSequence =
				(Long) jt.queryForObject("select SEQ_TRACE_ASSURANCE_VOYAGE.NEXTVAL from dual ", Long.class);
		return numeroSequence;
	}

	public Long getSequenceDetailContratAssuranceVoyage() {
		jt = new JdbcTemplate(dataSource);
		Long numeroSequence =
				(Long) jt.queryForObject("select SEQ_DETAIL_CONTRAT_ASS_VOYAGE.NEXTVAL from dual ", Long.class);
		return numeroSequence;
	}

	public String getDateComptableByStructure(Structure str) {
		jt = new JdbcTemplate(dataSource);

		String requete =
				"select to_char(J.DAT_JRN_JRN,'DD/MM/YYYY')" + " from JOURNEE_STRUCTURE_DOMAINE J, STRUCTURE STR "
						+ " where  J.COD_DOM_DOMM = 13  and (J.COD_STAT_JSD = 0 or J.COD_STAT_JSD = 3)"
						+ " and DAT_JRN_JRN in (select max(I.DAT_JRN_JRN) from JOURNEE_STRUCTURE_DOMAINE I  "
						+ " where I.COD_STRC_STRC =" + str.getCodStrcStrc() + " and I.COD_DOM_DOMM = 13)  "
						+ " and STR.COD_STRC_STRC =  " + str.getCodStrcStrc() + " and STR.COD_TSTR_TSTR = 1 ";

		// System.out.println(requete);
		String dateComptable = (String) jt.queryForObject(requete, String.class);
		System.out.println("dateComptable" + dateComptable);
		return dateComptable;

	}

	public List<DetailAssuranceVoyage> getlisteFamille(String numCrtAssVoyage) {
		jt = new JdbcTemplate(dataSource);
		String req = "select dassv.* from CONTRAT_ASSURANCE_VOYAGE ctrassv"
				+ " inner join DETAIL_ASSURANCE_VOYAGE dassv" + " on dassv.NUM_CRT_DASSV = ctrassv.NUM_CRT_CASSV"
				+ " where ctrassv.num_crt_cassv =" + numCrtAssVoyage;

		@SuppressWarnings("unchecked")
		List<DetailAssuranceVoyage> list = jt.query(req, new RowMapper() {

			public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
				DetailAssuranceVoyage detailAssVoyage = new DetailAssuranceVoyage();
				detailAssVoyage.setNomBenfDassv(rs.getString("NOM_BENF_DASSV"));
				detailAssVoyage.setPrnBenfDassv(rs.getString("PRN_BENF_DASSV"));
				detailAssVoyage.setDateNaisDassv(rs.getDate("DATE_NAIS_DASSV"));
				detailAssVoyage.setNumPasseportDassv(rs.getString("NUM_PASSEPORT_DASSV"));
				detailAssVoyage.setNumPasscrtDassv(rs.getString("NUM_PASSCRT_DASSV"));
				detailAssVoyage.setTypeBenfDassv(rs.getString("TYPE_BENF_DASSV"));
				return detailAssVoyage;
			}
		});
		return list;
	}

	public List<DetailAdhesion> getlisteDetailAdhesionVie(Date dateDebut, Date dateFin, Long codeAssurance,
			String etat) {
		jt = new JdbcTemplate(dataSource);
		SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
		List<DetailAdhesion> list = new ArrayList<DetailAdhesion>();
		try {
			String req = "select det.* from Detail_Adhesion det, Adhesion_Ass_Vie adh "
					+ " where Det.Num_Seq_Adh=Adh.Num_Seq_Adh and Adh.Cod_Ass_Ass=" + codeAssurance
					+ " and DAT_FIN_DADH Between '" + format.format(dateDebut) + "' and '" + format.format(dateFin)
					+ "' and COD_ETAT_DADH='" + etat + "' ";

			System.out.println("req getlisteDetailAdhesionVie : "+req);
			list = jt.query(req, new RowMapper() {

				public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
					DetailAdhesion detailAdhesion = new DetailAdhesion();
					detailAdhesion.setNumSeqDadh(rs.getLong("NUM_SEQ_DADH"));
					ContratCpt cpt = new ContratCpt();
					ContratCptId cptId = new ContratCptId();
					cptId.setCodStrcStrc(rs.getLong("COD_STRC_STRC"));
					cptId.setCodPrdPrd(rs.getLong("COD_PRD_PRD"));
					cptId.setNumCcptCcpt(rs.getLong("NUM_CCPT_CCPT"));
					cpt.setContratCptId(cptId);
					detailAdhesion.setContratCpt(cpt);
					AdhesionAssVie adhesionAssVie = new AdhesionAssVie();
					adhesionAssVie.setNumSeqAdh(rs.getLong("NUM_SEQ_ADH"));
					detailAdhesion.setAdhesionAssVie(adhesionAssVie);
					detailAdhesion.setDatDebDadh(rs.getDate("DAT_DEB_DADH"));
					detailAdhesion.setDatFinDadh(rs.getDate("DAT_FIN_DADH"));
					detailAdhesion.setCodEtatDadh(rs.getString("COD_ETAT_DADH"));

					return detailAdhesion;
				}
			});

		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	public List<ContratAssuranceVo> getVueContratAssurance(Long codAss, String date) {
		jt = new JdbcTemplate(dataSource);
		String req = "SELECT TO_CHAR(opm.dat_syst_omp, 'ddmmyyyy') date_jour ,\r\n"
				+ "    lpad(cass.cod_strc_strc,3,'0') code_agenc,\r\n"
				+ "    lpad(cass.num_seq_cass,5,'0') numAdhesion,\r\n"
				+ "    lpad(cass.num_att_cass,6,'0') quittance,\r\n"
				+ "    TO_CHAR(cass.dat_cred_cass, 'ddmmyyyy') dateEffet,\r\n"
				+ "    TO_CHAR(cass.dat_ech_cass, 'ddmmyyyy') datePremEcheance,\r\n"
				+ "    lpad(DECODE(cass.per_gra_cass,0,0,cass.per_gra_cass+1),3,'0') franchise,\r\n"
				+ "    rpad(upper(trim(nvl(p.nom_nom_pers,' '))),20,' ')\r\n"
				+ "	 || rpad(upper(trim(nvl(p.nom_prn_pers,' '))),20,' ') assure,\r\n"
				+ "    DECODE(trim(p.adr_rrue_pers),NULL,'                               ',rpad(upper(trim(p.adr_rrue_pers)) ,31,' '))\r\n"
				+ "    || DECODE(trim(p.lib_nais_pers),NULL,'                    ',rpad(upper(trim(p.lib_nais_pers)),20,' '))\r\n"
				+ "    || DECODE(trim(p.adr_rcp_pers),NULL,lpad('0' ,5,'0'),lpad(trim(p.adr_rcp_pers) ,5,'0')) adresse,\r\n"
				+ "    lpad(trim(p.num_pce_pers),10,'0') CIN,\r\n" + "    '03'\r\n"
				+ "    ||lpad(s.cod_bct_strc,3,'0')\r\n" + "    ||lpad(cass.cod_strc_strc,3,'0')\r\n"
				+ "    ||lpad(cass.cod_prd_prd,4,'0')\r\n" + "    ||lpad(cass.num_ccpt_ccpt,6,'0')\r\n"
				+ "    || DECODE(LENGTH(97 - mod('03'\r\n" + "    ||lpad(s.cod_bct_strc,3,'0')\r\n"
				+ "    ||lpad(cass.cod_strc_strc,3,'0')\r\n" + "    ||lpad(cass.cod_prd_prd,4,'0')\r\n"
				+ "    ||lpad(cass.num_ccpt_ccpt,6,'0')\r\n" + "    || '00' ,97)),1,'0'\r\n"
				+ "    ||(97 - mod('03'\r\n" + "    ||lpad(s.cod_bct_strc,3,'0')\r\n"
				+ "    ||lpad(cass.cod_strc_strc,3,'0')\r\n" + "    ||lpad(cass.cod_prd_prd,4,'0')\r\n"
				+ "    ||lpad(cass.num_ccpt_ccpt,6,'0')\r\n" + "    || '00' ,97)),97 - mod('03'\r\n"
				+ "    ||lpad(s.cod_bct_strc,3,'0')\r\n" + "    ||lpad(cass.cod_strc_strc,3,'0')\r\n"
				+ "    ||lpad(cass.cod_prd_prd,4,'0')\r\n" + "    ||lpad(cass.num_ccpt_ccpt,6,'0')\r\n"
				+ "    || '00' ,97)) AS rib ,\r\n" + "    rpad( upper(trim(\r\n"
				+ "    (SELECT lib_prof_prof FROM profession WHERE cod_prof_prof= p.cod_prof_prof\r\n"
				+ "    ))),15,' ') profession,\r\n" + "    TO_CHAR(p.dat_nais_pers, 'ddmmyyyy') dateNaissance,\r\n"
				+ "    lpad(DECODE(cass.mnt_ancr_cass,NULL,0,cass.mnt_ancr_cass),9,'0') capital ,\r\n"
				+ "    lpad(cass.mnt_cred_cass,9,'0') capitalenc,\r\n" + "    lpad(\r\n"
				+ "    (SELECT tarif_assurance.taux_cred_tass/10\r\n" + "    FROM tarif_assurance\r\n"
				+ "    WHERE cass.cod_tass_tass=tarif_assurance.cod_tass_tass\r\n" + "    ),4,'0') tauxPrime,\r\n"
				+ "    lpad(cass.taux_sprim_cass,4,'0') tauxsprime,\r\n"
				+ "    lpad(cass.mnt_prcom_cass,7,'0') mntPrimeComm,\r\n"
				+ "    lpad(cass.mnt_prbna_nett_cass,7,'0') mntPrimeBNA,\r\n"
				+ "    lpad(cass.mnt_dret_cass,6,'0') mntRetenu,\r\n"
				+ "    DECODE(cass.rep_badh_cass,0,'N',1,'O',' ') reponse,\r\n"
				+ "    lpad(cass.cod_emed_emed,2,'0') examen,\r\n"
				+ "    lpad(cass.mnt_fmed_cass,6,'0') honoraires,\r\n" + "    lpad(cass.dcre_clt_cass,3,'0') duree,\r\n"
				+ "    '000' tpx,\r\n" + "    TO_CHAR(cass.cod_ass_ass) assurance\r\n"
				+ "  FROM contrat_assurance cass,\r\n" + "    contrat_cpt cpt,\r\n" + "    personne p,\r\n"
				+ "    structure s,\r\n" + "    operation_moy_pay opm\r\n"
				+ "  WHERE s.cod_strc_strc                        =cass.cod_strc_strc\r\n"
				+ "  AND opm.num_oper_omp                         =cass.num_oper_omp\r\n"
				+ "  AND cass.cod_strc_strc                       =cpt.cod_strc_strc\r\n"
				+ "  AND cass.cod_prd_prd                         =cpt.cod_prd_prd\r\n"
				+ "  AND cass.num_ccpt_ccpt                       =cpt.num_ccpt_ccpt\r\n"
				+ "  AND p.num_seq_pers                           =cpt.num_seq_pers\r\n"
				+ "  AND TO_CHAR(cass.dat_ope_val_cass, 'dd/mm/yyyy')='" + date + "'\r\n" + "  AND cass.cod_ass_ass="
				+ codAss + "\r\n"
				+ " -- AND TO_CHAR(cass.dat_ope_val_cass, 'dd/mm/yyyy')=TO_CHAR('25/04/2019', 'dd/mm/yyyy')\r\n"
				+ "  AND cass.cod_etat_cass                       ='V' order by cass.dat_ope_val_cass , cass.num_seq_cass";
		System.out.println(req);
		@SuppressWarnings("unchecked")
		List<ContratAssuranceVo> list = jt.query(req, new RowMapper() {

			public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
				ContratAssuranceVo contratAssuranceVo = new ContratAssuranceVo();
				contratAssuranceVo.setAdresse(rs.getString("adresse"));
				contratAssuranceVo.setAssurance(rs.getString("assurance"));
				contratAssuranceVo.setAssure(rs.getString("assure"));
				contratAssuranceVo.setCapital(rs.getString("capital"));
				contratAssuranceVo.setCapitalEnc(rs.getString("capitalenc"));
				contratAssuranceVo.setCin(rs.getString("CIN"));
				contratAssuranceVo.setCodAgence(rs.getString("code_agenc"));
				contratAssuranceVo.setDateEffet(rs.getString("dateEffet"));
				contratAssuranceVo.setDateJour(rs.getString("date_jour"));
				contratAssuranceVo.setDateNaissance(rs.getString("dateNaissance"));
				contratAssuranceVo.setDatePremiereEcheance(rs.getString("datePremEcheance"));
				contratAssuranceVo.setDuree(rs.getString("duree"));
				contratAssuranceVo.setExamen(rs.getString("examen"));
				contratAssuranceVo.setFranchise(rs.getString("franchise"));
				contratAssuranceVo.setHonoraires(rs.getString("honoraires"));
				contratAssuranceVo.setMntPrimeBNA(rs.getString("mntPrimeBNA"));
				contratAssuranceVo.setMntPrimeComm(rs.getString("mntPrimeComm"));
				contratAssuranceVo.setMntRetenu(rs.getString("mntRetenu"));
				contratAssuranceVo.setNumAdhesion(rs.getString("numAdhesion"));
				contratAssuranceVo.setProfession(rs.getString("profession"));
				contratAssuranceVo.setQuittance(rs.getString("quittance"));
				contratAssuranceVo.setReponse(rs.getString("reponse"));
				contratAssuranceVo.setRib(rs.getString("rib"));
				contratAssuranceVo.setTauxPrime(rs.getString("tauxPrime"));
				contratAssuranceVo.setTauxSprime(rs.getString("tauxsprime"));
				contratAssuranceVo.setTpx(rs.getString("tpx"));

				return contratAssuranceVo;
			}
		});
		return list;
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

}
