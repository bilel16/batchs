package com.bna.smile.model.domainecompensation.gestionrejet.traitement;

import java.util.Timer;
import java.util.TimerTask;

import com.bna.smile.batch.moulinette.MoulinetteInsertingCheque;
import com.bna.smile.batch.moulinette.MoulinettePositionCheque;
import com.bna.smile.batch.test.Batch;
import com.bna.smile.batch.test.BatchFrame;
import com.oxia.fwk.core.ValueObject;

public class MoulinetteChequeTimer {
	
	
	public BatchFrame mainFrame;
	public Batch mainFrameBatch;
	
	 
	  public MoulinetteChequeTimer(BatchFrame mainFrame) {
		  
		  this.mainFrame=mainFrame;
	}

	
	Timer t = new Timer();
	public void run() {
		t.schedule(new ExecutionBatch(), 0, 2* (60 * 60000)); //(nombre d'heure * (60*60000)
	}

	class ExecutionBatch extends TimerTask {
		public void run() {
			new MoulinetteInsertingCheque(mainFrameBatch).perform(new ValueObject());
	        new MoulinettePositionCheque(mainFrameBatch).perform();

		}
	}

	public void cancel() {
		t.cancel();
	}
}


