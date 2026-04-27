package com.bna.smile.model.domainecontratcompte.moyensPaiement.traitement;

import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.collections.map.ListOrderedMap;

import com.bna.commun.model.CoTitulaire;
import com.bna.commun.model.MandatPersonne;
import com.bna.commun.model.OppMoypMandPers;
import com.bna.commun.model.OppMoypMandPersId;
import com.bna.commun.model.OppositionMoyenPaiement;
import com.bna.commun.model.OppositionMoyenPaiementId;
import com.bna.commun.model.Personne;
import com.bna.commun.model.Personnel;
import com.bna.commun.model.StructureDomaine;
import com.bna.commun.model.Tache;
import com.bna.commun.model.TacheId;
import com.bna.commun.model.TypePiece;
import com.bna.commun.traitements.Traitement;
import com.bna.commun.util.ContextHandler;
import com.bna.commun.util.DateHandler;
import com.bna.commun.util.StrHandler;
import com.bna.smile.model.constant.Constants;
import com.bna.smile.model.domainecommun.model.PersonneStrc;
import com.bna.smile.model.domainecommun.service.CRUDservice;
import com.bna.smile.model.domainecommun.traitement.GetPersonneTrt;
import com.bna.smile.model.domainecontratcompte.moyensPaiement.dao.OppositionMoyPaiementDAO;
import com.bna.smile.model.domainecontratcompte.moyensPaiement.model.ParamOpposition;
import com.oxia.fwk.context.Context;
import com.oxia.fwk.core.IValueObject;
import com.oxia.fwk.core.ValueObject;

/**
 * Levee Opposition livret.
 * @author Ramzi
 * @param ParamOpposition
 * @return ParamOpposition
 * @since 28/09/2007
 * 
 */
public class LeveeOppositionLivretTrt extends Traitement{
    public LeveeOppositionLivretTrt() {
    }
    //formation du nouveau numéro de carte

