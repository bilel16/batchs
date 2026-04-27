package com.bna.smile.model.domainecontratcompte.procuration.traitement;

import java.util.Date;

import org.springframework.orm.hibernate3.HibernateTemplate;

import com.bna.commun.model.MandatPersonne;
import com.bna.commun.traitements.Traitement;
import com.bna.commun.util.ContextHandler;
import com.bna.smile.model.constant.Constants;
import com.bna.smile.model.domainecommun.service.CRUDservice;
import com.oxia.fwk.context.Context;
import com.oxia.fwk.core.ISearchEngine;
import com.oxia.fwk.core.IValueObject;
import com.oxia.fwk.core.ValueObject;

public class UpdateMandatPersonneTrt extends Traitement{
   // public Context context = ContextHandler.getContext();
   // private static final Logger logger = Logger.getLogger(UpdateMandatPersonneTrt.class);

    public UpdateMandatPersonneTrt() {
    }

    /**
     * Methode permettant la MAJ d'une MandatPersonne
     * s'il ya changement d'etat (suppression du mandataire) il y aura
     * une creation d'une nouvelle DetailMandatPersonne et la MAJ de la
     * derniere DetailMandatPersonne (date fin = date systeme)
     * @param vo : MandatPersonne
     * @return MandatPersonne
     */
    public IValueObject perform(IValueObject vo) {

        Context context = ContextHandler.getContext();
        MandatPersonne mandatPersonne = (MandatPersonne)vo;

    try{
        //ISearchEngine searchEngine = (SearchEngine)context.getBean("searchEngine");
        ISearchEngine searchEngine=(ISearchEngine)Context.getInstance().getSpringContext().getBean("searchEngine");

        MandatPersonne mp = (MandatPersonne)searchEngine.get(MandatPersonne.class,mandatPersonne.getMandatPersonneId());
        HibernateTemplate  hibernateTemplate = (HibernateTemplate) context.getBean("hibernateTemplate");
        hibernateTemplate.evict(mp);

        /* MAJ du MandatPersonne dans la BD */
        mandatPersonne.setDatModMp(new Date());
        CRUDservice crudService = (CRUDservice)context.getBean("crudservice");
        crudService.update(mandatPersonne);

        /* s'il ya changement d'etat (suppression du mandataire) */
        if (!mandatPersonne.getCodEtatMp().equalsIgnoreCase(mp.getCodEtatMp())) {
            InsertDetailMandatPersonneTrt insertDetailMandatPersonneTrt = 
                new InsertDetailMandatPersonneTrt();
            mandatPersonne.getDetailMandatPersonnes().add(insertDetailMandatPersonneTrt.exec(mandatPersonne));
        }

        return (mandatPersonne);
    }
        catch (Exception e) {
            com.oxia.fwk.core.Error erreur = new com.oxia.fwk.core.Error();                    
            StringBuffer text = new StringBuffer("Erreur dans UpdateMandatPersonneTrt : ");
            text.append(e.toString());
            erreur.setCode("200");
            erreur.setDescription(text.toString());
            erreur.setKey("UpdateMandatPersonne");
            mandatPersonne.addError(erreur);
            logger.error(" *** Erreur lors de la UpdateMandatPersonneTrt concernant l'agence "+mandatPersonne.getMandat().getCodStrcMand()+" : ", e);
            return (mandatPersonne);
        }

    }
    
    
    public void genCroText(ValueObject vo) {    
    
    }
    
    public String getNumeroTache(IValueObject vo) {
      return (Constants.CODE_RESSOURCE_GENERALE);        
    }
    
}
