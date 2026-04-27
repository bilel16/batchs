package com.bna.smile.model.domainecompensation.gestionrejet.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.bna.commun.model.BlocageEffet;
import com.bna.commun.model.BordereauEffetMan;
import com.bna.commun.model.ContratCpt;
import com.bna.commun.model.Cro;
import com.bna.commun.model.DetailEffet;
import com.bna.commun.model.EffetMan;
import com.bna.commun.model.EffetRecu;
import com.bna.commun.model.EffetRecuTmp;
import com.bna.commun.model.GlobalEffet;
import com.bna.commun.model.OperationMoyPay;
import com.bna.commun.model.OppositionMoyenPaiement;
import com.bna.commun.model.Personne;
import com.bna.commun.model.SuiviHnEff;
import com.bna.commun.model.TraceCompensation;
import com.bna.commun.model.TraceEffetRecu;
import com.bna.commun.model.TraceRejetFichierRemise;
import com.bna.smile.web.commun.model.ParamAgence;
import com.oxia.fwk.core.ValueObject;

/**
 * @author Ayari Haythem
 * 
 */

public class RemiseEffetVo extends ValueObject implements Serializable {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private EffetRecu effetRecu;
	private EffetRecuTmp effetRecuTmp;
	private TraceEffetRecu traceEffetRecu;

	private BlocageEffet blocageEffet;
	private GlobalEffet globalEffet;
	private DetailEffet detailEffet;
	private String numCaisse;
	private ContratCpt contratCpt;
	private Long codeOperation;
	private boolean flagVerifoppositionEffet = false;
	private boolean flagVerifoppositionEffetJour = false;
	private boolean flagVerifoppositionEffetPec = false;
	private boolean flagVerifEffRecu = false;
	private boolean flagVerifEffRecuMan = false;
	private boolean flagVerifEffMan = false;

	private boolean isFlagVerifEffRecuPaye=false;
	private boolean flagVerifEffRecuOpp = false;
	private boolean flagVerifEffLiquide = false;
	private boolean flagVerifEffDetailPec = false;
	private boolean flagVerifEffDetailPecFichier = false;
	private boolean flagVerifFichierRemise = false;
	private boolean isFlagVerifEffRecuMig=false;
	private boolean isFlagVerifEffRemisImpayeMig=false;

	private boolean flagGetInfoRib = false;

	private boolean flagGetEffet = false;
	private boolean flagEffetConteste = false;
	private boolean flagOppositionEncours = false;
	private OperationMoyPay operationMoyPay;
	private boolean listByCpt = false;
	private boolean listEffetReclRecu = false;
	private boolean remiseByNum = false;
	private boolean remiseValide = false;
	private boolean remiseGuichet = false;
	private boolean delivranceLcnReclames = false;
	private boolean delivranceLcnImpayes = false;
	private boolean positionLcnReclames = false;
	private boolean recpetionLcnReclames = false;
	private Personne demandeur;
	private String typeDemandeur;
	private SuiviHnEff hnEff;
	private String statut;
	private String numEffet="";
	private String motifOpp;
	private String nomTier;
	private String prnTier;
	private String numPceTier;
	private String typePceTier;
	private String typeDelivrance;
	private Date datEch;
	private Date dateStart;
	private Date dateEnd;
	private String ribBen;
	private String ribTir;

