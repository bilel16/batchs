package com.bna.smile.model.domainecontratcompte.modificationdonneesclient.traitement;

import java.util.Date;
import java.util.Iterator;

import com.bna.commun.model.ModificationDonnees;
import com.bna.commun.model.ModificationDonneesId;
import com.bna.commun.model.PersClient;
import com.bna.commun.model.Personne;
import com.bna.commun.model.Personnel;
import com.bna.commun.model.StructureDomaine;
import com.bna.commun.traitements.Traitement;
import com.bna.commun.util.ContextHandler;
import com.bna.smile.model.constant.Constants;
import com.bna.smile.model.domainecommun.dao.SequenceDAO;
import com.bna.smile.model.domainecommun.model.ParamListPersonneQualiteClientVo;
import com.bna.smile.model.domainecommun.service.CRUDservice;
import com.bna.smile.model.domainecommun.traitement.GetPersonneByNumSeqPersTrt;
import com.bna.smile.model.domainecommun.traitement.GetPersonneClientQualiteTrt;
import com.bna.smile.model.domainecommun.traitement.GetPersonnelTrt;
import com.bna.smile.model.domainecontratcompte.modificationdonneesclient.model.ParamModificationQualitePersClientVo;
import com.oxia.fwk.context.Context;
import com.oxia.fwk.core.IValueObject;
import com.oxia.fwk.core.ValueObject;

public class ModifierQualitePersonneTrt extends Traitement {
   

    public ModifierQualitePersonneTrt() {
    }
    
