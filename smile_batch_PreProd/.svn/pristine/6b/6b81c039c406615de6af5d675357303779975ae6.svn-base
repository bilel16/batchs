package com.bna.smile.web.operationguichet.actions;

import com.bna.commun.model.CoTitulaire;
import com.bna.commun.model.ContratCpt;
import com.bna.commun.model.ContratCptId;
import com.bna.commun.model.DetailOperMoyPaiement;
import com.bna.commun.model.Devise;
import com.bna.commun.model.MandPersOperMoy;
import com.bna.commun.model.MandPersOperMoyId;
import com.bna.commun.model.MandatOperation;
import com.bna.commun.model.MandatPersonne;
import com.bna.commun.model.MontantMiseDiposition;
import com.bna.commun.model.MontantMiseDipositionId;
import com.bna.commun.model.Operation;
import com.bna.commun.model.OperationMoyPay;
import com.bna.commun.model.OppositionMoyenPaiementId;
import com.bna.commun.model.Pays;
import com.bna.commun.model.Personnel;
import com.bna.commun.model.PieceAnnexe;
import com.bna.commun.model.Structure;

import com.bna.commun.model.TypeMoyenPaiement;
import com.bna.commun.model.TypePiece;
import com.bna.commun.util.StrHandler;
import com.bna.commun.util.ContextHandler;
import com.bna.smile.model.domainecommun.commande.GetContratEtatCmd;
import com.bna.smile.model.domainecommun.commande.GetPersonneCptCmd;
import com.bna.smile.model.domainecommun.model.ContratCptMandat;
import com.bna.commun.vo.ContratCptSold;
import com.bna.smile.model.domainecommun.model.ContratPersonne;
import com.bna.smile.model.domainecommun.model.Listes;
import com.bna.smile.model.domainecommun.model.PersonneCpt;
import com.bna.smile.model.domainecommun.model.PersonneStrc;
import com.bna.commun.vo.PrimitiveVO;
//import com.bna.smile.model.domaineguichet.commande.GetMontantMADByIdCmd;
import com.bna.smile.model.domaineguichet.commande.GetMontantMADByIdPersCmd;
import com.bna.smile.model.domaineguichet.commande.GetOperationMoyPayByIDCmd;
import com.bna.smile.model.domaineguichet.commande.InsertMontantMADCmd;
import com.bna.commun.commande.InsertOperationMoyPayCmd;
//import com.bna.smile.model.domaineguichet.commande.UpdateMontantMADCmd;
import com.bna.commun.commande.UpdateOperationMoyPayCmd;
import com.bna.commun.commande.UpdateSoldCmd;
import com.bna.commun.conditiondebanque.commande.DemandeConditionCmd;
import com.bna.commun.conditiondebanque.vo.Condition;
import com.bna.commun.conditiondebanque.vo.ConditionBanque;
import com.bna.commun.conditiondebanque.vo.DemandeCondition;
import com.bna.commun.conditiondebanque.vo.DetailConditionBanque;
import com.bna.commun.conditiondebanque.vo.ListConditionVo;
import com.bna.commun.model.CoursChange;
import com.bna.commun.model.CoursChangeId;
import com.bna.commun.model.NomencElemtCondition;
import com.bna.commun.model.Tache;
import com.bna.commun.model.TacheId;
import com.bna.smile.model.constant.Constants;
import com.bna.smile.model.domainecommun.commande.GetCoursDevCmd;
import com.bna.smile.model.domainecontratcompte.procuration.commande.ConsultEnveloppeRestanteCmd;
import com.bna.smile.model.domainecontratcompte.procuration.commande.UpdateMandatOperationCmd;
import com.bna.smile.model.domaineguichet.commande.GetMontantMADByIdCmd;
import com.bna.smile.model.domaineguichet.commande.UpdateMontantMADCmd;
import com.bna.smile.model.domaineguichet.commande.VerifOppositionMoyPayCmd;
import com.bna.smile.model.domaineguichet.dao.GuichetDAO;
import com.bna.smile.web.commun.model.ParamAgence;
import com.bna.smile.web.commun.model.PersonneDemandeur;
import com.bna.smile.web.commun.model.Pouvoir;
import com.bna.smile.web.commun.view.ContratView;
import com.bna.smile.web.operationguichet.form.GuichetRetraitForm;

import com.bna.smile.web.operationguichet.view.MontantMiseDipositionView;

import com.oxia.fwk.context.Context;

import com.oxia.fwk.core.ValueObject;

import java.io.IOException;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.actions.DispatchAction;

import org.springframework.orm.hibernate3.HibernateTemplate;

public class GuichetRetraitAction extends DispatchAction {
    /**
     * <B> Action de la page  transfertContratCpt.jsp  </B>
     * @param mapping The ActionMapping used to select this instance.
     * @param form The optional ActionForm bean for this request.
     * @param request The HTTP Request we are processing.
     * @param response The HTTP Response we are processing.
     * Nom du package : com.bna.smile.web.operationguichet.actions
     *
     * @author  BOUSSEN Youssef 
     * @version le 20/08/2007
     */
    public Context context = ContextHandler.getContext();
    ContratCpt contratCpt = new ContratCpt();
    Pouvoir pouvoir = new Pouvoir();
    public String vNumOperMoyPay;
    ContratCpt contratCptV = new ContratCpt();

    public ActionForward initierPage(ActionMapping mapping, ActionForm form, 
                                     HttpServletRequest request, 
                                     HttpServletResponse response) throws IOException, 
                                                                          ServletException {

        try {
            GuichetRetraitForm guichetRetraitForm = (GuichetRetraitForm)form;
            guichetRetraitForm.clearForm();
            
            ParamAgence paramAgence = (ParamAgence)request.getSession().getAttribute("paramAgBNA"); /// structure de l'agent 
            
            guichetRetraitForm.getInitialisationView().setNumMatrUser(paramAgence.getNumMatrUser().toString());
            guichetRetraitForm.getInitialisationView().setCodeAgence(paramAgence.getCodStrcStrc().toString());                
            guichetRetraitForm.setDateVal(guichetRetraitForm.getInitialisationView().getDateActuelle());
            guichetRetraitForm.getInitialisationView().setDateOp(new Date());
            
            guichetRetraitForm.getInitialisationView().setReqCode(guichetRetraitForm.getReqCode());
            // saisir le N° de l'opération
            guichetRetraitForm.getInitialisationView().setCodeOperation(Constants.COD_OPER_RETRAIT.toString());
            

            guichetRetraitForm.getContratView().setCodStrcStrc(paramAgence.getCodStrcStrc().toString());
            if (guichetRetraitForm.getCodetrait().equalsIgnoreCase("RMD")){
                guichetRetraitForm.getInitialisationView().setCodeOperation(Constants.COD_OPER_RETRAIT_MAD.toString());
                return mapping.findForward("success1");  
            }else if (guichetRetraitForm.getCodetrait().equalsIgnoreCase("RMG")){
                guichetRetraitForm.getInitialisationView().setCodeOperation(Constants.COD_OPER_RETRAIT_MG.toString());
                return mapping.findForward("success1");  
            }else if (guichetRetraitForm.getCodetrait().equalsIgnoreCase("RCA")){
                guichetRetraitForm.getInitialisationView().setCodeOperation(Constants.COD_OPER_RETRAIT_CA.toString());
                return mapping.findForward("success1");                 
            }else if (guichetRetraitForm.getCodetrait().equalsIgnoreCase("RDEM") || guichetRetraitForm.getCodetrait().equalsIgnoreCase("VRDRE") || guichetRetraitForm.getCodetrait().equalsIgnoreCase("VRDEM")){
                guichetRetraitForm.getInitialisationView().setCodeOperation(Constants.COD_OPER_RETRAIT_DEPL_EMIS.toString());
                return mapping.findForward("success");                 
                         
            }else
            return mapping.findForward("success");
        } catch (Exception e) {
            System.out.println("erreur action ---------- " + e.getMessage());
            return mapping.findForward("error");
        }
    }


