package com.bna.smile.model.domaineplacement.model;

import com.oxia.fwk.core.ValueObject;

public class RecapMouvementPlac extends ValueObject{
    public RecapMouvementPlac() {
    }
    String libOperation;
    Long codStrcStrc;
    String numMatrUser;
    Long montantMouvement;
    String numeroCompte;
    String heureMouvement;
    Long codeOperation;
    Long numeroContratPlacement;
    Long numBonCaisse;

    public void setLibOperation(String libOperation) {
        this.libOperation = libOperation;
    }

    public String getLibOperation() {
        return libOperation;
    }

    public void setCodStrcStrc(Long codStrcStrc) {
        this.codStrcStrc = codStrcStrc;
    }

    public Long getCodStrcStrc() {
        return codStrcStrc;
    }

    public void setNumMatrUser(String numMatrUser) {
        this.numMatrUser = numMatrUser;
    }

    public String getNumMatrUser() {
        return numMatrUser;
    }

    public void setMontantMouvement(Long montantMouvement) {
        this.montantMouvement = montantMouvement;
    }

    public Long getMontantMouvement() {
        return montantMouvement;
    }

    public void setNumeroCompte(String numeroCompte) {
        this.numeroCompte = numeroCompte;
    }

    public String getNumeroCompte() {
        return numeroCompte;
    }

    public void setHeureMouvement(String heureMouvement) {
        this.heureMouvement = heureMouvement;
    }

    public String getHeureMouvement() {
        return heureMouvement;
    }

    public void setCodeOperation(Long codeOperation) {
        this.codeOperation = codeOperation;
    }

    public Long getCodeOperation() {
        return codeOperation;
    }

    public void setNumeroContratPlacement(Long numeroContratPlacement) {
        this.numeroContratPlacement = numeroContratPlacement;
    }

    public Long getNumeroContratPlacement() {
        return numeroContratPlacement;
    }

    public void setNumBonCaisse(Long numBonCaisse) {
        this.numBonCaisse = numBonCaisse;
    }

    public Long getNumBonCaisse() {
        return numBonCaisse;
    }
}
