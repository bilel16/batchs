package com.bna.smile.web.operationguichet.form;

import com.bna.smile.web.commun.view.InitialisationView;

import com.bna.smile.web.operationguichet.view.CaisseView;

import fr.improve.struts.taglib.layout.datagrid.Datagrid;

import java.util.ArrayList;

import java.util.Collection;
import java.util.List;

import org.apache.struts.action.ActionForm;

public class CaisseForm extends ActionForm{

    private  InitialisationView initialisationView = new InitialisationView();
    private  String titrePage="";
    private  CaisseView caisseView = new CaisseView();
    private  List listeCaisses;
    private  String numCaisseChoisi;
    private  String alertAfficheCaisse;
    private  List listeDetailSessionCaisses = new ArrayList();
    private  List listeDetailSessionCaisseOrigine = new ArrayList();
    private  List listeDetailCaisseAinserer = new ArrayList();
    private  String alert;
    private  String libelleOperation;
    private  String numeroSessionCaisseChoisi="-";
    private String matrCaissier;
    private String nomPrnUserAjax ="";
    private String codeStructureAjax ="";
    private String codeStructure ="";
    private String libStructureAjax ="";
    private String matrUserAjax ="";
    private String nomPrnUserRecup="";
    private String ancienMatricule="";
    private String titreConfirmation="Confirmation";
    private String libelleConfirmation="";
    private Datagrid listeDetailSessionCaissesGrid;
    private List listeCaisse;
    private String numCaisseCible;
    private List listeMouvements = new ArrayList();
    private List listeMouvementsAinserer = new ArrayList();
    private Collection listeMouvementsView;
    private List indexMvtsChoisis = new ArrayList();
    private String codetrait;

    public CaisseForm() {
    }

    public void clearFormCaisse(){

        titrePage="";
        initialisationView = new InitialisationView();
        caisseView = new CaisseView();
        listeCaisses=new ArrayList();
        numCaisseChoisi="0";
        alertAfficheCaisse="";
        listeDetailSessionCaisses = new ArrayList();
        alert=" ";
        numeroSessionCaisseChoisi="-";
        matrCaissier = "";
        nomPrnUserAjax ="";
        codeStructureAjax ="";
        matrUserAjax ="";
        nomPrnUserRecup="";
        ancienMatricule="";
        titreConfirmation="Confirmation";
        libelleConfirmation="";
        listeCaisse = new ArrayList();
        indexMvtsChoisis = new ArrayList();
        listeMouvements = new ArrayList();
        listeMouvementsAinserer = new ArrayList();
        listeDetailSessionCaisseOrigine = new ArrayList();
        listeDetailCaisseAinserer = new ArrayList();
        listeMouvementsView = new ArrayList();
    }
    public void setInitialisationView(InitialisationView initialisationView) {
        this.initialisationView = initialisationView;
    }

    public InitialisationView getInitialisationView() {
        return initialisationView;
    }
   public void setTitrePage(String titrePage) {
        this.titrePage = titrePage;
    }

    public String getTitrePage() {
        return titrePage;
    }

    public void setCaisseView(CaisseView caisseView) {
        this.caisseView = caisseView;
    }

    public CaisseView getCaisseView() {
        return caisseView;
    }

    public void setListeCaisses(List listeCaisses) {
        this.listeCaisses = listeCaisses;
    }

    public List getListeCaisses() {
        return listeCaisses;
    }

    public void setNumCaisseChoisi(String numCaisseChoisi) {
        this.numCaisseChoisi = numCaisseChoisi;
    }

    public String getNumCaisseChoisi() {
        return numCaisseChoisi;
    }

   

    public void setAlert(String alert) {
        this.alert = alert;
    }

    public String getAlert() {
        return alert;
    }

    public void setListeDetailSessionCaisses(List listeDetailSessionCaisses) {
        this.listeDetailSessionCaisses = listeDetailSessionCaisses;
    }

    public List getListeDetailSessionCaisses() {
        return listeDetailSessionCaisses;
    }

    public void setLibelleOperation(String libelleOperation) {
        this.libelleOperation = libelleOperation;
    }

    public String getLibelleOperation() {
        return libelleOperation;
    }

    public void setNumeroSessionCaisseChoisi(String numeroSessionCaisseChoisi) {
        this.numeroSessionCaisseChoisi = numeroSessionCaisseChoisi;
    }

    public String getNumeroSessionCaisseChoisi() {
        return numeroSessionCaisseChoisi;
    }


