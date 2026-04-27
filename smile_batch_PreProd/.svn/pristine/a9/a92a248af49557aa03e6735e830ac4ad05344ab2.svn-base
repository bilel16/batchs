package com.bna.smile.model.domainecontratcompte.moyensPaiement.traitement;

import java.util.Iterator;
import java.util.List;

import com.bna.commun.model.DemandeCarte;
import com.bna.commun.model.DemandeCarteMandatPersonne;
import com.bna.commun.model.SeqAgence;
import com.bna.commun.model.SeqAgenceId;
import com.bna.commun.model.StructureDomaine;
import com.bna.commun.traitements.Traitement;
import com.bna.commun.util.ContextHandler;
import com.bna.commun.util.DateHandler;
import com.bna.commun.util.StrHandler;
import com.bna.smile.model.constant.Constants;
import com.bna.smile.model.domainecommun.service.CRUDservice;
import com.bna.smile.model.domainecontratcompte.moyensPaiement.model.DemandeCarteSignataire;
import com.oxia.fwk.context.Context;
import com.oxia.fwk.core.ISearchEngine;
import com.oxia.fwk.core.IValueObject;
import com.oxia.fwk.core.ValueObject;

/**
 * Prise en charge d’une demande de carte donnée.
 * @author Ramzi
 * @param DemandeCarteSignataire
 * @return DemandeCarte
 * @since 21/06/2007
 * 
 */
public class PecDemandeCarteTrt extends Traitement{
    public PecDemandeCarteTrt() {
    }
    //formation du nouveau numéro de carte
    public String getNewNumDemandeCarte(DemandeCarte  demandeCarte) {
        Context context = ContextHandler.getContext();
        CRUDservice crudService = (CRUDservice)context.getBean("crudservice");
        ISearchEngine searchEngine=(ISearchEngine)context.getBean("searchEngine");


        String dateJour = DateHandler.dateJour();
        String d = dateJour.substring(dateJour.length()-4);
        String strc = StrHandler.lpad(demandeCarte.getContratCpt().getContratCptId().getCodStrcStrc().toString(),'0', 3);
        
        SeqAgenceId seqAgenceId = new SeqAgenceId();
        seqAgenceId.setLibSeqSeqa(Constants.LIB_SEQ_SEQA_NumDemDcar);
        seqAgenceId.setCodStrcStrc(Long.valueOf(strc));

        SeqAgence seqAgence = 
            (SeqAgence)searchEngine.get(SeqAgence.class, seqAgenceId);

        long valeur = seqAgence.getNumValSeqa().intValue() + 1;
        seqAgence.setNumValSeqa(new Long(valeur));
        /* MAJ de la sequence */
        crudService.update(seqAgence);
        
        String m = StrHandler.lpad(seqAgence.getNumValSeqa().toString(), 
                            '0', 6);

        String numDem = strc + d + m;
        return numDem;
    }
    
    public IValueObject perform(IValueObject vo) throws Exception{
    
        DemandeCarteSignataire  demandeCarteSignataire  = (DemandeCarteSignataire )vo;
        DemandeCarte  demandeCarte  = demandeCarteSignataire.getDemandeCarte();
        List listSignataires = demandeCarteSignataire.getSignataire();
        
        try {
        
            Context context = ContextHandler.getContext();
            CRUDservice crudService = 
                (CRUDservice)context.getBean("crudservice");
            String numDem=getNewNumDemandeCarte(demandeCarte);
            demandeCarte.setNumDemDcar(numDem);
            
            //insertion de la demande    
            crudService.create(demandeCarte);
            
            //insertion des signataires
            if(listSignataires != null && listSignataires.size()>0){
                Iterator it = listSignataires.iterator();
                DemandeCarteMandatPersonne demandeCarteMandatPersonneRetour;
                for (;it.hasNext();) {                                            
                    demandeCarteMandatPersonneRetour = (DemandeCarteMandatPersonne) it.next();
                    demandeCarteMandatPersonneRetour.getDemandeCarteMandatPersonneId().setNumDemDcar(demandeCarte.getNumDemDcar());;
                    //insertion de chaque signataire   
                    crudService.create(demandeCarteMandatPersonneRetour);
                }          
            }
            
            //sauvgarde de l'historique
            InsertDetailOperDemCartTrt insertDetailOperDemCartTrt = new InsertDetailOperDemCartTrt();
           // DetailOperDemCart detailOperDemCart = (DetailOperDemCart) insertDetailOperDemCartTrt.execute(demandeCarte);
            ValueObject voRetour = (ValueObject)insertDetailOperDemCartTrt.exec(demandeCarte);
            if (voRetour == null || voRetour.hasError()) {
                   List listErreur = voRetour.getErrors();
                   for (Iterator it = listErreur.iterator(); it.hasNext(); ) {
                       com.oxia.fwk.core.Error erreur = 
                           (com.oxia.fwk.core.Error)it.next();
                       demandeCarte.addError(erreur);
                       throw new RuntimeException(); 
                   }
            }
            
        } catch (Exception e) {
                com.oxia.fwk.core.Error erreur=new com.oxia.fwk.core.Error();
                erreur.setCode("Technique");
                erreur.setDescription("PecDemandeCarteTrt "+e.getMessage());;
                demandeCarte.addError(erreur);
                logger.error("Exception : ",e);
                throw new RuntimeException(e);     
        }
        return demandeCarte;
    }
    public void genCroText(ValueObject vo) {
   
    }
    public String getNumeroTache(IValueObject vo){
       /* DemandeCarteSignataire  demandeCarteSignataire  = (DemandeCarteSignataire)vo;
        return demandeCarteSignataire.getDemandeCarte().getTache().getTacheId().getCodOperOper().toString()+
             StrHandler.lpad(demandeCarteSignataire.getDemandeCarte().getTache().getTacheId().getCodTachTach().toString(),'0',2);
        */
        return "120";
    }
    public IValueObject getNumeroDomaine(IValueObject vo){
        DemandeCarteSignataire  demandeCarteSignataire  = (DemandeCarteSignataire )vo;
        StructureDomaine  structureDomaine  = new StructureDomaine();
        structureDomaine.setCodStrcStrc(demandeCarteSignataire.getDemandeCarte().getContratCpt().getContratCptId().getCodStrcStrc());
        structureDomaine.setCodDomDomm(Constants.COD_DOM_CONTRATCOMPTE);
        return structureDomaine;
    
    }
  
}
