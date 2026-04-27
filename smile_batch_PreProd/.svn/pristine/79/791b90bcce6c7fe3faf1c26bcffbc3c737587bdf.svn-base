package com.bna.smile.model.domainecommun.commande;

import com.bna.commun.util.ContextHandler;
import com.bna.smile.model.domainecommun.model.Listes;
import com.bna.smile.model.domainecommun.model.PersonneStrc;
import com.bna.smile.model.domainecommun.service.PersonneService;

import com.bna.smile.model.domainecontratcompte.modificationdonneesclient.model.ParamModificationDonneesVo;
import com.bna.smile.model.domainecontratcompte.modificationdonneesclient.service.ModificationDonneesService;
import com.bna.smile.model.domainecontratcompte.souscriptioncontratcompte.model.ListRgmCatEpargne;
import com.bna.smile.model.domainecontratcompte.souscriptioncontratcompte.service.SouscriptionContratCompteService;

import com.oxia.fwk.context.Context;
import com.oxia.fwk.core.ICommande;
import com.oxia.fwk.core.IValueObject;
import com.oxia.fwk.core.ValueObject;

/** Fichier: ModificationDonneesClientCmd.java version 1.0.0 
 * Copyright(c) 2006 BNA (www.bna.com.tn)
 * Classe: ModificationDonneesClientCmd
 * package: com.bna.smile.model.dommuncommun.commande
 * @auteur : Mdimagh Med Lassaad
 * @since 29/05/07
 */
public class ModificationDonneesClientCmd implements ICommande {
   

    public ModificationDonneesClientCmd() {
    }

    public IValueObject execute(IValueObject vo) {
        Context context = ContextHandler.getContext();
        ParamModificationDonneesVo paramModificationDonneesVo = 
            (ParamModificationDonneesVo)vo;
        ModificationDonneesService modificationDonneesService = 
            (ModificationDonneesService)context.getBean("modificationDonneesService");
        paramModificationDonneesVo = 
                (ParamModificationDonneesVo)modificationDonneesService.modifierDonneesClient(paramModificationDonneesVo);
        return (paramModificationDonneesVo);
    }
}
