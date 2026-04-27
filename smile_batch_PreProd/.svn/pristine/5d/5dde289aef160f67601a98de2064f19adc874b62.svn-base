package com.bna.commun.security.forms;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

import com.oxia.security.abc.model.RoleRessource;

import fr.improve.struts.taglib.layout.datagrid.Datagrid;

public class RoleForm extends ActionForm{

    private String codProfil;
    private String profilName;
    private String heureDebut;
    private String heureFin;
    private Integer roleStatus;
    private Integer jourOuvr;
    private String niveauHierarchique;    
    //private Set ressources = new HashSet();
    private List ressources = new ArrayList();
    private Set roleRessources = new HashSet();
    private List allApplications = new ArrayList();
    private List allRoles = new ArrayList();
    private Datagrid roleRessGrid = null;
    private List allParams = new ArrayList();
    
    private String codeApp;
    private String codepfl;
    private String codeRess;
    private String codeRessource;
    private String libRess;
    private RoleRessource savedRoleRessource;
    private List listSavedRoleRes = new ArrayList();
    private List listRoleResToRemove = new ArrayList();
    private boolean roleHasChanged;
    
    private String reqCode;
    private String action;
    private String mode;
    
    
    public RoleForm() {
    }

    public void setCodProfil(String codProfil) {
        this.codProfil = codProfil;
    }

    public String getCodProfil() {
        return codProfil;
    }

    public void setProfilName(String profilName) {
        this.profilName = profilName;
    }

    public String getProfilName() {
        return profilName;
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

    public void setHeureDebut(String heureDebut) {
        this.heureDebut = heureDebut;
    }

    public String getHeureDebut() {
        return heureDebut;
    }

    public void setHeureFin(String heureFin) {
        this.heureFin = heureFin;
    }

    public String getHeureFin() {
        return heureFin;
    }

    public void setCodepfl(String codepfl) {
        this.codepfl = codepfl;
    }

    public String getCodepfl() {
        return codepfl;
    }

    public void setRoleRessources(Set roleRessources) {
        this.roleRessources = roleRessources;
    }

    public Set getRoleRessources() {
        return roleRessources;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getAction() {
        return action;
    }

    public void setCodeRess(String codeRess) {
        this.codeRess = codeRess;
    }

    public String getCodeRess() {
        return codeRess;
    }

    public void setLibRess(String libRess) {
        this.libRess = libRess;
    }

    public String getLibRess() {
        return libRess;
    }

    
    public void reset(ActionMapping mapping, HttpServletRequest request) {
        Datagrid lc_datagrid = Datagrid.getInstance();
        lc_datagrid.setData(new ArrayList());
        lc_datagrid.setDataClass(RoleRessource.class);
        roleRessGrid = lc_datagrid;

    }

    public void setAllParams(List allParams) {
        this.allParams = allParams;
    }

    public List getAllParams() {
        return allParams;
    }


    public void setRoleRessGrid(Datagrid roleRessGrid) {
        this.roleRessGrid = roleRessGrid;
    }

    public Datagrid getRoleRessGrid() {
        return roleRessGrid;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }

    public String getMode() {
        return mode;
    }

    public void setSavedRoleRessource(RoleRessource savedRoleRessource) {
        this.savedRoleRessource = savedRoleRessource;
    }

    public RoleRessource getSavedRoleRessource() {
        return savedRoleRessource;
    }

    public void setListSavedRoleRes(List listSavedRoleRes) {
        this.listSavedRoleRes = listSavedRoleRes;
    }

    public List getListSavedRoleRes() {
        return listSavedRoleRes;
    }

    public void setRoleStatus(Integer roleStatus) {
        this.roleStatus = roleStatus;
    }

    public Integer getRoleStatus() {
        return roleStatus;
    }

    public void setJourOuvr(Integer jourOuvr) {
        this.jourOuvr = jourOuvr;
    }

    public Integer getJourOuvr() {
        return jourOuvr;
    }

    public void setAllApplications(List allApplications) {
        this.allApplications = allApplications;
    }

    public List getAllApplications() {
        return allApplications;
    }

    public void setCodeApp(String codeApp) {
        this.codeApp = codeApp;
    }

    public String getCodeApp() {
        return codeApp;
    }

    /*public void setRessources(Set ressources) {
        this.ressources = ressources;
    }

    public Set getRessources() {
        return ressources;
    }*/

    public void setRoleHasChanged(boolean roleHasChanged) {
        this.roleHasChanged = roleHasChanged;
    }

    public boolean isRoleHasChanged() {
        return roleHasChanged;
    }
    
    public void resetFields(){
        this.codeRessource = "";
        this.libRess = "";
    }
    
    public void resetCollections(){
        this.listSavedRoleRes = new ArrayList();
        this.listRoleResToRemove = new ArrayList();
    }
    
    public void resetAll(){
        this.codProfil = "";
        this.profilName = "";
        this.heureDebut="";
        this.heureFin="";
        this.roleStatus=null;
        this.jourOuvr=null;
        this.niveauHierarchique="";
        this.ressources = new ArrayList();
        this.allParams = new ArrayList();
        this.codepfl="";
        this.codeRess="";
        this.libRess="";
        this.roleHasChanged=false;
        this.listSavedRoleRes = new ArrayList();
    }    

    public void setNiveauHierarchique(String niveauHierarchique) {
        this.niveauHierarchique = niveauHierarchique;
    }

    public String getNiveauHierarchique() {
        return niveauHierarchique;
    }

    public void setRessources(List ressources) {
        this.ressources = ressources;
    }

    public List getRessources() {
        return ressources;
    }

    public void setCodeRessource(String codeRessource) {
        this.codeRessource = codeRessource;
    }

    public String getCodeRessource() {
        return codeRessource;
    }

    public void setListRoleResToRemove(List listRoleResToRemove) {
        this.listRoleResToRemove = listRoleResToRemove;
    }

    public List getListRoleResToRemove() {
        return listRoleResToRemove;
    }
}
