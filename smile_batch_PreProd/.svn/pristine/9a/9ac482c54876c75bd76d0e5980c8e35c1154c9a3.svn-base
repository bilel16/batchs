package com.bna.smile.model.domainecontratcompte.procuration.traitement;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;

import com.bna.commun.model.Mandat;
import com.bna.commun.model.MandatPersonne;
import com.bna.commun.model.Operation;
import com.bna.commun.model.Personne;
import com.bna.commun.model.Personnel;
import com.bna.commun.model.StructureDomaine;
import com.bna.commun.model.Tache;
import com.bna.commun.model.TacheId;
import com.bna.commun.model.TraceMandat;
import com.bna.commun.traitements.Traitement;
import com.bna.commun.util.ContextHandler;
import com.bna.commun.util.StrHandler;
import com.bna.smile.model.constant.Constants;
import com.bna.smile.model.domainecommun.traitement.GetPersonneByNumSeqPersTrt;
import com.bna.smile.model.domainecontratcompte.procuration.commande.GetMandatCmd;
import com.bna.smile.model.domainecontratcompte.procuration.commande.MiseAJourMandatCmd;
import com.bna.smile.model.domainecontratcompte.procuration.model.ParamInsertMandat;
import com.bna.smile.model.domainecontratcompte.procuration.model.ParamModifMandVo;
import com.bna.smile.model.domainecontratcompte.procuration.model.TraceMandataireSyncVO;
import com.oxia.fwk.context.Context;
import com.oxia.fwk.core.IValueObject;
import com.oxia.fwk.core.ValueObject;

public class ValidModifMandTrt extends Traitement{
    //private static final Logger logger = Logger.getLogger(ValidModifMandTrt.class);

    public ValidModifMandTrt() {
    }
    
