package com.bna.smile.model.histSoldePascale.dao;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import com.bna.commun.model.ContratCpt;
import com.bna.commun.model.ContratCptId;
import com.bna.commun.model.Devise;
import com.bna.commun.model.HistTrancheServi;
import com.bna.commun.model.Personnel;
import com.bna.commun.model.TraceBlocageMontantContrat;
import com.bna.commun.util.ContextHandler;
import com.bna.commun.util.DateHandler;
import com.bna.commun.util.StrHandler;
import com.bna.commun.vo.PrimitiveVO;
import com.bna.smile.model.domainecommun.service.CRUDservice;
import com.bna.smile.web.commun.controller.UtilCtr;
import com.oxia.fwk.context.Context;
import com.oxia.fwk.core.ISearchEngine;
import com.oxia.fwk.searchengine.SearchEngine;

public class HistSoldePascaleDAO implements Serializable {

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
	public HistSoldePascaleDAO() {
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

	// free days
	public boolean isFreeDay(Date jour) {
		jt = new JdbcTemplate(dataSource);
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		String sd = sdf.format(jour);
		Long count =
				(Long) jt.queryForObject(
						"select count(*) from free_days_calendar  where free_days_calendar.day=EXTRACT(DAY FROM  to_date('"
								+ sd + "','dd/MM/yyyy')) and free_days_calendar.month=EXTRACT(MONTH FROM  to_date('"
								+ sd + "','dd/MM/yyyy')) and free_days_calendar.year=EXTRACT(YEAR FROM  to_date('" + sd
								+ "','dd/MM/yyyy'))", Long.class);
		if (count > 1)
			return true;
		return false;

	}

	public boolean isWorkDay(Date d) {
		Calendar date = Calendar.getInstance();
		date.setTime(d);

		if (date.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY || date.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY)
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

	List<String> linesMig = new ArrayList<String>();

	// creation d'une ligne du fichier

	// formater un montant selon la structure du fichier
	private String formatMontant(Long mnt, Long nbr) {
		if (mnt.equals(0L))
			return "";
		else
			return StrHandler.formatMontant(mnt, nbr);
	}

	private String formatNombre(Long nbr) {
		if (nbr.equals(0L))
			return "";
		else
			return "" + nbr;
	}

	// Liste des structures
	public List<PrimitiveVO> getListAgences() {
		jt = new JdbcTemplate(dataSource);

		String requete =
				"select cod_strc_strc,lib_strc_strc from structure where  cod_tstr_tstr in (6,1) order by cod_strc_strc asc ";
		List<PrimitiveVO> res = new ArrayList<PrimitiveVO>();
		SqlRowSet srs = null;
		srs = jt.queryForRowSet(requete);
		try {

			while (srs.next()) {
				PrimitiveVO vo = new PrimitiveVO();
				vo.setVLong(srs.getLong("cod_strc_strc"));
				vo.setVString(srs.getString("lib_strc_strc").toUpperCase());
				res.add(vo);

			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return res;

	}

	/****************************************************/
	public List<PrimitiveVO> readCompte(String fileName) throws IOException {

		List<String> lines = new ArrayList<String>();
		List<PrimitiveVO> comptes = new ArrayList<PrimitiveVO>();

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
			if (lines.get(i) != null && lines.get(i).length() >= 26) {
				List<String> mvts = UtilCtr.splitInChunks(lines.get(i), 26);
				Iterator<String> itr = mvts.iterator();

				while (itr.hasNext()) {
					String mvt = itr.next();
					PrimitiveVO cpt = new PrimitiveVO();
					if (mvt != null && !mvt.trim().isEmpty() && mvt.startsWith("120")) {
						try {
							cpt.setVString(mvt.substring(0, 13));
							cpt.setVLong(Long.valueOf(mvt.substring(13, 25)));
							boolean CR_DB = mvt.substring(25, 26).equals("+");
							cpt.setVBool(CR_DB);
							comptes.add(cpt);
							System.out.println(cpt.getVString() + "/" + cpt.getVLong() + "/" + cpt.isVBool() + ":"
									+ mvt.substring(25, 26));
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				}

			}
		}

		return comptes;
	}

	public List<PrimitiveVO> readCompteDinars(String fileName, String structure) throws IOException {

		List<String> lines = new ArrayList<String>();
		List<PrimitiveVO> comptes = new ArrayList<PrimitiveVO>();

		BufferedReader br;

		br = new BufferedReader(new FileReader(fileName));

		try {
			String l = "";
			while ((l = br.readLine()) != null) {

				if (l != null && !l.isEmpty())
					if (!l.startsWith("9999"))
						lines.add(l);
					else
						break;

			}

		} finally {
			br.close();
		}

		for (int i = 0; i < lines.size(); i++) {
			if (lines.get(i) != null && lines.get(i).length() >= 26) {
				List<String> mvts = UtilCtr.splitInChunks(lines.get(i), 26);
				Iterator<String> itr = mvts.iterator();

				while (itr.hasNext()) {
					String mvt = itr.next();
					PrimitiveVO cpt = new PrimitiveVO();
					if (mvt != null && !mvt.trim().isEmpty() && mvt.startsWith(structure)) {
						try {
							Long.valueOf(mvt.substring(0, 13));
							cpt.setVString(mvt.substring(0, 13));
							cpt.setVLong(Long.valueOf(mvt.substring(13, 25)));
							boolean CR_DB = mvt.substring(25, 26).equals("+");
							cpt.setVBool(CR_DB);
							comptes.add(cpt);
							System.out.println(cpt.getVString() + "/" + cpt.getVLong() + "/" + cpt.isVBool() + ":"
									+ mvt.substring(25, 26));
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				}

			}
		}

		return comptes;
	}

	public List<PrimitiveVO> readCompteDevise(String fileName, String structure) throws IOException {

		List<String> lines = new ArrayList<String>();
		List<PrimitiveVO> comptes = new ArrayList<PrimitiveVO>();

		BufferedReader br;

		br = new BufferedReader(new FileReader(fileName));

		try {

			String l = "";
			// System.out.println(l);
			while ((l = br.readLine()) != null) {

				if (l != null && !l.isEmpty() && l.startsWith("9999"))
					break;
			}
			System.out.println("Starting reading Devise");
			while ((l = br.readLine()) != null) {

				if (l != null && !l.isEmpty()) {
					lines.add(l);
				}
			}

		} finally {
			br.close();
		}

		for (int i = 0; i < lines.size(); i++) {
			if (lines.get(i) != null && lines.get(i).length() >= 26) {
				List<String> mvts = UtilCtr.splitInChunks(lines.get(i), 26);
				Iterator<String> itr = mvts.iterator();

				while (itr.hasNext()) {
					String mvt = itr.next();
					PrimitiveVO cpt = new PrimitiveVO();
					if (mvt != null && !mvt.trim().isEmpty() && mvt.startsWith(structure)) {
						try {
							Long.valueOf(mvt.substring(0, 13));

							cpt.setVString(mvt.substring(0, 13));
							cpt.setVLong(Long.valueOf(mvt.substring(13, 25)));
							boolean CR_DB = mvt.substring(25, 26).equals("+");
							cpt.setVBool(CR_DB);
							comptes.add(cpt);
							System.out.println(cpt.getVString() + "/" + cpt.getVLong() + "/" + cpt.isVBool() + ":"
									+ mvt.substring(25, 26));
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
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

	private void createCompte(String numCpt, Long mnt_devise, boolean signeInverse) {
		jt = new JdbcTemplate(dataSource);
		Context context = ContextHandler.getContext();
		ISearchEngine searchEngine = (SearchEngine) context.getBean("searchEngine");
		ContratCptId cptId = new ContratCptId();
		cptId.setCodStrcStrc(Long.valueOf(numCpt.substring(0, 3)));
		cptId.setCodPrdPrd(Long.valueOf(numCpt.substring(3, 7)));
		cptId.setNumCcptCcpt(Long.valueOf(numCpt.substring(7, 13)));
		ContratCpt contratCpt = (ContratCpt) searchEngine.get(ContratCpt.class, cptId);
		if (signeInverse) {
			mnt_devise = -mnt_devise;
		}
		if (contratCpt != null) {
			long montantConverti = 0;
			double coursFixe = 0;
			if (!contratCpt.getDevise().getCodDevDev().equals(Long.valueOf(788))) {
				coursFixe =
						UtilCtr.getCoursFixe(DateHandler.strToDate("08/01/2015"), contratCpt.getDevise().getCodDevDev());

				montantConverti =
						UtilCtr.changeDeviseToTND(mnt_devise, contratCpt.getDevise().getNbrDecDev(), contratCpt
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
					+ "'"
					+ ","
					+ contratCpt.getDevise().getCodDevDev() + ")");
		} else {
			System.out.println("Contrat Inexistant:" + numCpt);
			String etat = "";
			jt.execute("insert into CPT_DEVISE_MIG(COD_STRC_STRC,COD_PRD_PRD,NUM_CCPT_CCPT,SOLDE_DEVISE) values("
					+ cptId.getCodStrcStrc() + "," + cptId.getCodPrdPrd() + "," + cptId.getNumCcptCcpt() + ","
					+ mnt_devise + ")");
		}

	}

	private void updateSoldeDinars(String numCpt, Long mnt_dinars, boolean signeInverse) {
		jt = new JdbcTemplate(dataSource);
		try {
			ContratCptId cptId = new ContratCptId();
			cptId.setCodStrcStrc(Long.valueOf(numCpt.substring(0, 3)));
			cptId.setCodPrdPrd(Long.valueOf(numCpt.substring(3, 7)));
			cptId.setNumCcptCcpt(Long.valueOf(numCpt.substring(7, 13)));

			if (signeInverse) {
				mnt_dinars = -mnt_dinars;
			}

			jt.execute("update CPT_DEVISE_MIG set " + "SOLDE_DINARS=" + mnt_dinars + " where cod_strc_strc="
					+ cptId.getCodStrcStrc() + " and cod_prd_prd=" + cptId.getCodPrdPrd() + " and num_ccpt_ccpt="
					+ cptId.getNumCcptCcpt());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void updateHistSoldePascal(String numCpt, Long mnt, boolean signeInverse, String dateTrace, String devise) {
		jt = new JdbcTemplate(dataSource);
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		// Context context = ContextHandler.getContext();
		// ISearchEngine searchEngine = (SearchEngine) context.getBean("searchEngine");
		try {
			ContratCptId cptId = new ContratCptId();
			cptId.setCodStrcStrc(Long.valueOf(numCpt.substring(0, 3)));
			cptId.setCodPrdPrd(Long.valueOf(numCpt.substring(3, 7)));
			cptId.setNumCcptCcpt(Long.valueOf(numCpt.substring(7, 13)));

			if (signeInverse) {
				mnt = -mnt;
			}
			if (devise.equals("788")) {

				jt.execute("update HIST_SOLDE_CONTRAT_CPT set " + "MNT_SLD_PASCAL_DIN=" + mnt + ",DAT_TRC_PASCAL='"
						+ sdf.format(new Date()) + "'" + " where cod_strc_strc=" + cptId.getCodStrcStrc()
						+ " and cod_prd_prd=" + cptId.getCodPrdPrd() + " and num_ccpt_ccpt=" + cptId.getNumCcptCcpt()
						+ " and trunc(DAT_JRN_HSCC)='" + dateTrace + "'");
				jt.execute("update HIST_SOLDE_CONTRAT_CPT set " + " MNT_DIF_DIN=MONT_SOLD_CCPT-MNT_SLD_PASCAL_DIN"
						+ " where cod_strc_strc=" + cptId.getCodStrcStrc() + " and cod_prd_prd=" + cptId.getCodPrdPrd()
						+ " and num_ccpt_ccpt=" + cptId.getNumCcptCcpt() + " and trunc(DAT_JRN_HSCC)='" + dateTrace
						+ "'");

			} else {
				jt.execute("update HIST_SOLDE_CONTRAT_CPT set " + "MNT_SLD_PASCAL_DEV=" + mnt + ",DAT_TRC_PASCAL='"
						+ sdf.format(new Date()) + "'" + " where cod_strc_strc=" + cptId.getCodStrcStrc()
						+ " and cod_prd_prd=" + cptId.getCodPrdPrd() + " and num_ccpt_ccpt=" + cptId.getNumCcptCcpt()
						+ " and trunc(DAT_JRN_HSCC)='" + dateTrace + "'");
				jt.execute("update HIST_SOLDE_CONTRAT_CPT set " + " MNT_DIF_DEV=MONT_SDEV_CCPT-MNT_SLD_PASCAL_DEV"
						+ " where cod_strc_strc=" + cptId.getCodStrcStrc() + " and cod_prd_prd=" + cptId.getCodPrdPrd()
						+ " and num_ccpt_ccpt=" + cptId.getNumCcptCcpt() + " and trunc(DAT_JRN_HSCC)='" + dateTrace
						+ "'");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void insertDetailBlocage(String line, Long strc) {
		jt = new JdbcTemplate(dataSource);
		Context context = ContextHandler.getContext();
		ISearchEngine searchEngine = (SearchEngine) context.getBean("searchEngine");
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
			ContratCpt contratCpt = (ContratCpt) searchEngine.get(ContratCpt.class, cptId);
			if (contratCpt != null && !mntBlocage.contains("-")) {

				TraceBlocageMontantContrat blocageMontantContrat = new TraceBlocageMontantContrat();
				blocageMontantContrat.setContratCpt(contratCpt);
				if (!dateBlocage.trim().isEmpty())
					blocageMontantContrat.setDatDebBloc(sdf.parse(dateBlocage));
				if (!dateDeblocage.trim().isEmpty())
					blocageMontantContrat.setDatFinBloc(sdf.parse(dateDeblocage));
				blocageMontantContrat.setMntBlocBloc(Long.valueOf(mntBlocage));
				blocageMontantContrat.setDatTimeOp(new Date());
				blocageMontantContrat.setMotifBlocage(motifBlocage);
				Personnel persBloc = (Personnel) searchEngine.get(Personnel.class, userBloc);
				Personnel persDebBloc = (Personnel) searchEngine.get(Personnel.class, userDeb);

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
						+ cptId.getNumCcptCcpt() + ",'" + line + "')");
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void updateCompteDevise(Long strc) {
		jt = new JdbcTemplate(dataSource);
		Context context = ContextHandler.getContext();
		ISearchEngine searchEngine = (SearchEngine) context.getBean("searchEngine");
		CRUDservice crudService = (CRUDservice) context.getBean("crudservice");

		String req = "";
		SqlRowSet srs = null;
		req =
				"select cod_strc_strc,cod_prd_prd,num_ccpt_ccpt,solde_devise,solde_dinars,cod_dev_dev "
						+ " from cpt_devise_mig "
						+ " where cod_dev_dev!=788"
						+ " and etat_compte='V'"
						// + " and solde_devise!=0"
						+ " and cod_prd_prd in (select  cod_prd_prd from produit where cod_gfam_gp='04' and cod_fam_fam='01' and cod_sfam_sfp='01')"
						+ " or cod_prd_prd=147 and (cod_dev_dev!=788 " + " and etat_compte='V'  ) "
						+ " or cod_prd_prd=421 and (cod_dev_dev!=788 " + " and etat_compte='V'  ) "
						+ " order by cod_prd_prd";
		srs = jt.queryForRowSet(req);

		while (srs.next()) {
			if (strc.equals(srs.getLong("cod_strc_strc"))) {
				System.out.println("Compte:" + srs.getLong("cod_prd_prd") + "/" + srs.getLong("num_ccpt_ccpt"));

				ContratCptId cptId = new ContratCptId();
				cptId.setCodStrcStrc(srs.getLong("cod_strc_strc"));
				cptId.setCodPrdPrd(srs.getLong("cod_prd_prd"));
				cptId.setNumCcptCcpt(srs.getLong("num_ccpt_ccpt"));
				ContratCpt contratCpt = (ContratCpt) searchEngine.get(ContratCpt.class, cptId);
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
		ISearchEngine searchEngine = (SearchEngine) context.getBean("searchEngine");
		CRUDservice crudService = (CRUDservice) context.getBean("crudservice");

		String req = "";
		SqlRowSet srs = null;
		req =
				"select cpt.cod_strc_strc,cpt.cod_prd_prd,cpt.num_ccpt_ccpt,sum(db.mnt_bloc_bloc) as blocage from trace_blocage_montant_contrat db,contrat_cpt cpt "
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
			System.out.println("Compte:" + srs.getLong("cod_prd_prd") + "/" + srs.getLong("num_ccpt_ccpt"));

			ContratCptId cptId = new ContratCptId();
			cptId.setCodStrcStrc(srs.getLong("cod_strc_strc"));
			cptId.setCodPrdPrd(srs.getLong("cod_prd_prd"));
			cptId.setNumCcptCcpt(srs.getLong("num_ccpt_ccpt"));
			ContratCpt contratCpt = (ContratCpt) searchEngine.get(ContratCpt.class, cptId);
			contratCpt.setMontBdevCcpt(srs.getLong("blocage"));
			crudService.update(contratCpt);

		}
		System.out.println("Fin mise à jours blocage compte devise!");

	}

	public void fixBlocageCompteDevise(Long strc) {
		jt = new JdbcTemplate(dataSource);
		Context context = ContextHandler.getContext();
		ISearchEngine searchEngine = (SearchEngine) context.getBean("searchEngine");
		CRUDservice crudService = (CRUDservice) context.getBean("crudservice");

		String req = "";
		SqlRowSet srs = null;
		req =
				"select cod_prd_prd,num_ccpt_ccpt,mont_bdev_ccpt,cod_dev_dev " + " from smile.contrat_cpt "
						+ " where  cod_strc_strc=" + strc + " " + " and cod_dev_dev !=788 "
						+ " and mont_bdev_ccpt is not null and mont_bdev_ccpt >0";

		srs = jt.queryForRowSet(req);

		while (srs.next()) {
			System.out.println("Compte:" + srs.getLong("cod_prd_prd") + "/" + srs.getLong("num_ccpt_ccpt"));
			Long cod_dev_dev = srs.getLong("cod_dev_dev");
			long montantConverti = 0;
			double coursFixe = 0;
			Long blocDevise = srs.getLong("mont_bdev_ccpt");
			Devise devise = (Devise) searchEngine.get(Devise.class, cod_dev_dev);
			coursFixe = UtilCtr.getCoursFixe(DateHandler.strToDate("30/01/2015"), devise.getCodDevDev());
			montantConverti =
					UtilCtr.changeDeviseToTND(blocDevise, devise.getNbrDecDev(), devise.getNbrUnitDev(), coursFixe);
			ContratCptId cptId = new ContratCptId();
			cptId.setCodStrcStrc(strc);
			cptId.setCodPrdPrd(srs.getLong("cod_prd_prd"));
			cptId.setNumCcptCcpt(srs.getLong("num_ccpt_ccpt"));
			ContratCpt contratCpt = (ContratCpt) searchEngine.get(ContratCpt.class, cptId);
			contratCpt.setMontBlocCcpt(montantConverti);
			crudService.update(contratCpt);

		}
		System.out.println("Fin mise à jours blocage compte devise!");

	}

	public void updateContreValeurBlocageDevise(Long strc) {
		jt = new JdbcTemplate(dataSource);
		Context context = ContextHandler.getContext();
		ISearchEngine searchEngine = (SearchEngine) context.getBean("searchEngine");
		CRUDservice crudService = (CRUDservice) context.getBean("crudservice");

		String req = "";
		SqlRowSet srs = null;
		req =
				"select cpt.cod_strc_strc,cpt.cod_prd_prd,cpt.num_ccpt_ccpt  ,db.MNT_BLOC_BLOC ,db.NUM_SEQ_TBC,cpt.cod_dev_dev from trace_blocage_montant_contrat db,contrat_cpt cpt "
						+ " where cpt.cod_dev_dev!=788 "
						+ " and cpt.cod_strc_strc="
						+ strc
						+ " and db.dat_mig_bloc is not null "
						+ " and cpt.cod_prd_prd=db.cod_prd_prd "
						+ " and cpt.num_ccpt_ccpt=db.num_ccpt_ccpt "
						+ " and (db.dat_fin_bloc is null or trunc(db.dat_fin_bloc) > trunc(sysdate)) "
						+ " and db.mnt_bloc_bloc is not null  " + " order by cod_prd_prd";
		srs = jt.queryForRowSet(req);

		while (srs.next()) {
			System.out.println("Compte:" + srs.getLong("cod_prd_prd") + "/" + srs.getLong("num_ccpt_ccpt"));

			Long mntBloc_dev = srs.getLong("MNT_BLOC_BLOC");
			Long cod_dev_dev = srs.getLong("cod_dev_dev");
			Long id_trace = srs.getLong("NUM_SEQ_TBC");
			long montantConverti = 0;
			double coursFixe = 0;
			Devise devise = (Devise) searchEngine.get(Devise.class, cod_dev_dev);
			coursFixe = UtilCtr.getCoursFixe(DateHandler.strToDate("21/01/2015"), devise.getCodDevDev());

			montantConverti =
					UtilCtr.changeDeviseToTND(mntBloc_dev, devise.getNbrDecDev(), devise.getNbrUnitDev(), coursFixe);

			jt.execute("update TRACE_BLOCAGE_MONTANT_CONTRAT set MNT_BLOCD_BLOC=" + montantConverti
					+ "  where NUM_SEQ_TBC=" + id_trace);

		}
		System.out.println("Fin mise à jours contre valeur blocage mnt compte devise!");

	}

	public void insertCompteMig(String fileName) {
		try {
			jt = new JdbcTemplate(dataSource);

			jt.execute("delete from  CPT_DEVISE_MIG");
			List<PrimitiveVO> comptes = readCompte(fileName);
			List<String> liste = new ArrayList<String>();
			List<String> listeRep = new ArrayList<String>();
			System.out.println("Compte à inserer:" + comptes.size());
			for (int i = 0; i < comptes.size(); i++) {
				if (!liste.contains(comptes.get(i).getVString())) {
					liste.add(comptes.get(i).getVString());
					System.out.println(i);

					createCompte(comptes.get(i).getVString(), comptes.get(i).getVLong(), comptes.get(i).isVBool());
				} else {
					System.out.println("Contrat repeté:" + comptes.get(i).getVString() + ":Solde:"
							+ comptes.get(i).getVLong());

					listeRep.add(comptes.get(i).getVString());
				}
			}
			System.out.println("Nombre Compte repeté:" + listeRep.size());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void migBlocageCompte(String fileName, Long strc) {
		try {
			jt = new JdbcTemplate(dataSource);

			List<String> blocs = readCompteBlocage(fileName);
			jt.execute("delete from  TRACE_BLOCAGE_MONTANT_CONTRAT where cod_strc_strc=" + strc
					+ " and dat_mig_bloc is not null");
			jt.execute("delete from  REJET_MIG_BLOCAGE_MNT where cod_strc_strc=" + strc);

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

			// jt.execute("delete from  CPT_DEVISE_MIG");
			List<PrimitiveVO> comptes = readCompte(fileName);
			List<String> liste = new ArrayList<String>();
			List<String> listeRep = new ArrayList<String>();
			System.out.println("Compte à inserer:" + comptes.size());
			for (int i = 0; i < comptes.size(); i++) {
				if (!liste.contains(comptes.get(i).getVString())) {
					liste.add(comptes.get(i).getVString());
					System.out.println(i);

					updateSoldeDinars(comptes.get(i).getVString(), comptes.get(i).getVLong(), comptes.get(i).isVBool());
				} else {
					System.out.println("Contrat repeté:" + comptes.get(i).getVString() + ":Solde:"
							+ comptes.get(i).getVLong());

					listeRep.add(comptes.get(i).getVString());
				}
			}
			System.out.println("Nombre Compte repeté:" + listeRep.size());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void insertHistSoldePascal(String fileName, String dateTrace, String structure) {
		try {
			jt = new JdbcTemplate(dataSource);

			// jt.execute("delete from  CPT_DEVISE_MIG");
			List<PrimitiveVO> comptesDinars = readCompteDinars(fileName, structure);
			List<PrimitiveVO> comptesDevise = readCompteDevise(fileName, structure);

			List<String> liste = new ArrayList<String>();
			List<String> listeRep = new ArrayList<String>();
			System.out.println("Compte Dinars à inserer:" + comptesDinars.size());
			for (int i = 0; i < comptesDinars.size(); i++) {
				if (!liste.contains(comptesDinars.get(i).getVString())) {
					liste.add(comptesDinars.get(i).getVString());

					updateHistSoldePascal(comptesDinars.get(i).getVString(), comptesDinars.get(i).getVLong(),
							comptesDinars.get(i).isVBool(), dateTrace, "788");
				} else {
					System.out.println("Contrat Dinars repeté:" + comptesDinars.get(i).getVString() + ":Solde:"
							+ comptesDinars.get(i).getVLong());

					listeRep.add(comptesDinars.get(i).getVString());
				}
			}
			System.out.println("Nombre Compte Dinars repeté:" + listeRep.size());
			liste = new ArrayList<String>();
			listeRep = new ArrayList<String>();
			System.out.println("Compte Devise à inserer:" + comptesDevise.size());
			for (int i = 0; i < comptesDevise.size(); i++) {
				if (!liste.contains(comptesDevise.get(i).getVString())) {
					liste.add(comptesDevise.get(i).getVString());

					updateHistSoldePascal(comptesDevise.get(i).getVString(), comptesDevise.get(i).getVLong(),
							comptesDevise.get(i).isVBool(), dateTrace, "978");
				} else {
					System.out.println("Contrat Devise repeté:" + comptesDevise.get(i).getVString() + ":Solde:"
							+ comptesDevise.get(i).getVLong());

					listeRep.add(comptesDevise.get(i).getVString());
				}
			}
			System.out.println("Nombre Compte Devise repeté:" + listeRep.size());

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("Insertion Hist Solde Pascal terminée");

	}

	// ******CCER*********/

	public void insertTraceCCER(String fileName, String dateEch) {
		jt = new JdbcTemplate(dataSource);
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yy");
		System.out.println("Starting ....");

		// jt.execute("delete from  CCER.RATIO_CPLA where dat_eche_cpla='" + dateEch + "'");
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
			System.out.println(DateHandler.strToDate(dateEchCpla).compareTo(DateHandler.strToDate(dateEch)) > 0);
			System.out.println(DateHandler.strToDate(dateEchCpla).compareTo(nextmonth) < 0);
			System.out.println(DateHandler.strToDate(dateEchCpla).compareTo(nextmonth) == 0);

			if (DateHandler.strToDate(DateHandler.dateToStr(dateCpla)).compareTo(DateHandler.strToDate(dateEch)) > 0
					&& (DateHandler.strToDate(DateHandler.dateToStr(dateCpla)).compareTo(nextmonth) < 0 || DateHandler
							.strToDate(DateHandler.dateToStr(dateCpla)).compareTo(nextmonth) == 0))
				echValide = true;
			try {
				if ((codEta.equals("V") || codEta.equals("VC") || codEta.equals("VT")) && echValide)
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
							+ "," + prd + "," + numCpt + "," + prdCpla + ",'" + sdf.format(new Date()) + "')");
			}

			catch (Exception e) {
				e.printStackTrace();
			}
		}
		System.out.println(" Fin extraction CCER!");

	}

	public void insertTraceSoldeDevise(String dateJrn, Long codStrc) {
		jt = new JdbcTemplate(dataSource);
		jt.execute("delete from  TRACE_SOLDE_DEVISE where cod_strc_strc=" + codStrc + " and DAT_TRC_SOLD='" + dateJrn
				+ "'");
		String req =
				"select cod_strc_strc,cod_prd_prd,num_ccpt_ccpt,mont_sold_ccpt,mont_sdev_ccpt,mont_bloc_ccpt,mont_bdev_ccpt,cod_dev_dev from contrat_cpt "
						+ " where cod_dev_dev!=788" + " and cod_etat_ccpt='V' and cod_strc_strc=" + codStrc;

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
					+ rs.getLong("MONT_BDEV_CCPT") + "," + rs.getLong("COD_DEV_DEV") + ",'" + dateJrn + "')");

		}
		System.out.println("Fin Insertion Trace Solde Devise !");
	}

	// Thread pour la migration du fichier prime
	class Exec extends Thread {

		int minI;
		int maxI;
		Long annee;
		Long sequenceStart;
		SimpleDateFormat fd = new SimpleDateFormat("dd MM yyyy");

		List<String> fichier;

		public Exec(int i, int j, List<String> l, Long annee, Long seqStart) {
			this.minI = i;
			this.maxI = j;
			fichier = l;
			sequenceStart = seqStart;
			this.annee = annee;

		}

		// Thread d'importaion de fichier prime_2012
		public void run() {

			Context context = ContextHandler.getContext();
			CRUDservice crudService = (CRUDservice) context.getBean("crudservice");

			// System.out.println("File:" + fichier.size());

			for (int i = minI; i < maxI; i++) {
				if (fichier.get(i) != null) {
					HistTrancheServi histTrancheServi = new HistTrancheServi();
					String[] line = fichier.get(i).split("\\*");
					histTrancheServi.setNumSeqTrch(sequenceStart);
					String cpt = line[0];

					histTrancheServi.setCodStrcStrc(Long.valueOf(cpt.substring(0, 3)));
					histTrancheServi.setCodPrdPrd(Long.valueOf(cpt.substring(3, 7)));
					histTrancheServi.setNumCcptCcpt(Long.valueOf(cpt.substring(7, 13)));
					histTrancheServi.setAnnTranchPrim(annee);
					String dateOuverture = line[1];

					try {
						if (!dateOuverture.isEmpty())
							histTrancheServi.setDatOuvCpt(fd.parse(dateOuverture));
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					String cat = line[2].trim();
					histTrancheServi.setCodCatCat(cat);
					String numTrch = line[3].trim();
					histTrancheServi.setNumTrchServ(Long.valueOf(numTrch));
					String soldeOpe = line[4].trim().replace(" ", "");
					if (!soldeOpe.isEmpty())
						histTrancheServi.setSoldCptTrch(Long.valueOf(soldeOpe));
					else
						histTrancheServi.setSoldCptTrch(Long.valueOf(0));
					String datOpe = line[5].trim();
					try {
						if (!datOpe.isEmpty())
							histTrancheServi.setDatOpeTrch(fd.parse(datOpe));
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					System.out.println(cpt + "/" + i);
					crudService.create(histTrancheServi);
					sequenceStart++;
				}
			}
			System.out.println("Fin importation :" + annee);
		}
	}
}
