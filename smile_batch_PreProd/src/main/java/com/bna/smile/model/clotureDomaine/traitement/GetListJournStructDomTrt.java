package com.bna.smile.model.clotureDomaine.traitement;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.bna.commun.model.JourneeStructureDomaine;
import com.bna.commun.model.JourneeStructureId;
import com.bna.commun.traitements.Traitement;
import com.bna.commun.util.ContextHandler;
import com.bna.smile.model.clotureDomaine.model.JournStructDomEtatVo;
import com.bna.smile.model.domainecommun.model.Listes;
import com.oxia.fwk.context.Context;
import com.oxia.fwk.core.ICriteria;
import com.oxia.fwk.core.IExpression;
import com.oxia.fwk.core.ISearchEngine;
import com.oxia.fwk.core.IValueObject;
import com.oxia.fwk.core.ValueObject;

public class GetListJournStructDomTrt extends Traitement {
    public GetListJournStructDomTrt() {
    }


    public IValueObject perform(IValueObject vo) {


        Context context = ContextHandler.getContext();
        ISearchEngine searchEngine = 
            (ISearchEngine)Context.getInstance().getSpringContext().getBean("searchEngine");
        ICriteria criteria = searchEngine.createCriteria();
        IExpression expression = searchEngine.createExpression();
        JourneeStructureId journeeStructureId = (JourneeStructureId)vo;
        Listes listedomaines = new Listes();
        List listeJSDVo = new ArrayList();
        try {
            if (this.checkClotureJournee()) {
                /*recherche des domaine d'une journee*/
                criteria.add(expression.eq("journeeStructureDomaineId.codStrcStrc", 
                                           journeeStructureId.getCodStrcStrc()));
                criteria.add(expression.eq("journeeStructureDomaineId.datJrnJrn", 
                                           journeeStructureId.getDatJrnJrn()));
                List l = 
                    searchEngine.find(JourneeStructureDomaine.class, criteria);

                if (l != null && l.size() > 0) {
                    for (Iterator it = l.iterator(); it.hasNext(); ) {
                        JourneeStructureDomaine JSD = 
                            (JourneeStructureDomaine)it.next();
                        JournStructDomEtatVo journStructDomEtatVo = 
                            new JournStructDomEtatVo();
                        journStructDomEtatVo.setJourneeStructureDomaine(JSD);
                        if (JSD.getCodStatJsd().intValue() == 0) {
                            journStructDomEtatVo.setEtat("Ouvert");
                        } else if (JSD.getCodStatJsd().intValue() == 1) {
                            journStructDomEtatVo.setEtat("En cour de côture");
                        } else if (JSD.getCodStatJsd().intValue() == 2) {
                            journStructDomEtatVo.setEtat("Cloturé");
                        } else {
                            journStructDomEtatVo.setEtat("Session Cloturée");
                        }
                        listeJSDVo.add(journStructDomEtatVo);
                    }
                    listedomaines.setList(listeJSDVo);
                }
            } else {
                com.oxia.fwk.core.Error erreur = new com.oxia.fwk.core.Error();
                StringBuffer text = 
                    new StringBuffer("La journée est déja clôturée...");
                erreur.setCode("100");
                erreur.setDescription(text.toString());
                erreur.setKey("GetListJournStructDomTrt");
                listedomaines.addError(erreur);

            }

        } catch (Exception e) {
            com.oxia.fwk.core.Error erreur = new com.oxia.fwk.core.Error();
            StringBuffer text = 
                new StringBuffer("Erreur dans GetListJournStructDomTrt : ");
            text.append(e.toString());
            erreur.setCode("200");
            erreur.setDescription(text.toString());
            erreur.setKey("ClotureDomaineTrt");
            listedomaines.addError(erreur);
            logger.error(" *** Erreur lors de la GetListJournStructDomTrt concernant l'agence " + 
                         journeeStructureId.getCodStrcStrc() + " : ", e);
            throw new RuntimeException(e);

        }
        return (listedomaines);
    }

    public void genCroText(ValueObject vo) {
    }

}
