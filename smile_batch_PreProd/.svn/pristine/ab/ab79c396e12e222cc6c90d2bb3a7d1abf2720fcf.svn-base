package com.bna.smile.web.operationguichet.view;

import com.bna.commun.model.OperationMoyPay;

import com.evermind.util.ArraySet;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Set;

public class VersementMemeAgenceView extends com.oxia.fwk.core.ValueObject implements java.io.Serializable {

    private String montantDinars;
    private String montantDevise;
    private String montantContreValeur;
    private String codDevDev;
    private String libDevDev;
    private String nbrUnitDev;
    private String motifOperation;
    private String referenceClient;
    private String refrenceOperation;
    private String origineDesFonds;
    private String typeVersementDevise;
    private String coursApplique;
    private String coursParite;
    private Date   dateValeur;
    private String structureInitiatrice;
    private String numeroLivret;
    private String mntCommVers;  
    private String mntTvaVers;
    private String datValVers;
    private String caisse;
    private Set listDetailOperMoyPai = new ArraySet();
    
    private OperationMoyPay operationMoyPay = new OperationMoyPay();
    
    public VersementMemeAgenceView() {
        montantDinars = "0";
        montantDevise = "0";
        montantContreValeur = "0";
        codDevDev = "788";
        motifOperation = "";
        refrenceOperation = "";
        origineDesFonds = "";
        coursApplique="0";
        coursParite="0";
        numeroLivret="";
        operationMoyPay = new OperationMoyPay();
        listDetailOperMoyPai = new ArraySet();
    }

    public void setMontantDinars(String montantDinars) {
        this.montantDinars = montantDinars;
    }

    public String getMontantDinars() {
        return montantDinars;
    }



    public void setMontantContreValeur(String montantContreValeur) {
        this.montantContreValeur = montantContreValeur;
    }

    public String getMontantContreValeur() {
        return montantContreValeur;
    }

    public void setCodDevDev(String codDevDev) {
        this.codDevDev = codDevDev;
    }

    public String getCodDevDev() {
        return codDevDev;
    }

    public void setMotifOperation(String motifOperation) {
        this.motifOperation = motifOperation;
    }

    public String getMotifOperation() {
        return motifOperation;
    }

    public void setRefrenceOperation(String refrenceOperation) {
        this.refrenceOperation = refrenceOperation;
    }

    public String getRefrenceOperation() {
        return refrenceOperation;
    }

    public void setLibDevDev(String libDevDev) {
        this.libDevDev = libDevDev;
    }

    public String getLibDevDev() {
        return libDevDev;
    }

    public void setOrigineDesFonds(String origineDesFonds) {
        this.origineDesFonds = origineDesFonds;
    }

    public String getOrigineDesFonds() {
        return origineDesFonds;
    }


    public void setTypeVersementDevise(String typeVersementDevise) {
        this.typeVersementDevise = typeVersementDevise;
    }

    public String getTypeVersementDevise() {
        return typeVersementDevise;
    }

    public void setMontantDevise(String montantDevise) {
        this.montantDevise = montantDevise;
    }

    public String getMontantDevise() {
        return montantDevise;
    }

    public void setCoursApplique(String coursApplique) {
        this.coursApplique = coursApplique;
    }

    public String getCoursApplique() {
        return coursApplique;
    }

    public void setCoursParite(String coursParite) {
        this.coursParite = coursParite;
    }

    public String getCoursParite() {
        return coursParite;
    }

    public void setDateValeur(Date dateValeur) {
        this.dateValeur = dateValeur;
    }

    public Date getDateValeur() {
        return dateValeur;
    }

    public void setReferenceClient(String referenceClient) {
        this.referenceClient = referenceClient;
    }

    public String getReferenceClient() {
        return referenceClient;
    }

    public void setStructureInitiatrice(String structureInitiatrice) {
        this.structureInitiatrice = structureInitiatrice;
    }

    public String getStructureInitiatrice() {
        return structureInitiatrice;
    }

    public void setNumeroLivret(String numeroLivret) {
        this.numeroLivret = numeroLivret;
    }

    public String getNumeroLivret() {
        return numeroLivret;
    }

    public void setNbrUnitDev(String nbrUnitDev) {
        this.nbrUnitDev = nbrUnitDev;
    }

    public String getNbrUnitDev() {
        return nbrUnitDev;
    }

    public void setMntCommVers(String mntCommVers) {
        this.mntCommVers = mntCommVers;
    }

    public String getMntCommVers() {
        return mntCommVers;
    }

    public void setMntTvaVers(String mntTvaVers) {
        this.mntTvaVers = mntTvaVers;
    }

    public String getMntTvaVers() {
        return mntTvaVers;
    }

    public void setDatValVers(String datValVers) {
        this.datValVers = datValVers;
    }

    public String getDatValVers() {
        return datValVers;
    }



    public void setOperationMoyPay(OperationMoyPay operationMoyPay) {
        this.operationMoyPay = operationMoyPay;
    }

    public OperationMoyPay getOperationMoyPay() {
        return operationMoyPay;
    }


    public void setListDetailOperMoyPai(Set listDetailOperMoyPai) {
        this.listDetailOperMoyPai = listDetailOperMoyPai;
    }

    public Set getListDetailOperMoyPai() {
        return listDetailOperMoyPai;
    }

    public void setCaisse(String caisse) {
        this.caisse = caisse;
    }

    public String getCaisse() {
        if (operationMoyPay != null && operationMoyPay.getAffectationCaisseStructure() != null ){
            String text = operationMoyPay.getAffectationCaisseStructure().getCodCaisAc() + " "+
            operationMoyPay.getAffectationCaisseStructure().getLibCaisAc();
            return text;
        }
        return caisse;
    }
}
