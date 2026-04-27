package com.bna.smile.web.pilotage.forms;

import com.bna.commun.model.Client;
import com.bna.commun.model.ContratCpt;
import com.bna.commun.model.Personne;
import com.bna.smile.web.commun.view.InitialisationView;

import fr.improve.struts.taglib.layout.datagrid.Datagrid;

import java.sql.Blob;

import java.util.Collection;

import org.apache.struts.action.ActionForm;

public class PilotageForm extends ActionForm{
    public PilotageForm() {
    }
    private String typePieceId;
    private String codTypePieceId;
    private String numPieceId;
    private String numSeqPers;
    private String codStrcRech;
    private String numCcptRech;
    private String codPrdRech;
    private String nomId;
    private String prenomId;
    private String dateDebut;
    private String dateFin;
    private Collection listeContrats;
    private Collection listeContratsEparg;
    private Collection listeContratsEpargLie;
    private Collection listeContratCtx;
    private Collection listeContratsMandataire;
    private Collection listeEntiteCotit;
    private String choixRecherche;
    private String choix;
    private Personne personne;
    private Client client;
    private ContratCpt contratCpt;
    private String codeAgance;
    private String cleContratChoisi;


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
    private String numRnePers;
    private String dateNotification;
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


    //--------------------------------
    //: données globales /////////////////
    private String alert;
    private String reqCode;
    private String choixInitPage = "initPage";
    private String openTabsheetClient = "false";
    private String openTabsheetContrat = "false";
    private String openTabsheetProduit = "false";
    private String openTabsheetTuteur = "false";
    private String openTabsheetMorale = "false";
    private String openTabsheetCotitulaire = "false";
    private String openTabsheetCompteVert = "false";
    private String dateActuelle;
    private String transactionReussite;


    //popup Paramètre Epargne
    private String codeRegimeEpargne;
    private String codeCategorieEpargne;
    private String mntVersementEpargne;
    private String mntCapitaliseEpargne;
    private String numLivretEpargne;
    private String typeRequest;

    //popup Informations complementaires du client
    private String appelInformationComplementaire;

    private String nomNomoPers;
    private String nomPrnoPers;
    private String nomNomaPers;
    private String nomPrnaPers;
    private String libNat2Pays;
    private String libNiviNivi;
    private String libCsprCspr;
    private String numAffsPers;
    private String datAffspers;
    private String nbrEnfPers = "0";
    private String codRmatRmat;
    private String nomNommPers;
    private String nomPrnmPers;
    private String numTelPers;
    private String numFaxpers;
    private String adrMailPers;
    private String adrWebPers;
    private String adrTlxPers;
    private String adrSwiftPers;
    private String numDecePers;
    private String datDecePers;
    // champs hidden pour ce popup
    private String codNiviNivi;
    private String codNat2Pays;
    private String libRmatRmat;
    private String codCsprCspr;

    //popup Informations complementaires du Tuteur
    private String nomNomoPersTuteur;
    private String nomPrnoPersTuteur;
    private String nomNomaPersTuteur;
    private String nomPrnaPersTuteur;
    private String libNat2PaysTuteur;
    private String libNiviNiviTuteur;
    private String libCsprCsprTuteur;
    private String numAffsPersTuteur;
    private String datAffsPersTuteur;
    private String nbrEnfPersTuteur = "0";
    private String codRmatRmatTuteur;
    private String nomNommPersTuteur;
    private String nomPrnmPersTuteur;
    private String numTelPersTuteur;
    private String numFaxPersTuteur;
    private String adrMailPersTuteur;
    private String adrWebPersTuteur;
    private String adrTlxPersTuteur;
    private String adrSwiftPersTuteur;
    private String numDecePersTuteur;
    private String datDecePersTuteur;
    // champs hidden pour ce popup
    private String codNiviNiviTuteur;
    private String codNat2PaysTuteur;
    private String libRmatRmatTuteur;
    private String codCsprCsprTuteur;


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

    private String numTelMoral;
    private String numFaxMoral;
    private String adrMailMoral;
    private String adrWebMoral;
    private String adrSwiftMoral;
    private String adrTelexMoral;
    private String codGouvGouvMoral;
    private String LibGouvGouvMoral;

    // popup cotitulaire
    private String typeCotit;
    private Collection listeMembreEntiteCotit;
    private String typeSignature;
    private String typPcePers;

    //donnes globales
    private String casAnnulation = "";
    private String contratChoisi = "";
    private String planEpargne = "";

