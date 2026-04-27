package com.bna.smile.model.domainecommun.dao;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.bna.commun.model.ExonerationCltTva;
import com.bna.commun.util.ContextHandler;
import com.bna.commun.vo.PrimitiveVO;
import com.bna.smile.model.constant.Constants;
import com.bna.smile.model.domainecommun.model.Listes;
import com.bna.smile.model.domainecommun.model.PersonneCpt;
import com.bna.smile.model.domainecommun.model.PersonneStrc;
import com.bna.smile.model.domainecommun.traitement.GetPersonneCptTrt;
import com.bna.smile.model.domainecontratcompte.moyensPaiement.model.ParamRechercheOpposition;
import com.oxia.fwk.context.Context;
import com.oxia.fwk.core.ICriteria;
import com.oxia.fwk.core.IExpression;
import com.oxia.fwk.core.ISearchEngine;
import com.oxia.fwk.core.IValueObject;
import com.oxia.fwk.searchengine.SearchEngine;

public class ExonerationTvaDAO {

	private static final Log LOGGER = LogFactory.getLog(ExonerationTvaDAO.class);

	public Context context = ContextHandler.getContext();
	ISearchEngine searchEngine = (SearchEngine) context.getBean("searchEngine");
	Listes listes = new Listes();

	/*
	 * Fonction qui permet de determiner si le client est exonéré TVA ou non
	 * 
	 * @Author : Nejmeddine Ben Ouarred
	 * 
	 * @since 28/09/2012
	 * 
	 * @param ParamRechercheOpposition : critaire de recherche: Type te numéro pieèce du client et la date de référence
	 * 
	 * @return PrimitiveVO : VBoll est true si le client est exonere TVA false sinon.
	 */
	public IValueObject isClientExonereTVA(IValueObject vo) {
		ParamRechercheOpposition paramRecherche = (ParamRechercheOpposition) vo;
		PrimitiveVO result = new PrimitiveVO();
		try {

			ICriteria criteria = searchEngine.createCriteria();
			IExpression expression = searchEngine.createExpression();

			// Etat de l exoneration doit etre valide
			criteria.add(expression.eq("codEtatEtva", Constants.COD_ETAT_ETVA_VALIDE));

			// L exoneration doit etre valide a la date de reference
			criteria.add(expression.ge("datFinEtva", paramRecherche.getDateDebutConsult()));
			criteria.add(expression.lt("datValcEtva", paramRecherche.getDateDebutConsult()));

			// Recherche du numSeqPers du client a partir du Num piece, type piece
			if ((paramRecherche.getTypPceDemd() != null) && (paramRecherche.getNumPceDemd() != null)) {
				PersonneStrc personneStrc = new PersonneStrc();
				PersonneCpt personneCpt = new PersonneCpt();
				GetPersonneCptTrt getPersonneCptTrt = new GetPersonneCptTrt();
				personneStrc.setNumPcePers(paramRecherche.getNumPceDemd());
				personneStrc.setCodTpceTpce(paramRecherche.getTypPceDemd());
				personneCpt = (PersonneCpt) getPersonneCptTrt.exec(personneStrc);

				if (personneCpt != null) {
					criteria.add(expression.eq("client.numSeqPers", personneCpt.getClient().getNumSeqPers()));
				} else {
					LOGGER.debug("---------- getPersonneCptTrt a retourné personneCpt vide");
				}

			}

			List l = searchEngine.find(ExonerationCltTva.class, criteria);

			if (l != null && l.size() > 0) {
				result.setVBool(true);
			} else {
				result.setVBool(false);
			}

			return result;
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
			result.setVBool(false);
			com.oxia.fwk.core.Error erreur = new com.oxia.fwk.core.Error();
			StringBuffer text = new StringBuffer("Erreur dans GetListExonerationTvaTrt : ");
			text.append(e.toString());
			erreur.setCode("200");
			erreur.setDescription(text.toString());
			result.addError(erreur);
			return (result);
		}

	}

}
