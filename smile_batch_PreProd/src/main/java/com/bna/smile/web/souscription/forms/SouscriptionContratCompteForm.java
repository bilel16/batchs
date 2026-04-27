package com.bna.smile.web.souscription.forms;

import com.bna.commun.model.Client;
import com.bna.commun.model.ContratCpt;
import com.bna.smile.model.domainecontratcompte.souscriptioncontratcompte.model.ParamInsertContrat;

import com.bna.smile.web.commun.view.InitialisationView;

import fr.improve.struts.taglib.layout.datagrid.Datagrid;

import java.sql.Blob;

import java.util.Collection;

import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

//import org.hibernate.type.BlobType;

public class SouscriptionContratCompteForm extends ActionForm {
    /**Reset all properties to their default values.
     * @param mapping The ActionMapping used to select this instance.
     * @param request The HTTP Request we are processing.
     */
    private

    //tabshett 1 : identification /////////////////
    String typePersonneId;
    private String categoriePersonneId;
    private String typePieceId;
    private String codTypePieceId;
    private String numPieceId;
    private Collection listeTypePersonne;
    private Collection listeCategoriePersonne;
    private String numSeqPers;
    private String codStrcStrc;
    private Client cltTrouve;    

    //--------------------------------------------------------

    private String nomId;
    private String prenomId;
    private String titreId;

    //tabshett 2 : Produit autorisés /////////////////
    private String dateNaissancePrd;
    private String nationalitePrd;
    private String codNationalitePrd;

    private String residentPrd;
    private String formeJuridiquePrd;
    private Collection listeFormeJuridique;
    private Collection listeContrats;
    private Collection listeProduits;
    private String produitChoisi;
    private String age="";

    //------------------------------------------------------------
    //tabshett 3 : Client          /////////////////
    private String typePersonneClt;
    private String categoriePersonneClt;
    private Collection listTypePersonneClt;
    private Collection listCategoriePersonneClt;

    private String typePieceClt;
    private String dateDelivClt;
    private String numPieceClt;
    private String lieuDelivClt;
    private String codLieuDelivClt;
    private String typePieceAnnexe;
    private String dateDellivPiann;
    private String dateFinPian;
    private String numPieceAnnexe;
    private String codeGouvernorat;

    private String titrePersClt;
    private String nomPersClt;
    private String prenomPersClt;
    private String nomPereClt;

    private String dateNaisClt;
    private String lieuNaisClt;
    private String nationaliteClt;
    private String residentClt;
    private String sexeClt;
    private String paysNaisClt;
    private String codPaysNaisClt;

    private String sitFamilialeClt;

    private String sectActiviteClt;
    private String professionClt;
    private String codGroupProfClt;
    private String codProfClt;
    private String activiteClt;

    private String codActiviteClt;
    private String codClasActiviteClt;
    private String codSclasActiviteClt;
    private Blob signatureClt;

    //adresse client;
    private String immeubleAdrResid;
    private String rueAdrResid;
    private String citeAdrResid;
    private String villeAdrResid;
    private String paysAdrResid;
    private String codPayAdrResid;
    private String codePostalAdrResid;
    private String libPostalAdrResid;
    private String immeubleAdrProf;
    private String rueAdrProf;
    private String citeAdrProf;
    private String villeAdrProf;
    private String paysAdrProf;
    private String codPayAdrProf;
    private String codePostalAdrProf;
    private String libPostalAdrProf;
    private String codGouvGouvRes;
    private String libGouvGouvRes;
    private String codGouvGouvProf;
    private String libGouvGouvProf;
    private String typeAdresse;
    private String numTelPers;
    private String numFaxPers;


    //---------------------------------------------------------------
    //tabsheet 4 : Contrat compte /////////////////

    private String codeProduitCpt;
    private String codePrdCpt;
    private String codeStructureCpt;
    private String numCompteCpt;
    private String codeDeviseCpt;
    private String libDeviseCpt;
    private String libelleProduitCpt;
    private String dateOuvertureCpt;
    private String intituleCompteCpt;

    private String typePieceCpt;
    private String nomCpt;
    private String numFiscaleCpt;
    private String numPieceCpt;
    private String codeDouaneCpt;
    private String dateRelationCpt;
    private String prenomCpt;
    private String numBctCpt;
    private String numFiscClt;

    private String immeubleCpt;
    private String rueCpt;
    private String citeCpt;
    private String villeCpt;
    private String paysCpt;
    private String codPayCpt;
    private String codePostalCpt;
    private String libCodePostalCpt;
    private String codGouvGouvCpt;
    private String libGouvGouvCpt;
    private String releveCpt;
    private String peridiciteCpt;
    private String fonctionementCpt;
    private String numMatriculeFisc;
    private String cleMatriculeFisc;
    private String codeTvaFisc;
    private String codeCategorieFisc;
    private String numEtabFisc;
    private String numRnePers;
    //-----------------------------------------------------------------
    // tabsheet 6 Information du tuteur

    private String typePersonneTuteur;
    private String categoriePersonneTuteur;

    private String typePieceTut;
    private String dateDelivTuteur;
    private String numPieceTut;
    private String lieuDelivTuteur;
    private String codLieuDelivTuteur;
    private String typePieceAnnexeTuteur;
    private String dateDellivPiannTuteur;
    private String dateFinPianTuteur;
    private String numPieceAnnexeTuteur;
    private String codeGouvernoratTuteur;

    private String titrePersTuteur;
    private String nomPersTuteur;
    private String prenomPersTuteur;
    private String nomPereTuteur;

    private String dateNaisTuteur;
    private String lieuNaisTuteur;
    private String nationaliteTuteur;
    private String codNationaliteTuteur;
    private String residentTuteur;
    private String sexeTuteur;
    private String paysNaisTuteur;
    private String codPaysNaisTuteur;
    private String sitFamilialeTuteur;
    private String sectActiviteTuteur;
    private String professionTuteur;
    private String codGroupProfTuteur;
    private String codProfTuteur;
    private String activiteTuteur;

    private String codActiviteTuteur;
    private String codClasActiviteTuteur;
    private String codSclasActiviteTuteur;
    private Blob signatureTuteur;
    private String formeJuridiqueTuteur;

    //adresse tuteur;
    private String immeubleAdrResidTuteur;
    private String rueAdrResidTuteur;
    private String citeAdrResidTuteur;
    private String villeAdrResidTuteur;
    private String paysAdrResidTuteur;
    private String codPayAdrResidTuteur;
    private String codePostalAdrResidTuteur;
    private String libPostalAdrResidTuteur;
    private String immeubleAdrProfTuteur;
    private String rueAdrProfTuteur;
    private String citeAdrProfTuteur;
    private String villeAdrProfTuteur;
    private String paysAdrProfTuteur;
    private String codPayAdrProfTuteur;
    private String codePostalAdrProfTuteur;
    private String libPostalAdrProfTuteur;
    private String codGouvGouvResTuteur;
    private String libGouvGouvResTuteur;
    private String codGouvGouvProfTuteur;
    private String libGouvGouvProfTuteur;
    private String numTelTuteur;
    private String numFaxTuteur;


    //--------------------------------
    //: données globales /////////////////
    private String alert;
    private String alertLivret;
    private String alertRcs;
    private String reqCode;
    private String message;
    private InitialisationView initialisationView = new InitialisationView();
    private String alertProduit;
    private String choixInitPage = "initPage";
    private String openTabsheetClient = "false";
    private String openTabsheetContrat = "false";
    private String openTabsheetProduit = "false";
    private String openTabsheetTuteur = "false";
    private String openTabsheetMorale = "false";
    private String openTabsheetCotitulaire = "false";
    private String openTabsheetCompteVert = "false";
    private String dateActuelle;
    private String typePrd;
    private String transactionReussite;
    private String sousFamPrd;
    private String groupePrd;
    private String chargEffectPramEp="false";
    private String NumMatriculeUser;

    //popup Identification
    private String codeResidEtr;
    private String typePieceAnnEtr;
    private String numPieceAnnEtr;
    private String typePiecePopup;
    private String numPiecePopup;
    private String categorieTuteur;
    private String typePieceTuteur;
    private String numPieceTuteur;
    private String nomTuteur;
    private String numSeqTuteur;
    private Boolean isTuteur = false;
    private String alertTuteur;

    private String prenomTuteur;
    private Collection listMineurs;
    private String messageNbreMineurs = "";
    private Integer nombreMineurs = 0;

    private String typePieceCotit;
    private String numPieceCotit;
    private String codeStructureCotit;
    private String codePrdCotit;
    private String numCompteCotit;
    private Collection listEntiteCotit;
    private String alertMembreCotit;
    private String alertListMembresCotit;
    private Collection listMembresEntiteCotit;
    private String entiteChoisie;

    //popup Paramètre Epargne
    private String codeRegimeEpargne;
    private Collection listRegimeEpargne;
    private String codeCategorieEpargne;
    private Collection listCategorieEpargne;
    private String mntVersementEpargne;
    private String mntCapitaliseEpargne;
    private String numLivretEpargne;
    private String mntBourse;
    private String periodVersm;  
    private String typeVers;  
    private String typeRequest;

    //tabsheet 5 : Personne Morale ///////////////// 

    private String typePersonneMoral;
    private String categoriePersonneMoral;
    private String typePieceMoral;
    private String numPieceMoral;
    private String dateDelivMoral;
    private String typeDelivMoral;
    private String lieuDelivMoral;
    private String codLieuDelivMoral;
    private String raisonSocialMoral;
    private String sigleMoral;
    private String residenceMoral;
    private String nationaliteMoral;
    private String secteurActMoral;
    private String dateActMoral;
    private String activiteMoral;
    private String codActiviteMoral;
    private String codClasActiviteMoral;
    private String codSclasActiviteMoral;
    private String dateCreationMoral;
    private String numLoiCreMoral;
    private String numJortMoral;
    private String datJortMoral;
    private String numDecretMoral;
    private String datDecretMoral;

