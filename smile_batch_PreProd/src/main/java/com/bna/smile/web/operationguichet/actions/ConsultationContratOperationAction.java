package com.bna.smile.web.operationguichet.actions;

import com.bna.commun.model.CoTitulaire;
import com.bna.commun.model.ContratCpt;
import com.bna.commun.model.ContratCptId;
import com.bna.commun.model.Mandat;
import com.bna.commun.model.MandatOperation;
import com.bna.commun.model.Operation;
import com.bna.commun.util.DateHandler;
import com.bna.commun.util.ContextHandler;
import com.bna.commun.constant.Constants;
import com.bna.commun.util.DateHandler;
import com.bna.smile.model.domainecommun.commande.GetContratMandatCmd;
import com.bna.smile.model.domainecommun.commande.GetDetailContratCmd;
import com.bna.smile.model.domainecommun.commande.GetListMembreCotitulaireCmd;
import com.bna.smile.model.domainecommun.commande.GetOperationCmd;
import com.bna.smile.model.domainecommun.commande.GetPersonneCptCmd;
import com.bna.smile.model.domainecommun.model.ContratCptMandat;
import com.bna.smile.model.domainecommun.model.Listes;
import com.bna.smile.model.domainecommun.model.PersonneCpt;
import com.bna.smile.model.domainecommun.model.PersonneStrc;
import com.bna.smile.model.domainecontratcompte.procuration.commande.GetDetailMandatCmd;
import com.bna.smile.model.domainecontratcompte.procuration.commande.GetListMandatOperationPersonneContratOperationCmd;
import com.bna.smile.model.domainecontratcompte.procuration.commande.GetMandatAvaliderCmd;
import com.bna.smile.model.domainecontratcompte.procuration.dao.MandatDAO;
import com.bna.smile.model.domainecontratcompte.procuration.model.DetailMandat;
import com.bna.smile.model.domainecontratcompte.procuration.model.ListMandatOperationVo;
import com.bna.smile.model.domainecontratcompte.procuration.model.MandatRecherche;
import com.bna.smile.model.domainecontratcompte.procuration.model.ParamMandatOperationVo;
import com.bna.smile.web.commun.model.ParamAgence;
import com.bna.smile.web.operationguichet.form.ConsultationContratOperationForm;

import com.bna.smile.web.procuration.util.MandatOperationView;
import com.bna.smile.web.procuration.util.MandatView;

import com.oxia.fwk.context.Context;

import java.io.IOException;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

public class ConsultationContratOperationAction extends DispatchAction {
    /**This is the main action called from the Struts framework.
     * @param mapping The ActionMapping used to select this instance.
     * @param form The optional ActionForm bean for this request.
     * @param request The HTTP Request we are processing.
     * @param response The HTTP Response we are processing.
     */

    public


    ActionForward initierPage(ActionMapping mapping, ActionForm form, 
                              HttpServletRequest request, 
                              HttpServletResponse response) throws IOException, 
                                                                   ServletException {

        ConsultationContratOperationForm consultationContratOperationForm = 
            (ConsultationContratOperationForm)form;
        ParamAgence paramAgence = 
            (ParamAgence)request.getSession().getAttribute("paramAgBNA"); /// structure de l'agent 
        //----------------------------------------------------------------------------//
        // recuperer l'operation

        if (!consultationContratOperationForm.getCodeOperation().equals("")) {
            GetOperationCmd getOperationCmd = new GetOperationCmd();
            Operation operation = new Operation();
            operation.setCodOperOper(new Long(consultationContratOperationForm.getCodeOperation()));
            operation = (Operation)getOperationCmd.execute(operation);
            consultationContratOperationForm.setLibelleOperation(operation.getLibOperOper());
        }
        String varCodeOperation = new String("");
        //-- sauvgarder le code operation
        if (consultationContratOperationForm.getCodeOperation() != null) {
            varCodeOperation = 
                    new String(consultationContratOperationForm.getCodeOperation());
        }

        consultationContratOperationForm.clearForm();
        if (consultationContratOperationForm.getCodeOperation() != null) {
            consultationContratOperationForm.setCodeOperation(varCodeOperation);
        }
        consultationContratOperationForm.setCodStrcStrc(paramAgence.getCodStrcStrc().toString());
        
        
        //----------------------------------------------------------------------------------//
        //-------- Faire le mappage vers la page de l'operation 
     /*   if (varCodeOperation.equals("41931")){
        return mapping.findForward("virement");
        
        }else if (varCodeOperation.equals("41932")){
          return mapping.findForward("versement");
            
        }else
        */
          return mapping.findForward("consultationContratOperation");
      

    }