	private Long mntEff;
	private OppositionMoyenPaiement oppositionMoyenPaiement;
	private OppositionMoyenPaiement oppositionMoyenPaiementToUp;
	private Long mntCommission;
	private Long nbrTotal;
	private Long mntTotal;
	private Long mntTvaCommission;
	private Long mntARegler;
	private Long mntCptVert;
	private ContratCpt cptVert;
	private Long mntIntr;
	private boolean effAval;
	private boolean effRejeter;
	private boolean effetDevise = false;
	private boolean effNonRecu;
	private String numRefCB;
	private String numRemise;
	private List<DetailEffet> listeDetailEffet = new ArrayList<DetailEffet>();
	private List<BlocageEffet> listeBlocage = new ArrayList<BlocageEffet>();
	private List<GlobalEffet> listeRemise = new ArrayList<GlobalEffet>();
	private List<OppositionMoyenPaiement> listeOppEffet = new ArrayList<OppositionMoyenPaiement>();
	private List<EffetRecu> listeEffetRecu = new ArrayList<EffetRecu>();
	private List<TraceEffetRecu> listeEffetRecuTrace = new ArrayList<TraceEffetRecu>();
	private List<EffetRecuTmp> listeEffetRecuTemp = new ArrayList<EffetRecuTmp>();
	private List<ContratCpt> listeContratCpts = new ArrayList<ContratCpt>();
	private OppositionMoyenPaiement selectedOppEffet;
	private Date dateComptable;
	private ParamAgence paramAgence;
	private boolean updateMode = false;
	private boolean leveeOppostion = false;
	private boolean rejetLieProvision = false;
	private String typeListe = "";
	private String typeFiltreListe = "";
	private String critere_Recherche = "";
	private Long sommeBlocage = 0L;
	private Long codValeffet;
	private boolean effetForcer = false;
	private boolean loadRemiseFichier = false;
	private List<TraceRejetFichierRemise> listeRejetFichierRemise = new ArrayList<TraceRejetFichierRemise>();
	private List<Cro> listeCros = new ArrayList<Cro>();
	private boolean effetReclameTrezor = false;

	private String  stadeEffet ="";
	private String  codeRessource ="120";

	/****** Compensation manuelle *****/
	private boolean pecMode = false;
	private boolean pecRecuMode = false;
	private boolean effetBrulant = false;
	private boolean effetManMode = false;

	private boolean deleteMode = false;
	private boolean deleteBordereauMode=false;
	private boolean toilettageMode = false;
	private boolean effetTraiteMode = false;

	private boolean avisSortMode = false;
	private boolean avisSortEditeMode = false;
	private boolean recapEnvoisRemise = false;
	private boolean recapEnvoisRejet = false;

	private boolean recepSortMode = false;
	private String motifRejet1 = "";
	private String motifRejet2 = "";
	private String motifRejet3 = "";
	private String motifRejet4 = "";

	private String numSort = "";
	private Long mntFraisPDL;
	private String structureReceptrice;
	private String refInterSiege = "";
	private EffetMan effetMan;
	private EffetMan effetManToDelete;
	private Long validationStep;
	private List<EffetMan> listeEffetMan = new ArrayList<EffetMan>();
	private List<BordereauEffetMan> listeBordereau = new ArrayList<BordereauEffetMan>();
	private boolean annulationTardif = false;
	private boolean rejetAuto = false;

	private boolean effetArejeter = false;
	private BordereauEffetMan bordereauEffetMan;
	private Long mntGolbalRejet;//
	private Long nombreGlobalRejet;//
	private Long mntGlobalRejetBNABNA;//
	private Long mntGlobalRejetBNAAUTRE;//
	/*****Controle ****/
	private String  codeStructure ="";
	private List<TraceCompensation> listeTraceComp = new ArrayList<TraceCompensation>();

	public Long getMntGolbalRejet() {
		return mntGolbalRejet;
	}

	
	public void setMntGolbalRejet(Long mntGolbalRejet) {
		this.mntGolbalRejet = mntGolbalRejet;
	}

	
	public Long getNombreGlobalRejet() {
		return nombreGlobalRejet;
	}

	
	public void setNombreGlobalRejet(Long nombreGlobalRejet) {
		this.nombreGlobalRejet = nombreGlobalRejet;
	}

	
	public Long getMntGlobalRejetBNABNA() {
		return mntGlobalRejetBNABNA;
	}

	
	public void setMntGlobalRejetBNABNA(Long mntGlobalRejetBNABNA) {
		this.mntGlobalRejetBNABNA = mntGlobalRejetBNABNA;
	}

	
	public Long getMntGlobalRejetBNAAUTRE() {
		return mntGlobalRejetBNAAUTRE;
	}

	
	public void setMntGlobalRejetBNAAUTRE(Long mntGlobalRejetBNAAUTRE) {
		this.mntGlobalRejetBNAAUTRE = mntGlobalRejetBNAAUTRE;
	}

	public GlobalEffet getGlobalEffet() {
		return globalEffet;
	}

	public void setGlobalEffet(GlobalEffet globalEffet) {
		this.globalEffet = globalEffet;
	}

	public Date getDateComptable() {
		return dateComptable;
	}

	public void setDateComptable(Date dateComptable) {
		this.dateComptable = dateComptable;
	}

