
package com.bna.smile.web.souscription.actions;

import com.bna.commun.model.Activite;
import com.bna.commun.model.ActiviteId;
import com.bna.commun.model.Adresse;
import com.bna.commun.model.Categorie;
import com.bna.commun.model.CategorieId;
import com.bna.commun.model.CategoriePersonne;
import com.bna.commun.model.Client;
import com.bna.commun.model.CoTitulaire;
import com.bna.commun.model.CoTitulaireId;
import com.bna.commun.model.CodePostal;
import com.bna.commun.model.ContratCpt;
import com.bna.commun.model.ContratCptId;
import com.bna.commun.model.Devise;
import com.bna.commun.model.FormeJuridique;
import com.bna.commun.model.Gouvernorat;
import com.bna.commun.model.MotifEtat;
import com.bna.commun.model.MotifEtatId;
import com.bna.commun.model.Pays;
import com.bna.commun.model.Personne;
import com.bna.commun.model.Personnel;
import com.bna.commun.model.PieceAnnexe;
import com.bna.commun.model.PieceAnnexeId;
import com.bna.commun.model.Profession;
import com.bna.commun.model.ProfessionId;
import com.bna.commun.model.Regime;
import com.bna.commun.model.RegimeId;
import com.bna.commun.model.StructureDomaine;
import com.bna.commun.model.Tribunal;
import com.bna.commun.model.TypePers;
import com.bna.commun.model.TypePiece;
import com.bna.commun.util.ContextHandler;
import com.bna.commun.util.DateHandler;
import com.bna.commun.util.SmileUtil;
import com.bna.commun.util.StrHandler;
import com.bna.smile.model.constant.Constants;
import com.bna.smile.model.domainecommun.commande.GetCodePostalCmd;
import com.bna.smile.model.domainecommun.commande.GetDetailContratCmd;
import com.bna.smile.model.domainecommun.commande.GetEntiteCotitByContratCmd;
import com.bna.smile.model.domainecommun.commande.GetListCotitulairePersonneCmd;
import com.bna.smile.model.domainecommun.commande.GetListMembreCotitulaireCmd;
import com.bna.smile.model.domainecommun.commande.GetNombreContratParClientCmd;
import com.bna.smile.model.domainecommun.commande.GetPaysCmd;
import com.bna.smile.model.domainecommun.commande.GetPersonneCptCmd;
import com.bna.smile.model.domainecommun.commande.GetPersonnelByCinCmd;
import com.bna.smile.model.domainecommun.commande.GetPieceAnnexeCmd;
import com.bna.smile.model.domainecommun.model.ListTypeCatTpce;
import com.bna.smile.model.domainecommun.model.Listes;
import com.bna.smile.model.domainecommun.model.PersonneCpt;
import com.bna.smile.model.domainecommun.model.PersonneStrc;
import com.bna.smile.model.domainecommun.model.Tuteur;
import com.bna.smile.model.domainecontratcompte.souscriptioncontratcompte.commande.ChargerRgmCatEpargneCmd;
import com.bna.smile.model.domainecontratcompte.souscriptioncontratcompte.commande.ChargerTypeCatPcePersonneCmd;
import com.bna.smile.model.domainecontratcompte.souscriptioncontratcompte.commande.GetProduitAutorisesCmd;
import com.bna.smile.model.domainecontratcompte.souscriptioncontratcompte.commande.GetTuteurCmd;
import com.bna.smile.model.domainecontratcompte.souscriptioncontratcompte.commande.InsertClientContratCmd;
import com.bna.smile.model.domainecontratcompte.souscriptioncontratcompte.commande.InsertCompteLieCmd;
import com.bna.smile.model.domainecontratcompte.souscriptioncontratcompte.dao.PersonneDAO;
import com.bna.smile.model.domainecontratcompte.souscriptioncontratcompte.model.ListRgmCatEpargne;
import com.bna.smile.model.domainecontratcompte.souscriptioncontratcompte.model.ParamCompteLie;
import com.bna.smile.model.domainecontratcompte.souscriptioncontratcompte.model.ParamEpargne;
import com.bna.smile.model.domainecontratcompte.souscriptioncontratcompte.model.ParamInsertContrat;
import com.bna.smile.model.domainecontratcompte.souscriptioncontratcompte.model.ParamPers;
import com.bna.smile.model.domainecontratcompte.souscriptioncontratcompte.model.TypeCatPers;
import com.bna.smile.web.commun.model.ParamAgence;
import com.bna.smile.web.commun.util.SessionUtil;
import com.bna.smile.web.procuration.model.Mandataire;
import com.bna.smile.web.souscription.forms.SouscriptionContratCompteForm;

import com.oxia.fwk.context.Context;
import com.oxia.fwk.core.IValueObject;
import com.oxia.fwk.core.ValueObject;

import fr.improve.struts.taglib.layout.datagrid.Datagrid;

import java.io.IOException;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
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


//import com.bna.smile.model.souscriptionContratCompte.service.PersonneService;


public class SouscriptionContratCompteAction extends DispatchAction {

    /**
     * <B> Action de la page  souscriptionContratCompte.jsp  </B>
     * @param mapping The ActionMapping used to select this instance.
     * @param form The optional ActionForm bean for this request.
     * @param request The HTTP Request we are processing.
     * @param response The HTTP Response we are processing.
     * Nom du package : com.bna.smile.web.souscription.actions
     *
     * @author El arbi Hassine
     * @version le 19/01/2007
     * @modify le 06/07/07
     */
     private static final Logger logger = Logger.getLogger(SouscriptionContratCompteAction.class);

    /**
     * @param mapping ActionMapping
     * @param form souscriptionContratCompteForm
     * @param request HttpServletRequest
     * @param response HttpServletResponse
     * @return Méthode d'initialisation des informations du formulaire : L'agence, L'utilisateur, La catégorie de la personne ...
     * @throws IOException
     * @throws ServletException
     * 
     */
    public