    public ActionForward rechercherContrat(ActionMapping mapping, 
                                           ActionForm form, 
                                           HttpServletRequest request, 
                                           HttpServletResponse response) throws IOException, 
                                                                                ServletException {

        try {

            ActionMessages actionMessages = new ActionMessages();

            GuichetRetraitForm guichetRetraitForm = (GuichetRetraitForm)form;

           /*******recherche du contrat**********/

            GetContratEtatCmd getContratEtatCmd = new GetContratEtatCmd();
            ContratCptId contratCptId = new ContratCptId();
            contratCptId.setCodStrcStrc(new Long(guichetRetraitForm.getContratView().getCodStrcStrc()));
            contratCptId.setCodPrdPrd(new Long(guichetRetraitForm.getContratView().getCodPrdPrd()));
            contratCptId.setNumCcptCcpt(new Long(guichetRetraitForm.getContratView().getNumCcptCcpt()));
            ValueObject vo = (ValueObject)getContratEtatCmd.execute(contratCptId);


            if (!vo.hasError()){ /// retour de l'habilitation
            
                ContratCptMandat contratCptMandat = (ContratCptMandat)vo;
                guichetRetraitForm.setVerifEtat(contratCptMandat.isVerifEtat());

                if ( contratCptMandat!=null && !contratCptMandat.hasError() && contratCptMandat.getContratCpt() != null ) {
                    /* Chargement du contrat si son etat est valide */
                    contratCpt = contratCptMandat.getContratCpt();
                    guichetRetraitForm.setContrat(contratCpt);
                    //guichetRetraitForm.setClient(contratCpt.getClient());
                    // guichetRetraitForm.setContratView(setContratView(contratCpt));
                    ContratView contratView = guichetRetraitForm.getContratView();
                    contratView.setContratCpt(contratCpt);
                   /// Long th =contratCpt.getMontSoldCcpt()+contratCpt.getMontAutCcpt()-contratCpt.getMontBlocCcpt();
                    guichetRetraitForm.setMontSoldThCcpt(Long.valueOf(StrHandler.strWithoutBlanck(contratView.getMontSoldFCcpt())));
                    guichetRetraitForm.setMontSoldDevThCcpt(contratView.getMontSdevCcpt());
    
                    if (!contratCptMandat.isVerifEtat()){/* affichage du message (si le contrat n'est pas valide) */
                        guichetRetraitForm.setMessageEtatCpt(contratCptMandat.getMessageEtat());
                    }
                    guichetRetraitForm.setLibDevDevRet(contratCpt.getDevise().getLibSiglDev());
                    guichetRetraitForm.setCodDevDevRet(contratCpt.getDevise().getCodDevDev());
    
                    /* Gestion des comptes verts */
                    if (contratCpt.getBoolCverCcpt()==null){contratCpt.setBoolCverCcpt(Long.valueOf(0));}
                    guichetRetraitForm.setCompteVert(contratCpt.getBoolCverCcpt());
                    if (guichetRetraitForm.getCompteVert().intValue()!=0){
                        contratCptId.setCodPrdPrd(Constants.COD_PRD_PRD_VERT);
                        ValueObject vo1 = (ValueObject) getContratEtatCmd.execute(contratCptId); 
                        if (!vo1.hasError()){ /// retour de l'habilitation
                            ContratCptMandat contratCptMandatV = (ContratCptMandat) vo1;
                            contratCptV = contratCptMandatV.getContratCpt();
                            guichetRetraitForm.setSoldCompteVert(contratCptV.getMontSoldCcpt());
                        }else{
                            return verifErrorVo(mapping, request, actionMessages, vo1);
                        }
                    }
    
                }
                else {
                    if(contratCpt.hasError()){
                        return verifErrorVo(mapping, request, actionMessages, contratCpt);
                    }else {
                        guichetRetraitForm.setNumCcptCcpt("");
                        guichetRetraitForm.setMessageEtatCpt(contratCptMandat.getMessageEtat());
                        guichetRetraitForm.setVerifEtat(false);
                    }
                }

            chargerConditionBanque(guichetRetraitForm);
             return mapping.findForward("success");
            }else{
                return verifErrorVo(mapping, request, actionMessages, vo);
            }

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("erreur action ---------- " + e.getMessage());
            return mapping.findForward("error");
        }

    }

    private ContratView setContratView(ContratCpt contratCpt) {
        ContratView contratView=new ContratView();

        contratView.setNomIntiCcpt(contratCpt.getNomIntiCcpt());
        if (contratCpt.getMontSoldCcpt()!=null){contratView.setMontSoldCcpt(StrHandler.formatmnt(Math.abs(contratCpt.getMontSoldCcpt().doubleValue())));}
        else {contratView.setMontSoldCcpt("0.000");contratCpt.setMontSoldCcpt(Long.valueOf(0));}
        if (contratCpt.getMontAutCcpt()!=null)
        contratView.setMontAutCcpt(StrHandler.formatmnt(contratCpt.getMontAutCcpt().doubleValue()));
        else {contratView.setMontAutCcpt("0.000"); contratCpt.setMontAutCcpt(Long.valueOf(0));}
        if (contratCpt.getMontBlocCcpt()!=null)
        contratView.setMontBlocCcpt(StrHandler.formatmnt(contratCpt.getMontBlocCcpt().doubleValue()));
        else {contratView.setMontBlocCcpt("0.000");contratCpt.setMontBlocCcpt(Long.valueOf(0));}
        contratView.setCodEtatCcpt(contratCpt.getCodEtatCcpt());
        contratView.setLibDevDev(contratCpt.getDevise().getLibSiglDev());
        if (contratCpt.getMontSoldCcpt().longValue()<0){
           contratView.setSensSolde("DB"); 
        }else contratView.setSensSolde("CR");
        return contratView;
    }
    
