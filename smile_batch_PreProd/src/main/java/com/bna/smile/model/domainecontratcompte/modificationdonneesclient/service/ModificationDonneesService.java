package com.bna.smile.model.domainecontratcompte.modificationdonneesclient.service;


import com.bna.commun.util.ContextHandler;
import com.bna.smile.model.domainecontratcompte.modificationdonneesclient.model.ParamModificationDonneesVo;
import com.bna.smile.model.domainecontratcompte.modificationdonneesclient.model.ParamRechercheModificationDonneesVo;
import com.bna.smile.model.domainecontratcompte.modificationdonneesclient.traitement.CorrectionDonneesClientTrt;
import com.bna.smile.model.domainecontratcompte.modificationdonneesclient.traitement.GetListPieceAnnexeParNumSeqPersTrt;
import com.bna.smile.model.domainecontratcompte.modificationdonneesclient.traitement.GetListeDesMineursDevenusMajeursTrt;
import com.bna.smile.model.domainecontratcompte.modificationdonneesclient.traitement.GetModificationDonneesClientTrt;
import com.bna.smile.model.domainecontratcompte.modificationdonneesclient.traitement.ModificationIntituleCompteTrt;
import com.bna.smile.model.domainecontratcompte.modificationdonneesclient.traitement.ModificationTypeCompteTrt;
import com.bna.smile.model.domainecontratcompte.modificationdonneesclient.traitement.ModifierDonneesClientTrt;
import com.bna.smile.model.domainecontratcompte.modificationdonneesclient.traitement.ModifierQualitePersonneTrt;
import com.bna.smile.model.domainecontratcompte.modificationdonneesclient.traitement.RecherchePersonneParNomTrt;
import com.oxia.fwk.context.Context;
import com.oxia.fwk.core.IValueObject;

public class ModificationDonneesService {

private  ModifierQualitePersonneTrt modifierQualitePersonneTrt;
private  ModifierDonneesClientTrt modifierDonneesClientTrt ;
private  GetModificationDonneesClientTrt getModificationDonneesClientTrt ;
private  GetListeDesMineursDevenusMajeursTrt getListeDesMineur ;
private  RecherchePersonneParNomTrt rechercheNom; 
private  GetListPieceAnnexeParNumSeqPersTrt GetList ;
private  CorrectionDonneesClientTrt correctionDonneesClient ;
private  ModificationIntituleCompteTrt modificationIntituleCompteTrt ;
private  ModificationTypeCompteTrt modificationTypeCompteTrt; 
    public Context context = ContextHandler.getContext();

    public ModificationDonneesService() {
    }

    /**
     * methode qui permet de modifier les données client
     * @param ParamModificationDonneesVo : l'objet de parametre de modification
     * @return  ParamModificationDonneesVo
     */
    public IValueObject modifierDonneesClient(IValueObject vo) {
        ParamModificationDonneesVo paramModificationDonneesVo = 
            (ParamModificationDonneesVo)vo;
        
        modifierDonneesClientTrt.setSecurityFlag(false);
        paramModificationDonneesVo = 
                (ParamModificationDonneesVo)modifierDonneesClientTrt.exec (paramModificationDonneesVo);
        return (paramModificationDonneesVo);
    }

    /**
     * methode qui permet l'execution de la recherche des modfifcation des données client
     * @param ParamRechercheModificationDonneesVo : l'objet de parametre de recherche de modification
     * @return  ParamRechercheModificationDonneesVo
     */
    public IValueObject getModificationDonneesClient(IValueObject vo) {
        ParamRechercheModificationDonneesVo paramRechercheModificationDonneesVo = 
            (ParamRechercheModificationDonneesVo)vo;
       
        getModificationDonneesClientTrt.setSecurityFlag(false);
        paramRechercheModificationDonneesVo = 
                (ParamRechercheModificationDonneesVo)getModificationDonneesClientTrt.exec(paramRechercheModificationDonneesVo);
        return (paramRechercheModificationDonneesVo);
    }

    /**
     * methode qui permet l'execution de la modification des relation d'un client avec d'autre personne
     * @param ParamListPersonneQualiteClientVo : l'objet de parametre de recherche de modification
     * @return  ParamListPersonneQualiteClientVo
     */
    public IValueObject modifierQualitePersonne(IValueObject vo) {
        return (modifierQualitePersonneTrt.exec(vo));
    }
    /**
     * methode qui permet de chercher les personnes mineurs devenus majeurs d'une structure
     * @param vo
     * @return
     * @author Mdimagh Med Lassaad
     * @since 23/05/2008
     */
    public IValueObject getListeMineursDevenusMajeurs(IValueObject vo) {
    
       getListeDesMineur.setSecurityFlag(false);
       return (getListeDesMineur.exec(vo));
    }
    
