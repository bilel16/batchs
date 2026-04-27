package com.bna.smile.model.domainecommun.service;

import com.bna.commun.model.Activite;
import com.bna.commun.model.CodePostal;
import com.bna.commun.model.NomencElemtCondition;
import com.bna.commun.model.Operation;

import com.bna.commun.model.Pays;
import com.bna.commun.model.Profession;
import com.bna.commun.model.TypeModification;
import com.bna.commun.model.TypePiece;
import com.bna.smile.model.domainecommun.traitement.GetActiviteByIdTrt;
import com.bna.smile.model.domainecommun.traitement.GetCatSocProfTrt;
import com.bna.smile.model.domainecommun.traitement.GetCategoriePersonneTrt;
import com.bna.smile.model.domainecommun.traitement.GetCodePostalTrt;
import com.bna.smile.model.domainecommun.traitement.GetCommissionTrt;
import com.bna.smile.model.domainecommun.traitement.GetDeviseTrt;

import com.bna.smile.model.domainecommun.traitement.GetEmployeurByIdTrt;
import com.bna.smile.model.domainecommun.traitement.GetFormeJuridiqueTrt;
import com.bna.smile.model.domainecommun.traitement.GetGouvernoratTrt;


import com.bna.smile.model.domainecommun.traitement.GetGroupeTrt;
import com.bna.smile.model.domainecommun.traitement.GetListCategoriesPersonneTrt;
import com.bna.smile.model.domainecommun.traitement.GetListeClassSegmentTrt;
import com.bna.smile.model.domainecommun.traitement.GetListeSegmentTrt;

import com.bna.smile.model.domainecommun.traitement.GetListeSousClassSegmentTrt;

import com.bna.smile.model.domainecommun.traitement.GetNiveauInstructionTrt;
import com.bna.smile.model.domainecommun.traitement.GetNomencElemCondTrt;
import com.bna.smile.model.domainecommun.traitement.GetOperationTrt;

import com.bna.smile.model.domainecommun.traitement.GetPaysTrt;

import com.bna.smile.model.domainecommun.traitement.GetPieceAnnexeByIdTrt;
import com.bna.smile.model.domainecommun.traitement.GetProfessionByIdTrt;
import com.bna.smile.model.domainecommun.traitement.GetRegimeMatrimonialTrt;
import com.bna.smile.model.domainecommun.traitement.GetSegmentTrt;
import com.bna.smile.model.domainecommun.traitement.GetStructureTrt;
import com.bna.smile.model.domainecommun.traitement.GetTypeModificationTrt;

import com.bna.smile.model.domainecommun.traitement.GetTypePieceTrt;

import com.oxia.fwk.beans.service.BasicService;
import com.oxia.fwk.core.IValueObject;
import com.oxia.fwk.core.ValueObject;

/**
 * Classe qui contient les services de la nomenclature
 * @author Lassaad Mdimagh
 * @version 1.0.0 17/05/2007
 */
public class NomenclatureService extends BasicService{
    public NomenclatureService() {
    }
    
