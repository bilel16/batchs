package com.bna.smile.web.moyenPaiement.demandeChequier.forms;

import com.bna.commun.model.CoTitulaire;
import com.bna.commun.model.ContratCpt;

import com.bna.commun.model.DemandeCheque;
import com.bna.commun.model.Personne;

import fr.improve.struts.taglib.layout.datagrid.Datagrid;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

public class RechercheDemandesChequesForm extends ActionForm {

    // Titre 
    private String libelleOperation;
    // Informations du contrat
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


    private String numSeqCliCotitulaire;
    private String typeSignatureCotitulaire;
    private String typeCotitulaire;
    private CoTitulaire cotitulaire;
    // 

    private List listCotitulaires = new ArrayList();
    private List listMandats = 
        new ArrayList(); // list les mandats valides pour ce contrat
    private List listMandatsConcernesPersonne = 
        new ArrayList(); // liste des mandats valide qui concerne la personne pour cette operation dans ce contrat
    private List listMandatsOperationConcernesPersonne = new ArrayList();
    private List listMandatsPersonneConcernesDemandeur = new ArrayList();

    private String reqCode;

    // information du mandat selectionné 
    private String numeroMandatChoisi;
    private String refMand = new String("");
    private List listdesMandatsPersonneChoisi = new ArrayList();
    private String numeroOperationMandatChoisi;

    //-- Données de Test
    private String alert;
    private String testExistanceMandat; //"O" si le mandat existe ;"N" sinon
    private String typePersonne; // personnePhysique;personneMorale;entiteCotitulaire
    private String categoriePersonne; // categorie personne
    private String typeDemandeur; // T: en cas de Titulaire; M: mandataire, C; entite co-titulaire; MC: mandat et cotitulaire
    private String codeOperation; //code de l'operation Guicher
    private String codeTache;
    private String testExistanceContrat; // (O : si le contrat existe /N :si le contrat n'existe pas )
    private String testExistanceDemandeur;
    private String alertDemandeur;
    private String alertRecherche;

    // données concernant les infos de la demande cheque
    private String numDemDchq;
    private String dateDemDchq;
    private String etatDemDchq;
    private String datEnvoiDsc;
    private String codConfConf;
    private String typeConfection;
    private String nbreChequesDchq;
    private String dateEnvoiDr;
    private String codFraisDchq;
    private String typeFactDchq;
    private String montantFraisDchq;
    private String motifRejet;
    private String nbreChequiers = "1";
    private String dateActuelle;
    private String codMotifRejet;
    private String strcDecision;


    //-------------- données de test d'affichage dans la page JSP 
    private String activerDemandeur; // "O" si on peut saisir le demandeur
    private String messageTexte;
    private String messageRestitution;

    //--------------------
    private String tabsheetActif;
    private String choixTypeConfection;
    private String choixTypeChq;
    private String messageAlerte = "";
    private String numMatrUser;
    private String codePage;
    private String libPage;

    // données concernant la page de recherche des demandes lors de la consultation
    private String codeAgance;
    private String codTypePieceId;
    private String openTabsheetCotitulaire = "false";
    private String openTabSheetMandat = "false";
    private String openTabSheetOperation = "false";
    private String openTabSheetChequiers = "false";
    private String choix;
    private String choixListe;
    private String choixRecherche;
    private String typePieceId;
    private String numPieceId;
    private String codStrcRech;
    private String codPrdRech;
    private String numCcptRech;
    private String numDemande;
    //**************************************13-03-08
    private String dateDebRecherch;
    private String dateFinRecherch;
    
    //****************************************************
    private Collection listeDemandesCheques;
    private String numDemandeChoisi;
    private String numDemandeChoisie;
    private String openTabsheetDemande = "false";
    private DemandeCheque demandeCheque;
    private Datagrid listChequiersGrid;
    private String restitution = "false";
    private String choixEtatDemande;
    private Collection listeHistoriqueCheques;
    private Collection listeDemandesChequesAnterieures;
    private Collection listChequiers;


    /**Reset all properties to their default values.
     * @param mapping The ActionMapping used to select this instance.
     * @param request The HTTP Request we are processing.
     */
    public void reset(ActionMapping mapping, HttpServletRequest request) {
        super.reset(mapping, request);
    }

