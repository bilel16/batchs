package com.bna.smile.web.operationguichet.form;

import com.bna.commun.model.ContratCpt;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

public class ConsultationContratOperationForm extends ActionForm {

    // Titre 
    private String libelleOperation;
    // Informations du contrat
    private String codStrcStrc;
    private String codPrdPrd;
    private String numCcptCcpt;
    private String cleCompte;
    private ContratCpt contratCpt;

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

    private String numSeqCliCotitulaire;
    private String typeSignatureCotitulaire;
    private String typeCotitulaire;

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
    private String refMand= new String("");
    private List listdesMandatsPersonneChoisi = new ArrayList();
    private String numeroOperationMandatChoisi;

    //-- Données de Test
    private String alert;
    private String testExistanceMandat; //"O" si le contrat existe ;"N" sinon
    private String testTypePersonne; // personnePhysique;personneMorale;entiteCotitulaire
    private String typeDemandeur; // T: en cas de Titulaire; M: mandataire, C; entite co-titulaire; MC: mandat et cotitulaire
    private String codeOperation; //code de l'operation Guicher
    private String testExistanceContrat; // (O : si le contrat existe /N :si le contrat n'existe pas )
    private String testExistanceDemandeur;

    //-------------- données de test d'affichage dans la page JSP 
    private String activerDemandeur; // "O" si on peut saisir le demandeur
    private String messageTexte;

    //--------------------
    private String tabsheetActif;

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
        refMand = "";
        listdesMandatsPersonneChoisi = new ArrayList();
        numeroOperationMandatChoisi = "";

        listCotitulaires = new ArrayList();
        listMandats = new ArrayList();
        listMandatsConcernesPersonne = new ArrayList();
        listMandatsOperationConcernesPersonne = new ArrayList();
        listMandatsPersonneConcernesDemandeur = new ArrayList();
        
        reqCode = "";
        alert = "";
        typeDemandeur = "";
        codeOperation = "";

        //---- données de test
        testExistanceContrat = ""; //"O" si le contrat existe ;"N" sinon
        testTypePersonne = 
                ""; // personnePhysique;personneMorale;entiteCotitulaire
        testExistanceMandat = ""; // "O" s'il existe des mandat pour ce contrat
        testExistanceDemandeur = ""; // "O" si le demandeur existe.
        //-------------- données de test d'affichage dans la page JSP 
        activerDemandeur = "O";
        messageTexte = "";
        
        tabsheetActif ="1";
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

    public void setTestTypePersonne(String testTypePersonne) {
        this.testTypePersonne = testTypePersonne;
    }

    public String getTestTypePersonne() {
        return testTypePersonne;
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
}
