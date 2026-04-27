package com.bna.smile.batch.test;

import com.bna.smile.batch.moulinette.MoulinetteInsertingChequeACH;
import com.bna.smile.batch.moulinette.MoulinetteInsertingChequeAgenceACH;
import com.bna.smile.batch.moulinette.MoulinettePositionChequeACH;
import com.bna.smile.model.domainecompensation.gestionrejet.traitement.Util;
import com.oxia.fwk.core.ValueObject;

public class MoulinetteChequeTest implements Runnable {

	public Batch mainFrame;
	public int flg_position_insertion;

	public MoulinetteChequeTest(int positionInsertion, Batch frame_Instance) {
		this.flg_position_insertion = positionInsertion;
		this.mainFrame = frame_Instance;
	}

	public void run() {

		/**
		 * @since 11/03/2026 Refonte SNT - ACH
		 **/

		// MoulinetteInsertingCheque insertion = new
		// MoulinetteInsertingCheque(mainFrame);
		// MoulinetteInsertingChequeAgence insertionAgence = new
		// MoulinetteInsertingChequeAgence(mainFrame);
		// MoulinettePositionCheque position = new MoulinettePositionCheque(mainFrame);

		MoulinetteInsertingChequeACH insertion = new MoulinetteInsertingChequeACH(mainFrame);
		MoulinetteInsertingChequeAgenceACH insertionAgence = new MoulinetteInsertingChequeAgenceACH(mainFrame);
		MoulinettePositionChequeACH position = new MoulinettePositionChequeACH(mainFrame);

		if (flg_position_insertion == Batch.INSERT_COMPENSATION) {
			Util.initDirectoriesTeleComp();
			insertion.perform(new ValueObject());

		} else if (flg_position_insertion == Batch.POSITION_COMPENSATION) {
			position.perform();

		} else if (flg_position_insertion == Batch.VALIDINSERT_COMPENSATION) {
			insertion.forcer(new ValueObject());

		}
		if (flg_position_insertion == Batch.INSERT_COMPENSATION_AGENCE) {
			insertionAgence.perform(new ValueObject());
		}

	}

}