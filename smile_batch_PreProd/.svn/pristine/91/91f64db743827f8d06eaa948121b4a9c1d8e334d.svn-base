package com.bna.smile.web.souscription.actions;

import com.bna.commun.model.Blocage;
import com.bna.commun.model.Categorie;
import com.bna.commun.model.CategorieId;
import com.bna.commun.model.Client;
import com.bna.commun.model.CoTitulaire;
import com.bna.commun.model.ContratCpt;
import com.bna.commun.model.ContratCptId;
import com.bna.commun.model.DemandeCheque;
import com.bna.commun.model.DetailCatCpt;
import com.bna.commun.model.Devise;
import com.bna.commun.model.EtatContrat;
import com.bna.commun.model.LivretEpargne;
import com.bna.commun.model.Mandat;
import com.bna.commun.model.MotifEtat;
import com.bna.commun.model.MotifEtatId;
import com.bna.commun.model.OperationMoyPay;
import com.bna.commun.model.Personne;
import com.bna.commun.model.Personnel;
import com.bna.commun.model.Structure;
import com.bna.commun.model.StructureDomaine;
import com.bna.commun.model.Tache;
import com.bna.commun.model.TacheId;
import com.bna.commun.model.TraceContrat;
import com.bna.commun.model.TypeModification;
import com.bna.commun.model.TypePiece;
import com.bna.commun.util.ContextHandler;
import com.bna.commun.util.DateHandler;
import com.bna.commun.util.SmileUtil;
import com.bna.commun.util.StrHandler;
import com.bna.commun.vo.PrimitiveVO;
import com.bna.smile.model.constant.Constants;
import com.bna.smile.model.domainecommun.commande.GetContratEtatCmd;
import com.bna.smile.model.domainecommun.commande.GetDetailContratCmd;
import com.bna.smile.model.domainecommun.commande.GetListContratCmd;
import com.bna.smile.model.domainecommun.commande.GetListContratMandataireCmd;
import com.bna.smile.model.domainecommun.commande.GetListCotitulairePersonneCmd;
import com.bna.smile.model.domainecommun.commande.GetListCreditCmd;
import com.bna.smile.model.domainecommun.commande.GetListMembreCotitulaireCmd;
import com.bna.smile.model.domainecommun.commande.GetPersonneCptCmd;
import com.bna.smile.model.domainecommun.commande.UpdatePersonneCmd;
import com.bna.smile.model.domainecommun.model.ContratCptMandat;
import com.bna.smile.model.domainecommun.model.ContratPersonne;
import com.bna.smile.model.domainecommun.model.Listes;
import com.bna.smile.model.domainecommun.model.MandatPersonneMandat;
import com.bna.smile.model.domainecommun.model.PersonneCpt;
import com.bna.smile.model.domainecommun.model.PersonneRechercheContratVo;
import com.bna.smile.model.domainecommun.model.PersonneStrc;
import com.bna.smile.model.domainecommun.model.Tuteur;
import com.bna.smile.model.domainecontratcompte.modificationdonneesclient.model.ParamModificationDonneesVo;
import com.bna.smile.model.domainecontratcompte.moyensPaiement.commande.GetChequiersCmd;
import com.bna.smile.model.domainecontratcompte.moyensPaiement.commande.GetListDemandesChequesCmd;
import com.bna.smile.model.domainecontratcompte.moyensPaiement.dao.DemandeChequeDAO;
import com.bna.smile.model.domainecontratcompte.moyensPaiement.model.ListesDemandesCheques;
import com.bna.smile.model.domainecontratcompte.moyensPaiement.model.ParamDemandeCheque;
import com.bna.smile.model.domainecontratcompte.moyensPaiement.model.Paramchequiers;
import com.bna.smile.model.domainecontratcompte.procuration.commande.GetMandatAvaliderCmd;
import com.bna.smile.model.domainecontratcompte.procuration.commande.UpdateMandatCmd;
import com.bna.smile.model.domainecontratcompte.procuration.model.MandatRecherche;
import com.bna.smile.model.domainecontratcompte.souscriptioncontratcompte.commande.BloquerContratCptCmd;
import com.bna.smile.model.domainecontratcompte.souscriptioncontratcompte.commande.BloquerMontantCmd;
import com.bna.smile.model.domainecontratcompte.souscriptioncontratcompte.commande.ChargerBlocagesCmd;
import com.bna.smile.model.domainecontratcompte.souscriptioncontratcompte.commande.ChargerCatSupCmd;
import com.bna.smile.model.domainecontratcompte.souscriptioncontratcompte.commande.ChargerMotifEtatCmd;
import com.bna.smile.model.domainecontratcompte.souscriptioncontratcompte.commande.ChargerNatureblocageCmd;
import com.bna.smile.model.domainecontratcompte.souscriptioncontratcompte.commande.ChargerRgmCatEpargneCmd;
import com.bna.smile.model.domainecontratcompte.souscriptioncontratcompte.commande.CloturerContratCmd;
import com.bna.smile.model.domainecontratcompte.souscriptioncontratcompte.commande.DebloquerContratCptCmd;
import com.bna.smile.model.domainecontratcompte.souscriptioncontratcompte.commande.DebloquerMontantCmd;
import com.bna.smile.model.domainecontratcompte.souscriptioncontratcompte.commande.GestionEpargneCmd;
import com.bna.smile.model.domainecontratcompte.souscriptioncontratcompte.commande.GetDetailCategorieContratCmd;
import com.bna.smile.model.domainecontratcompte.souscriptioncontratcompte.commande.GetNbrProduitByPersCmd;
import com.bna.smile.model.domainecontratcompte.souscriptioncontratcompte.commande.GetTuteurCmd;
import com.bna.smile.model.domainecontratcompte.souscriptioncontratcompte.commande.MAJLivretEpargneCmd;
import com.bna.smile.model.domainecontratcompte.souscriptioncontratcompte.commande.MiseAJourDetailCatContratCmd;
import com.bna.smile.model.domainecontratcompte.souscriptioncontratcompte.commande.TransfertCtxCmd;
import com.bna.smile.model.domainecontratcompte.souscriptioncontratcompte.commande.UpdateContratCptCmd;
import com.bna.smile.model.domainecontratcompte.souscriptioncontratcompte.commande.VerifDateLimiteCmd;
import com.bna.smile.model.domainecontratcompte.souscriptioncontratcompte.model.BlocageCriteres;
import com.bna.smile.model.domainecontratcompte.souscriptioncontratcompte.model.ContratABloquer;
import com.bna.smile.model.domainecontratcompte.souscriptioncontratcompte.model.ContratACloturer;
import com.bna.smile.model.domainecontratcompte.souscriptioncontratcompte.model.ContratCptACtx;
import com.bna.smile.model.domainecontratcompte.souscriptioncontratcompte.model.GestionEpargneVO;
import com.bna.smile.model.domainecontratcompte.souscriptioncontratcompte.model.ListRgmCatEpargne;
import com.bna.smile.model.domainecontratcompte.souscriptioncontratcompte.model.Livrets;
import com.bna.smile.model.domainecontratcompte.souscriptioncontratcompte.model.MontantBlocage;
import com.bna.smile.model.domainecontratcompte.souscriptioncontratcompte.model.ParamEpargne;
import com.bna.smile.model.domainecontratcompte.souscriptioncontratcompte.model.ParamInsertContrat;
import com.bna.smile.model.domainecontratcompte.souscriptioncontratcompte.model.ParamMiseAjourDetailcatCpt;
import com.bna.smile.model.domainecontratcompte.souscriptioncontratcompte.model.PersProduit;
import com.bna.smile.web.commun.model.ParamAgence;
import com.bna.smile.web.commun.util.SessionUtil;
import com.bna.smile.web.moyenPaiement.demandeChequier.forms.RechercheDemandesChequesForm;
import com.bna.smile.web.moyenPaiement.demandeChequier.util.DemandeChequeView;
import com.bna.smile.web.procuration.util.ContratCptView;
import com.bna.smile.web.souscription.forms.ConsultationContratCompteForm;
import com.bna.smile.web.souscription.forms.GestionContratCptForm;

import com.oxia.fwk.context.Context;
import com.oxia.fwk.core.ValueObject;

import java.io.IOException;

import java.text.SimpleDateFormat;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.actions.DispatchAction;


public class GestionContratCptAction extends DispatchAction {
    /**
     * <B> Action de la page  transfertContratCpt.jsp  </B>
     * @param mapping The ActionMapping used to select this instance.
     * @param form The optional ActionForm bean for this request.
     * @param request The HTTP Request we are processing.
     * @param response The HTTP Response we are processing.
     * Nom du package : com.bna.smile.web.procuration.actions
     *
     * @author Kriaa hatem & Boussen youssef
     * @version le 28/05/2007
     */
   
    Long codTach;
    Long codeOperation;
    private static final Logger logger = Logger.getLogger(SouscriptionContratCompteAction.class);
    ActionMessages actionMessages = new ActionMessages();
    public ActionForward initierPage(ActionMapping mapping, ActionForm form, 
                                     HttpServletRequest request, 
                                     HttpServletResponse response) throws IOException, 
                                                                          ServletException {
        
        
        SessionUtil sessionUtil =new SessionUtil();
        //Suppression des anciens Bean de type Form de la session, SAUF "gestionContratCptForm"
        sessionUtil.removeSession(request,"gestionContratCptForm"); 
        
        ParamAgence paramAgence = new ParamAgence();
        
        paramAgence = (ParamAgence)request.getSession().getAttribute("paramAgBNA");/// structure de l'agent qui fait la saisie
               
        GestionContratCptForm gestionContratCptForm = (GestionContratCptForm)form; 
       
            try {
                if (!(gestionContratCptForm.getCodetrait().equalsIgnoreCase("CR"))) {
                    //verification de l'habilitation sur cet operation
                    StructureDomaine structureDomaine = new StructureDomaine(paramAgence.getCodStrcStrc(),Constants.COD_DOM_CONTRATCOMPTE);
                    boolean bool = SmileUtil.testDomaineOuvert(structureDomaine);
                }
                String codeOperation = 
                    gestionContratCptForm.getInitialisationView().getCodeOperation();
                annulForm(gestionContratCptForm,paramAgence);
                gestionContratCptForm.getInitialisationView().setCodeOperation(codeOperation);
                gestionContratCptForm.getInitialisationView().setDateOp(new Date());
                gestionContratCptForm.getInitialisationView().setNumMatrUser(paramAgence.getNumMatrUser().toString());
                gestionContratCptForm.getInitialisationView().setCodeAgence(paramAgence.getCodStrcStrc().toString());
                gestionContratCptForm.getContratView().setCodStrcStrc(paramAgence.getCodStrcStrc().toString());
                if ((gestionContratCptForm.getCodetrait().equalsIgnoreCase("PC")) || 
                    (gestionContratCptForm.getCodetrait().equalsIgnoreCase("VC"))||
                    (gestionContratCptForm.getCodetrait().equalsIgnoreCase("AC"))) {
                    return mapping.findForward("indexCloture");
                }
                if ((gestionContratCptForm.getCodetrait().equalsIgnoreCase("BM")) || 
                    (gestionContratCptForm.getCodetrait().equalsIgnoreCase("DM"))||
                    (gestionContratCptForm.getCodetrait().equalsIgnoreCase("CB"))
                    ) {
                    return mapping.findForward("indexBlocageMnt");
                }
                if (gestionContratCptForm.getCodetrait().equalsIgnoreCase("RL")) {
                    return mapping.findForward("indexLivret");
                }
                if (gestionContratCptForm.getCodetrait().equalsIgnoreCase("TX")) {
                    return mapping.findForward("indexTransfertCtx");
                }
                if ((gestionContratCptForm.getCodetrait().equalsIgnoreCase("BC")) || 
                    (gestionContratCptForm.getCodetrait().equalsIgnoreCase("DC"))) {
    
                    return mapping.findForward("indexBlocageCpt");
                
                }if (gestionContratCptForm.getCodetrait().equalsIgnoreCase("RC")){
                    
                    return mapping.findForward("indexBlocageCpt");
                
                } else {
                    return mapping.findForward("success");
                }
    
            } catch (Exception e) {
                com.oxia.fwk.core.Error erreur = new com.oxia.fwk.core.Error();
                StringBuffer text = 
                    new StringBuffer("la transaction est Interrompu, une erreur dans GestionContratCptAction / Dispatch Action :initierPage ");
                text.append(e.toString());
                erreur.setCode("200");
                erreur.setDescription(text.toString());
                logger.error("Erreur au niveau de l'agence <<" +gestionContratCptForm.getCodStrcStrc()+ ">>. Exception : ",e);  
            //    logger.error("Exception : ",e);  
                ActionMessage actionMessage = 
                    new ActionMessage("exception.generique", 
                                      erreur.getDescription());
                actionMessages.add("Erreur ", actionMessage);
                this.saveMessages(request, actionMessages);
                return mapping.findForward("error");
            }
       
    }


