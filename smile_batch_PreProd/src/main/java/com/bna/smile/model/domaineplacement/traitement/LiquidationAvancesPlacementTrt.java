package com.bna.smile.model.domaineplacement.traitement;

import com.bna.commun.model.AvancRembLiquid;
import com.bna.commun.model.ContratCpt;
import com.bna.commun.model.ContratPlacement;
import com.bna.commun.model.DetailsOperationPlacement;
import com.bna.commun.model.Devise;
import com.bna.commun.model.Operation;
import com.bna.commun.model.OperationMoyPay;
import com.bna.commun.model.Personnel;
import com.bna.commun.model.Produit;
import com.bna.commun.model.Structure;
import com.bna.commun.model.Tache;
import com.bna.commun.model.TacheId;
import com.bna.commun.model.TypePiece;
import com.bna.commun.traitements.Traitement;
import com.bna.commun.util.ContextHandler;
import com.bna.commun.util.DateHandler;
import com.bna.commun.util.StrHandler;
import com.bna.commun.util.TraitementConditionBanque;
import com.bna.smile.model.constant.Constants;
import com.bna.smile.model.domainecommun.commande.GetDetailContratCmd;

import com.bna.smile.model.domaineplacement.model.ParamContratPlacement;
import com.bna.smile.model.domaineplacement.model.ParamLiquidation;

import com.oxia.fwk.context.Context;
import com.oxia.fwk.core.ICriteria;
import com.oxia.fwk.core.IExpression;
import com.oxia.fwk.core.ISearchEngine;
import com.oxia.fwk.core.IValueObject;

import com.oxia.fwk.core.ValueObject;
import com.oxia.fwk.searchengine.SearchEngine;

import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.springframework.orm.hibernate3.HibernateTemplate;

public class LiquidationAvancesPlacementTrt extends Traitement{
    public LiquidationAvancesPlacementTrt() {
    }

