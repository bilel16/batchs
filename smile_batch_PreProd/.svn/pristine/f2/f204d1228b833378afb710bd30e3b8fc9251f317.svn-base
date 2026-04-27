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
import com.bna.commun.model.SeqAgence;
import com.bna.commun.model.SeqAgenceId;
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

public class MiseAJourMandatTrt extends Traitement{

    //Context context = ContextHandler.getContext();
    //private static final Logger logger = Logger.getLogger(MiseAJourMandatTrt.class);

    public MiseAJourMandatTrt() {
    }

    public IValueObject perform(IValueObject vo) {

        Context context = ContextHandler.getContext();
       // Mandat mandatRetour = new Mandat();
    try{

        logger.debug("debut MiseAJourMandatTrt"); 
        //ISearchEngine searchEngine = (SearchEngine)context.getBean("searchEngine");
        ISearchEngine searchEngine=(ISearchEngine)Context.getInstance().getSpringContext().getBean("searchEngine");
        ICriteria criteria = searchEngine.createCriteria();
        IExpression expression = searchEngine.createExpression();

        if(((Mandat)vo).getCodEtatMand().equalsIgnoreCase("A")){ /// en cas de prévalidation generer le N° dossier
        String strc=StrHandler.lpad(((Mandat)vo).getCodStrcMand().toString(),'0',3);
        String d=""+(new Date().getYear()+1900);
        String  m=StrHandler.lpad(getNumDossierMandat(((Mandat)vo)).toString(),'0',5);
        
        Long numDem=new Long(strc+d+m);
        ((Mandat)vo).setNumDemMand(numDem);
        }

        criteria.add(expression.eq("numMandMand", ((Mandat)vo).getNumMandMand()));

        /* Charger le mandat existante */
        Mandat mandatBase = (Mandat)searchEngine.get(Mandat.class, ((Mandat)vo).getNumMandMand());


        HibernateTemplate  hibernateTemplate = (HibernateTemplate) context.getBean("hibernateTemplate");
        hibernateTemplate.evict(mandatBase);

        /* MAJ de l'objet Mandat */
        UpdateMandatTrt updateMandatTrt = new UpdateMandatTrt();
        vo = updateMandatTrt.exec(vo);
        /* MAJ en cas de renouvellement */

        if ((((Mandat)vo).getDatFinMand()!=null && !((DateHandler.dateToStr(((Mandat)vo).getDatFinMand())).equals(DateHandler.dateToStr(mandatBase.getDatFinMand())))) || (mandatBase.getDatFinMand()!=null && !((DateHandler.dateToStr(mandatBase.getDatFinMand())).equals(DateHandler.dateToStr(((Mandat)vo).getDatFinMand()))))){

            InsertDetailRenouvellementMandatTrt insertDetailRenouvellementMandatTrt=new InsertDetailRenouvellementMandatTrt();
            DetailRenouvellementMandat detailRenouvellementMandat = (DetailRenouvellementMandat) insertDetailRenouvellementMandatTrt.exec((Mandat)vo);
        }

        MiseAJourMandatOperation(((Mandat)vo), mandatBase);
        MiseAJourMandatPersonne(((Mandat)vo), mandatBase);

        vo = (Mandat)searchEngine.get(Mandat.class, ((Mandat)vo).getNumMandMand());
        
        logger.debug("Fin MiseAJourMandatTrt avec succes"); 
        
        return (vo);
        }
           catch (Exception e) {
              com.oxia.fwk.core.Error erreur = new com.oxia.fwk.core.Error();
              StringBuffer text = 
                  new StringBuffer("Erreur dans MiseAJourMandatTrt : ");
              text.append(e.toString());
              erreur.setCode("200");
              erreur.setDescription(text.toString());
              erreur.setKey("MiseAJourMandat");
              vo.addError(erreur);
              logger.error("Erreur lors de la Validation Modif du mandat (MiseAJourMandat) concernant l'agence "+ ((Mandat)vo).getCodStrcMand() +" : ", e);
            //  throw new RuntimeException(e); 
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
                        //else /* MandatOperation Supprimée */
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
      return (Constants.CODE_RESSOURCE_GENERALE);        
    }
    
    public void genererSynchronisationPascal(ValueObject vo) { 
    
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
                             
         System.out.println(partieVariable);
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

}
