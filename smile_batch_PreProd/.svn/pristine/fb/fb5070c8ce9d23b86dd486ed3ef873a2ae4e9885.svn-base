package com.bna.smile.web.banqueAssurance.forms;

import com.bna.commun.model.AdhesionAssVie;
import com.bna.commun.model.Assureur;
import com.bna.commun.model.TarifAssVie;
import com.bna.smile.web.banqueAssurance.View.AdhesionAssVieView;
import com.bna.smile.web.banqueAssurance.View.TarifAssVieView;
import com.bna.smile.web.commun.view.ContratView;
import com.bna.smile.web.commun.view.InitialisationView;

import java.util.ArrayList;
import java.util.Collection;
import org.apache.struts.action.ActionForm;

public class AssuranceVieForm extends ActionForm {
   
    private InitialisationView initialisationView = new InitialisationView();
    private String titrePage;
    private AdhesionAssVie adhesionAssVie = new AdhesionAssVie();
    private AdhesionAssVieView adhesionAssVieView = new AdhesionAssVieView();
    private Long typPcePers;
    private String numPcePce = new String("");
    private String nomNomPers = new String("");
    private String nomPrnPers;
    private boolean personneExist;
    private Collection listContratCpt;
    private Collection listeAdhesionAssVie;
    private Collection listeAdhesionAssVieView;
    private String etatAdhAssVieExist;
    private String CptFacturaction;
    private ContratView contratView = new ContratView();
    private String cleContratChoisi;
    private String contratChoisi;
    private String cleAdhesionChoisi;
    private String cleAssureurChoisi;
    private String adhesionChoisi;
    private String assureurChoisi;
    private String alert;
    private String libelleConfirmation;
    private String titreConfirmation;
    private Collection listeAssureurs;
    private String codAssureur;
    private String alertContrat;
    private String choixRecherche;
    private String etatContrat;
    private String ageClient;
    private String datNaissanceClt;
    private boolean boolAgeClt = true;
    private boolean boolDecesClt = false;
    private boolean resAvantDeuMois = false;
    private Long codStrcStrcRech;
    private Long codPrdPrdRech;
    private Long numCcptCcptRech;
    private Assureur assureur= new Assureur();
    private TarifAssVieView tarifAssVie101 = new TarifAssVieView();
    private TarifAssVieView tarifAssVie103 = new TarifAssVieView();
    private TarifAssVieView tarifAssVie109 = new TarifAssVieView();
    private TarifAssVieView tarifAssVie115 = new TarifAssVieView();
    private String dateDebRecherch="";
    private String dateFinRecherch="";
    private String structureRech="";
    private String etatAdhesion="";
    
    public AssuranceVieForm() {
    }
    public void clearForm(){
        adhesionAssVie = new AdhesionAssVie();
        adhesionAssVieView = new AdhesionAssVieView();
        listeAdhesionAssVieView = new ArrayList();
        assureur= new Assureur();
        tarifAssVie101 = new TarifAssVieView();
        tarifAssVie103 = new TarifAssVieView();
        tarifAssVie109 = new TarifAssVieView();
        tarifAssVie115 = new TarifAssVieView();
        typPcePers = new Long(1);
        numPcePce = "";
        nomNomPers = "";
        nomPrnPers = "";
        personneExist=true;
        listContratCpt = null;
        listeAdhesionAssVie= null;
        etatAdhAssVieExist="";
        CptFacturaction="";
        contratView = new ContratView();
        cleContratChoisi = "";
        cleAdhesionChoisi="";
        adhesionChoisi="";
        alert="";
        codAssureur="";
        alertContrat="";
        etatContrat="";
        ageClient="";
        datNaissanceClt="";
        boolAgeClt =true;
        boolDecesClt = false;
        codStrcStrcRech = null;
        codPrdPrdRech = null;
        numCcptCcptRech = null;
        resAvantDeuMois = false;
        etatAdhesion="";
        dateDebRecherch="";
        dateFinRecherch="";
        structureRech="";
        }

    public void setInitialisationView(InitialisationView initialisationView) {
        this.initialisationView = initialisationView;
    }

    public InitialisationView getInitialisationView() {
        return initialisationView;
    }

    public void setTitrePage(String titrePage) {
        this.titrePage = titrePage;
    }

