package com.bna.smile.web.procuration.actions;

import com.bna.commun.model.ContratCpt;
import com.bna.commun.model.ContratCptId;
import com.bna.commun.model.Mandat;
import com.bna.commun.model.MandatOperation;
import com.bna.commun.model.MandatOperationId;
import com.bna.commun.model.MandatPersonne;
import com.bna.commun.model.MandatPersonneId;
import com.bna.commun.model.Operation;
import com.bna.commun.model.PersClient;
import com.bna.commun.model.Personne;
import com.bna.commun.model.Personnel;
import com.bna.commun.model.Structure;
import com.bna.commun.model.StructureDomaine;
import com.bna.commun.model.Tache;
import com.bna.commun.model.TacheId;
import com.bna.commun.model.TraceMandat;
import com.bna.commun.util.DateHandler;import com.bna.commun.util.StrHandler;
import com.bna.commun.util.DateHandler;import com.bna.commun.util.StrHandler;
import com.bna.commun.util.ContextHandler;
import com.bna.commun.util.SmileUtil;
import com.bna.smile.model.constant.Constants;
import com.bna.smile.model.domainecommun.commande.GetPersonneByNumSeqPersCmd;
import com.bna.smile.model.domainecommun.commande.GetPersonneClientQualiteCmd;
import com.bna.smile.model.domainecommun.commande.GetPersonneCptCmd;
import com.bna.smile.model.domainecommun.commande.InsertPersonneCmd;
import com.bna.smile.model.domainecommun.model.MandatPersonneMandat;
import com.bna.smile.model.domainecommun.model.ParamListPersonneQualiteClientVo;
import com.bna.smile.model.domainecommun.model.PersonneCpt;
import com.bna.smile.model.domainecommun.model.PersonneStrc;
import com.bna.smile.model.domainecontratcompte.procuration.commande.CreatMandatCmd;
import com.bna.smile.model.domainecontratcompte.procuration.commande.CreationMandatCmd;
import com.bna.smile.model.domainecontratcompte.procuration.commande.GetDetailMandatCmd;
import com.bna.smile.model.domainecontratcompte.procuration.commande.GetMandatCmd;
import com.bna.smile.model.domainecontratcompte.procuration.commande.InsertTraceMandatCmd;
import com.bna.smile.model.domainecontratcompte.procuration.commande.MiseAJourMandatCmd;
import com.bna.smile.model.domainecontratcompte.procuration.commande.MiseAJourMandatTraceCmd;
import com.bna.smile.model.domainecontratcompte.procuration.commande.ValidModifMandCmd;
import com.bna.smile.model.domainecontratcompte.procuration.model.DetailMandat;
import com.bna.smile.model.domainecontratcompte.souscriptioncontratcompte.commande.InsertClientContratCmd;
import com.bna.smile.model.domainecontratcompte.souscriptioncontratcompte.model.ParamInsertContrat;
import com.bna.smile.web.commun.model.ParamAgence;
import com.bna.smile.web.procuration.forms.ConsultMandantContratForm;

import com.bna.smile.web.procuration.forms.ConsultationMandatForm;
import com.bna.smile.web.procuration.model.MandatOperationGrid;
import com.bna.smile.web.procuration.model.Mandataire;
import com.bna.smile.model.domainecontratcompte.procuration.model.ParamInsertMandat;
import com.bna.smile.model.domainecontratcompte.procuration.model.ParamModifMandVo;
import com.bna.smile.model.domainecontratcompte.procuration.traitement.ValidModifMandTrt;
import com.bna.smile.model.reporting.model.CommonReportVO;
import com.bna.smile.web.commun.util.PersonneClientView;
import com.bna.smile.web.commun.util.SessionUtil;
import com.bna.smile.web.moyenPaiement.demandeCarteBnacaire.actions.PecDemandeCarteBancaireAction;
import com.bna.smile.web.procuration.util.ContratCptView;

import com.bna.smile.web.souscription.forms.SouscriptionContratCompteForm;

import com.oxia.fwk.context.Context;
import com.oxia.fwk.core.ISearchEngine;
import com.oxia.fwk.core.ValueObject;
import com.oxia.fwk.searchengine.SearchEngine;

import fr.improve.struts.taglib.layout.datagrid.Datagrid;

import java.io.IOException;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

import java.util.Map;
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

