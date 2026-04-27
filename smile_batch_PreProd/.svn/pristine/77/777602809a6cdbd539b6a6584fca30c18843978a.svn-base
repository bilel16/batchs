package com.bna.smile.model.domainecontratcompte.souscriptioncontratcompte.service;

import com.bna.commun.model.ContratCpt;
import com.bna.commun.model.ProduitsLies;
import com.bna.commun.model.ProduitsLiesId;
import com.bna.commun.model.Signature;
import com.bna.commun.util.ContextHandler;
import com.bna.smile.model.domainecommun.model.ContratPersonne;
import com.bna.smile.model.domainecontratcompte.souscriptioncontratcompte.model.SignaturePersCpt;
import com.bna.smile.model.domainecontratcompte.souscriptioncontratcompte.traitement.BloquerContratCptTrt;
import com.bna.smile.model.domainecontratcompte.souscriptioncontratcompte.traitement.BloquerMntTrt;
import com.bna.smile.model.domainecontratcompte.souscriptioncontratcompte.traitement.CalculSoldTheorEpargneTrt;
import com.bna.smile.model.domainecontratcompte.souscriptioncontratcompte.traitement.ChargerBlocagesTrt;
import com.bna.smile.model.domainecontratcompte.souscriptioncontratcompte.traitement.ChargerCatSupTrt;
import com.bna.smile.model.domainecontratcompte.souscriptioncontratcompte.traitement.ChargerMotifEtatTrt;
import com.bna.smile.model.domainecontratcompte.souscriptioncontratcompte.traitement.ChargerNatureblocageTrt;
import com.bna.smile.model.domainecontratcompte.souscriptioncontratcompte.traitement.ChargerRgmCatEpargneTrt;
import com.bna.smile.model.domainecontratcompte.souscriptioncontratcompte.traitement.ChargerTypeCatPcePersonneTrt;
import com.bna.smile.model.domainecontratcompte.souscriptioncontratcompte.traitement.CloturerContratTrt;
import com.bna.smile.model.domainecontratcompte.souscriptioncontratcompte.traitement.DebloquerContratCptTrt;
import com.bna.smile.model.domainecontratcompte.souscriptioncontratcompte.traitement.DebloquerMntTrt;
import com.bna.smile.model.domainecontratcompte.souscriptioncontratcompte.traitement.GestionEpargneTrt;
import com.bna.smile.model.domainecontratcompte.souscriptioncontratcompte.traitement.GetListeContratsAmodifierTrt;
import com.bna.smile.model.domainecontratcompte.souscriptioncontratcompte.traitement.GetListeContratsRejetesTrt;
import com.bna.smile.model.domainecontratcompte.souscriptioncontratcompte.traitement.GetNbrProduitByPersTrt;
import com.bna.smile.model.domainecontratcompte.souscriptioncontratcompte.traitement.GetProduitAutorisesTrt;
import com.bna.smile.model.domainecontratcompte.souscriptioncontratcompte.traitement.GetSignaturesTrt;
import com.bna.smile.model.domainecontratcompte.souscriptioncontratcompte.traitement.InsertClientContCompteTrt;
import com.bna.smile.model.domainecontratcompte.souscriptioncontratcompte.traitement.InsertClientContratTrt;
import com.bna.smile.model.domainecontratcompte.souscriptioncontratcompte.traitement.InsertClientTrt;
import com.bna.smile.model.domainecontratcompte.souscriptioncontratcompte.traitement.InsertCompteLieTrt;
import com.bna.smile.model.domainecontratcompte.souscriptioncontratcompte.traitement.InsertComptePersonnelBnaTrt;
import com.bna.smile.model.domainecontratcompte.souscriptioncontratcompte.traitement.InsertCotitulaireTrt;
import com.bna.smile.model.domainecontratcompte.souscriptioncontratcompte.traitement.InsertDetailCatContratTrt;
import com.bna.smile.model.domainecontratcompte.souscriptioncontratcompte.traitement.InsertDetailEtatContratTrt;
import com.bna.smile.model.domainecontratcompte.souscriptioncontratcompte.traitement.InsertLivretEpargneTrt;
import com.bna.smile.model.domainecontratcompte.souscriptioncontratcompte.traitement.InsertPersonneClientTrt;
import com.bna.smile.model.domainecontratcompte.souscriptioncontratcompte.traitement.InsertSignaturesTrt;
import com.bna.smile.model.domainecontratcompte.souscriptioncontratcompte.traitement.InsertTraceContratTrt;
import com.bna.smile.model.domainecontratcompte.souscriptioncontratcompte.traitement.MAJContratClientTransfertEpargneTrt;
import com.bna.smile.model.domainecontratcompte.souscriptioncontratcompte.traitement.MiseAJourDetailCatContratTrt;
import com.bna.smile.model.domainecontratcompte.souscriptioncontratcompte.traitement.MiseAJourLivretEpargneTrt;
import com.bna.smile.model.domainecontratcompte.souscriptioncontratcompte.traitement.ModifSignaturesTrt;
import com.bna.smile.model.domainecontratcompte.souscriptioncontratcompte.traitement.RejeterContratTrt;
import com.bna.smile.model.domainecontratcompte.souscriptioncontratcompte.traitement.TraitementValidationContratTrt;
import com.bna.smile.model.domainecontratcompte.souscriptioncontratcompte.traitement.TransfertCtxTrt;
import com.bna.smile.model.domainecontratcompte.souscriptioncontratcompte.traitement.UpdateContratCptTrt;
import com.bna.smile.model.domainecontratcompte.souscriptioncontratcompte.traitement.ValiderContratTrt;
import com.bna.smile.model.domainecontratcompte.souscriptioncontratcompte.traitement.VerifDateLimiteTrt;
import com.oxia.fwk.beans.service.BasicService;
import com.oxia.fwk.context.Context;
import com.oxia.fwk.core.ISearchEngine;
import com.oxia.fwk.core.IValueObject;
import com.oxia.fwk.searchengine.SearchEngine;


