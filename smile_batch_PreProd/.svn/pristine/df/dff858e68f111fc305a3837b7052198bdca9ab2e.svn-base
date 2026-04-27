package com.bna.smile.model.domaineguichet.traitement;

import java.io.BufferedWriter;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.collections.map.ListOrderedMap;

import com.bna.commun.model.BatchRejetVirNSI;
import com.bna.commun.model.ContratCpt;
import com.bna.commun.model.ContratCptId;
import com.bna.commun.model.DepPersonnel;
import com.bna.commun.model.Devise;
import com.bna.commun.model.OperationMoyPay;
import com.bna.commun.model.Personnel;
import com.bna.commun.model.Structure;
import com.bna.commun.model.Tache;
import com.bna.commun.model.TacheId;
import com.bna.commun.model.TypePiece;
import com.bna.commun.service.CURService;
import com.bna.commun.traitements.Traitement;
import com.bna.commun.util.ContextHandler;
import com.bna.commun.util.DateHandler;
import com.bna.commun.util.StrHandler;
import com.bna.smile.model.constant.Constants;
import com.bna.smile.model.domainecommun.commande.GetContratCptByIdCmd;
import com.bna.smile.model.domainecommun.commande.GetContratEtatCmd;
import com.bna.smile.model.domainecommun.model.ContratCptMandat;
import com.bna.smile.model.domainecommun.service.CRUDservice;
import com.bna.smile.model.domaineguichet.dao.GuichetDAO;
import com.bna.smile.model.domaineguichet.model.AgencesMAJNSIVo;
import com.bna.smile.model.domaineguichet.model.Const;
import com.bna.smile.model.domaineguichet.model.MAJNSIVo;
import com.bna.smile.model.domaineguichet.service.GuichetService;
import com.bna.smile.web.commun.controller.UtilCtr;
import com.oxia.fwk.context.Context;
import com.oxia.fwk.core.ICriteria;
import com.oxia.fwk.core.IExpression;
import com.oxia.fwk.core.ISearchEngine;
import com.oxia.fwk.core.IValueObject;
import com.oxia.fwk.core.ValueObject;
import com.oxia.fwk.searchengine.SearchEngine;

public class MAJAutTresLigneTrt extends Traitement {

	public MAJAutTresLigneTrt() {
	}

	Context context = ContextHandler.getContext();
	ISearchEngine searchEngine = (SearchEngine) context.getBean("searchEngine");

	// à ne pas laisser en variable global
	ICriteria criteria = searchEngine.createCriteria();
	ICriteria criteriaAvanc = searchEngine.createCriteria();
	IExpression expression = searchEngine.createExpression();
	CRUDservice crudService = (CRUDservice) context.getBean("crudservice");
	GuichetDAO guichetDao;
	BufferedWriter bufWriter;
	SimpleDateFormat formatDate = new SimpleDateFormat("dd/mm/yyyy");

	public IValueObject perform(IValueObject vo) {
		MAJNSIVo mAJNSIVo = (MAJNSIVo) vo;
		String ligne = "";

		Structure strc = new Structure();
		strc.setCodStrcStrc(949L);
		try {
			bufWriter = mAJNSIVo.getBufWriter();
			this.setVerifDomaine(false);
			this.setCroFlag(false);
			ligne = mAJNSIVo.getLigne();
			Long mnt = 0L;
			// String month="06";
			// String year="2015";

			// month=ligne.trim().substring(30, 32);
			// year=ligne.trim().substring(32, 34);
			String dd = "29/06/2016";
			guichetDao = (GuichetDAO) context.getBean("guichetDAO");
			if (ligne.length() > 3) {
				String ind = ligne.trim().substring(66, 68);
				if (ind.equals("03")) {
					ContratCptId contratCptId = new ContratCptId();
					contratCptId.setCodStrcStrc(new Long(ligne.trim()
							.substring(71, 74)));
					strc.setCodStrcStrc(contratCptId.getCodStrcStrc());

					contratCptId.setCodPrdPrd(new Long(ligne.trim().substring(
							74, 78)));
					contratCptId.setNumCcptCcpt(new Long(ligne.trim()
							.substring(78, 84)));
					mnt = Long.valueOf(ligne.trim().substring(96, 103));
					mnt = new Double(mnt).longValue();

					if (mnt < 1500000L) {
						ContratCpt contratCptU = (ContratCpt) searchEngine
								.loadForUpdate(ContratCpt.class, contratCptId);
						if (contratCptU != null) {
							contratCptU.setDatEautCcpt(formatDate.parse(dd));
							contratCptU.setMontAutCcpt(mnt);
							crudService.update(contratCptU);
							bufWriter.write(ligne.trim().substring(66));
							bufWriter.newLine();
						} else {
							bufWriter.write("Compte inexistant "
									+ ligne.trim().substring(66));
							bufWriter.newLine();
						}
					}
				}
			}

		} catch (Exception e) {
			com.oxia.fwk.core.Error erreur = new com.oxia.fwk.core.Error();
			StringBuffer text = new StringBuffer(
					"Erreur dans MAJNSIAut-perf : ");
			text.append(e.getMessage());
			erreur.setCode("100");
			erreur.setDescription(text.toString());
			erreur.setKey("MAJNSIAutLigneTrt-perf");
			logger.error("Exception : ", e);
			gestionException(new Date(), strc, e, ligne);
			mAJNSIVo.addError(erreur);
			// /*** gerer une exception
			throw new RuntimeException(e);
		}
		return mAJNSIVo;
	}

	private void gestionException(Date dateOper, Structure agence, Exception e,
			String donnee) {

		try {
			bufWriter.write(agence.getCodStrcStrc() + " "
					+ DateHandler.dateToStr(dateOper) + " " + e.toString()
					+ " ** " + donnee);
			bufWriter.newLine();
		} catch (IOException e1) {
			com.oxia.fwk.core.Error erreur = new com.oxia.fwk.core.Error();
			StringBuffer text = new StringBuffer("Erreur dans MAJNSI-perf : ");
			text.append(e1.getMessage());
			erreur.setCode("100");
			erreur.setDescription(text.toString());
			erreur.setKey("MAJNSIAut-perf_fileLogExcep");
			logger.error("Exception : ", e1);
		}

	}

	public void genCroText(ValueObject vo) {

	}

	public String getNumeroTache(ValueObject vo) {
		return (Constants.CODE_RESSOURCE_GENERALE);
	}

	public Date getDateEch(String month, String year) throws ParseException {
		SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
		Date convertedDate = dateFormat.parse(month + "/01/" + year);
		Calendar c = Calendar.getInstance();
		c.setTime(convertedDate);
		c.add(Calendar.MONTH, 1);
		c.set(Calendar.DAY_OF_MONTH, c.getActualMaximum(Calendar.DAY_OF_MONTH));
		return c.getTime();
	}

	public static int getQuantieme(Date date) {
		GregorianCalendar gc = new GregorianCalendar();
		gc.setTime(date);
		return gc.get(GregorianCalendar.DAY_OF_YEAR);
	}
}
