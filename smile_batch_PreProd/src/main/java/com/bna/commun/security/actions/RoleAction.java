package com.bna.commun.security.actions;

import java.io.IOException;
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

import org.acegisecurity.context.SecurityContextHolder;
import org.acegisecurity.userdetails.UserDetails;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

import com.bna.commun.security.forms.RoleForm;
import com.bna.commun.util.CollectionHandler;
import com.bna.commun.util.ContextHandler;
import com.oxia.fwk.context.Context;
import com.oxia.security.Constants;
import com.oxia.security.abc.model.Parametre;
import com.oxia.security.abc.model.Personnel;
import com.oxia.security.abc.model.Ressource;
import com.oxia.security.abc.model.Role;
import com.oxia.security.abc.model.RoleHistory;
import com.oxia.security.abc.model.RoleRessource;
import com.oxia.security.abc.service.ApplicationManager;
import com.oxia.security.abc.service.ParametreManager;
import com.oxia.security.abc.service.RessourceManager;
import com.oxia.security.abc.service.RoleManager;

import fr.improve.struts.taglib.layout.datagrid.Datagrid;

public class RoleAction extends DispatchAction{

    public  ActionForward initierPage(ActionMapping mapping, ActionForm form, 
        HttpServletRequest request, 
            HttpServletResponse response) throws IOException, 
                ServletException {
                
        try{
        RoleForm roleForm = (RoleForm) form;
        roleForm.setAction(Constants.ACTION_ADD);
        roleForm.setMode(Constants.MODE_CONSULTATION);
        //request.getSession().removeAttribute("roleForm");
        Context context= ContextHandler.getContext();         
        ApplicationManager appMgr = (ApplicationManager)context.getBean("applicationManager");
        List allApplications = appMgr.getApplications();
        roleForm.setAllApplications(allApplications);
        return mapping.findForward("success");
        }
        catch(Exception e){
            System.err.println(e.getMessage());
            return mapping.findForward("error");
        }
    }
    
    public  ActionForward chargerProfils(ActionMapping mapping, ActionForm form, 
        HttpServletRequest request, 
            HttpServletResponse response) throws IOException, 
                ServletException {
                
        RoleForm roleForm = (RoleForm) form;
        roleForm.setAction("");
        roleForm.setMode(Constants.MODE_CONSULTATION);
        Context context= ContextHandler.getContext();
        RoleManager roleMgr = (RoleManager) context.getBean("roleManager");
        if(roleForm.getCodeApp()== null || roleForm.getCodeApp().equals("")){
            List allRoles = roleMgr.getRoles();
            roleForm.setAllRoles(allRoles);            
        }
        else{
            List roles = roleMgr.getRolesByApplication(roleForm.getCodeApp());
            roleForm.setAllRoles(roles);
        }
        
        roleForm.resetAll();
        roleForm.reset(mapping, request);
         return mapping.findForward("success");            
    }
    
    
    public  ActionForward find(ActionMapping mapping, ActionForm form, 
        HttpServletRequest request, 
            HttpServletResponse response) throws IOException, 
                ServletException {
                
        try{
            RoleForm roleForm = (RoleForm)form;
            String codProfil = roleForm.getCodepfl();
            if(codProfil != null && !codProfil.equals("")){
                Context context= ContextHandler.getContext(); 
                RoleManager roleManager = (RoleManager)context.getBean("roleManager");
                Role role = roleManager.getRoleById(codProfil);
                //BeanUtils.copyProperties(roleForm, role);
                copyRoleProperties(roleForm, role);
                roleForm.resetCollections();
                roleForm.setAction(Constants.ACTION_ADD);
                roleForm.setMode(Constants.MODE_CONSULTATION);

            }
        }
        catch(Exception e){
            System.err.println("exception in method find in RoleAction "+e.getMessage());
            return mapping.findForward("error");
        }
        return mapping.findForward("success");            
    }
    
    public  ActionForward add(ActionMapping mapping, ActionForm form, 
        HttpServletRequest request, 
            HttpServletResponse response) throws IOException, 
                ServletException {
                
        RoleForm roleForm = (RoleForm) form;
        roleForm.setAction(Constants.ACTION_ADD);
        roleForm.setMode(Constants.MODE_MODIFICATION);
        roleForm.resetFields();
        return mapping.findForward("success");            
    }
    
