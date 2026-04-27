package com.bna.smile.model.domainecontratcompte.moyensPaiement.traitement;

import java.util.List;

import com.bna.commun.model.CertificationCheques;
import com.bna.commun.traitements.Traitement;
import com.bna.commun.util.ContextHandler;
import com.bna.smile.model.constant.Constants;
import com.bna.smile.model.domainecommun.model.Listes;
import com.bna.smile.model.domainecontratcompte.moyensPaiement.model.ParamDemandeChequeCertifie;
import com.oxia.fwk.context.Context;
import com.oxia.fwk.core.ICriteria;
import com.oxia.fwk.core.IExpression;
import com.oxia.fwk.core.ISearchEngine;
import com.oxia.fwk.core.IValueObject;
import com.oxia.fwk.core.ValueObject;
import com.oxia.fwk.searchengine.SearchEngine;

/**
 * Classe de traitement :permet de donner la liste de toutes les demandes de chèque à certifier par agence et par etat
 
 * @author El arbi hassine
 * @since 04/09/2007
 * 
 */
public class GetListDemandesChequesCertifTrt extends Traitement{

    public GetListDemandesChequesCertifTrt() {
    }

    public IValueObject perform(IValueObject vo) {
        ParamDemandeChequeCertifie paramDemandeChequeCertifie = 
            (ParamDemandeChequeCertifie)vo;

        Listes listeDemandeChqACertifier = new Listes();


        try {
            this.setCroFlag(false);
            Context context = ContextHandler.getContext();
            ISearchEngine searchEngine = 
                (SearchEngine)context.getBean("searchEngine");
            ICriteria criteria = searchEngine.createCriteria();
            IExpression expression = searchEngine.createExpression();


            if (paramDemandeChequeCertifie.getContratPersonne().getContratCptId().getCodStrcStrc() != 
                null) {
                criteria.add(expression.eq("contratCpt.contratCptId.codStrcStrc", 
                                           paramDemandeChequeCertifie.getContratPersonne().getContratCptId().getCodStrcStrc()));
            }

            if (paramDemandeChequeCertifie.getContratPersonne().getContratCptId().getCodPrdPrd() != 
                null) {
                criteria.add(expression.eq("contratCpt.contratCptId.codPrdPrd", 
                                           paramDemandeChequeCertifie.getContratPersonne().getContratCptId().getCodPrdPrd()));
            }

            if (paramDemandeChequeCertifie.getContratPersonne().getContratCptId().getNumCcptCcpt() != 
                null) {
                criteria.add(expression.eq("contratCpt.contratCptId.numCcptCcpt", 
                                           paramDemandeChequeCertifie.getContratPersonne().getContratCptId().getNumCcptCcpt()));
            }

            if (paramDemandeChequeCertifie.getContratPersonne().getPersonneId().getNumPcePers() != 
                null && 
                paramDemandeChequeCertifie.getContratPersonne().getPersonneId().getCodTpceTpce() != 
                null) {
                criteria.add(expression.eq("numPceCchq", 
                                           paramDemandeChequeCertifie.getContratPersonne().getPersonneId().getNumPcePers()));
                criteria.add(expression.eq("codTpceTpce", 
                                           paramDemandeChequeCertifie.getContratPersonne().getPersonneId().getCodTpceTpce()));
            }

            if (paramDemandeChequeCertifie.getNumChqCchq() != null) {
                criteria.add(expression.eq("numChqCchq", 
                                           paramDemandeChequeCertifie.getNumChqCchq()));
            }

            if (paramDemandeChequeCertifie.getDateDebut() != null) {
                criteria.add(expression.ge("datCertCchq", 
                                           paramDemandeChequeCertifie.getDateDebut()));
            }
            if (paramDemandeChequeCertifie.getDateFin() != null) {
                criteria.add(expression.le("datCertCchq", 
                                           paramDemandeChequeCertifie.getDateFin()));
            }

            if (paramDemandeChequeCertifie.getCodStrcStrcDom() != null) {
                criteria.add(expression.eq("codAgdCchq", 
                                           paramDemandeChequeCertifie.getCodStrcStrcDom()));
                criteria.add(expression.eq("contratCpt.contratCptId.codStrcStrc", 
                                           paramDemandeChequeCertifie.getCodStrcStrcDom()));

            }

            if (paramDemandeChequeCertifie.getCodEtatCchq() != null) {
                criteria.add(expression.eq("codEtatCchq", 
                                           paramDemandeChequeCertifie.getCodEtatCchq()));
            }

            if (paramDemandeChequeCertifie.getCodStrcStrcInit() != null) {
                criteria.add(expression.eq("codAgiCchq", 
                                           paramDemandeChequeCertifie.getCodStrcStrcInit()));
            }


            List l = searchEngine.find(CertificationCheques.class, criteria);
            if (l != null && l.size() > 0)
                listeDemandeChqACertifier.setList(l);

        } catch (Exception e) {
            com.oxia.fwk.core.Error erreur = new com.oxia.fwk.core.Error();
            StringBuffer text = 
                new StringBuffer("Erreur dans GetListDemandesChequesCertifTrt : ");
            text.append(e.toString());
            erreur.setCode("100");
            erreur.setDescription(text.toString());
            erreur.setKey("GetListDemandesCheques");
            logger.error("Exception : ",e); 
            listeDemandeChqACertifier.addError(erreur);

        }
        return (listeDemandeChqACertifier);
    }
    
    public void genCroText(ValueObject vo) {    
    
    }
    
    public String getNumeroTache(IValueObject vo) {
      return (Constants.CODE_RESSOURCE_GENERALE);        
    }  
    
}
