package com.bna.smile.web.moyenPaiement.certificationCheque.forms;


import com.bna.commun.model.CertificationCheques;
import com.bna.commun.model.CoTitulaire;
import com.bna.commun.model.ContratCpt;
import com.bna.commun.model.Personne;
import com.bna.smile.web.commun.model.PersonneDemandeur;
import com.bna.smile.web.commun.model.Pouvoir;
import com.bna.smile.web.commun.view.ContratView;
import com.bna.smile.web.commun.view.InitialisationView;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

public class CertificationChequeForm extends ActionForm {

    private String libelleOperation;
    private String codeOperation;
    private String codeTache;

    private String etatContrat;
    private ContratView contratView = new ContratView();
    private InitialisationView initialisationView = new InitialisationView();
    private PersonneDemandeur personneDemandeur = new PersonneDemandeur();
    private String soldeFinal;

    private String codAgPayement;
    private String typeDemandeur;

    private String montRetrCcpt;
    private ContratCpt contratCpt = new ContratCpt();

    // informations de la demande de certification
    private String numCheque;
    private String typeCheque;
    private String montantCertif;
    private String numCertCchq;
    private String dateCertCchq;
    private String etatDemCchq;
    private String codLieuCchq;
    private String datRestCchq;
    private String datAnnuCchq;
    private String datPayCchq;
    private String mntFraisCchq;
    private String mntTvaCchq;
    private String datValCchq;
    private String nomTireCchq;


    private boolean verifOpposition;
    private String alertContrat;
    private String alertDemandeur;
    private String motifRejet = "0";
    private String numeroMandatChoisi;
    private String libelleConfirmation;
    private String certDeplacee;
    private Collection listeDemandesChequesCertifies;
    private String numDemandeChoisi;
    private String numDemandeChoisie;
    private String numDemandeAffichee;
    private String choix;
    private CertificationCheques certificationCheques;
    private Set listDetailOperMoyPai;
    private String forcage = "false";
    private String condBanque="";

    // page de consultation;
    private String choixEtatDemande;
    private String typePieceId;
    private String numPieceId;
    private String codStrcRech;
    private String codPrdRech;
    private String numCcptRech;
    private String numChequeRech;
    private String dateDebut;
    private String dateFin;
    private String etatDemCertifChoisie;
    private String datedemCertChqChoisie;

    private Pouvoir pouvoir;

    public CertificationChequeForm() {
    }

    public void clearForm() {

        contratView.clear();
        contratCpt = new ContratCpt();
        personneDemandeur.clear();
        montRetrCcpt = "";
        contratCpt = null;
        //codeOperation = "";        
        alertContrat = "";
        alertDemandeur = "";
        typeDemandeur = "";
        numeroMandatChoisi = "";
        libelleConfirmation = "";
        motifRejet = "0";
        // info de la demande
        numCheque = "";
        //typeCheque="C";
        montantCertif = "";
        numCertCchq = "";
        //dateCertCchq= "";
        // etatDemCchq="0";
        //codLieuCchq="1";
        datRestCchq = "";
        datAnnuCchq = "";
        datPayCchq = "";
        mntFraisCchq = "";
        mntTvaCchq = "";
        nomTireCchq = "";
        datValCchq = "";
        forcage = "false";
        condBanque="";

    }

    public void clearFormListeCertDeplacee() {
        certDeplacee = "";
        listeDemandesChequesCertifies = null;
        numDemandeChoisi = "";
        numDemandeChoisie = "";
        numDemandeAffichee = "";
        contratView = null;
        choix = "";
        certificationCheques = null;

    }

    public void clearFromConsultation() {
        choixEtatDemande = "";
        typePieceId = "";
        numPieceId = "";
        codStrcRech = "";
        codPrdRech = "";
        numCcptRech = "";
        numChequeRech = "";
        dateDebut = "";
        dateFin = "";
        etatDemCertifChoisie = "";
        datedemCertChqChoisie = "";

    }

    public void setVerifOpposition(boolean verifOpposition) {
        this.verifOpposition = verifOpposition;
    }

    public boolean isVerifOpposition() {
        return verifOpposition;
    }

    public void setMontRetrCcpt(String montRetrCcpt) {
        this.montRetrCcpt = montRetrCcpt;
    }

    public String getMontRetrCcpt() {
        return montRetrCcpt;
    }

    public void setContratView(ContratView contratView) {
        this.contratView = contratView;
    }

    public ContratView getContratView() {
        return contratView;
    }

    public void setLibelleOperation(String libelleOperation) {
        this.libelleOperation = libelleOperation;
    }

    public String getLibelleOperation() {
        return libelleOperation;
    }

