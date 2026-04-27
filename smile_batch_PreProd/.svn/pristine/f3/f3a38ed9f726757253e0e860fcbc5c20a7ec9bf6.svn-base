package com.bna.smile.model.domainecompensation.gestionrejet.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.bna.commun.model.Cheque;
import com.bna.commun.model.ContratCpt;

import com.bna.commun.model.BlocageCheque;
import com.bna.commun.model.OperationMoyPay;
import com.bna.commun.model.Papillon;
import com.bna.commun.model.Personne;
import com.bna.commun.model.ReglementOperationCheque;
import com.bna.commun.model.Signataire;
import com.bna.smile.web.commun.model.ParamAgence;
import com.oxia.fwk.core.ValueObject;

public class ReglementChequeVo extends ValueObject implements Serializable {

	private static final long serialVersionUID = 1L;
	private ContratCpt contratCpt;
	private Cheque cheque;
	private BlocageCheque blocageCheque;
	private Long codValVal;
	private Long soldeClient;
	private List<Cheque> listeCheques = new ArrayList<Cheque>();
	private List<BlocageCheque> listeBlocages = new ArrayList<BlocageCheque>();
	private List<Signataire> listeSignataire = new ArrayList<Signataire>();
	private Papillon papillon;
	private Personne personne;
	private String libMotifRejet;
	private boolean flagInterChq = false;
	private String motifRejet;
	private int modeOperation;
	private boolean motifBlocagePresent = false;
	private String motifBlocage;
	private Date dateComptable;
	private ParamAgence paramAgence;
	private List<ReglementOperationChequeVo> listReglementOperationChequeVo;
	private Long montantCommission = 0L;
	private OperationMoyPay operationMoyPay;
	private List<OperationMoyPay> listeOmp = new ArrayList<OperationMoyPay>();
	private List<ReglementOperationCheque> listeOmpReglementCheque = new ArrayList<ReglementOperationCheque>();
	private boolean editionPapillon = false;
	private Long sommeBlocage = 0L;
	private Long sommeReserve = 0L;
	private Long codeOperation;
	private Long mntOperationOmp;
	private Long montantARegler;
	private DecompteVo decompteVo;
	private String typeReglement;
	private String numeroReglement;
	private Date dateReglement;
	private boolean provisionDisponible;
	private boolean chequeExiste;
	private Long montVertUtil = null;

	private Long provision;
	private String typeBlocage;
	private Long NbreChqDev;

	// ********************************************/
	public ContratCpt getContratCpt() {
		return contratCpt;
	}

	public void setContratCpt(ContratCpt contratCpt) {
		this.contratCpt = contratCpt;
	}

	public Cheque getCheque() {
		return cheque;
	}