     public IValueObject perform(IValueObject vo) throws Exception{

        ParamOpposition paramOpposition = (ParamOpposition)vo;

        try {
            Context context = ContextHandler.getContext();
            CRUDservice crudService = 
                (CRUDservice)context.getBean("crudservice");
            
            String typeLivret = paramOpposition.getTypeLivret();
            String numLivret = paramOpposition.getNumLivret(); 
            
            OppositionMoyPaiementDAO oppositionMoyPaiementDAO = 
                (OppositionMoyPaiementDAO)context.getBean("oppositionMoyPaiementDAO");
            List listDernierEtatMoyenPaiement = null;
            ListOrderedMap dernierEtatMoyenPaiement = null;
            String codEtat = null;
            Date dateOperation = null;
         
            //verifier si moyen paiement est en circulation
            Date dateCirculation=oppositionMoyPaiementDAO.getDateLivretEnCirculation(typeLivret, numLivret,paramOpposition.getContratCpt().getContratCptId().getCodStrcStrc().toString(),paramOpposition.getContratCpt().getContratCptId().getCodPrdPrd().toString(),paramOpposition.getContratCpt().getContratCptId().getNumCcptCcpt().toString());
            if(dateCirculation == null){
                com.oxia.fwk.core.Error erreurEnOpposition = 
                    new com.oxia.fwk.core.Error();
                erreurEnOpposition.setCode("MoyPayEnCirculationLivret");
                erreurEnOpposition.setDescription("Ce livret n'est pas en circulation pour ce compte" );
                paramOpposition.addError(erreurEnOpposition);
                return paramOpposition;      
            }
            
            //verifier si moyen paiement est déja en opposition
             listDernierEtatMoyenPaiement=oppositionMoyPaiementDAO.getDernierEtatMoyPaiement(Constants.COD_MOYP_TMOY_Livret.toString(),numLivret,paramOpposition.getContratCpt().getContratCptId().getCodStrcStrc().toString(),paramOpposition.getContratCpt().getContratCptId().getCodPrdPrd().toString(),paramOpposition.getContratCpt().getContratCptId().getNumCcptCcpt().toString());
             if(listDernierEtatMoyenPaiement.size()>0){
                 dernierEtatMoyenPaiement = (ListOrderedMap) listDernierEtatMoyenPaiement.get(0);
                 codEtat = (String)(dernierEtatMoyenPaiement.getValue(0));
                 dateOperation = (Date)(dernierEtatMoyenPaiement.getValue(1));
                if(!codEtat.equals(Constants.COD_ETAT_OPMP_Opposition)){
                    com.oxia.fwk.core.Error erreurEnOpposition = 
                        new com.oxia.fwk.core.Error();
                    erreurEnOpposition.setCode("MoyPayEnOpposition");
                    erreurEnOpposition.setDescription("Ce livret n'est pas mis en opposition");
                    paramOpposition.addError(erreurEnOpposition);
                    return paramOpposition;                   
                }
             }else{
                     com.oxia.fwk.core.Error erreurEnOpposition = 
                         new com.oxia.fwk.core.Error();
                     erreurEnOpposition.setCode("MoyPayNotEnOpposition");
                     erreurEnOpposition.setDescription("Ce livret n'est pas mis en opposition");
                     paramOpposition.addError(erreurEnOpposition);
                     return paramOpposition;  
                 
             }
            
            Tache tache = new Tache();
            TacheId tacheId = new TacheId();
            tacheId.setCodOperOper(Constants.COD_OPER_OPER_LEVEE_LIVRET);
            tacheId.setCodTachTach(Constants.COD_TACH_TACH_LEVEE_LIVRET);
            tache.setTacheId(tacheId);
            Personnel personnel = new Personnel();
            personnel.setNumMatrUser(paramOpposition.getMatriculeUser());
            
            //remplir l'objet OppositionMoyenPaiement et insertion 
            OppositionMoyenPaiement oppositionMoyenPaiement = new OppositionMoyenPaiement();
            
            oppositionMoyenPaiement.setTache(tache);
            oppositionMoyenPaiement.setPersonnel(personnel);
            
            OppositionMoyenPaiementId oppositionMoyenPaiementId = new OppositionMoyenPaiementId();
            oppositionMoyenPaiementId.setCodMoypTmoy(Constants.COD_MOYP_TMOY_Livret);
            oppositionMoyenPaiementId.setNumMoypOpmp(numLivret);
            oppositionMoyenPaiementId.setDatOperOpmp(DateHandler.timeJour());
            oppositionMoyenPaiement.setOppositionMoyenPaiementId(oppositionMoyenPaiementId);
            
            oppositionMoyenPaiement.setCodEtatOpmp(Constants.COD_ETAT_OPMP_Levet);
            oppositionMoyenPaiement.setCodActrOpmp(paramOpposition.getTypeActeur());
            
            oppositionMoyenPaiement.setContratCpt(paramOpposition.getContratCpt());
            
            TypePiece typePiece = new TypePiece();
            typePiece.setCodTpceTpce(Long.valueOf(paramOpposition.getTypePieceActeur()));
            oppositionMoyenPaiement.setTypePiece(typePiece);
            
            oppositionMoyenPaiement.setNumPceOpmp(paramOpposition.getNumPieceActeur());
            
            if(paramOpposition.getNumActJudiciaire() != null){
                oppositionMoyenPaiement.setNumActjOpmp(paramOpposition.getNumActJudiciaire());
                oppositionMoyenPaiement.setDatActjOpmp(paramOpposition.getDatActJudiciaire());
            }
            
            if(paramOpposition.getTypeActeur().equals("C")){
                    // cas cotitulaire
                    if(paramOpposition.getListCotitulaire()!=null && paramOpposition.getListCotitulaire().size()>0 ){
                         CoTitulaire cotitulaire = (CoTitulaire)paramOpposition.getListCotitulaire().get(0);
                         oppositionMoyenPaiement.setCoTitulaire(cotitulaire);
                    }         
            }   
            
            //insertion dans la table opposition_moyen_paiement
            crudService.create(oppositionMoyenPaiement);
                                       
            //insertion la liste des mandataires si cas mandataire
            if(paramOpposition.getTypeActeur().equals("M")){
                if(paramOpposition.getMandat().getCodSignMand().equals("S")){
                  // signature séparée
                  /// insertion juste du demandeur                            
                    PersonneStrc personneStrc = new PersonneStrc();
                    personneStrc.setCodTpceTpce(Long.valueOf(paramOpposition.getTypePieceActeur()));
                    personneStrc.setNumPcePers(paramOpposition.getNumPieceActeur());
                    GetPersonneTrt getPersonneTrt = new GetPersonneTrt();
                    Personne personne = (Personne)getPersonneTrt.exec(personneStrc);
                    
                    createOppMoypMandPers(oppositionMoyenPaiementId,paramOpposition.getMandat().getNumMandMand(), personne.getNumSeqPers(),crudService);
                                                               
                }else{
                     // signature conjointe(insertion de tous les signataires)           
                     for (Iterator it = paramOpposition.getListMandatPersonne().iterator();it.hasNext(); ) {          
                        MandatPersonne mandatPersonne = (MandatPersonne)it.next(); 
                        Long numSeqPers = mandatPersonne.getMandatPersonneId().getNumSeqPers();
                        createOppMoypMandPers(oppositionMoyenPaiementId,paramOpposition.getMandat().getNumMandMand(), numSeqPers, crudService);
                     }               
                }
            }
            
        } catch (Exception e) {
            com.oxia.fwk.core.Error erreur = new com.oxia.fwk.core.Error();
            erreur.setCode("Technique");
            erreur.setDescription("LeveeOppositionLivretTrt " + e.getMessage());
            paramOpposition.addError(erreur);
            logger.error("Exception : ",e);
            throw new RuntimeException(e);
        }
        return paramOpposition;
    }

