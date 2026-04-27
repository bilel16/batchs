package com.bna.smile.model.histSoldePascale.dao;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Serializable;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.sql.DataSource;

import org.apache.commons.io.FileUtils;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import com.bna.commun.model.Adresse;
import com.bna.commun.model.ContratCpt;
import com.bna.commun.model.ContratCptId;
import com.bna.commun.model.Devise;
import com.bna.commun.model.Personne;
import com.bna.commun.model.Personnel;
import com.bna.commun.model.TraceBlocageMontantContrat;
import com.bna.commun.util.ContextHandler;
import com.bna.commun.util.DateHandler;
import com.bna.commun.util.StrHandler;
import com.bna.commun.vo.PrimitiveVO;
import com.bna.smile.model.domainecommun.service.CRUDservice;
import com.bna.smile.model.histSoldePascale.model.HistSoldePascalVo;
import com.bna.smile.web.commun.controller.UtilCtr;
import com.oxia.fwk.context.Context;
import com.oxia.fwk.core.ISearchEngine;
import com.oxia.fwk.searchengine.SearchEngine;

public class MigrationSoldeCompteDAO implements Serializable {

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
	public MigrationSoldeCompteDAO() {
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

	/**
	 * @return
	 */
	// Séquence table HIST_TRANCHE_SERVI
	public Long getSequenceHistTrch() {
		jt = new JdbcTemplate(dataSource);

		Long numeroSequence = (Long) jt.queryForObject(
				"select NUM_SEQ_HIST_TRCH.NEXTVAL from dual ", Long.class);
		Long lastSeq = (Long) jt
				.queryForObject(
						"select max(NUM_SEQ_TRCH) from HIST_TRANCHE_SERVI ",
						Long.class);
		if (numeroSequence <= lastSeq) {
			jt.execute("alter sequence NUM_SEQ_HIST_TRCH increment by +"
					+ lastSeq + 1);
			numeroSequence = (Long) jt.queryForObject(
					"select NUM_SEQ_HIST_TRCH.NEXTVAL from dual ", Long.class);
			jt.execute("alter sequence NUM_SEQ_HIST_TRCH increment by 1");
		}

		numeroSequence = (Long) jt.queryForObject(
				"select NUM_SEQ_HIST_TRCH.NEXTVAL from dual ", Long.class);
		// System.out.println(numeroSequence);
		return numeroSequence;
	}

	// free days
	public boolean isFreeDay(Date jour) {
		jt = new JdbcTemplate(dataSource);
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		String sd = sdf.format(jour);
		Long count = (Long) jt
				.queryForObject(
						"select count(*) from free_days_calendar  where free_days_calendar.day=EXTRACT(DAY FROM  to_date('"
								+ sd
								+ "','dd/MM/yyyy')) and free_days_calendar.month=EXTRACT(MONTH FROM  to_date('"
								+ sd
								+ "','dd/MM/yyyy')) and free_days_calendar.year=EXTRACT(YEAR FROM  to_date('"
								+ sd + "','dd/MM/yyyy'))", Long.class);
		if (count > 1)
			return true;
		return false;

	}

	public boolean isWorkDay(Date d) {
		Calendar date = Calendar.getInstance();
		date.setTime(d);

		if (date.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY
				|| date.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY)
			return false;
		else if (isFreeDay(d))
			return false;
		else
			return true;

	}

	public void jourOuvrable(Date jourDebut, int count) {

		boolean trouve = false;
		Date jourOuvrable = null;
		int counted = 0;
		while (!trouve) {
			Calendar c = Calendar.getInstance();
			c.setTime(jourDebut);
			if (count > 0)
				c.add(Calendar.DATE, 1);
			else
				c.add(Calendar.DATE, -1);
			jourDebut = c.getTime();
			if (isWorkDay(c.getTime()))
				counted++;
			if (counted == Math.abs(count)) {
				trouve = true;
				jourOuvrable = c.getTime();
			}
		}
		System.out.println(jourOuvrable);
	}

	// Formater un numero
	private String formatStringRight(int number, String value) {
		String res = "";
		if (value == null)
			value = "";
		value = value.trim();

		for (int i = 0; i < number; i++) {
			if (i < value.length())
				res += value.charAt(i);
			else
				res += " ";

		}

		return res;
	}
	public List<Long> getAgPilolte() {
		jt = new JdbcTemplate(dataSource);

		List<Long> liste = new ArrayList<Long>();

		Long seq = 1L;
		SqlRowSet rs = jt.queryForRowSet("select cod_strc_strc from agence_nsi   ");
		while (rs.next())
			liste.add(rs.getLong("cod_strc_strc"));

		return liste;
	}
	/****************************************************/
	public HistSoldePascalVo readCompte(String fileName, String strc)
			throws IOException {

		List<String> lines = new ArrayList<String>();
		HistSoldePascalVo histSoldePascalVo = new HistSoldePascalVo();
		List<PrimitiveVO> compteDinars = new ArrayList<PrimitiveVO>();
		List<PrimitiveVO> compteDevises = new ArrayList<PrimitiveVO>();

		BufferedReader br;

		br = new BufferedReader(new FileReader(fileName));

		try {

			String l = br.readLine();
			// System.out.println(l);
			lines.add(l);
			while (l != null) {

				l = br.readLine();
				lines.add(l);
			}

		} finally {
			br.close();
		}
		boolean loadDinars = true;
		for (int i = 0; i < lines.size(); i++) {
			if (lines.get(i) != null && lines.get(i).length() >= 26) {
				List<String> mvts = UtilCtr.splitInChunks(lines.get(i), 26);
				Iterator<String> itr = mvts.iterator();

				while (itr.hasNext()) {
					String mvt = itr.next();
					PrimitiveVO cpt = new PrimitiveVO();
					if (loadDinars && mvt.startsWith("9999"))
						loadDinars = false;
					if (mvt != null && !mvt.trim().isEmpty()
							&& mvt.startsWith(strc)) {
						try {
							cpt.setVString(mvt.substring(0, 13));
							cpt.setVLong(Long.valueOf(mvt.substring(13, 25)));
							boolean CR_DB = mvt.substring(25, 26).equals("+");
							cpt.setVBool(CR_DB);
							if (loadDinars)
								compteDinars.add(cpt);
							else
								compteDevises.add(cpt);
							System.out.println(cpt.getVString() + "/"
									+ cpt.getVLong() + "/" + cpt.isVBool()
									+ ":" + mvt.substring(25, 26));
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				}

			}
		}
		histSoldePascalVo.setCompteDevises(compteDevises);
		histSoldePascalVo.setCompteDinars(compteDinars);

		return histSoldePascalVo;
	}

	public List<ContratCpt> readCompteBlocageDinars(String fileName, Long strc)
			throws IOException {

		List<String> lines = new ArrayList<String>();
		List<ContratCpt> comptes = new ArrayList<ContratCpt>();

		BufferedReader br;

		br = new BufferedReader(new FileReader(fileName));

		try {

			String l = br.readLine();
			// System.out.println(l);
			lines.add(l);
			while (l != null) {

				l = br.readLine();
				lines.add(l);
			}

		} finally {
			br.close();
		}

		for (int i = 0; i < lines.size(); i++) {
			if (lines.get(i) != null && lines.get(i).length() >= 40) {
				String str = lines.get(i);
				try {
					//System.out.println(lines);
					ContratCpt contratCpt = new ContratCpt();
					ContratCptId contratCptId = new ContratCptId();
					contratCptId.setCodStrcStrc(strc);
					contratCptId
							.setCodPrdPrd(Long.valueOf(str.substring(0, 4)));
					contratCptId.setNumCcptCcpt(Long.valueOf(str.substring(4,
							10)));
					contratCpt.setMontBlocCcpt(Long.valueOf(str.substring(10,
							25)));
					contratCpt.setMontDaffCcpt(Long.valueOf(str.substring(25,
							40)));
					contratCpt.setContratCptId(contratCptId);
					comptes.add(contratCpt);
//					System.out.println(contratCpt.getContratCptId()
//							.getCompteClient()
//							+ ":"
//							+ contratCpt.getMontBlocCcpt()
//							+ ":"
//							+ contratCpt.getMontDaffCcpt());
				} catch (Exception e) {
					e.printStackTrace();
				}

			}
		}

		return comptes;
	}

	public List<String> readCompteBlocage(String fileName) throws IOException {

		List<String> lines = new ArrayList<String>();

		BufferedReader br;

		br = new BufferedReader(new FileReader(fileName));

		try {

			String l = br.readLine();
			if (l != null && !l.trim().isEmpty())
				lines.add(l);
			while (l != null) {

				l = br.readLine();
				if (l != null && !l.trim().isEmpty())
					lines.add(l);
			}

		} finally {
			br.close();
		}

		return lines;
	}

	private void createCompte(String numCpt, Long mnt_devise,
			boolean signeInverse) {
		jt = new JdbcTemplate(dataSource);
		Context context = ContextHandler.getContext();
		ISearchEngine searchEngine = (SearchEngine) context
				.getBean("searchEngine");
		ContratCptId cptId = new ContratCptId();
		cptId.setCodStrcStrc(Long.valueOf(numCpt.substring(0, 3)));
		cptId.setCodPrdPrd(Long.valueOf(numCpt.substring(3, 7)));
		cptId.setNumCcptCcpt(Long.valueOf(numCpt.substring(7, 13)));
		ContratCpt contratCpt = (ContratCpt) searchEngine.get(ContratCpt.class,
				cptId);
		if (signeInverse) {
			mnt_devise = -mnt_devise;
		}
		if (contratCpt != null) {
			long montantConverti = 0;
			double coursFixe = 0;
			if (!contratCpt.getDevise().getCodDevDev()
					.equals(Long.valueOf(788))) {
				coursFixe = UtilCtr.getCoursFixe(DateHandler
						.strToDate("08/01/2015"), contratCpt.getDevise()
						.getCodDevDev());

				montantConverti = UtilCtr.changeDeviseToTND(mnt_devise,
						contratCpt.getDevise().getNbrDecDev(), contratCpt
								.getDevise().getNbrUnitDev(), coursFixe);
			}
			jt.execute("insert into CPT_DEVISE_MIG(COD_STRC_STRC,COD_PRD_PRD,NUM_CCPT_CCPT,SOLDE_DEVISE,SOLDE_DINARS,ETAT_COMPTE,COD_DEV_DEV) values("
					+ cptId.getCodStrcStrc()
					+ ","
					+ cptId.getCodPrdPrd()
					+ ","
					+ cptId.getNumCcptCcpt()
					+ ","
					+ mnt_devise
					+ ","
					+ montantConverti
					+ ","
					+ "'"
					+ contratCpt.getCodEtatCcpt()
					+ "'" + "," + contratCpt.getDevise().getCodDevDev() + ")");
		} else {
			System.out.println("Contrat Inexistant:" + numCpt);
			String etat = "";
			jt.execute("insert into CPT_DEVISE_MIG(COD_STRC_STRC,COD_PRD_PRD,NUM_CCPT_CCPT,SOLDE_DEVISE) values("
					+ cptId.getCodStrcStrc()
					+ ","
					+ cptId.getCodPrdPrd()
					+ ","
					+ cptId.getNumCcptCcpt() + "," + mnt_devise + ")");
		}

	}

	public boolean verifExisteCompte(ContratCptId contratCptId) {

//		Long count = jt
//				.queryForLong("select count(*) from contrat_cpt where COD_STRC_STRC= "
//						+ contratCptId.getCodStrcStrc()
//						+ " and COD_PRD_PRD="
//						+ contratCptId.getCodPrdPrd()
//						+ "    and NUM_CCPT_CCPT="
//						+ contratCptId.getNumCcptCcpt());
//		if (count > 0)
//			return true;
//		else
			return true;
	}

	private void createCompteDinars(String numCpt, Long mnt_dinars,
			boolean signeInverse) {
		jt = new JdbcTemplate(dataSource);
		try {
			
			ContratCptId cptId = new ContratCptId();
			cptId.setCodStrcStrc(Long.valueOf(numCpt.substring(0, 3)));
			cptId.setCodPrdPrd(Long.valueOf(numCpt.substring(3, 7)));
			cptId.setNumCcptCcpt(Long.valueOf(numCpt.substring(7, 13)));
			if (signeInverse) {
				mnt_dinars = -mnt_dinars;
			}
			String cod_etat = "";
			Long codDev = null;
			if (verifExisteCompte(cptId) && (cptId.getCodPrdPrd()>200 && cptId.getCodPrdPrd()<1000) ) {

				jt.execute("insert into MIG_SOLDE_CPT(COD_STRC_STRC,COD_PRD_PRD,NUM_CCPT_CCPT,SOLDE_DINARS,ETAT_COMPTE,COD_DEV_DEV,DAT_MIG_FICH) values("
						+ cptId.getCodStrcStrc()
						+ ","
						+ cptId.getCodPrdPrd()
						+ ","
						+ cptId.getNumCcptCcpt()
						+ ","
						+ mnt_dinars
						+ ","
						+ "'"
						+ cod_etat
						+ "'"
						+ ","
						+ codDev
						+ ",'"
						+ DateHandler.dateToStr(new Date()) + "'" + ")");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void createCompteDevise(String numCpt, Long mnt_devise,
			boolean signeInverse) {
		jt = new JdbcTemplate(dataSource);
		try {
			ContratCptId cptId = new ContratCptId();
			cptId.setCodStrcStrc(Long.valueOf(numCpt.substring(0, 3)));
			cptId.setCodPrdPrd(Long.valueOf(numCpt.substring(3, 7)));
			cptId.setNumCcptCcpt(Long.valueOf(numCpt.substring(7, 13)));
			if (signeInverse) {
				mnt_devise = -mnt_devise;
			}
			String cod_etat = "";
			Long codDev = null;
			if (verifExisteCompte(cptId) && (cptId.getCodPrdPrd()>200 && cptId.getCodPrdPrd()<1000)) {

				int inserted = jt
						.update("update MIG_SOLDE_CPT set SOLDE_DEVISE="
								+ mnt_devise + " where cod_strc_strc="
								+ cptId.getCodStrcStrc() + " and cod_prd_prd="
								+ cptId.getCodPrdPrd() + " and num_ccpt_ccpt="
								+ cptId.getNumCcptCcpt());
				if (inserted == 0) {
					jt.execute("insert into MIG_SOLDE_CPT(COD_STRC_STRC,COD_PRD_PRD,NUM_CCPT_CCPT,SOLDE_DEVISE,ETAT_COMPTE,COD_DEV_DEV,DAT_MIG_FICH) values("
							+ cptId.getCodStrcStrc()
							+ ","
							+ cptId.getCodPrdPrd()
							+ ","
							+ cptId.getNumCcptCcpt()
							+ ","
							+ mnt_devise
							+ ","
							+ "'"
							+ cod_etat
							+ "'"
							+ ","
							+ codDev
							+ ",'"
							+ DateHandler.dateToStr(new Date()) + "'" + ")");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void updateSoldeDinars(String numCpt, Long mnt_dinars,
			boolean signeInverse) {
		jt = new JdbcTemplate(dataSource);
		try {
			ContratCptId cptId = new ContratCptId();
			cptId.setCodStrcStrc(Long.valueOf(numCpt.substring(0, 3)));
			cptId.setCodPrdPrd(Long.valueOf(numCpt.substring(3, 7)));
			cptId.setNumCcptCcpt(Long.valueOf(numCpt.substring(7, 13)));

			if (signeInverse) {
				mnt_dinars = -mnt_dinars;
			}

			jt.execute("update CPT_DINARS_MIG set " + "SOLDE_DINARS="
					+ mnt_dinars + ",DAT_MIG_FICH='"
					+ DateHandler.dateToStr(new Date())
					+ "' where cod_strc_strc=" + cptId.getCodStrcStrc()
					+ " and cod_prd_prd=" + cptId.getCodPrdPrd()
					+ " and num_ccpt_ccpt=" + cptId.getNumCcptCcpt());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void insertDetailBlocage(String line, Long strc) {
		jt = new JdbcTemplate(dataSource);
		Context context = ContextHandler.getContext();
		ISearchEngine searchEngine = (SearchEngine) context
				.getBean("searchEngine");
		CRUDservice crudService = (CRUDservice) context.getBean("crudservice");
		SimpleDateFormat sdf = new SimpleDateFormat("ddMMyy");

		String[] data = line.split(";");
		String numCompte = data[0].substring(0, 10);
		String userBloc = data[1];
		String dateBlocage = data[2];
		String mntBlocage = data[3];
		String dateDeblocage = data[4];
		String motifBlocage = data[5];
		String userDeb = data[6];

		System.out.println(numCompte + "/" + mntBlocage + "/" + dateDeblocage);
		try {

			ContratCptId cptId = new ContratCptId();
			cptId.setCodStrcStrc(strc);
			cptId.setCodPrdPrd(Long.valueOf(numCompte.substring(0, 4)));
			cptId.setNumCcptCcpt(Long.valueOf(numCompte.substring(4, 10)));
			ContratCpt contratCpt = (ContratCpt) searchEngine.get(
					ContratCpt.class, cptId);
			if (contratCpt != null && !mntBlocage.contains("-")) {

				TraceBlocageMontantContrat blocageMontantContrat = new TraceBlocageMontantContrat();
				blocageMontantContrat.setContratCpt(contratCpt);
				if (!dateBlocage.trim().isEmpty())
					blocageMontantContrat.setDatDebBloc(sdf.parse(dateBlocage));
				if (!dateDeblocage.trim().isEmpty())
					blocageMontantContrat.setDatFinBloc(sdf
							.parse(dateDeblocage));
				blocageMontantContrat.setMntBlocBloc(Long.valueOf(mntBlocage));
				blocageMontantContrat.setDatTimeOp(new Date());
				blocageMontantContrat.setMotifBlocage(motifBlocage);
				Personnel persBloc = (Personnel) searchEngine.get(
						Personnel.class, userBloc);
				Personnel persDebBloc = (Personnel) searchEngine.get(
						Personnel.class, userDeb);

				blocageMontantContrat.setPersonnelBlocage(persBloc);
				blocageMontantContrat.setPersonnelDeblocage(persDebBloc);
				blocageMontantContrat.setDatMigBloc(new Date());
				crudService.create(blocageMontantContrat);
			} else {
				jt.execute("insert into REJET_MIG_BLOCAGE_MNT(NUM_CCPT_SEQ,COD_STRC_STRC,COD_PRD_PRD,NUM_CCPT_CCPT,STR_DATA_BLOC) values('"
						+ data[0]
						+ "',"
						+ cptId.getCodStrcStrc()
						+ ","
						+ cptId.getCodPrdPrd()
						+ ","
						+ cptId.getNumCcptCcpt()
						+ ",'" + line + "')");
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void updateCompteDevise(Long strc) {
		jt = new JdbcTemplate(dataSource);
		Context context = ContextHandler.getContext();
		ISearchEngine searchEngine = (SearchEngine) context
				.getBean("searchEngine");
		CRUDservice crudService = (CRUDservice) context.getBean("crudservice");

		String req = "";
		SqlRowSet srs = null;
		req = "select cod_strc_strc,cod_prd_prd,num_ccpt_ccpt,solde_devise,solde_dinars,cod_dev_dev "
				+ " from cpt_devise_mig "
				+ " where cod_dev_dev!=788"
				+ " and etat_compte='V'"
				// + " and solde_devise!=0"
				+ " and cod_prd_prd in (select  cod_prd_prd from produit where cod_gfam_gp='04' and cod_fam_fam='01' and cod_sfam_sfp='01')"
				+ " or cod_prd_prd=147 and (cod_dev_dev!=788 "
				+ " and etat_compte='V'  ) "
				+ " or cod_prd_prd=421 and (cod_dev_dev!=788 "
				+ " and etat_compte='V'  ) " + " order by cod_prd_prd";
		srs = jt.queryForRowSet(req);

		while (srs.next()) {
			if (strc.equals(srs.getLong("cod_strc_strc"))) {
				System.out.println("Compte:" + srs.getLong("cod_prd_prd") + "/"
						+ srs.getLong("num_ccpt_ccpt"));

				ContratCptId cptId = new ContratCptId();
				cptId.setCodStrcStrc(srs.getLong("cod_strc_strc"));
				cptId.setCodPrdPrd(srs.getLong("cod_prd_prd"));
				cptId.setNumCcptCcpt(srs.getLong("num_ccpt_ccpt"));
				ContratCpt contratCpt = (ContratCpt) searchEngine.get(
						ContratCpt.class, cptId);
				contratCpt.setMontSoldCcpt(srs.getLong("solde_dinars"));
				contratCpt.setMontSdevCcpt(srs.getLong("solde_devise"));
				crudService.update(contratCpt);
			}
		}
		System.out.println("Fin mise à jours compte devise!");

	}

	public void updateBlocageCompteDevise(Long strc) {
		jt = new JdbcTemplate(dataSource);
		Context context = ContextHandler.getContext();
		ISearchEngine searchEngine = (SearchEngine) context
				.getBean("searchEngine");
		CRUDservice crudService = (CRUDservice) context.getBean("crudservice");

		String req = "";
		SqlRowSet srs = null;
		req = "select cpt.cod_strc_strc,cpt.cod_prd_prd,cpt.num_ccpt_ccpt,sum(db.mnt_bloc_bloc) as blocage from trace_blocage_montant_contrat db,contrat_cpt cpt "
				+ " where cpt.cod_dev_dev!=788 "
				+ " and cpt.cod_strc_strc="
				+ strc
				+ " and cpt.cod_prd_prd=db.cod_prd_prd "
				+ " and cpt.num_ccpt_ccpt=db.num_ccpt_ccpt "
				+ " and (db.dat_fin_bloc is null or trunc(db.dat_fin_bloc) > trunc(sysdate)) "
				+ " and db.mnt_bloc_bloc is not null group by cpt.cod_strc_strc,cpt.cod_prd_prd,cpt.num_ccpt_ccpt "
				+ " order by cod_prd_prd";
		srs = jt.queryForRowSet(req);

		while (srs.next()) {
			System.out.println("Compte:" + srs.getLong("cod_prd_prd") + "/"
					+ srs.getLong("num_ccpt_ccpt"));

			ContratCptId cptId = new ContratCptId();
			cptId.setCodStrcStrc(srs.getLong("cod_strc_strc"));
			cptId.setCodPrdPrd(srs.getLong("cod_prd_prd"));
			cptId.setNumCcptCcpt(srs.getLong("num_ccpt_ccpt"));
			ContratCpt contratCpt = (ContratCpt) searchEngine.get(
					ContratCpt.class, cptId);
			contratCpt.setMontBdevCcpt(srs.getLong("blocage"));
			crudService.update(contratCpt);

		}
		System.out.println("Fin mise à jours blocage compte devise!");

	}

	public void fixBlocageCompteDevise(Long strc) {
		jt = new JdbcTemplate(dataSource);
		Context context = ContextHandler.getContext();
		ISearchEngine searchEngine = (SearchEngine) context
				.getBean("searchEngine");
		CRUDservice crudService = (CRUDservice) context.getBean("crudservice");

		String req = "";
		SqlRowSet srs = null;
		req = "select cod_prd_prd,num_ccpt_ccpt,mont_bdev_ccpt,cod_dev_dev "
				+ " from smile.contrat_cpt " + " where  cod_strc_strc=" + strc
				+ " " + " and cod_dev_dev !=788 "
				+ " and mont_bdev_ccpt is not null and mont_bdev_ccpt >0";

		srs = jt.queryForRowSet(req);

		while (srs.next()) {
			System.out.println("Compte:" + srs.getLong("cod_prd_prd") + "/"
					+ srs.getLong("num_ccpt_ccpt"));
			Long cod_dev_dev = srs.getLong("cod_dev_dev");
			long montantConverti = 0;
			double coursFixe = 0;
			Long blocDevise = srs.getLong("mont_bdev_ccpt");
			Devise devise = (Devise) searchEngine
					.get(Devise.class, cod_dev_dev);
			coursFixe = UtilCtr.getCoursFixe(
					DateHandler.strToDate("30/01/2015"), devise.getCodDevDev());
			montantConverti = UtilCtr.changeDeviseToTND(blocDevise,
					devise.getNbrDecDev(), devise.getNbrUnitDev(), coursFixe);
			ContratCptId cptId = new ContratCptId();
			cptId.setCodStrcStrc(strc);
			cptId.setCodPrdPrd(srs.getLong("cod_prd_prd"));
			cptId.setNumCcptCcpt(srs.getLong("num_ccpt_ccpt"));
			ContratCpt contratCpt = (ContratCpt) searchEngine.get(
					ContratCpt.class, cptId);
			contratCpt.setMontBlocCcpt(montantConverti);
			crudService.update(contratCpt);

		}
		System.out.println("Fin mise à jours blocage compte devise!");

	}

	public void updateContreValeurBlocageDevise(Long strc) {
		jt = new JdbcTemplate(dataSource);
		Context context = ContextHandler.getContext();
		ISearchEngine searchEngine = (SearchEngine) context
				.getBean("searchEngine");
		CRUDservice crudService = (CRUDservice) context.getBean("crudservice");

		String req = "";
		SqlRowSet srs = null;
		req = "select cpt.cod_strc_strc,cpt.cod_prd_prd,cpt.num_ccpt_ccpt  ,db.MNT_BLOC_BLOC ,db.NUM_SEQ_TBC,cpt.cod_dev_dev from trace_blocage_montant_contrat db,contrat_cpt cpt "
				+ " where cpt.cod_dev_dev!=788 "
				+ " and cpt.cod_strc_strc="
				+ strc
				+ " and db.dat_mig_bloc is not null "
				+ " and cpt.cod_prd_prd=db.cod_prd_prd "
				+ " and cpt.num_ccpt_ccpt=db.num_ccpt_ccpt "
				+ " and (db.dat_fin_bloc is null or trunc(db.dat_fin_bloc) > trunc(sysdate)) "
				+ " and db.mnt_bloc_bloc is not null  "
				+ " order by cod_prd_prd";
		srs = jt.queryForRowSet(req);

		while (srs.next()) {
			System.out.println("Compte:" + srs.getLong("cod_prd_prd") + "/"
					+ srs.getLong("num_ccpt_ccpt"));

			Long mntBloc_dev = srs.getLong("MNT_BLOC_BLOC");
			Long cod_dev_dev = srs.getLong("cod_dev_dev");
			Long id_trace = srs.getLong("NUM_SEQ_TBC");
			long montantConverti = 0;
			double coursFixe = 0;
			Devise devise = (Devise) searchEngine
					.get(Devise.class, cod_dev_dev);
			coursFixe = UtilCtr.getCoursFixe(
					DateHandler.strToDate("21/01/2015"), devise.getCodDevDev());

			montantConverti = UtilCtr.changeDeviseToTND(mntBloc_dev,
					devise.getNbrDecDev(), devise.getNbrUnitDev(), coursFixe);

			jt.execute("update TRACE_BLOCAGE_MONTANT_CONTRAT set MNT_BLOCD_BLOC="
					+ montantConverti + "  where NUM_SEQ_TBC=" + id_trace);

		}
		System.out
				.println("Fin mise à jours contre valeur blocage mnt compte devise!");

	}

	public void insertCompteMig(String fileName, String strc) {
		try {
			jt = new JdbcTemplate(dataSource);

			jt.execute("delete from  MIG_SOLDE_CPT where cod_strc_strc="
					+ Long.valueOf(strc));
			HistSoldePascalVo comptes = readCompte(fileName, strc);
			List<PrimitiveVO> comptesDinars = comptes.getCompteDinars();
			List<PrimitiveVO> comptesDevises = comptes.getCompteDevises();

			List<String> liste = new ArrayList<String>();
			List<String> listeRep = new ArrayList<String>();
			System.out.println("Compte dinars à inserer:"
					+ comptesDinars.size());
			for (int i = 0; i < comptesDinars.size(); i++) {
				if (!liste.contains(comptesDinars.get(i).getVString())) {
					liste.add(comptesDinars.get(i).getVString());
					// System.out.println(i);

					createCompteDinars(comptesDinars.get(i).getVString(),
							comptesDinars.get(i).getVLong(),
							comptesDinars.get(i).isVBool());
				} else {
					System.out.println("Contrat repeté:"
							+ comptesDinars.get(i).getVString() + ":Solde:"
							+ comptesDinars.get(i).getVLong());

					listeRep.add(comptesDinars.get(i).getVString());
				}
			}
			System.out.println("Nombre Compte repeté:" + listeRep.size());
			System.out.println("Compte devise à inserer:"
					+ comptesDevises.size());
			liste = new ArrayList<String>();
			listeRep = new ArrayList<String>();
			for (int i = 0; i < comptesDevises.size(); i++) {
				if (!liste.contains(comptesDevises.get(i).getVString())) {
					liste.add(comptesDevises.get(i).getVString());
					// System.out.println(i);

					createCompteDevise(comptesDevises.get(i).getVString(),
							comptesDevises.get(i).getVLong(), comptesDevises
									.get(i).isVBool());
				} else {
					System.out.println("Contrat repeté:"
							+ comptesDevises.get(i).getVString() + ":Solde:"
							+ comptesDevises.get(i).getVLong());

					listeRep.add(comptesDevises.get(i).getVString());
				}
			}
			System.out.println("Nombre Compte repeté:" + listeRep.size());

		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println(fileName);
			e.printStackTrace();
		}

		System.out.println("Insertion fichier terminée:");
	}

	private void createBlocageDinars(ContratCpt cpt) {
		jt = new JdbcTemplate(dataSource);
		try {

			int inserted = jt.update("update MIG_SOLDE_CPT set MNT_BLOC="
					+ cpt.getMontBlocCcpt() + ",MNT_DEP_AFF="
					+ cpt.getMontDaffCcpt() + " where cod_strc_strc="
					+ cpt.getContratCptId().getCodStrcStrc()
					+ " and cod_prd_prd="
					+ cpt.getContratCptId().getCodPrdPrd()
					+ " and num_ccpt_ccpt="
					+ cpt.getContratCptId().getNumCcptCcpt());
			if (inserted == 0) {
				jt.execute("insert into MIG_SOLDE_CPT(COD_STRC_STRC,COD_PRD_PRD,NUM_CCPT_CCPT,MNT_BLOC,MNT_DEP_AFF,DAT_MIG_FICH) values("
						+ cpt.getContratCptId().getCodStrcStrc()
						+ ","
						+ cpt.getContratCptId().getCodPrdPrd()
						+ ","
						+ cpt.getContratCptId().getNumCcptCcpt()
						+ ","
						+ cpt.getMontBlocCcpt()
						+ ","
						+ cpt.getMontDaffCcpt()
						+ ",'" + DateHandler.dateToStr(new Date()) + "'" + ")");
				jt.execute("commit");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void insertMigBlocageCompte(String fileName, Long strc) {
		try {
			jt = new JdbcTemplate(dataSource);

			// jt.execute("delete from  CPT_BLOC_MIG where cod_strc_strc=" +
			// strc );
			List<ContratCpt> comptes = readCompteBlocageDinars(fileName, strc);
			List<String> liste = new ArrayList<String>();
			List<String> listeRep = new ArrayList<String>();
			System.out.println("Compte à inserer:" + comptes.size());
			for (int i = 0; i < comptes.size(); i++) {
				if (!liste.contains(comptes.get(i).getContratCptId()
						.getCompteClient())) {
					liste.add(comptes.get(i).getContratCptId()
							.getCompteClient());
					// System.out.println(i);
					// if
					// (comptes.get(i).getContratCptId().getCodPrdPrd().equals(103L))
					createBlocageDinars(comptes.get(i));
				} else {
					System.out.println("Contrat repeté:"
							+ comptes.get(i).getContratCptId()
									.getCompteClient() + ":Solde:"
							+ comptes.get(i).getMontBlocCcpt());

					listeRep.add(comptes.get(i).getContratCptId()
							.getCompteClient());
				}
			}
			System.out.println("Nombre Compte repeté:" + listeRep.size());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("Migration Blocage Dinars terminée!");

	}

	public void blocFile(String fileName) throws IOException {

		// jt.execute("delete from  CPT_BLOC_MIG where cod_strc_strc=" + strc );
		List<String> lines = new ArrayList<String>();
		List<ContratCpt> comptes = new ArrayList<ContratCpt>();

		BufferedReader br;

		br = new BufferedReader(new FileReader(fileName));

		try {

			String l = br.readLine();
			// System.out.println(l);
			lines.add(l);
			while (l != null) {

				l = br.readLine();
				lines.add(l);
			}

		} finally {
			br.close();
		}

		for (int i = 0; i < lines.size(); i++) {
			if (lines.get(i) != null && lines.get(i).length() == 28) {
				String str = lines.get(i);
				try {
					ContratCpt contratCpt = new ContratCpt();
					ContratCptId contratCptId = new ContratCptId();
					contratCptId.setCodStrcStrc(Long.valueOf(str
							.substring(0, 3)));
					contratCptId
							.setCodPrdPrd(Long.valueOf(str.substring(3, 7)));
					contratCptId.setNumCcptCcpt(Long.valueOf(str.substring(7,
							13)));
					contratCpt.setMontBlocCcpt(Long.valueOf(str.substring(13,
							28)));
					contratCpt.setContratCptId(contratCptId);
					comptes.add(contratCpt);
					System.out.println(contratCpt.getContratCptId()
							.getCompteClient()
							+ ":"
							+ contratCpt.getMontBlocCcpt());
				} catch (Exception e) {
					e.printStackTrace();
				}

			}
		}
		Context context = ContextHandler.getContext();
		ISearchEngine searchEngine = (SearchEngine) context
				.getBean("searchEngine");
		CRUDservice crudService = (CRUDservice) context.getBean("crudservice");
		for (int i = 0; i < comptes.size(); i++) {
			ContratCpt contratCpt = (ContratCpt) searchEngine.get(
					ContratCpt.class, comptes.get(i).getContratCptId());
			Long mntBloc = contratCpt.getMontBlocCcpt();
			if (mntBloc != null)
				mntBloc += comptes.get(i).getMontBlocCcpt();
			contratCpt.setMontBlocCcpt(mntBloc);
			crudService.update(contratCpt);
		}
		System.out.println("Fin rectif");
	}

	public void updateSoldeContratCompte(Long strc) {
		jt = new JdbcTemplate(dataSource);
		

		String req = "";
		SqlRowSet srs = null;
		req = "select * "
				+ " from MIG_SOLDE_CPT mig ,contrat_cpt cpt"
				+ " where mig.cod_strc_strc="
				+ strc
				+ " and mig.cod_strc_strc=cpt.cod_strc_strc and mig.cod_prd_prd=cpt.cod_prd_prd and mig.num_ccpt_ccpt=cpt.num_ccpt_ccpt order by mig.cod_prd_prd";
		srs = jt.queryForRowSet(req);

		while (srs.next()) {
			// System.out.println("Compte:" + srs.getLong("cod_prd_prd") + "/" +
			// srs.getLong("num_ccpt_ccpt"));

			ContratCptId cptId = new ContratCptId();
			cptId.setCodStrcStrc(srs.getLong("cod_strc_strc"));
			cptId.setCodPrdPrd(srs.getLong("cod_prd_prd"));
			cptId.setNumCcptCcpt(srs.getLong("num_ccpt_ccpt"));
			Long mntDin = srs.getLong("SOLDE_DINARS");
			Long mntDev = srs.getLong("SOLDE_DEVISE");
			// ContratCpt contratCpt = (ContratCpt)
			// searchEngine.get(ContratCpt.class, cptId);
			// if (contratCpt != null) {
			if (mntDin != null) {
				jt.execute("update contrat_cpt set " + "MONT_SOLD_CCPT="
						+ mntDin
						+ " ,DAT_MAJ=trunc(sysdate) where cod_strc_strc="
						+ cptId.getCodStrcStrc() + " and cod_prd_prd="
						+ cptId.getCodPrdPrd() + " and num_ccpt_ccpt="
						+ cptId.getNumCcptCcpt());
			}
			// contratCpt.setMontSoldCcpt(mntDin);
			if (mntDev != null) {
				jt.execute("update contrat_cpt set " + "MONT_SDEV_CCPT="
						+ mntDev
						+ " ,DAT_MAJ=trunc(sysdate)  where cod_strc_strc="
						+ cptId.getCodStrcStrc() + " and cod_prd_prd="
						+ cptId.getCodPrdPrd() + " and num_ccpt_ccpt="
						+ cptId.getNumCcptCcpt());
			}
			// contratCpt.setMontSdevCcpt(mntDev);

			// crudService.update(contratCpt);
			// }

		}
		jt.execute("commit");
		System.out.println("Fin mise à jours solde compte !");

	}

	public void updateCompteBlocage(Long strc) {
		jt = new JdbcTemplate(dataSource);
		

		String req = "";
		SqlRowSet srs = null;
		req = "select * "
				+ " from MIG_SOLDE_CPT "
				+ " where  cod_strc_strc="
				+ strc
				+ " and (MNT_BLOC is not null or MNT_DEP_AFF is not null) order by cod_prd_prd ";
		srs = jt.queryForRowSet(req);

		while (srs.next()) {
			// System.out.println("Compte:" + srs.getLong("cod_prd_prd") + "/" +
			// srs.getLong("num_ccpt_ccpt"));

			ContratCptId cptId = new ContratCptId();
			cptId.setCodStrcStrc(srs.getLong("cod_strc_strc"));
			cptId.setCodPrdPrd(srs.getLong("cod_prd_prd"));
			cptId.setNumCcptCcpt(srs.getLong("num_ccpt_ccpt"));
			// ContratCpt contratCpt = (ContratCpt)
			// searchEngine.get(ContratCpt.class, cptId);
			// if (contratCpt != null) {
			Long mntBloc = srs.getLong("MNT_BLOC");
			Long mntDepo = srs.getLong("MNT_DEP_AFF");
			if (mntBloc != null) {
				jt.execute("update contrat_cpt set " + "MONT_BLOC_CCPT="
						+ mntBloc
						+ " ,DAT_MAJ=trunc(sysdate) where cod_strc_strc="
						+ cptId.getCodStrcStrc() + " and cod_prd_prd="
						+ cptId.getCodPrdPrd() + " and num_ccpt_ccpt="
						+ cptId.getNumCcptCcpt());
			}
			// contratCpt.setMontBlocCcpt(srs.getLong("MNT_BLOC"));
			if (mntDepo != null) {
				jt.execute("update contrat_cpt set " + "MONT_DAFF_CCPT="
						+ mntDepo
						+ " ,DAT_MAJ=trunc(sysdate) where cod_strc_strc="
						+ cptId.getCodStrcStrc() + " and cod_prd_prd="
						+ cptId.getCodPrdPrd() + " and num_ccpt_ccpt="
						+ cptId.getNumCcptCcpt());
			}
			// contratCpt.setMontDaffCcpt(srs.getLong("MNT_DEP_AFF"));
			// if (mntBloc != null || mntDepo != null)
			// crudService.update(contratCpt);
			// }

		}
		jt.execute("commit");
		System.out.println("Fin mise à jours blocage dinars!");

	}

	public void migBlocageCompte(String fileName, Long strc) {
		try {
			jt = new JdbcTemplate(dataSource);

			List<String> blocs = readCompteBlocage(fileName);
			jt.execute("delete from  TRACE_BLOCAGE_MONTANT_CONTRAT where cod_strc_strc="
					+ strc + " and dat_mig_bloc is not null");
			jt.execute("delete from  REJET_MIG_BLOCAGE_MNT where cod_strc_strc="
					+ strc);

			System.out.println("blocage à inserer:" + blocs.size());
			for (int i = 0; i < blocs.size(); i++)
				insertDetailBlocage(blocs.get(i), strc);

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("Fin insertion trace blocage contrat!");

	}

	public void migBlocageCompteDinars(String fileName, Long strc) {
		try {
			jt = new JdbcTemplate(dataSource);

			List<String> blocs = readCompteBlocage(fileName);
			jt.execute("delete from  TRACE_BLOCAGE_MONTANT_CONTRAT where cod_strc_strc="
					+ strc + " and dat_mig_bloc is not null");
			jt.execute("delete from  REJET_MIG_BLOCAGE_MNT where cod_strc_strc="
					+ strc);

			System.out.println("blocage à inserer:" + blocs.size());
			for (int i = 0; i < blocs.size(); i++)
				insertDetailBlocage(blocs.get(i), strc);

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("Fin insertion trace blocage contrat!");

	}

	public void insertSoldeDinars(String fileName) {
		try {
			jt = new JdbcTemplate(dataSource);

			// // jt.execute("delete from  CPT_DEVISE_MIG");
			// List<PrimitiveVO> comptes = readCompte(fileName);
			// List<String> liste = new ArrayList<String>();
			// List<String> listeRep = new ArrayList<String>();
			// System.out.println("Compte à inserer:" + comptes.size());
			// for (int i = 0; i < comptes.size(); i++) {
			// if (!liste.contains(comptes.get(i).getVString())) {
			// liste.add(comptes.get(i).getVString());
			// // System.out.println(i);
			// updateSoldeDinars(comptes.get(i).getVString(),
			// comptes.get(i).getVLong(), comptes.get(i).isVBool());
			// } else {
			// System.out.println("Contrat repeté:" +
			// comptes.get(i).getVString() + ":Solde:"
			// + comptes.get(i).getVLong());
			//
			// listeRep.add(comptes.get(i).getVString());
			// }
			// }
			// System.out.println("Nombre Compte repeté:" + listeRep.size());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	// ******CCER*********/

	public void insertTraceCCER(String fileName, String dateEch) {
		jt = new JdbcTemplate(dataSource);
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yy");
		System.out.println("Starting ....");

		// jt.execute("delete from  CCER.RATIO_CPLA where dat_eche_cpla='" +
		// dateEch + "'");
		List<String> lines = new ArrayList<String>();
		try {
			lines = readCompteBlocage(fileName);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(DateHandler.strToDate(dateEch));
		calendar.add(Calendar.MONTH, 1);
		Date nextmonth = calendar.getTime();
		System.out.println("Next month :" + nextmonth);
		for (int i = 0; i < lines.size(); i++) {

			String[] data = lines.get(i).split(";");
			Long numSeq = Long.valueOf(data[0]);
			Long mntCpla = Long.valueOf(data[3]);
			String dateEchCpla = null;
			dateEchCpla = data[4].split(" ")[0];
			String codEta = data[6];
			Long strc = Long.valueOf(data[7]);
			Long prd = Long.valueOf(data[8]);
			Long numCpt = Long.valueOf(data[9]);
			Long prdCpla = Long.valueOf(data[21]);

			System.out.println(numSeq + "/" + dateEchCpla + "/" + prdCpla);
			Date dateCpla = null;
			try {
				dateCpla = sdf.parse(dateEchCpla);
			} catch (ParseException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			boolean echValide = false;
			System.out.println(DateHandler.strToDate(dateEchCpla).compareTo(
					DateHandler.strToDate(dateEch)) > 0);
			System.out.println(DateHandler.strToDate(dateEchCpla).compareTo(
					nextmonth) < 0);
			System.out.println(DateHandler.strToDate(dateEchCpla).compareTo(
					nextmonth) == 0);

			if (DateHandler.strToDate(DateHandler.dateToStr(dateCpla))
					.compareTo(DateHandler.strToDate(dateEch)) > 0
					&& (DateHandler.strToDate(DateHandler.dateToStr(dateCpla))
							.compareTo(nextmonth) < 0 || DateHandler.strToDate(
							DateHandler.dateToStr(dateCpla)).compareTo(
							nextmonth) == 0))
				echValide = true;
			try {
				if ((codEta.equals("V") || codEta.equals("VC") || codEta
						.equals("VT")) && echValide)
					jt.execute("insert into CCER.RATIO_CPLA(NUM_SEQ_CPLA,MONT_ACTU_CPLA,DAT_ECHE_CPLA,COD_ETAT_CPLA,COD_STRC_STRC,COD_PRD_PRD,NUM_CCPT_CCPT,COD_PRD_CPLA,DAT_CRE_TRACE) "
							+ " values("
							+ numSeq
							+ ","
							+ mntCpla
							+ ",'"
							+ dateEchCpla
							+ "','"
							+ codEta
							+ "',"
							+ strc
							+ ","
							+ prd
							+ ","
							+ numCpt
							+ ","
							+ prdCpla
							+ ",'"
							+ sdf.format(new Date()) + "')");
			}

			catch (Exception e) {
				e.printStackTrace();
			}
		}
		System.out.println(" Fin extraction CCER!");

	}

	public void insertTraceSoldeDevise(String dateJrn, Long codStrc) {
		jt = new JdbcTemplate(dataSource);
		jt.execute("delete from  TRACE_SOLDE_DEVISE where cod_strc_strc="
				+ codStrc + " and DAT_TRC_SOLD='" + dateJrn + "'");
		String req = "select cod_strc_strc,cod_prd_prd,num_ccpt_ccpt,mont_sold_ccpt,mont_sdev_ccpt,mont_bloc_ccpt,mont_bdev_ccpt,cod_dev_dev from contrat_cpt "
				+ " where cod_dev_dev!=788"
				+ " and cod_etat_ccpt='V' and cod_strc_strc=" + codStrc;

		SqlRowSet rs = jt.queryForRowSet(req);

		while (rs.next()) {

			jt.execute("insert into TRACE_SOLDE_DEVISE(COD_STRC_STRC,COD_PRD_PRD,NUM_CCPT_CCPT,MONT_SOLD_CCPT,MONT_SDEV_CCPT,MONT_BLOC_CCPT,MONT_BDEV_CCPT,COD_DEV_DEV,DAT_TRC_SOLD) "
					+ "values("
					+ rs.getLong("cod_strc_strc")
					+ ","
					+ rs.getLong("cod_prd_prd")
					+ ","
					+ rs.getLong("num_ccpt_ccpt")
					+ ","
					+ rs.getLong("mont_sold_ccpt")
					+ ","
					+ rs.getLong("mont_sdev_ccpt")
					+ ","
					+ rs.getLong("MONT_BLOC_CCPT")
					+ ","
					+ rs.getLong("MONT_BDEV_CCPT")
					+ ","
					+ rs.getLong("COD_DEV_DEV") + ",'" + dateJrn + "')");

		}
		System.out.println("Fin Insertion Trace Solde Devise !");
	}

	// Thread pour la migration du fichier prime

	/*********** Fix cpla ************/

	public List<String> getData(String fileName) throws IOException {

		List<String> lines = new ArrayList<String>();

		BufferedReader br;

		br = new BufferedReader(new FileReader(fileName));

		try {

			String l = br.readLine();
			// System.out.println(l);
			lines.add(l);
			while (l != null) {

				l = br.readLine();
				lines.add(l);
			}

		} finally {
			br.close();
		}

		return lines;
	}

	public void fixTaux() {

		jt = new JdbcTemplate(dataSource);

		List<String> lines = new ArrayList<String>();
		try {

			List<String> data = getData("d:/traceContratPlac310815.csv");
			File file = new File("d:/traceContratPlac310815-correction.csv");

			if (!file.exists())
				file.createNewFile();
			else
				file.delete();
			FileWriter writer = null;

			writer = new FileWriter(file);
			DecimalFormat df = new DecimalFormat("#.##");
			for (int i = 1; i < data.size(); i++) {
				if (data.get(i) != null) {
					String[] line = data.get(i).split(";");
					if (line.length > 0) {
						Long numSeq = 0l;
						// String req =
						// "select num_taui_arl from avanc_remb_liquid where NUM_SEQ_ARL="
						// + line[0];
						String req = "select num_taui_cpla from contrat_placement where num_seq_cpla="
								+ line[0];
						Double taux = (Double) jt.queryForObject(req,
								Double.class);
						req = "select num_marg_cpla from contrat_placement where num_seq_cpla="
								+ line[0];
						Double tauxMarge = (Double) jt.queryForObject(req,
								Double.class);
						if (taux != null) {
							line[13] = "" + df.format(taux);
							System.out.println(line);

						}
						if (tauxMarge != null) {
							line[25] = "" + df.format(tauxMarge);
						}
						System.out.println(line);
						String res = "";
						for (int j = 0; j < line.length; j++)
							res += (line[j] != null ? line[j] : "") + ";";
						lines.add(res);

					}
				}
			}

			for (int i = 0; i < lines.size(); i++) {
				writer.write(lines.get(i));
				writer.write("\n");
			}

			writer.close();

		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e.getMessage());
		}

	}

	/************** Migration compte etat OC NSI ***************************/

	public void insertConfrontationEtatCptOC_NSI(String fileName) {
		jt = new JdbcTemplate(dataSource);
		try {
			List<String> lignes = getData(fileName);
			for (int i = 0; i < lignes.size(); i++) {
				if (lignes.get(i) != null && lignes.get(i).length() > 0) {
					String[] data = lignes.get(i).split(";");
					Long strc = Long.valueOf(data[0].substring(0, 3));
					Long prd = Long.valueOf(data[0].substring(3, 7));
					Long numcpt = Long.valueOf(data[0].substring(7, 13));
					String etatNSI = data[1];
					String etatOC = "";
					if (data.length > 3)
						etatOC = data[3];
					System.out.println(lignes.get(i));

					SqlRowSet rs = jt
							.queryForRowSet("select count(*) from DISCORDANCE_CPT_NSI_OC where COD_STRC_STRC= "
									+ strc
									+ " and COD_PRD_PRD="
									+ prd
									+ "    and NUM_CCPT_CCPT=" + numcpt);
					if (rs.next()) {

						jt.execute("update DISCORDANCE_CPT_NSI_OC set "
								+ "COD_ETAT_NSI='" + etatNSI
								+ "', COD_ETAT_OC='" + etatOC
								+ "' where COD_STRC_STRC= " + strc
								+ " and COD_PRD_PRD=" + prd
								+ "    and NUM_CCPT_CCPT=" + numcpt);

					} else {

						jt.execute("insert into DISCORDANCE_CPT_NSI_OC(COD_STRC_STRC,COD_PRD_PRD,NUM_CCPT_CCPT,COD_ETAT_NSI,COD_ETAT_OC) "
								+ "values(" + strc + "," + prd + "," + numcpt

								+ ",'" + etatNSI + "','" + etatOC + "')");
					}
				}

			}
			System.out.println("Fin Insertion Trace Migration etat CPT OC !");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void updateCPTResilieInOC() {
		jt = new JdbcTemplate(dataSource);
		Context context = ContextHandler.getContext();
		ISearchEngine searchEngine = (SearchEngine) context
				.getBean("searchEngine");
		CRUDservice crudService = (CRUDservice) context.getBean("crudservice");

		String req = "";
		SqlRowSet srs = null;
		req = "select cod_prd_prd,num_ccpt_ccpt,cod_strc_strc "
				+ " from smile.DISCORDANCE_CPT_NSI_OC "
				+ " where  COD_ETAT_OC ='R' ";

		srs = jt.queryForRowSet(req);

		while (srs.next()) {
			System.out.println("Compte:" + srs.getLong("cod_prd_prd") + "/"
					+ srs.getLong("num_ccpt_ccpt"));

			ContratCptId cptId = new ContratCptId();
			cptId.setCodStrcStrc(srs.getLong("cod_strc_strc"));
			cptId.setCodPrdPrd(srs.getLong("cod_prd_prd"));
			cptId.setNumCcptCcpt(srs.getLong("num_ccpt_ccpt"));
			ContratCpt contratCpt = (ContratCpt) searchEngine.get(
					ContratCpt.class, cptId);
			contratCpt.setCodEtatCcpt("R");
			contratCpt.setDatCloCcpt(new Date());
			crudService.update(contratCpt);

		}
		System.out.println("Fin mise à jours blocage compte devise!");

	}

	public void createFichierCPTNotInOC() {
		jt = new JdbcTemplate(dataSource);
		try {
			Context context = ContextHandler.getContext();
			ISearchEngine searchEngine = (SearchEngine) context
					.getBean("searchEngine");

			String req = "";
			SqlRowSet srs = null;
			req = "select cod_prd_prd,num_ccpt_ccpt,cod_strc_strc "
					+ " from smile.DISCORDANCE_CPT_NSI_OC "
					+ " where  (COD_ETAT_OC is null or COD_ETAT_OC='I') and COD_ETAT_NSI='V' ";

			srs = jt.queryForRowSet(req);
			List<String> output = new ArrayList<String>();
			String res = "";
			while (srs.next()) {
				System.out.println("Compte:" + srs.getLong("cod_prd_prd") + "/"
						+ srs.getLong("num_ccpt_ccpt"));

				ContratCptId cptId = new ContratCptId();
				cptId.setCodStrcStrc(srs.getLong("cod_strc_strc"));
				cptId.setCodPrdPrd(srs.getLong("cod_prd_prd"));
				cptId.setNumCcptCcpt(srs.getLong("num_ccpt_ccpt"));
				ContratCpt contratCpt = (ContratCpt) searchEngine.get(
						ContratCpt.class, cptId);
				String cpt = StrHandler.lpad("" + cptId.getCodStrcStrc(), '0',
						3)
						+ StrHandler.lpad("" + cptId.getCodPrdPrd(), '0', 4)
						+ StrHandler.lpad("" + cptId.getNumCcptCcpt(), '0', 6);
				String relation = contratCpt.getNomIntiCcpt().length() > 29 ? contratCpt
						.getNomIntiCcpt().substring(0, 29) : contratCpt
						.getNomIntiCcpt();
				relation = StrHandler.rpad(relation.trim(), ' ', 29);
				String rue = StrHandler.lpad("", ' ', 36);
				String ville = StrHandler.lpad("", ' ', 19);
				String codePostal = StrHandler.lpad("", '0', 5);

				Personne personne = contratCpt.getClient().getPersonne();
				Adresse adr = personne.getAdresseProf();
				if (adr == null)
					adr = personne.getAdresseResid();
				if (adr != null) {
					rue = adr.toString().length() > 36 ? adr.toString()
							.substring(0, 36) : adr.toString();
					if (adr.getVille() != null)
						ville = adr.getVille().length() > 19 ? adr.getVille()
								.substring(0, 19) : StrHandler.lpad(
								adr.getVille(), ' ', 19);
					if (adr.getCodCpCp() != null)
						codePostal = StrHandler.lpad("" + adr.getCodCpCp(),
								'0', 5);
				}

				output.add(cpt + relation
						+ StrHandler.rpad(rue.trim(), ' ', 36) + ville
						+ codePostal);
				output.add("\n");

			}
			if (output.size() > 0)
				if (output.get(output.size() - 1).equals("\n"))
					output.remove(output.size() - 1);
			for (int j = 0; j < output.size(); j++) {
				// System.out.println(output.get(j).length() + ":" +
				// output.get(j));
				res += output.get(j);

			}
			File file = new File("d:" + File.separatorChar + "ETATCPTI");
			if (!file.exists())
				file.createNewFile();

			FileUtils.writeStringToFile(file, res);
			System.out.println("Fin creation fichier discordance!");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
