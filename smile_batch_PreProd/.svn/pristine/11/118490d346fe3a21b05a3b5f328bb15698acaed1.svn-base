package com.bna.smile.web.clotureJournee.forms;

import com.bna.smile.web.commun.view.InitialisationView;

import java.util.ArrayList;
import java.util.List;

import org.apache.struts.action.ActionForm;

public class TableauDeBordForm extends ActionForm {
    public TableauDeBordForm() {
    }

    private InitialisationView initialisationView = new InitialisationView();
    private String dateJournee;
    private Long codeStructure;

    //----------------------------------
    //------ Souscription
    private List listNombreSouscriptionParTypeContrat = new ArrayList(0);
    private List listSouscriptionAtt = new ArrayList(0);
    private List listNombreSouscAttParTypeClient = new ArrayList(0);
    private List listNombreSouscriptionParTypeClient = new ArrayList(0);
    private Long nombreTatalSouscription = Long.valueOf(0);
    private List listNombreSignatureParTypeContrat = new ArrayList(0);
    private Long nombreTatalSignature = Long.valueOf(0);
    private Long nbrSouscrAtt = Long.valueOf(0);
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

    //--------------------------------------
    //------- Support de paiements
    private Long nombreChequierDemande = Long.valueOf(0);
    private List listenombreChequierDemandeParType  = new ArrayList(0);
    private Long nombreChequierDemandeAttente       = Long.valueOf(0);
    private List listenombreChequierDemandeParTypeAttente = new ArrayList(0);
    private Long nombreChequierDemandeRejeter       = Long.valueOf(0);
    private List listenombreChequierDemandeParTypeRejeter = new ArrayList(0);

    private Long nombreCarteDemandeValide       = Long.valueOf(0);
    private Long nombreCarteDemandeNonValide    = Long.valueOf(0);
    private Long nombreCarteRecu        = Long.valueOf(0);
    private Long nombreCarteAnnule      = Long.valueOf(0);
    private Long nombreCarteDelivre     = Long.valueOf(0);
    private Long nombreCarteRejete      = Long.valueOf(0);
    
    private List listenombreCarteDemandeParType = new ArrayList(0);
    private List listenombreCarteDemandeNonValideParType = new ArrayList(0);
    private List listenombreCarteRecuParType    = new ArrayList(0);
    private List listenombreCarteAnnuleParType  = new ArrayList(0);
    private List listenombreCarteDelivreParType = new ArrayList(0);
    private List listenombreCarteRejeteParType = new ArrayList(0);
    
    
    //--------------------------------------
    //------- Opposition sur moyens de paiement

    private List listeOppositionParType     = new ArrayList(0);
    private Long nombreOpposition    = Long.valueOf(0);
    private List listeLeveOppositionParType = new ArrayList(0);
    private Long nombreLeveeOpposition    = Long.valueOf(0);
    
    //--------------------------------------
    //------- Modification données client

    private List listeModificationDonneeParType     = new ArrayList(0);
    private Long nombreModificationDonnees          = Long.valueOf(0);

