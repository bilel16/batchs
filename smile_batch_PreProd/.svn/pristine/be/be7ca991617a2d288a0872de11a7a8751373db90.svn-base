package com.bna.smile.model.domaineplacement.model;

import java.util.Date;

import com.bna.commun.model.AbonnementPlacement;
import com.bna.commun.model.AvancRembLiquid;
import com.bna.commun.model.ContratPlacement;
import com.oxia.fwk.core.ValueObject;


public class ParamAbonnementement extends ValueObject{
   
    private Date datDebAbpl;
    private Date datFinAbpl;
    private Date datDebArl;
    private Date datPrevAbpl;  ///*** date prevue de remboursement
    private Long montTotAbpl;  ///*** montant placement ou avance
    private Long montItotAbpl; ///*** montant total interet
    private Double numTauiCpla;
    private String typeOperation; ///*** S:souscription, A:avance, LA liquidation anticipée
     private boolean opRenouvellemnt; 
    private Long numSeqCpla;
    private Long numSeqArl;
    private Long montRembAbpl;  ///*** montant trop percu (Remboursement anticipé)
    private String typeInteret; ///*** I:indexé
    private Date dateLiquidationAnticipe;
    private ContratPlacement contratPlacement;
    private AvancRembLiquid avancRembLiquid;
    private String etatAbonnement;
    private AbonnementPlacement abonnementPlacement; 

    // traitement de la lquidation anticipée...
    private Double montDiffeInteretLiq;
    private Double montInteretMoisLiq;
    private Double montInteretCorrecAnneesPrec;
    private Double montInteretDiffAnneesCourante;
    private Double montInteretsAnnulle;
    private Double montAverserClt;
    private Double montAPercevoirClt;
    private Double montInteretAbonnPostCompte;
    private Double montRetenu;
    private Long   dureeReelPlc;
    private Double interetsReelPlc;
    private Long codeOperation;
    private Date dateDernierIntServi;
    private Double montInteretServis;
    private Double montInteretAVerserPercevoir;


    private Long montIpanReaj; ///***(15) montant non abonné (fraction du mois)
    private Long montIcomReaj; ///***(14) Somme des montants sur abonnement mensuels (entre la date prevu de remb et la date de remb)
    private Long montInetReaj; ///***(16) montant des interets sur avance remboursée en retard (16=15+14)

    

    private Long montAcmrReaj; ///***(12) montant sur abonnement mensuels (dernier remb)
    private Long montRisaReaj; ///***(13) montant des interets sur avance remboursée en anticipé
    
    private Date dateValeurLiquidation; 
    private Date dateCompAgence; 
    private Double tauxInteretPlacement;

    private Double montIntCorrectAbonnMois; // souscription SBDV

    public ParamAbonnementement() {
    }

    public void setDatDebAbpl(Date datDebAbpl) {
        this.datDebAbpl = datDebAbpl;
    }

    public Date getDatDebAbpl() {
        return datDebAbpl;
    }

    public void setDatFinAbpl(Date datFinAbpl) {
        this.datFinAbpl = datFinAbpl;
    }

    public Date getDatFinAbpl() {
        return datFinAbpl;
    }

    public void setMontTotAbpl(Long montTotAbpl) {
        this.montTotAbpl = montTotAbpl;
    }

    public Long getMontTotAbpl() {
        return montTotAbpl;
    }

    public void setNumTauiCpla(Double numTauiCpla) {
        this.numTauiCpla = numTauiCpla;
    }

    public Double getNumTauiCpla() {
        return numTauiCpla;
    }

    public void setTypeOperation(String typeOperation) {
        this.typeOperation = typeOperation;
    }

    public String getTypeOperation() {
        return typeOperation;
    }

    public void setNumSeqCpla(Long numSeqCpla) {
        this.numSeqCpla = numSeqCpla;
    }

    public Long getNumSeqCpla() {
        return numSeqCpla;
    }

