package com.bna.smile.model.domainecommun.service;

import com.bna.commun.model.Client;

import com.bna.commun.model.ContratCpt;
import com.bna.commun.util.ContextHandler;

import com.bna.smile.model.domainecommun.model.ParamListPersonneQualiteClientVo;
import com.bna.smile.model.domainecommun.traitement.GetNombreContratParClientTrt;
import com.bna.smile.model.domainecommun.traitement.GetPersonneClientQualiteTrt;
import com.bna.smile.model.domainecontratcompte.souscriptioncontratcompte.model.ParamCompteLie;
import com.bna.smile.model.domainecontratcompte.souscriptioncontratcompte.traitement.InsertCompteLieTrt;

import com.oxia.fwk.beans.service.BasicService;
import com.oxia.fwk.context.Context;
import com.oxia.fwk.core.ICriteria;
import com.oxia.fwk.core.IExpression;
import com.oxia.fwk.core.ISearchEngine;
import com.oxia.fwk.core.IValueObject;
import com.oxia.fwk.core.ValueObject;
import com.oxia.fwk.searchengine.SearchEngine;

import java.util.List;

import org.springframework.orm.hibernate3.HibernateTemplate;

public class ClientService extends BasicService{
    public Context context = ContextHandler.getContext();
    private GetNombreContratParClientTrt getNombreContratParClientTrt;
    private GetPersonneClientQualiteTrt getPersonneClientQualiteTrt;

    public ClientService() {
    }

    /**
     * methode qui verifie l'existance du client
     * @param numeroPersonne :numero sequentiel de la personne
     * @return boolen true s'il existe
     */
     public boolean verifierExistanceClient(Long numeroPersonne) {

         ISearchEngine searchEngine = 
             (SearchEngine)context.getBean("searchEngine");
         ICriteria criteria = searchEngine.createCriteria();
         IExpression expression = searchEngine.createExpression();

         criteria.add(expression.eq("numSeqPers", numeroPersonne));
         if (numeroPersonne != null) {
             List l = searchEngine.find(Client.class, criteria);
            
             if (l != null && l.size() > 0) {
                 HibernateTemplate  hibernateTemplate = (HibernateTemplate) context.getBean("hibernateTemplate");
                 hibernateTemplate.evict(l.get(0));
                 return (true);
             } else {
                 return (false);
             }            
            
             
         } else {
             return (false);
         }
         
         
     }

    /**
     * cette methode permet de retourner le nombre de contrat valides
     * @param vo ParamCompteLie
     * @return ParamCompteLie
     */
    public IValueObject getNombreContratParClient(IValueObject vo) {      
        return getNombreContratParClientTrt.exec(vo);
        
    }
    
    /**
     * cette methode permet de retourner la liste des personnes qui sont en relationa avec 
     * un client pour une qualité donnée
     * @param vo ParamListQualiteClientVo
     * @return ParamListQualiteClientVo
     */
    public IValueObject  getPersonneClientQualite(IValueObject vo) {
        return ( getPersonneClientQualiteTrt.exec(vo));
    }

    public void setGetNombreContratParClientTrt(GetNombreContratParClientTrt getNombreContratParClientTrt) {
        this.getNombreContratParClientTrt = getNombreContratParClientTrt;
    }

    public GetNombreContratParClientTrt getGetNombreContratParClientTrt() {
        return getNombreContratParClientTrt;
    }

    public void setGetPersonneClientQualiteTrt(GetPersonneClientQualiteTrt getPersonneClientQualiteTrt) {
        this.getPersonneClientQualiteTrt = getPersonneClientQualiteTrt;
    }

    public GetPersonneClientQualiteTrt getGetPersonneClientQualiteTrt() {
        return getPersonneClientQualiteTrt;
    }
}
