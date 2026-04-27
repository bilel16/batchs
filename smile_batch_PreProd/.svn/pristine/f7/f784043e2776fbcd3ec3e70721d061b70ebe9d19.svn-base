package com.bna.smile.model.domainecompensation.gestionrejet.service;

import com.bna.smile.model.domainecompensation.gestionrejet.traitement.ChargerMotifRejetTrt;
import com.bna.smile.model.domainecompensation.gestionrejet.traitement.ChercherRejetTrt;
import com.bna.smile.model.domainecompensation.gestionrejet.traitement.EnvoisLCNFichierTrt;
import com.bna.smile.model.domainecompensation.gestionrejet.traitement.ExtractionFichierTrt;
import com.bna.smile.model.domainecompensation.gestionrejet.traitement.MoulinetteInsertingACHTrt;
import com.bna.smile.model.domainecompensation.gestionrejet.traitement.MoulinetteInsertingEffetACHTrt;
import com.bna.smile.model.domainecompensation.gestionrejet.traitement.MoulinetteInsertingEffetTrt;
import com.bna.smile.model.domainecompensation.gestionrejet.traitement.MoulinetteInsertingTrt;
import com.bna.smile.model.domainecompensation.gestionrejet.traitement.PositionCompensationEffetTrt;
import com.bna.smile.model.domainecompensation.gestionrejet.traitement.PositionCompensationTrt;
import com.bna.smile.model.domaineguichet.traitement.InsertionOperationMoyPayRetraitTrt;
import com.oxia.fwk.beans.service.BasicService;
import com.oxia.fwk.core.IValueObject;
import com.oxia.fwk.core.ValueObject;

public class GestionRejetService extends BasicService {

	public GestionRejetService() {
	}

	public ValueObject chargerMotif(ValueObject vo) {
		ChargerMotifRejetTrt chargerMotifRejetTrt = new ChargerMotifRejetTrt();
		return (chargerMotifRejetTrt.execute(vo));
	}

	public ValueObject chercherRejet(ValueObject vo) {
		ChercherRejetTrt chercherRejetTrt = new ChercherRejetTrt();
		return (chercherRejetTrt.execute(vo));
	}

	public IValueObject positionCompensation(IValueObject vo) {
		PositionCompensationTrt positionCompensationTrt = new PositionCompensationTrt();
		return (positionCompensationTrt.exec(vo));
	}

	public IValueObject positionCompensationEffet(IValueObject vo) {
		PositionCompensationEffetTrt positionTrt = new PositionCompensationEffetTrt();
		return (positionTrt.exec(vo));
	}

	public IValueObject inserting(IValueObject vo) {
		MoulinetteInsertingTrt trt = new MoulinetteInsertingTrt();
		return (trt.exec(vo));
	}
	
	public IValueObject insertingACH(IValueObject vo) {
		MoulinetteInsertingACHTrt trt = new MoulinetteInsertingACHTrt();
		return (trt.exec(vo));
	}

	public IValueObject insertingEffet(IValueObject vo) {
		MoulinetteInsertingEffetTrt trt = new MoulinetteInsertingEffetTrt();
		return (trt.exec(vo));
	}
	
	public IValueObject insertingEffetACH(IValueObject vo) {
		MoulinetteInsertingEffetACHTrt trt = new MoulinetteInsertingEffetACHTrt();
		return (trt.exec(vo));
	}
	
	public IValueObject restoreEffet(IValueObject vo) {
		EnvoisLCNFichierTrt trt = new EnvoisLCNFichierTrt();
		return (trt.exec(vo));
	}
	public IValueObject restoreCheque(IValueObject vo) {
		ExtractionFichierTrt trt = new ExtractionFichierTrt();
		return (trt.exec(vo));
	}

	public IValueObject insertingCroFromOmp(IValueObject vo) {
		System.out.println(vo);
		InsertionOperationMoyPayRetraitTrt trt =
				new InsertionOperationMoyPayRetraitTrt();
		return (trt.exec(vo));
	}

}
