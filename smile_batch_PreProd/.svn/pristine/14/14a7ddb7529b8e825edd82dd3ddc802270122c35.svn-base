package com.bna.smile.model.domainecontratcompte.modificationdonneesclient.traitement;

import java.util.List;

import com.bna.commun.model.ModificationDonnees;
import com.bna.commun.model.Personne;
import com.bna.commun.traitements.Traitement;
import com.bna.commun.util.ContextHandler;
import com.bna.smile.model.domainecommun.model.PersonneCpt;
import com.bna.smile.model.domainecommun.model.PersonneStrc;
import com.bna.smile.model.domainecommun.traitement.GetPersonneCptTrt;
import com.bna.smile.model.domainecontratcompte.modificationdonneesclient.model.ParamRechercheModificationDonneesVo;
import com.oxia.fwk.context.Context;
import com.oxia.fwk.core.ICriteria;
import com.oxia.fwk.core.IExpression;
import com.oxia.fwk.core.ISearchEngine;
import com.oxia.fwk.core.IValueObject;
import com.oxia.fwk.core.ValueObject;

public class GetModificationDonneesClientTrt extends Traitement {
    public GetModificationDonneesClientTrt() {
    }

    /**
     * Methode permettant la recherche des modfifcation des données client
     * @param vo : ParamModificationDonneesVo
     * @return ParamModificationDonneesVo
     */
    public IValueObject perform (IValueObject vo) {
        ParamRechercheModificationDonneesVo paramRechercheModificationDonneesVo = 
            (ParamRechercheModificationDonneesVo)vo;
     try{
        Context context = ContextHandler.getContext();
            ISearchEngine searchEngine=(ISearchEngine)Context.getInstance().getSpringContext().getBean("searchEngine");

        ICriteria criteria = searchEngine.createCriteria();
        IExpression expression = searchEngine.createExpression();

        //--------------------------------------------------------//
        //-------- Recuperer l'objet personne de la base ---------//
        //--------------------------------------------------------//
        PersonneStrc personneStrc = new PersonneStrc();
        personneStrc.setCodTpceTpce(paramRechercheModificationDonneesVo.getPersonne().getTypePiece().getCodTpceTpce());
        personneStrc.setNumPcePers(paramRechercheModificationDonneesVo.getPersonne().getNumPcePers());

        GetPersonneCptTrt getPersonneCptTrt = new GetPersonneCptTrt();
        PersonneCpt personneCpt = 
            (PersonneCpt)getPersonneCptTrt.exec(personneStrc);
        Personne personneBase = personneCpt.getPersonne();
   
        if (personneBase != null) {
            paramRechercheModificationDonneesVo.setPersonne(personneBase);
            criteria.add(expression.eq("personne.numSeqPers", 
                                       personneBase.getNumSeqPers()));
            if((paramRechercheModificationDonneesVo.getDateDebut() != null) && (paramRechercheModificationDonneesVo.getDateFin() != null)) {
            criteria.add(expression.between("modificationDonneesId.datModModd",paramRechercheModificationDonneesVo.getDateDebut(),paramRechercheModificationDonneesVo.getDateFin())) ;
            }
            /*if(paramRechercheModificationDonneesVo.getDateFin() != null){
            
            //  Date d= new(DateHandler.dateToStr(paramRechercheModificationDonneesVo.getDateFin()));
              //int r = d.getDate();
              
            criteria.add(expression.lt("modificationDonneesId.datModModd",paramRechercheModificationDonneesVo.getDateFin()));
                    
            }
            if (paramRechercheModificationDonneesVo.getDateDebut() != null){
            criteria.add(expression.gt("modificationDonneesId.datModModd",paramRechercheModificationDonneesVo.getDateDebut()));
            }*/
            List listModifications = 
                searchEngine.find(ModificationDonnees.class, criteria);
            /*si la liste est non vide*/
            if (listModifications != null && listModifications.size() > 0) {
                paramRechercheModificationDonneesVo.setListeDesModifications(listModifications);
            }
        }
        return (paramRechercheModificationDonneesVo);
        } catch (Exception e) {
            com.oxia.fwk.core.Error erreur = new com.oxia.fwk.core.Error();
            StringBuffer text = 
                new StringBuffer("Erreur dans GetModificationDonneesClientTrt : ");
            text.append(e.toString());
            erreur.setCode("200");
            erreur.setDescription(text.toString());
            erreur.setKey("ModifierDonnesClient");

            paramRechercheModificationDonneesVo.addError(erreur);
            return (paramRechercheModificationDonneesVo);
        }
    }

    public void genCroText (ValueObject vo){
        
        }
     
        public String  getNumeroTache (IValueObject vo) {   
         return "70006";
        }
}
