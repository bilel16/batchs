package com.bna.smile.model.domainecontratcompte.modificationdonneesclient.traitement;

import java.util.Date;
import java.util.Iterator;

import org.apache.log4j.Logger;

import com.bna.commun.model.Activite;
import com.bna.commun.model.ActiviteId;
import com.bna.commun.model.CatSocProf;
import com.bna.commun.model.CategoriePersonne;
import com.bna.commun.model.Client;
import com.bna.commun.model.ContratCpt;
import com.bna.commun.model.Employeur;
import com.bna.commun.model.FormeJuridique;
import com.bna.commun.model.Gouvernorat;
import com.bna.commun.model.Groupe;
import com.bna.commun.model.ModificationDonnees;
import com.bna.commun.model.ModificationDonneesId;
import com.bna.commun.model.NiveauInstruction;
import com.bna.commun.model.Pays;
import com.bna.commun.model.Personne;
import com.bna.commun.model.Personnel;
import com.bna.commun.model.PieceAnnexe;
import com.bna.commun.model.PieceAnnexeId;
import com.bna.commun.model.Profession;
import com.bna.commun.model.ProfessionId;
import com.bna.commun.model.RegimeMatrimonial;
import com.bna.commun.model.Segment;
import com.bna.commun.model.StructureDomaine;
import com.bna.commun.model.TypePiece;
import com.bna.commun.traitements.Traitement;
import com.bna.commun.util.ContextHandler;
import com.bna.commun.util.DateHandler;
import com.bna.commun.util.StrHandler;
import com.bna.smile.model.constant.Constants;
import com.bna.smile.model.domainecommun.dao.SequenceDAO;
import com.bna.smile.model.domainecommun.model.Listes;
import com.bna.smile.model.domainecommun.model.PersonneCpt;
import com.bna.smile.model.domainecommun.model.PersonneStrc;
import com.bna.smile.model.domainecommun.service.CRUDservice;
import com.bna.smile.model.domainecommun.traitement.GetActiviteByIdTrt;
import com.bna.smile.model.domainecommun.traitement.GetCatSocProfTrt;
import com.bna.smile.model.domainecommun.traitement.GetContratCptByIdTrt;
import com.bna.smile.model.domainecommun.traitement.GetEmployeurByIdTrt;
import com.bna.smile.model.domainecommun.traitement.GetGouvernoratTrt;
import com.bna.smile.model.domainecommun.traitement.GetListContratMandataireTrt;
import com.bna.smile.model.domainecommun.traitement.GetNiveauInstructionTrt;
import com.bna.smile.model.domainecommun.traitement.GetPaysTrt;
import com.bna.smile.model.domainecommun.traitement.GetPersonneByNumSeqPersTrt;
import com.bna.smile.model.domainecommun.traitement.GetPersonneCptTrt;
import com.bna.smile.model.domainecommun.traitement.GetPersonnelTrt;
import com.bna.smile.model.domainecommun.traitement.GetPieceAnnexeByIdTrt;
import com.bna.smile.model.domainecommun.traitement.GetProfessionByIdTrt;
import com.bna.smile.model.domainecommun.traitement.GetRegimeMatrimonialTrt;
import com.bna.smile.model.domainecommun.traitement.GetSegmentTrt;
import com.bna.smile.model.domainecommun.traitement.GetTypePieceTrt;
import com.bna.smile.model.domainecontratcompte.modificationdonneesclient.model.ParamModificationDonneesVo;
import com.oxia.fwk.context.Context;
import com.oxia.fwk.core.IValueObject;
import com.oxia.fwk.core.ValueObject;


public class ModifierDonneesClientTrt extends Traitement {
   
    Logger logger = Logger.getLogger(ModifierDonneesClientTrt.class);

    public ModifierDonneesClientTrt() {
    }

