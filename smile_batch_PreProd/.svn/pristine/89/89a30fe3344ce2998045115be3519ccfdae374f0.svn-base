package com.bna.smile.model.domainecompensation.gestionrejet.traitement;

/**
 * @author Ayari haythem
 * @since 05/05/2013
 */
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.springframework.orm.hibernate3.HibernateTemplate;

import com.bna.commun.model.Arp;
import com.bna.commun.model.Cheque;
import com.bna.commun.model.Cnp;
import com.bna.commun.model.ComplementCnp;
import com.bna.commun.model.ComplementPapillon;
import com.bna.commun.model.ContratCpt;
import com.bna.commun.model.Papillon;
import com.bna.commun.model.Preavis;
import com.bna.commun.model.Structure;
import com.bna.commun.model.TraceCheque;
import com.bna.commun.traitements.Traitement;
import com.bna.commun.util.ContextHandler;
import com.bna.commun.util.StrHandler;
import com.bna.smile.model.constant.Constants;
import com.bna.smile.model.domainecommun.traitement.GenerateReferenceInterSiege;
import com.bna.smile.model.domainecompensation.gestionrejet.dao.CompensationDAO;
import com.bna.smile.model.domainecompensation.gestionrejet.model.EditionRejetVo;
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

public class ExtractionFichierTrt extends Traitement {

	Context context = ContextHandler.getContext();
	EditionRejetVo editionRejetVo = new EditionRejetVo();
	RejetDAO rejetDao = (RejetDAO) context.getBean("rejetDAO");

	public ExtractionFichierTrt() {
	}

	public Long getCodeValeur(Long numCheque, Date dateOpe) {
		ISearchEngine searchEngine = (SearchEngine) context
				.getBean("searchEngine");
		ICriteria criteria = searchEngine.createCriteria();
		IExpression expression = searchEngine.createExpression();

		criteria.add(expression.eq("traceChequeId.numChqChq", numCheque));
		criteria.add(expression.eq("traceChequeId.datOpeChq", dateOpe));

		List<TraceCheque> liste = (List<TraceCheque>) searchEngine.find(
				TraceCheque.class, criteria);
		if (!liste.isEmpty())
			return liste.get(0).getCodValTch();
		return Constants.COD_CHEQUE_PREMIERE_PRESENTATION;

	}

	private String getZoneLibre(int number) {
		String res = "";
		for (int i = 0; i < number; i++) {
			res += " ";
		}
		return res;
	}

