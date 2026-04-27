package com.bna.commun.security.actions;

import java.io.IOException;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.acegisecurity.context.SecurityContextHolder;
import org.acegisecurity.userdetails.UserDetails;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;
import org.springframework.orm.ObjectRetrievalFailureException;

import com.bna.commun.security.forms.UserForm;
import com.bna.commun.util.CollectionHandler;
import com.bna.commun.util.ContextHandler;
import com.bna.commun.util.DateHandler;
import com.oxia.fwk.context.Context;
import com.oxia.security.Constants;
import com.oxia.security.abc.model.Personnel;
import com.oxia.security.abc.model.Role;
import com.oxia.security.abc.model.UserHistory;
import com.oxia.security.abc.model.UserRole;
import com.oxia.security.abc.model.UserRoleHistory;
import com.oxia.security.abc.service.RoleManager;
import com.oxia.security.abc.service.UserExistsException;
import com.oxia.security.abc.service.UserManager;


public class UserAction extends DispatchAction{

    private static final String ADMIN_YES = "YES";
    private static final String ADMIN_NO = "NO";

    public  ActionForward initierPage(ActionMapping mapping, ActionForm form, 
        HttpServletRequest request, 
            HttpServletResponse response) throws IOException, 
                ServletException {
                
        //request.getSession().removeAttribute("userForm");
        UserForm userForm = (UserForm)form;
        userForm.resetAll();
        userForm.setMode(Constants.MODE_CONSULTATION);
        Object obj = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (obj instanceof UserDetails) {
            Personnel userConnected = (Personnel)obj;
            if(userConnected.hasAdminRole()){
                userForm.setAdminRole(this.ADMIN_YES);
            }
            else
                userForm.setAdminRole(this.ADMIN_NO);
        }
        return mapping.findForward("success");            
    }
    
    public  ActionForward find(ActionMapping mapping, ActionForm form, 
        HttpServletRequest request, 
            HttpServletResponse response) throws IOException, 
                ServletException {
                
        UserForm userForm = (UserForm)form;
        try{
            //String username = userForm.getUsername();
            String usermatricule = userForm.getNumMatrUser();
            if(usermatricule != null && !usermatricule.equals("")){
                Context context= ContextHandler.getContext(); 
                UserManager userManager = (UserManager)context.getBean("userManager");
                Personnel userConnected = (Personnel)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
                Personnel user = null;
                if(userForm.getAdminRole().equals(this.ADMIN_YES)){
                    //user = userManager.getUser(username);
                    user = userManager.loadUser(usermatricule);
                }
                else{
                    user = userManager.getUserByIDAndStructure(usermatricule, 
                        userConnected.getCodStruct().toString());
                }
                    
                userForm.reset();
                if(user!= null){
                    //BeanUtils.copyProperties(userForm, user);
                    copyUserProperties(userForm,user);
                    userForm.setMode(Constants.MODE_MODIFICATION);
                }
                else
                    userForm.resetAll();           
                    
            }
            return mapping.findForward("success");
        }
        catch(ObjectRetrievalFailureException orfe){
            userForm.resetAll();
            return mapping.findForward("success");
        }
        catch(NamingException ne){
            System.err.println("exception in method find in UserAction "+ne.getMessage());
            return mapping.findForward("error");
        }
        catch(Exception e){
            System.err.println("exception in method find in UserAction "+e.getMessage());
            return mapping.findForward("error");
        }
        
    }
    
