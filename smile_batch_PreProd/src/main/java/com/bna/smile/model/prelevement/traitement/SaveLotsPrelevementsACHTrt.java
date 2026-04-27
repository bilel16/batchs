package com.bna.smile.model.prelevement.traitement;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
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
import com.bna.smile.model.constant.Constants;
import com.bna.smile.model.domainecommun.service.CRUDservice;
import com.bna.smile.model.domaineplacement.service.BatchService;
import com.bna.smile.model.prelevement.dao.PrelevementDAO;
import com.bna.smile.model.prelevement.model.ADDetailPrelevementVo;
import com.bna.smile.model.prelevement.model.PrelevementVo;
import com.oxia.fwk.context.Context;
import com.oxia.fwk.core.ICriteria;
import com.oxia.fwk.core.IExpression;
import com.oxia.fwk.core.ISearchEngine;
import com.oxia.fwk.core.IValueObject;
import com.oxia.fwk.core.ValueObject;
import com.oxia.fwk.searchengine.SearchEngine;

/**
 * @author 5556
 * @since 11/03/2026 Refonte SNT - ACH
 **/
public class SaveLotsPrelevementsACHTrt extends Traitement {

	Context context = ContextHandler.getContext();

	CRUDservice crudService = (CRUDservice) context.getBean("crudservice");

	ISearchEngine searchEngine = (SearchEngine) context.getBean("searchEngine");
	ICriteria criteria = searchEngine.createCriteria();
	IExpression expression = searchEngine.createExpression();

	PrelevementDAO prelevementDAO = (PrelevementDAO) context.getBean("prelevementDAO");

	long mntFichier = 0;
	long mntTotalIntra = 0;
	long mntTotalInter = 0;

	long nbrTotalInter = 0;
	long nbrTotalIntra = 0;
	long nbreTotalFichier = 0;


	public SaveLotsPrelevementsACHTrt() {
	}

