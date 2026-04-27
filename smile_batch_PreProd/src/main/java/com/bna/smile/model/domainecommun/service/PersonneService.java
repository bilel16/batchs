package com.bna.smile.model.domainecommun.service;

import com.bna.commun.model.PersClient;
import com.bna.commun.model.Personne;
import com.bna.commun.model.PieceAnnexe;
import com.bna.commun.model.TypePiece;
import com.bna.commun.util.ContextHandler;

import com.bna.smile.model.domainecommun.model.PersonneStrc;
import com.bna.smile.model.domainecommun.traitement.GetListContratMandataireTrt;
import com.bna.smile.model.domainecommun.traitement.GetListContratTrt;
import com.bna.smile.model.domainecommun.traitement.GetListCotitulairePersonneTrt;
import com.bna.smile.model.domainecommun.traitement.GetListMembreCotitulaireTrt;
import com.bna.smile.model.domainecommun.traitement.GetListeActiviteTrt;
import com.bna.smile.model.domainecommun.traitement.GetListeClassActiviteTrt;
import com.bna.smile.model.domainecommun.traitement.GetListeGroupProfessionTrt;
import com.bna.smile.model.domainecommun.traitement.GetListeProfessionTrt;
import com.bna.smile.model.domainecommun.traitement.GetListeSousClassActiviteTrt;
import com.bna.smile.model.domainecommun.traitement.GetMembreCotitulaireTrt;
import com.bna.smile.model.domainecommun.traitement.GetPersonneByNumSeqPersTrt;
import com.bna.smile.model.domainecommun.traitement.GetPersonneCptTrt;

import com.bna.smile.model.domainecommun.traitement.GetPersonneTrt;
import com.bna.smile.model.domainecommun.traitement.GetPersonnelByCinTrt;
import com.bna.smile.model.domainecommun.traitement.GetPersonnelTrt;
import com.bna.smile.model.domainecommun.traitement.GetPieceAnnexeTrt;
import com.bna.smile.model.domainecommun.traitement.InsertPersonneTrt;

import com.bna.smile.model.domainecommun.traitement.InsertPieceAnnexeTrt;

import com.bna.smile.model.domainecommun.traitement.UpdatePersonneTrt;

import com.oxia.fwk.beans.service.BasicService;
import com.oxia.fwk.context.Context;
import com.oxia.fwk.core.ICriteria;
import com.oxia.fwk.core.IExpression;
import com.oxia.fwk.core.ISearchEngine;
import com.oxia.fwk.core.IValueObject;
import com.oxia.fwk.core.ValueObject;
import com.oxia.fwk.searchengine.SearchEngine;

import java.util.List;

public class PersonneService extends BasicService{

    private  GetPersonneCptTrt getPersonneCptTrt;
    private  InsertPersonneTrt insertPersonneTrt;
    private  GetPersonneTrt getPersonneTrt;
    private  GetPersonneByNumSeqPersTrt getPersonneByNumSeqPersTrt;
    private  GetPieceAnnexeTrt getPieceAnnexeTrt;
    private  GetListeClassActiviteTrt getListeClassActiviteTrt;
    private  GetListeSousClassActiviteTrt getListeSousClassActiviteTrt;
    private  GetListeActiviteTrt getListeActiviteTrt;
    private  GetListeGroupProfessionTrt getListeGroupProfessionTrt;
    private  GetListeProfessionTrt getListeProfessionTrt;
    private  InsertPieceAnnexeTrt insertPieceAnnexeTrt;
    private  GetListCotitulairePersonneTrt getListCotitulairePersonneTrt;
    private  GetListMembreCotitulaireTrt getListMembreCotitulaireTrt;
    private  GetListContratTrt getListContratTrt;
    private  GetListContratMandataireTrt getListContratMandataireTrt;
    private  GetMembreCotitulaireTrt getMembreCotitulaireTrt;
    private  GetPersonnelByCinTrt getPersonnelByCinTrt;
    private  GetPersonnelTrt getPersonnelTrt ;
    

    public PersonneService() {
    }
    
  
    

    /**
     * Fonction qui prend les données d'une personne et celle de l'agence
     * pour retourner toutes les données de cette personne ainsi que ses
     * contrats valides dans cette agence(si code agence est null on affiche
     * les contrats dans toute la banque ).
     * @param personneStrc : type piece, N° piece et le code de l'agence
     * @return PersonneCpt : l'objet personne et ses contrats valides
     */
    public IValueObject getPersonneCpt(IValueObject vo) {
        getPersonneCptTrt.setSecurityFlag(false);
        return (getPersonneCptTrt.exec(vo));

    }

