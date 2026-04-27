package com.bna.smile.batch.moulinette;

import com.bna.commun.util.ContextHandler;
import com.bna.smile.model.domainetraitementfichier.TimerBatch;
import com.oxia.fwk.context.Context;
import java.io.Serializable;

public class MoulinetteTraitementFichier implements Serializable {
    public MoulinetteTraitementFichier() {
    }

    Context context = ContextHandler.getContext();
    TimerBatch timerBatch = new TimerBatch();

    public String lancerTimerBatch() {

        return timerBatch.lancerBatch();
    }

    public void stopperTimerBatch() {

        timerBatch.stopperBatch();
    }


}
