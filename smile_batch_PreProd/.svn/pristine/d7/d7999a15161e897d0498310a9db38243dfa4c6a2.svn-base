package com.bna.smile.model.domainecompensation.gestionrejet.model;

/**
 * @author BDOUR  Nabil
 * 
 */
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.bna.commun.model.ContratCpt;
import com.bna.commun.model.EffetRecu;
import com.bna.commun.model.EffetRecuTmp;
import com.bna.commun.model.OperationMoyPay;
import com.bna.commun.model.Papillon;
import com.bna.commun.model.Personne;
import com.bna.commun.model.Signataire;
import com.bna.smile.web.commun.model.ParamAgence;
import com.oxia.fwk.core.ValueObject;

public class  ReglementEffetVo extends ValueObject implements Serializable {

	private static final long serialVersionUID = 1L;
	private String structure ;
	private ContratCpt contratCpt;
	private EffetRecu effetRecu;
	private EffetRecuTmp effetRecuTmp;
	private ParamAgence paramAgence;
	
	private List<EffetRecu> listeEffetRecu=new ArrayList<EffetRecu>();
	private List<Signataire> listeSignataire=new ArrayList<Signataire>();
	private Papillon papillon;
	private Personne personne;
	private String libMotifRejet;
	private String motifRejet;
	private int modeOperation;
	private boolean provisionDiponible=false;
	private Date dateComptable;
	private Long mntCptVert ;
	
	private Long montantReglement=0L;
	private OperationMoyPay operationMoyPay;
	private List<OperationMoyPay> listeOmp=new ArrayList<OperationMoyPay>();
	private boolean editionPapillon=false;
	
	private Long sommeBlocage=0L;
	private Long codeOperation;
	private Long mntOperationOmp;
	private Long montantARegler;
	public ContratCpt getContratCpt() {
		return contratCpt;
	}
	
