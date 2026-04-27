package com.bna.smile.model.domainecontratcompte.moyensPaiement.service;


import com.bna.smile.model.domainecontratcompte.moyensPaiement.traitement.DelivranceChequiersTrt;
import com.bna.smile.model.domainecontratcompte.moyensPaiement.traitement.DestructionChequiersTrt;
import com.bna.smile.model.domainecontratcompte.moyensPaiement.traitement.GetChequiersTrt;
import com.bna.smile.model.domainecontratcompte.moyensPaiement.traitement.GetDetailChequierTrt;
import com.bna.smile.model.domainecontratcompte.moyensPaiement.traitement.GetDetailDemandeChequeTrt;
import com.bna.smile.model.domainecontratcompte.moyensPaiement.traitement.GetListDemandeChequePersonneTrt;
import com.bna.smile.model.domainecontratcompte.moyensPaiement.traitement.GetListDemandesChequesMandatPersonneTrt;
import com.bna.smile.model.domainecontratcompte.moyensPaiement.traitement.GetListDemandesChequesTrt;
import com.bna.smile.model.domainecontratcompte.moyensPaiement.traitement.GetListDemandeursChequesMandatPersonneTrt;
import com.bna.smile.model.domainecontratcompte.moyensPaiement.traitement.GetListDetailOperationChequierTrt;
import com.bna.smile.model.domainecontratcompte.moyensPaiement.traitement.InsertDemandeChequeMandatPersonneTrt;
import com.bna.smile.model.domainecontratcompte.moyensPaiement.traitement.InsertDemandeChequeTrt;
import com.bna.smile.model.domainecontratcompte.moyensPaiement.traitement.ReceptionChequiersTrt;
import com.bna.smile.model.domainecontratcompte.moyensPaiement.traitement.RestitutionChequiersTrt;
import com.bna.smile.model.domainecontratcompte.moyensPaiement.traitement.ValidationDemandeChequeTrt;
import com.oxia.fwk.beans.service.BasicService;
import com.oxia.fwk.core.IValueObject;


public class DemandeChequesService extends BasicService  {

private   GetListDemandeChequePersonneTrt getListDemandeChequePersonneTrt ;
private   InsertDemandeChequeTrt insertDemandeChequeTrt ;
private   InsertDemandeChequeMandatPersonneTrt insertDemandeChequeMandatPersonneTrt ;
private   GetListDemandesChequesTrt getListDemandesChequesTrt ;
private   GetDetailDemandeChequeTrt getDetailDemandeChequeTrt;
private   ValidationDemandeChequeTrt validationDemandeChequeTrt;
private   GetListDemandesChequesMandatPersonneTrt getListDemandesChequesMandatPersonneTrt ;
private   GetListDemandeursChequesMandatPersonneTrt getListDemandeursChequesMandatPersonneTrt ;
private   ReceptionChequiersTrt receptionChequiersTrt ;
private   DelivranceChequiersTrt delivranceChequiersTrt ;
private   DestructionChequiersTrt destructionChequiersTrt;
private   RestitutionChequiersTrt restitutionChequiersTrt ;
private   GetListDetailOperationChequierTrt getListDetailOperationChequierTrt ;
private   GetChequiersTrt getChequiersTrt ;
private   GetDetailChequierTrt getDetailChequierTrt ;
    
    public DemandeChequesService() {
    }

    /**
     * Methode permettant d'avoir les listes de demandes cheques d'un contrat donné
     * effectué par un demandeur donné.
     * @return ListesDemandesCheques
     */
    public

    IValueObject getListDemandeChequePersonne(IValueObject vo) {
      
        return (getListDemandeChequePersonneTrt.exec(vo));
    }

    /** méthode d'insertion  d'une demande de cheques 
     * et retourne le même objet inséré
     * @param   ValueObject : demandeCheque
     * @return  ValueObject : demandeCheque
     */
    public IValueObject insertDemandeCheque(IValueObject vo) {
      
        return (insertDemandeChequeTrt.exec(vo));
    }

    /** méthode d'insertion  d'une demande de cheques Mandat personne
     * et retourne le même objet inséré
     * @param   ValueObject : demandeChequeMandatPersonne
     * @return  ValueObject : demandeChequeMandatPersonne
     */
    public IValueObject insertDemandeChequeMandatPersonne(IValueObject vo) {
       
        return (insertDemandeChequeMandatPersonneTrt.exec(vo));
    }

