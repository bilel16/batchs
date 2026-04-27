package com.bna.smile.model.domainecontratcompte.modificationdonneesclient.traitement;

import java.util.Date;

import org.apache.log4j.Logger;

import com.bna.commun.model.ContratCpt;
import com.bna.commun.model.ModificationDonnees;
import com.bna.commun.model.ModificationDonneesId;
import com.bna.commun.model.Personne;
import com.bna.commun.model.Personnel;
import com.bna.commun.model.Structure;
import com.bna.commun.model.StructureDomaine;
import com.bna.commun.model.TypeModification;
import com.bna.commun.traitements.Traitement;
import com.bna.commun.util.ContextHandler;
import com.bna.commun.util.StrHandler;
import com.bna.smile.model.constant.Constants;
import com.bna.smile.model.domainecommun.commande.GetContratCptByIdCmd;
import com.bna.smile.model.domainecommun.dao.SequenceDAO;
import com.bna.smile.model.domainecommun.service.CRUDservice;
import com.bna.smile.model.domainecommun.traitement.GetPersonnelTrt;
import com.bna.smile.model.domainecontratcompte.modificationdonneesclient.model.ParamModificationIntituleCompteVo;
import com.oxia.fwk.context.Context;
import com.oxia.fwk.core.IValueObject;
import com.oxia.fwk.core.ValueObject;

/**
 * Classe qui permet de faire les corrections des données client
 * @author Mdimagh
 * @since 23/04/2008
 */
public class ModificationIntituleCompteTrt extends Traitement {
    
    Logger logger = Logger.getLogger(ModifierDonneesClientTrt.class);
       
        public ModificationIntituleCompteTrt() {
        }
       

    public IValueObject perform (IValueObject vo) {
        Context context = ContextHandler.getContext();
        ParamModificationIntituleCompteVo ParamModificationVo = 
            (ParamModificationIntituleCompteVo)vo;
       
        try {
            logger.info("Structure: "+ ParamModificationVo.getCodeStructure().toString()+" Matricule: "+ParamModificationVo.getMatricule().toString()+" Evénement : entrée Trt");

          if(this.checkClotureJournee()){
            StringBuffer texteModification = new StringBuffer("");
            
          
            
            //---------------------------------------------------------------------//
            //---------- Preparer la table Modification ---------------//
            //---------------------------------------------------------------------//

            ModificationDonnees modificationDonnees = 
                new ModificationDonnees();
            TypeModification typeModification = new TypeModification();
            typeModification.setCodCodModf(Long.valueOf(25));
            
            modificationDonnees.setTypeModification(typeModification);
           
            GetPersonnelTrt getPersonnelTrt = new GetPersonnelTrt();
            Personnel personnel = new Personnel();
            personnel.setNumMatrUser(ParamModificationVo.getMatricule().toString());
            personnel = (Personnel)getPersonnelTrt.exec(personnel);
            modificationDonnees.setPersonnel(personnel);
            
            Structure structure = new Structure();
            structure.setCodStrcStrc(Long.valueOf(ParamModificationVo.getCodeStructure()));
            modificationDonnees.setStructure(structure);

            ModificationDonneesId modificationDonneesId = 
                new ModificationDonneesId();
            modificationDonneesId.setDatModModd(new Date());

            //--------------------- Fin Preparation ---------------------------------//
            
            //-------------------------------------------------------------//
            //------------- sauvgarde des données anciennes  --------------//
            //-------------------------------------------------------------//
    
             GetContratCptByIdCmd getContratCpt = new GetContratCptByIdCmd();
             ContratCpt contrat = new ContratCpt();
             contrat.setContratCptId(ParamModificationVo.getContratCpt().getContratCptId());
             contrat = (ContratCpt) getContratCpt.execute(contrat);
    
    
            if (contrat != null ){
                   texteModification.append("# Numéro de compte : ");
                   texteModification.append(contrat.getContratCptId().getCodStrcStrc());
                   texteModification.append(contrat.getContratCptId().getCodPrdPrd() );
                   texteModification.append(contrat.getContratCptId().getNumCcptCcpt() );
                   texteModification.append(" #  Intitulé du compte : ");
                   if(contrat.getNomIntiCcpt()  !=null){
                     texteModification.append(contrat.getNomIntiCcpt());
                   }
                   contrat.setNomIntiCcpt(ParamModificationVo.getContratCpt().getNomIntiCcpt());
             }  
            
             
         SequenceDAO sequenceDao = 
             (SequenceDAO)context.getBean("sequenceDAO");
         modificationDonneesId.setNumModModd(sequenceDao.getSequenceModificationDonnees());
         modificationDonnees.setModificationDonneesId(modificationDonneesId);

         if (texteModification.length()>999){
           modificationDonnees.setLibModModd(texteModification.toString().substring(0,998));
         }else{
           modificationDonnees.setLibModModd(texteModification.toString());   
         }
       
         Personne p = new Personne();
         p.setNumSeqPers(contrat.getClient().getPersonne().getNumSeqPers());
         modificationDonnees.setPersonne(p);
       
         CRUDservice crudService = 
             (CRUDservice)context.getBean("crudservice");
            crudService.update(contrat);  
            crudService.create(modificationDonnees);
            this.sychronisationPascal(ParamModificationVo); 
            
           
            logger.info("Structure: "+ ParamModificationVo.getCodeStructure().toString()+" Matricule: "+ParamModificationVo.getMatricule().toString()+" Evénement : Sortie Trt normale");            
        return ParamModificationVo;
        
        }else{
          com.oxia.fwk.core.Error erreur = new com.oxia.fwk.core.Error();
          StringBuffer text = new StringBuffer("La journée est déja clôturée...");            
          erreur.setCode("100");
          erreur.setDescription(text.toString());
          erreur.setKey("ModifierDonneesClientTrt");
          ParamModificationVo.addError(erreur); 
          

          return (ParamModificationVo);
        }     
    } catch (Exception e) {
        System.out.println(e.toString());
        com.oxia.fwk.core.Error erreur = new com.oxia.fwk.core.Error();
        StringBuffer text =
          new StringBuffer("Erreur dans ModificationIntituleCompteTrt : ");
        text.append(e.toString());
        text.append(e.getCause().getCause().getMessage())    ;
        erreur.setCode("200");
        erreur.setDescription(text.toString());
        erreur.setKey("ModifierDonnesClient");
        logger.info("Structure: "+ ParamModificationVo.getCodeStructure().toString()+" Matricule: "+ParamModificationVo.getMatricule().toString()+" Exception : " +  e.toString());
        
        ParamModificationVo.addError(erreur);
        return (ParamModificationVo);
    }
 }

 
    public void genCroText(ValueObject vo){
        
    
    }
    
