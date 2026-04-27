package com.bna.smile.model.domainecontratcompte.procuration.traitement;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.bna.commun.model.Mandat;
import com.bna.commun.model.MandatOperation;
import com.bna.commun.model.MandatPersonne;
import com.bna.commun.model.Personne;
import com.bna.commun.util.ContextHandler;
import com.bna.commun.util.DateHandler;
import com.bna.smile.model.constant.Constants;
import com.bna.smile.model.domainecommun.model.ContratCptMandat;
import com.bna.smile.model.domainecommun.model.PersonneStrc;
import com.bna.smile.model.domainecommun.service.ContratCompteService;
import com.bna.smile.model.domainecommun.service.PersonneService;
import com.bna.smile.model.domainecommun.traitement.GetPersonneTrt;
import com.bna.smile.model.domainecontratcompte.procuration.model.DetailMandat;
import com.bna.smile.model.domainecontratcompte.procuration.model.ListMandatOperationVo;
import com.bna.smile.model.domainecontratcompte.procuration.model.MandatRecherche;
import com.bna.smile.model.domainecontratcompte.procuration.model.ParamMandatOperationVo;
import com.oxia.fwk.context.Context;
import com.oxia.fwk.core.ICriteria;
import com.oxia.fwk.core.IExpression;
import com.oxia.fwk.core.ISearchEngine;
import com.oxia.fwk.core.ValueObject;
import com.oxia.fwk.searchengine.SearchEngine;

/**
 * Classe de traitement :permet de donner la liste
 * des mandat operation et la liste des mandats pour un contrat donné, une personne et pour une operation
 * @author Mdimagh Mohamed Lassaad
 * 
 * 
 */
public class GetListMandatOperationPersonneContratOperationTrt {
    public GetListMandatOperationPersonneContratOperationTrt() {
    }
    
    public ValueObject execute(ValueObject vo) {
        ParamMandatOperationVo      paramMandatOperationVo      = (ParamMandatOperationVo)vo;
        ListMandatOperationVo       listMandatOperationVo       = new ListMandatOperationVo();
        List ListDesMandatPourPersonne =  new ArrayList();
        List listDesMandatGeneraux = new ArrayList();
        List listDesMandatSpeciaux = new ArrayList();
        
        try{
        Context context = ContextHandler.getContext();
        ISearchEngine searchEngine = (SearchEngine)context.getBean("searchEngine"); 
        ICriteria   criteria       = searchEngine.createCriteria();
        IExpression expression     = searchEngine.createExpression();

        //--------------------------------------------------
        //---- Recherche la personne
        //--------------------------------------------------
        
        Personne personne = new Personne();
        PersonneStrc personneS = (PersonneStrc) paramMandatOperationVo.getPersonneStrc();
        
        PersonneService PersonneService = (PersonneService)context.getBean("PersonneService");
        GetPersonneTrt getPersonne = new GetPersonneTrt();
        
        personne = (Personne) getPersonne.exec(paramMandatOperationVo.getPersonneStrc());
        
        if (personne == null ){ // si la personne n'existe pas
            
        }else { // si la personne existe 
            
            //--------------------------------------------------
            //---- Recherche les mandats valide pour ce contrat
            //--------------------------------------------------
            
            ContratCompteService contratCompteService = (ContratCompteService)context.getBean("contratCompteService");
            MandatRecherche mandatRecherche =new MandatRecherche();
            mandatRecherche.setContratCptId(paramMandatOperationVo.getContraCptId());
            mandatRecherche.setCodEtat("V");
            mandatRecherche.setCodMenu("RP");
            ContratCptMandat contratCptMandat = (ContratCptMandat) contratCompteService.GetContratMandat(mandatRecherche);
                if (contratCptMandat.getListeMandat() == null){ // si aucun mandat n'est lié a ce contrat 
                
                }else{ // le contrat posséde des mandats
                  
                 //--------------------------------------------------
                 //---- determiner les mandats où la personne participe
                 //--------------------------------------------------
                  
                  // parcourir les mandats de ce contrat
                  for (Iterator itMandat = contratCptMandat.getListeMandat().iterator(); itMandat.hasNext();){
                    Mandat mandat=(Mandat)itMandat.next();
                    if(mandat.getCodEtatMand().equals("V") && (mandat.getDatFinMand() == null || !mandat.getDatFinMand().before(DateHandler.strToDate(DateHandler.dateJour())) )){
                          
                          //System.out.println(mandat.getMandatPersonnes());
                          // parcourir les mandatsPersonnes
                          
                          DetailMandatTrt detailMandatTrt = new DetailMandatTrt();
                          DetailMandat detaimMandat = (DetailMandat)detailMandatTrt.exec(mandat);
                      
                          for (Iterator itMandatPersonneValide = detaimMandat.getListeMandatPersonnes().iterator(); itMandatPersonneValide.hasNext();){
                           MandatPersonne mandatPersonne = (MandatPersonne) itMandatPersonneValide.next();
                           
                           // si la personne participe à ce mandat 
                           if (mandatPersonne.getPersonne().getNumSeqPers().equals(personne.getNumSeqPers()) &&
                               mandatPersonne.getCodEtatMp().equals(Constants.COD_ETAT_MAND_PERSONNE_VALID) ){
                            ListDesMandatPourPersonne.add(mandat);
                               //----------------------------------------------
                               //-- remplir la liste par les mandat généraux
                               if (mandat.getCodTypMand().equals(Constants.COD_TYPE_MAND_GENERAL)){
                                   listDesMandatGeneraux.add(mandat);
                               //----------------------------------------------
                               //-- remplir la liste par les mandat spéciaux
                               }else{
                                   if(paramMandatOperationVo.getOperation()== null ){
                                       listDesMandatSpeciaux = detaimMandat.getListeMandatOperations() ;
                                   }else{
                                       for (Iterator itMandatOperation = detaimMandat.getListeMandatOperations().iterator(); itMandatOperation.hasNext();){
                                       MandatOperation  mandatOperation =  (MandatOperation) itMandatOperation.next();
                                         if (mandatOperation.getMandatOperationId().getCodOperOper().equals(paramMandatOperationVo.getOperation().getCodOperOper())){
                                            if (! verifExistanceMandatDansList(listDesMandatSpeciaux,mandat)){
                                             listDesMandatSpeciaux.add(mandat);
                                            }
                                         }
                                       } // fin for mandatOperation
                                   }// fin else 
                               } // fin else mandat special
                                                 
                           } 
                          } // fin des mandatspersonne valide
                  }// fin for des mandats par contrat
                  }//fin test validité mandat      
                } // fin else contrat possede un mandat
        }// fin personne existe
        
        listMandatOperationVo.setListMandatsGeneraux(listDesMandatGeneraux);
        listMandatOperationVo.setListMandatsSpeciauxOperations(listDesMandatSpeciaux);
        
        return(listMandatOperationVo);
        }catch(Exception e){
           com.oxia.fwk.core.Error erreur=new com.oxia.fwk.core.Error();
           erreur.setCode("9999");
           erreur.setDescription("GetListMandatOperationPersonneContratOperationTrt "+e.getMessage());;
           
           listMandatOperationVo.addError(erreur);
           return(listMandatOperationVo);
        
        }
    }
    
    public boolean verifExistanceMandatDansList(List listMandat,Mandat mandatAverif){
         boolean test =false;
         for(Iterator it = listMandat.iterator(); it.hasNext(); ){
             Mandat mandat = (Mandat) it.next();
             if (mandat.getNumMandMand().equals(mandatAverif.getNumMandMand())){
                 test = true;
             }
          }
         return test;
     } 
    
}
