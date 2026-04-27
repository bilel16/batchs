package com.bna.smile.model.domainecontratcompte.procuration.traitement;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;

import org.springframework.orm.hibernate3.HibernateTemplate;

import com.bna.commun.model.DetailRenouvellementMandat;
import com.bna.commun.model.Mandat;
import com.bna.commun.model.MandatOperation;
import com.bna.commun.model.MandatPersonne;
import com.bna.commun.model.Personne;
import com.bna.commun.model.PieceAnnexe;
import com.bna.commun.model.SeqAgence;
import com.bna.commun.model.SeqAgenceId;
import com.bna.commun.model.StructureDomaine;
import com.bna.commun.model.TraceMandat;
import com.bna.commun.traitements.Traitement;
import com.bna.commun.util.ContextHandler;
import com.bna.commun.util.DateHandler;
import com.bna.commun.util.StrHandler;
import com.bna.smile.model.constant.Constants;
import com.bna.smile.model.domainecommun.service.CRUDservice;
import com.bna.smile.model.domainecommun.traitement.GetPersonneByNumSeqPersTrt;
import com.bna.smile.model.domainecontratcompte.procuration.model.TraceMandataireSyncVO;
import com.oxia.fwk.context.Context;
import com.oxia.fwk.core.ICriteria;
import com.oxia.fwk.core.IExpression;
import com.oxia.fwk.core.ISearchEngine;
import com.oxia.fwk.core.IValueObject;
import com.oxia.fwk.core.ValueObject;

public class MiseAJourMandatTraceTrt extends Traitement{

//    private static final Logger logger = Logger.getLogger(MiseAJourMandatTraceTrt.class);

    public MiseAJourMandatTraceTrt() {
    }

    /**
     * Methode permettant la MAJ d'un MAndat avec sa Trace
     * @param vo : TraceMandat
     * @return TraceMandat
     * @autors BOUSSEN Youssef & KRIAA Hatem
     */

