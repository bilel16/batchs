package com.bna.smile.model.domainecompensation.gestionrejet.traitement;

import java.util.Iterator;
import java.util.List;

import com.bna.commun.model.ContratCpt;
import com.bna.commun.model.DetailEffet;
import com.bna.commun.model.EffetId;
import com.bna.commun.model.EffetRecu;
import com.bna.commun.model.EffetRecuTmp;
import com.bna.commun.model.EffetRecuTmpEsc;
import com.bna.commun.model.Structure;
import com.bna.commun.traitements.Traitement;
import com.bna.commun.util.ContextHandler;
import com.bna.commun.util.StrHandler;
import com.bna.smile.model.constant.Constants;
import com.bna.smile.model.domainecommun.service.CRUDservice;
import com.bna.smile.model.domainecompensation.gestionrejet.model.CompensationEffetVo;
import com.bna.smile.model.domainecompensation.gestionrejet.model.ReceptionEffetVo;
import com.bna.smile.model.domainecompensation.gestionrejet.model.ReglementEffetVo;
import com.bna.smile.model.traitementCompensationRecu.model.ListEffetsRecusVo;
import com.bna.smile.model.traitementCompensationRecu.traitement.GetListEffetsRecusTrt;
import com.bna.smile.web.commun.controller.UtilCtr;
import com.oxia.fwk.context.Context;
import com.oxia.fwk.core.ICriteria;
import com.oxia.fwk.core.IExpression;
import com.oxia.fwk.core.ISearchEngine;
import com.oxia.fwk.core.IValueObject;
import com.oxia.fwk.core.ValueObject;
import com.oxia.fwk.searchengine.SearchEngine;

public class PositionEffet22Trt extends Traitement {

	public PositionEffet22Trt() {
	}

	Context context = ContextHandler.getContext();
	CRUDservice crudService = (CRUDservice) context.getBean("crudservice");
	ISearchEngine searchEngine = (SearchEngine) context.getBean("searchEngine");
	IExpression expression = searchEngine.createExpression();
	ICriteria criteria = searchEngine.createCriteria();

	Long mntTotRejetLCREsc = Long.valueOf(0);
	Long nbreTotRejetLCREsc = Long.valueOf(0);

	Long mntTotRejetLCREnc = Long.valueOf(0);
	Long nbreTotRejetLCREnc = Long.valueOf(0);

	Long mntTotRejetLCNEnc = Long.valueOf(0);
	Long nbreTotRejetLCNEnc = Long.valueOf(0);

