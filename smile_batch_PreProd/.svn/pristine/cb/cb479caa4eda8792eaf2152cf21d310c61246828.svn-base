package com.bna.smile.model.domainecontratcompte.moyensPaiement.traitement;

import java.util.List;

import com.bna.commun.model.DemandeCheque;
import com.bna.commun.model.Personne;
import com.bna.commun.traitements.Traitement;
import com.bna.smile.model.constant.Constants;
import com.bna.smile.model.domainecommun.model.PersonneStrc;
import com.bna.smile.model.domainecommun.traitement.GetPersonneTrt;
import com.bna.smile.model.domainecontratcompte.moyensPaiement.model.DetailDemandeCheque;
import com.bna.smile.model.domainecontratcompte.moyensPaiement.model.ParamDemandeCheque;
import com.oxia.fwk.context.Context;
import com.oxia.fwk.core.ICriteria;
import com.oxia.fwk.core.IExpression;
import com.oxia.fwk.core.ISearchEngine;
import com.oxia.fwk.core.IValueObject;
import com.oxia.fwk.core.ValueObject;

/**
 * Classe de traitement permettant de retourner l'objet DemandeCheque   
 * @author El arbi hassine
 * @param ParamDemandeCheque
 * @return DemandeCheque
 * @since 11/06/2007
 * 
 */
public class GetDetailDemandeChequeTrt extends Traitement{
    public GetDetailDemandeChequeTrt() {
    }

    public IValueObject perform(IValueObject vo) {
        ParamDemandeCheque paramDemandeCheque = (ParamDemandeCheque)vo;
        DetailDemandeCheque detailDemandeCheque = new DetailDemandeCheque();
        DemandeCheque demandeCheque = new DemandeCheque();

        try {
        
            ISearchEngine searchEngine=(ISearchEngine)Context.getInstance().getSpringContext().getBean("searchEngine");
            ICriteria criteria = searchEngine.createCriteria();
            IExpression expression = searchEngine.createExpression();


            if (paramDemandeCheque.getNumDemande() != null) {
                criteria.add(expression.eq("numDemDchq", 
                                           paramDemandeCheque.getNumDemande()));
            }

            List l = searchEngine.find(DemandeCheque.class, criteria);
            if (l != null && l.size() > 0) {
                demandeCheque = (DemandeCheque)l.get(0);
                detailDemandeCheque.setDemandeCheque(demandeCheque);
                if (demandeCheque.getNumPceDchq() != null && 
                    demandeCheque.getCodTpceDchq() != null) {
                    PersonneStrc personneStrc = new PersonneStrc();
                    personneStrc.setCodTpceTpce(demandeCheque.getCodTpceDchq());
                    personneStrc.setNumPcePers(demandeCheque.getNumPceDchq());
                    GetPersonneTrt getPersonneTrt = new GetPersonneTrt();
                    Personne demandeur = 
                        (Personne)getPersonneTrt.exec(personneStrc);
                    detailDemandeCheque.setDemandeur(demandeur);
                }
            }

        } catch (Exception e) {
            com.oxia.fwk.core.Error erreur = new com.oxia.fwk.core.Error();
            StringBuffer text = 
                new StringBuffer("Erreur dans GetDetailDemandeChequeTrt : ");
            text.append(e.toString());
            erreur.setCode("100");
            erreur.setDescription(text.toString());
            erreur.setKey("GetDetailDemandeCheque");
            detailDemandeCheque.addError(erreur);
            logger.error("Exception : ",e); 
            return (detailDemandeCheque);
        }
        return (detailDemandeCheque);
    }
    
    public void genCroText(ValueObject vo) {    
    
    }
    
    public String getNumeroTache(IValueObject vo) {
      return (Constants.CODE_RESSOURCE_GENERALE);        
    }  
}
