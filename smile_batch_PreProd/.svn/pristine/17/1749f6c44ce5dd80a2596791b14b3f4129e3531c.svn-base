package com.bna.smile.web.procuration.forms;

import com.bna.commun.model.Mandat;
import com.bna.commun.util.DateHandler;import com.bna.commun.util.StrHandler;

import fr.improve.struts.taglib.layout.datagrid.Datagrid;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

public class ConsultMandantContratForm extends ActionForm {
    /**Reset all properties to their default values.
     * @param mapping The ActionMapping used to select this instance.
     * @param request The HTTP Request we are processing.
     */
    private Long typPcePers;
    private String numPcePce;
    private String nomNomPers;
    private String nomPrnPers;
    private List listContratCpt = new ArrayList();
    private String reqCode;
    private List indexContratChoisis = new ArrayList();
    private List listContratChoisis = new ArrayList();
    private String codeTraitement = 
        "I"; /// code de l'operation à faire (insertion : "I" ,modification : "M" annulation : "A" ,modification : "M",consultation : "C")
    private Long numMandMand;
    private Long numPereMand;
    private String codEtatMand;
    private Mandat mandat;

    private String datDebMand = DateHandler.dateToStr(new Date());
    private String datFinMand;
    private String datCreMand = DateHandler.dateToStr(new Date());
    private String codTypMand;
    private String codSignMand; /// cas mandat général
    private Long nbrMinMand; /// cas mandat général
    private Long codStrcMand;
    private String typeStrcConc; /// structure concerné

    private List listMandatPersonne = new ArrayList();
    private List listMandatOperation = new ArrayList();
    private Datagrid listMandatPersonneGrid;
    private Datagrid listMandatOperationGrid;
    private Long myTypeStructure;
    private Long numRdjMand; /// reference dossier juridique
    private String datJustMand;
    private Long numDemMand;
    private String typeValidation;
    private String motifReserve;
    private String motifRejet;
    private String datEnvcMand;
    private String datValcMand;
    private String datEnvmMand;
    private String datValmMand;
    private String observation;

    /* Variable de test */
    private String personneexist = "true";
    private String casSouscriptionContratCompte = "";
    private String etatFormCreationPersonne="0";
    private String clientAgence;
    private String alertAssocié="true";
    
    private String capitalSoc;
    private String formeJur;
    private String siegeSoc;
    private  List ListAssocies= new ArrayList();
    private String persMorale="false";

    public void reset(ActionMapping mapping, HttpServletRequest request) {
        //  super.reset(mapping, request);
    }

    /**Validate all properties to their default values.
     * @param mapping The ActionMapping used to select this instance.
     * @param request The HTTP Request we are processing.
     * @return ActionErrors A list of all errors found.
     */
    public ActionErrors validate(ActionMapping mapping, 
                                 HttpServletRequest request) {
        return super.validate(mapping, request);
    }


