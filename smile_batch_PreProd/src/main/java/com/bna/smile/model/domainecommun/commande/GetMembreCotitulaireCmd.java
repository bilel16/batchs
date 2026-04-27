/** Fichier: GetGroupProfessionCmd.java version 1.0.0 du 19/01/2006
 * Copyright(c) 2006 BNA (www.bna.com.tn)
 * Classe: GetGroupProfessionCmd
 * package: com.bna.smile.model.souscriptionContratCompte.commande
 * Auteur : Ramzi
 */
package com.bna.smile.model.domainecommun.commande;

import com.bna.commun.util.ContextHandler;
import com.bna.smile.model.domainecommun.model.Listes;
import com.bna.smile.model.domainecommun.service.PersonneService;

import com.bna.smile.model.domainecommun.traitement.GetMembreCotitulaireTrt;

import com.oxia.fwk.context.Context;
import com.oxia.fwk.core.ICommande;
import com.oxia.fwk.core.IValueObject;
import com.oxia.fwk.core.ValueObject;

 /** Méthode qui permet de tester si une personne est membre cootitulaire sur
  * un contrat donné, et retourne cet objet dans le cas ou il existe.
  * @author Ramzi
  * @since  07/05/2007
  * @param VO:PersonneStrc contenant type piece, num piece & IdContratCpt:codStrcStrc,codPrdPrd,numCcptCcpt
  * @return VO:Entité cotitulaire
  */
public class GetMembreCotitulaireCmd implements ICommande{
    public GetMembreCotitulaireCmd() {
    }

    public IValueObject execute(IValueObject vo) {
        Context context = ContextHandler.getContext();
        PersonneService personneService = (PersonneService)context.getBean("personneService");
        return (personneService.getMembreCotitulaire(vo));
    }

}