    /**
     * Methode permettant la modification des données client
     * @param vo : ParamModificationDonneesVo
     * @return ParamModificationDonneesVo
     */
    public IValueObject perform(IValueObject vo) {
        Context context = ContextHandler.getContext();
        ParamModificationDonneesVo paramModificationDonneesVo = 
            (ParamModificationDonneesVo)vo;
        String testPieceAnnexe = "";
        try {
            logger.info("Structure: "+ paramModificationDonneesVo.getCodeStructure().toString()+" Matricule: "+paramModificationDonneesVo.getMatriculeUser().toString()+" Evénement : entrée Trt");

          if(this.checkClotureJournee()){
            StringBuffer texteModification = new StringBuffer("");
            //--------------------------------------------------------------------------//
            //-------- Recuperer l'objet personne de la base ---------//
            //-------------------------------------------------------------------------//
            GetPersonneByNumSeqPersTrt getPersonneByNumSeqPersTrt = 
                new GetPersonneByNumSeqPersTrt();
            getPersonneByNumSeqPersTrt.setSecurityFlag(false);
            Personne personneBase = 
                (Personne)getPersonneByNumSeqPersTrt.exec(paramModificationDonneesVo.getPersonneModifie());
            Client clientBase = new Client();
            PieceAnnexe anciennePieceAnnexe = new PieceAnnexe();
            PieceAnnexe nouvellePieceAnnexe = new PieceAnnexe();
            //---------------------------------------------------------------------//
            //---------- Preparer la table Modification ---------------//
            //---------------------------------------------------------------------//

            ModificationDonnees modificationDonnees = 
                new ModificationDonnees();

            modificationDonnees.setTypeModification(paramModificationDonneesVo.getTypeModification());
            modificationDonnees.setPersonne(paramModificationDonneesVo.getPersonneModifie());
            GetPersonnelTrt getPersonnelTrt = new GetPersonnelTrt();
            Personnel personnel = new Personnel();
            personnel.setNumMatrUser(paramModificationDonneesVo.getMatriculeUser());
            personnel = (Personnel)getPersonnelTrt.exec(personnel);
            modificationDonnees.setPersonnel(personnel);
            modificationDonnees.setStructure(personnel.getStructure());

            ModificationDonneesId modificationDonneesId = 
                new ModificationDonneesId();
            modificationDonneesId.setDatModModd(new Date());

            //--------------------- Fin Preparation ---------------------------------//
            
            
            
            //-------------------------------------------------------------------------//
            //------------- Detecter les modifications     ----------------//
            //------------------------------------------------------------------------//

            //-- Cas de changement de l'identifiant principal pass, CS --> CIN
            if (paramModificationDonneesVo.getTypeModification().getCodCodModf().equals(Long.valueOf(Constants.COD_MODIF_CHANGEMENT_ID))) {
                GetGouvernoratTrt getGouvernoratTrt = new GetGouvernoratTrt();
                Gouvernorat gouvernorat = new Gouvernorat();
                TypePiece ancTypePiece = personneBase.getTypePiece();
                GetTypePieceTrt getTypePieceTrt = new GetTypePieceTrt();
                ancTypePiece = 
                        (TypePiece)getTypePieceTrt.exec(ancTypePiece);

                texteModification = 
                        texteModification.append("# Type Piece : ");
                texteModification = 
                        texteModification.append(ancTypePiece.getLibSiglTpce());
                texteModification = 
                        texteModification.append(" #  Numéro Pièce : ");
                texteModification = 
                        texteModification.append(personneBase.getNumPcePers());

                texteModification.append(" #  Date de délivrance : ");
                texteModification.append(DateHandler.dateToStr(personneBase.getDatDlvPers()));


                TypePiece NouvTypePiece = 
                    (TypePiece)getTypePieceTrt.exec(paramModificationDonneesVo.getPersonneModifie().getTypePiece());
                personneBase.setTypePiece(NouvTypePiece);
                //--- si la piece est la CIN pour Personne Phyique
                if (paramModificationDonneesVo.getPersonneModifie().getTypePiece().getCodTpceTpce().equals(Long.valueOf(Constants.COD_CIN))) {
                    StrHandler.lpad(paramModificationDonneesVo.getPersonneModifie().getNumPcePers(), 
                                    '0', 8);
                }
                personneBase.setNumPcePers(paramModificationDonneesVo.getPersonneModifie().getNumPcePers());
                personneBase.setDatDlvPers(paramModificationDonneesVo.getPersonneModifie().getDatDlvPers());
                /// Gouvernorat 

                gouvernorat.setCodGouvGouv(paramModificationDonneesVo.getPersonneModifie().getGouvernorat().getCodGouvGouv());
                gouvernorat = 
                        (Gouvernorat)getGouvernoratTrt.exec(gouvernorat);
                personneBase.setGouvernorat(gouvernorat);
            }

            //-- Cas de modififcation de l'identifiant principal 
            if (paramModificationDonneesVo.getTypeModification().getCodCodModf().equals(Long.valueOf(Constants.COD_MODIF_IDENTIFIANT))) {
                GetGouvernoratTrt getGouvernoratTrt = new GetGouvernoratTrt();
                Gouvernorat gouvernorat = new Gouvernorat();
                Gouvernorat gouvernoratNew = new Gouvernorat();
               // System.out.println( "----------------- personne  ------------ " +  personneBase.getTypePiece().getCodTpceTpce() );
                TypePiece ancTypePiece = personneBase.getTypePiece();
                GetTypePieceTrt getTypePieceTrt = new GetTypePieceTrt();
                ancTypePiece = 
                        (TypePiece)getTypePieceTrt.exec(ancTypePiece);
               
                          
                texteModification = 
                        texteModification.append("# Type Piece : ");
                texteModification = 
                        texteModification.append(ancTypePiece.getLibSiglTpce());
                texteModification = 
                        texteModification.append(" #  Numéro Pièce : ");
                texteModification = 
                        texteModification.append(personneBase.getNumPcePers());

                texteModification.append(" #  Date de délivrance : ");
                texteModification.append(DateHandler.dateToStr(personneBase.getDatDlvPers()));
                texteModification.append(" #  Lieu de délivrance : ");
                        
                if (personneBase.getGouvernorat() != null) {
                    gouvernorat.setCodGouvGouv(personneBase.getGouvernorat().getCodGouvGouv());
                    gouvernorat = 
                            (Gouvernorat)getGouvernoratTrt.exec(gouvernorat);
                    texteModification.append(gouvernorat.getLibGouvGouv());
                }


                TypePiece NouvTypePiece = 
                    (TypePiece)getTypePieceTrt.exec(paramModificationDonneesVo.getPersonneModifie().getTypePiece());
                personneBase.setTypePiece(NouvTypePiece);
                //--- si la piece est la CIN pour Personne Phyique
                if (paramModificationDonneesVo.getPersonneModifie().getTypePiece().getCodTpceTpce().equals(Long.valueOf(Constants.COD_CIN))) {
                    StrHandler.lpad(paramModificationDonneesVo.getPersonneModifie().getNumPcePers(), 
                                    '0', 8);
                }
                personneBase.setNumPcePers(paramModificationDonneesVo.getPersonneModifie().getNumPcePers());
                personneBase.setDatDlvPers(paramModificationDonneesVo.getPersonneModifie().getDatDlvPers());
                /// Gouvernorat 

                gouvernoratNew.setCodGouvGouv(paramModificationDonneesVo.getPersonneModifie().getGouvernorat().getCodGouvGouv());
                gouvernoratNew =   (Gouvernorat) getGouvernoratTrt.exec( gouvernoratNew );
                
                               
                personneBase.setGouvernorat(gouvernoratNew);
            }
            //------------ Ajout d'une nouvelle Type Piece
            if (paramModificationDonneesVo.getTypeModification().getCodCodModf().equals(Long.valueOf(Constants.COD_MODIF_AJOUT_PIECE))) {
                texteModification.append("** Ajout d'une pièce  **");
                texteModification.append("# Type Piece : ");
                GetTypePieceTrt getTypePieceTrt = new GetTypePieceTrt();
                TypePiece tp = new TypePiece();
                tp.setCodTpceTpce(paramModificationDonneesVo.getNouvellePieceAnnexe().getPieceAnnexeId().getCodTpceTpce());
                getTypePieceTrt.setSecurityFlag(false);
                tp = (TypePiece) getTypePieceTrt.exec(tp);
                if (tp.getLibSiglTpce() != null){
                 texteModification.append(tp.getLibSiglTpce());
                }
                texteModification.append("# numéro : ");
                texteModification.append(paramModificationDonneesVo.getNouvellePieceAnnexe().getPieceAnnexeId().getNumPcePian());
                personneBase.getPieceAnnexes().add(paramModificationDonneesVo.getNouvellePieceAnnexe());
                
            }
            
            //-- Cas de modififcation de l'identifiant secondaire Piece Annexe
            if (paramModificationDonneesVo.getTypeModification().getCodCodModf().equals(Long.valueOf(Constants.COD_MODIF_IDENT_SEC))) {
                logger.info("Structure: "+ paramModificationDonneesVo.getCodeStructure().toString()+" Matricule: "+paramModificationDonneesVo.getMatriculeUser().toString()+" Evénement :Modification Piece Annexe");

                         // si le meme numero de piece
                         if (!paramModificationDonneesVo.getCodeTypePieceAnnexe().equals(Constants.COD_CIN)) {
                                 PieceAnnexeId pieceAnnexeId =new PieceAnnexeId();
                                 pieceAnnexeId.setCodTpceTpce(paramModificationDonneesVo.getCodeTypePieceAnnexe());
                                 pieceAnnexeId.setNumSeqPers(paramModificationDonneesVo.getNumSeqPesAnnexe());
                                 pieceAnnexeId.setNumPcePian(paramModificationDonneesVo.getNumeroPieceAnnexeAncien());
                                 anciennePieceAnnexe.setPieceAnnexeId(pieceAnnexeId);
                                 GetPieceAnnexeByIdTrt getPieceAnnexeTrt = new GetPieceAnnexeByIdTrt();
                                 anciennePieceAnnexe = (PieceAnnexe)getPieceAnnexeTrt.perform(anciennePieceAnnexe);
                              
                               if (paramModificationDonneesVo.getNumeroPieceAnnexeAncien().equals(paramModificationDonneesVo.getNumeroPieceAnnexenouvelle())) {
                                   
                                   testPieceAnnexe = "MAJ";
                                                             
                               } else {
                                   testPieceAnnexe = "AJOUT";
                                                          
                                   PieceAnnexeId pieceAnnexeIdnew =new PieceAnnexeId();
                                   pieceAnnexeIdnew.setCodTpceTpce(paramModificationDonneesVo.getCodeTypePieceAnnexe());
                                   pieceAnnexeIdnew.setNumSeqPers(paramModificationDonneesVo.getNumSeqPesAnnexe());
                                   pieceAnnexeIdnew.setNumPcePian(paramModificationDonneesVo.getNumeroPieceAnnexenouvelle() );
                                   nouvellePieceAnnexe.setPieceAnnexeId(pieceAnnexeIdnew);
                                   nouvellePieceAnnexe.setDatDelvPian(paramModificationDonneesVo.getDateDelivranceAnnexe());
                                   nouvellePieceAnnexe.setDatFvalPian(paramModificationDonneesVo.getDateFinValiditeAnnexe());
                                    
                               }

                                texteModification.append("** Mise à jour d'une pièce **");
                                texteModification.append("# Type Piece : ");
                                texteModification.append(anciennePieceAnnexe.getTypePiece().getLibSiglTpce());
                                texteModification.append(" # Numéro Pièce : ");
                                texteModification.append(anciennePieceAnnexe.getPieceAnnexeId().getNumPcePian());
                                texteModification.append(" # Date Délivrance : ");
                                texteModification.append(anciennePieceAnnexe.getDatDelvPian());
                                texteModification.append(" # Date Fin : ");
                                texteModification.append(DateHandler.dateToStr(anciennePieceAnnexe.getDatFvalPian()));
                                   
                                anciennePieceAnnexe.setDatDelvPian(paramModificationDonneesVo.getDateDelivranceAnnexe());
                                anciennePieceAnnexe.setDatFvalPian(paramModificationDonneesVo.getDateFinValiditeAnnexe());
                                
               
               //---------------------------------------------------------------------------------------------------//
               //------------- Lancer l'opération de synchronisation pour la Carete séjour et Passeport    ---------//
               //------------- Seulement pour les personnes ayant un numéro d'ordre                        
               //---------------------------------------------------------------------------------------------------//
                 
                 if (paramModificationDonneesVo.getPersonneModifie().getTypePiece().getCodTpceTpce().equals(Constants.COD_NUM_ORDRE)){
                  if (paramModificationDonneesVo.getCodeTypePieceAnnexe().equals(Constants.COD_PASS) ||
                      paramModificationDonneesVo.getCodeTypePieceAnnexe().equals(Constants.COD_CSEJ) ){
                         //---------------------------------------------------------------------------------------------------//
                         //----- Synchronisation pour tous les contrats de cette agence dont la personne est tiulaire----------//
                         //---------------------------------------------------------------------------------------------------//
                         
                          PersonneStrc personneStrc = new PersonneStrc();
                          personneStrc.setCodTpceTpce(paramModificationDonneesVo.getPersonneModifie().getTypePiece().getCodTpceTpce());
                          personneStrc.setNumPcePers(paramModificationDonneesVo.getPersonneModifie().getNumPcePers());
                          if(! paramModificationDonneesVo.getCodeStructure().equals(Constants.COD_STRC_DAJ)){
                           personneStrc.setCodStrcStrc(Long.valueOf(paramModificationDonneesVo.getCodeStructure()));
                          }
                          GetPersonneCptTrt getPersonneCptTrt = new GetPersonneCptTrt();
                          getPersonneCptTrt.setSecurityFlag(false);
                          PersonneCpt personneCpt =  (PersonneCpt)getPersonneCptTrt.exec(personneStrc);
                          if (personneCpt != null && personneCpt.getListeContratCpt() != null && personneCpt.getListeContratCpt().size() >0 ){
                           for(Iterator it = personneCpt.getListeContratCpt().iterator(); it.hasNext(); ){
                              ContratCpt contratCpt = (ContratCpt) it.next();
                              paramModificationDonneesVo.setContratModifie(contratCpt);
                              paramModificationDonneesVo.setTypePersonneAvecContrat("T");
                              this.sychronisationPascal(paramModificationDonneesVo); 
                              
                           }
                          }
                         //---------------------------------------------------------------------------------------------------//
                         //----- Synchronisation pour tous les contrats de cette agence dont la personne est mandataire----------//
                         //---------------------------------------------------------------------------------------------------//
                          GetListContratMandataireTrt getListContratMandataire = new GetListContratMandataireTrt();
                          getListContratMandataire.setSecurityFlag(false);
                          Listes listeDesMandat = (Listes) getListContratMandataire.exec(personneStrc);
                  
                         if (listeDesMandat != null && listeDesMandat.getList()!=null && listeDesMandat.getList().size()>0 ){  
                          for(Iterator it = listeDesMandat.getList().iterator(); it.hasNext(); ){
                               ContratCpt contratCpt = (ContratCpt) it.next();;
                               paramModificationDonneesVo.setContratModifie(contratCpt);
                               paramModificationDonneesVo.setTypePersonneAvecContrat("M");
                               this.sychronisationPascal(paramModificationDonneesVo); 
                          }
                         }//fin if
                      }  // Fin if type piece est un PASS ou Carte Sejour
                    } // Fin if type pièce de la personne est un numéro d'ordre
                    
                 } else { /// le cas de la CIN 
                    testPieceAnnexe = "CIN";
                    GetGouvernoratTrt getGouvernoratTrt = 
                        new GetGouvernoratTrt();
                    Gouvernorat gouvernorat = new Gouvernorat();
                    texteModification.append("# Type Piece : ");
                    texteModification.append("CIN ");
                    texteModification.append(" # Numéro Pièce : ");
                    texteModification.append(personneBase.getNumPcePers());
                    texteModification.append(" # Date Délivrance : ");
                    texteModification.append(DateHandler.dateToStr(personneBase.getDatDlvPers()));
                    texteModification.append(" # Lieu de délivrance : ");

                    if (personneBase.getGouvernorat() != null) {
                        Gouvernorat gouvernoratBase = new Gouvernorat();
                        gouvernoratBase.setCodGouvGouv(personneBase.getGouvernorat().getCodGouvGouv());
                        gouvernoratBase = 
                                (Gouvernorat)getGouvernoratTrt.exec(gouvernoratBase);
                        texteModification.append(gouvernoratBase.getLibGouvGouv());
                    }

                    StrHandler.lpad(paramModificationDonneesVo.getPersonneModifie().getNumPcePers(), 
                                    '0', 8);

                    personneBase.setNumPcePers(paramModificationDonneesVo.getPersonneModifie().getNumPcePers());
                    personneBase.setDatDlvPers(paramModificationDonneesVo.getPersonneModifie().getDatDlvPers());
                    /// Gouvernorat

                    gouvernorat.setCodGouvGouv(paramModificationDonneesVo.getPersonneModifie().getGouvernorat().getCodGouvGouv());
                    gouvernorat = 
                            (Gouvernorat)getGouvernoratTrt.exec(gouvernorat);
                    personneBase.setGouvernorat(gouvernorat);
                }
            }

            //-- Cas de modififcation adresse de residence
            if (paramModificationDonneesVo.getTypeModification().getCodCodModf().equals(Long.valueOf(Constants.COD_MODIF_ADR_RES))) {
                GetPaysTrt getPaysTrt = new GetPaysTrt();
                Pays pays = new Pays();
         if (personneBase.getAdresseResid() != null) {
                texteModification = 
                        texteModification.append(" ** Adresse de résidence ** # Imm: ");
                
             if (personneBase.getAdresseResid() != null) {
                if (personneBase.getAdresseResid().getRue()!=null){
                texteModification = 
                        texteModification.append(personneBase.getAdresseResid().getImmeuble());
                }
                texteModification = texteModification.append(" # Rue : ");
                if(personneBase.getAdresseResid().getRue() != null){
                texteModification = 
                        texteModification.append(personneBase.getAdresseResid().getRue());
                }
                texteModification = texteModification.append(" # Cite : ");
                if(personneBase.getAdresseResid().getCite() != null ){
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
                pays.setCodPaysPays(personneBase.getAdresseResid().getCodPaysPays());
                pays = (Pays)getPaysTrt.exec(pays);
                texteModification.append(pays.getLibPaysPays());
                }

                texteModification = 
                        texteModification.append(" # Code Postal: ");
                if(personneBase.getAdresseResid().getCodCpCp() != null){
                texteModification = 
                        texteModification.append(personneBase.getAdresseResid().getCodCpCp());
                }
             }
         }
                if (personneBase.getAdresseProf() != null) {

                    texteModification = 
                            texteModification.append(" ** Adresse de professionnelle ** # Imm: ");
                    if(personneBase.getAdresseProf().getImmeuble() !=null){
                    texteModification = 
                            texteModification.append(personneBase.getAdresseProf().getImmeuble());
                    }
                    texteModification = texteModification.append("# Rue : ");
                    if(personneBase.getAdresseProf().getRue() != null){
                    texteModification = 
                            texteModification.append(personneBase.getAdresseProf().getRue());
                    }
                    texteModification = texteModification.append(" # Cite : ");
                    if(personneBase.getAdresseProf().getCite()!= null){
                    texteModification = 
                            texteModification.append(personneBase.getAdresseProf().getCite());
                    }
                    texteModification = 
                            texteModification.append(" # Ville : ");
                    if(personneBase.getAdresseProf().getVille()!=null){
                    texteModification = 
                            texteModification.append(personneBase.getAdresseProf().getVille());
                    }
                    texteModification = texteModification.append(" # Pays : ");

                    if(personneBase.getAdresseProf().getCodPaysPays() != null){
                    pays.setCodPaysPays(personneBase.getAdresseProf().getCodPaysPays());
                    pays = (Pays)getPaysTrt.exec(pays);
                    texteModification.append(pays.getLibPaysPays());
                    }
                    texteModification = 
                            texteModification.append(" # Code Postal: ");
                    if(personneBase.getAdresseProf().getCodCpCp() != null){
                    texteModification = 
                            texteModification.append(personneBase.getAdresseProf().getCodCpCp());
                    }


                }
                personneBase.setAdresseResid(paramModificationDonneesVo.getPersonneModifie().getAdresseResid());
                personneBase.setAdresseProf(paramModificationDonneesVo.getPersonneModifie().getAdresseProf());
            }

            //-- Cas de modififcation adresse de correspondance
            if (paramModificationDonneesVo.getTypeModification().getCodCodModf().equals(Long.valueOf(Constants.COD_MODIF_ADR_CORR))) {

                GetContratCptByIdTrt getContratCptByIdTrt = 
                    new GetContratCptByIdTrt();
                ContratCpt contrat = 
                    (ContratCpt)getContratCptByIdTrt.exec(paramModificationDonneesVo.getContratModifie());
                  if(contrat.getAdresseCorresp() != null){
                    texteModification = 
                            texteModification.append(" ** Adresse de Correspondance ** # Imm: ");
                    if(contrat.getAdresseCorresp().getImmeuble() != null ){
                    texteModification = 
                            texteModification.append(contrat.getAdresseCorresp().getImmeuble());
                    }
                    texteModification = texteModification.append(" # Rue : ");
                    if(contrat.getAdresseCorresp().getRue() != null){
                    texteModification = 
                            texteModification.append(contrat.getAdresseCorresp().getRue());
                    }
                    texteModification = texteModification.append(" # Cite : ");
                    if(contrat.getAdresseCorresp().getCite() != null ){
                    texteModification = 
                            texteModification.append(contrat.getAdresseCorresp().getCite());
                    }
                    texteModification = texteModification.append(" # Ville : ");
                    if(contrat.getAdresseCorresp().getVille() != null){
                    texteModification = 
                            texteModification.append(contrat.getAdresseCorresp().getVille());
                    }
                    texteModification = texteModification.append(" # Pays : ");
        
                    GetPaysTrt getPaysTrt = new GetPaysTrt();
                    Pays pays = new Pays();
                    if(contrat.getAdresseCorresp().getCodPaysPays() != null ){
                    pays.setCodPaysPays(contrat.getAdresseCorresp().getCodPaysPays());
                    pays = (Pays)getPaysTrt.exec(pays);
                    texteModification.append(pays.getLibPaysPays());
                    }
                    texteModification = 
                            texteModification.append("# Code Postal: ");
                    if(contrat.getAdresseCorresp().getCodCpCp() != null){
                    texteModification = 
                            texteModification.append(contrat.getAdresseCorresp().getCodCpCp());
                    }
                  } else {
                     texteModification.append("Adresse de correespondance non garnie");
                  }
                ContratCpt contratCpt = 
                    (ContratCpt)getContratCptByIdTrt.exec(paramModificationDonneesVo.getContratModifie());

                    
                contratCpt.setAdresseCorresp(paramModificationDonneesVo.getContratModifie().getAdresseCorresp());

                modificationDonnees.setContratCpt(contrat);
                //-----------------------------------------------------------------------//
                //----- Synchronisation pour le contrats --------------------- ----------//
                //-----------------------------------------------------------------------//
                this.sychronisationPascal(paramModificationDonneesVo); 
                
                
            }
            //-- Cas de modififcation Nom et Prenom 
            if (paramModificationDonneesVo.getTypeModification().getCodCodModf().equals(Long.valueOf(Constants.COD_MODIF_NOM))) {

                texteModification = texteModification.append("# Titre : ");
                texteModification = 
                        texteModification.append(personneBase.getLibTitrPers());
                texteModification = texteModification.append(" #  Nom : ");
                texteModification = 
                        texteModification.append(personneBase.getNomNomPers());
                texteModification = texteModification.append(" #  Prénom : ");
                texteModification = 
                        texteModification.append(personneBase.getNomPrnPers());
                texteModification = 
                        texteModification.append(" #  Raison sociale : ");
                texteModification = 
                        texteModification.append(personneBase.getNomRsPers());

                personneBase.setLibTitrPers(paramModificationDonneesVo.getPersonneModifie().getLibTitrPers());
                personneBase.setNomNomPers(paramModificationDonneesVo.getPersonneModifie().getNomNomPers());
                personneBase.setNomPrnPers(paramModificationDonneesVo.getPersonneModifie().getNomPrnPers());
                personneBase.setNomRsPers(paramModificationDonneesVo.getPersonneModifie().getNomRsPers());
                
                
                
                //---------------------------------------------------------------------------------------------------//
                //----- Synchronisation pour tous les contrats de cette agence dont la personne est tiulaire----------//
                //---------------------------------------------------------------------------------------------------//
                
                 PersonneStrc personneStrc = new PersonneStrc();
                 personneStrc.setCodTpceTpce(paramModificationDonneesVo.getPersonneModifie().getTypePiece().getCodTpceTpce());
                 personneStrc.setNumPcePers(paramModificationDonneesVo.getPersonneModifie().getNumPcePers());
                 if (! paramModificationDonneesVo.getCodeStructure().equals(Constants.COD_STRC_DAJ)){
                  personneStrc.setCodStrcStrc(Long.valueOf(paramModificationDonneesVo.getCodeStructure()));
                 }
                 
                 GetPersonneCptTrt getPersonneCptTrt = new GetPersonneCptTrt();
                 getPersonneCptTrt.setSecurityFlag(false);
                 PersonneCpt personneCpt =  (PersonneCpt)getPersonneCptTrt.exec(personneStrc);
                 if (personneCpt != null && personneCpt.getListeContratCpt() != null && personneCpt.getListeContratCpt().size() >0 ){
                  for(Iterator it = personneCpt.getListeContratCpt().iterator(); it.hasNext(); ){
                     ContratCpt contratCpt = (ContratCpt) it.next();
                     paramModificationDonneesVo.setContratModifie(contratCpt);
                     paramModificationDonneesVo.setTypePersonneAvecContrat("T");
                     this.sychronisationPascal(paramModificationDonneesVo); 
                     
                  }
                 }
                //---------------------------------------------------------------------------------------------------//
                //----- Synchronisation pour tous les contrats de cette agence dont la personne est mandataire----------//
                //---------------------------------------------------------------------------------------------------//
                 GetListContratMandataireTrt getListContratMandataire = new GetListContratMandataireTrt();
                 getListContratMandataire.setSecurityFlag(false);
                 Listes listeDesMandat = (Listes) getListContratMandataire.exec(personneStrc);
                  
                 if (listeDesMandat != null && listeDesMandat.getList()!=null && listeDesMandat.getList().size()>0 ){
                   for(Iterator it = listeDesMandat.getList().iterator(); it.hasNext(); ){
                     ContratCpt contratCpt = (ContratCpt) it.next();;
                     paramModificationDonneesVo.setContratModifie(contratCpt);
                     paramModificationDonneesVo.setTypePersonneAvecContrat("M");
                     this.sychronisationPascal(paramModificationDonneesVo); 
                    }
                 }                
             }

            //-- cas de modifictaion de l'activite
            if (paramModificationDonneesVo.getTypeModification().getCodCodModf().equals(Long.valueOf(Constants.COD_MODIF_ACTIVITE))) {
                texteModification = texteModification.append(" #  Secteur : ");
                if (personneBase.getCodSectPers()==null){
                    texteModification.append("Non indiqué");
                }else  if (personneBase.getCodSectPers().equalsIgnoreCase("P")) {
                    texteModification.append("Public");
                } else if (personneBase.getCodSectPers().equalsIgnoreCase("V")) {
                    texteModification.append("Privé");
                }
                if (personneBase.getProfession() != null) {
                    texteModification = 
                            texteModification.append(" #  Profession : ");
                    GetProfessionByIdTrt getProfessionByIdTrtBase = 
                        new GetProfessionByIdTrt();
                    Profession professionBase = 
                        (Profession)getProfessionByIdTrtBase.exec(personneBase.getProfession());
                    texteModification.append(professionBase.getLibProfProf());
                }
                texteModification = 
                        texteModification.append(" #  Activité : ");
                GetActiviteByIdTrt getActiviteByIdTrtBase = 
                    new GetActiviteByIdTrt();
                Activite activiteBase = 
                    (Activite)getActiviteByIdTrtBase.exec(personneBase.getActivite());
                texteModification.append(activiteBase.getLibActAct());

                if (personneBase.getEmployeur()  != null) {
                    texteModification = 
                            texteModification.append(" #  Employeur : ");
                    GetEmployeurByIdTrt getEmployeurByIdTrtBase = 
                        new GetEmployeurByIdTrt();
                    Employeur employeur = 
                        (Employeur)getEmployeurByIdTrtBase.exec(personneBase.getEmployeur());
                    texteModification.append(employeur.getLibEmpEmp());
                }
               
                if (personneBase.getRevRevPers() != null) {
                    texteModification = 
                            texteModification.append(" #  Revenu : ");
                    texteModification.append( StrHandler.formatmnt(personneBase.getRevRevPers()));
                
                }            
                personneBase.setCodSectPers(paramModificationDonneesVo.getPersonneModifie().getCodSectPers());
                if (personneBase.getProfession() != null) {
                    ProfessionId professionId = new ProfessionId();
                    Profession profession = new Profession();

                    professionId.setCodProfProf(paramModificationDonneesVo.getPersonneModifie().getProfession().getProfessionId().getCodProfProf());
                    professionId.setCodGproGpro(paramModificationDonneesVo.getPersonneModifie().getProfession().getProfessionId().getCodGproGpro());
                    profession.setProfessionId(professionId);
                    GetProfessionByIdTrt getProfessionByIdTrt = 
                        new GetProfessionByIdTrt();
                    profession = 
                            (Profession)getProfessionByIdTrt.exec(profession);
                    if (profession != null) {
                        personneBase.setProfession(profession);
                    }
                }
                Activite activite = new Activite();
                ActiviteId activiteId = new ActiviteId();
                activiteId.setCodActAct(paramModificationDonneesVo.getPersonneModifie().getActivite().getActiviteId().getCodActAct());
                activiteId.setCodCactCact(paramModificationDonneesVo.getPersonneModifie().getActivite().getActiviteId().getCodCactCact());
                activiteId.setCodSactSact(paramModificationDonneesVo.getPersonneModifie().getActivite().getActiviteId().getCodSactSact());
                activite.setActiviteId(activiteId);

                GetActiviteByIdTrt getActiviteByIdTrt = 
                    new GetActiviteByIdTrt();
                activite = (Activite)getActiviteByIdTrt.exec(activite);
                if (activite != null) {
                    personneBase.setActivite(activite);
                }
                
                if (paramModificationDonneesVo.getPersonneModifie().getEmployeur()!=null && (paramModificationDonneesVo.getPersonneModifie().getEmployeur().getCodEmpEmp()!=null)){
                    GetEmployeurByIdTrt getEmployeurByIdTrt = new GetEmployeurByIdTrt();
                    Employeur employeur = new Employeur();
                    employeur.setCodEmpEmp(paramModificationDonneesVo.getPersonneModifie().getEmployeur().getCodEmpEmp());
                    employeur =(Employeur) getEmployeurByIdTrt.exec(employeur);
                    personneBase.setEmployeur(employeur);
                }
                
                if (paramModificationDonneesVo.getPersonneModifie().getRevRevPers() !=null){
                    personneBase.setRevRevPers(paramModificationDonneesVo.getPersonneModifie().getRevRevPers());
                }
            }

            //-- cas de modifictaion de la qualité
            if (paramModificationDonneesVo.getTypeModification().getCodCodModf().equals(Long.valueOf(Constants.COD_MODIF_QUALITE))) {
                texteModification = 
                        texteModification.append(" #  Nationalité : ");
                Pays paysNationalite = new Pays();
                paysNationalite.setCodPaysPays(personneBase.getPaysByCodNat1Pays().getCodPaysPays());
                GetPaysTrt getPaysTrt = new GetPaysTrt();
                paysNationalite = (Pays)getPaysTrt.exec(paysNationalite);
                texteModification = 
                        texteModification.append(paysNationalite.getLibPaysPays());
                texteModification = 
                        texteModification.append(" #  Résidence : ");

                if (personneBase.getBoolResPers().equals(Constants.COD_RESIDENT)) {
                    texteModification = texteModification.append(" Résident ");
                } else {
                    texteModification = 
                            texteModification.append(" #  Non Résident ");
                }

                Segment segment = new Segment();
                GetSegmentTrt getSegmentTrt = new GetSegmentTrt();
                texteModification = texteModification.append(" #  Segment : ");

                if (personneBase.getSegment() != null && 
                    personneBase.getSegment().getSegmentId() != null) {
                    segment = 
                            (Segment)getSegmentTrt.exec(personneBase.getSegment());
                    texteModification = 
                            texteModification.append(segment.getLibSegSeg());
                }

                personneBase.setBoolResPers(paramModificationDonneesVo.getPersonneModifie().getBoolResPers());
                //paysNationalite.setCodPaysPays(paramModificationDonneesVo.getPersonneModifie().getPaysByCodNat1Pays().getCodPaysPays());
                
                Pays pays = new Pays();
                pays.setCodPaysPays(paramModificationDonneesVo.getPersonneModifie().getPaysByCodNat1Pays().getCodPaysPays());
                //personneBase.getPaysByCodNat1Pays().setCodPaysPays(paramModificationDonneesVo.getPersonneModifie().getPaysByCodNat1Pays().getCodPaysPays());
                personneBase.setPaysByCodNat1Pays(pays);
               
                if (paramModificationDonneesVo.getPersonneModifie().getSegment() != 
                    null) {
                    Segment seg = new Segment();
                    seg.setSegmentId(paramModificationDonneesVo.getPersonneModifie().getSegment().getSegmentId());
                    
                    personneBase.setSegment(seg);

                }

            }
            //-- cas de modifictaion des données complementaires
            if (paramModificationDonneesVo.getTypeModification().getCodCodModf().equals(Long.valueOf(Constants.COD_MODIF_COMPLEMENTAIRE))) {
                texteModification = 
                        texteModification.append(" # Etat civil : ");

                if (personneBase.getCodSitfPers().equals("M")) {
                    texteModification.append("Marié(e) : ");
                } else if (personneBase.getCodSitfPers().equals("D")) {
                    texteModification.append("Divorcé(e) : ");
                } else if (personneBase.getCodSitfPers().equals("C")) {
                    texteModification.append("Célibataire : ");
                } else if (personneBase.getCodSitfPers().equals("V")) {
                    texteModification.append("Voeuf(ve) : ");
                }
                texteModification = 
                        texteModification.append(" # Deuxième nationalité : ");

                if (personneBase.getPaysByCodNat2Pays() != null) {
                    Pays paysNationalite = new Pays();
                    paysNationalite.setCodPaysPays(personneBase.getPaysByCodNat2Pays().getCodPaysPays());
                    GetPaysTrt getPaysTrt = new GetPaysTrt();
                    paysNationalite = 
                            (Pays)getPaysTrt.exec(paysNationalite);
                    texteModification = 
                            texteModification.append(paysNationalite.getLibPaysPays());
                }

                texteModification = 
                        texteModification.append(" # Régime matrimonial : ");

                if (personneBase.getRegimeMatrimonial() != null) {
                    RegimeMatrimonial regimeMatrimonial = 
                        new RegimeMatrimonial();
                    regimeMatrimonial.setCodRmatRmat(personneBase.getRegimeMatrimonial().getCodRmatRmat());
                    GetRegimeMatrimonialTrt getRegimeMatrimonialTrt = 
                        new GetRegimeMatrimonialTrt();
                    regimeMatrimonial = 
                            (RegimeMatrimonial)getRegimeMatrimonialTrt.exec(regimeMatrimonial);
                    texteModification = 
                            texteModification.append(regimeMatrimonial.getLibRmatRmat());
                }

                texteModification = 
                        texteModification.append(" # Niveau Instruction : ");

                if (personneBase.getNiveauInstruction() != null) {
                    NiveauInstruction niveauInstruction = 
                        new NiveauInstruction();
                    niveauInstruction.setCodNiviNivi(personneBase.getNiveauInstruction().getCodNiviNivi());
                    GetNiveauInstructionTrt getNiveauInstructionTrt = 
                        new GetNiveauInstructionTrt();
                    niveauInstruction = 
                            (NiveauInstruction)getNiveauInstructionTrt.exec(niveauInstruction);
                    texteModification = 
                            texteModification.append(niveauInstruction.getLibNiviNivi());
                }

                if (paramModificationDonneesVo.getClientModifie() != null) {
                    PersonneStrc personneStrc = new PersonneStrc();
                    personneStrc.setCodTpceTpce(personneBase.getTypePiece().getCodTpceTpce());
                    personneStrc.setNumPcePers(personneBase.getNumPcePers());

                    GetPersonneCptTrt getPersonneCptTrt = 
                        new GetPersonneCptTrt();
                    PersonneCpt personneCpt = 
                        (PersonneCpt)getPersonneCptTrt.exec(personneStrc);
                    clientBase = personneCpt.getClient();
                    texteModification = 
                            texteModification.append(" # Code en douane: ");
                    if (paramModificationDonneesVo.getClientModifie().getCodDoanClt() != 
                        null) {
                        texteModification = 
                                texteModification.append(clientBase.getCodDoanClt());
                    }
                    texteModification = 
                            texteModification.append(" # Matricule fiscal: ");
                    if (paramModificationDonneesVo.getClientModifie().getNumFiscClt() != 
                        null) {
                        texteModification = 
                                texteModification.append(clientBase.getNumFiscClt());
                    }

                }
                if (paramModificationDonneesVo.getPersonneModifie().getCodSitfPers() != 
                    null) {
                    personneBase.setCodSitfPers(paramModificationDonneesVo.getPersonneModifie().getCodSitfPers());
                }
                if (paramModificationDonneesVo.getPersonneModifie().getPaysByCodNat2Pays() != 
                    null) {
                    Pays pays = new Pays();
                    pays.setCodPaysPays(paramModificationDonneesVo.getPersonneModifie().getPaysByCodNat2Pays().getCodPaysPays());
                    personneBase.setPaysByCodNat2Pays(pays);
                }

                if (paramModificationDonneesVo.getPersonneModifie().getRegimeMatrimonial() != 
                    null) {
                    RegimeMatrimonial regimeMatrimonial = 
                        new RegimeMatrimonial();
                    regimeMatrimonial.setCodRmatRmat(paramModificationDonneesVo.getPersonneModifie().getRegimeMatrimonial().getCodRmatRmat());
                    personneBase.setRegimeMatrimonial(regimeMatrimonial);
                }

                if (paramModificationDonneesVo.getPersonneModifie().getNiveauInstruction() != 
                    null) {
                    NiveauInstruction niveauInstruction = 
                        new NiveauInstruction();
                    niveauInstruction.setCodNiviNivi(paramModificationDonneesVo.getPersonneModifie().getNiveauInstruction().getCodNiviNivi());
                    personneBase.setNiveauInstruction(niveauInstruction);
                }

                if (clientBase.getNumSeqPers() != null) {
                    if (paramModificationDonneesVo.getClientModifie().getCodDoanClt() != 
                        null) {
                        clientBase.setCodDoanClt(paramModificationDonneesVo.getClientModifie().getCodDoanClt());
                    }

                    if (paramModificationDonneesVo.getClientModifie().getNumFiscClt() != 
                        null) {
                        clientBase.setNumFiscClt(paramModificationDonneesVo.getClientModifie().getNumFiscClt());
                    }
                }

            }

            //-- cas de modifictaion de la nomination complemntaire
            if (paramModificationDonneesVo.getTypeModification().getCodCodModf().equals(Long.valueOf(Constants.COD_MODIF_NOMINAT_COMP))) {

                texteModification.append(" #  Nom de la mère : ");
                texteModification.append(personneBase.getNomNomaPers());

                texteModification.append(" #  Prenom de la mère : ");
                texteModification.append(personneBase.getNomPrnmPers());

                texteModification.append(" #  Nom origine : ");
                texteModification.append(personneBase.getNomNomoPers());

                texteModification.append(" #  Prenom origine : ");
                texteModification.append(personneBase.getNomPrnoPers());

                texteModification.append(" #  Nom attribué : ");
                texteModification.append(personneBase.getNomNomaPers());

                texteModification.append(" #  Prenom attribué : ");
                texteModification.append(personneBase.getNomPrnaPers());

                personneBase.setNomNomaPers(paramModificationDonneesVo.getPersonneModifie().getNomNomaPers());
                personneBase.setNomNomoPers(paramModificationDonneesVo.getPersonneModifie().getNomNomoPers());
                personneBase.setNomNommPers(paramModificationDonneesVo.getPersonneModifie().getNomNommPers());

                personneBase.setNomPrnaPers(paramModificationDonneesVo.getPersonneModifie().getNomPrnaPers());
                personneBase.setNomPrnoPers(paramModificationDonneesVo.getPersonneModifie().getNomPrnoPers());
                personneBase.setNomPrnmPers(paramModificationDonneesVo.getPersonneModifie().getNomPrnmPers());

            }

            //-- cas de modifictaion des données sociales
            if (paramModificationDonneesVo.getTypeModification().getCodCodModf().equals(Long.valueOf(Constants.COD_MODIF_SOCIALE))) {

                texteModification.append(" #  Nombre d'enfant : ");
                if (personneBase.getNbrEnfPers() != null) {
                    texteModification.append(personneBase.getNbrEnfPers().toString());
                }
                texteModification.append(" #  Num affeliation scociale : ");
                if (personneBase.getNumAffsPers() != null) {
                    texteModification.append(personneBase.getNumAffsPers());
                }
                texteModification.append(" #  date affeliation scociale : ");
                if (personneBase.getDatAffsPers() != null) {
                    texteModification.append(personneBase.getDatAffsPers());
                }
                texteModification.append(" #  Catégorie socio prof : ");
                if (personneBase.getCatSocProf() != null && 
                    personneBase.getCatSocProf().getCodCsprCspr() != null) {
                    CatSocProf catSocProf = new CatSocProf();
                    GetCatSocProfTrt getCatSocProfTrt = new GetCatSocProfTrt();
                    catSocProf = 
                            (CatSocProf)getCatSocProfTrt.exec(catSocProf);
                    texteModification.append(catSocProf.getLibCsprCspr());
                }

                personneBase.setNbrEnfPers(paramModificationDonneesVo.getPersonneModifie().getNbrEnfPers());
                personneBase.setNumAffsPers(paramModificationDonneesVo.getPersonneModifie().getNumAffsPers());
                personneBase.setDatAffsPers(paramModificationDonneesVo.getPersonneModifie().getDatAffsPers());
                if (paramModificationDonneesVo.getPersonneModifie().getCatSocProf() != 
                    null && 
                    paramModificationDonneesVo.getPersonneModifie().getCatSocProf().getCodCsprCspr() != 
                    null) {
                    CatSocProf catSocProf = new CatSocProf();
                    GetCatSocProfTrt getCatSocProfTrt = new GetCatSocProfTrt();
                    catSocProf.setCodCsprCspr(paramModificationDonneesVo.getPersonneModifie().getCatSocProf().getCodCsprCspr());
                    catSocProf = 
                            (CatSocProf)getCatSocProfTrt.exec(catSocProf);

                    personneBase.setCatSocProf(catSocProf);
                }
            }

            //-- cas de modifictaion du contact
            if (paramModificationDonneesVo.getTypeModification().getCodCodModf().equals(Long.valueOf(Constants.COD_MODIF_CONTACT))) {

                texteModification = 
                        texteModification.append(" ** Contact ** : # Téléphone : ");
                texteModification = 
                        texteModification.append(personneBase.getNumTelPers());
                texteModification = texteModification.append(" #  Fax : ");
                texteModification = 
                        texteModification.append(personneBase.getNumFaxPers());
                texteModification = texteModification.append(" #  Mail : ");
                texteModification = 
                        texteModification.append(personneBase.getAdrMailPers());
                texteModification = texteModification.append(" #  Web : ");
                texteModification = 
                        texteModification.append(personneBase.getAdrWebPers());
                texteModification = texteModification.append(" #  Swift : ");
                texteModification = 
                        texteModification.append(personneBase.getAdrSwiftPers());
                texteModification = texteModification.append(" #  Telex : ");
                texteModification = 
                        texteModification.append(personneBase.getAdrTlxPers());

                personneBase.setNumTelPers(paramModificationDonneesVo.getPersonneModifie().getNumTelPers());
                personneBase.setNumFaxPers(paramModificationDonneesVo.getPersonneModifie().getNumFaxPers());
                personneBase.setAdrMailPers(paramModificationDonneesVo.getPersonneModifie().getAdrMailPers());
                personneBase.setAdrWebPers(paramModificationDonneesVo.getPersonneModifie().getAdrWebPers());
                personneBase.setAdrSwiftPers(paramModificationDonneesVo.getPersonneModifie().getAdrSwiftPers());
                personneBase.setAdrTlxPers(paramModificationDonneesVo.getPersonneModifie().getAdrTlxPers());
            }

            //-- cas de modifictaion de la raison social pour les personnes morales
            if (paramModificationDonneesVo.getTypeModification().getCodCodModf().equals(Long.valueOf(Constants.COD_MODIF_RAISON_SOCIALE_PM))) {

                texteModification = 
                        texteModification.append(" ** Personne Morale ** : # Raison social : ");
                texteModification = 
                        texteModification.append(personneBase.getNomRsPers());
                texteModification = texteModification.append(" #  Sigle : ");
                texteModification = 
                        texteModification.append(personneBase.getLibSiglPers());

                personneBase.setNomRsPers(paramModificationDonneesVo.getPersonneModifie().getNomRsPers());
                personneBase.setLibSiglPers(paramModificationDonneesVo.getPersonneModifie().getLibSiglPers());
                
                //---------------------------------------------------------------------------------------------------//
                //----- Synchronisation pour tous les contrats de cette agence dont la personne morale est tiulaire----------//
                //---------------------------------------------------------------------------------------------------//
                
                 PersonneStrc personneStrc = new PersonneStrc();
                 personneStrc.setCodTpceTpce(paramModificationDonneesVo.getPersonneModifie().getTypePiece().getCodTpceTpce());
                 personneStrc.setNumPcePers(paramModificationDonneesVo.getPersonneModifie().getNumPcePers());
                 if (!paramModificationDonneesVo.getCodeStructure().equals(Constants.COD_STRC_DAJ) ){
                  personneStrc.setCodStrcStrc(Long.valueOf(paramModificationDonneesVo.getCodeStructure()));
                 }
                 
                 GetPersonneCptTrt getPersonneCptTrt = new GetPersonneCptTrt();
                 getPersonneCptTrt.setSecurityFlag(false);
                 PersonneCpt personneCpt =  (PersonneCpt)getPersonneCptTrt.exec(personneStrc);
                if (personneCpt != null && personneCpt.getListeContratCpt() != null && personneCpt.getListeContratCpt().size() >0 ){
                 for(Iterator it = personneCpt.getListeContratCpt().iterator(); it.hasNext(); ){
                     ContratCpt contratCpt = (ContratCpt) it.next();
                     paramModificationDonneesVo.setContratModifie(contratCpt);
                     paramModificationDonneesVo.setTypePersonneAvecContrat("T");
                     this.sychronisationPascal(paramModificationDonneesVo); 
                  }
                }
            }

            //-- cas de modifictaion de la forme juridique pour les personnes morales
            if (paramModificationDonneesVo.getTypeModification().getCodCodModf().equals(Long.valueOf(Constants.COD_MODIF_FORME_JUR_PM))) {

                texteModification = 
                        texteModification.append(" ** Personne Morale ** : # Catégorie : ");
                texteModification = 
                        texteModification.append(personneBase.getCategoriePersonne().getLibCatpCatp());
                texteModification = 
                        texteModification.append(" #  Forme juridique : ");
                texteModification = 
                        texteModification.append(personneBase.getFormeJuridique().getLibFjFj());
                FormeJuridique  fj = new FormeJuridique();
                fj.setCodFjFj(paramModificationDonneesVo.getPersonneModifie().getFormeJuridique().getCodFjFj());
                personneBase.setFormeJuridique(fj);
                CategoriePersonne ct = new CategoriePersonne();
                ct.setCodCatpCatp(paramModificationDonneesVo.getPersonneModifie().getCategoriePersonne().getCodCatpCatp());
                personneBase.setCategoriePersonne(ct);
            }

            //-- cas de modifictaion des code et matricules pour les personnes morales
            if (paramModificationDonneesVo.getTypeModification().getCodCodModf().equals(Long.valueOf(Constants.COD_MODIF_MATRICULE_PM))) {

                if (paramModificationDonneesVo.getClientModifie() != null) {
                    PersonneStrc personneStrc = new PersonneStrc();
                    personneStrc.setCodTpceTpce(personneBase.getTypePiece().getCodTpceTpce());
                    personneStrc.setNumPcePers(personneBase.getNumPcePers());

                    GetPersonneCptTrt getPersonneCptTrt =  new GetPersonneCptTrt();
                    PersonneCpt personneCpt = (PersonneCpt)getPersonneCptTrt.exec(personneStrc);
                    clientBase = personneCpt.getClient();
                }

                texteModification = 
                        texteModification.append(" ** Personne Morale ** : ");
                /*
                texteModification =
                        texteModification.append("# Date création: ");
                texteModification =
                        texteModification.append(DateHandler.dateToStr(personneBase.getDatPmePers()));
                texteModification =
                        texteModification.append(" # num Loi création: ");
                texteModification =
                        texteModification.append(personneBase.getNumLpmePers());
                texteModification =
                        texteModification.append(" # Date entrée en activité :");
                texteModification =
                        texteModification.append(DateHandler.dateToStr(personneBase.getDateExpPers()));
                texteModification =
                        texteModification.append(" # numero Decret : ");
                texteModification =
                        texteModification.append(personneBase.getNumDecrPers());
                texteModification =
                        texteModification.append(" # Date Decret : ");
                texteModification =
                        texteModification.append(DateHandler.dateToStr(personneBase.getDatDecrPers()));
                texteModification =
                        texteModification.append(" # Numero Jort : ");
                texteModification =
                        texteModification.append(personneBase.getNumJortPers());

                texteModification =
                        texteModification.append(" # Date Jort : ");
                texteModification =
                        texteModification.append(DateHandler.dateToStr(personneBase.getDatJortPers()));

                texteModification = texteModification.append(" # Num BCT : ");
                texteModification = 
                        texteModification.append(clientBase.getNumBctClt());
*/
                texteModification = 
                        texteModification.append(" # Num Mat. Fiscal : ");
                texteModification = 
                        texteModification.append(clientBase.getNumFiscClt());
/*
                texteModification = 
                        texteModification.append(" # Identifiant nationale (RNE) : ");
                texteModification = 
                        texteModification.append(clientBase.getNumRnePers());
*/
                texteModification = 
                        texteModification.append(" # code en Douane : ");
                texteModification = 
                        texteModification.append(clientBase.getCodDoanClt());

/*
                if (personneBase.getDateExpPers() != null && 
                    (!personneBase.getDateExpPers().equals(""))) {
                    personneBase.setDateExpPers(paramModificationDonneesVo.getPersonneModifie().getDateExpPers());
                }

                personneBase.setDatPmePers(paramModificationDonneesVo.getPersonneModifie().getDatPmePers());
                personneBase.setNumLpmePers(paramModificationDonneesVo.getPersonneModifie().getNumLpmePers());
                personneBase.setDateExpPers(paramModificationDonneesVo.getPersonneModifie().getDateExpPers());
                personneBase.setNumDecrPers(paramModificationDonneesVo.getPersonneModifie().getNumDecrPers());
                personneBase.setDatDecrPers(paramModificationDonneesVo.getPersonneModifie().getDatDecrPers());
                personneBase.setDatJortPers(paramModificationDonneesVo.getPersonneModifie().getDatJortPers());
                personneBase.setNumJortPers(paramModificationDonneesVo.getPersonneModifie().getNumJortPers());
*/

                if (paramModificationDonneesVo.getClientModifie() != null) {

                   /* if (paramModificationDonneesVo.getClientModifie().getNumBctClt() != 
                        null) {
                        clientBase.setNumBctClt(paramModificationDonneesVo.getClientModifie().getNumBctClt());
                       
                    }
                   */ 
                    if (paramModificationDonneesVo.getClientModifie().getNumFiscClt() != 
                        null) {
                        clientBase.setNumFiscClt(paramModificationDonneesVo.getClientModifie().getNumFiscClt());
                    }
                    /*
                    if (paramModificationDonneesVo.getClientModifie().getNumRnePers() != 
                        null) {
                        clientBase.setNumRnePers(paramModificationDonneesVo.getClientModifie().getNumRnePers());
                        personneBase.setNumRnePers(paramModificationDonneesVo.getClientModifie().getNumRnePers());
                    }
                    */
                    if (paramModificationDonneesVo.getClientModifie().getCodDoanClt() != 
                        null) {
                        clientBase.setCodDoanClt(paramModificationDonneesVo.getClientModifie().getCodDoanClt());
                    }
                
                }
            }
            //-- cas de Changement de Categorie
            if (paramModificationDonneesVo.getTypeModification().getCodCodModf().equals(Long.valueOf(Constants.COD_MODIF_CHANG_CAT_EPARGN))) {
                ContratCpt contratModifie = 
                    paramModificationDonneesVo.getContratModifie();
                texteModification.append(" ** Changement de Categorie ** # : ");

                texteModification.append("# Categorie/Regime : ");
                texteModification.append(contratModifie.getCatCcptCcpt());

                modificationDonnees.setContratCpt(paramModificationDonneesVo.getContratModifie());

            }

            //-- cas de Transfert d'epargne
            if (paramModificationDonneesVo.getTypeModification().getCodCodModf().equals(Long.valueOf(Constants.COD_MODIF_TRANSFERT_EPARGN))) {
                ContratCpt contratModifie = 
                    paramModificationDonneesVo.getContratModifie();
                Personne personneModifie = 
                    paramModificationDonneesVo.getPersonneModifie();
                texteModification.append(" ** Transfert d'epargne ** # : ");

                texteModification.append(" # Ancienne personne : ");
                texteModification.append(personneModifie.getNomPrnPers() + 
                                         " " + 
                                         personneModifie.getNomNomPers() + 
                                         " N° ");
                texteModification.append(personneModifie.getNumSeqPers());
                texteModification.append(" # Categorie/Regime : ");
                texteModification.append(contratModifie.getCatCcptCcpt());
          
                modificationDonnees.setContratCpt(paramModificationDonneesVo.getContratModifie());

            }

            //-- cas de modifictaion du groupe et Capital
            if (paramModificationDonneesVo.getTypeModification().getCodCodModf().equals(Long.valueOf(Constants.COD_MODIF_CAPITAL_GROUP))) {

                if (paramModificationDonneesVo.getClientModifie() != null) {
                    PersonneStrc personneStrc = new PersonneStrc();
                    personneStrc.setCodTpceTpce(personneBase.getTypePiece().getCodTpceTpce());
                    personneStrc.setNumPcePers(personneBase.getNumPcePers());

                    GetPersonneCptTrt getPersonneCptTrt = 
                        new GetPersonneCptTrt();
                    PersonneCpt personneCpt = 
                        (PersonneCpt)getPersonneCptTrt.exec(personneStrc);
                    clientBase = personneCpt.getClient();
                }

                texteModification.append(" # Date MAJ capital: ");
                texteModification.append(DateHandler.dateToStr(personneBase.getDatCapPers()));
                texteModification.append(" # Montant capital: ");
                texteModification.append(personneBase.getMontCapPers());
                if (clientBase != null && clientBase.getGroupe() != null && clientBase.getGroupe().getNomRsGrp() != null ){
                    texteModification.append(" #  Groupe: ");
                    texteModification.append(clientBase.getGroupe().getNomRsGrp());
                }
                personneBase.setMontCapPers(paramModificationDonneesVo.getPersonneModifie().getMontCapPers());
                personneBase.setDatCapPers(paramModificationDonneesVo.getPersonneModifie().getDatCapPers());

                if (paramModificationDonneesVo.getClientModifie() != null) {
                    if (paramModificationDonneesVo.getClientModifie().getGroupe() != null) {
                        Groupe groupe = new Groupe();
                        groupe.setCodGrpGrp(paramModificationDonneesVo.getClientModifie().getGroupe().getCodGrpGrp());
                        clientBase.setGroupe(groupe);
                    }else {
                        clientBase.setGroupe(null);
                    }
                }
            }
            
              //-- cas de modifictaion de la categorie personne pour les personne physique
           if (paramModificationDonneesVo.getTypeModification().getCodCodModf().equals(Long.valueOf(Constants.COD_MODIF_CHANGEMENT_CATEGORIE ))) {
               logger.info(" entrée changement de catégorie");
                  String ancienNumeroPiece = personneBase.getNumPcePers();
                  Long   ancienCodePiece = personneBase.getTypePiece().getCodTpceTpce();
                  
                  texteModification = 
                          texteModification.append(" ** Personne Physique ** : # Identifiant : type pièce :");
                  texteModification = 
                          texteModification.append(personneBase.getTypePiece().getLibSiglTpce() + " Numéro pièce : " + personneBase.getNumPcePers());
                  texteModification = 
                                            texteModification.append(" # Catégorie : ");        
                  texteModification = 
                          texteModification.append(personneBase.getCategoriePersonne().getLibCatpCatp());
                  texteModification = 
                          texteModification.append(" #  Nationalité : ");
                  Pays paysNationalite = new Pays();
                  paysNationalite.setCodPaysPays(personneBase.getPaysByCodNat1Pays().getCodPaysPays());
                  GetPaysTrt getPaysTrt = new GetPaysTrt();
                  paysNationalite = (Pays)getPaysTrt.exec(paysNationalite);
                  texteModification = 
                          texteModification.append(paysNationalite.getLibPaysPays());
                          
                  texteModification = 
                          texteModification.append(" #  Forme juridique : ");
                  texteModification = 
                          texteModification.append(personneBase.getFormeJuridique().getLibFjFj());
                  
                  boolean testModificationPiece = false; // variable pour tester s'il y a eu changement de piece ( soit annexe ou CIN)
                  
                  //------------------- cas de la CIN -----------------------------------//
                  if  (paramModificationDonneesVo.getPersonneModifie().getTypePiece().getCodTpceTpce().equals(Constants.COD_CIN)){
                      if (!paramModificationDonneesVo.getPersonneModifie().getNumPcePers().equals(personneBase.getNumPcePers()) ){
                       paramModificationDonneesVo.setNumeroPieceAnnexenouvelle(paramModificationDonneesVo.getPersonneModifie().getNumPcePers());
                       paramModificationDonneesVo.setCodeTypePieceAnnexe(paramModificationDonneesVo.getPersonneModifie().getTypePiece().getCodTpceTpce());
                       testModificationPiece = true;
                      }
                  }
                  
                  if (personneBase.getCategoriePersonne() != null && 
                      (     (personneBase.getCategoriePersonne().getCodCatpCatp().equals(Constants.MINEUR))
                         || (personneBase.getCategoriePersonne().getCodCatpCatp().equals(Constants.PERSONNE_PHYSIQUE_ETRANGERE_MAJEUR))
                         || (personneBase.getCategoriePersonne().getCodCatpCatp().equals(Constants.PERSONNE_PHYSIQUE_ETRANGERE_MAJEUR_INCAPABLE))
                         || (personneBase.getCategoriePersonne().getCodCatpCatp().equals(Constants.PERSONNE_PHYSIQUE_ETRANGERE_MINEUR_EMANCIPE)) 
                         
                      ) ){
                      
                      if ( paramModificationDonneesVo.getPersonneModifie().getCategoriePersonne().getCodCatpCatp().equals(Constants.PERSONNE_PHYSIQUE_TUNISIENNE_MAJEUR)||
                          paramModificationDonneesVo.getPersonneModifie().getCategoriePersonne().getCodCatpCatp().equals(Constants.PERSONNE_PHYSIQUE_TUNISIENNE_MAJEUR_INCAPABLE) ){
                          personneBase.setTypePiece(paramModificationDonneesVo.getPersonneModifie().getTypePiece());
                          personneBase.setNumPcePers(paramModificationDonneesVo.getPersonneModifie().getNumPcePers());
                          personneBase.setGouvernorat(paramModificationDonneesVo.getPersonneModifie().getGouvernorat());
                          personneBase.setDatDlvPers(paramModificationDonneesVo.getPersonneModifie().getDatDlvPers()); 
                          personneBase.setFormeJuridique(paramModificationDonneesVo.getPersonneModifie().getFormeJuridique());
                          personneBase.setPaysByCodNat2Pays(personneBase.getPaysByCodNat1Pays());
                          personneBase.setPaysByCodNat1Pays(paramModificationDonneesVo.getPersonneModifie().getPaysByCodNat1Pays());
                       } 
                           
                  }
                  
                  //-----------------------------------------------------//
                  //--------- Ajout ou MAJ pièce annexe ----------------//
                   if (paramModificationDonneesVo.getNouvellePieceAnnexe() != null ) {
                       logger.info("Structure: "+ paramModificationDonneesVo.getCodeStructure().toString()+" Matricule: "+paramModificationDonneesVo.getMatriculeUser().toString()+" entrée mise à jour pièce annexe");

                       PieceAnnexeId pieceAnnexeIdnew =new PieceAnnexeId();
                       pieceAnnexeIdnew.setCodTpceTpce(paramModificationDonneesVo.getNouvellePieceAnnexe().getPieceAnnexeId().getCodTpceTpce());
                       pieceAnnexeIdnew.setNumSeqPers(paramModificationDonneesVo.getNouvellePieceAnnexe().getPieceAnnexeId().getNumSeqPers());
                       pieceAnnexeIdnew.setNumPcePian(paramModificationDonneesVo.getNouvellePieceAnnexe().getPieceAnnexeId().getNumPcePian().toUpperCase() );
                       nouvellePieceAnnexe.setPieceAnnexeId(pieceAnnexeIdnew);
                       nouvellePieceAnnexe.setDatDelvPian(paramModificationDonneesVo.getNouvellePieceAnnexe().getDatDelvPian());
                       nouvellePieceAnnexe.setDatFvalPian(paramModificationDonneesVo.getNouvellePieceAnnexe().getDatFvalPian());
                   
                       GetPieceAnnexeByIdTrt getPieceAnnexeByIdTrt = new GetPieceAnnexeByIdTrt();
                       getPieceAnnexeByIdTrt.setSecurityFlag(false);
                       
                       anciennePieceAnnexe  = (PieceAnnexe)getPieceAnnexeByIdTrt.exec(nouvellePieceAnnexe);
                       
                       if (anciennePieceAnnexe != null && anciennePieceAnnexe.getPieceAnnexeId() != null){
                          
                           TypePiece typePiece = new TypePiece();
                           typePiece.setCodTpceTpce( anciennePieceAnnexe.getPieceAnnexeId().getCodTpceTpce()); 
                           
                           GetTypePieceTrt getTypePiece = new GetTypePieceTrt();
                           getTypePiece.setSecurityFlag(false);
                           typePiece = (TypePiece) getTypePiece.exec(typePiece);
                           texteModification =   texteModification.append(" #  MAJ Pièce : " + typePiece.getLibSiglTpce() + " numéro : "+anciennePieceAnnexe.getPieceAnnexeId().getNumPcePian());               
                           texteModification =   texteModification.append(" Date délivrance : " +anciennePieceAnnexe.getDatDelvPian());
                           texteModification =   texteModification.append(" Date fin validité : " +anciennePieceAnnexe.getDatFvalPian());
                        
                           testPieceAnnexe = "MAJ";
                          
                           anciennePieceAnnexe.setDatDelvPian(paramModificationDonneesVo.getNouvellePieceAnnexe().getDatDelvPian());
                           anciennePieceAnnexe.setDatFvalPian(paramModificationDonneesVo.getNouvellePieceAnnexe().getDatFvalPian());
                            
                       }else {
                           testPieceAnnexe = "AJOUT";                           
                       }
                  }
                  
                  CategoriePersonne ct = new CategoriePersonne();
                  ct.setCodCatpCatp(paramModificationDonneesVo.getPersonneModifie().getCategoriePersonne().getCodCatpCatp());
                  personneBase.setCategoriePersonne(ct);
                  
                  //-------------------------------------------------//
                  //----- Lancement de la synchronisation -----------//
                  //-------------------------------------------------//
                  
                
                  
                  if (testPieceAnnexe.equals("MAJ") ){
                    paramModificationDonneesVo.setCodeTypePieceAnnexe(nouvellePieceAnnexe.getPieceAnnexeId().getCodTpceTpce());
                    paramModificationDonneesVo.setNumeroPieceAnnexenouvelle(nouvellePieceAnnexe.getPieceAnnexeId().getNumPcePian());
                    testModificationPiece = true;
                  }
                  
                  
                  //---------------------------------------------------------------------------------------------------//
                  //----- Synchronisation pour tous les contrats de cette agence dont la personne est tiulaire----------//
                  //---------------------------------------------------------------------------------------------------//
                  if (testModificationPiece){
                       PersonneStrc personneStrc = new PersonneStrc();
                       personneStrc.setCodTpceTpce(ancienCodePiece);
                       personneStrc.setNumPcePers(ancienNumeroPiece);
                       if (! paramModificationDonneesVo.getCodeStructure().equals(Constants.COD_STRC_DAJ)){
                        personneStrc.setCodStrcStrc(Long.valueOf(paramModificationDonneesVo.getCodeStructure()));
                       }
                       
                       GetPersonneCptTrt getPersonneCptTrt = new GetPersonneCptTrt();
                       getPersonneCptTrt.setSecurityFlag(false);
                       PersonneCpt personneCpt =  (PersonneCpt)getPersonneCptTrt.exec(personneStrc);
                      if (personneCpt != null && personneCpt.getListeContratCpt() != null && personneCpt.getListeContratCpt().size() >0 ){
                       for(Iterator it = personneCpt.getListeContratCpt().iterator(); it.hasNext(); ){
                           ContratCpt contratCpt = (ContratCpt) it.next();
                           paramModificationDonneesVo.setContratModifie(contratCpt);
                           paramModificationDonneesVo.setTypePersonneAvecContrat("T");
                           this.sychronisationPascal(paramModificationDonneesVo); 
                        }
                      }
                      //---------------------------------------------------------------------------------------------------//
                      //----- Synchronisation pour tous les contrats de cette agence dont la personne est mandataire----------//
                      //---------------------------------------------------------------------------------------------------//
                       GetListContratMandataireTrt getListContratMandataire = new GetListContratMandataireTrt();
                       getListContratMandataire.setSecurityFlag(false);
                       Listes listeDesMandat = (Listes) getListContratMandataire.exec(personneStrc);
                       if (listeDesMandat != null && listeDesMandat.getList()!=null && listeDesMandat.getList().size()>0 ){
                        for(Iterator it = listeDesMandat.getList().iterator(); it.hasNext(); ){
                         ContratCpt contratCpt = (ContratCpt) it.next();;
                         paramModificationDonneesVo.setContratModifie(contratCpt);
                         paramModificationDonneesVo.setTypePersonneAvecContrat("M");
                         this.sychronisationPascal(paramModificationDonneesVo); 
                        }
                       }// fin if 
                 }  // Fin test modifiction piece 
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


            CRUDservice crudService = 
                (CRUDservice)context.getBean("crudservice");
            //----------- Modification Contrat 
            if (paramModificationDonneesVo.getTypeModification().getCodCodModf().equals(Long.valueOf(Constants.COD_MODIF_ADR_CORR))) {
                crudService.update(modificationDonnees.getContratCpt()) ;

                //-------- Modification piece annexe    
            } else if (paramModificationDonneesVo.getTypeModification().getCodCodModf().equals(Long.valueOf(Constants.COD_MODIF_IDENT_SEC))) {
                
                if (testPieceAnnexe.equalsIgnoreCase("AJOUT")) {
                    anciennePieceAnnexe.setDatFvalPian(new Date());
                    crudService.update(anciennePieceAnnexe);
                    crudService.create(nouvellePieceAnnexe);
                 } else if (testPieceAnnexe.equals("MAJ") ){
                     crudService.update(anciennePieceAnnexe);
                 } else if (testPieceAnnexe.equals("CIN")) {
                     crudService.update(personneBase);
                 }

            } else if ((paramModificationDonneesVo.getTypeModification().getCodCodModf().equals(Long.valueOf(Constants.COD_MODIF_COMPLEMENTAIRE)) || 
                        paramModificationDonneesVo.getTypeModification().getCodCodModf().equals(Long.valueOf(Constants.COD_MODIF_MATRICULE_PM)) || 
                        paramModificationDonneesVo.getTypeModification().getCodCodModf().equals(Long.valueOf(Constants.COD_MODIF_CAPITAL_GROUP))) && 
                       clientBase != null) {
                crudService.update(personneBase);
                if (clientBase.getNumSeqPers() != null) {
                    crudService.update(clientBase);
                }
            } else if (paramModificationDonneesVo.getTypeModification().getCodCodModf().equals(Long.valueOf(Constants.COD_MODIF_AJOUT_PIECE))) {
              
                crudService.create(paramModificationDonneesVo.getNouvellePieceAnnexe());
           
            } else if (paramModificationDonneesVo.getTypeModification().getCodCodModf().equals(Long.valueOf(Constants.COD_MODIF_CHANGEMENT_CATEGORIE))) {
                 
                 if (testPieceAnnexe.equals("MAJ") ){
                    crudService.update(anciennePieceAnnexe);
                 } else if( testPieceAnnexe.equals("AJOUT")) {
                     crudService.create(paramModificationDonneesVo.getNouvellePieceAnnexe());
                 }
                 crudService.update(personneBase);
                 
            }else {
                crudService.update(personneBase);
            }
            crudService.create(modificationDonnees);
            logger.info("Structure: "+ paramModificationDonneesVo.getCodeStructure().toString()+" Matricule: "+paramModificationDonneesVo.getMatriculeUser().toString()+" Evénement : Sortie Trt normale");            
            return (paramModificationDonneesVo);
         
           
              // Fin controle fin de journee
          }else{
                    com.oxia.fwk.core.Error erreur = new com.oxia.fwk.core.Error();
                    StringBuffer text = new StringBuffer("La journée est déja clôturée...");            
                    erreur.setCode("100");
                    erreur.setDescription(text.toString());
                    erreur.setKey("ModifierDonneesClientTrt");
                    paramModificationDonneesVo.addError(erreur); 
                    

                    return (paramModificationDonneesVo);
              }     
        } catch (Exception e) {
            System.out.println(e.toString());
            com.oxia.fwk.core.Error erreur = new com.oxia.fwk.core.Error();
            StringBuffer text = 
                new StringBuffer("Erreur dans ModifierDonneesClientTrt : ");
            text.append(e.toString());
            text.append(e.getCause().getCause().getMessage())    ;
            erreur.setCode("200");
            erreur.setDescription(text.toString());
            erreur.setKey("ModifierDonnesClient");
            logger.info("Structure: "+ paramModificationDonneesVo.getCodeStructure().toString()+" Matricule: "+paramModificationDonneesVo.getMatriculeUser().toString()+" Exception : " +  e.toString());
            
            paramModificationDonneesVo.addError(erreur);
            return (paramModificationDonneesVo);
        }
    }
    
    public void genCroText(ValueObject vo){
        
    
    }
    
    public String  getNumeroTache (IValueObject vo) {
       
       ParamModificationDonneesVo  paramModificationDonneesVo = (ParamModificationDonneesVo) vo;
        
        if (paramModificationDonneesVo.getTypeModification().getCodCodModf().equals(Long.valueOf(Constants.COD_MODIF_CHANGEMENT_ID))) {
            return Constants.RESS_MODIF_CHANGEMENT_ID;
        } else  if (paramModificationDonneesVo.getTypeModification().getCodCodModf().equals(Long.valueOf(Constants.COD_MODIF_IDENTIFIANT))) {
            return Constants.RESS_MODIF_IDENTIFIANT;
        }  else if (paramModificationDonneesVo.getTypeModification().getCodCodModf().equals(Long.valueOf(Constants.COD_MODIF_AJOUT_PIECE))) {
            return Constants.RESS_MODIF_AJOUT_PIECE;
        }  else  if (paramModificationDonneesVo.getTypeModification().getCodCodModf().equals(Long.valueOf(Constants.COD_MODIF_IDENT_SEC))) {
            return Constants.RESS_MODIF_IDENT_SEC;
        } else  if (paramModificationDonneesVo.getTypeModification().getCodCodModf().equals(Long.valueOf(Constants.COD_MODIF_ADR_RES))) {
            return Constants.RESS_MODIF_ADR_RES;
        } else if (paramModificationDonneesVo.getTypeModification().getCodCodModf().equals(Long.valueOf(Constants.COD_MODIF_ADR_CORR))) {
            return Constants.RESS_MODIF_ADR_CORR;
        } else if (paramModificationDonneesVo.getTypeModification().getCodCodModf().equals(Long.valueOf(Constants.COD_MODIF_NOM))) {
            return Constants.RESS_MODIF_NOM;
        } else  if (paramModificationDonneesVo.getTypeModification().getCodCodModf().equals(Long.valueOf(Constants.COD_MODIF_ACTIVITE))) {
            return Constants.RESS_MODIF_ACTIVITE;
        } else if (paramModificationDonneesVo.getTypeModification().getCodCodModf().equals(Long.valueOf(Constants.COD_MODIF_QUALITE))) {
            return Constants.RESS_MODIF_QUALITE;
        } else  if (paramModificationDonneesVo.getTypeModification().getCodCodModf().equals(Long.valueOf(Constants.COD_MODIF_COMPLEMENTAIRE))) {
            return Constants.RESS_MODIF_COMPLEMENTAIRE;
        } else if (paramModificationDonneesVo.getTypeModification().getCodCodModf().equals(Long.valueOf(Constants.COD_MODIF_NOMINAT_COMP))) {
            return Constants.RESS_MODIF_NOMINAT_COMP;
        } else  if (paramModificationDonneesVo.getTypeModification().getCodCodModf().equals(Long.valueOf(Constants.COD_MODIF_SOCIALE))) {
            return Constants.RESS_MODIF_SOCIALE;
        } else if (paramModificationDonneesVo.getTypeModification().getCodCodModf().equals(Long.valueOf(Constants.COD_MODIF_CONTACT))) {
            return Constants.RESS_MODIF_CONTACT;
        } else  if (paramModificationDonneesVo.getTypeModification().getCodCodModf().equals(Long.valueOf(Constants.COD_MODIF_RAISON_SOCIALE_PM))) {
            return Constants.RESS_MODIF_RAISON_SOCIALE_PM;
        } else if (paramModificationDonneesVo.getTypeModification().getCodCodModf().equals(Long.valueOf(Constants.COD_MODIF_MATRICULE_PM))) {
            return Constants.RESS_MODIF_MATRICULE_PM;
        } else if (paramModificationDonneesVo.getTypeModification().getCodCodModf().equals(Long.valueOf(Constants.COD_MODIF_CAPITAL_GROUP))) {
            return Constants.RESS_MODIF_CAPITAL_GROUP;
        } else if (paramModificationDonneesVo.getTypeModification().getCodCodModf().equals(Long.valueOf(Constants.COD_MODIF_CHANG_CAT_EPARGN))) {
            return Constants.RESS_MODIF_CHANG_CAT_EPARGN;
        } else if (paramModificationDonneesVo.getTypeModification().getCodCodModf().equals(Long.valueOf(Constants.COD_MODIF_TRANSFERT_EPARGN))) {
            return Constants.RESS_MODIF_TRANSFERT_EPARGN;
        }
            return "38302";
            
        
        
    }


    public void genererSynchronisationPascal(ValueObject vo) {   
        
       ParamModificationDonneesVo paramModificationDonneesVo = (ParamModificationDonneesVo)vo;
       logger.info("Structure: "+ paramModificationDonneesVo.getCodeStructure().toString()+" Matricule: "+paramModificationDonneesVo.getMatriculeUser().toString()+" Entrée : genererSynchronisationPascal");
        
       StringBuffer partieVariable =new StringBuffer();
       
       this.setDateOperationSynch(new Date());
      
       this.setCodeStructureSynch(paramModificationDonneesVo.getContratModifie().getContratCptId().getCodStrcStrc());
        
        ///------------------------------------------//
        ///--------- Modification Nom ---------------// 
        ///------------------------------------------//
        
       if (paramModificationDonneesVo.getTypeModification().getCodCodModf().equals(Long.valueOf(Constants.COD_MODIF_NOM)) ||
          (paramModificationDonneesVo.getTypeModification().getCodCodModf().equals(Long.valueOf(Constants.COD_MODIF_RAISON_SOCIALE_PM))) ) {
           
           logger.info("Structure: "+ paramModificationDonneesVo.getCodeStructure().toString()+" Matricule: "+paramModificationDonneesVo.getMatriculeUser().toString()+" Entrée synchronisation modification Nom / Raison sociale " );   
           
           this.setCodeOperationSynch(Constants.COD_OPER_MODIF_NOM);
           this.setCodeTacheSynch(Constants.COD_TACHE_MODIF_NOM);
           
           //-- Données du contrat 
           if (paramModificationDonneesVo.getContratModifie()!= null && paramModificationDonneesVo.getContratModifie().getContratCptId() != null){
               partieVariable.append(StrHandler.lpad(paramModificationDonneesVo.getContratModifie().getContratCptId().getCodPrdPrd().toString(),'0',4));
               partieVariable.append(StrHandler.lpad(paramModificationDonneesVo.getContratModifie().getContratCptId().getNumCcptCcpt().toString(),'0',6));
           }
           //----- Nom pour la personne physique
           
                 
          //------------------- Si la personne est physique
         if (paramModificationDonneesVo.getPersonneModifie().getCategoriePersonne().getTypePers().getCodTperTper().equals(Constants.PERSPHYSIQUE)){
               //----- nom
               if (paramModificationDonneesVo.getPersonneModifie().getNomPrnPers() != null){
                   String nom = new String();
                if(paramModificationDonneesVo.getPersonneModifie().getNomPrnPers().length()>20){
                    nom = paramModificationDonneesVo.getPersonneModifie().getNomPrnPers().substring(0,19);
                }else {
                    nom = paramModificationDonneesVo.getPersonneModifie().getNomPrnPers();
                }
                partieVariable.append(StrHandler.rpad(nom ,' ',20));
               }
               //----- Prénom
               if (paramModificationDonneesVo.getPersonneModifie().getNomNomPers() != null){
                String prenom = new String();
                if(paramModificationDonneesVo.getPersonneModifie().getNomNomPers().length() >20){
                    prenom = paramModificationDonneesVo.getPersonneModifie().getNomNomPers().substring(0,19);
                }else{
                    prenom = paramModificationDonneesVo.getPersonneModifie().getNomNomPers();
                }
                partieVariable.append(StrHandler.rpad(prenom ,' ',20));
               } 
          }
          
          //------------------- Si la personne est morale
          if (paramModificationDonneesVo.getPersonneModifie().getCategoriePersonne().getTypePers().getCodTperTper().equals(Constants.PERSMORALE)){
           //----- Raison sociale
           if (paramModificationDonneesVo.getPersonneModifie().getNomRsPers() != null && !paramModificationDonneesVo.getPersonneModifie().getNomRsPers().equals("")){
             partieVariable.append(StrHandler.rpad(paramModificationDonneesVo.getPersonneModifie().getNomRsPers() ,' ',40));
           }
          }
           //----- Type de la personne (T :titulaire / M :mandatair)
           if (paramModificationDonneesVo.getTypePersonneAvecContrat() != null){
               partieVariable.append(paramModificationDonneesVo.getTypePersonneAvecContrat());
           }
           
           //----- Type de la pièce
           if (paramModificationDonneesVo.getPersonneModifie().getTypePiece() != null && paramModificationDonneesVo.getPersonneModifie().getTypePiece().getCodTpceTpce() != null){
               partieVariable.append(StrHandler.lpad(paramModificationDonneesVo.getPersonneModifie().getTypePiece().getCodTpceTpce().toString(),'0',2));
           }
           
           //----- Numéro de la pièce
           if (paramModificationDonneesVo.getPersonneModifie().getCategoriePersonne().getTypePers().getCodTperTper().equals(Constants.PERSPHYSIQUE)){
             if (paramModificationDonneesVo.getPersonneModifie().getNumPcePers() != null){
                partieVariable.append(StrHandler.lpad(paramModificationDonneesVo.getPersonneModifie().getNumPcePers(),'0',10));
             }
           } else {  
               partieVariable.append("0000000000");
           }
        
       } else if (paramModificationDonneesVo.getTypeModification().getCodCodModf().equals(Long.valueOf(Constants.COD_MODIF_ADR_CORR))) {
           ///-------------------------------------------//
           ///--------- Modification Adresse ------------// 
           ///-------------------------------------------//
            //-- Données du contrat 
            if (paramModificationDonneesVo.getContratModifie()!= null && paramModificationDonneesVo.getContratModifie().getContratCptId() != null){
                partieVariable.append(StrHandler.lpad(paramModificationDonneesVo.getContratModifie().getContratCptId().getCodPrdPrd().toString(),'0',4));
                partieVariable.append(StrHandler.lpad(paramModificationDonneesVo.getContratModifie().getContratCptId().getNumCcptCcpt().toString(),'0',6));
            }
              logger.info("Structure: "+ paramModificationDonneesVo.getCodeStructure().toString()+" Matricule: "+paramModificationDonneesVo.getMatriculeUser().toString()+" Entrée synchronisation modification adresse " );
            this.setCodeOperationSynch(Constants.COD_OPER_MODIF_ADR_CORR);
            this.setCodeTacheSynch(Constants.COD_TACHE_MODIF_ADR_CORR);
            
            if(paramModificationDonneesVo.getContratModifie().getAdresseCorresp().getRue() != null){
             String  rue = new String("                                        ");
             if(!paramModificationDonneesVo.getContratModifie().getAdresseCorresp().getRue().equals("")){
               rue = StrHandler.rpad(paramModificationDonneesVo.getContratModifie().getAdresseCorresp().getRue(),' ',40);
             }else {
               rue =StrHandler.rpad( rue,' ',40);    
             }
             partieVariable.append(rue);
            }
            if(paramModificationDonneesVo.getContratModifie().getAdresseCorresp().getCite() != null){
             String ville = new String ("                    ");
             if (!paramModificationDonneesVo.getContratModifie().getAdresseCorresp().getCite().equals("")){
               ville =  StrHandler.rpad(paramModificationDonneesVo.getContratModifie().getAdresseCorresp().getCite(),' ',20);
             }else{
               ville =    StrHandler.rpad(ville,' ',20);
             }
             partieVariable.append(ville);
            }
            
            if(paramModificationDonneesVo.getContratModifie().getAdresseCorresp().getCodCpCp() != null){
             String codePostal = new String (StrHandler.lpad(paramModificationDonneesVo.getContratModifie().getAdresseCorresp().getCodCpCp(),'0',5));
             partieVariable.append(codePostal);
            }
            
            if(paramModificationDonneesVo.getContratModifie().getAdresseCorresp().getCodPaysPays() != null){
             String codePays = new String (StrHandler.lpad(paramModificationDonneesVo.getContratModifie().getAdresseCorresp().getCodPaysPays(),'0',5)); 
             partieVariable.append(codePays);
            }
            
           
          } else if (paramModificationDonneesVo.getTypeModification().getCodCodModf().equals(Long.valueOf((Constants.COD_MODIF_IDENT_SEC)))||
                     paramModificationDonneesVo.getTypeModification().getCodCodModf().equals(Long.valueOf((Constants.COD_MODIF_CHANGEMENT_CATEGORIE)))) {
               
                if (paramModificationDonneesVo.getTypeModification().getCodCodModf().equals(Long.valueOf((Constants.COD_MODIF_IDENT_SEC)))){
                    logger.info("Structure: "+ paramModificationDonneesVo.getCodeStructure().toString()+" Matricule: "+paramModificationDonneesVo.getMatriculeUser().toString()+" Entrée synchronisation modification pièce " ); 
                }else {
                    logger.info("Structure: "+ paramModificationDonneesVo.getCodeStructure().toString()+" Matricule: "+paramModificationDonneesVo.getMatriculeUser().toString()+" Entrée synchronisation changement catégorie" ); 
                }
                
                ///-------------------------------------------//
                ///--------- Modification Piece Annexe -------// 
                ///-------------------------------------------//
                 //-- Données du contrat 
                 if (paramModificationDonneesVo.getContratModifie()!= null && paramModificationDonneesVo.getContratModifie().getContratCptId() != null){
                     partieVariable.append(StrHandler.lpad(paramModificationDonneesVo.getContratModifie().getContratCptId().getCodPrdPrd().toString(),'0',4));
                     partieVariable.append(StrHandler.lpad(paramModificationDonneesVo.getContratModifie().getContratCptId().getNumCcptCcpt().toString(),'0',6));
                 }
                 
                 this.setCodeOperationSynch(Constants.COD_OPER_MODIF_IDENTIFIANT);
                 this.setCodeTacheSynch(Constants.COD_TACHE_MODIF_IDENTIFIANT);
                 
                //--------- Les données de la pièce ------------------------//
                //------ Type de la pièce   
                if (paramModificationDonneesVo.getCodeTypePieceAnnexe() != null){
                    if (paramModificationDonneesVo.getCodeTypePieceAnnexe().equals(Constants.COD_PASS)){
                        partieVariable.append('P');   
                    }else if (paramModificationDonneesVo.getCodeTypePieceAnnexe().equals(Constants.COD_CSEJ)){
                        partieVariable.append('S');   
                    }else if (paramModificationDonneesVo.getCodeTypePieceAnnexe().equals(Constants.COD_CIN)){
                        partieVariable.append('C');   
                    }
                }
                //------ Numéro de la pièce  
                if (paramModificationDonneesVo.getNumeroPieceAnnexenouvelle() != null){
                    if (paramModificationDonneesVo.getNumeroPieceAnnexenouvelle().toString().length() > 10){
                        partieVariable.append(paramModificationDonneesVo.getNumeroPieceAnnexenouvelle().substring(0,9));    
                    }else {
                        partieVariable.append(StrHandler.lpad(paramModificationDonneesVo.getNumeroPieceAnnexenouvelle().toString(),'0',10));   
                    }
                }
               
               //----- Type de la personne (T :titulaire / M :mandatair)
                if (paramModificationDonneesVo.getTypePersonneAvecContrat() != null){
                    partieVariable.append(paramModificationDonneesVo.getTypePersonneAvecContrat());
                }
                 
               
            }             
       
        logger.info("Structure: "+ paramModificationDonneesVo.getCodeStructure().toString()+" Matricule: "+paramModificationDonneesVo.getMatriculeUser().toString()+" Sortie : genererSynchronisationPascal le text est : "+partieVariable.toString());
        
       this.setTextSynch(partieVariable.toString());
           
        
    }
    public IValueObject getNumeroDomaine(IValueObject vo){
        StructureDomaine structureDomaine = new StructureDomaine();
        ParamModificationDonneesVo paramModificationDonneesVo = 
            (ParamModificationDonneesVo)vo;
        structureDomaine.setCodDomDomm(Constants.COD_DOM_CLIENT);
        structureDomaine.setCodStrcStrc(paramModificationDonneesVo.getCodeStructure());
        return structureDomaine;
    }
}
