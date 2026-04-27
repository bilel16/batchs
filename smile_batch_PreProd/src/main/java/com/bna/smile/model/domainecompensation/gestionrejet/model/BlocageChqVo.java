package com.bna.smile.model.domainecompensation.gestionrejet.model;

import java.util.Date;

import com.bna.commun.model.BlocageCheque;
import com.bna.commun.model.Cheque;
import com.bna.commun.model.OperationMoyPay;
import com.bna.smile.web.commun.model.ParamAgence;
import com.oxia.fwk.core.ValueObject;

public class BlocageChqVo extends ValueObject {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public BlocageChqVo() {
	}

	private BlocageCheque blocageCheque;
	private Long sommeBlocage;
	private Long montProv;
	private Long montVertUtilise;
	private ParamAgence paramAgence;
	private Date dateComptable;
	private OperationMoyPay operationMoyPay;
	private Cheque cheque;
	private String codMotifRej;
	private String typeBlocage;
	public BlocageCheque getBlocageCheque() {
		return blocageCheque;
	}

	public void setBlocageCheque(BlocageCheque blocageCheque) {
		this.blocageCheque = blocageCheque;
	}

	public Long getMontProv() {
		return montProv;
	}

	public void setMontProv(Long montProv) {
		this.montProv = montProv;
	}

	public Long getSommeBlocage() {
		return sommeBlocage;
	}

	public void setSommeBlocage(Long sommeBlocage) {
		this.sommeBlocage = sommeBlocage;
	}

	public void setDateComptable(Date dateComptable) {
		this.dateComptable = dateComptable;
	}

	public Date getDateComptable() {
		return dateComptable;
	}

	public void setParamAgence(ParamAgence paramAgence) {
		this.paramAgence = paramAgence;
	}

	public ParamAgence getParamAgence() {
		return paramAgence;
	}

	public void setOperationMoyPay(OperationMoyPay operationMoyPay) {
		this.operationMoyPay = operationMoyPay;
	}

	public OperationMoyPay getOperationMoyPay() {
		return operationMoyPay;
	}

	public void setCheque(Cheque cheque) {
		this.cheque = cheque;
	}

	public Cheque getCheque() {
		return cheque;
	}

	public void setCodMotifRej(String codMotifRej) {
		this.codMotifRej = codMotifRej;
	}

	public String getCodMotifRej() {
		return codMotifRej;
	}

	public void setTypeBlocage(String typeBlocage) {
		this.typeBlocage = typeBlocage;
	}

	public String getTypeBlocage() {
		return typeBlocage;
	}

	/**
	 * @param montVertUtilise the montVertUtilise to set
	 */
	public void setMontVertUtilise(Long montVertUtilise) {
		this.montVertUtilise = montVertUtilise;
	}

	/**
	 * @return the montVertUtilise
	 */
	public Long getMontVertUtilise() {
		return montVertUtilise;
	}

	
}
