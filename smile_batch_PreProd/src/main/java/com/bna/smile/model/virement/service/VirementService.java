package com.bna.smile.model.virement.service;

import com.bna.commun.util.ContextHandler;
import com.bna.smile.model.domainecontratcompte.souscriptioncontratcompte.traitement.GetCategorieContratTrt;
import com.bna.smile.model.domaineguichet.traitement.ReaffectationRejetNSITrt;
import com.bna.smile.model.virement.traitement.AlimentationCompteDepotTrt;
import com.bna.smile.model.virement.traitement.CreationCROAlimentationAPartirCompteVertTrt;
import com.bna.smile.model.virement.traitement.CreationCROAlimentationFaveurCompteDepotTrt;
import com.bna.smile.model.virement.traitement.CreationCROEnvoiRejetsVirementTrt;
import com.bna.smile.model.virement.traitement.CreationCROEnvoiVirementTrt;
import com.bna.smile.model.virement.traitement.CreationCROExecutionVirementMasseTrt;
import com.bna.smile.model.virement.traitement.CreationCROExecutionVirementPermanentTrt;
import com.bna.smile.model.virement.traitement.CreationCROExecutionVirementPonctuelTrt;
import com.bna.smile.model.virement.traitement.CreationCROExecutionVirementSuccessionTrt;
import com.bna.smile.model.virement.traitement.CreationCROPositionVirementCompteDevisesTrt;
import com.bna.smile.model.virement.traitement.CreationCROPositionVirementTrt;
import com.bna.smile.model.virement.traitement.CreationCROReaffectationRejetsVirementTrt;
import com.bna.smile.model.virement.traitement.CreationCROReceptionRejetsVirementParAgenceTrt;
import com.bna.smile.model.virement.traitement.CreationCROReceptionVirementParAgenceTrt;
import com.bna.smile.model.virement.traitement.CreationCROReceptionVirementSGMTTrt;
import com.bna.smile.model.virement.traitement.CreationCRORejetVirementTrt;
import com.bna.smile.model.virement.traitement.CreationFichierVirementTrt;
import com.bna.smile.model.virement.traitement.ExecutionDoVirDeviseTrt;
import com.bna.smile.model.virement.traitement.ExecutionDoVirMasseSalaireTrt;
import com.bna.smile.model.virement.traitement.ExecutionDoVirMasseTrt;
import com.bna.smile.model.virement.traitement.ExecutionDoVirPermanentTrt;
import com.bna.smile.model.virement.traitement.ExecutionDoVirPonctuelTrt;
import com.bna.smile.model.virement.traitement.ExecutionVirementCompteVertTrt;
import com.bna.smile.model.virement.traitement.GetNumLotAgenceTrt;
import com.bna.smile.model.virement.traitement.MiseAjourSoldeContratCptTrt;
import com.bna.smile.model.virement.traitement.ModifierDetailVirementTrt;
import com.bna.smile.model.virement.traitement.NonApprovisionDoVirTrt;
import com.bna.smile.model.virement.traitement.RejeterDetailVirementTrt;
import com.bna.smile.model.virement.traitement.RejeterGlobalVirementTrt;
import com.bna.smile.model.virement.traitement.TraitementVirPermanentTrt;
import com.bna.smile.model.virement.traitement.TraitementVirPonctuelMasseTrt;
import com.bna.smile.model.virement.traitement.VerfierContratCptTrt;
import com.bna.smile.model.virement.traitement.VerifierProvisionCompteVertTrt;
import com.bna.smile.model.virement.traitement.VerifierRibTrt;
import com.bna.smile.model.virement.traitement.VerifierValiditerRibBenifTrt;
import com.bna.smile.model.virement.traitement.VerifierValiditerRibDoTrt;
import com.bna.smile.model.virement.traitement.VirementAgenceTrt;
import com.bna.smile.model.virement.traitement.VirementsAecheanceTrt;
import com.oxia.fwk.beans.service.BasicService;
import com.oxia.fwk.context.Context;
import com.oxia.fwk.core.IValueObject;

public class VirementService extends BasicService implements IVirementService {

