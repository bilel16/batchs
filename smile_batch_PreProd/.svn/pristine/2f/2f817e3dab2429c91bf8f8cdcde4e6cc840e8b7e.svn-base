package com.bna.smile.model.domaineguichet.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.sql.DataSource;

import org.apache.log4j.Logger;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import com.bna.commun.model.BatchMetier;
import com.bna.commun.model.BatchVirFileCpy;
import com.bna.commun.model.ContratCpt;
import com.bna.commun.model.ContratCptId;
import com.bna.commun.model.DetailOperMoyPaiement;
import com.bna.commun.model.Devise;
import com.bna.commun.model.JourneeStructureBatchId;
import com.bna.commun.model.OperationMoyPay;
import com.bna.commun.model.Tache;
import com.bna.commun.model.TacheId;
import com.bna.commun.model.TypePiece;
import com.bna.smile.model.constant.Constants;
import com.bna.smile.model.domaineguichet.model.AgencesMAJNSIVo;
import com.bna.smile.model.domaineguichet.model.MvtDevise;

public class GuichetDAO {

	public GuichetDAO() {
	}

	protected String sqlQuery;
	protected JdbcTemplate jt;
	protected DataSource dataSource;
	private static final Logger logger = Logger.getLogger(GuichetDAO.class);

	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	public void setSqlQuery(String sqlQuery) {
		this.sqlQuery = sqlQuery;
	}

	public Long getSequenceOperationMoyPay() {
		jt = new JdbcTemplate(dataSource);
		Long numeroSequence = (Long) jt.queryForObject("select SEQ_OPER_MOY_PAY.NEXTVAL from dual ", Long.class);
		return numeroSequence;
	}

	public void setSldJrn() {
		String requete =
				" UPDATE JOURNEE_STRUCTURE    SET COD_SOLD_JRN = 1    WHERE COD_STRC_STRC in (select COD_STRC_STRC from agence_nsi)      AND  DAT_JRN_JRN   = trunc(sysdate)";
		jt.execute(requete);
		requete = " commit ";
		jt.execute(requete);
	}

	public List getListAgencesMajNsi() {
		jt = new JdbcTemplate(dataSource);
		List<Long> listAgNsi = new ArrayList<Long>();
		String requete = "select btchstrc.cod_strc_strc from batch_structure btchstrc where  btchstrc.cod_bat_bmet="
				+ Constants.COD_BATCH_MAJNSI +" order by btchstrc.cod_strc_strc ";
		logger.info(requete);

		listAgNsi = jt.query(requete, new RowMapper() {

			public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
				Long strc = new Long(rs.getLong("cod_strc_strc"));
				return strc;
			}
		});
		logger.info("la requete MAJNSI Ag a ramené " + String.valueOf(listAgNsi.size()));

