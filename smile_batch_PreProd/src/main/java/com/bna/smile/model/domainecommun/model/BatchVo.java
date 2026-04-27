package com.bna.smile.model.domainecommun.model;

import java.io.Serializable;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.bna.commun.model.BatchMetier;
import com.bna.commun.model.TraceBatch;
import com.oxia.fwk.core.ValueObject;

public class BatchVo extends ValueObject implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private BatchMetier batchMetier;
	private Date dateBatch;
	private Long etatTrace;
	private List<TraceBatch> listeTraceBatch = new ArrayList<TraceBatch>();

	public BatchMetier getBatchMetier() {
		return batchMetier;
	}

	public void setBatchMetier(BatchMetier batchMetier) {
		this.batchMetier = batchMetier;
	}

	public Date getDateBatch() {
		return dateBatch;
	}

	public void setDateBatch(Date dateBatch) {
		this.dateBatch = dateBatch;
	}

	public Long getEtatTrace() {
		return etatTrace;
	}

	public void setEtatTrace(Long etatTrace) {
		this.etatTrace = etatTrace;
	}

	public List<TraceBatch> getListeTraceBatch() {
		return listeTraceBatch;
	}

	public void setListeTraceBatch(List<TraceBatch> listeTraceBatch) {
		this.listeTraceBatch = listeTraceBatch;
	}

	/** Constructeur */
	public BatchVo() {
	}

}
