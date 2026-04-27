package com.bna.smile.model.domainecompensation.gestionrejet.traitement;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.bna.commun.model.BatchMetier;
import com.bna.commun.model.BatchStatPlacement;
import com.bna.commun.model.Operation;
import com.bna.commun.model.Produit;
import com.bna.commun.traitements.Traitement;
import com.bna.commun.util.ContextHandler;
import com.bna.commun.util.StrHandler;
import com.bna.smile.model.constant.Constants;
import com.bna.smile.model.domainecommun.traitement.GenerateReferenceInterSiege;
import com.bna.smile.model.domainecompensation.gestionrejet.model.CompensationVo;
import com.bna.smile.model.domaineplacement.service.BatchService;
import com.oxia.fwk.context.Context;
import com.oxia.fwk.core.IValueObject;
import com.oxia.fwk.core.ValueObject;

// Referenced classes of package com.bna.smile.model.domainecompensation.gestionrejet.traitement:
// PositionCheque30Trt, PositionCheque31Trt, PositionCheque32Trt, PositionCheque33Trt

public class PositionCompensationTrt extends Traitement {

	Long montGlob;
	Long nbrGlob;

	Context context;

	SimpleDateFormat formatter;
	SimpleDateFormat formatterCro;

	public PositionCompensationTrt() {
		montGlob = Long.valueOf(0L);
		nbrGlob = Long.valueOf(0L);

		context = ContextHandler.getContext();

		formatter = new SimpleDateFormat("dd/MM/yyyy");
		formatterCro = new SimpleDateFormat("yyyyMMdd");
	}

	public IValueObject perform(IValueObject vo) {

		this.setCroFlag(true);

		CompensationVo compensationVo = (CompensationVo) vo;

		PositionCheque30Trt positionCheque30Trt = new PositionCheque30Trt();
		compensationVo = (CompensationVo) positionCheque30Trt.exec(compensationVo);

		PositionCheque31Trt positionCheque31Trt = new PositionCheque31Trt();
		compensationVo = (CompensationVo) positionCheque31Trt.exec(compensationVo);

		PositionCheque32Trt positionCheque32Trt = new PositionCheque32Trt();
		compensationVo = (CompensationVo) positionCheque32Trt.exec(compensationVo);

		PositionCheque33Trt positionCheque33Trt = new PositionCheque33Trt();
		compensationVo = (CompensationVo) positionCheque33Trt.exec(compensationVo);

		this.montGlob = compensationVo.getMontGlobChq30() + compensationVo.getMontGlobChq32()
				+ compensationVo.getMontGlobChq31() + compensationVo.getMontGlobChq33();
		System.out.println("Mnt Global :" + this.montGlob);

		this.nbrGlob = compensationVo.getNbrGlobChq30() + compensationVo.getNbrGlobChq31()
				+ compensationVo.getNbrGlobChq32() + compensationVo.getNbrGlobChq33();
		System.out.println("Nbre Global :" + this.nbrGlob);

		String messageStatistique = "Sucées de l’exécution :\n";

		if (compensationVo.getNbrGlobChq30() > 0L) {
			messageStatistique = messageStatistique + compensationVo.getNbrGlobChq30().toString()
					+ " cheque 30 positionés pour un mnt = " + compensationVo.getMontGlobChq30() + "  ; \n ";
		}

		if (compensationVo.getNbrGlobChq31() > 0L) {
			messageStatistique = messageStatistique + compensationVo.getNbrGlobChq31().toString()
					+ " cheque 31 positionés pour un mnt = " + compensationVo.getMontGlobChq31() + "  ; \n ";
		}

		if (compensationVo.getNbrGlobChq32() > 0L) {
			messageStatistique = messageStatistique + compensationVo.getNbrGlobChq32().toString()
					+ " cheque 32 positionés pour un mnt = " + compensationVo.getMontGlobChq32() + "  ; \n ";
		}

		if (compensationVo.getNbrGlobChq33() > 0L) {
			messageStatistique = messageStatistique + compensationVo.getNbrGlobChq33().toString()
					+ " cheque 33 positionés pour un mnt = " + compensationVo.getMontGlobChq33() + "  ; \n ";
		}

		this.gestionStatistique(compensationVo, messageStatistique);

		return compensationVo;

	}

