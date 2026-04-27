package com.bna.smile.web.placement.forms;

import com.bna.commun.model.AvancRembLiquid;
import com.bna.commun.model.ContratCpt;
import com.bna.commun.model.ContratPlacement;

import com.bna.smile.web.commun.model.PersonneDemandeur;
import com.bna.smile.web.commun.model.Pouvoir;
import com.bna.smile.web.commun.view.ContratView;
import com.bna.smile.web.commun.view.InitialisationView;

import com.bna.smile.web.placement.view.AvancRembLiquidView;
import com.bna.smile.web.placement.view.ContratPlacementView;
import com.bna.smile.web.placement.view.DemandeDecisionView;

import java.util.Collection;

import java.util.Date;

import org.apache.struts.action.ActionForm;

public class AvancRembLiquidValidPlacementForm extends ActionForm {
    /**Reset all properties to their default values.
     * @param mapping The ActionMapping used to select this instance.
     * @param request The HTTP Request we are processing.
     */    

     public AvancRembLiquidValidPlacementForm() {
     }
 
     private InitialisationView initialisationView = new InitialisationView();
     private ContratView contratView = new ContratView();
     private AvancRembLiquidView avancRembLiquidView= new AvancRembLiquidView();
     private ContratPlacementView contratPlacementView= new ContratPlacementView();
     private PersonneDemandeur personneDemandeur = new PersonneDemandeur();
     private ContratPlacement contratPlacement = new ContratPlacement();
     private AvancRembLiquid avancRembLiquid = new AvancRembLiquid();
     private String typeForm;
     private String libelleOperation;
     private String libelleConfirmation;
     private String titreConfirmation="Confirmation";
     private ContratCpt contratCpt;
     private String soldeFinal;
     private String alertContrat="";
     private String etatContrat="";
     //pour la recherche

      private String numContratPlacChoisi;
      private String numeroContratPlacChoisi;
      private Collection listeContratsPlacement;
      private String alertAfficheContrat ="";
      private String alertContratPlacement="True";
      private String alertDemandeur=""; 
      private Pouvoir pouvoir;
      private String typeDemandeur;
      private String alertPouvoir;      
      private String montantBrutInteret;
      private String montantIRC;
      private String choixValidPlac;
      private String numAvanceChoisi;
      private String numeroAvanceChoisi;
      private Collection listeAvance;
      private String alertExistAvance="False";
      private String dateComptable;
      private String montOperOmp;
      private String envRestOmp;
      private String typeMandat;
      private String valMarge;
      private String chargementCpl="False";

      private String liquidationPossible=""; 
      private String tmm;
      private String moyenneTmm;      
      private String numliquidationChoisi;
      private String numeroLiqChoisi;
      private Collection listeLiquidationAnticipe;
      private String alertExistLiquidation;
      private Date   DateValeur;
      private String dateValeurInteret;
      private String bnaPlc;
      private Collection listeInteretServi;
      private String sommeInterets="0";
      private String dateLastInteretServi;
      private String typePost="";
      private boolean bcRecupere=false;
      private String operInteret="";
      private String texteLiqPartielle="";
      private String sommeInteretsProrats="0";
      private String nbreInteretServi="0";
      private String casLiqTotaleApLiqPartielle="";
      private String typeFaveurPlc="";
      private String alertNumBc="";
      private String numBc="";
      private String codSbdvDemd="";
      private String dateValAjax="";
      private String nbrJoursOuvrable="";
      private String typeDValeur;
      private String dateActuelle="";
      private boolean penalitePlacementBnaPlc;
     
      
    public void clearFormAvancRembLiquidValidPlacement(){
        contratCpt = new ContratCpt();
        contratView.clear(); 
        alertContrat="";
        etatContrat=""; 
        soldeFinal="";
        contratPlacementView = new ContratPlacementView();
        avancRembLiquidView= new AvancRembLiquidView();  
        numContratPlacChoisi = "";
        numeroContratPlacChoisi = "";
        listeContratsPlacement = null;
        personneDemandeur.clear();
        typeDemandeur="";
        montantBrutInteret="";
        montantIRC="";
        alertAfficheContrat ="";
        alertContratPlacement="True";
        alertDemandeur=""; 
        alertPouvoir="";
        listeAvance=null;
        alertExistAvance="False";
        liquidationPossible="";
        
        tmm="";
        moyenneTmm="";        
        numliquidationChoisi="";
        numeroLiqChoisi="";
        listeLiquidationAnticipe=null;
        listeInteretServi=null;
        alertExistLiquidation="";
        bnaPlc= "";
        bcRecupere = false;
        operInteret="";
        texteLiqPartielle="";
        sommeInteretsProrats="0";
        nbreInteretServi="0";
        sommeInterets="0";
        typeFaveurPlc="";
        alertNumBc="";
        numBc="";
      
        
        }

