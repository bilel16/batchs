package com.bna.smile.model.domainecontratcompte.moyensPaiement.traitement;

import java.util.Iterator;

import com.bna.commun.model.Chequier;
import com.bna.commun.model.DemandeCheque;
import com.bna.commun.model.DetailOperationChequier;
import com.bna.commun.model.DetailOperationChequierId;
import com.bna.commun.model.Personnel;
import com.bna.commun.model.StructureDomaine;
import com.bna.commun.traitements.Traitement;
import com.bna.commun.util.ContextHandler;
import com.bna.commun.util.DateHandler;
import com.bna.commun.util.StrHandler;
import com.bna.smile.model.constant.Constants;
import com.bna.smile.model.domainecommun.service.CRUDservice;
import com.bna.smile.model.domainecontratcompte.moyensPaiement.dao.DemandeChequeDAO;
import com.oxia.fwk.context.Context;
import com.oxia.fwk.core.IValueObject;
import com.oxia.fwk.core.ValueObject;

public class DestructionChequiersTrt extends Traitement{
    
    public DestructionChequiersTrt() {
    }

    /**
     * Methode permettant la destruction des chequiers
     * @param vo : DemandeCheque
     * @return DemandeCheque
     */
    public IValueObject perform(IValueObject vo) {
        Context context = ContextHandler.getContext();
        DemandeCheque demandeCheque = (DemandeCheque)vo;

        /* MAJ de la demande cheque et insertion des chequiers et des detail opérations
         * chequiers dans la BD */
        try {
        if(this.checkClotureJournee()){
            this.setCroFlag(false); 
            CRUDservice crudService = 
                (CRUDservice)context.getBean("crudservice");
            crudService.update(demandeCheque);      
            
            DemandeChequeDAO demandeChequeDAO = 
                (DemandeChequeDAO)context.getBean("demandeChequeDAO");
                        
           
            
            Personnel personnel = new Personnel();
            personnel.setNumMatrUser(demandeCheque.getPersonnel().getNumMatrUser());

            if (demandeCheque.getChequiers() != null && 
                demandeCheque.getChequiers().size() > 0) {
                // insertion des chequiers dans la table chequiers
                for (Iterator it = demandeCheque.getChequiers().iterator(); 
                     it.hasNext(); ) {
                    Chequier chequier = (Chequier)it.next();
                    if (chequier.getChequierId().getNumChqiChqi() != null) {
                        crudService.update(chequier);
                        if (chequier.getCodEtatChqi().equals(Constants.ETAT_CHQ_DETRUIT)) {
                            if (demandeChequeDAO.getDetailOperationChequier(chequier, demandeCheque.getTache().getTacheId().getCodOperOper()).equals(new Long(0))) {
                                // creer l'entité detail operation Chequier
                                DetailOperationChequier detailOperationChequier = 
                                    new DetailOperationChequier();
                                DetailOperationChequierId detailOperationChequierId = 
                                    new DetailOperationChequierId();
                                detailOperationChequierId.setCodOperOper(demandeCheque.getTache().getTacheId().getCodOperOper());
                                detailOperationChequierId.setCodTachTach(demandeCheque.getTache().getTacheId().getCodTachTach());
                                detailOperationChequierId.setNumChqiChqi(chequier.getChequierId().getNumChqiChqi());
                                detailOperationChequierId.setNumDemDchq(chequier.getChequierId().getNumDemDchq());
                                detailOperationChequier.setDetailOperationChequierId(detailOperationChequierId);
                                detailOperationChequier.setTache(demandeCheque.getTache());
                                detailOperationChequier.setPersonnel(personnel);
                                detailOperationChequier.setChequier(chequier);
                                detailOperationChequier.setCodEtatDdc(chequier.getCodEtatChqi());
                                detailOperationChequier.setDatOperDdc(DateHandler.strToDate(DateHandler.dateJour()));
                                crudService.create(detailOperationChequier);
                            }
                        }
                    }
                }
            }
            }else{
                        com.oxia.fwk.core.Error erreur = new com.oxia.fwk.core.Error();
                        StringBuffer text = new StringBuffer("La journée est déja clôturée...");            
                        erreur.setCode("100");
                        erreur.setDescription(text.toString());
                        erreur.setKey("InsertDemandeCheque");
                        demandeCheque.addError(erreur);        
                    }
        } catch (Exception e) {
            com.oxia.fwk.core.Error erreur = new com.oxia.fwk.core.Error();
            StringBuffer text = 
                new StringBuffer("Erreur dans DestructionChequiersTrt : ");
            text.append(e.toString());
            erreur.setCode("100");
            erreur.setDescription(text.toString());
            erreur.setKey("DestructionChequiers");
            demandeCheque.addError(erreur);
            logger.error("Erreur au niveau de l'agence <<" + demandeCheque.getContratCpt().getContratCptId().getCodStrcStrc() + ">>. Exception : ",e);                
            throw new RuntimeException(e);
        }
        return (demandeCheque);
    }
    
    public void genCroText(ValueObject vo) {
    
    }
    
    public String getNumeroTache(IValueObject vo) {
        DemandeCheque demandeCheque = (DemandeCheque)vo;
        
      return (demandeCheque.getTache().getTacheId().getCodOperOper().toString() + 
              StrHandler.lpad(demandeCheque.getTache().getTacheId().getCodTachTach().toString(),'0',2));    
    }
    
    public IValueObject getNumeroDomaine(IValueObject vo){
        StructureDomaine structureDomaine = new StructureDomaine();
        DemandeCheque demandeCheque = (DemandeCheque)vo;
        structureDomaine.setCodDomDomm(Constants.COD_DOM_CONTRATCOMPTE);
        structureDomaine.setCodStrcStrc(demandeCheque.getContratCpt().getContratCptId().getCodStrcStrc());
        return structureDomaine;
    }
}