    public ActionForward rechercherContrat(ActionMapping mapping, 
                                           ActionForm form, 
                                           HttpServletRequest request, 
                                           HttpServletResponse response) throws IOException, 
                                                                                ServletException {

            GestionContratCptForm gestionContratCptForm = 
                (GestionContratCptForm)form;
            try {
            ActionMessages actionMessages = new ActionMessages();
            /*     String prd=    gestionContratCptForm.getCodPrdPrd();
                 String cpt=    gestionContratCptForm.getNumCcptCcpt();
                 annulForm(gestionContratCptForm);
                 gestionContratCptForm.setCodPrdPrd(prd);
                 gestionContratCptForm.setNumCcptCcpt(cpt);

              */
            /*******recherche du contrat**********/

            GetDetailContratCmd getDetailContratCmd = 
                new GetDetailContratCmd();
            ContratCpt contratCpt = new ContratCpt();
            ContratCptId contratCptId = new ContratCptId();
            contratCptId.setCodStrcStrc(Long.valueOf(gestionContratCptForm.getCodStrcStrc()));
            contratCptId.setCodPrdPrd(Long.valueOf(gestionContratCptForm.getCodPrdPrd()));
            contratCptId.setNumCcptCcpt(Long.valueOf(gestionContratCptForm.getNumCcptCcpt()));
            contratCpt = (ContratCpt)getDetailContratCmd.execute(contratCptId);
            if (contratCpt != null && !contratCpt.hasError() && 
                contratCpt.getContratCptId() != null) {
                /* verifier date limite de changement */
                VerifDateLimiteCmd verifDateLimiteCmd = new VerifDateLimiteCmd();
                PrimitiveVO primitiveVO = (PrimitiveVO)verifDateLimiteCmd.execute(contratCpt);
                
                if (primitiveVO.hasError()) {
                    List listErreur = primitiveVO.getErrors();
                    for (Iterator it = listErreur.iterator(); it.hasNext(); ) {
                        com.oxia.fwk.core.Error erreur = (com.oxia.fwk.core.Error)it.next();
                        ActionMessage actionMessage = new ActionMessage("exception.generique",erreur.getDescription());
                        actionMessages.add("Erreur ", actionMessage);
                    }
                    this.saveMessages(request, actionMessages);
                    return mapping.findForward("error");

                } else{
                if (gestionContratCptForm.getExecutant()!=null && gestionContratCptForm.getExecutant().equalsIgnoreCase("DQMRP")){
                    gestionContratCptForm.setValidDateLim(true);
                }else{
                    gestionContratCptForm.setValidDateLim(primitiveVO.isVBool());
                }
                gestionContratCptForm.setContrat(contratCpt);
                gestionContratCptForm.setClient(contratCpt.getClient());
                gestionContratCptForm.setNomNomPers(contratCpt.getClient().getPersonne().getNomNomPers());
                gestionContratCptForm.setNomPrnPers(contratCpt.getClient().getPersonne().getNomPrnPers());
                gestionContratCptForm.setTypPcePers(contratCpt.getClient().getPersonne().getTypePiece().getCodTpceTpce().toString());
                ///gestionContratCptForm.setNumPcePers(StrHandler.lpad(contratCpt.getClient().getPersonne().getNumPcePers(),'0', 8));
                gestionContratCptForm.setNumPcePers(contratCpt.getClient().getPersonne().getNumPcePers());
       
                gestionContratCptForm.setCodEtatCcpt(contratCpt.getCodEtatCcpt());
                gestionContratCptForm.setNumLivrCcpt(contratCpt.getNumLivrCcpt());
                gestionContratCptForm.setDatOuvCcpt(DateHandler.dateToStr(contratCpt.getDatOuvCcpt()));
                gestionContratCptForm.setMontSoldCcpt(StrHandler.formatmnt(contratCpt.getMontSoldCcpt().doubleValue()));
                gestionContratCptForm.setMontSoldActuel(contratCpt.getMontSoldCcpt());
                gestionContratCptForm.setLibDevDev(contratCpt.getDevise().getLibDevDev());
                GetDetailCategorieContratCmd getDetailCategorieContratCmd = 
                    new GetDetailCategorieContratCmd();
                DetailCatCpt detailCatCpt = new DetailCatCpt();
                detailCatCpt = 
                        (DetailCatCpt)getDetailCategorieContratCmd.execute(contratCptId);
                if (detailCatCpt != null) {

                    gestionContratCptForm.setDetailCatCpt(detailCatCpt);
                    gestionContratCptForm.setCodCatCat(detailCatCpt.getCategorie().getCategorieId().getCodCatCat());
                    gestionContratCptForm.setCodRgmRgm(detailCatCpt.getCategorie().getCategorieId().getCodRgmRgm().toString());
                    gestionContratCptForm.setCodCorrCat(detailCatCpt.getCategorie().getCodCorrCat());
                    gestionContratCptForm.setLibCatCat(detailCatCpt.getCategorie().getLibCatCat());
                    gestionContratCptForm.setLibRgmRgm(detailCatCpt.getCategorie().getRegime().getLibRgmRgm());


                    gestionContratCptForm.setMontVersCat(StrHandler.formatmnt(detailCatCpt.getCategorie().getMontVersCat().doubleValue()));
                    gestionContratCptForm.setMontCaptCat(StrHandler.formatmnt(detailCatCpt.getCategorie().getMontCaptCat().doubleValue()));
                    gestionContratCptForm.setMontAssrCat(detailCatCpt.getCategorie().getMontAssrCat());
                    if (detailCatCpt.getCategorie().getMontBrsCat() != null) {
                        gestionContratCptForm.setMontBrsCat(StrHandler.formatmnt(detailCatCpt.getCategorie().getMontBrsCat().doubleValue()));
                    } /* Historique des cat/rgm */
                    gestionContratCptForm.getListAncienCategorieEpargne().clear();
                    gestionContratCptForm.getListAncienCategorieEpargne().addAll(contratCpt.getDetailCatCpts());

                }

                /*******chargement des categories et regime pour le produit**********/
                ChargerCatSupCmd chargerCatSupCmd = new ChargerCatSupCmd();
                ChargerRgmCatEpargneCmd chargerRgmCatEpargneCmd = 
                    new ChargerRgmCatEpargneCmd();
                ParamEpargne paramEpargne = new ParamEpargne(); //Vo input

                paramEpargne.setCodPrdPrd(Long.valueOf(gestionContratCptForm.getCodPrdPrd()));
                paramEpargne.setCodRgmRgm(gestionContratCptForm.getCodRgmRgm());
                paramEpargne.setCodCatCat(gestionContratCptForm.getCodCatCat());
                paramEpargne.setCodCorrCat(gestionContratCptForm.getCodCorrCat());
                /*   if (gestionContratCptForm.getTypeRequest().equals("choixCategorie")) {
                 paramEpargne.setCodCatCat(gestionContratCptForm.getCodeCategorieEpargne());
             } else {
                 paramEpargne.setCodCatCat("");
             }
            */
                ListRgmCatEpargne listRgmCatEpargne = 
                    new ListRgmCatEpargne(); //Vo output 

                listRgmCatEpargne = 
                        (ListRgmCatEpargne)chargerCatSupCmd.execute(paramEpargne);

                if (gestionContratCptForm.getTypeRequest().equals("ouverture")) {
                    gestionContratCptForm.setListRegimeEpargne(null);
                    gestionContratCptForm.setListRegimeEpargne(listRgmCatEpargne.getListRgmEpargne());
                }

                if (gestionContratCptForm.getTypeRequest().equals("ouverture") || 
                    gestionContratCptForm.getTypeRequest().equals("choixRegime")) {
                    gestionContratCptForm.setListCategorieEpargne(null);
                    gestionContratCptForm.setListCategorieEpargne(listRgmCatEpargne.getListCatEpargne());
                }

                if (listRgmCatEpargne.getCategorie() != null) {
                    gestionContratCptForm.setCodeCategorieEpargne(listRgmCatEpargne.getCategorie().getCategorieId().getCodCatCat());
                    gestionContratCptForm.setNouvMontVersCat(listRgmCatEpargne.getCategorie().getMontVersCat().toString());
                    gestionContratCptForm.setNouvMontCaptCat(listRgmCatEpargne.getCategorie().getMontCaptCat().toString());
                }
            }
            } else {
                if (contratCpt.hasError()) {
                    List listErreur = contratCpt.getErrors();
                    for (Iterator it = listErreur.iterator(); it.hasNext(); ) {
                        com.oxia.fwk.core.Error erreur = (com.oxia.fwk.core.Error)it.next();
                        ActionMessage actionMessage = new ActionMessage("exception.generique",erreur.getDescription());
                        actionMessages.add("Erreur ", actionMessage);
                    }
                    this.saveMessages(request, actionMessages);
                    return mapping.findForward("error");

                } else{
                    gestionContratCptForm.setNumCcptCcpt("");
                    gestionContratCptForm.setAlert("contratNonValide");
                }

            }

            return mapping.findForward("success");


        } catch (Exception e) {
            com.oxia.fwk.core.Error erreur = new com.oxia.fwk.core.Error();
            StringBuffer text = 
                new StringBuffer("la transaction est Interrompu, une erreur dans GestionContratCptAction / Dispatch Action :rechercherContrat ");
            text.append(e.toString());
            erreur.setCode("200");
            erreur.setDescription(text.toString());
            logger.error("Erreur au niveau de l'agence <<" +gestionContratCptForm.getCodStrcStrc()+ ">>. Exception : ",e);  
          //  logger.error("Exception : ",e);  
            ActionMessage actionMessage = 
                new ActionMessage("exception.generique", 
                                  erreur.getDescription());
            actionMessages.add("Erreur ", actionMessage);
            this.saveMessages(request, actionMessages);
            return mapping.findForward("error");
        }

    }

    private void annulForm(GestionContratCptForm gestionContratCptForm,ParamAgence paramAgence ) {

        String ct = gestionContratCptForm.getCodetrait();
        gestionContratCptForm.getListMineurs().clear();
        gestionContratCptForm.clearForm();
        gestionContratCptForm.setDroittransfert(true);
        gestionContratCptForm.setCodetrait(ct);
        if (!(gestionContratCptForm.getExecutant()!=null && gestionContratCptForm.getExecutant().equalsIgnoreCase("DQMRP"))){
            gestionContratCptForm.setCodStrcStrc(paramAgence.getCodStrcStrc().toString());
        }
    }

    public ActionForward rechercherPers(ActionMapping mapping, ActionForm form, 
                                        HttpServletRequest request, 
                                        HttpServletResponse response) throws IOException, 
                                                                             ServletException {
        ActionMessages actionMessages = new ActionMessages();
        GestionContratCptForm gestionContratCptForm = 
            (GestionContratCptForm)form;
        try {

            /*******recherche du nouveau client**********/

            GetPersonneCptCmd getPersonneCptCmd = new GetPersonneCptCmd();
            PersonneStrc personneStrc = new PersonneStrc();
            personneStrc.setCodStrcStrc(Long.valueOf(gestionContratCptForm.getCodStrcStrc()));
            personneStrc.setCodTpceTpce(Long.valueOf(gestionContratCptForm.getTypPcePersN()));
            personneStrc.setNumPcePers(gestionContratCptForm.getNumPcePersN());
            PersonneCpt personneCpt = 
                (PersonneCpt)getPersonneCptCmd.execute(personneStrc);

            if (personneCpt != null && !personneCpt.hasError()) {
                gestionContratCptForm.setNomNomPersN(personneCpt.getPersonne().getNomNomPers());
                gestionContratCptForm.setNomPrnPersN(personneCpt.getPersonne().getNomPrnPers());

            } else if (personneCpt.hasError()) {
                List listErreur = personneCpt.getErrors();
                for (Iterator it = listErreur.iterator(); it.hasNext(); ) {
                    com.oxia.fwk.core.Error erreur = 
                        (com.oxia.fwk.core.Error)it.next();
                    ActionMessage actionMessage = 
                        new ActionMessage("exception.generique", 
                                          erreur.getDescription());
                    actionMessages.add("Erreur ", actionMessage);
                }
                this.saveMessages(request, actionMessages);
                return mapping.findForward("error");
            }

            return mapping.findForward("success");


        } catch (Exception e) {
            com.oxia.fwk.core.Error erreur = new com.oxia.fwk.core.Error();
            StringBuffer text = 
                new StringBuffer("la transaction est Interrompu, une erreur dans GestionContratCptAction / Dispatch Action :rechercherPers ");
            text.append(e.toString());
            erreur.setCode("200");
            erreur.setDescription(text.toString());
            logger.error("Erreur au niveau de l'agence <<" +gestionContratCptForm.getCodStrcStrc()+ ">>. Exception : ",e);  
         //   logger.error("Exception : ",e);  
            ActionMessage actionMessage = 
                new ActionMessage("exception.generique", 
                                  erreur.getDescription());
            actionMessages.add("Erreur ", actionMessage);
            this.saveMessages(request, actionMessages);
            return mapping.findForward("error");
        }

    }

    public ActionForward rechercherDemandeur(ActionMapping mapping, 
                                             ActionForm form, 
                                             HttpServletRequest request, 
                                             HttpServletResponse response) throws IOException, 
                                                                                  ServletException {
        ActionMessages actionMessages = new ActionMessages();
        GestionContratCptForm gestionContratCptForm = 
            (GestionContratCptForm)form;
        try {
            Tuteur tuteur = new Tuteur();
            Long varTypePersonne = 
                Long.valueOf(gestionContratCptForm.getContrat().getClient().getTypePers().getCodTperTper());
            Long varCodCatpCatp = 
                Long.valueOf(gestionContratCptForm.getContrat().getClient().getPersonne().getCategoriePersonne().getCodCatpCatp());
            //----------- Si la personne est une entite co-titulaire
            if ((varTypePersonne.toString()).equals(Constants.ENTCOTITULAIRE)) {
                //-----------------------------------
                // chercher les personnes cotitulaires
                GetListMembreCotitulaireCmd getListMembreCotitulaireCmd = 
                    new GetListMembreCotitulaireCmd();
                PersonneStrc personneStrc = new PersonneStrc();
                personneStrc.setCodTpceTpce(gestionContratCptForm.getContrat().getClient().getPersonne().getTypePiece().getCodTpceTpce());
                personneStrc.setNumPcePers(gestionContratCptForm.getContrat().getClient().getPersonne().getNumPcePers());


                Listes lisCotitulaire = 
                    (Listes)getListMembreCotitulaireCmd.execute(personneStrc);
                if (!lisCotitulaire.hasError()) {

                    gestionContratCptForm.setListCotitulaires(lisCotitulaire.getList());
                    // vérifier si le demandeur existe dans les personnes cotitulaires
                    CoTitulaire cotitulaire1 = new CoTitulaire();
                    for (Iterator it = 
                         gestionContratCptForm.getListCotitulaires().iterator(); 
                         it.hasNext(); ) {
                        CoTitulaire cotitulaire = (CoTitulaire)it.next();
                        if (cotitulaire.getClient().getNumSeqPers().longValue() == 
                            gestionContratCptForm.getContrat().getClient().getNumSeqPers().longValue()) {
                            gestionContratCptForm.setExistCotit(true);
                            cotitulaire1 = cotitulaire;

                        }
                    }

                    if (gestionContratCptForm.isExistCotit()) {
                        gestionContratCptForm.setNumSeqCliCotitulaire(cotitulaire1.getClient().getNumSeqPers().toString());
                        gestionContratCptForm.setTypeCotitulaire(cotitulaire1.getCodTcotCoti());
                        gestionContratCptForm.setTypeSignatureCotitulaire(cotitulaire1.getCodSigCoti());

                        gestionContratCptForm.setNomNomPersClient(gestionContratCptForm.getContrat().getClient().getPersonne().getNomNomPers());
                        gestionContratCptForm.setNomPrnPersClient(gestionContratCptForm.getContrat().getClient().getPersonne().getNomPrnPers());

                        gestionContratCptForm.setTypPcePersDemandeur(cotitulaire1.getPersonne().getTypePiece().getCodTpceTpce().toString());
                        gestionContratCptForm.setNumPcePersDemandeur(cotitulaire1.getPersonne().getNumPcePers());
                        gestionContratCptForm.setNomNomPersDemandeur(cotitulaire1.getPersonne().getNomNomPers());
                        gestionContratCptForm.setNomPrnPersDemandeur(cotitulaire1.getPersonne().getNomPrnPers());
                        gestionContratCptForm.setDroitDemandeur(true);
                    } else {
                        gestionContratCptForm.setDroitDemandeur(false);
                    }
                } else {
                    List listErreur = lisCotitulaire.getErrors();
                    for (Iterator it = listErreur.iterator(); it.hasNext(); ) {
                        com.oxia.fwk.core.Error erreur = 
                            (com.oxia.fwk.core.Error)it.next();
                        ActionMessage actionMessage = 
                            new ActionMessage("exception.generique", 
                                              erreur.getDescription());
                        actionMessages.add("Erreur ", actionMessage);
                    }
                    this.saveMessages(request, actionMessages);
                    return mapping.findForward("error");

                }

            } else { // cas d'une personne physique
                //----------- Si la personne est mineur
                if (varCodCatpCatp.toString().equalsIgnoreCase(Constants.COD_CATEGORIE_MINEUR)) {
                    GetTuteurCmd getTuteurCmd = new GetTuteurCmd();
                    PersonneStrc personneStrc = new PersonneStrc();
                    personneStrc.setCodStrcStrc(Long.valueOf(gestionContratCptForm.getCodStrcStrc()));
                    personneStrc.setCodTpceTpce(Long.valueOf(gestionContratCptForm.getTypPcePersDemandeur()));
                    personneStrc.setNumPcePers(gestionContratCptForm.getNumPcePersDemandeur());

                    tuteur = (Tuteur)getTuteurCmd.execute(personneStrc);
                    if (tuteur != null && !tuteur.hasError() && 
                        tuteur.isIsTuteur()) {
                        gestionContratCptForm.setNomNomPersDemandeur(tuteur.getPersonneTuteur().getNomNomPers());
                        gestionContratCptForm.setNomPrnPersDemandeur(tuteur.getPersonneTuteur().getNomPrnPers());

                        for (Iterator it2 = 
                             tuteur.getListeDesMineures().iterator(); 
                             it2.hasNext(); ) {
                            Personne personne = (Personne)it2.next();
                            if (personne.getNumSeqPers().intValue() == 
                                gestionContratCptForm.getClient().getPersonne().getNumSeqPers().intValue()) {
                                gestionContratCptForm.setDroitDemandeur(true);
                                break;
                            } else {
                                gestionContratCptForm.setDroitDemandeur(false);
                            }
                        }

                    } else {
                        gestionContratCptForm.setDroitDemandeur(false);
                        if (tuteur.hasError()) {
                            List listErreur = tuteur.getErrors();
                            for (Iterator it = listErreur.iterator(); 
                                 it.hasNext(); ) {
                                com.oxia.fwk.core.Error erreur = 
                                    (com.oxia.fwk.core.Error)it.next();
                                ActionMessage actionMessage = 
                                    new ActionMessage("exception.generique", 
                                                      erreur.getDescription());
                                actionMessages.add("Erreur ", actionMessage);
                            }
                            this.saveMessages(request, actionMessages);
                            return mapping.findForward("error");
                        }
                    }


                } else if ((gestionContratCptForm.getTypPcePersDemandeur().equalsIgnoreCase(gestionContratCptForm.getTypPcePers())) && 
                           (gestionContratCptForm.getNumPcePersDemandeur().equalsIgnoreCase(gestionContratCptForm.getNumPcePers()))) {
                    gestionContratCptForm.setTypPcePersDemandeur(gestionContratCptForm.getTypPcePers());
                    gestionContratCptForm.setNumPcePersDemandeur(gestionContratCptForm.getNumPcePers());
                    gestionContratCptForm.setNomNomPersDemandeur(gestionContratCptForm.getNomNomPers());
                    gestionContratCptForm.setNomPrnPersDemandeur(gestionContratCptForm.getNomPrnPers());
                    gestionContratCptForm.setDroitDemandeur(true);


                } else {
                    gestionContratCptForm.setDroitDemandeur(false);
                }
            }

            return mapping.findForward("success");


        } catch (Exception e) {
            com.oxia.fwk.core.Error erreur = new com.oxia.fwk.core.Error();
            StringBuffer text = 
                new StringBuffer("la transaction est Interrompu, une erreur dans GestionContratCptAction / Dispatch Action :rechercherDemandeur ");
            text.append(e.toString());
            erreur.setCode("200");
            erreur.setDescription(text.toString());
            logger.error("Erreur au niveau de l'agence <<" +gestionContratCptForm.getCodStrcStrc()+ ">>. Exception : ",e);  
      //      logger.error("Exception : ",e);  
            ActionMessage actionMessage = 
                new ActionMessage("exception.generique", 
                                  erreur.getDescription());
            actionMessages.add("Erreur ", actionMessage);
            this.saveMessages(request, actionMessages);
            return mapping.findForward("error");
        }
    }

