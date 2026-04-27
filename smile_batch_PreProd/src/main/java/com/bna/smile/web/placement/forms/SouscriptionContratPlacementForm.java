package com.bna.smile.web.placement.forms;

import com.bna.commun.model.ContratCpt;
import com.bna.commun.model.ContratPlacement;
import com.bna.commun.model.DemandeDecision;

import com.bna.smile.web.commun.model.PersonneDemandeur;
import com.bna.smile.web.commun.model.Pouvoir;
import com.bna.smile.web.commun.view.ContratView;
import com.bna.smile.web.commun.view.InitialisationView;

import com.bna.smile.web.placement.view.ContratPlacementView;
import com.bna.smile.web.placement.view.DemandeDecisionView;

import java.util.Collection;

import org.apache.struts.action.ActionForm;

public class SouscriptionContratPlacementForm extends ActionForm {
    /**Reset all properties to their default values.
     * @param mapping The ActionMapping used to select this instance.
     * @param request The HTTP Request we are processing.
     */    

     public SouscriptionContratPlacementForm() {
     }
 
     private DemandeDecisionView  demandeDecisionView = new  DemandeDecisionView(); 
     private ContratView contratView = new ContratView();
     private InitialisationView initialisationView = new InitialisationView();
     private PersonneDemandeur personneDemandeur = new PersonneDemandeur();
     private ContratPlacement contratPlacement = new ContratPlacement();
     // données cocernant la forme Demande decision :
     private String typeForm;
     private String libelleOperation;
     private String libelleConfirmation;
     private String typePieceAjax;
     private String numPieceAjax;
     private String nomAjax;
     private String prenomAjax;
     private String alertCptPlac="";
     private String titreConfirmation="";
     private String choixRecherche;
     private Collection listContratCpt;
     private String cleContratChoisi;
     private Collection listeContratsPlacement;
     private String cleCplaChoisi;     
     private String contratChoisi;
     private String mntMinPlc="";
     private String mntMaxPlc="";
     private String durMinPlc="";
     private String durMaxPlc="";
     private String mntnominPlc="";
     private String libAbrPlc="";
     private String libStrvPlc="";
     private String dateEch;
     private Long nbrContrat;
     private String produitAjax;
     private String libRmqDemd="";
     private String interetNet="";
     private String boolTauxIrc="";
     
    // champs ajax pour la condition de banque
    private String operation;
    private String  numCcptCcpt;
    private String codStrcStrc;
    private String codPrdPrd;
    private String codTpceTpce;
    private String numPcePers;
    private String numRcbDemd;
    private String montPlaDemd;
    private String numDureDemd;
    private String dateActuelle;
    private String tauxInt;
    private String dateVal;
    private String tmm;
    private String marge;
    private String signe;
    private String signeMarge;
    private String activerAlert="0";
    
     // données concernant la forme de recherche des demandes
     private String typePieceId;
     private String numPieceId;
     private String numDemdRech;
     private String dateDebut;
     private String dateFin;
     private String choix;
     private String numDemandeChoisi ="";
     private String numDemandeChoisie ="";
     private Collection listeDemandesDecision;
     private DemandeDecision demandeDecision;
     private String alertDemandeDecision=""; // alerte si la demande est validée ou traitée ou inexistante (cette demande n'est pas en attente de réponse de la trésorerie
     private String libAjoutRmq="";   
     private String structureRech="";
     private String produitRech="";
     private String dateDebRecherch="";
     private String dateFinRecherch="";

     private String typeSouscripteur;
     private String alerteSolde="";
     //données concernant l'interface de souscription contrat placement
    private ContratPlacementView contratPlacementView = new ContratPlacementView();
    private Collection listeCcptDav;
    private String numCcptDav;
    private String structDecision;
    private String choixNumDde;
    private ContratCpt contratCpt;
    private String soldeFinal;
    private String alertContrat="";
    private String etatContrat="";
    private Pouvoir pouvoir;
    private String typeDemandeur;
    private String alertDemandeur=""; 
    private String alertAfficheDde ="";
    private String dureeAjax;
    private String dateEchAjax;
    private String alertPouvoir="";
    private String typePersonne="";
    private String alertCD="listeVide";
    //données de la page de validation souscription contrat placement
     private String choixValidPlac="";
    private String alertTauxFaveur="";
    private String alertMatriculeFiscale="";
     