public class ConsultMandantContratAction extends DispatchAction {
    /**
     * <B> Action de la page  consultationMandatContrat.jsp  </B>
     * This is the main action called from the Struts framework.
     * @param mapping The ActionMapping used to select this instance.
     * @param form The optional ActionForm bean for this request.
     * @param request The HTTP Request we are processing.
     * @param response The HTTP Response we are processing.
     * @author BOUSSEN Youssef & MDIMAGH Lassaad
     * @version le 11/05/2007
     */
    public Context context = ContextHandler.getContext();
    public ParamAgence paramAgence=new ParamAgence();
    public String tempCodeTraitement;
    private static final Logger logger = Logger.getLogger(PecDemandeCarteBancaireAction.class);
    public ActionForward afficheMandatsContrat(ActionMapping mapping, 
                                               ActionForm form, 
                                               HttpServletRequest request, 
                                               HttpServletResponse response) throws IOException, 
                                                                                    ServletException {

try {        
    SessionUtil sessionUtil =new SessionUtil();
    //Suppression des anciens Bean de type Form de la session, SAUF "consultMandantContratForm"
    sessionUtil.removeSession(request,"consultMandantContratForm"); 
        
    /* garnir les informations sur l'agence (apartir du Login) */   
    paramAgence = (ParamAgence)request.getSession().getAttribute("paramAgBNA");/// structure de l'agent qui fait la saisie


            ConsultMandantContratForm consultMandantContratForm = 
                (ConsultMandantContratForm)form;
            consultMandantContratForm.setClientAgence("false");    
            PersonneStrc personneStrc = new PersonneStrc();
            PersonneCpt personneCpt = new PersonneCpt();
            consultMandantContratForm.getListContratCpt().clear();
            consultMandantContratForm.getIndexContratChoisis().clear();
            consultMandantContratForm.setPersonneexist("true");
            consultMandantContratForm.setNomNomPers("");
            consultMandantContratForm.setNomPrnPers("");
            consultMandantContratForm.setMyTypeStructure(paramAgence.getCodTstrcTstrc());
            
            /*if (paramAgence.getCodStrcStrc().equals(Constants.COD_STRC_DAJ)){/// s'il est un agent de la DAJ
                personneStrc.setCodStrcStrc(null);
            } else 
            personneStrc.setCodStrcStrc(paramAgence.getCodStrcStrc());*/
            
            personneStrc.setCodTpceTpce(consultMandantContratForm.getTypPcePers());
            if (consultMandantContratForm.getTypPcePers().equals(Constants.COD_CIN)) {
                consultMandantContratForm.setNumPcePce(StrHandler.lpad(consultMandantContratForm.getNumPcePce(), '0', 8));
            }
            personneStrc.setNumPcePers(consultMandantContratForm.getNumPcePce());
    
            GetPersonneCptCmd getPersonneCptCmd = new GetPersonneCptCmd();
            personneCpt = (PersonneCpt)getPersonneCptCmd.execute(personneStrc);
            if (personneCpt.getPersonne() != null) { /* si personne est existante */
                if ((personneCpt.getPersonne().getTypePiece().getCodTpceTpce().equals(Constants.COD_RCS))||  /* cas RCS affichage de libSiglPers */
                   (personneCpt.getPersonne().getTypePiece().getCodTpceTpce().equals(Constants.COD_NUM_ORDRE))){
                    consultMandantContratForm.setNomNomPers(personneCpt.getPersonne().getLibSiglPers());
                    consultMandantContratForm.setNomPrnPers(personneCpt.getPersonne().getNomRsPers());
                } else {
                    consultMandantContratForm.setNomNomPers(personneCpt.getPersonne().getNomNomPers());
                    consultMandantContratForm.setNomPrnPers(personneCpt.getPersonne().getNomPrnPers());
                }
                consultMandantContratForm.setNumRdjMand(personneCpt.getPersonne().getNumDosJur());
                
                List listeDesContratsView = new ArrayList();
                
                for (Iterator it = personneCpt.getListeContratCpt().iterator(); 
                     it.hasNext(); ) {
                    ContratCpt contratCpt = (ContratCpt)it.next();
                    if (contratCpt.getContratCptId().getCodStrcStrc().equals(paramAgence.getCodStrcStrc())){
                        consultMandantContratForm.setClientAgence("true");
                    }
                    String cleContrat = 
                        contratCpt.getContratCptId().getCodStrcStrc() + "_" + 
                        contratCpt.getContratCptId().getCodPrdPrd() + "*" + 
                        contratCpt.getContratCptId().getNumCcptCcpt();
                    ContratCptView contratCptView = new ContratCptView();
                    contratCptView.setCleContrat(cleContrat);
                    contratCptView.setDateContrat(DateHandler.dateToStr(contratCpt.getDatOuvCcpt()));
                    contratCptView.setNumeroCompte(StrHandler.lpad(contratCpt.getContratCptId().getNumCcptCcpt().toString(), 
                                                                   '0', 6));
                    contratCptView.setCodeAgence(StrHandler.lpad(contratCpt.getContratCptId().getCodStrcStrc().toString(), 
                                                                 '0', 3));
                    contratCptView.setCodeProduit(StrHandler.lpad(contratCpt.getContratCptId().getCodPrdPrd().toString(), 
                                                                  '0', 4));
                    contratCptView.setContratCpt(contratCpt);
                    listeDesContratsView.add(contratCptView);
                }
            if (consultMandantContratForm.getClientAgence().equalsIgnoreCase("true")){
                consultMandantContratForm.setListContratCpt(listeDesContratsView);
            }
    
                for (int i = 0; 
                     i < consultMandantContratForm.getListContratCpt().size(); i++)
                    consultMandantContratForm.getIndexContratChoisis().add("");
    
                consultMandantContratForm.setPersonneexist("true");
            } else {
                consultMandantContratForm.setNumPcePce("");
                consultMandantContratForm.setTypPcePers(Constants.COD_CIN);
                consultMandantContratForm.setPersonneexist("false");
            }
    
    } catch (Exception e) {
        ActionMessages actionMessages = new ActionMessages();
        ActionMessage actionMessage = 
            new ActionMessage("exception.generique",e.getMessage());
        actionMessages.add("Erreur ", actionMessage);   
        this.saveMessages(request, actionMessages);
        logger.error("Exception : ",e);
        return mapping.findForward("error");
    }
    return mapping.findForward("success");

}
    public ActionForward appelGestionMandats(ActionMapping mapping, 
                                             ActionForm form, 
                                             HttpServletRequest request, 
                                             HttpServletResponse response) throws IOException, 
                                                                                  ServletException {

        ActionMessages actionMessages = new ActionMessages();
        ISearchEngine searchEngine =  (SearchEngine)context.getBean("searchEngine");

        ConsultMandantContratForm consultMandantContratForm =  (ConsultMandantContratForm)form;
        
        paramAgence = (ParamAgence)request.getSession().getAttribute("paramAgBNA");/// structure de l'agent qui fait la saisie
        if (paramAgence.hasError()){
             List listErreur = paramAgence.getErrors();                    
             for (Iterator it = listErreur.iterator(); it.hasNext(); ) {
                 com.oxia.fwk.core.Error erreur = (com.oxia.fwk.core.Error)it.next();
                 ActionMessage actionMessage = new ActionMessage("exception.generique",erreur.getDescription());
                 actionMessages.add("Erreur ", actionMessage);
             }    
             this.saveMessages(request, actionMessages);
             return mapping.findForward("error");
        }

         consultMandantContratForm.setMyTypeStructure(paramAgence.getCodTstrcTstrc());
         consultMandantContratForm.setCodStrcMand(paramAgence.getCodStrcStrc());
       
        if (consultMandantContratForm.getCodeTraitement().equalsIgnoreCase("I")) { 
            /* insertion */
            reset(consultMandantContratForm);
            List index = consultMandantContratForm.getIndexContratChoisis();
            consultMandantContratForm.getListContratChoisis().clear();
            for (int i = 0; i < index.size(); i++) {
                if (!index.get(i).equals("")) {
                    String vContratChoisi = (String)index.get(i);
                    String vcodStrcStrc = "";
                    String vcodPrdPrd = "";
                    String vnumCcptCcpt = "";
                    vcodStrcStrc = vContratChoisi.substring(0, vContratChoisi.indexOf("_"));
                    vcodPrdPrd = vContratChoisi.substring(vContratChoisi.indexOf("_") + 
                                                     1, 
                                                     vContratChoisi.indexOf("*"));
                    vnumCcptCcpt = 
                            vContratChoisi.substring(vContratChoisi.indexOf("*") + 
                                                     1, 
                                                     vContratChoisi.length());

                    ContratCptId cptkey = 
                        new ContratCptId(Long.valueOf(vnumCcptCcpt), 
                                         Long.valueOf(vcodStrcStrc), 
                                         Long.valueOf(vcodPrdPrd));
                    ContratCpt cpt = (ContratCpt)searchEngine.get(ContratCpt.class, cptkey);
                    consultMandantContratForm.getListContratChoisis().add(cpt);
                    consultMandantContratForm.setDatCreMand(DateHandler.dateToStr( new Date()));
                    consultMandantContratForm.setDatDebMand(DateHandler.dateToStr( new Date()));
                    
                    
                    
                    /*-----------------05/08/2010----------------*/
                     if (cpt.getClient().getTypePers().getCodTperTper().equals(Constants.PERSMORALE)) {
                         consultMandantContratForm.setPersMorale("true");
                         String rue="";
                         String cite="";
                         String imm="";
                         String codp="";
                         consultMandantContratForm.setNomNomPers(cpt.getClient().getPersonne().getNomRsPers());
                         consultMandantContratForm.setNomPrnPers(cpt.getClient().getPersonne().getLibSiglPers());
                         if (cpt.getClient().getPersonne().getMontCapPers()!=null){
                         consultMandantContratForm.setCapitalSoc(StrHandler.formatmnt(Math.abs(cpt.getClient().getPersonne().getMontCapPers())));
                         }else{
                             consultMandantContratForm.setCapitalSoc("0");
                         }
                         consultMandantContratForm.setFormeJur(cpt.getClient().getPersonne().getFormeJuridique().getLibFjFj());
                         if (cpt.getAdresseCorresp().getImmeuble()!=null){imm=cpt.getAdresseCorresp().getImmeuble();}
                         if (cpt.getAdresseCorresp().getRue()!=null){rue=cpt.getAdresseCorresp().getRue();}
                         if (cpt.getAdresseCorresp().getCite()!=null){cite=cpt.getAdresseCorresp().getCite();}
                         if (cpt.getAdresseCorresp().getCodCpCp()!=null){codp=cpt.getAdresseCorresp().getCodCpCp();}
                         consultMandantContratForm.setSiegeSoc(imm+" "+rue+" "+cite+" "+codp);
                         ParamListPersonneQualiteClientVo paramVo = new ParamListPersonneQualiteClientVo();
                         paramVo.setNumSeqPers(cpt.getClient().getPersonne().getNumSeqPers());
                         paramVo.setCodQualQual(Long.valueOf(Constants.COD_QUAL_ACTIONNAIRE));
                         GetPersonneClientQualiteCmd getPersonneClientQualiteCmd = new GetPersonneClientQualiteCmd();
                         paramVo = (ParamListPersonneQualiteClientVo)getPersonneClientQualiteCmd.execute(paramVo);
                         if (!paramVo.hasError()) {
                             if (paramVo.getListePersonneClient() != null && 
                                 (paramVo.getListePersonneClient().size() > 0)) {
                                 List listeDesPersonnes = new ArrayList();
                               
                                 
                                 for (Iterator it = 
                                      paramVo.getListePersonneClient().iterator(); 
                                      it.hasNext(); ) {
                                     PersClient persClient = (PersClient)it.next();
                                     PersonneClientView personneClientView = 
                                         new PersonneClientView();
                                     personneClientView.setNumSeqPers(persClient.getPersonne().getNumSeqPers());
                                     personneClientView.setCodTpceTpce(persClient.getPersonne().getTypePiece().getCodTpceTpce());
                                     personneClientView.setNumPcePers(persClient.getPersonne().getNumPcePers());
                                     personneClientView.setNomNomPers(persClient.getPersonne().getNomNomPers());
                                     personneClientView.setNomPrnPers(persClient.getPersonne().getNomPrnPers());
                                     if (persClient.getTauxPartPecl() != null) {
                                         personneClientView.setTauxPartPect(persClient.getTauxPartPecl());
                                     }

                                     listeDesPersonnes.add(personneClientView);
                                 }

                                 consultMandantContratForm.setListAssocies(listeDesPersonnes);
                             }/*else if ((contratCpt.getClient().getPersonne().getFormeJuridique().equals(Long.valueOf(30)))&&
                                       (!consultationMandatForm.getCodeTraitement().equalsIgnoreCase("I"))&&
                                       (!consultationMandatForm.getCodeTraitement().equalsIgnoreCase("SA"))&&
                                       (!consultationMandatForm.getCodeTraitement().equalsIgnoreCase("SR"))&&
                                       (!consultationMandatForm.getCodeTraitement().equalsIgnoreCase("SM")))  { 
                                       
                                 consultationMandatForm.setAlertAssocié("false");     
                             
                             }*/
                            
                             
                         } 
                         
                         
                     }
                    
                    
                }
            }
        } else { /* PréValidation + Validation */
            
            ConsultationMandatForm consultationMandatForm=new ConsultationMandatForm();
            
            if (consultMandantContratForm.getCodeTraitement().equalsIgnoreCase("MA")) {/// affichage pour annulation

                reset(consultMandantContratForm);
                /* afficher le mandat apres la modification */
                Mandat newMandMaj=new Mandat();
                
                if (consultMandantContratForm.getNumMandMand()!=null){
                newMandMaj.setNumMandMand(consultMandantContratForm.getNumMandMand());
                //newMandMaj.setCodEtatMand(consultMandantContratForm.getCodEtatMand());
                GetMandatCmd getMandatCmd =   new GetMandatCmd();
                consultMandantContratForm.setMandat((Mandat)getMandatCmd.execute(newMandMaj));

                DetailMandat detailMandat = new DetailMandat();
                GetDetailMandatCmd getDetailMandatCmd =   new GetDetailMandatCmd();
                detailMandat = (DetailMandat)getDetailMandatCmd.execute(consultMandantContratForm.getMandat());
   
                if (detailMandat.hasError()){
                    List listErreur = detailMandat.getErrors();                    
                    for (Iterator it = listErreur.iterator(); it.hasNext(); ) {
                        com.oxia.fwk.core.Error erreur = (com.oxia.fwk.core.Error)it.next();
                        ActionMessage actionMessage = new ActionMessage("exception.generique",erreur.getDescription());
                        actionMessages.add("Erreur ", actionMessage);
                    }    
                    this.saveMessages(request, actionMessages);
                    return mapping.findForward("error");
                }
                consultMandantContratForm.setCodTypMand(consultMandantContratForm.getMandat().getCodTypMand());
                consultMandantContratForm.setDatDebMand(DateHandler.dateToStr(consultMandantContratForm.getMandat().getDatDebMand()));
                consultMandantContratForm.setDatFinMand(DateHandler.dateToStr(consultMandantContratForm.getMandat().getDatFinMand()));
                consultMandantContratForm.setCodSignMand(consultMandantContratForm.getMandat().getCodSignMand());
                consultMandantContratForm.setNbrMinMand(consultMandantContratForm.getMandat().getNbrMinMand());
                consultMandantContratForm.setCodEtatMand(consultMandantContratForm.getMandat().getCodEtatMand());

                /*ajouter par hatem le 13/10/2009*/
                if (consultMandantContratForm.getMandat().getLibMrejMand()!=null && !consultMandantContratForm.getMandat().getLibMrejMand().equalsIgnoreCase("")){
                        consultMandantContratForm.setTypeValidation("R");
                }
                if (consultMandantContratForm.getMandat().getLibMrsvMand()!=null && !consultMandantContratForm.getMandat().getLibMrsvMand().equalsIgnoreCase("")){
                        consultMandantContratForm.setTypeValidation("VR");
                }
                consultMandantContratForm.setMotifRejet(consultMandantContratForm.getMandat().getLibMrejMand());
                consultMandantContratForm.setMotifReserve(consultMandantContratForm.getMandat().getLibMrsvMand());
                consultMandantContratForm.setObservation(consultMandantContratForm.getMandat().getLibObsMand());
                /////
                consultMandantContratForm.setListMandatOperation(detailMandat.getListeMandatOperations());
                consultMandantContratForm.setListMandatPersonne(detailMandat.getListeMandatPersonnes());

                consultMandantContratForm.setCodeTraitement(tempCodeTraitement);
                }
            }else{
            
            
            
            /// Validation
                reset(consultMandantContratForm);
                
                Long vMandatChoisi =  Long.valueOf((String)request.getParameter("numand"));
                /* importation de la formbean de la consultation */
                consultationMandatForm = (ConsultationMandatForm)request.getSession().getAttribute("consultationMandatForm");
                consultMandantContratForm.setCodeTraitement(consultationMandatForm.getCodeTraitement());
                consultMandantContratForm.getListContratChoisis().clear(); 
                consultMandantContratForm.setNumMandMand(vMandatChoisi);
            
            
                DetailMandat detailMandat = new DetailMandat();
            
                for (Iterator it = consultationMandatForm.getListeMandatAvalider().iterator(); it.hasNext(); ) {
                    MandatPersonneMandat mandatPersonneMandat=(MandatPersonneMandat) it.next();
                    Mandat mandat1 = mandatPersonneMandat.getMandat();
                    if (mandat1.getNumMandMand().longValue() ==  consultMandantContratForm.getNumMandMand().longValue()) {
    
                        GetDetailMandatCmd getDetailMandatCmd =   new GetDetailMandatCmd();
                        detailMandat = (DetailMandat)getDetailMandatCmd.execute(mandat1);
                        
                        if(detailMandat.hasError()){
                            List listErreur = detailMandat.getErrors();                    
                            for (Iterator it1 = listErreur.iterator(); it1.hasNext(); ) {
                                com.oxia.fwk.core.Error erreur = (com.oxia.fwk.core.Error)it1.next();
                                ActionMessage actionMessage = new ActionMessage("exception.generique",erreur.getDescription());
                                actionMessages.add("Erreur ", actionMessage);
                            }    
                            this.saveMessages(request, actionMessages);
                            return mapping.findForward("error");
                        }
                        /* transferer les données de la formebean de la consultation vers la formbean de la modification */
                        consultMandantContratForm.setMandat(mandat1);

 //                       consultMandantContratForm.setTypPcePers(new Long(consultationMandatForm.getTypPcePers()));
                        consultMandantContratForm.setTypPcePers(Constants.COD_CIN);

                        GetPersonneByNumSeqPersCmd getPersonneByNumSeqPersCmd =new GetPersonneByNumSeqPersCmd();
                        Personne pers = new Personne();
                        pers.setNumSeqPers(mandat1.getContratCpt().getClient().getNumSeqPers());
                        pers = (Personne)getPersonneByNumSeqPersCmd.execute(pers);

                        consultMandantContratForm.setNumPcePce(pers.getNumPcePers());//  consultationMandatForm.getNumPcePers());
                         if (pers.getTypePiece().getCodTpceTpce().equals(Constants.COD_RCS)) { /* cas RCS affichage de libSiglPers */
                             consultMandantContratForm.setNomNomPers(pers.getLibSiglPers());
                             consultMandantContratForm.setNomPrnPers(pers.getNomRsPers());
                         } else {
                             consultMandantContratForm.setNomNomPers(pers.getNomNomPers());
                             consultMandantContratForm.setNomPrnPers(pers.getNomPrnPers());
                         }
                        /*correction par hatem affichage nom cas rcs*/
                        /*if (pers.getNomNomPers() != null){consultMandantContratForm.setNomNomPers(pers.getNomNomPers());}
                        else { consultMandantContratForm.setNomNomPers(pers.getLibSiglPers());}
                        if (pers.getNomPrnPers() != null){consultMandantContratForm.setNomPrnPers(pers.getNomPrnPers());}
                        else {consultMandantContratForm.setNomPrnPers(pers.getNomRsPers());}*/
                        consultMandantContratForm.setCodTypMand(mandat1.getCodTypMand());
                        consultMandantContratForm.setDatCreMand(DateHandler.dateToStr(mandat1.getDatCreMand()));
                        consultMandantContratForm.setDatDebMand(DateHandler.dateToStr(mandat1.getDatDebMand()));
                        consultMandantContratForm.setDatFinMand(DateHandler.dateToStr(mandat1.getDatFinMand()));
                        consultMandantContratForm.setCodSignMand(mandat1.getCodSignMand());
                        consultMandantContratForm.setNbrMinMand(mandat1.getNbrMinMand());
                        consultMandantContratForm.setCodEtatMand(mandat1.getCodEtatMand());
                        consultMandantContratForm.setNumDemMand(mandat1.getNumDemMand());
                        consultMandantContratForm.setDatJustMand(DateHandler.dateToStr(mandat1.getDatJustMand()));
                        consultMandantContratForm.setDatEnvcMand(DateHandler.dateToStr(mandat1.getDatEnvcMand()));
                        consultMandantContratForm.setDatValcMand(DateHandler.dateToStr(mandat1.getDatValcMand()));
                        consultMandantContratForm.setCodStrcMand(mandat1.getCodStrcMand());
                        consultMandantContratForm.setNumRdjMand(pers.getNumDosJur());
                        consultMandantContratForm.setNumPereMand(mandat1.getNumPereMand());
                        consultMandantContratForm.setDatEnvmMand(DateHandler.dateToStr(mandat1.getDatEnvmMand()));
                        consultMandantContratForm.setDatValmMand(DateHandler.dateToStr(mandat1.getDatValmMand()));
                        ///*** suite a l'avenant de la DAJ
                        consultMandantContratForm.setMotifReserve(mandat1.getLibMrsvMand());
                        consultMandantContratForm.setMotifRejet(mandat1.getLibMrejMand());
                        consultMandantContratForm.setObservation(mandat1.getLibObsMand());
                        if (consultMandantContratForm.getMotifRejet()!=null && !consultMandantContratForm.getMotifRejet().equalsIgnoreCase("")){
                            consultMandantContratForm.setTypeValidation("R");
                        }
                        if (consultMandantContratForm.getMotifReserve()!=null && !consultMandantContratForm.getMotifReserve().equalsIgnoreCase("")){
                            consultMandantContratForm.setTypeValidation("VR");
                        }
                        
                        consultMandantContratForm.setListMandatOperation(detailMandat.getListeMandatOperations());
                        consultMandantContratForm.setListMandatPersonne(detailMandat.getListeMandatPersonnes());
                        consultMandantContratForm.getListContratChoisis().add(mandat1.getContratCpt());
                    }
                }
            }


            /*ajouter par hatem le 04/08/2010*/
            ContratCpt cpt0 = (ContratCpt)searchEngine.get(ContratCpt.class, consultMandantContratForm.getMandat().getContratCpt().getContratCptId());

             if (cpt0.getClient().getTypePers().getCodTperTper().equals(Constants.PERSMORALE)) {
                 consultMandantContratForm.setPersMorale("true");
                 String rue="";
                 String cite="";
                 String imm="";
                 String codp="";


                 consultMandantContratForm.setNomNomPers(cpt0.getClient().getPersonne().getNomRsPers());
                 consultMandantContratForm.setNomPrnPers(cpt0.getClient().getPersonne().getLibSiglPers());
                 if (cpt0.getClient().getPersonne().getMontCapPers()!=null){
                 consultMandantContratForm.setCapitalSoc(StrHandler.formatmnt(Math.abs(cpt0.getClient().getPersonne().getMontCapPers())));
                 }else{
                     consultMandantContratForm.setCapitalSoc("0");
                 }
                 consultMandantContratForm.setFormeJur(cpt0.getClient().getPersonne().getFormeJuridique().getLibFjFj());
                 if (cpt0.getAdresseCorresp().getImmeuble()!=null){imm=cpt0.getAdresseCorresp().getImmeuble();}
                 if (cpt0.getAdresseCorresp().getRue()!=null){rue=cpt0.getAdresseCorresp().getRue();}
                 if (cpt0.getAdresseCorresp().getCite()!=null){cite=cpt0.getAdresseCorresp().getCite();}
                 if (cpt0.getAdresseCorresp().getCodCpCp()!=null){codp=cpt0.getAdresseCorresp().getCodCpCp();}
                 consultMandantContratForm.setSiegeSoc(imm+" "+rue+" "+cite+" "+codp);
                 ParamListPersonneQualiteClientVo paramVo = new ParamListPersonneQualiteClientVo();
                 paramVo.setNumSeqPers(cpt0.getClient().getPersonne().getNumSeqPers());
                 paramVo.setCodQualQual(Long.valueOf(Constants.COD_QUAL_ACTIONNAIRE));
                 GetPersonneClientQualiteCmd getPersonneClientQualiteCmd = new GetPersonneClientQualiteCmd();
                 paramVo = (ParamListPersonneQualiteClientVo)getPersonneClientQualiteCmd.execute(paramVo);
                 if (!paramVo.hasError()) {
                     if (paramVo.getListePersonneClient() != null && 
                         (paramVo.getListePersonneClient().size() > 0)) {
                         List listeDesPersonnes = new ArrayList();
                       
                         
                         for (Iterator it = 
                              paramVo.getListePersonneClient().iterator(); 
                              it.hasNext(); ) {
                             PersClient persClient = (PersClient)it.next();
                             PersonneClientView personneClientView = 
                                 new PersonneClientView();
                             personneClientView.setNumSeqPers(persClient.getPersonne().getNumSeqPers());
                             personneClientView.setCodTpceTpce(persClient.getPersonne().getTypePiece().getCodTpceTpce());
                             personneClientView.setNumPcePers(persClient.getPersonne().getNumPcePers());
                             personneClientView.setNomNomPers(persClient.getPersonne().getNomNomPers());
                             personneClientView.setNomPrnPers(persClient.getPersonne().getNomPrnPers());
                             if (persClient.getTauxPartPecl() != null) {
                                 personneClientView.setTauxPartPect(persClient.getTauxPartPecl());
                             }

                             listeDesPersonnes.add(personneClientView);
                         }

                         consultMandantContratForm.setListAssocies(listeDesPersonnes);
                     }               
                 }
             }            
             
             /*---------------------*/
        } 
        /// Create a new datagrid (of Mandataire);
        Datagrid lc_datagrid = Datagrid.getInstance();
        lc_datagrid.setData(new ArrayList());
        lc_datagrid.setDataClass(Mandataire.class);
        /// Create a new datagrid  (of MandatOperationGrid);
        Datagrid l_datagrid = Datagrid.getInstance();
        l_datagrid.setData(new ArrayList());
        l_datagrid.setDataClass(MandatOperationGrid.class);

        if (!consultMandantContratForm.getCodeTraitement().equalsIgnoreCase("I") ) { /* modification : chargement des données */
            ArrayList l = new ArrayList();
            l.clear();
            /* convertir les mandatspersonnes (de la base) en mandataire (classe composant le datagrid) */
            for (Iterator it = consultMandantContratForm.getListMandatPersonne().iterator(); it.hasNext(); ) {
                MandatPersonne mandatPersonne = (MandatPersonne)it.next();
                Mandataire mandataire = new Mandataire();
                mandataire.setCodTpceTpce(mandatPersonne.getPersonne().getTypePiece().getCodTpceTpce());
                mandataire.setNumPcePers(mandatPersonne.getPersonne().getNumPcePers());
                mandataire.setNumSeqPers(mandatPersonne.getMandatPersonneId().getNumSeqPers());
                mandataire.setNom(mandatPersonne.getPersonne().getNomNomPers());
                mandataire.setPrenom(mandatPersonne.getPersonne().getNomPrnPers());
                mandataire.setQualite(mandatPersonne.getLibQualMp());

                l.add(mandataire);
            }
            lc_datagrid.setData(l);

            ArrayList ll = new ArrayList();
            ll.clear();
            /* convertir les mandatsOperation (de la base) en mandatOperationGrid (classe composant le datagrid) */
           for (Iterator it = consultMandantContratForm.getListMandatOperation().iterator(); it.hasNext(); ) {
                MandatOperation mandatOperation = (MandatOperation)it.next();
                MandatOperationGrid mandatOperationGrid = new MandatOperationGrid();
                
                mandatOperationGrid.setNumMaopMaop(mandatOperation.getMandatOperationId().getNumMaopMaop());
                mandatOperationGrid.setCodOperOper(mandatOperation.getOperation().getCodOperOper());
                mandatOperationGrid.setLibOperOper(mandatOperation.getOperation().getLibOperOper());
                mandatOperationGrid.setNumPereMaop(mandatOperation.getNumPereMaop());

                if (mandatOperation.getMontMlimMaop()==null ){mandatOperation.setMontMlimMaop(Long.valueOf(0));}
                mandatOperationGrid.setMontMlimMaop(StrHandler.formatmnt(mandatOperation.getMontMlimMaop()));
                if (mandatOperation.getMontElimMaop()==null ){mandatOperation.setMontElimMaop(Long.valueOf(0));}
                mandatOperationGrid.setMontElimMaop(StrHandler.formatmnt(mandatOperation.getMontElimMaop()));
                if (mandatOperation.getMontUtilMaop()==null ){mandatOperation.setMontUtilMaop(Long.valueOf(0));}
                mandatOperationGrid.setMontUtilMaop(StrHandler.formatmnt(mandatOperation.getMontUtilMaop()));
                mandatOperationGrid.setDatDperMaop(DateHandler.dateToStr(mandatOperation.getDatDperMaop()));
                mandatOperationGrid.setCodPerMaop(mandatOperation.getCodPerMaop());
                mandatOperationGrid.setCodSignMaop(mandatOperation.getCodSignMaop());
                if (mandatOperationGrid.getCodSignMaop().equalsIgnoreCase("C")){
                    mandatOperationGrid.setNbrMinMaop(mandatOperation.getNbrMinMaop());
                }
                ll.add(mandatOperationGrid);
            }
            l_datagrid.setData(ll);

        
        }
        consultMandantContratForm.setListMandatPersonneGrid(lc_datagrid);
        consultMandantContratForm.setListMandatOperationGrid(l_datagrid);

    
    
    return mapping.findForward("detMand");

    }

