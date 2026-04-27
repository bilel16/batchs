package com.bna.smile.model.domainecommun.service;


import com.bna.smile.model.domainecommun.traitement.GetContratCptByIdTrt;
import com.bna.smile.model.domainecommun.traitement.GetContratEtatTrt;


import com.bna.smile.model.domainecommun.traitement.GetContratMandatTrt;

import com.bna.smile.model.domainecommun.traitement.GetDetailCompteInterneTrt;
import com.bna.smile.model.domainecommun.traitement.GetDetailContratTrt;
import com.bna.smile.model.domainecommun.traitement.GetEntiteCotitByContratTrt;
import com.bna.smile.model.domainecommun.traitement.GetListCredTrt;
import com.bna.smile.model.domainecommun.traitement.GetTuteurTrt;

import com.bna.smile.model.domainecontratcompte.souscriptioncontratcompte.traitement.GetCategorieContratTrt;

import com.bna.smile.model.domainecontratcompte.souscriptioncontratcompte.traitement.GetPersClientTrt;

import com.oxia.fwk.beans.service.BasicService;
import com.oxia.fwk.core.IValueObject;
import com.oxia.fwk.core.ValueObject;

public class ContratCompteService extends BasicService{

private    GetDetailContratTrt getDetailContratTrt;
private    GetContratCptByIdTrt getContratCptByIdTrtTrt;
private    GetContratMandatTrt getContratMandatTrt;
private    GetEntiteCotitByContratTrt getEntiteCotitByContratTrt ;
private    GetPersClientTrt getPersClientTrt;
private    GetCategorieContratTrt getCategorieContratTrt;
private    GetContratEtatTrt getContratEtatTrt;
private    GetTuteurTrt getTuteurTrt;
private    GetDetailCompteInterneTrt getDetailCompteInterneTrt;

    public ContratCompteService() {
    }

    /** méthode pour la recherche d'un tuteur et la liste des mineures en charge
     * @param  ValueObject : IdentifiantPersonneVo : l'identifiant de lapersonne
     * @return ValueObject : TuteurVo :La personne tuteur et la liste de ses mineures
     */
    public

    IValueObject getTuteur(IValueObject vo) {

       
        return (getTuteurTrt.exec(vo));
    }


    /**
     * methode permettant l'affichage des informations sur un contrat donné
     * ainsi que le liste des mandats valides sur ce contrat
     * @param vo : IdContratCpt
     * @return ContratCptMandat
     */
    public IValueObject GetContratMandat(IValueObject vo) {

       
        return (getContratMandatTrt.exec(vo));
    }


    public IValueObject GetDetailContrat(IValueObject vo) {
        return (getDetailContratTrt.exec(vo));
    }


    public IValueObject GetEntiteCotitByContrat(IValueObject vo) {

      
        return (getEntiteCotitByContratTrt.exec(vo));
    }


    public IValueObject GetPersClient(IValueObject vo) {
       
        return (getPersClientTrt.exec(vo));
    }

    public IValueObject GetCategorieContrat(IValueObject vo) {
       
        return (getCategorieContratTrt.exec(vo));
    }
    /**
     * methode permettant l'affichage des informations sur un contrat donné
     * ainsi que le liste des mandats valides sur ce contrat
     * @param vo : IdContratCpt
     * @return ContratCptMandat
     */
    public IValueObject GetContratEtat(IValueObject vo) {
        return (getContratEtatTrt.exec(vo));
    }
    /**
     * Methode de recheche d'un contrat par sa clé
     * @param ContratCpt
     * @return ContratCpt
     */
    public IValueObject GetContratCptById(IValueObject vo) {
      
        return (getContratCptByIdTrtTrt.exec(vo));
    }


    public void setGetDetailContratTrt(GetDetailContratTrt getDetailContratTrt) {
        this.getDetailContratTrt = getDetailContratTrt;
    }

    public GetDetailContratTrt getGetDetailContratTrt() {
        return getDetailContratTrt;
    }

    public void setGetContratCptByIdTrtTrt(GetContratCptByIdTrt getContratCptByIdTrtTrt) {
        this.getContratCptByIdTrtTrt = getContratCptByIdTrtTrt;
    }

    public GetContratCptByIdTrt getGetContratCptByIdTrtTrt() {
        return getContratCptByIdTrtTrt;
    }

    public void setGetContratMandatTrt(GetContratMandatTrt getContratMandatTrt) {
        this.getContratMandatTrt = getContratMandatTrt;
    }

    public GetContratMandatTrt getGetContratMandatTrt() {
        return getContratMandatTrt;
    }

    public void setGetEntiteCotitByContratTrt(GetEntiteCotitByContratTrt getEntiteCotitByContratTrt) {
        this.getEntiteCotitByContratTrt = getEntiteCotitByContratTrt;
    }

    public GetEntiteCotitByContratTrt getGetEntiteCotitByContratTrt() {
        return getEntiteCotitByContratTrt;
    }

    public void setGetPersClientTrt(GetPersClientTrt getPersClientTrt) {
        this.getPersClientTrt = getPersClientTrt;
    }

    public GetPersClientTrt getGetPersClientTrt() {
        return getPersClientTrt;
    }

    public void setGetCategorieContratTrt(GetCategorieContratTrt getCategorieContratTrt) {
        this.getCategorieContratTrt = getCategorieContratTrt;
    }

    public GetCategorieContratTrt getGetCategorieContratTrt() {
        return getCategorieContratTrt;
    }

    public void setGetContratEtatTrt(GetContratEtatTrt getContratEtatTrt) {
        this.getContratEtatTrt = getContratEtatTrt;
    }

    public GetContratEtatTrt getGetContratEtatTrt() {
        return getContratEtatTrt;
    }

    public void setGetTuteurTrt(GetTuteurTrt getTuteurTrt) {
        this.getTuteurTrt = getTuteurTrt;
    }

    public GetTuteurTrt getGetTuteurTrt() {
        return getTuteurTrt;
    }
    
    public IValueObject GetDetailCptInterne(IValueObject vo) {
      
        return (getDetailCompteInterneTrt.exec(vo));
    }


    public void setGetDetailCompteInterneTrt(GetDetailCompteInterneTrt getDetailCompteInterneTrt) {
        this.getDetailCompteInterneTrt = getDetailCompteInterneTrt;
    }

    public GetDetailCompteInterneTrt getGetDetailCompteInterneTrt() {
        return getDetailCompteInterneTrt;
    }
    public IValueObject getListCredit(IValueObject vo) {
        GetListCredTrt getListCredTrt=new GetListCredTrt();
        return (getListCredTrt.exec(vo));
    }
}
