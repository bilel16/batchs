package com.bna.smile.model.domainecontratcompte.modificationdonneesclient.traitement;

import java.util.Date;

import org.apache.log4j.Logger;

import com.bna.commun.model.ModificationDonnees;
import com.bna.commun.model.ModificationDonneesId;
import com.bna.commun.model.Pays;
import com.bna.commun.model.Personne;
import com.bna.commun.model.Personnel;
import com.bna.commun.model.Structure;
import com.bna.commun.model.StructureDomaine;
import com.bna.commun.model.TypeModification;
import com.bna.commun.traitements.Traitement;
import com.bna.commun.util.ContextHandler;
import com.bna.commun.util.DateHandler;
import com.bna.smile.model.constant.Constants;
import com.bna.smile.model.domainecommun.dao.SequenceDAO;
import com.bna.smile.model.domainecommun.service.CRUDservice;
import com.bna.smile.model.domainecommun.traitement.GetPaysTrt;
import com.bna.smile.model.domainecommun.traitement.GetPersonneByNumSeqPersTrt;
import com.bna.smile.model.domainecommun.traitement.GetPersonnelTrt;
import com.bna.smile.model.domainecontratcompte.modificationdonneesclient.model.ParamCorrectionDonneesClientVo;
import com.oxia.fwk.context.Context;
import com.oxia.fwk.core.IValueObject;
import com.oxia.fwk.core.ValueObject;

/**
 * Classe qui permet de faire les corrections des données client
 * @author Mdimagh
 * @since 23/04/2008
 */
public class CorrectionDonneesClientTrt extends Traitement {
    
    Logger logger = Logger.getLogger(ModifierDonneesClientTrt.class);
       
        public CorrectionDonneesClientTrt() {
        }
       