    private  GetOperationTrt getOperationTrt;
    private  GetPaysTrt getPaysTrt;
    private  GetCodePostalTrt getCodePostalTrt;
    private  GetTypeModificationTrt getTypeModificationTrt;
    private  GetProfessionByIdTrt getProfessionByIdTrt;
    private  GetActiviteByIdTrt getActiviteByIdTrt;
    private  GetTypePieceTrt getTypePieceTrt ;
    private  GetGouvernoratTrt getGouvernoratTrt;
    private  GetSegmentTrt getSegmentTrt ;
    private  GetListeClassSegmentTrt getListeClassSegmentTrt;
    private  GetListeSousClassSegmentTrt getListeSousClassSegmentTrt;
    private  GetListeSegmentTrt getListeSegmentTrt ;
    private  GetRegimeMatrimonialTrt getRegimeMatrimonialTrt ;
    private  GetNiveauInstructionTrt getNiveauInstructionTrt ;
    private  GetFormeJuridiqueTrt getFomeJuridiqueTrt;
    private  GetCategoriePersonneTrt getCategoriePersonneTrt ;
    private  GetGroupeTrt getGroupeTrt;
    private  GetCatSocProfTrt getCatSocProfTrt;
    private  GetNomencElemCondTrt getNomencElemCondTrt ;
    private  GetCommissionTrt getCommissionTrt;
    private  GetDeviseTrt getDeviseTrt;
    private  GetPieceAnnexeByIdTrt getPieceAnnexeById ;
    private  GetEmployeurByIdTrt getEmployeurByIdTrt;
    private  GetStructureTrt getStructureTrt ;
    private  GetListCategoriesPersonneTrt getListCategoriesPersonneTrt;
    /**
     * methode pour recherche une operation par son code
     * @param vo : Operation
     * @return   : Operation
     */
    public IValueObject getOperation(IValueObject vo) {
        return getOperationTrt.exec(vo);
        
    }

    /**
     * methode pouyr la recherche d'un pays avec son code
     * @param vo :pays
     * @return  :pays
     */
    public IValueObject getPays(IValueObject vo) {
       return getPaysTrt.exec(vo);

    }

    /**
     * methode pouyr la recherche d'un code Postal
     * @param vo :CodePostal
     * @return  :CodePostal
     */
    public IValueObject getCodePostal(IValueObject vo) { 
   
        getCodePostalTrt.setSecurityFlag(false);
        return getCodePostalTrt.exec(vo);
    }

    /**
     * methode pour la recherche d'un Type de modification
     * @param vo : TypeModification
     * @return   : TypeModification
     */
    public IValueObject getTypeModification(IValueObject vo) {
        return getTypeModificationTrt.exec(vo);      
    }

    /**
     * methode pour la recherche d'une profession
     * @param vo : Profession
     * @return   : Profession
     */
    public IValueObject getProfessionById(IValueObject vo) {
       return getProfessionByIdTrt.exec(vo);
    }

    /**
     * methode pour la recherche d'une activite
     * @param vo : Activite
     * @return   : Activite
     */
    public IValueObject getActiviteById(IValueObject vo) {
        return getActiviteByIdTrt.exec(vo);
       

    }

    /**
     * methode pour la recherche d'un type pièce par son code
     * @param vo : TypePiece
     * @return   : TypePiece
     */
    public IValueObject getTypePiece(IValueObject vo) {
        return getTypePieceTrt.exec(vo);    

    }

    /**
     * methode pour la recherche d'un Gouvernorat par son code
     * @param vo : Gouvernorat
     * @return   : Gouvernorat
     */
    public IValueObject getGouvernorat(IValueObject vo) {     
        getCodePostalTrt.setSecurityFlag(false);
        return (getGouvernoratTrt.exec(vo));

    }

    /**
     * methode pour la recherche d'un Gouvernorat par son code
     * @param vo : Gouvernorat
     * @return   : Gouvernorat
     */
    public IValueObject getSegment(IValueObject vo) {
        return (getSegmentTrt.exec(vo));

    }


    /** méthode d'extraction des Classe segment en prend en argument le critaire de recherche
     * @param   String : critaire de recherche 
     * @return  ValueObject : Listes des classe segment
     */
    public IValueObject getListeClassSegment(IValueObject vo) {
       
        getListeClassSegmentTrt.setSecurityFlag(false);
        return (getListeClassSegmentTrt.exec(vo));
    }


    /** méthode d'extraction des Sous Classe segment en prend en argument le critaire de recherche et la code de la classe segment
     * @param   String : critaire de recherche et String : code classe segment
     * @return  ValueObject : Listes des sous classe segment
     */
    public IValueObject getListeSousClassSegment(IValueObject vo) {
        getListeSousClassSegmentTrt.setSecurityFlag(false);
        return (getListeSousClassSegmentTrt.exec(vo));
    }

