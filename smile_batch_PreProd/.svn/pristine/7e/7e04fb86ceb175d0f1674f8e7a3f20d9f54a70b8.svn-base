package com.bna.smile.model.domainecontratcompte.moyensPaiement.commande;

import com.bna.commun.util.ContextHandler;
import com.bna.smile.model.domainecontratcompte.moyensPaiement.service.OppositionMoyPaiService;
import com.oxia.fwk.context.Context;
import com.oxia.fwk.core.ValueObject;


/** Fichier: ChargerTypeMoyPaieCmd.java
 * @version 1.0.0 du 07/03/2007
 * Copyright(c) 2007 BNA (www.bna.com.tn)
 * Classe: ChargerTypeMoyPaieCmd
 * package com.bna.smile.model.domainecontratcompte.moyensPaiement.commande;
 * @author : Lamia JERBI
 */
public class ChargerTypeMoyPaieCmd {

    public ChargerTypeMoyPaieCmd() {
    }

    /**
     * methode execute
     * @return vo Objet : ListTypeMoyPaie
     */
    public ValueObject execute(ValueObject vo) {
       
        Context context = ContextHandler.getContext();
      //  Listes listeTypMoyPaie = new Listes();
        OppositionMoyPaiService oppositionMoyPaiService = 
            (OppositionMoyPaiService)context.getBean("oppositionMoyPaiService");
        ValueObject voo= (ValueObject)oppositionMoyPaiService.chargerTypMoyPaie(vo);
        return voo;
    }
}