    public  ActionForward findRessource(ActionMapping mapping, ActionForm form, 
        HttpServletRequest request, 
            HttpServletResponse response) throws IOException, 
                ServletException {
        try{
            RoleForm roleForm = (RoleForm) form;
            String  codRes = roleForm.getCodeRessource();
            if(codRes != null && !codRes.equals("")){
                Context context= ContextHandler.getContext(); 
                RessourceManager resMgr = (RessourceManager) context.getBean("ressourceManager");
                Ressource ressource = resMgr.getRessourceById(new Long(codRes));
                if(ressource != null){
                    roleForm.reset(mapping, request);
                    roleForm.setLibRess(ressource.getLibRess());
                    ParametreManager paramMgr = (ParametreManager) context.getBean("parametreManager");
                    List params = paramMgr.getParametres();
                    roleForm.setAllParams(params);
                }
            }    
        }
        catch(Exception e){
            System.err.println(e.getMessage());
        }
        
        return mapping.findForward("success");            
    }
    
    public  ActionForward affectRessource(ActionMapping mapping, ActionForm form, 
        HttpServletRequest request, 
            HttpServletResponse response) throws IOException, 
                ServletException {
                
        RoleForm roleForm = (RoleForm) form;
        Datagrid lc_datagrid = Datagrid.getInstance();
        try{
            lc_datagrid = roleForm.getRoleRessGrid();
            if(roleForm.getAction().equals(Constants.ACTION_ADD)){
                if(lc_datagrid != null && !lc_datagrid.getAddedData().isEmpty()){
                    Ressource ressAffected = new Ressource();
                    ressAffected.setCodRess(new Long(roleForm.getCodeRessource()));
                    ressAffected.setLibRess(roleForm.getLibRess());
                    Collection addedData = lc_datagrid.getAddedData();
                    Iterator it = addedData.iterator();
                    while(it.hasNext()){
                        RoleRessource addedRoleRess = (RoleRessource)it.next();
                        /*RoleRessourceId id = new RoleRessourceId(roleForm.getCodProfil(),
                                new Long(roleForm.getCodeRess()),
                                        addedRoleRess.getCodeParam());
                        addedRoleRess.setRoleRessourceId(id);*/
                        addedRoleRess.setRole(new Role(roleForm.getCodProfil()));
                        addedRoleRess.setRessource(ressAffected);
                        Parametre searchedParam = new Parametre(addedRoleRess.getCodeParam());
                        int indexParam = roleForm.getAllParams().indexOf(searchedParam);
                        Parametre paramAffected = (Parametre)roleForm.getAllParams().get(indexParam);
                        addedRoleRess.setParametre(paramAffected);
                    
                        roleForm.getRoleRessources().add(addedRoleRess);
                    }
                    // add new affected ressource to Ressource's collection for display
                    if(roleForm.getRessources().indexOf(ressAffected)== -1)
                        roleForm.getRessources().add(ressAffected);
                }
                else{
                    RoleRessource addedRoleRess = new RoleRessource();
                    /*RoleRessourceId id = new RoleRessourceId(roleForm.getCodProfil(),
                            new Long(roleForm.getCodeRess()), null);*/
                    addedRoleRess.setRole(new Role(roleForm.getCodProfil()));
                    Ressource ressAffected = new Ressource();
                    ressAffected.setCodRess(new Long(roleForm.getCodeRessource()));
                    ressAffected.setLibRess(roleForm.getLibRess());
                    addedRoleRess.setRessource(ressAffected);
                    addedRoleRess.setParametre(null);                    
                    roleForm.getRoleRessources().add(addedRoleRess);
                    // add new affected ressource to Ressource's collection for display
                    if(roleForm.getRessources().indexOf(ressAffected)== -1)
                        roleForm.getRessources().add(ressAffected);
                }
            }
            else{
                Set setRoleRessource = new HashSet() ;
                Collection modifiedData = lc_datagrid.getModifiedData();
                Iterator it = modifiedData.iterator();
                while(it.hasNext()){
                    RoleRessource modifiedRoleRess = (RoleRessource)it.next();
                    Parametre searchedParam = new Parametre(modifiedRoleRess.getCodeParam());
                    int indexParam = roleForm.getAllParams().indexOf(searchedParam);
                    Parametre paramAffected = (Parametre)roleForm.getAllParams().get(indexParam);
                    modifiedRoleRess.setParametre(paramAffected);
                    
                    setRoleRessource.add(modifiedRoleRess);

                    //roleForm.getRoleRessources().add(addedRoleRess);
                    
                }
                Collection addedData = lc_datagrid.getAddedData();
                Iterator it2 = addedData.iterator();
                while(it2.hasNext()){
                    RoleRessource addedRoleRess = (RoleRessource)it2.next();
                    /*RoleRessourceId id = new RoleRessourceId(roleForm.getCodProfil(),
                            new Long(roleForm.getCodeRess()),
                                    addedRoleRess.getCodeParam());
                    addedRoleRess.setRoleRessourceId(id);*/
                    addedRoleRess.setRole(new Role(roleForm.getCodProfil()));
                    Ressource ressAffected = new Ressource();
                    ressAffected.setCodRess(new Long(roleForm.getCodeRessource()));
                    ressAffected.setLibRess(roleForm.getLibRess());
                    addedRoleRess.setRessource(ressAffected);
                    Parametre searchedParam = new Parametre(addedRoleRess.getCodeParam());
                    int indexParam = roleForm.getAllParams().indexOf(searchedParam);
                    Parametre paramAffected = (Parametre)roleForm.getAllParams().get(indexParam);
                    addedRoleRess.setParametre(paramAffected);

                    setRoleRessource.add(addedRoleRess);
                    //roleForm.getRoleRessources().add(addedRoleRess);
                }
                Collection deletedData = lc_datagrid.getDeletedData();
                Iterator it3 = deletedData.iterator();
                while(it3.hasNext()){
                    RoleRessource deletedRoleRessParam = (RoleRessource)it3.next();
                    
                    //boolean trouve =  roleForm.getRoleRessources().contains(deletedRoleRessParam);
                    //System.out.println(trouve);
                }
                
                if(!setRoleRessource.isEmpty()){
                    roleForm.getRoleRessources().removeAll(roleForm.getListSavedRoleRes());
                    roleForm.getRoleRessources().addAll(setRoleRessource);                    
                    
                }
                //deleting parametre from roleRessource
                else if(((RoleRessource)roleForm.getListSavedRoleRes().get(0)).getParametre()!= null){
                    roleForm.getRoleRessources().removeAll(roleForm.getListSavedRoleRes());
                    RoleRessource RoleRessWithParam = (RoleRessource)roleForm.getListSavedRoleRes().get(0);
                    RoleRessource modifiedRoleRessWithoutParam = new RoleRessource();
                    modifiedRoleRessWithoutParam.setRessource(RoleRessWithParam.getRessource());
                    modifiedRoleRessWithoutParam.setRole(RoleRessWithParam.getRole());
                    modifiedRoleRessWithoutParam.setParametre(null);
    
                    roleForm.getRoleRessources().add(modifiedRoleRessWithoutParam);
                    
                }
                //remove the selected roleRossource(without parametre) from the list to delete because there isn't 
                //changes on it
                else{
                    roleForm.getListRoleResToRemove().removeAll(roleForm.getListSavedRoleRes());
                }
                roleForm.setAction(Constants.ACTION_ADD);
                roleForm.setMode(Constants.MODE_CONSULTATION);

            }
                roleForm.resetFields();
                roleForm.reset(mapping, request);
        }
        catch(Exception e){
            System.err.println(e.getMessage());
        }
        return mapping.findForward("success");            
    }
    
