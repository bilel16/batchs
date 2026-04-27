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
 * Remplacement une carte par une autre suite à une demande.
 * @author Ramzi
 * @param CarteBancaire 
 * @return CarteBancaire
 * @since 26/07/2007
 * 
 */
public class RemplacementCarteBancaireTrt extends Traitement{
    public RemplacementCarteBancaireTrt() {
    }

    public IValueObject perform(IValueObject vo) throws Exception{
        CarteBancaire  carteBancaireOld  = (CarteBancaire)vo;
        DemandeCarte demandecarteNew = carteBancaireOld.getCarteBancaire().getDemandeCarte();
        
        try {
            Context context = ContextHandler.getContext();
            CRUDservice crudService = 
                (CRUDservice)context.getBean("crudservice");
                

            //modification demande carte 
            crudService.update(demandecarteNew); 
            //sauvgarde de l'historique demande
            InsertDetailOperDemCartTrt insertDetailOperDemCartTrt = new InsertDetailOperDemCartTrt();
            //DetailOperDemCart detailOperDemCart = (DetailOperDemCart) insertDetailOperDemCartTrt.execute(demandecarteNew);
             ValueObject voRetour = (ValueObject)insertDetailOperDemCartTrt.exec(demandecarteNew);
             if (voRetour == null || voRetour.hasError()) {
                    List listErreur = voRetour.getErrors();
                    for (Iterator it = listErreur.iterator(); it.hasNext(); ) {
                        com.oxia.fwk.core.Error erreur = 
                            (com.oxia.fwk.core.Error)it.next();
                        demandecarteNew.addError(erreur);
                        throw new RuntimeException(); 
                    }
             }
            //modification de la carte           
            crudService.update(carteBancaireOld); 
            //sauvgarde de l'historique carte
            InsertDetailOperCarteTrt insertDetailOperCarteTrt = new InsertDetailOperCarteTrt();
            //DetailOperCarte detailOperCarte = (DetailOperCarte) insertDetailOperCarteTrt.execute(carteBancaireOld);
             ValueObject voRetour2 = (ValueObject)insertDetailOperCarteTrt.exec(carteBancaireOld);
             if (voRetour2 == null || voRetour2.hasError()) {
                    List listErreur = voRetour2.getErrors();
                    for (Iterator it = listErreur.iterator(); it.hasNext(); ) {
                        com.oxia.fwk.core.Error erreur = 
                            (com.oxia.fwk.core.Error)it.next();
                        carteBancaireOld.addError(erreur);
                        throw new RuntimeException(); 
                    }
             }
       
        } catch (Exception e) {
                com.oxia.fwk.core.Error erreur=new com.oxia.fwk.core.Error();
                erreur.setCode("Technique");
                erreur.setDescription("RemplacementCarteBancaireTrt "+e.getMessage());;
                carteBancaireOld.addError(erreur);
                logger.error("Exception : ",e);
                throw new RuntimeException(e);      
        }
        return carteBancaireOld;
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
