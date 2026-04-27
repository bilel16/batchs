package com.bna.smile.web.clotureDomaine.forms;

import com.bna.smile.model.clotureDomaine.model.StatSouscription;
import com.bna.smile.web.commun.view.InitialisationView;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.struts.action.ActionForm;

public class ClotureDomaineForm extends ActionForm{
    public ClotureDomaineForm() {
    }
    
    private InitialisationView initialisationView = new InitialisationView();
    private String dateJournee;
    private Long codeStructure;
    private String alert;
    private Collection listeContrats;
    private Collection listeContratVal;
    private Collection listeContratAtt;
    private Collection listeContratRej;
    //----------------------------------
    //------ Souscription
    private List listNombreSouscriptionParTypeContrat = new ArrayList(0);
    private List listSouscriptionAtt = new ArrayList(0);
    private List listSouscriptionREj = new ArrayList(0);
   /* private List listSouscriptionAnn = new ArrayList(0);
    private List listSouscriptionBlo = new ArrayList(0);
    private List listSouscriptionCtx = new ArrayList(0);
    private List listNombreSouscriptionParTypeClient = new ArrayList(0);
    private List listNombreSouscAttParTypeClient = new ArrayList(0);*/
    private Long nombreTatalSouscription = Long.valueOf(0);
    private List listNombreSignatureParTypeContrat = new ArrayList(0);
    private Long nombreTatalSignature = Long.valueOf(0);
    private StatSouscription StatSouscription;
    /*private Long nbrTotSouscr = Long.valueOf(0);*/
    private Long nbrSouscrVal = Long.valueOf(0);
    private Long nbrSouscrAtt = Long.valueOf(0);
    private Long nbrSouscrRej = Long.valueOf(0);
    private List listDetailSouscrEnAttente = new ArrayList(0);
    private List listDetailSouscrvalide = new ArrayList(0);
    private List listDetailSouscrRej = new ArrayList(0);
    private String codproduit ;
    private String typeClient;
    private String etatContrat = "";
    //--------------------------------------
    //------- Mandat
    private Long nombreMandatCree = Long.valueOf(0);
    private List listMandatCreationParTypeContrat = new ArrayList(0);
    private Long nombreMandatRenouvle = Long.valueOf(0);
    private List listMandatRenouvleParTypeContrat = new ArrayList(0);
    private Long nombreMandatModifie = Long.valueOf(0);
    private List listMandatModifieParTypeContrat = new ArrayList(0);
    private Long nombreMandatAnnule = Long.valueOf(0);
    private List listMandatAnnuleParTypeContrat = new ArrayList(0);
   /* private Long nbrTotMandat = Long.valueOf(0);*/
    private List listeCreationMandat = new ArrayList(0);
    private List listeModifMandat = new ArrayList(0);
    private List listeMandatRen = new ArrayList(0);
    private List listeMandatAnn = new ArrayList(0);
    private String etatMandat = "";
    //--------------------------------------
    //------- Support de paiements
    private Long nombreChequierDemande = Long.valueOf(0);
    private List listenombreChequierDemandeParType  = new ArrayList(0);
    private Long nombreChequierDemandeAttente       = Long.valueOf(0);
    private List listenombreChequierDemandeParTypeAttente = new ArrayList(0);
    private Long nombreChequierDemandeRejeter       = Long.valueOf(0);
    private List listenombreChequierDemandeParTypeRejeter = new ArrayList(0);
    private List listenombreChequierDemandeParTyperRecu          = new ArrayList(0);
    private List listenombreChequierDemandeParTypeDeliv         = new ArrayList(0);
    private Long nbrTotDemandeCheq       = Long.valueOf(0);
    private String etatCheque = "";
   /* private Long nombreCarteDemandeValide       = Long.valueOf(0);
    private Long nombreCarteDemandeNonValide    = Long.valueOf(0);
    private Long nombreCarteRecu        = Long.valueOf(0);
    private Long nombreCarteAnnule      = Long.valueOf(0);
    private Long nombreCarteDelivre     = Long.valueOf(0);
    private Long nombreCarteRejete      = Long.valueOf(0);
    private Long nbrTotCarte            = Long.valueOf(0);
    
    private List listenombreCarteDemandeParType = new ArrayList(0);
    private List listenombreCarteDemandeNonValideParType = new ArrayList(0);
    private List listenombreCarteRecuParType    = new ArrayList(0);
    private List listenombreCarteAnnuleParType  = new ArrayList(0);
    private List listenombreCarteDelivreParType = new ArrayList(0);
    private List listenombreCarteRejeteParType = new ArrayList(0);*/
    private List listeChqAttente = new ArrayList(0);;
    private List listeChqvalide = new ArrayList(0);;
    private List listeChqrejete = new ArrayList(0);;
    private List listeChqRecu = new ArrayList(0);;
    private List listeChqDeliv = new ArrayList(0);;
    
