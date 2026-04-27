package com.bna.smile.web.moyenPaiement.demandeChequier.forms;

import com.bna.commun.model.CoTitulaire;
import com.bna.commun.model.CompteInterne;
import com.bna.commun.model.ContratCpt;

import com.bna.commun.model.DemandeCheque;
import com.bna.commun.model.Personne;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

public class CreationDemandeChequeForm extends ActionForm {

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
    private CompteInterne compteInterne;
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
    private List listMandats = 
        new ArrayList(); // list les mandats valides pour ce contrat
    private List listMandatsConcernesPersonne = 
        new ArrayList(); // liste des mandats valide qui concerne la personne pour cette operation dans ce contrat    
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
    private String codeOperationChoisie;
    private String numOperation;
    private List listdesMandatsPersonneChoisi = new ArrayList();
    private String numeroOperationMandatChoisi;

    //-- Données de Test
    private String alert;
    private String testExistanceMandat; //"O" si le contrat existe ;"N" sinon
    private String typePersonne; // personnePhysique;personneMorale;entiteCotitulaire
    private String categoriePersonne; // categorie personne
    private String typeDemandeur; // T: en cas de Titulaire; M: mandataire, C; entite co-titulaire; MC: mandat et cotitulaire
    private String codeOperation; //code de l'operation Guicher
    
    private String codeTache;
    private String testExistanceContrat; // (O : si le contrat existe /N :si le contrat n'existe pas )
    private String testExistanceDemandeur;
    private String alertDemandeur;

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
    private String forcage= "0";
    private String typeLCheque;

    //-------------- données de test d'affichage dans la page JSP 
    private String activerDemandeur; // "O" si on peut saisir le demandeur
    private String messageTexte;

    //--------------------
    private String tabsheetActif;
    private String choixTypeConfection;
    private String choixTypeChq;
    private String openTabSheetCotitulaire = "false";
    private String openTabSheetMandat = "false";
    private String openTabSheetOperation = "false";
    private String messageAlerte = "";
    private String numMatrUser;
    private String codePage;
    private String libPage;
    
    // données concernant la page commande chequier certifié
    private String codTpceTpce;
    private String numPcePers;
    private String nomNomPers;
    private String nomPrnPers;
    private String alertChqcertif;
    private String numDebChqCertif;
    private DemandeCheque demandeCheque;
    private String dateComptableDchq;
   

    /**Reset all properties to their default values.
     * @param mapping The ActionMapping used to select this instance.
     * @param request The HTTP Request we are processing.
     */
    public void reset(ActionMapping mapping, HttpServletRequest request) {
        super.reset(mapping, request);
    }

    public void clearForm() {
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
        signatureMandatChoisi = "";
        nbrMinMandatChoisi = "";
        typeMandatChoisi = "";
        nbreSignataires = "";

        refMand = "";
        numOperation = "";
        codeOperationChoisie = "";
        listdesMandatsPersonneChoisi = new ArrayList();
        numeroOperationMandatChoisi = "";

        listCotitulaires = new ArrayList();
        listMandats = new ArrayList();
        listMandatsConcernesPersonne = new ArrayList();
        listMandatsPersonneConcernesDemandeur = new ArrayList();
        listMandataireCoches = new ArrayList();

        alert = "";
        typeDemandeur = "";


        /// données concernant les infos de la demande cheque
        numDemDchq = "";
        etatDemDchq = "";
        datEnvoiDsc = "";
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
        strcDecision = "0";
        forcage = "0";

        //---- données de test
        testExistanceContrat = ""; //"O" si le contrat existe ;"N" sinon
        typePersonne = ""; // personnePhysique;personneMorale;entiteCotitulaire
        categoriePersonne = "";
        testExistanceMandat = ""; // "O" s'il existe des mandat pour ce contrat
        testExistanceDemandeur = ""; // "O" si le demandeur existe.
        //-------------- données de test d'affichage dans la page JSP 
        activerDemandeur = "O";
        messageTexte = "";
        alertDemandeur = "";

        tabsheetActif = "1";
        openTabSheetCotitulaire = "false";
        openTabSheetMandat = "false";
        openTabSheetOperation = "false";
        messageAlerte = "";
    }
    
    
     public void clearFormChqCertif() { 
       
         codStrcStrc = "";
         codPrdPrd = "";
         numCcptCcpt = ""; 
         montSoldCcpt = "";
         nomIntiCcpt = "";
         devise = "";              
         codTpceTpce = "";
         numPcePers = "";
         nomNomPers = "";
         nomPrnPers = "";
         numDemDchq = "";
         alertChqcertif ="";
         numDebChqCertif= "";
         dateDemDchq = "";
         testExistanceContrat="";
         alertDemandeur ="";
         messageAlerte="";
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

    public void setOpenTabSheetCotitulaire(String openTabSheetCotitulaire) {
        this.openTabSheetCotitulaire = openTabSheetCotitulaire;
    }

    public String getOpenTabSheetCotitulaire() {
        return openTabSheetCotitulaire;
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

    public void setNumOperation(String numOperation) {
        this.numOperation = numOperation;
    }

    public String getNumOperation() {
        return numOperation;
    }

    public void setIdDemandeur(String idDemandeur) {
        this.idDemandeur = idDemandeur;
    }

    public String getIdDemandeur() {
        return idDemandeur;
    }

    public void setCodeOperationChoisie(String codeOperationChoisie) {
        this.codeOperationChoisie = codeOperationChoisie;
    }

    public String getCodeOperationChoisie() {
        return codeOperationChoisie;
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

    public void setListMandataireCoches(List listMandataireCoches) {
        this.listMandataireCoches = listMandataireCoches;
    }

    public List getListMandataireCoches() {
        return listMandataireCoches;
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

    public void setForcage(String forcage) {
        this.forcage = forcage;
    }

    public String getForcage() {
        return forcage;
    }

    public void setTypeLCheque(String typeLCheque) {
        this.typeLCheque = typeLCheque;
    }

    public String getTypeLCheque() {
        return typeLCheque;
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

    public void setNomNomPers(String nomNomPers) {
        this.nomNomPers = nomNomPers;
    }

    public String getNomNomPers() {
        return nomNomPers;
    }

    public void setNomPrnPers(String nomPrnPers) {
        this.nomPrnPers = nomPrnPers;
    }

    public String getNomPrnPers() {
        return nomPrnPers;
    }

    public void setAlertChqcertif(String alertChqcertif) {
        this.alertChqcertif = alertChqcertif;
    }

    public String getAlertChqcertif() {
        return alertChqcertif;
    }

    public void setCompteInterne(CompteInterne compteInterne) {
        this.compteInterne = compteInterne;
    }

    public CompteInterne getCompteInterne() {
        return compteInterne;
    }

    public void setNumDebChqCertif(String numDebChqCertif) {
        this.numDebChqCertif = numDebChqCertif;
    }

    public String getNumDebChqCertif() {
        return numDebChqCertif;
    }


    public void setDemandeCheque(DemandeCheque demandeCheque) {
        this.demandeCheque = demandeCheque;
    }

    public DemandeCheque getDemandeCheque() {
        return demandeCheque;
    }

    public void setDateComptableDchq(String dateComptableDchq) {
        this.dateComptableDchq = dateComptableDchq;
    }

    public String getDateComptableDchq() {
        return dateComptableDchq;
    }
}
