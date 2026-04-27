package com.bna.smile.model.domainecompensation.gestionrejet.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.apache.commons.collections.map.ListOrderedMap;
import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.orm.hibernate3.HibernateTemplate;

import com.bna.commun.model.Anr;
import com.bna.commun.model.Arp;
import com.bna.commun.model.CertificationCheques;
import com.bna.commun.model.Cheque;
import com.bna.commun.model.ChequeId;
import com.bna.commun.model.Chequier;
import com.bna.commun.model.ChequierId;
import com.bna.commun.model.Cnp;
import com.bna.commun.model.ComplementCnp;
import com.bna.commun.model.CompteInterne;
import com.bna.commun.model.CompteInterneId;
import com.bna.commun.model.ContratCpt;
import com.bna.commun.model.ContratCptId;
import com.bna.commun.model.Decompte;
import com.bna.commun.model.DetailEffet;
import com.bna.commun.model.DetailEffetId;
import com.bna.commun.model.Devise;
import com.bna.commun.model.OppositionMoyenPaiement;
import com.bna.commun.model.OppositionMoyenPaiementId;
import com.bna.commun.model.Personnel;
import com.bna.commun.model.Structure;
import com.bna.commun.model.SuiviHn;
import com.bna.commun.model.Tache;
import com.bna.commun.model.TacheId;
import com.bna.commun.util.CalanderHandler;
import com.bna.commun.util.ContextHandler;
import com.bna.commun.util.StrHandler;
import com.bna.commun.vo.PrimitiveVO;
import com.bna.smile.model.domainecommun.model.Listes;
import com.bna.smile.model.domainecompensation.gestionrejet.model.ChequeACHVo;
import com.bna.smile.model.domainecompensation.gestionrejet.model.EffetACHVo;
import com.bna.smile.model.domainecompensation.gestionrejet.model.ReservationChqVo;
import com.bna.smile.model.domainecompensation.gestionrejet.model.SwingInfoVo;
import com.bna.smile.web.commun.controller.UtilCtr;
import com.oxia.fwk.context.Context;
import com.oxia.fwk.core.ICriteria;
import com.oxia.fwk.core.IExpression;
import com.oxia.fwk.core.ISearchEngine;
import com.oxia.fwk.searchengine.SearchEngine;
import com.oxia.fwk.util.DateHandler;

public class CompensationDAO {

	private static final Logger logger = Logger.getLogger(CompensationDAO.class);

	public CompensationDAO() {
	}

	protected String sqlQuery;
	protected JdbcTemplate jt;
	protected DataSource dataSource;
	
	private SimpleDateFormat formaterDate = new SimpleDateFormat("dd/MM/yyyy");

	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	public void setSqlQuery(String sqlQuery) {
		this.sqlQuery = sqlQuery;
	}

	public List getListAgencesCompensation() {
		jt = new JdbcTemplate(dataSource);

		String requete =
				"select J.COD_STRC_STRC, to_char(J.DAT_JRN_JRN,'DD/MM/YYYY'), J.COD_DOM_DOMM  from JOURNEE_STRUCTURE_DOMAINE J"
						+ " where  J.COD_DOM_DOMM = 7  and (J.COD_STAT_JSD = 0 or J.COD_STAT_JSD = 3)and DAT_JRN_JRN in  "
						+ " (select max(I.DAT_JRN_JRN) from JOURNEE_STRUCTURE_DOMAINE I "
						+ " where I.COD_STRC_STRC = J.COD_STRC_STRC and I.COD_DOM_DOMM =7  )  order by J.DAT_JRN_JRN asc ";

		logger.info(requete);
		List listAgencesComp = jt.queryForList(requete);
		logger.info("la requete a ramené " + String.valueOf(listAgencesComp.size()));

		return listAgencesComp;

	}

	public String getDateCompta() {
		jt = new JdbcTemplate(dataSource);

		String requete = "select to_char(max(DAT_jou),'dd/MM/yyyy')  from cheque_30 ";

		String dateCompta = (String) jt.queryForObject(requete, String.class);

		return dateCompta;

	}

	// Getting codStrcBct by codStrcStrc
	public Structure findStructure(Long codStructure) {
		Listes listes = new Listes();
		Structure structure = new Structure();
		try {
			Context context = ContextHandler.getContext();
			ISearchEngine searchEngine = (SearchEngine) context.getBean("searchEngine");
			ICriteria criteria = searchEngine.createCriteria();
			IExpression expression = searchEngine.createExpression();
			criteria.add(expression.eq("codStrcStrc", codStructure));
			List res = searchEngine.find(Structure.class, criteria);
			Structure strc = (Structure) searchEngine.get(Structure.class, codStructure);
			return strc;
		} catch (Exception e) {
			com.oxia.fwk.core.Error erreur = new com.oxia.fwk.core.Error();
			StringBuffer text = new StringBuffer("Erreur dans findStructure : ");
			text.append(e.toString());
			erreur.setCode("500");
			erreur.setDescription(text.toString());
			erreur.setKey("StructureDAO");
			listes.addError(erreur);
			return (null);
		}
	}

	public Long getSequenceMvtCompensationEffet() {
		jt = new JdbcTemplate(dataSource);

		Long numeroSequence = (Long) jt.queryForObject("select SEQ_MVT_COM_EFFET.NEXTVAL from dual ", Long.class);
		return numeroSequence;
	}

	public Long verifPositionEffet() {
		jt = new JdbcTemplate(dataSource);

		Long nbr = (Long) jt.queryForObject("select count(*) from effet_recu_tmp where cod_etat_eff is null  ",
				Long.class);
		return nbr;
	}

	public Long verifPositionCheque() {
		jt = new JdbcTemplate(dataSource);

		Long nbr1 = (Long) jt.queryForObject("select count(*) from cheque_30 where cod_etat_chq is null ", Long.class);
		Long nbr2 = (Long) jt.queryForObject("select count(*) from cheque_31 where cod_etat_chq is null ", Long.class);
		Long nbr3 = (Long) jt.queryForObject("select count(*) from cheque_32 where cod_etat_chq is null  ", Long.class);
		Long nbr4 = (Long) jt.queryForObject("select count(*) from cheque_33 where cod_etat_chq is null  ", Long.class);
		return nbr1 + nbr2 + nbr3 + nbr4;
	}

	public Date getDateJourneeTrezor() {
		jt = new JdbcTemplate(dataSource);

		Date date = (Date) jt.queryForObject("select max(dat_jrn_jrn) from journee_structure ", Date.class);
		return date;
	}

	public Date getDateJourneeStructure(Long codStrcBct) {
		jt = new JdbcTemplate(dataSource);

		Date date = (Date) jt.queryForObject(
				"select max(dat_jrn_jrn) from journee_structure where COD_STRC_STRC=(select cod_strc_strc from structure where cod_bct_strc="
						+ codStrcBct + " )",
				Date.class);
		return date;
	}

	public String verifeffetPaye(String numEffet, Date dateEch) {
		jt = new JdbcTemplate(dataSource);

		String Cod_Sta = (String) jt.queryForObject("select COD_STA from ib_effet where ref_eff = '" + numEffet
				+ "' and typ_eff='ENC' and dat_ech=" + dateEch, String.class);
		return Cod_Sta;
	}

	public boolean verifEffetExisteTMP(String numEffet, String agbct, Long codVal, Long codEnr) {
		jt = new JdbcTemplate(dataSource);

		SqlRowSet rs = jt.queryForRowSet("select num_eff_eff from effet_recu_tmp where num_eff_eff= '" + numEffet
				+ "' and COD_AGE_DES='" + agbct + "'  and cod_val_eff=" + codVal + " and cod_enr_eff=" + codEnr);
		if (rs.next())
			return true;
		else
			return false;

	}

	public boolean verifEffetExisteRECU(String numEffet, String agbct, Date dateOpe) {
		jt = new JdbcTemplate(dataSource);

		SqlRowSet rs = jt.queryForRowSet("select num_eff_eff from effet_recu where num_eff_eff= '" + numEffet
				+ "' and COD_AGE_DES='" + agbct + "'  and dat_ope_eff='" + DateHandler.dateToStr(dateOpe) + "'");
		if (rs.next())
			return true;
		else
			return false;

	}

	public Double getCoursAchatBna(String codedev) {
		jt = new JdbcTemplate(dataSource);

		String query = "select MONT_CABA_CCHN from COURS_CHANGE WHERE COD_ETAT_CCHN like 'V' and COD_DEV_DEV = "
				+ codedev + " and DAT_JOUR_CCHN=(SELECT max(DAT_JOUR_CCHN) from COURS_CHANGE where COD_DEV_DEV= "
				+ codedev + ")";

		Double cours;
		try {
			cours = (Double) jt.queryForObject(query, Double.class);
			if (cours == null) {
				cours = new Double(1);
			}
		} catch (Exception e) {
			cours = new Double(1);
		}

		return cours;
	}

	public Double getCoursFixe(String codedev) {
		jt = new JdbcTemplate(dataSource);
		String query = "select MONT_COUR_PAOF from PARITE_OFFICIELLE WHERE  COD_DEV_DEV = " + codedev
				+ " and ANNEE=(SELECT max(ANNEE) from PARITE_OFFICIELLE where COD_DEV_DEV= " + codedev + ")";

		Double courFixe;
		try {
			courFixe = (Double) jt.queryForObject(query, Double.class);
			if (courFixe == null) {
				courFixe = new Double(1);
			}
		} catch (Exception e) {
			courFixe = new Double(1);
		}

		return courFixe;
	}

	public Long getNbreUnitDev(Long codedev) {
		jt = new JdbcTemplate(dataSource);
		String query = "select NBR_UNIT_DEV from devise WHERE  COD_DEV_DEV = " + codedev + ")";
		Long dev = new Long(0);

		try {
			dev = (Long) jt.queryForObject(query, Long.class);
			if (dev == null) {
				return 0L;
			} else
				return dev;
		} catch (Exception e) {
			dev = new Long(0);
		}
		return dev;

	}

	public void initEffetTmp() {
		jt = new JdbcTemplate(dataSource);
		jt.execute("delete from effet_recu_tmp ");

	}

	// requete spécifique pour le test prod

	public List getListAgencesCompensationPilote() {
		jt = new JdbcTemplate(dataSource);

		String requete =
				"select J.COD_STRC_STRC, to_char(j.dat_jrn_jrn,'dd/MM/yyyy'), J.COD_DOM_DOMM  from JOURNEE_STRUCTURE_DOMAINE J"
						+ " where  J.COD_DOM_DOMM = 7 and  (J.COD_STAT_JSD = 0 or J.COD_STAT_JSD = 3 or J.COD_STAT_JSD = 1)and DAT_JRN_JRN in  "
						+ " (select max(I.DAT_JRN_JRN) from JOURNEE_STRUCTURE_DOMAINE I "
						+ " where I.COD_STRC_STRC = J.COD_STRC_STRC and I.COD_DOM_DOMM =7  ) and J.COD_STRC_STRC in (select cod_strc_strc from agence_pilote) order by J.COD_STRC_STRC asc,J.DAT_JRN_JRN asc ";

		// String requete =
		// "select distinct cod_ug, to_char(dat_jou,'dd/MM/yyyy') from cheque_30 where dat_jou<='20122017' order by
		// to_char(dat_jou,'dd/MM/yyyy') asc";
		System.out.println(requete);

		logger.info(requete);
		List listAgencesComp = jt.queryForList(requete);
		logger.info("la requete a ramené " + String.valueOf(listAgencesComp.size()));

		return listAgencesComp;

	}

	public List getListAgencesCompensationPiloteAgence() {
		jt = new JdbcTemplate(dataSource);

		String requete = "select  cod_strc_strc, to_char(sysdate,'dd/MM/yyyy')  from agence_pilote "
				+ "where  cod_strc_strc not in  (select  st.cod_strc_strc from batch_comp_chq b ," + " structure st "
				+ "where trunc(date_oper_oper)=	(select max(j.DAT_JRN_JRN)"
				+ " from JOURNEE_STRUCTURE_DOMAINE j where j.cod_strc_strc=120 and j.COD_DOM_DOMM = 7	"
				+ "and  (j.COD_STAT_JSD = 0 or j.COD_STAT_JSD = 3 or j.COD_STAT_JSD = 1 ))	and st.cod_bct_strc=b.cod_strc_strc) order by COD_STRC_STRC asc ";
		System.out.println(requete);

		logger.info(requete);
		List listAgencesComp = jt.queryForList(requete);
		logger.info("la requete a ramené " + String.valueOf(listAgencesComp.size()));

		return listAgencesComp;

	}

	// requete spécifique pour le test prod

	public String getListAgencesCompensationForces(Date dateop) {
		jt = new JdbcTemplate(dataSource);
		final SimpleDateFormat formatDate1 = new SimpleDateFormat("dd/MM/yyyy");

		String requete = "select COD_STRC_STRC   from batch_comp_chq where ETAT_STRC_INSERT='F' and DATE_OPER_OPER='"
				+ formatDate1.format(dateop) + "' ";
		String strcs = " ";
		List<Long> listAgencesComp = jt.queryForList(requete, Long.class);
		for (Long bct : listAgencesComp) {
			strcs += " ** " + StrHandler.lpad("" + bct, '0', 3);
		}

		return strcs;

	}

	public List getListAgencesConsultPilote() {
		jt = new JdbcTemplate(dataSource);

		String requete = "select p.cod_strc_strc from agence_pilote p";

		logger.info(requete);
		List listAgencesComp = jt.queryForList(requete);
		logger.info("la requete a ramené " + String.valueOf(listAgencesComp.size()));

		return listAgencesComp;

	}

	// requete spécifique pour le test prod

	public List getListAgencesCompensationPilotePFC() {
		jt = new JdbcTemplate(dataSource);

		String requete =
				"select J.COD_STRC_STRC, to_char(J.DAT_JRN_JRN,'DD/MM/YYYY'), J.COD_DOM_DOMM  from JOURNEE_STRUCTURE_DOMAINE J"
						+ " where  J.COD_DOM_DOMM = 7  and DAT_JRN_JRN in  "
						+ " (select max(I.DAT_JRN_JRN) from JOURNEE_STRUCTURE_DOMAINE I "
						+ " where I.COD_STRC_STRC = J.COD_STRC_STRC and I.COD_DOM_DOMM =7  ) "
						+ " and COD_STRC_STRC in (select COD_STRC_STRC from AGENCE_PILOTE P ) order by J.DAT_JRN_JRN desc ";

		logger.info(requete);
		List listAgencesComp = jt.queryForList(requete);
		logger.info("la requete a ramené " + String.valueOf(listAgencesComp.size()));

		return listAgencesComp;

	}

