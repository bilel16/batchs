package com.bna.smile.web.placement.forms;

import com.bna.commun.model.ContratPlacement;
import com.bna.commun.model.DemandeDecision;
import com.bna.smile.web.commun.view.InitialisationView;
import com.bna.smile.web.placement.view.ContratPlacementView;
import com.bna.smile.web.placement.view.DemandeDecisionView;

import java.util.Collection;
import java.util.List;

import org.apache.struts.action.ActionForm;


public class ConsultationPlacementForm extends ActionForm {
    /**Reset all properties to their default values.
     * @param mapping The ActionMapping used to select this instance.
     * @param request The HTTP Request we are processing.
     */    

     public ConsultationPlacementForm() {
     }
 
     private DemandeDecisionView  demandeDecisionView = new  DemandeDecisionView();     
     private InitialisationView initialisationView = new InitialisationView();
     // données cocernant la forme Demande decision :
     private String libelleOperation;   
     private String codeOperation;   
     private String choixRecherche;
     // données concernant la forme de recherche des demandes
     private String typePieceId;
     private String numPieceId;
     private String numDemdRech;
     private String dateDebut;
     private String dateFin;
     private String choix;
    
    
     private Collection listeDemandesDecision;
     private DemandeDecision demandeDecision;
     private String alertDemandeDecision=""; // alerte si la demande est validée ou traitée ou inexistante (cette demande n'est pas en attente de réponse de la trésorerie
     
     private String structureRech="";
     private String produitRech="";
     private String dateDebRecherch="";
     private String dateFinRecherch="";
     private String numAvanceRecherch="";
     private String etatDemande;
     private String codTypeStructure;
     private List listAgConcernees;
     private String typeForm;
     private Collection listeContratPlacement;
     private String numCplaRech;
     private Long   numSeqPersRech;
     
     private String codeAgence;
     private String codeProduit;
     private String numeroCompte;

     private Collection listeARLPlacement;
     
     private Collection listeLiquidationAnticipe;
     private Collection listeAvance;
     private Collection listeInteretServi;     
     private String numliquidationChoisi;
     private String numeroLiqChoisi;
     private String sommeInterets;
     private String sommeIRC;
     private String sommeInteretsBrut;
     private String sommeCapital;
     private String datCreationRech="";
     private String etatPlacement;
     private String capitalRech="";
     private String capitalRestRech="";
     private String dureeRech="";
     private String tauxRech="";
     private String paiementRech;
     private String datValRech;
     private String datEcheRech;
     private List situationMensuelle;
     private List situationMensuelleInt;
     private List listCategoriesPersonne;
     private String categoriePersonne;
     private String echeance="";
     private String intBrut="";
     private String mntIrc="";
     private String intNet="";
     private String typeFaveurPlac="";
     private String prdPlc;
     private String tauxIrc="";
     private List recapMouvement;
     private List listeAbonnements;
     private String someNbreAbl;
     private String someAbT;
     private String someAbNT;
     private String errorMessage;
     private ContratPlacementView contratPlacementView;
     private String numInteretChoisi;
     private String numeroIntChoisi;
     private String sommeCapitalActualise;

    public void clearFormRechercheDemandeDecision(){
        //typePieceId="";
        numPieceId="";
        numDemdRech="";
        dateDebRecherch="";
        dateFinRecherch="";
        etatDemande="0";
        listeDemandesDecision = null;
        demandeDecisionView = new DemandeDecisionView();
        alertDemandeDecision="";
        codTypeStructure="";
        listAgConcernees=null;
    }
    
    public void clearFormRechercheContratPlacement(){
        //typePieceId="";
        numPieceId="";
        numCplaRech="";
        dateDebRecherch="";
        dateFinRecherch="";
        etatDemande="0";
        listeContratPlacement = null;
        codTypeStructure="";
        listAgConcernees=null;
        codeAgence="";
        codeProduit="";
        numeroCompte="";
        sommeCapitalActualise="";
    }
    public void setDemandeDecisionView(DemandeDecisionView demandeDecisionView) {
        this.demandeDecisionView = demandeDecisionView;
    }

