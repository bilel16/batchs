package com.bna.smile.model.domainecompensation.gestionrejet.traitement;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigInteger;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.bna.commun.model.Amende;
import com.bna.commun.model.Anr;
import com.bna.commun.model.Arp;
import com.bna.commun.model.BlocageCheque;
import com.bna.commun.model.Cheque;
import com.bna.commun.model.ChequeId;
import com.bna.commun.model.Cnp;
import com.bna.commun.model.ComplementCnp;
import com.bna.commun.model.ComplementPapillon;
import com.bna.commun.model.ContratCpt;
import com.bna.commun.model.ContratCptId;
import com.bna.commun.model.Decompte;
import com.bna.commun.model.Devise;
import com.bna.commun.model.ErrorMigration;
import com.bna.commun.model.MigrationCheque;
import com.bna.commun.model.MotifRejetChq;
import com.bna.commun.model.Papillon;
import com.bna.commun.model.PapillonId;
import com.bna.commun.model.Personne;
import com.bna.commun.model.Preavis;
import com.bna.commun.model.Signataire;
import com.bna.commun.model.SignataireId;
import com.bna.commun.model.SuiviHn;
import com.bna.commun.model.TraceCheque;
import com.bna.commun.model.TraceChequeId;
import com.bna.commun.model.Valeur;
import com.bna.commun.traitements.Traitement;
import com.bna.commun.util.CalanderHandler;
import com.bna.commun.util.ContextHandler;
import com.bna.commun.util.DateHandler;
import com.bna.commun.util.StrHandler;
import com.bna.smile.model.constant.Constants;
import com.bna.smile.model.domainecommun.service.CRUDservice;
import com.bna.smile.model.domainecommun.traitement.GetPersonneByNumPieTrt;
import com.bna.smile.model.domainecommun.traitement.GetPersonneCptTrt;
import com.bna.smile.model.domainecommun.traitement.GetPersonnelByCinTrt;
import com.bna.smile.model.domainecompensation.gestionrejet.dao.CompensationDAO;
import com.bna.smile.model.domainecompensation.gestionrejet.model.CompensationVo;
import com.bna.smile.model.traitementCompensationRecu.dao.RejetDAO;
import com.bna.smile.model.traitementCompensationRecu.model.Configuration;
import com.bna.smile.web.commun.controller.UtilCtr;
import com.oxia.fwk.context.Context;
import com.oxia.fwk.core.ICriteria;
import com.oxia.fwk.core.IExpression;
import com.oxia.fwk.core.ISearchEngine;
import com.oxia.fwk.core.IValueObject;
import com.oxia.fwk.core.ValueObject;
import com.oxia.fwk.searchengine.SearchEngine;

/**
 * @author nbdour
 * 
 */
public class MoulinetteMigrationTrt extends Traitement {

	Context context = ContextHandler.getContext();
	CRUDservice crudService = (CRUDservice) context.getBean("crudservice");
	ISearchEngine search = (SearchEngine) context.getBean("searchEngine");
	ICriteria criteria = search.createCriteria();
	IExpression expression = search.createExpression();
	CompensationDAO compensationDAO = (CompensationDAO) context.getBean("compensationDAO");
	RejetDAO rejetDao = (RejetDAO) context.getBean("rejetDAO");

	String valChq[] = { "30", "31", "32", "33" };
	SimpleDateFormat format = new SimpleDateFormat("ddMMyyyy");
	Date dateComptable = null;
	// test 13 jours limite
	Date dateMinRejet = null;

	// Logger
	public static final Logger logger = Logger.getLogger(MoulinetteMigrationTrt.class);

	@Override
	public IValueObject perform(IValueObject vo) {

		CompensationVo compensationVo = (CompensationVo) vo;
		String codStrc = StrHandler.lpad(""+ compensationVo.getStrutcure().getCodStrcStrc(), '0', 3);
		String srcRejet = Configuration.getPathFileRejet() + "TRPLACEM."+ codStrc;

		dateComptable = compensationVo.getDateComptable();
		dateMinRejet = CalanderHandler.getDateOuvrableBeforeNDays(dateComptable, -13);

		File f = new File(srcRejet);
		System.out.println(srcRejet);
		if (f.exists()) {
			logger.info("Treatment of file : " + srcRejet);
			logger.info("Date comptable :" + dateComptable + ", date min rejet :" + dateMinRejet);

			// Delete old data migration
			clearDb(StrHandler.lpad(""+ compensationVo.getStrutcure().getCodStrcStrc(), '0', 3)); // per agency
			// lire et parourir le fichier rejet
			try {
				BufferedWriter bufWriter = null;
				FileWriter fileWriter = null;
				InputStream ips = new FileInputStream(srcRejet);
				InputStreamReader ipsr = new InputStreamReader(ips);
				BufferedReader br = new BufferedReader(ipsr);
				String line = null, line171, line172;
				long numberLine = 0;
				while ((line = br.readLine()) != null && !line.equals("FIN")) {
					if (line.length() > 3 && line.substring(0, 3).equals("171")) {
						numberLine = numberLine + 1;
						line171 = line.substring(15);
						if ((line = br.readLine()) != null && line.length() > 3	&& line.substring(0, 3).equals("172")) {
							line172 = line.substring(15);
							line = line171 + ";" + line172;
							String vAttribs[] = line.split(";", -1);
							
							// s'il ya erreur dans le separateur des champs, on 	essaie de corriger ça , sinon erreur
							String[] attribs = correctFileSeparator(vAttribs);
							if (attribs != null) {
								// on cree une ligne dans la table migration cheque
								createMigrationCheque(codStrc, attribs);
							}
						}
					}

				}
				printStatMigration(codStrc,new Date(),new Date());
				br.close();
				fileWriter = new FileWriter(srcRejet, true);
				bufWriter = new BufferedWriter(fileWriter);
				bufWriter.newLine();
				System.out.println("Nombre de ligne total :" + numberLine);
				bufWriter.write("FIN");
				bufWriter.close();

			} catch (Exception e) {
				System.out.println(e.toString());
				throw new RuntimeException(e);
			}

//			} catch (Exception e) {
//				System.out.println(e.toString());
//			}
		}
		return null;
	}

	/***********************************************************************************************************************************************************************/

	/**
	 * createMigrationCheque( code structure agence , ligne rejet sous forme
	 * tableau)
	 **/
	/***********************************************************************************************************************************************************************/
	/***********************************************************************************************************************************************************************/