    private void reset(ConsultMandantContratForm consultMandantContratForm) {
        consultMandantContratForm.setCodTypMand("G");
        consultMandantContratForm.setDatDebMand(DateHandler.dateToStr(null));
        consultMandantContratForm.setDatFinMand(DateHandler.dateToStr(null));
        consultMandantContratForm.setCodSignMand("S");
        consultMandantContratForm.setNbrMinMand(null);
        consultMandantContratForm.setMotifRejet("");
        consultMandantContratForm.setMotifReserve("");
        consultMandantContratForm.setTypeValidation("V");
        consultMandantContratForm.setDatJustMand(null);
        consultMandantContratForm.setDatEnvcMand(null);
        consultMandantContratForm.setDatValcMand(null);
        consultMandantContratForm.setNumRdjMand(null);
        
        consultMandantContratForm.getListMandatOperation().clear();
        consultMandantContratForm.getListMandatPersonne().clear();
    }


    public ActionForward validation(ActionMapping mapping, ActionForm form, 
                                    HttpServletRequest request, 
                                    HttpServletResponse response) throws IOException, 
                                                                         ServletException {

        paramAgence = (ParamAgence)request.getSession().getAttribute("paramAgBNA");/// structure de l'agent qui fait la saisie
        String forward = "";        
        ActionMessages actionMessages = new ActionMessages();
        ConsultMandantContratForm consultMandantContratForm = (ConsultMandantContratForm)form;
        ValueObject vo = new ValueObject();        
       
            for (Iterator itContrat = consultMandantContratForm.getListContratChoisis().iterator(); itContrat.hasNext(); ) {
                ContratCpt contratCpt = (ContratCpt)itContrat.next();
                vo = insertModifMand(consultMandantContratForm, contratCpt, mapping, request);
                
                        if (vo.hasError()){
                            List listErreur = vo.getErrors();                    
                            for (Iterator it = listErreur.iterator(); it.hasNext(); ) {
                                com.oxia.fwk.core.Error erreur = (com.oxia.fwk.core.Error)it.next();
                                ActionMessage actionMessage = new ActionMessage("exception.generique",erreur.getDescription());
                                actionMessages.add("Erreur ", actionMessage);
                            }    
                            this.saveMessages(request, actionMessages);
                            return mapping.findForward("error");
                        }
                    
            }
         
            /*** affichage apres modification ***/
           if (consultMandantContratForm.getCodeTraitement().equalsIgnoreCase("I")||consultMandantContratForm.getCodeTraitement().equalsIgnoreCase("SM")) { 
                request.getSession().removeAttribute("consultationMandatForm");
                  if(!consultMandantContratForm.getCasSouscriptionContratCompte().equals("")){
                      SouscriptionContratCompteForm souscriptionContratCompteForm = new SouscriptionContratCompteForm();
                      Mandat m = (Mandat)vo;
                       StringBuffer message = new StringBuffer(
                              "La demande de souscription numéro  " +
                              StrHandler.lpad(m.getContratCpt().getContratCptId().getNumCcptCcpt().toString(), 
                                              '0', 6) + " au nom de " + 
                              consultMandantContratForm.getNomNomPers() + " " + 
                              consultMandantContratForm.getNomPrnPers() + 
                              " a été crée avec succès et en attente de validation par le chef d'agence.");
                              request.getSession().removeAttribute("paramSouscriptionMandat");
                      ActionMessage actionMessage = new ActionMessage("exception.generique",message.toString());
                      actionMessages.add("Msg_validation ", actionMessage);
                      this.saveMessages(request, actionMessages);                                 
                      forward = "confirmationGenerale";        
                      
                  }else  forward = "sortie"; 
              }else {
                //request.getSession().invalidate();
                
                Iterator itContrat = consultMandantContratForm.getListContratChoisis().iterator();
                ContratCpt contratCpt = (ContratCpt)itContrat.next();
                ContratCptId cptid = contratCpt.getContratCptId();
                imprimerMandat(consultMandantContratForm.getNumMandMand(),request,cptid.getCodStrcStrc().toString(), consultMandantContratForm.getCodeTraitement(),consultMandantContratForm.getTypeValidation());
              
                request.getSession().removeAttribute("consultationMandatForm");
                forward = "confirmationMand";
              }
        return mapping.findForward(forward);
    }

private void imprimerMandat(Long numMand,HttpServletRequest request, String struct, String codeTraitement, String typeValid){
    try {    
        CommonReportVO valueObject = new CommonReportVO();
        Map parameters = new HashMap();
        ParamAgence paramAgence = new ParamAgence();
        paramAgence = (ParamAgence)request.getSession().getAttribute("paramAgBNA");  
        parameters.put("P_NUM_MATR_USER",paramAgence.getNumMatrUser().toString());
     // libellé opération, prévalidation création, renouvel...
     if(codeTraitement.equalsIgnoreCase("P")){
             if (typeValid.equalsIgnoreCase("V")){ 
             /// validation
                parameters.put("P_LIB_ETAT","Prévalidation Création Mandat");
               } if(typeValid.equalsIgnoreCase("VR")){ /// validation avec reserve
                  parameters.put("P_LIB_ETAT","Prévalidation Création Mandat avec Réserve");
                  }if(typeValid.equalsIgnoreCase("R")){  /// rejet
                       parameters.put("P_LIB_ETAT","Rejet Prévalidation Création Mandat");
                      }
        
         }else if(codeTraitement.equalsIgnoreCase("V")){
                 if (typeValid.equalsIgnoreCase("V")){ 
                 /// validation
                    parameters.put("P_LIB_ETAT","Validation Création Mandat");
                   } if(typeValid.equalsIgnoreCase("VR")){ /// validation avec reserve
                      parameters.put("P_LIB_ETAT","Validation Création Mandat avec Réserve");
                      }if(typeValid.equalsIgnoreCase("R")){  /// rejet
                           parameters.put("P_LIB_ETAT","Rejet Création Mandat");
                          }              
             }else if(codeTraitement.equalsIgnoreCase("PM")){
                     if (typeValid.equalsIgnoreCase("V")){ 
                     /// validation
                        parameters.put("P_LIB_ETAT","Prévalidation Modification Mandat");
                       } if(typeValid.equalsIgnoreCase("VR")){ /// validation avec reserve
                          parameters.put("P_LIB_ETAT","Prévalidation Modification Mandat avec Réserve");
                          }if(typeValid.equalsIgnoreCase("R")){  /// rejet
                               parameters.put("P_LIB_ETAT","Rejet Prévalidation Modification Mandat");
                              }  
                
                 }else if(codeTraitement.equalsIgnoreCase("VM")){
                       if (typeValid.equalsIgnoreCase("V")){   /// validation
                            parameters.put("P_LIB_ETAT","Validation Modification Mandat");
                           } if(typeValid.equalsIgnoreCase("VR")){ /// validation avec reserve
                                  parameters.put("P_LIB_ETAT","Validation Modification Mandat avec Réserve");
                             }if(typeValid.equalsIgnoreCase("R")){  /// rejet
                                      parameters.put("P_LIB_ETAT","Rejet Modification Mandat");
                                    }                                            
                   } 
         
        parameters.put("P_COD_STRC_STRC",struct);
        if(codeTraitement.equalsIgnoreCase("V") || codeTraitement.equalsIgnoreCase("VM")){
            parameters.put("STRC_VALID_MAND",struct);
        }else {
            parameters.put("STRC_VALID_MAND",paramAgence.getCodStrcStrc().toString());
        }
        
     
                parameters.put("P_NUM_MAND_MAND",numMand.toString());
                valueObject.setNomReport("mandatNumMand");
        
        valueObject.setParams(parameters);
        
        request.getSession().setAttribute("CommonPrintVo",valueObject);
        request.setAttribute("print","1");
    } catch (Exception e) {
        logger.error(e.getMessage());
        throw new RuntimeException(e);
    }     
}
    private ValueObject insertModifMand(ConsultMandantContratForm consultMandantContratForm, 
                                 ContratCpt contratCpt,ActionMapping mapping, HttpServletRequest request) {
        Mandat mandat = new Mandat();
        ParamInsertContrat paramInsertContrat = (ParamInsertContrat)request.getSession().getAttribute("paramSouscriptionMandat");/// structure de l'agent qui fait la saisie
        ParamInsertMandat  paramInsertMandat = new ParamInsertMandat();
        if(paramInsertContrat != null){
            paramInsertMandat.setParamInsertContrat(paramInsertContrat);  
        }
        
        mandat.getMandatPersonnes().clear();
        mandat.getMandatOperations().clear();
        /*----------------------------------------*/
        /* Garnir les mandatPersonne dans mandat  */
        /*----------------------------------------*/

        Collection dgAdd = 
            consultMandantContratForm.getListMandatPersonneGrid().getDataWithState("");
        Collection dgSel = 
            consultMandantContratForm.getListMandatPersonneGrid().getDataWithState("selected");
        Collection listMandatPers = new ArrayList();
        listMandatPers.addAll(dgAdd);
        listMandatPers.addAll(dgSel);

        for (Iterator it = listMandatPers.iterator(); it.hasNext(); ) {
            MandatPersonne mandatPersonneTemp = new MandatPersonne();
            Mandataire mandataire = (Mandataire)it.next();
            if (mandataire.getNumPcePers() != "" && mandataire.getNumPcePers() != null && mandataire.getNumSeqPers() != null) { // verifier si les données obligatoires sont saisies
                MandatPersonneId mandatPersonneId = new MandatPersonneId();
                mandatPersonneId.setNumMandMand(consultMandantContratForm.getNumMandMand()); 
                mandatPersonneId.setNumSeqPers(mandataire.getNumSeqPers());
                mandatPersonneTemp.setMandatPersonneId(mandatPersonneId);
                mandatPersonneTemp.setLibQualMp(mandataire.getQualite());
                mandatPersonneTemp.setCodEtatMp("V");
                if (mandataire.getNumSeqPers() != null && !mandataire.getNumSeqPers().equals(Long.valueOf(0))){
                mandat.getMandatPersonnes().add(mandatPersonneTemp);
                }
            }
        }

        /*----------------------------------------*/
        /* Garnir les mandatOperation dans mandat */
        /*----------------------------------------*/
        if (consultMandantContratForm.getCodTypMand().equalsIgnoreCase("S") || consultMandantContratForm.getCodTypMand().equalsIgnoreCase("JS")) {
            Collection dgmAdd = 
                consultMandantContratForm.getListMandatOperationGrid().getDataWithState("");
            Collection dgmSel = 
                consultMandantContratForm.getListMandatOperationGrid().getDataWithState("selected");
            Collection listMandatOper = new ArrayList();
            listMandatOper.addAll(dgmAdd);
            listMandatOper.addAll(dgmSel);

            for (Iterator it = listMandatOper.iterator(); it.hasNext(); ) {
                MandatOperation mandatOperationTemp = new MandatOperation();
                MandatOperationGrid mandatOperationGrid = 
                    (MandatOperationGrid)it.next();
                MandatOperationId mandatOperationId = new MandatOperationId();

                mandatOperationId.setCodOperOper(mandatOperationGrid.getCodOperOper());
                mandatOperationId.setNumMaopMaop(mandatOperationGrid.getNumMaopMaop());
                mandatOperationId.setNumMandMand(consultMandantContratForm.getNumMandMand());
                mandatOperationTemp.setMandatOperationId(mandatOperationId);
                mandatOperationTemp.setCodPerMaop(mandatOperationGrid.getCodPerMaop());
                mandatOperationTemp.setCodSignMaop(mandatOperationGrid.getCodSignMaop());
                mandatOperationTemp.setDatDperMaop(DateHandler.strToDate(mandatOperationGrid.getDatDperMaop()));
                mandatOperationTemp.setDatFinMaop(DateHandler.strToDate(mandatOperationGrid.getDatFinMaop()));
                mandatOperationTemp.setDatDebMaop(new Date());
                mandatOperationTemp.setNbrMinMaop(mandatOperationGrid.getNbrMinMaop());
                mandatOperationTemp.setNumPereMaop(mandatOperationGrid.getNumPereMaop());
                
               if (mandatOperationGrid.getMontElimMaop()!=null && !mandatOperationGrid.getMontElimMaop().equalsIgnoreCase("")){
                mandatOperationTemp.setMontElimMaop(Long.valueOf(new Double(new Double(StrHandler.strWithoutBlanck(mandatOperationGrid.getMontElimMaop())).doubleValue()*1000).longValue()));
               }else mandatOperationTemp.setMontMlimMaop(Long.valueOf(0));
               if (mandatOperationGrid.getMontMlimMaop()!=null && !mandatOperationGrid.getMontMlimMaop().equalsIgnoreCase("")){
                mandatOperationTemp.setMontMlimMaop(Long.valueOf(new Double(new Double(StrHandler.strWithoutBlanck(mandatOperationGrid.getMontMlimMaop())).doubleValue()*1000).longValue()));
               }else mandatOperationTemp.setMontMlimMaop(Long.valueOf(0));
               if (mandatOperationGrid.getMontUtilMaop()!=null && !mandatOperationGrid.getMontUtilMaop().equalsIgnoreCase("")){
                mandatOperationTemp.setMontUtilMaop(Long.valueOf(new Double(new Double(StrHandler.strWithoutBlanck(mandatOperationGrid.getMontUtilMaop())).doubleValue()*1000).longValue()));
               }else mandatOperationTemp.setMontUtilMaop(Long.valueOf(0));
                if (mandatOperationGrid.getCodOperOper()!=null && !mandatOperationGrid.getCodOperOper().equals(Long.valueOf(0))){
                mandat.getMandatOperations().add(mandatOperationTemp);
                }
            }
        }
        mandat.setCodSignMand(consultMandantContratForm.getCodSignMand());
        mandat.setNbrMinMand(consultMandantContratForm.getNbrMinMand());
        mandat.setDatCreMand(DateHandler.strToDate(consultMandantContratForm.getDatCreMand()));
        mandat.setDatDebMand(DateHandler.strToDate(consultMandantContratForm.getDatDebMand()));
        mandat.setDatFinMand(DateHandler.strToDate(consultMandantContratForm.getDatFinMand()));
        mandat.setCodTypMand(consultMandantContratForm.getCodTypMand());
        mandat.setCodEtatMand(consultMandantContratForm.getCodEtatMand());
        mandat.setContratCpt(contratCpt);
        mandat.setDatEnvcMand(DateHandler.strToDate(consultMandantContratForm.getDatEnvcMand()));
        mandat.setDatValcMand(DateHandler.strToDate(consultMandantContratForm.getDatValcMand()));
        mandat.setDatJustMand(DateHandler.strToDate(consultMandantContratForm.getDatJustMand()));
        mandat.setNumDemMand(consultMandantContratForm.getNumDemMand());
        mandat.setNumRdjMand(consultMandantContratForm.getNumRdjMand());
        mandat.setNumPereMand(consultMandantContratForm.getNumPereMand());
        mandat.setDatEnvmMand(DateHandler.strToDate(consultMandantContratForm.getDatEnvmMand()));
        mandat.setDatValmMand(DateHandler.strToDate(consultMandantContratForm.getDatValmMand()));
        
        consultMandantContratForm.setMyTypeStructure(paramAgence.getCodTstrcTstrc());
        consultMandantContratForm.setCodStrcMand(paramAgence.getCodStrcStrc());
        mandat.setCodStrcMand(consultMandantContratForm.getCodStrcMand());
        
        
        /*conversion des string en int pour le swtch*/
        int codTraitement=0;
        String  s=consultMandantContratForm.getCodeTraitement();
         if (s.equalsIgnoreCase("I")){codTraitement=10;}
         if (s.equalsIgnoreCase("P")){codTraitement=11;}
         if (s.equalsIgnoreCase("V")){codTraitement=12;}
         if (s.equalsIgnoreCase("SM")){codTraitement=20;}
         if (s.equalsIgnoreCase("PM")){codTraitement=21;}
         if (s.equalsIgnoreCase("VM")){codTraitement=22;}
    
    
    switch (codTraitement)
    {
    case 10 :  { /* Création */
            if(consultMandantContratForm.getCasSouscriptionContratCompte().equals("mineur")){
                mandat.setCodEtatMand("V");  
                consultMandantContratForm.setTypeStrcConc("A");
                consultMandantContratForm.setMyTypeStructure(paramAgence.getCodTstrcTstrc());
            }else{
                mandat.setCodEtatMand("S");             
             }
            getStructureConcernee(consultMandantContratForm, mandat); //* suite au changement du choix de la structure validatrice le 06/10/2008

            mandat.setNumDemMand(null);
            //mandat.setCodStrcMand(paramAgence.getCodStrcStrc());/// structure concernée
             mandat.setCodStrcMand(consultMandantContratForm.getCodStrcMand());/// structure concernée suite a l'avenant de la DAJ
            paramInsertMandat.setMandat(mandat);
            CreatMandatCmd creatMandatCmd = new CreatMandatCmd();
            Mandat mandatRetour = new Mandat();
            Personnel personnel = new Personnel();
            personnel.setNumMatrUser(paramAgence.getNumMatrUser());
            Structure structure =new Structure();
            structure.setCodStrcStrc(paramAgence.getCodStrcStrc());
            personnel.setStructure(structure);
            paramInsertMandat.setPersonnel(personnel);
            ValueObject vo = (ValueObject)creatMandatCmd.execute(paramInsertMandat);
            consultMandantContratForm.setNumMandMand(mandatRetour.getNumMandMand());/// pour affichage apres insrtion
          //  consultMandantContratForm.setCodStrcMand(paramAgence.getCodStrcStrc());
            consultMandantContratForm.setCodEtatMand("S");
            
            return vo;
        } 
        
    case 20 :  { /* Saisie Modification */
    
            getStructureConcernee(consultMandantContratForm, mandat); //* suite au changement du choix de la structure validatrice le 06/10/2008
            /* MAJ du Mandat en cours de modification */
            mandat.setNumMandMand(consultMandantContratForm.getNumMandMand());
            GetMandatCmd getMandatCmd = new GetMandatCmd();
            Mandat mandatOld=(Mandat)getMandatCmd.execute(mandat);
            mandatOld.setCodEdemMand("M"); /// mandat en cours de modification
            majMandat(consultMandantContratForm, mandatOld);

            //mandat.setCodStrcMand(paramAgence.getCodStrcStrc()); /// structure concernée
            mandat.setCodStrcMand(consultMandantContratForm.getCodStrcMand());/// structure concernée suite a l'avenant de la DAJ
            mandat.setNumPereMand(mandat.getNumMandMand());
            mandat.setCodEtatMand("M");
            mandat.setCodEdemMand("SM");
            
            /* Créer un nouveau mandat */
            Set listMandatOperation =new HashSet();
             for (Iterator it = mandat.getMandatOperations().iterator(); it.hasNext(); ) { /// insertion du num_maop_pere
                 MandatOperation mandatOperation =  (MandatOperation)it.next();
                 mandatOperation.setNumPereMaop(mandatOperation.getMandatOperationId().getNumMaopMaop());
                 listMandatOperation.add(mandatOperation);
             }
            mandat.getMandatOperations().clear();
            mandat.getMandatOperations().addAll(listMandatOperation);
            Personnel personnel=new Personnel();
            Structure structure =new Structure();
            structure.setCodStrcStrc(paramAgence.getCodStrcStrc());
            personnel.setStructure(structure);
            paramInsertMandat.setPersonnel(personnel);
            paramInsertMandat.setMandat(mandat);
            CreationMandatCmd creationMandatCmd = new CreationMandatCmd();
            Mandat mandatRetour = new Mandat();
            mandatRetour = (Mandat)creationMandatCmd.execute(paramInsertMandat);
            
            consultMandantContratForm.setNumMandMand(mandatRetour.getNumMandMand());/// pour affichage apres insrtion
//            consultMandantContratForm.setCodStrcMand(paramAgence.getCodStrcStrc());

            ValueObject v = insertTrace(mandatRetour, Constants.COD_OPER_MODIF_MANDAT, Constants.COD_TACHE_MODIF_MANDAT);
            return v;
        } 
       
    case 11 :{/*Prévalidation*/
        
            ///* getStructureConcernee(consultMandantContratForm, mandat); // suite au changement du choix de la structure validatrice le 06/10/2008
            mandat.setCodEtatMand("A");
            mandat.setDatEnvcMand(new Date());
            consultMandantContratForm.setCodEtatMand("A");
            consultMandantContratForm.setCodeTraitement("P");/// prévalidation
            mandat.setLibMrsvMand(consultMandantContratForm.getMotifReserve());               
            mandat.setLibMrejMand(consultMandantContratForm.getMotifRejet());               
            mandat.setLibObsMand(consultMandantContratForm.getObservation());
            ValueObject v =  majMandatTrace(consultMandantContratForm, mandat, Constants.COD_OPER_CREAT_MANDAT, Constants.COD_TACHE_PREVALID_MANDAT);
           
            return v ;
        }
        
    case 21 :{ /* Prévalidation Modification*/
            
            ///* getStructureConcernee(consultMandantContratForm, mandat); // suite au changement du choix de la structure validatrice le 06/10/2008
            mandat.setCodEtatMand("M");
            consultMandantContratForm.setCodEtatMand("M");
            mandat.setCodEdemMand("AM");
            mandat.setDatEnvmMand(new Date());
            mandat.setLibMrsvMand(consultMandantContratForm.getMotifReserve());               
            mandat.setLibMrejMand(consultMandantContratForm.getMotifRejet());               
            mandat.setLibObsMand(consultMandantContratForm.getObservation());
            ValueObject v = majMandatTrace(consultMandantContratForm, mandat, Constants.COD_OPER_MODIF_MANDAT, Constants.COD_TACHE_PREVMODIF_MANDAT);
            
            return v;
        }
       
    case 12 :{ /* Validation */
                mandat.setDatValcMand(new Date()); /// date validation création
                if (consultMandantContratForm.getTypeValidation().equalsIgnoreCase("V")){   /// validation
                    mandat.setCodEtatMand("V");
                    consultMandantContratForm.setCodEtatMand("V");
                } if(consultMandantContratForm.getTypeValidation().equalsIgnoreCase("VR")){ /// validation avec reserve
                    mandat.setCodEtatMand("V");
                    mandat.setCodEdemMand(Constants.COD_ETAT_MAND_VAL_RES);
                    consultMandantContratForm.setCodEtatMand("V");
                    mandat.setLibMrsvMand(consultMandantContratForm.getMotifReserve());               
                }if(consultMandantContratForm.getTypeValidation().equalsIgnoreCase("R")){  /// rejet
                    mandat.setCodEtatMand("R");
                    consultMandantContratForm.setCodEtatMand("R");
                    mandat.setLibMrejMand(consultMandantContratForm.getMotifRejet());               
                }
                consultMandantContratForm.setCodeTraitement("V");
                //mandat.setCodStrcMand();
                mandat.setLibObsMand(consultMandantContratForm.getObservation());
                mandat.setCodStrcMand(mandat.getContratCpt().getStructure().getCodStrcStrc());/// structure concernée suite a l'avenant de la DAJ
                ValueObject v = majMandatTrace(consultMandantContratForm, mandat, Constants.COD_OPER_CREAT_MANDAT, Constants.COD_TACHE_VALID_MANDAT);
               
                return v;
            }
        case 22 :  { /* Validation Modification */
                mandat.setNumMandMand(consultMandantContratForm.getNumMandMand());
                mandat.setLibMrsvMand(consultMandantContratForm.getMotifReserve());               
                mandat.setLibMrejMand(consultMandantContratForm.getMotifRejet());               
                mandat.setLibObsMand(consultMandantContratForm.getObservation());
                consultMandantContratForm.setCodEtatMand("M");
                Personnel personnel = new Personnel();
                personnel.setNumMatrUser(paramAgence.getNumMatrUser());
                Structure structure =new Structure();
                structure.setCodStrcStrc(paramAgence.getCodStrcStrc());
                personnel.setStructure(structure);
                paramInsertMandat.setPersonnel(personnel);
                mandat.setCodStrcMand(mandat.getContratCpt().getStructure().getCodStrcStrc());/// structure concernée suite a l'avenant de la DAJ
                paramInsertMandat.setTypeValidation(consultMandantContratForm.getTypeValidation());
             
                return (validModifMand(consultMandantContratForm,mandat, paramInsertMandat));
            } 
        default:   {
                return mandat;
            }
    }   
                
 }