    /** méthode d'extraction des segments en prend en argument le critaire de recherche et la code du Sous Classe segment
     * @param   String : critaire de recherche et String : code sous classe segment
     * @return  ValueObject : Listes des segments
     */
    public IValueObject getListeSegment(IValueObject vo) {
      
        getListeSegmentTrt.setSecurityFlag(false);
        return (getListeSegmentTrt.exec(vo));
    }

    /** méthode por la recherche d'un régime matrimonial
     * @param   RegimeMatrimonial :
     * @return  RegimeMatrimonial :
     * @author Mdimagh lassaad
     */
    public IValueObject getRegimeMatrimonial(IValueObject vo) {
        return (getRegimeMatrimonialTrt.exec(vo));
    }

    /** méthode por la recherche d'un Niveau d'instruction
     * @param   NiveauInstruction :
     * @return  NiveauInstruction :
     * @author Mdimagh lassaad
     */
    public IValueObject getNiveauInstruction(IValueObject vo) {
        return (getNiveauInstructionTrt.exec(vo));
    }

    /** méthode por la recherche de la FomeJuridique
     * @param   FomeJuridique :
     * @return  FomeJuridique :
     * @author Mdimagh lassaad
     */
    public IValueObject getFomeJuridique(IValueObject vo) {
        getFomeJuridiqueTrt.setSecurityFlag(false);
        return (getFomeJuridiqueTrt.exec(vo));
    }

    /** méthode por la recherche de la CategoriePersonne
     * @param   CategoriePersonne :
     * @return  CategoriePersonne :
     * @author Mdimagh lassaad
     */
    public IValueObject getCategoriePersonne(IValueObject vo) {

      
        return (getCategoriePersonneTrt.exec(vo));
    }

    /** méthode pour la recherche d'un groupe
     * @param   Groupe :
     * @return  Groupe :
     * @author Mdimagh lassaad
     * @since 27/06/07
     */
    public IValueObject getGroupe(IValueObject vo) {

      
        getGroupeTrt.setSecurityFlag(false);
        return (getGroupeTrt.exec(vo));
    }
    
    /** méthode pour la recherche d'une categorie socioperofessionel
     * @param   CatSocProf :
     * @return  CatSocProf :
     * @author Mdimagh lassaad
     * @since 10/07/07
     */
    public IValueObject getCatSocProf(IValueObject vo) {

        
        return (getCatSocProfTrt.exec(vo));
    }
    
    /** méthode pour la recherche d'une commission
     * @param   commission :
     * @return  commission :
     * @author el arbi hassine
     * @since 17/10/07
     */
    public IValueObject getCommission(IValueObject vo) {

       
        return (getCommissionTrt.exec(vo));
    }

    
    /** méthode pour la recherche d'une devise
     * @param   Devise :
     * @return  Devise :
     * @author Mdimagh lassaad
     * @since 17/10/07
     */
    public IValueObject getDevise(IValueObject vo) {

       
        getDeviseTrt.setSecurityFlag(false);
        return (getDeviseTrt.exec(vo));
    }


    /** méthode pour la recherche d'une nomenclature Element condition
     * @param   NomencElemtCondition :
     * @return  NomencElemtCondition :
     * @author el arbi hassine
     * @since 14/11/07
     */
    public IValueObject getNomencElemCond(IValueObject vo) {

        
        return (getNomencElemCondTrt.exec(vo));
    }
   
    /** méthode pour la recherche d'une piece annexe par son identifiant
     * @param  PieceAnnexe :
     * @return  PieceAnnexe :
     * @author Mdimagh Med Lassaad
     * @since  25/12/2007
     */
    public IValueObject getPieceAnnexeById(IValueObject vo) {

       
        return (getPieceAnnexeById.exec(vo) );
    }
 