public class SouscriptionContratCompteService extends BasicService {


 private   InsertCotitulaireTrt insertCotitulaireTrt;
 private   InsertPersonneClientTrt insertPersonneClientTrt ;
 private   InsertClientTrt insertClientTrt;
 private   InsertClientContratTrt insertClientContratTrt ;
 private   GetProduitAutorisesTrt rechercheProduitAutorisesTrt ;
 private   ChargerTypeCatPcePersonneTrt chargerTypeCatPcePersonneTrt;
 private   ChargerRgmCatEpargneTrt chargerRgmCatEpargneTrt;
 private   CalculSoldTheorEpargneTrt calculSoldTheorEpargneTrt ;
 private   GetNbrProduitByPersTrt getNbrProduitByPersTrt;
 private   MAJContratClientTransfertEpargneTrt mAJContratClientTransfertEpargneTrt;
 private   ChargerCatSupTrt chargerCatSupTrt ;
 private   GestionEpargneTrt gestionEpargneTrt;
 private   InsertDetailEtatContratTrt insertDetailEtatContratTrt ;
 private   InsertDetailCatContratTrt insertDetailCatContratTrt ;
 private   InsertCompteLieTrt insertCompteLieTrt;
 private   InsertClientContCompteTrt insertClientContCompteTrt ; 
 private   TraitementValidationContratTrt traitementValidationContratTrt;
 private   ValiderContratTrt validerContratTrt ;
 private   VerifDateLimiteTrt verifDateLimiteTrt;
 private   RejeterContratTrt rejeterContratTrt;
 private   InsertSignaturesTrt insertSignaturesTrt;
 private   MiseAJourDetailCatContratTrt miseAJourDetailCatContratTrt ;
 private   GetSignaturesTrt getSignaturesTrt;
 private   ModifSignaturesTrt modifSignaturesTrt ;
 private   GetListeContratsAmodifierTrt getListsContratsAmodifierTrt ;
 private   InsertTraceContratTrt insertTraceContratTrt;
 private   InsertLivretEpargneTrt insertLivretEpargneTrt ;
 private   BloquerContratCptTrt bloquerContratCptTrt;
 private   ChargerNatureblocageTrt chargerNatureblocageTrt ;
 private   DebloquerMntTrt deBloquerMntTrt;
 private   ChargerBlocagesTrt chargerBlocagesTrt;
 private   TransfertCtxTrt transfertCtxTrt;
 private   CloturerContratTrt cloturerContratTrt ;
 private   InsertComptePersonnelBnaTrt insertComptePersonnelBnaTrt;
 private   GetListeContratsRejetesTrt getListeContratsRejetesTrt ;
 private   BloquerMntTrt bloquerMntTrt;
 private   DebloquerContratCptTrt debloquerContratCptTrt;
 private   MiseAJourLivretEpargneTrt miseAJourLivretEpargneTrt ;
 private   ChargerMotifEtatTrt chargerMotifEtatTrt ;

    

    public SouscriptionContratCompteService() {
    }

    /** méthode d'insertion  d'un cotitulaire,elle prend en argument
     * la liste des personne, le client et les informations sur le Cotitulaire
     * et retourne un valueObject : liste des Cotitulaires insérés
     * @param   ValueObject : ListeCotit
     * @return  ValueObject : Listes
     */
    public IValueObject insertCotitulaire(IValueObject vo){
        
        return (insertCotitulaireTrt.exec(vo));
    }