    public ActionForward rechercheContrat(ActionMapping mapping, 
                                          ActionForm form, 
                                          HttpServletRequest request, 
                                          HttpServletResponse response) throws IOException, 
                                                                               ServletException {
        try {
            Context context = ContextHandler.getContext();
            ConsultationContratOperationForm consultationContratOperationForm;
            consultationContratOperationForm = 
                    (ConsultationContratOperationForm)form;
            GetDetailContratCmd getDetailContratCmd = 
                new GetDetailContratCmd();
            ContratCptId contratCptId = new ContratCptId();
            String varTypeDemandeur = new String();

            contratCptId.setCodStrcStrc(new Long(consultationContratOperationForm.getCodStrcStrc()));
            contratCptId.setCodPrdPrd(new Long(consultationContratOperationForm.getCodPrdPrd()));
            contratCptId.setNumCcptCcpt(new Long(consultationContratOperationForm.getNumCcptCcpt()));

            //------------------------------------------------
            //-------- sauvgarder qlq information (le code operation, cle)
            Long varCodeOperation = 
                new Long(consultationContratOperationForm.getCodeOperation());
            String varCleCompte = 
                new String(consultationContratOperationForm.getCleCompte());
            //-----------------------------------------------------------
            //----------- Effacer l'ecran 
            consultationContratOperationForm.clearForm();

            //-------------------------------------------------------------------
            //------------- Recherche des donnée du Contrat et du Client 
            ContratCpt contratCpt = 
                (ContratCpt)getDetailContratCmd.execute(contratCptId);
            //test si contrat existant
            if (contratCpt.getContratCptId() != null) {
                consultationContratOperationForm.setTestExistanceContrat("O");
                Long varTypePersonne = 
                    new Long(contratCpt.getClient().getTypePers().getCodTperTper());
                //test si contrat valide
                if (contratCpt.getCodEtatCcpt().equals(Constants.COD_ETAT_CPT_VALID)) {
                    if (contratCpt.getMontSoldCcpt() != null) {
                        consultationContratOperationForm.setMontSoldCcpt(contratCpt.getMontSoldCcpt().toString());
                    }
                    consultationContratOperationForm.setDevise(contratCpt.getDevise().getLibDevDev());
                    consultationContratOperationForm.setNomIntiCcpt(contratCpt.getNomIntiCcpt());
                    consultationContratOperationForm.setCodTpceTpceClient(contratCpt.getClient().getPersonne().getTypePiece().getCodTpceTpce().toString());
                    consultationContratOperationForm.setNumPcePersClient(contratCpt.getClient().getPersonne().getNumPcePers());
                    consultationContratOperationForm.setDevise(contratCpt.getDevise().getLibDevDev());
                    consultationContratOperationForm.setNomIntiCcpt(contratCpt.getNomIntiCcpt());

                    //-----------------------------------------------------------
                    //----------- Si la personne est une entite co-titulaire
                    if ((varTypePersonne.toString()).equals(Constants.ENTCOTITULAIRE)) {
                        //-----------------------------------
                        // chercher les personnes cotitulaires
                        GetListMembreCotitulaireCmd listMembreCotitulaire = 
                            new GetListMembreCotitulaireCmd();
                        PersonneStrc personneStrc = new PersonneStrc();
                        personneStrc.setCodTpceTpce(contratCpt.getClient().getPersonne().getTypePiece().getCodTpceTpce());
                        personneStrc.setNumPcePers(contratCpt.getClient().getPersonne().getNumPcePers());


                        Listes lisCotitulaire = 
                            (Listes)listMembreCotitulaire.execute(personneStrc);
                        consultationContratOperationForm.setListCotitulaires(lisCotitulaire.getList());
                        CoTitulaire cotitulaire = 
                            (CoTitulaire)lisCotitulaire.getList().get(0);
                        consultationContratOperationForm.setNumSeqCliCotitulaire(cotitulaire.getClient().getNumSeqPers().toString());
                        consultationContratOperationForm.setTypeCotitulaire(cotitulaire.getCodTcotCoti());
                        consultationContratOperationForm.setTypeSignatureCotitulaire(cotitulaire.getCodSigCoti());

                        consultationContratOperationForm.setNomNomPersClient(contratCpt.getClient().getPersonne().getNomNomPers());
                        consultationContratOperationForm.setNomPrnPersClient(contratCpt.getClient().getPersonne().getNomPrnPers());

                        consultationContratOperationForm.setCodTpceTpceDemandeur(cotitulaire.getPersonne().getTypePiece().getCodTpceTpce().toString());
                        consultationContratOperationForm.setNumPcePersDemandeur(cotitulaire.getPersonne().getNumPcePers());
                        consultationContratOperationForm.setNomNomPersDemandeur(cotitulaire.getPersonne().getNomNomPers());
                        consultationContratOperationForm.setNomPrnPersDemandeur(cotitulaire.getPersonne().getNomPrnPers());
                        consultationContratOperationForm.setMessageTexte(" Le contrat appartient à une entite co-titulaire.");
                        consultationContratOperationForm.setTestTypePersonne("entiteCotitulaire");
                        consultationContratOperationForm.setTypeDemandeur("C");

                        //----------------------------------------------------------------
                        //--------- Cas d'une personne Morale
                    } else if (contratCpt.getClient().getTypePers().getCodTperTper().equals(Constants.PERSMORALE)) {
                        consultationContratOperationForm.setNomNomPersClient(contratCpt.getClient().getPersonne().getNomRsPers());
                        consultationContratOperationForm.setNomPrnPersClient(contratCpt.getClient().getPersonne().getLibSiglPers());
                        consultationContratOperationForm.setTestTypePersonne("personneMorale");
                    } else { // cas d'une personne physique
                        consultationContratOperationForm.setNomNomPersClient(contratCpt.getClient().getPersonne().getNomNomPers());
                        consultationContratOperationForm.setNomPrnPersClient(contratCpt.getClient().getPersonne().getNomPrnPers());
                        consultationContratOperationForm.setTestTypePersonne("personnePhysique");
                    }

                    if (contratCpt.getAdresseCorresp() != null) {
                        consultationContratOperationForm.setAdresseCorrespondanceClient(contratCpt.getAdresseCorresp().toString());
                    }
                    consultationContratOperationForm.setContratCpt(contratCpt);


                     MandatDAO mandatDAO = (MandatDAO)context.getBean("mandatDAO");
                     Long nombreMandatGeneraux = mandatDAO.getNombreMandatGenerauxParContrat(contratCpt.getContratCptId().getCodStrcStrc(),
                     contratCpt.getContratCptId().getCodPrdPrd(),contratCpt.getContratCptId().getNumCcptCcpt());
                     
                     Long nombreMandatSpeciaux = mandatDAO.getNombreMandatSpeciauxParContrat(contratCpt.getContratCptId().getCodStrcStrc(),
                    contratCpt.getContratCptId().getCodPrdPrd(),contratCpt.getContratCptId().getNumCcptCcpt(),Long.valueOf(varCodeOperation));
                    
                    if (nombreMandatGeneraux.intValue() > 0){
                        if (nombreMandatGeneraux.intValue() == 1){
                            consultationContratOperationForm.setMessageTexte(" un mandat général pour ce contrat"); 
                        }else {
                        consultationContratOperationForm.setMessageTexte(nombreMandatGeneraux + 
                                                                         " mandats généraux pour ce contrat");
                        }
                       
                    }
                   
                    if (nombreMandatSpeciaux.intValue() > 0){
                        if (nombreMandatSpeciaux.intValue() == 1){
                        consultationContratOperationForm.setMessageTexte("un mandat spécial pour ce contrat");
                        }else {
                            consultationContratOperationForm.setMessageTexte(nombreMandatSpeciaux + 
                                                                             " mandats spéciaux pour ce contrat");   
                            
                        }
                      
                    }
                   
                   if (nombreMandatSpeciaux.intValue() > 0 ||nombreMandatGeneraux.intValue() > 0 ){
                       consultationContratOperationForm.setTestExistanceMandat("O");  
                   }
                   

                } else {
                    consultationContratOperationForm.setAlert("ContratNonvalide");
                }

            } else {
                //--------------------------------------------------------------------
                //------------- Contrat Inexistant
                consultationContratOperationForm.setTestExistanceContrat("N");

            }
            consultationContratOperationForm.setCodPrdPrd(contratCptId.getCodPrdPrd().toString());
            consultationContratOperationForm.setCodStrcStrc(contratCptId.getCodStrcStrc().toString());
            consultationContratOperationForm.setNumCcptCcpt(contratCptId.getNumCcptCcpt().toString());
            consultationContratOperationForm.setCleCompte(varCleCompte);
            consultationContratOperationForm.setCodeOperation(varCodeOperation.toString());

            //--------------------------------------------------------------------
            //------------- Desactiver les champs  demandeur
            if (consultationContratOperationForm.getTestExistanceMandat().equals("O")) {
                consultationContratOperationForm.setActiverDemandeur("O");
            }
            //-------------- Fin desactivation champs demandeur  
            //-------------------------------------------------------------------- 

            return mapping.findForward("consultationContratOperation");
        } catch (Exception e) {
            System.out.println("Erreur --------- " + e.getMessage());
            return mapping.findForward("consultationContratOperation");

        }
    }