    Context context = ContextHandler.getContext();
    ISearchEngine searchEngine = (SearchEngine)context.getBean("searchEngine");
    ICriteria criteria = searchEngine.createCriteria();
    ICriteria criteriaPlac = searchEngine.createCriteria();
    ICriteria criteriaAvanc = searchEngine.createCriteria();
    IExpression expression = searchEngine.createExpression();

    
    public IValueObject perform(IValueObject vo) {
        
        this.setVerifDomaine(false);
        ParamLiquidation paramLiquidation = (ParamLiquidation)vo;
        AvancRembLiquid avancRembLiquidretour = new AvancRembLiquid();
    try{
    
           GetDetailContratCmd getDetailContratCmd = new GetDetailContratCmd();
           ContratCpt cpt = (ContratCpt)getDetailContratCmd.execute(paramLiquidation.getContratPlacement().getContratCpt().getContratCptId());
           paramLiquidation.setContratCpt(cpt);
           paramLiquidation.setSoldeCptAvant(cpt.getMontSoldCcpt());
           
           criteriaAvanc.add(expression.eq("contratPlacement.numSeqCpla",paramLiquidation.getContratPlacement().getNumSeqCpla()));
           criteriaAvanc.add(expression.isNull("datReelArl"));
           criteriaAvanc.add(expression.eq("codEtatArl","V"));
           criteriaAvanc.add(expression.eq("codToprArl",Constants.CODE_AVANCE));
           List listavancNRemb=searchEngine.find(AvancRembLiquid.class,criteriaAvanc);
    
           if (listavancNRemb!=null && listavancNRemb.size()>0 ){
               ///*** calcul cond bq pour le contrat (Remboursement et interet)
               TraitementConditionBanque traitementConditionBanqueRembAvance = getCB(paramLiquidation.getContratPlacement(),Constants.COD_OPER_REMB_AVANCE_PLAC,paramLiquidation);                       
               TraitementConditionBanque traitementConditionBanqueInteretPercuRembAvance = getCB(paramLiquidation.getContratPlacement(),Constants.COD_OPER_PERSEPT_INTERET_REMB_AVANCE_PLAC,paramLiquidation);                       
               TraitementConditionBanque traitementConditionBanqueInteretServiRembAvance = getCB(paramLiquidation.getContratPlacement(),Constants.COD_OPER_REMB_INTERET_REMB_AVANCE_PLAC,paramLiquidation);                       
               paramLiquidation.setDateValRemb(DateHandler.strToDate(traitementConditionBanqueRembAvance.getDatevaleur()));
               paramLiquidation.setDateValPercInteret(DateHandler.strToDate(traitementConditionBanqueInteretPercuRembAvance.getDatevaleur()));
               paramLiquidation.setDateValServInteret(DateHandler.strToDate(traitementConditionBanqueInteretServiRembAvance.getDatevaleur()));
    
               for (Iterator it2 = listavancNRemb.iterator(); it2.hasNext(); ){    
                   AvancRembLiquid avancRembLiquid=(AvancRembLiquid)it2.next();
                   avancRembLiquidretour = rembourserAvance(avancRembLiquid,paramLiquidation);
               }
           }
       }catch (Exception e) {
           com.oxia.fwk.core.Error erreur = new com.oxia.fwk.core.Error();
           StringBuffer text = new StringBuffer("Erreur dans Liquidation-traiterPlacement : ");
           text.append(e.getMessage());
           erreur.setCode("100");
           erreur.setDescription(text.toString());
           erreur.setKey("Liquidation-traiterPlacement");
           logger.error("Exception : ",e);   
           throw new RuntimeException(e);
                            
       } 
       return  avancRembLiquidretour;
    }
    
    
    public AvancRembLiquid rembourserAvance(AvancRembLiquid avance,ParamLiquidation paramLiquidation){
    
            AvancRembLiquid avancRembLiquidretour = new AvancRembLiquid();
     try{
            ParamContratPlacement paramContratPlacement =  new ParamContratPlacement();
        
            AvancRembLiquid Remboursement= affecterDonneesAvancRembLiquid(avance,paramLiquidation);
            DetailsOperationPlacement detailsOperationPlacement = affecterDonneesDetailsOperationPlacement(Remboursement,paramLiquidation);
            OperationMoyPay operationMoyPay = affecterDonneesOperationMoyenPaiement(Remboursement,paramLiquidation);
                
            paramContratPlacement.setAvancRembLiquid(Remboursement);
            detailsOperationPlacement.setOperationMoyPay(operationMoyPay);
            paramContratPlacement.setDetailsOperationPlacement(detailsOperationPlacement);  
        
             HibernateTemplate  hibernateTemplate = (HibernateTemplate) context.getBean("hibernateTemplate");
             hibernateTemplate.evict(avance);
            
            ValiderPECAvancePlacementTrt validerPECAvancePlacementTrt=new ValiderPECAvancePlacementTrt();
            validerPECAvancePlacementTrt.setVerifDomaine(false);
            avancRembLiquidretour=(AvancRembLiquid)validerPECAvancePlacementTrt.exec(paramContratPlacement);
            
            paramLiquidation.setSoldeCptAvant(operationMoyPay.getMontApreOmp());

        }catch (Exception e) {
            com.oxia.fwk.core.Error erreur = new com.oxia.fwk.core.Error();
            StringBuffer text = new StringBuffer("Erreur dans Liquidation-rembourserAvance : ");
            text.append(e.getMessage());
            erreur.setCode("100");
            erreur.setDescription(text.toString());
            erreur.setKey("Liquidation-rembourserAvance");
            logger.error("Exception : ",e);   
            avancRembLiquidretour.addError(erreur);
            throw new RuntimeException(e);
                             
        } 
        return avancRembLiquidretour;
    }
    