    public void clearForm() {

        typPcePers = new Long(1);
        numPcePce = "";
        nomNomPers = "";
        nomPrnPers = "";
        casSouscriptionContratCompte = "";
        listContratCpt.clear();
        ListAssocies.clear();
        persMorale="false";
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

    public void setListContratCpt(List listContratCpt) {
        this.listContratCpt = listContratCpt;
    }

    public List getListContratCpt() {
        return listContratCpt;
    }

    public void setReqCode(String reqCode) {
        this.reqCode = reqCode;
    }

    public String getReqCode() {
        return reqCode;
    }


    public void setCodeTraitement(String codeTraitement) {
        this.codeTraitement = codeTraitement;
    }

    public String getCodeTraitement() {
        return codeTraitement;
    }

    public void setIndexContratChoisis(List indexContratChoisis) {
        this.indexContratChoisis = indexContratChoisis;
    }

    public List getIndexContratChoisis() {
        return indexContratChoisis;
    }

    public void setListContratChoisis(List listContratChoisis) {
        this.listContratChoisis = listContratChoisis;
    }

    public List getListContratChoisis() {
        return listContratChoisis;
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

    public void setDatCreMand(String datCreMand) {
        this.datCreMand = datCreMand;
    }

    public String getDatCreMand() {
        return datCreMand;
    }

    public void setCodTypMand(String codTypMand) {
        this.codTypMand = codTypMand;
    }

    public String getCodTypMand() {
        return codTypMand;
    }

    public void setListMandatPersonne(List listMandatPersonne) {
        this.listMandatPersonne = listMandatPersonne;
    }

    public List getListMandatPersonne() {
        return listMandatPersonne;
    }

    public void setListMandatOperation(List listMandatOperation) {
        this.listMandatOperation = listMandatOperation;
    }

    public List getListMandatOperation() {
        return listMandatOperation;
    }

    public void setListMandatPersonneGrid(Datagrid listMandatPersonneGrid) {
        this.listMandatPersonneGrid = listMandatPersonneGrid;
    }

    public Datagrid getListMandatPersonneGrid() {
        return listMandatPersonneGrid;
    }


    public void setListMandatOperationGrid(Datagrid listMandatOperationGrid) {
        this.listMandatOperationGrid = listMandatOperationGrid;
    }

    public Datagrid getListMandatOperationGrid() {
        return listMandatOperationGrid;
    }


    public void setPersonneexist(String personneexist) {
        this.personneexist = personneexist;
    }

    public String getPersonneexist() {
        return personneexist;
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

    public void setNumMandMand(Long numMandMand) {
        this.numMandMand = numMandMand;
    }

    public Long getNumMandMand() {
        return numMandMand;
    }

    public void setCodEtatMand(String codEtatMand) {
        this.codEtatMand = codEtatMand;
    }

    public String getCodEtatMand() {
        return codEtatMand;
    }

    public void setMandat(Mandat mandat) {
        this.mandat = mandat;
    }

    public Mandat getMandat() {
        return mandat;
    }


    public void setCodStrcMand(Long codStrcMand) {
        this.codStrcMand = codStrcMand;
    }

    public Long getCodStrcMand() {
        return codStrcMand;
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

   

    public void setDatJustMand(String datJustMand) {
        this.datJustMand = datJustMand;
    }

    public String getDatJustMand() {
        return datJustMand;
    }

    public void setNumDemMand(Long numDemMand) {
        this.numDemMand = numDemMand;
    }

    public Long getNumDemMand() {
        return numDemMand;
    }

    public void setTypeValidation(String typeValidation) {
        this.typeValidation = typeValidation;
    }

    public String getTypeValidation() {
        return typeValidation;
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

    public void setDatEnvcMand(String datEnvcMand) {
        this.datEnvcMand = datEnvcMand;
    }

    public String getDatEnvcMand() {
        return datEnvcMand;
    }

    public void setDatValcMand(String datValcMand) {
        this.datValcMand = datValcMand;
    }

    public String getDatValcMand() {
        return datValcMand;
    }

    public void setCasSouscriptionContratCompte(String casSouscriptionContratCompte) {
        this.casSouscriptionContratCompte = casSouscriptionContratCompte;
    }

    public String getCasSouscriptionContratCompte() {
        return casSouscriptionContratCompte;
    }

    public void setNumPereMand(Long numPereMand) {
        this.numPereMand = numPereMand;
    }

    public Long getNumPereMand() {
        return numPereMand;
    }

    public void setDatEnvmMand(String datEnvmMand) {
        this.datEnvmMand = datEnvmMand;
    }

    public String getDatEnvmMand() {
        return datEnvmMand;
    }

    public void setDatValmMand(String datValmMand) {
        this.datValmMand = datValmMand;
    }

    public String getDatValmMand() {
        return datValmMand;
    }

    public void setEtatFormCreationPersonne(String etatFormCreationPersonne) {
        this.etatFormCreationPersonne = etatFormCreationPersonne;
    }

    public String getEtatFormCreationPersonne() {
        return etatFormCreationPersonne;
    }

    public void setObservation(String observation) {
        this.observation = observation;
    }

    public String getObservation() {
        return observation;
    }


    public void setClientAgence(String clientAgence) {
        this.clientAgence = clientAgence;
    }

    public String getClientAgence() {
        return clientAgence;
    }

    public void setNumRdjMand(Long numRdjMand) {
        this.numRdjMand = numRdjMand;
    }

    public Long getNumRdjMand() {
        return numRdjMand;
    }

    public void setAlertAssocié(String alertAssocié) {
        this.alertAssocié = alertAssocié;
    }

    public String getAlertAssocié() {
        return alertAssocié;
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
}