    public IValueObject perform(IValueObject vo) {

        Context context = ContextHandler.getContext();
        TraceMandat traceMandat = ((TraceMandat)vo);

    try{
            logger.debug("debut MiseAJourMandatTraceTrt");                 

            //ISearchEngine searchEngine = (SearchEngine)context.getBean("searchEngine");
            ISearchEngine searchEngine=(ISearchEngine)Context.getInstance().getSpringContext().getBean("searchEngine");
            ICriteria criteria = searchEngine.createCriteria();
            IExpression expression = searchEngine.createExpression();
            CRUDservice crudService = (CRUDservice)context.getBean("crudservice");

            

            if(traceMandat.getMandat().getCodEtatMand().equalsIgnoreCase("A")){ /// en cas de prévalidation generer le N° dossier
            String strc=StrHandler.lpad(traceMandat.getMandat().getCodStrcMand().toString(),'0',3);
            String d=""+(new Date().getYear()+1900);
            String  m=StrHandler.lpad(getNumDossierMandat(traceMandat.getMandat()).toString(),'0',5);
            
            Long numDem=new Long(strc+d+m);
            traceMandat.getMandat().setNumDemMand(numDem);
            }
    
            criteria.add(expression.eq("numMandMand", traceMandat.getMandat().getNumMandMand()));
    
            /* Charger le mandat existante */
            Mandat mandatBase = (Mandat)searchEngine.get(Mandat.class, traceMandat.getMandat().getNumMandMand());
    
            HibernateTemplate  hibernateTemplate = (HibernateTemplate) context.getBean("hibernateTemplate");
            hibernateTemplate.evict(mandatBase);
    
            /* MAJ de l'objet Mandat */
            /*insertion du num dossier jur si nouveau*/
             if (traceMandat.getMandat().getContratCpt().getClient().getTypePers().getCodTperTper().equals(Constants.PERSMORALE)) {
             if(traceMandat.getPersonnel().getStructure().getCodStrcStrc().intValue()==Constants.COD_STRC_DAJ.intValue()){
                if ((traceMandat.getMandat().getNumRdjMand()==null)||
                    (traceMandat.getMandat().getNumRdjMand()==0)){
                 SeqAgenceId seqAgenceId=new SeqAgenceId();
                 seqAgenceId.setLibSeqSeqa("SEQ_NUM_RDJ");
                 seqAgenceId.setCodStrcStrc(mandatBase.getCodStrcMand());
                 SeqAgence seqAgence = (SeqAgence)searchEngine.get(SeqAgence.class, seqAgenceId);
                 traceMandat.getMandat().setNumRdjMand(seqAgence.getNumValSeqa());
                  long valeur = seqAgence.getNumValSeqa().intValue() + 1;
                 seqAgence.setNumValSeqa(new Long(valeur));
                 crudService.update(seqAgence);
                }
             }
             }
            UpdateMandatTrt updateMandatTrt = new UpdateMandatTrt();
            vo = updateMandatTrt.exec(traceMandat.getMandat());
            /*maj de la personne*/
            if (traceMandat.getMandat().getContratCpt().getClient().getTypePers().getCodTperTper().equals(Constants.PERSMORALE)) {
                if(traceMandat.getPersonnel().getStructure().getCodStrcStrc().intValue()==Constants.COD_STRC_DAJ.intValue()){
                if (traceMandat.getMandat().getNumRdjMand()!=null) {
                    GetPersonneByNumSeqPersTrt getPersonneTrt = 
                    new GetPersonneByNumSeqPersTrt();
                    Personne pers = 
                    (Personne)getPersonneTrt.exec(mandatBase.getContratCpt().getClient().getPersonne());
                    pers.setNumDosJur(traceMandat.getMandat().getNumRdjMand());
                    pers.setCodStrcJur(traceMandat.getPersonnel().getStructure().getCodStrcStrc());
                    crudService.update(pers);
                }
                }
             }
            /* MAJ en cas de renouvellement */
    
            if ((traceMandat.getMandat().getDatFinMand()!=null && !((DateHandler.dateToStr(traceMandat.getMandat().getDatFinMand())).equals(DateHandler.dateToStr(mandatBase.getDatFinMand())))) || (mandatBase.getDatFinMand()!=null && !((DateHandler.dateToStr(mandatBase.getDatFinMand())).equals(DateHandler.dateToStr(traceMandat.getMandat().getDatFinMand()))))){
                InsertDetailRenouvellementMandatTrt insertDetailRenouvellementMandatTrt=new InsertDetailRenouvellementMandatTrt();
                DetailRenouvellementMandat detailRenouvellementMandat = (DetailRenouvellementMandat) insertDetailRenouvellementMandatTrt.exec(traceMandat.getMandat());
    
            }
    
            MiseAJourMandatOperation(traceMandat.getMandat(), mandatBase);
            MiseAJourMandatPersonne (traceMandat.getMandat(), mandatBase);
    
            vo = (Mandat)searchEngine.get(Mandat.class, traceMandat.getMandat().getNumMandMand());
            /*insertion de la trace mandat*/
            InsertTraceMandatTrt insertTraceMandatTrt=new InsertTraceMandatTrt();
            insertTraceMandatTrt.exec(traceMandat);
            
            /* Garniture de la Table sychronisation_Pascal (en cas de validation de création, Modification, Renouvellement)*/
            if ((traceMandat.getTache().getTacheId().getCodTachTach().toString()).equalsIgnoreCase(Constants.COD_TACHE_VALID_MANDAT.toString()) &&
            (!traceMandat.getMandat().getCodEtatMand().equalsIgnoreCase("R"))) {
    
                for (Iterator it1 = traceMandat.getMandat().getMandatPersonnes().iterator();it1.hasNext(); ) {
                    MandatPersonne mandatPersonne = (MandatPersonne)it1.next();
                    if (mandatPersonne.getCodEtatMp().equalsIgnoreCase("V")) {
                        TraceMandataireSyncVO traceMandataireSyncVO = new TraceMandataireSyncVO();
                        traceMandataireSyncVO.setTraceMandat(traceMandat);
                        traceMandataireSyncVO.setMandatPersonne(mandatPersonne);
                        this.sychronisationPascal(traceMandataireSyncVO); 
                    }
                }
            }
            logger.debug("fin MiseAJourMandatTraceTrt avec succes");                 
    
            return (vo);

        }
           catch (Exception e) {
              com.oxia.fwk.core.Error erreur = new com.oxia.fwk.core.Error();
              StringBuffer text = new StringBuffer("Erreur dans MiseAJourMandatTraceTrt : ");
              text.append(e.toString());
              erreur.setCode("200");
              erreur.setDescription(text.toString());
              erreur.setKey("MiseAJourMandatTrace");
              vo.addError(erreur);
              logger.error("*** Exception: MiseAJourMandatTraceTrt concernant l'agence "+traceMandat.getMandat().getCodStrcMand()+" : ",e);
              return (vo);
          }

    }