   public ValueObject perform (IValueObject vo){
   
        Context context = ContextHandler.getContext();
   //------------------------------------------------------------------
   //-------------- Extraire la liste de la Base ----------------------
   //------------------------------------------------------------------
    ParamModificationQualitePersClientVo paramVo = (ParamModificationQualitePersClientVo) vo;
    GetPersonneClientQualiteTrt   getPersonneClientQualiteTrt = new GetPersonneClientQualiteTrt();
    ParamListPersonneQualiteClientVo paramBase = new ParamListPersonneQualiteClientVo();
    
    paramBase.setCodQualQual(paramVo.getCodQualQual());
    paramBase.setNumSeqPers(paramVo.getNumSeqPers());
    
    paramBase =(ParamListPersonneQualiteClientVo) getPersonneClientQualiteTrt.exec(paramBase);
       CRUDservice crudService = 
           (CRUDservice)context.getBean("crudservice");
    try{ 
     if(this.checkClotureJournee()){
    //------------------------------------------------------------------
    //----------- verifier s'il n'y a pas eu de suppression ------------
    //----------- des personnes de la relation qualite      ------------
    // if (paramBase.getListePersonneClient() != null && paramBase.getListePersonneClient().size() >0 ){
      for(Iterator itModif = paramVo.getListePersonneClient().iterator(); itModif.hasNext(); ){
        PersClient persClientModif = (PersClient) itModif.next();  
        boolean testExistance= false;
            for(Iterator itBase = paramBase.getListePersonneClient().iterator(); itBase.hasNext(); ){
            PersClient persClientBase = (PersClient) itBase.next();
            //------------ Comparer si la relation existe  ---------------------------------//
           
            if (persClientModif.getPersClientId().equals(persClientBase.getPersClientId())){
                testExistance = true;
                //---------- Verifier s'il y a eu des modif sur les données ---------//
                if ((!persClientModif.getLibFoncPecl().equals(persClientBase.getLibFoncPecl())) ||(!persClientModif.getTauxPartPecl().equals(persClientBase.getTauxPartPecl()))) {
                    StringBuffer texteModification = new StringBuffer("");
                    ModificationDonnees modificationDonnees = 
                        new ModificationDonnees();
                        Personne personne = new Personne();
                    texteModification.append(" Mise à jour des données : ");
                    texteModification.append(" # Client : Type Pièce : ");
                    GetPersonneByNumSeqPersTrt getPersonneByNumSeqPersTrt = new GetPersonneByNumSeqPersTrt();
                    
                    personne.setNumSeqPers(persClientBase.getPersClientId().getNumSeqPers());
                    personne = (Personne)getPersonneByNumSeqPersTrt.exec(personne);
                    texteModification.append(personne.getTypePiece().getLibSiglTpce());
                    texteModification.append(" Num Piece :");
                    texteModification.append(personne.getNumPcePers());
                    personne.setNumSeqPers(persClientBase.getPersClientId().getNumSeqPers());
                    
                    personne = (Personne)getPersonneByNumSeqPersTrt.exec(personne);
                    texteModification.append(" # Personne : Type Pièce : ");
                    texteModification.append(personne.getTypePiece().getCodTpceTpce());
                    
                    texteModification.append(" Num Piece : ");
                    texteModification.append(personne.getNumPcePers());
                    texteModification.append(" # Fonction : ");
                    texteModification.append(persClientBase.getLibFoncPecl() );
                    texteModification.append(" # taux : ");
                    texteModification.append(persClientBase.getTauxPartPecl());
                    
                    creationModification(paramVo, modificationDonnees, texteModification.toString(),crudService);

                    UpdatePersClientTrt updatePersClientTrt = new UpdatePersClientTrt();
                    persClientModif = (PersClient)updatePersClientTrt.exec(persClientModif);
                    
                    if (persClientModif.hasError() ){
                        paramVo.addError(persClientModif.getErrors().get(0));
                        return (paramVo);
                    }
                }// sinon rien faire
                
            }
         }// fin for liste de la base
          //------- Creation d'une nouvelle relation en cas de son inexisatnce---//
         if (testExistance == false){
             StringBuffer texteModification = new StringBuffer("");
             ModificationDonnees modificationDonnees = 
                 new ModificationDonnees();
                 Personne  personneMorale = new Personne();
                 
             texteModification.append(" Création d'une relation : ");
             texteModification.append(" # Client : Type Pièce : ");
             GetPersonneByNumSeqPersTrt getPersonneByNumSeqPersTrt = new GetPersonneByNumSeqPersTrt();
             personneMorale.setNumSeqPers(persClientModif.getPersClientId().getNumSeqCli());
             personneMorale = (Personne)getPersonneByNumSeqPersTrt.exec(personneMorale);
             
             texteModification.append(personneMorale.getTypePiece().getLibSiglTpce());
             texteModification.append(" Num Piece :");
             texteModification.append(personneMorale.getNumPcePers());
             
             Personne  personnePhysique = new Personne();
             personnePhysique.setNumSeqPers(persClientModif.getPersClientId().getNumSeqPers());
             personnePhysique = (Personne)getPersonneByNumSeqPersTrt.exec(personnePhysique);
             texteModification.append(" # Personne : Type Pièce : ");
             texteModification.append(personnePhysique.getTypePiece().getLibSiglTpce());
             texteModification.append("  Num Piece : ");
             texteModification.append(personnePhysique.getNumPcePers());
             
             texteModification.append(" # Fonction : ");
             texteModification.append(persClientModif.getLibFoncPecl() );
             texteModification.append(" #taux : ");
             texteModification.append(persClientModif.getTauxPartPecl());
             creationModification(paramVo, modificationDonnees, texteModification.toString(),crudService);
             
               InsertPersClientTrt insertPersClientTrt = new InsertPersClientTrt();
               persClientModif = (PersClient)insertPersClientTrt.exec (persClientModif);
             if (persClientModif.hasError() ){
                 paramVo.addError(persClientModif.getErrors().get(0));
                 return (paramVo);
             }
         }
       }// fin for nouvelle liste
       
    //----------------------------------------------------------------------------//
    //----------------------- Detecter les suppressions --------------------------//
    if(paramBase.getListePersonneClient()!=null && paramBase.getListePersonneClient().size()>0 ){
       for(Iterator itBase = paramBase.getListePersonneClient().iterator(); itBase.hasNext(); ){
        PersClient persClientBase = (PersClient) itBase.next();
        boolean testExistance = false;
           for(Iterator itModif = paramVo.getListePersonneClient().iterator(); itModif.hasNext(); ){
           PersClient persClientModif = (PersClient) itModif.next();  

              //------------ Comparer si la relation existe encore ---------------------------------//
                if (persClientModif.getPersClientId().equals(persClientBase.getPersClientId())){
                   testExistance = true;
                }    
           }// fin for de la nouvelle liste
           
           //----------------- La relation n'existe plus -------------------//
           if (testExistance == false){
           
               StringBuffer texteModification = new StringBuffer("");
               ModificationDonnees modificationDonnees = new ModificationDonnees();
               Personne  personne = new Personne();
               texteModification.append(" Suppression d'une relation : ");
               texteModification.append(" # Client : Type Pièce : ");
              
               GetPersonneByNumSeqPersTrt getPersonneByNumSeqPersTrt = new GetPersonneByNumSeqPersTrt();
               personne.setNumSeqPers(persClientBase.getPersClientId().getNumSeqCli());
               personne = (Personne)getPersonneByNumSeqPersTrt.exec(personne);
             
              texteModification.append(personne.getTypePiece().getLibSiglTpce());
              
               texteModification.append(" Num Piece :");
               texteModification.append(personne.getNumPcePers());
               
               Personne  personneA = new Personne();
               personneA.setNumSeqPers(persClientBase.getPersClientId().getNumSeqPers()) ;
               personneA = (Personne)getPersonneByNumSeqPersTrt.exec(personne);
               
               texteModification.append(" # Personne : Type Pièce : ");
               texteModification.append(personne.getTypePiece().getLibSiglTpce());
              
               texteModification.append(" Num Piece : ");
               texteModification.append(personne.getNumPcePers());
               texteModification.append(" # Fonction : ");
               texteModification.append(persClientBase.getLibFoncPecl() );
               texteModification.append(" # taux : ");
               texteModification.append(persClientBase.getTauxPartPecl());
               
               creationModification(paramVo, modificationDonnees, texteModification.toString(),crudService);
               
               DeletePersClientTrt deletePersClientTrt = new DeletePersClientTrt() ;
               persClientBase = (PersClient) deletePersClientTrt.exec(persClientBase);
              
               if (persClientBase.hasError() ){
                   paramVo.addError(persClientBase.getErrors().get(0));
                   return (paramVo);
               }
              
               
           }
       }// fin for liste de la liste de Base
     }
        
   //--------------------------- ------- Fin de suppression -----------------------------------------// 
    return (paramVo); 
    
    // Fin controle fin de journee
    }else{
                  com.oxia.fwk.core.Error erreur = new com.oxia.fwk.core.Error();
                  StringBuffer text = new StringBuffer("La journée est déja clôturée...");            
                  erreur.setCode("100");
                  erreur.setDescription(text.toString());
                  erreur.setKey("ModifierDonneesClientTrt");
                  paramVo.addError(erreur);    
                  return (paramVo);
     }     
    }catch(Exception e) {
        com.oxia.fwk.core.Error erreur = new com.oxia.fwk.core.Error();
        StringBuffer text = 
            new StringBuffer("Erreur dans ModifierQualitePersonneTrt : ");
        text.append(e.toString());
        erreur.setCode("200");
        erreur.setDescription(text.toString());
        erreur.setKey("ModifierDonnesClient");

        paramVo.addError(erreur);
        return (paramVo);  
    }
    
   }