    //--------------------------------------
    //------- Opposition sur moyens de paiement

    private List listeOppositionParType     = new ArrayList(0);
    private Long nombreOpposition    = Long.valueOf(0);
    private List listeLeveOppositionParType = new ArrayList(0);
    private Long nombreLeveeOpposition    = Long.valueOf(0);
    private String codMoypTmoy="";
    private String typeOpp="";
    private List listDetailOpposition = new ArrayList(0);
    private String etatopposit ="";
    //--------------------------------------
    //------- Modification donn�es client

    private List listeModificationDonneeParType     = new ArrayList(0);
    private Long nombreModificationDonnees          = Long.valueOf(0);

    private String openTabsheetSouscription          ="";
    private String openTabsheetProcuration           ="";
    private String openTabsheetSupportPaiement       ="";
    private String openTabsheetOpposition            ="";
    private String openTabsheetModificationDonnee    ="";
    private String choixRecherche="";
    private String datecloturee="";
    private String dateOuverte="";
    private String libDom="";
    private String codModif="";
    private List listeModificationClient = new ArrayList(0);
    private Long nbrOper672;
    private Double mntOper672;
    private Long nbrOper703;
    private Double mntOper703;
    public void clear() {
        dateJournee = "";
        codeStructure = null;

    }

    public void setInitialisationView(InitialisationView initialisationView) {
        this.initialisationView = initialisationView;
    }

    public InitialisationView getInitialisationView() {
        return initialisationView;
    }

    public void setDateJournee(String dateJournee) {
        this.dateJournee = dateJournee;
    }

    public String getDateJournee() {
        return dateJournee;
    }

    public void setCodeStructure(Long codeStructure) {
        this.codeStructure = codeStructure;
    }

    public Long getCodeStructure() {
        return codeStructure;
    }

    public void setListNombreSouscriptionParTypeContrat(List listNombreSouscriptionParTypeContrat) {
        this.listNombreSouscriptionParTypeContrat = listNombreSouscriptionParTypeContrat;
    }

    public List getListNombreSouscriptionParTypeContrat() {
        return listNombreSouscriptionParTypeContrat;
    }

    public void setListNombreSignatureParTypeContrat(List listNombreSignatureParTypeContrat) {
        this.listNombreSignatureParTypeContrat = listNombreSignatureParTypeContrat;
    }

    public List getListNombreSignatureParTypeContrat() {
        return listNombreSignatureParTypeContrat;
    }

    public void setNombreTatalSignature(Long nombreTatalSignature) {
        this.nombreTatalSignature = nombreTatalSignature;
    }

    public Long getNombreTatalSignature() {
        return nombreTatalSignature;
    }

    public void setNombreMandatCree(Long nombreMandatCree) {
        this.nombreMandatCree = nombreMandatCree;
    }

    public Long getNombreMandatCree() {
        return nombreMandatCree;
    }