    private void MiseAJourMandatOperation(Mandat mandat, Mandat mandatBase) {

        Context context = ContextHandler.getContext();
        MandatOperation mandatOperationTemp;
        /* le traitement des mandatsOperation ne se fait que dans le cas d'un mandat spécial ou de justice */
        if (mandat.getCodTypMand().equals("S") || mandat.getCodTypMand().equals("JS")) {
            boolean v_modifier = false;
            boolean v_exist = false;

            for (Iterator it = mandat.getMandatOperations().iterator(); it.hasNext(); ) {
                MandatOperation mandatOperationNew =  (MandatOperation)it.next();
                /* Verifier si le MandatOperation est nouvellement inserée */
                if (mandatOperationNew.getMandatOperationId().getNumMaopMaop() != null && mandatOperationNew.getMandatOperationId().getNumMaopMaop().longValue() !=0) { /* Le cas ou le MandatOperation est existant */
                    for (Iterator it1 = mandatBase.getMandatOperations().iterator(); it1.hasNext(); ) {
                        MandatOperation mandatOperationOld = (MandatOperation)it1.next();
                        /* si la MandatOperation existe  */
                           if (mandatOperationOld.getMandatOperationId().equals(mandatOperationNew.getMandatOperationId()) || 
                            (
                               mandatOperationOld.getMandatOperationId().getCodOperOper().longValue()==mandatOperationNew.getMandatOperationId().getCodOperOper().longValue() &&
                               (mandat.getNumPereMand()!=null && mandatOperationOld.getMandatOperationId().getNumMandMand().longValue()==mandat.getNumPereMand().longValue()) && 
                               mandatOperationOld.getMandatOperationId().getNumMaopMaop().longValue()==mandatOperationNew.getNumPereMaop().longValue() 
                               )
                            ) {
                            if (!mandatOperationOld.equals(mandatOperationNew)) { /* mandat operation modifié */
                                v_modifier = true;
                                HibernateTemplate  hibernateTemplate = (HibernateTemplate) context.getBean("hibernateTemplate");
                                hibernateTemplate.evict(mandatOperationOld);
                            }
                        }

                    }
                    if (v_modifier) {
                        /* MAJ MandatOperation */
                        UpdateMandatOperationTrt updateMandatOperationTrt = new UpdateMandatOperationTrt();
                        mandatOperationTemp = (MandatOperation)updateMandatOperationTrt.exec(mandatOperationNew);
                    }
                    v_modifier = false;

                } else { /* Le cas ou le MandatOperation est non existant :  création d'une nouvelle MandatOperation*/
                    InsertMandatOperationTrt insertMandatOperationTrt =  new InsertMandatOperationTrt();
                    mandatOperationTemp = (MandatOperation)insertMandatOperationTrt.exec(mandatOperationNew);

                }
            }

            for (Iterator it2 = mandatBase.getMandatOperations().iterator(); it2.hasNext(); ) {
                MandatOperation mandatOperationOld = (MandatOperation)it2.next();
                for (Iterator it3 = mandat.getMandatOperations().iterator();  it3.hasNext(); ) {
                    MandatOperation mandatOperationNew = (MandatOperation)it3.next();
                     
                    /* Verifier si le MandatOperation n'est pas supprimé */
                          if ( (mandatOperationOld.getMandatOperationId().equals(mandatOperationNew.getMandatOperationId())) || 
                            (
                            (mandatOperationOld.getMandatOperationId().getCodOperOper().longValue()==mandatOperationNew.getMandatOperationId().getCodOperOper().longValue() )&&
                            ((mandat.getNumPereMand()!=null && mandatOperationOld.getMandatOperationId().getNumMandMand().longValue()==mandat.getNumPereMand().longValue()) || (mandatOperationNew.getMandatOperationId().getNumMandMand()!=null &&  mandatOperationOld.getMandatOperationId().getNumMandMand().longValue()==mandatOperationNew.getMandatOperationId().getNumMandMand().longValue())) && 
                            (mandatOperationNew.getNumPereMaop()!=null && mandatOperationOld.getMandatOperationId().getNumMaopMaop().longValue()==mandatOperationNew.getNumPereMaop().longValue()) 
                            )
                           ) 
                        {
                            v_exist = true;
                        }
                }
                if (!v_exist && mandatOperationOld.getDatFinMaop()==null) { /* MandatOperation Supprimée sauf dans le cas d'une modification*/
                    mandatOperationOld.setDatFinMaop(new Date());
                    HibernateTemplate  hibernateTemplate = (HibernateTemplate) context.getBean("hibernateTemplate");
                    hibernateTemplate.evict(mandatOperationOld);
                    UpdateMandatOperationTrt updateMandatOperationTrt = new UpdateMandatOperationTrt();
                    mandatOperationTemp = (MandatOperation)updateMandatOperationTrt.exec(mandatOperationOld);
                }
                v_exist = false;
            }
        }
    }