    private void creationModification (ParamModificationQualitePersClientVo paramVo, 
                                      ModificationDonnees modificationDonnees,String textModification,  CRUDservice crudService  ) {
        Context context = ContextHandler.getContext();
        Personne personnexx = new Personne();
        GetPersonneByNumSeqPersTrt getPersonneByNumSeqPersTrt = new GetPersonneByNumSeqPersTrt();
        
        personnexx.setNumSeqPers(Long.valueOf(paramVo.getNumSeqPers()));
        personnexx = (Personne) getPersonneByNumSeqPersTrt.exec(personnexx);
        
        modificationDonnees.setTypeModification(paramVo.getTypeModification());
        modificationDonnees.setPersonne(personnexx);
       
       GetPersonnelTrt getPersonnelTrt = new GetPersonnelTrt();
       Personnel personnel = new Personnel();
       personnel.setNumMatrUser(paramVo.getMatriculeUser());
       personnel = (Personnel)getPersonnelTrt.exec(personnel);
       
       modificationDonnees.setPersonnel(personnel);
       modificationDonnees.setStructure(personnel.getStructure());
       
       ModificationDonneesId modificationDonneesId =  new ModificationDonneesId();
       modificationDonneesId.setDatModModd(new Date());
       SequenceDAO sequenceDao =   (SequenceDAO)context.getBean("sequenceDAO");
       modificationDonneesId.setNumModModd(sequenceDao.getSequenceModificationDonnees());
       modificationDonnees.setModificationDonneesId(modificationDonneesId);
       modificationDonnees.setLibModModd(textModification);
      
       crudService.create(modificationDonnees);
       
    }
    
    public void genCroText (ValueObject vo){
    
    }
 
    public String  getNumeroTache (IValueObject vo) {   
        ParamModificationQualitePersClientVo paramVo = (ParamModificationQualitePersClientVo) vo;
        if (paramVo.getCodQualQual().equals(Constants.COD_QUAL_ACTIONNAIRE)){
            return "39004";
            
        } else if (paramVo.getCodQualQual().equals(Constants.COD_QUAL_ACTIONNAIRE)){
            return "39005";
        }
        return "39004";
    }
    public IValueObject getNumeroDomaine(IValueObject vo){
        StructureDomaine structureDomaine = new StructureDomaine();
        ParamModificationQualitePersClientVo paramVo = (ParamModificationQualitePersClientVo) vo;
        GetPersonnelTrt getPersonnelTrt = new GetPersonnelTrt();
        Personnel personnel = new Personnel();
        personnel.setNumMatrUser(paramVo.getMatriculeUser());
        personnel = (Personnel)getPersonnelTrt.exec(personnel);
        structureDomaine.setCodDomDomm(Constants.COD_DOM_CLIENT);
        structureDomaine.setCodStrcStrc(personnel.getStructure().getCodStrcStrc());
        return structureDomaine;
    }
}
