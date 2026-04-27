package com.bna.smile.model.domainecontratcompte.moyensPaiement.traitement;

import com.bna.commun.model.ContratCpt;
import com.bna.commun.model.TypeCarte;
import com.bna.commun.traitements.Traitement;
import com.bna.commun.util.ContextHandler;
import com.bna.commun.vo.PrimitiveVO;
import com.bna.smile.model.constant.Constants;
import com.bna.smile.model.domainecontratcompte.moyensPaiement.model.TypeCarteCpt;
import com.oxia.fwk.context.Context;
import com.oxia.fwk.core.ICriteria;
import com.oxia.fwk.core.IExpression;
import com.oxia.fwk.core.ISearchEngine;
import com.oxia.fwk.core.IValueObject;
import com.oxia.fwk.core.ValueObject;
import com.oxia.fwk.searchengine.SearchEngine;

/**
 * Classe de traitement permettant de Vérifier si un type de carte est éligible sur un contrat donné. 
 * @author Ramzi
 * @param TypeCarteCpt
 * @return PrimitiveVO
 * @since 15/06/2007
 * 
 */
public class VerifEligibiliteCarteTrt extends Traitement{
    public VerifEligibiliteCarteTrt() {
    }

    public IValueObject perform(IValueObject vo) throws Exception{
        TypeCarteCpt typeCarteCpt = (TypeCarteCpt)vo;
        PrimitiveVO primitiveVO = new PrimitiveVO();
        primitiveVO.setVBool(true);   
        try {
            Context context = ContextHandler.getContext();
            ISearchEngine searchEngine = 
                (SearchEngine)context.getBean("searchEngine");
            ICriteria criteria = searchEngine.createCriteria();
            IExpression expression = searchEngine.createExpression();
            
            Long typeCarte = typeCarteCpt.getTypeCarte();
            ContratCpt contratCpt = typeCarteCpt.getContratCpt();
            
            // determiner type personne PP/PM
            String typePersonne = contratCpt.getClient().getTypePers().getCodTperTper();
            
            // test si type personne valable pour ce type de carte
            TypeCarte typCarte = (TypeCarte)searchEngine.get(TypeCarte.class, typeCarte);
            ///si PM
            if(typePersonne.equals(Constants.PERSMORALE)){
                if(!typCarte.getCodTperTcar().equals(Constants.COD_TPER_TCAR_PM) && !typCarte.getCodTperTcar().equals(Constants.COD_TPER_TCAR_TP)){
                    primitiveVO.setVBool(false);
                }else{
                ///verifier si produit du contrat existe dans les produits eligible pour ce type de carte
                    String listeProduitPM = typCarte.getLibPrdmTcar();
                    if(listeProduitPM.indexOf(contratCpt.getContratCptId().getCodPrdPrd().toString())<0){
                        primitiveVO.setVBool(false);   
                    }
                }   
            ///si PP ou Cotitulaire
            }else{
                if(!typCarte.getCodTperTcar().equals(Constants.COD_TPER_TCAR_PP) && !typCarte.getCodTperTcar().equals(Constants.COD_TPER_TCAR_TP)){
                    primitiveVO.setVBool(false);
                }else{
                    String listeProduitPP = typCarte.getLibPrdpTcar();
                    int indexProduit = listeProduitPP.indexOf(contratCpt.getContratCptId().getCodPrdPrd().toString());
                    if(listeProduitPP==null || indexProduit <0){
                        primitiveVO.setVBool(false);   
                    }
                }        
            }
            
            
        } catch (Exception e) {
                com.oxia.fwk.core.Error erreur=new com.oxia.fwk.core.Error();
                erreur.setCode("Technique");
                erreur.setDescription("VerifEligibiliteCarteTrt "+e.getMessage());;
                primitiveVO.addError(erreur);
                logger.error("Exception : ",e);
                throw new RuntimeException(e); 
        }
        return primitiveVO;
    }
    
    public void genCroText(ValueObject vo) {
    
    }
    public String getNumeroTache(IValueObject vo){
       System.out.println(Constants.CODE_RESSOURCE_GENERALE);
       return Constants.CODE_RESSOURCE_GENERALE;
    }
}
