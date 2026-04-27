package com.bna.smile.web.commun.actions;

import com.bna.commun.model.Gouvernorat;
import com.bna.commun.model.PersClient;
import com.bna.commun.model.PersClientId;
import com.bna.commun.model.Personne;
import com.bna.commun.model.StructureDomaine;
import com.bna.commun.model.TypeModification;
import com.bna.commun.model.TypePiece;
import com.bna.commun.util.DateHandler;
import com.bna.commun.util.SmileUtil;
import com.bna.commun.util.StrHandler;

import com.bna.smile.model.constant.Constants;
import com.bna.smile.model.domainecommun.commande.GetPersonneClientQualiteCmd;
import com.bna.smile.model.domainecommun.commande.GetPersonneCptCmd;
import com.bna.smile.model.domainecommun.commande.GetTypeModificationCmd;
import com.bna.smile.model.domainecommun.commande.GetTypePieceCmd;
import com.bna.smile.model.domainecommun.model.ParamListPersonneQualiteClientVo;
import com.bna.smile.model.domainecommun.model.PersonneCpt;
import com.bna.smile.model.domainecommun.model.PersonneStrc;
import com.bna.smile.model.domainecommun.traitement.GetGouvernoratTrt;

import com.bna.smile.model.domainecontratcompte.modificationdonneesclient.commande.ModifierQualitePersonneCmd;
import com.bna.smile.model.domainecontratcompte.modificationdonneesclient.model.ParamModificationQualitePersClientVo;
import com.bna.smile.model.reporting.commande.PrinterCmd;
import com.bna.smile.model.reporting.model.CommonReportVO;
import com.bna.smile.web.commun.forms.ModificationDonneesClientForm;
import com.bna.smile.web.commun.forms.ModificationQualitePersonneForm;
import com.bna.smile.web.commun.model.ParamAgence;

import com.bna.smile.web.commun.util.PersonneClientView;

import com.bna.smile.web.commun.util.SessionUtil;

import com.bna.smile.web.moyenPaiement.demandeCarteBnacaire.actions.PecDemandeCarteBancaireAction;

import fr.improve.struts.taglib.layout.datagrid.Datagrid;

import java.io.IOException;

import java.util.ArrayList;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;

import java.util.List;

import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.actions.DispatchAction;

public class ModificationQualitePersonneAction extends DispatchAction {
    public ActionForward initierPageModificationClient(ActionMapping mapping, 
                                                       ActionForm form, 
                                                       HttpServletRequest request, 
                                                       HttpServletResponse response) throws IOException, 
                                                                                            ServletException {
        Logger logger = Logger.getLogger(ModificationQualitePersonneAction.class);                                                                                            
        ParamAgence paramAgence = 
            (ParamAgence)request.getSession().getAttribute("paramAgBNA"); /// structure de l'agent qui fait la saisie
        try{
        //verification de l'habilitation sur cet operation
        StructureDomaine structureDomaine = new StructureDomaine(paramAgence.getCodStrcStrc(),Constants.COD_DOM_CLIENT);
        boolean bool = SmileUtil.testDomaineOuvert(structureDomaine);
        ModificationQualitePersonneForm modificationQualitePersonneForm = 
            (ModificationQualitePersonneForm)form;
        String typePers = new String();
        
        
        
        SessionUtil sessionUtil =new SessionUtil();
        //Suppression des anciens Bean de type Form de la session, SAUF "consultationContratCompteForm"
        sessionUtil.removeSession(request,"modificationQualitePersonneForm");

        

        modificationQualitePersonneForm.clearForm();
        GetTypeModificationCmd getTypeModificationCmd = 
            new GetTypeModificationCmd();
        TypeModification typeModification = new TypeModification();
        typeModification.setCodCodModf(Long.valueOf(modificationQualitePersonneForm.getCodeModification()));
        typeModification = 
                (TypeModification)getTypeModificationCmd.execute(typeModification);
        modificationQualitePersonneForm.setLibelleModification(typeModification.getLibModfModf());
        modificationQualitePersonneForm.setTypeModification(typeModification);
        modificationQualitePersonneForm.setMatriculeUser(paramAgence.getNumMatrUser().toString());
        modificationQualitePersonneForm.setDateActuelle(DateHandler.dateJour());

        /// Create a new datagrid (of PersonneClient);
        Datagrid lc_datagrid = Datagrid.getInstance();
        lc_datagrid.setDataClass(PersonneClientView.class);
        lc_datagrid.setData(new ArrayList());

        modificationQualitePersonneForm.setListPersonneClientGrid(lc_datagrid);
        return mapping.findForward("initierPage");
            } catch (Exception e) {
                    ActionMessages actionMessages = new ActionMessages();
                    ActionMessage actionMessage = 
                        new ActionMessage("exception.generique", 
                                          e.getMessage() );
                    actionMessages.add("Erreur ", actionMessage);   
                    this.saveMessages(request, actionMessages);
                    logger.error("Exception : ",e);
                    return mapping.findForward("error");  
        }
    }

