package com.bna.smile.web.moyenPaiement.demandeCarteBnacaire.forms;

import com.bna.commun.model.CarteBancaire;
import com.bna.commun.model.CoTitulaire;
import com.bna.commun.model.ContratCpt;
import com.bna.commun.model.DemandeCarte;
import com.bna.commun.model.DemandeCheque;

import com.bna.commun.model.Personne;

import com.bna.smile.model.domainecontratcompte.moyensPaiement.model.ParamRechercheDemandeCarte;

import fr.improve.struts.taglib.layout.datagrid.Datagrid;

import java.util.ArrayList;
import java.util.Collection;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

public class RechercheDemandesCartesForm extends ActionForm {
    // Titre 
    private String libelleOperation;
    private String codeOperation;
    private String codeTache;
    private ParamRechercheDemandeCarte paramRechercheDemandeCarte;//code agence, etats de recherche ...
    private String libListeChoix;
    private String libChoisie;
    private String typeTraitement;// pour sauvgarder si on traites des demandes : la valeur "DEMANDES" si des carte "CARTES"
    private String libChoixNumero; 
    private String libFieldsetChoix; 

    //// données concernant la page de recherche des demandes lors de la consultation
     private String numMatrUser;
     private String codeAgance;
     private String dateActuelle;
     private String alertRecherche;
     private String messageAlertRecherche;
     
     
     private String choixEtatDemande;
     private String choixEtatCarte;
     private String choix;
     private String numDemande;
     private String typePieceId;
     private String numPieceId;
     private String codStrcRech;
     private String codPrdRech;
     private String numCcptRech;
     private String dateDebutRech;
     private String dateFinRech;
     private Collection listeDemandesCartes;
     private Collection listeCartesBancaires;
     private String numChoisie; 
     private DemandeCarte demandeCarte; 
     private CarteBancaire carteBancaire; 
     
     
     //// données concernaant la demande carte
      // Informations du contrat
      private String  openTabSheetDemande = "false";
      private String codStrcStrc;
      private String codPrdPrd;
      private String numCcptCcpt;
      private String cleCompte;
      private ContratCpt contratCpt;
      private String etatContrat;
      private String montSoldCcpt;
      private String nomIntiCcpt;
      private String devise;

      // Informations du client
      private String codTpceTpceClient;
      private String numPcePersClient;
      private String nomNomPersClient;
      private String nomPrnPersClient;
      private String adresseResidenceClient;
      private String adresseCorrespondanceClient;
      

      // Information du demandeur
      private String codTpceTpceDemandeur;
      private String numPcePersDemandeur;
      private String nomNomPersDemandeur;
      private String nomPrnPersDemandeur; 
      private Personne demandeur;
      private String idDemandeur;
      

      private String numSeqCliCotitulaire;
      private String typeSignatureCotitulaire;
      private String typeCotitulaire;
      private CoTitulaire cotitulaire;
      // 

      private List listCotitulaires = new ArrayList();
      private List listMandats = new ArrayList(); // list les mandats valides pour ce contrat
      private List listMandatsConcernesPersonne = new ArrayList(); // liste des mandats valide qui concerne la personne pour cette operation dans ce contrat    
      private List listMandatsPersonneConcernesDemandeur = new ArrayList();
      private List listMandataireCoches = new ArrayList();
      private String reqCode;

      // information du mandat selectionné 
      private String numeroMandatChoisi;
      private String signatureMandatChoisi;
      private String nbrMinMandatChoisi;
      private String typeMandatChoisi;
      private String nbreSignataires;
      private String refMand;
      private String numOperation;
      private List listdesMandatsPersonneChoisi = new ArrayList();
      private String numeroOperationMandatChoisi;