    public DemandeDecisionView getDemandeDecisionView() {
        return demandeDecisionView;
    }

  

    public void setInitialisationView(InitialisationView initialisationView) {
        this.initialisationView = initialisationView;
    }

    public InitialisationView getInitialisationView() {
        return initialisationView;
    }
    public void setLibelleOperation(String libelleOperation) {
        this.libelleOperation = libelleOperation;
    }

    public String getLibelleOperation() {
        return libelleOperation;
    }


    public void setTypePieceId(String typePieceId) {
        this.typePieceId = typePieceId;
    }

    public String getTypePieceId() {
        return typePieceId;
    }

    public void setNumPieceId(String numPieceId) {
        this.numPieceId = numPieceId;
    }

    public String getNumPieceId() {
        return numPieceId;
    }

    public void setNumDemdRech(String numDemdRech) {
        this.numDemdRech = numDemdRech;
    }

    public String getNumDemdRech() {
        return numDemdRech;
    }

    public void setDateDebut(String dateDebut) {
        this.dateDebut = dateDebut;
    }

    public String getDateDebut() {
        return dateDebut;
    }

    public void setDateFin(String dateFin) {
        this.dateFin = dateFin;
    }

    public String getDateFin() {
        return dateFin;
    }

    public void setChoix(String choix) {
        this.choix = choix;
    }

    public String getChoix() {
        return choix;
    }

  
    public void setListeDemandesDecision(Collection listeDemandesDecision) {
        this.listeDemandesDecision = listeDemandesDecision;
    }

    public Collection getListeDemandesDecision() {
        return listeDemandesDecision;
    }

    public void setDemandeDecision(DemandeDecision demandeDecision) {
        this.demandeDecision = demandeDecision;
    }

    public DemandeDecision getDemandeDecision() {
        return demandeDecision;
    }

    public void setAlertDemandeDecision(String alertDemandeDecision) {
        this.alertDemandeDecision = alertDemandeDecision;
    }

    public String getAlertDemandeDecision() {
        return alertDemandeDecision;
    }

    public void setStructureRech(String structureRech) {
        this.structureRech = structureRech;
    }

    public String getStructureRech() {
        return structureRech;
    }

    public void setProduitRech(String produitRech) {
        this.produitRech = produitRech;
    }

    public String getProduitRech() {
        return produitRech;
    }

    public void setDateDebRecherch(String dateDebRecherch) {
        this.dateDebRecherch = dateDebRecherch;
    }

    public String getDateDebRecherch() {
        return dateDebRecherch;
    }

    public void setDateFinRecherch(String dateFinRecherch) {
        this.dateFinRecherch = dateFinRecherch;
    }

    public String getDateFinRecherch() {
        return dateFinRecherch;
    }


    public void setChoixRecherche(String choixRecherche) {
        this.choixRecherche = choixRecherche;
    }

    public String getChoixRecherche() {
        return choixRecherche;
    }


    public void setEtatDemande(String etatDemande) {
        this.etatDemande = etatDemande;
    }

    public String getEtatDemande() {
        return etatDemande;
    }

    public void setCodTypeStructure(String codTypeStructure) {
        this.codTypeStructure = codTypeStructure;
    }

    public String getCodTypeStructure() {
        return codTypeStructure;
    }

    public void setListAgConcernees(List listAgConcernees) {
        this.listAgConcernees = listAgConcernees;
    }

    public List getListAgConcernees() {
        return listAgConcernees;
    }

    public void setListeContratPlacement(Collection listeContratPlacement) {
        this.listeContratPlacement = listeContratPlacement;
    }

    public Collection getListeContratPlacement() {
        return listeContratPlacement;
    }

    public void setNumCplaRech(String numCplaRech) {
        this.numCplaRech = numCplaRech;
    }

    public String getNumCplaRech() {
        return numCplaRech;
    }