	private void gestionStatistique(CompensationVo compensationVo, String message) {
		BatchStatPlacement batchStatPlacement = new BatchStatPlacement();

		batchStatPlacement.setCodEtatBats("V");
		batchStatPlacement.setDatSystBats(new Date());
		batchStatPlacement.setDatCompBats(compensationVo.getDateComptable());
		batchStatPlacement.setStructure(compensationVo.getStrutcure());
		batchStatPlacement.setLibExtrBats(message);

		BatchMetier batchMetier = new BatchMetier();
		batchMetier.setCodBatBmet(Constants.COD_BATCH_COMPENSATION_CHEQUE);
		batchStatPlacement.setBatchMetier(batchMetier);

		BatchService batchService = (BatchService) context.getBean("batchService");
		batchStatPlacement = (BatchStatPlacement) batchService.InsertBatchStatPlacement(batchStatPlacement);
	}

	public void genCroText(ValueObject vo) {

		CompensationVo compensationVo = (CompensationVo) vo;

		logger.info("starting CreationCROReceptionPrelevementsRecusTrt method ");

		Operation operation = new Operation();
		operation.setCodOperOper(Constants.COD_OPER_RECEP_COMP);

		Produit produit = new Produit();
		produit.setCodPrdPrd(Constants.COD_PRODUIT_CHEQUE);

		setNumRefCro(Long.valueOf(formatterCro.format(compensationVo.getDateComptable())).longValue());
		setLibRefCro("Reception compensation reçue cheque");
		setCodeStructInitiatrice((new StringBuilder()).append(Constants.COD_DIR_TRESORERIE).toString());
		setCodStrcImpt(compensationVo.getStrutcure().getCodStrcStrc());
		setDatExecCro(new Date());
		setCodEtatCro(0);
		setCodeProduit(produit.getCodPrdPrd().toString());
		setOperationId(operation.getCodOperOper().toString());
		setDateOperation(compensationVo.getDateComptable());

		GenerateReferenceInterSiege generateReferenceInterSiege = new GenerateReferenceInterSiege();
		String quantieme = StrHandler.lpad(String.valueOf(
				(new Double(GenerateReferenceInterSiege.getQuantieme(compensationVo.getDateComptable()))).intValue()),
				'0', 3);
		setCodRefInter("900" + quantieme + "2EC");

		setDatValCro(compensationVo.getDateComptable());
		setDatValCom(null);

		SimpleDateFormat formater = new SimpleDateFormat("dd/MM/yyyy");
		formater = new SimpleDateFormat("HH:mm:ss");
		String heureString = formater.format(new Date());

		setHeureOperation(heureString);
		setTypeOperationCro("O");
		setCodTachTach(Constants.COD_TACH_RECEP_COMP.longValue());
		setCodRefcOmp(formatterCro.format(compensationVo.getDateComptable()));
		setNumCinUser("9999");
		setCodTypUser("M");

		/*
		 * ------------------Garniture de la partie VARIABLE du
		 * CRO----------------------------------
		 */

		StringBuffer cro = new StringBuffer("");

		// *** Montant global compensation reçu ****//
		cro.append("MNT_GLO_CHQ_AGE=");
		cro.append(montGlob + ";");

		// *** Code référence inter siège ****//
		cro.append("code_ref_is=");
		cro.append(67 + ";");

		// *** Nombre global compensation reçu ****//
		cro.append("NBR_GLO_CHQ_AGE=");
		cro.append(nbrGlob + ";");

		// *** Structure receptrice ****//
		// cro.append("cod_strc_recep=");
		// cro.append(compensationVo.getStrutcure().getCodStrcStrc()+ ";");

		// *** Numéro de lot de la réception ****//
		// cro.append("Numlotreception=");
		// cro.append(1 + ";");

		// *** Date compensation ****//
		cro.append("Dat_comp=");
		cro.append(formatterCro.format(compensationVo.getDateComptable()) + ";");

		this.setCroText(cro.toString());
	}

	public String getNumeroTache(ValueObject vo) {
		return "120";
	}
}