    public ActionForward rechercherPersonne(ActionMapping mapping, 
                                            ActionForm form, 
                                            HttpServletRequest request, 
                                            HttpServletResponse response) throws IOException, 
                                                                                 ServletException {
        ActionMessages actionMessages = new ActionMessages();
        ModificationQualitePersonneForm modificationQualitePersonneForm = 
            (ModificationQualitePersonneForm)form;

        ParamAgence paramAgence = 
            (ParamAgence)request.getSession().getAttribute("paramAgBNA");
        Personne personne = new Personne();

        try {
            modificationQualitePersonneForm.setMessage("IdPrincipalExist");
            //-------------------------------------------------------
            //------- recherche de la personne ----------------------
            //-------------------------------------------------------
            PersonneStrc personneStrc = new PersonneStrc();
            personneStrc.setCodTpceTpce(Long.valueOf(modificationQualitePersonneForm.getTypePiece()));
            personneStrc.setNumPcePers(modificationQualitePersonneForm.getNumeroPiece());
            personneStrc.setCodStrcStrc(Long.valueOf(paramAgence.getCodStrcStrc()));
            GetPersonneCptCmd getPersonneCptCmd = new GetPersonneCptCmd();
            PersonneCpt personneCpt = 
                (PersonneCpt)getPersonneCptCmd.execute(personneStrc);
            //------- Fin de la recherche de la personne

            if (personneCpt.getPersonne() != null) {
                personne = (Personne)personneCpt.getPersonne();
                modificationQualitePersonneForm.setTestExistPersonne("Existe");
                modificationQualitePersonneForm.setPersonne(personneCpt.getPersonne());

                if (personne.getGouvernorat() != null) {
                    GetGouvernoratTrt getGouvernoratTrt = 
                        new GetGouvernoratTrt();
                    Gouvernorat gouvernorat = new Gouvernorat();
                    gouvernorat.setCodGouvGouv(personne.getGouvernorat().getCodGouvGouv());
                    gouvernorat = 
                            (Gouvernorat)getGouvernoratTrt.exec(gouvernorat);

                }

                if (personne.getNomNomPers() != null) {
                    modificationQualitePersonneForm.setNomPersonne(personne.getNomNomPers());

                }
                if (personne.getNomRsPers() != null) {
                    modificationQualitePersonneForm.setRaisonSocial(personne.getNomRsPers());
                }
                if (personne.getLibSiglPers() != null) {
                    modificationQualitePersonneForm.setSigle(personne.getLibSiglPers());

                }
                if (personne.getNomPrnPers() != null) {
                    modificationQualitePersonneForm.setPrenomPersonne(personne.getNomPrnPers());
                }
                //-------------------------------------------------------------------------//
                //----------- Si la personne est un client chercher les relations ---------//
                //-------------------------------------------------------------------------//

                if (personneCpt.getClient() != null) {
                    modificationQualitePersonneForm.setClient(personneCpt.getClient());
                    modificationQualitePersonneForm.setTestExistClient("Existe");

                    //--------------------------------------------------------------------------------
                    //---------- Recherche des personnes qui sont en relation avec le client
                    ParamListPersonneQualiteClientVo paramVo = 
                        new ParamListPersonneQualiteClientVo();
                    paramVo.setNumSeqPers(personne.getNumSeqPers());
                    paramVo.setCodQualQual(Long.valueOf(modificationQualitePersonneForm.getCodQualQual()));
                    GetPersonneClientQualiteCmd getPersonneClientQualiteCmd = 
                        new GetPersonneClientQualiteCmd();
                    paramVo = 
                            (ParamListPersonneQualiteClientVo)getPersonneClientQualiteCmd.execute(paramVo);
                    if (!paramVo.hasError()) {
                        if (paramVo.getListePersonneClient() != null && 
                            (paramVo.getListePersonneClient().size() > 0)) {
                            List listeDesPersonnes = new ArrayList();
                          //  GetTypePieceCmd getTypePieceCmd = new GetTypePieceCmd();
                           // TypePiece typePiece = new TypePiece();
                            
                            for (Iterator it = 
                                 paramVo.getListePersonneClient().iterator(); 
                                 it.hasNext(); ) {
                                PersClient persClient = (PersClient)it.next();
                                PersonneClientView personneClientView = 
                                    new PersonneClientView();
                                personneClientView.setNumSeqPers(persClient.getPersonne().getNumSeqPers());
                                personneClientView.setCodTpceTpce(persClient.getPersonne().getTypePiece().getCodTpceTpce());
                               // typePiece.setCodTpceTpce(persClient.getPersonne().getTypePiece().getCodTpceTpce());
                               // typePiece = (TypePiece) getTypePieceCmd.execute(typePiece);
                                
                                //personneClientView.setLibTpceTpce(typePiece.getLibSiglTpce());
                                personneClientView.setNumPcePers(persClient.getPersonne().getNumPcePers());
                                personneClientView.setNomNomPers(persClient.getPersonne().getNomNomPers());
                                personneClientView.setNomPrnPers(persClient.getPersonne().getNomPrnPers());

                                if (persClient.getLibFoncPecl() != null) {
                                    personneClientView.setLibFoncPecl(persClient.getLibFoncPecl());
                                }
                                if (persClient.getTauxPartPecl() != null) {
                                    personneClientView.setTauxPartPect(persClient.getTauxPartPecl());
                                }

                                listeDesPersonnes.add(personneClientView);
                            }

                            modificationQualitePersonneForm.getListPersonneClientGrid().setData(listeDesPersonnes);
                        }
                    } // fin if paramVo.hasError()
                } // fin client
            } // fin personne

            return mapping.findForward("initierPage");


        } catch (Exception e) {
            com.oxia.fwk.core.Error erreur = new com.oxia.fwk.core.Error();
            StringBuffer text = 
                new StringBuffer("la transaction est Interrompu, une erreur dans ModifierDonneesClientAction : ");
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


    public ActionForward modificationQualite(ActionMapping mapping, 
                                             ActionForm form, 
                                             HttpServletRequest request, 
                                             HttpServletResponse response) throws IOException, 
                                                                                  ServletException {
        ActionMessages actionMessages = new ActionMessages();
        ModificationQualitePersonneForm modificationQualitePersonneForm = 
            (ModificationQualitePersonneForm)form;

        ParamAgence paramAgence = 
            (ParamAgence)request.getSession().getAttribute("paramAgBNA");

        try {
            //--------------------------------------------------------------//
            //--------Reconstruire la liste des personnes client ----------------//
            Collection dgAdd = 
                modificationQualitePersonneForm.getListPersonneClientGrid().getDataWithState("");
            Collection dgSel = 
                modificationQualitePersonneForm.getListPersonneClientGrid().getDataWithState("selected");

            Collection listNewParsClientView = new ArrayList();
            listNewParsClientView.addAll(dgAdd);
            listNewParsClientView.addAll(dgSel);
            //
           
            List listePersonneClient = new ArrayList();
            Collection listNewPersCliView =   new ArrayList();
            
            for (Iterator it = listNewParsClientView.iterator(); it.hasNext(); 
            ) {
                PersonneClientView personneClientView = 
                    (PersonneClientView)it.next();
        
                    GetTypePieceCmd getTypePieceCmd = new GetTypePieceCmd();
                    TypePiece typePiece = new TypePiece();
                    typePiece.setCodTpceTpce(personneClientView.getCodTpceTpce());
                    typePiece = (TypePiece) getTypePieceCmd.execute(typePiece);
                    personneClientView.setLibTpceTpce(typePiece.getLibSiglTpce());
               
                PersClient persClient = new PersClient();
                PersClientId persClientId1 = new PersClientId();
                if (personneClientView.getNumSeqPers() != null && 
                    (!personneClientView.getNumSeqPers().equals("")) && 
                    (!personneClientView.getNumSeqPers().equals(Long.valueOf("0")))) {
                    persClientId1.setCodQualQual(Long.valueOf(modificationQualitePersonneForm.getCodQualQual()));
                    persClientId1.setNumSeqCli(Long.valueOf(modificationQualitePersonneForm.getClient().getNumSeqPers()));
                    persClientId1.setNumSeqPers(personneClientView.getNumSeqPers());
                    persClient.setPersClientId(persClientId1);
                    persClient.setTauxPartPecl(personneClientView.getTauxPartPect());
                    persClient.setLibFoncPecl(personneClientView.getLibFoncPecl());

                    listePersonneClient.add(persClient);
                    
                    listNewPersCliView.add(personneClientView);
                }
            }
            
            modificationQualitePersonneForm.setListNewParsClientView(listNewPersCliView);
            
            ModifierQualitePersonneCmd modifierQualitePersonneCmd = 
                new ModifierQualitePersonneCmd();
            ParamModificationQualitePersClientVo paramVo = 
                new ParamModificationQualitePersClientVo();
            paramVo.setNumSeqPers(modificationQualitePersonneForm.getClient().getNumSeqPers());
            paramVo.setCodQualQual(Long.valueOf(modificationQualitePersonneForm.getCodQualQual()));
            paramVo.setListePersonneClient(listePersonneClient);
            paramVo.setMatriculeUser(paramAgence.getNumMatrUser().toString());
            paramVo.setTypeModification(modificationQualitePersonneForm.getTypeModification());
            paramVo = 
                    (ParamModificationQualitePersClientVo)modifierQualitePersonneCmd.execute(paramVo);

            if (paramVo.hasError()) {
                List listErreur = paramVo.getErrors();
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
            } else {
                StringBuffer text = new StringBuffer();
                text.append(" La ");
                text.append(modificationQualitePersonneForm.getLibelleModification());
                text.append(" pour la ");
                text.append((modificationQualitePersonneForm.getPersonne().getNomRsPers()));
                text.append(" titulaire de ");
                text.append(modificationQualitePersonneForm.getPersonne().getTypePiece().getLibTpceTpce());
                text.append(" avec le numéro : ");
                text.append(modificationQualitePersonneForm.getPersonne().getNumPcePers());
                text.append(" a été effectuée avec succès");
                modificationQualitePersonneForm.setLibelleConfirmation(text.toString());
                return mapping.findForward("confirmation");
            }

        } catch (Exception e) {
            com.oxia.fwk.core.Error erreur = new com.oxia.fwk.core.Error();
            StringBuffer text = 
                new StringBuffer("la transaction est interrompu, une erreur dans ModificationQualitePersonnetAction : ");
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

    public ActionForward annuler(ActionMapping mapping, ActionForm form, 
                                 HttpServletRequest request, 
                                 HttpServletResponse response) throws IOException, 
                                                                      ServletException {
        ModificationQualitePersonneForm modificationQualitePersonneForm = 
            (ModificationQualitePersonneForm)form;

        modificationQualitePersonneForm.clearForm();

        return mapping.findForward("initierPage");
    }
    
    
    public ActionForward printModification(ActionMapping mapping, ActionForm form, 
                                     HttpServletRequest request, 
                                     HttpServletResponse response) throws IOException, 
                                                                          ServletException
                                                                     {
                                                                     
            ModificationDonneesClientForm modificationDonneesClientForm = 
                (ModificationDonneesClientForm)form;
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

                                    String vLibEtat = modificationDonneesClientForm.getLibelleModification();
                                    String vMatrUser = paramAgence.getNumMatrUser().toString();
                                    String vLogo = getServlet().getServletContext().getRealPath("")+ "\\reporting\\";
                                    String vMODIF1 = modificationDonneesClientForm.getLibelleConfirmation();
                                    String vMODIF2 = modificationDonneesClientForm.getLibelleConfirmation1();
                                    String vMODIF3 = modificationDonneesClientForm.getLibelleConfirmation2();
                                    parameters.put(pMatrUser, vMatrUser);
                                    parameters.put(pLibEtat, vLibEtat);
                                    parameters.put(pMODIF1, vMODIF1);
                                    parameters.put(pMODIF2, vMODIF2);
                                    parameters.put(pMODIF3, vMODIF3);
                                    parameters.put(pLogo, vLogo);
                                   
                                   valueObject.setParams(parameters);
                                   valueObject.setNomReport("EtatModification");
                                   valueObject.setRootFolder(getServlet().getServletContext().getRealPath("")+ "\\reporting\\");
                                   PrinterCmd printer = new PrinterCmd();

                                   valueObject = (CommonReportVO) printer.execute(valueObject);
                                   
                                   response.setContentType("application/pdf");
                                   response.setContentLength(valueObject.getContent().length);
                                   ServletOutputStream ouputStream = response.getOutputStream();
                                   ouputStream.write(valueObject.getContent(), 0, valueObject.getContent().length);
                                   ouputStream.flush();
                                   ouputStream.close();
                           } catch (Exception e) {
                                
                                   e.printStackTrace();
                           }
                    
            return mapping.findForward("confirmationModification");
                                                                          
        }

}
