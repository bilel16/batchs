package com.bna.smile.model.banqueAssurance.model;

import java.util.Date;

import com.bna.commun.model.AdhesionAssVie;
import com.bna.commun.model.Assureur;
import com.bna.commun.model.ContratCpt;
import com.bna.commun.model.Operation;
import com.bna.commun.model.OperationMoyPay;
import com.bna.commun.model.Structure;
import com.bna.commun.model.Tache;
import com.bna.commun.model.TraceAssuranceVieDecouvert;
import com.bna.smile.web.commun.model.ParamAgence;
import com.oxia.fwk.core.ValueObject;

public class ParamAdhesion extends ValueObject {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private AdhesionAssVie adhesionAssVie;
	private Date dateComptable;
	private ContratCpt nouveauCpt;
	private Assureur assureur;
	private String interSiege;
	private boolean etatValidation;
	private Operation operation;
	private ParamAgence paramAgence;
	private TraceAssuranceVieDecouvert traceAssuranceVieDecouvert;
	private Tache tache;
	private OperationMoyPay operationMoyPay;
	private OperationMoyPay operationMoyPayAssureur;
	private Structure structure;
	private String messageValidation;
	private OperationMoyPay operationMoyPayRs;
	
	/**********************************/
	public ParamAdhesion() {
	}

	/*************************************/
	public void setAdhesionAssVie(AdhesionAssVie adhesionAssVie) {
		this.adhesionAssVie = adhesionAssVie;
	}

	public AdhesionAssVie getAdhesionAssVie() {
		return adhesionAssVie;
	}

	public void setDateComptable(Date dateComptable) {
		this.dateComptable = dateComptable;
	}

	public Date getDateComptable() {
		return dateComptable;
	}

	public void setNouveauCpt(ContratCpt nouveauCpt) {
		this.nouveauCpt = nouveauCpt;
	}

	public ContratCpt getNouveauCpt() {
		return nouveauCpt;
	}

	public void setAssureur(Assureur assureur) {
		this.assureur = assureur;
	}

	public Assureur getAssureur() {
		return assureur;
	}

	public void setInterSiege(String interSiege) {
		this.interSiege = interSiege;
	}

	public String getInterSiege() {
		return interSiege;
	}

	public boolean isEtatValidation() {
		return etatValidation;
	}

	public void setEtatValidation(boolean etatValidation) {
		this.etatValidation = etatValidation;
	}

	public Operation getOperation() {
		return operation;
	}

	public void setOperation(Operation operation) {
		this.operation = operation;
	}

	public ParamAgence getParamAgence() {
		return paramAgence;
	}

	public void setParamAgence(ParamAgence paramAgence) {
		this.paramAgence = paramAgence;
	}

	public TraceAssuranceVieDecouvert getTraceAssuranceVieDecouvert() {
		return traceAssuranceVieDecouvert;
	}

	public void setTraceAssuranceVieDecouvert(TraceAssuranceVieDecouvert traceAssuranceVieDecouvert) {
		this.traceAssuranceVieDecouvert = traceAssuranceVieDecouvert;
	}

	public Tache getTache() {
		return tache;
	}

	public void setTache(Tache tache) {
		this.tache = tache;
	}

	public OperationMoyPay getOperationMoyPay() {
		return operationMoyPay;
	}

	public void setOperationMoyPay(OperationMoyPay operationMoyPay) {
		this.operationMoyPay = operationMoyPay;
	}

	public OperationMoyPay getOperationMoyPayAssureur() {
		return operationMoyPayAssureur;
	}

	public void setOperationMoyPayAssureur(OperationMoyPay operationMoyPayAssureur) {
		this.operationMoyPayAssureur = operationMoyPayAssureur;
	}

	public Structure getStructure() {
		return structure;
	}

	public void setStructure(Structure structure) {
		this.structure = structure;
	}

	public String getMessageValidation() {
		return messageValidation;
	}

	public void setMessageValidation(String messageValidation) {
		this.messageValidation = messageValidation;
	}

	public OperationMoyPay getOperationMoyPayRs() {
		return operationMoyPayRs;
	}

	public void setOperationMoyPayRs(OperationMoyPay operationMoyPayRs) {
		this.operationMoyPayRs = operationMoyPayRs;
	}
}
