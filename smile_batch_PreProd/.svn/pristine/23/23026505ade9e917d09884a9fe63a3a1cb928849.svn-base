package com.bna.smile.model.domainecontratcompte.moyensPaiement.traitement;


import java.util.Date;

import com.bna.commun.model.CarteBancaire;
import com.bna.commun.model.DetailOperCarte;
import com.bna.commun.model.DetailOperCarteId;
import com.bna.commun.model.StructureDomaine;
import com.bna.commun.traitements.Traitement;
import com.bna.commun.util.ContextHandler;
import com.bna.smile.model.constant.Constants;
import com.bna.smile.model.domainecommun.service.CRUDservice;
import com.oxia.fwk.context.Context;
import com.oxia.fwk.core.IValueObject;
import com.oxia.fwk.core.ValueObject;


/**
 * Insertion dans DetailOperCarte suite à une modification, ou insertion dans CarteBancaire
 * @author Ramzi
 * @param CarteBancaire: aprés modification de l'etat suite à une nouvelle opération
 * @return CarteBancaire
 * @since 19/06/2007
 * 
 */
public class InsertDetailOperCarteTrt  extends Traitement{
    public InsertDetailOperCarteTrt() {   
    }
    public IValueObject perform(IValueObject vo) throws Exception{
        CarteBancaire carteBancaire = (CarteBancaire)vo;
        DetailOperCarte detailOperCarte = new DetailOperCarte();
        try {
            Context context = ContextHandler.getContext();
            CRUDservice crudService = 
                (CRUDservice)context.getBean("crudservice");
                
            DetailOperCarteId detailOperCarteId = new DetailOperCarteId();
            detailOperCarteId.setCodOperOper(carteBancaire.getDemandeCarte().getTache().getTacheId().getCodOperOper());
            detailOperCarteId.setCodTachTach(carteBancaire.getDemandeCarte().getTache().getTacheId().getCodTachTach());
            detailOperCarteId.setCodBinTcar(carteBancaire.getCarteBancaireId().getCodBinTcar());
            detailOperCarteId.setNumCarbCarb(carteBancaire.getCarteBancaireId().getNumCarbCarb());
            detailOperCarteId.setDatOperDoc(new Date());
                    
            detailOperCarte.setDetailOperCarteId(detailOperCarteId);
            
            detailOperCarte.setCodEtatDoc(carteBancaire.getCodEtatCarb());       
            detailOperCarte.setPersonnel(carteBancaire.getDemandeCarte().getPersonnel());       
            
            crudService.create(detailOperCarte);
                
        } catch (Exception e) {
            com.oxia.fwk.core.Error erreur = new com.oxia.fwk.core.Error();
            erreur.setCode("Technique");            
            erreur.setDescription("InsertDetailOperCarteTrt " + 
                                  e.getMessage());
            detailOperCarte.addError(erreur);
            logger.error("Exception : ",e);
            throw new RuntimeException(e); 
        }
        return detailOperCarte;   
    }
    public void genCroText(ValueObject vo) {
    
    }
    public String getNumeroTache(IValueObject vo){
       return Constants.CODE_RESSOURCE_GENERALE;   
    }
    
    public IValueObject getNumeroDomaine(IValueObject vo){
        CarteBancaire  carteBancaire  = (CarteBancaire)vo;
        StructureDomaine  structureDomaine  = new StructureDomaine();
        structureDomaine.setCodStrcStrc(carteBancaire.getContratCpt().getContratCptId().getCodStrcStrc());
        structureDomaine.setCodDomDomm(Constants.COD_DOM_CONTRATCOMPTE);
        return structureDomaine;
    
    }
}