    /**
     * méthode permettant d'ajouter une relation entre un client et une personne
     * @param vo (PersClient)
     * @return ValueObject
     */
    public IValueObject insertPersonneClient(IValueObject vo) {
     
        return (insertPersonneClientTrt.exec(vo));
    }

    /**
     * méthode d'insertion  d'une nouveau client en prend en argument unVo client
     * et retourne un valueObject AjoutPersonneClientContratVo
     *
     * @param vo Client
     * @return vo AjoutPersonneClientContratVo
     */
    public IValueObject insertClient(IValueObject vo) {
     
        return (insertClientTrt.exec(vo));
    }


    /**
     * Cette  methode permet d'inserer un contrat donné ainsi que
     * le client rattaché s'il n'existe pas.
     * @param (contratCpt)ValueObject
     * @return contratCpt : l'objet contrat inseré
     */
    public IValueObject insertClientContrat(IValueObject vo)  {      
                       
        return (insertClientContratTrt.exec(vo));       
      
    }
    /*****************************
         * Cette  methode permet d'inserer un contrat 519
         * 
         * @param (contratCpt)ValueObject
         * @return contratCpt : l'objet contrat inseré
         */
        public IValueObject insertClientContCompte519(IValueObject vo)  {      
                           
            return (insertClientContCompteTrt.exec(vo));       
          
        }
        
   
    

    /**
     * methode permettant la recherche des produits autorisés
     * pour unr personne donnée
     * @param vo : ParamPers
     * @return liste
     */
    public IValueObject getProduitAutorises(IValueObject vo) {
     
        return (rechercheProduitAutorisesTrt.exec(vo));
    }


    /**
     * Fonction qui prend, le type de la personne et retourne la liste des catégories,
     * ou bien le type et la catégorie pour retourner le type de la piece de cette catégorie
     * @param typeCatPers      : type_personne, [categorie_personne]
     * @return ListTypeCatTpce : liste des types de personne et
     * la liste des categories de ce type_personne et le type piece correspondant
     */
    public IValueObject chargerTypeCatPcePersonne(IValueObject vo) {

       
        chargerTypeCatPcePersonneTrt.setSecurityFlag(false);
        return (chargerTypeCatPcePersonneTrt.exec(vo));

    }

    /**
     * Fonction qui permet de charger la liste des régime du plan épargne d'un produit spécifique, de charger la liste
     * des catégories epargne de ce régime selon le choix du régime. le choix de la catégorie permet d'extraire un objet catégorie 
     * afin d'extraire les infos nécéssaires.
     * @param ParamEpargne      : codRgmRgm ( régime épargne ) , codPrdPrd ( code produit), codCatCat (categoriePersonne)
     * @return ListRgmCatEpargne : liste des régime, liste des catégorie, catégorie epargne.      
     * @Author : El arbi hassine
     */
    public IValueObject chargerRgmCatEpargne(IValueObject vo) {

       
        return (chargerRgmCatEpargneTrt.exec(vo));

    }


    /**
     * Fonction qui permet de charger la liste des régime du plan épargne d'un produit spécifique, de charger la liste
     * des catégories epargne superieures (pour le Changement de categorie/regime) de ce régime selon le choix du régime. le choix de la catégorie permet d'extraire un objet catégorie 
     * afin d'extraire les infos nécéssaires.
     * @param ParamEpargne      : codRgmRgm ( régime épargne ) , codPrdPrd ( code produit), codCatCat (categoriePersonne)
     * @return ListRgmCatEpargne : liste des régime, liste des catégorie, catégorie epargne.      
     * @Author : BOUSSEN Youssef & KRIAA Hatem
     */
    public IValueObject ChargerCatSup(IValueObject vo) {

       
        return (chargerCatSupTrt.exec(vo));

    }

    /**
     * Fonction qui calcule le solde théorique d'un contrat donnée
     * lors de changement de Categorie/Régime.
     * @param ParamDetailCatCpt   : ContratCpt , Categorie
     * @return PrimitiveVO : Double      
     * @Author : BOUSSEN Youssef & KRIAA Hatem
     */
    public IValueObject CalculSoldTheorEpargneTrt(IValueObject vo) {

      
        calculSoldTheorEpargneTrt.setSecurityFlag(false);
        return (calculSoldTheorEpargneTrt.exec(vo));

    }

    /**
     * Fonction qui verifie si un contrat peut etre transferé ou changer 
     * de catégorie ou de régime.
     * @param   ContratCpt  :  contratCpt
     * @return  PrimitiveVO :  boolean      
     * @Author : BOUSSEN Youssef & KRIAA Hatem
     */
    public IValueObject VerifDateLimite(IValueObject vo) {

       
        return (verifDateLimiteTrt.exec(vo));

    }