	public void createMigrationCheque(String codStrcStrc, String[] attribs)
			throws ParseException {

		logger.info("------------------ NumCpt-NumChq---------------------------------- :"
				+ attribs[0]);
		SimpleDateFormat formatLimitSit = new SimpleDateFormat("yyyyMMdd");
		String codSit = attribs[32].trim();
		/**
		 * // 02- preavis // 03- Cnp // 04- cnp remi hn // 05- cnp avec pv //
		 * 07- ANR // 74-75-76- manuel // 06-66-99 : arp // 99 : papillon // 88-
		 * paye // 98- Cnp opposition
		 **/

		try {

			Date datAnr = attribs[40].trim().equals("") ? null : format
					.parse(attribs[40].trim());
			Date datRej = attribs[1].trim().equals("") ? null : format
					.parse(attribs[1].trim());
			Date datMAJ = attribs[60].trim().equals("") ? null : format
					.parse(attribs[60].trim());
			Date datarp = attribs[42].trim().equals("") ? null : format
					.parse(attribs[42].trim());

			// on prend les rejets à partir de la date limite ( date comptable -
			// 12 jour : 1ere delais )
			// on applique pas cette regle pour les anr
			/*
			 * if( datRej.before(dateMinRejet) && ! codSit.equals("07")) {
			 * if(datarp ==null || (datarp !=null && datarp.before(dateMinRejet)
			 * )){ createErrorMigration(codStrcStrc, attribs, " old rejet") ;
			 * return; } }
			 */

			// on migre pas les cheque payé
			if (codSit.equals("88")) {
				createErrorMigration(codStrcStrc, attribs, "paye");
				return;
			}
			// on migre pas les anr >90 jours : pas de suivi pour 3eme delais
			double nbrjourPre = 0L;
			Long mntBloc = Long.valueOf(attribs[34].trim());
			if (datAnr != null) {
				nbrjourPre = DateHandler.getDaysBetween(datAnr, dateComptable);

				if (nbrjourPre > 91 && codSit.equals("07") && mntBloc==0) {
					createErrorMigration(codStrcStrc, attribs, "date anr  :"
							+ attribs[40] + " , ANR > 91 jours ");
					return;

				}
			}

			// on migre pas les rejets manuels
			if (codSit.equals("74") || codSit.equals("75")|| codSit.equals("76")) {
				createErrorMigration(codStrcStrc, attribs, "rejManuel");
				return;
			}

			// on migre pas les rejet au stade preavis qui ont depasse les
			// delais preavis ( preavis périmé)
			if (codSit.equals("02")) {
				Date dateLimitPreavis = format.parse(attribs[25]);
				if (dateLimitPreavis.before(dateComptable)) {
					createErrorMigration(codStrcStrc, attribs, "regPreavis");
					return;
				}

			}

			/*
			 * Ce controle a été supprime suite aux tests à la succursale .
			 * (25/04/2014)
			 * 
			 * if (codSit.equals("03")) { Date dateLimiteRemiseHuiss =
			 * format.parse(attribs[33]) ; if
			 * (dateLimiteRemiseHuiss.compareTo(sysDate)<0){
			 * createErrorMigration(codStrcStrc, attribs, "CnpNonRemi") ;
			 * return; }
			 * 
			 * }
			 * 
			 * 
			 * if (codSit.equals("04")) { Date dateLimiteHuiss =
			 * format.parse(attribs[31]) ; if
			 * (dateLimiteHuiss.compareTo(sysDate)<0) {
			 * createErrorMigration(codStrcStrc, attribs, "pvNonRecu") ; return;
			 * }
			 * 
			 * 
			 * 
			 * }
			 */

			// on migre pas les rejet au stade CNP qui ont depasse les delais
			// CNP ( CNP périmé)
			if (codSit.equals("05")) {
				// si delais cnp (4 jours après remise pv) expiré , CNP===> ANR
				Date datLimiSitCNP = formatLimitSit.parse(attribs[33]);
				if (datLimiSitCNP.before(dateComptable)) {
					createErrorMigration(codStrcStrc, attribs, "anrNonEdite");
					return;
				}
			}

			// on migre pas les ANR qui ont ete regularisé .
			if (codSit.equals("07")) {
				/** Ne pas prendre les anr > 90 jours **/
				// Date datLimiSit = formatLimitSit.parse(attribs[33]) ;
				// if(datLimiSit.before(sysDate)) {
				// createErrorMigration(codStrcStrc, attribs, "rejAnr") ;
				// return;
				// }
				/** Controle sur paiement (reglement ou arp) après ANR **/
				Date datePayInt = attribs[57].trim().equals("") ? null : format.parse(attribs[57]);
				if (datePayInt != null) {
					System.out.println("attribs[58] :" + attribs[57]+ ", dat pay mont int :" + datePayInt);
					createErrorMigration(codStrcStrc, attribs, "rejAnrPaye");
					return;
				}

			}
			MigrationCheque migChq = new MigrationCheque();
			migChq.setRcptchq(attribs[0]);
			migChq.setRdatrej(attribs[1]);
			migChq.setRnummvt(attribs[2]);
			migChq.setRdatsit(attribs[3]);
			migChq.setRcodbtir(attribs[4]);
			migChq.setRcodatir(attribs[5]);
			migChq.setRcoddev(attribs[6]);
			migChq.setRmontchq(attribs[7]);
			migChq.setRsoldpre(attribs[8]);
			migChq.setRdatpres(attribs[9]);
			migChq.setRcodbpre(attribs[10]);
			migChq.setRcodapre(attribs[11]);
			migChq.setRribben(attribs[12]);
			migChq.setRbenf(attribs[13]);
			migChq.setRcodreji(attribs[14]);
			migChq.setRcodrejn(attribs[15]);
			migChq.setRcodrejn2(attribs[16]);
			migChq.setRcodrejn3(attribs[17]);
			migChq.setRcodrejn4(attribs[18]);
			migChq.setRmotifn(attribs[19]);
			migChq.setRdatdel(attribs[20]);
			migChq.setRdatem(attribs[21]);
			migChq.setRlieem(attribs[22]);
			migChq.setRnumpap(attribs[23]);
			migChq.setRdatprea(attribs[24]);
			migChq.setRdatlim1r(attribs[25]);
			migChq.setRdatcnp(attribs[26]);
			migChq.setRnumcnp(attribs[27]);
			migChq.setRdatremh(attribs[28]);
			migChq.setRcpthuis(attribs[29]);
			migChq.setRnomhuis(attribs[30]);
			migChq.setRdatlimh(attribs[31]);
			migChq.setRcodsit(attribs[32]);
			migChq.setRlimsit(attribs[33]);
			migChq.setRmontbloc(attribs[34]);
			migChq.setRdatblocm(attribs[35]);
			migChq.setRnumexpl(attribs[36]);
			migChq.setRdatexpl(attribs[37]);
			migChq.setRnumlrec(attribs[38]);
			migChq.setRdatlrec(attribs[39]);
			migChq.setRdatanr(attribs[40]);
			migChq.setRdregchq(attribs[41]);
			migChq.setRdatarp(attribs[42]);
			migChq.setRmontver(attribs[43]);
			migChq.setRnumver(attribs[44]);
			migChq.setRcodpro(attribs[45]);
			migChq.setRcodop(attribs[46]);
			migChq.setRmodpay(attribs[47]);
			migChq.setRmonthuis(attribs[48]);
			migChq.setRdpayhuis(attribs[49]);
			migChq.setRmontprea(attribs[50]);
			migChq.setRdpayprea(attribs[51]);
			migChq.setRmcomcnp(attribs[52]);
			migChq.setRdpcomcnp(attribs[53]);
			migChq.setRmontcom(attribs[54]);
			migChq.setRdppmontcom(attribs[55]);
			migChq.setRmontint(attribs[56]);
			migChq.setRdpmontint(attribs[57]);
			migChq.setRmontquit(attribs[58]);
			migChq.setRdmontquit(attribs[59]);
			migChq.setRdatmaj(attribs[60]);
			migChq.setRcinsig1(attribs[61]);
			migChq.setRnomsig1(attribs[62]);
			migChq.setRcinsig2(attribs[63]);
			migChq.setRnomsig2(attribs[64]);
			migChq.setRcinsig3(attribs[65]);
			migChq.setRnomsig3(attribs[66]);
			migChq.setRsolddres(attribs[67]);
			migChq.setRtesthuis(attribs[68]);
			migChq.setRmontthuis1(attribs[69]);
			migChq.setRflagcom(attribs[70]);
			migChq.setRdatdbrej(attribs[71]);
			migChq.setRdatcrcnp(attribs[72]);
			migChq.setRfiller1(attribs[73]);
			migChq.setRfiller2(attribs[74]);
			migChq.setRmontinit(attribs[75]);
			migChq.setFlagsit(attribs[76]);
			migChq.setRmntbdev(attribs[77]);
			migChq.setRcourdev(attribs[78]);
			migChq.setRcodrejn5(attribs[79]);
			migChq.setRcodsitp(attribs[80]);
			migChq.setRrejcod21(attribs[81]);
			migChq.setRblocsauv(attribs[82]);
			migChq.setRmontints(attribs[83]);
			migChq.setRmontvers(attribs[84]);
			migChq.setRmontchqs(attribs[85]);
			migChq.setRmont353(attribs[86]);
			migChq.setRcodenv(attribs[87]);
			migChq.setRcodval(attribs[88]);
			migChq.setRdenvpap(attribs[89]);
			migChq.setMotif(null);
			String rribtir = calculerRIB(attribs[4] + attribs[5] + codStrcStrc+ attribs[0].substring(0, 10));
			migChq.setRribtir(rribtir);

			// ctrl sur le rib ben ;;
			String ribBen = "";
			if (migChq.getRribben().trim().equals("")|| migChq.getRribben().trim().equals("00000000000000000000")|| migChq.getRribben() == null) {
				ribBen = rejetDao.getRibBenAdt(migChq.getRcptchq().substring(10), rribtir, migChq.getRcodatir());
			} else {
				ribBen = migChq.getRribben();
			}

			if (ribBen.equals("00000000000000000000")) {
				createErrorMigration(codStrcStrc, attribs,"Rib benif incorrect ! ");
				return;
			}

			// if ( !(isNumeric(attribs[86].trim()))) {
			try {
				Long l = Long.valueOf(attribs[86].trim());
			} catch (Exception e) {
				createErrorMigration(codStrcStrc, attribs,"ERRO_PARSE_MONT_353 ");
				return;
			}

			// Checking Value
			String errorDate = "";

			try {
				Date datPre;
				Date datPayPre;
				Date datPayAmd;
				Date datPapAfterPreavis;
				Long numPap;
				Date datEditCnp;
				Date datCnp;
				Date datCnpDec;
				Long numCnp;
				Date datRcnp;
				Date datExploit;
				Date datPayHuiss;
				Date datArp;
				Date datReg;
				Date datInt;
				Date datBloc;
				Date datlRec;

				errorDate = "migChq.getRdatprea()";
				datPre = migChq.getRdatprea().trim().equals("") ? null : format
						.parse(migChq.getRdatprea());

				errorDate = "migChq.getRdpayprea()";
				datPayPre = migChq.getRdpayprea().trim().equals("") ? null
						: format.parse(migChq.getRdpayprea());

				errorDate = "migChq.getRdmontquit()";
				datPayAmd = migChq.getRdmontquit().trim().equals("") ? null
						: format.parse(migChq.getRdmontquit().trim());

				errorDate = "migChq.getRdenvpap()";
				datPapAfterPreavis = migChq.getRdenvpap().trim().equals("") ? null
						: format.parse(migChq.getRdenvpap());
				numPap = migChq.getRnumpap().trim().equals("") ? null : Long
						.valueOf(migChq.getRnumpap().trim());

				errorDate = "migChq.getRdatcnp()";
				datEditCnp = migChq.getRdatcnp().trim().equals("") ? null
						: format.parse(migChq.getRdatcnp());

				errorDate = "migChq.getRdatcrcnp()";
				datCnp = migChq.getRdatcrcnp().trim().equals("") ? null
						: format.parse(migChq.getRdatcrcnp());

				errorDate = "migChq.getRdpcomcnp()";
				datCnpDec = migChq.getRdpcomcnp().trim().equals("") ? null
						: format.parse(migChq.getRdpcomcnp().trim());
				numCnp = migChq.getRnumcnp().trim().equals("") ? null : Long
						.valueOf(migChq.getRnumcnp().trim());

				errorDate = "migChq.getRdatremh()";
				datRcnp = migChq.getRdatremh().trim().equals("") ? null
						: format.parse(migChq.getRdatremh().trim());

				errorDate = "migChq.getRdatexpl()";
				datExploit = migChq.getRdatexpl().trim().equals("") ? null
						: format.parse(migChq.getRdatexpl().trim());
				errorDate = "migChq.getRdpayhuis()";
				datPayHuiss = migChq.getRdpayhuis().trim().equals("") ? null
						: format.parse(migChq.getRdpayhuis().trim());
				errorDate = "migChq.getRdatanr()";
				datAnr = migChq.getRdatanr().trim().equals("") ? null : format
						.parse(migChq.getRdatanr().trim());
				errorDate = "migChq.getRdatarp()";
				datArp = migChq.getRdatarp().trim().equals("") ? null : format
						.parse(migChq.getRdatarp().trim());
				errorDate = "migChq.getRdregchq()";
				datReg = migChq.getRdregchq().trim().equals("") ? null : format
						.parse(migChq.getRdregchq().trim());
				errorDate = "migChq.getRdpmontint()";
				datInt = migChq.getRdpmontint().trim().equals("") ? null
						: format.parse(migChq.getRdpmontint().trim());
				errorDate = "migChq.getRdatblocm()";
				datBloc = migChq.getRdatblocm().trim().equals("") ? null
						: format.parse(migChq.getRdatblocm());
				errorDate = "migChq.getRnumlrec()";
				datlRec = migChq.getRnumlrec().trim().equals("") ? null
						: format.parse(migChq.getRnumlrec());
			} catch (Exception e) {
				createErrorMigration(codStrcStrc, attribs, "ERRO_PARSE_DAT :"
						+ errorDate);
				return;

			}

			crudService.create(migChq);
			createRejet(migChq, attribs, codStrcStrc);

		} catch (Exception e) {
			System.out.println(e.toString());
		}

	}

