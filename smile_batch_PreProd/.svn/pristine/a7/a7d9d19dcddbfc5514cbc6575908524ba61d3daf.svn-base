package com.bna.smile.model.domainecontratcompte.procuration.traitement;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;

import com.bna.commun.model.MandatPersonne;
import com.bna.commun.model.Personne;
import com.bna.commun.model.SeqAgence;
import com.bna.commun.model.SeqAgenceId;
import com.bna.commun.model.StructureDomaine;
import com.bna.commun.model.TraceMandat;
import com.bna.commun.traitements.Traitement;
import com.bna.commun.util.ContextHandler;
import com.bna.commun.util.StrHandler;
import com.bna.smile.model.constant.Constants;
import com.bna.smile.model.domainecommun.service.CRUDservice;
import com.bna.smile.model.domainecommun.traitement.GetPersonneByNumSeqPersTrt;
import com.bna.smile.model.domainecontratcompte.procuration.model.TraceMandataireSyncVO;
import com.oxia.fwk.context.Context;
import com.oxia.fwk.core.ISearchEngine;
import com.oxia.fwk.core.IValueObject;
import com.oxia.fwk.core.ValueObject;

public class UpdateMandatTraceTrt extends Traitement {
    //    private static final Logger logger = Logger.getLogger(UpdateMandatTraceTrt.class);

    public UpdateMandatTraceTrt() {
    }

    /**
     * Methode permettant la MAJ un Mandat dans la BD
     * @param vo : TraceMandat
     * @return Mandat
     */
    public IValueObject perform(IValueObject vo) {
        Context context = ContextHandler.getContext();
        TraceMandat traceMandat = (TraceMandat)vo;
        CRUDservice crudService = 
            (CRUDservice)context.getBean("crudservice");
        ISearchEngine searchEngine=(ISearchEngine)Context.getInstance().getSpringContext().getBean("searchEngine");
        try {

            traceMandat.getMandat().setDatModMand(new Date());

            /* MAJ du Mandat dans la BD */
             /*insertion du num dossier jur si nouveau*/
              
            if (traceMandat.getMandat().getContratCpt().getClient().getTypePers().getCodTperTper().equals(Constants.PERSMORALE)) {
              if(traceMandat.getPersonnel().getStructure().getCodStrcStrc().intValue()==Constants.COD_STRC_DAJ.intValue()){
                if ((traceMandat.getMandat().getNumRdjMand()==null)||
                    (traceMandat.getMandat().getNumRdjMand()==0)){
                    SeqAgenceId seqAgenceId=new SeqAgenceId();
                    seqAgenceId.setLibSeqSeqa("SEQ_NUM_RDJ");
                    seqAgenceId.setCodStrcStrc(traceMandat.getMandat().getCodStrcMand());
                    SeqAgence seqAgence = (SeqAgence)searchEngine.get(SeqAgence.class, seqAgenceId);
                    traceMandat.getMandat().setNumRdjMand(seqAgence.getNumValSeqa());
                    long valeur = seqAgence.getNumValSeqa().intValue() + 1;
                    seqAgence.setNumValSeqa(new Long(valeur));
                    crudService.update(seqAgence);
                }
              }
            }
            crudService.update(traceMandat.getMandat());
            /*maj de la personne*/
             if (traceMandat.getMandat().getContratCpt().getClient().getTypePers().getCodTperTper().equals(Constants.PERSMORALE)) {
                if(traceMandat.getPersonnel().getStructure().getCodStrcStrc().intValue()==Constants.COD_STRC_DAJ.intValue()){
                if (traceMandat.getMandat().getNumRdjMand()!=null) {
                    GetPersonneByNumSeqPersTrt getPersonneTrt = 
                    new GetPersonneByNumSeqPersTrt();
                    Personne pers = 
                    (Personne)getPersonneTrt.exec(traceMandat.getMandat().getContratCpt().getClient().getPersonne());
                    pers.setNumDosJur(traceMandat.getMandat().getNumRdjMand());
                    pers.setCodStrcJur(traceMandat.getPersonnel().getStructure().getCodStrcStrc());
                    crudService.update(pers);
                }
                }
             }
            /*insertion de la trace*/
            InsertTraceMandatTrt insertTraceMandatTrt = 
                new InsertTraceMandatTrt();
            insertTraceMandatTrt.exec(traceMandat);

            if /*(traceMandat.getTache().getTacheId().getCodOperOper().toString().equalsIgnoreCase(Constants.COD_OPER_CREAT_MANDAT.toString()))&&*/
                ((traceMandat.getTache().getTacheId().getCodTachTach().toString()).equalsIgnoreCase(Constants.COD_TACHE_VALID_MANDAT.toString())) {

                for (Iterator it1 = 
                     traceMandat.getMandat().getMandatPersonnes().iterator(); 
                     it1.hasNext(); ) {
                    MandatPersonne mandatPersonne = (MandatPersonne)it1.next();
                    if (mandatPersonne.getCodEtatMp().equalsIgnoreCase("V")) {
                        TraceMandataireSyncVO traceMandataireSyncVO = 
                            new TraceMandataireSyncVO();
                        traceMandataireSyncVO.setTraceMandat(traceMandat);
                        traceMandataireSyncVO.setMandatPersonne(mandatPersonne);
                        this.sychronisationPascal(traceMandataireSyncVO);
                    }
                }
            }

            return (traceMandat.getMandat());

        } catch (Exception e) {
            com.oxia.fwk.core.Error erreur = new com.oxia.fwk.core.Error();
            StringBuffer text = 
                new StringBuffer("Erreur dans UpdateMandatTrt : ");
            text.append(e.toString());
            erreur.setCode("200");
            erreur.setDescription(text.toString());
            erreur.setKey("UpdateMandat");
            traceMandat.getMandat().addError(erreur);
            logger.error("Exception: UpdateMandatTraceTrt concernant l'agence " + 
                         traceMandat.getMandat().getCodStrcMand() + " : ", e);
            throw new RuntimeException(e);
        }
        // return (traceMandat.getMandat());
    }


