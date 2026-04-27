package com.bna.smile.web.commun.actions;

import com.bna.commun.model.Activite;
import com.bna.commun.model.ActiviteId;
import com.bna.commun.model.Adresse;
import com.bna.commun.model.CategoriePersonne;
import com.bna.commun.model.CodePostal;
import com.bna.commun.model.ContratCpt;
import com.bna.commun.model.FormeJuridique;
import com.bna.commun.model.Gouvernorat;
import com.bna.commun.model.Pays;
import com.bna.commun.model.Personne;
import com.bna.commun.model.Profession;
import com.bna.commun.model.ProfessionId;
import com.bna.commun.model.StructureDomaine;
import com.bna.commun.util.DateHandler;
import com.bna.commun.util.SmileUtil;
import com.bna.commun.util.StrHandler;
import com.bna.smile.model.constant.Constants;
import com.bna.smile.model.domainecommun.commande.GetCategoriePersonneCmd;
import com.bna.smile.model.domainecommun.commande.GetCodePostalCmd;
import com.bna.smile.model.domainecommun.commande.GetFormeJuridiqueCmd;
import com.bna.smile.model.domainecommun.commande.GetPaysCmd;
import com.bna.smile.model.domainecommun.commande.GetPersonneCmd;

import com.bna.smile.model.domainecommun.model.ListTypeCatTpce;
import com.bna.smile.model.domainecommun.model.PersonneStrc;
import com.bna.smile.model.domainecommun.traitement.GetFormeJuridiqueTrt;
import com.bna.smile.model.domainecontratcompte.modificationdonneesclient.commande.CorrectionDonneesClientCmd;
import com.bna.smile.model.domainecontratcompte.modificationdonneesclient.model.ParamCorrectionDonneesClientVo;
import com.bna.smile.model.domainecontratcompte.souscriptioncontratcompte.commande.ChargerTypeCatPcePersonneCmd;
import com.bna.smile.model.domainecontratcompte.souscriptioncontratcompte.commande.GetListeContratsAmodifierCmd;
import com.bna.smile.model.domainecontratcompte.souscriptioncontratcompte.model.ParamListContratsAmodifierVo;
import com.bna.smile.model.domainecontratcompte.souscriptioncontratcompte.model.ParamPers;
import com.bna.smile.model.domainecontratcompte.souscriptioncontratcompte.model.TypeCatPers;
import com.bna.smile.model.reporting.model.CommonReportVO;
import com.bna.smile.web.commun.forms.CorrectionDonneesClientForm;
import com.bna.smile.web.commun.forms.ModificationDonneesClientForm;
import com.bna.smile.web.commun.model.ParamAgence;

import com.bna.smile.web.commun.util.SessionUtil;
import com.bna.smile.web.procuration.util.ContratCptView;

import java.io.IOException;

import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.actions.DispatchAction;
/**
 * Cette classe permet de données la main à faire les corrections (modification)
 * des données bloquantes.
 * @author Mdimagh Med Lassaad
 * @since 22/07/2008
 */
public class CorrectionDonneesClientAction extends DispatchAction{
    public CorrectionDonneesClientAction() {
    }
    
    public ActionForward initierPage(ActionMapping mapping, 
                                                       ActionForm form, 
                                                       HttpServletRequest request, 
                                                       HttpServletResponse response) throws IOException, 
                                                                                            ServletException {
        ParamAgence paramAgence = 
            (ParamAgence)request.getSession().getAttribute("paramAgBNA"); /// structure de l'agent qui fait la saisie
             ActionMessages actionMessages = new ActionMessages();
        CorrectionDonneesClientForm correctionDonneesClientForm = 
            (CorrectionDonneesClientForm)form;
        
        
        SessionUtil sessionUtil =new SessionUtil();
        //Suppression des anciens Bean de type Form de la session, SAUF "consultationContratCompteForm"
        sessionUtil.removeSession(request,"correctionDonneesClientForm");
        
        
     try{
         /*test sur l'etat du domaine*/
          StructureDomaine structureDomaine = new StructureDomaine(paramAgence.getCodStrcStrc(),Constants.COD_DOM_CLIENT);
          Boolean bool= SmileUtil.testDomaineOuvert(structureDomaine);
         correctionDonneesClientForm.clear();
         correctionDonneesClientForm.setDateActuelle(DateHandler.dateToStr(DateHandler.strToDate(paramAgence.getDateComptable())));
         return mapping.findForward("initierPage");
       
     }catch(Exception e ){
         com.oxia.fwk.core.Error erreur = new com.oxia.fwk.core.Error();
         StringBuffer text = 
             new StringBuffer("la transaction est Interrompu, une erreur dans CorrectionDonneesClientAction / disp :initierPage: ");
         text.append(e.toString());
         erreur.setCode("200");
         erreur.setDescription(text.toString());

         ActionMessage actionMessage = 
             new ActionMessage("exception.generique", 
                               erreur.getDescription());
         actionMessages.add("Erreur ", actionMessage);
         this.saveMessages(request, actionMessages);
         return mapping.findForward("error");
         
     }
   

    }

