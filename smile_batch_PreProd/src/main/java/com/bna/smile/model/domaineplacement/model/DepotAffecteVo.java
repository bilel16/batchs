package com.bna.smile.model.domaineplacement.model;

import java.util.Date;

import com.bna.commun.model.ContratCpt;
import com.oxia.fwk.core.ValueObject;

public class DepotAffecteVo extends ValueObject {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String numDecDeccg;
	private Long numSeqDemande;
	private ContratCpt CompteDepot = new ContratCpt();
	private Long mntAutoDepot;
	private ContratCpt compteMemeClientDepot = new ContratCpt();
	private ContratCpt compteAutreClientDepot = new ContratCpt();
	private Date dateAutoDepot;
	private Long montBloqueDemande;
	private String numPlacement;
	private String codEtatDepot;
	private Date dateDemande;
	private Date dateLiquidation;
	private String numOperationLiquidation;

	/********************************/

	public DepotAffecteVo() {
		super();
	}

	/*******************************/
	public String getNumDecDeccg() {
		return numDecDeccg;
	}

	public void setNumDecDeccg(String numDecDeccg) {
		this.numDecDeccg = numDecDeccg;
	}

	public ContratCpt getCompteDepot() {
		return CompteDepot;
	}

	public void setCompteDepot(ContratCpt compteDepot) {
		CompteDepot = compteDepot;
	}

	public Long getMntAutoDepot() {
		return mntAutoDepot;
	}

	public void setMntAutoDepot(Long mntAutoDepot) {
		this.mntAutoDepot = mntAutoDepot;
	}

	public ContratCpt getCompteMemeClientDepot() {
		return compteMemeClientDepot;
	}

	public void setCompteMemeClientDepot(ContratCpt compteMemeClientDepot) {
		this.compteMemeClientDepot = compteMemeClientDepot;
	}

	public ContratCpt getCompteAutreClientDepot() {
		return compteAutreClientDepot;
	}

	public void setCompteAutreClientDepot(ContratCpt compteAutreClientDepot) {
		this.compteAutreClientDepot = compteAutreClientDepot;
	}

	public Date getDateAutoDepot() {
		return dateAutoDepot;
	}

	public void setDateAutoDepot(Date dateAutoDepot) {
		this.dateAutoDepot = dateAutoDepot;
	}

	public Long getMontBloqueDemande() {
		return montBloqueDemande;
	}

	public void setMontBloqueDemande(Long montBloqueDemande) {
		this.montBloqueDemande = montBloqueDemande;
	}

	public String getNumPlacement() {
		return numPlacement;
	}

	public void setNumPlacement(String numPlacement) {
		this.numPlacement = numPlacement;
	}

	public String getCodEtatDepot() {
		return codEtatDepot;
	}

	public void setCodEtatDepot(String codEtatDepot) {
		this.codEtatDepot = codEtatDepot;
	}

	public Date getDateDemande() {
		return dateDemande;
	}

	public void setDateDemande(Date dateDemande) {
		this.dateDemande = dateDemande;
	}

	public Date getDateLiquidation() {
		return dateLiquidation;
	}

	public void setDateLiquidation(Date dateLiquidation) {
		this.dateLiquidation = dateLiquidation;
	}

	public String getNumOperationLiquidation() {
		return numOperationLiquidation;
	}

	public void setNumOperationLiquidation(String numOperationLiquidation) {
		this.numOperationLiquidation = numOperationLiquidation;
	}

	public Long getNumSeqDemande() {
		return numSeqDemande;
	}

	public void setNumSeqDemande(Long numSeqDemande) {
		this.numSeqDemande = numSeqDemande;
	}

}