    private ValueObject validModifMand(ConsultMandantContratForm consultMandantContratForm,Mandat mandat,ParamInsertMandat paramInsertMandat) {
                                
        ParamModifMandVo paramModifMandVo=new ParamModifMandVo();
        paramModifMandVo.setParamInsertMandat(paramInsertMandat);
        paramModifMandVo.setMandat(mandat);
        paramModifMandVo.setTypevalidation(paramInsertMandat.getTypeValidation());
        ValidModifMandCmd validModifMandCmd=new ValidModifMandCmd();
        ParamModifMandVo paramModifMandVoRetour=(ParamModifMandVo)validModifMandCmd.execute(paramModifMandVo);
        consultMandantContratForm.setNumRdjMand(paramModifMandVoRetour.getMandat().getNumRdjMand());
        return paramModifMandVoRetour;
    }

    private ValueObject insertTrace(Mandat mandatRetour, Long codOper, Long codTache) {

 
        TraceMandat traceMandat = insertionTrace(mandatRetour, codOper, codTache);

        InsertTraceMandatCmd insertTraceMandatCmd=new InsertTraceMandatCmd();
        TraceMandat traceMandatRetour=(TraceMandat)insertTraceMandatCmd.execute(traceMandat);
        return traceMandatRetour;
  
  }

