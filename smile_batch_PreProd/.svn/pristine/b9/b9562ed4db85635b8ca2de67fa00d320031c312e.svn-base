package com.bna.smile.model.virement.model;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.bna.commun.model.BatchRejetVirNSI;
import com.bna.commun.model.CoTitulaire;
import com.bna.commun.model.ContratCpt;
import com.bna.commun.model.DetailVirement;
import com.bna.commun.model.FluxComptVirement;
import com.bna.commun.model.GlobalVirement;
import com.bna.commun.model.MandantPersVirement;
import com.bna.commun.model.Mandat;
import com.bna.commun.model.MandatPersonne;
import com.bna.commun.model.Operation;
import com.bna.commun.model.OperationMoyPay;
import com.bna.commun.model.PersCotitVirement;
import com.bna.commun.model.Personne;
import com.bna.commun.model.Produit;
import com.bna.commun.model.Structure;
import com.bna.commun.model.TraceFraisPack;
import com.bna.commun.model.TraceOperVirement;
import com.bna.commun.util.TraitementConditionBanque;
import com.bna.smile.web.commun.model.ParamAgence;
import com.bna.smile.web.commun.model.Pouvoir;
import com.oxia.fwk.core.ValueObject;

public class VirementVo extends ValueObject {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String messageValidation;
	private GlobalVirement globalVirement;
	private DetailVirement detailVirement;
	private List<DetailVirement> listeDetailsVirements = new ArrayList<DetailVirement>();
	private List<MandatPersonne> listMandatPersonneVirement = new ArrayList<MandatPersonne>();
	private List<CoTitulaire> listCotitulaireVirement = new ArrayList<CoTitulaire>();
	private List<GlobalVirement> listGlobalVirements = new ArrayList<GlobalVirement>();
	private List<TraceOperVirement> listTracesVirements = new ArrayList<TraceOperVirement>();
	private List<FluxComptVirement> listFluxComptVirement = new ArrayList<FluxComptVirement>();
	private Mandat mandat;
	private String strCodbanque;
	private String strCodAgenceBanque;
	private String strCompte;
	private String strCle;
	private boolean verifier;
	private String strPetitRib;
	private ParamAgence paramAgence = new ParamAgence();
	private List<String> listNumRemises = new ArrayList<String>();
	private String numRemise;
	private boolean stadeEnregistrement = false;
	private Pouvoir pouvoir = new Pouvoir();
	private String messageVerificationRib;
	private Long typeVirement;
	private Long etatVirement;
	private ContratCpt contratCpt;
	private Personne personneDemandeur;
	private Long nombresVirments;
	private Long montantGlobal;
	private String strEtatValidationTrt;
	private String strNumSeqGvir;
	private String strRib;
	private String strListVirementPermanent = "";
	private Date oldDateExecution = new Date();
	private Date newDateExecution = new Date();
	private Date dateFinExecution = new Date();
	private Date dateLendemainOuvrable;
	private long nbreEchEnAttente;
	private Produit produit;
	private Date dateComptableAgence;
	private Structure structure;
	private boolean finBatchStructure = false;
	private boolean boolValiderRibDoEtBenif = false;
	private boolean boolValiderContratCpt = false;
	private boolean boolValiderContratCptDO = false;
	private boolean boolValiderContratCptBENIF = false;
	private List<DetailVirement> listDetailVirementaExecuter = new ArrayList<DetailVirement>();
	private String strTestRib = "";
	private long MONT_TOTAL;
	private long MONT_VIR;
	private boolean boolProvisionCompteVert = false;
	private List<PersCotitVirement> listPersCotitVirements = new ArrayList<PersCotitVirement>();
	private List<MandantPersVirement> listMandantPersVirement = new ArrayList<MandantPersVirement>();
	private long monantSolde = 0;
	private Long codTachTach;
	private boolean etatEnvoiSGMT = false;
	private Long numRefCro;
	private Long codStrcStrc;
	private Date dateValeurCro;
	private Date dateValeurCom;
	private Date dateOperation;
	private String codRefcOmp;
	// / --- Condition de Banque ------ ///
	private String tvaRecue = "";
	private String tva;
	private String commissionRecue = "";
	private String valueDateRecue;
	private String valueDateComRecue;
	private long montant_commissionRecue = 0;
	private long montant_virement = 0;
	private TraitementConditionBanque traitementConditionBanque = new TraitementConditionBanque();
	private Operation operation = new Operation();
	private OperationMoyPay operationMoyPay = new OperationMoyPay();
	private List<String> listePalierCaractere = new ArrayList<String>();
	private String numPcePers;
	private Long typePiece;
	private Long numCondition;
	private String typeCondition;
	// ************* CRO *****************//