    public void clearTabDemande() {
        // Informations du contrat
        codStrcStrc = "";
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
        refMand = "";
        listdesMandatsPersonneChoisi = new ArrayList();
        numeroOperationMandatChoisi = "";

        listCotitulaires = new ArrayList();
        listMandats = new ArrayList();
        listMandatsConcernesPersonne = new ArrayList();
        listMandatsOperationConcernesPersonne = new ArrayList();
        listMandatsPersonneConcernesDemandeur = new ArrayList();

        alert = "";
        typeDemandeur = "";

        /// données concernant les infos de la demande cheque
        numDemDchq = "";
        etatDemDchq = "";
        dateDemDchq = "";
        codConfConf = "";
        typeConfection = "";
        nbreChequesDchq = "";
        dateEnvoiDr = "";
        codFraisDchq = "";
        typeFactDchq = "";
        montantFraisDchq = "";
        motifRejet = "";
        nbreChequiers = "1";
        codMotifRejet = "";
        strcDecision="0";
        

        openTabsheetDemande = "false";
        openTabsheetCotitulaire = "false";
        openTabSheetMandat = "false";
        openTabSheetOperation = "false";
        openTabSheetChequiers = "false";

        //---- données de test
        testExistanceContrat = ""; //"O" si le contrat existe ;"N" sinon
        typePersonne = ""; // personnePhysique;personneMorale;entiteCotitulaire
        categoriePersonne = "";
        testExistanceMandat = ""; // "O" s'il existe des mandat pour ce contrat
        testExistanceDemandeur = ""; // "O" si le demandeur existe.
        //-------------- données de test d'affichage dans la page JSP 
        activerDemandeur = "O";
        messageTexte = "";
        messageRestitution = "";
        alertDemandeur = "";

        tabsheetActif = "1";
        messageAlerte = "";
    }
    // données concernant la page de recherche des demandes lors de la consultation


    public void clearTabRecherche() {
        codTypePieceId = "";
        openTabsheetCotitulaire = "";
        openTabSheetMandat = "false";
        openTabSheetOperation = "false";
        choix = "";
        choixListe="";
        choixRecherche = "";
        typePieceId = "";
        numPieceId = "";
        codPrdRech = "";
        numCcptRech = "";
        numDemande = "";
        //dateDebRecherch="";
        //dateFinRecherch="";
        listeDemandesCheques = new ArrayList();
        listeHistoriqueCheques = new ArrayList();
        listChequiers = new ArrayList(); 
        listeDemandesChequesAnterieures = new ArrayList();
        numDemandeChoisi = "";
        numDemandeChoisie = "";
        restitution = "false";

    }

