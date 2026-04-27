package com.bna.smile.model.prelevement.model;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.bna.commun.model.ContratCpt;
import com.bna.commun.model.ContratDomiciliations;
import com.bna.commun.model.DetailsPrelevements;
import com.bna.commun.model.MvtPrelevements;
import com.bna.commun.model.Operation;
import com.bna.commun.model.OperationMoyPay;
import com.bna.commun.model.Produit;
import com.bna.commun.model.Structure;
import com.bna.commun.model.TraceContDomiciliations;
import com.oxia.fwk.core.ValueObject;

public class PrelevementVo extends ValueObject {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Date dateComptable;
	private Long codeValeur;
	private Long codeDevise;
	private boolean etatEnregistrementPrelevement = false;
	private String erreur;
	private ContratCpt contratCpt = new ContratCpt();
	private Date dateValeur;
	private Date dateValeurCommission;
	private Long montantCommission;
	private Operation operation = new Operation();
	private Produit produit = new Produit();
	private Long CodeStructure;
	private Structure structure = new Structure();
	private ContratDomiciliations contratDomiciliations = new ContratDomiciliations();
	private OperationMoyPay operationMoyPay = new OperationMoyPay();
	private DetailsPrelevements detailsPrelevements = new DetailsPrelevements();
	private MvtPrelevements mvtPrelevements = new MvtPrelevements();
	private List<ContratDomiciliations> listeContratDomiciliations = new ArrayList<ContratDomiciliations>();
	private List<DetailsPrelevements> listeDetailsPrelevements = new ArrayList<DetailsPrelevements>();
	private List<TraceContDomiciliations> listeTracesContDomiciliations = new ArrayList<TraceContDomiciliations>();
	private List<MvtPrelevements> listeMvtsPrelevements = new ArrayList<MvtPrelevements>();
	private String referenceInterSiege;
	private Long codeStructureReceptrice;
	private Long codeStructureBNA;
	private Long codeStructureBCT;
	private boolean etatComptePrelevement = false;
	private String msgEtatComptePrelevement;
	private Long codeRejet;
	private String pathFichier;
	private String pathFichierTraite;
	private String msgEnregistrement;
	private String ribCalculer;
	// ************ CRO *********************//
	private Long MNT_GLB_RCP_PRE_AGE;
	private Long NBR_GLB_RCP_PRE_AGE;
	private Long numeroLot;
	private Long numSeqMVTPrel;
	// ************ Condition de Banque *********************//
	private String valueDateRecu;

	// ************** Fichier ********* //
	private File file;
	private long mntGlobalFichier = 0;
	private long nbrGlobalFichier = 0;
	private long numLigneFichier = 0;
	private long numLotReception = 0;

	// *********Setter and Getter***************//

	public void setDateComptable(Date dateComptable) {
		this.dateComptable = dateComptable;
	}

	public Date getDateComptable() {
		return dateComptable;
	}

	public void setCodeValeur(Long codeValeur) {
		this.codeValeur = codeValeur;
	}

	public Long getCodeValeur() {
		return codeValeur;
	}

	public void setCodeDevise(Long codeDevise) {
		this.codeDevise = codeDevise;
	}

	public Long getCodeDevise() {
		return codeDevise;
	}

	public void setEtatEnregistrementPrelevement(boolean etatEnregistrementPrelevement) {
		this.etatEnregistrementPrelevement = etatEnregistrementPrelevement;
	}

	public boolean isEtatEnregistrementPrelevement() {
		return etatEnregistrementPrelevement;
	}

	public void setErreur(String erreur) {
		this.erreur = erreur;
	}

	public String getErreur() {
		return erreur;
	}

	public void setContratCpt(ContratCpt contratCpt) {
		this.contratCpt = contratCpt;
	}

	public ContratCpt getContratCpt() {
		return contratCpt;
	}

