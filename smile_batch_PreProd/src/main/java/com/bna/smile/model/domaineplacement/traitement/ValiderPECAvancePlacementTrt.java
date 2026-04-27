package com.bna.smile.model.domaineplacement.traitement;


import com.bna.commun.model.AvancRembLiquid;
import com.bna.commun.model.ContratCpt;
import com.bna.commun.model.ContratCptId;
import com.bna.commun.model.ContratPlacement;
import com.bna.commun.model.MandPersOperPlac;
import com.bna.commun.model.OperationMoyPay;

import com.bna.commun.model.StructureDomaine;
import com.bna.commun.model.Tache;
import com.bna.commun.model.TacheId;
import com.bna.commun.model.TraceMandat;
import com.bna.commun.service.ICrudService;
import com.bna.commun.traitements.Traitement;

import com.bna.smile.model.constant.Constants;
import com.bna.commun.traitements.InsertOperationMoyPayTrt;
import com.bna.commun.traitements.UpdateSoldTrt;
import com.bna.commun.util.ContextHandler;
import com.bna.commun.util.DateHandler;
import com.bna.commun.util.StrHandler;
import com.bna.commun.vo.ContratCptSold;

import com.bna.smile.model.domaineplacement.model.OperationMoyPayAbonnement;
import com.bna.smile.model.domaineplacement.model.ParamAbonnementement;
import com.bna.smile.model.domaineplacement.model.ParamContratPlacement;

import com.oxia.fwk.context.Context;

import com.oxia.fwk.core.ISearchEngine;
import com.oxia.fwk.core.IValueObject;
import com.oxia.fwk.core.ValueObject;

import com.oxia.fwk.searchengine.SearchEngine;

import com.oxia.security.abc.model.Personnel;

import java.text.SimpleDateFormat;

import java.util.Date;
import java.util.Iterator;

import org.acegisecurity.context.SecurityContextHolder;
import org.acegisecurity.userdetails.UserDetails;

import org.springframework.orm.hibernate3.HibernateTemplate;

/**
 * validation d'une avance sur capital .
 * @param ParamContratPlacement
 * @return AvancRembLiquid
 * 
 */
public class ValiderPECAvancePlacementTrt extends Traitement{
    public ValiderPECAvancePlacementTrt() {
    }
        
