package com.bna.smile.model.traitementCompensationRecu.traitement;

import java.util.List;

import com.bna.commun.model.Cheque;
import com.bna.commun.model.Cheque30;
import com.bna.commun.model.Cheque31;
import com.bna.commun.model.Cheque32;
import com.bna.commun.model.Cheque33;

import com.bna.commun.traitements.Traitement;
import com.bna.commun.util.ContextHandler;
import com.bna.smile.model.traitementCompensationRecu.model.CompensationRecuVo;


import com.oxia.fwk.context.Context;

import com.oxia.fwk.core.ISearchEngine;
import com.oxia.fwk.core.IValueObject;
import com.oxia.fwk.core.ValueObject;
import com.oxia.fwk.searchengine.SearchEngine;

public class ListeRejetChequeTrt extends Traitement {

	public Context context = ContextHandler.getContext();
	private String libelePreavis = "PREAVIS";
	private String libelePAP = "PAP";
	private String libeleCNPOPP = "CNPOPP";

	public ListeRejetChequeTrt() {
		super();
	}

	private Cheque creeCheque(ValueObject cheque, String codeValeur, String typeRejet) {
		Cheque nouveauCheque = new Cheque();
    
		return nouveauCheque;
	}

	private void filtrerCheque(CompensationRecuVo preavisVo, ValueObject cheque, String codeRejetStr, String codeValeur) {
		// /Liste Rejet pour Opposition
		if (codeRejetStr.contains("01") || codeRejetStr.contains("02") || codeRejetStr.contains("03")
				|| codeRejetStr.contains("04")) {
			preavisVo.getListeCheque().add(creeCheque(cheque, codeValeur, libeleCNPOPP));
		}

		else if (codeRejetStr.contains("13")) {
			// /Liste Rejet Compte Cloturé + probléme de provision
			if (codeRejetStr.contains("10") || codeRejetStr.contains("11") || codeRejetStr.contains("12")) {
				preavisVo.getListeCheque().add(creeCheque(cheque, codeValeur, libelePreavis));

			}
			// /Liste Rejet Compte Cloturé + vice de Forme
			else if (codeRejetStr.contains("14") || codeRejetStr.contains("16") || codeRejetStr.contains("22")
					|| codeRejetStr.contains("23") || codeRejetStr.contains("30") || codeRejetStr.contains("32")
					|| codeRejetStr.contains("33") || codeRejetStr.contains("36") || codeRejetStr.contains("37")) {

				preavisVo.getListeCheque().add(creeCheque(cheque, codeValeur, libelePAP));
			}

		}
		// /Liste Rejet vice de Forme cheque inexploitable
		else if (codeRejetStr.contains("15") || codeRejetStr.contains("17") || codeRejetStr.contains("20")
				|| codeRejetStr.contains("21") || codeRejetStr.contains("25") || codeRejetStr.contains("31")
				|| codeRejetStr.contains("35") || codeRejetStr.contains("38") || codeRejetStr.contains("39")
				|| codeRejetStr.contains("43") || codeRejetStr.contains("44")) {
			preavisVo.getListeCheque().add(creeCheque(cheque, codeValeur, libelePAP));

		}
		// /Liste Rejet uniquement vice de Forme
		else if (codeRejetStr.contains("14") || codeRejetStr.contains("16") || codeRejetStr.contains("22")
				|| codeRejetStr.contains("23") || codeRejetStr.contains("30") || codeRejetStr.contains("32")
				|| codeRejetStr.contains("33") || codeRejetStr.contains("36") || codeRejetStr.contains("37")
				&& !codeRejetStr.contains("10") || !codeRejetStr.contains("11") || !codeRejetStr.contains("12")) {
			preavisVo.getListeCheque().add(creeCheque(cheque, codeValeur, libelePAP));
		}
		// /Liste Rejet uniquement probléme de Provision
		else if (codeRejetStr.contains("10") || codeRejetStr.contains("11") || codeRejetStr.contains("12")) {
			preavisVo.getListeCheque().add(creeCheque(cheque, codeValeur, libelePreavis));

		}
	}

	@Override
	public IValueObject perform(IValueObject vo) {

		CompensationRecuVo chequeVo = (CompensationRecuVo) vo;

		try {

			ISearchEngine searchEngine = (SearchEngine) context.getBean("searchEngine");

			/**** Traittement de Rejet Chèques pour code présentation 30 ***/
			List liste = searchEngine.findAll(Cheque30.class);

			for (int i = 0; i < liste.size(); i++) {
				String codeRejetStr =
						((Cheque30) liste.get(i)).getCodRej1() + "-" + ((Cheque30) liste.get(i)).getCodRej2() + "-"
								+ ((Cheque30) liste.get(i)).getCodRej3() + "-" + ((Cheque30) liste.get(i)).getCodRej4();

				filtrerCheque(chequeVo, (ValueObject) liste.get(i), codeRejetStr, "30");

			}

			/**** Traittement de Rejet Chèques pour code présentation 31 ***/
			liste.clear();
			liste = searchEngine.findAll(Cheque31.class);

			for (int i = 0; i < liste.size(); i++) {
				String codeRejetStr =
						((Cheque31) liste.get(i)).getCodRej1() + "-" + ((Cheque31) liste.get(i)).getCodRej2() + "-"
								+ ((Cheque31) liste.get(i)).getCodRej3() + "-" + ((Cheque31) liste.get(i)).getCodRej4();
				filtrerCheque(chequeVo, (ValueObject) liste.get(i), codeRejetStr, "31");
			}
			/**** Traittement de Rejet Chèques pour code présentation 32 ***/
			liste.clear();
			liste = searchEngine.findAll(Cheque32.class);

			for (int i = 0; i < liste.size(); i++) {
				String codeRejetStr =
						((Cheque32) liste.get(i)).getCodRej1() + "-" + ((Cheque32) liste.get(i)).getCodRej2() + "-"
								+ ((Cheque32) liste.get(i)).getCodRej3() + "-" + ((Cheque32) liste.get(i)).getCodRej4();

				filtrerCheque(chequeVo, (ValueObject) liste.get(i), codeRejetStr, "32");
			}
			/**** Traittement de Rejet Chèques pour code présentation 33 ***/
			liste.clear();
			liste = searchEngine.findAll(Cheque33.class);

			for (int i = 0; i < liste.size(); i++) {
				String codeRejetStr =
						((Cheque33) liste.get(i)).getCodRej1() + "-" + ((Cheque33) liste.get(i)).getCodRej2() + "-"
								+ ((Cheque33) liste.get(i)).getCodRej3() + "-" + ((Cheque33) liste.get(i)).getCodRej4();
				filtrerCheque(chequeVo, (ValueObject) liste.get(i), codeRejetStr, "33");
			}

			return (chequeVo);

		} catch (Exception e) {
			com.oxia.fwk.core.Error erreur = new com.oxia.fwk.core.Error();
			StringBuffer text = new StringBuffer("Erreur dans ListePreavisTrt : ");
			text.append(e.toString());
			erreur.setCode("500");
			erreur.setDescription(text.toString());
			erreur.setKey("ListePreavisTrt");
			chequeVo.addError(erreur);
			return (chequeVo);
		}

	}

	@Override
	protected void genCroText(ValueObject valueobject) {
		// TODO Auto-generated method stub

	}

}