    public void setListMandatCreationParTypeContrat(List listMandatCreationParTypeContrat) {
        this.listMandatCreationParTypeContrat = listMandatCreationParTypeContrat;
    }

    public List getListMandatCreationParTypeContrat() {
        return listMandatCreationParTypeContrat;
    }

    public void setNombreMandatRenouvle(Long nombreMandatRenouvle) {
        this.nombreMandatRenouvle = nombreMandatRenouvle;
    }

    public Long getNombreMandatRenouvle() {
        return nombreMandatRenouvle;
    }

    public void setListMandatRenouvleParTypeContrat(List listMandatRenouvleParTypeContrat) {
        this.listMandatRenouvleParTypeContrat = listMandatRenouvleParTypeContrat;
    }

    public List getListMandatRenouvleParTypeContrat() {
        return listMandatRenouvleParTypeContrat;
    }

    public void setNombreMandatModifie(Long nombreMandatModifie) {
        this.nombreMandatModifie = nombreMandatModifie;
    }

    public Long getNombreMandatModifie() {
        return nombreMandatModifie;
    }

    public void setListMandatModifieParTypeContrat(List listMandatModifieParTypeContrat) {
        this.listMandatModifieParTypeContrat = listMandatModifieParTypeContrat;
    }

    public List getListMandatModifieParTypeContrat() {
        return listMandatModifieParTypeContrat;
    }

    public void setNombreMandatAnnule(Long nombreMandatAnnule) {
        this.nombreMandatAnnule = nombreMandatAnnule;
    }

    public Long getNombreMandatAnnule() {
        return nombreMandatAnnule;
    }

    public void setListMandatAnnuleParTypeContrat(List listMandatAnnuleParTypeContrat) {
        this.listMandatAnnuleParTypeContrat = listMandatAnnuleParTypeContrat;
    }

    public List getListMandatAnnuleParTypeContrat() {
        return listMandatAnnuleParTypeContrat;
    }

    public void setNombreChequierDemande(Long nombreChequierDemande) {
        this.nombreChequierDemande = nombreChequierDemande;
    }

    public Long getNombreChequierDemande() {
        return nombreChequierDemande;
    }

    public void setListenombreChequierDemandeParType(List listenombreChequierDemandeParType) {
        this.listenombreChequierDemandeParType = listenombreChequierDemandeParType;
    }

    public List getListenombreChequierDemandeParType() {
        return listenombreChequierDemandeParType;
    }

    public void setNombreChequierDemandeAttente(Long nombreChequierDemandeAttente) {
        this.nombreChequierDemandeAttente = nombreChequierDemandeAttente;
    }

    public Long getNombreChequierDemandeAttente() {
        return nombreChequierDemandeAttente;
    }

    public void setListenombreChequierDemandeParTypeAttente(List listenombreChequierDemandeParTypeAttente) {
        this.listenombreChequierDemandeParTypeAttente = listenombreChequierDemandeParTypeAttente;
    }

    public List getListenombreChequierDemandeParTypeAttente() {
        return listenombreChequierDemandeParTypeAttente;
    }

    public void setNombreChequierDemandeRejeter(Long nombreChequierDemandeRejeter) {
        this.nombreChequierDemandeRejeter = nombreChequierDemandeRejeter;
    }

    public Long getNombreChequierDemandeRejeter() {
        return nombreChequierDemandeRejeter;
    }

    public void setListenombreChequierDemandeParTypeRejeter(List listenombreChequierDemandeParTypeRejeter) {
        this.listenombreChequierDemandeParTypeRejeter = listenombreChequierDemandeParTypeRejeter;
    }

    public List getListenombreChequierDemandeParTypeRejeter() {
        return listenombreChequierDemandeParTypeRejeter;
    }

   
    public void setListeOppositionParType(List listeOppositionParType) {
        this.listeOppositionParType = listeOppositionParType;
    }