    private String nbrDemandeAlertes="0";
    private String etatDemandeAlertes;
    private String boolActiverAlert;
    private String nbrDemandeAttAlertes="0";
    private Collection listeProduitsPlacement; 
    private String remarquesPredefinis;
   
    private String numBonCaisseAjax;
    private String codeStructureAjax;
    private String existBCAjax;
    private String existDBCAjax;
    private String numSeqBCAjax;
   
    private String typeMenu="";
    private String dateValNvSousc;
    private String codSbdvDemd="";
    private String dateValAjax="";
    private String nbrJoursOuvrable="";
    private String typeDValeur;
  
    
      
    
       
    public void clearFormRechercheDemandeDecision(){
        //typePieceId="";
        numPieceId="";
        numDemdRech="";
        dateDebut="";
        dateFin="";     
        numDemandeChoisie="";         
        listeDemandesDecision = null;
        demandeDecisionView = new DemandeDecisionView();
        demandeDecision = new  DemandeDecision();
        alertDemandeDecision="";
        alertCD="listeVide";
        contratView = new ContratView();
        personneDemandeur = new PersonneDemandeur();
        contratView.clear();
        mntMinPlc="";
        mntMaxPlc="";
        durMinPlc="";
        durMaxPlc="";
        mntnominPlc="";
        libAbrPlc="";  
        libStrvPlc = "";
        libRmqDemd="";
        interetNet="";
        nbrDemandeAlertes="0";
        etatDemandeAlertes="";
        activerAlert="0";
        tmm="";
        alertTauxFaveur="";
            
    }
    
    public void clearFormRechercheDemandeDecisionSansParam(){
        //typePieceId="";
        //numPieceId="";
        //numDemdRech="";
        //dateDebut="";
        //dateFin="";     
        numDemandeChoisie="";         
        listeDemandesDecision = null;
        demandeDecisionView = new DemandeDecisionView();
        demandeDecision = new  DemandeDecision();
        alertDemandeDecision="";
        alertCD="listeVide";
        contratView = new ContratView();
        personneDemandeur = new PersonneDemandeur();
        contratView.clear();
        mntMinPlc="";
        mntMaxPlc="";
        durMinPlc="";
        durMaxPlc="";
        mntnominPlc="";
        libAbrPlc="";  
        libStrvPlc = "";
        libRmqDemd="";
        interetNet="";
        nbrDemandeAlertes="0";
        etatDemandeAlertes="";
        activerAlert="0";
        libAjoutRmq="";
        tmm="";
        
    }
    
    public void clearFormDemandeDecision (){
        demandeDecisionView = new DemandeDecisionView();  
        demandeDecision = new  DemandeDecision();
        typePieceAjax="";
        numPieceAjax="";
        nomAjax="";
        prenomAjax="";
        alertContrat="";
        alertCptPlac="";
        libelleConfirmation="";
        contratView = new ContratView();
        personneDemandeur = new PersonneDemandeur();
        listContratCpt = null;        
        listeContratsPlacement = null;
        contratChoisi="";
        cleContratChoisi=null;
        mntMinPlc="";
        mntMaxPlc="";
        durMinPlc="";
        durMaxPlc="";
        mntnominPlc="";
        libAbrPlc="";  
        libStrvPlc = "";
        alertDemandeur=""; 
        nbrContrat=null;
        libRmqDemd="";
        interetNet="";
        boolTauxIrc="";
        alertMatriculeFiscale="";
        tmm="";
        dateValAjax="";
        nbrJoursOuvrable="";
        
    }
    
    public void clearFormSouscriptionCptPlacement(){
        contratPlacementView = new ContratPlacementView();       
        listeCcptDav=null;
        numCcptDav="";        
        contratCpt = new ContratCpt();
        soldeFinal="";
        alertContrat="";
        etatContrat="";
        contratView.clear();               
        personneDemandeur.clear();
        typeDemandeur="";
        alertDemandeur="";
        alertAfficheDde ="";
        alertPouvoir="";
        typePersonne="";
        alertCD="listeVide";
 
    }
    
    
    
    public void setDemandeDecisionView(DemandeDecisionView demandeDecisionView) {
        this.demandeDecisionView = demandeDecisionView;
    }

    public DemandeDecisionView getDemandeDecisionView() {
        return demandeDecisionView;
    }

    public void setContratView(ContratView contratView) {
        this.contratView = contratView;
    }

    public ContratView getContratView() {
        return contratView;
    }

    public void setInitialisationView(InitialisationView initialisationView) {
        this.initialisationView = initialisationView;
    }

