package com.bna.smile.model.virement.traitement;

import java.util.List;

import com.bna.commun.model.ContratCpt;
import com.bna.commun.traitements.Traitement;
import com.bna.commun.util.ContextHandler;
import com.bna.smile.model.constant.Constants;
import com.bna.smile.model.domainecommun.service.CRUDservice;
import com.bna.smile.model.virement.model.VirementVo;
import com.oxia.fwk.context.Context;
import com.oxia.fwk.core.ICriteria;
import com.oxia.fwk.core.IExpression;
import com.oxia.fwk.core.ISearchEngine;
import com.oxia.fwk.core.IValueObject;
import com.oxia.fwk.core.ValueObject;
import com.oxia.fwk.searchengine.SearchEngine;

public class VerifierProvisionCompteVertTrt extends Traitement {

	Context context = ContextHandler.getContext();
	ISearchEngine searchEngine = (SearchEngine) context.getBean("searchEngine");
	CRUDservice crudService = (CRUDservice) context.getBean("crudservice");
	IExpression expression = searchEngine.createExpression();

	public VerifierProvisionCompteVertTrt() {
	}

	public IValueObject perform(IValueObject vo) {

		this.setCroFlag(false);

		VirementVo virementVo = (VirementVo) vo;
		ContratCpt contratCpt = new ContratCpt();
		contratCpt = virementVo.getContratCpt();
		boolean boolProvisionCompteVert = false;

		long SOLDE_COMPTE_VERT = 0;
		long mntProvisionSolde101 = 0;
		long mntBlocage101 = 0;
		long MONT_VIR = 0;
		long mntNecessaire165 = 0;
		MONT_VIR = virementVo.getMONT_VIR() + Constants.SOLDE_MIN_COMPTE_DAV.longValue();
		ContratCpt contratCptObj = new ContratCpt();
		try {

			if (contratCpt.getMontBlocCcpt() != null) {
				mntBlocage101=contratCpt.getMontBlocCcpt();
			}
			
			mntProvisionSolde101 = contratCpt.getMontSoldCcpt();
			
			mntNecessaire165 = Math.abs(MONT_VIR - mntProvisionSolde101+mntBlocage101);

			ISearchEngine searchEngine =
					(ISearchEngine) Context.getInstance().getSpringContext().getBean("searchEngine");
			ICriteria criteria = searchEngine.createCriteria();
			IExpression expression = searchEngine.createExpression();

			criteria.add(expression.eq("client.numSeqPers", contratCpt.getClient().getNumSeqPers()));
			criteria.add(expression.eq("contratCptId.codPrdPrd", Constants.COD_PRD_PRD_VERT));
			criteria.add(expression.eq("contratCptId.numCcptCcpt", contratCpt.getContratCptId().getNumCcptCcpt()));
			criteria.add(expression.eq("codEtatCcpt", Constants.COD_ETAT_CPT_VALID));

			List<ContratCpt> l = searchEngine.find(ContratCpt.class, criteria);

			if (l != null && l.size() > 0) {

				contratCptObj = (ContratCpt) l.get(0);

				if (contratCptObj.getContratCptId().getCodPrdPrd().equals(Constants.COD_PRD_PRD_VERT)) {
					SOLDE_COMPTE_VERT = contratCptObj.getMontSoldCcpt().longValue()
							- Long.valueOf(Constants.MNT_SEUIL_CPT_VERT).longValue();
					if (SOLDE_COMPTE_VERT >= mntNecessaire165) {
						boolProvisionCompteVert = true;
					} else {
						boolProvisionCompteVert = false;
					}
				}

			} else {
				boolProvisionCompteVert = false;
			}

			// / True : Exist Provision ---- False : Non Provision ----- ////
			virementVo.setBoolProvisionCompteVert(boolProvisionCompteVert);
			virementVo.setContratCptCompteVert(contratCptObj);
			return (virementVo);

		} catch (Exception e) {
			com.oxia.fwk.core.Error erreur = new com.oxia.fwk.core.Error();
			StringBuffer text = new StringBuffer("Erreur dans VerifierProvisionCompteVertTrt : ");
			text.append(e.toString());
			erreur.setCode("100");
			erreur.setDescription(text.toString());
			erreur.setKey("VerifierProvisionCompteVertTrt");
			virementVo.addError(erreur);
			logger.error("Erreur au niveau VerifierProvisionCompteVertTrt : ", e);
			virementVo.setMessageValidation("Probléme dans VerifierProvisionCompteVertTrt");
			return (virementVo);

		}

	}

	public void genCroText(ValueObject vo) {

	}

	public String getNumeroTache(IValueObject vo) {
		return (Constants.CODE_RESSOURCE_GENERALE);
	}
}