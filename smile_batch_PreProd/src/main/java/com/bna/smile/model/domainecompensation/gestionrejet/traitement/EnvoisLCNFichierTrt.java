package com.bna.smile.model.domainecompensation.gestionrejet.traitement;

/**
 * @author Ayari haythem
 * 
 * @since 05/05/2013
 */
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.springframework.orm.hibernate3.HibernateTemplate;

import com.bna.commun.model.Client;
import com.bna.commun.model.ComplementEffetRecu;
import com.bna.commun.model.ContratCpt;
import com.bna.commun.model.DetailEffet;
import com.bna.commun.model.EffetRecu;
import com.bna.commun.model.Structure;
import com.bna.commun.traitements.Traitement;
import com.bna.commun.util.ContextHandler;
import com.bna.commun.util.StrHandler;
import com.bna.smile.model.constant.Constants;
import com.bna.smile.model.domainecommun.traitement.GenerateReferenceInterSiege;
import com.bna.smile.model.domainecompensation.gestionrejet.dao.CompensationDAO;
import com.bna.smile.model.domainecompensation.gestionrejet.model.RemiseEffetVo;
import com.bna.smile.model.traitementCompensationRecu.model.Configuration;
import com.bna.smile.web.commun.controller.UtilCtr;
import com.oxia.fwk.context.Context;
import com.oxia.fwk.core.ICriteria;
import com.oxia.fwk.core.IExpression;
import com.oxia.fwk.core.ISearchEngine;
import com.oxia.fwk.core.IValueObject;
import com.oxia.fwk.core.ValueObject;
import com.oxia.fwk.searchengine.SearchEngine;

public class EnvoisLCNFichierTrt extends Traitement {

	Context context = ContextHandler.getContext();

	public EnvoisLCNFichierTrt() {
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
		// System.out.println("Formated Motifs:" + result);
		return result;
	}

	private String formatMontant(Long montant) {
		if (montant == null)
			montant = 0L;
		else if (montant < 0)
			montant = 0L;
		String res = String.format("%015d", montant);
		return res;
	}

	RemiseEffetVo remiseEffetVo = new RemiseEffetVo();

	private String getCodEndAval(DetailEffet eff,String etatAvalEscompte) {
		if (eff.getRefComTir().startsWith("6")) {

			if (etatAvalEscompte != null && etatAvalEscompte.equals("E") && eff.getEffAval()) {
				return "3";
			} else if (etatAvalEscompte != null && etatAvalEscompte.equals("E") && !eff.getEffAval()) {
				return "1";
			} else if (etatAvalEscompte != null && etatAvalEscompte.equals("NE") && eff.getEffAval()) {
				return "2";
			} else {
				return "0";
			}
		} else if (eff.getRefComTir().startsWith("8")) {
			if (eff.getEffAval()) {
				return "2";
			} else {
				return "0";
			}

		} else {
			return "0";
		}
	}