    public ActionForward changerCategorie(ActionMapping mapping, 
                                          ActionForm form, 
                                          HttpServletRequest request, 
                                          HttpServletResponse response) throws IOException, 
                                                                               ServletException {
        ActionMessages actionMessages = new ActionMessages();
        GestionContratCptForm gestionContratCptForm = 
            (GestionContratCptForm)form;
        try {

            if (gestionContratCptForm.getCodetrait().equalsIgnoreCase("CC") || 
                gestionContratCptForm.getCodetrait().equalsIgnoreCase("TC")) { /// Changement categorie

                if (gestionContratCptForm.getCodetrait().equalsIgnoreCase("TC")) { /// cas transfert
                    codeOperation=Constants.COD_OPER_TRANSF_CPT;
                    codTach      =Constants.COD_TACH_TRANSF_CPT;
                    PersonneStrc personneStrc = new PersonneStrc();
                    personneStrc.setCodTpceTpce(Long.valueOf(gestionContratCptForm.getTypPcePersN()));
                    personneStrc.setNumPcePers(gestionContratCptForm.getNumPcePersN());

                    GetPersonneCptCmd getPersonneCptCmd =  new GetPersonneCptCmd();
                    PersonneCpt personneCpt = (PersonneCpt)getPersonneCptCmd.execute(personneStrc);
                    if (personneCpt.hasError()) {
                        List listErreur = personneCpt.getErrors();
                        for (Iterator it = listErreur.iterator(); it.hasNext(); 
                        ) {
                            com.oxia.fwk.core.Error erreur = 
                                (com.oxia.fwk.core.Error)it.next();
                            ActionMessage actionMessage = 
                                new ActionMessage("exception.generique", 
                                                  erreur.getDescription());
                            actionMessages.add("Erreur ", actionMessage);
                        }
                        this.saveMessages(request, actionMessages);
                        return mapping.findForward("error");
                    }
                    //Client client =new Client();
                    //client.setPersonne(personne);
                    ///?      gestionContratCptForm.getContrat().setClient(personneCpt.getClient());

                    ParamInsertContrat paramInsertContrat = new ParamInsertContrat();

                    /* cas d'un transfert de PEE */
                    if (Long.valueOf(gestionContratCptForm.getCodPrdPrd()).intValue() == 
                        Constants.COD_PRD_PRD_PEE.intValue()) {
                        if (personneCpt.getPersonne().getCategoriePersonne().getCodCatpCatp().equals(Constants.COD_CATEGORIE_MINEUR)) {
                            GetTuteurCmd getTuteurCmd = new GetTuteurCmd();
                            personneStrc.setCodStrcStrc(Long.valueOf(gestionContratCptForm.getCodStrcStrc()));
                            personneStrc.setCodTpceTpce(Long.valueOf(gestionContratCptForm.getTypPcePersDemandeur()));
                            personneStrc.setNumPcePers(gestionContratCptForm.getNumPcePersDemandeur());
                            Tuteur tuteur =(Tuteur)getTuteurCmd.execute(personneStrc);
                            if (tuteur.isIsTuteur()) {
                                paramInsertContrat.setPersonneTuteur(tuteur.getPersonneTuteur());
                            } else
                                gestionContratCptForm.setDroitDemandeur(false);
                        } else {
                            gestionContratCptForm.setDroittransfert(false);
                        }
                    }
                    /* s'il a déja un PEE ou un PEL */
                    PersProduit persProduit = new PersProduit();
                    persProduit.setCodPrdPrd(Long.valueOf(gestionContratCptForm.getCodPrdPrd()));
                    persProduit.setNumSeqPers(personneCpt.getPersonne().getNumSeqPers());
                    GetNbrProduitByPersCmd getNbrProduitByPersCmd = 
                        new GetNbrProduitByPersCmd();
                    PrimitiveVO l = (PrimitiveVO)getNbrProduitByPersCmd.execute(persProduit);
                    if (l.hasError()) {
                        List listErreur = l.getErrors();
                        for (Iterator it = listErreur.iterator(); it.hasNext(); 
                        ) {
                            com.oxia.fwk.core.Error erreur = 
                                (com.oxia.fwk.core.Error)it.next();
                            ActionMessage actionMessage = 
                                new ActionMessage("exception.generique", 
                                                  erreur.getDescription());
                            actionMessages.add("Erreur ", actionMessage);
                        }
                        this.saveMessages(request, actionMessages);
                        return mapping.findForward("error");
                    }
                    if (l.getVLong().intValue() > 0) {
                        gestionContratCptForm.setDroittransfert(false);
                    }
                    if (gestionContratCptForm.isDroittransfert()) {

                        paramInsertContrat.setContratCpt(gestionContratCptForm.getContrat());

                        ValueObject vo = MajDetailCat( request, gestionContratCptForm,paramInsertContrat);

                        if (vo.hasError()) {
                            List listErreur = vo.getErrors();                    
                            for (Iterator it = listErreur.iterator(); it.hasNext(); ) {
                                com.oxia.fwk.core.Error erreur = (com.oxia.fwk.core.Error)it.next();
                                ActionMessage actionMessage = new ActionMessage("exception.generique",erreur.getDescription());
                                actionMessages.add("Erreur ", actionMessage);
                            }    
                            this.saveMessages(request, actionMessages);
                            return mapping.findForward("error");

                        }/*else{
                            MAJContratClientTransfertEpargneCmd mAJContratClientTransfertEpargne = new MAJContratClientTransfertEpargneCmd();
                             vo = mAJContratClientTransfertEpargne.execute(paramInsertContrat);
                        }*/
                        
                        gestionContratCptForm.clearForm();
                        if (persProduit.getCodPrdPrd().intValue() == Constants.COD_PRD_PRD_PEE.intValue()) {
                            return mapping.findForward("imprimerTransfertPEE");
                        } else
                            return mapping.findForward("imprimerTransfertPEL");
                    } else
                        return mapping.findForward("success");

                } else {/// changement cat_reg
                    codeOperation=Constants.COD_OPER_CHANG_CAT_RGM;
                    codTach      =Constants.COD_TACH_CHANG_CAT_RGM;
                    ValueObject vob = MajDetailCat( request, gestionContratCptForm, new ValueObject());
                    if (vob.hasError()) {
                        List listErreur = vob.getErrors();                    
                        for (Iterator it = listErreur.iterator(); it.hasNext(); ) {
                            com.oxia.fwk.core.Error erreur = (com.oxia.fwk.core.Error)it.next();
                            ActionMessage actionMessage = new ActionMessage("exception.generique",erreur.getDescription());
                            actionMessages.add("Erreur ", actionMessage);
                        }    
                        this.saveMessages(request, actionMessages);
                        return mapping.findForward("error");

                    }                    
                    if ((Long.valueOf(gestionContratCptForm.getCodPrdPrd())).intValue() == Constants.COD_PRD_PRD_PEE.intValue()) {
                        gestionContratCptForm.clearForm();
                        request.getSession().removeAttribute("gestionContratCptForm");
                        return mapping.findForward("imprimerChangCatRgmPEE");
                    } else{
                        gestionContratCptForm.clearForm();
                        request.getSession().removeAttribute("gestionContratCptForm");
                        return mapping.findForward("imprimerChangCatRgm");
                    }
                }

            } else if (gestionContratCptForm.getCodetrait().equalsIgnoreCase("CR")) { /// Calcul reliquat
                validation(gestionContratCptForm, request);
                gestionContratCptForm.clearForm();
                request.getSession().removeAttribute("gestionContratCptForm");
                return mapping.findForward("imprimerReliquat");
                //return mapping.findForward("success");
            } else
                return mapping.findForward("error");
        } catch (Exception e) {
            com.oxia.fwk.core.Error erreur = new com.oxia.fwk.core.Error();
            StringBuffer text = 
                new StringBuffer("la transaction est Interrompu, une erreur dans GestionContratCptAction / Dispatch Action :changerCategorie ");
            text.append(e.toString());
            erreur.setCode("200");
            erreur.setDescription(text.toString());
            logger.error("Erreur au niveau de l'agence <<" +gestionContratCptForm.getCodStrcStrc()+ ">>. Exception : ",e);  
            ActionMessage actionMessage = 
                new ActionMessage("exception.generique", 
                                  erreur.getDescription());
            actionMessages.add("Erreur ", actionMessage);
            this.saveMessages(request, actionMessages);
            return mapping.findForward("error");
        }

    }

    private ValueObject MajDetailCat( HttpServletRequest request, 
                              GestionContratCptForm gestionContratCptForm, ValueObject paramInsertContrat) throws IOException, 
                                                                                  ServletException {

        ParamMiseAjourDetailcatCpt paramMiseAjourDetailcatCpt = new ParamMiseAjourDetailcatCpt();

        Categorie nouvCategorie = new Categorie();
        CategorieId categorieId = new CategorieId();

        /**nouvelle categorie***/
        categorieId.setCodCatCat(gestionContratCptForm.getCodeCategorieEpargne());
        categorieId.setCodPrdPrd(Long.valueOf(gestionContratCptForm.getCodPrdPrd()));
        if (gestionContratCptForm.getCodeRegimeEpargne().equalsIgnoreCase("")) {
            gestionContratCptForm.setCodeRegimeEpargne(gestionContratCptForm.getCodRgmRgm());
            categorieId.setCodRgmRgm(Long.valueOf(gestionContratCptForm.getCodRgmRgm()));
        } else {
            categorieId.setCodRgmRgm(Long.valueOf(gestionContratCptForm.getCodeRegimeEpargne()));
        }
        nouvCategorie.setCategorieId(categorieId);

        paramMiseAjourDetailcatCpt.setDetailCatCpt(gestionContratCptForm.getDetailCatCpt());
        paramMiseAjourDetailcatCpt.setContratCpt(gestionContratCptForm.getContrat());
        paramMiseAjourDetailcatCpt.setNouvelleCategorie(nouvCategorie);
        paramMiseAjourDetailcatCpt.setType(gestionContratCptForm.getCodetrait());
        
//        gestionContratCptForm.clearForm();

        ValueObject v = InsertTrace(request, gestionContratCptForm, paramInsertContrat, paramMiseAjourDetailcatCpt);

        validation(gestionContratCptForm, request);
        return v;
    }

    private ValueObject InsertTrace( HttpServletRequest request, 
                             GestionContratCptForm gestionContratCptForm, ValueObject paramInsertContrat, ValueObject paramMiseAjourDetailcatCpt ) {

        ValueObject VO =new ValueObject();
        
        /* MAJ de la Table DETAIL_CAT_CPT*/
        MiseAJourDetailCatContratCmd miseAJourDetailCatContratCmd = new MiseAJourDetailCatContratCmd();
        VO = (ValueObject)miseAJourDetailCatContratCmd.execute(paramMiseAjourDetailcatCpt);
        if (VO.hasError()) {
            return VO;
        }

        PersonneStrc personneStrc = new PersonneStrc();
        GetPersonneCptCmd getPersonneCptCmd =  new GetPersonneCptCmd();
        if (gestionContratCptForm.getCodetrait().equalsIgnoreCase("TC")) {
            personneStrc.setCodTpceTpce(Long.valueOf(gestionContratCptForm.getTypPcePersN()));
            personneStrc.setNumPcePers(gestionContratCptForm.getNumPcePersN());
            PersonneCpt personneCpt0 = (PersonneCpt)getPersonneCptCmd.execute(personneStrc);
            Client client = new Client();
            client.setNumSeqPers(personneCpt0.getPersonne().getNumSeqPers());
            client.setPersonne(personneCpt0.getPersonne());
            ((ParamInsertContrat)paramInsertContrat).getContratCpt().setClient(client);
        }

        ParamModificationDonneesVo paramModificationDonneesVo = new ParamModificationDonneesVo();
        paramModificationDonneesVo.setContratModifie(gestionContratCptForm.getContrat());
        ParamAgence paramAgence = (ParamAgence)request.getSession().getAttribute("paramAgBNA");
        paramModificationDonneesVo.setMatriculeUser(paramAgence.getNumMatrUser().toString());
        TypeModification typeModification = new TypeModification();
        if (gestionContratCptForm.getCodetrait().equalsIgnoreCase("TC")) {
            /* trace du transfert */
            typeModification.setCodCodModf(Constants.COD_MODIF_TRANSFERT_EPARGN);
        } else if (gestionContratCptForm.getCodetrait().equalsIgnoreCase("CC")) {
            typeModification.setCodCodModf(Constants.COD_MODIF_CHANG_CAT_EPARGN);
        }
        paramModificationDonneesVo.setTypeModification(typeModification);

        //GetPersonneCptCmd getPersonneCptCmd = new GetPersonneCptCmd();
        //PersonneStrc personneStrc = new PersonneStrc();
        personneStrc.setCodStrcStrc(Long.valueOf(gestionContratCptForm.getCodStrcStrc()));
        personneStrc.setCodTpceTpce(Long.valueOf(gestionContratCptForm.getTypPcePers()));
        personneStrc.setNumPcePers(gestionContratCptForm.getNumPcePers());
        PersonneCpt personneCpt = (PersonneCpt)getPersonneCptCmd.execute(personneStrc);

        if (personneCpt.hasError()) {
            List listErreur = personneCpt.getErrors();
            for (Iterator it = listErreur.iterator(); it.hasNext(); ) {
                VO.getErrors().add((com.oxia.fwk.core.Error)it.next());
            }
        }

        paramModificationDonneesVo.setPersonneModifie(personneCpt.getPersonne());
        
        if (gestionContratCptForm.getExecutant()!= null && gestionContratCptForm.getExecutant().equalsIgnoreCase("DQMRP")){
            paramModificationDonneesVo.setCodeStructure(paramModificationDonneesVo.getContratModifie().getContratCptId().getCodStrcStrc());
        }else{
            paramModificationDonneesVo.setCodeStructure(paramAgence.getCodStrcStrc());   
        }
        /* gestion du transfert et du changement de categorie/epargne */
        GestionEpargneVO gestionEpargneVO= new GestionEpargneVO();
        if (gestionContratCptForm.getCodetrait().equalsIgnoreCase("TC")) {
        gestionEpargneVO.setParamInsertContrat((ParamInsertContrat)paramInsertContrat);
        }
        gestionEpargneVO.setParamModificationDonneesVo(paramModificationDonneesVo);
        gestionEpargneVO.setType(gestionContratCptForm.getCodetrait());
        
        gestionEpargneVO.setParamMiseAjourDetailcatCpt((ParamMiseAjourDetailcatCpt)paramMiseAjourDetailcatCpt);

        GestionEpargneCmd gestionEpargneCmd = new GestionEpargneCmd();
        return (ValueObject)gestionEpargneCmd.execute(gestionEpargneVO);
       
    }

    public void validation(GestionContratCptForm gestionContratCptForm, 
                           HttpServletRequest request) throws IOException, 
                                                              ServletException {

        ParamAgence paramAgence = new ParamAgence();
        
        paramAgence = (ParamAgence)request.getSession().getAttribute("paramAgBNA"); /// structure de l'agent qui fait la saisie
        
        request.getSession().setAttribute("codStrcStrcExec", 
                                          paramAgence.getCodStrcStrc());

        request.getSession().setAttribute("codStrcStrc", 
                                          Long.valueOf(gestionContratCptForm.getCodStrcStrc()));
        request.getSession().setAttribute("codPrdPrd", 
                                          Long.valueOf(gestionContratCptForm.getCodPrdPrd()));
        request.getSession().setAttribute("numCcptCcpt", 
                                          Long.valueOf(gestionContratCptForm.getNumCcptCcpt()));
        /// cas epargne menage
        if(gestionContratCptForm.getCodPrdPrd().equalsIgnoreCase(StrHandler.lpad(Constants.COD_PRD_PRD_PEM.toString(),'0', 4))){
            gestionContratCptForm.setCodeRegimeEpargne("2");
        }
        if (gestionContratCptForm.getCodetrait().equalsIgnoreCase("CR")) { /// Calcul reliquat

            request.getSession().setAttribute("NCatRgm", 
                                              "Régime " + gestionContratCptForm.getCodeRegimeEpargne() + 
                                              " ans, Catégorie " + 
                                              gestionContratCptForm.getCodeCategorieEpargne());
            request.getSession().setAttribute("reliquat", 
                                              gestionContratCptForm.getReliquat());
            // request.getSession().setAttribute("CatRgm" ,"Régime" +gestionContratCptForm.getCodRgmRgm()+" ans, Catégorie "+gestionContratCptForm.getCodCatCat());
            //return mapping.findForward("imprimerReliquat");

        } else if (!((Long.valueOf(gestionContratCptForm.getCodPrdPrd())).intValue() == Constants.COD_PRD_PRD_PEE.intValue()))  { //changement categorie / plan
            request.getSession().setAttribute("NRgm", 
                                              gestionContratCptForm.getCodeRegimeEpargne());
            request.getSession().setAttribute("NCat", 
                                              gestionContratCptForm.getCodeCategorieEpargne());
            request.getSession().setAttribute("ARgm", 
                                              gestionContratCptForm.getLibCatCat());
            request.getSession().setAttribute("ACat", 
                                              gestionContratCptForm.getLibRgmRgm());
            request.getSession().setAttribute("MontCaptCapt", 
                                              (Long.valueOf(new Double(new Double(StrHandler.strWithoutBlanck(gestionContratCptForm.getNouvMontCaptCat())).doubleValue() * 
                                                                   1000).longValue())));
            request.getSession().setAttribute("MontVersCat", 
                                              (Long.valueOf(new Double(new Double(StrHandler.strWithoutBlanck(gestionContratCptForm.getNouvMontVersCat())).doubleValue() * 
                                                                   1000).longValue())));

            // return mapping.findForward("imprimerChangCatRgm");
        } else /*if (gestionContratCptForm.getCodetrait().equalsIgnoreCase("TC"))*/ { // transfert contrat
            request.getSession().setAttribute("NRgm", 
                                              gestionContratCptForm.getCodeRegimeEpargne());
            request.getSession().setAttribute("NCat", 
                                              gestionContratCptForm.getCodeCategorieEpargne());
            request.getSession().setAttribute("ARgm", 
                                              gestionContratCptForm.getLibCatCat());
            request.getSession().setAttribute("ACat", 
                                              gestionContratCptForm.getLibRgmRgm());
            request.getSession().setAttribute("MontCaptCapt", 
                                              (Long.valueOf(new Double(new Double(StrHandler.strWithoutBlanck(gestionContratCptForm.getNouvMontCaptCat())).doubleValue() * 
                                                                   1000).longValue())));
            request.getSession().setAttribute("MontVersCat", 
                                              (Long.valueOf(new Double(new Double(StrHandler.strWithoutBlanck(gestionContratCptForm.getNouvMontVersCat())).doubleValue() * 
                                                                   1000).longValue())));
            request.getSession().setAttribute("NomPrnTuteur", 
                                              gestionContratCptForm.getNomNomPersDemandeur() + 
                                              " " + 
                                              gestionContratCptForm.getNomPrnPersDemandeur());
            request.getSession().setAttribute("TypePceTuteur", 
                                              gestionContratCptForm.getTypPcePersDemandeur());
            request.getSession().setAttribute("NumCinTuteur", 
                                              gestionContratCptForm.getNumPcePersDemandeur());
            if (gestionContratCptForm.getMontBrsCat() != null)
                request.getSession().setAttribute("MontBrsCat", 
                                                  (Long.valueOf(new Double(new Double(StrHandler.strWithoutBlanck(gestionContratCptForm.getMontBrsCat())).doubleValue() * 
                                                                       1000).longValue())));

        }

        //else return mapping.findForward("error");        

    }


