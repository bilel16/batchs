package com.bna.smile.model.domainecontratcompte.moyensPaiement.model;

import java.util.ArrayList;
import java.util.List;

import com.oxia.fwk.core.ValueObject;

/**
 * value Object de retour pour la commande: GetListDemandeChequePersonneCmd 
 * elle contient les différentes listes des demandes de chèque selon l'etat
 * : attente, validée,rejetée, tot.Satisfaite, part.Satisfaite, tot.délivré
 * part.délivré, envoyée DR/DCCCI
 * @author El Arbi Hassine
 * @since 04/06/2007
 */
 
public class ListesDemandesCheques extends ValueObject{

    private List listeGenerale;
    private List listeAttente;
    private List listeValidee;
    private List listeRejetee;
    private List listeTotSatisfaite;
    private List listePartSatisfaite;
    private List listeTotDelivree;
    private List listePartDelivree;
    private List listeEnvoyeeDR_DCCI;
    private List listeDemChqMandatPersonne;
    private List listeDemandeursChqMandatPersonne;
    private List listeDemandeChqADetruire = new ArrayList();
    private List listeDetailOperationChequier = new ArrayList();
    
    

    public ListesDemandesCheques() {
    }

    public void setListeGenerale(List listeGenerale) {
        this.listeGenerale = listeGenerale;
    }

    public List getListeGenerale() {
        return listeGenerale;
    }

    public void setListeAttente(List listeAttente) {
        this.listeAttente = listeAttente;
    }

    public List getListeAttente() {
        return listeAttente;
    }

    public void setListeValidee(List listeValidee) {
        this.listeValidee = listeValidee;
    }

    public List getListeValidee() {
        return listeValidee;
    }

    public void setListeRejetee(List listeRejetee) {
        this.listeRejetee = listeRejetee;
    }

    public List getListeRejetee() {
        return listeRejetee;
    }

    public void setListeTotSatisfaite(List listeTotSatisfaite) {
        this.listeTotSatisfaite = listeTotSatisfaite;
    }

    public List getListeTotSatisfaite() {
        return listeTotSatisfaite;
    }

    public void setListePartSatisfaite(List listePartSatisfaite) {
        this.listePartSatisfaite = listePartSatisfaite;
    }

    public List getListePartSatisfaite() {
        return listePartSatisfaite;
    }

    public void setListeTotDelivree(List listeTotDelivree) {
        this.listeTotDelivree = listeTotDelivree;
    }

    public List getListeTotDelivree() {
        return listeTotDelivree;
    }

    public void setListePartDelivree(List listePartDelivree) {
        this.listePartDelivree = listePartDelivree;
    }

    public List getListePartDelivree() {
        return listePartDelivree;
    }

    public void setListeEnvoyeeDR_DCCI(List listeEnvoyeeDR_DCCI) {
        this.listeEnvoyeeDR_DCCI = listeEnvoyeeDR_DCCI;
    }

    public List getListeEnvoyeeDR_DCCI() {
        return listeEnvoyeeDR_DCCI;
    }

    public void setListeDemChqMandatPersonne(List listeDemChqMandatPersonne) {
        this.listeDemChqMandatPersonne = listeDemChqMandatPersonne;
    }

    public List getListeDemChqMandatPersonne() {
        return listeDemChqMandatPersonne;
    }

    public void setListeDemandeursChqMandatPersonne(List listeDemandeursChqMandatPersonne) {
        this.listeDemandeursChqMandatPersonne = listeDemandeursChqMandatPersonne;
    }

    public List getListeDemandeursChqMandatPersonne() {
        return listeDemandeursChqMandatPersonne;
    }

    public void setListeDemandeChqADetruire(List listeDemandeChqADetruire) {
        this.listeDemandeChqADetruire = listeDemandeChqADetruire;
    }

    public List getListeDemandeChqADetruire() {
        return listeDemandeChqADetruire;
    }

    public void setListeDetailOperationChequier(List listeDetailOperationChequier) {
        this.listeDetailOperationChequier = listeDetailOperationChequier;
    }

    public List getListeDetailOperationChequier() {
        return listeDetailOperationChequier;
    }
}