    /**Validate all properties to their default values.
     * @param mapping The ActionMapping used to select this instance.
     * @param request The HTTP Request we are processing.
     * @return ActionErrors A list of all errors found.
     */
    public ActionErrors validate(ActionMapping mapping, 
                                 HttpServletRequest request) {
        return super.validate(mapping, request);
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

    public void setNumSeqCliCotitulaire(String numSeqCliCotitulaire) {
        this.numSeqCliCotitulaire = numSeqCliCotitulaire;
    }

    public String getNumSeqCliCotitulaire() {
        return numSeqCliCotitulaire;
    }


    public void setCleCompte(String cleCompte) {
        this.cleCompte = cleCompte;
    }

    public String getCleCompte() {
        return cleCompte;
    }

    public void setMontSoldCcpt(String montSoldCcpt) {
        this.montSoldCcpt = montSoldCcpt;
    }

    public String getMontSoldCcpt() {
        return montSoldCcpt;
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

    public void setListCotitulaires(List listCotitulaires) {
        this.listCotitulaires = listCotitulaires;
    }

    public List getListCotitulaires() {
        return listCotitulaires;
    }

    public void set_typeSignatureCotitulaire(String _typeSignatureCotitulaire) {
        this.typeSignatureCotitulaire = _typeSignatureCotitulaire;
    }

    public String get_typeSignatureCotitulaire() {
        return typeSignatureCotitulaire;
    }

    public void set_typeCotitulaire(String _typeCotitulaire) {
        this.typeCotitulaire = _typeCotitulaire;
    }

    public String get_typeCotitulaire() {
        return typeCotitulaire;
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

    public void setListMandats(List listMandats) {
        this.listMandats = listMandats;
    }

    public List getListMandats() {
        return listMandats;
    }


    public void setContratCpt(ContratCpt contratCpt) {
        this.contratCpt = contratCpt;
    }

    public ContratCpt getContratCpt() {
        return contratCpt;
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

    public void setReqCode(String reqCode) {
        this.reqCode = reqCode;
    }

    public String getReqCode() {
        return reqCode;
    }


    public String getAlert() {
        return alert;
    }

    public void setAlert(String alert) {
        this.alert = alert;
    }

    public void setTypeDemandeur(String typeDemandeur) {
        this.typeDemandeur = typeDemandeur;
    }

    public String getTypeDemandeur() {
        return typeDemandeur;
    }

    public void setCodeOperation(String codeOperation) {
        this.codeOperation = codeOperation;
    }

    public String getCodeOperation() {
        return codeOperation;
    }

    public void setListMandatsConcernesPersonne(List listMandatsConcernesPersonne) {
        this.listMandatsConcernesPersonne = listMandatsConcernesPersonne;
    }

    public List getListMandatsConcernesPersonne() {
        return listMandatsConcernesPersonne;
    }

    public void setListMandatsOperationConcernesPersonne(List listMandatsOperationConcernesPersonne) {
        this.listMandatsOperationConcernesPersonne = 
                listMandatsOperationConcernesPersonne;
    }

    public List getListMandatsOperationConcernesPersonne() {
        return listMandatsOperationConcernesPersonne;
    }

    public void setNumeroMandatChoisi(String numeroMandatChoisi) {
        this.numeroMandatChoisi = numeroMandatChoisi;
    }

    public String getNumeroMandatChoisi() {
        return numeroMandatChoisi;
    }

    public void setListMandatsPersonneConcernesDemandeur(List listMandatsPersonneConcernesDemandeur) {
        this.listMandatsPersonneConcernesDemandeur = 
                listMandatsPersonneConcernesDemandeur;
    }

    public List getListMandatsPersonneConcernesDemandeur() {
        return listMandatsPersonneConcernesDemandeur;
    }

    public void setTestExistanceContrat(String testExistanceContrat) {
        this.testExistanceContrat = testExistanceContrat;
    }

    public String getTestExistanceContrat() {
        return testExistanceContrat;
    }

    public void setTestExistanceMandat(String testExistanceMandat) {
        this.testExistanceMandat = testExistanceMandat;
    }

    public String getTestExistanceMandat() {
        return testExistanceMandat;
    }


    public void setActiverDemandeur(String activerDemandeur) {
        this.activerDemandeur = activerDemandeur;
    }

    public String getActiverDemandeur() {
        return activerDemandeur;
    }

    public void setTestExistanceDemandeur(String testExistanceDemandeur) {
        this.testExistanceDemandeur = testExistanceDemandeur;
    }

    public String getTestExistanceDemandeur() {
        return testExistanceDemandeur;
    }

    public void setMessageTexte(String messageTexte) {
        this.messageTexte = messageTexte;
    }

    public String getMessageTexte() {
        return messageTexte;
    }

    public void setLibelleOperation(String libelleOperation) {
        this.libelleOperation = libelleOperation;
    }

    public String getLibelleOperation() {
        return libelleOperation;
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

    public void setTabsheetActif(String tabsheetActif) {
        this.tabsheetActif = tabsheetActif;
    }

    public String getTabsheetActif() {
        return tabsheetActif;
    }

    public void setRefMand(String refMand) {
        this.refMand = refMand;
    }

    public String getRefMand() {
        return refMand;
    }


    public void setChoixTypeConfection(String choixTypeConfection) {
        this.choixTypeConfection = choixTypeConfection;
    }

    public String getChoixTypeConfection() {
        return choixTypeConfection;
    }

    public void setNumDemDchq(String numDemDchq) {
        this.numDemDchq = numDemDchq;
    }

    public String getNumDemDchq() {
        return numDemDchq;
    }

    public void setDateDemDchq(String dateDemDchq) {
        this.dateDemDchq = dateDemDchq;
    }

    public String getDateDemDchq() {
        return dateDemDchq;
    }

    public void setEtatDemDchq(String etatDemDchq) {
        this.etatDemDchq = etatDemDchq;
    }

    public String getEtatDemDchq() {
        return etatDemDchq;
    }

    public void setDatEnvoiDsc(String datEnvoiDsc) {
        this.datEnvoiDsc = datEnvoiDsc;
    }

    public String getDatEnvoiDsc() {
        return datEnvoiDsc;
    }

    public void setCodConfConf(String codConfConf) {
        this.codConfConf = codConfConf;
    }

    public String getCodConfConf() {
        return codConfConf;
    }

    public void setTypeConfection(String typeConfection) {
        this.typeConfection = typeConfection;
    }

    public String getTypeConfection() {
        return typeConfection;
    }


    public void setDateEnvoiDr(String dateEnvoiDr) {
        this.dateEnvoiDr = dateEnvoiDr;
    }

    public String getDateEnvoiDr() {
        return dateEnvoiDr;
    }

    public void setCodFraisDchq(String codFraisDchq) {
        this.codFraisDchq = codFraisDchq;
    }

    public String getCodFraisDchq() {
        return codFraisDchq;
    }

    public void setTypeFactDchq(String typeFactDchq) {
        this.typeFactDchq = typeFactDchq;
    }

    public String getTypeFactDchq() {
        return typeFactDchq;
    }

    public void setMontantFraisDchq(String montantFraisDchq) {
        this.montantFraisDchq = montantFraisDchq;
    }

    public String getMontantFraisDchq() {
        return montantFraisDchq;
    }

    public void setMotifRejet(String motifRejet) {
        this.motifRejet = motifRejet;
    }

    public String getMotifRejet() {
        return motifRejet;
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

    public void setAlertDemandeur(String alertDemandeur) {
        this.alertDemandeur = alertDemandeur;
    }

    public String getAlertDemandeur() {
        return alertDemandeur;
    }

    public void setEtatContrat(String etatContrat) {
        this.etatContrat = etatContrat;
    }

    public String getEtatContrat() {
        return etatContrat;
    }

    public void setChoixTypeChq(String choixTypeChq) {
        this.choixTypeChq = choixTypeChq;
    }

    public String getChoixTypeChq() {
        return choixTypeChq;
    }

    public void setNbreChequiers(String nbreChequiers) {
        this.nbreChequiers = nbreChequiers;
    }

    public String getNbreChequiers() {
        return nbreChequiers;
    }

    public void setDateActuelle(String dateActuelle) {
        this.dateActuelle = dateActuelle;
    }

    public String getDateActuelle() {
        return dateActuelle;
    }


    public void setMessageAlerte(String messageAlerte) {
        this.messageAlerte = messageAlerte;
    }

    public String getMessageAlerte() {
        return messageAlerte;
    }

    public void setNumMatrUser(String numMatrUser) {
        this.numMatrUser = numMatrUser;
    }

    public String getNumMatrUser() {
        return numMatrUser;
    }

    public void setNbreChequesDchq(String nbreChequesDchq) {
        this.nbreChequesDchq = nbreChequesDchq;
    }

    public String getNbreChequesDchq() {
        return nbreChequesDchq;
    }

    public void setCotitulaire(CoTitulaire cotitulaire) {
        this.cotitulaire = cotitulaire;
    }

    public CoTitulaire getCotitulaire() {
        return cotitulaire;
    }


    public void setDemandeur(Personne demandeur) {
        this.demandeur = demandeur;
    }

    public Personne getDemandeur() {
        return demandeur;
    }

    public void setCodMotifRejet(String codMotifRejet) {
        this.codMotifRejet = codMotifRejet;
    }

    public String getCodMotifRejet() {
        return codMotifRejet;
    }

    public void setCodePage(String codePage) {
        this.codePage = codePage;
    }

    public String getCodePage() {
        return codePage;
    }

    public void setLibPage(String libPage) {
        this.libPage = libPage;
    }

    public String getLibPage() {
        return libPage;
    }

    public void setCodTypePieceId(String codTypePieceId) {
        this.codTypePieceId = codTypePieceId;
    }

    public String getCodTypePieceId() {
        return codTypePieceId;
    }

    public void setOpenTabsheetCotitulaire(String openTabsheetCotitulaire) {
        this.openTabsheetCotitulaire = openTabsheetCotitulaire;
    }

    public String getOpenTabsheetCotitulaire() {
        return openTabsheetCotitulaire;
    }


    public void setChoix(String choix) {
        this.choix = choix;
    }

    public String getChoix() {
        return choix;
    }

    public void setChoixRecherche(String choixRecherche) {
        this.choixRecherche = choixRecherche;
    }

    public String getChoixRecherche() {
        return choixRecherche;
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

    public void setNumDemande(String numDemande) {
        this.numDemande = numDemande;
    }

    public String getNumDemande() {
        return numDemande;
    }

    public void setListeDemandesCheques(Collection listeDemandesCheques) {
        this.listeDemandesCheques = listeDemandesCheques;
    }

    public Collection getListeDemandesCheques() {
        return listeDemandesCheques;
    }

    public void setNumDemandeChoisi(String numDemandeChoisi) {
        this.numDemandeChoisi = numDemandeChoisi;
    }

    public String getNumDemandeChoisi() {
        return numDemandeChoisi;
    }

    public void setCodeAgance(String codeAgance) {
        this.codeAgance = codeAgance;
    }

    public String getCodeAgance() {
        return codeAgance;
    }

    public void setNumDemandeChoisie(String numDemandeChoisie) {
        this.numDemandeChoisie = numDemandeChoisie;
    }

    public String getNumDemandeChoisie() {
        return numDemandeChoisie;
    }

    public void setAlertRecherche(String alertRecherche) {
        this.alertRecherche = alertRecherche;
    }

    public String getAlertRecherche() {
        return alertRecherche;
    }

    public void setOpenTabsheetDemande(String openTabsheetDemande) {
        this.openTabsheetDemande = openTabsheetDemande;
    }

    public String getOpenTabsheetDemande() {
        return openTabsheetDemande;
    }

    public void setDemandeCheque(DemandeCheque demandeCheque) {
        this.demandeCheque = demandeCheque;
    }

    public DemandeCheque getDemandeCheque() {
        return demandeCheque;
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

    public void setListChequiersGrid(Datagrid listChequiersGrid) {
        this.listChequiersGrid = listChequiersGrid;
    }

    public Datagrid getListChequiersGrid() {
        return listChequiersGrid;
    }

    public void setOpenTabSheetChequiers(String openTabSheetChequiers) {
        this.openTabSheetChequiers = openTabSheetChequiers;
    }

    public String getOpenTabSheetChequiers() {
        return openTabSheetChequiers;
    }

    public void setRestitution(String restitution) {
        this.restitution = restitution;
    }

    public String getRestitution() {
        return restitution;
    }

    public void setMessageRestitution(String messageRestitution) {
        this.messageRestitution = messageRestitution;
    }

    public String getMessageRestitution() {
        return messageRestitution;
    }

    public void setChoixEtatDemande(String choixEtatDemande) {
        this.choixEtatDemande = choixEtatDemande;
    }

    public String getChoixEtatDemande() {
        return choixEtatDemande;
    }

    public void setListeHistoriqueCheques(Collection listeHistoriqueCheques) {
        this.listeHistoriqueCheques = listeHistoriqueCheques;
    }

    public Collection getListeHistoriqueCheques() {
        return listeHistoriqueCheques;
    }

    public void setListeDemandesChequesAnterieures(Collection listeDemandesChequesAnterieures) {
        this.listeDemandesChequesAnterieures = listeDemandesChequesAnterieures;
    }

    public Collection getListeDemandesChequesAnterieures() {
        return listeDemandesChequesAnterieures;
    }

    public void setCodeTache(String codeTache) {
        this.codeTache = codeTache;
    }

    public String getCodeTache() {
        return codeTache;
    }


    public void setStrcDecision(String strcDecision) {
        this.strcDecision = strcDecision;
    }

    public String getStrcDecision() {
        return strcDecision;
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

    public void setChoixListe(String choixListe) {
        this.choixListe = choixListe;
    }

    public String getChoixListe() {
        return choixListe;
    }

    public void setListChequiers(Collection listChequiers) {
        this.listChequiers = listChequiers;
    }

    public Collection getListChequiers() {
        return listChequiers;
    }

}
