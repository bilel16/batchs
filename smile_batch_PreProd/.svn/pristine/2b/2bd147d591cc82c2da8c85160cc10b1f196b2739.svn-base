package com.bna.smile.model.prelevement.traitement;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import org.springframework.dao.DataIntegrityViolationException;

import com.bna.commun.model.BatchExeptionPlac;
import com.bna.commun.model.DetailsPrelevements;
import com.bna.commun.model.DetailsPrelevementsId;
import com.bna.commun.model.Emetteur;
import com.bna.commun.model.MotifRejetPrelev;
import com.bna.commun.model.Structure;
import com.bna.commun.traitements.Traitement;
import com.bna.commun.util.ContextHandler;
import com.bna.commun.util.StrHandler;
import com.bna.smile.model.constant.Constants;
import com.bna.smile.model.domainecommun.service.CRUDservice;
import com.bna.smile.model.domainecompensation.gestionrejet.traitement.SuivFileTrt;
import com.bna.smile.model.domainecompensation.gestionrejet.traitement.Util;
import com.bna.smile.model.domaineplacement.service.BatchService;
import com.bna.smile.model.prelevement.dao.PrelevementDAO;
import com.bna.smile.model.prelevement.model.PrelevementVo;
import com.oxia.fwk.context.Context;
import com.oxia.fwk.core.ICriteria;
import com.oxia.fwk.core.IExpression;
import com.oxia.fwk.core.ISearchEngine;
import com.oxia.fwk.core.IValueObject;
import com.oxia.fwk.core.ValueObject;
import com.oxia.fwk.searchengine.SearchEngine;

public class SaveLotsPrelevementsTrt extends Traitement {

	public SaveLotsPrelevementsTrt() {
	}

	Context context = ContextHandler.getContext();
	ISearchEngine searchEngine = (SearchEngine) context.getBean("searchEngine");
	CRUDservice crudService = (CRUDservice) context.getBean("crudservice");
	PrelevementDAO prelevementDAO = (PrelevementDAO) context.getBean("prelevementDAO");
	private SimpleDateFormat formaterDate = new SimpleDateFormat("dd/MM/yyyy");
	private SimpleDateFormat formaterDate2 = new SimpleDateFormat("yyyyMMdd");
	private SimpleDateFormat formaterDate3 = new SimpleDateFormat("ddMMyyyy");
	// à ne pas laisser en variable global
	ICriteria criteria = searchEngine.createCriteria();
	IExpression expression = searchEngine.createExpression();
	long mntFichier = 0;
	long nbreTotalFichier = 0;
	long nbrTotalInter = 0;
	long nbrTotalIntra = 0;
	long mntTotalIntra = 0;
	long mntTotalInter = 0;

	public IValueObject perform(IValueObject vo) {

		this.setSecurityFlag(false);
		this.setVerifDomaine(false);
		this.setCroFlag(false);

		PrelevementVo prelevementVo = new PrelevementVo();
		Structure structureException = new Structure();
		try {
			structureException.setCodStrcStrc(prelevementVo.getCodeStructure());
			prelevementVo = (PrelevementVo) vo;

			String wmmjj = null;
			String ageBct = null;
			ageBct = StrHandler.lpad(prelevementVo.getCodeStructureBCT() + "", '0', 3);
			wmmjj = formaterDate3.format(prelevementVo.getDateComptable());

			String pathTravail = prelevementVo.getPathFichier();

			String srcFilePrl =
					pathTravail + "03-" + ageBct + "-" + Constants.COD_ENREGISTREMENT_PRELEVEMENT + "-"
							+ Constants.COD_ENREGISTREMENT_DETAIL_PRESENTATION + "-" + wmmjj + "-788.RCP";

			File srcFile = new File(srcFilePrl);

			boolean etatSaveFichier = importFromFile(srcFile, "" + Constants.COD_ENREGISTREMENT_PRELEVEMENT);

			Long valeurEnrFichier = Long.valueOf(0);
			if (etatSaveFichier == true) {
				valeurEnrFichier = Long.valueOf(1);

				SuivFileTrt.ajouterFichierAvecMontant("03-" + ageBct + "-" + Constants.COD_ENREGISTREMENT_PRELEVEMENT
						+ "-" + Constants.COD_ENREGISTREMENT_DETAIL_PRESENTATION + "-" + wmmjj + "-788.RCP", ageBct,
						prelevementVo.getDateComptable(), valeurEnrFichier.intValue(),
						Constants.COD_ENREGISTREMENT_PRELEVEMENT, mntFichier, nbreTotalFichier, mntTotalInter,
						nbrTotalInter, nbrTotalIntra, mntTotalIntra);

				// ********* Copier fichier dans traite ************//
				try {
					Util.copy(srcFilePrl, prelevementVo.getPathFichierTraite() + srcFile.getName());

				} catch (Exception e) {
					logger.error(e.getMessage());
				}
			}

			prelevementVo.setEtatEnregistrementPrelevement(etatSaveFichier);

		} catch (Exception e) {
			com.oxia.fwk.core.Error erreur = new com.oxia.fwk.core.Error();
			StringBuffer text = new StringBuffer("Erreur dans SaveLotsPrelevementsTrt : ");
			text.append(e.toString());
			erreur.setCode("100");
			erreur.setDescription(text.toString());
			erreur.setKey("SaveLotsPrelevementsTrt");
			logger.error("Exception : ", e);
			gestionException(prelevementVo.getDateComptable(), structureException, e);
			prelevementVo.setErreur(e.getMessage());
			prelevementVo.setEtatEnregistrementPrelevement(false);
			throw new RuntimeException(e);

		}
		return prelevementVo;
	}

