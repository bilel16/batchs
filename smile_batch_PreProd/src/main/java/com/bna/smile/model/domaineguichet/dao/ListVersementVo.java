package com.bna.smile.model.domaineguichet.dao;

import com.bna.commun.model.Personnel;

import com.oxia.fwk.core.ValueObject;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ListVersementVo extends ValueObject{
  private Date   dateVersement;
  private String typeVersement;
  private String etatVersement;
    
  private String codeOperation;
  private Long codeTache;
    
  private String userInitiateur;
  private String structurInitiatrice;
  private String structurReceptrice;
  
  private List listeVersements    = new ArrayList();
 
  
    public ListVersementVo() {
    }

    public void setDateVersement(Date dateVersement) {
        this.dateVersement = dateVersement;
    }

    public Date getDateVersement() {
        return dateVersement;
    }

    public void setTypeVersement(String typeVersement) {
        this.typeVersement = typeVersement;
    }

    public String getTypeVersement() {
        return typeVersement;
    }



    public void setUserInitiateur(String userInitiateur) {
        this.userInitiateur = userInitiateur;
    }

    public String getUserInitiateur() {
        return userInitiateur;
    }

    public void setStructurInitiatrice(String structurInitiatrice) {
        this.structurInitiatrice = structurInitiatrice;
    }

    public String getStructurInitiatrice() {
        return structurInitiatrice;
    }


    public void setCodeOperation(String codeOperation) {
        this.codeOperation = codeOperation;
    }

    public String getCodeOperation() {
        return codeOperation;
    }

    public void setStructurReceptrice(String structurReceptrice) {
        this.structurReceptrice = structurReceptrice;
    }

    public String getStructurReceptrice() {
        return structurReceptrice;
    }

    public void setEtatVersement(String etatVersement) {
        this.etatVersement = etatVersement;
    }

    public String getEtatVersement() {
        return etatVersement;
    }

    public void setListeVersements(List listeVersements) {
        this.listeVersements = listeVersements;
    }

    public List getListeVersements() {
        return listeVersements;
    }


    public void setCodeTache(Long codeTache) {
        this.codeTache = codeTache;
    }

    public Long getCodeTache() {
        return codeTache;
    }
}