    public List getListeOppositionParType() {
        return listeOppositionParType;
    }

    public void setNombreOpposition(Long nombreOpposition) {
        this.nombreOpposition = nombreOpposition;
    }

    public Long getNombreOpposition() {
        return nombreOpposition;
    }

    public void setListeLeveOppositionParType(List listeLeveOppositionParType) {
        this.listeLeveOppositionParType = listeLeveOppositionParType;
    }

    public List getListeLeveOppositionParType() {
        return listeLeveOppositionParType;
    }

    public void setNombreLeveeOpposition(Long nombreLeveeOpposition) {
        this.nombreLeveeOpposition = nombreLeveeOpposition;
    }

    public Long getNombreLeveeOpposition() {
        return nombreLeveeOpposition;
    }

    public void setListeModificationDonneeParType(List listeModificationDonneeParType) {
        this.listeModificationDonneeParType = listeModificationDonneeParType;
    }

    public List getListeModificationDonneeParType() {
        return listeModificationDonneeParType;
    }

    public void setNombreModificationDonnees(Long nombreModificationDonnees) {
        this.nombreModificationDonnees = nombreModificationDonnees;
    }

    public Long getNombreModificationDonnees() {
        return nombreModificationDonnees;
    }

    public void setOpenTabsheetSouscription(String openTabsheetSouscription) {
        this.openTabsheetSouscription = openTabsheetSouscription;
    }

    public String getOpenTabsheetSouscription() {
        return openTabsheetSouscription;
    }

    public void setOpenTabsheetProcuration(String openTabsheetProcuration) {
        this.openTabsheetProcuration = openTabsheetProcuration;
    }

    public String getOpenTabsheetProcuration() {
        return openTabsheetProcuration;
    }

    public void setOpenTabsheetSupportPaiement(String openTabsheetSupportPaiement) {
        this.openTabsheetSupportPaiement = openTabsheetSupportPaiement;
    }

    public String getOpenTabsheetSupportPaiement() {
        return openTabsheetSupportPaiement;
    }

    public void setOpenTabsheetOpposition(String openTabsheetOpposition) {
        this.openTabsheetOpposition = openTabsheetOpposition;
    }

    public String getOpenTabsheetOpposition() {
        return openTabsheetOpposition;
    }

    public void setOpenTabsheetModificationDonnee(String openTabsheetModificationDonnee) {
        this.openTabsheetModificationDonnee = openTabsheetModificationDonnee;
    }

    public String getOpenTabsheetModificationDonnee() {
        return openTabsheetModificationDonnee;
    }

    public void setChoixRecherche(String choixRecherche) {
        this.choixRecherche = choixRecherche;
    }

    public String getChoixRecherche() {
        return choixRecherche;
    }

   

    public void setListeContrats(Collection listeContrats) {
        this.listeContrats = listeContrats;
    }

    public Collection getListeContrats() {
        return listeContrats;
    }

    public void setAlert(String alert) {
        this.alert = alert;
    }

    public String getAlert() {
        return alert;
    }

    public void setListeContratVal(Collection listeContratVal) {
        this.listeContratVal = listeContratVal;
    }

    public Collection getListeContratVal() {
        return listeContratVal;
    }

    public void setListeContratAtt(Collection listeContratAtt) {
        this.listeContratAtt = listeContratAtt;
    }

    public Collection getListeContratAtt() {
        return listeContratAtt;
    }

    public void setListeContratRej(Collection listeContratRej) {
        this.listeContratRej = listeContratRej;
    }

    public Collection getListeContratRej() {
        return listeContratRej;
    }

    public void setListSouscriptionAtt(List listSouscriptionAtt) {
        this.listSouscriptionAtt = listSouscriptionAtt;
    }

    public List getListSouscriptionAtt() {
        return listSouscriptionAtt;
    }

   