	public void genCroText(ValueObject vo) {

	}

	public String getNumeroTache(ValueObject vo) {
		return (Constants.CODE_RESSOURCE_GENERALE);
	}

	private void gestionException(Date dateComptable, Structure agence, Exception e) {

		BatchExeptionPlac batchExeptionPlac = new BatchExeptionPlac();
		batchExeptionPlac.setDatSystBate(new Date());
		batchExeptionPlac.setDatCompBate(dateComptable);
		batchExeptionPlac.setStructure(agence);
		batchExeptionPlac.setLibTpbmBate("Exception Batch Prelevement");
		batchExeptionPlac.setLibExpBate(e.getMessage());
		BatchService batchService = (BatchService) context.getBean("batchService");
		batchExeptionPlac = (BatchExeptionPlac) batchService.InsertBatchExeptionPlac(batchExeptionPlac);
	}

	public boolean importFromFile(File fichier, String codEnregistrement) {

		// Begin Import
		try {
			BufferedWriter bufWriter = null;
			FileWriter fileWriter = null;
			InputStream ips = new FileInputStream(fichier);
			InputStreamReader ipsr = new InputStreamReader(ips);
			BufferedReader br = new BufferedReader(ipsr);
			String line;
			long numberLine = 0;

			while ((line = br.readLine()) != null && line.length() > 30) {

				if (codEnregistrement.equals("" + Constants.COD_ENREGISTREMENT_PRELEVEMENT)
						&& line.substring(21, 23).equals("" + Constants.COD_ENREGISTREMENT_GLOBAL_PRESENTATION)) {

					mntFichier = Long.valueOf(line.substring(26, 41));
					nbreTotalFichier = Long.valueOf(line.substring(41, 51));
				}

				if (codEnregistrement.equals("" + Constants.COD_ENREGISTREMENT_PRELEVEMENT)
						&& line.substring(21, 23).equals("" + Constants.COD_ENREGISTREMENT_DETAIL_PRESENTATION)) {

					createDetailsPrelevement(line, fichier.getName());
					numberLine++;
				}
			}
			br.close();

			logger.info("Nombre de ligne enregistree  :" + numberLine);
			return true;

		} catch (FileNotFoundException e) {

			logger.error("Le Fichier du prelevement " + fichier.getName() + " n'existe pas dans le repertoire ");
			return false;
		} catch (Exception e) {

			logger.error(e.getMessage());
			return false;
		}
	}