	private GetCategorieContratTrt getCategorieContratTrt;
	private VerifierRibTrt verifierRibTrt;
	private VerifierValiditerRibDoTrt verifierValiditerRibDoTrt;
	private VirementsAecheanceTrt virementsAecheanceTrt;
	private VirementAgenceTrt virementAgenceTrt;
	private ModifierDetailVirementTrt modifierDetailVirementTrt;
	private VerfierContratCptTrt verfierContratCptTrt;
	private RejeterGlobalVirementTrt rejeterGlobalVirementTrt;
	private TraitementVirPermanentTrt traitementVirPermanentTrt;
	private VerifierValiditerRibBenifTrt verifierValiditerRibBenifTrt;
	private RejeterDetailVirementTrt rejeterDetailVirementTrt;
	private NonApprovisionDoVirTrt nonApprovisionDoVirTrt;
	private VerifierProvisionCompteVertTrt verifierProvisionCompteVertTrt;
	private ExecutionDoVirPermanentTrt executionDoVirPermanentTrt;
	private MiseAjourSoldeContratCptTrt miseAjourSoldeContratCptTrt;
	private TraitementVirPonctuelMasseTrt traitementVirPonctuelMasseTrt;
	private ExecutionDoVirPonctuelTrt executionDoVirPonctuelTrt;
	private ExecutionDoVirMasseTrt executionDoVirMasseTrt;
	private CreationFichierVirementTrt creationFichierVirementTrt;
	private CreationCROPositionVirementTrt creationCROPositionVirementTrt;
	private CreationCROEnvoiVirementTrt creationCROEnvoiVirementTrt;
	private CreationCROReceptionVirementParAgenceTrt creationCROReceptionVirementParAgenceTrt;
	private CreationCROReaffectationRejetsVirementTrt creationCROReaffectationRejetsVirementTrt;
	private CreationCROEnvoiRejetsVirementTrt CreationCROEnvoiRejetsVirementTrt;
	private CreationCROReceptionRejetsVirementParAgenceTrt creationCROReceptionRejetsVirementParAgenceTrt;
	private CreationCROReceptionVirementSGMTTrt creationCROReceptionVirementSGMTTrt;
	private GetNumLotAgenceTrt getNumLotAgenceTrt;
	private CreationCRORejetVirementTrt creationCRORejetVirementTrt;
	private AlimentationCompteDepotTrt alimentationCompteDepotTrt;
	private CreationCROAlimentationAPartirCompteVertTrt creationCROAlimentationAPartirCompteVertTrt;
	private CreationCROAlimentationFaveurCompteDepotTrt creationCROAlimentationFaveurCompteDepotTrt;
	private CreationCROExecutionVirementPonctuelTrt creationCROExecutionVirementPonctuelTrt;
	private CreationCROExecutionVirementPermanentTrt creationCROExecutionVirementPermanentTrt;
	private CreationCROExecutionVirementMasseTrt creationCROExecutionVirementMasseTrt;
	private CreationCROExecutionVirementSuccessionTrt creationCROExecutionVirementSuccessionTrt;
	private CreationCROPositionVirementCompteDevisesTrt creationCROPositionVirementCompteDevisesTrt;
	private ExecutionVirementCompteVertTrt executionVirementCompteVertTrt;
	private ExecutionDoVirDeviseTrt executionDoVirDeviseTrt;
	private ExecutionDoVirMasseSalaireTrt executionDoVirMasseSalaireTrt;
	private ReaffectationRejetNSITrt reaffectationRejetNSITrt;
	public Context context = ContextHandler.getContext();

	public VirementService() {
	}

	public IValueObject getDetailCompte(IValueObject vo) {
		getCategorieContratTrt = new GetCategorieContratTrt();
		return (getCategorieContratTrt.exec(vo));
	}

	@Override
	public IValueObject verifierRib(IValueObject vo) {
		verifierRibTrt = new VerifierRibTrt();
		return (verifierRibTrt.exec(vo));

	}

	public IValueObject virementAgence(IValueObject vo) {
		virementAgenceTrt = new VirementAgenceTrt();
		return (virementAgenceTrt.exec(vo));
	}

