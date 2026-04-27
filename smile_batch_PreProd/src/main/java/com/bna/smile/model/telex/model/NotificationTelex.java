package com.bna.smile.model.telex.model;

import java.util.Date;

public class NotificationTelex {

	
	private Long NUM_TEL;	
	private Integer CODE_AGENCE;
	private Date DATE_GENERATION;
	private String TYPE_DOSSIER;
	private Long NUM_DOSSIER;
	private Date DATE_OPERATION;
	private String RELATION;
	private String ADRESSE;
	private String CODE_POSTAL;
	private Integer CODE_DEVISE;
	private Long MONTANT;
	private String ORDONNATEUR;
	private String COMMENTAIRE;
	private Integer ETAT;
	private String COMPTE_CLIENT;
	
	
	
	public String getCOMPTE_CLIENT() {
		return COMPTE_CLIENT;
	}
	public void setCOMPTE_CLIENT(String cOMPTE_CLIENT) {
		COMPTE_CLIENT = cOMPTE_CLIENT;
	}
	public Long getNUM_TEL() {
		return NUM_TEL;
	}
	public void setNUM_TEL(Long nUM_TEL) {
		NUM_TEL = nUM_TEL;
	}
	public Integer getCODE_AGENCE() {
		return CODE_AGENCE;
	}
	public void setCODE_AGENCE(Integer cODE_AGENCE) {
		CODE_AGENCE = cODE_AGENCE;
	}
	public Date getDATE_GENERATION() {
		return DATE_GENERATION;
	}
	public void setDATE_GENERATION(Date dATE_GENERATION) {
		DATE_GENERATION = dATE_GENERATION;
	}
	public String getTYPE_DOSSIER() {
		return TYPE_DOSSIER;
	}
	public void setTYPE_DOSSIER(String tYPE_DOSSIER) {
		TYPE_DOSSIER = tYPE_DOSSIER;
	}
	public Long getNUM_DOSSIER() {
		return NUM_DOSSIER;
	}
	public void setNUM_DOSSIER(Long nUM_DOSSIER) {
		NUM_DOSSIER = nUM_DOSSIER;
	}
	public Date getDATE_OPERATION() {
		return DATE_OPERATION;
	}
	public void setDATE_OPERATION(Date dATE_OPERATION) {
		DATE_OPERATION = dATE_OPERATION;
	}
	public String getRELATION() {
		return RELATION;
	}
	public void setRELATION(String rELATION) {
		RELATION = rELATION;
	}
	public String getADRESSE() {
		return ADRESSE;
	}
	public void setADRESSE(String aDRESSE) {
		ADRESSE = aDRESSE;
	}
	public String getCODE_POSTAL() {
		return CODE_POSTAL;
	}
	public void setCODE_POSTAL(String cODE_POSTAL) {
		CODE_POSTAL = cODE_POSTAL;
	}
	public Integer getCODE_DEVISE() {
		return CODE_DEVISE;
	}
	public void setCODE_DEVISE(Integer cODE_DEVISE) {
		CODE_DEVISE = cODE_DEVISE;
	}
	public Long getMONTANT() {
		return MONTANT;
	}
	public void setMONTANT(Long mONTANT) {
		MONTANT = mONTANT;
	}
	public String getORDONNATEUR() {
		return ORDONNATEUR;
	}
	public void setORDONNATEUR(String oRDONNATEUR) {
		ORDONNATEUR = oRDONNATEUR;
	}
	public String getCOMMENTAIRE() {
		return COMMENTAIRE;
	}
	public void setCOMMENTAIRE(String cOMMENTAIRE) {
		COMMENTAIRE = cOMMENTAIRE;
	}
	public Integer getETAT() {
		return ETAT;
	}
	public void setETAT(Integer eTAT) {
		ETAT = eTAT;
	}
	
	
	
}
