package com.bna.smile.model.domainecommun.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.sql.DataSource;

import org.apache.log4j.Logger;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import com.bna.smile.model.domainecommun.model.AFBMvt;
import com.bna.smile.model.domainecommun.model.AFBView;
import com.bna.smile.model.domainecommun.model.ListeRIBSocietesAFBView;
import com.bna.smile.model.domainecommun.model.SocietesAFBView;

public class AFBDAO {

	protected String sqlQuery;
	protected JdbcTemplate jt;
	protected DataSource dataSource;
	private SimpleDateFormat formaterDate = new SimpleDateFormat("dd/MM/yyyy");
	private static final Logger logger = Logger.getLogger(AFBDAO.class);

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

	public List<AFBView> getListeOperationsMoyenPayByCriteres(Long codStrcCcpt, Long codPrdCcpt, Long numCcptCcpt,
			Date dateJour) {
		jt = new JdbcTemplate(dataSource);
		SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
		String requete =
				"select oper.num_oper_omp,to_char(oper.dat_oper_omp,'ddMMyy') as dat_oper_omp ,to_char(oper.dat_val_omp,'ddMMyy') as dat_val_omp ,lpad(oper.cod_oper_oper,4,'0') as cod_oper_oper ,op.lib_cour_oper,oper.cod_refb_omp, lpad(oper.mont_din_omp,13,'0') as mont_din_omp,"
						+ " oper.MONT_SOLD_CCPT, oper.MONT_APRE_OMP,oper.COD_SENS_OMP,DAT_TIME_OMP,oper.cod_etat_omp,"
						+ " oper.dat_syst_omp, (select  nvl(sum(MONT_VAL_DOMP),0)  from DETAIL_OPER_MOY_PAIEMENT   "
						+ " where NUM_OPER_OMP=oper.NUM_OPER_OMP  ) as  v_SOMME_COMM,"
						+ "(select   to_char(max(dat_val_domp),'ddMMyy')  from DETAIL_OPER_MOY_PAIEMENT  where NUM_OPER_OMP=oper.NUM_OPER_OMP  ) as  dat_val_domp, "
						+ "  nvl(oper.mont_tva_omp,0) as mont_tva_omp "
						+ " from operation_moy_pay oper , operation op where   oper.cod_etat_omp='V'  and  "
						+ " dat_syst_omp >='" + format.format(dateJour) + "' and dat_syst_omp < '"
						+ format.format(new Date()) + "' " + " and cod_strc_strc= " + codStrcCcpt
						+ " and oper.NUM_CCPT_CCPT= " + numCcptCcpt + " and oper.cod_prd_prd=" + codPrdCcpt
						+ " and op.cod_oper_oper=oper.cod_oper_oper order by  DAT_TIME_OMP,num_oper_omp asc";

		List<AFBView> list = jt.query(requete, new RowMapper() {

			public Object mapRow(ResultSet rs, int rowNum) throws SQLException {

				AFBView AFBView = new AFBView();
				AFBView.setNumOperOmp(rs.getString("NUM_OPER_OMP"));
				AFBView.setCodOperation4(rs.getString("COD_OPER_OPER"));
				// AFBView.setCodOperation2(rs.getString("COMPTE"));
				AFBView.setLibOperation(rs.getString("LIB_COUR_OPER"));
				AFBView.setMontantOperation(rs.getString("MONT_DIN_OMP"));
				AFBView.setDateOperation(rs.getString("DAT_OPER_OMP"));
				AFBView.setDateValeur(rs.getString("DAT_VAL_OMP"));
				AFBView.setRefOperation(rs.getString("COD_REFB_OMP"));
				AFBView.setSoldeDeprt(rs.getLong("MONT_SOLD_CCPT"));
				AFBView.setSoldeApresOMP(rs.getLong("MONT_APRE_OMP"));
				AFBView.setMontantTVA(rs.getLong("MONT_TVA_OMP"));
				AFBView.setMontantCommission(rs.getLong("v_SOMME_COMM"));
				AFBView.setCodSensOmp(rs.getString("COD_SENS_OMP"));
				AFBView.setDatValDomp(rs.getString("DAT_VAL_DOMP"));
				return AFBView;
			}
		});
		return list;
	}

