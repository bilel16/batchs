package com.bna.smile.model.domainecommun.traitement;

import com.bna.commun.model.Client;
import com.bna.commun.model.ContratCpt;
import com.bna.commun.model.MandatPersonne;
import com.bna.commun.model.Personne;
import com.bna.commun.traitements.Traitement;
import com.bna.commun.util.DateHandler;
import com.bna.commun.util.ContextHandler;

import com.bna.smile.model.constant.Constants;
import com.bna.smile.model.domainecommun.model.Listes;
import com.bna.smile.model.domainecommun.model.PersonneStrc;

import com.oxia.fwk.context.Context;
import com.oxia.fwk.core.ICriteria;
import com.oxia.fwk.core.IExpression;
import com.oxia.fwk.core.ISearchEngine;
import com.oxia.fwk.core.IValueObject;
import com.oxia.fwk.core.ValueObject;
import com.oxia.fwk.searchengine.SearchEngine;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

public class GetListContratMandataireTrt extends Traitement {
    public GetListContratMandataireTrt() {
    }
     /** Méthode qui permet d'extraire les contrats valides sur les quelles il est mandataire
      * @author Ramzi
      * @since  30/04/2007
      * @param VO:PersonneStrc contenant type piece, num piece de l'entité
      * @return VO:Liste des contrats valides sur les quelles il est mandataire
      */
    public IValueObject perform (IValueObject vo) {
       
        PersonneStrc personneStrc = (PersonneStrc)vo;
        Context context = ContextHandler.getContext();
        Listes listes = new Listes();
      try{  
        ISearchEngine searchEngine = 
            (SearchEngine)context.getBean("searchEngine");
        ICriteria criteriaCpt = searchEngine.createCriteria();
        IExpression expression = searchEngine.createExpression();
        GetPersonneTrt getPersonneTrt = new GetPersonneTrt();
        

       
        List listMandPers = new ArrayList();
        List listContratValidMand = new ArrayList();
        Client client = new Client();
        Personne personne = new Personne();
        List listMandats = new ArrayList();

        personne = (Personne)getPersonneTrt.exec(personneStrc);
        Long numSeqPers=personne.getNumSeqPers();
        
        if (numSeqPers != null) {
            criteriaCpt.add(expression.eq("codEtatMp", "V"));
            criteriaCpt.add(expression.eq("personne.numSeqPers", numSeqPers));
           
             listMandPers = searchEngine.find(MandatPersonne.class, criteriaCpt);
            for (Iterator it = listMandPers.iterator();it.hasNext(); ) {
                MandatPersonne mandPers=(MandatPersonne)it.next();
                Date dateDebutMandat=DateHandler.strToDate(DateHandler.dateToStr(mandPers.getMandat().getDatDebMand()));
                Date dateFinMandat=DateHandler.strToDate(DateHandler.dateToStr(mandPers.getMandat().getDatFinMand()));
                Date dateJour=DateHandler.strToDate(DateHandler.dateJour());
            ///verifier validiter mandat, date debut mandat, date fin mandat, validiter contrat
                if(mandPers.getMandat().getCodEtatMand().equals("V") && dateDebutMandat.compareTo(dateJour)<=0 && (dateFinMandat==null || dateFinMandat.compareTo(dateJour)>=0) && mandPers.getMandat().getContratCpt().getCodEtatCcpt().equals("V")){
                    //---------------------------------------------------------------------------------//
                    //----- Verifier si le contrat a été déja saisi (verification de l'uniciter )------//
                    boolean test = false;
                     for (Iterator ite = listContratValidMand.iterator(); ite.hasNext(); ) {
                        ContratCpt contrat =(ContratCpt) ite.next();
                      if ( contrat.getContratCptId().getCodStrcStrc().equals(mandPers.getMandat().getContratCpt().getContratCptId().getCodStrcStrc()) &&
                           contrat.getContratCptId().getCodPrdPrd().equals(mandPers.getMandat().getContratCpt().getContratCptId().getCodPrdPrd()) &&
                           contrat.getContratCptId().getNumCcptCcpt().equals(mandPers.getMandat().getContratCpt().getContratCptId().getNumCcptCcpt()) 
                            ){
                            test = true  ;
                      }else{
                            test = false ;
                      }
                     }
                    
                    //----------- si le contrat n'a pas été inséré, il faut l'ajouter
                    if(test == false){ 
                        if (personneStrc.getCasModificationDonnees()!=null && personneStrc.getCasModificationDonnees().equals("OUI")){
                          if (mandPers.getMandat().getContratCpt().getContratCptId().getCodStrcStrc().equals(personneStrc.getCodStrcStrc())){
                              listContratValidMand.add(mandPers.getMandat().getContratCpt());     
                          }
                            
                        }else{
                             listContratValidMand.add(mandPers.getMandat().getContratCpt());   
                        }
                        
                        if (personneStrc.getCasNotificationDeces()!=null && personneStrc.getCasNotificationDeces().equals("true")){
                            listMandats.add(mandPers.getMandat());
                        }
                    }
                }
            }
            
            if (personneStrc.getCasNotificationDeces()!=null && personneStrc.getCasNotificationDeces().equals("true")){
                listes.setList(listMandats);  
            }else listes.setList(listContratValidMand);  
        }
        
        } catch (Exception e) {
            com.oxia.fwk.core.Error erreur = new com.oxia.fwk.core.Error();
            StringBuffer text = 
                new StringBuffer("Erreur dans GetListContratMandataireTrt : ");
            text.append(e.toString());
            erreur.setCode("100");
            erreur.setDescription(text.toString());
            erreur.setKey("GetListContratMandataireTrt");
            listes.addError(erreur);
            logger.error("Exception : ",e);   
            throw new RuntimeException(e);  
          
        } 
        return (listes);

    }
    
    public void genCroText(ValueObject vo){
        
    
    }
    
    public String  getNumeroTache (IValueObject vo) {
        return (Constants.CODE_RESSOURCE_GENERALE);     
    }
    
}