	public IValueObject perform(IValueObject vo) {

		this.setSecurityFlag(false);
		this.setVerifDomaine(false);
		this.setCroFlag(false);

		PrelevementVo prelevementVo = new PrelevementVo();

		Structure structureException = new Structure();

		try {
			prelevementVo = (PrelevementVo) vo;

			structureException.setCodStrcStrc(prelevementVo.getCodeStructure());

			boolean etatSaveFichier = importFromDataBase(prelevementVo.getDateComptable(),
					prelevementVo.getCodeStructureBCT(), prelevementVo.getCodeStructure(),
					"" + Constants.COD_ENREGISTREMENT_PRELEVEMENT);

			prelevementVo.setEtatEnregistrementPrelevement(etatSaveFichier);

		} catch (Exception e) {
			com.oxia.fwk.core.Error erreur = new com.oxia.fwk.core.Error();
			StringBuffer text = new StringBuffer("Erreur dans SaveLotsPrelevementsACHTrt : ");
			text.append(e.toString());
			erreur.setCode("100");
			erreur.setDescription(text.toString());
			erreur.setKey("SaveLotsPrelevementsACHTrt");
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
		batchExeptionPlac.setLibTpbmBate("Exception Batch Prelevement ACH");
		batchExeptionPlac.setLibExpBate(e.getMessage());

		BatchService batchService = (BatchService) context.getBean("batchService");

		batchExeptionPlac = (BatchExeptionPlac) batchService.InsertBatchExeptionPlac(batchExeptionPlac);
	}

	public boolean importFromDataBase(Date dateComptable, Long codBct, Long codStrc, String codEnregistrement) {

		try {

			List<ADDetailPrelevementVo> listPrelevementsACH = new ArrayList<ADDetailPrelevementVo>();

			listPrelevementsACH = prelevementDAO.getListPrelevementACHAgence(dateComptable, codBct);
			long numberLine = listPrelevementsACH.size();

			for (ADDetailPrelevementVo detailPrelevementVo : listPrelevementsACH) {
				createDetailsPrelevement(detailPrelevementVo);
			}

			logger.info("Nombre de ligne enregistree  :" + numberLine);
			return true;

		} catch (Exception e) {

			logger.error(e.getMessage());
			return false;
		}
	}

	public DetailsPrelevements createDetailsPrelevement(ADDetailPrelevementVo detailPrelevementVo) {
		DetailsPrelevements detailsPrelevements = new DetailsPrelevements();
		DetailsPrelevementsId detailsPrelevementsId = new DetailsPrelevementsId();

		try {

			detailsPrelevementsId.setMntPrlPrl(detailPrelevementVo.getMntPrl());
			detailsPrelevementsId.setNumPrlPrl(detailPrelevementVo.getNumPrl());

			detailsPrelevementsId.setDatOpePrl(detailPrelevementVo.getDatOpe());
			detailsPrelevementsId.setNumLotPrl(detailPrelevementVo.getNumLot());

			detailsPrelevementsId.setRibTirPrl(lpadS( detailPrelevementVo.getRibTir(),"0",20));
			detailsPrelevementsId.setRibBenPrl(lpadS(detailPrelevementVo.getRibBen(),"0",20));

			detailsPrelevements.setDetailsPrelevementsId(detailsPrelevementsId);

			detailsPrelevements.setCodSenPrl(detailPrelevementVo.getCodSen());
			detailsPrelevements.setCodValPrl(detailPrelevementVo.getCodVal());
			detailsPrelevements.setRefFicPrl(detailPrelevementVo.getRefFic());

			detailsPrelevements.setCodBanPrl(detailPrelevementVo.getCodBan());
			detailsPrelevements.setCodAgePrl(lpadS( detailPrelevementVo.getRibTir(),"0",20).substring(2, 5));

			detailsPrelevements.setCodEnrPrl(detailPrelevementVo.getCodEnr());
			detailsPrelevements.setCodDevPrl(detailPrelevementVo.getCodDev());

			detailsPrelevements.setCodBanDes(Long.valueOf(detailPrelevementVo.getCodBanDes()));
			detailsPrelevements.setCodAgeDes(Long.valueOf(detailPrelevementVo.getCodAgeDes()));

			Emetteur emetteur = new Emetteur(detailPrelevementVo.getCodEmePrl());
			detailsPrelevements.setEmetteur(emetteur);

			detailsPrelevements.setNumRefDom(detailPrelevementVo.getNumRefDom());
			detailsPrelevements.setLibPrlPrl(detailPrelevementVo.getLibPrl());

			MotifRejetPrelev motifRejetPrelev = new MotifRejetPrelev();
			if (detailPrelevementVo.getMotRej()!=null && detailPrelevementVo.getMotRej().length()!=0) {
				motifRejetPrelev.setCodMotrMrpr(Long.valueOf(detailPrelevementVo.getMotRej()));
				detailsPrelevements.setMotifRejetPrelev(motifRejetPrelev);
			} else {
				detailsPrelevements.setMotifRejetPrelev(null);
			}

			detailsPrelevements.setDatEchPrl(detailPrelevementVo.getDatEch());

			detailsPrelevements.setCodEtatPrl(Constants.COD_ETAT_PRELEVEMENT_ATTENTE);
			detailsPrelevements.setCodNatEta(Long.valueOf(1));

			if (rechercherDetailsPrelevements(detailsPrelevements) == null) {

				crudService.create(detailsPrelevements);

				if (detailsPrelevements.getCodBanPrl() != null) {
					Long codBanquePrl = 0L;
					codBanquePrl = Long.valueOf(detailsPrelevements.getCodBanPrl());

					if (codBanquePrl.equals(Long.valueOf("3"))) {

						nbrTotalIntra = nbrTotalIntra + 1;
						mntTotalIntra = mntTotalIntra + detailsPrelevements.getDetailsPrelevementsId().getMntPrlPrl();

					} else {

						nbrTotalInter = nbrTotalInter + 1;
						mntTotalInter = mntTotalInter + detailsPrelevements.getDetailsPrelevementsId().getMntPrlPrl();
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

			criteriaPrl.add(expressionPrl.eq("detailsPrelevementsId.datOpePrl",
					detailsPrelevements.getDetailsPrelevementsId().getDatOpePrl()));
			criteriaPrl.add(expressionPrl.eq("detailsPrelevementsId.mntPrlPrl",
					detailsPrelevements.getDetailsPrelevementsId().getMntPrlPrl()));
			criteriaPrl.add(expressionPrl.eq("detailsPrelevementsId.numPrlPrl",
					detailsPrelevements.getDetailsPrelevementsId().getNumPrlPrl()));
			criteriaPrl.add(expressionPrl.eq("detailsPrelevementsId.ribTirPrl",
					detailsPrelevements.getDetailsPrelevementsId().getRibTirPrl()));
			criteriaPrl.add(expressionPrl.eq("detailsPrelevementsId.ribBenPrl",
					detailsPrelevements.getDetailsPrelevementsId().getRibBenPrl()));
			criteriaPrl
					.add(expressionPrl.eq("emetteur.codEmtrEmtr", detailsPrelevements.getEmetteur().getCodEmtrEmtr()));
			criteriaPrl.add(expressionPrl.eq("codBanDes", detailsPrelevements.getCodBanDes()));
			criteriaPrl.add(expressionPrl.eq("codAgeDes", detailsPrelevements.getCodAgeDes()));
			criteriaPrl.add(expressionPrl.eq("datEchPrl", detailsPrelevements.getDatEchPrl()));

			Set<DetailsPrelevements> liste_DetailsPrelevements = new HashSet<DetailsPrelevements>(
					searchEngine.find(DetailsPrelevements.class, criteriaPrl));

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
	
	public static String lpadS(String valueToPad, String filler, int size) {
		StringBuilder builder = new StringBuilder();

		while (builder.length() + valueToPad.length() < size) {
			builder.append(filler);
		}
		builder.append(valueToPad);
		return builder.toString();
	}

	public static String rpadS(String valueToPad, String filler, int size) {
		StringBuilder builder = new StringBuilder();
		builder.append(valueToPad);

		while ((builder.length() + filler.length()) <= size) {
			builder.append(filler);
		}

		return builder.toString();
	}
}