    /**
     * Fonction qui compte le nombre de contrats d'un produit donné
     * pour une personne donnée
     * @param   PersProduit  :  persProduit
     * @return  PrimitiveVO  :  Long      
     * @Author : BOUSSEN Youssef & KRIAA Hatem
     */
    public IValueObject GetNbrProduitByPers(IValueObject vo) {

      
        return (getNbrProduitByPersTrt.exec(vo));

    }

    /**
     * Fonction qui verifie si un contrat peut etre transferé ou changer 
     * de catégorie ou de régime.
     * @param   ContratCpt  :  contratCpt
     * @return  PrimitiveVO :  boolean      
     * @Author : BOUSSEN Youssef & KRIAA Hatem
     */
    public IValueObject MAJContratClientTransfertEpergne(IValueObject vo) {

     
        return (mAJContratClientTransfertEpargneTrt.exec(vo));

    }

    /**
     * Fonction qui assure le transfert ou le changement 
     * de catégorie ou de régime.
     * @param   GestionEpargneVO  :  gestionEpargneVO
     * @return  ValuoObject      
     * @Author : BOUSSEN Youssef & KRIAA Hatem
     */
    public IValueObject GestionEpargne(IValueObject vo) {

       
        return (gestionEpargneTrt.exec(vo));

    }

    /**
     * Cette  methode permet d'inserer le detail etat contrat 
     * @param (detailEtatContrat)ValueObject
     * @return detailEtatContrat : l'objet detailEtatContrat inseré
     */
    public IValueObject insertDetailEtatContrat(IValueObject vo) {
     
        return (insertDetailEtatContratTrt.exec(vo));
    }

    /**
     * Cette  methode permet d'inserer le detail Categorie contrat 
     * @param (detailCatContrat)ValueObject
     * @return detailCatContrat : l'objet detailCatContrat inseré
     */
    public IValueObject insertDetailCatContrat(IValueObject vo) {
      
        return (insertDetailCatContratTrt.exec(vo));
    }

    /**
     * cette methode permet la creation d'un contrat lié
     * @param vo ParamCompteLie
     * @return contratCpt
     */
    public IValueObject insertCompteLie(IValueObject vo) {
        
      
        return(insertCompteLieTrt.exec(vo));
        
    }

    /**
     * Méhode de controle de la validité du compte DAV
     * verifie si une relation existe entre le compte DAV et le compte Lie
     * @param comptelie
     * @param compteDav
     * @return boolean
     */
    public boolean verifProduitLie(ContratCpt comptelie, 
                                   ContratCpt compteDav) {

        try {
            ProduitsLiesId produitsLiesId = new ProduitsLiesId();
            produitsLiesId.setCodPrdPrd(compteDav.getContratCptId().getCodPrdPrd());
            produitsLiesId.setCodPrdlPrd(comptelie.getContratCptId().getCodPrdPrd());
            Context context = ContextHandler.getContext();
            ISearchEngine searchEngine = 
                (SearchEngine)context.getBean("searchEngine");
            /* Rechercher de l'occurence entre les deux comptes */
            ProduitsLies produitsLies = 
                (ProduitsLies)searchEngine.get(ProduitsLies.class, 
                                               produitsLiesId);

            if (produitsLies != null) {
                return (true);
            } else {
                return (false);
            }
        } catch (Exception e) {
            System.out.println("Erreur dans verifProduitLie " + e.toString());
            return false;
        }
    }


    /**
     * cette methode permet la creation d'un contrat lié
     * @param vo ParamCompteLie
     * @return contratCpt
     */
    public IValueObject traitementValidationContrat(IValueObject vo) {
      
        TraitementValidationContratTrt traitementValidationContratTrt = new TraitementValidationContratTrt();
        return (traitementValidationContratTrt.exec(vo));
    }


    /**
     * Méhode de validation d'un contrat en attente
     * @param contratAttente
     * @return contratValide :nouveau numéro du contrat validé
     */
    public IValueObject validerContrat(IValueObject vo) {
        
        return validerContratTrt.exec(vo);
    }

    /**
     * Méhode de rejet d'un contrat en attente
     * @param contratAttente
     * @return contratRejeté :numéro du contrat rejeté
     */
    public IValueObject rejeterContrat(IValueObject vo) {
     
        return rejeterContratTrt.exec(vo);
    }

