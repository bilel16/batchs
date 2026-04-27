package com.bna.smile.model.domainecontratcompte.moyensPaiement.commande;

import com.bna.commun.util.ContextHandler;
import com.bna.smile.model.domainecontratcompte.moyensPaiement.service.OppositionMoyPaiService;
import com.oxia.fwk.context.Context;
import com.oxia.fwk.core.ICommande;
import com.oxia.fwk.core.IValueObject;
import com.oxia.fwk.core.ValueObject;

 /**
  * Opposition cheque.
  * @author Ramzi
  * @param ParamOpposition
  * @return ParamOpposition
  * @since 28/09/2007
  * 
  */
public class OppositionChequesCmd  implements ICommande{
    public OppositionChequesCmd() {
    }

    public IValueObject execute(IValueObject vo) {
        Context context = ContextHandler.getContext();
        OppositionMoyPaiService oppositionMoyPaiService = 
            (OppositionMoyPaiService)context.getBean("oppositionMoyPaiService");
        return (ValueObject)oppositionMoyPaiService.oppositionCheques(vo);
    }
}
