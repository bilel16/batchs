package com.bna.smile.web.admin.actions;

import com.bna.commun.commande.GetJourneeStructureCmd;
import com.bna.commun.commande.InitialisationCoursChangeCmd;
import com.bna.commun.model.JourneeStructure;
import com.bna.commun.model.JourneeStructureId;
import com.bna.commun.model.Structure;
import com.bna.commun.util.ContextHandler;
import com.bna.commun.util.DateHandler;
import com.bna.commun.vo.JourneeVo;
import com.bna.smile.model.constant.Constants;
import com.bna.smile.model.domainecommun.commande.GetStructureCmd;

import com.bna.smile.web.commun.model.ParamAgence;

import com.oxia.fwk.context.Context;
import com.oxia.fwk.core.IValueObject;
import com.oxia.fwk.logging.Log;

import com.oxia.security.abc.model.Interim;
import com.oxia.security.abc.model.Personnel;

import com.oxia.security.abc.model.Role;
import com.oxia.security.abc.model.UserRole;

import com.oxia.security.abc.service.InterimManager;
import com.oxia.security.abc.service.LookupManager;

import com.oxia.security.abc.service.UserManager;

import java.io.IOException;

import java.text.SimpleDateFormat;

import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.acegisecurity.context.SecurityContextHolder;
import org.acegisecurity.userdetails.UserDetails;

import org.apache.struts.action.Action;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;


public class InitSmile extends Action {

    private static Integer nbr_FailedAuthentication;

    Log logger = new Log(LoginAction.class);

