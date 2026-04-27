package com.bna.smile.web.banqueAssurance.actions;

import com.bna.commun.model.AdhesionAssVie;
import com.bna.commun.model.Assureur;
import com.bna.commun.model.ContratCpt;
import com.bna.commun.model.ContratCptId;
import com.bna.commun.model.DetailAdhesion;
import com.bna.commun.model.Personne;
import com.bna.commun.model.Produit;
import com.bna.commun.model.StructureDomaine;
import com.bna.commun.model.TarifAssVie;
import com.bna.commun.util.DateHandler;
import com.bna.commun.util.SmileUtil;
import com.bna.commun.util.StrHandler;
import com.bna.commun.vo.PrimitiveVO;
import com.bna.smile.model.banqueAssurance.commande.GetListAdhesionAssVieCmd;
import com.bna.smile.model.banqueAssurance.commande.GetListAsureurCmd;
import com.bna.smile.model.banqueAssurance.commande.InsertAssureurCmd;
import com.bna.smile.model.banqueAssurance.commande.PecChangCptFactCmd;
import com.bna.smile.model.banqueAssurance.commande.PecResiliationAssVieCmd;
import com.bna.smile.model.banqueAssurance.commande.RejeterChangCptFactCmd;
import com.bna.smile.model.banqueAssurance.commande.UpdateAssureurCmd;
import com.bna.smile.model.banqueAssurance.commande.ValidChangCptFactCmd;
import com.bna.smile.model.banqueAssurance.commande.ValidResiliationAssVieCmd;
import com.bna.smile.model.banqueAssurance.commande.ValiderAdhesionAssVieCmd;
import com.bna.smile.model.banqueAssurance.model.ParamAdhesion;
import com.bna.smile.model.banqueAssurance.traitement.CreatDetailAdhesionAssVieTrt;
import com.bna.smile.model.banqueAssurance.traitement.GetTarifAssVieTrt;
import com.bna.smile.model.constant.Constants;
import com.bna.smile.model.domainecommun.commande.GetContratCptByIdCmd;
import com.bna.smile.model.domainecommun.commande.GetDetailContratCmd;
import com.bna.smile.model.domainecommun.commande.GetPersonneCmd;
import com.bna.smile.model.domainecommun.commande.GetPersonneCptCmd;
import com.bna.smile.model.domainecommun.commande.GetRibCmd;
import com.bna.smile.model.domainecommun.model.Listes;
import com.bna.smile.model.domainecommun.model.PersonneCpt;
import com.bna.smile.model.domainecommun.model.PersonneStrc;
import com.bna.smile.model.domainecommun.traitement.GetProduitTrt;
import com.bna.smile.model.domainecontratcompte.moyensPaiement.model.ParamRechercheOpposition;
import com.bna.smile.model.reporting.model.CommonReportVO;
import com.bna.smile.web.banqueAssurance.View.AdhesionAssVieView;
import com.bna.smile.web.banqueAssurance.View.TarifAssVieView;
import com.bna.smile.web.banqueAssurance.forms.AssuranceVieForm;
import com.bna.smile.web.commun.model.ParamAgence;
import com.bna.smile.web.commun.util.SessionUtil;
import com.bna.smile.web.placement.forms.ConsultationPlacementForm;
import com.bna.smile.web.procuration.util.ContratCptView;

import java.io.IOException;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.GregorianCalendar;
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


public class AssuranceVieAction extends DispatchAction {
  
    private static final Logger logger = Logger.getLogger(AssuranceVieAction.class);
    