	public void createRejet(MigrationCheque migChq, String[] attribs,
			String codStrcStrc) throws Exception {

		Cheque cheque = new Cheque();
		Preavis preavis = null;
		Papillon papillon = null;
		Cnp cnp = null;
		SuiviHn suiviHn = null;
		Amende amende = null;
		Anr anr = null;
		BlocageCheque bloc = null;
		SimpleDateFormat format = new SimpleDateFormat("ddMMyyyy");
		long numChq = Long.valueOf(migChq.getRcptchq().substring(10));
		boolean finProc = false;
		cheque = new Cheque();
		cheque.setCodAgdeChq(migChq.getRcodatir());
		cheque.setCodBadeChq(migChq.getRcodbtir());
		cheque.setCodAgemChq(migChq.getRcodapre());
		cheque.setCodBaemChq(migChq.getRcodbpre());
		cheque.setCodEnrChq(Long.valueOf("21"));
		if (migChq.getRcodsit().trim().equals("88")) {
			cheque.setCodEtatChq("P");
		} else {
			cheque.setCodEtatChq("R");
		}
		cheque.setCodLemiChq(migChq.getRlieem());

		String mRejet = "";
		String mEnvoi = migChq.getRcodenv().trim();
		mRejet = mEnvoi;
		finProc = verifFinProc(migChq);
		if (finProc) {
			cheque.setCodFprocChq(Long.valueOf(1));
		}
		cheque.setCodMrejChq(mRejet);
		cheque.setCodNateChq("M"); // M(igration)
		cheque.setCodNcptChq("1");

		cheque.setCodSbenChq(Long.valueOf("1"));

		cheque.setCodSChq("M"); // Migration
		Date datDel = null;
		Date datEm = null;
		Date datOp = null;
		Date datPres = null;

		/******************************* Date Emission ******************************/

		try {
			datEm = migChq.getRdatem().equals("") ? null : format.parse(migChq
					.getRdatem());
		} catch (Exception e) {
			createErrorMigration(codStrcStrc, attribs, "La valeur :[ "
					+ attribs[21] + "] n'est pas une date emission");
			return;
		}

		/***************************** Date Presentation ******************************/

		try {
			datPres = migChq.getRdatpres().equals("") ? null : format
					.parse(migChq.getRdatpres());
		} catch (Exception e) {
			createErrorMigration(codStrcStrc, attribs, "La valeur :[ "
					+ attribs[9] + "] n'est pas une date presentation");
			return;
		}

		/**************************** Date delivrance chequier ************************/

		try {

			datDel = migChq.getRdatdel().equals("") ? null : format
					.parse(migChq.getRdatdel());
		} catch (Exception e) {
			createErrorMigration(codStrcStrc, attribs,
					"La valeur :[ " + migChq.getRdatdel()
							+ "] n'est pas une date  delivrance");
			return;
		}

		/************************** date Operation ( date Rejet ) *******************/

		try {
			datOp = migChq.getRdatrej().equals("") ? null : format.parse(migChq
					.getRdatrej());
		} catch (Exception e) {
			createErrorMigration(codStrcStrc, attribs,
					"La valeur :[ " + migChq.getRdatrej()
							+ "] n'est pas une date operation");
			return;
		}

		if (datOp == null) {
			createErrorMigration(codStrcStrc, attribs,
					"date operation cheque null");
			return;
		}

		cheque.setDatDelChq(datDel);
		cheque.setDatEmiChq(datEm);
		cheque.setDatOpeChq(datOp);

		Devise devise = new Devise();
		String codDev = migChq.getRcoddev().trim().equals("")
				|| migChq.getRcoddev().trim().equals("000") ? "788" : migChq
				.getRcoddev();
		devise.setCodDevDev(Long.valueOf(codDev));
		cheque.setDevise(devise);
		cheque.setMntChqChq(Long.valueOf(migChq.getRmontchq()));
		cheque.setMntPPartChq(null);
		cheque.setNomPrnChq(migChq.getRbenf());
		cheque.setNumEvenChq(Long.valueOf("0"));
		cheque.setNumEvrcpChq(Long.valueOf("0"));
		cheque.setNumLotChq(Long.valueOf("0001"));
		cheque.setRefFicChq("N");
		cheque.setRibTrecChq("N");

		// TODO : Check This with chiraz
		cheque.setMntPPartChq(null);

		// Cod valeur rejet : 31 , 30 , 32 ...
		String codVal = migChq.getRcodval().trim();
		if (!codVal.equals("")) {
			codVal = migChq.getRcodval().trim();
		}
		if (!Arrays.asList(valChq).contains(codVal)) {
			codVal = "30";
		}
		cheque.setValeur(new Valeur(Long.valueOf(codVal)));

		String ribTir = "";
		try {
			ribTir = calculerRIB(migChq.getRcodbtir() + migChq.getRcodatir()
					+ codStrcStrc + migChq.getRcptchq().substring(0, 10));
			ribTir = StrHandler.lpad(ribTir, '0', 20);
		} catch (Exception e) {
			System.out.println("Erreur calcul rib");
		}
		// rib ben
		String ribBen = "";
		if (migChq.getRribben().trim().equals("")
				|| migChq.getRribben().trim().equals("00000000000000000000")
				|| migChq.getRribben() == null) {
			ribBen = rejetDao.getRibBenAdt(migChq.getRcptchq().substring(10),
					ribTir, migChq.getRcodatir());
		} else {
			ribBen = migChq.getRribben();
		}

		if (ribBen.equals("00000000000000000000")) {
			createErrorMigration(codStrcStrc, attribs, "Rib benif incorrect ! ");
			return;
		}
		ChequeId id = new ChequeId(numChq, ribTir, ribBen);

		cheque.setChequeId(id);

		// Checking Value
		Date datPre;
		Date datPayPre;
		Date datPayAmd;
		Date datPapAfterPreavis;
		Long numPap;
		Date datEditCnp;
		Date datCnp;
		Date datCnpDec;
		Long numCnp;
		Date datRcnp;
		Date datExploit;
		Date datPayHuiss;
		Date datAnr;
		Date datArp;
		Date datReg;
		Date datInt;
		Date datBloc;
		Date datlRec;

		String errorDate = "";
		try {
			errorDate = "migChq.getRdatprea()";
			datPre = migChq.getRdatprea().trim().equals("") ? null : format
					.parse(migChq.getRdatprea());

			errorDate = "migChq.getRdpayprea()";
			datPayPre = migChq.getRdpayprea().trim().equals("") ? null : format
					.parse(migChq.getRdpayprea());

			errorDate = "migChq.getRdmontquit()";
			datPayAmd = migChq.getRdmontquit().trim().equals("") ? null
					: format.parse(migChq.getRdmontquit().trim());

			errorDate = "migChq.getRdenvpap()";
			datPapAfterPreavis = migChq.getRdenvpap().trim().equals("") ? null
					: format.parse(migChq.getRdenvpap());
			numPap = migChq.getRnumpap().trim().equals("") ? null : Long
					.valueOf(migChq.getRnumpap().trim());

			errorDate = "migChq.getRdatcnp()";
			datEditCnp = migChq.getRdatcnp().trim().equals("") ? null : format
					.parse(migChq.getRdatcnp());

			errorDate = "migChq.getRdatcrcnp()";
			datCnp = migChq.getRdatcrcnp().trim().equals("") ? null : format
					.parse(migChq.getRdatcrcnp());

			errorDate = "migChq.getRdpcomcnp()";
			datCnpDec = migChq.getRdpcomcnp().trim().equals("") ? null : format
					.parse(migChq.getRdpcomcnp().trim());
			numCnp = migChq.getRnumcnp().trim().equals("") ? null : Long
					.valueOf(migChq.getRnumcnp().trim());

			errorDate = "migChq.getRdatremh()";
			datRcnp = migChq.getRdatremh().trim().equals("") ? null : format
					.parse(migChq.getRdatremh().trim());

			errorDate = "migChq.getRdatexpl()";
			datExploit = migChq.getRdatexpl().trim().equals("") ? null : format
					.parse(migChq.getRdatexpl().trim());
			errorDate = "migChq.getRdpayhuis()";
			datPayHuiss = migChq.getRdpayhuis().trim().equals("") ? null
					: format.parse(migChq.getRdpayhuis().trim());
			errorDate = "migChq.getRdatanr()";
			datAnr = migChq.getRdatanr().trim().equals("") ? null : format
					.parse(migChq.getRdatanr().trim());
			errorDate = "migChq.getRdatarp()";
			datArp = migChq.getRdatarp().trim().equals("") ? null : format
					.parse(migChq.getRdatarp().trim());
			errorDate = "migChq.getRdregchq()";
			datReg = migChq.getRdregchq().trim().equals("") ? null : format
					.parse(migChq.getRdregchq().trim());
			errorDate = "migChq.getRdpmontint()";
			datInt = migChq.getRdpmontint().trim().equals("") ? null : format
					.parse(migChq.getRdpmontint().trim());
			errorDate = "migChq.getRdatblocm()";
			datBloc = migChq.getRdatblocm().trim().equals("") ? null : format
					.parse(migChq.getRdatblocm());
			errorDate = "migChq.getRnumlrec()";
			datlRec = migChq.getRnumlrec().trim().equals("") ? null : format
					.parse(migChq.getRnumlrec());
		} catch (Exception e) {
			createErrorMigration(codStrcStrc, attribs, "ERRO_PARSE_DAT :"
					+ errorDate);
			return;

		}
		Date datMig = new Date();

		Long montBloc = Long.valueOf(migChq.getRmontbloc());
		Long montVers = Long.valueOf(0);
		
		

		

		// if (montBloc ==0 && isNumeric(migChq.getRmont353())) {
		// montBloc = Long.valueOf(Long.valueOf(migChq.getRmont353()));
		// }
		if (isNumeric(migChq.getRmontver())) {
			montVers = Long.valueOf(Long.valueOf(migChq.getRmontver()));
		} else {
			createErrorMigration(codStrcStrc, attribs, "ERRO_PARSE_MONT_VERS ");

		}
		if (isNumeric(migChq.getRmontbloc())) {
			montBloc = Long.valueOf(Long.valueOf(migChq.getRmontbloc()));
		} else {
			createErrorMigration(codStrcStrc, attribs, "ERRO_PARSE_MONT_BLOC ");

		}

		Long mntAmdDec = null;
		if (Long.valueOf(migChq.getRmontquit()) != 0) {
			mntAmdDec = Long.valueOf(migChq.getRmontquit());
		}

		Long mntCnpDec =Long.valueOf(migChq.getRmcomcnp());
		if ( datCnp == null) { // 54
			mntCnpDec = null;
		}

		Long mntHnDec = null;
		if ((Long.valueOf(migChq.getRmonthuis()) + Long.valueOf(migChq
				.getRmontthuis1())) != 0)
			mntHnDec = formatMontant(Long.valueOf(migChq.getRmonthuis())
					+ Long.valueOf(migChq.getRmontthuis1()));

		Long mntIntDec = null;
		if (Long.valueOf(migChq.getRmontint()) != 0) {
			mntIntDec = formatMontant(Long.valueOf(migChq.getRmontint()));
		} else {
			if (datInt != null) {
				if ( isNumeric(migChq.getRmontints())) 
					mntIntDec = Long.valueOf(migChq.getRmontints());
				else
					mntIntDec = 0L;
				}
				
		}

		Long mntPreDec = 0L;
		mntPreDec = formatMontant(Long.valueOf(migChq.getRmontprea()));

		Long mntRegChDec = null;
		if (Long.valueOf(migChq.getRmontver()) != 0) {
			mntRegChDec = formatMontant(Long.valueOf(migChq.getRmontver()));
		} else {
			if (datArp != null)
				if ( isNumeric(migChq.getRmont353())) 
					mntRegChDec = Long.valueOf(migChq.getRmont353());
				else
					mntRegChDec = 0L;
					
		}

		// //////////////////////
		// cheque.setCodRejChq(new Valeur(Long.valueOf("81")));
		switch (Integer.valueOf(migChq.getRcodsit().trim())) {
		case 2:
			cheque.setCodRejChq(new Valeur(Long.valueOf(Constants.COD_PREAVIS)));
			break;
		case 3: // cnp non remi au hn
		case 4: // cnp remis hn
		case 5: // pv remis
		case 7: // cnp stade ANR
			cheque.setCodRejChq(new Valeur(Long.valueOf(Constants.COD_CNP)));
			break;
		case 98:// cnp opposition
			cheque.setCodRejChq(new Valeur(Long.valueOf(Constants.COD_CNP)));
			cheque.setCodFprocChq(Long.valueOf(1));
			break;
		case 6: // reglement D.compte
			cheque.setCodRejChq(new Valeur(Long.valueOf(Constants.COD_ARP)));
			break;
		case 66: // reglement L.benificiaire
			cheque.setCodRejChq(new Valeur(Long.valueOf(Constants.COD_ARP)));
			cheque.setCodEtatChq("P");
			datArp=null;
			break;

		case 99: // papillon
			if (datArp == null && datCnp == null && datExploit == null
					&& datlRec == null)
				cheque.setCodRejChq(new Valeur(Long
						.valueOf(Constants.COD_PAPILLON)));
			else if (datArp != null)
				cheque.setCodRejChq(new Valeur(Long.valueOf(Constants.COD_ARP)));
			break;
		}
		
		
		// adt verification
		  
		 
		if (cheque.getCodRejChq().getCodValVal() == 83 || cheque.getCodRejChq().getCodValVal() == 84) {
			System.out.println("<<<<<<<<<<<<<<<<<-  CHECKING ADT ->>>>>>>>>>>>>>>>");

			if (verifPaye(cheque)) {
				cheque.setCodEtatChq("P");
				migChq.setMotif("ADT_PAY");
				System.out.println("<<<<<<<<<<<<<<<<<- MODIF TO PAY ->>>>>>>>>>>>>>>>");
			}
		}
		
		

		String modPaiment = "";
		modPaiment = migChq.getRmodpay();
		// //logger.info("Inserting Cheque [" + numChq + "]");
		cheque.setDatMigChq(datMig);
		logger.info("Inserting Cheque [" + numChq + "]");
		crudService.create(cheque);
		System.out.println(mRejet);

		if (cheque.getCodEtatChq().equals("P")) {
			return;
		}
		if (finProc) {
			return;
		}

		//
		if (datPre != null) {

			preavis = new Preavis();
			preavis.setCheque(cheque);
			preavis.setChequeId(cheque.getChequeId());
			// preavis.setCodMrejPre(mRejet);
			preavis.setCodMrejPre(migChq.getRcodrejn3());
			preavis.setDatOpeChq(datOp);
			preavis.setMntProPre(montBloc);
			// preavis.setMntProPre(formatMontant(Long.valueOf(attribs[8])));
			preavis.setDatPrePre(datPre);
			preavis.setDatMigPre(datMig);
			// logger.info("Inserting Preavis [" + numChq + "]");
			crudService.create(preavis);

			createTrace(cheque, Long.valueOf(81), datPre);
		}

		// Creating CNP
		if (numCnp != null) {
			cnp = new Cnp();

			cnp.setCheque(cheque);
			// cnp.setCodMotRej(mRejet);
			if (mRejet != null && !mRejet.equals(""))
				cnp.setCodMotRej(mRejet);
			else
				cnp.setCodMotRej(migChq.getRcodrejn3());
			cnp.setDatCnpCnp(datCnp);
			cnp.setDatEditCnp(datEditCnp);
			cnp.setDatOpeChq(datOp);
			cnp.setNumCnpCnp(numCnp);
			cnp.setChequeId(id);

			cnp.setNbrEnrCom(0L);
			cnp.setRanChqCnp(0L);
			cnp.setRefClePub(0L);
			cnp.setRefFic("N");
			cnp.setSigCheAge("N");
			cnp.setSuiviHn(null);

			cnp.setDatMigCnp(datMig);
			crudService.create(cnp);

			createTrace(cheque, Long.valueOf(82), datCnp);

			List<ComplementCnp> complements = null;
			complements = rejetDao.getComplementCnpAdt(cnp,
					migChq.getRdatcrcnp());
			if (complements != null) {

				for (ComplementCnp complement : complements) {
					complement.setDatMigCmp(new Date());
					complement.setCnp(cnp);
					crudService.create(complement);
					
					Personne pers=new Personne();
					String numPie= StrHandler.lpad(""+Long.valueOf(complement.getNumPieCcnp()),'0',15);
					pers.setNumPcePers(numPie);
					GetPersonneByNumPieTrt getPersTrt = new GetPersonneByNumPieTrt();
					pers = rejetDao.getPersonneByNumPiece(pers);
					Signataire signataire = new Signataire();
					SignataireId signataireId = new SignataireId();
					signataireId.setNumChqChq(cheque.getChequeId().getNumChqChq());
					signataireId.setRibBenChq(cheque.getChequeId().getRibBenChq());
					signataireId.setRibTirChq(cheque.getChequeId().getRibTirChq());
					signataireId.setNumSeqPers(pers.getNumSeqPers());
					signataireId.setDatOpeChq(cheque.getDatOpeChq());
					signataire.setSignataireId(signataireId);
					signataire.setCodQuaPers(complement.getCodQuaCcnp());
					signataire.setNumPieceSig(numPie);
					signataire.setTypePieceSig(pers.getTypePiece());
					signataire.setDatMigSig(datMig) ;
					
					//
					complement.setDatNaiss(pers.getDatNaisPers());
					if(pers.getLibNaisPers() != null) {
						complement.setLibNaiss(pers.getLibNaisPers());
					} else {
						complement.setLibNaiss("");
					}
					
					if(pers.getProfession()!=null){
						complement.setCodProf(""+pers.getProfession().getProfessionId().getCodProfProf());
						}

					crudService.update(complement);
					crudService.create(signataire);
				}
			} else {
				// cnp= null ;
				// createErrorMigration(codStrcStrc, attribs,
				// "CNP sans complement ");

			}
		}

		if (!migChq.getRcpthuis().trim().equals("") && cnp != null) {
			suiviHn = new SuiviHn();
			suiviHn.setDatExpLrShn(datExploit);
			suiviHn.setDatOpeChq(datOp);
			suiviHn.setDatPvShn(datPayHuiss);
			// TODO : date paiement huissier notaire
			suiviHn.setDatRcnpShn(datRcnp);

			suiviHn.setMntFraisNImpShn(Long.valueOf(migChq.getRmonthuis()));
			// montant imposable
			suiviHn.setMntFraisShn(mntHnDec); // autre frais HN
			String nomHn = migChq.getRnomhuis().trim().replaceAll("( )+", " ")
					.length() > 20 ? migChq.getRnomhuis().trim()
					.replaceAll("( )+", " ").substring(0, 19) : migChq
					.getRnomhuis().trim().replaceAll("( )+", " ");
			suiviHn.setNomNomShn(nomHn);
			suiviHn.setNomPrnShn(" ");
			if (!migChq.getRnumexpl().trim().equals("")) {
				suiviHn.setNumExpLrShn(Long
						.valueOf(migChq.getRnumexpl().trim()));
				suiviHn.setTypSignShn("E");// exploit
			} else if (!migChq.getRnumlrec().trim().equals("")) {
				suiviHn.setNumExpLrShn(Long
						.valueOf(migChq.getRnumlrec().trim()));
				suiviHn.setTypSignShn("L");// exploit
			}

			suiviHn.setRibHnShn(migChq.getRcpthuis().trim());
			suiviHn.setTypRFisShn(null);
			suiviHn.setChequeId(id);
			suiviHn.setCnp(cnp);

			// logger.info("Inserting SuiviHn [" + numChq + "]");
			suiviHn.setDatMigHn(datMig);
			crudService.create(suiviHn);

		}

		// on insere anr si :
		// le cheque n'a pas ete regularise : ARP
		if (datAnr != null && cnp != null ){//&& !rejetDao.verifArpCci(cheque)) {
			anr = new Anr();
			anr.setChequeId(cheque.getChequeId());
			anr.setAmende(amende);
			anr.setDatAnrAnr(datAnr);
			anr.setDatOpeChq(datOp);
			anr.setCheque(cheque);
			// mig
			anr.setDatMigAnr(datMig);
			// logger.info("Inserting ANR [" + numChq + "]");
			crudService.create(anr);

			createTrace(cheque, Long.valueOf(89), datAnr);

		}

		if (datPayAmd != null && anr != null) {

			amende = new Amende();
			amende.setCodDelAme(null);
			amende.setDatOpeChq(datOp);
			amende.setDatPayAme(datPayAmd);
			amende.setMntPayAme(mntAmdDec);
			amende.setChequeId(cheque.getChequeId());
			amende.setAnr(anr);

			// logger.info("Inserting Amende [" + numChq + "]");
			amende.setDatMigAmd(datMig);
			crudService.create(amende);
		}

		// Creating ARP

		if (datArp != null) {

			Arp arp = new Arp();
			arp.setCheque(cheque);
			arp.setChequeId(cheque.getChequeId());
			arp.setDatArpArp(datArp);
			arp.setMntRegArp(mntRegChDec);
			arp.setMntRginArp(mntIntDec);
			arp.setDatOpeChq(datOp);
			arp.setRefFicArp(modPaiment);

			// logger.info("Inserting Arp [" + numChq + "]");
			arp.setDatMigArp(datMig);
			crudService.create(arp);
			// createTrace(cheque, Long.valueOf(83), datArp);

		}

		// Creating Decompte
		if (preavis != null) {
			Decompte decompte = new Decompte();
			decompte.setDatOpeChq(datOp);

			// decompte preavis
			decompte.setDatPreDec(datPayPre);
			decompte.setMntPreDec(mntPreDec);
			decompte.setTvaPreDec(Long.valueOf(0));

			// TODO : check this with chiraz
			decompte.setMntCnpDec(mntCnpDec);
			decompte.setDatCnpDec(datCnpDec);
			// TODO : check this with chiraz
			decompte.setTvaCnpDec(Long.valueOf(0));

			// decompte huissier notaire
			decompte.setMntHnDec(mntHnDec);
			decompte.setDatHnDec(datPayHuiss);
			

			// decompte reglement
			//decompte.setDatRegChDec(datReg);
			decompte.setDatRegChDec(datArp);
			// TODO : check this with chiraz
			decompte.setMntRegChDec(mntRegChDec);
			// decompte.set : type Reglement B or T

			// decompte amende
			decompte.setMntAmdeDec(mntAmdDec);
			decompte.setDatAmdeDec(datPayAmd);

			// decompte interet retard
			decompte.setMntIntDec(mntIntDec);
			decompte.setDatIntDec(datInt);

			decompte.setCheque(cheque);
			decompte.setChequeId(id);
			
			// update for reconst.
			if (decompte.getDatAmdeDec() != null ) {
				decompte.setEditAmdeDec(Long.valueOf(1));
			} 
			if (decompte.getDatHnDec() != null ) {
				decompte.setEditHnDec(Long.valueOf(1));
			}
			if (decompte.getDatRegChDec() != null ) {
				decompte.setEditRegChDec(Long.valueOf(1));
			}
			
			
			// mig
			// logger.info("Inserting Decompte [" + numChq + "]");
			decompte.setDatMigDec(datMig);
			crudService.create(decompte);

		}

		if (numPap != null) {

			if (!(preavis != null && datPapAfterPreavis == null)) {
				papillon = new Papillon();
				papillon.setCheque(cheque);
				papillon.setDatOpeChq(datOp);
				papillon.setNbrEnrPap(0L);
				if (datPapAfterPreavis != null) {
					papillon.setDatPapPap(datPapAfterPreavis);

				} else {
					papillon.setDatPapPap(datOp);
				}
				papillon.setRanPapPap(0L);
				// papillon.setCodMrejPap(mRejet);
				if (mRejet != null && !mRejet.equals(""))
					papillon.setCodMrejPap(mRejet);
				else
					papillon.setCodMrejPap(migChq.getRcodrejn()
							+ migChq.getRcodrejn5());
				papillon.setValeurCodValVal(0L);
				papillon.setRefFicPap("N");
				PapillonId papId = new PapillonId(numChq, ribBen, ribTir,
						numPap);
				papillon.setPapillonId(papId);
				papillon.setDatMigPap(datMig);
				crudService.create(papillon);
				createTrace(cheque, Long.valueOf(84), papillon.getDatPapPap());

				if (papillon != null) {
					List<ComplementPapillon> complements = null;
					complements = rejetDao.getComplementPapillonAdt(papillon);
					if (complements != null) {
						for (ComplementPapillon complement : complements) {
							complement.setDatMigCmp(new Date());
							complement.setPapillon(papillon);
							crudService.create(complement);
						}
					}
				}

			}
		}

		ContratCpt cpt = new ContratCpt();
		ContratCptId idCpt = new ContratCptId();
		idCpt.setCodStrcStrc(Long.valueOf(ribTir.substring(5, 8)));
		idCpt.setCodPrdPrd(Long.valueOf(ribTir.substring(8, 12)));
		idCpt.setNumCcptCcpt(Long.valueOf(ribTir.substring(12, 18)));
		cpt.setContratCptId(idCpt);

		
		crudService.update(cheque);
		if (!cheque.getCodEtatChq().equals("P") && cpt != null) {
			bloc = new BlocageCheque();
			bloc.setContratCpt(cpt);
			bloc.setNumChqChq(numChq);
			bloc.setContratCpt(UtilCtr.getContratCptByRIB(ribTir));
			bloc.setTypeBlocBloc("CHQ");
			bloc.setDatMigBloc(datMig);
			bloc.setDatFblocBloc(datReg);
			
			// bloc preavis et  papillon
			//if ( datPre !=null  && montBloc>0) {
			if (montBloc>0) {
				bloc.setNumBlocBloc(rejetDao.getSequenceMvtCompensation());
				if(datBloc!=null)
					bloc.setDatBlocBloc(datBloc);
				else 
					bloc.setDatBlocBloc(datOp);
				bloc.setMntBlocBloc(montBloc);
				crudService.create(bloc);
			}
			// bloc arp
			if (datArp != null && mntRegChDec>0) {
				bloc.setNumBlocBloc(rejetDao.getSequenceMvtCompensation());
				bloc.setDatBlocBloc(datArp);
				bloc.setMntBlocBloc(mntRegChDec);
				crudService.create(bloc);
			}
			// bloc int
			if (datArp != null && mntIntDec >0) {
				bloc.setNumBlocBloc(rejetDao.getSequenceMvtCompensation());
				bloc.setDatBlocBloc(datInt);
				bloc.setMntBlocBloc(mntIntDec);
				bloc.setTypeBlocBloc("INR");
				crudService.create(bloc);
			}
			

		}

	}

