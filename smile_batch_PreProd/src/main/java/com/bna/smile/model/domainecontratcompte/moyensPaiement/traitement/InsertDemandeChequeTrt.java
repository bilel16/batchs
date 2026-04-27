package com.bna.smile.model.domainecontratcompte.moyensPaiement.traitement;


import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.bna.commun.model.DemandeCheque;
import com.bna.commun.model.SeqAgence;
import com.bna.commun.model.SeqAgenceId;
import com.bna.commun.model.Structure;
import com.bna.commun.model.StructureDomaine;
import com.bna.commun.traitements.Traitement;
import com.bna.commun.util.ContextHandler;
import com.bna.commun.util.StrHandler;
import com.bna.smile.model.constant.Constants;
import com.bna.smile.model.domainecommun.commande.GetStructureCmd;
import com.bna.smile.model.domainecommun.service.CRUDservice;
import com.oxia.fwk.context.Context;
import com.oxia.fwk.core.ISearchEngine;
import com.oxia.fwk.core.IValueObject;
import com.oxia.fwk.core.ValueObject;

public class InsertDemandeChequeTrt  extends Traitement{
    

    public InsertDemandeChequeTrt() {
    }

    
    /** méthode d'insertion  d'une demande de cheques 
     * et retourne le même objet inséré
     * @param   ValueObject : demandeCheque
     * @return  ValueObject : demandeCheque
     */
    public IValueObject perform(IValueObject vo) {
        DemandeCheque demandeCheque = (DemandeCheque)vo;
        Context context = ContextHandler.getContext();
        try {
        
        if(this.checkClotureJournee()){
            this.setCroFlag(false);  
            CRUDservice crudservice = 
                (CRUDservice)context.getBean("crudservice");

            String strc = "";
                if(demandeCheque.getContratCpt() != null)
                 strc =  StrHandler.lpad(demandeCheque.getContratCpt().getContratCptId().getCodStrcStrc().toString(),'0', 3);
                else strc =  StrHandler.lpad(demandeCheque.getCompteInterne().getCompteInterneId().getCodStrcStrc().toString(),'0', 3);
            
            String d = "" + (new Date().getYear() + 1900);
            String m = 
                StrHandler.lpad(getNumDemandeCheque(Long.valueOf(strc)).toString(), 
                                '0', 6);

            String numDem = (strc + d + m);
            demandeCheque.setNumDemDchq(numDem);            
            crudservice.create(demandeCheque);
            
            if(!demandeCheque.hasError()){
            // traiter le cas du compte interne 141, insertion dans la table synchronise_pascal
              if(demandeCheque.getCompteInterne()!= null){
                  this.sychronisationPascal(demandeCheque);
              }
            }
           
        }else{
            com.oxia.fwk.core.Error erreur = new com.oxia.fwk.core.Error();
            StringBuffer text = new StringBuffer("La journée est déja clôturée...");            
            erreur.setCode("100");
            erreur.setDescription(text.toString());
            erreur.setKey("InsertDemandeCheque");
            demandeCheque.addError(erreur);        
        }

        } catch (Exception e) {
            com.oxia.fwk.core.Error erreur = new com.oxia.fwk.core.Error();
            StringBuffer text = 
                new StringBuffer("Erreur dans InsertDemandeChequeTrt : ");
            text.append(e.toString());
            erreur.setCode("100");
            erreur.setDescription(text.toString());
            erreur.setKey("InsertDemandeCheque");
            demandeCheque.addError(erreur);
            logger.error("Erreur au niveau de l'agence <<" + demandeCheque.getContratCpt().getContratCptId().getCodStrcStrc() + ">>. Exception : ",e);                 
            throw new RuntimeException(e);              
        }
        return (demandeCheque);

    }