      //-- Données de Test
      private String alert;
      private String testExistanceMandat; //"O" si le contrat existe ;"N" sinon
      private String typePersonne; // personnePhysique;personneMorale;entiteCotitulaire
      private String categoriePersonne; // categorie personne
      private String typeDemandeur; // T: en cas de Titulaire; M: mandataire, C; entite co-titulaire; MC: mandat et cotitulaire
     // private String codeOperation; //code de l'operation Guicher
      private String testExistanceContrat; // (O : si le contrat existe /N :si le contrat n'existe pas )
      private String testExistanceDemandeur;
      private String alertDemandeur;
      
      //-------------- données de test d'affichage dans la page JSP 
       private String messageTexte;

      //--------------------
      private String tabsheetActif;
      ///private String choixTypeConfection;
      ///private String choixTypeChq;
      private String openTabSheetCotitulaire="false";
      private String openTabSheetMandat="false";
      private String openTabSheetOperation="false";
      private String messageAlerte="";
     // private String numMatrUser;
      ///private String codePage;
      ///private String libPage;
      
      //donnee pour la demande carte
      private String codTcarTcar;
      private List listeTypeCarte;
      private String codAutDcar;
      private String salarie;
      private String domicilie;
      private String montSalDcar;
      private String montDretDcar;
      private String montDachDcar;
      private String codOperTcar;
      private String boolPlafTcar;
      private String codEtatDcar;
      private String codMotifRejet;
      private String avecAutorisation;
      
      //donnees pour validation
      private String montPlafRet;
      private String montPlafAch;
      private String natureGarantie;
      private String constitue;
      private String dateDernOperation;
      private String libEtat;
      
      private String numCarte;
      private String dateFinCarte;
      private String dureeCarte;
        
      private String nomAnnlCarb;
      
      private String libRempCarb; 
      private String numCarteRemplace;
      private String dateFinCarteRemp;
        
      private String malConfectRecept; 
      
      private String plafondRetrait;
      private String plafondAchat;
      private String boolModpDcar;
      private String nomModfDcar;
      
      
    
    
    
     
    //clear de la tabsheet de recherche
    public void clearTabRecherche() {
       
       choix = "";
       choixEtatDemande = "0";
       choixEtatCarte = "0";
       numDemande = "";
       typePieceId = "";
       numPieceId = "";
     //  codStrcRech = "";
       codPrdRech = "";
       numCcptRech = "";
       dateDebutRech = "";
       dateFinRech = "";
       listeDemandesCartes = null;
       listeCartesBancaires = null;
       numChoisie = ""; 
       demandeCarte = null; 
       carteBancaire = null;
       openTabSheetDemande = "false";
      
    
    }
    