	public DetailsPrelevements createDetailsPrelevement(String line, String nomFichier) throws ParseException {

		DetailsPrelevements detailsPrelevements = new DetailsPrelevements();
		DetailsPrelevementsId detailsPrelevementsId = new DetailsPrelevementsId();

		try {
			logger.info("Traitement of line : " + line);

			detailsPrelevements.setCodSenPrl(new Long(line.substring(0, 1)));

			detailsPrelevements.setCodValPrl(new Long(line.substring(1, 3)));
			detailsPrelevements.setRefFicPrl(nomFichier);
			detailsPrelevements.setCodBanPrl(line.substring(4, 6));

			detailsPrelevements.setCodAgePrl(line.substring(6, 9));

			String strDatOp = line.substring(9, 17);
			detailsPrelevementsId.setDatOpePrl(formaterDate2.parse(strDatOp));
			detailsPrelevementsId.setNumLotPrl(new Long(line.substring(17, 21)));

			detailsPrelevements.setCodEnrPrl(new Long(line.substring(21, 23)));
			detailsPrelevements.setCodDevPrl(line.substring(23, 26));
			detailsPrelevementsId.setMntPrlPrl(Long.valueOf(line.substring(26, 41)));
			detailsPrelevementsId.setNumPrlPrl(new Long(line.substring(41, 48)));
			detailsPrelevementsId.setRibTirPrl(line.substring(48, 68));

			detailsPrelevements.setCodBanDes(Long.valueOf(line.substring(68, 70)));
			detailsPrelevements.setCodAgeDes(Long.valueOf(line.substring(48, 68).substring(2, 5)));
			detailsPrelevementsId.setRibBenPrl(line.substring(73, 93));

			Emetteur emetteur = new Emetteur(Long.valueOf(line.substring(93, 99)));
			detailsPrelevements.setEmetteur(emetteur);
			detailsPrelevements.setNumRefDom(line.substring(99, 119));
			detailsPrelevements.setLibPrlPrl(line.substring(119, 169));
			MotifRejetPrelev motifRejetPrelev = new MotifRejetPrelev();
			if (Long.valueOf(line.substring(177, 185)).longValue() != 0) {
				motifRejetPrelev.setCodMotrMrpr(Long.valueOf(line.substring(177, 185)));
				detailsPrelevements.setMotifRejetPrelev(motifRejetPrelev);
			} else {
				detailsPrelevements.setMotifRejetPrelev(null);
			}

			String strDatEch = line.substring(185, 193);
			try {
				detailsPrelevements.setDatEchPrl(formaterDate2.parse(strDatEch));
			} catch (Exception e) {
				detailsPrelevements.setDatEchPrl(formaterDate.parse(strDatEch));
			}
			detailsPrelevements.setDetailsPrelevementsId(detailsPrelevementsId);
			detailsPrelevements.setCodEtatPrl(Constants.COD_ETAT_PRELEVEMENT_ATTENTE);
			detailsPrelevements.setCodNatEta(Long.valueOf(1));
			if (rechercherDetailsPrelevements(detailsPrelevements) == null) {

				crudService.create(detailsPrelevements);

				if (detailsPrelevements.getCodBanPrl() != null) {
					Long codBanquePrl = 0L;
					try {
						codBanquePrl = Long.valueOf(detailsPrelevements.getCodBanPrl());

						if (codBanquePrl.equals(Long.valueOf("3"))) {

							nbrTotalIntra = nbrTotalIntra + 1;
							mntTotalIntra =
									mntTotalIntra + detailsPrelevements.getDetailsPrelevementsId().getMntPrlPrl();

						} else {

							nbrTotalInter = nbrTotalInter + 1;
							mntTotalInter =
									mntTotalInter + detailsPrelevements.getDetailsPrelevementsId().getMntPrlPrl();

						}

					} catch (NumberFormatException e) {
						// TODO: handle exception
					}

				}

			} else {
				logger.info("Un même detailsPrelevements existe dans la base ");
			}
		} catch (DataIntegrityViolationException e) {
			e.printStackTrace();
			logger.error("DataIntegrityViolationException : " + e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
		}

		return detailsPrelevements;

	}

	public DetailsPrelevements rechercherDetailsPrelevements(DetailsPrelevements detailsPrelevements) {

		try {

			ICriteria criteriaPrl = searchEngine.createCriteria();
			IExpression expressionPrl = searchEngine.createExpression();

			criteriaPrl.add(expressionPrl.eq("detailsPrelevementsId.datOpePrl", detailsPrelevements
					.getDetailsPrelevementsId().getDatOpePrl()));
			criteriaPrl.add(expressionPrl.eq("detailsPrelevementsId.mntPrlPrl", detailsPrelevements
					.getDetailsPrelevementsId().getMntPrlPrl()));
			criteriaPrl.add(expressionPrl.eq("detailsPrelevementsId.numPrlPrl", detailsPrelevements
					.getDetailsPrelevementsId().getNumPrlPrl()));
			criteriaPrl.add(expressionPrl.eq("detailsPrelevementsId.ribTirPrl", detailsPrelevements
					.getDetailsPrelevementsId().getRibTirPrl()));
			criteriaPrl.add(expressionPrl.eq("detailsPrelevementsId.ribBenPrl", detailsPrelevements
					.getDetailsPrelevementsId().getRibBenPrl()));
			criteriaPrl.add(expressionPrl
					.eq("emetteur.codEmtrEmtr", detailsPrelevements.getEmetteur().getCodEmtrEmtr()));
			criteriaPrl.add(expressionPrl.eq("codBanDes", detailsPrelevements.getCodBanDes()));
			criteriaPrl.add(expressionPrl.eq("codAgeDes", detailsPrelevements.getCodAgeDes()));
			criteriaPrl.add(expressionPrl.eq("datEchPrl", detailsPrelevements.getDatEchPrl()));

			Set<DetailsPrelevements> liste_DetailsPrelevements =
					new HashSet<DetailsPrelevements>(searchEngine.find(DetailsPrelevements.class, criteriaPrl));

			if (liste_DetailsPrelevements != null && liste_DetailsPrelevements.size() > 0) {
				return liste_DetailsPrelevements.iterator().next();

			} else {
				return null;
			}

		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
			return null;
		}

	}
}
