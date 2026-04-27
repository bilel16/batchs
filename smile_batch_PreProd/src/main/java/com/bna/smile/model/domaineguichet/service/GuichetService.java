package com.bna.smile.model.domaineguichet.service;

import com.bna.smile.model.domaineguichet.traitement.AjoutOperationEpargneTrt;
import com.bna.smile.model.domaineguichet.traitement.AjoutVersementMisAdispositionTrt;
import com.bna.smile.model.domaineguichet.traitement.GetListMontantMADTrt;
import com.bna.smile.model.domaineguichet.traitement.GetListValidationVersementTrt;
import com.bna.smile.model.domaineguichet.traitement.GetMiseAdispositionByPrimaryKeyTrt;
import com.bna.smile.model.domaineguichet.traitement.GetMontantMADByIdPersTrt;
import com.bna.smile.model.domaineguichet.traitement.GetMontantMADByIdTrt;
import com.bna.smile.model.domaineguichet.traitement.GetOperationMoyPayByIDTrt;
import com.bna.smile.model.domaineguichet.traitement.GetOperationMoyPayTrt;
import com.bna.smile.model.domaineguichet.traitement.GetProvisionTrt;
import com.bna.smile.model.domaineguichet.traitement.InsertMontantMADTrt;
import com.bna.smile.model.domaineguichet.traitement.InsertOpMoyPayTrt;
import com.bna.smile.model.domaineguichet.traitement.MAJAutLigneTrt;
import com.bna.smile.model.domaineguichet.traitement.MAJAutTresLigneTrt;
import com.bna.smile.model.domaineguichet.traitement.MAJNSIActelFileCopyTrt;
import com.bna.smile.model.domaineguichet.traitement.MAJNSIActelFromOmpControlMTrt;
import com.bna.smile.model.domaineguichet.traitement.MAJNSIActelFromOmpTrt;
import com.bna.smile.model.domaineguichet.traitement.MAJNSIFileCopyControlMTrt;
import com.bna.smile.model.domaineguichet.traitement.MAJNSIFileCopyTrt;
import com.bna.smile.model.domaineguichet.traitement.MAJNSILigneTrt;
import com.bna.smile.model.domaineguichet.traitement.PecVersementTrt;
import com.bna.smile.model.domaineguichet.traitement.UpdateMontantMADTrt;
import com.bna.smile.model.domaineguichet.traitement.ValidationMiseAdispositionTrt;
import com.bna.smile.model.domaineguichet.traitement.ValidationVersementMemeAgenceTrt;
import com.bna.smile.model.domaineguichet.traitement.VerifInterditChequierTrt;
import com.bna.smile.model.domaineguichet.traitement.VerifOppositionMoyPayTrt;
import com.oxia.fwk.beans.service.BasicService;
import com.oxia.fwk.core.IValueObject;
import com.oxia.fwk.core.ValueObject;

public class GuichetService extends BasicService {

	public GuichetService() {
	}

	public IValueObject MAJNSIFileCopy(IValueObject vo) {
		MAJNSIFileCopyTrt mAJNSIFileCopyTrt = new MAJNSIFileCopyTrt();
		return (mAJNSIFileCopyTrt.exec(vo));
	}

	public IValueObject MAJNSIFileCopyControlM(IValueObject vo) {
		MAJNSIFileCopyControlMTrt mAJNSIFileCopyTrt = new MAJNSIFileCopyControlMTrt();
		return (mAJNSIFileCopyTrt.exec(vo));
	}
	
	public IValueObject MAJNSIActelFileCopy(IValueObject vo) {
		MAJNSIActelFileCopyTrt mAJNSIFileCopyTrt = new MAJNSIActelFileCopyTrt();
		return (mAJNSIFileCopyTrt.exec(vo));
	}

	public IValueObject mAJNSIActelFromOmpTrt(IValueObject vo) {
		MAJNSIActelFromOmpTrt mAJNSIActelFromOmpTrt = new MAJNSIActelFromOmpTrt();
		return (mAJNSIActelFromOmpTrt.exec(vo));
	}

	public IValueObject MAJNSILine(IValueObject vo) {
		MAJNSILigneTrt mAJNSILigneTrt = new MAJNSILigneTrt();
		return (mAJNSILigneTrt.exec(vo));
	}

	public IValueObject MAJNSIAut(IValueObject vo) {
		MAJAutLigneTrt mAJNSILigneTrt = new MAJAutLigneTrt();
		return (mAJNSILigneTrt.exec(vo));
	}

	public IValueObject MAJNSIAutTres(IValueObject vo) {
		MAJAutTresLigneTrt mAJNSITresLigneTrt = new MAJAutTresLigneTrt();
		return (mAJNSITresLigneTrt.exec(vo));
	}