    /**
     * Methode permettant d'avoir la liste de toute les demandes cheques 
     * @return ListesDemandesCheques
     */
    public IValueObject getListDemandesCheques(IValueObject vo) {
       
        return (getListDemandesChequesTrt.exec(vo));
    }

    /**
     * Methode permettant de retourner l'objet demande cheque         
     * @return DemandeCheque
     */
    public IValueObject getDetailDemandeCheque(IValueObject vo) {
       
        return (getDetailDemandeChequeTrt.exec(vo));
    }


    /**
     * Methode permettant de valider une demande cheque         
     * @return DemandeCheque
     */
    public IValueObject validerDemandeCheque(IValueObject vo) {
        ValidationDemandeChequeTrt validationDemandeChequeTrt = new ValidationDemandeChequeTrt(); 
        return (validationDemandeChequeTrt.exec(vo));
    }


    /**
     * Methode permettant d'avoir les listes de demandes cheques d'un contrat donné
     * effectué par un demandeur donné sur un mandat donnée.
     * @return ListesDemandesCheques
     */
    public IValueObject getListDemandesChequesMandatPersonne(IValueObject vo) {
      
        return (getListDemandesChequesMandatPersonneTrt.exec(vo));
    }

    /**
     * Methode permettant d'avoir la liste des demandeurs cheques mandat personne 
     * @return ListesDemandesCheques
     */
    public IValueObject getListDemandeursChequesMandatPersonne(IValueObject vo) {
      
        return (getListDemandeursChequesMandatPersonneTrt.exec(vo));
    }

    /**
     * Methode permettant la réception des carnets de chèques
     * @return demandeCheque
     */
    public IValueObject receptionChequiers(IValueObject vo) {
       
        return (receptionChequiersTrt.exec(vo));
    }

    /**
     * Methode permettant la délivrance des carnets de chèques
     * @return demandeCheque
     */
    public IValueObject delivranceChequiers(IValueObject vo) {
       
        return (delivranceChequiersTrt.exec(vo));
    }

    /**
     * Methode permettant la destruction des carnets de chèques
     * @return demandeCheque
     */
    public IValueObject destructionChequiers(IValueObject vo) {
      
        return (destructionChequiersTrt.exec(vo));
    }

    /**
     * Methode permettant la restitution des carnets de chèques
     * @return demandeCheque
     */
    public IValueObject restitutionChequiers(IValueObject vo) {
      
        return (restitutionChequiersTrt.exec(vo));
    }


    /**
     * Methode permettant de retourner la liste des détails opérations chéquiers 
     * @return ListesDemandesCheques
     */
    public IValueObject getListDetailOperationChequier(IValueObject vo) {
        
        return (getListDetailOperationChequierTrt.exec(vo));
    }

    /**
     * Methode permettant de retourner la liste des chéquiers 
     * @return ListesChequiers
     */
    public IValueObject getChequiers(IValueObject vo) {
     
        return (getChequiersTrt.exec(vo));
    }
    
    /**
     * Methode permettant de retourner le edtail d'un chequier
     * @return ListesChequiers
     */
    public IValueObject getDetailChequier(IValueObject vo) {
       
        return (getDetailChequierTrt.exec(vo));
    }


    public void setGetListDemandeChequePersonneTrt(GetListDemandeChequePersonneTrt getListDemandeChequePersonneTrt) {
        this.getListDemandeChequePersonneTrt = getListDemandeChequePersonneTrt;
    }

    public GetListDemandeChequePersonneTrt getGetListDemandeChequePersonneTrt() {
        return getListDemandeChequePersonneTrt;
    }

    public void setInsertDemandeChequeTrt(InsertDemandeChequeTrt insertDemandeChequeTrt) {
        this.insertDemandeChequeTrt = insertDemandeChequeTrt;
    }

    public InsertDemandeChequeTrt getInsertDemandeChequeTrt() {
        return insertDemandeChequeTrt;
    }

    public void setInsertDemandeChequeMandatPersonneTrt(InsertDemandeChequeMandatPersonneTrt insertDemandeChequeMandatPersonneTrt) {
        this.insertDemandeChequeMandatPersonneTrt = insertDemandeChequeMandatPersonneTrt;
    }