    public void clearTabDemande() {  
    
        openTabSheetDemande = "false";
        alertRecherche = "";
        messageAlertRecherche = "";
        // Informations du contrat
      //  codStrcStrc = "";
        codPrdPrd = "";
        numCcptCcpt = "";
        contratCpt = null;
        cleCompte = "";
        montSoldCcpt = "";
        nomIntiCcpt = "";
        devise = "";
        // Informations du client      
        codTpceTpceClient = "";
        numPcePersClient = "";
        nomNomPersClient = "";
        nomPrnPersClient = "";
        adresseResidenceClient = "";
        adresseCorrespondanceClient = "";

        // Information du demandeur  
        codTpceTpceDemandeur = "";
        numPcePersDemandeur = "";
        nomNomPersDemandeur = "";
        nomPrnPersDemandeur = "";


        numSeqCliCotitulaire = "";
        typeSignatureCotitulaire = "";
        typeCotitulaire = "";

        // Information du mandat choisi
        numeroMandatChoisi = "";
        signatureMandatChoisi="";
        nbrMinMandatChoisi="";
        typeMandatChoisi="";
        nbreSignataires="";
        
        refMand = "";
        numOperation="";
        listdesMandatsPersonneChoisi = new ArrayList();
        numeroOperationMandatChoisi = "";

        listCotitulaires = new ArrayList();
        listMandats = new ArrayList();
        listMandatsConcernesPersonne = new ArrayList();       
        listMandatsPersonneConcernesDemandeur = new ArrayList();  
        listMandataireCoches = new ArrayList(); 
        
        alert = "";
        typeDemandeur = "";
        
        //---- données de test
        testExistanceContrat = ""; //"O" si le contrat existe ;"N" sinon
        typePersonne = ""; // personnePhysique;personneMorale;entiteCotitulaire
         categoriePersonne="";
        testExistanceMandat = ""; // "O" s'il existe des mandat pour ce contrat
        testExistanceDemandeur = ""; // "O" si le demandeur existe.
        //-------------- données de test d'affichage dans la page JSP 
        ///activerDemandeur = "O";
        messageTexte = "";
        alertDemandeur = "";
        
        tabsheetActif ="1";
        openTabSheetCotitulaire="false";
        openTabSheetMandat="false";
        openTabSheetOperation="false";
        messageAlerte="";
        
        codTcarTcar = "";
        listeTypeCarte = null;
        codAutDcar = "";
        salarie = "";
        domicilie = "";
        montSalDcar = "";
        montDretDcar = "";
        montDachDcar = "";
        codOperTcar = "";
        boolPlafTcar = "";
        codEtatDcar = "";
        codMotifRejet = "";
        avecAutorisation = "";
        
        montPlafRet = "";
        montPlafAch = "";
        natureGarantie = "";
        constitue = "";
        dateDernOperation = "";
        libEtat = "";
        
        numCarte = "";
        dateFinCarte = "";
        dureeCarte = "";
        nomAnnlCarb = "";
        
        libRempCarb = "";
        numCarteRemplace = "";
        dateFinCarteRemp = "";
        
        malConfectRecept = "";
        
        plafondRetrait ="";
        plafondAchat ="";
        boolModpDcar ="";
        nomModfDcar ="";
        
    
    }
    
    public void clearForm() {
        clearTabRecherche();
        clearTabDemande();
    }


    /**Reset all properties to their default values.
     * @param mapping The ActionMapping used to select this instance.
     * @param request The HTTP Request we are processing.
     */
    public void reset(ActionMapping mapping, HttpServletRequest request) {
        super.reset( mapping, request);
    }

    /**Validate all properties to their default values.
     * @param mapping The ActionMapping used to select this instance.
     * @param request The HTTP Request we are processing.
     * @return ActionErrors A list of all errors found.
     */
    public ActionErrors validate(ActionMapping mapping, 
                                 HttpServletRequest request) {
        return super.validate( mapping, request);
    }

    public void setLibelleOperation(String libelleOperation) {
        this.libelleOperation = libelleOperation;
    }

    public String getLibelleOperation() {
        return libelleOperation;
    }

    public void setChoix(String choix) {
        this.choix = choix;
    }

    public String getChoix() {
        return choix;
    }

    

    public void setNumDemande(String numDemande) {
        this.numDemande = numDemande;
    }