    public  ActionForward initierPage(ActionMapping mapping, ActionForm form, 
                               HttpServletRequest request, 
                               HttpServletResponse response) throws IOException, 
                                                                    ServletException {

         ActionMessages actionMessages = new ActionMessages();
         AssuranceVieForm assuranceVieForm = (AssuranceVieForm)form;
         SessionUtil sessionUtil =new SessionUtil();
         String forward = new String("");
    try {
    
        //Suppression des anciens Bean de type Form de la session, SAUF "AssuranceVieForm"
        sessionUtil.removeSession(request,"assuranceVieForm"); 
        //---affectation du parametre de session code agence, matricule personnel et date du jour
        ParamAgence paramAgence = 
               (ParamAgence)request.getSession().getAttribute("paramAgBNA"); /// structure de l'agent 
        
        StructureDomaine structureDomaine = new StructureDomaine(paramAgence.getCodStrcStrc(),Constants.COD_DOM_CLIENT);
        SmileUtil.testDomaineOuvert(structureDomaine);
        assuranceVieForm.clearForm();
        
        if( paramAgence != null){
        assuranceVieForm.getInitialisationView().setNumMatrUser(paramAgence.getNumMatrUser().toString());
        assuranceVieForm.getInitialisationView().setCodeAgence(paramAgence.getCodStrcStrc().toString());
        assuranceVieForm.getInitialisationView().setDateComptable(paramAgence.getDateComptable());
        assuranceVieForm.getInitialisationView().setDateOp(paramAgence.getDateOp());
        }
        assuranceVieForm.getAdhesionAssVie().setCodEtatAdh("V");
        assuranceVieForm.getAdhesionAssVie().setDateSystAdh(new Date());
        assuranceVieForm.setChoixRecherche(new String("0"));
        assuranceVieForm.getContratView().setCodStrcStrc(paramAgence.getCodStrcStrc().toString());
        if(!paramAgence.getCodStrcStrc().equals(Constants.COD_STRC_DQMRP)){
            assuranceVieForm.setCodStrcStrcRech(paramAgence.getCodStrcStrc());
            }
       
        
        if (assuranceVieForm.getInitialisationView().getCodeOperation().equalsIgnoreCase("PCF")){
            assuranceVieForm.setTitrePage("P.E.C changement compte de facturation");
            forward = "pecChangCptFact";
               
        }else if (assuranceVieForm.getInitialisationView().getCodeOperation().equalsIgnoreCase("ADH")){
            assuranceVieForm.setTitrePage("Adhésion Assurance Vie");
            forward = "success";
        }else if (assuranceVieForm.getInitialisationView().getCodeOperation().equalsIgnoreCase("VCF")){
            assuranceVieForm.setTitrePage("Validation changement compte de facturation");
            forward = "validChangCptFact";
        }else if (assuranceVieForm.getInitialisationView().getCodeOperation().equalsIgnoreCase("PRES")){
            assuranceVieForm.setTitrePage("P.E.C Résiliation Assurance Vie");
            forward = "pecResiliation";
        }else if (assuranceVieForm.getInitialisationView().getCodeOperation().equalsIgnoreCase("PIN")){
            assuranceVieForm.setTitrePage("P.E.C Indemnisation Client");
            forward = "pecIndAssVie";
        }else if(assuranceVieForm.getInitialisationView().getCodeOperation().equalsIgnoreCase("PASS")){
            assuranceVieForm.setTitrePage("P.E.C Assureur (Assurance Vie)");
            assuranceVieForm.getTarifAssVie101().setCodPrdComptView("101");
            assuranceVieForm.getTarifAssVie103().setCodPrdComptView("103");
            assuranceVieForm.getTarifAssVie109().setCodPrdComptView("109");
            assuranceVieForm.getTarifAssVie115().setCodPrdComptView("115");
            forward ="pecAssureur";
        }else if (assuranceVieForm.getInitialisationView().getCodeOperation().equalsIgnoreCase("RASS")){
            assuranceVieForm.setTitrePage("Résiliation Assureur (Assurance Vie)");
            forward = "resAssureur";
        }
            
        GetListAsureurCmd getListAsureurCmd=new GetListAsureurCmd();     
        Listes listeAssureurs=new Listes();
        listeAssureurs=(Listes)getListAsureurCmd.execute(listeAssureurs);
        if(listeAssureurs!=null&&listeAssureurs.getList()!=null){
            assuranceVieForm.setListeAssureurs(listeAssureurs.getList());
        } 
        } catch (Exception e) {
        com.oxia.fwk.core.Error erreur = new com.oxia.fwk.core.Error();
        StringBuffer text = 
            new StringBuffer("L'initialisation de l'adhésion, Veuillez transmettre ce message à l'équipe informatique: ");
        text.append("Exception au niveau de l'agence:"); 
        text.append(assuranceVieForm.getInitialisationView().getCodeAgence());
        text.append(". Exception :"); text.append(e.toString());
        erreur.setCode("");
        erreur.setDescription(text.toString());
        logger.error(text.toString(),e); 
        ActionMessage actionMessage = 
            new ActionMessage("exception.generique", 
                              erreur.getDescription());
        actionMessages.add("Erreur ", actionMessage);
        this.saveMessages(request, actionMessages);
        return mapping.findForward("error");
    }
       
        return mapping.findForward(forward);
    }
    public  ActionForward initierConsultation(ActionMapping mapping, ActionForm form, 
                               HttpServletRequest request, 
                               HttpServletResponse response) throws IOException, 
                                                                    ServletException {

         ActionMessages actionMessages = new ActionMessages();
         AssuranceVieForm assuranceVieForm = (AssuranceVieForm)form;
         SessionUtil sessionUtil =new SessionUtil();
      
    try {
        //Suppression des anciens Bean de type Form de la session, SAUF "AssuranceVieForm"
        sessionUtil.removeSession(request,"assuranceVieForm"); 
        //---affectation du parametre de session code agence, matricule personnel et date du jour
        ParamAgence paramAgence = 
               (ParamAgence)request.getSession().getAttribute("paramAgBNA"); /// structure de l'agent 
        
        StructureDomaine structureDomaine = new StructureDomaine(paramAgence.getCodStrcStrc(),Constants.COD_DOM_CLIENT);
        SmileUtil.testDomaineOuvert(structureDomaine);
        assuranceVieForm.clearForm();
        
        if( paramAgence != null){
        assuranceVieForm.getInitialisationView().setNumMatrUser(paramAgence.getNumMatrUser().toString());
        assuranceVieForm.getInitialisationView().setCodeAgence(paramAgence.getCodStrcStrc().toString());
        assuranceVieForm.getInitialisationView().setDateComptable(paramAgence.getDateComptable());
        assuranceVieForm.getInitialisationView().setDateOp(paramAgence.getDateOp());
        }
        assuranceVieForm.setChoixRecherche(new String("0"));
        
        if(!paramAgence.getCodStrcStrc().equals(Constants.COD_STRC_DQMRP)){
            assuranceVieForm.setCodStrcStrcRech(paramAgence.getCodStrcStrc());
            } 
            
        assuranceVieForm.setTitrePage("Consultation/Edition adhésions assurance vie");
        assuranceVieForm.setDateDebRecherch(paramAgence.getDateComptable());
        assuranceVieForm.setDateFinRecherch(paramAgence.getDateComptable());
        
    
    } catch (Exception e) {
    com.oxia.fwk.core.Error erreur = new com.oxia.fwk.core.Error();
    StringBuffer text = 
        new StringBuffer("L'initialisation de la consultation de l'adhésion a été interrompu. Veuillez transmettre ce message à l'équipe maintenance informatique: ");
    text.append("Exception au niveau de l'agence:"); 
    text.append(assuranceVieForm.getInitialisationView().getCodeAgence());
    text.append(". Exception :"); text.append(e.toString());
    erreur.setCode("");
    erreur.setDescription(text.toString());
    logger.error(text.toString(),e); 
    ActionMessage actionMessage = 
        new ActionMessage("exception.generique", 
                          erreur.getDescription());
    actionMessages.add("Erreur ", actionMessage);
    this.saveMessages(request, actionMessages);
    return mapping.findForward("error");
    }
    
    return mapping.findForward("consultation");
    }
    
    
    public  ActionForward consulterListAdhesion(ActionMapping mapping, ActionForm form, 
                               HttpServletRequest request, 
                               HttpServletResponse response) throws IOException, 
                                                                    ServletException {

         ActionMessages actionMessages = new ActionMessages();
         AssuranceVieForm assuranceVieForm = (AssuranceVieForm)form;
         SessionUtil sessionUtil =new SessionUtil();
        List listeAdhesiowView = new ArrayList();
        ParamRechercheOpposition paramRecherche = new ParamRechercheOpposition();
        GetListAdhesionAssVieCmd getListAdhesionAssVieCmd= new GetListAdhesionAssVieCmd();
        
        Listes l = new Listes();
        
    try {
        assuranceVieForm.setListeAdhesionAssVieView(null);
        if(!assuranceVieForm.getInitialisationView().getCodeAgence().equals(Constants.COD_STRC_DQMRP.toString())){
            paramRecherche.setCodStrcStrc(assuranceVieForm.getCodStrcStrcRech());
        }else {
            // Structure est DQMRP
             if (!assuranceVieForm.getStructureRech().equals("")) {
                    paramRecherche.setCodStrcStrc(Long.valueOf(assuranceVieForm.getStructureRech()));
               }
        }
        
       
     //   System.out.println(Long.valueOf(Math.round(new Double(0.705).intValue()*15/100)));
        if(assuranceVieForm.getEtatAdhesion().equals("0")){
            paramRecherche.setEtat(null);
        }else if(assuranceVieForm.getEtatAdhesion().equals("AR")){ // en attente de résiliation
         paramRecherche.setEtat(Constants.COD_ETA_VALID_ASSUR_VIE);
       }else if(assuranceVieForm.getEtatAdhesion().equals("RN")){ // renouvelés
           paramRecherche.setEtat(Constants.COD_ETA_VALID_ASSUR_VIE);
         }else{
            paramRecherche.setEtat(assuranceVieForm.getEtatAdhesion());
        }
        
        if (assuranceVieForm.getChoixRecherche().equals("0")) {
            // traiter le cas de la recherche par type et numéro de pièce
            PersonneStrc personneStrc = new PersonneStrc();
            personneStrc.setCodTpceTpce(new Long(assuranceVieForm.getTypPcePers()));
            personneStrc.setNumPcePers(assuranceVieForm.getNumPcePce());
            GetPersonneCmd getPersonneCmd = new GetPersonneCmd();
            Personne pers = (Personne)getPersonneCmd.execute(personneStrc);
            paramRecherche.setNumSeqPers(pers.getNumSeqPers());
         }else if(assuranceVieForm.getChoixRecherche().equals("1")){
             // traiter le cas de la recherche par numéro contrat compte
              paramRecherche.setCodStrcStrc(Long.valueOf(assuranceVieForm.getCodStrcStrcRech()));
              paramRecherche.setCodPrdPrd(Long.valueOf(assuranceVieForm.getCodPrdPrdRech()));
              paramRecherche.setNumCcptCcpt(Long.valueOf(assuranceVieForm.getNumCcptCcptRech()));
           }
            
             l = (Listes)getListAdhesionAssVieCmd.execute(paramRecherche);
                   if(l != null && l.getList() != null ) {
                        if(l.getList().size()!=0){
                           Collection<AdhesionAssVie> listAdhAssVie = l.getList();
                           for(AdhesionAssVie adhAssVie : listAdhAssVie ){
                                AdhesionAssVieView adhesionAssVieView=new AdhesionAssVieView();
                                
                                if(assuranceVieForm.getChoixRecherche().equals("2")){
                                  if (!assuranceVieForm.getDateDebRecherch().equals("") && !assuranceVieForm.getDateFinRecherch().equals("")){
                                    if(!assuranceVieForm.getEtatAdhesion().equals("RN")){ 
                                      for (Iterator it = adhAssVie.getDetailAdhesions().iterator(); it.hasNext(); ) {
                                      DetailAdhesion detailAdhesion = (DetailAdhesion)it.next();
                                     
                                      if(detailAdhesion.getDatDebDadh().after(DateHandler.addJour(DateHandler.strToDate(assuranceVieForm.getDateDebRecherch()),-1))
                                          && detailAdhesion.getDatDebDadh().before(DateHandler.addJour(DateHandler.strToDate(assuranceVieForm.getDateFinRecherch()),1)) ){
                                                             if(assuranceVieForm.getEtatAdhesion().equals("AR")){
                                                                   if(detailAdhesion.getCodEtatDadh().equals(Constants.COD_ETA_RESIL_ASSUR_VIE) && detailAdhesion.getDatFinDadh() == null){
                                                                        adhesionAssVieView = creerAdhesionAssVieView(adhAssVie);
                                                                       listeAdhesiowView.add(adhesionAssVieView);
                                                                   }
                                                                }else {
                                                                    if(detailAdhesion.getCodEtatDadh().equals(adhAssVie.getCodEtatAdh())){          
                                                                      adhesionAssVieView = creerAdhesionAssVieView(adhAssVie);
                                                                        listeAdhesiowView.add(adhesionAssVieView);
                                                                    }
                                                                }
                                                            }
                                                     
                                         }
                                       
                                       }else {
                                           if(adhAssVie.getDatRenAdh() != null){
                                            if(adhAssVie.getDatRenAdh().after(DateHandler.addJour(DateHandler.strToDate(assuranceVieForm.getDateDebRecherch()),-1))
                                                   && adhAssVie.getDatRenAdh().before(DateHandler.addJour(DateHandler.strToDate(assuranceVieForm.getDateFinRecherch()),1))){
                                                                                adhesionAssVieView = creerAdhesionAssVieView(adhAssVie);
                                                                                adhesionAssVieView.setDateRenouvel(DateHandler.dateToStr(adhAssVie.getDatRenAdh()));
                                                                                listeAdhesiowView.add(adhesionAssVieView);
                                                                 }
                                           }
                                       }
                                    }
                                   }else {
                                       if(assuranceVieForm.getEtatAdhesion().equals("RN")){
                                           if(adhAssVie.getDatRenAdh() != null){
                                               adhesionAssVieView = creerAdhesionAssVieView(adhAssVie);
                                               adhesionAssVieView.setDateRenouvel(DateHandler.dateToStr(adhAssVie.getDatRenAdh()));
                                               listeAdhesiowView.add(adhesionAssVieView);
                                         }
                                       }else if(assuranceVieForm.getEtatAdhesion().equals("AR")){
                                           for (Iterator it = adhAssVie.getDetailAdhesions().iterator(); it.hasNext(); ) {
                                             DetailAdhesion detailAdhesion = (DetailAdhesion)it.next();
                                                 if(detailAdhesion.getCodEtatDadh().equals(Constants.COD_ETA_RESIL_ASSUR_VIE) && detailAdhesion.getDatFinDadh() == null){
                                                      adhesionAssVieView = creerAdhesionAssVieView(adhAssVie);
                                                     listeAdhesiowView.add(adhesionAssVieView);
                                                 }
                                             }
                                         }else{
                                           adhesionAssVieView = creerAdhesionAssVieView(adhAssVie);
                                           listeAdhesiowView.add(adhesionAssVieView);
                                           }
                                   }
                               
                            }
                            assuranceVieForm.setListeAdhesionAssVieView(listeAdhesiowView);
                        }
                   }
  
    } catch (Exception e) {
    com.oxia.fwk.core.Error erreur = new com.oxia.fwk.core.Error();
    StringBuffer text = 
        new StringBuffer("L'initialisation de la consultation des adhésions a été interrompue, Veuillez transmettre ce message à l'équipe maintenance informatique: ");
    text.append("Exception au niveau de l'agence:"); 
    text.append(assuranceVieForm.getInitialisationView().getCodeAgence());
    text.append(". Exception :"); text.append(e.toString());
    erreur.setCode("");
    erreur.setDescription(text.toString());
    logger.error(text.toString(),e); 
    ActionMessage actionMessage = 
        new ActionMessage("exception.generique", 
                          erreur.getDescription());
    actionMessages.add("Erreur ", actionMessage);
    this.saveMessages(request, actionMessages);
    return mapping.findForward("error");
    }
    
    return mapping.findForward("consultation");
    }
    