    public InsertDemandeChequeMandatPersonneTrt getInsertDemandeChequeMandatPersonneTrt() {
        return insertDemandeChequeMandatPersonneTrt;
    }

    public void setGetListDemandesChequesTrt(GetListDemandesChequesTrt getListDemandesChequesTrt) {
        this.getListDemandesChequesTrt = getListDemandesChequesTrt;
    }

    public GetListDemandesChequesTrt getGetListDemandesChequesTrt() {
        return getListDemandesChequesTrt;
    }

    public void setGetDetailDemandeChequeTrt(GetDetailDemandeChequeTrt getDetailDemandeChequeTrt) {
        this.getDetailDemandeChequeTrt = getDetailDemandeChequeTrt;
    }

    public GetDetailDemandeChequeTrt getGetDetailDemandeChequeTrt() {
        return getDetailDemandeChequeTrt;
    }

    public void setValidationDemandeChequeTrt(ValidationDemandeChequeTrt validationDemandeChequeTrt) {
        this.validationDemandeChequeTrt = validationDemandeChequeTrt;
    }

    public ValidationDemandeChequeTrt getValidationDemandeChequeTrt() {
        return validationDemandeChequeTrt;
    }

    public void setGetListDemandesChequesMandatPersonneTrt(GetListDemandesChequesMandatPersonneTrt getListDemandesChequesMandatPersonneTrt) {
        this.getListDemandesChequesMandatPersonneTrt = getListDemandesChequesMandatPersonneTrt;
    }

    public GetListDemandesChequesMandatPersonneTrt getGetListDemandesChequesMandatPersonneTrt() {
        return getListDemandesChequesMandatPersonneTrt;
    }

    public void setGetListDemandeursChequesMandatPersonneTrt(GetListDemandeursChequesMandatPersonneTrt getListDemandeursChequesMandatPersonneTrt) {
        this.getListDemandeursChequesMandatPersonneTrt = getListDemandeursChequesMandatPersonneTrt;
    }

    public GetListDemandeursChequesMandatPersonneTrt getGetListDemandeursChequesMandatPersonneTrt() {
        return getListDemandeursChequesMandatPersonneTrt;
    }

    public void setReceptionChequiersTrt(ReceptionChequiersTrt receptionChequiersTrt) {
        this.receptionChequiersTrt = receptionChequiersTrt;
    }

    public ReceptionChequiersTrt getReceptionChequiersTrt() {
        return receptionChequiersTrt;
    }

    public void setDelivranceChequiersTrt(DelivranceChequiersTrt delivranceChequiersTrt) {
        this.delivranceChequiersTrt = delivranceChequiersTrt;
    }

    public DelivranceChequiersTrt getDelivranceChequiersTrt() {
        return delivranceChequiersTrt;
    }

    public void setDestructionChequiersTrt(DestructionChequiersTrt destructionChequiersTrt) {
        this.destructionChequiersTrt = destructionChequiersTrt;
    }

    public DestructionChequiersTrt getDestructionChequiersTrt() {
        return destructionChequiersTrt;
    }

    public void setRestitutionChequiersTrt(RestitutionChequiersTrt restitutionChequiersTrt) {
        this.restitutionChequiersTrt = restitutionChequiersTrt;
    }

    public RestitutionChequiersTrt getRestitutionChequiersTrt() {
        return restitutionChequiersTrt;
    }

    public void setGetListDetailOperationChequierTrt(GetListDetailOperationChequierTrt getListDetailOperationChequierTrt) {
        this.getListDetailOperationChequierTrt = getListDetailOperationChequierTrt;
    }

    public GetListDetailOperationChequierTrt getGetListDetailOperationChequierTrt() {
        return getListDetailOperationChequierTrt;
    }

    public void setGetChequiersTrt(GetChequiersTrt getChequiersTrt) {
        this.getChequiersTrt = getChequiersTrt;
    }

    public GetChequiersTrt getGetChequiersTrt() {
        return getChequiersTrt;
    }

    public void setGetDetailChequierTrt(GetDetailChequierTrt getDetailChequierTrt) {
        this.getDetailChequierTrt = getDetailChequierTrt;
    }

    public GetDetailChequierTrt getGetDetailChequierTrt() {
        return getDetailChequierTrt;
    }
}
