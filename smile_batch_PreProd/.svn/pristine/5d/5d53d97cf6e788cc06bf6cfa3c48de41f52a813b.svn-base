package com.bna.smile.model.assVieEpargneEtude.dao;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Serializable;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.sql.DataSource;

import org.apache.log4j.Logger;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import com.bna.commun.model.Categorie;
import com.bna.commun.model.ContratCpt;
import com.bna.commun.model.ContratCptId;
import com.bna.commun.model.ForcageTrancheServi;
import com.bna.commun.model.HistTrancheNonServi;
import com.bna.commun.model.HistTrancheServi;
import com.bna.commun.util.ContextHandler;
import com.bna.commun.util.StrHandler;
import com.bna.commun.vo.PrimitiveVO;
import com.bna.smile.model.assVieEpargneEtude.model.AssVieEpargneVo;
import com.bna.smile.model.assVieEpargneEtude.model.ContratEpargneEtudeVo;
import com.bna.smile.model.assVieEpargneEtude.model.TraceAssuranceFaiezVo;
import com.bna.smile.model.domainecommun.service.CRUDservice;
import com.oxia.fwk.context.Context;

public class AssVieEpargneDAO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	protected String sqlQuery;
	protected JdbcTemplate jt;
	protected DataSource dataSource;
	private static final Logger logger = Logger.getLogger(AssVieEpargneDAO.class);
	/***************************************/
	public AssVieEpargneDAO() {
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

		Long numeroSequence = (Long) jt.queryForObject("select NUM_SEQ_HIST_TRCH.NEXTVAL from dual ", Long.class);
		Long lastSeq = (Long) jt.queryForObject("select max(NUM_SEQ_TRCH) from HIST_TRANCHE_SERVI ", Long.class);
		if (numeroSequence <= lastSeq) {
			jt.execute("alter sequence NUM_SEQ_HIST_TRCH increment by +" + lastSeq + 1);
			numeroSequence = (Long) jt.queryForObject("select NUM_SEQ_HIST_TRCH.NEXTVAL from dual ", Long.class);
			jt.execute("alter sequence NUM_SEQ_HIST_TRCH increment by 1");
		}

		numeroSequence = (Long) jt.queryForObject("select NUM_SEQ_HIST_TRCH.NEXTVAL from dual ", Long.class);
		// System.out.println(numeroSequence);
		return numeroSequence;
	}

	// free days
	public boolean isFreeDay(Date jour) {
		jt = new JdbcTemplate(dataSource);
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		String sd = sdf.format(jour);
		Long count = (Long) jt.queryForObject(
				"select count(*) from free_days_calendar  where free_days_calendar.day=EXTRACT(DAY FROM  to_date('" + sd
						+ "','dd/MM/yyyy')) and free_days_calendar.month=EXTRACT(MONTH FROM  to_date('" + sd
						+ "','dd/MM/yyyy')) and free_days_calendar.year=EXTRACT(YEAR FROM  to_date('" + sd
						+ "','dd/MM/yyyy'))",
				Long.class);
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

	// selectionner le montant de la tranche selon la categorie et le numero de tranche
	private Long getMontTrache(String codCat, Long numTranche) {
		String v_Cat = "" + codCat.charAt(0);
		String v_regim = codCat.substring(1, (codCat.length()));
		// System.out.println(v_Cat + "/" + v_regim);
		Long mnt = null;
		jt = new JdbcTemplate(dataSource);
		String req = "";
		SqlRowSet srs = null;
		req = "select COD_CORR_CAT from categorie  where COD_TYP_CAT is not null and COD_TYP_CAT='A' and "
				+ " COD_CAT_CAT='" + v_Cat + "' and COD_RGM_RGM=" + Long.valueOf(v_regim);
		// System.out.println(req);
		srs = jt.queryForRowSet(req);
		while (srs.next())
			if (srs.getString("COD_CORR_CAT") != null)
				v_Cat = srs.getString("COD_CORR_CAT");

		if (numTranche == 1L || numTranche == 0L) {
			req = "select MONT_TRCH1_CAT from categorie  where " + " COD_CAT_CAT='" + v_Cat + "' and COD_RGM_RGM="
					+ Long.valueOf(v_regim);
			// System.out.println(req);
			mnt = jt.queryForLong(req);
		}
		if (numTranche == 2L) {
			req = "select MONT_TRCH2_CAT from categorie  where " + " COD_CAT_CAT='" + v_Cat + "' and COD_RGM_RGM="
					+ Long.valueOf(v_regim);
			// System.out.println(req);
			mnt = jt.queryForLong(req);
		}
		if (numTranche == 3L) {
			req = "select MONT_TRCH3_CAT from categorie  where " + " COD_CAT_CAT='" + v_Cat + "' and COD_RGM_RGM="
					+ Long.valueOf(v_regim);
			// System.out.println(req);
			mnt = jt.queryForLong(req);
		}

		return mnt;
	}

	// calculer la somme des montants de la tranche selon la categorie et le numero de tranche
	private Long getTotalMontTrache(String codCat, Long numTranche) {
		String v_Cat = "" + codCat.charAt(0);
		String v_regim = codCat.substring(1, (codCat.length()));

		Long mnt = null;
		jt = new JdbcTemplate(dataSource);
		String req = "";
		try {
			SqlRowSet srs = null;
			req = "select COD_CORR_CAT from categorie  where COD_TYP_CAT is not null and COD_TYP_CAT='A' and "
					+ " COD_CAT_CAT='" + v_Cat + "' and COD_RGM_RGM=" + Long.valueOf(v_regim);
			// System.out.println(req);
			srs = jt.queryForRowSet(req);
			while (srs.next())
				if (srs.getString("COD_CORR_CAT") != null)
					v_Cat = srs.getString("COD_CORR_CAT");
			if (numTranche == 1L) {

				req = "select sum(MONT_TRCH1_CAT) from categorie  where MONT_TRCH1_CAT is not null and "
						+ " COD_CAT_CAT='" + v_Cat + "' and COD_RGM_RGM=" + Long.valueOf(v_regim);
				// System.out.println(req);
				mnt = jt.queryForLong(req);
			}
			if (numTranche == 2L) {
				req = "select sum(MONT_TRCH2_CAT) from categorie  where MONT_TRCH2_CAT is not null and  "
						+ " COD_CAT_CAT='" + v_Cat + "' and COD_RGM_RGM=" + Long.valueOf(v_regim);
				// System.out.println(req);
				mnt = jt.queryForLong(req);
			}
			if (numTranche == 3L) {
				req = "select sum(MONT_TRCH3_CAT) from categorie  where MONT_TRCH3_CAT is not null and  "
						+ " COD_CAT_CAT='" + v_Cat + "' and COD_RGM_RGM=" + Long.valueOf(v_regim);
				// System.out.println(req);
				mnt = jt.queryForLong(req);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return mnt;
	}

	// inverser cat selon categorie
	public String reverseCat(String cat) {
		System.out.println(cat);
		cat = StrHandler.lpad(cat, '0', 3);
		String v_cat = "" + cat.charAt(2);
		String v_regim = "" + cat.substring(0, 2);
		System.out.println(v_cat + "" + Long.valueOf(v_regim));
		return v_cat + "" + Long.valueOf(v_regim);
	}

	// à prévoir les nouveau compte 177
	public void getNewContrat(Long strc, Long annee, String libAg, Date datComptable) {

		Context context = ContextHandler.getContext();
		CRUDservice crudService = (CRUDservice) context.getBean("crudservice");
		jt = new JdbcTemplate(dataSource);

		String req = "";
		SqlRowSet srs = null;
		Long indexStartHist = getNumSeqHistStart();
		req = "SELECT * " + " FROM contrat_cpt cpt " + " where cpt.COD_ETAT_CCPT ='V'" + " and  cpt.COD_PRD_PRD =177"
				+ " and cpt.cod_strc_strc=" + strc + " and  cpt.CAT_CCPT_CCPT is not null"
				+ " and  cpt.DAT_OUV_CCPT between to_date('01/07/" + annee + "','dd/mm/yyyy') " + " and to_date('01/06/"
				+ (annee + 1) + "','dd/mm/yyyy')" + " and cpt.num_ccpt_ccpt not in "
				+ " (select  num_ccpt_ccpt from hist_tranche_servi where ann_tranch_prim=" + annee
				+ " and cod_prd_prd=177 and cod_strc_strc=" + strc + ")";
		srs = jt.queryForRowSet(req);
		System.out.println(req);

		while (srs.next()) {
			Long numCpt = srs.getLong("num_ccpt_ccpt");
			System.out.println(numCpt);
			String cat = reverseCat(srs.getString("CAT_CCPT_CCPT"));
			Long mnt = getMontTrache(cat, 1L);

			// if(numCpt.equals(new Long(750003)))
			System.out.println(srs.getLong("MONT_SOLD_CCPT") + ":" + mnt + ":" + numCpt + ":" + strc);
			// if (srs.getLong("sold_cpt_trch") >= mnt) {
			// test a activer à partir des tranches de 2013
			if (mnt > 0) {
				HistTrancheServi histTrancheServi = new HistTrancheServi();
				histTrancheServi.setNumSeqTrch(indexStartHist);
				histTrancheServi.setCodStrcStrc(srs.getLong("cod_strc_strc"));
				histTrancheServi.setCodPrdPrd(srs.getLong("cod_prd_prd"));
				histTrancheServi.setNumCcptCcpt(srs.getLong("num_ccpt_ccpt"));
				histTrancheServi.setAnnTranchPrim(-1L);// annee
				histTrancheServi.setDatOuvCpt(srs.getDate("DAT_OUV_CCPT"));
				histTrancheServi.setCodCatCat(cat);
				histTrancheServi.setNumTrchServ(0L);
				histTrancheServi.setSoldCptTrch(srs.getLong("MONT_SOLD_CCPT"));
				histTrancheServi.setDatOpeTrch(datComptable);

				crudService.create(histTrancheServi);
				indexStartHist++;
			}
		}
		// System.out.println("Fin creation nouveau Compte 177-"+strc);
	}

	List<String> linesMig = new ArrayList<String>();

	public void getNewContratMig(Long strc, Long annee, String libAg, Date datComptable) {
		jt = new JdbcTemplate(dataSource);

		String req = "";
		SqlRowSet srs = null;

		req = "SELECT * " + " FROM contrat_cpt cpt " + " where cpt.COD_ETAT_CCPT ='V'" + " and  cpt.COD_PRD_PRD =177"
				+ " and cpt.cod_strc_strc=" + strc + " and  cpt.CAT_CCPT_CCPT is not null"
				+ " and  cpt.DAT_OUV_CCPT between to_date('01/07/" + annee + "','dd/mm/yyyy') " + " and to_date('30/12/"
				+ (annee + 1) + "','dd/mm/yyyy')";
		srs = jt.queryForRowSet(req);
		System.out.println(req);

		while (srs.next()) {
			Long numCpt = srs.getLong("num_ccpt_ccpt");
			System.out.println(numCpt);
			linesMig.add(StrHandler.lpad("" + strc, '0', 3) + "0177" + StrHandler.lpad("" + numCpt, '0', 6) + " "
					+ StrHandler.lpad("" + srs.getString("CAT_CCPT_CCPT"), '0', 3) + "\n");
		}
		// System.out.println("Fin creation nouveau Compte 177-"+strc);

	}

	// creation d'une ligne du fichier

	private boolean verifChangeCat(String cat, Long rgm, Long prd, Long strc, Long numCcpt, Long annee) {
		jt = new JdbcTemplate(dataSource);
		String req = "";
		SqlRowSet srs = null;
		req = "select cat_ccpt_ccpt from contrat_cpt" + " where num_ccpt_ccpt =" + numCcpt + " and cod_strc_strc="
				+ strc + " and cod_prd_prd=" + prd;
		String catCpt = (String) jt.queryForObject(req, String.class);
		String v_Cat = "" + cat.charAt(0);
		String v_regim = cat.substring(1, (cat.length()));
		String newCat = getNewCat(Long.valueOf(v_regim), v_Cat) + v_regim;
		if (catCpt != null && !reverseCat(catCpt.trim()).equals(cat) && cat.equals(newCat)) {
			System.out.println("Changement de categorie :" + catCpt + "/" + cat + ":" + strc + "-" + numCcpt);
			req = "select max(dat_deb_dcc) from detail_cat_cpt " + " where num_ccpt_ccpt =" + numCcpt
					+ " and cod_strc_strc=" + strc + " and trunc(dat_deb_dcc)  between '01/07/" + annee
					+ "' and '30/06/" + (annee + 1) + "'" + " and cod_prd_prd=" + prd;
			Date dateChangement = (Date) jt.queryForObject(req, Date.class);

			if (dateChangement != null) {
				System.out.println(dateChangement);
				return true;
			}

		}

		return false;
	}

	private Date verifDateChangeCat(String cat, Long rgm, Long prd, Long strc, Long numCcpt, Long annee) {
		jt = new JdbcTemplate(dataSource);
		String req = "";

		req = "select max(dat_deb_dcc) from detail_cat_cpt " + " where num_ccpt_ccpt =" + numCcpt + " and cod_cat_cat='"
				+ cat + "' and cod_rgm_rgm=" + rgm + " and cod_strc_strc=" + strc
				+ " and trunc(dat_deb_dcc)  between '01/07/" + annee + "' and '30/06/" + (annee + 1) + "'"
				+ " and cod_prd_prd=" + prd;
		Date dateChangement = (Date) jt.queryForObject(req, Date.class);

		if (dateChangement != null) {
			System.out.println("Date de changement :" + dateChangement);
			return dateChangement;
		}

		return null;
	}

	private String getNewCat(Long rgm, String cat) {
		String req = "";
		SqlRowSet srs = null;
		req = "select  cod_cat_cat,cod_rgm_rgm,cod_typ_cat,cod_corr_cat from categorie  where cod_prd_prd=177 and cod_cat_cat='"
				+ cat + "' and cod_rgm_rgm=" + rgm;
		srs = jt.queryForRowSet(req);
		while (srs.next()) {

			if (srs.getString("cod_typ_cat") != null && srs.getString("cod_typ_cat").equals("A")
					&& srs.getString("cod_corr_cat") != null)

				return srs.getString("cod_corr_cat");
		}
		return cat;
	}

	private PrimitiveVO forcageCat(Long strc, Long prd, Long numCpt, Long annee, String cat) {
		String req = "";
		SqlRowSet srs = null;
		req = "select num_trch_serv,mnt_prm_trch from forc_chang_cat " + " where num_ccpt_ccpt=" + numCpt
				+ " and cod_strc_strc=" + strc + " and cod_prd_prd=" + prd + " and cod_cat_cat='" + cat + "'"
				+ " and  mnt_prm_trch is not null" + " and  ann_tranch_prim=" + annee;
		srs = jt.queryForRowSet(req);
		while (srs.next()) {

			PrimitiveVO vo = new PrimitiveVO();
			vo.setVLong(srs.getLong("mnt_prm_trch"));
			vo.setVString("" + srs.getLong("num_trch_serv"));
			return vo;

		}
		return null;
	}

	private boolean changementGarnie(Long annee) {
		String req = "select count(*) from forc_chang_cat where ann_tranch_prim=" + annee;
		Long count = jt.queryForLong(req);
		if (count > 0)
			return true;
		return false;
	}

	public AssVieEpargneVo getLignePrime(Long strc, Long annee, String libAg, String padding, Date datComptable,
			boolean forcageInserted) {
		List<String> res = new ArrayList<String>();
		AssVieEpargneVo assVieEpargneVo = new AssVieEpargneVo();

		Context context = ContextHandler.getContext();
		CRUDservice crudService = (CRUDservice) context.getBean("crudservice");
		jt = new JdbcTemplate(dataSource);
		Long somme = 0L;
		String req = "";
		SqlRowSet srs = null;

		Long indexStartHist = getNumSeqHistStart();
		Long indexStartNHist = getNumSeqNHistStart();
		Long indexStartForc = getNumSeqForcageStart();
		req = "select   cpt.cat_ccpt_ccpt,h.num_ccpt_ccpt,h.cod_cat_cat,cpt.nom_inti_ccpt,h.num_trch_serv,h.sold_cpt_trch,cpt.MONT_SOLD_CCPT,cpt.DAT_OUV_CCPT,cpt.cod_strc_strc,cpt.cod_prd_prd from  hist_tranche_servi h,contrat_cpt cpt "
				+ " where cpt.cod_strc_strc=h.cod_strc_strc" + " and h.cod_strc_strc=" + strc
				+ " and h.ann_tranch_prim=" + annee + " and  cpt.cod_prd_prd=h.cod_prd_prd"
				+ " and  COD_CAT_CAT is not null" + " and  COD_CAT_CAT  not like '%@%'"
				// + " and SOLD_CPT_TRCH is not null and SOLD_CPT_TRCH!=0 "
				+ " and  NUM_TRCH_SERV < 3 "
				// test a activer à partir des tranches de 2013
				+ " and  cpt.COD_ETAT_CCPT ='V'" + " and  cpt.COD_PRD_PRD =177" + " and  cpt.CAT_CCPT_CCPT is not null"
				+ " and  cpt.DAT_OUV_CCPT < to_date('30/06/" + (annee + 1) + "','dd/mm/yyyy') "
				+ " and  cpt.DAT_OUV_CCPT > to_date('01/05/2004','dd/mm/yyyy') "
				+ " and cpt.num_ccpt_ccpt=h.num_ccpt_ccpt  order by h.num_ccpt_ccpt ASC ";
		srs = jt.queryForRowSet(req);

		String titre = "\n" + padding + "LISTE DES PRIMES D ASSURANCE VIE EPARGNE ETUDE NOUVEAU REGIME " + (annee + 1)
				+ "\n\n";
		res.add(titre);
		String titreAg = padding + "AGENCE   :      " + StrHandler.lpad("" + strc, '0', 3) + "   " + libAg + "\n";
		res.add(titreAg);
		String header = padding + "---------------------------------------------------------------------" + "\n"
				+ padding + "* COMPTE  * CAT  *        NOM                    *   * TRANCHE PRIME  *" + "\n" + padding
				+ "---------------------------------------------------------------------" + "\n";

		res.add(header);

		while (srs.next()) {
			HistTrancheServi histTrancheServi = new HistTrancheServi();
			Long numCpt = srs.getLong("num_ccpt_ccpt");
			Long numNextTrch = null;
			Long mnt = 0L;
			String catHist = srs.getString("cod_cat_cat").trim();
			String v_Cat = "" + catHist.charAt(0);
			String v_regim = catHist.substring(1, (catHist.length()));
			String corrCatHist = getNewCat(Long.valueOf(v_regim), v_Cat) + v_regim;
			/******/
			String catCpt = srs.getString("cat_ccpt_ccpt").trim();
			catCpt = reverseCat(catCpt);
			v_Cat = "" + catCpt.charAt(0);
			v_regim = catCpt.substring(1, (catCpt.length()));
			String corrCatCpt = getNewCat(Long.valueOf(v_regim), v_Cat) + v_regim;

			Date dateChangement = verifDateChangeCat(v_Cat, Long.valueOf(v_regim), 177L, strc, numCpt, annee);
			if (!corrCatCpt.equals(corrCatHist) && dateChangement != null) {
				mnt = getMontTrache(corrCatCpt, 1L);
				numNextTrch = 1L;
				histTrancheServi.setNumTrchAnc(srs.getLong("num_trch_serv"));
				histTrancheServi.setCodCatAnc(catHist);
				histTrancheServi.setDatChangeCat(dateChangement);
				// Liste à forcer par la suite
				if (!forcageInserted) {
					ForcageTrancheServi forcageTrancheServi = new ForcageTrancheServi();
					forcageTrancheServi.setNumSeqTrch(indexStartForc);
					forcageTrancheServi.setCodStrcStrc(srs.getLong("cod_strc_strc"));
					forcageTrancheServi.setCodPrdPrd(srs.getLong("cod_prd_prd"));
					forcageTrancheServi.setNumCcptCcpt(numCpt);
					forcageTrancheServi.setAnnTranchPrim(annee + 1);
					forcageTrancheServi.setDatOuvCpt(srs.getDate("DAT_OUV_CCPT"));
					forcageTrancheServi.setCodCatCat(corrCatCpt);
					forcageTrancheServi.setNumTrchAnc(srs.getLong("num_trch_serv"));
					forcageTrancheServi.setCodCatAnc(catHist);
					forcageTrancheServi.setDatChangeCat(dateChangement);
					forcageTrancheServi.setSoldCptTrch(srs.getLong("MONT_SOLD_CCPT"));
					forcageTrancheServi.setDatOpeTrch(datComptable);
					forcageTrancheServi.setMntTrch(null);// mnt à recalculer par mr chedhly
					forcageTrancheServi.setNumTrchServ(null);// num tranche à affecter par mr chedhly
					crudService.create(forcageTrancheServi);
					indexStartForc++;
				}
			} else {
				mnt = getMontTrache(corrCatCpt, (srs.getLong("num_trch_serv") + 1));
				numNextTrch = (srs.getLong("num_trch_serv") + 1);

			}

			// if(numCpt.equals(new Long(750003)))
			System.out.println(srs.getLong("MONT_SOLD_CCPT") + ":" + mnt + ":" + numCpt + ":" + strc);
			// if (srs.getLong("sold_cpt_trch") >= mnt) {
			// test a activer à partir des tranches de 2013

			// test forcage
			PrimitiveVO vo = forcageCat(strc, 177L, numCpt, annee + 1, corrCatCpt);
			if (vo != null) {
				mnt = vo.getVLong();
				numNextTrch = Long.valueOf(vo.getVString());
			}
			if (srs.getLong("MONT_SOLD_CCPT") >= mnt && mnt > 0) {
				// System.out.println(srs.getLong("sold_cpt_trch")+":"+mnt+":"+srs.getLong("num_ccpt_ccpt")+":"+strc+":"+srs.getString("cod_cat_cat"));

				String line = padding + "* " + formatStringRight(8, StrHandler.lpad("" + numCpt, '0', 6)) + "* "
						+ formatStringRight(5, corrCatCpt) + "* "
						+ formatStringRight(30, srs.getString("nom_inti_ccpt")) + "* "
						+ formatStringRight(2, "" + numNextTrch) + "* "
						+ formatStringRight(15, StrHandler.formatMontant(mnt, 3L)) + "* " + "\n";
				somme += mnt;
				res.add(line);

				histTrancheServi.setNumSeqTrch(indexStartHist);
				histTrancheServi.setCodStrcStrc(srs.getLong("cod_strc_strc"));
				histTrancheServi.setCodPrdPrd(srs.getLong("cod_prd_prd"));
				histTrancheServi.setNumCcptCcpt(numCpt);
				histTrancheServi.setAnnTranchPrim(annee + 1);
				histTrancheServi.setDatOuvCpt(srs.getDate("DAT_OUV_CCPT"));
				histTrancheServi.setCodCatCat(corrCatCpt);
				histTrancheServi.setNumTrchServ(numNextTrch);
				histTrancheServi.setSoldCptTrch(srs.getLong("MONT_SOLD_CCPT"));
				histTrancheServi.setDatOpeTrch(datComptable);
				crudService.create(histTrancheServi);
				indexStartHist++;
			} else// liste prime solde insufisant
			{
				HistTrancheNonServi histTrancheNonServi = new HistTrancheNonServi();
				histTrancheNonServi.setNumSeqTrch(indexStartNHist);
				histTrancheNonServi.setCodStrcStrc(srs.getLong("cod_strc_strc"));
				histTrancheNonServi.setCodPrdPrd(srs.getLong("cod_prd_prd"));
				histTrancheNonServi.setNumCcptCcpt(numCpt);
				histTrancheNonServi.setAnnTranchPrim(annee + 1);
				histTrancheNonServi.setDatOuvCpt(srs.getDate("DAT_OUV_CCPT"));
				histTrancheNonServi.setCodCatCat(corrCatCpt);
				histTrancheNonServi.setNumTrchServ(numNextTrch);
				histTrancheNonServi.setSoldCptTrch(srs.getLong("MONT_SOLD_CCPT"));
				histTrancheNonServi.setDatOpeTrch(datComptable);
				crudService.create(histTrancheNonServi);
				indexStartNHist++;
			}
		}
		String footer = padding + "---------------------------------------------------------------------" + "\n"
				+ padding + "*         *      *                               *   * "
				+ formatStringRight(15, StrHandler.formatMontant(somme, 3L)) + "*" + "\n" + padding
				+ "---------------------------------------------------------------------" + "\n";
		res.add(footer);

		assVieEpargneVo.setLines(res);
		assVieEpargneVo.setSomme(somme);
		return assVieEpargneVo;
	}

	// calculer le nombre des comptes affecter avec une categorie d'assurence
	private Long getNbrCompteCat(String codCat, Long numTranche, Long annee) {
		Long nbr = null;
		jt = new JdbcTemplate(dataSource);
		String req = "";
		try {

			// req =
			// "select count(*) from HIST_TRANCHE_SERVI h,contrat_cpt cpt where " + " h.COD_CAT_CAT='" + codCat
			// + "' and h.ann_tranch_prim=" + annee + " and cpt.cod_prd_prd=h.cod_prd_prd"
			// + " and cpt.COD_STRC_STRC=h.COD_STRC_STRC" + " and cpt.NUM_CCPT_CCPT=h.NUM_CCPT_CCPT"
			// + " and h.SOLD_CPT_TRCH >= " + mntTranche + " and h.NUM_TRCH_SERV = " + numTranche
			// + " and h.DAT_OUV_CPT < to_date('30/06/" + annee + "','dd/mm/yyyy') "
			// + " and h.DAT_OUV_CPT > to_date('01/05/2004','dd/mm/yyyy') ";

			req = "select count(*) from HIST_TRANCHE_SERVI h where " + " h.COD_CAT_CAT='" + codCat
					+ "' and h.ann_tranch_prim=" + annee + " and  h.NUM_TRCH_SERV = " + numTranche;

			// System.out.println(req);
			nbr = jt.queryForLong(req);

		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return nbr;
	}

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

	// creation de la ligne total du fichier
	public PrimitiveVO getCatTabLine(Long annee, String libAg, String padding) {
		List<String> res = new ArrayList<String>();

		jt = new JdbcTemplate(dataSource);
		Long somme = 0L;
		String req = "";
		SqlRowSet srs = null;

		req = "select CONCAT( cod_cat_cat, cod_rgm_rgm ) as cat,mont_trch1_cat,mont_trch2_cat,mont_trch3_cat from categorie where COD_PRD_PRD=177 and MONT_TRCH1_CAT is not null order by cod_cat_cat ASC ,cod_rgm_rgm ASC";
		// "select distinct(cod_cat_cat) as cat from hist_tranche_servi where ann_tranch_prim="+(annee+1)+" order by
		// cod_cat_cat";

		srs = jt.queryForRowSet(req);
		try {

			String titre =
					"\n" + padding + "RECAPITULATION DES ASSURANCES PAR CATEGORIES DE COMPTES " + (annee + 1) + "\n\n";
			res.add(titre);

			String header = padding
					+ "-------------------------------------------------------------------------------------------------------------------------------"
					+ "\n" + padding
					+ "* CAT *       1 ERE  TRANCHE        *       2 EME  TRANCHE        *       3 EME  TRANCHE        *       TOTAL CATEGORIE       *"
					+ "\n" + padding
					+ "*     *  NBR  * MONTANT*    TOTAL   *  NBR  * MONTANT*    TOTAL   *  NBR  * MONTANT*    TOTAL   *  NBR  *    TOTAL   * T.PRIME*"
					+ "\n" + padding

					+ "-------------------------------------------------------------------------------------------------------------------------------"
					+ "\n";
			res.add(header);
			Long totalAllNbr1 = 0L;
			Long totalAllTr1 = 0L;
			Long totalAllNbr2 = 0L;
			Long totalAllTr2 = 0L;
			Long totalAllNbr3 = 0L;
			Long totalAllTr3 = 0L;
			Long totalAllNbr = 0L;
			Long totalAllTr = 0L;
			while (srs.next()) {
				String cat = srs.getString("cat");
				Long nbrTr1 = getNbrCompteCat(cat, 1L, annee + 1);

				Long mnttr1 = srs.getLong("mont_trch1_cat");
				Long totalTr1 = nbrTr1 * mnttr1;
				totalAllNbr1 += nbrTr1;
				totalAllTr1 += totalTr1;
				Long nbrTr2 = getNbrCompteCat(cat, 2L, annee + 1);
				Long mnttr2 = srs.getLong("mont_trch2_cat");
				Long totalTr2 = nbrTr2 * mnttr2;
				totalAllNbr2 += nbrTr2;
				totalAllTr2 += totalTr2;
				Long nbrTr3 = getNbrCompteCat(cat, 3L, annee + 1);
				Long mnttr3 = srs.getLong("mont_trch3_cat");
				Long totalTr3 = nbrTr3 * mnttr3;
				totalAllNbr3 += nbrTr3;
				totalAllTr3 += totalTr3;
				Long nbrTotal = nbrTr1 + nbrTr2 + nbrTr3;
				Long mntTotal = mnttr1 + mnttr2 + mnttr3;
				Long totalLigne = totalTr1 + totalTr2 + totalTr3;
				totalAllNbr += nbrTotal;
				totalAllTr += totalLigne;
				String lineQualite = padding + "* " + StrHandler.rpad(cat, ' ', 3) + " * "
						+ StrHandler.lpad("" + formatNombre(nbrTr1), ' ', 5) + " * "
						+ StrHandler.lpad("" + formatMontant(mnttr1, 3L), ' ', 6) + " * "
						+ StrHandler.lpad(formatMontant(totalTr1, 3L), ' ', 10) + " * "
						+ StrHandler.lpad("" + formatNombre(nbrTr2), ' ', 5) + " * "
						+ StrHandler.lpad("" + formatMontant(mnttr2, 3L), ' ', 6) + " * "
						+ StrHandler.lpad(formatMontant(totalTr2, 3L), ' ', 10) + " * "
						+ StrHandler.lpad("" + formatNombre(nbrTr3), ' ', 5) + " * "
						+ StrHandler.lpad("" + formatMontant(mnttr3, 3L), ' ', 6) + " * "
						+ StrHandler.lpad(formatMontant(totalTr3, 3L), ' ', 10) + " * "
						+ StrHandler.lpad("" + formatNombre(nbrTotal), ' ', 5) + " * "
						+ StrHandler.lpad("" + formatMontant(totalLigne, 3L), ' ', 10) + " * "
						+ StrHandler.lpad(formatMontant(mntTotal, 3L), ' ', 10) + " * " + "\n";
				res.add(lineQualite);

			}

			String delimiter = padding
					+ "-------------------------------------------------------------------------------------------------------------------------------"
					+ "\n";
			res.add(delimiter);
			String totalQualite = padding + "* " + StrHandler.rpad("", ' ', 3) + " * "
					+ StrHandler.lpad("" + formatNombre(totalAllNbr1), ' ', 5) + " * "
					+ StrHandler.lpad("" + formatMontant(0L, 3L), ' ', 6) + " * "
					+ StrHandler.lpad(formatMontant(totalAllTr1, 3L), ' ', 10) + " * "
					+ StrHandler.lpad("" + formatNombre(totalAllNbr2), ' ', 5) + " * "
					+ StrHandler.lpad("" + formatMontant(0L, 3L), ' ', 6) + " * "
					+ StrHandler.lpad(formatMontant(totalAllTr2, 3L), ' ', 10) + " * "
					+ StrHandler.lpad("" + formatNombre(totalAllNbr3), ' ', 5) + " * "
					+ StrHandler.lpad("" + formatMontant(0L, 3L), ' ', 6) + " * "
					+ StrHandler.lpad(formatMontant(totalAllTr3, 3L), ' ', 10) + " * "
					+ StrHandler.lpad("" + formatNombre(totalAllNbr), ' ', 5) + " * "
					+ StrHandler.lpad("" + formatMontant(totalAllTr, 3L), ' ', 6) + " * "
					+ StrHandler.lpad(formatMontant(0L, 3L), ' ', 10) + " * " + "\n";
			res.add(totalQualite);
			res.add(delimiter);

		} catch (Exception e) {
			e.printStackTrace();
		}
		PrimitiveVO vo = new PrimitiveVO();
		vo.setVlist(res);

		return vo;
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

	// insertion des categories selon le fichier
	public void insertCategorie(String filenameCsv) throws IOException, FileNotFoundException {
		List<String> lines = new ArrayList<String>();
		jt = new JdbcTemplate(dataSource);
		BufferedReader br;

		br = new BufferedReader(new FileReader(filenameCsv));

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
			// System.out.println(lines.get(i));
			if (lines.get(i) != null) {
				String[] line = lines.get(i).split(";");
				String codCat = line[0];
				String v_Cat = "" + codCat.charAt(0);
				String v_regim = codCat.substring(1, (codCat.length()));
				// System.out.println(codCat);
				// System.out.println(v_Cat);
				// System.out.println(v_regim);

				Long nb = jt.queryForLong("select count(*) from categorie  where COD_CAT_CAT='" + v_Cat
						+ "' and COD_RGM_RGM=" + Long.valueOf(v_regim));
				if (nb.equals(1L))
					jt.execute("update categorie set MONT_TRCH1_CAT=" + Long.valueOf(line[1]) + ",MONT_TRCH2_CAT="
							+ Long.valueOf(line[2]) + ",MONT_TRCH3_CAT=" + Long.valueOf(line[3])
							+ " where COD_CAT_CAT='" + v_Cat + "' and COD_RGM_RGM=" + Long.valueOf(v_regim));
				else if (nb.equals(0L)) {
					System.out.println("Categorie NOT FOUND:" + v_regim + ":" + v_Cat);
					try {
						// Long nbrg =
						// jt.queryForLong("select count(*) from regime where COD_RGM_RGM="
						// + Long.valueOf(v_regim) + " and COD_PRD_PRD=177");
						// if (nbrg == 0) {
						// jt.execute("insert into regime(COD_PRD_PRD,COD_RGM_RGM,LIB_RGM_RGM,ETAT_RGM_RGM) values(177,"
						// + Long.valueOf(v_regim) + ",'Regime " + Long.valueOf(v_regim) + " ans ',0" + ")");
						// }

						jt.execute(
								"insert into categorie(MONT_CAPT_CAT,MONT_VERS_CAT,COD_PRD_PRD,COD_CAT_CAT,COD_RGM_RGM,LIB_CAT_CAT,MONT_TRCH1_CAT,MONT_TRCH2_CAT,MONT_TRCH3_CAT) values(0,0,177,'"
										+ v_Cat + "'," + Long.valueOf(v_regim) + ",'iCategorie" + v_Cat + "',"
										+ Long.valueOf(line[1]) + "," + Long.valueOf(line[2]) + ","
										+ Long.valueOf(line[3]) + ")");
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		}
	}

	// creation du fichier prime
	public void loadNewContart(Long annee) {

		jt = new JdbcTemplate(dataSource);
		List<PrimitiveVO> listeag = getListAgences();
		try {
			for (int j = 0; j < listeag.size(); j++) {

				// creation de nouveau tranche
				getNewContrat(listeag.get(j).getVLong(), annee, listeag.get(j).getVString(), new Date());

			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			throw new RuntimeException(e.getMessage());
		}
		System.out.println("Fin Importation nouveau contarat Annee" + (annee + 1));
	}

	// creation du fichier prime
	public void loadNewContartMig(Long annee) {

		jt = new JdbcTemplate(dataSource);
		List<PrimitiveVO> listeag = getListAgences();
		try {
			for (int j = 0; j < listeag.size(); j++) {

				// creation de nouveau tranche
				getNewContratMig(listeag.get(j).getVLong(), annee, listeag.get(j).getVString(), new Date());

			}
			File file = new File("d:/NOUV_COMPTE_" + (annee + 1) + ".txt");

			if (!file.exists())
				file.createNewFile();
			else
				file.delete();
			FileWriter writer = null;

			writer = new FileWriter(file);
			for (int i = 0; i < linesMig.size(); i++)
				writer.write(linesMig.get(i));

			writer.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			throw new RuntimeException(e.getMessage());
		}
		System.out.println("Fin Importation nouveau contarat Annee" + (annee + 1));
	}

	// creation du fichier prime
	public void createFilePrime(Long annee) {

		jt = new JdbcTemplate(dataSource);
		List<PrimitiveVO> listeag = getListAgences();
		Long totalAg = 0L;
		String padding = "        ";
		List<String> lines = new ArrayList<String>();
		try {
			File file = new File("d:/PRIME_" + (annee + 1) + ".txt");

			if (!file.exists())
				file.createNewFile();
			else
				file.delete();
			FileWriter writer = null;

			writer = new FileWriter(file);
			jt.execute("delete from hist_tranche_servi where ANN_TRANCH_PRIM=" + annee + 1);
			jt.execute("delete from hist_tranche_nservi where ANN_TRANCH_PRIM=" + annee + 1);
			boolean forcageInserted = changementGarnie(annee + 1);
			for (int j = 0; j < listeag.size(); j++) {

				AssVieEpargneVo vo = getLignePrime(listeag.get(j).getVLong(), annee, listeag.get(j).getVString(),
						padding, new Date(), forcageInserted);
				totalAg += vo.getSomme();
				lines.addAll((List<String>) vo.getLines());

			}

			String totalLine = padding + "---------------------------------------------------------------------" + "\n"
					+ padding + "*         *      * TOTAL GENERAL                 * T * "
					+ formatStringRight(15, StrHandler.formatMontant(totalAg, 3L)) + "*" + "\n" + padding
					+ "---------------------------------------------------------------------" + "\n";
			lines.add(totalLine);

			lines.addAll((List<String>) getCatTabLine(annee, "", padding).getVlist());

			for (int i = 0; i < lines.size(); i++)
				writer.write(lines.get(i));

			writer.close();

		} catch (Exception e) {
			// TODO Auto-generated catch block
			throw new RuntimeException(e.getMessage());
		}
		System.out.println("Fin edition fichier PRIME_" + (annee + 1));
	}

	public void insertHistTrach(String fileName, Long annee) throws IOException {

		List<String> lines = new ArrayList<String>();
		jt = new JdbcTemplate(dataSource);
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

		// SCript d'importaion de fichier 2012
		List<Exec> list = new ArrayList<Exec>();
		if (lines != null && !lines.isEmpty()) {
			System.out.println("Importing file:" + lines.size());
			int nbThread = 20;
			int width = lines.size() / nbThread;
			int lastIndex = 0;
			int startIndex = 0;

			lines.remove(0);
			new Exec(0, lines.size(), lines, annee, getNumSeqHistStart()).start();
		}

	}

	public Long getNumSeqHistStart() {
		jt = new JdbcTemplate(dataSource);

		Long numeroSequence =
				(Long) jt.queryForObject("select nvl(max(num_seq_trch),1) from hist_tranche_servi", Long.class);
		return numeroSequence + 1;
	}

	public Long getNumSeqNHistStart() {
		jt = new JdbcTemplate(dataSource);

		Long numeroSequence =
				(Long) jt.queryForObject("select nvl(max(num_seq_trch),1) from hist_tranche_nservi", Long.class);
		return numeroSequence + 1;
	}

	public Long getNumSeqForcageStart() {
		jt = new JdbcTemplate(dataSource);

		Long numeroSequence =
				(Long) jt.queryForObject("select nvl(max(num_seq_trch),1) from forc_chang_cat", Long.class);
		return numeroSequence + 1;
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

	/*********** Fix cpla ************/

	// creation du fichier prime
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
						// String req = "select num_taui_arl from avanc_remb_liquid where NUM_SEQ_ARL=" + line[0];
						String req = "select num_taui_cpla from contrat_placement where num_seq_cpla=" + line[0];
						Double taux = (Double) jt.queryForObject(req, Double.class);
						req = "select num_marg_cpla from contrat_placement where num_seq_cpla=" + line[0];
						Double tauxMarge = (Double) jt.queryForObject(req, Double.class);
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

	public List<ContratEpargneEtudeVo> getListeContratEpargneEtudePaye(int tranche, String dateDebut, String dateFin) {
		jt = new JdbcTemplate(dataSource);
		String req = "SELECT" + "    cp.cod_strc_strc," + "    cp.cod_prd_prd," + "    cp.num_ccpt_ccpt,"
				+ "    cp.cat_ccpt_ccpt," + "    cp.mont_sold_ccpt,";
		if (tranche == 1) {
			req += "    ct.mont_trch1_cat,";
		} else if (tranche == 2) {
			req += "    ct.mont_trch2_cat,";
		} else if (tranche == 3) {
			req += "    ct.mont_trch3_cat,";
		}
		req += "	pers.COD_TPCE_TPCE," + "    pers.NUM_PCE_PERS," + "    pers.NOM_NOM_PERS,"
				+ "    pers.NOM_PRN_PERS	";
		req += "	 FROM" + "    contrat_cpt cp," + "    categorie ct,  personne pers " + "	WHERE"
				+ "    cp.cod_prd_prd = 177" + "    AND   cp.dat_ouv_ccpt >= TO_DATE('" + dateDebut + "','DD/MM/YYYY')"
				+ "    AND   cp.dat_ouv_ccpt <= TO_DATE('" + dateFin + "','DD/MM/YYYY')"
				+ "    AND   cp.cod_etat_ccpt = 'V'" + "    AND   substr(lpad(cp.cat_ccpt_ccpt,3,'0'),3,1) NOT IN ("
				+ "        'A'," + "        'B'," + "        'C'," + "        'D'" + "    )"
				+ "    AND   lpad(ct.cod_rgm_rgm,2,'0') = substr(lpad(cp.cat_ccpt_ccpt,3,'0'),0,2)"
				+ "    AND   ct.cod_cat_cat = substr(lpad(cp.cat_ccpt_ccpt,3,'0'),3,1)"
				+ "    AND   ct.cod_prd_prd = 177";
		if (tranche == 1) {
			req += "    AND   cp.mont_sold_ccpt >= ct.mont_trch1_cat";
		} else if (tranche == 2) {
			req += "    AND   cp.mont_sold_ccpt >= ct.mont_trch2_cat"
					+ " AND   (cp.COD_STRC_STRC,cp.COD_PRD_PRD,cp.NUM_CCPT_CCPT) in (select taf.COD_STRC_STRC,taf.COD_PRD_PRD,taf.NUM_CCPT_CCPT from TRACE_ASSURANCE_FAIEZ taf where taf.COD_ETAT_TRCH1 = 'P' AND taf.COD_ETAT_TRCH2 is null AND taf.COD_ETAT_TRCH3 is null)";
		} else if (tranche == 3) {
			req += "    AND   cp.mont_sold_ccpt >= ct.mont_trch3_cat"
					+ " AND   (cp.COD_STRC_STRC,cp.COD_PRD_PRD,cp.NUM_CCPT_CCPT) in (select taf.COD_STRC_STRC,taf.COD_PRD_PRD,taf.NUM_CCPT_CCPT from TRACE_ASSURANCE_FAIEZ taf where taf.COD_ETAT_TRCH1 = 'P' AND taf.COD_ETAT_TRCH2 = 'P' AND taf.COD_ETAT_TRCH3 is null)";
		}
		req += "	AND   pers.NUM_SEQ_PERS = cp.NUM_SEQ_PERS";
		req += "	 ORDER BY" + "    cp.cod_strc_strc," + "    cp.num_ccpt_ccpt";
		System.out.println(req);
		SqlRowSet srs = jt.queryForRowSet(req);

		List<ContratEpargneEtudeVo> res = new ArrayList<ContratEpargneEtudeVo>();
		while (srs.next()) {
			ContratEpargneEtudeVo cee = new ContratEpargneEtudeVo();
			ContratCpt cpt = new ContratCpt();
			ContratCptId cptId = new ContratCptId();
			Categorie categorie = new Categorie();
			cptId.setCodStrcStrc(srs.getLong("cod_strc_strc"));
			cptId.setCodPrdPrd(srs.getLong("cod_prd_prd"));
			cptId.setNumCcptCcpt(srs.getLong("num_ccpt_ccpt"));
			cpt.setContratCptId(cptId);
			cpt.setCatCcptCcpt(srs.getString("cat_ccpt_ccpt"));
			cpt.setMontSoldCcpt(srs.getLong("mont_sold_ccpt"));
			cee.setContratCpt(cpt);
			if (tranche == 1) {
				categorie.setMontTranch1Cat(srs.getLong("mont_trch1_cat"));
			}
			if (tranche == 2) {
				categorie.setMontTranch2Cat(srs.getLong("mont_trch2_cat"));
			}
			if (tranche == 3) {
				categorie.setMontTranch3Cat(srs.getLong("mont_trch3_cat"));
			}
			cee.setCategorie(categorie);
			cee.setCodTpceTpce(srs.getLong("COD_TPCE_TPCE"));
			cee.setNumPcePers(srs.getString("NUM_PCE_PERS"));
			cee.setNomNomPers(srs.getString("NOM_NOM_PERS"));
			cee.setNomPrnPers(srs.getString("NOM_PRN_PERS"));
			res.add(cee);

		}
		return res;
	}

	public List<ContratEpargneEtudeVo> getListeContratEpargneEtudeImpaye(int tranche, String dateDebut,
			String dateFin) {

		jt = new JdbcTemplate(dataSource);
		String req = "SELECT" + "    cp.cod_strc_strc," + "    cp.cod_prd_prd," + "    cp.num_ccpt_ccpt,"
				+ "    cp.cat_ccpt_ccpt," + "    cp.mont_sold_ccpt,";
		if (tranche == 1) {
			req += "    ct.mont_trch1_cat";
		} else if (tranche == 2) {
			req += "    ct.mont_trch2_cat";
		} else if (tranche == 3) {
			req += "    ct.mont_trch3_cat";
		}
		req += "	 FROM" + "    contrat_cpt cp," + "    categorie ct" + "	WHERE" + "    cp.cod_prd_prd = 177"
				+ "    AND   cp.dat_ouv_ccpt >= TO_DATE('" + dateDebut + "','DD/MM/YYYY')"
				+ "    AND   cp.dat_ouv_ccpt <= TO_DATE('" + dateFin + "','DD/MM/YYYY')"
				+ "    AND   cp.cod_etat_ccpt = 'V'" + "    AND   substr(lpad(cp.cat_ccpt_ccpt,3,'0'),3,1) NOT IN ("
				+ "        'A'," + "        'B'," + "        'C'," + "        'D'" + "    )"
				+ "    AND   lpad(ct.cod_rgm_rgm,2,'0') = substr(lpad(cp.cat_ccpt_ccpt,3,'0'),0,2)"
				+ "    AND   ct.cod_cat_cat = substr(lpad(cp.cat_ccpt_ccpt,3,'0'),3,1)"
				+ "    AND   ct.cod_prd_prd = 177";
		if (tranche == 1) {
			req += "    AND   cp.mont_sold_ccpt < ct.mont_trch1_cat "
					+ " AND   (cp.COD_STRC_STRC,cp.COD_PRD_PRD,cp.NUM_CCPT_CCPT) not in (select taf.COD_STRC_STRC,taf.COD_PRD_PRD,taf.NUM_CCPT_CCPT from TRACE_ASSURANCE_FAIEZ taf where taf.COD_ETAT_TRCH1 = 'P') ";
		} else if (tranche == 2) {
			req += "    AND   cp.mont_sold_ccpt < ct.mont_trch2_cat "
					+ "		AND   (cp.COD_STRC_STRC,cp.COD_PRD_PRD,cp.NUM_CCPT_CCPT) not in (select taf.COD_STRC_STRC,taf.COD_PRD_PRD,taf.NUM_CCPT_CCPT from TRACE_ASSURANCE_FAIEZ taf where taf.COD_ETAT_TRCH2 = 'P') ";
		} else if (tranche == 3) {
			req += "    AND   cp.mont_sold_ccpt < ct.mont_trch3_cat "
					+ " AND   (cp.COD_STRC_STRC,cp.COD_PRD_PRD,cp.NUM_CCPT_CCPT) not in (select taf.COD_STRC_STRC,taf.COD_PRD_PRD,taf.NUM_CCPT_CCPT from TRACE_ASSURANCE_FAIEZ taf where taf.COD_ETAT_TRCH3 = 'P') ";
		}
		req += "	 ORDER BY" + "    cp.cod_strc_strc," + "    cp.num_ccpt_ccpt";
		System.out.println(req);
		SqlRowSet srs = jt.queryForRowSet(req);

		List<ContratEpargneEtudeVo> res = new ArrayList<ContratEpargneEtudeVo>();
		while (srs.next()) {
			ContratEpargneEtudeVo cee = new ContratEpargneEtudeVo();
			ContratCpt cpt = new ContratCpt();
			ContratCptId cptId = new ContratCptId();
			Categorie categorie = new Categorie();
			cptId.setCodStrcStrc(srs.getLong("cod_strc_strc"));
			cptId.setCodPrdPrd(srs.getLong("cod_prd_prd"));
			cptId.setNumCcptCcpt(srs.getLong("num_ccpt_ccpt"));
			cpt.setContratCptId(cptId);
			cpt.setCatCcptCcpt(srs.getString("cat_ccpt_ccpt"));
			cpt.setMontSoldCcpt(srs.getLong("mont_sold_ccpt"));
			cee.setContratCpt(cpt);
			if (tranche == 1) {
				categorie.setMontTranch1Cat(srs.getLong("mont_trch1_cat"));
			}
			if (tranche == 2) {
				categorie.setMontTranch2Cat(srs.getLong("mont_trch2_cat"));
			}
			if (tranche == 3) {
				categorie.setMontTranch3Cat(srs.getLong("mont_trch3_cat"));
			}
			cee.setCategorie(categorie);
			res.add(cee);

		}
		return res;
	}

	public List<TraceAssuranceFaiezVo> getListeTrancheResidueFaiez(int tranche) {

		jt = new JdbcTemplate(dataSource);
		String req = "SELECT" + "    taf.cod_strc_strc," + "    taf.cod_prd_prd," + "    taf.num_ccpt_ccpt,"
				+ "	taf.CAT_CCPT_CCPT, ";
		if (tranche == 1) {
			req += "    taf.COD_ETAT_TRCH1, taf.DAT_PAY_TRCH1, taf.MONT_TRCH1_CAT,";
		} else if (tranche == 2) {
			req += "    taf.COD_ETAT_TRCH2, taf.DAT_PAY_TRCH2, taf.MONT_TRCH2_CAT,";
		} else if (tranche == 3) {
			req += "    taf.COD_ETAT_TRCH3, taf.DAT_PAY_TRCH3, taf.MONT_TRCH3_CAT,";
		}
		req += "	pers.COD_TPCE_TPCE," + "    pers.NUM_PCE_PERS," + "    pers.NOM_NOM_PERS,"
				+ "    pers.NOM_PRN_PERS," + "    cp.MONT_SOLD_CCPT ";
		req += "	 FROM" + "    contrat_cpt cp," + "    TRACE_ASSURANCE_FAIEZ taf,  personne pers " + "	WHERE"
				+ "	taf.COD_STRC_STRC=cp.COD_STRC_STRC" + "    AND taf.COD_PRD_PRD=cp.COD_PRD_PRD"
				+ "    AND taf.NUM_CCPT_CCPT=cp.NUM_CCPT_CCPT" + "   AND cp.cod_etat_ccpt = 'V'";
		if (tranche == 1) {
			req += "	AND taf.COD_ETAT_TRCH1='I'  AND   cp.mont_sold_ccpt >= taf.MONT_TRCH1_CAT";
		} else if (tranche == 2) {
			req += "    AND taf.COD_ETAT_TRCH2='I' AND taf.COD_ETAT_TRCH1='P'  AND   cp.mont_sold_ccpt >= taf.MONT_TRCH2_CAT";
		} else if (tranche == 3) {
			req += "    AND taf.COD_ETAT_TRCH3='I' AND (taf.COD_ETAT_TRCH1='P' AND taf.COD_ETAT_TRCH2='P')  AND   cp.mont_sold_ccpt >= taf.MONT_TRCH3_CAT";
		}
		req += "	AND   pers.NUM_SEQ_PERS = cp.NUM_SEQ_PERS";
		req += "	 ORDER BY" + "    taf.cod_strc_strc," + "    taf.num_ccpt_ccpt";
		System.out.println(req);
		SqlRowSet srs = jt.queryForRowSet(req);

		List<TraceAssuranceFaiezVo> res = new ArrayList<TraceAssuranceFaiezVo>();
		while (srs.next()) {
			TraceAssuranceFaiezVo taf = new TraceAssuranceFaiezVo();
			ContratCpt cpt = new ContratCpt();
			ContratCptId cptId = new ContratCptId();
			cptId.setCodStrcStrc(srs.getLong("cod_strc_strc"));
			cptId.setCodPrdPrd(srs.getLong("cod_prd_prd"));
			cptId.setNumCcptCcpt(srs.getLong("num_ccpt_ccpt"));
			cpt.setContratCptId(cptId);
			cpt.setCatCcptCcpt(srs.getString("cat_ccpt_ccpt"));
			cpt.setMontSoldCcpt(srs.getLong("MONT_SOLD_CCPT"));
			taf.setContratCpt(cpt);
			if (tranche == 1) {
				taf.setCod_etat_trch1(srs.getString("COD_ETAT_TRCH1"));
				taf.setDat_pay_trch1(srs.getDate("DAT_PAY_TRCH1"));
				taf.setMont_trch1_cat(srs.getLong("MONT_TRCH1_CAT"));
			}
			if (tranche == 2) {
				taf.setCod_etat_trch2(srs.getString("COD_ETAT_TRCH2"));
				taf.setDat_pay_trch2(srs.getDate("DAT_PAY_TRCH2"));
				taf.setMont_trch2_cat(srs.getLong("MONT_TRCH2_CAT"));
			}
			if (tranche == 3) {
				taf.setCod_etat_trch3(srs.getString("COD_ETAT_TRCH3"));
				taf.setDat_pay_trch3(srs.getDate("DAT_PAY_TRCH3"));
				taf.setMont_trch3_cat(srs.getLong("MONT_TRCH3_CAT"));
			}
			taf.setCodTpceTpce(srs.getLong("COD_TPCE_TPCE"));
			taf.setNumPcePers(srs.getString("NUM_PCE_PERS"));
			taf.setNomNomPers(srs.getString("NOM_NOM_PERS"));
			taf.setNomPrnPers(srs.getString("NOM_PRN_PERS"));
			res.add(taf);

		}
		return res;
	}

	public void InsertTraceTrancheFaiezImpayé(ContratEpargneEtudeVo vo, int tranche) {
		String req = null;
		jt = new JdbcTemplate(dataSource);
		if (tranche == 1) {
			req = "Insert into TRACE_ASSURANCE_FAIEZ (COD_STRC_STRC,COD_PRD_PRD,NUM_CCPT_CCPT,MONT_TRCH1_CAT,MONT_TRCH2_CAT,MONT_TRCH3_CAT,COD_ETAT_TRCH1,COD_ETAT_TRCH2,COD_ETAT_TRCH3,DAT_PAY_TRCH1,DAT_PAY_TRCH2,DAT_PAY_TRCH3,CAT_CCPT_CCPT) values ("
					+ vo.getContratCpt().getContratCptId().getCodStrcStrc() + ","
					+ vo.getContratCpt().getContratCptId().getCodPrdPrd() + ","
					+ vo.getContratCpt().getContratCptId().getNumCcptCcpt() + ","
					+ vo.getCategorie().getMontTranch1Cat() + ",null,null,'I',null,null,null,null,null,'"
					+ vo.getContratCpt().get_catCcptCcpt() + "')";
		} else if (tranche == 2) {
			req = "UPDATE TRACE_ASSURANCE_FAIEZ SET MONT_TRCH2_CAT = " + vo.getCategorie().getMontTranch2Cat()
					+ ", COD_ETAT_TRCH2 = 'I' WHERE COD_STRC_STRC = "
					+ vo.getContratCpt().getContratCptId().getCodStrcStrc() + " AND NUM_CCPT_CCPT = "
					+ vo.getContratCpt().getContratCptId().getNumCcptCcpt();
		} else if (tranche == 3) {
			req = "UPDATE TRACE_ASSURANCE_FAIEZ SET MONT_TRCH3_CAT = " + vo.getCategorie().getMontTranch3Cat()
					+ ", COD_ETAT_TRCH3 = 'I' WHERE COD_STRC_STRC = "
					+ vo.getContratCpt().getContratCptId().getCodStrcStrc() + " AND NUM_CCPT_CCPT = "
					+ vo.getContratCpt().getContratCptId().getNumCcptCcpt();
		}
		try {
			jt.execute(req);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void InsertTraceTrancheFaiezPayé(ContratEpargneEtudeVo vo, String date, int tranche) {
		String req = null;
		jt = new JdbcTemplate(dataSource);
		if (tranche == 1) {
			req = "Insert into TRACE_ASSURANCE_FAIEZ (COD_STRC_STRC,COD_PRD_PRD,NUM_CCPT_CCPT,MONT_TRCH1_CAT,MONT_TRCH2_CAT,MONT_TRCH3_CAT,COD_ETAT_TRCH1,COD_ETAT_TRCH2,COD_ETAT_TRCH3,DAT_PAY_TRCH1,DAT_PAY_TRCH2,DAT_PAY_TRCH3,CAT_CCPT_CCPT) values ("
					+ vo.getContratCpt().getContratCptId().getCodStrcStrc() + ","
					+ vo.getContratCpt().getContratCptId().getCodPrdPrd() + ","
					+ vo.getContratCpt().getContratCptId().getNumCcptCcpt() + ","
					+ vo.getCategorie().getMontTranch1Cat() + ",null,null,'P',null,null,TO_DATE('" + date
					+ "','dd/mm/yyyy'),null,null,'" + vo.getContratCpt().get_catCcptCcpt() + "')";
		} else if (tranche == 2) {
			req = "UPDATE TRACE_ASSURANCE_FAIEZ SET MONT_TRCH2_CAT = " + vo.getCategorie().getMontTranch2Cat()
					+ ", COD_ETAT_TRCH2 = 'P', DAT_PAY_TRCH2 = TO_DATE('" + date
					+ "','dd/mm/yyyy') WHERE COD_STRC_STRC = " + vo.getContratCpt().getContratCptId().getCodStrcStrc()
					+ " AND NUM_CCPT_CCPT = " + vo.getContratCpt().getContratCptId().getNumCcptCcpt();
		} else if (tranche == 3) {
			req = "UPDATE TRACE_ASSURANCE_FAIEZ SET MONT_TRCH3_CAT = " + vo.getCategorie().getMontTranch3Cat()
					+ ", COD_ETAT_TRCH3 = 'P', DAT_PAY_TRCH3 = TO_DATE('" + date
					+ "','dd/mm/yyyy') WHERE COD_STRC_STRC = " + vo.getContratCpt().getContratCptId().getCodStrcStrc()
					+ " AND NUM_CCPT_CCPT = " + vo.getContratCpt().getContratCptId().getNumCcptCcpt();
		}
		try {
			jt.execute(req);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void UpdateTraceTrancheFaiezRésiduePayée(TraceAssuranceFaiezVo vo, String date, int tranche) {
		String req = null;
		jt = new JdbcTemplate(dataSource);
		if (tranche == 1) {
			req = "UPDATE TRACE_ASSURANCE_FAIEZ SET COD_ETAT_TRCH1 = 'P', DAT_PAY_TRCH1 = TO_DATE('" + date
					+ "','DD/MM/YYYY') WHERE COD_STRC_STRC = " + vo.getContratCpt().getContratCptId().getCodStrcStrc()
					+ " AND NUM_CCPT_CCPT = " + vo.getContratCpt().getContratCptId().getNumCcptCcpt();
		} else if (tranche == 2) {
			req = "UPDATE TRACE_ASSURANCE_FAIEZ SET COD_ETAT_TRCH2 = 'P', DAT_PAY_TRCH2 = TO_DATE('" + date
					+ "','DD/MM/YYYY') WHERE COD_STRC_STRC = " + vo.getContratCpt().getContratCptId().getCodStrcStrc()
					+ " AND NUM_CCPT_CCPT = " + vo.getContratCpt().getContratCptId().getNumCcptCcpt();
		} else if (tranche == 3) {
			req = "UPDATE TRACE_ASSURANCE_FAIEZ SET COD_ETAT_TRCH3 = 'P', DAT_PAY_TRCH3 = TO_DATE('" + date
					+ "','DD/MM/YYYY') WHERE COD_STRC_STRC = " + vo.getContratCpt().getContratCptId().getCodStrcStrc()
					+ " AND NUM_CCPT_CCPT = " + vo.getContratCpt().getContratCptId().getNumCcptCcpt();
		}
		try {
			jt.execute(req);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public String getMaxRefInter(Long codStrc, Long codOper, String date) {
		String req = null;
		jt = new JdbcTemplate(dataSource);
		req = "select max(COD_REF_INTER) from CRO where DAT_OPER_CRO=TO_DATE('" + date
				+ "','DD/MM/YYYY') and COD_OPER_OPER=" + codOper + " and COD_STRC_STRC=" + codStrc;
		String max = (String) jt.queryForObject(req, String.class);
		return max;
	}

	// ////////////////
	public List getListAgencesAssurance() {
		jt = new JdbcTemplate(dataSource);

		String requete = "select J.COD_STRC_STRC, to_char(J.DAT_JRN_JRN,'DD/MM/YYYY') as DAT_JRN_JRN, J.COD_DOM_DOMM  "
				+ " from JOURNEE_STRUCTURE_DOMAINE J, STRUCTURE STR "
				+ " where  J.COD_DOM_DOMM = 13   and J.COD_STRC_STRC=2"
				+ " and DAT_JRN_JRN in (select max(I.DAT_JRN_JRN) from JOURNEE_STRUCTURE_DOMAINE I  "
				+ " where I.COD_STRC_STRC = J.COD_STRC_STRC and I.COD_DOM_DOMM = 13)  "
				+ " and STR.COD_STRC_STRC = J.COD_STRC_STRC and STR.COD_TSTR_TSTR = 1   order by j.cod_strc_strc";

		// System.out.println(requete);
		logger.info(requete);
		List listAgencesAssurance = jt.queryForList(requete);
		logger.info("la requete a ramené " + String.valueOf(listAgencesAssurance.size()));
		logger.info("listAgencesAssurance " + listAgencesAssurance);
		return listAgencesAssurance;

	}

	// ////////////////
}