    public AdhesionAssVieView creerAdhesionAssVieView(AdhesionAssVie adhAssVie) throws Exception {
        AdhesionAssVieView adhesionAssVieView=new AdhesionAssVieView();
        adhesionAssVieView.setNumAdh(adhAssVie.getNumSeqAdh().toString());
        adhesionAssVieView.setDateAdh(DateHandler.dateToStr(adhAssVie.getDateDebAdh()));
        adhesionAssVieView.setDateEch(DateHandler.dateToStr(adhAssVie.getDateFinAdh()));
        adhesionAssVieView.setDatePrelv(DateHandler.dateToStr(adhAssVie.getDatePrelAdh()));
        adhesionAssVieView.setNomIntiCcpt(adhAssVie.getContratCpt().getNomIntiCcpt());
        adhesionAssVieView.setCptFact(getValeurRIB(adhAssVie.getContratCpt())); 
        adhesionAssVieView.setEtatAdhesion(adhAssVie.getCodEtatAdh());
        if(adhAssVie.getCodMotfAdh() != null){
        int motif = adhAssVie.getCodMotfAdh().intValue();
            switch (motif) {
                        case 1:   adhesionAssVieView.setMotifResiliation("Clôture de compte"); break;
                        case 2:   adhesionAssVieView.setMotifResiliation("Transfert à contentieux"); break;
                        case 3:   adhesionAssVieView.setMotifResiliation("Décès ou IAD"); break;
                        case 4:   adhesionAssVieView.setMotifResiliation("Age > 70 ans"); break;
                        case 5:   adhesionAssVieView.setMotifResiliation("Autres (initié par clt)"); break;
                   }
        }
        return (adhesionAssVieView);
        
    }
    
    public String getValeurRIB(ContratCpt contrat) throws Exception {
        GetRibCmd getRibCmd = new GetRibCmd();
        GetContratCptByIdCmd getContratCptByIdCmd = new GetContratCptByIdCmd();
        contrat = (ContratCpt)getContratCptByIdCmd.execute(contrat);
        PrimitiveVO rib = (PrimitiveVO)(getRibCmd.execute(contrat));
        return (rib.getVString());
     }
  
    public