    private void MiseAJourMandatPersonne(Mandat mandat, Mandat mandatBase) {

        Context context = ContextHandler.getContext();
        MandatPersonne mandatPersonneTemp;
        /* le traitement des mandatsPersonnes */

        boolean v_modifier = false;
        boolean v_exist = false;

        for (Iterator it = mandat.getMandatPersonnes().iterator();  it.hasNext(); ) {
            MandatPersonne mandatPersonneNew = (MandatPersonne)it.next();
            mandatPersonneNew.getMandatPersonneId().setNumMandMand(mandat.getNumMandMand());
            /* Verifier si la MandatPersonne est nouvellement inserée */
            if (verifExistMandatPersonne(mandatPersonneNew) != null && mandatPersonneNew.getMandatPersonneId().getNumSeqPers().longValue()!=0) { /* Le cas ou le MandatPersonne est existant */
                for (Iterator it1 = mandatBase.getMandatPersonnes().iterator();it1.hasNext(); ) {
                    MandatPersonne mandatPersonneOld = (MandatPersonne)it1.next();
                    /* si la MandatPersonne existe  */
                    if (mandatPersonneOld.getMandatPersonneId().equals(mandatPersonneNew.getMandatPersonneId())) {
                        if ((mandatPersonneNew.getLibQualMp()!=null && !mandatPersonneNew.getLibQualMp().equalsIgnoreCase(mandatPersonneOld.getLibQualMp())) || (mandatPersonneOld.getLibQualMp()!=null && !mandatPersonneOld.getLibQualMp().equalsIgnoreCase(mandatPersonneNew.getLibQualMp())) || ( mandatPersonneNew.getLibQualMp()==null && mandatPersonneNew.getLibQualMp()!=null) || (!mandatPersonneOld.getCodEtatMp().equalsIgnoreCase(mandatPersonneNew.getCodEtatMp())) ) { /* mandat Personne modifié */
                            v_modifier = true;
                        } 
                    }

                }
                
                if (v_modifier) {
                    /*MAJ MandatPersonne*/
                    HibernateTemplate  hibernateTemplate = (HibernateTemplate) context.getBean("hibernateTemplate");
                    hibernateTemplate.evict(mandatPersonneNew);
                    UpdateMandatPersonneTrt updateMandatPersonneTrt = new UpdateMandatPersonneTrt();
                    mandatPersonneTemp = (MandatPersonne)updateMandatPersonneTrt.exec(mandatPersonneNew);
                }
                v_modifier = false;

            } else { /* Le cas ou le MandatPersonne est non existant :  création d'une nouvelle MandatPersonne*/
                InsertMandatPersonneTrt insertMandatPersonneTrt =  new InsertMandatPersonneTrt();
                insertMandatPersonneTrt.exec(mandatPersonneNew);
            }
        }

        for (Iterator it2 = mandatBase.getMandatPersonnes().iterator(); it2.hasNext(); ) {
            MandatPersonne mandatPersonneOld = (MandatPersonne)it2.next();
            for (Iterator it3 = mandat.getMandatPersonnes().iterator(); it3.hasNext(); ) {
                MandatPersonne mandatPersonneNew = (MandatPersonne)it3.next();
                /* Verifier si le MandatPersonne n'est pas supprimé */
                if (mandatPersonneOld.getMandatPersonneId().equals(mandatPersonneNew.getMandatPersonneId())) {
                    v_exist = true;
                }
            }
            if (!v_exist) { /* MandatPersonne Supprimée */
            
                HibernateTemplate  hibernateTemplate = (HibernateTemplate) context.getBean("hibernateTemplate");
                hibernateTemplate.evict(mandatPersonneOld);
                mandatPersonneOld.setCodEtatMp("N");
                UpdateMandatPersonneTrt updateMandatPersonneTrt = new UpdateMandatPersonneTrt();
                mandatPersonneTemp = (MandatPersonne)updateMandatPersonneTrt.exec(mandatPersonneOld);
            }
            v_exist = false;
        }
    }


