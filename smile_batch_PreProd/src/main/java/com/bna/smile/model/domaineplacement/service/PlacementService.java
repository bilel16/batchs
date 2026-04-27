package com.bna.smile.model.domaineplacement.service;


import com.bna.commun.model.AvancRembLiquid;
import com.bna.smile.model.domaineplacement.model.ParamContratPlacement;
import com.bna.smile.model.domaineplacement.model.ParamDemandeDecision;
import com.bna.smile.model.domaineplacement.traitement.AbonnementPlacementAgenceTrt;
import com.bna.smile.model.domaineplacement.traitement.CreateContratPlacementTrt;
import com.bna.smile.model.domaineplacement.traitement.GetAvancRembLiquidByIdTrt;
import com.bna.smile.model.domaineplacement.traitement.GetAvgTMMbetweenDatesTrt;
import com.bna.smile.model.domaineplacement.traitement.GetDetailsOperationPlacTrt;
import com.bna.smile.model.domaineplacement.traitement.ValiderPECAvancePlacementTrt;
import com.bna.smile.model.domaineplacement.traitement.GetContratPlacementTrt;

import com.bna.smile.model.domaineplacement.traitement.GetDemandeDecisionTrt;
import com.bna.smile.model.domaineplacement.traitement.GetDetailBcTrt;
import com.bna.smile.model.domaineplacement.traitement.GetDetailsOperationPlacTrt;
import com.bna.smile.model.domaineplacement.traitement.GetInteretServiByIdTrt;
import com.bna.smile.model.domaineplacement.traitement.GetListAbonnementsInteretsTrt;
import com.bna.smile.model.domaineplacement.traitement.GetListAbonnementsTrt;
import com.bna.smile.model.domaineplacement.traitement.GetListAvancRembLiquidByEtatTrt;
import com.bna.smile.model.domaineplacement.traitement.GetListBcRecupereTrt;
import com.bna.smile.model.domaineplacement.traitement.GetListContratsPlacementTrt;
import com.bna.smile.model.domaineplacement.traitement.GetListDemandesDecisionTrt;

import com.bna.smile.model.domaineplacement.traitement.GetListInteretServiTrt;
import com.bna.smile.model.domaineplacement.traitement.GetParamBonCaisseTrt;
import com.bna.smile.model.domaineplacement.traitement.GetSituationMensuelleByCategoriePersonneTrt;
import com.bna.smile.model.domaineplacement.traitement.GetSituationMensuelleByClientTrt;
import com.bna.smile.model.domaineplacement.traitement.GetSituationMensuelleByStructureTrt;
import com.bna.smile.model.domaineplacement.traitement.GetSituationMensuelleIntByCltTrt;
import com.bna.smile.model.domaineplacement.traitement.GetSituationMensuelleIntByStrcTrt;
import com.bna.smile.model.domaineplacement.traitement.GetSituationMensuelleInteretByCategPersTrt;
import com.bna.smile.model.domaineplacement.traitement.InsertMandPersOperPlacTrt;
//import com.bna.smile.model.domaineplacement.traitement.InteretServiAgenceTrt;
import com.bna.smile.model.domaineplacement.traitement.InteretServiAgenceTrt;
import com.bna.smile.model.domaineplacement.traitement.LiquidationAEcheanceTrt;

import com.bna.smile.model.domaineplacement.traitement.PecLiquidationAnticipeTrt;

import com.bna.smile.model.domaineplacement.traitement.LiquidationAvancesPlacementTrt;

import com.bna.smile.model.domaineplacement.traitement.ValiderDdeDecisionTrt;

import com.bna.smile.model.domaineplacement.traitement.ValiderMajDdeDecisionTrt;

import com.bna.smile.model.domaineplacement.traitement.PecSouscriptionPlacementTrt;

import com.bna.smile.model.domaineplacement.traitement.RecapMovPlacementTrt;
import com.bna.smile.model.domaineplacement.traitement.RejeterLiquidationAnticipeTrt;
import com.bna.smile.model.domaineplacement.traitement.TraitementAbonPlacementTrt;
import com.bna.smile.model.domaineplacement.traitement.TraitementLiquidationTrt;
import com.bna.smile.model.domaineplacement.traitement.UpdateAvanceRembLiquTrt;
import com.bna.smile.model.domaineplacement.traitement.UpdateContratPlacementTrt;
import com.bna.smile.model.domaineplacement.traitement.ValiderAvancePlacementTrt;

