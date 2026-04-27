package com.bna.smile.model.traitementCompensationRecu.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.bna.commun.model.Cheque;
import com.oxia.fwk.core.ValueObject;

public class CompensationRecuVo extends ValueObject implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 
	 */
	private List<Cheque> listeCheque = new ArrayList<Cheque>();
	/**
	 * 
	 */
	private Cheque selectedCheque = new Cheque();
	/**
	 * 
	 */
	private Date dateComptable = new Date();

	/**
	 * @param listeCheque
	 */
	public void setListeCheque(List<Cheque> listeCheque) {
		this.listeCheque = listeCheque;
	}

	public List<Cheque> getListeCheque() {
		return listeCheque;
	}

	public void setSelectedCheque(Cheque selectedCheque) {
		this.selectedCheque = selectedCheque;
	}

	public Cheque getSelectedCheque() {
		return selectedCheque;
	}

	/**
	 * @param dateComptable
	 *            the dateComptable to set
	 */
	public void setDateComptable(Date dateComptable) {
		this.dateComptable = dateComptable;
	}

	/**
	 * @return the dateComptable
	 */
	public Date getDateComptable() {
		return dateComptable;
	}

}
