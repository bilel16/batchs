package com.bna.smile.web.commun.forms;

import com.bna.commun.model.ContratCpt;
import com.bna.commun.model.TypeModification;
import com.bna.smile.web.commun.view.InitialisationView;

import java.util.ArrayList;
import java.util.Collection;

import org.apache.struts.action.ActionForm;

public class ModificationTypeCompteForm extends ActionForm{
    public ModificationTypeCompteForm() {
    }
    
    private  InitialisationView initialisationView = new InitialisationView();
    private  String codeModification;
    private  String libelleModification;
    private  TypeModification typeModification;
    private  String codStrcRech ;
    private  String codPrdRech  ;
    private  String numCcptRech ;
    private  String libelleCpt;
    private  String typeCotit;
    private  String typeSignature;
    private  String alert;
    private  String nomIntiCcpt;
    private  Collection listeMembreEntiteCotit = new ArrayList(0);
    private  Collection listeMembreEntiteCotitChoisi = new ArrayList(0);
    private  ContratCpt contratCpt;
    private  String codTpceTpceChoisi;
    private  String numPcePersChoisi;
    private  String libTpceTpceChoisi;
    private  String numSeqPersChoisi;
    private  String nomPersonneChoisi;
    private  String prenomPersonneChoisi;
    private  String confirmation;
    private  String confirmation1;
    private  String confirmation2;
    
    
    
    public void clearForm() {
        codStrcRech ="";
        codPrdRech  ="" ;
        numCcptRech ="" ;
        libelleCpt  ="";
        nomIntiCcpt ="";
        typeCotit   ="";
        typeSignature ="";
        alert ="";
        codTpceTpceChoisi ="";
        numPcePersChoisi ="";
        libTpceTpceChoisi ="";
        numSeqPersChoisi ="";
        nomPersonneChoisi ="";
        prenomPersonneChoisi ="";
        listeMembreEntiteCotit = new ArrayList(0);
        listeMembreEntiteCotitChoisi = new ArrayList(0);
        contratCpt = null;
        confirmation ="";
        confirmation1 ="";
        confirmation2 ="";
    }
    

    public void setInitialisationView(InitialisationView initialisationView) {
        this.initialisationView = initialisationView;
    }

    public InitialisationView getInitialisationView() {
        return initialisationView;
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

    public void setLibelleCpt(String libelleCpt) {
        this.libelleCpt = libelleCpt;
    }

    public String getLibelleCpt() {
        return libelleCpt;
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

    public void setAlert(String alert) {
        this.alert = alert;
    }

    public String getAlert() {
        return alert;
    }

    public void setListeMembreEntiteCotit(Collection listeMembreEntiteCotit) {
        this.listeMembreEntiteCotit = listeMembreEntiteCotit;
    }

    public Collection getListeMembreEntiteCotit() {
        return listeMembreEntiteCotit;
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

    public void setTypeModification(TypeModification typeModification) {
        this.typeModification = typeModification;
    }

    public TypeModification getTypeModification() {
        return typeModification;
    }

    public void setListeMembreEntiteCotitChoisi(Collection listeMembreEntiteCotitChoisi) {
        this.listeMembreEntiteCotitChoisi = listeMembreEntiteCotitChoisi;
    }

    public Collection getListeMembreEntiteCotitChoisi() {
        return listeMembreEntiteCotitChoisi;
    }

    public void setCodTpceTpceChoisi(String codTpceTpceChoisi) {
        this.codTpceTpceChoisi = codTpceTpceChoisi;
    }

    public String getCodTpceTpceChoisi() {
        return codTpceTpceChoisi;
    }

    public void setNumSeqPersChoisi(String numSeqPersChoisi) {
        this.numSeqPersChoisi = numSeqPersChoisi;
    }

    public String getNumSeqPersChoisi() {
        return numSeqPersChoisi;
    }

    public void setLibTpceTpceChoisi(String libTpceTpceChoisi) {
        this.libTpceTpceChoisi = libTpceTpceChoisi;
    }

    public String getLibTpceTpceChoisi() {
        return libTpceTpceChoisi;
    }

    public void setNomPersonneChoisi(String nomPersonneChoisi) {
        this.nomPersonneChoisi = nomPersonneChoisi;
    }

    public String getNomPersonneChoisi() {
        return nomPersonneChoisi;
    }

    public void setPrenomPersonneChoisi(String prenomPersonneChoisi) {
        this.prenomPersonneChoisi = prenomPersonneChoisi;
    }

    public String getPrenomPersonneChoisi() {
        return prenomPersonneChoisi;
    }

    public void setNumPcePersChoisi(String numPcePersChoisi) {
        this.numPcePersChoisi = numPcePersChoisi;
    }

    public String getNumPcePersChoisi() {
        return numPcePersChoisi;
    }

    public void setNomIntiCcpt(String nomIntiCcpt) {
        this.nomIntiCcpt = nomIntiCcpt;
    }

    public String getNomIntiCcpt() {
        return nomIntiCcpt;
    }

    public void setContratCpt(ContratCpt contratCpt) {
        this.contratCpt = contratCpt;
    }

    public ContratCpt getContratCpt() {
        return contratCpt;
    }

    public void setConfirmation(String confirmation) {
        this.confirmation = confirmation;
    }

    public String getConfirmation() {
        return confirmation;
    }

    public void setConfirmation1(String confirmation1) {
        this.confirmation1 = confirmation1;
    }

    public String getConfirmation1() {
        return confirmation1;
    }

    public void setConfirmation2(String confirmation2) {
        this.confirmation2 = confirmation2;
    }

    public String getConfirmation2() {
        return confirmation2;
    }
}
