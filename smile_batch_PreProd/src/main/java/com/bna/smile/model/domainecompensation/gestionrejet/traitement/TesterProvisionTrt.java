package com.bna.smile.model.domainecompensation.gestionrejet.traitement;

import java.util.List;

import com.bna.commun.model.BlocageCheque;
import com.bna.commun.model.Cheque;
import com.bna.commun.model.ContratCpt;
import com.bna.commun.traitements.Traitement;
import com.bna.commun.util.ContextHandler;
import com.bna.commun.util.DateHandler;
import com.bna.smile.model.constant.Constants;
import com.bna.smile.model.domainecompensation.gestionrejet.dao.CompensationDAO;
import com.bna.smile.model.domainecompensation.gestionrejet.model.BlocageChqVo;
import com.bna.smile.model.domainecompensation.gestionrejet.model.ReglementChequeVo;
import com.bna.smile.model.domainecompensation.gestionrejet.model.ReservationChqVo;
import com.bna.smile.web.commun.controller.UtilCtr;
import com.oxia.fwk.context.Context;
import com.oxia.fwk.core.ICriteria;
import com.oxia.fwk.core.IExpression;
import com.oxia.fwk.core.ISearchEngine;
import com.oxia.fwk.core.IValueObject;
import com.oxia.fwk.core.ValueObject;
import com.oxia.fwk.searchengine.SearchEngine;

public class TesterProvisionTrt extends Traitement {

	Context context = ContextHandler.getContext();
	ISearchEngine searchEngine = (SearchEngine) context.getBean("searchEngine");
	IExpression expression = searchEngine.createExpression();
	ICriteria criteria = searchEngine.createCriteria();

	public TesterProvisionTrt() {
	}