    public void setStatSouscription(StatSouscription statSouscription) {
        this.StatSouscription = statSouscription;
    }

    public StatSouscription getStatSouscription() {
        return StatSouscription;
    }

 

    public void setNbrSouscrVal(Long nbrSouscrVal) {
        this.nbrSouscrVal = nbrSouscrVal;
    }

    public Long getNbrSouscrVal() {
        return nbrSouscrVal;
    }

    public void setNbrSouscrAtt(Long nbrSouscrAtt) {
        this.nbrSouscrAtt = nbrSouscrAtt;
    }

    public Long getNbrSouscrAtt() {
        return nbrSouscrAtt;
    }
  
    public void setDatecloturee(String datecloturee) {
        this.datecloturee = datecloturee;
    }

    public String getDatecloturee() {
        return datecloturee;
    }

    public void setDateOuverte(String dateOuverte) {
        this.dateOuverte = dateOuverte;
    }

    public String getDateOuverte() {
        return dateOuverte;
    }

    public void setLibDom(String libDom) {
        this.libDom = libDom;
    }

    public String getLibDom() {
        return libDom;
    }

    public void setNbrTotDemandeCheq(Long nbrTotDemandeCheq) {
        this.nbrTotDemandeCheq = nbrTotDemandeCheq;
    }

    public Long getNbrTotDemandeCheq() {
        return nbrTotDemandeCheq;
    }

    public void setListDetailSouscrEnAttente(List listDetailSouscrEnAttente) {
        this.listDetailSouscrEnAttente = listDetailSouscrEnAttente;
    }

    public List getListDetailSouscrEnAttente() {
        return listDetailSouscrEnAttente;
    }

    public void setListDetailSouscrvalide(List listDetailSouscrvalide) {
        this.listDetailSouscrvalide = listDetailSouscrvalide;
    }

    public List getListDetailSouscrvalide() {
        return listDetailSouscrvalide;
    }


    public void setCodproduit(String codproduit) {
        this.codproduit = codproduit;
    }

    public String getCodproduit() {
        return codproduit;
    }

    public void setListeCreationMandat(List listeCreationMandat) {
        this.listeCreationMandat = listeCreationMandat;
    }

    public List getListeCreationMandat() {
        return listeCreationMandat;
    }

    public void setTypeClient(String typeClient) {
        this.typeClient = typeClient;
    }

    public String getTypeClient() {
        return typeClient;
    }

    public void setListeModifMandat(List listeModifMandat) {
        this.listeModifMandat = listeModifMandat;
    }

    public List getListeModifMandat() {
        return listeModifMandat;
    }

    public void setNombreTatalSouscription(Long nombreTatalSouscription) {
        this.nombreTatalSouscription = nombreTatalSouscription;
    }

    public Long getNombreTatalSouscription() {
        return nombreTatalSouscription;
    }

    public void setListSouscriptionREj(List listSouscriptionREj) {
        this.listSouscriptionREj = listSouscriptionREj;
    }

    public List getListSouscriptionREj() {
        return listSouscriptionREj;
    }

    public void setNbrSouscrRej(Long nbrSouscrRej) {
        this.nbrSouscrRej = nbrSouscrRej;
    }

    public Long getNbrSouscrRej() {
        return nbrSouscrRej;
    }

    public void setListDetailSouscrRej(List listDetailSouscrRej) {
        this.listDetailSouscrRej = listDetailSouscrRej;
    }

    public List getListDetailSouscrRej() {
        return listDetailSouscrRej;
    }

    public void setEtatContrat(String etatContrat) {
        this.etatContrat = etatContrat;
    }

    public String getEtatContrat() {
        return etatContrat;
    }

    public void setListeMandatRen(List listeMandatRen) {
        this.listeMandatRen = listeMandatRen;
    }

    public List getListeMandatRen() {
        return listeMandatRen;
    }