	public IValueObject verifierValiditerRibDo(IValueObject vo) {
		verifierValiditerRibDoTrt = new VerifierValiditerRibDoTrt();
		return (verifierValiditerRibDoTrt.exec(vo));
	}

	public IValueObject virementsAecheance(IValueObject vo) {
		virementsAecheanceTrt = new VirementsAecheanceTrt();
		return (virementsAecheanceTrt.exec(vo));
	}

	@Override
	public IValueObject modifierDetailVirement(IValueObject vo) {
		modifierDetailVirementTrt = new ModifierDetailVirementTrt();
		return modifierDetailVirementTrt.exec(vo);
	}

	@Override
	public IValueObject verfierContratCpt(IValueObject vo) {
		verfierContratCptTrt = new VerfierContratCptTrt();
		return verfierContratCptTrt.exec(vo);
	}

	@Override
	public IValueObject rejeterGlobalVirement(IValueObject vo) {
		rejeterGlobalVirementTrt = new RejeterGlobalVirementTrt();
		return rejeterGlobalVirementTrt.exec(vo);
	}

	@Override
	public IValueObject traitementVirPermanent(IValueObject vo) {
		traitementVirPermanentTrt = new TraitementVirPermanentTrt();
		return (traitementVirPermanentTrt.exec(vo));
	}

	public IValueObject verifierValiditerRibBenif(IValueObject vo) {
		verifierValiditerRibBenifTrt = new VerifierValiditerRibBenifTrt();
		return verifierValiditerRibBenifTrt.exec(vo);
	}

	public IValueObject rejeterDetailVirement(IValueObject vo) {
		rejeterDetailVirementTrt = new RejeterDetailVirementTrt();
		return rejeterDetailVirementTrt.exec(vo);
	}

	public IValueObject nonApprovisionDoVir(IValueObject vo) {
		nonApprovisionDoVirTrt = new NonApprovisionDoVirTrt();
		return nonApprovisionDoVirTrt.exec(vo);
	}

	public IValueObject verifierProvisionCompteVertDoVir(IValueObject vo) {
		verifierProvisionCompteVertTrt = new VerifierProvisionCompteVertTrt();
		return verifierProvisionCompteVertTrt.exec(vo);
	}

	public IValueObject executionDoVirPermanent(IValueObject vo) {
		executionDoVirPermanentTrt = new ExecutionDoVirPermanentTrt();
		return executionDoVirPermanentTrt.exec(vo);
	}

	public IValueObject miseAjourSoldeContratCpt(IValueObject vo) {
		miseAjourSoldeContratCptTrt = new MiseAjourSoldeContratCptTrt();
		return miseAjourSoldeContratCptTrt.exec(vo);
	}

	@Override
	public IValueObject traitementVirPonctuelMasse(IValueObject vo) {
		traitementVirPonctuelMasseTrt = new TraitementVirPonctuelMasseTrt();
		return traitementVirPonctuelMasseTrt.exec(vo);
	}

	@Override
	public IValueObject executionDoVirPonctuel(IValueObject vo) {
		executionDoVirPonctuelTrt = new ExecutionDoVirPonctuelTrt();
		return (executionDoVirPonctuelTrt.exec(vo));
	}

	@Override
	public IValueObject executionDoVirMasse(IValueObject vo) {
		executionDoVirMasseTrt = new ExecutionDoVirMasseTrt();
		return (executionDoVirMasseTrt.exec(vo));
	}

	@Override
	public IValueObject creerFichierVirement(IValueObject vo) {
		creationFichierVirementTrt = new CreationFichierVirementTrt();

		return (creationFichierVirementTrt.exec(vo));
	}

	@Override
	public IValueObject createCroPositionVirement(IValueObject vo) {
		creationCROPositionVirementTrt = new CreationCROPositionVirementTrt();

		return (creationCROPositionVirementTrt.exec(vo));
	}

	@Override
	public IValueObject createCroEnvoiVirement(IValueObject vo) {
		creationCROEnvoiVirementTrt = new CreationCROEnvoiVirementTrt();
		return (creationCROEnvoiVirementTrt.exec(vo));
	}