	private boolean etatInsertionCro = false;
	private boolean etatTaxableClient = false;
	private boolean etatBenifMemeAgence = false;
	private long montantGlobalByStructure = 0;
	private Long codStrcRecp;
	private String codRefInter = "";
	private long montantGlobalByCB = 0;
	private long nbreGlobalByCB = 0;
	private Long etatBenifVirementCB;
	private BatchRejetVirNSI batchRejetVirNSI;
	// *************Mise ajour Solde *****************//

	private String codeSens = "DB"; // ******** DB : en debite ==> CR : en credite
	private long montantMiseAjourSolde = 0;

	// ************** Fichier ********* //

	private File file;
	private Set<File> listeFile = new HashSet<File>();
	private Set<String> listeFilesNames = new HashSet<String>();
	private long numLot = 0;
	private String numeroLot;
	private long numVirSgmt = 0;
	private long mntVirementADT = 0;
	private long nbrVirementADT = 0;
	private long numeroLigneADT = 0;
	// ************** Alimentation ********* //

	private ContratCpt contratCptCompteVert = new ContratCpt();
	private ContratCpt contratCptCompteDepot = new ContratCpt();
	private long mntAlimentationCompteDepot = 0;

	private boolean etatEnregistrement = false;
	// ************** Client Contentieux ********* //

	private long numCtxClt = 0;
	private Structure structureCTX = new Structure();
	private String periodicite;
	private TraceFraisPack traceFraisPack = new TraceFraisPack();
	// ************ Getter and Setter ******************* //

	public GlobalVirement getGlobalVirement() {
		return globalVirement;
	}

	public void setGlobalVirement(GlobalVirement globalVirement) {
		this.globalVirement = globalVirement;
	}

	public DetailVirement getDetailVirement() {
		return detailVirement;
	}

	public void setDetailVirement(DetailVirement detailVirement) {
		this.detailVirement = detailVirement;
	}

	public List<DetailVirement> getListeDetailsVirements() {
		return listeDetailsVirements;
	}

	public void setListeDetailsVirements(List<DetailVirement> listeDetailsVirements) {
		this.listeDetailsVirements = listeDetailsVirements;
	}

	public String getMessageValidation() {
		return messageValidation;
	}

	public void setMessageValidation(String messageValidation) {
		this.messageValidation = messageValidation;
	}

	public String getStrCodbanque() {
		return strCodbanque;
	}

	public void setStrCodbanque(String strCodbanque) {
		this.strCodbanque = strCodbanque;
	}

	public String getStrCodAgenceBanque() {
		return strCodAgenceBanque;
	}

	public void setStrCodAgenceBanque(String strCodAgenceBnaque) {
		this.strCodAgenceBanque = strCodAgenceBnaque;
	}

	public String getStrCle() {
		return strCle;
	}

	public void setStrCle(String strCle) {
		this.strCle = strCle;
	}

	public String getStrCompte() {
		return strCompte;
	}

	public void setStrCompte(String strCompte) {
		this.strCompte = strCompte;
	}

	public String getStrPetitRib() {
		return strPetitRib;
	}

