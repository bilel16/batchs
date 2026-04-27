package com.bna.smile.web.conditionBanque.forms;

import java.util.ArrayList;
import java.util.Collection;


import org.apache.struts.action.ActionForm;

public class ConditionBanqueForm extends ActionForm {
   
   
    public ConditionBanqueForm() {
    }

  //Pour les conditions à échoir
  private String dateEcheance;
 // private String dateFinEcheance;
  
  //Pour les conditions echues
  private String dateDebutEcheance;
  
  private String etat;
  
  private String codeOperation;
  private String operation;
  private String codeProduit;
  private String produit;
  
  private String codeOrganisme;
  private String organisme;
  private String codeGroupe;
  private String groupe;
  
  private String reqCode;
  
  private String typePieceId;
  private String numPieceId;
  private String codStrcRech;
  private String codPrdRech;
  private String numCcptRech;
  private String choix;
  private String numSeqPers;
  private String nom;
  private String prenom;



  private Collection listeOperation =new ArrayList();
  private Collection listeProduit =new ArrayList();
  private Collection listeGroupe =new ArrayList();


    public void setDateEcheance(String dateEcheance) {
        this.dateEcheance = dateEcheance;
    }

    public String getDateEcheance() {
        return dateEcheance;
    }

    public void setEtat(String etat) {
        this.etat = etat;
    }

    public String getEtat() {
        return etat;
    }

    public void setCodeOperation(String codeOperation) {
        this.codeOperation = codeOperation;
    }

    public String getCodeOperation() {
        return codeOperation;
    }

    public void setOperation(String operation) {
        this.operation = operation;
    }

    public String getOperation() {
        return operation;
    }

    public void setCodeProduit(String codeProduit) {
        this.codeProduit = codeProduit;
    }

    public String getCodeProduit() {
        return codeProduit;
    }

    public void setProduit(String produit) {
        this.produit = produit;
    }

    public String getProduit() {
        return produit;
    }

    public void setCodeOrganisme(String codeOrganisme) {
        this.codeOrganisme = codeOrganisme;
    }

    public String getCodeOrganisme() {
        return codeOrganisme;
    }

    public void setOrganisme(String organisme) {
        this.organisme = organisme;
    }

    public String getOrganisme() {
        return organisme;
    }

    public void setCodeGroupe(String codeGroupe) {
        this.codeGroupe = codeGroupe;
    }

    public String getCodeGroupe() {
        return codeGroupe;
    }

    public void setGroupe(String groupe) {
        this.groupe = groupe;
    }

    public String getGroupe() {
        return groupe;
    }

    public void setReqCode(String reqCode) {
        this.reqCode = reqCode;
    }

    public String getReqCode() {
        return reqCode;
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

    public void setChoix(String choix) {
        this.choix = choix;
    }

    public String getChoix() {
        return choix;
    }

    public void setNumSeqPers(String numSeqPers) {
        this.numSeqPers = numSeqPers;
    }

    public String getNumSeqPers() {
        return numSeqPers;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getNom() {
        return nom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setListeOperation(Collection listeOperation) {
        this.listeOperation = listeOperation;
    }

    public Collection getListeOperation() {
        return listeOperation;
    }

    public void setListeProduit(Collection listeProduit) {
        this.listeProduit = listeProduit;
    }

    public Collection getListeProduit() {
        return listeProduit;
    }

    public void setListeGroupe(Collection listeGroupe) {
        this.listeGroupe = listeGroupe;
    }

    public Collection getListeGroupe() {
        return listeGroupe;
    }

    public void setDateDebutEcheance(String dateDebutEcheance) {
        this.dateDebutEcheance = dateDebutEcheance;
    }

    public String getDateDebutEcheance() {
        return dateDebutEcheance;
    }


   /* public void setDateFinEcheance(String dateFinEcheance) {
        this.dateFinEcheance = dateFinEcheance;
    }

    public String getDateFinEcheance() {
        return dateFinEcheance;
    }*/
}