	@Override
	public IValueObject createCroReceptionVirementParAgence(IValueObject vo) {
		creationCROReceptionVirementParAgenceTrt = new CreationCROReceptionVirementParAgenceTrt();
		return (creationCROReceptionVirementParAgenceTrt.exec(vo));
	}

	@Override
	public IValueObject createCroReaffectationRejetsVirement(IValueObject vo) {
		creationCROReaffectationRejetsVirementTrt = new CreationCROReaffectationRejetsVirementTrt();
		return (creationCROReaffectationRejetsVirementTrt.exec(vo));
	}

	@Override
	public IValueObject createCroEnvoiRejetsVirement(IValueObject vo) {
		CreationCROEnvoiRejetsVirementTrt = new CreationCROEnvoiRejetsVirementTrt();
		return (CreationCROEnvoiRejetsVirementTrt.exec(vo));
	}

	@Override
	public IValueObject creationCROReceptionRejetsVirementParAgence(IValueObject vo) {
		creationCROReceptionRejetsVirementParAgenceTrt = new CreationCROReceptionRejetsVirementParAgenceTrt();
		return (creationCROReceptionRejetsVirementParAgenceTrt.exec(vo));
	}

	@Override
	public IValueObject creationCROReceptionVirementSGMT(IValueObject vo) {
		creationCROReceptionVirementSGMTTrt = new CreationCROReceptionVirementSGMTTrt();
		return (creationCROReceptionVirementSGMTTrt.exec(vo));
	}

	@Override
	public IValueObject getNumLotAgence(IValueObject vo) {
		getNumLotAgenceTrt = new GetNumLotAgenceTrt();
		return (getNumLotAgenceTrt.exec(vo));
	}

	@Override
	public IValueObject createCroRejetVirement(IValueObject vo) {
		creationCRORejetVirementTrt = new CreationCRORejetVirementTrt();
		return (creationCRORejetVirementTrt.exec(vo));
	}

	@Override
	public IValueObject alimenterCompteDepot(IValueObject vo) {
		alimentationCompteDepotTrt = new AlimentationCompteDepotTrt();
		return (alimentationCompteDepotTrt.exec(vo));
	}

	@Override
	public IValueObject creationCROAlimentationAPartirCompteVert(IValueObject vo) {
		creationCROAlimentationAPartirCompteVertTrt = new CreationCROAlimentationAPartirCompteVertTrt();
		return (creationCROAlimentationAPartirCompteVertTrt.exec(vo));
	}

	@Override
	public IValueObject creationCROAlimentationFaveurCompteDepot(IValueObject vo) {
		creationCROAlimentationFaveurCompteDepotTrt = new CreationCROAlimentationFaveurCompteDepotTrt();
		return (creationCROAlimentationFaveurCompteDepotTrt.exec(vo));
	}

	@Override
	public IValueObject creationCROExecutionVirementPonctuel(IValueObject vo) {
		creationCROExecutionVirementPonctuelTrt = new CreationCROExecutionVirementPonctuelTrt();
		return (creationCROExecutionVirementPonctuelTrt.exec(vo));
	}

	@Override
	public IValueObject creationCROExecutionVirementMasse(IValueObject vo) {
		creationCROExecutionVirementMasseTrt = new CreationCROExecutionVirementMasseTrt();
		return creationCROExecutionVirementMasseTrt.exec(vo);
	}

	@Override
	public IValueObject creationCROExecutionVirementPermanent(IValueObject vo) {
		creationCROExecutionVirementPermanentTrt = new CreationCROExecutionVirementPermanentTrt();
		return (creationCROExecutionVirementPermanentTrt.exec(vo));
	}

	@Override
	public IValueObject creationCROExecutionVirementSuccession(IValueObject vo) {
		creationCROExecutionVirementSuccessionTrt = new CreationCROExecutionVirementSuccessionTrt();
		return (creationCROExecutionVirementSuccessionTrt.exec(vo));
	}

	@Override
	public IValueObject creationCROPositionVirementCompteDevises(IValueObject vo) {
		creationCROPositionVirementCompteDevisesTrt = new CreationCROPositionVirementCompteDevisesTrt();
		return (creationCROPositionVirementCompteDevisesTrt.exec(vo));
	}

