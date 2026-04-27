package com.bna.smile.model.domainecommun.model;

import java.io.Serializable;

public class ListeRIBSocietesAFBView implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Long numSoctAFB;
	private String nomSoctAFB;
	private String ribSoctAFB;
	private Long codStrcStrc;

	// ******Getter && Setter ***********//
	
	public Long getNumSoctAFB() {
		return numSoctAFB;
	}

	public void setNumSoctAFB(Long numSoctAFB) {
		this.numSoctAFB = numSoctAFB;
	}

	public String getNomSoctAFB() {
		return nomSoctAFB;
	}

	public void setNomSoctAFB(String nomSoctAFB) {
		this.nomSoctAFB = nomSoctAFB;
	}

	public String getRibSoctAFB() {
		return ribSoctAFB;
	}

	public void setRibSoctAFB(String ribSoctAFB) {
		this.ribSoctAFB = ribSoctAFB;
	}

	public void setCodStrcStrc(Long codStrcStrc) {
		this.codStrcStrc = codStrcStrc;
	}

	public Long getCodStrcStrc() {
		return codStrcStrc;
	}

}