    public ActionForward rechercherPers(ActionMapping mapping, ActionForm form, 
                                        HttpServletRequest request, 
                                        HttpServletResponse response) throws IOException, 
                                                                             ServletException {
        ActionMessages actionMessages = new ActionMessages();

        try {

            GuichetRetraitForm guichetRetraitForm = (GuichetRetraitForm)form;

            /*******recherche du nouveau client**********/

            GetPersonneCptCmd getPersonneCptCmd = new GetPersonneCptCmd();
            PersonneStrc personneStrc = new PersonneStrc();
            personneStrc.setCodStrcStrc(new Long(guichetRetraitForm.getContratView().getCodStrcStrc()));
            personneStrc.setCodTpceTpce(new Long(guichetRetraitForm.getTypPcePersBenef()));
            personneStrc.setNumPcePers(guichetRetraitForm.getNumPcePersBenef());
            ValueObject vo = (ValueObject)getPersonneCptCmd.execute(personneStrc);
                
            
            if (! vo.hasError())  { /// retour de l'habilitation

                PersonneCpt personneCpt = (PersonneCpt) vo;  
                if (personneCpt!=null && personneCpt.getPersonne()!=null ) {
                    guichetRetraitForm.setNomNomPersBenef(personneCpt.getPersonne().getNomNomPers());
                    guichetRetraitForm.setNomPrnPersBenef(personneCpt.getPersonne().getNomPrnPers());
    
                }else if (personneCpt.getPersonne()==null){
                    guichetRetraitForm.setNomNomPersBenef("");
                    guichetRetraitForm.setNomPrnPersBenef("");
                }
            
            }else{
                return verifErrorVo(mapping, request, actionMessages, vo);
            }

            /* verifier le pouvoir du bénéficiaire */
        /*    ContratPersonne contratPersonne = new ContratPersonne();
            ContratCptId contratCptId = new ContratCptId();
            contratCptId.setCodStrcStrc(Long.valueOf(guichetRetraitForm.getCodStrcStrc()));
            contratCptId.setCodPrdPrd(Long.valueOf(guichetRetraitForm.getCodPrdPrd()));
            contratCptId.setNumCcptCcpt(Long.valueOf(guichetRetraitForm.getNumCcptCcpt()));
            contratPersonne.setContratCptId(contratCptId);
            contratPersonne.setPersonneId(personneStrc);
            
            GetPouvoirPersonneContratCmd getPouvoirPersonneContratCmd = new GetPouvoirPersonneContratCmd();
            PouvoirVo pouvoirVo=(PouvoirVo)getPouvoirPersonneContratCmd.execute(contratPersonne);
            guichetRetraitForm.setTypePouvoir(pouvoirVo.getTypePouvoir());
            */
         return mapping.findForward("success");


        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("erreur action ---------- " + e.getMessage());
            return mapping.findForward("error");
        }

    }   
    public ActionForward rechercherPersMAD(ActionMapping mapping, ActionForm form, 
                                        HttpServletRequest request, 
                                        HttpServletResponse response) throws IOException, 
                                                                             ServletException {
        ActionMessages actionMessages = new ActionMessages();

        try {

            GuichetRetraitForm guichetRetraitForm = (GuichetRetraitForm)form;

            /*******recherche du nouveau client**********/

            GetPersonneCptCmd getPersonneCptCmd = new GetPersonneCptCmd();
            PersonneStrc personneStrc = new PersonneStrc();
            personneStrc.setCodStrcStrc(new Long(guichetRetraitForm.getContratView().getCodStrcStrc()));
            personneStrc.setCodTpceTpce(new Long(guichetRetraitForm.getTypPcePersBenef()));
            personneStrc.setNumPcePers(guichetRetraitForm.getNumPcePersBenef());
            ValueObject vo = (ValueObject)getPersonneCptCmd.execute(personneStrc);
            
            if (!vo.hasError())  {
                    
                PersonneCpt personneCpt = (PersonneCpt) vo;          
                if (personneCpt!=null && personneCpt.getPersonne()!=null && !personneCpt.hasError()) {
                    guichetRetraitForm.setNomNomPersBenef(personneCpt.getPersonne().getNomNomPers());
                    guichetRetraitForm.setNomPrnPersBenef(personneCpt.getPersonne().getNomPrnPers());
    
                }else if (personneCpt.getPersonne()==null){
                    guichetRetraitForm.setNomNomPersBenef("");
                    guichetRetraitForm.setNomPrnPersBenef("");
                }
            
            }else{
                return verifErrorVo(mapping, request, actionMessages, vo);
            }


         if (guichetRetraitForm.getCodetrait().equalsIgnoreCase("RMD")||guichetRetraitForm.getCodetrait().equalsIgnoreCase("RMG")||guichetRetraitForm.getCodetrait().equalsIgnoreCase("RCA")){
             return mapping.findForward("success1");  
         }else
         return mapping.findForward("success");


        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("erreur action ---------- " + e.getMessage());
            return mapping.findForward("error");
        }

    }
    public ActionForward verifOpposition(ActionMapping mapping, ActionForm form, 
                                        HttpServletRequest request, 
                                        HttpServletResponse response) throws IOException, 
                                                                             ServletException {
        ActionMessages actionMessages = new ActionMessages();

        try {

            GuichetRetraitForm guichetRetraitForm = (GuichetRetraitForm)form;
            guichetRetraitForm.setMontSoldThCcpt(Long.valueOf(StrHandler.strWithoutBlanck(guichetRetraitForm.getContratView().getMontSoldFCcpt())));

            /*******recherche du nouveau client**********/

            VerifOppositionMoyPayCmd verifOppositionMoyPayCmd = new VerifOppositionMoyPayCmd();
            
            OppositionMoyenPaiementId oppositionMoyenPaiementId =new OppositionMoyenPaiementId();

            Long codMoyPay = new Long("0");
            if (guichetRetraitForm.getChoixRetraitEspece().equalsIgnoreCase("1")){codMoyPay = Constants.COD_CHEQUE;}
            if (guichetRetraitForm.getChoixRetraitEspece().equalsIgnoreCase("7")){codMoyPay = Constants.COD_OM;}
            if (guichetRetraitForm.getChoixRetraitEspece().equalsIgnoreCase("3")){codMoyPay = Constants.COD_LIVRET;}
            oppositionMoyenPaiementId.setCodMoypTmoy(codMoyPay);
            oppositionMoyenPaiementId.setNumMoypOpmp(guichetRetraitForm.getNumPceOmp());
            
            ValueObject vo = (ValueObject)verifOppositionMoyPayCmd.execute(oppositionMoyenPaiementId);

            if (!vo.hasError())  {

                PrimitiveVO primitiveVO = (PrimitiveVO) vo ;
                guichetRetraitForm.setVerifOpposition(primitiveVO.isVBool());
            
                /* verifier si le cheque ou le livret existe */  
                boolean existeMoyPay=true;
                if (guichetRetraitForm.getChoixRetraitEspece().equalsIgnoreCase("1")){// cas d'un cheque
                   GuichetDAO guichetDAO = (GuichetDAO)context.getBean("guichetDAO");
                   existeMoyPay = guichetDAO.verifExistCheque(Long.valueOf(guichetRetraitForm.getNumPceOmp()),Long.valueOf(guichetRetraitForm.getContratView().getCodStrcStrc()),Long.valueOf(guichetRetraitForm.getContratView().getCodPrdPrd()),Long.valueOf(guichetRetraitForm.getContratView().getNumCcptCcpt()));
                }else if (guichetRetraitForm.getChoixRetraitEspece().equalsIgnoreCase("3") && contratCpt.getNumLivrCcpt()!=null && !contratCpt.getNumLivrCcpt().equalsIgnoreCase(guichetRetraitForm.getNumPceOmp())){ // cas Livret
                   existeMoyPay = false;
                }
                guichetRetraitForm.setExistMoyPay(existeMoyPay);
    
                return mapping.findForward("success");

            }else{
                return verifErrorVo(mapping, request, actionMessages, vo);
            }


        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("erreur action ---------- " + e.getMessage());
            return mapping.findForward("error");
        }

    }
       
       
    public ActionForward validation(ActionMapping mapping, ActionForm form, 
                                        HttpServletRequest request, 
                                        HttpServletResponse response) throws IOException, 
                                                                          ServletException {
       ActionMessages actionMessages = new ActionMessages();

        GuichetRetraitForm guichetRetraitForm = (GuichetRetraitForm)form;

        // trace de l'operation moy de payement //
        guichetRetraitForm.setMontSoldThCcpt(Long.valueOf(guichetRetraitForm.getContratView().getMontSoldFCcpt()));
        //OperationMoyPay operationMoyPay = new OperationMoyPay();

      //  ParamAgence paramAgence = (ParamAgence)request.getSession().getAttribute("paramAgBNA"); 
        
        OperationMoyPay operationMoyPayNew = affecterDonneesOperationMoyenPaiement (guichetRetraitForm);
        
        /* traitement cas mandatOperation */
        if(guichetRetraitForm.getTypePouvoir().equals("M")){
            if (!guichetRetraitForm.getPouvoir().getMandat().getCodTypMand().equalsIgnoreCase("G")){// cas mandat special ou de justice seuleemnt
                    /// maj enveloppe
                    UpdateMandatOperationCmd updateMandatOperationCmd=new UpdateMandatOperationCmd();
                    operationMoyPayNew.getMandatOperation().setMontUtilMaop(Long.valueOf(new Double((new Double(StrHandler.strWithoutBlanck(guichetRetraitForm.getMontRetrCcpt())).doubleValue()*1000)+operationMoyPayNew.getMandatOperation().getMontUtilMaop()).longValue()));
                    MandatOperation mandatOperation = (MandatOperation)updateMandatOperationCmd.execute(operationMoyPayNew.getMandatOperation());
                    /*  Verifier les exceptions  */
                    if (mandatOperation.hasError()){
                         return verifErrorVo(mapping, request, actionMessages, mandatOperation);
                    }
                    guichetRetraitForm.setMntDispo(Long.valueOf("0"));
                }
        } 
        
    InsertOperationMoyPayCmd insertOperationMoyPayCmd = new InsertOperationMoyPayCmd();
    if(!guichetRetraitForm.getTypePouvoir().equals("M") || guichetRetraitForm.getMntDispo().intValue()==0){
        operationMoyPayNew = (OperationMoyPay)insertOperationMoyPayCmd.execute(operationMoyPayNew);
        /*  Verifier les exceptions  */
        if (operationMoyPayNew.hasError()){ return verifErrorVo(mapping, request, actionMessages, operationMoyPayNew);}

        /* Débiter le compte (validation seulement)*/
        if (!operationMoyPayNew.hasError()){
            if(operationMoyPayNew.getCodEtatOmp().equalsIgnoreCase(Constants.COD_VALIDATION)){
                MAJSold(guichetRetraitForm, operationMoyPayNew);
                guichetRetraitForm.setNumOperOmp(operationMoyPayNew.getNumOperOmp());
            }
            guichetRetraitForm.setVerifHabilitation(true);
        }else{
            guichetRetraitForm.setVerifHabilitation(false);
            guichetRetraitForm.setMessageHabilitation(operationMoyPayNew.errorMessage);
        }
    }   
        if (guichetRetraitForm.getCompteVert().intValue()!=0){ /// compte vert
            if (Long.valueOf(new Double(new Double(StrHandler.strWithoutBlanck(guichetRetraitForm.getMontRetrCcpt())).doubleValue()*1000).longValue()).intValue()>guichetRetraitForm.getMontSoldThCcpt().intValue()){
                int diff=Long.valueOf(new Double(new Double(StrHandler.strWithoutBlanck(guichetRetraitForm.getMontRetrCcpt())).doubleValue()*1000).longValue()).intValue()-guichetRetraitForm.getMontSoldThCcpt().intValue();
                operationMoyPayNew.setMontDinOmp(Long.valueOf(diff));
                OperationMoyPay operationMoyPayNewVert = operationMoyPayNew;
                HibernateTemplate  hibernateTemplate = (HibernateTemplate) context.getBean("hibernateTemplate");
                hibernateTemplate.evict(operationMoyPayNew);

                operationMoyPayNewVert.getContratCpt().getContratCptId().setCodPrdPrd(Constants.COD_PRD_PRD_VERT);
                operationMoyPayNewVert = (OperationMoyPay)insertOperationMoyPayCmd.execute(operationMoyPayNewVert);
                /*  Verifier les exceptions  */
                if (operationMoyPayNewVert.hasError()){ return verifErrorVo(mapping, request, actionMessages, operationMoyPayNewVert);}

                guichetRetraitForm.setMontRetrCcpt(Long.valueOf(diff/1000).toString());
                if (!operationMoyPayNew.hasError()){
                    MAJSold(guichetRetraitForm, operationMoyPayNewVert);
                }else{
                    guichetRetraitForm.setVerifHabilitation(false);
                    guichetRetraitForm.setMessageHabilitation(operationMoyPayNew.errorMessage);
                }
                    
            }
        }
        
        /*  Verifier les exceptions  */
        if (operationMoyPayNew.hasError()){ return verifErrorVo(mapping, request, actionMessages, operationMoyPayNew);}
        else return mapping.findForward("confirmationRetrait");

        // InsertTrace(request, gestionContratCptForm);

       // validation(form, request);
    }

