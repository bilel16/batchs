package com.bna.smile.model.domainecontratcompte.souscriptioncontratcompte.traitement;


import com.bna.commun.model.Personne;
import com.bna.commun.model.Signature;
import com.bna.commun.model.SignatureId;
import com.bna.smile.model.domainecommun.model.ContratPersonne;
import com.bna.smile.model.domainecommun.traitement.GetPersonneTrt;
import com.oxia.fwk.context.Context;
import com.oxia.fwk.core.ISearchEngine;
import com.oxia.fwk.core.ValueObject;

public class GetSignaturesTrt {
   

    public GetSignaturesTrt() {
    }

    /**
     * méthode d'extraction d'un objet signature en prend en argument 
     * le contrat + personne
     * @param vo ContratPersonne 
     * @return vo Signature
     * @author :Ramzi
     */
    public ValueObject execute(ValueObject vo) {
            
            ContratPersonne contratPersonne = (ContratPersonne)vo;
            Signature  signature = null;
            SignatureId signatureID = new SignatureId();
            signatureID.setCodStrcStrc(contratPersonne.getContratCptId().getCodStrcStrc());
            signatureID.setCodPrdPrd(contratPersonne.getContratCptId().getCodPrdPrd());
            signatureID.setNumCcptCcpt(contratPersonne.getContratCptId().getNumCcptCcpt());
            //recherche num_seq_pers
            GetPersonneTrt getPersonneTrt = new GetPersonneTrt();
            Personne personne = 
                (Personne)getPersonneTrt.exec(contratPersonne.getPersonneId());
            signatureID.setNumSeqPers(personne.getNumSeqPers());
            
            ISearchEngine searchEngine=(ISearchEngine)Context.getInstance().getSpringContext().getBean("searchEngine");

            signature = (Signature) searchEngine.get(Signature.class, signatureID);
           
           return signature; 
            
    }
}
