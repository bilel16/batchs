package com.bna.smile.model.domainecompensation.gestionrejet.model;

import java.util.Date;

import com.bna.commun.model.Structure;
import com.oxia.fwk.core.ValueObject;

public class CompensationEffetVo extends ValueObject {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Structure structure;
	private Date dateComptable;
	private Long montGlobEffet=0L;
	private Long nbrGlobEffet=0L;
	
	
	
	public void setDateComptable(Date dateComptable) {
		this.dateComptable = dateComptable;
	}
	public Date getDateComptable() {
		return dateComptable;
	}
	/**
	 * @param montGlobEffet the montGlobEffet to set
	 */
	public void setMontGlobEffet(Long montGlobEffet) {
		this.montGlobEffet = montGlobEffet;
	}
	/**
	 * @return the montGlobEffet
	 */
	public Long getMontGlobEffet() {
		return montGlobEffet;
	}
	/**
	 * @param nbrGlobEffet the nbrGlobEffet to set
	 */
	public void setNbrGlobEffet(Long nbrGlobEffet) {
		this.nbrGlobEffet = nbrGlobEffet;
	}
	/**
	 * @return the nbrGlobEffet
	 */
	public Long getNbrGlobEffet() {
		return nbrGlobEffet;
	}
	public void setStructure(Structure structure) {
		this.structure = structure;
	}
	public Structure getStructure() {
		return structure;
	}

}