	/**
	 * function that calculate the key of the rib , and return the complete rib
	 * 
	 * @param RIB
	 * @return
	 */
	public String calculerRIB(String RIB) {
		String cle = "";
		String resultat = "";
		if (RIB.length() == 18) {

			String RI = RIB;
			BigInteger rr = new BigInteger(RI.concat("00"));
			int rest = rr.mod(new BigInteger("97")).intValue();
			int nb = 97 - rest;
			String nbr = "" + nb;
			if (nbr.length() == 1)
				resultat = RIB + "0" + nbr;
			else
				resultat = RIB + nbr;
		}
		return resultat;
	}

	/**
	 * function that test if attrib if numeric or no
	 * 
	 * @param s
	 * @return
	 */
	public boolean isNumeric(String s) {
		return s.matches("[-+]?\\d*\\.?\\d+");
	}

	/**
	 * Function that insert the line into table error_migration if there is an
	 * error with the line , like line without rib of benef
	 * 
	 * @param line
	 * @param motif
	 * @throws Exception
	 */
	public void createErrorMigration(String codStrcStrc, String[] attribs,
			String motif) {

		try {

			ErrorMigration error = new ErrorMigration();
			error.setRcptchq(attribs[0]);
			error.setRdatrej(attribs[1]);
			error.setRnummvt(attribs[2]);
			error.setRdatsit(attribs[3]);
			error.setRcodbtir(attribs[4]);
			error.setRcodatir(attribs[5]);
			error.setRcoddev(attribs[6]);
			error.setRmontchq(attribs[7]);
			error.setRsoldpre(attribs[8]);
			error.setRdatpres(attribs[9]);
			error.setRcodbpre(attribs[10]);
			error.setRcodapre(attribs[11]);
			error.setRribben(attribs[12]);
			error.setRbenf(attribs[13]);
			error.setRcodreji(attribs[14]);
			error.setRcodrejn(attribs[15]);
			error.setRcodrejn2(attribs[16]);
			error.setRcodrejn3(attribs[17]);
			error.setRcodrejn4(attribs[18]);
			error.setRmotifn(attribs[19]);
			error.setRdatdel(attribs[20]);
			error.setRdatem(attribs[21]);
			error.setRlieem(attribs[22]);
			error.setRnumpap(attribs[23]);
			error.setRdatprea(attribs[24]);
			error.setRdatlim1r(attribs[25]);
			error.setRdatcnp(attribs[26]);
			error.setRnumcnp(attribs[27]);
			error.setRdatremh(attribs[28]);
			error.setRcpthuis(attribs[29]);
			error.setRnomhuis(attribs[30]);
			error.setRdatlimh(attribs[31]);
			error.setRcodsit(attribs[32]);
			error.setRlimsit(attribs[33]);
			error.setRmontbloc(attribs[34]);
			error.setRdatblocm(attribs[35]);
			error.setRnumexpl(attribs[36]);
			error.setRdatexpl(attribs[37]);
			error.setRnumlrec(attribs[38]);
			error.setRdatlrec(attribs[39]);
			error.setRdatanr(attribs[40]);
			error.setRdregchq(attribs[41]);
			error.setRdatarp(attribs[42]);
			error.setRmontver(attribs[43]);
			error.setRnumver(attribs[44]);
			error.setRcodpro(attribs[45]);
			error.setRcodop(attribs[46]);
			error.setRmodpay(attribs[47]);
			error.setRmonthuis(attribs[48]);
			error.setRdpayhuis(attribs[49]);
			error.setRmontprea(attribs[50]);
			error.setRdpayprea(attribs[51]);
			error.setRmcomcnp(attribs[52]);
			error.setRdpcomcnp(attribs[53]);
			error.setRmontcom(attribs[54]);
			error.setRdppmontcom(attribs[55]);
			error.setRmontint(attribs[56]);
			error.setRdpmontint(attribs[57]);
			error.setRmontquit(attribs[58]);
			error.setRdmontquit(attribs[59]);
			error.setRdatmaj(attribs[60]);
			error.setRcinsig1(attribs[61]);
			error.setRnomsig1(attribs[62]);
			error.setRcinsig2(attribs[63]);
			error.setRnomsig2(attribs[64]);
			error.setRcinsig3(attribs[65]);
			error.setRnomsig3(attribs[66]);
			error.setRsolddres(attribs[67]);
			error.setRtesthuis(attribs[68]);
			error.setRmontthuis1(attribs[69]);
			error.setRflagcom(attribs[70]);
			error.setRdatdbrej(attribs[71]);
			error.setRdatcrcnp(attribs[72]);
			error.setRfiller1(attribs[73]);
			error.setRfiller2(attribs[74]);
			error.setRmontinit(attribs[75]);
			error.setFlagsit(attribs[76]);
			error.setRmntbdev(attribs[77]);
			error.setRcourdev(attribs[78]);
			error.setRcodrejn5(attribs[79]);
			error.setRcodsitp(attribs[80]);
			error.setRrejcod21(attribs[81]);
			error.setRblocsauv(attribs[82]);
			error.setRmontints(attribs[83]);
			error.setRmontvers(attribs[84]);
			error.setRmontchqs(attribs[85]);
			error.setRmont353(attribs[86]);
			error.setRcodenv(attribs[87]);
			error.setRcodval(attribs[88]);
			error.setRdenvpap(attribs[89]);
			error.setMotif(motif);
			String rribtir = calculerRIB(attribs[4] + attribs[5] + codStrcStrc
					+ attribs[0].substring(0, 10));
			error.setRribtir(rribtir);

			crudService.create(error);
		} catch (Exception e) {
			System.out.println(e.toString());
		}

	}