    private MandatPersonne verifExistMandatPersonne(MandatPersonne mp) {

        Context context = ContextHandler.getContext();
        ISearchEngine searchEngine=(ISearchEngine)Context.getInstance().getSpringContext().getBean("searchEngine");
        MandatPersonne mandatPersonne = 
            (MandatPersonne)searchEngine.get(MandatPersonne.class, 
                                             mp.getMandatPersonneId());
        return (mandatPersonne);
    }
    
    public Long getNumDossierMandat(Mandat mandat) {

        Context context = ContextHandler.getContext();
        CRUDservice crudService = (CRUDservice)context.getBean("crudservice");
        ISearchEngine searchEngine=(ISearchEngine)Context.getInstance().getSpringContext().getBean("searchEngine");

        /* Rechercher la sequence N° demande relative à la structure donnée */

        SeqAgenceId seqAgenceId=new SeqAgenceId();
        seqAgenceId.setLibSeqSeqa("SEQ_DEM_MAND");
        seqAgenceId.setCodStrcStrc(mandat.getCodStrcMand());

        SeqAgence seqAgence = (SeqAgence)searchEngine.get(SeqAgence.class, seqAgenceId);
    
        long valeur = seqAgence.getNumValSeqa().intValue() + 1;
        seqAgence.setNumValSeqa(new Long(valeur));
        /* MAJ de la sequence */
        crudService.update(seqAgence);
        /* Inserer le N° du ContratCpt*/
        return (new Long(seqAgence.getNumValSeqa().intValue()));
    }


    public void genCroText(ValueObject vo) {    
    
    }
    
    public String getNumeroTache(IValueObject vo) {
        TraceMandat traceMandat = ((TraceMandat)vo);
        
        return(traceMandat.getTache().getTacheId().getCodOperOper().toString()+
        StrHandler.lpad(traceMandat.getTache().getTacheId().getCodTachTach().toString(),'0',2));
    }
    
    
    
