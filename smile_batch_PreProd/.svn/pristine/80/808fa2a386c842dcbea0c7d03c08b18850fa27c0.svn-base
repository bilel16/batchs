package com.bna.smile.web.commun.forms;

import com.bna.commun.model.Activite;
import com.bna.commun.model.Client;
import com.bna.commun.model.ContratCpt;
import com.bna.commun.model.Gouvernorat;
import com.bna.commun.model.Personne;
import com.bna.commun.model.Profession;
import com.bna.commun.model.RegimeMatrimonial;
import com.bna.commun.model.TypeModification;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

public class ModificationDonneesClientForm extends ActionForm {
    /**Reset all properties to their default values.
     * @param mapping The ActionMapping used to select this instance.
     * @param request The HTTP Request we are processing.
     */
    private

    //-------------------------------------------------------
    //---------- Identifiant du type de la modification -----
    //-------------------------------------------------------
    TypeModification typeModification;
    private String typePersonne;
    private String codeModification;
    private String libelleModification;
    private String libelleConfirmation;
    private String libelleConfirmation1;
    private String libelleConfirmation2;
    
    private String matriculeUser;
    private String dateActuelle;
    //--------------------------------------------------------
    //--------Identification de la personne-------------------
    //--------------------------------------------------------
    private String typePiece;
    private String numeroPiece;

    private Personne personne;
    private Client client;

    private List listeDesContrats = new ArrayList();
    private List listeDesContratsAmodifierAdresse = new ArrayList();
    private ContratCpt contratModifie;
    private String codePrdChoisi;
    private String codeStructureChoisi;
    private String numCompteChoisi;
    private String nomPersonne;
    private String prenomPersonne;
    private String raisonSocial;
    private String sigle;

    private String nomNomPers;
    private String nomPrnPers;
    private String libTitrPers;
    private String ancienLibTitrPers;
    private String testPersCommercante;
    private String nomRsPers;
    private String libSiglPers;
    private String codGrpGrp;
    private String NomRsGrp;
    //--------------------------------------------------------
    //--------Identification principal de la personne---------
    //--------------------------------------------------------
    
    private String codTpceTpce;
    private String codTpceTpceAnnexe;
    private String ancCodTpceTpce;

    private String numPcePers;
    private String ancNumPcePers;

    private String datDlvPers;
    private String codGouvGouv;
    private String libGouvGouv;

    private String codeRcs = "";
    private String codeTribunal = "";
    private String numeroRcs = "";
    private String anneeRcs = "";
    //--------------------------------------------------------
    //--------Identification secondaire de la personne---------
    //--------------------------------------------------------
    List listDesPiecesSecondaire = new ArrayList();
    private String sigleTypePieceAnnexe;
    private String codeTypePieceAnnexe;
    private String ancNumeroPieceAnnexe;
    private String numeroPieceAnnexe;
    private String dateDelivrancePieceAnnexe;
    private String dateFinValiditePieceAnnexe;
    private String lieuDelivrance;
    private String codGouvGouvPiece;
    private String piece = "non";
    //-------------------------------------------------------------
    //---------- Adresse resiedence et profession -----------------
    //-------------------------------------------------------------
    private String immeubleRes;
    private String rueRes;
    private String citeRes;
    private String villeRes;
    private String codCpCpRes;
    private String libCpCpRes;
    private String codGouvGouvRes;
    private String libGouvGouvRes;
    private String codPaysPaysRes;
    private String libPaysPaysRes;

    private String immeubleProf;
    private String rueProf;
    private String citeProf;
    private String villeProf;
    private String codCpCpProf;
    private String libCpCpProf;
    private String codGouvGouvProf;
    private String libGouvGouvProf;
    private String codPaysPaysProf;
    private String libPaysPaysProf;

    //-------------------------------------------------------------
    //---------- Adresse de correspondance      -------------------
    //-------------------------------------------------------------
    private String immeubleCorresp;
    private String rueCorresp;
    private String citeCorresp;
    private String villeCorresp;
    private String codCpCpCorresp;
    private String libCpCpCorresp;
    private String codGouvGouvCorresp;
    private String libGouvGouvCorresp;
    private String codPaysPaysCorresp;
    private String libPaysPaysCorresp;

    private String choixContrat;
    //-------------------------------------------------------------
    //---------- données de l'activite      -----------------------
    //-------------------------------------------------------------
    private String dateActMoral;
    private String codSectPers;
    private String libProfession;
    private String libActivite;
    private String libEmployeur;
    private String codEmpEmp;
    
    private String codProfProf;
    private String codGproGpro;
    private String codActAct;
    private String codCactCact;
    private String codSactSact;
    
    private String revRevPers;
    //-------------------------------------------------------------
    //---------- données de contact      --------------------------
    //-------------------------------------------------------------

    private String numTelPers;
    private String numFaxPers;
    private String adrMailPers;
    private String adrWebPers;
    private String adrSwiftPers;
    private String adrTlxPers;


    private String datNaisPers;
    private String age;
    
    private String codCatpCatp;
    private String nouvelleCodCatpCatp;
    private String messageCreationMandat;
    
    //-------------------------------------------------------------
    //---------- données de la qualité     --------------------------
    //-------------------------------------------------------------

    private String codeNationalite;
    private String libelleNationalite;
    private String boolResPers;
    private String codSegSeg;
    private String codSsegSseg;
    private String codCsegCseg;
    private String libSegSeg;