	public void setStrPetitRib(String strPetitRib) {
		this.strPetitRib = strPetitRib;
	}

	public boolean isVerifier() {
		return verifier;
	}

	public void setVerifier(boolean verifier) {
		this.verifier = verifier;
	}

	public List<MandatPersonne> getListMandatPersonneVirement() {
		return listMandatPersonneVirement;
	}

	public void setListMandatPersonneVirement(List<MandatPersonne> listMandatPersonneVirement) {
		this.listMandatPersonneVirement = listMandatPersonneVirement;
	}

	public Mandat getMandat() {
		return mandat;
	}

	public void setMandat(Mandat mandat) {
		this.mandat = mandat;
	}

	public void setListCotitulaireVirement(List<CoTitulaire> listCotitulaireVirement) {
		this.listCotitulaireVirement = listCotitulaireVirement;
	}

	public List<CoTitulaire> getListCotitulaireVirement() {
		return listCotitulaireVirement;
	}

	public ParamAgence getParamAgence() {
		return paramAgence;
	}

	public void setParamAgence(ParamAgence paramAgence) {
		this.paramAgence = paramAgence;
	}

	public List<String> getListNumRemises() {
		return listNumRemises;
	}

	public void setListNumRemises(List<String> listNumRemises) {
		this.listNumRemises = listNumRemises;
	}

	public String getNumRemise() {
		return numRemise;
	}

	public void setNumRemise(String numRemise) {
		this.numRemise = numRemise;
	}

	public boolean isStadeEnregistrement() {
		return stadeEnregistrement;
	}

	public void setStadeEnregistrement(boolean stadeEnregistrement) {
		this.stadeEnregistrement = stadeEnregistrement;
	}

	public Pouvoir getPouvoir() {
		return pouvoir;
	}

	public void setPouvoir(Pouvoir pouvoir) {
		this.pouvoir = pouvoir;
	}

	public String getMessageVerificationRib() {
		return messageVerificationRib;
	}

	public void setMessageVerificationRib(String messageVerificationRib) {
		this.messageVerificationRib = messageVerificationRib;
	}

	public Long getTypeVirement() {
		return typeVirement;
	}

	public void setTypeVirement(Long typeVirement) {
		this.typeVirement = typeVirement;
	}

	public Long getEtatVirement() {
		return etatVirement;
	}

	public void setEtatVirement(Long etatVirement) {
		this.etatVirement = etatVirement;
	}

	public void setContratCpt(ContratCpt contratCpt) {
		this.contratCpt = contratCpt;
	}

	public ContratCpt getContratCpt() {
		return contratCpt;
	}

	public void setListGlobalVirements(List<GlobalVirement> listGlobalVirements) {
		this.listGlobalVirements = listGlobalVirements;
	}

	public List<GlobalVirement> getListGlobalVirements() {
		return listGlobalVirements;
	}

	public void setNombresVirments(Long nombresVirments) {
		this.nombresVirments = nombresVirments;
	}

	public Long getNombresVirments() {
		return nombresVirments;
	}

	public void setMontantGlobal(Long montantGlobal) {
		this.montantGlobal = montantGlobal;
	}

	public Long getMontantGlobal() {
		return montantGlobal;
	}

	public String getStrEtatValidationTrt() {
		return strEtatValidationTrt;
	}

	public void setStrEtatValidationTrt(String strEtatValidationTrt) {
		this.strEtatValidationTrt = strEtatValidationTrt;
	}

	public String getStrNumSeqGvir() {
		return strNumSeqGvir;
	}

	public void setStrNumSeqGvir(String strNumSeqGvir) {
		this.strNumSeqGvir = strNumSeqGvir;
	}

	public String getStrRib() {
		return strRib;
	}

	public void setStrRib(String strRib) {
		this.strRib = strRib;
	}

	public String getStrListVirementPermanent() {
		return strListVirementPermanent;
	}

