package com.bna.smile.web.placement.view;

import com.bna.commun.model.ContratPlacement;


public class ContratPlacementView extends com.oxia.fwk.core.ValueObject implements java.io.Serializable {

    private String numSeqCpla;
    private String codPrdPrd; // produit placement
    private String libPrdPrd;
    private String codTypsSous; // catégorie souscripteur :: cas BC/CAT // necessaire pour le CRO, dans le cas de BC/CAT
    private String datCreCpla;
    private String montCapCpla;
    private String montActuCpla;
    private String datEcheCpla;
    private String codPintCpla;
    private String libPintCpla;
    private String codEtatCpla;
    private String datLiqCpla;
    private String boolAvalCpla;
    private String numTauiCpla;
    private String numBcCpla;
    private String numAnccCla;
    private ContratPlacement contratPlacement = new ContratPlacement();
    private String montTauInt;
    private String montTauIRC;
    private String montNetTauInt;
    private String tauIRC;
    private String montantCommissionAval;
    private String nombreTitrBTA;
    private String dateValeur;
    private String dateSouscription;
    private String pecBoolAvalCpla="0";
    private String datPrevArl;
    //données de l'interface de souscription contrat placement
    private String duree; 
    private String tauGeneral;
    private String strcCcptCpla;
    private String prdCcptCpla;
    private String numCcptCpla;
    private String etatCcptCpla;
    private boolean boolProvision;  // true si le solde du compte est suffisant 
    private boolean boolNotForcing;  // true si le produit du compte est 103 ou type produit (codTprdPrd) est Dinars Convertible
    private String nomClient ="";
    private String prenomClient ="";
    private String intituleCpt ="";
    private String soldeCompte;
    private String cleCpla;
    private String codPieceClient;
    private String numPieceClient;
    private String refDossierCB;
    private String dateValeurTaux;
    private String typeFaveurCpla; // cas taux indexé au TMM
    private String signeMargeCpla;
    private String numMargeCpla;
    private String montArlCpla;
    private String tmm;
    private String boolAvance;
    private String typeRenouvellement;
    private String numPlacRenouvele;
    private String datEchePlacRenouvele;
    private String codSbdvCpla;
    private boolean boolDatePermise = false;
    
    public ContratPlacementView() {
        numSeqCpla="";
        codPrdPrd="";
        libPrdPrd="";
        numSeqCpla="";
        datCreCpla="";
        montCapCpla="";
        montActuCpla="";
        datEcheCpla="";
        codPintCpla="";
        codEtatCpla="";
        datLiqCpla="";
        boolAvalCpla="";
        numTauiCpla="";
        numAnccCla="";
        nombreTitrBTA="";
        montantCommissionAval="";
        montTauInt="";
        montTauIRC="";
        montNetTauInt="";
        dateValeur="";
        contratPlacement = new ContratPlacement();
        pecBoolAvalCpla="0";
        duree ="";
        tauIRC="";
        codPieceClient ="";
        numPieceClient ="";
        refDossierCB ="";
        dateSouscription="";
        montArlCpla="";
        strcCcptCpla = "";
        prdCcptCpla = "";
        numCcptCpla = "";
        libPintCpla = "";
        tauGeneral="";
        boolAvance="";
    }

    public void clear() {
        numSeqCpla="";
        datCreCpla="";
        montCapCpla="";
        montActuCpla="";
        datEcheCpla="";
        codPintCpla="";
        codEtatCpla="";
        datLiqCpla="";
        boolAvalCpla="";
        numTauiCpla="";
        numAnccCla="";
        nombreTitrBTA="";
        montantCommissionAval="";
        montTauInt="";
        montTauIRC="";
        montNetTauInt="";
        dateValeur="";
        contratPlacement = new ContratPlacement();
        pecBoolAvalCpla="0";
        duree ="";
        tauIRC="";
        codPrdPrd="";
        libPrdPrd="";
        codPieceClient ="";
        numPieceClient ="";
        refDossierCB ="";
        dateSouscription="";
        montArlCpla="";
        strcCcptCpla = "";
        prdCcptCpla = "";
        numCcptCpla = "";
        libPintCpla = "";
        tauGeneral="";
        boolAvance="";
    }

    public void setNumSeqCpla(String numSeqCpla) {
        this.numSeqCpla = numSeqCpla;
    }

    public String getNumSeqCpla() {
        return numSeqCpla;
    }

    public void setCodPrdPrd(String codPrdPrd) {
        this.codPrdPrd = codPrdPrd;
    }

    public String getCodPrdPrd() {
        return codPrdPrd;
    }

    public void setCodTypsSous(String codTypsSous) {
        this.codTypsSous = codTypsSous;
    }

    public String getCodTypsSous() {
        return codTypsSous;
    }