    public DetailsOperationPlacement affecterDonneesDetailsOperationPlacement(AvancRembLiquid avance, ParamLiquidation paramLiquidation) {

        DetailsOperationPlacement detailsOperationPlacement =  new DetailsOperationPlacement();

    try {
            detailsOperationPlacement.setDatOperDopl(new Date()); 
            Tache tache = new Tache();
            TacheId tacheId = new TacheId();        
            tacheId.setCodOperOper(Constants.COD_OPER_REMB_AVANCE_PLAC);
            tacheId.setCodTachTach(Constants.COD_TACHE_VALID_REMB_AVANC_PLAC);
            tache.setTacheId(tacheId);                
            detailsOperationPlacement.setTache(tache);
            Personnel personnel = new Personnel();
            personnel.setNumMatrUser("9999");
            detailsOperationPlacement.setPersonnel(personnel);
            Structure structure = new Structure();
            structure.setCodStrcStrc(paramLiquidation.getContratCpt().getContratCptId().getCodStrcStrc());
            detailsOperationPlacement.setStructure(structure);
            detailsOperationPlacement.setDatValDopl(paramLiquidation.getDateValRemb());
            detailsOperationPlacement.setMontDopDopl(avance.getMontArlArl());
            detailsOperationPlacement.setDatCompDopl(paramLiquidation.getDateComptLiquidation());
            detailsOperationPlacement.setContratPlacement(avance.getContratPlacement());
            detailsOperationPlacement.setOperationMoyPay(null);
            detailsOperationPlacement.setTypePieceByCodTpssTpce(paramLiquidation.getContratPlacement().getPersonne().getTypePiece());
            detailsOperationPlacement.setNumNpssDopl(paramLiquidation.getContratPlacement().getPersonne().getNumPcePers());
            
        }catch (Exception e) {
            com.oxia.fwk.core.Error erreur = new com.oxia.fwk.core.Error();
            StringBuffer text = new StringBuffer("Erreur dans Liquidation-affecterDonneesDetailsOperationPlacement : ");
            text.append(e.getMessage());
            erreur.setCode("100");
            erreur.setDescription(text.toString());
            erreur.setKey("Liquidation-affecterDonneesDetailsOperationPlacement");
            logger.error("Exception : ",e);   
            paramLiquidation.addError(erreur);
            throw new RuntimeException(e);
                             
        } 
     return detailsOperationPlacement;
     
    }
    public OperationMoyPay affecterDonneesOperationMoyenPaiement (AvancRembLiquid avance, ParamLiquidation paramLiquidation){
    
        OperationMoyPay operationMoyPay = new OperationMoyPay();          
     try{
        Personnel personnelInit = new Personnel();
        personnelInit.setNumMatrUser("9999");
        Operation operation = new Operation();    

        Structure structureInit = new Structure();    
        structureInit.setCodStrcStrc(paramLiquidation.getContratCpt().getContratCptId().getCodStrcStrc());
        Structure structureRecep = new Structure();    
        structureRecep.setCodStrcStrc(paramLiquidation.getContratCpt().getContratCptId().getCodStrcStrc());

        Devise devise = new Devise();
        devise.setCodDevDev(Constants.COD_DEV_DINAR);
        operationMoyPay.setDevise(devise);
         
        operationMoyPay.setContratCpt(paramLiquidation.getContratCpt());

        operationMoyPay.setStructureInitiatrice(structureInit);
        operationMoyPay.setStructureReceptrice(structureRecep);
        
        if (avance.getContratPlacement().getNumBcCpla()!=null )
        operationMoyPay.setNumMoypOmp(avance.getContratPlacement().getNumBcCpla().toString());
         
        operationMoyPay.setCodEtatOmp(Constants.COD_VALIDATION);
        operationMoyPay.setPersonnelInitiateur(personnelInit);/// personne initiatrice seulement au cas de retrait ponctuel
        operationMoyPay.setPersonnelValideur(personnelInit);/// personnel initiatrice = personnel validateur
        
        operation.setCodOperOper(Constants.COD_OPER_REMB_AVANCE_PLAC);
        
        Tache tache = new Tache();
        tache.setOperation(operation);
        TacheId tacheId = new TacheId();
        tacheId.setCodOperOper(operation.getCodOperOper());
        tacheId.setCodTachTach(Constants.COD_TACHE_VALID_REMB_AVANC_PLAC);
        tache.setTacheId(tacheId);
        operationMoyPay.setTache(tache);
        
        operationMoyPay.setDatOperOmp(paramLiquidation.getDateComptLiquidation());
        operationMoyPay.setDatSystOmp(new Date());
        operationMoyPay.setDatValOmp(paramLiquidation.getDateValRemb());
        Produit produitPlacOmp =new Produit();
            
        operationMoyPay.setCodRefbOmp("N° "+(StrHandler.lpad(avance.getContratPlacement().getNumSeqCpla().toString(),'0',15)).substring(7,15));
        operationMoyPay.setCodRefcOmp((StrHandler.lpad(avance.getContratPlacement().getNumSeqCpla().toString(),'0',15)).substring(7,15));
        produitPlacOmp.setCodPrdPrd(avance.getContratPlacement().getProduitPlacement().getCodPrdPlc());
        operationMoyPay.setProduit(produitPlacOmp);
       
     
        TypePiece typePieceDem =  avance.getContratPlacement().getPersonne().getTypePiece();
        operationMoyPay.setTypePieceDemandeur(typePieceDem);
        operationMoyPay.setNumPcedOmp(avance.getContratPlacement().getPersonne().getNumPcePers());
        operationMoyPay.setNomNomdOmp(avance.getContratPlacement().getPersonne().getNomNomPers());
        operationMoyPay.setNomPrndOmp(avance.getContratPlacement().getPersonne().getNomPrnPers());
        operationMoyPay.setCodSensOmp(Constants.COD_SENS_DB);
        operationMoyPay.setMontDinOmp(avance.getMontArlArl());
        operationMoyPay.setMontSoldCcpt(paramLiquidation.getSoldeCptAvant());
        operationMoyPay.setCodDemOmp("T"); ///*** type demandeur (Titulaire,CoTitul,Mandataire)
        operationMoyPay.setMontApreOmp(Long.valueOf(new Long(new Double((new Double(paramLiquidation.getSoldeCptAvant()).doubleValue())-(new Double(avance.getMontArlArl())) ).longValue())));
        operationMoyPay.setLibMotfOmp("Remboursement Av suite Liquidation a écheance");
              
        }catch (Exception e) {
            com.oxia.fwk.core.Error erreur = new com.oxia.fwk.core.Error();
            StringBuffer text = new StringBuffer("Erreur dans Liquidation-affecterDonneesOperationMoyenPaiement : ");
            text.append(e.getMessage());
            erreur.setCode("100");
            erreur.setDescription(text.toString());
            erreur.setKey("Liquidation-affecterDonneesOperationMoyenPaiement");
            logger.error("Exception : ",e);   
            paramLiquidation.addError(erreur);
            throw new RuntimeException(e);
                             
        } 
     
        return operationMoyPay;
    }       
    