    public ActionForward chercherTuteur(ActionMapping mapping, ActionForm form, 
                                        HttpServletRequest request, 
                                        HttpServletResponse response) throws IOException, 
                                                                             ServletException {
        ActionMessages actionMessages = new ActionMessages();
        GestionContratCptForm gestionContratCptForm = 
            (GestionContratCptForm)form;
        try {
            GetTuteurCmd getTuteurCmd = new GetTuteurCmd();
            PersonneStrc personneStrc = new PersonneStrc(); //Vo input 
            personneStrc.setCodTpceTpce(Long.valueOf(gestionContratCptForm.getTypPcePersDemandeur()));
            personneStrc.setNumPcePers(gestionContratCptForm.getNumPcePersDemandeur());
            Tuteur tuteur = new Tuteur(); //Vo output
            tuteur = (Tuteur)getTuteurCmd.execute(personneStrc);
            if (tuteur.hasError()) {
                List listErreur = tuteur.getErrors();
                for (Iterator it = listErreur.iterator(); it.hasNext(); ) {
                    com.oxia.fwk.core.Error erreur = 
                        (com.oxia.fwk.core.Error)it.next();
                    ActionMessage actionMessage = 
                        new ActionMessage("exception.generique", 
                                          erreur.getDescription());
                    actionMessages.add("Erreur ", actionMessage);
                }
                this.saveMessages(request, actionMessages);
                return mapping.findForward("error");
            }
            if (tuteur.getPersonneTuteur() != null) {
                gestionContratCptForm.setNomTuteur(tuteur.getPersonneTuteur().getNomNomPers());
                gestionContratCptForm.setPrenomTuteur(tuteur.getPersonneTuteur().getNomPrnPers());
                gestionContratCptForm.setNumSeqTuteur(tuteur.getPersonneTuteur().getNumSeqPers().toString());
                gestionContratCptForm.setListMineurs(tuteur.getListeDesMineures());
                gestionContratCptForm.setIsTuteur(tuteur.isIsTuteur());
                gestionContratCptForm.setAlertTuteur("TuteurExistant");
                gestionContratCptForm.setNomNomPersDemandeur(tuteur.getPersonneTuteur().getNomNomPers());
                gestionContratCptForm.setNomPrnPersDemandeur(tuteur.getPersonneTuteur().getNomPrnPers());
                if (!tuteur.isIsTuteur()) {
                    gestionContratCptForm.setAlert("personneInexistante");
                    gestionContratCptForm.setMessageNbreMineurs(" Pas de mineurs en charge de ce tuteur.");
                    gestionContratCptForm.setDroitDemandeur(false);
                } else {
                    gestionContratCptForm.setMessageNbreMineurs(tuteur.getListeDesMineures().size() + 
                                                                "  mineur(s) en charge de ce tuteur.");
                    gestionContratCptForm.setNombreMineurs(tuteur.getListeDesMineures().size());
                    gestionContratCptForm.setDroitDemandeur(true);
                }
            } else {
                //gestionContratCptForm.setOpenTabsheetProduit("true");
                gestionContratCptForm.setAlertTuteur("TuteurInexistant");
                gestionContratCptForm.setAlert("personneInexistante");
                gestionContratCptForm.setDroitDemandeur(false);

            }

            return mapping.findForward("success");
        } catch (Exception e) {
            com.oxia.fwk.core.Error erreur = new com.oxia.fwk.core.Error();
            StringBuffer text = 
                new StringBuffer("la transaction est Interrompu, une erreur dans GestionContratCptAction / Dispatch Action :chercherTuteur ");
            text.append(e.toString());
            erreur.setCode("200");
            erreur.setDescription(text.toString());
            logger.error("Erreur au niveau de l'agence <<" +gestionContratCptForm.getCodStrcStrc()+ ">>. Exception : ",e);  
        //    logger.error("Exception : ",e);  
            ActionMessage actionMessage = 
                new ActionMessage("exception.generique", 
                                  erreur.getDescription());
            actionMessages.add("Erreur ", actionMessage);
            this.saveMessages(request, actionMessages);
            return mapping.findForward("error");
        }


    }

    public ActionForward rechercherContratAcloturer(ActionMapping mapping, 
                                                    ActionForm form, 
                                                    HttpServletRequest request, 
                                                    HttpServletResponse response) throws IOException, 
                                                                                         ServletException {


        ActionMessages actionMessages = new ActionMessages();

        GestionContratCptForm gestionContratCptForm = 
            (GestionContratCptForm)form;
        
        String forward = "";
        /*******recherche du contrat**********/
        try {

            ContratCptId contratCptId = new ContratCptId();
            contratCptId.setCodStrcStrc(Long.valueOf(gestionContratCptForm.getContratView().getCodStrcStrc()));
            contratCptId.setCodPrdPrd(Long.valueOf(gestionContratCptForm.getContratView().getCodPrdPrd()));
            contratCptId.setNumCcptCcpt(Long.valueOf(gestionContratCptForm.getContratView().getNumCcptCcpt()));
            //recherche du contrat
            GetContratEtatCmd getContratEtatCmd = new GetContratEtatCmd();
            ContratCptMandat contratAclot = 
                (ContratCptMandat)getContratEtatCmd.execute(contratCptId);
            
                     
            if (contratAclot.getContratCpt() == null) {
                gestionContratCptForm.setAlert("Contrat Inéxistant, veuillez verifier votre saisie, SVP");
                gestionContratCptForm.setAlertCloture("true");
                
                //------- Verifier s'il y a une restriction sur le contrat -------------------//
            } else {
                gestionContratCptForm.getContratView().setContratCpt(contratAclot.getContratCpt());
                gestionContratCptForm.setExistCpt(true);
                /*verif etat compte*/

                // cas de la réactivation....
                if(!gestionContratCptForm.getCodetrait().equalsIgnoreCase("RC")){
                
                forward = "indexCloture";
                /*cas prise en charge cloture*/
                if (gestionContratCptForm.getCodetrait().equalsIgnoreCase("PC")) {
                    if (!contratAclot.isVerifEtat()) {
                        gestionContratCptForm.setAlertCloture("true"); 
                        gestionContratCptForm.setAlert("contrat Non Valide");
                    } 

                }
                /*cas validation / annulation cloture*/
                if ((gestionContratCptForm.getCodetrait().equalsIgnoreCase("VC"))||(gestionContratCptForm.getCodetrait().equalsIgnoreCase("AC"))) {
                    if ((!contratAclot.getContratCpt().getCodEtatCcpt().equalsIgnoreCase(Constants.COD_ETAT_CPT_SEMIACTIF))) {
                        gestionContratCptForm.setAlertCloture("true"); 
                        gestionContratCptForm.setAlert("contratNoncloturer");
                    } 
                }
                                
                /*verif solde*/
                if (contratAclot.getContratCpt().getMontSoldCcpt() != null && 
                    contratAclot.getContratCpt().getMontSoldCcpt().intValue() != 
                    0) {
                    gestionContratCptForm.setAlertCloture("true"); 
                    gestionContratCptForm.setAlert(gestionContratCptForm.getAlert()+" Compte non soldé,");
                }
                /*verif blocage*/
                if (contratAclot.getContratCpt().getMontBlocCcpt() != null && 
                    contratAclot.getContratCpt().getMontBlocCcpt().intValue() != 
                    0) {
                    gestionContratCptForm.setAlertCloture("true"); 
                    gestionContratCptForm.setAlert(gestionContratCptForm.getAlert()+" blocage existant, ");
                }
                /*verif facilité caisse*/
                if ((contratAclot.getContratCpt().getMontAutCcpt() != null) && 
                    (contratAclot.getContratCpt().getMontAutCcpt().intValue() != 
                     0)) {
                    gestionContratCptForm.setAlertCloture("true"); 
                    gestionContratCptForm.setAlert(gestionContratCptForm.getAlert()+" autorisation existante,");
                }

                /*verif demandes cheques*/
                
                GetListDemandesChequesCmd getListDemandesChequesCmd = 
                    new GetListDemandesChequesCmd();
                ParamDemandeCheque paramDemandeCheque = 
                    new ParamDemandeCheque();
                ContratPersonne contratPersonne = new ContratPersonne();
                contratPersonne.setContratCptId(contratCptId);
                PersonneStrc personneStrc=new PersonneStrc();
                personneStrc.setCodStrcStrc(contratCptId.getCodStrcStrc());
                contratPersonne.setPersonneId(personneStrc);
                paramDemandeCheque.setContratPersonne(contratPersonne);
                ListesDemandesCheques l1 = 
                    (ListesDemandesCheques)getListDemandesChequesCmd.execute(paramDemandeCheque);
                    
                if ((l1 != null)&&(l1.getListeGenerale()!=null)) {
                    List listechequeView = new ArrayList();
                    listechequeView = 
                            traiterListedemandes(l1.getListeGenerale(), 
                                                 gestionContratCptForm);
                    if((listechequeView!=null)&&(listechequeView.size()>0)){
                        gestionContratCptForm.setDemandeCheques(listechequeView);
                        gestionContratCptForm.setAlertCloture("true"); 
                        gestionContratCptForm.setAlert(gestionContratCptForm.getAlert()+" demande cheque existante,");
                    }
                   
                    
                }
                /*verif  chequiers*/
                GetChequiersCmd getChequiersCmd = new GetChequiersCmd();
                Paramchequiers paramchequiers = new Paramchequiers();
                paramchequiers.setContratCptId(contratCptId);
                Listes l4 = (Listes)getChequiersCmd.execute(paramchequiers);
                if ((l4.getList() != null)&&(l4.getList().size()!=0) ){
                    gestionContratCptForm.setChequiers(l4.getList());
                    
                    
                }
                /*verif  cartes*/
              /*  GetListCartesBancairesCmd getListCartesBancairesCmd = 
                    new GetListCartesBancairesCmd();
                ParamRechercheDemandeCarte paramRechercheDemandeCarte = 
                    new ParamRechercheDemandeCarte();
                paramRechercheDemandeCarte.setContratCptId(contratCptId);
                Listes l2 = 
                    (Listes)getListCartesBancairesCmd.execute(paramRechercheDemandeCarte);
                if ((l2.getList() != null)&&(l2.getList().size()!=0)) {
                    gestionContratCptForm.setCarteBancaires(l2.getList());
                    gestionContratCptForm.setAlertCloture("true"); 
                    gestionContratCptForm.setAlert(gestionContratCptForm.getAlert()+" carte existante");
                    
                }*/
                /*verif  demandes cartes*/
               /* ParamRechercheDemandeCarte paramRechercheDemandeCarte1=new ParamRechercheDemandeCarte();
                GetListDemandesCartesCmd getListDemandesCartesCmd=new GetListDemandesCartesCmd();
                paramRechercheDemandeCarte1.setContratCptId(contratCptId);
                Listes l3=(Listes)getListDemandesCartesCmd.execute(paramRechercheDemandeCarte1);
                if ((l3.getList() != null )&&(l3.getList().size() != 0 )) {
                    gestionContratCptForm.setDemandeCarteBancaires(l3.getList());
                    gestionContratCptForm.setAlertCloture("true"); 
                    gestionContratCptForm.setAlert(gestionContratCptForm.getAlert()+" demandes carte existante");
                
                }*/
                
                /*verif credit*/
                GetListCreditCmd getListCreditCmd=new GetListCreditCmd(); 
                ValueObject vo=(ValueObject)getListCreditCmd.execute(contratCptId);
                Listes listecred=(Listes)vo;
                if ((listecred!=null) &&(listecred.getNombre()!=0 ) ){
                    gestionContratCptForm.setAlertCloture("true"); 
                    gestionContratCptForm.setAlert(gestionContratCptForm.getAlert()+" crédit existant");
                }
                
                }else{
                    forward = "indexBlocageCpt";                
                    if(contratAclot.getContratCpt().getCodEtatCcpt().equals("V")){
                       gestionContratCptForm.setAlertCloture("true"); 
                       gestionContratCptForm.setAlert("ContratEncoreValide"); 
                       
                    }
                }
                
                /*verif placement*/
                /*verif encaissement effet*/
                /*verif remise*/
                /*engagement par sign*/
                if (gestionContratCptForm.getAlertCloture().equalsIgnoreCase("false")){
                    gestionContratCptForm.setActivValider("true");
                }
            }

            return mapping.findForward(forward);


        } catch (Exception e) {
            com.oxia.fwk.core.Error erreur = new com.oxia.fwk.core.Error();
            StringBuffer text = 
                new StringBuffer("la transaction est Interrompu, une erreur dans GestionContratCptAction / Dispatch Action :rechercherContratAcloturer ");
            text.append(e.toString());
            erreur.setCode("200");
            erreur.setDescription(text.toString());
            logger.error("Erreur au niveau de l'agence <<" +gestionContratCptForm.getCodStrcStrc()+ ">>. Exception : ",e);  
            //logger.error("Exception : ",e);  
            ActionMessage actionMessage = 
                new ActionMessage("exception.generique", 
                                  erreur.getDescription());
            actionMessages.add("Erreur ", actionMessage);
            this.saveMessages(request, actionMessages);
            return mapping.findForward("error");
        }

    }

