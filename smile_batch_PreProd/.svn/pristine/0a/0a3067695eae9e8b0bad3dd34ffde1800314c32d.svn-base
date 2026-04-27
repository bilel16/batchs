package com.bna.smile.model.domainecontratcompte.procuration.traitement;

import java.util.Date;

import com.bna.commun.model.Mandat;
import com.bna.commun.traitements.Traitement;
import com.bna.commun.util.ContextHandler;
import com.bna.smile.model.constant.Constants;
import com.bna.smile.model.domainecommun.service.CRUDservice;
import com.bna.smile.model.domainecontratcompte.procuration.dao.MandatDAO;
import com.oxia.fwk.context.Context;
import com.oxia.fwk.core.ISearchEngine;
import com.oxia.fwk.core.IValueObject;
import com.oxia.fwk.core.ValueObject;

public class InsertMandatTrt extends Traitement{
    // public Context context = ContextHandler.getContext();
    // private static final Logger logger = Logger.getLogger(InsertMandatTrt.class);

    public InsertMandatTrt() {
    }
 
    /**
     * Methode permettant d'inserer un Mandat dans la BD
     * @param vo : Mandat
     * @return Mandat
     */
    public IValueObject perform(IValueObject vo) {
    
        Context context = ContextHandler.getContext();
        CRUDservice crudService = (CRUDservice)context.getBean("crudservice");
        ISearchEngine searchEngine=(ISearchEngine)Context.getInstance().getSpringContext().getBean("searchEngine");
        Mandat mandat = (Mandat)vo;
    try{
        /* Garnir le N° de sequence du Mandat */
        MandatDAO mandatDAO = (MandatDAO)context.getBean("mandatDAO");
        mandat.setNumMandMand(mandatDAO.getSequenceMandat());
        mandat.setDatCreMand(new Date());
        
        if (mandat.getCodEtatMand()==null){
            mandat.setCodEtatMand("V");
        }else  mandat.setCodEtatMand(mandat.getCodEtatMand());

        /* insertion du Mandat dans la BD */
        crudService.create(mandat);

        return (mandat);
        }
           catch (Exception e) {
              com.oxia.fwk.core.Error erreur = new com.oxia.fwk.core.Error();
              StringBuffer text = 
                  new StringBuffer("Erreur dans InsertMandatTrt : ");
              text.append(e.toString());
              erreur.setCode("200");
              erreur.setDescription(text.toString());
              erreur.setKey("InsertMandat");
              mandat.addError(erreur);
              logger.error(" *** Erreur lors de  InsertMandatTrt concernant l'agence "+mandat.getCodStrcMand()+" : ", e);
              return (mandat);
          }
    }
    
    public void genCroText(ValueObject vo) {    
    
    }
    
    public String getNumeroTache(IValueObject vo) {
      return (Constants.CODE_RESSOURCE_GENERALE);        
    }
}