    //-------------------------------------------------------------
    //---------- données complémentaire  --------------------------
    //-------------------------------------------------------------
    private String codSitfPers;
    private String codeNationalite2;
    private String libelleNationalite2;
    private String codRmatRmat;
    private String libRmatRmat;
    private String codNiviNivi;
    private String libNiviNivi;
    private String codDoanClt;
    private String numFiscClt;

    //-------------------------------------------------------------
    //---------- données forme juridique --------------------------
    //-------------------------------------------------------------
    private String ancienneCodFjFj;
    private String ancienneCodCatpCatp;
    private String codFjFj;
    private String libelleFormeJuridique;

    private String libelleCategoriePersonne;
    private List listeCategoriePersonne = new ArrayList();
    private List listeFormeJuridique = new ArrayList();
    //-------------------------------------------------------------
    //---------- données des matricules -------------------------
    //-------------------------------------------------------------
    private String numBctClt;
    private String numRnePers;
    private String dateCreationMoral;
    private String numLoiCreMoral;
    private String numJortMoral;
    private String datJortMoral;
    private String numDecretMoral;
    private String datDecretMoral;
    private String dateDelivMoral;

    private String datCapPers;
    private String montCapPers;

    //------------------------------------------------------------
    //------- Matricule Fiscale  ---------------------------------
    //------------------------------------------------------------
    private String numMatriculeFisc = "";
    private String cleMatriculeFisc = "";
    private String codeTvaFisc = "";
    private String codeCategorieFisc = "";
    private String numEtabFisc = "";
    private String chaineMatriculeFisc = "";

    private String nomNomoPers;
    private String nomPrnoPers;
    private String nomNomaPers;
    private String nomPrnaPers;
    private String nomNommPers;
    private String nomPrnmPers;

    private String nbrEnfPers;
    private String libCsprCspr;
    private String codCsprCspr;
    private String numAffsPers;
    private String datAffspers;
    //------------------------------------------------------------
    //------- Liste des contrat touché par la modification-------
    //------------------------------------------------------------
    private List listDesContratAmodifier = new ArrayList();

    //------------------------------------------------------------
    //------- Données de Test ------------------------------------
    //------------------------------------------------------------
    private String reqCode;
    private String testExistPersonne    = "N";
    private String testIsClientAgence   = "N";
    private String testTypePersonne     = "O";
    private String message = "";
    //------------------------------------------------------------
    //------- Données de Test modifcation -------------------------
    //------------------------------------------------------------
    private String caseIdentifiantPrincipal;
    private String caseNom;
    private String caseAdreRes;
    private String caseAdreProf;
    private String caseAdreCorresp;
    private String caseActivite;
    private String caseContact;
    private String casePieceAnnexe;
    private String testIdentifiantPrincipal = "NonModif";
    private String testAdresseProf = "NonModif";
    private String testAdresseRes = "NonModif";
    private String testAdresseCorresp = "NonModif";
    private String testNom = "NonModif";
    private String testActivite = "NonModif";
    private String testContact = "NonModif";
    private String testPieceAnnexe = "NonModif";

    
    public void reset(ActionMapping mapping, HttpServletRequest request) {
        super.reset(mapping, request);
    }