    public  ActionForward add(ActionMapping mapping, ActionForm form, 
        HttpServletRequest request, 
            HttpServletResponse response) throws IOException, 
                ServletException {
                
        UserForm userForm = (UserForm) form;
        userForm.setAction(Constants.ACTION_ADD);
        userForm.setMode(Constants.MODE_MODIFICATION);
        Context context= ContextHandler.getContext(); 
        RoleManager roleMgr = (RoleManager) context.getBean("roleManager");
        
        List allRoles = roleMgr.getActiveRoles();
        userForm.setAllRoles(allRoles);
        userForm.resetRoleFilds();

        return mapping.findForward("success");            
    }
    
    
    public  ActionForward selectRole(ActionMapping mapping, ActionForm form, 
        HttpServletRequest request, 
            HttpServletResponse response) throws IOException, 
                ServletException {
                
        try{
            UserForm userForm = (UserForm)form;
            String profilCod = userForm.getProfilCod();
            Context context= ContextHandler.getContext(); 
            RoleManager roleMgr = (RoleManager) context.getBean("roleManager");
            Role role = roleMgr.getRoleById(profilCod);
            if(role != null){
                userForm.setProfilCod(role.getCodProfil());
                userForm.setProfilName(role.getProfilName());
                userForm.setAction(Constants.ACTION_ADD);
            }
            
        }
        catch(Exception e){
            System.err.println(e.getMessage());
        }
        return mapping.findForward("success");            
    }
    
    public  ActionForward selectUserRole(ActionMapping mapping, ActionForm form, 
        HttpServletRequest request, 
            HttpServletResponse response) throws IOException, 
                ServletException {
                
        try{
            UserForm userForm = (UserForm)form;
            String profilCod = userForm.getProfilCod();
            //Context context= ContextHandler.getContext(); 
            //RoleManager roleMgr = (RoleManager) context.getBean("roleManager");
            //Role role = roleMgr.getRoleById(profilCod);

            Iterator userRoles = userForm.getUserRoles().iterator();
            while(userRoles.hasNext()){
                UserRole userRole = (UserRole) userRoles.next();
                if(userRole.getRole().getCodProfil().equals(profilCod)){
                    userForm.setProfilCod(userRole.getRole().getCodProfil());
                    userForm.setProfilName(userRole.getRole().getProfilName());
                    userForm.setStatuUserProfil(""+userRole.getUserRoleSatus());
                    if(userRole.getDateDebutUR()!=null){
                        userForm.setDateBebut(DateHandler.dateToStr(userRole.getDateDebutUR()));    
                    }
                    if(userRole.getDateFinUR()!=null){
                        userForm.setDateFin(DateHandler.dateToStr(userRole.getDateFinUR()));    
                    }
                    userForm.setAction(Constants.ACTION_UPDATE);
                    break;
                }
            }
            
            
        }
        catch(Exception e){
            System.err.println(e.getMessage());
        }
        return mapping.findForward("success");            
    }
    
    public  ActionForward affectRole(ActionMapping mapping, ActionForm form, 
        HttpServletRequest request, 
            HttpServletResponse response) throws IOException, 
                ServletException {
                
        UserForm userForm = (UserForm)form;
        UserRole userRole = new UserRole();
        userRole.setUser(new Personnel(userForm.getNumMatrUser()));
        Role roleAffected = new Role();
        roleAffected.setCodProfil(userForm.getProfilCod());
        roleAffected.setProfilName(userForm.getProfilName());
        userRole.setRole(roleAffected);
        if(userForm.getDateBebut()!=null && userForm.getDateBebut()!=""){
            userRole.setDateDebutUR(DateHandler.strToDate(userForm.getDateBebut()));    
        }
        if(userForm.getDateBebut()!=null && userForm.getDateBebut()!=""){
            userRole.setDateFinUR(DateHandler.strToDate(userForm.getDateFin())); 
        }
        if(userForm.getStatuUserProfil()!= null && userForm.getStatuUserProfil()!=""){
            userRole.setUserRoleSatus(new Integer(userForm.getStatuUserProfil()));
        }
        
        if(userForm.getAction().equals(Constants.ACTION_ADD)){
            userForm.getUserRoles().add(userRole);
            userForm.getListAddedUserRoles().add(userRole);
        }
        else{
            userForm.getUserRoles().remove(userRole);
            userForm.getUserRoles().add(userRole);
        }
        
        userForm.deselectRole();
        
        return mapping.findForward("success");            
    }
    
