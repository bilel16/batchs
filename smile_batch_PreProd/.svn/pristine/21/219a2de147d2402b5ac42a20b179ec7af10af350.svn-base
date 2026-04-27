package com.bna.smile.model.domaineguichet.model;

/**
 * 
 * @author YAZIDI Mohamed
 * @since 19/09/2014
 */
import java.io.BufferedWriter;
import java.util.List;

import com.bna.commun.model.OperationMoyPay;
import com.bna.commun.model.TmpBatchVirNSI;
import com.bna.smile.batch.test.BatchMAJNSIActelFrame;
import com.bna.smile.batch.test.BatchMAJNSIFrame;
import com.oxia.fwk.core.ValueObject;

public class MAJNSIVo extends ValueObject {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	String ligne;
	List<AgencesMAJNSIVo> listAgencesMAJNSI;
	AgencesMAJNSIVo AgNonNsi;
	List<Long> listAgencesMAJNSIStrc;
	List<MvtDevise> mvtDevises ;
	BufferedWriter bufWriter;
	TmpBatchVirNSI tmpBatchVirNSI;
	BatchMAJNSIFrame mainFrame;
	BatchMAJNSIActelFrame mainFrameActel;
	OperationMoyPay operationMoyPay;
	
	public MAJNSIVo() {

	}

	public String getLigne() {
		return ligne;
	}

	public void setLigne(String ligne) {
		this.ligne = ligne;
	}

	public List<AgencesMAJNSIVo> getListAgencesMAJNSI() {
		return listAgencesMAJNSI;
	}

	public void setListAgencesMAJNSI(List<AgencesMAJNSIVo> listAgencesMAJNSI) {
		this.listAgencesMAJNSI = listAgencesMAJNSI;
	}

	
	public BufferedWriter getBufWriter() {
		return bufWriter;
	}

	
	public void setBufWriter(BufferedWriter bufWriter) {
		this.bufWriter = bufWriter;
	}

	
	public List<Long> getListAgencesMAJNSIStrc() {
		return listAgencesMAJNSIStrc;
	}

	
	public void setListAgencesMAJNSIStrc(List<Long> listAgencesMAJNSIStrc) {
		this.listAgencesMAJNSIStrc = listAgencesMAJNSIStrc;
	}

	
	public TmpBatchVirNSI getTmpBatchVirNSI() {
		return tmpBatchVirNSI;
	}

	
	public void setTmpBatchVirNSI(TmpBatchVirNSI tmpBatchVirNSI) {
		this.tmpBatchVirNSI = tmpBatchVirNSI;
	}

	
	public BatchMAJNSIFrame getMainFrame() {
		return mainFrame;
	}

	
	public void setMainFrame(BatchMAJNSIFrame mainFrame) {
		this.mainFrame = mainFrame;
	}

	
	public BatchMAJNSIActelFrame getMainFrameActel() {
		return mainFrameActel;
	}

	
	public void setMainFrameActel(BatchMAJNSIActelFrame mainFrameActel) {
		this.mainFrameActel = mainFrameActel;
	}

	
	public List<MvtDevise> getMvtDevises() {
		return mvtDevises;
	}

	
	public void setMvtDevises(List<MvtDevise> mvtDevises) {
		this.mvtDevises = mvtDevises;
	}

	public OperationMoyPay getOperationMoyPay() {
		return operationMoyPay;
	}

	public void setOperationMoyPay(OperationMoyPay operationMoyPay) {
		this.operationMoyPay = operationMoyPay;
	}

	public AgencesMAJNSIVo getAgNonNsi() {
		return AgNonNsi;
	}

	public void setAgNonNsi(AgencesMAJNSIVo agNonNsi) {
		AgNonNsi = agNonNsi;
	}
	

}
