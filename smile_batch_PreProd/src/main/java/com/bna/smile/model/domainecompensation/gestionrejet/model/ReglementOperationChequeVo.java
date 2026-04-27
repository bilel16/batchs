package com.bna.smile.model.domainecompensation.gestionrejet.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.bna.commun.model.OperationMoyPay;
import com.bna.commun.model.ReglementOperationCheque;
import com.oxia.fwk.core.ValueObject;

/**
 * @author JAOUALI Yossri
 * 
 */
public class ReglementOperationChequeVo extends ValueObject implements Serializable {


	private OperationMoyPay operationMoyPay;
	private List<ReglementOperationCheque> listeReglementOperationCheque= new ArrayList<ReglementOperationCheque>();
	Long montantRestant;
	public OperationMoyPay getOperationMoyPay() {
		return operationMoyPay;
	}
	public void setOperationMoyPay(OperationMoyPay operationMoyPay) {
		this.operationMoyPay = operationMoyPay;
	}
	public List<ReglementOperationCheque> getListeReglementOperationCheque() {
		return listeReglementOperationCheque;
	}
	public void setListeReglementOperationCheque(
			List<ReglementOperationCheque> listeReglementOperationCheque) {
		this.listeReglementOperationCheque = listeReglementOperationCheque;
	}
	public Long getMontantRestant() {
		return montantRestant;
	}
	public void setMontantRestant(Long montantRestant) {
		this.montantRestant = montantRestant;
	}
	

	


}