    public  ActionForward valider(ActionMapping mapping, ActionForm form, 
        HttpServletRequest request, 
            HttpServletResponse response) throws IOException, 
                ServletException {
                
        try{
            RoleForm roleForm = (RoleForm) form;
            Context context= ContextHandler.getContext();
            RoleManager roleManager = (RoleManager)context.getBean("roleManager");
            //delete old roleRess
            //Iterator it = roleForm.getListSavedRoleRes().iterator();
            Iterator it = roleForm.getListRoleResToRemove().iterator();
            while(it.hasNext()){
                RoleRessource roleRessTodelete = (RoleRessource)it.next();
                roleManager.removeRoleRessource(roleRessTodelete);
            }
            Role role = roleManager.getRoleById(roleForm.getCodProfil());
            //check if role properties has changed
            if(!role.getRoleStatus().equals(roleForm.getRoleStatus())||
                !role.getHeureDebut().equals(roleForm.getHeureDebut())||
                    !role.getHeureFin().equals(roleForm.getHeureFin())||
                        !role.getJourOuvr().equals(roleForm.getJourOuvr())||
                            !role.getNiveauHierarchique().equals(roleForm.getNiveauHierarchique()))
                roleForm.setRoleHasChanged(true);
            //role.getRoleRessources().removeAll(roleForm.getListSavedRoleRes());
            
            // do not use BeanUtils.copyProperties(role, roleForm)
            //BeanUtils.copyProperties(role, roleForm);
            role.setHeureDebut(roleForm.getHeureDebut());
            role.setHeureFin(roleForm.getHeureFin());
            role.setJourOuvr(roleForm.getJourOuvr());
            role.setRoleStatus(roleForm.getRoleStatus());
            role.setNiveauHierarchique(roleForm.getNiveauHierarchique());            
            role.setRoleRessources(roleForm.getRoleRessources());

            //role.setRoleRessources(roleForm.getRoleRessources());
            roleManager.saveRole(role);
            System.out.println("role updated");
            roleForm.setMode(Constants.MODE_CONSULTATION);
            roleForm.resetFields();
            roleForm.resetCollections();
            
            //if role properties has changed then insert history role
            if(roleForm.isRoleHasChanged()){
                RoleHistory roleHist = new RoleHistory();
                roleHist.setDateModifRole(new Date());
                roleHist.setRole(role);
                roleHist.setRoleName(role.getProfilName());
                roleHist.setHeureDebut(roleForm.getHeureDebut());
                roleHist.setHeureFin(roleForm.getHeureFin());
                roleHist.setRoleStatus(roleForm.getRoleStatus());
                roleHist.setJourOuvr(roleForm.getJourOuvr());
                roleHist.setNiveauHierarchique(roleForm.getNiveauHierarchique());
                Object obj = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
                if (obj instanceof UserDetails) {
                    Personnel userConnected = (Personnel)obj;
                    roleHist.setSupervisor(userConnected);
                    roleManager.saveRoleHistory(roleHist);
                    System.out.println("History role saved");
                }
                
            }

            return mapping.findForward("success");                       
        }
        catch(Exception e){
            System.err.println(e.getMessage());
            return mapping.findForward("success");                                   
        }
    }