		return listAgNsi;

	}

	public List getListAgencesRejetNsi(Long Agence) {
		jt = new JdbcTemplate(dataSource);
		List<Long> listAgNsi = new ArrayList<Long>();
		String requete =
				"select DISTINCT COD_STRC_BATR from BATCH_REJET_VIR_NSI where COD_ETAT_BATR='A'";
		if(Agence!=null) {
			requete+=" and COD_STRC_BATR="+Agence;
			
		}
		requete+="  order by COD_STRC_BATR ";
		logger.info(requete);

		listAgNsi = jt.query(requete, new RowMapper() {

			public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
				Long strc = new Long(rs.getLong("COD_STRC_BATR"));
				return strc;
			}
		});
		logger.info("Nombre des agences : " + String.valueOf(listAgNsi.size()));

		return listAgNsi;

	}

	public void insertActel(String ligne) {
		String strc = ligne.trim().substring(0, 3);
		String prd = ligne.trim().substring(3, 7);
		String numccp = ligne.trim().substring(7, 13);
		String sens = ligne.trim().substring(43, 44);
		String mnt = ligne.trim().substring(32, 43);
		SimpleDateFormat formatter = new SimpleDateFormat("ddMMyy");
		SimpleDateFormat formatter2 = new SimpleDateFormat("dd/MM/yyyy");
		String dateCpt = "";
		try {
			dateCpt = formatter2.format(formatter.parse(ligne.trim().substring(17, 23)));
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (ligne.length() > 7) {
			jt = new JdbcTemplate(dataSource);
			jt.execute(" insert into TMP_MAJNSI_ACTEL (DATA_MAJ,STRC,PRD,CPT,SENS,MNT,DATEOP) values ('" + ligne + "',"
					+ strc + "," + prd + "," + numccp + ",'" + sens + "'," + mnt + ",'" + dateCpt + "')");
		}

	}

	public List getListContratCptGloabalActel(String cod_strc, final Date dateOper) {
		jt = new JdbcTemplate(dataSource);
		List<OperationMoyPay> listAgNsi = new ArrayList<OperationMoyPay>();
		String requete =
				"  select structure.LIB_STRC_STRC, pers.cod_tpce_tpce,pers.num_pce_pers,pers.nom_prn_pers,pers.nom_nom_pers ,CONTRAT_CPT.* from CONTRAT_CPT left join structure on CONTRAT_CPT.COD_STRC_STRC=structure.COD_STRC_STRC left join personne pers on CONTRAT_CPT.NUM_SEQ_PERS=pers.NUM_SEQ_PERS  where (CONTRAT_CPT.COD_STRC_STRC,COD_PRD_PRD,NUM_CCPT_CCPT) in (select COD_STRC_STRC,COD_PRD_PRD,NUM_CCPT_CCPT from CPT_GLOBAL_135 where COD_STRC_STRC= "
						+ cod_strc + " ) and  COD_ETAT_CCPT = 'V' and MONT_SOLD_CCPT > 0 ";
		logger.info(requete);

		listAgNsi = jt.query(requete, new RowMapper() {

			public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
				OperationMoyPay omp = new OperationMoyPay();
				ContratCpt cpt = new ContratCpt();
				ContratCptId cptId = new ContratCptId();
				cptId.setCodStrcStrc(rs.getLong("COD_STRC_STRC"));
				cptId.setCodPrdPrd(rs.getLong("COD_PRD_PRD"));
				cptId.setNumCcptCcpt(rs.getLong("NUM_CCPT_CCPT"));
				cpt.setContratCptId(cptId);
				omp.setContratCpt(cpt);
				omp.setMontDinOmp(rs.getLong("MONT_SOLD_CCPT"));
				omp.setDatValOmp(dateOper);
				omp.setDatOperOmp(dateOper);
				omp.setCodSensOmp("C");

				omp.setDatTimeOmp(new Date());
				Devise devise = new Devise();
				devise.setCodDevDev(rs.getLong("COD_DEV_DEV"));
				omp.setDevise(devise);
				omp.setCodRefbOmp(rs.getString("LIB_STRC_STRC"));
				TypePiece typePieceDem = new TypePiece();
				typePieceDem.setCodTpceTpce(rs.getLong("cod_tpce_tpce"));
				omp.setTypePieceDemandeur(typePieceDem);
				omp.setNumPcedOmp(rs.getString("num_pce_pers"));
				omp.setNomNomdOmp(rs.getString("nom_nom_pers"));
				omp.setNomPrndOmp(rs.getString("nom_prn_pers"));
				omp.setCodDemOmp("TR");

				ContratCptId cptIdRat = new ContratCptId();
				cptIdRat.setCodStrcStrc(rs.getLong("COD_RAT_STRC"));
				cptIdRat.setCodPrdPrd(rs.getLong("COD_RAT_PRD"));
				cptIdRat.setNumCcptCcpt(rs.getLong("NUM_RAT_CCPT"));
				ContratCpt cptRat = new ContratCpt();
				cptRat.setContratCptId(cptIdRat);
				omp.getContratCpt().setContratCpt(cptRat);

				return omp;

			}
		});
		logger.info("la requete MAJNSIActelOmp Ag a ramené " + String.valueOf(listAgNsi.size()));
		return listAgNsi;

	}

	public List getListOperMoyPayActel(String cod_strc) {
		jt = new JdbcTemplate(dataSource);
		List<OperationMoyPay> listAgNsi = new ArrayList<OperationMoyPay>();

		jt.execute(" delete from temp_table_operation_moy_pay ");
		jt.execute(" insert into temp_table_operation_moy_pay "
				+ " select omp.num_oper_omp  from operation_moy_pay  omp"
				+ " left join detail_oper_moy_paiement det on det.num_oper_omp=omp.num_oper_omp "
				+ " left join contrat_cpt cpt on cpt.COD_STRC_STRC = omp.COD_STRC_STRC and cpt.COD_PRD_PRD = omp.COD_PRD_PRD and cpt.NUM_CCPT_CCPT = omp.NUM_CCPT_CCPT "
				+ " left join operation op on op.cod_oper_oper=omp.cod_oper_oper " + " where "
				+ "  omp.dat_oper_omp >='01/11/2016'  and omp.cod_prd_prd=135 and omp.cod_etat_omp = 'V' "
				+ " and omp.cod_oper_oper not in (2121,2122)  "
				+ " and cpt.NUM_RAT_CCPT is not null  and  cpt.COD_STRC_STRC = " + cod_strc
				+ " and COD_ETAT_NSI is null and  (omp.COD_STRC_STRC,omp.COD_PRD_PRD,omp.NUM_CCPT_CCPT) not in (select COD_STRC_STRC,COD_PRD_PRD,NUM_CCPT_CCPT from CPT_GLOBAL_135) ");
		String requete =
				" select op.LIB_COUR_OPER ,cpt.NUM_RAT_CCPT,cpt.COD_RAT_PRD,cpt.COD_RAT_STRC, omp.*,det.*,case when  det.num_oper_omp is null   then 1 else (( ROW_NUMBER () OVER (PARTITION BY det.num_oper_omp order by det.num_cod_domp ))) end  num  ,op.lib_oper_oper from operation_moy_pay  omp"
						+ " left join detail_oper_moy_paiement det on det.num_oper_omp=omp.num_oper_omp "
						+ " left join contrat_cpt cpt on cpt.COD_STRC_STRC = omp.COD_STRC_STRC and cpt.COD_PRD_PRD = omp.COD_PRD_PRD and cpt.NUM_CCPT_CCPT = omp.NUM_CCPT_CCPT "
						+ " left join operation op on op.cod_oper_oper=omp.cod_oper_oper " + " where "
						+ "  omp.num_oper_omp in ( select num_oper_omp from temp_table_operation_moy_pay ) "
						+ " and omp.dat_oper_omp >='01/11/2016' and omp.cod_prd_prd=135 and omp.cod_etat_omp = 'V'  and omp.cod_oper_oper not in (2121,2122)  "
						+ " and cpt.NUM_RAT_CCPT is not null  and  cpt.COD_STRC_STRC = " + cod_strc
						+ " and COD_ETAT_NSI is null  and  (omp.COD_STRC_STRC,omp.COD_PRD_PRD,omp.NUM_CCPT_CCPT) not in (select COD_STRC_STRC,COD_PRD_PRD,NUM_CCPT_CCPT from CPT_GLOBAL_135) order by DAT_SYS_GCD asc";
		// DAT_SYS_GCD > (select max(DATE_FILE_CRE) from batch_vir_file_cpy where cod_bat_bats=69 )
		logger.info(requete);

		listAgNsi = jt.query(requete, new RowMapper() {

			public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
				OperationMoyPay omp = new OperationMoyPay();
				ContratCpt cpt = new ContratCpt();
				ContratCptId cptId = new ContratCptId();
				cptId.setCodStrcStrc(rs.getLong("COD_STRC_STRC"));
				cptId.setCodPrdPrd(rs.getLong("COD_PRD_PRD"));
				cptId.setNumCcptCcpt(rs.getLong("NUM_CCPT_CCPT"));
				cpt.setContratCptId(cptId);
				omp.setContratCpt(cpt);
				omp.setNumOperOmp(rs.getString("NUM_OPER_OMP"));
				omp.setMontDinOmp(rs.getLong("MONT_DIN_OMP"));
				omp.setDatValOmp(rs.getDate("DAT_VAL_OMP"));
				omp.setDatOperOmp(rs.getDate("DAT_OPER_OMP"));
				omp.setCodSensOmp(rs.getString("COD_SENS_OMP"));
				omp.setCodRefcOmp(rs.getString("COD_REFC_OMP"));
				Tache tache = new Tache();
				TacheId tacheId = new TacheId();
				tacheId.setCodOperOper(rs.getLong("COD_OPER_OPER"));
				tacheId.setCodTachTach(rs.getLong("COD_TACH_TACH"));
				tache.setTacheId(tacheId);
				omp.setTache(tache);
				omp.setMontTvaOmp(rs.getLong("MONT_TVA_OMP"));

				String codRefB = "";
				if (rs.getString("COD_REFB_OMP") != null)
					codRefB = rs.getString("COD_REFB_OMP");
				omp.setCodRefbOmp(": " + codRefB);
				Long mntDcom = rs.getLong("MONT_VAL_DOMP");
				if (mntDcom != null && mntDcom > 0l) {
					DetailOperMoyPaiement det = new DetailOperMoyPaiement();
					det.setNumCodDomp(rs.getLong("num"));
					if (det.getNumCodDomp() > 1) {
						omp.setMontDinOmp(0L);
					}
					det.setMontValDomp(rs.getLong("MONT_VAL_DOMP"));
					det.setDatValDomp(rs.getDate("DAT_VAL_DOMP"));
					Set dets = new HashSet<DetailOperMoyPaiement>();
					dets.add(det);
					omp.setDetailOperMoyPaiements(dets);
				}
				omp.setDatTimeOmp(rs.getDate("DAT_TIME_OMP"));
				Devise devise = new Devise();
				devise.setCodDevDev(rs.getLong("COD_DEV_DEV"));
				omp.setDevise(devise);

				TypePiece typePieceDem = new TypePiece();
				typePieceDem.setCodTpceTpce(rs.getLong("COD_DEM_TPCE"));
				omp.setTypePieceDemandeur(typePieceDem);
				omp.setNumPcedOmp(rs.getString("NUM_PCED_OMP"));
				omp.setNomNomdOmp(rs.getString("NOM_NOMD_OMP"));
				omp.setNomPrndOmp(rs.getString("NUM_PCED_OMP"));
				omp.setCodDemOmp(rs.getString("COD_DEM_OMP"));
				omp.setLibMotfOmp(rs.getString("LIB_MOTF_OMP"));

				ContratCptId cptIdRat = new ContratCptId();
				cptIdRat.setCodStrcStrc(rs.getLong("COD_RAT_STRC"));
				cptIdRat.setCodPrdPrd(rs.getLong("COD_RAT_PRD"));
				cptIdRat.setNumCcptCcpt(rs.getLong("NUM_RAT_CCPT"));
				ContratCpt cptRat = new ContratCpt();
				cptRat.setContratCptId(cptIdRat);
				omp.getContratCpt().setContratCpt(cptRat);

				if (!tacheId.getCodOperOper().equals(Constants.COD_OPER_VIR_SIEGE)) {
					String libOper = rs.getString("LIB_COUR_OPER");
					if (libOper != null && libOper.length() > 1) {
						libOper = ": " + libOper.substring(0, Math.min(libOper.length(), 30));
						if (omp.getCodRefbOmp() != null) {
							libOper = libOper + " " + omp.getCodRefbOmp();
							libOper = libOper.substring(0, Math.min(libOper.length(), 30));
						}
					}
					omp.setCodRefbOmp(libOper);
				}

				return omp;

			}
		});
		logger.info("la requete MAJNSIActelOmp Ag a ramené " + String.valueOf(listAgNsi.size()));
		return listAgNsi;

	}

	public void updateListOperMoyPayActel(List<OperationMoyPay> listOmpActel, String etat) {
		jt = new JdbcTemplate(dataSource);
		String numOpers = "";
		if (listOmpActel != null && listOmpActel.size() > 0) {
			numOpers = " ( ";
			for (int i = 0; i < listOmpActel.size(); i++) {
				numOpers = numOpers + " '" + listOmpActel.get(i).getNumOperOmp() + "' ";
				if (i < listOmpActel.size() - 1)
					numOpers = numOpers + ", ";
			}
			numOpers = numOpers + " ) ";
		}

		System.out.println(" select num_oper_omp from temp_table_operation_moy_pay ");
		// jt.execute("Update operation_moy_pay set COD_ETAT_NSI = '"+etat+"', dat_sys_nsi=sysdate where num_oper_omp in
		// (select column_value from table (sys.ODCIVARCHAR2LIST "+numOpers+ " ) ) ");
		jt.execute("Update operation_moy_pay set COD_ETAT_NSI = '" + etat
				+ "', dat_sys_nsi=sysdate where num_oper_omp in ( select num_oper_omp from temp_table_operation_moy_pay ) ");

	}

	public void updateOperMoyPayActelGlobal(String strc, String cod_prd, String ccpt) {
		jt = new JdbcTemplate(dataSource);

		jt.execute("Update operation_moy_pay set COD_ETAT_NSI = '3', dat_sys_nsi=sysdate where COD_STRC_STRC=" + strc
				+ " and COD_PRD_PRD=" + cod_prd + " and NUM_CCPT_CCPT=" + ccpt
				+ "  and DAT_OPER_OMP >='01/11/2016' and COD_ETAT_NSI is null ");

	}

	public List getListAgencesMajNsiActel() {
		jt = new JdbcTemplate(dataSource);
		List<Long> listAgNsi = new ArrayList<Long>();
		String requete = "select btchstrc.cod_strc_strc from batch_structure btchstrc where  btchstrc.cod_bat_bmet="
				+ Constants.COD_BATCH_MAJNSI_Actel;
		logger.info(requete);

		listAgNsi = jt.query(requete, new RowMapper() {

			public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
				Long strc = new Long(rs.getLong("cod_strc_strc"));
				return strc;
			}
		});
		logger.info("la requete MAJNSIActel Ag a ramené " + String.valueOf(listAgNsi.size()));

		return listAgNsi;

	}

	public List getMvtDev() {
		jt = new JdbcTemplate(dataSource);
		List<MvtDevise> mvtDevises = new ArrayList<MvtDevise>();
		String requete =
				"select * from tmp_majnsi_devises where date_vir = (select max(date_vir) from tmp_majnsi_devises )";
		logger.info(requete);
		mvtDevises = jt.query(requete, new RowMapper() {

			public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
				MvtDevise mvtDevise = new MvtDevise(rs.getLong("ID_TMP_VIR"), rs.getDate("DATE_VIR"),
						rs.getString("DATA_VIR"), rs.getString("lIB_OPER"), rs.getString("REF_INTER_SIEGE"),
						rs.getLong("MNT_DEV_DEV"), rs.getString("COD_SENS_OPER"));

				return mvtDevise;
			}
		});
		return mvtDevises;

	}

	public List getMaxDateCreFileNSI(String codBatM) {
		jt = new JdbcTemplate(dataSource);
		List<BatchVirFileCpy> listBatchVirFileCpy = new ArrayList<BatchVirFileCpy>();
		String requete =
				"select * from batch_vir_file_cpy where date_batch_cpy = (select max(date_batch_cpy) from batch_vir_file_cpy where COD_BAT_BATS = "
						+ codBatM + " )";
		logger.info(requete);
		listBatchVirFileCpy = jt.query(requete, new RowMapper() {

			public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
				BatchVirFileCpy batchVirFileCpy = new BatchVirFileCpy();
				batchVirFileCpy.setdateFileCre(rs.getTimestamp("DATE_FILE_CRE"));
				batchVirFileCpy.setfileSize(rs.getLong("FILE_SIZE"));
				batchVirFileCpy.setdateBatchCpy(rs.getDate("DATE_BATCH_CPY"));
				batchVirFileCpy.settimeExec(rs.getString("TIME_EXEC_BATCH"));
				batchVirFileCpy.setnbLineCpy(rs.getLong("NB_LINE_CPY"));
				BatchMetier batch = new BatchMetier();
				batch.setCodBatBmet(rs.getLong("COD_BAT_BATS"));
				batchVirFileCpy.setBatchMetier(batch);
				return batchVirFileCpy;
			}
		});
		return listBatchVirFileCpy;

	}

	public List getListAgencesMajNsiStrcBtch() {
		jt = new JdbcTemplate(dataSource);
		List<AgencesMAJNSIVo> listAgencesPlacementLiq = new ArrayList<AgencesMAJNSIVo>();
		String requete = "	select jrn.* from batch_structure btchstrc ,journee_structure_batch jrn where   "
				+ " jrn.cod_strc_strc=btchstrc.cod_strc_strc     " + " and jrn.cod_bat_bmet=btchstrc.cod_bat_bmet     "
				+ " and btchstrc.cod_bat_bmet= " + Constants.COD_BATCH_MAJNSI
				+ " and jrn.dat_jrn_jrn = ( select max(js.dat_jrn_jrn) from journee_structure js  where  js.cod_strc_strc= jrn.cod_strc_strc) "
				+ "and ( jrn.COD_STAT_JSB = 0  or jrn.COD_STAT_JSB is null ) order by  jrn.cod_strc_strc ";

		logger.info(requete);
		System.out.println(requete);
		listAgencesPlacementLiq = jt.query(requete, new RowMapper() {

			public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
				AgencesMAJNSIVo agencesMAJNSIVo = new AgencesMAJNSIVo();
				JourneeStructureBatchId journeeStructureBatchId = new JourneeStructureBatchId();
				journeeStructureBatchId.setCodBatBmet(rs.getLong("COD_BAT_BMET"));
				journeeStructureBatchId.setCodStrcStrc(rs.getLong("COD_STRC_STRC"));
				journeeStructureBatchId.setDatJrnJrn(rs.getDate("DAT_JRN_JRN"));
				agencesMAJNSIVo.getJourneeStructureBatch().setCodStatJsb(1L);
				agencesMAJNSIVo.setOldCodStatJsb(rs.getLong("COD_STAT_JSB"));
				agencesMAJNSIVo.getJourneeStructureBatch().setJourneeStructureBatchId(journeeStructureBatchId);
				return agencesMAJNSIVo;
			}
		});
		logger.info("la requete MAJNSI a ramené " + String.valueOf(listAgencesPlacementLiq.size()));

		return listAgencesPlacementLiq;

	}

	public List getListAgencesMajNsiActelStrcBtch() {
		jt = new JdbcTemplate(dataSource);
		List<AgencesMAJNSIVo> listAgencesPlacementLiq = new ArrayList<AgencesMAJNSIVo>();
		String requete = "	select jrn.* from batch_structure btchstrc ,journee_structure_batch jrn where   "
				+ " jrn.cod_strc_strc=btchstrc.cod_strc_strc     " + " and jrn.cod_bat_bmet=btchstrc.cod_bat_bmet     "
				+ " and btchstrc.cod_bat_bmet= " + Constants.COD_BATCH_MAJNSI_Actel
				+ " and jrn.dat_jrn_jrn = ( select max(js.dat_jrn_jrn) from journee_structure js  where  js.cod_strc_strc= jrn.cod_strc_strc) "
				+ "and ( jrn.COD_STAT_JSB = 0  or jrn.COD_STAT_JSB is null ) order by jrn.cod_strc_strc ";

		logger.info(requete);
		System.out.println(requete);
		listAgencesPlacementLiq = jt.query(requete, new RowMapper() {

			public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
				AgencesMAJNSIVo agencesMAJNSIVo = new AgencesMAJNSIVo();
				JourneeStructureBatchId journeeStructureBatchId = new JourneeStructureBatchId();
				journeeStructureBatchId.setCodBatBmet(rs.getLong("COD_BAT_BMET"));
				journeeStructureBatchId.setCodStrcStrc(rs.getLong("COD_STRC_STRC"));
				journeeStructureBatchId.setDatJrnJrn(rs.getDate("DAT_JRN_JRN"));
				agencesMAJNSIVo.getJourneeStructureBatch().setCodStatJsb(1L);
				agencesMAJNSIVo.setOldCodStatJsb(rs.getLong("COD_STAT_JSB"));
				agencesMAJNSIVo.getJourneeStructureBatch().setJourneeStructureBatchId(journeeStructureBatchId);
				return agencesMAJNSIVo;
			}
		});
		logger.info("la requete MAJNSI a ramené " + String.valueOf(listAgencesPlacementLiq.size()));

		return listAgencesPlacementLiq;

	}

	public boolean verifExistCheque(Long numchq, Long codStrcStrc, Long codPrdPrd, Long numCcptCcpt) {
		jt = new JdbcTemplate(dataSource);
		Long numeroChequier = Long.valueOf(0);
		String myQuery = "select count(*) from CHEQUIER where COD_STRC_STRC= '" + codStrcStrc + "' and COD_PRD_PRD = '"
				+ codPrdPrd + "' and NUM_CCPT_CCPT =  '" + numCcptCcpt + "' and ('" + numchq
				+ "'BETWEEN NUM_DEB_CHQI and NUM_DEB_CHQI+NBR_CHQ_CHQI-1) and ( NBR_UTIL_CHQI is null or NBR_UTIL_CHQI <> NBR_CHQ_CHQI) and COD_ETAT_CHQI = '"
				+ Constants.ETAT_CHQ_REMI + "' ";
		numeroChequier = (Long) jt.queryForObject(myQuery, Long.class);
		if (numeroChequier.intValue() == 0)
			return false;
		else
			return true;
	}

	// Recherche du numero maximal des crédis CG pour une agence donné
	public List getMaxNumCreditCG(String CodStrcStrc) {
		jt = new JdbcTemplate(dataSource);
		try {

			String reqSQL = "SELECT MAX(substr(TO_CHAR(depassement_personnel.NUM_CRED_CREDEPS,'0000000000'),5,10) ) \n"
					+ "     FROM depassement_personnel \n"
					+ "     WHERE substr(TO_CHAR(depassement_personnel.NUM_CRED_CREDEPS,'0000000000'),2,3) = '"
					+ CodStrcStrc + "'";
			List rows = jt.queryForList(reqSQL);
			return rows;
		} catch (Exception e) {
			logger.error(e.getMessage());
			return (null);
		}
	}

}
