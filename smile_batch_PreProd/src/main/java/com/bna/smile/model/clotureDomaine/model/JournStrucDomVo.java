package com.bna.smile.model.clotureDomaine.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.bna.commun.model.JourneeStructureDomaineId;
import com.bna.commun.model.JourneeStructureId;
import com.oxia.fwk.core.ValueObject;

public class JournStrucDomVo extends ValueObject{
    public JournStrucDomVo() {
    }
    private JourneeStructureDomaineId journeeStructureDomaineId;
    private JourneeStructureId journeeStructureId;
    private String matriculeInitiateur;
    private String libDomaine;
    private String nbrSousc;
    private String nbrMandCre;
    private String nbrMandMod;
    private String nbrMandRen;
    private String nbrMandAnn;
    private String nbrCheq;
    private String nbrCart;
    private Long dernierDomaine=Long.valueOf(0);
    private Date nouvelleJournee;
    private List listeModificationDonneeParType     = new ArrayList(0);
    private List listeOperationsPlacement  = new ArrayList(0);
    private Long nbrOper672;
    private Double mntOper672;
    private Long nbrOper703;
    private Double mntOper703;
    private List listeOperVirement  = new ArrayList(0);
    
    public void setJourneeStructureDomaineId(JourneeStructureDomaineId journeeStructureDomaineId) {
        this.journeeStructureDomaineId = journeeStructureDomaineId;
    }

    public JourneeStructureDomaineId getJourneeStructureDomaineId() {
        return journeeStructureDomaineId;
    }

    public void setMatriculeInitiateur(String matriculeInitiateur) {
        this.matriculeInitiateur = matriculeInitiateur;
    }

    public String getMatriculeInitiateur() {
        return matriculeInitiateur;
    }

    public void setNbrSousc(String nbrSousc) {
        this.nbrSousc = nbrSousc;
    }

    public String getNbrSousc() {
        return nbrSousc;
    }

    public void setNbrCheq(String nbrCheq) {
        this.nbrCheq = nbrCheq;
    }

    public String getNbrCheq() {
        return nbrCheq;
    }

    public void setNbrCart(String nbrCart) {
        this.nbrCart = nbrCart;
    }

    public String getNbrCart() {
        return nbrCart;
    }

    public void setNbrMandCre(String nbrMandCre) {
        this.nbrMandCre = nbrMandCre;
    }

    public String getNbrMandCre() {
        return nbrMandCre;
    }

    public void setNbrMandMod(String nbrMandMod) {
        this.nbrMandMod = nbrMandMod;
    }

    public String getNbrMandMod() {
        return nbrMandMod;
    }

    public void setNbrMandRen(String nbrMandRen) {
        this.nbrMandRen = nbrMandRen;
    }

    public String getNbrMandRen() {
        return nbrMandRen;
    }

    public void setNbrMandAnn(String nbrMandAnn) {
        this.nbrMandAnn = nbrMandAnn;
    }

    public String getNbrMandAnn() {
        return nbrMandAnn;
    }

    public void setDernierDomaine(Long dernierDomaine) {
        this.dernierDomaine = dernierDomaine;
    }

    public Long getDernierDomaine() {
        return dernierDomaine;
    }

    public void setNouvelleJournee(Date nouvelleJournee) {
        this.nouvelleJournee = nouvelleJournee;
    }

    public Date getNouvelleJournee() {
        return nouvelleJournee;
    }

    public void setLibDomaine(String libDomaine) {
        this.libDomaine = libDomaine;
    }

    public String getLibDomaine() {
        return libDomaine;
    }

    public void setListeModificationDonneeParType(List listeModificationDonneeParType) {
        this.listeModificationDonneeParType = listeModificationDonneeParType;
    }

    public List getListeModificationDonneeParType() {
        return listeModificationDonneeParType;
    }

    public void setJourneeStructureId(JourneeStructureId journeeStructureId) {
        this.journeeStructureId = journeeStructureId;
    }

    public JourneeStructureId getJourneeStructureId() {
        return journeeStructureId;
    }


    public void setListeOperationsPlacement(List listeOperationsPlacement) {
        this.listeOperationsPlacement = listeOperationsPlacement;
    }

    public List getListeOperationsPlacement() {
        return listeOperationsPlacement;
    }


    public void setNbrOper672(Long nbrOper672) {
        this.nbrOper672 = nbrOper672;
    }

    public Long getNbrOper672() {
        return nbrOper672;
    }

    public void setMntOper672(Double mntOper672) {
        this.mntOper672 = mntOper672;
    }

    public Double getMntOper672() {
        return mntOper672;
    }

    public void setNbrOper703(Long nbrOper703) {
        this.nbrOper703 = nbrOper703;
    }

    public Long getNbrOper703() {
        return nbrOper703;
    }

    public void setMntOper703(Double mntOper703) {
        this.mntOper703 = mntOper703;
    }

    public Double getMntOper703() {
        return mntOper703;
    }

    public void setListeOperVirement(List listeOperVirement) {
        this.listeOperVirement = listeOperVirement;
    }

    public List getListeOperVirement() {
        return listeOperVirement;
    }
}