    public InitialisationView getInitialisationView() {
        return initialisationView;
    }

    public void setPersonneDemandeur(PersonneDemandeur personneDemandeur) {
        this.personneDemandeur = personneDemandeur;
    }

    public PersonneDemandeur getPersonneDemandeur() {
        return personneDemandeur;
    }

    public void setContratPlacement(ContratPlacement contratPlacement) {
        this.contratPlacement = contratPlacement;
    }

    public ContratPlacement getContratPlacement() {
        return contratPlacement;
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

    public void setTypePieceAjax(String typePieceAjax) {
        this.typePieceAjax = typePieceAjax;
    }

    public String getTypePieceAjax() {
        return typePieceAjax;
    }

    public void setNumPieceAjax(String numPieceAjax) {
        this.numPieceAjax = numPieceAjax;
    }

    public String getNumPieceAjax() {
        return numPieceAjax;
    }

    public void setNomAjax(String nomAjax) {
        this.nomAjax = nomAjax;
    }

    public String getNomAjax() {
        return nomAjax;
    }

    public void setPrenomAjax(String prenomAjax) {
        this.prenomAjax = prenomAjax;
    }

    public String getPrenomAjax() {
        return prenomAjax;
    }

    public void setAlertCptPlac(String alertCptPlac) {
        this.alertCptPlac = alertCptPlac;
    }

    public String getAlertCptPlac() {
        return alertCptPlac;
    }

    public void setLibelleConfirmation(String libelleConfirmation) {
        this.libelleConfirmation = libelleConfirmation;
    }

    public String getLibelleConfirmation() {
        return libelleConfirmation;
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

    public void setNumDemandeChoisi(String numDemandeChoisi) {
        this.numDemandeChoisi = numDemandeChoisi;
    }

    public String getNumDemandeChoisi() {
        return numDemandeChoisi;
    }

    public void setNumDemandeChoisie(String numDemandeChoisie) {
        this.numDemandeChoisie = numDemandeChoisie;
    }

    public String getNumDemandeChoisie() {
        return numDemandeChoisie;
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

    public void setTypeSouscripteur(String typeSouscripteur) {
        this.typeSouscripteur = typeSouscripteur;
    }

    public String getTypeSouscripteur() {
        return typeSouscripteur;
    }

    public void setAlerteSolde(String alerteSolde) {
        this.alerteSolde = alerteSolde;
    }

    public String getAlerteSolde() {
        return alerteSolde;
    }

    public void setContratPlacementView(ContratPlacementView contratPlacementView) {
        this.contratPlacementView = contratPlacementView;
    }

    public ContratPlacementView getContratPlacementView() {
        return contratPlacementView;
    }

    public void setListeCcptDav(Collection listeCcptDav) {
        this.listeCcptDav = listeCcptDav;
    }

    public Collection getListeCcptDav() {
        return listeCcptDav;
    }

    public void setNumCcptDav(String numCcptDav) {
        this.numCcptDav = numCcptDav;
    }

    public String getNumCcptDav() {
        return numCcptDav;
    }

    public void setStructDecision(String structDecision) {
        this.structDecision = structDecision;
    }

    public String getStructDecision() {
        return structDecision;
    }

    public void setChoixNumDde(String choixNumDde) {
        this.choixNumDde = choixNumDde;
    }

    public String getChoixNumDde() {
        return choixNumDde;
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

    public void setAlertDemandeur(String alertDemandeur) {
        this.alertDemandeur = alertDemandeur;
    }

    public String getAlertDemandeur() {
        return alertDemandeur;
    }

    public void setAlertAfficheDde(String alertAfficheDde) {
        this.alertAfficheDde = alertAfficheDde;
    }

    public String getAlertAfficheDde() {
        return alertAfficheDde;
    }

    public void setDureeAjax(String dureeAjax) {
        this.dureeAjax = dureeAjax;
    }

    public String getDureeAjax() {
        return dureeAjax;
    }

    public void setDateEchAjax(String dateEchAjax) {
        this.dateEchAjax = dateEchAjax;
    }

    public String getDateEchAjax() {
        return dateEchAjax;
    }

    public void setAlertPouvoir(String alertPouvoir) {
        this.alertPouvoir = alertPouvoir;
    }

    public String getAlertPouvoir() {
        return alertPouvoir;
    }

    public void setTypePersonne(String typePersonne) {
        this.typePersonne = typePersonne;
    }

    public String getTypePersonne() {
        return typePersonne;
    }

    public void setAlertCD(String alertCD) {
        this.alertCD = alertCD;
    }

    public String getAlertCD() {
        return alertCD;
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

    public void setChoixRecherche(String choixRecherche) {
        this.choixRecherche = choixRecherche;
    }

    public String getChoixRecherche() {
        return choixRecherche;
    }

    public void setListContratCpt(Collection listContratCpt) {
        this.listContratCpt = listContratCpt;
    }

    public Collection getListContratCpt() {
        return listContratCpt;
    }

    public void setCleContratChoisi(String cleContratChoisi) {
        this.cleContratChoisi = cleContratChoisi;
    }

    public String getCleContratChoisi() {
        return cleContratChoisi;
    }

    public void setListeContratsPlacement(Collection listeContratsPlacement) {
        this.listeContratsPlacement = listeContratsPlacement;
    }

    public Collection getListeContratsPlacement() {
        return listeContratsPlacement;
    }

    public void setContratChoisi(String contratChoisi) {
        this.contratChoisi = contratChoisi;
    }

    public String getContratChoisi() {
        return contratChoisi;
    }

    public void setMntMinPlc(String mntMinPlc) {
        this.mntMinPlc = mntMinPlc;
    }

    public String getMntMinPlc() {
        return mntMinPlc;
    }

    public void setMntMaxPlc(String mntMaxPlc) {
        this.mntMaxPlc = mntMaxPlc;
    }

    public String getMntMaxPlc() {
        return mntMaxPlc;
    }

    public void setDurMinPlc(String durMinPlc) {
        this.durMinPlc = durMinPlc;
    }

    public String getDurMinPlc() {
        return durMinPlc;
    }

    public void setDurMaxPlc(String durMaxPlc) {
        this.durMaxPlc = durMaxPlc;
    }

    public String getDurMaxPlc() {
        return durMaxPlc;
    }

    public void setMntnominPlc(String mntnominPlc) {
        this.mntnominPlc = mntnominPlc;
    }

    public String getMntnominPlc() {
        return mntnominPlc;
    }

    public void setLibAbrPlc(String libAbrPlc) {
        this.libAbrPlc = libAbrPlc;
    }

    public String getLibAbrPlc() {
        return libAbrPlc;
    }

    public void setLibStrvPlc(String libStrvPlc) {
        this.libStrvPlc = libStrvPlc;
    }

    public String getLibStrvPlc() {
        return libStrvPlc;
    }

    public void setDateEch(String dateEch) {
        this.dateEch = dateEch;
    }

    public String getDateEch() {
        return dateEch;
    }


    public void setNbrContrat(Long nbrContrat) {
        this.nbrContrat = nbrContrat;
    }

    public Long getNbrContrat() {
        return nbrContrat;
    }

    public void setProduitAjax(String produitAjax) {
        this.produitAjax = produitAjax;
    }

    public String getProduitAjax() {
        return produitAjax;
    }

    public void setLibRmqDemd(String libRmqDemd) {
        this.libRmqDemd = libRmqDemd;
    }

    public String getLibRmqDemd() {
        return libRmqDemd;
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

    public void setInteretNet(String interetNet) {
        this.interetNet = interetNet;
    }

    public String getInteretNet() {
        return interetNet;
    }

    public void setNbrDemandeAlertes(String nbrDemandeAlertes) {
        this.nbrDemandeAlertes = nbrDemandeAlertes;
    }

    public String getNbrDemandeAlertes() {
        return nbrDemandeAlertes;
    }

    public void setEtatDemandeAlertes(String etatDemandeAlertes) {
        this.etatDemandeAlertes = etatDemandeAlertes;
    }

    public String getEtatDemandeAlertes() {
        return etatDemandeAlertes;
    }

    public void setBoolActiverAlert(String boolActiverAlert) {
        this.boolActiverAlert = boolActiverAlert;
    }

    public String getBoolActiverAlert() {
        return boolActiverAlert;
    }

    public void setOperation(String operation) {
        this.operation = operation;
    }

    public String getOperation() {
        return operation;
    }

    public void setNumCcptCcpt(String numCcptCcpt) {
        this.numCcptCcpt = numCcptCcpt;
    }

    public String getNumCcptCcpt() {
        return numCcptCcpt;
    }

    public void setCodStrcStrc(String codStrcStrc) {
        this.codStrcStrc = codStrcStrc;
    }

    public String getCodStrcStrc() {
        return codStrcStrc;
    }

    public void setCodPrdPrd(String codPrdPrd) {
        this.codPrdPrd = codPrdPrd;
    }

    public String getCodPrdPrd() {
        return codPrdPrd;
    }

    public void setCodTpceTpce(String codTpceTpce) {
        this.codTpceTpce = codTpceTpce;
    }

    public String getCodTpceTpce() {
        return codTpceTpce;
    }

    public void setNumPcePers(String numPcePers) {
        this.numPcePers = numPcePers;
    }

    public String getNumPcePers() {
        return numPcePers;
    }

    public void setNumRcbDemd(String numRcbDemd) {
        this.numRcbDemd = numRcbDemd;
    }

    public String getNumRcbDemd() {
        return numRcbDemd;
    }

    public void setMontPlaDemd(String montPlaDemd) {
        this.montPlaDemd = montPlaDemd;
    }

    public String getMontPlaDemd() {
        return montPlaDemd;
    }

    public void setNumDureDemd(String numDureDemd) {
        this.numDureDemd = numDureDemd;
    }

    public String getNumDureDemd() {
        return numDureDemd;
    }

    public void setDateActuelle(String dateActuelle) {
        this.dateActuelle = dateActuelle;
    }

    public String getDateActuelle() {
        return dateActuelle;
    }

    public void setTauxInt(String tauxInt) {
        this.tauxInt = tauxInt;
    }

    public String getTauxInt() {
        return tauxInt;
    }

    public void setDateVal(String dateVal) {
        this.dateVal = dateVal;
    }

    public String getDateVal() {
        return dateVal;
    }

    public void setActiverAlert(String activerAlert) {
        this.activerAlert = activerAlert;
    }

    public String getActiverAlert() {
        return activerAlert;
    }

    public void setBoolTauxIrc(String boolTauxIrc) {
        this.boolTauxIrc = boolTauxIrc;
    }

    public String getBoolTauxIrc() {
        return boolTauxIrc;
    }

    public void setNbrDemandeAttAlertes(String nbrDemandeAttAlertes) {
        this.nbrDemandeAttAlertes = nbrDemandeAttAlertes;
    }

    public String getNbrDemandeAttAlertes() {
        return nbrDemandeAttAlertes;
    }

    public void setListeProduitsPlacement(Collection listeProduitsPlacement) {
        this.listeProduitsPlacement = listeProduitsPlacement;
    }

    public Collection getListeProduitsPlacement() {
        return listeProduitsPlacement;
    }

    public void setCleCplaChoisi(String cleCplaChoisi) {
        this.cleCplaChoisi = cleCplaChoisi;
    }

    public String getCleCplaChoisi() {
        return cleCplaChoisi;
    }

    public void setLibAjoutRmq(String libAjoutRmq) {
        this.libAjoutRmq = libAjoutRmq;
    }

    public String getLibAjoutRmq() {
        return libAjoutRmq;
    }

    public void setTmm(String tmm) {
        this.tmm = tmm;
    }

    public String getTmm() {
        return tmm;
    }

    public void setAlertTauxFaveur(String alertTauxFaveur) {
        this.alertTauxFaveur = alertTauxFaveur;
    }

    public String getAlertTauxFaveur() {
        return alertTauxFaveur;
    }

    public void setAlertMatriculeFiscale(String alertMatriculeFiscale) {
        this.alertMatriculeFiscale = alertMatriculeFiscale;
    }

    public String getAlertMatriculeFiscale() {
        return alertMatriculeFiscale;
    }

    public void setRemarquesPredefinis(String remarquesPredefinis) {
        this.remarquesPredefinis = remarquesPredefinis;
    }

    public String getRemarquesPredefinis() {
        return remarquesPredefinis;
    }

    public void setTypeMenu(String typeMenu) {
        this.typeMenu = typeMenu;
    }

    public String getTypeMenu() {
        return typeMenu;
    }

    public void setMarge(String marge) {
        this.marge = marge;
    }

    public String getMarge() {
        return marge;
    }


    public void setSigne(String signe) {
        this.signe = signe;
    }

    public String getSigne() {
        return signe;
    }

    public void setSigneMarge(String signeMarge) {
        this.signeMarge = signeMarge;
    }

    public String getSigneMarge() {
        return signeMarge;
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

    public void setDateValNvSousc(String dateValNvSousc) {
        this.dateValNvSousc = dateValNvSousc;
    }

    public String getDateValNvSousc() {
        return dateValNvSousc;
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

   
}