    public ActionForward rechercherPersonne(ActionMapping mapping, 
                                            ActionForm form, 
                                            HttpServletRequest request, 
                                            HttpServletResponse response) throws IOException, 
                                                                                 ServletException {
        CorrectionDonneesClientForm correctionDonneesClientForm = 
            (CorrectionDonneesClientForm)form;
                 correctionDonneesClientForm.setTestExistPersonne("");
        ParamAgence paramAgence = 
            (ParamAgence)request.getSession().getAttribute("paramAgBNA");

        ActionMessages actionMessages = new ActionMessages();
        try {
            //-------------------------------------------------------
            //------- recherche de la personne ----------------------
            //-------------------------------------------------------
            PersonneStrc personneStrc = new PersonneStrc();
            personneStrc.setCodTpceTpce(Long.valueOf(correctionDonneesClientForm.getTypePiece()));
            personneStrc.setNumPcePers(correctionDonneesClientForm.getNumeroPiece());
            
            GetPersonneCmd getPersonneCmd = new GetPersonneCmd();
            Personne personne = (Personne)getPersonneCmd.execute(personneStrc);
            //------- Fin de la recherche de la personne
            
            if (personne != null && personne.getNumSeqPers() != null ){
                
                correctionDonneesClientForm.setTestExistPersonne("1");
                correctionDonneesClientForm.setPersonne(personne);
                //---- Type de la persone 
                if (personne.getCategoriePersonne().getTypePers() != null){
                 correctionDonneesClientForm.setCodTperTper(personne.getCategoriePersonne().getTypePers().getCodTperTper());
                }
                
                if (correctionDonneesClientForm.getCodTperTper().equals(Constants.PERSPHYSIQUE)){
                 
                 if (personne.getNomNomPers() != null ){
                  correctionDonneesClientForm.setNomNomPers(personne.getNomNomPers());
                 }
                 if (personne.getNomPrnPers()!= null){
                  correctionDonneesClientForm.setNomPrnPers(personne.getNomPrnPers());
                 }
                 if (personne.getNomPrnpPers() != null ){
                  correctionDonneesClientForm.setNomPrnpPers(personne.getNomPrnpPers());
                 }
                 if(personne.getNomPrnPers()!=null){
                  correctionDonneesClientForm.setNomRaisonSociale(personne.getNomNomPers());
                 }
                 if (personne.getNomPrnPers()!=null){
                  correctionDonneesClientForm.setPrenomSigle(personne.getNomPrnPers());
                 }
                 
                }else if (correctionDonneesClientForm.getCodTperTper().equals(Constants.PERSMORALE)){
                    if(personne.getNomRsPers()!= null){
                    correctionDonneesClientForm.setNomRaisonSociale(personne.getNomRsPers());
                    }
                    if (personne.getNomPrnPers()!=null){
                    correctionDonneesClientForm.setPrenomSigle(personne.getLibSiglPers());
                    }
                }
                
                if(personne.getCategoriePersonne()!= null && personne.getCategoriePersonne().getCodCatpCatp() != null ){
                 correctionDonneesClientForm.setCodCatpCatp(personne.getCategoriePersonne().getCodCatpCatp());
                 correctionDonneesClientForm.setLibCtapCatp(personne.getCategoriePersonne().getLibCatpCatp());
                }
                               
                correctionDonneesClientForm.setNomRsPers(personne.getNomRsPers());
                //---- Activite
                if(personne.getActivite() != null && personne.getActivite().getActiviteId() != null && personne.getActivite().getActiviteId().getCodActAct() != null ){
                 correctionDonneesClientForm.setCodActAct(personne.getActivite().getActiviteId().getCodActAct());
                 correctionDonneesClientForm.setCodCactCact(personne.getActivite().getActiviteId().getCodCactCact());
                 correctionDonneesClientForm.setCodSactSact(personne.getActivite().getActiviteId().getCodSactSact().toString());
                 correctionDonneesClientForm.setLibActAct(personne.getActivite().getLibActAct());
                }
                //---- Profession
                if (correctionDonneesClientForm.getCodTperTper().equals(Constants.PERSPHYSIQUE)){
                    if(personne.getProfession() != null && personne.getProfession().getProfessionId() != null && personne.getProfession().getProfessionId().getCodProfProf() != null ){
                     correctionDonneesClientForm.setCodProfProf(personne.getProfession().getProfessionId().getCodProfProf().toString());
                     correctionDonneesClientForm.setCodGproGpro(personne.getProfession().getProfessionId().getCodGproGpro().toString());
                     correctionDonneesClientForm.setLibProfProf(personne.getProfession().getLibProfProf());
                    }
                }
                //---- Nationalité
                if (personne.getPaysByCodNat1Pays() != null && personne.getPaysByCodNat1Pays().getCodPaysPays() != null ){
                 correctionDonneesClientForm.setCodNat1Pays(personne.getPaysByCodNat1Pays().getCodPaysPays());
                 correctionDonneesClientForm.setLibNat1Pays(personne.getPaysByCodNat1Pays().getLibNatPays());
                }
                //---- Pays de naissance
                if (correctionDonneesClientForm.getCodTperTper().equals(Constants.PERSPHYSIQUE)){
                 if ((personne.getPaysByCodNaisPays() != null && personne.getPaysByCodNaisPays().getCodPaysPays() != null)){
                  correctionDonneesClientForm.setCodNaisPays(personne.getPaysByCodNaisPays().getCodPaysPays());
                  correctionDonneesClientForm.setLibNaisPays(personne.getPaysByCodNaisPays().getLibPaysPays());
                 }                
                 //---- Date de naissance 
                 if (personne.getDatNaisPers() != null ){
                 correctionDonneesClientForm.setDatNaisPers(DateHandler.dateToStr(personne.getDatNaisPers()));
                 }
                 //---- Lieu de naissance
                 if (personne.getLibNaisPers() != null ){
                 correctionDonneesClientForm.setLibNaisPers(personne.getLibNaisPers()); ;
                 }
                }
                //---- Identifiant
                correctionDonneesClientForm.setCodTpceTpce(personne.getTypePiece().getCodTpceTpce().toString());
                correctionDonneesClientForm.setLibTpceTpce(personne.getTypePiece().getLibTpceTpce());
                correctionDonneesClientForm.setNumPcePers(personne.getNumPcePers());
                
                //---- Forme juridique
               
                 
                if (personne.getBoolResPers() != null){
                  correctionDonneesClientForm.setBoolResPers(personne.getBoolResPers().toString());
                }
                 
                if (personne.getFormeJuridique() != null && personne.getFormeJuridique().getCodFjFj()!= null ){
                 correctionDonneesClientForm.setCodFjFj(personne.getFormeJuridique().getCodFjFj());
                 correctionDonneesClientForm.setLibelleFormeJuridique(personne.getFormeJuridique().getLibFjFj());
                }
                //---- délivrance pièce
                if (personne.getDatDlvPers() != null ){
                 correctionDonneesClientForm.setDatDlvPers(DateHandler.dateToStr(personne.getDatDlvPers()));
                }
                if (personne.getGouvernorat()!= null && personne.getGouvernorat().getLibGouvGouv() != null ){
                    correctionDonneesClientForm.setLibGouvGouv(personne.getGouvernorat().getLibGouvGouv());
                    correctionDonneesClientForm.setCodGouvGouv(personne.getGouvernorat().getCodGouvGouv().toString());
                }
                
                if (personne.getTribunal()!= null){
                   correctionDonneesClientForm.setLibTribTrib(personne.getTribunal().getLibTribTrib());
                   correctionDonneesClientForm.setCodTribTrib(personne.getTribunal().getCodTribTrib().toString());
                    
                }
                
                //----- chargement des categories et formes juridique
                 ChargerTypeCatPcePersonneCmd chargerTypeCatPcePersonneCmd = 
                     new ChargerTypeCatPcePersonneCmd();
                 TypeCatPers typeCatPersVo = new TypeCatPers();
                 ListTypeCatTpce listTypeCatTpce = 
                     new ListTypeCatTpce();
                 typeCatPersVo.setCodTperTper(personne.getCategoriePersonne().getTypePers().getCodTperTper());
                 listTypeCatTpce = 
                         (ListTypeCatTpce)chargerTypeCatPcePersonneCmd.execute(typeCatPersVo);

                 if (listTypeCatTpce.getListCatPers() != null) {
                     correctionDonneesClientForm.setListeCategoriePersonne(listTypeCatTpce.getListCatPers());
                 }
                 
                 correctionDonneesClientForm.setNouvelleCodCatpCatp(personne.getCategoriePersonne().getCodCatpCatp());
                 
                 FormeJuridique fm =new FormeJuridique();
                
                //--------------------------------------------------------------//
                //---- forme juridique d'une personne physique -----------------//
                if (personne.getCategoriePersonne().getTypePers().getCodTperTper().equals(Constants.PERSPHYSIQUE)){
                   
                    if (personne.getCategoriePersonne().getCodCatpCatp().equals(Constants.COD_CATEGORIE_PHY_TUN_MAJ) ||
                        personne.getCategoriePersonne().getCodCatpCatp().equals(Constants.COD_CATEGORIE_P_TUN_INC ) ||
                        personne.getCategoriePersonne().getCodCatpCatp().equals(Constants.COD_CATEGORIE_MINEUR) ||
                        personne.getCategoriePersonne().getCodCatpCatp().equals(Constants.COD_CATEGORIE_P_MIN_EMANCIPE) ){
                              
                        fm.setCodFjFj(Constants.COD_FORME_JURI_PERS_PHYS_TUNISIENNE);
                        fm.setLibFjFj("Personne Physique Tunisienne");
                        correctionDonneesClientForm.getListeFormeJuridique().add(fm);
                    }else {
                        fm.setCodFjFj(Constants.COD_FORME_JURI_PERS_PHYS_ETRANGERE);
                        fm.setLibFjFj("Personne Physique Etrangere");
                        correctionDonneesClientForm.getListeFormeJuridique().add(fm);
                    }
                    
                }else {
                
                    //--------------------------------------------------------------//
                    //---- forme juridique d'une personne morale -----------------//
                    fm.setCodFjFj(personne.getFormeJuridique().getCodFjFj());
                    fm.setLibFjFj(personne.getFormeJuridique().getLibFjFj());
                    correctionDonneesClientForm.getListeFormeJuridique().add(fm);
                    
                }
                
                //-------------------------
                 if (personne.getAdresseResid() != null) {
                     // Immeuble
                     if (personne.getAdresseResid().getImmeuble() != null) {
                         correctionDonneesClientForm.setImmeubleRes(personne.getAdresseResid().getImmeuble());
                     }
                     // Rue
                     if (personne.getAdresseResid().getRue() != null) {
                         correctionDonneesClientForm.setRueRes(personne.getAdresseResid().getRue());
                     }

                     // Cite
                     if (personne.getAdresseResid().getCite() != null) {
                         correctionDonneesClientForm.setCiteRes(personne.getAdresseResid().getCite());
                     }


                     // Pays 
                     if (personne.getAdresseResid().getCodPaysPays() != 
                         null) {
                         correctionDonneesClientForm.setCodPaysPaysRes(personne.getAdresseResid().getCodPaysPays());
                         GetPaysCmd getPaysCmd = new GetPaysCmd();
                         Pays pays = new Pays();
                         pays.setCodPaysPays(personne.getAdresseResid().getCodPaysPays());
                         pays = (Pays)getPaysCmd.execute(pays);
                         if (pays.getLibPaysPays() != null) {
                             correctionDonneesClientForm.setLibPaysPaysRes(pays.getLibPaysPays());
                         }
                     }

                     // Code Postal
                     if (personne.getAdresseResid().getCodCpCp() != null) {
                         correctionDonneesClientForm.setCodCpCpRes(personne.getAdresseResid().getCodCpCp());
                         // si le pays est la tunisie extraire le libelle du code postal
                         if ((personne.getAdresseResid().getCodPaysPays() != 
                              null) && 
                             (personne.getAdresseResid().getCodPaysPays().equals(Constants.COD_PAYS_TUNISIE))) {
                             GetCodePostalCmd getCodePostalCmd = 
                                 new GetCodePostalCmd();
                             CodePostal codePostal = new CodePostal();
                             codePostal.setCodCpCp(Long.valueOf(personne.getAdresseResid().getCodCpCp()));
                             codePostal = 
                                     (CodePostal)getCodePostalCmd.execute(codePostal);
                             correctionDonneesClientForm.setLibCpCpRes(codePostal.getLibCpCp());

                             // gouvernerat
                             correctionDonneesClientForm.setCodGouvGouvRes(codePostal.getGouvernorat().getCodGouvGouv().toString());
                             correctionDonneesClientForm.setLibGouvGouvRes(codePostal.getGouvernorat().getLibGouvGouv());


                         }
                     }
                 } // Fin adresse de résidence

                 //------------------------------------------------------//
                 //------------- adresse professionnelle------------------//
                 if (personne.getAdresseProf() != null) {
                     // Immeuble
                     if (personne.getAdresseProf().getImmeuble() != null) {
                         correctionDonneesClientForm.setImmeubleProf(personne.getAdresseProf().getImmeuble());
                     }
                     // Rue
                     if (personne.getAdresseProf().getRue() != null) {
                         correctionDonneesClientForm.setRueProf(personne.getAdresseProf().getRue());
                     }

                     // Cite
                     if (personne.getAdresseProf().getCite() != null) {
                         correctionDonneesClientForm.setCiteProf(personne.getAdresseProf().getCite());
                     }

                     // Ville
                     if (personne.getAdresseProf().getVille() != null) {
                         correctionDonneesClientForm.setVilleProf(personne.getAdresseProf().getVille());
                     }

                     // Pays 
                     if (personne.getAdresseProf().getCodPaysPays() != 
                         null) {
                         correctionDonneesClientForm.setCodPaysPaysProf(personne.getAdresseProf().getCodPaysPays());
                         GetPaysCmd getPaysCmd = new GetPaysCmd();
                         Pays pays = new Pays();
                         pays.setCodPaysPays(personne.getAdresseProf().getCodPaysPays());
                         pays = (Pays)getPaysCmd.execute(pays);
                         if (pays.getLibPaysPays() != null) {
                             correctionDonneesClientForm.setLibPaysPaysProf(pays.getLibPaysPays());
                         }
                     }

                     // Code Postal
                     if (personne.getAdresseProf().getCodCpCp() != null) {
                         correctionDonneesClientForm.setCodCpCpProf(personne.getAdresseProf().getCodCpCp());
                         // si le pays est la tunisie extraire le libelle du code postal
                         if (personne.getAdresseProf().getCodPaysPays().equals(Constants.COD_PAYS_TUNISIE)) {
                             GetCodePostalCmd getCodePostalCmd = 
                                 new GetCodePostalCmd();
                             CodePostal codePostal = new CodePostal();
                             codePostal.setCodCpCp(Long.valueOf(personne.getAdresseProf().getCodCpCp()));
                             codePostal = 
                                     (CodePostal)getCodePostalCmd.execute(codePostal);
                             correctionDonneesClientForm.setLibCpCpProf(codePostal.getLibCpCp());

                             // gouvernerat
                             correctionDonneesClientForm.setCodGouvGouvProf(codePostal.getGouvernorat().getCodGouvGouv().toString());
                             correctionDonneesClientForm.setLibGouvGouvProf(codePostal.getGouvernorat().getLibGouvGouv());

                         }
                     }
                 } // Fin adresse professionnelle
                
            }else {
                correctionDonneesClientForm.setTestExistPersonne("0");
            }
              return mapping.findForward("pageCorrectionDonnees");
              
          } catch (Exception e) {
                 System.out.println(e.toString());
                 com.oxia.fwk.core.Error erreur = new com.oxia.fwk.core.Error();
                 StringBuffer text = 
                     new StringBuffer("la transaction est Interrompu, une erreur dans CorrectionDonneesClientAction / disp :RecherchePersonne: ");
                 text.append(e.toString());
                 erreur.setCode("200");
                 erreur.setDescription(text.toString());

                 ActionMessage actionMessage = 
                     new ActionMessage("exception.generique", 
                                       erreur.getDescription());
                 actionMessages.add("Erreur ", actionMessage);
                 this.saveMessages(request, actionMessages);
                 return mapping.findForward("error");
             }
             }