    private ActionForward verifErrorVo(ActionMapping mapping, 
                                       HttpServletRequest request, 
                                       ActionMessages actionMessages, 
                                       ValueObject vo) {
        List listErreur = vo.getErrors();
        for (Iterator it = listErreur.iterator(); it.hasNext(); ) {
            com.oxia.fwk.core.Error erreur = (com.oxia.fwk.core.Error)it.next();
            ActionMessage actionMessage = new ActionMessage("exception.generique",erreur.getDescription());
            actionMessages.add("Erreur ", actionMessage);
        }    
        this.saveMessages(request, actionMessages);
        return mapping.findForward("error");
    }


    private void MAJSold(GuichetRetraitForm guichetRetraitForm,OperationMoyPay operationMoyPay) {
    
        ContratCptSold contratCptSold = new ContratCptSold();
        if (guichetRetraitForm.getCodetrait().equalsIgnoreCase("VRDEM")) 
          contratCptSold.setContratCpt(guichetRetraitForm.getContrat());
        else{ if (operationMoyPay.getContratCpt().getContratCptId().getCodPrdPrd()==Constants.COD_PRD_PRD_VERT){// compte vert
            contratCptSold.setContratCpt(contratCptV);
        }else
            contratCptSold.setContratCpt(contratCpt);
        }

        /* comptabiliser la commission et le tva */
        Double commission = Double.valueOf("0");
        Double tva =Double.valueOf("0");
        if (guichetRetraitForm.getMontCommission()!=null && guichetRetraitForm.getMontCommission()!="" ) {
         commission = Double.valueOf(StrHandler.strWithoutBlanck( guichetRetraitForm.getMontCommission())).doubleValue()*1000;}
        if (guichetRetraitForm.getMontTva()!=null )  {       
         tva = Double.valueOf(StrHandler.strWithoutBlanck( guichetRetraitForm.getMontTva())).doubleValue()*1000;}

        contratCptSold.setSens("D");
        if (!(operationMoyPay.getDevise().getCodDevDev().intValue() == Constants.COD_DEV_DINAR.intValue())){/// retrait en devise
            /// *** a transformer le montant de la devise par la parité mixte en cas de  2 devises differentes
            
             /// calculer la commission en devise
             CoursChangeId coursChangeId=new CoursChangeId();
             coursChangeId.setCodDevDev(Long.valueOf(guichetRetraitForm.getContratView().getCodDevDev()));
             coursChangeId.setDatJourCchn(new Date());
             GetCoursDevCmd getCoursDevCmd=new GetCoursDevCmd();
             CoursChange coursChange = (CoursChange)getCoursDevCmd.execute(coursChangeId);
          //   Double commDev =(commission+tva)/Double.valueOf(coursChange.getMontPoffCchn()).doubleValue();
             Double commDev =(commission+tva)/Double.valueOf(0).doubleValue();
             Double i =Double.valueOf(coursChange.getDevise().getNbrDecDev()).doubleValue();
             contratCptSold.setSoldeDevise(Long.valueOf(new Double((new Double(StrHandler.strWithoutBlanck(guichetRetraitForm.getMontRetrCcpt())).doubleValue()*1000+commDev)*(Math.pow(10,i))/1000).longValue()));
         
          //  contratCptSold.setSoldeDevise(Long.valueOf(new Double(new Double(StrHandler.strWithoutBlanck(guichetRetraitForm.getMontRetrCcpt())).doubleValue()*1000+commDev).longValue()));
            if (guichetRetraitForm.getContreValeur()!=null) {
                Double montant = Double.valueOf(StrHandler.strWithoutBlanck( guichetRetraitForm.getContreValeur())).doubleValue()*1000;
                contratCptSold.setSolde(Long.valueOf(new Double(montant+commission+tva).longValue()));
            }
        }else{
            Double montant = Double.valueOf(operationMoyPay.getMontDinOmp());
            contratCptSold.setSolde(Long.valueOf(new Double(montant+commission+tva).longValue()));
        }
        
        UpdateSoldCmd updateSoldCmd = new UpdateSoldCmd();
        ContratCpt contratCptR = (ContratCpt)updateSoldCmd.execute(contratCptSold);
        
    }
 
 
        public ActionForward preValidation(ActionMapping mapping, ActionForm form, 
                                            HttpServletRequest request, 
                                            HttpServletResponse response) throws IOException, 
                                                                                 ServletException {
            ActionMessages actionMessages = new ActionMessages();

            GuichetRetraitForm guichetRetraitForm = (GuichetRetraitForm)form;

            /* trace de l'operation moy de payement */
            
            OperationMoyPay operationMoyPay = new OperationMoyPay();

            ParamAgence paramAgence = (ParamAgence)request.getSession().getAttribute("paramAgBNA"); 
            
            GetOperationMoyPayByIDCmd getOperationMoyPayByIDCmd = new GetOperationMoyPayByIDCmd();
            PrimitiveVO primitiveVO=new PrimitiveVO();
            primitiveVO.setVString(vNumOperMoyPay);
            ValueObject vo = (ValueObject)getOperationMoyPayByIDCmd.execute(primitiveVO);
            /*  Verifier les exceptions  */
            if (!vo.hasError()){

                operationMoyPay = (OperationMoyPay) vo;
                if (guichetRetraitForm.getCodetrait().equalsIgnoreCase("VRDRE")){ /// prevalidation retrait deplacé recu
                    operationMoyPay.setCodEtatOmp(Constants.COD_PREVALID);
                    Personnel personnelInit = new Personnel();
                    personnelInit.setNumMatrUser(guichetRetraitForm.getInitialisationView().getNumMatrUser());
                    operationMoyPay.setPersonnelValideur(personnelInit);/// personnel validateur en cas de prevalidation
                }else if (guichetRetraitForm.getCodetrait().equalsIgnoreCase("VRDEM")) {/// validation retrait deplacé emis
                    operationMoyPay.setCodEtatOmp(Constants.COD_VALIDATION);
                    MAJSold(guichetRetraitForm, operationMoyPay);
                    guichetRetraitForm.setNumOperOmp(operationMoyPay.getNumOperOmp());
                }     
                 UpdateOperationMoyPayCmd updateOperationMoyPayCmd = new UpdateOperationMoyPayCmd();
                 operationMoyPay = (OperationMoyPay)updateOperationMoyPayCmd.execute(operationMoyPay);
                /*  Verifier les exceptions  */
                if (operationMoyPay.hasError()){ return verifErrorVo(mapping, request, actionMessages, operationMoyPay);}
                else return mapping.findForward("indexSMILE");
                // InsertTrace(request, gestionContratCptForm);
             }else{
                 return verifErrorVo(mapping, request, actionMessages, operationMoyPay);
             }

           // validation(form, request);
        }
   