    /**
     * méthode d'insertion des signatures Français et Arabe d'un client en prend en argument 
     * le contrat + personne + bufferedImage Fr + bufferedImage Ar
     * @param vo SignaturePersCpt:ContratPersonne, BufferedImage, BufferedImage 
     * @return vo SignaturePersCpt
     * @author :Ramzi
     */
    public IValueObject insertSignatures(IValueObject vo) {
        SignaturePersCpt signaturePersCpt = (SignaturePersCpt)vo;
       
        SignaturePersCpt signPersCpt = 
            (SignaturePersCpt)insertSignaturesTrt.execute(signaturePersCpt);
        return signPersCpt;
    }

    /**
     * Cette  methode permet de mettre à jour le detail Categorie contrat 
     * @param (detailCatContrat)ValueObject
     * @return detailCatContrat : l'objet detailCatContrat mis à jour
     */
    public IValueObject updateDetailCatContrat(IValueObject vo) {
      
       IValueObject v = miseAJourDetailCatContratTrt.exec(vo);
        return (v);
    }

    /**
     * méthode d'extraction d'un objet signature en prend en argument 
     * le contrat + personne
     * @param vo ContratPersonne 
     * @return vo Signature
     * @author :Ramzi
     */
    public IValueObject getSignatures(IValueObject vo) {
        ContratPersonne contratPersonne = (ContratPersonne)vo;
       
        Signature signature = 
            (Signature)getSignaturesTrt.execute(contratPersonne);
        return signature;
    }

    /**
     * méthode de modification des signatures Français et Arabe d'un client en prend en argument 
     * le contrat + personne + bufferedImage Fr + bufferedImage Ar
     * @param vo SignaturePersCpt:ContratPersonne, BufferedImage, BufferedImage 
     * @return vo SignaturePersCpt
     * @author :Ramzi
     */
    public IValueObject modifSignatures(IValueObject vo) {
        SignaturePersCpt signaturePersCpt = (SignaturePersCpt)vo;
        
        SignaturePersCpt signPersCpt = 
            (SignaturePersCpt)modifSignaturesTrt.execute(signaturePersCpt);
        return signPersCpt;
    }

    /**
     * méthode pour determiner la liste des contrats à modifier 
     * pour une personne suite à une modif sue les données de la personne
     * 
     * @param vo ParamListContratsAmodifierVo
     * @return vo ParamListContratsAmodifierVo
     * @author :Mdimagh
     * @since 4/07/2007
     */
    public IValueObject getListContratsAmodifier(IValueObject vo) {
        
        getListsContratsAmodifierTrt.setSecurityFlag(false);
        return (getListsContratsAmodifierTrt.exec(vo));
    }

    /**
     * Methode permettant d'inserer la TraceContrat dans la BD
     * @param vo : TraceMandat
     * @return TraceMandat
     */
    public

    IValueObject insertTraceContrat(IValueObject vo) {
      
        return (insertTraceContratTrt.exec(vo));
    }

    /**
     * Methode permettant d'inserer Le livret d'épargne dans la BD
     * @param vo : LivretEpargne
     * @return LivretEpargne
     */
    public

    IValueObject insertLivretEpargne(IValueObject vo) {
       
        return (insertLivretEpargneTrt.exec(vo));
    }

    /**
     * Cette  methode permet de mettre à jour le livret d'épargne 
     * @param LivretEpargne
     * @return LivretEpargne
     */
    public IValueObject MAJLivretEpargne(IValueObject vo) {
      
        return (miseAJourLivretEpargneTrt.exec(vo));
    }

    /**
     * Cette  methode permet de bloquer un contrat compte
     * @param ContratCpt
     * @return ContratCpt
     */
    public IValueObject BloquerContratCpt(IValueObject vo) {
       
        
        return (bloquerContratCptTrt.exec(vo));
    }

    /**
     * Cette  methode permet de charger les motif de blocage d'un contrat
     * @param contratABloquer
     * @return listeMotifEtat
     */
    public IValueObject chargerMotifEtat(IValueObject vo) {
        
        return (chargerMotifEtatTrt.exec(vo));
    }

    /**
     * Cette  methode permet de débloquer un contrat compte
     * @param ContratCpt
     * @return ContratCpt
     */
    public IValueObject debloquerContratCpt(IValueObject vo) {
       
        
        return (debloquerContratCptTrt.exec(vo));
    }

    /**
     * Cette  methode permet de débloquer un contrat compte
     * @param ContratCpt
     * @return ContratCpt
     */
    public IValueObject bloquerMnt(IValueObject vo) {
       
       
        return (bloquerMntTrt.exec(vo));
    }

    /**
     * Cette  methode permet de débloquer un contrat compte
     * @param ContratCpt
     * @return ContratCpt
     */
    public IValueObject chargerNatureblocage(IValueObject vo) {
      
        return (chargerNatureblocageTrt.exec(vo));
    }