    /**
     *
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws IOException
     * @throws ServletException
     */
    public String execute(HttpServletRequest request) throws IOException, 
                                                                      ServletException {
       
        ActionMessages actionMessages = new ActionMessages();
        ActionErrors actionErrors = new ActionErrors();
        try {
           
           // ActionForm form = request.getSession().getAttribute("loginForm");
            Context context = ContextHandler.getContext();
            UserManager userManager=(UserManager)context.getBean("userManager");
            Object obj = 
                SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            
            //--------------------------------------------------------///
            //------ Initialisation journee ---------------------------//
            InitialisationCoursChangeCmd ini = 
                new InitialisationCoursChangeCmd();
            JourneeVo j = new JourneeVo();
            j.setDateJourneeOuverte(DateHandler.strToDate(DateHandler.dateJour()));
            j = (JourneeVo)ini.execute(j);

            //----------------------------------------------------/// 
            
            if (obj != null && obj instanceof UserDetails) {
                Personnel userConnected = (Personnel)obj;
                if (!hasAvailableRoles(userConnected)) {
                    ActionMessage actionMessage = 
                        new ActionMessage("error.Authentication.Profil");
                    actionMessages.add("Authentication Failure", actionMessage);
                    this.saveErrors(request, actionMessages);

                    this.nbr_FailedAuthentication = 
                            (Integer)request.getSession().getAttribute("nbr_FailedAuthentication");
                    if (this.nbr_FailedAuthentication == null) {
                        request.getSession().setAttribute("nbr_FailedAuthentication", 
                                                          Integer.valueOf(1));
                    } else {
                        request.getSession().setAttribute("nbr_FailedAuthentication", 
                                                          nbr_FailedAuthentication.intValue() + 
                                                          1);
                    }

                   
                }

                //test if user connect from his Structure
                //String local =request.getLocalAddr();
                  if((!userConnectedFromHisStructure(request.getRemoteAddr(),
                                 Integer.valueOf(userConnected.getCodStruct().toString())))
                                 &&(!userInterimaire(userConnected, request.getRemoteAddr()))){

                                     ActionMessage actionMessage = new ActionMessage("error.Authentication.Structure");
                                     actionMessages.add("Authentication Failure", actionMessage);
                                     this.saveErrors(request, actionMessages);

                                     this.nbr_FailedAuthentication = (Integer)request.getSession().getAttribute("nbr_FailedAuthentication");
                                     if(this.nbr_FailedAuthentication == null){
                                         request.getSession().setAttribute("nbr_FailedAuthentication",
                                             Integer.valueOf(1));
                                     }
                                     else{
                                         request.getSession().setAttribute("nbr_FailedAuthentication",
                                             nbr_FailedAuthentication.intValue()+1);
                                     }
                                     return ("/login1.jsp");
                                 }

                /**********************************Test Intérimaire******************************************/
                                                if ((userConnectedFromHisStructure(request.getRemoteAddr(),Integer.valueOf(userConnected.getCodStruct().toString())))
                                                                && (isInterimaire(userConnected))&& !userManager.hasSupperRole(userConnected.getNumMatrUser())) 
                                                {

                                                       // ActionError actionError = new ActionError(
                                                                     //   "error.Authentication.Interim");
                                                    ActionMessage actionMessage = new ActionMessage("error.Authentication.Interim");
                                                        actionMessages.add("Authentication Failure", actionMessage);
                                                        this.saveErrors(request, actionMessages);

                                                        this.nbr_FailedAuthentication = (Integer) request
                                                                        .getSession().getAttribute(
                                                                                        "nbr_FailedAuthentication");
                                                        if (this.nbr_FailedAuthentication == null) {
                                                                request.getSession().setAttribute(
                                                                                "nbr_FailedAuthentication", Integer.valueOf(1));
                                                        } else {
                                                                request.getSession().setAttribute(
                                                                                "nbr_FailedAuthentication",
                                                                                nbr_FailedAuthentication.intValue() + 1);
                                                        }
                                                       
                                                }
                                                /********************************************************************************************************/

                /* Gestion des interims */
                
                ParamAgence paramAgence = new ParamAgence();
                
                String remoteAddress =request.getRemoteAddr();
                int occu1= remoteAddress.indexOf(".",  0);
                int occu2= remoteAddress.indexOf(".", occu1+1);
                int occu3= remoteAddress.indexOf(".", occu2+1);
                String remoteSegment =remoteAddress.substring(occu2+1,occu3);
                
                if(userConnectedFromHisStructure(request.getRemoteAddr(),Integer.valueOf(userConnected.getCodStruct().toString())))
                   paramAgence.setCodStrcStrc(userConnected.getStructure().getCodStrcStrc());
                else {
                  
                      // Context context = ContextHandler.getContext();
                       LookupManager lookupMgr =(LookupManager)context.getBean("lookupManager");
                  
                       InterimManager interimMgr = (InterimManager)context.getBean("interimManager");
                       List interims = interimMgr.getInterimaire(userConnected);
                       Integer codeStrcutureInterim=null;
                       if (!interims.isEmpty()){
                       int count=0;
                       Iterator interimItr=interims.iterator();
                       while (interimItr.hasNext()){
                           Interim interim=(Interim)interimItr.next();
                           if (lookupMgr.structureContainsSegment(interim.getCodStruct(), remoteSegment)) {
                               codeStrcutureInterim=interim.getCodStruct();
                               count=count+1;
                           }
                       }
                       if (count==1) {
                           Structure structureInterim=new Structure();
                           structureInterim.setCodStrcStrc(new Long(codeStrcutureInterim));
                           GetStructureCmd getStructureCmd=new GetStructureCmd();
                           structureInterim=(Structure)getStructureCmd.execute((IValueObject) structureInterim);
                           userConnected.setStructure(structureInterim);
                       }
                       else
                           logger.info("Agence : " +userConnected.getStructure().getCodStrcStrc()+ " Utilisateur : "+userConnected.getNumMatrUser() +" a plusieurs structure d'interim, la structure "+userConnected.getStructure().getCodStrcStrc()+" est attribuée par defaut" );
                       }
                }
                
                if (userConnected.getStructure().getStructure() != null) //{
                    paramAgence.setCodStrmStrc(userConnected.getStructure().getStructure().getCodStrcStrc());


                if (userConnected.getStructure().getTypeStructure() != null && 
                    userConnected.getStructure().getTypeStructure().getCodTstrTstr() != 
                    null) {
                    paramAgence.setCodTstrcTstrc(userConnected.getStructure().getTypeStructure().getCodTstrTstr());
                } else {
                    paramAgence.setCodTstrcTstrc(new Long(1));
                }
                paramAgence.setCodStrcStrc(userConnected.getStructure().getCodStrcStrc());
                paramAgence.setNumMatrUser(userConnected.getNumMatrUser());

                logger.info("Agence : " +userConnected.getStructure().getCodStrcStrc()+ " Utilisateur : "+userConnected.getNumMatrUser() +" Entrée dans InitSmile" );               

                request.getSession().setAttribute("paramAgBNA", paramAgence);
                //#######################################################################################//
                //
                // initialisation des parametres de l'agence */
                //
                //#######################################################################################//
                GetJourneeStructureCmd getJourneeStructure = 
                    new GetJourneeStructureCmd();

                JourneeStructure journeeStructure = new JourneeStructure();
                JourneeStructureId journeeStructureId = 
                    new JourneeStructureId();
                journeeStructureId.setCodStrcStrc(userConnected.getStructure().getCodStrcStrc());

                journeeStructure.setJourneeStructureId(journeeStructureId);
                journeeStructure = 
                        (JourneeStructure)getJourneeStructure.execute(journeeStructure);
                
                logger.info("Agence : " +userConnected.getStructure().getCodStrcStrc()+ " Utilisateur : "+userConnected.getNumMatrUser() +" Les données de l'enregistrement journee structure sont : code agence : "+ journeeStructure.getJourneeStructureId().getCodStrcStrc() +" date :" +journeeStructure.getJourneeStructureId().getDatJrnJrn() + " Status :" + journeeStructure.getCodStatJrn()); 
                
                //------------------------------------------------------------------------
                //----- donner la main qu'au chef d'agence et le second à ouvrir la journée
                 boolean testChefAgenceOuSecond = false;
                 for(Iterator it = userConnected.getUserRoles().iterator();it.hasNext() ;){
                     UserRole role = (UserRole) it.next();
                     if (role.getRole().getCodProfil().equals(Constants.CODE_PROFIL_CHEF_AGENCE) ||
                         role.getRole().getCodProfil().equals(Constants.CODE_PROFIL_SECOND_CHEF_AGENCE ) ){
                         
                                               
                         if ((role.getUserRoleSatus().equals(new Integer(1))) && (validRoleDate(role)) && (validRoleHour(role))){
                                testChefAgenceOuSecond = true;
                           break;
                        }
                     }
                    
                 }
                //-------------------------------------------------------------------------------//
                //----- Si le status de la journée est à 1 c à d que l ajournée est clôturée alors proposer une nouvelle ouverture
                request.getSession().setAttribute("alertChangementJournee","");
                
                if (journeeStructure.getJourneeStructureId() != null && 
                    journeeStructure.getCodStatJrn() != null) {

                    if (journeeStructure.getCodSesJrn().equals(Long.valueOf(1))) {
                        request.getSession().setAttribute("dateDerniereJournee", 
                                                          DateHandler.dateToStr(journeeStructure.getJourneeStructureId().getDatJrnJrn()));
                        if (testChefAgenceOuSecond == true){
                          return ("/verificationOuvertureJournee.do?initialisationView.reqCode=initierPage");
                        }else{
                          return ("/journeeNonOuverte.jsp");   
                        }
                    
                    }else { //------------------------------------------------------------------------------
                        //---------- Si la journée n' est pas cloturée et la date système a changé 
                        //---------- envoyer une alerte
                        //System.out.println(" /date "+DateHandler.strToDate(DateHandler.dateToStr(journeeStructure.getJourneeStructureId().getDatJrnJrn())));
                        //System.out.println(" /date 2 "+DateHandler.strToDate(DateHandler.dateToStr(new Date())));
                         if ( ! DateHandler.strToDate(DateHandler.dateToStr(journeeStructure.getJourneeStructureId().getDatJrnJrn())).equals(DateHandler.strToDate(DateHandler.dateToStr(new Date())))){
                            request.getSession().setAttribute("alertChangementJournee","Attention, date système non conforme avec la date comptable !!!"); 
                        }
                    }
                }

                //################################################
                //#####   Affectation des dates et generation 
                //#####   d'une exception en cas ou aucune journée n'est initiée
                
                if (journeeStructure.getJourneeStructureId() != null && 
                    journeeStructure.getJourneeStructureId().getDatJrnJrn() != 
                    null) {
                    paramAgence.setDateComptable(DateHandler.dateToStr(journeeStructure.getJourneeStructureId().getDatJrnJrn()));
                    paramAgence.setDateJours(DateHandler.dateToStr(new Date()));
                }else {
                    ActionMessage actionMessage = 
                        new ActionMessage("exception.generique", 
                                          "Aucune journée n'est initialisée pour cette structure!... Contacter l'administrateur svp. ");
                    actionMessages.add("Erreur ", actionMessage);
                    this.saveMessages(request, actionMessages);
                    return ("/errorLogin.jsp");
                }


                request.getSession().setAttribute("paramAgBNA", paramAgence);
            

                return ("/indexSMILE.jsp");
            } else {
                return ("/errorLogin.jsp");
            }
        } catch (Exception e) {
          //  System.err.println("erreur load  " + e.getMessage());
            return ("/error.jsp");
        }
    }


