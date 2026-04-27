package com.bna.commun.security.forms;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.struts.action.ActionForm;

public class UserForm extends ActionForm{

    private String numMatrUser;
    private String username;
    private String userLastname;
    private Integer userStatus;
    private String userCin;
    private String adminRole;
    private Set roles = new HashSet();
    //private Set userRoles = new HashSet();
    private List userRoles = new ArrayList();
    private List allRoles = new ArrayList();
    private List listModifiedUserRoles = new ArrayList();
    private List listAddedUserRoles = new ArrayList();
    private boolean userStatusHasChanged;    
    
    private String profilCod;
    private String profilCode;
    private String profilName;
    private String dateBebut;
    private String dateFin;
    private String statuUserProfil;
    
    private String reqCode;    
    private String action;
    private String mode;    
    
    
    public UserForm() {
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public void setRoles(Set roles) {
        this.roles = roles;
    }

    public Set getRoles() {
        return roles;
    }

    public void setReqCode(String reqCode) {
        this.reqCode = reqCode;
    }

    public String getReqCode() {
        return reqCode;
    }


    public void setAllRoles(List allRoles) {
        this.allRoles = allRoles;
    }

    public List getAllRoles() {
        return allRoles;
    }

    public void setProfilCod(String profilCod) {
        this.profilCod = profilCod;
    }

    public String getProfilCod() {
        return profilCod;
    }

    public void setProfilName(String profilName) {
        this.profilName = profilName;
    }

    public String getProfilName() {
        return profilName;
    }

    public void setDateBebut(String dateBebut) {
        this.dateBebut = dateBebut;
    }

    public String getDateBebut() {
        return dateBebut;
    }

    public void setDateFin(String dateFin) {
        this.dateFin = dateFin;
    }

    public String getDateFin() {
        return dateFin;
    }

    public void setStatuUserProfil(String statuUserProfil) {
        this.statuUserProfil = statuUserProfil;
    }

    public String getStatuUserProfil() {
        return statuUserProfil;
    }

    public void setProfilCode(String profilCode) {
        this.profilCode = profilCode;
    }

    public String getProfilCode() {
        return profilCode;
    }

    /*public void setUserRoles(Set userRoles) {
        this.userRoles = userRoles;
    }

    public Set getUserRoles() {
        return userRoles;
    }*/
    
    public void deselectRole(){
        this.profilCod = "";
        this.profilCode = "";
        this.profilName = "";
        this.dateBebut = "";
        this.dateFin = "";
    }


    public void setAction(String action) {
        this.action = action;
    }

    public String getAction() {
        return action;
    }

    public void setUserStatus(Integer userStatus) {
        this.userStatus = userStatus;
    }

    public Integer getUserStatus() {
        return userStatus;
    }

    public void setUserStatusHasChanged(boolean userStatusHasChanged) {
        this.userStatusHasChanged = userStatusHasChanged;
    }

    public boolean isUserStatusHasChanged() {
        return userStatusHasChanged;
    }

    public void setListModifiedUserRoles(List listModifiedUserRoles) {
        this.listModifiedUserRoles = listModifiedUserRoles;
    }

    public List getListModifiedUserRoles() {
        return listModifiedUserRoles;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }

    public String getMode() {
        return mode;
    }
    
    public void reset() {
        this.profilName = "";
        this.allRoles = new ArrayList();
        //this.listModifiedUserRoles = new  ArrayList();
    }
    
    public void resetAll(){
        this.action="";
        this.allRoles = new ArrayList();
        this.dateBebut="";
        this.dateFin="";
        this.listModifiedUserRoles = new ArrayList();
        this.mode = "";
        this.profilCod="";
        this.profilCode="";
        this.profilName="";
        this.roles= new HashSet();
        this.statuUserProfil="";
        this.username="";
        this.userLastname="";
        this.userCin="";
        this.numMatrUser="";
        //this.userRoles = new HashSet();
         this.userRoles = new ArrayList();
        this.userStatus=null;
        this.userStatusHasChanged = false;
        this.listAddedUserRoles = new ArrayList();
    }
    
    public void resetRoleFilds(){
        this.profilName = "";
        this.profilCod = "";
        this.profilCode = "";
        this.dateBebut="";
        this.dateFin="";
    }
    
    public void resetListModifiedUserRole(){
        this.listModifiedUserRoles = new  ArrayList();
        this.listAddedUserRoles = new ArrayList();
    }

    public void setAdminRole(String adminRole) {
        this.adminRole = adminRole;
    }

    public String getAdminRole() {
        return adminRole;
    }

    public void setNumMatrUser(String numMatrUser) {
        this.numMatrUser = numMatrUser;
    }

    public String getNumMatrUser() {
        return numMatrUser;
    }

    public void setUserLastname(String userLastname) {
        this.userLastname = userLastname;
    }

    public String getUserLastname() {
        return userLastname;
    }

    public void setUserCin(String userCin) {
        this.userCin = userCin;
    }

    public String getUserCin() {
        return userCin;
    }

    public void setUserRoles(List userRoles) {
        this.userRoles = userRoles;
    }

    public List getUserRoles() {
        return userRoles;
    }

    public void setListAddedUserRoles(List listAddedUserRoles) {
        this.listAddedUserRoles = listAddedUserRoles;
    }

    public List getListAddedUserRoles() {
        return listAddedUserRoles;
    }
}
