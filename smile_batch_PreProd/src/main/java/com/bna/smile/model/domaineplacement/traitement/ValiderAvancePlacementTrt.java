package com.bna.smile.model.domaineplacement.traitement;

import com.bna.commun.model.AvancRembLiquid;
import com.bna.commun.model.ContratCpt;
import com.bna.commun.model.ContratCptId;
import com.bna.commun.model.DetailsOperationPlacement;
import com.bna.commun.model.MandPersOperPlac;
//import com.bna.commun.model.Personnel;
import com.bna.commun.model.MandatOperation;
import com.bna.commun.model.OperationMoyPay;
import com.bna.commun.model.StructureDomaine;
import com.bna.commun.model.Tache;
import com.bna.commun.model.TacheId;
import com.bna.commun.model.TraceMandat;
import com.bna.commun.service.ICrudService;
import com.bna.commun.traitements.InsertOperationMoyPayTrt;
import com.bna.commun.traitements.Traitement;
import com.bna.commun.traitements.UpdateSoldTrt;
import com.bna.commun.util.ContextHandler;
import com.bna.commun.util.StrHandler;
import com.bna.commun.vo.ContratCptSold;
import com.bna.smile.model.constant.Constants;
import com.bna.smile.model.domainecontratcompte.procuration.traitement.UpdateMandatOperationTrt;
import com.bna.smile.model.domaineplacement.model.OperationMoyPayAbonnement;
import com.bna.smile.model.domaineplacement.model.ParamAbonnementement;
import com.bna.smile.model.domaineplacement.model.ParamContratPlacement;

import com.oxia.fwk.context.Context;
import com.oxia.fwk.core.ISearchEngine;
import com.oxia.fwk.core.IValueObject;
import com.oxia.fwk.core.ValueObject;

//import com.oxia.security.abc.model.Personnel;

import com.oxia.fwk.searchengine.SearchEngine;

import java.text.SimpleDateFormat;

import java.util.Date;
import java.util.Iterator;

import org.acegisecurity.context.SecurityContextHolder;
import org.acegisecurity.userdetails.UserDetails;

import org.springframework.orm.hibernate3.HibernateTemplate;