	public IValueObject perform(IValueObject vo) {

		ReglementChequeVo reglementChequeVo = new ReglementChequeVo();
		reglementChequeVo = (ReglementChequeVo) vo;
		ContratCpt contratCpt = reglementChequeVo.getContratCpt();
		try {
			Long mntBlocage = Long.valueOf(0);
			Long mntReservation = Long.valueOf(0);
			Long provision = Long.valueOf(0);
			Long provisionCptVert = Long.valueOf(0);
			ContratCpt contratCptVert = null;

			BlocageCheque blocageCheque = new BlocageCheque();
			BlocageChqVo blocageChqVo = new BlocageChqVo();
			Cheque cheque = reglementChequeVo.getCheque();
			GetBolcagePourChqTrt getBolcagePourChqTrt = new GetBolcagePourChqTrt();
			blocageCheque.setContratCpt(contratCpt);
			blocageCheque.setNumChqChq(cheque.getChequeId().getNumChqChq());
			blocageChqVo.setBlocageCheque(blocageCheque);
			blocageChqVo.setTypeBlocage(reglementChequeVo.getTypeBlocage());
			blocageChqVo = (BlocageChqVo) getBolcagePourChqTrt.exec(blocageChqVo);
			mntBlocage = blocageChqVo.getSommeBlocage();
			/************ TEST MONTANT RESERVATION ****/
			CompensationDAO compensationDAO = (CompensationDAO) context.getBean("compensationDAO");
			List<ReservationChqVo> listeReservationChqVo =
					compensationDAO.getListReservationChq(reglementChequeVo.getCheque().getChequeId().getRibTirChq(),
							reglementChequeVo.getCheque().getChequeId().getNumChqChq() + "");

			if (listeReservationChqVo != null && listeReservationChqVo.size() != 0) {

				for (ReservationChqVo reservationChqVo : listeReservationChqVo) {

					mntReservation += reservationChqVo.getMontantRsv();
				}
			}

			/**** Begin ************* Ctx/Clx *********************/
			if (cheque.getMntChqChq() != null && contratCpt != null && contratCpt.getContratCptId() != null
					&& contratCpt.getDevise() != null) {
				// if ((contratCpt.getCodEtatCcpt().equals(Constants.COD_ETAT_CPT_RESILIE)||
				// contratCpt.getCodEtatCcpt().equals(Constants.COD_ETAT_CPT_TCONTENTIEU)) && cheque.getMntChqChq()
				// <=Constants.MNT_SEUIL_CHQ) {
				if (!UtilCtr.isDinarConvertible(contratCpt)
						&& contratCpt.getDevise().getCodDevDev().toString().equals("" + Constants.COD_DEV_DINAR)
						&& cheque.getMntChqChq() <= Constants.MNT_SEUIL_CHQ) {
					reglementChequeVo.setProvisionDisponible(true);
					reglementChequeVo.setProvision(0L);
					reglementChequeVo.setSommeBlocage(0L);
					reglementChequeVo.setMontVertUtil(0L);
					reglementChequeVo.setSommeReserve(0l);
					return (reglementChequeVo);
				}
			}
			/**** End ************** Ctx/Clx *************************************/

			if (UtilCtr.isContratValide(contratCpt)) {
				provision = contratCpt.getProvision(reglementChequeVo.getDateComptable());
				if (contratCpt.getBoolCverCcpt() != null && contratCpt.getBoolCverCcpt().equals(1L)) {
					criteria.add(
							expression.eq("contratCptId.numCcptCcpt", contratCpt.getContratCptId().getNumCcptCcpt()));
					criteria.add(
							expression.eq("contratCptId.codStrcStrc", contratCpt.getContratCptId().getCodStrcStrc()));
					criteria.add(expression.eq("contratCptId.codPrdPrd", Constants.COD_COMPTE_VERT));
					criteria.add(expression.eq("codEtatCcpt", Constants.COD_ETAT_CPT_VALID));
					List liste = searchEngine.find(ContratCpt.class, criteria);
					if (!liste.isEmpty()) {
						contratCptVert = (ContratCpt) liste.get(0);
						// provision = provision-Constants.SEUIL_SLD_CR_101;
						provisionCptVert = contratCptVert.getProvision(reglementChequeVo.getDateComptable());
						// provisionCptVert = provisionCptVert - Constants.SEUIL_MIN_165; periorite payé le benificiaire
						provisionCptVert = provisionCptVert;
					}
				}
			}

			// test si paiement à partir du blocage
			if (mntBlocage >= reglementChequeVo.getMontantARegler()
					|| mntReservation >= reglementChequeVo.getMontantARegler()
					|| ((mntBlocage + mntReservation) >= reglementChequeVo.getMontantARegler())) {
				reglementChequeVo.setProvisionDisponible(true);
				reglementChequeVo.setProvision(provision);
				// test si paiement à partir du blocage+ compte
			} else if (provision >= 0 && UtilCtr.isContratValide(contratCpt)
					&& (mntBlocage + provision + mntReservation) >= reglementChequeVo.getMontantARegler()) {
				reglementChequeVo.setProvisionDisponible(true);
				reglementChequeVo.setProvision(provision);

			} else if (UtilCtr.isContratValide(contratCpt) && contratCptVert != null) {
				if ((mntBlocage + provision + provisionCptVert + mntReservation) >= reglementChequeVo
						.getMontantARegler()) {
					reglementChequeVo.setProvisionDisponible(true);
					reglementChequeVo.setMontVertUtil(
							reglementChequeVo.getMontantARegler() - mntBlocage - provision - mntReservation);

				} else {
					reglementChequeVo.setProvisionDisponible(false);
					reglementChequeVo.setMontVertUtil(provisionCptVert);

				}
				provision += provisionCptVert;
				reglementChequeVo.setProvision(provision);

			} else {
				reglementChequeVo.setProvisionDisponible(false);

			}
			if (provision < 0) {
				reglementChequeVo.setProvision(0L);
			} else {
				reglementChequeVo.setProvision(provision);
			}
			reglementChequeVo.setSommeBlocage(mntBlocage);
			reglementChequeVo.setSommeReserve(mntReservation);
			/**** Begin motif 14 ************************************/
			// if(cheque.getMntChqChq()!=null){
			// if ((UtilCtr.isDinarConvertible(contratCpt)||
			// (!contratCpt.getDevise().getCodDevDev().toString().equals(""+Constants.COD_DEV_DINAR)) )&&
			// cheque.getMntChqChq() <=Constants.MNT_SEUIL_CHQ) {
			// if( ! reglementChequeVo.isProvisionDisponible() )
			// {
			// reglementChequeVo.setProvisionDisponible(true);
			// reglementChequeVo.setProvision(0L);
			// reglementChequeVo.setSommeBlocage(0L);
			// reglementChequeVo.setMontVertUtil(0L);
			// return (reglementChequeVo);
			// }
			// }
			// }
			/**** End motif 14 **********************************/

		} catch (Exception e) {
			e.printStackTrace();
			com.oxia.fwk.core.Error erreur = new com.oxia.fwk.core.Error();
			StringBuffer text = new StringBuffer("Erreur dans TesterProvisionTrt : ");
			text.append(e.toString());
			erreur.setCode("500");
			erreur.setDescription(text.toString());
			erreur.setKey("TesterProvisionTrt");
			reglementChequeVo.addError(erreur);
			logger.error("Erreur au niveau TesterProvisionTrt : ", e);
			throw new RuntimeException(e.getMessage());
		}
		return (reglementChequeVo);
	}

	public void genCroText(ValueObject vo) {

	}
}