    private TraceMandat insertionTrace(Mandat mandatRetour, Long codOper, 
                                       Long codTache) {
        TraceMandat traceMandat = new TraceMandat();
        traceMandat.setMandat(mandatRetour);
        Personnel personnel = new Personnel();
        Structure structure =new Structure();
        structure.setCodStrcStrc(paramAgence.getCodStrcStrc());
        personnel.setStructure(structure);
        personnel.setNumMatrUser(paramAgence.getNumMatrUser());
        traceMandat.setPersonnel(personnel);
        Operation operation = new Operation();
        operation.setCodOperOper(codOper);

        Tache tache = new Tache();
        TacheId tacheId = new TacheId();
        tacheId.setCodOperOper(codOper);
        tacheId.setCodTachTach(codTache);
        tache.setTacheId(tacheId);
        traceMandat.setTache(tache);
        return traceMandat;
    }
    private ValueObject majMandatTrace(ConsultMandantContratForm consultMandantContratForm,  Mandat mandat, Long codOper, Long codTache) {
    
        TraceMandat traceMandat = insertionTrace(mandat, codOper, codTache);

        mandat.setNumMandMand(consultMandantContratForm.getNumMandMand());
        MiseAJourMandatTraceCmd miseAJourMandatTraceCmd = new MiseAJourMandatTraceCmd();
        ValueObject vo =(ValueObject) miseAJourMandatTraceCmd.execute(traceMandat);
        consultMandantContratForm.setNumRdjMand(traceMandat.getMandat().getNumRdjMand());
        consultMandantContratForm.setNumDemMand(traceMandat.getMandat().getNumDemMand());
        return vo;
    }