    public void setNumSeqArl(Long numSeqArl) {
        this.numSeqArl = numSeqArl;
    }

    public Long getNumSeqArl() {
        return numSeqArl;
    }

    public void setTypeInteret(String typeInteret) {
        this.typeInteret = typeInteret;
    }

    public String getTypeInteret() {
        return typeInteret;
    }

    public void setDateLiquidationAnticipe(Date dateLiquidationAnticipe) {
        this.dateLiquidationAnticipe = dateLiquidationAnticipe;
    }

    public Date getDateLiquidationAnticipe() {
        return dateLiquidationAnticipe;
    }

    public void setContratPlacement(ContratPlacement contratPlacement) {
        this.contratPlacement = contratPlacement;
    }

    public ContratPlacement getContratPlacement() {
        return contratPlacement;
    }

    public void setAvancRembLiquid(AvancRembLiquid avancRembLiquid) {
        this.avancRembLiquid = avancRembLiquid;
    }

    public AvancRembLiquid getAvancRembLiquid() {
        return avancRembLiquid;
    }

    public void setEtatAbonnement(String etatAbonnement) {
        this.etatAbonnement = etatAbonnement;
    }

    public String getEtatAbonnement() {
        return etatAbonnement;
    }

    public void setAbonnementPlacement(AbonnementPlacement abonnementPlacement) {
        this.abonnementPlacement = abonnementPlacement;
    }

    public AbonnementPlacement getAbonnementPlacement() {
        return abonnementPlacement;
    }

    public void setMontItotAbpl(Long montItotAbpl) {
        this.montItotAbpl = montItotAbpl;
    }

    public Long getMontItotAbpl() {
        return montItotAbpl;
    }



    public void setMontDiffeInteretLiq(Double montDiffeInteretLiq) {
        this.montDiffeInteretLiq = montDiffeInteretLiq;
    }

    public Double getMontDiffeInteretLiq() {
        return montDiffeInteretLiq;
    }

    public void setMontInteretMoisLiq(Double montInteretMoisLiq) {
        this.montInteretMoisLiq = montInteretMoisLiq;
    }

    public Double getMontInteretMoisLiq() {
        return montInteretMoisLiq;
    }

    public void setMontInteretCorrecAnneesPrec(Double montInteretCorrecAnneesPrec) {
        this.montInteretCorrecAnneesPrec = montInteretCorrecAnneesPrec;
    }

    public Double getMontInteretCorrecAnneesPrec() {
        return montInteretCorrecAnneesPrec;
    }

    public void setMontInteretDiffAnneesCourante(Double montInteretDiffAnneesCourante) {
        this.montInteretDiffAnneesCourante = montInteretDiffAnneesCourante;
    }

    public Double getMontInteretDiffAnneesCourante() {
        return montInteretDiffAnneesCourante;
    }

    public void setMontInteretsAnnulle(Double montInteretsAnnulle) {
        this.montInteretsAnnulle = montInteretsAnnulle;
    }

    public Double getMontInteretsAnnulle() {
        return montInteretsAnnulle;
    }

    public void setDureeReelPlc(Long dureeReelPlc) {
        this.dureeReelPlc = dureeReelPlc;
    }

    public Long getDureeReelPlc() {
        return dureeReelPlc;
    }


    public void setInteretsReelPlc(Double interetsReelPlc) {
        this.interetsReelPlc = interetsReelPlc;
    }

    public Double getInteretsReelPlc() {
        return interetsReelPlc;
    }

    public void setMontAPercevoirClt(Double montAPercevoirClt) {
        this.montAPercevoirClt = montAPercevoirClt;
    }

    public Double getMontAPercevoirClt() {
        return montAPercevoirClt;
    }

 

    public void setMontRetenu(Double montRetenu) {
        this.montRetenu = montRetenu;
    }

    public Double getMontRetenu() {
        return montRetenu;
    }

    public void setCodeOperation(Long codeOperation) {
        this.codeOperation = codeOperation;
    }