    public AvancRembLiquid affecterDonneesAvancRembLiquid(AvancRembLiquid avancRembLiquid,ParamLiquidation paramLiquidation){
                                                          
        try {
            ///*** date de remboursement : 
            avancRembLiquid.setDatReelArl(paramLiquidation.getDateComptableAg());
            ///*** remboursement liée a quelle avance
            avancRembLiquid.setAvancRembLiquid(avancRembLiquid);
            avancRembLiquid.setCodEtatArl(Constants.ETAT_ARL_VALIDEE);
            avancRembLiquid.setCodToprArl(Constants.CODE_REMBOURSEMENT_AVANCE);
            avancRembLiquid.getContratPlacement().setMontActuCpla((avancRembLiquid.getContratPlacement().getMontActuCpla()) + (avancRembLiquid.getMontArlArl()));  ///*** MAJ du montant actuel du placement

             ///*** remboursement liée a quelle avance
             AvancRembLiquid avancRemb = new AvancRembLiquid();
             avancRemb.setNumSeqArl(avancRembLiquid.getNumSeqArl());
             avancRembLiquid.setAvancRembLiquid(avancRemb);

            ///*** calcul montant remboursement
            avancRembLiquid = calculDonneesAvancRembLiquid(avancRembLiquid,paramLiquidation);
            avancRembLiquid.setDatArlArl(paramLiquidation.getDateComptableAg());
        
        }catch (Exception e) {
            com.oxia.fwk.core.Error erreur = new com.oxia.fwk.core.Error();
            StringBuffer text = new StringBuffer("Erreur dans Liquidation-affecterDonneesAvancRembLiquid : ");
            text.append(e.getMessage());
            erreur.setCode("100");
            erreur.setDescription(text.toString());
            erreur.setKey("Liquidation-affecterDonneesAvancRembLiquid");
            logger.error("Exception : ",e);   
            paramLiquidation.addError(erreur);
            throw new RuntimeException(e);
                             
        } 

        return avancRembLiquid;
    }


