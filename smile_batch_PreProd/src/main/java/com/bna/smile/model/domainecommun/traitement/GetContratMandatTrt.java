package com.bna.smile.model.domainecommun.traitement;

import com.bna.commun.model.ContratCpt;
import com.bna.commun.model.ContratCptId;
import com.bna.commun.model.Mandat;
import com.bna.commun.model.MandatPersonne;
import com.bna.commun.traitements.Traitement;
import com.bna.commun.util.ContextHandler;
import com.bna.smile.model.constant.Constants;
import com.bna.smile.model.domainecommun.model.ContratCptMandat;


import com.bna.smile.model.domainecommun.model.Listes;
import com.bna.smile.model.domainecommun.model.MandatPersonneMandat;

import com.bna.smile.model.domainecontratcompte.procuration.model.MandatRecherche;

import com.oxia.fwk.context.Context;
import com.oxia.fwk.core.Error;
import com.oxia.fwk.core.ICriteria;
import com.oxia.fwk.core.IExpression;
import com.oxia.fwk.core.ISearchEngine;
import com.oxia.fwk.core.IValueObject;
import com.oxia.fwk.core.ValueObject;
import com.oxia.fwk.searchengine.SearchEngine;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class GetContratMandatTrt extends Traitement {

    public GetContratMandatTrt() {
    }

    /**
     * methode permettant l'affichage des informations sur un contrat donné
     * ainsi que le liste des mandats valides sur ce contrat
     * @param vo : IdContratCpt
     * @return   : ContratCptMandat
     * @autor    : Youssef BOUSSEN 
     */
    public IValueObject perform(IValueObject vo) {


        ISearchEngine searchEngine = 
            (ISearchEngine)Context.getInstance().getSpringContext().getBean("searchEngine");
        ICriteria criteria = searchEngine.createCriteria();
        IExpression expression = searchEngine.createExpression();
        ContratCpt contratCpt = new ContratCpt();
        ContratCptMandat contratCptMandat = new ContratCptMandat();

        try {
            MandatRecherche mandatRecherche = (MandatRecherche)vo;

            List listMandatValid = new ArrayList();


            /* Rechercher du contrat */
            criteria.add(expression.eq("contratCptId.codPrdPrd", 
                                       mandatRecherche.getContratCptId().getCodPrdPrd()));
            criteria.add(expression.eq("contratCptId.codStrcStrc", 
                                       mandatRecherche.getContratCptId().getCodStrcStrc()));
            criteria.add(expression.eq("contratCptId.numCcptCcpt", 
                                       mandatRecherche.getContratCptId().getNumCcptCcpt()));

            List l = searchEngine.find(ContratCpt.class, criteria);

            if (l != null && l.size() > 0) {

                contratCpt = (ContratCpt)l.get(0);
                List listMandataires = new ArrayList();

                for (Iterator it = 
                     ((Listes)(getMandatsCpt(mandatRecherche))).getList().iterator(); 
                     it.hasNext(); ) {


                    Mandat mandat = (Mandat)it.next();

                    MandatPersonneMandat mandatPersonneMandat = 
                        new MandatPersonneMandat();
                    String nom = "";
                    for (Iterator it1 = mandat.getMandatPersonnes().iterator(); 
                         it1.hasNext(); ) {
                        MandatPersonne mandatPersonne = 
                            (MandatPersonne)it1.next();
                        if (mandatPersonne.getCodEtatMp().equalsIgnoreCase("V")) {
                            nom = 
nom + (String)mandatPersonne.getPersonne().getNomNomPers() + " " + 
  mandatPersonne.getPersonne().getNomPrnPers() + ", ";

                        }
                    }
                    mandatPersonneMandat.setMandat(mandat);
                    testetEtatMandat(mandat, mandatPersonneMandat);
                    mandatPersonneMandat.setMandataires(nom);
                    listMandataires.add(mandatPersonneMandat);
                    listMandatValid.add(mandat);
                }
                contratCptMandat.setContratCpt(contratCpt);
                contratCptMandat.setListeMandataire(listMandataires);
                contratCptMandat.setListeMandat(listMandatValid);
            } else { /* Contrat inexistant */
                com.oxia.fwk.core.Error erreur = new com.oxia.fwk.core.Error();
                erreur.setCode("1");
                erreur.setDescription("Contrat Inexistant");
                erreur.setKey("numero.compte.message");
                contratCptMandat.addError(erreur);
                contratCptMandat.setContratCpt(null);
                contratCptMandat.setListeMandat(null);
            }

        } catch (Exception e) {
            com.oxia.fwk.core.Error erreur = new com.oxia.fwk.core.Error();
            StringBuffer text = 
                new StringBuffer("Erreur dans GetContratMandatTrt : ");
            text.append(e.toString());
            erreur.setCode("100");
            erreur.setDescription(text.toString());
            erreur.setKey("GetContratMandatTrt");
            contratCptMandat.addError(erreur);
            logger.error("Exception : ", e);
            throw new RuntimeException(e);

        }
        return contratCptMandat;

    }

    public void testetEtatMandat(Mandat mandat, 
                                 MandatPersonneMandat mandatPersonneMandat) {

        if (mandat.getCodEtatMand().equalsIgnoreCase(Constants.COD_ETAT_MAND_ATT_PRE)) {
            mandatPersonneMandat.setEtatMand("Saisie");

        } else if (mandat.getCodEtatMand().equalsIgnoreCase(Constants.COD_ETAT_MAND_ATT)) {
            mandatPersonneMandat.setEtatMand("Prévalidé");

        } else if (mandat.getCodEtatMand().equalsIgnoreCase(Constants.COD_ETAT_MAND_VALID)) {
            if (mandat.getCodEdemMand() == null) {
                mandatPersonneMandat.setEtatMand("Valide");
            } else if (mandat.getCodEdemMand().equals("VR")) {
                mandatPersonneMandat.setEtatMand("Validé avec réserve");
            } else if (mandat.getCodEdemMand().equals("SR")) {
                mandatPersonneMandat.setEtatMand("Renouvellement saisie");
            } else if (mandat.getCodEdemMand().equals("AR")) {
                mandatPersonneMandat.setEtatMand("Renouvellement prévalidé");
            } else if (mandat.getCodEdemMand().equals("SA")) {
                mandatPersonneMandat.setEtatMand("Annulation saisie");
            } else if (mandat.getCodEdemMand().equals("AA")) {
                mandatPersonneMandat.setEtatMand("Annulation prévalidé");

            } else if (mandat.getCodEdemMand().equals("M")) {
                mandatPersonneMandat.setEtatMand("Valide en cour de modification");
            }
        } else if (mandat.getCodEtatMand().equalsIgnoreCase(Constants.COD_SYCL_MOD)) {
            if (mandat.getCodEdemMand().equals("SM")) {
                mandatPersonneMandat.setEtatMand("Modification saisie");
            } else if (mandat.getCodEdemMand().equals("PM")) {
                mandatPersonneMandat.setEtatMand("Modification prévalidé");
            }
        } else if (mandat.getCodEtatMand().equalsIgnoreCase(Constants.COD_ETAT_MAND_ANN)) {
            mandatPersonneMandat.setEtatMand("Annulé");
        } else if (mandat.getCodEtatMand().equalsIgnoreCase(Constants.COD_ETAT_MAND_HIST)) {
            if (mandat.getCodEdemMand() == null) {
                mandatPersonneMandat.setEtatMand("Historisé");
            } else if (mandat.getCodEdemMand().equals(Constants.COD_ETAT_MAND_REJ_MOD)) {
                mandatPersonneMandat.setEtatMand("Rejeté suite à modification");
            }
        } else if (mandat.getCodEtatMand().equalsIgnoreCase(Constants.COD_ETAT_MAND_R)) {
            mandatPersonneMandat.setEtatMand("Rejeté");
        }

    }

    public ValueObject getMandatsCpt(ValueObject vo) {

        try {
            MandatRecherche mandatRecherche = (MandatRecherche)vo;


            ISearchEngine searchEngine = 
                (ISearchEngine)Context.getInstance().getSpringContext().getBean("searchEngine");

            ICriteria criteria = searchEngine.createCriteria();
            IExpression expression = searchEngine.createExpression();

            criteria.add(expression.eq("contratCpt.contratCptId.codPrdPrd", 
                                       mandatRecherche.getContratCptId().getCodPrdPrd()));
            criteria.add(expression.eq("contratCpt.contratCptId.codStrcStrc", 
                                       mandatRecherche.getContratCptId().getCodStrcStrc()));
            criteria.add(expression.eq("contratCpt.contratCptId.numCcptCcpt", 
                                       mandatRecherche.getContratCptId().getNumCcptCcpt()));


            if ((mandatRecherche.getCodMenu() != null) && 
                ((mandatRecherche.getCodMenu().equalsIgnoreCase("SM")) || 
                 (mandatRecherche.getCodMenu().equalsIgnoreCase("SA")) || 
                 (mandatRecherche.getCodMenu().equalsIgnoreCase("SR")) || 
                 (mandatRecherche.getCodMenu().equalsIgnoreCase("RP")))) {
                criteria.add(expression.or(expression.isNull("codEdemMand"), 
                                           expression.eq("codEdemMand", 
                                                         "VR")));
                criteria.add(expression.eq("codEtatMand", 
                                           mandatRecherche.getCodEtat()));

            } else {
                if (mandatRecherche.getCodEtat() != null) {
                    criteria.add(expression.eq("codEtatMand", 
                                               mandatRecherche.getCodEtat()));
                }

                if (mandatRecherche.getCodEtatAttente() != null) {
                    criteria.add(expression.eq("codEdemMand", 
                                               mandatRecherche.getCodEtatAttente()));
                } else if (mandatRecherche.getCodEtat() != null) {
                    criteria.add(expression.isNull("codEdemMand"));
                }

            }
            if (mandatRecherche.getCodStrcConcer() != null) {
                criteria.add(expression.eq("codStrcMand", 
                                           mandatRecherche.getCodStrcConcer()));
            }
            //Critère de recherche sur les dates debut et fin consultation
            if (mandatRecherche.getDateDeb() != null) {
                criteria.add(expression.ge("datCreMand", 
                                           mandatRecherche.getDateDeb()));
            }
            if (mandatRecherche.getDateFin() != null) {
                criteria.add(expression.le("datCreMand", 
                                           mandatRecherche.getDateFin()));

            }
            if ((mandatRecherche.getCodMenu() != null) && 
                (mandatRecherche.getCodMenu().equalsIgnoreCase("SR"))) {
                criteria.add(expression.isNotNull("datFinMand"));
            }
            Listes listes = new Listes();
            listes.setList(searchEngine.find(Mandat.class, criteria));

            return listes;
        } catch (Exception e) {
            logger.error("Exception : ", e);
            throw new RuntimeException(e);

        }
    }

    public void genCroText(ValueObject vo) {

    }

    public String getNumeroTache(IValueObject vo) {
        return (Constants.CODE_RESSOURCE_GENERALE);
    }

}


