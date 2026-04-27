package com.bna.smile.web.operationguichet.form;


import com.bna.commun.model.ContratCpt;

import com.bna.smile.web.commun.model.PersonneDemandeur;
import com.bna.smile.web.commun.model.Pouvoir;
import com.bna.smile.web.commun.view.ContratView;
import com.bna.smile.web.commun.view.InitialisationView;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.struts.action.ActionForm;

public class GuichetRetraitForm extends ActionForm{


    private String numCcptCcpt;
    private String codStrcStrc;
    private String codPrdPrd;
    private String cle;
    private ContratView contratView = new ContratView();
    private InitialisationView initialisationView = new InitialisationView();
    private PersonneDemandeur personneDemandeur = new PersonneDemandeur();

    private String reqCode;
    
    private String typPcePersBenef;
    private String numPcePersBenef;
    private String nomNomPersBenef;
    private String nomPrnPersBenef;

    private String numOperOmp;
    private String choixRetraitEspece="7";
    private String choixRetrait="7";
    private String numPceOmp;
    private String montRetrCcpt;
    private ContratCpt contrat;
    private String codetrait = "RDTN";
    private String deplace = "";     // D : déplacé
    private String devdin = "DN";   // en devise DV ou en dinar DN
    
    private String messageEtatCpt;
    private boolean verifEtat = true;

    private String messageHabilitation;
    private boolean verifHabilitation = true;
    
    private boolean verifOpposition;
    private String typePouvoir;
    private Long montSoldThCcpt;
    private String montSoldDevThCcpt;
    
    private boolean cptCheque   = true;
    private boolean existMoyPay = true;
    private boolean cptLivret   = true;
    
    private String montCommission = "0.000";
    private String montTva = "0.000";
    private String dateVal = new Date().toString();
    
    /* cas de retrait devise */
    private Long codDevDevRet = Long.valueOf(788);// cod devise du retrait  
    private String libDevDevRet = "DTN";// devise du retrait 
    private String coursDev ; // cours de la devise
    private String contreValeur;
    private String montRetrDev;
    
    /* cas de retrait mis à disposition */
    private String  codTypeMmad;
    private Long    numMmadMmad;
    
    private List indexMADChoisis = new ArrayList();
    private List listMAD= new ArrayList();
    private List listMADChoisis= new ArrayList();
  
    private String  refTPE;     // CashAdv
    private String  numCarte;   // CashAdv
    private String  codPayProv; // MoneyGram
    private String  libPayProv; // MoneyGram
    private String  refMG;      // MoneyGram
    
    private Long compteVert = Long.valueOf(0); // verifier s'il a un compte vert
    private Long soldCompteVert = Long.valueOf(0); // solde du compte vert
    private Pouvoir pouvoir;
    private List    listDetailOperOmp = new ArrayList();

    private boolean maopDispo = true;
    private Long  mntDispo = Long.valueOf(0); ;     // montant disponible
    
    public GuichetRetraitForm() {
    }


    public void clearForm() {
        numCcptCcpt     = "";
        codStrcStrc     = "";
        codPrdPrd       = "";
        cle             = "";
        contratView     = new ContratView();
        reqCode         = "";
        typPcePersBenef = "";
        numPcePersBenef = "";
        nomNomPersBenef = "";
        nomPrnPersBenef = "";
        choixRetraitEspece="7";
        choixRetrait="7";

        numPceOmp   ="";
        montRetrCcpt= "";
        contrat     = null;
//        codetrait   = "RDTN";
        messageEtatCpt="";
        verifEtat   = true;
        refTPE      ="";// CashAdv
        numCarte    ="";// CashAdv
        codPayProv  =""; // MoneyGram
        libPayProv  =""; // MoneyGram
        refMG       ="";   // MoneyGram
        coursDev    ="";// cours de la devise
        contreValeur="";
        montRetrDev ="";
        montCommission="0.000";
        montTva ="0.000";
        listMAD.clear();
        indexMADChoisis.clear();
        listMADChoisis.clear();
        personneDemandeur.clear();
        devdin = "DN";
        verifHabilitation=true;
    }
    public void setNumCcptCcpt(String numCcptCcpt) {
        this.numCcptCcpt = numCcptCcpt;
    }

    public String getNumCcptCcpt() {
        return numCcptCcpt;
    }

    public void setCodStrcStrc(String codStrcStrc) {
        this.codStrcStrc = codStrcStrc;
    }

    public String getCodStrcStrc() {
        return codStrcStrc;
    }

