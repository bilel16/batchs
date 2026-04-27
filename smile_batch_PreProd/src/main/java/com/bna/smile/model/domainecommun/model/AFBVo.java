package com.bna.smile.model.domainecommun.model;

import java.io.File;
import java.io.Serializable;
import java.util.Date;

import com.oxia.fwk.core.ValueObject;

public class AFBVo extends ValueObject implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long codStrcStrc;
	private Date dateComptable;
	private Date dateDebut;
	private Date dateFin;
	private boolean etatEnregistrement = false;
	private String messageValidation;
	private boolean stadeEnregistrement = false;
	private String rib;
	private File file;
	private SocietesAFBView societesAFBView;
	private Long codeSocietesAFB;

	/** Constructeur */
	public AFBVo() {
	}

	public void setCodStrcStrc(Long codStrcStrc) {
		this.codStrcStrc = codStrcStrc;
	}

	public Long getCodStrcStrc() {
		return codStrcStrc;
	}

	public void setDateComptable(Date dateComptable) {
		this.dateComptable = dateComptable;
	}

	public Date getDateComptable() {
		return dateComptable;
	}

	public void setEtatEnregistrement(boolean etatEnregistrement) {
		this.etatEnregistrement = etatEnregistrement;
	}

	public boolean isEtatEnregistrement() {
		return etatEnregistrement;
	}

	public void setMessageValidation(String messageValidation) {
		this.messageValidation = messageValidation;
	}

	public String getMessageValidation() {
		return messageValidation;
	}

	public void setStadeEnregistrement(boolean stadeEnregistrement) {
		this.stadeEnregistrement = stadeEnregistrement;
	}

	public boolean isStadeEnregistrement() {
		return stadeEnregistrement;
	}

	public void setFile(File file) {
		this.file = file;
	}

	public File getFile() {
		return file;
	}

	public void setRib(String rib) {
		this.rib = rib;
	}

	public String getRib() {
		return rib;
	}

	public void setSocietesAFBView(SocietesAFBView societesAFBView) {
		this.societesAFBView = societesAFBView;
	}

	public SocietesAFBView getSocietesAFBView() {
		return societesAFBView;
	}

	public void setDateDebut(Date dateDebut) {
		this.dateDebut = dateDebut;
	}

	public Date getDateDebut() {
		return dateDebut;
	}

	public void setDateFin(Date dateFin) {
		this.dateFin = dateFin;
	}

	public Date getDateFin() {
		return dateFin;
	}

	public void setCodeSocietesAFB(Long codeSocietesAFB) {
		this.codeSocietesAFB = codeSocietesAFB;
	}

	public Long getCodeSocietesAFB() {
		return codeSocietesAFB;
	}

}