    public ActionForward modifierPersonne(ActionMapping mapping, 
                                            ActionForm form, 
                                            HttpServletRequest request, 
                                            HttpServletResponse response) throws IOException, 
                                                                                 ServletException {
        CorrectionDonneesClientForm correctionForm = 
            (CorrectionDonneesClientForm)form;
                 
        ParamAgence paramAgence = 
            (ParamAgence)request.getSession().getAttribute("paramAgBNA");

        ActionMessages actionMessages = new ActionMessages();
        try {
          
         CorrectionDonneesClientCmd correctionCmd = new CorrectionDonneesClientCmd();
        
         ParamCorrectionDonneesClientVo paramCorrection  = new ParamCorrectionDonneesClientVo();
         paramCorrection.setCodeStructure(paramAgence.getCodStrcStrc());
         paramCorrection.setMatricule(Long.valueOf(paramAgence.getNumMatrUser()));
         
         Personne personne = new Personne();
         personne.setNumSeqPers(correctionForm.getPersonne().getNumSeqPers());
         personne.setNomNomPers(correctionForm.getNomNomPers());
         personne.setNomPrnPers(correctionForm.getNomPrnPers());
         personne.setNomPrnpPers(correctionForm.getNomPrnpPers());
         personne.setNomRsPers(correctionForm.getNomRsPers());
        
         personne.setDatDlvPers(DateHandler.strToDate(correctionForm.getDatDlvPers()));
         Gouvernorat gouv = new Gouvernorat();
         if (correctionForm.getCodGouvGouv() != null && !correctionForm.getCodGouvGouv().equals("")){
          gouv.setCodGouvGouv(Long.valueOf(correctionForm.getCodGouvGouv()));
          personne.setGouvernorat(gouv);
         }
         
         
         Activite act = new Activite();
         ActiviteId actId = new ActiviteId();
         if (correctionForm.getCodCactCact()!=null){
          actId.setCodCactCact(correctionForm.getCodCactCact());
         }
         if (correctionForm.getCodSactSact() != null){
          actId.setCodSactSact(Long.valueOf(correctionForm.getCodSactSact()));
         }
         if (correctionForm.getCodActAct() != null){
          actId.setCodActAct(correctionForm.getCodActAct());
         }
         act.setActiviteId(actId);
         personne.setActivite(act);
         
         if (correctionForm.getCodTperTper().equals(Constants.PERSPHYSIQUE)){
          Profession prof = new Profession();
          ProfessionId profId = new ProfessionId();
          if (correctionForm.getCodGproGpro()!= null && correctionForm.getCodProfProf() != null){
           profId.setCodGproGpro(Long.valueOf(correctionForm.getCodGproGpro()));
           profId.setCodProfProf(Long.valueOf(correctionForm.getCodProfProf()));
           prof.setProfessionId(profId);
           personne.setProfession(prof);
          }
         }
         
         Pays paysNationalite = new Pays();
         if(correctionForm.getCodNat1Pays() != null){
          paysNationalite.setCodPaysPays(correctionForm.getCodNat1Pays());
         }
         personne.setPaysByCodNat1Pays(paysNationalite);
         
         if (correctionForm.getCodTperTper().equals(Constants.PERSPHYSIQUE)){
          Pays paysNaissance = new Pays();
          paysNaissance.setCodPaysPays(correctionForm.getCodNaisPays());
          personne.setPaysByCodNaisPays(paysNaissance);
         
          personne.setDatNaisPers(DateHandler.strToDate(correctionForm.getDatNaisPers()));
          personne.setLibNaisPers(correctionForm.getLibNaisPers());
         } 
         
         //--residence
         personne.setBoolResPers(Long.valueOf(correctionForm.getBoolResPers()));
         
         //--categorie et forme juridique
         CategoriePersonne ct = new CategoriePersonne();
         ct.setCodCatpCatp(correctionForm.getNouvelleCodCatpCatp());   
         personne.setCategoriePersonne(ct);
         
         FormeJuridique fj = new FormeJuridique();
         fj.setCodFjFj(correctionForm.getNouvellecodFjFj());
         personne.setFormeJuridique(fj);
         
         
         //-----------------------------------------------------------------------------//
         //------------ adresse résidence & professionelle -----------------------------//
            Adresse adrResidence = new Adresse();
            adrResidence.setImmeuble(correctionForm.getImmeubleRes());
            adrResidence.setRue(correctionForm.getRueRes());
            adrResidence.setVille(correctionForm.getVilleRes());
            adrResidence.setCite(correctionForm.getCiteRes());
            adrResidence.setCodPaysPays(correctionForm.getCodPaysPaysRes());
            adrResidence.setCodCpCp(correctionForm.getCodCpCpRes());
            personne.setAdresseResid(adrResidence);
            
            Adresse adrProf = new Adresse();
            if (correctionForm.getImmeubleProf() != null) {
                adrProf.setImmeuble(correctionForm.getImmeubleProf());
            }
            if (correctionForm.getRueProf() != null) {
                adrProf.setRue(correctionForm.getRueProf());
            }
    
            if (correctionForm.getVilleProf() != null) {
                adrProf.setVille(correctionForm.getVilleProf());
            }
    
            if (correctionForm.getCiteProf() != null) {
                adrProf.setCite(correctionForm.getCiteProf());
            }
    
            if (correctionForm.getCodPaysPaysProf() != 
                null) {
                adrProf.setCodPaysPays(correctionForm.getCodPaysPaysProf());
            }
    
            if (correctionForm.getCodCpCpProf() != null) {
                adrProf.setCodCpCp(correctionForm.getCodCpCpProf());
            }
            personne.setAdresseProf(adrProf);
            
            
         //--------------------------------------------------------//
         //------- Fin adresse ------------------------------------//
        
        
        //-------------------------------------------------------------//
        //-------- Lancer la modification 
         paramCorrection.setPersonneModifie(personne);
         
         paramCorrection = (ParamCorrectionDonneesClientVo) correctionCmd.execute(paramCorrection);
         
         if (paramCorrection.hasError()){
             List listErreur = paramCorrection.getErrors();                    
             for (Iterator it = listErreur.iterator(); it.hasNext(); ) {
                 com.oxia.fwk.core.Error erreur = (com.oxia.fwk.core.Error)it.next();
                 ActionMessage actionMessage = new ActionMessage("exception.generique",erreur.getDescription());
                 actionMessages.add("Erreur ", actionMessage);
              }    
              this.saveMessages(request, actionMessages);
              return mapping.findForward("error");    
             
         }else{
             //-------------------------------------------------------------------//
             //------ Affichage de message de confirmation
             
             correctionForm.setLibelleConfirmation("Les modifications effectuées sont : ");
             
             StringBuffer libelle1 = new StringBuffer();
             libelle1.append(" Avant modification : ");
             if (correctionForm.getCodTperTper().equals(Constants.PERSPHYSIQUE)){
              libelle1.append(" Nom : ");
              if (correctionForm.getPersonne().getNomNomPers() != null){
               libelle1.append(correctionForm.getPersonne().getNomNomPers());
              }
              libelle1.append(" # Prénom : ");
              if (correctionForm.getPersonne().getNomPrnPers() != null){
               libelle1.append(correctionForm.getPersonne().getNomPrnPers());
              }
              libelle1.append(" # Prénom du père : ");
              if (correctionForm.getPersonne().getNomPrnpPers() != null){
              libelle1.append(correctionForm.getPersonne().getNomPrnpPers());
              }
             }else{
              libelle1.append(" # Raison sociale : ");
              if(correctionForm.getPersonne().getNomRsPers() != null){
               libelle1.append(correctionForm.getPersonne().getNomRsPers()); 
              }
             }
             libelle1.append(" # Identifiant : ");
             libelle1.append(correctionForm.getPersonne().getTypePiece().getLibTpceTpce()); 
             libelle1.append(" # Numéro : ");
             libelle1.append(correctionForm.getPersonne().getNumPcePers()); 
             libelle1.append(" # Date délivrance : ");
             if (correctionForm.getPersonne().getDatDlvPers() != null ){
              libelle1.append(DateHandler.dateToStr(correctionForm.getPersonne().getDatDlvPers()) ); 
             }
             
             libelle1.append(" # Lieu de délivrance : ");
             if (correctionForm.getPersonne().getGouvernorat() != null ){
              libelle1.append(correctionForm.getPersonne().getGouvernorat().getLibGouvGouv()); 
             }
             if (correctionForm.getPersonne().getTribunal() != null ){
              libelle1.append(correctionForm.getPersonne().getTribunal().getLibTribTrib()); 
             }
             
             libelle1.append(" # Activité : ");
             if(correctionForm.getPersonne().getActivite() != null && correctionForm.getPersonne().getActivite().getLibActAct() != null){
              libelle1.append(correctionForm.getPersonne().getActivite().getLibActAct() ); 
             }
             if (correctionForm.getCodTperTper().equals(Constants.PERSPHYSIQUE)){
              libelle1.append(" # Profession : ");
              if(correctionForm.getPersonne().getProfession()!=null && correctionForm.getPersonne().getProfession().getLibProfProf() != null){
              libelle1.append(correctionForm.getPersonne().getProfession().getLibProfProf()); 
              }
             } 
             
             libelle1.append(" # Nationalité : ");
             if (correctionForm.getPersonne().getPaysByCodNat1Pays() != null && correctionForm.getPersonne().getPaysByCodNat1Pays().getLibNatPays() !=null){
              libelle1.append(correctionForm.getPersonne().getPaysByCodNat1Pays().getLibNatPays()); 
             }
             libelle1.append(" # Résidence : ");
             if (correctionForm.getPersonne().getBoolResPers().equals(Long.valueOf("1"))){
              libelle1.append("Résident"); 
             }else{
              libelle1.append("Non résident");   
             }
             
             if (correctionForm.getCodTperTper().equals(Constants.PERSPHYSIQUE)){
              libelle1.append(" # Pays de naissance : ");
              if(correctionForm.getPersonne().getPaysByCodNaisPays() != null && correctionForm.getPersonne().getPaysByCodNaisPays().getLibPaysPays() !=null ){
               libelle1.append(correctionForm.getPersonne().getPaysByCodNaisPays().getLibPaysPays()); 
              }
              libelle1.append(" # Date de naissance : ");
              if (correctionForm.getPersonne().getDatNaisPers() != null){
               libelle1.append(DateHandler.dateToStr(correctionForm.getPersonne().getDatNaisPers())); 
              }
             
              libelle1.append(" # Lieu de naissance : ");
              if (correctionForm.getPersonne().getLibNaisPers() != null){
               libelle1.append(correctionForm.getPersonne().getLibNaisPers()); 
              }
             }
             
             libelle1.append(" # Catégorie : ");
             libelle1.append(correctionForm.getPersonne().getCategoriePersonne().getLibCatpCatp() ); 
             
             
             libelle1.append(" # Forme juridique : ");
             if (correctionForm.getPersonne().getFormeJuridique() != null && correctionForm.getPersonne().getFormeJuridique().getLibFjFj() != null ){
             libelle1.append(correctionForm.getPersonne().getFormeJuridique().getLibFjFj()); 
             }
             
             
             //----------- Ancienne Adresse résidence-------------------------------------//
             if (correctionForm.getPersonne() !=null ){
              if (correctionForm.getCodTperTper().equals(Constants.PERSPHYSIQUE)){
               if( correctionForm.getPersonne().getAdresseResid() != null){
                 libelle1.append(" //*  Adresse de résidence : *// ");
                 if (correctionForm.getPersonne().getAdresseResid().getImmeuble() != null ) {
                     libelle1.append(" Immeuble : "+ correctionForm.getPersonne().getAdresseResid().getImmeuble()+"; ");
                 }
                 if (correctionForm.getPersonne().getAdresseResid().getRue()  != null ) {
                     libelle1.append(" Rue : "+ correctionForm.getPersonne().getAdresseResid().getRue()+"; ");
                 }
                 if (correctionForm.getPersonne().getAdresseResid().getCite()  != null ) {
                     libelle1.append(" Cité : "+ correctionForm.getPersonne().getAdresseResid().getCite()+"; ");
                 }
                 if (correctionForm.getPersonne().getAdresseResid().getCodCpCp()  != null ) {
                     libelle1.append(" Code Postal : "+ correctionForm.getPersonne().getAdresseResid().getCodCpCp()+"; ");
                 }
                 if (correctionForm.getPersonne().getAdresseResid().getCodPaysPays()  != null ) {
                     Pays pays = new Pays();
                     pays.setCodPaysPays(correctionForm.getPersonne().getAdresseResid().getCodPaysPays() );
                     GetPaysCmd getPaysCmd = new GetPaysCmd();
                     pays = (Pays) getPaysCmd.execute(pays);
                     if (pays.getLibPaysPays() != null ){
                      libelle1.append(" Pays : "+ pays.getLibPaysPays()+"; ");
                     }
                 }
               }//si @de residence est not null
              }// fin personne physique   
              else {
                  if( correctionForm.getPersonne().getAdresseProf() != null){
                    libelle1.append(" //*  Adresse professionnelle : *// ");
                    if (correctionForm.getPersonne().getAdresseProf().getImmeuble() != null ) {
                        libelle1.append(" Immeuble : "+ correctionForm.getPersonne().getAdresseProf().getImmeuble()+"; ");
                    }
                    if (correctionForm.getPersonne().getAdresseProf().getRue()  != null ) {
                        libelle1.append(" Rue : "+ correctionForm.getPersonne().getAdresseProf().getRue()+"; ");
                    }
                    if (correctionForm.getPersonne().getAdresseProf().getCite()  != null ) {
                        libelle1.append(" Cité : "+ correctionForm.getPersonne().getAdresseProf().getCite()+"; ");
                    }
                    if (correctionForm.getPersonne().getAdresseProf().getCodCpCp()  != null ) {
                        libelle1.append(" Code Postal : "+ correctionForm.getPersonne().getAdresseProf().getCodCpCp()+"; ");
                    }
                    if (correctionForm.getPersonne().getAdresseProf().getCodPaysPays()  != null ) {
                        Pays pays = new Pays();
                        pays.setCodPaysPays(correctionForm.getPersonne().getAdresseProf().getCodPaysPays() );
                        GetPaysCmd getPaysCmd = new GetPaysCmd();
                        pays = (Pays) getPaysCmd.execute(pays);
                        if (pays.getLibPaysPays() != null ){
                         libelle1.append(" Pays : "+ pays.getLibPaysPays()+"; ");
                        }
                    }
                  }//si @de residence est not null
                  
              }
             } // fin if adrese res
             
             
             correctionForm.setLibelleConfirmation1(libelle1.toString());
             
             StringBuffer libelle2 = new StringBuffer();
             libelle2.append(" Aprés modification : ");
             if (correctionForm.getCodTperTper().equals(Constants.PERSPHYSIQUE)){
              libelle2.append(" Nom : ");
              libelle2.append(correctionForm.getNomNomPers());
              libelle2.append(" # Prénom : ");
              libelle2.append(correctionForm.getNomPrnPers());
              libelle2.append(" # Prénom père : ");
              libelle2.append(correctionForm.getNomPrnpPers());
             }else {
                 libelle2.append(" # Raison sociale : ");
                 libelle2.append(correctionForm.getNomRsPers());
             }
              
             libelle2.append(" # Identifiant : ");
             libelle2.append(correctionForm.getPersonne().getTypePiece().getLibTpceTpce()); 
             libelle2.append(" # Numéro : ");
             libelle2.append(correctionForm.getPersonne().getNumPcePers()); 
             libelle2.append(" # Date délivrance : ");
             libelle2.append(correctionForm.getDatDlvPers() ); 
             libelle2.append(" # Lieu de délivrance : ");
             if (correctionForm.getLibGouvGouv() != null && ! correctionForm.getLibGouvGouv().equals("") ){
             libelle2.append(correctionForm.getLibGouvGouv());
             }else if (correctionForm.getLibTribTrib() != null && !correctionForm.getLibTribTrib().equals("")){
             libelle2.append(correctionForm.getLibTribTrib());    
             }
                                                                                 
             libelle2.append(" # Activité : ");
             libelle2.append(correctionForm.getLibActAct() ); 
             if (correctionForm.getCodTperTper().equals(Constants.PERSPHYSIQUE)){
              libelle2.append(" # Profession : ");
              libelle2.append(correctionForm.getLibProfProf()); 
             }
             libelle2.append(" # Nationalité : ");
             libelle2.append(correctionForm.getLibNat1Pays() ); 
             
             libelle2.append(" # Résidence : ");
             if (correctionForm.getBoolResPers().equals(Long.valueOf("1"))){
              libelle2.append("Résident"); 
             }else{
              libelle2.append("Non résident");   
             }
             
             if (correctionForm.getCodTperTper().equals(Constants.PERSPHYSIQUE)){
              libelle2.append(" # Pays de naissance : ");
              libelle2.append(correctionForm.getLibNaisPays() ); 
             
              libelle2.append(" # Date de naissance : ");
              libelle2.append(correctionForm.getDatNaisPers()); 
             
              libelle2.append(" # Lieu de naissance : ");
              libelle2.append(correctionForm.getLibNaisPers()); 
             }
             
             libelle2.append(" # Catégorie : ");
             
             GetCategoriePersonneCmd getCategoriePersonneCmd = new GetCategoriePersonneCmd();
             CategoriePersonne       newCetegoriePersonne = new CategoriePersonne();
             newCetegoriePersonne.setCodCatpCatp(correctionForm.getNouvelleCodCatpCatp());
             newCetegoriePersonne = (CategoriePersonne)getCategoriePersonneCmd.execute(newCetegoriePersonne);
             libelle2.append(newCetegoriePersonne.getLibCatpCatp() ); 
             
             libelle2.append(" # Forme juridique : ");
             GetFormeJuridiqueCmd    getFormeJuridique  = new GetFormeJuridiqueCmd();
             FormeJuridique          newFormJuridique  = new FormeJuridique();
             newFormJuridique.setCodFjFj (correctionForm.getNouvellecodFjFj() );
             newFormJuridique = (FormeJuridique) getFormeJuridique.execute(newFormJuridique);
             libelle2.append(newFormJuridique.getLibFjFj()); 
             
             //----------- Nouvelle Adresse résidence-------------------------------------//
             if (correctionForm.getPersonne() !=null ){
               if (correctionForm.getCodTperTper().equals(Constants.PERSPHYSIQUE)){
                 libelle2.append(" //*  Adresse de résidence : *// ");
                 if (correctionForm.getImmeubleRes() != null ) {
                     libelle2.append(" Immeuble : "+ correctionForm.getImmeubleRes()+"; ");
                 }
                 if (correctionForm.getRueRes()  != null ) {
                     libelle2.append(" Rue : "+ correctionForm.getRueRes()+"; ");
                 }
                 if (correctionForm.getCiteRes()  != null ) {
                     libelle2.append(" Cité : "+ correctionForm.getCiteRes()+"; ");
                 }
                 if (correctionForm.getCodCpCpRes()  != null ) {
                     libelle2.append(" Code Postal : "+ correctionForm.getCodCpCpRes()+"; ");
                 }
                 if (correctionForm.getCodPaysPaysRes()  != null ) {
                     Pays pays = new Pays();
                     pays.setCodPaysPays(correctionForm.getCodPaysPaysRes() );
                     GetPaysCmd getPaysCmd = new GetPaysCmd();
                     pays = (Pays) getPaysCmd.execute(pays);
                     if (pays.getLibPaysPays() != null ){
                      libelle2.append(" Pays : "+ pays.getLibPaysPays()+"; ");
                     }
                 }
              }// fin personne physique
              else {
                  libelle2.append(" //*  Adresse professionnelle : *// ");
                  if (correctionForm.getImmeubleProf()  != null ) {
                      libelle2.append(" Immeuble : "+ correctionForm.getImmeubleProf()+"; ");
                  }
                  if (correctionForm.getRueProf()  != null ) {
                      libelle2.append(" Rue : "+ correctionForm.getRueProf()+"; ");
                  }
                  if (correctionForm.getCiteProf()  != null ) {
                      libelle2.append(" Cité : "+ correctionForm.getCiteProf()+"; ");
                  }
                  if (correctionForm.getCodCpCpProf()  != null ) {
                      libelle2.append(" Code Postal : "+ correctionForm.getCodCpCpProf()+"; ");
                  }
                  if (correctionForm.getCodPaysPaysProf()  != null ) {
                      Pays pays = new Pays();
                      pays.setCodPaysPays(correctionForm.getCodPaysPaysProf() );
                      GetPaysCmd getPaysCmd = new GetPaysCmd();
                      pays = (Pays) getPaysCmd.execute(pays);
                      if (pays.getLibPaysPays() != null ){
                       libelle2.append(" Pays : "+ pays.getLibPaysPays()+"; ");
                      }
                  }
              }
              } // fin if adrese res
             correctionForm.setLibelleConfirmation2(libelle2.toString());
             
             verifListeContratAmodifier(correctionForm);
             
            return mapping.findForward("pageConfirmation");
         }
    
    } catch (Exception e) {
       System.out.println(e.toString());
       com.oxia.fwk.core.Error erreur = new com.oxia.fwk.core.Error();
       StringBuffer text = 
           new StringBuffer("la transaction est Interrompu, une erreur dans CorrectionDonneesClientAction / disp :modifierPersonne: ");
       text.append(e.toString());
       erreur.setCode("200");
       erreur.setDescription(text.toString());

       ActionMessage actionMessage = 
           new ActionMessage("exception.generique", 
                             erreur.getDescription());
       actionMessages.add("Erreur ", actionMessage);
       this.saveMessages(request, actionMessages);
       return mapping.findForward("error");
    }
    }
    
  
   public ActionForward printModification(ActionMapping mapping, ActionForm form, 
                                    HttpServletRequest request, 
                                    HttpServletResponse response) throws IOException, 
                                                                         ServletException
                                                                    {
                                                                    
           CorrectionDonneesClientForm correctionDonneesClientForm = 
               (CorrectionDonneesClientForm)form;
                         try {
                                   CommonReportVO valueObject = new CommonReportVO();
                                   ParamAgence paramAgence = new ParamAgence();
                                   paramAgence =
                                   (ParamAgence)request.getSession().getAttribute("paramAgBNA");
                                   Map parameters = new HashMap();
                                   String pMODIF1 = "MODIF1";
                                   String pMODIF2 = "MODIF2";
                                   String pMODIF3 = "MODIF3";
                                   String pLibEtat = "P_LIB_ETAT";
                                   String pMatrUser = "P_NUM_MATR_USER";
                                   String pLogo = "P_PATH";
                                   
                                   String pCcpt = "CCPT";
                                   String vCcpt ="";
                                   StringBuffer str = new StringBuffer();
                                   List listCcpt = correctionDonneesClientForm.getListDesContratAmodifier();
                                   for (Iterator it =  listCcpt.iterator(); it.hasNext(); ) {
                                        ContratCptView contratCptView = (ContratCptView)it.next();
                                          str.append(contratCptView.getCodeAgence()); str.append(" ");
                                          str.append(contratCptView.getCodeProduit()); str.append(" ");
                                          str = str.append(contratCptView.getNumeroCompte());
                                          str.append("\n");
                                      }
                                   vCcpt = str.toString();
                                   parameters.put(pCcpt, vCcpt);
                                   String vLibEtat = "Correction des données client";
                                   String vMatrUser = paramAgence.getNumMatrUser().toString();
                                   String vLogo = getServlet().getServletContext().getRealPath("")+ "\\reporting\\";
                                   String vMODIF1 = correctionDonneesClientForm.getLibelleConfirmation();
                                   String vMODIF2 = correctionDonneesClientForm.getLibelleConfirmation1();
                                   String vMODIF3 = correctionDonneesClientForm.getLibelleConfirmation2();
                                   parameters.put(pMatrUser, vMatrUser);
                                   parameters.put(pLibEtat, vLibEtat);
                                   parameters.put(pMODIF1, vMODIF1);
                                   parameters.put(pMODIF2, vMODIF2);
                                   parameters.put(pMODIF3, vMODIF3);
                                   parameters.put(pLogo, vLogo);
                                  
                                  valueObject.setParams(parameters);
                                  valueObject.setNomReport("EtatModification");
                                //  valueObject.setList(modificationDonneesClientForm.getListDesContratAmodifier());
                                //  valueObject.setRootFolder(getServlet().getServletContext().getRealPath("")+ "\\reporting\\");
                                //  PrinterCmd printer = new PrinterCmd();

                              //    valueObject = (CommonReportVO) printer.execute(valueObject);
                                  request.getSession().setAttribute("CommonPrintVo",valueObject);
                                  request.setAttribute("print","1");
                                  
                                  response.setContentType("application/pdf");
                                  response.setContentLength(valueObject.getContent().length);
                                  ServletOutputStream ouputStream = response.getOutputStream();
                                  ouputStream.write(valueObject.getContent(), 0, valueObject.getContent().length);
                                  ouputStream.flush();
                                  ouputStream.close();
                          } catch (Exception e) {
                               
                                  e.printStackTrace();
                          }
                    return mapping.findForward("pageConfirmation");
                }
                
                
    public ActionForward chargerFormeJuridique(ActionMapping mapping, 
                                            ActionForm form, 
                                            HttpServletRequest request, 
                                            HttpServletResponse response) throws IOException, 
                                                                                 ServletException {
        CorrectionDonneesClientForm correctionDonneesClientForm = 
            (CorrectionDonneesClientForm)form;
        
        ChargerTypeCatPcePersonneCmd chargerTypeCatPcePersonneCmd = 
            new ChargerTypeCatPcePersonneCmd();
        TypeCatPers typeCatPersVo = new TypeCatPers();
        ListTypeCatTpce listTypeCatTpce = new ListTypeCatTpce();
        
        typeCatPersVo.setCodTperTper(correctionDonneesClientForm.getCodTperTper()); 
        typeCatPersVo.setCodCatpCatp(correctionDonneesClientForm.getNouvelleCodCatpCatp());
        
        listTypeCatTpce = 
                (ListTypeCatTpce)chargerTypeCatPcePersonneCmd.execute(typeCatPersVo);
                
       correctionDonneesClientForm.setListeFormeJuridique(listTypeCatTpce.getListeCategp_Formj());
       
        return mapping.findForward("pageCorrectionDonnees");    
    }
    