    public ActionForward cloturerContrat(ActionMapping mapping, 
                                         ActionForm form, 
                                         HttpServletRequest request, 
                                         HttpServletResponse response) throws IOException, 
                                                                              ServletException {


        ActionMessages actionMessages = new ActionMessages();

        GestionContratCptForm gestionContratCptForm = 
            (GestionContratCptForm)form;
        ParamAgence paramAgence = new ParamAgence();
        
        paramAgence = 
                (ParamAgence)request.getSession().getAttribute("paramAgBNA"); /// structure de l'agent qui fait la saisie


        gestionContratCptForm.setNumMatriculeUser(paramAgence.getNumMatrUser().toString());
        codeOperation=Constants.COD_OPER_CLOT_CPT;
        /*******prise en charge / validation Clôture du contrat**********/
        try {
            CloturerContratCmd cloturerContratCmd = new CloturerContratCmd();
            ContratACloturer contratACloturer = new ContratACloturer();
            contratACloturer.setContratCpt(gestionContratCptForm.getContratView().getContratCpt());
            contratACloturer.setCocdtrait(gestionContratCptForm.getCodetrait());
            /*préparation de la trace*/
            TraceContrat traceContrat = new TraceContrat();
            String message="";
            if (contratACloturer.getCocdtrait().equalsIgnoreCase("PC")){
            traceContrat.setCodEtatTrc(Constants.COD_ETAT_CPT_SEMIACTIF);
            message="vous avez efféctué une prise en charge de clôture sur le contrat N°";
            codTach      =Constants.COD_TACH_SAISIE_CLOT;
            }
            if (contratACloturer.getCocdtrait().equalsIgnoreCase("VC")){
            traceContrat.setCodEtatTrc(Constants.COD_ETAT_CPT_REJETE);
            message="vous avez validé la clôture du contrat N°";
            codTach      =Constants.COD_TACH_VAL_CLOT;
            }
            if (contratACloturer.getCocdtrait().equalsIgnoreCase("AC")){
            traceContrat.setCodEtatTrc(Constants.COD_ETAT_CPT_VALID);
            message="Vous avez annulé la clôture du contrat N°";
            codTach      =Constants.COD_TACH_ANN_CLOT;
            }
            
            Personnel personnel = new Personnel();
            personnel.setNumMatrUser(gestionContratCptForm.getNumMatriculeUser());
            traceContrat.setPersonnel(personnel);
            traceContrat.setContratCpt(gestionContratCptForm.getContratView().getContratCpt());
            Tache tache = new Tache();
            TacheId tacheId=new TacheId();
            tacheId.setCodOperOper(codeOperation);
            tacheId.setCodTachTach(codTach);
            tache.setTacheId(tacheId);
            traceContrat.setTache(tache);
            contratACloturer.setTraceContrat(traceContrat);
           
            ValueObject vo = (ValueObject)cloturerContratCmd.execute(contratACloturer);
                   
                     if (vo == null || vo.hasError()) {
                            List listErreur = vo.getErrors();
                            for (Iterator it = listErreur.iterator(); it.hasNext(); ) {
                                com.oxia.fwk.core.Error erreur = 
                                    (com.oxia.fwk.core.Error)it.next();
                                ActionMessage actionMessage = 
                                    new ActionMessage("exception.generique", 
                                                      erreur.getDescription());
                                actionMessages.add("Erreur ", actionMessage);
                            }
                            this.saveMessages(request, actionMessages);
                           
                            return mapping.findForward("error");
                     }else{
                ContratCpt contratCpt=(ContratCpt)vo;
                SimpleDateFormat formater=new SimpleDateFormat("dd/MM/yyyy");
                String heureString = formater.format(gestionContratCptForm.getInitialisationView().getDateOp());
                message=message + contratCpt.getContratCptId().getCodStrcStrc()+" "+
                contratCpt.getContratCptId().getCodPrdPrd()+" "+contratCpt.getContratCptId().getNumCcptCcpt()+
                " en date du "+heureString;
                
                gestionContratCptForm.setLibelleConfirmation(message);    
            }

            return mapping.findForward("confirmation");


        } catch (Exception e) {
            com.oxia.fwk.core.Error erreur = new com.oxia.fwk.core.Error();
            StringBuffer text = 
                new StringBuffer("la transaction est Interrompu, une erreur dans GestionContratCptAction / Dispatch Action :cloturerContrat ");
            text.append(e.toString());
            erreur.setCode("200");
            erreur.setDescription(text.toString());
            logger.error("Erreur au niveau de l'agence <<" +gestionContratCptForm.getCodStrcStrc()+ ">>. Exception : ",e);  
        //    logger.error("Exception : ",e);  
            ActionMessage actionMessage = 
                new ActionMessage("exception.generique", 
                                  erreur.getDescription());
            actionMessages.add("Erreur ", actionMessage);
            this.saveMessages(request, actionMessages);
            return mapping.findForward("error");
        }

    }

    public ActionForward rechercherContratABloquer(ActionMapping mapping, 
                                                   ActionForm form, 
                                                   HttpServletRequest request, 
                                                   HttpServletResponse response) throws IOException, 
                                                                                        ServletException {


       /// ActionMessages actionMessages = new ActionMessages();

        GestionContratCptForm gestionContratCptForm = 
            (GestionContratCptForm)form;

        try {

            ContratCptId contratCptId = new ContratCptId();
            contratCptId.setCodStrcStrc(Long.valueOf(gestionContratCptForm.getContratView().getCodStrcStrc()));
            contratCptId.setCodPrdPrd(Long.valueOf(gestionContratCptForm.getContratView().getCodPrdPrd()));
            contratCptId.setNumCcptCcpt(Long.valueOf(gestionContratCptForm.getContratView().getNumCcptCcpt()));
            //recherche du contrat
            GetContratEtatCmd getContratEtatCmd = new GetContratEtatCmd();

            ContratCptMandat contratAclot = 
                (ContratCptMandat)getContratEtatCmd.execute(contratCptId);


            if (contratAclot.getContratCpt() == null) {
                gestionContratCptForm.setAlert("contratNonValide");

                //------- Verifier s'il y a une restriction sur le contrat -------------------//
            } else {
                gestionContratCptForm.getContratView().setContratCpt(contratAclot.getContratCpt());
                gestionContratCptForm.setExistCpt(true);
                /*cas Blocage Compte*/
                if ((gestionContratCptForm.getCodetrait().equalsIgnoreCase("BC")) || 
                    (gestionContratCptForm.getCodetrait().equalsIgnoreCase("TX"))) {
                    if (!contratAclot.isVerifEtat()) {
                        gestionContratCptForm.setAlert("contratNonValide");
                    } else {
                        gestionContratCptForm.setActivValider("true");
                    }
                    ChargerMotifEtatCmd chargerMotifBlocageCptCmd = 
                        new ChargerMotifEtatCmd();
                    EtatContrat etatContrat = new EtatContrat();
                    etatContrat.setCodEtatEcon(Constants.COD_ETAT_CPT_BLOQUE);
                    Listes listMotif = 
                        (Listes)chargerMotifBlocageCptCmd.execute(etatContrat);
                    gestionContratCptForm.setListMotifEtat(listMotif.getList());
                }

                /*cas déblocage Compte*/
                if (gestionContratCptForm.getCodetrait().equalsIgnoreCase("DC")) {
                    if ((!contratAclot.getContratCpt().getCodEtatCcpt().equalsIgnoreCase(Constants.COD_ETAT_CPT_BLOQUE)) && 
                        (!contratAclot.getContratCpt().getCodEtatCcpt().equalsIgnoreCase(Constants.COD_ETAT_CPT_SEMIACTIF))) {
                        gestionContratCptForm.setAlert("contratNonBloque");
                    } else {
                        gestionContratCptForm.setActivValider("true");
                    }
                    ChargerMotifEtatCmd chargerMotifBlocageCptCmd = 
                        new ChargerMotifEtatCmd();
                    EtatContrat etatContrat = new EtatContrat();
                    etatContrat.setCodEtatEcon(Constants.COD_ETAT_CPT_VALID);
                    Listes listMotif = 
                        (Listes)chargerMotifBlocageCptCmd.execute(etatContrat);
                    gestionContratCptForm.setListMotifEtat(listMotif.getList());
                }
            }
            if (gestionContratCptForm.getCodetrait().equalsIgnoreCase("TX")) {
                return mapping.findForward("indexTransfertCtx");
            } else {
                return mapping.findForward("indexBlocageCpt");
            }


        } catch (Exception e) {
            com.oxia.fwk.core.Error erreur = new com.oxia.fwk.core.Error();
            StringBuffer text = 
                new StringBuffer("la transaction est Interrompu, une erreur dans GestionContratCptAction / Dispatch Action :rechercherContratABloquer ");
            text.append(e.toString());
            erreur.setCode("200");
            erreur.setDescription(text.toString());
            logger.error("Erreur au niveau de l'agence <<" +gestionContratCptForm.getCodStrcStrc()+ ">>. Exception : ",e);  
           // logger.error("Exception : ",e);  
            ActionMessage actionMessage = 
                new ActionMessage("exception.generique", 
                                  erreur.getDescription());
            actionMessages.add("Erreur ", actionMessage);
            this.saveMessages(request, actionMessages);
            return mapping.findForward("error");
        }

    }
    

    public ActionForward bloquerCompte(ActionMapping mapping, ActionForm form, 
                                       HttpServletRequest request, 
                                       HttpServletResponse response) throws IOException, 
                                                                            ServletException {


        ActionMessages actionMessages = new ActionMessages();

        GestionContratCptForm gestionContratCptForm = 
            (GestionContratCptForm)form;
        ParamAgence paramAgence = new ParamAgence();
       
        paramAgence = 
                (ParamAgence)request.getSession().getAttribute("paramAgBNA"); /// structure de l'agent qui fait la saisie


        gestionContratCptForm.setNumMatriculeUser(paramAgence.getNumMatrUser().toString());
        /*******blocage du contrat**********/
        try {


            BloquerContratCptCmd bloquerContratCptCmd = 
                new BloquerContratCptCmd();
            ContratABloquer contratABloquer = new ContratABloquer();
            contratABloquer.setContratCpt(gestionContratCptForm.getContratView().getContratCpt());
            /*recherche du Motif Etat choisi*/

            for (Iterator it = 
                 gestionContratCptForm.getListMotifEtat().iterator(); 
                 it.hasNext(); ) {
                MotifEtat motifEtat = (MotifEtat)it.next();
                if (motifEtat.getMotifEtatId().getCodMotfMeta().longValue() == 
                    (Long.valueOf(gestionContratCptForm.getCodeMotifEtat()).longValue())) {
                    contratABloquer.setMotifEtat(motifEtat);
                }
            }
            if (Long.valueOf(gestionContratCptForm.getCodeMotifEtat()).longValue()==Constants.COD_MOTIF_DEC){
                codeOperation=Constants.COD_OPER_BLOC_CPT_DEC;
                codTach      =Constants.COD_TACH_BLOC_CPT;
            }
            if (Long.valueOf(gestionContratCptForm.getCodeMotifEtat()).longValue()==Constants.COD_MOTIF_FAIL){
                codeOperation=Constants.COD_OPER_BLOC_CPT_FAIL;
                codTach      =Constants.COD_TACH_BLOC_CPT;
            }
            if (Long.valueOf(gestionContratCptForm.getCodeMotifEtat()).longValue()==Constants.COD_MOTIF_JUD){
                codeOperation=Constants.COD_OPER_BLOC_CPT_JUR;
                codTach      =Constants.COD_TACH_BLOC_CPT;
            }
            /*préparation de la trace*/
             TraceContrat traceContrat = new TraceContrat();
             traceContrat.setCodEtatTrc(Constants.COD_ETAT_CPT_BLOQUE);
             Personnel personnel = new Personnel();
             personnel.setNumMatrUser(gestionContratCptForm.getNumMatriculeUser());
             traceContrat.setPersonnel(personnel);
             traceContrat.setContratCpt(gestionContratCptForm.getContratView().getContratCpt());
             Tache tache = new Tache();
             TacheId tacheId=new TacheId();
             tacheId.setCodOperOper(codeOperation);
             tacheId.setCodTachTach(codTach);
             tache.setTacheId(tacheId);
             traceContrat.setTache(tache);
             contratABloquer.setTraceContrat(traceContrat);

             ValueObject vo =(ValueObject) bloquerContratCptCmd.execute(contratABloquer);

              if (vo == null || vo.hasError()) {
                List listErreur = vo.getErrors();
                for (Iterator it = listErreur.iterator(); it.hasNext(); ) {
                    com.oxia.fwk.core.Error erreur = 
                    (com.oxia.fwk.core.Error)it.next();
                    ActionMessage actionMessage = 
                    new ActionMessage("exception.generique", 
                    erreur.getDescription());
                    actionMessages.add("Erreur ", actionMessage);
                }
                 this.saveMessages(request, actionMessages);
                return mapping.findForward("error");
                }else{
                ContratCpt contratCpt=(ContratCpt)vo;
                SimpleDateFormat formater=new SimpleDateFormat("dd/MM/yyyy");
                String heureString = formater.format(gestionContratCptForm.getInitialisationView().getDateOp());
                String message="le contrat N°"+ contratCpt.getContratCptId().getCodStrcStrc()+" "+
                contratCpt.getContratCptId().getCodPrdPrd()+" "+contratCpt.getContratCptId().getNumCcptCcpt()+
                " à été bloqué en date du "+heureString;
                gestionContratCptForm.setLibelleConfirmation(message);    
            }

            return mapping.findForward("confirmation");


        } catch (Exception e) {
            com.oxia.fwk.core.Error erreur = new com.oxia.fwk.core.Error();
            StringBuffer text = 
                new StringBuffer("la transaction est Interrompu, une erreur dans GestionContratCptAction / Dispatch Action :bloquerCompte ");
            text.append(e.toString());
            erreur.setCode("200");
            erreur.setDescription(text.toString());
            logger.error("Erreur au niveau de l'agence <<" +gestionContratCptForm.getCodStrcStrc()+ ">>. Exception : ",e);  
           // logger.error("Exception : ",e);  
            ActionMessage actionMessage = 
                new ActionMessage("exception.generique", 
                                  erreur.getDescription());
            actionMessages.add("Erreur ", actionMessage);
            this.saveMessages(request, actionMessages);
            return mapping.findForward("error");
        }

    }


