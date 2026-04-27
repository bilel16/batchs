package com.bna.smile.model.domainecontratcompte.moyensPaiement.traitement;

import java.util.Iterator;
import java.util.List;
import java.util.Set;

import com.bna.commun.model.CarteBancaire;
import com.bna.commun.model.ContratCpt;
import com.bna.commun.model.DemandeCarte;
import com.bna.commun.model.OperationMoyPay;
import com.bna.commun.model.StructureDomaine;
import com.bna.commun.traitements.Traitement;
import com.bna.commun.util.ContextHandler;
import com.bna.commun.util.DateHandler;
import com.bna.commun.util.StrHandler;
import com.bna.smile.model.constant.Constants;
import com.bna.smile.model.domainecommun.service.CRUDservice;
import com.bna.smile.model.domainecontratcompte.moyensPaiement.commande.VerifPossedeTypeCarteCmd;
import com.bna.smile.model.domainecontratcompte.moyensPaiement.model.PersonneTypeCarteCpt;
import com.bna.smile.model.domainecontratcompte.moyensPaiement.model.TypeCarteCpt;
import com.oxia.fwk.context.Context;
import com.oxia.fwk.core.IValueObject;
import com.oxia.fwk.core.ValueObject;

/**
 * Remise une demande de carte.
 * @author Ramzi
 * @param CarteBancaire 
 * @return CarteBancaire
 * @since 26/07/2007
 * 
 */
public class DelivranceCarteBancaireTrt extends Traitement{
    public DelivranceCarteBancaireTrt() {
    }
    private String datVal ;
    private String mntTva = "0";
    private Set listDetailOperMoyPai; 
    private OperationMoyPay  operationMoyPay=new OperationMoyPay();
   


    public IValueObject perform(IValueObject vo) throws Exception{
        CarteBancaire  carteBancaire  = (CarteBancaire)vo;
        try {
            Context context = ContextHandler.getContext();
            CRUDservice crudService = 
                (CRUDservice)context.getBean("crudservice");
                
            //modification de la carte           
            crudService.update(carteBancaire); 
            
            //modification demande carte-->>recue
            DemandeCarte demandeCarte = carteBancaire.getDemandeCarte();
            demandeCarte.setCodEtatDcar(Constants.COD_ETAT_DCAR_CarteRemis);
            demandeCarte.setDatRemiDcar(DateHandler.strToDate(DateHandler.dateJour()));
            crudService.update(demandeCarte); 
            //sauvgarde de l'historique demande
            InsertDetailOperDemCartTrt insertDetailOperDemCartTrt = new InsertDetailOperDemCartTrt();
            //DetailOperDemCart detailOperDemCart = (DetailOperDemCart) insertDetailOperDemCartTrt.execute(demandeCarte);
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
            //sauvgarde de l'historique
            InsertDetailOperCarteTrt insertDetailOperCarteTrt = new InsertDetailOperCarteTrt();
           // DetailOperCarte detailOperCarte = (DetailOperCarte) insertDetailOperCarteTrt.execute(carteBancaire);
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
/*
            //Extraire Condition de banque
            detailOperCarte.setCarteBancaire(carteBancaire);
            chargerConditionBanque(detailOperCarte);
            
            // mettre à jour le solde du contrat:
            ContratCptSold contratCptSold = new ContratCptSold();
            contratCptSold.setContratCpt(carteBancaire.getContratCpt()); 
            Long.valueOf(Double.valueOf(mntTva).longValue());
            Long totalMnt =  Long.valueOf(Double.valueOf(mntTva).longValue()) + calculerCommissions(listDetailOperMoyPai);
            contratCptSold.setSolde(totalMnt);
            contratCptSold.setSens(Constants.COD_SENS_DB);
            UpdateSoldTrt updateSoldTrt = new UpdateSoldTrt();
            ContratCpt ContratCptMaj = (ContratCpt)updateSoldTrt.execute(contratCptSold);      
            
            //Ecriture dans la table operation moyen de payement
            ///Chargement de operation moy pay
            operationMoyPay = chargementOperMoyPay(detailOperCarte, operationMoyPay);
            
            InsertOperationMoyPayTrt insertOperationMoyPayTrt = new InsertOperationMoyPayTrt(); 
            operationMoyPay = (OperationMoyPay)insertOperationMoyPayTrt.execute(operationMoyPay); 
 */                          
            //Genaration Cro
           // this.setCroFlag(false); 
            //return carteBancaire;
       
        } catch (Exception e) {
                com.oxia.fwk.core.Error erreur=new com.oxia.fwk.core.Error();
                erreur.setCode("Technique");
                erreur.setDescription("ReceptionCarteBancaireTrt "+e.getMessage());;
                carteBancaire.addError(erreur); 
                logger.error("Exception : ",e);
                throw new RuntimeException(e);      
        }
        return carteBancaire;
        
    }