public class ValiderAvancePlacementTrt extends Traitement{
    public ValiderAvancePlacementTrt() {
    }
    
    
    public IValueObject perform (IValueObject vo ) {     
     
    Context context = ContextHandler.getContext();
    AvancRembLiquid avancRembLiquid = (AvancRembLiquid)vo;             
    ICrudService crudService =(ICrudService)context.getBean("CURService");
         
       try{ 

            this.setCroFlag(false);  
                ///----------- création détails opération  ----------------------                
                DetailsOperationPlacement detailsOperationPlacement        = new DetailsOperationPlacement();
                DetailsOperationPlacement ancienDetailsOperationPlacement  = new DetailsOperationPlacement();
                if(!avancRembLiquid.getDetailsOperationPlacements().equals(null)){
                
                    for (Iterator it = avancRembLiquid.getDetailsOperationPlacements().iterator();it.hasNext(); ) { 
                        DetailsOperationPlacement detailsOperationPlacementTemp = (DetailsOperationPlacement)it.next();
                        if (detailsOperationPlacementTemp.getNumSeqDopl()==null){
                            detailsOperationPlacement =detailsOperationPlacementTemp;
                            detailsOperationPlacement.setAvancRembLiquid(avancRembLiquid);
                            detailsOperationPlacement.setContratPlacement(avancRembLiquid.getContratPlacement());                            
 
                           //break;
                        }else ancienDetailsOperationPlacement=detailsOperationPlacementTemp;

                    }

                    ///----------- création opération Moyen de payement ----------------------                
                    if(ancienDetailsOperationPlacement.getTypePieceByCodTpssTpce() != null){
                        detailsOperationPlacement.getOperationMoyPay().setTypePieceDemandeur(ancienDetailsOperationPlacement.getTypePieceByCodTpssTpce());
                        detailsOperationPlacement.getOperationMoyPay().setNumPcedOmp(ancienDetailsOperationPlacement.getNumNpssDopl());
                    }
                    if(ancienDetailsOperationPlacement.getMandPersOperPlacs()!=null && ancienDetailsOperationPlacement.getMandPersOperPlacs().size()>0 ){
                        if (detailsOperationPlacement.getMandatOperation()!=null){ ///*** mandat special (maj enveloppe utilisée)
                            detailsOperationPlacement.getMandatOperation().setMontUtilMaop(detailsOperationPlacement.getMandatOperation().getMontUtilMaop()+avancRembLiquid.getMontArlArl());
                            UpdateMandatOperationTrt updateMandatOperationTrt =new UpdateMandatOperationTrt();
                            MandatOperation mandatOperation = (MandatOperation)updateMandatOperationTrt.exec(detailsOperationPlacement.getMandatOperation()) ;
                            detailsOperationPlacement.getOperationMoyPay().setMandatOperation(mandatOperation);
                        }
                    }
                    
                    if(ancienDetailsOperationPlacement.getMandPersOperPlacs().size()>0){
                        detailsOperationPlacement.getOperationMoyPay().setCodDemOmp("M");
                    }else if(ancienDetailsOperationPlacement.getCoTitulaire()!=null){
                            detailsOperationPlacement.getOperationMoyPay().setCodDemOmp("C");                        
                        }
                    
                    InsertOperationMoyPayAvancePlacTrt insertOperationMoyPayAvancePlacTrt = new InsertOperationMoyPayAvancePlacTrt();
                    if (avancRembLiquid.getContratPlacement().getNumBcCpla() != null){
                    detailsOperationPlacement.getOperationMoyPay().setCodRefbOmp(detailsOperationPlacement.getOperationMoyPay().getCodRefbOmp()+"/"+avancRembLiquid.getContratPlacement().getNumBcCpla());
                    }else{
                        detailsOperationPlacement.getOperationMoyPay().setCodRefbOmp(detailsOperationPlacement.getOperationMoyPay().getCodRefbOmp());                        
                    }
                    ///detailsOperationPlacement.getOperationMoyPay().setCodRefbOmp(detailsOperationPlacement.getOperationMoyPay().getCodRefbOmp());
                    detailsOperationPlacement.getOperationMoyPay().setCodRefmOmp(detailsOperationPlacement.getAvancRembLiquid().getNumSeqArl().toString());///                    
                    OperationMoyPay operationMoyPay = (OperationMoyPay)insertOperationMoyPayAvancePlacTrt.exec(detailsOperationPlacement);

                    ///----------- création opération Moyen de payement (interet) ----------------------                
                    HibernateTemplate  hibernateTemplate = (HibernateTemplate) context.getBean("hibernateTemplate");
                    hibernateTemplate.evict(operationMoyPay);
                    
                    OperationMoyPay operationMoyPay1 = new OperationMoyPay();
                    operationMoyPay1.setNumOperOmp(operationMoyPay.getNumOperOmp());
                    operationMoyPay.setOperationMoyPayM(operationMoyPay1);
                    detailsOperationPlacement.setOperationMoyPay(operationMoyPay1);
                    
                    Tache tache = new Tache();
                    TacheId tacheId = new TacheId();
                    tacheId.setCodTachTach(Constants.COD_TACHE_INTERET_AVANC_PLAC);
                    tacheId.setCodOperOper(Constants.COD_OPER_PERSEPT_INTERET_AVANCE_PLAC_LIQ);
                    tache.setTacheId(tacheId);
                    operationMoyPay.setTache(tache);
                    operationMoyPay.setMontDinOmp(new Long(new Double(new Double(avancRembLiquid.getMontInetArl()).doubleValue()).longValue()));
                    operationMoyPay.setCodSensOmp(Constants.COD_SENS_DB);
                    operationMoyPay.setDatValOmp(avancRembLiquid.getDatValiArl());///*** date valeur interet
                    operationMoyPay.setMontSoldCcpt(operationMoyPay.getMontApreOmp()); 
                    operationMoyPay.setMontApreOmp(Long.valueOf(new Long(new Double(((new Double(operationMoyPay.getMontApreOmp()).doubleValue())-(new Double(avancRembLiquid.getMontInetArl()).doubleValue())) ).longValue())));
                   
                    OperationMoyPayAbonnement operationMoyPayAbonnement =new OperationMoyPayAbonnement();
                    operationMoyPayAbonnement.setOperationMoyPay(operationMoyPay);
                    InsertOperationMoyPayInteretPlacTrt insertOperationMoyPayInteretPlacTrt = new InsertOperationMoyPayInteretPlacTrt();
                    operationMoyPay = (OperationMoyPay)insertOperationMoyPayInteretPlacTrt.exec(operationMoyPayAbonnement);


                    ///*** MAJ du montant actualisé dans le contrat compte                    
                    ContratCptId contratCptId =operationMoyPay.getContratCpt().getContratCptId();
                    ISearchEngine searchEngine = (SearchEngine)context.getBean("searchEngine");
                    /* Charger le ContratCpt existante */
                    ContratCpt contratCpt =(ContratCpt) searchEngine.get(ContratCpt.class,contratCptId);
                    ContratCptSold contratCptSold = new ContratCptSold();
                    contratCptSold.setContratCpt(contratCpt);
                    contratCptSold.setSolde(Long.valueOf(new Long(new Double(((new Double(avancRembLiquid.getMontArlArl()).doubleValue())-(new Double(avancRembLiquid.getMontInetArl()).doubleValue())) ).longValue())));
                    contratCptSold.setSens("C");
                    UpdateSoldTrt updateSoldTrt = new UpdateSoldTrt();
                    contratCpt = (ContratCpt)updateSoldTrt.exec(contratCptSold);

///*** a faire  avancRembLiquid.getDetailsOperationPlacement().setRefOperationMoyPay
                    crudService.create(detailsOperationPlacement);         
                } 
           ///--------------------------------------------------------------
           ///----------- MAJ avance sur capital  et affectation détails opération ----------------------
           ///--------------------------------------------------------------
                
            if(!avancRembLiquid.equals(null)){
                ///*** MAJ du montant actualisé dans le contrat placement
                if(!avancRembLiquid.getContratPlacement().equals(null)){
                    crudService.update(avancRembLiquid.getContratPlacement()); 
                }
                ///*** Insertion de mand_pers_oper_plac du montant actualisé dans le contrat placement
                if(ancienDetailsOperationPlacement.getMandPersOperPlacs()!=null && ancienDetailsOperationPlacement.getMandPersOperPlacs().size()>0 ){
                    InsertMandPersOperPlacTrt insertMandPersOperPlacTrt = new InsertMandPersOperPlacTrt();
                    for (Iterator it = ancienDetailsOperationPlacement.getMandPersOperPlacs().iterator();it.hasNext(); ) { 
                        MandPersOperPlac mandPersOperPlac = (MandPersOperPlac)it.next();
                        mandPersOperPlac.getMandPersOperPlacId().setNumSeqDopl(detailsOperationPlacement.getNumSeqDopl());

                        HibernateTemplate  hibernateTemplate = (HibernateTemplate) context.getBean("hibernateTemplate");
                        hibernateTemplate.evict(mandPersOperPlac);

                        mandPersOperPlac = (MandPersOperPlac)insertMandPersOperPlacTrt.exec(mandPersOperPlac);
                    }
                }
            } 
            if (avancRembLiquid.getNumSeqArl()!=null){
                crudService.update(avancRembLiquid); 
            }else{
                HibernateTemplate  hibernateTemplate = (HibernateTemplate) context.getBean("hibernateTemplate");
                hibernateTemplate.evict(avancRembLiquid);
                crudService.create(avancRembLiquid);
            }
            
            ///*---------------------------- tester la generation de l'abonnement ------
             ParamAbonnementement paramAbonnementement = new ParamAbonnementement();
             paramAbonnementement.setMontItotAbpl(Long.valueOf(avancRembLiquid.getMontInetArl().longValue()));
             paramAbonnementement.setDatDebAbpl(avancRembLiquid.getDatArlArl());
             paramAbonnementement.setDatFinAbpl(avancRembLiquid.getContratPlacement().getDatEcheCpla());
             paramAbonnementement.setDatPrevAbpl(avancRembLiquid.getDatPrevArl());
             paramAbonnementement.setMontTotAbpl(Long.valueOf(avancRembLiquid.getMontArlArl().longValue()));
             paramAbonnementement.setNumSeqArl(avancRembLiquid.getNumSeqArl());
             paramAbonnementement.setTypeOperation("A");
             paramAbonnementement.setNumTauiCpla(avancRembLiquid.getNumTauiArl());
             if (avancRembLiquid.getContratPlacement()!=null)
                paramAbonnementement.setContratPlacement(avancRembLiquid.getContratPlacement());
             GenererAbonnementTrt genererAbonnementTrt = new GenererAbonnementTrt();
             paramAbonnementement = (ParamAbonnementement)genererAbonnementTrt.exec(paramAbonnementement);
            
            ///*------------------------------------------------------------------------

        }
                  
        
         catch (Exception e) {
                com.oxia.fwk.core.Error erreur=new com.oxia.fwk.core.Error();
                erreur.setCode("Technique");
                erreur.setDescription("ValiderAvancePlacementTrt  "+e.getMessage());;
                avancRembLiquid.addError(erreur);
                logger.error("Exception : ",e);   
                throw new RuntimeException(e);
        } 
        return (avancRembLiquid);
    }
    
    public void genCroText(ValueObject vo) {
            
        }   

    public IValueObject getNumeroDomaine(IValueObject vo){
        StructureDomaine structureDomaine = new StructureDomaine();
        AvancRembLiquid avancRembLiquid = (AvancRembLiquid)vo;             
        structureDomaine.setCodDomDomm(Constants.COD_DOM_PLACEMENT);
        structureDomaine.setCodStrcStrc(avancRembLiquid.getContratPlacement().getContratCpt().getContratCptId().getCodStrcStrc());
        return structureDomaine;
    }

    public String getNumeroTache(IValueObject vo) {
        
        return(Constants.COD_OPER_AVANCE_PLAC.toString()+
        StrHandler.lpad(Constants.COD_TACHE_VALID_AVANC_PLAC.toString(),'0',2));
    }


}