    public ActionForward debloquerCompte(ActionMapping mapping, 
                                         ActionForm form, 
                                         HttpServletRequest request, 
                                         HttpServletResponse response) throws IOException, 
                                                                              ServletException {


        ActionMessages actionMessages = new ActionMessages();

        GestionContratCptForm gestionContratCptForm = 
            (GestionContratCptForm)form;
        ParamAgence paramAgence = new ParamAgence();
        paramAgence = 
                (ParamAgence)request.getSession().getAttribute("paramAgBNA"); /// structure de l'agent qui fait la saisie

        gestionContratCptForm.setNumMatriculeUser(paramAgence.getNumMatrUser().toString());
        /*******déblocage du contrat**********/
        try {


            DebloquerContratCptCmd debloquerContratCptCmd = 
                new DebloquerContratCptCmd();
            ContratABloquer contratABloquer = new ContratABloquer();
            contratABloquer.setContratCpt(gestionContratCptForm.getContratView().getContratCpt());
            /*recherche du Motif Etat choisi*/

            for (Iterator it = 
                 gestionContratCptForm.getListMotifEtat().iterator(); 
                 it.hasNext(); ) {
                MotifEtat motifEtat = (MotifEtat)it.next();
                if (motifEtat.getMotifEtatId().getCodMotfMeta().longValue() == 
                    (Long.valueOf(gestionContratCptForm.getCodeMotifEtat()).longValue())) {
                    contratABloquer.setMotifEtat(motifEtat);
                }
            }
            if (Long.valueOf(gestionContratCptForm.getCodeMotifEtat()).longValue()==Constants.COD_MOTIF_DEC){
                codeOperation=Constants.COD_OPER_DEBLOC_CPT_DEC;
                codTach      =Constants.COD_TACH_DEBLOC_CPT;
            }
            if (Long.valueOf(gestionContratCptForm.getCodeMotifEtat()).longValue()==Constants.COD_MOTIF_FAIL){
                codeOperation=Constants.COD_OPER_DEBLOC_CPT_FAIL;
                codTach      =Constants.COD_TACH_DEBLOC_CPT;
            }
            if (Long.valueOf(gestionContratCptForm.getCodeMotifEtat()).longValue()==Constants.COD_MOTIF_JUD){
                codeOperation=Constants.COD_OPER_DEBLOC_CPT_JUR;
                codTach      =Constants.COD_TACH_DEBLOC_CPT;
            }
            /* préparation de la trace */
            TraceContrat traceContrat = new TraceContrat();
            traceContrat.setCodEtatTrc(Constants.COD_ETAT_CPT_VALID);
            Personnel personnel = new Personnel();
            personnel.setNumMatrUser(gestionContratCptForm.getNumMatriculeUser());
            traceContrat.setPersonnel(personnel);
            traceContrat.setContratCpt(gestionContratCptForm.getContratView().getContratCpt());
            Tache tache = new Tache();
            TacheId tacheId=new TacheId();
            tacheId.setCodOperOper(codeOperation);
            tacheId.setCodTachTach(codTach);
            tache.setTacheId(tacheId);
            traceContrat.setTache(tache);
            contratABloquer.setTraceContrat(traceContrat);
            ValueObject vo = (ValueObject)debloquerContratCptCmd.execute(contratABloquer);
            if (vo == null || vo.hasError()) {
               List listErreur = vo.getErrors();
               for (Iterator it = listErreur.iterator(); it.hasNext(); ) {
                   com.oxia.fwk.core.Error erreur = 
                   (com.oxia.fwk.core.Error)it.next();
                   ActionMessage actionMessage = 
                   new ActionMessage("exception.generique", 
                   erreur.getDescription());
                   actionMessages.add("Erreur ", actionMessage);
               }
                this.saveMessages(request, actionMessages);
               return mapping.findForward("error");
               }else{
                ContratCpt contratCpt=(ContratCpt)vo;
                SimpleDateFormat formater=new SimpleDateFormat("dd/MM/yyyy");
                String heureString = formater.format(gestionContratCptForm.getInitialisationView().getDateOp());
                String message="Vous avez débloqué le contrat N°"+ contratCpt.getContratCptId().getCodStrcStrc()+" "+
                contratCpt.getContratCptId().getCodPrdPrd()+" "+contratCpt.getContratCptId().getNumCcptCcpt()+
                " en date du "+heureString;
                
                gestionContratCptForm.setLibelleConfirmation(message);    
            }

            return mapping.findForward("confirmation");


        } catch (Exception e) {
            com.oxia.fwk.core.Error erreur = new com.oxia.fwk.core.Error();
            StringBuffer text = 
                new StringBuffer("la transaction est Interrompu, une erreur dans GestionContratCptAction / Dispatch Action :debloquerCompte ");
            text.append(e.toString());
            erreur.setCode("200");
            erreur.setDescription(text.toString());
            logger.error("Erreur au niveau de l'agence <<" +gestionContratCptForm.getCodStrcStrc()+ ">>. Exception : ",e);  
            //logger.error("Exception : ",e);  
            ActionMessage actionMessage = 
                new ActionMessage("exception.generique", 
                                  erreur.getDescription());
            actionMessages.add("Erreur ", actionMessage);
            this.saveMessages(request, actionMessages);
            return mapping.findForward("error");
        }

    }
    
    
    public ActionForward reactiverCompte(ActionMapping mapping, 
                                         ActionForm form, 
                                         HttpServletRequest request, 
                                         HttpServletResponse response) throws IOException, 
                                                                              ServletException {


        ActionMessages actionMessages = new ActionMessages();

        GestionContratCptForm gestionContratCptForm = 
            (GestionContratCptForm)form;
        ParamAgence paramAgence = new ParamAgence();
        paramAgence = 
                (ParamAgence)request.getSession().getAttribute("paramAgBNA"); /// structure de l'agent qui fait la saisie

        gestionContratCptForm.setNumMatriculeUser(paramAgence.getNumMatrUser().toString());
        /*******déblocage du contrat**********/
        try {


            DebloquerContratCptCmd debloquerContratCptCmd = 
                new DebloquerContratCptCmd();
            ContratABloquer contratABloquer = new ContratABloquer();
            contratABloquer.setContratCpt(gestionContratCptForm.getContratView().getContratCpt());
                        
            MotifEtat motifEtat = new MotifEtat();
            MotifEtatId motifEtatId = new MotifEtatId();
            motifEtatId.setCodMotfMeta(Long.valueOf("0"));
            motifEtatId.setCodEtatEcon("R");
            motifEtat.setMotifEtatId(motifEtatId);
            contratABloquer.setMotifEtat(motifEtat);
            
            
            codeOperation=Constants.COD_OPER_DEBLOC_CPT_DEC;
            codTach      =Constants.COD_TACH_DEBLOC_CPT;
            
           
            /* préparation de la trace */
            TraceContrat traceContrat = new TraceContrat();
            traceContrat.setCodEtatTrc(Constants.COD_ETAT_CPT_VALID);
            Personnel personnel = new Personnel();
            personnel.setNumMatrUser(gestionContratCptForm.getNumMatriculeUser());
            traceContrat.setPersonnel(personnel);
            traceContrat.setContratCpt(gestionContratCptForm.getContratView().getContratCpt());
            Tache tache = new Tache();
            TacheId tacheId=new TacheId();
            tacheId.setCodOperOper(codeOperation);
            tacheId.setCodTachTach(codTach);
            tache.setTacheId(tacheId);
            traceContrat.setTache(tache);
            contratABloquer.setTraceContrat(traceContrat);
            ValueObject vo = (ValueObject)debloquerContratCptCmd.execute(contratABloquer);
            if (vo == null || vo.hasError()) {
               List listErreur = vo.getErrors();
               for (Iterator it = listErreur.iterator(); it.hasNext(); ) {
                   com.oxia.fwk.core.Error erreur = 
                   (com.oxia.fwk.core.Error)it.next();
                   ActionMessage actionMessage = 
                   new ActionMessage("exception.generique", 
                   erreur.getDescription());
                   actionMessages.add("Erreur ", actionMessage);
               }
                this.saveMessages(request, actionMessages);
               return mapping.findForward("error");
               }else{
                ContratCpt contratCpt=(ContratCpt)vo;
                SimpleDateFormat formater=new SimpleDateFormat("dd/MM/yyyy");
                String heureString = formater.format(gestionContratCptForm.getInitialisationView().getDateOp());
                String message="Vous avez réactiver le contrat N°"+ contratCpt.getContratCptId().getCodStrcStrc()+" "+
                contratCpt.getContratCptId().getCodPrdPrd()+" "+contratCpt.getContratCptId().getNumCcptCcpt()+
                " en date du "+heureString;
                
                gestionContratCptForm.setLibelleConfirmation(message);    
            }

            return mapping.findForward("confirmation");


        } catch (Exception e) {
            com.oxia.fwk.core.Error erreur = new com.oxia.fwk.core.Error();
            StringBuffer text = 
                new StringBuffer("la transaction est Interrompu, une erreur dans GestionContratCptAction / Dispatch Action :reactiverCompte ");
            text.append(e.toString());
            erreur.setCode("200");
            erreur.setDescription(text.toString());
            logger.error("Erreur au niveau de l'agence <<" +gestionContratCptForm.getCodStrcStrc()+ ">>. Exception : ",e);  
            //logger.error("Exception : ",e);  
            ActionMessage actionMessage = 
                new ActionMessage("exception.generique", 
                                  erreur.getDescription());
            actionMessages.add("Erreur ", actionMessage);
            this.saveMessages(request, actionMessages);
            return mapping.findForward("error");
        }

    }

    public ActionForward rechercherCpt(ActionMapping mapping, ActionForm form, 
                                       HttpServletRequest request, 
                                       HttpServletResponse response) throws IOException, 
                                                                            ServletException {


        ActionMessages actionMessages = new ActionMessages();

        GestionContratCptForm gestionContratCptForm = 
            (GestionContratCptForm)form;
        /*******recherche du contrat**********/
        try {

            ContratCptId contratCptId = new ContratCptId();
            contratCptId.setCodStrcStrc(Long.valueOf(gestionContratCptForm.getContratView().getCodStrcStrc()));
            contratCptId.setCodPrdPrd(Long.valueOf(gestionContratCptForm.getContratView().getCodPrdPrd()));
            contratCptId.setNumCcptCcpt(Long.valueOf(gestionContratCptForm.getContratView().getNumCcptCcpt()));
            //recherche du contrat
            GetContratEtatCmd getContratEtatCmd = new GetContratEtatCmd();

            ContratCptMandat contratAclot = 
                (ContratCptMandat)getContratEtatCmd.execute(contratCptId);


            if (contratAclot.getContratCpt() == null) {
                gestionContratCptForm.setAlert("contratNonValide");

                //------- Verifier s'il y a une restriction sur le contrat -------------------//
            } else {
                gestionContratCptForm.getContratView().setContratCpt(contratAclot.getContratCpt());
                /*verif etat compte*/
                if (!contratAclot.isVerifEtat()) {
                    gestionContratCptForm.setAlert("contratNonValide");
                } else
                {
                   
                
                ChargerNatureblocageCmd chargerNatureblocageCmd = 
                    new ChargerNatureblocageCmd();
                Listes listNatBloc = 
                    (Listes)chargerNatureblocageCmd.execute(contratAclot.getContratCpt());
                gestionContratCptForm.setListNatureBlocage(listNatBloc.getList());
                ChargerBlocagesCmd chargerBlocagesCmd = 
                    new ChargerBlocagesCmd();
                BlocageCriteres blocageCriteres = new BlocageCriteres();
                blocageCriteres.setContratCptId(gestionContratCptForm.getContratView().getContratCpt().getContratCptId());
                if((gestionContratCptForm.getCodetrait().equalsIgnoreCase("DM"))||
                (gestionContratCptForm.getCodetrait().equalsIgnoreCase("CB"))){
                Listes listblocages = 
                    (Listes)chargerBlocagesCmd.execute(blocageCriteres);
                if (listblocages.getList() != null && listblocages.getList().size()>0 ) {
                    if(gestionContratCptForm.getCodetrait().equalsIgnoreCase("DM")){
                    gestionContratCptForm.setActivValider("true");}
                gestionContratCptForm.setListBlocages(listblocages.getList());
                int sizelistbloc = 
                        gestionContratCptForm.getListBlocages().size();
                    for (int i = 0; i < sizelistbloc; i++){
                        gestionContratCptForm.getIndexBlocageChoisis().add("");}
                   
                }else{
                    gestionContratCptForm.setAlertBloc("Aucunblocage");
                }
                    
                }
                if(gestionContratCptForm.getCodetrait().equalsIgnoreCase("BM")){
                gestionContratCptForm.setActivValider("true");}
            }
            }

            return mapping.findForward("indexBlocageMnt");


        } catch (Exception e) {
            com.oxia.fwk.core.Error erreur = new com.oxia.fwk.core.Error();
            StringBuffer text = 
                new StringBuffer("la transaction est Interrompu, une erreur dans GestionContratCptAction / Dispatch Action :rechercherCpt ");
            text.append(e.toString());
            erreur.setCode("200");
            erreur.setDescription(text.toString());
            logger.error("Erreur au niveau de l'agence <<" +gestionContratCptForm.getCodStrcStrc()+ ">>. Exception : ",e);  
           // logger.error("Exception : ",e);  
            ActionMessage actionMessage = 
                new ActionMessage("exception.generique", 
                                  erreur.getDescription());
            actionMessages.add("Erreur ", actionMessage);
            this.saveMessages(request, actionMessages);
            return mapping.findForward("error");
        }

    }

    public ActionForward bloquerMontant(ActionMapping mapping, ActionForm form, 
                                        HttpServletRequest request, 
                                        HttpServletResponse response) throws IOException, 
                                                                             ServletException {


        ActionMessages actionMessages = new ActionMessages();

        GestionContratCptForm gestionContratCptForm = 
            (GestionContratCptForm)form;

        try {
            
           
            
            BloquerMontantCmd bloquerMontantCmd = new BloquerMontantCmd();
            MontantBlocage montantBlocage = new MontantBlocage();
            montantBlocage.setContratCpt(gestionContratCptForm.getContratView().getContratCpt());
            montantBlocage.setCodNatureBlocage(Long.valueOf(gestionContratCptForm.getCodNatBloc()));
            montantBlocage.setMontantBlocage(Long.valueOf(new Double(new Double(StrHandler.strWithoutBlanck(gestionContratCptForm.getMontantBloc())).doubleValue() * 
                                                                 1000).longValue()));
          
            codeOperation=Constants.COD_OPER_BLOC_MNT_SAI;
            codTach      =Constants.COD_TACH_BLOC_MNT;
                                                      
            /* préparation de la trace */
            TraceContrat traceContrat = new TraceContrat();
            traceContrat.setCodEtatTrc(Constants.COD_ETAT_CPT_VALID);
            Personnel personnel = new Personnel();
            personnel.setNumMatrUser(gestionContratCptForm.getInitialisationView().getNumMatrUser());
            traceContrat.setPersonnel(personnel);
            traceContrat.setContratCpt(gestionContratCptForm.getContratView().getContratCpt());
            Tache tache = new Tache();
            TacheId tacheId=new TacheId();
            tacheId.setCodOperOper(codeOperation);
            tacheId.setCodTachTach(codTach);
            tache.setTacheId(tacheId);
            traceContrat.setTache(tache);
            montantBlocage.setTraceContrat(traceContrat);
            ValueObject vo = (ValueObject)bloquerMontantCmd.execute(montantBlocage);
            if (vo == null || vo.hasError()) {
               List listErreur = vo.getErrors();
               for (Iterator it = listErreur.iterator(); it.hasNext(); ) {
                   com.oxia.fwk.core.Error erreur = 
                   (com.oxia.fwk.core.Error)it.next();
                   ActionMessage actionMessage = 
                   new ActionMessage("exception.generique", 
                   erreur.getDescription());
                   actionMessages.add("Erreur ", actionMessage);
               }
                this.saveMessages(request, actionMessages);
               return mapping.findForward("error");
               }else{
                ContratCpt contratCpt=(ContratCpt)vo;
                SimpleDateFormat formater=new SimpleDateFormat("dd/MM/yyyy");
                String heureString = formater.format(gestionContratCptForm.getInitialisationView().getDateOp());
                String message="Vous avez bloqué un montant de "+gestionContratCptForm.getMontantBloc()+" sur le contrat N°"+ 
                contratCpt.getContratCptId().getCodStrcStrc()+" "+contratCpt.getContratCptId().getCodPrdPrd()+" "+contratCpt.getContratCptId().getNumCcptCcpt()+
                " en date du "+heureString;
                
                gestionContratCptForm.setLibelleConfirmation(message);    
            }

            return mapping.findForward("confirmation");



        } catch (Exception e) {
            com.oxia.fwk.core.Error erreur = new com.oxia.fwk.core.Error();
            StringBuffer text = 
                new StringBuffer("la transaction est Interrompu, une erreur dans GestionContratCptAction / Dispatch Action :bloquerMontant ");
            text.append(e.toString());
            erreur.setCode("200");
            erreur.setDescription(text.toString());
            logger.error("Erreur au niveau de l'agence <<" +gestionContratCptForm.getCodStrcStrc()+ ">>. Exception : ",e);  
          //  logger.error("Exception : ",e);  
            ActionMessage actionMessage = 
                new ActionMessage("exception.generique", 
                                  erreur.getDescription());
            actionMessages.add("Erreur ", actionMessage);
            this.saveMessages(request, actionMessages);
            return mapping.findForward("error");
        }

    }

    public ActionForward debloquerMontant(ActionMapping mapping, 
                                          ActionForm form, 
                                          HttpServletRequest request, 
                                          HttpServletResponse response) throws IOException, 
                                                                               ServletException {


        ActionMessages actionMessages = new ActionMessages();

        GestionContratCptForm gestionContratCptForm = 
            (GestionContratCptForm)form;

        try {
            DebloquerMontantCmd debloquerMontantCmd = 
                new DebloquerMontantCmd();
            MontantBlocage montantBlocage = new MontantBlocage();
            Long totalBlocage =Long.valueOf(0);
            List index = gestionContratCptForm.getIndexBlocageChoisis();
            for (int i = 0; i < index.size(); i++) {
                if (!index.get(i).equals("")) {
                    Long numBlocChoisi = (Long.valueOf((String)index.get(i)));   
                  
                    for (Iterator it = 
                        gestionContratCptForm.getListBlocages().iterator(); 
                         it.hasNext(); ) {
                         Blocage blocage = (Blocage)it.next();
                         if (blocage.getNumBlocBloc().longValue() == numBlocChoisi.longValue()) {
                          montantBlocage.getListBlocageChoisi().add(blocage);
                             totalBlocage=totalBlocage+blocage.getMntBlocBloc();
                         }
                    }
                }
            }
            montantBlocage.setMontantBlocage(totalBlocage);
            montantBlocage.setContratCpt(gestionContratCptForm.getContratView().getContratCpt());
            codeOperation=Constants.COD_OPER_DEBLOC_MNT_SAI;
            codTach      =Constants.COD_TACH_DEBLOC_MNT;
            /* préparation de la trace */
            TraceContrat traceContrat = new TraceContrat();
            traceContrat.setCodEtatTrc(Constants.COD_ETAT_CPT_VALID);
            Personnel personnel = new Personnel();
            personnel.setNumMatrUser(gestionContratCptForm.getInitialisationView().getNumMatrUser());
            traceContrat.setPersonnel(personnel);
            traceContrat.setContratCpt(gestionContratCptForm.getContratView().getContratCpt());
            Tache tache = new Tache();
            TacheId tacheId=new TacheId();
            tacheId.setCodOperOper(codeOperation);
            tacheId.setCodTachTach(codTach);
            tache.setTacheId(tacheId);
            traceContrat.setTache(tache);
            montantBlocage.setTraceContrat(traceContrat);
            ValueObject vo = (ValueObject)debloquerMontantCmd.execute(montantBlocage);
            if (vo == null || vo.hasError()) {
               List listErreur = vo.getErrors();
               for (Iterator it = listErreur.iterator(); it.hasNext(); ) {
                   com.oxia.fwk.core.Error erreur = 
                   (com.oxia.fwk.core.Error)it.next();
                   ActionMessage actionMessage = 
                   new ActionMessage("exception.generique", 
                   erreur.getDescription());
                   actionMessages.add("Erreur ", actionMessage);
               }
                this.saveMessages(request, actionMessages);
               return mapping.findForward("error");
               }else{
                ContratCpt cptresult=(ContratCpt)vo;
                SimpleDateFormat formater=new SimpleDateFormat("dd/MM/yyyy");
                String heureString = formater.format(gestionContratCptForm.getInitialisationView().getDateOp());
                String message="Vous avez débloqué un montant de "+gestionContratCptForm.getMontantBloc()+" sur le contrat N°"+ 
                cptresult.getContratCptId().getCodStrcStrc()+" "+cptresult.getContratCptId().getCodPrdPrd()+" "+cptresult.getContratCptId().getNumCcptCcpt()+
                " en date du "+heureString;
                
                gestionContratCptForm.setLibelleConfirmation(message);    
            }

            return mapping.findForward("confirmation");



        } catch (Exception e) {
            com.oxia.fwk.core.Error erreur = new com.oxia.fwk.core.Error();
            StringBuffer text = 
                new StringBuffer("la transaction est Interrompu, une erreur dans GestionContratCptAction / Dispatch Action :debloquerMontant ");
            text.append(e.toString());
            erreur.setCode("200");
            erreur.setDescription(text.toString());
            logger.error("Erreur au niveau de l'agence <<" +gestionContratCptForm.getCodStrcStrc()+ ">>. Exception : ",e);  
           // logger.error("Exception : ",e);  
            ActionMessage actionMessage = 
                new ActionMessage("exception.generique", 
                                  erreur.getDescription());
            actionMessages.add("Erreur ", actionMessage);
            this.saveMessages(request, actionMessages);
            return mapping.findForward("error");
        }

    }