	public ParamAgence getParamAgence() {
		return paramAgence;
	}

	public void setParamAgence(ParamAgence paramAgence) {
		this.paramAgence = paramAgence;
	}

	public void setNumEffet(String numEffet) {
		this.numEffet = numEffet;
	}

	public String getNumEffet() {
		return numEffet;
	}

	public String getRibBen() {
		return ribBen;
	}

	public void setRibBen(String ribBen) {
		this.ribBen = ribBen;
	}

	public Long getMntEff() {
		return mntEff;
	}

	public void setMntEff(Long mntEff) {
		this.mntEff = mntEff;
	}

	public boolean isEffAval() {
		return effAval;
	}

	public void setEffAval(boolean effAval) {
		this.effAval = effAval;
	}

	public void setListeRemise(List<GlobalEffet> listeRemise) {
		this.listeRemise = listeRemise;
	}

	public List<GlobalEffet> getListeRemise() {
		return listeRemise;
	}

	public void setUpdateMode(boolean updateMode) {
		this.updateMode = updateMode;
	}

	public boolean isUpdateMode() {
		return updateMode;
	}

	public void setDetailEffet(DetailEffet detailEffet) {
		this.detailEffet = detailEffet;
	}

	public DetailEffet getDetailEffet() {
		return detailEffet;
	}

	public void setListByCpt(boolean listByCpt) {
		this.listByCpt = listByCpt;
	}

	public boolean isListByCpt() {
		return listByCpt;
	}

	public void setContratCpt(ContratCpt contratCpt) {
		this.contratCpt = contratCpt;
	}

	public ContratCpt getContratCpt() {
		return contratCpt;
	}

	public void setRemiseByNum(boolean remiseByNum) {
		this.remiseByNum = remiseByNum;
	}

	public boolean isRemiseByNum() {
		return remiseByNum;
	}

	public void setNumRemise(String numRemise) {
		this.numRemise = numRemise;
	}

	public String getNumRemise() {
		return numRemise;
	}

	public String getNomTier() {
		return nomTier;
	}

	public void setNomTier(String nomTier) {
		this.nomTier = nomTier;
	}

	public String getPrnTier() {
		return prnTier;
	}

	public void setPrnTier(String prnTier) {
		this.prnTier = prnTier;
	}

	public String getNumPceTier() {
		return numPceTier;
	}

	public void setNumPceTier(String numPceTier) {
		this.numPceTier = numPceTier;
	}

	public void setRemiseValide(boolean remiseValide) {
		this.remiseValide = remiseValide;
	}

	public boolean isRemiseValide() {
		return remiseValide;
	}

	public void setStatut(String statut) {
		this.statut = statut;
	}

	public String getStatut() {
		return statut;
	}

	public void setNumRefCB(String numRefCB) {
		this.numRefCB = numRefCB;
	}

	public String getNumRefCB() {
		return numRefCB;
	}

	public void setDatEch(Date datEch) {
		this.datEch = datEch;
	}

	public Date getDatEch() {
		return datEch;
	}

	public void setListeContratCpts(List<ContratCpt> listeContratCpts) {
		this.listeContratCpts = listeContratCpts;
	}

	public List<ContratCpt> getListeContratCpts() {
		return listeContratCpts;
	}

	public void setTypePceTier(String typePceTier) {
		this.typePceTier = typePceTier;
	}

	public String getTypePceTier() {
		return typePceTier;
	}

	public void setListeOppEffet(List<OppositionMoyenPaiement> listeOppEffet) {
		this.listeOppEffet = listeOppEffet;
	}

	public List<OppositionMoyenPaiement> getListeOppEffet() {
		return listeOppEffet;
	}

	public void setSelectedOppEffet(OppositionMoyenPaiement selectedOppEffet) {
		this.selectedOppEffet = selectedOppEffet;
	}

	public OppositionMoyenPaiement getSelectedOppEffet() {
		return selectedOppEffet;
	}

	public void setListeEffetRecu(List<EffetRecu> listeEffetRecu) {
		this.listeEffetRecu = listeEffetRecu;
	}

	public List<EffetRecu> getListeEffetRecu() {
		return listeEffetRecu;
	}

	public void setMntIntr(Long mntIntr) {
		this.mntIntr = mntIntr;
	}

	public Long getMntIntr() {
		return mntIntr;
	}