	public void setDateValeur(Date dateValeur) {
		this.dateValeur = dateValeur;
	}

	public Date getDateValeur() {
		return dateValeur;
	}

	public void setDateValeurCommission(Date dateValeurCommission) {
		this.dateValeurCommission = dateValeurCommission;
	}

	public Date getDateValeurCommission() {
		return dateValeurCommission;
	}

	public void setMontantCommission(Long montantCommission) {
		this.montantCommission = montantCommission;
	}

	public Long getMontantCommission() {
		return montantCommission;
	}

	public void setOperation(Operation operation) {
		this.operation = operation;
	}

	public Operation getOperation() {
		return operation;
	}

	public void setCodeStructure(Long codeStructure) {
		CodeStructure = codeStructure;
	}

	public Long getCodeStructure() {
		return CodeStructure;
	}

	public void setContratDomiciliations(ContratDomiciliations contratDomiciliations) {
		this.contratDomiciliations = contratDomiciliations;
	}

	public ContratDomiciliations getContratDomiciliations() {
		return contratDomiciliations;
	}

	public void setProduit(Produit produit) {
		this.produit = produit;
	}

	public Produit getProduit() {
		return produit;
	}

	public void setOperationMoyPay(OperationMoyPay operationMoyPay) {
		this.operationMoyPay = operationMoyPay;
	}

	public OperationMoyPay getOperationMoyPay() {
		return operationMoyPay;
	}

	public void setReferenceInterSiege(String referenceInterSiege) {
		this.referenceInterSiege = referenceInterSiege;
	}

	public String getReferenceInterSiege() {
		return referenceInterSiege;
	}

	public void setCodeStructureReceptrice(Long codeStructureReceptrice) {
		this.codeStructureReceptrice = codeStructureReceptrice;
	}

	public Long getCodeStructureReceptrice() {
		return codeStructureReceptrice;
	}

	public void setCodeStructureBNA(Long codeStructureBNA) {
		this.codeStructureBNA = codeStructureBNA;
	}

	public Long getCodeStructureBNA() {
		return codeStructureBNA;
	}

	public void setEtatComptePrelevement(boolean etatComptePrelevement) {
		this.etatComptePrelevement = etatComptePrelevement;
	}

	public boolean isEtatComptePrelevement() {
		return etatComptePrelevement;
	}

	public void setMsgEtatComptePrelevement(String msgEtatComptePrelevement) {
		this.msgEtatComptePrelevement = msgEtatComptePrelevement;
	}

	public String getMsgEtatComptePrelevement() {
		return msgEtatComptePrelevement;
	}

	public void setCodeRejet(Long codeRejet) {
		this.codeRejet = codeRejet;
	}

	public Long getCodeRejet() {
		return codeRejet;
	}

	public void setDetailsPrelevements(DetailsPrelevements detailsPrelevements) {
		this.detailsPrelevements = detailsPrelevements;
	}

	public DetailsPrelevements getDetailsPrelevements() {
		return detailsPrelevements;
	}

	public void setListeContratDomiciliations(List<ContratDomiciliations> listeContratDomiciliations) {
		this.listeContratDomiciliations = listeContratDomiciliations;
	}

	public List<ContratDomiciliations> getListeContratDomiciliations() {
		return listeContratDomiciliations;
	}

	public void setListeDetailsPrelevements(List<DetailsPrelevements> listeDetailsPrelevements) {
		this.listeDetailsPrelevements = listeDetailsPrelevements;
	}

	public List<DetailsPrelevements> getListeDetailsPrelevements() {
		return listeDetailsPrelevements;
	}

	public void setListeTracesContDomiciliations(List<TraceContDomiciliations> listeTracesContDomiciliations) {
		this.listeTracesContDomiciliations = listeTracesContDomiciliations;
	}

	public List<TraceContDomiciliations> getListeTracesContDomiciliations() {
		return listeTracesContDomiciliations;
	}