    public void setDatCreCpla(String datCreCpla) {
        this.datCreCpla = datCreCpla;
    }

    public String getDatCreCpla() {
        return datCreCpla;
    }

    public void setMontCapCpla(String montCapCpla) {
        this.montCapCpla = montCapCpla;
    }

    public String getMontCapCpla() {
        return montCapCpla;
    }

    public void setMontActuCpla(String montActuCpla) {
        this.montActuCpla = montActuCpla;
    }

    public String getMontActuCpla() {
        return montActuCpla;
    }

    public void setDatEcheCpla(String datEcheCpla) {
        this.datEcheCpla = datEcheCpla;
    }

    public String getDatEcheCpla() {
        return datEcheCpla;
    }

    public void setCodPintCpla(String codPintCpla) {
        this.codPintCpla = codPintCpla;
    }

    public String getCodPintCpla() {
        return codPintCpla;
    }

    public void setCodEtatCpla(String codEtatCpla) {
        this.codEtatCpla = codEtatCpla;
    }

    public String getCodEtatCpla() {
        return codEtatCpla;
    }

    public void setDatLiqCpla(String datLiqCpla) {
        this.datLiqCpla = datLiqCpla;
    }

    public String getDatLiqCpla() {
        return datLiqCpla;
    }

    public void setBoolAvalCpla(String boolAvalCpla) {
        this.boolAvalCpla = boolAvalCpla;
    }

    public String getBoolAvalCpla() {
        return boolAvalCpla;
    }

    public void setNumTauiCpla(String numTauiCpla) {
        this.numTauiCpla = numTauiCpla;
    }

    public String getNumTauiCpla() {
        return numTauiCpla;
    }

    public void setNumBcCpla(String numBcCpla) {
        this.numBcCpla = numBcCpla;
    }

    public String getNumBcCpla() {
        return numBcCpla;
    }

    public void setNumAnccCla(String numAnccCla) {
        this.numAnccCla = numAnccCla;
    }

    public String getNumAnccCla() {
        return numAnccCla;
    }

    public void setContratPlacement(ContratPlacement contratPlacement) {
        this.contratPlacement = contratPlacement;
    }

    public ContratPlacement getContratPlacement() {
        return contratPlacement;
    }

    public void setMontTauInt(String montTauInt) {
        this.montTauInt = montTauInt;
    }

    public String getMontTauInt() {
        return montTauInt;
    }

    public void setMontTauIRC(String montTauIRC) {
        this.montTauIRC = montTauIRC;
    }

    public String getMontTauIRC() {
        return montTauIRC;
    }

    public void setMontNetTauInt(String montNetTauInt) {
        this.montNetTauInt = montNetTauInt;
    }

    public String getMontNetTauInt() {
        return montNetTauInt;
    }

    public void setTauIRC(String tauIRC) {
        this.tauIRC = tauIRC;
    }

    public String getTauIRC() {
        return tauIRC;
    }

    public void setMontantCommissionAval(String montantCommissionAval) {
        this.montantCommissionAval = montantCommissionAval;
    }

    public String getMontantCommissionAval() {
        return montantCommissionAval;
    }

    public void setNombreTitrBTA(String nombreTitrBTA) {
        this.nombreTitrBTA = nombreTitrBTA;
    }

    public String getNombreTitrBTA() {
        return nombreTitrBTA;
    }

    public void setDateValeur(String dateValeur) {
        this.dateValeur = dateValeur;
    }

    public String getDateValeur() {
        return dateValeur;
    }

    public void setPecBoolAvalCpla(String pecBoolAvalCpla) {
        this.pecBoolAvalCpla = pecBoolAvalCpla;
    }

    public String getPecBoolAvalCpla() {
        return pecBoolAvalCpla;
    }

    public void setLibPrdPrd(String libPrdPrd) {
        this.libPrdPrd = libPrdPrd;
    }

    public String getLibPrdPrd() {
        return libPrdPrd;
    }

    public void setDuree(String duree) {
        this.duree = duree;
    }

    public String getDuree() {
        return duree;
    }

    public void setDatPrevArl(String datPrevArl) {
        this.datPrevArl = datPrevArl;
    }

    public String getDatPrevArl() {
        return datPrevArl;
    }

    public void setTauGeneral(String tauGeneral) {
        this.tauGeneral = tauGeneral;
    }

    public String getTauGeneral() {
        return tauGeneral;
    }

    public void setStrcCcptCpla(String strcCcptCpla) {
        this.strcCcptCpla = strcCcptCpla;
    }

    public String getStrcCcptCpla() {
        return strcCcptCpla;
    }

    public void setPrdCcptCpla(String prdCcptCpla) {
        this.prdCcptCpla = prdCcptCpla;
    }

    public String getPrdCcptCpla() {
        return prdCcptCpla;
    }