    private void verifListeContratAmodifier(CorrectionDonneesClientForm correctionDonneesClientForm) throws Exception {
        //------------------------------------------------------------------------------//
        //--------------- verifier les contrats de la personne --------------------------//
        //------------------------------------------------------------------------------//
        correctionDonneesClientForm.getListDesContratAmodifier().clear();
        GetListeContratsAmodifierCmd getListeContratsAmodifierCmd =   new GetListeContratsAmodifierCmd();
        Long age = Long.valueOf("0");
        String categorie = "";
        
        if (!correctionDonneesClientForm.getCodTperTper().equals(Constants.PERSMORALE)) {
            age = getAge(DateHandler.strToDate(correctionDonneesClientForm.getDatNaisPers()));
        }
            categorie = correctionDonneesClientForm.getNouvelleCodCatpCatp();
            
        
        String codePays  = correctionDonneesClientForm.getCodNat1Pays();
        String residence = correctionDonneesClientForm.getBoolResPers();
        String formeJuridique = correctionDonneesClientForm.getNouvellecodFjFj();
        
        
        String codeTypePiece = correctionDonneesClientForm.getTypePiece();
        String numeroPiece   = correctionDonneesClientForm.getNumeroPiece();

        ParamPers paramPers = new ParamPers();
        paramPers.setAge(age.intValue());
        paramPers.setBoolResPers(Integer.valueOf(residence));
        paramPers.setCodPaysPays(codePays);
        paramPers.setCodFjFj(formeJuridique);
        paramPers.setCodCatpCatp(categorie);

        ParamListContratsAmodifierVo paramListVo =  new ParamListContratsAmodifierVo();

        paramListVo.setParampers(paramPers);

        PersonneStrc personneStrc = new PersonneStrc();
        personneStrc.setCodTpceTpce(Long.valueOf(codeTypePiece));
        personneStrc.setNumPcePers(numeroPiece);

        paramListVo.setPersonneStrc(personneStrc);
        paramListVo = (ParamListContratsAmodifierVo)getListeContratsAmodifierCmd.execute(paramListVo);
       
        if (paramListVo.getListContratAmodifier() != null && paramListVo.getListContratAmodifier().size() > 0) {
            for (Iterator it =  paramListVo.getListContratAmodifier().iterator(); it.hasNext(); ) {
                ContratCptView contratCptView = new ContratCptView();
                ContratCpt contratCpt = (ContratCpt)it.next();
                contratCptView.setNumeroCompte(StrHandler.lpad(contratCpt.getContratCptId().getNumCcptCcpt().toString(), 
                                                               '0', 6));
                contratCptView.setCodeAgence(StrHandler.lpad(contratCpt.getContratCptId().getCodStrcStrc().toString(), 
                                                             '0', 3));
                contratCptView.setCodeProduit(StrHandler.lpad(contratCpt.getContratCptId().getCodPrdPrd().toString(), 
                                                              '0', 4));
                correctionDonneesClientForm.getListDesContratAmodifier().add(contratCptView);

            } // fin for
        } // fin if

    }
         public  Long getAge(Date DateNaissance) {
          Double d = (getDaysBetween(DateNaissance,DateHandler.strToDate(DateHandler.dateJour())));
          Long nombreJours = d.longValue();
          Long age = (nombreJours / 365);
          return (age);
         
           
         }
         public  double getDaysBetween(Date first, Date second) {

             double milliElapsed = second.getTime() - first.getTime();
             double daysElapsed = (milliElapsed / 24F / 3600F / 1000F);
             return (Math.round(daysElapsed * 100F) / 100F);
         }
}