    ActionForward imprimerBordereauAdhesion(ActionMapping mapping, 
                                                ActionForm form, 
                                                HttpServletRequest request, 
                                                HttpServletResponse response) throws IOException, 
                                                                                     ServletException {

        AssuranceVieForm assuranceVieForm = (AssuranceVieForm)form;
        ActionMessages actionMessages = new ActionMessages();
        ParamAgence paramAgence = 
            (ParamAgence)request.getSession().getAttribute("paramAgBNA");
        try {
            CommonReportVO valueObject = new CommonReportVO();
            Map parameters = new HashMap();
            String pLibEtat = "P_LIBELLE_ETAT";
            String vLibEtat = "";
            StringBuffer txtLibEtat = new StringBuffer("BORDEREAU DES ADHESIONS");
            StringBuffer txtNomFichJasper = new StringBuffer("consultAssVi");
            vLibEtat = "";
          
          if (!assuranceVieForm.getStructureRech().equals("")) {
                   parameters.put("P_STRUCTURE",assuranceVieForm.getStructureRech());
                }else {
                      parameters.put("P_STRUCTURE",paramAgence.getCodStrcStrc().toString());
                  }
         
        if(!assuranceVieForm.getEtatAdhesion().equals("0")){
           
            if(assuranceVieForm.getEtatAdhesion().equals(Constants.COD_ETA_VALID_ASSUR_VIE)){
                parameters.put("COD_ETAT_ADHE",assuranceVieForm.getEtatAdhesion());
                txtLibEtat.append(" NOUVELLES");
            }else if(assuranceVieForm.getEtatAdhesion().equals(Constants.COD_ETA_RESIL_ASSUR_VIE)){
                parameters.put("COD_ETAT_ADHE",assuranceVieForm.getEtatAdhesion());
                txtLibEtat.append(" RESILIES");
                txtNomFichJasper.append("Res");
            }else if(assuranceVieForm.getEtatAdhesion().equals("RN")){
                txtNomFichJasper.append("Ren");
                txtLibEtat.append(" RENOUVELES");
            }else if(assuranceVieForm.getEtatAdhesion().equals("AR")){
                txtNomFichJasper.append("AttRes");
                txtLibEtat.append(" EN ATTENTE DE RESILIATION");
            }
        }else {
            txtNomFichJasper.append("Tout");
        }
             // Ajout du parametre matricule utilisateur
            parameters.put("P_NUM_MATR_USER",paramAgence.getNumMatrUser());
        if (assuranceVieForm.getChoixRecherche().equals("0")) {
            // traiter le cas de la recherche par type et numéro de pièce
             txtNomFichJasper.append("Pers");
            txtLibEtat.append(" (PAR PIECE D'IDENTIFICATION)");
            PersonneStrc personneStrc = new PersonneStrc();
            personneStrc.setCodTpceTpce(new Long(assuranceVieForm.getTypPcePers()));
            personneStrc.setNumPcePers(assuranceVieForm.getNumPcePce());
            GetPersonneCmd getPersonneCmd = new GetPersonneCmd();
            Personne pers = (Personne)getPersonneCmd.execute(personneStrc);
            parameters.put("P_NUM_SEQ_PERS",pers.getNumSeqPers().toString());
             }else if(assuranceVieForm.getChoixRecherche().equals("1")){
                 // traiter le cas de la recherche par numéro contrat compte
                  txtNomFichJasper.append("Cpt");
                   parameters.put("P_COD_PRD",assuranceVieForm.getCodPrdPrdRech().toString());
                   parameters.put("P_NUM_CCPT",assuranceVieForm.getNumCcptCcptRech().toString());
                   }else if(assuranceVieForm.getChoixRecherche().equals("2")){
                       txtNomFichJasper.append("Periode");
                       txtLibEtat.append(" (DU "); txtLibEtat.append(assuranceVieForm.getDateDebRecherch()); 
                       txtLibEtat.append(" AU "); txtLibEtat.append(assuranceVieForm.getDateFinRecherch());txtLibEtat.append(")");
                       parameters.put("P_DATE_DEB",assuranceVieForm.getDateDebRecherch());
                       parameters.put("P_DATE_FIN",assuranceVieForm.getDateFinRecherch());
                   }
        
        // Titre du fichier à imprimer
        vLibEtat = txtLibEtat.toString();
        parameters.put(pLibEtat,vLibEtat);
        
        valueObject.setParams(parameters);
        
        parameters = null;
        // indiquer le nom du fichier jasper                   
        valueObject.setNomReport(txtNomFichJasper.toString());  
        valueObject.setNomDossier("BanqueAssurance");
        request.getSession().setAttribute("CommonPrintVo",valueObject);
        request.setAttribute("print","1");
         
    return mapping.findForward("consultation");
    } catch (Exception e) {
    com.oxia.fwk.core.Error erreur = new com.oxia.fwk.core.Error();
    StringBuffer text = 
        new StringBuffer("L'impression de la liste des adhésions a été interrompue, veuillez transmettre ce message à l'équipe informatique: ");
    text.append("Exception au niveau de l'agence:");
    text.append(assuranceVieForm.getInitialisationView().getCodeAgence());
    text.append(". Exception :");
    text.append(e.toString());
    erreur.setCode("298");
    erreur.setDescription(text.toString());
    logger.error(text.toString(), e);

    ActionMessage actionMessage = 
        new ActionMessage("exception.generique", 
                          erreur.getDescription());
    actionMessages.add("Erreur ", actionMessage);
    this.saveMessages(request, actionMessages);
    return mapping.findForward("error");
    }
    }
    public  ActionForward initPageResAssureur(ActionMapping mapping, ActionForm form, 
                               HttpServletRequest request, 
                               HttpServletResponse response) throws IOException, 
                                                                    ServletException {

         ActionMessages actionMessages = new ActionMessages();
         AssuranceVieForm assuranceVieForm = (AssuranceVieForm)form;
         SessionUtil sessionUtil =new SessionUtil();
        
    try {
    
        //Suppression des anciens Bean de type Form de la session, SAUF "AssuranceVieForm"
        sessionUtil.removeSession(request,"assuranceVieForm"); 
        //---affectation du parametre de session code agence, matricule personnel et date du jour
        ParamAgence paramAgence = 
               (ParamAgence)request.getSession().getAttribute("paramAgBNA"); /// structure de l'agent 
        
        StructureDomaine structureDomaine = new StructureDomaine(paramAgence.getCodStrcStrc(),Constants.COD_DOM_CLIENT);
        SmileUtil.testDomaineOuvert(structureDomaine);
        assuranceVieForm.clearForm();
        
        if( paramAgence != null){
        assuranceVieForm.getInitialisationView().setCodeAgence(paramAgence.getCodStrcStrc().toString());
        assuranceVieForm.getInitialisationView().setDateComptable(paramAgence.getDateComptable());
        }
        assuranceVieForm.setTitrePage("Résiliation Assureur (Assurance Vie)");
       
        GetListAsureurCmd getListAsureurCmd=new GetListAsureurCmd();     
        Listes listeAssureurs=new Listes();
        listeAssureurs=(Listes)getListAsureurCmd.execute(listeAssureurs);
        if(listeAssureurs!=null&&listeAssureurs.getList()!=null){
            assuranceVieForm.setListeAssureurs(listeAssureurs.getList());
        } 
        
        } catch (Exception e) {
        com.oxia.fwk.core.Error erreur = new com.oxia.fwk.core.Error();
        StringBuffer text = 
            new StringBuffer("L'initialisation de la résiliation assureur a été interrompue, Veuillez transmettre ce message à l'équipe informatique: ");
        text.append("Exception au niveau de l'agence:"); 
        text.append(assuranceVieForm.getInitialisationView().getCodeAgence());
        text.append(". Exception :"); text.append(e.toString());
        erreur.setCode("");
        erreur.setDescription(text.toString());
        logger.error(text.toString(),e); 
        ActionMessage actionMessage = 
            new ActionMessage("exception.generique", 
                              erreur.getDescription());
        actionMessages.add("Erreur ", actionMessage);
        this.saveMessages(request, actionMessages);
        return mapping.findForward("error");
    }
       
        return mapping.findForward("resAssureur");
    }
    public ActionForward rechercherClient(ActionMapping mapping, ActionForm form,
                                    HttpServletRequest request, 
                                    HttpServletResponse response) throws IOException, 
                                                                         ServletException {
       StringBuffer text = 
            new StringBuffer("La recherche du client l'hors de la validation de l'adhésion assurance vie a été interrompue, veuillez transmettre ce message à l'équipe informatique: ");
       AssuranceVieForm assuranceVieForm = (AssuranceVieForm)form;
        String forward = new String("");
       Listes l = new Listes();
       assuranceVieForm.setDatNaissanceClt("");
       ParamRechercheOpposition paramRecherche = new ParamRechercheOpposition();
       GetListAdhesionAssVieCmd getListAdhesionAssVieCmd= new GetListAdhesionAssVieCmd();
        try{
            PersonneStrc personneStrc = new PersonneStrc();
            PersonneCpt personneCpt = new PersonneCpt();
            if(!assuranceVieForm.getInitialisationView().getCodeAgence().equals(Constants.COD_STRC_DQMRP.toString())){
                personneStrc.setCodStrcStrc(Long.valueOf(assuranceVieForm.getInitialisationView().getCodeAgence()));
            }
         
            
           if(assuranceVieForm.getChoixRecherche().equals("0")){
               if(assuranceVieForm.getTypPcePers() != null ){
                   personneStrc.setCodTpceTpce(assuranceVieForm.getTypPcePers());
                    if (assuranceVieForm.getTypPcePers().equals(Constants.COD_CIN)) {
                       personneStrc.setNumPcePers(StrHandler.lpad(assuranceVieForm.getNumPcePce(), '0', 8));
                         }else {
                                 personneStrc.setNumPcePers(assuranceVieForm.getNumPcePce());
                                 }
               }else {
                        logger.error(" Type piece vide");
                    }
           }else {
            /*******recherche du contrat**********/
            GetDetailContratCmd getDetailContratCmd = new GetDetailContratCmd();
            ContratCpt contratCpt = new ContratCpt();
             ContratCptId contratCptId = new ContratCptId();
               contratCptId.setCodStrcStrc(assuranceVieForm.getCodStrcStrcRech());
               contratCptId.setCodPrdPrd(assuranceVieForm.getCodPrdPrdRech());
               contratCptId.setNumCcptCcpt(assuranceVieForm.getNumCcptCcptRech());
               contratCpt = (ContratCpt)getDetailContratCmd.execute(contratCptId);
            if (contratCpt.getContratCptId() != null) {
                 if (contratCpt.getCodEtatCcpt().equals(Constants.COD_ETAT_CPT_VALID)) {
                assuranceVieForm.setAlertContrat("contratValide");
                assuranceVieForm.getContratView().setNomIntiCcpt(contratCpt.getNomIntiCcpt());
                   if (contratCpt.getClient().getPersonne().getTypePiece().getCodTpceTpce().equals(Constants.COD_CIN)) {
                            personneStrc.setNumPcePers(StrHandler.lpad(contratCpt.getClient().getPersonne().getNumPcePers(), '0', 8));
                              }else if (contratCpt.getClient().getPersonne().getTypePiece().getCodTpceTpce().equals(Constants.COD_RCS)) {
                                      if ( assuranceVieForm.getInitialisationView().getCodeOperation().equalsIgnoreCase("ADH")){    
                                            assuranceVieForm.setAlertContrat("personneMorale");
                                      }else {
                                      personneStrc.setNumPcePers(contratCpt.getClient().getPersonne().getNumPcePers());
                                      }
                                  }else {
                                     personneStrc.setNumPcePers(contratCpt.getClient().getPersonne().getNumPcePers());
                                    }
                assuranceVieForm.setNumPcePce(personneStrc.getNumPcePers());
                personneStrc.setCodTpceTpce(contratCpt.getClient().getPersonne().getTypePiece().getCodTpceTpce());
               }else {
                    assuranceVieForm.setAlertContrat("ContratNonvalide");
                    assuranceVieForm.setEtatContrat(contratCpt.getCodEtatCcpt());
                }
             }else {
                assuranceVieForm.setAlertContrat("contratInexistant");
            }
           }
           
         if(personneStrc.getNumPcePers() != null){ 
            GetPersonneCptCmd getPersonneCptCmd = new GetPersonneCptCmd();
            personneCpt = (PersonneCpt)getPersonneCptCmd.execute(personneStrc);
            
            if (personneCpt.getPersonne() != null) {
           
                    assuranceVieForm.getAdhesionAssVie().setClient(personneCpt.getClient());
                    if (personneCpt.getPersonne().getTypePiece().getCodTpceTpce().equals(Constants.COD_RCS)) { /* cas RCS affichage de libSiglPers */
                        assuranceVieForm.setNomNomPers(personneCpt.getPersonne().getLibSiglPers());
                        assuranceVieForm.setNomPrnPers(personneCpt.getPersonne().getNomRsPers());
                         } else {
                                assuranceVieForm.setNomNomPers(personneCpt.getPersonne().getNomNomPers());
                                assuranceVieForm.setNomPrnPers(personneCpt.getPersonne().getNomPrnPers());
                            }
                assuranceVieForm.setPersonneExist(true);
                
            // calcul d'age au niveau de la saisie adhesion
            if (assuranceVieForm.getInitialisationView().getCodeOperation().equalsIgnoreCase("ADH")){
                if(personneCpt.getPersonne().getDatNaisPers() != null){
                assuranceVieForm.setDatNaissanceClt(DateHandler.dateToStr(personneCpt.getPersonne().getDatNaisPers()));
                Date dateLimitAge = DateHandler.addMonth(personneCpt.getPersonne().getDatNaisPers(),833); // 69 ans + 5 mois = 833 mois
                 Date birthday = new Date();
                 birthday = personneCpt.getPersonne().getDatNaisPers();
                 Date today = DateHandler.strToDate(assuranceVieForm.getInitialisationView().getDateComptable());
                  if(dateLimitAge.compareTo(today) <= 0){
                      assuranceVieForm.setBoolAgeClt(false);
                  }else {
                      assuranceVieForm.setBoolAgeClt(true);
                  }
                int[] age = new int[3];
                age = DateHandler.getAgeYearMonthDay(birthday,today);
                StringBuffer ageView = new StringBuffer(" ");
                ageView.append(age[0]); ageView.append(" ans, ");
                ageView.append(age[1]); ageView.append(" mois, ");
                ageView.append(age[2]); ageView.append(" jours");
                assuranceVieForm.setAgeClient(ageView.toString());
                 }
                }
                if(personneCpt.getPersonne().getDatDecePers() == null){ 
                assuranceVieForm.setBoolDecesClt(false);
                    List listeDesContratsView = new ArrayList();
                if(personneCpt.getListeContratCpt() != null && personneCpt.getListeContratCpt().size() != 0){
                    assuranceVieForm.setAlertContrat("contratValide");
                                   for (Iterator it = personneCpt.getListeContratCpt().iterator();  it.hasNext(); ) {
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
                                       contratCptView.setNumeroCompte(StrHandler.lpad(contratCpt.getContratCptId().getNumCcptCcpt().toString(), 
                                                                                      '0', 6));
                                       contratCptView.setCodeAgence(StrHandler.lpad(contratCpt.getContratCptId().getCodStrcStrc().toString(), 
                                                                                    '0', 3));
                                       contratCptView.setCodeProduit(StrHandler.lpad(contratCpt.getContratCptId().getCodPrdPrd().toString(), 
                                                                                     '0', 4));
                                       contratCptView.setSolde(StrHandler.formatmnt(Math.abs(contratCpt.getMontSoldCcpt().doubleValue())));
                                       contratCptView.setContratCpt(contratCpt);
                                      
                                       if (contratCpt.getMontSoldCcpt() > 0)
                                           contratCptView.setSens("CR");
                                       else
                                           contratCptView.setSens("DB");
                                         
                                       if ( ! assuranceVieForm.getInitialisationView().getCodeOperation().equalsIgnoreCase("PASS")){    
                                               
                                         if( (contratCpt.getContratCptId().getCodPrdPrd().longValue()==Long.valueOf(101))||
                                           (contratCpt.getContratCptId().getCodPrdPrd().longValue()==Long.valueOf(109))||
                                           (contratCpt.getContratCptId().getCodPrdPrd().longValue()==Long.valueOf(115))||
                                           (contratCpt.getContratCptId().getCodPrdPrd().longValue()==Long.valueOf(103))){
                                           
                                            listeDesContratsView.add(contratCptView);
                                           }
                                       }else {
                                           listeDesContratsView.add(contratCptView);
                                       }
                                   }
                }else {
                    //list compte vide
                     assuranceVieForm.setAlertContrat("ListeCptVide");
                }
               
               if(listeDesContratsView.size() == 0){
                   assuranceVieForm.setAlertContrat("CptEligibleVide");
               }
                assuranceVieForm.setListContratCpt(listeDesContratsView);

            }else {
                // personne decedé
                 assuranceVieForm.setBoolDecesClt(true);
            }
                }else{
                    assuranceVieForm.setPersonneExist(false);
                    assuranceVieForm.setAlertContrat("PersonneInexistante");
                    logger.info("Personne inexistante");
                }
         } 
           
       if ( ! assuranceVieForm.getInitialisationView().getCodeOperation().equalsIgnoreCase("PASS")){    
         
            assuranceVieForm.setListeAdhesionAssVie(null);
            
            if(personneCpt.getClient() != null){
            paramRecherche.setNumSeqPers(personneCpt.getClient().getNumSeqPers());
            paramRecherche.setEtat("V");
            l = (Listes)getListAdhesionAssVieCmd.execute(paramRecherche);
            if(l != null && l.getList() != null ) {
                 if(l.getList().size()!=0){
                  assuranceVieForm.setListeAdhesionAssVie(l.getList());
                        Collection<AdhesionAssVie> listAdhAssVie = l.getList();
                        for(AdhesionAssVie adhAssVie : listAdhAssVie ){
                        if (adhAssVie.getCodEtatAdh().equalsIgnoreCase("V")){
                            AdhesionAssVieView adhesionAssVieView=new AdhesionAssVieView();
                            assuranceVieForm.setAdhesionAssVie(adhAssVie);
                            adhesionAssVieView.setNumAdh(adhAssVie.getNumSeqAdh().toString());
                            adhesionAssVieView.setDateAdh(DateHandler.dateToStr(adhAssVie.getDateReelAdh()));
                            adhesionAssVieView.setDateEch(DateHandler.dateToStr(adhAssVie.getDateEcheAdh()));
                            adhesionAssVieView.setDateDeb(DateHandler.dateToStr(adhAssVie.getDateDebAdh()));
                            adhesionAssVieView.setDateFin(DateHandler.dateToStr(adhAssVie.getDateFinAdh()));
                       //     System.out.println("))){   "+DateHandler.getMonthsBetween(DateHandler.strToDate(assuranceVieForm.getInitialisationView().getDateComptable()),adhAssVie.getDateEcheAdh()));
                            // 60j??
                           if(DateHandler.getMonthsBetween(DateHandler.strToDate(assuranceVieForm.getInitialisationView().getDateComptable()),adhAssVie.getDateFinAdh()) <= 2){
                                assuranceVieForm.setResAvantDeuMois(false);
                            }else {
                                assuranceVieForm.setResAvantDeuMois(true);
                            }
                            
                            adhesionAssVieView.setNumCptFact(StrHandler.lpad(adhAssVie.getContratCpt().getContratCptId().getNumCcptCcpt().toString(), 
                                                                           '0', 6));
                            adhesionAssVieView.setCodStrcFact(StrHandler.lpad(adhAssVie.getContratCpt().getContratCptId().getCodStrcStrc().toString(), 
                                                                         '0', 3));
                            adhesionAssVieView.setCodPrdFact(StrHandler.lpad(adhAssVie.getContratCpt().getContratCptId().getCodPrdPrd().toString(), 
                                                                          '0', 4));
                            adhesionAssVieView.setLibAssureur(adhAssVie.getTarifAssVie().getAssureur().getLibAssAss());
                            assuranceVieForm.setCodAssureur(adhAssVie.getTarifAssVie().getAssureur().getNumSeqAss().toString());
                            assuranceVieForm.setAdhesionAssVieView(adhesionAssVieView);
                            assuranceVieForm.setEtatAdhAssVieExist(adhAssVie.getCodEtatAdh());
                            assuranceVieForm.getAdhesionAssVieView().setMontPrmgTass(adhAssVie.getTarifAssVie().getMontPrmgTass().toString());
                            
                            if (assuranceVieForm.getInitialisationView().getCodeOperation().equalsIgnoreCase("PCF")){
                            verifListCptAdheSelonMenu(assuranceVieForm);
                            } else if (assuranceVieForm.getInitialisationView().getCodeOperation().equalsIgnoreCase("PRES")){
                                for (Iterator it = assuranceVieForm.getAdhesionAssVie().getDetailAdhesions().iterator(); it.hasNext(); ) {
                                    DetailAdhesion detailAdhesion = (DetailAdhesion)it.next();
                                    if (detailAdhesion.getCodEtatDadh().equalsIgnoreCase("R")){
                                        assuranceVieForm.setAlert("pecExist");   
                                    }
                                    }
                            }
                        }
                        }
                 }else {
                    assuranceVieForm.setAlert("aucuneAdhesion");
                    logger.debug("liste retourné vide");
                }
                }
                assuranceVieForm.getAdhesionAssVie().setClient(personneCpt.getClient());
            }
       }
          
        }catch (Exception e) {

           text.append("Exception au niveau de l'agence:"); text.append(assuranceVieForm.getInitialisationView().getCodeAgence());
           text.append(". Date :"); text.append(new Date());  text.append(e.toString());
           ActionMessages actionMessages = new ActionMessages();
           ActionMessage actionMessage = 
               new ActionMessage("exception.generique", 
                                 text.toString());
           actionMessages.add("Erreur ", actionMessage);   
           this.saveMessages(request, actionMessages);
           logger.error(text.toString(),e);
           return mapping.findForward("error");

       }    
        if (assuranceVieForm.getInitialisationView().getCodeOperation().equalsIgnoreCase("PCF")){
            forward  ="pecChangCptFact";
        }else if (assuranceVieForm.getInitialisationView().getCodeOperation().equalsIgnoreCase("ADH")){
            forward  ="success";

           }else if (assuranceVieForm.getInitialisationView().getCodeOperation().equalsIgnoreCase("PIN")){
            forward  ="pecIndAssVie";
         
           }else if (assuranceVieForm.getInitialisationView().getCodeOperation().equalsIgnoreCase("PRES")){
                    forward = "pecResiliation";
                }else if (assuranceVieForm.getInitialisationView().getCodeOperation().equalsIgnoreCase("PASS")){
                    forward = "pecAssureur";
                    }

        
        return mapping.findForward(forward);
    }
    void    verifListCptAdheSelonMenu(AssuranceVieForm assuranceVieForm){
        List listeDesContratsView1 = new ArrayList();
        if (assuranceVieForm.getInitialisationView().getCodeOperation().equalsIgnoreCase("PCF")){        
                   for (Iterator it = assuranceVieForm.getListContratCpt().iterator(); it.hasNext(); ) {
                       ContratCptView contratCptView = (ContratCptView)it.next();
                                 
               if (Long.valueOf(contratCptView.getNumeroCompte()).intValue()!=Long.valueOf(assuranceVieForm.getAdhesionAssVieView().getNumCptFact()).intValue()){
                   
                    listeDesContratsView1.add(contratCptView);
        }     
           
        }
    
        assuranceVieForm.setListContratCpt(listeDesContratsView1);
        if (listeDesContratsView1.size()==0){
            assuranceVieForm.setAlert("UnSeulCpt");
        }
        for (Iterator it = assuranceVieForm.getAdhesionAssVie().getDetailAdhesions().iterator(); it.hasNext(); ) {
            DetailAdhesion detailAdhesion = (DetailAdhesion)it.next();
            if (detailAdhesion.getCodEtatDadh().equalsIgnoreCase("A")){
                assuranceVieForm.setAlert("pecExist");   
            }
            }
    }
    }
    public ActionForward pecChangCptFact(ActionMapping mapping, 
                                             ActionForm form, 
                                             HttpServletRequest request, 
                                             HttpServletResponse response) throws IOException, 
                                                                                  ServletException {
            ParamAgence paramAgence = 
                (ParamAgence)request.getSession().getAttribute("paramAgBNA");
            AssuranceVieForm assuranceVieForm = (AssuranceVieForm)form;
            ActionMessages actionMessages = new ActionMessages();
            Listes l = new Listes();
            ParamAdhesion paramAdhesion = new ParamAdhesion();
            PecChangCptFactCmd pecChangCptFactCmd = new PecChangCptFactCmd();
            try {
                paramAdhesion.setAdhesionAssVie(assuranceVieForm.getAdhesionAssVie());
                paramAdhesion.setDateComptable(DateHandler.strToDate(paramAgence.getDateComptable()));
                Long x = 
                    Long.valueOf(assuranceVieForm.getContratView().getNumCcptCcpt());
                Long y = 
                    Long.valueOf(assuranceVieForm.getContratView().getCodPrdPrd());
                Long z = 
                    Long.valueOf(assuranceVieForm.getContratView().getCodStrcStrc());

                ContratCptId contratCptId = new ContratCptId();
                ContratCpt cpt = new ContratCpt();
                contratCptId.setNumCcptCcpt(x);
                contratCptId.setCodPrdPrd(y);
                contratCptId.setCodStrcStrc(z);
                cpt.setContratCptId(contratCptId);
                paramAdhesion.setNouveauCpt(cpt);
                
                DetailAdhesion detail =(DetailAdhesion)pecChangCptFactCmd.execute(paramAdhesion);
                if (!detail.hasError()) {
                    StringBuffer message = new StringBuffer("");
                    message.append(" la prise en charge du changement du compte de facturation a été effectuée avec succés");
                   
                    assuranceVieForm.setLibelleConfirmation(message.toString());
                    assuranceVieForm.setTitreConfirmation("Prise en charge changement du compte de facturation");
                    
                } else {
                    List listErreur = paramAdhesion.getErrors();
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
            } catch (Exception e) {
               ActionMessage actionMessage = 
                    new ActionMessage("exception.generique", e.getMessage());
                actionMessages.add("Erreur ", actionMessage);
                this.saveMessages(request, actionMessages);
                logger.error("Exception : ", e);
                return mapping.findForward("error");
            
            }
            return mapping.findForward("confirm");
        }
       public ActionForward getListAdhesAvalider(ActionMapping mapping, 
                                                ActionForm form, 
                                                HttpServletRequest request, 
                                                HttpServletResponse response) throws IOException, 
                                                                                     ServletException {
               ParamAgence paramAgence = 
                   (ParamAgence)request.getSession().getAttribute("paramAgBNA");
               AssuranceVieForm assuranceVieForm = (AssuranceVieForm)form;
               assuranceVieForm.clearForm();
               Listes l = new Listes();
               List listeAdhesiowViewChangCpt = new ArrayList();
               List listeAdhesiowViewInd = new ArrayList();
               List listeAdhesiowViewResiliation = new ArrayList();
               ParamRechercheOpposition paramRecherche = new ParamRechercheOpposition();
               GetListAdhesionAssVieCmd getListAdhesionAssVieCmd= new GetListAdhesionAssVieCmd();
              String forward= new String("");
               try {
                   if( paramAgence != null){
                   assuranceVieForm.getInitialisationView().setNumMatrUser(paramAgence.getNumMatrUser().toString());
                   assuranceVieForm.getInitialisationView().setCodeAgence(paramAgence.getCodStrcStrc().toString());
                   assuranceVieForm.getInitialisationView().setDateComptable(paramAgence.getDateComptable());
                   }
                   if (assuranceVieForm.getInitialisationView().getCodeOperation().equalsIgnoreCase("VRES")){
                       paramRecherche.setEtat(Constants.COD_ETA_VALID_ASSUR_VIE);
                   }
                   paramRecherche.setCodStrcStrc(paramAgence.getCodStrcStrc());
                   l = (Listes)getListAdhesionAssVieCmd.execute(paramRecherche);
                   if(l != null && l.getList() != null ) {
                        if(l.getList().size()!=0){
                               assuranceVieForm.setListeAdhesionAssVie(l.getList());
                               Collection<AdhesionAssVie> listAdhAssVie = l.getList();
                               for(AdhesionAssVie adhAssVie : listAdhAssVie ){
                                    for (Iterator it = adhAssVie.getDetailAdhesions().iterator(); 
                                         it.hasNext(); ) {
                                        DetailAdhesion detailAdhesion = (DetailAdhesion)it.next();
                                       
                                            AdhesionAssVieView adhesionAssVieView=new AdhesionAssVieView();
                                            assuranceVieForm.setAdhesionAssVie(adhAssVie);
                                            adhesionAssVieView.setNumAdh(adhAssVie.getNumSeqAdh().toString());
                                            adhesionAssVieView.setDateAdh(DateHandler.dateToStr(adhAssVie.getDateReelAdh()));
                                            adhesionAssVieView.setDateEch(DateHandler.dateToStr(adhAssVie.getDateEcheAdh()));
                                            adhesionAssVieView.setNomIntiCcpt(adhAssVie.getContratCpt().getNomIntiCcpt());
                                            adhesionAssVieView.setNumCptFact(StrHandler.lpad(adhAssVie.getContratCpt().getContratCptId().getNumCcptCcpt().toString(), 
                                                                                           '0', 6));
                                            adhesionAssVieView.setCodStrcFact(StrHandler.lpad(adhAssVie.getContratCpt().getContratCptId().getCodStrcStrc().toString(), 
                                                                                         '0', 3));
                                            adhesionAssVieView.setCodPrdFact(StrHandler.lpad(adhAssVie.getContratCpt().getContratCptId().getCodPrdPrd().toString(), 
                                                                          '0', 4));
                                            adhesionAssVieView.setCptFact(adhesionAssVieView.getCodStrcFact()+adhesionAssVieView.getCodPrdFact()+
                                            adhesionAssVieView.getNumCptFact()); 
                                            adhesionAssVieView.setNouvCptFact((StrHandler.lpad(detailAdhesion.getContratCpt().getContratCptId().getCodStrcStrc().toString(), 
                                                                                         '0', 3))+
                                                                              (StrHandler.lpad(detailAdhesion.getContratCpt().getContratCptId().getCodPrdPrd().toString(), 
                                                                                                                      '0', 4))+ 
                                                                              (StrHandler.lpad(detailAdhesion.getContratCpt().getContratCptId().getNumCcptCcpt().toString(), 
                                                                                                                                       '0', 6)));
                                  // System.out.println("))){   "+DateHandler.getMonthsBetween(DateHandler.strToDate(assuranceVieForm.getInitialisationView().getDateComptable()),adhAssVie.getDateEcheAdh()));
                                  
                                   if(DateHandler.getMonthsBetween(DateHandler.strToDate(paramAgence.getDateComptable()),adhAssVie.getDateFinAdh()) <= 2){
                                      adhesionAssVieView.setBoolResiliation("O");
                                   }else {
                                       adhesionAssVieView.setBoolResiliation("N");
                                   }
                                       
                                        if(detailAdhesion.getCodEtatDadh().equalsIgnoreCase("A")){                                                                                 
                                            listeAdhesiowViewChangCpt.add(adhesionAssVieView) ;           
                                        }else if(detailAdhesion.getCodEtatDadh().equalsIgnoreCase("I")){    
                                            listeAdhesiowViewInd.add(adhesionAssVieView) ;   
                                        }else if(detailAdhesion.getCodEtatDadh().equalsIgnoreCase("R")){    
                                            listeAdhesiowViewResiliation.add(adhesionAssVieView) ;   
                                        }
                               }
                            }
                            if (assuranceVieForm.getInitialisationView().getCodeOperation().equalsIgnoreCase("VCF")){  
                                assuranceVieForm.setTitrePage("Validation changement compte de facturation");
                                assuranceVieForm.setListeAdhesionAssVieView(listeAdhesiowViewChangCpt);
                            }else if (assuranceVieForm.getInitialisationView().getCodeOperation().equalsIgnoreCase("VIN")){
                                assuranceVieForm.setTitrePage("Validation indemnisation client");
                                assuranceVieForm.setListeAdhesionAssVieView(listeAdhesiowViewInd);
                            }else if (assuranceVieForm.getInitialisationView().getCodeOperation().equalsIgnoreCase("VRES")){
                                assuranceVieForm.setTitrePage("Validation résiliation assurance vie");
                                assuranceVieForm.setListeAdhesionAssVieView(listeAdhesiowViewResiliation);
                            }
                            if(assuranceVieForm.getListeAdhesionAssVieView().size()==0){
                                assuranceVieForm.setAlert("aucuneAdhesion");
                            }
                        }else{
                            assuranceVieForm.setAlert("aucuneAdhesion");
                        }
                   }

               } catch (Exception e) {
                   ActionMessages actionMessages = new ActionMessages();
                   ActionMessage actionMessage = 
                       new ActionMessage("exception.generique", e.getMessage());
                   actionMessages.add("Erreur ", actionMessage);
                   this.saveMessages(request, actionMessages);
                   logger.error("Exception : ", e);
                   return mapping.findForward("error");
               }
               if(assuranceVieForm.getInitialisationView().getCodeOperation().equalsIgnoreCase("VCF")){                                                                                 
                  forward = "validChangCptFact";
               }else if(assuranceVieForm.getInitialisationView().getCodeOperation().equalsIgnoreCase("VIN")){    
                   forward = "validChangCptFact";
               }else if(assuranceVieForm.getInitialisationView().getCodeOperation().equalsIgnoreCase("VRES")){    
                   forward = "validResiliation";
               }
               return mapping.findForward(forward);
           }
       public ActionForward rejeterChagCpt(ActionMapping mapping, 
                                              ActionForm form, 
                                              HttpServletRequest request, 
                                              HttpServletResponse response) throws IOException, 
                                                                                   ServletException {
           ParamAgence paramAgence = 
               (ParamAgence)request.getSession().getAttribute("paramAgBNA");
           AssuranceVieForm assuranceVieForm = (AssuranceVieForm)form;
           ActionMessages actionMessages = new ActionMessages();
           Listes l = new Listes();
           ParamAdhesion paramAdhesion = new ParamAdhesion();
           RejeterChangCptFactCmd rejeterChangCptFactCmd = new RejeterChangCptFactCmd();
           try {
               for (Iterator it = assuranceVieForm.getListeAdhesionAssVie().iterator(); 
                    it.hasNext(); ) {
                   AdhesionAssVie adhesionAssVie = (AdhesionAssVie)it.next();
                   if (adhesionAssVie.getNumSeqAdh().intValue()==Long.valueOf(assuranceVieForm.getCleAdhesionChoisi()).intValue()){
                       assuranceVieForm.setAdhesionAssVie(adhesionAssVie);
                   }
               }
               paramAdhesion.setAdhesionAssVie(assuranceVieForm.getAdhesionAssVie());
               paramAdhesion.setDateComptable(DateHandler.strToDate(paramAgence.getDateComptable()));

               ParamAdhesion paramAdhesionRet=(ParamAdhesion) rejeterChangCptFactCmd.execute(paramAdhesion);
               if (!paramAdhesionRet.hasError()) {
                   StringBuffer message = new StringBuffer("");
                   message.append(" Le changement du compte de facturation a été rejeté");
                  
                   assuranceVieForm.setLibelleConfirmation(message.toString());
                   assuranceVieForm.setTitreConfirmation("Validation changement du compte de facturation");
                   
               } else {
                   List listErreur = paramAdhesion.getErrors();
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
           } catch (Exception e) {
               
               ActionMessage actionMessage = 
                   new ActionMessage("exception.generique", e.getMessage());
               actionMessages.add("Erreur ", actionMessage);
               this.saveMessages(request, actionMessages);
               logger.error("Exception : ", e);
               return mapping.findForward("error");
           }
           return mapping.findForward("confirm");
       }
       
        public ActionForward validChangCptFact(ActionMapping mapping, 
                                               ActionForm form, 
                                               HttpServletRequest request, 
                                               HttpServletResponse response) throws IOException, 
                                                                                    ServletException {
            ParamAgence paramAgence = 
                (ParamAgence)request.getSession().getAttribute("paramAgBNA");
            AssuranceVieForm assuranceVieForm = (AssuranceVieForm)form;
            ActionMessages actionMessages = new ActionMessages();
            Listes l = new Listes();
            ParamAdhesion paramAdhesion = new ParamAdhesion();
            ValidChangCptFactCmd validChangCptFactCmd = new ValidChangCptFactCmd();
            try {
                for (Iterator it = assuranceVieForm.getListeAdhesionAssVie().iterator(); 
                     it.hasNext(); ) {
                    AdhesionAssVie adhesionAssVie = (AdhesionAssVie)it.next();
                    if (adhesionAssVie.getNumSeqAdh().intValue()==Long.valueOf(assuranceVieForm.getCleAdhesionChoisi()).intValue()){
                        assuranceVieForm.setAdhesionAssVie(adhesionAssVie);
                    }
                }
                paramAdhesion.setAdhesionAssVie(assuranceVieForm.getAdhesionAssVie());
                paramAdhesion.setDateComptable(DateHandler.strToDate(paramAgence.getDateComptable()));

                ParamAdhesion paramAdhesionRet=(ParamAdhesion) validChangCptFactCmd.execute(paramAdhesion);
                if (!paramAdhesionRet.hasError()) {
                    StringBuffer message = new StringBuffer("");
                    message.append(" la validation du changement du compte de facturation a été effectuée avec succés");
                   
                    assuranceVieForm.setLibelleConfirmation(message.toString());
                    assuranceVieForm.setTitreConfirmation("Validation changement du compte de facturation");
                    
                } else {
                    List listErreur = paramAdhesion.getErrors();
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
            } catch (Exception e) {
                
                ActionMessage actionMessage = 
                    new ActionMessage("exception.generique", e.getMessage());
                actionMessages.add("Erreur ", actionMessage);
                this.saveMessages(request, actionMessages);
                logger.error("Exception : ", e);
                return mapping.findForward("error");
            }
            return mapping.findForward("confirm");
        }
       
         public ActionForward validResiliationAssureur(ActionMapping mapping, 
                                                ActionForm form, 
                                                HttpServletRequest request, 
                                                HttpServletResponse response) throws IOException, 
                                                                                     ServletException {
             ParamAgence paramAgence = 
                 (ParamAgence)request.getSession().getAttribute("paramAgBNA");
             AssuranceVieForm assuranceVieForm = (AssuranceVieForm)form;
             ActionMessages actionMessages = new ActionMessages();
             Listes l = new Listes();
             ParamAdhesion paramAdhesion = new ParamAdhesion();
             UpdateAssureurCmd updateAssureurCmd = new UpdateAssureurCmd();
             try {
                 for (Iterator it = assuranceVieForm.getListeAssureurs().iterator(); it.hasNext(); ) {
                     Assureur assureur = (Assureur)it.next();
                     if (assureur.getNumSeqAss().intValue()==Long.valueOf(assuranceVieForm.getCleAssureurChoisi()).intValue()){
                         assureur.setDateFinAss(DateHandler.strToDate(paramAgence.getDateComptable()));
                         assureur=(Assureur) updateAssureurCmd.execute(assureur);
                         if (!assureur.hasError()) {
                             StringBuffer message = new StringBuffer("");
                             message.append(" La validation de la résiliation de l'assureur " +
                             assureur.getLibAssAss() +
                             " a été effectuée avec succés");
                            
                             assuranceVieForm.setLibelleConfirmation(message.toString());
                             assuranceVieForm.setTitreConfirmation("Validation résiliation assureur");
                             
                         } else {
                             List listErreur = paramAdhesion.getErrors();
                             for (Iterator iter = listErreur.iterator(); iter.hasNext(); ) {
                                 com.oxia.fwk.core.Error erreur = 
                                     (com.oxia.fwk.core.Error)iter.next();
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
                 
                 ActionMessage actionMessage = 
                     new ActionMessage("exception.generique", e.getMessage());
                 actionMessages.add("Erreur ", actionMessage);
                 this.saveMessages(request, actionMessages);
                 logger.error("Exception : ", e);
                 return mapping.findForward("error");
             }
             return mapping.findForward("confirm");
         }
        public ActionForward validerAdhesionAssuranceVie(ActionMapping mapping, 
                                               ActionForm form, 
                                               HttpServletRequest request, 
                                               HttpServletResponse response) throws IOException, 
                                                                                    ServletException {
            ParamAgence paramAgence = 
                (ParamAgence)request.getSession().getAttribute("paramAgBNA");
            AssuranceVieForm assuranceVieForm = (AssuranceVieForm)form;
            AdhesionAssVie adhesionAssVie = new AdhesionAssVie();
            ParamAdhesion paramAdhesion = new ParamAdhesion();
            ValiderAdhesionAssVieCmd validerAdhesionAssVieCmd = new ValiderAdhesionAssVieCmd();
            ActionMessages actionMessages = new ActionMessages();
            
            try {
                adhesionAssVie = assuranceVieForm.getAdhesionAssVie();
                adhesionAssVie.setCodEtatAdh(Constants.COD_ETA_VALID_ASSUR_VIE);
                adhesionAssVie.setDateReelAdh(DateHandler.strToDate(assuranceVieForm.getAdhesionAssVieView().getDateAdh()));
                adhesionAssVie.setDateEcheAdh(DateHandler.strToDate(assuranceVieForm.getAdhesionAssVieView().getDateEch()));
                adhesionAssVie.setDateSystAdh(new Date());
                adhesionAssVie.setDateDebAdh(DateHandler.strToDate(assuranceVieForm.getAdhesionAssVieView().getDateDeb()));
                adhesionAssVie.setDateFinAdh(DateHandler.strToDate(assuranceVieForm.getAdhesionAssVieView().getDateFin()));
                
                if (!assuranceVieForm.getContratView().getNumCcptCcpt().equals("") && 
                    !assuranceVieForm.getContratView().getCodPrdPrd().equals("") && 
                    !assuranceVieForm.getContratView().getCodStrcStrc().equals("")) {

                    Long x = 
                        Long.valueOf(assuranceVieForm.getContratView().getNumCcptCcpt());
                    Long y = 
                        Long.valueOf(assuranceVieForm.getContratView().getCodPrdPrd());
                    Long z = 
                        Long.valueOf(assuranceVieForm.getContratView().getCodStrcStrc());

                    ContratCptId contratCptId = new ContratCptId();
                    ContratCpt cpt = new ContratCpt();
                    contratCptId.setNumCcptCcpt(x);
                    contratCptId.setCodPrdPrd(y);
                    contratCptId.setCodStrcStrc(z);

                    cpt.setContratCptId(contratCptId);
                    adhesionAssVie.setContratCpt(cpt);
                    
                    //adhesionAssVie.setTarifAssVie(
                    //consulterTarifAssVie( Long.valueOf(assuranceVieForm.getContratView().getCodPrdPrd()));
                }

                paramAdhesion.setAdhesionAssVie(adhesionAssVie);
                paramAdhesion.setDateComptable(DateHandler.strToDate(paramAgence.getDateComptable()));
         
                paramAdhesion = (ParamAdhesion)validerAdhesionAssVieCmd.execute(paramAdhesion);
             
                if (!paramAdhesion.hasError()) {
                    StringBuffer message = new StringBuffer("");
                    message.append(" L'opération de validation de l'adhésion à l'assurance vie a été effectuée avec succés");
                    message.append(" Numéro adhésion: ");
                    message.append(paramAdhesion.getAdhesionAssVie().getNumSeqAdh());
                    assuranceVieForm.setLibelleConfirmation(message.toString());
                    assuranceVieForm.setTitreConfirmation("Confirmation de validation de l'adhésion à l'assurance vie");
                    
                } else {
                    List listErreur = paramAdhesion.getErrors();
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
                


            } catch (Exception e) {
               ActionMessage actionMessage = 
                    new ActionMessage("exception.generique", e.getMessage());
                actionMessages.add("Erreur ", actionMessage);
                this.saveMessages(request, actionMessages);
                logger.error("Exception : ", e);
                return mapping.findForward("error");
            }
            return mapping.findForward("confirm");
        }
    
    public TarifAssVie consulterTarifAssVie(Long codeProduitCompte, String codAssureur){
        
        GetTarifAssVieTrt getTarifAssVieTrt = new GetTarifAssVieTrt();
        TarifAssVie tarifAssVie = new  TarifAssVie();
        Assureur assureur =new Assureur();
        Produit prd = new Produit();
        prd.setCodPrdPrd(codeProduitCompte);
        assureur.setNumSeqAss(Long.valueOf(codAssureur));
        tarifAssVie.setProduit(prd);
        tarifAssVie.setAssureur(assureur);
       
        tarifAssVie = (TarifAssVie)getTarifAssVieTrt.exec(tarifAssVie);
        
        return tarifAssVie;
        
    }
    
       public ActionForward chargerTarifAdhesion(ActionMapping mapping, 
                                              ActionForm form, 
                                              HttpServletRequest request, 
                                              HttpServletResponse response) throws IOException, 
                                                                                   ServletException {
         AssuranceVieForm assuranceVieForm = (AssuranceVieForm)form;  
         ActionMessages actionMessages = new ActionMessages();
         String forward = new String("");
         try {
         
             TarifAssVie tarifAssVie=new TarifAssVie(); 
             tarifAssVie=consulterTarifAssVie( Long.valueOf(assuranceVieForm.getContratView().getCodPrdPrd()),assuranceVieForm.getCodAssureur());
             if(tarifAssVie != null && tarifAssVie.getNumSeqTass() != null ){
                 assuranceVieForm.getAdhesionAssVie().setTarifAssVie(tarifAssVie);
                 assuranceVieForm.getAdhesionAssVieView().setMontPrmgTass(StrHandler.formatmnt(Math.abs(tarifAssVie.getMontPrmgTass())));
             }
             if (assuranceVieForm.getInitialisationView().getCodeOperation().equalsIgnoreCase("ADH")){
             calculerDateAdhesion(assuranceVieForm.getInitialisationView().getDateComptable(),assuranceVieForm);
             }
         } catch (Exception e) {
            ActionMessage actionMessage = 
                 new ActionMessage("exception.generique", e.getMessage());
             actionMessages.add("Erreur ", actionMessage);
             this.saveMessages(request, actionMessages);
             logger.error("Exception : ", e);
             return mapping.findForward("error");
         }
        
         if (assuranceVieForm.getInitialisationView().getCodeOperation().equalsIgnoreCase("PCF")){
            forward = "pecChangCptFact";
         }else if (assuranceVieForm.getInitialisationView().getCodeOperation().equalsIgnoreCase("ADH")){
             forward = "success";
         }
         return mapping.findForward(forward);
     }
   void   calculerDateAdhesion(String dateComptable,AssuranceVieForm assuranceVieForm){
   String dateAdhesion="";
   String moisAdhesion=dateComptable.substring(3,5);
   Date DateEcheance;
   Long anneeAdhesion=Long.valueOf(dateComptable.substring(6,10));
   if((Long.valueOf(moisAdhesion).intValue()>=1)&&(Long.valueOf(moisAdhesion).intValue()<4)){
       dateAdhesion="01/04/"+dateComptable.substring(6,10);
     
   }else if((Long.valueOf(moisAdhesion).intValue()>=4)&&(Long.valueOf(moisAdhesion).intValue()<7)){
       dateAdhesion="01/07/"+dateComptable.substring(6,10);
       
   }else if((Long.valueOf(moisAdhesion).intValue()>=7)&&(Long.valueOf(moisAdhesion).intValue()<10)){
       dateAdhesion="01/10/"+dateComptable.substring(6,10);
       
   }else if((Long.valueOf(moisAdhesion).intValue()>=10)&&(Long.valueOf(moisAdhesion).intValue()<=12)){
       anneeAdhesion=anneeAdhesion+1;
       dateAdhesion="01/01/"+anneeAdhesion.toString();
   }    
       assuranceVieForm.getAdhesionAssVieView().setDateAdh(dateAdhesion);
       DateEcheance=DateHandler.addMonth(DateHandler.strToDate(dateAdhesion),12);
       assuranceVieForm.getAdhesionAssVieView().setDateEch(DateHandler.dateToStr(DateEcheance));
       assuranceVieForm.getAdhesionAssVieView().setDateDeb(dateComptable);
       assuranceVieForm.getAdhesionAssVieView().setDateFin(DateHandler.dateToStr(DateHandler.addMonth(DateHandler.strToDate(dateComptable),12)));
   }  
   
   
       public ActionForward pecIndAssVie(ActionMapping mapping, 
                                                ActionForm form, 
                                                HttpServletRequest request, 
                                                HttpServletResponse response) throws IOException, 
                                                                                     ServletException {
               ParamAgence paramAgence = 
                   (ParamAgence)request.getSession().getAttribute("paramAgBNA");
               AssuranceVieForm assuranceVieForm = (AssuranceVieForm)form;
               ActionMessages actionMessages = new ActionMessages();
              
               CreatDetailAdhesionAssVieTrt creatDetailAdhesionAssVieTrt = new CreatDetailAdhesionAssVieTrt();
               try {
                   DetailAdhesion detailInd=new DetailAdhesion();
                   detailInd.setAdhesionAssVie(assuranceVieForm.getAdhesionAssVie());
                   detailInd.setCodEtatDadh("I");
                   detailInd.setDatDebDadh(DateHandler.strToDate(paramAgence.getDateComptable()));
                   
                   DetailAdhesion detail =(DetailAdhesion)creatDetailAdhesionAssVieTrt.exec(detailInd);
                   if (!detail.hasError()) {
                       StringBuffer message = new StringBuffer("");
                       message.append(" la prise en charge de L'indemnisation a été effectuée avec succés");
                      
                       assuranceVieForm.setLibelleConfirmation(message.toString());
                       assuranceVieForm.setTitreConfirmation("Prise en charge Indemnisation client");
                       
                   } else {
                       List listErreur = detail.getErrors();
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
               } catch (Exception e) {
                  ActionMessage actionMessage = 
                       new ActionMessage("exception.generique", e.getMessage());
                   actionMessages.add("Erreur ", actionMessage);
                   this.saveMessages(request, actionMessages);
                   logger.error("Exception : ", e);
                   return mapping.findForward("error");
               
               }
               return mapping.findForward("confirm");
           }
    public ActionForward pecResiliation(ActionMapping mapping, 
                                             ActionForm form, 
                                             HttpServletRequest request, 
                                             HttpServletResponse response) throws IOException, 
                                                                                  ServletException {
            ParamAgence paramAgence = 
                (ParamAgence)request.getSession().getAttribute("paramAgBNA");
            AssuranceVieForm assuranceVieForm = (AssuranceVieForm)form;
            ActionMessages actionMessages = new ActionMessages();
            Listes l = new Listes();
            ParamAdhesion paramAdhesion = new ParamAdhesion();
            PecResiliationAssVieCmd pecResiliationAssVieCmd = new PecResiliationAssVieCmd();
            try {
              
                paramAdhesion.setAdhesionAssVie(assuranceVieForm.getAdhesionAssVie());
                paramAdhesion.setDateComptable(DateHandler.strToDate(paramAgence.getDateComptable()));
                
                DetailAdhesion detail =(DetailAdhesion)pecResiliationAssVieCmd.execute(paramAdhesion);
                if (!detail.hasError()) {
                    StringBuffer message = new StringBuffer("");
                    message.append(" La prise en charge de la résiliation de l'adhésion numéro ");
                    message.append(paramAdhesion.getAdhesionAssVie().getNumSeqAdh());
                    message.append(" a été effectuée avec succés.");
                   
                    assuranceVieForm.setLibelleConfirmation(message.toString());
                    assuranceVieForm.setTitreConfirmation("Confirmation de prise en charge résiliation assurance vie");
                    
                } else {
                    List listErreur = paramAdhesion.getErrors();
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
            } catch (Exception e) {
               ActionMessage actionMessage = 
                    new ActionMessage("exception.generique", e.getMessage());
                actionMessages.add("Erreur ", actionMessage);
                this.saveMessages(request, actionMessages);
                logger.error("Exception : ", e);
                return mapping.findForward("error");
            
            }
            return mapping.findForward("confirm");
        }  
    public ActionForward validResiliation(ActionMapping mapping, 
                                           ActionForm form, 
                                           HttpServletRequest request, 
                                           HttpServletResponse response) throws IOException, 
                                                                                ServletException {
        ParamAgence paramAgence = 
            (ParamAgence)request.getSession().getAttribute("paramAgBNA");
        AssuranceVieForm assuranceVieForm = (AssuranceVieForm)form;
        ActionMessages actionMessages = new ActionMessages();
        Listes l = new Listes();
        ParamAdhesion paramAdhesion = new ParamAdhesion();
        ValidResiliationAssVieCmd validResiliationAssVieCmd = new ValidResiliationAssVieCmd();
        try {
            for (Iterator it = assuranceVieForm.getListeAdhesionAssVie().iterator(); it.hasNext(); ) {
                AdhesionAssVie adhesionAssVie = (AdhesionAssVie)it.next();
                if (adhesionAssVie.getNumSeqAdh().intValue()==Long.valueOf(assuranceVieForm.getCleAdhesionChoisi()).intValue()){
                   GregorianCalendar calendrier = new GregorianCalendar();
                    calendrier.setTime(adhesionAssVie.getDateEcheAdh());
                    // tester si la résiliation s fai dans les deux derniers mois, ajouter une année à la date échéance
                 if(DateHandler.getMonthsBetween(DateHandler.strToDate(assuranceVieForm.getInitialisationView().getDateComptable()),adhesionAssVie.getDateEcheAdh()) <= 2){
                      calendrier.add(GregorianCalendar.YEAR,1);
                      adhesionAssVie.setDateEcheAdh(DateHandler.strToDate(DateHandler.dateToStr(calendrier.getTime())));
                 } 
                    adhesionAssVie.setCodEtatAdh("R");
                    adhesionAssVie.setCodMotfAdh(new Long("5"));
                    assuranceVieForm.setAdhesionAssVie(adhesionAssVie);
                }
            }
            paramAdhesion.setAdhesionAssVie(assuranceVieForm.getAdhesionAssVie());
            paramAdhesion.setDateComptable(DateHandler.strToDate(paramAgence.getDateComptable()));

            ParamAdhesion paramAdhesionRet=(ParamAdhesion) validResiliationAssVieCmd.execute(paramAdhesion);
            if (!paramAdhesionRet.hasError()) {
                StringBuffer message = new StringBuffer("");
                message.append(" La validation de la résiliation de l'adhésion numéro ");
                message.append(paramAdhesionRet.getAdhesionAssVie().getNumSeqAdh());
                message.append(" a été effectuée avec succés.");
                assuranceVieForm.setLibelleConfirmation(message.toString());
                assuranceVieForm.setTitreConfirmation("Confirmation de la validatione résiliation assurance vie");
                
            } else {
                List listErreur = paramAdhesion.getErrors();
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
        } catch (Exception e) {
            
            ActionMessage actionMessage = 
                new ActionMessage("exception.generique", e.getMessage());
            actionMessages.add("Erreur ", actionMessage);
            this.saveMessages(request, actionMessages);
            logger.error("Exception : ", e);
            return mapping.findForward("error");
        }
        return mapping.findForward("confirm");
    }
    
    public ActionForward validPecAssureur(ActionMapping mapping, 
                                           ActionForm form, 
                                           HttpServletRequest request, 
                                           HttpServletResponse response) throws IOException, 
                                                                                ServletException {
        ParamAgence paramAgence = 
            (ParamAgence)request.getSession().getAttribute("paramAgBNA");
        AssuranceVieForm assuranceVieForm = (AssuranceVieForm)form;
        ActionMessages actionMessages = new ActionMessages();
        
        Assureur assureur= new Assureur();
        Set tarifsAssVies = new HashSet(5);
        InsertAssureurCmd insertAssureurCmd = new InsertAssureurCmd();
        TarifAssVie tarifAssVie = new TarifAssVie();
        
        try {
        
            assureur.setLibAssAss(assuranceVieForm.getNomNomPers()+" "+assuranceVieForm.getNomPrnPers());
            if (!assuranceVieForm.getContratView().getNumCcptCcpt().equals("") && 
                !assuranceVieForm.getContratView().getCodPrdPrd().equals("") && 
                !assuranceVieForm.getContratView().getCodStrcStrc().equals("")) {

                Long x = 
                    Long.valueOf(assuranceVieForm.getContratView().getNumCcptCcpt());
                Long y = 
                    Long.valueOf(assuranceVieForm.getContratView().getCodPrdPrd());
                Long z = 
                    Long.valueOf(assuranceVieForm.getContratView().getCodStrcStrc());

                ContratCptId contratCptId = new ContratCptId();
                ContratCpt cpt = new ContratCpt();
                contratCptId.setNumCcptCcpt(x);
                contratCptId.setCodPrdPrd(y);
                contratCptId.setCodStrcStrc(z);

                cpt.setContratCptId(contratCptId);
                assureur.setContratCpt(cpt);
                 }
           
            assureur.setDateDebAss(DateHandler.strToDate(assuranceVieForm.getInitialisationView().getDateComptable()));
            
            tarifsAssVies.add(creerTarifAssVie(assuranceVieForm.getTarifAssVie101()));
            tarifsAssVies.add(creerTarifAssVie(assuranceVieForm.getTarifAssVie103()));
            tarifsAssVies.add(creerTarifAssVie(assuranceVieForm.getTarifAssVie109()));
            tarifsAssVies.add(creerTarifAssVie(assuranceVieForm.getTarifAssVie115()));
            
            assureur.setTarifAssVie(tarifsAssVies);
           
            assureur = (Assureur)insertAssureurCmd.execute(assureur);
            if (!assureur.hasError()) {
                StringBuffer message = new StringBuffer("");
                message.append(" La validation de la prise en charge assureur a été effectuée avec succés.");
                assuranceVieForm.setLibelleConfirmation(message.toString());
                assuranceVieForm.setTitreConfirmation("Confirmation de la prise en charge assureur");
                
            } else {
                List listErreur = assureur.getErrors();
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
            
        } catch (Exception e) {
            
            ActionMessage actionMessage = 
                new ActionMessage("exception.generique", e.getMessage());
            actionMessages.add("Erreur ", actionMessage);
            this.saveMessages(request, actionMessages);
            logger.error("Exception : ", e);
            return mapping.findForward("error");
        }
        return mapping.findForward("confirm");
    }
    
private TarifAssVie creerTarifAssVie(TarifAssVieView tarifAssVieView){

    TarifAssVie tarifAssVie = new TarifAssVie();
    GetProduitTrt getProduitTrt = new GetProduitTrt();
    Produit prd = new Produit();
    prd.setCodPrdPrd(Long.valueOf(tarifAssVieView.getCodPrdComptView()));
    prd = (Produit)getProduitTrt.exec(prd);    
    tarifAssVie.setProduit(prd);
    tarifAssVie.setMontComTass(Long.valueOf((StrHandler.strWithoutBlanck(tarifAssVieView.getMontComTassView().replace('.',' ')))));
    tarifAssVie.setMontPrmaTass(Long.valueOf((StrHandler.strWithoutBlanck(tarifAssVieView.getMontPrmaTassView().replace('.',' ')))));
    tarifAssVie.setMontPrmgTass(Long.valueOf((StrHandler.strWithoutBlanck(tarifAssVieView.getMontPrmgTassView().replace('.',' ')))));
    
    return tarifAssVie;
}
}
    