    public void setNumCcptCpla(String numCcptCpla) {
        this.numCcptCpla = numCcptCpla;
    }

    public String getNumCcptCpla() {
        return numCcptCpla;
    }

    public void setEtatCcptCpla(String etatCcptCpla) {
        this.etatCcptCpla = etatCcptCpla;
    }

    public String getEtatCcptCpla() {
        return etatCcptCpla;
    }

    public void setBoolProvision(boolean boolProvision) {
        this.boolProvision = boolProvision;
    }

    public boolean isBoolProvision() {
        return boolProvision;
    }

    public void setNomClient(String nomClient) {
        this.nomClient = nomClient;
    }

    public String getNomClient() {
        return nomClient;
    }

    public void setPrenomClient(String prenomClient) {
        this.prenomClient = prenomClient;
    }

    public String getPrenomClient() {
        return prenomClient;
    }

    public void setSoldeCompte(String soldeCompte) {
        this.soldeCompte = soldeCompte;
    }

    public String getSoldeCompte() {
        return soldeCompte;
    }

    public void setLibPintCpla(String libPintCpla) {
        this.libPintCpla = libPintCpla;
    }

    public String getLibPintCpla() {
        return libPintCpla;
    }

    public void setCleCpla(String cleCpla) {
        this.cleCpla = cleCpla;
    }

    public String getCleCpla() {
        return cleCpla;
    }
    
    public void setRefDossierCB(String refDossierCB) {
        this.refDossierCB = refDossierCB;
    }

    public String getRefDossierCB() {
        return refDossierCB;
    }

    public void setCodPieceClient(String codPieceClient) {
        this.codPieceClient = codPieceClient;
    }

    public String getCodPieceClient() {
        return codPieceClient;
    }

    public void setNumPieceClient(String numPieceClient) {
        this.numPieceClient = numPieceClient;
    }

    public String getNumPieceClient() {
        return numPieceClient;
    }

    public void setDateSouscription(String dateSouscription) {
        this.dateSouscription = dateSouscription;
    }

    public String getDateSouscription() {
        return dateSouscription;
    }

    public void setDateValeurTaux(String dateValeurTaux) {
        this.dateValeurTaux = dateValeurTaux;
    }

    public String getDateValeurTaux() {
        return dateValeurTaux;
    }

    public void setTypeFaveurCpla(String typeFaveurCpla) {
        this.typeFaveurCpla = typeFaveurCpla;
    }

    public String getTypeFaveurCpla() {
        return typeFaveurCpla;
    }

    public void setSigneMargeCpla(String signeMargeCpla) {
        this.signeMargeCpla = signeMargeCpla;
    }

    public String getSigneMargeCpla() {
        return signeMargeCpla;
    }

    public void setNumMargeCpla(String numMargeCpla) {
        this.numMargeCpla = numMargeCpla;
    }

    public String getNumMargeCpla() {
        return numMargeCpla;
    }


    public void setMontArlCpla(String montArlCpla) {
        this.montArlCpla = montArlCpla;
    }

    public String getMontArlCpla() {
        return montArlCpla;
    }


    public void setTmm(String tmm) {
        this.tmm = tmm;
    }

    public String getTmm() {
        return tmm;
    }

    public void setBoolAvance(String boolAvance) {
        this.boolAvance = boolAvance;
    }

    public String getBoolAvance() {
        return boolAvance;
    }

    public void setIntituleCpt(String intituleCpt) {
        this.intituleCpt = intituleCpt;
    }

    public String getIntituleCpt() {
        return intituleCpt;
    }

    public void setBoolNotForcing(boolean boolNotForcing) {
        this.boolNotForcing = boolNotForcing;
    }

    public boolean isBoolNotForcing() {
        return boolNotForcing;
    }

    public void setTypeRenouvellement(String typeRenouvellement) {
        this.typeRenouvellement = typeRenouvellement;
    }

    public String getTypeRenouvellement() {
        return typeRenouvellement;
    }

    public void setNumPlacRenouvele(String numPlacRenouvele) {
        this.numPlacRenouvele = numPlacRenouvele;
    }

    public String getNumPlacRenouvele() {
        return numPlacRenouvele;
    }

    public void setDatEchePlacRenouvele(String datEchePlacRenouvele) {
        this.datEchePlacRenouvele = datEchePlacRenouvele;
    }

    public String getDatEchePlacRenouvele() {
        return datEchePlacRenouvele;
    }

    public void setCodSbdvCpla(String codSbdvCpla) {
        this.codSbdvCpla = codSbdvCpla;
    }

    public String getCodSbdvCpla() {
        return codSbdvCpla;
    }

    public void setBoolDatePermise(boolean boolDatePermise) {
        this.boolDatePermise = boolDatePermise;
    }

    public boolean isBoolDatePermise() {
        return boolDatePermise;
    }
}