	public String[] checkLineSeparator(String codStrcStrc, String[] attribs)
			throws Exception {

		if (attribs.length == 91) {
			return attribs;
		} else {

			createErrorMigration(codStrcStrc, attribs,
					"Error in line separator ");
			return null;
		}
	}

	public String checkNumericData(String codStrcStrc, String line)
			throws Exception {
		// option -1 to get empty attribs at the and of line
		// String attribs[] = line.split(";",-1);
		String attribs[] = line.split(";", -1);
		// String attribs[] = new String[attribs1.length + 1];
		// for (int i = 0; i < 55; i++) {
		// attribs[i] = attribs1[i];
		// }
		// attribs[55] = "99999999";
		// for (int j = 56; j < attribs1.length; j++) {
		// attribs[j] = attribs1[j - 1];
		// }

		String motif = "";

		for (int i = 0; i < attribs.length; i++) {
			Integer  strData[] = {2,13,14,19,22,30,32,47,61,62,63,64,65,66,70,76,73,74,33,51,86};

			if (Arrays.asList(strData).contains(i))
				continue;
			if (!isNumeric(attribs[i].trim()) && !attribs[i].trim().equals(""))
				motif += "Pos." + i + ",";

		}

		if (motif.equals("")) {
			return "";
		} else {
			motif += "is not a number";
			createErrorMigration(codStrcStrc, attribs, motif);
			return null;
		}

	}