    private String immeubleAdrResidMoral;
    private String rueAdrResidMoral;
    private String citeAdrResidMoral;
    private String villeAdrResidMoral;
    private String paysAdrResidMoral;
    private String codPayAdrResidMoral;
    private String codePostalAdrResidMoral;
    private String libPostalAdrResidMoral;
    private String codGouvGouvMoral;
    private String LibGouvGouvMoral;

    private String numTelMoral;
    private String numFaxMoral;
    private String adrMailMoral;
    private String adrWebMoral;
    private String adrSwiftMoral;
    private String adrTelexMoral;

    //tabSheet compte vert 
    private String codStrcVert;
    private String codPrdVert;
    private String codStrcDav;
    private String numCcptDav;
    private String codPrdDav;
    private String cle;
    private String typPceDav;
    private String numPceDav;
    private String nomNomDav;
    private String nomPrnDav;
    private String montSoldCcpt;
    private String libDevDav;
    private String codeDeviseDav;
    private String soldeMintDav;
    private String alertDav;
    // popup cotitulaire
    private String typeCotit;
    private String typeSignature;
    private String typPcePers;


    //donnes globales
    private String casAnnulation = "";
    private String creerMandat = "false";
    private ContratCpt contratDav;
    private String typePersonneMenu;
    private String libelleConfirmation;
    private String etatFormCreationPersonne = "0";
    private String libelleOperation;

    //---------------------------------------------------------
    ParamInsertContrat ParamInsertContrat = new ParamInsertContrat();
    private Datagrid listPersonneCotitGrid;

    public void reset(ActionMapping mapping, HttpServletRequest request) {
        super.reset(mapping, request);
    }