	public void setCheque(Cheque cheque) {
		this.cheque = cheque;
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

	public OperationMoyPay getOperationMoyPay() {
		return operationMoyPay;
	}

	public void setOperationMoyPay(OperationMoyPay operationMoyPay) {
		this.operationMoyPay = operationMoyPay;
	}

	public Long getCodeOperation() {
		return codeOperation;
	}

	public void setCodeOperation(Long codeOperation) {
		this.codeOperation = codeOperation;
	}

	public void setSommeBlocage(Long sommeBlocage) {
		this.sommeBlocage = sommeBlocage;
	}

	public Long getSommeBlocage() {
		return sommeBlocage;
	}

	public void setEditionPapillon(boolean editionPapillon) {
		this.editionPapillon = editionPapillon;
	}

	public boolean isEditionPapillon() {
		return editionPapillon;
	}

	public void setPapillon(Papillon papillon) {
		this.papillon = papillon;
	}

	public Papillon getPapillon() {
		return papillon;
	}

	public void setListeCheques(List<Cheque> listeCheques) {
		this.listeCheques = listeCheques;
	}

	public List<Cheque> getListeCheques() {
		return listeCheques;
	}

	public void setLibMotifRejet(String libMotifRejet) {
		this.libMotifRejet = libMotifRejet;
	}

	public String getLibMotifRejet() {
		return libMotifRejet;
	}

	public void setMotifRejet(String motifRejet) {
		this.motifRejet = motifRejet;
	}

	public String getMotifRejet() {
		return motifRejet;
	}

	public void setPersonne(Personne personne) {
		this.personne = personne;
	}

	public Personne getPersonne() {
		return personne;
	}

	public void setListeSignataire(List<Signataire> listeSignataire) {
		this.listeSignataire = listeSignataire;
	}

	public List<Signataire> getListeSignataire() {
		return listeSignataire;
	}

	public void setCodValVal(Long codValVal) {
		this.codValVal = codValVal;
	}

	public Long getCodValVal() {
		return codValVal;
	}

	public void setListeOmp(List<OperationMoyPay> listeOmp) {
		this.listeOmp = listeOmp;
	}

	public List<OperationMoyPay> getListeOmp() {
		return listeOmp;
	}

	public void setMntOperationOmp(Long mntOperationOmp) {
		this.mntOperationOmp = mntOperationOmp;
	}

	public Long getMntOperationOmp() {
		return mntOperationOmp;
	}

	public void setModeOperation(int modeOperation) {
		this.modeOperation = modeOperation;
	}

	public int getModeOperation() {
		return modeOperation;
	}

	public Long getMontantARegler() {
		return montantARegler;
	}

	public void setMontantARegler(Long montantARegler) {
		this.montantARegler = montantARegler;
	}

	public DecompteVo getDecompteVo() {
		return decompteVo;
	}

	public void setDecompteVo(DecompteVo decompteVo) {
		this.decompteVo = decompteVo;
	}

	public String getTypeReglement() {
		return typeReglement;
	}

	public void setTypeReglement(String typeReglement) {
		this.typeReglement = typeReglement;
	}

	public boolean isProvisionDisponible() {
		return provisionDisponible;
	}

	public void setProvisionDisponible(boolean provisionDisponible) {
		this.provisionDisponible = provisionDisponible;
	}

	public boolean isChequeExiste() {
		return chequeExiste;
	}

	public void setChequeExiste(boolean chequeExiste) {
		this.chequeExiste = chequeExiste;
	}

	public void setListeOmpReglementCheque(List<ReglementOperationCheque> listeOmpReglementCheque) {
		this.listeOmpReglementCheque = listeOmpReglementCheque;
	}

	public List<ReglementOperationCheque> getListeOmpReglementCheque() {
		return listeOmpReglementCheque;
	}

	public void setSoldeClient(Long soldeClient) {
		this.soldeClient = soldeClient;
	}

	public Long getSoldeClient() {
		return soldeClient;
	}

	public List<ReglementOperationChequeVo> getListReglementOperationChequeVo() {
		return listReglementOperationChequeVo;
	}

	public void setListReglementOperationChequeVo(List<ReglementOperationChequeVo> listReglementOperationChequeVo) {
		this.listReglementOperationChequeVo = listReglementOperationChequeVo;
	}

	public Long getProvision() {
		return provision;
	}

	public void setProvision(Long provision) {
		this.provision = provision;
	}

	public void setMotifBlocagePresent(boolean motifBlocagePresent) {
		this.motifBlocagePresent = motifBlocagePresent;
	}

	public boolean isMotifBlocagePresent() {
		return motifBlocagePresent;
	}

	public void setMotifBlocage(String motifBlocage) {
		this.motifBlocage = motifBlocage;
	}

	public String getMotifBlocage() {
		return motifBlocage;
	}

	public void setMontantCommission(Long montantCommission) {
		this.montantCommission = montantCommission;
	}

	public Long getMontantCommission() {
		return montantCommission;
	}

	public void setFlagInterChq(boolean flagInterChq) {
		this.flagInterChq = flagInterChq;
	}

	public boolean isFlagInterChq() {
		return flagInterChq;
	}

	public void setListeBlocages(List<BlocageCheque> listeBlocages) {
		this.listeBlocages = listeBlocages;
	}

	public List<BlocageCheque> getListeBlocages() {
		return listeBlocages;
	}

	public void setBlocageCheque(BlocageCheque blocageCheque) {
		this.blocageCheque = blocageCheque;
	}

	public BlocageCheque getBlocageCheque() {
		return blocageCheque;
	}

	public String getNumeroReglement() {
		return numeroReglement;
	}

	public void setNumeroReglement(String numeroReglement) {
		this.numeroReglement = numeroReglement;
	}

	public Date getDateReglement() {
		return dateReglement;
	}

	public void setDateReglement(Date dateReglement) {
		this.dateReglement = dateReglement;
	}

	public String getTypeBlocage() {
		return typeBlocage;
	}

	public void setTypeBlocage(String typeBlocage) {
		this.typeBlocage = typeBlocage;
	}

	public void setMontVertUtil(Long montVertUtil) {
		this.montVertUtil = montVertUtil;
	}

	public Long getMontVertUtil() {
		return montVertUtil;
	}

	public Long getNbreChqDev() {
		return NbreChqDev;
	}

	public void setNbreChqDev(Long nbreChqDev) {
		NbreChqDev = nbreChqDev;
	}

	public Long getSommeReserve() {
		return sommeReserve;
	}

	public void setSommeReserve(Long sommeReserve) {
		this.sommeReserve = sommeReserve;
	}

}