	public void setStrListVirementPermanent(String strListVirementPermanent) {
		this.strListVirementPermanent = strListVirementPermanent;
	}

	public Date getOldDateExecution() {
		return oldDateExecution;
	}

	public void setOldDateExecution(Date oldDateExecution) {
		this.oldDateExecution = oldDateExecution;
	}

	public Date getNewDateExecution() {
		return newDateExecution;
	}

	public void setNewDateExecution(Date newDateExecution) {
		this.newDateExecution = newDateExecution;
	}

	public long getNbreEchEnAttente() {
		return nbreEchEnAttente;
	}

	public void setNbreEchEnAttente(long nbreEchEnAttente) {
		this.nbreEchEnAttente = nbreEchEnAttente;
	}

	public void setProduit(Produit produit) {
		this.produit = produit;
	}

	public Produit getProduit() {
		return produit;
	}

	public Date getDateFinExecution() {
		return dateFinExecution;
	}

	public void setDateFinExecution(Date dateFinExecution) {
		this.dateFinExecution = dateFinExecution;
	}

	public void setDateLendemainOuvrable(Date dateLendemainOuvrable) {
		this.dateLendemainOuvrable = dateLendemainOuvrable;
	}

	public Date getDateLendemainOuvrable() {
		return dateLendemainOuvrable;
	}

	public void setListTracesVirements(List<TraceOperVirement> listTracesVirements) {
		this.listTracesVirements = listTracesVirements;
	}

	public List<TraceOperVirement> getListTracesVirements() {
		return listTracesVirements;
	}

	public Date getDateComptableAgence() {
		return dateComptableAgence;
	}

	public void setDateComptableAgence(Date dateComptableAgence) {
		this.dateComptableAgence = dateComptableAgence;
	}

	public Structure getStructure() {
		return structure;
	}

	public void setStructure(Structure structure) {
		this.structure = structure;
	}

	public boolean isFinBatchStructure() {
		return finBatchStructure;
	}

	public void setFinBatchStructure(boolean finBatchStructure) {
		this.finBatchStructure = finBatchStructure;
	}

	public boolean isBoolValiderRibDoEtBenif() {
		return boolValiderRibDoEtBenif;
	}

	public void setBoolValiderRibDoEtBenif(boolean boolValiderRibDoEtBenif) {
		this.boolValiderRibDoEtBenif = boolValiderRibDoEtBenif;
	}

	public boolean isBoolValiderContratCpt() {
		return boolValiderContratCpt;
	}

	public void setBoolValiderContratCpt(boolean boolValiderContratCpt) {
		this.boolValiderContratCpt = boolValiderContratCpt;
	}

	public List<DetailVirement> getListDetailVirementaExecuter() {
		return listDetailVirementaExecuter;
	}

	public void setListDetailVirementaExecuter(List<DetailVirement> listDetailVirementaExecuter) {
		this.listDetailVirementaExecuter = listDetailVirementaExecuter;
	}

	public boolean isBoolValiderContratCptDO() {
		return boolValiderContratCptDO;
	}

	public void setBoolValiderContratCptDO(boolean boolValiderContratCptDO) {
		this.boolValiderContratCptDO = boolValiderContratCptDO;
	}

	public boolean isBoolValiderContratCptBENIF() {
		return boolValiderContratCptBENIF;
	}

	public void setBoolValiderContratCptBENIF(boolean boolValiderContratCptBENIF) {
		this.boolValiderContratCptBENIF = boolValiderContratCptBENIF;
	}

	public String getStrTestRib() {
		return strTestRib;
	}

	public void setStrTestRib(String strTestRib) {
		this.strTestRib = strTestRib;
	}

	public long getMONT_TOTAL() {
		return MONT_TOTAL;
	}

	public void setMONT_TOTAL(long mONT_TOTAL) {
		MONT_TOTAL = mONT_TOTAL;
	}

	public long getMONT_VIR() {
		return MONT_VIR;
	}