    public AvancRembLiquid calculDonneesAvancRembLiquid(AvancRembLiquid avancRembLiquid,ParamLiquidation paramLiquidation){
    
    
    try{
        
        double dureeEcoulee   = Double.valueOf(DateHandler.getDaysBetween(avancRembLiquid.getDatArlArl(),paramLiquidation.getDateComptableAg()));
        double dureeInitiale  = Double.valueOf(DateHandler.getDaysBetween(avancRembLiquid.getDatArlArl(),avancRembLiquid.getDatPrevArl())+1);
        
        if (dureeEcoulee < 15) {///*** minimum 15 jours d'avance
            dureeEcoulee = 15;
        }
        double dureeRestante  = dureeInitiale-dureeEcoulee;
             
        if (dureeRestante>0){///*** Remboursement antcipé
            avancRembLiquid.setCodTypiArl("S");///*** interet servi
            avancRembLiquid.setMontInetArl(Math.rint(avancRembLiquid.getMontArlArl().doubleValue() * dureeRestante * avancRembLiquid.getNumTauiArl()/Constants.NBR_JOURS_BC_CAT.doubleValue()));
            avancRembLiquid.setDatValiArl(paramLiquidation.getDateValServInteret());

        }else {
            if(dureeRestante<0){//*** Remboursement en retard
                avancRembLiquid.setCodTypiArl("P");///*** interet percu
                avancRembLiquid.setMontInetArl(Math.rint(Double.valueOf("-1") * avancRembLiquid.getMontArlArl().doubleValue() * dureeRestante * avancRembLiquid.getNumTauiArl()/Constants.NBR_JOURS_BC_CAT.doubleValue()));
                avancRembLiquid.setDatValiArl(paramLiquidation.getDateValPercInteret());
            }else{
                avancRembLiquid.setCodTypiArl("");///*** aucun interet (Remboursement a temps)
                avancRembLiquid.setMontInetArl(Double.valueOf("0"));                
            }
        }
                                         
    } catch (Exception e) {
                com.oxia.fwk.core.Error erreur = new com.oxia.fwk.core.Error();
                StringBuffer text = 
                    new StringBuffer("veuillez transmettre ce message à l'équipe informatique: ");
                text.append(". Exception calcDonneesAvancRembLiquid : "); text.append(e.getMessage());
                erreur.setCode("200");
                erreur.setDescription(text.toString());
            }
        return avancRembLiquid;   

    }

    public TraitementConditionBanque getCB(ContratPlacement contratPlacement,Long codOper,ParamLiquidation paramLiquidation) {
    

        TraitementConditionBanque traitementConditionBanque = new TraitementConditionBanque();

        try {
            traitementConditionBanque.setCodOperOper(codOper.toString());
            if (contratPlacement!=null){
                traitementConditionBanque.setCodPrdPrd(contratPlacement.getProduitPlacement().getCodPrdPlc().toString());
                traitementConditionBanque.setCodTpceTpce(contratPlacement.getPersonne().getTypePiece().getCodTpceTpce().toString());
                traitementConditionBanque.setNumPcePers(contratPlacement.getPersonne().getNumPcePers());
                traitementConditionBanque.setIdContrat(contratPlacement.getNumSeqCpla().toString());
                traitementConditionBanque.setMontant(contratPlacement.getMontCapCpla().toString());
                traitementConditionBanque.setNbUnites(contratPlacement.getNumNbrjCpla().toString());

            }
            if (paramLiquidation.getContratCpt()!=null){
                traitementConditionBanque.setNumCcptCcpt(paramLiquidation.getContratCpt().getContratCptId().getNumCcptCcpt().toString());
                traitementConditionBanque.setCodStrcStrc(paramLiquidation.getContratCpt().getContratCptId().getCodStrcStrc().toString());
                traitementConditionBanque.setCodPrdCpt(paramLiquidation.getContratCpt().getContratCptId().getCodPrdPrd().toString());
            }
            if(codOper.intValue()== Constants.OPER_INT_POST_SOUSC_PLAC.intValue()){///613
                traitementConditionBanque.setDateReference(DateHandler.dateToStr(paramLiquidation.getDateOperationLiq()));  
            }else{ 
                if(codOper.intValue()== Constants.COD_OPER_LIQUID_AECH_PLAC.intValue()){///311
                    traitementConditionBanque.setDateReference(DateHandler.dateToStr(paramLiquidation.getDateEcheanceContrat()));
                }else{
                    traitementConditionBanque.setDateReference(DateHandler.dateToStr(paramLiquidation.getDateComptableAg()));
                }
            }
            traitementConditionBanque.getCB();
            
           
        } catch (Exception e) {
                throw new RuntimeException(e);
            }
        return traitementConditionBanque;   
    
    } 
    
    
    public void genCroText(ValueObject vo) {
          
         
        } 
    public String getNumeroTache(ValueObject vo) {
        return (Constants.CODE_RESSOURCE_GENERALE);    
    }    
    

}