    public IValueObject perform(IValueObject vo) {
    
        Context context = ContextHandler.getContext();
 
        ParamModifMandVo  paramModifMandVo  = (ParamModifMandVo)vo;
        ParamInsertMandat paramInsertMandat = (ParamInsertMandat)paramModifMandVo.getParamInsertMandat();
        Mandat mandat = (Mandat)paramModifMandVo.getMandat();
    try{
            TraceMandat traceMandat = new TraceMandat();
            Tache tache = new Tache();
            TacheId tacheId = new TacheId();
           
           
            /* Historisation */
            Mandat mandatOld =new Mandat();
            mandatOld.setNumMandMand(((Mandat)paramModifMandVo.getMandat()).getNumPereMand());
            GetMandatCmd getMandatCmd = new GetMandatCmd();
            mandatOld=(Mandat)getMandatCmd.execute(mandatOld);
            mandatOld.setCodEtatMand("H"); 
            if (paramModifMandVo.getTypevalidation().equalsIgnoreCase("R")){
            mandatOld.setCodEdemMand(Constants.COD_ETAT_MAND_REJ_MOD);   
            }else{
            mandatOld.setCodEdemMand(null);
            }
            mandatOld.setNumPereMand(mandat.getNumMandMand());
            mandatOld.setDatEnvmMand(mandat.getDatEnvmMand());
            mandatOld.setDatValmMand(new Date());

            
            ///* DEBUT : annulation des anciens mandataires (Synchronisation pascal)
            tacheId.setCodOperOper(Constants.COD_OPER_ANNUL_MANDAT);
            tacheId.setCodTachTach(Constants.COD_TACHE_VAL_ANNUL);
            tache.setTacheId(tacheId);
            traceMandat.setTache(tache);
            traceMandat.setMandat(mandatOld);
            Personnel personnel = new Personnel();
            personnel.setNumMatrUser(paramInsertMandat.getPersonnel().getNumMatrUser());
            traceMandat.setPersonnel(personnel);

                /* Garniture de la Table sychronisation_Pascal (en cas de validation de Modification) pour enlever les anciens mandataires suite a une modification*/            
                if ((traceMandat.getTache().getTacheId().getCodTachTach().toString()).equalsIgnoreCase(Constants.COD_TACHE_VALIDMODIF_MANDAT.toString()) ) {

                    for (Iterator it1 = mandatOld.getMandatPersonnes().iterator();it1.hasNext(); ) {
                        MandatPersonne mandatPersonne = (MandatPersonne)it1.next();
                        if (mandatPersonne.getCodEtatMp().equalsIgnoreCase("V")) {
                            TraceMandataireSyncVO traceMandataireSyncVO = new TraceMandataireSyncVO();
                            traceMandataireSyncVO.setTraceMandat(traceMandat);
                            traceMandataireSyncVO.setMandatPersonne(mandatPersonne);
                            this.sychronisationPascal(traceMandataireSyncVO); 
                        }
                    }
                }
            ///* FIN : annulation des anciens mandataires (Synchronisation pascal)

            //** update(mandatOld Historique *//
            Mandat mandatRetourOld = new Mandat();
            MiseAJourMandatCmd majMandatCmd = new MiseAJourMandatCmd();
            mandatRetourOld = (Mandat)majMandatCmd.execute(mandatOld);
            
            if (!paramModifMandVo.getTypevalidation().equalsIgnoreCase("R")){//validation avec reserve ou validation simple
            /* MAJ du nouveau mandat */
                mandat.setCodEtatMand("V");
                if (paramModifMandVo.getTypevalidation().equalsIgnoreCase("VR")){
                mandat.setCodEdemMand(Constants.COD_ETAT_MAND_VAL_RES);
                }
                mandat.setNumPereMand(null);

                MiseAJourMandatCmd miseAJourMandatCmd = new MiseAJourMandatCmd();
                Mandat mandatRetourNew = new Mandat();
                mandatRetourNew = (Mandat)miseAJourMandatCmd.execute(mandat);
                if(mandatRetourNew.getNumRdjMand()!=null){
                    paramModifMandVo.getMandat().setNumRdjMand(mandatRetourNew.getNumRdjMand());
                }
                
            }else{//rejet
            
                mandat.setCodEtatMand("H"); 
                mandat.setCodEdemMand(Constants.COD_ETAT_MAND_REJ_MOD);
                mandat.setDatValmMand(new Date()); 
                MiseAJourMandatCmd mAjourMandatCmd = new MiseAJourMandatCmd();
                Mandat mandatRetourNew = new Mandat();
                mandatRetourNew = (Mandat)mAjourMandatCmd.execute(mandat);
             
                ///* DEBUT : annulation des nouveaux mandataires (Synchronisation pascal)
                tacheId.setCodOperOper(Constants.COD_OPER_ANNUL_MANDAT);
                tacheId.setCodTachTach(Constants.COD_TACHE_VAL_ANNUL);
                tache.setTacheId(tacheId);
                traceMandat.setTache(tache);
                traceMandat.setMandat(mandat);
                personnel.setNumMatrUser(paramInsertMandat.getPersonnel().getNumMatrUser());
                traceMandat.setPersonnel(personnel);

                    /* Garniture de la Table sychronisation_Pascal (en cas de validation de Modification) pour enlever les anciens mandataires suite a une modification*/            
             

                        for (Iterator it1 = mandat.getMandatPersonnes().iterator();it1.hasNext(); ) {
                            MandatPersonne mandatPersonne = (MandatPersonne)it1.next();
                            if (mandatPersonne.getCodEtatMp().equalsIgnoreCase("V")) {
                                TraceMandataireSyncVO traceMandataireSyncVO = new TraceMandataireSyncVO();
                                traceMandataireSyncVO.setTraceMandat(traceMandat);
                                traceMandataireSyncVO.setMandatPersonne(mandatPersonne);
                                this.sychronisationPascal(traceMandataireSyncVO); 
                            }
                        }
                    
                ///* FIN : annulation des nouveaux mandataires (Synchronisation pascal) 
            
            }
            //insertTrace(mandat,Constants.COD_OPER_MODIF_MANDAT,Constants.COD_TACHE_VALIDMODIF_MANDAT);
            
            traceMandat.setMandat(mandat);
            Operation operation = new Operation();
            operation.setCodOperOper(Constants.COD_OPER_MODIF_MANDAT);

            tacheId.setCodOperOper(Constants.COD_OPER_CREAT_MANDAT);
            tacheId.setCodTachTach(Constants.COD_TACHE_VALID_MANDAT);
            tache.setTacheId(tacheId);
            traceMandat.setTache(tache);

         //   InsertTraceMandatTrt insertTraceMandatTrt=new InsertTraceMandatTrt();
         //   TraceMandat traceMandatRetour=(TraceMandat)insertTraceMandatTrt.exec(traceMandat);

            /* Garniture de la Table sychronisation_Pascal (en cas de validation de création, Modification, Renouvellement)*/
            if ((traceMandat.getTache().getTacheId().getCodTachTach().toString()).equalsIgnoreCase(Constants.COD_TACHE_VALID_MANDAT.toString()) ) {

                for (Iterator it1 = traceMandat.getMandat().getMandatPersonnes().iterator();it1.hasNext(); ) {
                    MandatPersonne mandatPersonne = (MandatPersonne)it1.next();
                    if (mandatPersonne.getCodEtatMp().equalsIgnoreCase("V")) {
                        TraceMandataireSyncVO traceMandataireSyncVO = new TraceMandataireSyncVO();
                        traceMandat.getTache().getTacheId().setCodOperOper(Constants.COD_OPER_CREAT_MANDAT);
                        traceMandataireSyncVO.setTraceMandat(traceMandat);
                        traceMandataireSyncVO.setMandatPersonne(mandatPersonne);
                        this.sychronisationPascal(traceMandataireSyncVO); 
                    }
                }
            }
            tacheId.setCodOperOper(Constants.COD_OPER_MODIF_MANDAT);
            tache.setTacheId(tacheId);
            traceMandat.setTache(tache);

            InsertTraceMandatTrt insertTraceMandatTrt=new InsertTraceMandatTrt();
            TraceMandat traceMandatRetour=(TraceMandat)insertTraceMandatTrt.exec(traceMandat);



        }  catch (Exception e) {
                com.oxia.fwk.core.Error erreur = new com.oxia.fwk.core.Error();
                StringBuffer text = 
                    new StringBuffer("Erreur lors de la Validation Modif du mandat ");
                text.append(e.toString());
                erreur.setCode("303");
                erreur.setDescription(text.toString());
                erreur.setKey("ValidModifMandat");
                paramModifMandVo.addError(erreur);
                logger.fatal(" *** Erreur lors de la Validation Modif du mandat concernant l'agence "+mandat.getCodStrcMand()+" : ", e);
//                throw new RuntimeException(e); 
            }
        return (paramModifMandVo);
    }
    
    
    
