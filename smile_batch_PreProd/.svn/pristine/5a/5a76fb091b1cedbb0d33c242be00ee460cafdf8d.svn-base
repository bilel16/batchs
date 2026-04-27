package com.bna.smile.model.domaineplacement.traitement;

import com.bna.commun.model.MandPersOperPlac;
import com.bna.commun.model.StructureDomaine;
import com.bna.commun.traitements.Traitement;
import com.bna.commun.util.ContextHandler;
import com.bna.commun.util.StrHandler;
import com.bna.smile.model.constant.Constants;
import com.bna.smile.model.domaineplacement.model.ParamContratPlacement;

import com.oxia.fwk.context.Context;
import com.oxia.fwk.core.IValueObject;
import com.oxia.fwk.core.ValueObject;

import java.util.Iterator;

import org.springframework.orm.hibernate3.HibernateTemplate;


/**
 * validation d'une avance sur capital .
 * @param ParamContratPlacement
 * @return AvancRembLiquid
 * 
 */
public class PecLiquidationAnticipeTrt extends Traitement{
    public PecLiquidationAnticipeTrt() {
    }
        
    public IValueObject perform (IValueObject vo ) {     
     
    Context context = ContextHandler.getContext();
    ParamContratPlacement paramContratPlacement = (ParamContratPlacement)vo;             
             
       try{ 
           ///------------------------------------------------------------------------------------------------
           ///----------- création liquidation anticipe  et affectation détails opération ----------------------
           ///------------------------------------------------------------------------------------------------
                
            if(!paramContratPlacement.getAvancRembLiquid().equals(null)){
                getCrudservice().create(paramContratPlacement.getAvancRembLiquid());  
                // Mise  à jour de l'etat du contrat placement : en attente de liquidation en cas où la liquidation est totale
                if((paramContratPlacement.getAvancRembLiquid().getCodTyplArl().equals(String.valueOf("T")) || (paramContratPlacement.getAvancRembLiquid().getCodTyplArl().equals(String.valueOf("P"))))){
                    paramContratPlacement.getAvancRembLiquid().getContratPlacement().setCodEtatCpla(Constants.ETAT_CPT_PLC_ATT_LIQUID);                
                    getCrudservice().update(paramContratPlacement.getAvancRembLiquid().getContratPlacement());
                }else if(paramContratPlacement.getAvancRembLiquid().getCodTyplArl().equals(String.valueOf("R"))){ 
                    // cas de la résiliation : mettre le placement à en attente de résiliation
                     paramContratPlacement.getAvancRembLiquid().getContratPlacement().setCodEtatCpla(Constants.ETAT_CPT_PLC_ATT_RESILIATION);                
                     getCrudservice().update(paramContratPlacement.getAvancRembLiquid().getContratPlacement());
                }
                ///----------- création détails opération  ----------------------                
                
                if(!paramContratPlacement.getDetailsOperationPlacement().equals(null)){
                    paramContratPlacement.getDetailsOperationPlacement().setAvancRembLiquid(paramContratPlacement.getAvancRembLiquid());
                    getCrudservice().create(paramContratPlacement.getDetailsOperationPlacement());         
                } 
               
                
                ///*** Insertion de mand_pers_oper_plac du montant actualisé dans le contrat placement
                if(!paramContratPlacement.getDetailsOperationPlacement().getMandPersOperPlacs().isEmpty()){
                    InsertMandPersOperPlacTrt insertMandPersOperPlacTrt = new InsertMandPersOperPlacTrt();
                    for (Iterator it = paramContratPlacement.getDetailsOperationPlacement().getMandPersOperPlacs().iterator();it.hasNext(); ) { 
                        MandPersOperPlac mandPersOperPlac = (MandPersOperPlac)it.next();
                        mandPersOperPlac.getMandPersOperPlacId().setNumSeqDopl(paramContratPlacement.getDetailsOperationPlacement().getNumSeqDopl());

                        HibernateTemplate  hibernateTemplate = (HibernateTemplate) context.getBean("hibernateTemplate");
                        hibernateTemplate.evict(mandPersOperPlac);

                        mandPersOperPlac = (MandPersOperPlac)insertMandPersOperPlacTrt.exec(mandPersOperPlac);
                    }
                }
                
            } 
               
                  
           }
         catch (Exception e) {
                com.oxia.fwk.core.Error erreur=new com.oxia.fwk.core.Error();
                erreur.setCode("Technique");
                erreur.setDescription("ValiderPECAvancePlacementTrt  "+e.getMessage());
                paramContratPlacement.getAvancRembLiquid().addError(erreur);
                logger.error("Exception : ",e);   
                throw new RuntimeException(e);
        } 
        return (paramContratPlacement.getAvancRembLiquid());
    }
    
    public void genCroText(ValueObject vo) {
            
        }   

    public IValueObject getNumeroDomaine(IValueObject vo){
        StructureDomaine structureDomaine = new StructureDomaine();
        ParamContratPlacement paramContratPlacement = (ParamContratPlacement)vo;             
        structureDomaine.setCodDomDomm(Constants.COD_DOM_PLACEMENT);
        structureDomaine.setCodStrcStrc(paramContratPlacement.getAvancRembLiquid().getContratPlacement().getContratCpt().getStructure().getCodStrcStrc());
        return structureDomaine;
    }

    public String getNumeroTache(IValueObject vo) {
        
        return(Constants.COD_OPER_DEMANDE_LIQUID_ANTICIPE.toString()+
        StrHandler.lpad(Constants.COD_TACHE_DEMANDE_LIQUIDATION_ANTICIPE.toString(),'0',2));
    }
    

}