    public boolean hasAvailableRoles(Personnel user) {
        if (user == null)
            return false;

        Set roles = user.getUserRoles();
        if (roles == null || roles.isEmpty()) {
            return false;
        } else {
            Iterator it = roles.iterator();
            while (it.hasNext()) {
                UserRole userRole = (UserRole)it.next();
                String roleName = userRole.getRole().getProfilName();
                if ((userRole.getUserRoleSatus().equals(new Integer(1))) && 
                    (validRoleDate(userRole)) && (validRoleHour(userRole)))
                    return true;
            }
            return false;
        }

    }

    public boolean validRoleDate(UserRole userRole) {
        if (userRole == null)
            return false;

        return ((userRole.getDateDebutUR() == null) || 
                (userRole.getDateDebutUR() != null && 
                 userRole.getDateDebutUR().before(new Date()))) && 
            ((userRole.getDateFinUR() == null) || 
             (userRole.getDateFinUR() != null && 
              userRole.getDateFinUR().after(new Date())));

    }

    public boolean validRoleHour(UserRole userRole) {
        if (userRole == null)
            return false;

        Date actualDate = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("HH");
        int actualHour = Integer.parseInt(sdf.format(actualDate));

        return ((Integer.parseInt(userRole.getRole().getHeureDebut()) <= 
                 actualHour) && 
                (Integer.parseInt(userRole.getRole().getHeureFin()) > 
                 actualHour));

    }

