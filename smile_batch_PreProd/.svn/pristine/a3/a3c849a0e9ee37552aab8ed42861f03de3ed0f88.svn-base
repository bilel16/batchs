package com.bna.smile.web.placement.forms;

import com.bna.commun.model.ContratPlacement;
import com.bna.commun.model.InteretServi;
import com.bna.smile.web.commun.model.PersonneDemandeur;
import com.bna.smile.web.commun.model.Pouvoir;
import com.bna.smile.web.commun.view.ContratView;
import com.bna.smile.web.commun.view.InitialisationView;

import com.bna.smile.web.moyenPaiement.oppositionMoyenPaiement.model.ParamConsult;

import com.bna.smile.web.placement.view.ContratPlacementView;

import com.bna.smile.web.placement.view.DemandeDecisionView;

import java.util.ArrayList;
import java.util.Collection;

import org.apache.struts.action.ActionForm;

public class CreationContratPlacementForm extends ActionForm {
    
    public CreationContratPlacementForm() {
    }

    private InitialisationView initialisationView = new InitialisationView();
    private ContratPlacementView contratPlacementView = new ContratPlacementView();
    private Collection listeDemandesValides;
    private DemandeDecisionView demandeDecisionView = new DemandeDecisionView();
    private String numDemandeChoisi ="";
    private String libelleConfirmation ="";
    private String titreConfirmation="";
    private String titrePage="";
    private String numBonCaisseAjax ="";
    private String codeStructureAjax ="";
    private String existBCAjax ="";
    private String existDBCAjax ="";
    private boolean verifRecuperationBC = false; // = true si BC récupéré
    private String numSeqBCAjax="";
    private PersonneDemandeur personneDemandeur = new PersonneDemandeur();
    private Pouvoir pouvoir;
    private String typeDemandeur;
    private String alertDemandeur=""; 
    private String alertPouvoir; 
    private ContratView contratView = new ContratView();
    private String montOperOmp;
    private String envRestOmp;
    private String typeMandat;
    
    // variables JSP de validation des contrats placement
    private Collection listeContratPlacAtt;
    private String numCplaChoisi ="";
    private String numSeqCpla="";
    private String dateSouscription = "";
    private String rejet = "";
    InteretServi interetServi = new InteretServi();
    private String natureOp="";
    
    
    public void clearForm(){
    this.contratPlacementView.clear();
    this.listeDemandesValides = new ArrayList();
    this.listeContratPlacAtt = new ArrayList();
    this.demandeDecisionView.clear();
    this.numDemandeChoisi ="";
    this.dateSouscription="";
    this.numCplaChoisi ="";
    this.numSeqCpla="";
    this.dateSouscription = "";
    this.verifRecuperationBC = false;
    this.rejet = "";
    }
    public void clearPouvoir(){
     personneDemandeur.clear();;
     pouvoir = new Pouvoir();
     typeDemandeur ="";
     alertDemandeur=""; 
     alertPouvoir= ""; 
     contratView.clear();;
     montOperOmp="";
     envRestOmp="";
     typeMandat="";
    }
    public void setInitialisationView(InitialisationView initialisationView) {
        this.initialisationView = initialisationView;
    }

    public InitialisationView getInitialisationView() {
        return initialisationView;
    }

    public void setContratPlacementView(ContratPlacementView contratPlacementView) {
        this.contratPlacementView = contratPlacementView;
    }

    public ContratPlacementView getContratPlacementView() {
        return contratPlacementView;
    }

    public void setListeDemandesValides(Collection listeDemandesValides) {
        this.listeDemandesValides = listeDemandesValides;
    }

    public Collection getListeDemandesValides() {
        return listeDemandesValides;
    }

    public void setNumDemandeChoisi(String numDemandeChoisi) {
        this.numDemandeChoisi = numDemandeChoisi;
    }

    public String getNumDemandeChoisi() {
        return numDemandeChoisi;
    }

    public void setDemandeDecisionView(DemandeDecisionView demandeDecisionView) {
        this.demandeDecisionView = demandeDecisionView;
    }