	public void setEffetRecu(EffetRecu effetRecu) {
		this.effetRecu = effetRecu;
	}

	public EffetRecu getEffetRecu() {
		return effetRecu;
	}

	public void setListEffetReclRecu(boolean listEffetReclRecu) {
		this.listEffetReclRecu = listEffetReclRecu;
	}

	public boolean isListEffetReclRecu() {
		return listEffetReclRecu;
	}

	public void setHnEff(SuiviHnEff hnEff) {
		this.hnEff = hnEff;
	}

	public SuiviHnEff getHnEff() {
		return hnEff;
	}

	public void setEffNonRecu(boolean effNonRecu) {
		this.effNonRecu = effNonRecu;
	}

	public boolean isEffNonRecu() {
		return effNonRecu;
	}

	public void setTypeListe(String typeListe) {
		this.typeListe = typeListe;
	}

	public String getTypeListe() {
		return typeListe;
	}

	public void setCritere_Recherche(String critere_Recherche) {
		this.critere_Recherche = critere_Recherche;
	}

	public String getCritere_Recherche() {
		return critere_Recherche;
	}

	public void setLeveeOppostion(boolean leveeOppostion) {
		this.leveeOppostion = leveeOppostion;
	}

	public boolean isLeveeOppostion() {
		return leveeOppostion;
	}

	public void setTypeDelivrance(String typeDelivrance) {
		this.typeDelivrance = typeDelivrance;
	}

	public String getTypeDelivrance() {
		return typeDelivrance;
	}

	public void setRemiseGuichet(boolean remiseGuichet) {
		this.remiseGuichet = remiseGuichet;
	}

	public boolean isRemiseGuichet() {
		return remiseGuichet;
	}

	public void setListeEffetRecuTemp(List<EffetRecuTmp> listeEffetRecuTemp) {
		this.listeEffetRecuTemp = listeEffetRecuTemp;
	}

	public List<EffetRecuTmp> getListeEffetRecuTemp() {
		return listeEffetRecuTemp;
	}

	public void setEffetRecuTmp(EffetRecuTmp effetRecuTmp) {
		this.effetRecuTmp = effetRecuTmp;
	}

	public EffetRecuTmp getEffetRecuTmp() {
		return effetRecuTmp;
	}

	public void setOperationMoyPay(OperationMoyPay operationMoyPay) {
		this.operationMoyPay = operationMoyPay;
	}

	public OperationMoyPay getOperationMoyPay() {
		return operationMoyPay;
	}

	public void setFlagVerifoppositionEffet(boolean flagVerifoppositionEffet) {
		this.flagVerifoppositionEffet = flagVerifoppositionEffet;
	}

	public boolean isFlagVerifoppositionEffet() {
		return flagVerifoppositionEffet;
	}

	public void setDemandeur(Personne demandeur) {
		this.demandeur = demandeur;
	}

	public Personne getDemandeur() {
		return demandeur;
	}

	public void setTypeDemandeur(String typeDemandeur) {
		this.typeDemandeur = typeDemandeur;
	}

	public String getTypeDemandeur() {
		return typeDemandeur;
	}

	public void setCptVert(ContratCpt cptVert) {
		this.cptVert = cptVert;
	}

	public ContratCpt getCptVert() {
		return cptVert;
	}

	public void setMntARegler(Long mntARegler) {
		this.mntARegler = mntARegler;
	}

	public Long getMntARegler() {
		return mntARegler;
	}

	public void setMntCptVert(Long mntCptVert) {
		this.mntCptVert = mntCptVert;
	}

	public Long getMntCptVert() {
		return mntCptVert;
	}

	public void setMntTvaCommission(Long mntTvaCommission) {
		this.mntTvaCommission = mntTvaCommission;
	}

	public Long getMntTvaCommission() {
		return mntTvaCommission;
	}

	public void setMntCommission(Long mntCommission) {
		this.mntCommission = mntCommission;
	}

	public Long getMntCommission() {
		return mntCommission;
	}

	public void setNbrTotal(Long nbrTotal) {
		this.nbrTotal = nbrTotal;
	}

	public Long getNbrTotal() {
		return nbrTotal;
	}

	public void setMntTotal(Long mntTotal) {
		this.mntTotal = mntTotal;
	}

	public Long getMntTotal() {
		return mntTotal;
	}