    public void setCodPrdPrd(String codPrdPrd) {
        this.codPrdPrd = codPrdPrd;
    }

    public String getCodPrdPrd() {
        return codPrdPrd;
    }

    public void setCle(String cle) {
        this.cle = cle;
    }

    public String getCle() {
        return cle;
    }


    public void setReqCode(String reqCode) {
        this.reqCode = reqCode;
    }

    public String getReqCode() {
        return reqCode;
    }




    public void setContrat(ContratCpt contrat) {
        this.contrat = contrat;
    }

    public ContratCpt getContrat() {
        return contrat;
    }

    public void setCodetrait(String codetrait) {
        this.codetrait = codetrait;
    }

    public String getCodetrait() {
        return codetrait;
    }

    public void setTypPcePersBenef(String typPcePersBenef) {
        this.typPcePersBenef = typPcePersBenef;
    }

    public String getTypPcePersBenef() {
        return typPcePersBenef;
    }

    public void setNumPcePersBenef(String numPcePersBenef) {
        this.numPcePersBenef = numPcePersBenef;
    }

    public String getNumPcePersBenef() {
        return numPcePersBenef;
    }

    public void setNomNomPersBenef(String nomNomPersBenef) {
        this.nomNomPersBenef = nomNomPersBenef;
    }

    public String getNomNomPersBenef() {
        return nomNomPersBenef;
    }

    public void setNomPrnPersBenef(String nomPrnPersBenef) {
        this.nomPrnPersBenef = nomPrnPersBenef;
    }

    public String getNomPrnPersBenef() {
        return nomPrnPersBenef;
    }

    public void setVerifEtat(boolean verifEtat) {
        this.verifEtat = verifEtat;
    }

    public boolean isVerifEtat() {
        return verifEtat;
    }


   

    public void setNumPceOmp(String numPceOmp) {
        this.numPceOmp = numPceOmp;
    }

    public String getNumPceOmp() {
        return numPceOmp;
    }

    public void setMessageEtatCpt(String messageEtatCpt) {
        this.messageEtatCpt = messageEtatCpt;
    }