    public void setInitialisationView(InitialisationView initialisationView) {
        this.initialisationView = initialisationView;
    }

    public InitialisationView getInitialisationView() {
        return initialisationView;
    }

    public void setTypeForm(String typeForm) {
        this.typeForm = typeForm;
    }

    public String getTypeForm() {
        return typeForm;
    }

    public void setLibelleOperation(String libelleOperation) {
        this.libelleOperation = libelleOperation;
    }

    public String getLibelleOperation() {
        return libelleOperation;
    }

    public void setContratView(ContratView contratView) {
        this.contratView = contratView;
    }

    public ContratView getContratView() {
        return contratView;
    }

    public void setContratPlacementView(ContratPlacementView contratPlacementView) {
        this.contratPlacementView = contratPlacementView;
    }

    public ContratPlacementView getContratPlacementView() {
        return contratPlacementView;
    }

  
   
    public void setPersonneDemandeur(PersonneDemandeur personneDemandeur) {
        this.personneDemandeur = personneDemandeur;
    }

    public PersonneDemandeur getPersonneDemandeur() {
        return personneDemandeur;
    }

    public void setNumContratPlacChoisi(String numContratPlacChoisi) {
        this.numContratPlacChoisi = numContratPlacChoisi;
    }

    public String getNumContratPlacChoisi() {
        return numContratPlacChoisi;
    }

    public void setListeContratsPlacement(Collection listeContratsPlacement) {
        this.listeContratsPlacement = listeContratsPlacement;
    }

    public Collection getListeContratsPlacement() {
        return listeContratsPlacement;
    }

    public void setNumeroContratPlacChoisi(String numeroContratPlacChoisi) {
        this.numeroContratPlacChoisi = numeroContratPlacChoisi;
    }

    public String getNumeroContratPlacChoisi() {
        return numeroContratPlacChoisi;
    }




    public void setAlertDemandeur(String alertDemandeur) {
        this.alertDemandeur = alertDemandeur;
    }

    public String getAlertDemandeur() {
        return alertDemandeur;
    }

    public void setPouvoir(Pouvoir pouvoir) {
        this.pouvoir = pouvoir;
    }

    public Pouvoir getPouvoir() {
        return pouvoir;
    }

    public void setTypeDemandeur(String typeDemandeur) {
        this.typeDemandeur = typeDemandeur;
    }

    public String getTypeDemandeur() {
        return typeDemandeur;
    }

    public void setContratCpt(ContratCpt contratCpt) {
        this.contratCpt = contratCpt;
    }

    public ContratCpt getContratCpt() {
        return contratCpt;
    }

    public void setSoldeFinal(String soldeFinal) {
        this.soldeFinal = soldeFinal;
    }

    public String getSoldeFinal() {
        return soldeFinal;
    }

    public void setAlertContrat(String alertContrat) {
        this.alertContrat = alertContrat;
    }

    public String getAlertContrat() {
        return alertContrat;
    }

    public void setEtatContrat(String etatContrat) {
        this.etatContrat = etatContrat;
    }

    public String getEtatContrat() {
        return etatContrat;
    }

    public void setContratPlacement(ContratPlacement contratPlacement) {
        this.contratPlacement = contratPlacement;
    }

    public ContratPlacement getContratPlacement() {
        return contratPlacement;
    }

    public void setAlertAfficheContrat(String alertAfficheContrat) {
        this.alertAfficheContrat = alertAfficheContrat;
    }

    public String getAlertAfficheContrat() {
        return alertAfficheContrat;
    }

    public void setAvancRembLiquidView(AvancRembLiquidView avancRembLiquidView) {
        this.avancRembLiquidView = avancRembLiquidView;
    }

    public AvancRembLiquidView getAvancRembLiquidView() {
        return avancRembLiquidView;
    }

    public void setMontantBrutInteret(String montantBrutInteret) {
        this.montantBrutInteret = montantBrutInteret;
    }

    public String getMontantBrutInteret() {
        return montantBrutInteret;
    }

    public void setMontantIRC(String montantIRC) {
        this.montantIRC = montantIRC;
    }