	public List<ListeRIBSocietesAFBView> getListeRibAFBByCriteres(Long numSoctAfb) {
		jt = new JdbcTemplate(dataSource);

		SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
		String requete =
				" select num_soct_afb,rib_soct_afb,cod_strc_rib from LISTE_RIB_SOCIETE_AFB where num_soct_afb ="
						+ numSoctAfb
						+ "  and cod_strc_rib in (select cod_strc_strc from agence_pilote) order by rib_soct_afb";

		List<ListeRIBSocietesAFBView> list = jt.query(requete, new RowMapper() {

			public Object mapRow(ResultSet rs, int rowNum) throws SQLException {

				ListeRIBSocietesAFBView listeRIBSocietesAFBView = new ListeRIBSocietesAFBView();

				listeRIBSocietesAFBView.setNumSoctAFB(rs.getLong("num_soct_afb"));
				listeRIBSocietesAFBView.setCodStrcStrc(rs.getLong("cod_strc_rib"));
				listeRIBSocietesAFBView.setRibSoctAFB(rs.getString("rib_soct_afb"));

				return listeRIBSocietesAFBView;
			}
		});
		return list;
	}

	public List<SocietesAFBView> getListeSocietesAFBView(Long num_soct_afb) {
		jt = new JdbcTemplate(dataSource);
		SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
		String requete =
				" select distinct(listribafb.num_soct_afb),listeafb.nom_soct_afb,listeafb.EMAIL_SOCT_AFB from LISTE_RIB_SOCIETE_AFB listRibAFB,LISTE_SOCIETE_AFB listeAFB  "
						+ " where listribafb.num_soct_afb = listeafb.num_soct_afb   ";
		if (num_soct_afb != null && num_soct_afb.longValue() != 0 && num_soct_afb.longValue() != 99) {
			requete += " and listribafb.num_soct_afb in (" + num_soct_afb + ") ";
		} else {
			requete += " and listribafb.num_soct_afb in (99,999) ";
		}
		requete += " order by listribafb.num_soct_afb ";

		List<SocietesAFBView> list = jt.query(requete, new RowMapper() {

			public Object mapRow(ResultSet rs, int rowNum) throws SQLException {

				SocietesAFBView societesAFBView = new SocietesAFBView();

				societesAFBView.setNumSoctAFB(rs.getLong("num_soct_afb"));
				societesAFBView.setNomSoctAFB(rs.getString("nom_soct_afb"));
				societesAFBView.setEmailSoctAFB(rs.getString("EMAIL_SOCT_AFB"));
				return societesAFBView;
			}
		});
		return list;
	}

	public List<AFBMvt> getListeMvtsByCriteres(String compte) {
		jt = new JdbcTemplate(dataSource);
		SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
		String requete =
				"select NUM_COMPTE,DATE_OPERATION,CODE_OPERATION,CODE_SE,CODE_TERME,MVTS_DEBITEURS,MVTS_CREDITEURS,"
						+ " DATE_VALEUR,REFERENCE,LIBELLE,LIB from mvt_appitessante" + " where NUM_COMPTE='" + compte
						+ "'   order by to_date(DATE_OPERATION,'ddMMyyyy')";

		List<AFBMvt> list = jt.query(requete, new RowMapper() {

			public Object mapRow(ResultSet rs, int rowNum) throws SQLException {

				AFBMvt AFBMvt = new AFBMvt();
				AFBMvt.setNUM_COMPTE(rs.getString("NUM_COMPTE"));
				AFBMvt.setDATE_OPERATION(rs.getString("DATE_OPERATION"));
				AFBMvt.setCODE_OPERATION(rs.getString("CODE_OPERATION"));
				AFBMvt.setCODE_SE(rs.getString("CODE_SE"));
				AFBMvt.setCODE_TERME(rs.getString("CODE_TERME"));
				AFBMvt.setMVTS_DEBITEURS(rs.getString("MVTS_DEBITEURS"));
				AFBMvt.setMVTS_CREDITEURS(rs.getString("MVTS_CREDITEURS"));
				AFBMvt.setDATE_VALEUR(rs.getString("DATE_VALEUR"));
				AFBMvt.setREFERENCE(rs.getString("REFERENCE"));
				AFBMvt.setLIBELLE(rs.getString("LIBELLE"));
				AFBMvt.setLIB(rs.getString("LIB"));
				return AFBMvt;
			}
		});
		return list;
	}