	public String[] checkConsistencyData(String codStrcStrc, String[] attribs)
			throws Exception {

		String motif = "";
		String ribTir = calculerRIB(attribs[4] + attribs[5] + codStrcStrc
				+ attribs[0].substring(0, 10));

		// numcnp, date cnp, sig , hn.
		if ((attribs[27].trim().equals("")) && !(attribs[27].trim().equals(""))) {
			motif += ", num cnp not found ";
		}
		if (!(attribs[27].trim().equals("")) && (attribs[27].trim().equals(""))) {
			motif += ", date cnp not found ";
		}
		if (!(attribs[27].trim().equals(""))
				&& !(attribs[27].trim().equals(""))
				&& (attribs[27].trim().equals(""))) {
			motif += ", rib hn not found ";
		}
		if (attribs[88].trim().equals("")) {
			attribs[88] = "30";

		}

		if (attribs[12].trim().equals("")
				|| attribs[12].trim().equals("00000000000000000000")
				|| attribs[12] == null) {
			if (attribs[0].substring(10).equals("6293055"))
				System.out.println("debug");

			String ribBen = null;
			ribBen = rejetDao.getRibBenAdt(attribs[0].substring(10), ribTir,
					attribs[5]);
			if (ribBen == null) {
				// attribs[12]="";
				motif = "rib benef not found";
			} else {
				System.out.println("Getting rib benificiaire from ADT ...: "
						+ ribBen);
				attribs[12] = ribBen;
			}
		}

		if (!attribs[36].trim().equals("") && !isNumeric(attribs[36].trim())) {
			motif += "," + attribs[36] + " n est un num exploit";
		}

		if (motif.equals("")) {
			return attribs;
		} else {
			createErrorMigration(codStrcStrc, attribs, motif);
			return null;
		}

	}