    public DemandeDecisionView getDemandeDecisionView() {
        return demandeDecisionView;
    }

    public void setLibelleConfirmation(String libelleConfirmation) {
        this.libelleConfirmation = libelleConfirmation;
    }

    public String getLibelleConfirmation() {
        return libelleConfirmation;
    }

    public void setTitreConfirmation(String titreConfirmation) {
        this.titreConfirmation = titreConfirmation;
    }

    public String getTitreConfirmation() {
        return titreConfirmation;
    }

    public void setNumBonCaisseAjax(String numBonCaisseAjax) {
        this.numBonCaisseAjax = numBonCaisseAjax;
    }

    public String getNumBonCaisseAjax() {
        return numBonCaisseAjax;
    }

    public void setCodeStructureAjax(String codeStructureAjax) {
        this.codeStructureAjax = codeStructureAjax;
    }


    public String getCodeStructureAjax() {
        return codeStructureAjax;
    }

    public void setExistBCAjax(String existBCAjax) {
        this.existBCAjax = existBCAjax;
    }

    public String getExistBCAjax() {
        return existBCAjax;
    }

    public void setExistDBCAjax(String existDBCAjax) {
        this.existDBCAjax = existDBCAjax;
    }

    public String getExistDBCAjax() {
        return existDBCAjax;
    }

    public void setNumSeqBCAjax(String numSeqBCAjax) {
        this.numSeqBCAjax = numSeqBCAjax;
    }

    public String getNumSeqBCAjax() {
        return numSeqBCAjax;
    }

    public void setListeContratPlacAtt(Collection listeContratPlacAtt) {
        this.listeContratPlacAtt = listeContratPlacAtt;
    }

    public Collection getListeContratPlacAtt() {
        return listeContratPlacAtt;
    }

    public void setNumCplaChoisi(String numCplaChoisi) {
        this.numCplaChoisi = numCplaChoisi;
    }

    public String getNumCplaChoisi() {
        return numCplaChoisi;
    }

    public void setNumSeqCpla(String numSeqCpla) {
        this.numSeqCpla = numSeqCpla;
    }

    public String getNumSeqCpla() {
        return numSeqCpla;
    }

    public void setPersonneDemandeur(PersonneDemandeur personneDemandeur) {
        this.personneDemandeur = personneDemandeur;
    }

    public PersonneDemandeur getPersonneDemandeur() {
        return personneDemandeur;
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

    public void setAlertPouvoir(String alertPouvoir) {
        this.alertPouvoir = alertPouvoir;
    }

    public String getAlertPouvoir() {
        return alertPouvoir;
    }

    public void setAlertDemandeur(String alertDemandeur) {
        this.alertDemandeur = alertDemandeur;
    }

    public String getAlertDemandeur() {
        return alertDemandeur;
    }

    public void setContratView(ContratView contratView) {
        this.contratView = contratView;
    }

    public ContratView getContratView() {
        return contratView;
    }

    public void setTitrePage(String titrePage) {
        this.titrePage = titrePage;
    }

    public String getTitrePage() {
        return titrePage;
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

    public void setDateSouscription(String dateSouscription) {
        this.dateSouscription = dateSouscription;
    }

    public String getDateSouscription() {
        return dateSouscription;
    }

    public void setInteretServi(InteretServi interetServi) {
        this.interetServi = interetServi;
    }

    public InteretServi getInteretServi() {
        return interetServi;
    }

    public void setVerifRecuperationBC(boolean verifRecuperationBC) {
        this.verifRecuperationBC = verifRecuperationBC;
    }

    public boolean isVerifRecuperationBC() {
        return verifRecuperationBC;
    }

    public void setRejet(String rejet) {
        this.rejet = rejet;
    }

    public String getRejet() {
        return rejet;
    }

    public void setNatureOp(String natureOp) {
        this.natureOp = natureOp;
    }

    public String getNatureOp() {
        return natureOp;
    }
}
