package com.bna.smile.model.domainecommun.traitement;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.bna.commun.model.Client;
import com.bna.commun.model.ContratCpt;
import com.bna.commun.model.Personne;
import com.bna.commun.model.Structure;
import com.bna.commun.traitements.Traitement;
import com.bna.commun.util.ContextHandler;
import com.bna.smile.model.constant.Constants;
import com.bna.smile.model.domainecommun.model.PersonneCpt;
import com.bna.smile.model.domainecommun.model.PersonneStrc;
import com.oxia.fwk.context.Context;
import com.oxia.fwk.core.ICriteria;
import com.oxia.fwk.core.IExpression;
import com.oxia.fwk.core.ISearchEngine;
import com.oxia.fwk.core.IValueObject;
import com.oxia.fwk.core.ValueObject;
import com.oxia.fwk.searchengine.SearchEngine;

public class GetPersonneCptTrt extends Traitement {

	public GetPersonneCptTrt() {
	}

	/**
	 * Fonction qui prend les données d'une personne et celle de l'agence pour retourner toutes les données de cette
	 * personne ainsi que ses contrats valides dans cette agence(si code agence est null on affiche les contrats dans
	 * toute la banque ).
	 * 
	 * @param personneStrc
	 *            : type piece, N° piece et le code de l'agence
	 * @return PersonneCpt : l'objet personne et ses contrats valides
	 */

	public IValueObject perform(IValueObject vo) {

		this.setCroFlag(false);
		PersonneStrc personneStrc = (PersonneStrc) vo;
		Context context = ContextHandler.getContext();
		ISearchEngine searchEngine = (SearchEngine) context.getBean("searchEngine");
		ICriteria criteriaCpt = searchEngine.createCriteria();
		IExpression expression = searchEngine.createExpression();
		GetPersonneTrt getPersonneTrt = new GetPersonneTrt();
		ICriteria criteriaStrc = searchEngine.createCriteria();

		PersonneCpt personneCpt = new PersonneCpt();
		List listCptValid = new ArrayList();
		Client client = new Client();
		Personne personne = new Personne();
		try {
			getPersonneTrt.setSecurityFlag(false);
			personne = (Personne) getPersonneTrt.exec(personneStrc);

			if (personne.getNumSeqPers() != null) {

				client = (Client) searchEngine.get(Client.class, personne.getNumSeqPers());

				criteriaCpt.add(expression.eq("client.numSeqPers", personne.getNumSeqPers()));
				criteriaCpt.add(expression.eq("codEtatCcpt", "V"));

				/* si le code structure est null retourner les contrats dans toutes les agences cas Direction centrale */

				if (personneStrc.getCodStrcStrc() != null) { // / cas agence ou direction regionale

					Structure structure = (Structure) searchEngine.get(Structure.class, personneStrc.getCodStrcStrc());
					if (structure.getTypeStructure().getCodTstrTstr().equals(new Long(2))) { // / cas DR
						// /criteriaCpt.add(expression.eq("structure.codStrmStrc", personneStrc.getCodStrcStrc()));

						criteriaStrc.add(expression.eq("structure.codStrcStrc", personneStrc.getCodStrcStrc()));
						List listStructures = searchEngine.find(Structure.class, criteriaStrc);
						for (int i = 0; i < listStructures.size(); i++) {
							Structure st = (Structure) listStructures.get(i);
							ICriteria criteriaSt = searchEngine.createCriteria();
							criteriaSt.add(expression.eq("contratCptId.codStrcStrc", st.getCodStrcStrc()));
							criteriaSt.add(expression.eq("client.numSeqPers", personne.getNumSeqPers()));
							criteriaSt.add(expression.eq("codEtatCcpt", "V"));
							List lContrat = searchEngine.find(ContratCpt.class, criteriaSt);
							Collection c = new ArrayList(lContrat);
							listCptValid.addAll(c);
						}

					} else { // / cas agence
						criteriaCpt.add(expression.eq("contratCptId.codStrcStrc", personneStrc.getCodStrcStrc()));
						listCptValid = searchEngine.find(ContratCpt.class, criteriaCpt);
					}
				} else
					listCptValid = searchEngine.find(ContratCpt.class, criteriaCpt);

			} else {
				personne = null;
				client = null;
			}
			/* Charger le VO personneCpt */
			personneCpt.setPersonne(personne);
			personneCpt.setListeContratCpt(listCptValid);
			personneCpt.setClient(client);

		} catch (Exception e) {
			com.oxia.fwk.core.Error erreur = new com.oxia.fwk.core.Error();
			StringBuffer text = new StringBuffer("Erreur dans GetPersonneCptTrt : ");
			text.append(e.toString());
			erreur.setCode("100");
			erreur.setDescription(text.toString());
			erreur.setKey("GetPersonneCpt");
			personneCpt.addError(erreur);
			logger.error("Exception : ", e);
			throw new RuntimeException(e);

		}
		return (personneCpt);

	}

	public void genCroText(ValueObject vo) {

	}

	public String getNumeroTache(ValueObject vo) {
		return Constants.CODE_RESSOURCE_GENERALE;
	}

}