    private void createOppMoypMandPers(OppositionMoyenPaiementId oppositionMoyenPaiementId, Long numMandat, Long numSeqPers,
                                       CRUDservice crudService) {
        try{
        OppMoypMandPers oppMoypMandPers = new OppMoypMandPers();
        OppMoypMandPersId oppMoypMandPersId = new OppMoypMandPersId();
        oppMoypMandPersId.setNumMandMand(numMandat);               
        oppMoypMandPersId.setNumSeqPers(numSeqPers);
        oppMoypMandPersId.setNumMoypOpmp(oppositionMoyenPaiementId.getNumMoypOpmp());
        oppMoypMandPersId.setDatOperOpmp(oppositionMoyenPaiementId.getDatOperOpmp());
        oppMoypMandPersId.setCodMoypTmoy(oppositionMoyenPaiementId.getCodMoypTmoy());
        oppMoypMandPers.setOppMoypMandPersId(oppMoypMandPersId);
       
        
        crudService.create(oppMoypMandPers);
        } catch (Exception e) {
            logger.error("Exception: ",e);
            throw new RuntimeException(e);  
        }
    }
   
    public void genCroText(ValueObject vo) {
           
    } 
    
    public String getNumeroTache(IValueObject vo){
        return Constants.COD_OPER_OPER_LEVEE_LIVRET.toString()+
            StrHandler.lpad(Constants.COD_TACH_TACH_LEVEE_LIVRET.toString(),'0',2);    
    }
    public IValueObject getNumeroDomaine(IValueObject vo){
        ParamOpposition paramOpposition = (ParamOpposition)vo;
        StructureDomaine  structureDomaine  = new StructureDomaine();
        structureDomaine.setCodStrcStrc(paramOpposition.getContratCpt().getContratCptId().getCodStrcStrc());
        structureDomaine.setCodDomDomm(Constants.COD_DOM_CONTRATCOMPTE);
        return structureDomaine;
    
    }
}