import com.bna.smile.model.domaineplacement.traitement.ValiderLiquidationPlacementTrt;
import com.bna.smile.model.domaineplacement.traitement.ValiderRecuperationTrt;
import com.bna.smile.model.domaineplacement.traitement.ValiderRenouvellementPlacementTrt;
import com.bna.smile.model.domaineplacement.traitement.ValiderSouscriptionPlacementTrt;

import com.bna.smile.model.domaineplacement.traitement.VerifBCTrt;

import com.oxia.fwk.beans.service.BasicService;
import com.oxia.fwk.core.IValueObject;



public class PlacementService extends BasicService{

private GetContratPlacementTrt getContratPlacementTrt;
private ValiderDdeDecisionTrt validerDdeDecisionTrt;
private ValiderMajDdeDecisionTrt validerMajDdeDecisionTrt;
private ValiderPECAvancePlacementTrt validerPECAvancePlacementTrt;
private GetListDemandesDecisionTrt getListDemandesDecisionTrt;
private GetDemandeDecisionTrt getDemandeDecisionTrt;
private GetListContratsPlacementTrt getListContratsPlacementTrt;
private InsertMandPersOperPlacTrt insertMandPersOperPlacTrt;
private GetListAvancRembLiquidByEtatTrt getListAvancRembLiquidByEtatTrt;
private GetAvancRembLiquidByIdTrt getAvancRembLiquidByIdTrt;
private ValiderAvancePlacementTrt validerAvancePlacementTrt;
private GetParamBonCaisseTrt getParamBonCaisseTrt;
private GetSituationMensuelleByClientTrt getSituationMensuelleByClientTrt;
private PecLiquidationAnticipeTrt pecLiquidationAnticipeTrt;
private GetSituationMensuelleByStructureTrt getSituationMensuelleByStructureTrt;
private GetSituationMensuelleByCategoriePersonneTrt getSituationMensuelleByCategoriePersonneTrt;
private RecapMovPlacementTrt recapMovPlacementTrt ;
private GetListAbonnementsTrt getListAbonnementsTrt;
private VerifBCTrt verifBCTrt;


    public PlacementService(){
    }

    /**
     * Methode permettant de rechercher un contrat de placement.
     * @param  ContratPlacement
     * @return ContratPlacement
     */
    public IValueObject getContratPlacement(IValueObject vo) {
        return (getContratPlacementTrt.exec(vo));
    }
     /**
      * Methode permettant de valider une Demande deDecision.
      * @param  DemandeDecision
      * @return DemandeDecision
      */
     public IValueObject validerDdeDecision(IValueObject vo) {
         return (validerDdeDecisionTrt.exec(vo));
     }
  
/**
      * Methode permettant de valider une souscription.
      * @param  ContratPlacement
      * @return ContratPlacement
      */
     public IValueObject pecSouscriptionPlacement(IValueObject vo) {
         PecSouscriptionPlacementTrt pecSouscriptionPlacementTrt= new PecSouscriptionPlacementTrt();
         return (pecSouscriptionPlacementTrt.exec(vo));
     }
    /**
          * Methode permettant de créer un contrat placement
          * @param  ContratPlacement
          * @return ContratPlacement
          */
         public IValueObject createContratPlacement(IValueObject vo) {
             CreateContratPlacementTrt createContratPlacementTrt= new CreateContratPlacementTrt();
             return (createContratPlacementTrt.exec(vo));
         }
     /**
      * Methode permettant de valider la mise à jour d'une Demande de Decision.
      * @param  DemandeDecision
      * @return DemandeDecision
      */
     public IValueObject validerMajDdeDecision(IValueObject vo) {
       return (validerMajDdeDecisionTrt.exec(vo));
     }
     /**
           * Methode permettant de valider une avance(ParamContratPlacement).
           * @param  AvancRembLiquid
           * @return AvancRembLiquid
           */
           public IValueObject avancePlacement(IValueObject vo) {
               return (validerPECAvancePlacementTrt.exec(vo));
             }
     /**
      * Methode permettant de rechercher les Demandes deDecision.
      * @param  ParamDemandeDecision
      * @return Liste
      */
     public IValueObject getListDemandesDecisionPlacement(IValueObject vo) {
         return (getListDemandesDecisionTrt.exec(vo));
     }

