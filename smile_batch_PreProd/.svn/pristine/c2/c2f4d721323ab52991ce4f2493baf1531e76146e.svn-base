package com.bna.smile.web.placement.forms;

import com.bna.commun.model.ContratPlacement;
import com.bna.commun.model.DemandeDecision;
import com.bna.smile.web.commun.view.InitialisationView;
import com.bna.smile.web.placement.view.DemandeDecisionView;

import java.util.Collection;
import java.util.List;

import org.apache.struts.action.ActionForm;


public class AutresOperationsPlacementForm extends ActionForm {
    /**Reset all properties to their default values.
     * @param mapping The ActionMapping used to select this instance.
     * @param request The HTTP Request we are processing.
     */    

     public AutresOperationsPlacementForm() {
     }
 
     private DemandeDecisionView  demandeDecisionView = new  DemandeDecisionView();     
     private InitialisationView initialisationView = new InitialisationView();
     // données cocernant la forme Demande decision :
     private String libelleOperation;    
     private String choixRecherche;
     // données concernant la forme de recherche des demandes
     private String typePieceId;
     private String numPieceId;     
     private String choix;    
     private String alertDemandeDecision=""; // alerte si la demande est validée ou traitée ou inexistante (cette demande n'est pas en attente de réponse de la trésorerie
     
     private String codStrcRech;
     private String codPrdRech;
     private String numCcptRech;       
    
     private String etatDemande;    
     private String typeForm;
     private Collection listeContratPlacement;
     private String numCplaRech;
     private Long   numSeqPersRech;
     
     private String codeAgence;
     private String codeProduit;
     private String numeroCompte;
     
    private String numPlacement;
     
     private String numeroBcARecuperer;
     private String numeroBcRecupere;
     private ContratPlacement contratPlacement;
     private String codeOperation;
     private String checkCpla;
     private Collection listeBcRecupere;
     private String dateDebRecherch;
     private String dateFinRecherch;
     private String numBcRech;
     private String numDebBC;
     private String numFinBC;
     private String numNbreBC;
     private String errorMessage;
    
    
    public void clearFormAutresOperations(){
        //typePieceId="";
        numPieceId="";  
        etatDemande="0";        
        demandeDecisionView = new DemandeDecisionView();
        alertDemandeDecision="";
        codStrcRech="";
        codPrdRech="";
        numCcptRech="";
        numeroBcARecuperer="";
        numeroBcRecupere="";
        listeBcRecupere = null;
        dateDebRecherch="";
        dateFinRecherch="";
        numCplaRech="";
        numBcRech="";
        numFinBC="";
        numDebBC="";
        numNbreBC="";
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
    public void setChoix(String choix) {
        this.choix = choix;
    }

    public String getChoix() {
        return choix;
    }
    public void setAlertDemandeDecision(String alertDemandeDecision) {
        this.alertDemandeDecision = alertDemandeDecision;
    }

    public String getAlertDemandeDecision() {
        return alertDemandeDecision;
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
    public void setNumSeqPersRech(Long numSeqPersRech) {
        this.numSeqPersRech = numSeqPersRech;
    }

    public Long getNumSeqPersRech() {
        return numSeqPersRech;
    }

    public void setTypeForm(String typeForm) {
        this.typeForm = typeForm;
    }

    public String getTypeForm() {
        return typeForm;
    }


    public void setCodStrcRech(String codStrcRech) {
        this.codStrcRech = codStrcRech;
    }

    public String getCodStrcRech() {
        return codStrcRech;
    }

    public void setCodPrdRech(String codPrdRech) {
        this.codPrdRech = codPrdRech;
    }

    public String getCodPrdRech() {
        return codPrdRech;
    }

    public void setNumCcptRech(String numCcptRech) {
        this.numCcptRech = numCcptRech;
    }

    public String getNumCcptRech() {
        return numCcptRech;
    }

    public void setNumeroBcARecuperer(String numeroBcARecuperer) {
        this.numeroBcARecuperer = numeroBcARecuperer;
    }

    public String getNumeroBcARecuperer() {
        return numeroBcARecuperer;
    }

    public void setNumeroBcRecupere(String numeroBcRecupere) {
        this.numeroBcRecupere = numeroBcRecupere;
    }

    public String getNumeroBcRecupere() {
        return numeroBcRecupere;
    }

    public void setNumPlacement(String numPlacement) {
        this.numPlacement = numPlacement;
    }

    public String getNumPlacement() {
        return numPlacement;
    }

    public void setContratPlacement(ContratPlacement contratPlacement) {
        this.contratPlacement = contratPlacement;
    }

    public ContratPlacement getContratPlacement() {
        return contratPlacement;
    }

    public void setCodeOperation(String codeOperation) {
        this.codeOperation = codeOperation;
    }

    public String getCodeOperation() {
        return codeOperation;
    }

    public void setCheckCpla(String checkCpla) {
        this.checkCpla = checkCpla;
    }

    public String getCheckCpla() {
        return checkCpla;
    }

    public void setListeBcRecupere(Collection listeBcRecupere) {
        this.listeBcRecupere = listeBcRecupere;
    }

    public Collection getListeBcRecupere() {
        return listeBcRecupere;
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

    public void setNumBcRech(String numBcRech) {
        this.numBcRech = numBcRech;
    }

    public String getNumBcRech() {
        return numBcRech;
    }

    public void setNumDebBC(String numDebBC) {
        this.numDebBC = numDebBC;
    }

    public String getNumDebBC() {
        return numDebBC;
    }

    public void setNumFinBC(String numFinBC) {
        this.numFinBC = numFinBC;
    }

    public String getNumFinBC() {
        return numFinBC;
    }

    public void setNumNbreBC(String numNbreBC) {
        this.numNbreBC = numNbreBC;
    }

    public String getNumNbreBC() {
        return numNbreBC;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String getErrorMessage() {
        return errorMessage;
    }
}


