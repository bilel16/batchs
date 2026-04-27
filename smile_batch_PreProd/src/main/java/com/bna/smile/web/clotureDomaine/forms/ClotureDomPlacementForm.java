package com.bna.smile.web.clotureDomaine.forms;

import com.bna.commun.model.DemandeDecision;
import com.bna.smile.web.commun.view.InitialisationView;

import com.bna.smile.web.placement.view.DemandeDecisionView;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.struts.action.ActionForm;

public class ClotureDomPlacementForm extends ActionForm {
    public ClotureDomPlacementForm() {
    }
    private DemandeDecisionView demandeDecisionView = 
        new DemandeDecisionView();
    private InitialisationView initialisationView = new InitialisationView();
    // donn�es cocernant la forme Demande decision :
    private String libelleOperation;
    private String choixRecherche;
    // donn�es concernant la forme de recherche des demandes
    private String typePieceId;
    private String numPieceId;
    private String numDemdRech;
    private String dateDebut;
    private String dateFin;
    private String choix;
    private Long myTypeStructure;

    private Collection listeDemandesDecisionAttente;
    private Collection listeDemandesDecisionEncour;
    private Collection listeDemandesDecisionValide;
    private Collection listeDemandesDecisionRejete;
    private DemandeDecision demandeDecision;
    private String alertDemandeDecision = 
        ""; // alerte si la demande est valid�e ou trait�e ou inexistante (cette demande n'est pas en attente de r�ponse de la tr�sorerie

    private String structureRech = "";
    private String produitRech = "";
    private String dateDebRecherch = "";
    private String dateFinRecherch = "";
    private String etatDemande;
    private String codTypeStructure;
    private List listAgConcernees;
    private String typeForm;
    private Collection listeContratPlacement;
    private String numCplaRech;
    private Long numSeqPersRech;

    private String codeAgence;
    private String codeProduit;
    private String numeroCompte;

    private Collection listeARLPlacement;

    private Collection listeLiquidationAnticipe;
    private Collection listeAvance;
    private Collection listeDetailsAvance;
    private Collection listeInteretServi;
    private String numliquidationChoisi;
    private String numeroLiqChoisi;
    private String sommeInterets;

    private String datCreationRech = "";
    private String capitalRech = "";
    private String dureeRech = "";
    private String tauxRech = "";
    private String paiementRech;
    private Integer nbrAtt = 0;
    private Integer nbrEncour = 0;
    private Integer nbrVal = 0;
    private Integer nbrREj = 0;
    private String TotmontPlaDemdAtt;
    private String TotmontPlaDemdEncour;
    private String TotmontPlaDemdVal;
    private String TotmontPlaDemdRej;
    private Collection listeDemandes;
    private Collection listeSouscription;
    private Collection listeAvances;
    private Collection liquidPart = new ArrayList(0);
    private Collection liquidAvantEch = new ArrayList(0);
    private Collection LiquidArrivAEcheance = new ArrayList(0);
    private Collection liquidBtach = new ArrayList(0);
    private Collection listesInteretServi = new ArrayList(0);
    private Collection listesRenouvPlac = new ArrayList(0);
    private Collection listesLiquidation = new ArrayList(0);