     /**
      * Methode permettant de rechercher une Demande de Decision.
      * @param  DemandeDecision
      * @return DemandeDecision
      */
     public IValueObject getDemandeDecisionPlacement(IValueObject vo) {
         return (getDemandeDecisionTrt.exec(vo));
     }
     /**
          * Methode permettant de rechercher les Contrats de Placement.
          * @param  ParamDemandeDecision
          * @return Liste
          */
         public IValueObject getListContratsPlacement(IValueObject vo) {
             return (getListContratsPlacementTrt.exec(vo));
         }
    /**
         * Methode d'inserer MandPersOperPlac.
         * @param  MandPersOperPlac
         * @return MandPersOperPlac
         */
        public IValueObject insertMandPersOperPlac(IValueObject vo) {
            return (insertMandPersOperPlacTrt.exec(vo));
        }
    /**
         * Methode pour vérifier le numéro Bon de caisse.
         * @param  ParamBonCaisse
         * @return ParamBonCaisse
         */
        public IValueObject verifNumBonCaisse(IValueObject vo) {
              return (getParamBonCaisseTrt.exec(vo));
        }
    /**
         * valider la souscription à un contrat placement
         * @param  ContratPlacement
         * @return ContratPlacement
         */
        public IValueObject validerSouscriptionCpla(IValueObject vo) {
            ValiderSouscriptionPlacementTrt validerSouscriptionPlacementTrt = new ValiderSouscriptionPlacementTrt();
              return (validerSouscriptionPlacementTrt.exec(vo));
        }
        
        
    /**
          * Methode permettant de prendre en charge une liquidation anticipee(ParamContratPlacement).
          * @param  AvancRembLiquid
          * @return AvancRembLiquid
          */
          public IValueObject liquidationAnticipePlacement(IValueObject vo) {
              return (pecLiquidationAnticipeTrt.exec(vo));
          }
            
            
    /**
         * retourne DetailsOperationPlacement
         * @param  DetailsOperationPlacement
         * @return DetailsOperationPlacement
         */
        public IValueObject getDetailsOperationPlac(IValueObject vo) {
              GetDetailsOperationPlacTrt getDetailsOperationPlacTrt = new GetDetailsOperationPlacTrt();
              return (getDetailsOperationPlacTrt.exec(vo));
        }

        
    /**
         * validation d'une liquidation anticipée
         * @param  AvancRembLiquid
         * @return AvancRembLiquid
         */
        public IValueObject validerLiquidationAnticipeePlacement(IValueObject vo) {
          ValiderLiquidationPlacementTrt validerLiquidationPlacementTrt = new ValiderLiquidationPlacementTrt();
          return (validerLiquidationPlacementTrt.exec(vo));
        }

    /**
         * validation d'une liquidation anticipée Lors de la Liquidation a Echéance (LAE)
         * @param  AvancRembLiquid
         * @return AvancRembLiquid
         */
        public IValueObject validerLiquidationAnticipeePlacementLAE(IValueObject vo) {
          ValiderLiquidationPlacementTrt validerLiquidationPlacementTrt = new ValiderLiquidationPlacementTrt();
          validerLiquidationPlacementTrt.setVerifDomaine(false);
          return (validerLiquidationPlacementTrt.exec(vo));
        }
    
    /**
          * Methode permettant de retourner la liste des interets servis pour un contrat de placement.
          * @param  contratplacement
          * @return Liste
          */
          public IValueObject getListInteretServi(IValueObject vo) {
              GetListInteretServiTrt getListInteretServiTrt = new GetListInteretServiTrt();
              return (getListInteretServiTrt.exec(vo));
          }
            
   
    /**
          * Methode permettant de traiter l'opéartion liquidation.
          * @param  AvancRembLiquid
          * @return AvancRembLiquid
          */
          public IValueObject traitementLiquidation(IValueObject vo) {
              TraitementLiquidationTrt traitementLiquidationTrt = new TraitementLiquidationTrt();
              return (traitementLiquidationTrt.exec(vo));
          }
    