    /**
     * Cette  methode permet de débloquer un contrat compte
     * @param MontantBlocage
     * @return Blocage
     */
    public IValueObject debloquerMnt(IValueObject vo) {
      
        return (deBloquerMntTrt.exec(vo));
    }

    /**
     * Cette  methode permet de débloquer un contrat compte
     * @param MontantBlocage
     * @return Blocage
     */
    public IValueObject chargerBlocages(IValueObject vo) {
       
        return (chargerBlocagesTrt.exec(vo));
    }

    /**
     * Cette  methode permet transferer un contrat à contentieux
     * @param ContratCpt
     * @return ContratCpt
     */
    public IValueObject transfertCtx(IValueObject vo) {
      
       
        return (transfertCtxTrt.exec(vo));
    }

    /**
     * Cette  methode permet de cloturer un contrat 
     * @param ContratCpt
     * @return ContratCpt
     */
    public IValueObject cloturerContrat(IValueObject vo) {
       
        return (cloturerContratTrt.exec(vo));
    }
  
  
    /**
     * Cette  methode permet de creer un contrat compte personnel en attente
     * @param paramInsertContrat
     * @return ContratCpt
     */
    public IValueObject insertComptePersonnelBna(IValueObject vo) {
       
        return (insertComptePersonnelBnaTrt.exec(vo));
    }
    
    
    public IValueObject updateContrat(IValueObject vo) {
       UpdateContratCptTrt  updateContratCptTrt = new UpdateContratCptTrt();      
       
        return (updateContratCptTrt.exec(vo));
    }
    /**
     * Cette  methode permet de retourner la liste des contrats rejetés
     * @param paramRecherche
     * @return liste ContratCpt
     */
    public IValueObject getListContratsRejetes(IValueObject vo) {
       
        return (getListeContratsRejetesTrt.exec(vo));
    }

    public void setInsertCotitulaireTrt(InsertCotitulaireTrt insertCotitulaireTrt) {
        this.insertCotitulaireTrt = insertCotitulaireTrt;
    }

    public InsertCotitulaireTrt getInsertCotitulaireTrt() {
        return insertCotitulaireTrt;
    }

    public void setInsertPersonneClientTrt(InsertPersonneClientTrt insertPersonneClientTrt) {
        this.insertPersonneClientTrt = insertPersonneClientTrt;
    }

    public InsertPersonneClientTrt getInsertPersonneClientTrt() {
        return insertPersonneClientTrt;
    }

    public void setInsertClientTrt(InsertClientTrt insertClientTrt) {
        this.insertClientTrt = insertClientTrt;
    }

    public InsertClientTrt getInsertClientTrt() {
        return insertClientTrt;
    }

    public void setInsertClientContratTrt(InsertClientContratTrt insertClientContratTrt) {
        this.insertClientContratTrt = insertClientContratTrt;
    }

    public InsertClientContratTrt getInsertClientContratTrt() {
        return insertClientContratTrt;
    }

    public void setRechercheProduitAutorisesTrt(GetProduitAutorisesTrt rechercheProduitAutorisesTrt) {
        this.rechercheProduitAutorisesTrt = rechercheProduitAutorisesTrt;
    }

    public GetProduitAutorisesTrt getRechercheProduitAutorisesTrt() {
        return rechercheProduitAutorisesTrt;
    }

    public void setChargerTypeCatPcePersonneTrt(ChargerTypeCatPcePersonneTrt chargerTypeCatPcePersonneTrt) {
        this.chargerTypeCatPcePersonneTrt = chargerTypeCatPcePersonneTrt;
    }

    public ChargerTypeCatPcePersonneTrt getChargerTypeCatPcePersonneTrt() {
        return chargerTypeCatPcePersonneTrt;
    }

    public void setChargerRgmCatEpargneTrt(ChargerRgmCatEpargneTrt chargerRgmCatEpargneTrt) {
        this.chargerRgmCatEpargneTrt = chargerRgmCatEpargneTrt;
    }

    public ChargerRgmCatEpargneTrt getChargerRgmCatEpargneTrt() {
        return chargerRgmCatEpargneTrt;
    }

    public void setCalculSoldTheorEpargneTrt(CalculSoldTheorEpargneTrt calculSoldTheorEpargneTrt) {
        this.calculSoldTheorEpargneTrt = calculSoldTheorEpargneTrt;
    }

    public CalculSoldTheorEpargneTrt getCalculSoldTheorEpargneTrt() {
        return calculSoldTheorEpargneTrt;
    }

    public void setGetNbrProduitByPersTrt(GetNbrProduitByPersTrt getNbrProduitByPersTrt) {
        this.getNbrProduitByPersTrt = getNbrProduitByPersTrt;
    }

