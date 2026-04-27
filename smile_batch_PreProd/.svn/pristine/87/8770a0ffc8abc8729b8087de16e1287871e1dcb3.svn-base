package com.bna.smile.model.domaineplacement.traitement;

import com.bna.commun.model.BonDeCaisse;
import com.bna.commun.model.DetailsBc;
import com.bna.commun.traitements.Traitement;
import com.bna.smile.model.constant.Constants;
import com.bna.smile.model.domaineplacement.model.ParamBonCaisse;
import com.oxia.fwk.core.ICriteria;
import com.oxia.fwk.core.IExpression;
import com.oxia.fwk.core.IValueObject;
import com.oxia.fwk.core.ValueObject;
import java.util.List;

import org.apache.log4j.Logger;
/**
 * vérifier que le numero BC existe dans les carnets BC de l'agence, et vérifier que ce num. n'est pas affecté a un contrat placement
 * @author Lamia
 * @param ParamBonCaisse
 * @return ParamBonCaisse
 * @since 02/04/2009
 * 
 */
 
public class GetParamBonCaisseTrt extends Traitement {

    private static final Logger logger = Logger.getLogger(GetParamBonCaisseTrt.class);
   
    public GetParamBonCaisseTrt() {
    }


    public IValueObject perform(IValueObject vo) {
        
        ICriteria   criteria       = getSearchEngine().createCriteria();
        IExpression expression     = getSearchEngine().createExpression();
        ParamBonCaisse paramBonCaisse = (ParamBonCaisse)vo;
      
        this.setCroFlag(false);
        try{
        
         //   vérifier que le numero BC existe dans les carnets BC de l'agence
       
        if(paramBonCaisse.getCodeStructure() != null){
              criteria.add(expression.eq("structure.codStrcStrc",paramBonCaisse.getCodeStructure()));
          }
        
        if(paramBonCaisse.getNumBonCaisse() != null){
              criteria.add(expression.le("numDebcBc",paramBonCaisse.getNumBonCaisse())); 
              criteria.add(expression.ge("numFincBc",paramBonCaisse.getNumBonCaisse()));
           }
           
        List list = getSearchEngine().find(BonDeCaisse.class,criteria);
        if(list != null){
            if(list.size() > 0){
                paramBonCaisse.setExistBonCaisse(true);
                BonDeCaisse BC = (BonDeCaisse)list.get(0);
                paramBonCaisse.setNumSeqBc(BC.getNumSeqBc());
                
            }else {
                paramBonCaisse.setExistBonCaisse(false);
            }
        }else {
            logger.debug("liste des Bon de caisse par agence (BON_DE_CAISSE) null ");
        }
          // réinitialiser le critère de recherche 
            criteria = getSearchEngine().createCriteria();
            expression = getSearchEngine().createExpression();
            
       if(paramBonCaisse.isExistBonCaisse()){
           if(paramBonCaisse.getNumBonCaisse() != null){
               criteria.add(expression.eq("numBcDbc",paramBonCaisse.getNumBonCaisse()));
             }
           if(paramBonCaisse.getNumBonCaisse() != null){
               criteria.add(expression.eq("numBcDbc",paramBonCaisse.getNumBonCaisse()));
             }
        // vérifier que le num. BC n'est pas affecté a un contrat placement
         List l = getSearchEngine().find(DetailsBc.class,criteria);
           if(l != null){
               if(l.size() > 0){
                   paramBonCaisse.setExistDetailsBC(true);
               }else {
                   paramBonCaisse.setExistDetailsBC(false);
               }
           }else {
               logger.debug("le num. BC n est pas affecté a un contrat placement (DETAILS_BC) l == null ");
           }
       }else {
           paramBonCaisse.setExistDetailsBC(false); // si le numéro n existe pas dans les carnets de l agence, par defaut il n est pas affecté à un contrat placement
       }
        
        return (paramBonCaisse);
        }catch (Exception e) {
                com.oxia.fwk.core.Error erreur = new com.oxia.fwk.core.Error();
                StringBuffer text = 
                    new StringBuffer("Erreur GetParamBonCaisseTrt ");
                text.append(e.toString());
                erreur.setCode("400");
                erreur.setDescription(text.toString());
                erreur.setKey("GetParamBonCaisseTrt");
                paramBonCaisse.addError(erreur);
                logger.error(" *** Erreur lors de GetParamBonCaisseTrt : ", e);
                return (paramBonCaisse);
            }
    }
    
    
     public void genCroText(ValueObject valueObject) {
     
     }
     public String getNumeroTache(ValueObject vo) {
         return (Constants.CODE_RESSOURCE_GENERALE);
     }
 }