	public void setListeMvtsPrelevements(List<MvtPrelevements> listeMvtsPrelevements) {
		this.listeMvtsPrelevements = listeMvtsPrelevements;
	}

	public List<MvtPrelevements> getListeMvtsPrelevements() {
		return listeMvtsPrelevements;
	}

	public void setMvtPrelevements(MvtPrelevements mvtPrelevements) {
		this.mvtPrelevements = mvtPrelevements;
	}

	public MvtPrelevements getMvtPrelevements() {
		return mvtPrelevements;
	}

	public void setMNT_GLB_RCP_PRE_AGE(Long mNT_GLB_RCP_PRE_AGE) {
		MNT_GLB_RCP_PRE_AGE = mNT_GLB_RCP_PRE_AGE;
	}

	public Long getMNT_GLB_RCP_PRE_AGE() {
		return MNT_GLB_RCP_PRE_AGE;
	}

	public void setNBR_GLB_RCP_PRE_AGE(Long nBR_GLB_RCP_PRE_AGE) {
		NBR_GLB_RCP_PRE_AGE = nBR_GLB_RCP_PRE_AGE;
	}

	public Long getNBR_GLB_RCP_PRE_AGE() {
		return NBR_GLB_RCP_PRE_AGE;
	}

	public void setValueDateRecu(String valueDateRecu) {
		this.valueDateRecu = valueDateRecu;
	}

	public String getValueDateRecu() {
		return valueDateRecu;
	}

	public void setNumeroLot(Long numeroLot) {
		this.numeroLot = numeroLot;
	}

	public Long getNumeroLot() {
		return numeroLot;
	}

	public void setNumSeqMVTPrel(Long numSeqMVTPrel) {
		this.numSeqMVTPrel = numSeqMVTPrel;
	}

	public Long getNumSeqMVTPrel() {
		return numSeqMVTPrel;
	}

	public void setCodeStructureBCT(Long codeStructureBCT) {
		this.codeStructureBCT = codeStructureBCT;
	}

	public Long getCodeStructureBCT() {
		return codeStructureBCT;
	}

	public void setPathFichier(String pathFichier) {
		this.pathFichier = pathFichier;
	}

	public String getPathFichier() {
		return pathFichier;
	}

	public void setPathFichierTraite(String pathFichierTraite) {
		this.pathFichierTraite = pathFichierTraite;
	}

	public String getPathFichierTraite() {
		return pathFichierTraite;
	}

	public void setMsgEnregistrement(String msgEnregistrement) {
		this.msgEnregistrement = msgEnregistrement;
	}

	public String getMsgEnregistrement() {
		return msgEnregistrement;
	}

	public void setStructure(Structure structure) {
		this.structure = structure;
	}

	public Structure getStructure() {
		return structure;
	}

	public void setRibCalculer(String ribCalculer) {
		this.ribCalculer = ribCalculer;
	}

	public String getRibCalculer() {
		return ribCalculer;
	}

	
	public File getFile() {
		return file;
	}

	
	public void setFile(File file) {
		this.file = file;
	}

	
	public long getMntGlobalFichier() {
		return mntGlobalFichier;
	}

	
	public void setMntGlobalFichier(long mntGlobalFichier) {
		this.mntGlobalFichier = mntGlobalFichier;
	}

	
	public long getNbrGlobalFichier() {
		return nbrGlobalFichier;
	}

	
	public void setNbrGlobalFichier(long nbrGlobalFichier) {
		this.nbrGlobalFichier = nbrGlobalFichier;
	}

	
	public long getNumLigneFichier() {
		return numLigneFichier;
	}

	
	public void setNumLigneFichier(long numLigneFichier) {
		this.numLigneFichier = numLigneFichier;
	}

	
	public long getNumLotReception() {
		return numLotReception;
	}

	
	public void setNumLotReception(long numLotReception) {
		this.numLotReception = numLotReception;
	}

}
