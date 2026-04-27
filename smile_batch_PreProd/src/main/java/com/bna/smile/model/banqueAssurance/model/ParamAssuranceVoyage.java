package com.bna.smile.model.banqueAssurance.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.bna.commun.model.Assurances;
import com.bna.commun.model.ContratAssuranceVoyage;
import com.bna.commun.model.DetailAssuranceVoyage;
import com.bna.commun.model.MouvementGuichet;
import com.bna.commun.model.Operation;
import com.bna.commun.model.OperationMoyPay;
import com.bna.commun.model.Structure;
import com.bna.commun.model.Tache;
import com.bna.commun.model.TarifAssuranceVoyage;
import com.bna.commun.model.TraceAssuranceVoyage;
import com.bna.smile.batch.test.RenouvellementAssuranceVoyageFrame;
import com.bna.smile.web.commun.model.ParamAgence;
import com.oxia.fwk.core.ValueObject;

public class ParamAssuranceVoyage extends ValueObject{
	/**
	 * 
	 */
	private static final long serialVersionUID = -5993743803083665286L;


	public ParamAssuranceVoyage() {
	}
	private Assurances assurances;
	private Long  age;
	private Long  montant;
	private Long  codTarif;
	private Long prmTotTassv;
	private TarifAssuranceVoyage tarifAssuranceVoyage;
	private TraceAssuranceVoyage traceAssuranceVoyage;
	private Long dureeGarantie;
	private OperationMoyPay operationMoyPay;
	private OperationMoyPay operationMoyPayAssureur1;
	private OperationMoyPay operationMoyPayAssureur2;
	
	
	private OperationMoyPay operationMoyPayCr;
	private OperationMoyPay operationMoyPayAssureurCr1;
	private OperationMoyPay operationMoyPayAssureurCr2;
	private OperationMoyPay operationMoyPayAssureurReglementCr1;
	private OperationMoyPay operationMoyPayAssureurReglementCr2;
	
	
	private ContratAssuranceVoyage contratAssuranceVoyage = new ContratAssuranceVoyage();
	private ContratAssuranceVoyage contratAssuranceVoyageNew = new ContratAssuranceVoyage();
	private Long numContratAssuranceVoyage;
	private Structure structure;
	private Date dateValeurVirementRecu;//date valeur virement reçu suite souscription assurance vie sur crédit
	private String codRefInter;   
	private ParamAgence paramAgence;
	private MouvementGuichet mouvementGuichet;
	private MouvementGuichet mouvementGuichetCr;
	private Tache tacheAnnulation;
	private Tache tacheCreation;
	private Operation operation1;
	private Operation operation2;
	private Operation operation3;
	private Operation operation4;
	private Long identifiantCaisse;
	private boolean isPersonnel;
	private RenouvellementAssuranceVoyageFrame mainFrame;
	private List<DetailAssuranceVoyage> detailsAssuranceVoyages = new ArrayList<DetailAssuranceVoyage>();

	
	public Long getIdentifiantCaisse() {
		return identifiantCaisse;
	}
	public void setIdentifiantCaisse(Long identifiantCaisse) {
		this.identifiantCaisse = identifiantCaisse;
	}
	public ParamAgence getParamAgence() {
		return paramAgence;
	}
	public void setParamAgence(ParamAgence paramAgence) {
		this.paramAgence = paramAgence;
	}
	public void setAssurances(Assurances assurances) {
		this.assurances = assurances;
	}
	public Assurances getAssurances() {
		return assurances;
	}
	public void setAge(Long age) {
		this.age = age;
	}
	public Long getAge() {
		return age;
	}
	public void setMontant(Long montant) {
		this.montant = montant;
	}
	public Long getMontant() {
		return montant;
	}
	public void setTarifAssuranceVoyage(TarifAssuranceVoyage tarifAssuranceVoyage) {
		this.tarifAssuranceVoyage = tarifAssuranceVoyage;
	}
	public TarifAssuranceVoyage getTarifAssuranceVoyage() {
		return tarifAssuranceVoyage;
	}
	public void setDureeGarantie(Long dureeGarantie) {
		this.dureeGarantie = dureeGarantie;
	}
	public Long getDureeGarantie() {
		return dureeGarantie;
	}
	public void setOperationMoyPay(OperationMoyPay operationMoyPay) {
		this.operationMoyPay = operationMoyPay;
	}
	public OperationMoyPay getOperationMoyPay() {
		return operationMoyPay;
	}
	public void setContratAssuranceVoyage(ContratAssuranceVoyage contratAssuranceVoyage) {
		this.contratAssuranceVoyage = contratAssuranceVoyage;
	}
	public ContratAssuranceVoyage getContratAssuranceVoyage() {
		return contratAssuranceVoyage;
	}
	public void setNumContratAssuranceVoyage(Long numContratAssuranceVoyage) {
		this.numContratAssuranceVoyage = numContratAssuranceVoyage;
	}
	public Long getNumContratAssuranceVoyage() {
		return numContratAssuranceVoyage;
	}
	public void setStructure(Structure structure) {
		this.structure = structure;
	}
	public Structure getStructure() {
		return structure;
	}
	public void setDateValeurVirementRecu(Date dateValeurVirementRecu) {
		this.dateValeurVirementRecu = dateValeurVirementRecu;
	}
	public Date getDateValeurVirementRecu() {
		return dateValeurVirementRecu;
	}
	public void setCodRefInter(String codRefInter) {
		this.codRefInter = codRefInter;
	}
	public String getCodRefInter() {
		return codRefInter;
	}
	public void setTraceAssuranceVoyage(TraceAssuranceVoyage traceAssuranceVoyage) {
		this.traceAssuranceVoyage = traceAssuranceVoyage;
	}
	public TraceAssuranceVoyage getTraceAssuranceVoyage() {
		return traceAssuranceVoyage;
	}