    /** méthode pour la recherche d'un employeur  par son identifiant
     * @param  Employeur :
     * @return  Employeur :
     * @author Mdimagh Med Lassaad
     * @since  25/12/2007
     */
    public IValueObject getEmployeurById(IValueObject vo) {

       
        return (getEmployeurByIdTrt.exec(vo) );
    }
    
    
    /** méthode pour la recherche d'une structure
     * @param   Structure :
     * @return  Structure :
     * @author Mdimagh Med Lassaad
     * @since  25/12/2007
     */
    public IValueObject getStructure(IValueObject vo) {

       
        return (getStructureTrt.exec(vo) );
    }

    public void setGetOperationTrt(GetOperationTrt getOperationTrt) {
        this.getOperationTrt = getOperationTrt;
    }

    public GetOperationTrt getGetOperationTrt() {
        return getOperationTrt;
    }

    public void setGetPaysTrt(GetPaysTrt getPaysTrt) {
        this.getPaysTrt = getPaysTrt;
    }

    public GetPaysTrt getGetPaysTrt() {
        return getPaysTrt;
    }

    public void setGetCodePostalTrt(GetCodePostalTrt getCodePostalTrt) {
        this.getCodePostalTrt = getCodePostalTrt;
    }

    public GetCodePostalTrt getGetCodePostalTrt() {
        return getCodePostalTrt;
    }

    public void setGetTypeModificationTrt(GetTypeModificationTrt getTypeModificationTrt) {
        this.getTypeModificationTrt = getTypeModificationTrt;
    }

    public GetTypeModificationTrt getGetTypeModificationTrt() {
        return getTypeModificationTrt;
    }

    public void setGetProfessionByIdTrt(GetProfessionByIdTrt getProfessionByIdTrt) {
        this.getProfessionByIdTrt = getProfessionByIdTrt;
    }

    public GetProfessionByIdTrt getGetProfessionByIdTrt() {
        return getProfessionByIdTrt;
    }

    public void setGetActiviteByIdTrt(GetActiviteByIdTrt getActiviteByIdTrt) {
        this.getActiviteByIdTrt = getActiviteByIdTrt;
    }

    public GetActiviteByIdTrt getGetActiviteByIdTrt() {
        return getActiviteByIdTrt;
    }

    public void setGetTypePieceTrt(GetTypePieceTrt getTypePieceTrt) {
        this.getTypePieceTrt = getTypePieceTrt;
    }

    public GetTypePieceTrt getGetTypePieceTrt() {
        return getTypePieceTrt;
    }

    public void setGetGouvernoratTrt(GetGouvernoratTrt getGouvernoratTrt) {
        this.getGouvernoratTrt = getGouvernoratTrt;
    }

    public GetGouvernoratTrt getGetGouvernoratTrt() {
        return getGouvernoratTrt;
    }

    public void setGetSegmentTrt(GetSegmentTrt getSegmentTrt) {
        this.getSegmentTrt = getSegmentTrt;
    }

    public GetSegmentTrt getGetSegmentTrt() {
        return getSegmentTrt;
    }

    public void setGetListeClassSegmentTrt(GetListeClassSegmentTrt getListeClassSegmentTrt) {
        this.getListeClassSegmentTrt = getListeClassSegmentTrt;
    }

    public GetListeClassSegmentTrt getGetListeClassSegmentTrt() {
        return getListeClassSegmentTrt;
    }

    public void setGetListeSousClassSegmentTrt(GetListeSousClassSegmentTrt getListeSousClassSegmentTrt) {
        this.getListeSousClassSegmentTrt = getListeSousClassSegmentTrt;
    }

    public GetListeSousClassSegmentTrt getGetListeSousClassSegmentTrt() {
        return getListeSousClassSegmentTrt;
    }

    public void setGetListeSegmentTrt(GetListeSegmentTrt getListeSegmentTrt) {
        this.getListeSegmentTrt = getListeSegmentTrt;
    }

