package com.bna.smile.web.commun.forms;

import com.bna.commun.model.Client;
import com.bna.commun.model.Personne;

import com.bna.smile.web.commun.view.InitialisationView;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

/**
 * Cette classe permet de données la main à faire les corrections (modification)
 * des données bloquantes.
 * @author Mdimagh Med Lassaad
 * @since 22/07/2008
 */
public class CorrectionDonneesClientForm extends ActionForm{
    public CorrectionDonneesClientForm() {
    }
    
    private  InitialisationView initialisationView = new InitialisationView();
    
    //--------------------------------------------------------
    //--------Identification de la personne-------------------
    //--------------------------------------------------------
    private String typePiece;
    private String numeroPiece;
    
    private String nomRaisonSociale;
    private String prenomSigle;
    
    private Personne personne;
    private Client client;

    private List listeDesContrats = new ArrayList();
    private List listDesContratAmodifier = new ArrayList();
    private List listeCategoriePersonne = new ArrayList();
    private List listeFormeJuridique= new ArrayList();
    
    private String codTperTper;
    
    private String nomNomPers;
    private String nomPrnPers;
    private String nomPrnpPers;
    
    private String libTitrPers;
    private String nomRsPers;
    private String sigle;
    private String libCtapCatp;
    private String codCatpCatp;
    
    private String codTpceTpce;
    private String libTpceTpce;
    private String numPcePers;
    
    
    private String datDlvPers;
    private String codGouvGouv;
    private String libGouvGouv;
    
    private String codProfProf;
    private String codGproGpro;
    private String libProfProf;
    
    private String libTribTrib;
    private String codTribTrib;
    
    private String codActAct;
    private String codCactCact;
    private String codSactSact;
    private String libActAct;
    
    private String boolResPers;
    
    private String nouvelleCodCatpCatp;
    private String nouvellecodFjFj;
    private String codFjFj;
    private String libFjFj;
    private String libelleFormeJuridique;
    
    
    private String datNaisPers;
    private String libNaisPers;
    
    private String codNaisPays;
    private String libNaisPays;
    
    private String codNat1Pays;
    private String libNat1Pays;
   
    //-------------------------------------------------------------
    //---------- Adresse resiedence et profession -----------------
    //-------------------------------------------------------------
    private String immeubleRes;
    private String rueRes;
    private String citeRes;
    private String villeRes;
    private String codCpCpRes;
    private String libCpCpRes;
    private String codGouvGouvRes;
    private String libGouvGouvRes;
    private String codPaysPaysRes;
    private String libPaysPaysRes;

    private String immeubleProf;
    private String rueProf;
    private String citeProf;
    private String villeProf;
    private String codCpCpProf;
    private String libCpCpProf;
    private String codGouvGouvProf;
    private String libGouvGouvProf;
    private String codPaysPaysProf;
    private String libPaysPaysProf;
   
    //------------------------------------------------
    //--------les variables de test  ------------------
    //------------------------------------------------
    private String dateActuelle;
  
    private String testExistPersonne;
    private String libelleConfirmation;
    private String libelleConfirmation1;
    private String libelleConfirmation2;
    


    public void reset(ActionMapping mapping, HttpServletRequest request) {
        super.reset( mapping, request);
    }

    public void clear(){
    
            typePiece ="";
            numeroPiece ="";
            nomRaisonSociale="";
            prenomSigle="";
            
            codTperTper="";
            
            nomNomPers="";
            nomPrnPers="";
            nomPrnpPers="";
            
            libTitrPers ="";
            nomRsPers="";
            sigle="";
            libCtapCatp="";
            datDlvPers="";
            
            libGouvGouv ="";
            codGouvGouv ="";
            
            libTribTrib ="";
            codTribTrib ="";
            
            codTpceTpce = "";
            libTpceTpce ="";
            numPcePers="";
            
            codProfProf="";
            codGproGpro="";
            libProfProf="";
            
            codActAct="";
            codCactCact="";
            codSactSact="";
            libActAct="";
            
            boolResPers="";
            
            codCatpCatp="";
            nouvelleCodCatpCatp="";
            codFjFj="";
            libFjFj="";
            
            datNaisPers="";
            libNaisPers="";
            
            codNaisPays="";
            libNaisPays="";
            
            codNat1Pays="";
            libNat1Pays="";
   
            //-------------------------------------------------------------
            //---------- Adresse resiedence et profession -----------------
            //-------------------------------------------------------------
            immeubleRes = "";
            rueRes = "";
            citeRes = "";
            villeRes = "";
            codCpCpRes = "";
            libCpCpRes = "";
            codGouvGouvRes = "";
            libGouvGouvRes = "";
            codPaysPaysRes = "";
            libPaysPaysRes = "";
    
            immeubleProf = "";
            rueProf = "";
            citeProf = "";
            villeProf = "";
            codCpCpProf = "";
            libCpCpProf = "";
            codGouvGouvProf = "";
            libGouvGouvProf = "";
            codPaysPaysProf = "";
            libPaysPaysProf = "";
 
            
            testExistPersonne="";
            dateActuelle="";
            
            libelleConfirmation="";
            libelleConfirmation1="";
            libelleConfirmation2="";
            libelleFormeJuridique ="";
            
            listeCategoriePersonne = new ArrayList();
            listeFormeJuridique= new ArrayList();
    }