	public String getCodeBctPays(String code) {
		jt = new JdbcTemplate(dataSource);

		String codeRes = (String) jt.queryForObject("select COD_BCT_PAYS from pays where COD_PAYS_PAYS='" + code + "'",
				String.class);
		return codeRes;
	}

	public List<SwingInfoVo> getListEditBatch(String sens, String dateStart, String dateEnd, String valeur,
			String strurtureBct) {
		String dateFilter = "";
		if (dateStart.equals(dateEnd))
			dateFilter = "='" + dateStart + "'";
		else
			dateFilter = "between '" + dateStart + "' and '" + dateEnd + "'";
		jt = new JdbcTemplate(dataSource);
		List<SwingInfoVo> liste = new ArrayList<SwingInfoVo>();
		String requete = "";
		SqlRowSet srs = null;
		if (strurtureBct == null) {

			requete =
					"select nbr_tot_sfile,nbr_tot_inter,nbr_tot_intra,mnt_tot_sfile,mnt_tot_intra,mnt_tot_inter,strc.cod_strc_strc,strc.lib_strc_strc,sf.dat_oper_sfile  "
							+ " from suivi_file_telecompensation sf,structure strc " + " where trunc(dat_oper_sfile)"
							+ dateFilter + " and sf.cod_strc_strc=strc.cod_bct_strc " + " and nom_orig_sfile like '%-"
							+ valeur + "-%'" + " and nom_orig_sfile like '%." + sens
							+ "' order by strc.cod_strc_strc asc ";
			srs = jt.queryForRowSet(requete);

		} else {
			requete =
					"select nbr_tot_sfile,nbr_tot_inter,nbr_tot_intra,mnt_tot_sfile,mnt_tot_intra,mnt_tot_inter,strc.cod_strc_strc,strc.lib_strc_strc,sf.dat_oper_sfile "
							+ "from suivi_file_telecompensation sf,structure strc " + "where trunc(dat_oper_sfile)"
							+ dateFilter + " and sf.cod_strc_strc='" + strurtureBct + "'"
							+ " and sf.cod_strc_strc=strc.cod_bct_strc" + " and nom_orig_sfile like '%-" + valeur
							+ "-%'" + " and nom_orig_sfile like '%." + sens + "' order by strc.cod_strc_strc asc";
			srs = jt.queryForRowSet(requete);
		}
		System.out.println(requete);
		while (srs.next()) {
			SwingInfoVo infoVo = new SwingInfoVo();
			infoVo.setNombre_total("" + srs.getLong("nbr_tot_sfile"));
			infoVo.setMontant_total(StrHandler.formatMontant(srs.getLong("mnt_tot_sfile"), 3L));
			infoVo.setNombre_inter("" + srs.getLong("nbr_tot_inter"));
			infoVo.setNombre_intra("" + srs.getLong("nbr_tot_intra"));
			infoVo.setMontant_inter(StrHandler.formatMontant(srs.getLong("mnt_tot_inter"), 3L));
			infoVo.setMontant_intra(StrHandler.formatMontant(srs.getLong("mnt_tot_intra"), 3L));
			infoVo.setDateComptable(DateHandler.dateToStr(srs.getDate("dat_oper_sfile")));
			infoVo.setNT20_21(srs.getLong("nbr_tot_inter"));
			infoVo.setMNT20_21(srs.getLong("mnt_tot_inter"));
			infoVo.setNombre_intra("" + srs.getLong("nbr_tot_intra"));
			infoVo.setStructure(StrHandler.lpad(srs.getString("cod_strc_strc"), '0', 3));
			infoVo.setBct(StrHandler.lpad(srs.getString("cod_bct_strc"), '0', 3));
			infoVo.setLib_structure(srs.getString("lib_strc_strc"));

			liste.add(infoVo);

		}

		logger.info("la requete Edition a ramené " + String.valueOf(liste.size()));

		return liste;

	}

	public List<SwingInfoVo> calculGlobalAgence(String dateOperation, String valeur) {

		List<SwingInfoVo> swingInfoVos = new ArrayList<SwingInfoVo>();
		ListOrderedMap ListAg = null;
		Context context = ContextHandler.getContext();
		// System.out.println("Globale");
		ISearchEngine searchEngine = (SearchEngine) context.getBean("searchEngine");

		List listAgences = getListAgencesConsultPilote();
		if (valeur.equals("30")) {

			Long mntTot30 = 0L;
			Long nbrTot30 = 0L;
			Long mntTot31 = 0L;
			Long nbrTot31 = 0L;
			Long mntTot32 = 0L;
			Long nbrTot32 = 0L;
			Long mntTot33 = 0L;
			Long nbrTot33 = 0L;
			if (listAgences != null && listAgences.size() > 0) {
				for (Iterator it1 = listAgences.iterator(); it1.hasNext();) {
					ListAg = (ListOrderedMap) it1.next();
					if ((ListAg.getValue(0)).toString() != null) {
						SwingInfoVo swingInfoVo = new SwingInfoVo();
						// System.out.println((ListAg.getValue(0)).toString());
						Structure strc = findStructure(new Long((ListAg.getValue(0)).toString()));
						String structure = (ListAg.getValue(0)).toString();
						swingInfoVo.setStructure(structure);
						SwingInfoVo swingInfoVo_30 = getListGlobal(dateOperation, "-30-", strc.getCodBctStrc());
						SwingInfoVo swingInfoVo_31 = getListGlobal(dateOperation, "-31-", strc.getCodBctStrc());
						SwingInfoVo swingInfoVo_32 = getListGlobal(dateOperation, "-32-", strc.getCodBctStrc());
						SwingInfoVo swingInfoVo_33 = getListGlobal(dateOperation, "-33-", strc.getCodBctStrc());
						swingInfoVo.setNombre_30(swingInfoVo_30.getNombre_total());
						swingInfoVo.setMontant_30(
								StrHandler.formatMontant(Long.valueOf(swingInfoVo_30.getMontant_total()), 3L));
						swingInfoVo.setNombre_31(swingInfoVo_31.getNombre_total());
						swingInfoVo.setMontant_31(
								StrHandler.formatMontant(Long.valueOf(swingInfoVo_31.getMontant_total()), 3L));
						swingInfoVo.setNombre_32(swingInfoVo_32.getNombre_total());
						swingInfoVo.setMontant_32(
								StrHandler.formatMontant(Long.valueOf(swingInfoVo_32.getMontant_total()), 3L));
						swingInfoVo.setNombre_33(swingInfoVo_33.getNombre_total());
						swingInfoVo.setMontant_33(
								StrHandler.formatMontant(Long.valueOf(swingInfoVo_33.getMontant_total()), 3L));
						mntTot30 = mntTot30 + Long.valueOf(swingInfoVo_30.getMontant_total());
						nbrTot30 = nbrTot30 + Long.valueOf(swingInfoVo_30.getNombre_total());
						mntTot31 = mntTot31 + Long.valueOf(swingInfoVo_31.getMontant_total());
						nbrTot31 = nbrTot31 + Long.valueOf(swingInfoVo_31.getNombre_total());
						mntTot32 = mntTot32 + Long.valueOf(swingInfoVo_32.getMontant_total());
						nbrTot32 = nbrTot32 + Long.valueOf(swingInfoVo_32.getNombre_total());
						mntTot33 = mntTot33 + Long.valueOf(swingInfoVo_33.getMontant_total());
						nbrTot33 = nbrTot33 + Long.valueOf(swingInfoVo_33.getNombre_total());

						swingInfoVos.add(swingInfoVo);
					}
				}
			}
			swingInfoVos.get(0).setNombre_total_30("" + nbrTot30);
			swingInfoVos.get(0).setMontant_total_30(StrHandler.formatMontant(mntTot30, 3L));
			swingInfoVos.get(0).setNombre_total_31("" + nbrTot31);
			swingInfoVos.get(0).setMontant_total_31(StrHandler.formatMontant(mntTot31, 3L));
			swingInfoVos.get(0).setNombre_total_32("" + nbrTot32);
			swingInfoVos.get(0).setMontant_total_32(StrHandler.formatMontant(mntTot32, 3L));
			swingInfoVos.get(0).setNombre_total_33("" + nbrTot33);
			swingInfoVos.get(0).setMontant_total_33(StrHandler.formatMontant(mntTot33, 3L));

			// Map recap = getRecap(dateOperation);
			/*
			 * swingInfoVos.get(0).setMT30(StrHandler.formatMontant(mntTot30+Long
			 * .valueOf(recap.get("MT30").toString()),3L)); swingInfoVos.get(0).setMT31
			 * (StrHandler.formatMontant(mntTot31+Long .valueOf(recap.get("MT31").toString()),3L));
			 * swingInfoVos.get(0).setMT32 (StrHandler.formatMontant(mntTot32+Long
			 * .valueOf(recap.get("MT32").toString()),3L)); swingInfoVos.get(0).setMT33
			 * (StrHandler.formatMontant(mntTot33+Long .valueOf(recap.get("MT33").toString()),3L));
			 */

			swingInfoVos.get(0).setMT30(StrHandler.formatMontant(mntTot30, 3L));
			swingInfoVos.get(0).setMT31(StrHandler.formatMontant(mntTot31, 3L));
			swingInfoVos.get(0).setMT32(StrHandler.formatMontant(mntTot32, 3L));
			swingInfoVos.get(0).setMT33(StrHandler.formatMontant(mntTot33, 3L));

			swingInfoVos.get(0).setNT30("" + nbrTot30);
			swingInfoVos.get(0).setNT31("" + nbrTot31);
			swingInfoVos.get(0).setNT32("" + nbrTot32);
			swingInfoVos.get(0).setNT33("" + nbrTot33);

		} else {
			Long mntTot40_21 = 0L;
			Long nbrTot40_21 = 0L;
			Long mntTot40_22 = 0L;
			Long nbrTot40_22 = 0L;
			Long mntTot40_25 = 0L;
			Long nbrTot40_25 = 0L;
			Long mntTot41_21 = 0L;
			Long nbrTot41_21 = 0L;
			Long mntTot41_22 = 0L;
			Long nbrTot41_22 = 0L;
			Long mntTot41_25 = 0L;
			Long nbrTot41_25 = 0L;
			Long mntTot42_21 = 0L;
			Long nbrTot42_21 = 0L;

			if (listAgences != null && listAgences.size() > 0) {
				for (Iterator it1 = listAgences.iterator(); it1.hasNext();) {
					ListAg = (ListOrderedMap) it1.next();
					if ((ListAg.getValue(0)).toString() != null) {
						SwingInfoVo swingInfoVo = new SwingInfoVo();
						Structure strc = findStructure(new Long((ListAg.getValue(0)).toString()));
						String structure = (ListAg.getValue(0)).toString();

						swingInfoVo.setStructure(structure);
						SwingInfoVo swingInfoVo_40_21 = getListGlobal(dateOperation, "-40-21-", strc.getCodBctStrc());
						SwingInfoVo swingInfoVo_40_22 = getListGlobal(dateOperation, "-40-22-", strc.getCodBctStrc());
						SwingInfoVo swingInfoVo_40_25 = getListGlobal(dateOperation, "-40-25-", strc.getCodBctStrc());
						SwingInfoVo swingInfoVo_41_21 = getListGlobal(dateOperation, "-41-21-", strc.getCodBctStrc());
						SwingInfoVo swingInfoVo_41_22 = getListGlobal(dateOperation, "-41-22-", strc.getCodBctStrc());
						SwingInfoVo swingInfoVo_41_25 = getListGlobal(dateOperation, "-41-25-", strc.getCodBctStrc());
						SwingInfoVo swingInfoVo_42_21 = getListGlobal(dateOperation, "-42-21-", strc.getCodBctStrc());
						swingInfoVo.setNombre_40_21(swingInfoVo_40_21.getNombre_total());
						swingInfoVo.setMontant_40_21(
								StrHandler.formatMontant(Long.valueOf(swingInfoVo_40_21.getMontant_total()), 3L));
						swingInfoVo.setNombre_40_22(swingInfoVo_40_22.getNombre_total());
						swingInfoVo.setMontant_40_22(
								StrHandler.formatMontant(Long.valueOf(swingInfoVo_40_22.getMontant_total()), 3L));
						swingInfoVo.setNombre_40_25(swingInfoVo_40_25.getNombre_total());
						swingInfoVo.setMontant_40_25(
								StrHandler.formatMontant(Long.valueOf(swingInfoVo_40_25.getMontant_total()), 3L));
						swingInfoVo.setNombre_41_21(swingInfoVo_41_21.getNombre_total());
						swingInfoVo.setMontant_41_21(
								StrHandler.formatMontant(Long.valueOf(swingInfoVo_41_21.getMontant_total()), 3L));
						swingInfoVo.setNombre_41_22(swingInfoVo_41_22.getNombre_total());
						swingInfoVo.setMontant_41_22(
								StrHandler.formatMontant(Long.valueOf(swingInfoVo_41_22.getMontant_total()), 3L));
						swingInfoVo.setNombre_41_25(swingInfoVo_41_25.getNombre_total());
						swingInfoVo.setMontant_41_25(
								StrHandler.formatMontant(Long.valueOf(swingInfoVo_41_25.getMontant_total()), 3L));
						swingInfoVo.setNombre_42_21(swingInfoVo_42_21.getNombre_total());
						swingInfoVo.setMontant_42_21(
								StrHandler.formatMontant(Long.valueOf(swingInfoVo_42_21.getMontant_total()), 3L));
						mntTot40_21 = mntTot40_21 + Long.valueOf(swingInfoVo_40_21.getMontant_total());
						nbrTot40_21 = nbrTot40_21 + Long.valueOf(swingInfoVo_40_21.getNombre_total());
						mntTot40_22 = mntTot40_22 + Long.valueOf(swingInfoVo_40_22.getMontant_total());
						nbrTot40_22 = nbrTot40_22 + Long.valueOf(swingInfoVo_40_22.getNombre_total());
						mntTot40_25 = mntTot40_25 + Long.valueOf(swingInfoVo_40_25.getMontant_total());
						nbrTot40_25 = nbrTot40_25 + Long.valueOf(swingInfoVo_40_25.getNombre_total());
						mntTot41_21 = mntTot41_21 + Long.valueOf(swingInfoVo_41_21.getMontant_total());
						nbrTot41_21 = nbrTot41_21 + Long.valueOf(swingInfoVo_41_21.getNombre_total());
						mntTot41_22 = mntTot41_22 + Long.valueOf(swingInfoVo_41_22.getMontant_total());
						nbrTot41_22 = nbrTot41_22 + Long.valueOf(swingInfoVo_41_22.getNombre_total());
						mntTot41_25 = mntTot41_25 + Long.valueOf(swingInfoVo_41_25.getMontant_total());
						nbrTot41_25 = nbrTot41_25 + Long.valueOf(swingInfoVo_41_25.getNombre_total());
						mntTot42_21 = mntTot42_21 + Long.valueOf(swingInfoVo_42_21.getMontant_total());
						nbrTot42_21 = nbrTot42_21 + Long.valueOf(swingInfoVo_42_21.getNombre_total());

						swingInfoVos.add(swingInfoVo);

					}
				}
			}
			// System.out.println(nbrTot41_21 + "/" +
			// StrHandler.formatMontant(mntTot41_21, 3L));
			swingInfoVos.get(0).setNombre_total_40_21("" + nbrTot40_21);
			swingInfoVos.get(0).setMontant_total_40_21(StrHandler.formatMontant(mntTot40_21, 3L));
			swingInfoVos.get(0).setNombre_total_40_22("" + nbrTot40_22);
			swingInfoVos.get(0).setMontant_total_40_22(StrHandler.formatMontant(mntTot40_22, 3L));
			swingInfoVos.get(0).setNombre_total_40_25("" + nbrTot40_25);
			swingInfoVos.get(0).setMontant_total_40_25(StrHandler.formatMontant(mntTot40_25, 3L));
			swingInfoVos.get(0).setNombre_total_41_21("" + nbrTot41_21);
			swingInfoVos.get(0).setMontant_total_41_21(StrHandler.formatMontant(mntTot41_21, 3L));
			swingInfoVos.get(0).setNombre_total_41_22("" + nbrTot41_22);
			swingInfoVos.get(0).setMontant_total_41_22(StrHandler.formatMontant(mntTot41_22, 3L));
			swingInfoVos.get(0).setNombre_total_41_25("" + nbrTot41_25);
			swingInfoVos.get(0).setMontant_total_41_25(StrHandler.formatMontant(mntTot41_25, 3L));
			swingInfoVos.get(0).setNombre_total_42_21("" + nbrTot42_21);
			swingInfoVos.get(0).setMontant_total_42_21(StrHandler.formatMontant(mntTot42_21, 3L));
		}
		return swingInfoVos;
	}

