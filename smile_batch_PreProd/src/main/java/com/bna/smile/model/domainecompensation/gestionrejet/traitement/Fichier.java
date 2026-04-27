package com.bna.smile.model.domainecompensation.gestionrejet.traitement;

import java.util.Date;

public class Fichier {
   
   String nomFichier;
   String codeStructure;
   Date datePECFichier;
   int codeTraitFichier;
   Long codValVal;
   
    public Long getCodValVal() {
	return codValVal;
}
public void setCodValVal(Long codValVal) {
	this.codValVal = codValVal;
}
	public Fichier(String nomFichier,int codeTraitFichier){     
    this.nomFichier = nomFichier;
    this.codeTraitFichier = codeTraitFichier;   
    }
    public Fichier(){     
   
    }
    public void setNomFichier(String nomFichier) {
        this.nomFichier = nomFichier;
    }

    public String getNomFichier() {
        return nomFichier;
    }

    public void setCodeStructure(String codeStructure) {
        this.codeStructure = codeStructure;
    }

    public String getCodeStructure() {
        return codeStructure;
    }

    public void setDatePECFichier(Date datePECFichier) {
        this.datePECFichier = datePECFichier;
    }

    public Date getDatePECFichier() {
        return datePECFichier;
    }

    public void setCodeTraitFichier(int codeTraitFichier) {
        this.codeTraitFichier = codeTraitFichier;
    }

    public int getCodeTraitFichier() {
        return codeTraitFichier;
    }
}