    public IValueObject perform(IValueObject vo) {
    
        Context context = ContextHandler.getContext();
        ParamCorrectionDonneesClientVo ParamCorrectionVo = 
            (ParamCorrectionDonneesClientVo)vo;
       
        //try {
            logger.info("Structure: "+ ParamCorrectionVo.getCodeStructure().toString()+" Matricule: "+ParamCorrectionVo.getMatricule().toString()+" Evénement : entrée Trt");

          if(this.checkClotureJournee()){
            StringBuffer texteModification = new StringBuffer("");
            
            //--------------------------------------------------------------------------//
            //-------- Recuperer l'objet personne de la base ---------//
            //-------------------------------------------------------------------------//
            
            GetPersonneByNumSeqPersTrt getPersonneByNumSeqPersTrt = 
                new GetPersonneByNumSeqPersTrt();
            getPersonneByNumSeqPersTrt.setSecurityFlag(false);
            Personne personneBase = 
                (Personne)getPersonneByNumSeqPersTrt.exec(ParamCorrectionVo.getPersonneModifie());
           
            
            //---------------------------------------------------------------------//
            //---------- Preparer la table Modification ---------------//
            //---------------------------------------------------------------------//

            ModificationDonnees modificationDonnees = 
                new ModificationDonnees();
            TypeModification typeModification = new TypeModification();
            typeModification.setCodCodModf(Long.valueOf(99));
            
            modificationDonnees.setTypeModification(typeModification);
           
            GetPersonnelTrt getPersonnelTrt = new GetPersonnelTrt();
            Personnel personnel = new Personnel();
            personnel.setNumMatrUser(ParamCorrectionVo.getMatricule().toString());
            personnel = (Personnel)getPersonnelTrt.exec(personnel);
            modificationDonnees.setPersonnel(personnel);
            Structure structure = new Structure();
            structure.setCodStrcStrc(Long.valueOf(ParamCorrectionVo.getCodeStructure()));
            modificationDonnees.setStructure(structure);

            ModificationDonneesId modificationDonneesId = 
                new ModificationDonneesId();
            modificationDonneesId.setDatModModd(new Date());

            //--------------------- Fin Preparation ---------------------------------//
            
            //-------------------------------------------------------------//
            //------------- sauvgarde des données anciennes  --------------//
            //-------------------------------------------------------------//
    
            if (personneBase.getCategoriePersonne().getTypePers().getCodTperTper().equals(Constants.PERSPHYSIQUE)){
                texteModification = 
                        texteModification.append("# Nom : ");
                        if(personneBase.getNomNomPers() != null){
                texteModification = 
                        texteModification.append(personneBase.getNomNomPers() );
                        }
                texteModification = 
                        texteModification.append(" #  Prénom : ");
                        if(personneBase.getNomPrnPers() !=null){
                texteModification = 
                        texteModification.append(personneBase.getNomPrnPers());
                        }
                texteModification = 
                        texteModification.append(" #  Prénom du père : ");
                        if(personneBase.getNomPrnpPers() != null){
                texteModification = 
                        texteModification.append(personneBase.getNomPrnpPers());
                        }
            }else{
                texteModification = 
                        texteModification.append("# Raison sociale : ");
                if(personneBase.getNomRsPers() != null){
                texteModification = 
                                        texteModification.append(personneBase.getNomRsPers());
                }
                
            }
                texteModification.append(" #  Date de délivrance : ");
                if (personneBase.getDatDlvPers() != null){
                    texteModification.append(DateHandler.dateToStr(personneBase.getDatDlvPers()));
                }
                
                texteModification.append(" #  Lieu de délivrance : ");
                if (personneBase.getGouvernorat()!=null && personneBase.getGouvernorat().getLibGouvGouv()!=null ){
                    texteModification.append(personneBase.getGouvernorat().getLibGouvGouv());
                }
              
              if (personneBase.getCategoriePersonne().getTypePers().getCodTperTper().equals(Constants.PERSPHYSIQUE)){
                texteModification.append(" #  Profession : ");
                if (personneBase.getProfession() !=null && personneBase.getProfession().getLibProfProf() != null ){
                    texteModification.append(personneBase.getProfession().getLibProfProf());
                }
              }   
                texteModification.append(" #  Activite : ");
                if (personneBase.getActivite() !=null && personneBase.getActivite().getLibActAct() !=null){
                    texteModification.append(personneBase.getActivite().getLibActAct() );
                }
                
                texteModification.append(" #  Nationalité : ");
                if (personneBase.getPaysByCodNat1Pays()  !=null && personneBase.getPaysByCodNat1Pays().getLibPaysPays() !=null ){
                   texteModification.append(personneBase.getPaysByCodNat1Pays().getLibPaysPays());
                }
                
                texteModification.append(" #  Résidence : ");
                if (personneBase.getBoolResPers() !=null ){
                    if (personneBase.getBoolResPers().equals(Long.valueOf("1"))){
                     texteModification.append("Résident");
                    }else{
                     texteModification.append("Non résident");
                    }
                }
             
             if (personneBase.getCategoriePersonne().getTypePers().getCodTperTper().equals(Constants.PERSPHYSIQUE)){
                
                texteModification.append(" #  Pays de naissance : ");
                if (personneBase.getPaysByCodNaisPays()  !=null && personneBase.getPaysByCodNaisPays().getLibPaysPays()!=null ){
                   texteModification.append(personneBase.getPaysByCodNaisPays().getLibPaysPays());
                }
                
                texteModification.append(" #  Date de naissance : ");
                if (personneBase.getDatNaisPers()  !=null && personneBase.getDatNaisPers() != null ){
                   texteModification.append(personneBase.getDatNaisPers());
                }
                
                texteModification.append(" #  Lieu de naissance : ");
                if (personneBase.getLibNaisPers() !=null ){
                   texteModification.append(personneBase.getLibNaisPers());
                }
           
              }  
              
            texteModification.append(" #  Categorie  : ");
            if (personneBase.getCategoriePersonne() !=null && personneBase.getCategoriePersonne().getLibCatpCatp() != null ){
               texteModification.append(personneBase.getCategoriePersonne().getLibCatpCatp());
            }
            
            texteModification.append(" #  Forme Juridique  : ");
            if (personneBase.getFormeJuridique() !=null && personneBase.getFormeJuridique().getLibFjFj() != null ){
               texteModification.append(personneBase.getFormeJuridique().getLibFjFj()) ;
            }
            
            //--------------------------------------------------------------------------//
            //------ adresse-----------------------//
            GetPaysTrt getPaysTrt = new GetPaysTrt();
            Pays paysRes = new Pays();
            Pays paysProf = new Pays();
            
            texteModification = 
                    texteModification.append(" ** Adresse de résidence ** # Imm: ");
           
            if(personneBase.getAdresseResid() != null){
                if (personneBase.getAdresseResid().getImmeuble() != null){
                texteModification = 
                        texteModification.append(personneBase.getAdresseResid().getImmeuble());
                 }
                 
                texteModification = texteModification.append(" # Rue : ");
                if(personneBase.getAdresseResid().getRue() != null){
                texteModification = 
                        texteModification.append(personneBase.getAdresseResid().getRue());
                }
                texteModification = texteModification.append(" # Cite : ");
                if(personneBase.getAdresseResid().getCite() != null){
                texteModification = 
                        texteModification.append(personneBase.getAdresseResid().getCite());
                }
                texteModification = texteModification.append(" # Ville : ");
                if(personneBase.getAdresseResid().getVille() != null ){
                texteModification = 
                        texteModification.append(personneBase.getAdresseResid().getVille());
                }
                texteModification = texteModification.append(" # Pays : ");
    
                if(personneBase.getAdresseResid().getCodPaysPays() != null ){
                 paysRes.setCodPaysPays(personneBase.getAdresseResid().getCodPaysPays());
                 paysRes = (Pays)getPaysTrt.exec(paysRes);
                 texteModification.append(paysRes.getLibPaysPays());
                }
    
                texteModification = 
                        texteModification.append(" # Code Postal: ");
                if(personneBase.getAdresseResid().getCodCpCp() != null){
                 texteModification = 
                        texteModification.append(personneBase.getAdresseResid().getCodCpCp());
                }
            }//fin @de residence

            if (personneBase.getAdresseProf() != null) {

                texteModification = 
                        texteModification.append(" ** Adresse de professionnelle ** # Imm: ");
                if(personneBase.getAdresseProf().getImmeuble() != null){
                texteModification = 
                        texteModification.append(personneBase.getAdresseProf().getImmeuble());
                }
                texteModification = texteModification.append("# Rue : ");
                if(personneBase.getAdresseProf().getRue() != null){
                texteModification = 
                        texteModification.append(personneBase.getAdresseProf().getRue());
                }
                texteModification = texteModification.append(" # Cite : ");
                if(personneBase.getAdresseProf().getCite() !=null){
                texteModification = 
                        texteModification.append(personneBase.getAdresseProf().getCite());
                }
                texteModification = 
                        texteModification.append(" # Ville : ");
                if(personneBase.getAdresseProf().getVille() != null){
                texteModification = 
                        texteModification.append(personneBase.getAdresseProf().getVille());
                }
                texteModification = texteModification.append(" # Pays : ");
                
                if(personneBase.getAdresseProf().getCodPaysPays() != null){
                 paysProf.setCodPaysPays(personneBase.getAdresseProf().getCodPaysPays());
                 paysProf = (Pays)getPaysTrt.exec(paysProf);
                 texteModification.append(paysProf.getLibPaysPays());
                }
                texteModification = 
                        texteModification.append(" # Code Postal: ");
                if(personneBase.getAdresseProf().getCodCpCp() != null){
                 texteModification = 
                        texteModification.append(personneBase.getAdresseProf().getCodCpCp());
                }
            }
         
             //-----------------------------------------------------------------//
             //------------- Mettre à jour les nouvelles données  --------------//
             //-----------------------------------------------------------------//
             Personne pm = ParamCorrectionVo.getPersonneModifie();
             if (personneBase.getCategoriePersonne().getTypePers().getCodTperTper().equals(Constants.PERSPHYSIQUE)){
              personneBase.setNomNomPers(pm.getNomNomPers());
              personneBase.setNomPrnPers(pm.getNomPrnPers());
              personneBase.setNomPrnpPers(pm.getNomPrnpPers());
             }
              personneBase.setNomRsPers(pm.getNomRsPers());
             //--delivrance pièce
              personneBase.setDatDlvPers(pm.getDatDlvPers());
             
              if (pm.getGouvernorat() != null){
               personneBase.setGouvernorat(pm.getGouvernorat());
              }
             //-- activite
              personneBase.setActivite(pm.getActivite());
             //-- profession
             if (personneBase.getCategoriePersonne().getTypePers().getCodTperTper().equals(Constants.PERSPHYSIQUE)){
              personneBase.setProfession(pm.getProfession());
             }
             personneBase.setPaysByCodNat1Pays(pm.getPaysByCodNat1Pays());
             
            if (personneBase.getCategoriePersonne().getTypePers().getCodTperTper().equals(Constants.PERSPHYSIQUE)){
             personneBase.setPaysByCodNaisPays(pm.getPaysByCodNaisPays());
             personneBase.setDatNaisPers(pm.getDatNaisPers());
             personneBase.setLibNaisPers(pm.getLibNaisPers());
            }
            
             personneBase.setCategoriePersonne(pm.getCategoriePersonne());
             personneBase.setFormeJuridique(pm.getFormeJuridique());
             
             //---residence
             personneBase.setBoolResPers(pm.getBoolResPers());
              
             //-------Les adresses:
             personneBase.setAdresseResid(pm.getAdresseResid());
             personneBase.setAdresseProf(pm.getAdresseProf());
            
             
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
         p.setNumSeqPers(personneBase.getNumSeqPers());
         modificationDonnees.setPersonne(p);

         CRUDservice crudService = 
             (CRUDservice)context.getBean("crudservice");
            crudService.update(personneBase);  
            crudService.create(modificationDonnees);
            
            
            logger.info("Structure: "+ ParamCorrectionVo.getCodeStructure().toString()+" Matricule: "+ParamCorrectionVo.getMatricule().toString()+" Evénement : Sortie Trt normale");            
        return ParamCorrectionVo;
        
        }else{
          com.oxia.fwk.core.Error erreur = new com.oxia.fwk.core.Error();
          StringBuffer text = new StringBuffer("La journée est déja clôturée...");            
          erreur.setCode("100");
          erreur.setDescription(text.toString());
          erreur.setKey("ModifierDonneesClientTrt");
          ParamCorrectionVo.addError(erreur); 
          

          return (ParamCorrectionVo);
        }     
    /*} catch (Exception e) {
        System.out.println(e.toString());
        com.oxia.fwk.core.Error erreur = new com.oxia.fwk.core.Error();
        StringBuffer text =
          new StringBuffer("Erreur dans ModifierDonneesClientTrt : ");
        text.append(e.toString());
        text.append(e.getCause().getCause().getMessage())    ;
        erreur.setCode("200");
        erreur.setDescription(text.toString());
        erreur.setKey("ModifierDonnesClient");
        logger.info("Structure: "+ ParamCorrectionVo.getCodeStructure().toString()+" Matricule: "+ParamCorrectionVo.getMatricule().toString()+" Exception : " +  e.toString());
        
        ParamCorrectionVo.addError(erreur);
        return (ParamCorrectionVo);
    }*/
 }

 
    public void genCroText(ValueObject vo){
        
    
    }
    
    public String  getNumeroTache (IValueObject vo) {
        return "38302";
    }
    public IValueObject getNumeroDomaine(IValueObject vo){
        StructureDomaine structureDomaine = new StructureDomaine();
        ParamCorrectionDonneesClientVo ParamCorrectionVo = 
            (ParamCorrectionDonneesClientVo)vo;
        structureDomaine.setCodDomDomm(Constants.COD_DOM_CLIENT);
        structureDomaine.setCodStrcStrc(ParamCorrectionVo.getCodeStructure());
        return structureDomaine;
    }
}  