    /**
          * Methode permettant de mettre à jour un contrat de placement.
          * @param  contratPlacement
          * @return contratPlacement
          */
          public IValueObject updateContratPlacement(IValueObject vo) {
              UpdateContratPlacementTrt updateContratPlacementTrt =new UpdateContratPlacementTrt();
              return (updateContratPlacementTrt.exec(vo));
          }
        
        
    /**
          * Methode permettant de retourner la liste des BC recupérés.
          * @param  ParamBonCaisse
          * @return List
          */
    public IValueObject getListBcRecupere(IValueObject vo) {
         GetListBcRecupereTrt getListBcRecupereTrt =new GetListBcRecupereTrt();
         return (getListBcRecupereTrt.exec(vo));
    }
    
    /**
          * Methode permettant de rejeter une liq anticipée
          * @param  ParamContratPlacement
          * @return AvancRembLiquid
          */
    public IValueObject rejeterLiquidationAnticipePlacement(IValueObject vo) {
         RejeterLiquidationAnticipeTrt rejeterLiquidationAnticipeTrt =new RejeterLiquidationAnticipeTrt();
         return (rejeterLiquidationAnticipeTrt.exec(vo));
    }
    
    
    public void setGetContratPlacementTrt(GetContratPlacementTrt getContratPlacementTrt) {
        this.getContratPlacementTrt = getContratPlacementTrt;
    }

    public GetContratPlacementTrt getGetContratPlacementTrt() {
        return getContratPlacementTrt;
    }

    public void setValiderDdeDecisionTrt(ValiderDdeDecisionTrt validerDdeDecisionTrt) {
        this.validerDdeDecisionTrt = validerDdeDecisionTrt;
    }

    public ValiderDdeDecisionTrt getValiderDdeDecisionTrt() {
        return validerDdeDecisionTrt;
    }

    public void setValiderMajDdeDecisionTrt(ValiderMajDdeDecisionTrt validerMajDdeDecisionTrt) {
        this.validerMajDdeDecisionTrt = validerMajDdeDecisionTrt;
    }

    public ValiderMajDdeDecisionTrt getValiderMajDdeDecisionTrt() {
        return validerMajDdeDecisionTrt;
    }

    public void setGetListDemandesDecisionTrt(GetListDemandesDecisionTrt getListDemandesDecisionTrt) {
        this.getListDemandesDecisionTrt = getListDemandesDecisionTrt;
    }

    public GetListDemandesDecisionTrt getGetListDemandesDecisionTrt() {
        return getListDemandesDecisionTrt;
    }


    public void setGetListContratsPlacementTrt(GetListContratsPlacementTrt getListContratsPlacementTrt) {
        this.getListContratsPlacementTrt = getListContratsPlacementTrt;
    }

    public GetListContratsPlacementTrt getGetListContratsPlacementTrt() {
        return getListContratsPlacementTrt;
    }

    public void setGetDemandeDecisionTrt(GetDemandeDecisionTrt getDemandeDecisionTrt) {
        this.getDemandeDecisionTrt = getDemandeDecisionTrt;
    }

    public GetDemandeDecisionTrt getGetDemandeDecisionTrt() {
        return getDemandeDecisionTrt;
    }

    public void setValiderAvancePlacementTrt(ValiderPECAvancePlacementTrt validerPECAvancePlacementTrt) {
        this.validerPECAvancePlacementTrt = validerPECAvancePlacementTrt;
    }

    public ValiderPECAvancePlacementTrt getValiderPECAvancePlacementTrt() {
        return validerPECAvancePlacementTrt;
    }

    public void setInsertMandPersOperPlacTrt(InsertMandPersOperPlacTrt insertMandPersOperPlacTrt) {
        this.insertMandPersOperPlacTrt = insertMandPersOperPlacTrt;
    }

    public InsertMandPersOperPlacTrt getInsertMandPersOperPlacTrt() {
        return insertMandPersOperPlacTrt;
    }