    //---------------------------------------------------------

    private Datagrid listPersonneCotitGrid;
    private Long nbrPlacement;
    private String mntPlacement;
    private Long nbrDemOpp;
    private Long nbrDemChq;
    private Long nbrDemCart;
    private Long nbrDepot;
    private Long nbrCtx;
    private Long nbrEparg;
    private Long nbrEpargLie;
    private Long nbrEng;
    private Long nbrInter;
    private String dateInerdiction;
    private String mntDepot;
    private String mntFacilte;
    private String mntCtx;
    private String mntEparg;
    private String mntEparglie;
    private String mntEng;
    private String Adresse;
    private String gouvertnorat;
    private String sigDepot;
    private String sigCtx;
    //---------------------------------------------------variables EDITION
    private String choixEdit;
    private String choixEtatCcpt="";
    private String dateDebRecherch="";
    private String dateFinRecherch="";
    private String nbrJours="";
    private String coddetail;
    private Collection listeDemandesCheques;
    private Collection listeDemandesCartes;
    private Collection listeContratPlacement;
    public void clearForm() {

        //tabshett 1 : identification /////////////////
         sigDepot="";
        sigCtx="";
        mntFacilte="0";
        mntEparglie="0";
        nbrEpargLie=Long.valueOf(0);
        mntCtx="";
        nbrCtx=Long.valueOf(0);
        personne = null;
        client = null;
        contratCpt = null;
        listeContratsMandataire = null;
        listeEntiteCotit = null;
        listeContrats = null;
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
        nomId = "";
        prenomId = "";
        codeAgance = "";

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

        immeubleCpt = "";
        rueCpt = "";
        citeCpt = "";
        villeCpt = "";
        paysCpt = "";
        codePostalCpt = "";
        libCodePostalCpt = "";
        numRnePers="";
        //-----------------------------------------------------------------
        //: données globales /////////////////
        alert = "";

        transactionReussite = "";
        //choixInitPage = "initPage";
        openTabsheetClient = "false";
        openTabsheetContrat = "false";
        openTabsheetProduit = "false";
        openTabsheetTuteur = "false";
        openTabsheetMorale = "false";
        openTabsheetCotitulaire = "false";
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
        codGouvGouvProf = "";
        libGouvGouvProf = "";

        // paramètres epargne
        codeRegimeEpargne = "";
        codeCategorieEpargne = "";
        mntVersementEpargne = "";
        mntCapitaliseEpargne = "";
        numLivretEpargne = "";
        typeRequest = "";


        //popup informations complémentaires client

        nomNomoPers = "";
        nomPrnoPers = "";
        nomNomaPers = "";
        nomPrnaPers = "";
        libNat2Pays = "";
        libNiviNivi = "";
        libCsprCspr = "";
        numAffsPers = "";
        datAffspers = "";
        nbrEnfPers = "0";
        codRmatRmat = "";
        nomNommPers = "";
        nomPrnmPers = "";
        numTelPers = "";
        numFaxpers = "";
        adrMailPers = "";
        adrWebPers = "";
        adrTlxPers = "";
        adrSwiftPers = "";
        numDecePers = "";
        datDecePers = "";
        codNiviNivi = "";
        codNat2Pays = "";
        libRmatRmat = "";
        codCsprCspr = "";

        //popup informations complémentaires tuteur
        nomNomoPersTuteur = "";
        nomPrnoPersTuteur = "";
        nomNomaPersTuteur = "";
        nomPrnaPersTuteur = "";
        libNat2PaysTuteur = "";
        libNiviNiviTuteur = "";
        libCsprCsprTuteur = "";
        numAffsPersTuteur = "";
        datAffsPersTuteur = "";
        nbrEnfPersTuteur = "0";
        codRmatRmatTuteur = "";
        nomNommPersTuteur = "";
        nomPrnmPersTuteur = "";
        numTelPersTuteur = "";
        numFaxPersTuteur = "";
        adrMailPersTuteur = "";
        adrWebPersTuteur = "";
        adrTlxPersTuteur = "";
        adrSwiftPersTuteur = "";
        numDecePersTuteur = "";
        datDecePersTuteur = "";
        // champs hidden pour ce popup
        codNiviNiviTuteur = "";
        codNat2PaysTuteur = "";
        libRmatRmatTuteur = "";
        codCsprCsprTuteur = "";
        /*
        NumDecrPersTuteur ="";
        DatDecrPersTuteur ="";
        GouvernoratTuteur ="";
        TribunalTuteur ="";
        DatPmePersTuteur ="";
        NumLpmePersTuteur ="";
        DatExpPersTuteur ="";
        NumJortPersTuteur ="";
        DatJortPersTuteur ="";
        DatCapPersTuteur ="";
        MontCapPersTuteur ="";
        CodSectPersTuteur ="";
        ParentTuteurTuteur ="";
        ConjointTuteur ="";
        NumFailPersTuteur ="";
        DatFailPersTuteur ="";
        NumBlocPersTuteur ="";
        DatBlocPersTuteur ="";
        NumRnePersTuteur ="";*/

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

        // tabSheet cotitulaire
        listeMembreEntiteCotit = null;

        //donnes globales
        casAnnulation = "";
        contratChoisi = "";
        planEpargne = "";
        
        nbrDemOpp=Long.valueOf(0);
        nbrDemChq=Long.valueOf(0);
        nbrDemCart=Long.valueOf(0);
        nbrDepot=Long.valueOf(0);
        nbrEparg=Long.valueOf(0);
        nbrEng=Long.valueOf(0);
        nbrInter=Long.valueOf(0);
        dateInerdiction= "";
        mntDepot= "";
        mntEparg= "";
        mntEng= "";
        Adresse= "";
        gouvertnorat= "";
        nbrPlacement=Long.valueOf(0);
            mntPlacement= "";
    }