	public void setMONT_VIR(long mONT_VIR) {
		MONT_VIR = mONT_VIR;
	}

	public boolean isBoolProvisionCompteVert() {
		return boolProvisionCompteVert;
	}

	public void setBoolProvisionCompteVert(boolean boolProvisionCompteVert) {
		this.boolProvisionCompteVert = boolProvisionCompteVert;
	}

	public TraitementConditionBanque getTraitementConditionBanque() {
		return traitementConditionBanque;
	}

	public void setTraitementConditionBanque(TraitementConditionBanque traitementConditionBanque) {
		this.traitementConditionBanque = traitementConditionBanque;
	}

	public String getTvaRecue() {
		return tvaRecue;
	}

	public void setTvaRecue(String tvaRecue) {
		this.tvaRecue = tvaRecue;
	}

	public String getTva() {
		return tva;
	}

	public void setTva(String tva) {
		this.tva = tva;
	}

	public String getCommissionRecue() {
		return commissionRecue;
	}

	public void setCommissionRecue(String commissionRecue) {
		this.commissionRecue = commissionRecue;
	}

	public String getValueDateRecue() {
		return valueDateRecue;
	}

	public void setValueDateRecue(String valueDateRecue) {
		this.valueDateRecue = valueDateRecue;
	}

	public Operation getOperation() {
		return operation;
	}

	public void setOperation(Operation operation) {
		this.operation = operation;
	}

	public List<PersCotitVirement> getListPersCotitVirements() {
		return listPersCotitVirements;
	}

	public void setListPersCotitVirements(List<PersCotitVirement> listPersCotitVirements) {
		this.listPersCotitVirements = listPersCotitVirements;
	}

	public List<MandantPersVirement> getListMandantPersVirement() {
		return listMandantPersVirement;
	}

	public void setListMandantPersVirement(List<MandantPersVirement> listMandantPersVirement) {
		this.listMandantPersVirement = listMandantPersVirement;
	}

	public long getMonantSolde() {
		return monantSolde;
	}

	public void setMonantSolde(long monantSolde) {
		this.monantSolde = monantSolde;
	}

	public long getMontant_commissionRecue() {
		return montant_commissionRecue;
	}

	public void setMontant_commissionRecue(long montant_commissionRecue) {
		this.montant_commissionRecue = montant_commissionRecue;
	}

	public long getMontant_virement() {
		return montant_virement;
	}

	public void setMontant_virement(long montant_virement) {
		this.montant_virement = montant_virement;
	}

	public OperationMoyPay getOperationMoyPay() {
		return operationMoyPay;
	}

	public void setOperationMoyPay(OperationMoyPay operationMoyPay) {
		this.operationMoyPay = operationMoyPay;
	}

	public void setCodTachTach(Long codTachTach) {
		this.codTachTach = codTachTach;
	}

	public Long getCodTachTach() {
		return codTachTach;
	}

	public void setEtatInsertionCro(boolean etatInsertionCro) {
		this.etatInsertionCro = etatInsertionCro;
	}

	public boolean isEtatInsertionCro() {
		return etatInsertionCro;
	}

	public String getCodeSens() {
		return codeSens;
	}

	public void setCodeSens(String codeSens) {
		this.codeSens = codeSens;
	}

	public long getMontantMiseAjourSolde() {
		return montantMiseAjourSolde;
	}

	public void setMontantMiseAjourSolde(long montantMiseAjourSolde) {
		this.montantMiseAjourSolde = montantMiseAjourSolde;
	}

	public void setFile(File file) {
		this.file = file;
	}

	public File getFile() {
		return file;
	}

	public void setListeFile(Set<File> listeFile) {
		this.listeFile = listeFile;
	}

	public Set<File> getListeFile() {
		return listeFile;
	}

	public void setNumLot(long numLot) {
		this.numLot = numLot;
	}

	public long getNumLot() {
		return numLot;
	}