	/**
	 * méthode qui permet de calculer la provision d'un contrat donné
	 * 
	 * @param IValueObject
	 *            : ContratCpt
	 * @return IValueObject : PrimitiveVO
	 * @author BOUSSEN Youssef
	 */
	public IValueObject getProvision(IValueObject vo) {
		GetProvisionTrt getProvisionTrt = new GetProvisionTrt();
		return (getProvisionTrt.exec(vo));
	}

	/**
	 * méthode qui permet de vérifier si un moyen de payement est en opposition
	 * ou pas
	 * 
	 * @param IValueObject
	 *            : OppositionMoyenPaiementId
	 * @return IValueObject : PrimitiveVO
	 * @author BOUSSEN Youssef
	 */
	public IValueObject verifOppositionMoyPay(IValueObject vo) {
		VerifOppositionMoyPayTrt verifOppositionMoyPayTrt = new VerifOppositionMoyPayTrt();
		return (verifOppositionMoyPayTrt.exec(vo));
	}

	/**
	 * Methode permettant l'insertion de la MAD
	 * 
	 * @param vo
	 *            : MontantMiseDiposition
	 * @return : MontantMiseDiposition
	 * @author BOUSSEN Youssef
	 */
	public IValueObject InsertMontantMAD(IValueObject vo) {
		InsertMontantMADTrt insertMontantMADTrt = new InsertMontantMADTrt();
		return (insertMontantMADTrt.exec(vo));
	}

	/**
	 * Methode permettant de rechercher les MAD d'une personne donnée
	 * 
	 * @param vo
	 *            : PersonneStrc
	 * @return : Listes
	 * @author BOUSSEN Youssef
	 */
	public IValueObject GetMontantMADByIdPers(IValueObject vo) {
		GetMontantMADByIdPersTrt getMontantMADByIdPersTrt = new GetMontantMADByIdPersTrt();
		return (getMontantMADByIdPersTrt.exec(vo));
	}

	/**
	 * Methode permettant de rechercher les MAD
	 * 
	 * @param vo
	 *            : PrimitiveVO
	 * @return : Listes
	 * @author BOUSSEN Youssef
	 */
	public IValueObject GetMontantMADById(IValueObject vo) {
		GetMontantMADByIdTrt getMontantMADByIdTrt = new GetMontantMADByIdTrt();
		return (getMontantMADByIdTrt.exec(vo));
	}

	/**
	 * méthode permet la prise en charges des versements
	 * 
	 * @param IValueObject
	 *            : OperationMoyPay
	 * @return IValueObject : OperationMoyPay
	 * 
	 */
	public IValueObject pecVersement(IValueObject vo) {
		PecVersementTrt pecVersementTrt = new PecVersementTrt();
		return (pecVersementTrt.exec(vo));
	}

	/**
	 * méthode permet d'extraire les versment à valider pour la meme ou autres
	 * agences
	 * 
	 * @param ValueObject
	 *            : ListVersementVo
	 * @return ValueObject : ListVersementVo
	 */
	public ValueObject getListVersment(ValueObject vo) {
		GetListValidationVersementTrt getListValidationVersementTrt = new GetListValidationVersementTrt();
		return (getListValidationVersementTrt.execute(vo));
	}

	/**
	 * méthode permet de valider un virement
	 * 
	 * @param ValueObject
	 *            : OperationMoyPay
	 * @return ValueObject : OperationMoyPay
	 */
	public IValueObject validerVersementMemeAgence(IValueObject vo) {
		ValidationVersementMemeAgenceTrt validation = new ValidationVersementMemeAgenceTrt();
		return (validation.exec(vo));
	}

	/**
	 * Methode permettant la Mise à jour de la MAD
	 * 
	 * @param vo
	 *            : MontantMiseDiposition
	 * @return : MontantMiseDiposition
	 * @author BOUSSEN Youssef
	 */
	public IValueObject UpdateMontantMAD(IValueObject vo) {
		UpdateMontantMADTrt updateMontantMAD = new UpdateMontantMADTrt();
		return (updateMontantMAD.exec(vo));
	}

	/**
	 * Methode permet trouver les les OperationsMoyenPay par agence (en attente
	 * ou prévalidé)
	 * 
	 * @param vo
	 *            : PrimitiveVO (cod agence , etat OperationMoyPay)
	 * @return : Listes : liste des OperationsMoyenPay
	 * @author BOUSSEN Youssef
	 */
	public IValueObject GetOperationMoyPay(IValueObject vo) {
		GetOperationMoyPayTrt getOperationMoyPayTrt = new GetOperationMoyPayTrt();
		return (getOperationMoyPayTrt.exec(vo));
	}

