package com.bna.smile.web.souscription.forms;

import com.bna.commun.model.Blocage;
import com.bna.commun.model.Categorie;
import com.bna.commun.model.Client;

import com.bna.commun.model.ContratCpt;

import com.bna.commun.model.DetailCatCpt;

import com.bna.smile.web.commun.view.ContratView;
import com.bna.smile.web.commun.view.InitialisationView;

import java.util.ArrayList;
import java.util.Collection;

import java.util.HashSet;
import java.util.List;

import java.util.Set;

import org.apache.struts.action.ActionForm;

public class GestionContratCptForm extends ActionForm{
    /**Reset all properties to their default values.
     * @param mapping The ActionMapping used to select this instance.
     * @param request The HTTP Request we are processing.
     */
     
     private String numCcptCcpt;
     private String codStrcStrc;
     private String codPrdPrd;
     private String cle;
     private String typPcePers;
     private String numPcePers;
     private String nomNomPers;
     private String nomPrnPers;
     private String datOuvCcpt;
     private String datCloCcpt;
     private String codEtatCcpt;
     private String montSoldCcpt;
     private String libDevDev;
     private String numLivrCcpt;
     private String montVersCat;
     private String montCaptCat;
     private Long montAssrCat;
     private String montBrsCat;
     private String reqCode;
     private String codRgmRgm;
     private String codCatCat;
     private String libRgmRgm;
     private String CodCorrCat;
     private String libCatCat;
     private String typPcePersN;
     private String numPcePersN;
     private String nomNomPersN;
     private String nomPrnPersN;
     private String typPcePersDemandeur;
     private String numPcePersDemandeur;
     private String nomNomPersDemandeur;
     private String nomPrnPersDemandeur;
     private boolean droitDemandeur=true;
     private boolean droittransfert=true;
     private Client client;
     private Boolean istuteur=false;
     private String NcodRgmRgm;
     private String NcodCatCat;
     private String nouvMontVersCat;
     private String nouvMontCaptCat;
     private Collection listRegimeEpargne;
     private Collection listCategorieEpargne;
     private String NnumLivrCcpt;
     private String typeRequest;
     private String codeRegimeEpargne;
     private String codeCategorieEpargne;
     private String codetrait;
     private Long montSoldActuel;
     private Long montSoldTheor;
     private String reliquat;
     private String reliquatAssur;
     private List listAncienCategorieEpargne = new ArrayList();
     private DetailCatCpt detailCatCpt;
     private ContratCpt contrat;
     private List listCotitulaires = new ArrayList();
     private String numSeqCliCotitulaire;
     private String typeSignatureCotitulaire;
     private String typeCotitulaire;
     private boolean existCotit=false;
     private boolean existCpt=false;
     private String executant;
     
    // Informations du client
    private String codTpceTpceClient;
    private String numPcePersClient;
    private String nomNomPersClient;
    private String nomPrnPersClient;
    private boolean validDateLim=false;
    
    // partie reservée pour les données de la DIV Tuteur/mineur
     private String categorieTuteur;
     private String typePieceTuteur;
     private String numPieceTuteur;
     private String nomTuteur;
     private String prenomTuteur;
     private String messageNbreMineurs = "";
     private List listMineurs= new ArrayList();
     private String numSeqTuteur;
     private Boolean isTuteur = false;
     private String alertTuteur;
     private Integer nombreMineurs = 0;
     private String alert;
    private  InitialisationView initialisationView = new InitialisationView();
    private ContratView contratView = new ContratView();
    private List chequiers = new ArrayList();
    private List demandeCheques = new ArrayList();
    private List carteBancaires = new ArrayList();
    private List demandeCarteBancaires = new ArrayList();
    private String numMatriculeUser = "";
    private Collection listMotifEtat;
    private String codeMotifEtat;
    private String montantBloc;
    private String codNatBloc;
    private Collection listNatureBlocage;
    private Collection listBlocages;
    private List indexBlocageChoisis = new ArrayList();
    private String numbloc;
    private Blocage BlocageChoisi;
    private String activValider="false";
    private String alertCloture="false";
    private String datvalTctx;
    private String alertBloc;
    private String nouvnumLivrCcpt;
    private String libelleConfirmation;
    private Collection livrets ;
    private String etatFormCreationPersonne="0";
    
