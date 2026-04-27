package com.bna.smile.model.domaineguichet.traitement;

import com.bna.commun.model.MontantMiseDiposition;
import com.bna.commun.model.SeqAgence;
import com.bna.commun.model.SeqAgenceId;
import com.bna.commun.service.CURService;
import com.bna.commun.traitements.Traitement;
import com.bna.commun.util.ContextHandler;
import com.bna.commun.util.StrHandler;

import com.bna.smile.model.constant.Constants;

import com.oxia.fwk.context.Context;
import com.oxia.fwk.core.ISearchEngine;
import com.oxia.fwk.core.IValueObject;
import com.oxia.fwk.core.ValueObject;
import com.oxia.fwk.searchengine.SearchEngine;

import java.util.Date;


/**
 * Classe pour l' insertion d'une operation de versment de mise à dispositon
 * @author Mdimagh Med Lassaad
 * @since 03/12/2007
 * 
 */
public class AjoutVersementMisAdispositionTrt extends Traitement {
    public Context context = ContextHandler.getContext();
    public AjoutVersementMisAdispositionTrt() {
    }
    
    public ValueObject perform (IValueObject vo) {

        MontantMiseDiposition montantMiseDiposition = (MontantMiseDiposition)vo;
        this.setCroFlag(false);      
    try{
        /* insertion de la trace de la mise à disposition dans la BD */
            String strc=StrHandler.lpad(montantMiseDiposition.getStructureByCodEmetStrc().getCodStrcStrc().toString(),'0',3);
            String d=""+(new Date().getYear()+1900);
            String  m=StrHandler.lpad(getNumDossierMAD(montantMiseDiposition).toString(),'0',5);
            
            String numMAD=strc+d+m;
            montantMiseDiposition.getMontantMiseDipositionId().setNumMmadMmad(numMAD);
            montantMiseDiposition.setCodEtatMmad(Constants.COD_ETAT_MISE_DISPOSITION_ATTENTE);
            
        CURService crudService = (CURService)context.getBean("CURService");
        crudService.create(montantMiseDiposition);

        return (montantMiseDiposition);
        }
           catch (Exception e) {
              com.oxia.fwk.core.Error erreur = new com.oxia.fwk.core.Error();
              StringBuffer text = 
                  new StringBuffer("Erreur dans InsertMontantMADTrt : ");
              text.append(e.toString());
              erreur.setCode("300");
              erreur.setDescription(text.toString());
              erreur.setKey("InsertMontantMADTrt");
              montantMiseDiposition.addError(erreur);
              return (montantMiseDiposition);
          }
    }

    public Long getNumDossierMAD(MontantMiseDiposition montantMiseDiposition) {

        CURService crudService = (CURService)context.getBean("CURService");
        ISearchEngine searchEngine = (SearchEngine)context.getBean("searchEngine");

        /* Rechercher la sequence N° MAD relative à la structure donnée */

        SeqAgenceId seqAgenceId=new SeqAgenceId();
        seqAgenceId.setLibSeqSeqa("SEQ_MIS_A_DISPOSITION");
        seqAgenceId.setCodStrcStrc(montantMiseDiposition.getStructureByCodEmetStrc().getCodStrcStrc());

        SeqAgence seqAgence = (SeqAgence)searchEngine.get(SeqAgence.class, seqAgenceId);
    
        long valeur = seqAgence.getNumValSeqa().intValue() + 1;
        seqAgence.setNumValSeqa(new Long(valeur));
        /* MAJ de la sequence */
        crudService.update(seqAgence);
        /* Inserer le N° du ContratCpt*/
        return (new Long(seqAgence.getNumValSeqa().intValue()));
    }

    public void genCroText(ValueObject vo) {
    }
    
}
