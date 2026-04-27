package com.bna.smile.model.domainecontratcompte.moyensPaiement.traitement;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.commons.collections.map.ListOrderedMap;

import com.bna.commun.model.Operation;
import com.bna.commun.model.OppositionMoyenPaiement;
import com.bna.commun.model.OppositionMoyenPaiementId;
import com.bna.commun.model.Personnel;
import com.bna.commun.model.StructureDomaine;
import com.bna.commun.model.Tache;
import com.bna.commun.model.TacheId;
import com.bna.commun.traitements.Traitement;
import com.bna.commun.util.ContextHandler;
import com.bna.commun.util.DateHandler;
import com.bna.commun.util.StrHandler;
import com.bna.smile.model.constant.Constants;
import com.bna.smile.model.domainecommun.service.CRUDservice;
import com.bna.smile.model.domainecontratcompte.moyensPaiement.dao.OppositionMoyPaiementDAO;
import com.bna.smile.model.domainecontratcompte.moyensPaiement.model.ParamOpposition;
import com.oxia.fwk.context.Context;
import com.oxia.fwk.core.IValueObject;
import com.oxia.fwk.core.ValueObject;

/**
 * Opposition cheque banque.
 * @author Ramzi
 * @param ParamOpposition
 * @return ParamOpposition
 * @since 28/09/2007
 * 
 */
public class OppositionChequesBanqueTrt  extends Traitement{
    public OppositionChequesBanqueTrt() {
    }
    //formation du nouveau numéro de carte

