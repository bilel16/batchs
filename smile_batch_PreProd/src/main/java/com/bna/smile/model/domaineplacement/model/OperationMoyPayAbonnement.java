package com.bna.smile.model.domaineplacement.model;

import com.bna.commun.model.OperationMoyPay;
import com.oxia.fwk.core.ValueObject;

public class OperationMoyPayAbonnement extends ValueObject{

    private OperationMoyPay operationMoyPay;
    private ParamAbonnementement paramAbonnementement;
    
    public OperationMoyPayAbonnement() {
    }

    public void setOperationMoyPay(OperationMoyPay operationMoyPay) {
        this.operationMoyPay = operationMoyPay;
    }

    public OperationMoyPay getOperationMoyPay() {
        return operationMoyPay;
    }

    public void setParamAbonnementement(ParamAbonnementement paramAbonnementement) {
        this.paramAbonnementement = paramAbonnementement;
    }

    public ParamAbonnementement getParamAbonnementement() {
        return paramAbonnementement;
    }
}