        public OperationMoyPay affecterDonneesOperationMoyenPaiement (ActionForm form){
        
            GuichetRetraitForm guichetRetraitForm = (GuichetRetraitForm)form;
            
             OperationMoyPay operationMoyPay = new OperationMoyPay();          
             Personnel personnelInit = new Personnel();
             personnelInit.setNumMatrUser(guichetRetraitForm.getInitialisationView().getNumMatrUser());
             Operation operation = new Operation();    
             Structure structureInit = new Structure();    
             structureInit.setCodStrcStrc(Long.valueOf(guichetRetraitForm.getInitialisationView().getCodeAgence()));
             
             Structure structureRecep = new Structure();    
             structureRecep.setCodStrcStrc(Long.valueOf(guichetRetraitForm.getContratView().getCodStrcStrc()));
             TypePiece typePieceDem = new TypePiece();    
             typePieceDem.setCodTpceTpce(Long.valueOf(guichetRetraitForm.getPersonneDemandeur().getCodTpceTpceDemandeur()));          
             TypeMoyenPaiement  typeMoyenPaiement = new TypeMoyenPaiement();    
             typeMoyenPaiement.setCodMoypTmoy(Long.valueOf(guichetRetraitForm.getChoixRetraitEspece()));
             Devise devise = new Devise();
             devise.setCodDevDev(Long.valueOf(guichetRetraitForm.getCodDevDevRet()));
             operationMoyPay.setDevise(devise);
             
             if ((guichetRetraitForm.getDevdin().equalsIgnoreCase("DV"))){/// retrait en devise
                if (guichetRetraitForm.getContreValeur()!=null && guichetRetraitForm.getMontRetrCcpt()!=null){
                    operationMoyPay.setMontDinOmp(new Long(new Double(new Double(StrHandler.strWithoutBlanck(guichetRetraitForm.getContreValeur())).doubleValue()*1000).longValue()));

                    CoursChangeId coursChangeId=new CoursChangeId();
                    coursChangeId.setCodDevDev(Long.valueOf(guichetRetraitForm.getContratView().getCodDevDev()));
                    coursChangeId.setDatJourCchn(new Date());
                    GetCoursDevCmd getCoursDevCmd=new GetCoursDevCmd();
                    CoursChange coursChange = (CoursChange)getCoursDevCmd.execute(coursChangeId);

                    Double i =Double.valueOf(coursChange.getDevise().getNbrDecDev()).doubleValue();
                   operationMoyPay.setMontDevOmp(new Long(new Double((new Double(StrHandler.strWithoutBlanck(guichetRetraitForm.getMontRetrCcpt())).doubleValue()*1000)*(Math.pow(10,i))/1000).longValue()));
                }
                if (guichetRetraitForm.getMontRetrDev()!=null && guichetRetraitForm.getMontRetrDev()!="")
                operationMoyPay.setMontCdinOmp(new Double(new Double(StrHandler.strWithoutBlanck(guichetRetraitForm.getMontRetrDev())).doubleValue()*1000));/// parité fixe
                operationMoyPay.setMontCourOmp(new Double(StrHandler.strWithoutBlanck(guichetRetraitForm.getCoursDev())).doubleValue());/// parité mixte

             }else{
                if (guichetRetraitForm.getCompteVert().intValue()!=0 && (Long.valueOf(new Double(new Double(StrHandler.strWithoutBlanck(guichetRetraitForm.getMontRetrCcpt())).doubleValue()*1000).longValue()).intValue()>guichetRetraitForm.getMontSoldThCcpt().intValue())){ /// compte vert
                    operationMoyPay.setMontDinOmp(guichetRetraitForm.getMontSoldThCcpt());
                }else
                operationMoyPay.setMontDinOmp(new Long(new Double(new Double(StrHandler.strWithoutBlanck(guichetRetraitForm.getMontRetrCcpt())).doubleValue()*1000).longValue()));
             }
            

             operationMoyPay.setContratCpt(guichetRetraitForm.getContratView().getContratCpt());
             operationMoyPay.setTypePieceDemandeur(typePieceDem);           
             operationMoyPay.setNumPcedOmp(guichetRetraitForm.getPersonneDemandeur().getNumPcePersDemandeur());
             operationMoyPay.setNomNomdOmp(guichetRetraitForm.getPersonneDemandeur().getNomNomPersDemandeur());
             operationMoyPay.setNomPrndOmp(guichetRetraitForm.getPersonneDemandeur().getNomPrnPersDemandeur());
             operationMoyPay.setTypeMoyenPaiement(typeMoyenPaiement);
             operationMoyPay.setStructureInitiatrice(structureInit);
             operationMoyPay.setStructureReceptrice(structureRecep);
             
             operationMoyPay.setCodDemOmp(guichetRetraitForm.getTypePouvoir());// type demandeur (Titulaire,CoTitul,Mandataire)

             operationMoyPay.setNumMoypOmp(guichetRetraitForm.getNumPceOmp());
            
             operationMoyPay.setContratCpt(guichetRetraitForm.getContrat());
             
             /* cas cheque insertion du beneficiaire */
             if (guichetRetraitForm.getChoixRetraitEspece().equalsIgnoreCase(Constants.COD_CHEQUE.toString())){
                 TypePiece typePieceBenef = new TypePiece();    
                 typePieceBenef.setCodTpceTpce(Long.valueOf(guichetRetraitForm.getTypPcePersBenef())); 
                 operationMoyPay.setTypePieceBeneficiaire(typePieceBenef);
                 operationMoyPay.setNumPcebOmp(guichetRetraitForm.getNumPcePersBenef());
                 operationMoyPay.setNomNombOmp(guichetRetraitForm.getNomNomPersBenef());
                 operationMoyPay.setNomPrnbOmp(guichetRetraitForm.getNomPrnPersBenef());
             }
            
             if (guichetRetraitForm.getCodetrait().equalsIgnoreCase("RDEM")){/// retrait deplacé emis
                if  (operationMoyPay.getMontDinOmp().intValue() > Constants.SEUIL_RETRAIT)
                 operationMoyPay.setCodEtatOmp(Constants.COD_ATTENTE);
                 else operationMoyPay.setCodEtatOmp(Constants.COD_VALIDATION);
                operationMoyPay.setPersonnelInitiateur(personnelInit);

            }else if (guichetRetraitForm.getCodetrait().equalsIgnoreCase("VRDRE")){ /// validation retrait deplacé recu
               operationMoyPay.setCodEtatOmp(Constants.COD_PREVALID);
                operationMoyPay.setPersonnelValideur(personnelInit);/// personnel validateur en cas de validation

            }else {
                operationMoyPay.setCodEtatOmp(Constants.COD_VALIDATION);
                if (!guichetRetraitForm.getCodetrait().equalsIgnoreCase("VRDEM"))
                 operationMoyPay.setPersonnelInitiateur(personnelInit);/// personne initiatrice seulement au cas de retrait ponctuel
                 operationMoyPay.setPersonnelValideur(personnelInit);/// personnel initiatrice = personnel validateur
              }
            
                  operation.setCodOperOper(Long.valueOf(guichetRetraitForm.getInitialisationView().getCodeOperation()));
            
                  Tache tache = new Tache();
                  tache.setOperation(operation);
                  TacheId tacheId = new TacheId();
                  tacheId.setCodOperOper(operation.getCodOperOper());

                 /// guichetRetraitForm.getInitialisationView().setCodeOperation(Constants.COD_OPER_RETRAIT.toString());
                tacheId.setCodTachTach(Constants.COD_TACHE_RETRAIT);

                   if (guichetRetraitForm.getCodetrait().equalsIgnoreCase("RMD")){
                      tacheId.setCodTachTach(Constants.COD_TACHE_RETRAIT_MAD);
                  }else if (guichetRetraitForm.getCodetrait().equalsIgnoreCase("RMG")){
                      tacheId.setCodTachTach(Constants.COD_TACHE_RETRAIT_MG);
                  }else if (guichetRetraitForm.getCodetrait().equalsIgnoreCase("RCA")){
                      tacheId.setCodTachTach(Constants.COD_TACHE_RETRAIT_CA);
                  }else if (guichetRetraitForm.getCodetrait().equalsIgnoreCase("RDEM") ){
                      tacheId.setCodTachTach(Constants.COD_TACHE_RETRAIT_DEPL_EMIS);
                  }else if (guichetRetraitForm.getCodetrait().equalsIgnoreCase("VRDRE") ){
                      tacheId.setCodTachTach(Constants.COD_TACHE_VALID_RETRAIT_DEPL_RECU);
                  }else if (guichetRetraitForm.getCodetrait().equalsIgnoreCase("VRDEM")){
                      tacheId.setCodTachTach(Constants.COD_TACHE_VALID_RETRAIT_DEPL_EMIS);
                  }

             tache.setTacheId(tacheId);
             operationMoyPay.setTache(tache);
            
             operationMoyPay.setDatOperOmp(new Date());
             operationMoyPay.setDatValOmp(new Date());// en attandant l"API" des conditions de banque
             operationMoyPay.setCodSensOmp(Constants.COD_SENS_DB);
             operationMoyPay.setMontTvaOmp(new Long(new Double(new Double(StrHandler.strWithoutBlanck(guichetRetraitForm.getMontTva())).doubleValue()*1000).longValue()));
             // Insertion dans la table Detail_Oper_moy_Paiement.
           //    operationMoyPay.setDetailOperMoyPaiements(guichetRetraitForm.getListDetailOperOmp());
            int size = guichetRetraitForm.getListDetailOperOmp().size();
            operationMoyPay.getDetailOperMoyPaiements().clear();
            for(int i=0;i<size;i++){
                 operationMoyPay.getDetailOperMoyPaiements().add(guichetRetraitForm.getListDetailOperOmp().get(i));
            }

             // Insertion dans la table Mand_pers_oper_moy
             
              if(guichetRetraitForm.getTypePouvoir().equals("C")){
                  // cas cotitulaire
                   if(guichetRetraitForm.getPouvoir().getListCotitulaire()!=null && guichetRetraitForm.getPouvoir().getListCotitulaire().size()>0 ){
                       CoTitulaire cotitulaire = (CoTitulaire)guichetRetraitForm.getPouvoir().getListCotitulaire().get(0);
                       operationMoyPay.setCoTitulaire(cotitulaire);
                   }         
              }            
             
             if(guichetRetraitForm.getTypePouvoir().equals("M")){
              Set listeMandPersOperMoy = new HashSet(0);
              for (Iterator it = guichetRetraitForm.getPouvoir().getListMandatPersonne().iterator();it.hasNext(); ) { 
                  MandatPersonne mandatPersonne = (MandatPersonne)it.next();
                  
                  MandPersOperMoy mandPersOperMoy = new MandPersOperMoy();
                  MandPersOperMoyId mandPersOperMoyId = new MandPersOperMoyId();
                  mandPersOperMoyId.setNumMandMand(mandatPersonne.getMandat().getNumMandMand());               
                  mandPersOperMoyId.setNumSeqPers(mandatPersonne.getPersonne().getNumSeqPers());
                  mandPersOperMoyId.setNumOperOmp(operationMoyPay.getNumOperOmp());               
                  mandPersOperMoy.setMandPersOperMoyId(mandPersOperMoyId);
                  listeMandPersOperMoy.add(mandPersOperMoy);
              }
                 operationMoyPay.setMandPersOperMoies(listeMandPersOperMoy);
                 if(guichetRetraitForm.getPouvoir().getListMandatOperation() != null && guichetRetraitForm.getPouvoir().getListMandatOperation().size()>0 ){
                     MandatOperation mandatOperation = (MandatOperation)guichetRetraitForm.getPouvoir().getListMandatOperation().get(0);
                     operationMoyPay.setMandatOperation(mandatOperation);                  
                 }
             }
            
            return operationMoyPay;
        }   
    