    ActionForward initierPage(ActionMapping mapping, ActionForm form, 
                              HttpServletRequest request, 
                              HttpServletResponse response) throws IOException, 
                                                                   ServletException {

        
      
        
        
        SessionUtil sessionUtil =new SessionUtil();
        //Suppression des anciens Bean de type Form de la session, SAUF "souscriptionContratCompteForm"
        sessionUtil.removeSession(request,"souscriptionContratCompteForm"); 
        
        ActionMessages actionMessages = new ActionMessages();
        SouscriptionContratCompteForm souscriptionContratCompteForm = 
            (SouscriptionContratCompteForm)form;
      
        try {
            ParamAgence paramAgence = new ParamAgence();
            paramAgence = (ParamAgence)request.getSession().getAttribute("paramAgBNA");/// structure de l'agent qui fait la saisie
               
            souscriptionContratCompteForm.setCodStrcStrc(paramAgence.getCodStrcStrc().toString());
            souscriptionContratCompteForm.setNumMatriculeUser(paramAgence.getNumMatrUser().toString());
            
             //verification de l'habilitation sur cet operation
             StructureDomaine structureDomaine = new StructureDomaine(paramAgence.getCodStrcStrc(),Constants.COD_DOM_CONTRATCOMPTE);
             boolean bool = SmileUtil.testDomaineOuvert(structureDomaine);
            
            if (souscriptionContratCompteForm.getCasAnnulation().equals("true")) {            
                souscriptionContratCompteForm.setTypePersonneId(null);
                souscriptionContratCompteForm.setCategoriePersonneId(null);
                souscriptionContratCompteForm.setListeTypePersonne(null);
                souscriptionContratCompteForm.setListeCategoriePersonne(null);
            }
            souscriptionContratCompteForm.clearForm();
            souscriptionContratCompteForm.setNumPieceId("");


            ChargerTypeCatPcePersonneCmd chargerTypeCatPcePersonneCmd = 
                new ChargerTypeCatPcePersonneCmd();

            ListTypeCatTpce listTypeCatTpce = new ListTypeCatTpce();
            //
            if (souscriptionContratCompteForm.getTypePersonneMenu().equals("physique") || 
                souscriptionContratCompteForm.getTypePersonneMenu().equals("personnel")) {
                souscriptionContratCompteForm.setTypePersonneId(Constants.PERSPHYSIQUE);
            } else if (souscriptionContratCompteForm.getTypePersonneMenu().equals("morale")) {
                souscriptionContratCompteForm.setTypePersonneId(Constants.PERSMORALE);
            } else if (souscriptionContratCompteForm.getTypePersonneMenu().equals("cotitulaire")) {
                souscriptionContratCompteForm.setTypePersonneId(Constants.ENTCOTITULAIRE);
            }

            if (souscriptionContratCompteForm.getChoixInitPage().equals("initPage") || 
                souscriptionContratCompteForm.getChoixInitPage().equals("choixTypPers")) {
                souscriptionContratCompteForm.setCategoriePersonneId(null);
            }

            TypeCatPers typeCatPersVo = 
                new TypeCatPers(souscriptionContratCompteForm.getTypePersonneId(), 
                                souscriptionContratCompteForm.getCategoriePersonneId());

            if (souscriptionContratCompteForm.getChoixInitPage().equals("initPage") || 
                souscriptionContratCompteForm.getChoixInitPage().equals("choixTypPers")) {
                typeCatPersVo.setCodCatpCatp(null);
            }

            listTypeCatTpce = 
                    (ListTypeCatTpce)chargerTypeCatPcePersonneCmd.execute(typeCatPersVo);

            if (!listTypeCatTpce.hasError()) {
                if (souscriptionContratCompteForm.getChoixInitPage().equals("initPage")) {
                    souscriptionContratCompteForm.setListeTypePersonne(listTypeCatTpce.getListTypePers());
                    souscriptionContratCompteForm.setListTypePersonneClt(listTypeCatTpce.getListTypePers());
                    // souscriptionContratCompteForm.setListeFormeJuridique(listTypeCatTpce.getListeCategp_Formj());

                }
                if (souscriptionContratCompteForm.getChoixInitPage().equals("initPage") || 
                    souscriptionContratCompteForm.getChoixInitPage().equals("choixTypPers")) {
                    souscriptionContratCompteForm.setListeCategoriePersonne(listTypeCatTpce.getListCatPers());
                    souscriptionContratCompteForm.setListCategoriePersonneClt(listTypeCatTpce.getListCatPers());
                    souscriptionContratCompteForm.setCodTypePieceId(new String(""));
                    souscriptionContratCompteForm.setTypePieceId(new String(""));
                }
                if (souscriptionContratCompteForm.getChoixInitPage().equals("initPage") || 
                    souscriptionContratCompteForm.getChoixInitPage().equals("choixTypPers") || 
                    souscriptionContratCompteForm.getChoixInitPage().equals("choixCatPers")) {
                    souscriptionContratCompteForm.setListeFormeJuridique(listTypeCatTpce.getListeCategp_Formj());
                    souscriptionContratCompteForm.setCodTypePieceId(listTypeCatTpce.getTypePiece().getCodTpceTpce().toString());
                    souscriptionContratCompteForm.setTypePieceId(listTypeCatTpce.getTypePiece().getLibSiglTpce());

                }
                DateFormat myformat = new SimpleDateFormat("dd/MM/yyyy");
                String d = myformat.format(new Date());
                souscriptionContratCompteForm.setDateActuelle(d);

                /// Create a new datagrid (of Mandataire);
                Datagrid lc_datagrid = Datagrid.getInstance();
                lc_datagrid.setData(new ArrayList());
                lc_datagrid.setDataClass(Mandataire.class);

                souscriptionContratCompteForm.setListPersonneCotitGrid(lc_datagrid);

            } else {
                List listErreur = listTypeCatTpce.getErrors();
                //ActionMessages actionMessages = new ActionMessages();
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
            String appel = "";
            if (souscriptionContratCompteForm.getTypePersonneMenu().equals("physique") || 
                souscriptionContratCompteForm.getTypePersonneMenu().equals("personnel")) {
                appel = "successPhysique";
            } else if (souscriptionContratCompteForm.getTypePersonneMenu().equals("morale")) {
                appel = "successMorale";
            } else if (souscriptionContratCompteForm.getTypePersonneMenu().equals("cotitulaire")) {
                appel = "successCotit";
            } else if (souscriptionContratCompteForm.getTypePersonneMenu().equals("CptLie")) {
                appel = "successLie";
            }

            return mapping.findForward(appel);


            } catch (Exception e) {
                            
                             ActionMessage actionMessage = 
                                 new ActionMessage("exception.generique", 
                                                   e.getMessage() );
                             actionMessages.add("Erreur ", actionMessage);   
                             this.saveMessages(request, actionMessages);
                             logger.error("Exception : ",e);
                             return mapping.findForward("error");  
         }
        

      
    }

    /**
     * @param mapping ActionMapping
     * @param form souscriptionContratCompteForm
     * @param request HttpServletRequest
     * @param response HttpServletResponse
     * @return Méthode pour la recherche 
     * de la personne et le 'SET' de ses informations dans la page JSP (Morale, Physique, Mineur, Personnel ...).
     * Les commandes utilisés sont: getPersonneCptCmd , GetPersonnelByCinCmd , GetPaysCmd , GetCodePostalCmd
     * @throws IOException
     * @throws ServletException
     *  */
    public ActionForward rechercherPersonne(ActionMapping mapping, 
                                            ActionForm form, 
                                            HttpServletRequest request, 
                                            HttpServletResponse response) throws IOException, 
                                                                                 ServletException {

        /*************************************commande de rechercher personne*/
        ActionMessages actionMessages = new ActionMessages();
        SouscriptionContratCompteForm souscriptionContratCompteForm = 
            (SouscriptionContratCompteForm)form;

        try {

            DateFormat myformat = new SimpleDateFormat("dd/MM/yyyy");

            // traitement du cas mineur s'il existe : affecter son numero d'ordre au  numero piece de la personne
            if (souscriptionContratCompteForm.getCodTypePieceId().equals(Constants.COD_NUM_ORDRE.toString()) && 
                souscriptionContratCompteForm.getCategoriePersonneId().equals(Constants.COD_CATEGORIE_MINEUR)) {
                String numOrdreMineur = 
                    new String(request.getParameter("numOrdreMineur"));
                if (numOrdreMineur != "") {
                    souscriptionContratCompteForm.setNumPieceId(numOrdreMineur);
                }
            }

            clearPage(mapping, form, request, response);

            //si type piece RCS

            if (souscriptionContratCompteForm.getCodTypePieceId().equals(Constants.COD_RCS.toString())) {
                if (!Constants.verifRCS(souscriptionContratCompteForm.getNumPieceId(), 
                                        souscriptionContratCompteForm.getTypePersonneId())) {
                    souscriptionContratCompteForm.setAlert("rcsErreur");
                    return mapping.findForward("successMorale");
                }
            }
            souscriptionContratCompteForm.setOpenTabsheetProduit("true");

            GetPersonneCptCmd getPersonneCptCmd = new GetPersonneCptCmd();
            PersonneStrc personneStrc = new PersonneStrc(); //Vo input

            personneStrc.setCodTpceTpce(new Long(souscriptionContratCompteForm.getCodTypePieceId()));
            personneStrc.setNumPcePers(souscriptionContratCompteForm.getNumPieceId());
            personneStrc.setCodStrcStrc(new Long(souscriptionContratCompteForm.getCodStrcStrc()));
            
            if(souscriptionContratCompteForm.getTypePersonneMenu().equals("personnel")){
                souscriptionContratCompteForm.setOpenTabsheetProduit("false");
                souscriptionContratCompteForm.setOpenTabsheetProduit("false");
                personneStrc.setCodStrcStrc(null);
                GetPersonnelByCinCmd getPersonnelByCinCmd= new GetPersonnelByCinCmd(); 
                Personnel personnel = new Personnel();
                personnel.setNumCinUser(personneStrc.getNumPcePers());
                personnel = (Personnel)getPersonnelByCinCmd.execute(personnel);
                
                if(personnel.getNumMatrUser() != null){                   
                    souscriptionContratCompteForm.setCodeProduitCpt("103");
                    souscriptionContratCompteForm.setLibelleProduitCpt("Comptes chèques personnel BNA");
                    souscriptionContratCompteForm.setCodePrdCpt("0103");
                    souscriptionContratCompteForm.setNumCompteCpt("88" + StrHandler.lpad(personnel.getNumMatrUser(),'0',4));  
                    souscriptionContratCompteForm.setAlertProduit("matriculeExistante"); 
                }else souscriptionContratCompteForm.setAlertProduit("matriculeInexistante"); 
                
            }
            
            PersonneCpt personneCpt = new PersonneCpt(); //Vo output
            personneCpt = (PersonneCpt)getPersonneCptCmd.execute(personneStrc);

            if (!personneCpt.hasError()) {
                if (personneCpt.getPersonne() != null) {
                    if (souscriptionContratCompteForm.getTypePersonneId().equals(Constants.PERSPHYSIQUE)){
                        souscriptionContratCompteForm.setDateNaissancePrd(myformat.format(personneCpt.getPersonne().getDatNaisPers()));
                        souscriptionContratCompteForm.setNomId(personneCpt.getPersonne().getNomNomPers());
                        souscriptionContratCompteForm.setPrenomId(personneCpt.getPersonne().getNomPrnPers());
                        souscriptionContratCompteForm.setTitreId(personneCpt.getPersonne().getLibTitrPers());
                        // affecter l'adresse du la personne physique---------------------------------------------
                        souscriptionContratCompteForm.setImmeubleCpt(personneCpt.getPersonne().getAdresseResid().getImmeuble());
                        souscriptionContratCompteForm.setRueCpt(personneCpt.getPersonne().getAdresseResid().getRue());
                        souscriptionContratCompteForm.setCiteCpt(personneCpt.getPersonne().getAdresseResid().getCite());
                        //souscriptionContratCompteForm.setVilleCpt(personneCpt.getPersonne().getAdresseResid().getVille());
                        /* pays */
                        if (personneCpt.getPersonne().getAdresseResid().getCodPaysPays() != 
                            null) {
                            souscriptionContratCompteForm.setCodPayCpt(personneCpt.getPersonne().getAdresseResid().getCodPaysPays());
                            GetPaysCmd getPaysCmd = new GetPaysCmd();
                            Pays pays = new Pays();
                            pays.setCodPaysPays(personneCpt.getPersonne().getAdresseResid().getCodPaysPays());
                            pays = (Pays)getPaysCmd.execute(pays);
                            if (pays.getLibPaysPays() != null) {
                                souscriptionContratCompteForm.setPaysCpt(pays.getLibPaysPays());
                                souscriptionContratCompteForm.setCodPayCpt(pays.getCodPaysPays());
                            }
                        }
                        /* code Postal*/
                        if (personneCpt.getPersonne().getAdresseResid().getCodCpCp() != 
                            null) {
                            souscriptionContratCompteForm.setCodePostalCpt(personneCpt.getPersonne().getAdresseResid().getCodCpCp());
                            // si le pays est la tunisie extraire le libelle du code postal
                            if ((personneCpt.getPersonne().getAdresseResid().getCodPaysPays() != 
                                 null) && 
                                (personneCpt.getPersonne().getAdresseResid().getCodPaysPays().equals(Constants.COD_PAYS_TUNISIE))) {
                                GetCodePostalCmd getCodePostalCmd = 
                                    new GetCodePostalCmd();
                                CodePostal codePostal = new CodePostal();
                                codePostal.setCodCpCp(Long.valueOf(personneCpt.getPersonne().getAdresseResid().getCodCpCp()));
                                codePostal = 
                                        (CodePostal)getCodePostalCmd.execute(codePostal);
                                souscriptionContratCompteForm.setLibCodePostalCpt(codePostal.getLibCpCp());
                                souscriptionContratCompteForm.setCodGouvGouvCpt(codePostal.getGouvernorat().getCodGouvGouv().toString());
                                souscriptionContratCompteForm.setLibGouvGouvCpt(codePostal.getGouvernorat().getLibGouvGouv());

                            }
                        }
                                              
                        //--------------------------------------------------------------------------------
                    } else if (souscriptionContratCompteForm.getTypePersonneId().equals(Constants.PERSMORALE)) {
                        souscriptionContratCompteForm.setNomId(personneCpt.getPersonne().getNomRsPers());
                        souscriptionContratCompteForm.setPrenomId(personneCpt.getPersonne().getLibSiglPers());
                        // affecter l'adresse du la personne morale ---------------------------------------------
                        souscriptionContratCompteForm.setImmeubleCpt(personneCpt.getPersonne().getAdresseProf().getImmeuble());
                        souscriptionContratCompteForm.setRueCpt(personneCpt.getPersonne().getAdresseProf().getRue());
                        souscriptionContratCompteForm.setCiteCpt(personneCpt.getPersonne().getAdresseProf().getCite());
                        //souscriptionContratCompteForm.setVilleCpt(personneCpt.getPersonne().getAdresseProf().getVille());
                        /* pays */
                        if (personneCpt.getPersonne().getAdresseProf().getCodPaysPays() != 
                            null) {
                            souscriptionContratCompteForm.setCodPayCpt(personneCpt.getPersonne().getAdresseProf().getCodPaysPays());
                            GetPaysCmd getPaysCmd = new GetPaysCmd();
                            Pays pays = new Pays();
                            pays.setCodPaysPays(personneCpt.getPersonne().getAdresseProf().getCodPaysPays());
                            pays = (Pays)getPaysCmd.execute(pays);
                            if (pays.getLibPaysPays() != null) {
                                souscriptionContratCompteForm.setPaysCpt(pays.getLibPaysPays());
                                souscriptionContratCompteForm.setCodPayCpt(pays.getCodPaysPays());
                            }
                        }
                        /* code Postal*/
                        if (personneCpt.getPersonne().getAdresseProf().getCodCpCp() != 
                            null) {
                            souscriptionContratCompteForm.setCodePostalCpt(personneCpt.getPersonne().getAdresseProf().getCodCpCp());
                            // si le pays est la tunisie extraire le libelle du code postal
                            if ((personneCpt.getPersonne().getAdresseProf().getCodPaysPays() != 
                                 null) && 
                                (personneCpt.getPersonne().getAdresseProf().getCodPaysPays().equals(Constants.COD_PAYS_TUNISIE))) {
                                GetCodePostalCmd getCodePostalCmd = 
                                    new GetCodePostalCmd();
                                CodePostal codePostal = new CodePostal();
                                codePostal.setCodCpCp(Long.valueOf(personneCpt.getPersonne().getAdresseProf().getCodCpCp()));
                                codePostal = 
                                        (CodePostal)getCodePostalCmd.execute(codePostal);
                                souscriptionContratCompteForm.setLibCodePostalCpt(codePostal.getLibCpCp());
                                // gouvernerat
                                souscriptionContratCompteForm.setCodGouvGouvCpt(codePostal.getGouvernorat().getCodGouvGouv().toString());
                                souscriptionContratCompteForm.setLibGouvGouvCpt(codePostal.getGouvernorat().getLibGouvGouv());

                            }
                        }
                        //--------------------------------------------------------------------------------
                    }
                    /* nationalite */
                    if (personneCpt.getPersonne().getPaysByCodNat1Pays() != 
                        null) {
                        GetPaysCmd getPaysCmd = new GetPaysCmd();
                        Pays pays = new Pays();
                        pays.setCodPaysPays(personneCpt.getPersonne().getPaysByCodNat1Pays().getCodPaysPays());
                        pays = (Pays)getPaysCmd.execute(pays);
                        souscriptionContratCompteForm.setCodNationalitePrd(pays.getCodPaysPays());
                        souscriptionContratCompteForm.setNationalitePrd(pays.getLibNatPays());
                    }

                    if(!souscriptionContratCompteForm.getTypePersonneId().equals(Constants.ENTCOTITULAIRE)) {
                      souscriptionContratCompteForm.setResidentPrd(personneCpt.getPersonne().getBoolResPers().toString());
                      souscriptionContratCompteForm.setFormeJuridiquePrd(personneCpt.getPersonne().getFormeJuridique().getCodFjFj());
                    }else{
                        souscriptionContratCompteForm.setNomId(personneCpt.getPersonne().getNomNomPers());
                    }
                    souscriptionContratCompteForm.setNumSeqPers(personneCpt.getPersonne().getNumSeqPers().toString());
                    souscriptionContratCompteForm.setCategoriePersonneId(personneCpt.getPersonne().getCategoriePersonne().getCodCatpCatp());
                    
                    souscriptionContratCompteForm.setAlert("personneExistante");
                    
                    if (personneCpt.getClient() != null) {
                        souscriptionContratCompteForm.setCltTrouve(personneCpt.getClient());
                        souscriptionContratCompteForm.setListeContrats(personneCpt.getListeContratCpt());
                      if(souscriptionContratCompteForm.getTypePersonneMenu().equals("personnel")){
                       Long nbrComptePresonnel = Long.valueOf(0);
                       if(souscriptionContratCompteForm.getAlertProduit().equals("matriculeExistante")){
                       if(personneCpt.getListeContratCpt().size()>0){
                        for (Iterator it = personneCpt.getListeContratCpt().iterator() ; it.hasNext(); ) {
                              ContratCpt cpt = (ContratCpt)it.next();
                              if((cpt.getContratCptId().getCodPrdPrd().equals(Long.valueOf("103"))) && (cpt.getCodEtatCcpt().equals(("V")))
                                  && (cpt.getContratCptId().getCodStrcStrc().equals(Long.valueOf(souscriptionContratCompteForm.getCodStrcStrc())))){
                                  souscriptionContratCompteForm.setAlertProduit("comptePersonnelExistant");                              
                                  break;
                              }else if((cpt.getContratCptId().getCodPrdPrd().equals(Long.valueOf("103"))) && (cpt.getCodEtatCcpt().equals(("V")))){
                                 // verifier le nombre de compte 103 valides dans la banque... si > 2 alors break...
                                  nbrComptePresonnel = nbrComptePresonnel + 1;
                                  if(nbrComptePresonnel >= Long.valueOf(2)){
                                      souscriptionContratCompteForm.setAlertProduit("nombreComptePersonnel");
                                      break;
                                  }
                              }else  souscriptionContratCompteForm.setAlertProduit("comptePersonnelInexistant");            
                        } 
                       }else souscriptionContratCompteForm.setAlertProduit("comptePersonnelInexistant"); 
                       }
                      }
                        if (personneCpt.getClient().getNumFiscClt() != null)
                            souscriptionContratCompteForm.setNumFiscaleCpt(personneCpt.getClient().getNumFiscClt());
                        if (personneCpt.getClient().getCodDoanClt() != null)
                            souscriptionContratCompteForm.setCodeDouaneCpt(personneCpt.getClient().getCodDoanClt());
                        if (personneCpt.getClient().getNumBctClt() != null)
                            souscriptionContratCompteForm.setNumBctCpt(personneCpt.getClient().getNumBctClt());
                        if (personneCpt.getClient().getDatRelClt() != null)
                            souscriptionContratCompteForm.setDateRelationCpt(DateHandler.dateToStr(personneCpt.getClient().getDatRelClt()));

                    }else{
                        if(souscriptionContratCompteForm.getTypePersonneMenu().equals("personnel")){
                            if(souscriptionContratCompteForm.getAlertProduit().equals("matriculeExistante")){
                              souscriptionContratCompteForm.setAlertProduit("comptePersonnelInexistant"); 
                            }  
                        }
                        
                    }
                    
                    if(personneCpt.getPersonne().getDatDecePers() != null  && personneCpt.getPersonne().getNumDecePers() != null){ 
                        souscriptionContratCompteForm.setAlert("personneDecede");                       
                    }

                } else {
                    souscriptionContratCompteForm.setNumSeqPers(null);
                    souscriptionContratCompteForm.setAlert("personneInexistante");
                }
                

                
            } else {
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

            String appel = "";
            if (souscriptionContratCompteForm.getTypePersonneMenu().equals("physique") || 
                souscriptionContratCompteForm.getTypePersonneMenu().equals("personnel")) {
                appel = "successPhysique";
            } else if (souscriptionContratCompteForm.getTypePersonneMenu().equals("morale")) {
                appel = "successMorale";
            } else if (souscriptionContratCompteForm.getTypePersonneMenu().equals("CptLie")) {
                appel = "successLie";
            }
            return mapping.findForward(appel);

        } catch (Exception e) {
            com.oxia.fwk.core.Error erreur = new com.oxia.fwk.core.Error();
            StringBuffer text = 
                new StringBuffer("La recherche de la personne pour la prise en charge du contrat compte a été interrompue, veuillez transmettre ce message à l'équipe informatique: ");
            text.append("Exception au niveau de l'agence: "); text.append(souscriptionContratCompteForm.getCodStrcStrc());
            text.append(". Exception : "); text.append(e.toString());
            logger.error(text.toString(),e);  
          //  logger.error("Exception : ",e);
            erreur.setCode("200");
            erreur.setDescription(text.toString());
            ActionMessage actionMessage = 
                new ActionMessage("exception.generique", text);
            actionMessages.add("Erreur ", actionMessage);
            this.saveMessages(request, actionMessages);
            return mapping.findForward("error");

        }

    }
    /**
     * @param mapping ActionMapping
     * @param form souscriptionContratCompteForm
     * @param request HttpServletRequest
     * @param response HttpServletResponse
     * @return Méthode pour la recherche des produits autorisés pour la personne choisit,
     * Les commandes utilisés sont: getProduitAutorisesCmd
     * @throws IOException
     * @throws ServletException
     * 
     */
    public ActionForward rechercherProduits(ActionMapping mapping, 
                                            ActionForm form, 
                                            HttpServletRequest request, 
                                            HttpServletResponse response) throws IOException, 
                                                                                 ServletException {

        ActionMessages actionMessages = new ActionMessages();
        SouscriptionContratCompteForm souscriptionContratCompteForm = 
            (SouscriptionContratCompteForm)form;

        try {

            GetProduitAutorisesCmd getProduitAutorisesCmd = 
                new GetProduitAutorisesCmd();
            //instancier le VO input  ( ParamPers)
            ParamPers paramPers = new ParamPers();
            if (souscriptionContratCompteForm.getNumSeqPers() == null) {
                paramPers.setNumSeqPers(null);
            } else {
                paramPers.setNumSeqPers(new Integer(souscriptionContratCompteForm.getNumSeqPers()));
            }
            paramPers.setBoolResPers(new Integer(souscriptionContratCompteForm.getResidentPrd()));
            paramPers.setCodCatpCatp(souscriptionContratCompteForm.getCategoriePersonneId());
            paramPers.setCodFjFj(souscriptionContratCompteForm.getFormeJuridiquePrd());
            paramPers.setCodPaysPays(souscriptionContratCompteForm.getCodNationalitePrd());
            if (souscriptionContratCompteForm.getAge() == null || 
                souscriptionContratCompteForm.getAge().equals("")) {
                souscriptionContratCompteForm.setAge("0");
            }
            paramPers.setAge(Integer.valueOf(souscriptionContratCompteForm.getAge()));

            if (souscriptionContratCompteForm.getTypePersonneMenu().equals("physique") || 
                souscriptionContratCompteForm.getTypePersonneMenu().equals("morale")) {
                paramPers.setCas("");
            } else if (souscriptionContratCompteForm.getTypePersonneMenu().equals("personnel")) {
                paramPers.setCas("personnel");
            }
            // instanceier le VO output ( listes)
            Listes listePrd = new Listes();
            listePrd = (Listes)getProduitAutorisesCmd.execute(paramPers);
            if (!listePrd.hasError()) {
                if (listePrd != null) {
                    souscriptionContratCompteForm.setListeProduits(listePrd.getList());
                    souscriptionContratCompteForm.setAlertProduit("listeRemplie");
                } else {
                    souscriptionContratCompteForm.setAlertProduit("listeVide");
                }
            } else {
                List listErreur = listePrd.getErrors();
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

            String appel = "";
            if (souscriptionContratCompteForm.getTypePersonneMenu().equals("physique") || 
                souscriptionContratCompteForm.getTypePersonneMenu().equals("personnel")) {
                appel = "successPhysique";
            } else if (souscriptionContratCompteForm.getTypePersonneMenu().equals("morale")) {
                appel = "successMorale";
            } else if (souscriptionContratCompteForm.getTypePersonneMenu().equals("CptLie")) {
                appel = "successLie";
            }
            return mapping.findForward(appel);
        } catch (Exception e) {
            com.oxia.fwk.core.Error erreur = new com.oxia.fwk.core.Error();
            StringBuffer text = 
                new StringBuffer("La recherche des produits comptes pour la prise en charge du contrat a été interrompue, veuillez transmettre ce message à l'équipe informatique: ");
            text.append("Exception au niveau de l'agence: "); text.append(souscriptionContratCompteForm.getCodStrcStrc());
            text.append(". Exception : "); text.append(e.toString());
            erreur.setCode("200");
            erreur.setDescription(text.toString());
            logger.error(text.toString(),e);  
            ActionMessage actionMessage = 
                new ActionMessage("exception.generique", 
                                  erreur.getDescription());
            actionMessages.add("Erreur ", actionMessage);
            this.saveMessages(request, actionMessages);
            return mapping.findForward("error");

        }

    }

    public ActionForward clearPage(ActionMapping mapping, ActionForm form, 
                                   HttpServletRequest request, 
                                   HttpServletResponse response) throws IOException, 
                                                                        ServletException {
        try {
            SouscriptionContratCompteForm souscriptionContratCompteForm = 
                (SouscriptionContratCompteForm)form;

            souscriptionContratCompteForm.clearForm();
            String appel = "";
            if (souscriptionContratCompteForm.getTypePersonneMenu().equals("physique") || 
                souscriptionContratCompteForm.getTypePersonneMenu().equals("personnel")) {
                appel = "successPhysique";
            } else if (souscriptionContratCompteForm.getTypePersonneMenu().equals("morale")) {
                appel = "successMorale";
            } else if (souscriptionContratCompteForm.getTypePersonneMenu().equals("cotitulaire")) {
                appel = "successCotit";
            } else if (souscriptionContratCompteForm.getTypePersonneMenu().equals("CptLie")) {
                appel = "successLie";
            }
            return mapping.findForward(appel);
        } catch (Exception e) {
            com.oxia.fwk.core.Error erreur = new com.oxia.fwk.core.Error();
            StringBuffer text = 
                new StringBuffer("La page n'a pas été réinitialisé,veuillez transmettre ce message à l'équipe informatique: ");
            text.append(e.toString());
            erreur.setDescription(text.toString());
            logger.error(text.toString(),e);
            return mapping.findForward("error");
        }
    }
    /**
     * @param form souscriptionContratCompteForm
     * @return Personne -- Méthode pour la création d'une personne, elle n'appel aucune 
     * commandes, les fonctions appelées sont: creerPersonnePhysique, creerPersonneMorale, creerPersonneCotitulaire
     * @throws RuntimeException
     */
    public Personne creerPersonne(ActionForm form) {
        SouscriptionContratCompteForm souscriptionContratCompteForm = 
            (SouscriptionContratCompteForm)form;
        Personne persCree = new Personne();
    try{
        if (souscriptionContratCompteForm.getTypePersonneId().equals(Constants.PERSPHYSIQUE)) {
            persCree = creerPersonnePhysique(souscriptionContratCompteForm);
        } else {
            if (souscriptionContratCompteForm.getTypePersonneId().equals(Constants.PERSMORALE)) {
                persCree = creerPersonneMorale(souscriptionContratCompteForm);
            } else {
                if (souscriptionContratCompteForm.getTypePersonneId().equals(Constants.ENTCOTITULAIRE)) {
                    persCree = 
                            creerPersonneCotitulaire(souscriptionContratCompteForm);
                }
            }
        }
        } catch (Exception e) {
           StringBuffer text = 
                new StringBuffer("Erreur au niveau de l'agence << ");
                text.append(souscriptionContratCompteForm.getCodeStructureCpt()); 
                text.append("Exception dans souscriptionContratCompteAction / Methode : creerPersonne:");
                text.append(e.toString());
                logger.error(text.toString(),e);
          throw new RuntimeException(e);               
        }   
        return persCree;

    }
    /**
     * @param form souscriptionContratCompteForm
     * @return Personne -- Méthode pour la création d'une personne physique et entre autre la création
     * de la categorie personne, type Piece, piece annexe, ...
     * @throws RuntimeException
     */
    public Personne creerPersonnePhysique(ActionForm form) {


        SouscriptionContratCompteForm souscriptionContratCompteForm = 
            (SouscriptionContratCompteForm)form;

        Personne personne = new Personne();
        // personne.settypePersonneClt;
        // categorie personnne
    try{
        CategoriePersonne categoriePersonne = new CategoriePersonne();
        categoriePersonne.setCodCatpCatp(souscriptionContratCompteForm.getCategoriePersonneId());
        // type pièce
        TypePiece typePiece = new TypePiece();
        typePiece.setCodTpceTpce(new Long(souscriptionContratCompteForm.getCodTypePieceId()));
        // pays naissance
        Pays paysNais = new Pays();
        paysNais.setCodPaysPays(souscriptionContratCompteForm.getCodPaysNaisClt());
        // nationalite
        Pays paysNat = new Pays();
        paysNat.setCodPaysPays(souscriptionContratCompteForm.getCodNationalitePrd());

        //Gouvernorat
        Gouvernorat gouvernorat = new Gouvernorat();
        //typePiece.setCodTpceTpce(new Long(souscriptionContratCompteForm.getCodTypePieceId()));
        gouvernorat.setCodGouvGouv(Long.valueOf(souscriptionContratCompteForm.getCodLieuDelivClt()));

        // Activite
        Activite activite = new Activite();
        ActiviteId activiteId = new ActiviteId();
        activiteId.setCodActAct(souscriptionContratCompteForm.getCodActiviteClt()); //souscriptionContratCompteForm.getCodActiviteClt());
        activiteId.setCodSactSact(Long.valueOf(souscriptionContratCompteForm.getCodSclasActiviteClt()));
        activiteId.setCodCactCact(souscriptionContratCompteForm.getCodClasActiviteClt());
        activite.setActiviteId(activiteId);

        //profession
        Profession profession = new Profession();
        ProfessionId professionId = new ProfessionId();
        professionId.setCodGproGpro(Long.valueOf(souscriptionContratCompteForm.getCodGroupProfClt()));
        professionId.setCodProfProf(Long.valueOf(souscriptionContratCompteForm.getCodProfClt())); //new Integer(souscriptionContratCompteForm.getCodProfClt()));
        profession.setProfessionId(professionId);

        //forme juridique
        FormeJuridique formeJuridique = new FormeJuridique();
        formeJuridique.setCodFjFj(souscriptionContratCompteForm.getFormeJuridiquePrd());

        //Adresse de résidence
        Adresse adresseResidence = new Adresse();
        adresseResidence.setImmeuble(souscriptionContratCompteForm.getImmeubleAdrResid());
        adresseResidence.setRue(souscriptionContratCompteForm.getRueAdrResid());
        adresseResidence.setCite(souscriptionContratCompteForm.getCiteAdrResid());
        adresseResidence.setVille(souscriptionContratCompteForm.getLibPostalAdrResid());
        adresseResidence.setCodPaysPays(souscriptionContratCompteForm.getCodPayAdrResid());
        adresseResidence.setCodCpCp(souscriptionContratCompteForm.getCodePostalAdrResid());

        //Adresse professionnelle
        Adresse adresseProfessionnelle = new Adresse();
        adresseProfessionnelle.setImmeuble(souscriptionContratCompteForm.getImmeubleAdrProf());
        adresseProfessionnelle.setRue(souscriptionContratCompteForm.getRueAdrProf());
        adresseProfessionnelle.setCite(souscriptionContratCompteForm.getCiteAdrProf());
        adresseProfessionnelle.setVille(souscriptionContratCompteForm.getLibPostalAdrProf());
        adresseProfessionnelle.setCodPaysPays(souscriptionContratCompteForm.getCodPayAdrProf());
        adresseProfessionnelle.setCodCpCp(souscriptionContratCompteForm.getCodePostalAdrProf());

        //Piece annexe si elle existe 
        Set listPieceAnn = new HashSet();
        if (!souscriptionContratCompteForm.getTypePieceAnnexe().equals("") && 
            !souscriptionContratCompteForm.getNumPieceAnnexe().equals("") && 
            !souscriptionContratCompteForm.getDateDellivPiann().equals("")) {
            PieceAnnexe pieceAnnexe = new PieceAnnexe();
            PieceAnnexeId pieceAnnexeId = new PieceAnnexeId();
            pieceAnnexeId.setCodTpceTpce(new Long(souscriptionContratCompteForm.getTypePieceAnnexe()));
            pieceAnnexeId.setNumPcePian(souscriptionContratCompteForm.getNumPieceAnnexe());
            pieceAnnexe.setPieceAnnexeId(pieceAnnexeId);
            pieceAnnexe.setDatDelvPian(DateHandler.strToDate(souscriptionContratCompteForm.getDateDellivPiann()));
            pieceAnnexe.setDatFvalPian(DateHandler.strToDate(souscriptionContratCompteForm.getDateFinPian()));
            listPieceAnn = new HashSet();
            listPieceAnn.add(pieceAnnexe);
            personne.setPieceAnnexes(listPieceAnn);
        }
        //#########################################################################################
        //Instantiation de la personne

        personne.setTypePiece(typePiece);
        personne.setNumPcePers(souscriptionContratCompteForm.getNumPieceId());
        personne.setDatDlvPers(DateHandler.strToDate(souscriptionContratCompteForm.getDateDelivClt()));
        personne.setCategoriePersonne(categoriePersonne);
        personne.setPaysByCodNaisPays(paysNais);
        personne.setPaysByCodNat1Pays(paysNat);
        personne.setActivite(activite);
        personne.setProfession(profession);
        personne.setNomNomPers(souscriptionContratCompteForm.getNomPersClt());
        personne.setNomPrnPers(souscriptionContratCompteForm.getPrenomPersClt());
        personne.setLibTitrPers(souscriptionContratCompteForm.getTitrePersClt());
        personne.setCodSexPers(souscriptionContratCompteForm.getSexeClt());
        personne.setDatNaisPers((DateHandler.strToDate(souscriptionContratCompteForm.getDateNaisClt())));
        personne.setLibNaisPers(souscriptionContratCompteForm.getLieuNaisClt());
        personne.setCodSitfPers(souscriptionContratCompteForm.getSitFamilialeClt());
        personne.setBoolResPers(Long.valueOf(souscriptionContratCompteForm.getResidentClt()));
        personne.setNomPrnpPers(souscriptionContratCompteForm.getNomPereClt());
        personne.setFormeJuridique(formeJuridique);
        personne.setAdresseResid(adresseResidence);
        personne.setAdresseProf(adresseProfessionnelle);
        personne.setGouvernorat(gouvernorat);
        personne.setNumTelPers(souscriptionContratCompteForm.getNumTelPers());
        personne.setNumFaxPers(souscriptionContratCompteForm.getNumFaxPers());

        } catch (Exception e) {
            StringBuffer text = 
                new StringBuffer("Exception dans souscriptionContratCompteAction / Methode : creerPersonnePhysique: ");
                text.append(e.toString());
            logger.error(text.toString(),e);  
               throw new RuntimeException(e);               
        }   
        return personne;
    }

    /**
     * @param form souscriptionContratCompteForm
     * @return Personne -- Méthode pour la création d'une personne physique en tant que tuteur
     * @throws RuntimeException
     */
    public Personne creerPersonnePhysiqueTuteur(ActionForm form) {


        SouscriptionContratCompteForm souscriptionContratCompteForm = 
            (SouscriptionContratCompteForm)form;

        Personne personneTuteur = new Personne();
        // personne.settypePersonneClt;
        // categorie personnne
    try{
        CategoriePersonne categoriePersonneTuteur = new CategoriePersonne();
        categoriePersonneTuteur.setCodCatpCatp(souscriptionContratCompteForm.getCategoriePersonneTuteur());
        // type pièce
        TypePiece typePieceTuteur = new TypePiece();
        typePieceTuteur.setCodTpceTpce(new Long(souscriptionContratCompteForm.getTypePieceTuteur()));
        // pays naissance
        Pays paysNaisTuteur = new Pays();
        paysNaisTuteur.setCodPaysPays(souscriptionContratCompteForm.getCodPaysNaisTuteur());
        // nationalite
        Pays paysNatTuteur = new Pays();
        paysNatTuteur.setCodPaysPays(souscriptionContratCompteForm.getCodNationaliteTuteur());

        //Gouvernorat
        Gouvernorat gouvernoratTuteur = new Gouvernorat();
        gouvernoratTuteur.setCodGouvGouv(new Long(souscriptionContratCompteForm.getCodLieuDelivTuteur()));

        // Activite
        Activite activiteTuteur = new Activite();
        ActiviteId activiteIdTuteur = new ActiviteId();
        activiteIdTuteur.setCodActAct(souscriptionContratCompteForm.getCodActiviteTuteur());
        activiteIdTuteur.setCodSactSact(Long.valueOf(souscriptionContratCompteForm.getCodSclasActiviteTuteur()));
        activiteIdTuteur.setCodCactCact(souscriptionContratCompteForm.getCodClasActiviteTuteur());
        activiteTuteur.setActiviteId(activiteIdTuteur);

        //profession
        Profession professionTuteur = new Profession();
        ProfessionId professionIdTuteur = new ProfessionId();
        professionIdTuteur.setCodGproGpro(Long.valueOf(souscriptionContratCompteForm.getCodGroupProfTuteur()));
        professionIdTuteur.setCodProfProf(Long.valueOf(souscriptionContratCompteForm.getCodProfTuteur()));
        professionTuteur.setProfessionId(professionIdTuteur);

        //forme juridique
        FormeJuridique formeJuridiqueTuteur = new FormeJuridique();
        formeJuridiqueTuteur.setCodFjFj(souscriptionContratCompteForm.getFormeJuridiqueTuteur());

        //Adresse de résidence
        Adresse adresseResidenceTuteur = new Adresse();
        adresseResidenceTuteur.setImmeuble(souscriptionContratCompteForm.getImmeubleAdrResidTuteur());
        adresseResidenceTuteur.setRue(souscriptionContratCompteForm.getRueAdrResidTuteur());
        adresseResidenceTuteur.setCite(souscriptionContratCompteForm.getCiteAdrResidTuteur());
        adresseResidenceTuteur.setVille(souscriptionContratCompteForm.getLibPostalAdrResidTuteur());
        adresseResidenceTuteur.setCodPaysPays(souscriptionContratCompteForm.getCodPayAdrResidTuteur());
        adresseResidenceTuteur.setCodCpCp(souscriptionContratCompteForm.getCodePostalAdrResidTuteur());

        //Adresse professionnelle
        Adresse adresseProfessionnelleTuteur = new Adresse();
        adresseProfessionnelleTuteur.setImmeuble(souscriptionContratCompteForm.getImmeubleAdrProfTuteur());
        adresseProfessionnelleTuteur.setRue(souscriptionContratCompteForm.getRueAdrProfTuteur());
        adresseProfessionnelleTuteur.setCite(souscriptionContratCompteForm.getCiteAdrProfTuteur());
        adresseProfessionnelleTuteur.setVille(souscriptionContratCompteForm.getLibPostalAdrProfTuteur());
        adresseProfessionnelleTuteur.setCodPaysPays(souscriptionContratCompteForm.getCodPayAdrProfTuteur());
        adresseProfessionnelleTuteur.setCodCpCp(souscriptionContratCompteForm.getCodePostalAdrProf());

        //Piece annexe si elle existe 
        Set listPieceAnnTuteur = new HashSet();
        if (!souscriptionContratCompteForm.getTypePieceAnnexeTuteur().equals("") && 
            !souscriptionContratCompteForm.getNumPieceAnnexeTuteur().equals("") && 
            !souscriptionContratCompteForm.getDateDellivPiannTuteur().equals("") && 
            !souscriptionContratCompteForm.getDateFinPianTuteur().equals("")) {
            PieceAnnexe pieceAnnexeTuteur = new PieceAnnexe();
            PieceAnnexeId pieceAnnexeIdTuteur = new PieceAnnexeId();
            pieceAnnexeIdTuteur.setCodTpceTpce(Long.valueOf(souscriptionContratCompteForm.getTypePieceAnnexeTuteur()));
            pieceAnnexeIdTuteur.setNumPcePian(souscriptionContratCompteForm.getNumPieceAnnexeTuteur());
            pieceAnnexeTuteur.setPieceAnnexeId(pieceAnnexeIdTuteur);
            pieceAnnexeTuteur.setDatDelvPian(DateHandler.strToDate(souscriptionContratCompteForm.getDateDellivPiannTuteur()));
            pieceAnnexeTuteur.setDatFvalPian(DateHandler.strToDate(souscriptionContratCompteForm.getDateFinPianTuteur()));
            //listPieceAnn = new HashSet();
            listPieceAnnTuteur.add(pieceAnnexeTuteur);
            personneTuteur.setPieceAnnexes(listPieceAnnTuteur);
        }
        //#########################################################################################
        //Instantiation de la personne

        personneTuteur.setTypePiece(typePieceTuteur);
        personneTuteur.setNumPcePers(souscriptionContratCompteForm.getNumPieceTuteur());
        personneTuteur.setDatDlvPers(DateHandler.strToDate(souscriptionContratCompteForm.getDateDelivTuteur()));
        personneTuteur.setCategoriePersonne(categoriePersonneTuteur);
        personneTuteur.setPaysByCodNaisPays(paysNaisTuteur);
        personneTuteur.setPaysByCodNat1Pays(paysNatTuteur);
        personneTuteur.setActivite(activiteTuteur);
        personneTuteur.setProfession(professionTuteur);
        personneTuteur.setNomNomPers(souscriptionContratCompteForm.getNomPersTuteur());
        personneTuteur.setNomPrnPers(souscriptionContratCompteForm.getPrenomPersTuteur());
        personneTuteur.setLibTitrPers(souscriptionContratCompteForm.getTitrePersTuteur());
        personneTuteur.setCodSexPers(souscriptionContratCompteForm.getSexeTuteur());
        personneTuteur.setDatNaisPers((DateHandler.strToDate(souscriptionContratCompteForm.getDateNaisTuteur())));
        personneTuteur.setLibNaisPers(souscriptionContratCompteForm.getLieuNaisTuteur());
        personneTuteur.setCodSitfPers(souscriptionContratCompteForm.getSitFamilialeTuteur());
        personneTuteur.setBoolResPers(Long.valueOf(souscriptionContratCompteForm.getResidentTuteur()));
        personneTuteur.setNomPrnpPers(souscriptionContratCompteForm.getNomPereTuteur());
        personneTuteur.setFormeJuridique(formeJuridiqueTuteur);
        personneTuteur.setAdresseResid(adresseResidenceTuteur);
        personneTuteur.setAdresseProf(adresseProfessionnelleTuteur);
        personneTuteur.setGouvernorat(gouvernoratTuteur);
        } catch (Exception e) {
               logger.error("Erreur au niveau de l'agence <<" +souscriptionContratCompteForm.getCodeStructureCpt() + ">>. Exception dans souscriptionContratCompteAction / Methode : creerPersonnePhysiqueTuteur:  ",e);  
               throw new RuntimeException(e);               
        }   
        return personneTuteur;


    }

    /**
     * @param form souscriptionContratCompteForm
     * @return Personne -- Méthode pour la création d'une personne morale
     * @throws RuntimeException
     */
    public Personne creerPersonneMorale(ActionForm form) {


        SouscriptionContratCompteForm souscriptionContratCompteForm = 
            (SouscriptionContratCompteForm)form;

        Personne personneMorale = new Personne();
        // personne.settypePersonneClt;
        // categorie personnne
       try{
        CategoriePersonne categoriePersonneMorale = new CategoriePersonne();
        categoriePersonneMorale.setCodCatpCatp(souscriptionContratCompteForm.getCategoriePersonneId());
        // type pièce
        TypePiece typePieceMorale = new TypePiece();
        typePieceMorale.setCodTpceTpce(Long.valueOf(souscriptionContratCompteForm.getCodTypePieceId()));

        // nationalite
        Pays paysNatMorale = new Pays();
        paysNatMorale.setCodPaysPays(souscriptionContratCompteForm.getCodNationalitePrd());

        //Gouvernorat / tribunal
        Gouvernorat gouvernoratMorale = new Gouvernorat();
        Tribunal tribunal = new Tribunal();
        if (souscriptionContratCompteForm.getTypeDelivMoral().equals("G")) {
            if(souscriptionContratCompteForm.getCodLieuDelivMoral()!= null)
              gouvernoratMorale.setCodGouvGouv(Long.valueOf(souscriptionContratCompteForm.getCodLieuDelivMoral()));
        } else {
            tribunal.setCodTribTrib(Long.valueOf(souscriptionContratCompteForm.getCodLieuDelivMoral()));
        }
        // Activite
        Activite activiteMorale = new Activite();
        ActiviteId activiteId = new ActiviteId();
        activiteId.setCodActAct(souscriptionContratCompteForm.getCodActiviteMoral()); //souscriptionContratCompteForm.getCodActiviteClt());
        activiteId.setCodSactSact(new Long(souscriptionContratCompteForm.getCodSclasActiviteMoral()));
        activiteId.setCodCactCact(souscriptionContratCompteForm.getCodClasActiviteMoral());
        activiteMorale.setActiviteId(activiteId);

        //forme juridique
        FormeJuridique formeJuridiqueMorale = new FormeJuridique();
        formeJuridiqueMorale.setCodFjFj(souscriptionContratCompteForm.getFormeJuridiquePrd());

        //Adresse professionnelle
        Adresse adresseProfessionnelleMorale = new Adresse();
        adresseProfessionnelleMorale.setImmeuble(souscriptionContratCompteForm.getImmeubleAdrResidMoral());
        adresseProfessionnelleMorale.setRue(souscriptionContratCompteForm.getRueAdrResidMoral());
        adresseProfessionnelleMorale.setCite(souscriptionContratCompteForm.getCiteAdrResidMoral());
        //adresseProfessionnelleMorale.setVille(souscriptionContratCompteForm.getVilleAdrResidMoral());
        adresseProfessionnelleMorale.setCodPaysPays(souscriptionContratCompteForm.getCodPayAdrResidMoral());
        adresseProfessionnelleMorale.setCodCpCp(souscriptionContratCompteForm.getCodePostalAdrResidMoral());


        //#########################################################################################
        //Instantiation de la personne morale

        personneMorale.setTypePiece(typePieceMorale);
        personneMorale.setNumPcePers(souscriptionContratCompteForm.getNumPieceId());
        personneMorale.setDatDlvPers(DateHandler.strToDate(souscriptionContratCompteForm.getDateDelivMoral()));
        personneMorale.setCategoriePersonne(categoriePersonneMorale);
        personneMorale.setPaysByCodNat1Pays(paysNatMorale);
        personneMorale.setActivite(activiteMorale);
        personneMorale.setFormeJuridique(formeJuridiqueMorale);
        personneMorale.setAdresseProf(adresseProfessionnelleMorale);
        if (souscriptionContratCompteForm.getTypeDelivMoral().equals("G")) {
            personneMorale.setGouvernorat(gouvernoratMorale);
        } else {
            personneMorale.setTribunal(tribunal);
        }
        personneMorale.setNomRsPers(souscriptionContratCompteForm.getRaisonSocialMoral());
        personneMorale.setLibSiglPers(souscriptionContratCompteForm.getSigleMoral());
        personneMorale.setCodSectPers(souscriptionContratCompteForm.getSecteurActMoral());
        personneMorale.setBoolResPers(new Long(souscriptionContratCompteForm.getResidenceMoral()));
        //Informations complémentaires  
        personneMorale.setDateExpPers(DateHandler.strToDate(souscriptionContratCompteForm.getDateActMoral()));
        personneMorale.setDatDecrPers(DateHandler.strToDate(souscriptionContratCompteForm.getDatDecretMoral()));
        personneMorale.setDatJortPers(DateHandler.strToDate(souscriptionContratCompteForm.getDatJortMoral()));
        personneMorale.setDatPmePers(DateHandler.strToDate(souscriptionContratCompteForm.getDateCreationMoral()));
        personneMorale.setNumJortPers(souscriptionContratCompteForm.getNumJortMoral());
        personneMorale.setNumDecrPers(souscriptionContratCompteForm.getNumDecretMoral());
        personneMorale.setNumLpmePers(souscriptionContratCompteForm.getNumLoiCreMoral());
        personneMorale.setNumTelPers(souscriptionContratCompteForm.getNumTelMoral());
        personneMorale.setNumFaxPers(souscriptionContratCompteForm.getNumFaxMoral());
        personneMorale.setAdrMailPers(souscriptionContratCompteForm.getAdrMailMoral());
        personneMorale.setAdrWebPers(souscriptionContratCompteForm.getAdrWebMoral());
        personneMorale.setAdrSwiftPers(souscriptionContratCompteForm.getAdrSwiftMoral());
        personneMorale.setAdrTlxPers(souscriptionContratCompteForm.getAdrTelexMoral());
       
        } catch (Exception e) {
               logger.error("Erreur au niveau de l'agence <<" +souscriptionContratCompteForm.getCodeStructureCpt() + ">>. Exception dans souscriptionContratCompteAction / Methode : creerPersonneMorale:  ",e);  
               throw new RuntimeException(e);               
        }   
        return personneMorale;


    }
    /**
     * @param form souscriptionContratCompteForm
     * @return Personne -- Méthode pour la création d'une personne cotitulaire
     * @throws RuntimeException
     */
    public Personne creerPersonneCotitulaire(ActionForm form) {


        SouscriptionContratCompteForm souscriptionContratCompteForm = 
            (SouscriptionContratCompteForm)form;

        Personne personneCotitulaire = new Personne();
        // categorie personnne
    try{
        CategoriePersonne categorieCotit = new CategoriePersonne();
        categorieCotit.setCodCatpCatp(souscriptionContratCompteForm.getCategoriePersonneId());
        // type pièce
        TypePiece typePieceCotit = new TypePiece();
        typePieceCotit.setCodTpceTpce(Long.valueOf(souscriptionContratCompteForm.getCodTypePieceId()));
        // nationalite
        Pays paysNatCotit = new Pays();
        paysNatCotit.setCodPaysPays(Constants.COD_PAYS_TUNISIE);
        // Activite
        Activite activiteCotit = new Activite();
        ActiviteId activiteId = new ActiviteId();
        activiteId.setCodActAct(souscriptionContratCompteForm.getCodActiviteClt()); //souscriptionContratCompteForm.getCodActiviteClt());
        activiteId.setCodSactSact(Long.valueOf(souscriptionContratCompteForm.getCodSclasActiviteClt()));
        activiteId.setCodCactCact(souscriptionContratCompteForm.getCodClasActiviteClt());
        activiteCotit.setActiviteId(activiteId);
       
        //Instanciation de la personne
        personneCotitulaire.setTypePiece(typePieceCotit);
        personneCotitulaire.setNumPcePers(souscriptionContratCompteForm.getNumPieceId());
        personneCotitulaire.setDatDlvPers(DateHandler.strToDate(souscriptionContratCompteForm.getDateRelationCpt()));
        personneCotitulaire.setCategoriePersonne(categorieCotit);
        personneCotitulaire.setPaysByCodNat1Pays(paysNatCotit);
        personneCotitulaire.setActivite(activiteCotit);
        if(souscriptionContratCompteForm.getNomCpt().length()>59){
          personneCotitulaire.setNomNomPers(souscriptionContratCompteForm.getNomCpt().substring(0,59));
        }else personneCotitulaire.setNomNomPers(souscriptionContratCompteForm.getNomCpt());
        
        personneCotitulaire.setNomPrnpPers(" ");
        
        } catch (Exception e) {
               logger.error("Erreur au niveau de l'agence <<" +souscriptionContratCompteForm.getCodeStructureCpt() + ">>. Exception dans souscriptionContratCompteAction / Methode : creerPersonneCotitulaire:  ",e);  
               throw new RuntimeException(e);               
        }   
        
        return personneCotitulaire;
        

    }
    /**
     * @param form souscriptionContratCompteForm
     * @return Client -- Méthode pour la création d'un client, les fonctions appelées sont: creerPersonne
     * @throws RuntimeException
     */
   public Client creerClient(ActionForm form) {
                  
            SouscriptionContratCompteForm souscriptionContratCompteForm = 
                (SouscriptionContratCompteForm)form;
            Client client = new Client();
          try{  
            
            //type personne
            TypePers typepersonne = new TypePers();
            typepersonne.setCodTperTper(souscriptionContratCompteForm.getTypePersonneId());
            //Structure structure = new Structure();
            //structure.setCodStrcStrc(Long.valueOf(souscriptionContratCompteForm.getCodeStructureCpt()));
            client.setTypePers(typepersonne);
            //client.setStructure(structure);
            client.setDatRelClt(DateHandler.strToDate(souscriptionContratCompteForm.getDateRelationCpt())); 
            //client.setNumBctClt(souscriptionContratCompteForm.getNumBctCpt());                           
            client.setCodDoanClt(souscriptionContratCompteForm.getCodeDouaneCpt());
            client.setCodEtatClt(Constants.COD_ETAT_CLT_ATT);
            client.setNumFiscClt(souscriptionContratCompteForm.getNumFiscClt());
            //client.setNumRnePers(souscriptionContratCompteForm.getNumRnePers());
            if (souscriptionContratCompteForm.getAlert().equalsIgnoreCase("personneInexistante")) {
                client.setBoolDeclClt(Long.valueOf(0));
                client.setPersonne(creerPersonne(souscriptionContratCompteForm));

            } else {
                Personne personne = new Personne();
                personne.setNumSeqPers(Long.valueOf(souscriptionContratCompteForm.getNumSeqPers()));
                personne.setNumPcePers(souscriptionContratCompteForm.getNumPieceId());
                TypePiece typePiece = new TypePiece();
                typePiece.setCodTpceTpce(Long.valueOf(souscriptionContratCompteForm.getCodTypePieceId()));
                personne.setTypePiece(typePiece);
                CategoriePersonne categoriePersonne = new CategoriePersonne();
                categoriePersonne.setCodCatpCatp(souscriptionContratCompteForm.getCategoriePersonneId());
                personne.setCategoriePersonne(categoriePersonne);
                if (souscriptionContratCompteForm.getTypePersonneId().equals(Constants.PERSPHYSIQUE)) {
                    personne.setNomNomPers(souscriptionContratCompteForm.getNomCpt());
                    personne.setNomPrnPers(souscriptionContratCompteForm.getPrenomCpt());
                } else if (souscriptionContratCompteForm.getTypePersonneId().equals(Constants.PERSMORALE)) {
                    personne.setNomRsPers(souscriptionContratCompteForm.getNomCpt());
                    personne.setLibSiglPers(souscriptionContratCompteForm.getPrenomCpt());
                }
                
                if(souscriptionContratCompteForm.getCltTrouve()==null){
                  // cas où la personne existe mais le client n'existe pas
                  client.setNumSeqPers(Long.valueOf(souscriptionContratCompteForm.getNumSeqPers()));
                  client.setBoolDeclClt(Long.valueOf(0));
                  client.setPersonne(personne);  
                }else{
                    client = souscriptionContratCompteForm.getCltTrouve();
                    if(!souscriptionContratCompteForm.getNumFiscClt().equals("")){
                      client.setNumFiscClt(souscriptionContratCompteForm.getNumFiscClt());
                    }                   
                }
                
            }
        } catch (Exception e) {
               logger.error("Erreur au niveau de l'agence <<" +souscriptionContratCompteForm.getCodeStructureCpt() + ">>. Exception dans souscriptionContratCompteAction / Methode : creerClient:  ",e);  
               throw new RuntimeException(e);               
        }   
            return client;
  
    }

    /**
     * @param form souscriptionContratCompteForm
     * @return ParamInsertContrat -- Méthode pour la création d'un contrat compte,
     * les fonctions appelées sont: creerClient, creerPersonnePhysiqueTuteur
     * @throws RuntimeException
     */
    public ParamInsertContrat creerContrat(ActionForm form, ParamInsertContrat paramInsertContrat) {


        SouscriptionContratCompteForm souscriptionContratCompteForm = 
            (SouscriptionContratCompteForm)form;
      
      try{
        ContratCpt contrat = new ContratCpt();
        ContratCptId contratCptId = new ContratCptId();
        contratCptId.setCodPrdPrd(Long.valueOf(souscriptionContratCompteForm.getCodePrdCpt()));
        contratCptId.setCodStrcStrc(Long.valueOf(souscriptionContratCompteForm.getCodeStructureCpt()));
        if(souscriptionContratCompteForm.getTypePersonneMenu().equals("personnel")){  
            contratCptId.setNumCcptCcpt(Long.valueOf(souscriptionContratCompteForm.getNumCompteCpt()));        
        }
        contrat.setContratCptId(contratCptId);
        contrat.setCodEtatCcpt(Constants.COD_ETAT_CPT_ATT);
        contrat.setDatOuvCcpt(DateHandler.strToDate(souscriptionContratCompteForm.getDateActuelle()));
        contrat.setMontSoldCcpt(Long.valueOf(0));
        contrat.setMontAutCcpt(Long.valueOf(0));
        contrat.setMontBlocCcpt(Long.valueOf(0));
        contrat.setMontSdevCcpt(Long.valueOf(0));
        contrat.setMontSminCcpt(Long.valueOf(0));
        contrat.setMontBdevCcpt(Long.valueOf(0));
        contrat.setNomIntiCcpt(souscriptionContratCompteForm.getIntituleCompteCpt());
        contrat.setCodPerCpt(souscriptionContratCompteForm.getPeridiciteCpt());
        contrat.setCodFoncCpt(souscriptionContratCompteForm.getFonctionementCpt());
        contrat.setBoolRelvCpt(Long.valueOf(souscriptionContratCompteForm.getReleveCpt()));

        //adresse : 
        Adresse adresseCpt = new Adresse();
        adresseCpt.setImmeuble(souscriptionContratCompteForm.getImmeubleCpt());
        adresseCpt.setRue(souscriptionContratCompteForm.getRueCpt());
        adresseCpt.setCite(souscriptionContratCompteForm.getCiteCpt());
        adresseCpt.setVille(souscriptionContratCompteForm.getLibCodePostalCpt());
        adresseCpt.setCodPaysPays(souscriptionContratCompteForm.getCodPayCpt());
        adresseCpt.setCodCpCp(souscriptionContratCompteForm.getCodePostalCpt());
        contrat.setAdresseCorresp(adresseCpt);
        //--------------------------------------------------------------------------
        //devise
        Devise devise = new Devise();
        devise.setCodDevDev(Long.valueOf(souscriptionContratCompteForm.getCodeDeviseCpt()));
        contrat.setDevise(devise);
        contrat.setClient(creerClient(souscriptionContratCompteForm));
        
        Personne personneTuteur = new Personne();
        Categorie categorie = new Categorie();
        CategorieId categorieId = new CategorieId();
        Regime regime = new Regime();
        RegimeId regimeId = new RegimeId();

        if (souscriptionContratCompteForm.getCategoriePersonneId().equals(Constants.COD_CATEGORIE_MINEUR)) {
            // traitement du cas mineur.  ==> creation du tuteur                 
            if (souscriptionContratCompteForm.getAlertTuteur().equals("TuteurInexistant")) {
                personneTuteur = 
                        creerPersonnePhysiqueTuteur(souscriptionContratCompteForm);
            } else {
                personneTuteur.setNumSeqPers(Long.valueOf(souscriptionContratCompteForm.getNumSeqTuteur()));
                personneTuteur.setNumPcePers(souscriptionContratCompteForm.getNumPieceTuteur());
                TypePiece typePieceTuteur = new TypePiece();
                typePieceTuteur.setCodTpceTpce(Long.valueOf(souscriptionContratCompteForm.getTypePieceTuteur()));
                personneTuteur.setTypePiece(typePieceTuteur);
                personneTuteur.setNomNomPers(souscriptionContratCompteForm.getNomTuteur());
                personneTuteur.setNomPrnPers(souscriptionContratCompteForm.getPrenomTuteur());
            }
            paramInsertContrat.setPersonneTuteur(personneTuteur);
        }

        if (souscriptionContratCompteForm.getTypePersonneId().equals(Constants.ENTCOTITULAIRE)) {
            // traitement du cas cotitulaire
            if (souscriptionContratCompteForm.getAlert().equalsIgnoreCase("personneInexistante")) {
                ArrayList listCotit = new ArrayList();
                listCotit.clear();
                Collection dgAdd = 
                    souscriptionContratCompteForm.getListPersonneCotitGrid().getDataWithState("");
                Collection dgSel = 
                    souscriptionContratCompteForm.getListPersonneCotitGrid().getDataWithState("selected");
                Collection listMembresCotit = new ArrayList();
                listMembresCotit.addAll(dgAdd);
                listMembresCotit.addAll(dgSel);
                for (Iterator it = listMembresCotit.iterator(); it.hasNext(); 
                ) {
                    CoTitulaire coTitulaire = new CoTitulaire();
                    Mandataire mandataire = (Mandataire)it.next();
                    if (mandataire.getNumPcePers() != "" && 
                        mandataire.getNumPcePers() != null && 
                        mandataire.getNumSeqPers() != 
                        null) { // verifier si les données obligatoires sont saisies

                        Personne membreCotit = new Personne();
                        TypePiece typePieceMembre = new TypePiece();
                        CoTitulaireId CoTitulaireId = new CoTitulaireId();
                        CoTitulaireId.setNumSeqPers(mandataire.getNumSeqPers());
                        //CoTitulaireId.setNumSeqCli(contrat.getClient().getNumSeqPers);
                        coTitulaire.setCodSigCoti(souscriptionContratCompteForm.getTypeSignature());
                        coTitulaire.setCodTcotCoti(souscriptionContratCompteForm.getTypeCotit());
                        coTitulaire.setCoTitulaireId(CoTitulaireId);
                        typePieceMembre.setCodTpceTpce(mandataire.getCodTpceTpce());
                        membreCotit.setNumSeqPers(mandataire.getNumSeqPers());
                        membreCotit.setNumPcePers(mandataire.getNumPcePers());
                        membreCotit.setTypePiece(typePieceMembre);
                        coTitulaire.setPersonne(membreCotit);
                        if (mandataire.getNumSeqPers() != null && 
                            !mandataire.getNumSeqPers().equals(Long.valueOf(0))) {
                            listCotit.add(coTitulaire);
                        }
                    }
                }
                paramInsertContrat.setListCotitulaire(listCotit);
            }
        }
        // traitement des cas des produits epargnes : groupe famille = '03' et sous famille = '02'
        if (souscriptionContratCompteForm.getSousFamPrd().equals(Constants.COD_SFAM_PRD) && 
            souscriptionContratCompteForm.getGroupePrd().equals(Constants.COD_GFAM_PRD)) {
            categorieId.setCodPrdPrd(Long.valueOf(souscriptionContratCompteForm.getCodePrdCpt()));
            categorieId.setCodCatCat(souscriptionContratCompteForm.getCodeCategorieEpargne());
            categorieId.setCodRgmRgm(Long.valueOf(souscriptionContratCompteForm.getCodeRegimeEpargne()));

            regimeId.setCodPrdPrd(Long.valueOf(souscriptionContratCompteForm.getCodePrdCpt()));
            regimeId.setCodRgmRgm(Long.valueOf(souscriptionContratCompteForm.getCodeRegimeEpargne()));
            regime.setRegimeId(regimeId);

            categorie.setCategorieId(categorieId);
            categorie.setRegime(regime);
            paramInsertContrat.setCategorie(categorie);
            paramInsertContrat.setTypeVersementEpargne(souscriptionContratCompteForm.getTypeVers());
            
            String catEp = "";
            if(contrat.getContratCptId().getCodPrdPrd().equals(Constants.COD_PRD_PRD_PEE)){
                catEp = StrHandler.lpad(categorieId.getCodRgmRgm().toString(),'0',2) + categorieId.getCodCatCat();                
            }else if(contrat.getContratCptId().getCodPrdPrd().equals(Constants.COD_PRD_PRD_PEM)){
                      catEp = StrHandler.lpad(categorieId.getCodCatCat(),' ',3);
                  }else if(contrat.getContratCptId().getCodPrdPrd().equals(Constants.COD_PRD_PRD_PEL)){
                           catEp = StrHandler.lpad(categorieId.getCodCatCat(),' ',2) + categorieId.getCodRgmRgm().toString();
                        }
            
            contrat.setCatCcptCcpt(catEp);
        }
        contrat.setNumLivrCcpt(souscriptionContratCompteForm.getNumLivretEpargne());

        paramInsertContrat.setContratCpt(contrat);

        // instancier le motif etat du contrat
        MotifEtatId motifEtatId = new MotifEtatId();
        motifEtatId.setCodEtatEcon(Constants.COD_ETAT_CPT_ATT);
        motifEtatId.setCodMotfMeta(Long.valueOf(0));
        MotifEtat motifEtat = new MotifEtat();
        motifEtat.setMotifEtatId(motifEtatId);
        paramInsertContrat.setMotifEtat(motifEtat);
        } catch (Exception e) {
               logger.error("Erreur au niveau de l'agence <<" +souscriptionContratCompteForm.getCodeStructureCpt() + ">>. Exception dans souscriptionContratCompteAction / Methode : creerContrat:  ",e);  
               throw new RuntimeException(e);               
        }   
        return paramInsertContrat;


    }

    public ActionForward validerTransaction(ActionMapping mapping, 
                                            ActionForm form, 
                                            HttpServletRequest request, 
                                            HttpServletResponse response) throws IOException, 
                                                                                 ServletException {
        SouscriptionContratCompteForm souscriptionContratCompteForm = 
            (SouscriptionContratCompteForm)form;
        ParamInsertContrat paramInsertContrat = new ParamInsertContrat();
        ActionMessages actionMessages = new ActionMessages();
        ValueObject vo = new ValueObject();
        try {
            Personnel personnel = new Personnel();
            personnel.setNumMatrUser(souscriptionContratCompteForm.getNumMatriculeUser());     
            paramInsertContrat.setPersonnel(personnel);  
            /*souscriptionContratCompteForm.getNumLivretEpargne()*/
             if(souscriptionContratCompteForm.getNumLivretEpargne()!= null && !souscriptionContratCompteForm.getNumLivretEpargne().equals("") ){
                 Context context = ContextHandler.getContext(); 
                 PersonneDAO  personneDAO = (PersonneDAO)context.getBean("personneDAO");     
                 if(personneDAO.verifExistLivretEpargne(souscriptionContratCompteForm.getNumLivretEpargne())){
                     souscriptionContratCompteForm.setAlertLivret("LivretExistant");
                     return mapping.findForward("successPhysique");
                 }                 
             }
            
            if (souscriptionContratCompteForm.getCategoriePersonneId().equals(Constants.COD_CATEGORIE_MINEUR) || 
                souscriptionContratCompteForm.getCategoriePersonneId().equals(Constants.COD_CATEGORIE_P_TUN_INC) || 
                souscriptionContratCompteForm.getCategoriePersonneId().equals(Constants.COD_CATEGORIE_P_ETR_INC)) {
                // traitement des cas des mandataires
                paramInsertContrat = 
                        creerContrat(souscriptionContratCompteForm,paramInsertContrat);
                request.getSession().setAttribute("paramSouscriptionMandat", 
                                                  paramInsertContrat);
                souscriptionContratCompteForm.setParamInsertContrat(paramInsertContrat);
                return mapping.findForward("mandat");
            } else {
                if (souscriptionContratCompteForm.getTypePersonneId().equals(Constants.PERSMORALE)) {
                    if (souscriptionContratCompteForm.getCreerMandat().equals("true")) {
                        // creer mandat pour personne morale
                        paramInsertContrat = 
                                creerContrat(souscriptionContratCompteForm,paramInsertContrat);
                        request.getSession().setAttribute("paramSouscriptionMandat", 
                                                          paramInsertContrat);
                        souscriptionContratCompteForm.setParamInsertContrat(paramInsertContrat);
                        return mapping.findForward("mandat");
                    } else { //return mapping.findForward("confirmationModification"); {
                        // ne pas creer de mandat pour personne morale
                        InsertClientContratCmd insertClientContratCmd = new InsertClientContratCmd();
                        vo = (ValueObject)insertClientContratCmd.execute(creerContrat(souscriptionContratCompteForm,paramInsertContrat));
                        
                        if (!vo.hasError()) {
                            ContratCpt contratCpt = (ContratCpt)vo;
                            String message = 
                                "La demande de souscription numéro  " +  
                                
                                StrHandler.lpad(contratCpt.getContratCptId().getNumCcptCcpt().toString(), 
                                                '0', 6) + " au nom de " + 
                                souscriptionContratCompteForm.getNomCpt() + " " + 
                                souscriptionContratCompteForm.getPrenomCpt() + 
                                " a été crée avec succès et en attente de validation par le chef d'agence.";
                            souscriptionContratCompteForm.setLibelleConfirmation(message);
                            return mapping.findForward("confirmationCreationContrat");
                        } else {
                            List listErreur = vo.getErrors();
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
                } else {
                    // traitement des cas de creation qui ne nécessitent pas des manadats                     
                     
                      //if(!souscriptionContratCompteForm.getTypePersonneMenu().equals("personnel")){                             
                          InsertClientContratCmd insertClientContratCmd = new InsertClientContratCmd();
                          vo = (ValueObject)insertClientContratCmd.execute(creerContrat(souscriptionContratCompteForm,paramInsertContrat));                         
                          
                     /* }else{
                          InsertComptePersonnelBnaCmd insertComptePersonnelBnaCmd = new InsertComptePersonnelBnaCmd();
                           vo = insertComptePersonnelBnaCmd.execute(creerContrat(souscriptionContratCompteForm,paramInsertContrat));
                      }*/
                    
                    if (!vo.hasError()) {
                        ContratCpt contratCpt = (ContratCpt)vo;
                        String message = 
                            "La demande de souscription numéro  " +
                            StrHandler.lpad(contratCpt.getContratCptId().getNumCcptCcpt().toString(), 
                                            '0', 6) + " au nom de " + 
                            souscriptionContratCompteForm.getNomCpt() + " " + 
                            souscriptionContratCompteForm.getPrenomCpt() + 
                            " a été crée avec succès et en attente de validation par le chef d'agence.";
                        souscriptionContratCompteForm.setLibelleConfirmation(message);
                        return mapping.findForward("confirmationCreationContrat");
                    } else {
                        List listErreur = vo.getErrors();
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
                }
            }
        } catch (Exception e) {
            com.oxia.fwk.core.Error erreur = new com.oxia.fwk.core.Error();
            StringBuffer text = 
                new StringBuffer("la transaction est Interrompu, une erreur dans SouscriptionContratCompteAction / Dispatch Action :validerTransaction ");
            text.append(e.toString());
            erreur.setCode("200");
            erreur.setDescription(text.toString());
            logger.error("Erreur au niveau de l'agence <<" +souscriptionContratCompteForm.getCodStrcStrc() + ">>. Exception : ",e);  
        //    logger.error("Exception : ",e);  
            ActionMessage actionMessage = 
                new ActionMessage("exception.generique", 
                                  erreur.getDescription());
            actionMessages.add("Erreur ", actionMessage);
            this.saveMessages(request, actionMessages);
            return mapping.findForward("error");
        }
    }


    public ActionForward validerTransactionCompteLie(ActionMapping mapping, 
                                                     ActionForm form, 
                                                     HttpServletRequest request, 
                                                     HttpServletResponse response) throws IOException, 
                                                                                          ServletException {
        SouscriptionContratCompteForm souscriptionContratCompteForm = 
            (SouscriptionContratCompteForm)form;

        
        ParamCompteLie paramCompteLie = new ParamCompteLie();
        ActionMessages actionMessages = new ActionMessages();
        String message = "";
        try {
        
            Personnel personnel = new Personnel();
            personnel.setNumMatrUser(souscriptionContratCompteForm.getNumMatriculeUser());     
            paramCompteLie.setPersonnel(personnel);  
            
            if (souscriptionContratCompteForm.getContratDav() != null) {
                InsertCompteLieCmd insertCompteLieCmd = new InsertCompteLieCmd();
                paramCompteLie.setContratCpt(souscriptionContratCompteForm.getContratDav());
                paramCompteLie.setCodeProduit(Long.valueOf(souscriptionContratCompteForm.getCodPrdVert()));
                if(souscriptionContratCompteForm.getSoldeMintDav() != null && !souscriptionContratCompteForm.getSoldeMintDav().equals(""))
                   paramCompteLie.setMontantSoldeMinimum(new Long(new Double(new Double(StrHandler.strWithoutBlanck(souscriptionContratCompteForm.getSoldeMintDav())).doubleValue() * 
                                                                 1000).longValue()));
                
                ValueObject vo = (ValueObject)insertCompteLieCmd.execute(paramCompteLie);
                
                if (!vo.hasError()) {
                    ContratCpt contratLieCree = (ContratCpt)vo; 
                    message = 
                            "La demande de souscription numéro  " +
                            StrHandler.lpad(contratLieCree.getContratCptId().getNumCcptCcpt().toString(), 
                                            '0', 6) + " au nom de " + 
                            souscriptionContratCompteForm.getNomId() + " " + 
                            souscriptionContratCompteForm.getPrenomId() + 
                            " a été crée avec succès et en attente de validation par le chef d'agence.";
                } else {
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
                }
            }
            souscriptionContratCompteForm.setLibelleConfirmation(message);
            return mapping.findForward("confirmationCreationContrat");
        } catch (Exception e) {
            com.oxia.fwk.core.Error erreur = new com.oxia.fwk.core.Error();
            StringBuffer text = 
                new StringBuffer("la transaction est Interrompu, une erreur dans SouscriptionContratCompteAction / Dispatch Action :validerTransactionCompteLie ");
            text.append(e.toString());
            erreur.setCode("200");
            erreur.setDescription(text.toString());
            logger.error("Erreur au niveau de l'agence <<" +souscriptionContratCompteForm.getCodStrcStrc() + ">>. Exception : ",e);  
       //     logger.error("Exception : ",e);  
            ActionMessage actionMessage = 
                new ActionMessage("exception.generique", 
                                  erreur.getDescription());
            actionMessages.add("Erreur ", actionMessage);
            this.saveMessages(request, actionMessages);
            return mapping.findForward("error");
        }

    }


    public ActionForward recherchePieceAnnexe(ActionMapping mapping, 
                                              ActionForm form, 
                                              HttpServletRequest request, 
                                              HttpServletResponse response) throws IOException, 
                                                                                   ServletException {

        SouscriptionContratCompteForm souscriptionContratCompteForm = 
            (SouscriptionContratCompteForm)form;
        ActionMessages actionMessages = new ActionMessages();
        try {

            GetPieceAnnexeCmd getPieceAnnexeCmd = new GetPieceAnnexeCmd();

            PersonneStrc personneStrc = new PersonneStrc(); //Vo input 
            personneStrc.setCodTpceTpce(Long.valueOf(souscriptionContratCompteForm.getTypePieceAnnEtr()));
            personneStrc.setNumPcePers(souscriptionContratCompteForm.getNumPieceAnnEtr());
            PieceAnnexe pieceAnnexe = new PieceAnnexe(); //Vo output
            pieceAnnexe = (PieceAnnexe)getPieceAnnexeCmd.execute(personneStrc);
            if (!pieceAnnexe.hasError()) {
                if (pieceAnnexe.getPieceAnnexeId() != null) {
                    // personne trouvé à travers sa pièce annexe
                    souscriptionContratCompteForm.setTypePieceId(pieceAnnexe.getPersonne().getTypePiece().getLibSiglTpce());
                    souscriptionContratCompteForm.setCodTypePieceId(pieceAnnexe.getPersonne().getTypePiece().getCodTpceTpce().toString());
                    souscriptionContratCompteForm.setNumPieceId(pieceAnnexe.getPersonne().getNumPcePers());
                } else {
                    souscriptionContratCompteForm.setTypePieceId("NUM");
                    souscriptionContratCompteForm.setCodTypePieceId(Constants.COD_NUM_ORDRE.toString());
                    souscriptionContratCompteForm.setNumPieceId(null);
                }

                souscriptionContratCompteForm.setReqCode("rechercherPersonne");
                souscriptionContratCompteForm.setChoixInitPage("");
                rechercherPersonne(mapping, form, request, response);
                souscriptionContratCompteForm.setTypePieceAnnexe(souscriptionContratCompteForm.getTypePieceAnnEtr());
                souscriptionContratCompteForm.setNumPieceAnnexe(souscriptionContratCompteForm.getNumPieceAnnEtr());
            } else {
                List listErreur = pieceAnnexe.getErrors();
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
            String appel = "";
            if (souscriptionContratCompteForm.getTypePersonneMenu().equals("physique") || 
                souscriptionContratCompteForm.getTypePersonneMenu().equals("personnel")) {
                appel = "successPhysique";
            } else if (souscriptionContratCompteForm.getTypePersonneMenu().equals("cptLie")) {
                appel = "successLie";
            }
            return mapping.findForward(appel);
        } catch (Exception e) {
            com.oxia.fwk.core.Error erreur = new com.oxia.fwk.core.Error();
            StringBuffer text = 
                new StringBuffer("la transaction est Interrompu, une erreur dans SouscriptionContratCompteAction / Dispatch Action :recherchePieceAnnexe ");
            text.append(e.toString());
            erreur.setCode("200");
            erreur.setDescription(text.toString());
            logger.error("Erreur au niveau de l'agence <<" +souscriptionContratCompteForm.getCodStrcStrc() + ">>. Exception : ",e);  
         //   logger.error("Exception : ",e); 
            ActionMessage actionMessage = 
                new ActionMessage("exception.generique", 
                                  erreur.getDescription());
            actionMessages.add("Erreur ", actionMessage);
            this.saveMessages(request, actionMessages);
            return mapping.findForward("error");
        }
    }


    public ActionForward chercherParmEpargne(ActionMapping mapping, 
                                             ActionForm form, 
                                             HttpServletRequest request, 
                                             HttpServletResponse response) throws IOException, 
                                                                                  ServletException {
        ActionMessages actionMessages = new ActionMessages();
        SouscriptionContratCompteForm souscriptionContratCompteForm = 
            (SouscriptionContratCompteForm)form;

        try {
            ChargerRgmCatEpargneCmd chargerRgmCatEpargneCmd = 
                new ChargerRgmCatEpargneCmd();
            ParamEpargne paramEpargne = new ParamEpargne(); //Vo input

            paramEpargne.setCodPrdPrd(Long.valueOf(souscriptionContratCompteForm.getCodeProduitCpt()));
            paramEpargne.setCodRgmRgm(souscriptionContratCompteForm.getCodeRegimeEpargne());

            if (souscriptionContratCompteForm.getTypeRequest().equals("choixCategorie")) {
                paramEpargne.setCodCatCat(souscriptionContratCompteForm.getCodeCategorieEpargne());
            } else {
                paramEpargne.setCodCatCat("");
            }

            ListRgmCatEpargne listRgmCatEpargne = 
                new ListRgmCatEpargne(); //Vo output 

            listRgmCatEpargne = 
                    (ListRgmCatEpargne)chargerRgmCatEpargneCmd.execute(paramEpargne);

            if (!listRgmCatEpargne.hasError()) {
                if (souscriptionContratCompteForm.getTypeRequest().equals("ouverture")) {
                    souscriptionContratCompteForm.setListRegimeEpargne(null);
                    souscriptionContratCompteForm.setListRegimeEpargne(listRgmCatEpargne.getListRgmEpargne());
                }

                if (souscriptionContratCompteForm.getTypeRequest().equals("ouverture") || 
                    souscriptionContratCompteForm.getTypeRequest().equals("choixRegime")) {
                    souscriptionContratCompteForm.setListCategorieEpargne(null);
                    souscriptionContratCompteForm.setListCategorieEpargne(listRgmCatEpargne.getListCatEpargne());
                }

                if (listRgmCatEpargne.getCategorie() != null) {
                    souscriptionContratCompteForm.setChargEffectPramEp("true");
                    souscriptionContratCompteForm.setCodeCategorieEpargne(listRgmCatEpargne.getCategorie().getCategorieId().getCodCatCat());
                    souscriptionContratCompteForm.setMntVersementEpargne(listRgmCatEpargne.getCategorie().getMontVersCat().toString());
                    souscriptionContratCompteForm.setMntCapitaliseEpargne(listRgmCatEpargne.getCategorie().getMontCaptCat().toString());
                    if(souscriptionContratCompteForm.getCodeProduitCpt().equals("0" + Constants.COD_PRD_PRD_PEE.toString())){
                        // cas d'un produit epargne etude, afficher le montant de bourse
                         souscriptionContratCompteForm.setMntBourse(listRgmCatEpargne.getCategorie().getMontBrsCat().toString());
                    }else if(souscriptionContratCompteForm.getCodeProduitCpt().equals("0" +Constants.COD_PRD_PRD_PEL.toString())
                             ||souscriptionContratCompteForm.getCodeProduitCpt().equals("0" +Constants.COD_PRD_PRD_PEM.toString())){
                        // produit PEM, PEL, afficher la periodicité de versement
                         souscriptionContratCompteForm.setPeriodVersm(listRgmCatEpargne.getCategorie().getNbrMensCat().toString());
                    }
                }
            } else {
                List listErreur = listRgmCatEpargne.getErrors();
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
            String appel = "";
            if (souscriptionContratCompteForm.getTypePersonneMenu().equals("physique") || 
                souscriptionContratCompteForm.getTypePersonneMenu().equals("personnel")) {
                appel = "successPhysique";
            } else if (souscriptionContratCompteForm.getTypePersonneMenu().equals("morale")) {
                appel = "successMorale";
            } else if (souscriptionContratCompteForm.getTypePersonneMenu().equals("cotitulaire")) {
                appel = "successCotit";
            }
            return mapping.findForward(appel);
        } catch (Exception e) {
            com.oxia.fwk.core.Error erreur = new com.oxia.fwk.core.Error();
            StringBuffer text = 
                new StringBuffer("la transaction est Interrompu, une erreur dans SouscriptionContratCompteAction / Dispatch Action :chercherParmEpargne ");
            text.append(e.toString());
            erreur.setCode("200");
            erreur.setDescription(text.toString());
            logger.error("Erreur au niveau de l'agence <<" +souscriptionContratCompteForm.getCodStrcStrc() + ">>. Exception : ",e);  
      //      logger.error("Exception : ",e); 
            ActionMessage actionMessage = 
                new ActionMessage("exception.generique", 
                                  erreur.getDescription());
            actionMessages.add("Erreur ", actionMessage);
            this.saveMessages(request, actionMessages);
            return mapping.findForward("error");
        }

    }


    public ActionForward chercherTuteur(ActionMapping mapping, ActionForm form, 
                                        HttpServletRequest request, 
                                        HttpServletResponse response) throws IOException, 
                                                                             ServletException {

        ActionMessages actionMessages = new ActionMessages();
        SouscriptionContratCompteForm souscriptionContratCompteForm = 
            (SouscriptionContratCompteForm)form;
        try {

            GetTuteurCmd getTuteurCmd = new GetTuteurCmd();
            PersonneStrc personneStrc = new PersonneStrc(); //Vo input 
            personneStrc.setCodTpceTpce(new Long(souscriptionContratCompteForm.getTypePieceTuteur()));
            personneStrc.setNumPcePers(souscriptionContratCompteForm.getNumPieceTuteur());
            Tuteur tuteur = new Tuteur(); //Vo output
            tuteur = (Tuteur)getTuteurCmd.execute(personneStrc);
            if (!tuteur.hasError()) {
                if (tuteur.getPersonneTuteur() != null) {
                    souscriptionContratCompteForm.setNomTuteur(tuteur.getPersonneTuteur().getNomNomPers());
                    souscriptionContratCompteForm.setPrenomTuteur(tuteur.getPersonneTuteur().getNomPrnPers());
                    souscriptionContratCompteForm.setNumSeqTuteur(tuteur.getPersonneTuteur().getNumSeqPers().toString());
                    souscriptionContratCompteForm.setListMineurs(tuteur.getListeDesMineures());
                    souscriptionContratCompteForm.setIsTuteur(tuteur.isIsTuteur());
                    souscriptionContratCompteForm.setAlertTuteur("TuteurExistant");
                    if (!tuteur.isIsTuteur()) {
                        souscriptionContratCompteForm.setAlert("personneInexistante");
                        souscriptionContratCompteForm.setMessageNbreMineurs(" Pas de mineurs en charge de ce tuteur.");
                    } else {
                        souscriptionContratCompteForm.setMessageNbreMineurs(tuteur.getListeDesMineures().size() + 
                                                                            "  mineur(s) en charge de ce tuteur.");
                        souscriptionContratCompteForm.setNombreMineurs(tuteur.getListeDesMineures().size());
                        //  affecter l'adresse du tuteur au mineur 

                        souscriptionContratCompteForm.setImmeubleAdrResid(tuteur.getPersonneTuteur().getAdresseResid().getImmeuble());
                        souscriptionContratCompteForm.setRueAdrResid(tuteur.getPersonneTuteur().getAdresseResid().getRue());
                        souscriptionContratCompteForm.setCiteAdrResid(tuteur.getPersonneTuteur().getAdresseResid().getCite());
                        //souscriptionContratCompteForm.setVilleCpt(personneCpt.getPersonne().getAdresseProf().getVille());
                        /* pays */
                        if (tuteur.getPersonneTuteur().getAdresseResid().getCodPaysPays() != 
                            null) {
                            souscriptionContratCompteForm.setCodPayAdrResid(tuteur.getPersonneTuteur().getAdresseResid().getCodPaysPays());
                            GetPaysCmd getPaysCmd = new GetPaysCmd();
                            Pays pays = new Pays();
                            pays.setCodPaysPays(tuteur.getPersonneTuteur().getAdresseResid().getCodPaysPays());
                            pays = (Pays)getPaysCmd.execute(pays);
                            if (pays.getLibPaysPays() != null) {
                                souscriptionContratCompteForm.setPaysAdrResid(pays.getLibPaysPays());
                                souscriptionContratCompteForm.setCodPayAdrResid(pays.getCodPaysPays());
                            }
                        }
                        /* code Postal*/
                        if (tuteur.getPersonneTuteur().getAdresseResid().getCodCpCp() != 
                            null) {
                            souscriptionContratCompteForm.setCodePostalAdrResid(tuteur.getPersonneTuteur().getAdresseResid().getCodCpCp());
                            // si le pays est la tunisie extraire le libelle du code postal
                            if ((tuteur.getPersonneTuteur().getAdresseResid().getCodPaysPays() != 
                                 null) && 
                                (tuteur.getPersonneTuteur().getAdresseResid().getCodPaysPays().equals(Constants.COD_PAYS_TUNISIE))) {
                                GetCodePostalCmd getCodePostalCmd = 
                                    new GetCodePostalCmd();
                                CodePostal codePostal = new CodePostal();
                                codePostal.setCodCpCp(Long.valueOf(tuteur.getPersonneTuteur().getAdresseResid().getCodCpCp()));
                                codePostal = 
                                        (CodePostal)getCodePostalCmd.execute(codePostal);
                                souscriptionContratCompteForm.setLibPostalAdrResid(codePostal.getLibCpCp());
                                // gouvernerat
                                souscriptionContratCompteForm.setCodGouvGouvRes(codePostal.getGouvernorat().getCodGouvGouv().toString());
                                souscriptionContratCompteForm.setLibGouvGouvRes(codePostal.getGouvernorat().getLibGouvGouv());
                            }
                        }


                    }
                } else {
                    souscriptionContratCompteForm.setOpenTabsheetProduit("false");
                    souscriptionContratCompteForm.setAlertTuteur("TuteurInexistant");
                    souscriptionContratCompteForm.setAlert("personneInexistante");
                }
            } else {
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
            String appel = "";
            if (souscriptionContratCompteForm.getTypePersonneMenu().equals("physique")) {
                appel = "successPhysique";
            } else if (souscriptionContratCompteForm.getTypePersonneMenu().equals("cptLie")) {
                appel = "successLie";
            }
            return mapping.findForward(appel);
        } catch (Exception e) {
            com.oxia.fwk.core.Error erreur = new com.oxia.fwk.core.Error();
            StringBuffer text = 
                new StringBuffer("la transaction est Interrompu, une erreur dans SouscriptionContratCompteAction / Dispatch Action :chercherParmEpargne ");
            text.append(e.toString());
            erreur.setCode("200");
            erreur.setDescription(text.toString());
            logger.error("Erreur au niveau de l'agence <<" +souscriptionContratCompteForm.getCodStrcStrc() + ">>. Exception : ",e);  
         //   logger.error("Exception : ",e); 
            ActionMessage actionMessage = 
                new ActionMessage("exception.generique", 
                                  erreur.getDescription());
            actionMessages.add("Erreur ", actionMessage);
            this.saveMessages(request, actionMessages);
            return mapping.findForward("error");
        }


    }

    public ActionForward chercherCotitulaire(ActionMapping mapping, 
                                             ActionForm form, 
                                             HttpServletRequest request, 
                                             HttpServletResponse response) throws IOException, 
                                                                                  ServletException {

        ActionMessages actionMessages = new ActionMessages();
        SouscriptionContratCompteForm souscriptionContratCompteForm = 
            (SouscriptionContratCompteForm)form;
        try {
            souscriptionContratCompteForm.setCltTrouve(null);
            souscriptionContratCompteForm.setAlertMembreCotit("");
            Listes listEntiteCotit = new Listes();
            List listEntiteCotitCpt = new ArrayList();
            GetListCotitulairePersonneCmd getListCotitulairePersonneCmd = 
                new GetListCotitulairePersonneCmd();
            PersonneStrc personneStrc = new PersonneStrc(); //Vo input 
            if (!souscriptionContratCompteForm.getNumPieceCotit().equals("")) {
                // cas de la recherche par type et numero de piece membre cotit
                personneStrc.setCodTpceTpce(new Long(souscriptionContratCompteForm.getTypePieceCotit()));
                personneStrc.setNumPcePers(souscriptionContratCompteForm.getNumPieceCotit());
                listEntiteCotit = 
                        (Listes)getListCotitulairePersonneCmd.execute((IValueObject)personneStrc);
                if (!listEntiteCotit.hasError()) {
                    if (listEntiteCotit.getList() == null) {
                        souscriptionContratCompteForm.setAlertMembreCotit("membreNonTrouve");
                    } else {
                        if (listEntiteCotit.getList().size() > 0) {
                            souscriptionContratCompteForm.setAlertMembreCotit("membreTrouve");
                            souscriptionContratCompteForm.setListEntiteCotit(listEntiteCotit.getList());
                        } else {
                            souscriptionContratCompteForm.setAlertMembreCotit("membreNonTrouve");
                        }
                    }
                } else {
                    List listErreur = listEntiteCotit.getErrors();
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
            } else {
                if (!souscriptionContratCompteForm.getCodePrdCotit().equals("") && 
                    !souscriptionContratCompteForm.getNumCompteCotit().equals("") && 
                    !souscriptionContratCompteForm.getCodeStructureCotit().equals("")) {
                    GetDetailContratCmd getDetailContratCmd = 
                        new GetDetailContratCmd();
                    ContratCpt contratCpt = new ContratCpt();
                    ContratCptId contratCptId = new ContratCptId();
                    contratCptId.setCodPrdPrd(new Long(souscriptionContratCompteForm.getCodePrdCotit()));
                    contratCptId.setCodStrcStrc(new Long(souscriptionContratCompteForm.getCodeStructureCotit()));
                    contratCptId.setNumCcptCcpt(new Long(souscriptionContratCompteForm.getNumCompteCotit()));
                    contratCpt = 
                            (ContratCpt)getDetailContratCmd.execute(contratCptId);
                    if (!contratCpt.hasError()) {
                        if (contratCpt.getContratCptId() != null) {
                            GetEntiteCotitByContratCmd getEntiteCotitByContratCmd = 
                                new GetEntiteCotitByContratCmd();
                            CoTitulaire coTitulaire = 
                                (CoTitulaire)getEntiteCotitByContratCmd.execute(contratCpt);
                            if (!coTitulaire.hasError()) {
                                if (coTitulaire.getCoTitulaireId() != null) {
                                    listEntiteCotitCpt.add(coTitulaire);
                                    souscriptionContratCompteForm.setListEntiteCotit(listEntiteCotitCpt);
                                    souscriptionContratCompteForm.setAlertMembreCotit("membreTrouve");
                                } else {
                                    souscriptionContratCompteForm.setAlertMembreCotit("entiteInexistante");
                                    
                                }
                            } else {
                                List listErreur = coTitulaire.getErrors();
                                for (Iterator it = listErreur.iterator(); 
                                     it.hasNext(); ) {
                                    com.oxia.fwk.core.Error erreur = 
                                        (com.oxia.fwk.core.Error)it.next();
                                    ActionMessage actionMessage = 
                                        new ActionMessage("exception.generique", 
                                                          erreur.getDescription());
                                    actionMessages.add("Erreur ", 
                                                       actionMessage);
                                }
                                this.saveMessages(request, actionMessages);
                                return mapping.findForward("error");
                            }
                        } else {
                            souscriptionContratCompteForm.setAlertMembreCotit("contratErrone");
                        }
                    } else {
                        List listErreur = contratCpt.getErrors();
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
                }
            }

            String appel = "";
            if (souscriptionContratCompteForm.getTypePersonneMenu().equals("cotitulaire")) {
                appel = "successCotit";
            } else if (souscriptionContratCompteForm.getTypePersonneMenu().equals("cptLie")) {
                appel = "successLie";
            }
            return mapping.findForward(appel);
        } catch (Exception e) {
            com.oxia.fwk.core.Error erreur = new com.oxia.fwk.core.Error();
            StringBuffer text = 
                new StringBuffer("la transaction est Interrompu, une erreur dans SouscriptionContratCompteAction / Dispatch Action :chercherCotitulaire ");
            text.append(e.toString());
            erreur.setCode("200");
            erreur.setDescription(text.toString());
            logger.error("Erreur au niveau de l'agence <<" +souscriptionContratCompteForm.getCodStrcStrc() + ">>. Exception : ",e);  
        //    logger.error("Exception : ",e); 
            ActionMessage actionMessage = 
                new ActionMessage("exception.generique", 
                                  erreur.getDescription());
            actionMessages.add("Erreur ", actionMessage);
            this.saveMessages(request, actionMessages);
            return mapping.findForward("error");
        }


    }


    public ActionForward chercherMembresCotitulaire(ActionMapping mapping, 
                                                    ActionForm form, 
                                                    HttpServletRequest request, 
                                                    HttpServletResponse response) throws IOException, 
                                                                                         ServletException {
        
        ActionMessages actionMessages = new ActionMessages();
        SouscriptionContratCompteForm souscriptionContratCompteForm = 
                        (SouscriptionContratCompteForm)form;

        try {
 
            Listes listMembresCotit = new Listes();
            //List listEntiteCotitCpt = new ArrayList();
            GetListMembreCotitulaireCmd getListMembreCotitulaireCmd = 
                new GetListMembreCotitulaireCmd();
            PersonneStrc personneStrc = new PersonneStrc(); //Vo input 

            String numOrdreCotitulaire = new String(request.getParameter("numOrdreCotit"));
            souscriptionContratCompteForm.setEntiteChoisie(numOrdreCotitulaire);
            souscriptionContratCompteForm.setAlert("personneExistante");
            if (!numOrdreCotitulaire.equals("")) {
                personneStrc.setCodTpceTpce(Constants.COD_NUM_ORDRE);
                personneStrc.setNumPcePers(numOrdreCotitulaire);
                personneStrc.setCodStrcStrc(Long.valueOf(souscriptionContratCompteForm.getCodStrcStrc()));
                listMembresCotit = (Listes)getListMembreCotitulaireCmd.execute((IValueObject)personneStrc);
                
                if(!listMembresCotit.hasError()){
                    if (listMembresCotit.getList() == null) {
                        souscriptionContratCompteForm.setAlertListMembresCotit("listeMembresVide");
                    } else {
                        if (listMembresCotit.getList().size() > 0) {
                            souscriptionContratCompteForm.setAlertListMembresCotit("listeMembresRemplie");
                            souscriptionContratCompteForm.setListMembresEntiteCotit(listMembresCotit.getList());
                        } else {
                            souscriptionContratCompteForm.setAlertMembreCotit("listeMembresVide");
                        }
                        // extraire le nom de l'entité choisie
                        for (Iterator it = listMembresCotit.getList().iterator(); 
                             it.hasNext(); ) {
                            CoTitulaire cotitulaire = (CoTitulaire)it.next();
                            souscriptionContratCompteForm.setCltTrouve(cotitulaire.getClient());
                            souscriptionContratCompteForm.setNomId(cotitulaire.getClient().getPersonne().getNomNomPers());
                            souscriptionContratCompteForm.setNumSeqPers(cotitulaire.getClient().getPersonne().getNumSeqPers().toString());
                            souscriptionContratCompteForm.setNumPieceId(cotitulaire.getClient().getPersonne().getNumPcePers().toString());
                            
                            
                             
                          /*   souscriptionContratCompteForm.setImmeubleCpt(cotitulaire.getClient().getPersonne().getAdresseResid().getImmeuble());
                             souscriptionContratCompteForm.setRueCpt(cotitulaire.getClient().getPersonne().getAdresseResid().getRue());
                             souscriptionContratCompteForm.setCiteCpt(cotitulaire.getClient().getPersonne().getAdresseResid().getCite());
                             
                             
                              if (cotitulaire.getClient().getPersonne().getAdresseResid().getCodPaysPays() != null) {
                                  souscriptionContratCompteForm.setCodPayCpt(cotitulaire.getClient().getPersonne().getAdresseResid().getCodPaysPays());
                                  GetPaysCmd getPaysCmd = new GetPaysCmd();
                                  Pays pays = new Pays();
                                  pays.setCodPaysPays(cotitulaire.getClient().getPersonne().getAdresseResid().getCodPaysPays());
                                  pays = (Pays)getPaysCmd.execute(pays);
                                  if (pays.getLibPaysPays() != null) {
                                      souscriptionContratCompteForm.setPaysCpt(pays.getLibPaysPays());
                                      souscriptionContratCompteForm.setCodPayCpt(pays.getCodPaysPays());
                                  }
                              }                    
                             
                              if (cotitulaire.getClient().getPersonne().getAdresseResid().getCodCpCp() != null) {
                                  souscriptionContratCompteForm.setCodePostalCpt(cotitulaire.getClient().getPersonne().getAdresseResid().getCodCpCp());
                                  // si le pays est la tunisie extraire le libelle du code postal
                                  if ((cotitulaire.getClient().getPersonne().getAdresseResid().getCodPaysPays() != null) && 
                                      (cotitulaire.getClient().getPersonne().getAdresseResid().getCodPaysPays().equals(Constants.COD_PAYS_TUNISIE))) {
                                      GetCodePostalCmd getCodePostalCmd = new GetCodePostalCmd();
                                      CodePostal codePostal = new CodePostal();
                                      codePostal.setCodCpCp(Long.valueOf(cotitulaire.getClient().getPersonne().getAdresseResid().getCodCpCp()));
                                      codePostal = (CodePostal)getCodePostalCmd.execute(codePostal);
                                      souscriptionContratCompteForm.setLibCodePostalCpt(codePostal.getLibCpCp());
                                      souscriptionContratCompteForm.setCodGouvGouvCpt(codePostal.getGouvernorat().getCodGouvGouv().toString());
                                      souscriptionContratCompteForm.setLibGouvGouvCpt(codePostal.getGouvernorat().getLibGouvGouv());

                                 }
                              }                    
                             */
                            if (cotitulaire.getClient() != null) {                                
                                
                                if(cotitulaire.getClient().getNumFiscClt() != null)
                                 souscriptionContratCompteForm.setNumFiscaleCpt(cotitulaire.getClient().getNumFiscClt());
                                if(cotitulaire.getClient().getCodDoanClt() != null)
                                 souscriptionContratCompteForm.setCodeDouaneCpt(cotitulaire.getClient().getCodDoanClt());
                                if(cotitulaire.getClient().getNumBctClt() != null)
                                  souscriptionContratCompteForm.setNumBctCpt(cotitulaire.getClient().getNumBctClt());
                                if(cotitulaire.getClient().getDatRelClt() != null)
                                    souscriptionContratCompteForm.setDateRelationCpt(DateHandler.dateToStr(cotitulaire.getClient().getDatRelClt()));
                                 
                            }
                             
                            
                            
                            GetPersonneCptCmd getPersonneCptCmd = new GetPersonneCptCmd();    
                            PersonneCpt personneCpt =  new PersonneCpt(); //Vo output
                            personneCpt = (PersonneCpt)getPersonneCptCmd.execute(personneStrc);
                            if(!personneCpt.hasError()){
                                if (personneCpt.getPersonne() != null) {
                                    souscriptionContratCompteForm.setListeContrats(personneCpt.getListeContratCpt());
                                }
                            }else{
                                //gererErreur(personneCpt);
                                List listErreur = personneCpt.getErrors();                    
                                for (Iterator it1 = listErreur.iterator(); it1.hasNext(); ) {
                                    com.oxia.fwk.core.Error erreur = (com.oxia.fwk.core.Error)it1.next();
                                    ActionMessage actionMessage = new ActionMessage("exception.generique",erreur.getDescription());
                                    actionMessages.add("Erreur ", actionMessage);
                                }    
                                this.saveMessages(request, actionMessages);
                                return mapping.findForward("error");  
                            }
                        }
                    }
                 }else{                    
                      List listErreur = listMembresCotit.getErrors();                    
                      for (Iterator it = listErreur.iterator(); it.hasNext(); ) {
                          com.oxia.fwk.core.Error erreur = (com.oxia.fwk.core.Error)it.next();
                          ActionMessage actionMessage = new ActionMessage("exception.generique",erreur.getDescription());
                          actionMessages.add("Erreur ", actionMessage);
                      }    
                      this.saveMessages(request, actionMessages);
                      return mapping.findForward("error");  
                  }
            }
             String appel = "";
             if(souscriptionContratCompteForm.getTypePersonneMenu().equals("cotitulaire")){
               appel = "successCotit";
             }else if(souscriptionContratCompteForm.getTypePersonneMenu().equals("cptLie")){ 
                      appel = "successLie";
                   }        
             return mapping.findForward(appel); 
         } catch (Exception e) {
                         com.oxia.fwk.core.Error erreur = new com.oxia.fwk.core.Error();
                         StringBuffer text = 
                             new StringBuffer("la transaction est Interrompu, une erreur dans SouscriptionContratCompteAction / Dispatch Action :chercherMembresCotitulaire ");
                         text.append(e.toString());
                         erreur.setCode("200");
                         erreur.setDescription(text.toString());
                         logger.error("Erreur au niveau de l'agence <<" +souscriptionContratCompteForm.getCodStrcStrc() + ">>. Exception : ",e);  
                        // logger.error("Exception : ",e); 
                         ActionMessage actionMessage = new ActionMessage("exception.generique",erreur.getDescription());
                         actionMessages.add("Erreur ", actionMessage);
                         this.saveMessages(request, actionMessages);
                         return mapping.findForward("error");
            }

    }



    public ActionForward chercherContratDav(ActionMapping mapping, 
                                            ActionForm form, 
                                            HttpServletRequest request, 
                                            HttpServletResponse response) throws IOException, 
                                                                                 ServletException {

        ActionMessages actionMessages = new ActionMessages();
        SouscriptionContratCompteForm souscriptionContratCompteForm = 
            (SouscriptionContratCompteForm)form;

        try {
            Integer cptExist = Integer.valueOf(0);
            GetDetailContratCmd getDetailContratCmd = 
                new GetDetailContratCmd();
            ContratCpt contratCpt = new ContratCpt();
            ParamCompteLie paramCompteLie = new ParamCompteLie();
            ContratCptId contratCptId = new ContratCptId();
            contratCptId.setCodPrdPrd(Long.valueOf(souscriptionContratCompteForm.getCodPrdDav()));
            contratCptId.setCodStrcStrc(Long.valueOf(souscriptionContratCompteForm.getCodStrcDav()));
            contratCptId.setNumCcptCcpt(Long.valueOf(souscriptionContratCompteForm.getNumCcptDav()));
            contratCpt = (ContratCpt)getDetailContratCmd.execute(contratCptId);

            souscriptionContratCompteForm.setTypPceDav("");
            souscriptionContratCompteForm.setNumPceDav("");
            souscriptionContratCompteForm.setNomNomDav("");
            souscriptionContratCompteForm.setNomPrnDav("");
            souscriptionContratCompteForm.setMontSoldCcpt("");
            souscriptionContratCompteForm.setLibDevDav("");
            souscriptionContratCompteForm.setCodeDeviseDav("");

            if (!contratCpt.hasError()) {
                if (contratCpt.getContratCptId() != null) {
                    if (contratCpt.getClient().getNumSeqPers().toString().equals(souscriptionContratCompteForm.getNumSeqPers())) {

                        paramCompteLie.setContratCpt(contratCpt);
                        paramCompteLie.setCodeProduit(new Long(souscriptionContratCompteForm.getCodPrdVert()));
                        GetNombreContratParClientCmd getNombreContratParClientCmd = 
                            new GetNombreContratParClientCmd();
                        paramCompteLie = 
                                (ParamCompteLie)getNombreContratParClientCmd.execute(paramCompteLie);

                        if (paramCompteLie.getNbreContrats().equals(new Integer(0))) {
                            souscriptionContratCompteForm.setTypPceDav(contratCpt.getClient().getPersonne().getTypePiece().getCodTpceTpce().toString());
                            souscriptionContratCompteForm.setNumPceDav(contratCpt.getClient().getPersonne().getNumPcePers());
                            if (contratCpt.getClient().getTypePers().getCodTperTper().equals(Constants.PERSPHYSIQUE)) {
                                souscriptionContratCompteForm.setNomNomDav(contratCpt.getClient().getPersonne().getNomNomPers());
                                souscriptionContratCompteForm.setNomPrnDav(contratCpt.getClient().getPersonne().getNomPrnPers());
                            } else if (contratCpt.getClient().getTypePers().getCodTperTper().equals(Constants.PERSMORALE)) {
                                souscriptionContratCompteForm.setNomNomDav(contratCpt.getClient().getPersonne().getNomRsPers());
                                souscriptionContratCompteForm.setNomPrnDav(contratCpt.getClient().getPersonne().getLibSiglPers());
                            }else{
                                souscriptionContratCompteForm.setNomNomDav(contratCpt.getClient().getPersonne().getNomNomPers());
                            }
                            if(contratCpt.getMontSoldCcpt()!= null)
                             souscriptionContratCompteForm.setMontSoldCcpt(contratCpt.getMontSoldCcpt().toString());
                            
                            souscriptionContratCompteForm.setLibDevDav(contratCpt.getDevise().getLibDevDev());
                            souscriptionContratCompteForm.setCodeDeviseDav(contratCpt.getDevise().getCodDevDev().toString());
                            souscriptionContratCompteForm.setContratDav(contratCpt);
                            souscriptionContratCompteForm.setAlertDav("contratValide");
                        } else {
                            souscriptionContratCompteForm.setAlertDav("contratLieExistant");
                        }
                    } else {
                        souscriptionContratCompteForm.setAlertDav("contratInvalide");
                    }
                } else {
                    souscriptionContratCompteForm.setAlertDav("contratErrone");
                }
            } else {
                List listErreur = contratCpt.getErrors();
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

            return mapping.findForward("successLie");
        } catch (Exception e) {
            com.oxia.fwk.core.Error erreur = new com.oxia.fwk.core.Error();
            StringBuffer text = 
                new StringBuffer("la transaction est Interrompu, une erreur dans SouscriptionContratCompteAction / Dispatch Action :chercherContratDav ");
            text.append(e.toString());
            erreur.setCode("200");
            erreur.setDescription(text.toString());
            logger.error("Erreur au niveau de l'agence <<" +souscriptionContratCompteForm.getCodStrcStrc() + ">>. Exception : ",e);  
        //    logger.error("Exception : ",e); 
            ActionMessage actionMessage = 
                new ActionMessage("exception.generique", 
                                  erreur.getDescription());
            actionMessages.add("Erreur ", actionMessage);
            this.saveMessages(request, actionMessages);
            return mapping.findForward("error");
        }
    }


    public ActionForward verifierRCSPieceAnnexe(ActionMapping mapping, 
                                            ActionForm form, 
                                            HttpServletRequest request, 
                                            HttpServletResponse response) throws IOException, 
                                                                                 ServletException {

        ActionMessages actionMessages = new ActionMessages();
        SouscriptionContratCompteForm souscriptionContratCompteForm = 
            (SouscriptionContratCompteForm)form;
        
        souscriptionContratCompteForm.setAlertRcs(""); 

    if (souscriptionContratCompteForm.getTypePieceAnnexe().equals(Constants.COD_RCS.toString())) {
        if (!Constants.verifRCS(souscriptionContratCompteForm.getNumPieceAnnexe(), 
                                souscriptionContratCompteForm.getTypePersonneId())) {
            souscriptionContratCompteForm.setAlertRcs("rcsErreur");            
        }
    }
    
        return mapping.findForward("successPhysique");
    }
    
    


}


