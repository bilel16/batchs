package com.bna.smile.model.domainecontratcompte.moyensPaiement.traitement;


import java.util.ArrayList;
import java.util.List;

import com.bna.commun.model.TypeMoyenPaiement;
import com.bna.commun.traitements.Traitement;
import com.bna.commun.util.ContextHandler;
import com.bna.smile.model.constant.Constants;
import com.bna.smile.model.domainecommun.model.Listes;
import com.oxia.fwk.context.Context;
import com.oxia.fwk.core.ISearchEngine;
import com.oxia.fwk.core.IValueObject;
import com.oxia.fwk.core.ValueObject;
import com.oxia.fwk.searchengine.SearchEngine;

/**
 * Extraire la liste des types de carte qui sont éligible pour un contrat donné. 
 * @author Lamia
 * @return Listes : de Type moyen de paiement
 * @since 10/04/2008
 * 
 */
public class ChargerTypeMoyPaieTrt  extends Traitement{
    public ChargerTypeMoyPaieTrt() {
    }

    public IValueObject perform(IValueObject vo) throws Exception{
      
        Listes listeTypMoyPaie =new Listes();
        List  l= new ArrayList();
    
        try {
        
            Context context = ContextHandler.getContext();
            ISearchEngine searchEngine = 
                (SearchEngine)context.getBean("searchEngine");

            l = searchEngine.findAll(TypeMoyenPaiement.class);
            listeTypMoyPaie.setList(l);
        } catch (Exception e) {
                com.oxia.fwk.core.Error erreur=new com.oxia.fwk.core.Error();
                erreur.setCode("Technique");
                erreur.setDescription("ChargerTypeMoyPaieTrt "+e.getMessage());;
                listeTypMoyPaie.addError(erreur); 
                throw new RuntimeException(e);       
        }
        return listeTypMoyPaie;
    }
    public void genCroText(ValueObject vo) {
    
    }
    public String getNumeroTache(IValueObject vo){
       return Constants.CODE_RESSOURCE_GENERALE;   
    }
}
