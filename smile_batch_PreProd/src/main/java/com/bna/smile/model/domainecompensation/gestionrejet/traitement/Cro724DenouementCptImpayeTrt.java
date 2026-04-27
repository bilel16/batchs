package com.bna.smile.model.domainecompensation.gestionrejet.traitement;

/**
 * @author Ayari haythem
 * @since 05/03/2013
 */
import java.text.SimpleDateFormat;
import java.util.Date;

import com.bna.commun.model.ContratCpt;
import com.bna.commun.model.EffetRecu;
import com.bna.commun.traitements.Traitement;
import com.bna.commun.util.ContextHandler;
import com.bna.commun.util.StrHandler;
import com.bna.smile.model.constant.Constants;
import com.bna.smile.model.domainecommun.service.CRUDservice;
import com.bna.smile.model.domainecompensation.gestionrejet.model.ReglementEffetVo;
import com.oxia.fwk.context.Context;
import com.oxia.fwk.core.IExpression;
import com.oxia.fwk.core.ISearchEngine;
import com.oxia.fwk.core.IValueObject;
import com.oxia.fwk.core.ValueObject;
import com.oxia.fwk.searchengine.SearchEngine;

public class Cro724DenouementCptImpayeTrt extends Traitement {
	Context context = ContextHandler.getContext();

	ISearchEngine searchEngine = (SearchEngine) context.getBean("searchEngine");
	CRUDservice crudService = (CRUDservice) context.getBean("crudservice");
	IExpression expression = searchEngine.createExpression();
	boolean etatClientTaxable = false;

	SimpleDateFormat formaterDate = new SimpleDateFormat("dd/MM/yyyy");
	SimpleDateFormat formaterHeure = new SimpleDateFormat("HH:mm:ss");

	public Cro724DenouementCptImpayeTrt() {
	}

	public IValueObject perform(IValueObject vo) {
		logger.info("Cro d'operation 724");
		ReglementEffetVo reglementEffetVo = (ReglementEffetVo) vo;

		try {

			this.setCroFlag(true);
			

		} catch (Exception e) {
			logger.info(e.getMessage());
			com.oxia.fwk.core.Error erreur = new com.oxia.fwk.core.Error();
			StringBuffer text = new StringBuffer("Erreur dans Cro724DenouementCptImpayeTrt : ");
			text.append(e.toString());
			erreur.setCode("100");
			erreur.setDescription(text.toString());
			erreur.setKey("Cro724DenouementCptImpayeTrt");
			reglementEffetVo.addError(erreur);
			logger.error("Erreur au niveau Cro724DenouementCptImpayeTrt  : ", e);
			throw new RuntimeException(e);

		}
		return reglementEffetVo;

	}

	public String getNumeroTache(IValueObject vo) {
		return (Constants.CODE_RESSOURCE_GENERALE);
	}

	public void genCroText(ValueObject vo) {
		try {

			ReglementEffetVo reglementEffetVo = (ReglementEffetVo) vo;
             ContratCpt contratCptBen=reglementEffetVo.getContratCpt();
             EffetRecu effetRecu=reglementEffetVo.getEffetRecu();
			/*
			 * ---------------------- Garniture de la partie FIXE du CRO -----------------------------------
			 */

			this.setNumRefCro(9999L);
			this.setLibRefCro("DENOUEMENT COMPTE IMPAYE");
			this.setCodeStructInitiatrice(reglementEffetVo.getStructure() + "");
            this.setCodRefcOmp(effetRecu.getEffetId().getNumEff());
			this.setCodStrcImpt(Long.valueOf(reglementEffetVo.getStructure()));
			this.setDatExecCro(new Date());
			this.setCodEtatCro(0);
			this.setCodeProduit(""+Constants.COD_PRODUIT_EFFET);
			this.setOperationId(""+Constants.COD_OPERATION_DENOUEMENT_CPT_IMPAYE );
			this.setDateOperation(reglementEffetVo.getDateComptable());
			this.setDatValCro(reglementEffetVo.getDateComptable());
			this.setDatValCom(null);
			SimpleDateFormat formater = new SimpleDateFormat("dd/MM/yyyy");
			formater = new SimpleDateFormat("HH:mm:ss");
			String heureString = formater.format(new Date());
			this.setHeureOperation(heureString);
			this.setTypeOperationCro("O");
			this.setCodTachTach(Constants.COD_TACHE_EFFET);
			
			
			this.setNumCinUser("9999");
			this.setCodTypUser("M");

			/*
			 * ------------------Garniture de la partie VARIABLE du CRO----------------------------------
			 */
			StringBuffer cro = new StringBuffer("");
			// *** Montant effet ****//
			cro.append("MNT_EFF_IMP_DENO=");
			Long mntEff = effetRecu.getMntEff();
			cro.append(mntEff + ";");
			// *** Numero effet ****//
			cro.append("NUM_EFF_IMP_DENO=");
			cro.append(effetRecu.getEffetId().getNumEff() + ";");

			// *** Numéro du compte client ****//
			cro.append("numCptBna=");
			cro.append(StrHandler.lpad(contratCptBen.getContratCptId().getCodStrcStrc().toString(), '0', 3));
			// cro.append("COD_PRD_PRD=");
			cro.append(StrHandler.lpad(contratCptBen.getContratCptId().getCodPrdPrd().toString(), '0', 4));
			// cro.append("NUM_CCPT_CCPT=");
			cro.append(StrHandler.lpad(contratCptBen.getContratCptId().getNumCcptCcpt().toString(), '0', 6) + ";");
			


			this.setCroText(cro.toString());
			logger.info("Cro generer avec succés");
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	

}