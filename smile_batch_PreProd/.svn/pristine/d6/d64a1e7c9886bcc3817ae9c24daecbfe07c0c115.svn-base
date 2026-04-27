package com.bna.smile.model.domainecommun.traitement;


import java.io.Serializable;
import java.util.Arrays;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import com.bna.commun.model.SeqAgence;
import com.bna.commun.model.SeqAgenceId;
import com.bna.commun.service.CURService;
import com.bna.commun.util.ContextHandler;
import com.bna.commun.util.DateHandler;
import com.bna.commun.util.StrHandler;
import com.oxia.fwk.context.Context;
import com.oxia.fwk.core.ISearchEngine;
import com.oxia.fwk.searchengine.SearchEngine;

/**
 * @author Yossri JAOUALI
 * 
 */
public class GenerateReferenceInterSiege implements Serializable {

	/**
	 * 
	 */
	// ajouter par haythem 02-11-2015
	private static final long serialVersionUID = 1L;
	static List<String> alpha = Arrays.asList(new String[]{ "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L",
			"M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "Y", "W", "X", "Z" });

	private static String getNextOne(String val) {
		int index = alpha.indexOf(val);
		if (index < alpha.size() - 1)
			return alpha.get(index + 1);
		else
			// val=="Z"
			return alpha.get(0);
	}

	// ajouter par haythem 02-11-2015
	private static String getNexSeq(String val) {
		StringBuilder seq = new StringBuilder(val);
		int pos = 0;
		for (int i = 0; i < val.length(); i++) {
			String strPos = ("" + val.charAt(i));
			if (!strPos.equals(alpha.get(alpha.size() - 1))) {

				pos = i;
				break;

			} else
				seq.setCharAt(i, alpha.get(0).charAt(0));

		}

		String vs = "" + seq.charAt(pos);
		String retour = getNextOne(vs);
		seq.setCharAt(pos, retour.charAt(0));

		return seq.toString();
	}

	public String getRISWithUpdate(Long structure, Date dateComptable) {

		Context context = ContextHandler.getContext();
		CURService crudService = (CURService) context.getBean("CURService");
		ISearchEngine searchEngine = (SearchEngine) context.getBean("searchEngine");

		/* Rechercher la sequence N° MAD relative à la structure donnée */
		SeqAgenceId seqAgenceId = new SeqAgenceId();
		seqAgenceId.setLibSeqSeqa("SEQ_MVT_GUICHET");
		seqAgenceId.setCodStrcStrc(structure);
		SeqAgence seqAgence = (SeqAgence) searchEngine.get(SeqAgence.class, seqAgenceId);
		Long valeur = seqAgence.getNumValSeqa() + 1;
		String strc = StrHandler.lpad(structure.toString(), '0', 3);
		String quantieme = StrHandler.lpad(String.valueOf(new Double(getQuantieme(dateComptable)).intValue()), '0', 3);
		// ajouter par haythem 02-11-2015
		String retour = "";
		if (Long.valueOf(valeur-1L).equals(1000L)) {
			seqAgence.setCodeSeqStr("AAA");
			seqAgence.setNumValSeqa(valeur);
			retour = strc + quantieme + seqAgence.getCodeSeqStr();
		} else if (seqAgence.getCodeSeqStr() != null && valeur > 1000) {
			seqAgence.setCodeSeqStr(getNexSeq(seqAgence.getCodeSeqStr()));
			retour = strc + quantieme + seqAgence.getCodeSeqStr();
			seqAgence.setNumValSeqa(valeur);
		} else {
			seqAgence.setNumValSeqa(valeur);
			String seq = StrHandler.lpad(String.valueOf(valeur - 1), '0', 3);
			retour = strc + quantieme + seq;
		}
		/* MAJ de la sequence */
		crudService.update(seqAgence);

		

		return retour;
	}

	public String getRISWithoutUpdate(Long structure, Date dateComptable) {

		Context context = ContextHandler.getContext();
		CURService crudService = (CURService) context.getBean("CURService");
		ISearchEngine searchEngine = (SearchEngine) context.getBean("searchEngine");

		/* Rechercher la sequence N° MAD relative à la structure donnée */

		SeqAgenceId seqAgenceId = new SeqAgenceId();
		seqAgenceId.setLibSeqSeqa("SEQ_MVT_GUICHET");
		seqAgenceId.setCodStrcStrc(structure);
		SeqAgence seqAgence = (SeqAgence) searchEngine.get(SeqAgence.class, seqAgenceId);
		long valeur = seqAgence.getNumValSeqa().intValue();
		seqAgence.setNumValSeqa(Long.valueOf(valeur));

		String strc = StrHandler.lpad(structure.toString(), '0', 3);
		String quantieme = StrHandler.lpad(String.valueOf(new Double(getQuantieme(dateComptable)).intValue()), '0', 3);
		//String seq = StrHandler.lpad(String.valueOf(valeur), '0', 3);

		// ajouter par haythem 02-11-2015
		String retour = "";
		if (Long.valueOf(valeur-1L).equals(1000L)) {
			seqAgence.setCodeSeqStr("AAA");
			seqAgence.setNumValSeqa(valeur);
			retour = strc + quantieme + seqAgence.getCodeSeqStr();
		} else if (seqAgence.getCodeSeqStr() != null && valeur > 1000) {
			seqAgence.setCodeSeqStr(getNexSeq(seqAgence.getCodeSeqStr()));
			retour = strc + quantieme + seqAgence.getCodeSeqStr();
			seqAgence.setNumValSeqa(valeur);
		} else {
			seqAgence.setNumValSeqa(valeur);
			String seq = StrHandler.lpad(String.valueOf(valeur - 1), '0', 3);
			retour = strc + quantieme + seq;
		}
		return retour;
	}
	
	public String getRISForMoneyGramWithUpdate(Long structure, Date dateComptable) {

		Context context = ContextHandler.getContext();
		CURService crudService = (CURService) context.getBean("CURService");
		ISearchEngine searchEngine = (SearchEngine) context.getBean("searchEngine");

		/* Rechercher la sequence N° MAD relative à la structure donnée */

		SeqAgenceId seqAgenceId = new SeqAgenceId();
		seqAgenceId.setLibSeqSeqa("SEQ_MIS_A_DISPOSITION_RMG");
		seqAgenceId.setCodStrcStrc(structure);
		SeqAgence seqAgence = (SeqAgence) searchEngine.get(SeqAgence.class, seqAgenceId);
		long valeur = seqAgence.getNumValSeqa().intValue()+1;
		seqAgence.setNumValSeqa(Long.valueOf(valeur));

		String strc = StrHandler.lpad(structure.toString(), '0', 3);
		String quantieme = StrHandler.lpad(String.valueOf(new Double(getQuantieme(dateComptable)).intValue()), '0', 3);
		String seq = StrHandler.lpad(String.valueOf(valeur), '0', 2);//sur deux positions
		seq = 'H' + seq;

		/* MAJ de la sequence */
		seqAgence.setNumValSeqa(valeur);
		crudService.update(seqAgence);
		
		return strc + quantieme + seq;
	}

	public String getSequenceMVG(Long structure) {

		Context context = ContextHandler.getContext();
		CURService crudService = (CURService) context.getBean("CURService");
		ISearchEngine searchEngine = (SearchEngine) context.getBean("searchEngine");

		/* Rechercher la sequence N° MAD relative à la structure donnée */

		SeqAgenceId seqAgenceId = new SeqAgenceId();
		seqAgenceId.setLibSeqSeqa("SEQ_MVT_GUICHET");
		seqAgenceId.setCodStrcStrc(structure);
		SeqAgence seqAgence = (SeqAgence) searchEngine.get(SeqAgence.class, seqAgenceId);
		long valeur = seqAgence.getNumValSeqa().intValue();
		seqAgence.setNumValSeqa(Long.valueOf(valeur));

		String seq = StrHandler.lpad(String.valueOf(valeur), '0', 3);

		return seq;
	}

	public static double getQuantieme(Date date) {
		GregorianCalendar gc = new GregorianCalendar();
		gc.setTime(date);
		return gc.get(GregorianCalendar.DAY_OF_YEAR);
	}

}