    public ActionForward validerDemande(ActionMapping mapping, ActionForm form, 
                                        HttpServletRequest request, 
                                        HttpServletResponse response) throws IOException, 
                                                                             ServletException {
        ConsultationContratOperationForm consultationContratOperationForm = 
            (ConsultationContratOperationForm)form;

        consultationContratOperationForm.setAlert("");
        consultationContratOperationForm.setTestExistanceDemandeur("");

        // Vider les champs concernant le mandats
        consultationContratOperationForm.setListdesMandatsPersonneChoisi(null);
        consultationContratOperationForm.setListMandats(null);
        consultationContratOperationForm.setListMandatsConcernesPersonne(null);
        //consultationContratOperationForm.setListMandatsOperationConcernesDemandeur(null);
        consultationContratOperationForm.setListMandatsPersonneConcernesDemandeur(null);

        //----------------------------------------------------------------------
        //----------- Verifier si le demandeur est celui le titlaire du mandat
        Long typePieceTitulaireContrat = 
            consultationContratOperationForm.getContratCpt().getClient().getPersonne().getTypePiece().getCodTpceTpce();
        String numeroPieceTitulaireContrat = 
            consultationContratOperationForm.getContratCpt().getClient().getPersonne().getNumPcePers();
        String testDemandeur = new String("");
        if (consultationContratOperationForm.getCodTpceTpceDemandeur().equals(typePieceTitulaireContrat.toString()) && 
            consultationContratOperationForm.getNumPcePersDemandeur().equals(numeroPieceTitulaireContrat)) {
            testDemandeur = "Titulaire";
            consultationContratOperationForm.setNomNomPersDemandeur(consultationContratOperationForm.getNomNomPersClient());
            consultationContratOperationForm.setNomPrnPersDemandeur(consultationContratOperationForm.getNomPrnPersClient());;
            }
        //-------------------------------------------------------------------
        //afficher les mandats qui concerne la personne pour cette operation
        if (consultationContratOperationForm.getTestExistanceMandat().equals("O") && 
            (!testDemandeur.equals("Titulaire"))) {
            //-------------------------------------------------------
            //------- recherche de la personne
            PersonneStrc personneStrc = new PersonneStrc();
            personneStrc.setCodTpceTpce(new Long(consultationContratOperationForm.getCodTpceTpceDemandeur()));
            personneStrc.setNumPcePers(consultationContratOperationForm.getNumPcePersDemandeur());
            GetPersonneCptCmd getPersonneCptCmd = new GetPersonneCptCmd();
            PersonneCpt personneCpt = 
                (PersonneCpt)getPersonneCptCmd.execute(personneStrc);
            //------- Fin de la recherche de la personne

            if (personneCpt.getPersonne() != null) {

                consultationContratOperationForm.setNomNomPersDemandeur(personneCpt.getPersonne().getNomNomPers());
                consultationContratOperationForm.setNomPrnPersDemandeur(personneCpt.getPersonne().getNomPrnPers());

                GetListMandatOperationPersonneContratOperationCmd getListM = 
                    new GetListMandatOperationPersonneContratOperationCmd();
                //--------------  Les Objets de parametre ---------------------------------

                ParamMandatOperationVo paramMandatOperationVo = 
                    new ParamMandatOperationVo();
                ContratCpt contratCptRecherche = new ContratCpt();
                Operation operation = new Operation();

                contratCptRecherche.setContratCptId(consultationContratOperationForm.getContratCpt().getContratCptId());
                operation.setCodOperOper(new Long(consultationContratOperationForm.getCodeOperation()));

                personneStrc.setCodTpceTpce(personneCpt.getPersonne().getTypePiece().getCodTpceTpce()); // new Long(consultationContratOperationForm.getCodTpceTpceDemandeur()));
                personneStrc.setNumPcePers(personneCpt.getPersonne().getNumPcePers());

               // paramMandatOperationVo.setContraCptId(contratCptRecherche.getContratCptId());
                paramMandatOperationVo.setPersonneStrc(personneStrc);
                paramMandatOperationVo.setOperation(operation);


                ListMandatOperationVo listMandatOperation = 
                    (ListMandatOperationVo)getListM.execute(paramMandatOperationVo);

                //---------------------------------------------- 
                //------- Remplir la liste des mandats View par les mandat generaux
                List listeDesMandatsView = new ArrayList();
                if (listMandatOperation.getListMandatsGeneraux().size() > 0) {
                    for (Iterator it = 
                         listMandatOperation.getListMandatsGeneraux().iterator(); 
                         it.hasNext(); ) {
                        Mandat mandat = (Mandat)it.next();
                        MandatView mandatView = new MandatView();
                        mandatView.setDateDebut(DateHandler.dateToStr(mandat.getDatDebMand()));
                        mandatView.setDateFin(DateHandler.dateToStr(mandat.getDatFinMand()));
                        if (mandat.getCodTypMand().equals("S"))
                            mandatView.setType("Spécial");
                        else if (mandat.getCodTypMand().equals("G")) {
                            mandatView.setType("Général");
                        }
                        mandatView.setMandat(mandat);
                        listeDesMandatsView.add(mandatView);
                    } //--- Fin remplir les mandats view
                } // Fin if   mandats generaux    

                //---------------------------------------------- 
                //------- Remplir la liste des mandats View par les mandat spéciaux
                if (listMandatOperation.getListMandatsSpeciauxOperations().size() > 
                    0) {
                    for (Iterator it = 
                         listMandatOperation.getListMandatsSpeciauxOperations().iterator(); 
                         it.hasNext(); ) {
                        Mandat mandat = (Mandat)it.next();
                        MandatView mandatView = new MandatView();
                        mandatView.setDateDebut(DateHandler.dateToStr(mandat.getDatDebMand()));
                        mandatView.setDateFin(DateHandler.dateToStr(mandat.getDatFinMand()));
                        if (mandat.getCodTypMand().equals("S")) {
                            mandatView.setType("Spécial");
                        } else if (mandat.getCodTypMand().equals("G")) {
                            mandatView.setType("Général");
                        } else if (mandat.getCodTypMand().equals("J")) {
                            mandatView.setType("M. de Justice");
                        }
                        mandatView.setMandat(mandat);
                        listeDesMandatsView.add(mandatView);
                    } //--- Fin remplir les mandats view
                } // Fin if  

                consultationContratOperationForm.setListMandatsConcernesPersonne(listeDesMandatsView);
                if (consultationContratOperationForm.getListMandatsConcernesPersonne().size() == 
                    0) {
                    consultationContratOperationForm.setAlert("PasDeMandat");
                }


            } else { //-- Demandeur inexistant dans la base
                consultationContratOperationForm.setTestExistanceDemandeur("N");
            }
        } // Fin recherche personne 
        return mapping.findForward("consultationContratOperation");

    }

