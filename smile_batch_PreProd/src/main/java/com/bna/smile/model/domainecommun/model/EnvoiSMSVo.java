package com.bna.smile.model.domainecommun.model;

import java.io.Serializable;
import java.util.Date;

import com.bna.commun.model.ContratCpt;
import com.bna.smile.model.SMS.model.PowerCardSMS;
import com.oxia.fwk.core.ValueObject;

public class EnvoiSMSVo extends ValueObject implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long codStrcStrc;
	private Date dateComptable;
	private boolean etatEnregistrement = false;
	private String messageValidation;
	private boolean stadeEnregistrement = false;
	private ContratCpt contratCpt = new ContratCpt();
	private String numTelephone;
	private Long montantOperation;
	private Long nbreOperation;
	private Long codeOperation;
	private String libOperation;
	private String sens;
	private String textSms;
	private PowerCardSMS powerCardSMS = new PowerCardSMS();
	/********************************/
	/** Constructeur */
	public EnvoiSMSVo() {
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

	public ContratCpt getContratCpt() {
		return contratCpt;
	}

	public void setContratCpt(ContratCpt contratCpt) {
		this.contratCpt = contratCpt;
	}

	public String getNumTelephone() {
		return numTelephone;
	}

	public void setNumTelephone(String numTelephone) {
		this.numTelephone = numTelephone;
	}

	public Long getMontantOperation() {
		return montantOperation;
	}

	public void setMontantOperation(Long montantOperation) {
		this.montantOperation = montantOperation;
	}

	public Long getNbreOperation() {
		return nbreOperation;
	}

	public void setNbreOperation(Long nbreOperation) {
		this.nbreOperation = nbreOperation;
	}

	public Long getCodeOperation() {
		return codeOperation;
	}

	public void setCodeOperation(Long codeOperation) {
		this.codeOperation = codeOperation;
	}

	public String getLibOperation() {
		return libOperation;
	}

	public void setLibOperation(String libOperation) {
		this.libOperation = libOperation;
	}

	public String getSens() {
		return sens;
	}

	public void setSens(String sens) {
		this.sens = sens;
	}

	public String getTextSms() {
		return textSms;
	}

	public void setTextSms(String textSms) {
		this.textSms = textSms;
	}

	public PowerCardSMS getPowerCardSMS() {
		return powerCardSMS;
	}

	public void setPowerCardSMS(PowerCardSMS powerCardSMS) {
		this.powerCardSMS = powerCardSMS;
	}

}