	public SwingInfoVo getListGlobal(String dateOperation, String valeur, String strurtureBct) {
		jt = new JdbcTemplate(dataSource);
		List<SwingInfoVo> liste = new ArrayList<SwingInfoVo>();
		String requete = "";
		SqlRowSet srs = null;

		requete = "select nbr_tot_sfile,mnt_tot_sfile,strc.cod_strc_strc,strc.lib_strc_strc,sf.dat_oper_sfile  "
				+ " from suivi_file_telecompensation sf,structure strc " + " where trunc(dat_oper_sfile)='"
				+ dateOperation + "' and sf.cod_strc_strc=strc.cod_bct_strc " + " and nom_orig_sfile like '%" + valeur
				+ "%'" + " and nom_orig_sfile like '%.RCP'"
				// + sens
				+ " and sf.cod_strc_strc='" + strurtureBct + "' order by strc.cod_strc_strc asc ";
		srs = jt.queryForRowSet(requete);

		// System.out.println(requete);
		SwingInfoVo infoVo = new SwingInfoVo();
		while (srs.next()) {
			Long nbr = srs.getLong("nbr_tot_sfile");
			Long mnt = srs.getLong("mnt_tot_sfile");

			// System.out.println(nbr + "/" + mnt);
			if (nbr == null)
				infoVo.setNombre_total("0");
			else
				infoVo.setNombre_total("" + nbr);
			if (mnt == null)
				infoVo.setMontant_total("0");
			else
				infoVo.setMontant_total("" + mnt);

		}

		// logger.info("la requete Edition a ramené " +
		// String.valueOf(liste.size()));

		return infoVo;

	}

	// public Map getRecap(String date) {
	//
	// jt= new JdbcTemplate(dataSource);
	// String sql =
	// "select SUM(MNT_30) AS MT30,SUM(MNT_31) AS MT31,SUM(MNT_32) AS MT32,SUM(MNT_33) AS MT33,SUM(NBRE_30) AS
	// NT30,SUM(NBRE_31) AS NT31,SUM(NBRE_32) AS NT32,SUM(NBRE_33) AS NT33 from recap_envoi where
	// to_char(dat_op,'DD/MM/YYYY')='"+date+"' and cod_ag not between 703 and 900 and cod_ag not in (select
	// cod_strc_strc from agence_pilote) ";
	// Map l = jt.queryForMap(sql);
	// return l;
	//
	//
	// }
	public Map getRecapEffet(String date) {

		jt = new JdbcTemplate(dataSource);
		String sql =
				"select SUM(MNT_30) AS MT30,SUM(MNT_31) AS MT31,SUM(MNT_32) AS MT32,SUM(MNT_33) AS MT33,SUM(NBRE_30) AS NT30,SUM(NBRE_31) AS NT31,SUM(NBRE_32) AS NT32,SUM(NBRE_33) AS NT33 from recap_envoi where  to_char(dat_op,'DD/MM/YYYY')='"
						+ date + "' and cod_ag not between 703 and 900 ";
		Map l = jt.queryForMap(sql);
		return l;

	}

	public List<SwingInfoVo> getListEditBatchJour(String dateJour) {
		jt = new JdbcTemplate(dataSource);
		List<SwingInfoVo> liste = new ArrayList<SwingInfoVo>();
		String requete = "";
		SqlRowSet srs = null;

		requete =
				"select nbr_tot_sfile,nbr_tot_inter,nbr_tot_intra,mnt_tot_sfile,mnt_tot_intra,mnt_tot_inter,strc.cod_strc_strc,strc.lib_strc_strc,sf.dat_oper_sfile  "
						+ " from suivi_file_telecompensation sf,structure strc " + " where trunc(dat_oper_sfile)='"
						+ dateJour + "' and sf.cod_strc_strc=strc.cod_bct_strc "
						+ " and nom_orig_sfile like '%-30-%' and nom_orig_sfile like '%.RCP'  order by strc.cod_strc_strc asc ";
		srs = jt.queryForRowSet(requete);
		System.out.println(requete);

		System.out.println(requete);
		while (srs.next()) {
			SwingInfoVo infoVo = new SwingInfoVo();
			infoVo.setNombre_total("" + srs.getLong("nbr_tot_sfile"));
			infoVo.setMontant_total(StrHandler.formatMontant(srs.getLong("mnt_tot_sfile"), 3L));
			infoVo.setNombre_inter("" + srs.getLong("nbr_tot_inter"));
			infoVo.setNombre_intra("" + srs.getLong("nbr_tot_intra"));
			infoVo.setMontant_inter(StrHandler.formatMontant(srs.getLong("mnt_tot_inter"), 3L));
			infoVo.setMontant_intra(StrHandler.formatMontant(srs.getLong("mnt_tot_intra"), 3L));
			infoVo.setDateComptable(DateHandler.dateToStr(srs.getDate("dat_oper_sfile")));

			infoVo.setStructure(StrHandler.lpad(srs.getString("cod_strc_strc"), '0', 3));
			infoVo.setLib_structure(srs.getString("lib_strc_strc"));

			liste.add(infoVo);

		}

		logger.info("la requete Edition a ramené " + String.valueOf(liste.size()));

		return liste;

	}

	/*****
	 * 
	 * test batch
	 */