    public ActionForward getMAD(ActionMapping mapping, ActionForm form, 
                                            HttpServletRequest request, 
                                            HttpServletResponse response) throws IOException, 
                                                                                 ServletException {
            ActionMessages actionMessages = new ActionMessages();

            GuichetRetraitForm guichetRetraitForm = (GuichetRetraitForm)form;
            
            List listeMontantMiseDipositionView = new ArrayList();
            PersonneStrc personneStrc=new PersonneStrc();
            personneStrc.setCodTpceTpce(Long.valueOf(guichetRetraitForm.getTypPcePersBenef()));
            personneStrc.setNumPcePers(StrHandler.lpad(guichetRetraitForm.getNumPcePersBenef(),'0',8));
            personneStrc.setCode(guichetRetraitForm.getCodetrait());
            
            GetMontantMADByIdPersCmd getMontantMADByIdPersCmd = new GetMontantMADByIdPersCmd();
            ValueObject vo = (ValueObject)getMontantMADByIdPersCmd.execute(personneStrc);
            /*  Verifier les exceptions  */
            if (!vo.hasError()){ 
                Listes listes = (Listes)vo;
                if (listes.getList()!=null){
                    for (Iterator it = listes.getList().iterator(); it.hasNext(); ) {
                        MontantMiseDiposition montantMiseDiposition = (MontantMiseDiposition)it.next();
                        MontantMiseDipositionView montantMiseDipositionView = new MontantMiseDipositionView();
                        montantMiseDipositionView.setNumMmadMmad(montantMiseDiposition.getMontantMiseDipositionId().getNumMmadMmad());
                        montantMiseDipositionView.setCodStrcEmet(montantMiseDiposition.getStructureByCodEmetStrc().getCodStrcStrc());
                        montantMiseDipositionView.setLibStrcStrc(montantMiseDiposition.getStructureByCodEmetStrc().getLibStrcStrc());
                        montantMiseDipositionView.setDatMmadMmad(montantMiseDiposition.getDatMmadMmad());
                        montantMiseDipositionView.setMontMontMmad(StrHandler.formatmnt(montantMiseDiposition.getMontMontMmad().doubleValue()));
                        montantMiseDipositionView.setNomEmetMmad(montantMiseDiposition.getNomEmetMmad());
                        montantMiseDipositionView.setNomPremMmad(montantMiseDiposition.getNomPremMmad());
                        montantMiseDipositionView.setRetour(montantMiseDiposition.getMontantMiseDipositionId().getNumMmadMmad()+"*"+montantMiseDipositionView.getMontMontMmad());
                        listeMontantMiseDipositionView.add(montantMiseDipositionView);
                    }
               }
            }else{
                return verifErrorVo(mapping, request, actionMessages, vo);
            }

        guichetRetraitForm.setListMAD(listeMontantMiseDipositionView);
    
        guichetRetraitForm.getIndexMADChoisis().clear();
        for (int i = 0; 
             i < guichetRetraitForm.getListMAD().size(); i++)
            guichetRetraitForm.getIndexMADChoisis().add("");
            
        return mapping.findForward("success1");
}
    

    public  ActionForward chargerPouvoir(ActionMapping mapping, ActionForm form, 
                                  HttpServletRequest request, 
                                  HttpServletResponse response) throws IOException, 
                                                                       ServletException {

           try{ 
                ActionMessages actionMessages = new ActionMessages();

                GuichetRetraitForm guichetRetraitForm = (GuichetRetraitForm)form;
                PersonneDemandeur personneDemandeur = guichetRetraitForm.getPersonneDemandeur();  
                pouvoir = (Pouvoir)request.getSession().getAttribute("pouvoir"); /// structure de l'agent 
            
                personneDemandeur = pouvoir.chargerPouvoir(personneDemandeur);  
                guichetRetraitForm.setTypePouvoir(pouvoir.getTypePouvoir());
                guichetRetraitForm.setPouvoir(pouvoir); 
                guichetRetraitForm.setMontSoldThCcpt(Long.valueOf(StrHandler.strWithoutBlanck(guichetRetraitForm.getContratView().getMontSoldFCcpt())));
                 
                 /* vérification montant de l'enveloppe */
                if(guichetRetraitForm.getTypePouvoir().equals("M")){
                    if (!guichetRetraitForm.getPouvoir().getMandat().getCodTypMand().equalsIgnoreCase("G")){// cas mandat special ou de justice seulement
                        ConsultEnveloppeRestanteCmd consultEnveloppeRestanteCmd = new ConsultEnveloppeRestanteCmd();
                        MandatOperation mandatOperation = (MandatOperation)guichetRetraitForm.getPouvoir().getListMandatOperation().get(0);
                        ValueObject vo = (ValueObject)consultEnveloppeRestanteCmd.execute(mandatOperation);

                        if (!vo.hasError())  {
                            PrimitiveVO primitiveVO = (PrimitiveVO)vo;
                            guichetRetraitForm.setMntDispo(Long.valueOf(Math.min(primitiveVO.getVLong().longValue(),mandatOperation.getMontMlimMaop().longValue())));// montant dispodible
                            guichetRetraitForm.setMntDispo(Long.valueOf(Math.min(guichetRetraitForm.getMntDispo().longValue(),guichetRetraitForm.getMontSoldThCcpt().longValue())));
       /*                     int montRetrait = Long.valueOf(new Double(new Double(StrHandler.strWithoutBlanck(guichetRetraitForm.getMontRetrCcpt())).doubleValue()*1000).longValue()).intValue();
                            if (primitiveVO.getVLong().intValue()< montRetrait || mandatOperation.getMontMlimMaop().intValue()< montRetrait){
                                guichetRetraitForm.setMaopDispo(false); // le montant autorisé est insuffisant
                                guichetRetraitForm.setMntDispo(primitiveVO.getVLong());// montant dispodible
                            }else{
                                guichetRetraitForm.setMaopDispo(true); // le montant autorisé est suffisant
                            }
       */
                        }else{
                            return verifErrorVo(mapping, request, actionMessages, vo);
                        }

                    }else guichetRetraitForm.setMntDispo(guichetRetraitForm.getMontSoldThCcpt());
                }
                return mapping.findForward("success");

            } catch (Exception e) {
                System.out.println("Erreur pouvoirAction  " + e.getMessage());
                return mapping.findForward("error");
            }   
            
               // ActionForward actionForward = mapping.findForward("error");
                //newActionForward.setPath(actionForward.getPath() + "?id=" + id);
        }


 
    public ActionForward validationMAD(ActionMapping mapping, ActionForm form, 
                                            HttpServletRequest request, 
                                            HttpServletResponse response) throws IOException, 
                                                                                 ServletException {
           
        ActionMessages actionMessages = new ActionMessages();

        GuichetRetraitForm guichetRetraitForm = (GuichetRetraitForm)form;
        guichetRetraitForm.getListMADChoisis().clear();
        List index = guichetRetraitForm.getIndexMADChoisis();

        ParamAgence paramAgence = (ParamAgence)request.getSession().getAttribute("paramAgBNA"); 
        Structure structure = new Structure();
        structure.setCodStrcStrc(paramAgence.getCodStrcStrc());

        if (guichetRetraitForm.getCodetrait().equalsIgnoreCase("RMD")){ // update MAD
            for (int i = 0; i < index.size(); i++) {
                if (!index.get(i).equals("")) {
                String vNumMmadMmad = index.get(i).toString().substring(0, index.get(i).toString().indexOf("*"));
                
                    PrimitiveVO primitiveVO=new PrimitiveVO();
                    primitiveVO.setVString(vNumMmadMmad);
                    
                    GetMontantMADByIdCmd getMontantMADByIdCmd=new GetMontantMADByIdCmd();
                    ValueObject vo=(ValueObject) getMontantMADByIdCmd.execute(primitiveVO); 
                    if(!vo.hasError()){
                        MontantMiseDiposition montantMiseDiposition=(MontantMiseDiposition)vo;
                        montantMiseDiposition.setDatRetMmad(new Date());
                        montantMiseDiposition.setStructureByCodRecpStrc(structure);
                        Personnel personnelRetrait = new Personnel();
                        personnelRetrait.setNumMatrUser(guichetRetraitForm.getInitialisationView().getNumMatrUser());
                        montantMiseDiposition.setPersonnelRetrait(personnelRetrait);
                        UpdateMontantMADCmd updateMontantMADCmd= new UpdateMontantMADCmd();
                        montantMiseDiposition = (MontantMiseDiposition)updateMontantMADCmd.execute(montantMiseDiposition);
                        /*  Verifier les exceptions  */
                        if (montantMiseDiposition.hasError()){ return verifErrorVo(mapping, request, actionMessages, montantMiseDiposition);}
                    }else{
                        return verifErrorVo(mapping, request, actionMessages, vo);
                    }
                }
            }
        }else{ //Insert un nouveau MAD
                MontantMiseDiposition montantMiseDiposition=new MontantMiseDiposition();
                    MontantMiseDipositionId montantMiseDipositionId=new MontantMiseDipositionId();
                    if (guichetRetraitForm.getCodetrait().equalsIgnoreCase("RMG")){ // cas MoneyGram
                        montantMiseDipositionId.setCodTypeMmad(Constants.COD_MONEYGRAM);
                        montantMiseDiposition.setCodRtpeMmad(guichetRetraitForm.getRefMG().toString());
                        Pays pays= new Pays();
                        pays.setCodPaysPays(guichetRetraitForm.getCodPayProv());
                        montantMiseDiposition.setPays(pays);

                    }else{ // Cash Advance
                        montantMiseDipositionId.setCodTypeMmad(Constants.COD_CASHADVANCE);
                        montantMiseDiposition.setCodRtpeMmad(guichetRetraitForm.getRefTPE());
                        montantMiseDiposition.setNumCartMmad(guichetRetraitForm.getNumCarte());
                        montantMiseDiposition.setStructureByCodRecpStrc(structure);
                        
                    }
                montantMiseDiposition.setMontantMiseDipositionId(montantMiseDipositionId);
                    
                montantMiseDiposition.setCodTpceMmad(Long.valueOf(guichetRetraitForm.getTypPcePersBenef()));
                montantMiseDiposition.setNumPceMmad(guichetRetraitForm.getNumPcePersBenef());
                montantMiseDiposition.setNomNomMmad(guichetRetraitForm.getNomNomPersBenef());
                montantMiseDiposition.setNomPrnMmad(guichetRetraitForm.getNomPrnPersBenef());
                montantMiseDiposition.setCodTpemMmad(Long.valueOf(guichetRetraitForm.getTypPcePersBenef()));
                montantMiseDiposition.setNumNpemMmad(guichetRetraitForm.getNumPcePersBenef());
                if (guichetRetraitForm.getMontRetrCcpt()!=null && guichetRetraitForm.getMontRetrCcpt()!=""){
                montantMiseDiposition.setMontMontMmad(new Long(new Double(new Double(StrHandler.strWithoutBlanck(guichetRetraitForm.getMontRetrCcpt())).doubleValue()*1000).longValue()));}
                if (guichetRetraitForm.getContreValeur()!=null && guichetRetraitForm.getContreValeur()!=""){
                montantMiseDiposition.setMontDevMmad(new Long(new Double(new Double(StrHandler.strWithoutBlanck(guichetRetraitForm.getContreValeur())).doubleValue()*1000).longValue()));}
                montantMiseDiposition.setDatRetMmad(new Date());
                montantMiseDiposition.setDatMmadMmad(new Date());
                Devise devise = new Devise();
                devise.setCodDevDev(guichetRetraitForm.getCodDevDevRet());
                montantMiseDiposition.setDevise(devise);
                montantMiseDiposition.setStructureByCodEmetStrc(structure);
                if (guichetRetraitForm.getCoursDev()!=null && guichetRetraitForm.getCoursDev()!=""){
                montantMiseDiposition.setTauxCourMmad(new Long(new Double(new Double(StrHandler.strWithoutBlanck(guichetRetraitForm.getCoursDev())).doubleValue()*1000).longValue()));
                }
                Personnel personnelRetrait = new Personnel();
                personnelRetrait.setNumMatrUser(guichetRetraitForm.getInitialisationView().getNumMatrUser());
                montantMiseDiposition.setPersonnelRetrait(personnelRetrait);
                
                InsertMontantMADCmd insertMontantMADCmd=new InsertMontantMADCmd();
                montantMiseDiposition = (MontantMiseDiposition)insertMontantMADCmd.execute(montantMiseDiposition);
                /*  Verifier les exceptions  */
                if (montantMiseDiposition.hasError()){ return verifErrorVo(mapping, request, actionMessages, montantMiseDiposition);}

            }
                
            
        return mapping.findForward("indexSMILE");
}
    
    
   
