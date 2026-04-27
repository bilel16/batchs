package com.bna.smile.model.clotureDomaine.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.bna.commun.model.JourneeStructureDomaineId;
import com.bna.smile.model.moyenPayement.model.Accuse;
import com.oxia.fwk.core.ValueObject;

public class StatMoyPai extends ValueObject{
    public StatMoyPai() {
    }
    private JourneeStructureDomaineId journeeStructureDomaineId;
    private Long structure;
    private Long produit;
    private Date dateJournee;
    private String etat;
    private Long nbr822;
    private Double mnt822;
    private Long nbr948;
    private Double mnt948;
    private Long nbr947;
    private Double mnt947; 
    private Long nbr821;
    private Double mnt821;
    private Long nbr823;
    private Double mnt823;
    private Long nbr824;
    private Double mnt824;
    
    private Long nbrVirCompRec;
    private Double MntVirCompRec;
    private Long nbrRejVirRec;
    private Double MntRejVirRec;
    private List listeVirCompRec=new ArrayList(0);
    private List<Accuse> listeAccusee=new ArrayList<Accuse>();
    
    public void setJourneeStructureDomaineId(JourneeStructureDomaineId journeeStructureDomaineId) {
        this.journeeStructureDomaineId = journeeStructureDomaineId;
    }

    public JourneeStructureDomaineId getJourneeStructureDomaineId() {
        return journeeStructureDomaineId;
    }

    public void setStructure(Long structure) {
        this.structure = structure;
    }

    public Long getStructure() {
        return structure;
    }

    public void setProduit(Long produit) {
        this.produit = produit;
    }

    public Long getProduit() {
        return produit;
    }

    public void setDateJournee(Date dateJournee) {
        this.dateJournee = dateJournee;
    }

    public Date getDateJournee() {
        return dateJournee;
    }

    public void setEtat(String etat) {
        this.etat = etat;
    }

    public String getEtat() {
        return etat;
    }

    public void setListeVirCompRec(List listeVirCompRec) {
        this.listeVirCompRec = listeVirCompRec;
    }

    public List getListeVirCompRec() {
        return listeVirCompRec;
    }

    public void setNbrVirCompRec(Long nbrVirCompRec) {
        this.nbrVirCompRec = nbrVirCompRec;
    }

    public Long getNbrVirCompRec() {
        return nbrVirCompRec;
    }

    public void setMntVirCompRec(Double mntVirCompRec) {
        this.MntVirCompRec = mntVirCompRec;
    }

    public Double getMntVirCompRec() {
        return MntVirCompRec;
    }

    public void setListeAccusee(List<Accuse> listeAccusee) {
        this.listeAccusee = listeAccusee;
    }

    public List<Accuse> getListeAccusee() {
        return listeAccusee;
    }

    public void setNbrRejVirRec(Long nbrRejVirRec) {
        this.nbrRejVirRec = nbrRejVirRec;
    }

    public Long getNbrRejVirRec() {
        return nbrRejVirRec;
    }

    public void setMntRejVirRec(Double mntRejVirRec) {
        this.MntRejVirRec = mntRejVirRec;
    }

    public Double getMntRejVirRec() {
        return MntRejVirRec;
    }

    public void setNbr822(Long nbr822) {
        this.nbr822 = nbr822;
    }

    public Long getNbr822() {
        return nbr822;
    }

    public void setMnt822(Double mnt822) {
        this.mnt822 = mnt822;
    }

    public Double getMnt822() {
        return mnt822;
    }

    public void setNbr948(Long nbr948) {
        this.nbr948 = nbr948;
    }

    public Long getNbr948() {
        return nbr948;
    }

    public void setMnt948(Double mnt948) {
        this.mnt948 = mnt948;
    }

    public Double getMnt948() {
        return mnt948;
    }

    public void setNbr947(Long nbr947) {
        this.nbr947 = nbr947;
    }

    public Long getNbr947() {
        return nbr947;
    }

    public void setMnt947(Double mnt947) {
        this.mnt947 = mnt947;
    }

    public Double getMnt947() {
        return mnt947;
    }

    public void setNbr821(Long nbr821) {
        this.nbr821 = nbr821;
    }

    public Long getNbr821() {
        return nbr821;
    }

    public void setMnt821(Double mnt821) {
        this.mnt821 = mnt821;
    }

    public Double getMnt821() {
        return mnt821;
    }

    public void setNbr823(Long nbr823) {
        this.nbr823 = nbr823;
    }

    public Long getNbr823() {
        return nbr823;
    }

    public void setMnt823(Double mnt823) {
        this.mnt823 = mnt823;
    }

    public Double getMnt823() {
        return mnt823;
    }

    public void setNbr824(Long nbr824) {
        this.nbr824 = nbr824;
    }

    public Long getNbr824() {
        return nbr824;
    }

    public void setMnt824(Double mnt824) {
        this.mnt824 = mnt824;
    }

    public Double getMnt824() {
        return mnt824;
    }
}