	public List<Anr> getAnrs() {
		jt = new JdbcTemplate(dataSource);
		List<Anr> anrs = new ArrayList<Anr>();
		try {
			jt = new JdbcTemplate(dataSource);
			String req =
					"select DAT_OPE_CHQ,NUM_CHQ_CHQ,RIB_TIR_CHQ,RIB_BEN_CHQ,DAT_ANR_ANR,DAT_EDIT_ANR,DAT_MIG_ANR from anr where dat_anr_anr>='22122016' and DAT_MIG_ANR is null and (num_chq_chq,RIB_TIR_CHQ) not in (select num_chq,rib_tir from det_anr)";

			SqlRowSet srs = jt.queryForRowSet(req);

			while (srs.next()) {
				Anr anr = new Anr();
				ChequeId id = new ChequeId(srs.getLong(2), srs.getString(3), srs.getString(4));
				anr.setDatAnrAnr(srs.getDate(5));
				anr.setDatEditAnr(srs.getDate(6));
				anr.setChequeId(id);
				anrs.add(anr);

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return anrs;
	}

	public List<Arp> getArps() {
		jt = new JdbcTemplate(dataSource);
		List<Arp> anrs = new ArrayList<Arp>();
		try {
			jt = new JdbcTemplate(dataSource);
			String req =
					"select DAT_OPE_CHQ,NUM_CHQ_CHQ,RIB_TIR_CHQ,RIB_BEN_CHQ,DAT_ARP_ARP,DAT_MIG_ARP from arp where DAT_MIG_ARP is null and (num_chq_chq,RIB_TIR_CHQ) not in (select num_chq,rib_tir from det_arp) and dat_arp_arp>='01012018' and dat_arp_arp<='25042018'";

			SqlRowSet srs = jt.queryForRowSet(req);

			while (srs.next()) {
				Arp arp = new Arp();
				ChequeId id = new ChequeId(srs.getLong(2), srs.getString(3), srs.getString(4));
				arp.setDatArpArp(srs.getDate(5));
				arp.setChequeId(id);
				anrs.add(arp);

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return anrs;
	}

	private List<PrimitiveVO> getComplement(List<ComplementCnp> liste, Long nPers) {
		List<PrimitiveVO> primitiveVOs = new ArrayList<PrimitiveVO>();

		for (int i = 0; i < liste.size(); i++) {
			if (liste.get(i).getCodNperCcnp().equals(nPers)) {

				PrimitiveVO primitiveVO = new PrimitiveVO();
				if (nPers.equals(2L)) {
					primitiveVO.setVString(liste.get(i).getIdeRcsCcnp());

				} else {
					primitiveVO.setVString(liste.get(i).getNumPieCcnp());

				}
				Long typePiece = null;
				if (liste.get(i).getCodTperCcnp().equals("C")) {
					typePiece = 2L;
				} else if (liste.get(i).getCodTperCcnp().equals("S")) {
					typePiece = 4L;
				} else if (liste.get(i).getCodTperCcnp().equals("P")) {
					typePiece = 3L;
				} else {
					typePiece = 9L;
				}
				primitiveVO.setVLong(typePiece);
				primitiveVO.setVDouble(Double.valueOf(liste.get(i).getCodQuaCcnp()));
				primitiveVOs.add(primitiveVO);

			}
		}

		return primitiveVOs;
	}

	public Long getTypeCompteCci(Long codPrd) {
		jt = new JdbcTemplate(dataSource);

		Long typeCompte =
				(Long) jt.queryForObject("select TYPE_CPT from produit_cci where cod_prd_prd=" + codPrd, Long.class);

		return typeCompte;

	}

	public Long getNatureCompteCci(Long codPrd) {
		jt = new JdbcTemplate(dataSource);

		Long natCompte =
				(Long) jt.queryForObject("select nat_cpt from produit_cci where cod_prd_prd=" + codPrd, Long.class);

		return natCompte;

	}

	public Long getNumAnr(Date dateOpe, Long codAge) {
		jt = new JdbcTemplate(dataSource);

		Long numAnr = jt.queryForLong("select count(*)  from det_anr where CODE_AGE_DES=" + codAge + " and date_ope='"
				+ DateHandler.dateToStr(dateOpe) + "'");
		return numAnr + 1;
	}

	/*********** Cheque man 88 89 87 ***************/
	private Float getMontant(Long montant) {
		try {
			return Float.valueOf(montant / 1000);
		} catch (NullPointerException e) {
			return 0f;
		}
	}

	public void inserDetAnr(Anr anr) {
		jt = new JdbcTemplate(dataSource);
		Cheque cheque = anr.getCheque();
		Cnp cnp = cheque.getCnp();
		SuiviHn suiviHn = cnp.getSuiviHn();
		ContratCpt contratCpt = UtilCtr.getContratCptByRIB(cheque.getChequeId().getRibTirChq());
		List<ComplementCnp> listeComp = new ArrayList<ComplementCnp>(cnp.getComplementCnps());
		SimpleDateFormat formaterDate = new SimpleDateFormat("ddMMyy");

		try {
			Long NUM_ANR = Long.valueOf("" + formaterDate.format(anr.getDatEditAnr()) + ""
					+ getNumAnr(anr.getDatEditAnr(), Long.valueOf(cheque.getCodAgdeChq())));
			Long NATURE_REMET = 1L;

			Long CODE_BAN_DES = Long.valueOf(cheque.getCodBaemChq());
			Long CODE_AGE_DES = Long.valueOf(cheque.getCodAgemChq());
			Long CODE_AGE = Long.valueOf(cheque.getCodAgdeChq());
			Long CODE_BAN = Long.valueOf(cheque.getCodBadeChq());

			Date DATE_OPE = anr.getDatEditAnr();
			Long NUM_LOT = 1L;
			Long CODE_DEV = 788L;
			Float MNT_CHQ = getMontant(cheque.getMntChqChq());
			Long NUM_CHQ = cheque.getChequeId().getNumChqChq();
			String RIB_TIR = cheque.getChequeId().getRibTirChq();

			String RIB_BENEF = cheque.getChequeId().getRibBenChq();
			Long NUM_CNP = cnp.getNumCnpCnp();
			Date DATE_EMI_CHQ = cheque.getDatEmiChq();
			String LIEU_EMI_CHQ = cheque.getCodLemiChq();
			Date DATE_CNP = cnp.getDatCnpCnp();
			Date DATE_PRES_CHQ = cheque.getPreavis().getDatPrePre();
			Date DATE_PREAVIS = cheque.getPreavis().getDatPrePre();
			Float MNT_PROVISION = getMontant(cheque.getPreavis().getMntProPre());
			Date DATE_DELIV_CHQ = cheque.getDatDelChq();
			Long NBR_ENR_COM = Long.valueOf(listeComp.size());
			Date DATE_ANR = anr.getDatAnrAnr();
			String hnNomPrn = suiviHn.getNomPrnShn() + " " + suiviHn.getNomNomShn();
			if (hnNomPrn != null && hnNomPrn.contains("null")) {
				hnNomPrn = hnNomPrn.replace("null", "");
			}

			hnNomPrn = hnNomPrn.length() > 30 ? hnNomPrn.substring(0, 29) : hnNomPrn;

			String NOM_PREN_HUISS_NOT = hnNomPrn;
			Date DATE_EXPLOIT = suiviHn.getDatExpLrShn();
			Date DATE_LETT_REC = null;
			Long TYPE_COMPTE = getTypeCompteCci(contratCpt.getContratCptId().getCodPrdPrd());

			Long NAT_COMPTE = getNatureCompteCci(contratCpt.getContratCptId().getCodPrdPrd());
			Date DATE_CHARG = DATE_OPE;
			Date JOURNEE = DATE_OPE;
			String SOURCE = "M";
			String DATE_DECL = null;
			String DECLARATION = null;

			List<PrimitiveVO> listePers = getComplement(listeComp, 2L);

			String TYPE_PIECE_PM = "";
			String NUM_PIECE_PM = "";
			if (!listePers.isEmpty()) {
				TYPE_PIECE_PM = "" + listePers.get(0).getVLong();
				NUM_PIECE_PM = "" + listePers.get(0).getVString();
			}
			String TYPE_PIECE_PP1 = "";
			String NUM_PIECE_PP1 = "";
			String CODE_POUVOIR_PP1 = "";
			String TYPE_PIECE_PP2 = "";
			String NUM_PIECE_PP2 = "";
			String CODE_POUVOIR_PP2 = "";
			String TYPE_PIECE_PP3 = "";
			String NUM_PIECE_PP3 = "";
			String CODE_POUVOIR_PP3 = "";

			listePers = getComplement(listeComp, 1L);
			Iterator<PrimitiveVO> vo = listePers.iterator();
			if (vo.hasNext()) {
				PrimitiveVO pm = vo.next();
				TYPE_PIECE_PP1 = "" + pm.getVLong();
				NUM_PIECE_PP1 = pm.getVString();
				CODE_POUVOIR_PP1 = "" + pm.getVDouble().longValue();
			}
			if (vo.hasNext()) {
				PrimitiveVO pm = vo.next();
				TYPE_PIECE_PP2 = "" + pm.getVLong();
				NUM_PIECE_PP2 = pm.getVString();
				CODE_POUVOIR_PP2 = "" + pm.getVDouble().longValue();
				;
			}
			if (vo.hasNext()) {
				PrimitiveVO pm = vo.next();
				TYPE_PIECE_PP3 = "" + pm.getVLong();
				NUM_PIECE_PP3 = pm.getVString();
				CODE_POUVOIR_PP3 = "" + pm.getVDouble().longValue();
			}

			Long MOTIF_REJET = Long.valueOf(cheque.getPreavis().getCodMrejPre());

			Long NUM_MATR_USER = Long.valueOf("9999");

			jt.update(
					"Insert into DET_ANR (NUM_ANR,NATURE_REMET,CODE_BAN,CODE_AGE,DATE_OPE,NUM_LOT,CODE_DEV,MNT_CHQ,NUM_CHQ,RIB_TIR,CODE_AGE_DES,CODE_BAN_DES,RIB_BENEF,NUM_CNP,DATE_EMI_CHQ,LIEU_EMI_CHQ,DATE_CNP,DATE_PRES_CHQ,DATE_PREAVIS,MNT_PROVISION,DATE_DELIV_CHQ,NBR_ENR_COM,DATE_ANR,NOM_PREN_HUISS_NOT,DATE_EXPLOIT,DATE_LETT_REC,TYPE_COMPTE,NAT_COMPTE,DATE_CHARG,JOURNEE,SOURCE,DATE_DECL,DECLARATION,TYPE_PIECE_PM,NUM_PIECE_PM,TYPE_PIECE_PP1,NUM_PIECE_PP1,CODE_POUVOIR_PP1,TYPE_PIECE_PP2,NUM_PIECE_PP2,CODE_POUVOIR_PP2,TYPE_PIECE_PP3,NUM_PIECE_PP3,CODE_POUVOIR_PP3,MOTIF_REJET,NUM_MATR_USER) values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)",
					new Object[]{ NUM_ANR, NATURE_REMET, CODE_BAN, CODE_AGE, DATE_OPE, NUM_LOT, CODE_DEV, MNT_CHQ,
							NUM_CHQ, RIB_TIR, CODE_AGE_DES, CODE_BAN_DES, RIB_BENEF, NUM_CNP, DATE_EMI_CHQ,
							LIEU_EMI_CHQ, DATE_CNP, DATE_PRES_CHQ, DATE_PREAVIS, MNT_PROVISION, DATE_DELIV_CHQ,
							NBR_ENR_COM, DATE_ANR, NOM_PREN_HUISS_NOT, DATE_EXPLOIT, DATE_LETT_REC, TYPE_COMPTE,
							NAT_COMPTE, DATE_CHARG, JOURNEE, SOURCE, DATE_DECL, "H", TYPE_PIECE_PM, NUM_PIECE_PM,
							TYPE_PIECE_PP1, NUM_PIECE_PP1, CODE_POUVOIR_PP1, TYPE_PIECE_PP2, NUM_PIECE_PP2,
							CODE_POUVOIR_PP2, TYPE_PIECE_PP3, NUM_PIECE_PP3, CODE_POUVOIR_PP3, MOTIF_REJET,
							NUM_MATR_USER });

		} catch (Exception ex) {
			ex.printStackTrace();
			throw new RuntimeException();
		}

	}

	public Long getNumCnp(Date dateOpe, Long codAge) {
		jt = new JdbcTemplate(dataSource);

		Long numCnp = jt.queryForLong("select count(*)  from det_cnp where CODE_AGE_DES=" + codAge + " and date_ope='"
				+ DateHandler.dateToStr(dateOpe) + "'");
		return numCnp + 1;
	}

	public void inserDetArp(Cnp cnp) {
		jt = new JdbcTemplate(dataSource);
		ContratCpt contratCpt = UtilCtr.getContratCptByRIB(cnp.getChequeId().getRibTirChq());
		Cheque cheque = cnp.getCheque();
		List<ComplementCnp> listeComp = new ArrayList<ComplementCnp>(cnp.getComplementCnps());
		SimpleDateFormat formaterDate = new SimpleDateFormat("ddMMyy");

		try {
			Long NUM_ARP = Long.valueOf("" + formaterDate.format(cnp.getDatCnpCnp()) + ""
					+ getNumCnp(cnp.getDatCnpCnp(), Long.valueOf(cheque.getCodAgdeChq())));
			Long NATURE_REMET = 1L;
			Long CODE_BAN_DES = Long.valueOf(cheque.getCodBaemChq());
			Long CODE_AGE_DES = Long.valueOf(cheque.getCodAgemChq());
			Long CODE_AGE = Long.valueOf(cheque.getCodAgdeChq());
			Long CODE_BAN = Long.valueOf(cheque.getCodBadeChq());

			Date DATE_OPE = cnp.getDatEditCnp();
			Long NUM_LOT = 1L;
			Long CODE_DEV = 788L;
			Float MNT_CHQ = getMontant(cheque.getMntChqChq());
			Long NUM_CHQ = cheque.getChequeId().getNumChqChq();
			String RIB_TIR = cheque.getChequeId().getRibTirChq();

			String RIB_BENEF = cheque.getChequeId().getRibBenChq();
			Long NUM_CNP = cnp.getNumCnpCnp();
			Date DATE_EMI_CHQ = cheque.getDatEmiChq();
			String LIEU_EMI_CHQ = cheque.getCodLemiChq();
			Date DATE_CNP = cnp.getDatCnpCnp();
			Date DATE_PRES_CHQ = cheque.getPreavis().getDatPrePre();
			Date DATE_PREAVIS = cheque.getPreavis().getDatPrePre();
			Float MNT_PROVISION = getMontant(cheque.getPreavis().getMntProPre());
			Date DATE_DELIV_CHQ = cheque.getDatDelChq();
			Long NBR_ENR_COM = Long.valueOf(listeComp.size());

			Decompte decompte = cheque.getDecompte();
			Date DATE_REGUL = decompte.getDatRegChDec();
			Long COD_DEV_POS = 788L;
			Float MNT_REG = getMontant(decompte.getMntRegChDec());
			Float MNT_REG_INT = getMontant(decompte.getMntIntDec());

			Long TYPE_COMPTE = getTypeCompteCci(contratCpt.getContratCptId().getCodPrdPrd());

			Long NAT_COMPTE = getNatureCompteCci(contratCpt.getContratCptId().getCodPrdPrd());
			Date DATE_CHARG = DATE_OPE;
			Date JOURNEE = DATE_OPE;
			String SOURCE = "M";
			String DATE_DECL = null;
			String DECLARATION = null;

			List<PrimitiveVO> listePers = getComplement(listeComp, 2L);

			String TYPE_PIECE_PM = "";
			String NUM_PIECE_PM = "";
			if (!listePers.isEmpty()) {
				TYPE_PIECE_PM = "" + listePers.get(0).getVLong();
				NUM_PIECE_PM = "" + listePers.get(0).getVString();
			}
			String TYPE_PIECE_PP1 = "";
			String NUM_PIECE_PP1 = "";
			String CODE_POUVOIR_PP1 = "";
			String TYPE_PIECE_PP2 = "";
			String NUM_PIECE_PP2 = "";
			String CODE_POUVOIR_PP2 = "";
			String TYPE_PIECE_PP3 = "";
			String NUM_PIECE_PP3 = "";
			String CODE_POUVOIR_PP3 = "";

			listePers = getComplement(listeComp, 1L);
			Iterator<PrimitiveVO> vo = listePers.iterator();
			if (vo.hasNext()) {
				PrimitiveVO pm = vo.next();
				TYPE_PIECE_PP1 = "" + pm.getVLong();
				NUM_PIECE_PP1 = pm.getVString();
				CODE_POUVOIR_PP1 = "" + pm.getVDouble().longValue();
			}
			if (vo.hasNext()) {
				PrimitiveVO pm = vo.next();
				TYPE_PIECE_PP2 = "" + pm.getVLong();
				NUM_PIECE_PP2 = pm.getVString();
				CODE_POUVOIR_PP2 = "" + pm.getVDouble().longValue();
				;
			}
			if (vo.hasNext()) {
				PrimitiveVO pm = vo.next();
				TYPE_PIECE_PP3 = "" + pm.getVLong();
				NUM_PIECE_PP3 = pm.getVString();
				CODE_POUVOIR_PP3 = "" + pm.getVDouble().longValue();
			}

			Long MOTIF_REJET = Long.valueOf(cheque.getPreavis().getCodMrejPre());

			Long NUM_MATR_USER = Long.valueOf("9999");
			Date DATE_EXPLOIT = cnp.getSuiviHn().getDatExpLrShn();
			Long DELAIS = cheque.getAnr() != null ? 2L : 1L;
			String REF_REG = null;

			jt.update(
					"Insert into DET_ARP (NUM_ARP,NATURE_REMET,CODE_BAN,CODE_AGE,DATE_OPE,NUM_LOT,CODE_DEV,MNT_CHQ,NUM_CHQ,RIB_TIR,CODE_AGE_DES,CODE_BAN_DES,RIB_BENEF,NUM_CNP,DATE_EMI_CHQ,LIEU_EMI_CHQ,DATE_CNP,DATE_PRES_CHQ,DATE_PREAVIS,MNT_PROVISION,DATE_DELIV_CHQ,NBR_ENR_COM,DATE_REGUL,COD_DEV_POS,MNT_REG,MNT_REG_INT,TYPE_COMPTE,NAT_COMPTE,DATE_CHARG,JOURNEE,SOURCE,DATE_DECL,DECLARATION,TYPE_PIECE_PM,NUM_PIECE_PM,TYPE_PIECE_PP1,NUM_PIECE_PP1,CODE_POUVOIR_PP1,TYPE_PIECE_PP2,NUM_PIECE_PP2,CODE_POUVOIR_PP2,TYPE_PIECE_PP3,NUM_PIECE_PP3,CODE_POUVOIR_PP3,MOTIF_REJET,NUM_MATR_USER,DATE_EXPLOIT,DELAIS,REF_REG) values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)",
					new Object[]{ NUM_ARP, NATURE_REMET, CODE_BAN, CODE_AGE, DATE_OPE, NUM_LOT, CODE_DEV, MNT_CHQ,
							NUM_CHQ, RIB_TIR, CODE_AGE_DES, CODE_BAN_DES, RIB_BENEF, NUM_CNP, DATE_EMI_CHQ,
							LIEU_EMI_CHQ, DATE_CNP, DATE_PRES_CHQ, DATE_PREAVIS, MNT_PROVISION, DATE_DELIV_CHQ,
							NBR_ENR_COM, DATE_REGUL, COD_DEV_POS, MNT_REG, MNT_REG_INT, TYPE_COMPTE, NAT_COMPTE,
							DATE_CHARG, JOURNEE, SOURCE, DATE_DECL, "Z", TYPE_PIECE_PM, NUM_PIECE_PM, TYPE_PIECE_PP1,
							NUM_PIECE_PP1, CODE_POUVOIR_PP1, TYPE_PIECE_PP2, NUM_PIECE_PP2, CODE_POUVOIR_PP2,
							TYPE_PIECE_PP3, NUM_PIECE_PP3, CODE_POUVOIR_PP3, MOTIF_REJET, NUM_MATR_USER, DATE_EXPLOIT,
							DELAIS, REF_REG });

		} catch (Exception ex) {
			ex.printStackTrace();
			throw new RuntimeException();
		}

	}

	public boolean verifPositionCheque(Date dateOp) {
		jt = new JdbcTemplate(dataSource);
		final SimpleDateFormat formatDate1 = new SimpleDateFormat("dd/MM/yyyy");

		Long nbr = (Long) jt.queryForObject(
				"select count(*) from batch_comp_chq where DATE_OPER_OPER='" + formatDate1.format(dateOp) + "' ",
				Long.class);

		Long nbr1 = (Long) jt.queryForObject("select count(*) from agence_pilote ", Long.class);
		if (nbr.longValue() == nbr1.longValue())
			return true;
		else
			return false;

	}

	public void appurement(String codStrc, String codVal) {
		jt = new JdbcTemplate(dataSource);
		if (codVal.equals("30")) {
			jt.update(
					"insert into cheque_30_removed select * from cheque_30 where cmp_auto is null or cmp_auto!='B' and cod_ug="
							+ codStrc);
			jt.update("delete from  cheque_30 where cmp_auto is null or cmp_auto!='B' and cod_ug=" + codStrc);

		}
		if (codVal.equals("33")) {
			jt.update(
					"insert into cheque_33_removed select * from cheque_33 where cmp_auto is null or cmp_auto!='B' and cod_ug="
							+ codStrc);
			jt.update("delete from  cheque_33 where cmp_auto is null or cmp_auto!='B' and cod_ug=" + codStrc);

		}

		System.out.println(
				"insert into cheque_30_removed select * from cheque_30 where cmp_auto is null or cmp_aut!='B' and cod_ug="
						+ codStrc);

	}

	// dashboard

	public void insertAnrAediter(Long codBctAge, Date dateComptable) throws Exception {
		jt = new JdbcTemplate(dataSource);
		jt.update("delete from   ALERT_JOUR_GIP where COD_AGE_CHQ=?" + " and TYP_ALRT_CHQ='ANRAEDITER'",
				new Object[]{ Long.valueOf(codBctAge) });
		jt.execute("commit");
		HibernateTemplate hibernateTemplate =
				(HibernateTemplate) ContextHandler.getContext().getBean("hibernateTemplate");
		Session sess = hibernateTemplate.getSessionFactory().getCurrentSession();

		List<Cheque> listeSuiviHnChq = sess.createCriteria(Cheque.class).createAlias("cnp", "c")
				.createAlias("c.suiviHn", "hn").add(Restrictions.isNotNull("hn.datPvShn"))
				.add(Restrictions.isNotNull("hn.datExpLrShn"))
				.add(Restrictions.eq("codAgdeChq", StrHandler.lpad("" + codBctAge, '0', 3)))
				.add(Restrictions.eq("codRejChq.codValVal", 82L)).add(Restrictions.eq("codEtatChq", "R")).list();
		List<Cheque> listeSuiviHn = new ArrayList<Cheque>();

		for (Cheque cheque : listeSuiviHnChq) {

			if (cheque.getAnr() == null
					&& CalanderHandler.GetNextWorkingDayAfterNdays(cheque.getCnp().getSuiviHn().getDatExpLrShn(), 4)
							.compareTo(dateComptable) <= 0) {

				insertAlertGip(cheque, "ANRAEDITER");
			}

		}

	}

	public void insertCnpAediter(Long codBctAge, Date dateComptable) throws Exception {
		jt = new JdbcTemplate(dataSource);
		jt.update("delete from   ALERT_JOUR_GIP where COD_AGE_CHQ=?" + " and TYP_ALRT_CHQ='CNPAEDITER'",
				new Object[]{ Long.valueOf(codBctAge) });
		jt.execute("commit");
		ISearchEngine searchEngine = (SearchEngine) ContextHandler.getContext().getBean("searchEngine");

		HibernateTemplate hibernateTemplate =
				(HibernateTemplate) ContextHandler.getContext().getBean("hibernateTemplate");
		Session sess = hibernateTemplate.getSessionFactory().getCurrentSession();

		Date nextDay = CalanderHandler.getDateOuvrableBeforeNDays(dateComptable, -3);

		/******** Cnp non editee *****/

		// System.out.println("Next day:" + nextDay);
		// System.out.println("Date CompT:" +
		// alertRejetVo.getDateComptable());

		List<Cheque> listeChequeCnp =

				sess.createCriteria(Cheque.class).createAlias("preavis", "pre")
						// .add(Restrictions.eq("pre.datPrePre", nextDay))
						.add(Restrictions.le("pre.datPrePre", nextDay)).add(Restrictions.eq("codEtatChq", "R"))
						.add(Restrictions.eq("codRejChq.codValVal", 81L))
						.add(Restrictions.eq("codAgdeChq", StrHandler.lpad("" + codBctAge, '0', 3))).list();

		for (int i = 0; i < listeChequeCnp.size(); i++) {
			insertAlertGip(listeChequeCnp.get(i), "CNPAEDITER");
		}

	}

	public void insertAlertGip(Cheque cheque, String typeAlert) {

		jt.update(
				"insert into ALERT_JOUR_GIP(COD_AGE_CHQ,NUM_CHQ_CHQ,RIB_BEN_CHQ,RIB_TIR_CHQ,TYP_ALRT_CHQ) "
						+ "values(?,?,?,?,?)",
				new Object[]{ Long.valueOf(cheque.getCodAgdeChq()), cheque.getChequeId().getNumChqChq(),
						cheque.getChequeId().getRibBenChq(), cheque.getChequeId().getRibTirChq(), typeAlert });

		jt.execute("commit");

	}

	public boolean isEffetEscInserted(String numEff, String datOpe) {
		jt = new JdbcTemplate(dataSource);

		Long count = (Long) jt.queryForLong("select count(*) from effet_recu_adt where num_eff_eff='" + numEff
				+ "' and dat_ope_eff='" + datOpe + "'");
		if (count > 0L)
			return true;
		else
			return false;
	}

	public boolean isEffetInserted(String numEff, String datOpe) {
		jt = new JdbcTemplate(dataSource);

		Long count = (Long) jt.queryForLong("select count(*) from effet_recu_tmp where num_eff_eff='" + numEff
				+ "' and dat_ope_eff='" + datOpe + "'");
		if (count > 0L)
			return true;
		else
			return false;
	}

	public List<DetailEffet> getEffetRemiseEsc(String dateComptable, Long strc) {
		jt = new JdbcTemplate(dataSource);

		List<DetailEffet> effets = new ArrayList<DetailEffet>();
		String req =
				"select  e.* ,d.num_ribtire_deteffesc,d.nom_tire_deteffesc from saebcg.credit_Esc c,saebcg.effet_esc e,saebcg.detail_effet_esc d "
						+ " where c.NUM_CRD_CRDESC=e.NUM_CRD_CRDESC" + " and c.DAT_DEBLCAGE_CRDESC=?"

						+ " and c.COD_ETATCRD_ETCRDESC='6'" + " and c.COD_STRC_STRC=?"

						+ " and e.COD_ETAT_ETEFFESC='1'" + " and e.num_effet_effesc =  d.num_deteffet_deteffesc"
						+ " and e.num_seq_effesc =   d.num_seq_deteffetesc"
						+ " and e.dat_oper_effesc =  d.dat_oper_deteffesc";

		SqlRowSet srs = jt.queryForRowSet(req, new Object[]{ dateComptable, strc });

		while (srs.next()) {
			DetailEffet effet = new DetailEffet();
			DetailEffetId effetId = new DetailEffetId();
			effetId.setNumEff(srs.getString("NUM_EFFET_EFFESC"));
			effetId.setDatOpe(DateHandler.strToDate(dateComptable));
			effet.setEffetId(effetId);

			effet.setRibTir(srs.getString("NUM_RIBTIRE_DETEFFESC"));
			effet.setRibBen(srs.getString("NUM_RIBTIREUR_EFFESC"));
			effet.setCodAgDes(effet.getRibTir().substring(2, 5));
			effet.setCodBqDes(effet.getRibTir().substring(0, 2)/*
															    * effet.getRibBen ( ).substring(0 , 2)
															    */);
			effet.setCodAgEm(effet.getRibBen().substring(2, 5));
			effet.setCodBqEm(effet.getRibBen().substring(0, 2)/*
															   * effet.getRibTir(). substring(0, 2)
															   */);
			effet.setMntEff(srs.getLong("MONT_EFFET_EFFESC"));
			effet.setDatEch(srs.getDate("DAT_ECHEANCE_EFFESC"));
			effet.setRefComTir("6");
			effet.setRefComBen("6");
			effet.setDatCre(srs.getDate("DAT_CRE_EFFESC"));
			// TAUXINT_EFFESC
			effet.setDescription(String.valueOf(srs.getBigDecimal("TAUXINT_EFFESC")));
			Long aval = srs.getLong("BOL_AVAL_EFFESC");
			// (22)code endossement et aval ??? sur 8 caracteres
			if (getEndosseurEsc(srs.getString("NUM_EFFET_EFFESC"), srs.getLong("NUM_SEQ_EFFESC"),
					srs.getDate("DAT_OPER_EFFESC")))
				effet.setCodSitBen("E");
			else
				effet.setCodSitBen("NE");

			effet.setEffAval(srs.getLong("BOL_AVAL_EFFESC") == 0 ? false : true);

			effet.setEffBap(srs.getLong("BOL_BAP_EFFESC") == 1 ? true : false);

			effet.setMntIntr(srs.getLong("MON_INTPROG_EFFESC"));
			effet.setCodVal(41L);
			effet.setCodEnrg(21L);

			effet.setNomTir(srs.getString("NOM_TIRE_DETEFFESC"));

			Devise devise = new Devise();
			devise.setCodDevDev(788L);
			effet.setDevise(devise);
			// (21)Code d'acceptation ???
			// code =0 si LC Accepte, =1 si non accepte,=2 si présenté
			effet.setEffAcpt(false);// code =0 si LC Accepte
			effet.setLieuCre("0");
			effets.add(effet);
		}
		return effets;
	}

	public boolean getEndosseurEsc(String num_effet, Long num_seq, Date datOp) {
		boolean endoss = false;
		String req = "select e.* from saebcg.endosseur e where NUM_DETEFFET_ESC=?"

				+ " and DAT_OPER_DETEFFESC = ?"

				+ "  and NUM_SEQ_DETEFFETESC =? ";
		SqlRowSet srs = jt.queryForRowSet(req, new Object[]{ num_effet, DateHandler.dateToStr(datOp), num_seq });

		int rsCount = 0;
		while (srs.next()) {
			rsCount = rsCount + 1;
		}
		if (rsCount != 0)
			endoss = true;
		return endoss;

	}

	public String getNumLotEffet(Long strc) {
		jt = new JdbcTemplate(dataSource);
		Long numLot = 1L;
		// SqlRowSet rs =
		// jt.queryForRowSet("select NUM_VAL_SEQA from SEQ_AGENCE where COD_STRC_STRC= "
		// + strc
		// + " and LIB_SEQ_SEQA='NUM_LOT_EFF'");
		// if (rs.next()) {
		// numLot = rs.getLong("NUM_VAL_SEQA");
		//
		// executerTransaction("update SEQ_AGENCE set NUM_VAL_SEQA=" + (numLot +
		// 1) + " where COD_STRC_STRC= " + strc
		// + " and LIB_SEQ_SEQA='NUM_LOT_EFF'");
		// return String.format("%04d", numLot);
		//
		// } else {
		//
		// executerTransaction("insert into SEQ_AGENCE(COD_STRC_STRC,LIB_SEQ_SEQA,NUM_VAL_SEQA,NBR_FREQ_SEQA) values("
		// + strc + ",'NUM_LOT_EFF'," + (numLot + 1) + ",'A')");
		// }

		return "0001";
	}

	public List<ReservationChqVo> getListReservationChq(String ribTirChq, String numChqChq) {

		jt = new JdbcTemplate(dataSource);
		List<ReservationChqVo> liste = new ArrayList<ReservationChqVo>();

		String requete =
				"select COD_ETAT_RESV, COD_STRC_STRC, COD_PRD_PRD, NUM_CCPT_CCPT, NUM_CHQ_CHQ, MNT_RESV_RESV, RIB_TIR_CHQ, NUM_OPER_OMP, DAT_CONSULT_RESV, "
						+ "	DAT_RESV_RESV, DAT_FIN_RESV, DAT_EXPIR_RESV "
						+ " from Reservation_Cheque where COD_ETAT_RESV='R' and RIB_TIR_CHQ = " + ribTirChq
						+ " and NUM_CHQ_CHQ= " + numChqChq;

		liste = jt.query(requete, new RowMapper() {

			public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
				ReservationChqVo reservationChqVo = new ReservationChqVo();

				reservationChqVo.setCodEtatRsv(rs.getString("COD_ETAT_RESV"));
				reservationChqVo.setCodStrcStrc(rs.getLong("COD_STRC_STRC"));
				reservationChqVo.setCodPrdPrd(rs.getLong("COD_PRD_PRD"));
				reservationChqVo.setNumCcptCcpt(rs.getLong("NUM_CCPT_CCPT"));
				reservationChqVo.setNumChqChq(rs.getLong("NUM_CHQ_CHQ"));
				reservationChqVo.setMontantRsv(rs.getLong("MNT_RESV_RESV"));

				reservationChqVo.setRibTirChq(rs.getString("RIB_TIR_CHQ"));
				reservationChqVo.setNumOperOmp(rs.getString("NUM_OPER_OMP"));
				reservationChqVo.setDateConsultRsv(rs.getDate("DAT_CONSULT_RESV"));
				reservationChqVo.setDateRsvRsv(rs.getDate("DAT_RESV_RESV"));
				reservationChqVo.setDateFinRsv(rs.getDate("DAT_FIN_RESV"));
				reservationChqVo.setDateExpirRsv(rs.getDate("DAT_EXPIR_RESV"));

				return reservationChqVo;

			}

		});

		logger.info("la requete Reservation Cheque a ramené " + String.valueOf(liste.size()));

		return liste;

	}

	public List<Chequier> getInfoChequierByCriteres(ContratCpt compte, Long numChqChq) {

		jt = new JdbcTemplate(dataSource);
		List<Chequier> liste = new ArrayList<Chequier>();

		String requete =
				"   select NUM_DEM_DCHQ,COD_STRC_STRC, COD_PRD_PRD, NUM_CCPT_CCPT, NUM_CHQI_CHQI, COD_ETAT_DCHQ as COD_ETAT_CHQI,"
						+ "  to_number(MNT_MAX) as PLAFOND, trunc(DAT_FIN_VAL) as DAT_EXP_CHQI , CLE_SEC as CLE_SEC_PECC_CHQI,NBR_CHQ_CHQI,NUM_DEB_CHQI,(NUM_DEB_CHQI+NBR_CHQ_CHQI-1) as NUM_FIN "
						+ " from Demande_Chq_Chequier " + "where COD_ETAT_DCHQ=1  and  Cod_Strc_Strc="
						+ compte.getContratCptId().getCodStrcStrc() + " and Cod_Prd_Prd="
						+ compte.getContratCptId().getCodPrdPrd() + " and Num_Ccpt_Ccpt="
						+ compte.getContratCptId().getNumCcptCcpt() + " and " + numChqChq
						+ "  between NUM_DEB_CHQI and (NUM_DEB_CHQI+NBR_CHQ_CHQI-1) " + " UNION ALL "
						+ " select NUM_DEM_DCHQ,COD_STRC_STRC, COD_PRD_PRD, NUM_CCPT_CCPT, NUM_CHQI_CHQI, Cod_Etat_Chqi,		 (MNT_PLAF_CHQI*1000) as PLAFOND,"
						+ " DAT_EXP_CHQI, CLE_SEC_PECC_CHQI,NBR_CHQ_CHQI,NUM_DEB_CHQI,(NUM_DEB_CHQI+NBR_CHQ_CHQI-1) as NUM_FIN "
						+ " from Chequier chq where COD_CONF_CONF='L'  and chq.Cle_Sec_Pecc_Chqi is not null and Chq.Dat_Exp_Chqi is not null and  Chq.Mnt_Plaf_Chqi is not null "
						+ "  and  Cod_Strc_Strc= " + compte.getContratCptId().getCodStrcStrc() + " and Cod_Prd_Prd= "
						+ compte.getContratCptId().getCodPrdPrd() + " and Num_Ccpt_Ccpt="
						+ compte.getContratCptId().getNumCcptCcpt() + " and " + numChqChq + " "
						+ " between NUM_DEB_CHQI and (NUM_DEB_CHQI+NBR_CHQ_CHQI-1) "
						+ " order by NUM_DEM_DCHQ,NUM_CHQI_CHQI ";

		System.out.println(" requete getInfoChequierByCriteres : " + requete);

		liste = jt.query(requete, new RowMapper() {

			public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
				Chequier chequier = new Chequier();
				ChequierId chequierId = new ChequierId();
				chequierId.setNumDemDchq(rs.getString("NUM_DEM_DCHQ"));
				chequierId.setNumChqiChqi(rs.getLong("NUM_CHQI_CHQI"));
				chequier.setChequierId(chequierId);
				ContratCpt contratCpt = new ContratCpt();
				ContratCptId cptId = new ContratCptId();
				cptId.setCodStrcStrc(rs.getLong("COD_STRC_STRC"));
				cptId.setCodPrdPrd(rs.getLong("COD_PRD_PRD"));
				cptId.setNumCcptCcpt(rs.getLong("NUM_CCPT_CCPT"));
				contratCpt.setContratCptId(cptId);
				chequier.setContratCpt(contratCpt);
				chequier.setCleSecPeccChqi(rs.getString("CLE_SEC_PECC_CHQI"));
				chequier.setMntPlafChqi(rs.getLong("PLAFOND"));
				chequier.setDatExpChqi(rs.getDate("DAT_EXP_CHQI"));
				chequier.setCodEtatChqi(rs.getLong("COD_ETAT_CHQI"));

				return chequier;

			}

		});

		logger.info("la requete chequier  a ramené " + String.valueOf(liste.size()));

		return liste;

	}

	public List<CertificationCheques> listeChequeCertifiesAgence(Long codeAgence, Long numChqChq, Long montant) {
		jt = new JdbcTemplate(dataSource);
		List<CertificationCheques> liste = new ArrayList<CertificationCheques>();

		String requete =
				" select * from Certification_Cheques  where COD_PAY_CCHQ=0 and COD_ETAT_CCHQ=1 and COD_STRC_CPTI="
						+ codeAgence + " and  Mont_Cert_Cchq=" + montant + " and Num_Chq_Cchq =" + numChqChq + "";

		System.out.println(" requete listeChequeCertifies : " + requete);

		liste = jt.query(requete, new RowMapper() {

			public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
				CertificationCheques certificationCheques = new CertificationCheques();

				certificationCheques.setNumCertCchq(rs.getString("NUM_CERT_CCHQ"));
				certificationCheques.setDatOperCchq(rs.getDate("DAT_OPER_CCHQ"));
				certificationCheques.setDatCertCchq(rs.getDate("DAT_CERT_CCHQ"));
				certificationCheques.setCodEtatCchq(rs.getLong("COD_ETAT_CCHQ"));
				certificationCheques.setCodDemCchq(rs.getString("COD_DEM_CCHQ"));
				certificationCheques.setNumChqCchq(rs.getLong("NUM_CHQ_CCHQ"));
				certificationCheques.setCodTpceTpce(rs.getLong("COD_TPCE_TPCE"));
				certificationCheques.setNumPceCchq(rs.getString("NUM_PCE_CCHQ"));
				certificationCheques.setMontCertCchq(rs.getLong("MONT_CERT_CCHQ"));
				certificationCheques.setCodLieuCchq(rs.getString("COD_LIEU_CCHQ"));
				certificationCheques.setCodTypcCchq(rs.getString("COD_TYPC_CCHQ"));
				certificationCheques.setNomBenfCchq(rs.getString("NOM_BENF_CCHQ"));
				ContratCpt contratCpt = new ContratCpt();
				ContratCptId cptId = new ContratCptId();
				cptId.setCodStrcStrc(rs.getLong("COD_STRC_STRC"));
				cptId.setCodPrdPrd(rs.getLong("COD_PRD_PRD"));
				cptId.setNumCcptCcpt(rs.getLong("NUM_CCPT_CCPT"));
				contratCpt.setContratCptId(cptId);
				certificationCheques.setContratCpt(contratCpt);
				Tache tache = new Tache();
				TacheId id = new TacheId();
				id.setCodOperOper(rs.getLong("COD_OPER_OPER"));
				id.setCodTachTach(rs.getLong("COD_TACH_TACH"));
				tache.setTacheId(id);
				certificationCheques.setTache(tache);
				Personnel personnel = new Personnel();
				personnel.setNumMatrUser(rs.getString("NUM_MATR_USER"));
				certificationCheques.setPersonnel(personnel);
				CompteInterne compteInterne = new CompteInterne();
				CompteInterneId compteInterneId = new CompteInterneId();
				compteInterneId.setCodStrcStrc(rs.getLong("COD_STRC_CPTI"));
				compteInterneId.setCodPrdPrd(rs.getLong("COD_PRD_CPTI"));
				compteInterneId.setNumCptiCpti(rs.getLong("NUM_CCPT_CPTI"));
				compteInterne.setCompteInterneId(compteInterneId);
				certificationCheques.setCompteInterne(compteInterne);
				certificationCheques.setCodPayCchq(rs.getLong("COD_PAY_CCHQ"));

				return certificationCheques;

			}

		});

		logger.info("la requete certificationCheques  a ramené " + String.valueOf(liste.size()));

		return liste;

	}

	public List<CertificationCheques> listeChequeCertifies(Long numChqChq, Long montant, ContratCpt contratCpt) {
		jt = new JdbcTemplate(dataSource);
		List<CertificationCheques> liste = new ArrayList<CertificationCheques>();

		String requete =
				" select * from Certification_Cheques  where COD_PAY_CCHQ=0 and COD_ETAT_CCHQ=1 and  Mont_Cert_Cchq="
						+ montant + " and Num_Chq_Cchq =" + numChqChq + "";

		if (contratCpt != null && contratCpt.getContratCptId() != null) {

			requete += " AND COD_STRC_STRC=" + contratCpt.getContratCptId().getCodStrcStrc() + " AND COD_PRD_PRD="
					+ contratCpt.getContratCptId().getCodPrdPrd() + " AND NUM_CCPT_CCPT="
					+ contratCpt.getContratCptId().getNumCcptCcpt() + " ";
		}

		System.out.println(" requete listeChequeCertifies : " + requete);

		liste = jt.query(requete, new RowMapper() {

			public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
				CertificationCheques certificationCheques = new CertificationCheques();

				certificationCheques.setNumCertCchq(rs.getString("NUM_CERT_CCHQ"));
				certificationCheques.setDatOperCchq(rs.getDate("DAT_OPER_CCHQ"));
				certificationCheques.setDatCertCchq(rs.getDate("DAT_CERT_CCHQ"));
				certificationCheques.setCodEtatCchq(rs.getLong("COD_ETAT_CCHQ"));
				certificationCheques.setCodDemCchq(rs.getString("COD_DEM_CCHQ"));
				certificationCheques.setNumChqCchq(rs.getLong("NUM_CHQ_CCHQ"));
				certificationCheques.setCodTpceTpce(rs.getLong("COD_TPCE_TPCE"));
				certificationCheques.setNumPceCchq(rs.getString("NUM_PCE_CCHQ"));
				certificationCheques.setMontCertCchq(rs.getLong("MONT_CERT_CCHQ"));
				certificationCheques.setCodLieuCchq(rs.getString("COD_LIEU_CCHQ"));
				certificationCheques.setCodTypcCchq(rs.getString("COD_TYPC_CCHQ"));
				certificationCheques.setNomBenfCchq(rs.getString("NOM_BENF_CCHQ"));
				ContratCpt contratCpt = new ContratCpt();
				ContratCptId cptId = new ContratCptId();
				cptId.setCodStrcStrc(rs.getLong("COD_STRC_STRC"));
				cptId.setCodPrdPrd(rs.getLong("COD_PRD_PRD"));
				cptId.setNumCcptCcpt(rs.getLong("NUM_CCPT_CCPT"));
				contratCpt.setContratCptId(cptId);
				certificationCheques.setContratCpt(contratCpt);
				Tache tache = new Tache();
				TacheId id = new TacheId();
				id.setCodOperOper(rs.getLong("COD_OPER_OPER"));
				id.setCodTachTach(rs.getLong("COD_TACH_TACH"));
				tache.setTacheId(id);
				certificationCheques.setTache(tache);
				Personnel personnel = new Personnel();
				personnel.setNumMatrUser(rs.getString("NUM_MATR_USER"));
				certificationCheques.setPersonnel(personnel);
				CompteInterne compteInterne = new CompteInterne();
				CompteInterneId compteInterneId = new CompteInterneId();
				compteInterneId.setCodStrcStrc(rs.getLong("COD_STRC_CPTI"));
				compteInterneId.setCodPrdPrd(rs.getLong("COD_PRD_CPTI"));
				compteInterneId.setNumCptiCpti(rs.getLong("NUM_CCPT_CPTI"));
				compteInterne.setCompteInterneId(compteInterneId);
				certificationCheques.setCompteInterne(compteInterne);
				certificationCheques.setCodPayCchq(rs.getLong("COD_PAY_CCHQ"));

				return certificationCheques;

			}

		});

		logger.info("la requete certificationCheques  a ramené " + String.valueOf(liste.size()));

		return liste;

	}