	/**
	 * Methode permet trouver une OperationsMoyenPay par son identifiant
	 * 
	 * @param vo
	 *            : PrimitiveVO (N° OperationMoyPay)
	 * @return : OperationMoyPay : OperationsMoyenPay
	 * @author BOUSSEN Youssef
	 */
	public IValueObject GetOperationMoyPayByID(IValueObject vo) {
		GetOperationMoyPayByIDTrt getOperationMoyPayByIDTrt = new GetOperationMoyPayByIDTrt();
		return (getOperationMoyPayByIDTrt.exec(vo));
	}

	/**
	 * méthode qui permet l'insertion d'une opération d'épargne
	 * 
	 * @param ValueObject
	 *            : OperationEpargnes
	 * @return ValueObject : OperationEpargnes
	 */
	public IValueObject ajoutOperationEpargne(IValueObject vo) {
		AjoutOperationEpargneTrt ajoutOperationEpargneTrt = new AjoutOperationEpargneTrt();
		return (ajoutOperationEpargneTrt.exec(vo));
	}

	/**
	 * méthode qui permet l'extraction des mise à disposition selon des critères
	 * 
	 * @param ValueObject
	 *            : ListMiseAdispositionVo
	 * @return ValueObject : ListMiseAdispositionVo
	 */
	public IValueObject getListMontantMAD(IValueObject vo) {
		GetListMontantMADTrt getListMontantMADTrt = new GetListMontantMADTrt();
		return (getListMontantMADTrt.exec(vo));
	}

	/**
	 * méthode qui permet la validation de la mise à disposition
	 * 
	 * @param ValueObject
	 *            : MontantMiseDiposition
	 * @return ValueObject : MontantMiseDiposition
	 * @author MDIMAGH med Lassaad
	 */
	public IValueObject validationMiseAdisposition(IValueObject vo) {
		ValidationMiseAdispositionTrt validationMiseAdispositionTrt = new ValidationMiseAdispositionTrt();
		return (validationMiseAdispositionTrt.exec(vo));
	}

	/**
	 * méthode qui permet de charcher une mise à disposition par sa clé primaire
	 * 
	 * @param ValueObject
	 *            : MontantMiseDiposition
	 * @return ValueObject : MontantMiseDiposition
	 * @author Mdimagh Med Lassaad
	 * @since 26/11/2007
	 */
	public IValueObject getMiseAdispositionByPrimaryKey(IValueObject vo) {
		GetMiseAdispositionByPrimaryKeyTrt getMiseAdispositionByPrimaryKeyTrt = new GetMiseAdispositionByPrimaryKeyTrt();
		return (getMiseAdispositionByPrimaryKeyTrt.exec(vo));
	}

	/**
	 * méthode qui permet l'insertion d'un versment mis à dispositon
	 * 
	 * @param ValueObject
	 *            : MontantMiseDiposition
	 * @return ValueObject : MontantMiseDiposition
	 * @author Mdimagh Med Lassaad
	 * @since 03/12/2007
	 */
	public IValueObject ajoutVersementMisAdisposition(IValueObject vo) {
		AjoutVersementMisAdispositionTrt versementMisAdispositionTrt = new AjoutVersementMisAdispositionTrt();
		return (versementMisAdispositionTrt.exec(vo));
	}

	/**
	 * méthode qui permet l'insertion de L'op moy de pay
	 * 
	 * @param ValueObject
	 *            : OperationMoyPay
	 * @return ValueObject : OperationMoyPay
	 * @author Mdimagh Med Lassaad
	 * @since 03/12/2007
	 */
	public IValueObject ajoutOpMoyPay(IValueObject vo) {
		InsertOpMoyPayTrt insertOpMoyPayTrt = new InsertOpMoyPayTrt();
		return (insertOpMoyPayTrt.exec(vo));
	}

	/**
	 * méthode qui permet la vérification d'un interdit de chequier
	 * 
	 * @param ValueObject
	 *            : PersonneStrc
	 * @return ValueObject : PrimitiveVo
	 * @author El arbi hassine
	 * @since 04/02/2008
	 */
	public IValueObject verifierInterditChequier(ValueObject vo) {
		VerifInterditChequierTrt verifInterditChequierTrt = new VerifInterditChequierTrt();
		return (verifInterditChequierTrt.execute(vo));
	}
	
	public IValueObject mAJNSIActelFromOmpTrtControlM(IValueObject vo) {
		MAJNSIActelFromOmpControlMTrt mAJNSIActelFromOmpControlMTrt = new MAJNSIActelFromOmpControlMTrt();
		return (mAJNSIActelFromOmpControlMTrt.exec(vo));
	}

}
