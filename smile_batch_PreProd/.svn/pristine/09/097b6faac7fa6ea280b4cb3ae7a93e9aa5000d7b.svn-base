package com.bna.smile.model.domainecontratcompte.moyensPaiement.model;


import java.util.Date;

import com.bna.commun.model.ContratCptId;
import com.bna.smile.model.domainecommun.model.PersonneStrc;
import com.oxia.fwk.core.ValueObject;

public class ParamRechercheDemandeCarte extends ValueObject {

    private Long codAgence;
    private PersonneStrc porteur;
    private ContratCptId contratCptId;
    private String numRecherche;// num carte ou demande
    private String [] etatsRecherche;//etats carte ou demande
    private Date dateDebutInf;
    private Date dateDebutSup;
    private Date dateFinInf;
    private Date dateFinSup;
    private Long boolAnnulerRenouv;//pour critère carte non annulées
    private Date dateDernOper;//date dernière opération pour critère carte mise pour destruction apres 3 mois de reception sans remise
    private Long boolModifPlafond;//pour critère demande de modification

    public ParamRechercheDemandeCarte() {
    }


    public void setPorteur(PersonneStrc porteur) {
        this.porteur = porteur;
    }

    public PersonneStrc getPorteur() {
        return porteur;
    }   

    public void setContratCptId(ContratCptId contratCptId) {
        this.contratCptId = contratCptId;
    }

    public ContratCptId getContratCptId() {
        return contratCptId;
    }

    public void setCodAgence(Long codAgence) {
        this.codAgence = codAgence;
    }

    public Long getCodAgence() {
        return codAgence;
    }

    public void setBoolAnnulerRenouv(Long boolAnnulerRenouv) {
        this.boolAnnulerRenouv = boolAnnulerRenouv;
    }

    public Long getBoolAnnulerRenouv() {
        return boolAnnulerRenouv;
    }

    public void setNumRecherche(String numRecherche) {
        this.numRecherche = numRecherche;
    }

    public String getNumRecherche() {
        return numRecherche;
    }

    public void setEtatsRecherche(String[] etatsRecherche) {
        this.etatsRecherche = etatsRecherche;
    }

    public String[] getEtatsRecherche() {
        return etatsRecherche;
    }

    public void setDateFinInf(Date dateFinInf) {
        this.dateFinInf = dateFinInf;
    }

    public Date getDateFinInf() {
        return dateFinInf;
    }

    public void setDateFinSup(Date dateFinSup) {
        this.dateFinSup = dateFinSup;
    }

    public Date getDateFinSup() {
        return dateFinSup;
    }

    public void setDateDebutInf(Date dateDebutInf) {
        this.dateDebutInf = dateDebutInf;
    }

    public Date getDateDebutInf() {
        return dateDebutInf;
    }

    public void setDateDebutSup(Date dateDebutSup) {
        this.dateDebutSup = dateDebutSup;
    }

    public Date getDateDebutSup() {
        return dateDebutSup;
    }

    public void setDateDernOper(Date dateDernOper) {
        this.dateDernOper = dateDernOper;
    }

    public Date getDateDernOper() {
        return dateDernOper;
    }

    public void setBoolModifPlafond(Long boolModifPlafond) {
        this.boolModifPlafond = boolModifPlafond;
    }

    public Long getBoolModifPlafond() {
        return boolModifPlafond;
    }
}