    public String getTitrePage() {
        return titrePage;
    }

    public void setAdhesionAssVie(AdhesionAssVie adhesionAssVie) {
        this.adhesionAssVie = adhesionAssVie;
    }

    public AdhesionAssVie getAdhesionAssVie() {
        return adhesionAssVie;
    }

    public void setTypPcePers(Long typPcePers) {
        this.typPcePers = typPcePers;
    }

    public Long getTypPcePers() {
        return typPcePers;
    }

    public void setNumPcePce(String numPcePce) {
        this.numPcePce = numPcePce;
    }

    public String getNumPcePce() {
        return numPcePce;
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

    public void setPersonneExist(boolean personneExist) {
        this.personneExist = personneExist;
    }

    public boolean isPersonneExist() {
        return personneExist;
    }

    public void setListContratCpt(Collection listContratCpt) {
        this.listContratCpt = listContratCpt;
    }

    public Collection getListContratCpt() {
        return listContratCpt;
    }

    public void setListeAdhesionAssVie(Collection listeAdhesionAssVie) {
        this.listeAdhesionAssVie = listeAdhesionAssVie;
    }

    public Collection getListeAdhesionAssVie() {
        return listeAdhesionAssVie;
    }

    public void setEtatAdhAssVieExist(String etatAdhAssVieExist) {
        this.etatAdhAssVieExist = etatAdhAssVieExist;
    }

    public String getEtatAdhAssVieExist() {
        return etatAdhAssVieExist;
    }

    public void setCptFacturaction(String cptFacturaction) {
        this.CptFacturaction = cptFacturaction;
    }

    public String getCptFacturaction() {
        return CptFacturaction;
    }

    public void setContratView(ContratView contratView) {
        this.contratView = contratView;
    }

    public ContratView getContratView() {
        return contratView;
    }

    public void setCleContratChoisi(String cleContratChoisi) {
        this.cleContratChoisi = cleContratChoisi;
    }

    public String getCleContratChoisi() {
        return cleContratChoisi;
    }


    public void setAdhesionAssVieView(AdhesionAssVieView adhesionAssVieView) {
        this.adhesionAssVieView = adhesionAssVieView;
    }

    public AdhesionAssVieView getAdhesionAssVieView() {
        return adhesionAssVieView;
    }

    public void setContratChoisi(String contratChoisi) {
        this.contratChoisi = contratChoisi;
    }

    public String getContratChoisi() {
        return contratChoisi;
    }

    public void setAlert(String alert) {
        this.alert = alert;
    }

    public String getAlert() {
        return alert;
    }


    public void setLibelleConfirmation(String libelleConfirmation) {
        this.libelleConfirmation = libelleConfirmation;
    }

    public String getLibelleConfirmation() {
        return libelleConfirmation;
    }

    public void setTitreConfirmation(String titreConfirmation) {
        this.titreConfirmation = titreConfirmation;
    }

    public String getTitreConfirmation() {
        return titreConfirmation;
    }


    public void setCleAdhesionChoisi(String cleAdhesionChoisi) {
        this.cleAdhesionChoisi = cleAdhesionChoisi;
    }

    public String getCleAdhesionChoisi() {
        return cleAdhesionChoisi;
    }

    public void setAdhesionChoisi(String adhesionChoisi) {
        this.adhesionChoisi = adhesionChoisi;
    }

    public String getAdhesionChoisi() {
        return adhesionChoisi;
    }

    public void setListeAssureurs(Collection listeAssureurs) {
        this.listeAssureurs = listeAssureurs;
    }

    public Collection getListeAssureurs() {
        return listeAssureurs;
    }

    public void setCodAssureur(String codAssureur) {
        this.codAssureur = codAssureur;
    }

    public String getCodAssureur() {
        return codAssureur;
    }
    public void setListeAdhesionAssVieView(Collection listeAdhesionAssVieView) {
        this.listeAdhesionAssVieView = listeAdhesionAssVieView;
    }

    public Collection getListeAdhesionAssVieView() {
        return listeAdhesionAssVieView;
    }

    public void setAlertContrat(String alertContrat) {
        this.alertContrat = alertContrat;
    }

    public String getAlertContrat() {
        return alertContrat;
    }

    public void setChoixRecherche(String choixRecherche) {
        this.choixRecherche = choixRecherche;
    }

    public String getChoixRecherche() {
        return choixRecherche;
    }

    public void setEtatContrat(String etatContrat) {
        this.etatContrat = etatContrat;
    }

    public String getEtatContrat() {
        return etatContrat;
    }

    public void setAgeClient(String ageClient) {
        this.ageClient = ageClient;
    }

    public String getAgeClient() {
        return ageClient;
    }

    public void setDatNaissanceClt(String datNaissanceClt) {
        this.datNaissanceClt = datNaissanceClt;
    }

    public String getDatNaissanceClt() {
        return datNaissanceClt;
    }

    public void setBoolAgeClt(boolean boolAgeClt) {
        this.boolAgeClt = boolAgeClt;
    }

    public boolean isBoolAgeClt() {
        return boolAgeClt;
    }

    public void setBoolDecesClt(boolean boolDecesClt) {
        this.boolDecesClt = boolDecesClt;
    }

    public boolean isBoolDecesClt() {
        return boolDecesClt;
    }

    public void setCodStrcStrcRech(Long codStrcStrcRech) {
        this.codStrcStrcRech = codStrcStrcRech;
    }

    public Long getCodStrcStrcRech() {
        return codStrcStrcRech;
    }

    public void setCodPrdPrdRech(Long codPrdPrdRech) {
        this.codPrdPrdRech = codPrdPrdRech;
    }

    public Long getCodPrdPrdRech() {
        return codPrdPrdRech;
    }

    public void setNumCcptCcptRech(Long numCcptCcptRech) {
        this.numCcptCcptRech = numCcptCcptRech;
    }

    public Long getNumCcptCcptRech() {
        return numCcptCcptRech;
    }

    public void setAssureur(Assureur assureur) {
        this.assureur = assureur;
    }

    public Assureur getAssureur() {
        return assureur;
    }

    public void setTarifAssVie101(TarifAssVieView tarifAssVie101) {
        this.tarifAssVie101 = tarifAssVie101;
    }

    public TarifAssVieView getTarifAssVie101() {
        return tarifAssVie101;
    }

    public void setTarifAssVie103(TarifAssVieView tarifAssVie103) {
        this.tarifAssVie103 = tarifAssVie103;
    }

    public TarifAssVieView getTarifAssVie103() {
        return tarifAssVie103;
    }

    public void setTarifAssVie109(TarifAssVieView tarifAssVie109) {
        this.tarifAssVie109 = tarifAssVie109;
    }

    public TarifAssVieView getTarifAssVie109() {
        return tarifAssVie109;
    }

    public void setTarifAssVie115(TarifAssVieView tarifAssVie115) {
        this.tarifAssVie115 = tarifAssVie115;
    }

    public TarifAssVieView getTarifAssVie115() {
        return tarifAssVie115;
    }

    public void setAssureurChoisi(String assureurChoisi) {
        this.assureurChoisi = assureurChoisi;
    }

    public String getAssureurChoisi() {
        return assureurChoisi;
    }

    public void setCleAssureurChoisi(String cleAssureurChoisi) {
        this.cleAssureurChoisi = cleAssureurChoisi;
    }

    public String getCleAssureurChoisi() {
        return cleAssureurChoisi;
    }

    public void setResAvantDeuMois(boolean resAvantDeuMois) {
        this.resAvantDeuMois = resAvantDeuMois;
    }

    public boolean isResAvantDeuMois() {
        return resAvantDeuMois;
    }

    public void setDateDebRecherch(String dateDebRecherch) {
        this.dateDebRecherch = dateDebRecherch;
    }

    public String getDateDebRecherch() {
        return dateDebRecherch;
    }

    public void setDateFinRecherch(String dateFinRecherch) {
        this.dateFinRecherch = dateFinRecherch;
    }

    public String getDateFinRecherch() {
        return dateFinRecherch;
    }

    public void setStructureRech(String structureRech) {
        this.structureRech = structureRech;
    }

    public String getStructureRech() {
        return structureRech;
    }

    public void setEtatAdhesion(String etatAdhesion) {
        this.etatAdhesion = etatAdhesion;
    }

    public String getEtatAdhesion() {
        return etatAdhesion;
    }
}