    public void setCodeAgence(String codeAgence) {
        this.codeAgence = codeAgence;
    }

    public String getCodeAgence() {
        return codeAgence;
    }

    public void setCodeProduit(String codeProduit) {
        this.codeProduit = codeProduit;
    }

    public String getCodeProduit() {
        return codeProduit;
    }

    public void setNumeroCompte(String numeroCompte) {
        this.numeroCompte = numeroCompte;
    }

    public String getNumeroCompte() {
        return numeroCompte;
    }


    public void setListeARLPlacement(Collection listeARLPlacement) {
        this.listeARLPlacement = listeARLPlacement;
    }

    public Collection getListeARLPlacement() {
        return listeARLPlacement;
    }

    public void setNumSeqPersRech(Long numSeqPersRech) {
        this.numSeqPersRech = numSeqPersRech;
    }

    public Long getNumSeqPersRech() {
        return numSeqPersRech;
    }



    public void setListeLiquidationAnticipe(Collection listeLiquidationAnticipe) {
        this.listeLiquidationAnticipe = listeLiquidationAnticipe;
    }

    public Collection getListeLiquidationAnticipe() {
        return listeLiquidationAnticipe;
    }

    public void setListeAvance(Collection listeAvance) {
        this.listeAvance = listeAvance;
    }

    public Collection getListeAvance() {
        return listeAvance;
    }

    public void setListeInteretServi(Collection listeInteretServi) {
        this.listeInteretServi = listeInteretServi;
    }