    public void clearForm() {
        typePersonne = "";
        typePiece = "";
        numeroPiece = "";
        typePersonne = "";
        personne = null;
        client = null;
        listeDesContrats = new ArrayList();
        listeDesContratsAmodifierAdresse = new ArrayList();
        contratModifie = null;
        codePrdChoisi = "";
        codeStructureChoisi = "";
        numCompteChoisi = "";
        nomPersonne = "";
        prenomPersonne = "";
        raisonSocial = "";
        sigle = "";
        testExistPersonne = "N";

        //-------------------------------------------------------- 
        //------------- identifiant principal ----------------------
        codTpceTpce = "";
        ancNumPcePers = "";
        numPcePers = "";
        datDlvPers = "";
        codGouvGouv = "";
        libGouvGouv = "";

        codeRcs = "";
        codeTribunal = "";
        numeroRcs = "";
        anneeRcs = "";
        //---------------------------------------
        nomNomPers = "";
        nomPrnPers = "";
        libTitrPers = "";
        ancienLibTitrPers = "";
        nomRsPers = "";
        libSiglPers = "";
        testPersCommercante = "";

        //--------------------------------------------------------
        //--------Identification secondaire de la personne---------
        //--------------------------------------------------------
        listDesPiecesSecondaire = new ArrayList();
        sigleTypePieceAnnexe = "";
        codeTypePieceAnnexe = "";
        ancNumeroPieceAnnexe = "";
        numeroPieceAnnexe = "";
        dateDelivrancePieceAnnexe = "";
        dateFinValiditePieceAnnexe = "";

        lieuDelivrance = "";
        codGouvGouvPiece = "";
        //piece = "non";
        //-------------------------------------------------------------
        //---------- Adresse resiedence et profession -----------------
        //-------------------------------------------------------------
        immeubleRes = "";
        rueRes = "";
        citeRes = "";
        villeRes = "";
        codCpCpRes = "";
        libCpCpRes = "";
        codGouvGouvRes = "";
        libGouvGouvRes = "";
        codPaysPaysRes = "";
        libPaysPaysRes = "";

        immeubleProf = "";
        rueProf = "";
        citeProf = "";
        villeProf = "";
        codCpCpProf = "";
        libCpCpProf = "";
        codGouvGouvProf = "";
        libGouvGouvProf = "";
        codPaysPaysProf = "";
        libPaysPaysProf = "";

        datNaisPers = "";
        age = "";
        codCatpCatp = "";
        nouvelleCodCatpCatp ="";
        libelleCategoriePersonne ="";
        messageCreationMandat =null;
        //-------------------------------------------------------------
        //---------- Adresse de correspondance      -----------------
        //-------------------------------------------------------------
        immeubleCorresp = "";
        rueCorresp = "";
        citeCorresp = "";
        villeCorresp = "";
        codCpCpCorresp = "";
        libCpCpCorresp = "";
        codGouvGouvCorresp = "";
        libGouvGouvCorresp = "";
        codPaysPaysCorresp = "";
        libPaysPaysCorresp = "";
        choixContrat = "";
        //-------------------------------------------------------------
        //---------- données de l'activite      ------------------------
        //-------------------------------------------------------------
        codSectPers = "";
        libProfession = "";
        libActivite = "";
        libEmployeur ="";
        codEmpEmp ="";
        codProfProf = "";
        codGproGpro = "";
        codActAct = "";
        codCactCact = "";
        codSactSact = "";
        revRevPers ="";
        //-------------------------------------------------------------
        //---------- données de la qualité     --------------------------
        //-------------------------------------------------------------

        codeNationalite = "";
        libelleNationalite = "";
        boolResPers = "";
        codSegSeg = "";
        codSsegSseg = "";
        codCsegCseg = "";
        libSegSeg = "";
        codGrpGrp = "";
        NomRsGrp = "";
        //-------------------------------------------------------------
        //---------- données complémentaire  --------------------------
        //-------------------------------------------------------------
        codSitfPers = "";
        codeNationalite2 = "";
        libelleNationalite2 = "";
        codRmatRmat = "";
        libRmatRmat = "";
        codNiviNivi = "";
        libNiviNivi = "";
        codDoanClt = "";
        numFiscClt = "";

        //-------------------------------------------------------------
        //---------- données de Contacts   ----------------------------
        //-------------------------------------------------------------
        numTelPers = "";
        numFaxPers = "";
        adrMailPers = "";
        adrWebPers = "";
        adrSwiftPers = "";
        adrTlxPers = "";

        //-------------------------------------------------------------
        //---------- données forme juridique --------------------------
        //-------------------------------------------------------------

        codFjFj = "";
        ancienneCodFjFj = "";
        ancienneCodCatpCatp = "";

        listeCategoriePersonne = new ArrayList();
        listeFormeJuridique = new ArrayList();

        //-------------------------------------------------------------
        //---------- données des matricules -------------------------
        //-------------------------------------------------------------
        dateDelivMoral = "";
        dateActMoral = "";
        numBctClt = "";
        numRnePers = "";
        dateCreationMoral = "";
        numLoiCreMoral = "";
        numJortMoral = "";
        datJortMoral = "";
        numDecretMoral = "";
        datDecretMoral = "";
        datCapPers = "";
        montCapPers = "";

        //------------------------------------------------------------
        //------- Matricule Fiscale  ---------------------------------
        //------------------------------------------------------------
        numMatriculeFisc = "";
        cleMatriculeFisc = "";
        codeTvaFisc = "";
        codeCategorieFisc = "";
        numEtabFisc = "";
        chaineMatriculeFisc = "";
        //------------------------------------------------------------
        //------- nominations complementaire  ---------------------------------
        //------------------------------------------------------------
        nomNomoPers = "";
        nomPrnoPers = "";
        nomNomaPers = "";
        nomPrnaPers = "";
        nomNommPers = "";
        nomPrnmPers = "";

        nbrEnfPers = "0";
        libCsprCspr = "";
        numAffsPers = "";
        datAffspers = "";
        codCsprCspr = "";
        //------------------------------------------------------------
        //------- Données de Test ------------------------------------
        //------------------------------------------------------------
        testTypePersonne  = "O";
        testExistPersonne = "N";
        testIsClientAgence   = "N";
        message = "";
        //------------------------------------------------------------
        //------- Liste des contrat touché par la modification-------
        //------------------------------------------------------------
        listDesContratAmodifier = new ArrayList();
        //------------------------------------------------------------
        //------- Données de Test changement -------------------------
        //------------------------------------------------------------
        caseIdentifiantPrincipal = "";
        caseNom = "";
        caseAdreRes = "";
        caseAdreProf = "";
        caseAdreCorresp = "";
        caseActivite = "";
        caseContact = "";
        casePieceAnnexe = "";
        testIdentifiantPrincipal = "NonModif";
        testNom = "NonModif";
        testAdresseProf = "NonModif";
        testAdresseRes = "NonModif";
        testAdresseCorresp = "NonModif";
        testActivite = "NonModif";
        testContact = "NonModif";
        testPieceAnnexe = "NonModif";
        
        libelleConfirmation ="";
        libelleConfirmation1="";
        libelleConfirmation2 ="";
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


    public void setCodeModification(String codeModification) {
        this.codeModification = codeModification;
    }

    public String getCodeModification() {
        return codeModification;
    }

    public void setLibelleModification(String libelleModification) {
        this.libelleModification = libelleModification;
    }

    public String getLibelleModification() {
        return libelleModification;
    }

    public void setTypePiece(String typePiece) {
        this.typePiece = typePiece;
    }

    public String getTypePiece() {
        return typePiece;
    }

    public void setNumeroPiece(String numeroPiece) {
        this.numeroPiece = numeroPiece;
    }

    public String getNumeroPiece() {
        return numeroPiece;
    }

    public void setPersonne(Personne personne) {
        this.personne = personne;
    }

    public Personne getPersonne() {
        return personne;
    }

    public void setNomPersonne(String nomPersonne) {
        this.nomPersonne = nomPersonne;
    }

    public String getNomPersonne() {
        return nomPersonne;
    }

    public void setPrenomPersonne(String prenomPersonne) {
        this.prenomPersonne = prenomPersonne;
    }

    public String getPrenomPersonne() {
        return prenomPersonne;
    }

    public void setReqCode(String reqCode) {
        this.reqCode = reqCode;
    }

    public String getReqCode() {
        return reqCode;
    }

    public void setTestExistPersonne(String testExistPersonne) {
        this.testExistPersonne = testExistPersonne;
    }

    public String getTestExistPersonne() {
        return testExistPersonne;
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

    public void setLibTitrPers(String libTitrPers) {
        this.libTitrPers = libTitrPers;
    }

    public String getLibTitrPers() {
        return libTitrPers;
    }

    public void setNomRsPers(String nomRsPers) {
        this.nomRsPers = nomRsPers;
    }

    public String getNomRsPers() {
        return nomRsPers;
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

    public void setImmeubleRes(String immeubleRes) {
        this.immeubleRes = immeubleRes;
    }

    public String getImmeubleRes() {
        return immeubleRes;
    }

    public void setRueRes(String rueRes) {
        this.rueRes = rueRes;
    }

    public String getRueRes() {
        return rueRes;
    }

    public void setCiteRes(String citeRes) {
        this.citeRes = citeRes;
    }

    public String getCiteRes() {
        return citeRes;
    }

    public void setVilleRes(String villeRes) {
        this.villeRes = villeRes;
    }

    public String getVilleRes() {
        return villeRes;
    }

    public void setCodCpCpRes(String codCpCpRes) {
        this.codCpCpRes = codCpCpRes;
    }

    public String getCodCpCpRes() {
        return codCpCpRes;
    }

    public void setCodPaysPaysRes(String codPaysPaysRes) {
        this.codPaysPaysRes = codPaysPaysRes;
    }

    public String getCodPaysPaysRes() {
        return codPaysPaysRes;
    }

    public void setImmeubleProf(String immeubleProf) {
        this.immeubleProf = immeubleProf;
    }

    public String getImmeubleProf() {
        return immeubleProf;
    }

    public void setRueProf(String rueProf) {
        this.rueProf = rueProf;
    }

    public String getRueProf() {
        return rueProf;
    }

    public void setCiteProf(String citeProf) {
        this.citeProf = citeProf;
    }

    public String getCiteProf() {
        return citeProf;
    }

    public void setVilleProf(String villeProf) {
        this.villeProf = villeProf;
    }

    public String getVilleProf() {
        return villeProf;
    }

    public void setCodCpCpProf(String codCpCpProf) {
        this.codCpCpProf = codCpCpProf;
    }

    public String getCodCpCpProf() {
        return codCpCpProf;
    }

    public void setCodPaysPaysProf(String codPaysPaysProf) {
        this.codPaysPaysProf = codPaysPaysProf;
    }

    public String getCodPaysPaysProf() {
        return codPaysPaysProf;
    }

    public void setLibCpCpRes(String libCpCpRes) {
        this.libCpCpRes = libCpCpRes;
    }

    public String getLibCpCpRes() {
        return libCpCpRes;
    }

    public void setLibPaysPaysRes(String libPaysPaysRes) {
        this.libPaysPaysRes = libPaysPaysRes;
    }

    public String getLibPaysPaysRes() {
        return libPaysPaysRes;
    }

    public void setLibCpCpProf(String libCpCpProf) {
        this.libCpCpProf = libCpCpProf;
    }

    public String getLibCpCpProf() {
        return libCpCpProf;
    }

    public void setLibPaysPaysProf(String libPaysPaysProf) {
        this.libPaysPaysProf = libPaysPaysProf;
    }

    public String getLibPaysPaysProf() {
        return libPaysPaysProf;
    }

    public void setTestAdresseProf(String testAdresseProf) {
        this.testAdresseProf = testAdresseProf;
    }

    public String getTestAdresseProf() {
        return testAdresseProf;
    }

    public void setTestAdresseRes(String testAdresseRes) {
        this.testAdresseRes = testAdresseRes;
    }

    public String getTestAdresseRes() {
        return testAdresseRes;
    }

    public void setCaseAdreRes(String caseAdreRes) {
        this.caseAdreRes = caseAdreRes;
    }

    public String getCaseAdreRes() {
        return caseAdreRes;
    }

    public void setCaseAdreProf(String caseAdreProf) {
        this.caseAdreProf = caseAdreProf;
    }

    public String getCaseAdreProf() {
        return caseAdreProf;
    }

    public void setTypeModification(TypeModification typeModification) {
        this.typeModification = typeModification;
    }

    public TypeModification getTypeModification() {
        return typeModification;
    }

    public void setMatriculeUser(String matriculeUser) {
        this.matriculeUser = matriculeUser;
    }

    public String getMatriculeUser() {
        return matriculeUser;
    }

    public void setAncienLibTitrPers(String ancienLibTitrPers) {
        this.ancienLibTitrPers = ancienLibTitrPers;
    }

    public String getAncienLibTitrPers() {
        return ancienLibTitrPers;
    }

    public void setCaseNom(String caseNom) {
        this.caseNom = caseNom;
    }

    public String getCaseNom() {
        return caseNom;
    }

    public void setTestNom(String testNom) {
        this.testNom = testNom;
    }

    public String getTestNom() {
        return testNom;
    }

    public void setListeDesContrats(List listeDesContrats) {
        this.listeDesContrats = listeDesContrats;
    }

    public List getListeDesContrats() {
        return listeDesContrats;
    }

    public void setCodePrdChoisi(String codePrdChoisi) {
        this.codePrdChoisi = codePrdChoisi;
    }

    public String getCodePrdChoisi() {
        return codePrdChoisi;
    }


    public void setNumCompteChoisi(String numCompteChoisi) {
        this.numCompteChoisi = numCompteChoisi;
    }

    public String getNumCompteChoisi() {
        return numCompteChoisi;
    }

    public void setCodeStructureChoisi(String codeStructureChoisi) {
        this.codeStructureChoisi = codeStructureChoisi;
    }

    public String getCodeStructureChoisi() {
        return codeStructureChoisi;
    }

    public void setImmeubleCorresp(String immeubleCorresp) {
        this.immeubleCorresp = immeubleCorresp;
    }

    public String getImmeubleCorresp() {
        return immeubleCorresp;
    }

    public void setRueCorresp(String rueCorresp) {
        this.rueCorresp = rueCorresp;
    }

    public String getRueCorresp() {
        return rueCorresp;
    }

    public void setCiteCorresp(String citeCorresp) {
        this.citeCorresp = citeCorresp;
    }

    public String getCiteCorresp() {
        return citeCorresp;
    }

    public void setVilleCorresp(String villeCorresp) {
        this.villeCorresp = villeCorresp;
    }

    public String getVilleCorresp() {
        return villeCorresp;
    }

    public void setCodCpCpCorresp(String codCpCpCorresp) {
        this.codCpCpCorresp = codCpCpCorresp;
    }

    public String getCodCpCpCorresp() {
        return codCpCpCorresp;
    }

    public void setLibCpCpCorresp(String libCpCpCorresp) {
        this.libCpCpCorresp = libCpCpCorresp;
    }

    public String getLibCpCpCorresp() {
        return libCpCpCorresp;
    }

    public void setCodPaysPaysCorresp(String codPaysPaysCorresp) {
        this.codPaysPaysCorresp = codPaysPaysCorresp;
    }

    public String getCodPaysPaysCorresp() {
        return codPaysPaysCorresp;
    }

    public void setLibPaysPaysCorresp(String libPaysPaysCorresp) {
        this.libPaysPaysCorresp = libPaysPaysCorresp;
    }

    public String getLibPaysPaysCorresp() {
        return libPaysPaysCorresp;
    }

    public void setCaseAdreCorresp(String caseAdreCorresp) {
        this.caseAdreCorresp = caseAdreCorresp;
    }

    public String getCaseAdreCorresp() {
        return caseAdreCorresp;
    }

    public void setTestAdresseCorresp(String testAdresseCorresp) {
        this.testAdresseCorresp = testAdresseCorresp;
    }

    public String getTestAdresseCorresp() {
        return testAdresseCorresp;
    }

    public void setContratModifie(ContratCpt contratModifie) {
        this.contratModifie = contratModifie;
    }

    public ContratCpt getContratModifie() {
        return contratModifie;
    }

    public void setCodSectPers(String codSectPers) {
        this.codSectPers = codSectPers;
    }

    public String getCodSectPers() {
        return codSectPers;
    }

    public void setLibProfession(String libProfession) {
        this.libProfession = libProfession;
    }

    public String getLibProfession() {
        return libProfession;
    }

    public void setLibActivite(String libActivite) {
        this.libActivite = libActivite;
    }

    public String getLibActivite() {
        return libActivite;
    }

    public void setCodProfProf(String codProfProf) {
        this.codProfProf = codProfProf;
    }

    public String getCodProfProf() {
        return codProfProf;
    }

    public void setCodGproGpro(String codGproGpro) {
        this.codGproGpro = codGproGpro;
    }

    public String getCodGproGpro() {
        return codGproGpro;
    }

    public void setCodActAct(String codActAct) {
        this.codActAct = codActAct;
    }

    public String getCodActAct() {
        return codActAct;
    }

    public void setCodCactCact(String codCactCact) {
        this.codCactCact = codCactCact;
    }

    public String getCodCactCact() {
        return codCactCact;
    }

    public void setCodSactSact(String codSactSact) {
        this.codSactSact = codSactSact;
    }

    public String getCodSactSact() {
        return codSactSact;
    }

    public void setCaseActivite(String caseActivite) {
        this.caseActivite = caseActivite;
    }

    public String getCaseActivite() {
        return caseActivite;
    }

    public void setTestActivite(String testActivite) {
        this.testActivite = testActivite;
    }

    public String getTestActivite() {
        return testActivite;
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

    public void setAdrSwiftPers(String adrSwiftPers) {
        this.adrSwiftPers = adrSwiftPers;
    }

    public String getAdrSwiftPers() {
        return adrSwiftPers;
    }

    public void setAdrTlxPers(String adrTlxPers) {
        this.adrTlxPers = adrTlxPers;
    }

    public String getAdrTlxPers() {
        return adrTlxPers;
    }

    public void setTestContact(String testContact) {
        this.testContact = testContact;
    }

    public String getTestContact() {
        return testContact;
    }

    public void setCaseContact(String caseContact) {
        this.caseContact = caseContact;
    }

    public String getCaseContact() {
        return caseContact;
    }

    public void setCaseIdentifiantPrincipal(String caseIdentifiantPrincipal) {
        this.caseIdentifiantPrincipal = caseIdentifiantPrincipal;
    }

    public String getCaseIdentifiantPrincipal() {
        return caseIdentifiantPrincipal;
    }

    public void setTestIdentifiantPrincipal(String testIdentifiantPrincipal) {
        this.testIdentifiantPrincipal = testIdentifiantPrincipal;
    }

    public String getTestIdentifiantPrincipal() {
        return testIdentifiantPrincipal;
    }

    public void setListDesPiecesSecondaire(List listDesPiecesSecondaire) {
        this.listDesPiecesSecondaire = listDesPiecesSecondaire;
    }

    public List getListDesPiecesSecondaire() {
        return listDesPiecesSecondaire;
    }

    public void setNumeroPieceAnnexe(String numeroPieceAnnexe) {
        this.numeroPieceAnnexe = numeroPieceAnnexe;
    }

    public String getNumeroPieceAnnexe() {
        return numeroPieceAnnexe;
    }

    public void setDateDelivrancePieceAnnexe(String dateDelivrancePieceAnnexe) {
        this.dateDelivrancePieceAnnexe = dateDelivrancePieceAnnexe;
    }

    public String getDateDelivrancePieceAnnexe() {
        return dateDelivrancePieceAnnexe;
    }

    public void setCodeTypePieceAnnexe(String codeTypePieceAnnexe) {
        this.codeTypePieceAnnexe = codeTypePieceAnnexe;
    }

    public String getCodeTypePieceAnnexe() {
        return codeTypePieceAnnexe;
    }

    public void setAncNumeroPieceAnnexe(String ancNumeroPieceAnnexe) {
        this.ancNumeroPieceAnnexe = ancNumeroPieceAnnexe;
    }

    public String getAncNumeroPieceAnnexe() {
        return ancNumeroPieceAnnexe;
    }

    public void setDateFinValiditePieceAnnexe(String dateFinValiditePieceAnnexe) {
        this.dateFinValiditePieceAnnexe = dateFinValiditePieceAnnexe;
    }

    public String getDateFinValiditePieceAnnexe() {
        return dateFinValiditePieceAnnexe;
    }

    public void setTestPieceAnnexe(String testPieceAnnexe) {
        this.testPieceAnnexe = testPieceAnnexe;
    }

    public String getTestPieceAnnexe() {
        return testPieceAnnexe;
    }

    public void setCasePieceAnnexe(String casePieceAnnexe) {
        this.casePieceAnnexe = casePieceAnnexe;
    }

    public String getCasePieceAnnexe() {
        return casePieceAnnexe;
    }

    public void setSigleTypePieceAnnexe(String sigleTypePieceAnnexe) {
        this.sigleTypePieceAnnexe = sigleTypePieceAnnexe;
    }

    public String getSigleTypePieceAnnexe() {
        return sigleTypePieceAnnexe;
    }

    public void setDateActuelle(String dateActuelle) {
        this.dateActuelle = dateActuelle;
    }

    public String getDateActuelle() {
        return dateActuelle;
    }

    public void setDatNaisPers(String datNaisPers) {
        this.datNaisPers = datNaisPers;
    }

    public String getDatNaisPers() {
        return datNaisPers;
    }

    public void setCodCatpCatp(String codCatpCatp) {
        this.codCatpCatp = codCatpCatp;
    }

    public String getCodCatpCatp() {
        return codCatpCatp;
    }

    public void setLibelleConfirmation(String libelleConfirmation) {
        this.libelleConfirmation = libelleConfirmation;
    }

    public String getLibelleConfirmation() {
        return libelleConfirmation;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
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

    public void setCodGouvGouvCorresp(String codGouvGouvCorresp) {
        this.codGouvGouvCorresp = codGouvGouvCorresp;
    }

    public String getCodGouvGouvCorresp() {
        return codGouvGouvCorresp;
    }

    public void setLibGouvGouvCorresp(String libGouvGouvCorresp) {
        this.libGouvGouvCorresp = libGouvGouvCorresp;
    }

    public String getLibGouvGouvCorresp() {
        return libGouvGouvCorresp;
    }

    public void setDatDlvPers(String datDlvPers) {
        this.datDlvPers = datDlvPers;
    }

    public String getDatDlvPers() {
        return datDlvPers;
    }

    public void setCodGouvGouv(String codGouvGouv) {
        this.codGouvGouv = codGouvGouv;
    }

    public String getCodGouvGouv() {
        return codGouvGouv;
    }

    public void setLibGouvGouv(String libGouvGouv) {
        this.libGouvGouv = libGouvGouv;
    }

    public String getLibGouvGouv() {
        return libGouvGouv;
    }


    public void setAncNumPcePers(String ancNumPcePers) {
        this.ancNumPcePers = ancNumPcePers;
    }

    public String getAncNumPcePers() {
        return ancNumPcePers;
    }

    public void setAncCodTpceTpce(String ancCodTpceTpce) {
        this.ancCodTpceTpce = ancCodTpceTpce;
    }

    public String getAncCodTpceTpce() {
        return ancCodTpceTpce;
    }

    public void setTestPersCommercante(String testPersCommercante) {
        this.testPersCommercante = testPersCommercante;
    }

    public String getTestPersCommercante() {
        return testPersCommercante;
    }

    public void setCodeNationalite(String codeNationalite) {
        this.codeNationalite = codeNationalite;
    }

    public String getCodeNationalite() {
        return codeNationalite;
    }

    public void setLibelleNationalite(String libelleNationalite) {
        this.libelleNationalite = libelleNationalite;
    }

    public String getLibelleNationalite() {
        return libelleNationalite;
    }

    public void setBoolResPers(String boolResPers) {
        this.boolResPers = boolResPers;
    }

    public String getBoolResPers() {
        return boolResPers;
    }

    public void setCodSegSeg(String codSegSeg) {
        this.codSegSeg = codSegSeg;
    }

    public String getCodSegSeg() {
        return codSegSeg;
    }

    public void setCodSsegSseg(String codSsegSseg) {
        this.codSsegSseg = codSsegSseg;
    }

    public String getCodSsegSseg() {
        return codSsegSseg;
    }

    public void setCodCsegCseg(String codCsegCseg) {
        this.codCsegCseg = codCsegCseg;
    }

    public String getCodCsegCseg() {
        return codCsegCseg;
    }

    public void setLibSegSeg(String libSegSeg) {
        this.libSegSeg = libSegSeg;
    }

    public String getLibSegSeg() {
        return libSegSeg;
    }

    public void setChoixContrat(String choixContrat) {
        this.choixContrat = choixContrat;
    }

    public String getChoixContrat() {
        return choixContrat;
    }

    public void setCodeNationalite2(String codeNationalite2) {
        this.codeNationalite2 = codeNationalite2;
    }

    public String getCodeNationalite2() {
        return codeNationalite2;
    }

    public void setLibelleNationalite2(String libelleNationalite2) {
        this.libelleNationalite2 = libelleNationalite2;
    }

    public String getLibelleNationalite2() {
        return libelleNationalite2;
    }

    public void setCodRmatRmat(String codRmatRmat) {
        this.codRmatRmat = codRmatRmat;
    }

    public String getCodRmatRmat() {
        return codRmatRmat;
    }

    public void setLibRmatRmat(String libRmatRmat) {
        this.libRmatRmat = libRmatRmat;
    }

    public String getLibRmatRmat() {
        return libRmatRmat;
    }

    public void setCodNiviNivi(String codNiviNivi) {
        this.codNiviNivi = codNiviNivi;
    }

    public String getCodNiviNivi() {
        return codNiviNivi;
    }

    public void setLibNiviNivi(String libNiviNivi) {
        this.libNiviNivi = libNiviNivi;
    }

    public String getLibNiviNivi() {
        return libNiviNivi;
    }

    public void setCodDoanClt(String codDoanClt) {
        this.codDoanClt = codDoanClt;
    }

    public String getCodDoanClt() {
        return codDoanClt;
    }

    public void setNumFiscClt(String numFiscClt) {
        this.numFiscClt = numFiscClt;
    }

    public String getNumFiscClt() {
        return numFiscClt;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public Client getClient() {
        return client;
    }

    public void setTypePersonne(String typePersonne) {
        this.typePersonne = typePersonne;
    }

    public String getTypePersonne() {
        return typePersonne;
    }

    public void setRaisonSocial(String raisonSocial) {
        this.raisonSocial = raisonSocial;
    }

    public String getRaisonSocial() {
        return raisonSocial;
    }

    public void setSigle(String sigle) {
        this.sigle = sigle;
    }

    public String getSigle() {
        return sigle;
    }

    public void setLibSiglPers(String libSiglPers) {
        this.libSiglPers = libSiglPers;
    }

    public String getLibSiglPers() {
        return libSiglPers;
    }

    public void setCodFjFj(String codFjFj) {
        this.codFjFj = codFjFj;
    }

    public String getCodFjFj() {
        return codFjFj;
    }

    public void setLibelleFormeJuridique(String libelleFormeJuridique) {
        this.libelleFormeJuridique = libelleFormeJuridique;
    }

    public String getLibelleFormeJuridique() {
        return libelleFormeJuridique;
    }

    public void setLibelleCategoriePersonne(String libelleCategoriePersonne) {
        this.libelleCategoriePersonne = libelleCategoriePersonne;
    }

    public String getLibelleCategoriePersonne() {
        return libelleCategoriePersonne;
    }

    public void setListeCategoriePersonne(List listeCategoriePersonne) {
        this.listeCategoriePersonne = listeCategoriePersonne;
    }

    public List getListeCategoriePersonne() {
        return listeCategoriePersonne;
    }

    public void setListeFormeJuridique(List listeFormeJuridique) {
        this.listeFormeJuridique = listeFormeJuridique;
    }

    public List getListeFormeJuridique() {
        return listeFormeJuridique;
    }

    public void setAncienneCodFjFj(String ancienneCodFjFj) {
        this.ancienneCodFjFj = ancienneCodFjFj;
    }

    public String getAncienneCodFjFj() {
        return ancienneCodFjFj;
    }

    public void setAncienneCodCatpCatp(String ancienneCodCatpCatp) {
        this.ancienneCodCatpCatp = ancienneCodCatpCatp;
    }

    public String getAncienneCodCatpCatp() {
        return ancienneCodCatpCatp;
    }

    public void setNumBctClt(String numBctClt) {
        this.numBctClt = numBctClt;
    }

    public String getNumBctClt() {
        return numBctClt;
    }

    public void setNumRnePers(String numRnePers) {
        this.numRnePers = numRnePers;
    }

    public String getNumRnePers() {
        return numRnePers;
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

    public void setDateActMoral(String dateActMoral) {
        this.dateActMoral = dateActMoral;
    }

    public String getDateActMoral() {
        return dateActMoral;
    }

    public void setDateDelivMoral(String dateDelivMoral) {
        this.dateDelivMoral = dateDelivMoral;
    }

    public String getDateDelivMoral() {
        return dateDelivMoral;
    }

    public void setDatCapPers(String datCapPers) {
        this.datCapPers = datCapPers;
    }

    public String getDatCapPers() {
        return datCapPers;
    }

    public void setMontCapPers(String montCapPers) {
        this.montCapPers = montCapPers;
    }

    public String getMontCapPers() {
        return montCapPers;
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

    public void setCodSitfPers(String codSitfPers) {
        this.codSitfPers = codSitfPers;
    }

    public String getCodSitfPers() {
        return codSitfPers;
    }

    public void setNomRsGrp(String nomRsGrp) {
        this.NomRsGrp = nomRsGrp;
    }

    public String getNomRsGrp() {
        return NomRsGrp;
    }

    public void setCodGrpGrp(String codGrpGrp) {
        this.codGrpGrp = codGrpGrp;
    }

    public String getCodGrpGrp() {
        return codGrpGrp;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getAge() {
        return age;
    }

    public void setListDesContratAmodifier(List listDesContratAmodifier) {
        this.listDesContratAmodifier = listDesContratAmodifier;
    }

    public List getListDesContratAmodifier() {
        return listDesContratAmodifier;
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

    public void setNbrEnfPers(String nbrEnfPers) {
        this.nbrEnfPers = nbrEnfPers;
    }

    public String getNbrEnfPers() {
        return nbrEnfPers;
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

    public void setCodCsprCspr(String codCsprCspr) {
        this.codCsprCspr = codCsprCspr;
    }

    public String getCodCsprCspr() {
        return codCsprCspr;
    }

    public void setChaineMatriculeFisc(String chaineMatriculeFisc) {
        this.chaineMatriculeFisc = chaineMatriculeFisc;
    }

    public String getChaineMatriculeFisc() {
        return chaineMatriculeFisc;
    }

    public void setCodeTribunal(String codeTribunal) {
        this.codeTribunal = codeTribunal;
    }

    public String getCodeTribunal() {
        return codeTribunal;
    }

    public void setCodeRcs(String codeRcs) {
        this.codeRcs = codeRcs;
    }

    public String getCodeRcs() {
        return codeRcs;
    }

    public void setNumeroRcs(String numeroRcs) {
        this.numeroRcs = numeroRcs;
    }

    public String getNumeroRcs() {
        return numeroRcs;
    }

    public void setAnneeRcs(String anneeRcs) {
        this.anneeRcs = anneeRcs;
    }

    public String getAnneeRcs() {
        return anneeRcs;
    }

    public void setListeDesContratsAmodifierAdresse(List listeDesContratsAmodifierAdresse) {
        this.listeDesContratsAmodifierAdresse = 
                listeDesContratsAmodifierAdresse;
    }

    public List getListeDesContratsAmodifierAdresse() {
        return listeDesContratsAmodifierAdresse;
    }

    public void setLieuDelivrance(String lieuDelivrance) {
        this.lieuDelivrance = lieuDelivrance;
    }

    public String getLieuDelivrance() {
        return lieuDelivrance;
    }

    public void setCodGouvGouvPiece(String codGouvGouvPiece) {
        this.codGouvGouvPiece = codGouvGouvPiece;
    }

    public String getCodGouvGouvPiece() {
        return codGouvGouvPiece;
    }

    public void setPiece(String piece) {
        this.piece = piece;
    }

    public String getPiece() {
        return piece;
    }



    public void setLibelleConfirmation1(String libelleConfirmation1) {
        this.libelleConfirmation1 = libelleConfirmation1;
    }

    public String getLibelleConfirmation1() {
        return libelleConfirmation1;
    }

    public void setLibelleConfirmation2(String libelleConfirmation2) {
        this.libelleConfirmation2 = libelleConfirmation2;
    }

    public String getLibelleConfirmation2() {
        return libelleConfirmation2;
    }

    public void setLibEmployeur(String libEmployeur) {
        this.libEmployeur = libEmployeur;
    }

    public String getLibEmployeur() {
        return libEmployeur;
    }

    public void setCodEmpEmp(String codEmpEmp) {
        this.codEmpEmp = codEmpEmp;
    }

    public String getCodEmpEmp() {
        return codEmpEmp;
    }

    public void setTestTypePersonne(String testTypePersonne) {
        this.testTypePersonne = testTypePersonne;
    }

    public String getTestTypePersonne() {
        return testTypePersonne;
    }

    public void setRevRevPers(String revRevPers) {
        this.revRevPers = revRevPers;
    }

    public String getRevRevPers() {
        return revRevPers;
    }

    public void setTestIsClientAgence(String testIsClientAgence) {
        this.testIsClientAgence = testIsClientAgence;
    }

    public String getTestIsClientAgence() {
        return testIsClientAgence;
    }

    public void setNouvelleCodCatpCatp(String nouvelleCodCatpCatp) {
        this.nouvelleCodCatpCatp = nouvelleCodCatpCatp;
    }

    public String getNouvelleCodCatpCatp() {
        return nouvelleCodCatpCatp;
    }

    public void setMessageCreationMandat(String messageCreationMandat) {
        this.messageCreationMandat = messageCreationMandat;
    }

    public String getMessageCreationMandat() {
        return messageCreationMandat;
    }

    public void setCodTpceTpceAnnexe(String codTpceTpceAnnexe) {
        this.codTpceTpceAnnexe = codTpceTpceAnnexe;
    }

    public String getCodTpceTpceAnnexe() {
        return codTpceTpceAnnexe;
    }
}