	private List<String> codEndAval(DetailEffet eff, List<String> output,String etatAvalEscompte) {
		// if (eff.getRefComTir().startsWith("6")) {
		//
		// if (eff.getCodSitBen() != null && eff.getCodSitBen().equals("E") && eff.getEffAval())
		// output.add("3");
		// else if (eff.getCodSitBen() != null && eff.getCodSitBen().equals("E") && !eff.getEffAval())
		// output.add("1");
		// else if (eff.getCodSitBen() != null && eff.getCodSitBen().equals("NE") && eff.getEffAval())
		// output.add("2");
		// else
		// output.add("0");
		// }
		// if (eff.getRefComTir().startsWith("8")) {
		// if (eff.getEffAval())
		// output.add("2");
		// else
		// output.add("0");
		//
		// }
		output.add(getCodEndAval(eff,etatAvalEscompte));
		return output;
	}
	private String createEffet(Long strc, List<DetailEffet> liste, String numLot, String codeVal, String codEnrg,
			Date dateComptable) {

		SimpleDateFormat formaterDate = new SimpleDateFormat("yyyyMMdd");
		String res = "";
		List<String> output = new ArrayList<String>();

		String codeRem = "03";
		String codeAg = "   ";
		String codeDev = "788";
		String codeNatRem = "1";
		String CodeSens = "1";

		Long totalMnt = 0L;

		/********* LCN Prï¿½sentï¿½ *********/

		res = "";
		output = new ArrayList<String>();

		codeRem = "03";
		codeAg = "   ";
		codeDev = "788";
		codeNatRem = "1";
		CodeSens = "1";

		totalMnt = 0L;
		for (int i = 0; i < liste.size(); i++) {
			totalMnt += liste.get(i).getMntEff();
		}
		/******* ENR GLOBAL ******/
		output.add(CodeSens);
		output.add(codeVal);
		output.add(codeNatRem);
		output.add(codeRem);
		output.add(codeAg);
		output.add(formaterDate.format(dateComptable));
		output.add(numLot);
		output.add("11");
		output.add(codeDev);
		output.add("00");
		output.add(formatMontant(totalMnt));
		output.add(String.format("%010d", liste.size()));

		String prefix = "";
		if (codeVal.equals("41"))
			prefix = "RE1";
		else
			prefix = "RE0";
		output.add(getRefIntSig(strc, dateComptable, prefix));
		output.add(getZoneLibre(327 - 9));
		output.add(System.getProperty("line.separator"));

		for (int i = 0; i < liste.size(); i++) {
			/******* ENR DETAIL ******/
			DetailEffet eff = liste.get(i);
			output.add(CodeSens);
			output.add(codeVal);
			output.add(codeNatRem);
			output.add(codeRem);
			output.add(codeAg);
			output.add(formaterDate.format(dateComptable));
			output.add(numLot);
			output.add(codEnrg);
			output.add(codeDev);

			output.add(String.format("%02d", 0));
			output.add(formatMontant(eff.getMntEff()));
			output.add(formatMontant(eff.getMntIntr()));
			output.add(formatMontant(0L));
			output.add(eff.getEffetId().getNumEff());
			output.add(eff.getRibTir());
			if (eff.getRibTirIni() != null)
				output.add(eff.getRibTirIni());
			else
				output.add(eff.getRibTir());
			output.add(eff.getCodBqDes());
			output.add(codeAg);
			output.add(eff.getRibBen());
			ContratCpt cptBen = UtilCtr.getContratCptByRIB(eff.getRibBen());
			output.add(formatString(30, UtilCtr.normalizeWord(cptBen.get_nomIntiCcpt())));
			// update demande par nabil suite reclamation sur envois fichier ADT-14-04-2016
			output.add(formatString(30, UtilCtr.normalizeWord(eff.getNomTir())));
			output.add(StrHandler.rpad(eff.getRefComTir(), '0', 30));
			// String code = "0";
			// if (eff.getEffAcpt())
			// code = "1";
			// if (eff.getEffBap())
			// code = "3";
			// output.add(code);

			// 15-11-2016 **code acceptation 0 / 1**/
			if (eff.getEffAcpt()) {
				output.add("1");
			} else {
				output.add("0");
			}

			String code = "0";
			// if (eff.getEffAval())
			// code = "2";
			// output.add(code);
			// ajout controle endossement et aval pour le 8 et 6
			String etatAvalEscompte=eff.getCodSitBen();
			if (eff.getCodSitBen() != null && (eff.getCodSitBen().equals("E") || eff.getCodSitBen().equals("NE")))
				eff.setCodSitBen("0");
			output = codEndAval(eff, output,etatAvalEscompte);
			

			output.add(formaterDate.format(eff.getDatEch()));
			if (eff.getDatEchAnc() != null)
				output.add(formaterDate.format(eff.getDatEchAnc()));
			else
				output.add(formaterDate.format(eff.getDatEch()));

			output.add(formaterDate.format(eff.getDatCre()));
			output.add(formatString(30, UtilCtr.normalizeWord(eff.getLieuCre())));
			output.add(StrHandler.rpad(eff.getRefComBen(), '0', 30));

			// if (eff.getEffBap() != null)
			// if (eff.getEffBap())
			// code = "3";
			// output.add(code);

			// 15-11-2016 code ordre a payer si bap 1 sinon 0
			if (eff.getEffBap()) {
				output.add("1");
			} else {
				output.add("0");
			}
			code = "0";
			if (eff.getCodSitBen() != null)
				code = "" + eff.getCodSitBen();
			output.add(code);
			code = "0";
			if (eff.getCodNatCpt() != null)
				code = "" + eff.getCodNatCpt();
			output.add(code);

			output.add(formaterDate.format(dateComptable));
			output.add(StrHandler.lpad("", '0', 8));
			// TODO si escompte: 20 zero+tauxint sur7 caractere espace 600 (31) caractere
			if (eff.getRefComTir().startsWith("6")) {
				output.add("00000000000000000000 " + StrHandler.rpad(eff.getDescription(), '0', 6) + " " + "600");
				// output.add("00000000000000000000 " + eff.getDescription() + "00 " + "600");
				// System.out.println("<<<<>>>>>>"+"00000000000000000000"+eff.getDescription()+" "+"600");
				output.add(getZoneLibre(4));
			} else if (eff.getRefComTir().startsWith("8")) {
				output.add(formatString(6, ""));
				output.add(getZoneLibre(29));
			}
			// output.add(formatString(6, ""));
			// output.add(getZoneLibre(29));
			output.add(System.getProperty("line.separator"));
			// ajout ligne complement si avalisé
			if (!getCodEndAval(eff,etatAvalEscompte).equals("0") ) {
				output.add(CodeSens);
				output.add(codeVal);
				output.add(codeNatRem);
				output.add(codeRem);
				output.add(codeAg);
				output.add(formaterDate.format(dateComptable));
				output.add(numLot);
				output.add(codEnrg);
				output.add(codeDev);
				output.add("01");
				output.add(eff.getEffetId().getNumEff());
				output.add(formatString(30,
						UtilCtr.normalizeWord(cptBen.get_nomIntiCcpt() != null ? cptBen.get_nomIntiCcpt() : "BNA")));
				output.add(formatString(30, UtilCtr.normalizeWord(cptBen.getAdresseCorresp() != null ? cptBen
						.getAdresseCorresp().toString() : "TUNIS")));
				Client client=cptBen.getClient();
				Long typePce = client.getPersonne().getTypePiece().getCodTpceTpce();
				String numPce = client.getPersonne().getNumPcePers();
				List<Long> typeAutorise = Arrays.asList(0L, 1L, 2L, 3L, 4L);
				if (typeAutorise.contains(typePce)) {
					output.add("" + typePce);
					
				} else {
					output.add("1");
				}
				
				output.add(numPce.length() != 15 ? StrHandler.lpad(numPce, '0', 15) : numPce);
				output.add("000000");
				output.add(getZoneLibre(258));
				output.add(System.getProperty("line.separator"));
			}

		}
		// if (output.size() > 0)
		// if (output.get(output.size() - 1).equals(output.add(System.getProperty("line.separator"))))
		// output.remove(output.size() - 1);
		for (int j = 0; j < output.size(); j++) {
			// System.out.println(output.get(j).length() + ":" + output.get(j));
			res += output.get(j);

		}

		return res;
	}