    public void setListeMandatAnn(List listeMandatAnn) {
        this.listeMandatAnn = listeMandatAnn;
    }

    public List getListeMandatAnn() {
        return listeMandatAnn;
    }

    public void setEtatMandat(String etatMandat) {
        this.etatMandat = etatMandat;
    }

    public String getEtatMandat() {
        return etatMandat;
    }

    public void setListeChqAttente(List listeChqAttente) {
        this.listeChqAttente = listeChqAttente;
    }

    public List getListeChqAttente() {
        return listeChqAttente;
    }

    public void setListeChqvalide(List listeChqvalide) {
        this.listeChqvalide = listeChqvalide;
    }

    public List getListeChqvalide() {
        return listeChqvalide;
    }

    public void setListeChqrejete(List listeChqrejete) {
        this.listeChqrejete = listeChqrejete;
    }

    public List getListeChqrejete() {
        return listeChqrejete;
    }

    public void setEtatCheque(String etatCheque) {
        this.etatCheque = etatCheque;
    }

    public String getEtatCheque() {
        return etatCheque;
    }

    public void setListenombreChequierDemandeParTyperRecu(List listenombreChequierDemandeParTyperRecu) {
        this.listenombreChequierDemandeParTyperRecu = listenombreChequierDemandeParTyperRecu;
    }

    public List getListenombreChequierDemandeParTyperRecu() {
        return listenombreChequierDemandeParTyperRecu;
    }

    public void setListenombreChequierDemandeParTypeDeliv(List listenombreChequierDemandeParTypeDeliv) {
        this.listenombreChequierDemandeParTypeDeliv = listenombreChequierDemandeParTypeDeliv;
    }

    public List getListenombreChequierDemandeParTypeDeliv() {
        return listenombreChequierDemandeParTypeDeliv;
    }

    public void setListeChqRecu(List listeChqRecu) {
        this.listeChqRecu = listeChqRecu;
    }

    public List getListeChqRecu() {
        return listeChqRecu;
    }

    public void setListeChqDeliv(List listeChqDeliv) {
        this.listeChqDeliv = listeChqDeliv;
    }

    public List getListeChqDeliv() {
        return listeChqDeliv;
    }

    public void setCodModif(String codModif) {
        this.codModif = codModif;
    }

    public String getCodModif() {
        return codModif;
    }


    public void setListeModificationClient(List listeModificationClient) {
        this.listeModificationClient = listeModificationClient;
    }

    public List getListeModificationClient() {
        return listeModificationClient;
    }

    public void setCodMoypTmoy(String codMoypTmoy) {
        this.codMoypTmoy = codMoypTmoy;
    }

    public String getCodMoypTmoy() {
        return codMoypTmoy;
    }

    public void setTypeOpp(String typeOpp) {
        this.typeOpp = typeOpp;
    }

    public String getTypeOpp() {
        return typeOpp;
    }

    public void setListDetailOpposition(List listDetailOpposition) {
        this.listDetailOpposition = listDetailOpposition;
    }

    public List getListDetailOpposition() {
        return listDetailOpposition;
    }

    public void setEtatopposit(String etatopposit) {
        this.etatopposit = etatopposit;
    }

    public String getEtatopposit() {
        return etatopposit;
    }


    public void setNbrOper672(Long nbrOper672) {
        this.nbrOper672 = nbrOper672;
    }

    public Long getNbrOper672() {
        return nbrOper672;
    }

    public void setMntOper672(Double mntOper672) {
        this.mntOper672 = mntOper672;
    }

    public Double getMntOper672() {
        return mntOper672;
    }

    public void setNbrOper703(Long nbrOper703) {
        this.nbrOper703 = nbrOper703;
    }

    public Long getNbrOper703() {
        return nbrOper703;
    }

    public void setMntOper703(Double mntOper703) {
        this.mntOper703 = mntOper703;
    }

    public Double getMntOper703() {
        return mntOper703;
    }
}