     public ActionForward insererLivret(ActionMapping mapping,
                                           ActionForm form,
                                           HttpServletRequest request,
                                           HttpServletResponse response) throws IOException,
                                                                                ServletException {
        GestionContratCptForm gestionContratCptForm =
            (GestionContratCptForm)form;
        try {

            ActionMessages actionMessages = new ActionMessages();

            
            codeOperation=Constants.COD_OPER_RENOUV_LIV;
            codTach      =Constants.COD_TACH_RENOUV_LIV;
            Livrets livrets =new Livrets();
            LivretEpargne ancienLivret =new LivretEpargne();
            LivretEpargne nouveauLivret =new LivretEpargne();
            Set l =gestionContratCptForm.getContrat().getLivretEpargne();
            if (l!=null&&l.size()>0){
            for (Iterator it = gestionContratCptForm.getContrat().getLivretEpargne().iterator(); it.hasNext(); ) {
                LivretEpargne livretEpargne =
                    (LivretEpargne)it.next();
                    if ((livretEpargne.getDatFinLive()==null)&&
                    (livretEpargne.getCodEtatLive().equalsIgnoreCase("V"))){
                    ancienLivret=livretEpargne;
                    }
                 }
            }
            nouveauLivret.setContratCpt(gestionContratCptForm.getContrat());
            nouveauLivret.setNumLivrLive(gestionContratCptForm.getNouvnumLivrCcpt());
            nouveauLivret.setDatDebLive(new Date());
            nouveauLivret.setCodEtatLive("V");
            livrets.setAncienLivret(ancienLivret);
            livrets.setNouveauLivret(nouveauLivret);
            MAJLivretEpargneCmd majLivretEpargneCmd=new MAJLivretEpargneCmd();
            ValueObject vo = (ValueObject)majLivretEpargneCmd.execute(livrets);
            if (vo == null || vo.hasError()) {
               List listErreur = vo.getErrors();
               for (Iterator it = listErreur.iterator(); it.hasNext(); ) {
                   com.oxia.fwk.core.Error erreur = 
                   (com.oxia.fwk.core.Error)it.next();
                   ActionMessage actionMessage = 
                   new ActionMessage("exception.generique", 
                   erreur.getDescription());
                   actionMessages.add("Erreur ", actionMessage);
               }
                this.saveMessages(request, actionMessages);
               return mapping.findForward("error");
               }else
            
            return mapping.findForward("indexSMILE");


        } catch (Exception e) {
            com.oxia.fwk.core.Error erreur = new com.oxia.fwk.core.Error();
            StringBuffer text = 
                new StringBuffer("la transaction est Interrompu, une erreur dans GestionContratCptAction / Dispatch Action :insertLivert ");
            text.append(e.toString());
            erreur.setCode("200");
            erreur.setDescription(text.toString());
            logger.error("Erreur au niveau de l'agence <<" +gestionContratCptForm.getCodStrcStrc()+ ">>. Exception : ",e);  
           // logger.error("Exception : ",e);  
            ActionMessage actionMessage = 
                new ActionMessage("exception.generique", 
                                  erreur.getDescription());
            actionMessages.add("Erreur ", actionMessage);
            this.saveMessages(request, actionMessages);
            return mapping.findForward("error");
        }

    }

    public ActionForward transfererCtx(ActionMapping mapping, ActionForm form, 
                                       HttpServletRequest request, 
                                       HttpServletResponse response) throws IOException, 
                                                                            ServletException {


        ActionMessages actionMessages = new ActionMessages();

        GestionContratCptForm gestionContratCptForm = 
            (GestionContratCptForm)form;

        ParamAgence paramAgence = new ParamAgence();
        paramAgence = 
                (ParamAgence)request.getSession().getAttribute("paramAgBNA"); /// structure de l'agent qui fait la saisie
        gestionContratCptForm.setNumMatriculeUser(paramAgence.getNumMatrUser().toString());
        /*******transfert du contrat à CTX**********/
        try {
           
            
            /*préparation de l'opération moyen de paiement*/
            OperationMoyPay operationMoyPay = new OperationMoyPay();
            operationMoyPay.setDatOperOmp(DateHandler.strToDate(gestionContratCptForm.getInitialisationView().getDateActuelle()));
            //operationMoyPay.setDatValOmp(DateHandler.strToDate( gestionContratCptForm.getDatvalTctx()));
            operationMoyPay.setDatValOmp(new Date());
            codeOperation=Constants.COD_OPER_TRANS_CTX;
            codTach      =Constants.COD_TACH_VAL_TRANS_CTX;
            Tache tache = new Tache();
            TacheId tacheId=new TacheId();
            tacheId.setCodOperOper(codeOperation);
            tacheId.setCodTachTach(codTach);
            tache.setTacheId(tacheId);
            operationMoyPay.setTache(tache);
            
            Personnel persInitiateur = new Personnel();
            persInitiateur.setNumMatrUser(paramAgence.getNumMatrUser());
            operationMoyPay.setPersonnelInitiateur(persInitiateur);
            
            Structure structureInitiatrice = new Structure();
            structureInitiatrice.setCodStrcStrc(Long.valueOf(gestionContratCptForm.getInitialisationView().getCodeAgence()));
            operationMoyPay.setStructureInitiatrice(structureInitiatrice);
            
            operationMoyPay.setContratCpt(gestionContratCptForm.getContratView().getContratCpt());
            Devise devise = new Devise();
            devise.setCodDevDev(Long.valueOf(gestionContratCptForm.getContratView().getCodDevDev()));
            operationMoyPay.setDevise(devise);
            operationMoyPay.setMontDinOmp(gestionContratCptForm.getContratView().getContratCpt().getMontSoldCcpt());
            
            operationMoyPay.setCodDemOmp("TR");
            operationMoyPay.setCodEtatOmp("V");
            operationMoyPay.setCodSensOmp("D");
            operationMoyPay.setNumPcedOmp("0");
            TypePiece typePieceDemandeur = new TypePiece();
            typePieceDemandeur.setCodTpceTpce(Constants.COD_CIN);
            operationMoyPay.setTypePieceDemandeur(typePieceDemandeur);
            
          
            
            TransfertCtxCmd transfertCtxCmd = new TransfertCtxCmd();
            ContratCptACtx contratCptACtx =new  ContratCptACtx();
            contratCptACtx.setContratCpt(gestionContratCptForm.getContratView().getContratCpt());    
            contratCptACtx.setOperationMoyPay(operationMoyPay);
            ValueObject vo = (ValueObject)transfertCtxCmd.execute(contratCptACtx);
            if (vo == null || vo.hasError()) {
               List listErreur = vo.getErrors();
               for (Iterator it = listErreur.iterator(); it.hasNext(); ) {
                   com.oxia.fwk.core.Error erreur = 
                   (com.oxia.fwk.core.Error)it.next();
                   ActionMessage actionMessage = 
                   new ActionMessage("exception.generique", 
                   erreur.getDescription());
                   actionMessages.add("Erreur ", actionMessage);
               }
                this.saveMessages(request, actionMessages);
               return mapping.findForward("error");
               }else{
                ContratCpt contratCpt=(ContratCpt)vo;
                SimpleDateFormat formater=new SimpleDateFormat("dd/MM/yyyy");
                String heureString = formater.format(gestionContratCptForm.getInitialisationView().getDateOp());
                String message="le contrat N°"+ contratCpt.getContratCptId().getCodStrcStrc()+" "+
                contratCpt.getContratCptId().getCodPrdPrd()+" "+contratCpt.getContratCptId().getNumCcptCcpt()+
                " à été transférer à contentieux en date du "+heureString;
                
                gestionContratCptForm.setLibelleConfirmation(message);    
            }

            return mapping.findForward("confirmation");


        } catch (Exception e) {
            com.oxia.fwk.core.Error erreur = new com.oxia.fwk.core.Error();
            StringBuffer text = 
                new StringBuffer("la transaction est Interrompu, une erreur dans GestionContratCptAction / Dispatch Action :transfertCtx ");
            text.append(e.toString());
            erreur.setCode("200");
            erreur.setDescription(text.toString());
            logger.error("Erreur au niveau de l'agence <<" +gestionContratCptForm.getCodStrcStrc()+ ">>. Exception : ",e);  
           // logger.error("Exception : ",e);  
            ActionMessage actionMessage = 
                new ActionMessage("exception.generique", 
                                  erreur.getDescription());
            actionMessages.add("Erreur ", actionMessage);
            this.saveMessages(request, actionMessages);
            return mapping.findForward("error");
        }

    }
    public ActionForward rechercherCptLivret(ActionMapping mapping, 
                                           ActionForm form, 
                                           HttpServletRequest request, 
                                           HttpServletResponse response) throws IOException, 
                                                                                ServletException {
        GestionContratCptForm gestionContratCptForm = 
            (GestionContratCptForm)form;
        try {

            ActionMessages actionMessages = new ActionMessages();
            /*******recherche du contrat**********/

            GetDetailContratCmd getDetailContratCmd = 
                new GetDetailContratCmd();
            ContratCpt contratCpt = new ContratCpt();
            ContratCptId contratCptId = new ContratCptId();
            contratCptId.setCodStrcStrc(Long.valueOf(gestionContratCptForm.getCodStrcStrc()));
            contratCptId.setCodPrdPrd(Long.valueOf(gestionContratCptForm.getCodPrdPrd()));
            contratCptId.setNumCcptCcpt(Long.valueOf(gestionContratCptForm.getNumCcptCcpt()));
            contratCpt = (ContratCpt)getDetailContratCmd.execute(contratCptId);
            if ( contratCpt!=null && !contratCpt.hasError() && contratCpt.getContratCptId() != null) {
                

                gestionContratCptForm.setContrat(contratCpt);
                gestionContratCptForm.setClient(contratCpt.getClient());
                gestionContratCptForm.setNomNomPers(contratCpt.getClient().getPersonne().getNomNomPers());
                gestionContratCptForm.setNomPrnPers(contratCpt.getClient().getPersonne().getNomPrnPers());
                gestionContratCptForm.setTypPcePers(contratCpt.getClient().getPersonne().getTypePiece().getCodTpceTpce().toString());
                gestionContratCptForm.setNumPcePers(StrHandler.lpad(contratCpt.getClient().getPersonne().getNumPcePers(), 
                                                                    '0', 8));
                gestionContratCptForm.setCodEtatCcpt(contratCpt.getCodEtatCcpt());
                gestionContratCptForm.setNumLivrCcpt(contratCpt.getNumLivrCcpt());
                gestionContratCptForm.setDatOuvCcpt(DateHandler.dateToStr(contratCpt.getDatOuvCcpt()));
                gestionContratCptForm.setMontSoldCcpt(StrHandler.formatmnt(contratCpt.getMontSoldCcpt().doubleValue()));
                gestionContratCptForm.setMontSoldActuel(contratCpt.getMontSoldCcpt());
                gestionContratCptForm.setLibDevDev(contratCpt.getDevise().getLibDevDev());
                gestionContratCptForm.setLivrets(contratCpt.getLivretEpargne());
                
            } else {
                if(contratCpt.hasError()){
                    List listErreur = contratCpt.getErrors();                    
                    for (Iterator it = listErreur.iterator(); it.hasNext(); ) {
                        com.oxia.fwk.core.Error erreur = (com.oxia.fwk.core.Error)it.next();
                        ActionMessage actionMessage = new ActionMessage("exception.generique",erreur.getDescription());
                        actionMessages.add("Erreur ", actionMessage);
                    }    
                    this.saveMessages(request, actionMessages);
                    return mapping.findForward("error");
                    
                }else gestionContratCptForm.setAlert("contratNonValide");

            }

            return mapping.findForward("indexLivret");


        } catch (Exception e) {
            com.oxia.fwk.core.Error erreur = new com.oxia.fwk.core.Error();
            StringBuffer text = 
                new StringBuffer("la transaction est Interrompu, une erreur dans GestionContratCptAction / Dispatch Action :rechercheCptLivert ");
            text.append(e.toString());
            erreur.setCode("200");
            erreur.setDescription(text.toString());
            logger.error("Erreur au niveau de l'agence <<" +gestionContratCptForm.getCodStrcStrc()+ ">>. Exception : ",e);  
            ActionMessage actionMessage = 
                new ActionMessage("exception.generique", 
                                  erreur.getDescription());
            actionMessages.add("Erreur ", actionMessage);
            this.saveMessages(request, actionMessages);
            return mapping.findForward("error");
        }

    }
    public ActionForward verifLivret(ActionMapping mapping, ActionForm form, 
                                        HttpServletRequest request, 
                                        HttpServletResponse response) throws IOException, 
                                                                             ServletException {


        ActionMessages actionMessages = new ActionMessages();

        GestionContratCptForm gestionContratCptForm = 
            (GestionContratCptForm)form;

        try {
            gestionContratCptForm.setActivValider("true");
            for (Iterator it = gestionContratCptForm.getContrat().getLivretEpargne().iterator(); it.hasNext(); ) {
                LivretEpargne livretEpargne =
                    (LivretEpargne)it.next();
                    if (livretEpargne.getNumLivrLive().equals(gestionContratCptForm.getNouvnumLivrCcpt())){
                        gestionContratCptForm.setAlert("LIVRETNONVALIDE");
                        gestionContratCptForm.setActivValider("false");
                    }
                 }
           
            return mapping.findForward("indexLivret");
          


        } catch (Exception e) {
            com.oxia.fwk.core.Error erreur = new com.oxia.fwk.core.Error();
            StringBuffer text = 
                new StringBuffer("la transaction est Interrompu, une erreur dans GestionContratCptAction / Dispatch Action :verifLivert ");
            text.append(e.toString());
            erreur.setCode("200");
            erreur.setDescription(text.toString());
            logger.error("Erreur au niveau de l'agence <<" +gestionContratCptForm.getCodStrcStrc()+ ">>. Exception : ",e);  
           // logger.error("Exception : ",e);  
            ActionMessage actionMessage = 
                new ActionMessage("exception.generique", 
                                  erreur.getDescription());
            actionMessages.add("Erreur ", actionMessage);
            this.saveMessages(request, actionMessages);
            return mapping.findForward("error");
        }

    }
    public List traiterListedemandes(List listDemandes, ActionForm form) {

        
        List listDemandeChequeView = new ArrayList();
        Context context = ContextHandler.getContext();
        DemandeChequeDAO demandeChequeDAO = 
            (DemandeChequeDAO)context.getBean("demandeChequeDAO");

        boolean ajouter = true;
        if (listDemandes != null && listDemandes.size() > 0) {


            for (Iterator it = listDemandes.iterator(); it.hasNext(); ) {
                DemandeChequeView demandeChequeView = new DemandeChequeView();
                DemandeCheque demandeCheque = (DemandeCheque)it.next();
               
                demandeChequeView.setDemandeCheque(demandeCheque);
                demandeChequeView.setDateDemande(DateHandler.dateToStr(demandeCheque.getDatDemDchq()));

               
                    if (demandeCheque.getCodEtatDchq() != null) {
                        if (demandeCheque.getCodEtatDchq().equals(Constants.DEM_CHQ_ATTENTE)) {
                            demandeChequeView.setEtatDemande("Attente");
                        } else if (demandeCheque.getCodEtatDchq().equals(Constants.DEM_CHQ_VALIDEE)) {
                            demandeChequeView.setEtatDemande("Validée");
                        } else if (demandeCheque.getCodEtatDchq().equals(Constants.DEM_CHQ_TOT_DELIVREE)) {
                            demandeChequeView.setEtatDemande("Tot Délivrée");
                        } else if (demandeCheque.getCodEtatDchq().equals(Constants.DEM_CHQ_REJETEE)) {
                            demandeChequeView.setEtatDemande("rejetée");
                        } else if (demandeCheque.getCodEtatDchq().equals(Constants.DEM_CHQ_TOT_SATISFAITE)) {
                            demandeChequeView.setEtatDemande("Tot satisfaite");
                        } else if (demandeCheque.getCodEtatDchq().equals(Constants.DEM_CHQ_PART_SATISFAITE)) {
                            demandeChequeView.setEtatDemande("Part satisfaite");
                        } else if (demandeCheque.getCodEtatDchq().equals(Constants.DEM_CHQ_PART_DELIVREE)) {
                            demandeChequeView.setEtatDemande("Part Délivrée");
                        } else if (demandeCheque.getCodEtatDchq().equals(Constants.DEM_CHQ_ENVOYEE_DR)) {
                            demandeChequeView.setEtatDemande("Envoyée DGR/DCCI");
                        }
                    }
                    if (demandeCheque.getCodTpceDchq().equals(Constants.COD_CIN))
                        demandeChequeView.setTypePieceDem("CIN");
                    else if (demandeCheque.getCodTpceDchq().equals(Constants.COD_RCS))
                        demandeChequeView.setTypePieceDem("RCS");
                    else if (demandeCheque.getCodTpceDchq().equals(Constants.COD_NUM_ORDRE))
                        demandeChequeView.setTypePieceDem("NUM");

                    demandeChequeView.setCodeAgence(StrHandler.lpad(demandeCheque.getContratCpt().getContratCptId().getCodStrcStrc().toString(), 
                                                                    '0', 3));
                    demandeChequeView.setCodeProduit(StrHandler.lpad(demandeCheque.getContratCpt().getContratCptId().getCodPrdPrd().toString(), 
                                                                     '0', 4));
                    demandeChequeView.setNumeroCompte(StrHandler.lpad(demandeCheque.getContratCpt().getContratCptId().getNumCcptCcpt().toString(), 
                                                                      '0', 6));
                    
                    
                    if (demandeCheque.getBoolWebDchq() != null) {  
                        demandeChequeView.setBoolWebDchq("Web");                    
                    }else demandeChequeView.setBoolWebDchq("Smile");  
                if ((!demandeCheque.getCodEtatDchq().equals(Constants.DEM_CHQ_REJETEE))&&
                    (!demandeCheque.getCodEtatDchq().equals(Constants.DEM_CHQ_TOT_DELIVREE)))
                    
                
                {
                    listDemandeChequeView.add(demandeChequeView);
                }
            } // Fin For  
        }
        return listDemandeChequeView;

    }
    
