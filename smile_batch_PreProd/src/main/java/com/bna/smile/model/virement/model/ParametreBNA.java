package com.bna.smile.model.virement.model;

import com.oxia.fwk.core.ValueObject;

public class ParametreBNA extends ValueObject {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String nomParametre;
	private String valParametre;
	private Long numHdjJrn;
	private Long numHfjJrn;

	// *******************************//

	public ParametreBNA() {
		super();
	}

	public ParametreBNA(String nomParametre, String valParametre) {
		super();
		this.nomParametre = nomParametre;
		this.valParametre = valParametre;

	}

	public ParametreBNA(String nomParametre, String valParametre, Long numHdjJrn, Long numHfjJrn) {
		super();
		this.nomParametre = nomParametre;
		this.valParametre = valParametre;
		this.numHdjJrn = numHdjJrn;
		this.numHfjJrn = numHfjJrn;
	}

	// *********Getter and Setter **********//
	
	public String getNomParametre() {
		return nomParametre;
	}

	public String getValParametre() {
		return valParametre;
	}

	public Long getNumHdjJrn() {
		return numHdjJrn;
	}

	public Long getNumHfjJrn() {
		return numHfjJrn;
	}

	public void setNomParametre(String nomParametre) {
		this.nomParametre = nomParametre;
	}

	public void setValParametre(String valParametre) {
		this.valParametre = valParametre;
	}

	public void setNumHdjJrn(Long numHdjJrn) {
		this.numHdjJrn = numHdjJrn;
	}

	public void setNumHfjJrn(Long numHfjJrn) {
		this.numHfjJrn = numHfjJrn;
	}

}