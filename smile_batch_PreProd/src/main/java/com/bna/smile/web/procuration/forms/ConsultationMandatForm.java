package com.bna.smile.web.procuration.forms;

import com.bna.commun.model.TypePiece;

import com.bna.smile.web.commun.view.InitialisationView;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;


import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;


import org.hibernate.type.BlobType;


public class ConsultationMandatForm extends ActionForm {
    /**Reset all properties to their default values.
     * @param mapping The ActionMapping used to select this instance.
     * @param request The HTTP Request we are processing.
     */
    private String numCcptCcpt;
    private String codStrcStrc;
    private String codPrdPrd;
    private String typPcePers;
    private String numPcePers;
    private String nomNomPers;
    private String nomPrnPers;
    private String datOuvCcpt;
    private String datCloCcpt;
    private String codEtatCcpt;
    private String montSoldCcpt;
    private List listeMandat;
    private List listeMandataire = new ArrayList();
    private List indexMandatChoisis = new ArrayList();
    private List listContratCpt = new ArrayList();
    private List indexContratChoisis = new ArrayList();
    private String reqCode;
    private String codeTraitement;
    private String codeTraitDetail;
    private List detailMandatPersonne;
    private List detailMandatOperation;
    private String codTypMand;
    private String mandatchoisi;
    private String datCreMand;
    private String datDebMand;
    private String datFinMand;
    private String datFinIMand;
    private String ancDatFinMand;
    private String cle;
    private String libDevDev;

    private Long codStrConcer;
    private List listeMandatAvalider=new ArrayList();
    private String libStrcStrc;
    private String codEtatRecherche;
    private String codEtatAttente;
    private String codmenu;
    private String dateDebutconsult;
    private String dateFinconsult;
    private String personneexist = "true";
    private Long numDemMand;
    private String choixConsult;
    private String choixtab;
    private String choixagence;
    private String alert;
    private String alertAssocié="true";
    private String libelleConfirmation;
    
    private  InitialisationView initialisationView = new InitialisationView();
    private Long myTypeStructure;
    private String typeStrcConc; /// structure concerné
    //---------------------------------------------------------page edition mandats ccpt
    private Collection listesMandat=null;
    private String choixEdit;

    private String codStrcAgence;

    private String codtach;
    private String codOper;
    private String dateDebutOper;
    private String dateFinOper;
    private List listeCreation = new ArrayList();
    private List listeModification = new ArrayList();
    private List listeAnnulation = new ArrayList();
    private List listeRenouvellement = new ArrayList();
    private String motifReserve="";
    private String motifRejet="";
    private String observation="";
    private String typeValidation;
    private Long numRdjMand; /// reference dossier juridique
    private String capitalSoc;
    private String formeJur;
    private String siegeSoc;
    private  List ListAssocies= new ArrayList();
    private String persMorale="false";
    private String numDosJur;
    private String codStrcJur;
    private String codSignMand; 
    private Long nbrMinMand;

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


    public void setNumPcePers(String numPcePers) {
        this.numPcePers = numPcePers;
    }

    public String getNumPcePers() {
        return numPcePers;
    }

    public void setListeMandat(List listeMandat) {
        this.listeMandat = listeMandat;
    }

