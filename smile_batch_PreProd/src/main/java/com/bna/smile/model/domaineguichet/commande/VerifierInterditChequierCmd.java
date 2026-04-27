package com.bna.smile.model.domaineguichet.commande;

import com.bna.commun.model.OppositionMoyenPaiementId;
import com.bna.commun.util.ContextHandler;
import com.bna.commun.vo.PrimitiveVO;

import com.bna.smile.model.domainecommun.model.PersonneStrc;
import com.bna.smile.model.domaineguichet.service.GuichetService;

import com.oxia.fwk.context.Context;
import com.oxia.fwk.core.ValueObject;

public class VerifierInterditChequierCmd {
    public VerifierInterditChequierCmd() {
    }

    /**
     * methode execute 
     * @param  vo Objet : PersonneStrc
     * @return vo Objet : PrimitiveVO
     */
    public ValueObject execute(ValueObject vo) {
        PersonneStrc personneStrc = (PersonneStrc)vo;
        Context context = ContextHandler.getContext();

        GuichetService guichetService = 
            (GuichetService)context.getBean("guichetService");
        PrimitiveVO primitiveVO = 
            (PrimitiveVO)guichetService.verifierInterditChequier(personneStrc);
        return (primitiveVO);

    }

}