    /** méthode d'insertion  d'une nouvelle personne en prend en argument la classe Personne et retourne un valueObject Personne
     * @param   ValueObject : AjoutPersonneClientContratVo avec la personne sans numero de sequence
     * @return  ValueObject : AjoutPersonneClientContratVo ,Personne  avec numero de sequence
     */
    public IValueObject insertPersonne(IValueObject vo) {       
        return (insertPersonneTrt.exec(vo));
    }


    /** Méthode privé qui verifi l'existance du client
     * @author Mdimagh Lassaad
     * @since  26/01/07
     * @param Integer Type pièce et String Numero de la pièce
     * @return boolean
     */
     public boolean verifierExistancePersonne(Long codeTypePiece, 
                                              String numeroPiece) {

         PersonneStrc personneStrc = new PersonneStrc();
         Personne pers = new Personne();
         
         personneStrc.setCodTpceTpce(codeTypePiece);
         personneStrc.setNumPcePers(numeroPiece);
         
         pers = (Personne)getPersonneTrt.exec(personneStrc);
         
         
         if (pers != null && pers.getNumSeqPers() != null ) {
             return (true);
         } else {
             return (false);

         }
     }


      
    /**
         * methode permettant la recherche d'une personne selon le type pièce et numero pièce
         * @param vo :Objet : PersonneStrc
         * @return   :Objet : Personne
         */
        public IValueObject getPersonne(IValueObject vo) {                     
            
            getPersonneTrt.setSecurityFlag(false);
            return (getPersonneTrt.exec(vo));
        }

    /**
     * methode permettant la recherche d'une personne par son numero séquentiel : NumSeqPers
     * @param vo :Objet : Personne
     * @return   :Objet : Personne
     */

    public IValueObject getPersonneByNumSeqPers(IValueObject vo) {
      return getPersonneByNumSeqPersTrt.exec(vo);        
    }

    /**
     * methode permettant la recherche d'une piece annexe selon le type pièce et numero pièce annexe
     * @param vo :Objet : PersonneStrc
     * @return   :Objet : pieceAnnexe
     */
    public IValueObject getPieceAnnexe(IValueObject vo) {
      
        return (getPieceAnnexeTrt.exec(vo));
    }


    /** méthode d'extraction des Classe activite d'une en prend en argument le critaire de recherche
     * @param   String : critaire de recherche 
     * @return  ValueObject : Listes des classe activité
     */
    public IValueObject getListeClassActivite(IValueObject vo) {
        
        return (getListeClassActiviteTrt.exec(vo));
    }

    /** méthode d'extraction des Sous Classe activite  en prend en argument le critaire de recherche et la code de la classe d'activité
     * @param   String : critaire de recherche et String : code classe activité
     * @return  ValueObject : Listes des sous classe activité
     */
    public IValueObject getListeSousClassActivite(IValueObject vo) { 
        
        return (getListeSousClassActiviteTrt.exec(vo));
    }

    /** méthode d'extraction des activités en prend en argument le critaire de recherche et la code du Sous Classe activité
     * @param   String : critaire de recherche et String : code sous classe activité
     * @return  ValueObject : Listes des sous classe activité
     */
    public IValueObject getListeActivite(IValueObject vo) {
        
        return (getListeActiviteTrt.exec(vo));
    }

    /** méthode d'extraction des Groupe profession d'une en prend en argument le critaire de recherche
     * @param   String : critaire de recherche 
     * @return  ValueObject : Listes des Groupe profession 
     */
    public IValueObject getListeGroupProfession(IValueObject vo) {
        
        return (getListeGroupProfessionTrt.exec(vo));
    }

    /** méthode d'extraction des professions en prend en argument le critaire de recherche et la code du Groupe profession
     * @param   String : critaire de recherche et String : code Groupe profession
     * @return  ValueObject : Listes des Profession
     */
    public IValueObject getListeProfession(IValueObject vo) {
       
        return (getListeProfessionTrt.exec(vo));
    }


    /** méthode d'extraction des professions en prend en argument le critaire de recherche et la code du Groupe profession
     * @param   String : critaire de recherche et String : code Groupe profession
     * @return  ValueObject : Listes des Profession
     */
    public IValueObject insertPieceAnnexe(IValueObject vo) {     
        return (insertPieceAnnexeTrt.exec(vo));
    }


