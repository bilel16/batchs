
package com.bna.smile.web.conditionBanque.actions;


import com.bna.commun.model.Client;
import com.bna.commun.model.ContratCpt;
import com.bna.commun.model.ContratCptId;
import com.bna.commun.model.Personne;
import com.bna.commun.util.ContextHandler;
import com.bna.commun.util.DateHandler;
import com.bna.commun.util.StrHandler;
import com.bna.smile.model.constant.Constants;
import com.bna.smile.model.domaineCB.dao.CBDAO;
import com.bna.smile.model.domainecommun.commande.GetDetailContratCmd;
import com.bna.smile.model.domainecommun.commande.GetListContratCmd;
import com.bna.smile.model.domainecommun.commande.GetListContratMandataireCmd;
import com.bna.smile.model.domainecommun.commande.GetListCotitulairePersonneCmd;
import com.bna.smile.model.domainecommun.model.Listes;
import com.bna.smile.model.domainecommun.model.PersonneCpt;
import com.bna.smile.model.domainecommun.model.PersonneRechercheContratVo;
import com.bna.smile.model.domainecommun.model.PersonneStrc;
import com.bna.smile.model.domainecontratcompte.souscriptioncontratcompte.commande.GetPersClientCmd;
import com.bna.smile.model.domaineplacement.dao.PlacementDAO;
import com.bna.smile.model.reporting.model.CommonReportVO;
import com.bna.smile.web.commun.model.ParamAgence;
import com.bna.smile.web.commun.util.SessionUtil;
import com.bna.smile.web.conditionBanque.forms.ConditionBanqueForm;
import com.bna.smile.web.placement.forms.SouscriptionContratPlacementForm;

import com.bna.smile.web.procuration.util.ContratCptView;
import com.bna.smile.web.reporting.forms.ExtraitCptForm;
import com.bna.smile.web.souscription.actions.ConsultationContratCompteAction;
import com.bna.smile.web.souscription.forms.ConsultationContratCompteForm;

import com.oxia.fwk.context.Context;

import java.io.IOException;


import java.text.DateFormat;

import java.text.SimpleDateFormat;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import java.util.Map;

import java.util.Vector;

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

public class ConditionBanqueAction extends DispatchAction {

    /**
     * <B> Action de la page  avanceContratPlacement.jsp  </B>
     * @param mapping The ActionMapping used to select this instance.
     * @param form The optional ActionForm bean for this request.
     * @param request The HTTP Request we are processing.
     * @param response The HTTP Response we are processing.
     * Nom du package : com.bna.smile.web.souscription.actions
     *
 
     */
    
    ParamAgence paramAgence = new ParamAgence();
    private static final Logger logger = Logger.getLogger(ConditionBanqueAction.class);
    
    public  ActionForward initierPage(ActionMapping mapping, 
                                         ActionForm form, 
                                         HttpServletRequest request, 
                                         HttpServletResponse response) throws IOException, 
                                                                              ServletException {
       
        ConditionBanqueForm conditionBanqueForm =(ConditionBanqueForm)form;        
       
        StringBuffer text = 
                       new StringBuffer("L'initialisation de la consultation des états de Condition de banque a été interrompue, veuillez transmettre ce message à l'équipe informatique: ");
                       
        SessionUtil sessionUtil =new SessionUtil();
        try{
        //Suppression des anciens Bean de type Form de la session, SAUF "ExtraitCptForm"
         sessionUtil.removeSession(request,"conditionBanqueForm");   
         
         paramAgence = (ParamAgence)request.getSession().getAttribute("paramAgBNA");
        //conditionBanqueForm.clearForm();
        if(paramAgence != null){
         conditionBanqueForm.setCodStrcRech(paramAgence.getCodStrcStrc().toString());
         //conditionBanqueForm.getI.getInitialisationView().setNumMatrUser(paramAgence.getNumMatrUser());
             }else {
                  logger.debug("L'objet param agence est null");
                 }
   
        return mapping.findForward("success");
         } 
        catch (Exception e) {
                        //text.append("Exception au niveau de l'agence:"); text.append(extraitCptForm.getCodStrcStrc());
                        text.append(". Exception :"); text.append(e.toString());
                        ActionMessages actionMessages = new ActionMessages();
                        ActionMessage actionMessage = 
                            new ActionMessage("exception.generique", 
                                              e.getMessage());
                        actionMessages.add("Erreur ", actionMessage);   
                        this.saveMessages(request, actionMessages);
                        logger.error(text.toString(),e);
                        return mapping.findForward("error");
                    }   
    }

