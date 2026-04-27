package com.bna.smile.model.domainecontratcompte.souscriptioncontratcompte.traitement;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.bna.commun.model.CategoriePersonne;
import com.bna.commun.model.CategpFormj;
import com.bna.commun.model.FormeJuridique;
import com.bna.commun.model.TypePers;
import com.bna.commun.model.TypePiece;
import com.bna.commun.traitements.Traitement;
import com.bna.smile.model.constant.Constants;
import com.bna.smile.model.domainecommun.model.ListTypeCatTpce;
import com.bna.smile.model.domainecontratcompte.souscriptioncontratcompte.model.TypeCatPers;
import com.oxia.fwk.context.Context;
import com.oxia.fwk.core.ICriteria;
import com.oxia.fwk.core.IExpression;
import com.oxia.fwk.core.ISearchEngine;
import com.oxia.fwk.core.IValueObject;
import com.oxia.fwk.core.ValueObject;

public class ChargerTypeCatPcePersonneTrt extends Traitement{
    

    public ChargerTypeCatPcePersonneTrt() {
    }

    /** 
     * Fonction qui prend, le type de la personne et retourne la liste des catégories,
     * ou bien le type et la catégorie pour retourner le type de la piece de cette catégorie
     * @param typeCatPers      : type_personne, [categorie_personne]
     * @return ListTypeCatTpce : liste des types de personne et
     * la liste des categories de ce type_personne et le type piece correspondant
     */
    public IValueObject perform(IValueObject vo) {

        TypeCatPers typeCatPers = (TypeCatPers)vo;
        ISearchEngine searchEngine=(ISearchEngine)Context.getInstance().getSpringContext().getBean("searchEngine");
        ICriteria criteriaCP = searchEngine.createCriteria();
        IExpression expression = searchEngine.createExpression();

        ListTypeCatTpce listTypeCatTpce = new ListTypeCatTpce();
        TypePiece typePiece = new TypePiece();
        List liste_Formj = new ArrayList();
        this.setCroFlag(false);
       /*Charger tous les types_pers*/
    try{
        List listeTypePersonne = searchEngine.findAll(TypePers.class);
        /* Cahreger toutes les formes juridique */
        liste_Formj = searchEngine.findAll(FormeJuridique.class);

        if (typeCatPers != null && typeCatPers.getCodTperTper() != null) {
            criteriaCP.add(expression.eq("typePers.codTperTper", 
                                         typeCatPers.getCodTperTper()));
        } else { /* valeur par defaut (liste des catégories d'une personne physique) */
            criteriaCP.add(expression.eq("typePers.codTperTper", 
                                         Constants.DEFAULT_COD_TPER_TPER));
        }
            criteriaCP.add(expression.ne("codCatpCatp",new String("99")));
        
        /*Liste des Catégories*/
        List listeCategoriePersonne = 
            searchEngine.find(CategoriePersonne.class, criteriaCP);


        if (typeCatPers == null || typeCatPers.getCodCatpCatp() == null) {

            if (listeCategoriePersonne != null && 
                listeCategoriePersonne.size() > 0) {
                CategoriePersonne categoriePersonne = 
                    (CategoriePersonne)listeCategoriePersonne.get(0);
                /*Type piece*/
                typePiece = categoriePersonne.getTypePiece();
                /* ListeCategp */
                liste_Formj.clear();
                for (Iterator it = 
                     categoriePersonne.getCategpFormjs().iterator(); 
                     it.hasNext(); ) {
                    CategpFormj categpFormj = (CategpFormj)it.next();
                    FormeJuridique formej = categpFormj.getFormeJuridique();
                    liste_Formj.add(formej);
                }
            }
        } else {
            criteriaCP.add(expression.eq("codCatpCatp", 
                                         typeCatPers.getCodCatpCatp()));
            List listeCategoriePersonne2 = 
                searchEngine.find(CategoriePersonne.class, criteriaCP);
            if (listeCategoriePersonne2 != null && 
                listeCategoriePersonne2.size() > 0) {
                CategoriePersonne categoriePersonne = 
                    (CategoriePersonne)listeCategoriePersonne2.get(0);
                typePiece = categoriePersonne.getTypePiece();
                /* ListeCategp */
                liste_Formj.clear();
                for (Iterator it = 
                     categoriePersonne.getCategpFormjs().iterator(); 
                     it.hasNext(); ) {
                    CategpFormj categpFormj = (CategpFormj)it.next();
                    FormeJuridique formej = categpFormj.getFormeJuridique();
                    liste_Formj.add(formej);
                }
            }
        }

        /*Charger le VO listTypeCatTpce*/
        listTypeCatTpce.setListTypePers(listeTypePersonne);
        listTypeCatTpce.setListCatPers(listeCategoriePersonne);
        listTypeCatTpce.setListeCategp_Formj(liste_Formj);
        listTypeCatTpce.setTypePiece(typePiece);

        return listTypeCatTpce;
    
        } catch (Exception e) {
            com.oxia.fwk.core.Error erreur = new com.oxia.fwk.core.Error();
            StringBuffer text = 
                new StringBuffer("Erreur dans ChargerTypeCatPcePersonneTrt : ");
            text.append(e.toString());
            erreur.setCode("200");
            erreur.setDescription(text.toString());
            erreur.setKey("ChargerTypeCatPcePersonne");
            listTypeCatTpce.addError(erreur);
            logger.error("Exception : ",e);  
            return (listTypeCatTpce);
        }
}

    public void genCroText(ValueObject vo) {    
    
    }
    
    public String getNumeroTache(IValueObject vo) {
      return (Constants.CODE_RESSOURCE_GENERALE);        
    }

}