    private String openTabsheetSouscription          ="";
    private String openTabsheetProcuration           ="";
    private String openTabsheetSupportPaiement       ="";
    private String openTabsheetOpposition            ="";
    private String openTabsheetModificationDonnee    ="";
    private String choixRecherche="";

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
        this.listNombreSouscriptionParTypeContrat = 
                listNombreSouscriptionParTypeContrat;
    }

    public List getListNombreSouscriptionParTypeContrat() {
        return listNombreSouscriptionParTypeContrat;
    }

    public void setListNombreSouscriptionParTypeClient(List listNombreSouscriptionParTypeClient) {
        this.listNombreSouscriptionParTypeClient = 
                listNombreSouscriptionParTypeClient;
    }

    public List getListNombreSouscriptionParTypeClient() {
        return listNombreSouscriptionParTypeClient;
    }

    public void setNombreTatalSouscription(Long nombreTatalSouscription) {
        this.nombreTatalSouscription = nombreTatalSouscription;
    }

    public Long getNombreTatalSouscription() {
        return nombreTatalSouscription;
    }

    public void setListMandatCreationParTypeContrat(List listMandatCreationParTypeContrat) {
        this.listMandatCreationParTypeContrat = 
                listMandatCreationParTypeContrat;
    }

    public List getListMandatCreationParTypeContrat() {
        return listMandatCreationParTypeContrat;
    }

    public void setListMandatRenouvleParTypeContrat(List listMandatRenouvleParTypeContrat) {
        this.listMandatRenouvleParTypeContrat = 
                listMandatRenouvleParTypeContrat;
    }

    public List getListMandatRenouvleParTypeContrat() {
        return listMandatRenouvleParTypeContrat;
    }

    public void setListMandatModifieParTypeContrat(List listMandatModifieParTypeContrat) {
        this.listMandatModifieParTypeContrat = listMandatModifieParTypeContrat;
    }

    public List getListMandatModifieParTypeContrat() {
        return listMandatModifieParTypeContrat;
    }

    public void setListMandatAnnuleParTypeContrat(List listMandatAnnuleParTypeContrat) {
        this.listMandatAnnuleParTypeContrat = listMandatAnnuleParTypeContrat;
    }

    public List getListMandatAnnuleParTypeContrat() {
        return listMandatAnnuleParTypeContrat;
    }

    public void setNombreMandatCree(Long nombreMandatCree) {
        this.nombreMandatCree = nombreMandatCree;
    }

    public Long getNombreMandatCree() {
        return nombreMandatCree;
    }

    public void setNombreMandatRenouvle(Long nombreMandatRenouvle) {
        this.nombreMandatRenouvle = nombreMandatRenouvle;
    }

    public Long getNombreMandatRenouvle() {
        return nombreMandatRenouvle;
    }

    public void setNombreMandatModifie(Long nombreMandatModifie) {
        this.nombreMandatModifie = nombreMandatModifie;
    }

    public Long getNombreMandatModifie() {
        return nombreMandatModifie;
    }

    public void setNombreMandatAnnule(Long nombreMandatAnnule) {
        this.nombreMandatAnnule = nombreMandatAnnule;
    }

    public Long getNombreMandatAnnule() {
        return nombreMandatAnnule;
    }

    public void setNombreChequierDemande(Long nombreChequierDemande) {
        this.nombreChequierDemande = nombreChequierDemande;
    }

    public Long getNombreChequierDemande() {
        return nombreChequierDemande;
    }

    public void setListenombreChequierDemandeParType(List listenombreChequierDemandeParType) {
        this.listenombreChequierDemandeParType = 
                listenombreChequierDemandeParType;
    }

    public List getListenombreChequierDemandeParType() {
        return listenombreChequierDemandeParType;
    }

    public void setListenombreCarteDemandeParType(List listenombreCarteDemandeParType) {
        this.listenombreCarteDemandeParType = listenombreCarteDemandeParType;
    }

    public List getListenombreCarteDemandeParType() {
        return listenombreCarteDemandeParType;
    }

    public void setNombreCarteDemandeValide(Long nombreCarteDemandeValide) {
        this.nombreCarteDemandeValide = nombreCarteDemandeValide;
    }

    public Long getNombreCarteDemandeValide() {
        return nombreCarteDemandeValide;
    }

    public void setNombreCarteDemandeNonValide(Long nombreCarteDemandeNonValide) {
        this.nombreCarteDemandeNonValide = nombreCarteDemandeNonValide;
    }

    public Long getNombreCarteDemandeNonValide() {
        return nombreCarteDemandeNonValide;
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

    public void setListeLeveOppositionParType(List listeLeveOppositionParType) {
        this.listeLeveOppositionParType = listeLeveOppositionParType;
    }

    public List getListeLeveOppositionParType() {
        return listeLeveOppositionParType;
    }

    public void setNombreOpposition(Long nombreOpposition) {
        this.nombreOpposition = nombreOpposition;
    }

    public Long getNombreOpposition() {
        return nombreOpposition;
    }

    public void setNombreLeveeOpposition(Long nombreLeveeOpposition) {
        this.nombreLeveeOpposition = nombreLeveeOpposition;
    }

    public Long getNombreLeveeOpposition() {
        return nombreLeveeOpposition;
    }

    public void setListenombreCarteRecuParType(List listenombreCarteRecuParType) {
        this.listenombreCarteRecuParType = listenombreCarteRecuParType;
    }

    public List getListenombreCarteRecuParType() {
        return listenombreCarteRecuParType;
    }

    public void setListenombreCarteAnnuleParType(List listenombreCarteAnnuleParType) {
        this.listenombreCarteAnnuleParType = listenombreCarteAnnuleParType;
    }

    public List getListenombreCarteAnnuleParType() {
        return listenombreCarteAnnuleParType;
    }

    public void setListenombreCarteDelivreParType(List listenombreCarteDelivreParType) {
        this.listenombreCarteDelivreParType = listenombreCarteDelivreParType;
    }

    public List getListenombreCarteDelivreParType() {
        return listenombreCarteDelivreParType;
    }

    public void setNombreCarteRecu(Long nombreCarteRecu) {
        this.nombreCarteRecu = nombreCarteRecu;
    }

    public Long getNombreCarteRecu() {
        return nombreCarteRecu;
    }

    public void setNombreCarteAnnule(Long nombreCarteAnnule) {
        this.nombreCarteAnnule = nombreCarteAnnule;
    }

    public Long getNombreCarteAnnule() {
        return nombreCarteAnnule;
    }

    public void setNombreCarteDelivre(Long nombreCarteDelivre) {
        this.nombreCarteDelivre = nombreCarteDelivre;
    }

    public Long getNombreCarteDelivre() {
        return nombreCarteDelivre;
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

    public void setListenombreCarteDemandeNonValideParType(List listenombreCarteDemandeNonValideParType) {
        this.listenombreCarteDemandeNonValideParType = listenombreCarteDemandeNonValideParType;
    }

    public List getListenombreCarteDemandeNonValideParType() {
        return listenombreCarteDemandeNonValideParType;
    }

    public void setNombreCarteRejete(Long nombreCarteRejete) {
        this.nombreCarteRejete = nombreCarteRejete;
    }

    public Long getNombreCarteRejete() {
        return nombreCarteRejete;
    }

    public void setListenombreCarteRejeteParType(List listenombreCarteRejeteParType) {
        this.listenombreCarteRejeteParType = listenombreCarteRejeteParType;
    }

    public List getListenombreCarteRejeteParType() {
        return listenombreCarteRejeteParType;
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

    public void setListSouscriptionAtt(List listSouscriptionAtt) {
        this.listSouscriptionAtt = listSouscriptionAtt;
    }

    public List getListSouscriptionAtt() {
        return listSouscriptionAtt;
    }

    public void setListNombreSouscAttParTypeClient(List listNombreSouscAttParTypeClient) {
        this.listNombreSouscAttParTypeClient = listNombreSouscAttParTypeClient;
    }

    public List getListNombreSouscAttParTypeClient() {
        return listNombreSouscAttParTypeClient;
    }

    public void setNbrSouscrAtt(Long nbrSouscrAtt) {
        this.nbrSouscrAtt = nbrSouscrAtt;
    }

    public Long getNbrSouscrAtt() {
        return nbrSouscrAtt;
    }
}