    public void genCroText(ValueObject vo) {

    }

    public String getNumeroTache(IValueObject vo) {
        TraceMandat traceMandat = ((TraceMandat)vo);

        return (traceMandat.getTache().getTacheId().getCodOperOper().toString() + 
                StrHandler.lpad(traceMandat.getTache().getTacheId().getCodTachTach().toString(), 
                                '0', 2));
    }


    public void genererSynchronisationPascal(ValueObject vo) {

        TraceMandataireSyncVO traceMandataireSyncVO = 
            (TraceMandataireSyncVO)vo;
        MandatPersonne mandatPersonne = 
            traceMandataireSyncVO.getMandatPersonne();
        TraceMandat traceMandat = traceMandataireSyncVO.getTraceMandat();

        DateFormat myformat = new SimpleDateFormat("ddMMyy");
        /*partie fixe*/
        this.setCodeOperationSynch(traceMandat.getTache().getTacheId().getCodOperOper());
        this.setCodeTacheSynch(traceMandat.getTache().getTacheId().getCodTachTach());
        this.setDateOperationSynch(new Date());
        this.setCodeStructureSynch(traceMandat.getMandat().getContratCpt().getContratCptId().getCodStrcStrc());

        /*partie variable*/
        String partieVariable = "";
        String numCompte = 
            StrHandler.lpad(traceMandat.getMandat().getContratCpt().getContratCptId().getCodPrdPrd().toString(), 
                            '0', 4) + 
            StrHandler.lpad(traceMandat.getMandat().getContratCpt().getContratCptId().getNumCcptCcpt().toString(), 
                            '0', 6);

        String datedebPouv = "      ";
        String dateFinPouv = "      ";
        String montLimt = "000000000000000";
        if (traceMandat.getMandat().getDatDebMand() != null)
            datedebPouv = 
                    myformat.format(traceMandat.getMandat().getDatDebMand());
        if (traceMandat.getMandat().getDatFinMand() != null)
            dateFinPouv = 
                    myformat.format(traceMandat.getMandat().getDatFinMand());


        /*recherche des mandat personne*/
        String qualite = "               ";
        if (mandatPersonne.getLibQualMp() != null) {
            qualite = StrHandler.rpad(mandatPersonne.getLibQualMp(), ' ', 15);
        }
        GetPersonneByNumSeqPersTrt getPersonneByNumSeqPersTrt = 
            new GetPersonneByNumSeqPersTrt();
        Personne personne = new Personne();
        personne.setNumSeqPers(mandatPersonne.getMandatPersonneId().getNumSeqPers());
        Personne pers = (Personne)getPersonneByNumSeqPersTrt.exec(personne);

        String vNom = "";
        if (pers.getNomNomPers().length() > 19) {
            vNom = pers.getNomNomPers().substring(0, 19);
        } else {
            vNom = pers.getNomNomPers();
        }
        String vPrenom = "";
        if (pers.getNomPrnPers().length() > 19) {
            vPrenom = pers.getNomPrnPers().substring(0, 19);
        } else {
            vPrenom = pers.getNomPrnPers();
        }

        String typePiece = "";
        if (pers.getTypePiece().getCodTpceTpce().toString().equals(Constants.COD_PASS))
            typePiece = "P";
        else if (pers.getTypePiece().getCodTpceTpce().toString().equals(Constants.COD_CSEJ))
            typePiece = "S";
        else
            typePiece = "C";
        String libNais = "";
        if (pers.getLibNaisPers() != null) {
            libNais = pers.getLibNaisPers();
        }
        partieVariable = 
                partieVariable + numCompte + StrHandler.lpad(pers.getNumPcePers(), 
                                                             '0', 10) + 
                typePiece + determinerTitre(pers) + 
                StrHandler.rpad(vNom, ' ', 20) + 
                StrHandler.rpad(vPrenom, ' ', 20) + 
                myformat.format(pers.getDatNaisPers()) + 
                StrHandler.rpad(libNais, ' ', 20) + datedebPouv + dateFinPouv + 
                StrHandler.lpad(traceMandat.getPersonnel().getNumMatrUser(), 
                                '0', 4) + montLimt + qualite;

        System.out.println(partieVariable);
        this.setTextSynch(partieVariable);
    }


    private String determinerTitre(Personne personne) {
        String codeTitre = "1";
        String titre = "";
        if (personne.getLibTitrPers() != null) {
            titre = personne.getLibTitrPers();
        }
        if (titre.equals("M."))
            codeTitre = "1";
        else if (titre.equals("Mme"))
            codeTitre = "2";
        else if (titre.equals("Mlle"))
            codeTitre = "3";

        return codeTitre;
    }

    public IValueObject getNumeroDomaine(IValueObject vo) {
        StructureDomaine structureDomaine = new StructureDomaine();
        TraceMandat traceMandat = (TraceMandat)vo;
        structureDomaine.setCodDomDomm(Constants.COD_DOM_CONTRATCOMPTE);
        structureDomaine.setCodStrcStrc(traceMandat.getPersonnel().getStructure().getCodStrcStrc());
        return structureDomaine;
    }
}