    public  ActionForward chargerListeOperationProduit(ActionMapping mapping, 
                                         ActionForm form, 
                                         HttpServletRequest request, 
                                         HttpServletResponse response) throws IOException, 
                                                                              ServletException {
       
        Context context = ContextHandler.getContext();
        ConditionBanqueForm conditionBanqueForm =(ConditionBanqueForm)form;
       
        CBDAO cbDao= (CBDAO)context.getBean("cbDaoLstOper");   
        
        List ls = new ArrayList();
        ls = cbDao.getListOperation();
        //conditionBanqueForm.setListeOperation(ls);        
        
        cbDao= (CBDAO)context.getBean("cbDaoLstPrd");   
        ls=cbDao.getListProduit();
       // conditionBanqueForm.setListeProduit(ls);
        
        
        return mapping.findForward("success");   
    
    
    }
    
    public  ActionForward imprimer(ActionMapping mapping, 
                                         ActionForm form, 
                                         HttpServletRequest request, 
                                         HttpServletResponse response) throws IOException, 
                                                                              ServletException {
       
       

        ParamAgence paramAgence = (ParamAgence)request.getSession().getAttribute("paramAgBNA");    
        
        // FORM Bean
        ConditionBanqueForm conditionBanqueForm = (ConditionBanqueForm)form;
        
        
        Map parameters = new HashMap();

        String pMatrUser = "P_NUM_MATR_USER";
        String vMatrUser = paramAgence.getNumMatrUser().toString();
        
        String pLibEtat = "P_LIB_ETAT";
        String vLibEtat="";
        
        //String vMatr=userForm.getNumMatrUser();
        String vCodStrcStrc = paramAgence.getCodStrcStrc().toString();
        CommonReportVO valueObject=new CommonReportVO();
        
        try
        {
            parameters.put(pMatrUser, vMatrUser);
            
            //Condition generale par operation produit
            if (conditionBanqueForm.getEtat().equals("0"))
            {
                vLibEtat="Liste des conditions générales par Opération / Produit";
                
                String pCodOper = "COD_OPER";
                double vCodOper=new Double (conditionBanqueForm.getCodeOperation()).doubleValue();
                parameters.put(pCodOper,vCodOper);
                
                String pCodPrd="COD_PRD";
                double vCodPrd=0;
                if (conditionBanqueForm.getCodeProduit().equalsIgnoreCase(""))
                    parameters.put(pCodPrd,vCodPrd);
                else
                {
                    vCodPrd=new Double(conditionBanqueForm.getCodeProduit()).doubleValue();
                    parameters.put(pCodPrd,vCodPrd);
                }
                valueObject.setParams(parameters);
                
                Context context = ContextHandler.getContext();
                CBDAO cbDao= (CBDAO)context.getBean("cbDaoLstOper");
                int res;
                if (conditionBanqueForm.getCodeProduit().equalsIgnoreCase(""))
                    res=cbDao.getCBGeneraleOperPrd(new Long(conditionBanqueForm.getCodeOperation()).longValue(),-1);
                else
                    res=cbDao.getCBGeneraleOperPrd(new Long(conditionBanqueForm.getCodeOperation()).longValue(),new Long(conditionBanqueForm.getCodeProduit()).longValue());    

                if (res==1)
                {
                    valueObject.setNomReport("CB_Generale_Oper_Prd");
                }
                else if (res==2)
                {
                    valueObject.setNomReport("CB_Generale_Oper_Grp_Prd");
                }
                else if (res==3)
                {
                    valueObject.setNomReport("CB_Generale_Oper_SFam_Prd");
                }
                else if (res==4)
                {
                    valueObject.setNomReport("CB_Generale_Oper_Fam_Prd");
                }
                
                else
                    return mapping.findForward("success");
            }
            //Conditions préférentielles à échoir dans une période 
            else if (conditionBanqueForm.getEtat().equals("1"))
            {
                vLibEtat="Liste des Conditions préférentielles à échoir dans une période";
                
               //String pDateFinEcheance="DateFin";
                //DateFormat df = new SimpleDateFormat ("yyyy-MM-dd");
               
               /*15/12/2009
                * Date vDateDebut = DateHandler.strToDate(conditionBanqueForm.getDateDebutEcheance());
                parameters.put(pDateDebutEcheance,vDateDebut);
                */
               
                String pDateEcheance="DateEcheance";
                String d=conditionBanqueForm.getDateEcheance();
                Date vDateEchance = DateHandler.strToDate(d);
                parameters.put(pDateEcheance,vDateEchance);
                
                String pCodeAg="CODE_AG";
                String vCodeAg=paramAgence.getCodStrcStrc().toString();
                parameters.put(pCodeAg,vCodeAg);
                
                valueObject.setNomReport("CB_Pref_A_Echoir");
                
            }
            //Conditions préférentielles échus 
            else if (conditionBanqueForm.getEtat().equals("2"))
            {
                vLibEtat="Liste des Conditions préférentielles échues";
                
                String pDateEcheance="date";
                Date vDateEcheance = DateHandler.strToDate(conditionBanqueForm.getDateDebutEcheance());
                parameters.put(pDateEcheance,vDateEcheance);
                
                String pCodeAg="CODE_AG";
                String vCodeAg=paramAgence.getCodStrcStrc().toString();
                parameters.put(pCodeAg,vCodeAg);
                
                valueObject.setNomReport("CB_Pref_Echues");
            }
            //Conditions de masse entre la BNA et autres organismes
            else if (conditionBanqueForm.getEtat().equals("3"))
            {
                vLibEtat="Liste des Conditions de masse entre la BNA et "+conditionBanqueForm.getOrganisme();
                
                String pOrg="CRITERE";
                double vOrg=new Double(conditionBanqueForm.getCodeOrganisme()).doubleValue();
                parameters.put(pOrg,vOrg);
                
                valueObject.setNomReport("CB_Masse_Organisme");
            }
            //Conditions préférentielles relatives à un client
            else if (conditionBanqueForm.getEtat().equals("4"))
            {
                vLibEtat="La liste des Conditions préférentielles de client: "+ conditionBanqueForm.getNom()+" "+conditionBanqueForm.getPrenom() ;
                
                String pClt="NUM_PERS";
                String vClt=conditionBanqueForm.getNumSeqPers();
                parameters.put(pClt,vClt);
                
                valueObject.setNomReport("CB_CLIENT");
            }
            //Conditions préférentielles relatives à un groupe de clients 
            else if (conditionBanqueForm.getEtat().equals("5"))
            {
                vLibEtat="Liste des Conditions préférentielles relatives à un groupe de clients";
                String pOrg="CRITERE";
                double vOrg=new Double(conditionBanqueForm.getCodeGroupe()).doubleValue();
                parameters.put(pOrg,vOrg);
                valueObject.setNomReport("CB_Masse_Groupe");
            }
            

            parameters.put(pLibEtat, vLibEtat);
            valueObject.setParams(parameters);
            valueObject.setNomDossier("ConditionBanque");
            request.getSession().setAttribute("CommonPrintVo",valueObject);
            request.setAttribute("print","1");
            
            return mapping.findForward("success");
        }
        catch(Exception e){
            System.err.println(e.getMessage());
            return mapping.findForward("error");
        }
    }
    
    
    public  ActionForward chargerListeGroupe(ActionMapping mapping, 
                                         ActionForm form, 
                                         HttpServletRequest request, 
                                         HttpServletResponse response) throws IOException, 
                                                                              ServletException {
       
        Context context = ContextHandler.getContext();
        ConditionBanqueForm conditionBanqueForm =(ConditionBanqueForm)form;
        CBDAO cbDao= (CBDAO)context.getBean("cbDaoLstGroupe");   
        
        List ls = new ArrayList();
        ls = cbDao.getListGroupe();
        //conditionBanqueForm.setListeGroupe(ls);         
         return mapping.findForward("success");   
    
    
    }