    public String getMontantIRC() {
        return montantIRC;
    }

    public void setAlertContratPlacement(String alertContratPlacement) {
        this.alertContratPlacement = alertContratPlacement;
    }

    public String getAlertContratPlacement() {
        return alertContratPlacement;
    }

    public void setAvancRembLiquid(AvancRembLiquid avancRembLiquid) {
        this.avancRembLiquid = avancRembLiquid;
    }

    public AvancRembLiquid getAvancRembLiquid() {
        return avancRembLiquid;
    }

    public void setLibelleConfirmation(String libelleConfirmation) {
        this.libelleConfirmation = libelleConfirmation;
    }

    public String getLibelleConfirmation() {
        return libelleConfirmation;
    }

    public void setAlertPouvoir(String alertPouvoir) {
        this.alertPouvoir = alertPouvoir;
    }

    public String getAlertPouvoir() {
        return alertPouvoir;
    }

    public void setChoixValidPlac(String choixValidPlac) {
        this.choixValidPlac = choixValidPlac;
    }

    public String getChoixValidPlac() {
        return choixValidPlac;
    }

    public void setTitreConfirmation(String titreConfirmation) {
        this.titreConfirmation = titreConfirmation;
    }

    public String getTitreConfirmation() {
        return titreConfirmation;
    }


    public void setListeAvance(Collection listeAvance) {
        this.listeAvance = listeAvance;
    }

    public Collection getListeAvance() {
        return listeAvance;
    }

    public void setNumAvanceChoisi(String numAvanceChoisi) {
        this.numAvanceChoisi = numAvanceChoisi;
    }

    public String getNumAvanceChoisi() {
        return numAvanceChoisi;
    }

    public void setAlertExistAvance(String alertExistAvance) {
        this.alertExistAvance = alertExistAvance;
    }

    public String getAlertExistAvance() {
        return alertExistAvance;
    }

    public void setNumeroAvanceChoisi(String numeroAvanceChoisi) {
        this.numeroAvanceChoisi = numeroAvanceChoisi;
    }

    public String getNumeroAvanceChoisi() {
        return numeroAvanceChoisi;
    }

    public void setDateComptable(String dateComptable) {
        this.dateComptable = dateComptable;
    }

    public String getDateComptable() {
        return dateComptable;
    }

    public void setMontOperOmp(String montOperOmp) {
        this.montOperOmp = montOperOmp;
    }

    public String getMontOperOmp() {
        return montOperOmp;
    }

    public void setEnvRestOmp(String envRestOmp) {
        this.envRestOmp = envRestOmp;
    }

    public String getEnvRestOmp() {
        return envRestOmp;
    }

    public void setTypeMandat(String typeMandat) {
        this.typeMandat = typeMandat;
    }

    public String getTypeMandat() {
        return typeMandat;
    }

    public void setValMarge(String valMarge) {
        this.valMarge = valMarge;
    }

    public String getValMarge() {
        return valMarge;
    }

    public void setChargementCpl(String chargementCpl) {
        this.chargementCpl = chargementCpl;
    }

    public String getChargementCpl() {
        return chargementCpl;
    }


    public void setLiquidationPossible(String liquidationPossible) {
        this.liquidationPossible = liquidationPossible;
    }

    public String getLiquidationPossible() {
        return liquidationPossible;
    }

    public void setTmm(String tmm) {
        this.tmm = tmm;
    }

    public String getTmm() {
        return tmm;
    }

    public void setMoyenneTmm(String moyenneTmm) {
        this.moyenneTmm = moyenneTmm;
    }

    public String getMoyenneTmm() {
        return moyenneTmm;
    }

   
    public void setNumliquidationChoisi(String numliquidationChoisi) {
        this.numliquidationChoisi = numliquidationChoisi;
    }

    public String getNumliquidationChoisi() {
        return numliquidationChoisi;
    }

    public void setNumeroLiqChoisi(String numeroLiqChoisi) {
        this.numeroLiqChoisi = numeroLiqChoisi;
    }

    public String getNumeroLiqChoisi() {
        return numeroLiqChoisi;
    }

    public void setListeLiquidationAnticipe(Collection listeLiquidationAnticipe) {
        this.listeLiquidationAnticipe = listeLiquidationAnticipe;
    }

    public Collection getListeLiquidationAnticipe() {
        return listeLiquidationAnticipe;
    }

    public void setAlertExistLiquidation(String alertExistLiquidation) {
        this.alertExistLiquidation = alertExistLiquidation;
    }