    private String libDom;
    private Long nbrGlobSouscVal = new Long(0);
    private Double mntGlobSouscVal = new Double(0);
    private Long nbrGlobAvancVal = new Long(0);
    private Double mntGlobAvancVal = new Double(0);
    private Long nbrGlobRembVal = new Long(0);
    private Double mntGlobRembVal = new Double(0);
    private Long nbrGlobRenVal = new Long(0);
    private Double mntGlobRenVal = new Double(0);
    private Long nbrGlobInteretPre = new Long(0);
    private Double mntGlobInteretPre = new Double(0);
    private Long nbrGlobInteretPart = new Long(0);
    private Double mntGlobInteretPart = new Double(0);
    private Long nbrGlobInteretPost = new Long(0);
    private Double mntGlobInteretPost = new Double(0);
    private Long nbrLiqBatch = new Long(0);
    private Double mntGlobLiqBatch = new Double(0);
    private Long nbrGlobRenBatch = new Long(0);
    private Double mntGlobRenBatch = new Double(0);
    private Long nbrLiqAVEcheanceTOT = new Long(0);
    private Double mntGlobLiqAVEcheanceTOT = new Double(0);
    private Long nbrLiqPart = new Long(0);
    private Double mntGlobLiqPart = new Double(0);
    private Long nbrGlobResInt = new Long(0);
    private Double MntGlobalResInt = new Double(0);
    private Long nbrGlobrestit = new Long(0);
    private Double MntGlobalrestit = new Double(0);
    private Long nbrGloblRecBC = new Long(0);
    private Double MntGlobalRecBC = new Double(0);
    private Long nbrGlobVerIntLiq = new Long(0);
    private Double MntGlobalVerIntLiq = new Double(0);
    private Long nbrGlobPerIntAvanc = new Long(0);
    private Double MntGlobalPerIntAvanc = new Double(0);
    private Long nbrGlobRistIntAvanc = new Long(0);
    private Double MntGlobalRistIntAvanc = new Double(0);
    private Long nbrGlobPerIntCompRemb = new Long(0);
    private Double MntGlobalPerIntCompRemb = new Double(0);
    private Long nbrGlobAbonExtInt = new Long(0);
    private Double MntGlobalAbonExtInt = new Double(0);
    private Long nbrGlobAbonIntPlacPost;
    private Double mntGlobalAbonIntPlacPost;
    private Long nbrGlobAbonIntPlacPre;
    private Double mntGlobalAbonIntPlacPre;
    private Long nbrGlobResiliation;
    private Double mntGlobalResiliation;
    private Long nbrGlobRistIntResi;
    private Double mntGlobalRistIntResi;
    private Long nbrGlobVersIntResi;
    private Double mntGlobalVersIntResi;
    private String alertClotTreso="false";
    private Long nbrGlobAbonIntRembAv;
    private Double mntGlobalAbonIntRembAv;
    private Long nbrOper641;
    private Long nbrOper642;
    private Long nbrOper643;
    private Long nbrOper644;
    private Long nbrOper645;
    private Long nbrOper615;
    private Double mntOper641;
    private Double mntOper642;
    private Double mntOper643;
    private Double mntOper644;
    private Double mntOper645;
    private Double mntOper615;

    private Long produitChoisi;


    public void clearClotureDomPlacementForm() {
        listeDemandesDecisionAttente = null;
        listeContratPlacement = null;
        listeDetailsAvance = null;
        listeInteretServi = null;
        listesLiquidation = null;
    }

    public void setDemandeDecisionView(DemandeDecisionView demandeDecisionView) {
        this.demandeDecisionView = demandeDecisionView;
    }

    public DemandeDecisionView getDemandeDecisionView() {
        return demandeDecisionView;
    }

    public void setInitialisationView(InitialisationView initialisationView) {
        this.initialisationView = initialisationView;
    }

    public InitialisationView getInitialisationView() {
        return initialisationView;
    }

    public void setLibelleOperation(String libelleOperation) {
        this.libelleOperation = libelleOperation;
    }

    public String getLibelleOperation() {
        return libelleOperation;
    }

    public void setChoixRecherche(String choixRecherche) {
        this.choixRecherche = choixRecherche;
    }

