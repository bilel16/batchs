package com.bna.smile.web.commun.forms;

import com.bna.commun.model.Client;
import com.bna.commun.model.ContratCpt;
import com.bna.commun.model.Personne;
import com.bna.commun.model.TypeModification;

import com.bna.smile.web.commun.util.PersonneClientView;

import fr.improve.struts.taglib.layout.datagrid.Datagrid;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.struts.action.ActionForm;

public class ModificationQualitePersonneForm extends ActionForm {
    //-------------------------------------------------------
    //---------- Identifiant du type de la modification -----
    //-------------------------------------------------------
    private TypeModification typeModification;
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

    private String nomPersonne;
    private String prenomPersonne;
    private String raisonSocial;
    private String sigle;
    //------------------------------------------------------------
    //------- Liste des Personnes Client ------------------------------------
    //------------------------------------------------------------
    private List listPersonneClient = new ArrayList();
    private Datagrid listPersonneClientGrid;
    private Collection listNewParsClientView = new ArrayList(0);
    //------------------------------------------------------------
    //------- qualite ------------------------------------
    //------------------------------------------------------------
    private String codQualQual;
    private String libQualQual;

    //------------------------------------------------------------
    //------- Données de Test ------------------------------------
    //------------------------------------------------------------
    private String reqCode;
    private String testExistPersonne = "N";
    private String testExistClient = "N";
    private String etatForm ="0";
    private String message = "";
    private String etatFormCreationPersonne ="0";

    public void clearForm() {


        //--------------------------------------------------------
        //--------Identification de la personne-------------------
        //--------------------------------------------------------
        typePiece = "";
        numeroPiece = "";

        personne = null;
        client = null;

        nomPersonne = "";
        prenomPersonne = "";
        raisonSocial = "";
        sigle = "";
        libelleConfirmation1 ="";
        libelleConfirmation2 ="";
        //------------------------------------------------------------
        //------- Liste des Personnes Client ------------------------------------
        //------------------------------------------------------------
        listPersonneClient = new ArrayList();
        listNewParsClientView = new ArrayList(0);
        
        Datagrid lc_datagrid = Datagrid.getInstance();
        lc_datagrid.setDataClass(PersonneClientView.class);
        lc_datagrid.setData(new ArrayList());
        listPersonneClientGrid = lc_datagrid;
        //------------------------------------------------------------
        //------- qualite ------------------------------------
        //------------------------------------------------------------

        libQualQual = "";

        //------------------------------------------------------------
        //------- Données de Test ------------------------------------
        //------------------------------------------------------------
        reqCode = "";
        testExistPersonne = "N";
        testExistClient = "N";
        message = "";
        etatForm = "0";
        etatFormCreationPersonne ="0";
    }

    public void setTypeModification(TypeModification typeModification) {
        this.typeModification = typeModification;
    }

    public TypeModification getTypeModification() {
        return typeModification;
    }

    public void setTypePersonne(String typePersonne) {
        this.typePersonne = typePersonne;
    }

    public String getTypePersonne() {
        return typePersonne;
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

    public void setLibelleConfirmation(String libelleConfirmation) {
        this.libelleConfirmation = libelleConfirmation;
    }

    public String getLibelleConfirmation() {
        return libelleConfirmation;
    }

    public void setMatriculeUser(String matriculeUser) {
        this.matriculeUser = matriculeUser;
    }

    public String getMatriculeUser() {
        return matriculeUser;
    }

    public void setDateActuelle(String dateActuelle) {
        this.dateActuelle = dateActuelle;
    }

    public String getDateActuelle() {
        return dateActuelle;
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

    public void setClient(Client client) {
        this.client = client;
    }

    public Client getClient() {
        return client;
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

    public void setMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setListPersonneClient(List listPersonneClient) {
        this.listPersonneClient = listPersonneClient;
    }

    public List getListPersonneClient() {
        return listPersonneClient;
    }


    public void setListPersonneClientGrid(Datagrid listPersonneClientGrid) {
        this.listPersonneClientGrid = listPersonneClientGrid;
    }

    public Datagrid getListPersonneClientGrid() {
        return listPersonneClientGrid;
    }

    public void setCodQualQual(String codQualQual) {
        this.codQualQual = codQualQual;
    }

    public String getCodQualQual() {
        return codQualQual;
    }

    public void setLibQualQual(String libQualQual) {
        this.libQualQual = libQualQual;
    }

    public String getLibQualQual() {
        return libQualQual;
    }

    public void setTestExistClient(String testExistClient) {
        this.testExistClient = testExistClient;
    }

    public String getTestExistClient() {
        return testExistClient;
    }

    public void setEtatForm(String etatForm) {
        this.etatForm = etatForm;
    }

    public String getEtatForm() {
        return etatForm;
    }

    public void setEtatFormCreationPersonne(String etatFormCreationPersonne) {
        this.etatFormCreationPersonne = etatFormCreationPersonne;
    }

    public String getEtatFormCreationPersonne() {
        return etatFormCreationPersonne;
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

    public void setListNewParsClientView(Collection listNewParsClientView) {
        this.listNewParsClientView = listNewParsClientView;
    }

    public Collection getListNewParsClientView() {
        return listNewParsClientView;
    }
}