    public void genererSynchronisationPascal(ValueObject vo) { 
    
        TraceMandataireSyncVO traceMandataireSyncVO = (TraceMandataireSyncVO)vo;
        MandatPersonne mandatPersonne = traceMandataireSyncVO.getMandatPersonne();
        TraceMandat traceMandat = traceMandataireSyncVO.getTraceMandat();
        
        DateFormat myformat = new SimpleDateFormat("ddMMyy");
        String numCompte = StrHandler.lpad(traceMandat.getMandat().getContratCpt().getContratCptId().getCodPrdPrd().toString(),'0',4) +
                           StrHandler.lpad(traceMandat.getMandat().getContratCpt().getContratCptId().getNumCcptCcpt().toString(),'0',6);
        
        /*partie fixe*/
         if (traceMandat.getTache()!=null){
            this.setCodeOperationSynch(traceMandat.getTache().getTacheId().getCodOperOper());
            this.setCodeTacheSynch(traceMandat.getTache().getTacheId().getCodTachTach());
            }else {
                logger.error(traceMandat.getMandat().getContratCpt().getContratCptId().getCodStrcStrc().toString()+numCompte + "   >> traceMandat.getTache() == null");
            }
        
        this.setDateOperationSynch(new Date());
        this.setCodeStructureSynch(traceMandat.getMandat().getContratCpt().getContratCptId().getCodStrcStrc());
        
        /*partie variable*/
        String partieVariable ="";
        
        String datedebPouv = "      ";
        String dateFinPouv = "      ";
        String montLimt="000000000000000";
        if (traceMandat.getMandat().getDatDebMand()!=null){
        datedebPouv=myformat.format(traceMandat.getMandat().getDatDebMand());
        }else {
            logger.error(traceMandat.getMandat().getContratCpt().getContratCptId().getCodStrcStrc().toString()+numCompte+"   >> Date début mandat == null");
        }
        if (traceMandat.getMandat().getDatFinMand()!=null){
        dateFinPouv=myformat.format(traceMandat.getMandat().getDatFinMand());                         
        }else {
            logger.debug(numCompte+ " >> Date fin mandat == null");
        }
        /*recherche des mandat personne*/
                String qualite="               ";
                if(mandatPersonne.getLibQualMp()!=null){
                qualite=StrHandler.rpad(mandatPersonne.getLibQualMp(),' ',15);
                }else {
                        logger.debug(numCompte + "  >>  mandatPersonne.getLibQualMp == null");
                    }
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
                 }else {
                            logger.error(traceMandat.getMandat().getContratCpt().getContratCptId().getCodStrcStrc().toString()+numCompte+ "  >>  Nom Personne == null");
                        }
                String vPrenom = "";
                if (pers.getNomPrnPers()!=null){
                    if (pers.getNomPrnPers().length()>19){
                        vPrenom = pers.getNomPrnPers().substring(0,19);
                    }else{
                        vPrenom = pers.getNomPrnPers();
                    }
                }else {
                            logger.error(numCompte+ " >> Prénom Personne == null");
                        }
                String typePiece="";
                String numPiece=pers.getNumPcePers();
                if(pers.getTypePiece().getCodTpceTpce().toString().equals(Constants.COD_CIN.toString()))
                    typePiece = "C";
                else {
                    if(pers.getTypePiece().getCodTpceTpce().toString().equals(Constants.COD_NUM_ORDRE.toString())){
                        if(pers.getPieceAnnexes().size()>0){
                            for (Iterator it = pers.getPieceAnnexes().iterator(); it.hasNext(); ){
                                PieceAnnexe pieceAnnexe=(PieceAnnexe) it.next();
                                if (pieceAnnexe.getTypePiece().getCodTpceTpce().toString().equals(Constants.COD_PASS.toString())){
                                    typePiece = "P";
                                    numPiece = pieceAnnexe.getPieceAnnexeId().getNumPcePian();
                                    break;
                                }else{ 
                                    if(pieceAnnexe.getTypePiece().getCodTpceTpce().toString().equals(Constants.COD_CSEJ.toString())){
                                        typePiece = "S";
                                        numPiece = pieceAnnexe.getPieceAnnexeId().getNumPcePian();
                                    }
                                }
                            }
                        }
                    }                   
                    else  typePiece = "C";
                }
                
                String libNaisPers ="";
                if(pers.getLibNaisPers()!=null){
                    libNaisPers=pers.getLibNaisPers();
                }else {
                            logger.debug(numCompte+ " >> getLibNaisPers == null");
                        }
                Date datNaisPers =new Date();
                if(pers.getDatNaisPers()!=null){
                    datNaisPers=pers.getDatNaisPers()  ;
                }else {
                            logger.error(numCompte + " >> getDatNaisPers == null");
                        }
                
                partieVariable=partieVariable+numCompte+
                StrHandler.lpad(numPiece,'0',10)+
                typePiece+
                determinerTitre(pers)+
                StrHandler.rpad(vNom,' ',20) + 
                StrHandler.rpad(vPrenom,' ',20)+
                myformat.format(datNaisPers)+
                StrHandler.rpad(libNaisPers,' ',20)+
                datedebPouv+dateFinPouv+
                StrHandler.lpad(traceMandat.getPersonnel().getNumMatrUser(),'0',4)+
                montLimt+qualite
                ;
                        
       //  System.out.println(partieVariable);
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
        TraceMandat traceMandat = (TraceMandat)vo;
        structureDomaine.setCodDomDomm(Constants.COD_DOM_CONTRATCOMPTE);
        structureDomaine.setCodStrcStrc(traceMandat.getPersonnel().getStructure().getCodStrcStrc());
        return structureDomaine;
    }
}