    public GetNbrProduitByPersTrt getGetNbrProduitByPersTrt() {
        return getNbrProduitByPersTrt;
    }

    public void setChargerCatSupTrt(ChargerCatSupTrt chargerCatSupTrt) {
        this.chargerCatSupTrt = chargerCatSupTrt;
    }

    public ChargerCatSupTrt getChargerCatSupTrt() {
        return chargerCatSupTrt;
    }

    public void setGestionEpargneTrt(GestionEpargneTrt gestionEpargneTrt) {
        this.gestionEpargneTrt = gestionEpargneTrt;
    }

    public GestionEpargneTrt getGestionEpargneTrt() {
        return gestionEpargneTrt;
    }

    public void setInsertDetailEtatContratTrt(InsertDetailEtatContratTrt insertDetailEtatContratTrt) {
        this.insertDetailEtatContratTrt = insertDetailEtatContratTrt;
    }

    public InsertDetailEtatContratTrt getInsertDetailEtatContratTrt() {
        return insertDetailEtatContratTrt;
    }

    public void setInsertDetailCatContratTrt(InsertDetailCatContratTrt insertDetailCatContratTrt) {
        this.insertDetailCatContratTrt = insertDetailCatContratTrt;
    }

    public InsertDetailCatContratTrt getInsertDetailCatContratTrt() {
        return insertDetailCatContratTrt;
    }

    public void setInsertCompteLieTrt(InsertCompteLieTrt insertCompteLieTrt) {
        this.insertCompteLieTrt = insertCompteLieTrt;
    }

    public InsertCompteLieTrt getInsertCompteLieTrt() {
        return insertCompteLieTrt;
    }

    public void setTraitementValidationContratTrt(TraitementValidationContratTrt traitementValidationContratTrt) {
        this.traitementValidationContratTrt = traitementValidationContratTrt;
    }

    public TraitementValidationContratTrt getTraitementValidationContratTrt() {
        return traitementValidationContratTrt;
    }

    public void setValiderContratTrt(ValiderContratTrt validerContratTrt) {
        this.validerContratTrt = validerContratTrt;
    }

    public ValiderContratTrt getValiderContratTrt() {
        return validerContratTrt;
    }

    public void setVerifDateLimiteTrt(VerifDateLimiteTrt verifDateLimiteTrt) {
        this.verifDateLimiteTrt = verifDateLimiteTrt;
    }

    public VerifDateLimiteTrt getVerifDateLimiteTrt() {
        return verifDateLimiteTrt;
    }

    public void setRejeterContratTrt(RejeterContratTrt rejeterContratTrt) {
        this.rejeterContratTrt = rejeterContratTrt;
    }

    public RejeterContratTrt getRejeterContratTrt() {
        return rejeterContratTrt;
    }

    public void setInsertSignaturesTrt(InsertSignaturesTrt insertSignaturesTrt) {
        this.insertSignaturesTrt = insertSignaturesTrt;
    }

    public InsertSignaturesTrt getInsertSignaturesTrt() {
        return insertSignaturesTrt;
    }

    public void setMiseAJourDetailCatContratTrt(MiseAJourDetailCatContratTrt miseAJourDetailCatContratTrt) {
        this.miseAJourDetailCatContratTrt = miseAJourDetailCatContratTrt;
    }

    public MiseAJourDetailCatContratTrt getMiseAJourDetailCatContratTrt() {
        return miseAJourDetailCatContratTrt;
    }

    public void setGetSignaturesTrt(GetSignaturesTrt getSignaturesTrt) {
        this.getSignaturesTrt = getSignaturesTrt;
    }

    public GetSignaturesTrt getGetSignaturesTrt() {
        return getSignaturesTrt;
    }

    public void setModifSignaturesTrt(ModifSignaturesTrt modifSignaturesTrt) {
        this.modifSignaturesTrt = modifSignaturesTrt;
    }

    public ModifSignaturesTrt getModifSignaturesTrt() {
        return modifSignaturesTrt;
    }

    public void setGetListsContratsAmodifierTrt(GetListeContratsAmodifierTrt getListsContratsAmodifierTrt) {
        this.getListsContratsAmodifierTrt = getListsContratsAmodifierTrt;
    }

    public GetListeContratsAmodifierTrt getGetListsContratsAmodifierTrt() {
        return getListsContratsAmodifierTrt;
    }

    public void setInsertTraceContratTrt(InsertTraceContratTrt insertTraceContratTrt) {
        this.insertTraceContratTrt = insertTraceContratTrt;
    }

    public InsertTraceContratTrt getInsertTraceContratTrt() {
        return insertTraceContratTrt;
    }

    public void setInsertLivretEpargneTrt(InsertLivretEpargneTrt insertLivretEpargneTrt) {
        this.insertLivretEpargneTrt = insertLivretEpargneTrt;
    }

