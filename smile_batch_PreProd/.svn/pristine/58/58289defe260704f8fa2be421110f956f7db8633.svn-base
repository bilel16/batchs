package com.bna.smile.model.domainecontratcompte.procuration.traitement;

import java.util.Date;

import com.bna.commun.model.Mandat;
import com.bna.commun.traitements.Traitement;
import com.bna.commun.util.ContextHandler;
import com.bna.smile.model.constant.Constants;
import com.bna.smile.model.domainecommun.service.CRUDservice;
import com.oxia.fwk.context.Context;
import com.oxia.fwk.core.IValueObject;
import com.oxia.fwk.core.ValueObject;

public class UpdateMandatTrt extends Traitement{
    // public Context context = ContextHandler.getContext();
    // private static final Logger logger = Logger.getLogger(UpdateMandatTrt.class);

    public UpdateMandatTrt() {
    }

    /**
     * Methode permettant la MAJ un Mandat dans la BD
     * @param vo : Mandat
     * @return Mandat
     */
    public IValueObject perform(IValueObject vo) {

        Context context = ContextHandler.getContext();
        //Mandat mandat = (Mandat)vo;
    try{
        ((Mandat)vo).setDatModMand(new Date());

        /* MAJ du Mandat dans la BD */
        CRUDservice crudService = (CRUDservice)context.getBean("crudservice");
        crudService.update((Mandat)vo);
        return (vo);
    }
        catch (Exception e) {
            com.oxia.fwk.core.Error erreur = new com.oxia.fwk.core.Error();
            StringBuffer text = new StringBuffer("Erreur dans UpdateMandatTrt : ");
            text.append(e.toString());
            erreur.setCode("200");
            erreur.setDescription(text.toString());
            erreur.setKey("UpdateMandat");
            vo.addError(erreur);
            logger.error(" *** Erreur lors de la UpdateMandatTrt du mandat concernant l'agence "+((Mandat)vo).getCodStrcMand()+" : ", e);
            return (vo);
        }

    }
    
    
    public void genCroText(ValueObject vo) {    
    
    }
    
    public String getNumeroTache(IValueObject vo) {
      return (Constants.CODE_RESSOURCE_GENERALE);        
    }
    
}