    public void setNomPrnUserAjax(String nomPrnUserAjax) {
        this.nomPrnUserAjax = nomPrnUserAjax;
    }

    public String getNomPrnUserAjax() {
        return nomPrnUserAjax;
    }

    public void setCodeStructureAjax(String codeStructureAjax) {
        this.codeStructureAjax = codeStructureAjax;
    }

    public String getCodeStructureAjax() {
        return codeStructureAjax;
    }

    public void setMatrUserAjax(String matrUserAjax) {
        this.matrUserAjax = matrUserAjax;
    }

    public String getMatrUserAjax() {
        return matrUserAjax;
    }

    public void setNomPrnUserRecup(String nomPrnUserRecup) {
        this.nomPrnUserRecup = nomPrnUserRecup;
    }

    public String getNomPrnUserRecup() {
        return nomPrnUserRecup;
    }

    public void setAncienMatricule(String ancienMatricule) {
        this.ancienMatricule = ancienMatricule;
    }

    public String getAncienMatricule() {
        return ancienMatricule;
    }

    public void setMatrCaissier(String matrCaissier) {
        this.matrCaissier = matrCaissier;
    }

    public String getMatrCaissier() {
        return matrCaissier;
    }

    public void setTitreConfirmation(String titreConfirmation) {
        this.titreConfirmation = titreConfirmation;
    }

    public String getTitreConfirmation() {
        return titreConfirmation;
    }

    public void setLibelleConfirmation(String libelleConfirmation) {
        this.libelleConfirmation = libelleConfirmation;
    }

    public String getLibelleConfirmation() {
        return libelleConfirmation;
    }

    public void setAlertAfficheCaisse(String alertAfficheCaisse) {
        this.alertAfficheCaisse = alertAfficheCaisse;
    }

    public String getAlertAfficheCaisse() {
        return alertAfficheCaisse;
    }

    public void setListeDetailSessionCaissesGrid(Datagrid listeDetailSessionCaissesGrid) {
        this.listeDetailSessionCaissesGrid = listeDetailSessionCaissesGrid;
    }

    public Datagrid getListeDetailSessionCaissesGrid() {
        return listeDetailSessionCaissesGrid;
    }

    public void setNumCaisseCible(String numCaisseCible) {
        this.numCaisseCible = numCaisseCible;
    }

    public String getNumCaisseCible() {
        return numCaisseCible;
    }

    public void setListeCaisse(List listeCaisse) {
        this.listeCaisse = listeCaisse;
    }

    public List getListeCaisse() {
        return listeCaisse;
    }

    public void setIndexMvtsChoisis(List indexMvtsChoisis) {
        this.indexMvtsChoisis = indexMvtsChoisis;
    }

    public List getIndexMvtsChoisis() {
        return indexMvtsChoisis;
    }

    public void setListeMouvementsView(Collection listeMouvementsView) {
        this.listeMouvementsView = listeMouvementsView;
    }

    public Collection getListeMouvementsView() {
        return listeMouvementsView;
    }

    public void setListeMouvements(List listeMouvements) {
        this.listeMouvements = listeMouvements;
    }

    public List getListeMouvements() {
        return listeMouvements;
    }

    public void setListeMouvementsAinserer(List listeMouvementsAinserer) {
        this.listeMouvementsAinserer = listeMouvementsAinserer;
    }

    public List getListeMouvementsAinserer() {
        return listeMouvementsAinserer;
    }

    public void setCodetrait(String codetrait) {
        this.codetrait = codetrait;
    }

    public String getCodetrait() {
        return codetrait;
    }

    public void setLibStructureAjax(String libStructureAjax) {
        this.libStructureAjax = libStructureAjax;
    }

    public String getLibStructureAjax() {
        return libStructureAjax;
    }

    public void setCodeStructure(String codeStructure) {
        this.codeStructure = codeStructure;
    }

    public String getCodeStructure() {
        return codeStructure;
    }

    public void setListeDetailSessionCaisseOrigine(List listeDetailSessionCaisseOrigine) {
        this.listeDetailSessionCaisseOrigine = listeDetailSessionCaisseOrigine;
    }

    public List getListeDetailSessionCaisseOrigine() {
        return listeDetailSessionCaisseOrigine;
    }

    public void setListeDetailCaisseAinserer(List listeDetailCaisseAinserer) {
        this.listeDetailCaisseAinserer = listeDetailCaisseAinserer;
    }

    public List getListeDetailCaisseAinserer() {
        return listeDetailCaisseAinserer;
    }
}