    public void setTypePiece(String typePiece) {
        this.typePiece = typePiece;
    }

    public String getTypePiece() {
        return typePiece;
    }

    public void setNumeroPiece(String numeroPiece) {
        this.numeroPiece = numeroPiece;
    }

    public String getNumeroPiece() {
        return numeroPiece;
    }

    public void setPersonne(Personne personne) {
        this.personne = personne;
    }

    public Personne getPersonne() {
        return personne;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public Client getClient() {
        return client;
    }

    public void setListeDesContrats(List listeDesContrats) {
        this.listeDesContrats = listeDesContrats;
    }

    public List getListeDesContrats() {
        return listeDesContrats;
    }

  

    public void setNomNomPers(String nomNomPers) {
        this.nomNomPers = nomNomPers;
    }

    public String getNomNomPers() {
        return nomNomPers;
    }

    public void setNomPrnPers(String nomPrnPers) {
        this.nomPrnPers = nomPrnPers;
    }

    public String getNomPrnPers() {
        return nomPrnPers;
    }

    public void setLibTitrPers(String libTitrPers) {
        this.libTitrPers = libTitrPers;
    }

    public String getLibTitrPers() {
        return libTitrPers;
    }

    public void setNomRsPers(String nomRsPers) {
        this.nomRsPers = nomRsPers;
    }

    public String getNomRsPers() {
        return nomRsPers;
    }

    public void setSigle(String sigle) {
        this.sigle = sigle;
    }

    public String getSigle() {
        return sigle;
    }


    public void setInitialisationView(InitialisationView initialisationView) {
        this.initialisationView = initialisationView;
    }

    public InitialisationView getInitialisationView() {
        return initialisationView;
    }

    public void setTestExistPersonne(String testExistPersonne) {
        this.testExistPersonne = testExistPersonne;
    }

    public String getTestExistPersonne() {
        return testExistPersonne;
    }

    public void setLibCtapCatp(String libCtapCatp) {
        this.libCtapCatp = libCtapCatp;
    }

    public String getLibCtapCatp() {
        return libCtapCatp;
    }



    public void setCodProfProf(String codProfProf) {
        this.codProfProf = codProfProf;
    }

    public String getCodProfProf() {
        return codProfProf;
    }

    public void setCodGproGpro(String codGproGpro) {
        this.codGproGpro = codGproGpro;
    }

    public String getCodGproGpro() {
        return codGproGpro;
    }

    public void setLibProfProf(String libProfProf) {
        this.libProfProf = libProfProf;
    }

    public String getLibProfProf() {
        return libProfProf;
    }

    public void setCodActAct(String codActAct) {
        this.codActAct = codActAct;
    }

    public String getCodActAct() {
        return codActAct;
    }

    public void setCodCactCact(String codCactCact) {
        this.codCactCact = codCactCact;
    }

    public String getCodCactCact() {
        return codCactCact;
    }

    public void setCodSactSact(String codSactSact) {
        this.codSactSact = codSactSact;
    }

    public String getCodSactSact() {
        return codSactSact;
    }

    public void setLibActAct(String libActAct) {
        this.libActAct = libActAct;
    }

    public String getLibActAct() {
        return libActAct;
    }

    public void setCodFjFj(String codFjFj) {
        this.codFjFj = codFjFj;
    }

    public String getCodFjFj() {
        return codFjFj;
    }

    public void setLibFjFj(String libFjFj) {
        this.libFjFj = libFjFj;
    }

    public String getLibFjFj() {
        return libFjFj;
    }

    public void setDatNaisPers(String datNaisPers) {
        this.datNaisPers = datNaisPers;
    }

    public String getDatNaisPers() {
        return datNaisPers;
    }

    public void setLibNaisPers(String libNaisPers) {
        this.libNaisPers = libNaisPers;
    }

    public String getLibNaisPers() {
        return libNaisPers;
    }

    public void setCodNaisPays(String codNaisPays) {
        this.codNaisPays = codNaisPays;
    }

    public String getCodNaisPays() {
        return codNaisPays;
    }

    public void setLibNaisPays(String libNaisPays) {
        this.libNaisPays = libNaisPays;
    }

    public String getLibNaisPays() {
        return libNaisPays;
    }

    public void setCodNat1Pays(String codNat1Pays) {
        this.codNat1Pays = codNat1Pays;
    }

    public String getCodNat1Pays() {
        return codNat1Pays;
    }

    public void setLibNat1Pays(String libNat1Pays) {
        this.libNat1Pays = libNat1Pays;
    }

    public String getLibNat1Pays() {
        return libNat1Pays;
    }

    public void setDateActuelle(String dateActuelle) {
        this.dateActuelle = dateActuelle;
    }

    public String getDateActuelle() {
        return dateActuelle;
    }

    public void setNomRaisonSociale(String nomRaisonSociale) {
        this.nomRaisonSociale = nomRaisonSociale;
    }

    public String getNomRaisonSociale() {
        return nomRaisonSociale;
    }

    public void setPrenomSigle(String prenomSigle) {
        this.prenomSigle = prenomSigle;
    }

    public String getPrenomSigle() {
        return prenomSigle;
    }

    public void setCodGouvGouv(String codGouvGouv) {
        this.codGouvGouv = codGouvGouv;
    }

    public String getCodGouvGouv() {
        return codGouvGouv;
    }

    public void setLibGouvGouv(String libGouvGouv) {
        this.libGouvGouv = libGouvGouv;
    }

    public String getLibGouvGouv() {
        return libGouvGouv;
    }

    public void setCodTpceTpce(String codTpceTpce) {
        this.codTpceTpce = codTpceTpce;
    }

    public String getCodTpceTpce() {
        return codTpceTpce;
    }

    public void setLibTpceTpce(String libTpceTpce) {
        this.libTpceTpce = libTpceTpce;
    }

    public String getLibTpceTpce() {
        return libTpceTpce;
    }

    public void setNumPcePers(String numPcePers) {
        this.numPcePers = numPcePers;
    }

    public String getNumPcePers() {
        return numPcePers;
    }

    public void setDatDlvPers(String datDlvPers) {
        this.datDlvPers = datDlvPers;
    }

    public String getDatDlvPers() {
        return datDlvPers;
    }

    public void setCodTperTper(String codTperTper) {
        this.codTperTper = codTperTper;
    }

    public String getCodTperTper() {
        return codTperTper;
    }

    public void setLibelleConfirmation(String libelleConfirmation) {
        this.libelleConfirmation = libelleConfirmation;
    }

    public String getLibelleConfirmation() {
        return libelleConfirmation;
    }

    public void setLibelleConfirmation1(String libelleConfirmation1) {
        this.libelleConfirmation1 = libelleConfirmation1;
    }

    public String getLibelleConfirmation1() {
        return libelleConfirmation1;
    }

    public void setLibelleConfirmation2(String libelleConfirmation2) {
        this.libelleConfirmation2 = libelleConfirmation2;
    }

    public String getLibelleConfirmation2() {
        return libelleConfirmation2;
    }

    public void setListDesContratAmodifier(List listDesContratAmodifier) {
        this.listDesContratAmodifier = listDesContratAmodifier;
    }

    public List getListDesContratAmodifier() {
        return listDesContratAmodifier;
    }

    public void setLibelleFormeJuridique(String libelleFormeJuridique) {
        this.libelleFormeJuridique = libelleFormeJuridique;
    }

    public String getLibelleFormeJuridique() {
        return libelleFormeJuridique;
    }

    public void setLibTribTrib(String libTribTrib) {
        this.libTribTrib = libTribTrib;
    }

    public String getLibTribTrib() {
        return libTribTrib;
    }

    public void setCodTribTrib(String codTribTrib) {
        this.codTribTrib = codTribTrib;
    }

    public String getCodTribTrib() {
        return codTribTrib;
    }

    public void setImmeubleRes(String immeubleRes) {
        this.immeubleRes = immeubleRes;
    }

    public String getImmeubleRes() {
        return immeubleRes;
    }

    public void setRueRes(String rueRes) {
        this.rueRes = rueRes;
    }

    public String getRueRes() {
        return rueRes;
    }

    public void setCiteRes(String citeRes) {
        this.citeRes = citeRes;
    }

    public String getCiteRes() {
        return citeRes;
    }

    public void setVilleRes(String villeRes) {
        this.villeRes = villeRes;
    }

    public String getVilleRes() {
        return villeRes;
    }

    public void setCodCpCpRes(String codCpCpRes) {
        this.codCpCpRes = codCpCpRes;
    }

    public String getCodCpCpRes() {
        return codCpCpRes;
    }

    public void setLibCpCpRes(String libCpCpRes) {
        this.libCpCpRes = libCpCpRes;
    }

    public String getLibCpCpRes() {
        return libCpCpRes;
    }

    public void setCodGouvGouvRes(String codGouvGouvRes) {
        this.codGouvGouvRes = codGouvGouvRes;
    }

    public String getCodGouvGouvRes() {
        return codGouvGouvRes;
    }

    public void setLibGouvGouvRes(String libGouvGouvRes) {
        this.libGouvGouvRes = libGouvGouvRes;
    }

    public String getLibGouvGouvRes() {
        return libGouvGouvRes;
    }

    public void setCodPaysPaysRes(String codPaysPaysRes) {
        this.codPaysPaysRes = codPaysPaysRes;
    }

    public String getCodPaysPaysRes() {
        return codPaysPaysRes;
    }

    public void setLibPaysPaysRes(String libPaysPaysRes) {
        this.libPaysPaysRes = libPaysPaysRes;
    }

    public String getLibPaysPaysRes() {
        return libPaysPaysRes;
    }

    public void setImmeubleProf(String immeubleProf) {
        this.immeubleProf = immeubleProf;
    }

    public String getImmeubleProf() {
        return immeubleProf;
    }

    public void setRueProf(String rueProf) {
        this.rueProf = rueProf;
    }

    public String getRueProf() {
        return rueProf;
    }

    public void setCiteProf(String citeProf) {
        this.citeProf = citeProf;
    }

    public String getCiteProf() {
        return citeProf;
    }

    public void setVilleProf(String villeProf) {
        this.villeProf = villeProf;
    }

    public String getVilleProf() {
        return villeProf;
    }

    public void setCodCpCpProf(String codCpCpProf) {
        this.codCpCpProf = codCpCpProf;
    }

    public String getCodCpCpProf() {
        return codCpCpProf;
    }

    public void setLibCpCpProf(String libCpCpProf) {
        this.libCpCpProf = libCpCpProf;
    }

    public String getLibCpCpProf() {
        return libCpCpProf;
    }

    public void setCodGouvGouvProf(String codGouvGouvProf) {
        this.codGouvGouvProf = codGouvGouvProf;
    }

    public String getCodGouvGouvProf() {
        return codGouvGouvProf;
    }

    public void setLibGouvGouvProf(String libGouvGouvProf) {
        this.libGouvGouvProf = libGouvGouvProf;
    }

    public String getLibGouvGouvProf() {
        return libGouvGouvProf;
    }

    public void setCodPaysPaysProf(String codPaysPaysProf) {
        this.codPaysPaysProf = codPaysPaysProf;
    }

    public String getCodPaysPaysProf() {
        return codPaysPaysProf;
    }

    public void setLibPaysPaysProf(String libPaysPaysProf) {
        this.libPaysPaysProf = libPaysPaysProf;
    }

    public String getLibPaysPaysProf() {
        return libPaysPaysProf;
    }

    public void setNouvelleCodCatpCatp(String nouvelleCodCatpCatp) {
        this.nouvelleCodCatpCatp = nouvelleCodCatpCatp;
    }

    public String getNouvelleCodCatpCatp() {
        return nouvelleCodCatpCatp;
    }

    public void setListeCategoriePersonne(List listeCategoriePersonne) {
        this.listeCategoriePersonne = listeCategoriePersonne;
    }

    public List getListeCategoriePersonne() {
        return listeCategoriePersonne;
    }

    public void setNouvellecodFjFj(String nouvellecodFjFj) {
        this.nouvellecodFjFj = nouvellecodFjFj;
    }

    public String getNouvellecodFjFj() {
        return nouvellecodFjFj;
    }

    public void setListeFormeJuridique(List listeFormeJuridique) {
        this.listeFormeJuridique = listeFormeJuridique;
    }

    public List getListeFormeJuridique() {
        return listeFormeJuridique;
    }

    public void setBoolResPers(String boolResPers) {
        this.boolResPers = boolResPers;
    }

    public String getBoolResPers() {
        return boolResPers;
    }



    public void setCodCatpCatp(String codCatpCatp) {
        this.codCatpCatp = codCatpCatp;
    }

    public String getCodCatpCatp() {
        return codCatpCatp;
    }



    public void setNomPrnpPers(String nomPrnpPers) {
        this.nomPrnpPers = nomPrnpPers;
    }

    public String getNomPrnpPers() {
        return nomPrnpPers;
    }
}