    public String  getNumeroTache (IValueObject vo) {
        return  Constants.RESS_MODIF_INTITULE_CPT ;
    }
    
    public void genererSynchronisationPascal(ValueObject vo) {   
        
       ParamModificationIntituleCompteVo paramModificationDonneesVo = (ParamModificationIntituleCompteVo)vo;
       logger.debug("Structure: "+ paramModificationDonneesVo.getCodeStructure().toString()+" Matricule: "+paramModificationDonneesVo.getMatricule().toString()+" Entrée : genererSynchronisationPascal");
        
       StringBuffer partieVariable =new StringBuffer();
       
       this.setDateOperationSynch(new Date());
      
       this.setCodeStructureSynch(paramModificationDonneesVo.getContratCpt().getContratCptId().getCodStrcStrc());
            
       this.setCodeOperationSynch(Constants.COD_OPER_MODIF_NOM);
       this.setCodeTacheSynch(Constants.COD_TACHE_MODIF_NOM);
           
           //-- Données du contrat 
        if (paramModificationDonneesVo.getContratCpt()!= null && paramModificationDonneesVo.getContratCpt().getContratCptId() != null){
          partieVariable.append(StrHandler.lpad(paramModificationDonneesVo.getContratCpt().getContratCptId().getCodPrdPrd().toString(),'0',4));
          partieVariable.append(StrHandler.lpad(paramModificationDonneesVo.getContratCpt().getContratCptId().getNumCcptCcpt().toString(),'0',6));
        }
         
          String intitule = new String();
     
         if(paramModificationDonneesVo.getContratCpt().getNomIntiCcpt().length()>40){
            intitule = paramModificationDonneesVo.getContratCpt().getNomIntiCcpt().substring(0,39);
         }else {
           intitule = paramModificationDonneesVo.getContratCpt().getNomIntiCcpt();
         }
      
        partieVariable.append(StrHandler.rpad(intitule ,' ',40));
        partieVariable.append("T           ");
       
        logger.debug("Structure: "+ paramModificationDonneesVo.getCodeStructure().toString()+" Matricule: "+paramModificationDonneesVo.getMatricule().toString()+" Sortie : genererSynchronisationPascal le texte est : "+partieVariable.toString()+" de longeur "+partieVariable.length()) ;
        
        this.setTextSynch(partieVariable.toString());
            
           
    }
    public IValueObject getNumeroDomaine(IValueObject vo){
        StructureDomaine structureDomaine = new StructureDomaine();
        ParamModificationIntituleCompteVo ParamModificationVo = 
            (ParamModificationIntituleCompteVo)vo;
        structureDomaine.setCodDomDomm(Constants.COD_DOM_CLIENT);
        structureDomaine.setCodStrcStrc(ParamModificationVo.getCodeStructure());
        return structureDomaine;
    }    
    
}  