    public void setCodeOperation(String codeOperation) {
        this.codeOperation = codeOperation;
    }

    public String getCodeOperation() {
        return codeOperation;
    }


    public void setEtatContrat(String etatContrat) {
        this.etatContrat = etatContrat;
    }

    public String getEtatContrat() {
        return etatContrat;
    }

    public void setAlertContrat(String alertContrat) {
        this.alertContrat = alertContrat;
    }

    public String getAlertContrat() {
        return alertContrat;
    }

    public void setCodAgPayement(String codAgPayement) {
        this.codAgPayement = codAgPayement;
    }

    public String getCodAgPayement() {
        return codAgPayement;
    }

    public void setNumCheque(String numCheque) {
        this.numCheque = numCheque;
    }

    public String getNumCheque() {
        return numCheque;
    }

    public void setTypeCheque(String typeCheque) {
        this.typeCheque = typeCheque;
    }

    public String getTypeCheque() {
        return typeCheque;
    }

    public void setMontantCertif(String montantCertif) {
        this.montantCertif = montantCertif;
    }

    public String getMontantCertif() {
        return montantCertif;
    }

    public void setNumCertCchq(String numCertCchq) {
        this.numCertCchq = numCertCchq;
    }

    public String getNumCertCchq() {
        return numCertCchq;
    }

    public void setDateCertCchq(String dateCertCchq) {
        this.dateCertCchq = dateCertCchq;
    }

    public String getDateCertCchq() {
        return dateCertCchq;
    }

    public void setEtatDemCchq(String etatDemCchq) {
        this.etatDemCchq = etatDemCchq;
    }

    public String getEtatDemCchq() {
        return etatDemCchq;
    }

    public void setCodLieuCchq(String codLieuCchq) {
        this.codLieuCchq = codLieuCchq;
    }

    public String getCodLieuCchq() {
        return codLieuCchq;
    }

    public void setDatRestCchq(String datRestCchq) {
        this.datRestCchq = datRestCchq;
    }

    public String getDatRestCchq() {
        return datRestCchq;
    }

    public void setDatAnnuCchq(String datAnnuCchq) {
        this.datAnnuCchq = datAnnuCchq;
    }

    public String getDatAnnuCchq() {
        return datAnnuCchq;
    }

    public void setDatPayCchq(String datPayCchq) {
        this.datPayCchq = datPayCchq;
    }

    public String getDatPayCchq() {
        return datPayCchq;
    }


    public void setDatValCchq(String datValCchq) {
        this.datValCchq = datValCchq;
    }

    public String getDatValCchq() {
        return datValCchq;
    }

    public void setAlertDemandeur(String alertDemandeur) {
        this.alertDemandeur = alertDemandeur;
    }

    public String getAlertDemandeur() {
        return alertDemandeur;
    }


    public void setSoldeFinal(String soldeFinal) {
        this.soldeFinal = soldeFinal;
    }

    public String getSoldeFinal() {
        return soldeFinal;
    }

    public void setMotifRejet(String motifRejet) {
        this.motifRejet = motifRejet;
    }

    public String getMotifRejet() {
        return motifRejet;
    }

    public void setTypeDemandeur(String typeDemandeur) {
        this.typeDemandeur = typeDemandeur;
    }

    public String getTypeDemandeur() {
        return typeDemandeur;
    }

    public void setNumeroMandatChoisi(String numeroMandatChoisi) {
        this.numeroMandatChoisi = numeroMandatChoisi;
    }

    public String getNumeroMandatChoisi() {
        return numeroMandatChoisi;
    }

    public void setLibelleConfirmation(String libelleConfirmation) {
        this.libelleConfirmation = libelleConfirmation;
    }

    public String getLibelleConfirmation() {
        return libelleConfirmation;
    }

    public void setCertDeplacee(String certDeplacee) {
        this.certDeplacee = certDeplacee;
    }

    public String getCertDeplacee() {
        return certDeplacee;
    }

    public void setListeDemandesChequesCertifies(Collection listeDemandesChequesCertifies) {
        this.listeDemandesChequesCertifies = listeDemandesChequesCertifies;
    }

    public Collection getListeDemandesChequesCertifies() {
        return listeDemandesChequesCertifies;
    }

    public void setNumDemandeChoisi(String numDemandeChoisi) {
        this.numDemandeChoisi = numDemandeChoisi;
    }

    public String getNumDemandeChoisi() {
        return numDemandeChoisi;
    }

    public void setNumDemandeChoisie(String numDemandeChoisie) {
        this.numDemandeChoisie = numDemandeChoisie;
    }

    public String getNumDemandeChoisie() {
        return numDemandeChoisie;
    }

