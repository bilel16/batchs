package com.bna.smile.model.domainecontratcompte.procuration.traitement;

import java.util.Date;

import com.bna.commun.model.MandatPersonne;
import com.bna.commun.traitements.Traitement;
import com.bna.commun.util.ContextHandler;
import com.bna.smile.model.constant.Constants;
import com.bna.smile.model.domainecommun.service.CRUDservice;
import com.oxia.fwk.context.Context;
import com.oxia.fwk.core.IValueObject;
import com.oxia.fwk.core.ValueObject;

public class InsertMandatPersonneTrt extends Traitement{
    //public Context context = ContextHandler.getContext();
    //private static final Logger logger = Logger.getLogger(InsertMandatPersonneTrt.class);

    public InsertMandatPersonneTrt() {
    }

    /**
     * Methode permettant l'insertion d'une MandatPersonne
     *  il y aura une creation d'une nouvelle DetailMandatPersonne
     * @param vo : MandatPersonne
     * @return MandatPersonne
     */
    public IValueObject perform(IValueObject vo) {
    
        Context context = ContextHandler.getContext();
        MandatPersonne mandatPersonne = (MandatPersonne)vo;
    try{
        mandatPersonne.setCodEtatMp("V");
        mandatPersonne.setDatModMp(new Date());//mandatPersonne.getMandat().getDatCreMand());
        /* Insertion du MandatPersonne dans la BD */
        CRUDservice crudService = (CRUDservice)context.getBean("crudservice");
        crudService.create(mandatPersonne);

        /* insertion d'une nouvelle DetailMandatPersonne */
        InsertDetailMandatPersonneTrt insertDetailMandatPersonneTrt = 
            new InsertDetailMandatPersonneTrt();
        mandatPersonne.getDetailMandatPersonnes().add(insertDetailMandatPersonneTrt.exec(mandatPersonne));

        return (mandatPersonne);
        }
           catch (Exception e) {
              com.oxia.fwk.core.Error erreur = new com.oxia.fwk.core.Error();
              StringBuffer text = 
                  new StringBuffer("Erreur dans InsertMandatPersonneTrt : ");
              text.append(e.toString());
              erreur.setCode("200");
              erreur.setDescription(text.toString());
              erreur.setKey("InsertMandatPersonne");
              mandatPersonne.addError(erreur);
              logger.error(" *** Erreur lors de InsertMandatPersonneTrt concernant l'agence "+mandatPersonne.getMandat().getCodStrcMand()+" : ", e);
              return (mandatPersonne);
          }
    }


    public void genCroText(ValueObject vo) {    
    
    }
    
    public String getNumeroTache(IValueObject vo) {
      return (Constants.CODE_RESSOURCE_GENERALE);        
    }
}