    public String getMessageEtatCpt() {
        return messageEtatCpt;
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

    public void setChoixRetraitEspece(String choixRetraitEspece) {
        this.choixRetraitEspece = choixRetraitEspece;
    }

    public String getChoixRetraitEspece() {
        return choixRetraitEspece;
    }

    public void setContratView(ContratView contratView) {
        this.contratView = contratView;
    }

    public ContratView getContratView() {
        return contratView;
    }

    public void setTypePouvoir(String typePouvoir) {
        this.typePouvoir = typePouvoir;
    }

    public String getTypePouvoir() {
        return typePouvoir;
    }



    public void setCptCheque(boolean cptCheque) {
        this.cptCheque = cptCheque;
    }

    public boolean isCptCheque() {
        return cptCheque;
    }

    public void setExistMoyPay(boolean existMoyPay) {
        this.existMoyPay = existMoyPay;
    }

    public boolean isExistMoyPay() {
        return existMoyPay;
    }

    public void setMontCommission(String montCommission) {
        this.montCommission = montCommission;
    }

    public String getMontCommission() {
        return montCommission;
    }

    public void setMontTva(String montTva) {
        this.montTva = montTva;
    }

    public String getMontTva() {
        return montTva;
    }

    public void setLibDevDevRet(String libDevDevRet) {
        this.libDevDevRet = libDevDevRet;
    }

    public String getLibDevDevRet() {
        return libDevDevRet;
    }

    public void setCoursDev(String coursDev) {
        this.coursDev = coursDev;
    }

    public String getCoursDev() {
        return coursDev;
    }

    public void setContreValeur(String contreValeur) {
        this.contreValeur = contreValeur;
    }

    public String getContreValeur() {
        return contreValeur;
    }

    public void setCodDevDevRet(Long codDevDevRet) {
        this.codDevDevRet = codDevDevRet;
    }

    public Long getCodDevDevRet() {
        return codDevDevRet;
    }

    public void setIndexMADChoisis(List indexMADChoisis) {
        this.indexMADChoisis = indexMADChoisis;
    }

    public List getIndexMADChoisis() {
        return indexMADChoisis;
    }

    public void setListMAD(List listMAD) {
        this.listMAD = listMAD;
    }

    public List getListMAD() {
        return listMAD;
    }

    public void setRefTPE(String refTPE) {
        this.refTPE = refTPE;
    }

    public String getRefTPE() {
        return refTPE;
    }

    public void setNumCarte(String numCarte) {
        this.numCarte = numCarte;
    }

    public String getNumCarte() {
        return numCarte;
    }


    public void setRefMG(String refMG) {
        this.refMG = refMG;
    }

    public String getRefMG() {
        return refMG;
    }

    public void setListMADChoisis(List listMADChoisis) {
        this.listMADChoisis = listMADChoisis;
    }

    public List getListMADChoisis() {
        return listMADChoisis;
    }

    public void setCodTypeMmad(String codTypeMmad) {
        this.codTypeMmad = codTypeMmad;
    }

    public String getCodTypeMmad() {
        return codTypeMmad;
    }

    public void setNumMmadMmad(Long numMmadMmad) {
        this.numMmadMmad = numMmadMmad;
    }

    public Long getNumMmadMmad() {
        return numMmadMmad;
    }

    public void setMontRetrDev(String montRetrDev) {
        this.montRetrDev = montRetrDev;
    }

    public String getMontRetrDev() {
        return montRetrDev;
    }

    public void setDateVal(String dateVal) {
        this.dateVal = dateVal;
    }

    public String getDateVal() {
        return dateVal;
    }

    public void setCodPayProv(String codPayProv) {
        this.codPayProv = codPayProv;
    }

    public String getCodPayProv() {
        return codPayProv;
    }

    public void setLibPayProv(String libPayProv) {
        this.libPayProv = libPayProv;
    }

    public String getLibPayProv() {
        return libPayProv;
    }

    public void setInitialisationView(InitialisationView initialisationView) {
        this.initialisationView = initialisationView;
    }

    public InitialisationView getInitialisationView() {
        return initialisationView;
    }

    public void setPersonneDemandeur(PersonneDemandeur personneDemandeur) {
        this.personneDemandeur = personneDemandeur;
    }

    public PersonneDemandeur getPersonneDemandeur() {
        return personneDemandeur;
    }

    public void setPouvoir(Pouvoir pouvoir) {
        this.pouvoir = pouvoir;
    }

    public Pouvoir getPouvoir() {
        return pouvoir;
    }

    public void setMontSoldDevThCcpt(String montSoldDevThCcpt) {
        this.montSoldDevThCcpt = montSoldDevThCcpt;
    }

    public String getMontSoldDevThCcpt() {
        return montSoldDevThCcpt;
    }

    public void setDeplace(String deplace) {
        this.deplace = deplace;
    }

    public String getDeplace() {
        return deplace;
    }

    public void setDevdin(String devdin) {
        this.devdin = devdin;
    }

    public String getDevdin() {
        return devdin;
    }

    public void setCompteVert(Long compteVert) {
        this.compteVert = compteVert;
    }

    public Long getCompteVert() {
        return compteVert;
    }

    public void setSoldCompteVert(Long soldCompteVert) {
        this.soldCompteVert = soldCompteVert;
    }

    public Long getSoldCompteVert() {
        return soldCompteVert;
    }

    public void setMontSoldThCcpt(Long montSoldThCcpt) {
        this.montSoldThCcpt = montSoldThCcpt;
    }

    public Long getMontSoldThCcpt() {
        return montSoldThCcpt;
    }

    public void setListDetailOperOmp(List listDetailOperOmp) {
        this.listDetailOperOmp = listDetailOperOmp;
    }

    public List getListDetailOperOmp() {
        return listDetailOperOmp;
    }

    public void setMntDispo(Long mntDispo) {
        this.mntDispo = mntDispo;
    }

    public Long getMntDispo() {
        return mntDispo;
    }

    public void setMaopDispo(boolean maopDispo) {
        this.maopDispo = maopDispo;
    }

    public boolean isMaopDispo() {
        return maopDispo;
    }

    public void setChoixRetrait(String choixRetrait) {
        this.choixRetrait = choixRetrait;
    }

    public String getChoixRetrait() {
        return choixRetrait;
    }

    public void setCptLivret(boolean cptLivret) {
        this.cptLivret = cptLivret;
    }

    public boolean isCptLivret() {
        return cptLivret;
    }

    public void setNumOperOmp(String numOperOmp) {
        this.numOperOmp = numOperOmp;
    }

    public String getNumOperOmp() {
        return numOperOmp;
    }

    public void setMessageHabilitation(String messageHabilitation) {
        this.messageHabilitation = messageHabilitation;
    }

    public String getMessageHabilitation() {
        return messageHabilitation;
    }

    public void setVerifHabilitation(boolean verifHabilitation) {
        this.verifHabilitation = verifHabilitation;
    }

    public boolean isVerifHabilitation() {
        return verifHabilitation;
    }
}
