package com.bna.smile.model.clotureDomaine.traitement;

import com.bna.commun.model.JourneeStructureDomaine;
import com.bna.commun.model.Mandat;
import com.bna.commun.traitements.Traitement;
import com.bna.commun.util.ContextHandler;
import com.bna.smile.model.constant.Constants;
import com.bna.smile.model.domainecommun.service.CRUDservice;
import com.oxia.fwk.context.Context;
import com.oxia.fwk.core.IValueObject;
import com.oxia.fwk.core.ValueObject;


public class UpdateDomaineTrt extends Traitement {
    public UpdateDomaineTrt() {
    }

    public IValueObject perform(IValueObject vo) {

        Context context = ContextHandler.getContext();
        JourneeStructureDomaine journeeStructureDomaine = 
            (JourneeStructureDomaine)vo;


        try {
            journeeStructureDomaine = 
                    this.getJourneeStructureDomaine(journeeStructureDomaine.getJourneeStructureDomaineId());
            if (this.checkClotureJournee()) {
                if ((journeeStructureDomaine.getCodStatJsd().equals(Constants.ETAT_JSDOM_COURCLO)) || 
                    (journeeStructureDomaine.getCodStatJsd().equals(Constants.ETAT_JSDOM_OUV))) {
                    journeeStructureDomaine.setCodStatJsd(Constants.ETAT_JSDOM_OUV);
                    CRUDservice crudService = 
                        (CRUDservice)context.getBean("crudservice");
                    crudService.update(journeeStructureDomaine);
                } else {
                    com.oxia.fwk.core.Error erreur = 
                        new com.oxia.fwk.core.Error();
                    StringBuffer text = 
                        new StringBuffer("Le domaine est déja clôturée...");
                    erreur.setCode("100");
                    erreur.setDescription(text.toString());
                    erreur.setKey("UpdateDomaineTrt");
                    journeeStructureDomaine.addError(erreur);
                }
            } else {
                com.oxia.fwk.core.Error erreur = new com.oxia.fwk.core.Error();
                StringBuffer text = 
                    new StringBuffer("La journée est déja clôturée...");
                erreur.setCode("100");
                erreur.setDescription(text.toString());
                erreur.setKey("UpdateDomaineTrt");
                journeeStructureDomaine.addError(erreur);

            }

            return (journeeStructureDomaine);
        } catch (Exception e) {
            com.oxia.fwk.core.Error erreur = new com.oxia.fwk.core.Error();
            StringBuffer text = 
                new StringBuffer("Erreur dans UpdateDomaineTrt : ");
            text.append(e.toString());
            erreur.setCode("200");
            erreur.setDescription(text.toString());
            erreur.setKey("UpdateDomaineTrt");
            journeeStructureDomaine.addError(erreur);
            logger.error(" *** Erreur lors de la UpdateDomaineTrt  concernant l'agence " + 
                         ((Mandat)vo).getCodStrcMand() + " : ", e);
            return (journeeStructureDomaine);
        }

    }

    public void genCroText(ValueObject vo) {
    }

    public String getNumeroTache(IValueObject vo) {
        return (Constants.CODE_RESSOURCE_GENERALE);
    }
}
