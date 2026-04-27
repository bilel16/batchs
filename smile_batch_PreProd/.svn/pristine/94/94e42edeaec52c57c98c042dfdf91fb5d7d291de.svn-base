package com.bna.smile.model.domaineplacement.model;

import java.util.Date;

import com.bna.commun.model.AvancRembLiquid;
import com.bna.commun.model.ContratPlacement;
import com.bna.commun.model.DemandeDecision;
import com.bna.commun.model.DetailsOperationPlacement;
import com.bna.commun.model.InteretServi;
import com.bna.commun.model.MouvementInterne;
import com.bna.commun.model.Structure;
import com.oxia.fwk.core.ValueObject;

public class ParamContratPlacement extends ValueObject {

    private ContratPlacement contratPlacement=new ContratPlacement();
    private Long numSeqBc; // la ligne des carnets qui contiennent le numéro BC (table BonDeCaisse)
    private DetailsOperationPlacement detailsOperationPlacement=new DetailsOperationPlacement();
    private InteretServi interetServi=new InteretServi();
    private MouvementInterne mouvementInterne = new MouvementInterne();  
    private DemandeDecision demandeDecision=new DemandeDecision();    
    private AvancRembLiquid avancRembLiquid =new AvancRembLiquid(); 
    private Date dateComptRenouvel;
    private int nbrCptPlacRenouvelable = 0;
    private int nbrCptPlac = 0;
    private Double sommePlacement = Double.valueOf("0");
    private boolean finBatchStructure = false;
    private Structure agence =new Structure();
    private String typeOperation;
    private Date dateValeur;
    private boolean operationForce;
    private Long montAbonnCorrectAnnee;
    private Long montAbonnCorrectMois;
    
    
    public ParamContratPlacement() {
    }

    public void setContratPlacement(ContratPlacement contratPlacement) {
        this.contratPlacement = contratPlacement;
    }

    public ContratPlacement getContratPlacement() {
        return contratPlacement;
    }

    public void setNumSeqBc(Long numSeqBc) {
        this.numSeqBc = numSeqBc;
    }

    public Long getNumSeqBc() {
        return numSeqBc;
    }

    public void setDetailsOperationPlacement(DetailsOperationPlacement detailsOperationPlacement) {
        this.detailsOperationPlacement = detailsOperationPlacement;
    }

    public DetailsOperationPlacement getDetailsOperationPlacement() {
        return detailsOperationPlacement;
    }

    public void setInteretServi(InteretServi interetServi) {
        this.interetServi = interetServi;
    }

    public InteretServi getInteretServi() {
        return interetServi;
    }

    public void setDemandeDecision(DemandeDecision demandeDecision) {
        this.demandeDecision = demandeDecision;
    }

    public DemandeDecision getDemandeDecision() {
        return demandeDecision;
    }

    public void setAvancRembLiquid(AvancRembLiquid avancRembLiquid) {
        this.avancRembLiquid = avancRembLiquid;
    }

    public AvancRembLiquid getAvancRembLiquid() {
        return avancRembLiquid;
    }

    public void setDateComptRenouvel(Date dateComptRenouvel) {
        this.dateComptRenouvel = dateComptRenouvel;
    }

    public Date getDateComptRenouvel() {
        return dateComptRenouvel;
    }

    public void setNbrCptPlac(int nbrCptPlac) {
        this.nbrCptPlac = nbrCptPlac;
    }

    public int getNbrCptPlac() {
        return nbrCptPlac;
    }

    public void setSommePlacement(Double sommePlacement) {
        this.sommePlacement = sommePlacement;
    }

    public Double getSommePlacement() {
        return sommePlacement;
    }

    public void setFinBatchStructure(boolean finBatchStructure) {
        this.finBatchStructure = finBatchStructure;
    }

    public boolean isFinBatchStructure() {
        return finBatchStructure;
    }

    public void setAgence(Structure agence) {
        this.agence = agence;
    }

    public Structure getAgence() {
        return agence;
    }

    public void setTypeOperation(String typeOperation) {
        this.typeOperation = typeOperation;
    }

    public String getTypeOperation() {
        return typeOperation;
    }

    public void setNbrCptPlacRenouvelable(int nbrCptPlacRenouvelable) {
        this.nbrCptPlacRenouvelable = nbrCptPlacRenouvelable;
    }

    public int getNbrCptPlacRenouvelable() {
        return nbrCptPlacRenouvelable;
    }

    public void setDateValeur(Date dateValeur) {
        this.dateValeur = dateValeur;
    }

    public Date getDateValeur() {
        return dateValeur;
    }

    public void setOperationForce(boolean operationForce) {
        this.operationForce = operationForce;
    }

    public boolean isOperationForce() {
        return operationForce;
    }

    public void setMouvementInterne(MouvementInterne mouvementInterne) {
        this.mouvementInterne = mouvementInterne;
    }

    public MouvementInterne getMouvementInterne() {
        return mouvementInterne;
    }

    public void setMontAbonnCorrectAnnee(Long montAbonnCorrectAnnee) {
        this.montAbonnCorrectAnnee = montAbonnCorrectAnnee;
    }

    public Long getMontAbonnCorrectAnnee() {
        return montAbonnCorrectAnnee;
    }

    public void setMontAbonnCorrectMois(Long montAbonnCorrectMois) {
        this.montAbonnCorrectMois = montAbonnCorrectMois;
    }

    public Long getMontAbonnCorrectMois() {
        return montAbonnCorrectMois;
    }
}