	private String formatString(int number, String value) {
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

	private String formatNumber(int number, String value) {

		if (value == null)
			value = "";
		value = value.trim();

		return StrHandler.lpad(value, '0', number);
	}

	private String writeComplimentCnp(Cnp cnp, Cheque cheque, String codeVal,
			String numLot) {

		SimpleDateFormat formaterDate = new SimpleDateFormat("yyyyMMdd");
		String res = "";
		String codeRem = "03";
		String codeAg = "   ";
		String codeDev = "788";
		String codeNatRem = "1";
		String CodeSens = "1";

		List<String> output = new ArrayList<String>();

		List<ComplementCnp> liste = new ArrayList<ComplementCnp>();
		liste.addAll(cnp.getComplementCnps());
		for (int i = 0; i < liste.size(); i++) {
			ComplementCnp complementCnp = liste.get(i);

			output.add("1");
			output.add(codeVal);
			output.add(codeNatRem);
			output.add(codeRem);
			output.add(codeAg);
			output.add(formaterDate.format(editionRejetVo.getDateComptable()));
			output.add(numLot);
			output.add("21");
			output.add("788");
			output.add(String.format("%02d", i + 1));
			output.add(String.format("%07d", cheque.getChequeId()
					.getNumChqChq()));
			output.add(cheque.getChequeId().getRibTirChq());
			output.add(formaterDate.format(cnp.getDatCnpCnp()));
			output.add(String.format("%04d", cnp.getNumCnpCnp()));
			output.add("" + complementCnp.getCodNperCcnp());
			output.add("" + complementCnp.getCodTperCcnp());
			if (complementCnp.getCodNperCcnp().equals(1L))
				output.add(formatString(12, complementCnp.getNumPieCcnp()));
			else
				output.add(formatString(12, ""));

			output.add(formatString(30,
					UtilCtr.normalizeWord(complementCnp.getNomPrnCcnp())));
			output.add(formatNumber(15, complementCnp.getIdeRcsCcnp()));
			output.add(String.format("%5s", ""));
			output.add(formatString(50,
					UtilCtr.normalizeWord(complementCnp.getNomRueCcnp())));
			output.add(String.format("%4s", ""));
			output.add(formatString(4, complementCnp.getCodPosCcnp()));
			output.add("" + complementCnp.getCodQuaCcnp());
			output.add(getZoneLibre(160));
			output.add(System.getProperty("line.separator"));

		}
		// if (!output.isEmpty()) {
		// if (output.get(output.size() - 1).equals(
		// System.getProperty("line.separator")))
		// output.remove(output.size() - 1);
		// }
		for (int j = 0; j < output.size(); j++) {
			res += output.get(j);
		}
		return res;
	}

	private String writeComplimentAnrMan(Cnp cnp, Cheque cheque,
			String codeVal, String numLot) {

		SimpleDateFormat formaterDate = new SimpleDateFormat("yyyyMMdd");
		String res = "";
		String codeRem = "03";
		String codeAg = "   ";
		String codeDev = "788";
		String codeNatRem = "1";
		String CodeSens = "1";

		List<String> output = new ArrayList<String>();

		List<ComplementCnp> liste = new ArrayList<ComplementCnp>();
		liste.addAll(cnp.getComplementCnps());
		for (int i = 0; i < liste.size(); i++) {
			ComplementCnp complementCnp = liste.get(i);

			output.add("1");
			output.add(codeVal);
			output.add(codeNatRem);
			output.add(codeRem);
			output.add(codeAg);
			output.add(formaterDate.format(editionRejetVo.getDateComptable()));
			output.add(numLot);
			output.add("21");
			output.add("788");
			output.add(String.format("%02d", i + 1));
			output.add(String.format("%07d", cheque.getChequeId()
					.getNumChqChq()));
			output.add(cheque.getChequeId().getRibTirChq());
			output.add(formaterDate.format(cnp.getDatCnpCnp()));
			output.add(String.format("%04d", cnp.getNumCnpCnp()));
			output.add("" + complementCnp.getCodNperCcnp());
			output.add("" + complementCnp.getCodTperCcnp());
			if (complementCnp.getCodNperCcnp().equals(1L))
				output.add(formatNumber(12, complementCnp.getNumPieCcnp()));
			else
				output.add(formatString(12, ""));

			output.add(formatString(30,
					UtilCtr.normalizeWord(complementCnp.getNomPrnCcnp())));
			output.add(formatNumber(15, complementCnp.getIdeRcsCcnp()));
			output.add(String.format("%5s", ""));
			output.add(formatString(50,
					UtilCtr.normalizeWord(complementCnp.getNomRueCcnp())));
			output.add(String.format("%4s", ""));
			output.add(formatString(4, complementCnp.getCodPosCcnp()));
			output.add("" + complementCnp.getCodQuaCcnp());
			output.add(getZoneLibre(160));
			output.add(System.getProperty("line.separator"));

		}
		// if (!output.isEmpty()) {
		// if (output.get(output.size() - 1).equals(
		// System.getProperty("line.separator")))
		// output.remove(output.size() - 1);
		// }
		for (int j = 0; j < output.size(); j++) {
			res += output.get(j);
		}
		return res;
	}

	private Long getProvision(String rib, Date dateOpe) {
		ContratCpt cpt = UtilCtr.getContratCptByRIB(rib);
		Long provision = cpt.getProvision(dateOpe);
		return provision;
	}

	private String writeComplimentPap(Papillon pap, Cheque cheque, String numLot) {
		String result = "";
		SimpleDateFormat formaterDate = new SimpleDateFormat("yyyyMMdd");

		String codeVal = "" + Constants.COD_REJET_PAPILLON;
		String codeRem = "03";
		String codeAg = "   ";
		String codeDev = "788";
		String codeNatRem = "1";
		String CodeSens = "1";
		List<String> output = new ArrayList<String>();
		List<ComplementPapillon> liste = new ArrayList<ComplementPapillon>();
		liste.addAll(pap.getComplementPapillons());
		for (int i = 0; i < liste.size(); i++) {
			ComplementPapillon complementPap = liste.get(i);
			output.add(CodeSens);
			output.add(codeVal);
			output.add(codeNatRem);
			output.add(codeRem);
			output.add(codeAg);
			output.add(formaterDate.format(editionRejetVo.getDateComptable()));
			output.add(numLot);
			output.add("21");
			output.add(codeDev);
			output.add(String.format("%02d", i + 1));

			output.add(String.format("%07d", cheque.getChequeId()
					.getNumChqChq()));

			output.add(cheque.getChequeId().getRibTirChq());

			output.add(formaterDate.format(pap.getDatPapPap()));
			output.add(String
					.format("%04d", pap.getPapillonId().getNumPapPap()));

			output.add("" + complementPap.getCodNperCpap());
			output.add("" + complementPap.getCodTperCpap());

			if (complementPap.getCodNperCpap().equals(1L))
				output.add(formatString(12, complementPap.getNumPieCpap()));
			else
				output.add(formatString(12, ""));
			output.add(formatString(30,
					UtilCtr.normalizeWord(complementPap.getNomRsCpap())));
			output.add(formatNumber(15, complementPap.getIdeRcsCpap()));

			output.add(String.format("%5s", ""));
			output.add(formatString(50,
					UtilCtr.normalizeWord(complementPap.getNomRueCpap())));
			output.add(String.format("%4s", ""));
			output.add(formatString(4, complementPap.getCodPosCpap()));
			output.add("" + complementPap.getCodQuaCpap());
			output.add(getZoneLibre(90));
			output.add(System.getProperty("line.separator"));

		}
		// if (!output.isEmpty()) {
		// if (output.get(output.size() - 1).equals(
		// System.getProperty("line.separator")))
		// output.remove(output.size() - 1);
		// }
		for (int j = 0; j < output.size(); j++) {
			result += output.get(j);
		}
		return result;

	}

	private String formatMotif(String motifs) {
		if (motifs == null)
			motifs = "";
		String result = motifs;
		result = result.trim();
		result = result.replaceAll("null", "");
		int numberofZero = 8 - result.length();
		for (int i = 0; i < numberofZero; i++) {
			result += "0";
		}
		return result;
	}

	private String formatMontant(Long montant) {
		if (montant < 0)
			montant = 0L;
		String res = StrHandler.lpad("" + montant, '0', 15);
		return res;
	}

	private void createFileCheque(List<Cheque> cheques, String numLot,
			Date dateOpe) throws Exception {

		List<Cheque> cheque30 = new ArrayList<Cheque>();
		List<Cheque> cheque31 = new ArrayList<Cheque>();
		List<Cheque> cheque32 = new ArrayList<Cheque>();
		List<Cheque> cheque33 = new ArrayList<Cheque>();

		for (int i = 0; i < cheques.size(); i++) {
			if (cheques.get(i).getValeur().getCodValVal().equals(30L)) {
				cheque30.add(cheques.get(i));
			}
			if (cheques.get(i).getValeur().getCodValVal().equals(31L)) {
				cheque31.add(cheques.get(i));
			}
			if (cheques.get(i).getValeur().getCodValVal().equals(32L)) {
				cheque32.add(cheques.get(i));
			}
			if (cheques.get(i).getValeur().getCodValVal().equals(33L)) {
				cheque33.add(cheques.get(i));
			}
		}

		createFileChequeValeur(cheque30, 30L, numLot, dateOpe);
		createFileChequeValeur(cheque31, 31L, numLot, dateOpe);
		createFileChequeValeur(cheque32, 32L, numLot, dateOpe);
		createFileChequeValeur(cheque33, 33L, numLot, dateOpe);

	}

	private void createFileChequeValeur(List<Cheque> cheque30, Long valeur,
			String numLot, Date dateOpe) throws Exception {

		String res = "";
		List<String> output = new ArrayList<String>();

		String codeVal = "" + valeur;
		String codeRem = "03";
		String codeAg = "   ";
		String codeDev = "788";
		String codeNatRem = "1";
		String CodeSens = "1";
		Long totalMnt = 0L;
		SimpleDateFormat formaterDate = new SimpleDateFormat("yyyyMMdd");
		SimpleDateFormat formaterDateFile = new SimpleDateFormat("ddMMyyyy");
		CompensationDAO dao = new CompensationDAO();
		Structure strc = dao.findStructure(editionRejetVo.getParamAgence()
				.getCodStrcStrc());
		String structureBct = StrHandler.lpad(strc.getCodBctStrc(), '0', 3);
		SimpleDateFormat formatDateFile = new SimpleDateFormat("ddMMyyyy");
		String jjmmyyyySys = formatDateFile.format(new Date());
		String pathAg = "agence" + structureBct + File.separatorChar
				+ jjmmyyyySys + File.separatorChar + "travail";
		String rootPath = File.separatorChar + Configuration.getParentPath()
				+ File.separatorChar + Configuration.getLocalPathCheque()
				+ File.separatorChar + "emis" + File.separatorChar + "cheque";
		String succesPath = rootPath + File.separatorChar + "agence"
				+ structureBct + File.separatorChar + jjmmyyyySys
				+ File.separatorChar + "travail";
		String remotePathTresor = Configuration.getTresoreriePathSend();

		SimpleDateFormat timeFileExtractFormat = new SimpleDateFormat("HHmmss");
		String timeFileExtract = "-"
				+ formaterDateFile.format(editionRejetVo.getDateComptable())
				+ "-" + timeFileExtractFormat.format(new Date()) + "-";

		String fcheque30 = "03-" + structureBct + "-" + valeur + "-22-"
				+ numLot + timeFileExtract + "788.ENV";

		// System.out.println("file 30 : " + fcheque30);

		Long mntTotalInter = 0L;
		Long nbrTotalInter = 0L;
		Long mntTotalIntra = 0L;
		Long nbrTotalIntra = 0L;

		GenerateReferenceInterSiege generateReferenceInterSiege = new GenerateReferenceInterSiege();

		String quantieme = StrHandler.lpad(String.valueOf(new Double(
				generateReferenceInterSiege.getQuantieme(dateOpe)).intValue()),
				'0', 3);
		String refIntSg = String.format("%03d", editionRejetVo.getParamAgence()
				.getCodStrcStrc())
				+ quantieme + "1EC";
		if (!cheque30.isEmpty()) {

			res = "";
			codeVal = "" + valeur;
			codeRem = "03";
			codeAg = "   ";
			codeDev = "788";
			codeNatRem = "1";
			CodeSens = "1";

			output.add(CodeSens);
			output.add(codeVal);
			output.add(codeNatRem);
			output.add(codeRem);
			output.add(codeAg);
			output.add(formaterDate.format(editionRejetVo.getDateComptable()));
			output.add(numLot);
			output.add("12");
			output.add(codeDev);
			totalMnt = 0L;
			for (int i = 0; i < cheque30.size(); i++) {
				Long mntCheque = cheque30.get(i).getMntChequeTrace() != null ? cheque30
						.get(i).getMntChequeTrace() : cheque30.get(i)
						.getMntChqChq();

				totalMnt += mntCheque;
				if (cheque30.get(i).getCodBadeChq()
						.equals(cheque30.get(i).getCodBaemChq())) {
					mntTotalIntra += mntCheque;
					nbrTotalIntra += 1;
				} else {
					mntTotalInter += mntCheque;
					nbrTotalInter += 1;
				}
			}
			output.add(String.format("%015d", totalMnt));
			output.add(String.format("%010d", cheque30.size()));
			String addOnField1 = String.format("%015d", totalMnt);
			String addOnField2 = refIntSg;
			String addOnField3 = String.format("%024d", 0)
					+ String.format("%015d", totalMnt)
					+ String.format("%015d", 0);
			output.add(addOnField1);
			output.add(addOnField2);
			output.add(addOnField3);
			output.add(getZoneLibre(109 - (addOnField1.length()
					+ addOnField2.length() + addOnField3.length())));
			output.add(System.getProperty("line.separator"));
			mntTotalInter = 0L;
			nbrTotalInter = 0L;
			mntTotalIntra = 0L;
			nbrTotalIntra = 0L;
			for (int i = 0; i < cheque30.size(); i++) {

				Long mntCheque = cheque30.get(i).getMntChequeTrace() != null ? cheque30
						.get(i).getMntChequeTrace() : cheque30.get(i)
						.getMntChqChq();

				totalMnt += mntCheque;
				if (cheque30.get(i).getCodBadeChq()
						.equals(cheque30.get(i).getCodBaemChq())) {
					mntTotalIntra += mntCheque;
					nbrTotalIntra += 1;
				} else {
					mntTotalInter += mntCheque;
					nbrTotalInter += 1;
				}

				Cheque cheque = cheque30.get(i);
				output.add(CodeSens);
				output.add(codeVal);
				output.add(codeNatRem);
				output.add(codeRem);
				output.add(codeAg);
				output.add(formaterDate.format(editionRejetVo
						.getDateComptable()));
				output.add(numLot);
				output.add("22");
				output.add(codeDev);

				output.add(String.format("%015d", mntCheque));

				output.add(String.format("%07d", cheque.getChequeId()
						.getNumChqChq()));

				output.add(cheque.getChequeId().getRibTirChq());
				output.add(cheque.getCodBaemChq());
				output.add(codeAg);
				output.add(cheque.getChequeId().getRibBenChq());
				output.add(formatString(30,
						UtilCtr.normalizeWord(cheque.getNomPrnChq())));
				output.add(formaterDate.format(cheque.getDatEmiChq()));
				output.add(cheque.getCodLemiChq());
				output.add("" + cheque.getCodSbenChq());
				if (cheque.getCodNcptChq() == null)
					output.add(" ");
				else
					output.add("" + cheque.getCodNcptChq());

				output.add(formatMotif(cheque.getCodMrejChq()));
				output.add(getZoneLibre(18));
				output.add(System.getProperty("line.separator"));
			}

		} else {
			res = "";
			codeVal = "" + valeur;
			codeRem = "03";
			codeAg = "   ";
			codeDev = "788";
			codeNatRem = "1";
			CodeSens = "1";

			output.add(CodeSens);
			output.add(codeVal);
			output.add(codeNatRem);
			output.add(codeRem);
			output.add(codeAg);
			output.add(formaterDate.format(editionRejetVo.getDateComptable()));
			output.add(numLot);
			output.add("12");
			output.add(codeDev);
			output.add(String.format("%015d", 0));
			output.add(String.format("%010d", 0));
			String addOnField1 = String.format("%015d", 0);
			String addOnField2 = refIntSg;
			String addOnField3 = String.format("%024d", 0)
					+ String.format("%015d", 0) + String.format("%015d", 0);
			output.add(addOnField1);
			output.add(addOnField2);
			output.add(addOnField3);
			output.add(getZoneLibre(109 - (addOnField1.length()
					+ addOnField2.length() + addOnField3.length())));
			output.add(System.getProperty("line.separator"));
		}
		if (output.get(output.size() - 1).equals(
				System.getProperty("line.separator")))
			output.remove(output.size() - 1);

		for (int j = 0; j < output.size(); j++) {

			res += output.get(j);
		}

		File file = new File(rootPath + File.separatorChar + pathAg
				+ File.separatorChar + fcheque30);

		if (!file.exists())
			file.createNewFile();
		FileUtils.writeStringToFile(file, res);
		Util.copy(succesPath + File.separatorChar + fcheque30, remotePathTresor
				+ fcheque30);
		

	}

	private Long getMontantChequeTrace(Papillon papillon, String strcBct) {
		HibernateTemplate hibernateTemplate = (HibernateTemplate) ContextHandler
				.getContext().getBean("hibernateTemplate");
		Session sess = hibernateTemplate.getSessionFactory()
				.getCurrentSession();
		List<TraceCheque> listPapTrc = sess
				.createCriteria(TraceCheque.class)
				.add(Restrictions.eq("traceChequeId.datOpeChq",
						papillon.getDatPapPap()))
				.add(Restrictions.eq("traceChequeId.numChqChq", papillon
						.getPapillonId().getNumChqChq()))
				.add(Restrictions.eq("traceChequeId.ribTirChq", papillon
						.getPapillonId().getRibTirChq()))
				.add(Restrictions.eq("traceChequeId.ribBenChq", papillon
						.getPapillonId().getRibBenChq()))
				.add(Restrictions.ne("codSChq",
						Constants.COD_CHEQUE_MANUEL_VALIDE))
				// .add(Restrictions.ne("valeur.codValVal",
				// Constants.COD_REJET_PAPILLON))
				.add(Restrictions.eq("valeur.codValVal",
						Constants.COD_REJET_PAPILLON))
				.add(Restrictions.eq("codAgedTch", strcBct)).list();
		if (listPapTrc.isEmpty()) {
			return null;
		} else {
			return listPapTrc.get(0).getMntChqTch();

		}

	}

	public IValueObject perform(IValueObject vo) {

		editionRejetVo = (EditionRejetVo) vo;
		Long mntTotalInter = 0L;
		Long nbrTotalInter = 0L;
		Long mntTotalIntra = 0L;
		Long nbrTotalIntra = 0L;
		List<Cheque> cheque30 = new ArrayList<Cheque>();
		List<Cheque> chequeMan = new ArrayList<Cheque>();

		SimpleDateFormat formaterDate = new SimpleDateFormat("yyyyMMdd");
		SimpleDateFormat formaterDateFile = new SimpleDateFormat("ddMMyyyy");
		SimpleDateFormat formaterDateFileMan = new SimpleDateFormat("ddMMyy");

		SimpleDateFormat timeFileExtractFormat = new SimpleDateFormat("HHmmss");
		String timeFileExtract = formaterDateFile.format(editionRejetVo
				.getDateComptable())
				+ "-"
				+ timeFileExtractFormat.format(new Date()) + "-";
		CompensationDAO dao = new CompensationDAO();
		Structure strc = dao.findStructure(editionRejetVo.getParamAgence()
				.getCodStrcStrc());
		String structureBct = StrHandler.lpad(strc.getCodBctStrc(), '0', 3);
		String structureBna = StrHandler.lpad("" + strc.getCodStrcStrc(), '0',
				3);

		SimpleDateFormat formatDateFile = new SimpleDateFormat("ddMMyyyy");
		String jjmmyyyySys = formatDateFile.format(new Date());
		// path to agence
		String pathAg = "agence" + structureBct + File.separatorChar
				+ jjmmyyyySys + File.separatorChar + "travail";
		// path to directory
		String rootPath = File.separatorChar + Configuration.getParentPath()
				+ File.separatorChar + Configuration.getLocalPathCheque()
				+ File.separatorChar + "emis" + File.separatorChar + "cheque";
		String succesPath = rootPath + File.separatorChar + "agence"
				+ structureBct + File.separatorChar + jjmmyyyySys
				+ File.separatorChar + "travail";
		// remote path : tresorerie
		String remotePathTresor = Configuration.getTresoreriePathSend();

		String remotePathTresorMan = Configuration.getTresoreriePathManSend();
		File directoryFiles = new File(remotePathTresorMan + File.separatorChar
				+ structureBna);
		if (!directoryFiles.exists())
			directoryFiles.mkdir();

		try {

			ISearchEngine searchEngine = (SearchEngine) context
					.getBean("searchEngine");
			ICriteria criteria = searchEngine.createCriteria();
			IExpression expression = searchEngine.createExpression();
			HibernateTemplate hibernateTemplate = (HibernateTemplate) this.context
					.getBean("hibernateTemplate");
			Session sess = hibernateTemplate.getSessionFactory()
					.getCurrentSession();

			String res = "";
			List<String> output = new ArrayList<String>();
			String numLot = rejetDao.getNumLotCheque(editionRejetVo
					.getParamAgence().getCodStrcStrc());
			String codeVal = "";
			String codeRem = "03";
			String codeAg = "   ";
			String codeDev = "788";
			String codeNatRem = "1";
			String CodeSens = "1";

			Long totalMnt = 0L;

			String preavis = "03-" + structureBct + "-81-21-" + numLot + "-"
					+ timeFileExtract + "788.ENV";
			String pap = "03-" + structureBct + "-84-21-" + numLot + "-"
					+ timeFileExtract + "788.ENV";
			String cnp = "03-" + structureBct + "-82-21-" + numLot + "-"
					+ timeFileExtract + "788.ENV";
			String arp = "03-" + structureBct + "-83-21-" + numLot + "-"
					+ timeFileExtract + "788.ENV";

			/********* Preavis *********/
			mntTotalInter = 0L;
			nbrTotalInter = 0L;
			mntTotalIntra = 0L;
			nbrTotalIntra = 0L;
			List<Preavis> liste = new ArrayList<Preavis>();
			liste = sess
					.createCriteria(Preavis.class)
					.createAlias("cheque", "chq")
					.add(Restrictions.eq("datPrePre",
							editionRejetVo.getDateComptable()))
					.add(Restrictions.eq("chq.codEtatChq",
							Constants.COD_ETAT_CHEQUE_REJETEE))

					.add(Restrictions.or(Restrictions.isNull("chq.codSChq"),
							Restrictions.ne("chq.codSChq",
									Constants.COD_CHEQUE_MANUEL_VALIDE)))
					.add(Restrictions.eq("chq.codAgdeChq", structureBct))
					.list();

			res = "";
			output = new ArrayList<String>();

			codeVal = "" + Constants.COD_REJET_PREAVIS;
			codeRem = "03";
			codeAg = "   ";
			codeDev = "788";
			codeNatRem = "1";
			CodeSens = "1";

			totalMnt = 0L;
			for (int i = 0; i < liste.size(); i++) {

				totalMnt += liste.get(i).getCheque().getMntChqChq();
				if (liste.get(i).getCheque().getCodBadeChq()
						.equals(liste.get(i).getCheque().getCodBaemChq())) {
					mntTotalIntra += liste.get(i).getCheque().getMntChqChq();
					nbrTotalIntra += 1;
				} else {
					mntTotalInter += liste.get(i).getCheque().getMntChqChq();
					nbrTotalInter += 1;
				}
			}
			output.add(CodeSens);
			output.add(codeVal);
			output.add(codeNatRem);
			output.add(codeRem);
			output.add(codeAg);
			output.add(formaterDate.format(editionRejetVo.getDateComptable()));
			output.add(numLot);
			output.add("11");
			output.add(codeDev);
			output.add(formatMontant(totalMnt));
			output.add(String.format("%010d", liste.size()));
			output.add(getZoneLibre(109));
			output.add(System.getProperty("line.separator"));
			mntTotalInter = 0L;
			nbrTotalInter = 0L;
			mntTotalIntra = 0L;
			nbrTotalIntra = 0L;
			for (int i = 0; i < liste.size(); i++) {

				totalMnt += liste.get(i).getCheque().getMntChqChq();
				if (liste.get(i).getCheque().getCodBadeChq()
						.equals(liste.get(i).getCheque().getCodBaemChq())) {
					mntTotalIntra += liste.get(i).getCheque().getMntChqChq();
					nbrTotalIntra += 1;
				} else {
					mntTotalInter += liste.get(i).getCheque().getMntChqChq();
					nbrTotalInter += 1;
				}

				Cheque cheque = liste.get(i).getCheque();
				output.add(CodeSens);
				output.add(codeVal);
				output.add(codeNatRem);
				output.add(codeRem);
				output.add(codeAg);
				output.add(formaterDate.format(editionRejetVo
						.getDateComptable()));
				output.add(numLot);
				output.add("21");
				output.add(codeDev);
				output.add(formatMontant(cheque.getMntChqChq()));
				output.add(String.format("%07d", cheque.getChequeId()
						.getNumChqChq()));
				output.add(cheque.getChequeId().getRibTirChq());
				output.add(cheque.getCodBaemChq());
				output.add(codeAg);
				output.add(cheque.getChequeId().getRibBenChq());
				output.add(formaterDate.format(cheque.getDatEmiChq()));
				output.add(formaterDate.format(liste.get(i).getDatPrePre()));
				output.add(codeDev);
				if (liste.get(i).getMntProPre().longValue() < 0)
					output.add(formatMontant(0L));
				else
					output.add(formatMontant(liste.get(i).getMntProPre()));
				output.add(formatMotif(cheque.getCodMrejChq()));
				output.add(getZoneLibre(25));
				output.add(System.getProperty("line.separator"));

			}
			if (output.size() > 0)
				if (output.get(output.size() - 1).equals(
						System.getProperty("line.separator")))
					output.remove(output.size() - 1);
			for (int j = 0; j < output.size(); j++) {
				res += output.get(j);

			}

			File file = new File(rootPath + File.separatorChar + pathAg
					+ File.separatorChar + preavis);
			if (!file.exists())
				file.createNewFile();
			FileUtils.writeStringToFile(file, res);
			logger.error("fichier SRC Travail:" + file.getAbsolutePath());
			logger.error("fichier SRC traite:" + succesPath
					+ File.separatorChar + preavis);
			logger.error("fichier DEST:" + remotePathTresor + preavis);
			logger.info("fichier SRC Travail:" + file.getAbsolutePath());
			logger.info("fichier SRC traite:" + succesPath + File.separatorChar
					+ preavis);
			logger.info("fichier DEST:" + remotePathTresor + preavis);
			Util.copy(succesPath + File.separatorChar + preavis,
					remotePathTresor + preavis);
			

			/********** CNP *************/
			mntTotalInter = 0L;
			nbrTotalInter = 0L;
			mntTotalIntra = 0L;
			nbrTotalIntra = 0L;
			List<Cnp> listCnp = sess
					.createCriteria(Cnp.class)
					.createAlias("cheque", "chq")
					.add(Restrictions.eq("datCnpCnp",
							editionRejetVo.getDateComptable()))
					.add(Restrictions.eq("chq.codEtatChq",
							Constants.COD_ETAT_CHEQUE_REJETEE))
					.add(Restrictions.or(Restrictions.isNull("chq.codSChq"),
							Restrictions.ne("chq.codSChq",
									Constants.COD_CHEQUE_MANUEL_VALIDE)))
					.add(Restrictions.eq("chq.codAgdeChq", structureBct))
					.list();
			res = "";
			output = new ArrayList<String>();

			codeVal = "" + Constants.COD_REJET_CNP_OPPOSITION;
			codeRem = "03";
			codeAg = "   ";
			codeDev = "788";
			codeNatRem = "1";
			CodeSens = "1";

			totalMnt = 0L;
			for (int i = 0; i < listCnp.size(); i++) {

				totalMnt += listCnp.get(i).getCheque().getMntChqChq();
				if (listCnp.get(i).getCheque().getCodBadeChq()
						.equals(listCnp.get(i).getCheque().getCodBaemChq())) {
					mntTotalIntra += listCnp.get(i).getCheque().getMntChqChq();
					nbrTotalIntra += 1;
				} else {
					mntTotalInter += listCnp.get(i).getCheque().getMntChqChq();
					nbrTotalInter += 1;
				}
			}
			output.add(CodeSens);
			output.add(codeVal);
			output.add(codeNatRem);
			output.add(codeRem);
			output.add(codeAg);
			output.add(formaterDate.format(editionRejetVo.getDateComptable()));
			output.add(numLot);
			output.add("11");
			output.add(codeDev);
			output.add("00");
			output.add(String.format("%015d", totalMnt));
			output.add(String.format("%010d", listCnp.size()));
			output.add(getZoneLibre(297));
			output.add(System.getProperty("line.separator"));
			mntTotalInter = 0L;
			nbrTotalInter = 0L;
			mntTotalIntra = 0L;
			nbrTotalIntra = 0L;
			for (int i = 0; i < listCnp.size(); i++) {

				totalMnt += listCnp.get(i).getCheque().getMntChqChq();
				if (listCnp.get(i).getCheque().getCodBadeChq()
						.equals(listCnp.get(i).getCheque().getCodBaemChq())) {
					mntTotalIntra += listCnp.get(i).getCheque().getMntChqChq();
					nbrTotalIntra += 1;
				} else {
					mntTotalInter += listCnp.get(i).getCheque().getMntChqChq();
					nbrTotalInter += 1;
				}

				codeVal = "82";
				Cheque cheque = listCnp.get(i).getCheque();
				output.add(CodeSens);
				output.add(codeVal);
				output.add(codeNatRem);
				output.add(codeRem);
				output.add(codeAg);
				output.add(formaterDate.format(editionRejetVo
						.getDateComptable()));
				output.add(numLot);
				output.add("21");
				output.add(codeDev);
				output.add("00");
				output.add(String.format("%015d", cheque.getMntChqChq()));
				output.add(String.format("%07d", cheque.getChequeId()
						.getNumChqChq()));

				output.add(cheque.getChequeId().getRibTirChq());
				output.add(cheque.getCodBaemChq());
				output.add(codeAg);
				output.add(cheque.getChequeId().getRibBenChq());
				output.add(formaterDate.format(cheque.getDatEmiChq()));
				output.add(cheque.getCodLemiChq());
				output.add(formaterDate.format(listCnp.get(i).getDatCnpCnp()));
				output.add(String.format("%04d", listCnp.get(i).getNumCnpCnp()));
				output.add(formaterDate.format(cheque.getDatOpeChq()));
				if (cheque.getPreavis() != null) {
					output.add(formaterDate.format(cheque.getPreavis()
							.getDatPrePre()));
					if (cheque.getPreavis().getMntProPre().longValue() < 0)
						output.add(formatMontant(0L));
					else
						output.add(formatMontant(cheque.getPreavis()
								.getMntProPre()));
				} else {
					output.add(getZoneLibre(8));
					output.add(formatMontant(0L));

				}

				output.add(formaterDate.format(cheque.getDatDelChq()));
				output.add(formatMotif(cheque.getCodMrejChq()));
				output.add(String.format("%02d", listCnp.get(i)
						.getComplementCnps().size()));
				output.add(String.format("%128s", ""));
				output.add(String.format("%14s", ""));

				output.add(getZoneLibre(43));
				if (listCnp.get(i).getComplementCnps().size() > 0) {
					output.add(System.getProperty("line.separator"));
					output.add(writeComplimentCnp(listCnp.get(i), cheque, "82",
							numLot));
				} else
					output.add(System.getProperty("line.separator"));
				cheque30.add(cheque);

			}

			if (output.get(output.size() - 1).equals(
					System.getProperty("line.separator")))
				output.remove(output.size() - 1);

			for (int j = 0; j < output.size(); j++) {
				res += output.get(j);
			}

			file = new File(rootPath + File.separatorChar + pathAg
					+ File.separatorChar + cnp);
			if (!file.exists())
				file.createNewFile();
			FileUtils.writeStringToFile(file, res);
			Util.copy(succesPath + File.separatorChar + cnp, remotePathTresor
					+ cnp);
			

			/******** ARP ******/
			mntTotalInter = 0L;
			nbrTotalInter = 0L;
			mntTotalIntra = 0L;
			nbrTotalIntra = 0L;
			criteria = searchEngine.createCriteria();
			expression = searchEngine.createExpression();

			criteria.add(expression.eq("datArpArp",
					editionRejetVo.getDateComptable()));
			List<Arp> listArp = sess
					.createCriteria(Arp.class)
					.createAlias("cheque", "chq")
					.add(Restrictions.eq("datArpArp",
							editionRejetVo.getDateComptable()))
					.add(Restrictions.or(Restrictions.isNull("chq.codSChq"),
							Restrictions.ne("chq.codSChq",
									Constants.COD_CHEQUE_MANUEL_VALIDE)))
					.add(Restrictions.eq("chq.codAgdeChq", structureBct))
					.list();

			res = "";
			output = new ArrayList<String>();
			res = "";
			output = new ArrayList<String>();

			codeVal = "83";
			codeRem = "03";
			codeAg = "   ";
			codeDev = "788";
			codeNatRem = "1";
			CodeSens = "1";

			totalMnt = 0L;
			for (int i = 0; i < listArp.size(); i++) {

				totalMnt += listArp.get(i).getCheque().getMntChqChq();
				if (listArp.get(i).getCheque().getCodBadeChq()
						.equals(listArp.get(i).getCheque().getCodBaemChq())) {
					mntTotalIntra += listArp.get(i).getCheque().getMntChqChq();
					nbrTotalIntra += 1;
				} else {
					mntTotalInter += listArp.get(i).getCheque().getMntChqChq();
					nbrTotalInter += 1;
				}
			}
			output.add(CodeSens);
			output.add("83");
			output.add(codeNatRem);
			output.add(codeRem);
			output.add(codeAg);
			output.add(formaterDate.format(editionRejetVo.getDateComptable()));
			output.add(numLot);
			output.add("11");
			output.add(codeDev);

			output.add(String.format("%015d", totalMnt));
			output.add(String.format("%010d", listArp.size()));
			output.add(getZoneLibre(109));
			output.add(System.getProperty("line.separator"));
			mntTotalInter = 0L;
			nbrTotalInter = 0L;
			mntTotalIntra = 0L;
			nbrTotalIntra = 0L;
			for (int i = 0; i < listArp.size(); i++) {

				totalMnt += listArp.get(i).getCheque().getMntChqChq();
				if (listArp.get(i).getCheque().getCodBadeChq()
						.equals(listArp.get(i).getCheque().getCodBaemChq())) {
					mntTotalIntra += listArp.get(i).getCheque().getMntChqChq();
					nbrTotalIntra += 1;
				} else {
					mntTotalInter += listArp.get(i).getCheque().getMntChqChq();
					nbrTotalInter += 1;
				}

				codeVal = "83";
				Cheque cheque = listArp.get(i).getCheque();
				output.add(CodeSens);
				output.add("83");
				output.add(codeNatRem);
				output.add(codeRem);
				output.add(codeAg);
				output.add(formaterDate.format(editionRejetVo
						.getDateComptable()));
				output.add(numLot);
				output.add("21");
				output.add(codeDev);
				output.add(String.format("%015d", cheque.getMntChqChq()));
				output.add(String.format("%07d", cheque.getChequeId()
						.getNumChqChq()));

				output.add(cheque.getChequeId().getRibTirChq());
				output.add(cheque.getCodBaemChq());
				output.add(codeAg);
				output.add(cheque.getChequeId().getRibBenChq());
				output.add(formaterDate.format(cheque.getDatEmiChq()));
				output.add(cheque.getCodLemiChq());
				output.add(formaterDate.format(cheque.getCnp().getDatCnpCnp()));
				output.add(String
						.format("%04d", cheque.getCnp().getNumCnpCnp()));
				output.add(codeDev);
				output.add(String
						.format("%015d", listArp.get(i).getMntRegArp()));
				output.add(String.format("%015d", listArp.get(i)
						.getMntRginArp()));
				output.add(getZoneLibre(13));
				output.add(System.getProperty("line.separator"));
				// cheque30.add(cheque);

			}

			if (output.get(output.size() - 1).equals(
					System.getProperty("line.separator")))
				output.remove(output.size() - 1);

			for (int j = 0; j < output.size(); j++) {
				res += output.get(j);
			}

			file = new File(rootPath + File.separatorChar + pathAg
					+ File.separatorChar + arp);
			if (!file.exists())
				file.createNewFile();
			FileUtils.writeStringToFile(file, res);
			Util.copy(succesPath + File.separatorChar + arp, remotePathTresor
					+ arp);
			

			/********** PAPILLON *************/

			criteria = searchEngine.createCriteria();
			expression = searchEngine.createExpression();

			criteria.add(expression.eq("datPapPap",
					editionRejetVo.getDateComptable()));
			List<Papillon> listPap = sess
					.createCriteria(Papillon.class)
					.createAlias("cheque", "chq")
					.add(Restrictions.eq("datPapPap",
							editionRejetVo.getDateComptable()))
					.add(Restrictions.or(Restrictions.isNull("chq.codSChq"),
							Restrictions.ne("chq.codSChq",
									Constants.COD_CHEQUE_MANUEL_VALIDE)))
					.add(Restrictions.eq("chq.codAgdeChq", structureBct))
					.list();

			res = "";
			output = new ArrayList<String>();

			codeVal = "" + Constants.COD_REJET_PAPILLON;
			codeRem = "03";
			codeAg = "   ";
			codeDev = "788";
			codeNatRem = "1";
			CodeSens = "1";

			totalMnt = 0L;
			for (int i = 0; i < listPap.size(); i++) {
				Long mntTracePap = getMontantChequeTrace(listPap.get(i),
						structureBct);
				Long mntCheque = listPap.get(i).getCheque().getMntChqChq();

				if (mntTracePap != null) {
					mntCheque = mntTracePap;
				}
				totalMnt += mntCheque;
				if (listPap.get(i).getCheque().getCodBadeChq()
						.equals(listPap.get(i).getCheque().getCodBaemChq())) {
					mntTotalIntra += mntCheque;
					nbrTotalIntra += 1;
				} else {
					mntTotalInter += mntCheque;
					nbrTotalInter += 1;
				}
			}
			mntTotalInter = 0L;
			nbrTotalInter = 0L;
			mntTotalIntra = 0L;
			nbrTotalIntra = 0L;
			output.add(CodeSens);
			output.add(codeVal);
			output.add(codeNatRem);
			output.add(codeRem);
			output.add(codeAg);
			output.add(formaterDate.format(editionRejetVo.getDateComptable()));
			output.add(numLot);
			output.add("11");
			output.add(codeDev);
			output.add("00");
			output.add(String.format("%015d", totalMnt));
			output.add(String.format("%010d", listPap.size()));
			output.add(getZoneLibre(227));
			output.add(System.getProperty("line.separator"));

			for (int i = 0; i < listPap.size(); i++) {

				Long mntTracePap = getMontantChequeTrace(listPap.get(i),
						structureBct);
				Long mntCheque = listPap.get(i).getCheque().getMntChqChq();

				if (mntTracePap != null) {
					mntCheque = mntTracePap;
				}
				totalMnt += mntCheque;
				if (listPap.get(i).getCheque().getCodBadeChq()
						.equals(listPap.get(i).getCheque().getCodBaemChq())) {
					mntTotalIntra += mntCheque;
					nbrTotalIntra += 1;
				} else {
					mntTotalInter += mntCheque;
					nbrTotalInter += 1;
				}

				Cheque cheque = listPap.get(i).getCheque();
				output.add(CodeSens);
				output.add(codeVal);
				output.add(codeNatRem);
				output.add(codeRem);
				output.add(codeAg);
				output.add(formaterDate.format(editionRejetVo
						.getDateComptable()));
				output.add(numLot);
				output.add("21");
				output.add(codeDev);
				output.add("00");
				if (mntTracePap == null) {
					output.add(String.format("%015d", cheque.getMntChqChq()));

				} else {
					output.add(String.format("%015d", mntTracePap));
					cheque.setMntChequeTrace(mntTracePap);

				}
				output.add(String.format("%07d", cheque.getChequeId()
						.getNumChqChq()));

				output.add(cheque.getChequeId().getRibTirChq());
				output.add(cheque.getCodBaemChq());
				output.add(codeAg);
				output.add(cheque.getChequeId().getRibBenChq());
				output.add(formaterDate.format(cheque.getDatEmiChq()));
				output.add(cheque.getCodLemiChq());
				output.add(formaterDate.format(listPap.get(i).getDatPapPap()));
				output.add(String.format("%04d", listPap.get(i).getPapillonId()
						.getNumPapPap()));

				output.add(formatMotif(listPap.get(i).getCodMrejPap()));
				output.add(String.format("%02d", listPap.get(i)
						.getComplementPapillons().size()));
				output.add(getZoneLibre(154));
				if (listPap.get(i).getComplementPapillons().size() > 0) {
					output.add(System.getProperty("line.separator"));
					output.add(writeComplimentPap(listPap.get(i), cheque,
							numLot));
				} else
					output.add(System.getProperty("line.separator"));
				// updated 02-08-2016
				cheque.setCodMrejChq(listPap.get(i).getCodMrejPap());
				cheque30.add(cheque);

			}

			if (output.get(output.size() - 1).equals(
					System.getProperty("line.separator")))
				output.remove(output.size() - 1);
			for (int j = 0; j < output.size(); j++) {
				res += output.get(j);
			}

			file = new File(rootPath + File.separatorChar + pathAg
					+ File.separatorChar + pap);
			if (!file.exists())
				file.createNewFile();
			FileUtils.writeStringToFile(file, res);
			Util.copy(succesPath + File.separatorChar + pap, remotePathTresor
					+ pap);
			

			/********** Fichier Manuels ***/
			// String cnpMan =
			// "87" +
			// formaterDateFileMan.format(editionRejetVo.getDateComptable()) +
			// "."
			// + StrHandler.lpad("" +
			// UtilCtr.getParamAgenceFromGeneralCtr().getCodStrcStrc(), '0', 3);
			// String arpMan =
			// "88" +
			// formaterDateFileMan.format(editionRejetVo.getDateComptable()) +
			// "."
			// + StrHandler.lpad("" +
			// UtilCtr.getParamAgenceFromGeneralCtr().getCodStrcStrc(), '0', 3);
			// String anrMan =
			// "89" +
			// formaterDateFileMan.format(editionRejetVo.getDateComptable()) +
			// "."
			// + StrHandler.lpad("" +
			// UtilCtr.getParamAgenceFromGeneralCtr().getCodStrcStrc(), '0', 3);
			// /****** CNP Manuel ********/
			//
			// mntTotalInter = 0L;
			// nbrTotalInter = 0L;
			// mntTotalIntra = 0L;
			// nbrTotalIntra = 0L;
			// List<Cnp> listCnpMan =
			// sess.createCriteria(Cnp.class).createAlias("cheque", "chq")
			// .add(Restrictions.eq("datCnpCnp",
			// editionRejetVo.getDateComptable()))
			// .add(Restrictions.eq("chq.codEtatChq",
			// Constants.COD_ETAT_CHEQUE_REJETEE))
			// .add(Restrictions.or(Restrictions.eq("chq.codSChq",
			// Constants.COD_CHEQUE_MANUEL_VALIDE),
			// Restrictions.in("codMotRej", Constants.COD_MOTIF_OPPOSITION)))
			// .add(Restrictions.eq("chq.codAgdeChq", structureBct)).list();
			// res = "";
			// output = new ArrayList<String>();
			//
			// codeVal = "" + Constants.COD_REJET_CNP_MANUELLE;
			// codeRem = "03";
			// codeAg = "   ";
			// codeDev = "788";
			// codeNatRem = "1";
			// CodeSens = "1";
			//
			// totalMnt = 0L;
			// for (int i = 0; i < listCnpMan.size(); i++) {
			// totalMnt += listCnpMan.get(i).getCheque().getMntChqChq();
			// if
			// (listCnpMan.get(i).getCheque().getCodBadeChq().equals(listCnpMan.get(i).getCheque().getCodBaemChq()))
			// {
			// mntTotalIntra += listCnpMan.get(i).getCheque().getMntChqChq();
			// nbrTotalIntra += 1;
			// } else {
			// mntTotalInter += listCnpMan.get(i).getCheque().getMntChqChq();
			// nbrTotalInter += 1;
			// }
			// }
			//
			// output.add(CodeSens);
			// output.add(codeVal);
			// output.add(codeNatRem);
			// output.add(codeRem);
			// output.add(codeAg);
			// output.add(formaterDate.format(editionRejetVo.getDateComptable()));
			// output.add(numLot);
			// output.add("11");
			// output.add(codeDev);
			// output.add("00");
			// output.add(String.format("%015d", totalMnt));
			// output.add(String.format("%010d", listCnpMan.size()));
			// output.add(getZoneLibre(297));
			// output.add(System.getProperty("line.separator"));
			//
			// for (int i = 0; i < listCnpMan.size(); i++) {
			// Cnp cnpChq = listCnpMan.get(i).getCheque().getCnp();
			// if (cnpChq.getComplementCnps().size() > 0) {
			// codeVal = "" + Constants.COD_REJET_CNP_MANUELLE;
			// Cheque cheque = listCnpMan.get(i).getCheque();
			// output.add(CodeSens);
			// output.add(codeVal);
			// output.add(codeNatRem);
			// output.add(codeRem);
			// output.add(codeAg);
			// output.add(formaterDate.format(editionRejetVo.getDateComptable()));
			// output.add(numLot);
			// output.add("21");
			// output.add(codeDev);
			// output.add("00");
			// output.add(String.format("%015d", cheque.getMntChqChq()));
			// output.add(String.format("%07d",
			// cheque.getChequeId().getNumChqChq()));
			//
			// output.add(cheque.getChequeId().getRibTirChq());
			// output.add(cheque.getCodBaemChq());
			// output.add(cheque.getCodAgemChq());
			// output.add(cheque.getChequeId().getRibBenChq());
			// output.add(formaterDate.format(cheque.getDatEmiChq()));
			// output.add(cheque.getCodLemiChq());
			// output.add(formaterDate.format(listCnpMan.get(i).getDatCnpCnp()));
			// output.add(String.format("%04d",
			// listCnpMan.get(i).getNumCnpCnp()));
			// output.add(formaterDate.format(cheque.getPreavis().getDatPrePre()));
			// if (cheque.getPreavis() != null) {
			// output.add(formaterDate.format(cheque.getPreavis().getDatPrePre()));
			// if (cheque.getPreavis().getMntProPre().longValue() < 0)
			// output.add(formatMontant(0L));
			// else
			// output.add(formatMontant(cheque.getPreavis().getMntProPre()));
			// } else {
			// output.add(getZoneLibre(8));
			// output.add(String.format("%015d",
			// getProvision(cheque.getChequeId().getRibTirChq())));
			//
			// }
			//
			// Date dateDeliv = cheque.getDatDelChq();
			//
			// if (cheque.getDatDelChq().equals(cheque.getDatOpeChq())) {
			// Calendar calendar = Calendar.getInstance();
			// calendar.setTime(dateDeliv);
			// calendar.add(Calendar.DAY_OF_YEAR, -1);
			// dateDeliv = calendar.getTime();
			// }
			// output.add(formaterDate.format(dateDeliv));
			// output.add(formatMotif(cheque.getPreavis().getCodMrejPre()));
			// output.add(String.format("%02d",
			// listCnpMan.get(i).getComplementCnps().size()));
			// output.add(String.format("%128s", ""));
			// output.add(String.format("%14s", ""));
			//
			// output.add(getZoneLibre(131));
			// if (listCnpMan.get(i).getComplementCnps().size() > 0) {
			// output.add(System.getProperty("line.separator"));
			// output.add(writeComplimentAnrMan(listCnpMan.get(i), cheque, "87",
			// numLot));
			// } else {
			// output.add(System.getProperty("line.separator"));
			// }
			// // output.add(System.getProperty("line.separator"));
			// // chequeMan.add(cheque);
			// }
			// }
			//
			// if (listCnpMan.size() > 0) {
			// if (output.get(output.size() -
			// 1).equals(System.getProperty("line.separator")))
			// output.remove(output.size() - 1);
			// }
			//
			// for (int j = 0; j < output.size(); j++) {
			// res += output.get(j);
			// }
			//
			// File file = new File(remotePathTresorMan + File.separatorChar +
			// structureBna + File.separatorChar +
			// cnpMan);
			// if (!file.exists())
			// file.createNewFile();
			// FileUtils.writeStringToFile(file, res);
			// // Util.copy(succesPath + File.separatorChar +
			// cnpMan,remotePathTresorMan + File.separatorChar
			// // +structureBct+ File.separatorChar +cnpMan);
			// SuivFileTrt.ajouterFichierAvecMontant(cnpMan, structureBct,
			// editionRejetVo.getDateComptable(), 1, 81L,
			// mntTotalInter + mntTotalIntra, nbrTotalInter + nbrTotalIntra,
			// mntTotalInter, nbrTotalInter,
			// nbrTotalIntra, mntTotalIntra);
			//
			// /******** ARP Man ******/
			// mntTotalInter = 0L;
			// nbrTotalInter = 0L;
			// mntTotalIntra = 0L;
			// nbrTotalIntra = 0L;
			// criteria = searchEngine.createCriteria();
			// expression = searchEngine.createExpression();
			//
			// List<Arp> listArpMan =
			// sess.createCriteria(Arp.class).createAlias("cheque", "chq")
			// .add(Restrictions.eq("datArpArp",
			// editionRejetVo.getDateComptable()))
			// .add(Restrictions.eq("chq.codSChq",
			// Constants.COD_CHEQUE_MANUEL_VALIDE))
			// .add(Restrictions.eq("chq.codAgdeChq", structureBct)).list();
			//
			// res = "";
			// output = new ArrayList<String>();
			//
			// codeVal = "" + Constants.COD_ARP_MANUELLE;
			// codeRem = "03";
			// codeAg = "   ";
			// codeDev = "788";
			// codeNatRem = "1";
			// CodeSens = "1";
			//
			// totalMnt = 0L;
			// for (int i = 0; i < listArpMan.size(); i++) {
			// totalMnt += listArpMan.get(i).getCheque().getMntChqChq();
			// if
			// (listArpMan.get(i).getCheque().getCodBadeChq().equals(listArpMan.get(i).getCheque().getCodBaemChq()))
			// {
			// mntTotalIntra += listArpMan.get(i).getCheque().getMntChqChq();
			// nbrTotalIntra += 1;
			// } else {
			// mntTotalInter += listArpMan.get(i).getCheque().getMntChqChq();
			// nbrTotalInter += 1;
			// }
			// }
			//
			// output.add(CodeSens);
			// output.add("" + Constants.COD_ARP_MANUELLE);
			// output.add(codeNatRem);
			// output.add(codeRem);
			// output.add(codeAg);
			// output.add(formaterDate.format(editionRejetVo.getDateComptable()));
			// output.add(numLot);
			// output.add("11");
			// output.add(codeDev);
			// output.add("00");
			// output.add(String.format("%015d", totalMnt));
			// output.add(String.format("%010d", listArpMan.size()));
			// output.add(getZoneLibre(297));
			// output.add(System.getProperty("line.separator"));
			//
			// for (int i = 0; i < listArpMan.size(); i++) {
			// Cnp cnpChq = listArpMan.get(i).getCheque().getCnp();
			// if (cnpChq.getComplementCnps().size() > 0) {
			// codeVal = "" + Constants.COD_ARP_MANUELLE;
			// Cheque cheque = listArpMan.get(i).getCheque();
			// output.add(CodeSens);
			// output.add(codeVal);
			// output.add(codeNatRem);
			// output.add(codeRem);
			// output.add(codeAg);
			// output.add(formaterDate.format(editionRejetVo.getDateComptable()));
			// output.add(numLot);
			// output.add("21");
			// output.add(codeDev);
			// output.add("00");
			// output.add(String.format("%015d", cheque.getMntChqChq()));
			// output.add(String.format("%07d",
			// cheque.getChequeId().getNumChqChq()));
			//
			// output.add(cheque.getChequeId().getRibTirChq());
			// output.add(cheque.getCodBaemChq());
			// output.add(cheque.getCodAgemChq());
			// output.add(cheque.getChequeId().getRibBenChq());
			// output.add(formaterDate.format(cheque.getDatEmiChq()));
			// output.add(cheque.getCodLemiChq());
			// output.add(formaterDate.format(cheque.getCnp().getDatCnpCnp()));
			// output.add(String.format("%04d",
			// cheque.getCnp().getNumCnpCnp()));
			// output.add(formaterDate.format(cheque.getPreavis().getDatPrePre()));
			// if (cheque.getPreavis() != null) {
			// output.add(formaterDate.format(cheque.getPreavis().getDatPrePre()));
			// if (cheque.getPreavis().getMntProPre().longValue() < 0)
			// output.add(formatMontant(0L));
			// else
			// output.add(formatMontant(cheque.getPreavis().getMntProPre()));
			// } else {
			// output.add(getZoneLibre(8));
			// output.add(String.format("%015d",
			// getProvision(cheque.getChequeId().getRibTirChq())));
			//
			// }
			//
			// Date dateDeliv = cheque.getDatDelChq();
			//
			// if (cheque.getDatDelChq().equals(cheque.getDatOpeChq())) {
			// Calendar calendar = Calendar.getInstance();
			// calendar.setTime(dateDeliv);
			// calendar.add(Calendar.DAY_OF_YEAR, -1);
			// dateDeliv = calendar.getTime();
			// }
			// output.add(formaterDate.format(dateDeliv));
			// output.add(formatMotif(cheque.getPreavis().getCodMrejPre()));
			// output.add(String.format("%02d",
			// cheque.getCnp().getComplementCnps().size()));
			// output.add(codeDev);
			// output.add(String.format("%015d",
			// listArpMan.get(i).getMntRegArp()));
			// output.add(String.format("%015d",
			// listArpMan.get(i).getMntRginArp()));
			// output.add(getZoneLibre(152));
			// if (cheque.getCnp().getComplementCnps().size() > 0) {
			// output.add(System.getProperty("line.separator"));
			// output.add(writeComplimentAnrMan(cheque.getCnp(), cheque, "88",
			// numLot));
			// } else {
			// output.add(System.getProperty("line.separator"));
			// }
			// // output.add(System.getProperty("line.separator"));
			// }
			// // cheque30.add(cheque);
			//
			// }
			//
			// if (listArpMan.size() > 0) {
			// if (output.get(output.size() -
			// 1).equals(System.getProperty("line.separator")))
			// output.remove(output.size() - 1);
			// }
			//
			// for (int j = 0; j < output.size(); j++) {
			// res += output.get(j);
			// }
			//
			// file = new File(remotePathTresorMan + File.separatorChar +
			// structureBna + File.separatorChar + arpMan);
			// if (!file.exists())
			// file.createNewFile();
			// FileUtils.writeStringToFile(file, res);
			// // Util.copy(succesPath + File.separatorChar +
			// arpMan,remotePathTresorMan + File.separatorChar
			// // +structureBct+ File.separatorChar +arpMan);
			// SuivFileTrt.ajouterFichierAvecMontant(arpMan, structureBct,
			// editionRejetVo.getDateComptable(), 1, 81L,
			// mntTotalInter + mntTotalIntra, nbrTotalInter + nbrTotalIntra,
			// mntTotalInter, nbrTotalInter,
			// nbrTotalIntra, mntTotalIntra);
			//
			// /******** ANR Man ******/
			// mntTotalInter = 0L;
			// nbrTotalInter = 0L;
			// mntTotalIntra = 0L;
			// nbrTotalIntra = 0L;
			// criteria = searchEngine.createCriteria();
			// expression = searchEngine.createExpression();
			//
			// List<Anr> listAnrMan =
			// sess.createCriteria(Anr.class).createAlias("cheque", "chq")
			// .add(Restrictions.eq("datAnrAnr",
			// editionRejetVo.getDateComptable()))
			// .add(Restrictions.eq("chq.codEtatChq",
			// Constants.COD_ETAT_CHEQUE_REJETEE))
			// // .add(Restrictions.eq("chq.codSChq",
			// // Constants.COD_CHEQUE_MANUEL_VALIDE))
			// .add(Restrictions.eq("chq.codAgdeChq", structureBct)).list();
			//
			// res = "";
			// output = new ArrayList<String>();
			// res = "";
			// output = new ArrayList<String>();
			//
			// codeVal = "" + Constants.COD_REJET_ANR_MANUELLE;
			// codeRem = "03";
			// codeAg = "   ";
			// codeDev = "788";
			// codeNatRem = "1";
			// CodeSens = "1";
			//
			// totalMnt = 0L;
			// for (int i = 0; i < listAnrMan.size(); i++) {
			// totalMnt += listAnrMan.get(i).getCheque().getMntChqChq();
			// if
			// (listAnrMan.get(i).getCheque().getCodBadeChq().equals(listAnrMan.get(i).getCheque().getCodBaemChq()))
			// {
			// mntTotalIntra += listAnrMan.get(i).getCheque().getMntChqChq();
			// nbrTotalIntra += 1;
			// } else {
			// mntTotalInter += listAnrMan.get(i).getCheque().getMntChqChq();
			// nbrTotalInter += 1;
			// }
			// }
			//
			// output.add(CodeSens);
			// output.add("" + Constants.COD_REJET_ANR_MANUELLE);
			// output.add(codeNatRem);
			// output.add(codeRem);
			// output.add(codeAg);
			// output.add(formaterDate.format(editionRejetVo.getDateComptable()));
			// output.add(numLot);
			// output.add("11");
			// output.add(codeDev);
			// output.add("00");
			// output.add(String.format("%015d", totalMnt));
			// output.add(String.format("%010d", listAnrMan.size()));
			// output.add(getZoneLibre(297));
			// output.add(System.getProperty("line.separator"));
			//
			// for (int i = 0; i < listAnrMan.size(); i++) {
			// Cnp cnpChq = listAnrMan.get(i).getCheque().getCnp();
			// if (cnpChq.getComplementCnps().size() > 0) {
			// codeVal = "" + Constants.COD_REJET_ANR_MANUELLE;
			// Cheque cheque = listAnrMan.get(i).getCheque();
			// output.add(CodeSens);
			// output.add(codeVal);
			// output.add(codeNatRem);
			// output.add(codeRem);
			// output.add(codeAg);
			// output.add(formaterDate.format(editionRejetVo.getDateComptable()));
			// output.add(numLot);
			// output.add("21");
			// output.add(codeDev);
			// output.add("00");
			// output.add(String.format("%015d", cheque.getMntChqChq()));
			// output.add(String.format("%07d",
			// cheque.getChequeId().getNumChqChq()));
			// output.add(cheque.getChequeId().getRibTirChq());
			// output.add(cheque.getCodBaemChq());
			// output.add(cheque.getCodAgemChq());
			// output.add(cheque.getChequeId().getRibBenChq());
			// output.add(formaterDate.format(cheque.getDatEmiChq()));
			// output.add(cheque.getCodLemiChq());
			// output.add(formaterDate.format(cheque.getCnp().getDatCnpCnp()));
			// output.add(String.format("%04d",
			// cheque.getCnp().getNumCnpCnp()));
			// output.add(formaterDate.format(cheque.getPreavis().getDatPrePre()));
			// if (cheque.getPreavis() != null) {
			// output.add(formaterDate.format(cheque.getPreavis().getDatPrePre()));
			// if (cheque.getPreavis().getMntProPre().longValue() < 0)
			// output.add(formatMontant(0L));
			// else
			// output.add(formatMontant(cheque.getPreavis().getMntProPre()));
			// } else {
			// output.add(getZoneLibre(8));
			// output.add(String.format("%015d",
			// getProvision(cheque.getChequeId().getRibTirChq())));
			//
			// }
			//
			// Date dateDeliv = cheque.getDatDelChq();
			//
			// if (cheque.getDatDelChq().equals(cheque.getDatOpeChq())) {
			// Calendar calendar = Calendar.getInstance();
			// calendar.setTime(dateDeliv);
			// calendar.add(Calendar.DAY_OF_YEAR, -1);
			// dateDeliv = calendar.getTime();
			// }
			// output.add(formaterDate.format(dateDeliv));
			// output.add(formatMotif(cheque.getPreavis().getCodMrejPre()));
			// output.add(String.format("%02d",
			// cnpChq.getComplementCnps().size()));
			// output.add(formaterDate.format(cheque.getAnr().getDatAnrAnr()));
			// String nom_hn =
			// cnpChq.getSuiviHn().getNomPrnShn() != null ?
			// cnpChq.getSuiviHn().getNomPrnShn().trim() : "";
			// nom_hn +=
			// cnpChq.getSuiviHn().getNomNomShn() != null ?
			// cnpChq.getSuiviHn().getNomNomShn().trim() : "";
			//
			// output.add(formatString(30, nom_hn));
			// if (cnpChq.getSuiviHn().getTypSignShn().equals("E")) {
			// output.add(formaterDate.format(cnpChq.getSuiviHn().getDatExpLrShn()));
			// output.add("00000000");
			//
			// } else {
			// output.add("00000000");
			// output.add(formaterDate.format(cnpChq.getSuiviHn().getDatExpLrShn()));
			//
			// }
			//
			// output.add(getZoneLibre(131));
			// if (cnpChq.getComplementCnps().size() > 0) {
			// output.add(System.getProperty("line.separator"));
			// output.add(writeComplimentAnrMan(cnpChq, cheque, "89", numLot));
			// } else {
			// output.add(System.getProperty("line.separator"));
			// }
			// // output.add(System.getProperty("line.separator"));
			// // chequeMan.add(cheque);
			// }
			// }
			//
			// if (listAnrMan.size() > 0) {
			// if (output.get(output.size() -
			// 1).equals(System.getProperty("line.separator")))
			// output.remove(output.size() - 1);
			// }
			//
			// for (int j = 0; j < output.size(); j++) {
			// res += output.get(j);
			// }
			//
			// file = new File(remotePathTresorMan + File.separatorChar +
			// structureBna + File.separatorChar + anrMan);
			// if (!file.exists())
			// file.createNewFile();
			// FileUtils.writeStringToFile(file, res);
			// // Util.copy(succesPath + File.separatorChar +
			// anrMan,remotePathTresorMan + File.separatorChar
			// // +structureBct+ File.separatorChar +anrMan);
			// SuivFileTrt.ajouterFichierAvecMontant(anrMan, structureBct,
			// editionRejetVo.getDateComptable(), 1, 81L,
			// mntTotalInter + mntTotalIntra, nbrTotalInter + nbrTotalIntra,
			// mntTotalInter, nbrTotalInter,
			// nbrTotalIntra, mntTotalIntra);

			/********* ecritue des cheque auto ******/
			createFileCheque(cheque30, numLot,
					editionRejetVo.getDateComptable());
			/********* ecritue des cheque man ******/
			// createFileChequeMan(chequeMan, numLot);

		} catch (Exception e) {
			e.printStackTrace();
			com.oxia.fwk.core.Error erreur = new com.oxia.fwk.core.Error();
			StringBuffer text = new StringBuffer(
					"Erreur dans ExtractionFichierTrt : ");
			text.append(e.toString());
			erreur.setCode("100");
			erreur.setDescription(text.toString());
			erreur.setKey("ExtractionFichierTrt");
			editionRejetVo.addError(erreur);
			logger.error("Erreur au niveau ExtractionFichierTrt : ", e);
			throw new RuntimeException(e);

		}
		return editionRejetVo;
	}

	public void genCroText(ValueObject vo) {

	}

}