	private String getRefIntSig(Long strc, Date dateComptable, String prefix) {
		GenerateReferenceInterSiege generateReferenceInterSiege = new GenerateReferenceInterSiege();
		String quantieme =
				StrHandler.lpad(
						String.valueOf(new Double(generateReferenceInterSiege.getQuantieme(dateComptable)).intValue()),
						'0', 3);
		return String.format("%03d", strc) + quantieme + prefix;

	}

	private String createEffetRecu(Long strc, List<EffetRecu> liste, String numLot, String codeVal, String codEnrg) {

		SimpleDateFormat formaterDate = new SimpleDateFormat("yyyyMMdd");
		String res = "";
		List<String> output = new ArrayList<String>();

		String codeRem = "03";
		String codeAg = "   ";
		String codeDev = "788";
		String codeNatRem = "1";
		String CodeSens = "1";

		Long totalMnt = 0L;

		/********* LCN Prï¿½sentï¿½ *********/

		res = "";
		output = new ArrayList<String>();

		codeRem = "03";
		codeAg = "   ";
		codeDev = "788";
		codeNatRem = "1";
		CodeSens = "1";

		totalMnt = 0L;
		for (int i = 0; i < liste.size(); i++) {
			totalMnt += liste.get(i).getMntEff();
		}
		/******* ENR GLOBAL ******/
		output.add(CodeSens);
		output.add(codeVal);
		output.add(codeNatRem);
		output.add(codeRem);
		output.add(codeAg);
		output.add(formaterDate.format(remiseEffetVo.getDateComptable()));
		output.add(numLot);
		output.add("12");
		output.add(codeDev);
		output.add("00");
		output.add(formatMontant(totalMnt));
		output.add(String.format("%010d", liste.size()));
		String prefix = "";
		if (codeVal.equals("41"))
			prefix = "RE1";
		else
			prefix = "RE0";
		output.add(getRefIntSig(strc, remiseEffetVo.getDateComptable(), prefix));
		output.add(getZoneLibre(327 - 9));
		output.add(System.getProperty("line.separator"));

		for (int i = 0; i < liste.size(); i++) {
			/******* ENR DETAIL ******/
			EffetRecu eff = liste.get(i);
			output.add(CodeSens);
			output.add(codeVal);
			output.add(codeNatRem);
			output.add(codeRem);
			output.add(codeAg);
			output.add(formaterDate.format(remiseEffetVo.getDateComptable()));
			output.add(numLot);
			output.add(codEnrg);
			output.add(codeDev);

			output.add(String.format("%02d", 0));
			output.add(formatMontant(eff.getMntEff()));
			// mnt int bloc adt repence
			if (eff.getMntInt() != null && eff.getMntInt().equals(0L) && eff.getMntIntIni() != null) {
				output.add(formatMontant(eff.getMntIntIni()));
			} else {
				output.add(formatMontant(eff.getMntInt()));
			}
			output.add(formatMontant(0L));
			// num eff 40 unique smile bloc adt repence
			if (eff.getCodVal().equals(40L) && eff.getNumEffIni() != null) {
				output.add(eff.getNumEffIni());
			} else {
				output.add(eff.getEffetId().getNumEff());
			}
			output.add(eff.getRibTir());
			if (eff.getRibTirIni() != null)
				output.add(eff.getRibTirIni());
			else
				output.add(eff.getRibTir());
			output.add(eff.getCodBan());

			output.add(codeAg);
			output.add(eff.getRibBen());

			output.add(formatString(30, eff.getNomBen()));
			output.add(formatString(30, eff.getNomTir()));
			output.add(formatString(30, eff.getRefComTir()));

			output.add("" + eff.getCodAcc());

			output.add("" + eff.getCodEnd());// code endo et aval
			output.add(formaterDate.format(eff.getDatEch()));
			if (eff.getDatEchIni() != null)
				output.add(formaterDate.format(eff.getDatEchIni()));
			else
				output.add(formaterDate.format(eff.getDatEch()));

			output.add(formaterDate.format(eff.getDatCre()));
			output.add(formatString(30, eff.getLieCre()));
			output.add(formatString(30, eff.getRefComBen()));
			output.add((eff.getCodOrd() == null ? "0" : ("" + eff.getCodOrd())));
			output.add((eff.getCodSit() == null ? "0" : ("" + eff.getCodSit())));
			output.add((eff.getCodNatCpt() == null ? " " : ("" + eff.getCodNatCpt())));
			output.add(formaterDate.format(remiseEffetVo.getDateComptable()));
			String motifs = eff.getCodRej1() + eff.getCodRej2() + eff.getCodRej3() + eff.getCodRej4();

			output.add(formatMotif(motifs));
			if (eff.getCodRisBct() != null)
				output.add(StrHandler.lpad(eff.getCodRisBct(), '0', 6));
			else
				output.add(StrHandler.lpad("0", '0', 6));
			output.add(getZoneLibre(29));
			output.add(System.getProperty("line.separator"));
			// complement effet
			HibernateTemplate hibernateTemplate = (HibernateTemplate) this.context.getBean("hibernateTemplate");
			Session sess = hibernateTemplate.getSessionFactory()

			.getCurrentSession();
			List<ComplementEffetRecu> listeComps =
					sess.createCriteria(ComplementEffetRecu.class)
							.add(Restrictions.eq("codAgeDes", eff.getCodAgeDes()))
							.add(Restrictions.eq("effetId.datOpe", eff.getEffetId().getDatOpe()))
							.add(Restrictions.eq("effetId.numEff", eff.getEffetId().getNumEff()))
							.list();
			
				for(int j=0;j<listeComps.size();j++)
				{
					output.add(CodeSens);
					output.add(codeVal);
					output.add(codeNatRem);
					output.add(codeRem);
					output.add(codeAg);
					output.add(formaterDate.format(remiseEffetVo.getDateComptable()));
					output.add(numLot);
					output.add(codEnrg);
					output.add(codeDev);
					output.add(listeComps.get(j).getDonnComplement());
					output.add(getZoneLibre(258));
					output.add(System.getProperty("line.separator"));

				}
			

		}
		// if (output.size() > 0)
		// if (output.get(output.size() - 1).equals(output.add(System.getProperty("line.separator"))))
		// output.remove(output.size() - 1);
		for (int j = 0; j < output.size(); j++) {
			// System.out.println(output.get(j).length() + ":" + output.get(j));
			res += output.get(j);

		}

		return res;
	}

