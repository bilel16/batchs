package com.bna.smile.model.domainecontratcompte.moyensPaiement.traitement;

import java.util.List;

import com.bna.commun.model.CertificationCheques;
import com.bna.commun.traitements.Traitement;
import com.bna.commun.util.ContextHandler;
import com.bna.smile.model.constant.Constants;
import com.bna.smile.model.domainecontratcompte.moyensPaiement.model.ParamDemandeChequeCertifie;
import com.oxia.fwk.context.Context;
import com.oxia.fwk.core.ICriteria;
import com.oxia.fwk.core.IExpression;
import com.oxia.fwk.core.ISearchEngine;
import com.oxia.fwk.core.IValueObject;
import com.oxia.fwk.core.ValueObject;
import com.oxia.fwk.searchengine.SearchEngine;

/**
 * Classe de traitement permettant de retourner l'objet DemandeCheque   
 * @author El arbi hassine
 * @param ParamDemandeCheque
 * @return DemandeCheque
 * @since 11/06/2007
 * 
 */
public class GetDetailDemandeCertificationChequeTrt extends Traitement{
    public GetDetailDemandeCertificationChequeTrt() {
    }

    public IValueObject perform(IValueObject vo) {
        ParamDemandeChequeCertifie paramDemandeChequeCertifie = 
            (ParamDemandeChequeCertifie)vo;
        CertificationCheques certificationCheques = new CertificationCheques();

        try {
            this.setCroFlag(false);
            Context context = ContextHandler.getContext();
            ISearchEngine searchEngine = 
                (SearchEngine)context.getBean("searchEngine");
            ICriteria criteria = searchEngine.createCriteria();
            IExpression expression = searchEngine.createExpression();


            if (paramDemandeChequeCertifie.getNumCertCchq() != null) {
                criteria.add(expression.eq("numCertCchq", 
                                           paramDemandeChequeCertifie.getNumCertCchq()));
            }

            List l = searchEngine.find(CertificationCheques.class, criteria);
            if (l != null && l.size() > 0) {
                certificationCheques = (CertificationCheques)l.get(0);

            }

        } catch (Exception e) {
            com.oxia.fwk.core.Error erreur = new com.oxia.fwk.core.Error();
            StringBuffer text = 
                new StringBuffer("Erreur dans GetDetailDemandeChequeTrt : ");
            text.append(e.toString());
            erreur.setCode("100");
            erreur.setDescription(text.toString());
            erreur.setKey("GetDetailDemandeCheque");            
            certificationCheques.addError(erreur);
            logger.error("Exception : ",e); 
            return (certificationCheques);
        }
        return (certificationCheques);
    }
    
    public void genCroText(ValueObject vo) {    
    
    }
    
    public String getNumeroTache(IValueObject vo) {
      return (Constants.CODE_RESSOURCE_GENERALE);        
    }  
}
