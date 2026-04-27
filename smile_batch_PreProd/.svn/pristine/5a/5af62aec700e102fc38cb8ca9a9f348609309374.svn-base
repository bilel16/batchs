package com.bna.smile.model.domainecontratcompte.moyensPaiement.traitement;

import java.util.Iterator;
import java.util.List;

import com.bna.commun.model.CarteBancaire;
import com.bna.commun.model.DemandeCarte;
import com.bna.commun.model.StructureDomaine;
import com.bna.commun.traitements.Traitement;
import com.bna.commun.util.ContextHandler;
import com.bna.commun.util.StrHandler;
import com.bna.smile.model.constant.Constants;
import com.bna.smile.model.domainecommun.service.CRUDservice;
import com.oxia.fwk.context.Context;
import com.oxia.fwk.core.IValueObject;
import com.oxia.fwk.core.ValueObject;

/**
 * Recevoir notification modif plafond du DQMRP.
 * @author Ramzi
 * @param CarteBancaire 
 * @return CarteBancaire
 * @since 18/01/2010
 * 
 */
public class ModifPlafondTrt  extends Traitement{
    public ModifPlafondTrt() {
    }

    public IValueObject perform(IValueObject vo) throws Exception{
        CarteBancaire  carteBancaire  = (CarteBancaire)vo;
        try {
            Context context = ContextHandler.getContext();
            CRUDservice crudService = 
                (CRUDservice)context.getBean("crudservice");
                
            //modification demande carte-->>recue
            DemandeCarte demandeCarte = carteBancaire.getDemandeCarte();
            crudService.update(demandeCarte); 

            //modification carte bancaire 
            crudService.update(carteBancaire);    
            //sauvgarde de l'historique
            InsertDetailOperCarteTrt insertDetailOperCarteTrt = new InsertDetailOperCarteTrt();
            //DetailOperCarte detailOperCarte = (DetailOperCarte) insertDetailOperCarteTrt.execute(carteBancaire);
             ValueObject voRetour2 = (ValueObject)insertDetailOperCarteTrt.exec(carteBancaire);
             if (voRetour2 == null || voRetour2.hasError()) {
                    List listErreur = voRetour2.getErrors();
                    for (Iterator it = listErreur.iterator(); it.hasNext(); ) {
                        com.oxia.fwk.core.Error erreur = 
                            (com.oxia.fwk.core.Error)it.next();
                        carteBancaire.addError(erreur);
                        throw new RuntimeException(); 
                    }
             }
       
        } catch (Exception e) {
                com.oxia.fwk.core.Error erreur=new com.oxia.fwk.core.Error();
                erreur.setCode("Technique");
                erreur.setDescription("ReceptionCarteBancaireTrt "+e.getMessage());;
                carteBancaire.addError(erreur);
                logger.error("Exception : ", e);
                throw new RuntimeException(e);       
        }
        return carteBancaire;
    }
    
    public void genCroText(ValueObject vo) {
    
    }
    public String getNumeroTache(IValueObject vo){
        CarteBancaire  carteBancaire  = (CarteBancaire)vo;
        return carteBancaire.getDemandeCarte().getTache().getTacheId().getCodOperOper().toString()+
             StrHandler.lpad(carteBancaire.getDemandeCarte().getTache().getTacheId().getCodTachTach().toString(),'0',2);
    
    }
    public IValueObject getNumeroDomaine(IValueObject vo){
        CarteBancaire  carteBancaire  = (CarteBancaire)vo;
        StructureDomaine  structureDomaine  = new StructureDomaine();
        structureDomaine.setCodStrcStrc(carteBancaire.getContratCpt().getContratCptId().getCodStrcStrc());
        structureDomaine.setCodDomDomm(Constants.COD_DOM_CONTRATCOMPTE);
        return structureDomaine;
    
    }
}