    public String getNumDemande() {
        return numDemande;
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

    public void setDateDebutRech(String dateDebutRech) {
        this.dateDebutRech = dateDebutRech;
    }

    public String getDateDebutRech() {
        return dateDebutRech;
    }

    public void setDateFinRech(String dateFinRech) {
        this.dateFinRech = dateFinRech;
    }

    public String getDateFinRech() {
        return dateFinRech;
    }

    public void setListeDemandesCartes(Collection listeDemandesCartes) {
        this.listeDemandesCartes = listeDemandesCartes;
    }

    public Collection getListeDemandesCartes() {
        return listeDemandesCartes;
    }

   
    
    public void setNumMatrUser(String numMatrUser) {
        this.numMatrUser = numMatrUser;
    }

    public String getNumMatrUser() {
        return numMatrUser;
    }

    public void setCodeAgance(String codeAgance) {
        this.codeAgance = codeAgance;
    }

    public String getCodeAgance() {
        return codeAgance;
    }

    public void setDateActuelle(String dateActuelle) {
        this.dateActuelle = dateActuelle;
    }

    public String getDateActuelle() {
        return dateActuelle;
    }


    public void setCodeOperation(String codeOperation) {
        this.codeOperation = codeOperation;
    }

    public String getCodeOperation() {
        return codeOperation;
    }

    public void setAlertRecherche(String alertRecherche) {
        this.alertRecherche = alertRecherche;
    }

    public String getAlertRecherche() {
        return alertRecherche;
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

    public void setNumCcptCcpt(String numCcptCcpt) {
        this.numCcptCcpt = numCcptCcpt;
    }

    public String getNumCcptCcpt() {
        return numCcptCcpt;
    }

    public void setCleCompte(String cleCompte) {
        this.cleCompte = cleCompte;
    }

    public String getCleCompte() {
        return cleCompte;
    }

    public void setContratCpt(ContratCpt contratCpt) {
        this.contratCpt = contratCpt;
    }

    public ContratCpt getContratCpt() {
        return contratCpt;
    }

    public void setEtatContrat(String etatContrat) {
        this.etatContrat = etatContrat;
    }

    public String getEtatContrat() {
        return etatContrat;
    }

    public void setMontSoldCcpt(String montSoldCcpt) {
        this.montSoldCcpt = montSoldCcpt;
    }

    public String getMontSoldCcpt() {
        return montSoldCcpt;
    }

    public void setNomIntiCcpt(String nomIntiCcpt) {
        this.nomIntiCcpt = nomIntiCcpt;
    }

    public String getNomIntiCcpt() {
        return nomIntiCcpt;
    }

    public void setDevise(String devise) {
        this.devise = devise;
    }

    public String getDevise() {
        return devise;
    }

    public void setCodTpceTpceClient(String codTpceTpceClient) {
        this.codTpceTpceClient = codTpceTpceClient;
    }

    public String getCodTpceTpceClient() {
        return codTpceTpceClient;
    }

    public void setNumPcePersClient(String numPcePersClient) {
        this.numPcePersClient = numPcePersClient;
    }

    public String getNumPcePersClient() {
        return numPcePersClient;
    }

    public void setNomNomPersClient(String nomNomPersClient) {
        this.nomNomPersClient = nomNomPersClient;
    }

    public String getNomNomPersClient() {
        return nomNomPersClient;
    }

    public void setNomPrnPersClient(String nomPrnPersClient) {
        this.nomPrnPersClient = nomPrnPersClient;
    }

    public String getNomPrnPersClient() {
        return nomPrnPersClient;
    }

    public void setAdresseResidenceClient(String adresseResidenceClient) {
        this.adresseResidenceClient = adresseResidenceClient;
    }

    public String getAdresseResidenceClient() {
        return adresseResidenceClient;
    }

    public void setAdresseCorrespondanceClient(String adresseCorrespondanceClient) {
        this.adresseCorrespondanceClient = adresseCorrespondanceClient;
    }

    public String getAdresseCorrespondanceClient() {
        return adresseCorrespondanceClient;
    }

    public void setCodTpceTpceDemandeur(String codTpceTpceDemandeur) {
        this.codTpceTpceDemandeur = codTpceTpceDemandeur;
    }

    public String getCodTpceTpceDemandeur() {
        return codTpceTpceDemandeur;
    }

    public void setNumPcePersDemandeur(String numPcePersDemandeur) {
        this.numPcePersDemandeur = numPcePersDemandeur;
    }

    public String getNumPcePersDemandeur() {
        return numPcePersDemandeur;
    }

    public void setNomNomPersDemandeur(String nomNomPersDemandeur) {
        this.nomNomPersDemandeur = nomNomPersDemandeur;
    }

    public String getNomNomPersDemandeur() {
        return nomNomPersDemandeur;
    }

    public void setNomPrnPersDemandeur(String nomPrnPersDemandeur) {
        this.nomPrnPersDemandeur = nomPrnPersDemandeur;
    }

    public String getNomPrnPersDemandeur() {
        return nomPrnPersDemandeur;
    }

    public void setDemandeur(Personne demandeur) {
        this.demandeur = demandeur;
    }

    public Personne getDemandeur() {
        return demandeur;
    }

    public void setIdDemandeur(String idDemandeur) {
        this.idDemandeur = idDemandeur;
    }

    public String getIdDemandeur() {
        return idDemandeur;
    }

    public void setNumSeqCliCotitulaire(String numSeqCliCotitulaire) {
        this.numSeqCliCotitulaire = numSeqCliCotitulaire;
    }

    public String getNumSeqCliCotitulaire() {
        return numSeqCliCotitulaire;
    }

    public void setTypeSignatureCotitulaire(String typeSignatureCotitulaire) {
        this.typeSignatureCotitulaire = typeSignatureCotitulaire;
    }

    public String getTypeSignatureCotitulaire() {
        return typeSignatureCotitulaire;
    }

    public void setTypeCotitulaire(String typeCotitulaire) {
        this.typeCotitulaire = typeCotitulaire;
    }

    public String getTypeCotitulaire() {
        return typeCotitulaire;
    }

    public void setCotitulaire(CoTitulaire cotitulaire) {
        this.cotitulaire = cotitulaire;
    }

    public CoTitulaire getCotitulaire() {
        return cotitulaire;
    }

    public void setListCotitulaires(List listCotitulaires) {
        this.listCotitulaires = listCotitulaires;
    }

    public List getListCotitulaires() {
        return listCotitulaires;
    }

    public void setListMandats(List listMandats) {
        this.listMandats = listMandats;
    }

    public List getListMandats() {
        return listMandats;
    }

    public void setListMandatsConcernesPersonne(List listMandatsConcernesPersonne) {
        this.listMandatsConcernesPersonne = listMandatsConcernesPersonne;
    }

    public List getListMandatsConcernesPersonne() {
        return listMandatsConcernesPersonne;
    }

    public void setListMandatsPersonneConcernesDemandeur(List listMandatsPersonneConcernesDemandeur) {
        this.listMandatsPersonneConcernesDemandeur = listMandatsPersonneConcernesDemandeur;
    }

    public List getListMandatsPersonneConcernesDemandeur() {
        return listMandatsPersonneConcernesDemandeur;
    }

    public void setListMandataireCoches(List listMandataireCoches) {
        this.listMandataireCoches = listMandataireCoches;
    }

    public List getListMandataireCoches() {
        return listMandataireCoches;
    }

    public void setReqCode(String reqCode) {
        this.reqCode = reqCode;
    }

    public String getReqCode() {
        return reqCode;
    }

    public void setNumeroMandatChoisi(String numeroMandatChoisi) {
        this.numeroMandatChoisi = numeroMandatChoisi;
    }

    public String getNumeroMandatChoisi() {
        return numeroMandatChoisi;
    }

    public void setSignatureMandatChoisi(String signatureMandatChoisi) {
        this.signatureMandatChoisi = signatureMandatChoisi;
    }

    public String getSignatureMandatChoisi() {
        return signatureMandatChoisi;
    }

    public void setNbrMinMandatChoisi(String nbrMinMandatChoisi) {
        this.nbrMinMandatChoisi = nbrMinMandatChoisi;
    }

    public String getNbrMinMandatChoisi() {
        return nbrMinMandatChoisi;
    }

    public void setTypeMandatChoisi(String typeMandatChoisi) {
        this.typeMandatChoisi = typeMandatChoisi;
    }

    public String getTypeMandatChoisi() {
        return typeMandatChoisi;
    }

    public void setNbreSignataires(String nbreSignataires) {
        this.nbreSignataires = nbreSignataires;
    }

    public String getNbreSignataires() {
        return nbreSignataires;
    }

    public void setRefMand(String refMand) {
        this.refMand = refMand;
    }

    public String getRefMand() {
        return refMand;
    }

    public void setNumOperation(String numOperation) {
        this.numOperation = numOperation;
    }

    public String getNumOperation() {
        return numOperation;
    }

    public void setListdesMandatsPersonneChoisi(List listdesMandatsPersonneChoisi) {
        this.listdesMandatsPersonneChoisi = listdesMandatsPersonneChoisi;
    }

    public List getListdesMandatsPersonneChoisi() {
        return listdesMandatsPersonneChoisi;
    }

    public void setNumeroOperationMandatChoisi(String numeroOperationMandatChoisi) {
        this.numeroOperationMandatChoisi = numeroOperationMandatChoisi;
    }

    public String getNumeroOperationMandatChoisi() {
        return numeroOperationMandatChoisi;
    }

    public void setAlert(String alert) {
        this.alert = alert;
    }

    public String getAlert() {
        return alert;
    }

    public void setTestExistanceMandat(String testExistanceMandat) {
        this.testExistanceMandat = testExistanceMandat;
    }

    public String getTestExistanceMandat() {
        return testExistanceMandat;
    }

    public void setTypePersonne(String typePersonne) {
        this.typePersonne = typePersonne;
    }

    public String getTypePersonne() {
        return typePersonne;
    }

    public void setCategoriePersonne(String categoriePersonne) {
        this.categoriePersonne = categoriePersonne;
    }

    public String getCategoriePersonne() {
        return categoriePersonne;
    }

    public void setTypeDemandeur(String typeDemandeur) {
        this.typeDemandeur = typeDemandeur;
    }

    public String getTypeDemandeur() {
        return typeDemandeur;
    }

    public void setTestExistanceContrat(String testExistanceContrat) {
        this.testExistanceContrat = testExistanceContrat;
    }

    public String getTestExistanceContrat() {
        return testExistanceContrat;
    }

    public void setTestExistanceDemandeur(String testExistanceDemandeur) {
        this.testExistanceDemandeur = testExistanceDemandeur;
    }

    public String getTestExistanceDemandeur() {
        return testExistanceDemandeur;
    }

    public void setAlertDemandeur(String alertDemandeur) {
        this.alertDemandeur = alertDemandeur;
    }

    public String getAlertDemandeur() {
        return alertDemandeur;
    }

    public void setMessageTexte(String messageTexte) {
        this.messageTexte = messageTexte;
    }

    public String getMessageTexte() {
        return messageTexte;
    }

    public void setTabsheetActif(String tabsheetActif) {
        this.tabsheetActif = tabsheetActif;
    }

    public String getTabsheetActif() {
        return tabsheetActif;
    }

    public void setOpenTabSheetCotitulaire(String openTabSheetCotitulaire) {
        this.openTabSheetCotitulaire = openTabSheetCotitulaire;
    }

    public String getOpenTabSheetCotitulaire() {
        return openTabSheetCotitulaire;
    }

    public void setOpenTabSheetMandat(String openTabSheetMandat) {
        this.openTabSheetMandat = openTabSheetMandat;
    }

    public String getOpenTabSheetMandat() {
        return openTabSheetMandat;
    }

    public void setOpenTabSheetOperation(String openTabSheetOperation) {
        this.openTabSheetOperation = openTabSheetOperation;
    }

    public String getOpenTabSheetOperation() {
        return openTabSheetOperation;
    }

    public void setMessageAlerte(String messageAlerte) {
        this.messageAlerte = messageAlerte;
    }

    public String getMessageAlerte() {
        return messageAlerte;
    }

    public void setCodTcarTcar(String codTcarTcar) {
        this.codTcarTcar = codTcarTcar;
    }

    public String getCodTcarTcar() {
        return codTcarTcar;
    }

    public void setListeTypeCarte(List listeTypeCarte) {
        this.listeTypeCarte = listeTypeCarte;
    }

    public List getListeTypeCarte() {
        return listeTypeCarte;
    }

    public void setCodAutDcar(String codAutDcar) {
        this.codAutDcar = codAutDcar;
    }

    public String getCodAutDcar() {
        return codAutDcar;
    }

    public void setSalarie(String salarie) {
        this.salarie = salarie;
    }

    public String getSalarie() {
        return salarie;
    }

    public void setMontSalDcar(String montSalDcar) {
        this.montSalDcar = montSalDcar;
    }

    public String getMontSalDcar() {
        return montSalDcar;
    }

    public void setMontDretDcar(String montDretDcar) {
        this.montDretDcar = montDretDcar;
    }

    public String getMontDretDcar() {
        return montDretDcar;
    }

    public void setMontDachDcar(String montDachDcar) {
        this.montDachDcar = montDachDcar;
    }

    public String getMontDachDcar() {
        return montDachDcar;
    }

    public void setCodOperTcar(String codOperTcar) {
        this.codOperTcar = codOperTcar;
    }

    public String getCodOperTcar() {
        return codOperTcar;
    }

    public void setBoolPlafTcar(String boolPlafTcar) {
        this.boolPlafTcar = boolPlafTcar;
    }

    public String getBoolPlafTcar() {
        return boolPlafTcar;
    }

    public void setCodEtatDcar(String codEtatDcar) {
        this.codEtatDcar = codEtatDcar;
    }

    public String getCodEtatDcar() {
        return codEtatDcar;
    }

    public void setCodMotifRejet(String codMotifRejet) {
        this.codMotifRejet = codMotifRejet;
    }

    public String getCodMotifRejet() {
        return codMotifRejet;
    }

    public void setAvecAutorisation(String avecAutorisation) {
        this.avecAutorisation = avecAutorisation;
    }

    public String getAvecAutorisation() {
        return avecAutorisation;
    }

    public void setDemandeCarte(DemandeCarte demandeCarte) {
        this.demandeCarte = demandeCarte;
    }

    public DemandeCarte getDemandeCarte() {
        return demandeCarte;
    }

    public void setOpenTabSheetDemande(String openTabSheetDemande) {
        this.openTabSheetDemande = openTabSheetDemande;
    }

    public String getOpenTabSheetDemande() {
        return openTabSheetDemande;
    }

    public void setMontPlafRet(String montPlafRet) {
        this.montPlafRet = montPlafRet;
    }

    public String getMontPlafRet() {
        return montPlafRet;
    }

    public void setMontPlafAch(String montPlafAch) {
        this.montPlafAch = montPlafAch;
    }

    public String getMontPlafAch() {
        return montPlafAch;
    }

    public void setNatureGarantie(String natureGarantie) {
        this.natureGarantie = natureGarantie;
    }

    public String getNatureGarantie() {
        return natureGarantie;
    }

    public void setConstitue(String constitue) {
        this.constitue = constitue;
    }

    public String getConstitue() {
        return constitue;
    }

    public void setDateDernOperation(String dateDernOperation) {
        this.dateDernOperation = dateDernOperation;
    }

    public String getDateDernOperation() {
        return dateDernOperation;
    }
    
    public void setParamRechercheDemandeCarte(ParamRechercheDemandeCarte paramRechercheDemandeCarte) {
        this.paramRechercheDemandeCarte = paramRechercheDemandeCarte;
    }

    public ParamRechercheDemandeCarte getParamRechercheDemandeCarte() {
        return paramRechercheDemandeCarte;
    }

    public void setLibEtat(String libEtat) {
        this.libEtat = libEtat;
    }

    public String getLibEtat() {
        return libEtat;
    }

    public void setListeCartesBancaires(Collection listeCartesBancaires) {
        this.listeCartesBancaires = listeCartesBancaires;
    }

    public Collection getListeCartesBancaires() {
        return listeCartesBancaires;
    }

    public void setLibListeChoix(String libListeChoix) {
        this.libListeChoix = libListeChoix;
    }

    public String getLibListeChoix() {
        return libListeChoix;
    }

    public void setLibChoisie(String libChoisie) {
        this.libChoisie = libChoisie;
    }

    public String getLibChoisie() {
        return libChoisie;
    }

    
    public void setTypeTraitement(String typeTraitement) {
        this.typeTraitement = typeTraitement;
    }

    public String getTypeTraitement() {
        return typeTraitement;
    }

    public void setNumChoisie(String numChoisie) {
        this.numChoisie = numChoisie;
    }

    public String getNumChoisie() {
        return numChoisie;
    }

    public void setCarteBancaire(CarteBancaire carteBancaire) {
        this.carteBancaire = carteBancaire;
    }

    public CarteBancaire getCarteBancaire() {
        return carteBancaire;
    }

    public void setLibChoixNumero(String libChoixNumero) {
        this.libChoixNumero = libChoixNumero;
    }

    public String getLibChoixNumero() {
        return libChoixNumero;
    }

    public void setLibFieldsetChoix(String libFieldsetChoix) {
        this.libFieldsetChoix = libFieldsetChoix;
    }

    public String getLibFieldsetChoix() {
        return libFieldsetChoix;
    }

    public void setNumCarte(String numCarte) {
        this.numCarte = numCarte;
    }

    public String getNumCarte() {
        return numCarte;
    }

    public void setDateFinCarte(String dateFinCarte) {
        this.dateFinCarte = dateFinCarte;
    }

    public String getDateFinCarte() {
        return dateFinCarte;
    }

    public void setNomAnnlCarb(String nomAnnlCarb) {
        this.nomAnnlCarb = nomAnnlCarb;
    }

    public String getNomAnnlCarb() {
        return nomAnnlCarb;
    }

    public void setLibRempCarb(String libRempCarb) {
        this.libRempCarb = libRempCarb;
    }

    public String getLibRempCarb() {
        return libRempCarb;
    }

    public void setNumCarteRemplace(String numCarteRemplace) {
        this.numCarteRemplace = numCarteRemplace;
    }

    public String getNumCarteRemplace() {
        return numCarteRemplace;
    }

    public void setMessageAlertRecherche(String messageAlertRecherche) {
        this.messageAlertRecherche = messageAlertRecherche;
    }

    public String getMessageAlertRecherche() {
        return messageAlertRecherche;
    }


    public void setChoixEtatDemande(String choixEtatDemande) {
        this.choixEtatDemande = choixEtatDemande;
    }

    public String getChoixEtatDemande() {
        return choixEtatDemande;
    }

    public void setChoixEtatCarte(String choixEtatCarte) {
        this.choixEtatCarte = choixEtatCarte;
    }

    public String getChoixEtatCarte() {
        return choixEtatCarte;
    }

    public void setCodeTache(String codeTache) {
        this.codeTache = codeTache;
    }

    public String getCodeTache() {
        return codeTache;
    }

    public void setMalConfectRecept(String malConfectRecept) {
        this.malConfectRecept = malConfectRecept;
    }

    public String getMalConfectRecept() {
        return malConfectRecept;
    }

    public void setDomicilie(String domicilie) {
        this.domicilie = domicilie;
    }

    public String getDomicilie() {
        return domicilie;
    }

    public void setPlafondRetrait(String plafondRetrait) {
        this.plafondRetrait = plafondRetrait;
    }

    public String getPlafondRetrait() {
        return plafondRetrait;
    }

    public void setPlafondAchat(String plafondAchat) {
        this.plafondAchat = plafondAchat;
    }

    public String getPlafondAchat() {
        return plafondAchat;
    }

    public void setBoolModpDcar(String boolModpDcar) {
        this.boolModpDcar = boolModpDcar;
    }

    public String getBoolModpDcar() {
        return boolModpDcar;
    }

    public void setNomModfDcar(String nomModfDcar) {
        this.nomModfDcar = nomModfDcar;
    }

    public String getNomModfDcar() {
        return nomModfDcar;
    }

    public void setDureeCarte(String dureeCarte) {
        this.dureeCarte = dureeCarte;
    }

    public String getDureeCarte() {
        return dureeCarte;
    }

    public void setDateFinCarteRemp(String dateFinCarteRemp) {
        this.dateFinCarteRemp = dateFinCarteRemp;
    }

    public String getDateFinCarteRemp() {
        return dateFinCarteRemp;
    }
}