	@Override
	public IValueObject executerVirementCompteVert(IValueObject vo) {
		executionVirementCompteVertTrt = new ExecutionVirementCompteVertTrt();
		return executionVirementCompteVertTrt.exec(vo);
	}

	@Override
	public IValueObject executerVirementDevise(IValueObject vo) {
		executionDoVirDeviseTrt = new ExecutionDoVirDeviseTrt();
		return executionDoVirDeviseTrt.exec(vo);
	}

	@Override
	public IValueObject executerDoVirMasseSalaire(IValueObject vo) {
		executionDoVirMasseSalaireTrt = new ExecutionDoVirMasseSalaireTrt();
		return executionDoVirMasseSalaireTrt.exec(vo);
	}

	@Override
	public IValueObject reaffecterRejetVirSiege(IValueObject vo) {
		reaffectationRejetNSITrt = new ReaffectationRejetNSITrt();
		return reaffectationRejetNSITrt.exec(vo);
	}
	
	// / ---------------------- Getter and Setter -------------------------- ///

	public void setContext(Context context) {
		this.context = context;
	}

	public Context getContext() {
		return context;
	}

	public GetCategorieContratTrt getGetCategorieContratTrt() {
		return getCategorieContratTrt;
	}

	public void setGetCategorieContratTrt(GetCategorieContratTrt getCategorieContratTrt) {
		this.getCategorieContratTrt = getCategorieContratTrt;
	}

	public VerifierRibTrt getVerifierRibTrt() {
		return verifierRibTrt;
	}

	public void setVerifierRibTrt(VerifierRibTrt verifierRibTrt) {
		this.verifierRibTrt = verifierRibTrt;
	}

	public VirementsAecheanceTrt getVirementsAecheanceTrt() {
		return virementsAecheanceTrt;
	}

	public void setVirementsAecheanceTrt(VirementsAecheanceTrt virementsAecheanceTrt) {
		this.virementsAecheanceTrt = virementsAecheanceTrt;
	}

	public VirementAgenceTrt getVirementAgenceTrt() {
		return virementAgenceTrt;
	}

	public void setVirementAgenceTrt(VirementAgenceTrt virementAgenceTrt) {
		this.virementAgenceTrt = virementAgenceTrt;
	}

	public ModifierDetailVirementTrt getModifierDetailVirementTrt() {
		return modifierDetailVirementTrt;
	}

	public void setModifierDetailVirementTrt(ModifierDetailVirementTrt modifierDetailVirementTrt) {
		this.modifierDetailVirementTrt = modifierDetailVirementTrt;
	}

	public VerfierContratCptTrt getVerfierContratCptTrt() {
		return verfierContratCptTrt;
	}

	public void setVerfierContratCptTrt(VerfierContratCptTrt verfierContratCptTrt) {
		this.verfierContratCptTrt = verfierContratCptTrt;
	}

	public RejeterGlobalVirementTrt getRejeterGlobalVirementTrt() {
		return rejeterGlobalVirementTrt;
	}

	public void setRejeterGlobalVirementTrt(RejeterGlobalVirementTrt rejeterGlobalVirementTrt) {
		this.rejeterGlobalVirementTrt = rejeterGlobalVirementTrt;
	}

	public VerifierValiditerRibDoTrt getVerifierValiditerRibDoTrt() {
		return verifierValiditerRibDoTrt;
	}

	public void setVerifierValiditerRibDoTrt(VerifierValiditerRibDoTrt verifierValiditerRibDoTrt) {
		this.verifierValiditerRibDoTrt = verifierValiditerRibDoTrt;
	}

	public TraitementVirPermanentTrt getTraitementVirPermanentTrt() {
		return traitementVirPermanentTrt;
	}

	public void setTraitementVirPermanentTrt(TraitementVirPermanentTrt traitementVirPermanentTrt) {
		this.traitementVirPermanentTrt = traitementVirPermanentTrt;
	}

	public VerifierValiditerRibBenifTrt getVerifierValiditerRibBenifTrt() {
		return verifierValiditerRibBenifTrt;
	}

	public void setVerifierValiditerRibBenifTrt(VerifierValiditerRibBenifTrt verifierValiditerRibBenifTrt) {
		this.verifierValiditerRibBenifTrt = verifierValiditerRibBenifTrt;
	}