    //
     public ActionForward initierPageNotificationDeces(ActionMapping mapping, ActionForm form, 
                                      HttpServletRequest request, 
                                      HttpServletResponse response) throws IOException, 
                                                                           ServletException {
         ActionMessages actionMessages = new ActionMessages();
         GestionContratCptForm gestionContratCptForm = 
             (GestionContratCptForm)form;
         ParamAgence paramAgence = new ParamAgence();                 
         paramAgence = (ParamAgence)request.getSession().getAttribute("paramAgBNA");/// structure de l'agent qui fait la saisie
         try {
             gestionContratCptForm.setCodStrcStrc(paramAgence.getCodStrcStrc().toString());
             gestionContratCptForm.clearFormNotificationDeces();
             gestionContratCptForm.setDateActuelle(paramAgence.getDateComptable());
             return mapping.findForward("notificationDeces");
             } catch (Exception e) {
                 com.oxia.fwk.core.Error erreur = new com.oxia.fwk.core.Error();
                 StringBuffer text = 
                     new StringBuffer("la transaction est Interrompu, une erreur dans initierPageNotificationDeces / Dispatch Action :initierPageConsultContratBanque ");
                 text.append(e.toString());
                 erreur.setCode("200");
                 erreur.setDescription(text.toString());
                 logger.error("Erreur au niveau de l'agence <<" + paramAgence.getCodStrcStrc().toString() + ">>. Exception : ",e);  
            //     logger.error("Exception : ",e); 
                 ActionMessage actionMessage = new ActionMessage("exception.generique",erreur.getDescription());
                 actionMessages.add("Erreur ", actionMessage);
                 this.saveMessages(request, actionMessages);
                 return mapping.findForward("error");
             }
     }
     
     
    public ActionForward rechercherListeContratNotificationDeces(ActionMapping mapping, 
                                            ActionForm form, 
                                            HttpServletRequest request, 
                                            HttpServletResponse response) throws IOException, 
                                                                                 ServletException {

        /*************************************commande de rechercher personne*/
        ActionMessages actionMessages = new ActionMessages();
        GestionContratCptForm gestionContratCptForm = 
            (GestionContratCptForm)form;
        try {
                        
            gestionContratCptForm.setListeContrats(null);
            PersonneRechercheContratVo personneRechercheContratVo = new PersonneRechercheContratVo();            
            personneRechercheContratVo.setTypePersonne(Constants.PERSMORALE);
            Listes listesCpts = new Listes();
            Listes listesMandats = new Listes();
            List listMandValide = new ArrayList();
            listesCpts.setList(new ArrayList());            
            PersonneStrc personneStrc = new PersonneStrc();
            GetListContratMandataireCmd getListContratMandataireCmd = 
                    new GetListContratMandataireCmd();
            GetListCotitulairePersonneCmd getListCotitulairePersonneCmd = 
                    new GetListCotitulairePersonneCmd();
            // si la recherche est effectuée par type et num piece
            
                personneStrc.setCodTpceTpce(new Long(gestionContratCptForm.getTypePieceId()));
                personneStrc.setNumPcePers(gestionContratCptForm.getNumPieceId());
                personneStrc.setCasNotificationDeces("true");                
                personneRechercheContratVo.setPersonneStrc(personneStrc);   
                personneRechercheContratVo.setEtatContrat(Constants.COD_ETAT_CPT_VALID);
                GetListContratCmd getListContratCmd = new GetListContratCmd();    
                GetMandatAvaliderCmd getMandatAvaliderCmd = new GetMandatAvaliderCmd();        
                listesCpts = (Listes)getListContratCmd.execute(personneRechercheContratVo);
                
            
            if(!listesCpts.hasError()){  
                    if (listesCpts.getList() == null || listesCpts.getList().size()==0) {
                        gestionContratCptForm.setAlert("ClientInexistant");
                    } else {
                       if (listesCpts.getList().size() > 0 ) {
                        // affecter la liste des contrats à la liste de collection Tag
                        gestionContratCptForm.setListeContrats(listesCpts.getList()); 
                        List listeDesContratsView = new ArrayList();
                        for (Iterator it = listesCpts.getList().iterator(); 
                             it.hasNext(); ) {
                            ContratCpt contratCpt = (ContratCpt)it.next();
                            String cleContrat = 
                                StrHandler.lpad(contratCpt.getContratCptId().getCodStrcStrc().toString(), 
                                                '0', 3) + 
                                StrHandler.lpad(contratCpt.getContratCptId().getCodPrdPrd().toString(), 
                                                '0', 4) + 
                                StrHandler.lpad(contratCpt.getContratCptId().getNumCcptCcpt().toString(), 
                                                '0', 6);
                            ContratCptView contratCptView = new ContratCptView();
                            contratCptView.setCleContrat(cleContrat);
                            
                            contratCptView.setDateContrat(DateHandler.dateToStr(contratCpt.getDatOuvCcpt()));
                            //contratCptView.setCodeAgence(contratCpt.getStructure().getCodStrcStrc().toString().);
                            contratCptView.setNumeroCompte(StrHandler.lpad(contratCpt.getContratCptId().getNumCcptCcpt().toString(), 
                                                                           '0', 
                                                                           6));
                            contratCptView.setCodeAgence(StrHandler.lpad(contratCpt.getContratCptId().getCodStrcStrc().toString(), 
                                                                         '0', 3));
                            contratCptView.setCodeProduit(StrHandler.lpad(contratCpt.getContratCptId().getCodPrdPrd().toString(), 
                                                                          '0', 4));
                            
                            contratCptView.setContratCpt(contratCpt);
                            listeDesContratsView.add(contratCptView);
                            
                            
                            MandatRecherche mandatRecherche = new MandatRecherche();
                            mandatRecherche.setCodEtat("V");  
                            mandatRecherche.setContratCptId(contratCpt.getContratCptId());
                            mandatRecherche.setCodStrcConcer(contratCpt.getContratCptId().getCodStrcStrc());
                            mandatRecherche.setCodMenu("DEC");
                            listesMandats = (Listes)getMandatAvaliderCmd.execute(mandatRecherche);
                            
                            if (listesMandats != null &&  listesMandats.getList().size() > 0 ) {                               
                               for (Iterator it1 = listesMandats.getList().iterator(); 
                                    it1.hasNext(); ) {
                                   MandatPersonneMandat mandatPersonneMandat = (MandatPersonneMandat)it1.next();
                                   listMandValide.add(mandatPersonneMandat.getMandat());
                               } 
                           }
                            
                          } 
                          
                        gestionContratCptForm.setListeMandatsValides(listMandValide);
                          
                        if(listeDesContratsView.size()>0){  
                           gestionContratCptForm.setListeContratsView(listeDesContratsView);
                           gestionContratCptForm.setAlert("ClientExistant"); 
                            ContratCpt premierContrat = new ContratCpt();
                            Client client = new Client();
                            premierContrat = (ContratCpt)listesCpts.getList().get(0);
                            client = premierContrat.getClient();
                            gestionContratCptForm.setClient(client);
                            if (client.getTypePers().getCodTperTper().equals(Constants.PERSPHYSIQUE)) {
                                gestionContratCptForm.setNomNomPersClient(client.getPersonne().getNomNomPers());
                                gestionContratCptForm.setNomPrnPersClient(client.getPersonne().getNomPrnPers());
                            }
                            Listes listesCptsMandataire = new Listes();
                            Listes listEntiteCotit = new Listes();
                            /*mandataire sur les contrats suivants */
                            listesCptsMandataire = 
                                    (Listes)getListContratMandataireCmd.execute(personneStrc);
                            gestionContratCptForm.setListeContratsMandataire(listesCptsMandataire.getList());
                            
                            /* cotitulaire dans les entités cotitulaire suivantes  */
                            listEntiteCotit = 
                                    (Listes)getListCotitulairePersonneCmd.execute(personneStrc);
                            gestionContratCptForm.setListeEntiteCotit(listEntiteCotit.getList());
                            
                        }else gestionContratCptForm.setAlert("ClientInexistant");
                        
                    }else{
                        gestionContratCptForm.setAlert("ClientInexistant");
                    }
            }
            
            }else {                   
                     List listErreur = listesCpts.getErrors();
                     //ActionMessages actionMessages = new ActionMessages();
                     for (Iterator it = listErreur.iterator(); it.hasNext(); ) {
                         com.oxia.fwk.core.Error erreur = (com.oxia.fwk.core.Error)it.next();
                         ActionMessage actionMessage = new ActionMessage("exception.generique",erreur.getDescription());
                         actionMessages.add("Erreur ", actionMessage);
                     }    
                    this.saveMessages(request, actionMessages);
                    return mapping.findForward("error");                    
                }            

            return mapping.findForward("notificationDeces");
           
            } catch (Exception e) {
                com.oxia.fwk.core.Error erreur = new com.oxia.fwk.core.Error();
                StringBuffer text = 
                    new StringBuffer("la transaction est Interrompu, une erreur dans rechercherListeContratBanque / Dispatch Action :rechercherListeContratBanque ");
                text.append(e.toString());
                erreur.setCode("200");
                erreur.setDescription(text.toString());
                logger.error("Erreur au niveau de l'agence <<" +gestionContratCptForm.getCodStrcStrc()+ ">>. Exception : ",e);  
         //       logger.error("Exception : ",e); 
                ActionMessage actionMessage = new ActionMessage("exception.generique",erreur.getDescription());
                actionMessages.add("Erreur ", actionMessage);
                this.saveMessages(request, actionMessages);
                return mapping.findForward("error");
            }
    }
    
    
    
    
    public ActionForward validerNotificationDeces(ActionMapping mapping, 
                                            ActionForm form, 
                                            HttpServletRequest request, 
                                            HttpServletResponse response) throws IOException, 
                                                                                 ServletException {

        /*************************************commande de rechercher personne*/
        ActionMessages actionMessages = new ActionMessages();
        GestionContratCptForm gestionContratCptForm = (GestionContratCptForm)form;
        try {
          //mettre à jour la personne
          
           
           if(gestionContratCptForm.getClient() != null){
               gestionContratCptForm.getClient().getPersonne().setDatDecePers(DateHandler.strToDate(gestionContratCptForm.getDateDeces()));
               gestionContratCptForm.getClient().getPersonne().setNumDecePers(gestionContratCptForm.getNumDeces());
               UpdatePersonneCmd updatePersonneCmd = new UpdatePersonneCmd();
               Personne pers = new Personne();
               pers=(Personne)updatePersonneCmd.execute(gestionContratCptForm.getClient().getPersonne());
           }
          if(gestionContratCptForm.getListeContrats().size()>0 ){
              // Mettre les contrats de ce clients à BLOQUE
              UpdateContratCptCmd updateContratCptCmd = new UpdateContratCptCmd();
              for (Iterator it = gestionContratCptForm.getListeContrats().iterator(); 
                   it.hasNext(); ) {
                  ContratCpt contratCpt = (ContratCpt)it.next();                  
                  if(contratCpt.getCodEtatCcpt().equalsIgnoreCase(Constants.COD_ETAT_CPT_VALID)){
                      //mettre l'etat du contrat à bloqué
                       contratCpt.setCodEtatCcpt(Constants.COD_ETAT_CPT_BLOQUE);
                       ContratCpt contratCptUpdated = new ContratCpt();
                       contratCptUpdated= (ContratCpt)updateContratCptCmd.execute(contratCpt);                       
                  }                  
              }              
          }          
            if(gestionContratCptForm.getListeMandatsValides().size()>0 ){
                // annuler tous les mandats dont les client est mandant...
                UpdateMandatCmd updateMandatCmd = new UpdateMandatCmd();
                for (Iterator it = gestionContratCptForm.getListeMandatsValides().iterator(); 
                     it.hasNext(); ) {
                     Mandat mandat = (Mandat)it.next();  
                        //mettre l'etat du mandat à Annuleé                    
                       mandat.setCodEtatMand(Constants.COD_ETAT_MAND_ANN);                     
                       mandat= (Mandat)updateMandatCmd.execute(mandat);
                    
                }
            }
           
            if(gestionContratCptForm.getListeContratsMandataire().size()>0 ){
                // annuler tous les mandats dont les client est mandant...
                UpdateMandatCmd updateMandatCmd = new UpdateMandatCmd();
                for (Iterator it = gestionContratCptForm.getListeContratsMandataire().iterator(); 
                     it.hasNext(); ) {
                     Mandat mandat = (Mandat)it.next();  
                        //mettre l'etat du mandat à Annuleé dont le cas où la signature est conjointe
                     if(mandat.getCodSignMand().equals(Constants.COD_TYPE_SIGNATURE_CONJOINTE)){
                       mandat.setCodEtatMand(Constants.COD_ETAT_MAND_ANN);                     
                       mandat= (Mandat)updateMandatCmd.execute(mandat);
                     }
                }
            }
            
            if(gestionContratCptForm.getListeEntiteCotit().size()>0 ){
                // bloquer tous les contrats  des cotitulaire indivis
                 UpdateContratCptCmd updateContratCptCmd = new UpdateContratCptCmd();
                for (Iterator it = gestionContratCptForm.getListeEntiteCotit().iterator(); 
                     it.hasNext(); ) {
                     CoTitulaire coTitulaire = (CoTitulaire)it.next(); 
                        
                     if(coTitulaire.getCodTcotCoti().equals("I")){
                         GetPersonneCptCmd getPersonneCptCmd = new GetPersonneCptCmd();
                         PersonneStrc personneStrc = new PersonneStrc(); //Vo input

                         personneStrc.setCodTpceTpce(Long.valueOf("11"));
                         personneStrc.setNumPcePers(String.valueOf(coTitulaire.getCoTitulaireId().getNumSeqCli()));
                         PersonneCpt personneCpt = new PersonneCpt(); //Vo output
                         personneCpt = (PersonneCpt)getPersonneCptCmd.execute(personneStrc);
                         
                         if (personneCpt.getClient() != null) {
                             if(personneCpt.getListeContratCpt()!= null &&  personneCpt.getListeContratCpt().size()>0){
                                 for (Iterator it1 = personneCpt.getListeContratCpt().iterator(); 
                                      it1.hasNext();) {
                                      ContratCpt contratCpt = (ContratCpt)it1.next(); 
                                      contratCpt.setCodEtatCcpt(Constants.COD_ETAT_CPT_BLOQUE);
                                      contratCpt = (ContratCpt)updateContratCptCmd.execute(contratCpt);
                                  }
                             }
                         } 
                     }
                }
            }
          
          
          return mapping.findForward("notificationDeces");
    
        } catch (Exception e) {
            com.oxia.fwk.core.Error erreur = new com.oxia.fwk.core.Error();
            StringBuffer text = 
                new StringBuffer("la transaction est Interrompu, une erreur dans validerNotificationDeces / Dispatch Action :rechercherListeContratBanque ");
            text.append(e.toString());
            erreur.setCode("200");
            erreur.setDescription(text.toString());
            logger.error("Erreur au niveau de l'agence <<" +gestionContratCptForm.getCodStrcStrc()+ ">>. Exception : ",e);  
        //       logger.error("Exception : ",e);
            ActionMessage actionMessage = new ActionMessage("exception.generique",erreur.getDescription());
            actionMessages.add("Erreur ", actionMessage);
            this.saveMessages(request, actionMessages);
            return mapping.findForward("error");
        }
     
     
}  

}