     public IValueObject perform(IValueObject vo) throws Exception{

        ParamOpposition paramOpposition = (ParamOpposition)vo;

        try {
            Context context = ContextHandler.getContext();
            CRUDservice crudService = 
                (CRUDservice)context.getBean("crudservice");
            
            int numPrem = Long.valueOf(paramOpposition.getNumPremierChq()).intValue();
            int numDern;
            if(paramOpposition.getNumDernierChq().equals("")){
                numDern = numPrem;
            }else{
                numDern = Long.valueOf(paramOpposition.getNumDernierChq()).intValue();
            }
            
            OppositionMoyPaiementDAO oppositionMoyPaiementDAO = 
                (OppositionMoyPaiementDAO)context.getBean("oppositionMoyPaiementDAO");
            List listDernierEtatMoyenPaiement = null;
            ListOrderedMap dernierEtatMoyenPaiement = null;
            String codEtat = null;
            Date dateOperation = null;
            Date dateCirculation = null;
            Date dateTelecompensation = null;
            
            String numChqNEnCirculation = "";
            for(int i=numPrem;i>= numPrem && i<=numDern;i++){           
                //verifier si moyen paiement est en circulation : si pas de foecage
                if(!paramOpposition.getForcageEnCirculation().equals("1")){
                     dateCirculation=oppositionMoyPaiementDAO.getDateChequeEnCirculation(String.valueOf(i),paramOpposition.getContratCpt().getContratCptId().getCodStrcStrc().toString()
                     ,paramOpposition.getContratCpt().getContratCptId().getCodPrdPrd().toString(),paramOpposition.getContratCpt().getContratCptId().getNumCcptCcpt().toString());
                    if(dateCirculation == null){
                        if (i==numPrem)
                            numChqNEnCirculation=i+"";
                        else
                            numChqNEnCirculation = numChqNEnCirculation+","+i;
                    }
                }
                 
                //verifier si moyen paiement est déja en opposition
                 listDernierEtatMoyenPaiement=oppositionMoyPaiementDAO.getDernierEtatMoyPaiement(Constants.COD_MOYP_TMOY_Cheque.toString(),String.valueOf(i),paramOpposition.getContratCpt().getContratCptId().getCodStrcStrc().toString(),paramOpposition.getContratCpt().getContratCptId().getCodPrdPrd().toString(),paramOpposition.getContratCpt().getContratCptId().getNumCcptCcpt().toString());
                 if(listDernierEtatMoyenPaiement.size()>0){
                     dernierEtatMoyenPaiement = (ListOrderedMap) listDernierEtatMoyenPaiement.get(0);
                     codEtat = (String)(dernierEtatMoyenPaiement.getValue(0));
                     dateOperation = (Date)(dernierEtatMoyenPaiement.getValue(1));
                    if(codEtat.equals(Constants.COD_ETAT_OPMP_Opposition)){
                        com.oxia.fwk.core.Error erreurEnOpposition = 
                            new com.oxia.fwk.core.Error();
                        erreurEnOpposition.setCode("MoyPayEnOpposition");
                        erreurEnOpposition.setDescription("Le chèque numéro "+i+" est déja mis en opposition le "+DateHandler.dateToStr(dateOperation));
                        paramOpposition.addError(erreurEnOpposition);
                        return paramOpposition;                   
                    }
                 }
                    
                //verifier si moyen paiement est déja telecompensé
                dateTelecompensation = oppositionMoyPaiementDAO.getDateTelecompensation(Constants.COD_MOYP_TMOY_Cheque.toString(),String.valueOf(i));
                if(dateTelecompensation != null){
                    com.oxia.fwk.core.Error erreurChqTelecompense = 
                        new com.oxia.fwk.core.Error();
                    erreurChqTelecompense.setCode("MoyPayTelecompense");
                    erreurChqTelecompense.setDescription("Le chèque numéro "+i+" est déja telecompensé le "+DateHandler.dateToStr(dateTelecompensation));;
                    paramOpposition.addError(erreurChqTelecompense);
                    return paramOpposition;      
                }
            }
            //test si liste des cheques en circulation 
            if(!numChqNEnCirculation.equals("")){
                 com.oxia.fwk.core.Error erreurEnOpposition = 
                     new com.oxia.fwk.core.Error();
                 erreurEnOpposition.setCode("MoyPayEnCirculation");
                 erreurEnOpposition.setDescription("Le(s) chèque(s) numéro "+numChqNEnCirculation+" n'est(ne sont) pas en circulation" );
                 paramOpposition.addError(erreurEnOpposition);
                 return paramOpposition;
            }
            Operation operation = new Operation();
            operation.setCodOperOper(Long.valueOf(paramOpposition.getCodeOperation()));
            Personnel personnel = new Personnel();
            personnel.setNumMatrUser(paramOpposition.getMatriculeUser());
            //remplir l'objet OppositionMoyenPaiement et insertion 
            for(int i=numPrem;i>= numPrem && i<=numDern;i++){   
                OppositionMoyenPaiement oppositionMoyenPaiement = new OppositionMoyenPaiement();
                
                Tache tache = new Tache();
                TacheId tacheId = new TacheId();
                tacheId.setCodOperOper(Constants.COD_OPER_OPER_OPPOSITION_CHQ_BANQUE);
                tacheId.setCodTachTach(Constants.COD_TACH_TACH_OPPOSITION_CHQ_BANQUE);
                tache.setTacheId(tacheId); 
                oppositionMoyenPaiement.setTache(tache);
                oppositionMoyenPaiement.setPersonnel(personnel);
            
                OppositionMoyenPaiementId oppositionMoyenPaiementId = new OppositionMoyenPaiementId();
                oppositionMoyenPaiementId.setCodMoypTmoy(Constants.COD_MOYP_TMOY_Cheque);
                oppositionMoyenPaiementId.setNumMoypOpmp(String.valueOf(i));
                oppositionMoyenPaiementId.setDatOperOpmp(DateHandler.timeJour());
                oppositionMoyenPaiement.setOppositionMoyenPaiementId(oppositionMoyenPaiementId);
                
                oppositionMoyenPaiement.setCodEtatOpmp(Constants.COD_ETAT_OPMP_Opposition);
                oppositionMoyenPaiement.setCodActrOpmp(paramOpposition.getTypeActeur());
                
                oppositionMoyenPaiement.setContratCpt(paramOpposition.getContratCpt());
                
                oppositionMoyenPaiement.setCodMotfOpmp(paramOpposition.getMotifOpposition());
                if(paramOpposition.getNumJugement() != null){
                    oppositionMoyenPaiement.setNumJugfOpmp(paramOpposition.getNumJugement());
                    oppositionMoyenPaiement.setDatJugfOpmp( DateHandler.strToDate(paramOpposition.getDatJugement()));
                }
                                
                //insertion dans la table opposition_moyen_paiement
                crudService.create(oppositionMoyenPaiement);
                
                //syncronisation pascal
                this.sychronisationPascal(oppositionMoyenPaiement);
            }
            
        } catch (Exception e) {
            com.oxia.fwk.core.Error erreur = new com.oxia.fwk.core.Error();
            erreur.setCode("Technique");
            erreur.setDescription("OppositionChequesTrt " + e.getMessage());
            paramOpposition.addError(erreur);
            logger.error("Exception : ",e);
            throw new RuntimeException(e);  
        }
        return paramOpposition;
    }
    public void genCroText(ValueObject vo) {
           
    } 
    
