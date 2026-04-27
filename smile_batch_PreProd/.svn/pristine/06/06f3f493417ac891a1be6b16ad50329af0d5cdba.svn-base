package com.bna.smile.model.domainecompensation.gestionrejet.traitement;

/*
 * @author Ayari haythem
 * 
 * @date 04 04 2013
 */

import org.springframework.orm.hibernate3.HibernateTemplate;

import com.bna.commun.model.EffetId;
import com.bna.commun.model.EffetRecu;
import com.bna.commun.model.TraceEffetRecu;
import com.bna.commun.traitements.Traitement;
import com.bna.commun.util.ContextHandler;
import com.bna.smile.model.domainecommun.service.CRUDservice;
import com.bna.smile.model.domainecompensation.gestionrejet.model.ReglementEffetVo;
import com.oxia.fwk.context.Context;
import com.oxia.fwk.core.IValueObject;
import com.oxia.fwk.core.ValueObject;

public class CreateTraceEffetRecu extends Traitement {

	public Context context = ContextHandler.getContext();

	@Override
	protected IValueObject perform(IValueObject vo) throws Exception {
		ReglementEffetVo remiseEffetVo = (ReglementEffetVo) vo;
		CRUDservice crudService = (CRUDservice) context.getBean("crudservice");

		try {

			TraceEffetRecu nouveauEffetRecu = new TraceEffetRecu();
			EffetId effetId = new EffetId();
			effetId.setNumEff(remiseEffetVo.getEffetRecu().getEffetId().getNumEff()); // TODO : to be updated
			effetId.setDatOpe(remiseEffetVo.getDateComptable()); // TODO : to be updated
			nouveauEffetRecu.setEffetId(effetId);
			nouveauEffetRecu.setCodDev(remiseEffetVo.getEffetRecu().getCodDev());
			nouveauEffetRecu.setNomTir(remiseEffetVo.getEffetRecu().getNomTir());
			nouveauEffetRecu.setMntEff(remiseEffetVo.getEffetRecu().getMntEff());
			nouveauEffetRecu.setNomBen(remiseEffetVo.getEffetRecu().getNomBen());
			nouveauEffetRecu.setDatCre(remiseEffetVo.getEffetRecu().getDatCre());
			nouveauEffetRecu.setCodEnr(remiseEffetVo.getEffetRecu().getCodEnr());
			nouveauEffetRecu.setCodBan(remiseEffetVo.getEffetRecu().getCodBan());
			nouveauEffetRecu.setCodAge(remiseEffetVo.getEffetRecu().getCodAge());
			nouveauEffetRecu.setCodBanDes(remiseEffetVo.getEffetRecu().getCodBanDes());
			nouveauEffetRecu.setCodAgeDes(remiseEffetVo.getEffetRecu().getCodAgeDes());
			nouveauEffetRecu.setLieCre(remiseEffetVo.getEffetRecu().getLieCre());
			nouveauEffetRecu.setCodAcc(remiseEffetVo.getEffetRecu().getCodAcc());
			nouveauEffetRecu.setCodNatCpt(remiseEffetVo.getEffetRecu().getCodNatCpt());
			nouveauEffetRecu.setNumLot(remiseEffetVo.getEffetRecu().getNumLot());
			nouveauEffetRecu.setMntFra(remiseEffetVo.getEffetRecu().getMntFra());
			nouveauEffetRecu.setMntInt(remiseEffetVo.getEffetRecu().getMntInt());
			nouveauEffetRecu.setCodEnd(remiseEffetVo.getEffetRecu().getCodEnd());
			nouveauEffetRecu.setRefComBen(remiseEffetVo.getEffetRecu().getRefComBen());
			nouveauEffetRecu.setRefComTir(remiseEffetVo.getEffetRecu().getRefComTir());
			nouveauEffetRecu.setRefFic(remiseEffetVo.getEffetRecu().getRefFic());
			nouveauEffetRecu.setRibBen(remiseEffetVo.getEffetRecu().getRibBen());
			nouveauEffetRecu.setCodVal(remiseEffetVo.getEffetRecu().getCodVal());
			nouveauEffetRecu.setRibTir(remiseEffetVo.getEffetRecu().getRibTir());
			nouveauEffetRecu.setRibTirIni(remiseEffetVo.getEffetRecu().getRibTirIni());
			nouveauEffetRecu.setAutInf(remiseEffetVo.getEffetRecu().getAutInf());
			nouveauEffetRecu.setDatCre(remiseEffetVo.getEffetRecu().getDatCre());
			nouveauEffetRecu.setCodProt(remiseEffetVo.getEffetRecu().getCodProt());
			nouveauEffetRecu.setDatEch(remiseEffetVo.getEffetRecu().getDatEch());
			nouveauEffetRecu.setDatEchIni(remiseEffetVo.getEffetRecu().getDatEchIni());
			nouveauEffetRecu.setDatCmp(remiseEffetVo.getEffetRecu().getDatCmp());
			nouveauEffetRecu.setCodSit(remiseEffetVo.getEffetRecu().getCodSit());
			nouveauEffetRecu.setCodNatEta(remiseEffetVo.getEffetRecu().getCodNatEta());
			// nouveauEffetRecu.setCodAval(eff.getCodAval());
			// nouveauEffetRecu.setCodBap(eff.getCodOrd());

			nouveauEffetRecu.setRngDet(remiseEffetVo.getEffetRecu().getRngDet());
			nouveauEffetRecu.setRjtReg(remiseEffetVo.getEffetRecu().getRjtReg());
			nouveauEffetRecu.setCodEnd(remiseEffetVo.getEffetRecu().getCodEnd());
			nouveauEffetRecu.setCodEtatEff(remiseEffetVo.getEffetRecu().getCodEtatEff());
			nouveauEffetRecu.setCodRej1(remiseEffetVo.getEffetRecu().getCodRej1());
			nouveauEffetRecu.setCodRej2(remiseEffetVo.getEffetRecu().getCodRej2());
			nouveauEffetRecu.setCodRej3(remiseEffetVo.getEffetRecu().getCodRej3());
			nouveauEffetRecu.setCodRej4(remiseEffetVo.getEffetRecu().getCodRej4());
			crudService.create(nouveauEffetRecu);

		} catch (Exception e) {
			e.printStackTrace();
			com.oxia.fwk.core.Error erreur = new com.oxia.fwk.core.Error();
			StringBuffer text = new StringBuffer("Erreur dans CreateTraceEffetRecu ");
			text.append(e.toString());
			erreur.setCode("500");
			erreur.setDescription(text.toString());
			erreur.setKey("CreateTraceEffetRecu");
			remiseEffetVo.addError(erreur);
			throw new RuntimeException(e);

		}
		return remiseEffetVo;
	}

	@Override
	protected void genCroText(ValueObject paramValueObject) {

		// TODO Auto-generated method stub

	}
}
