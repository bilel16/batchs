package com.bna.smile.model.domaineguichet.model;

/**
 * 
 * @author YAZIDI Mohamed
 * @since 19/09/2014
 */
import com.bna.commun.model.JourneeStructureBatch;
import com.bna.commun.model.JourneeStructureBatchId;
import com.oxia.fwk.core.ValueObject;

public class AgencesMAJNSIVo extends ValueObject {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JourneeStructureBatch journeeStructureBatch = new JourneeStructureBatch();
	private int nbTotalOperations = 0;
	private Long sommeOp = 0L;
	private Long sommeOpDebit = 0L;
	private int nbOperationsPosi = 0;
	private int nbOperationsInter = 0;
	private int nbOperationsRej = 0;
	private Long oldCodStatJsb = 0L;

	
	public AgencesMAJNSIVo() {
		}
	public AgencesMAJNSIVo(Long codStrc) {
		JourneeStructureBatchId journeeStructureBatchId=new JourneeStructureBatchId();
		journeeStructureBatchId.setCodStrcStrc(codStrc);
		journeeStructureBatch.setJourneeStructureBatchId(journeeStructureBatchId);
	}
//	@Override
//	public boolean equals(Object o) {
//		AgencesMAJNSIVo agencesMAJNSIVo=((AgencesMAJNSIVo)o);
//		Long codStrc=agencesMAJNSIVo.getJourneeStructureBatch().getJourneeStructureBatchId().getCodStrcStrc();
//		if ((agencesMAJNSIVo.getJourneeStructureBatch().getJourneeStructureBatchId() != null)
//				&&(agencesMAJNSIVo.getJourneeStructureBatch().getJourneeStructureBatchId().get_codStrcStrc().equals(codStrc)) 
//				&& ((agencesMAJNSIVo.getJourneeStructureBatch().getCodStatJsb() == null ) || agencesMAJNSIVo.getJourneeStructureBatch().getCodStatJsb().equals(0L) ) )
//			return true;
//		else
//			return false;
//	}

	public JourneeStructureBatch getJourneeStructureBatch() {
		return journeeStructureBatch;
	}

	public void setJourneeStructureBatch(JourneeStructureBatch journeeStructureBatch) {
		this.journeeStructureBatch = journeeStructureBatch;
	}

	public int getNbTotalOperations() {
		return nbTotalOperations;
	}

	public void setNbTotalOperations(int nbTotalOperations) {
		this.nbTotalOperations = nbTotalOperations;
	}

	public Long getSommeOp() {
		return sommeOp;
	}

	public void setSommeOp(Long sommeOp) {
		this.sommeOp = sommeOp;
	}

	public int getNbOperationsPosi() {
		return nbOperationsPosi;
	}

	public void setNbOperationsPosi(int nbOperationsPosi) {
		this.nbOperationsPosi = nbOperationsPosi;
	}
	
	public Long getOldCodStatJsb() {
		return oldCodStatJsb;
	}
	
	public void setOldCodStatJsb(Long oldCodStatJsb) {
		this.oldCodStatJsb = oldCodStatJsb;
	}
	
	public int getNbOperationsInter() {
		return nbOperationsInter;
	}
	
	public void setNbOperationsInter(int nbOperationsInter) {
		this.nbOperationsInter = nbOperationsInter;
	}
	
	public int getNbOperationsRej() {
		return nbOperationsRej;
	}
	
	public void setNbOperationsRej(int nbOperationsRej) {
		this.nbOperationsRej = nbOperationsRej;
	}
	
	public Long getSommeOpDebit() {
		return sommeOpDebit;
	}
	
	public void setSommeOpDebit(Long sommeOpDebit) {
		this.sommeOpDebit = sommeOpDebit;
	}
	

}
