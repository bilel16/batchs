package com.bna.smile.model.domainecontratcompte.moyensPaiement.traitement;

import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.collections.map.ListOrderedMap;

import com.bna.commun.model.BonDeCaisse;
import com.bna.commun.model.CoTitulaire;
import com.bna.commun.model.ContratPlacement;
import com.bna.commun.model.DetailsBc;
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
import com.oxia.fwk.core.ICriteria;
import com.oxia.fwk.core.IExpression;
import com.oxia.fwk.core.ISearchEngine;
import com.oxia.fwk.core.IValueObject;
import com.oxia.fwk.core.ValueObject;
import com.oxia.fwk.searchengine.SearchEngine;

/**
 * Opposition BC Plac.
 * @author Ramzi
 * @param ParamOpposition
 * @return ParamOpposition
 * @since 09/12/2009
 * 
 */
public class OppositionBcPlacTrt  extends Traitement{
    public OppositionBcPlacTrt() {
    }
    

    public IValueObject perform(IValueObject vo) throws Exception{

        ParamOpposition paramOpposition = (ParamOpposition)vo;

        try {
                Context context = ContextHandler.getContext();
                CRUDservice crudService = 
                    (CRUDservice)context.getBean("crudservice");
            
                String numBcPlac = paramOpposition.getNumBcPlac(); 
                
                OppositionMoyPaiementDAO oppositionMoyPaiementDAO = 
                    (OppositionMoyPaiementDAO)context.getBean("oppositionMoyPaiementDAO");
                List listDernierEtatMoyenPaiement = null;
                ListOrderedMap dernierEtatMoyenPaiement = null;
                String codEtat = null;
                Date dateOperation = null;
                Date dateCirculation = null;
                
                //verifier si moyen paiement est déja en opposition
                 listDernierEtatMoyenPaiement=oppositionMoyPaiementDAO.getDernierEtatMoyPaiement(Constants.COD_MOYP_TMOY_BC_Plac.toString(),numBcPlac,paramOpposition.getContratCpt().getContratCptId().getCodStrcStrc().toString(),paramOpposition.getContratCpt().getContratCptId().getCodPrdPrd().toString(),paramOpposition.getContratCpt().getContratCptId().getNumCcptCcpt().toString());
                 if(listDernierEtatMoyenPaiement.size()>0){
                     dernierEtatMoyenPaiement = (ListOrderedMap) listDernierEtatMoyenPaiement.get(0);
                     codEtat = (String)(dernierEtatMoyenPaiement.getValue(0));
                     dateOperation = (Date)(dernierEtatMoyenPaiement.getValue(1));
                    if(codEtat.equals(Constants.COD_ETAT_OPMP_Opposition)){
                        com.oxia.fwk.core.Error erreurEnOpposition = 
                            new com.oxia.fwk.core.Error();
                        erreurEnOpposition.setCode("MoyPayEnOpposition");
                        erreurEnOpposition.setDescription("BC est déja mis en opposition le "+DateHandler.dateToStr(dateOperation));
                        paramOpposition.addError(erreurEnOpposition);
                        return paramOpposition;                   
                    }
                 }
                  
                //verifier si moyen paiement est en circulation
                dateCirculation=oppositionMoyPaiementDAO.getDateBcEnCirculation(numBcPlac,paramOpposition.getContratCpt().getContratCptId().getCodStrcStrc().toString(),paramOpposition.getContratCpt().getContratCptId().getCodPrdPrd().toString(),paramOpposition.getContratCpt().getContratCptId().getNumCcptCcpt().toString());
                if(dateCirculation == null){
                    com.oxia.fwk.core.Error erreurEnOpposition = 
                        new com.oxia.fwk.core.Error();
                    erreurEnOpposition.setCode("MoyPayEnCirculationBc");
                    erreurEnOpposition.setDescription("BC n'est pas en circulation pour ce compte" );
                    paramOpposition.addError(erreurEnOpposition);
                    return paramOpposition;      
                }
                
               //verifier si moyen paiement est déja présenté par le client
                Date datePresentation=oppositionMoyPaiementDAO.getDateBcPresentation(numBcPlac);
                if(datePresentation != null ){
                    com.oxia.fwk.core.Error erreurEnOpposition = 
                        new com.oxia.fwk.core.Error();
                    erreurEnOpposition.setCode("MoyPayPresentationBc");
                    erreurEnOpposition.setDescription("BC est déja présenté par le client le "+DateHandler.dateToStr(datePresentation));
                    paramOpposition.addError(erreurEnOpposition);
                    return paramOpposition;      
                }
               
                

                Tache tache = new Tache();
                TacheId tacheId = new TacheId();
                tacheId.setCodOperOper(Constants.COD_OPER_OPER_OPPOSITION_BC_PLAC);
                tacheId.setCodTachTach(Constants.COD_TACH_TACH_OPPOSITION_BC_PLAC);
                tache.setTacheId(tacheId); 
                Personnel personnel = new Personnel();
                personnel.setNumMatrUser(paramOpposition.getMatriculeUser());
                
                //remplir l'objet OppositionMoyenPaiement et insertion 
                OppositionMoyenPaiement oppositionMoyenPaiement = new OppositionMoyenPaiement();
                
                oppositionMoyenPaiement.setTache(tache);
                oppositionMoyenPaiement.setPersonnel(personnel);
            
                OppositionMoyenPaiementId oppositionMoyenPaiementId = new OppositionMoyenPaiementId();
                oppositionMoyenPaiementId.setCodMoypTmoy(Constants.COD_MOYP_TMOY_BC_Plac);
                oppositionMoyenPaiementId.setNumMoypOpmp(numBcPlac);
                oppositionMoyenPaiementId.setDatOperOpmp(DateHandler.timeJour());
                oppositionMoyenPaiement.setOppositionMoyenPaiementId(oppositionMoyenPaiementId);
                
                oppositionMoyenPaiement.setCodEtatOpmp(Constants.COD_ETAT_OPMP_Opposition);
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
                
                oppositionMoyenPaiement.setCodMotfOpmp(paramOpposition.getMotifOpposition());
                
                
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
                    
            //modification du num BC de contrat placemnet
            ////extraire le contrat placement 
             ISearchEngine searchEngine = 
                 (SearchEngine)context.getBean("searchEngine");
             ICriteria   criteria       = searchEngine.createCriteria();
             IExpression expression     = searchEngine.createExpression();
             criteria.add(expression.eq("numBcCpla", 
                                       Long.valueOf(paramOpposition.getNumBcPlac())));
             List listContPlac = searchEngine.find(ContratPlacement.class,criteria);
             ContratPlacement contratPlacement = (ContratPlacement)listContPlac.get(0);
             contratPlacement.setNumBcaCpla(contratPlacement.getNumBcCpla());
             contratPlacement.setNumBcCpla(Long.valueOf(paramOpposition.getNumBcnPlac()));
             crudService.update(contratPlacement);
            ////prise du numéro BC duplicata de la souche
             DetailsBc detailsBc =new DetailsBc();
             detailsBc.setContratPlacement(contratPlacement);
             BonDeCaisse bonCaiss =new BonDeCaisse();
             bonCaiss.setNumSeqBc(Long.valueOf(paramOpposition.getNumSeqBc()));
             detailsBc.setBonDeCaisse(bonCaiss);
             detailsBc.setNumBcDbc(contratPlacement.getNumBcCpla());
             crudService.create(detailsBc);
            
        } catch (Exception e) {
            com.oxia.fwk.core.Error erreur = new com.oxia.fwk.core.Error();
            erreur.setCode("Technique");
            erreur.setDescription("OppositionBCTrt " + e.getMessage());
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
        return Constants.COD_OPER_OPER_OPPOSITION_BC_PLAC.toString()+
            StrHandler.lpad(Constants.COD_TACH_TACH_OPPOSITION_BC_PLAC.toString(),'0',2);
        
    }

    public IValueObject getNumeroDomaine(IValueObject vo){
        ParamOpposition paramOpposition = (ParamOpposition)vo;
        StructureDomaine  structureDomaine  = new StructureDomaine();
        structureDomaine.setCodStrcStrc(paramOpposition.getContratCpt().getContratCptId().getCodStrcStrc());
        structureDomaine.setCodDomDomm(Constants.COD_DOM_PLACEMENT);
        return structureDomaine;
    
    }


}
