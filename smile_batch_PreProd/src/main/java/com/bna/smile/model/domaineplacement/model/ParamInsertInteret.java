package com.bna.smile.model.domaineplacement.model;

import com.bna.commun.model.InteretServi;
import com.bna.commun.model.OperationMoyPay;
import com.oxia.fwk.core.ValueObject;

public class ParamInsertInteret extends ValueObject {
    private OperationMoyPay operationMoyPay = new OperationMoyPay();
    private InteretServi interetServi=new InteretServi();
    private Long montAbonnCorrectionAnnee;
    private Long montAbonnCorrectionMois;
    
    public ParamInsertInteret() {
    }

    public void setOperationMoyPay(OperationMoyPay operationMoyPay) {
        this.operationMoyPay = operationMoyPay;
    }

    public OperationMoyPay getOperationMoyPay() {
        return operationMoyPay;
    }

    public void setInteretServi(InteretServi interetServi) {
        this.interetServi = interetServi;
    }

    public InteretServi getInteretServi() {
        return interetServi;
    }

    public void setMontAbonnCorrectionAnnee(Long montAbonnCorrectionAnnee) {
        this.montAbonnCorrectionAnnee = montAbonnCorrectionAnnee;
    }

    public Long getMontAbonnCorrectionAnnee() {
        return montAbonnCorrectionAnnee;
    }

    public void setMontAbonnCorrectionMois(Long montAbonnCorrectionMois) {
        this.montAbonnCorrectionMois = montAbonnCorrectionMois;
    }

    public Long getMontAbonnCorrectionMois() {
        return montAbonnCorrectionMois;
    }
}