    private void majMandat(ConsultMandantContratForm consultMandantContratForm,  Mandat mandat) {
    
        mandat.setNumMandMand(consultMandantContratForm.getNumMandMand());
        mandat.setDatModMand(new Date());
        MiseAJourMandatCmd miseAJourMandatCmd = new MiseAJourMandatCmd();
        Mandat mandatRetour = new Mandat();
        mandatRetour = (Mandat)miseAJourMandatCmd.execute(mandat);
        consultMandantContratForm.setNumDemMand(mandatRetour.getNumDemMand());
    }

    private void getStructureConcernee(ConsultMandantContratForm consultMandantContratForm, Mandat mandat) {

        /* affectation de la structure concernée */
        if(consultMandantContratForm.getMyTypeStructure()==1 ){
            if (consultMandantContratForm.getTypeStrcConc().equalsIgnoreCase("A")){ /// structure concerné : Agence
                mandat.setCodStrcMand(paramAgence.getCodStrcStrc());
                consultMandantContratForm.setCodStrcMand(paramAgence.getCodStrcStrc());
            }else{ if (consultMandantContratForm.getTypeStrcConc().equalsIgnoreCase("R")){ /// structure concerné : Dir Rég
                    mandat.setCodStrcMand(paramAgence.getCodStrmStrc());
                    consultMandantContratForm.setCodStrcMand(paramAgence.getCodStrmStrc());
                    }else { /// structure concerné : DAJur
                     mandat.setCodStrcMand(Constants.COD_STRC_DAJ);
                     consultMandantContratForm.setCodStrcMand(Constants.COD_STRC_DAJ);
                    }
            }
        }else { if (consultMandantContratForm.getMyTypeStructure()==2 ){
                    if (consultMandantContratForm.getTypeStrcConc()!=null && consultMandantContratForm.getTypeStrcConc().equalsIgnoreCase("R")){ /// structure concerné : Dir Rég
                        mandat.setCodStrcMand(paramAgence.getCodStrmStrc());
                        consultMandantContratForm.setCodStrcMand(paramAgence.getCodStrmStrc());
                    }else { /// structure concerné : DAJur
                        mandat.setCodStrcMand(Constants.COD_STRC_DAJ);
                        consultMandantContratForm.setCodStrcMand(Constants.COD_STRC_DAJ);
                        }
                }else {
                mandat.setCodStrcMand(Constants.COD_STRC_DAJ);
                consultMandantContratForm.setCodStrcMand(Constants.COD_STRC_DAJ);
            }
        }
    }