	public void setContratCpt(ContratCpt contratCpt) {
		this.contratCpt = contratCpt;
	}

	
	/**
	 * @return the effetRecu
	 */
	public EffetRecu getEffetRecu() {
		return effetRecu;
	}

	
	/**
	 * @param effetRecu the effetRecu to set
	 */
	public void setEffetRecu(EffetRecu effetRecu) {
		this.effetRecu = effetRecu;
	}

	
	

	
	/**
	 * @return the listeEffetRecu
	 */
	public List<EffetRecu> getListeEffetRecu() {
		return listeEffetRecu;
	}

	
	/**
	 * @param listeEffetRecu the listeEffetRecu to set
	 */
	public void setListeEffetRecu(List<EffetRecu> listeEffetRecu) {
		this.listeEffetRecu = listeEffetRecu;
	}

	
	/**
	 * @return the listeSignataire
	 */
	public List<Signataire> getListeSignataire() {
		return listeSignataire;
	}

	
	/**
	 * @param listeSignataire the listeSignataire to set
	 */
	public void setListeSignataire(List<Signataire> listeSignataire) {
		this.listeSignataire = listeSignataire;
	}

	
	/**
	 * @return the papillon
	 */
	public Papillon getPapillon() {
		return papillon;
	}

	
	/**
	 * @param papillon the papillon to set
	 */
	public void setPapillon(Papillon papillon) {
		this.papillon = papillon;
	}

	
	/**
	 * @return the personne
	 */
	public Personne getPersonne() {
		return personne;
	}

	
	/**
	 * @param personne the personne to set
	 */
	public void setPersonne(Personne personne) {
		this.personne = personne;
	}

	
	/**
	 * @return the libMotifRejet
	 */
	public String getLibMotifRejet() {
		return libMotifRejet;
	}

	
	/**
	 * @param libMotifRejet the libMotifRejet to set
	 */
	public void setLibMotifRejet(String libMotifRejet) {
		this.libMotifRejet = libMotifRejet;
	}

	
	/**
	 * @return the motifRejet
	 */
	public String getMotifRejet() {
		return motifRejet;
	}

	
	/**
	 * @param motifRejet the motifRejet to set
	 */
	public void setMotifRejet(String motifRejet) {
		this.motifRejet = motifRejet;
	}

	
	/**
	 * @return the modeOperation
	 */
	public int getModeOperation() {
		return modeOperation;
	}

	
	/**
	 * @param modeOperation the modeOperation to set
	 */
	public void setModeOperation(int modeOperation) {
		this.modeOperation = modeOperation;
	}

	
	/**
	 * @return the provisionDiponible
	 */
	public boolean isProvisionDiponible() {
		return provisionDiponible;
	}

	
	/**
	 * @param provisionDiponible the provisionDiponible to set
	 */
	public void setProvisionDiponible(boolean provisionDiponible) {
		this.provisionDiponible = provisionDiponible;
	}

	
	/**
	 * @return the dateComptable
	 */
	public Date getDateComptable() {
		return dateComptable;
	}

	
	/**
	 * @param dateComptable the dateComptable to set
	 */
	public void setDateComptable(Date dateComptable) {
		this.dateComptable = dateComptable;
	}

	
	
	
	/**
	 * @return the montantReglement
	 */
	public Long getMontantReglement() {
		return montantReglement;
	}

	
	/**
	 * @param montantReglement the montantReglement to set
	 */
	public void setMontantReglement(Long montantReglement) {
		this.montantReglement = montantReglement;
	}

	
	/**
	 * @return the operationMoyPay
	 */
	public OperationMoyPay getOperationMoyPay() {
		return operationMoyPay;
	}

	
	/**
	 * @param operationMoyPay the operationMoyPay to set
	 */
	public void setOperationMoyPay(OperationMoyPay operationMoyPay) {
		this.operationMoyPay = operationMoyPay;
	}

	
	/**
	 * @return the listeOmp
	 */
	public List<OperationMoyPay> getListeOmp() {
		return listeOmp;
	}

	
	/**
	 * @param listeOmp the listeOmp to set
	 */
	public void setListeOmp(List<OperationMoyPay> listeOmp) {
		this.listeOmp = listeOmp;
	}

	
	/**
	 * @return the editionPapillon
	 */
	public boolean isEditionPapillon() {
		return editionPapillon;
	}

	
	/**
	 * @param editionPapillon the editionPapillon to set
	 */
	public void setEditionPapillon(boolean editionPapillon) {
		this.editionPapillon = editionPapillon;
	}

	
	/**
	 * @return the sommeBlocage
	 */
	public Long getSommeBlocage() {
		return sommeBlocage;
	}

	
	/**
	 * @param sommeBlocage the sommeBlocage to set
	 */
	public void setSommeBlocage(Long sommeBlocage) {
		this.sommeBlocage = sommeBlocage;
	}

	
	/**
	 * @return the codeOperation
	 */
	public Long getCodeOperation() {
		return codeOperation;
	}

	
	/**
	 * @param codeOperation the codeOperation to set
	 */
	public void setCodeOperation(Long codeOperation) {
		this.codeOperation = codeOperation;
	}

	
	/**
	 * @return the mntOperationOmp
	 */
	public Long getMntOperationOmp() {
		return mntOperationOmp;
	}

	
	/**
	 * @param mntOperationOmp the mntOperationOmp to set
	 */
	public void setMntOperationOmp(Long mntOperationOmp) {
		this.mntOperationOmp = mntOperationOmp;
	}

	
	/**
	 * @return the montantARegler
	 */
	public Long getMontantARegler() {
		return montantARegler;
	}

	
	/**
	 * @param montantARegler the montantARegler to set
	 */
	public void setMontantARegler(Long montantARegler) {
		this.montantARegler = montantARegler;
	}

	/**
	 * @param structure the structure to set
	 */
	public void setStructure(String structure) {
		this.structure = structure;
	}

	/**
	 * @return the structure
	 */
	public String getStructure() {
		return structure;
	}

	public void setParamAgence(ParamAgence paramAgence) {
		this.paramAgence = paramAgence;
	}

	public ParamAgence getParamAgence() {
		return paramAgence;
	}

	public void setEffetRecuTmp(EffetRecuTmp effetRecuTmp) {
		this.effetRecuTmp = effetRecuTmp;
	}

	public EffetRecuTmp getEffetRecuTmp() {
		return effetRecuTmp;
	}

	public void setMntCptVert(Long mntCptVert) {
		this.mntCptVert = mntCptVert;
	}

	public Long getMntCptVert() {
		return mntCptVert;
	}

	
}
	