    public List getListeMandat() {
        return listeMandat;
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

    public void setCodEtatCcpt(String codEtatCcpt) {
        this.codEtatCcpt = codEtatCcpt;
    }

    public String getCodEtatCcpt() {
        return codEtatCcpt;
    }

    public void setDatCloCcpt(String datCloCcpt) {
        this.datCloCcpt = datCloCcpt;
    }

    public String getDatCloCcpt() {
        return datCloCcpt;
    }


    public void setMontSoldCcpt(String montSoldCcpt) {
        this.montSoldCcpt = montSoldCcpt;
    }

    public String getMontSoldCcpt() {
        return montSoldCcpt;
    }

    public void clearForm(){
    
        codStrcAgence="";
        typPcePers="";
        numPcePers="";
        nomNomPers="";
        nomPrnPers="";
        datOuvCcpt="";
        datCloCcpt="";
        codEtatCcpt="";
        montSoldCcpt="";
        listeMandat=new ArrayList();
        listeMandataire=new ArrayList();
        listeMandatAvalider=new ArrayList();
        indexMandatChoisis= new ArrayList();
        reqCode="";
        
        codeTraitDetail="";
        detailMandatPersonne=new ArrayList();
        detailMandatOperation=new ArrayList();
        listContratCpt=new ArrayList();
        codTypMand="";
        mandatchoisi=null;
        datCreMand="";
        datDebMand="";
        datFinMand="";
        ancDatFinMand="";
        cle="";
        libDevDev="";
        libStrcStrc="";
        numCcptCcpt=null;
        codStrcStrc=null;
        codPrdPrd=null;
        numDemMand=null;
        codEtatRecherche=null;
        codEtatAttente=null;
        codmenu=null;
        dateDebutconsult=null;
        dateFinconsult=null;
        choixtab="";
        choixtab=""; 
        choixagence="";
        alert="";
        myTypeStructure=null;
        typeStrcConc="";
        codtach="";
        codOper="";
        dateDebutOper="";
        dateFinOper="";
        listeCreation=new ArrayList();
        listeModification=new ArrayList();
        listeAnnulation=new ArrayList();
        listeRenouvellement=new ArrayList();
        numRdjMand=null;
        numDosJur="";
        codStrcJur="";
    }

    public void setReqCode(String reqCode) {
        this.reqCode = reqCode;
    }

    public String getReqCode() {
        return reqCode;
    }

    public void setListeMandataire(List listeMandataire) {
        this.listeMandataire = listeMandataire;
    }

    public List getListeMandataire() {
        return listeMandataire;
    }

    public void setTypPcePers(String typPcePers) {
        this.typPcePers = typPcePers;
    }

    public String getTypPcePers() {
        return typPcePers;
    }

    public void setIndexMandatChoisis(List indexMandatChoisis) {
        this.indexMandatChoisis = indexMandatChoisis;
    }

    public List getIndexMandatChoisis() {
        return indexMandatChoisis;
    }

    public void setCodeTraitement(String codeTraitement) {
        this.codeTraitement = codeTraitement;
    }

    public String getCodeTraitement() {
        return codeTraitement;
    }

    public void setDetailMandatPersonne(List detailMandatPersonne) {
        this.detailMandatPersonne = detailMandatPersonne;
    }

    public List getDetailMandatPersonne() {
        return detailMandatPersonne;
    }

    public void setDetailMandatOperation(List detailMandatOperation) {
        this.detailMandatOperation = detailMandatOperation;
    }

    public List getDetailMandatOperation() {
        return detailMandatOperation;
    }

    public void setCodTypMand(String codTypMand) {
        this.codTypMand = codTypMand;
    }

    public String getCodTypMand() {
        return codTypMand;
    }


   

    public void setCle(String cle) {
        this.cle = cle;
    }

    public String getCle() {
        return cle;
    }

    public void setLibDevDev(String libDevDev) {
        this.libDevDev = libDevDev;
    }

    public String getLibDevDev() {
        return libDevDev;
    }

    public void setDatCreMand(String datCreMand) {
        this.datCreMand = datCreMand;
    }

    public String getDatCreMand() {
        return datCreMand;
    }

    public void setDatDebMand(String datDebMand) {
        this.datDebMand = datDebMand;
    }

    public String getDatDebMand() {
        return datDebMand;
    }

    public void setDatFinMand(String datFinMand) {
        this.datFinMand = datFinMand;
    }

    public String getDatFinMand() {
        return datFinMand;
    }


    public void setCodeTraitDetail(String codeTraitDetail) {
        this.codeTraitDetail = codeTraitDetail;
    }

    public String getCodeTraitDetail() {
        return codeTraitDetail;
    }



    public void setCodStrConcer(Long codStrConcer) {
        this.codStrConcer = codStrConcer;
    }

    public Long getCodStrConcer() {
        return codStrConcer;
    }

    public void setListeMandatAvalider(List listeMandatAvaliser) {
        this.listeMandatAvalider = listeMandatAvaliser;
    }

    public List getListeMandatAvalider() {
        return listeMandatAvalider;
    }


    public void setLibStrcStrc(String libStrcStrc) {
        this.libStrcStrc = libStrcStrc;
    }

    public String getLibStrcStrc() {
        return libStrcStrc;
    }

    public void setCodEtatRecherche(String codEtatRecherche) {
        this.codEtatRecherche = codEtatRecherche;
    }

    public String getCodEtatRecherche() {
        return codEtatRecherche;
    }

    public void setListContratCpt(List listContratCpt) {
        this.listContratCpt = listContratCpt;
    }

    public List getListContratCpt() {
        return listContratCpt;
    }

    public void setIndexContratChoisis(List indexContratChoisis) {
        this.indexContratChoisis = indexContratChoisis;
    }

    public List getIndexContratChoisis() {
        return indexContratChoisis;
    }

    public void setPersonneexist(String personneexist) {
        this.personneexist = personneexist;
    }

    public String getPersonneexist() {
        return personneexist;
    }


    public void setNumDemMand(Long numDemMand) {
        this.numDemMand = numDemMand;
    }

    public Long getNumDemMand() {
        return numDemMand;
    }

    public void setCodEtatAttente(String codEtatAttente) {
        this.codEtatAttente = codEtatAttente;
    }

    public String getCodEtatAttente() {
        return codEtatAttente;
    }

    public void setCodmenu(String codmenu) {
        this.codmenu = codmenu;
    }

    public String getCodmenu() {
        return codmenu;
    }

    public void setDateDebutconsult(String dateDebutconsult) {
        this.dateDebutconsult = dateDebutconsult;
    }

    public String getDateDebutconsult() {
        return dateDebutconsult;
    }

    public void setDateFinconsult(String dateFinconsult) {
        this.dateFinconsult = dateFinconsult;
    }

    public String getDateFinconsult() {
        return dateFinconsult;
    }

    public void setChoixConsult(String choixConsult) {
        this.choixConsult = choixConsult;
    }

    public String getChoixConsult() {
        return choixConsult;
    }

    public void setMandatchoisi(String mandatchoisi) {
        this.mandatchoisi = mandatchoisi;
    }

    public String getMandatchoisi() {
        return mandatchoisi;
    }

    public void setChoixtab(String choixtab) {
        this.choixtab = choixtab;
    }

    public String getChoixtab() {
        return choixtab;
    }

    public void setChoixagence(String choixagence) {
        this.choixagence = choixagence;
    }

    public String getChoixagence() {
        return choixagence;
    }

    public void setAlert(String alert) {
        this.alert = alert;
    }

    public String getAlert() {
        return alert;
    }

    public void setAncDatFinMand(String ancDatFinMand) {
        this.ancDatFinMand = ancDatFinMand;
    }

    public String getAncDatFinMand() {
        return ancDatFinMand;
    }

    public void setLibelleConfirmation(String libelleConfirmation) {
        this.libelleConfirmation = libelleConfirmation;
    }

    public String getLibelleConfirmation() {
        return libelleConfirmation;
    }

    public void setInitialisationView(InitialisationView initialisationView) {
        this.initialisationView = initialisationView;
    }

    public InitialisationView getInitialisationView() {
        return initialisationView;
    }

    public void setDatFinIMand(String datFinIMand) {
        this.datFinIMand = datFinIMand;
    }

    public String getDatFinIMand() {
        return datFinIMand;
    }

 
    public void setListesMandat(Collection listesMandat) {
        this.listesMandat = listesMandat;
    }

    public Collection getListesMandat() {
        return listesMandat;
    }

    public void setChoixEdit(String choixEdit) {
        this.choixEdit = choixEdit;
    }

    public String getChoixEdit() {
        return choixEdit;
    }


    public void setMyTypeStructure(Long myTypeStructure) {
        this.myTypeStructure = myTypeStructure;
    }

    public Long getMyTypeStructure() {
        return myTypeStructure;
    }

    public void setTypeStrcConc(String typeStrcConc) {
        this.typeStrcConc = typeStrcConc;
    }

    public String getTypeStrcConc() {
        return typeStrcConc;
    }



    public void setCodStrcAgence(String codStrcAgence) {
        this.codStrcAgence = codStrcAgence;
    }

    public String getCodStrcAgence() {
        return codStrcAgence;
    }


    public void setCodtach(String codtach) {
        this.codtach = codtach;
    }

    public String getCodtach() {
        return codtach;
    }

    public void setCodOper(String codOper) {
        this.codOper = codOper;
    }

    public String getCodOper() {
        return codOper;
    }

    public void setDateDebutOper(String dateDebutOper) {
        this.dateDebutOper = dateDebutOper;
    }

    public String getDateDebutOper() {
        return dateDebutOper;
    }

    public void setDateFinOper(String dateFinOper) {
        this.dateFinOper = dateFinOper;
    }

    public String getDateFinOper() {
        return dateFinOper;
    }

    public void setListeCreation(List listeCreation) {
        this.listeCreation = listeCreation;
    }

    public List getListeCreation() {
        return listeCreation;
    }

    public void setListeModification(List listeModification) {
        this.listeModification = listeModification;
    }

    public List getListeModification() {
        return listeModification;
    }

    public void setListeAnnulation(List listeAnnulation) {
        this.listeAnnulation = listeAnnulation;
    }

    public List getListeAnnulation() {
        return listeAnnulation;
    }

    public void setListeRenouvellement(List listeRenouvellement) {
        this.listeRenouvellement = listeRenouvellement;
    }

    public List getListeRenouvellement() {
        return listeRenouvellement;
    }

    public void setObservation(String observation) {
        this.observation = observation;
    }

    public String getObservation() {
        return observation;
    }

    public void setMotifReserve(String motifReserve) {
        this.motifReserve = motifReserve;
    }

    public String getMotifReserve() {
        return motifReserve;
    }

    public void setMotifRejet(String motifRejet) {
        this.motifRejet = motifRejet;
    }

    public String getMotifRejet() {
        return motifRejet;
    }

    public void setTypeValidation(String typeValidation) {
        this.typeValidation = typeValidation;
    }

    public String getTypeValidation() {
        return typeValidation;
    }

    public void setNumRdjMand(Long numRdjMand) {
        this.numRdjMand = numRdjMand;
    }

    public Long getNumRdjMand() {
        return numRdjMand;
    }

    public void setCapitalSoc(String capitalSoc) {
        this.capitalSoc = capitalSoc;
    }

    public String getCapitalSoc() {
        return capitalSoc;
    }

    public void setFormeJur(String formeJur) {
        this.formeJur = formeJur;
    }

    public String getFormeJur() {
        return formeJur;
    }

    public void setSiegeSoc(String siegeSoc) {
        this.siegeSoc = siegeSoc;
    }

    public String getSiegeSoc() {
        return siegeSoc;
    }


    public void setListAssocies(List listAssocies) {
        this.ListAssocies = listAssocies;
    }

    public List getListAssocies() {
        return ListAssocies;
    }


    public void setPersMorale(String persMorale) {
        this.persMorale = persMorale;
    }

    public String getPersMorale() {
        return persMorale;
    }

    public void setAlertAssocié(String alertAssocié) {
        this.alertAssocié = alertAssocié;
    }

    public String getAlertAssocié() {
        return alertAssocié;
    }

    public void setNumDosJur(String numDosJur) {
        this.numDosJur = numDosJur;
    }

    public String getNumDosJur() {
        return numDosJur;
    }

    public void setCodStrcJur(String codStrcJur) {
        this.codStrcJur = codStrcJur;
    }

    public String getCodStrcJur() {
        return codStrcJur;
    }

    public void setCodSignMand(String codSignMand) {
        this.codSignMand = codSignMand;
    }

    public String getCodSignMand() {
        return codSignMand;
    }

    public void setNbrMinMand(Long nbrMinMand) {
        this.nbrMinMand = nbrMinMand;
    }

    public Long getNbrMinMand() {
        return nbrMinMand;
    }
}

