package com.bna.smile.model.domainecompensation.gestionrejet.traitement;

import java.util.Iterator;
import java.util.List;

import com.bna.commun.model.ContratCpt;
import com.bna.commun.model.DetailEffet;
import com.bna.commun.model.EffetId;
import com.bna.commun.model.EffetRecu;
import com.bna.commun.model.EffetRecuTmp;
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

public class PositionEffet25Trt extends Traitement {

	public PositionEffet25Trt() {
	}

	Context context = ContextHandler.getContext();
	CRUDservice crudService = (CRUDservice) context.getBean("crudservice");
	ISearchEngine searchEngine = (SearchEngine) context.getBean("searchEngine");
	IExpression expression = searchEngine.createExpression();
	ICriteria criteria = searchEngine.createCriteria();
	Long mntTotAvisSortLCN = Long.valueOf(0);
	Long nbreTotAvisSortLCN = Long.valueOf(0);
	
	
	Long mntTotAvisSortLCR = Long.valueOf(0);
	Long nbreTotAvisSortLCR = Long.valueOf(0);


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

			listEffetsRecusVo.setCodEnr(Long.valueOf(25));
			listEffetsRecusVo.setStructure(strc);

			listEffetsRecusVo = (ListEffetsRecusVo) getListEffetsRecusTrt.exec(listEffetsRecusVo);

			ReceptionEffetVo receptionEffetVo = new ReceptionEffetVo();
			receptionEffetVo.setDateComptable(compensationEffetVo.getDateComptable());
			receptionEffetVo.setStructure(StrHandler.lpad(compensationEffetVo.getStructure().getCodStrcStrc()
					.toString(), '0', 3));

			logger.info("Traitement de Lot 25 Structure :" + compensationEffetVo.getStructure().getCodStrcStrc() + ":"
					+ listEffetsRecusVo.getListEffet25().size());