    public void setGetListAvancRembLiquidByEtatTrt(GetListAvancRembLiquidByEtatTrt getListAvancRembLiquidByEtatTrt) {
        this.getListAvancRembLiquidByEtatTrt = getListAvancRembLiquidByEtatTrt;
    }

    public IValueObject getListAvancRembLiquidByEtat(IValueObject vo) {
        return (getListAvancRembLiquidByEtatTrt.exec(vo));
    }

    public void setGetAvancRembLiquidByIdTrt(GetAvancRembLiquidByIdTrt getAvancRembLiquidByIdTrt) {
        this.getAvancRembLiquidByIdTrt = getAvancRembLiquidByIdTrt;
    }

    public IValueObject getAvancRembLiquidById(IValueObject vo) {
        return (getAvancRembLiquidByIdTrt.exec(vo));
    }

    public void setValiderPECAvancePlacementTrt(ValiderPECAvancePlacementTrt validerPECAvancePlacementTrt) {
        this.validerPECAvancePlacementTrt = validerPECAvancePlacementTrt;
    }

    public GetListAvancRembLiquidByEtatTrt getGetListAvancRembLiquidByEtatTrt() {
        return getListAvancRembLiquidByEtatTrt;
    }

    public GetAvancRembLiquidByIdTrt getGetAvancRembLiquidByIdTrt() {
        return getAvancRembLiquidByIdTrt;
    }

    public void setValiderAvancePlacementTrt(ValiderAvancePlacementTrt validerAvancePlacementTrt) {
        this.validerAvancePlacementTrt = validerAvancePlacementTrt;
    }

    public ValiderAvancePlacementTrt getValiderAvancePlacementTrt() {
        return validerAvancePlacementTrt;
    }
    public IValueObject validerAvancePlacement(IValueObject vo) {
        return (validerAvancePlacementTrt.exec(vo));
    }


    public void setGetParamBonCaisseTrt(GetParamBonCaisseTrt getParamBonCaisseTrt) {
        this.getParamBonCaisseTrt = getParamBonCaisseTrt;
    }

    public GetParamBonCaisseTrt getGetParamBonCaisseTrt() {
        return getParamBonCaisseTrt;
    }

    public IValueObject liquiderPlacement (IValueObject vo) {
    LiquidationAEcheanceTrt liquidationAEcheanceTrt=new LiquidationAEcheanceTrt();
    
    return (liquidationAEcheanceTrt.exec(vo));
    }
    
    public IValueObject GetAvgTMMbetweenDates (IValueObject vo) {
    GetAvgTMMbetweenDatesTrt getAvgTMMbetweenDatesTrt=new GetAvgTMMbetweenDatesTrt();
    
    return (getAvgTMMbetweenDatesTrt.exec(vo));
    }



    public void setPecLiquidationAnticipeTrt(PecLiquidationAnticipeTrt pecLiquidationAnticipeTrt) {
        this.pecLiquidationAnticipeTrt = pecLiquidationAnticipeTrt;
    }

    public PecLiquidationAnticipeTrt getPecLiquidationAnticipeTrt() {
        return pecLiquidationAnticipeTrt;
    }
    
    
   

    public IValueObject LiquidationAvancesPlacement (IValueObject vo) {
    LiquidationAvancesPlacementTrt liquidationAvancesPlacementTrt=new LiquidationAvancesPlacementTrt();
    
    return (liquidationAvancesPlacementTrt.exec(vo));
    }
	 
    public IValueObject interetServiAgence (IValueObject vo) {
    InteretServiAgenceTrt interetServiAgenceTrt=new InteretServiAgenceTrt();
    interetServiAgenceTrt.setVerifDomaine(false);
    return (interetServiAgenceTrt.exec(vo));
    }
    
    public IValueObject abonnementPlacementAgence (IValueObject vo) {
    AbonnementPlacementAgenceTrt abonnementPlacementAgenceTrt=new AbonnementPlacementAgenceTrt();
    
    return (abonnementPlacementAgenceTrt.exec(vo));
    }
    