	public void setEtatTaxableClient(boolean etatTaxableClient) {
		this.etatTaxableClient = etatTaxableClient;
	}

	public boolean isEtatTaxableClient() {
		return etatTaxableClient;
	}

	public void setEtatBenifMemeAgence(boolean etatBenifMemeAgence) {
		this.etatBenifMemeAgence = etatBenifMemeAgence;
	}

	public boolean isEtatBenifMemeAgence() {
		return etatBenifMemeAgence;
	}

	public void setContratCptCompteVert(ContratCpt contratCptCompteVert) {
		this.contratCptCompteVert = contratCptCompteVert;
	}

	public ContratCpt getContratCptCompteVert() {
		return contratCptCompteVert;
	}

	public void setContratCptCompteDepot(ContratCpt contratCptCompteDepot) {
		this.contratCptCompteDepot = contratCptCompteDepot;
	}

	public ContratCpt getContratCptCompteDepot() {
		return contratCptCompteDepot;
	}

	public void setMntAlimentationCompteDepot(long mntAlimentationCompteDepot) {
		this.mntAlimentationCompteDepot = mntAlimentationCompteDepot;
	}

	public long getMntAlimentationCompteDepot() {
		return mntAlimentationCompteDepot;
	}

	public void setListePalierCaractere(List<String> listePalierCaractere) {
		this.listePalierCaractere = listePalierCaractere;
	}

	public List<String> getListePalierCaractere() {
		return listePalierCaractere;
	}

	public void setValueDateComRecue(String valueDateComRecue) {
		this.valueDateComRecue = valueDateComRecue;
	}

	public String getValueDateComRecue() {
		return valueDateComRecue;
	}

	public void setMntVirementADT(long mntVirementADT) {
		this.mntVirementADT = mntVirementADT;
	}

	public long getMntVirementADT() {
		return mntVirementADT;
	}

	public void setNbrVirementADT(long nbrVirementADT) {
		this.nbrVirementADT = nbrVirementADT;
	}

	public long getNbrVirementADT() {
		return nbrVirementADT;
	}

	public void setNumeroLigneADT(long numeroLigneADT) {
		this.numeroLigneADT = numeroLigneADT;
	}

	public long getNumeroLigneADT() {
		return numeroLigneADT;
	}

	public Long getCodStrcRecp() {
		return codStrcRecp;
	}

	public void setCodStrcRecp(Long codStrcRecp) {
		this.codStrcRecp = codStrcRecp;
	}

	public String getCodRefInter() {
		return codRefInter;
	}

	public void setCodRefInter(String codRefInter) {
		this.codRefInter = codRefInter;
	}

	public long getMontantGlobalByCB() {
		return montantGlobalByCB;
	}

	public void setMontantGlobalByCB(long montantGlobalByCB) {
		this.montantGlobalByCB = montantGlobalByCB;
	}

	public void setListeFilesNames(Set<String> listeFilesNames) {
		this.listeFilesNames = listeFilesNames;
	}

	public Set<String> getListeFilesNames() {
		return listeFilesNames;
	}

	public void setListFluxComptVirement(List<FluxComptVirement> listFluxComptVirement) {
		this.listFluxComptVirement = listFluxComptVirement;
	}

	public List<FluxComptVirement> getListFluxComptVirement() {
		return listFluxComptVirement;
	}

	public void setMontantGlobalByStructure(long montantGlobalByStructure) {
		this.montantGlobalByStructure = montantGlobalByStructure;
	}

	public long getMontantGlobalByStructure() {
		return montantGlobalByStructure;
	}

	public void setNumCtxClt(long numCtxClt) {
		this.numCtxClt = numCtxClt;
	}

	public long getNumCtxClt() {
		return numCtxClt;
	}

	public void setStructureCTX(Structure structureCTX) {
		this.structureCTX = structureCTX;
	}

	public Structure getStructureCTX() {
		return structureCTX;
	}