    public Long getCodeOperation() {
        return codeOperation;
    }

    public void setMontAverserClt(Double montAverserClt) {
        this.montAverserClt = montAverserClt;
    }

    public Double getMontAverserClt() {
        return montAverserClt;
    }


    public void setDatPrevAbpl(Date datPrevAbpl) {
        this.datPrevAbpl = datPrevAbpl;
    }

    public Date getDatPrevAbpl() {
        return datPrevAbpl;
    }

    public void setDatDebArl(Date datDebArl) {
        this.datDebArl = datDebArl;
    }

    public Date getDatDebArl() {
        return datDebArl;
    }

    public void setMontRembAbpl(Long montRembAbpl) {
        this.montRembAbpl = montRembAbpl;
    }

    public Long getMontRembAbpl() {
        return montRembAbpl;
    }

    public void setMontIpanReaj(Long montIpanReaj) {
        this.montIpanReaj = montIpanReaj;
    }

    public Long getMontIpanReaj() {
        return montIpanReaj;
    }

    public void setMontIcomReaj(Long montIcomReaj) {
        this.montIcomReaj = montIcomReaj;
    }

    public Long getMontIcomReaj() {
        return montIcomReaj;
    }

    public void setMontInetReaj(Long montInetReaj) {
        this.montInetReaj = montInetReaj;
    }

    public Long getMontInetReaj() {
        return montInetReaj;
    }


    public void setDateDernierIntServi(Date dateDernierIntServi) {
        this.dateDernierIntServi = dateDernierIntServi;
    }

    public Date getDateDernierIntServi() {
        return dateDernierIntServi;
    }

    public void setMontInteretServis(Double montInteretServis) {
        this.montInteretServis = montInteretServis;
    }

    public Double getMontInteretServis() {
        return montInteretServis;
    }

    public void setMontInteretAbonnPostCompte(Double montInteretAbonnPostCompte) {
        this.montInteretAbonnPostCompte = montInteretAbonnPostCompte;
    }

    public Double getMontInteretAbonnPostCompte() {
        return montInteretAbonnPostCompte;
    }


    public void setMontAcmrReaj(Long montAcmrReaj) {
        this.montAcmrReaj = montAcmrReaj;
    }

    public Long getMontAcmrReaj() {
        return montAcmrReaj;
    }

    public void setMontRisaReaj(Long montRisaReaj) {
        this.montRisaReaj = montRisaReaj;
    }

    public Long getMontRisaReaj() {
        return montRisaReaj;
    }

    public void setDateValeurLiquidation(Date dateValeurLiquidation) {
        this.dateValeurLiquidation = dateValeurLiquidation;
    }

    public Date getDateValeurLiquidation() {
        return dateValeurLiquidation;
    }

    public void setOpRenouvellemnt(boolean opRenouvellemnt) {
        this.opRenouvellemnt = opRenouvellemnt;
    }

    public boolean isOpRenouvellemnt() {
        return opRenouvellemnt;
    }

    public void setMontInteretAVerserPercevoir(Double montInteretAVerserPercevoir) {
        this.montInteretAVerserPercevoir = montInteretAVerserPercevoir;
    }

    public Double getMontInteretAVerserPercevoir() {
        return montInteretAVerserPercevoir;
    }

    public void setDateCompAgence(Date dateCompAgence) {
        this.dateCompAgence = dateCompAgence;
    }

    public Date getDateCompAgence() {
        return dateCompAgence;
    }

    public void setTauxInteretPlacement(Double tauxInteretPlacement) {
        this.tauxInteretPlacement = tauxInteretPlacement;
    }

    public Double getTauxInteretPlacement() {
        return tauxInteretPlacement;
    }

    public void setMontIntCorrectAbonnMois(Double montIntCorrectAbonnMois) {
        this.montIntCorrectAbonnMois = montIntCorrectAbonnMois;
    }

    public Double getMontIntCorrectAbonnMois() {
        return montIntCorrectAbonnMois;
    }
}