    public InsertLivretEpargneTrt getInsertLivretEpargneTrt() {
        return insertLivretEpargneTrt;
    }

    public void setBloquerContratCptTrt(BloquerContratCptTrt bloquerContratCptTrt) {
        this.bloquerContratCptTrt = bloquerContratCptTrt;
    }

    public BloquerContratCptTrt getBloquerContratCptTrt() {
        return bloquerContratCptTrt;
    }

    public void setChargerNatureblocageTrt(ChargerNatureblocageTrt chargerNatureblocageTrt) {
        this.chargerNatureblocageTrt = chargerNatureblocageTrt;
    }

    public ChargerNatureblocageTrt getChargerNatureblocageTrt() {
        return chargerNatureblocageTrt;
    }

    public void setDeBloquerMntTrt(DebloquerMntTrt deBloquerMntTrt) {
        this.deBloquerMntTrt = deBloquerMntTrt;
    }

    public DebloquerMntTrt getDeBloquerMntTrt() {
        return deBloquerMntTrt;
    }

    public void setChargerBlocagesTrt(ChargerBlocagesTrt chargerBlocagesTrt) {
        this.chargerBlocagesTrt = chargerBlocagesTrt;
    }

    public ChargerBlocagesTrt getChargerBlocagesTrt() {
        return chargerBlocagesTrt;
    }

    public void setTransfertCtxTrt(TransfertCtxTrt transfertCtxTrt) {
        this.transfertCtxTrt = transfertCtxTrt;
    }

    public TransfertCtxTrt getTransfertCtxTrt() {
        return transfertCtxTrt;
    }

    public void setCloturerContratTrt(CloturerContratTrt cloturerContratTrt) {
        this.cloturerContratTrt = cloturerContratTrt;
    }

    public CloturerContratTrt getCloturerContratTrt() {
        return cloturerContratTrt;
    }

    public void setInsertComptePersonnelBnaTrt(InsertComptePersonnelBnaTrt insertComptePersonnelBnaTrt) {
        this.insertComptePersonnelBnaTrt = insertComptePersonnelBnaTrt;
    }

    public InsertComptePersonnelBnaTrt getInsertComptePersonnelBnaTrt() {
        return insertComptePersonnelBnaTrt;
    }

    public void setGetListeContratsRejetesTrt(GetListeContratsRejetesTrt getListeContratsRejetesTrt) {
        this.getListeContratsRejetesTrt = getListeContratsRejetesTrt;
    }

    public GetListeContratsRejetesTrt getGetListeContratsRejetesTrt() {
        return getListeContratsRejetesTrt;
    }

    public void setBloquerMntTrt(BloquerMntTrt bloquerMntTrt) {
        this.bloquerMntTrt = bloquerMntTrt;
    }

    public BloquerMntTrt getBloquerMntTrt() {
        return bloquerMntTrt;
    }

    public void setDebloquerContratCptTrt(DebloquerContratCptTrt debloquerContratCptTrt) {
        this.debloquerContratCptTrt = debloquerContratCptTrt;
    }

    public DebloquerContratCptTrt getDebloquerContratCptTrt() {
        return debloquerContratCptTrt;
    }

    public void setMiseAJourLivretEpargneTrt(MiseAJourLivretEpargneTrt miseAJourLivretEpargneTrt) {
        this.miseAJourLivretEpargneTrt = miseAJourLivretEpargneTrt;
    }

    public MiseAJourLivretEpargneTrt getMiseAJourLivretEpargneTrt() {
        return miseAJourLivretEpargneTrt;
    }

    public void setChargerMotifEtatTrt(ChargerMotifEtatTrt chargerMotifEtatTrt) {
        this.chargerMotifEtatTrt = chargerMotifEtatTrt;
    }

    public ChargerMotifEtatTrt getChargerMotifEtatTrt() {
        return chargerMotifEtatTrt;
    }

    public void setMAJContratClientTransfertEpargneTrt(MAJContratClientTransfertEpargneTrt mAJContratClientTransfertEpargneTrt) {
        this.mAJContratClientTransfertEpargneTrt = mAJContratClientTransfertEpargneTrt;
    }

    public MAJContratClientTransfertEpargneTrt getMAJContratClientTransfertEpargneTrt() {
        return mAJContratClientTransfertEpargneTrt;
    }

    public void setInsertClientContCompteTrt(InsertClientContCompteTrt insertClientContCompteTrt) {
        this.insertClientContCompteTrt = insertClientContCompteTrt;
    }

   

    public InsertClientContCompteTrt getInsertClientContCompteTrt() {
        return insertClientContCompteTrt;
    }


 
}