	public void setListeDetailEffet(List<DetailEffet> listeDetailEffet) {
		this.listeDetailEffet = listeDetailEffet;
	}

	public List<DetailEffet> getListeDetailEffet() {
		return listeDetailEffet;
	}

	public void setEffRejeter(boolean effRejeter) {
		this.effRejeter = effRejeter;
	}

	public boolean isEffRejeter() {
		return effRejeter;
	}

	public void setOppositionMoyenPaiement(OppositionMoyenPaiement oppositionMoyenPaiement) {
		this.oppositionMoyenPaiement = oppositionMoyenPaiement;
	}

	public OppositionMoyenPaiement getOppositionMoyenPaiement() {
		return oppositionMoyenPaiement;
	}

	

	public void setMotifOpp(String motifOpp) {
		this.motifOpp = motifOpp;
	}

	public String getMotifOpp() {
		return motifOpp;
	}

	public void setNumCaisse(String numCaisse) {
		this.numCaisse = numCaisse;
	}

	public String getNumCaisse() {
		return numCaisse;
	}

	public void setDelivranceLcnReclames(boolean delivranceLcnReclames) {
		this.delivranceLcnReclames = delivranceLcnReclames;
	}

	public boolean isDelivranceLcnReclames() {
		return delivranceLcnReclames;
	}

	public void setPositionLcnReclames(boolean positionLcnReclames) {
		this.positionLcnReclames = positionLcnReclames;
	}

	public boolean isPositionLcnReclames() {
		return positionLcnReclames;
	}

	public void setRecpetionLcnReclames(boolean recpetionLcnReclames) {
		this.recpetionLcnReclames = recpetionLcnReclames;
	}

	public boolean isRecpetionLcnReclames() {
		return recpetionLcnReclames;
	}

	public void setDelivranceLcnImpayes(boolean delivranceLcnImpayes) {
		this.delivranceLcnImpayes = delivranceLcnImpayes;
	}

	public boolean isDelivranceLcnImpayes() {
		return delivranceLcnImpayes;
	}

	public void setFlagVerifEffRecu(boolean flagVerifEffRecu) {
		this.flagVerifEffRecu = flagVerifEffRecu;
	}

	public boolean isFlagVerifEffRecu() {
		return flagVerifEffRecu;
	}

	public void setRejetLieProvision(boolean rejetLieProvision) {
		this.rejetLieProvision = rejetLieProvision;
	}

	public boolean isRejetLieProvision() {
		return rejetLieProvision;
	}

	public void setCodeOperation(Long codeOperation) {
		this.codeOperation = codeOperation;
	}

	public Long getCodeOperation() {
		return codeOperation;
	}

	public void setFlagGetEffet(boolean flagGetEffet) {
		this.flagGetEffet = flagGetEffet;
	}

	public boolean isFlagGetEffet() {
		return flagGetEffet;
	}

	public void setDateStart(Date dateStart) {
		this.dateStart = dateStart;
	}

	public Date getDateStart() {
		return dateStart;
	}

	public void setDateEnd(Date dateEnd) {
		this.dateEnd = dateEnd;
	}

	public Date getDateEnd() {
		return dateEnd;
	}

	public void setSommeBlocage(Long sommeBlocage) {
		this.sommeBlocage = sommeBlocage;
	}

	public Long getSommeBlocage() {
		return sommeBlocage;
	}

	public void setListeEffetRecuTrace(List<TraceEffetRecu> listeEffetRecuTrace) {
		this.listeEffetRecuTrace = listeEffetRecuTrace;
	}

	public List<TraceEffetRecu> getListeEffetRecuTrace() {
		return listeEffetRecuTrace;
	}

	public void setFlagVerifEffRecuOpp(boolean flagVerifEffRecuOpp) {
		this.flagVerifEffRecuOpp = flagVerifEffRecuOpp;
	}

	public boolean isFlagVerifEffRecuOpp() {
		return flagVerifEffRecuOpp;
	}

	public void setFlagOppositionEncours(boolean flagOppositionEncours) {
		this.flagOppositionEncours = flagOppositionEncours;
	}

	public boolean isFlagOppositionEncours() {
		return flagOppositionEncours;
	}

	public void setCodValeffet(Long codValeffet) {
		this.codValeffet = codValeffet;
	}

	public Long getCodValeffet() {
		return codValeffet;
	}

