package com.bna.smile.model.domainecontratcompte.moyensPaiement.traitement;


import java.util.Date;

import com.bna.commun.model.DemandeCarte;
import com.bna.commun.model.DetailOperDemCart;
import com.bna.commun.model.DetailOperDemCartId;
import com.bna.commun.model.StructureDomaine;
import com.bna.commun.traitements.Traitement;
import com.bna.commun.util.ContextHandler;
import com.bna.smile.model.constant.Constants;
import com.bna.smile.model.domainecommun.service.CRUDservice;
import com.oxia.fwk.context.Context;
import com.oxia.fwk.core.IValueObject;
import com.oxia.fwk.core.ValueObject;


/**
 * Insertion dans DetailOperDemCart suite à une modification, ou insertion dans DemandeCarte
 * @author Ramzi
 * @param DemandeCarte: aprés modification de l'etat suite à une nouvelle opération
 * @return DemandeCarte
 * @since 19/06/2007
 * 
 */
public class InsertDetailOperDemCartTrt extends Traitement{
    public InsertDetailOperDemCartTrt() {   
    }
    public IValueObject perform(IValueObject vo) throws Exception{
        DemandeCarte demandeCarte = (DemandeCarte)vo;
        DetailOperDemCart detailOperDemCart = new DetailOperDemCart();
        try {
            Context context = ContextHandler.getContext();
            CRUDservice crudService = 
                (CRUDservice)context.getBean("crudservice");
                
            DetailOperDemCartId detailOperDemCartId = new DetailOperDemCartId();
            detailOperDemCartId.setCodTachTach(demandeCarte.getTache().getTacheId().getCodTachTach());
            detailOperDemCartId.setCodOperOper(demandeCarte.getTache().getTacheId().getCodOperOper());
            detailOperDemCartId.setNumDemDcar(demandeCarte.getNumDemDcar());              
            detailOperDemCartId.setDatOperDodc(new Date());
            
            detailOperDemCart.setDetailOperDemCartId(detailOperDemCartId);
            detailOperDemCart.setCodEtatDodc(demandeCarte.getCodEtatDcar());       
            detailOperDemCart.setPersonnel(demandeCarte.getPersonnel());
            
            
            crudService.create(detailOperDemCart);
            
                
        } catch (Exception e) {
            com.oxia.fwk.core.Error erreur = new com.oxia.fwk.core.Error();
            erreur.setCode("Technique");            
            erreur.setDescription("InsertDetailOperDemCartTrt " + 
                                  e.getMessage());
            detailOperDemCart.addError(erreur);
            logger.error("Exception : ",e);
            throw new RuntimeException(e); 
        }
        return detailOperDemCart;
       
    }
    public void genCroText(ValueObject vo) {
    
    }
    public String getNumeroTache(IValueObject vo){
       return Constants.CODE_RESSOURCE_GENERALE;   
    }
    public IValueObject getNumeroDomaine(IValueObject vo){
        DemandeCarte  demandeCarte  = (DemandeCarte)vo;
        StructureDomaine  structureDomaine  = new StructureDomaine();
        structureDomaine.setCodStrcStrc(demandeCarte.getContratCpt().getContratCptId().getCodStrcStrc());
        structureDomaine.setCodDomDomm(Constants.COD_DOM_CONTRATCOMPTE);
        return structureDomaine;
    
    }
}