	public RejeterDetailVirementTrt getRejeterDetailVirementTrt() {
		return rejeterDetailVirementTrt;
	}

	public void setRejeterDetailVirementTrt(RejeterDetailVirementTrt rejeterDetailVirementTrt) {
		this.rejeterDetailVirementTrt = rejeterDetailVirementTrt;
	}

	public NonApprovisionDoVirTrt getNonApprovisionDoVirTrt() {
		return nonApprovisionDoVirTrt;
	}

	public void setNonApprovisionDoVirTrt(NonApprovisionDoVirTrt nonApprovisionDoVirTrt) {
		this.nonApprovisionDoVirTrt = nonApprovisionDoVirTrt;
	}

	public VerifierProvisionCompteVertTrt getVerifierProvisionCompteVertTrt() {
		return verifierProvisionCompteVertTrt;
	}

	public void setVerifierProvisionCompteVertTrt(VerifierProvisionCompteVertTrt verifierProvisionCompteVertTrt) {
		this.verifierProvisionCompteVertTrt = verifierProvisionCompteVertTrt;
	}

	public ExecutionDoVirPermanentTrt getExecutionDoVirPermanentTrt() {
		return executionDoVirPermanentTrt;
	}

	public void setExecutionDoVirPermanentTrt(ExecutionDoVirPermanentTrt executionDoVirPermanentTrt) {
		this.executionDoVirPermanentTrt = executionDoVirPermanentTrt;
	}

	public TraitementVirPonctuelMasseTrt getTraitementVirPonctuelMasseTrt() {
		return traitementVirPonctuelMasseTrt;
	}

	public void setTraitementVirPonctuelMasseTrt(TraitementVirPonctuelMasseTrt traitementVirPonctuelMasseTrt) {
		this.traitementVirPonctuelMasseTrt = traitementVirPonctuelMasseTrt;
	}

	public void setExecutionDoVirPonctuelTrt(ExecutionDoVirPonctuelTrt executionDoVirPonctuelTrt) {
		this.executionDoVirPonctuelTrt = executionDoVirPonctuelTrt;
	}

	public ExecutionDoVirPonctuelTrt getExecutionDoVirPonctuelTrt() {
		return executionDoVirPonctuelTrt;
	}

	public void setExecutionDoVirMasseTrt(ExecutionDoVirMasseTrt executionDoVirMasseTrt) {
		this.executionDoVirMasseTrt = executionDoVirMasseTrt;
	}

	public ExecutionDoVirMasseTrt getExecutionDoVirMasseTrt() {
		return executionDoVirMasseTrt;
	}

	public void setCreationFichierVirementTrt(CreationFichierVirementTrt creationFichierVirementTrt) {
		this.creationFichierVirementTrt = creationFichierVirementTrt;
	}

	public CreationFichierVirementTrt getCreationFichierVirementTrt() {
		return creationFichierVirementTrt;
	}

	public void setCreationCROPositionVirementTrt(CreationCROPositionVirementTrt creationCROPositionVirementTrt) {
		this.creationCROPositionVirementTrt = creationCROPositionVirementTrt;
	}

	public CreationCROPositionVirementTrt getCreationCROPositionVirementTrt() {
		return creationCROPositionVirementTrt;
	}

	public void setCreationCROEnvoiVirementTrt(CreationCROEnvoiVirementTrt creationCROEnvoiVirementTrt) {
		this.creationCROEnvoiVirementTrt = creationCROEnvoiVirementTrt;
	}

	public CreationCROEnvoiVirementTrt getCreationCROEnvoiVirementTrt() {
		return creationCROEnvoiVirementTrt;
	}

	public void setCreationCROReceptionVirementParAgenceTrt(
			CreationCROReceptionVirementParAgenceTrt creationCROReceptionVirementParAgenceTrt) {
		this.creationCROReceptionVirementParAgenceTrt = creationCROReceptionVirementParAgenceTrt;
	}

	public CreationCROReceptionVirementParAgenceTrt getCreationCROReceptionVirementParAgenceTrt() {
		return creationCROReceptionVirementParAgenceTrt;
	}