    /* Methode qui permet de chercher si un type de carte est deja delivret sur un contrat donnée
    * * * @return boolean
    */

    public boolean possedeTypeCarte(CarteBancaire carteBancaire) {
        try {
           
            PersonneTypeCarteCpt personneTypeCarteCpt = new PersonneTypeCarteCpt();
    
            TypeCarteCpt typeCarteCpt = new TypeCarteCpt();
            typeCarteCpt.setTypeCarte(carteBancaire.getTypeCarte().getCodTcarTcar());
            ContratCpt contratCpt = carteBancaire.getContratCpt();
            typeCarteCpt.setContratCpt(contratCpt); 
            personneTypeCarteCpt.setTypeCarteCpt(typeCarteCpt);
            VerifPossedeTypeCarteCmd verifPossedeTypeCarteCmd = new VerifPossedeTypeCarteCmd();
            CarteBancaire cartBanq = (CarteBancaire) verifPossedeTypeCarteCmd.execute(personneTypeCarteCpt);
            if(cartBanq.getCarteBancaireId()!=null){
                return true;
            }else{
                return false;
            }    
        } catch (Exception e) {
            System.out.println("possedeTypeCarte ---- "+e.getMessage());
            return false;

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
    
 /*   private void chargerConditionBanque(DetailOperCarte detailOperCarte){
        
        
         listDetailOperMoyPai = new HashSet(); 
    try {
        CarteBancaire carteBancaire = detailOperCarte.getCarteBancaire();
        DemandeConditionCmd cmd= new DemandeConditionCmd();
        int codPrdPrd = carteBancaire.getTypeCarte().getProduit().getCodPrdPrd().intValue();
        int codOperOper = detailOperCarte.getDetailOperCarteId().getCodOperOper().intValue();
        int codTpceTpce = carteBancaire.getCodTpceCarb().intValue();
        String numPcePers = carteBancaire.getNumPceCarb();
        float montant = 0;
        int nbUnites;
        if( !possedeTypeCarte(carteBancaire)){
            nbUnites = 1;
        }else{
            nbUnites = 2;
        }
        
        Date dateReference = detailOperCarte.getDatOperDoc();    
        
        DemandeCondition demCond = new DemandeCondition(codPrdPrd, codOperOper, codTpceTpce, numPcePers, montant, nbUnites , dateReference);  
        ListConditionVo v =(ListConditionVo)cmd.execute(demCond);
        System.out.println("OKKKKKKKKKKKK");
             
        if(v.getListConditionBanque().size()==0){ 
            System.out.println("\n \n auccune condition de banque a appliquer \n");
        }else{  
                 for(Iterator itCond = v.getListConditionBanque().iterator();itCond.hasNext();){
                       Condition condition = (Condition) itCond.next();
                       List conditionsBanque = condition.getConditionBanque();
                       
                       for(Iterator it = conditionsBanque.iterator();it.hasNext();){
                             
                              ConditionBanque conditionBanque = (ConditionBanque) it.next();  
                              mntTva = String.valueOf(conditionBanque.getTvaCalculePourCommisions());
                              List detailsConditionBanque = conditionBanque.getDetailConditionBanque();
                              for(Iterator itde = detailsConditionBanque.iterator();itde.hasNext();){
                                   DetailConditionBanque detailConditionBanque = (DetailConditionBanque) itde.next();
                                   DetailOperMoyPaiement detailOperMoyPaiement = new DetailOperMoyPaiement();
                                   NomencElemtCondition nomencElemtCondition   = new NomencElemtCondition();
                                  if(detailConditionBanque.getCodTecdTecd().equals("D")) 
                                     // garnir la date valeur
                                     datVal = detailConditionBanque.getDateValeur(); 
                                   else{ 
                                       nomencElemtCondition.setCodNecdNecd(detailConditionBanque.getCodNecdNecd());
                                       detailOperMoyPaiement.setNomencElemtCondition(nomencElemtCondition);
                                       detailOperMoyPaiement.setCodTypDomp(detailConditionBanque.getCodTecdTecd());
                                       if(detailConditionBanque.getCodTecdTecd().equals("C")){ 
                                         detailOperMoyPaiement.setMontValDomp(new Long(new Double(new Double(StrHandler.strWithoutBlanck(String.valueOf(detailConditionBanque.getValeurCommission()))).doubleValue()).longValue()));                                                                   
                                       }
                                       if(detailConditionBanque.getCodTecdTecd().equals("T")){                                            
                                          detailOperMoyPaiement.setMontValDomp(Long.valueOf(String.valueOf(detailConditionBanque.getValValVael())));
                                       }
                                       listDetailOperMoyPai.add(detailOperMoyPaiement);
                                   }
                                   
                                                                    
                              }
                       }
                 } 
                     
         }
        
      } catch (Exception e) {
              com.oxia.fwk.core.Error erreur=new com.oxia.fwk.core.Error();
              erreur.setCode("Technique");
              erreur.setDescription("chargerConditionBanque -- ReceptionCarteBancaireTrt "+e.getMessage());;
              
      }
       System.out.println("OKKKKKKKKKKKK");
    }  
     
    
    public Long calculerCommissions(Set listDetailOperMoyPai){
        Long sommeCommissions = Long.valueOf(0);
        for (Iterator it = listDetailOperMoyPai.iterator();it.hasNext(); ) {   
           DetailOperMoyPaiement detailOperMoyPaiement = (DetailOperMoyPaiement)it.next();
           sommeCommissions = sommeCommissions + detailOperMoyPaiement.getMontValDomp(); 
        }
        return sommeCommissions;
    }
    private OperationMoyPay chargementOperMoyPay(DetailOperCarte detailOperCarte, OperationMoyPay operationMoyPay){
        
        try{
        operationMoyPay.setContratCpt(detailOperCarte.getCarteBancaire().getContratCpt());
        operationMoyPay.setOperation(detailOperCarte.getCarteBancaire().getDemandeCarte().getOperation());
        operationMoyPay.setDatOperOmp(DateHandler.strToDate(DateHandler.dateJour()));
        operationMoyPay.setCodDemOmp(detailOperCarte.getCarteBancaire().getDemandeCarte().getCodDemDcar());
        TypePiece typePiece = new TypePiece();
        typePiece.setCodTpceTpce(detailOperCarte.getCarteBancaire().getCodTpceCarb());
        operationMoyPay.setTypePieceDemandeur(typePiece);
        operationMoyPay.setNumPcedOmp(detailOperCarte.getCarteBancaire().getNumPceCarb());
        if(datVal != null){
            operationMoyPay.setDatValOmp(DateHandler.strToDate(datVal));
        }
        operationMoyPay.setCodEtatOmp(Constants.COD_VALIDATION);
        operationMoyPay.setCodSensOmp(Constants.COD_SENS_DB);
        operationMoyPay.setPersonnelInitiateur(detailOperCarte.getPersonnel());
        operationMoyPay.setPersonnelValideur(detailOperCarte.getPersonnel());
        //devise commision toujours en dinars
        Devise devise = new Devise();
        devise.setCodDevDev(Constants.COD_DEV_DINAR);
        operationMoyPay.setDevise(devise);
        // insertion du produit vendu
        operationMoyPay.setProduit(detailOperCarte.getCarteBancaire().getTypeCarte().getProduit());
        // insertion du numero (ou referance BNA) du produit vendu
        String numCarteBancaire=detailOperCarte.getCarteBancaire().getCarteBancaireId().getCodBinTcar().toString()+detailOperCarte.getCarteBancaire().getCarteBancaireId().getNumCarbCarb().toString();
        operationMoyPay.setCodRefbOmp(numCarteBancaire);
        operationMoyPay.setStructureInitiatrice(detailOperCarte.getCarteBancaire().getContratCpt().getStructure());
        
        return operationMoyPay;
        } catch (Exception e) {
                System.out.println("chargementOperMoyPay ---- "+e.getMessage());
                return null;

        }
        
    }
*/
   // public void genCroText(ValueObject vo) {
        //    CarteBancaire  carteBancaire  = (CarteBancaire)vo;  
              /* ---------------------- Garniture de la partie FIXE du CRO ----------------------------------- */
         /*     this.setNumRefCro(Long.valueOf(operationMoyPay.getNumOperOmp()));
              this.setLibRefCro("smile.operation_moy_pay");
              this.setDatValCro(new Date());
              this.setCodeStructInitiatrice(operationMoyPay.getStructureInitiatrice().getCodStrcStrc().toString());
              this.setTypeCro("F");
              this.setCodEtatCro(0);
              this.setCodHistCro(1);
              this.setCodeProduit(operationMoyPay.getContratCpt().getContratCptId().getCodPrdPrd().toString());
              this.setOperationId(operationMoyPay.getOperation().getCodOperOper().toString());
              this.setDateOperation(operationMoyPay.getDatOperOmp());
              SimpleDateFormat formater=new SimpleDateFormat("dd/MM/yyyy");
              formater=new SimpleDateFormat("HH:mm:ss");
              String heureString = formater.format(new Date());
              this.setHeureOperation(heureString);                    
              this.setMatriculeUser(operationMoyPay.getPersonnelInitiateur().getNumMatrUser());
              this.setTypeOperationCro("O");
              
                
                 /* ------------------Garniture de la partie VARIABLE du CRO----------------------------------  */
                
           /*     StringBuffer cro=new StringBuffer("");
                
                // contrat Client
                cro.append("COD_STRC_STRC=");
                cro.append(operationMoyPay.getContratCpt().getContratCptId().getCodStrcStrc()+";");
                cro.append("COD_PRD_PRD=");
                cro.append(operationMoyPay.getContratCpt().getContratCptId().getCodPrdPrd()+";");
                cro.append("NUM_CCPT_CCPT=");
                cro.append(operationMoyPay.getContratCpt().getContratCptId().getNumCcptCcpt()+";");
                
                //type piece porteur carte
                cro.append("COD_DEM_TPCE=");
                cro.append(operationMoyPay.getTypePieceDemandeur().getCodTpceTpce()+";");
                
                //num piece porteur carte
                cro.append("NUM_PCED_OMP=");
                cro.append(operationMoyPay.getNumPcedOmp()+";");
                
                //type demandeur/porteur carte : Titulaitre, mandataire...
                cro.append("COD_DEM_OMP=");
                cro.append(operationMoyPay.getCodDemOmp()+";");  
                
                // code produit vendu 
                cro.append("COD_PRD_OMP=");
                cro.append(operationMoyPay.getProduit().getCodPrdPrd()+";");
                
                // num Carte
                cro.append("NUM_CARB_CARB=");
                cro.append(operationMoyPay.getCodRefbOmp()+";");
                
                // montant retrait
                cro.append("MONT_PRET_DCAR=");
                cro.append(carteBancaire.getDemandeCarte().getMontPretDcar() +";");
                
                // montant achat
                cro.append("MONT_PACH_DCAR=");
                cro.append(carteBancaire.getDemandeCarte().getMontPachDcar() +";");
                
                // type autorisation si carte internationale
                cro.append("COD_AUT_DCAR=");
                cro.append(carteBancaire.getDemandeCarte().getCodAutDcar() +";");
                
                
                
                //cro.append("MONT_TVA_OMP=");
                //cro.append(paramDemandeChequeCertifie.getOperationMoyPay().getMontTvaOmp()+"; ");
                
             
                this.setCroText(cro.toString());*/
                
                
                //*/
  //      } 
}
