package com.bna.smile.model.domainecompensation.gestionrejet.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.bna.commun.model.Cheque;
import com.bna.commun.model.Cnp;
import com.bna.commun.model.ContratCpt;
import com.bna.commun.model.DetailCheque;
import com.bna.commun.model.MouvementCompensation;
import com.bna.commun.model.OperationMoyPay;
import com.bna.commun.model.Papillon;
import com.bna.commun.model.Preavis;
import com.bna.smile.model.traitementCompensationRecu.model.ChequeRecu;
import com.bna.smile.web.commun.model.ParamAgence;
import com.oxia.fwk.core.ValueObject;

/**
 * @author Ayari haythem
 * 
 */
public class EditionRejetVo extends ValueObject implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String edtionAFaire;
	private String libeleMotifProvision = "";
	private ChequeRecu chequeRecu = new ChequeRecu();
	private Date dateComptable;
	private Long mntProPre;
	private Long mntCompteVert;
	private Long codValVal;
	private Preavis preavis;
	private String codMrejPre;
	private boolean chequeStatut = false;// cheque 30/31/32/33 en cours de traitement oui/non
	
	private boolean chequeEtat = false;// cheque 30/31/32/33 traitee oui/non
	private boolean lockUnlockCheque = false;// blockUnlockCheque=true blockUnlockCheque=false Blocker/liberer Cheque
	private Long mntBlocage = new Long(0);
	private Long fraisTelegrame = new Long(0);
	private String structure;
	private ParamAgence paramAgence;
	private Cheque cheque = new Cheque();
	private List<ChequeRecu> listechequeRecu = new ArrayList<ChequeRecu>();
	private List<ChequeRecu> listechequeRecuFiltree = new ArrayList<ChequeRecu>();
	private ValueObject chequeTemp;
	private Long codeOperation;
	private ContratCpt contratCpt;
	private Papillon papillon;
	
	private OperationMoyPay operationMoyPay = new OperationMoyPay();
	private Long montantComm = new Long("0");
	private Long tvaComm = new Long("0");
	private Cnp cnp;
	private String refInterSiege;
	private Long mntGolbalRejetCheque;//pap+cnp
	private Long nombreGlobalRejetCheque;//pap+cnp
	private Long mntGlobalRejetChequeBNABNA;//pap+cnp
	private Long mntGlobalRejetChequeBNAAUTRE;//pap+cnp



	private MouvementCompensation mouvementCompensation;
	private boolean commissionNonPercu = false;// commission non percu 
	private boolean chequeForcer = false;// cheque payer avec forcage
	private Long mntForcage;//montant à forcer
	private String numReference;//n° cheque/effet
	private Date  dateEchFaciliteForcage;//pap+cnp
	private boolean genPapSansCro = false;//generer pap apres preavis sans cro 02092015
	private boolean chequeDejaTraite = false;//probleme transact
	
	
	private DetailCheque  detailCheque ;

	
	
	public Cheque getCheque() {
		return cheque;
	}

	public void setCheque(Cheque cheque) {
		this.cheque = cheque;
	}

	public Long getCodeOperation() {
		return codeOperation;
	}

	public void setCodeOperation(Long codeOperation) {
		this.codeOperation = codeOperation;
	}

	public String getEdtionAFaire() {
		return edtionAFaire;
	}

	public void setEdtionAFaire(String edtionAFaire) {
		this.edtionAFaire = edtionAFaire;
	}

	public ChequeRecu getChequeRecu() {
		return chequeRecu;
	}

	public void setChequeRecu(ChequeRecu chequeRecu) {
		this.chequeRecu = chequeRecu;
	}

	public Date getDateComptable() {
		return dateComptable;
	}

	public void setDateComptable(Date dateComptable) {
		this.dateComptable = dateComptable;
	}

	public String getStructure() {
		return structure;
	}

	public void setStructure(String structure) {
		this.structure = structure;
	}

	public void setChequeTemp(ValueObject chequeTemp) {
		this.chequeTemp = chequeTemp;
	}

	public ValueObject getChequeTemp() {
		return chequeTemp;
	}

	public void setLibeleMotifProvision(String libeleMotifProvision) {
		this.libeleMotifProvision = libeleMotifProvision;
	}

	public String getLibeleMotifProvision() {
		return libeleMotifProvision;
	}

	public void setCodMrejPre(String codMrejPre) {
		this.codMrejPre = codMrejPre;
	}

	public String getCodMrejPre() {
		return codMrejPre;
	}

	public void setMntProPre(Long mntProPre) {
		this.mntProPre = mntProPre;
	}

	public Long getMntProPre() {
		return mntProPre;
	}

	public void setContratCpt(ContratCpt contratCpt) {
		this.contratCpt = contratCpt;
	}

	public ContratCpt getContratCpt() {
		return contratCpt;
	}

	public void setParamAgence(ParamAgence paramAgence) {
		this.paramAgence = paramAgence;
	}

	public ParamAgence getParamAgence() {
		return paramAgence;
	}

	/**
	 * @param papillon
	 *            the papillon to set
	 */
	public void setPapillon(Papillon papillon) {
		this.papillon = papillon;
	}

	/**
	 * @return the papillon
	 */
	public Papillon getPapillon() {
		return papillon;
	}

	/**
	 * @param cnp
	 *            the cnp to set
	 */
	public void setCnp(Cnp cnp) {
		this.cnp = cnp;
	}

	/**
	 * @return the cnp
	 */
	public Cnp getCnp() {
		return cnp;
	}

	public void setOperationMoyPay(OperationMoyPay operationMoyPay) {
		this.operationMoyPay = operationMoyPay;
	}

	public OperationMoyPay getOperationMoyPay() {
		return operationMoyPay;
	}

	public void setChequeEtat(boolean chequeEtat) {
		this.chequeEtat = chequeEtat;
	}

	public boolean isChequeEtat() {
		return chequeEtat;
	}

	public void setChequeStatut(boolean chequeStatut) {
		this.chequeStatut = chequeStatut;
	}

	public boolean isChequeStatut() {
		return chequeStatut;
	}

	public void setLockUnlockCheque(boolean lockUnlockCheque) {
		this.lockUnlockCheque = lockUnlockCheque;
	}

	public boolean isLockUnlockCheque() {
		return lockUnlockCheque;
	}

	public void setMntBlocage(Long mntBlocage) {
		this.mntBlocage = mntBlocage;
	}

	public Long getMntBlocage() {
		return mntBlocage;
	}

	public void setFraisTelegrame(Long fraisTelegrame) {
		this.fraisTelegrame = fraisTelegrame;
	}

	public Long getFraisTelegrame() {
		return fraisTelegrame;
	}

	public void setMontantComm(Long montantComm) {
		this.montantComm = montantComm;
	}

	public Long getMontantComm() {
		return montantComm;
	}

	public void setTvaComm(Long tvaComm) {
		this.tvaComm = tvaComm;
	}

	public Long getTvaComm() {
		return tvaComm;
	}

	public void setCodValVal(Long codValVal) {
		this.codValVal = codValVal;
	}

	public Long getCodValVal() {
		return codValVal;
	}

	public void setListechequeRecu(List<ChequeRecu> listechequeRecu) {
		this.listechequeRecu = listechequeRecu;
	}

	public List<ChequeRecu> getListechequeRecu() {
		return listechequeRecu;
	}

	public void setListechequeRecuFiltree(List<ChequeRecu> listechequeRecuFiltree) {
		this.listechequeRecuFiltree = listechequeRecuFiltree;
	}

	public List<ChequeRecu> getListechequeRecuFiltree() {
		return listechequeRecuFiltree;
	}

	public void setNombreGlobalRejetCheque(Long nombreGlobalRejetCheque) {
		this.nombreGlobalRejetCheque = nombreGlobalRejetCheque;
	}

	public Long getNombreGlobalRejetCheque() {
		return nombreGlobalRejetCheque;
	}

	public void setMntGolbalRejetCheque(Long mntGolbalRejetCheque) {
		this.mntGolbalRejetCheque = mntGolbalRejetCheque;
	}

	public Long getMntGolbalRejetCheque() {
		return mntGolbalRejetCheque;
	}

	public void setRefInterSiege(String refInterSiege) {
		this.refInterSiege = refInterSiege;
	}

	public String getRefInterSiege() {
		return refInterSiege;
	}

	public void setMouvementCompensation(MouvementCompensation mouvementCompensation) {
		this.mouvementCompensation = mouvementCompensation;
	}

	public MouvementCompensation getMouvementCompensation() {
		return mouvementCompensation;
	}

	public void setPreavis(Preavis preavis) {
		this.preavis = preavis;
	}

	public Preavis getPreavis() {
		return preavis;
	}

	public void setMntCompteVert(Long mntCompteVert) {
		this.mntCompteVert = mntCompteVert;
	}

	public Long getMntCompteVert() {
		return mntCompteVert;
	}

	public void setCommissionNonPercu(boolean commissionNonPercu) {
		this.commissionNonPercu = commissionNonPercu;
	}

	public boolean isCommissionNonPercu() {
		return commissionNonPercu;
	}

	public void setChequeForcer(boolean chequeForcer) {
		this.chequeForcer = chequeForcer;
	}

	public boolean isChequeForcer() {
		return chequeForcer;
	}

	public void setDateEchFaciliteForcage(Date dateEchFaciliteForcage) {
		this.dateEchFaciliteForcage = dateEchFaciliteForcage;
	}

	public Date getDateEchFaciliteForcage() {
		return dateEchFaciliteForcage;
	}

	public void setMntForcage(Long mntForcage) {
		this.mntForcage = mntForcage;
	}

	public Long getMntForcage() {
		return mntForcage;
	}

	public void setNumReference(String numReference) {
		this.numReference = numReference;
	}

	public String getNumReference() {
		return numReference;
	}

	

	public void setMntGlobalRejetChequeBNABNA(Long mntGlobalRejetChequeBNABNA) {
		this.mntGlobalRejetChequeBNABNA = mntGlobalRejetChequeBNABNA;
	}

	public Long getMntGlobalRejetChequeBNABNA() {
		return mntGlobalRejetChequeBNABNA;
	}

	public void setMntGlobalRejetChequeBNAAUTRE(Long mntGlobalRejetChequeBNAAUTRE) {
		this.mntGlobalRejetChequeBNAAUTRE = mntGlobalRejetChequeBNAAUTRE;
	}

	public Long getMntGlobalRejetChequeBNAAUTRE() {
		return mntGlobalRejetChequeBNAAUTRE;
	}

	public void setGenPapSansCro(boolean genPapSansCro) {
		this.genPapSansCro = genPapSansCro;
	}

	public boolean isGenPapSansCro() {
		return genPapSansCro;
	}

	

	public void setChequeDejaTraite(boolean chequeDejaTraite) {
		this.chequeDejaTraite = chequeDejaTraite;
	}

	public boolean isChequeDejaTraite() {
		return chequeDejaTraite;
	}

	public void setDetailCheque(DetailCheque detailCheque) {
		this.detailCheque = detailCheque;
	}

	public DetailCheque getDetailCheque() {
		return detailCheque;
	}

	

}
