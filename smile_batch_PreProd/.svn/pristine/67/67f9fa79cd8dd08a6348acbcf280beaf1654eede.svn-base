package com.bna.smile.model.domainecontratcompte.procuration.traitement;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;

import com.bna.commun.model.DetailMandatPersonne;
import com.bna.commun.model.MandatOperation;
import com.bna.commun.model.MandatPersonne;
import com.bna.commun.model.Personne;
import com.bna.commun.model.PieceAnnexe;
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
import com.oxia.fwk.core.IValueObject;
import com.oxia.fwk.core.ValueObject;

public class AnnulMandatTrt  extends Traitement {
    //public Context context = ContextHandler.getContext();

    public AnnulMandatTrt() {
    }

    /**
     * Methode permettant d'annuler un Mandat dans la BD
     * @param vo : TraceMandat
     * @return Mandat
     */
    public IValueObject perform(IValueObject vo) {
    Context context = ContextHandler.getContext();
    TraceMandat traceMandat = (TraceMandat)vo;

try{
      
        /* Garniture de la Table sychronisation_Pascal (en cas de validation de l'annulation)*/
        if ((traceMandat.getTache().getTacheId().getCodTachTach().toString()).equalsIgnoreCase(Constants.COD_TACHE_VAL_ANNUL.toString()) ) {

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

        /* Annulation des MandatOperations */
       

            for (Iterator it = traceMandat.getMandat().getMandatOperations().iterator(); 
                 it.hasNext(); ) {
                MandatOperation mandatOperation = (MandatOperation)it.next();
                mandatOperation.setDatFinMaop(new Date());
                CRUDservice crudService = 
                    (CRUDservice)context.getBean("crudservice");
                crudService.update(mandatOperation);
            }
            /* Annulation des MandatPersonnes */

            for (Iterator it1 = traceMandat.getMandat().getMandatPersonnes().iterator(); 
                 it1.hasNext(); ) {
                MandatPersonne mandatPersonne = (MandatPersonne)it1.next();
                if (mandatPersonne.getCodEtatMp().equalsIgnoreCase("V")) {

                    /* Annulation des DetailMandatPersonnes */
                    for (Iterator it2 = 
                         mandatPersonne.getDetailMandatPersonnes().iterator(); 
                         it2.hasNext(); ) {
                        DetailMandatPersonne detailMandatPersonne = 
                            (DetailMandatPersonne)it2.next();
                        if (detailMandatPersonne.getDatFinDmp() == null) {
                            detailMandatPersonne.setDatFinDmp(new Date());
                            CRUDservice crudService = 
                                (CRUDservice)context.getBean("crudservice");
                            crudService.update(detailMandatPersonne);
                        }
                    }
                    mandatPersonne.setCodEtatMp("N");
                    CRUDservice crudService = 
                        (CRUDservice)context.getBean("crudservice");
                    crudService.update(mandatPersonne);
                }

            }
            /* Annulation du mandat */
            traceMandat.getMandat().setCodEtatMand(Constants.COD_ETAT_MAND_ANN);
            traceMandat.getMandat().setCodEdemMand(null);
            traceMandat.getMandat().setDatFinMand(new Date());
            traceMandat.getMandat().setDatVldaMand(new Date());
            CRUDservice crudService = (CRUDservice)context.getBean("crudservice");
            crudService.update(traceMandat.getMandat());
            /*insertion de la trace*/
            InsertTraceMandatTrt insertTraceMandatTrt=new InsertTraceMandatTrt();
            insertTraceMandatTrt.exec(traceMandat);
           // this.sychronisationPascal(traceMandat); 
            return (traceMandat.getMandat());  
        

   
    }  catch (Exception e) {
            com.oxia.fwk.core.Error erreur = new com.oxia.fwk.core.Error();
            StringBuffer text = 
                new StringBuffer("Erreur lors de l'annulation du mandat ");
            text.append(e.toString());
            erreur.setCode("200");
            erreur.setDescription(text.toString());
            erreur.setKey("AnnulationMandat");
            traceMandat.getMandat().addError(erreur);
            logger.error("Exception dans AnnulMandatTrt concernant l'agence "+traceMandat.getMandat().getContratCpt().getStructure().getCodStrcStrc()+" : ",e);  
            throw new RuntimeException(e);  
        }
    //return (traceMandat.getMandat());
}
    public void genCroText(ValueObject vo) {
          
         
        }  
    public String getNumeroTache(ValueObject vo) {
        return(Constants.COD_OPER_ANNUL_MAND.toString()+
        StrHandler.lpad(Constants.COD_TACH_VAL_ANNUL_MAND.toString(),'0',2));
        
        
    }

    public void genererSynchronisationPascal(ValueObject vo) { 
    try{
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
                    logger.error(traceMandat.getMandat().getContratCpt().getContratCptId().getCodStrcStrc().toString()+numCompte+ "  >>  Prenom Personne == null");
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
                String libNais="";
                if (pers.getLibNaisPers()!=null){
                    libNais=pers.getLibNaisPers();
                }
                partieVariable=partieVariable+numCompte+
                StrHandler.lpad(numPiece,'0',10)+
                typePiece+
                determinerTitre(pers)+
                StrHandler.rpad(vNom,' ',20) + 
                StrHandler.rpad(vPrenom,' ',20)+
                myformat.format(pers.getDatNaisPers())+
                StrHandler.rpad(libNais,' ',20)+
                datedebPouv+dateFinPouv+
                StrHandler.lpad(traceMandat.getPersonnel().getNumMatrUser(),'0',4)+
                montLimt+qualite
                ;
                             
      //   System.out.println(partieVariable);
        this.setTextSynch(partieVariable);
    }catch(Exception e){
        
            throw new RuntimeException(e);  
        
        }
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