    /**
     * methode qui permet de chercher les personnes par leur nom prenom raison sociale ou sigle
     * @param vo
     * @return
     * @author Mdimagh Med Lassaad
     * @since 28/05/2008
     */
    public IValueObject RecherchePersonneParNom(IValueObject vo) {
       
       rechercheNom.setSecurityFlag(false);
       return (rechercheNom.exec(vo));
    }
    
    /**
     * methode qui permet de chercher la liste des pièces annexes d'une personne
     * @param vo
     * @return
     * @author Mdimagh Med Lassaad
     * @since 02/06/2008
     */
    public IValueObject getListeDesPiecesAnnexes(IValueObject vo) {
       
       GetList.setSecurityFlag(false);
       return (GetList.exec(vo));
    }
    
    /**
     * methode qui permet de faire les correction des données d'une personne
     * @param vo
     * @return
     * @author Mdimagh Med Lassaad
     * @since 23/07/2008
     */
    public IValueObject correctionDonneesClient(IValueObject vo) {
       
       correctionDonneesClient.setSecurityFlag(false);
       return (correctionDonneesClient.exec(vo));
    }
    
    /**
     * methode qui permet de faire la modification de l'intitulé d'un compte
     * @param vo
     * @return
     * @author Mdimagh Med Lassaad
     * @since 06/10/2008
     */
    public IValueObject modificationIntituleCompte(IValueObject vo) {
       
       return (modificationIntituleCompteTrt.exec(vo));
    }
    
    /**
     * methode qui permet de faire la modification du type de compte
     * @param vo
     * @return
     * @author Mdimagh Med Lassaad
     * @since 06/10/2008
     */
    public IValueObject modificationTypeCompte(IValueObject vo) {
       
       return (modificationTypeCompteTrt.exec(vo));
    }

    public void setModifierQualitePersonneTrt(ModifierQualitePersonneTrt modifierQualitePersonneTrt) {
        this.modifierQualitePersonneTrt = modifierQualitePersonneTrt;
    }

    public ModifierQualitePersonneTrt getModifierQualitePersonneTrt() {
        return modifierQualitePersonneTrt;
    }

    public void setModifierDonneesClientTrt(ModifierDonneesClientTrt modifierDonneesClientTrt) {
        this.modifierDonneesClientTrt = modifierDonneesClientTrt;
    }

    public ModifierDonneesClientTrt getModifierDonneesClientTrt() {
        return modifierDonneesClientTrt;
    }

    public void setGetModificationDonneesClientTrt(GetModificationDonneesClientTrt getModificationDonneesClientTrt) {
        this.getModificationDonneesClientTrt = getModificationDonneesClientTrt;
    }

    public GetModificationDonneesClientTrt getGetModificationDonneesClientTrt() {
        return getModificationDonneesClientTrt;
    }

    public void setGetListeDesMineur(GetListeDesMineursDevenusMajeursTrt getListeDesMineur) {
        this.getListeDesMineur = getListeDesMineur;
    }

    public GetListeDesMineursDevenusMajeursTrt getGetListeDesMineur() {
        return getListeDesMineur;
    }

    public void setRechercheNom(RecherchePersonneParNomTrt rechercheNom) {
        this.rechercheNom = rechercheNom;
    }

    public RecherchePersonneParNomTrt getRechercheNom() {
        return rechercheNom;
    }

    public void setGetList(GetListPieceAnnexeParNumSeqPersTrt getList) {
        this.GetList = getList;
    }

    public GetListPieceAnnexeParNumSeqPersTrt getGetList() {
        return GetList;
    }

    public void setCorrectionDonneesClient(CorrectionDonneesClientTrt correctionDonneesClient) {
        this.correctionDonneesClient = correctionDonneesClient;
    }

    public CorrectionDonneesClientTrt getCorrectionDonneesClient() {
        return correctionDonneesClient;
    }

    public void setModificationIntituleCompteTrt(ModificationIntituleCompteTrt modificationIntituleCompteTrt) {
        this.modificationIntituleCompteTrt = modificationIntituleCompteTrt;
    }

    public ModificationIntituleCompteTrt getModificationIntituleCompteTrt() {
        return modificationIntituleCompteTrt;
    }

    public void setModificationTypeCompteTrt(ModificationTypeCompteTrt modificationTypeCompteTrt) {
        this.modificationTypeCompteTrt = modificationTypeCompteTrt;
    }

    public ModificationTypeCompteTrt getModificationTypeCompteTrt() {
        return modificationTypeCompteTrt;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public Context getContext() {
        return context;
    }
}