    public void setTypePieceId(String typePieceId) {
        this.typePieceId = typePieceId;
    }

    public String getTypePieceId() {
        return typePieceId;
    }

    public void setCodTypePieceId(String codTypePieceId) {
        this.codTypePieceId = codTypePieceId;
    }

    public String getCodTypePieceId() {
        return codTypePieceId;
    }

    public void setNumPieceId(String numPieceId) {
        this.numPieceId = numPieceId;
    }

    public String getNumPieceId() {
        return numPieceId;
    }

    public void setNumSeqPers(String numSeqPers) {
        this.numSeqPers = numSeqPers;
    }

    public String getNumSeqPers() {
        return numSeqPers;
    }

    public void setCodStrcRech(String codStrcRech) {
        this.codStrcRech = codStrcRech;
    }

    public String getCodStrcRech() {
        return codStrcRech;
    }

    public void setNumCcptRech(String numCcptRech) {
        this.numCcptRech = numCcptRech;
    }

    public String getNumCcptRech() {
        return numCcptRech;
    }

    public void setCodPrdRech(String codPrdRech) {
        this.codPrdRech = codPrdRech;
    }

    public String getCodPrdRech() {
        return codPrdRech;
    }

    public void setNomId(String nomId) {
        this.nomId = nomId;
    }

    public String getNomId() {
        return nomId;
    }

    public void setPrenomId(String prenomId) {
        this.prenomId = prenomId;
    }