	public List<OppositionMoyenPaiement> listeOppositionMoyenPaiement(String numMoyenPaiement, Long typeMoyenPaiement,
			ContratCptId contratCptId) {
		jt = new JdbcTemplate(dataSource);
		List<OppositionMoyenPaiement> liste = new ArrayList<OppositionMoyenPaiement>();

		String requete = " 	select  * from (select  op1.*, (select count(*)  from  Opposition_Moyen_Paiement op2 "
				+ " where Op1.Cod_Strc_Strc=Op2.Cod_Strc_Strc" + "	and Op1.Cod_Prd_Prd=Op2.Cod_Prd_Prd"
				+ "	and Op1.Num_Ccpt_Ccpt=Op2.Num_Ccpt_Ccpt"
				+ "	and  op2.COD_ETAT_OPMP='L' and op1.COD_MOYP_TMOY=op2.COD_MOYP_TMOY"
				+ " and op1.NUM_MOYP_OPMP=op2.NUM_MOYP_OPMP) as NBRE_LEVEE " + "	from Opposition_Moyen_Paiement op1 "
				+ "	where Op1.Cod_Strc_Strc=" + contratCptId.getCodStrcStrc() + "  and Op1.Cod_Prd_Prd="
				+ contratCptId.getCodPrdPrd() + " and Op1.Num_Ccpt_Ccpt=" + contratCptId.getNumCcptCcpt()
				+ " and op1.COD_ETAT_OPMP='O' and op1.COD_MOYP_TMOY=" + typeMoyenPaiement + "	and op1.NUM_MOYP_OPMP='"
				+ numMoyenPaiement + "' 	) where NBRE_LEVEE=0 ";

		System.out.println(" requete listeOppositionMoyenPaiement : " + requete);

		liste = jt.query(requete, new RowMapper() {

			public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
				OppositionMoyenPaiement oppositionMoyenPaiement = new OppositionMoyenPaiement();
				OppositionMoyenPaiementId oppositionMoyenPaiementId = new OppositionMoyenPaiementId();
				oppositionMoyenPaiementId.setCodMoypTmoy(rs.getLong("COD_MOYP_TMOY"));
				oppositionMoyenPaiementId.setNumMoypOpmp(rs.getString("NUM_MOYP_OPMP"));
				oppositionMoyenPaiementId.setDatOperOpmp(rs.getDate("DAT_OPER_OPMP"));
				oppositionMoyenPaiement.setOppositionMoyenPaiementId(oppositionMoyenPaiementId);
				ContratCpt contratCpt = new ContratCpt();
				ContratCptId cptId = new ContratCptId();
				cptId.setCodStrcStrc(rs.getLong("COD_STRC_STRC"));
				cptId.setCodPrdPrd(rs.getLong("COD_PRD_PRD"));
				cptId.setNumCcptCcpt(rs.getLong("NUM_CCPT_CCPT"));
				contratCpt.setContratCptId(cptId);
				oppositionMoyenPaiement.setContratCpt(contratCpt);
				oppositionMoyenPaiement.setCodEtatOpmp(rs.getString("COD_ETAT_OPMP"));
				oppositionMoyenPaiement.setCodActrOpmp(rs.getString("COD_ACTR_OPMP"));
				Tache tache = new Tache();
				TacheId id = new TacheId();
				id.setCodOperOper(rs.getLong("COD_OPER_OPER"));
				id.setCodTachTach(rs.getLong("COD_TACH_TACH"));
				tache.setTacheId(id);
				oppositionMoyenPaiement.setTache(tache);
				Personnel personnel = new Personnel();
				personnel.setNumMatrUser(rs.getString("NUM_MATR_USER"));
				oppositionMoyenPaiement.setPersonnel(personnel);
				oppositionMoyenPaiement.setCodMotfOpmp(rs.getString("COD_MOTF_OPMP"));
				return oppositionMoyenPaiement;

			}

		});