    public String getNumeroTache(IValueObject vo){
        return Constants.COD_OPER_OPER_OPPOSITION_CHQ_BANQUE.toString()+
            StrHandler.lpad(Constants.COD_TACH_TACH_OPPOSITION_CHQ_BANQUE.toString(),'0',2);
        //return "120";
        
    }
    
    
         public void genererSynchronisationPascal(ValueObject vo) {
         
             OppositionMoyenPaiement oppositionMoyenPaiement = (OppositionMoyenPaiement)vo;   
             
             try{
             DateFormat myformat1 = new SimpleDateFormat("ddMMyy");
             DateFormat myformat2 = new SimpleDateFormat("ddMMyyyy");
                  
             //partie fixe
             this.setCodeOperationSynch(Constants.COD_OPER_OPER_OPPOSITION_CHQ_CLIENT.longValue());
             this.setCodeTacheSynch(oppositionMoyenPaiement.getTache().getTacheId().getCodTachTach());
             this.setDateOperationSynch(new Date());
             this.setCodeStructureSynch(oppositionMoyenPaiement.getContratCpt().getContratCptId().getCodStrcStrc());
             
             //partie variable
             String numCompte = StrHandler.lpad(oppositionMoyenPaiement.getContratCpt().getContratCptId().getCodPrdPrd().toString(),'0',4) +
                                StrHandler.lpad(oppositionMoyenPaiement.getContratCpt().getContratCptId().getNumCcptCcpt().toString(),'0',6); 
             
             String numCheque = " 0000000";
             if(oppositionMoyenPaiement.getOppositionMoyenPaiementId().getNumMoypOpmp() != null )            
                 numCheque =  " "+StrHandler.lpad(oppositionMoyenPaiement.getOppositionMoyenPaiementId().getNumMoypOpmp(),'0',7);

             String dateOpposition = "      ";
             if(oppositionMoyenPaiement.getOppositionMoyenPaiementId().getDatOperOpmp() != null )            
                 dateOpposition =  myformat1.format(oppositionMoyenPaiement.getOppositionMoyenPaiementId().getDatOperOpmp());

             String matricule = "0000";
             if(oppositionMoyenPaiement.getPersonnel().getNumMatrUser() != null )            
                 matricule =  StrHandler.lpad(oppositionMoyenPaiement.getPersonnel().getNumMatrUser(),'0',4);
             
             
             
             String partieVariable =  numCompte + numCheque + dateOpposition + matricule;
             
             this.setTextSynch(partieVariable);
             } catch (Exception e) {
                 logger.error("Exception: ",e);
                 throw new RuntimeException(e);  
             }
             
         }
  
    public IValueObject getNumeroDomaine(IValueObject vo){
        ParamOpposition paramOpposition = (ParamOpposition)vo;
        StructureDomaine  structureDomaine  = new StructureDomaine();
        structureDomaine.setCodStrcStrc(paramOpposition.getContratCpt().getContratCptId().getCodStrcStrc());
        structureDomaine.setCodDomDomm(Constants.COD_DOM_CONTRATCOMPTE);
        return structureDomaine;
    
    }

}