    public String getPrenomId() {
        return prenomId;
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

    public void setListeContrats(Collection listeContrats) {
        this.listeContrats = listeContrats;
    }

    public Collection getListeContrats() {
        return listeContrats;
    }

    public void setListeContratsMandataire(Collection listeContratsMandataire) {
        this.listeContratsMandataire = listeContratsMandataire;
    }

    public Collection getListeContratsMandataire() {
        return listeContratsMandataire;
    }

    public void setListeEntiteCotit(Collection listeEntiteCotit) {
        this.listeEntiteCotit = listeEntiteCotit;
    }

    public Collection getListeEntiteCotit() {
        return listeEntiteCotit;
    }

    public void setChoixRecherche(String choixRecherche) {
        this.choixRecherche = choixRecherche;
    }

    public String getChoixRecherche() {
        return choixRecherche;
    }

    public void setChoix(String choix) {
        this.choix = choix;
    }

    public String getChoix() {
        return choix;
    }

    public void setPersonne(Personne personne) {
        this.personne = personne;
    }

    public Personne getPersonne() {
        return personne;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public Client getClient() {
        return client;
    }

    public void setContratCpt(ContratCpt contratCpt) {
        this.contratCpt = contratCpt;
    }

    public ContratCpt getContratCpt() {
        return contratCpt;
    }

    public void setCodeAgance(String codeAgance) {
        this.codeAgance = codeAgance;
    }

    public String getCodeAgance() {
        return codeAgance;
    }

    public void setCleContratChoisi(String cleContratChoisi) {
        this.cleContratChoisi = cleContratChoisi;
    }

    public String getCleContratChoisi() {
        return cleContratChoisi;
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

    public void setCodLieuDelivClt(String codLieuDelivClt) {
        this.codLieuDelivClt = codLieuDelivClt;
    }

    public String getCodLieuDelivClt() {
        return codLieuDelivClt;
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

    public void setCodPaysNaisClt(String codPaysNaisClt) {
        this.codPaysNaisClt = codPaysNaisClt;
    }

    public String getCodPaysNaisClt() {
        return codPaysNaisClt;
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

    public void setActiviteClt(String activiteClt) {
        this.activiteClt = activiteClt;
    }

    public String getActiviteClt() {
        return activiteClt;
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

    public void setSignatureClt(Blob signatureClt) {
        this.signatureClt = signatureClt;
    }

    public Blob getSignatureClt() {
        return signatureClt;
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

    public void setCodPayAdrResid(String codPayAdrResid) {
        this.codPayAdrResid = codPayAdrResid;
    }

    public String getCodPayAdrResid() {
        return codPayAdrResid;
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

    public void setCodPayAdrProf(String codPayAdrProf) {
        this.codPayAdrProf = codPayAdrProf;
    }

    public String getCodPayAdrProf() {
        return codPayAdrProf;
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

    public void setLibDeviseCpt(String libDeviseCpt) {
        this.libDeviseCpt = libDeviseCpt;
    }

    public String getLibDeviseCpt() {
        return libDeviseCpt;
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

    public void setCodPayCpt(String codPayCpt) {
        this.codPayCpt = codPayCpt;
    }

    public String getCodPayCpt() {
        return codPayCpt;
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

    public void setNumRnePers(String numRnePers) {
        this.numRnePers = numRnePers;
    }

    public String getNumRnePers() {
        return numRnePers;
    }

    public void setDateNotification(String dateNotification) {
        this.dateNotification = dateNotification;
    }

    public String getDateNotification() {
        return dateNotification;
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

    public void setCodNationaliteTuteur(String codNationaliteTuteur) {
        this.codNationaliteTuteur = codNationaliteTuteur;
    }

    public String getCodNationaliteTuteur() {
        return codNationaliteTuteur;
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

    public void setAlert(String alert) {
        this.alert = alert;
    }

    public String getAlert() {
        return alert;
    }

    public void setReqCode(String reqCode) {
        this.reqCode = reqCode;
    }

    public String getReqCode() {
        return reqCode;
    }

    public void setChoixInitPage(String choixInitPage) {
        this.choixInitPage = choixInitPage;
    }

    public String getChoixInitPage() {
        return choixInitPage;
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

    public void setOpenTabsheetTuteur(String openTabsheetTuteur) {
        this.openTabsheetTuteur = openTabsheetTuteur;
    }

    public String getOpenTabsheetTuteur() {
        return openTabsheetTuteur;
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

    public void setOpenTabsheetCompteVert(String openTabsheetCompteVert) {
        this.openTabsheetCompteVert = openTabsheetCompteVert;
    }

    public String getOpenTabsheetCompteVert() {
        return openTabsheetCompteVert;
    }

    public void setDateActuelle(String dateActuelle) {
        this.dateActuelle = dateActuelle;
    }

    public String getDateActuelle() {
        return dateActuelle;
    }

    public void setTransactionReussite(String transactionReussite) {
        this.transactionReussite = transactionReussite;
    }

    public String getTransactionReussite() {
        return transactionReussite;
    }

    public void setCodeRegimeEpargne(String codeRegimeEpargne) {
        this.codeRegimeEpargne = codeRegimeEpargne;
    }

    public String getCodeRegimeEpargne() {
        return codeRegimeEpargne;
    }

    public void setCodeCategorieEpargne(String codeCategorieEpargne) {
        this.codeCategorieEpargne = codeCategorieEpargne;
    }

    public String getCodeCategorieEpargne() {
        return codeCategorieEpargne;
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

    public void setAppelInformationComplementaire(String appelInformationComplementaire) {
        this.appelInformationComplementaire = appelInformationComplementaire;
    }

    public String getAppelInformationComplementaire() {
        return appelInformationComplementaire;
    }

    public void setNomNomoPers(String nomNomoPers) {
        this.nomNomoPers = nomNomoPers;
    }

    public String getNomNomoPers() {
        return nomNomoPers;
    }

    public void setNomPrnoPers(String nomPrnoPers) {
        this.nomPrnoPers = nomPrnoPers;
    }

    public String getNomPrnoPers() {
        return nomPrnoPers;
    }

    public void setNomNomaPers(String nomNomaPers) {
        this.nomNomaPers = nomNomaPers;
    }

    public String getNomNomaPers() {
        return nomNomaPers;
    }

    public void setNomPrnaPers(String nomPrnaPers) {
        this.nomPrnaPers = nomPrnaPers;
    }

    public String getNomPrnaPers() {
        return nomPrnaPers;
    }

    public void setLibNat2Pays(String libNat2Pays) {
        this.libNat2Pays = libNat2Pays;
    }

    public String getLibNat2Pays() {
        return libNat2Pays;
    }

    public void setLibNiviNivi(String libNiviNivi) {
        this.libNiviNivi = libNiviNivi;
    }

    public String getLibNiviNivi() {
        return libNiviNivi;
    }

    public void setLibCsprCspr(String libCsprCspr) {
        this.libCsprCspr = libCsprCspr;
    }

    public String getLibCsprCspr() {
        return libCsprCspr;
    }

    public void setNumAffsPers(String numAffsPers) {
        this.numAffsPers = numAffsPers;
    }

    public String getNumAffsPers() {
        return numAffsPers;
    }

    public void setDatAffspers(String datAffspers) {
        this.datAffspers = datAffspers;
    }

    public String getDatAffspers() {
        return datAffspers;
    }

    public void setNbrEnfPers(String nbrEnfPers) {
        this.nbrEnfPers = nbrEnfPers;
    }

    public String getNbrEnfPers() {
        return nbrEnfPers;
    }

    public void setCodRmatRmat(String codRmatRmat) {
        this.codRmatRmat = codRmatRmat;
    }

    public String getCodRmatRmat() {
        return codRmatRmat;
    }

    public void setNomNommPers(String nomNommPers) {
        this.nomNommPers = nomNommPers;
    }

    public String getNomNommPers() {
        return nomNommPers;
    }

    public void setNomPrnmPers(String nomPrnmPers) {
        this.nomPrnmPers = nomPrnmPers;
    }

    public String getNomPrnmPers() {
        return nomPrnmPers;
    }

    public void setNumTelPers(String numTelPers) {
        this.numTelPers = numTelPers;
    }

    public String getNumTelPers() {
        return numTelPers;
    }

    public void setNumFaxpers(String numFaxpers) {
        this.numFaxpers = numFaxpers;
    }

    public String getNumFaxpers() {
        return numFaxpers;
    }

    public void setAdrMailPers(String adrMailPers) {
        this.adrMailPers = adrMailPers;
    }

    public String getAdrMailPers() {
        return adrMailPers;
    }

    public void setAdrWebPers(String adrWebPers) {
        this.adrWebPers = adrWebPers;
    }

    public String getAdrWebPers() {
        return adrWebPers;
    }

    public void setAdrTlxPers(String adrTlxPers) {
        this.adrTlxPers = adrTlxPers;
    }

    public String getAdrTlxPers() {
        return adrTlxPers;
    }

    public void setAdrSwiftPers(String adrSwiftPers) {
        this.adrSwiftPers = adrSwiftPers;
    }

    public String getAdrSwiftPers() {
        return adrSwiftPers;
    }

    public void setNumDecePers(String numDecePers) {
        this.numDecePers = numDecePers;
    }

    public String getNumDecePers() {
        return numDecePers;
    }

    public void setDatDecePers(String datDecePers) {
        this.datDecePers = datDecePers;
    }

    public String getDatDecePers() {
        return datDecePers;
    }

    public void setCodNiviNivi(String codNiviNivi) {
        this.codNiviNivi = codNiviNivi;
    }

    public String getCodNiviNivi() {
        return codNiviNivi;
    }

    public void setCodNat2Pays(String codNat2Pays) {
        this.codNat2Pays = codNat2Pays;
    }

    public String getCodNat2Pays() {
        return codNat2Pays;
    }

    public void setLibRmatRmat(String libRmatRmat) {
        this.libRmatRmat = libRmatRmat;
    }

    public String getLibRmatRmat() {
        return libRmatRmat;
    }

    public void setCodCsprCspr(String codCsprCspr) {
        this.codCsprCspr = codCsprCspr;
    }

    public String getCodCsprCspr() {
        return codCsprCspr;
    }

    public void setNomNomoPersTuteur(String nomNomoPersTuteur) {
        this.nomNomoPersTuteur = nomNomoPersTuteur;
    }

    public String getNomNomoPersTuteur() {
        return nomNomoPersTuteur;
    }

    public void setNomPrnoPersTuteur(String nomPrnoPersTuteur) {
        this.nomPrnoPersTuteur = nomPrnoPersTuteur;
    }

    public String getNomPrnoPersTuteur() {
        return nomPrnoPersTuteur;
    }

    public void setNomNomaPersTuteur(String nomNomaPersTuteur) {
        this.nomNomaPersTuteur = nomNomaPersTuteur;
    }

    public String getNomNomaPersTuteur() {
        return nomNomaPersTuteur;
    }

    public void setNomPrnaPersTuteur(String nomPrnaPersTuteur) {
        this.nomPrnaPersTuteur = nomPrnaPersTuteur;
    }

    public String getNomPrnaPersTuteur() {
        return nomPrnaPersTuteur;
    }

    public void setLibNat2PaysTuteur(String libNat2PaysTuteur) {
        this.libNat2PaysTuteur = libNat2PaysTuteur;
    }

    public String getLibNat2PaysTuteur() {
        return libNat2PaysTuteur;
    }

    public void setLibNiviNiviTuteur(String libNiviNiviTuteur) {
        this.libNiviNiviTuteur = libNiviNiviTuteur;
    }

    public String getLibNiviNiviTuteur() {
        return libNiviNiviTuteur;
    }

    public void setLibCsprCsprTuteur(String libCsprCsprTuteur) {
        this.libCsprCsprTuteur = libCsprCsprTuteur;
    }

    public String getLibCsprCsprTuteur() {
        return libCsprCsprTuteur;
    }

    public void setNumAffsPersTuteur(String numAffsPersTuteur) {
        this.numAffsPersTuteur = numAffsPersTuteur;
    }

    public String getNumAffsPersTuteur() {
        return numAffsPersTuteur;
    }

    public void setDatAffsPersTuteur(String datAffsPersTuteur) {
        this.datAffsPersTuteur = datAffsPersTuteur;
    }

    public String getDatAffsPersTuteur() {
        return datAffsPersTuteur;
    }

    public void setNbrEnfPersTuteur(String nbrEnfPersTuteur) {
        this.nbrEnfPersTuteur = nbrEnfPersTuteur;
    }

    public String getNbrEnfPersTuteur() {
        return nbrEnfPersTuteur;
    }

    public void setCodRmatRmatTuteur(String codRmatRmatTuteur) {
        this.codRmatRmatTuteur = codRmatRmatTuteur;
    }

    public String getCodRmatRmatTuteur() {
        return codRmatRmatTuteur;
    }

    public void setNomNommPersTuteur(String nomNommPersTuteur) {
        this.nomNommPersTuteur = nomNommPersTuteur;
    }

    public String getNomNommPersTuteur() {
        return nomNommPersTuteur;
    }

    public void setNomPrnmPersTuteur(String nomPrnmPersTuteur) {
        this.nomPrnmPersTuteur = nomPrnmPersTuteur;
    }

    public String getNomPrnmPersTuteur() {
        return nomPrnmPersTuteur;
    }

    public void setNumTelPersTuteur(String numTelPersTuteur) {
        this.numTelPersTuteur = numTelPersTuteur;
    }

    public String getNumTelPersTuteur() {
        return numTelPersTuteur;
    }

    public void setNumFaxPersTuteur(String numFaxPersTuteur) {
        this.numFaxPersTuteur = numFaxPersTuteur;
    }

    public String getNumFaxPersTuteur() {
        return numFaxPersTuteur;
    }

    public void setAdrMailPersTuteur(String adrMailPersTuteur) {
        this.adrMailPersTuteur = adrMailPersTuteur;
    }

    public String getAdrMailPersTuteur() {
        return adrMailPersTuteur;
    }

    public void setAdrWebPersTuteur(String adrWebPersTuteur) {
        this.adrWebPersTuteur = adrWebPersTuteur;
    }

    public String getAdrWebPersTuteur() {
        return adrWebPersTuteur;
    }

    public void setAdrTlxPersTuteur(String adrTlxPersTuteur) {
        this.adrTlxPersTuteur = adrTlxPersTuteur;
    }

    public String getAdrTlxPersTuteur() {
        return adrTlxPersTuteur;
    }

    public void setAdrSwiftPersTuteur(String adrSwiftPersTuteur) {
        this.adrSwiftPersTuteur = adrSwiftPersTuteur;
    }

    public String getAdrSwiftPersTuteur() {
        return adrSwiftPersTuteur;
    }

    public void setNumDecePersTuteur(String numDecePersTuteur) {
        this.numDecePersTuteur = numDecePersTuteur;
    }

    public String getNumDecePersTuteur() {
        return numDecePersTuteur;
    }

    public void setDatDecePersTuteur(String datDecePersTuteur) {
        this.datDecePersTuteur = datDecePersTuteur;
    }

    public String getDatDecePersTuteur() {
        return datDecePersTuteur;
    }

    public void setCodNiviNiviTuteur(String codNiviNiviTuteur) {
        this.codNiviNiviTuteur = codNiviNiviTuteur;
    }

    public String getCodNiviNiviTuteur() {
        return codNiviNiviTuteur;
    }

    public void setCodNat2PaysTuteur(String codNat2PaysTuteur) {
        this.codNat2PaysTuteur = codNat2PaysTuteur;
    }

    public String getCodNat2PaysTuteur() {
        return codNat2PaysTuteur;
    }

    public void setLibRmatRmatTuteur(String libRmatRmatTuteur) {
        this.libRmatRmatTuteur = libRmatRmatTuteur;
    }

    public String getLibRmatRmatTuteur() {
        return libRmatRmatTuteur;
    }

    public void setCodCsprCsprTuteur(String codCsprCsprTuteur) {
        this.codCsprCsprTuteur = codCsprCsprTuteur;
    }

    public String getCodCsprCsprTuteur() {
        return codCsprCsprTuteur;
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

    public void setCodPayAdrResidMoral(String codPayAdrResidMoral) {
        this.codPayAdrResidMoral = codPayAdrResidMoral;
    }

    public String getCodPayAdrResidMoral() {
        return codPayAdrResidMoral;
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

    public void setTypeCotit(String typeCotit) {
        this.typeCotit = typeCotit;
    }

    public String getTypeCotit() {
        return typeCotit;
    }

    public void setListeMembreEntiteCotit(Collection listeMembreEntiteCotit) {
        this.listeMembreEntiteCotit = listeMembreEntiteCotit;
    }

    public Collection getListeMembreEntiteCotit() {
        return listeMembreEntiteCotit;
    }

    public void setTypeSignature(String typeSignature) {
        this.typeSignature = typeSignature;
    }

    public String getTypeSignature() {
        return typeSignature;
    }

    public void setTypPcePers(String typPcePers) {
        this.typPcePers = typPcePers;
    }

    public String getTypPcePers() {
        return typPcePers;
    }

    public void setCasAnnulation(String casAnnulation) {
        this.casAnnulation = casAnnulation;
    }

    public String getCasAnnulation() {
        return casAnnulation;
    }

    public void setContratChoisi(String contratChoisi) {
        this.contratChoisi = contratChoisi;
    }

    public String getContratChoisi() {
        return contratChoisi;
    }

    public void setPlanEpargne(String planEpargne) {
        this.planEpargne = planEpargne;
    }

    public String getPlanEpargne() {
        return planEpargne;
    }

    public void setListPersonneCotitGrid(Datagrid listPersonneCotitGrid) {
        this.listPersonneCotitGrid = listPersonneCotitGrid;
    }

    public Datagrid getListPersonneCotitGrid() {
        return listPersonneCotitGrid;
    }

    public void setChoixEdit(String choixEdit) {
        this.choixEdit = choixEdit;
    }

    public String getChoixEdit() {
        return choixEdit;
    }

    public void setChoixEtatCcpt(String choixEtatCcpt) {
        this.choixEtatCcpt = choixEtatCcpt;
    }

    public String getChoixEtatCcpt() {
        return choixEtatCcpt;
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

    public void setNbrJours(String nbrJours) {
        this.nbrJours = nbrJours;
    }

    public String getNbrJours() {
        return nbrJours;
    }

   

    public void setNbrDemCart(Long nbrDemCart) {
        this.nbrDemCart = nbrDemCart;
    }

    public Long getNbrDemCart() {
        return nbrDemCart;
    }

   
    public void setNbrDemChq(Long nbrDemChq) {
        this.nbrDemChq = nbrDemChq;
    }

    public Long getNbrDemChq() {
        return nbrDemChq;
    }

    public void setNbrDemOpp(Long nbrDemOpp) {
        this.nbrDemOpp = nbrDemOpp;
    }

    public Long getNbrDemOpp() {
        return nbrDemOpp;
    }

    public void setNbrDepot(Long nbrDepot) {
        this.nbrDepot = nbrDepot;
    }

    public Long getNbrDepot() {
        return nbrDepot;
    }

    public void setNbrEparg(Long nbrEparg) {
        this.nbrEparg = nbrEparg;
    }

    public Long getNbrEparg() {
        return nbrEparg;
    }

    public void setNbrEng(Long nbrEng) {
        this.nbrEng = nbrEng;
    }

    public Long getNbrEng() {
        return nbrEng;
    }

    public void setMntDepot(String mntDepot) {
        this.mntDepot = mntDepot;
    }

    public String getMntDepot() {
        return mntDepot;
    }

    public void setMntEparg(String mntEparg) {
        this.mntEparg = mntEparg;
    }

    public String getMntEparg() {
        return mntEparg;
    }

    public void setMntEng(String mntEng) {
        this.mntEng = mntEng;
    }

    public String getMntEng() {
        return mntEng;
    }

    public void setAdresse(String adresse) {
        this.Adresse = adresse;
    }

    public String getAdresse() {
        return Adresse;
    }

    public void setGouvertnorat(String gouvertnorat) {
        this.gouvertnorat = gouvertnorat;
    }

    public String getGouvertnorat() {
        return gouvertnorat;
    }

    public void setNbrInter(Long nbrInter) {
        this.nbrInter = nbrInter;
    }

    public Long getNbrInter() {
        return nbrInter;
    }

   
    public void setDateInerdiction(String dateInerdiction) {
        this.dateInerdiction = dateInerdiction;
    }

    public String getDateInerdiction() {
        return dateInerdiction;
    }

    public void setNbrPlacement(Long nbrPlacement) {
        this.nbrPlacement = nbrPlacement;
    }

    public Long getNbrPlacement() {
        return nbrPlacement;
    }

    public void setMntPlacement(String mntPlacement) {
        this.mntPlacement = mntPlacement;
    }

    public String getMntPlacement() {
        return mntPlacement;
    }

    public void setCoddetail(String coddetail) {
        this.coddetail = coddetail;
    }

    public String getCoddetail() {
        return coddetail;
    }

    public void setListeDemandesCheques(Collection listeDemandesCheques) {
        this.listeDemandesCheques = listeDemandesCheques;
    }

    public Collection getListeDemandesCheques() {
        return listeDemandesCheques;
    }

    public void setListeDemandesCartes(Collection listeDemandesCartes) {
        this.listeDemandesCartes = listeDemandesCartes;
    }

    public Collection getListeDemandesCartes() {
        return listeDemandesCartes;
    }

    public void setListeContratPlacement(Collection listeContratPlacement) {
        this.listeContratPlacement = listeContratPlacement;
    }

    public Collection getListeContratPlacement() {
        return listeContratPlacement;
    }

    public void setListeContratCtx(Collection listeContratCtx) {
        this.listeContratCtx = listeContratCtx;
    }

    public Collection getListeContratCtx() {
        return listeContratCtx;
    }

    public void setNbrCtx(Long nbrCtx) {
        this.nbrCtx = nbrCtx;
    }

    public Long getNbrCtx() {
        return nbrCtx;
    }

    public void setMntCtx(String mntCtx) {
        this.mntCtx = mntCtx;
    }

    public String getMntCtx() {
        return mntCtx;
    }

    public void setMntFacilte(String mntFacilte) {
        this.mntFacilte = mntFacilte;
    }

    public String getMntFacilte() {
        return mntFacilte;
    }


    public void setSigDepot(String sigDepot) {
        this.sigDepot = sigDepot;
    }

    public String getSigDepot() {
        return sigDepot;
    }

    public void setSigCtx(String sigCtx) {
        this.sigCtx = sigCtx;
    }

    public String getSigCtx() {
        return sigCtx;
    }

    public void setNbrEpargLie(Long nbrEpargLie) {
        this.nbrEpargLie = nbrEpargLie;
    }

    public Long getNbrEpargLie() {
        return nbrEpargLie;
    }

    public void setMntEparglie(String mntEparglie) {
        this.mntEparglie = mntEparglie;
    }

    public String getMntEparglie() {
        return mntEparglie;
    }

    public void setListeContratsEparg(Collection listeContratsEparg) {
        this.listeContratsEparg = listeContratsEparg;
    }

    public Collection getListeContratsEparg() {
        return listeContratsEparg;
    }

    public void setListeContratsEpargLie(Collection listeContratsEpargLie) {
        this.listeContratsEpargLie = listeContratsEpargLie;
    }

    public Collection getListeContratsEpargLie() {
        return listeContratsEpargLie;
    }
}