    public String getAlertExistLiquidation() {
        return alertExistLiquidation;
    }


    public void setDateValeur(Date dateValeur) {
        this.DateValeur = dateValeur;
    }

    public Date getDateValeur() {
        return DateValeur;
    }

    public void setDateValeurInteret(String dateValeurInteret) {
        this.dateValeurInteret = dateValeurInteret;
    }

    public String getDateValeurInteret() {
        return dateValeurInteret;
    }

    public void setBnaPlc(String bnaPlc) {
        this.bnaPlc = bnaPlc;
    }

    public String getBnaPlc() {
        return bnaPlc;
    }

    public void setListeInteretServi(Collection listeInteretServi) {
        this.listeInteretServi = listeInteretServi;
    }

    public Collection getListeInteretServi() {
        return listeInteretServi;
    }

    public void setSommeInterets(String sommeInterets) {
        this.sommeInterets = sommeInterets;
    }

    public String getSommeInterets() {
        return sommeInterets;
    }

    public void setDateLastInteretServi(String dateLastInteretServi) {
        this.dateLastInteretServi = dateLastInteretServi;
    }

    public String getDateLastInteretServi() {
        return dateLastInteretServi;
    }

    public void setTypePost(String typePost) {
        this.typePost = typePost;
    }

    public String getTypePost() {
        return typePost;
    }


    public void setBcRecupere(boolean bcRecupere) {
        this.bcRecupere = bcRecupere;
    }

    public boolean isBcRecupere() {
        return bcRecupere;
    }

    public void setOperInteret(String operInteret) {
        this.operInteret = operInteret;
    }

    public String getOperInteret() {
        return operInteret;
    }

    public void setTexteLiqPartielle(String texteLiqPartielle) {
        this.texteLiqPartielle = texteLiqPartielle;
    }

    public String getTexteLiqPartielle() {
        return texteLiqPartielle;
    }

    public void setSommeInteretsProrats(String sommeInteretsProrats) {
        this.sommeInteretsProrats = sommeInteretsProrats;
    }

    public String getSommeInteretsProrats() {
        return sommeInteretsProrats;
    }

    public void setNbreInteretServi(String nbreInteretServi) {
        this.nbreInteretServi = nbreInteretServi;
    }

    public String getNbreInteretServi() {
        return nbreInteretServi;
    }

    public void setCasLiqTotaleApLiqPartielle(String casLiqTotaleApLiqPartielle) {
        this.casLiqTotaleApLiqPartielle = casLiqTotaleApLiqPartielle;
    }

    public String getCasLiqTotaleApLiqPartielle() {
        return casLiqTotaleApLiqPartielle;
    }

    public void setTypeFaveurPlc(String typeFaveurPlc) {
        this.typeFaveurPlc = typeFaveurPlc;
    }

    public String getTypeFaveurPlc() {
        return typeFaveurPlc;
    }

    public void setAlertNumBc(String alertNumBc) {
        this.alertNumBc = alertNumBc;
    }

    public String getAlertNumBc() {
        return alertNumBc;
    }

    public void setNumBc(String numBc) {
        this.numBc = numBc;
    }

    public String getNumBc() {
        return numBc;
    }


    public void setCodSbdvDemd(String codSbdvDemd) {
        this.codSbdvDemd = codSbdvDemd;
    }

    public String getCodSbdvDemd() {
        return codSbdvDemd;
    }

    public void setDateValAjax(String dateValAjax) {
        this.dateValAjax = dateValAjax;
    }

    public String getDateValAjax() {
        return dateValAjax;
    }

    public void setNbrJoursOuvrable(String nbrJoursOuvrable) {
        this.nbrJoursOuvrable = nbrJoursOuvrable;
    }

    public String getNbrJoursOuvrable() {
        return nbrJoursOuvrable;
    }

    public void setTypeDValeur(String typeDValeur) {
        this.typeDValeur = typeDValeur;
    }

    public String getTypeDValeur() {
        return typeDValeur;
    }

    public void setDateActuelle(String dateActuelle){
        this.dateActuelle = dateActuelle;
    }

    public String getDateActuelle() {
        return dateActuelle;
    }


    public void setPenalitePlacementBnaPlc(boolean penalitePlacementBnaPlc) {
        this.penalitePlacementBnaPlc = penalitePlacementBnaPlc;
    }

    public boolean isPenalitePlacementBnaPlc() {
        return penalitePlacementBnaPlc;
    }
}