	public List<AFBView> getListeOperationsMoyenPayByPeriode(Long codStrcCcpt, Long codPrdCcpt, Long numCcptCcpt,
			Date dateDebut, Date dateFin) {
		jt = new JdbcTemplate(dataSource);
		SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
		String requete =
				"select oper.num_oper_omp,to_char(oper.dat_oper_omp,'ddMMyy') as dat_oper_omp ,to_char(oper.dat_val_omp,'ddMMyy') as dat_val_omp ,lpad(oper.cod_oper_oper,4,'0') as cod_oper_oper ,op.lib_cour_oper,oper.cod_refb_omp, "
						+ " (nvl(NOM_NOMD_OMP,'') || nvl(NOM_PRND_OMP,''))  as donneur, lpad(oper.mont_din_omp,13,'0') as mont_din_omp,"
						+ " oper.MONT_SOLD_CCPT, oper.MONT_APRE_OMP,oper.COD_SENS_OMP,DAT_TIME_OMP,oper.cod_etat_omp,"
						+ " oper.dat_syst_omp, (select  nvl(sum(MONT_VAL_DOMP),0)  from DETAIL_OPER_MOY_PAIEMENT   "
						+ " where NUM_OPER_OMP=oper.NUM_OPER_OMP  ) as  v_SOMME_COMM,"
						+ "(select   to_char(max(dat_val_domp),'ddMMyy')  from DETAIL_OPER_MOY_PAIEMENT  where NUM_OPER_OMP=oper.NUM_OPER_OMP  ) as  dat_val_domp, "
						+ "  nvl(oper.mont_tva_omp,0) as mont_tva_omp ,oper.lib_motf_omp ,lpad(nvl(afb.cod_oper_afb,33),2,'0') as cod_oper_afb "
						+ " from operation_moy_pay oper , operation op ,refcptable.codes_operations_afb afb where "
						+ " oper.cod_oper_oper=afb.cod_oper_BNA(+)  and    oper.cod_etat_omp='V'  and  "
						+ " trunc(DAT_TIME_OMP)  >='" + format.format(dateDebut) + "' and  trunc(DAT_TIME_OMP) < '"
						+ format.format(dateFin) + "' " + " and cod_strc_strc= " + codStrcCcpt
						+ " and oper.NUM_CCPT_CCPT= " + numCcptCcpt + " and oper.cod_prd_prd=" + codPrdCcpt
						+ " and op.cod_oper_oper=oper.cod_oper_oper order by  DAT_TIME_OMP,num_oper_omp asc";

		List<AFBView> list = jt.query(requete, new RowMapper() {

			public Object mapRow(ResultSet rs, int rowNum) throws SQLException {

				AFBView AFBView = new AFBView();
				AFBView.setNumOperOmp(rs.getString("NUM_OPER_OMP"));
				AFBView.setCodOperation4(rs.getString("COD_OPER_OPER"));
				AFBView.setCodOperation2(rs.getString("COD_OPER_AFB"));
				AFBView.setLibOperation(rs.getString("LIB_COUR_OPER"));
				AFBView.setMontantOperation(rs.getString("MONT_DIN_OMP"));
				AFBView.setDateOperation(rs.getString("DAT_OPER_OMP"));
				AFBView.setDateValeur(rs.getString("DAT_VAL_OMP"));
				AFBView.setRefOperation(rs.getString("COD_REFB_OMP"));
				AFBView.setSoldeDeprt(rs.getLong("MONT_SOLD_CCPT"));
				AFBView.setSoldeApresOMP(rs.getLong("MONT_APRE_OMP"));
				AFBView.setMontantTVA(rs.getLong("MONT_TVA_OMP"));
				AFBView.setMontantCommission(rs.getLong("v_SOMME_COMM"));
				AFBView.setCodSensOmp(rs.getString("COD_SENS_OMP"));
				AFBView.setDatValDomp(rs.getString("DAT_VAL_DOMP"));
				AFBView.setDonneurDordre(rs.getString("DONNEUR"));
				AFBView.setLibMotfOmp(rs.getString("LIB_MOTF_OMP"));
				return AFBView;
			}
		});
		return list;
	}

