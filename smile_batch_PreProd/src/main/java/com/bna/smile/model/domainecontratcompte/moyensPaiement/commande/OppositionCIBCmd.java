package com.bna.smile.model.domainecontratcompte.moyensPaiement.commande;

import com.bna.commun.util.ContextHandler;
import com.bna.smile.model.domainecontratcompte.moyensPaiement.service.OppositionMoyPaiService;
import com.oxia.fwk.context.Context;
import com.oxia.fwk.core.ICommande;
import com.oxia.fwk.core.IValueObject;

 /**
  * Opposition cib.
  * @author Ramzi
  * @param ParamOpposition
  * @return ParamOpposition
  * @since 28/09/2007
  * 
  */
public class OppositionCIBCmd  implements ICommande{
    public OppositionCIBCmd() {
    }

    public IValueObject execute(IValueObject vo) {
        Context context = ContextHandler.getContext();
        OppositionMoyPaiService oppositionMoyPaiService = 
            (OppositionMoyPaiService)context.getBean("oppositionMoyPaiService");
        return oppositionMoyPaiService.oppositionCib(vo);
    }
}
