package com.bna.smile.model.domainecontratcompte.souscriptioncontratcompte.traitement;

import com.bna.commun.traitements.Traitement;
import com.bna.commun.util.ContextHandler;
import com.bna.commun.vo.PrimitiveVO;
import com.bna.smile.model.constant.Constants;
import com.bna.smile.model.domainecontratcompte.souscriptioncontratcompte.dao.ProduitDAO;
import com.bna.smile.model.domainecontratcompte.souscriptioncontratcompte.model.PersProduit;
import com.oxia.fwk.context.Context;
import com.oxia.fwk.core.IValueObject;
import com.oxia.fwk.core.ValueObject;


public class GetNbrProduitByPersTrt extends Traitement{
    public GetNbrProduitByPersTrt() {
    }
    /**
     * methode permettant la recherche des produits autorisés
     * pour unr personne donnée
     * @param vo : PersProduit
     * @return primitiveVo
     * @autor BOUSSEN Youssef & KRIAA Hatem
     */
    public IValueObject perform(IValueObject vo) {

        PrimitiveVO primitiveVo = new PrimitiveVO();
     try{
        PersProduit persProduit = (PersProduit)vo;
        Context context = ContextHandler.getContext();
        ProduitDAO executeAStatement = 
            (ProduitDAO)context.getBean("produitDAO");
        Long l = executeAStatement.getNbrProdByPers(persProduit);
        primitiveVo.setVLong(l);
        return primitiveVo;
        } catch (Exception e) {
            com.oxia.fwk.core.Error erreur = new com.oxia.fwk.core.Error();
            StringBuffer text = 
                new StringBuffer("Erreur dans GetNbrProduitByPersTrt : ");
            text.append(e.toString());
            erreur.setCode("200");
            erreur.setDescription(text.toString());
            erreur.setKey("GetNbrProduitByPers");
            primitiveVo.addError(erreur);
            return (primitiveVo);
        }
    }
    
    
    public void genCroText(ValueObject vo) {
        
    }
    public String getNumeroTache(IValueObject vo) {
      return (Constants.CODE_RESSOURCE_GENERALE);        
    }  
}