	public void setEffetDevise(boolean effetDevise) {
		this.effetDevise = effetDevise;
	}

	public boolean isEffetDevise() {
		return effetDevise;
	}

	public void setFlagVerifoppositionEffetJour(boolean flagVerifoppositionEffetJour) {
		this.flagVerifoppositionEffetJour = flagVerifoppositionEffetJour;
	}

	public boolean isFlagVerifoppositionEffetJour() {
		return flagVerifoppositionEffetJour;
	}

	

	public void setFlagVerifoppositionEffetPec(boolean flagVerifoppositionEffetPec) {
		this.flagVerifoppositionEffetPec = flagVerifoppositionEffetPec;
	}

	public boolean isFlagVerifoppositionEffetPec() {
		return flagVerifoppositionEffetPec;
	}

	public void setOppositionMoyenPaiementToUp(OppositionMoyenPaiement oppositionMoyenPaiementToUp) {
		this.oppositionMoyenPaiementToUp = oppositionMoyenPaiementToUp;
	}

	public OppositionMoyenPaiement getOppositionMoyenPaiementToUp() {
		return oppositionMoyenPaiementToUp;
	}

	public void setListeBlocage(List<BlocageEffet> listeBlocage) {
		this.listeBlocage = listeBlocage;
	}

	public List<BlocageEffet> getListeBlocage() {
		return listeBlocage;
	}

	public void setBlocageEffet(BlocageEffet blocageEffet) {
		this.blocageEffet = blocageEffet;
	}

	public BlocageEffet getBlocageEffet() {
		return blocageEffet;
	}

	public void setFlagEffetConteste(boolean flagEffetConteste) {
		this.flagEffetConteste = flagEffetConteste;
	}

	public boolean isFlagEffetConteste() {
		return flagEffetConteste;
	}

	public void setFlagVerifEffLiquide(boolean flagVerifEffLiquide) {
		this.flagVerifEffLiquide = flagVerifEffLiquide;
	}

	public boolean isFlagVerifEffLiquide() {
		return flagVerifEffLiquide;
	}

	public void setFlagVerifEffDetailPec(boolean flagVerifEffDetailPec) {
		this.flagVerifEffDetailPec = flagVerifEffDetailPec;
	}

	public boolean isFlagVerifEffDetailPec() {
		return flagVerifEffDetailPec;
	}

	public void setFlagGetInfoRib(boolean flagGetInfoRib) {
		this.flagGetInfoRib = flagGetInfoRib;
	}

	public boolean isFlagGetInfoRib() {
		return flagGetInfoRib;
	}

	public void setEffetForcer(boolean effetForcer) {
		this.effetForcer = effetForcer;
	}

	public boolean isEffetForcer() {
		return effetForcer;
	}

	public void setPecMode(boolean pecMode) {
		this.pecMode = pecMode;
	}

	public boolean isPecMode() {
		return pecMode;
	}

	public void setDeleteMode(boolean deleteMode) {
		this.deleteMode = deleteMode;
	}

	public boolean isDeleteMode() {
		return deleteMode;
	}

	public void setEffetMan(EffetMan effetMan) {
		this.effetMan = effetMan;
	}

	public EffetMan getEffetMan() {
		return effetMan;
	}

	public void setValidationStep(Long validationStep) {
		this.validationStep = validationStep;
	}

	public Long getValidationStep() {
		return validationStep;
	}

	public void setListeEffetMan(List<EffetMan> listeEffetMan) {
		this.listeEffetMan = listeEffetMan;
	}

	public List<EffetMan> getListeEffetMan() {
		return listeEffetMan;
	}

	public void setEffetManToDelete(EffetMan effetManToDelete) {
		this.effetManToDelete = effetManToDelete;
	}

	public EffetMan getEffetManToDelete() {
		return effetManToDelete;
	}

	

	public void setNumSort(String numSort) {
		this.numSort = numSort;
	}

	public String getNumSort() {
		return numSort;
	}

	public void setMntFraisPDL(Long mntFraisPDL) {
		this.mntFraisPDL = mntFraisPDL;
	}

	public Long getMntFraisPDL() {
		return mntFraisPDL;
	}

	public void setPecRecuMode(boolean pecRecuMode) {
		this.pecRecuMode = pecRecuMode;
	}