    public void clearForm() {

        //tabshett 1 : identification /////////////////
        //numPieceId ="";    
        nomId = "";
        prenomId = "";
        titreId = "";

        //tabshett 2 : Produit autorisés /////////////////
        dateNaissancePrd = "";
        nationalitePrd = "";
        codNationalitePrd = "";
        age = "";
        formeJuridiquePrd = "";
        //listeFormeJuridique =null;
        listeContrats = null;
        listeProduits = null;
        produitChoisi = "";
        cltTrouve = null;


        //------------------------------------------------------------
        //tabshett 3 : Client          /////////////////
        typePersonneClt = "";
        categoriePersonneClt = "";
        typePieceClt = "";
        dateDelivClt = "";
        numPieceClt = "";
        lieuDelivClt = "";
        //typePieceAnnexe = "";
        dateDellivPiann = "";
        dateFinPian = "";
        numPieceAnnexe = "";
        codeGouvernorat = "";

        titrePersClt = "";
        nomPersClt = "";
        prenomPersClt = "";
        nomPereClt = "";

        dateNaisClt = "";
        lieuNaisClt = "";
        nationaliteClt = "";
        residentClt = "";
        sexeClt = "";
        paysNaisClt = "";
        sitFamilialeClt = "";

        sectActiviteClt = "";
        professionClt = "";
        activiteClt = "";
        signatureClt = null;


        //---------------------------------------------------------------
        //tabshett 4 : Contrat compte /////////////////
           
        codeProduitCpt = "";
        codePrdCpt = "";
        codeStructureCpt = "";
        numCompteCpt = "";
        codeDeviseCpt = "";
        libDeviseCpt = "";
        libelleProduitCpt = "";
        dateOuvertureCpt = "";
        intituleCompteCpt = "";

        typePieceCpt = "";
        nomCpt = "";
        numFiscaleCpt = "";
        numPieceCpt = "";
        codeDouaneCpt = "";
        dateRelationCpt = "";
        prenomCpt = "";
        numBctCpt = "";
        numFiscClt = "";

        immeubleCpt = "";
        rueCpt = "";
        citeCpt = "";
        villeCpt = "";
        paysCpt = "";
        codePostalCpt = "";
        libCodePostalCpt = "";
        codGouvGouvRes = "";
        libGouvGouvRes = "";
        codGouvGouvProf = "";
        libGouvGouvProf = "";
        codGouvGouvCpt = "";
        libGouvGouvCpt = "";
        typeAdresse = "";
        numMatriculeFisc = "";
        cleMatriculeFisc = "";
        numEtabFisc = "";
        numRnePers="";
        //-----------------------------------------------------------------
        //: données globales /////////////////
        alert = "";
        //alertTuteur = "";
        //reqCode ="initierPage";
        alertProduit = "";
        alertRcs = "";
        //dateActuelle ="";
        typePrd = "";
        sousFamPrd = "";
        groupePrd = "";

        transactionReussite = "";
        //choixInitPage = "initPage";
        openTabsheetClient = "false";
        openTabsheetContrat = "false";
        openTabsheetProduit = "false";
        openTabsheetTuteur = "false";
        openTabsheetMorale = "false";
        openTabsheetCotitulaire = "false";
        openTabsheetCompteVert = "false";
        chargEffectPramEp="false";
        immeubleAdrResid = "";
        rueAdrResid = "";
        citeAdrResid = "";
        villeAdrResid = "";
        paysAdrResid = "";
        codPayAdrResid = "";
        codePostalAdrResid = "";
        libPostalAdrResid = "";
        immeubleAdrProf = "";
        rueAdrProf = "";
        citeAdrProf = "";
        villeAdrProf = "";
        paysAdrProf = "";
        codPayAdrProf = "";
        codePostalAdrProf = "";
        libPostalAdrProf = "";

        //popup Identification
        codeResidEtr = "";
        typePiecePopup = "";
        numPiecePopup = "";
        categorieTuteur = "";
        messageNbreMineurs = "";

        typePieceCotit = "";
        numPieceCotit = "";
        codeStructureCotit = "";
        codePrdCotit = "";
        numCompteCotit = "";
        listEntiteCotit = null;
        alertMembreCotit = "";
        alertListMembresCotit = "";
        listMembresEntiteCotit = null;
        entiteChoisie = "";

        // paramètres epargne
        codeRegimeEpargne = "";
        listRegimeEpargne = null;
        codeCategorieEpargne = "";
        listCategorieEpargne = null;
        mntVersementEpargne = "";
        mntCapitaliseEpargne = "";
        numLivretEpargne = "";
        mntBourse="";
        periodVersm="";
        typeRequest = "";
        etatFormCreationPersonne = "0";


        //tabsheet tuteur
        typePersonneTuteur = "";
        categoriePersonneTuteur = "";
        typePieceTut = "";
        dateDelivTuteur = "";
        numPieceTut = "";
        lieuDelivTuteur = "";
        codLieuDelivTuteur = "";
        typePieceAnnexeTuteur = "";
        dateDellivPiannTuteur = "";
        dateFinPianTuteur = "";
        numPieceAnnexeTuteur = "";
        codeGouvernoratTuteur = "";
        titrePersTuteur = "";
        nomPersTuteur = "";
        prenomPersTuteur = "";
        nomPereTuteur = "";
        dateNaisTuteur = "";
        lieuNaisTuteur = "";
        nationaliteTuteur = "";
        codNationaliteTuteur = "";
        residentTuteur = "";
        sexeTuteur = "";
        paysNaisTuteur = "";
        codPaysNaisTuteur = "";
        sitFamilialeTuteur = "";
        sectActiviteTuteur = "";
        professionTuteur = "";
        codGroupProfTuteur = "";
        codProfTuteur = "";
        activiteTuteur = "";
        codActiviteTuteur = "";
        codClasActiviteTuteur = "";
        codSclasActiviteTuteur = "";
        formeJuridiqueTuteur = "";
        //adresse tuteur="";
        immeubleAdrResidTuteur = "";
        rueAdrResidTuteur = "";
        citeAdrResidTuteur = "";
        villeAdrResidTuteur = "";
        paysAdrResidTuteur = "";
        codPayAdrResidTuteur = "";
        codePostalAdrResidTuteur = "";
        libPostalAdrResidTuteur = "";
        immeubleAdrProfTuteur = "";
        rueAdrProfTuteur = "";
        citeAdrProfTuteur = "";
        villeAdrProfTuteur = "";
        paysAdrProfTuteur = "";
        codPayAdrProfTuteur = "";
        codePostalAdrProfTuteur = "";
        libPostalAdrProfTuteur = "";
        codGouvGouvResTuteur = "";
        libGouvGouvResTuteur = "";
        codGouvGouvProfTuteur = "";
        libGouvGouvProfTuteur = "";

        //tabshett 5 : Personne Morale /////////////////
        typePersonneMoral = "";
        categoriePersonneMoral = "";
        typePieceMoral = "";
        numPieceMoral = "";
        dateDelivMoral = "";
        typeDelivMoral = "";
        lieuDelivMoral = "";
        codLieuDelivMoral = "";
        raisonSocialMoral = "";
        sigleMoral = "";
        residenceMoral = "";
        nationaliteMoral = "";
        secteurActMoral = "";
        dateActMoral = "";
        activiteMoral = "";
        codActiviteMoral = "";
        codClasActiviteMoral = "";
        codSclasActiviteMoral = "";
        dateCreationMoral = "";
        numLoiCreMoral = "";
        datJortMoral = "";
        numJortMoral = "";
        numDecretMoral = "";
        datDecretMoral = "";

        numTelMoral = "";
        numFaxMoral = "";
        adrMailMoral = "";
        adrWebMoral = "";
        adrSwiftMoral = "";
        adrTelexMoral = "";

        immeubleAdrResidMoral = "";
        rueAdrResidMoral = "";
        citeAdrResidMoral = "";
        villeAdrResidMoral = "";
        paysAdrResidMoral = "";
        codPayAdrResidMoral = "";
        codePostalAdrResidMoral = "";
        libPostalAdrResidMoral = "";
        codGouvGouvMoral = "";
        LibGouvGouvMoral = "";


        //tabSheet compte vert 
        codStrcVert = "";
        codPrdVert = "";
        codStrcDav = "";
        numCcptDav = "";
        cle = "";
        typPceDav = "";
        numPceDav = "";
        nomNomDav = "";
        nomPrnDav = "";
        montSoldCcpt = "";
        libDevDav = "";
        codeDeviseDav = "";
        soldeMintDav = "";
        codPrdDav = "";
        alertDav = "";
        //donnes globales
        casAnnulation = "";
        creerMandat = "false";
        contratDav = null;
        message = "";

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

    public void setTypePersonneId(String typePersonneId) {
        this.typePersonneId = typePersonneId;
    }

    public String getTypePersonneId() {
        return typePersonneId;
    }

    public void setCategoriePersonneId(String categoriePersonneId) {
        this.categoriePersonneId = categoriePersonneId;
    }

    public String getCategoriePersonneId() {
        return categoriePersonneId;
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

    public void setListeTypePersonne(Collection listeTypePersonne) {
        this.listeTypePersonne = listeTypePersonne;
    }

    public Collection getListeTypePersonne() {
        return listeTypePersonne;
    }

    public void setListeCategoriePersonne(Collection listeCategoriePersonne) {
        this.listeCategoriePersonne = listeCategoriePersonne;
    }

    public Collection getListeCategoriePersonne() {
        return listeCategoriePersonne;
    }

    public void setNomId(String nomId) {
        this.nomId = nomId;
    }

    public String getNomId() {
        return nomId;
    }


    public void setTitreId(String titreId) {
        this.titreId = titreId;
    }

    public String getTitreId() {
        return titreId;
    }

    public void setPrenomId(String prenomId) {
        this.prenomId = prenomId;
    }

    public String getPrenomId() {
        return prenomId;
    }


    public void setAlert(String alert) {
        this.alert = alert;
    }

    public String getAlert() {
        return alert;
    }

    public void setDateNaissancePrd(String dateNaissancePrd) {
        this.dateNaissancePrd = dateNaissancePrd;
    }

    public String getDateNaissancePrd() {
        return dateNaissancePrd;
    }

    public void setNationalitePrd(String nationalitePrd) {
        this.nationalitePrd = nationalitePrd;
    }

    public String getNationalitePrd() {
        return nationalitePrd;
    }

    public void setResidentPrd(String residentPrd) {
        this.residentPrd = residentPrd;
    }

    public String getResidentPrd() {
        return residentPrd;
    }

    public void setFormeJuridiquePrd(String formeJuridiquePrd) {
        this.formeJuridiquePrd = formeJuridiquePrd;
    }

    public String getFormeJuridiquePrd() {
        return formeJuridiquePrd;
    }

    public void setCodTypePieceId(String codTypePieceId) {
        this.codTypePieceId = codTypePieceId;
    }

    public String getCodTypePieceId() {
        return codTypePieceId;
    }

    public void setCodNationalitePrd(String codNationalitePrd) {
        this.codNationalitePrd = codNationalitePrd;
    }

    public String getCodNationalitePrd() {
        return codNationalitePrd;
    }

    public void setListeFormeJuridique(Collection listeFormeJuridique) {
        this.listeFormeJuridique = listeFormeJuridique;
    }

    public Collection getListeFormeJuridique() {
        return listeFormeJuridique;
    }


    public void setReqCode(String reqCode) {
        this.reqCode = reqCode;
    }

    public String getReqCode() {
        return reqCode;
    }


    public void setListeContrats(Collection listeContrats) {
        this.listeContrats = listeContrats;
    }

    public Collection getListeContrats() {
        return listeContrats;
    }

    public void setChoixInitPage(String choixInitPage) {
        this.choixInitPage = choixInitPage;
    }

    public String getChoixInitPage() {
        return choixInitPage;
    }

    public void setListeProduits(Collection listeProduits) {
        this.listeProduits = listeProduits;
    }

    public Collection getListeProduits() {
        return listeProduits;
    }

    public void setTypePersonneClt(String typePersonneClt) {
        this.typePersonneClt = typePersonneClt;
    }

    public String getTypePersonneClt() {
        return typePersonneClt;
    }

    public void setCategoriePersonneClt(String categoriePersonneClt) {
        this.categoriePersonneClt = categoriePersonneClt;
    }

    public String getCategoriePersonneClt() {
        return categoriePersonneClt;
    }

    public void setTypePieceClt(String typePieceClt) {
        this.typePieceClt = typePieceClt;
    }

    public String getTypePieceClt() {
        return typePieceClt;
    }

    public void setDateDelivClt(String dateDelivClt) {
        this.dateDelivClt = dateDelivClt;
    }

    public String getDateDelivClt() {
        return dateDelivClt;
    }

    public void setNumPieceClt(String numPieceClt) {
        this.numPieceClt = numPieceClt;
    }

    public String getNumPieceClt() {
        return numPieceClt;
    }

    public void setLieuDelivClt(String lieuDelivClt) {
        this.lieuDelivClt = lieuDelivClt;
    }

    public String getLieuDelivClt() {
        return lieuDelivClt;
    }

    public void setTypePieceAnnexe(String typePieceAnnexe) {
        this.typePieceAnnexe = typePieceAnnexe;
    }

    public String getTypePieceAnnexe() {
        return typePieceAnnexe;
    }

    public void setDateDellivPiann(String dateDellivPiann) {
        this.dateDellivPiann = dateDellivPiann;
    }

    public String getDateDellivPiann() {
        return dateDellivPiann;
    }

    public void setDateFinPian(String dateFinPian) {
        this.dateFinPian = dateFinPian;
    }

    public String getDateFinPian() {
        return dateFinPian;
    }

    public void setNumPieceAnnexe(String numPieceAnnexe) {
        this.numPieceAnnexe = numPieceAnnexe;
    }

    public String getNumPieceAnnexe() {
        return numPieceAnnexe;
    }

    public void setCodeGouvernorat(String codeGouvernorat) {
        this.codeGouvernorat = codeGouvernorat;
    }

    public String getCodeGouvernorat() {
        return codeGouvernorat;
    }

    public void setTitrePersClt(String titrePersClt) {
        this.titrePersClt = titrePersClt;
    }

    public String getTitrePersClt() {
        return titrePersClt;
    }

    public void setNomPersClt(String nomPersClt) {
        this.nomPersClt = nomPersClt;
    }

    public String getNomPersClt() {
        return nomPersClt;
    }

    public void setPrenomPersClt(String prenomPersClt) {
        this.prenomPersClt = prenomPersClt;
    }

    public String getPrenomPersClt() {
        return prenomPersClt;
    }

    public void setNomPereClt(String nomPereClt) {
        this.nomPereClt = nomPereClt;
    }

    public String getNomPereClt() {
        return nomPereClt;
    }

    public void setDateNaisClt(String dateNaisClt) {
        this.dateNaisClt = dateNaisClt;
    }

    public String getDateNaisClt() {
        return dateNaisClt;
    }

    public void setLieuNaisClt(String lieuNaisClt) {
        this.lieuNaisClt = lieuNaisClt;
    }

    public String getLieuNaisClt() {
        return lieuNaisClt;
    }

    public void setNationaliteClt(String nationaliteClt) {
        this.nationaliteClt = nationaliteClt;
    }

    public String getNationaliteClt() {
        return nationaliteClt;
    }

    public void setResidentClt(String residentClt) {
        this.residentClt = residentClt;
    }

    public String getResidentClt() {
        return residentClt;
    }

    public void setSexeClt(String sexeClt) {
        this.sexeClt = sexeClt;
    }

    public String getSexeClt() {
        return sexeClt;
    }

    public void setPaysNaisClt(String paysNaisClt) {
        this.paysNaisClt = paysNaisClt;
    }

    public String getPaysNaisClt() {
        return paysNaisClt;
    }

    public void setSitFamilialeClt(String sitFamilialeClt) {
        this.sitFamilialeClt = sitFamilialeClt;
    }

    public String getSitFamilialeClt() {
        return sitFamilialeClt;
    }

    public void setSectActiviteClt(String sectActiviteClt) {
        this.sectActiviteClt = sectActiviteClt;
    }

    public String getSectActiviteClt() {
        return sectActiviteClt;
    }

    public void setProfessionClt(String professionClt) {
        this.professionClt = professionClt;
    }

    public String getProfessionClt() {
        return professionClt;
    }

    public void setActiviteClt(String activiteClt) {
        this.activiteClt = activiteClt;
    }

    public String getActiviteClt() {
        return activiteClt;
    }


    public void setCodeProduitCpt(String codeProduitCpt) {
        this.codeProduitCpt = codeProduitCpt;
    }

    public String getCodeProduitCpt() {
        return codeProduitCpt;
    }

    public void setCodePrdCpt(String codePrdCpt) {
        this.codePrdCpt = codePrdCpt;
    }

    public String getCodePrdCpt() {
        return codePrdCpt;
    }

    public void setCodeStructureCpt(String codeStructureCpt) {
        this.codeStructureCpt = codeStructureCpt;
    }

    public String getCodeStructureCpt() {
        return codeStructureCpt;
    }

    public void setNumCompteCpt(String numCompteCpt) {
        this.numCompteCpt = numCompteCpt;
    }

    public String getNumCompteCpt() {
        return numCompteCpt;
    }

    public void setCodeDeviseCpt(String codeDeviseCpt) {
        this.codeDeviseCpt = codeDeviseCpt;
    }

    public String getCodeDeviseCpt() {
        return codeDeviseCpt;
    }

    public void setLibelleProduitCpt(String libelleProduitCpt) {
        this.libelleProduitCpt = libelleProduitCpt;
    }

    public String getLibelleProduitCpt() {
        return libelleProduitCpt;
    }

    public void setDateOuvertureCpt(String dateOuvertureCpt) {
        this.dateOuvertureCpt = dateOuvertureCpt;
    }

    public String getDateOuvertureCpt() {
        return dateOuvertureCpt;
    }


    public void setIntituleCompteCpt(String intituleCompteCpt) {
        this.intituleCompteCpt = intituleCompteCpt;
    }

    public String getIntituleCompteCpt() {
        return intituleCompteCpt;
    }

    public void setTypePieceCpt(String typePieceCpt) {
        this.typePieceCpt = typePieceCpt;
    }

    public String getTypePieceCpt() {
        return typePieceCpt;
    }

    public void setNomCpt(String nomCpt) {
        this.nomCpt = nomCpt;
    }

    public String getNomCpt() {
        return nomCpt;
    }

    public void setNumFiscaleCpt(String numFiscaleCpt) {
        this.numFiscaleCpt = numFiscaleCpt;
    }

    public String getNumFiscaleCpt() {
        return numFiscaleCpt;
    }

    public void setNumPieceCpt(String numPieceCpt) {
        this.numPieceCpt = numPieceCpt;
    }

    public String getNumPieceCpt() {
        return numPieceCpt;
    }

    public void setCodeDouaneCpt(String codeDouaneCpt) {
        this.codeDouaneCpt = codeDouaneCpt;
    }

    public String getCodeDouaneCpt() {
        return codeDouaneCpt;
    }

    public void setDateRelationCpt(String dateRelationCpt) {
        this.dateRelationCpt = dateRelationCpt;
    }

    public String getDateRelationCpt() {
        return dateRelationCpt;
    }

    public void setPrenomCpt(String prenomCpt) {
        this.prenomCpt = prenomCpt;
    }

    public String getPrenomCpt() {
        return prenomCpt;
    }

    public void setNumBctCpt(String numBctCpt) {
        this.numBctCpt = numBctCpt;
    }

    public String getNumBctCpt() {
        return numBctCpt;
    }

    public void setImmeubleCpt(String immeubleCpt) {
        this.immeubleCpt = immeubleCpt;
    }

    public String getImmeubleCpt() {
        return immeubleCpt;
    }

    public void setRueCpt(String rueCpt) {
        this.rueCpt = rueCpt;
    }

    public String getRueCpt() {
        return rueCpt;
    }

    public void setCiteCpt(String citeCpt) {
        this.citeCpt = citeCpt;
    }

    public String getCiteCpt() {
        return citeCpt;
    }

    public void setVilleCpt(String villeCpt) {
        this.villeCpt = villeCpt;
    }

    public String getVilleCpt() {
        return villeCpt;
    }

    public void setPaysCpt(String paysCpt) {
        this.paysCpt = paysCpt;
    }

    public String getPaysCpt() {
        return paysCpt;
    }

    public void setCodePostalCpt(String codePostalCpt) {
        this.codePostalCpt = codePostalCpt;
    }

    public String getCodePostalCpt() {
        return codePostalCpt;
    }

    public void setLibCodePostalCpt(String libCodePostalCpt) {
        this.libCodePostalCpt = libCodePostalCpt;
    }

    public String getLibCodePostalCpt() {
        return libCodePostalCpt;
    }


    public void setOpenTabsheetClient(String openTabsheetClient) {
        this.openTabsheetClient = openTabsheetClient;
    }

    public String getOpenTabsheetClient() {
        return openTabsheetClient;
    }

    public void setOpenTabsheetContrat(String openTabsheetContrat) {
        this.openTabsheetContrat = openTabsheetContrat;
    }

    public String getOpenTabsheetContrat() {
        return openTabsheetContrat;
    }

    public void setOpenTabsheetProduit(String openTabsheetProduit) {
        this.openTabsheetProduit = openTabsheetProduit;
    }

    public String getOpenTabsheetProduit() {
        return openTabsheetProduit;
    }

    public void setProduitChoisi(String produitChoisi) {
        this.produitChoisi = produitChoisi;
    }

    public String getProduitChoisi() {
        return produitChoisi;
    }


    public void setDateActuelle(String dateActuelle) {
        this.dateActuelle = dateActuelle;
    }

    public String getDateActuelle() {
        return dateActuelle;
    }

    public void setLibDeviseCpt(String libDeviseCpt) {
        this.libDeviseCpt = libDeviseCpt;
    }

    public String getLibDeviseCpt() {
        return libDeviseCpt;
    }

    public void setAlertProduit(String alertProduit) {
        this.alertProduit = alertProduit;
    }

    public String getAlertProduit() {
        return alertProduit;
    }

    public void setListTypePersonneClt(Collection listTypePersonneClt) {
        this.listTypePersonneClt = listTypePersonneClt;
    }

    public Collection getListTypePersonneClt() {
        return listTypePersonneClt;
    }

    public void setListCategoriePersonneClt(Collection listCategoriePersonneClt) {
        this.listCategoriePersonneClt = listCategoriePersonneClt;
    }

    public Collection getListCategoriePersonneClt() {
        return listCategoriePersonneClt;
    }

    public void setNumSeqPers(String numSeqPers) {
        this.numSeqPers = numSeqPers;
    }

    public String getNumSeqPers() {
        return numSeqPers;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getAge() {
        return age;
    }

    public void setCodPaysNaisClt(String codPaysNaisClt) {
        this.codPaysNaisClt = codPaysNaisClt;
    }

    public String getCodPaysNaisClt() {
        return codPaysNaisClt;
    }

    public void setCodActiviteClt(String codActiviteClt) {
        this.codActiviteClt = codActiviteClt;
    }

    public String getCodActiviteClt() {
        return codActiviteClt;
    }

    public void setCodClasActiviteClt(String codClasActiviteClt) {
        this.codClasActiviteClt = codClasActiviteClt;
    }

    public String getCodClasActiviteClt() {
        return codClasActiviteClt;
    }

    public void setCodSclasActiviteClt(String codSclasActiviteClt) {
        this.codSclasActiviteClt = codSclasActiviteClt;
    }

    public String getCodSclasActiviteClt() {
        return codSclasActiviteClt;
    }

    public void setCodGroupProfClt(String codGroupProfClt) {
        this.codGroupProfClt = codGroupProfClt;
    }

    public String getCodGroupProfClt() {
        return codGroupProfClt;
    }

    public void setCodProfClt(String codProfClt) {
        this.codProfClt = codProfClt;
    }

    public String getCodProfClt() {
        return codProfClt;
    }

    public void setCodPayCpt(String codPayCpt) {
        this.codPayCpt = codPayCpt;
    }

    public String getCodPayCpt() {
        return codPayCpt;
    }

    public void setImmeubleAdrResid(String immeubleAdrResid) {
        this.immeubleAdrResid = immeubleAdrResid;
    }

    public String getImmeubleAdrResid() {
        return immeubleAdrResid;
    }

    public void setRueAdrResid(String rueAdrResid) {
        this.rueAdrResid = rueAdrResid;
    }

    public String getRueAdrResid() {
        return rueAdrResid;
    }

    public void setCiteAdrResid(String citeAdrResid) {
        this.citeAdrResid = citeAdrResid;
    }

    public String getCiteAdrResid() {
        return citeAdrResid;
    }

    public void setVilleAdrResid(String villeAdrResid) {
        this.villeAdrResid = villeAdrResid;
    }

    public String getVilleAdrResid() {
        return villeAdrResid;
    }

    public void setPaysAdrResid(String paysAdrResid) {
        this.paysAdrResid = paysAdrResid;
    }

    public String getPaysAdrResid() {
        return paysAdrResid;
    }

    public void setCodePostalAdrResid(String codePostalAdrResid) {
        this.codePostalAdrResid = codePostalAdrResid;
    }

    public String getCodePostalAdrResid() {
        return codePostalAdrResid;
    }

    public void setLibPostalAdrResid(String libPostalAdrResid) {
        this.libPostalAdrResid = libPostalAdrResid;
    }

    public String getLibPostalAdrResid() {
        return libPostalAdrResid;
    }

    public void setImmeubleAdrProf(String immeubleAdrProf) {
        this.immeubleAdrProf = immeubleAdrProf;
    }

    public String getImmeubleAdrProf() {
        return immeubleAdrProf;
    }

    public void setRueAdrProf(String rueAdrProf) {
        this.rueAdrProf = rueAdrProf;
    }

    public String getRueAdrProf() {
        return rueAdrProf;
    }

    public void setCiteAdrProf(String citeAdrProf) {
        this.citeAdrProf = citeAdrProf;
    }

    public String getCiteAdrProf() {
        return citeAdrProf;
    }

    public void setVilleAdrProf(String villeAdrProf) {
        this.villeAdrProf = villeAdrProf;
    }

    public String getVilleAdrProf() {
        return villeAdrProf;
    }

    public void setPaysAdrProf(String paysAdrProf) {
        this.paysAdrProf = paysAdrProf;
    }

    public String getPaysAdrProf() {
        return paysAdrProf;
    }

    public void setCodePostalAdrProf(String codePostalAdrProf) {
        this.codePostalAdrProf = codePostalAdrProf;
    }

    public String getCodePostalAdrProf() {
        return codePostalAdrProf;
    }

    public void setLibPostalAdrProf(String libPostalAdrProf) {
        this.libPostalAdrProf = libPostalAdrProf;
    }

    public String getLibPostalAdrProf() {
        return libPostalAdrProf;
    }

    public void setCodPayAdrResid(String codPayAdrResid) {
        this.codPayAdrResid = codPayAdrResid;
    }

    public String getCodPayAdrResid() {
        return codPayAdrResid;
    }

    public void setCodPayAdrProf(String codPayAdrProf) {
        this.codPayAdrProf = codPayAdrProf;
    }

    public String getCodPayAdrProf() {
        return codPayAdrProf;
    }


    public void setTransactionReussite(String transactionReussite) {
        this.transactionReussite = transactionReussite;
    }

    public String getTransactionReussite() {
        return transactionReussite;
    }


    public void setCodLieuDelivClt(String codLieuDelivClt) {
        this.codLieuDelivClt = codLieuDelivClt;
    }

    public String getCodLieuDelivClt() {
        return codLieuDelivClt;
    }

    public void setCodeResidEtr(String codeResidEtr) {
        this.codeResidEtr = codeResidEtr;
    }

    public String getCodeResidEtr() {
        return codeResidEtr;
    }

    public void setTypePieceAnnEtr(String typePieceAnnEtr) {
        this.typePieceAnnEtr = typePieceAnnEtr;
    }

    public String getTypePieceAnnEtr() {
        return typePieceAnnEtr;
    }

    public void setNumPieceAnnEtr(String numPieceAnnEtr) {
        this.numPieceAnnEtr = numPieceAnnEtr;
    }

    public String getNumPieceAnnEtr() {
        return numPieceAnnEtr;
    }

    public void setSignatureClt(Blob signatureClt) {
        this.signatureClt = signatureClt;
    }

    public Blob getSignatureClt() {
        return signatureClt;
    }

    public void setTypePiecePopup(String typePiecePopup) {
        this.typePiecePopup = typePiecePopup;
    }

    public String getTypePiecePopup() {
        return typePiecePopup;
    }

    public void setNumPiecePopup(String numPiecePopup) {
        this.numPiecePopup = numPiecePopup;
    }

    public String getNumPiecePopup() {
        return numPiecePopup;
    }

    public void setCodeRegimeEpargne(String codeRegimeEpargne) {
        this.codeRegimeEpargne = codeRegimeEpargne;
    }

    public String getCodeRegimeEpargne() {
        return codeRegimeEpargne;
    }

    public void setListRegimeEpargne(Collection listRegimeEpargne) {
        this.listRegimeEpargne = listRegimeEpargne;
    }

    public Collection getListRegimeEpargne() {
        return listRegimeEpargne;
    }

    public void setCodeCategorieEpargne(String codeCategorieEpargne) {
        this.codeCategorieEpargne = codeCategorieEpargne;
    }

    public String getCodeCategorieEpargne() {
        return codeCategorieEpargne;
    }

    public void setListCategorieEpargne(Collection listCategorieEpargne) {
        this.listCategorieEpargne = listCategorieEpargne;
    }

    public Collection getListCategorieEpargne() {
        return listCategorieEpargne;
    }

    public void setMntVersementEpargne(String mntVersementEpargne) {
        this.mntVersementEpargne = mntVersementEpargne;
    }

    public String getMntVersementEpargne() {
        return mntVersementEpargne;
    }

    public void setMntCapitaliseEpargne(String mntCapitaliseEpargne) {
        this.mntCapitaliseEpargne = mntCapitaliseEpargne;
    }

    public String getMntCapitaliseEpargne() {
        return mntCapitaliseEpargne;
    }

    public void setNumLivretEpargne(String numLivretEpargne) {
        this.numLivretEpargne = numLivretEpargne;
    }

    public String getNumLivretEpargne() {
        return numLivretEpargne;
    }

    public void setTypeRequest(String typeRequest) {
        this.typeRequest = typeRequest;
    }

    public String getTypeRequest() {
        return typeRequest;
    }


    public void setTypePrd(String typePrd) {
        this.typePrd = typePrd;
    }

    public String getTypePrd() {
        return typePrd;
    }


    public void setSousFamPrd(String sousFamPrd) {
        this.sousFamPrd = sousFamPrd;
    }

    public String getSousFamPrd() {
        return sousFamPrd;
    }

    public void setGroupePrd(String groupePrd) {
        this.groupePrd = groupePrd;
    }

    public String getGroupePrd() {
        return groupePrd;
    }

    public void setCategorieTuteur(String categorieTuteur) {
        this.categorieTuteur = categorieTuteur;
    }

    public String getCategorieTuteur() {
        return categorieTuteur;
    }

    public void setTypePieceTuteur(String typePieceTuteur) {
        this.typePieceTuteur = typePieceTuteur;
    }

    public String getTypePieceTuteur() {
        return typePieceTuteur;
    }

    public void setNumPieceTuteur(String numPieceTuteur) {
        this.numPieceTuteur = numPieceTuteur;
    }

    public String getNumPieceTuteur() {
        return numPieceTuteur;
    }

    public void setNomTuteur(String nomTuteur) {
        this.nomTuteur = nomTuteur;
    }

    public String getNomTuteur() {
        return nomTuteur;
    }

    public void setPrenomTuteur(String prenomTuteur) {
        this.prenomTuteur = prenomTuteur;
    }

    public String getPrenomTuteur() {
        return prenomTuteur;
    }

    public void setListMineurs(Collection listMineurs) {
        this.listMineurs = listMineurs;
    }

    public Collection getListMineurs() {
        return listMineurs;
    }

    public void setNumSeqTuteur(String numSeqTuteur) {
        this.numSeqTuteur = numSeqTuteur;
    }

    public String getNumSeqTuteur() {
        return numSeqTuteur;
    }


    public void setIsTuteur(Boolean isTuteur) {
        this.isTuteur = isTuteur;
    }

    public Boolean getIsTuteur() {
        return isTuteur;
    }

    public void setAlertTuteur(String alertTuteur) {
        this.alertTuteur = alertTuteur;
    }

    public String getAlertTuteur() {
        return alertTuteur;
    }

    public void setTypePersonneTuteur(String typePersonneTuteur) {
        this.typePersonneTuteur = typePersonneTuteur;
    }

    public String getTypePersonneTuteur() {
        return typePersonneTuteur;
    }

    public void setCategoriePersonneTuteur(String categoriePersonneTuteur) {
        this.categoriePersonneTuteur = categoriePersonneTuteur;
    }

    public String getCategoriePersonneTuteur() {
        return categoriePersonneTuteur;
    }


    public void setTypePieceTut(String typePieceTut) {
        this.typePieceTut = typePieceTut;
    }

    public String getTypePieceTut() {
        return typePieceTut;
    }

    public void setDateDelivTuteur(String dateDelivTuteur) {
        this.dateDelivTuteur = dateDelivTuteur;
    }

    public String getDateDelivTuteur() {
        return dateDelivTuteur;
    }

    public void setNumPieceTut(String numPieceTut) {
        this.numPieceTut = numPieceTut;
    }

    public String getNumPieceTut() {
        return numPieceTut;
    }

    public void setLieuDelivTuteur(String lieuDelivTuteur) {
        this.lieuDelivTuteur = lieuDelivTuteur;
    }

    public String getLieuDelivTuteur() {
        return lieuDelivTuteur;
    }

    public void setCodLieuDelivTuteur(String codLieuDelivTuteur) {
        this.codLieuDelivTuteur = codLieuDelivTuteur;
    }

    public String getCodLieuDelivTuteur() {
        return codLieuDelivTuteur;
    }

    public void setTypePieceAnnexeTuteur(String typePieceAnnexeTuteur) {
        this.typePieceAnnexeTuteur = typePieceAnnexeTuteur;
    }

    public String getTypePieceAnnexeTuteur() {
        return typePieceAnnexeTuteur;
    }

    public void setDateDellivPiannTuteur(String dateDellivPiannTuteur) {
        this.dateDellivPiannTuteur = dateDellivPiannTuteur;
    }

    public String getDateDellivPiannTuteur() {
        return dateDellivPiannTuteur;
    }

    public void setDateFinPianTuteur(String dateFinPianTuteur) {
        this.dateFinPianTuteur = dateFinPianTuteur;
    }

    public String getDateFinPianTuteur() {
        return dateFinPianTuteur;
    }

    public void setNumPieceAnnexeTuteur(String numPieceAnnexeTuteur) {
        this.numPieceAnnexeTuteur = numPieceAnnexeTuteur;
    }

    public String getNumPieceAnnexeTuteur() {
        return numPieceAnnexeTuteur;
    }

    public void setCodeGouvernoratTuteur(String codeGouvernoratTuteur) {
        this.codeGouvernoratTuteur = codeGouvernoratTuteur;
    }

    public String getCodeGouvernoratTuteur() {
        return codeGouvernoratTuteur;
    }

    public void setTitrePersTuteur(String titrePersTuteur) {
        this.titrePersTuteur = titrePersTuteur;
    }

    public String getTitrePersTuteur() {
        return titrePersTuteur;
    }

    public void setNomPersTuteur(String nomPersTuteur) {
        this.nomPersTuteur = nomPersTuteur;
    }

    public String getNomPersTuteur() {
        return nomPersTuteur;
    }

    public void setPrenomPersTuteur(String prenomPersTuteur) {
        this.prenomPersTuteur = prenomPersTuteur;
    }

    public String getPrenomPersTuteur() {
        return prenomPersTuteur;
    }

    public void setNomPereTuteur(String nomPereTuteur) {
        this.nomPereTuteur = nomPereTuteur;
    }

    public String getNomPereTuteur() {
        return nomPereTuteur;
    }

    public void setDateNaisTuteur(String dateNaisTuteur) {
        this.dateNaisTuteur = dateNaisTuteur;
    }

    public String getDateNaisTuteur() {
        return dateNaisTuteur;
    }

    public void setLieuNaisTuteur(String lieuNaisTuteur) {
        this.lieuNaisTuteur = lieuNaisTuteur;
    }

    public String getLieuNaisTuteur() {
        return lieuNaisTuteur;
    }

    public void setNationaliteTuteur(String nationaliteTuteur) {
        this.nationaliteTuteur = nationaliteTuteur;
    }

    public String getNationaliteTuteur() {
        return nationaliteTuteur;
    }

    public void setResidentTuteur(String residentTuteur) {
        this.residentTuteur = residentTuteur;
    }

    public String getResidentTuteur() {
        return residentTuteur;
    }

    public void setSexeTuteur(String sexeTuteur) {
        this.sexeTuteur = sexeTuteur;
    }

    public String getSexeTuteur() {
        return sexeTuteur;
    }

    public void setPaysNaisTuteur(String paysNaisTuteur) {
        this.paysNaisTuteur = paysNaisTuteur;
    }

    public String getPaysNaisTuteur() {
        return paysNaisTuteur;
    }

    public void setCodPaysNaisTuteur(String codPaysNaisTuteur) {
        this.codPaysNaisTuteur = codPaysNaisTuteur;
    }

    public String getCodPaysNaisTuteur() {
        return codPaysNaisTuteur;
    }

    public void setSitFamilialeTuteur(String sitFamilialeTuteur) {
        this.sitFamilialeTuteur = sitFamilialeTuteur;
    }

    public String getSitFamilialeTuteur() {
        return sitFamilialeTuteur;
    }

    public void setSectActiviteTuteur(String sectActiviteTuteur) {
        this.sectActiviteTuteur = sectActiviteTuteur;
    }

    public String getSectActiviteTuteur() {
        return sectActiviteTuteur;
    }

    public void setProfessionTuteur(String professionTuteur) {
        this.professionTuteur = professionTuteur;
    }

    public String getProfessionTuteur() {
        return professionTuteur;
    }

    public void setCodGroupProfTuteur(String codGroupProfTuteur) {
        this.codGroupProfTuteur = codGroupProfTuteur;
    }

    public String getCodGroupProfTuteur() {
        return codGroupProfTuteur;
    }

    public void setCodProfTuteur(String codProfTuteur) {
        this.codProfTuteur = codProfTuteur;
    }

    public String getCodProfTuteur() {
        return codProfTuteur;
    }

    public void setActiviteTuteur(String activiteTuteur) {
        this.activiteTuteur = activiteTuteur;
    }

    public String getActiviteTuteur() {
        return activiteTuteur;
    }

    public void setCodActiviteTuteur(String codActiviteTuteur) {
        this.codActiviteTuteur = codActiviteTuteur;
    }

    public String getCodActiviteTuteur() {
        return codActiviteTuteur;
    }

    public void setCodClasActiviteTuteur(String codClasActiviteTuteur) {
        this.codClasActiviteTuteur = codClasActiviteTuteur;
    }

    public String getCodClasActiviteTuteur() {
        return codClasActiviteTuteur;
    }

    public void setCodSclasActiviteTuteur(String codSclasActiviteTuteur) {
        this.codSclasActiviteTuteur = codSclasActiviteTuteur;
    }

    public String getCodSclasActiviteTuteur() {
        return codSclasActiviteTuteur;
    }

    public void setSignatureTuteur(Blob signatureTuteur) {
        this.signatureTuteur = signatureTuteur;
    }

    public Blob getSignatureTuteur() {
        return signatureTuteur;
    }

    public void setOpenTabsheetTuteur(String openTabsheetTuteur) {
        this.openTabsheetTuteur = openTabsheetTuteur;
    }

    public String getOpenTabsheetTuteur() {
        return openTabsheetTuteur;
    }

    public void setFormeJuridiqueTuteur(String formeJuridiqueTuteur) {
        this.formeJuridiqueTuteur = formeJuridiqueTuteur;
    }

    public String getFormeJuridiqueTuteur() {
        return formeJuridiqueTuteur;
    }

    public void setImmeubleAdrResidTuteur(String immeubleAdrResidTuteur) {
        this.immeubleAdrResidTuteur = immeubleAdrResidTuteur;
    }

    public String getImmeubleAdrResidTuteur() {
        return immeubleAdrResidTuteur;
    }

    public void setRueAdrResidTuteur(String rueAdrResidTuteur) {
        this.rueAdrResidTuteur = rueAdrResidTuteur;
    }

    public String getRueAdrResidTuteur() {
        return rueAdrResidTuteur;
    }

    public void setCiteAdrResidTuteur(String citeAdrResidTuteur) {
        this.citeAdrResidTuteur = citeAdrResidTuteur;
    }

    public String getCiteAdrResidTuteur() {
        return citeAdrResidTuteur;
    }

    public void setVilleAdrResidTuteur(String villeAdrResidTuteur) {
        this.villeAdrResidTuteur = villeAdrResidTuteur;
    }

    public String getVilleAdrResidTuteur() {
        return villeAdrResidTuteur;
    }

    public void setPaysAdrResidTuteur(String paysAdrResidTuteur) {
        this.paysAdrResidTuteur = paysAdrResidTuteur;
    }

    public String getPaysAdrResidTuteur() {
        return paysAdrResidTuteur;
    }

    public void setCodPayAdrResidTuteur(String codPayAdrResidTuteur) {
        this.codPayAdrResidTuteur = codPayAdrResidTuteur;
    }

    public String getCodPayAdrResidTuteur() {
        return codPayAdrResidTuteur;
    }

    public void setCodePostalAdrResidTuteur(String codePostalAdrResidTuteur) {
        this.codePostalAdrResidTuteur = codePostalAdrResidTuteur;
    }

    public String getCodePostalAdrResidTuteur() {
        return codePostalAdrResidTuteur;
    }

    public void setLibPostalAdrResidTuteur(String libPostalAdrResidTuteur) {
        this.libPostalAdrResidTuteur = libPostalAdrResidTuteur;
    }

    public String getLibPostalAdrResidTuteur() {
        return libPostalAdrResidTuteur;
    }

    public void setImmeubleAdrProfTuteur(String immeubleAdrProfTuteur) {
        this.immeubleAdrProfTuteur = immeubleAdrProfTuteur;
    }

    public String getImmeubleAdrProfTuteur() {
        return immeubleAdrProfTuteur;
    }

    public void setRueAdrProfTuteur(String rueAdrProfTuteur) {
        this.rueAdrProfTuteur = rueAdrProfTuteur;
    }

    public String getRueAdrProfTuteur() {
        return rueAdrProfTuteur;
    }

    public void setCiteAdrProfTuteur(String citeAdrProfTuteur) {
        this.citeAdrProfTuteur = citeAdrProfTuteur;
    }

    public String getCiteAdrProfTuteur() {
        return citeAdrProfTuteur;
    }

    public void setVilleAdrProfTuteur(String villeAdrProfTuteur) {
        this.villeAdrProfTuteur = villeAdrProfTuteur;
    }

    public String getVilleAdrProfTuteur() {
        return villeAdrProfTuteur;
    }

    public void setPaysAdrProfTuteur(String paysAdrProfTuteur) {
        this.paysAdrProfTuteur = paysAdrProfTuteur;
    }

    public String getPaysAdrProfTuteur() {
        return paysAdrProfTuteur;
    }

    public void setCodPayAdrProfTuteur(String codPayAdrProfTuteur) {
        this.codPayAdrProfTuteur = codPayAdrProfTuteur;
    }

    public String getCodPayAdrProfTuteur() {
        return codPayAdrProfTuteur;
    }

    public void setCodePostalAdrProfTuteur(String codePostalAdrProfTuteur) {
        this.codePostalAdrProfTuteur = codePostalAdrProfTuteur;
    }

    public String getCodePostalAdrProfTuteur() {
        return codePostalAdrProfTuteur;
    }

    public void setLibPostalAdrProfTuteur(String libPostalAdrProfTuteur) {
        this.libPostalAdrProfTuteur = libPostalAdrProfTuteur;
    }

    public String getLibPostalAdrProfTuteur() {
        return libPostalAdrProfTuteur;
    }

    public void setOpenTabsheetMorale(String openTabsheetMorale) {
        this.openTabsheetMorale = openTabsheetMorale;
    }

    public String getOpenTabsheetMorale() {
        return openTabsheetMorale;
    }

    public void setOpenTabsheetCotitulaire(String openTabsheetCotitulaire) {
        this.openTabsheetCotitulaire = openTabsheetCotitulaire;
    }

    public String getOpenTabsheetCotitulaire() {
        return openTabsheetCotitulaire;
    }

    public void setCodNationaliteTuteur(String codNationaliteTuteur) {
        this.codNationaliteTuteur = codNationaliteTuteur;
    }

    public String getCodNationaliteTuteur() {
        return codNationaliteTuteur;
    }


    public void setMessageNbreMineurs(String messageNbreMineurs) {
        this.messageNbreMineurs = messageNbreMineurs;
    }

    public String getMessageNbreMineurs() {
        return messageNbreMineurs;
    }


    public void setNombreMineurs(Integer nombreMineurs) {
        this.nombreMineurs = nombreMineurs;
    }

    public Integer getNombreMineurs() {
        return nombreMineurs;
    }


    public void setTypePersonneMoral(String typePersonneMoral) {
        this.typePersonneMoral = typePersonneMoral;
    }

    public String getTypePersonneMoral() {
        return typePersonneMoral;
    }

    public void setCategoriePersonneMoral(String categoriePersonneMoral) {
        this.categoriePersonneMoral = categoriePersonneMoral;
    }

    public String getCategoriePersonneMoral() {
        return categoriePersonneMoral;
    }

    public void setTypePieceMoral(String typePieceMoral) {
        this.typePieceMoral = typePieceMoral;
    }

    public String getTypePieceMoral() {
        return typePieceMoral;
    }

    public void setNumPieceMoral(String numPieceMoral) {
        this.numPieceMoral = numPieceMoral;
    }

    public String getNumPieceMoral() {
        return numPieceMoral;
    }

    public void setDateDelivMoral(String dateDelivMoral) {
        this.dateDelivMoral = dateDelivMoral;
    }

    public String getDateDelivMoral() {
        return dateDelivMoral;
    }

    public void setTypeDelivMoral(String typeDelivMoral) {
        this.typeDelivMoral = typeDelivMoral;
    }

    public String getTypeDelivMoral() {
        return typeDelivMoral;
    }

    public void setLieuDelivMoral(String lieuDelivMoral) {
        this.lieuDelivMoral = lieuDelivMoral;
    }

    public String getLieuDelivMoral() {
        return lieuDelivMoral;
    }

    public void setCodLieuDelivMoral(String codLieuDelivMoral) {
        this.codLieuDelivMoral = codLieuDelivMoral;
    }

    public String getCodLieuDelivMoral() {
        return codLieuDelivMoral;
    }

    public void setRaisonSocialMoral(String raisonSocialMoral) {
        this.raisonSocialMoral = raisonSocialMoral;
    }

    public String getRaisonSocialMoral() {
        return raisonSocialMoral;
    }

    public void setSigleMoral(String sigleMoral) {
        this.sigleMoral = sigleMoral;
    }

    public String getSigleMoral() {
        return sigleMoral;
    }

    public void setResidenceMoral(String residenceMoral) {
        this.residenceMoral = residenceMoral;
    }

    public String getResidenceMoral() {
        return residenceMoral;
    }

    public void setNationaliteMoral(String nationaliteMoral) {
        this.nationaliteMoral = nationaliteMoral;
    }

    public String getNationaliteMoral() {
        return nationaliteMoral;
    }

    public void setSecteurActMoral(String secteurActMoral) {
        this.secteurActMoral = secteurActMoral;
    }

    public String getSecteurActMoral() {
        return secteurActMoral;
    }

    public void setDateActMoral(String dateActMoral) {
        this.dateActMoral = dateActMoral;
    }

    public String getDateActMoral() {
        return dateActMoral;
    }

    public void setActiviteMoral(String activiteMoral) {
        this.activiteMoral = activiteMoral;
    }

    public String getActiviteMoral() {
        return activiteMoral;
    }

    public void setCodActiviteMoral(String codActiviteMoral) {
        this.codActiviteMoral = codActiviteMoral;
    }

    public String getCodActiviteMoral() {
        return codActiviteMoral;
    }

    public void setCodClasActiviteMoral(String codClasActiviteMoral) {
        this.codClasActiviteMoral = codClasActiviteMoral;
    }

    public String getCodClasActiviteMoral() {
        return codClasActiviteMoral;
    }

    public void setCodSclasActiviteMoral(String codSclasActiviteMoral) {
        this.codSclasActiviteMoral = codSclasActiviteMoral;
    }

    public String getCodSclasActiviteMoral() {
        return codSclasActiviteMoral;
    }

    public void setDateCreationMoral(String dateCreationMoral) {
        this.dateCreationMoral = dateCreationMoral;
    }

    public String getDateCreationMoral() {
        return dateCreationMoral;
    }

    public void setNumLoiCreMoral(String numLoiCreMoral) {
        this.numLoiCreMoral = numLoiCreMoral;
    }

    public String getNumLoiCreMoral() {
        return numLoiCreMoral;
    }

    public void setNumJortMoral(String numJortMoral) {
        this.numJortMoral = numJortMoral;
    }

    public String getNumJortMoral() {
        return numJortMoral;
    }

    public void setDatJortMoral(String datJortMoral) {
        this.datJortMoral = datJortMoral;
    }

    public String getDatJortMoral() {
        return datJortMoral;
    }

    public void setNumDecretMoral(String numDecretMoral) {
        this.numDecretMoral = numDecretMoral;
    }

    public String getNumDecretMoral() {
        return numDecretMoral;
    }

    public void setDatDecretMoral(String datDecretMoral) {
        this.datDecretMoral = datDecretMoral;
    }

    public String getDatDecretMoral() {
        return datDecretMoral;
    }

    public void setNumTelMoral(String numTelMoral) {
        this.numTelMoral = numTelMoral;
    }

    public String getNumTelMoral() {
        return numTelMoral;
    }

    public void setNumFaxMoral(String numFaxMoral) {
        this.numFaxMoral = numFaxMoral;
    }

    public String getNumFaxMoral() {
        return numFaxMoral;
    }

    public void setAdrMailMoral(String adrMailMoral) {
        this.adrMailMoral = adrMailMoral;
    }

    public String getAdrMailMoral() {
        return adrMailMoral;
    }

    public void setAdrWebMoral(String adrWebMoral) {
        this.adrWebMoral = adrWebMoral;
    }

    public String getAdrWebMoral() {
        return adrWebMoral;
    }

    public void setAdrSwiftMoral(String adrSwiftMoral) {
        this.adrSwiftMoral = adrSwiftMoral;
    }

    public String getAdrSwiftMoral() {
        return adrSwiftMoral;
    }

    public void setAdrTelexMoral(String adrTelexMoral) {
        this.adrTelexMoral = adrTelexMoral;
    }

    public String getAdrTelexMoral() {
        return adrTelexMoral;
    }

    public void setCodPayAdrResidMoral(String codPayAdrResidMoral) {
        this.codPayAdrResidMoral = codPayAdrResidMoral;
    }

    public String getCodPayAdrResidMoral() {
        return codPayAdrResidMoral;
    }

    public void setImmeubleAdrResidMoral(String immeubleAdrResidMoral) {
        this.immeubleAdrResidMoral = immeubleAdrResidMoral;
    }

    public String getImmeubleAdrResidMoral() {
        return immeubleAdrResidMoral;
    }

    public void setRueAdrResidMoral(String rueAdrResidMoral) {
        this.rueAdrResidMoral = rueAdrResidMoral;
    }

    public String getRueAdrResidMoral() {
        return rueAdrResidMoral;
    }

    public void setCiteAdrResidMoral(String citeAdrResidMoral) {
        this.citeAdrResidMoral = citeAdrResidMoral;
    }

    public String getCiteAdrResidMoral() {
        return citeAdrResidMoral;
    }

    public void setVilleAdrResidMoral(String villeAdrResidMoral) {
        this.villeAdrResidMoral = villeAdrResidMoral;
    }

    public String getVilleAdrResidMoral() {
        return villeAdrResidMoral;
    }

    public void setPaysAdrResidMoral(String paysAdrResidMoral) {
        this.paysAdrResidMoral = paysAdrResidMoral;
    }

    public String getPaysAdrResidMoral() {
        return paysAdrResidMoral;
    }

    public void setCodePostalAdrResidMoral(String codePostalAdrResidMoral) {
        this.codePostalAdrResidMoral = codePostalAdrResidMoral;
    }

    public String getCodePostalAdrResidMoral() {
        return codePostalAdrResidMoral;
    }

    public void setLibPostalAdrResidMoral(String libPostalAdrResidMoral) {
        this.libPostalAdrResidMoral = libPostalAdrResidMoral;
    }

    public String getLibPostalAdrResidMoral() {
        return libPostalAdrResidMoral;
    }

    public void setParamInsertContrat(ParamInsertContrat paramInsertContrat) {
        this.ParamInsertContrat = paramInsertContrat;
    }

    public ParamInsertContrat getParamInsertContrat() {
        return ParamInsertContrat;
    }


    public void setTypePieceCotit(String typePieceCotit) {
        this.typePieceCotit = typePieceCotit;
    }

    public String getTypePieceCotit() {
        return typePieceCotit;
    }

    public void setNumPieceCotit(String numPieceCotit) {
        this.numPieceCotit = numPieceCotit;
    }

    public String getNumPieceCotit() {
        return numPieceCotit;
    }

    public void setCodeStructureCotit(String codeStructureCotit) {
        this.codeStructureCotit = codeStructureCotit;
    }

    public String getCodeStructureCotit() {
        return codeStructureCotit;
    }

    public void setCodePrdCotit(String codePrdCotit) {
        this.codePrdCotit = codePrdCotit;
    }

    public String getCodePrdCotit() {
        return codePrdCotit;
    }

    public void setNumCompteCotit(String numCompteCotit) {
        this.numCompteCotit = numCompteCotit;
    }

    public String getNumCompteCotit() {
        return numCompteCotit;
    }

    public void setTypeCotit(String typeCotit) {
        this.typeCotit = typeCotit;
    }

    public String getTypeCotit() {
        return typeCotit;
    }

    public void setTypeSignature(String typeSignature) {
        this.typeSignature = typeSignature;
    }

    public String getTypeSignature() {
        return typeSignature;
    }

    public void setListPersonneCotitGrid(Datagrid listPersonneCotitGrid) {
        this.listPersonneCotitGrid = listPersonneCotitGrid;
    }

    public Datagrid getListPersonneCotitGrid() {
        return listPersonneCotitGrid;
    }

    public void setTypPcePers(String typPcePers) {
        this.typPcePers = typPcePers;
    }

    public String getTypPcePers() {
        return typPcePers;
    }

    public void setListEntiteCotit(Collection listEntiteCotit) {
        this.listEntiteCotit = listEntiteCotit;
    }

    public Collection getListEntiteCotit() {
        return listEntiteCotit;
    }

    public void setAlertMembreCotit(String alertMembreCotit) {
        this.alertMembreCotit = alertMembreCotit;
    }

    public String getAlertMembreCotit() {
        return alertMembreCotit;
    }


    public void setAlertListMembresCotit(String alertListMembresCotit) {
        this.alertListMembresCotit = alertListMembresCotit;
    }

    public String getAlertListMembresCotit() {
        return alertListMembresCotit;
    }

    public void setListMembresEntiteCotit(Collection listMembresEntiteCotit) {
        this.listMembresEntiteCotit = listMembresEntiteCotit;
    }

    public Collection getListMembresEntiteCotit() {
        return listMembresEntiteCotit;
    }

    public void setEntiteChoisie(String entiteChoisie) {
        this.entiteChoisie = entiteChoisie;
    }

    public String getEntiteChoisie() {
        return entiteChoisie;
    }


    public void setCasAnnulation(String casAnnulation) {
        this.casAnnulation = casAnnulation;
    }

    public String getCasAnnulation() {
        return casAnnulation;
    }

    public void setCodStrcVert(String codStrcVert) {
        this.codStrcVert = codStrcVert;
    }

    public String getCodStrcVert() {
        return codStrcVert;
    }

    public void setCodPrdVert(String codPrdVert) {
        this.codPrdVert = codPrdVert;
    }

    public String getCodPrdVert() {
        return codPrdVert;
    }

    public void setCodStrcDav(String codStrcDav) {
        this.codStrcDav = codStrcDav;
    }

    public String getCodStrcDav() {
        return codStrcDav;
    }

    public void setNumCcptDav(String numCcptDav) {
        this.numCcptDav = numCcptDav;
    }

    public String getNumCcptDav() {
        return numCcptDav;
    }

    public void setCle(String cle) {
        this.cle = cle;
    }

    public String getCle() {
        return cle;
    }

    public void setTypPceDav(String typPceDav) {
        this.typPceDav = typPceDav;
    }

    public String getTypPceDav() {
        return typPceDav;
    }

    public void setNumPceDav(String numPceDav) {
        this.numPceDav = numPceDav;
    }

    public String getNumPceDav() {
        return numPceDav;
    }

    public void setNomNomDav(String nomNomDav) {
        this.nomNomDav = nomNomDav;
    }

    public String getNomNomDav() {
        return nomNomDav;
    }

    public void setNomPrnDav(String nomPrnDav) {
        this.nomPrnDav = nomPrnDav;
    }

    public String getNomPrnDav() {
        return nomPrnDav;
    }

    public void setMontSoldCcpt(String montSoldCcpt) {
        this.montSoldCcpt = montSoldCcpt;
    }

    public String getMontSoldCcpt() {
        return montSoldCcpt;
    }

    public void setLibDevDav(String libDevDav) {
        this.libDevDav = libDevDav;
    }

    public String getLibDevDav() {
        return libDevDav;
    }

    public void setCodeDeviseDav(String codeDeviseDav) {
        this.codeDeviseDav = codeDeviseDav;
    }

    public String getCodeDeviseDav() {
        return codeDeviseDav;
    }


    public void setSoldeMintDav(String soldeMintDav) {
        this.soldeMintDav = soldeMintDav;
    }

    public String getSoldeMintDav() {
        return soldeMintDav;
    }

    public void setOpenTabsheetCompteVert(String openTabsheetCompteVert) {
        this.openTabsheetCompteVert = openTabsheetCompteVert;
    }

    public String getOpenTabsheetCompteVert() {
        return openTabsheetCompteVert;
    }

    public void setCodPrdDav(String codPrdDav) {
        this.codPrdDav = codPrdDav;
    }

    public String getCodPrdDav() {
        return codPrdDav;
    }

    public void setAlertDav(String alertDav) {
        this.alertDav = alertDav;
    }

    public String getAlertDav() {
        return alertDav;
    }

    public void setCreerMandat(String creerMandat) {
        this.creerMandat = creerMandat;
    }

    public String getCreerMandat() {
        return creerMandat;
    }

    public void setContratDav(ContratCpt contratDav) {
        this.contratDav = contratDav;
    }

    public ContratCpt getContratDav() {
        return contratDav;
    }

    public void setCodStrcStrc(String codStrcStrc) {
        this.codStrcStrc = codStrcStrc;
    }

    public String getCodStrcStrc() {
        return codStrcStrc;
    }

    public void setCodGouvGouvRes(String codGouvGouvRes) {
        this.codGouvGouvRes = codGouvGouvRes;
    }

    public String getCodGouvGouvRes() {
        return codGouvGouvRes;
    }

    public void setLibGouvGouvRes(String libGouvGouvRes) {
        this.libGouvGouvRes = libGouvGouvRes;
    }

    public String getLibGouvGouvRes() {
        return libGouvGouvRes;
    }

    public void setCodGouvGouvProf(String codGouvGouvProf) {
        this.codGouvGouvProf = codGouvGouvProf;
    }

    public String getCodGouvGouvProf() {
        return codGouvGouvProf;
    }

    public void setLibGouvGouvProf(String libGouvGouvProf) {
        this.libGouvGouvProf = libGouvGouvProf;
    }

    public String getLibGouvGouvProf() {
        return libGouvGouvProf;
    }

    public void setCodGouvGouvResTuteur(String codGouvGouvResTuteur) {
        this.codGouvGouvResTuteur = codGouvGouvResTuteur;
    }

    public String getCodGouvGouvResTuteur() {
        return codGouvGouvResTuteur;
    }

    public void setLibGouvGouvResTuteur(String libGouvGouvResTuteur) {
        this.libGouvGouvResTuteur = libGouvGouvResTuteur;
    }

    public String getLibGouvGouvResTuteur() {
        return libGouvGouvResTuteur;
    }

    public void setCodGouvGouvProfTuteur(String codGouvGouvProfTuteur) {
        this.codGouvGouvProfTuteur = codGouvGouvProfTuteur;
    }

    public String getCodGouvGouvProfTuteur() {
        return codGouvGouvProfTuteur;
    }

    public void setLibGouvGouvProfTuteur(String libGouvGouvProfTuteur) {
        this.libGouvGouvProfTuteur = libGouvGouvProfTuteur;
    }

    public String getLibGouvGouvProfTuteur() {
        return libGouvGouvProfTuteur;
    }

    public void setCodGouvGouvCpt(String codGouvGouvCpt) {
        this.codGouvGouvCpt = codGouvGouvCpt;
    }

    public String getCodGouvGouvCpt() {
        return codGouvGouvCpt;
    }

    public void setLibGouvGouvCpt(String libGouvGouvCpt) {
        this.libGouvGouvCpt = libGouvGouvCpt;
    }

    public String getLibGouvGouvCpt() {
        return libGouvGouvCpt;
    }

    public void setCodGouvGouvMoral(String codGouvGouvMoral) {
        this.codGouvGouvMoral = codGouvGouvMoral;
    }

    public String getCodGouvGouvMoral() {
        return codGouvGouvMoral;
    }

    public void setLibGouvGouvMoral(String libGouvGouvMoral) {
        this.LibGouvGouvMoral = libGouvGouvMoral;
    }

    public String getLibGouvGouvMoral() {
        return LibGouvGouvMoral;
    }

    public void setTypeAdresse(String typeAdresse) {
        this.typeAdresse = typeAdresse;
    }

    public String getTypeAdresse() {
        return typeAdresse;
    }

    public void setTypePersonneMenu(String typePersonneMenu) {
        this.typePersonneMenu = typePersonneMenu;
    }

    public String getTypePersonneMenu() {
        return typePersonneMenu;
    }

    public void setLibelleConfirmation(String libelleConfirmation) {
        this.libelleConfirmation = libelleConfirmation;
    }

    public String getLibelleConfirmation() {
        return libelleConfirmation;
    }

    public void setEtatFormCreationPersonne(String etatFormCreationPersonne) {
        this.etatFormCreationPersonne = etatFormCreationPersonne;
    }

    public String getEtatFormCreationPersonne() {
        return etatFormCreationPersonne;
    }

    public void setReleveCpt(String releveCpt) {
        this.releveCpt = releveCpt;
    }

    public String getReleveCpt() {
        return releveCpt;
    }

    public void setPeridiciteCpt(String peridiciteCpt) {
        this.peridiciteCpt = peridiciteCpt;
    }

    public String getPeridiciteCpt() {
        return peridiciteCpt;
    }

    public void setFonctionementCpt(String fonctionementCpt) {
        this.fonctionementCpt = fonctionementCpt;
    }

    public String getFonctionementCpt() {
        return fonctionementCpt;
    }

    public void setNumTelPers(String numTelPers) {
        this.numTelPers = numTelPers;
    }

    public String getNumTelPers() {
        return numTelPers;
    }

    public void setNumFaxPers(String numFaxPers) {
        this.numFaxPers = numFaxPers;
    }

    public String getNumFaxPers() {
        return numFaxPers;
    }

    public void setNumTelTuteur(String numTelTuteur) {
        this.numTelTuteur = numTelTuteur;
    }

    public String getNumTelTuteur() {
        return numTelTuteur;
    }

    public void setNumFaxTuteur(String numFaxTuteur) {
        this.numFaxTuteur = numFaxTuteur;
    }

    public String getNumFaxTuteur() {
        return numFaxTuteur;
    }

    public void setNumMatriculeFisc(String numMatriculeFisc) {
        this.numMatriculeFisc = numMatriculeFisc;
    }

    public String getNumMatriculeFisc() {
        return numMatriculeFisc;
    }

    public void setCleMatriculeFisc(String cleMatriculeFisc) {
        this.cleMatriculeFisc = cleMatriculeFisc;
    }

    public String getCleMatriculeFisc() {
        return cleMatriculeFisc;
    }

    public void setCodeTvaFisc(String codeTvaFisc) {
        this.codeTvaFisc = codeTvaFisc;
    }

    public String getCodeTvaFisc() {
        return codeTvaFisc;
    }

    public void setCodeCategorieFisc(String codeCategorieFisc) {
        this.codeCategorieFisc = codeCategorieFisc;
    }

    public String getCodeCategorieFisc() {
        return codeCategorieFisc;
    }

    public void setNumEtabFisc(String numEtabFisc) {
        this.numEtabFisc = numEtabFisc;
    }

    public String getNumEtabFisc() {
        return numEtabFisc;
    }

    public void setNumFiscClt(String numFiscClt) {
        this.numFiscClt = numFiscClt;
    }

    public String getNumFiscClt() {
        return numFiscClt;
    }

    public void setCltTrouve(Client cltTrouve) {
        this.cltTrouve = cltTrouve;
    }

    public Client getCltTrouve() {
        return cltTrouve;
    }

    public void setChargEffectPramEp(String chargEffectPramEp) {
        this.chargEffectPramEp = chargEffectPramEp;
    }

    public String getChargEffectPramEp() {
        return chargEffectPramEp;
    }

    public void setNumMatriculeUser(String numMatriculeUser) {
        this.NumMatriculeUser = numMatriculeUser;
    }

    public String getNumMatriculeUser() {
        return NumMatriculeUser;
    }

    public void setNumRnePers(String numRnePers) {
        this.numRnePers = numRnePers;
    }

    public String getNumRnePers() {
        return numRnePers;
    }

    public void setMntBourse(String mntBourse) {
        this.mntBourse = mntBourse;
    }

    public String getMntBourse() {
        return mntBourse;
    }

    public void setPeriodVersm(String periodVersm) {
        this.periodVersm = periodVersm;
    }

    public String getPeriodVersm() {
        return periodVersm;
    }

    public void setTypeVers(String typeVers) {
        this.typeVers = typeVers;
    }

    public String getTypeVers() {
        return typeVers;
    }

    public void setAlertLivret(String alertLivret) {
        this.alertLivret = alertLivret;
    }

    public String getAlertLivret() {
        return alertLivret;
    }

    public void setAlertRcs(String alertRcs) {
        this.alertRcs = alertRcs;
    }

    public String getAlertRcs() {
        return alertRcs;
    }
    
  
    public void setMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
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
    
    public void initialiser519() {
           message = "";
           codePrdCpt="0519";
           numCompteCpt="";
          
       }

  
}