    public IValueObject perform (IValueObject vo ) {     
     
    Context context = ContextHandler.getContext();
    ParamContratPlacement paramContratPlacement = (ParamContratPlacement)vo;             
    ICrudService crudService =(ICrudService)context.getBean("CURService");
             
       try{ 
           ///------------------------------------------------------------------------------------------------
           ///----------- création avance sur capital  et affectation détails opération ----------------------
           ///------------------------------------------------------------------------------------------------
                
            if(!paramContratPlacement.getAvancRembLiquid().equals(null)){
                crudService.create(paramContratPlacement.getAvancRembLiquid());  
                
                OperationMoyPay operationMoyPay =new OperationMoyPay();
                
                if (paramContratPlacement.getAvancRembLiquid().getCodToprArl().equalsIgnoreCase(Constants.CODE_REMBOURSEMENT_AVANCE)){
                    if (paramContratPlacement.getAvancRembLiquid().getContratPlacement().getNumBcCpla()!=null){
                        paramContratPlacement.getDetailsOperationPlacement().getOperationMoyPay().setCodRefbOmp(paramContratPlacement.getDetailsOperationPlacement().getOperationMoyPay().getCodRefbOmp()+"/"+paramContratPlacement.getAvancRembLiquid().getContratPlacement().getNumBcCpla());
                    }else{
                        paramContratPlacement.getDetailsOperationPlacement().getOperationMoyPay().setCodRefbOmp(paramContratPlacement.getDetailsOperationPlacement().getOperationMoyPay().getCodRefbOmp());                        
                    }
                    ///paramContratPlacement.getDetailsOperationPlacement().getOperationMoyPay().setCodRefbOmp(paramContratPlacement.getDetailsOperationPlacement().getOperationMoyPay().getCodRefbOmp());
                    paramContratPlacement.getDetailsOperationPlacement().getOperationMoyPay().setCodRefmOmp(paramContratPlacement.getAvancRembLiquid().getNumSeqArl().toString());///
                    InsertOperationMoyPayRembAvancPlacTrt insertOperationMoyPayRembAvancPlacTrt = new InsertOperationMoyPayRembAvancPlacTrt();
                     operationMoyPay = (OperationMoyPay)insertOperationMoyPayRembAvancPlacTrt.exec(paramContratPlacement);
                    
                    ///*** MAJ du montant actualisé dans le contrat placement
                    if(!paramContratPlacement.getAvancRembLiquid().getContratPlacement().equals(null)){
                        crudService.update(paramContratPlacement.getAvancRembLiquid().getContratPlacement()); 
                    }

                    ///----------- création opération Moyen de payement (interet) ----------------------                
                     if(!paramContratPlacement.getAvancRembLiquid().getCodTypiArl().equalsIgnoreCase("")){///*** remboursement a temps (pas d'interet)
                        Long numSeqArlVar = paramContratPlacement.getAvancRembLiquid().getAvancRembLiquid().getNumSeqArl();
                        HibernateTemplate  hibernateTemplate = (HibernateTemplate) context.getBean("hibernateTemplate");
                        hibernateTemplate.evict(operationMoyPay);

                        OperationMoyPay operationMoyPay1 = new OperationMoyPay();
                        operationMoyPay1.setNumOperOmp(operationMoyPay.getNumOperOmp());
                        operationMoyPay.setOperationMoyPayM(operationMoyPay1);
                        ///??paramContratPlacement.getDetailsOperationPlacement().setOperationMoyPay(operationMoyPay1);
                         
                        operationMoyPay.setMontDinOmp(new Long(new Double(new Double(paramContratPlacement.getAvancRembLiquid().getMontInetArl()).doubleValue()).longValue()));
                        Tache tache = new Tache();
                        TacheId tacheId = new TacheId();
                        tacheId.setCodTachTach(Constants.COD_TACHE_INTERET_AVANC_PLAC);
                        operationMoyPay.setMontSoldCcpt(operationMoyPay.getMontApreOmp());
                        if(paramContratPlacement.getAvancRembLiquid().getCodTypiArl().equalsIgnoreCase("S")){///*** remboursement anticipé
                            operationMoyPay.setMontApreOmp(Long.valueOf(new Long(new Double(((new Double(operationMoyPay.getMontApreOmp()).doubleValue())+(new Double(paramContratPlacement.getAvancRembLiquid().getMontInetArl()).doubleValue())) ).longValue())));
                            tacheId.setCodOperOper(Constants.COD_OPER_REMB_INTERET_REMB_AVANCE_PLAC);
                            tache.setTacheId(tacheId);
                            operationMoyPay.setTache(tache);
                            operationMoyPay.setCodSensOmp(Constants.COD_SENS_CR);                    
                        }else{
                            operationMoyPay.setMontApreOmp(Long.valueOf(new Long(new Double(((new Double(operationMoyPay.getMontApreOmp()).doubleValue())-(new Double(paramContratPlacement.getAvancRembLiquid().getMontInetArl()).doubleValue())) ).longValue())));                        
                            tacheId.setCodOperOper(Constants.COD_OPER_PERSEPT_INTERET_REMB_AVANCE_PLAC);
                            tache.setTacheId(tacheId);
                            operationMoyPay.setTache(tache);
                            operationMoyPay.setCodSensOmp(Constants.COD_SENS_DB);
                        }
                        operationMoyPay.setDatValOmp(paramContratPlacement.getAvancRembLiquid().getDatValiArl());///*** date valeur interet
                       // operationMoyPay.setDatValOmp(new Date());///??????? a enlever

                        AvancRembLiquid avancRembLiquid=new AvancRembLiquid();
                        avancRembLiquid.setNumSeqArl(numSeqArlVar);
                        GetAvancRembLiquidByIdTrt getAvancRembLiquidById= new GetAvancRembLiquidByIdTrt();
                        avancRembLiquid = (AvancRembLiquid)getAvancRembLiquidById.exec(avancRembLiquid);
                        ///*---------------------------- tester le réajustement de l'abonnement ------
                        ParamAbonnementement paramAbonnementement = new ParamAbonnementement();
                        gererAbonnement(paramContratPlacement, avancRembLiquid, paramAbonnementement);               
                        ///*------------------------------------------------------------------------
                        OperationMoyPayAbonnement operationMoyPayAbonnement =new OperationMoyPayAbonnement();
                        operationMoyPayAbonnement.setOperationMoyPay(operationMoyPay);
                        operationMoyPayAbonnement.setParamAbonnementement(paramAbonnementement);
                        InsertOperationMoyPayInteretPlacTrt insertOperationMoyPayInteretPlacTrt =new InsertOperationMoyPayInteretPlacTrt();
                        operationMoyPay = (OperationMoyPay)insertOperationMoyPayInteretPlacTrt.exec(operationMoyPayAbonnement);
                     }else{///*** cas d'un remboursement a temps
                         Long numSeqArlVar = paramContratPlacement.getAvancRembLiquid().getAvancRembLiquid().getNumSeqArl();
                         AvancRembLiquid avancRembLiquid=new AvancRembLiquid();
                         avancRembLiquid.setNumSeqArl(numSeqArlVar);
                         GetAvancRembLiquidByIdTrt getAvancRembLiquidById= new GetAvancRembLiquidByIdTrt();
                         avancRembLiquid = (AvancRembLiquid)getAvancRembLiquidById.exec(avancRembLiquid);
                         ///*---------------------------- tester le réajustement de l'abonnement ------
                         ParamAbonnementement paramAbonnementement = new ParamAbonnementement();
                         gererAbonnement(paramContratPlacement, avancRembLiquid, paramAbonnementement);               
                         
                     }

                    ///*** MAJ du montant actualisé dans le contrat compte                    
                    ContratCptId contratCptId =operationMoyPay.getContratCpt().getContratCptId();
                    ISearchEngine searchEngine = (SearchEngine)context.getBean("searchEngine");
                    /* Charger le ContratCpt existante */
                    ContratCpt contratCpt =(ContratCpt) searchEngine.get(ContratCpt.class,contratCptId);
                    ContratCptSold contratCptSold = new ContratCptSold();
                    contratCptSold.setContratCpt(contratCpt);
                    if(paramContratPlacement.getAvancRembLiquid().getCodTypiArl().equalsIgnoreCase("S")){///*** remboursement anticipé
                        contratCptSold.setSolde(Long.valueOf(new Long(new Double(((new Double(paramContratPlacement.getAvancRembLiquid().getMontArlArl()).doubleValue())-(new Double(paramContratPlacement.getAvancRembLiquid().getMontInetArl()).doubleValue())) ).longValue())));
                    }else{///*** remboursement en retard
                        contratCptSold.setSolde(Long.valueOf(new Long(new Double(((new Double(paramContratPlacement.getAvancRembLiquid().getMontArlArl()).doubleValue())+(new Double(paramContratPlacement.getAvancRembLiquid().getMontInetArl()).doubleValue())) ).longValue())));                        
                    }
                    contratCptSold.setSens("D");
                    UpdateSoldTrt updateSoldTrt = new UpdateSoldTrt();
                    contratCpt = (ContratCpt)updateSoldTrt.exec(contratCptSold);
                    
                    
                }
                
                ///----------- création détails opération  ----------------------                
                if(!paramContratPlacement.getDetailsOperationPlacement().equals(null)){
                    paramContratPlacement.getDetailsOperationPlacement().setAvancRembLiquid(paramContratPlacement.getAvancRembLiquid());
                    if (operationMoyPay.getOperationMoyPayM()!=null) 
                    paramContratPlacement.getDetailsOperationPlacement().setOperationMoyPay(operationMoyPay.getOperationMoyPayM());
                    
                    crudService.create(paramContratPlacement.getDetailsOperationPlacement());         
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
                ///*** MAJ de la date reel de remboursememnt dans la table AvancRembLiquid
                    AvancRembLiquid avancRembLiquid=new AvancRembLiquid();
                if (paramContratPlacement.getAvancRembLiquid().getCodToprArl().equalsIgnoreCase(Constants.CODE_REMBOURSEMENT_AVANCE)){
                    avancRembLiquid.setNumSeqArl(paramContratPlacement.getAvancRembLiquid().getAvancRembLiquid().getNumSeqArl());
                    GetAvancRembLiquidByIdTrt getAvancRembLiquidById= new GetAvancRembLiquidByIdTrt();
                    avancRembLiquid = (AvancRembLiquid)getAvancRembLiquidById.exec(avancRembLiquid);
                    avancRembLiquid.setDatReelArl(new Date());
                    ///System.out.println("  *** Date comptable  :   "+paramContratPlacement.getDetailsOperationPlacement().getDatCompDopl());
                    ///avancRembLiquid.setDatArlArl(paramContratPlacement.getDetailsOperationPlacement().getDatCompDopl());
                    crudService.update(avancRembLiquid); 
                }
            
                  
           }
         catch (Exception e) {
                com.oxia.fwk.core.Error erreur=new com.oxia.fwk.core.Error();
                erreur.setCode("Technique");
                erreur.setDescription("ValiderPECAvancePlacementTrt  "+e.getMessage());;
                paramContratPlacement.getAvancRembLiquid().addError(erreur);
                logger.error("Exception : ",e);   
                throw new RuntimeException(e);
        } 
        return (paramContratPlacement.getAvancRembLiquid());
    }

    private void gererAbonnement(ParamContratPlacement paramContratPlacement, 
                                 AvancRembLiquid avancRembLiquid, 
                                 ParamAbonnementement paramAbonnementement) {
        if (paramContratPlacement.getAvancRembLiquid().getCodToprArl().equalsIgnoreCase(Constants.CODE_REMBOURSEMENT_AVANCE)){
            paramAbonnementement.setDatDebArl(avancRembLiquid.getDatArlArl());///***
            paramAbonnementement.setDatDebAbpl(paramContratPlacement.getAvancRembLiquid().getDatArlArl());
            if (paramContratPlacement.getAvancRembLiquid().getCodTypiArl().equalsIgnoreCase("S")){
                paramAbonnementement.setDatFinAbpl(paramContratPlacement.getAvancRembLiquid().getDatPrevArl());
                paramAbonnementement.setMontRembAbpl(Long.valueOf(paramContratPlacement.getAvancRembLiquid().getMontInetArl().longValue()));
            }else{
                paramAbonnementement.setDatFinAbpl(paramContratPlacement.getDetailsOperationPlacement().getDatCompDopl());
            }
            paramAbonnementement.setDateCompAgence(paramContratPlacement.getDetailsOperationPlacement().getDatCompDopl());
            paramContratPlacement.getAvancRembLiquid().setAvancRembLiquid(avancRembLiquid);
            paramAbonnementement.setMontTotAbpl(Long.valueOf(paramContratPlacement.getAvancRembLiquid().getMontArlArl().longValue()));
            paramAbonnementement.setNumSeqArl(paramContratPlacement.getAvancRembLiquid().getNumSeqArl());
            paramAbonnementement.setTypeOperation("A");
            paramAbonnementement.setNumTauiCpla(paramContratPlacement.getAvancRembLiquid().getNumTauiArl());
            paramAbonnementement.setAvancRembLiquid(paramContratPlacement.getAvancRembLiquid());
 
            GetContratPlacementTrt getContratPlacementTrt =new GetContratPlacementTrt();
            paramAbonnementement.setContratPlacement((ContratPlacement)getContratPlacementTrt.exec(paramAbonnementement.getAvancRembLiquid().getContratPlacement()));

            ReajusterAbonnementTrt reajusterAbonnementTrt = new ReajusterAbonnementTrt();
            paramAbonnementement = (ParamAbonnementement)reajusterAbonnementTrt.exec(paramAbonnementement);
        }
    }
    
    public void genCroText(ValueObject vo) {
            
        }   

    public IValueObject getNumeroDomaine(IValueObject vo){
        StructureDomaine structureDomaine = new StructureDomaine();
        ParamContratPlacement paramContratPlacement = (ParamContratPlacement)vo;             
        structureDomaine.setCodDomDomm(Constants.COD_DOM_PLACEMENT);
        structureDomaine.setCodStrcStrc(paramContratPlacement.getDetailsOperationPlacement().getStructure().getCodStrcStrc());
        return structureDomaine;
    }

    public String getNumeroTache(IValueObject vo) {
        ParamContratPlacement paramContratPlacement = (ParamContratPlacement)vo;             
        
        return(paramContratPlacement.getDetailsOperationPlacement().getTache().getTacheId().getCodOperOper().toString()+
        StrHandler.lpad(paramContratPlacement.getDetailsOperationPlacement().getTache().getTacheId().getCodTachTach().toString(),'0',2));
    }
    

}