    public ActionForward clearPageConsult(ActionMapping mapping, 
                                          ActionForm form, 
                                          HttpServletRequest request, 
                                          HttpServletResponse response) throws IOException, 
                                                                               ServletException {

        try{
         paramAgence = (ParamAgence)request.getSession().getAttribute("paramAgBNA");/// structure de l'agent qui fait la saisie
         StructureDomaine structureDomaine = new StructureDomaine(paramAgence.getCodStrcStrc(),Constants.COD_DOM_CONTRATCOMPTE);
         boolean bool = SmileUtil.testDomaineOuvert(structureDomaine);
       
            ConsultMandantContratForm consultMandantContratForm = 
                (ConsultMandantContratForm)form;
            consultMandantContratForm.clearForm();
            // consultMandantContratForm.setMyTypeStructure(new String("1"));
            return mapping.findForward("success");
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

    public ActionForward clearPageDetails(ActionMapping mapping, 
                                          ActionForm form, 
                                          HttpServletRequest request, 
                                          HttpServletResponse response) throws IOException, 
                                                                               ServletException {

        ConsultMandantContratForm consultMandantContratForm = 
            (ConsultMandantContratForm)form;
        reset(consultMandantContratForm);

        tempCodeTraitement=consultMandantContratForm.getCodeTraitement(); /// save code traitement avant annulation
        consultMandantContratForm.setCodeTraitement("MA");/// affichage apres annulation

        appelGestionMandats( mapping,consultMandantContratForm,request,response);
        return mapping.findForward("detMand");

    }

    public ActionForward appelGestionMandatsParSouscription(ActionMapping mapping, 
                                                 ActionForm form, 
                                                 HttpServletRequest request, 
                                                 HttpServletResponse response) throws IOException, 
                                                                                      ServletException {
        ActionMessages actionMessages = new ActionMessages();

            ISearchEngine searchEngine = 
                (SearchEngine)context.getBean("searchEngine");
        paramAgence = (ParamAgence)request.getSession().getAttribute("paramAgBNA");/// structure de l'agent qui fait la saisie

            ConsultMandantContratForm consultMandantContratForm = 
                (ConsultMandantContratForm)form;
            /// Create a new datagrid (of Mandataire);
            Datagrid lc_datagrid = Datagrid.getInstance();
            lc_datagrid.setData(new ArrayList());
            lc_datagrid.setDataClass(Mandataire.class);
            lc_datagrid.setData(new ArrayList());  
            
            /// Create a new datagrid  (of MandatOperationGrid);
            Datagrid l_datagrid = Datagrid.getInstance();
            l_datagrid.setData(new ArrayList());
            l_datagrid.setDataClass(MandatOperationGrid.class);            
            l_datagrid.setData(new ArrayList());
            
            consultMandantContratForm.setListMandatPersonneGrid(null);
            consultMandantContratForm.setListMandatOperationGrid(null);
            consultMandantContratForm.getListContratChoisis().clear();
            /// indiquer que c'est le cas d'une création d'un mandat
              consultMandantContratForm.setCodeTraitement("I"); 
              reset(consultMandantContratForm);
            
            ///recupération des parametres transférés par Session     
             ParamInsertContrat paramInsertContrat = (ParamInsertContrat)request.getSession().getAttribute("paramSouscriptionMandat");/// structure de l'agent qui fait la saisie
            
           ///------Affectation des contrats 
             consultMandantContratForm.getListContratChoisis().add(paramInsertContrat.getContratCpt());
           ///------Fin affectation des contrats
                
           ///------Traitement du cas du mineur 
           if (paramInsertContrat.getContratCpt().getClient().getPersonne().getCategoriePersonne().getCodCatpCatp().equals(Constants.COD_CATEGORIE_MINEUR)) {
               
               //------Affectation du client 
               consultMandantContratForm.setNomNomPers(paramInsertContrat.getContratCpt().getClient().getPersonne().getNomNomPers());
               consultMandantContratForm.setNomPrnPers(paramInsertContrat.getContratCpt().getClient().getPersonne().getNomPrnPers());
               consultMandantContratForm.setCasSouscriptionContratCompte("mineur");
                ///------Fin affectation du client
               consultMandantContratForm.setNbrMinMand(Long.valueOf(1));
               Mandataire mandataire = new Mandataire();
               
               if(paramInsertContrat.getPersonneTuteur().getNumSeqPers()!=null){               
               mandataire.setCodTpceTpce(paramInsertContrat.getPersonneTuteur().getTypePiece().getCodTpceTpce());
               mandataire.setNumPcePers(paramInsertContrat.getPersonneTuteur().getNumPcePers());
               mandataire.setNumSeqPers(paramInsertContrat.getPersonneTuteur().getNumSeqPers());
               mandataire.setNom(paramInsertContrat.getPersonneTuteur().getNomNomPers());
               mandataire.setPrenom(paramInsertContrat.getPersonneTuteur().getNomPrnPers()); 
               }else{
                   InsertPersonneCmd insertPersonneCmd = new InsertPersonneCmd(); 
                   Personne personneTuteur = (Personne)insertPersonneCmd.execute(paramInsertContrat.getPersonneTuteur());

                   if (personneTuteur.hasError()){
                       List listErreur = personneTuteur.getErrors();                    
                       for (Iterator it = listErreur.iterator(); it.hasNext(); ) {
                           com.oxia.fwk.core.Error erreur = (com.oxia.fwk.core.Error)it.next();
                           ActionMessage actionMessage = new ActionMessage("exception.generique",erreur.getDescription());
                           actionMessages.add("Erreur ", actionMessage);
                       }    
                       this.saveMessages(request, actionMessages);
                       return mapping.findForward("error");
                   }

                   if(personneTuteur.getNumSeqPers()!=null){
                       mandataire.setCodTpceTpce(personneTuteur.getTypePiece().getCodTpceTpce());
                       mandataire.setNumPcePers(personneTuteur.getNumPcePers());
                       mandataire.setNumSeqPers(personneTuteur.getNumSeqPers());
                       mandataire.setNom(personneTuteur.getNomNomPers());
                       mandataire.setPrenom(personneTuteur.getNomPrnPers()); 
                   }                   
               }
               
               consultMandantContratForm.getListMandatPersonne().add(mandataire);
               lc_datagrid.setData(consultMandantContratForm.getListMandatPersonne());
               
               
           }else if (paramInsertContrat.getContratCpt().getClient().getPersonne().getCategoriePersonne().getCodCatpCatp().equals(Constants.COD_CATEGORIE_P_ETR_INC)
           || paramInsertContrat.getContratCpt().getClient().getPersonne().getCategoriePersonne().getCodCatpCatp().equals(Constants.COD_CATEGORIE_P_TUN_INC)) {
              
           ///------Traité le cas des personnes physique maj et etranger incappables     
            //------Affectation du client 
                consultMandantContratForm.setMyTypeStructure(paramAgence.getCodTstrcTstrc());
                consultMandantContratForm.setNomNomPers(paramInsertContrat.getContratCpt().getClient().getPersonne().getNomNomPers());
                consultMandantContratForm.setNomPrnPers(paramInsertContrat.getContratCpt().getClient().getPersonne().getNomPrnPers());
                consultMandantContratForm.setCasSouscriptionContratCompte("incappable");
                ///------Fin affectation du client
           
           }else if (paramInsertContrat.getContratCpt().getClient().getTypePers().getCodTperTper().equals(Constants.PERSMORALE)){
               consultMandantContratForm.setMyTypeStructure(paramAgence.getCodTstrcTstrc());
               consultMandantContratForm.setNomNomPers(paramInsertContrat.getContratCpt().getClient().getPersonne().getNomRsPers());
               consultMandantContratForm.setNomPrnPers(paramInsertContrat.getContratCpt().getClient().getPersonne().getLibSiglPers());
               consultMandantContratForm.setCasSouscriptionContratCompte("morale");
           
           }         
                consultMandantContratForm.setDatCreMand(DateHandler.dateToStr( new Date()));
                consultMandantContratForm.setDatDebMand(DateHandler.dateToStr( new Date()));
                consultMandantContratForm.setCodEtatMand("S");       
                
                consultMandantContratForm.setListMandatPersonneGrid(lc_datagrid);
                consultMandantContratForm.setListMandatOperationGrid(l_datagrid);
                
                return mapping.findForward("detMand");

    }
   
}