    public GetListeSegmentTrt getGetListeSegmentTrt() {
        return getListeSegmentTrt;
    }

    public void setGetRegimeMatrimonialTrt(GetRegimeMatrimonialTrt getRegimeMatrimonialTrt) {
        this.getRegimeMatrimonialTrt = getRegimeMatrimonialTrt;
    }

    public GetRegimeMatrimonialTrt getGetRegimeMatrimonialTrt() {
        return getRegimeMatrimonialTrt;
    }

    public void setGetNiveauInstructionTrt(GetNiveauInstructionTrt getNiveauInstructionTrt) {
        this.getNiveauInstructionTrt = getNiveauInstructionTrt;
    }

    public GetNiveauInstructionTrt getGetNiveauInstructionTrt() {
        return getNiveauInstructionTrt;
    }

    public void setGetFomeJuridiqueTrt(GetFormeJuridiqueTrt getFomeJuridiqueTrt) {
        this.getFomeJuridiqueTrt = getFomeJuridiqueTrt;
    }

    public GetFormeJuridiqueTrt getGetFomeJuridiqueTrt() {
        return getFomeJuridiqueTrt;
    }

    public void setGetCategoriePersonneTrt(GetCategoriePersonneTrt getCategoriePersonneTrt) {
        this.getCategoriePersonneTrt = getCategoriePersonneTrt;
    }

    public GetCategoriePersonneTrt getGetCategoriePersonneTrt() {
        return getCategoriePersonneTrt;
    }

    public void setGetGroupeTrt(GetGroupeTrt getGroupeTrt) {
        this.getGroupeTrt = getGroupeTrt;
    }

    public GetGroupeTrt getGetGroupeTrt() {
        return getGroupeTrt;
    }

    public void setGetCatSocProfTrt(GetCatSocProfTrt getCatSocProfTrt) {
        this.getCatSocProfTrt = getCatSocProfTrt;
    }

    public GetCatSocProfTrt getGetCatSocProfTrt() {
        return getCatSocProfTrt;
    }

    public void setGetNomencElemCondTrt(GetNomencElemCondTrt getNomencElemCondTrt) {
        this.getNomencElemCondTrt = getNomencElemCondTrt;
    }

    public GetNomencElemCondTrt getGetNomencElemCondTrt() {
        return getNomencElemCondTrt;
    }

    public void setGetCommissionTrt(GetCommissionTrt getCommissionTrt) {
        this.getCommissionTrt = getCommissionTrt;
    }

    public GetCommissionTrt getGetCommissionTrt() {
        return getCommissionTrt;
    }

    public void setGetDeviseTrt(GetDeviseTrt getDeviseTrt) {
        this.getDeviseTrt = getDeviseTrt;
    }

    public GetDeviseTrt getGetDeviseTrt() {
        return getDeviseTrt;
    }

    public void setGetPieceAnnexeById(GetPieceAnnexeByIdTrt getPieceAnnexeById) {
        this.getPieceAnnexeById = getPieceAnnexeById;
    }

    public GetPieceAnnexeByIdTrt getGetPieceAnnexeById() {
        return getPieceAnnexeById;
    }

    public void setGetEmployeurByIdTrt(GetEmployeurByIdTrt getEmployeurByIdTrt) {
        this.getEmployeurByIdTrt = getEmployeurByIdTrt;
    }

    public GetEmployeurByIdTrt getGetEmployeurByIdTrt() {
        return getEmployeurByIdTrt;
    }

    public void setGetStructureTrt(GetStructureTrt getStructureTrt) {
        this.getStructureTrt = getStructureTrt;
    }

    public GetStructureTrt getGetStructureTrt() {
        return getStructureTrt;
    }
    public IValueObject  getListCategoriesPersonneService(IValueObject vo) {
        GetListCategoriesPersonneTrt getListCategoriesPersonneTrt=new GetListCategoriesPersonneTrt();
        return getListCategoriesPersonneTrt.perform(vo);
    }
}