	public boolean isPecRecuMode() {
		return pecRecuMode;
	}

	public void setToilettageMode(boolean toilettageMode) {
		this.toilettageMode = toilettageMode;
	}

	public boolean isToilettageMode() {
		return toilettageMode;
	}

	public void setEffetTraiteMode(boolean effetTraiteMode) {
		this.effetTraiteMode = effetTraiteMode;
	}

	public boolean isEffetTraiteMode() {
		return effetTraiteMode;
	}

	public void setEffetArejeter(boolean effetArejeter) {
		this.effetArejeter = effetArejeter;
	}

	public boolean isEffetArejeter() {
		return effetArejeter;
	}

	public void setRefInterSiege(String refInterSiege) {
		this.refInterSiege = refInterSiege;
	}

	public String getRefInterSiege() {
		return refInterSiege;
	}

	public void setStructureReceptrice(String structureReceptrice) {
		this.structureReceptrice = structureReceptrice;
	}

	public String getStructureReceptrice() {
		return structureReceptrice;
	}

	public void setLoadRemiseFichier(boolean loadRemiseFichier) {
		this.loadRemiseFichier = loadRemiseFichier;
	}

	public boolean isLoadRemiseFichier() {
		return loadRemiseFichier;
	}

	public void setFlagVerifEffDetailPecFichier(boolean flagVerifEffDetailPecFichier) {
		this.flagVerifEffDetailPecFichier = flagVerifEffDetailPecFichier;
	}

	public boolean isFlagVerifEffDetailPecFichier() {
		return flagVerifEffDetailPecFichier;
	}

	public void setListeRejetFichierRemise(List<TraceRejetFichierRemise> listeRejetFichierRemise) {
		this.listeRejetFichierRemise = listeRejetFichierRemise;
	}

	public List<TraceRejetFichierRemise> getListeRejetFichierRemise() {
		return listeRejetFichierRemise;
	}

	

	public void setFlagVerifFichierRemise(boolean flagVerifFichierRemise) {
		this.flagVerifFichierRemise = flagVerifFichierRemise;
	}

	public boolean isFlagVerifFichierRemise() {
		return flagVerifFichierRemise;
	}

	public void setBordereauEffetMan(BordereauEffetMan bordereauEffetMan) {
		this.bordereauEffetMan = bordereauEffetMan;
	}

	public BordereauEffetMan getBordereauEffetMan() {
		return bordereauEffetMan;
	}

	public void setFlagVerifEffRecuPaye(boolean isFlagVerifEffRecuPaye) {
		this.isFlagVerifEffRecuPaye = isFlagVerifEffRecuPaye;
	}

	public boolean isFlagVerifEffRecuPaye() {
		return isFlagVerifEffRecuPaye;
	}

	public void setFlagVerifEffRecuMan(boolean flagVerifEffRecuMan) {
		this.flagVerifEffRecuMan = flagVerifEffRecuMan;
	}

	public boolean isFlagVerifEffRecuMan() {
		return flagVerifEffRecuMan;
	}

	public void setDeleteBordereauMode(boolean deleteBordereauMode) {
		this.deleteBordereauMode = deleteBordereauMode;
	}

	public boolean isDeleteBordereauMode() {
		return deleteBordereauMode;
	}

	public void setListeBordereau(List<BordereauEffetMan> listeBordereau) {
		this.listeBordereau = listeBordereau;
	}

	public List<BordereauEffetMan> getListeBordereau() {
		return listeBordereau;
	}

	public void setStadeEffet(String stadeEffet) {
		this.stadeEffet = stadeEffet;
	}

	public String getStadeEffet() {
		return stadeEffet;
	}

	public void setFlagVerifEffMan(boolean flagVerifEffMan) {
		this.flagVerifEffMan = flagVerifEffMan;
	}

	public boolean isFlagVerifEffMan() {
		return flagVerifEffMan;
	}

	public void setEffetBrulant(boolean effetBrulant) {
		this.effetBrulant = effetBrulant;
	}

	public boolean isEffetBrulant() {
		return effetBrulant;
	}

	public void setAvisSortMode(boolean avisSortMode) {
		this.avisSortMode = avisSortMode;
	}

	public boolean isAvisSortMode() {
		return avisSortMode;
	}

	public void setRecepSortMode(boolean recepSortMode) {
		this.recepSortMode = recepSortMode;
	}

