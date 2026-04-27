package com.bna.smile.model.domainecontratcompte.procuration.traitement;

import java.util.Date;
import java.util.List;

import com.bna.commun.model.DetailMandatPersonne;
import com.bna.commun.model.MandatPersonne;
import com.bna.commun.traitements.Traitement;
import com.bna.commun.util.ContextHandler;
import com.bna.smile.model.constant.Constants;
import com.bna.smile.model.domainecommun.service.CRUDservice;
import com.oxia.fwk.context.Context;
import com.oxia.fwk.core.ICriteria;
import com.oxia.fwk.core.IExpression;
import com.oxia.fwk.core.ISearchEngine;
import com.oxia.fwk.core.IValueObject;
import com.oxia.fwk.core.ValueObject;

public class UpdateDetailMandatPersonneTrt extends Traitement{
   // public Context context = ContextHandler.getContext();
   // private static final Logger logger = Logger.getLogger(UpdateDetailMandatPersonneTrt.class);

    public UpdateDetailMandatPersonneTrt() {
    }

    /**
         * methode permettant la MAJ de la derniere DetailMandatPersonne
         * d'une MandatPersonne donnée
         * @param vo : MandatPersonne
         * @return DetailMandatPersonne
         */
    public IValueObject perform(IValueObject vo) {
    
        Context context = ContextHandler.getContext();
        DetailMandatPersonne dmp =new DetailMandatPersonne();
    try{
       // ISearchEngine searchEngine =(SearchEngine)context.getBean("searchEngine");
        ISearchEngine searchEngine=(ISearchEngine)Context.getInstance().getSpringContext().getBean("searchEngine");

        ICriteria criteria = searchEngine.createCriteria();
        IExpression expression = searchEngine.createExpression();

        MandatPersonne mandatPersonne = (MandatPersonne)vo;

        criteria.add(expression.eq("detailMandatPersonneId.numSeqPers", 
                                   mandatPersonne.getMandatPersonneId().getNumSeqPers()));
        criteria.add(expression.eq("detailMandatPersonneId.numMandMand", 
                                   mandatPersonne.getMandatPersonneId().getNumMandMand()));
        criteria.add(expression.isNull("datFinDmp"));


   /*     criteria.add(expression.eq("detailMandatPersonneId.mandatPersonne.mandatPersonneId.personne.numSeqPers", 
                                   mandatPersonne.getMandatPersonneId().getNumSeqPers()));
        criteria.add(expression.eq("detailMandatPersonneId.mandatPersonne.mandatPersonneId.mandat.numMandMand", 
                                   mandatPersonne.getMandatPersonneId().getNumMandMand()));
        criteria.add(expression.isNull("datFinDmp"));
    */
        List l = searchEngine.find(DetailMandatPersonne.class, criteria);
        if (l != null && l.size() > 0) {
            dmp = (DetailMandatPersonne)l.get(0);
            dmp.setDatFinDmp(new Date());
            CRUDservice crudService = 
                (CRUDservice)context.getBean("crudservice");
            crudService.update(dmp);
            return (dmp);
        } else
            return null;
    }
           catch (Exception e) {
              com.oxia.fwk.core.Error erreur = new com.oxia.fwk.core.Error();
              StringBuffer text = 
                  new StringBuffer("Erreur dans UpdateDetailMandatPersonneTrt : ");
              text.append(e.toString());
              erreur.setCode("200");
              erreur.setDescription(text.toString());
              erreur.setKey("UpdateDetailMandatPersonne");
              dmp.addError(erreur);
              logger.error(" *** Erreur lors de  UpdateDetailMandatPersonneTrt concernant l'agence "+dmp.getMandatPersonne().getMandat().getCodStrcMand()+" : ", e);
              return (dmp);
          }
    }
    
    
    public void genCroText(ValueObject vo) {    
    
    }
    
    public String getNumeroTache(IValueObject vo) {
      return (Constants.CODE_RESSOURCE_GENERALE);        
    }
}