    public String getChoixRecherche() {
        return choixRecherche;
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

    public void setNumDemdRech(String numDemdRech) {
        this.numDemdRech = numDemdRech;
    }

    public String getNumDemdRech() {
        return numDemdRech;
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

    public void setChoix(String choix) {
        this.choix = choix;
    }

    public String getChoix() {
        return choix;
    }


    public void setDemandeDecision(DemandeDecision demandeDecision) {
        this.demandeDecision = demandeDecision;
    }

    public DemandeDecision getDemandeDecision() {
        return demandeDecision;
    }

    public void setAlertDemandeDecision(String alertDemandeDecision) {
        this.alertDemandeDecision = alertDemandeDecision;
    }

    public String getAlertDemandeDecision() {
        return alertDemandeDecision;
    }

    public void setStructureRech(String structureRech) {
        this.structureRech = structureRech;
    }

    public String getStructureRech() {
        return structureRech;
    }

    public void setProduitRech(String produitRech) {
        this.produitRech = produitRech;
    }

    public String getProduitRech() {
        return produitRech;
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

    public void setEtatDemande(String etatDemande) {
        this.etatDemande = etatDemande;
    }

    public String getEtatDemande() {
        return etatDemande;
    }

    public void setCodTypeStructure(String codTypeStructure) {
        this.codTypeStructure = codTypeStructure;
    }

    public String getCodTypeStructure() {
        return codTypeStructure;
    }

    public void setListAgConcernees(List listAgConcernees) {
        this.listAgConcernees = listAgConcernees;
    }

    public List getListAgConcernees() {
        return listAgConcernees;
    }

    public void setTypeForm(String typeForm) {
        this.typeForm = typeForm;
    }

    public String getTypeForm() {
        return typeForm;
    }

    public void setListeContratPlacement(Collection listeContratPlacement) {
        this.listeContratPlacement = listeContratPlacement;
    }

    public Collection getListeContratPlacement() {
        return listeContratPlacement;
    }

    public void setNumCplaRech(String numCplaRech) {
        this.numCplaRech = numCplaRech;
    }

    public String getNumCplaRech() {
        return numCplaRech;
    }

    public void setNumSeqPersRech(Long numSeqPersRech) {
        this.numSeqPersRech = numSeqPersRech;
    }

    public Long getNumSeqPersRech() {
        return numSeqPersRech;
    }

    public void setCodeAgence(String codeAgence) {
        this.codeAgence = codeAgence;
    }

    public String getCodeAgence() {
        return codeAgence;
    }

    public void setCodeProduit(String codeProduit) {
        this.codeProduit = codeProduit;
    }

    public String getCodeProduit() {
        return codeProduit;
    }

    public void setNumeroCompte(String numeroCompte) {
        this.numeroCompte = numeroCompte;
    }

    public String getNumeroCompte() {
        return numeroCompte;
    }

    public void setListeARLPlacement(Collection listeARLPlacement) {
        this.listeARLPlacement = listeARLPlacement;
    }

    public Collection getListeARLPlacement() {
        return listeARLPlacement;
    }

    public void setListeLiquidationAnticipe(Collection listeLiquidationAnticipe) {
        this.listeLiquidationAnticipe = listeLiquidationAnticipe;
    }

    public Collection getListeLiquidationAnticipe() {
        return listeLiquidationAnticipe;
    }

    public void setListeAvance(Collection listeAvance) {
        this.listeAvance = listeAvance;
    }

    public Collection getListeAvance() {
        return listeAvance;
    }

    public void setListeInteretServi(Collection listeInteretServi) {
        this.listeInteretServi = listeInteretServi;
    }

    public Collection getListeInteretServi() {
        return listeInteretServi;
    }

    public void setNumliquidationChoisi(String numliquidationChoisi) {
        this.numliquidationChoisi = numliquidationChoisi;
    }

    public String getNumliquidationChoisi() {
        return numliquidationChoisi;
    }

    public void setNumeroLiqChoisi(String numeroLiqChoisi) {
        this.numeroLiqChoisi = numeroLiqChoisi;
    }

    public String getNumeroLiqChoisi() {
        return numeroLiqChoisi;
    }

    public void setSommeInterets(String sommeInterets) {
        this.sommeInterets = sommeInterets;
    }

    public String getSommeInterets() {
        return sommeInterets;
    }

    public void setDatCreationRech(String datCreationRech) {
        this.datCreationRech = datCreationRech;
    }

    public String getDatCreationRech() {
        return datCreationRech;
    }

    public void setCapitalRech(String capitalRech) {
        this.capitalRech = capitalRech;
    }

    public String getCapitalRech() {
        return capitalRech;
    }

    public void setDureeRech(String dureeRech) {
        this.dureeRech = dureeRech;
    }

    public String getDureeRech() {
        return dureeRech;
    }

    public void setTauxRech(String tauxRech) {
        this.tauxRech = tauxRech;
    }

    public String getTauxRech() {
        return tauxRech;
    }

    public void setPaiementRech(String paiementRech) {
        this.paiementRech = paiementRech;
    }

    public String getPaiementRech() {
        return paiementRech;
    }

    public void setListeDemandesDecisionAttente(Collection listeDemandesDecisionAttente) {
        this.listeDemandesDecisionAttente = listeDemandesDecisionAttente;
    }

    public Collection getListeDemandesDecisionAttente() {
        return listeDemandesDecisionAttente;
    }

    public void setListeDemandesDecisionEncour(Collection listeDemandesDecisionEncour) {
        this.listeDemandesDecisionEncour = listeDemandesDecisionEncour;
    }

    public Collection getListeDemandesDecisionEncour() {
        return listeDemandesDecisionEncour;
    }

    public void setListeDemandesDecisionValide(Collection listeDemandesDecisionValide) {
        this.listeDemandesDecisionValide = listeDemandesDecisionValide;
    }

    public Collection getListeDemandesDecisionValide() {
        return listeDemandesDecisionValide;
    }

    public void setListeDemandesDecisionRejete(Collection listeDemandesDecisionRejete) {
        this.listeDemandesDecisionRejete = listeDemandesDecisionRejete;
    }

    public Collection getListeDemandesDecisionRejete() {
        return listeDemandesDecisionRejete;
    }

    public void setNbrAtt(Integer nbrAtt) {
        this.nbrAtt = nbrAtt;
    }

    public Integer getNbrAtt() {
        return nbrAtt;
    }

    public void setNbrEncour(Integer nbrEncour) {
        this.nbrEncour = nbrEncour;
    }

    public Integer getNbrEncour() {
        return nbrEncour;
    }

    public void setNbrVal(Integer nbrVal) {
        this.nbrVal = nbrVal;
    }

    public Integer getNbrVal() {
        return nbrVal;
    }

    public void setNbrREj(Integer nbrREj) {
        this.nbrREj = nbrREj;
    }

    public Integer getNbrREj() {
        return nbrREj;
    }

    public void setTotmontPlaDemdAtt(String totmontPlaDemdAtt) {
        this.TotmontPlaDemdAtt = totmontPlaDemdAtt;
    }

    public String getTotmontPlaDemdAtt() {
        return TotmontPlaDemdAtt;
    }

    public void setTotmontPlaDemdEncour(String totmontPlaDemdEncour) {
        this.TotmontPlaDemdEncour = totmontPlaDemdEncour;
    }

    public String getTotmontPlaDemdEncour() {
        return TotmontPlaDemdEncour;
    }

    public void setTotmontPlaDemdVal(String totmontPlaDemdVal) {
        this.TotmontPlaDemdVal = totmontPlaDemdVal;
    }

    public String getTotmontPlaDemdVal() {
        return TotmontPlaDemdVal;
    }

    public void setTotmontPlaDemdRej(String totmontPlaDemdRej) {
        this.TotmontPlaDemdRej = totmontPlaDemdRej;
    }

    public String getTotmontPlaDemdRej() {
        return TotmontPlaDemdRej;
    }

    public void setListeDemandes(Collection listeDemandes) {
        this.listeDemandes = listeDemandes;
    }

    public Collection getListeDemandes() {
        return listeDemandes;
    }

    public void setListeSouscription(Collection listeSouscription) {
        this.listeSouscription = listeSouscription;
    }

    public Collection getListeSouscription() {
        return listeSouscription;
    }

    public void setListeAvances(Collection listeAvances) {
        this.listeAvances = listeAvances;
    }

    public Collection getListeAvances() {
        return listeAvances;
    }

    public void setLibDom(String libDom) {
        this.libDom = libDom;
    }

    public String getLibDom() {
        return libDom;
    }

    public void setLiquidPart(Collection liquidPart) {
        this.liquidPart = liquidPart;
    }

    public Collection getLiquidPart() {
        return liquidPart;
    }

    public void setLiquidAvantEch(Collection liquidAvantEch) {
        this.liquidAvantEch = liquidAvantEch;
    }

    public Collection getLiquidAvantEch() {
        return liquidAvantEch;
    }


    public void setListesInteretServi(Collection listesInteretServi) {
        this.listesInteretServi = listesInteretServi;
    }

    public Collection getListesInteretServi() {
        return listesInteretServi;
    }

    public void setListesRenouvPlac(Collection listesRenouvPlac) {
        this.listesRenouvPlac = listesRenouvPlac;
    }

    public Collection getListesRenouvPlac() {
        return listesRenouvPlac;
    }


    public void setMntGlobSouscVal(Double mntGlobSouscVal) {
        this.mntGlobSouscVal = mntGlobSouscVal;
    }

    public Double getMntGlobSouscVal() {
        return mntGlobSouscVal;
    }


    public void setMntGlobAvancVal(Double mntGlobAvancVal) {
        this.mntGlobAvancVal = mntGlobAvancVal;
    }

    public Double getMntGlobAvancVal() {
        return mntGlobAvancVal;
    }


    public void setMntGlobRembVal(Double mntGlobRembVal) {
        this.mntGlobRembVal = mntGlobRembVal;
    }

    public Double getMntGlobRembVal() {
        return mntGlobRembVal;
    }


    public void setMntGlobRenVal(Double mntGlobRenVal) {
        this.mntGlobRenVal = mntGlobRenVal;
    }

    public Double getMntGlobRenVal() {
        return mntGlobRenVal;
    }


    public void setMntGlobInteretPre(Double mntGlobInteretPre) {
        this.mntGlobInteretPre = mntGlobInteretPre;
    }

    public Double getMntGlobInteretPre() {
        return mntGlobInteretPre;
    }

    public void setNbrGlobSouscVal(Long nbrGlobSouscVal) {
        this.nbrGlobSouscVal = nbrGlobSouscVal;
    }

    public Long getNbrGlobSouscVal() {
        return nbrGlobSouscVal;
    }

    public void setNbrGlobAvancVal(Long nbrGlobAvancVal) {
        this.nbrGlobAvancVal = nbrGlobAvancVal;
    }

    public Long getNbrGlobAvancVal() {
        return nbrGlobAvancVal;
    }

    public void setNbrGlobRembVal(Long nbrGlobRembVal) {
        this.nbrGlobRembVal = nbrGlobRembVal;
    }

    public Long getNbrGlobRembVal() {
        return nbrGlobRembVal;
    }

    public void setNbrGlobRenVal(Long nbrGlobRenVal) {
        this.nbrGlobRenVal = nbrGlobRenVal;
    }

    public Long getNbrGlobRenVal() {
        return nbrGlobRenVal;
    }

    public void setNbrGlobInteretPre(Long nbrGlobInteretPre) {
        this.nbrGlobInteretPre = nbrGlobInteretPre;
    }

    public Long getNbrGlobInteretPre() {
        return nbrGlobInteretPre;
    }

    public void setNbrGlobInteretPart(Long nbrGlobInteretPart) {
        this.nbrGlobInteretPart = nbrGlobInteretPart;
    }

    public Long getNbrGlobInteretPart() {
        return nbrGlobInteretPart;
    }

    public void setMntGlobInteretPart(Double mntGlobInteretPart) {
        this.mntGlobInteretPart = mntGlobInteretPart;
    }

    public Double getMntGlobInteretPart() {
        return mntGlobInteretPart;
    }

    public void setNbrGlobInteretPost(Long nbrGlobInteretPost) {
        this.nbrGlobInteretPost = nbrGlobInteretPost;
    }

    public Long getNbrGlobInteretPost() {
        return nbrGlobInteretPost;
    }

    public void setMntGlobInteretPost(Double mntGlobInteretPost) {
        this.mntGlobInteretPost = mntGlobInteretPost;
    }

    public Double getMntGlobInteretPost() {
        return mntGlobInteretPost;
    }

    public void setNbrLiqBatch(Long nbrLiqBatch) {
        this.nbrLiqBatch = nbrLiqBatch;
    }

    public Long getNbrLiqBatch() {
        return nbrLiqBatch;
    }

    public void setMntGlobLiqBatch(Double mntGlobLiqBatch) {
        this.mntGlobLiqBatch = mntGlobLiqBatch;
    }

    public Double getMntGlobLiqBatch() {
        return mntGlobLiqBatch;
    }

    public void setNbrGlobRenBatch(Long nbrGlobRenBatch) {
        this.nbrGlobRenBatch = nbrGlobRenBatch;
    }

    public Long getNbrGlobRenBatch() {
        return nbrGlobRenBatch;
    }

    public void setMntGlobRenBatch(Double mntGlobRenBatch) {
        this.mntGlobRenBatch = mntGlobRenBatch;
    }

    public Double getMntGlobRenBatch() {
        return mntGlobRenBatch;
    }

    public void setLiquidBtach(Collection liquidBtach) {
        this.liquidBtach = liquidBtach;
    }

    public Collection getLiquidBtach() {
        return liquidBtach;
    }


    public void setNbrLiqAVEcheanceTOT(Long nbrLiqAVEcheanceTOT) {
        this.nbrLiqAVEcheanceTOT = nbrLiqAVEcheanceTOT;
    }

    public Long getNbrLiqAVEcheanceTOT() {
        return nbrLiqAVEcheanceTOT;
    }

    public void setMntGlobLiqAVEcheanceTOT(Double mntGlobLiqAVEcheanceTOT) {
        this.mntGlobLiqAVEcheanceTOT = mntGlobLiqAVEcheanceTOT;
    }

    public Double getMntGlobLiqAVEcheanceTOT() {
        return mntGlobLiqAVEcheanceTOT;
    }

    public void setNbrLiqPart(Long nbrLiqPart) {
        this.nbrLiqPart = nbrLiqPart;
    }

    public Long getNbrLiqPart() {
        return nbrLiqPart;
    }

    public void setMntGlobLiqPart(Double mntGlobLiqPart) {
        this.mntGlobLiqPart = mntGlobLiqPart;
    }

    public Double getMntGlobLiqPart() {
        return mntGlobLiqPart;
    }

    public void setNbrGlobResInt(Long nbrGlobResInt) {
        this.nbrGlobResInt = nbrGlobResInt;
    }

    public Long getNbrGlobResInt() {
        return nbrGlobResInt;
    }

    public void setMntGlobalResInt(Double mntGlobalResInt) {
        this.MntGlobalResInt = mntGlobalResInt;
    }

    public Double getMntGlobalResInt() {
        return MntGlobalResInt;
    }

    public void setNbrGlobrestit(Long nbrGlobrestit) {
        this.nbrGlobrestit = nbrGlobrestit;
    }

    public Long getNbrGlobrestit() {
        return nbrGlobrestit;
    }

    public void setMntGlobalrestit(Double mntGlobalrestit) {
        this.MntGlobalrestit = mntGlobalrestit;
    }

    public Double getMntGlobalrestit() {
        return MntGlobalrestit;
    }

    public void setNbrGloblRecBC(Long nbrGloblRecBC) {
        this.nbrGloblRecBC = nbrGloblRecBC;
    }

    public Long getNbrGloblRecBC() {
        return nbrGloblRecBC;
    }

    public void setMntGlobalRecBC(Double mntGlobalRecBC) {
        this.MntGlobalRecBC = mntGlobalRecBC;
    }

    public Double getMntGlobalRecBC() {
        return MntGlobalRecBC;
    }

    public void setNbrGlobVerIntLiq(Long nbrGlobVerIntLiq) {
        this.nbrGlobVerIntLiq = nbrGlobVerIntLiq;
    }

    public Long getNbrGlobVerIntLiq() {
        return nbrGlobVerIntLiq;
    }

    public void setMntGlobalVerIntLiq(Double mntGlobalVerIntLiq) {
        this.MntGlobalVerIntLiq = mntGlobalVerIntLiq;
    }

    public Double getMntGlobalVerIntLiq() {
        return MntGlobalVerIntLiq;
    }


    public void setLiquidArrivAEcheance(Collection liquidArrivAEcheance) {
        this.LiquidArrivAEcheance = liquidArrivAEcheance;
    }

    public Collection getLiquidArrivAEcheance() {
        return LiquidArrivAEcheance;
    }

    public void setMyTypeStructure(Long myTypeStructure) {
        this.myTypeStructure = myTypeStructure;
    }

    public Long getMyTypeStructure() {
        return myTypeStructure;
    }


    public void setProduitChoisi(Long produitChoisi) {
        this.produitChoisi = produitChoisi;
    }

    public Long getProduitChoisi() {
        return produitChoisi;
    }

    public void setListesLiquidation(Collection listesLiquidation) {
        this.listesLiquidation = listesLiquidation;
    }

    public Collection getListesLiquidation() {
        return listesLiquidation;
    }

    public void setNbrGlobPerIntAvanc(Long nbrGlobPerIntAvanc) {
        this.nbrGlobPerIntAvanc = nbrGlobPerIntAvanc;
    }

    public Long getNbrGlobPerIntAvanc() {
        return nbrGlobPerIntAvanc;
    }

    public void setMntGlobalPerIntAvanc(Double mntGlobalPerIntAvanc) {
        this.MntGlobalPerIntAvanc = mntGlobalPerIntAvanc;
    }

    public Double getMntGlobalPerIntAvanc() {
        return MntGlobalPerIntAvanc;
    }

    public void setNbrGlobRistIntAvanc(Long nbrGlobRistIntAvanc) {
        this.nbrGlobRistIntAvanc = nbrGlobRistIntAvanc;
    }

    public Long getNbrGlobRistIntAvanc() {
        return nbrGlobRistIntAvanc;
    }

    public void setMntGlobalRistIntAvanc(Double mntGlobalRistIntAvanc) {
        this.MntGlobalRistIntAvanc = mntGlobalRistIntAvanc;
    }

    public Double getMntGlobalRistIntAvanc() {
        return MntGlobalRistIntAvanc;
    }

    public void setNbrGlobPerIntCompRemb(Long nbrGlobPerIntCompRemb) {
        this.nbrGlobPerIntCompRemb = nbrGlobPerIntCompRemb;
    }

    public Long getNbrGlobPerIntCompRemb() {
        return nbrGlobPerIntCompRemb;
    }

    public void setMntGlobalPerIntCompRemb(Double mntGlobalPerIntCompRemb) {
        this.MntGlobalPerIntCompRemb = mntGlobalPerIntCompRemb;
    }

    public Double getMntGlobalPerIntCompRemb() {
        return MntGlobalPerIntCompRemb;
    }

    public void setListeDetailsAvance(Collection listeDetailsAvance) {
        this.listeDetailsAvance = listeDetailsAvance;
    }

    public Collection getListeDetailsAvance() {
        return listeDetailsAvance;
    }

    public void setNbrGlobAbonExtInt(Long nbrGlobAbonExtInt) {
        this.nbrGlobAbonExtInt = nbrGlobAbonExtInt;
    }

    public Long getNbrGlobAbonExtInt() {
        return nbrGlobAbonExtInt;
    }

    public void setMntGlobalAbonExtInt(Double mntGlobalAbonExtInt) {
        this.MntGlobalAbonExtInt = mntGlobalAbonExtInt;
    }

    public Double getMntGlobalAbonExtInt() {
        return MntGlobalAbonExtInt;
    }

    public void setNbrGlobAbonIntPlacPost(Long nbrGlobAbonIntPlacPost) {
        this.nbrGlobAbonIntPlacPost = nbrGlobAbonIntPlacPost;
    }

    public Long getNbrGlobAbonIntPlacPost() {
        return nbrGlobAbonIntPlacPost;
    }

    public void setMntGlobalAbonIntPlacPost(Double mntGlobalAbonIntPlacPost) {
        this.mntGlobalAbonIntPlacPost = mntGlobalAbonIntPlacPost;
    }

    public Double getMntGlobalAbonIntPlacPost() {
        return mntGlobalAbonIntPlacPost;
    }

    public void setNbrGlobAbonIntPlacPre(Long nbrGlobAbonIntPlacPre) {
        this.nbrGlobAbonIntPlacPre = nbrGlobAbonIntPlacPre;
    }

    public Long getNbrGlobAbonIntPlacPre() {
        return nbrGlobAbonIntPlacPre;
    }

    public void setMntGlobalAbonIntPlacPre(Double mntGlobalAbonIntPlacPre) {
        this.mntGlobalAbonIntPlacPre = mntGlobalAbonIntPlacPre;
    }

    public Double getMntGlobalAbonIntPlacPre() {
        return mntGlobalAbonIntPlacPre;
    }

    public void setNbrGlobResiliation(Long nbrGlobResiliation) {
        this.nbrGlobResiliation = nbrGlobResiliation;
    }

    public Long getNbrGlobResiliation() {
        return nbrGlobResiliation;
    }

    public void setMntGlobalResiliation(Double mntGlobalResiliation) {
        this.mntGlobalResiliation = mntGlobalResiliation;
    }

    public Double getMntGlobalResiliation() {
        return mntGlobalResiliation;
    }

    public void setNbrGlobRistIntResi(Long nbrGlobRistIntResi) {
        this.nbrGlobRistIntResi = nbrGlobRistIntResi;
    }

    public Long getNbrGlobRistIntResi() {
        return nbrGlobRistIntResi;
    }

    public void setMntGlobalRistIntResi(Double mntGlobalRistIntResi) {
        this.mntGlobalRistIntResi = mntGlobalRistIntResi;
    }

    public Double getMntGlobalRistIntResi() {
        return mntGlobalRistIntResi;
    }

    public void setNbrGlobVersIntResi(Long nbrGlobVersIntResi) {
        this.nbrGlobVersIntResi = nbrGlobVersIntResi;
    }

    public Long getNbrGlobVersIntResi() {
        return nbrGlobVersIntResi;
    }

    public void setMntGlobalVersIntResi(Double mntGlobalVersIntResi) {
        this.mntGlobalVersIntResi = mntGlobalVersIntResi;
    }

    public Double getMntGlobalVersIntResi() {
        return mntGlobalVersIntResi;
    }

    public void setAlertClotTreso(String alertClotTreso) {
        this.alertClotTreso = alertClotTreso;
    }

    public String getAlertClotTreso() {
        return alertClotTreso;
    }

    public void setNbrGlobAbonIntRembAv(Long nbrGlobAbonIntRembAv) {
        this.nbrGlobAbonIntRembAv = nbrGlobAbonIntRembAv;
    }

    public Long getNbrGlobAbonIntRembAv() {
        return nbrGlobAbonIntRembAv;
    }

    public void setMntGlobalAbonIntRembAv(Double mntGlobalAbonIntRembAv) {
        this.mntGlobalAbonIntRembAv = mntGlobalAbonIntRembAv;
    }

    public Double getMntGlobalAbonIntRembAv() {
        return mntGlobalAbonIntRembAv;
    }

    public void setNbrOper641(Long nbrOper641) {
        this.nbrOper641 = nbrOper641;
    }

    public Long getNbrOper641() {
        return nbrOper641;
    }

    public void setNbrOper642(Long nbrOper642) {
        this.nbrOper642 = nbrOper642;
    }

    public Long getNbrOper642() {
        return nbrOper642;
    }

    public void setNbrOper643(Long nbrOper643) {
        this.nbrOper643 = nbrOper643;
    }

    public Long getNbrOper643() {
        return nbrOper643;
    }

    public void setNbrOper644(Long nbrOper644) {
        this.nbrOper644 = nbrOper644;
    }

    public Long getNbrOper644() {
        return nbrOper644;
    }

    public void setNbrOper645(Long nbrOper645) {
        this.nbrOper645 = nbrOper645;
    }

    public Long getNbrOper645() {
        return nbrOper645;
    }

    public void setMntOper641(Double mntOper641) {
        this.mntOper641 = mntOper641;
    }

    public Double getMntOper641() {
        return mntOper641;
    }

    public void setMntOper642(Double mntOper642) {
        this.mntOper642 = mntOper642;
    }

    public Double getMntOper642() {
        return mntOper642;
    }

    public void setMntOper643(Double mntOper643) {
        this.mntOper643 = mntOper643;
    }

    public Double getMntOper643() {
        return mntOper643;
    }

    public void setMntOper644(Double mntOper644) {
        this.mntOper644 = mntOper644;
    }

    public Double getMntOper644() {
        return mntOper644;
    }

    public void setMntOper645(Double mntOper645) {
        this.mntOper645 = mntOper645;
    }

    public Double getMntOper645() {
        return mntOper645;
    }

    public void setNbrOper615(Long nbrOper615) {
        this.nbrOper615 = nbrOper615;
    }

    public Long getNbrOper615() {
        return nbrOper615;
    }

    public void setMntOper615(Double mntOper615) {
        this.mntOper615 = mntOper615;
    }

    public Double getMntOper615() {
        return mntOper615;
    }
}
