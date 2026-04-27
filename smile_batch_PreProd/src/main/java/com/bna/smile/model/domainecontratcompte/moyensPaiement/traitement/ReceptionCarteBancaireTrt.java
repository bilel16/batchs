package com.bna.smile.model.domainecontratcompte.moyensPaiement.traitement;

import java.util.Iterator;
import java.util.List;

import com.bna.commun.model.CarteBancaire;
import com.bna.commun.model.DemandeCarte;
import com.bna.commun.model.StructureDomaine;
import com.bna.commun.model.Tache;
import com.bna.commun.model.TacheId;
import com.bna.commun.traitements.Traitement;
import com.bna.commun.util.ContextHandler;
import com.bna.commun.util.DateHandler;
import com.bna.commun.util.StrHandler;
import com.bna.commun.vo.PrimitiveVO;
import com.bna.smile.model.constant.Constants;
import com.bna.smile.model.domainecommun.service.CRUDservice;
import com.bna.smile.model.domainecontratcompte.moyensPaiement.commande.GetCarteBancaireCmd;
import com.oxia.fwk.context.Context;
import com.oxia.fwk.core.IValueObject;
import com.oxia.fwk.core.ValueObject;

/**
 * Recevoir une demande de carte.
 * @author Ramzi
 * @param CarteBancaire 
 * @return CarteBancaire
 * @since 26/07/2007
 * 
 */
public class ReceptionCarteBancaireTrt  extends Traitement{
    public ReceptionCarteBancaireTrt() {
    }

    public IValueObject perform(IValueObject vo) throws Exception{
        CarteBancaire  carteBancaire  = (CarteBancaire)vo;
        try {
            Context context = ContextHandler.getContext();
            CRUDservice crudService = 
                (CRUDservice)context.getBean("crudservice");
                
            //creation de la carte           
            crudService.create(carteBancaire); 
            
            //modification demande carte-->>remplacée si reception rempl si non recue
            DemandeCarte demandeCarte = carteBancaire.getDemandeCarte();
            ////si cat de reception carte remplacées
            if(demandeCarte.getCodEtatDcar().equals(Constants.COD_ETAT_DCAR_DemandeRemplValide)){
                //remplacement de l'ancienne carte si carte bien confectionnée 
                if(carteBancaire.getCodEtatCarb().equals(Constants.COD_ETAT_CARB_CarteMalConfect)){
                    demandeCarte.setCodEtatDcar(Constants.COD_ETAT_DCAR_CarteRecu);
                }else{
                    demandeCarte.setCodEtatDcar(Constants.COD_ETAT_DCAR_CarteRemplacee);
                    remplacerCarteBancaire(carteBancaire);
                }
            }else{
                demandeCarte.setCodEtatDcar(Constants.COD_ETAT_DCAR_CarteRecu);
            }
            
            demandeCarte.setDatRecpDcar(DateHandler.strToDate(DateHandler.dateJour()));
            demandeCarte.setNumCarDcar(Long.valueOf(carteBancaire.getCarteBancaireId().getCodBinTcar().toString() + StrHandler.lpad(carteBancaire.getCarteBancaireId().getNumCarbCarb().toString(),'0',10)));
            crudService.update(demandeCarte); 
            //sauvgarde de l'historique demande
            InsertDetailOperDemCartTrt insertDetailOperDemCartTrt = new InsertDetailOperDemCartTrt();
           // DetailOperDemCart detailOperDemCart = (DetailOperDemCart) insertDetailOperDemCartTrt.exec(demandeCarte);
            ValueObject voRetour = (ValueObject)insertDetailOperDemCartTrt.exec(demandeCarte);
            if (voRetour == null || voRetour.hasError()) {
                   List listErreur = voRetour.getErrors();
                   for (Iterator it = listErreur.iterator(); it.hasNext(); ) {
                       com.oxia.fwk.core.Error erreur = 
                           (com.oxia.fwk.core.Error)it.next();
                       carteBancaire.addError(erreur);
                       throw new RuntimeException(); 
                   }
            }
            
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

    private void remplacerCarteBancaire(CarteBancaire carteBancaire) {
        DemandeCarte demandeCarte = carteBancaire.getDemandeCarte();
        //recherche de la carte ancienne
        PrimitiveVO voCarte = new PrimitiveVO();
        voCarte.setVString(demandeCarte.getNumCarDcar().toString());
        GetCarteBancaireCmd getCarteBancaireCmd = new GetCarteBancaireCmd();
        CarteBancaire carteBancaireOld = (CarteBancaire)getCarteBancaireCmd.execute(voCarte);
        //maj de l'ancienne carte
        carteBancaireOld.setCarteBancaire(carteBancaire);
        carteBancaireOld.setDatRempCarb(DateHandler.timeJour());
        carteBancaireOld.setLibRempCarb(demandeCarte.getLibRempDcar());
        carteBancaireOld.setCodEtatCarb(Constants.COD_ETAT_CARB_CarteRemplacee);
        carteBancaireOld.setDatOperCarb(DateHandler.timeJour());
        
        //envoi des information pour insertion dans la table historique
        ///probleme sur nom possibilité de modifier PK d'un objet ds la même session hibernate
        Tache tache = new Tache();
        TacheId tacheId = new TacheId();
        tacheId.setCodOperOper(Constants.COD_OPER_OPER_RemplaceeCarte);
        tacheId.setCodTachTach(Constants.COD_TACH_TACH_RemplaceeCarte);
        tache.setTacheId(tacheId);
        
        carteBancaireOld.getDemandeCarte().setTache(tache);
        carteBancaireOld.getDemandeCarte().setPersonnel(demandeCarte.getPersonnel());
        
        //sauvgarde de l'historique
        InsertDetailOperCarteTrt insertDetailOperCarteTrt = new InsertDetailOperCarteTrt();
        //DetailOperCarte detailOperCarte = (DetailOperCarte) insertDetailOperCarteTrt.execute(carteBancaire);
         ValueObject voRetour3 = (ValueObject)insertDetailOperCarteTrt.exec(carteBancaireOld);
         if (voRetour3 == null || voRetour3.hasError()) {
                List listErreur = voRetour3.getErrors();
                for (Iterator it = listErreur.iterator(); it.hasNext(); ) {
                    com.oxia.fwk.core.Error erreur = 
                        (com.oxia.fwk.core.Error)it.next();
                    carteBancaire.addError(erreur);
                    throw new RuntimeException(); 
                }
         }
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