	public IValueObject perform(IValueObject vo) {
		this.setSecurityFlag(false);
		this.setVerifDomaine(false);
		this.setCroFlag(false);

		CompensationEffetVo compensationEffetVo = (CompensationEffetVo) vo;
		GetListEffetsRecusTrt getListEffetsRecusTrt = new GetListEffetsRecusTrt();

		try {

			Structure strc =
					(Structure) searchEngine.get(Structure.class, compensationEffetVo.getStructure().getCodStrcStrc());

			ListEffetsRecusVo listEffetsRecusVo = new ListEffetsRecusVo();
			listEffetsRecusVo.setDateComptable(compensationEffetVo.getDateComptable());
			listEffetsRecusVo.setCodEnr(Long.valueOf(22));
			listEffetsRecusVo.setStructure(strc);

			listEffetsRecusVo = (ListEffetsRecusVo) getListEffetsRecusTrt.exec(listEffetsRecusVo);

			ReceptionEffetVo receptionEffetVo = new ReceptionEffetVo();
			receptionEffetVo.setDateComptable(compensationEffetVo.getDateComptable());
			receptionEffetVo.setStructure(StrHandler.lpad(compensationEffetVo.getStructure().getCodStrcStrc()
					.toString(), '0', 3));

			logger.info("Traitement de Lot 22 Structure  :" + compensationEffetVo.getStructure().getCodStrcStrc() + ":"
					+ listEffetsRecusVo.getListEffet22().size());

			if (listEffetsRecusVo.getListEffet22() != null && listEffetsRecusVo.getListEffet22().size() > 0) {
				for (Iterator it = listEffetsRecusVo.getListEffet22().iterator(); it.hasNext();) {
					EffetRecuTmp eff = new EffetRecuTmp();
					eff = (EffetRecuTmp) it.next();

					expression = searchEngine.createExpression();
					criteria = searchEngine.createCriteria();
					criteria.add(expression.eq("effetId.numEff", eff.getEffetId().getNumEff()));
					List<DetailEffet> listEff = searchEngine.find(DetailEffet.class, criteria);

					listEff.clear();
					if (listEff.isEmpty()) {
						expression = searchEngine.createExpression();
						criteria = searchEngine.createCriteria();
						if (isLCR(eff)) {
							mntTotRejetLCREnc = mntTotRejetLCREnc + eff.getMntEff();
							nbreTotRejetLCREnc = nbreTotRejetLCREnc + 1;
						} else {
							mntTotRejetLCNEnc = mntTotRejetLCNEnc + eff.getMntEff();
							nbreTotRejetLCNEnc = nbreTotRejetLCNEnc + 1;
						}
						EffetRecu nouveauEffetRecu = new EffetRecu();
						eff.setCodEtatEff("F");

						nouveauEffetRecu = new EffetRecu();
						// updated 17-09-2015
						criteria.add(expression.eq("effetId.numEff", eff.getEffetId().getNumEff()));
						criteria.add(expression.eq("ribTir", eff.getRibTir()));
						criteria.add(expression.eq("ribBen", eff.getRibBen()));
						List<EffetRecu> liste = (List<EffetRecu>) searchEngine.find(EffetRecu.class, criteria);
						if (!liste.isEmpty()) {
							// 2eme presentation )
							// update by haythem : garder une seule ocurence de 21 ou 22(cas mm agence)
							for (int i = 0; i < liste.size(); i++) {
								EffetRecu effToRem = liste.get(i);
								crudService.remove(effToRem);
							}

						}

						EffetId effetId = new EffetId();
						effetId.setNumEff(eff.getEffetId().getNumEff()); // TODO : to be updated
						effetId.setDatOpe(receptionEffetVo.getDateComptable()); // TODO : to be updated
						nouveauEffetRecu.setEffetId(effetId);
						nouveauEffetRecu.setCodDev(eff.getCodDev());
						nouveauEffetRecu.setNomTir(eff.getNomTir());
						nouveauEffetRecu.setMntEff(eff.getMntEff());
						nouveauEffetRecu.setNomBen(eff.getNomBen());
						nouveauEffetRecu.setDatCre(eff.getDatCre());
						nouveauEffetRecu.setCodEnr(eff.getCodEnr());
						nouveauEffetRecu.setCodBan(eff.getCodBan());
						nouveauEffetRecu.setCodVal(eff.getCodVal());
						nouveauEffetRecu.setCodAge(eff.getCodAge());
						nouveauEffetRecu.setCodBanDes(eff.getCodBanDes());
						nouveauEffetRecu.setCodAgeDes(eff.getCodAgeDes());
						nouveauEffetRecu.setLieCre(eff.getLieCre());
						nouveauEffetRecu.setCodAcc(eff.getCodAcc());
						nouveauEffetRecu.setCodNatCpt(eff.getCodNatCpt());
						nouveauEffetRecu.setNumLot(eff.getNumLot());
						nouveauEffetRecu.setMntFra(eff.getMntFra());
						nouveauEffetRecu.setMntInt(eff.getMntInt());
						nouveauEffetRecu.setCodEnd(eff.getCodEnd());
						nouveauEffetRecu.setRefComBen(eff.getRefComBen());
						nouveauEffetRecu.setRefComTir(eff.getRefComTir());
						nouveauEffetRecu.setRefFic(eff.getRefFic());
						nouveauEffetRecu.setRibBen(eff.getRibBen());
						nouveauEffetRecu.setRibTir(eff.getRibTir());
						nouveauEffetRecu.setRibTirIni(eff.getRibTirIni());
						nouveauEffetRecu.setAutInf(eff.getAutInf());
						nouveauEffetRecu.setDatCre(eff.getDatCre());
						nouveauEffetRecu.setDatEch(eff.getDatEch());
						nouveauEffetRecu.setDatEchIni(eff.getDatEchIni());
						nouveauEffetRecu.setDatCmp(eff.getDatCmp());
						nouveauEffetRecu.setCodSit(eff.isCodSit());
						nouveauEffetRecu.setCodProt(eff.getCodProt());
						nouveauEffetRecu.setCodNatEta(eff.isCodNatEta());
						nouveauEffetRecu.setCodOrd(eff.getCodOrd());
						nouveauEffetRecu.setRngDet(eff.getRngDet());
						nouveauEffetRecu.setRjtReg(eff.getRjtReg());
						nouveauEffetRecu.setCodRej1(eff.getCodRej1());
						nouveauEffetRecu.setCodRej2(eff.getCodRej2());
						nouveauEffetRecu.setCodRej3(eff.getCodRej3());
						nouveauEffetRecu.setCodRej4(eff.getCodRej4());
						nouveauEffetRecu.setCodEtatEff(Constants.COD_ETAT_EFF_IMP);
						crudService.create(nouveauEffetRecu);
						eff.setCodEtatEff(Constants.COD_ETAT_EFF_TMP_FIN);
						crudService.update(eff);
						ReglementEffetVo reglementEffetVo = new ReglementEffetVo();
						reglementEffetVo.setStructure(receptionEffetVo.getStructure());
						reglementEffetVo.setDateComptable(receptionEffetVo.getDateComptable());
						reglementEffetVo.setEffetRecu(nouveauEffetRecu);

						CreateTraceEffetRecu createTraceEffetRecu = new CreateTraceEffetRecu();
						reglementEffetVo = (ReglementEffetVo) createTraceEffetRecu.exec(reglementEffetVo);

						ContratCpt cpt = UtilCtr.getContratCptByRIB(eff.getRibBen());
						if (cpt != null) {

							reglementEffetVo.setContratCpt(cpt);
							if (isLCR(eff)) {
								Cro1018DenouementCptImpayeTrt cptImpayeTrt = new Cro1018DenouementCptImpayeTrt();
								reglementEffetVo = (ReglementEffetVo) cptImpayeTrt.exec(reglementEffetVo);

							} else {

								Cro724DenouementCptImpayeTrt cptImpayeTrt = new Cro724DenouementCptImpayeTrt();
								reglementEffetVo = (ReglementEffetVo) cptImpayeTrt.exec(reglementEffetVo);

							}

						} else {
							System.out.println("Compte Ben 724/1018 NOT FOUND :" + eff.getRibBen());
						}

					}
				}
			}
			receptionEffetVo.setMntTotEffetRecu(mntTotRejetLCNEnc);
			receptionEffetVo.setNbrTotEffetRecu(nbreTotRejetLCNEnc);
			Cro949ReceptionRejetTrt cro949ReceptionRejetTrt = new Cro949ReceptionRejetTrt();
			receptionEffetVo = (ReceptionEffetVo) cro949ReceptionRejetTrt.exec(receptionEffetVo);

			receptionEffetVo.setMntTotEffetRecu(mntTotRejetLCREnc);
			receptionEffetVo.setNbrTotEffetRecu(nbreTotRejetLCREnc);
			Cro963ReceptionRejetTrt cro963ReceptionRejetTrt = new Cro963ReceptionRejetTrt();
			receptionEffetVo = (ReceptionEffetVo) cro963ReceptionRejetTrt.exec(receptionEffetVo);

			// generation cro rejet global escompte LCR
			expression = searchEngine.createExpression();
			criteria = searchEngine.createCriteria();

			criteria.add(expression.eq("codEnr", Long.valueOf(22))); // / Date
			criteria.add(expression.eq("codVal", Long.valueOf(40)));
			criteria.add(expression.eq("effetId.datOpe", listEffetsRecusVo.getDateComptable())); // / Date
			criteria.add(expression.eq("codAgeDes",
					StrHandler.lpad(listEffetsRecusVo.getStructure().getCodBctStrc(), '0', 3))); // / Structure
			List<EffetRecuTmpEsc> rejetEscompteLCR = searchEngine.find(EffetRecuTmpEsc.class, criteria);
			for (EffetRecuTmpEsc esc : rejetEscompteLCR) {
				mntTotRejetLCREsc = mntTotRejetLCREsc + esc.getMntEff();

			}
			nbreTotRejetLCREsc = (long) rejetEscompteLCR.size();
			receptionEffetVo.setMntTotEffetRecu(mntTotRejetLCREsc + mntTotRejetLCREnc);
			receptionEffetVo.setNbrTotEffetRecu(nbreTotRejetLCREsc + nbreTotRejetLCREnc);
			Cro2070ReceptionRejetLCRTrt cro2070ReceptionRejetTrt = new Cro2070ReceptionRejetLCRTrt();
			receptionEffetVo = (ReceptionEffetVo) cro2070ReceptionRejetTrt.exec(receptionEffetVo);

		} catch (Exception e) {
			com.oxia.fwk.core.Error erreur = new com.oxia.fwk.core.Error();
			StringBuffer text = new StringBuffer("Erreur dans PositionEffetTrt : ");
			text.append(e.toString());
			erreur.setCode("100");
			erreur.setDescription(text.toString());
			erreur.setKey("PositionEffetTrt");
			logger.error("Exception : ", e);
			compensationEffetVo.addError(erreur);
			throw new RuntimeException(e);

		}
		return (compensationEffetVo);
	}

	public void genCroText(ValueObject vo) {

	}

	public boolean isLCR(EffetRecuTmp eff) {
		return eff.getCodVal().toString().equals("40");
	}

}
