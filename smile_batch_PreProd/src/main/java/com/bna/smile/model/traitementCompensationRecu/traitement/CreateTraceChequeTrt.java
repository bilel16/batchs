package com.bna.smile.model.traitementCompensationRecu.traitement;

import java.util.Date;

import com.bna.commun.model.Cheque;
import com.bna.commun.model.TraceCheque;
import com.bna.commun.model.TraceChequeId;
import com.bna.commun.model.Valeur;
import com.bna.commun.traitements.Traitement;
import com.bna.commun.util.ContextHandler;
import com.bna.smile.model.domainecommun.service.CRUDservice;
import com.bna.smile.model.traitementCompensationRecu.model.CompensationRecuVo;
import com.oxia.fwk.context.Context;
import com.oxia.fwk.core.IValueObject;
import com.oxia.fwk.core.ValueObject;

public class CreateTraceChequeTrt extends Traitement {

	public Context context = ContextHandler.getContext();

	@Override
	protected void genCroText(ValueObject paramValueObject) {

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.oxia.fwk.beans.traitement.AbstractTraitement#perform(com.oxia.fwk
	 * .core.IValueObject)
	 */
	@Override
	protected IValueObject perform(IValueObject vo) throws Exception {

		/*
		 * CompensationRecuVo compVo = (CompensationRecuVo) vo; Cheque cheque =
		 * compVo.getSelectedCheque(); // Create TraceChequeId Date datOpeChq =
		 * cheque.getChequeId().getDatOpeChq(); long numChqChq =
		 * cheque.getChequeId().getNumChqChq(); String ribBenChq =
		 * cheque.getChequeId().getRibBenChq(); String ribTirChq =
		 * cheque.getChequeId().getRibTirChq(); Long codValVal =
		 * cheque.getValeur().getCodValVal(); // TraceChequeId TraceChequeId
		 * traceChequeId = new TraceChequeId(datOpeChq, numChqChq, ribBenChq,
		 * ribTirChq, codValVal);
		 * 
		 * Valeur valeur = cheque.getValeur(); Long numLotTch =
		 * cheque.getNumLotChq(); Long mntChqTch = cheque.getMntChqChq(); String
		 * nomPrnTch = cheque.getNomPrnChq(); Date datEmiTch =
		 * cheque.getDatEmiChq(); Long codSenTch = 2L; // recu String codNateTch
		 * = cheque.getCodNateChq(); Long codEnrTch = cheque.getCodEnrChq();
		 * String codBanTch = cheque.getCodBadeChq(); String codAgedTch =
		 * cheque.getCodAgdeChq(); String codBandTch = cheque.getCodBaemChq();
		 * String codLieuTch = cheque.getCodLemiChq(); Long codSitTch =
		 * cheque.getCodSbenChq(); String codNatcTch = cheque.getCodNcptChq();
		 * String codDevTch = "";//cheque.getDevise().getCodDevDev(); long
		 * codValTch = cheque.getValeur().getCodValVal(); String refFicTch =
		 * cheque.getRefFicChq(); String codMotrTch = cheque.getCodMrejChq();
		 * String ribTirRecTch = cheque.getRibTrecChq(); Long numEvtEnvTch =
		 * cheque.getNumEvenChq(); Long numEvtRcpTch = cheque.getNumEvrcpChq();
		 * String rjtRegTch = cheque.getRjtRegChq(); // Create Trace cheque
		 * TraceCheque traceCheque = new TraceCheque(traceChequeId, valeur,
		 * cheque, numLotTch, mntChqTch, nomPrnTch, datEmiTch, codSenTch,
		 * codNateTch, codEnrTch, codBanTch, codAgedTch, codBandTch, codLieuTch,
		 * codSitTch, codNatcTch, codDevTch, codValTch, refFicTch, codMotrTch,
		 * ribTirRecTch, numEvtEnvTch, numEvtRcpTch, rjtRegTch);
		 * 
		 * try {
		 * 
		 * CRUDservice crudService = (CRUDservice) context
		 * .getBean("CRUdService"); crudService.create(traceCheque);
		 * 
		 * } catch (Exception e) { com.oxia.fwk.core.Error erreur = new
		 * com.oxia.fwk.core.Error(); StringBuffer text = new StringBuffer(
		 * "Error in CreateTraceChequeTrt : "); text.append(e.toString());
		 * erreur.setCode("200"); erreur.setDescription(text.toString());
		 * erreur.setKey("CreateTraceChequeTrt"); cheque.addError(erreur); throw
		 * new RuntimeException( "Error in creating Trace Cheque Treatement");
		 * 
		 * } return traceCheque;
		 */
		return null;

	}
}