			if (listEffetsRecusVo.getListEffet25() != null && listEffetsRecusVo.getListEffet25().size() > 0) {
				for (Iterator it = listEffetsRecusVo.getListEffet25().iterator(); it.hasNext();) {

					EffetRecuTmp eff = new EffetRecuTmp();
					eff = (EffetRecuTmp) it.next();

					expression = searchEngine.createExpression();
					criteria = searchEngine.createCriteria();
					criteria.add(expression.eq("effetId.numEff", eff.getEffetId().getNumEff()));
					criteria.add(expression.eq("codEtat", "T"));
					List<DetailEffet> listEff = searchEngine.find(DetailEffet.class, criteria);

					// Verification de liquidation anterieur
					expression = searchEngine.createExpression();
					criteria = searchEngine.createCriteria();
					criteria.add(expression.eq("effetId.numEff", eff.getEffetId().getNumEff()));
					criteria.add(expression.eq("codEnr", 25L));
					criteria.add(expression.eq("codEtatEff", Constants.COD_ETAT_EFF_REG));
					List<EffetRecu> listeLiquide = (List<EffetRecu>) searchEngine.find(EffetRecu.class, criteria);
					
					if (listeLiquide.isEmpty()) {
						ContratCpt contratCpt = UtilCtr.getContratCptByRIB(eff.getRibBen());
						if (contratCpt != null) {
							criteria.add(expression.eq("effetId.numEff", eff.getEffetId().getNumEff()));

							List<EffetRecu> liste = (List<EffetRecu>) searchEngine.find(EffetRecu.class, criteria);
							EffetRecu nouveauEffetRecu = new EffetRecu();
							if (!liste.isEmpty()) { // maj effet
								nouveauEffetRecu = liste.get(0);
								// P: etat payé dans la table
								nouveauEffetRecu.setCodEtatEff(Constants.COD_ETAT_EFF_REG);
								String motif =
										eff.getCodRej1() + eff.getCodRej2() + eff.getCodRej3() + eff.getCodRej4();
								// System.out.println(motif);
								motif = motif.replace("null", "");
								nouveauEffetRecu.setMotRej(motif);
								nouveauEffetRecu.setCodDev(eff.getCodDev());
								nouveauEffetRecu.setCodRej1(eff.getCodRej1());
								nouveauEffetRecu.setCodRej2(eff.getCodRej2());
								nouveauEffetRecu.setCodRej3(eff.getCodRej3());
								nouveauEffetRecu.setCodRej4(eff.getCodRej4());
								nouveauEffetRecu.setRjtLieProv(null);
								nouveauEffetRecu.setCodEnr(eff.getCodEnr());
								// EffetRecu
								// // R : pour rejeté

								crudService.update(nouveauEffetRecu);
								ReglementEffetVo remiseEffetVo = new ReglementEffetVo();
								remiseEffetVo.setDateComptable(receptionEffetVo.getDateComptable());
								remiseEffetVo.setEffetRecu(nouveauEffetRecu);
								CreateTraceEffetRecu createTraceEffetRecu = new CreateTraceEffetRecu();
								remiseEffetVo = (ReglementEffetVo) createTraceEffetRecu.exec(remiseEffetVo);
							} else {
								logger.info("Effet Paye 962/972 :" + eff.getEffetId().getNumEff());

								EffetId effetId = new EffetId();
								effetId.setNumEff(eff.getEffetId().getNumEff()); // TODO : to be updated
								effetId.setDatOpe(receptionEffetVo.getDateComptable()); // TODO : to be updated
								nouveauEffetRecu.setEffetId(effetId);

								nouveauEffetRecu.setCodDev(eff.getCodDev());
								nouveauEffetRecu.setNomTir(eff.getNomTir());
								nouveauEffetRecu.setCodVal(eff.getCodVal());
								nouveauEffetRecu.setMntEff(eff.getMntEff());
								nouveauEffetRecu.setNomBen(eff.getNomBen());
								nouveauEffetRecu.setDatCre(eff.getDatCre());
								nouveauEffetRecu.setCodEnr(eff.getCodEnr());
								nouveauEffetRecu.setCodBan(eff.getCodBan());
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
								nouveauEffetRecu.setCodNatEta(eff.isCodNatEta());
								nouveauEffetRecu.setCodOrd(eff.getCodOrd());
								nouveauEffetRecu.setRngDet(eff.getRngDet());
								nouveauEffetRecu.setRjtReg(eff.getRjtReg());
								nouveauEffetRecu.setCodEtatEff(Constants.COD_ETAT_EFF_REG);
								crudService.create(nouveauEffetRecu);
								eff.setCodEtatEff(Constants.COD_ETAT_EFF_TMP_FIN);
								crudService.update(eff);

								ReglementEffetVo reglementEffetVo = new ReglementEffetVo();
								reglementEffetVo.setContratCpt(contratCpt);
								reglementEffetVo.setDateComptable(compensationEffetVo.getDateComptable());
								reglementEffetVo.setStructure("" + compensationEffetVo.getStructure().getCodStrcStrc());
								reglementEffetVo.setEffetRecu(nouveauEffetRecu);
								CreateTraceEffetRecu createTraceEffetRecu = new CreateTraceEffetRecu();
								reglementEffetVo = (ReglementEffetVo) createTraceEffetRecu.exec(reglementEffetVo);
								if(isLCR(eff)) {
									Cro2069EncaissementLcrPayeeTrt cro972EncaissementLcrPayeeTrt =	new Cro2069EncaissementLcrPayeeTrt();
									reglementEffetVo =	(ReglementEffetVo) cro972EncaissementLcrPayeeTrt.exec(reglementEffetVo);
									mntTotAvisSortLCR = mntTotAvisSortLCR + eff.getMntEff();
									nbreTotAvisSortLCR = nbreTotAvisSortLCR + 1;

									
								} else {
									Cro962EncaissementLcnPayeeTrt cro962EncaissementLcnPayeeTrt =	new Cro962EncaissementLcnPayeeTrt();
									reglementEffetVo =	(ReglementEffetVo) cro962EncaissementLcnPayeeTrt.exec(reglementEffetVo);
									mntTotAvisSortLCN = mntTotAvisSortLCN + eff.getMntEff();
									nbreTotAvisSortLCN = nbreTotAvisSortLCN + 1;

								}

							}
						} else {
							logger.info("RIB BEN NOT FOUND:" + eff.getRibBen());
						}
					}

				}
			}
			receptionEffetVo.setMntTotEffetRecu(mntTotAvisSortLCN);
			receptionEffetVo.setNbrTotEffetRecu(nbreTotAvisSortLCN);

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

	public String getNumeroTache(ValueObject vo) {
		return (Constants.CODE_RESSOURCE_GENERALE);

	}

	@Override
	protected void genCroText(ValueObject arg0) {
		// TODO Auto-generated method stub

	}
	public boolean isLCR(EffetRecuTmp eff) {
		return eff.getCodVal().toString().equals("40");
	}

}
