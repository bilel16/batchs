package com.bna.smile.model.domaineplacement.service;

import com.bna.smile.model.domainecommun.traitement.CreationTraceBatchTrt;
import com.bna.smile.model.domainecommun.traitement.GetJourneeStructureBatchTrt;
import com.bna.smile.model.domainecommun.traitement.InsertCroBNACommissionFraisTenuePackTrt;
import com.bna.smile.model.domainecommun.traitement.InsertJourneeStructureBatchTrt;
import com.bna.smile.model.domainecommun.traitement.UpdateJourneeStructureBatchTrt;
import com.bna.smile.model.domaineplacement.traitement.InsertBatchExeptionPlacTrt;
import com.bna.smile.model.domaineplacement.traitement.InsertBatchStatPlacementTrt;
import com.bna.smile.model.domaineplacement.traitement.RenouvellementAEcheanceTrt;
import com.oxia.fwk.beans.service.BasicService;
import com.oxia.fwk.core.IValueObject;

public class BatchService extends BasicService {

	public BatchService() {
	}

	/**
	 * Methode permettant d'inserer BatchExeptionPlac.
	 * 
	 * @param BatchExeptionPlac
	 * @return BatchExeptionPlac
	 */
	public IValueObject InsertBatchExeptionPlac(IValueObject vo) {
		InsertBatchExeptionPlacTrt insertBatchExeptionPlacTrt = new InsertBatchExeptionPlacTrt();
		return (insertBatchExeptionPlacTrt.exec(vo));
	}

	/**
	 * Methode permettant d'inserer BatchStatPlacement.
	 * 
	 * @param BatchStatPlacement
	 * @return BatchStatPlacement
	 */
	public IValueObject InsertBatchStatPlacement(IValueObject vo) {
		InsertBatchStatPlacementTrt insertBatchStatPlacementTrt = new InsertBatchStatPlacementTrt();
		return (insertBatchStatPlacementTrt.exec(vo));
	}

	/**
	 * Methode permettant de réaliser le renouvellement.
	 * 
	 * @param ParamContratPlacement
	 * @return ParamContratPlacement
	 */
	public IValueObject renouvelerPlacement(IValueObject vo) {
		RenouvellementAEcheanceTrt renouvellementAEcheanceTrt = new RenouvellementAEcheanceTrt();
		return (renouvellementAEcheanceTrt.exec(vo));
	}

	/**
	 * Methode permettant de créer JourneeStructureBatch.
	 * 
	 * @param JourneeStructureBatch
	 * @return JourneeStructureBatch
	 */
	public IValueObject insertJourneeStructureBatch(IValueObject vo) {
		InsertJourneeStructureBatchTrt insertJourneeStructureBatchTrt = new InsertJourneeStructureBatchTrt();
		return (insertJourneeStructureBatchTrt.exec(vo));
	}

	/**
	 * Methode permettant de maj JourneeStructureBatch.
	 * 
	 * @param JourneeStructureBatch
	 * @return JourneeStructureBatch
	 */
	public IValueObject updateJourneeStructureBatch(IValueObject vo) {
		UpdateJourneeStructureBatchTrt updateJourneeStructureBatchTrt = new UpdateJourneeStructureBatchTrt();
		return (updateJourneeStructureBatchTrt.exec(vo));
	}

	/**
	 * Methode permettant de retourner JourneeStructureBatch.
	 * 
	 * @param JourneeStructureBatch
	 * @return JourneeStructureBatch
	 */
	public IValueObject getJourneeStructureBatch(IValueObject vo) {
		GetJourneeStructureBatchTrt getJourneeStructureBatchTrt = new GetJourneeStructureBatchTrt();
		return (getJourneeStructureBatchTrt.exec(vo));
	}

	/**
	 * Methode permettant d'inserer TraceBatch.
	 * 
	 * @param BatchExeptionPlac
	 * @return BatchExeptionPlac
	 */
	public IValueObject InsertTraceBatch(IValueObject vo) {
		CreationTraceBatchTrt creationTraceBatchTrt = new CreationTraceBatchTrt();
		return (creationTraceBatchTrt.exec(vo));
	}

	/******* Commission Pack **************/
	public IValueObject preleverCommissionFraisPack(IValueObject vo) {
		InsertCroBNACommissionFraisTenuePackTrt insertCroBNACommissionFraisTenuePackTrt =
				new InsertCroBNACommissionFraisTenuePackTrt();
		return (insertCroBNACommissionFraisTenuePackTrt.exec(vo));
	}

}