    public boolean userConnectedFromHisStructure(String remoteAddress, 
                                                 Structure structure) {
                                                 
        int occu1= remoteAddress.indexOf(".",  0);
        int occu2= remoteAddress.indexOf(".", occu1+1);
        int occu3= remoteAddress.indexOf(".", occu2+1);
        String remoteSegment =remoteAddress.substring(occu2+1,occu3);
            
        //String remoteSegment = remoteAddress.substring(8, 11);
        if (structure == null)
            return false;
        if (structure.getCodStrcStrc().toString().equals(remoteSegment))
            return true;

        return false;
    }


    public boolean userConnectedFromHisStructure(String remoteAddress, 
                                                 Integer codStructure) {
        
        
        int occu1= remoteAddress.indexOf(".",  0);
        int occu2= remoteAddress.indexOf(".", occu1+1);
        int occu3= remoteAddress.indexOf(".", occu2+1);
        String remoteSegment =remoteAddress.substring(occu2+1,occu3);
           
       //String remoteSegment = remoteAddress.substring(8, 11);
        if (codStructure == null)
            return false;
        //if(structure.getCodStrcStrc().toString().equals(remoteSegment))
        //  return true;
        Context context = ContextHandler.getContext();
        LookupManager lookupMgr = 
            (LookupManager)context.getBean("lookupManager");
        if (lookupMgr.structureContainsSegment(codStructure, remoteSegment))
            return true;

        return false;
    }

    public boolean userInterimaire(Personnel user, String remoteAddress) {
        Context context = ContextHandler.getContext();
        InterimManager interimMgr = 
            (InterimManager)context.getBean("interimManager");
        List interims = interimMgr.getInterimaire(user);
        if (interims.isEmpty())
            return false;
        else {
            Interim interim = (Interim)interims.get(0);
            if (!userConnectedFromHisStructure(remoteAddress, 
                                               interim.getCodStruct()))
                return false;
            else
                return true;
        }

    }
    public boolean isInterimaire(Personnel user) {
                    Context context = ContextHandler.getContext();
                    InterimManager interimMgr = (InterimManager) context
                                    .getBean("interimManager");
                    List interims = interimMgr.getInterimaire(user);
                    /*
                     * if (interims.isEmpty()) return false; else {
                     * 
                     * Interim interim = (Interim)interims.get(0); if
                     * (!userConnectedFromHisStructure(remoteAddress,
                     * interim.getCodStruct())) return false; else return true; }
                     */

                    if (interims.isEmpty())
                            return false;
                    else 
                            return true;
            }
}