    public void setNumDemandeAffichee(String numDemandeAffichee) {
        this.numDemandeAffichee = numDemandeAffichee;
    }

    public String getNumDemandeAffichee() {
        return numDemandeAffichee;
    }

    public void setChoix(String choix) {
        this.choix = choix;
    }

    public String getChoix() {
        return choix;
    }

    public void setCertificationCheques(CertificationCheques certificationCheques) {
        this.certificationCheques = certificationCheques;
    }

    public CertificationCheques getCertificationCheques() {
        return certificationCheques;
    }

    public void setChoixEtatDemande(String choixEtatDemande) {
        this.choixEtatDemande = choixEtatDemande;
    }

    public String getChoixEtatDemande() {
        return choixEtatDemande;
    }

    public void setTypePieceId(String typePieceId) {
        this.typePieceId = typePieceId;
    }

    public String getTypePieceId() {
        return typePieceId;
    }

    public void setNumPieceId(String numPieceId) {
        this.numPieceId = numPieceId;
    }

    public String getNumPieceId() {
        return numPieceId;
    }

    public void setCodStrcRech(String codStrcRech) {
        this.codStrcRech = codStrcRech;
    }

    public String getCodStrcRech() {
        return codStrcRech;
    }

    public void setCodPrdRech(String codPrdRech) {
        this.codPrdRech = codPrdRech;
    }

    public String getCodPrdRech() {
        return codPrdRech;
    }

    public void setNumCcptRech(String numCcptRech) {
        this.numCcptRech = numCcptRech;
    }

    public String getNumCcptRech() {
        return numCcptRech;
    }

    public void setNumChequeRech(String numChequeRech) {
        this.numChequeRech = numChequeRech;
    }

    public String getNumChequeRech() {
        return numChequeRech;
    }

    public void setDateDebut(String dateDebut) {
        this.dateDebut = dateDebut;
    }

    public String getDateDebut() {
        return dateDebut;
    }

    public void setDateFin(String dateFin) {
        this.dateFin = dateFin;
    }

    public String getDateFin() {
        return dateFin;
    }

    public void setEtatDemCertifChoisie(String etatDemCertifChoisie) {
        this.etatDemCertifChoisie = etatDemCertifChoisie;
    }

    public String getEtatDemCertifChoisie() {
        return etatDemCertifChoisie;
    }


    public void setDatedemCertChqChoisie(String datedemCertChqChoisie) {
        this.datedemCertChqChoisie = datedemCertChqChoisie;
    }

    public String getDatedemCertChqChoisie() {
        return datedemCertChqChoisie;
    }

    public void setPersonneDemandeur(PersonneDemandeur personneDemandeur) {
        this.personneDemandeur = personneDemandeur;
    }

    public PersonneDemandeur getPersonneDemandeur() {
        return personneDemandeur;
    }

    public void setContratCpt(ContratCpt contratCpt) {
        this.contratCpt = contratCpt;
    }

    public ContratCpt getContratCpt() {
        return contratCpt;
    }

    public void setPouvoir(Pouvoir pouvoir) {
        this.pouvoir = pouvoir;
    }

    public Pouvoir getPouvoir() {
        return pouvoir;
    }

    public void setInitialisationView(InitialisationView initialisationView) {
        this.initialisationView = initialisationView;
    }

    public InitialisationView getInitialisationView() {
        return initialisationView;
    }


    public void setListDetailOperMoyPai(Set listDetailOperMoyPai) {
        this.listDetailOperMoyPai = listDetailOperMoyPai;
    }

    public Set getListDetailOperMoyPai() {
        return listDetailOperMoyPai;
    }


    public void setNomTireCchq(String nomTireCchq) {
        this.nomTireCchq = nomTireCchq;
    }

    public String getNomTireCchq() {
        return nomTireCchq;
    }


    public void setMntFraisCchq(String mntFraisCchq) {
        this.mntFraisCchq = mntFraisCchq;
    }

    public String getMntFraisCchq() {
        return mntFraisCchq;
    }

    public void setMntTvaCchq(String mntTvaCchq) {
        this.mntTvaCchq = mntTvaCchq;
    }

    public String getMntTvaCchq() {
        return mntTvaCchq;
    }

    public void setForcage(String forcage) {
        this.forcage = forcage;
    }

    public String getForcage() {
        return forcage;
    }

    public void setCodeTache(String codeTache) {
        this.codeTache = codeTache;
    }

    public String getCodeTache() {
        return codeTache;
    }


    public void setCondBanque(String condBanque) {
        this.condBanque = condBanque;
    }

    public String getCondBanque() {
        return condBanque;
    }
}
