package com.bna.smile.model.compteGod.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.bna.commun.model.BlocageGod;
import com.bna.commun.model.ContratCpt;
import com.bna.commun.model.ContratGod;
import com.bna.commun.model.DeblocageGod;
import com.bna.commun.model.OperationMoyPay;
import com.bna.commun.vo.ContratCptSold;
import com.bna.smile.web.commun.model.ParamAgence;
import com.oxia.fwk.core.ValueObject;

public class BlocageGodVo extends ValueObject implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private BlocageGod blocageGod;
	private DeblocageGod deblocageGod;
	private OperationMoyPay operationMoyPay;
	private ParamAgence paramAgence;
	private List<BlocageGod> listeBlocageEnAtt;
	private List<BlocageGod> listeBlocageVal;
	private List<DeblocageGod> listeDeblocEnAtt;
	private ContratCpt contratCptRech;
	private List<BlocageGod> listeBlocageGodClt;
	private long CodOper;
	private double coursFixe;
	private List<BlocageGod> listeBlocageDevEnAtt;
	private List<BlocageGod> listBlocageDevVal;
	private List<DeblocageGod> listDeblocageDevEnAtt;
	private long typeCcpt;
	private ContratCptSold contratCptSold;
	private ContratGod contratGod;
	private String msgValidation;
	private boolean etatValidation = false;
	private Long etatBlocage;
	private String motifOperation;
	private Date dateOperation;

	/*************************************/
	public List<BlocageGod> getListeBlocageDevEnAtt() {
		return listeBlocageDevEnAtt;
	}

	public void setListeBlocageDevEnAtt(List<BlocageGod> listeBlocageDevEnAtt) {
		this.listeBlocageDevEnAtt = listeBlocageDevEnAtt;
	}

	public List<BlocageGod> getListBlocageDevVal() {
		return listBlocageDevVal;
	}

	public void setListBlocageDevVal(List<BlocageGod> listBlocageDevVal) {
		this.listBlocageDevVal = listBlocageDevVal;
	}

	public List<DeblocageGod> getListDeblocageDevEnAtt() {
		return listDeblocageDevEnAtt;
	}

	public void setListDeblocageDevEnAtt(List<DeblocageGod> listDeblocageDevEnAtt) {
		this.listDeblocageDevEnAtt = listDeblocageDevEnAtt;
	}

	public long getTypeCcpt() {
		return typeCcpt;
	}

	public void setTypeCcpt(long typeCcpt) {
		this.typeCcpt = typeCcpt;
	}

	public long getCodOper() {
		return CodOper;
	}

	public void setCodOper(long codOper) {
		CodOper = codOper;
	}

	public BlocageGod getBlocageGod() {
		return blocageGod;
	}

	public void setBlocageGod(BlocageGod blocageGod) {
		this.blocageGod = blocageGod;
	}

	public OperationMoyPay getOperationMoyPay() {
		return operationMoyPay;
	}

	public void setOperationMoyPay(OperationMoyPay operationMoyPay) {
		this.operationMoyPay = operationMoyPay;
	}

	public ParamAgence getParamAgence() {
		return paramAgence;
	}

	public void setParamAgence(ParamAgence paramAgence) {
		this.paramAgence = paramAgence;
	}

	public List<BlocageGod> getListeBlocageEnAtt() {
		return listeBlocageEnAtt;
	}

	public void setListeBlocageEnAtt(List<BlocageGod> listeBlocageEnAtt) {
		this.listeBlocageEnAtt = listeBlocageEnAtt;
	}

	public List<BlocageGod> getListeBlocageVal() {
		return listeBlocageVal;
	}

	public void setListeBlocageVal(List<BlocageGod> listeBlocageVal) {
		this.listeBlocageVal = listeBlocageVal;
	}

	public DeblocageGod getDeblocageGod() {
		return deblocageGod;
	}

	public void setDeblocageGod(DeblocageGod deblocageGod) {
		this.deblocageGod = deblocageGod;
	}

	public List<DeblocageGod> getListeDeblocEnAtt() {
		return listeDeblocEnAtt;
	}

	public void setListeDeblocEnAtt(List<DeblocageGod> listeDeblocEnAtt) {
		this.listeDeblocEnAtt = listeDeblocEnAtt;
	}

	public ContratCpt getContratCptRech() {
		return contratCptRech;
	}

	public void setContratCptRech(ContratCpt contratCptRech) {
		this.contratCptRech = contratCptRech;
	}

	public List<BlocageGod> getListeBlocageGodClt() {
		return listeBlocageGodClt;
	}

	public void setListeBlocageGodClt(List<BlocageGod> listeBlocageGodClt) {
		this.listeBlocageGodClt = listeBlocageGodClt;
	}

	public void setMsgValidation(String msgValidation) {
		this.msgValidation = msgValidation;
	}

	public String getMsgValidation() {
		return msgValidation;
	}

	public void setEtatValidation(boolean etatValidation) {
		this.etatValidation = etatValidation;
	}

	public boolean isEtatValidation() {
		return etatValidation;
	}

	public ContratGod getContratGod() {
		return contratGod;
	}

	public void setContratGod(ContratGod contratGod) {
		this.contratGod = contratGod;
	}

	public ContratCptSold getContratCptSold() {
		return contratCptSold;
	}

	public void setContratCptSold(ContratCptSold contratCptSold) {
		this.contratCptSold = contratCptSold;
	}

	public double getCoursFixe() {
		return coursFixe;
	}

	public void setCoursFixe(double coursFixe) {
		this.coursFixe = coursFixe;
	}

	public void setEtatBlocage(Long etatBlocage) {
		this.etatBlocage = etatBlocage;
	}

	public Long getEtatBlocage() {
		return etatBlocage;
	}

	public void setMotifOperation(String motifOperation) {
		this.motifOperation = motifOperation;
	}

	public String getMotifOperation() {
		return motifOperation;
	}

	public void setDateOperation(Date dateOperation) {
		this.dateOperation = dateOperation;
	}

	public Date getDateOperation() {
		return dateOperation;
	}

	
}