    public ActionForward annuler(ActionMapping mapping, ActionForm form, 
                                 HttpServletRequest request, 
                                 HttpServletResponse response) throws IOException, 
                                                                      ServletException {
        ConsultationContratOperationForm consultationContratOperationForm = 
            (ConsultationContratOperationForm)form;
        consultationContratOperationForm.clearForm();
        return mapping.findForward("consultationContratOperation");
    }

    /**
     * methode pour la recherche des operations d'un mandat choisi
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws IOException
     * @throws ServletException
     */
    public

    ActionForward mandatOperation(ActionMapping mapping, ActionForm form, 
                                  HttpServletRequest request, 
                                  HttpServletResponse response) throws IOException, 
                                                                       ServletException {
        ConsultationContratOperationForm consultationContratOperationForm = 
            (ConsultationContratOperationForm)form;
        Mandat mandat = new Mandat();
        
        
        
        
    // consultationContratOperationForm.setListMandatsOperationConcernesDemandeur(null);
     consultationContratOperationForm.setListMandatsPersonneConcernesDemandeur(null);
        //---------------------------------------------------------------------//
        //------------------ rechercher le mandat ------------------------------//
        //---------------------------------------------------------------------//
        for (Iterator it = 
             consultationContratOperationForm.getListMandatsConcernesPersonne().iterator(); 
             it.hasNext(); ) {
            MandatView mandatView = (MandatView)it.next();
            if (mandatView.getMandat().getNumMandMand().equals(new Long(consultationContratOperationForm.getNumeroMandatChoisi()))) {
                mandat = mandatView.getMandat();
            

        
        consultationContratOperationForm.setRefMand(mandat.getNumDemMand().toString());
        GetDetailMandatCmd getDetailMandatCmd = new GetDetailMandatCmd();
        DetailMandat detailMandat = 
            (DetailMandat)getDetailMandatCmd.execute(mandat);

        //---------------------------------------------------------------------//
        //----------- Recherche des Operations         ------------------------//
        //---------------------------------------------------------------------//
        if (detailMandat.getListeMandatOperations() != null) {
            MandatOperationView mandatOperationView = 
                new MandatOperationView();
            List listMandatOperationView = new ArrayList();
            for (Iterator itMandatOperation = 
                 detailMandat.getListeMandatOperations().iterator(); 
                 itMandatOperation.hasNext(); ) {
                MandatOperation mandatOeration = (MandatOperation)itMandatOperation.next();
               
               //-----------------------------------------------------------------------//
               //-------- Verifier si le mandatOperation concerne cette operation ------//
               //-----------------------------------------------------------------------//
                if (consultationContratOperationForm.getCodeOperation().equals(mandatOeration.getOperation().getCodOperOper().toString())){
                    mandatOperationView.setMandatOperation(mandatOeration);
                    mandatOperationView.setDateDebutOperation(DateHandler.dateToStr(mandatOeration.getDatDebMaop()));
                    mandatOperationView.setDateDebutPeriode(DateHandler.dateToStr(mandatOeration.getDatDperMaop()));
              
                    if (mandatOeration.getCodSignMaop() != null) {
                        if (mandatOeration.getCodSignMaop().equals("S")) {
                            mandatOperationView.setTypeSignature("Séparée");
                        } else if (mandatOeration.getCodSignMaop().equals("C")) {
                            mandatOperationView.setTypeSignature("Conjointe");
                        }
                    }
              
                listMandatOperationView.add(mandatOperationView);
                } //fin if l'operation est celle concernée  
            } // Fin For    
        //    consultationContratOperationForm.setListMandatsOperationConcernesDemandeur(listMandatOperationView);
        }


        //---------------------------------------------------------------------//
        //----------- Recherche des personnes         ------------------------//
        //---------------------------------------------------------------------//
        if (detailMandat.getListeMandatPersonnes() != null) {
            consultationContratOperationForm.setListdesMandatsPersonneChoisi(new ArrayList());
            for (int i = 0; i < detailMandat.getListeMandatPersonnes().size(); 
                 i++) {
                consultationContratOperationForm.getListdesMandatsPersonneChoisi().add("");
            }
            consultationContratOperationForm.setListMandatsPersonneConcernesDemandeur(detailMandat.getListeMandatPersonnes());

        } // fin recherche des personnes
       
       } // fin il a trouver le mandat
      }  // fin for
        return mapping.findForward("consultationContratOperation");
 }   

}