	public void createTrace(Cheque cheque, Long codRej, Date datOpe) {
		 TraceChequeId traceChequeId = new TraceChequeId();
		 //System.out.println("trace datOpe:" + datOpe);
		 traceChequeId.setDatOpeChq(datOpe);
		 
		 traceChequeId.setCodValVal(codRej);
		 traceChequeId.setNumChqChq(cheque.getChequeId().getNumChqChq());
		 traceChequeId.setRibBenChq(cheque.getChequeId().getRibBenChq());
		 traceChequeId.setRibTirChq(cheque.getChequeId().getRibTirChq());
		
		 TraceCheque traceCheque = new TraceCheque();
		 traceCheque.setCheque(cheque);
		 traceCheque.setTraceChequeId(traceChequeId);
		 traceCheque.setNumLotTch(cheque.getNumLotChq());
		 traceCheque.setMntChqTch(cheque.getMntChqChq());
		 
		 
		
		 traceCheque.setNomPrnTch(cheque.getNomPrnChq().trim());
		 traceCheque.setDatEmiTch(cheque.getDatEmiChq());
		 traceCheque.setCodSenTch(2L);// code sens recu
		 traceCheque.setCodEnrTch(cheque.getCodEnrChq());
		 traceCheque.setCodBanTch(cheque.getCodBadeChq());
		 traceCheque.setCodAgedTch(cheque.getCodAgdeChq());
		 traceCheque.setCodBandTch(cheque.getCodBaemChq());
		 traceCheque.setCodLieuTch(cheque.getCodLemiChq());
		 traceCheque.setCodSitTch(cheque.getCodSbenChq());
		 traceCheque.setCodNatcTch(cheque.getCodNcptChq());
		 traceCheque.setCodNateTch(cheque.getCodNateChq());
		 traceCheque.setCodDevTch("" + cheque.getDevise().getCodDevDev()); //
		 traceCheque.setCodValTch(cheque.getValeur().getCodValVal()); 
		 traceCheque.setCodMotrTch(cheque.getCodMrejChq());
		 traceCheque.setRefFicTch(cheque.getRefFicChq());
		 traceCheque.setRibTirRecTch(cheque.getRibTrecChq());
		 traceCheque.setNumEvtEnvTch(cheque.getNumEvenChq());
		 traceCheque.setNumEvtRcpTch(cheque.getNumEvrcpChq());
		 traceCheque.setRjtRegTch(cheque.getRjtRegChq());
		 traceCheque.setDatMigTch(new Date());
		 crudService.create(traceCheque);
	}