	public boolean isRecepSortMode() {
		return recepSortMode;
	}

	
	public String getMotifRejet1() {
		return motifRejet1;
	}

	
	public void setMotifRejet1(String motifRejet1) {
		this.motifRejet1 = motifRejet1;
	}

	
	public String getMotifRejet2() {
		return motifRejet2;
	}

	
	public void setMotifRejet2(String motifRejet2) {
		this.motifRejet2 = motifRejet2;
	}

	
	public String getMotifRejet3() {
		return motifRejet3;
	}

	
	public void setMotifRejet3(String motifRejet3) {
		this.motifRejet3 = motifRejet3;
	}

	
	public String getMotifRejet4() {
		return motifRejet4;
	}

	
	public void setMotifRejet4(String motifRejet4) {
		this.motifRejet4 = motifRejet4;
	}

	public void setFlagVerifEffRecuMig(boolean isFlagVerifEffRecuMig) {
		this.isFlagVerifEffRecuMig = isFlagVerifEffRecuMig;
	}

	public boolean isFlagVerifEffRecuMig() {
		return isFlagVerifEffRecuMig;
	}

	public void setEffetManMode(boolean effetManMode) {
		this.effetManMode = effetManMode;
	}

	public boolean isEffetManMode() {
		return effetManMode;
	}


	public void setTypeFiltreListe(String typeFiltreListe) {
		this.typeFiltreListe = typeFiltreListe;
	}


	public String getTypeFiltreListe() {
		return typeFiltreListe;
	}


	public void setAvisSortEditeMode(boolean avisSortEditeMode) {
		this.avisSortEditeMode = avisSortEditeMode;
	}


	public boolean isAvisSortEditeMode() {
		return avisSortEditeMode;
	}


	public void setRecapEnvoisRemise(boolean recapEnvoisRemise) {
		this.recapEnvoisRemise = recapEnvoisRemise;
	}


	public boolean isRecapEnvoisRemise() {
		return recapEnvoisRemise;
	}


	public void setRecapEnvoisRejet(boolean recapEnvoisRejet) {
		this.recapEnvoisRejet = recapEnvoisRejet;
	}


	public boolean isRecapEnvoisRejet() {
		return recapEnvoisRejet;
	}


	public void setListeCros(List<Cro> listeCros) {
		this.listeCros = listeCros;
	}


	public List<Cro> getListeCros() {
		return listeCros;
	}


	public void setEffetReclameTrezor(boolean effetReclameTrezor) {
		this.effetReclameTrezor = effetReclameTrezor;
	}


	public boolean isEffetReclameTrezor() {
		return effetReclameTrezor;
	}


	


	public void setFlagVerifEffRemisImpayeMig(boolean isFlagVerifEffRemisImpayeMig) {
		this.isFlagVerifEffRemisImpayeMig = isFlagVerifEffRemisImpayeMig;
	}


	public boolean isFlagVerifEffRemisImpayeMig() {
		return isFlagVerifEffRemisImpayeMig;
	}


	public void setTraceEffetRecu(TraceEffetRecu traceEffetRecu) {
		this.traceEffetRecu = traceEffetRecu;
	}


	public TraceEffetRecu getTraceEffetRecu() {
		return traceEffetRecu;
	}


	public void setCodeStructure(String codeStructure) {
		this.codeStructure = codeStructure;
	}


	public String getCodeStructure() {
		return codeStructure;
	}


	public void setListeTraceComp(List<TraceCompensation> listeTraceComp) {
		this.listeTraceComp = listeTraceComp;
	}


	public List<TraceCompensation> getListeTraceComp() {
		return listeTraceComp;
	}


	public void setAnnulationTardif(boolean annulationTardif) {
		this.annulationTardif = annulationTardif;
	}


	public boolean isAnnulationTardif() {
		return annulationTardif;
	}


	public void setRejetAuto(boolean rejetAuto) {
		this.rejetAuto = rejetAuto;
	}


	public boolean isRejetAuto() {
		return rejetAuto;
	}


	public void setRibTir(String ribTir) {
		this.ribTir = ribTir;
	}


	public String getRibTir() {
		return ribTir;
	}


	public void setCodeRessource(String codeRessource) {
		this.codeRessource = codeRessource;
	}


	public String getCodeRessource() {
		return codeRessource;
	}

}
