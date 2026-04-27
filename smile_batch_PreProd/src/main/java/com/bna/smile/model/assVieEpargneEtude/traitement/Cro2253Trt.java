package com.bna.smile.model.assVieEpargneEtude.traitement;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;

import org.acegisecurity.context.SecurityContextHolder;
import org.acegisecurity.userdetails.UserDetails;

import com.bna.commun.constant.Constants;
import com.bna.commun.model.DetailOperMoyPaiement;
import com.bna.commun.model.OperationMoyPay;
import com.bna.commun.traitements.Traitement;
import com.bna.commun.util.ContextHandler;
import com.bna.commun.util.StrHandler;
import com.bna.smile.model.assVieEpargneEtude.dao.AssVieEpargneDAO;
import com.oxia.fwk.context.Context;
import com.oxia.fwk.core.IValueObject;
import com.oxia.fwk.core.ValueObject;

public class Cro2253Trt extends Traitement {
	
	Context context = ContextHandler.getContext();
	AssVieEpargneDAO assVieEpargneDAO = (AssVieEpargneDAO) context.getBean("assVieEpargneDAO");
	
	public Cro2253Trt() {
		super();
	}
	
	@Override
	protected IValueObject perform(IValueObject vo) throws Exception {
		OperationMoyPay operationMoyPay = (OperationMoyPay) vo;

		if (operationMoyPay.getCodEtatOmp().equalsIgnoreCase(Constants.COD_VALIDATION))
			this.setCroFlag(true);
		else
			this.setCroFlag(false);
		return operationMoyPay;
	}

	public void genCroText(ValueObject vo) {
		OperationMoyPay operationMoyPay = (OperationMoyPay) vo;
		/*
		 * ---------------------- Garniture de la partie FIXE du CRO -----------------------------------
		 */

		Calendar date = Calendar.getInstance();
		String numRefCro = null;
		SimpleDateFormat formatDateFile = new SimpleDateFormat("ddMMyyyy");
//		numRefCro = formatDateFile.format(date.getTime());
		numRefCro = operationMoyPay.getCodRefbOmp();
		this.setNumRefCro(Long.valueOf(numRefCro));
		this.setLibRefCro("Règlement Assureur");
		String sysdate = null;
		SimpleDateFormat formatDate = new SimpleDateFormat("dd/MM/yyyy");
		sysdate = formatDate.format(date.getTime());
		String refIntSg1 = null;
		int compteur = 1;
		Long nextRef = 0L;
		if(operationMoyPay.getStructureReceptrice().getCodStrcStrc() == 120L) {
			refIntSg1 = null;
		}else {
			String res = assVieEpargneDAO.getMaxRefInter(operationMoyPay.getStructureReceptrice().getCodStrcStrc(), operationMoyPay.getTache().getTacheId().getCodOperOper(), sysdate);
			if (res == null) {
				if(operationMoyPay.getStructureReceptrice().getCodStrcStrc() == 1L) {
					compteur = 3;
					String ag = StrHandler.lpad(operationMoyPay.getStructureReceptrice().getCodStrcStrc().toString(), '0', 3);
					refIntSg1 = ag + "002" + StrHandler.lpad(String.valueOf(compteur), '0', 3);
				}else {
					String ag = StrHandler.lpad(operationMoyPay.getStructureReceptrice().getCodStrcStrc().toString(), '0', 3);
					refIntSg1 = ag + "002" + StrHandler.lpad(String.valueOf(compteur), '0', 3);
				}
			}else {
				nextRef = Long.valueOf(res) + 1L;
				refIntSg1 = StrHandler.lpad(String.valueOf(nextRef), '0', 9);
			}
		}
		this.setCodRefInter(refIntSg1);
		this.setDatValCro(operationMoyPay.getDatValOmp());
		this.setDatValCom(operationMoyPay.getDatValOmp());
		this.setCodeStructInitiatrice(operationMoyPay.getStructureReceptrice().getCodStrcStrc().toString());
		this.setCodStrcImpt(120L);
		this.setDatExecCro(new Date());
		this.setCodEtatCro(0);
		this.setCodeProduit("1213");
		this.setOperationId(operationMoyPay.getTache().getTacheId().getCodOperOper().toString());
		this.setDateOperation(operationMoyPay.getDatOperOmp());
		SimpleDateFormat formater = new SimpleDateFormat("dd/MM/yyyy");
		formater = new SimpleDateFormat("HH:mm:ss");
		String heureString = formater.format(new Date());
		this.setHeureOperation(heureString);
		this.setTypeOperationCro("O");
		this.setCodTachTach(operationMoyPay.getTache().getTacheId().getCodTachTach());
		this.setCodRefcOmp(operationMoyPay.getNumOperOmp());
		this.setNumCinUser("8888");
		this.setCodTypUser("M");
		/*
		 * ------------------Garniture de la partie VARIABLE du CRO----------------------------------
		 */

		StringBuffer cro = new StringBuffer("");

		// contratClient
		// cro.append("COD_STRC_STRC=");
		cro.append("numCptBna=");
		cro.append(StrHandler.lpad(operationMoyPay.getContratCpt().getContratCptId().getCodStrcStrc().toString(), '0',
				3));
		// cro.append("COD_PRD_PRD=");
		cro.append(StrHandler.lpad(operationMoyPay.getContratCpt().getContratCptId().getCodPrdPrd().toString(), '0', 4));
		// cro.append("NUM_CCPT_CCPT=");
		cro.append(StrHandler.lpad(operationMoyPay.getContratCpt().getContratCptId().getNumCcptCcpt().toString(), '0',
				6) + ";");

		// Montant tranche
		cro.append("ONM_MNT_OPER=");
		cro.append(operationMoyPay.getMontDinOmp() + ";");

		// Structure Receptrice
		cro.append("cod_strc_recep=");
		cro.append(operationMoyPay.getStructureReceptrice().getCodStrcStrc().toString() + ";");

		cro.append("ONM_REF_IS=");
		cro.append(refIntSg1);

		this.setCroText(cro.toString());
	}

}