	public List<AFBView> getListeOperationsMoyenPayByPeriodeDevise(Long codStrcCcpt, Long codPrdCcpt, Long numCcptCcpt,
			Date dateDebut, Date dateFin) {
		jt = new JdbcTemplate(dataSource);
		SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
		String requete =
				"select oper.num_oper_omp,to_char(oper.dat_oper_omp,'ddMMyy') as dat_oper_omp ,to_char(oper.dat_val_omp,'ddMMyy') as dat_val_omp ,"
						+ " lpad(oper.cod_oper_oper,4,'0') as cod_oper_oper ,op.lib_cour_oper,oper.cod_refb_omp,  "
						+ " (nvl(NOM_NOMD_OMP,'') || nvl(NOM_PRND_OMP,''))  as donneur,  lpad(oper.mont_dev_omp,13,'0') as mont_dev_omp, "
						+ " oper.mont_sdev_ccpt, oper.MONTD_APRE_OMP,oper.COD_SENS_OMP,DAT_TIME_OMP,oper.cod_etat_omp, oper.dat_syst_omp, "
						+ " (select  nvl(sum(MONT_VALD_DOMP),0)  from DETAIL_OPER_MOY_PAIEMENT   where NUM_OPER_OMP=oper.NUM_OPER_OMP  ) as  v_SOMME_COMM,"
						+ " (select   to_char(max(dat_val_domp),'ddMMyy')  from DETAIL_OPER_MOY_PAIEMENT  where NUM_OPER_OMP=oper.NUM_OPER_OMP  ) as  dat_val_domp,"
						+ "  nvl(oper.mont_tvad_omp,0) as mont_tvad_omp ,oper.lib_motf_omp,lpad(nvl(afb.cod_oper_afb,33),2,'0') as cod_oper_afb "
						+ "  from operation_moy_pay oper , operation op ,refcptable.codes_operations_afb afb where "
						+ " oper.cod_oper_oper=afb.cod_oper_BNA(+)  and    oper.cod_etat_omp='V'  and   "
						+ " trunc(DAT_TIME_OMP)  >='" + format.format(dateDebut) + "' and  trunc(DAT_TIME_OMP) < '"
						+ format.format(dateFin) + "' " + " and cod_strc_strc= " + codStrcCcpt
						+ " and oper.NUM_CCPT_CCPT= " + numCcptCcpt + " and oper.cod_prd_prd=" + codPrdCcpt
						+ " and op.cod_oper_oper=oper.cod_oper_oper order by  DAT_TIME_OMP,num_oper_omp asc";

		List<AFBView> list = jt.query(requete, new RowMapper() {

			public Object mapRow(ResultSet rs, int rowNum) throws SQLException {

				AFBView AFBView = new AFBView();
				AFBView.setNumOperOmp(rs.getString("NUM_OPER_OMP"));
				AFBView.setCodOperation4(rs.getString("COD_OPER_OPER"));
				AFBView.setCodOperation2(rs.getString("COD_OPER_AFB"));
				AFBView.setLibOperation(rs.getString("LIB_COUR_OPER"));
				AFBView.setMontantOperation(rs.getString("MONT_DEV_OMP"));
				AFBView.setDateOperation(rs.getString("DAT_OPER_OMP"));
				AFBView.setDateValeur(rs.getString("DAT_VAL_OMP"));
				AFBView.setRefOperation(rs.getString("COD_REFB_OMP"));
				AFBView.setSoldeDeprt(rs.getLong("MONT_SDEV_CCPT"));
				AFBView.setSoldeApresOMP(rs.getLong("MONTD_APRE_OMP"));
				AFBView.setMontantTVA(rs.getLong("MONT_TVAD_OMP"));
				AFBView.setMontantCommission(rs.getLong("v_SOMME_COMM"));
				AFBView.setCodSensOmp(rs.getString("COD_SENS_OMP"));
				AFBView.setDatValDomp(rs.getString("DAT_VAL_DOMP"));
				AFBView.setDonneurDordre(rs.getString("DONNEUR"));
				AFBView.setLibMotfOmp(rs.getString("LIB_MOTF_OMP"));
				return AFBView;
			}
		});
		return list;
	}

	public Date getDateFichier() {

		jt = new JdbcTemplate(dataSource);
		Date dateAgence = (Date) jt.queryForObject(
				"select max(dat_jrn_jrn) from journee_structure" + " where cod_strc_strc=120 and cod_stat_jrn=1  ",
				Date.class);
		return dateAgence;
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