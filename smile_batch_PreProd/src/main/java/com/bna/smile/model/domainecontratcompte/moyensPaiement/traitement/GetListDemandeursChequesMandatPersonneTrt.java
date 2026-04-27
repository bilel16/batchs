package com.bna.smile.model.domainecontratcompte.moyensPaiement.traitement;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.bna.commun.model.DemandeChequeMandatPersonne;
import com.bna.commun.traitements.Traitement;
import com.bna.commun.util.ContextHandler;
import com.bna.smile.model.constant.Constants;
import com.bna.smile.model.domainecontratcompte.moyensPaiement.model.ListesDemandesCheques;
import com.bna.smile.model.domainecontratcompte.moyensPaiement.model.ParamDemandeCheque;
import com.oxia.fwk.context.Context;
import com.oxia.fwk.core.ICriteria;
import com.oxia.fwk.core.IExpression;
import com.oxia.fwk.core.ISearchEngine;
import com.oxia.fwk.core.IValueObject;
import com.oxia.fwk.core.ValueObject;

/**
 * Classe de traitement :permet  d'avoir la liste des demandeurs cheques mandat personne 
 * @author El arbi hassine
 * @since 20/06/2007
 * 
 */
public class GetListDemandeursChequesMandatPersonneTrt extends Traitement{
    public GetListDemandeursChequesMandatPersonneTrt() {
    }

    public IValueObject perform(IValueObject vo) {
        ParamDemandeCheque paramDemandeCheque = (ParamDemandeCheque)vo;
        ListesDemandesCheques listesDemandesCheques = 
            new ListesDemandesCheques();

        List listeDemandeurs = new ArrayList();
        List listeMandatPersonne = new ArrayList();

        try {
            this.setCroFlag(false);
            Context context = ContextHandler.getContext();
            ISearchEngine searchEngine=(ISearchEngine)Context.getInstance().getSpringContext().getBean("searchEngine");
            ICriteria criteria = searchEngine.createCriteria();
            IExpression expression = searchEngine.createExpression();


            criteria.add(expression.eq("demandeChequeMandatPersonneId.numMandMand", 
                                       paramDemandeCheque.getNumMandMand()));


            List l = 
                searchEngine.find(DemandeChequeMandatPersonne.class, criteria);
            if (l != null && l.size() > 0) {
                // parcourir les demandes de cheque pour extraires les demandeurs
                for (Iterator itDemande = l.iterator(); itDemande.hasNext(); 
                ) {
                    DemandeChequeMandatPersonne demandeChequeMandatPersonne = 
                        (DemandeChequeMandatPersonne)itDemande.next();

                    if (demandeChequeMandatPersonne.getDemandeCheque().getNumDemDchq().equals(paramDemandeCheque.getNumDemande())) {
                        listeMandatPersonne.add(demandeChequeMandatPersonne);
                        listeDemandeurs.add(demandeChequeMandatPersonne.getMandatPersonne().getPersonne());
                    }
                }
                listesDemandesCheques.setListeDemandeursChqMandatPersonne(listeDemandeurs);
                listesDemandesCheques.setListeDemChqMandatPersonne(listeMandatPersonne);
            }


            return (listesDemandesCheques);

        } catch (Exception e) {
            com.oxia.fwk.core.Error erreur = new com.oxia.fwk.core.Error();
            StringBuffer text = 
                new StringBuffer("Erreur dans GetListDemandeursChequesMandatPersonneTrt : ");
            text.append(e.toString());
            erreur.setCode("100");
            erreur.setDescription(text.toString());
            erreur.setKey("GetListDemandeursChequesMandatPersonne");
            listesDemandesCheques.addError(erreur);
            logger.error("Exception : ",e); 
            return (listesDemandesCheques);
        }
    }
    
    public void genCroText(ValueObject vo) {    
    
    }
    
    public String getNumeroTache(IValueObject vo) {
      return (Constants.CODE_RESSOURCE_GENERALE);        
    }  
}