	public void setCreationCROReaffectationRejetsVirementTrt(
			CreationCROReaffectationRejetsVirementTrt creationCROReaffectationRejetsVirementTrt) {
		this.creationCROReaffectationRejetsVirementTrt = creationCROReaffectationRejetsVirementTrt;
	}

	public CreationCROReaffectationRejetsVirementTrt getCreationCROReaffectationRejetsVirementTrt() {
		return creationCROReaffectationRejetsVirementTrt;
	}

	public void setCreationCROEnvoiRejetsVirementTrt(
			CreationCROEnvoiRejetsVirementTrt creationCROEnvoiRejetsVirementTrt) {
		CreationCROEnvoiRejetsVirementTrt = creationCROEnvoiRejetsVirementTrt;
	}

	public CreationCROEnvoiRejetsVirementTrt getCreationCROEnvoiRejetsVirementTrt() {
		return CreationCROEnvoiRejetsVirementTrt;
	}

	public void setCreationCROReceptionRejetsVirementParAgenceTrt(
			CreationCROReceptionRejetsVirementParAgenceTrt creationCROReceptionRejetsVirementParAgenceTrt) {
		this.creationCROReceptionRejetsVirementParAgenceTrt = creationCROReceptionRejetsVirementParAgenceTrt;
	}

	public CreationCROReceptionRejetsVirementParAgenceTrt getCreationCROReceptionRejetsVirementParAgenceTrt() {
		return creationCROReceptionRejetsVirementParAgenceTrt;
	}

	public void setCreationCROReceptionVirementSGMTTrt(
			CreationCROReceptionVirementSGMTTrt creationCROReceptionVirementSGMTTrt) {
		this.creationCROReceptionVirementSGMTTrt = creationCROReceptionVirementSGMTTrt;
	}

	public CreationCROReceptionVirementSGMTTrt getCreationCROReceptionVirementSGMTTrt() {
		return creationCROReceptionVirementSGMTTrt;
	}

	public void setGetNumLotAgenceTrt(GetNumLotAgenceTrt getNumLotAgenceTrt) {
		this.getNumLotAgenceTrt = getNumLotAgenceTrt;
	}

	public GetNumLotAgenceTrt getGetNumLotAgenceTrt() {
		return getNumLotAgenceTrt;
	}

	public void setCreationCRORejetVirementTrt(CreationCRORejetVirementTrt creationCRORejetVirementTrt) {
		this.creationCRORejetVirementTrt = creationCRORejetVirementTrt;
	}

	public CreationCRORejetVirementTrt getCreationCRORejetVirementTrt() {
		return creationCRORejetVirementTrt;
	}

	public void setAlimentationCompteDepotTrt(AlimentationCompteDepotTrt alimentationCompteDepotTrt) {
		this.alimentationCompteDepotTrt = alimentationCompteDepotTrt;
	}

	public AlimentationCompteDepotTrt getAlimentationCompteDepotTrt() {
		return alimentationCompteDepotTrt;
	}

	public void setCreationCROAlimentationAPartirCompteVertTrt(
			CreationCROAlimentationAPartirCompteVertTrt creationCROAlimentationAPartirCompteVertTrt) {
		this.creationCROAlimentationAPartirCompteVertTrt = creationCROAlimentationAPartirCompteVertTrt;
	}

	public CreationCROAlimentationAPartirCompteVertTrt getCreationCROAlimentationAPartirCompteVertTrt() {
		return creationCROAlimentationAPartirCompteVertTrt;
	}

	public void setCreationCROAlimentationFaveurCompteDepotTrt(
			CreationCROAlimentationFaveurCompteDepotTrt creationCROAlimentationFaveurCompteDepotTrt) {
		this.creationCROAlimentationFaveurCompteDepotTrt = creationCROAlimentationFaveurCompteDepotTrt;
	}

	public CreationCROAlimentationFaveurCompteDepotTrt getCreationCROAlimentationFaveurCompteDepotTrt() {
		return creationCROAlimentationFaveurCompteDepotTrt;
	}

