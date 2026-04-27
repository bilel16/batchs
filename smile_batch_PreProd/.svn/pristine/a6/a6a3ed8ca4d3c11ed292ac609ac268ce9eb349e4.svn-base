package com.bna.smile.web.commun.forms;

import java.sql.Blob;

import java.util.Collection;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

public class CreationPersonneForm extends ActionForm {
    /**Reset all properties to their default values.
     * @param mapping The ActionMapping used to select this instance.
     * @param request The HTTP Request we are processing.
     */
    private

    Collection listeTypePersonne;
    private Collection listeCategoriePersonne;
    private String numSeqPers;

    //--------------------------------------------------------

    private String nomId;
    private String prenomId;
    private String titreId;

    //------------------------------------------------------------
    //données de la Personne:         /////////////////
    private String typePersonneClt;
    private String categoriePersonneClt;
    private Collection listTypePersonneClt;
    private Collection listCategoriePersonneClt;

    private String typePieceClt;
    private String codTypePieceClt;
    private String codeFormeJuridiquePers;
    private String dateDelivClt;
    private String numPieceClient;
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
    private String codNationaliteClt;
    private String nationaliteClt;
    private String residentClt;
    private String sexeClt;
    private String paysNaisClt;
    private String codPaysNaisClt;

    private String sitFamilialeClt;
    private String sectActiviteClt;
    private String professionPers;
    private String codGroupProfPers;
    private String codProfPers;
    private String activiteClt;
    private String codActiviteClt;
    private String codClasActiviteClt;
    private String codSclasActiviteClt;
    private Blob signatureClt;


    // JSP adresse ;
    private String immeubleAdrResid;
    private String rueAdrResid;
    private String citeAdrResid;
    private String villeAdrResid;
    private String paysAdrResid;
    private String codPayAdrResid;
    private String codePostalAdrResid;
    private String libPostalAdrResid;
    private String codGouvGouvRes;
    private String libGouvGouvRes;
    private String immeubleAdrProf;
    private String rueAdrProf;
    private String citeAdrProf;
    private String villeAdrProf;
    private String paysAdrProf;
    private String codPayAdrProf;
    private String codePostalAdrProf;
    private String libPostalAdrProf;
    private String codGouvGouvProf;
    private String libGouvGouvProf;

    //-----------------------------------------------------------------
    //: données globales /////////////////
    private String dateActuelle;
    private String alert;
    private String reqCode;
    private String alertProduit;
    private String choixInitPage = "initPage";

    //-------------------------------------------------------
    //: Type d'appel
    private String numero;
    private String nom;

   
    //---------------------------------------------------------    
    // index de l'element de la page appelante
    private String indexElementAppel;
    private String etatInsertion;

    //-------------------------------------------------
    // Donnnées information complementaire
    
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
     
     private String numRetour;
    
    
    public void reset(ActionMapping mapping, HttpServletRequest request) {
        super.reset(mapping, request);
    }

    public void clearForm() {

        //tabshett 1 : identification /////////////////
        //numPieceId ="";    
        nomId       = "";
        prenomId    = "";
        titreId     = "";

        //------------------------------------------------------------
        //données de la personne         /////////////////
   
        categoriePersonneClt = "";
        
        dateDelivClt = "";
   
        lieuDelivClt = "";
        
        dateDellivPiann = "";
        dateFinPian = "";
        
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
        professionPers = "";
        activiteClt = "";
        signatureClt = null;


        //: données globales /////////////////
        alert = "";
        //reqCode ="initierPage";
        alertProduit = "";
        //dateActuelle ="";


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
        codGouvGouvRes ="";
        libGouvGouvRes="";
        codGouvGouvProf="";
        libGouvGouvProf="";
        
        etatInsertion = "";
       
        //---Données complementaire        
        nomNomoPers= "";
        nomPrnoPers="";
        nomNomaPers="";
        nomPrnaPers="";
        libNat2Pays="";
        libNiviNivi="";
        libCsprCspr="";
        numAffsPers="";
        datAffspers="";
        nbrEnfPers = "0";
        codRmatRmat="";
        nomNommPers="";
        nomPrnmPers="";
        numTelPers="";
        numFaxpers="";
        adrMailPers="";
        adrWebPers="";
        adrTlxPers="";
        adrSwiftPers="";
        numDecePers="";
        datDecePers="";
         // champs hidden pour le popup
        codNiviNivi="";
        codNat2Pays="";
        libRmatRmat="";
        codCsprCspr="";
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

    public void setNumSeqPers(String numSeqPers) {
        this.numSeqPers = numSeqPers;
    }

    public String getNumSeqPers() {
        return numSeqPers;
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

    public void setTitreId(String titreId) {
        this.titreId = titreId;
    }

    public String getTitreId() {
        return titreId;
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

    public void setAlertProduit(String alertProduit) {
        this.alertProduit = alertProduit;
    }

    public String getAlertProduit() {
        return alertProduit;
    }

    public void setChoixInitPage(String choixInitPage) {
        this.choixInitPage = choixInitPage;
    }

    public String getChoixInitPage() {
        return choixInitPage;
    }


    public void setDateActuelle(String dateActuelle) {
        this.dateActuelle = dateActuelle;
    }

    public String getDateActuelle() {
        return dateActuelle;
    }


    public void setCodTypePieceClt(String codTypePieceClt) {
        this.codTypePieceClt = codTypePieceClt;
    }

    public String getCodTypePieceClt() {
        return codTypePieceClt;
    }

    public void setNumPieceClient(String numPieceClient) {
        this.numPieceClient = numPieceClient;
    }

    public String getNumPieceClient() {
        return numPieceClient;
    }

    public void setCodNationaliteClt(String codNationaliteClt) {
        this.codNationaliteClt = codNationaliteClt;
    }

    public String getCodNationaliteClt() {
        return codNationaliteClt;
    }

    public void setProfessionPers(String professionPers) {
        this.professionPers = professionPers;
    }

    public String getProfessionPers() {
        return professionPers;
    }

    public void setCodGroupProfPers(String codGroupProfPers) {
        this.codGroupProfPers = codGroupProfPers;
    }

    public String getCodGroupProfPers() {
        return codGroupProfPers;
    }

    public void setCodProfPers(String codProfPers) {
        this.codProfPers = codProfPers;
    }

    public String getCodProfPers() {
        return codProfPers;
    }

    public void setCodeFormeJuridiquePers(String codeFormeJuridiquePers) {
        this.codeFormeJuridiquePers = codeFormeJuridiquePers;
    }

    public String getCodeFormeJuridiquePers() {
        return codeFormeJuridiquePers;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getNumero() {
        return numero;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getNom() {
        return nom;
    }

    public void setIndexElementAppel(String indexElementAppel) {
        this.indexElementAppel = indexElementAppel;
    }

    public String getIndexElementAppel() {
        return indexElementAppel;
    }


    public void setEtatInsertion(String etatInsertion) {
        this.etatInsertion = etatInsertion;
    }

    public String getEtatInsertion() {
        return etatInsertion;
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

    public void setNumRetour(String numRetour) {
        this.numRetour = numRetour;
    }

    public String getNumRetour() {
        return numRetour;
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
}