	Long mntGlobalRemiseEsc = 0L;
	Long nbrTotalRemiseEsc = 0L;

	public IValueObject perform(IValueObject vo) {
		Long mntGlobalRemise = 0L;
		Long nbrTotalRemise = 0L;
		Long mntTotalInter = 0L;
		Long nbrTotalInter = 0L;
		Long mntTotalIntra = 0L;
		Long nbrTotalIntra = 0L;
		Long mntGlobalRejet = 0L;
		Long nbrTotalRejet = 0L;
		remiseEffetVo = (RemiseEffetVo) vo;
		CompensationDAO effetDAO = (CompensationDAO) context.getBean("compensationDAO");

		SimpleDateFormat formaterDate = new SimpleDateFormat("yyyyMMdd");

		SimpleDateFormat formaterDateFile = new SimpleDateFormat("ddMMyyyy");
		CompensationDAO dao = new CompensationDAO();
		Structure strc = dao.findStructure(remiseEffetVo.getParamAgence().getCodStrcStrc());
		String structureBct = StrHandler.lpad(strc.getCodBctStrc(), '0', 3);
		SimpleDateFormat formatDateFile = new SimpleDateFormat("ddMMyyyy");
		String jjmmyyyySys = formatDateFile.format(new Date());
		String pathAg = "agence" + structureBct + File.separatorChar + jjmmyyyySys + File.separatorChar + "travail";
		String rootPath =
				File.separatorChar + Configuration.getParentPath() + File.separatorChar
						+ Configuration.getLocalPathCheque() + File.separatorChar + "emis" + File.separatorChar
						+ "effet";
		String succesPath =
				rootPath + File.separatorChar + "agence" + structureBct + File.separatorChar + jjmmyyyySys
						+ File.separatorChar + "travail";
		String remotePathPfc = Configuration.getPfcPathSend();
		String remotePathTresor = Configuration.getTresoreriePathSend();

		try {

			ISearchEngine searchEngine = (SearchEngine) context.getBean("searchEngine");
			ICriteria criteria = searchEngine.createCriteria();
			IExpression expression = searchEngine.createExpression();
			HibernateTemplate hibernateTemplate = (HibernateTemplate) this.context.getBean("hibernateTemplate");
			Session sess = hibernateTemplate.getSessionFactory()

			.getCurrentSession();
			SimpleDateFormat timeFileExtractFormat = new SimpleDateFormat("HHmmss");
			String timeFileExtract =
					formaterDateFile.format(remiseEffetVo.getDateComptable()) + "-"
							+ timeFileExtractFormat.format(new Date()) + "-";
			String res = "";
			List<String> output = new ArrayList<String>();
			String numLot = effetDAO.getNumLotEffet(remiseEffetVo.getParamAgence().getCodStrcStrc());
			String codeVal = "";
			String codeRem = "03";
			String codeAg = "   ";
			String codeDev = "788";
			String codeNatRem = "1";
			String CodeSens = "1";

			/********* LCN Prï¿½sentï¿½ 41 21 *********/

			List<DetailEffet> listeEffet41 =
					sess.createCriteria(DetailEffet.class)
							.add(Restrictions.eq("codAgEm", structureBct))
							.add(Restrictions.eq("effetId.datOpe", remiseEffetVo.getDateComptable()))
							.add(Restrictions.eq("codVal", Constants.COD_TYPE_EFFET_NORMALISE))
							.add(Restrictions.or(Restrictions.isNull("codTrtEff"),
									Restrictions.ne("codTrtEff", Constants.COD_EFFET_MANUEL_VALIDE)))

							.add(Restrictions.eq("codEtat", Constants.COD_ETAT_EFFET_TRANSFERE)).list();

			// System.out.println("******ENVOIS LCN PRESENTES 41*******");
			res = "";
			output = new ArrayList<String>();

			for (int i = 0; i < listeEffet41.size(); i++) {
				mntGlobalRemise += listeEffet41.get(i).getMntEff();
				if (listeEffet41.get(i).getCodBqDes().equals(listeEffet41.get(i).getCodBqEm())) {
					mntTotalIntra += listeEffet41.get(i).getMntEff();
					nbrTotalIntra += 1;
				} else {
					mntTotalInter += listeEffet41.get(i).getMntEff();
					nbrTotalInter += 1;
				}
			}
			nbrTotalRemise += listeEffet41.size();
			String numLotRemise = StrHandler.lpad("" + (Long.valueOf(numLot)), '0', 4);
			// /ajouter liste escompte 6 requete mohamed kachout EFfetDAO return list<DetailEffet>

			List<DetailEffet> listeEsc =
					effetDAO.getEffetRemiseEsc(remiseEffetVo.getParamAgence().getDateComptable(), remiseEffetVo.getParamAgence().getCodStrcStrc());
			mntGlobalRemiseEsc = 0L;
			nbrTotalRemiseEsc = Long.valueOf(listeEsc.size());
			for (int i = 0; i < listeEsc.size(); i++) {
				mntGlobalRemiseEsc += listeEsc.get(i).getMntEff();

			}
			listeEffet41.addAll(listeEsc);

			res =
					createEffet(strc.getCodStrcStrc(), listeEffet41, numLotRemise, ""
							+ Constants.COD_TYPE_EFFET_NORMALISE, "21", remiseEffetVo.getDateComptable());

			String envoisLcn41 = "03-" + structureBct + "-41-21-" + numLotRemise + "-" + timeFileExtract + "788.ENV";

			
				File file = new File(rootPath + File.separatorChar + pathAg + File.separatorChar + envoisLcn41);
				System.out.println(file.getAbsolutePath());
				if (!file.exists())
					file.createNewFile();
				FileUtils.writeStringToFile(file, res);
				logger.error("fichier SRC Travail:" + file.getAbsolutePath());
				logger.error("fichier SRC traite:" + succesPath + File.separatorChar + envoisLcn41);
				logger.error("fichier DEST:" + remotePathPfc + envoisLcn41);
				logger.info("fichier SRC Travail:" + file.getAbsolutePath());
				logger.info("fichier SRC traite:" + succesPath + File.separatorChar + envoisLcn41);
				logger.info("fichier DEST:" + remotePathPfc + envoisLcn41);
				Util.copy(succesPath + File.separatorChar + envoisLcn41, remotePathPfc + envoisLcn41);
				

			mntTotalInter = 0L;
			nbrTotalInter = 0L;
			mntTotalIntra = 0L;
			nbrTotalIntra = 0L;
			List<EffetRecu> listeEffetRejetes41 =
					sess.createCriteria(EffetRecu.class)
							.add(Restrictions.eq("codAgeDes", structureBct))
							.add(Restrictions.eq("effetId.datOpe", remiseEffetVo.getDateComptable()))
							// .add(Restrictions.like("ribTir", "03%"))
							.add(Restrictions.eq("codEnr", 21L))
							.add(Restrictions.eq("codVal", Constants.COD_TYPE_EFFET_NORMALISE))
							.add(Restrictions.or(Restrictions.isNull("codTrtEff"),
									Restrictions.ne("codTrtEff", Constants.COD_EFFET_MANUEL_VALIDE)))
							.add(Restrictions.eq("codEtatEff", Constants.COD_ETAT_EFFET_REJETE)).list();

			// System.out.println("******ENVOIS LCN REJTES*******");
			res = "";
			for (int i = 0; i < listeEffetRejetes41.size(); i++) {
				mntGlobalRejet += listeEffetRejetes41.get(i).getMntEff();
				if (listeEffetRejetes41.get(i).getCodBan().equals(listeEffetRejetes41.get(i).getCodBanDes())) {
					mntTotalIntra += listeEffetRejetes41.get(i).getMntEff();
					nbrTotalIntra += 1;
				} else {
					mntTotalInter += listeEffetRejetes41.get(i).getMntEff();
					nbrTotalInter += 1;
				}
			}
			nbrTotalRejet += listeEffetRejetes41.size();
			res =
					createEffetRecu(strc.getCodStrcStrc(), listeEffetRejetes41, numLot, ""
							+ Constants.COD_TYPE_EFFET_NORMALISE, "22");

			String envoisRejets41 = "03-" + structureBct + "-41-22-" + numLot + "-" + timeFileExtract + "788.ENV";

			
				 file = new File(rootPath + File.separatorChar + pathAg + File.separatorChar + envoisRejets41);
				if (!file.exists())
					file.createNewFile();
				FileUtils.writeStringToFile(file, res);
				Util.copy(succesPath + File.separatorChar + envoisRejets41, remotePathTresor + envoisRejets41);
				

			/********* LCN REJETES 40 22 *********/
			Long mntTotalInterLCR = 0L;
			Long nbrTotalInterLCR = 0L;
			Long mntTotalIntraLCR = 0L;
			Long nbrTotalIntraLCR = 0L;
			Long mntGlobalRejetLCR = 0L;
			Long nbrTotalRejetLCR = 0L;
			List<EffetRecu> listeEffetRejetes40 =
					sess.createCriteria(EffetRecu.class)
							.add(Restrictions.eq("codAgeDes", structureBct))
							.add(Restrictions.eq("effetId.datOpe", remiseEffetVo.getDateComptable()))
							// .add(Restrictions.like("ribTir", "03%"))
							.add(Restrictions.eq("codEnr", 21L))
							.add(Restrictions.or(Restrictions.isNull("codTrtEff"),
									Restrictions.ne("codTrtEff", Constants.COD_EFFET_MANUEL_VALIDE)))
							.add(Restrictions.eq("codVal", Constants.COD_TYPE_EFFET_NON_NORMALISE))
							.add(Restrictions.eq("codEtatEff", Constants.COD_ETAT_EFFET_REJETE)).list();

			// System.out.println("******ENVOIS LCN REJTES*******");
			res = "";
			for (int i = 0; i < listeEffetRejetes40.size(); i++) {
				mntGlobalRejetLCR += listeEffetRejetes40.get(i).getMntEff();
				if (listeEffetRejetes40.get(i).getCodBan().equals(listeEffetRejetes40.get(i).getCodBanDes())) {
					mntTotalIntraLCR += listeEffetRejetes40.get(i).getMntEff();
					nbrTotalIntraLCR += 1;
				} else {
					mntTotalInterLCR += listeEffetRejetes40.get(i).getMntEff();
					nbrTotalInterLCR += 1;
				}
			}
			nbrTotalRejetLCR += listeEffetRejetes40.size();
			res =
					createEffetRecu(strc.getCodStrcStrc(), listeEffetRejetes40, numLot, ""
							+ Constants.COD_TYPE_EFFET_NON_NORMALISE, "22");
			// System.out.println("*************");
			// System.out.println("REJET LCN " + res.length() + ": " + res);

			String envoisRejets40 = "03-" + structureBct + "-40-22-" + numLot + "-" + timeFileExtract + "788.ENV";

		
				 file = new File(rootPath + File.separatorChar + pathAg + File.separatorChar + envoisRejets40);
				if (!file.exists())
					file.createNewFile();
				FileUtils.writeStringToFile(file, res);
				Util.copy(succesPath + File.separatorChar + envoisRejets40, remotePathTresor + envoisRejets40);
				
			

		} catch (Exception e) {
			e.printStackTrace();
			com.oxia.fwk.core.Error erreur = new com.oxia.fwk.core.Error();
			StringBuffer text = new StringBuffer("Erreur dans EnvoisLCNFichierTrt : ");
			text.append(e.toString());
			erreur.setCode("100");
			erreur.setDescription(text.toString());
			erreur.setKey("EnvoisLCNFichierTrt");
			remiseEffetVo.addError(erreur);
			logger.error("Erreur au niveau EnvoisLCNFichierTrt : ", e);
			throw new RuntimeException(e);

		}
		return remiseEffetVo;
	}

	public void genCroText(ValueObject vo) {

	}

}