	public void setCreationCROExecutionVirementPermanentTrt(
			CreationCROExecutionVirementPermanentTrt creationCROExecutionVirementPermanentTrt) {
		this.creationCROExecutionVirementPermanentTrt = creationCROExecutionVirementPermanentTrt;
	}

	public CreationCROExecutionVirementPermanentTrt getCreationCROExecutionVirementPermanentTrt() {
		return creationCROExecutionVirementPermanentTrt;
	}

	public void setCreationCROExecutionVirementMasseTrt(
			CreationCROExecutionVirementMasseTrt creationCROExecutionVirementMasseTrt) {
		this.creationCROExecutionVirementMasseTrt = creationCROExecutionVirementMasseTrt;
	}

	public CreationCROExecutionVirementMasseTrt getCreationCROExecutionVirementMasseTrt() {
		return creationCROExecutionVirementMasseTrt;
	}

	public void setCreationCROExecutionVirementSuccessionTrt(
			CreationCROExecutionVirementSuccessionTrt creationCROExecutionVirementSuccessionTrt) {
		this.creationCROExecutionVirementSuccessionTrt = creationCROExecutionVirementSuccessionTrt;
	}

	public CreationCROExecutionVirementSuccessionTrt getCreationCROExecutionVirementSuccessionTrt() {
		return creationCROExecutionVirementSuccessionTrt;
	}

	public MiseAjourSoldeContratCptTrt getMiseAjourSoldeContratCptTrt() {
		return miseAjourSoldeContratCptTrt;
	}

	public void setMiseAjourSoldeContratCptTrt(MiseAjourSoldeContratCptTrt miseAjourSoldeContratCptTrt) {
		this.miseAjourSoldeContratCptTrt = miseAjourSoldeContratCptTrt;
	}

	public CreationCROExecutionVirementPonctuelTrt getCreationCROExecutionVirementPonctuelTrt() {
		return creationCROExecutionVirementPonctuelTrt;
	}

	public void setCreationCROExecutionVirementPonctuelTrt(
			CreationCROExecutionVirementPonctuelTrt creationCROExecutionVirementPonctuelTrt) {
		this.creationCROExecutionVirementPonctuelTrt = creationCROExecutionVirementPonctuelTrt;
	}

	public CreationCROPositionVirementCompteDevisesTrt getCreationCROPositionVirementCompteDevisesTrt() {
		return creationCROPositionVirementCompteDevisesTrt;
	}

	public void setCreationCROPositionVirementCompteDevisesTrt(
			CreationCROPositionVirementCompteDevisesTrt creationCROPositionVirementCompteDevisesTrt) {
		this.creationCROPositionVirementCompteDevisesTrt = creationCROPositionVirementCompteDevisesTrt;
	}

	public void setExecutionVirementCompteVertTrt(ExecutionVirementCompteVertTrt executionVirementCompteVertTrt) {
		this.executionVirementCompteVertTrt = executionVirementCompteVertTrt;
	}

	public ExecutionVirementCompteVertTrt getExecutionVirementCompteVertTrt() {
		return executionVirementCompteVertTrt;
	}

	public void setExecutionDoVirDeviseTrt(ExecutionDoVirDeviseTrt executionDoVirDeviseTrt) {
		this.executionDoVirDeviseTrt = executionDoVirDeviseTrt;
	}

	public ExecutionDoVirDeviseTrt getExecutionDoVirDeviseTrt() {
		return executionDoVirDeviseTrt;
	}

	public void setExecutionDoVirMasseSalaireTrt(ExecutionDoVirMasseSalaireTrt executionDoVirMasseSalaireTrt) {
		this.executionDoVirMasseSalaireTrt = executionDoVirMasseSalaireTrt;
	}

	public ExecutionDoVirMasseSalaireTrt getExecutionDoVirMasseSalaireTrt() {
		return executionDoVirMasseSalaireTrt;
	}

	public ReaffectationRejetNSITrt getReaffectationRejetNSITrt() {
		return reaffectationRejetNSITrt;
	}

	public void setReaffectationRejetNSITrt(ReaffectationRejetNSITrt reaffectationRejetNSITrt) {
		this.reaffectationRejetNSITrt = reaffectationRejetNSITrt;
	}

}