    public  ActionForward valider(ActionMapping mapping, ActionForm form, 
        HttpServletRequest request, 
            HttpServletResponse response) throws IOException, UserExistsException ,  
                ServletException {
                
       // try{
        UserForm userForm = (UserForm)form;
        Context context= ContextHandler.getContext();
        UserManager userMgr = (UserManager) context.getBean("userManager");
        Personnel user = userMgr.getUserOld(userForm.getNumMatrUser());
        //test if user status has changed
        if(!user.getUserStatus().equals(userForm.getUserStatus()))
            userForm.setUserStatusHasChanged(true);
        //test if properties's userRole collection has changed    
        Iterator it = user.getUserRoles().iterator();
        List listUserRoles = userForm.getUserRoles();
           // CollectionHandler.convertSetToList(userForm.getUserRoles());
        while(it.hasNext()){
            UserRole userRole = (UserRole)it.next();
            int index = listUserRoles.indexOf(userRole);
            UserRole userRoleForm = (UserRole) listUserRoles.get(index);
            if(!userRole.getUserRoleSatus().equals(userRoleForm.getUserRoleSatus()))
                    
                userForm.getListModifiedUserRoles().add(userRoleForm);
        }
        //add the newely created userRoles to the listModifiedUserRoles
        //listUserRoles.removeAll(user.getUserRoles());
        if(userForm.getListAddedUserRoles().size()>0)
            userForm.getListModifiedUserRoles().addAll(userForm.getListAddedUserRoles());
        //BeanUtils.copyProperties(user, userForm);
        user.setUserStatus(userForm.getUserStatus());
       // if(userForm.isUserStatusHasChanged()) user.setUserStatusDate(new Date());
        user.setUserRoles(CollectionHandler.convertListToSet(userForm.getUserRoles()));
        userMgr.saveUser(user);
        userForm.setMode(Constants.MODE_CONSULTATION);
        userForm.reset();
        //System.out.println("user updated");
        if(userForm.isUserStatusHasChanged()){
            //inserer historique user
            UserHistory userHistory = new UserHistory();
            userHistory.setDateModifUser(new Date());
            userHistory.setUserModified(user);
            userHistory.setUserModifiedStatus(user.getUserStatus());
            userHistory.setUserModifiedCodStruct(user.getCodStruct());
            Object obj = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            if (obj instanceof UserDetails) {
                Personnel userConnected = (Personnel)obj;
                userHistory.setSupervisor(userConnected);
                userMgr.saveUserHistory(userHistory);
                //System.out.println("History user saved");
            }
        }
        // inserer historique UserRole
        Iterator it2 = userForm.getListModifiedUserRoles().iterator();
        while(it2.hasNext()){
            UserRole  userRoleModif = (UserRole)it2.next();
            UserRoleHistory userRoleHist = new UserRoleHistory();
            userRoleHist.setDateModifUserRole(new Date());
            userRoleHist.setUserModified(user);
            userRoleHist.setRole(userRoleModif.getRole());
            userRoleHist.setDateDebutUR(userRoleModif.getDateDebutUR());
            userRoleHist.setDateFinUR(userRoleModif.getDateFinUR());
            userRoleHist.setUserRoleStatus(userRoleModif.getUserRoleSatus());
            Object obj = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            if (obj instanceof UserDetails) {
                Personnel userConnected = (Personnel)obj;
                userRoleHist.setSupervisor(userConnected);
                userMgr.saveUserRoleHistory(userRoleHist);
                //System.out.println("History userRole saved");
            }

        }
            userForm.resetListModifiedUserRole();
        
        /*}catch(Exception e){
            System.err.println("exception in method valider "+ e.getMessage());
            return mapping.findForward("error");
        }*/
         return mapping.findForward("success");            
    }
    
    public  ActionForward quitter(ActionMapping mapping, ActionForm form, 
        HttpServletRequest request, 
            HttpServletResponse response) throws IOException, 
                ServletException {
        
        request.getSession().removeAttribute("userForm");
        return mapping.findForward("indexHabilitation");
        
    }    
    
    public void copyUserProperties(UserForm userForm, Personnel user){
        userForm.setNumMatrUser(user.getNumMatrUser());
        userForm.setUsername(user.getUsername());
        userForm.setUserLastname(user.getUserLastname());
        userForm.setUserStatus(user.getUserStatus());
        userForm.setUserCin(user.getUserCin());
        userForm.setUserRoles(CollectionHandler.convertSetToList(user.getUserRoles()));
    }
}