    /** Méthode  qui permet de verifier l'existance de la relation entre un
     * client et une personne ( PersClient)
     * @author el Arbi Hassine
     * @since  30/03/2007
     * @param Long Num Sequence Personne, Long Num Sequence Client, Long code qualite
     * @return boolean
     */
    public boolean verifierExistancePersClient(Long numSeqPers, Long numSeqCli, 
                                               Long codeQualQual) {

        Context context = ContextHandler.getContext();
        ISearchEngine searchEngine = 
            (SearchEngine)context.getBean("searchEngine");
        ICriteria criteria = searchEngine.createCriteria();
        IExpression expression = searchEngine.createExpression();

        criteria.add(expression.eq("persClientId.numSeqPers", numSeqPers));
        criteria.add(expression.eq("persClientId.numSeqCli", numSeqCli));
        criteria.add(expression.eq("persClientId.codQualQual", codeQualQual));

        List l = searchEngine.find(PersClient.class, criteria);

        if (l != null && l.size() > 0) {
            return (true);
        } else {
            return (false);

        }
    }

    /** Méthode  qui permet d'extraire les entités cootitulaires contenant une personne
     * @author Ramzi
     * @since  16/04/2007
     * @param VO:PersonneStrc contenant type piece, num piece
     * @return VO:Liste des cootitulaires
     */
    public IValueObject getListCotitulairePersonne(IValueObject vo) {
     
        return (getListCotitulairePersonneTrt.exec(vo));
    }


    /** Méthode qui permet d'extraire les membres d'une entité cootitulaires
     * @author Ramzi
     * @since  16/04/2007
     * @param VO:PersonneStrc contenant type piece, num piece de l'entité
     * @return VO:Liste des membres cootitulaires
     */
    public IValueObject getListMembreCotitulaire(IValueObject vo) {     
        return (getListMembreCotitulaireTrt.exec(vo));
    }

    /** Méthode qui permet d'extraire tous les contrats d'un client(valide & non valide)
     * @author Ramzi
     * @since 30/04/2007
     * @param VO:PersonneStrc contenant type piece, num piece de l'entité
     * @return VO:Liste des contrats valides
     */
    public IValueObject getListContrat(IValueObject vo) {   
        return (getListContratTrt.exec(vo));
    }

    /** Méthode qui permet d'extraire les contrats valides sur les quelles il est mandataire
     * @author Ramzi
     * @since  30/04/2007
     * @param VO:PersonneStrc contenant type piece, num piece de l'entité
     * @return VO:Liste des contrats valides sur les quelles il est mandataire
     */
    public IValueObject getListContratMandataire(IValueObject vo) {
      
        return (getListContratMandataireTrt.exec(vo));
    }

    /** Méthode qui permet de tester si une personne est membre cootitulaire sur
     * un contrat donné, et retourne cet objet dans le cas ou il existe.
     * @author Ramzi
     * @since  07/05/2007
     * @param VO:PersonneStrc contenant type piece, num piece & IdContratCpt:codStrcStrc,codPrdPrd,numCcptCcpt
     * @return VO:Entité cotitulaire
     */
    public IValueObject getMembreCotitulaire(IValueObject vo) {       
        return (getMembreCotitulaireTrt.exec(vo));
    }


    /** Méthode qui permet de vérifier l'existance d'un personnel à travers son CIN
     * 
     * @author El Arbi Hassine
     * @since  20/02/2008
     * @param VO: Personnel
     * @return VO:personnel
     */
    public IValueObject getPersonnelByCin(IValueObject vo) {

        return (getPersonnelByCinTrt.exec(vo));
    }
    
    /** Méthode qui permet de vérifier l'existance d'un personnel à travers son matricule
     * 
     * @author El Arbi Hassine
     * @since  05/11/2008
     * @param VO: Personnel
     * @return VO:personnel
     */
    public IValueObject getPersonnel(IValueObject vo) {    
        return (getPersonnelTrt.exec(vo));
    }

  
    public void setGetPersonneCptTrt(GetPersonneCptTrt getPersonneCptTrt) {
        this.getPersonneCptTrt = getPersonneCptTrt;
    }

    public GetPersonneCptTrt getGetPersonneCptTrt() {
        return getPersonneCptTrt;
    }

    public void setInsertPersonneTrt(InsertPersonneTrt insertPersonneTrt) {
        this.insertPersonneTrt = insertPersonneTrt;
    }

    public InsertPersonneTrt getInsertPersonneTrt() {
        return insertPersonneTrt;
    }

    public void setGetPersonneTrt(GetPersonneTrt getPersonneTrt) {
        this.getPersonneTrt = getPersonneTrt;
    }

    public GetPersonneTrt getGetPersonneTrt() {
        return getPersonneTrt;
    }

    public void setGetPersonneByNumSeqPersTrt(GetPersonneByNumSeqPersTrt getPersonneByNumSeqPersTrt) {
        this.getPersonneByNumSeqPersTrt = getPersonneByNumSeqPersTrt;
    }

    public GetPersonneByNumSeqPersTrt getGetPersonneByNumSeqPersTrt() {
        return getPersonneByNumSeqPersTrt;
    }

