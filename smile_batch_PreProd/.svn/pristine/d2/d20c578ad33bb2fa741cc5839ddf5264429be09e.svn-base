package com.bna.smile.model.prelevement.service;

import com.bna.commun.util.ContextHandler;
import com.bna.smile.model.prelevement.traitement.ExtractionFichierPrelevementTrt;
import com.bna.smile.model.prelevement.traitement.GestionLotsDomiciliationsTrt;
import com.bna.smile.model.prelevement.traitement.GestionPrelevementsDomiciliationsTrt;
import com.bna.smile.model.prelevement.traitement.GestionPrelevementsRecusTrt;
import com.bna.smile.model.prelevement.traitement.SaveLotsDomiciliationsACHTrt;
import com.bna.smile.model.prelevement.traitement.SaveLotsDomiciliationsTrt;
import com.bna.smile.model.prelevement.traitement.SaveLotsPrelevementsACHTrt;
import com.bna.smile.model.prelevement.traitement.SaveLotsPrelevementsTrt;
import com.oxia.fwk.beans.service.BasicService;
import com.oxia.fwk.context.Context;
import com.oxia.fwk.core.IValueObject;

public class PrelevementBatchService extends BasicService implements IPrelevementBatchService {

	public Context context = ContextHandler.getContext();
	private GestionPrelevementsRecusTrt gestionPrelevementsRecusTrt;
	private SaveLotsDomiciliationsTrt saveLotsDomiciliationsTrt;
	private SaveLotsDomiciliationsACHTrt saveLotsDomiciliationsACHTrt;
	private SaveLotsPrelevementsTrt saveLotsPrelevementsTrt;
	private SaveLotsPrelevementsACHTrt saveLotsPrelevementsACHTrt;
	private GestionPrelevementsDomiciliationsTrt gestionPrelevementsDomiciliationsTrt;
	private GestionLotsDomiciliationsTrt gestionLotsDomiciliationsTrt;
	private ExtractionFichierPrelevementTrt extractionFichierPrelevementTrt;

	public PrelevementBatchService() {
	}

	public IValueObject saveLotsDomiciliations(IValueObject vo) {
		saveLotsDomiciliationsTrt = new SaveLotsDomiciliationsTrt();
		return saveLotsDomiciliationsTrt.exec(vo);
	}

	public IValueObject saveLotsPrelevements(IValueObject vo) {
		saveLotsPrelevementsTrt = new SaveLotsPrelevementsTrt();
		return saveLotsPrelevementsTrt.exec(vo);
	}

	public IValueObject traiterPrelevementsRecus(IValueObject vo) {
		gestionPrelevementsRecusTrt = new GestionPrelevementsRecusTrt();
		return gestionPrelevementsRecusTrt.exec(vo);
	}

	public IValueObject gestionPrelevementsDomiciliationRecus(IValueObject vo) {
		gestionPrelevementsDomiciliationsTrt = new GestionPrelevementsDomiciliationsTrt();
		return gestionPrelevementsDomiciliationsTrt.exec(vo);
	}

	public IValueObject traiterDomiciliationsRecus(IValueObject vo) {
		gestionLotsDomiciliationsTrt = new GestionLotsDomiciliationsTrt();
		return gestionLotsDomiciliationsTrt.exec(vo);
	}

	public IValueObject genererFichierRejtesPrelevements(IValueObject vo) {
		extractionFichierPrelevementTrt= new ExtractionFichierPrelevementTrt();
		return extractionFichierPrelevementTrt.exec(vo);
	}
	
	/**
	 * MAJ ACH REFONTE SNT 11/03/2026
	 **/
	public IValueObject saveLotsDomiciliationsACH(IValueObject vo) {
		saveLotsDomiciliationsACHTrt = new SaveLotsDomiciliationsACHTrt();
		return saveLotsDomiciliationsACHTrt.exec(vo);
	}
	
	public IValueObject saveLotsPrelevementsACH(IValueObject vo) {
		saveLotsPrelevementsACHTrt = new SaveLotsPrelevementsACHTrt();
		return saveLotsPrelevementsACHTrt.exec(vo);
	}

	// ********* Getter and Setter *****************//
	public void setGestionPrelevementsRecusTrt(GestionPrelevementsRecusTrt gestionPrelevementsRecusTrt) {
		this.gestionPrelevementsRecusTrt = gestionPrelevementsRecusTrt;
	}

	public GestionPrelevementsRecusTrt getGestionPrelevementsRecusTrt() {
		return gestionPrelevementsRecusTrt;
	}

	public void setSaveLotsDomiciliationsTrt(SaveLotsDomiciliationsTrt saveLotsDomiciliationsTrt) {
		this.saveLotsDomiciliationsTrt = saveLotsDomiciliationsTrt;
	}

	public SaveLotsDomiciliationsTrt getSaveLotsDomiciliationsTrt() {
		return saveLotsDomiciliationsTrt;
	}

	public void setSaveLotsPrelevementsTrt(SaveLotsPrelevementsTrt saveLotsPrelevementsTrt) {
		this.saveLotsPrelevementsTrt = saveLotsPrelevementsTrt;
	}

	public SaveLotsPrelevementsTrt getSaveLotsPrelevementsTrt() {
		return saveLotsPrelevementsTrt;
	}

	public void setGestionPrelevementsDomiciliationsTrt(
			GestionPrelevementsDomiciliationsTrt gestionPrelevementsDomiciliationsTrt) {
		this.gestionPrelevementsDomiciliationsTrt = gestionPrelevementsDomiciliationsTrt;
	}

	public GestionPrelevementsDomiciliationsTrt getGestionPrelevementsDomiciliationsTrt() {
		return gestionPrelevementsDomiciliationsTrt;
	}

	public void setGestionLotsDomiciliationsTrt(GestionLotsDomiciliationsTrt gestionLotsDomiciliationsTrt) {
		this.gestionLotsDomiciliationsTrt = gestionLotsDomiciliationsTrt;
	}

	public GestionLotsDomiciliationsTrt getGestionLotsDomiciliationsTrt() {
		return gestionLotsDomiciliationsTrt;
	}

	public SaveLotsDomiciliationsACHTrt getSaveLotsDomiciliationsACHTrt() {
		return saveLotsDomiciliationsACHTrt;
	}

	public void setSaveLotsDomiciliationsACHTrt(SaveLotsDomiciliationsACHTrt saveLotsDomiciliationsACHTrt) {
		this.saveLotsDomiciliationsACHTrt = saveLotsDomiciliationsACHTrt;
	}

	public ExtractionFichierPrelevementTrt getExtractionFichierPrelevementTrt() {
		return extractionFichierPrelevementTrt;
	}

	public void setExtractionFichierPrelevementTrt(ExtractionFichierPrelevementTrt extractionFichierPrelevementTrt) {
		this.extractionFichierPrelevementTrt = extractionFichierPrelevementTrt;
	}

}