	public void setNbreGlobalByCB(long nbreGlobalByCB) {
		this.nbreGlobalByCB = nbreGlobalByCB;
	}

	public long getNbreGlobalByCB() {
		return nbreGlobalByCB;
	}

	public void setNumeroLot(String numeroLot) {
		this.numeroLot = numeroLot;
	}

	public String getNumeroLot() {
		return numeroLot;
	}

	public Personne getPersonneDemandeur() {
		return personneDemandeur;
	}

	public void setPersonneDemandeur(Personne personneDemandeur) {
		this.personneDemandeur = personneDemandeur;
	}

	public void setEtatBenifVirementCB(Long etatBenifVirementCB) {
		this.etatBenifVirementCB = etatBenifVirementCB;
	}

	public Long getEtatBenifVirementCB() {
		return etatBenifVirementCB;
	}

	public void setEtatEnregistrement(boolean etatEnregistrement) {
		this.etatEnregistrement = etatEnregistrement;
	}

	public boolean isEtatEnregistrement() {
		return etatEnregistrement;
	}

	public void setEtatEnvoiSGMT(boolean etatEnvoiSGMT) {
		this.etatEnvoiSGMT = etatEnvoiSGMT;
	}

	public boolean isEtatEnvoiSGMT() {
		return etatEnvoiSGMT;
	}

	public void setNumPcePers(String numPcePers) {
		this.numPcePers = numPcePers;
	}

	public String getNumPcePers() {
		return numPcePers;
	}

	public void setTypePiece(Long typePiece) {
		this.typePiece = typePiece;
	}

	public Long getTypePiece() {
		return typePiece;
	}

	public void setNumVirSgmt(long numVirSgmt) {
		this.numVirSgmt = numVirSgmt;
	}

	public long getNumVirSgmt() {
		return numVirSgmt;
	}

	public void setNumRefCro(Long numRefCro) {
		this.numRefCro = numRefCro;
	}

	public Long getNumRefCro() {
		return numRefCro;
	}

	public Long getCodStrcStrc() {
		return codStrcStrc;
	}

	public void setCodStrcStrc(Long codStrcStrc) {
		this.codStrcStrc = codStrcStrc;
	}

	public Date getDateValeurCro() {
		return dateValeurCro;
	}

	public void setDateValeurCro(Date dateValeurCro) {
		this.dateValeurCro = dateValeurCro;
	}

	public Date getDateValeurCom() {
		return dateValeurCom;
	}

	public void setDateValeurCom(Date dateValeurCom) {
		this.dateValeurCom = dateValeurCom;
	}

	public Date getDateOperation() {
		return dateOperation;
	}

	public void setDateOperation(Date dateOperation) {
		this.dateOperation = dateOperation;
	}

	public String getCodRefcOmp() {
		return codRefcOmp;
	}

	public void setCodRefcOmp(String codRefcOmp) {
		this.codRefcOmp = codRefcOmp;
	}

	public BatchRejetVirNSI getBatchRejetVirNSI() {
		return batchRejetVirNSI;
	}

	public void setBatchRejetVirNSI(BatchRejetVirNSI batchRejetVirNSI) {
		this.batchRejetVirNSI = batchRejetVirNSI;
	}

	public String getPeriodicite() {
		return periodicite;
	}

	public void setPeriodicite(String periodicite) {
		this.periodicite = periodicite;
	}

	public TraceFraisPack getTraceFraisPack() {
		return traceFraisPack;
	}

	public void setTraceFraisPack(TraceFraisPack traceFraisPack) {
		this.traceFraisPack = traceFraisPack;
	}

	public Long getNumCondition() {
		return numCondition;
	}

	public void setNumCondition(Long numCondition) {
		this.numCondition = numCondition;
	}

	public String getTypeCondition() {
		return typeCondition;
	}

	public void setTypeCondition(String typeCondition) {
		this.typeCondition = typeCondition;
	}

}