	public String[] correctFileSeparator(String vAttribs[]) {

		String[] attribs = new String[vAttribs.length + 1];

		if (vAttribs.length == 91) {
			return vAttribs;
		} else if (vAttribs.length == 90) {
			for (int i = 0; i < 54; i++) {
				attribs[i] = vAttribs[i];
			}
			attribs[54] = vAttribs[54].substring(0, 14);
			attribs[55] = vAttribs[55].length() > 15 ? vAttribs[55]
					.substring(14) : "";
			for (int j = 56; j < attribs.length; j++) {
				attribs[j] = vAttribs[j - 1];
			}

			return attribs;
		} else {
			createErrorMigration("120", attribs, "erreur ligne separateur");
			return null;
		}

	}

	public Long formatMontant(Long mnt) {

		return mnt;// * 1000 ;
	}

	@Override
	protected void genCroText(ValueObject arg0) {

	}

	// trie des motifs
	// trier la liste des motifs de rejet
	// private String trierMotif(String motif1, String motif2, String motif3,
	// String motif4) {
	//
	// List<String> motifList = new ArrayList<String>();
	// try {
	//
	// Map<String, Long> sorter = new HashMap<String, Long>();
	// if (motif1 != null && !motif1.equals(""))
	// motifList.add(motif1);
	// if (motif2 != null && !motif2.equals(""))
	// motifList.add(motif2);
	// if (motif3 != null && !motif3.equals(""))
	// motifList.add(motif3);
	// if (motif4 != null && !motif4.equals(""))
	// motifList.add(motif4);
	// // System.out.println("Resultat Avant tri ");
	// for (int i = 0; i < motifList.size(); i++)
	// motifList.get(i);
	//
	// for (int i = 0; i < motifList.size(); i++) {
	// sorter.put(motifList.get(i), getOrdreMotif(motifList.get(i)));
	// }
	//
	// // sorting Map e.g. HashMap, Hashtable by keys in Java
	// Map<String, Long> sorted = sortByValues(sorter);
	//
	// motifList.clear();
	// Set motifs = sorted.keySet();
	// for (Iterator i = motifs.iterator(); i.hasNext();) {
	// motifList.add(i.next().toString());
	// }
	//
	// } catch (Exception ex) {
	// ex.printStackTrace();
	// }
	//
	// String vReturn = "";
	// for (int i = 0; i < motifList.size(); i++)
	// vReturn = vReturn + motifList.get(i);
	// return vReturn;
	//
	// }
	//
	// private Long getOrdreMotif(String codeMotif) {
	// System.out.println(codeMotif);
	// ISearchEngine searchEngine = (SearchEngine) context
	// .getBean("searchEngine");
	// ICriteria criteria = searchEngine.createCriteria();
	// IExpression expression = searchEngine.createExpression();
	// criteria.add(expression.eq("motifRejetChqId.codMotfMrej", codeMotif));
	// List<MotifRejetChq> liste = (List<MotifRejetChq>) searchEngine.find(
	// MotifRejetChq.class, criteria);
	// return ((MotifRejetChq) liste.get(0)).getCodPrioMrej();
	// }
	//
	// // trier une collection de valeur Long
	// public static <K extends Comparable, V extends Comparable> Map<K, V>
	// sortByValues(
	// Map<K, V> map) {
	// List<Map.Entry<K, V>> entries = new LinkedList<Map.Entry<K, V>>(
	// map.entrySet());
	//
	// Collections.sort(entries, new Comparator<Map.Entry<K, V>>() {
	//
	// public int compare(Entry<K, V> o1, Entry<K, V> o2) {
	// return o1.getValue().compareTo(o2.getValue());
	// }
	// });
	//
	// // LinkedHashMap will keep the keys in the order they are inserted
	// // which is currently sorted on natural ordering
	// Map<K, V> sortedMap = new LinkedHashMap<K, V>();
	//
	// for (Map.Entry<K, V> entry : entries) {
	// sortedMap.put(entry.getKey(), entry.getValue());
	// }
	//
	// return sortedMap;
	// }

	// print etat migration
	 public void printStatMigration(String codStrcStrc, Date fromDate,
	 Date thruDate) throws IOException {
	
	 String pathReportCheque = "D:\\jasper\\stat_mig_chq.jrxml";
	 String pathReportPreavis = "D:\\jasper\\stat_mig_preavis.jrxml";
	 String pathReportPapillon = "D:\\jasper\\stat_mig_papillon.jrxml";
	 String pathReportCnp = "D:\\jasper\\stat_mig_cnp.jrxml";
	 String pathReportError = "D:\\jasper\\stat_mig_error.jrxml";
	 String pathReportMig = "D:\\jasper\\stat_mig_mig.jrxml";
	
	 // Util class must be static
	 Util util = new Util();
	 SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
	
	 Map params = new HashMap();
	
	 params.put("cod_strc_strc", codStrcStrc);
	 params.put("P_PATH", "D:\\jasper\\");
	 params.put("dat_deb", format.format(fromDate));
	 params.put("dat_fin", format.format(thruDate));
	
	 String dirAge = Util.createDirectory("D:\\" + codStrcStrc);
	
	 System.out.println("Directory :" + dirAge);
	 String pdfChq = dirAge + "\\stat_mig_chq.pdf";
	 String pdfPre = dirAge + "\\stat_mig_preavis.pdf";
	 String pdfPap = dirAge + "\\stat_mig_papillon.pdf";
	 String pdfCnp = dirAge + "\\stat_mig_cnp.pdf";
	 String pdfError = dirAge + "\\stat_mig_error.pdf";
	 String pdfMig = dirAge + "\\stat_mig_mig.pdf";
	
	 util.editJasper(pathReportCheque, pdfChq, params);
	 Util.ShowPDF(pdfChq);
	
	 util.editJasper(pathReportPreavis, pdfPre, params);
	 Util.ShowPDF(pdfPre);
	
	 util.editJasper(pathReportPapillon, pdfPap, params);
	 Util.ShowPDF(pdfPap);
	
	 util.editJasper(pathReportCnp, pdfCnp, params);
	 Util.ShowPDF(pdfCnp);
	
	 util.editJasper(pathReportError, pdfError, params);
	 Util.ShowPDF(pdfError);
	
	 util.editJasper(pathReportMig, pdfMig, params);
	 Util.ShowPDF(pdfMig);
	
	 }

	private boolean verifFinProc(MigrationCheque mig) {
		ISearchEngine searchEngine = (SearchEngine) context
				.getBean("searchEngine");
		ICriteria criteria = searchEngine.createCriteria();
		IExpression expression = searchEngine.createExpression();

		String codR1 = "";
		String codR2 = "";
		String codR3 = "";
		String codR4 = "";
		String codR5 = "";
		if (mig.getRcodrejn() != null)
			codR1 = mig.getRcodrejn().trim();
		if (mig.getRcodrejn2() != null)
			codR2 = mig.getRcodrejn2().trim();
		if (mig.getRcodrejn3() != null)
			codR3 = mig.getRcodrejn3().trim();
		if (mig.getRcodrejn4() != null)
			codR4 = mig.getRcodrejn4().trim();
		if (mig.getRcodrejn5() != null)
			codR5 = mig.getRcodrejn5().trim();

		criteria.add(expression.in("motifRejetChqId.codMotfMrej", new String[] {
				codR1, codR2, codR3, codR4, codR5 }));

		List<MotifRejetChq> liste = (List<MotifRejetChq>) searchEngine.find(
				MotifRejetChq.class, criteria);
		if (!liste.isEmpty()) {
			for (MotifRejetChq motifRejetChq : liste)
				if (motifRejetChq.getCodFprocMrej() == 1)
					return true;
		}
		return false;
	}

	boolean verifPaye(Cheque chq) throws ParseException {

		Long stade = chq.getCodRejChq().getCodValVal();
		boolean vReturn = false;

		if (stade == 84) {
			vReturn = rejetDao.verifExist33Adt(chq);

		} else {
			if (stade == 83) {
				vReturn = rejetDao.verifExist32Adt(chq);
			} else {
				return false;
			}
		}
		return vReturn;
	}

	public void clearDb(String codStrc) {
		System.out.println("Begining initializing db ...");
		System.out.println("Deleting  Old migration data...");
		rejetDao.initDbForMigration(codStrc);
		System.out.println("Ending initializing db ...");

	}

}
