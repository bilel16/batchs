package com.bna.smile.model.domainecontratcompte.souscriptioncontratcompte.traitement;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.collections.map.ListOrderedMap;

import com.bna.commun.model.ContratCpt;
import com.bna.commun.traitements.Traitement;
import com.bna.smile.model.constant.Constants;
import com.bna.smile.model.domainecommun.model.Listes;
import com.bna.smile.model.domainecommun.model.PersonneCpt;
import com.bna.smile.model.domainecommun.model.PersonneStrc;
import com.bna.smile.model.domainecommun.traitement.GetPersonneCptTrt;
import com.bna.smile.model.domainecontratcompte.souscriptioncontratcompte.model.ParamListContratsAmodifierVo;
import com.oxia.fwk.core.IValueObject;
import com.oxia.fwk.core.ValueObject;

public class GetListeContratsAmodifierTrt extends Traitement{
   
    public GetListeContratsAmodifierTrt() {
    }
    
    
    /**
     * Fonction qui permet de determiner la liste des contrats à modifier suite à une rectificatin des données client
     * 
     * @param   ParamListContratsAmodifierVo     
     * @return  ParamListContratsAmodifierVo 
     * @Author : Mdimagh Lassaad
     * @since 04/07/2007
     */
     public IValueObject perform(IValueObject vo) {
       
        ParamListContratsAmodifierVo ParamListVo = (ParamListContratsAmodifierVo) vo;
        try {
        GetProduitAutorisesTrt   getProduitAutorisesTrt = new  GetProduitAutorisesTrt();
        PersonneStrc personneStrc =   ParamListVo.getPersonneStrc();
        //--------- extraire les contrats -------------------//
        GetPersonneCptTrt getPersonneCptTrt = new GetPersonneCptTrt();
        PersonneCpt personneCpt = (PersonneCpt)getPersonneCptTrt.exec(personneStrc);
        //---------- determiner la liste des nouveaux contrats avec les nouveaux parametres --//
         ParamListVo.getParampers().setNumSeqPers(Integer.valueOf(personneCpt.getPersonne().getNumSeqPers().toString()));
        getProduitAutorisesTrt.setSecurityFlag(false);
        Listes listeDesProduits=(Listes)getProduitAutorisesTrt.exec(ParamListVo.getParampers());
             int i=0;
             ListOrderedMap listOrder = null;
         //--------------------------------------------------------------------//
         //-------- verification des produits dont il n'a plus droit ----------//
         List listeDesProduitsAmodifier = new ArrayList();
         for(Iterator itListProduit = personneCpt.getListeContratCpt().iterator();itListProduit.hasNext(); ){
             boolean test = false;
             ContratCpt contratCpt = (ContratCpt) itListProduit.next();
             String codeProduit = contratCpt.getProduit().getCodPrdPrd().toString();
             for(Iterator it =listeDesProduits.getList().iterator();  it.hasNext(); ){
                 listOrder = (ListOrderedMap)it.next();
                 String produit = listOrder.getValue(0).toString();
                 
                 if (codeProduit.equals(produit)){
                     test = true;
                 }
                }// fin for liste autorisé
             if (test == false){
             listeDesProduitsAmodifier.add(contratCpt);
             }
         }
             ParamListVo.setListContratAmodifier(listeDesProduitsAmodifier);
             return (ParamListVo);  
         }catch(Exception e){
            com.oxia.fwk.core.Error erreur = new com.oxia.fwk.core.Error();
            StringBuffer text = 
                new StringBuffer("Erreur dans GetListsContratsAmodifierTrt : ");
            text.append(e.toString());
            erreur.setCode("200");
            erreur.setDescription(text.toString());
            erreur.setKey("ModifierDonnesClient");

            ParamListVo.addError(erreur);
            return (ParamListVo);  
        }
   
    }
    
    
    public void genCroText(ValueObject vo) {    
    
    }
    
    public String getNumeroTache  (ValueObject vo) {
     return  Constants.CODE_RESSOURCE_GENERALE;
    }
    
}
