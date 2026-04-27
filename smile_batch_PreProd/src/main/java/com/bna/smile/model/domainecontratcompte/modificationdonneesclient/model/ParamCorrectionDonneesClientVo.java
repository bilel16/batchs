package com.bna.smile.model.domainecontratcompte.modificationdonneesclient.model;

import com.bna.commun.model.Personne;
import com.oxia.fwk.core.ValueObject;

public class ParamCorrectionDonneesClientVo extends ValueObject{
    Personne personneModifie;
    Long codeStructure;
    Long matricule;
    
    public ParamCorrectionDonneesClientVo() {
    
    }

    public void setPersonneModifie(Personne personneModifie) {
        this.personneModifie = personneModifie;
    }

    public Personne getPersonneModifie() {
        return personneModifie;
    }

    public void setCodeStructure(Long codeStructure) {
        this.codeStructure = codeStructure;
    }

    public Long getCodeStructure() {
        return codeStructure;
    }

    public void setMatricule(Long matricule) {
        this.matricule = matricule;
    }

    public Long getMatricule() {
        return matricule;
    }
}