		logger.info("la requete oppositionMoyenPaiement  a ramené " + String.valueOf(liste.size()));

		return liste;

	}
	
	public List<ChequeACHVo> getListCheques30ACHAgence(Date dateComptable, String codBct, String codValChq) {

		jt = new JdbcTemplate(dataSource);

		List<ChequeACHVo> listeCheque = new ArrayList<ChequeACHVo>();

		String requete = "select c.MNT_TOT, c.NBR_TOT, c.COD_SEN, c.COD_AGE, c.COD_BAN, c.COD_VAL, c.COD_NAT_ETA, c.COD_DEV, c.COD_ENR, "
				+ " c.REF_FIC, c.MNT_REJ_ADT, c.REF_OPER, c.COD_STA, c.DAT_ENV_PA, c.REF_LOT, ARC_FLG, c.ENV_AGE, dc.NUM_CHQ, "
				+ " dc.RIB_TIR, dc.NUM_LOT, dc.DAT_OPE, dc.MNT_CHQ, dc.RIB_BEN, dc.NOM_PRN, dc.DAT_EMI, dc.COD_SEN, dc.COD_NAT_ETA, "
				+ "  dc.COD_ENR, dc.COD_AGE, dc.COD_BAN, dc.COD_AGE_DES, dc.COD_BAN_DES, dc.COD_LIE_EMI_CHQ, dc.COD_SIT, dc.COD_NAT_CPT, "
				+ "  dc.COD_DEV, dc.COD_VAL, dc.REF_FIC, dc.COD_MOT_REJ, dc.RIB_TIR_REC, dc.NUM_EVT_ENV, dc.NUM_EVT_RCP,  dc.RJT_REG "
				+ "  from IBANK.AD_CHEQUE_30@T24.BNA.TN c, IBANK.AD_DETAIL_CHEQUE_30@T24.BNA.TN dc  "
				+ "  where c.NUM_LOT=dc.NUM_LOT " + "  and c.DAT_OPE=dc.DAT_OPE " + "  and c.COD_AGE =dc.COD_AGE_DES"
				+ "  and trunc(c.DAT_OPE)='" + formaterDate.format(dateComptable) + "'" + "  and c.COD_STA='7'"
				+ "  and dc.COD_SEN=2 " + "  and dc.COD_ENR=21" + "  and dc.COD_AGE_DES = " + codBct
				+ "  and dc.COD_BAN_DES='03'" + "  and c.COD_VAL= " + codValChq + " ";

		System.out.println(" requete listeChequeACH : " + requete);

		listeCheque = jt.query(requete, new RowMapper() {

			public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
				ChequeACHVo chequeACHVo = new ChequeACHVo();

				chequeACHVo.setMntTot(rs.getLong("MNT_TOT"));
				chequeACHVo.setNbrTot(rs.getLong("NBR_TOT"));
				chequeACHVo.setMntRejAdt(rs.getLong("MNT_REJ_ADT"));
				chequeACHVo.setRefOper(rs.getString("REF_OPER"));
				chequeACHVo.setCodSta(rs.getString("COD_STA"));
				chequeACHVo.setDatEnvPa(rs.getDate("DAT_ENV_PA"));
				chequeACHVo.setRefLot(rs.getString("REF_LOT"));
				chequeACHVo.setArcFlg(rs.getLong("ARC_FLG"));
				chequeACHVo.setEnvAge(rs.getString("ENV_AGE"));

				chequeACHVo.setNumChq(rs.getLong("NUM_CHQ"));
				chequeACHVo.setRibTir(rs.getLong("RIB_TIR"));
				chequeACHVo.setNumLot(rs.getLong("NUM_LOT"));
				chequeACHVo.setDatOpe(rs.getDate("DAT_OPE"));
				chequeACHVo.setMntChq(rs.getLong("MNT_CHQ"));
				chequeACHVo.setRibBen(rs.getLong("RIB_BEN"));
				chequeACHVo.setNomPrn(rs.getString("NOM_PRN"));
				chequeACHVo.setDatEmi(rs.getDate("DAT_EMI"));
				chequeACHVo.setCodSen(rs.getLong("COD_SEN"));
				chequeACHVo.setCodNatEta(rs.getLong("COD_NAT_ETA"));
				chequeACHVo.setCodEnr(rs.getLong("COD_ENR"));
				chequeACHVo.setCodAge(rs.getString("COD_AGE"));
				chequeACHVo.setCodBan(rs.getString("COD_BAN"));
				chequeACHVo.setCodAgeDes(rs.getString("COD_AGE_DES"));
				chequeACHVo.setCodBanDes(rs.getString("COD_BAN_DES"));
				chequeACHVo.setCodLieEmiChq(rs.getString("COD_LIE_EMI_CHQ"));
				chequeACHVo.setCodSit(rs.getLong("COD_SIT"));
				chequeACHVo.setCodNatCpt(rs.getLong("COD_NAT_CPT"));
				chequeACHVo.setCodDev(rs.getString("COD_DEV"));
				chequeACHVo.setCodVal(rs.getLong("COD_VAL"));
				chequeACHVo.setRefFic(rs.getString("REF_FIC"));
				chequeACHVo.setCodMotRej(rs.getString("COD_MOT_REJ"));
				chequeACHVo.setRibTirRec(rs.getString("RIB_TIR_REC"));
				chequeACHVo.setNumEvtEnv(rs.getLong("NUM_EVT_ENV"));
				chequeACHVo.setNumEvtRcp(rs.getLong("NUM_EVT_RCP"));
				chequeACHVo.setRjtReg(rs.getString("RJT_REG"));

				// ChequeACHVo.setImgR(rs.getBytes("IMG_R"));
				// ChequeACHVo.setImgV(rs.getBytes("IMG_V"));

				return chequeACHVo;
			}
		});

		logger.info("la requete cheque 30 ACH  a ramené " + String.valueOf(listeCheque.size()));

		return listeCheque;
	}

	public List<ChequeACHVo> getListCheques31ACHAgence(Date dateComptable, String codBct, String codValChq) {

		jt = new JdbcTemplate(dataSource);

		List<ChequeACHVo> listeCheque = new ArrayList<ChequeACHVo>();

		String requete = " c.MNT_TOT, c.NBR_TOT, c.COD_SEN, c.COD_VAL, c.COD_NAT_ETA, c.COD_DEV, c.COD_ENR, c.COD_AGE, c.COD_BAN, c.REF_FIC, "
				+ " c.MNT_REJ_ADT, c.COD_STA, c.DAT_ENV_PA, c.REF_LOT, c.ARC_FLG, c.ENV_AGE, dc.NUM_CHQ, dc.RIB_TIR, "
				+ " dc.NUM_LOT, dc.DAT_OPE, dc.MNT_CHQ, dc.RIB_BEN, dc.DAT_EMI, dc.DAT_CNP, dc.NUM_CNP, dc.MNT_REC, dc.COD_ENR, dc.COD_SEN, "
				+ " dc.COD_NAT_ETA, dc.COD_AGE, dc.COD_BAN, dc.COD_DEV, dc.COD_AGE_DES, dc.COD_BAN_DES, dc.COD_DEV_POS, dc.COD_VAL, "
				+ " dc.COD_LIE_EMI_CHQ, dc.REF_FIC, dc.COD_MOT_REJ, dc.RIB_TIR_REC, dc.NUM_EVT_ENV, dc.NUM_EVT_RCP, dc.RJT_REG "
				+ " from IBANK.AD_CHEQUE_31@T24.BNA.TN c, IBANK.AD_DETAIL_CHEQUE_31@T24.BNA.TN dc where  c.NUM_LOT=dc.NUM_LOT "
				+ "  and c.DAT_OPE=dc.DAT_OPE and c.COD_AGE =dc.COD_AGE_DES "
				+ " and c.COD_STA=7 and c.COD_SEN=2 and dc.COD_ENR=21 and dc.COD_AGE_DES = " + codBct
				+ " and c.COD_VAL= " + codValChq + " and trunc(c.DAT_OPE)='" + formaterDate.format(dateComptable)
				+ "'and dc.COD_BAN_DES='03'";

		System.out.println(" requete listeChequeACH : " + requete);

		listeCheque = jt.query(requete, new RowMapper() {

			public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
				ChequeACHVo chequeACHVo = new ChequeACHVo();

				chequeACHVo.setMntTot(rs.getLong("MNT_TOT"));
				chequeACHVo.setNbrTot(rs.getLong("NBR_TOT"));
				chequeACHVo.setMntRejAdt(rs.getLong("MNT_REJ_ADT"));
				chequeACHVo.setRefOper(rs.getString("REF_OPER"));
				chequeACHVo.setCodSta(rs.getString("COD_STA"));
				chequeACHVo.setDatEnvPa(rs.getDate("DAT_ENV_PA"));
				chequeACHVo.setRefLot(rs.getString("REF_LOT"));
				chequeACHVo.setArcFlg(rs.getLong("ARC_FLG"));
				chequeACHVo.setEnvAge(rs.getString("ENV_AGE"));

				chequeACHVo.setNumChq(rs.getLong("NUM_CHQ"));
				chequeACHVo.setNumCnp(rs.getLong("NUM_CNP"));
				chequeACHVo.setRibTir(rs.getLong("RIB_TIR"));
				chequeACHVo.setNumLot(rs.getLong("NUM_LOT"));
				chequeACHVo.setDatOpe(rs.getDate("DAT_OPE"));
				chequeACHVo.setMntChq(rs.getLong("MNT_CHQ"));
				chequeACHVo.setMntRec(rs.getLong("MNT_REC"));
				chequeACHVo.setRibBen(rs.getLong("RIB_BEN"));
				chequeACHVo.setNomPrn(rs.getString("NOM_PRN"));
				chequeACHVo.setDatEmi(rs.getDate("DAT_EMI"));
				chequeACHVo.setDatCnp(rs.getDate("DAT_CNP"));
				chequeACHVo.setCodSen(rs.getLong("COD_SEN"));
				chequeACHVo.setCodNatEta(rs.getLong("COD_NAT_ETA"));
				chequeACHVo.setCodEnr(rs.getLong("COD_ENR"));
				chequeACHVo.setCodAge(rs.getString("COD_AGE"));
				chequeACHVo.setCodBan(rs.getString("COD_BAN"));
				chequeACHVo.setCodAgeDes(rs.getString("COD_AGE_DES"));
				chequeACHVo.setCodBanDes(rs.getString("COD_BAN_DES"));
				chequeACHVo.setCodLieEmiChq(rs.getString("COD_LIE_EMI_CHQ"));
				chequeACHVo.setCodSit(rs.getLong("COD_SIT"));
				chequeACHVo.setCodNatCpt(rs.getLong("COD_NAT_CPT"));
				chequeACHVo.setCodDev(rs.getString("COD_DEV"));
				chequeACHVo.setCodDevPos(rs.getString("COD_DEV_POS"));
				chequeACHVo.setCodVal(rs.getLong("COD_VAL"));
				chequeACHVo.setRefFic(rs.getString("REF_FIC"));
				chequeACHVo.setCodMotRej(rs.getString("COD_MOT_REJ"));
				chequeACHVo.setRibTirRec(rs.getString("RIB_TIR_REC"));
				chequeACHVo.setNumEvtEnv(rs.getLong("NUM_EVT_ENV"));
				chequeACHVo.setNumEvtRcp(rs.getLong("NUM_EVT_RCP"));
				chequeACHVo.setRjtReg(rs.getString("RJT_REG"));

				// ChequeACHVo.setImgR(rs.getBytes("IMG_R"));
				// ChequeACHVo.setImgV(rs.getBytes("IMG_V"));

				return chequeACHVo;
			}
		});

		logger.info("la requete cheque ACH 31 a ramené " + String.valueOf(listeCheque.size()));

		return listeCheque;
	}

	public List<ChequeACHVo> getListCheques32ACHAgence(Date dateComptable, String codBct, String codValChq) {

		jt = new JdbcTemplate(dataSource);

		List<ChequeACHVo> listeCheque = new ArrayList<ChequeACHVo>();

		String requete = "select c.MNT_TOT, c.NBR_TOT, c.COD_SEN, c.COD_VAL, c.COD_NAT_ETA, c.COD_DEV, c.COD_ENR, c.COD_AGE, c.COD_BAN, c.REF_FIC, "
				+ "c.MNT_REJ_ADT, c.COD_STA, c.DAT_ENV_PA, c.REF_LOT, c.ARC_FLG, c.ENV_AGE, dc.NUM_CHQ, dc.RIB_TIR, "
				+ "dc.NUM_LOT, dc.DAT_OPE, dc.MNT_CHQ, dc.RIB_BEN, dc.DAT_EMI, dc.DAT_CNP, dc.NUM_CNP, dc.MNT_REG, dc.MNT_REG_INT, dc.COD_ENR, dc.COD_SEN, "
				+ "dc.COD_NAT_ETA, dc.COD_AGE, dc.COD_BAN, dc.COD_DEV, dc.COD_AGE_DES, dc.COD_BAN_DES, dc.COD_DEV_POS, dc.COD_VAL, "
				+ "dc.COD_LIE_EMI_CHQ, dc.REF_FIC, dc.COD_MOT_REJ, dc.RIB_TIR_REC, dc.NUM_EVT_ENV, dc.NUM_EVT_RCP, dc.RJT_REG  "
				+ " from IBANK.AD_CHEQUE_32@T24.BNA.TN c, IBANK.AD_DETAIL_CHEQUE_32@T24.BNA.TN dc where c.NUM_LOT=dc.NUM_LOT "
				+ "  and c.DAT_OPE=dc.DAT_OPE and c.COD_AGE =dc.COD_AGE_DES "
				+ " and c.COD_STA=7 and c.COD_SEN=2 and dc.COD_ENR=21 and dc.COD_AGE_DES = " + codBct
				+ " and c.COD_VAL= " + codValChq + " and c.DAT_OPE=to_date('" + formaterDate.format(dateComptable)
				+ "','DD/MM/YYYY') and dc.COD_BAN_DES='03'";

		System.out.println(" requete listeChequeACH : " + requete);

		listeCheque = jt.query(requete, new RowMapper() {

			public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
				ChequeACHVo chequeACHVo = new ChequeACHVo();

				chequeACHVo.setMntTot(rs.getLong("MNT_TOT"));
				chequeACHVo.setNbrTot(rs.getLong("NBR_TOT"));
				chequeACHVo.setMntRejAdt(rs.getLong("MNT_REJ_ADT"));
				chequeACHVo.setRefOper(rs.getString("REF_OPER"));
				chequeACHVo.setCodSta(rs.getString("COD_STA"));
				chequeACHVo.setDatEnvPa(rs.getDate("DAT_ENV_PA"));
				chequeACHVo.setRefLot(rs.getString("REF_LOT"));
				chequeACHVo.setArcFlg(rs.getLong("ARC_FLG"));
				chequeACHVo.setEnvAge(rs.getString("ENV_AGE"));

				chequeACHVo.setNumChq(rs.getLong("NUM_CHQ"));
				chequeACHVo.setRibTir(rs.getLong("RIB_TIR"));
				chequeACHVo.setNumLot(rs.getLong("NUM_LOT"));
				chequeACHVo.setDatOpe(rs.getDate("DAT_OPE"));
				chequeACHVo.setMntChq(rs.getLong("MNT_CHQ"));
				chequeACHVo.setMntReg(rs.getLong("MNT_REG"));
				chequeACHVo.setMntRegInt(rs.getLong("MNT_REG_INT"));
				chequeACHVo.setRibBen(rs.getLong("RIB_BEN"));
				chequeACHVo.setNomPrn(rs.getString("NOM_PRN"));
				chequeACHVo.setDatEmi(rs.getDate("DAT_EMI"));
				chequeACHVo.setCodSen(rs.getLong("COD_SEN"));
				chequeACHVo.setCodNatEta(rs.getLong("COD_NAT_ETA"));
				chequeACHVo.setCodEnr(rs.getLong("COD_ENR"));
				chequeACHVo.setCodAge(rs.getString("COD_AGE"));
				chequeACHVo.setCodBan(rs.getString("COD_BAN"));
				chequeACHVo.setCodAgeDes(rs.getString("COD_AGE_DES"));
				chequeACHVo.setCodBanDes(rs.getString("COD_BAN_DES"));
				chequeACHVo.setCodLieEmiChq(rs.getString("COD_LIE_EMI_CHQ"));
				chequeACHVo.setCodSit(rs.getLong("COD_SIT"));
				chequeACHVo.setCodNatCpt(rs.getLong("COD_NAT_CPT"));
				chequeACHVo.setCodDev(rs.getString("COD_DEV"));
				chequeACHVo.setCodVal(rs.getLong("COD_VAL"));
				chequeACHVo.setRefFic(rs.getString("REF_FIC"));
				chequeACHVo.setCodMotRej(rs.getString("COD_MOT_REJ"));
				chequeACHVo.setRibTirRec(rs.getString("RIB_TIR_REC"));
				chequeACHVo.setNumEvtEnv(rs.getLong("NUM_EVT_ENV"));
				chequeACHVo.setNumEvtRcp(rs.getLong("NUM_EVT_RCP"));
				chequeACHVo.setRjtReg(rs.getString("RJT_REG"));

				// ChequeACHVo.setImgR(rs.getBytes("IMG_R"));
				// ChequeACHVo.setImgV(rs.getBytes("IMG_V"));

				return chequeACHVo;
			}
		});

		logger.info("la requete cheque 32 ACH  a ramené " + String.valueOf(listeCheque.size()));

		return listeCheque;
	}

	public List<ChequeACHVo> getListCheques33ACHAgence(Date dateComptable, String codBct, String codValChq) {

		jt = new JdbcTemplate(dataSource);

		List<ChequeACHVo> listeCheque = new ArrayList<ChequeACHVo>();

		String requete = "select c.MNT_TOT, c.NBR_TOT, c.COD_SEN, c.COD_AGE, c.COD_BAN, c.COD_VAL, c.COD_NAT_ETA, c.COD_DEV, c.COD_ENR, "
				+ " c.REF_FIC, c.MNT_REJ_ADT, c.COD_STA, c.DAT_ENV_PA, c.REF_LOT, ARC_FLG, c.ENV_AGE, dc.NUM_CHQ, "
				+ " dc.RIB_TIR, dc.NUM_LOT, dc.DAT_OPE, dc.MNT_CHQ, dc.RIB_BEN, dc.NOM_PRN, dc.DAT_EMI, dc.COD_SEN, dc.COD_NAT_ETA, "
				+ " dc.COD_ENR, dc.COD_AGE, dc.COD_BAN, dc.COD_AGE_DES, dc.COD_BAN_DES, dc.COD_LIE_EMI_CHQ, dc.COD_SIT, dc.COD_NAT_CPT, "
				+ " dc.COD_DEV, dc.COD_VAL, dc.REF_FIC, dc.COD_MOT_REJ, dc.RIB_TIR_REC, dc.NUM_EVT_ENV, dc.NUM_EVT_RCP,  dc.RJT_REG "
				+ " from Ibank.AD_CHEQUE_33@T24.BNA.TN c, IBANK.AD_DETAIL_CHEQUE_33@T24.BNA.TN dc  where c.NUM_LOT=c.NUM_LOT "
				+ " and c.DAT_OPE=dc.DAT_OPE and dc.COD_AGE_DES=c.COD_AGE and c.COD_VAL=dc.COD_VAL"
				+ " and c.COD_STA=7 and c.COD_SEN=2 and c.COD_ENR=11 and dc.COD_AGE_DES = " + codBct
				+ " and c.COD_VAL= " + codValChq + " and c.DAT_OPE=to_date('" + formaterDate.format(dateComptable)
				+ "','DD/MM/YYYY') and dc.COD_BAN_DES='03'";

		System.out.println(" requete listeChequeACH : " + requete);

		listeCheque = jt.query(requete, new RowMapper() {

			public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
				ChequeACHVo chequeACHVo = new ChequeACHVo();

				chequeACHVo.setMntTot(rs.getLong("MNT_TOT"));
				chequeACHVo.setNbrTot(rs.getLong("NBR_TOT"));
				chequeACHVo.setMntRejAdt(rs.getLong("MNT_REJ_ADT"));
				chequeACHVo.setRefOper(rs.getString("REF_OPER"));
				chequeACHVo.setCodSta(rs.getString("COD_STA"));
				chequeACHVo.setDatEnvPa(rs.getDate("DAT_ENV_PA"));
				chequeACHVo.setRefLot(rs.getString("REF_LOT"));
				chequeACHVo.setArcFlg(rs.getLong("ARC_FLG"));
				chequeACHVo.setEnvAge(rs.getString("ENV_AGE"));

				chequeACHVo.setNumChq(rs.getLong("NUM_CHQ"));
				chequeACHVo.setRibTir(rs.getLong("RIB_TIR"));
				chequeACHVo.setNumLot(rs.getLong("NUM_LOT"));
				chequeACHVo.setDatOpe(rs.getDate("DAT_OPE"));
				chequeACHVo.setMntChq(rs.getLong("MNT_CHQ"));
				chequeACHVo.setRibBen(rs.getLong("RIB_BEN"));
				chequeACHVo.setNomPrn(rs.getString("NOM_PRN"));
				chequeACHVo.setDatEmi(rs.getDate("DAT_EMI"));
				chequeACHVo.setCodSen(rs.getLong("COD_SEN"));
				chequeACHVo.setCodNatEta(rs.getLong("COD_NAT_ETA"));
				chequeACHVo.setCodEnr(rs.getLong("COD_ENR"));
				chequeACHVo.setCodAge(rs.getString("COD_AGE"));
				chequeACHVo.setCodBan(rs.getString("COD_BAN"));
				chequeACHVo.setCodAgeDes(rs.getString("COD_AGE_DES"));
				chequeACHVo.setCodBanDes(rs.getString("COD_BAN_DES"));
				chequeACHVo.setCodLieEmiChq(rs.getString("COD_LIE_EMI_CHQ"));
				chequeACHVo.setCodSit(rs.getLong("COD_SIT"));
				chequeACHVo.setCodNatCpt(rs.getLong("COD_NAT_CPT"));
				chequeACHVo.setCodDev(rs.getString("COD_DEV"));
				chequeACHVo.setCodVal(rs.getLong("COD_VAL"));
				chequeACHVo.setRefFic(rs.getString("REF_FIC"));
				chequeACHVo.setCodMotRej(rs.getString("COD_MOT_REJ"));
				chequeACHVo.setRibTirRec(rs.getString("RIB_TIR_REC"));
				chequeACHVo.setNumEvtEnv(rs.getLong("NUM_EVT_ENV"));
				chequeACHVo.setNumEvtRcp(rs.getLong("NUM_EVT_RCP"));
				chequeACHVo.setRjtReg(rs.getString("RJT_REG"));

				// ChequeACHVo.setImgR(rs.getBytes("IMG_R"));
				// ChequeACHVo.setImgV(rs.getBytes("IMG_V"));

				return chequeACHVo;
			}
		});

		logger.info("la requete cheque 33 ACH  a ramené " + String.valueOf(listeCheque.size()));

		return listeCheque;
	}
	
	public List<EffetACHVo> getListEffetsACHAgence(Date dateComptable, String codBct, String codValEff,
			String codEnrEff) {

		jt = new JdbcTemplate(dataSource);

		List<EffetACHVo> listeEffets = new ArrayList<EffetACHVo>();

		String requete = "SELECT adg.NUM_LOT, adg.DAT_OPE, adg.MNT_TOT, adg.NBR_TOT, adg.COD_SEN, adg.COD_AGE, adg.COD_BAN, adg.COD_VAL, "
				+ "adg.COD_NAT_ETA, adg.COD_DEV, adg.COD_ENR, adg.RNG_LOT, adg.REF_FIC, adg.COD_STA, adg.DAT_ENV_PA, adg.ENV_AGE, "
				+ "adg.ARC_FLG, addt.NUM_EFF, addt.MNT_EFF, addt.MNT_INT, addt.RIB_TIR, addt.RIB_BEN, addt.NOM_BEN, addt.NOM_TIR, "
				+ "addt.REF_COM_TIR, addt.COD_ACC, addt.COD_END, addt.DAT_ECH, addt.DAT_CRE, addt.REF_COM_BEN, addt.COD_ORD, "
				+ "addt.COD_SIT, addt.DAT_CMP, addt.MOT_REJ, addt.COD_SEN, addt.COD_NAT_ETA, addt.COD_AGE, addt.COD_BAN, addt.COD_AGE_DES, "
				+ "addt.COD_BAN_DES, addt.COD_NAT_CPT, addt.COD_DEV, addt.RNG_DET, addt.COD_VAL, addt.NUM_CMC7, addt.MNT_FRA, "
				+ "addt.RIB_TIR_INI, addt.DAT_ECH_INI, addt.LIE_CRE, addt.COD_RIS_BCT, addt.RJT_REG, addt.NUM_EVT_ENV, addt.NUM_EVT_RCP, "
				+ "addt.AUT_INF, addt.ID_IMG"
				+ "from AD_EFFET@T24.BNA.TN adg, AD_DETAIL_EFFET@T24.BNA.TN addt where  adg.NUM_LOT=addt.NUM_LOT "
				+ "and trunc(adg.DAT_OPE)=trunc(addt.DAT_OPE) and adg.COD_VAL=addt.COD_VAL and adg.COD_SEN=2 "
				+ "and addt.COD_VAL=" + codValEff + "and addt.COD_BAN='03' and addt.COD_ENR in (" + codEnrEff
				+ ") and trunc(adg.DAT_OPE)='" + formaterDate.format(dateComptable) + "' ";

		System.out.println(" requete listeChequeACH : " + requete);

		listeEffets = jt.query(requete, new RowMapper() {

			public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
				EffetACHVo effetACHVo = new EffetACHVo();

				// --- LOT ---
				effetACHVo.setMntTot(rs.getLong("MNT_TOT"));
				effetACHVo.setNbrTot(rs.getLong("NBR_TOT"));
				effetACHVo.setRngLot(rs.getInt("RNG_LOT"));
				effetACHVo.setCodSta(rs.getString("COD_STA"));
				effetACHVo.setDatEnvPa(rs.getDate("DAT_ENV_PA"));
				effetACHVo.setEnvAge(rs.getString("ENV_AGE"));
				effetACHVo.setArcFlg(rs.getInt("ARC_FLG"));

				// --- EFFET ---
				effetACHVo.setNumEff(rs.getString("NUM_EFF"));
				effetACHVo.setNumLot(rs.getLong("NUM_LOT"));
				effetACHVo.setDatOpe(rs.getDate("DAT_OPE"));
				effetACHVo.setMntEff(rs.getLong("MNT_EFF"));
				effetACHVo.setMntInt(rs.getLong("MNT_INT"));
				effetACHVo.setRibTir(rs.getString("RIB_TIR"));
				effetACHVo.setRibBen(rs.getString("RIB_BEN"));
				effetACHVo.setNomBen(rs.getString("NOM_BEN"));
				effetACHVo.setNomTir(rs.getString("NOM_TIR"));
				effetACHVo.setRefComTir(rs.getString("REF_COM_TIR"));
				effetACHVo.setCodAcc(rs.getInt("COD_ACC"));
				effetACHVo.setCodEnd(rs.getInt("COD_END"));
				effetACHVo.setDatEch(rs.getDate("DAT_ECH"));
				effetACHVo.setDatCre(rs.getDate("DAT_CRE"));
				effetACHVo.setRefComBen(rs.getString("REF_COM_BEN"));
				effetACHVo.setCodOrd(rs.getInt("COD_ORD"));
				effetACHVo.setCodSit(rs.getInt("COD_SIT"));
				effetACHVo.setDatCmp(rs.getDate("DAT_CMP"));
				effetACHVo.setMotRej(rs.getString("MOT_REJ"));
				effetACHVo.setCodSen(rs.getInt("COD_SEN"));
				effetACHVo.setCodNatEta(rs.getInt("COD_NAT_ETA"));
				effetACHVo.setCodEnr(rs.getInt("COD_ENR"));
				effetACHVo.setCodAge(rs.getString("COD_AGE"));
				effetACHVo.setCodBan(rs.getString("COD_BAN"));
				effetACHVo.setCodAgeDes(rs.getString("COD_AGE_DES"));
				effetACHVo.setCodBanDes(rs.getString("COD_BAN_DES"));
				effetACHVo.setCodNatCpt(rs.getInt("COD_NAT_CPT"));
				effetACHVo.setCodDev(rs.getString("COD_DEV"));
				effetACHVo.setRngDet(rs.getInt("RNG_DET"));
				effetACHVo.setCodVal(rs.getInt("COD_VAL"));
				effetACHVo.setRefFic(rs.getString("REF_FIC"));
				effetACHVo.setNumCmc7(rs.getString("NUM_CMC7"));
				effetACHVo.setMntFra(rs.getLong("MNT_FRA"));
				effetACHVo.setRibTirIni(rs.getString("RIB_TIR_INI"));
				effetACHVo.setDatEchIni(rs.getDate("DAT_ECH_INI"));
				effetACHVo.setLieCre(rs.getString("LIE_CRE"));
				effetACHVo.setCodRisBct(rs.getString("COD_RIS_BCT"));
				effetACHVo.setRjtReg(rs.getString("RJT_REG"));
				effetACHVo.setNumEvtEnv(rs.getLong("NUM_EVT_ENV"));
				effetACHVo.setNumEvtRcp(rs.getLong("NUM_EVT_RCP"));
				effetACHVo.setAutInf(rs.getString("AUT_INF"));
				effetACHVo.setIdImg(rs.getLong("ID_IMG"));
				return effetACHVo;
			}
		});

		System.out.println("la requete EFFET " + codValEff + " - " + codEnrEff + " ACH  a ramené "
				+ String.valueOf(listeEffets.size()));

		return listeEffets;
	}


}