    public Collection getListeInteretServi() {
        return listeInteretServi;
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

    public void setSommeInterets(String sommeInterets) {
        this.sommeInterets = sommeInterets;
    }

    public String getSommeInterets() {
        return sommeInterets;
    }

    public void setDatCreationRech(String datCreationRech) {
        this.datCreationRech = datCreationRech;
    }

    public String getDatCreationRech() {
        return datCreationRech;
    }

    public void setCapitalRech(String capitalRech) {
        this.capitalRech = capitalRech;
    }

    public String getCapitalRech() {
        return capitalRech;
    }

    public void setDureeRech(String dureeRech) {
        this.dureeRech = dureeRech;
    }

    public String getDureeRech() {
        return dureeRech;
    }

    public void setTauxRech(String tauxRech) {
        this.tauxRech = tauxRech;
    }

    public String getTauxRech() {
        return tauxRech;
    }

    public void setPaiementRech(String paiementRech) {
        this.paiementRech = paiementRech;
    }

    public String getPaiementRech() {
        return paiementRech;
    }


    public void setTypeForm(String typeForm) {
        this.typeForm = typeForm;
    }

    public String getTypeForm() {
        return typeForm;
    }


    public void setSommeIRC(String sommeIRC) {
        this.sommeIRC = sommeIRC;
    }

    public String getSommeIRC() {
        return sommeIRC;
    }

    public void setSommeInteretsBrut(String sommeInteretsBrut) {
        this.sommeInteretsBrut = sommeInteretsBrut;
    }

    public String getSommeInteretsBrut() {
        return sommeInteretsBrut;
    }

    public void setDatValRech(String datValRech) {
        this.datValRech = datValRech;
    }

    public String getDatValRech() {
        return datValRech;
    }

    public void setDatEcheRech(String datEcheRech) {
        this.datEcheRech = datEcheRech;
    }

    public String getDatEcheRech() {
        return datEcheRech;
    }

    public void setSommeCapital(String sommeCapital) {
        this.sommeCapital = sommeCapital;
    }

    public String getSommeCapital() {
        return sommeCapital;
    }

    public void setSituationMensuelle(List situationMensuelle) {
        this.situationMensuelle = situationMensuelle;
    }

    public List getSituationMensuelle() {
        return situationMensuelle;
    }


    public void setListCategoriesPersonne(List listCategoriesPersonne) {
        this.listCategoriesPersonne = listCategoriesPersonne;
    }

    public List getListCategoriesPersonne() {
        return listCategoriesPersonne;
    }

    public void setCategoriePersonne(String categoriePersonne) {
        this.categoriePersonne = categoriePersonne;
    }

    public String getCategoriePersonne() {
        return categoriePersonne;
    }

    public void setEcheance(String echeance) {
        this.echeance = echeance;
    }

    public String getEcheance() {
        return echeance;
    }

    public void setIntBrut(String intBrut) {
        this.intBrut = intBrut;
    }

    public String getIntBrut() {
        return intBrut;
    }

    public void setMntIrc(String mntIrc) {
        this.mntIrc = mntIrc;
    }

    public String getMntIrc() {
        return mntIrc;
    }

    public void setIntNet(String intNet) {
        this.intNet = intNet;
    }

    public String getIntNet() {
        return intNet;
    }

    public void setPrdPlc(String prdPlc) {
        this.prdPlc = prdPlc;
    }

    public String getPrdPlc() {
        return prdPlc;
    }

    public void setTauxIrc(String tauxIrc) {
        this.tauxIrc = tauxIrc;
    }

    public String getTauxIrc() {
        return tauxIrc;
    }

    public void setRecapMouvement(List recapMouvement) {
        this.recapMouvement = recapMouvement;
    }

    public List getRecapMouvement() {
        return recapMouvement;
    }


    public void setContratPlacementView(ContratPlacementView contratPlacementView) {
        this.contratPlacementView = contratPlacementView;
    }

    public ContratPlacementView getContratPlacementView() {
        return contratPlacementView;
    }

    public void setListeAbonnements(List listeAbonnements) {
        this.listeAbonnements = listeAbonnements;
    }

    public List getListeAbonnements() {
        return listeAbonnements;
    }

    public void setSomeNbreAbl(String someNbreAbl) {
        this.someNbreAbl = someNbreAbl;
    }

    public String getSomeNbreAbl() {
        return someNbreAbl;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setSomeAbT(String someAbT) {
        this.someAbT = someAbT;
    }

    public String getSomeAbT() {
        return someAbT;
    }

    public void setSomeAbNT(String someAbNT) {
        this.someAbNT = someAbNT;
    }

    public String getSomeAbNT() {
        return someAbNT;
    }

    public void setNumAvanceRecherch(String numAvanceRecherch) {
        this.numAvanceRecherch = numAvanceRecherch;
    }

    public String getNumAvanceRecherch() {
        return numAvanceRecherch;
    }

    public void setEtatPlacement(String etatPlacement) {
        this.etatPlacement = etatPlacement;
    }

    public String getEtatPlacement() {
        return etatPlacement;
    }

    public void setCodeOperation(String codeOperation) {
        this.codeOperation = codeOperation;
    }

    public String getCodeOperation() {
        return codeOperation;
    }

    public void setCapitalRestRech(String capitalRestRech) {
        this.capitalRestRech = capitalRestRech;
    }

    public String getCapitalRestRech() {
        return capitalRestRech;
    }


    public void setNumInteretChoisi(String numInteretChoisi) {
        this.numInteretChoisi = numInteretChoisi;
    }

    public String getNumInteretChoisi() {
        return numInteretChoisi;
    }

    public void setNumeroIntChoisi(String numeroIntChoisi) {
        this.numeroIntChoisi = numeroIntChoisi;
    }

    public String getNumeroIntChoisi() {
        return numeroIntChoisi;
    }


    public void setTypeFaveurPlac(String typeFaveurPlac) {
        this.typeFaveurPlac = typeFaveurPlac;
    }

    public String getTypeFaveurPlac() {
        return typeFaveurPlac;
    }

    public void setSommeCapitalActualise(String sommeCapitalActualise) {
        this.sommeCapitalActualise = sommeCapitalActualise;
    }

    public String getSommeCapitalActualise() {
        return sommeCapitalActualise;
    }

    public void setSituationMensuelleInt(List situationMensuelleInt) {
        this.situationMensuelleInt = situationMensuelleInt;
    }

    public List getSituationMensuelleInt() {
        return situationMensuelleInt;
    }
}