    public Long getNumDemandeCheque(Long Strc) {

        Context context = ContextHandler.getContext();
        CRUDservice crudService = (CRUDservice)context.getBean("crudservice");
        ISearchEngine searchEngine=(ISearchEngine)Context.getInstance().getSpringContext().getBean("searchEngine");

        /* Rechercher la sequence N° demande cheque relative à la structure donnée */

        SeqAgenceId seqAgenceId = new SeqAgenceId();
        seqAgenceId.setLibSeqSeqa("SEQ_NUM_DEM_DCHQ");
        seqAgenceId.setCodStrcStrc(Strc);

        SeqAgence seqAgence = 
            (SeqAgence)searchEngine.get(SeqAgence.class, seqAgenceId);

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
        DemandeCheque demandeCheque = (DemandeCheque)vo;
        
      return (demandeCheque.getTache().getTacheId().getCodOperOper().toString() + 
              StrHandler.lpad(demandeCheque.getTache().getTacheId().getCodTachTach().toString(),'0',2));    
    }
    
    public IValueObject getNumeroDomaine(IValueObject vo){
        StructureDomaine structureDomaine = new StructureDomaine();
        DemandeCheque demandeCheque = (DemandeCheque)vo;
        structureDomaine.setCodDomDomm(Constants.COD_DOM_CONTRATCOMPTE);
        if(demandeCheque.getContratCpt()!= null)
          structureDomaine.setCodStrcStrc(demandeCheque.getContratCpt().getContratCptId().getCodStrcStrc());
        else if(demandeCheque.getCompteInterne()!= null)
          structureDomaine.setCodStrcStrc(demandeCheque.getCompteInterne().getCompteInterneId().getCodStrcStrc());
        return structureDomaine;
    }
    
    
    public void genererSynchronisationPascal(ValueObject vo) {
    
        DemandeCheque demandeCheque = (DemandeCheque)vo; 
        DateFormat myformat1 = new SimpleDateFormat("yyMMdd");
        DateFormat myformat2 = new SimpleDateFormat("ddMMyyyy");
             
        this.setCodeOperationSynch(Long.valueOf(9));
        this.setCodeTacheSynch(demandeCheque.getTache().getTacheId().getCodTachTach());
        this.setDateOperationSynch(new Date());
        this.setCodeStructureSynch(demandeCheque.getCompteInterne().getCompteInterneId().getCodStrcStrc());
        
        String numCompte = StrHandler.lpad(demandeCheque.getCompteInterne().getCompteInterneId().getCodPrdPrd().toString(),'0',4) +
                           StrHandler.lpad(demandeCheque.getCompteInterne().getCompteInterneId().getNumCptiCpti().toString(),'0',6);
         
        String dateDemande = myformat1.format(demandeCheque.getDatDemDchq());
        String dateValidation = "      ";
        if(demandeCheque.getDatEnvDchq() != null )            
            dateValidation =  myformat1.format(demandeCheque.getDatEnvDchq());
        
        String nbreCarnet = StrHandler.lpad(demandeCheque.getNbrChqiDchq().toString(),'0',2);
        
        String typeCarnet = "A";        
                        
        String typeCheque = "0" ;
        
        Structure structure = new Structure();
        structure.setCodStrcStrc(this.getCodeStructureSynch());
        GetStructureCmd getStructureCmd=new GetStructureCmd();
        structure=(Structure)getStructureCmd.execute(structure);
        
        String nomDemandeur = "                                        ";            
        nomDemandeur = StrHandler.rpad(structure.getLibStrcStrc(),' ',40);
        
       String adresse = "                                                            ";
             
       String codePostal = "     ";
       
       if(structure.getAdrImmStrc()!= null)
          adresse = StrHandler.rpad(structure.getAdrImmStrc(),' ',60); 
        
        
        if(structure.getCodePostal().getCodCpCp() != null)
         codePostal = StrHandler.lpad(structure.getCodePostal().getCodCpCp().toString(),'0',5); 
       
        
        String partieVariable =  numCompte + nbreCarnet + typeCarnet + nomDemandeur +  adresse + codePostal + "  ";
            
        this.setTextSynch(partieVariable);
        
        }

}