    public void setGetPieceAnnexeTrt(GetPieceAnnexeTrt getPieceAnnexeTrt) {
        this.getPieceAnnexeTrt = getPieceAnnexeTrt;
    }

    public GetPieceAnnexeTrt getGetPieceAnnexeTrt() {
        return getPieceAnnexeTrt;
    }

    public void setGetListeClassActiviteTrt(GetListeClassActiviteTrt getListeClassActiviteTrt) {
        this.getListeClassActiviteTrt = getListeClassActiviteTrt;
    }

    public GetListeClassActiviteTrt getGetListeClassActiviteTrt() {
        return getListeClassActiviteTrt;
    }

    public void setGetListeSousClassActiviteTrt(GetListeSousClassActiviteTrt getListeSousClassActiviteTrt) {
        this.getListeSousClassActiviteTrt = getListeSousClassActiviteTrt;
    }

    public GetListeSousClassActiviteTrt getGetListeSousClassActiviteTrt() {
        return getListeSousClassActiviteTrt;
    }

    public void setGetListeActiviteTrt(GetListeActiviteTrt getListeActiviteTrt) {
        this.getListeActiviteTrt = getListeActiviteTrt;
    }

    public GetListeActiviteTrt getGetListeActiviteTrt() {
        return getListeActiviteTrt;
    }

    public void setGetListeGroupProfessionTrt(GetListeGroupProfessionTrt getListeGroupProfessionTrt) {
        this.getListeGroupProfessionTrt = getListeGroupProfessionTrt;
    }

    public GetListeGroupProfessionTrt getGetListeGroupProfessionTrt() {
        return getListeGroupProfessionTrt;
    }

    public void setGetListeProfessionTrt(GetListeProfessionTrt getListeProfessionTrt) {
        this.getListeProfessionTrt = getListeProfessionTrt;
    }

    public GetListeProfessionTrt getGetListeProfessionTrt() {
        return getListeProfessionTrt;
    }

    public void setInsertPieceAnnexeTrt(InsertPieceAnnexeTrt insertPieceAnnexeTrt) {
        this.insertPieceAnnexeTrt = insertPieceAnnexeTrt;
    }

    public InsertPieceAnnexeTrt getInsertPieceAnnexeTrt() {
        return insertPieceAnnexeTrt;
    }

    public void setGetListCotitulairePersonneTrt(GetListCotitulairePersonneTrt getListCotitulairePersonneTrt) {
        this.getListCotitulairePersonneTrt = getListCotitulairePersonneTrt;
    }

    public GetListCotitulairePersonneTrt getGetListCotitulairePersonneTrt() {
        return getListCotitulairePersonneTrt;
    }

    public void setGetListMembreCotitulaireTrt(GetListMembreCotitulaireTrt getListMembreCotitulaireTrt) {
        this.getListMembreCotitulaireTrt = getListMembreCotitulaireTrt;
    }

    public GetListMembreCotitulaireTrt getGetListMembreCotitulaireTrt() {
        return getListMembreCotitulaireTrt;
    }

    public void setGetListContratTrt(GetListContratTrt getListContratTrt) {
        this.getListContratTrt = getListContratTrt;
    }

    public GetListContratTrt getGetListContratTrt() {
        return getListContratTrt;
    }

    public void setGetListContratMandataireTrt(GetListContratMandataireTrt getListContratMandataireTrt) {
        this.getListContratMandataireTrt = getListContratMandataireTrt;
    }

    public GetListContratMandataireTrt getGetListContratMandataireTrt() {
        return getListContratMandataireTrt;
    }

    public void setGetMembreCotitulaireTrt(GetMembreCotitulaireTrt getMembreCotitulaireTrt) {
        this.getMembreCotitulaireTrt = getMembreCotitulaireTrt;
    }

    public GetMembreCotitulaireTrt getGetMembreCotitulaireTrt() {
        return getMembreCotitulaireTrt;
    }

    public void setGetPersonnelByCinTrt(GetPersonnelByCinTrt getPersonnelByCinTrt) {
        this.getPersonnelByCinTrt = getPersonnelByCinTrt;
    }

    public GetPersonnelByCinTrt getGetPersonnelByCinTrt() {
        return getPersonnelByCinTrt;
    }

    public void setGetPersonnelTrt(GetPersonnelTrt getPersonnelTrt) {
        this.getPersonnelTrt = getPersonnelTrt;
    }

    public GetPersonnelTrt getGetPersonnelTrt() {
        return getPersonnelTrt;
    }
    
    public IValueObject updatePersonne(IValueObject vo) {       
        UpdatePersonneTrt updatepersonneTrt = new UpdatePersonneTrt();
        return (updatepersonneTrt.exec(vo));
    }
}