    public  ActionForward chargerListeOrganisme(ActionMapping mapping, 
                                         ActionForm form, 
                                         HttpServletRequest request, 
                                         HttpServletResponse response) throws IOException, 
                                                                              ServletException {
       
        Context context = ContextHandler.getContext();
        ConditionBanqueForm conditionBanqueForm =(ConditionBanqueForm)form;
        CBDAO cbDao= (CBDAO)context.getBean("cbDaoLstGroupe");   
        
        List ls = new ArrayList();
        ls = cbDao.getListOrganisme();
        //conditionBanqueForm.setListeOrganisme(ls);         
         return mapping.findForward("success");   
    
    
    }
    
    public  ActionForward chargerCBEchu(ActionMapping mapping, 
                                         ActionForm form, 
                                         HttpServletRequest request, 
                                         HttpServletResponse response) throws IOException, 
                                                                              ServletException {
       
        Context context = ContextHandler.getContext();
        ConditionBanqueForm conditionBanqueForm =(ConditionBanqueForm)form;
      /*  CBDAO cbDao= (CBDAO)context.getBean("cbDaoLstGroupe");   
        
        List ls = new ArrayList();
        ls = cbDao.getListGroupe();
        conditionBanqueForm.setListeGroupe(ls);         */
         return mapping.findForward("success");   
    
    
    } 
    
    
    public  ActionForward chargerListeClient(ActionMapping mapping, 
                                         ActionForm form, 
                                         HttpServletRequest request, 
                                         HttpServletResponse response) throws IOException, 
                                                                              ServletException {
       
        Context context = ContextHandler.getContext();
        ConditionBanqueForm conditionBanqueForm =(ConditionBanqueForm)form;
      /*  CBDAO cbDao= (CBDAO)context.getBean("cbDaoLstGroupe");   
        
        List ls = new ArrayList();
        ls = cbDao.getListGroupe();
        conditionBanqueForm.setListeGroupe(ls);         */
         return mapping.findForward("success");   
    
    
    } 
    
    
    