    // gestion de notification deces client...
    private String typePieceId;
    private String  numPieceId;
    private Collection listeContrats;
    private Collection listeContratsView;
    private Collection listeContratsMandataire;
    private Collection listeEntiteCotit;    
    private String numDeces;
    private String dateDeces;
    private Collection listeMandatsValides;
    private String dateActuelle;
    
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

    public void setTypPcePers(String typPcePers) {
        this.typPcePers = typPcePers;
    }

    public String getTypPcePers() {
        return typPcePers;
    }

    public void setNumPcePers(String numPcePers) {
        this.numPcePers = numPcePers;
    }

    public String getNumPcePers() {
        return numPcePers;
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

    public void setDatOuvCcpt(String datOuvCcpt) {
        this.datOuvCcpt = datOuvCcpt;
    }

    public String getDatOuvCcpt() {
        return datOuvCcpt;
    }

    public void setDatCloCcpt(String datCloCcpt) {
        this.datCloCcpt = datCloCcpt;
    }

    public String getDatCloCcpt() {
        return datCloCcpt;
    }

    public void setCodEtatCcpt(String codEtatCcpt) {
        this.codEtatCcpt = codEtatCcpt;
    }

    public String getCodEtatCcpt() {
        return codEtatCcpt;
    }

    public void setMontSoldCcpt(String montSoldCcpt) {
        this.montSoldCcpt = montSoldCcpt;
    }

    public String getMontSoldCcpt() {
        return montSoldCcpt;
    }

    public void setLibDevDev(String libDevDev) {
        this.libDevDev = libDevDev;
    }

    public String getLibDevDev() {
        return libDevDev;
    }

    public void setReqCode(String reqCode) {
        this.reqCode = reqCode;
    }

    public String getReqCode() {
        return reqCode;
    }
   

    public void setNumLivrCcpt(String numLivrCcpt) {
        this.numLivrCcpt = numLivrCcpt;
    }

    public String getNumLivrCcpt() {
        return numLivrCcpt;
    }

    public void setMontVersCat(String montVersCat) {
        this.montVersCat = montVersCat;
    }

    public String getMontVersCat() {
        return montVersCat;
    }

    public void setMontCaptCat(String montCaptCat) {
        this.montCaptCat = montCaptCat;
    }

    public String getMontCaptCat() {
        return montCaptCat;
    }

    
    public void setLibRgmRgm(String libRgmRgm) {
        this.libRgmRgm = libRgmRgm;
    }

    public String getLibRgmRgm() {
        return libRgmRgm;
    }

    public void setLibCatCat(String libCatCat) {
        this.libCatCat = libCatCat;
    }

    public String getLibCatCat() {
        return libCatCat;
    }

    public void setTypPcePersN(String typPcePersN) {
        this.typPcePersN = typPcePersN;
    }

    public String getTypPcePersN() {
        return typPcePersN;
    }

    public void setNumPcePersN(String numPcePersN) {
        this.numPcePersN = numPcePersN;
    }

    public String getNumPcePersN() {
        return numPcePersN;
    }

    public void setNomNomPersN(String nomNomPersN) {
        this.nomNomPersN = nomNomPersN;
    }

    public String getNomNomPersN() {
        return nomNomPersN;
    }

    public void setNomPrnPersN(String nomPrnPersN) {
        this.nomPrnPersN = nomPrnPersN;
    }

    public String getNomPrnPersN() {
        return nomPrnPersN;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public Client getClient() {
        return client;
    }

    public void setIstuteur(Boolean istuteur) {
        this.istuteur = istuteur;
    }

    public Boolean getIstuteur() {
        return istuteur;
    }

    public void setNouvMontVersCat(String nouvMontVersCat) {
        this.nouvMontVersCat = nouvMontVersCat;
    }

    public String getNouvMontVersCat() {
        return nouvMontVersCat;
    }

    public void setNouvMontCaptCat(String nouvMontCaptCat) {
        this.nouvMontCaptCat = nouvMontCaptCat;
    }

    public String getNouvMontCaptCat() {
        return nouvMontCaptCat;
    }

    public void setListRegimeEpargne(Collection listRegimeEpargne) {
        this.listRegimeEpargne = listRegimeEpargne;
    }

    public Collection getListRegimeEpargne() {
        return listRegimeEpargne;
    }

    public void setListCategorieEpargne(Collection listCategorieEpargne) {
        this.listCategorieEpargne = listCategorieEpargne;
    }

    public Collection getListCategorieEpargne() {
        return listCategorieEpargne;
    }

    public void setCodRgmRgm(String codRgmRgm) {
        this.codRgmRgm = codRgmRgm;
    }

    public String getCodRgmRgm() {
        return codRgmRgm;
    }

    public void setCodCatCat(String codCatCat) {
        this.codCatCat = codCatCat;
    }

    public String getCodCatCat() {
        return codCatCat;
    }

    public void setNcodRgmRgm(String ncodRgmRgm) {
        this.NcodRgmRgm = ncodRgmRgm;
    }

    public String getNcodRgmRgm() {
        return NcodRgmRgm;
    }

    public void setNcodCatCat(String ncodCatCat) {
        this.NcodCatCat = ncodCatCat;
    }

    public String getNcodCatCat() {
        return NcodCatCat;
    }

    public void setNnumLivrCcpt(String nnumLivrCcpt) {
        this.NnumLivrCcpt = nnumLivrCcpt;
    }

    public String getNnumLivrCcpt() {
        return NnumLivrCcpt;
    }

    public void setTypeRequest(String typeRequest) {
        this.typeRequest = typeRequest;
    }

    public String getTypeRequest() {
        return typeRequest;
    }

    public void setCodeRegimeEpargne(String codeRegimeEpargne) {
        this.codeRegimeEpargne = codeRegimeEpargne;
    }

    public String getCodeRegimeEpargne() {
        return codeRegimeEpargne;
    }

    public void setCodeCategorieEpargne(String codeCategorieEpargne) {
        this.codeCategorieEpargne = codeCategorieEpargne;
    }

    public String getCodeCategorieEpargne() {
        return codeCategorieEpargne;
    }

    public void setCodetrait(String codetrait) {
        this.codetrait = codetrait;
    }

    public String getCodetrait() {
        return codetrait;
    }

    public void setMontSoldActuel(Long montSoldActuel) {
        this.montSoldActuel = montSoldActuel;
    }

    public Long getMontSoldActuel() {
        return montSoldActuel;
    }

    public void setMontSoldTheor(Long montSoldTheor) {
        this.montSoldTheor = montSoldTheor;
    }

    public Long getMontSoldTheor() {
        return montSoldTheor;
    }


    public void setListAncienCategorieEpargne(List listAncienCategorieEpargne) {
        this.listAncienCategorieEpargne = listAncienCategorieEpargne;
    }

    public List getListAncienCategorieEpargne() {
        return listAncienCategorieEpargne;
    }

   

    public void setContrat(ContratCpt contrat) {
        this.contrat = contrat;
    }

    public ContratCpt getContrat() {
        return contrat;
    }

    public void setDetailCatCpt(DetailCatCpt detailCatCpt) {
        this.detailCatCpt = detailCatCpt;
    }

    public DetailCatCpt getDetailCatCpt() {
        return detailCatCpt;
    }

    public void setListCotitulaires(List listCotitulaires) {
        this.listCotitulaires = listCotitulaires;
    }

    public List getListCotitulaires() {
        return listCotitulaires;
    }

    public void setNumSeqCliCotitulaire(String numSeqCliCotitulaire) {
        this.numSeqCliCotitulaire = numSeqCliCotitulaire;
    }

    public String getNumSeqCliCotitulaire() {
        return numSeqCliCotitulaire;
    }

    public void setTypeSignatureCotitulaire(String typeSignatureCotitulaire) {
        this.typeSignatureCotitulaire = typeSignatureCotitulaire;
    }

    public String getTypeSignatureCotitulaire() {
        return typeSignatureCotitulaire;
    }

    public void setTypeCotitulaire(String typeCotitulaire) {
        this.typeCotitulaire = typeCotitulaire;
    }

    public String getTypeCotitulaire() {
        return typeCotitulaire;
    }

    public void setCodTpceTpceClient(String codTpceTpceClient) {
        this.codTpceTpceClient = codTpceTpceClient;
    }

    public String getCodTpceTpceClient() {
        return codTpceTpceClient;
    }

    public void setNumPcePersClient(String numPcePersClient) {
        this.numPcePersClient = numPcePersClient;
    }

    public String getNumPcePersClient() {
        return numPcePersClient;
    }

    public void setNomNomPersClient(String nomNomPersClient) {
        this.nomNomPersClient = nomNomPersClient;
    }

    public String getNomNomPersClient() {
        return nomNomPersClient;
    }

    public void setNomPrnPersClient(String nomPrnPersClient) {
        this.nomPrnPersClient = nomPrnPersClient;
    }

    public String getNomPrnPersClient() {
        return nomPrnPersClient;
    }

    public void setTypPcePersDemandeur(String typPcePersDemandeur) {
        this.typPcePersDemandeur = typPcePersDemandeur;
    }

    public String getTypPcePersDemandeur() {
        return typPcePersDemandeur;
    }

    public void setNumPcePersDemandeur(String numPcePersDemandeur) {
        this.numPcePersDemandeur = numPcePersDemandeur;
    }

    public String getNumPcePersDemandeur() {
        return numPcePersDemandeur;
    }

    public void setNomNomPersDemandeur(String nomNomPersDemandeur) {
        this.nomNomPersDemandeur = nomNomPersDemandeur;
    }

    public String getNomNomPersDemandeur() {
        return nomNomPersDemandeur;
    }

    public void setNomPrnPersDemandeur(String nomPrnPersDemandeur) {
        this.nomPrnPersDemandeur = nomPrnPersDemandeur;
    }

    public String getNomPrnPersDemandeur() {
        return nomPrnPersDemandeur;
    }

    public void setExistCotit(boolean existCotit) {
        this.existCotit = existCotit;
    }

    public boolean isExistCotit() {
        return existCotit;
    }

    public void setDroitDemandeur(boolean droitDemandeur) {
        this.droitDemandeur = droitDemandeur;
    }

    public boolean isDroitDemandeur() {
        return droitDemandeur;
    }
    public void clearForm() {
    
         numCcptCcpt="";
         codStrcStrc="";
        codPrdPrd="";
         cle="";
         typPcePers="";
        numPcePers="";
        nomNomPers="";
       nomPrnPers="";
        datOuvCcpt="";
        datCloCcpt="";
        codEtatCcpt="";
         montSoldCcpt="";
        libDevDev="";
         numLivrCcpt="";
         montVersCat="";
        montCaptCat="";
        reqCode="";
         codRgmRgm="";
        codCatCat="";
        libRgmRgm="";
        libCatCat="";
         typPcePersN="";
         numPcePersN="";
        nomNomPersN="";
         nomPrnPersN="";
         typPcePersDemandeur="";
        numPcePersDemandeur="";
         nomNomPersDemandeur="";
         nomPrnPersDemandeur="";
        droitDemandeur=true;
         client=null;
         istuteur=false;
        NcodRgmRgm="";
        NcodCatCat="";
        nouvMontVersCat="";
        nouvMontCaptCat="";
        listRegimeEpargne=null;
        listCategorieEpargne=null;
         NnumLivrCcpt="";
        typeRequest="";
         codeRegimeEpargne="";
        codeCategorieEpargne="";
         codetrait="";
         montSoldActuel=null;
        montSoldTheor=null;
        reliquat=null;
         listAncienCategorieEpargne = new ArrayList();
        detailCatCpt=null;
        contrat=null;
        listCotitulaires = new ArrayList();
        numSeqCliCotitulaire="";
         typeSignatureCotitulaire="";
        typeCotitulaire="";
         existCotit=false;
        existCpt=false;
        // Informations du client
         codTpceTpceClient="";
        numPcePersClient="";
        nomNomPersClient="";
        nomPrnPersClient="";
        contratView = new ContratView();
        listMotifEtat=null;
        codeMotifEtat="";
        montantBloc="";
        codNatBloc="";
        listNatureBlocage=null;
        listBlocages=null;
        indexBlocageChoisis = new ArrayList();
        numbloc="";
        BlocageChoisi=null;
        activValider="false";
        alert="";
        alertCloture="false";
        alertBloc="";
        nouvnumLivrCcpt="";
        libelleConfirmation="";
        demandeCheques=new ArrayList();
        chequiers=new ArrayList();
        demandeCarteBancaires=new ArrayList();
        carteBancaires=new ArrayList();
        livrets = null;
       
    }
    
    public void clearFormNotificationDeces(){
         typePieceId = "";
         numPieceId= "";
         listeContrats= null;
         listeContratsMandataire= null;
         listeMandatsValides=null;
         listeEntiteCotit= null;
         numDeces="";
         dateDeces="";
         nomNomPersClient="";
         nomPrnPersClient="";
        
    }

    public void setReliquat(String reliquat) {
        this.reliquat = reliquat;
    }

    public String getReliquat() {
        return reliquat;
    }

    public void setValidDateLim(boolean validDateLim) {
        this.validDateLim = validDateLim;
    }

    public boolean isValidDateLim() {
        return validDateLim;
    }

    public void setDroittransfert(boolean droittransfert) {
        this.droittransfert = droittransfert;
    }

    public boolean isDroittransfert() {
        return droittransfert;
    }

    public void setCategorieTuteur(String categorieTuteur) {
        this.categorieTuteur = categorieTuteur;
    }

    public String getCategorieTuteur() {
        return categorieTuteur;
    }

    public void setTypePieceTuteur(String typePieceTuteur) {
        this.typePieceTuteur = typePieceTuteur;
    }

    public String getTypePieceTuteur() {
        return typePieceTuteur;
    }

    public void setNumPieceTuteur(String numPieceTuteur) {
        this.numPieceTuteur = numPieceTuteur;
    }

    public String getNumPieceTuteur() {
        return numPieceTuteur;
    }

    public void setNomTuteur(String nomTuteur) {
        this.nomTuteur = nomTuteur;
    }

    public String getNomTuteur() {
        return nomTuteur;
    }

    public void setPrenomTuteur(String prenomTuteur) {
        this.prenomTuteur = prenomTuteur;
    }

    public String getPrenomTuteur() {
        return prenomTuteur;
    }

    public void setListMineurs(List listMineurs) {
        this.listMineurs = listMineurs;
    }

    public List getListMineurs() {
        return listMineurs;
    }

    public void setMessageNbreMineurs(String messageNbreMineurs) {
        this.messageNbreMineurs = messageNbreMineurs;
    }

    public String getMessageNbreMineurs() {
        return messageNbreMineurs;
    }

    public void setNumSeqTuteur(String numSeqTuteur) {
        this.numSeqTuteur = numSeqTuteur;
    }

    public String getNumSeqTuteur() {
        return numSeqTuteur;
    }

    public void setIsTuteur(Boolean isTuteur) {
        this.isTuteur = isTuteur;
    }

    public Boolean getIsTuteur() {
        return isTuteur;
    }

    public void setAlertTuteur(String alertTuteur) {
        this.alertTuteur = alertTuteur;
    }

    public String getAlertTuteur() {
        return alertTuteur;
    }

    public void setNombreMineurs(Integer nombreMineurs) {
        this.nombreMineurs = nombreMineurs;
    }

    public Integer getNombreMineurs() {
        return nombreMineurs;
    }

    public void setAlert(String alert) {
        this.alert = alert;
    }

    public String getAlert() {
        return alert;
    }

    public void setMontBrsCat(String montBrsCat) {
        this.montBrsCat = montBrsCat;
    }

    public String getMontBrsCat() {
        return montBrsCat;
    }

    public void setInitialisationView(InitialisationView initialisationView) {
        this.initialisationView = initialisationView;
    }

    public InitialisationView getInitialisationView() {
        return initialisationView;
    }

    public void setContratView(ContratView contratView) {
        this.contratView = contratView;
    }

    public ContratView getContratView() {
        return contratView;
    }

    public void setChequiers(List chequiers) {
        this.chequiers = chequiers;
    }

    public List getChequiers() {
        return chequiers;
    }

    public void setDemandeCheques(List demandeCheques) {
        this.demandeCheques = demandeCheques;
    }

    public List getDemandeCheques() {
        return demandeCheques;
    }

    public void setCarteBancaires(List carteBancaires) {
        this.carteBancaires = carteBancaires;
    }

    public List getCarteBancaires() {
        return carteBancaires;
    }

    public void setNumMatriculeUser(String numMatriculeUser) {
        this.numMatriculeUser = numMatriculeUser;
    }

    public String getNumMatriculeUser() {
        return numMatriculeUser;
    }

    public void setListMotifEtat(Collection listMotifEtat) {
        this.listMotifEtat = listMotifEtat;
    }

    public Collection getListMotifEtat() {
        return listMotifEtat;
    }

    public void setCodeMotifEtat(String codeMotifEtat) {
        this.codeMotifEtat = codeMotifEtat;
    }

    public String getCodeMotifEtat() {
        return codeMotifEtat;
    }

    public void setMontantBloc(String montantBloc) {
        this.montantBloc = montantBloc;
    }

    public String getMontantBloc() {
        return montantBloc;
    }

  

    public void setListNatureBlocage(Collection listNatureBlocage) {
        this.listNatureBlocage = listNatureBlocage;
    }

    public Collection getListNatureBlocage() {
        return listNatureBlocage;
    }


    public void setCodNatBloc(String codNatBloc) {
        this.codNatBloc = codNatBloc;
    }

    public String getCodNatBloc() {
        return codNatBloc;
    }

    public void setListBlocages(Collection listBlocages) {
        this.listBlocages = listBlocages;
    }

    public Collection getListBlocages() {
        return listBlocages;
    }

    public void setIndexBlocageChoisis(List indexBlocageChoisis) {
        this.indexBlocageChoisis = indexBlocageChoisis;
    }

    public List getIndexBlocageChoisis() {
        return indexBlocageChoisis;
    }

    public void setNumbloc(String numbloc) {
        this.numbloc = numbloc;
    }

    public String getNumbloc() {
        return numbloc;
    }

    public void setBlocageChoisi(Blocage blocageChoisi) {
        this.BlocageChoisi = blocageChoisi;
    }

    public Blocage getBlocageChoisi() {
        return BlocageChoisi;
    }


    public void setActivValider(String activValider) {
        this.activValider = activValider;
    }

    public String getActivValider() {
        return activValider;
    }

    public void setDemandeCarteBancaires(List demandeCarteBancaires) {
        this.demandeCarteBancaires = demandeCarteBancaires;
    }

    public List getDemandeCarteBancaires() {
        return demandeCarteBancaires;
    }

    public void setAlertCloture(String alertCloture) {
        this.alertCloture = alertCloture;
    }

    public String getAlertCloture() {
        return alertCloture;
    }

    public void setNouvnumLivrCcpt(String nouvnumLivrCcpt) {
        this.nouvnumLivrCcpt = nouvnumLivrCcpt;
    }

    public String getNouvnumLivrCcpt() {
        return nouvnumLivrCcpt;
    }


    public void setDatvalTctx(String datvalTctx) {
        this.datvalTctx = datvalTctx;
    }

    public String getDatvalTctx() {
        return datvalTctx;
    }

    public void setAlertBloc(String alertBloc) {
        this.alertBloc = alertBloc;
    }

    public String getAlertBloc() {
        return alertBloc;
    }

    public void setLibelleConfirmation(String libelleConfirmation) {
        this.libelleConfirmation = libelleConfirmation;
    }

    public String getLibelleConfirmation() {
        return libelleConfirmation;
    }

    public void setExistCpt(boolean existCpt) {
        this.existCpt = existCpt;
    }

    public boolean isExistCpt() {
        return existCpt;
    }


    public void setLivrets(Collection livrets) {
        this.livrets = livrets;
    }

    public Collection getLivrets() {
        return livrets;
    }

    public void setReliquatAssur(String reliquatAssur) {
        this.reliquatAssur = reliquatAssur;
    }

    public String getReliquatAssur() {
        return reliquatAssur;
    }


    public void setMontAssrCat(Long montAssrCat) {
        this.montAssrCat = montAssrCat;
    }

    public Long getMontAssrCat() {
        return montAssrCat;
    }

    public void setCodCorrCat(String codCorrCat) {
        this.CodCorrCat = codCorrCat;
    }

    public String getCodCorrCat() {
        return CodCorrCat;
    }

    public void setEtatFormCreationPersonne(String etatFormCreationPersonne) {
        this.etatFormCreationPersonne = etatFormCreationPersonne;
    }

    public String getEtatFormCreationPersonne() {
        return etatFormCreationPersonne;
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

    public void setListeContrats(Collection listeContrats) {
        this.listeContrats = listeContrats;
    }

    public Collection getListeContrats() {
        return listeContrats;
    }

    public void setListeContratsMandataire(Collection listeContratsMandataire) {
        this.listeContratsMandataire = listeContratsMandataire;
    }

    public Collection getListeContratsMandataire() {
        return listeContratsMandataire;
    }

    public void setListeEntiteCotit(Collection listeEntiteCotit) {
        this.listeEntiteCotit = listeEntiteCotit;
    }

    public Collection getListeEntiteCotit() {
        return listeEntiteCotit;
    }

    public void setNumDeces(String numDeces) {
        this.numDeces = numDeces;
    }

    public String getNumDeces() {
        return numDeces;
    }

    public void setDateDeces(String dateDeces) {
        this.dateDeces = dateDeces;
    }

    public String getDateDeces() {
        return dateDeces;
    }

    public void setListeContratsView(Collection listeContratsView) {
        this.listeContratsView = listeContratsView;
    }

    public Collection getListeContratsView() {
        return listeContratsView;
    }

    public void setExecutant(String executant) {
        this.executant = executant;
    }

    public String getExecutant() {
        return executant;
    }

    public void setListeMandatsValides(Collection listeMandatsValides) {
        this.listeMandatsValides = listeMandatsValides;
    }

    public Collection getListeMandatsValides() {
        return listeMandatsValides;
    }

    public void setDateActuelle(String dateActuelle) {
        this.dateActuelle = dateActuelle;
    }

    public String getDateActuelle() {
        return dateActuelle;
    }
}