    public IValueObject traitementAbonPlacement (IValueObject vo) {
    TraitementAbonPlacementTrt traitementAbonPlacementTrt=new TraitementAbonPlacementTrt();
    
    return (traitementAbonPlacementTrt.exec(vo));
    }
    
    public IValueObject validerRecuperationBc (IValueObject vo) {
    ValiderRecuperationTrt validerRecuperationBcTrt= new ValiderRecuperationTrt();
    
    return (validerRecuperationBcTrt.exec(vo));
    }
    
    public IValueObject getDetailBc (IValueObject vo) {
    GetDetailBcTrt getDetailBcTrt= new GetDetailBcTrt();
    
    return (getDetailBcTrt.exec(vo));
    }
  /*  public IValueObject getListeProduitPlacement (IValueObject vo) {
    GetListeProduitPlacementTrt getListeProduitPlacementTrt= new GetListeProduitPlacementTrt();
    
    return (getListeProduitPlacementTrt.exec(vo));
    }*/
	public IValueObject getSituationMensuelle (IValueObject vo) {
       getSituationMensuelleByStructureTrt=new GetSituationMensuelleByStructureTrt();
      return (getSituationMensuelleByStructureTrt.perform(vo));
    }
   
    public IValueObject getSituationMensuelleInt (IValueObject vo) {
    GetSituationMensuelleIntByStrcTrt getSituationMensuelleIntByStrcTrt =new GetSituationMensuelleIntByStrcTrt();
    return (getSituationMensuelleIntByStrcTrt.perform(vo));
    }
    public IValueObject validerRenouvellement (IValueObject vo) {
    ValiderRenouvellementPlacementTrt validerRenouvellementPlacementTrt =new ValiderRenouvellementPlacementTrt();
    return (validerRenouvellementPlacementTrt.perform(vo));
    }
	public IValueObject getSituationMensuelleClient (IValueObject vo) {
    getSituationMensuelleByClientTrt=new GetSituationMensuelleByClientTrt();
    return (getSituationMensuelleByClientTrt.perform(vo));
    }
    public IValueObject getSituationMensuelleClientInt (IValueObject vo) {
    GetSituationMensuelleIntByCltTrt getSituationMensuelleIntByCltTrt =new GetSituationMensuelleIntByCltTrt();
    return (getSituationMensuelleIntByCltTrt.perform(vo));
    }
    public IValueObject getSituationMensuelleCategorieClient (IValueObject vo) {
    getSituationMensuelleByCategoriePersonneTrt =new GetSituationMensuelleByCategoriePersonneTrt();
    return (getSituationMensuelleByCategoriePersonneTrt.perform(vo));
    }
    public IValueObject getSituationMensuelleIntCategClt (IValueObject vo) {
    GetSituationMensuelleInteretByCategPersTrt getSitMensInteretByCategPersTrt =new GetSituationMensuelleInteretByCategPersTrt();
    return (getSitMensInteretByCategPersTrt.perform(vo));
    }
    public IValueObject recapMouvementPlacService (IValueObject vo) {
    recapMovPlacementTrt =new RecapMovPlacementTrt();
    return (recapMovPlacementTrt.perform(vo));
    }
    public IValueObject getListAbonnementsInteretsService (IValueObject vo) {
    GetListAbonnementsTrt getListAbonnementsTrt=new GetListAbonnementsTrt();
    return (getListAbonnementsTrt.perform(vo));
    }
    public IValueObject getInteretServiById (IValueObject vo) {
    GetInteretServiByIdTrt getInteretServiByIdTrt=new GetInteretServiByIdTrt();
        return (getInteretServiByIdTrt.perform(vo));
    }
    public IValueObject updateAvanceRembLiqu (IValueObject vo) {
    UpdateAvanceRembLiquTrt updateAvanceRembLiquTrt=new UpdateAvanceRembLiquTrt();
        return (updateAvanceRembLiquTrt.perform(vo));
    }
    
    public IValueObject verifBCService (IValueObject vo) {
     verifBCTrt=new VerifBCTrt();
        return (verifBCTrt.perform(vo));
    }
    public IValueObject InsertBCService (IValueObject vo) {
     verifBCTrt=new VerifBCTrt();
        return (verifBCTrt.insertBC(vo));
    }
}