	public MouvementGuichet getMouvementGuichet() {
		return mouvementGuichet;
	}
	public void setMouvementGuichet(MouvementGuichet mouvementGuichet) {
		this.mouvementGuichet = mouvementGuichet;
	}
	public OperationMoyPay getOperationMoyPayAssureur1() {
		return operationMoyPayAssureur1;
	}
	public void setOperationMoyPayAssureur1(OperationMoyPay operationMoyPayAssureur1) {
		this.operationMoyPayAssureur1 = operationMoyPayAssureur1;
	}
	public OperationMoyPay getOperationMoyPayAssureur2() {
		return operationMoyPayAssureur2;
	}
	public void setOperationMoyPayAssureur2(OperationMoyPay operationMoyPayAssureur2) {
		this.operationMoyPayAssureur2 = operationMoyPayAssureur2;
	}
	public Operation getOperation1() {
		return operation1;
	}
	public void setOperation1(Operation operation1) {
		this.operation1 = operation1;
	}
	public Operation getOperation2() {
		return operation2;
	}
	public void setOperation2(Operation operation2) {
		this.operation2 = operation2;
	}
	public Operation getOperation3() {
		return operation3;
	}
	public void setOperation3(Operation operation3) {
		this.operation3 = operation3;
	}
	public ContratAssuranceVoyage getContratAssuranceVoyageNew() {
		return contratAssuranceVoyageNew;
	}
	public void setContratAssuranceVoyageNew(ContratAssuranceVoyage contratAssuranceVoyageNew) {
		this.contratAssuranceVoyageNew = contratAssuranceVoyageNew;
	}
	public OperationMoyPay getOperationMoyPayCr() {
		return operationMoyPayCr;
	}
	public void setOperationMoyPayCr(OperationMoyPay operationMoyPayCr) {
		this.operationMoyPayCr = operationMoyPayCr;
	}
	public OperationMoyPay getOperationMoyPayAssureurCr1() {
		return operationMoyPayAssureurCr1;
	}
	public void setOperationMoyPayAssureurCr1(OperationMoyPay operationMoyPayAssureurCr1) {
		this.operationMoyPayAssureurCr1 = operationMoyPayAssureurCr1;
	}
	public OperationMoyPay getOperationMoyPayAssureurCr2() {
		return operationMoyPayAssureurCr2;
	}
	public void setOperationMoyPayAssureurCr2(OperationMoyPay operationMoyPayAssureurCr2) {
		this.operationMoyPayAssureurCr2 = operationMoyPayAssureurCr2;
	}
	public Long getCodTarif() {
		return codTarif;
	}
	public void setCodTarif(Long codTarif) {
		this.codTarif = codTarif;
	}
	public OperationMoyPay getOperationMoyPayAssureurReglementCr1() {
		return operationMoyPayAssureurReglementCr1;
	}
	public void setOperationMoyPayAssureurReglementCr1(OperationMoyPay operationMoyPayAssureurReglementCr1) {
		this.operationMoyPayAssureurReglementCr1 = operationMoyPayAssureurReglementCr1;
	}
	public OperationMoyPay getOperationMoyPayAssureurReglementCr2() {
		return operationMoyPayAssureurReglementCr2;
	}
	public void setOperationMoyPayAssureurReglementCr2(OperationMoyPay operationMoyPayAssureurReglementCr2) {
		this.operationMoyPayAssureurReglementCr2 = operationMoyPayAssureurReglementCr2;
	}
	public Tache getTacheAnnulation() {
		return tacheAnnulation;
	}
	public void setTacheAnnulation(Tache tacheAnnulation) {
		this.tacheAnnulation = tacheAnnulation;
	}
	public Long getPrmTotTassv() {
		return prmTotTassv;
	}
	public void setPrmTotTassv(Long prmTotTassv) {
		this.prmTotTassv = prmTotTassv;
	}
	public Tache getTacheCreation() {
		return tacheCreation;
	}
	public void setTacheCreation(Tache tacheCreation) {
		this.tacheCreation = tacheCreation;
	}
	public MouvementGuichet getMouvementGuichetCr() {
		return mouvementGuichetCr;
	}
	public void setMouvementGuichetCr(MouvementGuichet mouvementGuichetCr) {
		this.mouvementGuichetCr = mouvementGuichetCr;
	}
	public boolean isPersonnel() {
		return isPersonnel;
	}
	public void setPersonnel(boolean isPersonnel) {
		this.isPersonnel = isPersonnel;
	}
	public Operation getOperation4() {
		return operation4;
	}
	public void setOperation4(Operation operation4) {
		this.operation4 = operation4;
	}
	
	public RenouvellementAssuranceVoyageFrame getMainFrame() {
		return mainFrame;
	}

	public void setMainFrame(RenouvellementAssuranceVoyageFrame mainFrame) {
		this.mainFrame = mainFrame;
	}
	public List<DetailAssuranceVoyage> getDetailsAssuranceVoyages() {
		return detailsAssuranceVoyages;
	}
	public void setDetailsAssuranceVoyages(List<DetailAssuranceVoyage> detailsAssuranceVoyages) {
		this.detailsAssuranceVoyages = detailsAssuranceVoyages;
	}
	


}