    public  ActionForward selectRessource(ActionMapping mapping, ActionForm form, 
        HttpServletRequest request, 
            HttpServletResponse response) throws IOException, 
                ServletException {
                
        RoleForm roleForm = (RoleForm) form;
        Context context= ContextHandler.getContext();
        Datagrid lc_datagrid = Datagrid.getInstance();
        List list = new ArrayList();
        List lstRoleResWithParam = new ArrayList();
        
        String codeRes = roleForm.getCodeRess();
        Iterator it = roleForm.getRoleRessources().iterator();
        while(it.hasNext()){
            RoleRessource roleRess = (RoleRessource)it.next();
            if(roleRess.getRessource().getCodRess().toString().equals(codeRes)){
                roleForm.setCodeRessource(roleRess.getRessource().getCodRess().toString());
                roleForm.setLibRess(roleRess.getRessource().getLibRess());
                list.add(roleRess);
                
                if(roleRess.getParametre()!= null)
                    lstRoleResWithParam.add(roleRess);

            }
        }
        lc_datagrid.setDataClass(RoleRessource.class);
        lc_datagrid.setData(lstRoleResWithParam);
        roleForm.setRoleRessGrid(lc_datagrid);
        roleForm.setListSavedRoleRes(list);
        roleForm.getListRoleResToRemove().addAll(list);
        roleForm.setAction(Constants.ACTION_UPDATE);
        roleForm.setMode(Constants.MODE_MODIFICATION);

        
        if(roleForm.getAllParams().isEmpty()){
            ParametreManager paramMgr = (ParametreManager) context.getBean("parametreManager");
            List params = paramMgr.getParametres();
            roleForm.setAllParams(params);
        }
        
        return mapping.findForward("success");            
    }
    
    public  ActionForward quitter(ActionMapping mapping, ActionForm form, 
        HttpServletRequest request, 
            HttpServletResponse response) throws IOException, 
                ServletException {
        
        request.getSession().removeAttribute("roleForm");
        return mapping.findForward("indexHabilitation");
        
    }    
    

    public void copyRoleProperties(RoleForm roleForm, Role role){
        roleForm.setCodProfil(role.getCodProfil());
        roleForm.setProfilName(role.getProfilName());
        roleForm.setHeureDebut(role.getHeureDebut());
        roleForm.setHeureFin(role.getHeureFin());
        roleForm.setRoleStatus(role.getRoleStatus());
        roleForm.setJourOuvr(role.getJourOuvr());
        roleForm.setNiveauHierarchique(role.getNiveauHierarchique());
        roleForm.setRoleRessources(role.getRoleRessources());
        roleForm.setRessources(CollectionHandler.convertSetToList(role.getRessources()));
    }
    
    public Set getSetRemovedRoleRess(List listSaved, Set setAdded){
        Set set = new HashSet();
        set.addAll(listSaved);
        set.removeAll(setAdded);
        return set;
    }
}