    public ActionForward afficheOperation(ActionMapping mapping, 
                                           ActionForm form, 
                                           HttpServletRequest request, 
                                           HttpServletResponse response) throws IOException, 
                                                                                ServletException {
        /* affichage de l'operation retrait */
        try {
            ActionMessages actionMessages = new ActionMessages();

            GuichetRetraitForm guichetRetraitForm = (GuichetRetraitForm)form;

            /*******recherche du OperationMoyPay**********/

            vNumOperMoyPay =  (String)request.getParameter("numOperMoyPay");

            GetOperationMoyPayByIDCmd getOperationMoyPayByIDCmd = new GetOperationMoyPayByIDCmd();
            PrimitiveVO primitiveVO=new PrimitiveVO();
            primitiveVO.setVString(vNumOperMoyPay);
            ValueObject vo = (ValueObject)getOperationMoyPayByIDCmd.execute(primitiveVO);
            /*  Verifier les exceptions  */
            if (!vo.hasError())  {
                OperationMoyPay operationMoyPay = (OperationMoyPay)vo;
                ContratView contratView = new ContratView();
                contratView.setCodStrcStrc(operationMoyPay.getContratCpt().getContratCptId().getCodStrcStrc().toString());
                contratView.setCodPrdPrd(operationMoyPay.getContratCpt().getContratCptId().getCodPrdPrd().toString());
                contratView.setNumCcptCcpt(operationMoyPay.getContratCpt().getContratCptId().getNumCcptCcpt().toString());
               ///contratView.setCle(operationMoyPay.getStructureInitiatrice().getCodStrcStrc().toString());
                contratView.setNomIntiCcpt(operationMoyPay.getContratCpt().getNomIntiCcpt().toString());
                //contratView.setMontSoldCcpt(operationMoyPay.getMontDinOmp().toString());
                if (operationMoyPay.getContratCpt().getMontSoldCcpt()!=null)
                {contratView.setMontSoldCcpt(StrHandler.formatmnt(Math.abs(operationMoyPay.getContratCpt().getMontSoldCcpt().doubleValue())));}
                else contratView.setMontSoldCcpt("0.000");
                ///contratView.setSensSolde(operationMoyPay.getStructureInitiatrice().getCodStrcStrc().toString());
                contratView.setLibDevDev(operationMoyPay.getContratCpt().getDevise().getLibDevDev().toString());
                contratView.setCodDevDev(operationMoyPay.getContratCpt().getDevise().getCodDevDev().toString());
                contratView.setMontBlocCcpt(operationMoyPay.getContratCpt().getMontBlocCcpt().toString());
                contratView.setMontAutCcpt(StrHandler.formatmnt(Math.abs(operationMoyPay.getContratCpt().getMontAutCcpt().doubleValue())));
                contratView.setMontSdevCcpt(StrHandler.formatmnt(Math.abs(operationMoyPay.getContratCpt().getMontSdevCcpt().doubleValue())));
                contratView.setMontBlocCcpt(StrHandler.formatmnt(Math.abs(operationMoyPay.getContratCpt().getMontBlocCcpt().doubleValue())));
                contratView.setNumeroCompte(operationMoyPay.getContratCpt().getContratCptId().getNumCcptCcpt().toString());
                
                guichetRetraitForm.setContratView(contratView);
                
                PersonneDemandeur personneDemandeur = new PersonneDemandeur();
                personneDemandeur.setCodTpceTpceDemandeur(operationMoyPay.getTypePieceDemandeur().getCodTpceTpce().toString());
                personneDemandeur.setNumPcePersDemandeur(operationMoyPay.getNumPcedOmp());
                personneDemandeur.setNomNomPersDemandeur(operationMoyPay.getNomNomdOmp());
                personneDemandeur.setNomPrnPersDemandeur(operationMoyPay.getNomPrndOmp());
                
                guichetRetraitForm.setPersonneDemandeur(personneDemandeur);
                
                guichetRetraitForm.setMontRetrCcpt(StrHandler.formatmnt(operationMoyPay.getMontDinOmp()).toString());
                if (operationMoyPay.getMontDevOmp()!=null)
                guichetRetraitForm.setMontRetrDev(operationMoyPay.getMontDevOmp().toString());
                guichetRetraitForm.setNumPceOmp(operationMoyPay.getNumMoypOmp().toString());
                
                guichetRetraitForm.setDateVal(operationMoyPay.getDatValOmp().toString());
                guichetRetraitForm.setContrat(operationMoyPay.getContratCpt());
                guichetRetraitForm.setChoixRetraitEspece(operationMoyPay.getTypeMoyenPaiement().getCodMoypTmoy().toString());
    
                /* cas cheque insertion du beneficiaire */
                if (guichetRetraitForm.getChoixRetraitEspece().equalsIgnoreCase(Constants.COD_CHEQUE.toString())){
                    guichetRetraitForm.setTypPcePersBenef(operationMoyPay.getTypePieceBeneficiaire().getCodTpceTpce().toString());
                    guichetRetraitForm.setNumPcePersBenef(operationMoyPay.getNumPcebOmp());
                    guichetRetraitForm.setNomNomPersBenef(operationMoyPay.getNomNombOmp());
                    guichetRetraitForm.setNomPrnPersBenef(operationMoyPay.getNomPrnbOmp());
                }
                
                if (guichetRetraitForm.getCodetrait().equalsIgnoreCase("RDEM") || guichetRetraitForm.getCodetrait().equalsIgnoreCase("VRDRE") || guichetRetraitForm.getCodetrait().equalsIgnoreCase("VRDEM")){
                        guichetRetraitForm.getInitialisationView().setCodeOperation(Constants.COD_OPER_RETRAIT_DEPL_EMIS.toString());
                }
                /// Charger les commission a partir de DetailOperMoyPaiement
                if (operationMoyPay.getDetailOperMoyPaiements()!=null && operationMoyPay.getDetailOperMoyPaiements().size()>0){
                    for (Iterator it = operationMoyPay.getDetailOperMoyPaiements().iterator(); it.hasNext(); ) {
                        DetailOperMoyPaiement detailOperMoyPaiement = (DetailOperMoyPaiement)it.next();    
                        if  (detailOperMoyPaiement.getCodTypDomp().equalsIgnoreCase(Constants.COD_TYPE_COMMISSION))
                            guichetRetraitForm.setMontCommission(detailOperMoyPaiement.getMontValDomp().toString());
                   //     else if  (detailOperMoyPaiement.getCodTypDomp().equalsIgnoreCase(Constants.COD_TYPE_TVA))
                     //      guichetRetraitForm.setMontTva(detailOperMoyPaiement.getNomencElemtCondition().getCodNecdNecd().toString());
                    }
                }
                if (operationMoyPay.getMontTvaOmp()!=null)
                guichetRetraitForm.setMontTva(operationMoyPay.getMontTvaOmp().toString());
                
                ParamAgence paramAgence = (ParamAgence)request.getSession().getAttribute("paramAgBNA"); /// structure de l'agent 
                guichetRetraitForm.getInitialisationView().setNumMatrUser(paramAgence.getNumMatrUser().toString());
                guichetRetraitForm.getInitialisationView().setCodeAgence(StrHandler.lpad(paramAgence.getCodStrcStrc().toString(),'0',3));                
                guichetRetraitForm.setDateVal(guichetRetraitForm.getInitialisationView().getDateActuelle());
                guichetRetraitForm.getInitialisationView().setReqCode(guichetRetraitForm.getReqCode());
    
                Long a=new Long(new Double(new Double(StrHandler.strWithoutBlanck(guichetRetraitForm.getContratView().getMontSoldCcpt())).doubleValue()*1000).longValue());
                Long b=new Long(new Double(new Double(StrHandler.strWithoutBlanck(guichetRetraitForm.getContratView().getMontAutCcpt())).doubleValue()*1000).longValue());
                Long c=new Long(new Double(new Double(StrHandler.strWithoutBlanck(guichetRetraitForm.getContratView().getMontBlocCcpt())).doubleValue()*1000).longValue());
                Long montTheo =Long.valueOf(a+b-c);
                guichetRetraitForm.setMontSoldThCcpt(montTheo);
                
            ///    guichetRetraitForm.getContratView().setContratCpt(operationMoyPay.getContratCpt());
             ///   guichetRetraitForm.setMontSoldThCcpt(Long.valueOf(StrHandler.strWithoutBlanck(guichetRetraitForm.getContratView().getMontSoldFCcpt())));
    
    /*
                if (guichetRetraitForm.getCodetrait().equalsIgnoreCase("VRDRE")){
                    guichetRetraitForm.getInitialisationView().setCodeOperation(Constants.COD_OPER_RETRAIT_DEPL_RECU.toString());
                }else if (guichetRetraitForm.getCodetrait().equalsIgnoreCase("VRDEM"))
                    guichetRetraitForm.getInitialisationView().setCodeOperation(Constants.COD_OPER_RETRAIT.toString());
     */               
                return mapping.findForward("success");
            }else{
                return verifErrorVo(mapping, request, actionMessages, vo);
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("erreur action ---------- " + e.getMessage());
            return mapping.findForward("error");
        }
    }



    public void chargerConditionBanque(ActionForm form){
        
        try{   
        GuichetRetraitForm guichetRetraitForm = (GuichetRetraitForm)form;

        List listDetailOperMoyPai = new ArrayList(); 
        DemandeConditionCmd cmd= new DemandeConditionCmd();             
        DemandeCondition demCond = new DemandeCondition  (Long.valueOf(guichetRetraitForm.getInitialisationView().getCodeOperation()).intValue(), // code operation
         guichetRetraitForm.getContratView().getContratCpt().getContratCptId().getNumCcptCcpt().intValue(), // num sous compte
         guichetRetraitForm.getContratView().getContratCpt().getContratCptId().getCodStrcStrc().intValue(), // code structure
         guichetRetraitForm.getContratView().getContratCpt().getContratCptId().getCodPrdPrd().intValue(),   // code produit      
         0, // code type piece
         null, // num pièce
         0,0,guichetRetraitForm.getInitialisationView().getDateOp());        
       
        ListConditionVo v=(ListConditionVo)cmd.execute(demCond);
          
        if(v.getListConditionBanque().size()==0) System.out.println("\n \n aucune condition de banque a appliquer \n");
        else{  
                 for(Iterator itCond = v.getListConditionBanque().iterator();itCond.hasNext();){
                       Condition condition = (Condition) itCond.next();
                       List conditionsBanque = condition.getConditionBanque();
                       
                       for(Iterator it = conditionsBanque.iterator();it.hasNext();){
                             
                              ConditionBanque conditionBanque = (ConditionBanque) it.next();                                
                              guichetRetraitForm.setMontTva(StrHandler.formatmnt(Long.valueOf(Float.valueOf(conditionBanque.getTvaCalculePourCommisions()).longValue())).toString()); 
                             
                              List detailsConditionBanque = conditionBanque.getDetailConditionBanque();
                              for(Iterator itde = detailsConditionBanque.iterator();itde.hasNext();){
                                   DetailConditionBanque detailConditionBanque = (DetailConditionBanque) itde.next();
                                   DetailOperMoyPaiement detailOperMoyPaiement = new DetailOperMoyPaiement();
                                   NomencElemtCondition nomencElemtCondition   = new NomencElemtCondition();
                                  if(detailConditionBanque.getCodTecdTecd().equals("D")) {
                                     // garnir la date valeur
                                    guichetRetraitForm.setDateVal(detailConditionBanque.getDateValeur()); 
                                 }else{ 
                                       nomencElemtCondition.setCodNecdNecd(detailConditionBanque.getCodNecdNecd());
                                       detailOperMoyPaiement.setNomencElemtCondition(nomencElemtCondition);
                                       if(detailConditionBanque.getCodTecdTecd().equals("C")){   
                                         guichetRetraitForm.setMontCommission(StrHandler.formatmnt(Long.valueOf(Float.valueOf(detailConditionBanque.getValeurCommission()).longValue())).toString());     
                                         detailOperMoyPaiement.setMontValDomp(Long.valueOf(Float.valueOf(detailConditionBanque.getValeurCommission()).longValue()));
                                         detailOperMoyPaiement.setCodTypDomp("C");
                                         listDetailOperMoyPai.add(detailOperMoyPaiement);                                  
                                       }
                                       if(detailConditionBanque.getCodTecdTecd().equals("T")){                                            
                                         detailOperMoyPaiement.setMontValDomp(Long.valueOf(Float.valueOf(detailConditionBanque.getValValVael()).longValue()));
                                         guichetRetraitForm.setMontTva(StrHandler.formatmnt(Long.valueOf(Float.valueOf(detailConditionBanque.getValValVael()).longValue())).toString());
                                       }
                                   }
                              }
                       }
                 } 
                 
             guichetRetraitForm.setListDetailOperOmp(listDetailOperMoyPai);     
         }
    } catch(Exception e  ){
        
     System.out.println("Erreur dans le chargement de condition de banque "+ e.toString())   ;
    }

}
    
        
}
