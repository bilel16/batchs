package com.bna.smile.model.banqueAssurance.traitement;

import com.bna.commun.model.DetailAdhesion;
import com.bna.commun.model.StructureDomaine;
import com.bna.commun.traitements.Traitement;
import com.bna.commun.util.ContextHandler;
import com.bna.smile.model.constant.Constants;
import com.bna.smile.model.domainecommun.service.CRUDservice;
import com.oxia.fwk.context.Context;
import com.oxia.fwk.core.IValueObject;
import com.oxia.fwk.core.ValueObject;

public class CreatDetailAdhesionAssVieTrt extends Traitement{

       
        public CreatDetailAdhesionAssVieTrt() {
        }
        public IValueObject perform(IValueObject vo) {
          
            DetailAdhesion detailAdhesion = (DetailAdhesion)vo;
             
           try{
                this.setCroFlag(false);
                Context context = ContextHandler.getContext();
                CRUDservice crudService = (CRUDservice)context.getBean("crudservice");           
                crudService.create(detailAdhesion);

            } catch (Exception e) {
                com.oxia.fwk.core.Error erreur = new com.oxia.fwk.core.Error();
                StringBuffer text = 
                    new StringBuffer("Erreur dans CreatDetailAdhesionAssVieTrt : ");
                text.append(e.toString());
                erreur.setCode("100");
                erreur.setDescription(text.toString());
                erreur.setKey("CreatAdhesionAssVieTrt");
                detailAdhesion.addError(erreur);
                logger.error("Exception : ",e);   
                throw new RuntimeException(e);  
                
            }  
            return (detailAdhesion);
        }


        public void genCroText(ValueObject vo) {    
        
        }
        
        public String  getNumeroTache (IValueObject vo) {
              return (Constants.CODE_RESSOURCE_GENERALE);     
          }
        
        public IValueObject getNumeroDomaine(IValueObject vo){
            StructureDomaine structureDomaine = new StructureDomaine();
            DetailAdhesion detailAdhesion = (DetailAdhesion)vo;
            structureDomaine.setCodDomDomm(Constants.COD_DOM_CLIENT);
            structureDomaine.setCodStrcStrc(detailAdhesion.getContratCpt().getContratCptId().getCodStrcStrc());
            return structureDomaine;
        }

    }