    public void genCroText(ValueObject vo) {    
    
    }
    
    public String getNumeroTache(IValueObject vo) {
        return(Constants.COD_OPER_MODIF_MANDAT.toString()+
        StrHandler.lpad(Constants.COD_TACHE_VALIDMODIF_MANDAT.toString(),'0',2));
    }
  
  
    public void genererSynchronisationPascal(ValueObject vo) { 
    
 //       MandatPersonne mandatPersonne0 =null;
 //       String s=mandatPersonne0.getCodEtatMp();
        
        TraceMandataireSyncVO traceMandataireSyncVO = (TraceMandataireSyncVO)vo;
        MandatPersonne mandatPersonne = traceMandataireSyncVO.getMandatPersonne();
        TraceMandat traceMandat = traceMandataireSyncVO.getTraceMandat();
        
        DateFormat myformat = new SimpleDateFormat("ddMMyy");
        /*partie fixe*/
        this.setCodeOperationSynch(traceMandat.getTache().getTacheId().getCodOperOper());
        this.setCodeTacheSynch(traceMandat.getTache().getTacheId().getCodTachTach());
        this.setDateOperationSynch(new Date());
        this.setCodeStructureSynch(traceMandat.getMandat().getContratCpt().getContratCptId().getCodStrcStrc());
        
        /*partie variable*/
        String partieVariable ="";
        String numCompte = StrHandler.lpad(traceMandat.getMandat().getContratCpt().getContratCptId().getCodPrdPrd().toString(),'0',4) +
                           StrHandler.lpad(traceMandat.getMandat().getContratCpt().getContratCptId().getNumCcptCcpt().toString(),'0',6);
       
        String datedebPouv = "      ";
        String dateFinPouv = "      ";
        String montLimt="000000000000000";
        if (traceMandat.getMandat().getDatDebMand()!=null)
        datedebPouv=myformat.format(traceMandat.getMandat().getDatDebMand());
        if (traceMandat.getMandat().getDatFinMand()!=null)
        dateFinPouv=myformat.format(traceMandat.getMandat().getDatFinMand());                         
         
        
        /*recherche des mandat personne*/
                String qualite="               ";
                if(mandatPersonne.getLibQualMp()!=null){
                qualite=StrHandler.rpad(mandatPersonne.getLibQualMp(),' ',15);}
                GetPersonneByNumSeqPersTrt  getPersonneByNumSeqPersTrt  = new GetPersonneByNumSeqPersTrt ();
                Personne personne=new Personne();
                personne.setNumSeqPers(mandatPersonne.getMandatPersonneId().getNumSeqPers());
                Personne pers =  (Personne)getPersonneByNumSeqPersTrt.exec(personne);
                
                String vNom = "";
                if (pers.getNomNomPers()!=null){
                    if (pers.getNomNomPers().length()>19){
                        vNom = pers.getNomNomPers().substring(0,19);
                    }else{
                        vNom = pers.getNomNomPers();
                    }
                }else{
                    logger.error(traceMandat.getMandat().getContratCpt().getContratCptId().getCodStrcStrc().toString()+numCompte+ "  >>  Nom Personne == null");

                }
                String vPrenom = "";
                if(pers.getNomPrnPers()!=null){
                    if (pers.getNomPrnPers().length()>19){
                        vPrenom = pers.getNomPrnPers().substring(0,19);
                    }else{
                        vPrenom = pers.getNomPrnPers();
                    }
                }else{
                    logger.error(traceMandat.getMandat().getContratCpt().getContratCptId().getCodStrcStrc().toString()+numCompte+ "  >> Prenom Personne == null");

                }
        
                String typePiece="";
                if(pers.getTypePiece().getCodTpceTpce().toString().equals(Constants.COD_PASS))
                    typePiece = "P";
                else if(pers.getTypePiece().getCodTpceTpce().toString().equals(Constants.COD_CSEJ))
                    typePiece = "S";
                else  typePiece = "C";
                String libNais="";
                if (pers.getLibNaisPers()!=null){
                    libNais=pers.getLibNaisPers();
                }
                partieVariable=partieVariable+numCompte+
                StrHandler.lpad(pers.getNumPcePers(),'0',10)+
                typePiece+
                determinerTitre(pers)+
                StrHandler.rpad(vNom,' ',20) + 
                StrHandler.rpad(vPrenom,' ',20)+
                myformat.format(pers.getDatNaisPers())+
                StrHandler.rpad(libNais,' ',20)+
                datedebPouv+dateFinPouv+
                StrHandler.rpad(traceMandat.getPersonnel().getNumMatrUser(),' ',4)+
                montLimt+qualite
                ;
                             
        this.setTextSynch(partieVariable);
    }  
    
    
    private String determinerTitre(Personne personne)  {
        String codeTitre = "1";
        String titre ="";
        if (personne.getLibTitrPers()!=null) {
           titre =personne.getLibTitrPers();
        }
             if(titre.equals("M."))
               codeTitre = "1";
             else if(titre.equals("Mme")) 
               codeTitre = "2";
             else if(titre.equals("Mlle")) 
               codeTitre = "3";
           
        return codeTitre;        
    }
    
    public IValueObject getNumeroDomaine(IValueObject vo){
        StructureDomaine structureDomaine = new StructureDomaine();
        ParamModifMandVo  paramModifMandVo  = (ParamModifMandVo)vo;
        structureDomaine.setCodDomDomm(Constants.COD_DOM_CONTRATCOMPTE);
        structureDomaine.setCodStrcStrc(paramModifMandVo.getParamInsertMandat().getPersonnel().getStructure().getCodStrcStrc());
        return structureDomaine;
    }
}