    public ActionForward rechercherPersonne(ActionMapping mapping, 
                                            ActionForm form, 
                                            HttpServletRequest request, 
                                            HttpServletResponse response) throws IOException, 
                                                                                 ServletException {

        /*************************************commande de rechercher personne*/
         ActionMessages actionMessages = new ActionMessages();
        
        try {
                ConditionBanqueForm conditionBanqueForm =(ConditionBanqueForm)form;
                paramAgence = 
                        (ParamAgence)request.getSession().getAttribute("paramAgBNA"); /// structure de l'agent qui fait la saisie
            PersonneRechercheContratVo personneRechercheContratVo = new PersonneRechercheContratVo();
            
            Listes listesCpts = new Listes();
            listesCpts.setList(new ArrayList());           
            PersonneStrc personneStrc = new PersonneStrc();
              
                            
            // si la recherche est effectuée par type et num piece

            if (conditionBanqueForm.getChoix().equals("0")) {
                personneStrc.setCodTpceTpce(new Long(conditionBanqueForm.getTypePieceId()));
                personneStrc.setNumPcePers(conditionBanqueForm.getNumPieceId());
                personneStrc.setCodStrcStrc(paramAgence.getCodStrcStrc());
                personneRechercheContratVo.setPersonneStrc(personneStrc);
                personneRechercheContratVo.setEtatContrat(Constants.COD_ETAT_CPT_VALID);
                GetListContratCmd getListContratCmd = new GetListContratCmd();                
                listesCpts = (Listes)getListContratCmd.execute(personneRechercheContratVo);
            } else if (conditionBanqueForm.getChoix().equals("1")) {
                // si la recherche est effectuée par num de contrat
                GetDetailContratCmd getDetailContratCmd = 
                    new GetDetailContratCmd();
                ContratCptId contratCptId = new ContratCptId();
                ContratCpt contratCpt = new ContratCpt();
                contratCptId.setCodPrdPrd(new Long(conditionBanqueForm.getCodPrdRech()));
                contratCptId.setNumCcptCcpt(new Long(conditionBanqueForm.getNumCcptRech()));
                contratCptId.setCodStrcStrc(paramAgence.getCodStrcStrc());
                contratCpt = (ContratCpt)getDetailContratCmd.execute(contratCptId);
                
                if (contratCpt.getContratCptId() != null) {
                    listesCpts.getList().add(contratCpt);
                }
            }
            if(!listesCpts.hasError()){  
                    
                       if (listesCpts.getList().size() > 0) {
                        // affecter la liste des contrats à la liste de collection Tag
                           
                      
                        ContratCpt premierContrat = (ContratCpt)listesCpts.getList().get(0);
                        Client client = premierContrat.getClient();
                        Personne personne = premierContrat.getClient().getPersonne();
                        conditionBanqueForm.setNumSeqPers(personne.getNumSeqPers().toString());
                        
                        if (client.getTypePers().getCodTperTper().equals(Constants.PERSPHYSIQUE)) {
                            conditionBanqueForm.setNom(personne.getNomNomPers());
                            conditionBanqueForm.setPrenom(personne.getNomPrnPers());                            
                          
                          
                        } else if (client.getTypePers().getCodTperTper().equals(Constants.PERSMORALE)) {
                            conditionBanqueForm.setNom(personne.getNomRsPers());
                            conditionBanqueForm.setPrenom(personne.getLibSiglPers());                           
                            
    
                        } else if (client.getTypePers().getCodTperTper().equals(Constants.ENTCOTITULAIRE)) {
                            conditionBanqueForm.setNom(personne.getNomNomPers());
                          
                        }                       
            }
             
            }
            return mapping.findForward("success");
            } catch (Exception e) {
                com.oxia.fwk.core.Error erreur = new com.oxia.fwk.core.Error();
                StringBuffer text = 
                    new StringBuffer("la transaction est Interrompu, une erreur dans ConditionBanqueAction / Dispatch Action :rechercherPersonne ");
                text.append(e.toString());
                erreur.setCode("200");
                erreur.setDescription(text.toString());
                logger.error(e);  
                ActionMessage actionMessage = new ActionMessage("exception.generique",erreur.getDescription());
                actionMessages.add("Erreur ", actionMessage);
                this.saveMessages(request, actionMessages);
                return mapping.findForward("error");
            }
            
      
    }


}
