package com.bna.smile.model.assVieEpargneEtude.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.bna.commun.model.Categorie;
import com.bna.commun.model.ContratCpt;
import com.bna.commun.model.OperationMoyPay;

public class TraceAssuranceFaiezVo {

	private ContratCpt contratCpt;
	private long mont_trch1_cat;
	private long mont_trch2_cat;
	private long mont_trch3_cat;
	private String cod_etat_trch1;
	private String cod_etat_trch2;
	private String cod_etat_trch3;
	private Date dat_pay_trch1;
	private Date dat_pay_trch2;
	private Date dat_pay_trch3;
	private Long codTpceTpce;
	private String numPcePers;
	private String nomNomPers;
	private String nomPrnPers;
	private OperationMoyPay operationMoyPay;
	public TraceAssuranceFaiezVo() {
		super();
	}

	public TraceAssuranceFaiezVo(ContratCpt contratCpt, long mont_trch1_cat, long mont_trch2_cat, long mont_trch3_cat,
			String cod_etat_trch1, String cod_etat_trch2, String cod_etat_trch3, Date dat_pay_trch1, Date dat_pay_trch2,
			Date dat_pay_trch3, Long codTpceTpce, String numPcePers, String nomNomPers, String nomPrnPers,
			OperationMoyPay operationMoyPay) {
		super();
		this.contratCpt = contratCpt;
		this.mont_trch1_cat = mont_trch1_cat;
		this.mont_trch2_cat = mont_trch2_cat;
		this.mont_trch3_cat = mont_trch3_cat;
		this.cod_etat_trch1 = cod_etat_trch1;
		this.cod_etat_trch2 = cod_etat_trch2;
		this.cod_etat_trch3 = cod_etat_trch3;
		this.dat_pay_trch1 = dat_pay_trch1;
		this.dat_pay_trch2 = dat_pay_trch2;
		this.dat_pay_trch3 = dat_pay_trch3;
		this.codTpceTpce = codTpceTpce;
		this.numPcePers = numPcePers;
		this.nomNomPers = nomNomPers;
		this.nomPrnPers = nomPrnPers;
		this.operationMoyPay = operationMoyPay;
	}

	public ContratCpt getContratCpt() {
		return contratCpt;
	}
	public void setContratCpt(ContratCpt contratCpt) {
		this.contratCpt = contratCpt;
	}

	public long getMont_trch1_cat() {
		return mont_trch1_cat;
	}

	public void setMont_trch1_cat(long mont_trch1_cat) {
		this.mont_trch1_cat = mont_trch1_cat;
	}

	public long getMont_trch2_cat() {
		return mont_trch2_cat;
	}

	public void setMont_trch2_cat(long mont_trch2_cat) {
		this.mont_trch2_cat = mont_trch2_cat;
	}

	public long getMont_trch3_cat() {
		return mont_trch3_cat;
	}

	public void setMont_trch3_cat(long mont_trch3_cat) {
		this.mont_trch3_cat = mont_trch3_cat;
	}

	public String getCod_etat_trch1() {
		return cod_etat_trch1;
	}

	public void setCod_etat_trch1(String cod_etat_trch1) {
		this.cod_etat_trch1 = cod_etat_trch1;
	}

	public String getCod_etat_trch2() {
		return cod_etat_trch2;
	}

	public void setCod_etat_trch2(String cod_etat_trch2) {
		this.cod_etat_trch2 = cod_etat_trch2;
	}

	public String getCod_etat_trch3() {
		return cod_etat_trch3;
	}

	public void setCod_etat_trch3(String cod_etat_trch3) {
		this.cod_etat_trch3 = cod_etat_trch3;
	}

	public Date getDat_pay_trch1() {
		return dat_pay_trch1;
	}

	public void setDat_pay_trch1(Date dat_pay_trch1) {
		this.dat_pay_trch1 = dat_pay_trch1;
	}

	public Date getDat_pay_trch2() {
		return dat_pay_trch2;
	}

	public void setDat_pay_trch2(Date dat_pay_trch2) {
		this.dat_pay_trch2 = dat_pay_trch2;
	}

	public Date getDat_pay_trch3() {
		return dat_pay_trch3;
	}

	public void setDat_pay_trch3(Date dat_pay_trch3) {
		this.dat_pay_trch3 = dat_pay_trch3;
	}

	public Long getCodTpceTpce() {
		return codTpceTpce;
	}

	public void setCodTpceTpce(Long codTpceTpce) {
		this.codTpceTpce = codTpceTpce;
	}

	public String getNumPcePers() {
		return numPcePers;
	}

	public void setNumPcePers(String numPcePers) {
		this.numPcePers = numPcePers;
	}

	public String getNomNomPers() {
		return nomNomPers;
	}

	public void setNomNomPers(String nomNomPers) {
		this.nomNomPers = nomNomPers;
	}

	public String getNomPrnPers() {
		return nomPrnPers;
	}

	public void setNomPrnPers(String nomPrnPers) {
		this.nomPrnPers = nomPrnPers;
	}

	public OperationMoyPay getOperationMoyPay() {
		return operationMoyPay;
	}

	public void setOperationMoyPay(OperationMoyPay operationMoyPay) {
		this.operationMoyPay = operationMoyPay;
	}
	
}
